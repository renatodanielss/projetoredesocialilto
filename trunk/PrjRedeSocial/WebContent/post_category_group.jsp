<%@page import="com.iliketo.util.Str"%>
<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="HardCore.UCmaintainDataILiketo" %>


<%

System.out.println("Log - " + "execute action post_category_group - Criar nova Categoria/Grupo");

cms.CMSLog(myrequest.getParameter("id"), "post", myrequest.getParameter("database"));

if (! myrequest.getParameter("database").equals("")) {
			
		UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext);
		Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "post_category");
		
		
		String idCreateCategory = data.getId();	//id criado da categoria
		myrequest.setParameter("fk_category_id", idCreateCategory); //seta chave estrangeira para criar novo grupo
		data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "post_group");
		
		
		String idCreateGroup = data.getId(); //id criado da grupo		
		myrequest.setParameter("fk_group_id", idCreateGroup); //seta chave estrangeira para criar novo forum
		data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "post_forum");

		System.out.println("Log - Create Category/Group - name: " + myrequest.getParameter("name_category"));
}


%>


<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
