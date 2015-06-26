<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="HardCore.UCmaintainDataILiketo" %>
<%@ page import="com.iliketo.util.*" %>
<%@ page import="com.iliketo.model.*" %>
<%@ page import="com.iliketo.dao.*" %>

<%

//menu new register collection
if(myrequest.getParameter("newRegisterCollection") != null &&
		myrequest.getParameter("newRegisterCollection").equals("yes")){
	mysession.remove(myconfig.get(db, "getset") + Str.S_ID_REGISTER_COLLECTION); //remove o s_id_register_collection da sessão para adicionar uma nova coleção
	System.out.println("Log Redirecionar para - New Register Collection");
	myresponse.sendRedirect("/page.jsp?id=410");
}

System.out.println("Log - " + "execute action post_collection");
System.out.println("Log - DOCUMENT_ROOT = " + DOCUMENT_ROOT);

cms.CMSLog(myrequest.getParameter("id"), "post", myrequest.getParameter("database"));

if (! myrequest.getParameter("database").equals("")) {
	
	//Se tiver o s_id_register_collection na sessão, então é um update
	if(mysession.get(myconfig.get(db, "getset") + Str.S_ID_REGISTER_COLLECTION) != null && 
			!mysession.get(myconfig.get(db, "getset") + Str.S_ID_REGISTER_COLLECTION).equals("")){
		System.out.println(mysession.get(myconfig.get(db, "getset") + Str.S_ID_REGISTER_COLLECTION));
		System.out.println();
		//se conter um id no parametro é um update do usuario da nova coleção na database dbcollection
		if(myrequest.getParameter("id") != null && !myrequest.getParameter("id").equals("")){
			
			//faz update da coleção
			UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext);
			Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "post_collection");
			System.out.println("Log - Update da nova coleção pelo usuário OK = " + 
					mysession.get(myconfig.get(db, "getset") + Str.S_COLLECTION) + 
					" / ID update = " + mysession.get(myconfig.get(db, "getset") + Str.S_ID_REGISTER_COLLECTION));
			
			
			//redireciona para add new item
			myresponse.sendRedirect("/page.jsp?id=90");
			
		
		//se não tiver o id para update, adiciona no parametro e redireciona para fazer update
		}else{
			
			String idRegisterUpdate = mysession.get(myconfig.get(db, "getset") + Str.S_ID_REGISTER_COLLECTION);
			String post = "/post_collection.jsp?database=dbcollection&id=" + idRegisterUpdate;
			%>
				<!-- redireciona para adicionar os novos campos alterados na pagina pelo usuario na dbcollection -->
				<jsp:forward page="<%=post%>">
					<jsp:param value="<%=idRegisterUpdate%>" name="id_collection"/>
				</jsp:forward>
			<%
		}
		
	}else{//Se não tiver o s_id_register_collection na sessão, então cria uma nova coleção
		
		
		//validacoes
		CmsConfigILiketo cmsILiketo = new CmsConfigILiketo(request, response);
		String myUserId = mysession.get("userid");
		MemberDAO memberDAO = new MemberDAO(db, request);
		Member member = ((Member) memberDAO.readByColumn("id_member", myUserId, Member.class));
		long uploadBytes = cmsILiketo.getSizeFilesInBytes();
		com.iliketo.model.Collection col = (com.iliketo.model.Collection) cmsILiketo.getObjectOfParameter(com.iliketo.model.Collection.class);	//objeto com dados do form
		
		if(cmsILiketo.validateFreeSpaceStorage(member, uploadBytes)){			
			UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext);
			Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "post_collection");
			
			String ID = data.getId(); //reupera o ID gerado	
			System.out.println("Log - Create nova coleção OK = " + 
						mysession.get(myconfig.get(db, "getset") + Str.S_COLLECTION) + 
						" / novo ID da coleção OK = " + ID);
			//Seta o nome da coleção recuperado pelo parametro
			mysession.set(myconfig.get(db, "getset") + Str.S_ID_REGISTER_COLLECTION, ID);//seta na session
			System.out.println("Set session s_id_register_collection=" + ID);
			
			mysession.set(Str.S_ID_COLLECTION, ID);//seta na session o id da coleção criada			
			
			memberDAO.calculateTotalFilesMemberInBytes(); //calcula espaco utilizado do membro
			
			//redireciona para add new item
			myresponse.sendRedirect("/page.jsp?id=90");
			
		}else{
			ModelILiketo model = new ModelILiketo(request, response);
			model.addAttribute("collection", col);
			model.addMessageError("freeSpace", "You do not have enough free space, needed " + uploadBytes/1024 + " KB.");
			myresponse.sendRedirect("/page.jsp?id=410");			//reload page form register collection
		}		
				
	}
	
}

%>


<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
