<%@ include file="../../config.jsp" %>
<%@ page import="HardCore.Common" %>
<%@ page import="HardCore.Currency" %>
<%@ page import="HardCore.Order" %>
<%@ page import="HardCore.UCbrowseWebsite" %>
<%@ page import="HardCore.UCpublishScheduled" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.Date" %>
<%

String paypal_transaction_type = myrequest.getParameter("txn_type");
//String paypal_invoice_id = myrequest.getParameter("invoice");
String paypal_invoice_id = myrequest.getParameter("custom");
String paypal_payment_type = myrequest.getParameter("payment_type");
String paypal_payment_status = myrequest.getParameter("payment_status");
String paypal_payment_currency = myrequest.getParameter("mc_currency");
String paypal_payment_amount = myrequest.getParameter("mc_gross");
String paypal_payment_period = myrequest.getParameter("period3");
//String paypal_txn_id = myrequest.getParameter("txn_id");
//String paypal_payer_email = myrequest.getParameter("payer_email");
//String paypal_receiver_email = myrequest.getParameter("receiver_email");


boolean paypal_deliver = false;
if (paypal_transaction_type.equals("subscr_signup")) {
	// ok - new subscriber
	paypal_deliver = true;
	paypal_payment_amount = myrequest.getParameter("mc_amount3");
	if (paypal_payment_period.equals("1 D")) {
		paypal_payment_period = "daily";
	} else if (paypal_payment_period.equals("1 W")) {
		paypal_payment_period = "weekly";
	} else if (paypal_payment_period.equals("2 W")) {
		paypal_payment_period = "biweekly";
	} else if (paypal_payment_period.equals("1 M")) {
		paypal_payment_period = "monthly";
	} else if (paypal_payment_period.equals("2 M")) {
		paypal_payment_period = "bimonthly";
	} else if (paypal_payment_period.equals("3 M")) {
		paypal_payment_period = "quarterly";
	} else if (paypal_payment_period.equals("6 M")) {
		paypal_payment_period = "biquarterly";
	} else if (paypal_payment_period.equals("1 Y")) {
		paypal_payment_period = "yearly";
	}
} else if (paypal_transaction_type.equals("subscr_payment")) {
	// ignore - payment from existing subscriber
} else if ((! paypal_payment_type.equals("echeck")) && (paypal_payment_status.equals("Completed"))) {
	// ok - instant payment
	paypal_deliver = true;
} else {
	// echeck
	if (myconfig.get(db, "paypal_echeck").equals("Pending")) {
		// accept if pending
		if (paypal_payment_status.equals("Pending")) {
			// ok - echeck pending
			paypal_deliver = true;
		} else {
			// ignore - already handled when pending
			paypal_deliver = false;
		}
	} else {
		// accept when completed
		if (paypal_payment_status.equals("Completed")) {
			// ok - echeck completed
			paypal_deliver = true;
		} else {
			// wait - echeck pending
			paypal_deliver = false;
		}
	}
}


if (paypal_deliver) {
	Enumeration en = request.getParameterNames();
	String req = "cmd=_notify-validate";
	while(en.hasMoreElements()){
		String paramName = (String)en.nextElement();
		String paramValue = request.getParameter(paramName);
		req += "&" + paramName + "=" + URLEncoder.encode(paramValue);
	}
	URL u = new URL("http://www.paypal.com/cgi-bin/webscr");
	URLConnection uc = u.openConnection();
	uc.setDoOutput(true);
	uc.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	PrintWriter pw = new PrintWriter(uc.getOutputStream());
	pw.println(req);
	pw.close();
	BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
	String res = in.readLine();
	in.close();
	if (res.equals("VERIFIED")) {
		Order my_order = new Order();
		my_order.read(db, paypal_invoice_id);
		Currency my_currency = new Currency();
		my_currency.readSymbol(db, my_order.getOrderCurrency());
		if ((my_order.getPaid().equals("")) && (paypal_payment_currency.equals(my_currency.getTitle())) && ((paypal_payment_amount.equals(my_order.getOrderTotal())) || (paypal_payment_amount.equals(Common.numberformat(my_order.getOrderTotal(),2))))) {
			if (paypal_transaction_type.equals("subscr_signup")) {
				if (my_order.orderitems(db)) {
					if (paypal_payment_period.equals(my_order.getOrderitem().getProductPeriod())) {
						if (! my_order.orderitems(db)) {
							my_order.setPaid(Common.strftime("%Y-%m-%d %H:%M:%S", new Date()));
							my_order.update(db);
							while (my_order.orderitems(db)) {
								// nothing
							}
							UCbrowseWebsite browseWebsite = new UCbrowseWebsite(mytext);
							browseWebsite.doDeliver(my_order, website, servletcontext, inifile, mysession, myrequest, myresponse, myconfig, db, original_database);
						}
					}
				}
			} else if (paypal_transaction_type.equals("subscr_payment")) {
				// ignore - payment from existing subscriber
			} else {
				my_order.setPaid(Common.strftime("%Y-%m-%d %H:%M:%S", new Date()));
				my_order.update(db);
				UCbrowseWebsite browseWebsite = new UCbrowseWebsite(mytext);
				browseWebsite.doDeliver(my_order, website, servletcontext, inifile, mysession, myrequest, myresponse, myconfig, db, original_database);
			}
		}
	}
}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>