<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Page" %>
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
	Content pageEdit = maintainContent.doSearch(mysession, myrequest, myresponse, myconfig, db, true, false);
	Content pageCreate = maintainContent.getCreateRecords();
	Workflow workflow = new Workflow(mytext);
	Page mypage = new Page(mytext);

	String page_name = mytext.display("content.search.title");
	String column_name = mytext.display("content.content");

%>
<%@ include file="searchadvanced.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>