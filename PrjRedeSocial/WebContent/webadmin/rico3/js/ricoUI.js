/*
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

Rico.Shim = function(DivRef) {
  this.initialize(DivRef);
}

if (Rico.isIE && Rico.ieVersion < 7) {
  Rico.Shim.prototype = {
/**
 * @class Fixes select control bleed-thru on floating divs in IE. Used by Rico.Popup.
 * @see Based on <a href='http://www.dotnetjunkies.com/WebLog/jking/archive/2003/07/21/488.aspx'>technique published by Joe King</a>
 * @constructs
 */
    initialize: function(DivRef) {
      this.ifr = document.createElement('iframe');
      this.ifr.style.position="absolute";
      this.ifr.style.display = "none";
      this.ifr.style.top     = '0px';
      this.ifr.style.left    = '0px';
      this.ifr.src="javascript:false;";
      DivRef.parentNode.appendChild(this.ifr);
      this.DivRef=DivRef;
    },

    hide: function() {
      this.ifr.style.display = "none";
    },

    move: function() {
      this.ifr.style.top  = this.DivRef.style.top;
      this.ifr.style.left = this.DivRef.style.left;
    },

    show: function() {
      this.ifr.style.width   = this.DivRef.offsetWidth;
      this.ifr.style.height  = this.DivRef.offsetHeight;
      this.move();
      this.ifr.style.zIndex  = this.DivRef.currentStyle.zIndex - 1;
      this.ifr.style.display = "block";
    }
  };
} else {
  Rico.Shim.prototype = {
/** @ignore */
    initialize: function() {},
/** @ignore */
    hide: function() {},
/** @ignore */
    move: function() {},
/** @ignore */
    show: function() {}
  };
}


Rico.Shadow = function(DivRef) {
  this.initialize(DivRef);
}

Rico.Shadow.prototype = {
/**
 * @class Creates a shadow for positioned elements. Used by Rico.Popup.
 * Uses blur filter in IE, and alpha-transparent png images for all other browsers.
 * @see Based on <a href='http://www.positioniseverything.net/articles/dropshadows.html'>positioniseverything article</a>
 * @constructs
 */
  initialize: function(DivRef) {
    this.div = document.createElement('div');
    this.div.style.position="absolute";
    this.div.style.top='0px';
    this.div.style.left='0px';
    // testing for filter property fails on Opera
    if (Rico.isIE) {
      this.div.style.backgroundColor='#888';
      this.div.style.filter='progid:DXImageTransform.Microsoft.Blur(makeShadow=1, shadowOpacity=0.3, pixelRadius=3)';
      this.offset=0; // MS blur filter already does offset
    } else {
      this.createShadow();
      this.offset=6;
    }
    this.div.style.display = "none";
    DivRef.parentNode.appendChild(this.div);
    this.DivRef=DivRef;
  },

  createShadow: function() {
    var tab = document.createElement('table');
    tab.style.height='100%';
    tab.style.width='100%';
    tab.cellSpacing=0;
    tab.dir='ltr';

    var tr1=tab.insertRow(-1);
    tr1.style.height='8px';
    var td11=tr1.insertCell(-1);
    td11.style.width='8px';
    var td12=tr1.insertCell(-1);
    td12.style.background="transparent url("+Rico.imgDir+"shadow_ur.png"+") no-repeat right bottom";

    var tr2=tab.insertRow(-1);
    var td21=tr2.insertCell(-1);
    td21.style.background="transparent url("+Rico.imgDir+"shadow_ll.png"+") no-repeat right bottom";
    var td22=tr2.insertCell(-1);
    td22.style.background="transparent url("+Rico.imgDir+"shadow.png"+") no-repeat right bottom";

    this.div.appendChild(tab);
  },

  hide: function() {
    this.div.style.display = "none";
  },

  move: function() {
    this.div.style.top  = (parseInt(this.DivRef.style.top || '0',10)+this.offset)+'px';
    this.div.style.left = (parseInt(this.DivRef.style.left || '0',10)+this.offset)+'px';
  },

  show: function() {
    this.div.style.width = this.DivRef.offsetWidth + 'px';
    this.div.style.height= this.DivRef.offsetHeight + 'px';
    this.move();
    this.div.style.zIndex= parseInt(Rico.getStyle(this.DivRef,'z-index'),10) - 1;
    this.div.style.display = "block";
  }
};


Rico.Popup = function(DivRef,options) {
  this.initialize(DivRef,options);
};

Rico.Popup.prototype = {
/**
 * @class Class to manage pop-up div windows.
 * @constructs
 * @param options object may contain any of the following:<dl>
 *   <dt>hideOnEscape</dt><dd> hide popup when escape key is pressed? default=true</dd>
 *   <dt>hideOnClick </dt><dd> hide popup when mouse button is clicked? default=true</dd>
 *   <dt>ignoreClicks</dt><dd> if true, mouse clicks within the popup are not allowed to bubble up to parent elements</dd>
 *   <dt>position    </dt><dd> defaults to absolute, use "auto" to auto-detect</dd>
 *   <dt>shadow      </dt><dd> display shadow with popup? default=true</dd>
 *   <dt>margin      </dt><dd> number of pixels to allow for shadow, default=6</dd>
 *   <dt>zIndex      </dt><dd> which layer? default=1</dd>
 *   <dt>canDrag     </dt><dd> boolean value (or function that returns a boolean) indicating if it is ok to drag/reposition popup, default=false</dd>
 *</dl>
 * @param DivRef if supplied, then setDiv() is called at the end of initialization
 */
  initialize: function(DivRef,options) {
    this.options = {
      hideOnEscape  : true,
      hideOnClick   : true,
      ignoreClicks  : false,
      position      : 'absolute',
      shadow        : true,
      margin        : 6,
      zIndex        : 1,
      canDrag       : false,
      dragElement   : false,
      closeFunc     : false
    };
    if (DivRef) this.setDiv(DivRef,options);
  },

/**
 * Apply popup behavior to a div that already exists in the DOM
 * @param DivRef div element in the DOM
 */
  setDiv: function(DivRef,options) {
    Rico.extend(this.options, options || {});
    this.divPopup=typeof(DivRef)=='string' ? document.getElementById(DivRef) : DivRef;
    if (!this.divPopup) return;
    if (this.options.position == 'auto') {
      this.position=Rico.getStyle(this.divPopup,'position').toLowerCase();
    } else {
      this.position=this.divPopup.style.position=this.options.position;
    }
    if (this.position != 'absolute') return;
    if (this.options.zIndex >= 0) this.divPopup.style.zIndex=this.options.zIndex;
    this.closeFunc=this.options.closeFunc || Rico.bind(this,'closePopup');
    this.shim=new Rico.Shim(this.divPopup);
    if (this.options.shadow)
      this.shadow=new Rico.Shadow(this.divPopup);
    if (this.options.hideOnClick)
      Rico.eventBind(document,"click", Rico.eventHandle(this,'_docClick'));
    if (this.options.hideOnEscape)
      Rico.eventBind(document,"keyup", Rico.eventHandle(this,'_checkKey'));
    this.dragEnabled=false;
    this.mousedownHandler = Rico.eventHandle(this,'_startDrag');
    this.dragHandler = Rico.eventHandle(this,'_drag');
    this.dropHandler = Rico.eventHandle(this,'_endDrag');
    if (this.options.canDrag) this.enableDragging();
    if (this.options.ignoreClicks || this.options.canDrag) this.ignoreClicks();
  },
  
  enableDragging: function() {
    if (!this.dragEnabled && this.options.dragElement) {
      Rico.eventBind(this.options.dragElement, "mousedown", this.mousedownHandler);
      this.dragEnabled=true;
    }
    return this.dragEnabled;
  },
  
  disableDragging: function() {
    if (!this.dragEnabled) return;
    Rico.eventUnbind(this.options.dragElement, "mousedown", this.mousedownHandler);
    this.dragEnabled=false;
  },
  
  setZ: function(zIndex) {
    this.divPopup.style.zIndex=zIndex;
  },

/** @private */
  ignoreClicks: function() {
    Rico.eventBind(this.divPopup,"click", Rico.eventHandle(this,'_ignoreClick'));
  },

  _ignoreClick: function(e) {
    if (e.stopPropagation)
      e.stopPropagation();
    else
      e.cancelBubble = true;
    return true;
  },

  // event handler to process keyup events (hide menu on escape key)
  _checkKey: function(e) {
    if (Rico.eventKey(e)==27) this.closeFunc();
    return true;
  },

  _docClick: function(e) {
    this.closeFunc();
    return true;
  },

/**
 * Move popup to specified position
 */
  move: function(left,top) {
    if (typeof left=='number') this.divPopup.style.left=left+'px';
    if (typeof top=='number') this.divPopup.style.top=top+'px';
    if (this.shim) this.shim.move();
    if (this.shadow) this.shadow.move();
  },

  _startDrag : function(event){
    var elem=Rico.eventElement(event);
    this.divPopup.style.cursor='move';
    this.lastMouse = Rico.eventClient(event);
    Rico.eventBind(document, "mousemove", this.dragHandler);
    Rico.eventBind(document, "mouseup", this.dropHandler);
    Rico.eventStop(event);
  },

  _drag : function(event){
    var newMouse = Rico.eventClient(event);
    var newLeft = parseInt(this.divPopup.style.left,10) + newMouse.x - this.lastMouse.x;
    var newTop = parseInt(this.divPopup.style.top,10) + newMouse.y - this.lastMouse.y;
    this.move(newLeft, newTop);
    this.lastMouse = newMouse;
    Rico.eventStop(event);
  },

  _endDrag : function(){
    this.divPopup.style.cursor='';
    Rico.eventUnbind(document, "mousemove", this.dragHandler);
    Rico.eventUnbind(document, "mouseup", this.dropHandler);
  },

/**
 * Display popup at specified position
 */
  openPopup: function(left,top) {
    this.divPopup.style.display=this.position=='absolute' ? "block" : Rico.isIE && Rico.ieVersion<8 ? "inline" : "inline-block";
    if (typeof left=='number') this.divPopup.style.left=left+'px';
    if (typeof top=='number') this.divPopup.style.top=top+'px';
    if (this.divPopup.id) Rico.log('openPopup '+this.divPopup.id+' at '+left+','+top);
    if (this.shim) this.shim.show();
    if (this.shadow) this.shadow.show();
  },

  centerPopup: function() {
    this.openPopup();
    var msgWidth=this.divPopup.offsetWidth;
    var msgHeight=this.divPopup.offsetHeight;
    var divwi=this.divPopup.parentNode.offsetWidth;
    var divht=this.divPopup.parentNode.offsetHeight;
    this.move(parseInt((divwi-msgWidth)/2,10), parseInt((divht-msgHeight)/2,10));
  },
  
  visible: function() {
    return Rico.visible(this.divPopup.style.display);
  },

/**
 * Hide popup
 */
  closePopup: function() {
    if (this.divPopup.id) Rico.log('closePopup '+this.divPopup.id);
    if (this.dragEnabled) this._endDrag();
    if (this.shim) this.shim.hide();
    if (this.shadow) this.shadow.hide();
    this.divPopup.style.display="none";
  }

};

Rico.closeButton = function(handle) {
  var a = document.createElement('a');
  a.className='RicoCloseAnchor';
  if (Rico.theme.closeAnchor) Rico.addClass(a,Rico.theme.closeAnchor);
  var span = a.appendChild(document.createElement('span'));
  span.title=Rico.getPhraseById('close');
  new Rico.HoverSet([a]);
  Rico.addClass(span,Rico.theme.close || 'RicoClose');
  Rico.eventBind(a,"click", handle);
  return a;
};

Rico.floatButton = function(buttonName, handle, title) {
  var a=document.createElement("a");
  a.className='RicoButtonAnchor'
  Rico.addClass(a,Rico.theme.buttonAnchor || 'RicoButtonAnchorNative');
  var span=a.appendChild(document.createElement("span"));
  if (title) span.title=title;
  span.className=Rico.theme[buttonName.toLowerCase()] || 'Rico'+buttonName;
  Rico.eventBind(a,"click", handle, false);
  new Rico.HoverSet([a]);
  return a
}

Rico.clearButton = function(handle) {
  var span=document.createElement("span");
  span.title=Rico.getPhraseById('clear');
  span.className='ricoClear';
  Rico.addClass(span, Rico.theme.clear || 'ricoClearNative');
  span.style.display='inline-block';
  span.style.cursor='pointer';
  Rico.eventBind(span,"click", handle);
  return span;
}

Rico.Window = function(div, title, options) {
  this.initialize(div, title, options);
};

Rico.Window.prototype = {

/**
 * Create popup div with a title bar.
 */
  initialize: function(div, title, options) {
    div=Rico.$(div);
    options=options || {overflow:'auto'};
    Rico.extend(this, new Rico.Popup());
    this.options.canDrag=true;
    this.options.hideOnClick=false;
    this.titleDiv = document.createElement('div');
    this.options.dragElement=this.titleDiv;
    this.contentDiv = Rico.wrapChildren(div,'ricoContent');
    this.titleDiv.className='ricoTitle';
    if (Rico.theme.dialogTitle) Rico.addClass(this.titleDiv,Rico.theme.dialogTitle);
    this.titleDiv.style.position='relative';
    div.appendChild(this.titleDiv);
    div.appendChild(this.contentDiv);
    this.titleContent = document.createElement('span');
    this.titleDiv.appendChild(this.titleContent);
    this.titleDiv.appendChild(Rico.closeButton(Rico.eventHandle(this,'closePopup')));
    this.contentDiv.style.position='relative';
    if (Rico.theme.dialogContent) Rico.addClass(this.contentDiv,Rico.theme.dialogContent);
    if (options.height) this.contentDiv.style.height=options.height;
    if (options.width) this.contentDiv.style.width=options.width;
    if (options.overflow) this.contentDiv.style.overflow=options.overflow;
    div.style.top='0px';
    div.style.left='0px';
    div.style.display='none';
    div.style.width='auto';
    Rico.addClass(div,'ricoWindow');
    if (Rico.theme.dialog) Rico.addClass(div,Rico.theme.dialog);
    if (Rico.theme.content) Rico.addClass(div,Rico.theme.content);
    this.setTitle(title || div.title || '&nbsp;');
    div.title='';
    this.setDiv(div,options);
  },

  setContent: function(contentString) {
    this.contentDiv.innerHTML=contentString;
  },
  
  setTitle: function(title) {
    this.titleContent.innerHTML=title;
  }

}


Rico.Menu = function(options) {
  this.initialize(options);
}

Rico.Menu.prototype = {
/**
 * @class Implements popup menus and submenus
 * @extends Rico.Popup
 * @constructs
 */
  initialize: function(options) {
    Rico.extend(this, new Rico.Popup());
    Rico.extend(this.options, {
      width        : "15em"
    });
    if (typeof options=='string')
      this.options.width=options;
    else
      Rico.extend(this.options, options || {});
    this.hideFunc=null;
    this.highlightElem=null;
    new Image().src = Rico.imgDir+'left_b.gif';
    new Image().src = Rico.imgDir+'right_b.gif';
  },
  
  createDiv: function(parentNode) {
    if (this.div) return;
    this.div = document.createElement('div');
    this.div.className = Rico.isWebKit ? 'ricoMenuSafari' : 'ricoMenu';
    this.div.style.position="absolute";
    this.div.style.top='0px';
    this.div.style.left='0px';
    this.div.style.width=this.options.width;
    if (!parentNode) parentNode = document.getElementsByTagName("body")[0];
    parentNode.appendChild(this.div);
    this.width=this.div.offsetWidth;
    this.setDiv(this.div,{closeFunc:Rico.bind(this,'cancelmenu')});
    this.direction=Rico.getStyle(this.div,'direction') || 'ltr';
    this.direction=this.direction.toLowerCase();  // ltr or rtl
    this.hidemenu();
    this.itemCount=0;
  },
  
  showmenu: function(e,hideFunc){
    Rico.eventStop(e);
    this.hideFunc=hideFunc;
    if (this.div.childNodes.length==0) {
      this.cancelmenu();
      return false;
    }
    var mousePos = Rico.eventClient(e);
    this.openmenu(mousePos.x,mousePos.y,0,0);
  },
  
  openmenu: function(x,y,clickItemWi,clickItemHt,noOffset) {
    var newLeft=x + (noOffset ? 0 : Rico.docScrollLeft());
    //window.status='openmenu: newLeft='+newLeft+' width='+this.width+' windowWi='+Rico.windowWidth();
    if (this.direction == 'rtl') {
      if (newLeft > this.width+clickItemWi) newLeft-=this.width+clickItemWi;
    } else {
      if (x+this.width+this.options.margin > Rico.windowWidth()) newLeft-=this.width+clickItemWi;
    }
    var scrTop=Rico.docScrollTop();
    var newTop=y + (noOffset ? 0 : scrTop);
    this.div.style.visibility="hidden";
    this.div.style.display="block";
    var contentHt=this.div.offsetHeight;
    if (y+contentHt+this.options.margin-scrTop > Rico.windowHeight())
      newTop=Math.max(newTop-contentHt+clickItemHt,0);
    this.openPopup(newLeft,newTop);
    this.div.style.visibility ="visible";
    return false;
  },

  clearMenu: function() {
    this.div.innerHTML="";
    this.defaultAction=null;
    this.itemCount=0;
  },

  addMenuHeading: function(hdg) {
    var el=document.createElement('div');
    el.innerHTML=hdg;
    el.className='ricoMenuHeading';
    this.div.appendChild(el);
  },

  addMenuBreak: function() {
    var brk=document.createElement('div');
    brk.className="ricoMenuBreak";
    this.div.appendChild(brk);
  },

  addSubMenuItem: function(menutext, submenu, translate) {
    var dir=this.direction=='rtl' ? 'left' : 'right';
    var a=this.addMenuItem(menutext,null,true,null,translate);
    a.className='ricoSubMenu';
    a.style.backgroundImage='url('+Rico.imgDir+dir+'_b.gif)';
    a.style.backgroundRepeat='no-repeat';
    a.style.backgroundPosition=dir;
    a.RicoSubmenu=submenu;
    Rico.eventBind(a,"mouseover", Rico.eventHandle(this,'showSubMenu'));
    Rico.eventBind(a,"mouseout", Rico.eventHandle(this,'subMenuOut'));
  },
  
  showSubMenu: function(e) {
    if (this.openSubMenu) this.hideSubMenu();
    var a=Rico.eventElement(e);
    this.openSubMenu=a.RicoSubmenu;
    this.openMenuAnchor=a;
    if (a.className=='ricoSubMenu') a.className='ricoSubMenuOpen';
    a.RicoSubmenu.openmenu(parseInt(a.parentNode.style.left)+a.offsetWidth, parseInt(a.parentNode.style.top)+a.offsetTop, a.offsetWidth-2, a.offsetHeight+2,true);
  },
  
  subMenuOut: function(e) {
    if (!this.openSubMenu) return;
    Rico.eventStop(e);
    var elem=Rico.eventElement(e);
    var reltg = Rico.eventRelatedTarget(e) || e.toElement;
    try {
      while (reltg != null && reltg != this.openSubMenu.div)
        reltg=reltg.parentNode;
    } catch(err) {}
    if (reltg == this.openSubMenu.div) return;
    this.hideSubMenu();
  },
  
  hideSubMenu: function() {
    if (this.openMenuAnchor) {
      this.openMenuAnchor.className='ricoSubMenu';
      this.openMenuAnchor=null;
    }
    if (this.openSubMenu) {
      this.openSubMenu.hidemenu();
      this.openSubMenu=null;
    }
  },

  addMenuItemId: function(phraseId,action,enabled,title,target) {
    if ( arguments.length < 3 ) enabled=true;
    this.addMenuItem(Rico.getPhraseById(phraseId),action,enabled,title,target);
  },

// if action is a string, then it is assumed to be a URL and the target parm can be used indicate which window gets the content
// action can also be a function -- it may be wrapped using Rico.bind, but not Rico.eventHandle
  addMenuItem: function(menutext,action,enabled,title,target) {
    this.itemCount++;
    var a = document.createElement(typeof action=='string' ? 'a' : 'div');
    if ( arguments.length < 3 || enabled ) {
      if (typeof action=='string') {
        a.href = action; 
        if (target) a.target = target; 
      } else if (target=='event') {
        Rico.eventBind(a,"click", action);
      } else {
        a.onclick=action;
      }
      a.className = 'enabled';
      if (this.defaultAction==null) this.defaultAction=action;
    } else {
      a.disabled = true;
      a.className = 'disabled';
    }
    a.innerHTML = menutext;
    if (typeof title=='string')
      a.title = title;
    a=this.div.appendChild(a);
    Rico.eventBind(a,"mouseover", Rico.eventHandle(this,'mouseOver'));
    Rico.eventBind(a,"mouseout", Rico.eventHandle(this,'mouseOut'));
    return a;
  },
  
  mouseOver: function(e) {
    if (this.highlightElem && this.highlightElem.className=='enabled-hover') {
      // required for Safari
      this.highlightElem.className='enabled';
      this.highlightElem=null;
    }
    var elem=Rico.eventElement(e);
    if (this.openMenuAnchor && this.openMenuAnchor!=elem)
      this.hideSubMenu();
    if (elem.className=='enabled') {
      elem.className='enabled-hover';
      this.highlightElem=elem;
    }
  },

  mouseOut: function(e) {
    var elem=Rico.eventElement(e);
    if (elem.className=='enabled-hover') elem.className='enabled';
    if (this.highlightElem==elem) this.highlightElem=null;
  },

  isVisible: function() {
    return this.div && Rico.visible(this.div);
  },
  
  cancelmenu: function() {
    if (!this.isVisible()) return;
    if (this.hideFunc) this.hideFunc();
    this.hideFunc=null;
    this.hidemenu();
  },

  hidemenu: function() {
    if (!this.div) return;
    if (this.openSubMenu) this.openSubMenu.hidemenu();
    this.closePopup();
  }

}


Rico.SelectionSet = function(selectionSet, options) {
  this.initialize(selectionSet, options);
}

Rico.SelectionSet.prototype = {
/**
 * @class
 * @constructs
 * @param selectionSet collection of DOM elements (or a CSS selection string)
 * @param options object may contain any of the following:<dl>
 *   <dt>selectedClass</dt><dd>class name to add when element is selected, default is "selected"</dd>
 *   <dt>selectNode   </dt><dd>optional function that returns the element to be selected</dd>
 *   <dt>onSelect     </dt><dd>optional function that gets called when element is selected</dd>
 *   <dt>onFirstSelect</dt><dd>optional function that gets called the first time element is selected</dd>
 *   <dt>noDefault    </dt><dd>when true, no element in the set is initially selected, default is false</dd>
 *   <dt>selectedIndex</dt><dd>index of the element that should be initially selected, default is 0</dd>
 *   <dt>cookieName   </dt><dd>optional name of cookie to use to remember selected element. If specified, and the cookie exists, then the cookie value overrides selectedIndex.</dd>
 *   <dt>cookieDays   </dt><dd>specifies how long cookie should persist (in days). If unspecified, then the cookie persists for the current session.</dd>
 *   <dt>cookiePath   </dt><dd>optional cookie path</dd>
 *   <dt>cookieDomain </dt><dd>optional cookie domain</dd>
 *</dl>
 */
	initialize: function(selectionSet, options){
    Rico.log('SelectionSet#initialize');
		this.options = options || {};
    if (typeof selectionSet == 'string')
      selectionSet = Rico.select(selectionSet);
	  this.previouslySelected = [];
		this.selectionSet = [];
		this.selectedClassName = this.options.selectedClass || Rico.theme.selected || "selected";
		this.selectNode = this.options.selectNode || function(e){return e;};
		this.onSelect = this.options.onSelect;
    this.onFirstSelect = this.options.onFirstSelect;
    this.clickHandler = Rico.bind(this,'selectIndex');
    this.selectedIndex=-1;
    for (var i=0; i<selectionSet.length; i++)
      this.add(selectionSet[i]);
    if (!this.options.noDefault) {
		  var cookieIndex=this.options.cookieName ? this.getCookie() : 0;
      this.selectIndex(cookieIndex || this.options.selectedIndex || 0);
    }
	},
  getCookie: function() {
    var cookie = Rico.getCookie(this.options.cookieName);
    if (!cookie) return 0;
    var index = parseInt(cookie);
    return index < this.selectionSet.length ? index : 0;
	},
	reset: function(){
	  this.previouslySelected = [];
	  this._notifySelected(this.selectedIndex);
	},
  clearSelected: function() {
		if (this.selected)
		  Rico.removeClass(this.selectNode(this.selected), this.selectedClassName);
  },
  getIndex: function(element) {
    for (var i=0; i<this.selectionSet.length; i++) {
      if (element == this.selectionSet[i]) return i;
    }
    return -1;
  },
	select: function(element){
		if (this.selected == element) return;
    var i=this.getIndex(element);
    if (i >= 0) this.selectIndex(i);
	},
	_notifySelected: function(index){
    if (index < 0) return;
    var element = this.selectionSet[index];
		if (this.options.cookieName)
      Rico.setCookie(this.options.cookieName, index, this.options.cookieDays, this.options.cookiePath, this.options.cookieDomain);
    if (this.onFirstSelect && !this.previouslySelected[index]){
      this.onFirstSelect(element, index);
      this.previouslySelected[index] = true;
    }
  	if (this.onSelect)
      try{
  	    this.onSelect(element, index);
      } catch (e) {};
	},
	selectIndex: function(index){
		if (this.selectedIndex == index || index >= this.selectionSet.length) return;
    this.clearSelected();
    this._notifySelected(index);
		this.selectedIndex = index;
    this.selected=this.selectionSet[index].element;
    Rico.addClass(this.selectNode(this.selected), this.selectedClassName);
	},
	nextSelectIndex: function(){
    return (this.selectedIndex + 1) % this.selectionSet.length;
	},
	nextSelectItem: function(){
    return this.selectionSet[this.nextSelectIndex()];
	},
	selectNext: function(){
    this.selectIndex(this.nextSelectIndex());
	},
	add: function(item){
    var index=this.selectionSet.length;
    this.selectionSet[index] = new Rico._SelectionItem(item,index,this.clickHandler);
	},
	remove: function(item){
    if (item==this.selected) this.clearSelected();
    var i=this.getIndex(item);
    if (i < 0) return;
    this.selectionSet[i].remove();
    this.selectionSet.splice(i,1);
	},
	removeAll: function(){
    this.clearSelected();
    while (this.selectionSet.length > 0) {
      this.selectionSet.pop().remove();
    }
	}
};


Rico._SelectionItem=function(element,index,callback) {
  this.add(element,index,callback);
};

Rico._SelectionItem.prototype = {
  add: function(element,index,callback) {
    this.element=element;
    this.index=index;
    this.callback=callback;
    this.handle=Rico.eventHandle(this,'click');
    Rico.eventBind(element, "click", this.handle);
  },
  
	click: function(ev) {
		this.callback(this.index);
	},

  remove: function() {
    Rico.eventUnbind(this.element, "click", this.handle);
  }
};

 
Rico.HoverSet = function(hoverSet, options) {
  this.initialize(hoverSet, options);
};

Rico.HoverSet.prototype = {
/**
 * @class
 * @constructs
 * @param hoverSet collection of DOM elements
 * @param options object may contain any of the following:<dl>
 *   <dt>hoverClass</dt><dd> class name to add when mouse is over element, default is "hover"</dd>
 *   <dt>hoverNodes</dt><dd> optional function to select/filter which nodes are in the set</dd>
 *</dl>
 */
  initialize: function(hoverSet, options){
    Rico.log('HoverSet#initialize');
    options = options || {};
    this.hoverClass = options.hoverClass || Rico.theme.hover || "hover";
    this.hoverFunc = options.hoverNodes || function(e){return [e];};
    this.hoverSet=[];
    if (!hoverSet) return;
    for (var i=0; i<hoverSet.length; i++)
      this.add(hoverSet[i]);
  },
  add: function(item) {
    this.hoverSet.push(new Rico._HoverItem(item,this.hoverFunc,this.hoverClass));
  },
	removeAll: function(){
    while (this.hoverSet.length > 0) {
      this.hoverSet.pop().remove();
    }
  }
};


Rico._HoverItem=function(element,selectFunc,hoverClass) {
  this.add(element,selectFunc,hoverClass);
};

Rico._HoverItem.prototype = {
  add: function(element,selectFunc,hoverClass) {
    this.element=element;
    this.selectFunc=selectFunc;
    this.hoverClass=hoverClass;
    this.movehandle=Rico.eventHandle(this,'move');
    this.outhandle=Rico.eventHandle(this,'mouseout');
    Rico.eventBind(element, "mousemove", this.movehandle);
    Rico.eventBind(element, "mouseout", this.outhandle);
  },
  
	move: function(ev) {
		var elems=this.selectFunc(this.element);
    for (var i=0; i<elems.length; i++)
      Rico.addClass(elems[i],this.hoverClass);
	},

	mouseout: function(ev) {
		var elems=this.selectFunc(this.element);
    for (var i=0; i<elems.length; i++)
      Rico.removeClass(elems[i],this.hoverClass);
	},

  remove: function() {
    Rico.eventUnbind(element, "mousemove", this.movehandle);
    Rico.eventUnbind(element, "mouseout", this.outhandle);
  }
};


/** @namespace */
Rico.Effect = {};
Rico.Effect.easeIn = function(step){
  return Math.sqrt(step);
};
Rico.Effect.easeOut = function(step){
  return step*step;
};


/** @class core methods for transition effects */
Rico.ContentTransitionBase = function() {};
Rico.ContentTransitionBase.prototype = {
	initBase: function(titles, contents, options) {
    Rico.log('ContentTransitionBase#initBase');
    if (typeof titles == 'string')
      titles = Rico.select(titles);
    if (typeof contents == 'string')
      contents = Rico.select(contents);

	  this.options = options || {};
    this.titles = titles;
	  this.contents = contents;
	  this.hoverSet = new Rico.HoverSet(titles, options);
    for (var i=0; i<contents.length; i++) {
      if (contents[i]) Rico.hide(contents[i]);
    }
	  this.selectionSet = new Rico.SelectionSet(titles, Rico.extend(options, {onSelect: Rico.bind(this,'select')}));
	},
	reset: function(){
	  this.selectionSet.reset();
	},
	select: function(element,index) {
    Rico.log('ContentTransitionBase#select');
		var panel = this.contents[index];
	  if ( this.selected == panel) return;
		if (this.transition){
			if (this.selected){
			  this.transition(panel);
      } else {
        panel.style.display='block';
      }
		} else {
			if (this.selected) Rico.hide(this.selected);
      panel.style.display='block';
		}
		this.selected = panel;
	},
	addBase: function(title, content){
		this.titles.push(title);
		this.contents.push(content);
		this.hoverSet.add(title);
		this.selectionSet.add(title);
    Rico.hide(content);
		//this.selectionSet.select(title);
	},
	removeBase: function(title){},
	removeAll: function(){
		this.hoverSet.removeAll();
		this.selectionSet.removeAll();
	}
};


/**
 * @class Implements accordion effect
 * @see Rico.ContentTransitionBase#initialize for construction parameters
 * @extends Rico.ContentTransitionBase
 */
Rico.Accordion = function(element, options) {
  this.initialize(element, options);
};

Rico.Accordion.prototype = Rico.extend(new Rico.ContentTransitionBase(), 
/** @lends Rico.Accordion# */
{
  initialize: function(element, options) {
		element=Rico.$(element);
    element.style.overflow='hidden';
    element.className=options.accClass || Rico.theme.accordion || "Rico_accordion";
    var panels=Rico.getDirectChildrenByTag(element,'div');
    var items,titles=[], contents=[];
    for (var i=0; i<panels.length; i++) {
      items=Rico.getDirectChildrenByTag(panels[i],'div');
      if (items.length>=2) {
        items[0].className=options.titleClass || Rico.theme.accTitle || "Rico_accTitle";
        items[1].className=options.contentClass || Rico.theme.accContent || "Rico_accContent";
        titles.push(items[0]);
        contents.push(items[1]);
      }
    }
    this.initBase(titles, contents, options);
    this.selected.style.height = this.options.panelHeight + "px";
    this.totSteps=(typeof options.duration =='number' ? options.duration : 200)/25;
	},
  transition: function(p){
    if (!this.options.noAnimate) {
      this.closing=this.selected;
      this.opening=p;
      this.curStep=0;
      this.timer=setInterval(Rico.bind(this,'step'),25);
    } else {
      p.style.height = this.options.panelHeight + "px";
      if (this.selected) Rico.hide(this.selected);
  		p.style.display='block';
    }
	},
  step: function() {
    this.curStep++;
    var oheight=Math.round(this.curStep/this.totSteps*this.options.panelHeight);
    this.opening.style.height=oheight+'px';
    this.closing.style.height=(this.options.panelHeight - oheight)+'px';
    if (this.curStep==1) {
      this.opening.style.paddingTop=this.opening.style.paddingBottom='0px';
      this.opening.style.display='block';
    }
    if (this.curStep==this.totSteps) {
      clearInterval(this.timer);
      this.opening.style.paddingTop=this.opening.style.paddingBottom='';
      Rico.hide(this.closing);
    }
  },
  setPanelHeight: function(h) {
    this.options.panelHeight = h;
    this.selected.style.height = this.options.panelHeight + "px";
  }
});


/**
 * @class Implements tabbed panel effect
 * @see Rico.ContentTransitionBase#initialize for construction parameters
 * @extends Rico.ContentTransitionBase
 */
Rico.TabbedPanel = function(element, options) {
  this.initialize(element, options);
};

Rico.TabbedPanel.prototype = Rico.extend(new Rico.ContentTransitionBase(),
{
  initialize: function(element, options) {
    element=Rico.$(element);
    options=options || {};
    if (typeof options.panelWidth=='number') options.panelWidth+="px";
    if (typeof options.panelHeight=='number') options.panelHeight+="px";
    element.className=options.tabClass || Rico.theme.tabPanel || "Rico_tabPanel";
    if (options.panelWidth) element.style.width = options.panelWidth;
    var items = [];
    var allKids = element.childNodes;
    for( var i = 0 ; i < allKids.length ; i++ ) {
      if (allKids[i] && allKids[i].tagName && allKids[i].tagName.match(/^div|ul$/i))
        items.push(allKids[i]);
    }
    if (items.length < 2) return;
    var childTag=items[0].tagName.toLowerCase()=='ul' ? 'li' : 'div';
    items[0].className=options.navContainerClass || Rico.theme.tabNavContainer || "Rico_tabNavContainer";
    items[0].style.listStyle='none';
    items[1].className=options.contentContainerClass || Rico.theme.tabContentContainer || "Rico_tabContentContainer";
    var titles=Rico.getDirectChildrenByTag(items[0], childTag);
    var contents=Rico.getDirectChildrenByTag(items[1],'div');
    if (!options.corners) options.corners='top';
    for (var i=0; i<titles.length; i++) {
      titles[i].className=options.titleClass || Rico.theme.tabTitle || "Rico_tabTitle";
      contents[i].className=options.titleClass || Rico.theme.tabContent || "Rico_tabContent";
      if (options.corners!='none') {
        if (options.panelHdrWidth) titles[i].style.width=options.panelHdrWidth;
        Rico.Corner.round(titles[i], Rico.theme.tabCornerOptions || options);
      }
    }
    this.initBase(titles, contents, options);
    if (this.selected) this.transition(this.selected);
  },
  transition: function(p){
    Rico.log('TabbedPanel#transition '+typeof(p));
    if (this.selected) Rico.hide(this.selected);
		Rico.show(p);
    if (this.options.panelHeight) p.style.height = this.options.panelHeight;
  }
});


/**
 * @namespace
 */
Rico.Corner = {

   round: function(e, options) {
      e = Rico.$(e);
      this.options = {
         corners : "all",
         bgColor : "fromParent",
         compact : false,
         nativeCorners: false  // only use native corners
      };
      Rico.extend(this.options, options || {});
      if (Rico.isGecko)
        this._roundCornersGecko(e);
      else if (typeof(Rico.getStyle(e,'-webkit-border-radius'))=='string')
        this._roundCornersWebKit(e);
      else if (!this.options.nativeCorners)
        this._roundCornersImpl(e);
   },

   _roundCornersImpl: function(e) {
      var bgColor = this.options.bgColor == "fromParent" ? this._background(e.parentNode) : this.options.bgColor;
      e.style.position='relative';
      //this.options.numSlices = this.options.compact ? 2 : 4;
      if (this._hasString(this.options.corners, "all", "top", "tl")) this._createCorner(e,'top','left',bgColor);
      if (this._hasString(this.options.corners, "all", "top", "tr")) this._createCorner(e,'top','right',bgColor);
      if (this._hasString(this.options.corners, "all", "bottom", "bl")) this._createCorner(e,'bottom','left',bgColor);
      if (this._hasString(this.options.corners, "all", "bottom", "br")) this._createCorner(e,'bottom','right',bgColor);
   },

   _roundCornersGecko: function(e) {
      var radius=this.options.compact ? '4px' : '8px';
      if (this._hasString(this.options.corners, "all"))
        Rico.setStyle(e, {MozBorderRadius:radius}, true);
      else {
        if (this._hasString(this.options.corners, "top", "tl")) Rico.setStyle(e, {MozBorderRadiusTopleft:radius}, true);
        if (this._hasString(this.options.corners, "top", "tr")) Rico.setStyle(e, {MozBorderRadiusTopright:radius}, true);
        if (this._hasString(this.options.corners, "bottom", "bl")) Rico.setStyle(e, {MozBorderRadiusBottomleft:radius}, true);
        if (this._hasString(this.options.corners, "bottom", "br")) Rico.setStyle(e, {MozBorderRadiusBottomright:radius}, true);
      }
   },

   _roundCornersWebKit: function(e) {
      var radius=this.options.compact ? '4px' : '8px';
      if (this._hasString(this.options.corners, "all"))
        Rico.setStyle(e, {WebkitBorderRadius:radius}, true);
      else {
        if (this._hasString(this.options.corners, "top", "tl")) Rico.setStyle(e, {WebkitBorderTopLeftRadius:radius}, true);
        if (this._hasString(this.options.corners, "top", "tr")) Rico.setStyle(e, {WebkitBorderTopRightRadius:radius}, true);
        if (this._hasString(this.options.corners, "bottom", "bl")) Rico.setStyle(e, {WebkitBorderBottomLeftRadius:radius}, true);
        if (this._hasString(this.options.corners, "bottom", "br")) Rico.setStyle(e, {WebkitBorderBottomRightRadius:radius}, true);
      }
   },
   
  _createCorner: function(elem,tb,lr,bgColor) {
    //alert('Corner: '+tb+' '+lr+' bgColor='+typeof(bgColor));
    var corner = document.createElement("div");
	corner.className='ricoCorner';
    Rico.setStyle(corner,{width:'5px', height:'5px'});
    var borderStyle = Rico.getStyle(elem,'border-'+tb+'-style');
    var borderColor = borderStyle=='none' ? bgColor : Rico.getStyle(elem,'border-'+tb+'-color');
    var pos=borderStyle=='none' ? '0px' : '-1px';
    corner.style[tb]=pos;
    corner.style[lr]=pos;
    elem.appendChild(corner);
    var marginSizes = [ 0, 2, 3, 4, 4 ];
    if (tb=='bottom') marginSizes.reverse();
    var borderVal= borderStyle=='none' ? '0px none' : '1px solid '+borderColor;
    var d= lr=='left' ? 'Right' : 'Left';
    for (var i=0; i<marginSizes.length; i++) {
      var slice = document.createElement("div");
      Rico.setStyle(slice,{backgroundColor:bgColor,height:'1px'});
      slice.style['margin'+d]=marginSizes[i]+'px';
      slice.style['border'+d]=borderVal;
      corner.appendChild(slice);
    }
  },

  _background: function(elem) {
     try {
       var actualColor = Rico.getStyle(elem, "backgroundColor");

       // if color is tranparent, check parent
       // Safari returns "rgba(0, 0, 0, 0)", which means transparent
       if ( actualColor.match(/^(transparent|rgba\(0,\s*0,\s*0,\s*0\))$/i) && elem.parentNode )
          return this._background(elem.parentNode);

       return actualColor == null ? "#ffffff" : actualColor;
     } catch(err) {
       return "#ffffff";
     }
   },

   _hasString: function(str) {
     for(var i=1 ; i<arguments.length ; i++) {
       if (str.indexOf(arguments[i]) >= 0) return true;
     }
     return false;
   }

};

Rico.toColorPart = function(c) {
  return Rico.zFill(c, 2, 16);
};


Rico.Color = function(red, green, blue) {
  this.initialize(red, green, blue);
};

Rico.Color.prototype = {
/**
 * @class Methods to manipulate color values.
 * @constructs
 * @param red integer (0-255)
 * @param green integer (0-255)
 * @param blue integer (0-255)
 */
   initialize: function(red, green, blue) {
      this.rgb = { r: red, g : green, b : blue };
   },

   setRed: function(r) {
      this.rgb.r = r;
   },

   setGreen: function(g) {
      this.rgb.g = g;
   },

   setBlue: function(b) {
      this.rgb.b = b;
   },

   setHue: function(h) {

      // get an HSB model, and set the new hue...
      var hsb = this.asHSB();
      hsb.h = h;

      // convert back to RGB...
      this.rgb = Rico.Color.HSBtoRGB(hsb.h, hsb.s, hsb.b);
   },

   setSaturation: function(s) {
      // get an HSB model, and set the new hue...
      var hsb = this.asHSB();
      hsb.s = s;

      // convert back to RGB and set values...
      this.rgb = Rico.Color.HSBtoRGB(hsb.h, hsb.s, hsb.b);
   },

   setBrightness: function(b) {
      // get an HSB model, and set the new hue...
      var hsb = this.asHSB();
      hsb.b = b;

      // convert back to RGB and set values...
      this.rgb = Rico.Color.HSBtoRGB( hsb.h, hsb.s, hsb.b );
   },

   darken: function(percent) {
      var hsb  = this.asHSB();
      this.rgb = Rico.Color.HSBtoRGB(hsb.h, hsb.s, Math.max(hsb.b - percent,0));
   },

   brighten: function(percent) {
      var hsb  = this.asHSB();
      this.rgb = Rico.Color.HSBtoRGB(hsb.h, hsb.s, Math.min(hsb.b + percent,1));
   },

   blend: function(other) {
      this.rgb.r = Math.floor((this.rgb.r + other.rgb.r)/2);
      this.rgb.g = Math.floor((this.rgb.g + other.rgb.g)/2);
      this.rgb.b = Math.floor((this.rgb.b + other.rgb.b)/2);
   },

   isBright: function() {
      var hsb = this.asHSB();
      return this.asHSB().b > 0.5;
   },

   isDark: function() {
      return ! this.isBright();
   },

   asRGB: function() {
      return "rgb(" + this.rgb.r + "," + this.rgb.g + "," + this.rgb.b + ")";
   },

   asHex: function() {
      return "#" + Rico.toColorPart(this.rgb.r) + Rico.toColorPart(this.rgb.g) + Rico.toColorPart(this.rgb.b);
   },

   asHSB: function() {
      return Rico.Color.RGBtoHSB(this.rgb.r, this.rgb.g, this.rgb.b);
   },

   toString: function() {
      return this.asHex();
   }

};

/**
 * Factory method for creating a color from an RGB string
 * @param hexCode a 3 or 6 digit hex string, optionally preceded by a # symbol
 * @returns a Rico.Color object
 */
Rico.Color.createFromHex = function(hexCode) {
  if(hexCode.length==4) {
    var shortHexCode = hexCode;
    hexCode = '#';
    for(var i=1;i<4;i++)
      hexCode += (shortHexCode.charAt(i) + shortHexCode.charAt(i));
  }
  if ( hexCode.indexOf('#') == 0 )
    hexCode = hexCode.substring(1);
  if (!hexCode.match(/^[0-9A-Fa-f]{6}$/)) return null;
  var red   = hexCode.substring(0,2);
  var green = hexCode.substring(2,4);
  var blue  = hexCode.substring(4,6);
  return new Rico.Color( parseInt(red,16), parseInt(green,16), parseInt(blue,16) );
};

/**
 * Retrieves the background color of an HTML element
 * @param elem the DOM element whose background color should be retreived
 * @returns a Rico.Color object
 */
Rico.Color.createColorFromBackground = function(elem) {

   if (!elem.style) return new Rico.Color(255,255,255);
   var actualColor = Rico.getStyle(elem, "background-color");

   // if color is tranparent, check parent
   // Safari returns "rgba(0, 0, 0, 0)", which means transparent
   if ( actualColor.match(/^(transparent|rgba\(0,\s*0,\s*0,\s*0\))$/i) && elem.parentNode )
      return Rico.Color.createColorFromBackground(elem.parentNode);

   if (actualColor == null) return new Rico.Color(255,255,255);

   if ( actualColor.indexOf("rgb(") == 0 ) {
      var colors = actualColor.substring(4, actualColor.length - 1 );
      var colorArray = colors.split(",");
      return new Rico.Color( parseInt( colorArray[0],10 ),
                             parseInt( colorArray[1],10 ),
                             parseInt( colorArray[2],10 )  );

   }
   else if ( actualColor.indexOf("#") == 0 ) {
      return Rico.Color.createFromHex(actualColor);
   }
   else
      return new Rico.Color(255,255,255);
};

/**
 * Converts hue/saturation/brightness to RGB
 * @returns a 3-element object: r=red, g=green, b=blue.
 */
Rico.Color.HSBtoRGB = function(hue, saturation, brightness) {

  var red   = 0;
	var green = 0;
	var blue  = 0;

  if (saturation == 0) {
     red = parseInt(brightness * 255.0 + 0.5,10);
	   green = red;
	   blue = red;
	}
	else {
      var h = (hue - Math.floor(hue)) * 6.0;
      var f = h - Math.floor(h);
      var p = brightness * (1.0 - saturation);
      var q = brightness * (1.0 - saturation * f);
      var t = brightness * (1.0 - (saturation * (1.0 - f)));

      switch (parseInt(h,10)) {
         case 0:
            red   = (brightness * 255.0 + 0.5);
            green = (t * 255.0 + 0.5);
            blue  = (p * 255.0 + 0.5);
            break;
         case 1:
            red   = (q * 255.0 + 0.5);
            green = (brightness * 255.0 + 0.5);
            blue  = (p * 255.0 + 0.5);
            break;
         case 2:
            red   = (p * 255.0 + 0.5);
            green = (brightness * 255.0 + 0.5);
            blue  = (t * 255.0 + 0.5);
            break;
         case 3:
            red   = (p * 255.0 + 0.5);
            green = (q * 255.0 + 0.5);
            blue  = (brightness * 255.0 + 0.5);
            break;
         case 4:
            red   = (t * 255.0 + 0.5);
            green = (p * 255.0 + 0.5);
            blue  = (brightness * 255.0 + 0.5);
            break;
          case 5:
            red   = (brightness * 255.0 + 0.5);
            green = (p * 255.0 + 0.5);
            blue  = (q * 255.0 + 0.5);
            break;
	    }
	}

   return { r : parseInt(red,10), g : parseInt(green,10) , b : parseInt(blue,10) };
};

/**
 * Converts RGB value to hue/saturation/brightness
 * @param r integer (0-255)
 * @param g integer (0-255)
 * @param b integer (0-255)
 * @returns a 3-element object: h=hue, s=saturation, b=brightness.
 * (unlike some HSB documentation which states hue should be a value 0-360, this routine returns hue values from 0 to 1.0)
 */
Rico.Color.RGBtoHSB = function(r, g, b) {

   var hue;
   var saturation;
   var brightness;

   var cmax = (r > g) ? r : g;
   if (b > cmax)
      cmax = b;

   var cmin = (r < g) ? r : g;
   if (b < cmin)
      cmin = b;

   brightness = cmax / 255.0;
   if (cmax != 0)
      saturation = (cmax - cmin)/cmax;
   else
      saturation = 0;

   if (saturation == 0)
      hue = 0;
   else {
      var redc   = (cmax - r)/(cmax - cmin);
    	var greenc = (cmax - g)/(cmax - cmin);
    	var bluec  = (cmax - b)/(cmax - cmin);

    	if (r == cmax)
    	   hue = bluec - greenc;
    	else if (g == cmax)
    	   hue = 2.0 + redc - bluec;
      else
    	   hue = 4.0 + greenc - redc;

    	hue = hue / 6.0;
    	if (hue < 0)
    	   hue = hue + 1.0;
   }

   return { h : hue, s : saturation, b : brightness };
};

/**
 * Returns a new XML document object
 */
Rico.createXmlDocument = function() {
  if (document.implementation && document.implementation.createDocument) {
    var doc = document.implementation.createDocument("", "", null);
    // some older versions of Moz did not support the readyState property
    // and the onreadystate event so we patch it! 
    if (doc.readyState == null) {
      doc.readyState = 1;
      doc.addEventListener("load", function () {
        doc.readyState = 4;
        if (typeof doc.onreadystatechange == "function") {
          doc.onreadystatechange();
        }
      }, false);
    }
    return doc;
  }

  if (window.ActiveXObject)
      return Rico.tryFunctions(
        function() { return new ActiveXObject('MSXML2.DomDocument');   },
        function() { return new ActiveXObject('Microsoft.DomDocument');},
        function() { return new ActiveXObject('MSXML.DomDocument');    },
        function() { return new ActiveXObject('MSXML3.DomDocument');   }
      ) || false;
  return null;
}

Rico.includeLoaded('ricoUI.js');
