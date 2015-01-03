<%@ include file="../../config.jsp" %><%@ page import="HardCore.RequireUser" %><%@ page import="HardCore.MenuContent" %><% RequireUser.SuperAdministrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, database, mysession.get("database")); %><%

String title = "Presentation";

String menu = "";

String content = "";
content += "        <table width=\"100%\">" + "\r\n";
content += "          <tr>" + "\r\n";
content += "            <th class=\"WCMinnerContentHeading1\" align=\"left\">Presentation</th>" + "\r\n";
content += "          </tr>" + "\r\n";
content += "          <tr>" + "\r\n";
content += "            <td class=\"WCMinnerContentIntro\">Applied style sheet and template to content group/type.</td>" + "\r\n";
content += "          </tr>" + "\r\n";
content += "        </table>" + "\r\n";

if ((myrequest.getParameter("stylesheet").equals("-")) && (myrequest.getParameter("template").equals("-"))) {
	content += "<div style=\"color: #cc0000;\"><b>ERROR:</b> No style sheet or template selected.</div>\r\n";
} else {
	String SQLset = "";
	String SQLwhere = "(contentclass='page')";
	if (myrequest.getParameter("stylesheet").equals("")) {
		if (! SQLset.equals("")) SQLset += ", ";
		SQLset += " stylesheet=''";
	} else if (! myrequest.getParameter("stylesheet").equals("-")) {
		if (! SQLset.equals("")) SQLset += ", ";
		SQLset += " stylesheet=" + myrequest.getParameter("stylesheet");
	}
	if (myrequest.getParameter("template").equals("")) {
		if (! SQLset.equals("")) SQLset += ", ";
		SQLset += " template=''";
	} else if (! myrequest.getParameter("template").equals("-")) {
		if (! SQLset.equals("")) SQLset += ", ";
		SQLset += " template=" + myrequest.getParameter("template");
	}
	if (! myrequest.getParameter("contentgroup").equals("-")) {
		if (! SQLwhere.equals("")) SQLwhere += " and ";
		SQLwhere += "(contentgroup='" + myrequest.getParameter("contentgroup") + "')";
	}
	if (! myrequest.getParameter("contenttype").equals("-")) {
		if (! SQLwhere.equals("")) SQLwhere += " and ";
		SQLwhere += "(contenttype='" + myrequest.getParameter("contenttype") + "')";
	}

	PreparedStatement ps = null;
	try {
		String SQL = "update content set " + SQLset + " where " + SQLwhere;
content += "<p>" + SQL + "</p>" + "\r\n";
		ps = db.prepareStatement(SQL);
		ps.executeUpdate();
		ps.close();
		SQL = "update content_public set " + SQLset + " where " + SQLwhere;
content += "<p>" + SQL + "</p>" + "\r\n";
		ps = db.prepareStatement(SQL);
		ps.executeUpdate();
		ps.close();
	} catch (SQLException e) {
		if (ps != null) try { ps.close(); } catch (SQLException ee) { ; }
	}
}

%><%@ include file="../../module.jsp.html" %>