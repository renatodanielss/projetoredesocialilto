<%@page import="com.iliketo.util.Str"%>
<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="HardCore.UCmaintainDataILiketo" %>


<%

System.out.println("Log - " + "execute action post_photo_member");
System.out.println("Log - DOCUMENT_ROOT = " + DOCUMENT_ROOT);

cms.CMSLog(myrequest.getParameter("id"), "post", myrequest.getParameter("database"));

if (! myrequest.getParameter("database").equals("")) {
		
	String action = "";	
	if(myrequest.getParameter("database").equals("dbmembers")){
		action = "post_photo_member";
	}else{
		action = "post_photo_store";
	}
	
	UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext);
	Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, action);
} 

%>


<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
