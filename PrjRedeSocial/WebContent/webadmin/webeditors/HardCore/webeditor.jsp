<%@ include file="../../config.jsp" %><%
	// Get the webeditor configuration settings

	String toolbar = "";
	String toolbar1 = "";
	String toolbar2 = "";
	String toolbar3 = "";
	String toolbar4 = "";
	String toolbar5 = "";
	if ((! adminuser.getHardcoreToolbar().equals("")) || (! adminuser.getHardcoreToolbar1().equals("")) || (! adminuser.getHardcoreToolbar2().equals("")) || (! adminuser.getHardcoreToolbar3().equals("")) || (! adminuser.getHardcoreToolbar4().equals("")) || (! adminuser.getHardcoreToolbar5().equals(""))) {
		toolbar = adminuser.getHardcoreToolbar();
		toolbar1 = adminuser.getHardcoreToolbar1();
		toolbar2 = adminuser.getHardcoreToolbar2();
		toolbar3 = adminuser.getHardcoreToolbar3();
		toolbar4 = adminuser.getHardcoreToolbar4();
		toolbar5 = adminuser.getHardcoreToolbar5();
	} else {
		toolbar = myconfig.get(db, "hardcore_toolbar");
		toolbar1 = myconfig.get(db, "hardcore_toolbar1");
		toolbar2 = myconfig.get(db, "hardcore_toolbar2");
		toolbar3 = myconfig.get(db, "hardcore_toolbar3");
		toolbar4 = myconfig.get(db, "hardcore_toolbar4");
		toolbar5 = myconfig.get(db, "hardcore_toolbar5");
	}
	String browseedit_toolbar = "";
	String browseedit_toolbar1 = "";
	String browseedit_toolbar2 = "";
	String browseedit_toolbar3 = "";
	String browseedit_toolbar4 = "";
	String browseedit_toolbar5 = "";
	if ((! adminuser.getHardcoreToolbar().equals("")) || (! adminuser.getHardcoreToolbar1().equals("")) || (! adminuser.getHardcoreToolbar2().equals("")) || (! adminuser.getHardcoreToolbar3().equals("")) || (! adminuser.getHardcoreToolbar4().equals("")) || (! adminuser.getHardcoreToolbar5().equals(""))) {
		browseedit_toolbar = adminuser.getHardcoreToolbar();
		browseedit_toolbar1 = adminuser.getHardcoreToolbar1();
		browseedit_toolbar2 = adminuser.getHardcoreToolbar2();
		browseedit_toolbar3 = adminuser.getHardcoreToolbar3();
		browseedit_toolbar4 = adminuser.getHardcoreToolbar4();
		browseedit_toolbar5 = adminuser.getHardcoreToolbar5();
	} else {
		browseedit_toolbar = myconfig.get(db, "hardcore_toolbar_browseedit");
		browseedit_toolbar1 = myconfig.get(db, "hardcore_toolbar1_browseedit");
		browseedit_toolbar2 = myconfig.get(db, "hardcore_toolbar2_browseedit");
		browseedit_toolbar3 = myconfig.get(db, "hardcore_toolbar3_browseedit");
		browseedit_toolbar4 = myconfig.get(db, "hardcore_toolbar4_browseedit");
		browseedit_toolbar5 = myconfig.get(db, "hardcore_toolbar5_browseedit");
	}
	String onenter = "";
	if (! adminuser.getHardcoreOnEnter().equals("")) {
		onenter = adminuser.getHardcoreOnEnter();
	} else {
		onenter = myconfig.get(db, "hardcore_onenter");
	}
	String onshiftenter = "";
	if (! adminuser.getHardcoreOnShiftEnter().equals("")) {
		onshiftenter = adminuser.getHardcoreOnShiftEnter();
	} else {
		onshiftenter = myconfig.get(db, "hardcore_onshiftenter");
	}
	String onctrlenter = "";
	if (! adminuser.getHardcoreOnCtrlEnter().equals("")) {
		onctrlenter = adminuser.getHardcoreOnCtrlEnter();
	} else {
		onctrlenter = myconfig.get(db, "hardcore_onctrlenter");
	}
	String onaltenter = "";
	if (! adminuser.getHardcoreOnAltEnter().equals("")) {
		onaltenter = adminuser.getHardcoreOnAltEnter();
	} else {
		onaltenter = myconfig.get(db, "hardcore_onaltenter");
	}
	String width = "600";
	if (! adminuser.getHardcoreWidth().equals("")) {
		width = adminuser.getHardcoreWidth();
	} else {
		width = myconfig.get(db, "hardcore_width");
	}
	String height = "450";
	if (! adminuser.getHardcoreHeight().equals("")) {
		height = adminuser.getHardcoreHeight();
	} else {
		height = myconfig.get(db, "hardcore_height");
	}
	String format = "";
	if (! adminuser.getHardcoreFormat().equals("")) {
		format = adminuser.getHardcoreFormat();
	} else {
		format = myconfig.get(db, "hardcore_format");
	}
	String encoding = "";
	if (! adminuser.getHardcoreEncoding().equals("")) {
		encoding = adminuser.getHardcoreEncoding();
	} else {
		encoding = myconfig.get(db, "hardcore_encoding");
	}
	String[] formatblock;
	if (! adminuser.getHardcoreFormatBlock().equals("")) {
		formatblock = adminuser.getHardcoreFormatBlock().split("\\n");
	} else {
		formatblock = myconfig.get(db, "hardcore_formatblock").split("\\n");
	}
	String[] fontname;
	if (! adminuser.getHardcoreFontName().equals("")) {
		fontname = adminuser.getHardcoreFontName().split("\\n");
	} else {
		fontname = myconfig.get(db, "hardcore_fontname").split("\\n");
	}
	String[] fontsize;
	if (! adminuser.getHardcoreFontSize().equals("")) {
		fontsize = adminuser.getHardcoreFontSize().split("\\n");
	} else {
		fontsize = myconfig.get(db, "hardcore_fontsize").split("\\n");
	}
%>

<script src="/<%= mytext.display("adminpath") %>/webeditor/webeditor.js.jsp"></script>
<script type="text/javascript">

if (typeof(webeditor) != "undefined") {
	if (! webeditor.shortenLocalURLsRegExp["^index.jsp"]) webeditor.shortenLocalURLsRegExp["^index.jsp"] = "/index.jsp";
	if (! webeditor.shortenLocalURLsRegExp["^page.jsp"]) webeditor.shortenLocalURLsRegExp["^page.jsp"] = "/page.jsp";
	if (! webeditor.shortenLocalURLsRegExp["^image.jsp"]) webeditor.shortenLocalURLsRegExp["^image.jsp"] = "/image.jsp";
	if (! webeditor.shortenLocalURLsRegExp["^file.jsp"]) webeditor.shortenLocalURLsRegExp["^file.jsp"] = "/file.jsp";
	if (! webeditor.shortenLocalURLsRegExp["^link.jsp"]) webeditor.shortenLocalURLsRegExp["^link.jsp"] = "/link.jsp";
	if (! webeditor.shortenLocalURLsRegExp["^product.jsp"]) webeditor.shortenLocalURLsRegExp["^product.jsp"] = "/product.jsp";

	if (! webeditor.shortenLocalURLsRegExp["^script.jsp"]) webeditor.shortenLocalURLsRegExp["^script.jsp"] = "/script.jsp";
	if (! webeditor.shortenLocalURLsRegExp["^stylesheet.jsp"]) webeditor.shortenLocalURLsRegExp["^stylesheet.jsp"] = "/stylesheet.jsp";
	if (! webeditor.shortenLocalURLsRegExp["^template.jsp"]) webeditor.shortenLocalURLsRegExp["^template.jsp"] = "/template.jsp";

	if (! webeditor.shortenLocalURLsRegExp["^contentitem.jsp"]) webeditor.shortenLocalURLsRegExp["^contentitem.jsp"] = "/contentitem.jsp";
	if (! webeditor.shortenLocalURLsRegExp["^data.jsp"]) webeditor.shortenLocalURLsRegExp["^data.jsp"] = "/data.jsp";
	if (! webeditor.shortenLocalURLsRegExp["^element.jsp"]) webeditor.shortenLocalURLsRegExp["^element.jsp"] = "/element.jsp";

	if (! webeditor.shortenLocalURLsRegExp["^atom.jsp"]) webeditor.shortenLocalURLsRegExp["^atom.jsp"] = "/atom.jsp";
	if (! webeditor.shortenLocalURLsRegExp["^rss.jsp"]) webeditor.shortenLocalURLsRegExp["^rss.jsp"] = "/rss.jsp";
	if (! webeditor.shortenLocalURLsRegExp["^xml.jsp"]) webeditor.shortenLocalURLsRegExp["^xml.jsp"] = "/xml.jsp";

	if (! webeditor.shortenLocalURLsRegExp["^contact.jsp"]) webeditor.shortenLocalURLsRegExp["^contact.jsp"] = "/contact.jsp";
	if (! webeditor.shortenLocalURLsRegExp["^guestbook.jsp"]) webeditor.shortenLocalURLsRegExp["^guestbook.jsp"] = "/guestbook.jsp";
	if (! webeditor.shortenLocalURLsRegExp["^post.jsp"]) webeditor.shortenLocalURLsRegExp["^post.jsp"] = "/post.jsp";
	if (! webeditor.shortenLocalURLsRegExp["^search.jsp"]) webeditor.shortenLocalURLsRegExp["^search.jsp"] = "/search.jsp";
	if (! webeditor.shortenLocalURLsRegExp["^shopcart.jsp"]) webeditor.shortenLocalURLsRegExp["^shopcart.jsp"] = "/shopcart.jsp";

	if (! webeditor.shortenLocalURLsRegExp["^register.jsp"]) webeditor.shortenLocalURLsRegExp["^register.jsp"] = "/register.jsp";
	if (! webeditor.shortenLocalURLsRegExp["^subscribe.jsp"]) webeditor.shortenLocalURLsRegExp["^subscribe.jsp"] = "/subscribe.jsp";
	if (! webeditor.shortenLocalURLsRegExp["^unsubscribe.jsp"]) webeditor.shortenLocalURLsRegExp["^unsubscribe.jsp"] = "/unsubscribe.jsp";
	if (! webeditor.shortenLocalURLsRegExp["^login.jsp"]) webeditor.shortenLocalURLsRegExp["^login.jsp"] = "/login.jsp";
	if (! webeditor.shortenLocalURLsRegExp["^login_post.jsp"]) webeditor.shortenLocalURLsRegExp["^login_post.jsp"] = "/login_post.jsp";
	if (! webeditor.shortenLocalURLsRegExp["^logout.jsp"]) webeditor.shortenLocalURLsRegExp["^logout.jsp"] = "/logout.jsp";

	if (! webeditor.shortenLocalURLsRegExp["^[./]+image.jsp"]) webeditor.shortenLocalURLsRegExp["^[./]+image.jsp"] = "/image.jsp";
}

</script>
<script type="text/javascript">

function WebEditorInit(textareaid) {
	// onload - Turn the default HTML FORM TEXTAREA with the given id (content/summary) into a webeditor

//	if (! document.getElementById(textareaid)) return;
//	if (! document.getElementById('webeditor_stylesheet')) return;
//	if (! document.getElementById(textareaid+'_container')) return;

	var webeditor_content = false;
	var webeditor_stylesheet = false;
	var webeditor_container = false;
	var webeditor_toolbar = false;
	var webeditor_DOMinspector = false;
	var webeditor_contenteditable = false;
	if (! textareaid) {
		// end Browse & Edit mode inline editing
		webeditor_custom_save = null;
		return;
	} else if (typeof(textareaid) == "string") {
		// Get the webeditor content (if any) from the default HTML FORM TEXTAREA
		if (document.getElementById(textareaid)) webeditor_content = document.getElementById(textareaid).value;
		// Get the webeditor stylesheet(s) to be used for the webeditor content
		if (document.getElementById('webeditor_stylesheet')) webeditor_stylesheet = document.getElementById('webeditor_stylesheet').value;
		// Get the webeditor container
		webeditor_container = document.getElementById(textareaid+'_container');
		webeditor_toolbar = document.getElementById(textareaid+'_toolbar');
		webeditor_DOMinspector = document.getElementById(textareaid+'_DOMinspector');
	} else if (textareaid && textareaid.contentEditable) {
		webeditor_contenteditable = true;
	}

	var format = '<%= format %>';
	if (document.getElementById('webeditor_format') && document.getElementById('webeditor_format').value) format = document.getElementById('webeditor_format').value;

	var encoding = '<%= encoding %>';
	if (document.getElementById('webeditor_encoding')) encoding = document.getElementById('webeditor_encoding').value;

	// Inject the webeditor into the webeditor container
	var options = new Array();

	var formatblock = new Array();
<%
	for (int i=0; i<formatblock.length; i++) {
		String[] formatblockparts = formatblock[i].trim().split("=");
		if ((formatblockparts.length >= 2) && (! formatblockparts[0].equals("")) && (! formatblockparts[1].equals(""))) {
	
%>	<%= "formatblock[formatblock.length] = new Object();" + "\r\n" %><%
%>	<%= "formatblock[formatblock.length-1].name='" + formatblockparts[0] + "';" + "\r\n" %><%
%>	<%= "formatblock[formatblock.length-1].value='" + formatblockparts[1] + "';" + "\r\n" %><%
	
		}
	}
%>
	if (formatblock.length > 0) {
		options['formatblock'] = formatblock;
	}
	
	var fontname = new Array();
<%
	for (int i=0; i<fontname.length; i++) {
		String[] fontnameparts = fontname[i].trim().split("=");
		if ((fontnameparts.length >= 2) && (! fontnameparts[0].equals("")) && (! fontnameparts[1].equals(""))) {

%>	<%= "fontname[fontname.length] = new Object();" + "\r\n" %><%
%>	<%= "fontname[fontname.length-1].name='" + fontnameparts[0] + "';" + "\r\n" %><%
%>	<%= "fontname[fontname.length-1].value='" + fontnameparts[1] + "';" + "\r\n" %><%

		}
	}
%>
	if (fontname.length > 0) {
		options['fontname'] = fontname;
	}
	
	var fontsize = new Array();
<%
	for (int i=0; i<fontsize.length; i++) {
		String[] fontsizeparts = fontsize[i].trim().split("=");
		if ((fontsizeparts.length >= 2) && (! fontsizeparts[0].equals("")) && (! fontsizeparts[1].equals(""))) {

%>	<%= "fontsize[fontsize.length] = new Object();" + "\r\n" %><%
%>	<%= "fontsize[fontsize.length-1].name='" + fontsizeparts[0] + "';" + "\r\n" %><%
%>	<%= "fontsize[fontsize.length-1].value='" + fontsizeparts[1] + "';" + "\r\n" %><%

		}
	}
%>
	if (fontsize.length > 0) {
		options['fontsize'] = fontsize;
	}

	if (webeditor_toolbar && (textareaid == "WCMeditor")) {
		options['toolbar'] = '<%= browseedit_toolbar %>';
		options['toolbar1'] = '<%= browseedit_toolbar1 %>';
		options['toolbar2'] = '<%= browseedit_toolbar2 %>';
		options['toolbar3'] = '<%= browseedit_toolbar3 %>';
		options['toolbar4'] = '<%= browseedit_toolbar4 %>';
		options['toolbar5'] = '<%= browseedit_toolbar5 %>';
	} else {
		options['toolbar'] = '<%= toolbar %>';
		options['toolbar1'] = '<%= toolbar1 %>';
		options['toolbar2'] = '<%= toolbar2 %>';
		options['toolbar3'] = '<%= toolbar3 %>';
		options['toolbar4'] = '<%= toolbar4 %>';
		options['toolbar5'] = '<%= toolbar5 %>';
	}

	var width = '<%= width %>';
	var height = '<%= height %>';
	if (webeditor_container && webeditor_container.style && webeditor_container.style.width) {
		width = parseInt(webeditor_container.style.width);
		webeditor_container.style.width = '';
	}
	if (webeditor_container && webeditor_container.style && webeditor_container.style.height) {
		height = parseInt(webeditor_container.style.height);
		webeditor_container.style.height = '';
	}
	if (webeditor) {
		if (! width) {
			webeditor.minWidth = "600px";
		} else {
			webeditor.minWidth = "";
		}
		if (! height ) {
			webeditor.minHeight = "450px";
		} else {
			webeditor.minHeight = "";
		}
	}

	options.baseHref = baseHref;
	options.baseHrefPrefix = baseHrefPrefix;
	options.language = 'jsp';
	options.manager = 'manager_wcm';
	options.onEnter = '<%= onenter %>';
	options.onShiftEnter = '<%= onshiftenter %>';
	options.onCtrlEnter = '<%= onctrlenter %>';
	options.onAltEnter = '<%= onaltenter %>';
	options.width = width;
	options.height = height;
	options.format = format;
	options.encoding = encoding;

	var myhtml = '<table cellpadding="0" cellspacing="0" border="0" width="100%"><tr><td id="' + textareaid + '_toolbar_container">WebEditorToolbar</td></tr></table>\r\n';
	myhtml += '<table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td id="' + textareaid + '_webeditor_container">WebEditor</td></tr></table>\r\n';
	myhtml += '<table cellpadding="0" cellspacing="0" border="0" width="100%"><tr><td id="' + textareaid + '_DOMinspector_container">WebEditorDOMInspector</td></tr></table>';
	if (webeditor_container) {
		webeditor_container.innerHTML = myhtml;
	}

	if (webeditor_toolbar) {
		options.container = webeditor_toolbar;
		WebEditorToolbar(textareaid, options);
	} else if (webeditor_container) {
		options.container = textareaid+'_toolbar_container';
		WebEditorToolbar(textareaid, options);
	} else if (webeditor_contenteditable) {
		contenteditable_event_handler(textareaid, "keydown", contenteditable_event, true);
		contenteditable_event_handler(textareaid, "keyup", contenteditable_event, true);
		contenteditable_event_handler(textareaid, "keypress", contenteditable_event, true);
		contenteditable_event_handler(textareaid, "mousedown", contenteditable_event, true);
		contenteditable_event_handler(textareaid, "mouseup", contenteditable_event, true);
		contenteditable_event_handler(textareaid, "drag", contenteditable_event, true);
		if (webeditor.contextmenus) contenteditable_event_handler(textareaid, "contextmenu", contenteditable_contextmenu, true);
		webeditor_custom_save = function() {
			WCMdoSaveAll();
		}
	}

	if (webeditor_DOMinspector) {
		WebEditorDOMInspector(textareaid, webeditor_DOMinspector);
	} else if (webeditor_container && (webeditor_content !== false)) {
		WebEditorDOMInspector(textareaid, textareaid+'_DOMinspector_container');
	}

	if (webeditor_container && (webeditor_content !== false) && webeditor_stylesheet) {
		WebEditor(textareaid, webeditor_content, { baseHref: baseHref, baseHrefPrefix: baseHrefPrefix, language: 'jsp', stylesheet: webeditor_stylesheet, manager: 'manager_wcm', onEnter: '<%= onenter %>', onShiftEnter: '<%= onshiftenter %>', onCtrlEnter: '<%= onctrlenter %>', onAltEnter: '<%= onaltenter %>', width: width, height: height, format: format, encoding: encoding, container: textareaid+'_webeditor_container' });
	} else if (webeditor_container && (webeditor_content !== false)) {
		WebEditor(textareaid, webeditor_content, { baseHref: baseHref, baseHrefPrefix: baseHrefPrefix, language: 'jsp', manager: 'manager_wcm', onEnter: '<%= onenter %>', onShiftEnter: '<%= onshiftenter %>', onCtrlEnter: '<%= onctrlenter %>', onAltEnter: '<%= onaltenter %>', width: width, height: height, format: format, encoding: encoding, container: textareaid+'_webeditor_container' });
	} else {
		WebEditorOptions(options, true);
	}
}



function WebEditorDisplayReady(tabname) {
	// Is the webeditor ready for the 'Primary Content' tab or another tab to be displayed?
	if ((tabname == 'Primary Content') || (typeof(webeditor) == "undefined") || (webeditor.count == 0) || webeditor.inited) {
		if (typeof(webeditor_dropdown_close_all) != "undefined") webeditor_dropdown_close_all();
		if (typeof(webeditor_menu_hide) != "undefined") webeditor_menu_hide();
		return true;
	} else {
		return false;
	}
}



/*
// This function is already provided by the Asbru Web Content Editor
function WebEditorFocus() {
	// The displayed tab has been changed - "focus"/refresh the webeditor
}
*/



/*
// This function is already provided by the Asbru Web Content Editor
function WebEditorStylesheet(stylesheet) {
	// The selected style sheet(s) for the content has been changed - update the webeditor to use the new selected style sheet(s)
	if (stylesheet == false) {
		// no style sheets
		// set the webeditor to not use any style sheet(s) here
	} else {
		// stylesheet = '/stylesheet.jsp?id=ID,ID,ID'
		// set the webeditor to use the give style sheet(s) here
	}
}
*/



function WebEditorSaveReady(form) {
	// Is the webeditor loaded and inited and ready for the HTML FORM to be posted/saved?
	if ((typeof(webeditor) == "undefined") || (webeditor.count == 0) || webeditor.inited) {
		return true;
	} else {
		return false;
	}
}



/*
// This function is already provided by the Asbru Web Content Editor
function WebEditorSubmit() {
	// The HTML FORM is about to be posted - prepare the webeditor to post the form - ie. copy the content from the webeditor to the HTML FORM TEXTAREA/INPUT
}
*/



/*
// This function is already provided by the Asbru Web Content Editor
function WebEditorPreview() {
	// Preview the edited content from the webeditor
	if (typeof(doPreview) == "function") {
		doPreview(); // Post the HTML FORM to be previewed by the web content management system
	}
}
*/



/*
// This function is already provided by the Asbru Web Content Editor
function WebEditorSave() {
	// Save the edited content from the webeditor
	if (typeof(doSaveAndClose) == "function") {
		doSaveAndClose(false); // Post the HTML FORM to be saved by the web content management system - do not close/leave the editor page and return to the index page
		//doSaveAndClose(true); // Post the HTML FORM to be saved by the web content management system - close/leave the editor page and return to the index page
	}
}
*/



function webeditor_custom_ready() {
	// Asbru Web Content Editor inited and ready function
	// Init tabs after editor for correct height
	mySlideTabs = $('#tabs').slidetabs({ autoHeight: true, externalLinking: true, urlLinking: true, responsive: true });
}



function webeditor_custom_preview() {
	// Asbru Web Content Editor toolbar preview function
	// Preview the edited content from the webeditor
	if (typeof(doPreview) == "function") {
		doPreview(); // Post the HTML FORM to be previewed by the web content management system
	}
}



function webeditor_custom_save() {
	// Asbru Web Content Editor toolbar save function
	// Save the edited content from the webeditor
	if (typeof(doSaveAndClose) == "function") {
		doSaveAndClose(false); // Post the HTML FORM to be saved by the web content management system - do not close/leave the editor page and return to the index page
		//doSaveAndClose(true); // Post the HTML FORM to be saved by the web content management system - close/leave the editor page and return to the index page
	}
}



/*
// These functions are already provided by the Asbru Web Content Editor
// Webeditor call-back functions object for use of the web content management system's Insert Hyperlink and Insert Media pop-up dialogue windows
var webeditor = {};
webeditor.insertHyperlink = function(href, target, text, htmlclass, htmlid, onclick, title) {
	// insert hyperlink into webeditor content
};
webeditor.insertFlash = function(url, alt, width, height, htmlclass, htmlid) {
	// insert Flash into webeditor content
};
webeditor.insertApplet = function(url, alt, width, height, htmlclass, htmlid) {
	// insert Applet into webeditor content
};
webeditor.insertQuicktime = function(url, alt, width, height, htmlclass, htmlid) {
	// insert Quicktime into webeditor content
};
webeditor.insertImage = function(url, border, alt, width, height, vspace, hspace, align, htmlclass, htmlid, mouseover, mouseout, usemap){
	// insert Image into webeditor content
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
*/

</script>
