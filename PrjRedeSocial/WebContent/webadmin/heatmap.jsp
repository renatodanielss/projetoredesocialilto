<%@ include file="config.jsp" %>
<% if ((mysession.get("mode").equals("heatmap")) && (mysession.get("administrator").equals("administrator"))) { %>
window.onload = function() {
	var mywidth = Math.max(document.documentElement.clientWidth, document.body.scrollWidth, document.documentElement.scrollWidth, document.body.offsetWidth, document.documentElement.offsetWidth);
	var myheight = Math.max(document.documentElement.clientHeight, document.body.scrollHeight, document.documentElement.scrollHeight, document.body.offsetHeight, document.documentElement.offsetHeight);
	var heatmap = h337.create({ element: document.body, width: mywidth, height: myheight });
	var m = function(x,y,w,h,count) {
		if (!count) count=1;
<% if (myrequest.getParameter("align").equals("l")) { // left %>
		var left = x;
		var top = y;
<% } else if (myrequest.getParameter("align").equals("r")) { // right %>
		var left = Math.abs(mywidth-(w-x));
		var top = Math.abs(myheight-(h-y));
<% } else if (myrequest.getParameter("align").equals("f")) { // full %>
		var left = Math.abs((mywidth/w)*x);
		var top = Math.abs((myheight/h)*y);
<% } else { // center %>
		var left = Math.abs((mywidth/2)-((w/2)-x));
		var top = Math.abs((myheight/2)-((h/2)-y));
<% } %>
		heatmap.store.addDataPoint(left,top,count);
	}
<%
	String SQLwhere = "id=" + db.quote(Common.SQL_clean(myrequest.getParameter("id")));
	if ((! myrequest.getParameter("heatmap_start").equals("")) && (! myrequest.getParameter("heatmap_start").equals("null"))) {
		SQLwhere += "and (logged >= " + db.quote(Common.SQL_clean(myrequest.getParameter("heatmap_start"))) + ")";
	}
	if ((! myrequest.getParameter("heatmap_end").equals("")) && (! myrequest.getParameter("heatmap_end").equals("null"))) {
		SQLwhere += "and (logged <= " + db.quote(Common.SQL_clean(myrequest.getParameter("heatmap_end"))) + ")";
	}
	String SQL = "select x,y,w,h,count(*) as q from heatmaps where " + SQLwhere + " group by x,y,w,h";
	Statement st = db.records(SQL);
	HashMap record = db.records(st);
	while (record != null) {
%>
	m(<%= record.get("x") %>,<%= record.get("y") %>,<%= record.get("w") %>,<%= record.get("h") %>,<%= record.get("q") %>);
<%
		record = db.records(st);
	}
	db.closeRecords(st);
%>
};
<% } %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>