
var myLayout;
if (typeof(layoutState) != "undefined") {
	layoutState.options.path = "/";
}
var mySlideTabs;
$(document).ready(function() {
	if (typeof(layoutState) != "undefined") {
		var myLayoutState = layoutState.load('myLayout');
		if (! myLayoutState['north__size']) myLayoutState['north__size'] = 155;
		myLayout = $('body').layout( $.extend( { fxName: "slide", fxSpeed: "slow", slideTrigger_open: "mouseenter", slideTrigger_close: "mouseleave", spacing_open: 8, spacing_closed: 16, closable: true, resizable: true, slidable: true, east__initClosed: true }, myLayoutState ) );
		myLayout.hide("east");
	}
	focusForm();
	if (typeof(initLiveGrid) == 'function') initLiveGrid();
	if (typeof(getmenus) == 'function') getmenus();
	if (typeof(initOptions) == 'function') initOptions();
	if (typeof(initMultiFileUpload) == 'function') initMultiFileUpload();
	if (typeof(getPersonalWorkspace) == 'function') getPersonalWorkspace();
});
if (typeof(layoutState) != "undefined") {
	$(window).unload(function(){ layoutState.save('myLayout') });
}



function focusForm() {
	if (($('#tabs').length) && ($('#tabs').slidetabs)) return;
	save_window_onerror = window.onerror;
	window.onerror = function (msg, url, line) { return true; }
	keep_looking = 1;
	form = 0;
	while (keep_looking && (form < document.forms.length)) {
		element = 0;
		while (keep_looking && (element < document.forms[form].elements.length)) {
			if ((document.forms[form].elements[element].type == "text") && ((document.forms[form].elements[element].name != "search") || document.location.pathname.indexOf("/index.aspx") || document.location.pathname.indexOf("/index.jsp") || document.location.pathname.indexOf("/index.php"))) {
				try {
					document.forms[form].elements[element].focus();
					keep_looking = 0;
				}
				catch(oException) {
					keep_looking = 1;
				}
			}
			element++;
		}
		form ++;
	}
	if (! keep_looking) document.forms[--form].elements[--element].focus();
	window.onerror = save_window_onerror;
}



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
		$.WCMModal('<img class="WCMmodalclose" src="/webadmin/close.gif" align="right" onclick="$.WCMModalClose();" /><br>' + myhtml, name, width, height+75);
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
