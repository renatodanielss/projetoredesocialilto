<%@ include file="webadmin.jsp" %><%@ page import="com.iliketo.dao.MemberDAO" %><%@ page import="com.iliketo.model.Member" %>
<%@ page import="com.iliketo.dao.GenericDAO" %><%

/*MemberDAO memberDao = new MemberDAO(db, request);
Member member = new Member();
member = (Member) memberDao.readByColumn("username", myrequest.getParameter("user"), Member.class);

if (member.getActivated().equals(myrequest.getParameter("activationkey")) && !member.getActivated().equals("0")){
	Boolean verdadeiro = member.getActivated() == "0";
	System.out.println("Usu�rio link:" + myrequest.getParameter("user"));
	System.out.println("Ativado = " + member.getActivated() + "\nid = " + member.getEmail());
	System.out.println("Verdadeiro ou falso: " + verdadeiro.toString());
	member.setActivated("0");
	memberDao.update(member);*/
	
	String report = myrequest.getParameter("report");
	String message = myrequest.getParameter("message");
	
	System.out.println("Teste");
	System.out.println("Report: " + report);
	System.out.println("Message: " + message);
	System.out.println("Username: " + myrequest.getParameter("username"));
	
	%>
	

<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>