// Asbru Web Content Editor
// (C) 2002-2014 Asbru Ltd.
// www.asbrusoft.com

var webeditor_rootpath = "/webeditor/";
// auto-configure webeditor_rootpath
webeditor_rootpath = "" + document.location.protocol + "//" + document.location.host + document.location.pathname;
//webeditor_rootpath = "" + document.location.pathname;
webeditor_rootpath = "" + webeditor_rootpath.substring(0, webeditor_rootpath.lastIndexOf("/")+1);
var scripts = document.getElementsByTagName("script");
for (var i=0; i<scripts.length; i++) {
	if (scripts[i].src && scripts[i].src.match(/\/webeditor\.textareas\.js(\?.*)?$/)) {
		webeditor_rootpath = scripts[i].src.replace(/webeditor\.textareas\.js(\?.*)?$/,'').replace(/^(http|https):\/\/[^\/]+/, "");
	}
}
if(webeditor_rootpath.substring(0,3) == "../") {
	var mybase = document.location.pathname.substring(0,document.location.pathname.lastIndexOf('/')+1);
	while(webeditor_rootpath.substring(0,3) == "../") {
		webeditor_rootpath = webeditor_rootpath.substring(3);
		mybase = mybase.substring(0,mybase.length-1);
		mybase = mybase.substring(0,mybase.lastIndexOf('/')+1);
	}
	webeditor_rootpath = mybase+webeditor_rootpath;
}
if (typeof(webeditor_stylesheet)=='undefined') webeditor_stylesheet = webeditor_rootpath + "default.css";
document.write('<script type="text/javascript" src="' + webeditor_rootpath + 'webeditor.js"></script>');

function webeditor_textareas() {
	var textareas = document.getElementsByTagName("TEXTAREA");
	if (textareas) {
		for (var i=0; i<textareas.length; i++) {
			var name = '' + textareas[i].name;
			var value = '' + textareas[i].value;
			var width = '' + textareas[i].clientWidth;
			var height = '' + textareas[i].clientHeight;

			var div = document.createElement("div");
			div.style.border = "1px solid";
			div.style.minWidth = width+"px";
			div.style.minHeight = height+"px";
			div.innerHTML = '<div id="'+name+'_toolbar_container" class="webeditor_toolbar_container"></div><div id="'+name+'_webeditor_container" class="webeditor_container"></div><div id="'+name+'_DOMInspector_container" class="webeditor_DOMInspector_container"></div>';
			textareas[i].parentNode.replaceChild(div, textareas[i]);

			WebEditorToolbar(name, { container: name+'_toolbar_container' } );
			WebEditor(name, value, {container: name+'_webeditor_container', stylesheet: webeditor_stylesheet, format: 'html', width: width, height: height});
			WebEditorDOMInspector(name, name+'_DOMInspector_container');
		}
	}
}

if (window.addEventListener){
	window.addEventListener("load", webeditor_textareas, true);
} else if (window.attachEvent){
	window.attachEvent("on"+"load", webeditor_textareas);
} else {
	window.onload = webeditor_textareas;
}
