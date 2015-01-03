<%@ include file="config.jsp" %>
<%@ page import="HardCore.UCaccessAdministration" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="java.io.File" %>
<%
	UCaccessAdministration.doIndex(mytext, "", mysession, myrequest, myresponse, myconfig, db);
	LinkedHashMap mywebsitesettings = new LinkedHashMap();
	if (db == null) db = new DB();
	mywebsitesettings = db.query_records("select * from config where configname like 'config_%' order by configname");

	String title = "";
	if (mysession.get("menu").equals("config")) {
		title = mytext.display("config.index.config");
	} else if (mysession.get("menu").equals("configsystem")) {
		title = mytext.display("config.index.system");
	} else if (mysession.get("menu").equals("configfeatures")) {
		title = mytext.display("config.index.features");
	} else if (mysession.get("menu").equals("configorders")) {
		title = mytext.display("config.index.orders");
	} else if (mysession.get("menu").equals("configproducts")) {
		title = mytext.display("config.index.products");
	} else if (mysession.get("menu").equals("configcontent")) {
		title = mytext.display("config.index.content");
	} else if (mysession.get("menu").equals("configimages")) {
		title = mytext.display("config.index.images");
	} else if (mysession.get("menu").equals("configfiles")) {
		title = mytext.display("config.index.files");
	} else if (mysession.get("menu").equals("configusers")) {
		title = mytext.display("config.index.users");
	} else {
		title = mytext.display("config.index.config");
	}
%>
<%@ include file="index.config.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>