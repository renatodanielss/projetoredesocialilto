<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainData" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Data" %>
<%@ page import="HardCore.Databases" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%@ page import="HardCore.http" %>
<%@ page import="HardCore.Common" %>
<%@ include file="../webeditor/webeditor.jsp" %>
<%
	UCmaintainData maintainData = new UCmaintainData(mytext);
	Data data = maintainData.getCreate(mysession, myrequest, myresponse, myconfig, db);
	Content content = new Content(mytext);
	Page mypage = new Page(mytext);

	String myurlhost = "" + myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort();
	String myurlscript = "/" + mytext.display("adminpath") + "/content/stylesheets.jsp";
	String myurlquery = "id=" + mypage.getTemplate() + "&" + Math.random();
	String template_stylesheets = "" + http.get(myurlhost, myurlscript, myurlquery, myrequest);
	String stylesheet = "";
	if (mypage.getStyleSheet().equals("0")) {
		stylesheet = "";
	} else if ((! mypage.getStyleSheet().equals("")) && (! template_stylesheets.equals(""))) {
		stylesheet = "/stylesheet.jsp?id=" + template_stylesheets + "," + mypage.getStyleSheet();
	} else if (! template_stylesheets.equals("")) {
		stylesheet = "/stylesheet.jsp?id=" + template_stylesheets;
	} else if (! mypage.getStyleSheet().equals("")) {
		stylesheet = "/stylesheet.jsp?id=" + mypage.getStyleSheet();
	} else {
		stylesheet = "/stylesheet.jsp?id=" + myconfig.get(db, "default_stylesheet");
	}

	mysession.set("mode", "preview");
	if (! mypage.getScheduledPublish().equals("")) {
		mysession.set("preview_scheduled", mypage.getScheduledPublish());
	} else if (! mypage.getRequestedPublish().equals("")) {
		mysession.set("preview_scheduled", mypage.getRequestedPublish());
	} else {
		mysession.set("preview_scheduled", "" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
	}

%>
<%@ include file="create.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>