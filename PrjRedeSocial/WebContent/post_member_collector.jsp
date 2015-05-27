<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="com.iliketo.control.UserController" %>
<%

cms.CMSLog(myrequest.getParameter("id"), "post", myrequest.getParameter("database"));

if (! myrequest.getParameter("database").equals("")) {
	UCmaintainData maintainData = new UCmaintainData(mytext);
	Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
} else {
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page mypage = maintainContent.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
}

%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>
<% String idRegisterUser = controller.getIdCreatedUser(db, "username", request.getParameter("username")); %>
<!-- TAG para redirecionar para pagina post.jsp passando mais um parametro com o valor da pagina retorno realizado pelo Asbru -->
	<jsp:forward page="/post.jsp?database=dbmemberscollector">
		<jsp:param value="<%=idRegisterUser%>" name="id_member"/>
		<jsp:param value="/page.jsp?id=286" name="redirect"/>
	</jsp:forward>