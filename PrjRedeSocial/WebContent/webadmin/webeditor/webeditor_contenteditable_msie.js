// Asbru Web Content Editor
// (C) 2002-2014 Asbru Ltd.
// www.asbrusoft.com

if (webeditor.majorVersion == 11) {
	// MSIE11 may not resize textarea inputs when rows/cols are changed
	webeditor.textareaResizeOuterHTML = true;
}

if (webeditor.majorVersion >= 9) {
	// MSIE9 may not handle use of both old align attributes and new css style text-align resulting in multiple conflicting css style text-align values
//	delete webeditor.reformat['p']['align'];
}

if (webeditor.majorVersion >= 8) {
	// MSIE8 may crash if using document.open/write/close to set content body
	webeditor.setcontentbodydocumentwrite = false; // set true to set MSIE content through document.write instead of DOM
	webeditor.setcontentbodyinnerhtml = true; // set true to set MSIE content through innerHTML instead of DOM
}
if (webeditor.majorVersion == 8) {
	// MSIE8 may crash if using document.open/write/close to set content body
	webeditor.setcontentbodydocumentwrite = false; // set true to set MSIE content through document.write instead of DOM
	// MSIE8 may expand relative URLs  if using document.body.innerHTML to set content body
	webeditor.setcontentbodyinnerhtml = false; // set true to set MSIE content through innerHTML instead of DOM
	// MSIE8 may expand relative URLs  if using paste to set content body
	// !!!!! MSIE8 may crash if using document.open/write/close to set content body
//	webeditor.setcontentbodydocumentwrite = true; // set true to set MSIE content through document.write instead of DOM
}

if (webeditor.majorVersion == 7) {
	// MSIE7 / MSIE9 MSIE7 mode may crash on .style.getAttribute
	webeditor.useCssStyleGetAttribute = false;
}

if ((webeditor.majorVersion == 6) || (webeditor.majorVersion == 7)) {
	// MSIE6/MSIE7 may fail to return correct style attribute value using node.attributes and node.getAttribute and return null/blank instead
	webeditor.getAttribute_attributes = false; // use node.attributes[attribute].nodeValue instead of default node.getAttribute(attribute,2)
	webeditor.getAttribute_outerHTML = true; // use node.outerHTML and RegExp instead of default node.getAttribute(attribute,2)
}

try {
	document.execCommand("BackgroundImageCache", false, true);
} catch(e) {
}

function webeditor_empty_content_fix(content) {
	return content;
}

function webeditor_supported(command) {
	return true;
}

function webeditor_select_init(node) {
	node.ondeactivate = webeditor_select_deactivate;
	node.onblur = webeditor_select_blur;
}

function webeditor_select_focus(evt) {
	if (contenteditable_focused_contentwindow()) contenteditable_focused_contentwindow().focus();
	return true;
}

function contenteditable_onload(handler) {
	if (window.attachEvent) {
		window.attachEvent("onload", handler);
	} else if (window.addEventListener) {
		window.addEventListener("load", handler);
//	} else {
//		window.attachEvent("onload", handler);
	}
	WebEditorSkin();
}

function contenteditable_onload_remove(handler) {
	if (window.detachEvent) {
		window.detachEvent("onload", handler);
	} else if (window.removeEventListener) {
		window.removeEventListener("load", handler);
//	} else {
//		window.detachEvent("onload", handler);
	}
}

function contenteditable_event_handler(node, event, handler, capturingphase) {
	contenteditable_detach_event_handler(node, event, handler, capturingphase);
	contenteditable_attach_event_handler(node, event, handler, capturingphase);
}

function contenteditable_attach_event_handler(node, event, handler, capturingphase) {
	node["on"+event] = handler;
	if (node.attachEvent) {
		node.attachEvent("on"+event, handler);
	} else if (node.addEventListener) {
		node.addEventListener(event, handler, capturingphase);
//	} else {
//		node.attachEvent("on"+event, handler);
	}
}

function contenteditable_detach_event_handler(node, event, handler, capturingphase) {
	if (node.detachEvent) {
		node.detachEvent("on"+event, handler);
	} else if (node.removeEventListener) {
		node.removeEventListener(event, handler, capturingphase);
//	} else {
//		node.detachEvent("on"+event, handler);
	}
}

function contenteditable_reinit() {
	for (var i=0; i<document.getElementsByTagName('iframe').length; i++) {
		var iframe = document.getElementsByTagName('iframe').item(i);
		try {
			if (iframe && iframe.contentWindow && iframe.contentWindow.document && iframe.contentWindow.document && iframe.contentWindow.document.body) {
				if (((iframe.className == 'webeditor_contenteditable') || (iframe.className == 'webeditor_contenteditable_disabled'))) {
					try {
						contenteditable_detach_event_handler(iframe.contentWindow, "focus", contenteditable_onfocus[i]);
					}  catch (e) {
					}
					try {
						contenteditable_detach_event_handler(iframe.contentWindow, "blur", contenteditable_onblur[i]);
					}  catch (e) {
					}
					try {
						contenteditable_detach_event_handler(iframe.contentWindow.document.body, "focus", contenteditable_onfocus[i]);
					}  catch (e) {
					}
					try {
						contenteditable_detach_event_handler(iframe.contentWindow.document.body, "blur", contenteditable_onblur[i]);
					}  catch (e) {
					}
				}
			}
		}  catch (e) {
		}
	}
	contenteditable_inited = new Array();
	contenteditable_onfocus = new Array();
	contenteditable_onblur = new Array();
	for (var i=0; i<document.getElementsByTagName('iframe').length; i++) {
		var iframe = document.getElementsByTagName('iframe').item(i);
		try {
			if (iframe && iframe.contentWindow && iframe.contentWindow.document && iframe.contentWindow.document && iframe.contentWindow.document.body) {
				if (((iframe.className == 'webeditor_contenteditable') || (iframe.className == 'webeditor_contenteditable_disabled')) && (! contenteditable_inited[iframe.id])) {
					try {
						if (! contenteditable_onfocus[i]) contenteditable_onfocus[i] = new Function('event', 'contenteditable_focused='+i+';webeditor_onfocus();webeditor_refreshToolbar(true);;');
						if (! contenteditable_onblur[i]) contenteditable_onblur[i] = new Function('event', 'webeditor_onblur();');
						contenteditable_event_handler(iframe.contentWindow, "focus", contenteditable_onfocus[i]);
						contenteditable_event_handler(iframe.contentWindow, "blur", contenteditable_onblur[i]);
						contenteditable_event_handler(iframe.contentWindow.document.body, "focus", contenteditable_onfocus[i]);
						contenteditable_event_handler(iframe.contentWindow.document.body, "blur", contenteditable_onblur[i]);
					} catch (e) {
					}
					contenteditable_inited[iframe.id] = true;
				}
			}
		} catch (e) {
		}
	}
}

function contenteditable_init() {
	webeditor.refreshToolbar = false;
	if (! webeditor.scrolledContentToTop) webeditor.scrolledContentToTop = new Object();
	if (webeditor.first != false) webeditor.first = true;
	var first = webeditor.first;

	// load style sheets and empty content files for all web editor input fields
	for (var i=0; i<document.getElementsByTagName('iframe').length; i++) {
		var iframe = document.getElementsByTagName('iframe').item(i);
		try {
			if (iframe && iframe.contentWindow && iframe.contentWindow.document && iframe.contentWindow.document && iframe.contentWindow.document.body) {
				if (((iframe.className == 'webeditor_contenteditable') || (iframe.className == 'webeditor_contenteditable_disabled')) && (! contenteditable_inited[iframe.id])) {
					try {
						if (contenteditable_stylesheet[iframe.id] && iframe && iframe.contentWindow && iframe.contentWindow.document && iframe.contentWindow.document.getElementById('stylesheet') && (! contenteditable_inited_stylesheet[iframe.id])) {
							iframe.contentWindow.document.getElementById('stylesheet').href = contenteditable_stylesheet[iframe.id];
							contenteditable_inited_stylesheet[iframe.id] = true;
						}
					}  catch (e) {
						alert(Text('webbrowser_unsupported_contenteditable')+"\r\n\r\n"+e+"\r\n\r\n"+e.message);
					}
				}
			} else {
				setTimeout(contenteditable_init, webeditor.initTimeout);
				webeditor.initTimeout = webeditor.initTimeout * webeditor.initTimeoutMultiplier;
				return;
			}
		}  catch (e) {
		}
	}

	// all style sheets and empty content files for all web editor input fields have been loaded
	// make all webeditor input fields editable and set the content
	for (var i=0; i<document.getElementsByTagName('iframe').length; i++) {
		var iframe = document.getElementsByTagName('iframe').item(i);
		try {
			if (iframe && iframe.contentWindow && iframe.contentWindow.document && iframe.contentWindow.document && iframe.contentWindow.document.body) {
				if (((iframe.className == 'webeditor_contenteditable') || (iframe.className == 'webeditor_contenteditable_disabled')) && (! contenteditable_inited[iframe.id])) {
					try {
						if (webeditor.direction) iframe.contentWindow.document.dir = webeditor.direction;
						iframe.contentWindow.document.body.contentEditable = true;
						iframe.contentWindow.document.execCommand("redo", false, null);
						// Setting innerHTML may not work properly - changes href/src from relative to absolute - use write instead
						//iframe.contentWindow.document.body.innerHTML = contenteditable_contents[iframe.id];
						iframe.contentWindow.document.body.innerHTML = "&nbsp;";
						// MSIE replace may be broken for escaped "\$1" and "\$2" dollar characters in replacement string
//						iframe.contentWindow.document.write(iframe.contentWindow.document.documentElement.outerHTML.replace(new RegExp("(&nbsp;)?(</body>)", "i"), contenteditable_contents[iframe.id].replace(/\$([_`'+&0-9]+)/g, "\\\$\\$1")+"$2").replace(/\\(\$)\\([_`'+&0-9]+)/g, "$1$2"));
// MSIE may add web page to web browser "history" on document.write - set content using Javascript DOM instead
//						if (! contenteditable_onfocus[i]) contenteditable_onfocus[i] = new Function('event', 'contenteditable_focused='+i+';webeditor_onfocus();webeditor_refreshToolbar(true);');
//						if (! contenteditable_onblur[i]) contenteditable_onblur[i] = new Function('event', 'webeditor_onblur();webeditor_refreshToolbar(true);');
//						if (! contenteditable_onfocus[i]) contenteditable_onfocus[i] = new Function('event', 'contenteditable_focused='+i+';webeditor_onfocus();webeditor_refreshToolbar(false, 1);');
//						if (! contenteditable_onblur[i]) contenteditable_onblur[i] = new Function('event', 'webeditor_onblur();webeditor_refreshToolbar(false, 1);');
						if (! contenteditable_onfocus[i]) contenteditable_onfocus[i] = new Function('event', 'contenteditable_focused='+i+';webeditor_onfocus();webeditor_refreshToolbar(true);');
						if (! contenteditable_onblur[i]) contenteditable_onblur[i] = new Function('event', 'webeditor_onblur();');
						contenteditable_event_handler(iframe.contentWindow, "focus", contenteditable_onfocus[i]);
						contenteditable_event_handler(iframe.contentWindow, "blur", contenteditable_onblur[i]);
						contenteditable_event_handler(iframe.contentWindow.document.body, "focus", contenteditable_onfocus[i]);
						contenteditable_event_handler(iframe.contentWindow.document.body, "blur", contenteditable_onblur[i]);
						contenteditable_event_handler(iframe.contentWindow.document, "keydown", contenteditable_event);
						contenteditable_event_handler(iframe.contentWindow.document, "keyup", contenteditable_event);
						contenteditable_event_handler(iframe.contentWindow.document, "keypress", contenteditable_event);
						contenteditable_event_handler(iframe.contentWindow.document, "mousedown", contenteditable_event);
						contenteditable_event_handler(iframe.contentWindow.document, "mouseup", contenteditable_event);
						contenteditable_event_handler(iframe.contentWindow.document, "drag", contenteditable_event);
						contenteditable_event_handler(iframe.contentWindow.document, "dragstart", contenteditable_event);
						contenteditable_event_handler(iframe.contentWindow.document, "dragend", contenteditable_event);
						contenteditable_event_handler(iframe.contentWindow.document, "dragenter", contenteditable_event);
						contenteditable_event_handler(iframe.contentWindow.document, "dragover", contenteditable_event);
						contenteditable_event_handler(iframe.contentWindow.document, "dragleave", contenteditable_event);
						contenteditable_event_handler(iframe.contentWindow.document, "drop", contenteditable_event);
						if (webeditor.contextmenus) contenteditable_event_handler(iframe.contentWindow.document, "contextmenu", contenteditable_contextmenu);
						iframe.contentWindow.document.close();
						// MSIE may add web page to web browser "history" on document.write - set content using Javascript DOM instead
						contenteditable_setContent(contenteditable_contents[iframe.id], iframe.id);
						var form = iframe;
						while ((form.tagName != "FORM") && (form.tagName != "HTML")) {
							form = form.parentNode;
						}
						if (form.tagName != "HTML") {
							contenteditable_event_handler(form, "submit", contenteditable_onSubmit);
							form[iframe.id].value = contenteditable_contents[iframe.id];
						}
					}  catch (e) {
						alert(Text('webbrowser_unsupported_contenteditable')+"\r\n\r\n"+e+"\r\n\r\n"+e.message);
					}
					if (! contenteditable_inited[iframe.id]) webeditor.inited += 1;
					contenteditable_inited[iframe.id] = true;
					if (first) {
						first = false;
						contenteditable_focused = i;
//						webeditor_dropdown_stylesheet('formatclass', contenteditable_stylesheet[iframe.id]);
//						webeditor_dropdown_stylesheet('formatblock', contenteditable_stylesheet[iframe.id]);
//						webeditor_dropdown_stylesheet('fontname', contenteditable_stylesheet[iframe.id]);
//						webeditor_dropdown_stylesheet('fontsize', contenteditable_stylesheet[iframe.id]);
					}
					try {
//						if (webeditor.scrollContentToTop && iframe && iframe.contentWindow && iframe.contentWindow.document && iframe.contentWindow.document.body && iframe.contentWindow.document.body.firstChild && iframe.contentWindow.document.body.firstChild.scrollIntoView) iframe.contentWindow.document.body.firstChild.scrollIntoView();
					} catch(e) {
					}
				}
				if ((iframe.className == 'webeditor_contenteditable') || (iframe.className == 'webeditor_contenteditable_disabled')) {
					if ((! webeditor.scrolledContentToTop[i]) && webeditor.scrollContentToTop && iframe && iframe.contentWindow && iframe.contentWindow.document && iframe.contentWindow.document.body) {
						webeditor.scrolledContentToTop[i] = true;
//						iframe.contentWindow.focus();
						if (iframe.contentWindow.document.body.firstChild) {
							contenteditable_selection_node(iframe.contentWindow.document.body.firstChild);
						} else {
							contenteditable_selection_node(iframe.contentWindow.document.body);
						}
						var range = contenteditable_selection_range();
						if (range) range.collapse(true);
						if (range) range.select();
					}
				}
				webeditor.refreshToolbar = true;
			} else {
				setTimeout(contenteditable_init, webeditor.initTimeout);
				webeditor.initTimeout = webeditor.initTimeout * webeditor.initTimeoutMultiplier;
			}
		}  catch (e) {
		}
		scrollAllToTopLeft(iframe);
	}

	// finalize initialization
	if (webeditor.scrollContentToTop && (webeditor.inited == webeditor.count-1)) {
		var iframe = document.getElementsByTagName('iframe').item(contenteditable_focused);
		try {
			if (iframe && iframe.contentWindow && iframe.contentWindow.document && iframe.contentWindow.document.body) {
				if (iframe.contentWindow.document.body.firstChild) {
					contenteditable_selection_node(iframe.contentWindow.document.body.firstChild);
				} else {
					contenteditable_selection_node(iframe.contentWindow.document.body);
				}
				var range = contenteditable_selection_range();
				if (range) range.collapse(true);
				if (range) range.select();
//				iframe.contentWindow.focus();
				webeditor_onfocus();
				webeditor.refreshToolbar = true;
				webeditor_refreshToolbar(true);
////				iframe.contentWindow.blur();
//				window.focus();
				if (document.location.href.indexOf("#") < 0) window.scrollTo(0,0);
			}
		}  catch (e) {
		}
	}

	if (typeof(webeditor_custom_ready) != "undefined") {
		try {
			webeditor_custom_ready();
		} catch(e) {
		}
	}
}

function contenteditable_enable(id) {
	for (var i=0; i<document.getElementsByTagName('iframe').length; i++) {
		var iframe = document.getElementsByTagName('iframe').item(i);
		try {
			if (iframe && iframe.contentWindow && iframe.contentWindow.document && iframe.contentWindow.document && iframe.contentWindow.document.body) {
				if ((iframe.className == 'webeditor_contenteditable') || (iframe.className == 'webeditor_contenteditable_disabled')) {
					if ((iframe.id == id) || (! id)) {
						try {
							if (iframe.className != 'webeditor_contenteditable') {
								iframe.className = 'webeditor_contenteditable';
							}
							if (iframe.contentWindow.document.body.contentEditable != true) {
								iframe.contentWindow.document.body.contentEditable = true;
							}
							if (webeditor.direction && (iframe.contentWindow.document.dir != webeditor.direction)) {
								iframe.contentWindow.document.dir = webeditor.direction;
							}
						}  catch (e) {
						}
						try {
							if (! contenteditable_onfocus[i]) contenteditable_onfocus[i] = new Function('event', 'contenteditable_focused='+i+';webeditor_onfocus();webeditor_refreshToolbar(true);;');
							if (! contenteditable_onblur[i]) contenteditable_onblur[i] = new Function('event', 'webeditor_onblur();');
							contenteditable_event_handler(iframe.contentWindow, "focus", contenteditable_onfocus[i]);
							contenteditable_event_handler(iframe.contentWindow, "blur", contenteditable_onblur[i]);
							contenteditable_event_handler(iframe.contentWindow.document.body, "focus", contenteditable_onfocus[i]);
							contenteditable_event_handler(iframe.contentWindow.document.body, "blur", contenteditable_onblur[i]);
						}  catch (e) {
						}
						contenteditable_inited[iframe.id] = true;
					}
				}
			}
		}  catch (e) {
		}
	}
}

function contenteditable_disable(id) {
	for (var i=0; i<document.getElementsByTagName('iframe').length; i++) {
		var iframe = document.getElementsByTagName('iframe').item(i);
		try {
			if (iframe && iframe.contentWindow && iframe.contentWindow.document && iframe.contentWindow.document && iframe.contentWindow.document.body) {
				if ((iframe.className == 'webeditor_contenteditable') || (iframe.className == 'webeditor_contenteditable_disabled')) {
					if ((iframe.id == id) || (! id)) {
						try {
							if (iframe.className != 'webeditor_contenteditable_disabled') {
								iframe.className = 'webeditor_contenteditable_disabled';
							}
							if (iframe.contentWindow.document.body.contentEditable != false) {
								iframe.contentWindow.document.body.contentEditable = false;
							}
							if (webeditor.direction && (iframe.contentWindow.document.dir != webeditor.direction)) {
								iframe.contentWindow.document.dir = webeditor.direction;
							}
						}  catch (e) {
						}
						try {
							contenteditable_detach_event_handler(iframe.contentWindow, "onfocus", contenteditable_onfocus[i]);
						}  catch (e) {
						}
						try {
							contenteditable_detach_event_handler(iframe.contentWindow, "onblur", contenteditable_onblur[i]);
						}  catch (e) {
						}
						try {
							contenteditable_detach_event_handler(iframe.contentWindow.document.body, "onfocus", contenteditable_onfocus[i]);
						}  catch (e) {
						}
						try {
							contenteditable_detach_event_handler(iframe.contentWindow.document.body, "onblur", contenteditable_onblur[i]);
						}  catch (e) {
						}
					}
				}
			}
		}  catch (e) {
		}
	}
}

function contenteditable_toolbar(focused) {
	if (webeditor.toolbarid && document.getElementById(webeditor.toolbarid) && document.getElementById(webeditor.toolbarid).contentWindow && document.getElementById(webeditor.toolbarid).contentWindow.document) {
		return document.getElementById(webeditor.toolbarid).contentWindow.document;
	} else if (parent && parent.document && parent.document.getElementById(webeditor.toolbarid) && parent.document.getElementById(webeditor.toolbarid).contentWindow && parent.document.getElementById(webeditor.toolbarid).contentWindow.document) {
		return parent.document.getElementById(webeditor.toolbarid).contentWindow.document;
	} else if (webeditor.toolbar && webeditor.toolbar.contentWindow && webeditor.toolbar.contentWindow.document && webeditor.toolbar.contentWindow.document.body) {
		return webeditor.toolbar.contentWindow.document;
	} else if (focused && contenteditable_focused_iframe() && contenteditable_focused_iframe().id && document.getElementById('webeditor_toolbar_' + contenteditable_focused_iframe().id)) {
		return document.getElementById('webeditor_toolbar_' + contenteditable_focused_iframe().id);
	} else {
		return document;
	}
}

function contenteditable_document_stylesheets_cssrules(stylesheet) {
	return stylesheet.rules;
}





function contenteditable_selection_fix() {
}

function contenteditable_iframe_scrollTop(iframe) {
	if (iframe.pageYOffset) {
		return iframe.pageYOffset;
	} else if (iframe.contentWindow.document.documentElement && iframe.contentWindow.document.documentElement.scrollTop) {
		return iframe.contentWindow.document.documentElement.scrollTop;
	} else if (iframe.contentWindow.document.body) {
		return iframe.contentWindow.document.body.scrollTop;
	} else {
		return 0;
	}
}
function contenteditable_iframe_scrollLeft(iframe) {
	if (iframe.pageXOffset) {
		return iframe.pageXOffset;
	} else if (iframe.contentWindow.document.documentElement && iframe.contentWindow.document.documentElement.scrollLeft) {
		return iframe.contentWindow.document.documentElement.scrollLeft;
	} else if (iframe.contentWindow.document.body) {
		return iframe.contentWindow.document.body.scrollLeft;
	} else {
		return 0;
	}
}
function contenteditable_iframe_height(iframe) {
	if (iframe.scrollHeight) {
		return iframe.scrollHeight;
	} else {
		return 0;
	}
}
function contenteditable_iframe_width(iframe) {
	if (iframe.scrollWidth) {
		return iframe.scrollWidth;
	} else {
		return 0;
	}
}
function contenteditable_iframe_content_height(iframe) {
	if (iframe.contentWindow.document.body.scrollHeight > iframe.contentWindow.document.body.offsetHeight) {
		return iframe.contentWindow.document.body.scrollHeight;
	} else {
		return iframe.contentWindow.document.body.offsetHeight;
	}
}
function contenteditable_iframe_content_width(iframe) {
	if (iframe.contentWindow.document.body.scrollWidth > iframe.contentWindow.document.body.offsetWidth) {
		return iframe.contentWindow.document.body.scrollWidth;
	} else {
		return iframe.contentWindow.document.body.offsetWidth;
	}
}
var contenteditable_event_dragstart = false;
function contenteditable_event(event) {
	// MSIE may rewrite/expand URLs in dragged content - MSIE may not trigger dragend/mouseup event on dragged/dropped content - note dragstart and fix content on next event of any type
	if (contenteditable_event_dragstart) {
		contenteditable_event_dragstart = false;
		contenteditable_paste_fix();
	}
	if (event && event.type && (event.type == "dragstart")) {
		contenteditable_event_dragstart = true;
	}

	// MSIE may not focus input field if clicked below content in input field
	if (event && event.srcElement && (event.srcElement.nodeName == "HTML") && event.srcElement.document && event.srcElement.document.body) {
		for (var i=0; i<document.getElementsByTagName('iframe').length; i++) {
			var iframe = document.getElementsByTagName('iframe').item(i);
			try {
				if (((iframe.className == 'webeditor_contenteditable') || (iframe.className == 'webeditor_contenteditable_disabled')) && (iframe.contentWindow.document.body == event.srcElement.document.body)) {
					if ((contenteditable_iframe_height(iframe) > contenteditable_iframe_content_height(iframe)) && (contenteditable_iframe_width(iframe) > contenteditable_iframe_content_width(iframe))) {
						contenteditable_focused = i;
						iframe.contentWindow.focus();
						if (iframe.contentWindow.document.body.lastChild) {
							contenteditable_selection_node(iframe.contentWindow.document.body.lastChild);
						} else {
							contenteditable_selection_node(iframe.contentWindow.document.body);
						}
						var range = contenteditable_selection_range();
						if (range) range.collapse(false);
						if (range) range.select();
					}
				}
			}  catch (e) {
			}
		}
	}
	webeditor_refreshToolbar(webeditor.refreshtoolbarOnAnyEvent);
	if (contenteditable_focused_contentwindow()) return webeditor_event(contenteditable_focused_contentwindow().event);
}

function contenteditable_event_stop(event) {
	event.cancelBubble = true;
	event.returnValue = false;
}

function contenteditable_event_ctrlkey(event) {
	if (event.type == "keypress") {
		return event.ctrlKey;
	} else {
		return event.ctrlKey;
	}
}

function contenteditable_event_key(event) {
	if ((event.type == "keydown") && ((event.keyCode < 16) || (event.keyCode > 18))) {
		return String.fromCharCode(event.keyCode).toLowerCase();
	} else {
		return false;
	}
}

function contenteditable_event_delete(my_event) {
	// MSIE may delete all content if deleting a table using Backspace
	if (my_event && my_event.keyCode && (my_event.keyCode == webeditor_keyCode_backspace)) {
		if ((my_event.type == "keydown") || (my_event.type == "keypress")) {
			var container = contenteditable_selection_container();
			var parentnode = container.parentNode;
			if ((container.nodeName == "TBODY") || (container.nodeName == "THEAD") || (container.nodeName == "TFOOT")) {
				contenteditable_event_stop(my_event);
				contenteditable_undo_save();
				parentnode.removeChild(container);
				if ((parentnode.nodeName == "TABLE") && (parentnode.childNodes && parentnode.childNodes.length == 0)) {
					parentnode.parentNode.removeChild(parentnode);
				}
				return;
			} else if (contenteditable_selection_range_control() && ((container.nodeName == "HR") || (container.nodeName == "IFRAME") || (container.nodeName == "DIV") || (container.nodeName == "IMG") || (container.nodeName == "TABLE") || (container.nodeName == "INPUT") || (container.nodeName == "TEXTAREA") || (container.nodeName == "SELECT"))) {
				// MSIE may return wrong container/parentnode for SELECT tags so check container nodeName
				contenteditable_event_stop(my_event);
				contenteditable_undo_save();
				parentnode.removeChild(container);
				return;
			} else if (contenteditable_selection_range_control()) {
				// MSIE may return wrong container/parentnode for SELECT tags so ignore delete
				contenteditable_event_stop(my_event);
				return;
			}
		}
	}
	// MSIE may insert "&nbsp;" when selection text is deleted
	if (my_event && my_event.keyCode && ((my_event.keyCode == webeditor_keyCode_backspace) || (my_event.keyCode == webeditor_keyCode_delete))) {
		if ((my_event.type == "keydown") || (my_event.type == "keypress")) {
			if (contenteditable_selection_text()) {
				var range = contenteditable_selection_range();
				var parentnode = contenteditable_selection_range_parentNode(range);
				var startnode = contenteditable_selection_range_startNode(range);
				var endnode = contenteditable_selection_range_endNode(range);
				if (startnode && (startnode == endnode) && (startnode.nodeName == "#text") && (startnode.parentNode.firstChild == startnode.parentNode.lastChild) && (startnode.parentNode.nodeName != "BODY") && (contenteditable_selection_text() == startnode.nodeValue)) {
					// MSIE may not delete the selected node but only its inner text
					startnode.parentNode.parentNode.removeChild(startnode.parentNode);
				} else if (parentnode && (! startnode) && (! endnode)) {
					// MSIE paste may not delete the selected node if no inner text
				} else if (contenteditable_selection_text().match(/^\r?\n?<.+>&nbsp;<\/.+>$/)) {
					// MSIE paste may not delete an empty tag
				} else if (contenteditable_selection_text().match(/^\r?\n?<.+><\/.+>$/)) {
					// MSIE paste may not delete an empty tag
				} else if (webeditor.pasteToDelete) {
					contenteditable_event_stop(my_event);
					contenteditable_execcommand("paste", "")
				}
			}
		}
	}
}

function contenteditable_event_enter_p(my_event) {
	if (my_event.ctrlKey || my_event.shiftKey || my_event.altKey) {
		contenteditable_event_stop(my_event);
		contenteditable_pasteContent("<p>");
	}
	return;

	if (my_event.type != "keydown") {
		contenteditable_event_stop(my_event);
		return;
	}
	var range = contenteditable_selection_range();
	if (range.text == range.htmlText) { // #text node
		var parentNode = contenteditable_selection_range_parentNode();
		var oldP = parentNode.firstChild;
		var preP = "";
		var postP = "";
		var preRange = contenteditable_createrange();
		var postRange = contenteditable_createrange();
		if (preRange.moveToElementText) {
			preRange.moveToElementText(parentNode);
			while (range.compareEndPoints("StartToEnd",preRange) == -1) {
				preRange.moveEnd("character",-1);
			}
			postRange.moveToElementText(parentNode);
			while (range.compareEndPoints("EndToStart",postRange) == 1) {
				postRange.moveStart("character",1);
			}
			preP = preRange.htmlText.replace(/<p>/gi, "").replace(/<\/p>/gi, "") || "";
			postP = postRange.htmlText.replace(/<p>/gi, "").replace(/<\/p>/gi, "") || "";
		}
		var oldPparentNode = oldP.parentNode;
		if (parentNode.nodeName == "P") {
			if (oldPparentNode.firstChild == oldPparentNode.lastChild) {
				var newP = contenteditable_focused_contentwindow().document.createElement("P");
				newP.innerHTML = preP || "&nbsp;";
				if (postP) {
					oldP.nodeValue = postP;
				} else {
					oldPparentNode.innerHTML = "&nbsp;";
				}
				oldPparentNode.parentNode.insertBefore(newP, oldPparentNode);
				range = contenteditable_selection_range();
				range.moveToElementText(oldPparentNode);
				range.collapse(1);
				range.select();
			} else if (oldP == oldPparentNode.lastChild) {
				var newP = contenteditable_focused_contentwindow().document.createElement("P");
				newP.innerHTML = postP || "&nbsp;";
				oldP.nodeValue = preP;
				if (oldPparentNode.nextSibling) {
					oldPparentNode.parentNode.insertBefore(newP, oldPparentNode.nextSibling);
				} else {
					oldPparentNode.parentNode.appendChild(newP);
				}
				range = contenteditable_selection_range();
				range.moveToElementText(newP);
				range.collapse(1);
				range.select();
			} else if (oldP == oldPparentNode.firstChild) {
				var newP = contenteditable_focused_contentwindow().document.createElement("P");
				newP.innerHTML = preP || "&nbsp;";
				oldP.nodeValue = postP;
				oldPparentNode.parentNode.insertBefore(newP, oldPparentNode);
				range = contenteditable_selection_range();
				range.moveToElementText(oldP);
				range.collapse(1);
				range.select();
			} else {
				var newP = contenteditable_focused_contentwindow().document.createElement("P");
				newP.innerHTML = postP || "&nbsp;";
				oldP.nodeValue = preP;
				while (oldP.nextSibling) {
					nextSibling = oldP.nextSibling;
					oldPparentNode.removeChild(nextSibling);
					newP.appendChild(nextSibling);
				}
				if (oldPparentNode.nextSibling) {
					oldPparentNode.parentNode.insertBefore(newP, oldPparentNode.nextSibling);
				} else {
					oldPparentNode.parentNode.appendChild(newP);
				}
				range = contenteditable_selection_range();
				range.moveToElementText(newP);
				range.collapse(1);
				range.select();
			}
		} else if (parentNode.nodeName == "BODY") {
			var newP = contenteditable_focused_contentwindow().document.createElement("P");
			newP.innerHTML = preP || "&nbsp;";
			var newP2 = contenteditable_focused_contentwindow().document.createElement("P");
			newP2.innerHTML = postP || "&nbsp;";
			oldPparentNode.insertBefore(newP, oldP);
			oldPparentNode.insertBefore(newP2, oldP);
			oldPparentNode.removeChild(oldP);
			range = contenteditable_selection_range();
			range.moveToElementText(newP2);
			range.collapse(1);
			range.select();
		} else {
			var newP = contenteditable_focused_contentwindow().document.createElement("P");
			newP.innerHTML = postP || "&nbsp;";
			oldP.nodeValue = preP;
			while (oldP.nextSibling) {
				nextSibling = oldP.nextSibling;
				oldPparentNode.removeChild(nextSibling);
				newP.appendChild(nextSibling);
			}
			while (oldPparentNode.nextSibling && (oldPparentNode.nextSibling.nodeName != "P")) {
				nextSibling = oldPparentNode.nextSibling;
				oldPparentNode.parentNode.removeChild(nextSibling);
				newP.appendChild(nextSibling);
			}
			if (oldPparentNode.nextSibling) {
				oldPparentNode.parentNode.insertBefore(newP, oldPparentNode.nextSibling);
			} else {
				oldPparentNode.parentNode.appendChild(newP);
			}
			range = contenteditable_selection_range();
			range.moveToElementText(newP);
			range.collapse(1);
			range.select();
		}
	} else {
		contenteditable_pasteContent("<p>");
	}
}

function contenteditable_event_enter_fix(my_event) {
	if (my_event && my_event.keyCode && (my_event.keyCode == webeditor_keyCode_enter)) {
		if (webeditor.onCtrlEnter && my_event.ctrlKey) {
		} else if (webeditor.onShiftEnter && my_event.shiftKey) {
		} else if (webeditor.onAltEnter && my_event.altKey) {
		} else if (webeditor.onEnter && contenteditable_selection_container('li')) {
		} else if (webeditor.onEnter && (webeditor.onEnter.toLowerCase() != "<p>")) {
		} else {
			var range = contenteditable_selection_range();
			var parentnode = contenteditable_selection_range_parentNode(range);
			var startnode = contenteditable_selection_range_startNode(range);
			var endnode = contenteditable_selection_range_endNode(range);
			var startoffset = contenteditable_selection_range_startOffset(range);
			var endoffset = contenteditable_selection_range_endOffset(range);
			if (startnode && endnode && (startnode == endnode) && (startnode.nodeName != "#text")) {
				if (my_event.type == "keydown") {
					contenteditable_event_stop(my_event);
					contenteditable_pasteContent("<p>");
				} else {
					contenteditable_event_stop(my_event);
				}
			} else if (startnode && endnode && (startnode == endnode) && parentnode && (parentnode.nodeName == "DIV") && (startoffset == endoffset) && (startoffset == startnode.nodeValue.length)) {
				if (my_event.type == "keydown") {
					contenteditable_event_stop(my_event);
					var node = contenteditable_focused_contentwindow().document.createElement("div");
					node.innerHTML = "<br>";
					if (parentnode.nextSibling) {
						parentnode.parentNode.insertBefore(node, parentnode.nextSibling);
					} else {
						parentnode.parentNode.appendChild(node);
					}
					contenteditable_selection_node(node.firstChild);
				} else {
					contenteditable_event_stop(my_event);
				}
			} else if (startnode && endnode && (startnode == endnode) && parentnode && (parentnode.nodeName == "DIV") && (startoffset == endoffset) && (startoffset == 0)) {
				if (my_event.type == "keydown") {
					contenteditable_event_stop(my_event);
					var node = contenteditable_focused_contentwindow().document.createElement("div");
					node.innerHTML = "<br>";
					parentnode.parentNode.insertBefore(node, parentnode);
				} else {
					contenteditable_event_stop(my_event);
				}
			}
		}
	}
}

function contenteditable_event_dragdrop(my_event) {
	// MSIE may not trigger dragend/mouseup event on dragged/dropped image/link but try anyway
	if (my_event && my_event.srcElement && (my_event.srcElement.nodeName == "IMG")) {
		contenteditable_event_paste_fix_sub("IMG", "src", my_event.srcElement);
	}
	if (my_event && my_event.srcElement && (my_event.srcElement.nodeName == "A")) {
		contenteditable_event_paste_fix_sub("A", "href", my_event.srcElement);
	}
}





function contenteditable_selection(contentWindow) {
	if (! contentWindow) contentWindow = contenteditable_focused_contentwindow();
	if (contentWindow && contentWindow.document) return contentWindow.document.selection;
}

function contenteditable_selection_text(contentSelection) {
	if (! contentSelection) contentSelection = contenteditable_selection();
	var contentSelectionRange = contenteditable_selection_range(contentSelection);
	if (contentSelectionRange) {
		return contentSelectionRange.htmlText;
	} else {
		return "";
	}
}

function contenteditable_selection_range_control(contentSelection) {
	if (! contentSelection) contentSelection = contenteditable_selection();
	if (contentSelection) {
		return (contentSelection.type == 'Control');
	}
}

function contenteditable_selection_range_is_webeditor(contentRange) {
//QQQQQ TODO handle contenteditable
	var rangebody = contenteditable_selection_range_body(contentRange);
	if ((! rangebody) || (rangebody.className != "WebEditor")) {
		return false;
	} else {
		return true;
	}
}

function contenteditable_selection_range_body(contentRange) {
	var rangebody = false;
	if (! contentRange) contentRange = contenteditable_selection_range();
	if (contentRange && contentRange.parentElement) {
		rangebody = contentRange.parentElement();
		while (rangebody && (rangebody.nodeName != "BODY") && (rangebody.nodeName != "HTML")) {
			rangebody = rangebody.parentNode;
		}
		if (rangebody.nodeName != "BODY") {
			rangebody = false;
		}
	}
	return rangebody;
}

function contenteditable_selection_range_parentNode(contentRange) {
	if (! contentRange) contentRange = contenteditable_selection_range();
	if (contentRange && contentRange.parentElement) {
		try {
			var parentnode = contentRange.parentElement();
			// MSIE may not return correct parentelement for selection range - expand to grandparentnode if wrong
			if (parentnode.nodeName == "BODY") {
				// ok
			} else if (parentnode.innerHTML.indexOf(contentRange.htmlText) < 0) {
				var rangestart = contenteditable_selection_range_startNode();
				var rangeend = contenteditable_selection_range_endNode();
				if (parentnode && rangestart && rangeend && (rangestart == rangeend) && rangestart.parentNode && (rangestart.parentNode == parentnode)) {
					// ok 
				} else {
					parentnode = parentnode.parentNode;
				}
			}
			return parentnode;
		} catch(e) {
			return false;
		}
	} else {
		return false;
	}
}

function contenteditable_selection_range_startNode(contentRange) {
	if (! contentRange) contentRange = contenteditable_selection_range();
	var startNode = false;
	var parentNode = false;
	if (contentRange && contentRange.parentElement) {
		parentNode = contentRange.parentElement();
	}
	if (parentNode && parentNode.firstChild) {
		startNode = parentNode.firstChild;
		for (var node=parentNode.firstChild; node; node=node.nextSibling) {
			var testRange = false;
			try {
				testRange = contenteditable_createrange();
				testRange.moveToElementText(node);
			} catch (e) {
				testRange = false;
			}
			if (! testRange) {
				try {
					testRange = contenteditable_createtextrange();
					testRange.moveToElementText(node);
				} catch (e) {
					testRange = false;
				}
			}
			if (node.nodeName != "#text") {
				testRange.moveToElementText(node);
				if ((contentRange.compareEndPoints("StartToStart",testRange)==1) && (contentRange.compareEndPoints("StartToEnd",testRange)==1) && (contentRange.compareEndPoints("EndToStart",testRange)==1) && (contentRange.compareEndPoints("EndToEnd",testRange)==1)) {
					if (node.nextSibling) startNode = node.nextSibling;
				}
				if ((contentRange.compareEndPoints("StartToStart",testRange)==-1) && (contentRange.compareEndPoints("StartToEnd",testRange)==-1) && (contentRange.compareEndPoints("EndToStart",testRange)==-1) && (contentRange.compareEndPoints("EndToEnd",testRange)==-1)) {
					break;
				}
			}
		}
	}
	return startNode;
}

function contenteditable_selection_range_endNode(contentRange) {
	if (! contentRange) contentRange = contenteditable_selection_range();
	var endNode = false;
	var parentNode = false;
	if (contentRange && contentRange.parentElement) {
		parentNode = contentRange.parentElement();
	}
	if (parentNode && parentNode.firstChild) {
		endNode = parentNode.firstChild;
		for (var node=parentNode.firstChild; node; node=node.nextSibling) {
			if (node.nodeName != "#text") {
				var testRange = contenteditable_createrange();
				testRange.moveToElementText(node);
				if ((contentRange.compareEndPoints("StartToStart",testRange)==-1) && (contentRange.compareEndPoints("StartToEnd",testRange)==-1) && (contentRange.compareEndPoints("EndToStart",testRange)==-1) && (contentRange.compareEndPoints("EndToEnd",testRange)==-1)) {
					if (node.previousSibling) endNode = node.previousSibling;
					break;
				} else {
					if (node.previousSibling) endNode = node.previousSibling;
				}
			}
		}
	}
	return endNode;
}

function contenteditable_selection_range_startOffset(range) {
	var startOffset = 0;
	var startNode = contenteditable_selection_range_startNode(range);
	if (startNode) {
		var testRange = contenteditable_createrange();
		if (startNode.previousSibling) {
			testRange.moveToElementText(startNode.previousSibling);
		} else {
			testRange.moveToElementText(startNode.parentNode);
		}
		for (var i=0; i<startNode.length; i++) {
			if (range.compareEndPoints("StartToStart",testRange)==0) {
				startOffset = i;
				break;
			} else if (range.compareEndPoints("StartToStart",testRange)==1) {
				startOffset = i+1;
			}
			testRange.moveStart("character", 1);
		}
	}
	return startOffset;
}

function contenteditable_selection_range_endOffset(range) {
	var endOffset = 0;
	var endNode = contenteditable_selection_range_endNode(range);
	if (endNode) {
		var testRange = contenteditable_createrange();
		if (endNode.nextSibling) {
			try {
				testRange.moveToElementText(endNode.nextSibling);
			} catch(e) {
				try {
					testRange = contenteditable_createtextrange();
					testRange.moveToElementText(endNode.nextSibling);
				} catch (e) {
				}
			}
		} else {
			testRange.moveToElementText(endNode.parentNode);
			if (endNode.nodeName == "#text") {
				if ((testRange.htmlText == testRange.text) && (testRange.text == endNode.nodeValue)) {
					// range does not include parent node
				} else {
					testRange.moveEnd("character", -1);
					if ((testRange.htmlText == testRange.text) && (testRange.text == endNode.nodeValue)) {
						// adjusted range does not include parent node
					} else {
						// restore range
						testRange.moveEnd("character", 1);
					}
				}
			}
		}
		for (var i=endNode.length; i>=0; i--) {
			if (range.compareEndPoints("EndToEnd",testRange)==0) {
				endOffset = i;
				break;
			} else if (range.compareEndPoints("EndToEnd",testRange)==-1) {
				endOffset = i-1;
			}
			testRange.moveEnd("character", -1);
		}
	}
	return endOffset;
}

function contenteditable_selection_range_deletenodes(range) {
	var parentnode = contenteditable_selection_range_parentNode(range);
	var startnode = contenteditable_selection_range_startNode(range);
	var endnode = contenteditable_selection_range_endNode(range);
	var startoffset = contenteditable_selection_range_startOffset(range);
	var endoffset = contenteditable_selection_range_endOffset(range);
	var node=startnode;
	while (node) {
		var nextnode = node.nextSibling;
		if (contenteditable_selection_range_contains(range,node)) {
			node.parentNode.removeChild(node);
		}
		if (nextnode!=endnode) {
			node = nextnode;
		} else {
			node = false;
		}
	}
	if (range.htmlText.match(new RegExp("^"+startnode.outerHTML))) {
		startnode.parentNode.removeChild(startnode);
	}
	if (range.htmlText.match(new RegExp(endnode.outerHTML+"$")) && (startnode!=endnode)) {
		endnode.parentNode.removeChild(endnode);
	}
}

function contenteditable_selection_range_contains(contentRange,node,partially) {
	var contains = false;
	if (node.nodeName == "#text") node = node.parentNode;
	if (! contentRange) contentRange = contenteditable_selection_range();
	var testRange = false;
	try {
		testRange = contenteditable_createrange();
		testRange.moveToElementText(node);
	} catch (e) {
		testRange = false;
	}
	if (! testRange) {
		try {
			testRange = contenteditable_createtextrange();
			testRange.moveToElementText(node);
		} catch (e) {
			testRange = false;
		}
	}
	if (node.nodeName != "#text") {
		testRange.moveToElementText(node);
		if ((contentRange.compareEndPoints("StartToStart",testRange)==-1) && (contentRange.compareEndPoints("StartToEnd",testRange)==-1) && (contentRange.compareEndPoints("EndToStart",testRange)==1) && (contentRange.compareEndPoints("EndToEnd",testRange)==1)) {
			contains = true;
		} else if (partially && (contentRange.compareEndPoints("StartToEnd",testRange)==-1) && (contentRange.compareEndPoints("EndToStart",testRange)==1)) {
			contains = true;
		}
	}
	return contains;
}

function contenteditable_selection_contains(tagName) {
	var range = contenteditable_selection_range();
	var parentnode = contenteditable_selection_range_parentNode(range);
	if (parentnode) {
		var nodes = parentnode.getElementsByTagName(tagName);
		for (var i=0; i<nodes.length; i++) {
			if (contenteditable_selection_range_contains(range,nodes[i],true)) {
				return true;
			}
		}
	}
	return false;
}

function contenteditable_selection_range(contentSelection, preparePasteHTML) {
	if (! contentSelection) contentSelection = contenteditable_selection();
	if (contentSelection) {
		if (contentSelection.type == 'Control') {
			var range = contenteditable_createrange();
			try {
				range.moveToElementText(contentSelection.createRange()(0));
			} catch(e) {
				try {
					range.moveToElementText(contentSelection.createRange().item(0));
				} catch(e) {
					range = contenteditable_createtextrange();
					try {
						range.moveToElementText(contentSelection.createRange()(0));
					} catch(e) {
						try {
							range.moveToElementText(contentSelection.createRange().item(0));
						} catch(e) {
						}
					}
				}
			}
			return range;
		} else {
			// MSIE pasteHTML may not work if selection is empty unless contentEditable container is focused
			if ((contentSelection.type == 'None') && contenteditable_focused_contentwindow().getSelection && preparePasteHTML) {
				var selection = contenteditable_focused_contentwindow().getSelection();
				if (selection && selection.rangeCount && selection.getRangeAt) {
					range = selection.getRangeAt(0);
					var container = contenteditable_contenteditable_container(range.commonAncestorContainer);
					if (container && container.focus) {
						container.focus();
					}
				}
			}
			try {
				range = contentSelection.createRange();
				return range;
			} catch(e) {
				return false;
			}
		}
	}
}

function contenteditable_createrange() {
	try {
		return document.selection.createRange();
	} catch(e) {
		try {
			return contenteditable_focused_contentwindow().document.selection.createRange();
		} catch(e) {
			return;
		}
	}
}

function contenteditable_createtextrange() {
	return contenteditable_focused_contentwindow().document.body.createTextRange();
}

function contenteditable_createcontrolrange() {
	return contenteditable_focused_contentwindow().document.body.createControlRange();
}



function contenteditable_selection_container(tagName, ignoreCache, useBookmark) {
	if (useBookmark && webeditor._bookmark) {
		contenteditable_selection_bookmark(webeditor._bookmark);
	}
	var return_container = false;
	var range = contenteditable_selection_range();
	var container_exact = false;
	var container = false;
	if (range && range.parentElement && tagName && (range.parentElement().tagName == tagName.toUpperCase())) {
		container = range.parentElement();
	} else if (contenteditable_selection_range_control()) {
		try {
			container = contenteditable_selection().createRange()(0);
		} catch (e) {
			try {
				container = contenteditable_selection().createRange().item(0);
			} catch(e) {
			}
		}
	} else {
		container = contenteditable_selection_range_parentNode();
		if (tagName && container && container.firstChild && container.lastChild && (container.firstChild == container.lastChild) && (container.firstChild.nodeName != "#text#")) {
			container = container.firstChild;
		}
	}
	if ((tagName == "object") && container) {
		for (var node=container.firstChild; node; node=node.nextSibling) {
			if (node.tagName == "OBJECT") {
				container = node;
			}
		}
	}
	if (! container.tagName) container = container.parentNode;
	if (webeditor.selection_node && container) {
		var selection_node = container;
		while (selection_node && (webeditor.selection_node != selection_node.nodeName)) {
			selection_node = selection_node.parentNode;
		}
		if (selection_node) container = selection_node;
	}
//	while (container && container.childNodes && (container.childNodes.length == 1) && (container.firstChild.nodeName != "#text")) {
//		container = container.firstChild;
//	}
	if (tagName) {
		var selection_node = container_exact || container || false;
		while (selection_node && (tagName.toUpperCase() != selection_node.tagName)) {
			selection_node = selection_node.parentNode;
		}
		return_container = selection_node;
	} else {
		if (contenteditable_focused_document()) {
			return_container = container_exact || container || contenteditable_focused_document().body;
		} else {
			return_container = container_exact || container;
		}
	}
	if (return_container && return_container.ownerDocument && return_container.ownerDocument.body && (return_container.ownerDocument.body.className == "WebEditor")) {
		return return_container;
	} else if (return_container && contenteditable_contenteditable_container(return_container)) {
		return return_container;
	} else if ((! tagName) && contenteditable_focused_document()) {
		return contenteditable_focused_document().body;
	} else {
		return false;
	}
}



function contenteditable_selection_all() {
	if (webeditor.contentEditable || (contenteditable_focused_contentwindow() == window)) {
		var container = contenteditable_selection_range_parentNode();
		while (container && (container.contentEditable != "true")) {
			container = container.parentNode;
		}
		if (container) {
			contenteditable_selection_node(container);
		}
	} else {
		contenteditable_selection_node(contenteditable_focused_document().body);
	}
}



function contenteditable_selection_node(node) {
	webeditor.selection_node = node.nodeName;
	// MSIE may not select everything when BODY is selected - start may be set to first text node instead of first non-text node - no known workaround
	if ((node.nodeName == "TABLE") || (node.nodeName == "IMG") || (node.nodeName == "INPUT") || (node.nodeName == "SELECT") || (node.nodeName == "TEXTAREA") || (node.nodeName == "VIDEO") || (node.nodeName == "AUDIO")) {
		try {
			var range = contenteditable_createcontrolrange();
			range.addElement(node);
			range.select();
		} catch(e) {
		}
	} else {
		var range = contenteditable_createrange();
		if (range) {
			range.collapse();
			if (range.moveToElementText) {
				try {
					range.moveToElementText(node);
					range.select();
				} catch(e) {
					try {
						range = contenteditable_createtextrange();
						range.moveToElementText(node);
						range.select();
					} catch(e) {
					}
				}
			} else {
				try {
					range = contenteditable_createtextrange();
					range.moveToElementText(node);
					range.select();
				} catch(e) {
				}
			}
		}
	}
}



function contenteditable_selection_cells(contentSelection) {
	var range = contenteditable_selection_range();
	var table = contenteditable_selection_range_parentNode();
	while ((table.tagName == "THEAD") || (table.tagName == "TBODY") || (table.tagName == "TFOOT") || (table.tagName == "TR") || (table.tagName == "TD")) {
		table = table.parentNode;
	}
	if (table.tagName == "TABLE") {
		// MSIE as of v6.0 does not handle cell selection across rows "correctly".
		// It is not possible to select a square of cells.
		// Trailing and leading cells across rows are included in the selection.
		// Simulate a square selection of cells by finding first column in first row and last column in last row.
		var firstColumn = -1;
		var firstRow = -1;
		var lastColumn = -1;
		var lastRow = -1;
		for (var i=0; i<table.rows.length; i++) {
			for (var j=0; j<table.rows[i].cells.length; j++) {
				// create new range to use for comparison of current selection range to each tag element range
				var element = contenteditable_createrange();
				// set element range for this tag
				if (element.moveToElementText) {
					element.moveToElementText(table.rows[i].cells[j]);
					// test if current selection range is equal to or part of this tag element range
					if ((range.compareEndPoints("EndToStart",element) == 1) && (range.compareEndPoints("StartToEnd",element) == -1)) {
						if (firstColumn == -1) firstColumn = j;
						if (firstRow == -1) firstRow = i;
						lastColumn = j;
						lastRow = i;
					}
				}
			}
		}
		if ((firstRow > -1) && (lastRow > -1) && (firstColumn > -1) && (lastColumn > -1)) {
			var cells = new Array();
			for (var i=firstRow; i<=lastRow; i++) {
				for (var j=firstColumn; j<=lastColumn; j++) {
					if (! cells[i-firstRow]) cells[i-firstRow] = new Array();
					cells[i-firstRow].push(table.rows[i].cells[j]);
				}
			}
			if (cells.length) return cells;
		}
	}
}



function contenteditable_selection_bookmark(bookmark) {
	if (bookmark) {
		if (typeof(bookmark) == "string") {
			var range;
			if (range = contenteditable_selection_range()) {
				try {
					range.moveToBookmark(bookmark);
					range.select();
					range.scrollIntoView();
				} catch(e) {
				}
			}
//		} else if (bookmark.startContainer && bookmark.endContainer) {
//				range = contenteditable_focused_contentwindow().document.createRange();
//				range.setStart(bookmark.startContainer, bookmark.startOffset);
//				range.setEnd(bookmark.endContainer, bookmark.endOffset);
//				var selection = contenteditable_focused_contentwindow().getSelection();
//				selection.removeAllRanges();
//				selection.addRange(range);
//
		} else {
			contenteditable_selection_node(bookmark);
		}
		if (contenteditable_focused_contentwindow()) contenteditable_focused_contentwindow().focus();
	} else {
		if (contenteditable_selection_range_control()) {
			bookmark = contenteditable_selection_container();
//		} else if ((contenteditable_selection().type.toLowerCase() == "none") && contenteditable_focused_contentwindow().getSelection)  {
//			var range = contenteditable_focused_contentwindow().getSelection().getRangeAt(0);
//			bookmark = {};
//			bookmark.startContainer = range.startContainer;
//			bookmark.startOffset = range.startOffset;
//			bookmark.endContainer = range.endContainer;
//			bookmark.endOffset = range.endOffset;
//			bookmark.commonAncestorContainer = range.commonAncestorContainer;
		} else {
			var range;
			if (range = contenteditable_selection_range()) {
				bookmark = range.getBookmark();
			}
		}
	}
	return bookmark;
}





function contenteditable_expanded_node(range) {
	var expandednode = false;
	try {
		var node = range.parentElement();
		var parentrange = contenteditable_createrange();
		parentrange.moveToElementText(node);
		while ((node.nodeName != "BODY") && (range.compareEndPoints("StartToStart",parentrange) == 0) && (range.compareEndPoints("EndToEnd",parentrange) == 0)) {
			expandednode = node;
			node = node.parentNode;
			parentrange.moveToElementText(node);
		}
	} catch(e) {
	}
	return expandednode;
}

function contenteditable_collapsed_node(node) {
	while (node && node.childNodes && (node.childNodes.length == 1)) {
		node = node.firstChild;
	}
	var parentrange = contenteditable_createrange();
	parentrange.moveToElementText(node);
	return node;
}

function contenteditable_setContent_body(content, id, iframe) {
	if (iframe && iframe.contentWindow && iframe.contentWindow.document && iframe.contentWindow.document.body) {
		iframe.contentWindow.document.body.innerHTML = "";
		contenteditable_selection_node(iframe.contentWindow.document.body);
		if (webeditor.setcontentbodydocumentwrite) {
			var html = iframe.contentWindow.document.documentElement.innerHTML;
			html = html.replace(new RegExp("<body([^>]*)>(.*)</body>", "i"), "<body$1>" + content.replace(/\$/g,"$$$$") + "</body>");
			iframe.contentWindow.document.open() ;
			iframe.contentWindow.document.write(html);
			iframe.contentWindow.document.close() ;
			for (var i=0; i<document.getElementsByTagName('iframe').length; i++) {
				if (iframe == document.getElementsByTagName('iframe').item(i)) {
					contenteditable_event_handler(iframe.contentWindow, "focus", contenteditable_onfocus[i]);
					contenteditable_event_handler(iframe.contentWindow, "blur", contenteditable_onblur[i]);
					contenteditable_event_handler(iframe.contentWindow.document.body, "focus", contenteditable_onfocus[i]);
					contenteditable_event_handler(iframe.contentWindow.document.body, "blur", contenteditable_onblur[i]);
					contenteditable_event_handler(iframe.contentWindow.document, "keydown", contenteditable_event);
					contenteditable_event_handler(iframe.contentWindow.document, "keyup", contenteditable_event);
					contenteditable_event_handler(iframe.contentWindow.document, "keypress", contenteditable_event);
					contenteditable_event_handler(iframe.contentWindow.document, "mousedown", contenteditable_event);
					contenteditable_event_handler(iframe.contentWindow.document, "mouseup", contenteditable_event);
					contenteditable_event_handler(iframe.contentWindow.document, "drag", contenteditable_event);
					contenteditable_event_handler(iframe.contentWindow.document, "dragstart", contenteditable_event);
					contenteditable_event_handler(iframe.contentWindow.document, "dragend", contenteditable_event);
					contenteditable_event_handler(iframe.contentWindow.document, "dragenter", contenteditable_event);
					contenteditable_event_handler(iframe.contentWindow.document, "dragover", contenteditable_event);
					contenteditable_event_handler(iframe.contentWindow.document, "dragleave", contenteditable_event);
					contenteditable_event_handler(iframe.contentWindow.document, "drop", contenteditable_event);
					if (webeditor.contextmenus) contenteditable_event_handler(iframe.contentWindow.document, "contextmenu", contenteditable_contextmenu);
				}
			}
			contenteditable_event_paste_fix(iframe.contentWindow.document.body);
		} else if (webeditor.setcontentbodyinnerhtml) {
			iframe.contentWindow.document.body.innerHTML = content;
			contenteditable_event_paste_fix(iframe.contentWindow.document.body);
		} else {
			contenteditable_pasteContent(content, id);
		}
		contenteditable_setBody(iframe.contentWindow.document.body, iframe.id)
	}
}

function contenteditable_insertNodeAtSelection(contentWindow, insertNode, insertHTML, collapsenode) {
	var insertedNode = insertNode;
	// MSIE9 may loose 'Control' selection on focus
	// if (contentWindow) contentWindow.focus();
	if (contentWindow && ((!contenteditable_selection()) || (contenteditable_selection().type == 'None'))) contentWindow.focus();
	var selection = contenteditable_selection(contentWindow);
	try {
		var range = contenteditable_selection_range(selection, true);
//		if (! contenteditable_selection_range_is_webeditor(range)) return;
//QQQQQ TODO handle contenteditable
		if (insertNode.outerHTML) {
			if ((typeof(selection) == "object") && (selection.type == 'Control')) {
				var selectionNode = null;
				try {
					selectionNode = selection.createRange()(0);
				} catch(e) {
					try {
						selectionNode = selection.createRange().item(0);
					} catch(e) {
					}
				}
				var parentNode = selectionNode.parentNode;
				parentNode.replaceChild(insertNode, selectionNode);
			} else {
				insertedNode = contenteditable_insertNodeAtSelection_outerHTML(range, insertNode, insertHTML, collapsenode);
				contenteditable_event_paste_fix(insertedNode);
				range = contenteditable_selection_range(false, true);
				// MSIE may fail to select BR tag and to collapse caret after it - select and collapse to start of the next node
				if ((insertedNode.nodeType == 1) && (insertedNode.tagName = "BR")) {
					if (insertedNode.nextSibling) {
						range.moveToElementText(insertedNode.nextSibling);
						range.collapse(true);
						range.select();
					}
				} else {
					range.moveToElementText(insertedNode);
					range.collapse(false);
					range.select();
				}
			}
		} else {
			var expandednode = contenteditable_expanded_node(range);
			if (expandednode) {
				var new_node = contentWindow.document.createTextNode(insertNode.nodeValue);
				var parent_node = expandednode.parentNode;
				parent_node.replaceChild(new_node, expandednode);
				insertedNode = new_node;
			} else {
				contenteditable_selection_range_deletenodes(range);
//				range.pasteHTML(insertNode.nodeValue);
				range.pasteHTML(contenteditable_encodeScriptTags(insertNode.nodeValue));
				// MISSING: insertedNode = [inserted node in document DOM]
			}
		}
	} catch(e) {
		try {
			var range = null;
			try {
				range = contenteditable_selection().createRange()(0);
			} catch(e) {
				try {
					range = contenteditable_selection().createRange().item(0);
				} catch(e) {
				}
			}
//			if (! contenteditable_selection_range_is_webeditor(range)) return;
//QQQQQ TODO handle contenteditable
			if (insertNode.outerHTML) {
				insertedNode = contenteditable_insertNodeAtSelection_outerHTML(range, insertNode, insertHTML, collapsenode);
				contenteditable_event_paste_fix(insertedNode);
				range = contenteditable_selection_range();
				range.moveToElementText(insertedNode);
				range.collapse(false);
				range.select();
			} else {
//				range.outerHTML = insertNode.nodeValue;
				range.outerHTML = contenteditable_encodeScriptTags(insertNode.nodeValue);
				// MISSING: insertedNode = [inserted node in document DOM]
			}
		} catch(e) {
		}
	}
	return insertedNode;
}

function contenteditable_insertNodeAtSelection_outerHTML(range, insertNode, insertHTML, collapsenode) {
	var remove_appended_p = false;
	var insertedNode = insertNode;
	if ((! insertNode.childNodes) || (! insertNode.childNodes.length)) {
		if (insertNode.outerHTML) {
			if (! insertNode.id) insertNode.id = "HardCoreWebContentEditorInsertNodeAtSelectionDummy";
			contenteditable_selection_range_deletenodes(range);
			range.pasteHTML(insertNode.outerHTML);
			insertedNode = contenteditable_focused_document().getElementById('HardCoreWebContentEditorInsertNodeAtSelectionDummy');
			if (! insertedNode) {
				if (range && range.parentElement() && (range.parentElement().id == 'HardCoreWebContentEditorInsertNodeAtSelectionDummy')) insertedNode = range.parentElement();
				if (range && range.parentElement() && range.parentElement().firstChild && (range.parentElement().firstChild.id == 'HardCoreWebContentEditorInsertNodeAtSelectionDummy')) insertedNode = range.parentElement().firstChild;
			}
			if (insertedNode && (insertedNode.id == "HardCoreWebContentEditorInsertNodeAtSelectionDummy")) contenteditable_removeAttribute(insertedNode, "id");
		} else {
			range.pasteHTML(insertNode.nodeValue);
			// MISSING: insertedNode = [inserted node in document DOM]
		}
	} else {
		try {
			var expandednode = contenteditable_expanded_node(range);
			if (collapsenode) {
				expandednode = contenteditable_collapsed_node(expandednode);
			}
			if (expandednode) {
				if (expandednode.nodeName == "BODY") {
					expandednode.innerHTML = '<div id="HardCoreWebContentEditorInsertNodeAtSelectionDummy">&nbsp;</div>';
				} else {
					expandednode.outerHTML = '<div id="HardCoreWebContentEditorInsertNodeAtSelectionDummy">&nbsp;</div>';
				}
			} else if ((range.text == range.htmlText) && (insertNode.nodeName != "DIV")) {
				range.pasteHTML('<span id="HardCoreWebContentEditorInsertNodeAtSelectionDummy">&nbsp;</span>');
			} else {
				var parentnode = range.parentElement();
				while ((parentnode.nodeName != "SPAN") && (parentnode.nodeName != "DIV") && (parentnode.nodeName != "P") && (parentnode.nodeName != "BODY") && (parentnode.nodeName != "HTML")) {
					parentnode = parentnode.parentNode;
				}
				if (parentnode.nodeName == "SPAN") {
					contenteditable_selection_range_deletenodes(range);
					range.pasteHTML('<span id="HardCoreWebContentEditorInsertNodeAtSelectionDummy">&nbsp;</span>');
				} else {
					contenteditable_selection_range_deletenodes(range);
					if (((parentnode.nodeName == "DIV") || (parentnode.nodeName == "P")) && (insertNode.nodeName != "DIV")) {
						range.pasteHTML('<span id="HardCoreWebContentEditorInsertNodeAtSelectionDummy">&nbsp;</span>');
					} else {
						range.pasteHTML('<div id="HardCoreWebContentEditorInsertNodeAtSelectionDummy">&nbsp;</div>');
					}
					remove_appended_p = true;
				}
			}
		} catch(e) {
			try {
				contenteditable_selection_range_deletenodes(range);
				var parentnode = range.parentElement();
				while ((parentnode.nodeName != "SPAN") && (parentnode.nodeName != "DIV") && (parentnode.nodeName != "P") && (parentnode.nodeName != "BODY") && (parentnode.nodeName != "HTML")) {
					parentnode = parentnode.parentNode;
				}
				if (parentnode.nodeName == "SPAN") {
					contenteditable_selection_range_deletenodes(range);
					range.pasteHTML('<span id="HardCoreWebContentEditorInsertNodeAtSelectionDummy">&nbsp;</span>');
				} else {
					contenteditable_selection_range_deletenodes(range);
					if (((parentnode.nodeName == "DIV") || (parentnode.nodeName == "P")) && (insertNode.nodeName != "DIV")) {
						range.pasteHTML('<span id="HardCoreWebContentEditorInsertNodeAtSelectionDummy">&nbsp;</span>');
					} else {
						range.pasteHTML('<div id="HardCoreWebContentEditorInsertNodeAtSelectionDummy">&nbsp;</div>');
					}
					remove_appended_p = true;
				}
			} catch(e) {
				contenteditable_selection_range_deletenodes(range);
				range.pasteHTML('<div id="HardCoreWebContentEditorInsertNodeAtSelectionDummy">&nbsp;</div>');
			}
		}
		var dummyNode = contenteditable_focused_document().getElementById('HardCoreWebContentEditorInsertNodeAtSelectionDummy');
		if (! dummyNode) {
			if (range && range.parentElement() && (range.parentElement().id == 'HardCoreWebContentEditorInsertNodeAtSelectionDummy')) dummyNode = range.parentElement();
			if (range && range.parentElement() && range.parentElement().firstChild && (range.parentElement().firstChild.id == 'HardCoreWebContentEditorInsertNodeAtSelectionDummy')) dummyNode = range.parentElement().firstChild;
		}
		if (dummyNode) {
			var previousSibling = dummyNode.previousSibling;
			var nextSibling = dummyNode.nextSibling;
			var parentNode = dummyNode.parentNode;
			if (false && insertHTML) {
				// range.pasteHTML may generate invalid HTML code with overlapping tags and broken DOM
				contenteditable_selection_node(dummyNode);
				var range = contenteditable_selection_range();
				range.pasteHTML(insertHTML);
			} else {
				// MSIE may have broken/removed PARAM tags inside OBJECT tag when insertNode.innerHTML was set
				try {
					dummyNode.outerHTML = insertNode.outerHTML;
				} catch (e) {
					// MSIE8 may fail with Unknown runtime error
					try {
						contenteditable_selection_node(dummyNode);
						var range = contenteditable_selection_range();
						contenteditable_selection_range_deletenodes(range);
						range.pasteHTML(insertNode.outerHTML);
						insertedNode = contenteditable_focused_document().getElementById('HardCoreWebContentEditorInsertNodeAtSelectionDummy');
						if (! insertedNode) {
							if (range && range.parentElement() && (range.parentElement().id == 'HardCoreWebContentEditorInsertNodeAtSelectionDummy')) insertedNode = range.parentElement();
							if (range && range.parentElement() && range.parentElement().firstChild && (range.parentElement().firstChild.id == 'HardCoreWebContentEditorInsertNodeAtSelectionDummy')) insertedNode = range.parentElement().firstChild;
						}
						if (insertedNode && (insertedNode.id == "HardCoreWebContentEditorInsertNodeAtSelectionDummy")) contenteditable_removeAttribute(insertedNode, "id");
					} catch (e) {
						alert("ERROR:contenteditable_insertNodeAtSelection_outerHTML:"+insertNode.nodeName);
					}
				}
			}
			if (previousSibling) {
				insertedNode = previousSibling.nextSibling;
			} else {
				insertedNode = parentNode.firstChild;
			}
			if (remove_appended_p && nextSibling) {
			}
			contenteditable_fix_copied_node(insertNode, insertedNode, "IMG", "src");
			contenteditable_fix_copied_node(insertNode, insertedNode, "A", "href");
		}
	}
	return insertedNode;
}
function contenteditable_fix_copied_node(oldnode, newnode, tagname, attributename) {
	var oldtags = oldnode.getElementsByTagName(tagname);
	var newtags = newnode.getElementsByTagName(tagname);
	if (oldtags.length == newtags.length) {
		for(var i=0; i<oldtags.length; i++) {
			if (contenteditable_getAttribute(oldtags[i], attributename) != contenteditable_getAttribute(newtags[i], attributename)) {
				contenteditable_setAttribute(newtags[i], attributename, contenteditable_getAttribute(oldtags[i], attributename));
			}
		}
	}
}





function contenteditable_useCSS() {
	try {
		contenteditable_focused_document().execCommand("styleWithCSS", 0, true);
	} catch (e) {
		try {
			contenteditable_focused_document().execCommand("useCSS", false, false);
		} catch (e) {
		}
	}

}

function contenteditable_BlockDirLTR(id) {
//	contenteditable_execcommand(id);
	webeditor.direction = "ltr";
	contenteditable_focused_contentwindow().document.dir = 'ltr';
}

function contenteditable_BlockDirRTL(id) {
//	contenteditable_execcommand(id);
	webeditor.direction = "rtl";
	contenteditable_focused_contentwindow().document.dir = 'rtl';
}





function contenteditable_formatblock_query() {
	return ('' + contenteditable_focused_document().queryCommandValue("formatblock")).toLowerCase();
}



function contenteditable_formatblock(command,value) {
	if (value == "<p>") value = "normal";
if ((value == "<section>") || (value == "<nav>") || (value == "<article>") || (value == "<aside>") || (value == "<header>") || (value == "<footer>") || (value == "<main>") || (value == "<figure>") || (value == "<figcaption>") || (value == "<details>") || (value == "<summary>")) {
		var tagname = value.replace(/^<|>$/g, '');
		var text = contenteditable_selection_text();
		var container = contenteditable_selection_container(tagname);
		if (container) {
			// ignore existing
		} else if (text != "") {
			contenteditable_pasteContent("<" + tagname + ">" + text + "</" + tagname + ">");
//			contenteditable_execcommand("insertHTML", "<" + tagname + ">" + text + "</" + tagname + ">");
		} else {
			container = contenteditable_selection_range_startNode();
			if (container) {
				text = container.nodeValue;
				text = text.replace(/^\s+|\s+$/g, '');
				contenteditable_selection_node(container);
				contenteditable_pasteContent("<" + tagname + ">" + text + "</" + tagname + ">");
//				contenteditable_execcommand("insertHTML", "<" + tagname + ">" + text + "</" + tagname + ">");
			}
		}
	} else {
		contenteditable_execcommand(command,value);
	}
}



function contenteditable_formatclass(command,value) {
	var range = contenteditable_selection_range(false, true);
	var container = contenteditable_selection_range_parentNode();
	if ((range.text == range.htmlText) && (range.text != '')) {
		range.pasteHTML('<span class="'+value+'">' + range.text + '</span>');
	} else if (container.outerHTML.indexOf(range.htmlText) < 0) {
		range.pasteHTML('<div class="'+value+'">' + range.htmlText + '</div>');
	} else if ((container.childNodes.length == 1) && (container.childNodes[0].nodeType == 3)) { // TEXT NODE
		contenteditable_formatclass_attribute(container, value);
	} else if (container.nodeName == "BODY") {
		range.pasteHTML('<div class="'+value+'">' + range.htmlText + '</div>');
	} else {
		contenteditable_formatclass_range(command,value,container,range);
	}
}
function contenteditable_formatclass_range(command,value,container,range) {
	// create new range to use for comparison of current selection range to each tag element range
	var element;
	element = range.duplicate();
	// set element range for this tag
	if ((element.moveToElementText) && (container.nodeType != 3)) {
		element.moveToElementText(container);
		// test if current selection range is equal to or part of this tag element range
		if ((range.compareEndPoints("StartToStart",element) == 0) && (range.compareEndPoints("EndToEnd",element) == 0)) {
			if (container.nodeName == "BODY") {
				var node = contenteditable_focused_contentwindow().document.createElement("span");
				contenteditable_formatclass_attribute(node, value);
				while (container.childNodes[0]) {
					node.appendChild(container.childNodes[0]);
				}
				contenteditable_insertNodeAtSelection(contenteditable_focused_contentwindow(), node);
			} else {
				contenteditable_formatclass_attribute(container, value);
			}
		} else {
			for (var i=0; i<container.childNodes.length; i++) {
				var node = container.childNodes[i];
				if (node.nodeType == 3) { // TEXT NODE
					contenteditable_formatclass_attribute(node.parentNode, value);
				} else {
					// create new range to use for comparison of current selection range to each tag element range
					var element;
					element = range.duplicate();
					// set element range for this tag
					if (element.moveToElementText) {
						element.moveToElementText(node);
						// test if current selection range is equal to or part of this tag element range
						if ((range.compareEndPoints("EndToStart",element) == 1) && (range.compareEndPoints("StartToEnd",element) == -1)) {
							if ((range.compareEndPoints("StartToStart",element) == 1) && (range.compareEndPoints("EndToEnd",element) == 1)) {
								// start of range includes element partially
								contenteditable_formatclass_range(command,value,node,range);
							} else if ((range.compareEndPoints("StartToStart",element) == -1) && (range.compareEndPoints("EndToEnd",element) == -1)) {
								// end of range includes element partially
								contenteditable_formatclass_range(command,value,node,range);
							} else if ((range.compareEndPoints("StartToStart",element) != 1) && (range.compareEndPoints("EndToEnd",element) != -1)) {
								// range includes element completely
								contenteditable_formatclass_attribute(node, value);
							}
						}
					}
				}
			}
		}
	}
}





function contenteditable_execcommand(command, value) {
	try {
		// Microsoft Internet Explorer may indent using BLOCKQUOTE instead of CSS
		if ((command == "indent") && (webeditor.indentAsDiv)) {
			var range = contenteditable_selection_range();
			var parentnode = contenteditable_selection_range_parentNode(range);
			var startnode = contenteditable_selection_range_startNode(range);
			var endnode = contenteditable_selection_range_endNode(range);
			var node = contenteditable_selection_container();
			if (node && startnode && endnode && (startnode == endnode) && ((startnode.tagName == "P") || (startnode.tagName == "DIV"))) {
				node = startnode;
			} else if (node && startnode && endnode && (startnode == endnode) && startnode.parentNode && ((startnode.parentNode.tagName == "P") || (startnode.parentNode.tagName == "DIV"))) {
				node = startnode.parentNode;
				for (var i=0; i<node.childNodes.length; i++) {
					if (node.childNodes[i].tagName && node.childNodes[i].tagName.match(new RegExp("^(TEXTAREA|PRE|TABLE|THEAD|TBODY|TFOOT|TR|TD|DIR|MENU|DL|OL|UL|FORM|SELECT|H1|H2|H3|H4|H5|H6|OBJECT|EMBED|NOEMBED|MAP|IFRAME|DIV|P|BR|BODY)$"))) {
						// parentnode content is not "simple" - only use text node
						node = null;
						break;
					}
				}
			} else if (node && startnode && endnode && startnode.parentNode && ((startnode.parentNode.tagName == "P") || (startnode.parentNode.tagName == "DIV"))) {
				node = startnode.parentNode;
				for (var i=0; i<node.childNodes.length; i++) {
					if (node.childNodes[i].tagName && node.childNodes[i].tagName.match(new RegExp("^(TEXTAREA|PRE|TABLE|THEAD|TBODY|TFOOT|TR|TD|DIR|MENU|DL|OL|UL|FORM|SELECT|H1|H2|H3|H4|H5|H6|OBJECT|EMBED|NOEMBED|MAP|IFRAME|DIV|P|BR|BODY)$"))) {
						// parentnode content is not "simple" - only use text node
						node = null;
						break;
					}
				}
			} else if (node && node.parentNode.tagName.match(new RegExp("^(TEXTAREA|PRE|TABLE|THEAD|TBODY|TFOOT|TR|TD|DIR|MENU|DL|OL|UL|FORM|SELECT|H1|H2|H3|H4|H5|H6|OBJECT|EMBED|NOEMBED|MAP|IFRAME|DIV|P|BR|BODY)$"))) {
				// ok
			} else {
				node = false;
			}
			if ((node !== null) && (node || (node = contenteditable_selection_container('p')) || (node = contenteditable_selection_container('div')))) {
				var margin = parseInt(webeditor.cssIndent);
				var unit = "" + webeditor.cssIndent.replace(/^[0-9]+([a-zA-Z]*)[^a-zA-Z]*$/gi, "$1");
				if (node.style.marginLeft) {
					margin = parseInt(node.style.marginLeft) + margin;
				}
				if ((! margin) || (margin <= 0)) {
					margin = parseInt(webeditor.cssIndent);
				}
				if (margin > 0) {
					node.style.marginLeft = "" + margin + unit;
				} else {
					node.style.marginLeft = "";
				}
				return true;
			} else if (node = contenteditable_selection_container()) {
				// expand startnode and endnode to their parent nodes directly under their common range parentnode
				while (startnode.parentNode != parentnode) {
					startnode = startnode.parentNode;
				}
				while (endnode.parentNode != parentnode) {
					endnode = endnode.parentNode;
				}

				// expand startnode and endnode to block/paragraph parentnode
				while (startnode && (! startnode.parentNode.tagName.match(new RegExp("^(TEXTAREA|PRE|TABLE|THEAD|TBODY|TFOOT|TR|TD|DIR|MENU|DL|OL|UL|FORM|SELECT|H1|H2|H3|H4|H5|H6|OBJECT|EMBED|NOEMBED|MAP|IFRAME|DIV|P|BR|BODY)$")))) {
					startnode = startnode.parentNode;
				}
				while (endnode && (! endnode.parentNode.tagName.match(new RegExp("^(TEXTAREA|PRE|TABLE|THEAD|TBODY|TFOOT|TR|TD|DIR|MENU|DL|OL|UL|FORM|SELECT|H1|H2|H3|H4|H5|H6|OBJECT|EMBED|NOEMBED|MAP|IFRAME|DIV|P|BR|BODY)$")))) {
					endnode = endnode.parentNode;
				}

				// expand startnode and endnode to previous/next "block" sibling nodes
				while (startnode && startnode.previousSibling && ((startnode.previousSibling.nodeType == 3) || (startnode.previousSibling.tagName && (! startnode.previousSibling.tagName.match(new RegExp("^(TEXTAREA|PRE|TABLE|THEAD|TBODY|TFOOT|TR|TD|DIR|MENU|DL|OL|UL|FORM|SELECT|H1|H2|H3|H4|H5|H6|OBJECT|EMBED|NOEMBED|MAP|IFRAME|DIV|P|BR|BODY)$")))))) {
					startnode = startnode.previousSibling;
				}
				while (endnode && endnode.nextSibling && ((endnode.previousSibling.nodeType == 3) || (endnode.nextSibling.tagName && (! endnode.nextSibling.tagName.match(new RegExp("^(TEXTAREA|PRE|TABLE|THEAD|TBODY|TFOOT|TR|TD|DIR|MENU|DL|OL|UL|FORM|SELECT|H1|H2|H3|H4|H5|H6|OBJECT|EMBED|NOEMBED|MAP|IFRAME|DIV|P|BR|BODY)$")))))) {
					endnode = endnode.nextSibling;
				}

				// get nodes to be indented
				var nodes = new Array();
				nodes[nodes.length] = startnode;
				for (node=startnode.nextSibling; (node && (node != endnode.nextSibling)); node = node.nextSibling) {
					if (node) nodes[nodes.length] = node;
				}

				// create indented container and move nodes
				var container = contenteditable_focused_document().createElement("div");
				container.style.marginLeft = webeditor.cssIndent;
				startnode.parentNode.insertBefore(container, startnode);
				for (var i=0; i<nodes.length; i++) {
					container.appendChild(nodes[i]);
				}

				// trim leading/trailing whitespace from first/last text nodes
				if ((container.childNodes.length >= 1) && (container.childNodes[0].nodeType == 3)) { // TEXT NODE
					container.childNodes[0].nodeValue = container.childNodes[0].nodeValue.replace(/^[\r\n\t ]/gi, "");
				}
				if ((container.childNodes.length >= 1) && (container.childNodes[container.childNodes.length-1].nodeType == 3)) { // TEXT NODE
					container.childNodes[container.childNodes.length-1].nodeValue = container.childNodes[container.childNodes.length-1].nodeValue.replace(/[\r\n\t ]$/gi, "");
				}
				// trim leading/trailing break nodes
				if (container.previousSibling && (container.previousSibling.tagName == "BR")) {
					container.parentNode.removeChild(container.previousSibling);
				}
				if (container.nextSibling && (container.nextSibling.tagName == "BR")) {
					container.parentNode.removeChild(container.nextSibling);
				}
				return true;
			}
		} else if ((command == "outdent") && (webeditor.indentAsDiv)) {
			var range = contenteditable_selection_range();
			var parentnode = contenteditable_selection_range_parentNode(range);
			var startnode = contenteditable_selection_range_startNode(range);
			var endnode = contenteditable_selection_range_endNode(range);
			var node = contenteditable_selection_container();
			if (node && startnode && endnode && (startnode == endnode) && ((startnode.tagName == "P") || (startnode.tagName == "DIV"))) {
				node = startnode;
			} else if (node && startnode && endnode && (startnode == endnode) && startnode.parentNode && ((startnode.parentNode.tagName == "P") || (startnode.parentNode.tagName == "DIV"))) {
				node = startnode.parentNode;
			} else if (node && node.parentNode.tagName.match(new RegExp("^(TEXTAREA|PRE|TABLE|THEAD|TBODY|TFOOT|TR|TD|DIR|MENU|DL|OL|UL|FORM|SELECT|H1|H2|H3|H4|H5|H6|OBJECT|EMBED|NOEMBED|MAP|IFRAME|DIV|P|BR|BODY)$"))) {
				// ok
			} else {
				node = false;
			}
			if (node || (node = contenteditable_selection_container('p')) || (node = contenteditable_selection_container('div'))) {
				var margin = parseInt(webeditor.cssIndent);
				var unit = "" + webeditor.cssIndent.replace(/^[0-9]+([a-zA-Z]*)[^a-zA-Z]*$/gi, "$1");
				if (node.style.marginLeft) {
					margin = parseInt(node.style.marginLeft) - margin;
				} else {
					margin = 0;
				}
				if ((! margin) || (margin <= 0)) {
					margin = 0;
				}
				if (margin > 0) {
					node.style.marginLeft = "" + margin + unit;
				} else {
					node.style.marginLeft = "";
				}
				return true;
			}
		}
		contenteditable_focused_document().body.focus();
		if (webeditor.useCSS) contenteditable_useCSS();
		return contenteditable_focused_document().execCommand(command, false, contenteditable_execcommand_value(command, value));
	} catch(e) {
		return false;
	}
	return true;
}

function contenteditable_execcommand_value(command, value) {
	if ((command == "forecolor") && ((value == "") || (value == "inherit"))) {
		return "";
	} else if ((command == "backcolor") && ((value == "") || (value == "inherit"))) {
		return "";
	}
	return value;
}

function contenteditable_querycommand_value(command, value) {
	return value;
}

function contenteditable_find(command) {
	if (contenteditable_execcommand(command)) {
		return true;
	} else {
		try {
			contenteditable_focused_contentwindow().find();
			return true;
		} catch (e) {
			webeditor.find_window = window.open(webeditor.rootpath+"find.html?editor=webeditor&find=", "find_window", ",width=400,height=150,scrollbars=yes,resizable=yes,status=yes", true);
			return true;
		}
	}
}

function contenteditable_print(command) {
	if (contenteditable_execcommand(command)) {
		return true;
	} else {
		try {
			contenteditable_focused_contentwindow().print();
			return true;
		} catch (e) {
			return false;
		}
	}
}

function contenteditable_specialcharacter(value) {
	if (value) {
		// MSIE execCommand paste may not work if clipboard contains binary image etc.
		// contenteditable_focused_document().execCommand('paste', false, value);
		var selection = contenteditable_selection();
		if (selection.type.toLowerCase() != "none") {
			selection.clear();
		}
		contenteditable_selection_range(false, true).pasteHTML(value);
	}
}





function contenteditable_position(state) {
	var element = contenteditable_positionable();
	if (state) {
		contenteditable_execcommand('2D-Position', true);
	} else if (element) {
		if (element.style.position == "absolute") {
			contenteditable_execcommand('2D-Position', false);
			element.style.position = "";
			element.style.top = "";
			element.style.left = "";
			element.style.zIndex = "";
		} else {
			element.style.position = "absolute";
			contenteditable_execcommand('2D-Position', true);
		}
		if (contenteditable_getAttribute(element, "style") == "") {
			contenteditable_removeAttribute(element, "style");
		}
	}
}

function contenteditable_forwards() {
	var element = contenteditable_positionable();
	if (element) {
		if (element.style && element.style.zIndex && (element.style.zIndex < 1000000)) {
			element.style.zIndex = parseInt(element.style.zIndex) + 1;
		} else {
			element.style.zIndex = 1;
		}
	}
}

function contenteditable_backwards() {
	var element = contenteditable_positionable();
	if (element) {
		if (element.style && element.style.zIndex && (element.style.zIndex > -1000000)) {
			element.style.zIndex = parseInt(element.style.zIndex) - 1;
		} else {
			element.style.zIndex = -1;
		}
	}
}

function contenteditable_front() {
	var element = contenteditable_positionable();
	if (element) {
		zIndex = contenteditable_positionable_front_max();
		if (zIndex < 1000000) {
			zIndex++;
		} else {
			zIndex = 1000000;
		}
		element.style.zIndex = zIndex;
	}
}

function contenteditable_back() {
	var element = contenteditable_positionable();
	if (element) {
		zIndex = contenteditable_positionable_back_min();
		if (zIndex > -1000000) {
			zIndex--;
		} else {
			zIndex = -1000000;
		}
		element.style.zIndex = zIndex;
	}
}

function contenteditable_abovetext() {
	var element = contenteditable_positionable();
	if (element && element.style) {
		if (element.style.zIndex < 0) {
			element.style.zIndex = -element.style.zIndex;
		} else if (element.style.zIndex == 0) {
			element.style.zIndex = 1;
		}
	}
}

function contenteditable_belowtext() {
	var element = contenteditable_positionable();
	if (element && element.style) {
		if (element.style.zIndex > 0) {
			element.style.zIndex = -element.style.zIndex;
		} else if (element.style.zIndex == 0) {
			element.style.zIndex = -1;
		}
	}
}





function contenteditable_viewsource_onfocus(textarea) {
	contenteditable_event_handler(textarea, "focus", contenteditable_onfocus[contenteditable_focused]);
	contenteditable_event_handler(textarea, "blur", contenteditable_onblur[contenteditable_focused]);
}

function contenteditable_viewsource_textarea2html(textarea, iframe) {
	var content = textarea.value;
	content = contenteditable_split_content(iframe.id, content);
	try {
		if (webeditor_custom_encode) content = webeditor_custom_encode(content);
	} catch(e) {
	}
	try {
		if (webeditor_custom_initialize) content = webeditor_custom_initialize(content);
	} catch(e) {
	}
	content = contenteditable_encodeScriptTags(content);
	content = webeditor_empty_content_fix(content);

	// Setting innerHTML may not work properly - changes href/src from relative to absolute - use write instead
	//iframe.contentWindow.document.body.innerHTML = content;
	iframe.contentWindow.document.body.innerHTML = "&nbsp;";
	// MSIE replace may be broken for escaped "\$1" and "\$2" dollar characters in replacement string
//	content = iframe.contentWindow.document.documentElement.outerHTML.replace(new RegExp("(&nbsp;)?(</body>)", "i"), content.replace(/\$([_`'+&0-9]+)/g, "\\\$\\$1")+"$2").replace(/\\(\$)\\([_`'+&0-9]+)/g, "$1$2");
//	iframe.contentWindow.document.write(content);
// MSIE may add web page to web browser "history" on document.write - set content using Javascript DOM instead

	contenteditable_event_handler(iframe.contentWindow, "focus", contenteditable_onfocus[contenteditable_focused]);
	contenteditable_event_handler(iframe.contentWindow.document.body, "focus", contenteditable_onfocus[contenteditable_focused]);
	contenteditable_event_handler(iframe.contentWindow, "blur", contenteditable_onblur[contenteditable_focused]);
	contenteditable_event_handler(iframe.contentWindow.document.body, "blur", contenteditable_onblur[contenteditable_focused]);
	contenteditable_event_handler(iframe.contentWindow.document, "keydown", contenteditable_event);
	contenteditable_event_handler(iframe.contentWindow.document, "keyup", contenteditable_event);
	contenteditable_event_handler(iframe.contentWindow.document, "keypress", contenteditable_event);
	contenteditable_event_handler(iframe.contentWindow.document, "mousedown", contenteditable_event);
	contenteditable_event_handler(iframe.contentWindow.document, "mouseup", contenteditable_event);
	contenteditable_event_handler(iframe.contentWindow.document, "drag", contenteditable_event);
	contenteditable_event_handler(iframe.contentWindow.document, "dragstart", contenteditable_event);
	contenteditable_event_handler(iframe.contentWindow.document, "dragend", contenteditable_event);
	contenteditable_event_handler(iframe.contentWindow.document, "dragenter", contenteditable_event);
	contenteditable_event_handler(iframe.contentWindow.document, "dragover", contenteditable_event);
	contenteditable_event_handler(iframe.contentWindow.document, "dragleave", contenteditable_event);
	contenteditable_event_handler(iframe.contentWindow.document, "drop", contenteditable_event);
	iframe.contentWindow.document.close();

// MSIE may add web page to web browser "history" on document.write - set content using Javascript DOM instead
contenteditable_setContent(content, iframe.id);

}

function contenteditable_viewsource_show() {
	// old viewsource functionality using text encoding
	var html = document.createTextNode(document.getElementsByTagName('iframe').item(contenteditable_focused).contentWindow.document.body.innerHTML);
	document.getElementsByTagName('iframe').item(contenteditable_focused).contentWindow.document.body.innerText = html.toString();
}

function contenteditable_viewsource_hide() {
	// old viewsource functionality using text encoding
	var html = document.getElementsByTagName('iframe').item(contenteditable_focused).contentWindow.document.body.innerText;
	document.getElementsByTagName('iframe').item(contenteditable_focused).contentWindow.document.body.innerHTML = html.toString();
}

function contenteditable_iframe_hide(iframe) {
	iframe.style.display = "none";
}

function contenteditable_iframe_show(iframe) {
	iframe.style.display = "block";
}

function contenteditable_dropdown_hide(iframe) {
	iframe.style.display = "none";
}

function contenteditable_dropdown_show(iframe) {
	iframe.style.display = "block";
}

function contenteditable_dropdown_hidden(iframe) {
	return (iframe.style.display == "none");
}





function contenteditable_setAttribute(node, attribute, value) {
	// MSIE may partially loose attributes and display image incorrectly if resetting width/height
	if ((node.nodeName == "IMG") && ((attribute == "width") || (attribute == "height")) && (contenteditable_getAttribute(node, attribute) == value)) {
		// ignore
	} else {
		contenteditable_removeAttribute(node, attribute);
		try {
			node.setAttribute(attribute, value);
		} catch(e) {
		}
	}
	if ((node.nodeName == "INPUT") && (attribute == "type") && (contenteditable_getAttribute(node, attribute) != value)) {
		RegExp.global = true;
		RegExp.multiline = true;
		if (node.outerHTML.match(/^(<INPUT[^>]+type=)("[a-z]*")([^>]*>)$/gi)) {
			node.outerHTML = node.outerHTML.replace(/^(<INPUT[^>]+type=)("[a-z]*")([^>]*>)$/gi, "$1"+value.replace(/\$/g,"$$$$")+"$3");
		} else if (node.outerHTML.match(/^(<INPUT[^>]+type=)([a-z]+)([^>]*>)$/gi)) {
			node.outerHTML = node.outerHTML.replace(/^(<INPUT[^>]+type=)([a-z]+)([^>]*>)$/gi, "$1"+value.replace(/\$/g,"$$$$")+"$3");
		} else {
			node.outerHTML = node.outerHTML.replace(/^(<INPUT[^>]+)([^>]*>)$/gi, "$1 type="+value.replace(/\$/g,"$$$$")+" $2");
		}
	}
	if ((node.nodeName == "A") && (attribute == "name") && (contenteditable_getAttribute(node, attribute) != value)) {
		node.removeAttribute("NAME");
		node.setAttribute("NAME", value);
	}
	if ((node.nodeName == "INPUT") && (attribute == "name") && (contenteditable_getAttribute(node, attribute) != value)) {
		node.removeAttribute("NAME");
		node.setAttribute("NAME", value);
	}
	if ((node.nodeName == "TEXTAREA") && (attribute == "name") && (contenteditable_getAttribute(node, attribute) != value)) {
		node.removeAttribute("NAME");
		node.setAttribute("NAME", value);
	}
	if ((node.nodeName == "SELECT") && (attribute == "name") && (contenteditable_getAttribute(node, attribute) != value)) {
		node.removeAttribute("NAME");
		node.setAttribute("NAME", value);
	}
	if ((node.nodeName == "MAP") && (attribute == "name") && (contenteditable_getAttribute(node, attribute) != value)) {
		node.removeAttribute("NAME");
		node.setAttribute("NAME", value);
	}
}

function contenteditable_removeAttribute(node, attribute) {
	try {
		node.setAttribute(attribute, "");
		node.removeAttribute(attribute);
	} catch(e) {
	}
	if (attribute == "class") {
		node.removeAttribute("className");
	}
	if ((node.nodeName == "A") && (attribute == "name")) {
		node.removeAttribute("NAME");
	}
	if ((node.nodeName == "INPUT") && (attribute == "name")) {
		node.removeAttribute("NAME");
	}
	if ((node.nodeName == "TEXTAREA") && (attribute == "name")) {
		node.removeAttribute("NAME");
	}
	if ((node.nodeName == "SELECT") && (attribute == "name")) {
		node.removeAttribute("NAME");
	}
}

// MSIE element.getAttribute(attribute) may not work properly - changes href/src from relative to absolute + returns unspecified default values
var contenteditable_getAttribute = false;
if (webeditor.getAttribute_attributes && webeditor.getAttribute_amp) {
	contenteditable_getAttribute = function (node, attribute) {
		var value = "";
		if (node && node.attributes && node.attributes[attribute]) {
			value = node.attributes[attribute].nodeValue;
		} else if (node && node.getAttribute) {
			value = node.getAttribute(attribute,2);
		}
		// MSIE may return null instead of empty string
		if (value == null) value = "";
		// MSIE may return &amp; instead of &
		value = (""+value).replace(/&amp;/gi, "&");
		// MSIE may change table cell align=center to align=middle
		if (node && (node.nodeName == "TD") && (attribute == "align") && (value == "middle")) {
			value = "center";
		}
		// MSIE may change table cell valign=middle to valign=center
		if (node && (node.nodeName == "TD") && (attribute == "vAlign") && (value == "center")) {
			value = "middle";
		}
		return ""+value;
	}
} else if (webeditor.getAttribute_attributes) {
	contenteditable_getAttribute = function (node, attribute) {
		var value = "";
		if (node && node.attributes && node.attributes[attribute]) {
			value = node.attributes[attribute].nodeValue;
		} else if (node && node.getAttribute) {
			value = node.getAttribute(attribute,2);
		}
		// MSIE may return null instead of empty string
		if (value == null) value = "";
		// MSIE may change table cell align=center to align=middle
		if (node && (node.nodeName == "TD") && (attribute == "align") && (value == "middle")) {
			value = "center";
		}
		// MSIE may change table cell valign=middle to valign=center
		if (node && (node.nodeName == "TD") && (attribute == "vAlign") && (value == "center")) {
			value = "middle";
		}
		return ""+value;
	}
} else if ((! webeditor.getAttribute_outerHTML) && webeditor.getAttribute_amp) {
	contenteditable_getAttribute = function (node, attribute) {
		var value = "";
		if (node && node.getAttribute) {
			value = node.getAttribute(attribute,2);
		}
		// MSIE may return null instead of empty string
		if (value == null) value = "";
		// MSIE may return &amp; instead of &
		value = (""+value).replace(/&amp;/gi, "&");
		// MSIE may change table cell align=center to align=middle
		if (node && (node.nodeName == "TD") && (attribute == "align") && (value == "middle")) {
			value = "center";
		}
		// MSIE may change table cell valign=middle to valign=center
		if (node && (node.nodeName == "TD") && (attribute == "vAlign") && (value == "center")) {
			value = "middle";
		}
		return ""+value;
	}
} else if (! webeditor.getAttribute_outerHTML) {
	contenteditable_getAttribute = function (node, attribute) {
		var value = "";
		if (node && node.getAttribute) {
			value = node.getAttribute(attribute,2);
		}
		// MSIE may return null instead of empty string
		if (value == null) value = "";
		// MSIE may change table cell align=center to align=middle
		if (node && (node.nodeName == "TD") && (attribute == "align") && (value == "middle")) {
			value = "center";
		}
		// MSIE may change table cell valign=middle to valign=center
		if (node && (node.nodeName == "TD") && (attribute == "vAlign") && (value == "center")) {
			value = "middle";
		}
		return ""+value;
	}
} else {
	contenteditable_getAttribute = function (node, attribute) {
		var value = "";
		if (node && ((node.outerHTML) || (typeof(node) == "string"))) {
			var node_outerHTML;
			if (node.outerHTML) {
				node_outerHTML = node.outerHTML;
			} else {
				node_outerHTML = node;
			}
			RegExp.global = true;
			RegExp.multiline = true;
			var quotedValue = new RegExp('^[^>]*[ \t\r\n]'+attribute+'="([^"]*)"', 'gi');
			var unquotedValue = new RegExp('^[^>]*[ \t\r\n]'+attribute+'=([^ >]*)', 'gi');
			var flagAttribute = new RegExp('^[^>]*[ \t\r\n]('+attribute+')[ \t\r\n>]', 'gi');
			var matches;
			if (matches = quotedValue.exec(node_outerHTML)) {
				value = matches[1] || "";
			} else if (matches = unquotedValue.exec(node_outerHTML)) {
				value = matches[1] || "";
			} else if (matches = flagAttribute.exec(node_outerHTML)) {
				value = matches[1] || "";
			}
		} else if (node && node.getAttribute) {
			value = node.getAttribute(attribute,2);
		}
		// MSIE may return null instead of empty string
		if (value == null) value = "";
		// MSIE may return &amp; instead of &
		if (webeditor.getAttribute_amp) value = (""+value).replace(/&amp;/gi, "&");
		// MSIE may change table cell align=center to align=middle
		if (node && (node.nodeName == "TD") && (attribute == "align") && (value == "middle")) {
			value = "center";
		}
		// MSIE may change table cell valign=middle to valign=center
		if (node && (node.nodeName == "TD") && (attribute == "vAlign") && (value == "center")) {
			value = "middle";
		}
		return ""+value;
	}
}





function contenteditable_cellRowSpan(cellnode) {
	return cellnode.rowSpan;
}

function contenteditable_cellColSpan(cellnode) {
	return cellnode.colSpan;
}

function contenteditable_cellIndex(cellnode) {
	return cellnode.cellIndex;
}





function contenteditable_insertimage_fix(src, border, alt, width, height, vspace, hspace, align, htmlclass, htmlid, onmouseover, onmouseout, usemap) {
// MSIE insertNodeAtSelection/pasteHTML may not work properly - changes src from relative to absolute + sets unspecified default values
	var img = null;
	var range = contenteditable_selection_range();
	if (range && range.moveStart) {
		range.moveStart("character",-1);
	}
	var tags = contenteditable_focused_document().getElementsByTagName("img");
	for (var i=0; i<tags.length; i++) {
		try {
			// create new range to use for comparison of current selection range to each tag element range
			var element = contenteditable_createrange();
			// set element range for this tag
			element.moveToElementText(tags[i]);
			// test if current selection range is equal to or part of this tag element range
			if ((range.compareEndPoints("EndToStart",element) == 1) && (range.compareEndPoints("StartToEnd",element) == -1) && (range.compareEndPoints("StartToStart",element) != -1) && (range.compareEndPoints("EndToEnd",element) != 1)) {
				img = tags[i];
			} else if ((range.compareEndPoints("EndToStart",element) == 1) && (range.compareEndPoints("StartToEnd",element) == 0) && (range.compareEndPoints("StartToStart",element) == 0) && (range.compareEndPoints("EndToEnd",element) == 1)) {
				img = tags[i];
			}
		} catch(e) {
		}
	}
	if (img) {
		contenteditable_setAttribute(img, "src", src);
		if (border) {
			contenteditable_setAttribute(img, "border", border);
		} else {
			contenteditable_removeAttribute(img, "border");
		}
		if (alt) {
			contenteditable_setAttribute(img, "alt", alt);
		} else {
			contenteditable_removeAttribute(img, "alt");
		}
		if (width) {
			contenteditable_setAttribute(img, "width", width);
		} else {
			contenteditable_removeAttribute(img, "width");
		}
		if (height) {
			contenteditable_setAttribute(img, "height", height);
		} else {
			contenteditable_removeAttribute(img, "height");
		}
		if (vspace) {
			contenteditable_setAttribute(img, "vspace", vspace);
		} else {
			contenteditable_removeAttribute(img, "vspace");
		}
		if (hspace) {
			contenteditable_setAttribute(img, "hspace", hspace);
		} else {
			contenteditable_removeAttribute(img, "hspace");
		}
		if (align) {
			contenteditable_setAttribute(img, "align", align);
		} else {
			contenteditable_removeAttribute(img, "align");
		}
		if (onmouseover) {
			contenteditable_setAttribute(img, "onMouseOver", onmouseover);
		} else {
			contenteditable_removeAttribute(img, "onMouseOver");
		}
		if (onmouseout) {
			contenteditable_setAttribute(img, "onMouseOut", onmouseout);
		} else {
			contenteditable_removeAttribute(img, "onMouseOut");
		}
		if (usemap) {
			contenteditable_setAttribute(img, "usemap", usemap);
		} else {
			contenteditable_removeAttribute(img, "usemap");
		}
		if (htmlclass) {
			contenteditable_setAttribute(img, "class", htmlclass);
		} else {
			contenteditable_removeAttribute(img, "class");
		}
		if (htmlid) {
			contenteditable_setAttribute(img, "id", htmlid);
		} else {
			contenteditable_removeAttribute(img, "id");
		}
		if (contenteditable_getAttribute(img, "href") == "/") {
			contenteditable_removeAttribute(img, "href");
		}
		if (contenteditable_getAttribute(img, "background") == "/") {
			contenteditable_removeAttribute(img, "background");
		}
	}
}



function contenteditable_insertmap_fix(node, href, target, text, htmlclass, htmlid, onclick) {
}



function contenteditable_insertlink_fix(node, href, target, text, htmlclass, htmlid, onclick, title) {
// MSIE insertNodeAtSelection/pasteHTML may not work properly - changes src from relative to absolute + sets unspecified default values
	var a = null;
	var range = contenteditable_selection_range();
	if (range && range.moveStart) {
		range.moveStart("character",-1);
	}
	var tags = contenteditable_focused_document().getElementsByTagName("a");
	for (var i=0; i<tags.length; i++) {
		try {
			// create new range to use for comparison of current selection range to each tag element range
			var element = contenteditable_createrange();
			// set element range for this tag
			element.moveToElementText(tags[i]);
			// test if current selection range is equal to or part of this tag element range
			if ((range.compareEndPoints("EndToStart",element) == 1) && (range.compareEndPoints("StartToEnd",element) == -1) && (range.compareEndPoints("StartToStart",element) != -1) && (range.compareEndPoints("EndToEnd",element) != 1)) {
				a = tags[i];
			}
		} catch(e) {
		}
	}
	if (a) {
		contenteditable_setAttribute(a, "href", href);
		if (target) {
			contenteditable_setAttribute(a, "target", target);
		} else {
			contenteditable_removeAttribute(a, "target");
		}
		if (htmlclass) {
			contenteditable_setAttribute(a, "class", htmlclass);
		} else {
			contenteditable_removeAttribute(a, "class");
		}
		if (htmlid) {
			contenteditable_setAttribute(a, "id", htmlid);
		} else {
			contenteditable_removeAttribute(a, "id");
		}
		if (onclick) {
			contenteditable_setAttribute(a, "onclick", onclick);
		} else {
			contenteditable_removeAttribute(a, "onclick");
		}
		if (title) {
			contenteditable_setAttribute(a, "title", title);
		} else {
			contenteditable_removeAttribute(a, "title");
		}
	}
}



function contenteditable_insertiframe_fix(width, height, src, htmlclass, htmlid, htmlname, htmltitle) {
// MSIE insertNodeAtSelection/pasteHTML may not work properly - changes src from relative to absolute + sets unspecified default values
	var iframe = null;
	var range = contenteditable_selection_range();
	if (range && range.moveStart) {
		range.moveStart("character",-1);
	}
	var tags = contenteditable_focused_document().getElementsByTagName("iframe");
	for (var i=0; i<tags.length; i++) {
		try {
			// create new range to use for comparison of current selection range to each tag element range
			var element = contenteditable_createrange();
			// set element range for this tag
			element.moveToElementText(tags[i]);
			// test if current selection range is equal to or part of this tag element range
			if ((range.compareEndPoints("EndToStart",element) == 1) && (range.compareEndPoints("StartToEnd",element) == -1) && (range.compareEndPoints("StartToStart",element) != -1) && (range.compareEndPoints("EndToEnd",element) != 1)) {
				iframe = tags[i];
			} else if ((range.compareEndPoints("EndToStart",element) == 1) && (range.compareEndPoints("StartToEnd",element) == 0) && (range.compareEndPoints("StartToStart",element) == 1) && (range.compareEndPoints("EndToEnd",element) == 1)) {
				iframe = tags[i];
			}
		} catch(e) {
		}
	}
	if (iframe) {
		if (contenteditable_getAttribute(iframe, "href") == "/") {
			contenteditable_removeAttribute(iframe, "href");
		}
		if (contenteditable_getAttribute(iframe, "background") == "/") {
			contenteditable_removeAttribute(iframe, "background");
		}
	}
}



function contenteditable_execcommand_fix(command, value) {
}



function contenteditable_formatclass_fix(command, value) {
}



function contenteditable_nobr_fix() {
}



function contenteditable_box_fix() {
	if (contenteditable_focused_contentwindow() == window) return;
	// MSIE may not display set class/style for anchor
	contenteditable_focused_contentwindow().document.body.innerHTML = contenteditable_focused_contentwindow().document.body.innerHTML;
	contenteditable_event_paste_fix(contenteditable_focused_contentwindow().document.body);
}



function contenteditable_mailto_fix() {
	if (contenteditable_focused_contentwindow() == window) return;
	// MSIE may not display set class/style for anchor
	contenteditable_focused_contentwindow().document.body.innerHTML = contenteditable_focused_contentwindow().document.body.innerHTML;
	contenteditable_event_paste_fix(contenteditable_focused_contentwindow().document.body);
}



function contenteditable_anchor_fix() {
	if (contenteditable_focused_contentwindow() == window) return;
	// MSIE may not display set class/style for anchor
	contenteditable_focused_contentwindow().document.body.innerHTML = contenteditable_focused_contentwindow().document.body.innerHTML;
	contenteditable_event_paste_fix(contenteditable_focused_contentwindow().document.body);
}



function contenteditable_cleanContent_fix() {
}



function contenteditable_formatContent_fix(content) {
	if (! content) return content;

	content = contenteditable_event_paste_fix_sub("A", "href", content);
	content = contenteditable_event_paste_fix_sub("IMG", "src", content);

	// MSIE may add "href="/" src="/" background="/"" attributes for DIV and P tags etc.
	content = content.replace(/ href="\/" src="\/" background="\/">/gi, ">");

	content = content.replace(/(<a[^>]* href=")\/([a-z]+\:[^>]*>)/gi, "$1$2");
	content = content.replace(/(<a[^>]*) background="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<a[^>]*) background=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<a[^>]*) background=\/(>)/gi, "$1$2");
	content = content.replace(/(<a[^>]*) src="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<a[^>]*) src=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<a[^>]*) src=\/(>)/gi, "$1$2");

	content = content.replace(/(<a[^>]*) href="\/"( name=[^>]*>)/gi, "$1$2");
	content = content.replace(/(<a[^>]*) href=\/( name=[^>]*>)/gi, "$1$2");

	content = content.replace(/(<div[^>]*) background="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<div[^>]*) background=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<div[^>]*) background=\/(>)/gi, "$1$2");
	content = content.replace(/(<div[^>]*) href="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<div[^>]*) href=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<div[^>]*) href=\/(>)/gi, "$1$2");
	content = content.replace(/(<div[^>]*) src="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<div[^>]*) src=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<div[^>]*) src=\/(>)/gi, "$1$2");

	content = content.replace(/(<p[^>]*) background="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<p[^>]*) background=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<p[^>]*) background=\/(>)/gi, "$1$2");
	content = content.replace(/(<p[^>]*) href="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<p[^>]*) href=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<p[^>]*) href=\/(>)/gi, "$1$2");
	content = content.replace(/(<p[^>]*) src="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<p[^>]*) src=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<p[^>]*) src=\/(>)/gi, "$1$2");

	content = content.replace(/(<br[^>]*) background="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<br[^>]*) background=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<br[^>]*) background=\/(>)/gi, "$1$2");
	content = content.replace(/(<br[^>]*) href="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<br[^>]*) href=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<br[^>]*) href=\/(>)/gi, "$1$2");
	content = content.replace(/(<br[^>]*) src="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<br[^>]*) src=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<br[^>]*) src=\/(>)/gi, "$1$2");

	content = content.replace(/(<table[^>]*) background="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<table[^>]*) background=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<table[^>]*) background=\/(>)/gi, "$1$2");
	content = content.replace(/(<table[^>]*) href="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<table[^>]*) href=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<table[^>]*) href=\/(>)/gi, "$1$2");
	content = content.replace(/(<table[^>]*) src="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<table[^>]*) src=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<table[^>]*) src=\/(>)/gi, "$1$2");

	content = content.replace(/(<tr[^>]*) background="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<tr[^>]*) background=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<tr[^>]*) background=\/(>)/gi, "$1$2");
	content = content.replace(/(<tr[^>]*) href="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<tr[^>]*) href=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<tr[^>]*) href=\/(>)/gi, "$1$2");
	content = content.replace(/(<tr[^>]*) src="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<tr[^>]*) src=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<tr[^>]*) src=\/(>)/gi, "$1$2");

	content = content.replace(/(<td[^>]*) background="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<td[^>]*) background=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<td[^>]*) background=\/(>)/gi, "$1$2");
	content = content.replace(/(<td[^>]*) href="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<td[^>]*) href=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<td[^>]*) href=\/(>)/gi, "$1$2");
	content = content.replace(/(<td[^>]*) src="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<td[^>]*) src=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<td[^>]*) src=\/(>)/gi, "$1$2");

	content = content.replace(/(<input[^>]*) background="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<input[^>]*) background=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<input[^>]*) background=\/(>)/gi, "$1$2");
	content = content.replace(/(<input[^>]*) href="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<input[^>]*) href=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<input[^>]*) href=\/(>)/gi, "$1$2");
	content = content.replace(/(<input[^>]*) src="\/"([^>]*>)/gi, "$1$2");
	content = content.replace(/(<input[^>]*) src=\/( [^>]*>)/gi, "$1$2");
	content = content.replace(/(<input[^>]*) src=\/(>)/gi, "$1$2");

	return content;
}



function contenteditable_node_attributes_fix(attributes, node) {
	return attributes;
}



function contenteditable_pasteContent_node(nodedocument) {
	if (nodedocument) {
		return nodedocument.createElement("div");
	} else {
		return document.createElement("div");
	}
}



function contenteditable_paste_fix_prepare() {
}



function contenteditable_paste_fix() {
	contenteditable_event_paste_fix_sub("A", "href");
	contenteditable_event_paste_fix_sub("IMG", "src");
	contenteditable_event_paste_fix_sub("INPUT", "src");
	contenteditable_event_paste_fix_sub("TABLE", "background");
	contenteditable_event_paste_fix_sub("TR", "background");
	contenteditable_event_paste_fix_sub("TD", "background");
	contenteditable_event_paste_fix_sub("IFRAME", "src");
	contenteditable_event_paste_fix_sub("AREA", "href");
}



function contenteditable_dragdrop_fix(node) {
	// MSIE may change images and links to absolute URLs on drag and drop
//	if (node && (node.nodeName == "IMG")) {
//		contenteditable_event_paste_fix_sub("IMG", "src", node);
//	}

	tags = node.getElementsByTagName("IMG");
	if (node.nodeName == "IMG") {
		tags[tags.length] = node;
	}
	if (! tags.length) {
		tags = node.parentNode.getElementsByTagName("IMG");
	}
	for (var i=0; i<tags.length; i++) {
		contenteditable_event_paste_fix_sub("IMG", "src", tags[i]);
	}

	tags = node.getElementsByTagName("A");
	if (node.nodeName == "A") {
		tags[tags.length] = node;
	}
	if (! tags.length) {
		tags = node.parentNode.getElementsByTagName("A");
	}
	for (var i=0; i<tags.length; i++) {
		contenteditable_event_paste_fix_sub("A", "href", tags[i]);
	}
}



function contenteditable_formatContentNodeHTML_fix(node) {
	// MSIE may ignore EMBED tags inside OBJECT tags
	var output = "";
	if (node.nodeName == "OBJECT") {
		output = node.innerHTML;
		output = output.replace(new RegExp("^.*(<embed [^>]*>.*</embed>).*$", "gi"), "$1");
		output = output.replace(new RegExp("^.*(<embed [^>]* ?/ ?>).*$", "gi"), "$1");
	}
	return output;
}



function contenteditable_formatContentNodeXHTML_fix(node) {
	// MSIE may ignore EMBED tags inside OBJECT tags
	var output = "";
	if (node.nodeName == "OBJECT") {
		output = node.innerHTML;
		output = output.replace(new RegExp("^.*(<embed [^>]*>.*</embed>).*$", "gi"), "$1");
		output = output.replace(new RegExp("^.*(<embed [^>]* ?/ ?>).*$", "gi"), "$1");
	}
	return output;
}



function contenteditable_contextmenu(evt){
	var iframe = contenteditable_focused_iframe();
	webeditor_menu('context', false, getAbsoluteOffsetLeft(iframe)+evt.clientX, getAbsoluteOffsetTop(iframe)+evt.clientY);
	contenteditable_event_stop(evt);
	return;
}



function contenteditable_webeditor_clipboard_cut() {
	try {
		if (webeditor.clipboard && window.clipboardData && window.clipboardData.getData) {
			webeditor.clipboardText = window.clipboardData.getData("Text");
		}
	} catch (e) {
		webeditor.clipboardText = "";
		webeditor.clipboard = false;
	}
}



function contenteditable_webeditor_clipboard_copy() {
	try {
		if (webeditor.clipboard && window.clipboardData && window.clipboardData.getData) {
			webeditor.clipboardText = window.clipboardData.getData("Text");
		}
	} catch (e) {
		webeditor.clipboardText = "";
		webeditor.clipboard = false;
	}
}



function contenteditable_webeditor_clipboard() {
	try {
		if (webeditor.clipboard && webeditor.clipboardHTML && webeditor.clipboardText && window.clipboardData && window.clipboardData.getData) {
			return true;
		}
	} catch (e) {
	}
	return false;
}



function contenteditable_webeditor_clipboard_paste() {
	try {
		if (webeditor.clipboard && webeditor.clipboardHTML && webeditor.clipboardText && window.clipboardData && window.clipboardData.getData && (webeditor.clipboardText == window.clipboardData.getData("Text"))) {
			return true;
		}
	} catch (e) {
	}
	return false;
}



function contenteditable_contenteditable_container(node) {
	while (node && (node.contentEditable != "true") && (node.contentEditable != "false")) {
		node = node.parentNode;
	}
	if (node && (node.contentEditable == "true")) {
		return node;
	} else {
		return false;
	}
}
