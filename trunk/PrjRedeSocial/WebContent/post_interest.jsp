<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="HardCore.UCmaintainDataILiketo" %>
<%@ page import="com.iliketo.util.*" %>

<%

//menu new register collection
if(myrequest.getParameter("newRegisterInterest") != null &&
		myrequest.getParameter("newRegisterInterest").equals("yes")){
	mysession.remove(myconfig.get(db, "getset") + Str.S_ID_REGISTER_INTEREST); //remove o s_id_register_interest da sess�o para adicionar um novo interesse
	System.out.println("Log Redirecionar para - New Register Interest");
	myresponse.sendRedirect("/page.jsp?id=695");
}

System.out.println("Log - " + "execute action post_interest");
System.out.println("Log - DOCUMENT_ROOT = " + DOCUMENT_ROOT);

cms.CMSLog(myrequest.getParameter("id"), "post", myrequest.getParameter("database"));

if (! myrequest.getParameter("database").equals("")) {
	
	//Se tiver o s_id_register_collection na sess�o, ent�o � um update
	if(mysession.get(myconfig.get(db, "getset") + Str.S_ID_REGISTER_INTEREST) != null && 
			!mysession.get(myconfig.get(db, "getset") + Str.S_ID_REGISTER_INTEREST).equals("")){
		System.out.println(mysession.get(myconfig.get(db, "getset") + Str.S_ID_REGISTER_INTEREST));
		System.out.println();
		//se conter um id no parametro � um update do usuario da nova cole��o na database dbcollection
		if(myrequest.getParameter("id") != null && !myrequest.getParameter("id").equals("")){
			
			//faz update da cole��o
			UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext);
			Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "post_interest");
			System.out.println("Log - Update do novo interesse pelo usu�rio OK = " + 
					mysession.get(myconfig.get(db, "getset") + Str.S_INTEREST) + 
					" / ID update = " + mysession.get(myconfig.get(db, "getset") + Str.S_ID_REGISTER_INTEREST));
			
			//redireciona para add new item
			myresponse.sendRedirect("/page.jsp?id=696");
			
		
		//se n�o tiver o id para update, adiciona no parametro e redireciona para fazer update
		}else{
			
			String idRegisterUpdate = mysession.get(myconfig.get(db, "getset") + Str.S_ID_REGISTER_INTEREST);
			String post = "/post_interest.jsp?database=dbinterest&id=" + idRegisterUpdate;
			%>
				<!-- redireciona para adicionar os novos campos alterados na pagina pelo usuario na dbcollection -->
				<jsp:forward page="<%=post%>">
					<jsp:param value="<%=idRegisterUpdate%>" name="id_interest"/>
				</jsp:forward>
			<%
		}
		
	}else{//Se n�o tiver o s_id_register_collection na sess�o, ent�o cria uma nova cole��o
		
		//UCmaintainData maintainData = new UCmaintainData(mytext);
		UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext);
		Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "post_interest");
		
		String ID = data.getId(); //reupera o ID gerado	
		System.out.println("Log - Create nova cole��o OK = " + 
					mysession.get(myconfig.get(db, "getset") + Str.S_INTEREST) + 
					" / novo ID da cole��o OK = " + ID);
		//Seta o nome da cole��o recuperado pelo parametro
		mysession.set(myconfig.get(db, "getset") + Str.S_ID_REGISTER_INTEREST, ID);//seta na session
		System.out.println("Set session s_id_register_interest=" + ID);
		
		mysession.set(Str.S_ID_INTEREST, ID);//seta na session o id da cole��o criada
		
		//redireciona para add new item
		myresponse.sendRedirect("/page.jsp?id=696");
		
	}
	
}//else {
	//UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	//Page mypage = maintainContent.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
//}

%>



<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
