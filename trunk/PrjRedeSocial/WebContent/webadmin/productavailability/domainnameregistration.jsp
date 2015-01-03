<%@ include file="../config.jsp" %><%@ page import="HardCore.HostingAPI" %><%@ page import="HardCore.Orderitem" %><%@ page import="java.util.regex.*" %><%

String registrar = myconfig.get(db, "productdelivery_domainnameregistration_url");
if (registrar.equals("")) {
	registrar = "http://resellertest.enom.com/";
}
registrar += "interface.asp?uid=@@@username@@@&pw=@@@password@@@";

String registrar_check = registrar + "&command=check&tld=@@@tld@@@&sld=@@@sld@@@";

HashMap outputs = new HashMap();
outputs.put("- Output returned from registrar -", "- Output return from this extension -");
outputs.put("Domain available", mytext.display("hosting.domain.available"));
outputs.put("Domain not available", mytext.display("hosting.domain.unavailable"));

session.setAttribute("productavailability", "+1 &nbsp;");

String username = myconfig.get(db, "productdelivery_domainnameregistration_username");
String password = myconfig.get(db, "productdelivery_domainnameregistration_password");

// CHECK

String domain = "";
String subdomain = "";
String sld = "";
String tld = "";
String[] tlds = "com,net,org".split(",");

String registrant_firstname = "";
String registrant_lastname = "";
String registrant_address1 = "";
String registrant_city = "";
String registrant_postalcode = "";
String registrant_country = "";
String registrant_email = "";
String registrant_phone = "";
String registrant_organisation = "";

if ((session.getAttribute("invoice_name") != null) && (! session.getAttribute("invoice_name").equals(""))) {
	if ((""+session.getAttribute("invoice_name")).lastIndexOf(" ") > 1) {
		registrant_firstname = (""+session.getAttribute("invoice_name")).substring(0, (""+session.getAttribute("invoice_name")).lastIndexOf(" "));
		registrant_lastname = (""+session.getAttribute("invoice_name")).substring((""+session.getAttribute("invoice_name")).lastIndexOf(" ")+1);
	}
}
if (session.getAttribute("invoice_address") != null) registrant_address1 = "" + session.getAttribute("invoice_address");
if (session.getAttribute("invoice_city") != null) registrant_city = "" + session.getAttribute("invoice_city");
if (session.getAttribute("invoice_postalcode") != null) registrant_postalcode = "" + session.getAttribute("invoice_postalcode");
if (session.getAttribute("invoice_country") != null) registrant_country = "" + session.getAttribute("invoice_country");
if (session.getAttribute("invoice_email") != null) registrant_email = "" + session.getAttribute("invoice_email");
if (session.getAttribute("invoice_phone") != null) registrant_phone = "" + session.getAttribute("invoice_phone");
if (session.getAttribute("invoice_organisation") != null) registrant_organisation = "" + session.getAttribute("invoice_organisation");
if (! myrequest.getParameter("invoice_name").equals("")) {
	if ((""+myrequest.getParameter("invoice_name")).lastIndexOf(" ") > 1) {
		registrant_firstname = myrequest.getParameter("invoice_name").substring(0, (""+myrequest.getParameter("invoice_name")).lastIndexOf(" ")-1);
		registrant_lastname = myrequest.getParameter("invoice_name").substring((""+myrequest.getParameter("invoice_name")).lastIndexOf(" ")+1);
	}
}
if (! myrequest.getParameter("invoice_address").equals("")) registrant_address1 = myrequest.getParameter("invoice_address");
if (! myrequest.getParameter("invoice_city").equals("")) registrant_city = myrequest.getParameter("invoice_city");
if (! myrequest.getParameter("invoice_postalcode").equals("")) registrant_postalcode = myrequest.getParameter("invoice_postalcode");
if (! myrequest.getParameter("invoice_country").equals("")) registrant_country = myrequest.getParameter("invoice_country");
if (! myrequest.getParameter("invoice_email").equals("")) registrant_email = myrequest.getParameter("invoice_email");
if (! myrequest.getParameter("invoice_phone").equals("")) registrant_phone = myrequest.getParameter("invoice_phone");
if (! myrequest.getParameter("invoice_organisation").equals("")) registrant_organisation = myrequest.getParameter("invoice_organisation");

registrant_address1 = registrant_address1.replaceAll("[\r\n]+", ", ").replaceAll("^[, ]+", "").replaceAll("[, ]+$", "");

Orderitem shopcartitem = null;
try {
	shopcartitem = (Orderitem)myrequest.getAttribute("shopcartitem");
} catch (Exception e) {
}
if ((shopcartitem != null) && (shopcartitem.getShopcart() != null)) {
	String[] product_options_selected = (("" + shopcartitem.getShopcart().get(shopcartitem.getProductId())) + "|||||||||||||||||||| ").split("\\|");
	String[] product_options = shopcartitem.getProductOptions().split("[\\r\\n]+");
	for (int i=0; i<product_options.length; i++) {
		String product_option = product_options[i];
		Pattern p = Pattern.compile("<([^<>]+?)>([^<>]*?)<\\/([^<>]+?)>");
		Matcher m = p.matcher(product_option);
		if (m.find()) {
			String myname = "" + m.group(1);
			String myvalue = "" + m.group(2);
			if (myname.equals("hosting:subdomain")) {
				subdomain = product_options_selected[i+1];
			} else if (myname.equals("hosting:domain")) {
				domain = product_options_selected[i+1];
				if (domain.matches("^([^.]+)\\.([^.]+\\.[^.]+)$")) {
					sld = domain.replaceAll("^([^.]+)\\.([^.]+\\.[^.]+)$", "$1");
					tld = domain.replaceAll("^([^.]+)\\.([^.]+\\.[^.]+)$", "$2");
				} else if (domain.matches("^([^.]+)\\.([^.]+)$")) {
					sld = domain.replaceAll("^([^.]+)\\.([^.]+)$", "$1");
					tld = domain.replaceAll("^([^.]+)\\.([^.]+)$", "$2");
				}
			} else if (myname.equals("hosting:domain:tld")) {
				tlds = myvalue.split(",");
			}
		}
	}
}

if ((! username.equals("")) && (! password.equals("")) && (! domain.equals(""))) {
	String output = "";
	String domain_prefix = myconfig.get(db, "productdelivery_hosting_www");
	String domain_postfix = myconfig.get(db, "productdelivery_hosting_domain");
	String client_address = domain_prefix + subdomain + domain_postfix;
	String client_database = myconfig.get(db, "productdelivery_hosting_database");
	client_database = client_database.replaceAll("@@@domain@@@", subdomain.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
	client_database = client_database.replaceAll("@@@username@@@", subdomain.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
	client_database = client_database.replaceAll("@@@password@@@", "" + (Math.round(Math.random()*89999999+10000000)));
	String client_URLrootpath = "/" + subdomain + "/";

	boolean valid_tld = false;
	for (int i=0; i<tlds.length; i++) {
		if (tld.equals(tlds[i])) valid_tld = true;
	}

	HostingAPI hostingAPI = new HostingAPI(mytext, servletcontext, mysession, myrequest, inifile, db, original_database, myconfig);
	String hosting_availability = "";
	if (! subdomain.equals("")) {
		hosting_availability = hostingAPI.hosting_availability(client_address, client_database, client_URLrootpath);
	}

	if (! hosting_availability.equals("")) {
		output = "0 <font color=\"red\">" + mytext.display("error") + " - " + mytext.display("hosting.subdomain.error") + ": " + subdomain + "<br>" + hosting_availability + "</font>";
	} else if ((! subdomain.equals("")) && (! myconfig.get(db, "productdelivery_hosting_min").equals("")) && (subdomain.length() < Common.number(myconfig.get(db, "productdelivery_hosting_min")))) {
		output = "0 <font color=\"red\">" + mytext.display("error") + " - " + mytext.display("hosting.subdomain.error.short") + ": " + subdomain + "</font>";
	} else if ((! subdomain.equals("")) && (! myconfig.get(db, "productdelivery_hosting_max").equals("")) && (subdomain.length() > Common.number(myconfig.get(db, "productdelivery_hosting_max")))) {
		output = "0 <font color=\"red\">" + mytext.display("error") + " - " + mytext.display("hosting.subdomain.error.long") + ": " + subdomain + "</font>";
	} else if ((! subdomain.equals("")) && (! Pattern.compile("^[a-z]").matcher(subdomain).find())) {
		output = "0 <font color=\"red\">" + mytext.display("error") + " - " + mytext.display("hosting.subdomain.error.start") + ": " + subdomain + "</font>";
	} else if ((! subdomain.equals("")) && (new java.io.File(Common.getRealPath(servletcontext, subdomain)).exists())) {
		output = "0 <font color=\"red\">" + mytext.display("error") + " - " + mytext.display("hosting.subdomain.error.unavailable") + ": " + subdomain + "</font>";
	} else if ((! subdomain.equals("")) && (DB.db_type(myconfig.get(db, "productdelivery_hosting_database")).equals("mysql")) && (new java.io.File(myconfig.get(db, "hosting_api_database_rootpath") + subdomain).exists())) {
		output = "0 <font color=\"red\">" + mytext.display("error") + " - " + mytext.display("hosting.subdomain.error.unavailable") + ": " + subdomain + "</font>";
	} else if ((! subdomain.equals("")) && (! myconfig.get(db, "productdelivery_hosting_blocked").equals("")) && (Pattern.compile("(" + myconfig.get(db, "productdelivery_hosting_blocked").replaceAll("[ \\r\\n\\t,]", "|") + ")").matcher(subdomain).find())) {
		output = "0 <font color=\"red\">" + mytext.display("error") + " - " + mytext.display("hosting.subdomain.error.unavailable") + ": " + subdomain + "</font>";
	} else if (! hosting_availability.equals("")) {
		output = "0 <font color=\"red\">" + mytext.display("error") + " - " + mytext.display("hosting.subdomain.error") + ": " + subdomain + "<br>" + hosting_availability + "</font>";
	} else if (! valid_tld) {
		output = "0 <font color=\"red\">" + mytext.display("error") + " - " + mytext.display("hosting.domain.error.tld") + ": " + Common.join(", ", tlds) + "</font>";
	} else if ((tld.equals("")) || (sld.equals(""))) {
		output = "0 <font color=\"red\">" + mytext.display("error") + " - " + mytext.display("hosting.domain.error.invalid") + ": " + domain + "</font>";
	} else if ((! tld.equals("")) && (! sld.equals(""))) {
		String url = registrar_check.replaceAll("@@@username@@@", username).replaceAll("@@@password@@@", password).replaceAll("@@@tld@@@", tld).replaceAll("@@@sld@@@", sld).replaceAll("@@@domain@@@", domain);
		String result = HardCore.http.get(url);
		if (result != null) result = result.replaceAll("[\r\n]+", ":::");
		if ((result.matches("(.*):::RRPCode=210:::(.*)")) && (result.matches("(.*):::RRPText=(.*?):::(.*)")) && (result.matches("(.*):::DomainName=(.*?):::(.*)"))) {
			String outputtext = result.replaceAll("(.*):::RRPText=(.*?):::(.*)", "$2");
			if (outputs.get(outputtext) != null) outputtext = "" + outputs.get(outputtext);
			output = "+1 <font color=\"green\">" + outputtext + ": " + result.replaceAll("(.*):::DomainName=(.*?):::(.*)", "$2") + "</font>";
			if ((myrequest.getQueryString().equals("confirm")) || (! myrequest.getParameter("confirm").equals("")) || (myrequest.getQueryString().equals("complete")) || (! myrequest.getParameter("complete").equals(""))) {
				if (registrant_firstname.equals("")) {
					output = output.replaceAll("^\\+1 ", "0 ");
					output += " <br><font color=\"red\">" + mytext.display("error") + " - " + mytext.display("hosting.domain.error.missing.firstname") + "</font>";
				}
				if (registrant_lastname.equals("")) {
					output = output.replaceAll("^\\+1 ", "0 ");
					output += " <br><font color=\"red\">" + mytext.display("error") + " - " + mytext.display("hosting.domain.error.missing.lastname") + "</font>";
				}
				if (registrant_address1.equals("")) {
					output = output.replaceAll("^\\+1 ", "0 ");
					output += " <br><font color=\"red\">" + mytext.display("error") + " - " + mytext.display("hosting.domain.error.missing.address") + "</font>";
				}
				if (registrant_city.equals("")) {
					output = output.replaceAll("^\\+1 ", "0 ");
					output += " <br><font color=\"red\">" + mytext.display("error") + " - " + mytext.display("hosting.domain.error.missing.city") + "</font>";
				}
				if (registrant_postalcode.equals("")) {
					output = output.replaceAll("^\\+1 ", "0 ");
					output += " <br><font color=\"red\">" + mytext.display("error") + " - " + mytext.display("hosting.domain.error.missing.postalcode") + "</font>";
				}
				if (registrant_country.equals("")) {
					output = output.replaceAll("^\\+1 ", "0 ");
					output += " <br><font color=\"red\">" + mytext.display("error") + " - " + mytext.display("hosting.domain.error.missing.country") + "</font>";
				}
				if (registrant_email.equals("")) {
					output = output.replaceAll("^\\+1 ", "0 ");
					output += " <br><font color=\"red\">" + mytext.display("error") + " - " + mytext.display("hosting.domain.error.missing.email") + "</font>";
				}
				if (registrant_phone.equals("")) {
					output = output.replaceAll("^\\+1 ", "0 ");
					output += " <br><font color=\"red\">" + mytext.display("error") + " - " + mytext.display("hosting.domain.error.missing.phone") + "</font>";
				}
			}
		} else if (result.matches("(.*):::Err1=(.*?):::(.*)")) {
			String outputtext = result.replaceAll("(.*):::Err1=(.*?):::(.*)", "$2");
			if (outputs.get(outputtext) != null) outputtext = "" + outputs.get(outputtext);
			output = "0 <font color=\"red\">" + mytext.display("error") + " - " + outputtext + "</font>";
		} else {
			String outputtext = result.replaceAll("(.*):::RRPText=(.*?):::(.*)", "$2");
			if (outputs.get(outputtext) != null) outputtext = "" + outputs.get(outputtext);
			output = "0 <font color=\"red\">" + mytext.display("error") + " - " + outputtext + ": " + result.replaceAll("(.*):::DomainName=(.*?):::(.*)", "$2") + "</font>";
		}
	} else {
		output = "0 <font color=\"red\">" + mytext.display("error") + "</font>";
	}

	session.setAttribute("productavailability", output);

}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>