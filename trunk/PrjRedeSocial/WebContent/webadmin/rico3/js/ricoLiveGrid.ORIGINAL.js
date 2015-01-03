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

if(typeof Rico=='undefined') throw("LiveGrid requires the Rico JavaScript framework");
if(typeof Rico.TableColumn=='undefined') throw("LiveGrid requires ricoGridCommon.js");


/** @namespace */
Rico.Buffer = {};

Rico.Buffer.Base = function(dataTable, options) {
  this.initialize(dataTable, options);
}
/** @lends Rico.Buffer.Base# */
Rico.Buffer.Base.prototype = {
/**
 * @class Defines the static buffer class (no AJAX).
 * Loads buffer with data that already exists in the document as an HTML table or passed via javascript.
 * Also serves as a base class for AJAX-enabled buffers.
 * @constructs
 */
  initialize: function(dataTable, options) {
    this.clear();
    this.updateInProgress = false;
    this.lastOffset = 0;
    this.rcvdRowCount = false;  // true if an eof element was included in the last xml response
    this.foundRowCount = false; // true if an xml response is ever received with eof true
    this.totalRows = 0;
    this.rowcntContent = "";
    this.rcvdOffset = -1;
    this.options = {
      fixedHdrRows     : 0,
      canFilter        : true,  // does buffer object support filtering?
      isEncoded        : true,  // is the data received via ajax html encoded?
      acceptAttr       : []     // attributes that can be copied from original/ajax data (e.g. className, style, id)
    };
    Rico.extend(this.options, options || {});
    if (dataTable) {
      this.loadRowsFromTable(dataTable,this.options.fixedHdrRows);
    } else {
      this.clear();
    }
  },

  registerGrid: function(liveGrid) {
    this.liveGrid = liveGrid;
  },

  setTotalRows: function( newTotalRows ) {
    if (typeof(newTotalRows)!='number') newTotalRows=this.size;
    if (this.totalRows == newTotalRows) return;
    this.totalRows = newTotalRows;
    if (this.liveGrid) {
      Rico.log("setTotalRows, newTotalRows="+newTotalRows);
      if (this.liveGrid.sizeTo=='data') this.liveGrid.resizeWindow();
      this.liveGrid.updateHeightDiv();
    }
  },

  loadRowsFromTable: function(tableElement,firstRow) {
    var newRows = new Array();
    var trs = tableElement.getElementsByTagName("tr");
    for ( var i=firstRow || 0; i < trs.length; i++ ) {
      var row = new Array();
      var cells = trs[i].getElementsByTagName("td");
      for ( var j=0; j < cells.length ; j++ )
        row[j]=cells[j].innerHTML;
      newRows.push( row );
    }
    this.loadRows(newRows);
  },

  loadRowsFromArray: function(array2D) {
    for ( var i=0; i < array2D.length; i++ ) {
      for ( var j=0; j < array2D[i].length ; j++ ) {
        array2D[i][j]=array2D[i][j].toString();
      }
    }
    this.loadRows(array2D);
  },

  loadRows: function(jstable) {
    this.baseRows = jstable;
    this.startPos = 0;
    this.size = this.baseRows.length;
  },

  dom2jstable: function(rowsElement) {
    Rico.log('dom2jstable: encoded='+this.options.isEncoded);
    var newRows = new Array();
    var trs = rowsElement.getElementsByTagName("tr");
    for ( var i=0; i < trs.length; i++ ) {
      var row = new Array();
      var cells = trs[i].getElementsByTagName("td");
      for ( var j=0; j < cells.length ; j++ )
        row[j]=Rico.getContentAsString(cells[j],this.options.isEncoded);
      newRows.push( row );
    }
    return newRows;
  },

  dom2jstableAttr: function(rowsElement,firstRow) {
    var acceptAttr=this.options.acceptAttr;
    Rico.log("dom2jstableAttr start, # attr="+acceptAttr.length);
    var newRows = new Array();
    var trs = rowsElement.getElementsByTagName("tr");
    for ( var i=firstRow || 0; i < trs.length; i++ ) {
      var row = new Array();
      var cells = trs[i].getElementsByTagName("td");
      for ( var j=0; j < cells.length ; j++ ) {
        row[j]={};
        for (var k=0; k<acceptAttr.length; k++)
          row[j]['_'+acceptAttr[k]]=cells[j].getAttribute(acceptAttr[k]);
        if (Rico.isIE) row[j]._class=cells[j].getAttribute('className');
      }
      newRows.push( row );
    }
    Rico.log("dom2jstableAttr end");
    return newRows;
  },

  _blankRow: function() {
    var newRow=[];
    for (var i=0; i<this.liveGrid.columns.length; i++) {
      newRow[i]='';
    }
    return newRow;
  },

  deleteRows: function(rowIndex,cnt) {
    this.baseRows.splice(rowIndex,typeof(cnt)=='number' ? cnt : 1);
    this.liveGrid.isPartialBlank=true;
    this.size=this.baseRows.length;
  },

  insertRow: function(beforeRowIndex) {
    var r=this._blankRow();
    this.baseRows.splice(beforeRowIndex,0,r);
    this.size=this.baseRows.length;
    this.liveGrid.isPartialBlank=true;
    if (this.startPos < 0) this.startPos=0;
    return r;
  },

  appendRows: function(cnt) {
    var newRows=[];
    for (var i=0; i<cnt; i++) {
      var r=this._blankRow();
      this.baseRows.push(r);
      newRows.push(r);
    }
    this.size=this.baseRows.length;
    this.liveGrid.isPartialBlank=true;
    if (this.startPos < 0) this.startPos=0;
    return newRows;
  },
  
  sortFunc: function(coltype) {
    switch (coltype) {
      case 'number': return Rico.bind(this,'_sortNumeric');
      case 'control':return Rico.bind(this,'_sortControl');
      default:       return Rico.bind(this,'_sortAlpha');
    }
  },

  sortBuffer: function(colnum) {
    if (!this.baseRows) {
      this.delayedSortCol=colnum;
      return;
    }
    this.liveGrid.showMsg(Rico.getPhraseById("sorting"));
    this.sortColumn=colnum;
    var col=this.liveGrid.columns[colnum];
    this.getValFunc=col._sortfunc;
    this.baseRows.sort(this.sortFunc(col.format.type));
    if (col.getSortDirection()=='DESC') this.baseRows.reverse();
  },
  
  _sortAlpha: function(a,b) {
    var aa = this.sortColumn<a.length ? Rico.getInnerText(a[this.sortColumn]) : '';
    var bb = this.sortColumn<b.length ? Rico.getInnerText(b[this.sortColumn]) : '';
    if (aa==bb) return 0;
    if (aa<bb) return -1;
    return 1;
  },

  _sortNumeric: function(a,b) {
    var aa = this.sortColumn<a.length ? this.nan2zero(Rico.getInnerText(a[this.sortColumn])) : 0;
    var bb = this.sortColumn<b.length ? this.nan2zero(Rico.getInnerText(b[this.sortColumn])) : 0;
    return aa-bb;
  },

  nan2zero: function(n) {
    if (typeof(n)=='string') n=parseFloat(n);
    return isNaN(n) || typeof(n)=='undefined' ? 0 : n;
  },
  
  _sortControl: function(a,b) {
    var aa = this.sortColumn<a.length ? Rico.getInnerText(a[this.sortColumn]) : '';
    var bb = this.sortColumn<b.length ? Rico.getInnerText(b[this.sortColumn]) : '';
    if (this.getValFunc) {
      aa=this.getValFunc(aa);
      bb=this.getValFunc(bb);
    }
    if (aa==bb) return 0;
    if (aa<bb) return -1;
    return 1;
  },

  clear: function() {
    this.baseRows = [];
    this.rows = [];
    this.startPos = -1;
    this.size = 0;
    this.windowPos = 0;
  },

  isInRange: function(position) {
    var lastRow=Math.min(this.totalRows, position + this.liveGrid.pageSize);
    return (position >= this.startPos) && (lastRow <= this.endPos()); // && (this.size != 0);
  },

  endPos: function() {
    return this.startPos + this.rows.length;
  },

  fetch: function(offset) {
    Rico.log('fetch '+this.liveGrid.tableId+': offset='+offset);
    this.applyFilters();
    this.setTotalRows();
    this.rcvdRowCount = true;
    this.foundRowCount = true;
    if (offset < 0) offset=0;
    this.liveGrid.refreshContents(offset);
    return;
  },

  exportAllRows: function(populate,finish) {
    populate(this.getRows(0,this.totalRows));
    finish();
  },
  
/**
 * @return a 2D array of buffer data representing the rows that are currently visible on the grid
 */
  visibleRows: function() {
    return this.rows.slice(this.windowStart,this.windowEnd);
  },

  setWindow: function(start, count) {
    this.windowStart = start - this.startPos;                       // position in the buffer of first visible row
    this.windowEnd = Math.min(this.windowStart + count,this.size);  // position in the buffer of last visible row containing data+1
    this.windowPos = start;                                         // position in the dataset of first visible row
  },

/**
 * @return true if bufRow is currently visible in the grid
 */
  isVisible: function(bufRow) {
    return bufRow < this.rows.length && bufRow >= this.windowStart && bufRow < this.windowEnd;
  },
  
/**
 * takes a window row index and returns the corresponding buffer row index
 */
  bufferRow: function(windowRow) {
    return this.windowStart+windowRow;
  },

/**
 * @return buffer cell at the specified visible row/col index
 */
  getWindowCell: function(windowRow,col) {
    var bufrow=this.bufferRow(windowRow);
    return this.isVisible(bufrow) && col < this.rows[bufrow].length ? this.rows[bufrow][col] : null;
  },

  getWindowAttr: function(windowRow,col) {
    var bufrow=this.bufferRow(windowRow);
    return this.attr && this.isVisible(bufrow) && col < this.attr[bufrow].length ? this.attr[bufrow][col] : null;
  },

  getWindowValue: function(windowRow,col) {
    return this.getWindowCell(windowRow,col);
  },

  setWindowValue: function(windowRow,col,newval) {
    var bufrow=this.bufferRow(windowRow);
    if (bufrow >= this.windowEnd) return false;
    return this.setValue(bufrow,col,newval);
  },

  getCell: function(bufRow,col) {
    return bufRow < this.size ? this.rows[bufRow][col] : null;
  },

  getValue: function(bufRow,col) {
    return this.getCell(bufRow,col);
  },

  setValue: function(bufRow,col,newval,newstyle) {
    if (bufRow>=this.size) return false;
    if (!this.rows[bufRow][col]) this.rows[bufRow][col]={};
    this.rows[bufRow][col]=newval;
    if (typeof newstyle=='string') this.rows[bufRow][col]._style=newstyle;
    this.rows[bufRow][col].modified=true;
    return true;
  },

  getRows: function(start, count) {
    var begPos = start - this.startPos;
    var endPos = Math.min(begPos + count,this.size);
    var results = new Array();
    for ( var i=begPos; i < endPos; i++ ) {
      results.push(this.rows[i]);
    }
    return results;
  },

  applyFilters: function() {
    var newRows=[],re=[];
    var r,c,n,i,showRow,filtercnt;
    var cols=this.liveGrid.columns;
    for (n=0,filtercnt=0; n<cols.length; n++) {
      c=cols[n];
      if (c.filterType == Rico.TableColumn.UNFILTERED) continue;
      filtercnt++;
      if (c.filterOp=='LIKE') re[n]=new RegExp(c.filterValues[0],'i');
    }
    Rico.log('applyFilters: # of filters='+filtercnt);
    if (filtercnt==0) {
      this.rows = this.baseRows;
    } else {
      for (r=0; r<this.baseRows.length; r++) {
        showRow=true;
        for (n=0; n<cols.length && showRow; n++) {
          c=cols[n];
          if (c.filterType == Rico.TableColumn.UNFILTERED) continue;
          switch (c.filterOp) {
            case 'LIKE':
              showRow=re[n].test(this.baseRows[r][n]);
              break;
            case 'EQ':
              showRow=this.baseRows[r][n]==c.filterValues[0];
              break;
            case 'NE':
              for (i=0; i<c.filterValues.length && showRow; i++)
                showRow=this.baseRows[r][n]!=c.filterValues[i];
              break;
            case 'LE':
              if (c.format.type=='number')
                showRow=this.nan2zero(this.baseRows[r][n])<=this.nan2zero(c.filterValues[0]);
              else
                showRow=this.baseRows[r][n]<=c.filterValues[0];
              break;
            case 'GE':
              if (c.format.type=='number')
                showRow=this.nan2zero(this.baseRows[r][n])>=this.nan2zero(c.filterValues[0]);
              else
                showRow=this.baseRows[r][n]>=c.filterValues[0];
              break;
            case 'NULL':
              showRow=this.baseRows[r][n]=='';
              break;
            case 'NOTNULL':
              showRow=this.baseRows[r][n]!='';
              break;
          }
        }
        if (showRow) newRows.push(this.baseRows[r]);
      }
      this.rows = newRows;
    }
    this.rowcntContent = this.size = this.rows.length;
  }

}


// Rico.LiveGrid -----------------------------------------------------

Rico.LiveGrid = function(tableId, buffer, options) {
  this.initialize(tableId, buffer, options);
}

/** 
 * @lends Rico.LiveGrid#
 * @property tableId id string for this grid
 * @property options the options object passed to the constructor extended with defaults
 * @property buffer the buffer object containing the data for this grid
 * @property columns array of {@link Rico.TableColumn} objects
 */
Rico.LiveGrid.prototype = {
/**
 * @class Buffered LiveGrid component
 * @extends Rico.GridCommon
 * @constructs
 */
  initialize: function( tableId, buffer, options ) {
    Rico.extend(this, Rico.GridCommon);
    Rico.extend(this, Rico.LiveGridMethods);
    this.baseInit();
    this.tableId = tableId;
    this.buffer = buffer;
    Rico.setDebugArea(tableId+"_debugmsgs");    // if used, this should be a textarea

    Rico.extend(this.options, {
      visibleRows      : -1,    // -1 or 'window'=size grid to client window; -2 or 'data'=size grid to min(window,data); -3 or 'body'=size so body does not have a scrollbar; -4 or 'parent'=size to parent element (e.g. if grid is inside a div)
      frozenColumns    : 0,
      offset           : 0,     // first row to be displayed
      prefetchBuffer   : true,  // load table on page load?
      minPageRows      : 2,
      maxPageRows      : 50,
      canSortDefault   : true,  // can be overridden in the column specs
      canFilterDefault : buffer.options.canFilter, // can be overridden in the column specs
      canHideDefault   : true,  // can be overridden in the column specs

      // highlight & selection parameters
      highlightElem    : 'none',// what gets highlighted/selected (cursorRow, cursorCell, menuRow, menuCell, selection, or none)
      highlightSection : 3,     // which section gets highlighted (frozen=1, scrolling=2, all=3, none=0)
      highlightMethod  : 'class', // outline, class, both (outline is less CPU intensive on the client)
      highlightClass   : Rico.theme.gridHighlightClass || 'ricoLG_selection',

      // export/print parameters
      maxPrint         : 1000,  // max # of rows that can be printed/exported, 0=disable print/export feature

      // heading parameters
      headingSort      : 'link', // link: make headings a link that will sort column, hover: make headings a hoverset, none: events on headings are disabled
      hdrIconsFirst    : true,   // true: put sort & filter icons before header text, false: after
      filterImg        : 'filtercol.gif'
    });
    // other options:
    //   sortCol: initial sort column

    this.options.sortHandler = Rico.bind(this,'sortHandler');
    this.options.filterHandler = Rico.bind(this,'filterHandler');
    this.options.onRefreshComplete = Rico.bind(this,'bookmarkHandler');
    this.options.rowOverHandler = Rico.eventHandle(this,'rowMouseOver');
    this.options.mouseDownHandler = Rico.eventHandle(this,'selectMouseDown');
    this.options.mouseOverHandler = Rico.eventHandle(this,'selectMouseOver');
    this.options.mouseUpHandler  = Rico.eventHandle(this,'selectMouseUp');
    Rico.extend(this.options, options || {});

    switch (typeof this.options.visibleRows) {
      case 'string':
        this.sizeTo=this.options.visibleRows;
        switch (this.options.visibleRows) {
          case 'data':   this.options.visibleRows=-2; break;
          case 'body':   this.options.visibleRows=-3; break;
          case 'parent': this.options.visibleRows=-4; break;
          default:       this.options.visibleRows=-1; break;
        }
        break;
      case 'number':
        switch (this.options.visibleRows) {
          case -1: this.sizeTo='window'; break;
          case -2: this.sizeTo='data'; break;
          case -3: this.sizeTo='body'; break;
          case -4: this.sizeTo='parent'; break;
          default: this.sizeTo='fixed'; break;
        }
        break;
      default:
        this.sizeTo='window';
        this.options.visibleRows=-1;
        break;
    }
    this.highlightEnabled=this.options.highlightSection>0;
    this.pageSize=0;
    this.createTables();
    if (this.headerColCnt==0) {
      alert('ERROR: no columns found in "'+this.tableId+'"');
      return;
    }
    this.createColumnArray('TableColumn');
    if (this.options.headingSort=='hover')
      this.createHoverSet();

    this.bookmark=document.getElementById(this.tableId+"_bookmark");
    this.sizeDivs();
    var filterUIrow=this.buffer.options.canFilter ? this.options.FilterLocation : false;
    if (typeof(filterUIrow)=='number' && filterUIrow<0)
      filterUIrow=this.addHeadingRow();
    this.createDataCells(this.options.visibleRows);
    if (this.pageSize == 0) return;
    this.buffer.registerGrid(this);
    if (this.buffer.setBufferSize) this.buffer.setBufferSize(this.pageSize);
    this.scrollTimeout = null;
    this.lastScrollPos = 0;
    this.attachMenuEvents();

    // preload the images...
    new Image().src = Rico.imgDir+this.options.filterImg;
    Rico.log("images preloaded");

    this.setSortUI( this.options.sortCol, this.options.sortDir );
    this.setImages();
    if (this.listInvisible().length==this.columns.length)
      this.columns[0].showColumn();
    this.sizeDivs();
    this.scrollDiv.style.display="";
    if (this.buffer.totalRows>0)
      this.updateHeightDiv();
    if (this.options.prefetchBuffer) {
      if (this.bookmark) this.bookmark.innerHTML = Rico.getPhraseById('bookmarkLoading');
      if (this.options.canFilterDefault && this.options.getQueryParms)
        this.checkForFilterParms();
      this.buffer.fetch(this.options.offset);
    }
    if (typeof(filterUIrow)=='number')
      this.createFilters(filterUIrow);
    this.scrollEventFunc=Rico.eventHandle(this,'handleScroll');
    this.wheelEventFunc=Rico.eventHandle(this,'handleWheel');
    this.wheelEvent=(Rico.isIE || Rico.isOpera || Rico.isWebKit) ? 'mousewheel' : 'DOMMouseScroll';
    if (this.options.offset && this.options.offset < this.buffer.totalRows)
      Rico.runLater(50,this,'scrollToRow',this.options.offset);  // Safari requires a delay
    this.pluginScroll();
    this.setHorizontalScroll();
    Rico.log("setHorizontalScroll done");
    if (this.options.windowResize)
      Rico.runLater(100,this,'pluginWindowResize');
    Rico.log("initialize complete for "+this.tableId);
  }
};


Rico.LiveGridMethods = {
/** @lends Rico.LiveGrid# */

  createHoverSet: function() {
    var hdrs=[];
    for( var c=0; c < this.headerColCnt; c++ ) {
      if (this.columns[c].sortable) {
        hdrs.push(this.columns[c].hdrCellDiv);
      }
    }
    this.hoverSet = new Rico.HoverSet(hdrs);
  },

  checkForFilterParms: function() {
    var s=window.location.search;
    if (s.charAt(0)=='?') s=s.substring(1);
    var pairs = s.split('&');
    for (var i=0; i<pairs.length; i++) {
      if (pairs[i].match(/^f\[\d+\]/)) {
        this.buffer.options.requestParameters.push(pairs[i]);
      }
    }
  },

/**
 * set filter on a detail grid that is in a master-detail relationship
 */
  setDetailFilter: function(colNumber,filterValue) {
    var c=this.columns[colNumber];
    c.format.ColData=filterValue;
    c.setSystemFilter('EQ',filterValue);
  },

/**
 * Create one table for frozen columns and one for scrolling columns.
 * Also create div's to contain them.
 * @returns true on success
 */
  createTables: function() {
    var insertloc,hdrSrc,i;
    var table = document.getElementById(this.tableId) || document.getElementById(this.tableId+'_outerDiv');
    if (!table) return false;
    if (table.tagName.toLowerCase()=='table') {
      var theads=table.getElementsByTagName("thead");
      if (theads.length == 1) {
        Rico.log("createTables: using thead section, id="+this.tableId);
        if (this.options.PanelNamesOnTabHdr && this.options.panels) {
          var r=theads[0].insertRow(0);
          this.insertPanelNames(r, 0, this.options.frozenColumns, 'ricoFrozen');
          this.insertPanelNames(r, this.options.frozenColumns, this.options.columnSpecs.length);
        }
        hdrSrc=theads[0].rows;
      } else {
        Rico.log("createTables: using tbody section, id="+this.tableId);
        hdrSrc=new Array(table.rows[0]);
      }
      insertloc=table;
    } else if (this.options.columnSpecs.length > 0) {
      if (!table.id.match(/_outerDiv$/)) insertloc=table;
      Rico.log("createTables: inserting at "+table.tagName+", id="+this.tableId);
    } else {
      alert("ERROR!\n\nUnable to initialize '"+this.tableId+"'\n\nLiveGrid terminated");
      return false;
    }

    this.createDivs();
    this.scrollTabs = this.createDiv("scrollTabs",this.innerDiv);
    this.shadowDiv  = this.createDiv("shadow",this.scrollDiv);
    this.shadowDiv.style.direction='ltr';  // avoid FF bug
    this.scrollDiv.style.display="none";
    this.scrollDiv.scrollTop=0;
    if (this.options.highlightMethod!='class') {
      this.highlightDiv=[];
      switch (this.options.highlightElem) {
        case 'menuRow':
        case 'cursorRow':
          this.highlightDiv[0] = this.createDiv("highlight",this.outerDiv);
          this.highlightDiv[0].style.display="none";
          break;
        case 'menuCell':
        case 'cursorCell':
          for (i=0; i<2; i++) {
            this.highlightDiv[i] = this.createDiv("highlight",i==0 ? this.frozenTabs : this.scrollTabs);
            this.highlightDiv[i].style.display="none";
            this.highlightDiv[i].id+=i;
          }
          break;
        case 'selection':
          // create one div for each side of the rectangle
          var parentDiv=this.options.highlightSection==1 ? this.frozenTabs : this.scrollTabs;
          for (i=0; i<4; i++) {
            this.highlightDiv[i] = this.createDiv("highlight",parentDiv);
            this.highlightDiv[i].style.display="none";
            this.highlightDiv[i].style.overflow="hidden";
            this.highlightDiv[i].id+=i;
            this.highlightDiv[i].style[i % 2==0 ? 'height' : 'width']="0px";
          }
          break;
      }
    }

    // create new tables
    for (i=0; i<2; i++) {
      this.tabs[i] = document.createElement("table");
      this.tabs[i].className = 'ricoLG_table';
      this.tabs[i].border=0;
      this.tabs[i].cellPadding=0;
      this.tabs[i].cellSpacing=0;
      this.tabs[i].id = this.tableId+"_tab"+i;
      this.thead[i]=this.tabs[i].createTHead();
      this.thead[i].className='ricoLG_top';
      if (Rico.theme.gridheader) Rico.addClass(this.thead[i],Rico.theme.gridheader);
      if (this.tabs[i].tBodies.length==0)
        this.tbody[i]=this.tabs[i].appendChild(document.createElement("tbody"));
      else
        this.tbody[i]=this.tabs[i].tBodies[0];
      this.tbody[i].className='ricoLG_bottom';
      if (Rico.theme.gridcontent) Rico.addClass(this.tbody[i],Rico.theme.gridcontent);
      this.tbody[i].insertRow(-1);
    }
    this.frozenTabs.appendChild(this.tabs[0]);
    this.scrollTabs.appendChild(this.tabs[1]);
    if (insertloc) insertloc.parentNode.insertBefore(this.outerDiv,insertloc);
    if (hdrSrc) {
      this.headerColCnt = this.getColumnInfo(hdrSrc);
      this.loadHdrSrc(hdrSrc);
    } else {
      this.createHdr(0,0,this.options.frozenColumns);
      this.createHdr(1,this.options.frozenColumns,this.options.columnSpecs.length);
      if (this.options.PanelNamesOnTabHdr && this.options.panels) {
        this.insertPanelNames(this.thead[0].insertRow(0), 0, this.options.frozenColumns);
        this.insertPanelNames(this.thead[1].insertRow(0), this.options.frozenColumns, this.options.columnSpecs.length);
      }
      for (i=0; i<2; i++)
        this.headerColCnt = this.getColumnInfo(this.thead[i].rows);
    }
    for( var c=0; c < this.headerColCnt; c++ )
      this.tbody[c<this.options.frozenColumns ? 0 : 1].rows[0].insertCell(-1);
    if (insertloc) table.parentNode.removeChild(table);
    Rico.log('createTables end');
    return true;
  },

  createDataCells: function(visibleRows) {
    if (visibleRows < 0) {
      for (var i=0; i<this.options.minPageRows; i++)
        this.appendBlankRow();
      this.sizeDivs();
      this.autoAppendRows(this.remainingHt());
    } else {
      for( var r=0; r < visibleRows; r++ )
        this.appendBlankRow();
    }
    var s=this.options.highlightSection;
    if (s & 1) this.attachHighlightEvents(this.tbody[0]);
    if (s & 2) this.attachHighlightEvents(this.tbody[1]);
  },

/**
 * @param colnum column number
 * @return id string for a filter element
 */
  filterId: function(colnum) {
    return 'RicoFilter_'+this.tableId+'_'+colnum;
  },

/**
 * Create filter elements in heading
 * Reads this.columns[].filterUI to determine type of filter element for each column (t=text box, s=select list, c=custom)
 * @param r heading row where filter elements will be placed
 */
  createFilters: function(r) {
    for( var c=0; c < this.headerColCnt; c++ ) {
      var col=this.columns[c];
      var fmt=col.format;
      if (typeof fmt.filterUI!='string') continue;
      var cell=this.hdrCells[r][c].cell;
      var field,name=this.filterId(c);
      var divs=cell.getElementsByTagName('div');
      // copy text alignment from data cell
      var align=Rico.getStyle(this.cell(0,c),'textAlign');
      divs[1].style.textAlign=align;
      switch (fmt.filterUI.charAt(0)) {
        case 't':
          // text field
          field=Rico.createFormField(divs[1],'input','text',name,name);
          var size=fmt.filterUI.match(/\d+/);
          field.maxLength=fmt.Length || 50;
          field.size=size ? parseInt(size,10) : 10;
          divs[1].appendChild(Rico.clearButton(Rico.eventHandle(col,'filterClear')));
          if (col.filterType==Rico.TableColumn.USERFILTER && col.filterOp=='LIKE') {
            var v=col.filterValues[0];
            if (v.charAt(0)=='*') v=v.substr(1);
            if (v.slice(-1)=='*') v=v.slice(0,-1);
            field.value=v;
            col.lastKeyFilter=v;
          }
          Rico.eventBind(field,'keyup',Rico.eventHandle(col,'filterKeypress'),false);
          col.filterField=field;
          break;
        case 's':
          // drop-down select
          field=Rico.createFormField(divs[1],'select',null,name);
          Rico.addSelectOption(field,this.options.FilterAllToken,Rico.getPhraseById("filterAll"));
          col.filterField=field;
          var options={};
          Rico.extend(options, this.buffer.ajaxOptions);
          var colnum=typeof(fmt.filterCol)=='number' ? fmt.filterCol : c;
          options.parameters = {id: this.tableId, distinct:colnum};
          options.onComplete = Rico.bind(this,'filterValuesUpdate',c);
          new Rico.ajaxRequest(this.buffer.dataSource, options);
          break;
        case 'c':
          // custom
          if (typeof col._createFilters == 'function')
            col._createFilters(divs[1], name);
          break;
      }
    }
    this.initFilterImage(r);
  },

/**
 * update select list filter with values in AJAX response
 * @returns true on success
 */
  filterValuesUpdate: function(colnum,request) {
    var response = request.responseXML.getElementsByTagName("ajax-response");
    Rico.log("filterValuesUpdate: "+request.status);
    if (response == null || response.length != 1) return false;
    response=response[0];
    var error = response.getElementsByTagName('error');
    if (error.length > 0) {
      Rico.log("Data provider returned an error:\n"+Rico.getContentAsString(error[0],this.buffer.isEncoded));
      alert(Rico.getPhraseById("requestError",Rico.getContentAsString(error[0],this.buffer.isEncoded)));
      return false;
    }
    response=response.getElementsByTagName('response')[0];
    var rowsElement = response.getElementsByTagName('rows')[0];
    //var colnum = rowsElement.getAttribute("distinct");
    var col=this.columns[parseInt(colnum,10)];
    var rows = this.buffer.dom2jstable(rowsElement);
    var c0,c1,opt,v, field=document.getElementById(this.filterId(colnum));
    if (col.filterType==Rico.TableColumn.USERFILTER && col.filterOp=='EQ') v=col.filterValues[0];
    Rico.log('filterValuesUpdate: col='+colnum+' rows='+rows.length);
    for (var i=0; i<rows.length; i++) {
      if (rows[i].length>0) {
        c0=c1=rows[i][0];
        if (c0.match(/<span\s+class=(['"]?)ricolookup\1>(.*)<\/span>/i)) {
          c1=RegExp.leftContext;
        }
        opt=Rico.addSelectOption(field,c0,c1 || Rico.getPhraseById("filterBlank"));
        if (col.filterType==Rico.TableColumn.USERFILTER && c0==v) opt.selected=true;
      }
    }
    Rico.eventBind(field,'change',Rico.eventHandle(col,'filterChange'),false);
    return true;
  },

  unplugHighlightEvents: function() {
    var s=this.options.highlightSection;
    if (s & 1) this.detachHighlightEvents(this.tbody[0]);
    if (s & 2) this.detachHighlightEvents(this.tbody[1]);
  },

/**
 * place panel names on first row of grid header (used by LiveGridForms)
 */
  insertPanelNames: function(r,start,limit,cellClass) {
    Rico.log('insertPanelNames: start='+start+' limit='+limit);
    r.className='ricoLG_hdg';
    var lastIdx=-1, span, newCell=null, spanIdx=0;
    for( var c=start; c < limit; c++ ) {
      if (lastIdx == this.options.columnSpecs[c].panelIdx) {
        span++;
      } else {
        if (newCell) newCell.colSpan=span;
        newCell = r.insertCell(-1);
        if (cellClass) newCell.className=cellClass;
        span=1;
        lastIdx=this.options.columnSpecs[c].panelIdx;
        newCell.innerHTML=this.options.panels[lastIdx];
      }
    }
    if (newCell) newCell.colSpan=span;
  },

/**
 * create grid header for table i (if none was provided)
 */
  createHdr: function(i,start,limit) {
    Rico.log('createHdr: i='+i+' start='+start+' limit='+limit);
    var mainRow = this.thead[i].insertRow(-1);
    mainRow.id=this.tableId+'_tab'+i+'h_main';
    mainRow.className='ricoLG_hdg';
    for( var c=start; c < limit; c++ ) {
      var newCell = mainRow.insertCell(-1);
      newCell.innerHTML=this.options.columnSpecs[c].Hdg;
    }
  },

/**
 * move header cells in original table to grid
 */
  loadHdrSrc: function(hdrSrc) {
    var i,h,c,r,newrow,cells;
    Rico.log('loadHdrSrc start');
    for (i=0; i<2; i++) {
      for (r=0; r<hdrSrc.length; r++) {
        newrow = this.thead[i].insertRow(-1);
        newrow.className='ricoLG_hdg '+this.tableId+'_hdg'+r;
      }
    }
    if (hdrSrc.length==1) {
      cells=hdrSrc[0].cells;
      for (c=0; cells.length > 0; c++)
        this.thead[c<this.options.frozenColumns ? 0 : 1].rows[0].appendChild(cells[0]);
    } else {
      for (r=0; r<hdrSrc.length; r++) {
        cells=hdrSrc[r].cells;
        for (c=0,h=0; cells.length > 0; c++) {
          if (cells[0].className=='ricoFrozen') {
            if (r==this.headerRowIdx) this.options.frozenColumns=c+1;
          } else {
            h=1;
          }
          this.thead[h].rows[r].appendChild(cells[0]);
        }
      }
    }
    Rico.log('loadHdrSrc end');
  },

/**
 * Size div elements
 */
  sizeDivs: function() {
    Rico.log('sizeDivs: '+this.tableId);
    //this.cancelMenu();
    this.unhighlight();
    this.baseSizeDivs();
    var firstVisible=this.firstVisible();
    if (this.pageSize == 0 || firstVisible < 0) return;
    var totRowHt=this.columns[firstVisible].dataColDiv.offsetHeight;
    this.rowHeight = Math.round(totRowHt/this.pageSize);
    var scrHt=this.dataHt;
    if (this.scrWi>0 || Rico.isIE || Rico.isWebKit)
      scrHt+=this.options.scrollBarWidth;
    this.scrollDiv.style.height=scrHt+'px';
    this.innerDiv.style.width=(this.scrWi-this.options.scrollBarWidth+1)+'px';
    this.resizeDiv.style.height=this.frozenTabs.style.height=this.innerDiv.style.height=(this.hdrHt+this.dataHt+1)+'px';
    Rico.log('sizeDivs scrHt='+scrHt+' innerHt='+this.innerDiv.style.height+' rowHt='+this.rowHeight+' pageSize='+this.pageSize);
    var pad=(this.scrWi-this.scrTabWi < this.options.scrollBarWidth) ? 2 : 0;
    this.shadowDiv.style.width=(this.scrTabWi+pad)+'px';
    this.outerDiv.style.height=(this.hdrHt+scrHt)+'px';
    this.setHorizontalScroll();
  },

  setHorizontalScroll: function() {
    var scrleft=this.scrollDiv.scrollLeft;
    this.scrollTabs.style.left=(-scrleft)+'px';
  },

  remainingHt: function() {
    var tabHt;
    var winHt=Rico.windowHeight();
    var margin=Rico.isIE ? 15 : 10;
    // if there is a horizontal scrollbar take it into account
    if (!Rico.isIE && window.frameElement && window.frameElement.scrolling=='yes' && this.sizeTo!='parent') margin+=this.options.scrollBarWidth;
    switch (this.sizeTo) {
      case 'window':
      case 'data':
        var divTop=Rico.cumulativeOffset(this.outerDiv).top;
        tabHt=Math.max(this.tabs[0].offsetHeight,this.tabs[1].offsetHeight);
        Rico.log("remainingHt, winHt="+winHt+' tabHt='+tabHt+' gridY='+divTop);
        return winHt-divTop-tabHt-this.options.scrollBarWidth-margin;  // allow for scrollbar and some margin
      case 'parent':
        var offset=this.offsetFromParent(this.outerDiv);
        tabHt=Math.max(this.tabs[0].offsetHeight,this.tabs[1].offsetHeight);
        if (Rico.isIE) Rico.hide(this.outerDiv);
        var parentHt=this.outerDiv.parentNode.offsetHeight;
        if (Rico.isIE) Rico.show(this.outerDiv);
        Rico.log("remainingHt, parentHt="+parentHt+' gridY='+offset+' winHt='+winHt+' tabHt='+tabHt);
        return parentHt - tabHt - offset - this.options.scrollBarWidth;
      case 'body':
        //Rico.log("remainingHt, document.height="+document.height);
        //Rico.log("remainingHt, body.offsetHeight="+document.body.offsetHeight);
        //Rico.log("remainingHt, body.scrollHeight="+document.body.scrollHeight);
        //Rico.log("remainingHt, documentElement.scrollHeight="+document.documentElement.scrollHeight);
        var bodyHt=Rico.isIE ? document.body.scrollHeight : document.body.offsetHeight;
        var remHt=winHt-bodyHt-margin;
        if (!Rico.isWebKit) remHt-=this.options.scrollBarWidth;
        Rico.log("remainingHt, winHt="+winHt+' pageHt='+bodyHt+' remHt='+remHt);
        return remHt;
      default:
        tabHt=Math.max(this.tabs[0].offsetHeight,this.tabs[1].offsetHeight);
        Rico.log("remainingHt, winHt="+winHt+' tabHt='+tabHt);
        if (this.sizeTo.slice(-1)=='%') winHt*=parseFloat(this.sizeTo)/100.0;
        else if (this.sizeTo.slice(-2)=='px') winHt=parseInt(this.sizeTo,10);
        return winHt-tabHt-this.options.scrollBarWidth-margin;  // allow for scrollbar and some margin
    }
  },

  offsetFromParent: function(element) {
    var valueT = 0;
    var elParent=element.parentNode;
    do {
      //Rico.log("offsetFromParent: "+element.tagName+' id='+element.id+' otop='+element.offsetTop);
      valueT += element.offsetTop  || 0;
      element = element.offsetParent;
      if (!element || element==null) break;
      var p = Rico.getStyle(element, 'position');
      if (element.tagName=='BODY' || element.tagName=='HTML' || p=='absolute') return valueT-elParent.offsetTop;
    } while (element != elParent);
    return valueT;
  },

  adjustPageSize: function() {
    var remHt=this.remainingHt();
    Rico.log('adjustPageSize remHt='+remHt+' lastRow='+this.lastRowPos);
    if (remHt > this.rowHeight)
      this.autoAppendRows(remHt);
    else if (remHt < 0 || this.sizeTo=='data')
      this.autoRemoveRows(-remHt);
  },

  pluginWindowResize: function() {
    Rico.log("pluginWindowResize");
    this.resizeWindowHandler=Rico.eventHandle(this,'resizeWindow');
    Rico.eventBind(window, "resize", this.resizeWindowHandler, false);
  },

  unplugWindowResize: function() {
    if (!this.resizeWindowHandler) return;
    Rico.eventUnbind(window,"resize", this.resizeWindowHandler, false);
    this.resizeWindowHandler=null;
  },

  resizeWindow: function() {
    Rico.log('resizeWindow '+this.tableId+' lastRow='+this.lastRowPos);
    if (this.resizeState=='finish') {
      Rico.log('resizeWindow postponed');
      this.resizeState='resize';
      return;
    }
    if (!this.sizeTo || this.sizeTo=='fixed') {
      this.sizeDivs();
      return;
    }
    if (this.sizeTo=='parent' && Rico.getStyle(this.outerDiv.parentNode,'display') == 'none') return;
    var oldSize=this.pageSize;
    this.adjustPageSize();
    if (this.pageSize > oldSize && this.buffer.totalRows>0) {
      this.isPartialBlank=true;
      var adjStart=this.adjustRow(this.lastRowPos);
      this.buffer.fetch(adjStart);
    } else if (this.pageSize < oldSize) {
      if (this.options.onRefreshComplete) this.options.onRefreshComplete(this.contentStartPos,this.contentStartPos+this.pageSize-1);  // update bookmark
    }
    this.resizeState='finish';
    Rico.runLater(20,this,'finishResize');
    Rico.log('resizeWindow '+this.tableId+' complete. old size='+oldSize+' new size='+this.pageSize);
  },

  finishResize: function() {
    this.sizeDivs();
    this.updateHeightDiv();
    if (this.resizeState=='resize') {
      this.resizeWindow();
    } else {
      this.resizeState='';
    }
  },

  topOfLastPage: function() {
    return Math.max(this.buffer.totalRows-this.pageSize,0);
  },

  updateHeightDiv: function() {
    var notdisp=this.topOfLastPage();
    var ht = this.scrollDiv.clientHeight + this.rowHeight * notdisp;
    Rico.log("updateHeightDiv, ht="+ht+' scrollDiv.clientHeight='+this.scrollDiv.clientHeight+' rowsNotDisplayed='+notdisp);
    this.shadowDiv.style.height=ht+'px';
  },

  autoRemoveRows: function(overage) {
    if (!this.rowHeight) return;
    var removeCnt=Math.ceil(overage / this.rowHeight);
    if (this.sizeTo=='data')
      removeCnt=Math.max(removeCnt,this.pageSize-this.buffer.totalRows);
    Rico.log("autoRemoveRows overage="+overage+" removeCnt="+removeCnt);
    for (var i=0; i<removeCnt; i++)
      this.removeRow();
  },

  removeRow: function() {
    if (this.pageSize <= this.options.minPageRows) return;
    this.pageSize--;
    for( var c=0; c < this.headerColCnt; c++ ) {
      var cell=this.columns[c].cell(this.pageSize);
      this.columns[c].dataColDiv.removeChild(cell);
    }
  },

  autoAppendRows: function(overage) {
    if (!this.rowHeight) return;
    var addCnt=Math.floor(overage / this.rowHeight);
    Rico.log("autoAppendRows overage="+overage+" cnt="+addCnt+" rowHt="+this.rowHeight);
    for (var i=0; i<addCnt; i++) {
      if (this.sizeTo=='data' && this.pageSize>=this.buffer.totalRows) break;
      this.appendBlankRow();
    }
  },

/**
 * on older systems, this can be fairly slow
 */
  appendBlankRow: function() {
    if (this.pageSize >= this.options.maxPageRows) return;
    Rico.log("appendBlankRow #"+this.pageSize);
    var cls=this.defaultRowClass(this.pageSize);
    for( var c=0; c < this.headerColCnt; c++ ) {
      var newdiv = document.createElement("div");
      newdiv.className = 'ricoLG_cell '+cls;
      newdiv.id=this.tableId+'_'+this.pageSize+'_'+c;
      this.columns[c].dataColDiv.appendChild(newdiv);
      newdiv.innerHTML='&nbsp;';
      if (this.columns[c].format.canDrag && this.options.dndMgr)
        this.options.dndMgr.registerDraggable( new Rico.LiveGridDraggable(this, this.pageSize, c) );
      if (this.columns[c]._create)
        this.columns[c]._create(newdiv,this.pageSize);
    }
    this.pageSize++;
  },

  defaultRowClass: function(rownum) {
    var cls
    if (rownum % 2==0) {
      cls='ricoLG_evenRow';
      //if (Rico.theme.primary) cls+=' '+Rico.theme.primary;
    } else {
      cls='ricoLG_oddRow';
      //if (Rico.theme.secondary) cls+=' '+Rico.theme.secondary;
    }
    return cls;
  },

  handleMenuClick: function(e) {
    if (!this.menu) return;
    this.cancelMenu();
    this.unhighlight(); // in case highlighting was invoked externally
    var idx;
    var cell=Rico.eventElement(e);
    if (cell.className=='ricoLG_highlightDiv') {
      idx=this.highlightIdx;
    } else {
      cell=Rico.getParentByTagName(cell,'div','ricoLG_cell');
      if (!cell) return;
      idx=this.winCellIndex(cell);
      if ((this.options.highlightSection & (idx.tabIdx+1))==0) return;
    }
    this.highlight(idx);
    this.highlightEnabled=false;
    if (this.hideScroll) this.scrollDiv.style.overflow="hidden";
    this.menuIdx=idx;
    if (!this.menu.div) this.menu.createDiv();
    this.menu.liveGrid=this;
    if (this.menu.buildGridMenu) {
      var showMenu=this.menu.buildGridMenu(idx.row, idx.column, idx.tabIdx);
      if (!showMenu) return;
    }
    if (this.options.highlightElem=='selection' && !this.isSelected(idx.cell)) {
      this.selectCell(idx.cell);
    }
    this.menu.showmenu(e,Rico.bind(this,'closeMenu'));
  },

  closeMenu: function() {
    if (!this.menuIdx) return;
    if (this.hideScroll) this.scrollDiv.style.overflow="";
    this.unhighlight();
    this.highlightEnabled=true;
    this.menuIdx=null;
  },

/**
 * @return index of cell within the window
 */
  winCellIndex: function(cell) {
    var a=cell.id.split(/_/);
    var l=a.length;
    var r=parseInt(a[l-2],10);
    var c=parseInt(a[l-1],10);
    return {row:r, column:c, tabIdx:this.columns[c].tabIdx, cell:cell};
  },

/**
 * @return index of cell within the dataset
 */
  datasetIndex: function(cell) {
    var idx=this.winCellIndex(cell);
    idx.row+=this.buffer.windowPos;
    idx.onBlankRow=(idx.row >= this.buffer.endPos());
    return idx;
  },

  attachHighlightEvents: function(tBody) {
    switch (this.options.highlightElem) {
      case 'selection':
        Rico.eventBind(tBody,"mousedown", this.options.mouseDownHandler, false);
        /** @ignore */
        tBody.ondrag = function () { return false; };
        /** @ignore */
        tBody.onselectstart = function () { return false; };
        break;
      case 'cursorRow':
      case 'cursorCell':
        Rico.eventBind(tBody,"mouseover", this.options.rowOverHandler, false);
        break;
    }
  },

  detachHighlightEvents: function(tBody) {
    switch (this.options.highlightElem) {
      case 'selection':
        Rico.eventUnbind(tBody,"mousedown", this.options.mouseDownHandler, false);
        tBody.ondrag = null;
        tBody.onselectstart = null;
        break;
      case 'cursorRow':
      case 'cursorCell':
        Rico.eventUnbind(tBody,"mouseover", this.options.rowOverHandler, false);
        break;
    }
  },

/**
 * @return array of objects containing row/col indexes (index values are relative to the start of the window)
 */
  getVisibleSelection: function() {
    var cellList=[];
    if (this.SelectIdxStart && this.SelectIdxEnd) {
      var r1=Math.max(Math.min(this.SelectIdxEnd.row,this.SelectIdxStart.row)-this.buffer.startPos,this.buffer.windowStart);
      var r2=Math.min(Math.max(this.SelectIdxEnd.row,this.SelectIdxStart.row)-this.buffer.startPos,this.buffer.windowEnd-1);
      var c1=Math.min(this.SelectIdxEnd.column,this.SelectIdxStart.column);
      var c2=Math.max(this.SelectIdxEnd.column,this.SelectIdxStart.column);
      //Rico.log("getVisibleSelection "+r1+','+c1+' to '+r2+','+c2+' ('+this.SelectIdxStart.row+',startPos='+this.buffer.startPos+',windowPos='+this.buffer.windowPos+',windowEnd='+this.buffer.windowEnd+')');
      for (var r=r1; r<=r2; r++) {
        for (var c=c1; c<=c2; c++)
          cellList.push({row:r-this.buffer.windowStart,column:c});
      }
    }
    if (this.SelectCtrl) {
      for (var i=0; i<this.SelectCtrl.length; i++) {
        if (this.SelectCtrl[i].row>=this.buffer.windowStart && this.SelectCtrl[i].row<this.buffer.windowEnd)
          cellList.push({row:this.SelectCtrl[i].row-this.buffer.windowStart,column:this.SelectCtrl[i].column});
      }
    }
    return cellList;
  },

  updateSelectOutline: function() {
    if (!this.SelectIdxStart || !this.SelectIdxEnd) return;
    var r1=Math.max(Math.min(this.SelectIdxEnd.row,this.SelectIdxStart.row), this.buffer.windowStart);
    var r2=Math.min(Math.max(this.SelectIdxEnd.row,this.SelectIdxStart.row), this.buffer.windowEnd-1);
    if (r1 > r2) {
      this.HideSelection();
      return;
    }
    var c1=Math.min(this.SelectIdxEnd.column,this.SelectIdxStart.column);
    var c2=Math.max(this.SelectIdxEnd.column,this.SelectIdxStart.column);
    var top1=this.columns[c1].cell(r1-this.buffer.windowStart).offsetTop;
    var cell2=this.columns[c1].cell(r2-this.buffer.windowStart);
    var bottom2=cell2.offsetTop+cell2.offsetHeight;
    var left1=this.columns[c1].dataCell.offsetLeft;
    var left2=this.columns[c2].dataCell.offsetLeft;
    var right2=left2+this.columns[c2].dataCell.offsetWidth;
    //window.status='updateSelectOutline: '+r1+' '+r2+' top='+top1+' bot='+bottom2;
    this.highlightDiv[0].style.top=this.highlightDiv[3].style.top=this.highlightDiv[1].style.top=(this.hdrHt+top1-1) + 'px';
    this.highlightDiv[2].style.top=(this.hdrHt+bottom2-1)+'px';
    this.highlightDiv[3].style.left=(left1-2)+'px';
    this.highlightDiv[0].style.left=this.highlightDiv[2].style.left=(left1-1)+'px';
    this.highlightDiv[1].style.left=(right2-1)+'px';
    this.highlightDiv[0].style.width=this.highlightDiv[2].style.width=(right2-left1-1) + 'px';
    this.highlightDiv[1].style.height=this.highlightDiv[3].style.height=(bottom2-top1) + 'px';
    //this.highlightDiv[0].style.right=this.highlightDiv[2].style.right=this.highlightDiv[1].style.right=()+'px';
    //this.highlightDiv[2].style.bottom=this.highlightDiv[3].style.bottom=this.highlightDiv[1].style.bottom=(this.hdrHt+bottom2) + 'px';
    for (var i=0; i<4; i++)
      this.highlightDiv[i].style.display='';
  },

  HideSelection: function() {
    var i;
    if (this.options.highlightMethod!='class') {
      for (i=0; i<this.highlightDiv.length; i++)
        this.highlightDiv[i].style.display='none';
    }
    if (this.options.highlightMethod!='outline') {
      var cellList=this.getVisibleSelection();
      Rico.log("HideSelection "+cellList.length);
      for (i=0; i<cellList.length; i++)
        this.unhighlightCell(this.columns[cellList[i].column].cell(cellList[i].row));
    }
  },

  ShowSelection: function() {
    if (this.options.highlightMethod!='class')
      this.updateSelectOutline();
    if (this.options.highlightMethod!='outline') {
      var cellList=this.getVisibleSelection();
      for (var i=0; i<cellList.length; i++)
        this.highlightCell(this.columns[cellList[i].column].cell(cellList[i].row));
    }
  },

  ClearSelection: function() {
    Rico.log("ClearSelection");
    this.HideSelection();
    this.SelectIdxStart=null;
    this.SelectIdxEnd=null;
    this.SelectCtrl=[];
  },

  selectCell: function(cell) {
    this.ClearSelection();
    this.SelectIdxStart=this.SelectIdxEnd=this.datasetIndex(cell);
    this.ShowSelection();
  },

  AdjustSelection: function(cell) {
    var newIdx=this.datasetIndex(cell);
    if (this.SelectIdxStart.tabIdx != newIdx.tabIdx) return;
    this.HideSelection();
    this.SelectIdxEnd=newIdx;
    this.ShowSelection();
  },

  RefreshSelection: function() {
    var cellList=this.getVisibleSelection();
    for (var i=0; i<cellList.length; i++) {
      this.columns[cellList[i].column].displayValue(cellList[i].row);
    }
  },

  FillSelection: function(newVal,newStyle) {
    if (this.SelectIdxStart && this.SelectIdxEnd) {
      var r1=Math.min(this.SelectIdxEnd.row,this.SelectIdxStart.row);
      var r2=Math.max(this.SelectIdxEnd.row,this.SelectIdxStart.row);
      var c1=Math.min(this.SelectIdxEnd.column,this.SelectIdxStart.column);
      var c2=Math.max(this.SelectIdxEnd.column,this.SelectIdxStart.column);
      for (var r=r1; r<=r2; r++) {
        for (var c=c1; c<=c2; c++) {
          this.buffer.setValue(r,c,newVal,newStyle);
        }
      }
    }
    if (this.SelectCtrl) {
      for (var i=0; i<this.SelectCtrl.length; i++) {
        this.buffer.setValue(this.SelectCtrl[i].row,this.SelectCtrl[i].column,newVal,newStyle);
      }
    }
    this.RefreshSelection();
  },

/**
 * Process mouse down event
 * @param e event object
 */
  selectMouseDown: function(e) {
    if (this.highlightEnabled==false) return true;
    this.cancelMenu();
    var cell=Rico.eventElement(e);
    if (!Rico.eventLeftClick(e)) return true;
    cell=Rico.getParentByTagName(cell,'div','ricoLG_cell');
    if (!cell) return true;
    Rico.eventStop(e);
    var newIdx=this.datasetIndex(cell);
    if (newIdx.onBlankRow) return true;
    Rico.log("selectMouseDown @"+newIdx.row+','+newIdx.column);
    if (e.ctrlKey) {
      if (!this.SelectIdxStart || this.options.highlightMethod!='class') return true;
      if (!this.isSelected(cell)) {
        this.highlightCell(cell);
        this.SelectCtrl.push(this.datasetIndex(cell));
      } else {
        for (var i=0; i<this.SelectCtrl.length; i++) {
          if (this.SelectCtrl[i].row==newIdx.row && this.SelectCtrl[i].column==newIdx.column) {
            this.unhighlightCell(cell);
            this.SelectCtrl.splice(i,1);
            break;
          }
        }
      }
    } else if (e.shiftKey) {
      if (!this.SelectIdxStart) return true;
      this.AdjustSelection(cell);
    } else {
      this.selectCell(cell);
      this.pluginSelect();
    }
    return false;
  },

  pluginSelect: function() {
    if (this.selectPluggedIn) return;
    var tBody=this.tbody[this.SelectIdxStart.tabIdx];
    Rico.eventBind(tBody,"mouseover", this.options.mouseOverHandler, false);
    Rico.eventBind(this.outerDiv,"mouseup",  this.options.mouseUpHandler,  false);
    this.selectPluggedIn=true;
  },

  unplugSelect: function() {
    if (!this.selectPluggedIn) return;
    var tBody=this.tbody[this.SelectIdxStart.tabIdx];
    Rico.eventUnbind(tBody,"mouseover", this.options.mouseOverHandler , false);
    Rico.eventUnbind(this.outerDiv,"mouseup", this.options.mouseUpHandler , false);
    this.selectPluggedIn=false;
  },

  selectMouseUp: function(e) {
    this.unplugSelect();
    var cell=Rico.eventElement(e);
    cell=Rico.getParentByTagName(cell,'div','ricoLG_cell');
    if (!cell) return;
    if (this.SelectIdxStart && this.SelectIdxEnd)
      this.AdjustSelection(cell);
    else
      this.ClearSelection();
  },

  selectMouseOver: function(e) {
    var cell=Rico.eventElement(e);
    cell=Rico.getParentByTagName(cell,'div','ricoLG_cell');
    if (!cell) return;
    this.AdjustSelection(cell);
    Rico.eventStop(e);
  },

  isSelected: function(cell) {
    if (this.options.highlightMethod!='outline') return Rico.hasClass(cell,this.options.highlightClass);
    if (!this.SelectIdxStart || !this.SelectIdxEnd) return false;
    var r1=Math.max(Math.min(this.SelectIdxEnd.row,this.SelectIdxStart.row), this.buffer.windowStart);
    var r2=Math.min(Math.max(this.SelectIdxEnd.row,this.SelectIdxStart.row), this.buffer.windowEnd-1);
    if (r1 > r2) return false;
    var c1=Math.min(this.SelectIdxEnd.column,this.SelectIdxStart.column);
    var c2=Math.max(this.SelectIdxEnd.column,this.SelectIdxStart.column);
    var curIdx=this.datasetIndex(cell);
    return (r1<=curIdx.row && curIdx.row<=r2 && c1<=curIdx.column && curIdx.column<=c2);
  },

  highlightCell: function(cell) {
    Rico.addClass(cell,this.options.highlightClass);
  },

  unhighlightCell: function(cell) {
    if (cell==null) return;
    Rico.removeClass(cell,this.options.highlightClass);
  },

  selectRow: function(r) {
    for (var c=0; c<this.columns.length; c++)
      this.highlightCell(this.columns[c].cell(r));
  },

  unselectRow: function(r) {
    for (var c=0; c<this.columns.length; c++)
      this.unhighlightCell(this.columns[c].cell(r));
  },

  rowMouseOver: function(e) {
    if (!this.highlightEnabled) return;
    var cell=Rico.eventElement(e);
    cell=Rico.getParentByTagName(cell,'div','ricoLG_cell');
    if (!cell) return;
    var newIdx=this.winCellIndex(cell);
    if ((this.options.highlightSection & (newIdx.tabIdx+1))==0) return;
    this.highlight(newIdx);
  },

  highlight: function(newIdx) {
    if (this.options.highlightMethod!='outline') this.cursorSetClass(newIdx);
    if (this.options.highlightMethod!='class') this.cursorOutline(newIdx);
    this.highlightIdx=newIdx;
  },

  cursorSetClass: function(newIdx) {
    switch (this.options.highlightElem) {
      case 'menuCell':
      case 'cursorCell':
        if (this.highlightIdx) this.unhighlightCell(this.highlightIdx.cell);
        this.highlightCell(newIdx.cell);
        break;
      case 'menuRow':
      case 'cursorRow':
        if (this.highlightIdx) this.unselectRow(this.highlightIdx.row);
        var s1=this.options.highlightSection & 1;
        var s2=this.options.highlightSection & 2;
        var c0=s1 ? 0 : this.options.frozenColumns;
        var c1=s2 ? this.columns.length : this.options.frozenColumns;
        for (var c=c0; c<c1; c++)
          this.highlightCell(this.columns[c].cell(newIdx.row));
        break;
      default: return;
    }
  },

  cursorOutline: function(newIdx) {
    var div;
    switch (this.options.highlightElem) {
      case 'menuCell':
      case 'cursorCell':
        div=this.highlightDiv[newIdx.tabIdx];
        div.style.left=(this.columns[newIdx.column].dataCell.offsetLeft-1)+'px';
        div.style.width=this.columns[newIdx.column].colWidth;
        this.highlightDiv[1-newIdx.tabIdx].style.display='none';
        break;
      case 'menuRow':
      case 'cursorRow':
        div=this.highlightDiv[0];
        var s1=this.options.highlightSection & 1;
        var s2=this.options.highlightSection & 2;
        div.style.left=s1 ? '0px' : this.frozenTabs.style.width;
        div.style.width=((s1 ? this.frozenTabs.offsetWidth : 0) + (s2 ? this.innerDiv.offsetWidth : 0) - 4)+'px';
        break;
      default: return;
    }
    div.style.top=(this.hdrHt+newIdx.row*this.rowHeight-1)+'px';
    div.style.height=(this.rowHeight-1)+'px';
    div.style.display='';
  },

  unhighlight: function() {
    switch (this.options.highlightElem) {
      case 'menuCell':
        this.highlightIdx=this.menuIdx;
        /*jsl:fallthru*/
      case 'cursorCell':
        if (this.highlightIdx) this.unhighlightCell(this.highlightIdx.cell);
        if (!this.highlightDiv) return;
        for (var i=0; i<2; i++)
          this.highlightDiv[i].style.display='none';
        break;
      case 'menuRow':
        this.highlightIdx=this.menuIdx;
        /*jsl:fallthru*/
      case 'cursorRow':
        if (this.highlightIdx) this.unselectRow(this.highlightIdx.row);
        if (this.highlightDiv) this.highlightDiv[0].style.display='none';
        break;
    }
  },

  resetContents: function(resetHt) {
    Rico.log("resetContents("+resetHt+")");
    this.ClearSelection();
    this.buffer.clear();
    this.clearRows();
    if (typeof resetHt=='undefined' || resetHt==true) {
      this.buffer.setTotalRows(0);
    } else {
      this.scrollToRow(0);
    }
    this.clearBookmark();
  },

  setImages: function() {
    for (var n=0; n<this.columns.length; n++)
      this.columns[n].setImage();
  },

  // returns column index, or -1 if there are no sorted columns
  findSortedColumn: function() {
    for (var n=0; n<this.columns.length; n++) {
      if (this.columns[n].isSorted()) return n;
    }
    return -1;
  },

  findColumnName: function(name) {
    for (var n=0; n<this.columns.length; n++) {
      if (this.columns[n].fieldName == name) return n;
    }
    return -1;
  },

/**
 * Set initial sort
 */
  setSortUI: function( columnNameOrNum, sortDirection ) {
    Rico.log("setSortUI: "+columnNameOrNum+' '+sortDirection);
    var colnum=this.findSortedColumn();
    if (colnum >= 0) {
      sortDirection=this.columns[colnum].getSortDirection();
    } else {
      if (typeof sortDirection!='string') {
        sortDirection=Rico.TableColumn.SORT_ASC;
      } else {
        sortDirection=sortDirection.toUpperCase();
        if (sortDirection != Rico.TableColumn.SORT_DESC) sortDirection=Rico.TableColumn.SORT_ASC;
      }
      switch (typeof columnNameOrNum) {
        case 'string':
          colnum=this.findColumnName(columnNameOrNum);
          break;
        case 'number':
          colnum=columnNameOrNum;
          break;
      }
    }
    if (typeof(colnum)!='number' || colnum < 0) return;
    this.clearSort();
    this.columns[colnum].setSorted(sortDirection);
    this.buffer.sortBuffer(colnum);
  },

/**
 * clear sort flag on all columns
 */
  clearSort: function() {
    for (var x=0;x<this.columns.length;x++)
      this.columns[x].setUnsorted();
  },

/**
 * clear filters on all columns
 */
  clearFilters: function() {
    for (var x=0;x<this.columns.length;x++) {
      this.columns[x].setUnfiltered(true);
    }
    if (this.options.filterHandler) {
      this.options.filterHandler();
    }
  },

/**
 * returns number of columns with a user filter set
 */
  filterCount: function() {
    for (var x=0,cnt=0;x<this.columns.length;x++) {
      if (this.columns[x].isFiltered()) cnt++;
    }
    return cnt;
  },

  sortHandler: function() {
    this.cancelMenu();
    this.ClearSelection();
    this.setImages();
    var n=this.findSortedColumn();
    if (n < 0) return;
    Rico.log("sortHandler: sorting column "+n);
    this.buffer.sortBuffer(n);
    this.clearRows();
    this.scrollDiv.scrollTop = 0;
    this.buffer.fetch(0);
  },

  filterHandler: function() {
    Rico.log("filterHandler");
    this.cancelMenu();
    if (this.buffer.processingRequest) {
      this.queueFilter=true;
      return;
    }
    this.unplugScroll();
    this.ClearSelection();
    this.setImages();
    this.clearBookmark();
    this.clearRows();
    this.buffer.fetch(-1);
    Rico.runLater(1,this,'pluginScroll'); // resetting ht div can cause a scroll event, triggering an extra fetch
  },

  clearBookmark: function() {
    if (this.bookmark) this.bookmark.innerHTML="&nbsp;";
  },

  bookmarkHandler: function(firstrow,lastrow) {
    var newhtml;
    if (isNaN(firstrow) || !this.bookmark) return;
    var totrows=this.buffer.totalRows;
    if (totrows < lastrow) lastrow=totrows;
    if (totrows<=0) {
      newhtml = Rico.getPhraseById('bookmarkNoMatch');
    } else if (lastrow<0) {
      newhtml = Rico.getPhraseById('bookmarkNoRec');
    } else if (this.buffer.foundRowCount) {
      newhtml = Rico.getPhraseById('bookmarkExact',firstrow,lastrow,totrows);
    } else {
      newhtml = Rico.getPhraseById('bookmarkAbout',firstrow,lastrow,totrows);
    }
    this.bookmark.innerHTML = newhtml;
  },

  clearRows: function() {
    if (this.isBlank==true) return;
    for (var c=0; c < this.columns.length; c++)
      this.columns[c].clearColumn();
    this.isBlank = true;
  },

  refreshContents: function(startPos) {
    Rico.log("refreshContents: startPos="+startPos+" lastRow="+this.lastRowPos+" PartBlank="+this.isPartialBlank+" pageSize="+this.pageSize);
    this.hideMsg();
    this.cancelMenu();
    this.unhighlight(); // in case highlighting was manually invoked
    if (this.queueFilter) {
      Rico.log("refreshContents: cancelling refresh because filter has changed");
      this.queueFilter=false;
      this.filterHandler();
      return;
    }
    this.highlightEnabled=this.options.highlightSection!='none';
    if (startPos == this.lastRowPos && !this.isPartialBlank && !this.isBlank) return;
    this.isBlank = false;
    var viewPrecedesBuffer = this.buffer.startPos > startPos;
    var contentStartPos = viewPrecedesBuffer ? this.buffer.startPos: startPos;
    this.contentStartPos = contentStartPos+1;
    var contentEndPos = Math.min(this.buffer.startPos + this.buffer.size, startPos + this.pageSize);
    var onRefreshComplete = this.options.onRefreshComplete;

    if ((startPos + this.pageSize < this.buffer.startPos) ||
        (this.buffer.startPos + this.buffer.size < startPos) ||
        (this.buffer.size == 0)) {
      this.clearRows();
      if (onRefreshComplete) onRefreshComplete(this.contentStartPos,contentEndPos);  // update bookmark
      return;
    }

    Rico.log('refreshContents: contentStartPos='+contentStartPos+' contentEndPos='+contentEndPos+' viewPrecedesBuffer='+viewPrecedesBuffer);
    var rowSize = contentEndPos - contentStartPos;
    this.buffer.setWindow(contentStartPos, rowSize );
    var blankSize = this.pageSize - rowSize;
    var blankOffset = viewPrecedesBuffer ? 0: rowSize;
    var contentOffset = viewPrecedesBuffer ? blankSize: 0;

    for (var r=0; r < rowSize; r++) { //initialize what we have
      for (var c=0; c < this.columns.length; c++)
        this.columns[c].displayValue(r + contentOffset);
    }
    for (var i=0; i < blankSize; i++)     // blank out the rest
      this.blankRow(i + blankOffset);
    if (this.options.highlightElem=='selection') this.ShowSelection();
    this.isPartialBlank = blankSize > 0;
    this.lastRowPos = startPos;
    Rico.log("refreshContents complete, startPos="+startPos);
    if (onRefreshComplete) onRefreshComplete(this.contentStartPos,contentEndPos);  // update bookmark
  },

  scrollToRow: function(rowOffset) {
     var p=this.rowToPixel(rowOffset);
     Rico.log("scrollToRow, rowOffset="+rowOffset+" pixel="+p);
     this.scrollDiv.scrollTop = p; // this causes a scroll event
     if ( this.options.onscroll )
        this.options.onscroll( this, rowOffset );
  },

  scrollUp: function() {
     this.moveRelative(-1);
  },

  scrollDown: function() {
     this.moveRelative(1);
  },

  pageUp: function() {
     this.moveRelative(-this.pageSize);
  },

  pageDown: function() {
     this.moveRelative(this.pageSize);
  },

  adjustRow: function(rowOffset) {
     var notdisp=this.topOfLastPage();
     if (notdisp == 0 || !rowOffset) return 0;
     return Math.min(notdisp,rowOffset);
  },

  rowToPixel: function(rowOffset) {
     return this.adjustRow(rowOffset) * this.rowHeight;
  },

/**
 * @returns row to display at top of scroll div
 */
  pixeltorow: function(p) {
     var notdisp=this.topOfLastPage();
     if (notdisp == 0) return 0;
     var prow=parseInt(p/this.rowHeight,10);
     return Math.min(notdisp,prow);
  },

  moveRelative: function(relOffset) {
     var newoffset=Math.max(this.scrollDiv.scrollTop+relOffset*this.rowHeight,0);
     newoffset=Math.min(newoffset,this.scrollDiv.scrollHeight);
     //Rico.log("moveRelative, newoffset="+newoffset);
     this.scrollDiv.scrollTop=newoffset;
  },

  pluginScroll: function() {
     if (this.scrollPluggedIn) return;
     Rico.log("pluginScroll: wheelEvent="+this.wheelEvent);
     Rico.eventBind(this.scrollDiv,"scroll",this.scrollEventFunc, false);
     for (var t=0; t<2; t++)
       Rico.eventBind(this.tabs[t],this.wheelEvent,this.wheelEventFunc, false);
     this.scrollPluggedIn=true;
  },

  unplugScroll: function() {
     if (!this.scrollPluggedIn) return;
     Rico.log("unplugScroll");
     Rico.eventUnbind(this.scrollDiv,"scroll", this.scrollEventFunc , false);
     for (var t=0; t<2; t++)
       Rico.eventUnbind(this.tabs[t],this.wheelEvent,this.wheelEventFunc, false);
     this.scrollPluggedIn=false;
  },

  handleWheel: function(e) {
    var delta = 0;
    if (e.wheelDelta) {
      if (Rico.isOpera)
        delta = e.wheelDelta/120;
      else if (Rico.isWebKit)
        delta = -e.wheelDelta/12;
      else
        delta = -e.wheelDelta/120;
    } else if (e.detail) {
      delta = e.detail/3; /* Mozilla/Gecko */
    }
    if (delta) this.moveRelative(delta);
    Rico.eventStop(e);
    return false;
  },

  handleScroll: function(e) {
     if ( this.scrollTimeout )
       clearTimeout( this.scrollTimeout );
     this.setHorizontalScroll();
     var scrtop=this.scrollDiv.scrollTop;
     var vscrollDiff = this.lastScrollPos-scrtop;
     if (vscrollDiff == 0.00) return;
     var newrow=this.pixeltorow(scrtop);
     if (newrow == this.lastRowPos && !this.isPartialBlank && !this.isBlank) return;
     var stamp1 = new Date();
     //Rico.log("handleScroll, newrow="+newrow+" scrtop="+scrtop);
     if (this.options.highlightElem=='selection') this.HideSelection();
     this.buffer.fetch(newrow);
     if (this.options.onscroll) this.options.onscroll(this, newrow);
     this.scrollTimeout = Rico.runLater(1200,this,'scrollIdle');
     this.lastScrollPos = this.scrollDiv.scrollTop;
     var stamp2 = new Date();
     //Rico.log("handleScroll, time="+(stamp2.getTime()-stamp1.getTime()));
  },

  scrollIdle: function() {
     if ( this.options.onscrollidle )
        this.options.onscrollidle();
  },

  printAll: function(exportType) {
    this.showMsg(Rico.getPhraseById('exportInProgress'));
    Rico.runLater(10,this,'_printAll',exportType);  // allow message to paint
  },

/**
 * Support function for printAll()
 */
  _printAll: function(exportType) {
    this.exportStart();
    this.buffer.exportAllRows(Rico.bind(this,'exportBuffer'),Rico.bind(this,'exportFinish',exportType));
  },

  _printVisible: function(exportType) {
    this.exportStart();
    this.exportBuffer(this.buffer.visibleRows(),0);
    this.exportFinish(exportType);
  },

/**
 * Send all rows to print/export window
 */
  exportBuffer: function(rows,startPos) {
    var r,c,v,col,exportText;
    Rico.log("exportBuffer: "+rows.length+" rows");
    var tdstyle=[];
    var totalcnt=startPos || 0;
    for (c=0; c<this.columns.length; c++) {
      if (this.columns[c].visible) tdstyle[c]=this.exportStyle(this.columns[c].cell(0));  // assumes row 0 style applies to all rows
    }
    for(r=0; r < rows.length; r++) {
      exportText='';
      for (c=0; c<this.columns.length; c++) {
        if (!this.columns[c].visible) continue;
        col=this.columns[c];
        col.expStyle=tdstyle[c];
        v=col._export(rows[r][c],rows[r]);
        if (v.match(/<span\s+class=(['"]?)ricolookup\1>(.*)<\/span>/i))
          v=RegExp.leftContext;
        if (v=='') v='&nbsp;';
        exportText+="<td style='"+col.expStyle+"'>"+v+"</td>";
      }
      this.exportRows.push(exportText);
      totalcnt++;
      if (totalcnt % 10 == 0) window.status=Rico.getPhraseById('exportStatus',totalcnt);
    }
  }

};


Rico.TableColumn.prototype = 
/** @lends Rico.TableColumn# */
{
/**
 * Implements a LiveGrid column. Also contains static properties used by SimpleGrid columns.
 * @extends Rico.TableColumnBase
 * @constructs
 */
initialize: function(liveGrid,colIdx,hdrInfo,tabIdx) {
  Rico.extend(this, new Rico.TableColumnBase());
  this.baseInit(liveGrid,colIdx,hdrInfo,tabIdx);
  if (typeof this.format.type!='string') this.format.type='raw';
  if (typeof this.isNullable!='boolean') this.isNullable = /number|date/.test(this.format.type);
  this.isText = /raw|text|showTags/.test(this.format.type);
  Rico.log(" sortable="+this.sortable+" filterable="+this.filterable+" hideable="+this.hideable+" isNullable="+this.isNullable+' isText='+this.isText);
  this.fixHeaders(this.liveGrid.tableId, this.options.hdrIconsFirst);
  if (this.format.control) {
    // copy all properties/methods that start with '_'
    if (typeof this.format.control=='string') {
      this.format.control=eval(this.format.control);
    }
    for (var property in this.format.control) {
      if (property.charAt(0)=='_') {
        Rico.log("Copying control property "+property);
        this[property] = this.format.control[property];
      }
    }
  }
  if (this['format_'+this.format.type])
    this._format=Rico.bind(this,'format_'+this.format.type);
},

/**
 * Sorts the column in ascending order
 */
sortAsc: function() {
  this.setColumnSort(Rico.TableColumn.SORT_ASC);
},

/**
 * Sorts the column in descending order
 */
sortDesc: function() {
  this.setColumnSort(Rico.TableColumn.SORT_DESC);
},

/**
 * Sorts the column in the specified direction
 * @param direction must be one of Rico.TableColumn.UNSORTED, .SORT_ASC, or .SORT_DESC
 */
setColumnSort: function(direction) {
  this.liveGrid.clearSort();
  this.setSorted(direction);
  if (this.liveGrid.options.saveColumnInfo.sort)
    this.liveGrid.setCookie();
  if (this.options.sortHandler)
    this.options.sortHandler();
},

/**
 * @returns true if this column is allowed to be sorted
 */
isSortable: function() {
  return this.sortable;
},

/**
 * @returns true if this column is currently sorted
 */
isSorted: function() {
  return this.currentSort != Rico.TableColumn.UNSORTED;
},

/**
 * @returns Rico.TableColumn.UNSORTED, .SORT_ASC, or .SORT_DESC
 */
getSortDirection: function() {
  return this.currentSort;
},

/**
 * toggle the sort sequence for this column
 */
toggleSort: function() {
  if (this.liveGrid.buffer && this.liveGrid.buffer.totalRows==0) return;
  if (this.currentSort == Rico.TableColumn.SORT_ASC)
    this.sortDesc();
  else
    this.sortAsc();
},

/**
 * Flags that this column is not sorted
 */
setUnsorted: function() {
  this.setSorted(Rico.TableColumn.UNSORTED);
},

/**
 * Flags that this column is sorted, but doesn't actually carry out the sort
 * @param direction must be one of Rico.TableColumn.UNSORTED, .SORT_ASC, or .SORT_DESC
 */
setSorted: function(direction) {
  this.currentSort = direction;
},

/**
 * @returns true if this column is allowed to be filtered
 */
canFilter: function() {
  return this.filterable;
},

/**
 * @returns a textual representation of how this column is filtered
 */
getFilterText: function() {
  var vals=[];
  for (var i=0; i<this.filterValues.length; i++) {
    var v=this.filterValues[i];
    if (typeof(v)=='string' && v.match(/<span\s+class=(['"]?)ricolookup\1>(.*)<\/span>/i)) v=RegExp.leftContext;
    vals.push(v=='' ? Rico.getPhraseById('filterBlank') : v);
  }
  switch (this.filterOp) {
    case 'EQ':   return '= '+(vals[0]);
    case 'NE':   return Rico.getPhraseById('filterNot',vals.join(', '));
    case 'LE':   return '<= '+vals[0];
    case 'GE':   return '>= '+vals[0];
    case 'LIKE': return Rico.getPhraseById('filterLike',vals[0]);
    case 'NULL': return Rico.getPhraseById('filterEmpty');
    case 'NOTNULL': return Rico.getPhraseById('filterNotEmpty');
  }
  return '?';
},

/**
 * @returns returns the query string representation of the filter
 */
getFilterQueryParm: function() {
  if (this.filterType == Rico.TableColumn.UNFILTERED) return '';
  var retval='&f['+this.index+'][op]='+this.filterOp;
  retval+='&f['+this.index+'][len]='+this.filterValues.length;
  for (var i=0; i<this.filterValues.length; i++) {
    retval+='&f['+this.index+']['+i+']='+escape(this.filterValues[i]);
  }
  return retval;
},

/**
 * removes the filter from this column
 */
setUnfiltered: function(skipHandler) {
  this.filterType = Rico.TableColumn.UNFILTERED;
  if (this.liveGrid.options.saveColumnInfo.filter)
    this.liveGrid.setCookie();
  if (this.removeFilterFunc)
    this.removeFilterFunc();
  if (this.options.filterHandler && !skipHandler)
    this.options.filterHandler();
},

setFilterEQ: function() {
  this.setUserFilter('EQ');
},
setFilterNE: function() {
  this.setUserFilter('NE');
},
addFilterNE: function() {
  this.filterValues.push(this.userFilter);
  if (this.liveGrid.options.saveColumnInfo.filter)
    this.liveGrid.setCookie();
  if (this.options.filterHandler)
    this.options.filterHandler();
},
setFilterGE: function() { this.setUserFilter('GE'); },
setFilterLE: function() { this.setUserFilter('LE'); },
setFilterKW: function(keyword) {
  if (keyword!='' && keyword!=null) {
    this.setFilter('LIKE',keyword,Rico.TableColumn.USERFILTER);
  } else {
    this.setUnfiltered(false);
  }
},

setUserFilter: function(relop) {
  this.setFilter(relop,this.userFilter,Rico.TableColumn.USERFILTER);
},

setSystemFilter: function(relop,filter) {
  this.setFilter(relop,filter,Rico.TableColumn.SYSTEMFILTER);
},

setFilter: function(relop,filter,type,removeFilterFunc) {
  this.filterValues = [filter];
  this.filterType = type;
  this.filterOp = relop;
  if (type == Rico.TableColumn.USERFILTER && this.liveGrid.options.saveColumnInfo.filter)
    this.liveGrid.setCookie();
  this.removeFilterFunc=removeFilterFunc;
  if (this.options.filterHandler)
    this.options.filterHandler();
},

isFiltered: function() {
  return this.filterType == Rico.TableColumn.USERFILTER;
},

filterChange: function(e) {
  var selbox=Rico.eventElement(e);
  if (selbox.value==this.liveGrid.options.FilterAllToken)
    this.setUnfiltered();
  else
    this.setFilter('EQ',selbox.value,Rico.TableColumn.USERFILTER,function() {selbox.selectedIndex=0;});
},

filterClear: function(e) {
  this.filterField.value='';
  this.setUnfiltered();
},

filterKeypress: function(e) {
  var txtbox=Rico.eventElement(e);
  if (typeof this.lastKeyFilter != 'string') this.lastKeyFilter='';
  if (this.lastKeyFilter==txtbox.value) return;
  var v=txtbox.value;
  Rico.log("filterKeypress: "+this.index+' '+v);
  this.lastKeyFilter=v;
  if (v=='' || v=='*')
    this.setUnfiltered();
  else {
    this.setFilter('LIKE', v, Rico.TableColumn.USERFILTER, function() {txtbox.value='';});
  }
},

format_text: function(v) {
  if (typeof v!='string')
    return '&nbsp;';
  else
    return v.stripTags();
},

format_showTags: function(v) {
  if (typeof v!='string')
    return '&nbsp;';
  else
    return v.replace(/&/g, '&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
},

format_number: function(v) {
  if (typeof v=='undefined' || v=='' || v==null)
    return '&nbsp;';
  else
    return Rico.formatNumber(v,this.format);
},

format_datetime: function(v) {
  if (typeof v=='undefined' || v=='' || v==null)
    return '&nbsp;';
  else {
    var d=Rico.setISO8601(v);
    if (!d) return v;
    return (this.format.prefix || '')+Rico.formatDate(d,this.format.dateFmt || 'translateDateTime')+(this.format.suffix || '');
  }
},

// converts GMT/UTC to local time
format_UTCasLocalTime: function(v) {
  if (typeof v=='undefined' || v=='' || v==null)
    return '&nbsp;';
  else {
    var d=Rico.setISO8601(v,-d.getTimezoneOffset());
    if (!d) return v;
    return (this.format.prefix || '')+Rico.formatDate(d,this.format.dateFmt || 'translateDateTime')+(this.format.suffix || '');
  }
},

format_date: function(v) {
  if (typeof v=='undefined' || v==null || v=='')
    return '&nbsp;';
  else {
    var d=Rico.setISO8601(v);
    if (!d) return v;
    return (this.format.prefix || '')+Rico.formatDate(d,this.format.dateFmt || 'translateDate')+(this.format.suffix || '');
  }
},

fixHeaders: function(prefix, iconsfirst) {
  if (this.sortable) {
    var handler=Rico.eventHandle(this,'toggleSort');
    switch (this.options.headingSort) {
      case 'link':
        var a=Rico.wrapChildren(this.hdrCellDiv,'ricoSort',undefined,'a');
        a.href = "javascript:void(0)";
        Rico.eventBind(a,"click", handler);
        break;
      case 'hover':
        Rico.eventBind(this.hdrCellDiv,"click", handler);
        break;
    }
  }
  this.imgFilter = document.createElement('img');
  this.imgFilter.style.display='none';
  this.imgFilter.src=Rico.imgDir+this.options.filterImg;
  this.imgFilter.className='ricoLG_HdrIcon';
  this.imgSort = document.createElement('span');
  this.imgSort.style.display='none';
  if (iconsfirst) {
    this.hdrCellDiv.insertBefore(this.imgSort,this.hdrCellDiv.firstChild);
    this.hdrCellDiv.insertBefore(this.imgFilter,this.hdrCellDiv.firstChild);
  } else {
    this.hdrCellDiv.appendChild(this.imgFilter);
    this.hdrCellDiv.appendChild(this.imgSort);
  }
  if (!this.format.filterUI) {
    Rico.eventBind(this.imgFilter, 'click', Rico.eventHandle(this,'filterClick'), false);
  }
},

filterClick: function(e) {
  if (this.filterType==Rico.TableColumn.USERFILTER && this.filterOp=='LIKE') {
    this.liveGrid.openKeyword(this.index);
  }
},

getValue: function(windowRow) {
  return this.liveGrid.buffer.getWindowValue(windowRow,this.index);
},

getBufferCell: function(windowRow) {
  return this.liveGrid.buffer.getWindowCell(windowRow,this.index);
},

getBufferAttr: function(windowRow) {
  return this.liveGrid.buffer.getWindowAttr(windowRow,this.index);
},

setValue: function(windowRow,newval) {
  this.liveGrid.buffer.setWindowValue(windowRow,this.index,newval);
},

_format: function(v) {
  return v;
},

_display: function(v,gridCell) {
  gridCell.innerHTML=this._format(v);
},

_export: function(v) {
  return this._format(v);
},

displayValue: function(windowRow) {
  var bufCell=this.getBufferCell(windowRow);
  if (bufCell==null) {
    this.clearCell(windowRow);
    return;
  }
  var gridCell=this.cell(windowRow);
  this._display(bufCell,gridCell,windowRow);
  var acceptAttr=this.liveGrid.buffer.options.acceptAttr;
  if (acceptAttr.length==0) return;
  var bufAttr=this.getBufferAttr(windowRow);
  if (bufAttr==null) return;
  for (var k=0; k<acceptAttr.length; k++) {
    bufAttr=bufAttr['_'+acceptAttr[k]] || '';
    switch (acceptAttr[k]) {
      case 'style': gridCell.style.cssText=bufAttr; break;
      case 'class': gridCell.className=bufAttr; break;
      default:      gridCell['_'+acceptAttr[k]]=bufAttr; break;
    }
  }
}

};

Rico.includeLoaded('ricoLiveGrid.js');
