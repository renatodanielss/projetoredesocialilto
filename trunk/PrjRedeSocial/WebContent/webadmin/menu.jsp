<%@ include file="config.jsp" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Website" %>
<%@ page import="HardCore.Workflow" %>
<%@ page import="HardCore.UCaccessAdministration" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.UCmaintainWebsites" %>
<%
	if ((myrequest.getParameter("menu").equals("content")) && (mysession.get("admin_contentclass").equals("product"))) {
		mysession.set("admin_contentpackage", "");
		mysession.set("admin_contentclass", "");
		mysession.set("admin_contentbundle", "");
		mysession.set("admin_contentgroup", "");
		mysession.set("admin_contenttype", "");
	} else if ((myrequest.getParameter("menu").equals("ecommerce")) && (! mysession.get("admin_contentclass").equals("product")) && (! mysession.get("admin_contentclass").equals("order"))) {
		mysession.set("admin_contentpackage", "");
		mysession.set("admin_contentclass", "");
		mysession.set("admin_contentbundle", "");
		mysession.set("admin_contentgroup", "");
		mysession.set("admin_contenttype", "");
	}
	if (! myrequest.getParameter("menu").equals("")) {
		mysession.set("menu", myrequest.getParameter("menu"));
	}
%>
<% if (mysession.get("menu").equals("structure")) { %>
<jsp:include page="menu.structure.jsp" />
<% } else if (mysession.get("menu").equals("content")) { %>
<jsp:include page="menu.content.jsp" />
<% } else if (mysession.get("menu").equals("library")) { %>
<jsp:include page="menu.library.jsp" />
<% } else if (mysession.get("menu").equals("ecommerce")) { %>
<jsp:include page="menu.ecommerce.jsp" />
<% } else if (mysession.get("menu").equals("databases")) { %>
<jsp:include page="menu.databases.jsp" />
<% } else if (mysession.get("menu").equals("experience")) { %>
<jsp:include page="menu.experience.jsp" />
<% } else if (mysession.get("menu").equals("user")) { %>
<jsp:include page="menu.user.jsp" />
<% } else if (mysession.get("menu").equals("statistics")) { %>
<jsp:include page="menu.statistics.jsp" />
<% } else if (mysession.get("menu").equals("clients")) { %>
<jsp:include page="menu.clients.jsp" />
<% } else if (mysession.get("menu").startsWith("config")) { %>
<jsp:include page="menu.config.jsp" />
<% } else { %>
<%@ include file="menu.jsp.html" %>
<% } %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>