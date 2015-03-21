<%@page import="com.iliketo.util.Str"%>
<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="HardCore.UCmaintainDataILiketo" %>


<%

System.out.println("Log - " + "execute action post_group_forum");

cms.CMSLog(myrequest.getParameter("id"), "post", myrequest.getParameter("database"));

if (! myrequest.getParameter("database").equals("")) {
		
	String group = myrequest.getParameter("group");	//parametro recuperado possui um valor se create ou update do grupo
	
	//valida um novo group para criar, se sim cria forum e eventos para este grupo
	if(group != null && group.equals("create")){
		
		UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext);
		Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "post_group");
		
		//id criado do grupo é um chave estrangeira no campo fk_group_id na database forum
		String idCreateGroup = data.getId();	
		
		//seta valores iniciais para o forum do grupo criado, a chave estrangeira do grupo será criada junto ao forum
		myrequest.setParameter("fk_group_id", idCreateGroup);
		
		//Chama método para criar o forum para o grupo criado
		data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "post_forum");
	
	}else if(group != null && group.equals("update")){
		//update do grupo
		UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext);
		Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "post_group");
	}
}


%>


<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
