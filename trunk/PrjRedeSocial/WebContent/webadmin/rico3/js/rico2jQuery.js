/**
  *  Copyright (c) 2009 Matt Brown
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

if (typeof jQuery=='undefined') throw('This version of Rico requires the jQuery library');


Rico.Lib='jQuery';
Rico.LibVersion=jQuery().jquery;
Rico.extend=jQuery.extend;
Rico.trim=jQuery.trim;
Rico.tryFunctions = function() {
  for (var i=0; i<arguments.length; i++) {
		try {
			return arguments[i]();
		} catch(e){}
	}
	return null;
};


Rico._j=function(element) {
  if (typeof element=='string')
    element = document.getElementById(element);
  return jQuery(element);
};
  
Rico.select=function(selector, element) {
  return element ? this._j(element).find(selector) : jQuery(selector);
};
  
Rico.eventBind=function(element, eventName, handler) {
  this._j(element).bind(eventName, handler);
};

Rico.eventUnbind=function(element, eventName, handler) {
  this._j(element).unbind(eventName, handler);
};

Rico.eventHandle=function(object, method) {
  return function(e) {
    return object[method].call(object,e);
  }
};

Rico.eventElement=function(ev) {
  return ev.target;
};

Rico.eventClient=function(ev) {
  return {x:ev.clientX, y:ev.clientY};
};

Rico.eventStop=function(ev) {
  ev.preventDefault();
  ev.stopPropagation();
};
  
Rico.addClass=function(element, className) {
  return this._j(element).addClass(className);
};

Rico.removeClass=function(element, className) {
  return this._j(element).removeClass(className);
};

Rico.hasClass=function(element, className) {
  return this._j(element).hasClass(className);
};
  
Rico.getStyle=function(element, property) {
  return this._j(element).css(property);
};
Rico.setStyle=function(element, properties) {
  return this._j(element).css(properties);
};

/**
 * @returns available height, excluding scrollbar & margin
 */
Rico.windowHeight=function() {
  return jQuery(window).height();
};

/**
 * @returns available width, excluding scrollbar & margin
 */
Rico.windowWidth=function() {
  return jQuery(window).width();
};

Rico.positionedOffset=function(element) {
  return this._j(element).position();
};

Rico.cumulativeOffset=function(element) {
  return this._j(element).offset();
};

Rico.docScrollLeft=function() {
  return jQuery('html').scrollLeft();
};

Rico.docScrollTop=function() {
  return jQuery('html').scrollTop();
};

Rico.getDirectChildrenByTag=function(element, tagName) {
  return this._j(element).children(tagName);
};

Rico.ajaxRequest=function(url,options) {
  this.jSend(url,options);
}

Rico.ajaxRequest.prototype = {
  jSend: function(url,options) {
    this.onSuccess=options.onSuccess;
    var jOptions = {
      complete : options.onComplete,
      error: options.onFailure,
      success: Rico.bind(this,'jSuccess'),
      type : options.method.toUpperCase(),
      url : url,
      data : options.parameters
    }
    this.xhr=jQuery.ajax(jOptions);
  },
  
  jSuccess: function() {
    if (this.onSuccess) this.onSuccess(this.xhr);
  }
}

Rico.ajaxSubmit=function(form,url,options) {
  options.parameters=this._j(form).serialize();
  if (!options.method) options.method='post';
  url=url || form.action;
  new Rico.ajaxRequest(url,options);
}

// Animation

Rico.fadeIn=function(element,duration,onEnd) {
  this._j(element).fadeIn(duration,onEnd);
};

Rico.fadeOut=function(element,duration,onEnd) {
  this._j(element).fadeOut(duration,onEnd);
};

Rico.animate=function(element,options,properties) {
  options.complete=options.onEnd;
  this._j(element).animate(properties,options);
};
