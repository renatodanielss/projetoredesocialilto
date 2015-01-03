<%

	UsageLog usagelog = new UsageLog();
	usagelog.summarise(out, mytext, db, logdb, myconfig, (! myrequest.getParameter("test").equals("")), (! myrequest.getParameter("commit").equals("")), (! myrequest.getParameter("all").equals("")), (! myrequest.getParameter("force").equals("")));

%>