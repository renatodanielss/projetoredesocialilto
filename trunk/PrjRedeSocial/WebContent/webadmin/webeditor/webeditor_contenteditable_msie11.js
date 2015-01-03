// Asbru Web Content Editor
// (C) 2002-2014 Asbru Ltd.
// www.asbrusoft.com

// MSIE 11 may not resize textarea when changing rows/cols and style height/width
if ((webbrowser == "MSIE") && (webeditor.majorVersion == 11)) {
	// MSIE11 may not resize textarea inputs when rows/cols are changed
	webeditor.textareaResizeOuterHTML = true;
}

// Mozilla may set CSS font-size one size off - "x-small" instead of "xx-small" for "1" - "small" instead of "x-small" for "2" - "medium" instead of "small" for "3" - "large" instead of "medium" for "4" - "x-large" instead of "large" for "5" - "xx-large" instead of "x-large" for "6" - no size for "7"
webeditor.useCSSfontsize = false; // set true to let Mozilla set the CSS font-size through execCommand although they may be one size too small and there may be no CSS font-size for fontsize "7"

function webeditor_empty_content_fix(content) {
	if (content == "") content = "<br>";
	return content;
}

function webeditor_supported(command) {
	return true;
}

function webeditor_select_init(node) {
	node.ondeactivate = webeditor_select_deactivate;
	node.onblur = webeditor_select_blur;
	node.onfocus = webeditor_select_focus;
}

function webeditor_select_focus(evt) {
	if (evt && evt.target && evt.target.id) webeditor.select_focused[evt.target.id] = true;
	return true;
}

function contenteditable_onload(handler) {
	window.addEventListener("load", handler, true);
	WebEditorSkin();
}

function contenteditable_onload_remove(handler) {
	window.removeEventListener("load", handler, true);
}

function contenteditable_event_handler(node, event, handler, capturingphase) {
	contenteditable_detach_event_handler(node, event, handler, capturingphase);
	contenteditable_attach_event_handler(node, event, handler, capturingphase);
}

function contenteditable_attach_event_handler(node, event, handler, capturingphase) {
	node.addEventListener(event, handler, capturingphase);
}

function contenteditable_detach_event_handler(node, event, handler, capturingphase) {
	node.removeEventListener(event, handler, capturingphase);
}

function contenteditable_reinit() {
	for (var i=0; i<document.getElementsByTagName('iframe').length; i++) {
		var iframe = document.getElementsByTagName('iframe').item(i);
		try {
			if (iframe && iframe.contentWindow && iframe.contentWindow.document && iframe.contentWindow.document && iframe.contentWindow.document.body) {
				if (((iframe.className == 'webeditor_contenteditable') || (iframe.className == 'webeditor_contenteditable_disabled'))) {
					try {
						iframe.contentWindow.document.removeEventListener("focus", contenteditable_onfocus[i], true);
					} catch (e) {
					}
					try {
						iframe.contentWindow.document.removeEventListener("blur", contenteditable_onblur[i], true);
					} catch (e) {
					}
				}
			}
		} catch (e) {
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
						if (! contenteditable_onfocus[i]) contenteditable_onfocus[i] = new Function('event', 'contenteditable_focused='+i+';WebEditorActivate();webeditor_onfocus();webeditor_refreshToolbar(true);');
						if (! contenteditable_onblur[i]) contenteditable_onblur[i] = new Function('event', 'webeditor_onblur();webeditor_refreshToolbar(true);');
						contenteditable_event_handler(iframe.contentWindow.document, "focus", contenteditable_onfocus[i], true);
						contenteditable_event_handler(iframe.contentWindow.document, "blur", contenteditable_onblur[i], true);
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
	var first = true;

	// load style sheets and empty content files for all web editor input fields
	for (var i=0; i<document.getElementsByTagName('iframe').length; i++) {
		var iframe = document.getElementsByTagName('iframe').item(i);
		if (((iframe.className == 'webeditor_contenteditable') || (iframe.className == 'webeditor_contenteditable_disabled')) && (! contenteditable_inited[iframe.id])) {
			try {
				if (contenteditable_stylesheet[iframe.id] && iframe && iframe.contentWindow && iframe.contentWindow.document && iframe.contentWindow.document.getElementById('stylesheet') && (! contenteditable_inited_stylesheet[iframe.id])) {
					iframe.contentWindow.document.getElementById('stylesheet').href = contenteditable_stylesheet[iframe.id];
					contenteditable_inited_stylesheet[iframe.id] = true;
				} else {
					if (iframe.src && iframe.contentWindow && iframe.contentWindow.document && iframe.contentWindow.document.location) {
						if (iframe.contentWindow.document.location.href != iframe.src) {
							iframe.contentWindow.document.location.href = iframe.src;
//							contenteditable_inited_document[iframe.id] = true;
							setTimeout(contenteditable_init, webeditor.initTimeout);
							webeditor.initTimeout = webeditor.initTimeout * webeditor.initTimeoutMultiplier;
							return;
						} else if (! contenteditable_inited_document[iframe.id]) {
//							iframe.contentWindow.document.location.href = iframe.src;
							contenteditable_inited_document[iframe.id] = true;
							setTimeout(contenteditable_init, webeditor.initTimeout);
							webeditor.initTimeout = webeditor.initTimeout * webeditor.initTimeoutMultiplier;
							return;
						} else {
							// ok - inited
						}
					} else {
						setTimeout(contenteditable_init, webeditor.initTimeout);
						webeditor.initTimeout = webeditor.initTimeout * webeditor.initTimeoutMultiplier;
						return;
					}
					if (iframe && iframe.contentWindow && iframe.contentWindow.document && iframe.contentWindow.document.body) {
						// ok
					} else {
						setTimeout(contenteditable_init, webeditor.initTimeout);
						return;
					}
				}
			} catch (e) {
//				alert(Text('webbrowser_unsupported_contenteditable')+"\r\n\r\n"+e+"\r\n\r\n"+e.message);
			}
		}
	}

	// all style sheets and empty content files for all web editor input fields have been loaded
	// make all webeditor input fields editable and set the content
	for (var i=0; i<document.getElementsByTagName('iframe').length; i++) {
		var iframe = document.getElementsByTagName('iframe').item(i);
		if (((iframe.className == 'webeditor_contenteditable') || (iframe.className == 'webeditor_contenteditable_disabled')) && (! contenteditable_inited[iframe.id])) {
			try {
				if (webeditor.direction) iframe.contentWindow.document.dir = webeditor.direction;
				try {
					iframe.contentWindow.document.designMode = "on";
					iframe.contentWindow.document.execCommand("redo", false, null);
				} catch (e) {
				}
//if (webeditor.baseHref) iframe.contentWindow.document.getElementsByTagName('BASE')[0].href = webeditor.baseHref;
				try {
					iframe.contentWindow.document.body.innerHTML = contenteditable_contents[iframe.id];
				} catch (e) {
					setTimeout(contenteditable_init, webeditor.initTimeout);
					return;
				}
				contenteditable_setBody(iframe.contentWindow.document.body, iframe.id);
				if (! contenteditable_onfocus[i]) contenteditable_onfocus[i] = new Function('event', 'contenteditable_focused='+i+';WebEditorActivate();webeditor_onfocus();webeditor_refreshToolbar(true);');
				if (! contenteditable_onblur[i]) contenteditable_onblur[i] = new Function('event', 'webeditor_onblur();webeditor_refreshToolbar(true);');
				contenteditable_event_handler(iframe.contentWindow.document, "focus", contenteditable_onfocus[i], true);
				contenteditable_event_handler(iframe.contentWindow.document, "blur", contenteditable_onblur[i], true);
				contenteditable_event_handler(iframe.contentWindow.document, "keydown", contenteditable_event, true);
				contenteditable_event_handler(iframe.contentWindow.document, "keyup", contenteditable_event, true);
				contenteditable_event_handler(iframe.contentWindow.document, "keypress", contenteditable_event, true);
				contenteditable_event_handler(iframe.contentWindow.document, "mousedown", contenteditable_event, true);
				contenteditable_event_handler(iframe.contentWindow.document, "mouseup", contenteditable_event, true);
				contenteditable_event_handler(iframe.contentWindow.document, "drag", contenteditable_event, true);
				if (webeditor.contextmenus) contenteditable_event_handler(iframe.contentWindow.document, "contextmenu", contenteditable_contextmenu, true);
				var form = iframe;
				while ((form.tagName != "FORM") && (form.tagName != "HTML")) {
					form = form.parentNode;
				}
				if (form.tagName != "HTML") {
					contenteditable_event_handler(form, "submit", contenteditable_onSubmit, true);
					form[iframe.id].value = contenteditable_contents[iframe.id];
				}
			} catch (e) {
//				alert(Text('webbrowser_unsupported_contenteditable')+"\r\n\r\n"+e+"\r\n\r\n"+e.message);
			}
			if (! contenteditable_inited[iframe.id]) webeditor.inited += 1;
			contenteditable_inited[iframe.id] = true;
			if (first) {
				first = false;
				contenteditable_focused = i;
//				webeditor_dropdown_stylesheet('formatclass', contenteditable_stylesheet[iframe.id]);
//				webeditor_dropdown_stylesheet('formatblock', contenteditable_stylesheet[iframe.id]);
//				webeditor_dropdown_stylesheet('fontname', contenteditable_stylesheet[iframe.id]);
//				webeditor_dropdown_stylesheet('fontsize', contenteditable_stylesheet[iframe.id]);
			}
			try {
//				if (webeditor.scrollContentToTop && iframe && iframe.contentWindow && iframe.contentWindow.document && iframe.contentWindow.document.body && iframe.contentWindow.document.body.firstChild && iframe.contentWindow.document.body.firstChild.scrollIntoView) iframe.contentWindow.document.body.firstChild.scrollIntoView();
			} catch(e) {
			}
		}
	}

	// finalize initialization
	if (webeditor.scrollContentToTop) {
		var iframe = document.getElementsByTagName('iframe').item(contenteditable_focused);
		try {
//			if (iframe && iframe.contentWindow && iframe.contentWindow.focus) iframe.contentWindow.focus();
		} catch(e) {
		}
		webeditor_onfocus();
		webeditor_refreshToolbar(true);
//		window.focus();
//		try {
//			if (iframe && iframe.contentWindow && iframe.contentWindow.blur) iframe.contentWindow.blur();
//		} catch(e) {
//		}
//		window.focus();
		if (document.location.href.indexOf("#") < 0) window.scrollTo(0,0);
	}
	webeditor.refreshToolbar = true;

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
		if (((iframe.className == 'webeditor_contenteditable') || (iframe.className == 'webeditor_contenteditable_disabled')) && (contenteditable_inited[iframe.id])) {
			if ((iframe.id == id) || (! id)) {
				try {
					if (iframe.className != 'webeditor_contenteditable') {
						iframe.className = 'webeditor_contenteditable';
					}
					if (iframe.contentWindow.document.designMode != "on") {
						iframe.contentWindow.document.designMode = "on";
					}
					if (webeditor.direction && (iframe.contentWindow.document.dir != webeditor.direction)) {
						iframe.contentWindow.document.dir = webeditor.direction;
					}
				} catch (e) {
				}
				try {
					if (! contenteditable_onfocus[i]) contenteditable_onfocus[i] = new Function('event', 'contenteditable_focused='+i+';WebEditorActivate();webeditor_onfocus();webeditor_refreshToolbar(true);');
					if (! contenteditable_onblur[i]) contenteditable_onblur[i] = new Function('event', 'webeditor_onblur();webeditor_refreshToolbar(true);');
					contenteditable_event_handler(iframe.contentWindow.document, "focus", contenteditable_onfocus[i], true);
					contenteditable_event_handler(iframe.contentWindow.document, "blur", contenteditable_onblur[i], true);
				} catch (e) {
				}
				contenteditable_inited[iframe.id] = true;
			}
		}
	}
}

function contenteditable_disable(id) {
	for (var i=0; i<document.getElementsByTagName('iframe').length; i++) {
		var iframe = document.getElementsByTagName('iframe').item(i);
		if ((iframe.className == 'webeditor_contenteditable') || (iframe.className == 'webeditor_contenteditable_disabled')) {
			if ((iframe.id == id) || (! id)) {
				try {
					if (iframe.className != 'webeditor_contenteditable_disabled') {
						iframe.className = 'webeditor_contenteditable_disabled';
					}
					if (iframe.contentWindow.document.designMode != "off") {
						iframe.contentWindow.document.designMode = "off";
					}
					if (webeditor.direction && (iframe.contentWindow.document.dir != webeditor.direction)) {
						iframe.contentWindow.document.dir = webeditor.direction;
					}
				} catch (e) {
				}
				try {
					iframe.contentWindow.document.removeEventListener("focus", contenteditable_onfocus[i], true);
				} catch (e) {
				}
				try {
					iframe.contentWindow.document.removeEventListener("blur", contenteditable_onblur[i], true);
				} catch (e) {
				}
			}
		}
	}
}

function contenteditable_toolbar(focused) {
	if (webeditor.toolbar && webeditor.toolbar.contentDocument && webeditor.toolbar.contentDocument.body) {
		return webeditor.toolbar.contentDocument;
	} else if (focused && contenteditable_focused_iframe() && contenteditable_focused_iframe().id && document.getElementById('webeditor_toolbar_' + contenteditable_focused_iframe().id)) {
		return document.getElementById('webeditor_toolbar_' + contenteditable_focused_iframe().id);
	} else {
		return document;
	}
}

function contenteditable_document_stylesheets_cssrules(stylesheet) {
	return stylesheet.cssRules;
}





function contenteditable_selection_fix() {
}

function contenteditable_event(event) {
	webeditor_refreshToolbar(webeditor.refreshtoolbarOnAnyEvent);
	return webeditor_event(event);
}

function contenteditable_event_stop(event) {
	event.preventDefault();
	event.stopPropagation();
}

function contenteditable_event_ctrlkey(event) {
	if (event.ctrlKey && (event.type == "keypress")) {
		return true;
	} else {
		return false;
	}
}

function contenteditable_event_key(event) {
	if (event.type == "keypress") {
		return String.fromCharCode(event.charCode).toLowerCase();
	} else {
		return false;
	}
}

function contenteditable_event_delete(my_event) {
	// Mozilla may insert "&nbsp;" when selection text is deleted
	if (my_event && my_event.keyCode && ((my_event.keyCode == webeditor_keyCode_backspace) || (my_event.keyCode == webeditor_keyCode_delete))) {
		if ((my_event.type == "keydown") || (my_event.type == "keypress")) {
			if (contenteditable_selection_text()) {
				var range = contenteditable_selection_range();
				if ((range.startContainer == range.endContainer) && (range.startContainer.nodeName == "#text")) {
//					// Mozilla may display linebreak if pasting blank into textnode
//					contenteditable_event_stop(my_event);
//					var startOffset = range.startOffset;
//					var value = range.startContainer.nodeValue.substring(0, range.startOffset) + range.startContainer.nodeValue.substring(range.endOffset);
//					range.startContainer.nodeValue = value;
//					contenteditable_selection_node(range.startContainer, range.startContainer, startOffset, startOffset)
				} else {
					contenteditable_event_stop(my_event);
					contenteditable_pasteContent("");
				}
				// Mozilla may not display caret/cursor with completely empty content
				if (contenteditable_focused_document().body.innerHTML == "") {
					contenteditable_event_stop(my_event);
					contenteditable_focused_document().body.innerHTML = "<br>";
				}
				return;
			}
		}
	}
}

function contenteditable_event_enter_p(my_event) {
	contenteditable_event_stop(my_event);
	if (my_event.type != "keydown") {
		return;
	}
	var range = contenteditable_selection_range();
	if ((range.startContainer == range.endContainer) && (range.startContainer.nodeName == "#text")) {
		var preP = range.startContainer.nodeValue.substring(0, range.startOffset) || "";
		var postP = range.startContainer.nodeValue.substring(range.endOffset) || "";
		var oldP = range.startContainer;
		var oldPparentNode = oldP.parentNode;
		if (oldPparentNode.nodeName == "P") {
			if (oldPparentNode.firstChild == oldPparentNode.lastChild) {
				var newP = contenteditable_focused_contentwindow().document.createElement("P");
				newP.innerHTML = preP || "&nbsp;";
				if (postP) {
					oldP.nodeValue = postP;
				} else {
					oldPparentNode.innerHTML = "&nbsp;";
				}
				oldPparentNode.parentNode.insertBefore(newP, oldPparentNode);
				range = contenteditable_focused_contentwindow().document.createRange();
				range.selectNodeContents(oldPparentNode);
				range.collapse(true);
				var selection = contenteditable_focused_contentwindow().getSelection();
				selection.removeAllRanges();
				selection.addRange(range);
			} else if (oldP == oldPparentNode.lastChild) {
				var newP = contenteditable_focused_contentwindow().document.createElement("P");
				newP.innerHTML = postP || "&nbsp;";
				oldP.nodeValue = preP;
				if (oldPparentNode.nextSibling) {
					oldPparentNode.parentNode.insertBefore(newP, oldPparentNode.nextSibling);
				} else {
					oldPparentNode.parentNode.appendChild(newP);
				}
				range = contenteditable_focused_contentwindow().document.createRange();
				range.selectNodeContents(newP);
				range.collapse(true);
				var selection = contenteditable_focused_contentwindow().getSelection();
				selection.removeAllRanges();
				selection.addRange(range);
			} else if (oldP == oldPparentNode.firstChild) {
				var newP = contenteditable_focused_contentwindow().document.createElement("P");
				newP.innerHTML = preP || "&nbsp;";
				oldP.nodeValue = postP;
				oldPparentNode.parentNode.insertBefore(newP, oldPparentNode);
				range = contenteditable_focused_contentwindow().document.createRange();
				range.selectNodeContents(oldP);
				range.collapse(true);
				var selection = contenteditable_focused_contentwindow().getSelection();
				selection.removeAllRanges();
				selection.addRange(range);
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
				range = contenteditable_focused_contentwindow().document.createRange();
				range.selectNodeContents(newP);
				range.collapse(true);
				var selection = contenteditable_focused_contentwindow().getSelection();
				selection.removeAllRanges();
				selection.addRange(range);
			}
		} else if (oldPparentNode.nodeName == "BODY") {
			var newP = contenteditable_focused_contentwindow().document.createElement("P");
			newP.innerHTML = preP || "&nbsp;";
			var newP2 = contenteditable_focused_contentwindow().document.createElement("P");
			newP2.innerHTML = postP || "&nbsp;";
			oldPparentNode.insertBefore(newP, oldP);
			oldPparentNode.insertBefore(newP2, oldP);
			oldPparentNode.removeChild(oldP);
			range = contenteditable_focused_contentwindow().document.createRange();
			range.selectNodeContents(newP2);
			range.collapse(true);
			var selection = contenteditable_focused_contentwindow().getSelection();
			selection.removeAllRanges();
			selection.addRange(range);
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
			range = contenteditable_focused_contentwindow().document.createRange();
			range.selectNodeContents(newP);
			range.collapse(true);
			var selection = contenteditable_focused_contentwindow().getSelection();
			selection.removeAllRanges();
			selection.addRange(range);
		}
	} else {
		if ((range.startContainer.nodeName == "BODY") && (range.startContainer.nodeName == "BODY")) {
			contenteditable_pasteContent("<p>");
			var newP = contenteditable_focused_contentwindow().document.createElement("P");
			newP.innerHTML = postP || "&nbsp;";
			range.startContainer.insertBefore(newP, range.startContainer.firstChild);
			range = contenteditable_focused_contentwindow().document.createRange();
			range.selectNodeContents(newP.nextSibling);
			range.collapse(true);
			var selection = contenteditable_focused_contentwindow().getSelection();
			selection.removeAllRanges();
			selection.addRange(range);
		} else {
			contenteditable_pasteContent("<p>");
		}
	}
}

function contenteditable_event_enter_fix(my_event) {
}

function contenteditable_event_dragdrop(my_event) {
	if ((my_event.type == "mousedown") && (my_event.target.nodeName == "IMG")) {
		webeditor.dragdropNode = my_event.target;
		webeditor.dragdropNodeURL = my_event.target.src;
//	} else if ((! webeditor.dragdropNode) && (my_event.type == "mousedown")) {
	} else if (my_event.type == "mousedown") {
		webeditor.dragdropNode = my_event.target;
//	} else if (webeditor.dragdropNode && webeditor.dragdropNodeURL) {
	} else if (webeditor.dragdropNode && (my_event.type != "mousemove") && (my_event.type != "mouseover") && (my_event.type != "mouseout")) {
		contenteditable_dragdrop_fix();
		if ((my_event.type == "mousedown") || (my_event.type == "mouseup") || (my_event.type == "keydown") || (my_event.type == "keypress")) {
			webeditor.dragdropNode = false;
			webeditor.dragdropNodeURL = false;
		}
	}
}





function contenteditable_selection(contentWindow) {
	if (! contentWindow) contentWindow = contenteditable_focused_contentwindow();
	try {
		if (contentWindow && contentWindow.getSelection) return contentWindow.getSelection();
	} catch(e) {
	}
}

function contenteditable_selection_text(contentSelection) {
	if (! contentSelection) contentSelection = contenteditable_selection();
	return getRangeHTML(contenteditable_selection_range(contentSelection),false);
}

function contenteditable_selection_range_control(contentSelection) {
}

function contenteditable_selection_range_parentNode(contentRange) {
	if (! contentRange) contentRange = contenteditable_selection_range();
	if (contentRange) {
		try {
			if (contentRange.commonAncestorContainer && (contentRange.commonAncestorContainer.nodeName == '#text')) {
				return contentRange.commonAncestorContainer.parentNode;
			} else {
				return contentRange.commonAncestorContainer;
			}
		} catch(e) {
		}
	}
}

function contenteditable_selection_range_startNode(contentRange) {
	if (! contentRange) contentRange = contenteditable_selection_range();
	if (contentRange) {
		return contentRange.startContainer;
	}
}

function contenteditable_selection_range_endNode(contentRange) {
	if (! contentRange) contentRange = contenteditable_selection_range();
	if (contentRange) {
		return contentRange.endContainer;
	}
}

function contenteditable_selection_range_contains(contentRange,node,partially) {
	const START_TO_START = 0;
	const START_TO_END = 1;
	const END_TO_END = 2;
	const END_TO_START = 3;
	var contains = false;
	var element = contenteditable_createrange();
	element.selectNode(node);
	if (contentRange && (contentRange.compareBoundaryPoints(START_TO_START,element) == -1) && (contentRange.compareBoundaryPoints(START_TO_END,element) == 1) && (contentRange.compareBoundaryPoints(END_TO_START,element) == -1) && (contentRange.compareBoundaryPoints(END_TO_END,element) == 1)) {
		contains = true;
	} else if (partially && contentRange && (contentRange.compareBoundaryPoints(START_TO_END,element) == 1) && (contentRange.compareBoundaryPoints(END_TO_START,element) == -1)) {
		contains = true;
	}
	return contains;
}

function contenteditable_selection_contains(tagName) {
	var range = contenteditable_selection_range();
	var parentnode = contenteditable_selection_range_parentNode(range);
	var nodes = parentnode.getElementsByTagName(tagName);
	for (var i=0; i<nodes.length; i++) {
		if (contenteditable_selection_range_contains(range,nodes[i],true)) {
			return true;
		}
	}
	return false;
}

function contenteditable_selection_range(contentSelection) {
	if (! contentSelection) contentSelection = contenteditable_selection();
	if (contentSelection && (! contentSelection.rangeCount)) {
		// Mozilla Firefox 1.5 may loose selection/range after CTRL+A + DELETE
		try {
			var range = contenteditable_createrange();
			range.selectNodeContents(contenteditable_focused_document().body);
			range.collapse(true);
			contentSelection = contenteditable_selection();
			// MSIE11 may change focus if removing and adding range
			//if (contentSelection) contentSelection.removeAllRanges();
			//if (contentSelection) contentSelection.addRange(range);
			//contentSelection = contenteditable_selection();
		} catch(e) {
			return false;
		}
	}
	if (contentSelection && contentSelection.rangeCount) {
		try {
			return contentSelection.getRangeAt(0);
		} catch(e) {
		}
	}
}

function contenteditable_createrange() {
	return contenteditable_focused_contentwindow().document.createRange();
//	return document.createRange();
}



var contenteditable_selection_container_cache = new Array();
var contenteditable_selection_container_cache_range_startContainer = false;
var contenteditable_selection_container_cache_range_endContainer = false;
var contenteditable_selection_container_cache_range_startOffset = false;
var contenteditable_selection_container_cache_range_endOffset = false;

function contenteditable_selection_container(tagName, ignoreCache, useBookmark) {
try{
	const START_TO_START = 0;
	const START_TO_END = 1;
	const END_TO_END = 2;
	const END_TO_START = 3;
	var return_value;
	var range = contenteditable_selection_range();
	if ((! ignoreCache) && range && (contenteditable_selection_container_cache_range_startContainer == range.startContainer) && (contenteditable_selection_container_cache_range_endContainer == range.endContainer) && (contenteditable_selection_container_cache_range_startOffset == range.startOffset) && (contenteditable_selection_container_cache_range_endOffset == range.endOffset) && (typeof(contenteditable_selection_container_cache[tagName]) != "undefined")) {
		return contenteditable_selection_container_cache[tagName];
	}
	var container;
	var container_exact = false;
	if (range && (range.startContainer == range.endContainer) && (! range.startContainer.tagName)) {
		// range is within single text node
		container = contenteditable_selection_range_parentNode();
		if (container && (! container.tagName)) container = container.parentNode;
	} else if (range && (range.startContainer == range.endContainer) && (range.startContainer.tagName == "BODY") && (range.startOffset == 0) && (range.endOffset == range.startContainer.childNodes.length)) {
		// range is entire body
		container = contenteditable_selection_range_parentNode();
		if (container && (! container.tagName)) container = container.parentNode;
		if (tagName && container.firstChild && (container.firstChild.tagName == tagName.toUpperCase()) && (container.firstChild == container.lastChild)) container = container.firstChild;
	} else {
		var content;
		var startContainer = false;
		var endContainer = false;
		var tag;
		if (false && range && (range.startContainer == range.endContainer) && (range.startOffset == 0) && (range.endOffset == range.startContainer.childNodes.length)) {
			// range is all child nodes
		} else if (true && range && (range.startContainer == range.endContainer) && (range.startOffset == range.endOffset-1)) {
			// range is one child node
			content = range.startContainer.childNodes[range.startOffset];
			tag = content;
		} else if (true && range && (range.startContainer == range.endContainer) && (range.startOffset == range.endOffset)) {
			// range is nothing
			content = range.startContainer.childNodes[range.startOffset];
			tag = content;
		} else if (true && range && range.startContainer.tagName && range.endContainer.tagName) {
			// range is node range
			content = contenteditable_selection_range_parentNode();
			startContainer = range.startContainer.childNodes[range.startOffset];
			endContainer = range.endContainer.childNodes[range.endOffset];
			tag = startContainer;
		} else if (true && range && (range.startContainer.tagName || range.endContainer.tagName)) {
			// range is partial node range
			content = contenteditable_selection_range_parentNode();
			tag = content;
		} else {
			// range is text range
			content = contenteditable_selection_range_parentNode();
			tag = content;
			if (content && content.lastChild) tag = content.lastChild;
		}
		container_exact = false;
		container = content;
		if (container && (! container.tagName)) container = container.parentNode;
		var skipChildren = false;
		var containerCount = 0;
		while (tag = contenteditable_nextnode(content, tag, skipChildren)) {
			var element = contenteditable_createrange();
			if (element.selectNode) {
				element.selectNode(tag);
				if (tag.tagName && range && (range.compareBoundaryPoints(START_TO_START,element) == 0) && (range.compareBoundaryPoints(END_TO_END,element) == 0)) {
					container_exact = tag;
					if (container_exact.nodeName == webeditor.selection_node) break;
					if (tag.tagName == "TABLE") {
						skipChildren = true;
					} else {
						skipChildren = false;
					}
				} else if (tag.tagName && range && (range.compareBoundaryPoints(END_TO_START,element) <= 0) && (range.compareBoundaryPoints(START_TO_END,element) >= 0)) {
					if (container == tag.parentNode) {
						containerCount = 1;
					} else {
						containerCount++;
					}
					container = tag;
					if (tag.tagName == "TABLE") {
						skipChildren = true;
					} else {
						skipChildren = false;
					}
				} else {
					skipChildren = true;
				}
			}
			if (tag == endContainer) break;
		}
		if (containerCount > 1) container = content;
	}
	if (webeditor.selection_node && container) {
		var selection_node = container;
		while (selection_node && (webeditor.selection_node != selection_node.nodeName)) {
			selection_node = selection_node.parentNode;
		}
		if (selection_node) container = selection_node;
	}
	if (tagName) {
		var selection_node = container_exact || container || false;
		while (selection_node && (tagName.toUpperCase() != selection_node.tagName)) {
			selection_node = selection_node.parentNode;
		}
		return_value = selection_node;
	} else {
		if (contenteditable_focused_document()) {
			return_value = container_exact || container || contenteditable_focused_document().body;
		} else {
			return_value = container_exact || container;
		}
	}

	if (range) {
		if ((contenteditable_selection_container_cache_range_startContainer != range.startContainer) || (contenteditable_selection_container_cache_range_endContainer != range.endContainer) || (contenteditable_selection_container_cache_range_startOffset != range.startOffset) || (contenteditable_selection_container_cache_range_endOffset != range.endOffset)) {
			contenteditable_selection_container_cache = new Array();
			contenteditable_selection_container_cache_range_startContainer = range.startContainer;
			contenteditable_selection_container_cache_range_endContainer = range.endContainer;
			contenteditable_selection_container_cache_range_startOffset = range.startOffset;
			contenteditable_selection_container_cache_range_endOffset = range.endOffset;
		}
		contenteditable_selection_container_cache[tagName] = return_value;
	}
	return return_value;
} catch(e) {
}
}



function contenteditable_selection_all() {
	if (webeditor.contentEditable || (contenteditable_focused_contentwindow() == window)) {
		var container = contenteditable_selection_range_parentNode();
		while (container && (container.contentEditable != "true")) {
			container = container.parentNode;
		}
		if (container) {
			contenteditable_selection_node(container, container, 0, container.childNodes.length || 1);
		}
	} else {
		contenteditable_selection_node(contenteditable_focused_document().body);
	}
}



function contenteditable_selection_node(startNode, endNode, startOffset, endOffset) {
	webeditor.selection_node = startNode.nodeName;
	var range = contenteditable_selection_range() || contenteditable_createrange();
	if (startNode && startNode.nodeName == "BODY") {
		try {
			range.selectNodeContents(startNode);
		} catch(e) {
		}
	} else if (startNode) {
		try {
			range.selectNode(startNode);
		} catch(e) {
			// Mozilla may fail to select AUDIO node - and AUDIO node may not have parent node - no workaround!?
		}
	}
	if (endNode) {
		try {
			range.setStart(startNode, startOffset);
			range.setEnd(endNode, endOffset);
		} catch(e) {
		}
	}
	var selection = contenteditable_selection();
	if (selection) selection.removeAllRanges();
	if (selection) selection.addRange(range);
}



function contenteditable_selection_cells(contentSelection) {
	if (! contentSelection) contentSelection = contenteditable_selection();
	var cells = new Array();
	var rows = new Array();
	var row;
	for (var i=0; i<contentSelection.rangeCount; i++) {
		var range = contentSelection.getRangeAt(i);
		var node = range.startContainer.childNodes[range.startOffset];
		if (node && (node.tagName == "TD")) {
			var rowIndex = rows.length;
			for (var j=0; j<rows.length; j++) {
				if (rows[j] == node.parentNode) rowIndex = j;
			}
			rows[rowIndex] = node.parentNode;
			if (! cells[rowIndex]) cells[rowIndex] = new Array();
			cells[rowIndex].push(node);
		}
	}
	if (cells.length) return cells;
}



function contenteditable_selection_bookmark(bookmark) {
	if (bookmark) {
		var rootnode = contenteditable_focused_document().body;
		var startNodeNumber = bookmark.startNode;
		var startOffset = bookmark.startOffset;
		var endNodeNumber = bookmark.endNode;
		var endOffset = bookmark.endOffset;
		var startNode = contenteditable_selection_bookmark_path_node(rootnode, bookmark.startNodePath)
		var endNode = contenteditable_selection_bookmark_path_node(rootnode, bookmark.endNodePath)
		contenteditable_selection_node(startNode, endNode, startOffset, endOffset);
		// May scroll input to top and toolbar out of view
		//if (contenteditable_selection_container().scrollIntoView) contenteditable_selection_container().scrollIntoView();
		contenteditable_focused_contentwindow().focus();
	} else {
		var range;
		if (range = contenteditable_selection_range()) {
			bookmark = new Object();
			bookmark.startNode = 0;
			bookmark.startOffset = range.startOffset;
			bookmark.endNode = 0;
			bookmark.endOffset = range.endOffset;
	
			bookmark.startNodePath = contenteditable_selection_bookmark_path(range.startContainer);
			bookmark.endNodePath = contenteditable_selection_bookmark_path(range.endContainer);
		}
	}
	return bookmark;
}
function contenteditable_selection_bookmark_path(node) {
	var nodePath = "";
	var nodeNumber = 1;
	var nodeName = node.nodeName;
	var tmp_node = node;
	while (tmp_node = tmp_node.previousSibling) {
		if (tmp_node.nodeName == nodeName) nodeNumber++;
	}
	nodePath = nodeNumber + "." + nodeName;
	while ((node = node.parentNode) && (node.nodeName != "BODY") && (node.nodeName != "HTML") && (node.nodeName != "#document")) {
		var nodeNumber = 1;
		var nodeName = node.nodeName;
		var tmp_node = node;
		while (tmp_node = tmp_node.previousSibling) {
			if (tmp_node.nodeName == nodeName) nodeNumber++;
		}
		nodePath = nodeNumber + "." + nodeName + " " + nodePath;
	}
	return nodePath;
}
function contenteditable_selection_bookmark_path_node(node, nodePath) {
	var nodePathSteps = nodePath.split(" ");
	for (var i=0; i<nodePathSteps.length; i++) {
		var parts = nodePathSteps[i].split(".");
		var nodeNumber = parseInt(parts[0]);
		var nodeName = parts[1];
		var childNode = node.firstChild;
		if (childNode.nodeName == nodeName) nodeNumber--;
		while (nodeNumber && (childNode = childNode.nextSibling)) {
			if (childNode.nodeName == nodeName) nodeNumber--;
		}
		if (childNode) {
			node = childNode;
		} else {
		}
	}
	return node;
}





function contenteditable_setContent_body(content, id, iframe) {
	if (iframe && iframe.contentWindow && iframe.contentWindow.document && iframe.contentWindow.document.body) {
		iframe.contentWindow.document.body.innerHTML = "";
//		contenteditable_selection_node(iframe.contentWindow.document.body);
//		contenteditable_pasteContent(content, id);
		iframe.contentWindow.document.body.innerHTML = content;
		contenteditable_setBody(iframe.contentWindow.document.body, iframe.id);
	}
}

function contenteditable_insertNodeAtSelection(contentWindow, insertNode, insertHTML) {
	var insertedNode = insertNode;

	// get current selection
	var selection = contenteditable_selection(contentWindow);

	// get the first range of the selection
	// (there's almost always only one range)
	var range;
	try {
		range = selection.getRangeAt(0);
	} catch(e) {
		range = contenteditable_createrange();
		range.selectNodeContents(contenteditable_focused_document().body);
		range.collapse(1);
	}

	// deselect everything
	if (selection) selection.removeAllRanges();

	// remove content of current selection from document
	range.deleteContents();

	// get location of current selection
	var container = range.startContainer;
	var pos = range.startOffset;

	// make a new range for the new selection
	range = document.createRange();

	if ((container.nodeType == 3) && (insertNode.nodeType == 3)) {
		// if we insert text in a textnode, do optimized insertion
		container.insertData(pos, insertNode.nodeValue);

		// put cursor after inserted text
		range.setEnd(container, pos+insertNode.length);
		range.setStart(container, pos+insertNode.length);

		insertedNode = container;
	} else {
		var afterNode;
		if (container.nodeType==3) {
			// when inserting into a textnode
			// we create 2 new textnodes
			// and put the insertNode in between

			var textNode = container;
			container = textNode.parentNode;
			var text = textNode.nodeValue;

			// text before the split
			var textBefore = text.substr(0,pos);
			// text after the split
			var textAfter = text.substr(pos);

			var beforeNode = document.createTextNode(textBefore);
			var afterNode = document.createTextNode(textAfter);

			// insert the 3 new nodes before the old one
			container.insertBefore(afterNode, textNode);
			container.insertBefore(insertNode, afterNode);
			container.insertBefore(beforeNode, insertNode);

			// remove the old node
			container.removeChild(textNode);

			insertedNode = insertNode;
		} else {
			// else simply insert the node
			afterNode = container.childNodes[pos];
			if (afterNode) {
				container.insertBefore(insertNode, afterNode);
				insertedNode = insertNode;
			} else {
				try {
					container.appendChild(insertNode);
					insertedNode = insertNode;
				} catch(e) {
					var parentNode = container.parentNode;
					parentNode.replaceChild(insertNode, container);
					insertedNode = insertNode;
				}
			}
		}
		if (range && range.setEnd && afterNode) {
			if ((container.nodeName == "BODY") && (afterNode.nodeName == "BR")) {
				range.setEnd(container, pos+1);
			} else {
				range.setEnd(afterNode, 0);
			}
		}
		if (range && range.setStart && afterNode) {
			if ((container.nodeName == "BODY") && (afterNode.nodeName == "BR")) {
				range.setStart(container, pos+1);
			} else {
				range.setStart(afterNode, 0);
			}
		}
	}
	try {
		selection.addRange(range);
	} catch (e) {
	}
	return insertedNode;
}





function contenteditable_useCSS() {
	// "useCSS" deprecated - use "styleWithCSS" instead
	try {
		contenteditable_focused_document().execCommand("styleWithCSS", 0, true);
	} catch (e) {
		try {
			contenteditable_focused_document().execCommand("useCSS", false, false);
		} catch (e) {
		}
	}
}

function contenteditable_notCSS() {
	// "useCSS" deprecated - use "styleWithCSS" instead
	try {
		contenteditable_focused_document().execCommand("styleWithCSS", 0, false);
	} catch (e) {
		try {
			contenteditable_focused_document().execCommand("useCSS", false, true);
		} catch (e) {
		}
	}
}

function contenteditable_BlockDirLTR(id) {
	webeditor.direction = "ltr";
	contenteditable_focused_contentwindow().document.dir = 'ltr';
}

function contenteditable_BlockDirRTL(id) {
	webeditor.direction = "rtl";
	contenteditable_focused_contentwindow().document.dir = 'rtl';
}





function contenteditable_formatblock_query() {
	var formatblock;
	var list;
	var listitem;
	if ((formatblock = ('' + contenteditable_focused_document().queryCommandValue("formatblock")).toLowerCase()) && (formatblock != "p")) {
		return formatblock;
	} else if ((listitem = contenteditable_selection_container_listitem()) && (listitem.nodeName != "LI"))  {
		return listitem.nodeName.toLowerCase();
	} else if (list = contenteditable_selection_container_list()) {
		return list.nodeName.toLowerCase();
	}
	return "p";
}
function contenteditable_selection_container_list() {
	var list;
	if (list = contenteditable_selection_container('ul')) {
	} else if (list = contenteditable_selection_container('ol')) {
	} else if (list = contenteditable_selection_container('dir')) {
	} else if (list = contenteditable_selection_container('menu')) {
	} else if (list = contenteditable_selection_container('dl')) {
	}
	return list;
}
function contenteditable_selection_container_listitem() {
	var listitem;
	if (listitem = contenteditable_selection_container('li')) {
	} else if (listitem = contenteditable_selection_container('dt')) {
	} else if (listitem = contenteditable_selection_container('dd')) {
	}
	return listitem;;
}



function contenteditable_formatblock(command,value) {
	if (value == "<ol>") {
		var list = contenteditable_formatblock_list("ol");
		contenteditable_formatblock_listitems(list,"li");
	} else if (value == "<ul>") {
		var list = contenteditable_formatblock_list("ul");
		contenteditable_formatblock_listitems(list,"li");
	} else if (value == "<dir>") {
		var list = contenteditable_formatblock_list("dir");
		contenteditable_formatblock_listitems(list,"li");
	} else if (value == "<menu>") {
		var list = contenteditable_formatblock_list("menu");
		contenteditable_formatblock_listitems(list,"li");
	} else if (value == "<dt>") {
		var list = contenteditable_selection_container_list();
		if (list && list.nodeName == "dl") {
			contenteditable_formatblock_listitem("dt");
		} else {
			list = contenteditable_formatblock_list("dl");
			contenteditable_formatblock_listitems(list,"dt");
		}
	} else if (value == "<dd>") {
		var list = contenteditable_selection_container_list();
		if (list && list.nodeName == "dl") {
			contenteditable_formatblock_listitem("dd");
		} else {
			list = contenteditable_formatblock_list("dl");
			contenteditable_formatblock_listitems(list,"dd");
		}
	} else if (value == "<p>") {
		// applying normal/p to lists and list items does not work
		// approximate it by getting rid of the list completely
		// not ideal - but better than lists that are stuck
		var list = contenteditable_selection_container_list();
		if (list) {
			list = contenteditable_formatblock_list("");
		} else {
			contenteditable_execcommand(command,value);
		}
	} else if ((value == "<section>") || (value == "<nav>") || (value == "<article>") || (value == "<aside>") || (value == "<header>") || (value == "<footer>") || (value == "<main>") || (value == "<figure>") || (value == "<figcaption>") || (value == "<details>") || (value == "<summary>")) {
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
function contenteditable_formatblock_list(value) {
	var list = contenteditable_selection_container_list();
	if (! list) {
		contenteditable_execcommand("insertunorderedlist");
		list = contenteditable_selection_container("ul", true);
	}
	if (list && value) {
		var new_list = contenteditable_focused_contentwindow().document.createElement(value);
		for (var element=list.firstChild; element; element=element.nextSibling) {
			var new_element = element.cloneNode(true);
			new_list.appendChild(new_element);
		}
		list.parentNode.replaceChild(new_list,list);
		return new_list;
	} else if (list) {
		// remove list
		var new_list = contenteditable_focused_contentwindow().document.createElement("p");
		for (var element=list.firstChild; element; element=element.nextSibling) {
			for (var subelement=element.firstChild; subelement; subelement=subelement.nextSibling) {
				var new_subelement = subelement.cloneNode(true);
				new_list.appendChild(new_subelement);
			}
			new_list.appendChild(contenteditable_focused_contentwindow().document.createElement("br"));
			new_list.appendChild(contenteditable_focused_contentwindow().document.createTextNode("\r\n"));
		}
		list.parentNode.replaceChild(new_list,list);
		return new_list;
	}
}
function contenteditable_formatblock_listitem(value) {
	var listitem = contenteditable_selection_container_listitem();
	if (listitem) {
		var new_listitem = contenteditable_focused_contentwindow().document.createElement(value);
		for (var element=listitem.firstChild; element; element=element.nextSibling) {
			var new_element = element.cloneNode(true);
			new_listitem.appendChild(new_element);
		}
		listitem.parentNode.replaceChild(new_listitem,listitem);
		return new_listitem;
	}
}
function contenteditable_formatblock_listitems(list,value) {
	if (list) {
		for (var listitem=list.firstChild; listitem; listitem=listitem.nextSibling) {
			if ((listitem.nodeName == "LI") || (listitem.nodeName == "DT") || (listitem.nodeName == "DD")) {
				var new_listitem = contenteditable_focused_contentwindow().document.createElement(value);
				for (var element=listitem.firstChild; element; element=element.nextSibling) {
					var new_element = element.cloneNode(true);
					new_listitem.appendChild(new_element);
				}
				list.replaceChild(new_listitem,listitem);
				listitem = new_listitem;
			}
		}
	}
}



function contenteditable_formatclass(command,value) {
	var contentSelection = contenteditable_selection();
	for (var i=0; i<contentSelection.rangeCount; i++) {
		var range = contentSelection.getRangeAt(i);
		var startcontainer = range.startContainer;
		var endcontainer = range.endContainer;
		var startoffset = range.startOffset;
		var endoffset = range.endOffset;
		if (endcontainer.nodeName == "BODY") {
			if (endoffset > 0) {
				endcontainer = endcontainer.childNodes[endoffset-1];
				if (endcontainer.nodeType == 3) { // TEXT NODE
					endoffset = endcontainer.nodeValue.length;
				} else {
					endoffset = endcontainer.childNodes.length;
				}
			} else {
				endcontainer = endcontainer.childNodes[endoffset];
				endoffset = 0;
			}
		}
		if ((startcontainer == endcontainer) && (startoffset != endoffset) && (startcontainer.nodeType == 3)) { // TEXT NODE
			if ((startoffset == 0) && (endoffset == startcontainer.nodeValue.length) && (startcontainer.parentNode.nodeName == "SPAN") && (startcontainer.parentNode.childNodes.length == 1)) {
				contenteditable_formatclass_attribute(startcontainer.parentNode, value);
			} else {
				var node = document.createElement("span");
				contenteditable_formatclass_attribute(node, value);
				node.appendChild(document.createTextNode(startcontainer.nodeValue.substring(startoffset, endoffset)));
				contenteditable_insertNodeAtSelection(contenteditable_focused_contentwindow(), node);
			}
		} else if (startcontainer != endcontainer) {
			var node = startcontainer;
			if (startcontainer.nodeType == 3) { // TEXT NODE
				if (startoffset < startcontainer.nodeValue.length) {
					startcontainer = startcontainer.parentNode;
				} else {
					startcontainer = startcontainer.nextSibling;
				}
			}
			var endcontainer = endcontainer;
			if (endcontainer.nodeType == 3) { // TEXT NODE
				if (endoffset > 0) {
					endcontainer = endcontainer.parentNode;
				} else {
					endcontainer = endcontainer.previousSibling;
				}
			}
			for (var node = startcontainer; node != null; node = node.nextSibling) {
				contenteditable_formatclass_attribute(node, value);
				if (node == endcontainer) break;
			}
		} else if (startcontainer.nodeName == "BODY") {
			var node;
			if ((startcontainer.firstChild != startcontainer.lastChild) || (startcontainer.firstChild.nodeType != 3) || (endcontainer.firstChild.nodeType != 3)) {
				node = document.createElement("div");
			} else {
				node = document.createElement("span");
			}
			contenteditable_formatclass_attribute(node, value);
			var nodes = new Array();
			for (var j=startoffset; j<endoffset; j++) {
				var childnode = startcontainer.childNodes[j];
				nodes[nodes.length] = childnode;
			}
			for (var j=0; j<nodes.length; j++) {
				node.appendChild(nodes[j]);
			}
			contenteditable_insertNodeAtSelection(contenteditable_focused_contentwindow(), node);
		} else if (startoffset != endoffset) {
			for (var j=startoffset; j<endoffset; j++) {
				var node = startcontainer.childNodes[startoffset];
				if (node.nodeType == 3) node = node.parentNode; // TEXT NODE
				contenteditable_formatclass_attribute(node, value);
			}
		} else {
			var node = range.commonAncestorContainer;
			while ((node != null) && (node.nodeType == 3)) { node = node.parentNode; } // TEXT NODE
			contenteditable_formatclass_attribute(node, value);
		}
	}
}





function contenteditable_execcommand(command, value) {
	try {
		if (command == "backcolor") {
			// "useCSS" depreceated - use "styleWithCSS" instead
			contenteditable_focused_document().execCommand("useCSS", false, false);
			contenteditable_focused_document().execCommand("hilitecolor", false, contenteditable_execcommand_value(command, value));
			contenteditable_focused_document().execCommand("useCSS", false, webeditor.useCSS);
		} else if (webeditor.boldAsStrong && (command == "bold")) {
			var container = contenteditable_selection_container('strong');
			if (container) {
				contenteditable_remove_parentnode(container);
			} else {
				var text = contenteditable_selection_text() || '&nbsp;';
				contenteditable_event_paste_do_pre();
				contenteditable_pasteContent('<strong>' + text + '</strong>');
				contenteditable_event_paste_do_post();
				contenteditable_event_paste_fix();
				contenteditable_nobr_fix();
			}
		} else if (webeditor.italicAsEm && (command == "italic")) {
			var container = contenteditable_selection_container('em');
			if (container) {
				contenteditable_remove_parentnode(container);
			} else {
				var text = contenteditable_selection_text() || '&nbsp;';
				contenteditable_event_paste_do_pre();
				contenteditable_pasteContent('<em>' + text + '</em>');
				contenteditable_event_paste_do_post();
				contenteditable_event_paste_fix();
				contenteditable_nobr_fix();
			}
		} else if (webeditor.striketroughAsStrike && (command == "strikethrough")) {
			var container = contenteditable_selection_container('strike');
			if (container) {
				contenteditable_remove_parentnode(container);
			} else {
				var text = contenteditable_selection_text() || '&nbsp;';
				contenteditable_event_paste_do_pre();
				contenteditable_pasteContent('<strike>' + text + '</strike>');
				contenteditable_event_paste_do_post();
				contenteditable_event_paste_fix();
				contenteditable_nobr_fix();
			}
		} else if (webeditor.inserthorizontalruleUnstyled && (command == "inserthorizontalrule")) {
			contenteditable_event_paste_do_pre();
			contenteditable_pasteContent('<hr>');
			contenteditable_event_paste_do_post();
			contenteditable_event_paste_fix();
		} else if (command == "justifyleft") {
			var container = contenteditable_selection_container('p');
			if (! container) contenteditable_selection_container('div');
			if (container) contenteditable_selection_node(container);
			if (webeditor.useCSS) contenteditable_useCSS();
			return contenteditable_focused_document().execCommand(command, false, contenteditable_execcommand_value(command, value));
		} else if (command == "justifycenter") {
			var container = contenteditable_selection_container('p');
			if (! container) contenteditable_selection_container('div');
			if (container) contenteditable_selection_node(container);
			if (webeditor.useCSS) contenteditable_useCSS();
			return contenteditable_focused_document().execCommand(command, false, contenteditable_execcommand_value(command, value));
		} else if (command == "justifyright") {
			var container = contenteditable_selection_container('p');
			if (! container) contenteditable_selection_container('div');
			if (container) contenteditable_selection_node(container);
			if (webeditor.useCSS) contenteditable_useCSS();
			return contenteditable_focused_document().execCommand(command, false, contenteditable_execcommand_value(command, value));
		} else if (command == "justifyfull") {
			var container = contenteditable_selection_container('p');
			if (! container) contenteditable_selection_container('div');
			if (container) contenteditable_selection_node(container);
			if (webeditor.useCSS) contenteditable_useCSS();
			return contenteditable_focused_document().execCommand(command, false, contenteditable_execcommand_value(command, value));
		} else if (command == "fontsize") {
			if (webeditor.useCSS && webeditor.useCSSfontsize) {
				contenteditable_useCSS();
			} else {
				contenteditable_notCSS();
			}
			return contenteditable_focused_document().execCommand(command, false, contenteditable_execcommand_value(command, value));
		} else {
			if (webeditor.useCSS) contenteditable_useCSS();
			return contenteditable_focused_document().execCommand(command, false, contenteditable_execcommand_value(command, value));
		}
	} catch(e) {
		return false;
	}
	return true;
}

function contenteditable_execcommand_value(command, value) {
	if ((command == "forecolor") && ((value == "") || (value == "inherit"))) {
		return "inherit";
	} else if ((command == "backcolor") && ((value == "") || (value == "inherit"))) {
		return "inherit";
	}
	return value;
}

function contenteditable_querycommand_value(command, value) {
	return value;
}

function contenteditable_find(command) {
	// Find does not work properly in Mozilla 1.3-1.5 - nsFindInstData may be undefined
	if (! contenteditable_focused_contentwindow().nsFindInstData) contenteditable_focused_contentwindow().nsFindInstData = new Function();
	if (contenteditable_viewsource_status[contenteditable_focused]) {
		contenteditable_focused_textarea().find();
	} else {
		contenteditable_focused_contentwindow().find();
	}
	return true;
}

function contenteditable_print(command) {
	contenteditable_focused_contentwindow().print();
	return true;
}

function contenteditable_specialcharacter(value) {
	var contentWindow = contenteditable_focused_contentwindow();
	if (value) {
		var a = contentWindow.document.createTextNode(value);
		var selection = contenteditable_selection();
		var range = contenteditable_selection_range(selection);
		contenteditable_insertNodeAtSelection(contentWindow, a);
	}
}





function contenteditable_position(state) {
	var element = contenteditable_positionable();
	if (state) {
		// OK
	} else if (element) {
		if (element.style.position == "absolute") {
			element.style.position = "";
			element.style.top = "";
			element.style.left = "";
			element.style.zIndex = "";
		} else {
			var top = contenteditable_position_offsetTop(element);
			var left = contenteditable_position_offsetLeft(element);
			element.style.position = "absolute";
			if (top) element.style.top = top + "px";
			if (left) element.style.left = left + "px";
			element.style.zIndex = 1;
		}
		if (element.getAttribute("STYLE") == "") {
			element.removeAttribute("STYLE", 0);
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
		if (element.style && element.style.zIndex && (element.style.zIndex > 0)) {
			element.style.zIndex = parseInt(element.style.zIndex) - 1;
		} else {
			element.style.zIndex = 0;
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
		if (zIndex > 0) {
			zIndex--;
		} else {
			zIndex = 0;
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
}





function contenteditable_viewsource_onfocus(textarea) {
	contenteditable_event_handler(textarea, "focus", contenteditable_onfocus[contenteditable_focused], true);
	contenteditable_event_handler(textarea, "blur", contenteditable_onblur[contenteditable_focused], true);
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
	iframe.contentWindow.document.body.innerHTML = content;
contenteditable_setBody(iframe.contentWindow.document.body, iframe.id);
	contenteditable_event_handler(iframe.contentWindow.document, "focus", contenteditable_onfocus[contenteditable_focused], true);
	contenteditable_event_handler(iframe.contentWindow.document, "blur", contenteditable_onblur[contenteditable_focused], true);
}

function contenteditable_viewsource_show() {
	// old viewsource functionality using text encoding
	var html = document.createTextNode(document.getElementsByTagName('iframe').item(contenteditable_focused).contentWindow.document.body.innerHTML);
	document.getElementsByTagName('iframe').item(contenteditable_focused).contentWindow.document.body.innerHTML = "";
	document.getElementsByTagName('iframe').item(contenteditable_focused).contentWindow.document.body.appendChild(html);
}

function contenteditable_viewsource_hide() {
	// old viewsource functionality using text encoding
	var html = document.getElementsByTagName('iframe').item(contenteditable_focused).contentWindow.document.body.ownerDocument.createRange();
	html.selectNodeContents(document.getElementsByTagName('iframe').item(contenteditable_focused).contentWindow.document.body);
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
	if (attribute.toLowerCase() == "src") value = value.replace(new RegExp('^//'), "/");
	node.setAttribute(attribute, value);
}

function contenteditable_removeAttribute(node, attribute) {
	node.removeAttribute(attribute);
}

function contenteditable_getAttribute(node, attribute) {
	var value = "";
	if (typeof(node) == "string") {
		var node_outerHTML = node;
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
	} else if (node) {
		value = node.getAttribute(attribute) || "";
	} else {
		value = "";
	}
	if (attribute.toLowerCase() == "src") value = value.replace(new RegExp('^//'), "/")
	return value;
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
}



function contenteditable_insertmap_fix(node, href, target, text, htmlclass, htmlid, onclick) {
}



function contenteditable_insertlink_fix(node, href, target, text, htmlclass, htmlid, onclick, title) {
}



function contenteditable_insertiframe_fix(width, height, src, htmlclass, htmlid, htmlname, htmltitle) {
}



function contenteditable_execcommand_fix(command, value) {
}



function contenteditable_formatclass_fix(command, value) {
}



function contenteditable_nobr_fix() {
}



function contenteditable_box_fix() {
	if (contenteditable_focused_contentwindow() == window) return;
	contenteditable_focused_contentwindow().document.body.innerHTML = contenteditable_focused_contentwindow().document.body.innerHTML;
}



function contenteditable_mailto_fix() {
	if (contenteditable_focused_contentwindow() == window) return;
	contenteditable_focused_contentwindow().document.body.innerHTML = contenteditable_focused_contentwindow().document.body.innerHTML;
}



function contenteditable_anchor_fix() {
	if (contenteditable_focused_contentwindow() == window) return;
	contenteditable_focused_contentwindow().document.body.innerHTML = contenteditable_focused_contentwindow().document.body.innerHTML;
}



function contenteditable_cleanContent_fix() {
}



function contenteditable_formatContent_fix(content) {
	if (! content) return content;

	content = contenteditable_event_paste_fix_sub("A", "href", content);
	content = contenteditable_event_paste_fix_sub("IMG", "src", content);

	content = content.replace(/ background="\/"/g, "");

	content = content.replace(/(<a[^>]*) href="\/"( name=[^>]*>)/gi, "$1$2");
	content = content.replace(/(<a[^>]*) href=\/( name=[^>]*>)/gi, "$1$2");
	content = content.replace(/(<a[^>]*)( name=[^>]*) href="\/"([^>]*>)/gi, "$1$2$3");
	content = content.replace(/(<a[^>]*)( name=[^>]*) href=\/([^>]*>)/gi, "$1$2$3");

	return content;
}



function contenteditable_node_attributes_fix(attributes, node) {
	return attributes;
}



var contenteditable_preview_window;
var contenteditable_preview_head;
var contenteditable_preview_body;
var contenteditable_preview_content;
var contenteditable_preview_direction;

function contenteditable_preview_fix(preview_window, contenthead, contentbody, contentcontent) {
	// Netscape may not display content correctly if written directly to the preview window
	contenteditable_preview_window = preview_window;
	contenteditable_preview_head = contenthead;
	contenteditable_preview_body = contentbody;
	contenteditable_preview_content = contentcontent;
	contenteditable_preview_direction = webeditor.direction;
	preview_window.document.location.href = webeditor.rootpath + "preview.html";
}



function contenteditable_pasteContent_node() {
	return contenteditable_focused_contentwindow().document.createElement("div");
}



function contenteditable_paste_fix_prepare() {
	contenteditable_event_paste_pre = contenteditable_focused_document().body.cloneNode(true);
	contenteditable_event_paste_parentNode = contenteditable_selection_range_parentNode();
	contenteditable_event_paste_previousNode = new Array();
	var startnode = contenteditable_selection_range_startNode();
	if (startnode) {
		for (var i=0; startnode && startnode.nodeName != "BODY"; i++) {
			startnode = contenteditable_previousnode(contenteditable_focused_document().body, startnode);
			contenteditable_event_paste_previousNode[i] = startnode;
		}
	}
	contenteditable_event_paste_nextNode = new Array();
	var endnode = contenteditable_selection_range_endNode();
	if (endnode) {
		for (var i=0; endnode && endnode.nodeName != "BODY"; i++) {
			endnode = contenteditable_nextnode(contenteditable_focused_document().body, endnode);
			contenteditable_event_paste_nextNode[i] = endnode;
		}
	}
}



function contenteditable_paste_fix() {
	if (webeditor.clipboard) return;
	// Mozilla may change URLs on paste
	var parentnode = contenteditable_event_paste_parentNode;
	var range = contenteditable_selection_range();
	if (range.startContainer.nodeName == "BODY") {
		parentnode = range.startContainer;
	} else if (range.startContainer.nodeName != "#text") {
		parentnode = range.startContainer;
		while (! parentnode.previousSibling) {
			parentnode = parentnode.parentNode;
		}
		parentnode = parentnode.previousSibling.parentNode;
//	} else if ((range.startContainer.nodeName == "#text") && (range.startOffset == 0)) {
	} else if (range.startContainer.nodeName == "#text") {
		parentnode = range.startContainer;
		while (! parentnode.previousSibling) {
			parentnode = parentnode.parentNode;
		}
		parentnode = parentnode.previousSibling.parentNode;
	}

	range = contenteditable_createrange();

	var startnode = contenteditable_event_paste_previousNode[0];
	var endnode = contenteditable_event_paste_nextNode[0];
	var node;
	node = contenteditable_focused_document().body.firstChild;
	for (var i=contenteditable_event_paste_previousNode.length-2; i>=0; i--) {
		if (contenteditable_event_paste_previousNode[i] == node) {
			startnode = node;
			node = contenteditable_nextnode(contenteditable_focused_document().body, node);
		} else {
			break;
		}
	}
	node = contenteditable_previousnode(contenteditable_focused_document().body, contenteditable_focused_document().body);
	for (var i=contenteditable_event_paste_nextNode.length-2; i>=0; i--) {
		if (contenteditable_event_paste_nextNode[i] == node) {
			endnode = node;
			node = contenteditable_previousnode(contenteditable_focused_document().body, node);
		} else {
			break;
		}
	}

	if (parentnode.nodeName == "BODY") {
		range.selectNode(parentnode);
	} else if (startnode && endnode) {
		range = contenteditable_createrange();
		try {
			range.setStartBefore(startnode);
			range.setEndAfter(endnode);
		} catch(e) {
			range.selectNode(parentnode);
		}
	} else {
		range.selectNode(parentnode);
	}
	
	var docbase = '';
	var base = '';
	if (contenteditable_focused_document().location.pathname) docbase = contenteditable_focused_document().location.pathname.substring(0,contenteditable_focused_document().location.pathname.lastIndexOf('/')+1);
	base = webeditor.baseHref;

	webeditor.images = contenteditable_focused_document().getElementsByTagName("IMG");
	if (! webeditor.imagesFix) webeditor.imagesFix = new Object;
	for (var i=0; i<webeditor.images.length; i++) {
		if (range.isPointInRange(webeditor.images[i],0)) {
			if ((webeditor.images[i].src != contenteditable_getAttribute(webeditor.images[i], "src")) && (webeditor.images[i].src == webeditor.baseHref + contenteditable_getAttribute(webeditor.images[i], "src"))) {
//				contenteditable_setAttribute(webeditor.images[i], "src", base + contenteditable_getAttribute(webeditor.images[i], "src"));
				contenteditable_setAttribute(webeditor.images[i], "src", contenteditable_getAttribute(webeditor.images[i], "src"));
			} else if (contenteditable_getAttribute(webeditor.images[i], "src").substring(0,3) == "/..") {
				var mysrc = contenteditable_getAttribute(webeditor.images[i], "src");
				var mybase = base;
				while (mysrc.substring(0,3) == "/..") {
					mysrc = mysrc.substring(3);
					mybase = mybase.substring(0,mybase.length-1);
					mybase = mybase.substring(0,mybase.lastIndexOf('/')+1);
				}
//				if (mybase == "") mybase = "/";
				if (mybase == "http:/") mybase = "";
				if (mybase == "http://") mybase = "";
				if (webeditor.baseHrefPathRelative == ("/"+mysrc).substring(0,webeditor.baseHrefPathRelative.length)) mybase = "/";
				if ((mybase == "/") && (mysrc.startsWith("/"))) mybase = "";
				contenteditable_setAttribute(webeditor.images[i], "src", mybase + mysrc);
				if ((webeditor.images[i].src != contenteditable_getAttribute(webeditor.images[i], "src")) && (webeditor.images[i].src == webeditor.baseHref + contenteditable_getAttribute(webeditor.images[i], "src"))) {
//					contenteditable_setAttribute(webeditor.images[i], "src", base + contenteditable_getAttribute(webeditor.images[i], "src"));
					contenteditable_setAttribute(webeditor.images[i], "src", contenteditable_getAttribute(webeditor.images[i], "src"));
				}
			} else if (contenteditable_getAttribute(webeditor.images[i], "src").substring(0,3) == "../") {
//			} else if (contenteditable_getAttribute(webeditor.images[i], "src").substring(0,6) == "../../") {
//			} else if (contenteditable_getAttribute(webeditor.images[i], "src").substring(0,9) == "../../../") {
//			} else if (contenteditable_getAttribute(webeditor.images[i], "src").substring(0,15) == "../../../../../") {
				var mysrc = contenteditable_getAttribute(webeditor.images[i], "src");
				var mybase = base;
				while (mysrc.substring(0,3) == "../") {
					mysrc = mysrc.substring(3);
					mybase = mybase.substring(0,mybase.length-1);
					mybase = mybase.substring(0,mybase.lastIndexOf('/')+1);
				}
//				if (mybase == "") mybase = "/";
				if (mybase == "http:/") mybase = "";
				if (mybase == "http://") mybase = "";
				if (webeditor.baseHrefPathRelative == ("/"+mysrc).substring(0,webeditor.baseHrefPathRelative.length)) mybase = "/";
				if ((webeditor.baseHrefPathRelative.length > 1) && (mybase == "")) mybase = "/";
				if ((mybase == "/") && (mysrc.startsWith("/"))) mybase = "";
				contenteditable_setAttribute(webeditor.images[i], "src", mybase + mysrc);
				if ((webeditor.images[i].src != contenteditable_getAttribute(webeditor.images[i], "src")) && (webeditor.images[i].src == webeditor.baseHref + contenteditable_getAttribute(webeditor.images[i], "src"))) {
//					contenteditable_setAttribute(webeditor.images[i], "src", base + contenteditable_getAttribute(webeditor.images[i], "src"));
					contenteditable_setAttribute(webeditor.images[i], "src", contenteditable_getAttribute(webeditor.images[i], "src"));
				}
			} else if (contenteditable_getAttribute(webeditor.images[i], "src").substring(0,17+webeditor.language.length) == "empty." + webeditor.language + "?basehref=#") {
				contenteditable_setAttribute(webeditor.images[i], "src", contenteditable_getAttribute(webeditor.images[i], "src").substring(16+webeditor.language.length));
			}
			for (var j=0; j<webeditor.images.length; j++) {
				if (webeditor.baseHrefPathRelative + contenteditable_getAttribute(webeditor.images[i], "src") == contenteditable_getAttribute(webeditor.images[j], "src")) {
					contenteditable_setAttribute(webeditor.images[i], "src", contenteditable_getAttribute(webeditor.images[j], "src"));
				}
				if (webeditor.baseHrefRelative + contenteditable_getAttribute(webeditor.images[i], "src") == contenteditable_getAttribute(webeditor.images[j], "src")) {
					contenteditable_setAttribute(webeditor.images[i], "src", contenteditable_getAttribute(webeditor.images[j], "src"));
				}
			}
		}
	}
	webeditor.links = contenteditable_focused_document().getElementsByTagName("A");
	if (! webeditor.linksFix) webeditor.linksFix = new Object;
	for (var i=0; i<webeditor.links.length; i++) {
		if (range.isPointInRange(webeditor.links[i],0)) {
			if ((webeditor.links[i].href != contenteditable_getAttribute(webeditor.links[i], "href")) && (webeditor.links[i].href == webeditor.baseHref + contenteditable_getAttribute(webeditor.links[i], "href"))) {
//				contenteditable_setAttribute(webeditor.links[i], "href", base + contenteditable_getAttribute(webeditor.links[i], "href"));
				contenteditable_setAttribute(webeditor.links[i], "href", contenteditable_getAttribute(webeditor.links[i], "href"));
			} else if (contenteditable_getAttribute(webeditor.links[i], "href").substring(0,3) == "../") {
//			} else if (contenteditable_getAttribute(webeditor.links[i], "href").substring(0,6) == "../../") {
//			} else if (contenteditable_getAttribute(webeditor.links[i], "href").substring(0,9) == "../../../") {
//			} else if (contenteditable_getAttribute(webeditor.links[i], "href").substring(0,15) == "../../../../../") {
				var myhref = contenteditable_getAttribute(webeditor.links[i], "href");
				var mybase = base;
				while(myhref.substring(0,3) == "../") {
					myhref = myhref.substring(3);
					mybase = mybase.substring(0,mybase.length-1);
					mybase = mybase.substring(0,mybase.lastIndexOf('/')+1);
				}
//				if (mybase == "") mybase = "/";
				if (mybase == "http:/") mybase = "";
				if (mybase == "http://") mybase = "";
				contenteditable_setAttribute(webeditor.links[i], "href", mybase + myhref);
				if ((webeditor.links[i].href != contenteditable_getAttribute(webeditor.links[i], "href")) && (webeditor.links[i].href == webeditor.baseHref + contenteditable_getAttribute(webeditor.links[i], "href"))) {
//					contenteditable_setAttribute(webeditor.links[i], "href", base + contenteditable_getAttribute(webeditor.links[i], "href"));
					contenteditable_setAttribute(webeditor.links[i], "href", contenteditable_getAttribute(webeditor.links[i], "href"));
				}
			} else if (contenteditable_getAttribute(webeditor.links[i], "href").substring(0,17+webeditor.language.length) == "empty." + webeditor.language + "?basehref=#") {
				contenteditable_setAttribute(webeditor.links[i], "href", contenteditable_getAttribute(webeditor.links[i], "href").substring(16+webeditor.language.length));
			}
			for (var j=0; j<webeditor.links.length; j++) {
				if (webeditor.baseHrefPathRelative + contenteditable_getAttribute(webeditor.links[i], "href") == contenteditable_getAttribute(webeditor.links[j], "href")) {
					contenteditable_setAttribute(webeditor.links[i], "href", contenteditable_getAttribute(webeditor.links[j], "href"));
				}
				if (webeditor.baseHrefRelative + contenteditable_getAttribute(webeditor.links[i], "href") == contenteditable_getAttribute(webeditor.links[j], "href")) {
					contenteditable_setAttribute(webeditor.links[i], "href", contenteditable_getAttribute(webeditor.links[j], "href"));
				}
			}
		}
	}
}



function contenteditable_dragdrop_fix(node) {
	// Mozilla may change image src URL on drag and drop
	if (webeditor.dragdropNode && webeditor.dragdropNodeURL) {
		var tags;
		if (node) {
			tags = node.getElementsByTagName("IMG");
			if (node.nodeName == "IMG") {
				tags[tags.length] = node;
			}
			if (! tags.length) {
				tags = contenteditable_focused_document().getElementsByTagName("IMG");
			}
		} else {
			tags = contenteditable_focused_document().getElementsByTagName("IMG");
		}
		for (var i=0; i<tags.length; i++) {
			var old_src = contenteditable_getAttribute(tags[i], "src");
			var filename = old_src.replace(/^[\.\/]*([^\.\/].*)/,"$1");
			var new_src = contenteditable_getAttribute(tags[i], "src");
			if (webeditor.dragdropNode && webeditor.dragdropNodeURL) {
				if (webeditor.dragdropNodeURL.match(new RegExp("/"+filename+"$"))) {
					contenteditable_setAttribute(tags[i], "src", webeditor.dragdropNodeURL);
					contenteditable_event_paste_fix_sub("IMG", "src", tags[i]);
				}
			}
		}
	}
	// Mozilla may change image src URL on drag and drop
//	if (node = node || webeditor.dragdropNode) {
//	if (node = webeditor.dragdropNode) {
	if (node && webeditor.dragdropNode) {
		tags = node.getElementsByTagName("IMG");
		if (node.nodeName == "IMG") {
			tags[tags.length] = node;
		}
		if ((! tags.length) && node.parentNode) {
			tags = node.parentNode.getElementsByTagName("IMG");
		}
		for (var i=0; i<tags.length; i++) {
//			if (contenteditable_getAttribute(tags[i], "src").substring(0,3) == "../") {
			if (contenteditable_getAttribute(tags[i], "src").substring(0,9) == "../../../") {
//			if (contenteditable_getAttribute(tags[i], "src").substring(0,15) == "../../../../../") {
				var base = '';
				if (contenteditable_focused_document().location.pathname) base = contenteditable_focused_document().location.pathname.substring(0,contenteditable_focused_document().location.pathname.lastIndexOf('/')+1);
				var mysrc = contenteditable_getAttribute(tags[i], "src");
				var mybase = base;
				while(mysrc.substring(0,3) == "../") {
					if (mysrc.substring(0,6) == "../../") {
						mysrc = mysrc.substring(3);
					} else {
						mysrc = mysrc.substring(2);
					}
					mybase = mybase.substring(0,mybase.length-1);
					mybase = mybase.substring(0,mybase.lastIndexOf('/')+1);
				}
//				if (mybase == "") mybase = "/";
				if (mybase == "http:/") mybase = "";
				if (mybase == "http://") mybase = "";
				contenteditable_setAttribute(tags[i], "src", mybase + mysrc);
				if ((tags[i].src != contenteditable_getAttribute(tags[i], "src")) && (tags[i].src == webeditor.baseHref + contenteditable_getAttribute(tags[i], "src"))) {
//					contenteditable_setAttribute(tags[i], "src", base + contenteditable_getAttribute(tags[i], "src"));
					contenteditable_setAttribute(tags[i], "src", contenteditable_getAttribute(tags[i], "src"));
					contenteditable_event_paste_fix_sub("IMG", "src", tags[i]);
				}
			}
		}
	}
	// Mozilla may change link href URL on drag and drop
//	if (node = node || webeditor.dragdropNode) {
//	if (node = webeditor.dragdropNode) {
	if (node && webeditor.dragdropNode) {
		tags = node.getElementsByTagName("A");
		if (node.nodeName == "A") {
			tags[tags.length] = node;
		}
		if ((! tags.length) && node.parentNode) {
			tags = node.parentNode.getElementsByTagName("A");
		}
		for (var i=0; i<tags.length; i++) {
//			if (contenteditable_getAttribute(tags[i], "href").substring(0,3) == "../") {
			if (contenteditable_getAttribute(tags[i], "href").substring(0,9) == "../../../") {
//			if (contenteditable_getAttribute(tags[i], "href").substring(0,15) == "../../../../../") {
				var base = '';
				if (contenteditable_focused_document().location.pathname) base = contenteditable_focused_document().location.pathname.substring(0,contenteditable_focused_document().location.pathname.lastIndexOf('/')+1);
				var myhref = contenteditable_getAttribute(tags[i], "href");
				var mybase = base;
				while(myhref.substring(0,3) == "../") {
					if (myhref.substring(0,6) == "../../") {
						myhref = myhref.substring(3);
					} else {
						myhref = myhref.substring(2);
					}
					mybase = mybase.substring(0,mybase.length-1);
					mybase = mybase.substring(0,mybase.lastIndexOf('/')+1);
				}
//				if (mybase == "") mybase = "/";
				if (mybase == "http:/") mybase = "";
				if (mybase == "http://") mybase = "";
				contenteditable_setAttribute(tags[i], "href", mybase + myhref);
				if ((tags[i].href != contenteditable_getAttribute(tags[i], "href")) && (tags[i].href == webeditor.baseHref + contenteditable_getAttribute(tags[i], "href"))) {
//					contenteditable_setAttribute(tags[i], "href", base + contenteditable_getAttribute(tags[i], "href"));
					contenteditable_setAttribute(tags[i], "href", contenteditable_getAttribute(tags[i], "href"));
					contenteditable_event_paste_fix_sub("A", "href", tags[i]);
				}
			}
		}
	}
}



function contenteditable_formatContentNodeHTML_fix(node) {
	return "";
}



function contenteditable_formatContentNodeXHTML_fix(node) {
	return "";
}





//	getRangeHTML and getNodeHTML based on:

//	File:		RangePatch1_3.js
//	Name:		Mozilla Range Implementation Patch
//	Version:	1.3
//	Author:		Jeffrey M. Yates
//	Contact:	PBWiz@PBWizard.com
//	Home:		http://www.pbwizard.com
//	Date:		25 March 2001

function getRangeHTML(range) {
	var rangeHTML = "";
	var node;
	if ((! range) || ((range.commonAncestorContainer.nodeName == "BODY") && (! range.commonAncestorContainer.firstChild))) {
		// empty
	} else if (node = range.commonAncestorContainer.firstChild) {
		while (node) {
			rangeHTML += getNodeHTML(node, range);
			node = node.nextSibling;
		}
	} else {
		rangeHTML += getNodeHTML(range.startContainer, range);
	}
	return rangeHTML;
}

function getNodeHTML(node, range) {
	if (range && (! nodeInRange(node, range))) return '';
	switch(node.nodeType) {
	case 1: // ELEMENT_NODE
		var nodeHTML = '';
		if ((node != range.startContainer) || (range.startOffset == 0)) {
			nodeHTML += '<' + node.nodeName;
			for (var i = 0; i < node.attributes.length; i++) {
				if (node.attributes.item(i).specified)
//					nodeHTML += ' ' + getNodeHTML(node.attributes.item(i), null);
					nodeHTML += ' ' + node.attributes.item(i).name + '="' + node.attributes.item(i).value + '"';
			}
			nodeHTML += '>';
		}
		if (node.hasChildNodes() || (node.tagName == "TEXTAREA")) {
			var childnode = node.firstChild;
			while (childnode) {
				nodeHTML += getNodeHTML(childnode, range);
				childnode = childnode.nextSibling;
			}
			if ((node != range.startContainer) || (range.startOffset == 0)) {
				nodeHTML += '</' + node.nodeName + '>';
			}
		}
		return nodeHTML;

	case 2: // ATTRIBUTE_NODE
		var nodeHTML = '';
		if (node.specified && (node.nodeName == 'type') && (node.nodeValue == '_moz')) {
			// ignore
		} else if (node.specified && (node.nodeName.substr(0,4) == "_moz")) {
			// ignore
		} else if (node.specified) {
			nodeHTML += node.nodeName + '="' + node.nodeValue + '"';
		}
		return nodeHTML;

	case 3: // TEXT_NODE
		var nodeHTML = node.data;
		if (range) {
			if (node == range.endContainer) {
				nodeHTML = nodeHTML.substring(0, range.endOffset);
			}
			if (node == range.startContainer) {
				nodeHTML = nodeHTML.substring(range.startOffset, nodeHTML.length+1);
			}
		}
		return nodeHTML;

	case 4: // CDATA_SECTION_NODE
		var nodeHTML = node.data;
		if (range) {
			if (node == range.endContainer) {
				nodeHTML = nodeHTML.substring(0, range.endOffset);
			}
			if (node == range.startContainer) {
				nodeHTML = nodeHTML.substring(range.startOffset, nodeHTML.length+1);
			}
		}
		return '<![CDATA[' + nodeHTML + ']]>';
	
	case 5: // ENTITY_REFERENCE_NODE
		return '&' + node.nodeName + ';';
	
	case 6: // ENTITY_NODE
		var nodeHTML = '<!ENTITY ' + node.nodeName;
		if( node.publicId ){
			nodeHTML += ' PUBLIC "' + node.publicId + '"';
			if (node.systemId) {
				nodeHTML += ' "' + node.systemId + '"';
			}
			if (node.notationName) {
				nodeHTML += ' NDATA ' + node.notationName;
			}
		} else if( node.systemId ) {
			nodeHTML += ' SYSTEM "' + node.systemId + '"';
			if( node.notationName ) {
				nodeHTML += ' NDATA ' + node.notationName;
			}
		} else {
			nodeHTML += '"';
			var childnode = node.firstChild;
			while (childnode) {
				nodeHTML += getNodeHTML(childnode, range);
				childnode = childnode.nextSibling;
			}
			nodeHTML += '"';
		}
		return nodeHTML + '>';

	case 7: // PROCESSING_INSTRUCTION_NODE
		var nodeHTML = '<?' + node.target + ' ' + node.data + '?>';
		return nodeHTML;
			
	case 8: // COMMENT_NODE
		var nodeHTML = node.data;
		if (range) {
			if (node == range.endContainer) {
				nodeHTML = nodeHTML.substring(0, range.endOffset);
			}
			if (node == range.startContainer) {
				nodeHTML = nodeHTML.substring(range.startOffset, nodeHTML.length+1);
			}
		}
		nodeHTML = '<!--' + nodeHTML + '-->';
		return nodeHTML;
	
	case 9: // DOCUMENT_NODE
		var nodeHTML = '';
		var foundDocType = false;
		var childnode = node.firstChild;
		while (childnode) {
			if (childnode.nodeType != 10 ) foundDocType = true;
			nodeHTML += getNodeHTML(childnode, range);
			childnode = childnode.nextSibling;
		}
		if (! foundDocType && node.doctype) {
			nodeHTML = getNodeHTML(node.doctype, range) + nodeHTML;
		}
		return nodeHTML;

	case 10: // DOCUMENT_TYPE_NODE
		var nodeHTML = '<!DOCTYPE ' + node.nodeName;
		if (node.publicId) {
			nodeHTML += ' PUBLIC "' + node.publicId + '"';
			if (node.systemId) nodeHTML += ' "' + node.systemId + '"';
		} else if (node.systemId) {
			nodeHTML += ' SYSTEM "' + node.systemId + '"';
		}
		if (node.internalSubset) {
			nodeHTML += ' ' + node.internalSubset;
		}
		nodeHTML += '>\n';
		return nodeHTML;

	case 11: // DOCUMENT_FRAGMENT_NODE
		var nodeHTML = '';
		var childnode = node.firstChild;
		while (childnode) {
			nodeHTML += getNodeHTML(childnode, range);
			childnode = childnode.nextSibling;
		}
		return nodeHTML;

	case 12: // NOTATION_NODE
		var nodeHTML = '<!NOTATION ' + node.nodeName;
		if (node.publicId) {
			nodeHTML += ' PUBLIC "' + node.publicId + '"';
			if (node.systemId) nodeHTML += ' "' + node.systemId + '"';
		} else if (node.systemId) {
			nodeHTML += ' SYSTEM "' + node.systemId + '"';
		}
		nodeHTML += '>';
		return nodeHTML;
	}
}

function nodeInRange(node, range) {
	const START_TO_START = 0;
	const START_TO_END = 1;
	const END_TO_END = 2;
	const END_TO_START = 3;

	try {
		var nodeRange = document.createRange();
		nodeRange.selectNode(node);
		if ((range.compareBoundaryPoints(END_TO_START,nodeRange) == -1) && (range.compareBoundaryPoints(START_TO_END,nodeRange) == 1)) {
			return true;
		}
	} catch(e) {
	}
	return false;
}

function contenteditable_contextmenu(evt){
	if (evt && evt.target && evt.target.ownerDocument && evt.target.ownerDocument.designMode && (evt.target.ownerDocument.designMode == "on")) {
		contenteditable_event_stop(evt);
		var iframe = contenteditable_focused_iframe();
		webeditor_menu('context', false, getAbsoluteOffsetLeft(iframe)+evt.clientX, getAbsoluteOffsetTop(iframe)+evt.clientY);
		return false;
	} else if (evt && evt.target) {
		var container = evt.target;
		while (container && (container.contentEditable != "true")) {
			container = container.parentNode;
		}
		if (container) {
			contenteditable_event_stop(evt);
			webeditor_menu('context', false, evt.clientX, evt.clientY);
			return false;
		}
	}
}



function contenteditable_webeditor_clipboard_cut() {
	if (webeditor.clipboard) {
		try {
			webeditor.clipboardText = contenteditable_copyFromClipboard();
		} catch (e) {
			webeditor.clipboardText = "";
			webeditor.clipboard = false;
		}
	}
}



function contenteditable_webeditor_clipboard_copy() {
	if (webeditor.clipboard) {
		try {
			webeditor.clipboardText = contenteditable_copyFromClipboard();
		} catch (e) {
			webeditor.clipboardText = "";
			webeditor.clipboard = false;
		}
	}
}



function contenteditable_webeditor_clipboard() {
	if (webeditor.clipboard && webeditor.clipboardHTML && webeditor.clipboardText) {
		return true;
	} else {
		return false;
	}
}



function contenteditable_webeditor_clipboard_paste() {
	if (webeditor.clipboard && webeditor.clipboardHTML && webeditor.clipboardText && (webeditor.clipboardText == contenteditable_copyFromClipboard())) {
		return true;
	} else {
		return false;
	}
}



function contenteditable_copyToClipboard(copytext) {
	try {
		// Requires signed code or permission through about:config by setting signed.applets.codebase_principal_support = true
		netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');

		var clipid = Components.interfaces.nsIClipboard;

		var clip = Components.classes["@mozilla.org/widget/clipboard;1"].getService(clipid);
//		var clip = Components.classes["@mozilla.org/widget/clipboard;1"].createInstance(clipid);
//		var clip = Components.classes["@mozilla.org/widget/clipboard;[[[[1]]]]"].createInstance(clipid);
		if (!clip) return false;

		var trans = Components.classes["@mozilla.org/widget/transferable;1"].createInstance(Components.interfaces.nsITransferable);
//		var trans = Components.classes["@mozilla.org/widget/transferable;[[[[1]]]]"].createInstance(Components.interfaces.nsITransferable);
		if (!trans) return false;

		trans.addDataFlavor('text/unicode');

		var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
//		var str = Components.classes["@mozilla.org/supports-string;[[[[1]]]]"].createInstance(Components.interfaces.nsISupportsString);

		str.data=copytext;

		trans.setTransferData("text/unicode", str, copytext.length*2);
//		trans.setTransferData("text/unicode",str,copytext.length*[[[[2]]]]);

		clip.setData(trans,null,clipid.kGlobalClipboard);	   
	} catch (e) {
	}
}



function contenteditable_copyFromClipboard() {
	try {
		// Requires signed code or permission through about:config by setting signed.applets.codebase_principal_support = true
		netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");

		var clipid = Components.interfaces.nsIClipboard;

		var clip = Components.classes["@mozilla.org/widget/clipboard;1"].getService(clipid);
		if (!clip) return "";

		var trans = Components.classes["@mozilla.org/widget/transferable;1"].createInstance(Components.interfaces.nsITransferable);
		if (!trans) return "";

		trans.addDataFlavor("text/unicode");

		clip.getData(trans, clip.kGlobalClipboard);

		var str = new Object();
		var strLength = new Object();

		trans.getTransferData("text/unicode", str, strLength);

		if (str) str = str.value.QueryInterface(Components.interfaces.nsISupportsString);
		if (str) pastetext = str.data.substring(0, strLength.value / 2);

		return pastetext;
	} catch (e) {
		return "";
	}
}
