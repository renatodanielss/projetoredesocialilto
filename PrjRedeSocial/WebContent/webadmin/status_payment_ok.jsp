<%@ include file="config.jsp" %>
<%@ page import="HardCore.UCaccessAdministration" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="com.iliketo.dao.*" %>
<%@ page import="com.iliketo.model.*" %>
<%@ page import="org.apache.log4j.Logger" %>

<%
	UCaccessAdministration.doIndex(mytext, "", mysession, myrequest, myresponse, myconfig, db);
%>


<%
	//Atualiza status do anuncio no banco de dados
	final Logger log = Logger.getLogger("/webadmin/status_payment_ok.jsp");
	log.info("/webadmin/status_payment_ok.jsp");
	
	String idAnnounce = request.getParameter("idAnnounce");
	String status = request.getParameter("status");
	String idBuyer = request.getParameter("idBuyer");
	String votos = request.getParameter("votos");
	String destaque = request.getParameter("destaque");
	String pay1 = request.getParameter("pay1");
	String pay2 = request.getParameter("pay2");
	
	AnnounceDAO announceDAO = new AnnounceDAO(db, request);
	Announce anuncio = new Announce();
	anuncio.setIdAnnounce(idAnnounce);
	anuncio.setStatus(status);
	anuncio.setIdBuyer(idBuyer);
	anuncio.setRating(votos);
	anuncio.setFeatured(destaque);
	anuncio.setPaymentStatus(pay1);
	anuncio.setPaymentStatusDestaque(pay2);
	announceDAO.update(anuncio, false);
	log.info("ID: " + idAnnounce + " Status: " + status + " - alterado com sucesso!");
	response.sendRedirect("/webadmin/status_payment_ads_iliketoo.jsp");
%>



<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>