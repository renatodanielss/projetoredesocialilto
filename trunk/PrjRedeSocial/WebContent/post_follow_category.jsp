<%@page import="com.iliketo.dao.IliketoDAO"%>
<%@page import="com.iliketo.util.Str"%>
<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="HardCore.UCmaintainDataILiketo" %>


<%

System.out.println("Log - " + "execute action post_follow_category");

String myIdInterest = myrequest.getParameter("myIdInterest");	//recupera do parametro o id do interesse
String idCategory = myrequest.getParameter("id_category");		//recupera do parametro o id da categoria

//valida myIdIntereste e idCategory
if (!myIdInterest.equals("") && !idCategory.equals("")) {
	
	myrequest.setParameter("database", "dbinterest");			//set database para atualizar

	//Atualizar dados da categoria na database dbcollection
	myrequest.setParameter("fk_category_id", idCategory);
	System.out.println("Log - ID Interesse: '"+ myIdInterest +"' seguiu a categoria/grupo: " + idCategory);
		
	String idReal = IliketoDAO.getValueOfDatabase(db, "id", "dbinterest", "id_collection", myIdInterest);//recupera id real do Asbru
	myrequest.setParameter("id", idReal);	//Asbru usa referencia id real para update
		
	//chama método update
	UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext);
	Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "");
		
	%>
	<!-- jsp responsavel para redirecionar para o grupo -->
	<jsp:forward page="/redirect_info_group.jsp">
		<jsp:param value="<%=idCategory%>" name="id_category"/>
	</jsp:forward>
	<%
		

}else{
	System.out.println("Log - Error não encontrado parametro myIdInterest");
}


%>

<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
