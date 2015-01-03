/*
 *  (c) 2005-2009 Richard Cowin (http://openrico.org)
 *  (c) 2005-2009 Matt Brown (http://dowdybrown.com)
 *
 *  Rico is licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 *  file except in compliance with the License. You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the
 *  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 *  either express or implied. See the License for the specific language governing permissions
 *  and limitations under the License.
 */

if(typeof Rico=='undefined') throw("LiveGridAjax requires the Rico JavaScript framework");
if(typeof Rico.Buffer=='undefined') throw("LiveGridAjax requires the Rico.Buffer object");


Rico.Buffer.AjaxXML = function(url,options,ajaxOptions) {
  this.initialize(url,options,ajaxOptions);
}

Rico.Buffer.AjaxXML.prototype = {
/**
 * @class Implements buffer for LiveGrid. Loads data from server via a single AJAX call.
 * @extends Rico.Buffer.Base
 * @constructs
 */
  initialize: function(url,options,ajaxOptions) {
    Rico.extend(this, new Rico.Buffer.Base());
    Rico.extend(this, Rico.Buffer.AjaxXMLMethods);
    this.dataSource=url;
    this.options.bufferTimeout=20000;            // time to wait for ajax response (milliseconds)
    this.options.requestParameters=[];
    this.options.waitMsg=Rico.getPhraseById("waitForData");  // replace this with an image tag if you prefer
    this.options.canFilter=true;
    Rico.extend(this.options, options || {});
    this.ajaxOptions = { parameters: null, method : 'get' };
    Rico.extend(this.ajaxOptions, ajaxOptions || {});
    this.requestCount=0;
    this.processingRequest=false;
    this.pendingRequest=-2;
    this.fetchData=true;
    this.sortParm={};
  }
}

Rico.Buffer.AjaxXMLMethods = {

/** @lends Rico.Buffer.AjaxXML# */
  fetch: function(offset) {
    if (this.fetchData) {
      this.foundRowCount=true;
      this.fetchData=false;
      this.processingRequest=true;
      this.liveGrid.showMsg(this.options.waitMsg);
      this.timeoutHandler = Rico.runLater(this.options.bufferTimeout,this,'handleTimedOut');
      this.ajaxOptions.parameters = this.formQueryHashXML(0,-1);
      Rico.log('sending request');
      if (typeof this.dataSource=='string') {
        this.ajaxOptions.onComplete = Rico.bind(this,'ajaxUpdate',offset);
        new Rico.ajaxRequest(this.dataSource, this.ajaxOptions);
      } else {
        this.ajaxOptions.onComplete = Rico.bind(this,'jsUpdate',offset);
        this.dataSource(this.ajaxOptions);
      }
    } else {
      if (offset < 0) {
        this.applyFilters();
        this.setTotalRows(this.size);
        offset=0;
      }
      this.liveGrid.refreshContents(offset);
    }
  },

/**
 * Server did not respond in time... assume that there could have been
 * an error, and allow requests to be processed again.
 */
  handleTimedOut: function() {
    Rico.log("Request Timed Out");
    this.liveGrid.showMsg(Rico.getPhraseById("requestTimedOut"));
  },

  formQueryHashXML: function(startPos,fetchSize) {
    var queryHash= {
      id: this.liveGrid.tableId,
      page_size: (typeof fetchSize=='number') ? fetchSize : this.totalRows,
      offset: startPos.toString()
    };
    if (!this.foundRowCount) queryHash['get_total']='true';
    if (this.options.requestParameters) {
      for ( var i=0; i < this.options.requestParameters.length; i++ ) {
        var anArg = this.options.requestParameters[i];
        if ( anArg.name != undefined && anArg.value != undefined ) {
          queryHash[anArg.name]=anArg.value;
        } else {
          var ePos  = anArg.indexOf('=');
          var argName  = anArg.substring( 0, ePos );
          var argValue = anArg.substring( ePos + 1 );
          queryHash[argName]=argValue;
        }
      }
    }
    return queryHash;
  },

  clearTimer: function() {
    if(typeof this.timeoutHandler != "number") return;
    window.clearTimeout(this.timeoutHandler);
    delete this.timeoutHandler;
  },

  // used by both XML and SQL buffers
  jsUpdate: function(startPos, newRows, newAttr, totalRows, errMsg) {
    this.clearTimer();
    this.processingRequest=false;
    Rico.log("jsUpdate: "+arguments.length);
    if (errMsg) {
      Rico.log("jsUpdate: received error="+errMsg);
      this.liveGrid.showMsg(Rico.getPhraseById("requestError",errMsg));
      return;
    }
    this.rcvdRows = newRows.length;
    if (typeof totalRows=='number') {
      this.rowcntContent = totalRows.toString();
      this.rcvdRowCount = true;
      this.foundRowCount = true;
      Rico.log("jsUpdate: found RowCount="+this.rowcntContent);
    }
    this.updateBuffer(startPos, newRows, newAttr);
    if (this.options.onAjaxUpdate)
      this.options.onAjaxUpdate();
    this.updateGrid(startPos);
    if (this.options.TimeOut && this.timerMsg)
      this.restartSessionTimer();
    if (this.pendingRequest>=-1) {
      var offset=this.pendingRequest;
      Rico.log("jsUpdate: found pending request for offset="+offset);
      this.pendingRequest=-2;
      this.fetch(offset);
    }
  },

  // used by both XML and SQL buffers
  ajaxUpdate: function(startPos,request) {
    this.clearTimer();
    this.processingRequest=false;
    if (request.status != 200) {
      Rico.log("ajaxUpdate: received http error="+request.status);
      this.liveGrid.showMsg(Rico.getPhraseById("httpError",request.status));
      return;
    }
    if (!this.processResponse(startPos,request)) return;
    if (this.options.onAjaxUpdate)
      this.options.onAjaxUpdate();
    this.updateGrid(startPos);
    if (this.options.TimeOut && this.timerMsg)
      this.restartSessionTimer();
    if (this.pendingRequest>=-1) {
      var offset=this.pendingRequest;
      Rico.log("ajaxUpdate: found pending request for offset="+offset);
      this.pendingRequest=-2;
      this.fetch(offset);
    }
  },
  
  // used by both XML and SQL buffers
  processResponse: function(startPos,request) {
    // The response text may contain META DATA for debugging if client side debugging is enabled in VS
    var xmlDoc = request.responseXML;
    if (request.responseText.substring(0, 4) == "<!--") {
      var nEnd = request.responseText.indexOf("-->");
      if (nEnd == -1) {
        this.liveGrid.showMsg('Web server error - client side debugging may be enabled');
        return false;
      }
      xmlDoc = Rico.createXmlDocument();
      xmlDoc.loadXML(request.responseText.substring(nEnd+3));
    }

    // process children of <ajax-response>
    var response = xmlDoc.getElementsByTagName("ajax-response");
    if (response == null || response.length != 1) return false;
    this.rcvdRows = 0;
    this.rcvdRowCount = false;
    var ajaxResponse=response[0];
    var debugtags = ajaxResponse.getElementsByTagName('debug');
    for (var i=0; i<debugtags.length; i++)
      Rico.log("ajaxUpdate: debug msg "+i+": "+Rico.getContentAsString(debugtags[i],this.options.isEncoded));
    var error = ajaxResponse.getElementsByTagName('error');
    if (error.length > 0) {
      var msg=Rico.getContentAsString(error[0],this.options.isEncoded);
      alert("Data provider returned an error:\n"+msg);
      Rico.log("Data provider returned an error:\n"+msg);
      return false;
    }
    var rowsElement = ajaxResponse.getElementsByTagName('rows')[0];
    if (!rowsElement) {
      Rico.log("ajaxUpdate: invalid response");
      this.liveGrid.showMsg(Rico.getPhraseById("invalidResponse"));
      return false;
    }
    var rowcnttags = ajaxResponse.getElementsByTagName('rowcount');
    if (rowcnttags && rowcnttags.length==1) {
      this.rowcntContent = Rico.getContentAsString(rowcnttags[0],this.options.isEncoded);
      this.rcvdRowCount = true;
      this.foundRowCount = true;
      Rico.log("ajaxUpdate: found RowCount="+this.rowcntContent);
    }

    // process <rows>
    this.updateUI = rowsElement.getAttribute("update_ui") == "true";
    this.rcvdOffset = rowsElement.getAttribute("offset");
    Rico.log("ajaxUpdate: rcvdOffset="+this.rcvdOffset);
    var newRows = this.dom2jstable(rowsElement);
    var newAttr = (this.options.acceptAttr.length > 0) ? this.dom2jstableAttr(rowsElement) : false;
    this.rcvdRows = newRows.length;
    this.updateBuffer(startPos, newRows, newAttr);
    return true;
  },

  // specific to XML buffer
  updateBuffer: function(start, newRows, newAttr) {
    this.baseRows = newRows;
    this.attr = newAttr;
    Rico.log("updateBuffer: # of rows="+this.rcvdRows);
    this.rcvdRowCount=true;
    this.rowcntContent=this.rcvdRows;
    if (typeof this.delayedSortCol=='number')
      this.sortBuffer(this.delayedSortCol);
    this.applyFilters();
    this.startPos = 0;
  },

  // used by both XML and SQL buffers
  updateGrid: function(offset) {
    Rico.log("updateGrid, size="+this.size+' rcv cnt type='+typeof(this.rowcntContent));
    var newpos;
    if (this.rcvdRowCount==true) {
      Rico.log("found row cnt: "+this.rowcntContent);
      var eofrow=parseInt(this.rowcntContent,10);
      var lastTotalRows=this.totalRows;
      if (!isNaN(eofrow) && eofrow!=lastTotalRows) {
        this.setTotalRows(eofrow);
        newpos=Math.min(this.liveGrid.topOfLastPage(),offset);
        Rico.log("updateGrid: new rowcnt="+eofrow+" newpos="+newpos);
        if (lastTotalRows==0 && this.liveGrid.sizeTo=='data')
          this.liveGrid.adjustPageSize();
        this.liveGrid.scrollToRow(newpos);
        if ( this.isInRange(newpos) ) {
          this.liveGrid.refreshContents(newpos);
        } else {
          this.fetch(newpos);
        }
        return;
      }
    } else {
      var lastbufrow=offset+this.rcvdRows;
      if (lastbufrow>this.totalRows) {
        var newcnt=lastbufrow;
        Rico.log("extending totrows to "+newcnt);
        this.setTotalRows(newcnt);
      }
    }
    newpos=this.liveGrid.pixeltorow(this.liveGrid.scrollDiv.scrollTop);
    Rico.log("updateGrid: newpos="+newpos);
    this.liveGrid.refreshContents(newpos);
  }

};



Rico.Buffer.AjaxSQL = function(url,options,ajaxOptions) {
  this.initialize(url,options,ajaxOptions);
}

Rico.Buffer.AjaxSQL.prototype = {
/**
 * @class Implements buffer for LiveGrid. Loads data from server in chunks as user scrolls through the grid.
 * @extends Rico.Buffer.AjaxXML
 * @constructs
 */
  initialize: function(url,options,ajaxOptions) {
    Rico.extend(this, new Rico.Buffer.AjaxXML());
    Rico.extend(this, Rico.Buffer.AjaxSQLMethods);
    this.dataSource=url;
    this.options.canFilter=true;
    this.options.largeBufferSize  = 7.0;   // 7 pages
    this.options.nearLimitFactor  = 1.0;   // 1 page
    Rico.extend(this.options, options || {});
    Rico.extend(this.ajaxOptions, ajaxOptions || {});
  }
}

Rico.Buffer.AjaxSQLMethods = {
/** @lends Rico.Buffer.AjaxSQL# */

  registerGrid: function(liveGrid) {
    this.liveGrid = liveGrid;
    this.sessionExpired=false;
    this.timerMsg=document.getElementById(liveGrid.tableId+'_timer');
    if (this.options.TimeOut && this.timerMsg) {
      if (!this.timerMsg.title) this.timerMsg.title=Rico.getPhraseById("sessionExpireMinutes");
      this.restartSessionTimer();
    }
  },

  setBufferSize: function(pageSize) {
    this.maxFetchSize = Math.max(50,parseInt(this.options.largeBufferSize * pageSize,10));
    this.nearLimit = parseInt(this.options.nearLimitFactor * pageSize,10);
    this.maxBufferSize = this.maxFetchSize * 3;
  },

  restartSessionTimer: function() {
    if (this.sessionExpired==true) return;
    this.sessionEndTime = (new Date()).getTime() + this.options.TimeOut*60000;
    if (this.sessionTimer) clearTimeout(this.sessionTimer);
    this.updateSessionTimer();
  },

  updateSessionTimer: function() {
    var now=(new Date()).getTime();
    if (now > this.sessionEndTime) {
      this.displaySessionTimer(Rico.getPhraseById("sessionExpired"));
      this.timerMsg.style.backgroundColor="red";
      this.sessionExpired=true;
    } else {
      var timeRemaining=Math.ceil((this.sessionEndTime - now) / 60000);
      this.displaySessionTimer(timeRemaining);
      this.sessionTimer=Rico.runLater(10000,this,'updateSessionTimer');
    }
  },

  displaySessionTimer: function(msg) {
    this.timerMsg.innerHTML='&nbsp;'+msg+'&nbsp;';
  },

  /**
   * Update the grid with fresh data from the database, maintaining scroll position.
   * @param resetRowCount indicates whether the total row count should be refreshed as well
   */
  refresh: function(resetRowCount) {
    var lastGridPos=this.liveGrid.lastRowPos;
    this.clear();
    if (resetRowCount) {
      this.setTotalRows(0);
      this.foundRowCount = false;
    }
    this.liveGrid.clearBookmark();
    this.liveGrid.clearRows();
    this.fetch(lastGridPos);
  },

  /**
   * Fetch data from database.
   * @param offset position (row) within the dataset (-1=clear existing buffer before issuing request)
   */
  fetch: function(offset) {
    Rico.log("AjaxSQL fetch: offset="+offset+', lastOffset='+this.lastOffset);
    if (this.processingRequest) {
      Rico.log("AjaxSQL fetch: queue request");
      this.pendingRequest=offset;
      return;
    }
    if (offset < 0) {
      this.clear();
      this.setTotalRows(0);
      this.foundRowCount = false;
      offset=0;
    }
    var lastOffset = this.lastOffset;
    this.lastOffset = offset;
    if (this.isInRange(offset)) {
      Rico.log("AjaxSQL fetch: in buffer");
      this.liveGrid.refreshContents(offset);
      if (offset > lastOffset) {
        if (offset+this.liveGrid.pageSize < this.endPos()-this.nearLimit) return;
        if (this.endPos()==this.totalRows && this.foundRowCount) return;
      } else if (offset < lastOffset) {
        if (offset > this.startPos+this.nearLimit) return;
        if (this.startPos==0) return;
      } else return;
    }
    if (offset >= this.totalRows && this.foundRowCount) return;

    this.processingRequest=true;
    Rico.log("AjaxSQL fetch: processing offset="+offset);
    var bufferStartPos = this.getFetchOffset(offset);
    var fetchSize = this.getFetchSize(bufferStartPos);
    var partialLoaded = false;

    this.liveGrid.showMsg(this.options.waitMsg);
    this.timeoutHandler = Rico.runLater(this.options.bufferTimeout, this, 'handleTimedOut');
    this.ajaxOptions.parameters = this.formQueryHashSQL(bufferStartPos,fetchSize);
    this.requestCount++;
    Rico.log('sending req #'+this.requestCount);
    if (typeof this.dataSource=='string') {
      this.ajaxOptions.onComplete = Rico.bind(this,'ajaxUpdate',bufferStartPos);
      new Rico.ajaxRequest(this.dataSource, this.ajaxOptions);
    } else {
      this.ajaxOptions.onComplete = Rico.bind(this,'jsUpdate',bufferStartPos);
      this.dataSource(this.ajaxOptions);
    }
  },

  formQueryHashSQL: function(startPos,fetchSize) {
    var queryHash=this.formQueryHashXML(startPos,fetchSize);

    // sort
    Rico.extend(queryHash,this.sortParm);

    // filters
    for (var n=0; n<this.liveGrid.columns.length; n++) {
      var c=this.liveGrid.columns[n];
      if (c.filterType == Rico.TableColumn.UNFILTERED) continue;
      var colnum=typeof(c.format.filterCol)=='number' ? c.format.filterCol : c.index;
      queryHash['f['+colnum+'][op]']=c.filterOp;
      queryHash['f['+colnum+'][len]']=c.filterValues.length;
      for (var i=0; i<c.filterValues.length; i++) {
        var fval=c.filterValues[i];
        if (c.filterOp=='LIKE' && fval.indexOf('*')==-1) fval='*'+fval+'*';
        queryHash['f['+colnum+']['+i+']']=fval;
      }
    }
    return queryHash;
  },

  getFetchSize: function(adjustedOffset) {
    var adjustedSize = 0;
    if (adjustedOffset >= this.startPos) { //appending
      var endFetchOffset = this.maxFetchSize + adjustedOffset;
      adjustedSize = endFetchOffset - adjustedOffset;
      if(adjustedOffset == 0 && adjustedSize < this.maxFetchSize)
        adjustedSize = this.maxFetchSize;
      Rico.log("getFetchSize/append, adjustedSize="+adjustedSize+" adjustedOffset="+adjustedOffset+' endFetchOffset='+endFetchOffset);
    } else { //prepending
      adjustedSize = Math.min(this.startPos - adjustedOffset,this.maxFetchSize);
    }
    return adjustedSize;
  },

  getFetchOffset: function(offset) {
    var adjustedOffset = offset;
    if (offset > this.startPos)
      adjustedOffset = Math.max(offset, this.endPos());  //appending
    else if (offset + this.maxFetchSize >= this.startPos)
      adjustedOffset = Math.max(this.startPos - this.maxFetchSize, 0);  //prepending
    return adjustedOffset;
  },

  updateBuffer: function(start, newRows, newAttr) {
    Rico.log("updateBuffer: start="+start+", # of rows="+this.rcvdRows);
    if (this.rows.length == 0) { // initial load
      this.rows = newRows;
      this.attr = newAttr;
      this.startPos = start;
    } else if (start > this.startPos) { //appending
      if (this.startPos + this.rows.length < start) {
        this.rows =  newRows;
        this.attr = newAttr;
        this.startPos = start;
      } else {
        this.rows = this.rows.concat( newRows.slice(0, newRows.length));
        if (this.attr) this.attr = this.attr.concat( newAttr.slice(0, newAttr.length));
        if (this.rows.length > this.maxBufferSize) {
          var fullSize = this.rows.length;
          this.rows = this.rows.slice(this.rows.length - this.maxBufferSize, this.rows.length);
          if (this.attr) this.attr = this.attr.slice(this.attr.length - this.maxBufferSize, this.attr.length);
          this.startPos = this.startPos +  (fullSize - this.rows.length);
        }
      }
    } else { //prepending
      if (start + newRows.length < this.startPos) {
        this.rows =  newRows;
      } else {
        this.rows = newRows.slice(0, this.startPos).concat(this.rows);
        if (this.maxBufferSize && this.rows.length > this.maxBufferSize)
          this.rows = this.rows.slice(0, this.maxBufferSize);
      }
      this.startPos =  start;
    }
    this.size = this.rows.length;
  },

  sortBuffer: function(colnum) {
    this.sortParm={};
    var col=this.liveGrid.columns[colnum];
// Added by Asbru Ltd. - pass header column id attribute value as the sort column parameter
    if (this.options.sortParmFmt == 'headerId') {
      this.sortParm['sort_col'] = col.hdrCell.id ? col.hdrCell.id : colnum;
      this.sortParm['sort_dir'] = col.getSortDirection();
    } else 
    if (this.options.sortParmFmt) {
      this.sortParm['sort_col']=col[this.options.sortParmFmt];
      this.sortParm['sort_dir']=col.getSortDirection();
    } else {
      this.sortParm['s'+colnum]=col.getSortDirection();
    }
    this.clear();
  },

  exportAllRows: function(populate,finish) {
    this.exportPopulate=populate;
    this.exportFinish=finish;
    this.sendExportRequest(0);
  },

/**
 * Send request for print window data
 */
  sendExportRequest: function(offset) {
    this.timeoutHandler = Rico.runLater(this.options.bufferTimeout,this,'exportTimedOut');
    this.ajaxOptions.parameters = this.formQueryHashSQL(offset,200);
    this.requestCount++;
    Rico.log('sending export req #'+this.requestCount);
    if (typeof this.dataSource=='string') {
      this.ajaxOptions.onComplete = Rico.bind(this,'ajaxExportAppend',offset);
      new Rico.ajaxRequest(this.dataSource, this.ajaxOptions);
    } else {
      this.ajaxOptions.onComplete = Rico.bind(this,'jsExportAppend',offset);
      this.dataSource(this.ajaxOptions);
    }
  },

  exportTimedOut: function() {
    Rico.log("Print Request Timed Out");
    this.liveGrid.showMsg(Rico.getPhraseById("requestTimedOut"));
    this.exportFinish();
  },

  jsExportAppend: function(startPos, newRows, newAttr, totalRows, errMsg) {
    this.clearTimer();
    Rico.log("jsExportAppend: "+arguments.length);
    if (errMsg) {
      Rico.log("jsExportAppend: received error="+errMsg);
      this.liveGrid.showMsg(Rico.getPhraseById("requestError",errMsg));
      return;
    }
    this.exportPopulate(newRows,startPos);
    if (newRows.length==0)
      this.exportFinish();
    else
      this.sendExportRequest(startPos+newRows.length);
  },

  ajaxExportAppend: function(startPos,request) {
    this.clearTimer();
    Rico.log("ajaxExportAppend");
    var response = request.responseXML.getElementsByTagName("ajax-response");
    if (response == null || response.length != 1) return;
    var rowsElement = response[0].getElementsByTagName('rows')[0];
    var rows=this.dom2jstable(rowsElement);
    this.exportPopulate(rows,startPos);
    if (rows.length==0)
      this.exportFinish();
    else
      this.sendExportRequest(startPos+rows.length);
  }

};

Rico.includeLoaded('ricoLiveGridAjax.js');
