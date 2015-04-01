<%@page import="com.iliketo.util.Str"%>
<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="HardCore.UCmaintainDataILiketo" %>


<%

System.out.println("Log - " + "execute action post_event");

cms.CMSLog(myrequest.getParameter("id"), "post", myrequest.getParameter("database"));

if (! myrequest.getParameter("database").equals("")) {
		
		//cria um novo evento no grupo da categoria
		UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext);
		Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "post_event");
		
		String idEvent = data.getId(); //id evento criado
		
		if(idEvent != null && !idEvent.equals("")){ //verifica se foi criado evento
			System.out.println("Log - " + "Event name: " + myrequest.getParameter("name_event") +" id: "+idEvent+" criado com sucesso");
			myresponse.sendRedirect("/page.jsp?id=623&group=" + mysession.get("s_id_group")); //redireciona para o grupo da categoria
		}else{
			System.out.println("Log - " + "Error ao criar evento");
		}
}


%>


<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
