<%@ include file="../config.jsp" %><%@ page import="HardCore.UCmaintainOrders" %><%@ page import="HardCore.Fileupload" %><%@ page import="HardCore.Order" %><%@ page import="HardCore.Content" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.Workflow" %><%@ page import="HardCore.MenuContent" %><%@ page import="HardCore.html" %><%@ page import="HardCore.Common" %><%

UCmaintainOrders maintainOrders = new UCmaintainOrders(mytext);
Order order = maintainOrders.getView(mysession, myrequest, myresponse, myconfig, db);
if (myrequest.getParameter("id").equals("")) {
	order.setId("-");
}
if (((myrequest.getParameter("workflow").equals("")) || (myrequest.getParameter("workflow").equals("0"))) && (order.getStatus().equals(""))) {
	%><%= adminuser.select_options_where(db, order.getCheckedOut(), "where userclass='administrator'") %><%
} else {
	order.getForm(myrequest);
	Workflow workflow = new Workflow(mytext);
	if (! myrequest.getParameter("workflow").equals("")) {
		workflow.read(db, myrequest.getParameter("workflow"));
	} else {
		workflow.setId("-");
		workflow.setToState(order.getStatus());
	}

	order.setStatus(workflow.getToState());

	Page mypage = new Page();
	mypage.setId("-");
	mypage.setContentClass("-order-");
	mypage.setContentGroup("");
	mypage.setContentType("");
	mypage.setVersion("");
	mypage.setStatus(order.getStatus());
	mypage.setCheckedOut(order.getCheckedOut());
	mypage.setUsersGroup(myconfig.get(db, "order_admin_users_group"));
	mypage.setUsersType(myconfig.get(db, "order_admin_users_type"));
	mypage.setCreatorsGroup(myconfig.get(db, "order_admin_users_group"));
	mypage.setCreatorsType(myconfig.get(db, "order_admin_users_type"));
	mypage.setDevelopersGroup(myconfig.get(db, "order_admin_users_group"));
	mypage.setDevelopersType(myconfig.get(db, "order_admin_users_type"));
	mypage.setEditorsGroup(myconfig.get(db, "order_admin_users_group"));
	mypage.setEditorsType(myconfig.get(db, "order_admin_users_type"));
	mypage.setPublishersGroup(myconfig.get(db, "order_admin_users_group"));
	mypage.setPublishersType(myconfig.get(db, "order_admin_users_type"));
	mypage.setAdministratorsGroup(myconfig.get(db, "order_admin_users_group"));
	mypage.setAdministratorsType(myconfig.get(db, "order_admin_users_type"));

	String[] adminusernames = workflow.adminUsernames(mypage, mysession, myconfig, db);
	java.util.Arrays.sort(adminusernames);
	for (int i=0; i<adminusernames.length; i++) {
		%><option value="<%= adminusernames[i] %>"><%= adminusernames[i] %></option><%
	}
}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>