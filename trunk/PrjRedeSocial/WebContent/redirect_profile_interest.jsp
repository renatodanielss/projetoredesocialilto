<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="com.iliketo.util.*" %>

<%


System.out.println("Log - " + "Mostrar tela Profile Interest");

String ID = (String) myrequest.getParameter("myIdInterest"); //recupera o id da coleção como parametro

//Seta o id da coleção
mysession.set(myconfig.get(db, "getset") + Str.S_ID_INTEREST, ID);//seta na session o id da coleção
System.out.println("Set session s_id_interest=" + ID);

myresponse.sendRedirect("/page.jsp?id=699");	//redireciona para o id que contem a pagina de My Interest Profile


%>


<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
