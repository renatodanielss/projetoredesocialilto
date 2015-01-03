///////////////////////////////////////////////////////////////////////////////////////////////////
// Asbru Web Content Editor
// (C) 2002-2014 Asbru Ltd.
// www.asbrusoft.com
///////////////////////////////////////////////////////////////////////////////////////////////////

var webeditor;
webeditor.count = 0;
webeditor.inited = 0;

webeditor.window_proxy = {};

webeditor.window_close = function(mywindow) {
	mywindow.close();
}

webeditor.window_open = function(url, name, width, height, specs, replacehistory) {
	return window.open(url, name, specs, replacehistory);
}

///////////////////////////////////////////////////////////////////////////////////////////////////
// Main function/object called from web page at location of the web editor content
///////////////////////////////////////////////////////////////////////////////////////////////////

function WebEditor(name, value, options) {
	// options: stylesheet language manager onEnter onShiftEnter onCtrlEnter onAltEnter toolbar width height format encoding direction
	// set options for global webeditor attributes
	for (var attr in options) {
		if (typeof(webeditor[attr])!='undefined') webeditor[attr] = options[attr];
	}
	if (typeof(options.stylesheet)!='undefined') {
		if (options.stylesheet.match("^https?://.*$")) {
			// ok - absolute url
		} else if (options.stylesheet.match("^/.*$")) {
			// ok - absolute path
		} else {
			options.stylesheet = document.location.pathname.substring(0, document.location.pathname.lastIndexOf("/")+1) + options.stylesheet;
		}
	}
	if (typeof(options.language)=='undefined') {
		if (document.location.pathname.match(".*\.asp$")) {
			options.language = 'asp';
		} else if (document.location.pathname.match(".*\.aspx$")) {
			options.language = 'aspx';
		} else if (document.location.pathname.match(".*\.cfm$")) {
			options.language = 'cfm';
		} else if (document.location.pathname.match(".*\.jsp$")) {
			options.language = 'jsp';
		} else if (document.location.pathname.match(".*\.php$")) {
			options.language = 'php';
		}
	}
	if ((typeof(options.manager)=='undefined') && (typeof(options.language)!='undefined') && (options.language != 'html')) {
		options.manager = 'manager';
	}
	return HardCoreWebEditor(options.rootpath, options.language, name, value, options.value_htmlencoded, options.stylesheet, options.showhtml, options.manager, options.onEnter, options.onShiftEnter, options.onCtrlEnter, options.onAltEnter, options.toolbar, options.width, options.height, options.format, options.encoding, options.direction);
}
function HardCoreWebEditor(rootpath, language, name, value, value_htmlencoded, stylesheet, showhtml, manager, onEnter, onShiftEnter, onCtrlEnter, onAltEnter, toolbar, width, height, format, encoding, direction) {
	webeditor.width = width || "100%";
	webeditor.height = height || "100%";
	document.write(Text('webbrowser_unsupported_textarea'));
//	document.write('<textarea id="'+name+'_textarea" name="'+name+'" cols="80" rows="25">'+unescape(value)+'</textarea>');
//	document.write('<textarea id="'+name+'_textarea" name="'+name+'" style="width: ' + webeditor.width + '; height: ' + webeditor.height + ';">'+unescape(value)+'</textarea>');
	document.write('<textarea id="'+name+'_textarea" name="'+name+'" cols="80" rows="25" style="width: ' + webeditor.width + '; height: ' + webeditor.height + ';"></textarea>');
	if (document.getElementById(name+'_textarea')) {
		document.getElementById(name+'_textarea').value = unescape(value);
	}
	webeditor.inited += 1;
}

function HardCoreWebEditorFocus() {
	return WebEditorFocus();
}
function WebEditorFocus() {
}
function WebEditorActivate() {
}

function HardCoreWebEditorDOMInspector(name) {
	return WebEditorDOMInspector(name);
}
function WebEditorDOMInspector(name) {
}

function WebEditorSkin(name) {
}

function HardCoreWebEditorToolbar(name, options) {
	return WebEditorToolbar(name, options);
}
function WebEditorToolbar(name, options) {
}

function HardCoreWebEditorStylesheet(stylesheet) {
	return WebEditorStylesheet(stylesheet);
}
function WebEditorStylesheet(stylesheet) {
}

function HardCoreWebEditorSubmit() {
	return WebEditorSubmit();
}
function WebEditorSubmit() {
}

function WebEditorPreview(id) {
	contenteditable_preview(id);
}

function HardCoreWebEditorGetContent(id) {
	return WebEditorGetContent(id);
}
function WebEditorGetContent(id) {
}

function HardCoreWebEditorGetContentSelection(id) {
	return WebEditorGetContentSelection(id);
}
function WebEditorGetContentSelection(id) {
}

function HardCoreWebEditorGetContentEdited(id) {
	return WebEditorGetContentEdited(id);
}
function WebEditorGetContentEdited(id) {
}

function HardCoreWebEditorSetContent(content, id) {
	return WebEditorSetContent(content, id);
}
function WebEditorSetContent(content, id) {
}

function HardCoreWebEditorPasteContent(content, id) {
	return WebEditorPasteContent(content, id);
}
function WebEditorPasteContent(content, id) {
}

function HardCoreWebEditorCleanContent(all_html, all_xml, all_namespace, all_lang, all_class, all_style, empty_span, all_span, empty_font, all_font, all_del_ins, empty_p_div) {
	return WebEditorCleanContent(all_html, all_xml, all_namespace, all_lang, all_class, all_style, empty_span, all_span, empty_font, all_font, all_del_ins, empty_p_div, all_format_tags, mso_class, mso_style);
}
function WebEditorCleanContent(all_html, all_xml, all_namespace, all_lang, all_class, all_style, empty_span, all_span, empty_font, all_font, all_del_ins, empty_p_div, all_format_tags, mso_class, mso_style) {
}

function HardCoreWebEditorCleanContentString(content, all_html, all_xml, all_namespace, all_lang, all_class, all_style, empty_span, all_span, empty_font, all_font, all_del_ins, empty_p_div) {
	return WebEditorCleanContentString(content, all_html, all_xml, all_namespace, all_lang, all_class, all_style, empty_span, all_span, empty_font, all_font, all_del_ins, empty_p_div, all_format_tags, mso_class, mso_style);
}
function WebEditorCleanContentString(content, all_html, all_xml, all_namespace, all_lang, all_class, all_style, empty_span, all_span, empty_font, all_font, all_del_ins, empty_p_div, all_format_tags, mso_class, mso_style) {
	return content;
}

