<%@ include file="../config.jsp" %>

$(function(){
	if (typeof(WebEditorInit) != "undefined") {
		WebEditorInit("WCMeditor");
	}
});

function WCMdecodeHTML(html) {
	if (! html) return html;
        document.getElementById("WCMwebeditorinput").innerHTML = '<textarea id="WCMhtmlconverter" name="WCMhtmlconverter" style="display:none;">'+(html.replace(/"/g,'&quot;').replace(/</g,'&lt;').replace(/>/g,'&gt;'))+'</textarea>';
        var output = document.getElementById("WCMhtmlconverter").value;
        document.getElementById("WCMwebeditorinput").innerHTML = "";
        return output;
}

function WCMdoEdit(id) {
	if (WCMdoEditAllUnsaved()) return;
	var content = eval('(' + GET('/<%= mytext.display("adminpath") %>/content/get.jsp?id=' + id + "&" + Math.random()) + ')');
	content.title = WCMdecodeHTML(content.title);
	content.server_filename = WCMdecodeHTML(content.server_filename);

	if (content.publisher) {
		if (document.getElementById("WCMreadytopublishinput")) document.getElementById("WCMreadytopublishinput").checked = false;
		if (document.getElementById("WCMpublishinputs")) document.getElementById("WCMpublishinputs").style.display = "inline";
		if (document.getElementById("WCMreadytopublishinputs")) document.getElementById("WCMreadytopublishinputs").style.display = "none";
	} else {
		if (document.getElementById("WCMpublishinput")) document.getElementById("WCMpublishinput").checked = false;
		if (document.getElementById("WCMpublishinputs")) document.getElementById("WCMpublishinputs").style.display = "none";
		if (document.getElementById("WCMreadytopublishinputs")) document.getElementById("WCMreadytopublishinputs").style.display = "inline";
	}

	if (document.getElementById("WCMidinput")) document.getElementById("WCMidinput").value = id;
	if (document.getElementById("WCMtitleinput")) document.getElementById("WCMtitleinput").value = content.title;
	if (document.getElementById("WCMcontentinput")) document.getElementById("WCMcontentinput").value = content.content;
	if (document.getElementById("WCMserverfilenameinput")) document.getElementById("WCMserverfilenameinput").value = content.server_filename;

	document.getElementById("WCMoverlay").style.display = "inline";
	document.getElementById("WCMwrapper").style.display = "inline";
	document.getElementById("WCMoverlay").style.visibility = "visible";
	document.getElementById("WCMwrapper").style.visibility = "visible";
	document.body.style.overflow = "hidden";
	if (document.documentElement) document.documentElement.style.overflow = "hidden";

	var width = 800;
	var height = 200;
	if (window.innerHeight && window.innerHeight >= 1000) {
		height = 650;
	} else if (window.innerHeight && window.innerHeight >= 900) {
		height = 550;
	} else if (window.innerHeight && window.innerHeight >= 800) {
		height = 450;
	} else if (window.innerHeight && window.innerHeight >= 700) {
		height = 350;
	} else if (window.innerHeight && window.innerHeight >= 600) {
		height = 250;
	} else if (window.innerHeight && window.innerHeight >= 500) {
		height = 150;
	} else if (window.innerHeight && window.innerHeight < 500) {
		height = 100;
	} else if (document.documentElement.clientHeight && document.documentElement.clientHeight >= 1000) {
		height = 650;
	} else if (document.documentElement.clientHeight && document.documentElement.clientHeight >= 900) {
		height = 550;
	} else if (document.documentElement.clientHeight && document.documentElement.clientHeight >= 800) {
		height = 450;
	} else if (document.documentElement.clientHeight && document.documentElement.clientHeight >= 700) {
		height = 350;
	} else if (document.documentElement.clientHeight && document.documentElement.clientHeight >= 600) {
		height = 250;
	} else if (document.documentElement.clientHeight && document.documentElement.clientHeight < 600) {
		height = 200;
	}
	if (! content.width) content.width = width-10;
	if (! content.height) content.height = height;

	if ((content.contentclass == "stylesheet") || (content.contentformat == "text") || ('<%= adminuser.getContentEditor() %>' == 'textarea') || (('<%= adminuser.getContentEditor() %>' == '') && ('<%= myconfig.get(db, "content_editor") %>' == 'textarea'))) {
		document.getElementById('WCMwebeditorinput').innerHTML = '<div id="WCMcontentinput_container"><textarea id="WCMcontentinput" name="WCMcontentinput" cols="80" rows="15" style="width: ' + (width-15) + 'px; height: ' + (height) + 'px;">' + content.content + '</textarea></div>';
	} else {
		var myhtml = '<div id="WCMcontentinput_container" style="width: ' + (content.width) + 'px; height: ' + (content.height) + 'px;"><textarea id="WCMcontentinput" name="WCMcontentinput" cols="80" rows="15" style="width: ' + (width-15) + 'px; height: ' + (height) + 'px;">' + content.content + '</textarea></div><input type="hidden" id="webeditor_stylesheet" name="webeditor_stylesheet" value="' +content.stylesheet + '">';
		if (content.format) {
			myhtml += '<input type="hidden" id="webeditor_format" name="webeditor_format" value="' +content.format + '">';
		} else if (content.webeditor_format) {
			myhtml += '<input type="hidden" id="webeditor_format" name="webeditor_format" value="' +content.webeditor_format + '">';
		}
		document.getElementById('WCMwebeditorinput').innerHTML = myhtml;
		WebEditorInit('WCMcontentinput');
	}
	window.scrollTo(0,0);
}



function webeditor_custom_refresh() {
	if (parseInt($("#WCMeditor_toolbar").css("width")) < parseInt($("#webeditor_toolbar_WCMeditor").width())) {
		$("#WCMeditor_toolbar").css("width", "200px");
		$("#WCMeditor_toolbar").css("width", parseInt($("#webeditor_toolbar_WCMeditor").width())+"px");
	}
}

function WCMdoEditAll(toggle, id) {
	if (! WCMdoEditAll_enabled) {
		WCMtoggleOutline('none');
		if (toggle) {
			WCMdoEditAll_enabled = (! WCMdoEditAll_enabled);
		} else {
			WCMdoEditAll_enabled = false;
		}
		WCMdoEditAll_content = [];
		$(".WCMeditablecontentcontent").each(function(index) {
			WCMdoEditAll_content[$(this).attr("id")] = $(this).html();
		});
		$(".WCMeditablecontentcontent").attr("contentEditable", true);
		$(".WCMeditablecontentcontent").each(function(i,node) {
			if (typeof(WebEditorInit) != "undefined") {
				WebEditorInit(node);
			}
		});
		$(".WCMeditablecontentcontent").parent().addClass("WCMeditor_content");
		$(".WCMeditablecontentcontent").parent().click(function(evt) {
			$(".WCMeditor_content_editing").removeClass("WCMeditor_content_editing");
			var mytarget = evt.target;
			while (mytarget && ((! mytarget.id) || (! $(mytarget).hasClass("WCMeditablecontentcontent")))) {
				mytarget = mytarget.parentNode;
			}
			if (mytarget) {
				$(mytarget).parent().addClass("WCMeditor_content_editing");
			} else {
				$(this).parent().addClass("WCMeditor_content_editing");
			}
			//evt.stopPropagation();
			return false;
		});
		$("#WCMeditor_toolbarcontainer").css("display", "inline");

		// MSIE force resize toolbar div or width may be 100% with Save/Cancel below instead of to the right
		$("#WCMeditor_toolbar").css("width", "200px");
		$("#WCMeditor_toolbar").css("width", parseInt($("#webeditor_toolbar_WCMeditor").width())+"px");

		$("#WCMpagecontainer").css("margin-top", ($("#WCMmenucontainer").height() + $("#WCMeditor_toolbarcontainer").height()) + "px");
		$("#WCMpagecontainer").click(function(evt) {
			//evt.stopPropagation();
			return false;
		});
		$(".WCMbrowseEditHeader").click(function(evt) {
			if (evt && evt.target && evt.target.id && (evt.target.id == "WCMhelp")) {
				return true;
			} else if (WCMdoEditAllUnsaved()) {
				return false;
			}
		});
		if (id) {
			var content = eval('(' + GET('/<%= mytext.display("adminpath") %>/content/get.jsp?id=' + id + "&" + Math.random()) + ')');
			if (content && content.stylesheet) {
				WebEditorStylesheet(content.stylesheet);
			}
		}
	} else if (! WCMdoEditAllUnsaved()) {
		WCMdoEditAllDone();
	}
}



function WCMdoEditAllUnsaved() {
	if (WCMdoEditAll_enabled) {
		var unsaved = false;
		$(".WCMeditablecontentcontent").each(function(index) {
			if (WCMdoEditAll_content[$(this).attr("id")] != $(this).html()) {
				unsaved = true;
			}
		});
		if (! unsaved) {
			return false;
		} else if (confirm('<%= mytext.display("browseedit.discard") %>')) {
			WCMdoEditAllDone();
			return false;
		} else {
			return true;
		}
	} else {
		return false;
	}
}



function WCMdoEditAllDone() {
	WCMdoEditAll_enabled = false;
	WCMdoEditAll_content = [];
	$("#WCMeditor_toolbarcontainer").css("display", "none");
	$(".WCMeditablecontentcontent").off("click");
	$(".WCMeditablecontentcontent").removeClass("WCMeditor_content_editing");
	$(".WCMeditablecontentcontent").removeClass("WCMeditor_content");
	$(".WCMeditablecontentcontent").attr("contentEditable", false);
	$("#WCMpagecontainer").css("margin-top", "");
	$("#WCMpagecontainer").off("click");
	$(".WCMbrowseEditHeader").off("click");
	if (typeof(WebEditorInit) != "undefined") {
		WebEditorInit(false);
	}
	window.location.reload(true);
}



function WCMdoSaveAll() {
	if (WCMdoEditAll_enabled) {
		$.WCMModal('<img class="WCMmodalclose" src="/webadmin/close.gif" align="right" onclick="$.WCMModalClose();" /><br>' + '<table style="width:300px; height:200px"><tr><td style="text-align:center; vertical-align:middle;" id="WCMsaving"></td></tr></table>', "WCMmodalsaving", 300, 200+75);
		document.getElementById("WCMsaving").innerHTML = '<%= mytext.display("browseedit.saving") %>';
		setTimeout("WCMdoSaveAllSub()", 1000);
	}
}
function WCMdoSaveAllSub() {
	var errors = "";
	$(".WCMeditablecontentcontent").each(function(index) {
		if (WCMdoEditAll_content[$(this).attr("id")] != $(this).html()) {
			unsaved = true;
			if ($(this).attr("id").match(/^WCMeditable_content_([0-9]+)$/)) {
				var id = $(this).attr("id").replace(/^WCMeditable_content_([0-9]+)$/, "$1");
				var title = false;
				var content = $(this).html();
				var publish = "";
				if ($("#WCMpublishinputall").is(':checked')) {
					publish = $("#WCMpublishinputall").attr("value");
				}
				var ready_to_publish = "";
				if ($("#WCMreadytopublishinputall").is(':checked')) {
					ready_to_publish = $("#WCMreadytopublishinputall").attr("value");
				}
				var error = WCMdoSavePOST(id, title, content, publish, ready_to_publish);
				if (error && (error != "OK")) errors += error + "\r\n\r\n";
			}
		}
	});
	if (errors) {
		document.getElementById("WCMsaving").innerHTML = '<%= mytext.display("browseedit.errors") %>' + errors;
	} else {
		document.getElementById("WCMsaving").innerHTML = '<%= mytext.display("browseedit.saved") %>';
		WCMdoEditAllDone();
	}
}



function WCMdoCancelAll() {
	if (! WCMdoEditAllUnsaved()) {
		WCMdoEditAllDone();
	}
}



function webeditor_custom_save() {
	WCMdoSave();
}
function WCMdoSave() {
	if (! document) return;
	if (! document.getElementById) return;
	var id = "";
	var title = "";
	var content = "";
	var publish = "";
	var ready_to_publish = "";
	if (document.getElementById("WCMidinput")) id = document.getElementById("WCMidinput").value;
	if (document.getElementById("WCMtitleinput")) title = document.getElementById("WCMtitleinput").value;
	if (document.getElementById("WCMcontentinput") && document.getElementById("WCMcontentinput").value) {
		content = document.getElementById("WCMcontentinput").value;
	} else {
		content = WebEditorGetContent("WCMcontentinput");
	}
	if (document.getElementById("WCMpublishinput") && document.getElementById("WCMpublishinput").value && (document.getElementById("WCMpublishinput").checked || (document.getElementById("WCMpublishinput").type == "hidden"))) {
		publish = 'Save & Publish';
	}
	if (document.getElementById("WCMreadytopublishinput") && document.getElementById("WCMreadytopublishinput").value && (document.getElementById("WCMreadytopublishinput").checked || (document.getElementById("WCMreadytopublishinput").type == "hidden"))) {
		ready_to_publish = 'Ready To Publish';
	}

	var error = WCMdoSavePOST(id, title, content, publish, ready_to_publish);

	if (error == "OK") {
		document.body.style.overflow = "auto";
		if (document.documentElement) document.documentElement.style.overflow = "auto";
		document.getElementById("WCMoverlay").style.visibility = "hidden";
		document.getElementById("WCMwrapper").style.visibility = "hidden";
		document.getElementById("WCMoverlay").style.display = "none";
		document.getElementById("WCMwrapper").style.display = "none";
//		window.location.reload(true);
		if (window.location.search && (window.location.search != "?")) {
			var href = "" + window.location.href;
			href = href.replace("#$", "");
			href = href.replace(/[?&][0-9]*$/g, "");
			href = href.replace(/[?&]mode=admin$/g, "");
			href = href.replace(/[?]mode=admin[&]/g, "?");
			href = href.replace(/[&]mode=admin[&]/g, "&");
			if (href.indexOf("?")<0) {
				href = href + "?mode=admin&" + (Math.floor(Math.random()*10000000000)+1000000000);
			} else {
				href = href + "&mode=admin&" + (Math.floor(Math.random()*10000000000)+1000000000);
			}
			window.location.href = href;
		} else {
			var href = "" + window.location.href;
			href = href.replace("#$", "");
			href = href.replace("\?$", "");
			href = href + "?mode=admin&" + (Math.floor(Math.random()*10000000000)+1000000000);
			window.location.href = href;
		}
	} else if (error != "") {
		alert(error);
	}
}



function WCMdoSavePOST(id, title, content, publish, ready_to_publish) {
        var do_save = true;
        var validation = "";
<% if (Common.fileExists(Common.getRealPath(servletcontext, "/" + mytext.display("adminpath") + "/api/validatecontent.jsp"))) { %>
	validation = POST('/<%= mytext.display("adminpath") %>/api/validatecontent.jsp?id=' + id, { title: title, content: content, publish: publish, ready_to_publish: ready_to_publish, mode: 'admin' });
        if ((! validation) || (validation == "OK")) {
                // ok
        } else if (validation.match(/^OK:ALERT:/)) {
                alert(validation.replace(/^OK:ALERT:/, ""));
        } else if (validation.match(/^ERROR:ALERT:/)) {
                do_save = false;
                alert(validation.replace(/^ERROR:ALERT:/, ""));
        } else if (validation.match(/^ERROR:CONFIRM:/)) {
                do_save = confirm(validation.replace(/^ERROR:CONFIRM:/, ""));
        } else {
                do_save = confirm(validation);
        }
<% } %>
        if (do_save) {
		validation = POST('/<%= mytext.display("adminpath") %>/content/checklinks.jsp?id=' + id, { title: title, content: content, publish: publish, ready_to_publish: ready_to_publish, mode: 'admin' });
                if ((! validation) || (validation == "OK")) {
                        // ok
                } else if (validation.match(/^OK:ALERT:/)) {
                        alert(validation.replace(/^OK:ALERT:/, ""));
                } else if (validation.match(/^ERROR:ALERT:/)) {
                        do_save = false;
                        alert(validation.replace(/^ERROR:ALERT:/, ""));
                } else if (validation.match(/^ERROR:CONFIRM:/)) {
                        do_save = confirm(validation.replace(/^ERROR:CONFIRM:/, ""));
                } else {
                        do_save = confirm(validation);
                }
        }
	if (! do_save) return;

	var error = "";
	if (title == false) {
		error = POST('/<%= mytext.display("adminpath") %>/content/post.jsp?id=' + id, { content: content, publish: publish, ready_to_publish: ready_to_publish, mode: 'admin' });
	} else {
		error = POST('/<%= mytext.display("adminpath") %>/content/post.jsp?id=' + id, { title: title, content: content, publish: publish, ready_to_publish: ready_to_publish, mode: 'admin' });
	}
	return error;
}



function WCMdoCancel() {
	document.body.style.overflow = "auto";
	if (document.documentElement) document.documentElement.style.overflow = "auto";
	document.getElementById("WCMoverlay").style.visibility = "hidden";
	document.getElementById("WCMwrapper").style.visibility = "hidden";
	document.getElementById("WCMoverlay").style.display = "none";
	document.getElementById("WCMwrapper").style.display = "none";
}
