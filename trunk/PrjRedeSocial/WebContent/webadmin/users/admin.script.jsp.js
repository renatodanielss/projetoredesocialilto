
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
		th.innerHTML = name;
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
			var regex = new RegExp("<" + name + ">.*</" + name + ">");
			inputfield.value = inputfield.value.replace(regex, "<" + name + ">" + value.replace(/[\r\n]+/g, "|") + "</" + name + ">");
		}
	}
}

function doSave(form) {
        var do_save = true;
        var validation = "";
<% if (Common.fileExists(Common.getRealPath(servletcontext, "/" + mytext.display("adminpath") + "/api/validateuser.jsp"))) { %>
	validation = POST("/<%= mytext.display("adminpath") %>/api/validateuser.jsp?request=<%= myrequest.getRequestURI() %>&id=<%= html.encodeHtmlEntities(myrequest.getParameter("id")) %>&<%= Math.random() %>", form);
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
		form.submit();
	}
}
