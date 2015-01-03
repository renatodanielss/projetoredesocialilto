<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.MenuContent" %>
<%
	mysession.set("admin_pagesize", "all");
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Content pageEdit = maintainContent.getIndex(mysession, myrequest, myresponse, myconfig, db);
	mysession.set("admin_pagesize", "");

	String page_name = "";
	String column_name = "";
	if (mysession.get("admin_contentclass").equals("page")) {
		page_name = mytext.display("content.pages");
		column_name = mytext.display("content.page");
	} else if (mysession.get("admin_contentclass").equals("template")) {
		page_name = mytext.display("content.templates");
		column_name = mytext.display("content.template");
	} else if (mysession.get("admin_contentclass").equals("stylesheet")) {
		page_name = mytext.display("content.stylesheets");
		column_name = mytext.display("content.stylesheet");
	} else if (mysession.get("admin_contentclass").equals("image")) {
		page_name = mytext.display("content.images");
		column_name = mytext.display("content.image");
	} else if (mysession.get("admin_contentclass").equals("file")) {
		page_name = mytext.display("content.files");
		column_name = mytext.display("content.file");
	} else if (mysession.get("admin_contentclass").equals("link")) {
		page_name = mytext.display("content.links");
		column_name = mytext.display("content.link");
	} else if (mysession.get("admin_contentclass").equals("product")) {
		page_name = mytext.display("content.products");
		column_name = mytext.display("content.product");
	} else {
		page_name = mytext.display("content.elements");
		column_name = mytext.display("content.element");
	}
%>
<%@ include file="relations.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>