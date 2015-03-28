<%@ include file="../webadmin.jsp" %><%@ include file="../config.personal.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainUsers" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.User" %><%@ page import="HardCore.RequireUser" %>
<%@ page import="com.iliketo.control.UpdateUserController" %><%@ page import="com.iliketo.dao.IliketoDAO"%><%
	
	//***Executa e valida Registro iliketo**** 
	String errorILiketo = "";
	UpdateUserController controller = new UpdateUserController();
	
	if(!myrequest.getParameter("username").equals(mysession.get("username"))){
		if(!controller.validateUsername(db, request)){
			if(request.getAttribute("msgError") != null && !request.getAttribute("msgError").equals("")){ //se tiver msg de error
				errorILiketo += (String) request.getAttribute("msgError");
			}
		}
	}
	if(!myrequest.getParameter("email").equals(mysession.get("email"))){
		if(!controller.validateEmail(db, request)){
			if(request.getAttribute("msgError") != null && !request.getAttribute("msgError").equals("")){ //se tiver msg de error
				errorILiketo += (String) request.getAttribute("msgError");
			}
		}
	}
	
	Page mypage = null;
	
	if(!errorILiketo.equals("")){ //se conter error na validação do registro, adiciona o erro na page
				
		//reponse mesma pagina passando error no parametro
		myresponse.sendRedirect("/personal/admin.jsp?id=328&error=" + errorILiketo); //recuperar parametro no html >> ###error###	
				
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
		
		%>
		<!-- TAG para redirecionar para pagina post.jsp passando mais um parametro com o valor da pagina retorno realizado pelo Asbru -->
		<jsp:forward page="/post.jsp?database=dbmembers">
			<jsp:param value="<%=idRegisterUser%>" name="id"/>
			<jsp:param value="/page.jsp?id=620" name="redirect"/>
		</jsp:forward>
		<%
}
%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>