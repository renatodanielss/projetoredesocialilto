<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Contentgroup" %>
<%@ page import="HardCore.Contenttype" %>
<%@ page import="HardCore.Imagegroup" %>
<%@ page import="HardCore.Imagetype" %>
<%@ page import="HardCore.Filegroup" %>
<%@ page import="HardCore.Filetype" %>
<%@ page import="HardCore.Linkgroup" %>
<%@ page import="HardCore.Linktype" %>
<%@ page import="HardCore.Productgroup" %>
<%@ page import="HardCore.Producttype" %>
<%@ page import="HardCore.Workflow" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Content pageEdit = maintainContent.getIndex(mysession, myrequest, myresponse, myconfig, db, true, false);
	Content pageCreate = maintainContent.getCreateRecords();
	Workflow workflow = new Workflow(mytext);
	int recordCount = maintainContent.getRecordCount();
	String error = "";

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
	} else if (mysession.get("admin_contentclass").equals("script")) {
		page_name = mytext.display("content.scripts");
		column_name = mytext.display("content.script");
	} else {
		page_name = mytext.display("content.elements");
		column_name = mytext.display("content.element");
	}
%>
<%@ include file="stock.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>