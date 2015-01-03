<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Generator" content="Asbru Web Content Editor">
<meta http-equiv="Copyright" content="(C) 2002-2014 - Asbru Ltd. - www.asbrusoft.com">
<script type="text/javascript" src="webeditor.properties.js"></script>
<title>Asbru Web Content Editor</title>
<link rel="stylesheet" type="text/css" href="webeditor.css">
<script type="text/javascript">

document.title = HtmlDecode(Text('hyperlink_title'));

function requestParameter(name) {
	var value = "";
	if (start = request.indexOf("?"+name+"=")+1) {
		value = request.substring(start+name.length+1);
	} else if (start = request.indexOf("&"+name+"=")+1) {
		value = request.substring(start+name.length+1);
	}
	if (value.indexOf("&")+1) {
		value = value.substring(0, value.indexOf("&"));
	}
	value = unescape(value);
	return value;
}

window.focus();
var request = "" + window.location;
editor = requestParameter("editor");
if ((typeof(webeditor) == "undefined") && editor && window.parent.opener && window.parent.opener[editor]) webeditor = window.parent.opener[editor];
if ((typeof(webeditor) == "undefined") && editor && window.opener && window.opener[editor]) webeditor = window.opener[editor];
if ((typeof(webeditor) == "undefined") && editor && top.opener && top.opener[editor]) webeditor = top.opener[editor];
if ((typeof(webeditor) == "undefined") && editor && top && top[editor]) webeditor = top[editor];
if ((typeof(webeditor) == "undefined") && editor && parent && parent[editor]) webeditor = parent[editor];

function initform(link) {
	var target = "";
	var text = "";
	var htmlclass = "";
	var htmlid = "";
	var title = "";
	var onclick = "";
	if (link == null) {
		link = requestParameter("href");
		target = requestParameter("target");
		text = requestParameter("text");
		htmlclass = requestParameter("htmlclass");
		htmlid = requestParameter("htmlid");
		title = requestParameter("title");
		onclick = requestParameter("onclick");
	} else {
		if (document.linkform.target) target = document.linkform.target.options[document.linkform.target.selectedIndex].value;
		if (document.linkform.text) text = document.linkform.text.value;
		if (document.linkform.htmlclass) htmlclass = document.linkform.htmlclass.value;
		if (document.linkform.htmlid) htmlid = document.linkform.htmlid.value;
		if (document.linkform.title) title = document.linkform.title.value;
		if (document.linkform.htmlonclick) onclick = document.linkform.htmlonclick.value;
	}

	if (('http://'+document.location.hostname == link.substring(0,7+document.location.hostname.length)) && (link.length > 7+document.location.hostname.length)) {
		link = link.substring(7+document.location.hostname.length);
	} else {
		for (j=0; j<=document.linkform.type.length; j++) {
			if (document.linkform.type.options[j] && (document.linkform.type.options[j].value == link.substring(0,document.linkform.type.options[j].value.length))) {
				document.linkform.type.selectedIndex = j;
			}
		}
		link = link.substring(document.linkform.type.options[document.linkform.type.selectedIndex].value.length);
	}

	if (link.indexOf('#')>=0) {
		document.linkform.bookmark.value=link.substring(link.indexOf('#')+1);
		link = link.substring(0,link.indexOf('#'));
	} else {
		document.linkform.bookmark.value='';
	}

	if (target) {
		for (j=0; j<=document.linkform.target.length; j++) {
			if (document.linkform.target.options[j] && (document.linkform.target.options[j].value == target)) {
				document.linkform.target.selectedIndex = j;
			}
		}
	}

	document.linkform.link.value=link;
	if (link) {
		for (j=0; j<=document.linkform.quicklink.length; j++) {
			if (document.linkform.quicklink.options[j] && (document.linkform.quicklink.options[j].value == link)) {
				document.linkform.quicklink.selectedIndex = j;
			} else if (document.linkform.quicklink.options[j] && (document.linkform.quicklink.options[j].value == document.linkform.type.options[document.linkform.type.selectedIndex].value + link)) {
				document.linkform.quicklink.selectedIndex = j;
			}
		}
	}

	if (document.linkform.text) document.linkform.text.value = text.replace("\t", " ").replace("\r", " ").replace("\n", " ");
	if (document.linkform.htmlclass) document.linkform.htmlclass.value = htmlclass.replace("\t", " ").replace("\r", " ").replace("\n", " ");
	if (document.linkform.htmlid) document.linkform.htmlid.value = htmlid.replace("\t", " ").replace("\r", " ").replace("\n", " ");
	if (document.linkform.title) document.linkform.title.value = title.replace("\t", " ").replace("\r", " ").replace("\n", " ");
	if (document.linkform.htmlonclick) document.linkform.htmlonclick.value = onclick.replace("\t", " ").replace("\r", " ").replace("\n", " ");
}

function linkit() {
	var url;
	var target = "";
	var text = "";
	var htmlclass = "";
	var htmlid = "";
	var title = "";
	var onclick = "";
	url = document.linkform.type.value+document.linkform.link.value;
	if ((document.linkform.type.value == "") && (url.substring(0,4) == "www.")) {
		url = 'http://'+url;
	}
	if (document.linkform.bookmark.value != '') {
		url += '#'+document.linkform.bookmark.value;
	}
	if (document.linkform.target) { target = document.linkform.target.value; } else { target = ""; }
	if (document.linkform.text) { text = document.linkform.text.value; } else { text = ""; }
	if (document.linkform.htmlclass) { htmlclass = document.linkform.htmlclass.value; } else { htmlclass = ""; }
	if (document.linkform.htmlid) { htmlid = document.linkform.htmlid.value; } else { htmlid = ""; }
	if (document.linkform.title) { title = document.linkform.title.value; } else { title = ""; }
	if (document.linkform.htmlonclick) { onclick = document.linkform.htmlonclick.value; } else { onclick = ""; }
	webeditor.insertHyperlink(url, target, text, htmlclass, htmlid, onclick, title);
	webeditor.window_close(top);
}

</script>
</head>
<body onload="initform()">
<form action="#" name="linkform" onSubmit="linkit(); return false;">
<input type="hidden" name="text" value="">
<input type="hidden" name="htmlonclick" value="">
<!--
	<p><font size="-1">
	Modify this file ("hyperlink.html") to integrate with your own web application and database.
	You may simply want to add your own hyperlinks to the "Quicklinks" list below,
	or you may want to make a complete hyperlink manager like the one used in the Asbru Web Content Management system
	(<a href="hyperlinkmanager.jpg" target="_blank">view example</a>).
	</font></p>
-->
<table summary="" width="100%" border="0" class="dtree">
	<tr align="left" valign="top"> 
		<td colspan="4" class="webeditor_window_title"><script type="text/javascript">document.write(Text('hyperlink_title'));</script></td>
	</tr>
	<tr align="left" valign="top"> 
		<td colspan="4">
			<fieldset>
			<legend class="webeditor_window_heading"><script type="text/javascript">document.write(Text('hyperlink_url'));</script></legend>
				<table summary="" width="100%">
					<tr>
						<td class="webeditor_window_attribute"><script type="text/javascript">document.write(Text('hyperlink_url_quicklinks'));</script></td>
						<td colspan="2" class="webeditor_window_value"> 
							<select name="quicklink" style="width: 100%;" onChange="javascript:initform(this.options[this.selectedIndex].value)">
								<option value="" selected>&nbsp;
<%
out.write("<option value=\"http://www.asbrusoft.com/\">www.asbrusoft.com" + "\r\n");
%>
							</select>
						</td>
					</tr>
					<tr>
						<td class="webeditor_window_attribute"><script type="text/javascript">document.write(Text('hyperlink_url_type'));</script></td>
						<td class="webeditor_window_attribute"><script type="text/javascript">document.write(Text('hyperlink_url_address'));</script></td>
						<td class="webeditor_window_attribute"><script type="text/javascript">document.write(Text('hyperlink_url_bookmark'));</script></td>
					</tr>
					<tr>
						<td class="webeditor_window_value"> 
							<select name="type">
								<option value="" selected>&nbsp;
								<option value="http://">http://
								<option value="https://">https://
								<option value="ftp://">ftp://
								<option value="mailto:">mailto:
							</select>
						</td>
						<td class="webeditor_window_value" width="100%"> 
							<input type="text" name="link" size="35" style="width: 100%;">
						</td>
						<td class="webeditor_window_value"> 
							<span style="white-space: nowrap;">#<input type="text" name="bookmark" size="10"></span>
						</td>
					</tr>
				</table>
			</fieldset>
		</td>
	</tr>
	<tr align="left" valign="top"> 
		<td colspan="4">
			<fieldset>
			<legend class="webeditor_window_heading"><script type="text/javascript">document.write(Text('hyperlink_title_title'));</script></legend>
				<table summary="" width="100%">
					<tr>
						<td class="webeditor_window_attribute"><script type="text/javascript">document.write(Text('hyperlink_title_title'));</script></td>
						<td class="webeditor_window_value"> 
							<input type="text" name="title" size="50" value="">
						</td>
					</tr>
				</table>
			</fieldset>
		</td>
	</tr>
	<tr align="left" valign="top"> 
		<td colspan="4">
			<fieldset>
			<legend class="webeditor_window_heading"><script type="text/javascript">document.write(Text('hyperlink_target'));</script></legend>
				<table summary="" width="100%">
					<tr>
						<td class="webeditor_window_attribute"><script type="text/javascript">document.write(Text('hyperlink_target_target'));</script></td>
						<td class="webeditor_window_value"> 
							<select name="target">
<script type="text/javascript">
<!--
								document.writeln('<option value="" selected>&nbsp;');
								document.writeln('<option value="_self">' + Text('hyperlink_target_target_self'));
								document.writeln('<option value="_parent">' + Text('hyperlink_target_target_parent'));
								document.writeln('<option value="_top">' + Text('hyperlink_target_target_top'));
								document.writeln('<option value="_blank">' + Text('hyperlink_target_target_blank'));
// -->
</script>
							</select>
						</td>
					</tr>
				</table>
			</fieldset>
		</td>
	</tr>
	<tr align="left" valign="top"> 
		<td colspan="4">
			<fieldset>
			<legend class="webeditor_window_heading"><script type="text/javascript">document.write(Text('class_id'));</script></legend>
				<table summary="" width="100%">
					<tr>
						<td class="webeditor_window_attribute"><script type="text/javascript">document.write(Text('htmlclass'));</script></td>
						<td class="webeditor_window_value"> 
							<input type="text" name="htmlclass" size="20" value="">
						</td>
						<td></td>
						<td class="webeditor_window_attribute"><script type="text/javascript">document.write(Text('htmlid'));</script></td>
						<td class="webeditor_window_value"> 
							<input type="text" name="htmlid" size="20" value="">
						</td>
					</tr>
				</table>
			</fieldset>
		</td>
	</tr>
</table>
<br>
<div align="center">
<script type="text/javascript">document.write('<input type="submit" value="' + Text('ok') + '">');</script>
<script type="text/javascript">document.write('<input type="button" value="' + Text('cancel') + '" onClick="webeditor.window_close(window);">');</script>
</div>
</form>
</body>
</html>
