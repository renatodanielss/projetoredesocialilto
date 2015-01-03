/**
  *  Copyright 2005 Sabre Airline Solutions
  *
  *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
  *  file except in compliance with the License. You may obtain a copy of the License at
  *
  *         http://www.apache.org/licenses/LICENSE-2.0
  *
  *  Unless required by applicable law or agreed to in writing, software distributed under the
  *  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
  *  either express or implied. See the License for the specific language governing permissions
  *  and limitations under the License.
  **/

/**
 * @namespace Main Rico object
 */
var Rico = {
  Version: '3.0',
  loadRequested: 1,
  loadComplete: 2,
  theme: {},

  init : function() {
    try {  // fix IE background image flicker (credit: www.mister-pixel.com)
      document.execCommand("BackgroundImageCache", false, true);
    } catch(err) {}
    this.preloadMsgs='';
    this.baseHref= location.protocol + "//" + location.host;
    this.loadedFiles={};
    this.windowIsLoaded=false;
    this.onLoadCallbacks=[];
    var onloadAction=Rico.bind(this,'windowLoaded');
    if (window.addEventListener)
      window.addEventListener('load', onloadAction, false);
    else if (window.attachEvent)
      window.attachEvent('onload', onloadAction);
    this.onLoad(function() { Rico.log('Pre-load messages:\n'+Rico.preloadMsgs); });
  },

  setPaths : function(jsDir,cssDir,imgDir,htmDir) {
    this.jsDir = jsDir;
    this.cssDir = cssDir;
    this.imgDir = imgDir;
    this.htmDir = htmDir || jsDir;
  },

  // Array entries can reference a javascript file or css stylesheet
  // A dependency on another module can be indicated with a plus-sign prefix: '+DependsOnModule'
  moduleDependencies : {
    DragAndDrop: ['ricoDragDrop.js'],
    Calendar   : ['ricoCalendar.js'],
    Tree       : ['ricoTree.js'],
    ColorPicker: ['ricoColorPicker.js'],
    SimpleGrid : ['ricoGridCommon.js', 'ricoSimpleGrid.js'],
    LiveGridBasic : ['ricoGridCommon.js', 'ricoLiveGrid.js'],
    LiveGrid      : ['+LiveGridBasic', 'ricoLiveGridControls.js'],
    LiveGridMenu  : ['ricoLiveGridMenu.js'],
    LiveGridAjax  : ['+LiveGrid', 'ricoLiveGridAjax.js'],
    LiveGridJSON  : ['+LiveGridAjax', 'ricoLiveGridJSON.js'],
    LiveGridForms : ['+LiveGridAjax', '+LiveGridMenu', 'ricoLiveGridForms.js']
  },

  languages : {
    de: "translations/ricoLocale_de.js",
    en: "translations/ricoLocale_en.js",
    es: "translations/ricoLocale_es.js",
    fr: "translations/ricoLocale_fr.js",
    it: "translations/ricoLocale_it.js",
    ja: "translations/ricoLocale_ja.js",
    ko: "translations/ricoLocale_ko.js",
    pt: "translations/ricoLocale_pt.js",
    zh: "translations/ricoLocale_zh.js"
  },

  languageInclude : function(lang2) {
    var filename=this.languages[lang2];
    if (filename) this.include(filename);
    return !!filename;
  },

  acceptLanguage : function(acceptLang) {
    var arLang=acceptLang.toLowerCase().split(',');
    for (var i=0; i<arLang.length; i++) {
      var lang2=arLang[i].match(/\w\w/);
      if (!lang2) continue;
      if (this.languageInclude(lang2)) return true;
    }
    return false;
  },

  /**
   *  Expects one or more module or file names and loads
   *  the appropriate files.
   *  MUST call Rico.setPaths() before calling this method
   */
  loadModule : function() {
    for (var a=0, length=arguments.length; a<length; a++) {
      var name=arguments[a];
      var dep=this.moduleDependencies[name];
      if (dep) {
        for (var i=0; i<dep.length; i++) {
          if (dep[i].substring(0,1)=='+') {
            this.loadModule(dep[i].slice(1));
          } else {
            this.include(dep[i]);
          }
        }
      } else {
        this.include(name);
      }
    }
  },

  include : function(filename) {
    if (this.loadedFiles[filename]) return;
    this.addPreloadMsg('include: '+filename);
    var ext = filename.substr(filename.lastIndexOf('.')+1);
    switch (ext.toLowerCase()) {
      case 'js':
        this.loadedFiles[filename]=filename.substring(0,4)=='rico' ? this.loadRequested : this.loadComplete;
        document.write("<script type='text/javascript' src='"+this.jsDir+filename+"'><\/script>");
        return;
      case 'css':
        var el = document.createElement('link');
        el.type = 'text/css';
        el.rel = 'stylesheet';
        el.href = this.cssDir+filename;
        this.loadedFiles[filename]=this.loadComplete;
        document.getElementsByTagName('head')[0].appendChild(el);
        return;
    }
  },

  // called after a script file has finished loading
  includeLoaded: function(filename) {
    this.loadedFiles[filename]=this.loadComplete;
    this.checkIfComplete();
  },

  // called by the document onload event
  windowLoaded: function() {
    this.windowIsLoaded=true;
    this.checkIfComplete();
  },

  checkIfComplete: function() {
    var waitingFor=this.windowIsLoaded ? '' : 'window';
    for(var filename in  this.loadedFiles) {
      if (this.loadedFiles[filename]==this.loadRequested)
        waitingFor+=' '+filename;
    }
    //window.status='waitingFor: '+waitingFor;
    this.addPreloadMsg('waitingFor: '+waitingFor);
    if (waitingFor.length==0) {
      this.addPreloadMsg('Processing callbacks');
      while (this.onLoadCallbacks.length > 0) {
        var callback=this.onLoadCallbacks.shift();
        if (callback) callback();
      }
    }
  },

  onLoad: function(callback,frontOfQ) {
    if (frontOfQ)
      this.onLoadCallbacks.unshift(callback);
    else
      this.onLoadCallbacks.push(callback);
    this.checkIfComplete();
  },

  isKonqueror : navigator.userAgent.toLowerCase().indexOf("konqueror") > -1,
  isIE:  !!(window.attachEvent && navigator.userAgent.indexOf('Opera') === -1),
  isOpera: navigator.userAgent.indexOf('Opera') > -1,
  isWebKit: navigator.userAgent.indexOf('AppleWebKit/') > -1,
  isGecko:  navigator.userAgent.indexOf('Gecko') > -1 && navigator.userAgent.indexOf('KHTML') === -1,
  ieVersion: /MSIE (\d+\.\d+);/.test(navigator.userAgent) ? new Number(RegExp.$1) : null,
  // Added by Asbru Ltd. - MSIE 11 detection
  isIE11:  !!navigator.userAgent.match(/Trident\/7\./),

  // logging funtions

  startTime : new Date(),

  timeStamp: function() {
    var stamp = new Date();
    return (stamp.getTime()-this.startTime.getTime())+": ";
  },

setDebugArea: function(id, forceit) {
  if (!this.debugArea || forceit) {
    var newarea=document.getElementById(id);
    if (!newarea) return;
    this.debugArea=newarea;
    newarea.value='';
  }
},

addPreloadMsg: function(msg) {
  this.preloadMsgs+=this.timeStamp()+msg+"\n";
},

log: function() {},

enableLogging: function() {
  if (this.debugArea) {
    this.log = function(msg, resetFlag) {
      if (resetFlag) this.debugArea.value='';
      this.debugArea.value+=this.timeStamp()+msg+"\n";
    };
  } else if (window.console) {
    if (window.console.firebug)
      this.log = function(msg) { window.console.log(this.timeStamp(),msg); };
    else
      this.log = function(msg) { window.console.log(this.timeStamp()+msg); };
  } else if (window.opera) {
    this.log = function(msg) { window.opera.postError(this.timeStamp()+msg); };
  }
},

$: function(e) {
  return typeof e == 'string' ? document.getElementById(e) : e;
},

bind: function() {
  var args = Array.prototype.slice.call(arguments);
  var object = args.shift();
  var method = args.shift();
  return function() {
    var newargs = Array.prototype.slice.call(arguments);
    return object[method].apply(object,args.concat(newargs));
  };
},

runLater: function() {
  var args = Array.prototype.slice.call(arguments);
  var msec = args.shift();
  var object = args.shift();
  var method = args.shift();
  return setTimeout(function() { object[method].apply(object,args); },msec);
},

visible: function(element) {
  return Rico.getStyle(element,"display") != 'none';
},

show: function(element) {
  element.style.display = '';
},

hide: function(element) {
  element.style.display = 'none';
},

toggle: function(element) {
  element.style.display = element.style.display == 'none' ? '' : 'none';
},

viewportOffset: function(element) {
  var offset=Rico.cumulativeOffset(element);
  offset.left -= this.docScrollLeft();
  offset.top -= this.docScrollTop();
  return offset;
},

/**
 * Return text within an html element
 * @param el DOM node
 * @param xImg true to exclude img tag info
 * @param xForm true to exclude input, select, and textarea tags
 * @param xClass exclude elements with a class name of xClass
 */
getInnerText: function(el,xImg,xForm,xClass) {
  switch (typeof el) {
    case 'string': return el;
    case 'undefined': return el;
    case 'number': return el.toString();
  }
  var cs = el.childNodes;
  var l = cs.length;
  var str = "";
  for (var i = 0; i < l; i++) {
   switch (cs[i].nodeType) {
     case 1: //ELEMENT_NODE
       if (this.getStyle(cs[i],'display')=='none') continue;
       if (xClass && this.hasClass(cs[i],xClass)) continue;
       switch (cs[i].tagName.toLowerCase()) {
         case 'img':   if (!xImg) str += cs[i].alt || cs[i].title || cs[i].src; break;
         case 'input': if (cs[i].type=='hidden') continue;
         case 'select':
         /******* replace $F here **********/
         case 'textarea': if (!xForm) str += $F(cs[i]) || ''; break;
         default:      str += this.getInnerText(cs[i],xImg,xForm,xClass); break;
       }
       break;
     case 3: //TEXT_NODE
       str += cs[i].nodeValue;
       break;
   }
  }
  return str;
},

/**
 * Return value of a node in an XML response.
 * For Konqueror 3.5, isEncoded must be true.
 */
getContentAsString: function( parentNode, isEncoded ) {
  if (isEncoded) return this._getEncodedContent(parentNode);
  if (typeof parentNode.xml != 'undefined') return this._getContentAsStringIE(parentNode);
  return this._getContentAsStringMozilla(parentNode);
},

_getEncodedContent: function(parentNode) {
  if (parentNode.innerHTML) return parentNode.innerHTML;
  switch (parentNode.childNodes.length) {
    case 0:  return "";
    case 1:  return parentNode.firstChild.nodeValue;
    default: return parentNode.childNodes[1].nodeValue;
  }
},

_getContentAsStringIE: function(parentNode) {
  var contentStr = "";
  for ( var i = 0 ; i < parentNode.childNodes.length ; i++ ) {
     var n = parentNode.childNodes[i];
     contentStr += (n.nodeType == 4) ? n.nodeValue : n.xml;
  }
  return contentStr;
},

_getContentAsStringMozilla: function(parentNode) {
   var xmlSerializer = new XMLSerializer();
   var contentStr = "";
   for ( var i = 0 ; i < parentNode.childNodes.length ; i++ ) {
        var n = parentNode.childNodes[i];
        if (n.nodeType == 4) { // CDATA node
            contentStr += n.nodeValue;
        }
        else {
          contentStr += xmlSerializer.serializeToString(n);
      }
   }
   return contentStr;
},

/**
 * @param n a number (or a string to be converted using parseInt)
 * @returns the integer value of n, or 0 if n is not a number
 */
nan2zero: function(n) {
  if (typeof(n)=='string') n=parseInt(n,10);
  return isNaN(n) || typeof(n)=='undefined' ? 0 : n;
},

stripTags: function(s) {
  return s.replace(/<\/?[^>]+>/gi, '');
},

truncate: function(s,length) {
  return s.length > length ? s.substr(0, length - 3) + '...' : s;
},

zFill: function(n,slen, radix) {
  var s=n.toString(radix || 10);
  while (s.length<slen) s='0'+s;
  return s;
},

/**
 * @param e event object
 * @returns the key code stored in the event
 */
eventKey: function(e) {
  if( typeof( e.keyCode ) == 'number'  ) {
    return e.keyCode; //DOM
  } else if( typeof( e.which ) == 'number' ) {
    return e.which;   //NS 4 compatible
  } else if( typeof( e.charCode ) == 'number'  ) {
    return e.charCode; //also NS 6+, Mozilla 0.9+
  }
  return -1;  //total failure, we have no way of obtaining the key code
},

eventLeftClick: function(e) {
  return (((e.which) && (e.which == 1)) ||
          ((e.button) && (e.button == 1)));
},

eventRelatedTarget: function(e) {
  return e.relatedTarget;
},

  /**
 * Return the previous sibling that has the specified tagName
 */
 getPreviosSiblingByTagName: function(el,tagName) {
 	var sib=el.previousSibling;
 	while (sib) {
 		if ((sib.tagName==tagName) && (sib.style.display!='none')) return sib;
 		sib=sib.previousSibling;
 	}
 	return null;
 },

/**
 * Return the parent of el that has the specified tagName.
 * @param el DOM node
 * @param tagName tag to search for
 * @param className optional
 */
getParentByTagName: function(el,tagName,className) {
  var par=el;
  tagName=tagName.toLowerCase();
  while (par) {
    if (par.tagName && par.tagName.toLowerCase()==tagName) {
      if (!className || par.className.indexOf(className)>=0) return par;
    }
  	par=par.parentNode;
  }
  return null;
},

/**
 * Wrap the children of a DOM element in a new element
 * @param el the element whose children are to be wrapped
 * @param cls class name of the wrapper (optional)
 * @param id id of the wrapper (optional)
 * @param wrapperTag type of wrapper element to be created (optional, defaults to DIV)
 * @returns new wrapper element
 */
wrapChildren: function(el,cls,id,wrapperTag) {
  var wrapper = document.createElement(wrapperTag || 'div');
  if (id) wrapper.id=id;
  if (cls) wrapper.className=cls;
  while (el.firstChild) {
    wrapper.appendChild(el.firstChild);
  }
  el.appendChild(wrapper);
  return wrapper;
},

/**
 * Positions ctl over icon
 * @param ctl (div with position:absolute)
 * @param icon element (img, button, etc) that ctl should be displayed next to
 */
positionCtlOverIcon: function(ctl,icon) {
  icon=this.$(icon);
  var offsets=this.cumulativeOffset(icon);
  var scrTop=this.docScrollTop();
  var winHt=this.windowHeight();
  if (ctl.style.display=='none') ctl.style.display='block';
  var correction=this.isIE ? 1 : 2;  // based on a 1px border
  var lpad=this.nan2zero(this.getStyle(icon,'paddingLeft'));
  ctl.style.left = (offsets.left+lpad+correction)+'px';
  var newTop=offsets.top + correction;// + scrTop;
  var ctlht=ctl.offsetHeight;
  var iconht=icon.offsetHeight;
  var margin=10;  // account for shadow
  if (newTop+iconht+ctlht+margin < winHt+scrTop) {
    newTop+=iconht;  // display below icon
  } else {
    newTop=Math.max(newTop-ctlht,scrTop);  // display above icon
  }
  ctl.style.top = newTop+'px';
},

/**
 * Creates a form element
 * @param parent new element will be appended to this node
 * @param elemTag element to be created (input, button, select, textarea, ...)
 * @param elemType for input tag this specifies the type (checkbox, radio, text, ...)
 * @param id id for new element
 * @param name name for new element, if not specified then name will be the same as the id
 * @returns new element
 */
createFormField: function(parent,elemTag,elemType,id,name) {
  var field;
  if (typeof name!='string') name=id;
  if (this.isIE) {
    // IE cannot set NAME attribute on dynamically created elements
    var s=elemTag+' id="'+id+'"';
    if (elemType) {
      s+=' type="'+elemType+'"';
    }
    if (elemTag.match(/^(form|input|select|textarea|object|button|img)$/)) {
      s+=' name="'+name+'"';
    }
    field=document.createElement('<'+s+' />');
  } else {
    field=document.createElement(elemTag);
    if (elemType) {
      field.type=elemType;
    }
    field.id=id;
    if (typeof field.name=='string') {
      field.name=name;
    }
  }
  parent.appendChild(field);
  return field;
},

/**
 * Adds a new option to the end of a select list
 * @returns new option element
 */
addSelectOption: function(elem,value,text) {
  var opt=document.createElement('option');
  if (typeof value=='string') opt.value=value;
  opt.text=text;
  if (this.isIE) {
    elem.add(opt);
  } else {
    elem.add(opt,null);
  }
  return opt;
},

/**
 * @returns the value of the specified cookie (or null if it doesn't exist)
 */
getCookie: function(itemName) {
  var arg = itemName+'=';
  var alen = arg.length;
  var clen = document.cookie.length;
  var i = 0;
  while (i < clen) {
    var j = i + alen;
    if (document.cookie.substring(i, j) == arg) {
      var endstr = document.cookie.indexOf (';', j);
      if (endstr == -1) {
        endstr=document.cookie.length;
      }
      return unescape(document.cookie.substring(j, endstr));
    }
    i = document.cookie.indexOf(' ', i) + 1;
    if (i == 0) break;
  }
  return null;
},

/**
 * Write information to a cookie.
 * For cookies to be retained for the current session only, set daysToKeep=null.
 * To erase a cookie, pass a negative daysToKeep value.
 * @see <a href="http://www.quirksmode.org/js/cookies.html">Quirksmode article</a> for more information about cookies.
 */
setCookie: function(itemName,itemValue,daysToKeep,cookiePath,cookieDomain) {
	var c = itemName+"="+escape(itemValue);
	if (typeof(daysToKeep)=='number') {
		var date = new Date();
		date.setTime(date.getTime()+(daysToKeep*24*60*60*1000));
		c+="; expires="+date.toGMTString();
	}
	if (typeof(cookiePath)=='string') {
    c+="; path="+cookiePath;
  }
	if (typeof(cookieDomain)=='string') {
    c+="; domain="+cookieDomain;
  }
  document.cookie = c;
},

phrasesById : {},
/** thousands separator for number formatting */
thouSep : ",",
/** decimal point for number formatting */
decPoint: ".",
/** target language (2 character code) */
langCode: "en",
/** date format */
dateFmt : "mm/dd/yyyy",
/** time format */
timeFmt : "hh:nn:ss a/pm",
/** month name array (Jan is at index 0) */
monthNames: ['January','February','March','April','May','June',
             'July','August','September','October','November','December'],
/** day of week array (Sunday is at index 0) */
dayNames: ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'],

/**
 * @param monthIdx 0-11
 * @returns 3 character abbreviation
 */
monthAbbr: function(monthIdx) {
  return this.monthNames[monthIdx].substr(0,3);
},

/**
 * @param dayIdx 0-6 (Sunday=0)
 * @returns 3 character day of week abbreviation
 */
dayAbbr: function(dayIdx) {
  return this.dayNames[dayIdx].substr(0,3);
},

addPhraseId: function(phraseId, phrase) {
  this.phrasesById[phraseId]=phrase;
},

getPhraseById: function(phraseId) {
  var phrase=this.phrasesById[phraseId];
  if (!phrase) {
    alert('Error: missing phrase for '+phraseId);
    return '';
  }
  if (arguments.length <= 1) return phrase;
  var a=arguments;
  return phrase.replace(/(\$\d)/g,
    function($1) {
      var idx=parseInt($1.charAt(1),10);
      return (idx < a.length) ? a[idx] : '';
    }
  );
},

/**
 * Format a positive number (integer or float)
 * @param posnum number to format
 * @param decPlaces the number of digits to display after the decimal point
 * @param thouSep the character to use as the thousands separator
 * @param decPoint the character to use as the decimal point
 * @returns formatted string
 */
formatPosNumber: function(posnum,decPlaces,thouSep,decPoint) {
  var a=posnum.toFixed(decPlaces).split(/\./);
  if (thouSep) {
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(a[0])) {
      a[0]=a[0].replace(rgx, '$1'+thouSep+'$2');
    }
  }
  return a.join(decPoint);
},

/**
 * Format a number according to the specs in fmt object.
 * @returns string, wrapped in a span element with a class of: negNumber, zeroNumber, posNumber
 * These classes can be set in CSS to display negative numbers in red, for example.
 *
 * @param n number to be formatted
 * @param fmt may contain any of the following:<dl>
 *   <dt>multiplier </dt><dd> the original number is multiplied by this amount before formatting</dd>
 *   <dt>decPlaces  </dt><dd> number of digits to the right of the decimal point</dd>
 *   <dt>decPoint   </dt><dd> character to be used as the decimal point</dd>
 *   <dt>thouSep    </dt><dd> character to use as the thousands separator</dd>
 *   <dt>prefix     </dt><dd> string added to the beginning of the result (e.g. a currency symbol)</dd>
 *   <dt>suffix     </dt><dd> string added to the end of the result (e.g. % symbol)</dd>
 *   <dt>negSign    </dt><dd> specifies format for negative numbers: L=leading minus, T=trailing minus, P=parens</dd>
 *</dl>
 */
formatNumber : function(n,fmt) {
  if (typeof n=='string') n=parseFloat(n.replace(/,/,'.'),10);
  if (isNaN(n)) return 'NaN';
  if (typeof fmt.multiplier=='number') n*=fmt.multiplier;
  var decPlaces=typeof fmt.decPlaces=='number' ? fmt.decPlaces : 0;
  var thouSep=typeof fmt.thouSep=='string' ? fmt.thouSep : this.thouSep;
  var decPoint=typeof fmt.decPoint=='string' ? fmt.decPoint : this.decPoint;
  var prefix=fmt.prefix || "";
  var suffix=fmt.suffix || "";
  var negSign=typeof fmt.negSign=='string' ? fmt.negSign : "L";
  negSign=negSign.toUpperCase();
  var s,cls;
  if (n<0.0) {
    s=this.formatPosNumber(-n,decPlaces,thouSep,decPoint);
    if (negSign=="P") s="("+s+")";
    s=prefix+s;
    if (negSign=="L") s="-"+s;
    if (negSign=="T") s+="-";
    cls='negNumber';
  } else {
    cls=n==0.0 ? 'zeroNumber' : 'posNumber';
    s=prefix+this.formatPosNumber(n,decPlaces,thouSep,decPoint);
  }
  return "<span class='"+cls+"'>"+s+suffix+"</span>";
},

/**
 * Converts a date to a string according to specs in fmt
 * @returns formatted string
 * @param d date to be formatted
 * @param fmt string specifying the output format, may be one of the following:<dl>
 * <dt>locale or localeDateTime</dt>
 *   <dd>use javascript's built-in toLocaleString() function</dd>
 * <dt>localeDate</dt>
 *   <dd>use javascript's built-in toLocaleDateString() function</dd>
 * <dt>translate or translateDateTime</dt>
 *   <dd>use the formats specified in the Rico.dateFmt and Rico.timeFmt properties</dd>
 * <dt>translateDate</dt>
 *   <dd>use the date format specified in the Rico.dateFmt property</dd>
 * <dt>Otherwise</dt>
 *   <dd>Any combination of: yyyy, yy, mmmm, mmm, mm, m, hh, h, HH, H, nn, ss, a/p</dd>
 *</dl>
 */
formatDate : function(d,fmt) {
  var datefmt=(typeof fmt=='string') ? fmt : 'translateDate';
  switch (datefmt) {
    case 'locale':
    case 'localeDateTime':
      return d.toLocaleString();
    case 'localeDate':
      return d.toLocaleDateString();
    case 'translate':
    case 'translateDateTime':
      datefmt=this.dateFmt+' '+this.timeFmt;
      break;
    case 'translateDate':
      datefmt=this.dateFmt;
      break;
  }
  return datefmt.replace(/(yyyy|yy|mmmm|mmm|mm|dddd|ddd|dd|hh|nn|ss|a\/p)/gi,
    function($1) {
      var h;
      switch ($1) {
      case 'yyyy': return d.getFullYear();
      case 'yy':   return d.getFullYear().toString().substr(2);
      case 'mmmm': return Rico.monthNames[d.getMonth()];
      case 'mmm':  return Rico.monthAbbr(d.getMonth());
      case 'mm':   return Rico.zFill(d.getMonth() + 1, 2);
      case 'm':    return (d.getMonth() + 1);
      case 'dddd': return Rico.dayNames[d.getDay()];
      case 'ddd':  return Rico.dayAbbr(d.getDay());
      case 'dd':   return Rico.zFill(d.getDate(), 2);
      case 'd':    return d.getDate();
      case 'hh':   return Rico.zFill((h = d.getHours() % 12) ? h : 12, 2);
      case 'h':    return ((h = d.getHours() % 12) ? h : 12);
      case 'HH':   return Rico.zFill(d.getHours(), 2);
      case 'H':    return d.getHours();
      case 'nn':   return Rico.zFill(d.getMinutes(), 2);
      case 'ss':   return Rico.zFill(d.getSeconds(), 2);
      case 'a/p':  return d.getHours() < 12 ? 'a' : 'p';
      }
    }
  );
},

/**
 * Converts a string in ISO 8601 format to a date object.
 * @returns date object, or false if string is not a valid date or date-time.
 * @param string value to be converted
 * @param offset can be used to bias the conversion and must be in minutes if provided
 * @see Based on <a href='http://delete.me.uk/2005/03/iso8601.html'>delete.me.uk article</a>
 */
setISO8601 : function (string,offset) {
  if (!string) return false;
  var d = string.match(/(\d\d\d\d)(?:-?(\d\d)(?:-?(\d\d)(?:[T ](\d\d)(?::?(\d\d)(?::?(\d\d)(?:\.(\d+))?)?)?(Z|(?:([-+])(\d\d)(?::?(\d\d))?)?)?)?)?)?/);
  if (!d) return false;
  if (!offset) offset=0;
  var date = new Date(d[1], 0, 1);

  if (d[2]) { date.setMonth(d[2] - 1); }
  if (d[3]) { date.setDate(d[3]); }
  if (d[4]) { date.setHours(d[4]); }
  if (d[5]) { date.setMinutes(d[5]); }
  if (d[6]) { date.setSeconds(d[6]); }
  if (d[7]) { date.setMilliseconds(Number("0." + d[7]) * 1000); }
  if (d[8]) {
      if (d[10] && d[11]) {
        offset = (Number(d[10]) * 60) + Number(d[11]);
      }
      offset *= ((d[9] == '-') ? 1 : -1);
      offset -= date.getTimezoneOffset();
  }
  var time = (Number(date) + (offset * 60 * 1000));
  date.setTime(Number(time));
  return date;
},

/**
 * Convert date to an ISO 8601 formatted string.
 * @param date date object to be converted
 * @param format an integer in the range 1-6 (default is 6):<dl>
 * <dt>1 (year)</dt>
 *   <dd>YYYY (eg 1997)</dd>
 * <dt>2 (year and month)</dt>
 *   <dd>YYYY-MM (eg 1997-07)</dd>
 * <dt>3 (complete date)</dt>
 *   <dd>YYYY-MM-DD (eg 1997-07-16)</dd>
 * <dt>4 (complete date plus hours and minutes)</dt>
 *   <dd>YYYY-MM-DDThh:mmTZD (eg 1997-07-16T19:20+01:00)</dd>
 * <dt>5 (complete date plus hours, minutes and seconds)</dt>
 *   <dd>YYYY-MM-DDThh:mm:ssTZD (eg 1997-07-16T19:20:30+01:00)</dd>
 * <dt>6 (complete date plus hours, minutes, seconds and a decimal
 *   fraction of a second)</dt>
 *   <dd>YYYY-MM-DDThh:mm:ss.sTZD (eg 1997-07-16T19:20:30.45+01:00)</dd>
 *</dl>
 * @see Based on: <a href='http://www.codeproject.com/jscript/dateformat.asp'>codeproject.com article</a>
 */
toISO8601String : function (date, format, offset) {
  if (!format) format=6;
  if (!offset) {
      offset = 'Z';
  } else {
      var d = offset.match(/([-+])([0-9]{2}):([0-9]{2})/);
      var offsetnum = (Number(d[2]) * 60) + Number(d[3]);
      offsetnum *= ((d[1] == '-') ? -1 : 1);
      date = new Date(Number(Number(date) + (offsetnum * 60000)));
  }

  var zeropad = function (num) { return ((num < 10) ? '0' : '') + num; };

  var str = date.getUTCFullYear();
  if (format > 1) { str += "-" + zeropad(date.getUTCMonth() + 1); }
  if (format > 2) { str += "-" + zeropad(date.getUTCDate()); }
  if (format > 3) {
      str += "T" + zeropad(date.getUTCHours()) +
             ":" + zeropad(date.getUTCMinutes());
  }
  if (format > 5) {
    var secs = Number(date.getUTCSeconds() + "." +
               ((date.getUTCMilliseconds() < 100) ? '0' : '') +
               zeropad(date.getUTCMilliseconds()));
    str += ":" + zeropad(secs);
  } else if (format > 4) {
    str += ":" + zeropad(date.getUTCSeconds());
  }

  if (format > 3) { str += offset; }
  return str;
}

};

Rico.ajaxUpdater = function(elem,url,options) {
  this.updateSend(elem,url,options);
};

Rico.ajaxUpdater.prototype = {
  updateSend : function(elem,url,options) {
    this.element=elem;
    this.onComplete=options.onComplete;
    options.onComplete=Rico.bind(this,'updateComplete');
    new Rico.ajaxRequest(url,options);
  },

  updateComplete : function(xhr) {
    this.element.innerHTML=xhr.responseText;
    if (this.onComplete) this.onComplete(xhr);
  }
};

Rico.writeDebugMsg=Rico.log;  // for backwards compatibility

Rico.init();
