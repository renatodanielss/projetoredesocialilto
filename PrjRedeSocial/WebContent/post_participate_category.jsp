<%@page import="com.iliketo.dao.IliketoDAO"%>
<%@page import="com.iliketo.util.Str"%>
<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="HardCore.UCmaintainDataILiketo" %>


<%

System.out.println("Log - " + "execute action post_participate_category");

cms.CMSLog(myrequest.getParameter("id"), "post", myrequest.getParameter("database"));

if (! myrequest.getParameter("database").equals("") && ! myrequest.getParameter("id").equals("")) {//passar database dbcollection e id da coleção para tualizar participante
	
	if(myrequest.getParameter("database").equals("dbcollection")){ //atualizar na dbcollection o membro participante
		
		String idCategory = myrequest.getParameter("id_category");	//recupera do parametro o id da categoria
		String nameCategory = myrequest.getParameter("name_category"); //recupera do parametro o nome da categoria
		
		if(idCategory != null && !idCategory.equals("") && nameCategory != null && !nameCategory.equals("")){	
			
			//Atualizar o id e o nome da categoria na chave estrangeira da database dbcollection
			myrequest.setParameter("fk_category_id", idCategory);
			myrequest.setParameter("name_category", nameCategory);
			
			//chama método update
			UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext);
			Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "");
			
			System.out.println("Log - Coleção: '"+ mysession.get("s_collection") +"' entrou para a categoria/grupo: " + nameCategory);
			
			%>
			<!-- request redireciona para jsp responsavel por redirecionar para o grupo -->
			<jsp:forward page="/redirect_info_group.jsp">
				<jsp:param value="<%=idCategory%>" name="id_category"/>
			</jsp:forward>
			<%
			
		}else{
			System.out.println("Log - Não encontrado id_category ou name_category");
		}
	}else{
		System.out.println("Log - Não encontrado database dbcollection para atualizar participante");
	}
}else{
	System.out.println("Log - Não encontrado database dbcollection e id real da coleção para realizar update do participante");
}


%>


<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
