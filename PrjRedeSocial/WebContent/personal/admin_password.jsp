<%@ include file="../webadmin.jsp" %><%@ include file="../config.personal.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainUsers" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.User" %><%@ page import="HardCore.RequireUser" %>
<%@ page import="com.iliketo.control.UpdateUserController" %><%@ page import="com.iliketo.dao.IliketoDAO"%><%
	
	//***Executa e valida Registro iliketo**** 
	String email = "";
	String password = "";
	UpdateUserController controller = new UpdateUserController();
	
	if(!myrequest.getParameter("password").equals(mysession.get("password"))){
		if(!controller.validatePassword(db, request)){
			if(request.getAttribute("msgError") != null && !request.getAttribute("msgError").equals("")){ //se tiver msg de error
				password = (String) request.getAttribute("msgError");
			}
		}
	}
	
	Page mypage = null;
	
	if(!password.equals("")){ //se conter error na validação do registro, adiciona o erro na page
				
		//reponse mesma pagina passando error no parametro
		myresponse.sendRedirect("/personal/admin.jsp?id=678&p=" + password); //recuperar parametro no html >> ###error###	
				
		//Cria um UCbrowseWebsite
		//UCbrowseWebsite browseWebsite = new UCbrowseWebsite(new Text());
		//mypage = browseWebsite.getPage(servletcontext, mysession, myrequest, myresponse, myconfig, db, website);
		
		//coloca os valores do campo do form novamente na page para não perder os dados que o usuário digitou
		//mypage.parse_output_register_iliketo(errorILiketo, request);
		//website.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_doctype");
		
	}else{
		
		String idRegisterUser = IliketoDAO.getValueOfDatabase(db, "id", "dbmembers", "id_member", mysession.get("userid"));
		mytext = new Text(myrequest);
	
		mysession.set("mode", "");
		RequireUser.User(mytext, mysession.get("username"), myrequest, myresponse, mysession, db);
	
		UCmaintainUsers maintainUsers = new UCmaintainUsers(mytext);
		User myuser = maintainUsers.doPersonalUpdate(mysession, myrequest, myresponse, myconfig, db);
	
		Login.userSession(myuser, mysession, db);
	
		UCmaintainContent maintainContent = new UCmaintainContent(mytext);
		mypage = maintainContent.doPersonalUpdate(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
	
		Page adminpage = maintainContent.getPersonalAdmin(myuser, mypage, servletcontext, mysession, myrequest, myresponse, myconfig, db);
		mysession.set("id", adminpage.getId());
		
}
%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>