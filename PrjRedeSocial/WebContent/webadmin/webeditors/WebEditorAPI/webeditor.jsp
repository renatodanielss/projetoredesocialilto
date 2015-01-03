<%@ include file="../../config.jsp" %>

<script src="/<%= mytext.display("adminpath") %>/webeditors/LOAD YOUR JAVASCRIPT PROGRAM CODE HERE/webeditor.js"></script>

<script type="text/javascript">

function WebEditorInit(textareaid) {
	// onload - Turn the default HTML FORM TEXTAREA with the given id (content/summary) into a webeditor

	if (! document.getElementById(textareaid)) return;
	if (! document.getElementById('webeditor_stylesheet')) return;
	if (! document.getElementById(textareaid+'_container')) return;

	// Get the webeditor content (if any) from the default HTML FORM TEXTAREA
	var webeditor_content = document.getElementById(textareaid).value;
	// Get the webeditor stylesheet(s) to be used for the webeditor content
	var webeditor_stylesheet = document.getElementById('webeditor_stylesheet').value;
	// Get the webeditor container
	var webeditor_container = document.getElementById(textareaid+'_container');

	// Inject the webeditor into the webeditor container
	// PLACE YOUR JAVASCRIPT PROGRAM CODE HERE
}



function WebEditorDisplayReady(tabname) {
	// Is the webeditor ready for the 'Primary Content' tab or another tab to be displayed?
	var ready = true;
	// PLACE YOUR JAVASCRIPT PROGRAM CODE HERE
	if (ready) {
		return true;
	} else {
		return false;
	}
}



function WebEditorFocus() {
	// The displayed tab has been changed - "focus"/refresh the webeditor
	// PLACE YOUR JAVASCRIPT PROGRAM CODE HERE
}



function WebEditorStylesheet(stylesheet) {
	// The selected style sheet(s) for the content has been changed - update the webeditor to use the new selected style sheet(s)
	if (stylesheet == false) {
		// no style sheets
		// set the webeditor to not use any style sheet(s) here
		// PLACE YOUR JAVASCRIPT PROGRAM CODE HERE
	} else {
		// stylesheet = '/stylesheet.jsp?id=ID,ID,ID'
		// set the webeditor to use the give style sheet(s) here
		// PLACE YOUR JAVASCRIPT PROGRAM CODE HERE
	}
}



function WebEditorSaveReady(form) {
	// Is the webeditor loaded and inited and ready for the HTML FORM to be posted/saved?
	var ready = true;
	// PLACE YOUR JAVASCRIPT PROGRAM CODE HERE
	if (ready) {
		return true;
	} else {
		return false;
	}
}



function WebEditorSubmit() {
	// The HTML FORM is about to be posted - prepare the webeditor to post the form - ie. copy the content from the webeditor to the HTML FORM TEXTAREA/INPUT
	// PLACE YOUR JAVASCRIPT PROGRAM CODE HERE
}



function WebEditorPreview() {
	// Preview the edited content from the webeditor
	if (typeof(doPreview) == "function") {
		doPreview(); // Post the HTML FORM to be previewed by the web content management system
	}
}



function WebEditorSave() {
	// Save the edited content from the webeditor
	if (typeof(doSaveAndClose) == "function") {
		doSaveAndClose(false); // Post the HTML FORM to be saved by the web content management system - do not close/leave the editor page and return to the index page
		//doSaveAndClose(true); // Post the HTML FORM to be saved by the web content management system - close/leave the editor page and return to the index page
	}
}



// Webeditor call-back functions object for use of the web content management system's Insert Hyperlink and Insert Media pop-up dialogue windows
var webeditor = {};
webeditor.insertHyperlink = function(href, target, text, htmlclass, htmlid, onclick, title) {
	// insert hyperlink into webeditor content
	// PLACE YOUR JAVASCRIPT PROGRAM CODE HERE
};
webeditor.insertFlash = function(url, alt, width, height, htmlclass, htmlid) {
	// insert Flash into webeditor content
	// PLACE YOUR JAVASCRIPT PROGRAM CODE HERE
};
webeditor.insertApplet = function(url, alt, width, height, htmlclass, htmlid) {
	// insert Applet into webeditor content
	// PLACE YOUR JAVASCRIPT PROGRAM CODE HERE
};
webeditor.insertQuicktime = function(url, alt, width, height, htmlclass, htmlid) {
	// insert Quicktime into webeditor content
	// PLACE YOUR JAVASCRIPT PROGRAM CODE HERE
};
webeditor.insertImage = function(url, border, alt, width, height, vspace, hspace, align, htmlclass, htmlid, mouseover, mouseout, usemap){
	// insert Image into webeditor content
	// PLACE YOUR JAVASCRIPT PROGRAM CODE HERE
};



// Open the web content management system's Insert Hyperlink pop-up dialogue window
function WebEditorInsertHyperlink(href, target, text, htmlclass, htmlid, onclick, title) {
	window.open("/webadmin/webeditor/hyperlinkmanager_wcm.jsp?editor=webeditor&href="+encodeURIComponent(href)+"&target="+encodeURIComponent(target)+"&htmlid="+encodeURIComponent(htmlid)+"&htmlclass="+encodeURIComponent(htmlclass)+"&onclick="+encodeURIComponent(onclick)+"&title="+encodeURIComponent(title)+"&text="+encodeURIComponent(text)+"","hyperlink_window","scrollbars=yes,width=750,height=450,resizable=yes,status=yes",true);
}



// Open the web content management system's Insert Media pop-up dialogue window
function WebEditorInsertMedia(url, border, alt, width, height, vspace, hspace, align, htmlclass, htmlid, mouseover, mouseout, usemap, mediaclass) {
	window.open("/webadmin/webeditor/mediamanager_wcm.jsp?editor=webeditor&href="+encodeURIComponent(url)+"&border="+encodeURIComponent(border)+"&alt="+encodeURIComponent(alt)+"&width="+encodeURIComponent(width)+"&height="+encodeURIComponent(height)+"&vspace="+encodeURIComponent(vspace)+"&hspace="+encodeURIComponent(hspace)+"&align="+encodeURIComponent(align)+"&onmouseover="+encodeURIComponent(mouseover)+"&onmouseout="+encodeURIComponent(mouseout)+"&usemap="+encodeURIComponent(usemap)+"&htmlclass="+encodeURIComponent(htmlclass)+"&htmlid="+encodeURIComponent(htmlid)+"&mediaclass="+encodeURIComponent(mediaclass)+"","image_window","scrollbars=yes,width=750,height=500,resizable=yes,status=yes");
}
function WebEditorInsertImage(url, border, alt, width, height, vspace, hspace, align, htmlclass, htmlid, mouseover, mouseout, usemap) {
	WebEditorInsertMedia(url, border, alt, width, height, vspace, hspace, align, htmlclass, htmlid, mouseover, mouseout, usemap, "image");
}
function WebEditorInsertFlash(url, border, alt, width, height, vspace, hspace, align, htmlclass, htmlid, mouseover, mouseout, usemap) {
	WebEditorInsertMedia(url, border, alt, width, height, vspace, hspace, align, htmlclass, htmlid, mouseover, mouseout, usemap, "flash");
}
function WebEditorInsertApplet(url, border, alt, width, height, vspace, hspace, align, htmlclass, htmlid, mouseover, mouseout, usemap) {
	WebEditorInsertMedia(url, border, alt, width, height, vspace, hspace, align, htmlclass, htmlid, mouseover, mouseout, usemap, "applet");
}
function WebEditorInsertQuicktime(url, border, alt, width, height, vspace, hspace, align, htmlclass, htmlid, mouseover, mouseout, usemap) {
	WebEditorInsertMedia(url, border, alt, width, height, vspace, hspace, align, htmlclass, htmlid, mouseover, mouseout, usemap, "quicktime");
}

</script>
