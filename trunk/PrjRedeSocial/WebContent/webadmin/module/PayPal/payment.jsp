<%@ include file="../../config.jsp" %><%@ page import="HardCore.Common" %><%@ page import="HardCore.Currency" %><%@ page import="HardCore.Order" %><%

Order order = (Order)myrequest.getAttribute("order");
if (order == null) order = new Order();

String my_output = "";
String my_orderitems = "";
String my_output_subscriptions = "";
if ((Common.number(order.getOrderTotal()) > 0) && (order.getPaid().equals("")) && (! myconfig.get(db, "paypal_email").equals(""))) {
	Currency my_currency = new Currency();
	my_currency.readSymbol(db, order.getOrderCurrency());
	my_output += "<form target=\"_blank\" id=\"paypal\" name=\"paypal\" method=\"" + myconfig.get(db, "paypal_method") + "\" action=\"https://www.paypal.com/cgi-bin/webscr\">" + "\r\n";
	my_output += myconfig.get(db, "paypal_text") + "\r\n";
	my_output += "<input name=\"bn\" value=\"asbrusoft.com\" type=\"hidden\">" + "\r\n";
	my_output += "<input name=\"mrb\" value=\"SRD5TKTJUGFKJ\" type=\"hidden\">" + "\r\n";
	my_output += "<input name=\"business\" value=\"" + myconfig.get(db, "paypal_email") + "\" type=\"hidden\">" + "\r\n";


// pre-populate new account registration
	my_output += "<input name=\"redirect_cmd\" value=\"_cart\" type=\"hidden\">" + "\r\n";
	String first_name = order.getCardName();
	String last_name = order.getCardName();
	if (order.getCardName().lastIndexOf(" ") > 0) {
		first_name = order.getCardName().substring(0, order.getCardName().lastIndexOf(" "));
		last_name = order.getCardName().substring(order.getCardName().lastIndexOf(" ")+1);
	}
	my_output += "<input name=\"first_name\" value=\"" + first_name + "\" type=\"hidden\">" + "\r\n";
	my_output += "<input name=\"last_name\" value=\"" + last_name + "\" type=\"hidden\">" + "\r\n";
	String[] address = order.getInvoiceAddress().split("[\r\n]+");
	if (address.length < 2) {
		address = Common.array_redim(address, 2);
	}
	my_output += "<input name=\"address1\" value=\"" + address[0] + "\" type=\"hidden\">" + "\r\n";
	my_output += "<input name=\"address2\" value=\"" + address[1] + "\" type=\"hidden\">" + "\r\n";
	my_output += "<input name=\"city\" value=\"" + order.getInvoiceCity() + "\" type=\"hidden\">" + "\r\n";
	my_output += "<input name=\"state\" value=\"" + order.getInvoiceState() + "\" type=\"hidden\">" + "\r\n";
	my_output += "<input name=\"zip\" value=\"" + order.getInvoicePostalcode() + "\" type=\"hidden\">" + "\r\n";
//phone numbers no longer working after PayPal changes March 2007
//	my_output += "<input name=\"night_phone_a\" value=\"" + order.getInvoicePhone() + "\" type=\"hidden\">" + "\r\n";
//	my_output += "<input name=\"night_phone_b\" value=\"" + order.getInvoicePhone() + "\" type=\"hidden\">" + "\r\n";
//	my_output += "<input name=\"night_phone_c\" value=\"" + order.getInvoicePhone() + "\" type=\"hidden\">" + "\r\n";
//	my_output += "<input name=\"day_phone_a\" value=\"" + order.getInvoicePhone() + "\" type=\"hidden\">" + "\r\n";
//	my_output += "<input name=\"day_phone_b\" value=\"" + order.getInvoicePhone() + "\" type=\"hidden\">" + "\r\n";
//	my_output += "<input name=\"day_phone_c\" value=\"" + order.getInvoicePhone() + "\" type=\"hidden\">" + "\r\n";
// force use of posted address instead of registered address
//	my_output += "<input name=\"address_override\" value=\"1\" type=\"hidden\">" + "\r\n";


// passthrough to IPN
	my_output += "<input name=\"invoice\" value=\"" + Common.strftime("%Y-%m-%d %H:%M:%S", new java.util.Date()) + " " + order.getId() + "\" type=\"hidden\">" + "\r\n";
	my_output += "<input name=\"custom\" value=\"" + order.getId() + "\" type=\"hidden\">" + "\r\n";

	if (myconfig.get(db, "paypal_cart").equals("details")) { // pass individual order items to PayPal
//		my_output += "<input name=\"cmd\" value=\"_cart\" type=\"hidden\">" + "\r\n";
		my_output += "<input name=\"cmd\" value=\"_ext-enter\" type=\"hidden\">" + "\r\n";
		my_output += "<input name=\"upload\" value=\"1\" type=\"hidden\">" + "\r\n";
//		my_output += "<input name=\"display\" value=\"1\" type=\"hidden\">" + "\r\n";
		int my_orderitem = 0;
		while (order.orderitems(db)) {
			if (order.getOrderitem().getProductPeriod().equals("")) {
				my_orderitem += 1;
				my_orderitems += "<input name=\"item_name_" + my_orderitem + "\" value=\"" + order.getOrderitem().getTitle() + "\" type=\"hidden\">" + "\r\n";
				my_orderitems += "<input name=\"amount_" + my_orderitem + "\" value=\"" + Common.numberformat(order.getOrderitem().getProductPrice(),2) + "\" type=\"hidden\">" + "\r\n";
				my_orderitems += "<input name=\"quantity_" + my_orderitem + "\" value=\"" + order.getOrderitem().getItemQuantity() + "\" type=\"hidden\">" + "\r\n";
				// per order item handling shipping and tax
				//my_orderitems += "<input name=\"handling_" + my_orderitem + "\" value=\"" + Common.numberformat(order.getOrderitem().getXXXXX(),2) + "\" type=\"hidden\">" + "\r\n";
				//my_orderitems += "<input name=\"shipping_" + my_orderitem + "\" value=\"" + Common.numberformat(order.getOrderitem().getXXXXX(),2) + "\" type=\"hidden\">" + "\r\n";
				//my_orderitems += "<input name=\"tax_" + my_orderitem + "\" value=\"" + Common.numberformat(order.getOrderitem().getXXXXX(),2) + "\" type=\"hidden\">" + "\r\n";
				// order item option fields - one or two options per order item
				//my_orderitems += "<input name=\"on0_" + my_orderitem + "\" value=\"" + order.getOrderitem().getXXXXX() + "\" type=\"hidden\">" + "\r\n";
				//my_orderitems += "<input name=\"os0_" + my_orderitem + "\" value=\"" + order.getOrderitem().getXXXXX() + "\" type=\"hidden\">" + "\r\n";
				//my_orderitems += "<input name=\"on1_" + my_orderitem + "\" value=\"" + order.getOrderitem().getXXXXX() + "\" type=\"hidden\">" + "\r\n";
				//my_orderitems += "<input name=\"os1_" + my_orderitem + "\" value=\"" + order.getOrderitem().getXXXXX() + "\" type=\"hidden\">" + "\r\n";
			} else {
				// SUBSCRIPTION
				my_output_subscriptions += "<div><a target=\"_blank\" href=\"https://www.paypal.com/cgi-bin/webscr?cmd=_xclick-subscriptions&business=" + myconfig.get(db, "paypal_email") + "&invoice=" + URLEncoder.encode(Common.strftime("%Y-%m-%d %H:%M:%S", new java.util.Date()) + " " + order.getId()) + "&custom=" + order.getId() + "&item_name=" + order.getOrderitem().getTitle() + "&no_shipping=1&no_note=1&bn=asbrusoft.com&mrb=SRD5TKTJUGFKJ&currency_code=" + my_currency.getTitle() + "&charset=UTF%2d8&a3=" + Common.numberformat(order.getOrderitem().getProductPrice(),2) + "&src=1&sra=1";
				if (order.getOrderitem().getProductPeriod().equals("daily")) {
					my_output_subscriptions += "&p3=1&t3=D";
				} else if (order.getOrderitem().getProductPeriod().equals("weekly")) {
					my_output_subscriptions += "&p3=1&t3=W";
				} else if (order.getOrderitem().getProductPeriod().equals("biweekly")) {
					my_output_subscriptions += "&p3=2&t3=W";
				} else if (order.getOrderitem().getProductPeriod().equals("monthly")) {
					my_output_subscriptions += "&p3=1&t3=M";
				} else if (order.getOrderitem().getProductPeriod().equals("bimonthly")) {
					my_output_subscriptions += "&p3=2&t3=M";
				} else if (order.getOrderitem().getProductPeriod().equals("quarterly")) {
					my_output_subscriptions += "&p3=3&t3=M";
				} else if (order.getOrderitem().getProductPeriod().equals("biquarterly")) {
					my_output_subscriptions += "&p3=6&t3=M";
				} else if (order.getOrderitem().getProductPeriod().equals("yearly")) {
					my_output_subscriptions += "&p3=1&t3=Y";
				}
				if (myconfig.get(db, "payment_return_success").matches("^[0-9]+$")) {
					my_output_subscriptions += "&return=" + URLEncoder.encode(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/page.jsp?id=" + myconfig.get(db, "payment_return_success"));
				} else {
					my_output_subscriptions += "&return=" + URLEncoder.encode(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + myconfig.get(db, "payment_return_success"));
				}
				if (myconfig.get(db, "payment_return_cancel").matches("^[0-9]+$")) {
					my_output_subscriptions += "&cancel_return=" + URLEncoder.encode(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/page.jsp?id=" + myconfig.get(db, "payment_return_cancel"));
				} else {
					my_output_subscriptions += "&cancel_return=" + URLEncoder.encode(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + myconfig.get(db, "payment_return_cancel"));
				}
				my_output_subscriptions += "&notify_url=" + URLEncoder.encode(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + mytext.display("adminpath") + "/module/" + myconfig.get(db, "payment_provider") + "/delivery.jsp");
				my_output_subscriptions += "\">";
				my_output_subscriptions += order.getOrderitem().getTitle() + "<br>" + "\r\n";
				my_output_subscriptions += "<img src=\"http://images.paypal.com/images/x-click-but6.gif\" alt=\"Click here to pay - Make payments with PayPal - it's fast, free and secure!\" border=\"0\">" + "\r\n";
				my_output_subscriptions += "</a></div>" + "\r\n";
			}
		}
		my_output += my_orderitems;
	} else { // pass order items to PayPal as a single combined order item
		my_output += "<input name=\"cmd\" value=\"_xclick\" type=\"hidden\">" + "\r\n";
		my_orderitems = "";
		while (order.orderitems(db)) {
			if (order.getOrderitem().getProductPeriod().equals("")) {
				if (! my_orderitems.equals("")) {
					my_orderitems += ", ";
				}
				my_orderitems += order.getOrderitem().getTitle();
			} else {
				// SUBSCRIPTION
				my_output_subscriptions += "<div><a target=\"_blank\" href=\"https://www.paypal.com/cgi-bin/webscr?cmd=_xclick-subscriptions&business=" + myconfig.get(db, "paypal_email") + "&invoice=" + URLEncoder.encode(Common.strftime("%Y-%m-%d %H:%M:%S", new java.util.Date()) + " " + order.getId()) + "&custom=" + order.getId() + "&item_name=" + order.getOrderitem().getTitle() + "&no_shipping=1&no_note=1&bn=asbrusoft.com&mrb=SRD5TKTJUGFKJ&currency_code=" + my_currency.getTitle() + "&charset=UTF%2d8&a3=" + Common.numberformat(order.getOrderitem().getProductPrice(),2) + "&src=1&sra=1";
				if (order.getOrderitem().getProductPeriod().equals("daily")) {
					my_output_subscriptions += "&p3=1&t3=D";
				} else if (order.getOrderitem().getProductPeriod().equals("weekly")) {
					my_output_subscriptions += "&p3=1&t3=W";
				} else if (order.getOrderitem().getProductPeriod().equals("biweekly")) {
					my_output_subscriptions += "&p3=2&t3=W";
				} else if (order.getOrderitem().getProductPeriod().equals("monthly")) {
					my_output_subscriptions += "&p3=1&t3=M";
				} else if (order.getOrderitem().getProductPeriod().equals("bimonthly")) {
					my_output_subscriptions += "&p3=2&t3=M";
				} else if (order.getOrderitem().getProductPeriod().equals("quarterly")) {
					my_output_subscriptions += "&p3=3&t3=M";
				} else if (order.getOrderitem().getProductPeriod().equals("biquarterly")) {
					my_output_subscriptions += "&p3=6&t3=M";
				} else if (order.getOrderitem().getProductPeriod().equals("yearly")) {
					my_output_subscriptions += "&p3=1&t3=Y";
				}
				if (myconfig.get(db, "payment_return_success").matches("^[0-9]+$")) {
					my_output_subscriptions += "&return=" + URLEncoder.encode(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/page.jsp?id=" + myconfig.get(db, "payment_return_success"));
				} else {
					my_output_subscriptions += "&return=" + URLEncoder.encode(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + myconfig.get(db, "payment_return_success"));
				}
				if (myconfig.get(db, "payment_return_cancel").matches("^[0-9]+$")) {
					my_output_subscriptions += "&cancel_return=" + URLEncoder.encode(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/page.jsp?id=" + myconfig.get(db, "payment_return_cancel"));
				} else {
					my_output_subscriptions += "&cancel_return=" + URLEncoder.encode(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + myconfig.get(db, "payment_return_cancel"));
				}
				my_output_subscriptions += "&notify_url=" + URLEncoder.encode(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + mytext.display("adminpath") + "/module/" + myconfig.get(db, "payment_provider") + "/delivery.jsp");
				my_output_subscriptions += "\">";
				my_output_subscriptions += order.getOrderitem().getTitle() + "<br>" + "\r\n";
				my_output_subscriptions += "<img src=\"http://images.paypal.com/images/x-click-but6.gif\" alt=\"Click here to pay - Make payments with PayPal - it's fast, free and secure!\" border=\"0\">" + "\r\n";
				my_output_subscriptions += "</a></div>" + "\r\n";
			}
		}
		my_output += "<input name=\"item_name\" value=\"" + my_orderitems + "\" type=\"hidden\">" + "\r\n";
		my_output += "<input name=\"amount\" value=\"" + Common.numberformat(order.getOrderTotal(),2) + "\" type=\"hidden\">" + "\r\n";
	}
	my_output += "<input name=\"currency_code\" value=\"" + my_currency.getTitle() + "\" type=\"hidden\">" + "\r\n";
	my_output += "<input name=\"handling_cart\" value=\"" + Common.numberformat(order.getShippingTotal(),2) + "\" type=\"hidden\">" + "\r\n";
	my_output += "<input name=\"tax_cart\" value=\"" + Common.numberformat(order.getTaxTotal(),2) + "\" type=\"hidden\">" + "\r\n";
	if (myconfig.get(db, "payment_return_success").matches("^[0-9]+$")) {
		my_output += "<input name=\"return\" value=\"" + myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/page.jsp?id=" + myconfig.get(db, "payment_return_success") + "\" type=\"hidden\">" + "\r\n";
	} else {
		my_output += "<input name=\"return\" value=\"" + myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + myconfig.get(db, "payment_return_success") + "\" type=\"hidden\">" + "\r\n";
	}
	if (myconfig.get(db, "payment_return_cancel").matches("^[0-9]+$")) {
		my_output += "<input name=\"cancel_return\" value=\"" + myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/page.jsp?id=" + myconfig.get(db, "payment_return_cancel") + "\" type=\"hidden\">" + "\r\n";
	} else {
		my_output += "<input name=\"cancel_return\" value=\"" + myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + myconfig.get(db, "payment_return_cancel") + "\" type=\"hidden\">" + "\r\n";
	}
	my_output += "<input name=\"notify_url\" value=\"" + myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + mytext.display("adminpath") + "/module/" + myconfig.get(db, "payment_provider") + "/delivery.jsp" + "\" type=\"hidden\">" + "\r\n";
//	my_output += "<input name=\"submit\" src=\"http://images.paypal.com/images/x-click-butcc.gif\" alt=\"Make payments with PayPal - it's fast, free and secure!\" border=\"0\" type=\"image\">" + "\r\n";
	my_output += "<input name=\"submit\" src=\"http://images.paypal.com/images/x-click-but6.gif\" alt=\"Make payments with PayPal - it's fast, free and secure!\" border=\"0\" type=\"image\">" + "\r\n";
	my_output += "</form>" + "\r\n";
	if (my_orderitems.equals("")) {
		my_output = "";
	}
	if (my_output.equals("")) {
		my_output += myconfig.get(db, "paypal_text") + "\r\n";
	}
	my_output += my_output_subscriptions;
}
mysession.set("payment", my_output);

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>