<%@page import="com.iliketo.util.Str"%>
<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="HardCore.UCmaintainDataILiketo" %>
<%@ page import="com.iliketo.dao.*" %>
<%@ page import="com.iliketo.model.*" %>

<%

System.out.println("Log - " + "execute action post_add_item");
System.out.println("Log - DOCUMENT_ROOT = " + DOCUMENT_ROOT);

cms.CMSLog(myrequest.getParameter("id"), "post", myrequest.getParameter("database"));

if (! myrequest.getParameter("database").equals("")) {
	String idRegister = mysession.get(myconfig.get(db, "getset") + Str.S_ID_REGISTER_COLLECTION);
	if(idRegister != null && !idRegister.equals("")){	//se conter o s_id_register_collection, adiciona os novos itens
		
		
		//validacoes
		CmsConfigILiketo cmsILiketo = new CmsConfigILiketo(request, response);
		String myUserId = mysession.get("userid");
		MemberDAO memberDAO = new MemberDAO(db, request);
		Member member = ((Member) memberDAO.readByColumn("id_member", myUserId, Member.class));
		long uploadBytes = cmsILiketo.getSizeFilesInBytes();
		
		if(cmsILiketo.validateFreeSpaceStorage(member, uploadBytes)){
			UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext);
			Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "post_add_item");
			
			//após finalizar o cadastro de coleção, remove o s_id_register_collection da sessão
			System.out.println("Log session - s_id_register_collection = " + mysession.get(myconfig.get(db, "getset") + Str.S_ID_REGISTER_COLLECTION));
			mysession.remove(myconfig.get(db, "getset") + Str.S_ID_REGISTER_COLLECTION);
			System.out.println("Log session - remove s_id_register_collection = " + mysession.get(myconfig.get(db, "getset") + Str.S_ID_REGISTER_COLLECTION));
		
			memberDAO.calculateTotalFilesMemberInBytes(); //calcula espaco utilizado do membro
			
		}else{
			ModelILiketo model = new ModelILiketo(request, response);
			model.addMessageError("freeSpace", "You do not have enough free space, needed " + uploadBytes/1024 + " KB.");
			myresponse.sendRedirect("/page.jsp?id=90");			//reload page add item
		}
		
	}else{	//senão já foi adicionado novos itens, redireciona para pagina coleção
		System.out.println("Log Register Collection - error, já foi adicionado os itens");
		myresponse.sendRedirect("/page.jsp?id=48");
	}
	
} 


System.out.println("");


%>


<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
