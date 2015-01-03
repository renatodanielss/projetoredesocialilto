<%@ page buffer="256kb" %><%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCbrowseWebsite" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.Shopcart" %><%@ page import="HardCore.Order" %><%

UCbrowseWebsite browseWebsite = new UCbrowseWebsite(mytext);
Page mypage = browseWebsite.getShopcartPage(website, servletcontext, inifile, mysession, myrequest, myresponse, myconfig, db, original_database);
cms.CMSpage(myrequest.getParameter("id"), mypage);
cms.HttpHeaders(myrequest.getParameter("id"));
if (! myconfig.get(db, "charset").equals("")) myresponse.setContentType("text/html; charset=" + myconfig.get(db, "charset"));

%><%= cms.PageDOCTYPE(myrequest.getParameter("id")) %>
<html<%= cms.PageHTML(myrequest.getParameter("id")) %>>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=<%= myconfig.get(db, "charset") %>"<%= cms.XHTMLclosed %>>
<meta name="author" content="<%= cms.PageHeader(myrequest.getParameter("id"), "author", "") %>"<%= cms.XHTMLclosed %>>
<meta name="description" content="<%= cms.PageHeader(myrequest.getParameter("id"), "description", "") %>"<%= cms.XHTMLclosed %>>
<meta name="keywords" content="<%= cms.PageHeader(myrequest.getParameter("id"), "keywords", "") %>"<%= cms.XHTMLclosed %>>
<title><%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getParameter("id"), "title", "")) %></title>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getParameter("id"), "metainfo", "")) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.CMSHeader(myrequest.getParameter("id"))) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageStyleSheet(myrequest.getParameter("id"))) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageScripts(myrequest.getParameter("id"))) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getParameter("id"), "htmlheader", "")) %>
<script type="text/javascript">
if (typeof(set_form_values) == "undefined") {
set_form_values = function() {
	for (f=1; f<=document.forms.length; f++) {
		for (e=1; e<=document.forms[f-1].elements.length; e++) {
			var name = document.forms[f-1].elements[e-1].name
			var type = document.forms[f-1].elements[e-1].type
			var value = document.forms[f-1].elements[e-1].value
			if (name == 'card_type') { value = '<%= mysession.get("card_type").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'card_number') { value = '<%= mysession.get("card_number").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'card_issuedmonth') { value = '<%= mysession.get("card_issuedmonth").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'card_issuedyear') { value = '<%= mysession.get("card_issuedyear").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'card_expirymonth') { value = '<%= mysession.get("card_expirymonth").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'card_expiryyear') { value = '<%= mysession.get("card_expiryyear").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'card_name') { value = '<%= mysession.get("card_name").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'card_cvc') { value = '<%= mysession.get("card_cvc").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'card_issue') { value = '<%= mysession.get("card_issue").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'card_postalcode') { value = '<%= mysession.get("card_postalcode").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'delivery_organisation') { value = '<%= mysession.get("delivery_organisation").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'delivery_name') { value = '<%= mysession.get("delivery_name").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'delivery_address') { value = '<%= mysession.get("delivery_address").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'delivery_postalcode') { value = '<%= mysession.get("delivery_postalcode").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'delivery_city') { value = '<%= mysession.get("delivery_city").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'delivery_state') { value = '<%= mysession.get("delivery_state").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'delivery_country') { value = '<%= mysession.get("delivery_country").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'delivery_phone') { value = '<%= mysession.get("delivery_phone").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'delivery_fax') { value = '<%= mysession.get("delivery_fax").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'delivery_email') { value = '<%= mysession.get("delivery_email").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'invoice_organisation') { value = '<%= mysession.get("invoice_organisation").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'invoice_name') { value = '<%= mysession.get("invoice_name").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'invoice_address') { value = '<%= mysession.get("invoice_address").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'invoice_postalcode') { value = '<%= mysession.get("invoice_postalcode").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'invoice_city') { value = '<%= mysession.get("invoice_city").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'invoice_state') { value = '<%= mysession.get("invoice_state").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'invoice_country') { value = '<%= mysession.get("invoice_country").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'invoice_phone') { value = '<%= mysession.get("invoice_phone").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'invoice_fax') { value = '<%= mysession.get("invoice_fax").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if (name == 'invoice_email') { value = '<%= mysession.get("invoice_email").replaceAll("\r", "\\r").replaceAll("\n", "\\n") %>'; }
			if ((type == 'text') || (type == 'textarea')) {
				document.forms[f-1].elements[e-1].value = value;
			} else  if ((type == 'select-one') || (type == 'select-multiple')) {
				for (o=document.forms[f-1].elements[e-1].options.length; o>=1; o--) {
					if ((document.forms[f-1].elements[e-1].options[o-1].text == value) || (document.forms[f-1].elements[e-1].options[o-1].value == value)) {
						document.forms[f-1].elements[e-1].options[o-1].selected = true;
					}
				}
			} else  if ((type == 'checkbox') || (type == 'radio')) {
				if (document.forms[f-1].elements[e-1].value == value) {
					document.forms[f-1].elements[e-1].checked = true;
				}
			}
<% if ((mysession.get("shopcart").equals("")) || (! mysession.get("shopcart_availability").equals("")) || (! mysession.get("shopcart_outofstock").equals(""))) { %>
			if (name == 'checkout') { document.forms[f-1].elements[e-1].disabled = true; }
			if (name == 'confirm') { document.forms[f-1].elements[e-1].disabled = true; }
			if (name == 'complete') { document.forms[f-1].elements[e-1].disabled = true; }
<% } %>
		}
	}
}
}
</script>
</head>
<body<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getParameter("id"), "htmlbodyonload", "", cms.BODYmargins, "onload=\"set_form_values();\"")) %>>
<%= cms.CMSDisplay(myrequest.getParameter("id")) %>
<%= cms.CMSStyleSheet(myrequest.getParameter("id")) %>
<%= cms.CMSTemplate(myrequest.getParameter("id")) %>
<% HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, out, cms.DisplayPage(myrequest.getParameter("id"), "body", "")); %>
</body>
</html>
<% cms.CMSLog(myrequest.getParameter("id"), "page", ""); %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>