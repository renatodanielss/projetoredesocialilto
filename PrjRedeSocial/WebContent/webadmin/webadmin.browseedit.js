
var WCMbrowseEditMenus = [];

function WCMbrowseEditMenu(id, contentclass, title, addnew, edit, admin, publish, status) {
	if (! id) return;
	if (WCMbrowseEditMenus[id]) return;
	WCMbrowseEditMenus[id] = true;
	if (addnew) WCMbrowseEditMenuNew("WCMmenuaddnewitems", (addnew ? addnew : "#"), id, contentclass, title, status);
	if (admin) WCMbrowseEditMenuNew("WCMmenuadminitems", (admin ? admin : "#"), id, contentclass, title, status);
	if (edit) WCMbrowseEditMenuNew("WCMmenuedititems", (edit ? edit : "#"), id, contentclass, title, status);
	if (publish) WCMbrowseEditMenuNew("WCMmenupublishitems", (publish ? publish : "#"), id, contentclass, title, status);
}

function WCMbrowseEditMenuNew(menuname, url, id, contentclass, title, status) {
	var mymenu = document.getElementById(menuname);
	if (mymenu) {
		var mynewnode = document.createElement('li');
		mynewnode.className = 'WCMmenuitem_' + contentclass;
		mynewnode.title = '' + title + ' (' + contentclass + ') (' + id + ')';
		mynewnode.innerHTML = '<a href="' + url + '">' + title + ' (' + contentclass + ') (' + id + ')<br>' + status + '</a>';

		var mynode = false;
		for (var i=0; i<mymenu.childNodes.length; i++) {
			mynode = mymenu.childNodes[i];
			if (contentclass == 'page') {
				if ((mynode.nodeValue == 'pages') || (mynode.nodeValue == ' pages ') || (mynode.innerHTML == '<!-- pages -->')) break;
			} else if (contentclass == 'product') {
				if ((mynode.nodeValue == 'pages') || (mynode.nodeValue == ' pages ') || (mynode.innerHTML == '<!-- pages -->')) break;
			} else if (contentclass == 'template') {
				if ((mynode.nodeValue == 'templates') || (mynode.nodeValue == ' templates ') || (mynode.innerHTML == '<!-- templates -->')) break;
			} else if (contentclass == 'stylesheet') {
				if ((mynode.nodeValue == 'stylesheets') || (mynode.nodeValue == ' stylesheets ') || (mynode.innerHTML == '<!-- stylesheets -->')) break;
			} else if (contentclass == 'script') {
				if ((mynode.nodeValue == 'scripts') || (mynode.nodeValue == ' scripts ') || (mynode.innerHTML == '<!-- scripts -->')) break;
			} else if (contentclass == 'image') {
				if ((mynode.nodeValue == 'images') || (mynode.nodeValue == ' images ') || (mynode.innerHTML == '<!-- images -->')) break;
			} else if (contentclass == 'file') {
				if ((mynode.nodeValue == 'files') || (mynode.nodeValue == ' files ') || (mynode.innerHTML == '<!-- files -->')) break;
			} else if (contentclass == 'link') {
				if ((mynode.nodeValue == 'links') || (mynode.nodeValue == ' links ') || (mynode.innerHTML == '<!-- links -->')) break;
			} else {
				if ((mynode.nodeValue == 'elements') || (mynode.nodeValue == ' elements ') || (mynode.innerHTML == '<!-- elements -->')) break;
			}
		}

		var myprevnode = mynode;
		if (! myprevnode.nextSibling) {
			mymenu.appendChild(mynewnode);
		} else if ((myprevnode.nodeValue == 'elements') || (myprevnode.nodeValue == ' elements ') || (myprevnode.innerHTML == '<!-- elements -->')) {
			while (myprevnode.nextSibling && (myprevnode.nextSibling.nodeName != "#comment") && ((myprevnode.nextSibling.className < mynewnode.className) || ((myprevnode.nextSibling.className == mynewnode.className) && (myprevnode.nextSibling.title < mynewnode.title)))) {
				myprevnode = myprevnode.nextSibling;
			}
		} else {
			while (myprevnode.nextSibling && (myprevnode.nextSibling.nodeName != "#comment") && (myprevnode.nextSibling.className == mynewnode.className) && myprevnode.nextSibling.title && (myprevnode.nextSibling.title < mynewnode.title)) {
				myprevnode = myprevnode.nextSibling;
			}
		}

//		if (((contentclass == "page") || (contentclass == "product")) && ((WCMrequestParameter("id") == id) || ((WCMrequestParameter("id") == "") && (document.title == title)))) {
//			mymenu.insertBefore(mynewnode, mymenu.childNodes[0]);
//		} else {
			if (myprevnode.nextSibling) {
				mymenu.insertBefore(mynewnode, myprevnode.nextSibling);
			} else {
				mymenu.appendChild(mynewnode);
			}
//		}
	}
}

function WCMtoggleOutline(mode) {
	if ((mode != 'none') && WCMdoEditAllUnsaved()) return;
	var css = document.getElementById("WCMbrowseeditcss");
	if (css) {
		if (mode) {
			var href = "webadmin.browseedit.css";
			if (mode == "none") href = "webadmin.browseedit.css";
			if (mode == "black") href = "webadmin.browseedit.black.css";
			if (mode == "white") href = "webadmin.browseedit.white.css";
			var myhref = css.href;
			myhref = myhref.replace("webadmin.browseedit.css", href);
			myhref = myhref.replace("webadmin.browseedit.black.css", href);
			myhref = myhref.replace("webadmin.browseedit.white.css", href);
			css.href = myhref;
		} else if (css.href.indexOf("webadmin.browseedit.css") > 0) {
			css.href = css.href.replace("webadmin.browseedit.css", "webadmin.browseedit.black.css");
			mode = "black";
		} else if (css.href.indexOf("webadmin.browseedit.black.css") > 0) {
			css.href = css.href.replace("webadmin.browseedit.black.css", "webadmin.browseedit.white.css");
			mode = "white";
		} else if (css.href.indexOf("webadmin.browseedit.white.css") > 0) {
			css.href = css.href.replace("webadmin.browseedit.white.css", "webadmin.browseedit.css");
			mode = "none";
		}
		var cookiename = "WCMbrowseeditoutline";
		var cookievalue = mode;
		var date = new Date();
		date.setTime(date.getTime()+(7*24*60*60*1000));
		var expires = "; expires="+date.toGMTString();
		document.cookie = "WCMbrowseeditoutline" + "=" + mode + "; expires=" + date.toGMTString() + "; path=/";
	}
}

function WCMtoggleVersion(version) {
	if (WCMdoEditAllUnsaved()) return;
	var myhref = document.location.href;
	myhref = myhref.replace(/(^|[?&])version=[^&]*([&]|$)/, "$1");
	if (myhref.indexOf("?")<0) {
		myhref += "?version=" + version + "&";
	} else {
		myhref += "&version=" + version + "&";
	}
	if (myhref != document.location.href) {
		document.location.href = myhref;
	}
	myhref = myhref.replace("&&", "&");
}

function WCMtogglePreview(datetime, doprompt) {
	if (WCMdoEditAllUnsaved()) return;
	if (doprompt) datetime = prompt(doprompt, datetime);
	if (! datetime) datetime = "";
	var myhref = document.location.href;
	myhref = myhref.replace(/(^|[?&])preview_scheduled=[^&]*([&]|$)/, "$1");
	if (myhref.indexOf("?")<0) {
		myhref += "?preview_scheduled=" + datetime + "&";
	} else {
		myhref += "&preview_scheduled=" + datetime + "&";
	}
	myhref = myhref.replace("&&", "&");
	if (myhref != document.location.href) {
		document.location.href = myhref;
	}
}

function WCMrequestParameter(name) {
	var request = "" + window.location;
	var value = "";
	if (start = request.indexOf("?"+name+"=")+1) {
		value = request.substring(start+name.length+1);
	} else if (start = request.indexOf("&"+name+"=")+1) {
		value = request.substring(start+name.length+1);
	}
	if (value.indexOf("&")+1) {
		value = value.substring(0, value.indexOf("&"));
	}
	value = unescape(decodeURI(value));
	return value;
}

var WCMdoEditAll_enabled = false;
var WCMdoEditAll_content = [];

function WCMdoAdmin(url) {
	if (WCMdoEditAllUnsaved()) return;
	document.location.href = url;
}

function WCMdoAddNew(url) {
	if (WCMdoEditAllUnsaved()) return;
	document.location.href = url;
}

function WCMdoPublish(url) {
	if (WCMdoEditAllUnsaved()) return;
	document.location.href = url;
}

var cookiename = "WCMbrowseeditoutline";
var cookievalue = "none";
var cookies = document.cookie.split(';');
for (var i=0; i<cookies.length; i++) {
	var mycookie = cookies[i];
	while (mycookie.charAt(0)==' ') mycookie = mycookie.substring(1,mycookie.length);
	if (mycookie.indexOf(cookiename+"=") == 0) cookievalue = mycookie.substring((cookiename+"=").length, mycookie.length);
}
WCMtoggleOutline(cookievalue);



/* modal dialogue windows */

function webeditor_window_close(mywindow) {
	return webeditor_custom_window_close(mywindow);
}
function webeditor_custom_window_close(mywindow) {
	if ((typeof(browserIs) != "undefined") && browserIs("Safari") && (browserIs("iPad") || browserIs("iPhone"))) {
		if (mywindow && mywindow.close) mywindow.close();
	} else {
		$.WCMModalClose();
	}
}

function webeditor_window(url, name, width, height, specs, replacehistory) {
	return webeditor_custom_window(url, name, width, height, specs, replacehistory);
}
function webeditor_custom_window(url, name, width, height, specs, replacehistory) {
	if ((typeof(browserIs) != "undefined") && browserIs("Safari") && (browserIs("iPad") || browserIs("iPhone"))) {
		return window.open(url, name, specs, replacehistory);
	} else {
		var myhtml = '<iframe id="' + name + '" name="' + name + '" src="' + url + '" width="' + width + '" height="' + height + '"></iframe>';
		$.WCMModal('<img class="WCMmodalclose" src="/webadmin/close.gif" align="right" onclick="$.WCMModalClose();" /><br>' + myhtml, name, width, parseInt(height)+75);
		return;
	}
}

jQuery.WCMmodals = [];

jQuery.WCMModal = function(sHTML, name, width, height) {
	var myzindex = 10000 + (2 * jQuery.WCMmodals.length);
	var myleft = Math.round(($(window).innerWidth() - width) / 2);
	var mytop = Math.round(($(window).innerHeight() - height) / 2);
	jQuery.WCMmodals.push(name);
	$('<div />').attr('id',name+'_overlay').addClass('WCMmodaloverlay').css('z-index',myzindex).appendTo('body').show();
	$('<div />').attr('id',name+'_modal').addClass('WCMmodal').css('z-index',myzindex+1).appendTo('body').html(sHTML);
	$('#'+name+'_modal')
	.css('position','fixed')
	.css('left',myleft)
	.css('top',mytop);
	$('#'+name+'_modal').fadeIn('normal');
}
 
jQuery.WCMModalClose = function() {
	var name = jQuery.WCMmodals.pop();
	$('#'+name+'_modal').fadeOut('fast', function() {
		$('#'+name+'_modal').remove();
		$('#'+name+'_overlay').remove();
	});
}
