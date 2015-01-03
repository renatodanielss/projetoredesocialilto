
var mySlideTabs;

function changeWorkflowStatus(workflowid) {
	var mycheckedout = document.getElementById("checkedout");
	if (mycheckedout) {
		//var usernameoptions = POST("/<%= mytext.display("adminpath") %>/content/workflow_username_options.jsp?id=<%= html.encodeHtmlEntities(myrequest.getParameter("id")) %>&workflow=" + workflowid + "&<%= Math.random() %>", document.contentform);
		var mycontentclass = document.getElementById("contentclass") ? document.getElementById("contentclass").value : "";
		var mycontentgroup = document.getElementById("contentgroup") ? document.getElementById("contentgroup").value : "";
		var mycontenttype = document.getElementById("contenttype") ? document.getElementById("contenttype").value : "";
		var myversion = document.getElementById("version") ? document.getElementById("version").value : "";
		var usernameoptions = GET("/<%= mytext.display("adminpath") %>/content/workflow_username_options.jsp?id=<%= html.encodeHtmlEntities(myrequest.getParameter("id")) %>&contentclass=" + encodeURI(mycontentclass) + "&contentgroup=" + encodeURI(mycontentgroup) + "&contenttype=" + encodeURI(mycontenttype) + "&version=" + encodeURI(myversion) + "&workflow=" + workflowid + "&<%= Math.random() %>");
		var myselectedindex = mycheckedout.selectedIndex; 
		var myselectedvalue = mycheckedout.value; 
		mycheckedout.options.length = 7;
		var myselect = "" + mycheckedout.outerHTML;
		myselect = myselect.substring(0, myselect.lastIndexOf("<")) + usernameoptions + "</select>";
		mycheckedout.innerHTML += usernameoptions;
		mycheckedout.value = myselectedvalue;	
		// Microsoft Internet Explorer may not correctly update the select options
		if (document.all) mycheckedout.outerHTML = "" + myselect;
	}
}

function changeUsers(user) {
	var users = "";
	for (var i=0; i<user.options.length; i++) {
		if (user.options[i].selected && user.options[i].value) {
			if (users != "") users += ",";
			users += user.options[i].value;
		}
	}
	users = users.replace(/^-/, "");
	document.getElementById(user.id+"input").value = users;
}

function changeScripts(script) {
	var scripts = "";
	for (var i=0; i<script.options.length; i++) {
		if (script.options[i].selected && script.options[i].value) {
			if (scripts != "") scripts += ",";
			scripts += script.options[i].value;
		}
	}
	scripts = scripts.replace(/^-/, "");
	document.getElementById("scriptsinput").value = scripts;
}

function changeRelated(content) {
	var related = "";
	for (var i=0; i<content.options.length; i++) {
		if (content.options[i].selected && content.options[i].value) {
			if (related != "") related += ",";
			related += content.options[i].value;
		}
	}
	related = related.replace(/^-/, "");
	document.getElementById("relatedinput").value = related;
}

var template_stylesheets = "0";
function changeWebEditorTemplate(template, stylesheet) {
	if (! template) return;
	var mytemplate = template.value;
	var mycontentclass = "";
	var contentclass = document.getElementById("contentclass");
	if (contentclass) mycontentclass = contentclass.value;
	var mycontentgroup = "";
	var contentgroup = document.getElementById("contentgroup");
	if (contentgroup) mycontentgroup = contentgroup.value;
	var mycontenttype = "";
	var contenttype = document.getElementById("contenttype");
	if (contenttype) mycontenttype = contenttype.value;
	template_stylesheets = GET("/<%= mytext.display("adminpath") %>/content/stylesheets.jsp?id=" + encodeURI(mytemplate) + "&contentclass=" + encodeURI(mycontentclass) + "&contentgroup=" + encodeURI(mycontentgroup) + "&contenttype=" + encodeURI(mycontenttype) + "&" + Math.random());
	if (stylesheet) changeWebEditorStylesheet(stylesheet);
}

function changeWebEditorStylesheet(stylesheet) {
	var template = document.getElementById("template");
	if (template_stylesheets == "0") {
		changeWebEditorTemplate(template, false);
	}
	var stylesheets = "";
	for (var i=0; i<stylesheet.options.length; i++) {
		if (stylesheet.options[i].selected) {
			if (stylesheets != "") stylesheets += ",";
			stylesheets += stylesheet.options[i].value || "-";
		}
	}
	stylesheets = stylesheets.replace(/^-/, "");
	document.getElementById("stylesheetinput").value = stylesheets;
	if (stylesheets == '') {
		var mycontentclass = "";
		var contentclass = document.getElementById("contentclass");
		if (contentclass) mycontentclass = contentclass.value;
		var mycontentgroup = "";
		var contentgroup = document.getElementById("contentgroup");
		if (contentgroup) mycontentgroup = contentgroup.value;
		var mycontenttype = "";
		var contenttype = document.getElementById("contenttype");
		if (contenttype) mycontenttype = contenttype.value;
		stylesheets = GET("/<%= mytext.display("adminpath") %>/content/stylesheets.jsp?contentclass=" + encodeURI(mycontentclass) + "&contentgroup=" + encodeURI(mycontentgroup) + "&contenttype=" + encodeURI(mycontenttype) + "&" + Math.random());
	}
	if (stylesheets == '') {
		stylesheets = '<%=  myconfig.get(db, "default_stylesheet") %>';
	}
	if ((stylesheets == '0') && (template_stylesheets != '')) {
		WebEditorStylesheet('/stylesheet.jsp?id='+template_stylesheets);
	} else if (stylesheets == '0') {
		WebEditorStylesheet(false);
	} else if ((stylesheets != '') && (template_stylesheets != '')) {
		WebEditorStylesheet('/stylesheet.jsp?id='+template_stylesheets+','+stylesheets);
	} else if (stylesheets != '') {
		WebEditorStylesheet('/stylesheet.jsp?id='+stylesheets);
	} else if (template_stylesheets != '') {
		WebEditorStylesheet('/stylesheet.jsp?id='+template_stylesheets);
	} else {
		WebEditorStylesheet(false);
	}
}

// MSIE document.getElementById may be broken also returning elements by NAME instead of just by ID
if (/msie/i.test (navigator.userAgent)) {
	document.nativeGetElementById = document.getElementById;
	document.getElementById = function(id) {
		var elem = document.nativeGetElementById(id);
		if (elem) {
			if (elem.id == id) {
				return elem;
			} else {
				for (var i=1; i<document.all[id].length; i++) {
					if (document.all[id][i].id == id) {
						return document.all[id][i];
					}
				}
			}
		}
		return null;
	};
}

function openSelectFile(inputnode, contentclass, callback) {
	if (! callback) callback = "selectFile";
	if (! contentclass) contentclass = "";
	var filename = inputnode.value;
	filename = encodeURIComponent(filename);
	if (inputnode) {
		if (! webeditor.window_close) {
			webeditor.window_close = function(mywindow) {
				if (webeditor_custom_window_close) {
					try {
						webeditor_custom_window_close(mywindow);
					} catch(e) {
					}
				} else {
					mywindow.close();
				}
			}
		}
		if (! webeditor.window_open) {
			webeditor.window_open = function(url, name, width, height, specs, replacehistory) {
				if (webeditor_custom_window) {
					try {
						return webeditor_custom_window(url, name, width, height, specs, replacehistory);
					} catch(e) {
					}
				} else {
					return window.open(url, name, specs, replacehistory);
				}
			}
		}
		if (! webeditor.window_proxy) webeditor.window_proxy = {};
		webeditor.window_proxy['fileselector'] = webeditor;
		webeditor_window('/<%= mytext.display("adminpath") %>/webeditor/fileselectormanager.jsp?editor=fileselector&callback=' + callback + '&contentclass=' + contentclass + '&file=' + filename, 'selector_window', 750, 450, 'scrollbars=yes,width=750,height=450,resizable=yes,status=yes', true);
	}
}

function selectFile(filename) {
	var inputnode = document.getElementById("server_filename");
	if (inputnode) inputnode.value = filename.replace(/^\//,"");
}

function selectedInputFile(files) {
	if (typeof(FileReader) == "undefined") return false;
	if (! files) return false;
	if (files.length == 0) return false;
	if (files.length > 1) {
		// 2 OR MORE SELECTED FILES - ONLY 1 WILL BE USED;
	}
	var mycontentclass = document.getElementById("contentclass") ? document.getElementById("contentclass").value : "";
	if (mycontentclass == "image") {
		var file = false;
		var fileformats = '<%= HardCore.Image.filenameextension_list(db) %>';
		for (var i=0; i<files.length; i++) {
			var extension = files[i].name.replace(/^.+\.([^.]+)$/, "$1");
			if ((files[i].type.match(/^image\//)) && (fileformats.match(new RegExp("(^|,)" + extension + "(,|$)", "gi"))) && (files[i].size > 0)) {
				file = files[i];
				break;
			}
		}
		if (file) {
			var old_format = document.getElementById("server_filename").value.replace(/\?.*$/, "");
			if (old_format.match(/^.*\.([^.]+)$/)) {
				old_format = old_format.replace(/^.*\.([^.]+)$/, "$1");
			} else {
				old_format = "png";
			}
			var new_format = file.type.replace(/^image\//, "").replace(/^jpeg$/, "jpg");
			var readFile = function(file) {
				var reader = new FileReader();
				reader.onload = function(evt) {
					var imagedata = evt.target.result;
					if (imagedata != "") {
						var old_filename = "";
						if (document.getElementById("contentfile")) {
							old_filename = document.getElementById("contentfile").innerHTML.replace("&quot;", '"').replace("&gt;", ">").replace("&lt;", "<").replace("&amp;", "&");
							document.getElementById("contentfile").innerHTML = file.name.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace('"',"&quot;");
						}
						if (document.getElementById("contentimage")) {
							document.getElementById("contentimage").onload = function() {
								setTimeout(function() {
									if (mySlideTabs) mySlideTabs.tabs_resize(); 
								}, 1000);
							};
							document.getElementById("contentimage").src = imagedata;
						}
						document.getElementById("file_data").value = imagedata;
						if (document.getElementById("server_filename").value == "") {
							document.getElementById("server_filename").value = "image/" + file.name;
						} else if (document.getElementById("server_filename").value == "image/" + old_filename) {
							document.getElementById("server_filename").value = "image/" + file.name;
						} else if (old_format != new_format) {
							document.getElementById("server_filename").value = document.getElementById("server_filename").value.replace(new RegExp("\." + old_format + "$"), "." + new_format);
						}
						if (document.getElementById("file_input")) {
							document.getElementById("file_input").scrollIntoView();
							// MSIE 10 may scroll tab content up/left after file browse/drag+drop
							var node = document.getElementById("file_input");
							while (node) {
								if (node.scrollTop > 0) {
									node.scrollTop = 0;
								}
								if (node.scrollLeft > 0) {
									node.scrollLeft = 0;
								}
								node = node.parentNode;
							}
						}
					}
					if (mySlideTabs) mySlideTabs.tabs_resize();
				};
				reader.readAsDataURL(file);
			}
			readFile(file);
		} else {
			alert('<%= mytext.display("error.content.upload.format") %>' + "\r\n" + files[0].name + "\r\n\r\n" + '<%= mytext.display("error.content.upload.formats") %>' + "\r\n" + fileformats);
		}
	} else if (mycontentclass == "file") {
		var file = false;
		var fileformats = '<%= HardCore.File.filenameextension_list(db) %>';
		for (var i=0; i<files.length; i++) {
			var extension = files[i].name.replace(/^.+\.([^.]+)$/, "$1");
			if ((fileformats.match(new RegExp("(^|,)" + extension + "(,|$)", "gi"))) && (files[i].size > 0)) {
				file = files[i];
				break;
			}
		}
		if (file) {
			var old_format = document.getElementById("server_filename").value.replace(/\?.*$/, "");
			if (old_format.match(/^.*\.([^.]+)$/)) {
				old_format = old_format.replace(/^.*\.([^.]+)$/, "$1");
			} else {
				old_format = "";
			}
			var new_format = file.name.replace(/^.+\.([^.]+)$/, "$1");
			var readFile = function(file) {
				var reader = new FileReader();
				reader.onload = function(evt) {
					var filedata = evt.target.result;
					if (filedata != "") {
						var old_filename = "";
						if (document.getElementById("contentfile")) {
							old_filename = document.getElementById("contentfile").innerHTML.replace("&quot;", '"').replace("&gt;", ">").replace("&lt;", "<").replace("&amp;", "&");
							document.getElementById("contentfile").innerHTML = file.name.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace('"',"&quot;");
						}
						if (document.getElementById("contentfile")) {
							document.getElementById("contentfile").href = filedata;
						}
						document.getElementById("file_data").value = filedata;
						if (document.getElementById("server_filename").value == "") {
							document.getElementById("server_filename").value = "file/" + file.name;
						} else if (document.getElementById("server_filename").value == "file/" + old_filename) {
							document.getElementById("server_filename").value = "file/" + file.name;
						} else if (old_format != new_format) {
							document.getElementById("server_filename").value = document.getElementById("server_filename").value.replace(new RegExp("\." + old_format + "$"), "." + new_format);
						}
						if (document.getElementById("file_input")) {
							document.getElementById("file_input").scrollIntoView();
							// MSIE 10 may scroll tab content up/left after file browse/drag+drop
							var node = document.getElementById("file_input");
							while (node) {
								if (node.scrollTop > 0) {
									node.scrollTop = 0;
								}
								if (node.scrollLeft > 0) {
									node.scrollLeft = 0;
								}
								node = node.parentNode;
							}
						}
					}
					if (mySlideTabs) mySlideTabs.tabs_resize();
				};
				reader.readAsDataURL(file);
			}
			readFile(file);
		} else {
			alert('<%= mytext.display("error.content.upload.format") %>' + "\r\n" + files[0].name + "\r\n\r\n" + '<%= mytext.display("error.content.upload.formats") %>' + "\r\n" + fileformats);
		}
	}
}

function doEditImage(filename) {
	filename = encodeURIComponent(filename);
	if (! webeditor.window_close) {
		webeditor.window_close = function(mywindow) {
			if (webeditor_custom_window_close) {
				try {
					webeditor_custom_window_close(mywindow);
				} catch(e) {
				}
			} else {
				mywindow.close();
			}
		}
	}
	if (! webeditor.window_open) {
		webeditor.window_open = function(url, name, width, height, specs, replacehistory) {
			if (webeditor_custom_window) {
				try {
					return webeditor_custom_window(url, name, width, height, specs, replacehistory);
				} catch(e) {
				}
			} else {
				return window.open(url, name, specs, replacehistory);
			}
		}
	}
	webeditor.editImage = function(imagedata, old_format, new_format) {
		if (imagedata === false) {
			return document.getElementById("contentimage").src;
		} else if (imagedata != "") {
			document.getElementById("contentimage").src = imagedata;
			document.getElementById("file_data").value = imagedata;
			if (old_format != new_format) {
				document.getElementById("server_filename").value = document.getElementById("server_filename").value.replace(new RegExp("\." + old_format + "$"), "." + new_format);
			}
		}
	}
	if (! webeditor.window_proxy) webeditor.window_proxy = {};
	webeditor.window_proxy['imageeditor'] = webeditor;
	webeditor_window('/<%= mytext.display("adminpath") %>/webeditor/editimage.html?editor=imageeditor&url=' + filename, 'editimage_window', 750, 550, 'scrollbars=yes,width=750,height=550,resizable=yes,status=yes', true);
}

function openSelectContentItem(inputid, section, category, title, href_contentclass) {
	var inputnode = document.getElementById(inputid);
	if (inputnode && inputnode.options) {
		var contenttitle;
		var contentid;
		var contenthref;
		if (inputnode.selectedIndex >= 0) {
			contenttitle = inputnode.options[inputnode.selectedIndex].text;
			contentid = inputnode.options[inputnode.selectedIndex].value;
			contenthref = '/' + href_contentclass + '.jsp%3Fid%3D' + contentid;
		}
		if (! ((contentid > 0) && (contenttitle != ""))) {
			contentid = '';
			contenttitle = '';
			contenthref = '';
		}
		contenttitle = encodeURIComponent(contenttitle);
		if (! webeditor.window_close) {
			webeditor.window_close = function(mywindow) {
				if (webeditor_custom_window_close) {
					try {
						webeditor_custom_window_close(mywindow);
					} catch(e) {
					}
				} else {
					mywindow.close();
				}
			}
		}
		if (! webeditor.window_open) {
			webeditor.window_open = function(url, name, width, height, specs, replacehistory) {
				if (webeditor_custom_window) {
					try {
						return webeditor_custom_window(url, name, width, height, specs, replacehistory);
					} catch(e) {
					}
				} else {
					return window.open(url, name, specs, replacehistory);
				}
			}
		}
		if (! webeditor.window_proxy) webeditor.window_proxy = {};
		webeditor.window_proxy['selector'] = webeditor;
		webeditor_window('/<%= mytext.display("adminpath") %>/webeditor/selectormanager_wcm.jsp?editor=selector&input=' + inputid + '&section=' + section + '&category=' + category + '&title=' + title + '&id=' + contentid + '&link=' + contenttitle + '&href=' + contenthref, 'selector_window', 750, 450, 'scrollbars=yes,width=750,height=450,resizable=yes,status=yes', true);
	}
}

function selectContentItem(inputid, contentid, contenttitle, href) {
	var inputnode = document.getElementById(inputid);
	if (inputnode && inputnode.options && (contentid != "") && (contenttitle != "")) {
		var selected = false;
		if (inputnode.type == "select-multiple") {
			if (! selected) for (var i=0; i<inputnode.options.length; i++) {
				if ((inputnode.options[i].text.toLowerCase() == contenttitle.toLowerCase()) && (inputnode.options[i].value == contentid)) {
					inputnode.options[i].selected = true;
					selected = true;
					break;
				}
			}
			var newoption = new Option(contenttitle, contentid, true, true);
			if (! selected) for (var i=inputnode.options.length; i>0; i--) {
				if (inputnode.options[i-1].text.toLowerCase() > contenttitle.toLowerCase()) {
					var oldoption = inputnode.options[i-1];
					oldoption = new Option(oldoption.text, oldoption.value, oldoption.defaultSelected, oldoption.selected);
					inputnode.options[i] = new Option("", "", true, true);
					inputnode.options[i-1] = newoption;
					inputnode.options[i] = oldoption;
					selected = true;
				} else {
					inputnode.options[i] = newoption;
					selected = true;
					break;
				}
			}
			if (! selected) {
				inputnode.options[inputnode.options.length] = newoption;
			}
			inputnode.size = inputnode.options.length;
			if (inputid == "stylesheet") changeWebEditorStylesheet(inputnode);
			if (inputid == "scripts") changeScripts(inputnode);
			if (inputid == "related") changeRelated(inputnode);
			if (inputid.match(/_users$/)) changeUsers(inputnode);
			if (inputnode.onchange) {
				inputnode.onchange();
			}
		} else {
			inputnode.options[0].text = contenttitle;
			inputnode.options[0].value = contentid;
			inputnode.selectedIndex = 0;
			if (inputnode.onchange) {
				inputnode.onchange();
			}
		}
	}
}

function changedSelectedOptions(myinput, myoutput) {
	var myvalues = '';
	var unselectoptions = false;
	var selectedoptions = false;
	// has any of the non-special options been selected?
	for (var i=1; i<myinput.options.length; i++) {
		if ((myinput.options[i].value == '') || (myinput.options[i].value == '0') || (myinput.options[i].value == '-1') || (myinput.options[i].value == '-2')) {
			// ignore
		} else if (myinput.options[i].selected) {
			selectedoptions = true;
		}
	}
	for (var i=0; i<myinput.options.length; i++) {
		if (unselectoptions) {
			// an exclusive non-special option have been selected so unselect all other options
			if (myinput.options[i].selected) myinput.options[i].selected = false;
		} else if ((myinput.options[i].value == '') || (myinput.options[i].value == '0') || (myinput.options[i].value == '-1') || (myinput.options[i].value == '-2')) {
			// special options
			if (selectedoptions) {
				// some non-special options have been been selected so unselect this exclusive special option
				if (myinput.options[i].selected) myinput.options[i].selected = false;
			} else if (myinput.options[i].selected) {
				// no non-special options have been been selected so use this first selected exclusive special option and unselect other special options
				myvalues = myinput.options[i].value;
				selectedoptions = true;
				unselectoptions = true;
			}
		} else {
			// non-special options
			if (true) {
				// post values for all specific content items if any of them are selected
				// so users need to explicitly "remove" unwanted content items instead of simply unselecting them
				if (myinput.options[0].selected) {
					// the default (current value) special option has been selected so ignore any other selected options values
				} else if (i>0) {
					// use all specific content items values
					if (myvalues != '') myvalues += ',';
					myvalues += myinput.options[i].value;
					if (myinput.options[i].selected) {
						selectedoptions = true;
					}
				}
			} else {
				// only post values for actually selected specific content items
				// so users do not need to explicitly "remove" unwanted content items but simply to unselect them
				if (myinput.options[i].selected) {
					// use selected specific content items values
					if (myvalues != '') myvalues += ',';
					myvalues += myinput.options[i].value;
					selectedoptions = true;
				}
			}
		}
	}
	// if no selected options and values select the default (current value) special option
	if ((! selectedoptions) || (myvalues == '')) {
		if (myinput.options.length > 0) {
			myvalues = myinput.options[0].value;
			if (! myinput.options[0].selected) myinput.options[0].selected = true;
		}
	}
	// if the default (current value) special option has been selected check which (if any) other individual option that matches and select that instead
	var selectedoption = 0;
	for (var i=0; i<myinput.options.length; i++) {
		if (''+myinput.options[i].value == ''+myvalues) {
			if (myinput.options[selectedoption].selected) myinput.options[selectedoption].selected = false;
			if (! myinput.options[i].selected) myinput.options[i].selected = true;
			selectedoption = i;
		}
	}
	// if any other options has been selected then unselect the default (current value) special option
	for (var i=1; i<myinput.options.length; i++) {
		if (myinput.options[i].selected) {
			if (myinput.options[0].selected) myinput.options[0].selected = false;
		}
	}
	// if the default (current value) special option has been selected and has multiple values check if options for all the values still exist and select them instead
	if (myinput.options[0].selected) {
		// check if select options for all the default (current value) special option values still exist
		var selectedsinglevalues = 0;
		var mysinglevalues = myvalues.split(',');
		for (var j=0; j<mysinglevalues.length; j++) {
			var myvalue = mysinglevalues[j];
			for (var i=1; i<myinput.options.length; i++) {
				if (''+myinput.options[i].value == ''+myvalue) {
					selectedsinglevalues +=1;
					break;
				}
			}
		}
		// if select options for all the default (current value) special option values still exist then select them instead
		if (selectedsinglevalues == mysinglevalues.length) {
			for (var j=0; j<mysinglevalues.length; j++) {
				var myvalue = mysinglevalues[j];
				for (var i=1; i<myinput.options.length; i++) {
					if (''+myinput.options[i].value == ''+myvalue) {
						if (! myinput.options[i].selected) myinput.options[i].selected = true;
						if (myinput.options[0].selected) myinput.options[0].selected = false;
					}
				}
			}
		}
		// if only some select options for the default (current value) special option values still exist then select them instead and use their values
		if (selectedsinglevalues < mysinglevalues.length) {
			var myvalues = '';
			for (var j=0; j<mysinglevalues.length; j++) {
				var myvalue = mysinglevalues[j];
				for (var i=1; i<myinput.options.length; i++) {
					if (''+myinput.options[i].value == ''+myvalue) {
						if (myvalues != '') myvalues += ',';
						myvalues += myinput.options[i].value;
						if (! myinput.options[i].selected) myinput.options[i].selected = true;
						if (myinput.options[0].selected) myinput.options[0].selected = false;
					}
				}
			}
		}
			
	}
	myoutput.value = myvalues;
}

function moveSelectedOptionsUp(myinput) {
	for (var i=1; i<myinput.options.length; i++) {
		if ((myinput.options[i].value == '') || (myinput.options[i].value == '0') || (myinput.options[i].value == '-1') || (myinput.options[i].value == '-2')) {
			// ignore
		} else if ((myinput.options[i-1].value == '') || (myinput.options[i-1].value == '0') || (myinput.options[i-1].value == '-1') || (myinput.options[i-1].value == '-2')) {
			// ignore
		} else if ((myinput.options[i].selected) && (! myinput.options[i-1].selected)) {
			var myvalue = myinput.options[i-1].value;
			var mytext = myinput.options[i-1].text;
			var myselected = myinput.options[i-1].selected;
			myinput.options[i-1].value = myinput.options[i].value;
			myinput.options[i-1].text = myinput.options[i].text;
			myinput.options[i-1].selected = myinput.options[i].selected;
			myinput.options[i].value = myvalue;
			myinput.options[i].text = mytext;
			myinput.options[i].selected = myselected;
		}
	}
	triggerEvent(myinput, 'change');
}

function moveSelectedOptionsDown(myinput) {
	for (var i=myinput.options.length-2; i>=0; i--) {
		if ((myinput.options[i].value == '') || (myinput.options[i].value == '0') || (myinput.options[i].value == '-1') || (myinput.options[i].value == '-2')) {
			// ignore
		} else if ((myinput.options[i+1].value == '') || (myinput.options[i+1].value == '0') || (myinput.options[i+1].value == '-1') || (myinput.options[i+1].value == '-2')) {
			// ignore
		} else if ((myinput.options[i].selected) && (! myinput.options[i+1].selected)) {
			var myvalue = myinput.options[i+1].value;
			var mytext = myinput.options[i+1].text;
			var myselected = myinput.options[i+1].selected;
			myinput.options[i+1].value = myinput.options[i].value;
			myinput.options[i+1].text = myinput.options[i].text;
			myinput.options[i+1].selected = myinput.options[i].selected;
			myinput.options[i].value = myvalue;
			myinput.options[i].text = mytext;
			myinput.options[i].selected = myselected;
		}
	}
	triggerEvent(myinput, 'change');
}

function deleteSelectedOptions(myinput) {
	for (var i=myinput.options.length-1; i>0; i--) {
		if ((myinput.options[i].value == '') || (myinput.options[i].value == '0') || (myinput.options[i].value == '-1') || (myinput.options[i].value == '-2')) {
			// ignore
		} else if (myinput.options[i].selected) {
			myinput.options[i] = null;
		}
	}
	triggerEvent(myinput, 'change');
}

function triggerEvent(myinput, myevent) {
	if (myinput.fireEvent) {
		myinput.fireEvent("on"+myevent);
	} else {
	    var evt = document.createEvent("HTMLEvents");
	    evt.initEvent(myevent, false, true);
	    myinput.dispatchEvent(evt);
	}
}





var inputCount = new Object();

function initForm(input) {
	inputCount[input] = 0;
	var inputfield = document.getElementById(input);
	var inputvalues = inputfield.value.split(/\r?\n/);
	inputfield.value = "";
	for (var inputvalue in inputvalues) {
		var name = inputvalues[inputvalue].replace(/^<([^<>]+)>(.*)<\/([^<>]+)>$/, "$1");
		var value = inputvalues[inputvalue].replace(/^<([^<>]+)>(.*)<\/([^<>]+)>$/, "$2").replace(/&/g, "&amp;").replace(/"/g, "&quot;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
		name = name.replace(/[<\/>]/g, "").replace(/^ /g, "").replace(/ $/g, "");
		if (name) {
			inputfield.value += "\r\n<" + name + ">" + value + "</" + name + ">\r\n";
			inputfield.value = inputfield.value.replace(/\r\n\r\n/g, "\r\n");
		}
	}
	inputvalues = inputfield.value.split(/\r?\n/);
	for (var inputvalue in inputvalues) {
		var name = inputvalues[inputvalue].replace(/^<([^>]+)>(.*)<\/([^>]+)>$/, "$1");
		var value = inputvalues[inputvalue].replace(/^<([^>]+)>(.*)<\/([^>]+)>$/, "$2").replace(/&/g, "&amp;").replace(/"/g, "&quot;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
		name = name.replace(/[<\/>]/g, "").replace(/^ /g, "").replace(/ $/g, "");
		if (name) {
			addInfo(input, false, name, value);
		}
	}
}

function addInfo(input, id, name, value) {
	if (! document.getElementById(input + "name")) return;
	if (! name) name = document.getElementById(input + "name").value;
	if ((! value) && (document.getElementById(input + "value"))) value = document.getElementById(input + "value").value;
	if (! value) value = "";
	name = name.replace(/[<\/>]/g, "").replace(/^ /g, "").replace(/ $/g, "");
	if (! name) return;
	if ((input == 'segmentation') && (! value.match(/^[-+=][0-9]+$/))) {
		alert('<%= mytext.display("content.segmentation.format.error") %>');
		return;
	}
	if (document.getElementById(input + "internal") && document.getElementById(input + "internal").checked) name = "_" + name;
if (update) {
	document.getElementById(input + "name").value = "";
}
	if (name) {
		if (! id) {
			if (! inputCount[input]) inputCount[input] = 0;
			inputCount[input] += 1;
			id = inputCount[input];
		} else {
			if (id > inputCount[input]) inputCount[input] = parseInt(id);
		}

		var tr = document.createElement("tr");
		tr.setAttribute("id", input + "name" + id);
		var th;
		th = document.createElement("th");
		th.setAttribute("class", "WCMinnerContentInputName");
		th.setAttribute("align", "left");
		th.setAttribute("colSpan", "2");
		if (name.charAt(0) == "_") {
			th.innerHTML = name.substring(1) + " <font size=\"-1\">(<%= mytext.display("content.metainformation.internal") %>)</font>";
		} else {
			th.innerHTML = name;
		}
		tr.appendChild(th);
		var insertpoint = document.getElementById(input + "addnew");
		if (insertpoint && insertpoint.parentNode) insertpoint.parentNode.insertBefore(tr, insertpoint);

		tr = document.createElement("tr");
		tr.setAttribute("id", input + "value" + id);
		var td;
		td = document.createElement("td");
		td.setAttribute("class", "WCMinnerContentInputValue");
		td.setAttribute("align", "left");
		td.setAttribute("colSpan", "3");
		if (input == 'product_options') {
if (update) {
			td.innerHTML = '<textarea cols="80" rows="5" id="' + input + id + '" name="' + input + id + '" onBlur="updateInfo(\'' + input + '\',' + id + ',\'' + name + '\',this.value)" onChange="updateInfo(\'' + input + '\',' + id + ',\'' + name + '\',this.value)">' + value.replace(/&/g, "&amp;").replace(/"/g, "&quot;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/\|/g, "\r\n") + '</textarea> <input type="button" onClick="removeInfo(\'' + input + '\',' + id + ',\'' + name + '\')" value="<%= mytext.display("delete") %>">';
} else {
			td.innerHTML = value.replace(/&/g, "&amp;").replace(/"/g, "&quot;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/\|/g, "<br>") + "&nbsp;";
}
		} else if (input == 'segmentation') {
if (update) {
			td.innerHTML = '<input type="text" size="5" maxlength="10" id="' + input + id + '" name="' + input + id + '" value="' + value.replace(/"/g, "&quot;") + '" onBlur="updateInfo(\'' + input + '\',' + id + ',\'' + name + '\',this.value)" onChange="updateInfo(\'' + input + '\',' + id + ',\'' + name + '\',this.value)"> <input type="button" onClick="removeInfo(\'' + input + '\',' + id + ',\'' + name + '\')" value="<%= mytext.display("delete") %>">';
} else {
			td.innerHTML = value.replace(/&/g, "&amp;").replace(/"/g, "&quot;").replace(/</g, "&lt;").replace(/>/g, "&gt;") + "&nbsp;";
}
		} else {
if (update) {
			td.innerHTML = '<input type="text" size="80" maxlength="255" id="' + input + id + '" name="' + input + id + '" value="' + value.replace(/"/g, "&quot;") + '" onBlur="updateInfo(\'' + input + '\',' + id + ',\'' + name + '\',this.value)" onChange="updateInfo(\'' + input + '\',' + id + ',\'' + name + '\',this.value)"> <input type="button" onClick="removeInfo(\'' + input + '\',' + id + ',\'' + name + '\')" value="<%= mytext.display("delete") %>">';
} else {
			td.innerHTML = value.replace(/&/g, "&amp;").replace(/"/g, "&quot;").replace(/</g, "&lt;").replace(/>/g, "&gt;") + "&nbsp;";
}
		}
		tr.appendChild(td);
		insertpoint = document.getElementById(input + "addnew");
		if (insertpoint && insertpoint.parentNode) insertpoint.parentNode.insertBefore(tr, insertpoint);
		var inputfield = document.getElementById(input);
		var regex = new RegExp("<" + name + ">.*</" + name + ">");
		if (inputfield && (! inputfield.value.match(regex))) {
			inputfield.value = inputfield.value + "\r\n<" + name + "></" + name + ">\r\n";
			inputfield.value = inputfield.value.replace(/\r\n\r\n/g, "\r\n");
		}
		var inputfield = document.getElementById(input + id);
		try {
			if (inputfield) inputfield.focus();
		} catch(e) {
		}
	}
	if (mySlideTabs) mySlideTabs.tabs_resize();
}

function removeInfo(input, id, name) {
	if (id && name) {
		var tr = document.getElementById(input + "name" + id);
		if (tr && tr.parentNode) tr.parentNode.removeChild(tr);
		tr = document.getElementById(input + "value" + id);
		if (tr && tr.parentNode) tr.parentNode.removeChild(tr);
		var inputfield = document.getElementById(input);
		if (inputfield && inputfield.value) {
			var regex = new RegExp("<" + name + ">.*</" + name + ">");
			inputfield.value = inputfield.value.replace(regex, "");
			inputfield.value = inputfield.value.replace(/\r\n\r\n/g, "\r\n");
		}
	}
}

function updateInfo(input, id, name, value) {
	if (id && name) {
		var inputfield = document.getElementById(input);
		if (inputfield && inputfield.value) {
			if ((input == 'segmentation') && (! value.match(/^[-+=][0-9]+$/))) {
				alert('<%= mytext.display("content.segmentation.format.error") %>');
			}
			var regex = new RegExp("<" + name + ">.*</" + name + ">");
			inputfield.value = inputfield.value.replace(regex, "<" + name + ">" + value.replace(/[\r\n]+/g, "|") + "</" + name + ">");
		}
	}
}

function doValidateMarkup(myform) {
	var content = POST("/<%= mytext.display("adminpath") %>/content/validatemarkup.jsp?id=<%= html.encodeHtmlEntities(myrequest.getParameter("id")) %>&<%= Math.random() %>", document.contentform);
	document.validationform.fragment.value = "";
	document.validationform.text.value = "";
	if (document.contentform.contentclass.value == "stylesheet") {
		document.validationform.text.value = content;
		mywindow = webeditor_window('', '_validate', 800, 600, 'scrollbars=yes,width=800,height=600,resizable=yes,status=yes', true);
		if (mywindow && (typeof(mywindow.focus) == "function")) mywindow.focus();
		document.validationform.action = "http://jigsaw.w3.org/css-validator/validator";
		document.validationform.submit();
	} else {
		document.validationform.fragment.value = content;
		mywindow = webeditor_window('', '_validate', 800, 600, 'scrollbars=yes,width=800,height=600,resizable=yes,status=yes', true);
		if (mywindow && (typeof(mywindow.focus) == "function")) mywindow.focus();
		document.validationform.action = "http://validator.w3.org/check";
		document.validationform.submit();
	}
}

function doCompare(myform) {
	var save_target = document.contentform.target;
	var save_action = document.contentform.action;
	var save_method = document.contentform.method;
	var save_title_disabled;
	var save_content_disabled;
	var save_summary_disabled;
	if (document.contentform.title) save_title_disabled = document.contentform.title.disabled;
	if (document.contentform.content) save_content_disabled = document.contentform.content.disabled;
	if (document.contentform.summary) save_summary_disabled = document.contentform.summary.disabled;
	document.contentform.target = '_compare';
	document.contentform.action = '/<%= mytext.display("adminpath") %>/content/compare.jsp?id=' + myform.id.value + '&archive=' + myform.archive.value;
	document.contentform.method = 'post';
	if (document.contentform.title) document.contentform.title.disabled = false;
	if (document.contentform.content) document.contentform.content.disabled = false;
	if (document.contentform.summary) document.contentform.summary.disabled = false;
	mywindow = webeditor_window('', document.contentform.target, 800, 600, 'scrollbars=yes,width=800,height=600,resizable=yes,status=yes', true);
	if (mywindow && (typeof(mywindow.focus) == "function")) mywindow.focus();
	$('#tabs').find('[rel=Primary_Content]').click();
	if (document.contentform.onsubmit) {
		document.contentform.onsubmit();
	} else if (typeof(WebEditorSubmit) != "undefined") {
		WebEditorSubmit();
	}
	document.contentform.submit();
	document.contentform.target = save_target;
	document.contentform.action = save_action;
	document.contentform.method = save_method;
	if (document.contentform.title) document.contentform.title.disabled = save_title_disabled;
	if (document.contentform.content) document.contentform.content.disabled = save_content_disabled;
	if (document.contentform.summary) document.contentform.summary.disabled = save_summary_disabled;
}

function doPreview(myform) {
	if (! myform) myform = document.contentform;
	var save_target = myform.target;
	var save_action = myform.action;
	myform.target = '_preview';
	if (update) {
		myform.action = '/<%= mytext.display("adminpath") %>/content/preview.jsp?mode=preview&id=&redirect=&preview=' + saveId;
	} else {
		myform.action = '/page.jsp?mode=preview&id=<%= html.encodeHtmlEntities(myrequest.getParameter("id")) %>&version=&redirect=';
	}
	if (document.getElementById("simulator") && (document.getElementById("simulator").value != "")) {
		var myurl = myform.action;
		myform.action = '/<%= mytext.display("adminpath") %>/simulators/' + document.getElementById("simulator").value + '/index.jsp?url=' + encodeURIComponent(myurl);
	}
	var windowoptions = "";
	if (document.getElementById("simulator") && (document.getElementById("simulator").value != "")) {
		windowoptions = GET('/<%= mytext.display("adminpath") %>/simulators/' + document.getElementById("simulator").value + '/window.txt');
	}
	if (windowoptions == "") windowoptions = 'width=800,height=600';
	mywindow = window.open('', myform.target, 'scrollbars=yes,resizable=yes,status=yes,'+windowoptions);
	if (mywindow && (typeof(mywindow.focus) == "function")) mywindow.focus();
	$('#tabs').find('[rel=Primary_Content]').click();
	if (myform.onsubmit) {
		myform.onsubmit();
	} else if (typeof(WebEditorSubmit) != "undefined") {
		WebEditorSubmit();
	}
	myform.submit();
	myform.target = save_target;
	myform.action = save_action;
}

function doCreate(myform) {
	myform.action = '/<%= mytext.display("adminpath") %>/content/create_post.jsp?id=<%= html.encodeHtmlEntities(myrequest.getParameter("id")) %>&redirect=<% if (! myrequest.getParameter("redirect").equals("")) { %><%= URLEncoder.encode(myrequest.getParameter("redirect"), myconfig.get(db, "charset")) %><% } %>';
	if (myform.onsubmit) {
		myform.onsubmit();
	}
	myform.submit();
}

function doUnpublish(myform) {
	if (confirm("<%= mytext.display("content.unpublish.confirm") %>")) {
		myform.action = '/<%= mytext.display("adminpath") %>/content/delete_post.jsp?id=<%= html.encodeHtmlEntities(myrequest.getParameter("id")) %>&unpublish=unpublish&redirect=<% if (! myrequest.getParameter("redirect").equals("")) { %><%= URLEncoder.encode(myrequest.getParameter("redirect"), myconfig.get(db, "charset")) %><% } %>';
		if (myform.onsubmit) {
			myform.onsubmit();
		}
		myform.submit();
	}
}

function doDelete(myform) {
	if (confirm("<%= mytext.display("content.delete.confirm") %>")) {
		myform.action = '/<%= mytext.display("adminpath") %>/content/delete_post.jsp?id=<%= html.encodeHtmlEntities(myrequest.getParameter("id")) %>&delete=delete&redirect=<% if (! myrequest.getParameter("redirect").equals("")) { %><%= URLEncoder.encode(myrequest.getParameter("redirect"), myconfig.get(db, "charset")) %><% } %>';
		if (myform.onsubmit) {
			myform.onsubmit();
		}
		myform.submit();
	}
}

function doStock() {
	if (document.getElementById("product_stock_amount") && document.getElementById("product_stock_amount_input") && document.getElementById("product_stock_amount_update") && document.getElementById("product_stock_amount_current")) {
		var my_product_stock_amount_current = parseInt(document.getElementById("product_stock_amount_current").innerHTML);
		var my_product_stock_amount_input = parseInt(document.getElementById("product_stock_amount_input").value);
		my_product_stock_amount_current = isNaN(my_product_stock_amount_current) ? 0 : my_product_stock_amount_current;
		my_product_stock_amount_input = isNaN(my_product_stock_amount_input)? 0 : my_product_stock_amount_input;
		document.getElementById("product_stock_amount").value = "" + my_product_stock_amount_input;
		if (document.getElementById("product_stock_amount_update").value == "add") {
			document.getElementById("product_stock_amount_current").innerHTML = "" + (my_product_stock_amount_current + my_product_stock_amount_input);
		} else if (document.getElementById("product_stock_amount_update").value == "subtract") {
			document.getElementById("product_stock_amount_current").innerHTML = "" + (my_product_stock_amount_current - my_product_stock_amount_input);
		} else if (document.getElementById("product_stock_amount_update").value == "set") {
			document.getElementById("product_stock_amount_current").innerHTML = "" + my_product_stock_amount_input;
		}
	}
	if (document.getElementById("product_restocked_amount") && document.getElementById("product_restocked_amount_input") && document.getElementById("product_restocked_amount_update") && document.getElementById("product_restocked_amount_current")) {
		var my_product_restocked_amount_current = parseInt(document.getElementById("product_restocked_amount_current").innerHTML);
		var my_product_restocked_amount_input = parseInt(document.getElementById("product_restocked_amount_input").value);
		my_product_restocked_amount_current = isNaN(my_product_restocked_amount_current) ? 0 : my_product_restocked_amount_current;
		my_product_restocked_amount_input = isNaN(my_product_restocked_amount_input)? 0 : my_product_restocked_amount_input;
		document.getElementById("product_restocked_amount").value = "" + my_product_restocked_amount_input;
		if (document.getElementById("product_restocked_amount_update").value == "add") {
			document.getElementById("product_restocked_amount_current").innerHTML = "" + (my_product_restocked_amount_current + my_product_restocked_amount_input);
		} else if (document.getElementById("product_restocked_amount_update").value == "subtract") {
			document.getElementById("product_restocked_amount_current").innerHTML = "" + (my_product_restocked_amount_current - my_product_restocked_amount_input);
		} else if (document.getElementById("product_restocked_amount_update").value == "set") {
			document.getElementById("product_restocked_amount_current").innerHTML = "" + my_product_restocked_amount_input;
		}
	}
}

function doSaved() {
	if (document.getElementById("product_stock_amount") && document.getElementById("product_stock_amount_input") && document.getElementById("product_stock_amount_update") && document.getElementById("product_stock_amount_current")) {
		document.getElementById("product_stock_amount_input").value = "0";
		document.getElementById("product_stock_amount_update").value = "add";
		document.getElementById("product_stock_amount").value = "0";
	}
	if (document.getElementById("product_restocked_amount") && document.getElementById("product_restocked_amount_input") && document.getElementById("product_restocked_amount_update") && document.getElementById("product_restocked_amount_current")) {
		document.getElementById("product_restocked_amount_input").value = "0";
		document.getElementById("product_restocked_amount_update").value = "add";
		document.getElementById("product_restocked_amount").value = "0";
	}
}

var saveAction = "/<%= mytext.display("adminpath") %>/content/update_post.jsp";
if (document.location.href.indexOf("create.jsp")>0) {
	saveAction = "/<%= mytext.display("adminpath") %>/content/create_post.jsp";
}

var saveId = "<%= html.encodeHtmlEntities(myrequest.getParameter("id")) %>";

function doSaveAndClose(do_close) {
	doStock();
	var saveframe = document.getElementById("iframe_save");
	var savedocument = (saveframe.contentDocument)? saveframe.contentDocument : saveframe.Document;
	if (savedocument.getElementById("waiterror")) {
		savedocument.getElementById("waiterror").parentNode.removeChild(savedocument.getElementById("waiterror"));
	}
	if (document.getElementById("errorcontainer")) {
		document.getElementById("errorcontainer").innerHTML = "&nbsp;";
		document.getElementById("errorcontainer").style.display = "none";
	}
	if (document.contentform.revision && document.getElementById("revision_input")) {
		document.contentform.revision.value = document.getElementById("revision_input").value;
	}
	var do_save = true;
	var validation = "";
<% if (Common.fileExists(Common.getRealPath(servletcontext, "/" + mytext.display("adminpath") + "/api/validatecontent.jsp"))) { %>
	if (! document.location.href.indexOf("create.jsp")) {
		validation = POST("/<%= mytext.display("adminpath") %>/api/validatecontent.jsp?id=<%= html.encodeHtmlEntities(myrequest.getParameter("id")) %>&<%= Math.random() %>", document.contentform);
	} else {
		validation = POST("/<%= mytext.display("adminpath") %>/api/validatecontent.jsp?<%= Math.random() %>", document.contentform);
	}
	validation = validation.replace(/\\r/gi, "\r").replace(/\\n/gi, "\n");
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
        if (do_save && document.contentform && document.contentform.contentclass && (document.contentform.contentclass.value != "image") && (document.contentform.contentclass.value != "file") && (document.contentform.contentclass.value != "link")) {
		if (! document.location.href.indexOf("create.jsp")) {
	                validation = POST("/<%= mytext.display("adminpath") %>/content/checklinks.jsp?id=<%= html.encodeHtmlEntities(myrequest.getParameter("id")) %>&<%= Math.random() %>", document.contentform);
		} else {
    	            validation = POST("/<%= mytext.display("adminpath") %>/content/checklinks.jsp?<%= Math.random() %>", document.contentform);
		}
		validation = validation.replace(/\\r/gi, "\r").replace(/\\n/gi, "\n");
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
	if (do_save) {
		if (do_close) {
			document.contentform.action = saveAction + "?id=" + saveId + "&redirect=<% if (! myrequest.getParameter("redirect").equals("")) { %><%= URLEncoder.encode(myrequest.getParameter("redirect"), myconfig.get(db, "charset")) %><% } %>";
			document.contentform.target = "";
			if (WebEditorSaveReady(document.contentform)) {
				doLogin(true);
			} else {
				alert('<%= mytext.display("wait.page.loading") %>');
			}
		} else {
			document.contentform.action = saveAction + "?id=" + saveId + "&redirect=/<%= mytext.display("adminpath") %>/close.html";
			document.contentform.target = "iframe_save";
			if (WebEditorSaveReady(document.contentform)) {
				doLogin(false);
			} else {
				alert('<%= mytext.display("wait.page.loading") %>');
			}
		}
	}
}

function doLogin(saveclose) {
	if (saveclose) {
		savewindow = webeditor_window('/<%= mytext.display("adminpath") %>/saveclose.jsp', '_save', 800, 600, 'scrollbars=yes,width=800,height=600,resizable=yes,status=yes', true);
	} else {
		savewindow = webeditor_window('/<%= mytext.display("adminpath") %>/save.jsp', '_save', 800, 600, 'scrollbars=yes,width=800,height=600,resizable=yes,status=yes', true);
	}
	if (savewindow && (typeof(savewindow.focus) == "function")) savewindow.focus();
}

function doSave() {
	$('#tabs').find('[rel=Primary_Content]').click();
	if (typeof(doMultiFileUpload) == "function") {
		try {
			if (doMultiFileUpload(document.contentform)) {
				if ((typeof(savewindow)!="undefined") && (typeof(savewindow.close)=="function")) savewindow.close();
				return;
			}
		} catch(e) {
		}
	}
	if (document.contentform.onsubmit) {
		document.contentform.onsubmit();
	} else if (typeof(WebEditorSubmit) != "undefined") {
		WebEditorSubmit();
	}
	document.contentform.submit();
}

function doCreated(id, error) {
	if (id) {
		saveAction = "/<%= mytext.display("adminpath") %>/content/update_post.jsp";
		saveId = id;
	} else if (error) {
		alert("<%= mytext.display("errortext") %>"+error);
	}
}
