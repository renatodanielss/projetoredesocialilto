<%@ include file="../config.jsp" %><%@ page import="HardCore.RequireUser" %><%@ page import="HardCore.MenuContent" %><%@ page import="HardCore.Content" %><%

	RequireUser.SuperAdministrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));

	%><%= "<p>Updating content dependencies ...</p>" + "\r\n" %><%

	%><%= "<p>content</p>" + "\r\n" %><%
	String SQL = "select id from content order by id";
	LinkedHashMap contentitems = db.query_records(SQL);
	Iterator i = contentitems.keySet().iterator();
	while (i.hasNext()) {
		String id = "" + i.next();
		HashMap contentitem = (HashMap)contentitems.get(id);
		Content content = new Content(mytext);
		content.preview_read(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, id, "", "", "");
		if (id.equals(content.getId())) {
			%><%= id + " - " + content.getTitle() + "<br>" + "\r\n" %><%
			out.flush();
			response.flushBuffer();
			content.dependencies(db, myconfig, id, "content", true);
		}
	}

	%><%= "<p>content_public</p>" + "\r\n" %><%
	SQL = "select id from content_public order by id";
	contentitems = db.query_records(SQL);
	i = contentitems.keySet().iterator();
	while (i.hasNext()) {
		String id = "" + i.next();
		HashMap contentitem = (HashMap)contentitems.get(id);
		Content content = new Content(mytext);
		content.public_read(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, id, "", "", "");
		if (id.equals(content.getId())) {
			%><%= id + " - " + content.getTitle() + "<br>" + "\r\n" %><%
			out.flush();
			response.flushBuffer();
			content.dependencies(db, myconfig, id, "content_public", true);
		}
	}

	%><%= "<p>Done.</p>" + "\r\n" %><%

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>