<%@ page buffer="256kb" %><%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="com.iliketo.control.UserController" %>

<%

//***Executa e valida Registro iliketo**** 
String errorILiketo = "";
UserController controller = new UserController();

if(!controller.validateFieldsForm(db, request)){
	if(request.getAttribute("msgError") != null && !request.getAttribute("msgError").equals("")){ //se tiver msg de error
		errorILiketo = (String) request.getAttribute("msgError");
	}
}

Page mypage = null;

if(!errorILiketo.equals("")){ //se conter error na validação do registro, adiciona o erro na page
	
	//Cria um UCbrowseWebsite
	UCbrowseWebsite browseWebsite = new UCbrowseWebsite(new Text());
	mypage = browseWebsite.getPage(servletcontext, mysession, myrequest, myresponse, myconfig, db, website);
	
	//coloca os valores do campo do form novamente na page para não perder os dados que o usuário digitou
	mypage.parse_output_register_iliketo(errorILiketo, request);
	website.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_doctype");
	
}else{
	
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	mypage = maintainContent.doRegister(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, website, database);
	
	//recupera o registro do usuario criado na tabela users
	String idRegisterUser = controller.getIdCreatedUser(db, "username", request.getParameter("username"));
	
	//Solicitação post.jsp - INIT
	//Depois que chama o método "doRegister" acima, o sistema Asbru faz o insert na tabela users 
	//código abaixo faz o redicionamento com solicitação para pagina post.jsp
	System.out.println("email = " + request.getParameter("email"));
	System.out.println("username = " + request.getParameter("username"));
	System.out.println("ID User = " + idRegisterUser);
	
	%>
	<!-- TAG para redirecionar para pagina post.jsp passando mais um parametro com o valor da pagina retorno realizado pelo Asbru -->
	<jsp:forward page="/post.jsp?database=dbmembers">
		<jsp:param value="<%=idRegisterUser%>" name="id_member"/>
		<jsp:param value="/post.jsp?database=dbmemberscollector" name="redirect"/>
	</jsp:forward>
	<%
	//Solicitação post.jsp - END
}

//***Continua aplicação do sistema Asbru**** 

cms.CMSpage(myrequest.getParameter("id"), mypage);
cms.HttpHeaders(myrequest.getParameter("id"));
if (! myconfig.get(db, "charset").equals("")) myresponse.setContentType("text/html; charset=" + myconfig.get(db, "charset"));

%><%= cms.PageDOCTYPE(myrequest.getParameter("id")) %>
<html<%= cms.PageHTML(myrequest.getParameter("id")) %>>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=<%= myconfig.get(db, "charset") %>"<%= cms.XHTMLclosed %>>
<meta name="author" content="<%= cms.PageHeader(myrequest.getParameter("id"), "author", "") %>"<%= cms.XHTMLclosed %>>
<meta name="description" content="<%= cms.PageHeader(myrequest.getParameter("id"), "description", "") %>"<%= cms.XHTMLclosed %>>
<meta name="keywords" content="<%= cms.PageHeader(myrequest.getParameter("id"), "keywords", "") %>"<%= cms.XHTMLclosed %>>
<title><%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getParameter("id"), "title", "")) %></title>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getParameter("id"), "metainfo", "")) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.CMSHeader(myrequest.getParameter("id"))) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageStyleSheet(myrequest.getParameter("id"))) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageScripts(myrequest.getParameter("id"))) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getParameter("id"), "htmlheader", "")) %>
</head>
<body<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getParameter("id"), "htmlbodyonload", "", cms.BODYmargins)) %>>
<%= cms.CMSDisplay(myrequest.getParameter("id")) %>
<%= cms.CMSStyleSheet(myrequest.getParameter("id")) %>
<%= cms.CMSTemplate(myrequest.getParameter("id")) %>
<% HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, out, cms.DisplayPage(myrequest.getParameter("id"), "body", "")); %>
</body>
</html>
<% cms.CMSLog(mypage.getId(), "page", ""); %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>

