<%@ include file="../config.jsp" %><%@ page import="HardCore.Content" %><%@ page import="HardCore.Contentgroup" %><%@ page import="HardCore.Contenttype" %><%@ page import="HardCore.Page" %><%

	String output = "";
	Contentgroup mycontentgroup = new Contentgroup();
	if (! myrequest.getParameter("contentgroup").equals("")) {
		mycontentgroup.readContentgroup(db, myrequest.getParameter("contentgroup"));
	}
	Contenttype mycontenttype = new Contenttype();
	if (! myrequest.getParameter("contenttype").equals("")) {
		mycontenttype.readContenttype(db, myrequest.getParameter("contenttype"));
	}
	Page mypage = new Page();
	if (myrequest.parameterExists("id")) {
		// get template stylesheets
		if (myrequest.getParameter("id").equals("-")) {
			mypage.preview_read(servletcontext, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", "", "", "", "", "", "", "", db, myconfig, myconfig.get(db, "default_template"), "", "", "", "", "", "", "", myconfig.get(db, "default_template"), "", "", myconfig.get(db, "default_stylesheet"), "", "", "", "");
		} else if (! myrequest.getParameter("id").equals("")) {
			mypage.preview_read(servletcontext, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", "", "", "", "", "", "", "", db, myconfig, myrequest.getParameter("id"), "", "", "", "", "", "", "", myconfig.get(db, "default_template"), "", "", myconfig.get(db, "default_stylesheet"), "", "", "", "");
		} else if (! mycontentgroup.getTemplate().equals("")) {
			mypage.preview_read(servletcontext, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", "", "", "", "", "", "", "", db, myconfig, mycontentgroup.getTemplate(), "", "", "", "", "", "", "", myconfig.get(db, "default_template"), "", "", myconfig.get(db, "default_stylesheet"), "", "", "", "");
		} else if (! mycontenttype.getTemplate().equals("")) {
			mypage.preview_read(servletcontext, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", "", "", "", "", "", "", "", db, myconfig, mycontenttype.getTemplate(), "", "", "", "", "", "", "", myconfig.get(db, "default_template"), "", "", myconfig.get(db, "default_stylesheet"), "", "", "", "");
		} else {
			mypage.preview_read(servletcontext, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", "", "", "", "", "", "", "", db, myconfig, myconfig.get(db, "default_template"), "", "", "", "", "", "", "", myconfig.get(db, "default_template"), "", "", myconfig.get(db, "default_stylesheet"), "", "", "", "");
		}
		HashMap displayedstylesheet = new HashMap();
		if (mypage != null) {
			if (! output.equals("")) output += ",";
			output += mypage.outputStylesheetIds(myconfig, db, displayedstylesheet);
			if (mypage.getTemplateContent() != null) {
				if (! output.equals("")) output += ",";
				output += mypage.getTemplateContent().outputStylesheetIds(myconfig, db, displayedstylesheet);
				if (mypage.getTemplateContent().getTemplateContent() != null) {
					if (! output.equals("")) output += ",";
					output += mypage.getTemplateContent().getTemplateContent().outputStylesheetIds(myconfig, db, displayedstylesheet);
				}
			}
		}
	} else {
		// no template - just get content group/type stylesheets
		if (! mycontentgroup.getStyleSheet().equals("")) {
			output += mycontentgroup.getStyleSheet();
		} else if (! mycontenttype.getStyleSheet().equals("")) {
			output += mycontenttype.getStyleSheet();
		}
	}
	output = output.replaceAll(",+", ",");
	output = output.replaceAll("^,", "");
	output = output.replaceAll(",$", "");

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>