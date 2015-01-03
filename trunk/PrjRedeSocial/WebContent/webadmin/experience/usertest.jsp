<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCreportUsertest" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.Usertest" %>
<%@ page import="HardCore.MenuContent" %>
<%

	Usertest usertest = new Usertest();
	usertest.readUsertest(db, myrequest.getParameter("usertest"));
	UCreportUsertest reportUsertest = new UCreportUsertest(mytext);
	Page mypage = new Page();
	HashMap data = reportUsertest.getUsertest(usertest, mysession, myrequest, myresponse, myconfig, db, logdb, database);
	HashMap total = (HashMap)data.get("total"); if (total == null) total = new HashMap();
	HashMap usage = (HashMap)data.get("usage"); if (usage == null) usage = new HashMap();
	HashMap visitors = (HashMap)data.get("visitors"); if (visitors == null) visitors = new HashMap();
	HashMap conversions = (HashMap)data.get("conversions"); if (conversions == null) conversions = new HashMap();
	HashMap rate = (HashMap)data.get("rate"); if (rate == null) rate = new HashMap();
	HashMap improvement = (HashMap)data.get("improvement"); if (improvement == null) improvement = new HashMap();
	HashMap confidence = (HashMap)data.get("confidence"); if (confidence == null) confidence = new HashMap();
	HashMap maxconfidence = (HashMap)data.get("maxconfidence"); if (maxconfidence == null) maxconfidence = new HashMap();
	HashMap totalvisitors = (HashMap)data.get("totalvisitors"); if (totalvisitors == null) totalvisitors = new HashMap();
	HashMap totalconversions = (HashMap)data.get("totalconversions"); if (totalconversions == null) totalconversions = new HashMap();
	HashMap totalrate = (HashMap)data.get("totalrate"); if (totalrate == null) totalrate = new HashMap();

%>
<%@ include file="usertest.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>