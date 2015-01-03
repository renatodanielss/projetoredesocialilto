<%@ include file="../../webadmin.jsp" %><%@ page import="HardCore.User" %><%

String output = "";
String username = mysession.get("username");
String attribute = mysession.get("extension");
if ((myrequest.getAttribute("extension") != null) && (! myrequest.getAttribute("extension").equals(""))) {
	attribute = ""+myrequest.getAttribute("extension");
} else {
	attribute = mysession.get("extension");
}

if (! mysession.get("user_extension").equals(username)) {
	mysession.set("user_extension", username);
	User user = new User(mytext);
	user.readByUsername(myconfig.get(db, "superadmin"), "", "", "", "", db, myconfig, username);
	mysession.set("user_organisation", user.getOrganisation());
	mysession.set("user_created", user.getCreated());
	mysession.set("user_updated", user.getUpdated());
	mysession.set("user_scheduled_publish", user.getScheduledPublish());
	mysession.set("user_scheduled_notify", user.getScheduledNotify());
	mysession.set("user_scheduled_unpublish", user.getScheduledUnpublish());
	mysession.set("user_card_type", user.getCardType());
	mysession.set("user_card_number", user.getCardNumber());
	mysession.set("user_card_issuedmonth", user.getCardIssuedMonth());
	mysession.set("user_card_issuedyear", user.getCardIssuedYear());
	mysession.set("user_card_expirymonth", user.getCardExpiryMonth());
	mysession.set("user_card_expiryyear", user.getCardExpiryYear());
	mysession.set("user_card_name", user.getCardName());
	mysession.set("user_card_cvc", user.getCardCVC());
	mysession.set("user_card_issue", user.getCardIssue());
	mysession.set("user_card_postalcode", user.getCardPostalcode());
	mysession.set("user_delivery_name", user.getDeliveryName());
	mysession.set("user_delivery_organisation", user.getDeliveryOrganisation());
	mysession.set("user_delivery_address", user.getDeliveryAddress());
	mysession.set("user_delivery_postalcode", user.getDeliveryPostalcode());
	mysession.set("user_delivery_city", user.getDeliveryCity());
	mysession.set("user_delivery_state", user.getDeliveryState());
	mysession.set("user_delivery_country", user.getDeliveryCountry());
	mysession.set("user_delivery_phone", user.getDeliveryPhone());
	mysession.set("user_delivery_fax", user.getDeliveryFax());
	mysession.set("user_delivery_email", user.getDeliveryEmail());
	mysession.set("user_delivery_website", user.getDeliveryWebsite());
	mysession.set("user_invoice_name", user.getInvoiceName());
	mysession.set("user_invoice_organisation", user.getInvoiceOrganisation());
	mysession.set("user_invoice_address", user.getInvoiceAddress());
	mysession.set("user_invoice_postalcode", user.getInvoicePostalcode());
	mysession.set("user_invoice_city", user.getInvoiceCity());
	mysession.set("user_invoice_state", user.getInvoiceState());
	mysession.set("user_invoice_country", user.getInvoiceCountry());
	mysession.set("user_invoice_phone", user.getInvoicePhone());
	mysession.set("user_invoice_fax", user.getInvoiceFax());
	mysession.set("user_invoice_email", user.getInvoiceEmail());
	mysession.set("user_invoice_website", user.getInvoiceWebsite());
	mysession.set("user_notes", user.getNotes());
}

if (attribute.equals("username")) {
	output += mysession.get("username");
//} else if (attribute.equals("password")) {
//	output += mysession.get("password");
} else if (attribute.equals("name")) {
	output += mysession.get("name");
} else if (attribute.equals("email")) {
	output += mysession.get("email");
} else if (attribute.equals("group")) {
	output += mysession.get("usergroup");
} else if (attribute.equals("type")) {
	output += mysession.get("usertype");
} else if (attribute.equals("groups")) {
	output += mysession.get("usergroups");
} else if (attribute.equals("types")) {
	output += mysession.get("usertypes");

} else if (attribute.equals("organisation")) {
	output += mysession.get("user_organisation");
} else if (attribute.equals("created")) {
	output += mysession.get("user_created");
} else if (attribute.equals("updated")) {
	output += mysession.get("user_updated");
} else if (attribute.equals("activation")) {
	output += mysession.get("user_scheduled_publish");
} else if (attribute.equals("scheduled_publish")) {
	output += mysession.get("user_scheduled_publish");
} else if (attribute.equals("notification")) {
	output += mysession.get("user_scheduled_notify");
} else if (attribute.equals("scheduled_notify")) {
	output += mysession.get("user_scheduled_notify");
} else if (attribute.equals("expiration")) {
	output += mysession.get("user_scheduled_unpublish");
} else if (attribute.equals("scheduled_unpublish")) {
	output += mysession.get("user_scheduled_unpublish");
} else if (attribute.equals("card_type")) {
	output += mysession.get("user_card_type");
} else if (attribute.equals("card_number")) {
	output += mysession.get("user_card_number");
} else if (attribute.equals("card_issuedmonth")) {
	output += mysession.get("user_card_issuedmonth");
} else if (attribute.equals("card_issuedyear")) {
	output += mysession.get("user_card_issuedyear");
} else if (attribute.equals("card_expirymonth")) {
	output += mysession.get("user_card_expirymonth");
} else if (attribute.equals("card_expiryyear")) {
	output += mysession.get("user_card_expiryyear");
} else if (attribute.equals("card_name")) {
	output += mysession.get("user_card_name");
} else if (attribute.equals("card_cvc")) {
	output += mysession.get("user_card_cvc");
} else if (attribute.equals("card_issue")) {
	output += mysession.get("user_card_issue");
} else if (attribute.equals("card_postalcode")) {
	output += mysession.get("user_card_postalcode");
} else if (attribute.equals("delivery_name")) {
	output += mysession.get("user_delivery_name");
} else if (attribute.equals("delivery_organisation")) {
	output += mysession.get("user_delivery_organisation");
} else if (attribute.equals("delivery_address")) {
	output += mysession.get("user_delivery_address");
} else if (attribute.equals("delivery_postalcode")) {
	output += mysession.get("user_delivery_postalcode");
} else if (attribute.equals("delivery_city")) {
	output += mysession.get("user_delivery_city");
} else if (attribute.equals("delivery_state")) {
	output += mysession.get("user_delivery_state");
} else if (attribute.equals("delivery_country")) {
	output += mysession.get("user_delivery_country");
} else if (attribute.equals("delivery_phone")) {
	output += mysession.get("user_delivery_phone");
} else if (attribute.equals("delivery_fax")) {
	output += mysession.get("user_delivery_fax");
} else if (attribute.equals("delivery_email")) {
	output += mysession.get("user_delivery_email");
} else if (attribute.equals("delivery_website")) {
	output += mysession.get("user_delivery_website");
} else if (attribute.equals("invoice_name")) {
	output += mysession.get("user_invoice_name");
} else if (attribute.equals("invoice_organisation")) {
	output += mysession.get("user_invoice_organisation");
} else if (attribute.equals("invoice_address")) {
	output += mysession.get("user_invoice_address");
} else if (attribute.equals("invoice_postalcode")) {
	output += mysession.get("user_invoice_postalcode");
} else if (attribute.equals("invoice_city")) {
	output += mysession.get("user_invoice_city");
} else if (attribute.equals("invoice_state")) {
	output += mysession.get("user_invoice_state");
} else if (attribute.equals("invoice_country")) {
	output += mysession.get("user_invoice_country");
} else if (attribute.equals("invoice_phone")) {
	output += mysession.get("user_invoice_phone");
} else if (attribute.equals("invoice_fax")) {
	output += mysession.get("user_invoice_fax");
} else if (attribute.equals("invoice_email")) {
	output += mysession.get("user_invoice_email");
} else if (attribute.equals("invoice_website")) {
	output += mysession.get("user_invoice_website");
} else if (attribute.equals("notes")) {
	output += mysession.get("user_notes");
}

session.setAttribute("extension", output);

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %><%= output %>