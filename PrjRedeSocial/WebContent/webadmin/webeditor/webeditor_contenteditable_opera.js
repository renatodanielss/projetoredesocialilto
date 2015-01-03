// Asbru Web Content Editor
// (C) 2002-2014 Asbru Ltd.
// www.asbrusoft.com

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
	window.attachEvent("onload", handler);
}

function contenteditable_onload_remove(handler) {
	window.detachEvent("onload", handler);
}

function contenteditable_init() {
	webeditor.refreshToolbar = false;
	if (! webeditor.scrolledContentToTop) webeditor.scrolledContentToTop = new Object();
	if (webeditor.first != false) webeditor.first = true;
	var first = webeditor.first;
	for (var i=0; i<document.getElementsByTagName('iframe').length; i++) {
		var iframe = document.getElementsByTagName('iframe').item(i);
		if ((iframe.className == 'HardCore_contenteditable') && (! contenteditable_inited[iframe.id])) {
			try {
				if (contenteditable_stylesheet[iframe.id]) {
					iframe.contentWindow.document.getElementById('stylesheet').href = contenteditable_stylesheet[iframe.id];
				}
				if (webeditor.direction) iframe.contentWindow.document.dir = webeditor.direction;
				iframe.contentWindow.document.body.contentEditable = true;
				iframe.contentWindow.document.execCommand("redo", false, null);
				// Setting innerHTML may not work properly - changes href/src from relative to absolute - use write instead
				//iframe.contentWindow.document.body.innerHTML = contenteditable_contents[iframe.id];
				iframe.contentWindow.document.body.innerHTML = "&nbsp;";
				// MSIE replace may be broken for escaped "\$1" and "\$2" dollar characters in replacement string
//				iframe.contentWindow.document.write(iframe.contentWindow.document.documentElement.outerHTML.replace(new RegExp("(&nbsp;)?(</body>)", "i"), contenteditable_contents[iframe.id].replace(/\$([_`'+&0-9]+)/g, "\\\$\\$1")+"$2").replace(/\\(\$)\\([_`'+&0-9]+)/g, "$1$2"));
// MSIE may add web page to web browser "history" on document.write - set content using Javascript DOM instead
//				if (! contenteditable_onfocus[i]) contenteditable_onfocus[i] = new Function('event', 'contenteditable_focused='+i+';webeditor_onfocus();webeditor_refreshToolbar(true);');
//				if (! contenteditable_onblur[i]) contenteditable_onblur[i] = new Function('event', 'webeditor_onblur();webeditor_refreshToolbar(true);');
//				if (! contenteditable_onfocus[i]) contenteditable_onfocus[i] = new Function('event', 'contenteditable_focused='+i+';webeditor_onfocus();webeditor_refreshToolbar(false, 1);');
//				if (! contenteditable_onblur[i]) contenteditable_onblur[i] = new Function('event', 'webeditor_onblur();webeditor_refreshToolbar(false, 1);');
				if (! contenteditable_onfocus[i]) contenteditable_onfocus[i] = new Function('event', 'contenteditable_focused='+i+';webeditor_onfocus();');
				if (! contenteditable_onblur[i]) contenteditable_onblur[i] = new Function('event', 'webeditor_onblur();');
				iframe.contentWindow.attachEvent("onfocus", contenteditable_onfocus[i]);
				iframe.contentWindow.attachEvent("onblur", contenteditable_onblur[i]);
				iframe.contentWindow.document.body.attachEvent("onfocus", contenteditable_onfocus[i]);
				iframe.contentWindow.document.body.attachEvent("onblur", contenteditable_onblur[i]);
				iframe.contentWindow.document.attachEvent("onkeydown", contenteditable_event);
				iframe.contentWindow.document.attachEvent("onkeyup", contenteditable_event);
				iframe.contentWindow.document.attachEvent("onkeypress", contenteditable_event);
				iframe.contentWindow.document.attachEvent("onmousedown", contenteditable_event);
				iframe.contentWindow.document.attachEvent("onmouseup", contenteditable_event);
				iframe.contentWindow.document.attachEvent("ondrag", contenteditable_event);
				iframe.contentWindow.document.attachEvent("ondragstart", contenteditable_event);
				iframe.contentWindow.document.attachEvent("ondragend", contenteditable_event);
				iframe.contentWindow.document.attachEvent("ondragenter", contenteditable_event);
				iframe.contentWindow.document.attachEvent("ondragover", contenteditable_event);
				iframe.contentWindow.document.attachEvent("ondragleave", contenteditable_event);
				iframe.contentWindow.document.attachEvent("ondrop", contenteditable_event);
				iframe.contentWindow.document.close();
// MSIE may add web page to web browser "history" on document.write - set content using Javascript DOM instead
contenteditable_setContent(contenteditable_contents[iframe.id], iframe.id);
				var form = iframe;
				while ((form.tagName != "FORM") && (form.tagName != "HTML")) {
					form = form.parentNode;
				}
				if (form.tagName != "HTML") {
					form.attachEvent("onsubmit", contenteditable_onSubmit);
					form[iframe.id].value = contenteditable_contents[iframe.id];
				}
			}  catch (e) {
				alert(Text('webbrowser_unsupported_contenteditable')+"\r\n\r\n"+e);
			}
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
		if (iframe.className == 'HardCore_contenteditable') {
			if ((! webeditor.scrolledContentToTop[i]) && webeditor.scrollContentToTop && iframe && iframe.contentWindow && iframe.contentWindow.document && iframe.contentWindow.document.body) {
				webeditor.scrolledContentToTop[i] = true;
				iframe.contentWindow.focus();
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
	}
	if (webeditor.scrollContentToTop && (webeditor.inited == webeditor.count-1)) {
		var iframe = document.getElementsByTagName('iframe').item(contenteditable_focused);
		if (iframe.contentWindow.document.body.firstChild) {
			contenteditable_selection_node(iframe.contentWindow.document.body.firstChild);
		} else {
			contenteditable_selection_node(iframe.contentWindow.document.body);
		}
		var range = contenteditable_selection_range();
		if (range) range.collapse(true);
		if (range) range.select();
//		iframe.contentWindow.focus();
		webeditor_onfocus();
		webeditor.refreshToolbar = true;
		webeditor_refreshToolbar(true);
////		iframe.contentWindow.blur();
//		window.focus();
		if (document.location.href.indexOf("#") < 0) window.scrollTo(0,0);
	}
}

function contenteditable_enable() {
}

function contenteditable_toolbar(focused) {
	if (webeditor.toolbarid && document.getElementById(webeditor.toolbarid) && document.getElementById(webeditor.toolbarid).contentWindow && document.getElementById(webeditor.toolbarid).contentWindow.document) {
		return document.getElementById(webeditor.toolbarid).contentWindow.document;
	} else if (parent && parent.document && parent.document.getElementById(webeditor.toolbarid) && parent.document.getElementById(webeditor.toolbarid).contentWindow && parent.document.getElementById(webeditor.toolbarid).contentWindow.document) {
		return parent.document.getElementById(webeditor.toolbarid).contentWindow.document;
	} else if (webeditor.toolbar && webeditor.toolbar.contentWindow && webeditor.toolbar.contentWindow.document && webeditor.toolbar.contentWindow.document.body) {
		return webeditor.toolbar.contentWindow.document;
	} else if (focused && contenteditable_focused_iframe().id && document.getElementById('webeditor_toolbar_' + contenteditable_focused_iframe().id)) {
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
function contenteditable_event(event) {
return;
	// MSIE may not focus input field if clicked below content in input field
	if (event && event.srcElement && (event.srcElement.nodeName == "HTML") && event.srcElement.document && event.srcElement.document.body) {
		for (var i=0; i<parent.document.getElementsByTagName('iframe').length; i++) {
			var iframe = document.getElementsByTagName('iframe').item(i);
			if ((iframe.className == 'HardCore_contenteditable') && (iframe.contentWindow.document.body == event.srcElement.document.body)) {
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
		}
	}
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
				contenteditable_event_stop(my_event);
				contenteditable_execcommand("paste", "")
				return;
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
	return contenteditable_selection_range(contentSelection).htmlText;
}

function contenteditable_selection_range_control(contentSelection) {
	if (! contentSelection) contentSelection = contenteditable_selection();
	if (contentSelection) {
		return (contentSelection.type == 'Control');
	}
}

function contenteditable_selection_range_parentNode(contentRange) {
	if (! contentRange) contentRange = contenteditable_selection_range();
	if (contentRange && contentRange.parentElement) {
		try {
			return contentRange.parentElement();
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
	var parentNode = contentRange.parentElement();
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
					startNode = node.nextSibling;
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
	var parentNode = contentRange.parentElement();
	if (parentNode && parentNode.firstChild) {
		endNode = parentNode.firstChild;
		for (var node=parentNode.firstChild; node; node=node.nextSibling) {
			var testRange = contenteditable_createrange();
			if (node.nodeName != "#text") {
				testRange.moveToElementText(node);
				if ((contentRange.compareEndPoints("StartToStart",testRange)==-1) && (contentRange.compareEndPoints("StartToEnd",testRange)==-1) && (contentRange.compareEndPoints("EndToStart",testRange)==-1) && (contentRange.compareEndPoints("EndToEnd",testRange)==-1)) {
					endNode = node.previousSibling;
					break;
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
			testRange.moveToElementText(endNode.nextSibling);
		} else {
			testRange.moveToElementText(endNode.parentNode);
		}
		for (var i=endNode.length; i>=0; i--) {
			if (range.compareEndPoints("EndToEnd",testRange)==0) {
				endOffset = i + 1;
				break;
			}
			testRange.moveEnd("character", -1);
		}
	}
	return endOffset;
}

function contenteditable_selection_range(contentSelection) {
	if (! contentSelection) contentSelection = contenteditable_selection();
	if (contentSelection) {
		if (contentSelection.type == 'Control') {
			var range = contenteditable_createrange();
			try {
				range.moveToElementText(contentSelection.createRange()(0));
			} catch(e) {
				range = contenteditable_createtextrange();
				try {
					range.moveToElementText(contentSelection.createRange()(0));
				} catch(e) {
				}
			}
			return range;
		} else {
			try {
				return contentSelection.createRange();
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
	var range = contenteditable_selection_range();
	var container_exact = false;
	var container = false;
	if (contenteditable_selection_range_control()) {
		container = contenteditable_selection().createRange()(0);
	} else {
		container = contenteditable_selection_range_parentNode();
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
	if (tagName) {
		var selection_node = container_exact || container || false;
		while (selection_node && (tagName.toUpperCase() != selection_node.tagName)) {
			selection_node = selection_node.parentNode;
		}
		return selection_node;
	} else {
		if (contenteditable_focused_document()) {
			return container_exact || container || contenteditable_focused_document().body;
		} else {
			return container_exact || container;
		}
	}
}



function contenteditable_selection_all() {
	contenteditable_selection_node(contenteditable_focused_document().body);
}



function contenteditable_selection_node(node) {
	webeditor.selection_node = node.nodeName;
	// MSIE may not select everything when BODY is selected - start may be set to first text node instead of first non-text node - no known workaround
	if ((node.nodeName == "TABLE") || (node.nodeName == "IMG") || (node.nodeName == "INPUT") || (node.nodeName == "SELECT") || (node.nodeName == "TEXTAREA")) {
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
return;
	if (bookmark) {
		var range;
		if (range = contenteditable_selection_range()) {
			try {
				range.moveToBookmark(bookmark);
				range.select();
				range.scrollIntoView();
			} catch(e) {
			}
		}
		if (contenteditable_focused_contentwindow()) contenteditable_focused_contentwindow().focus();
	} else {
		var range;
		if (range = contenteditable_selection_range()) {
			bookmark = range.getBookmark();
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

function contenteditable_setContent_body(content, id, iframe) {
	iframe.contentWindow.document.body.innerHTML = "";
	contenteditable_selection_node(iframe.contentWindow.document.body);
	contenteditable_pasteContent(content, id);
	contenteditable_setBody(iframe.contentWindow.document.body, iframe.id)
}

function contenteditable_insertNodeAtSelection(contentWindow, insertNode, insertHTML) {
	var insertedNode = insertNode;
	if (contentWindow) contentWindow.focus();
	var selection = contenteditable_selection(contentWindow);
	try {
		var range = contenteditable_selection_range(selection);
		if (insertNode.outerHTML) {
			insertedNode = contenteditable_insertNodeAtSelection_outerHTML(range, insertNode, insertHTML);
			contenteditable_event_paste_fix(insertedNode);
			range = contenteditable_selection_range();
			range.moveToElementText(insertedNode);
			range.collapse(false);
			range.select();
		} else {
			var expandednode = contenteditable_expanded_node(range);
			if (expandednode) {
				var new_node = contentWindow.document.createTextNode(insertNode.nodeValue);
				var parent_node = expandednode.parentNode;
				parent_node.replaceChild(new_node, expandednode);
				insertedNode = new_node;
			} else {
//				range.pasteHTML(insertNode.nodeValue);
				range.pasteHTML(contenteditable_encodeScriptTags(insertNode.nodeValue));
				// MISSING: insertedNode = [inserted node in document DOM]
			}
		}
	} catch(e) {
		try {
			var range = contenteditable_selection().createRange()(0);
			if (insertNode.outerHTML) {
				insertedNode = contenteditable_insertNodeAtSelection_outerHTML(range, insertNode, insertHTML);
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

function contenteditable_insertNodeAtSelection_outerHTML(range, insertNode, insertHTML) {
	var insertedNode = insertNode;
	if ((! insertNode.childNodes) || (! insertNode.childNodes.length)) {
		if (insertNode.outerHTML) {
			if (! insertNode.id) insertNode.id = "HardCoreWebContentEditorInsertNodeAtSelectionDummy";
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
			if (expandednode) {
				if (expandednode.nodeName == "BODY") {
					expandednode.innerHTML = '<div id="HardCoreWebContentEditorInsertNodeAtSelectionDummy">&nbsp;</div>';
				} else {
					expandednode.outerHTML = '<div id="HardCoreWebContentEditorInsertNodeAtSelectionDummy">&nbsp;</div>';
				}
			} else if ((range.text == range.htmlText) && (insertNode.nodeName != "DIV")) {
				range.pasteHTML('<span id="HardCoreWebContentEditorInsertNodeAtSelectionDummy">&nbsp;</span>');
			} else {
				range.pasteHTML('<div id="HardCoreWebContentEditorInsertNodeAtSelectionDummy">&nbsp;</div>');
			}
		} catch(e) {
			range.pasteHTML('<div id="HardCoreWebContentEditorInsertNodeAtSelectionDummy">&nbsp;</div>');
		}
		var dummyNode = contenteditable_focused_document().getElementById('HardCoreWebContentEditorInsertNodeAtSelectionDummy');
		if (! dummyNode) {
			if (range && range.parentElement() && (range.parentElement().id == 'HardCoreWebContentEditorInsertNodeAtSelectionDummy')) dummyNode = range.parentElement();
			if (range && range.parentElement() && range.parentElement().firstChild && (range.parentElement().firstChild.id == 'HardCoreWebContentEditorInsertNodeAtSelectionDummy')) dummyNode = range.parentElement().firstChild;
		}
		if (dummyNode) {
			var previousSibling = dummyNode.previousSibling;
			var parentNode = dummyNode.parentNode;
			if (false && insertHTML) {
				// range.pasteHTML may generate invalid HTML code with overlapping tags and broken DOM
				contenteditable_selection_node(dummyNode);
				var range = contenteditable_selection_range();
				range.pasteHTML(insertHTML);
			} else {
				// MSIE may have broken/removed PARAM tags inside OBJECT tag when insertNode.innerHTML was set
				dummyNode.outerHTML = insertNode.outerHTML;
			}
			if (previousSibling) {
				insertedNode = previousSibling.nextSibling;
			} else {
				insertedNode = parentNode.firstChild;
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
}

function contenteditable_BlockDirLTR(id) {
	contenteditable_execcommand(id);
}

function contenteditable_BlockDirRTL(id) {
	contenteditable_execcommand(id);
}





function contenteditable_formatblock_query() {
	return ('' + contenteditable_focused_document().queryCommandValue("formatblock")).toLowerCase();
}



function contenteditable_formatblock(command,value) {
	if (value == "<p>") value = "normal";
	return contenteditable_execcommand(command,value);
}



function contenteditable_formatclass(command,value) {
	var range = contenteditable_selection_range();
	var container = contenteditable_selection_range_parentNode();
	if ((range.text == range.htmlText) && (range.text != '')) {
		range.pasteHTML('<span class="'+value+'">' + range.text + '</span>');
	} else if ((container.childNodes.length == 1) && (container.childNodes[0].nodeType == 3)) { // TEXT NODE
		contenteditable_formatclass_attribute(container, value);
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
		if (webeditor.useCSS) contenteditable_useCSS();
		contenteditable_focused_document().execCommand(command, false, contenteditable_execcommand_value(command, value));
	} catch(e) {
		return false;
	}
	return true;
}

function contenteditable_execcommand_value(command, value) {
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
		selection.createRange().pasteHTML(value);
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
	textarea.detachEvent("onfocus", contenteditable_onfocus[contenteditable_focused]);
	textarea.attachEvent("onfocus", contenteditable_onfocus[contenteditable_focused]);
	textarea.onfocus = contenteditable_onfocus[contenteditable_focused];
	textarea.detachEvent("onblur", contenteditable_onblur[contenteditable_focused]);
	textarea.attachEvent("onblur", contenteditable_onblur[contenteditable_focused]);
	textarea.onblur = contenteditable_onblur[contenteditable_focused];
}

function contenteditable_viewsource_textarea2html(textarea, iframe) {
	var content = textarea.value;
	content = contenteditable_split_content(iframe.id, content);
	try {
		if (webeditor_custom_encode) content = webeditor_custom_encode(content);
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

	iframe.contentWindow.detachEvent("onfocus", contenteditable_onfocus[contenteditable_focused]);
	iframe.contentWindow.attachEvent("onfocus", contenteditable_onfocus[contenteditable_focused]);
	iframe.contentWindow.document.body.detachEvent("onfocus", contenteditable_onfocus[contenteditable_focused]);
	iframe.contentWindow.document.body.attachEvent("onfocus", contenteditable_onfocus[contenteditable_focused]);
	iframe.contentWindow.detachEvent("onblur", contenteditable_onblur[contenteditable_focused]);
	iframe.contentWindow.attachEvent("onblur", contenteditable_onblur[contenteditable_focused]);
	iframe.contentWindow.document.body.detachEvent("onblur", contenteditable_onblur[contenteditable_focused]);
	iframe.contentWindow.document.body.attachEvent("onblur", contenteditable_onblur[contenteditable_focused]);
	iframe.contentWindow.document.detachEvent("onkeydown", contenteditable_event);
	iframe.contentWindow.document.attachEvent("onkeydown", contenteditable_event);
	iframe.contentWindow.document.detachEvent("onkeyup", contenteditable_event);
	iframe.contentWindow.document.attachEvent("onkeyup", contenteditable_event);
	iframe.contentWindow.document.detachEvent("onkeypress", contenteditable_event);
	iframe.contentWindow.document.attachEvent("onkeypress", contenteditable_event);
	iframe.contentWindow.document.detachEvent("onmousedown", contenteditable_event);
	iframe.contentWindow.document.attachEvent("onmousedown", contenteditable_event);
	iframe.contentWindow.document.detachEvent("onmouseup", contenteditable_event);
	iframe.contentWindow.document.attachEvent("onmouseup", contenteditable_event);
	iframe.contentWindow.document.detachEvent("ondrag", contenteditable_event);
	iframe.contentWindow.document.attachEvent("ondrag", contenteditable_event);
	iframe.contentWindow.document.detachEvent("ondragstart", contenteditable_event);
	iframe.contentWindow.document.attachEvent("ondragstart", contenteditable_event);
	iframe.contentWindow.document.detachEvent("ondragend", contenteditable_event);
	iframe.contentWindow.document.attachEvent("ondragend", contenteditable_event);
	iframe.contentWindow.document.detachEvent("ondragenter", contenteditable_event);
	iframe.contentWindow.document.attachEvent("ondragenter", contenteditable_event);
	iframe.contentWindow.document.detachEvent("ondragover", contenteditable_event);
	iframe.contentWindow.document.attachEvent("ondragover", contenteditable_event);
	iframe.contentWindow.document.detachEvent("ondragleave", contenteditable_event);
	iframe.contentWindow.document.attachEvent("ondragleave", contenteditable_event);
	iframe.contentWindow.document.detachEvent("ondrop", contenteditable_event);
	iframe.contentWindow.document.attachEvent("ondrop", contenteditable_event);
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
			node.outerHTML = node.outerHTML.replace(/^(<INPUT[^>]+type=)("[a-z]*")([^>]*>)$/gi, "$1"+value+"$3");
		} else if (node.outerHTML.match(/^(<INPUT[^>]+type=)([a-z]+)([^>]*>)$/gi)) {
			node.outerHTML = node.outerHTML.replace(/^(<INPUT[^>]+type=)([a-z]+)([^>]*>)$/gi, "$1"+value+"$3");
		} else {
			node.outerHTML = node.outerHTML.replace(/^(<INPUT[^>]+)([^>]*>)$/gi, "$1 type="+value+" $3");
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

function contenteditable_getAttribute(node, attribute) {
// MSIE element.getAttribute(attribute) may not work properly - changes href/src from relative to absolute + returns unspecified default values
	var value = "";
	if (node) {
		if (node.outerHTML) {
			var node_outerHTML = node.outerHTML;
			RegExp.global = true;
			RegExp.multiline = true;
			var quotedValue = new RegExp('^[^>]*[ \t\r\n]'+attribute+'="([^"]*)"', 'gi');
			var unquotedValue = new RegExp('^[^>]*[ \t\r\n]'+attribute+'=([^ >]*)', 'gi');
			var flagAttribute = new RegExp('^[^>]*[ \t\r\n]('+attribute+')[ \t\r\n>]', 'gi');
			var matches;
			if (matches = quotedValue.exec(node.outerHTML)) {
				value = matches[1] || "";
			} else if (matches = unquotedValue.exec(node.outerHTML)) {
				value = matches[1] || "";
			} else if (matches = flagAttribute.exec(node.outerHTML)) {
				value = matches[1] || "";
			}
		} else if (node.getAttribute) {
			value = node.getAttribute(attribute) || "";
		}
		value = value.replace(/&amp;/gi, "&");
	}
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
// MSIE insertNodeAtSelection/pasteHTML may not work properly - changes src from relative to absolute + sets unspecified default values
	var img = null;
	var range = contenteditable_selection_range();
	range.moveStart("character",-1);
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
	}
}



function contenteditable_insertmap_fix(node, href, target, text, htmlclass, htmlid, onclick) {
}



function contenteditable_insertlink_fix(node, href, target, text, htmlclass, htmlid, onclick, title) {
// MSIE insertNodeAtSelection/pasteHTML may not work properly - changes src from relative to absolute + sets unspecified default values
	var a = null;
	var range = contenteditable_selection_range();
	range.moveStart("character",-1);
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



function contenteditable_execcommand_fix(command, value) {
}



function contenteditable_formatclass_fix(command, value) {
}



function contenteditable_nobr_fix() {
}



function contenteditable_box_fix() {
	// MSIE may not display set class/style for anchor
	contenteditable_focused_contentwindow().document.body.innerHTML = contenteditable_focused_contentwindow().document.body.innerHTML;
	contenteditable_event_paste_fix(contenteditable_focused_contentwindow().document.body);
}



function contenteditable_mailto_fix() {
	// MSIE may not display set class/style for anchor
	contenteditable_focused_contentwindow().document.body.innerHTML = contenteditable_focused_contentwindow().document.body.innerHTML;
	contenteditable_event_paste_fix(contenteditable_focused_contentwindow().document.body);
}



function contenteditable_anchor_fix() {
	// MSIE may not display set class/style for anchor
	contenteditable_focused_contentwindow().document.body.innerHTML = contenteditable_focused_contentwindow().document.body.innerHTML;
	contenteditable_event_paste_fix(contenteditable_focused_contentwindow().document.body);
}



function contenteditable_cleanContent_fix() {
}



function contenteditable_formatContent_fix(content) {
	return content;
}



function contenteditable_node_attributes_fix(attributes) {
	return attributes;
}



function contenteditable_pasteContent_node() {
	return document.createElement("div");
}



function contenteditable_paste_fix_prepare() {
}



function contenteditable_paste_fix() {
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

