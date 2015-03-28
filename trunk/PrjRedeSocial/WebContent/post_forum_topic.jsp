<%@page import="com.iliketo.util.Str"%>
<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="HardCore.UCmaintainDataILiketo" %>


<%

System.out.println("Log - " + "execute action post_forum_topic");

cms.CMSLog(myrequest.getParameter("id"), "post", myrequest.getParameter("database"));

if (! myrequest.getParameter("database").equals("")) {
		
		//cria um novo topic ou faz update do mesmo
		UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext);
		Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "post_topic");
		
		String idTopic = data.getId(); //recupera id criado do topico
		
		if(idTopic != null && idTopic.equals("")){
			
			myresponse.sendRedirect("/page.jsp?id=628&topic=" + idTopic); //redireciona para pagina comentario do topico
			System.out.println("Log - " + "Topic id = " + idTopic +" criado com sucesso");
		
		}else{
			System.out.println("Log - " + "Topic não foi criado");
		}
}


%>


<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
