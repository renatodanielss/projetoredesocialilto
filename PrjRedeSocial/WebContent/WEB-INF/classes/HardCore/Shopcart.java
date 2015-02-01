package HardCore;

import java.net.URLEncoder;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import javax.servlet.*;

public class Shopcart {
	private HashMap<String,String> shoppingcart = new HashMap<String,String>();
	private HashMap<String,Orderitem> shoppingcartitems = new HashMap<String,Orderitem>();
	private LinkedHashMap<String,String> shoppingcartitems_sorted = new LinkedHashMap<String,String>();
	private String out_of_stock = "";
	private String order_availability = "";
	private String order_quantity = "";
	private Currency order_currency = new Currency();
	private String order_subtotal = "";
	private String tax_description = "";
	private String tax_total = "";
	private String shipping_description = "";
	private String shipping_total = "";
	private String discount_description = "";
	private String discount_total = "";
	private String order_total = "";
	private String card_type = "";
	private String card_number = "";
	private String card_issuedmonth = "";
	private String card_issuedyear = "";
	private String card_expirymonth = "";
	private String card_expiryyear = "";
	private String card_name = "";
	private String card_cvc = "";
	private String card_issue = "";
	private String card_postalcode = "";
	private String delivery_name = "";
	private String delivery_organisation = "";
	private String delivery_address = "";
	private String delivery_postalcode = "";
	private String delivery_city = "";
	private String delivery_state = "";
	private String delivery_country = "";
	private String delivery_phone = "";
	private String delivery_fax = "";
	private String delivery_email = "";
	private String delivery_website = "";
	private String invoice_name = "";
	private String invoice_organisation = "";
	private String invoice_address = "";
	private String invoice_postalcode = "";
	private String invoice_city = "";
	private String invoice_state = "";
	private String invoice_country = "";
	private String invoice_phone = "";
	private String invoice_fax = "";
	private String invoice_email = "";
	private String invoice_website = "";
	private Text text = new Text();



	public Shopcart() {
		init();
	}



	public Shopcart(Text mytext) {
		if (mytext != null) text = mytext;
		init();
	}



	public void init() {
		shoppingcart = new HashMap<String,String>();
		shoppingcartitems = new HashMap<String,Orderitem>();
		out_of_stock = "";
		order_availability = "";
		order_quantity = "";
		order_currency = new Currency();
		order_subtotal = "";
		tax_description = "";
		tax_total = "";
		shipping_description = "";
		shipping_total = "";
		discount_description = "";
		discount_total = "";
		order_total = "";
		card_type = "";
		card_number = "";
		card_issuedmonth = "";
		card_issuedyear = "";
		card_expirymonth = "";
		card_expiryyear = "";
		card_name = "";
		card_cvc = "";
		card_issue = "";
		card_postalcode = "";
		delivery_name = "";
		delivery_organisation = "";
		delivery_address = "";
		delivery_postalcode = "";
		delivery_city = "";
		delivery_state = "";
		delivery_country = "";
		delivery_phone = "";
		delivery_fax = "";
		delivery_email = "";
		delivery_website = "";
		invoice_name = "";
		invoice_organisation = "";
		invoice_address = "";
		invoice_postalcode = "";
		invoice_city = "";
		invoice_state = "";
		invoice_country = "";
		invoice_phone = "";
		invoice_fax = "";
		invoice_email = "";
		invoice_website = "";
	}



	public void read(Session session, DB db, Configuration config) {
		// Unpack shopcart variables
		String session_shopcart = session.get("shopcart");
		if ((session_shopcart.equals("")) && (! session.get("userid").equals(""))) {
			User myuser = new User();
			session_shopcart = myuser.getShopcart(db, session.get("userid"));
		}
		String[] shopcartitems = session_shopcart.split("\r\n");
		for (int i=0; i<shopcartitems.length; i++) {
			if (! shopcartitems[i].equals("")) {
				String[] shopcartitemvalues = shopcartitems[i].split("x", 2);
				if (shopcartitemvalues.length >= 2) {
					shoppingcart.put(shopcartitemvalues[0], shopcartitemvalues[1]);
				}
			}
		}

		String session_currency = session.get("currency");
		order_currency = new Currency();
		order_currency.read(db, session_currency);
		if (order_currency.getRate().equals("")) {
			order_currency.read(db, config.get(db, "default_currency"));
		}

		card_type = session.get("card_type");
		card_number = session.get("card_number");
		card_issuedmonth = session.get("card_issuedmonth");
		card_issuedyear = session.get("card_issuedyear");
		card_expirymonth = session.get("card_expirymonth");
		card_expiryyear = session.get("card_expiryyear");
		card_name = session.get("card_name");
		card_cvc = session.get("card_cvc");
		card_issue = session.get("card_issue");
		card_postalcode = session.get("card_postalcode");
		delivery_name = session.get("delivery_name");
		delivery_organisation = session.get("delivery_organisation");
		delivery_address = session.get("delivery_address");
		delivery_postalcode = session.get("delivery_postalcode");
		delivery_city = session.get("delivery_city");
		delivery_state = session.get("delivery_state");
		delivery_country = session.get("delivery_country");
		if (delivery_country.equals("")) {
			delivery_country = session.get("default_country");
			delivery_state = session.get("default_state");
		}
		if (delivery_country.equals("")) {
			delivery_country = config.get(db, "default_country");
			delivery_state = config.get(db, "default_state");
		}
		delivery_phone = session.get("delivery_phone");
		delivery_fax = session.get("delivery_fax");
		delivery_email = session.get("delivery_email");
		delivery_website = session.get("delivery_website");
		invoice_name = session.get("invoice_name");
		invoice_organisation = session.get("invoice_organisation");
		invoice_address = session.get("invoice_address");
		invoice_postalcode = session.get("invoice_postalcode");
		invoice_city = session.get("invoice_city");
		invoice_state = session.get("invoice_state");
		invoice_country = session.get("invoice_country");
		invoice_phone = session.get("invoice_phone");
		invoice_fax = session.get("invoice_fax");
		invoice_email = session.get("invoice_email");
		invoice_website = session.get("invoice_website");
	}



	public void create(Session session) {
		// Pack shopcart variables
		String tmp_shopcart = "";
		Iterator shopcartitems = shoppingcart.keySet().iterator();
		while (shopcartitems.hasNext()) {
			String shopcartitem = "" + shopcartitems.next();
			String[] shopcartitem_elements = ("" + shoppingcart.get(shopcartitem)).split("\\|");
			String shopcartitem_quantity = shopcartitem_elements[0];
			if (Common.number("" + shopcartitem_quantity) > 0) {
				if (! tmp_shopcart.equals("")) {
					tmp_shopcart = tmp_shopcart + "\r\n";
				}
				tmp_shopcart = tmp_shopcart + shopcartitem + "x" + shoppingcart.get(shopcartitem);
			}
		}
		session.set("shopcart", tmp_shopcart);
	}



	public void save(Session session, DB db) {
		if (! session.get("userid").equals("")) {
			User myuser = new User();
			myuser.setShopcart(db, session.get("userid"), session.get("shopcart"));
		}
	}



	public void add(String productid) {
		shoppingcart.put(productid, "1");
	}
	public void add(String productid, String productquantity) {
		shoppingcart.put(productid, productquantity);
	}



	public void drop(String productid) {
		shoppingcart.put(productid, "0");
	}



	public void getForm(Request request, Session session) {
		if ((request.getParameter("discount") != null) && (! request.getParameter("discount").equals("")) && (! (session.get("discount").indexOf("|" + request.getParameter("discount") + "|") >= 0))) {
			session.set("discount", (session.get("discount") + "|" + request.getParameter("discount") + "|").replaceAll("\\|\\|", "|"));
		}

		if ((request.getParameter("add") != null) && (! request.getParameter("add").equals(""))) {
			String[] myproducts = request.getParameters("add");
			for (int i=0; i<myproducts.length; i++) {
				String myproduct = myproducts[i];
				if (shoppingcart.get(myproduct) == null) {
					shoppingcart.put(myproduct, "1");
					for (int productoption = 1; productoption <= 50; productoption++) {
						if (request.parameterExists("add_" + productoption)) {
							shoppingcart.put(myproduct, "" + shoppingcart.get(myproduct) + "|" + request.getParameter("add_" + productoption));
						} else {
							shoppingcart.put(myproduct, "" + shoppingcart.get(myproduct) + "|" + "");
						}
					}
				}
			}
		}

		if ((request.getParameter("drop") != null) && (! request.getParameter("drop").equals(""))) {
			String[] myproducts = request.getParameters("drop");
			for (int i=0; i<myproducts.length; i++) {
				String myproduct = myproducts[i];
				shoppingcart.put(myproduct, "0");
			}
		}

		for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
			String param = "" + params.nextElement();
			if (shoppingcart.get(param) != null) {
				shoppingcart.put(param, Common.numberformat(request.getParameter(param), 0));
				for (int productoption = 1; productoption <= 50; productoption++) {
					if (request.parameterExists(param + "_" + productoption)) {
						shoppingcart.put(param, "" + shoppingcart.get(param) + "|" + request.getParameter(param + "_" + productoption));
					} else {
						shoppingcart.put(param, "" + shoppingcart.get(param) + "|" + "");
					}
				}
			}
			if ((param.equals("card_type")) || (param.equals("card_number")) || (param.equals("card_issuedmonth")) || (param.equals("card_issuedyear")) || (param.equals("card_expirymonth")) || (param.equals("card_expiryyear")) || (param.equals("card_name")) || (param.equals("card_cvc")) || (param.equals("card_issue")) || (param.equals("card_postalcode")) || (param.equals("delivery_name")) || (param.equals("delivery_organisation")) || (param.equals("delivery_address")) || (param.equals("delivery_postalcode")) || (param.equals("delivery_city")) || (param.equals("delivery_state")) || (param.equals("delivery_country")) || (param.equals("delivery_phone")) || (param.equals("delivery_fax")) || (param.equals("delivery_email")) || (param.equals("delivery_website")) || (param.equals("invoice_name")) || (param.equals("invoice_organisation")) || (param.equals("invoice_address")) || (param.equals("invoice_postalcode")) || (param.equals("invoice_city")) || (param.equals("invoice_state")) || (param.equals("invoice_country")) || (param.equals("invoice_phone")) || (param.equals("invoice_fax")) || (param.equals("invoice_email")) || (param.equals("invoice_website"))) {
				session.set(param, html.encodeHtmlEntities(request.getParameter(param)));
			}
			if (param.equals("card_type")) card_type = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("card_number")) card_number = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("card_issuedmonth")) card_issuedmonth = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("card_issuedyear")) card_issuedyear = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("card_expirymonth")) card_expirymonth = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("card_expiryyear")) card_expiryyear = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("card_name")) card_name = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("card_cvc")) card_cvc = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("card_issue")) card_issue = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("card_postalcode")) card_postalcode = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("delivery_name")) delivery_name = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("delivery_organisation")) delivery_organisation = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("delivery_address")) delivery_address = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("delivery_postalcode")) delivery_postalcode = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("delivery_city")) delivery_city = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("delivery_state")) delivery_state = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("delivery_country")) delivery_country = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("delivery_phone")) delivery_phone = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("delivery_fax")) delivery_fax = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("delivery_email")) delivery_email = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("delivery_website")) delivery_website = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("invoice_name")) invoice_name = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("invoice_organisation")) invoice_organisation = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("invoice_address")) invoice_address = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("invoice_postalcode")) invoice_postalcode = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("invoice_city")) invoice_city = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("invoice_state")) invoice_state = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("invoice_country")) invoice_country = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("invoice_phone")) invoice_phone = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("invoice_fax")) invoice_fax = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("invoice_email")) invoice_email = html.encodeHtmlEntities(request.getParameter(param));
			if (param.equals("invoice_website")) invoice_website = html.encodeHtmlEntities(request.getParameter(param));
		}
	}



	public void delete(Session session) {
		shoppingcart = new HashMap<String,String>();
		shoppingcartitems = new HashMap<String,Orderitem>();

		card_type = "";
		card_number = "";
		card_issuedmonth = "";
		card_issuedyear = "";
		card_expirymonth = "";
		card_expiryyear = "";
		card_name = "";
		card_cvc = "";
		card_issue = "";
		card_postalcode = "";

		session.set("shopcart", "");
		session.set("card_type", "");
		session.set("card_number", "");
		session.set("card_issuedmonth", "");
		session.set("card_issuedyear", "");
		session.set("card_expirymonth", "");
		session.set("card_expiryyear", "");
		session.set("card_name", "");
		session.set("card_cvc", "");
		session.set("card_issue", "");
		session.set("card_postalcode", "");
	}



	public void calculate(ServletContext server, Session mysession, Request myrequest, Response myresponse, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version) {
		calculate(server, mysession, myrequest, myresponse, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", "", "", true, true);
	}
	public void calculate(ServletContext server, Session mysession, Request myrequest, Response myresponse, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests) {
		calculate(server, mysession, myrequest, myresponse, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, true, true);
	}
	public void calculate(ServletContext server, Session mysession, Request myrequest, Response myresponse, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, boolean stockcheck) {
		calculate(server, mysession, myrequest, myresponse, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", "", "", stockcheck, true);
	}
	public void calculate(ServletContext server, Session mysession, Request myrequest, Response myresponse, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, boolean stockcheck, boolean discounts) {
		calculate(server, mysession, myrequest, myresponse, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", "", "", stockcheck, discounts);
	}
	public void calculate(ServletContext server, Session mysession, Request myrequest, Response myresponse, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, boolean stockcheck) {
		calculate(server, mysession, myrequest, myresponse, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, stockcheck, true);
	}
	public void calculate(ServletContext server, Session mysession, Request myrequest, Response myresponse, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, boolean stockcheck, boolean discounts) {
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		shoppingcartitems_sorted = new LinkedHashMap<String,String>();
		shoppingcartitems = new HashMap<String,Orderitem>();
		if (shoppingcart.size() > 0) {
			String productids = "";
			Iterator shopcartitems = shoppingcart.keySet().iterator();
			while (shopcartitems.hasNext()) {
				String shopcartitem = "" + shopcartitems.next();
				String[] shopcartitem_elements = ("" + shoppingcart.get(shopcartitem)).split("\\|");
				String shopcartitem_quantity = shopcartitem_elements[0];
				if (Common.number("" + shopcartitem_quantity) > 0) {
					if (! productids.equals("")) {
						productids = productids + ",";
					}
					productids = productids + shopcartitem;
				}
			}

			String SQL = "";
			if (productids.equals("")) {
				SQL = "select * from content where 1=0";
			} else if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
				SQL = "select " + Common.SQLnumber(db, "product_price") + ", id, contentpackage, contentbundle, contentclass, contentgroup, contenttype, version, device, title, author, keywords, description, summary, image1, image2, image3, file1, file2, file3, link1, link2, link3, product_code, product_currency, product_price, product_period, product_weight, product_volume, product_width, product_height, product_depth, product_stock, product_comment, product_stock_amount, product_stock_text, product_lowstock_amount, product_lowstock_text, product_nostock_order, product_nostock_text, product_email, product_url, product_delivery, product_user, product_page, product_program, product_hosting, product_brand, product_colour, product_size, product_options, version_master from content where contentclass=" + db.quote("product") + " and id in (" + Common.SQL_clean(productids) + ") order by 1 desc, title, id";
//				SQL = "select id, version, device, title, author, keywords, description, summary, image1, image2, image3, file1, file2, file3, link1, link2, link3, product_code, product_currency, product_price, product_period, product_weight, product_volume, product_width, product_height, product_depth, product_stock, product_comment, product_stock_amount, product_stock_text, product_lowstock_amount, product_lowstock_text, product_nostock_order, product_nostock_text, product_email, product_url, product_delivery, product_user, product_page, product_program, product_hosting, product_brand, product_colour, product_size, product_options, version_master from content where contentclass=" + db.quote("product") + " and id in (" + Common.SQL_clean(productids) + ") order by title, id";
			} else {
				SQL = "select " + Common.SQLnumber(db, "product_price") + ", id, contentpackage, contentbundle, contentclass, contentgroup, contenttype, version, device, title, author, keywords, description, summary, image1, image2, image3, file1, file2, file3, link1, link2, link3, product_code, product_currency, product_price, product_period, product_weight, product_volume, product_width, product_height, product_depth, product_stock, product_comment, product_stock_amount, product_stock_text, product_lowstock_amount, product_lowstock_text, product_nostock_order, product_nostock_text, product_email, product_url, product_delivery, product_user, product_page, product_program, product_hosting, product_brand, product_colour, product_size, product_options, version_master from content_public where contentclass=" + db.quote("product") + " and id in (" + Common.SQL_clean(productids) + ") order by 1 desc, title, id";
//				SQL = "select id, version, device, title, author, keywords, description, summary, image1, image2, image3, file1, file2, file3, link1, link2, link3, product_code, product_currency, product_price, product_period, product_weight, product_volume, product_width, product_height, product_depth, product_stock, product_comment, product_stock_amount, product_stock_text, product_lowstock_amount, product_lowstock_text, product_nostock_order, product_nostock_text, product_email, product_url, product_delivery, product_user, product_page, product_program, product_hosting, product_brand, product_colour, product_size, product_options, version_master from content_public where contentclass=" + db.quote("product") + " and id in (" + Common.SQL_clean(productids) + ") order by title, id";
			}
			String productsSQL = "" + SQL;

			out_of_stock = "";
			order_availability = "";
			double order_quantity = 0;
			double order_subtotal = 0;
			double order_total = 0;
			double order_weight = 0;
			double order_volume = 0;

			Content results = new Content(text);
			results.init();
			results.records("", "", "", "", "", "", "", db, config, productsSQL);
			while (results.records("", "", "", "", "", "", "", db, config, "")) {
				Orderitem shopcartitem = doShopcartItem(server, mysession, myrequest, myresponse, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, stockcheck, results);
				String quantity = shopcartitem.getItemQuantity();
				String display_price = shopcartitem.getProductPrice();
				order_availability += doProductAvailability(server, mysession, myrequest, myresponse, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, stockcheck, shopcartitem);
				if (true) {
					order_total = order_total + (Common.number(quantity) * Common.number(display_price));
					order_subtotal = order_subtotal + (Common.number(quantity) * Common.number(display_price));
					order_quantity = order_quantity + Common.number(quantity);
					order_weight = order_weight + (Common.number(quantity) * Common.number(shopcartitem.getProductWeight()));
					order_volume = order_volume + (Common.number(quantity) * Common.number(shopcartitem.getProductVolume()));
				}
			}

			String discount_description = "";
			double discount_total = 0;
			String shopcartitem_discount_description = "";
			double shopcartitem_discount_total = 0;
			HashMap<String,String> shopcartitem_discount_item_quantity_rest = new HashMap<String,String>();

if (discounts) { // begin discounts calculation
			Discount discount = new Discount();
			results.init();
			results.records("", "", "", "", "", "", "", db, config, productsSQL);
			while (results.records("", "", "", "", "", "", "", db, config, "")) {
				String productid = results.getId();
				Orderitem shopcartitem = (Orderitem)shoppingcartitems.get(productid);
//QQQ				Orderitem shopcartitem = shopcartitem = doShopcartItem(server, mysession, myrequest, myresponse, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, stockcheck, results);
//QQQ				order_availability += doProductAvailability(server, mysession, myrequest, myresponse, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, stockcheck, shopcartitem);
				if (true) {
					discount = new Discount();
					SQL = "select * from discounts";
					SQL += " where ((" + db.is_blank("country") + ") or (country=" + db.quote(delivery_country) + "))";
					SQL += " and ((" + db.is_blank("state") + ") or (state=" + db.quote(delivery_state) + "))";
					SQL += " and (((quantity_from is null) or (quantity_from <= " + order_quantity + ")) and ((quantity_to is null) or (quantity_to = 0) or (quantity_to >= " + order_quantity + ")))";
					SQL += " and (((total_from is null) or (total_from <= " + order_subtotal + ")) and ((total_to is null) or (total_to = 0) or (total_to >= " + order_subtotal + ")))";
					if (order_weight > 0) {
						SQL += " and ((((total_weight_from is null) or (total_weight_from = 0)) and ((total_weight_to is null) or (total_weight_to = 0))) or ((total_weight_from <= " + order_weight + ") and (total_weight_to >= " + order_weight + ")))";
					}
					if (order_volume > 0) {
						SQL += " and ((((total_volume_from is null) or (total_volume_from = 0)) and ((total_volume_to is null) or (total_volume_to = 0))) or ((total_volume_from <= " + order_volume + ") and (total_volume_to >= " + order_volume + ")))";
					}
					SQL += " and (" + db.is_blank("user_username") + " or (user_username = " + db.quote(Common.SQL_clean(mysession.get("username"))) + "))";
					SQL += " and (" + db.is_blank("user_group") + " or (" + Common.SQLwhere_in("-", "user_group", (mysession.get("usergroup") + "|" + mysession.get("usergroups")).replaceAll("\\|", ",")) + "))";
					SQL += " and (" + db.is_blank("user_type") + " or (" + Common.SQLwhere_in("-", "user_type", (mysession.get("usertype") + "|" + mysession.get("usertypes")).replaceAll("\\|", ",")) + "))";
					SQL += " and (" + db.is_blank("user_code") + " or (" + Common.SQLwhere_in("-", "user_code", mysession.get("discount").replaceAll("\\|", ",")) + "))";
					SQL += " and (" + db.is_blank("period_start") + " or (period_start <= " + db.quote(now) + "))";
					SQL += " and (" + db.is_blank("period_end") + " or (period_end >= " + db.quote(now) + "))";
					SQL += " and ((product_id=" + db.quote(productid) + ") or (" + db.is_blank("product_id") + "))";
					SQL += " and ((product_group=" + db.quote(results.getContentGroup()) + ") or (" + db.is_blank("product_group") + "))";
					SQL += " and ((product_type=" + db.quote(results.getContentType()) + ") or (" + db.is_blank("product_type") + "))";
					if (Common.number(shopcartitem.getProductWeight()) > 0) {
						SQL += " and (((product_weight_from is null) or (product_weight_from <= " + shopcartitem.getProductWeight() + ")) and ((product_weight_to is null) or (product_weight_to = 0) or (product_weight_to >= " + shopcartitem.getProductWeight() + ")))";
					}
					if (Common.number(shopcartitem.getProductVolume()) > 0) {
						SQL += " and (((product_volume_from is null) or (product_volume_from <= " + shopcartitem.getProductVolume() + ")) and ((product_volume_to is null) or (product_volume_to = 0) or (product_volume_to >= " + shopcartitem.getProductVolume() + ")))";
					}
					if (Common.number(shopcartitem.getProductWidth()) > 0) {
						SQL += " and (((product_width_from is null) or (product_width_from <= " + shopcartitem.getProductWidth() + ")) and ((product_width_to is null) or (product_width_to = 0) or (product_width_to >= " + shopcartitem.getProductWidth() + ")))";
					}
					if (Common.number(shopcartitem.getProductHeight()) > 0) {
						SQL += " and (((product_height_from is null) or (product_height_from <= " + shopcartitem.getProductHeight() + ")) and ((product_height_to is null) or (product_height_to = 0) or (product_height_to >= " + shopcartitem.getProductHeight() + ")))";
					}
					if (Common.number(shopcartitem.getProductDepth()) > 0) {
						SQL += " and (((product_depth_from is null) or (product_depth_from <= " + shopcartitem.getProductDepth() + ")) and ((product_depth_to is null) or (product_depth_to = 0) or (product_depth_to >= " + shopcartitem.getProductDepth() + ")))";
					}
					// only "applies to products"
					SQL += " AND ( (" + db.is_not_blank("product_id") + ") or (" + db.is_not_blank("product_group") + ") or (" + db.is_not_blank("product_type") + ") or ((product_weight_from is not null) and (product_weight_from <> 0)) or ((product_weight_to is not null) and (product_weight_to <> 0)) or ((product_volume_from is not null) and (product_volume_from <> 0)) or ((product_volume_to is not null) and (product_volume_to <> 0)) or ((product_width_from is not null) and (product_width_from <> 0)) or ((product_width_to is not null) and (product_width_to <> 0)) or ((product_height_from is not null) and (product_height_from <> 0)) or ((product_height_to is not null) and (product_height_to <> 0)) or ((product_depth_from is not null) and (product_depth_from <> 0)) or (discount_products=" + db.quote("same") + ") or ((discount_type=" + db.quote("general") + ") and (discount_orderitems=" + db.quote("each") + ")) )";
					SQL += " order by discount_quantity desc, discount_quantity2 desc, discount_amount desc, title, id";

					shopcartitem_discount_item_quantity_rest = new HashMap<String,String>();
					shopcartitem_discount_description = "";
					shopcartitem_discount_total = 0;
					discount.records(db, SQL);
					while (discount.records(db, "")) {
						if (discount.getDiscountCurrency().equals("")) {
							discount.setDiscountCurrency(config.get(db, "default_currency"));
						}
						double my_discount_amount = 0;
						String my_discount_description = "";

						if (discount.getDiscountType().equals("general")) {
							if (discount.getDiscountCurrency().equals("%")) {
								my_discount_amount = Common.number(discount.getDiscountAmount()) * Common.number(shopcartitem.getItemTotal()) / 100;
								my_discount_description = "" + discount.getDiscountDescription();
							} else if (discount.getDiscountOrderItems().equals("each")) {
								my_discount_amount = Common.number(discount.getDiscountAmount()) * Common.number(shopcartitem.getItemQuantity());
								my_discount_description = discount.getDiscountDescription();
							} else if (discount.getDiscountOrderItems().equals("total")) {
								// ignore - handled below for shopcart totals discounts
							}
							if (my_discount_amount > Common.number(shopcartitem.getItemTotal())) {
								my_discount_amount = Common.number(shopcartitem.getItemTotal());
							}
							shopcartitem.setItemTotal("" + (Common.number(shopcartitem.getItemTotal()) - my_discount_amount));
						}

						if ((discount.getDiscountType().equals("volume")) && (discount.getDiscountProducts().equals("same"))) {
							if ((discount.getDiscountQuantity().equals("")) || (discount.getDiscountQuantity().equals("0")) || (Common.number(discount.getDiscountQuantity()) <= Common.number(shopcartitem.getItemQuantity()))) {
								if (discount.getDiscountCurrency().equals("%")) {
									my_discount_amount = Common.number(discount.getDiscountAmount()) * Common.number(shopcartitem.getItemTotal()) / 100;
									my_discount_description = "" + discount.getDiscountDescription();
									if (my_discount_amount > Common.number(shopcartitem.getItemTotal())) {
										my_discount_amount = Common.number(shopcartitem.getItemTotal());
									}
								} else if (discount.getDiscountOrderItems().equals("each")) {
									my_discount_amount = Common.number(discount.getDiscountAmount()) * Common.number(shopcartitem.getItemQuantity());
									my_discount_description = discount.getDiscountDescription();
									if (my_discount_amount > Common.number(shopcartitem.getItemTotal())) {
										my_discount_amount = Common.number(shopcartitem.getItemTotal());
									}
								} else if (discount.getDiscountOrderItems().equals("total")) {
									if (shopcartitem_discount_item_quantity_rest.get(shopcartitem.getProductId()) == null) {
										long my_fixed_sets = 0;
										long my_fixed_rest = 0;
										if ((Common.number(discount.getDiscountQuantity2()) > 0) && (Common.number(shopcartitem.getItemQuantity()) > Common.number(discount.getDiscountQuantity2()))) {
											my_fixed_sets = (long)Math.floor(Common.number(shopcartitem.getItemQuantity()) / Common.number(discount.getDiscountQuantity2()));
											my_fixed_rest = Common.intnumber(shopcartitem.getItemQuantity()) - (my_fixed_sets * Common.intnumber(discount.getDiscountQuantity2()));
										} else {
											my_fixed_sets = 1;
											my_fixed_rest = 0;
										}
										if (Common.number(shopcartitem.getItemTotal()) > ((Common.number(shopcartitem.getItemQuantity()) * Common.number(shopcartitem.getProductPrice())) - (my_fixed_sets * Common.number(discount.getDiscountAmount())))) {
											my_discount_amount = Common.number(shopcartitem.getItemTotal()) - ((Common.number(shopcartitem.getItemQuantity()) * Common.number(shopcartitem.getProductPrice())) - (my_fixed_sets * Common.number(discount.getDiscountAmount())));
											my_discount_description = discount.getDiscountDescription();
										}
										shopcartitem_discount_item_quantity_rest.put(shopcartitem.getProductId(), ""+my_fixed_rest);
									} else {
										long my_quantity = Common.intnumber("" + shopcartitem_discount_item_quantity_rest.get(shopcartitem.getProductId()));
										long my_fixed_sets = 0;
										long my_fixed_rest = 0;
										if ((Common.number(discount.getDiscountQuantity2()) > 0) && (my_quantity > Common.number(discount.getDiscountQuantity2()))) {
											my_fixed_sets = (long)Math.floor(my_quantity / Common.number(discount.getDiscountQuantity2()));
											my_fixed_rest = my_quantity - (my_fixed_sets * Common.intnumber(discount.getDiscountQuantity2()));
										} else {
											my_fixed_sets = 1;
											my_fixed_rest = 0;
										}
										if (Common.number(shopcartitem.getItemTotal()) > ((my_quantity * Common.number(shopcartitem.getProductPrice())) - (my_fixed_sets * Common.number(discount.getDiscountAmount())))) {
											my_discount_amount = my_fixed_sets * Common.number(discount.getDiscountAmount());
											my_discount_description = discount.getDiscountDescription();
										}
										shopcartitem_discount_item_quantity_rest.put(shopcartitem.getProductId(), ""+my_fixed_rest);
									}
								}
								shopcartitem.setItemTotal("" + (Common.number(shopcartitem.getItemTotal()) - my_discount_amount));
							}
						}

						if ((discount.getDiscountType().equals("fixed")) && (discount.getDiscountProducts().equals("same"))) {
							if ((discount.getDiscountQuantity().equals("")) || (discount.getDiscountQuantity().equals("0")) || (Common.number(discount.getDiscountQuantity()) <= Common.number(shopcartitem.getItemQuantity()))) {
								if (discount.getDiscountOrderItems().equals("each")) {
//QQQQQ MISSING: handle different shopcartitem currency
									if (shopcartitem_discount_item_quantity_rest.get(shopcartitem.getProductId()) == null) {
										if (Common.number(shopcartitem.getProductPrice()) > Common.number(discount.getDiscountAmount())) {
											long my_fixed_sets = 0;
											long my_fixed_rest = 0;
											if ((Common.number(discount.getDiscountQuantity2()) > 0) && (Common.number(shopcartitem.getItemQuantity()) > Common.number(discount.getDiscountQuantity2()))) {
												my_fixed_sets = (long)Math.floor(Common.number(shopcartitem.getItemQuantity()) / Common.number(discount.getDiscountQuantity2()));
												my_fixed_rest = Common.intnumber(shopcartitem.getItemQuantity()) - (my_fixed_sets * Common.intnumber(discount.getDiscountQuantity2()));
												my_discount_amount = (Common.number(shopcartitem.getProductPrice()) - Common.number(discount.getDiscountAmount())) * (my_fixed_sets * Common.number(discount.getDiscountQuantity2()));
											} else {
												my_fixed_rest = 0;
												my_discount_amount = (Common.number(shopcartitem.getProductPrice()) - Common.number(discount.getDiscountAmount())) * Common.number(shopcartitem.getItemQuantity());
											}
											my_discount_description = discount.getDiscountDescription();
											shopcartitem_discount_item_quantity_rest.put(shopcartitem.getProductId(), ""+my_fixed_rest);
										}
									} else {
										if (Common.number(shopcartitem.getProductPrice()) > Common.number(discount.getDiscountAmount())) {
											long my_quantity = Common.intnumber("" + shopcartitem_discount_item_quantity_rest.get(shopcartitem.getProductId()));
											long my_fixed_sets = 0;
											long my_fixed_rest = 0;
											if ((Common.number(discount.getDiscountQuantity2()) > 0) && (my_quantity > Common.number(discount.getDiscountQuantity2()))) {
												my_fixed_sets = (long)Math.floor(my_quantity / Common.number(discount.getDiscountQuantity2()));
												my_fixed_rest = my_quantity - (my_fixed_sets * Common.intnumber(discount.getDiscountQuantity2()));
												my_discount_amount = (Common.number(shopcartitem.getProductPrice()) - Common.number(discount.getDiscountAmount())) * (my_fixed_sets * Common.number(discount.getDiscountQuantity2()));
											} else {
												my_fixed_rest = 0;
												my_discount_amount = (Common.number(shopcartitem.getProductPrice()) - Common.number(discount.getDiscountAmount())) * my_quantity;
											}
											my_discount_description = discount.getDiscountDescription();
											shopcartitem_discount_item_quantity_rest.put(shopcartitem.getProductId(), ""+my_fixed_rest);
										}
									}
									if (my_discount_amount > Common.number(shopcartitem.getItemTotal())) {
										my_discount_amount -= (my_discount_amount - Common.number(shopcartitem.getItemTotal()));
										if (my_discount_amount <= 0) {
											my_discount_amount = 0;
											my_discount_description = "";
										}
									}

								} else if (discount.getDiscountOrderItems().equals("total")) {
//QQQQQ MISSING: handle different shopcartitem currency
									if (shopcartitem_discount_item_quantity_rest.get(shopcartitem.getProductId()) == null) {
										long my_fixed_sets = 0;
										long my_fixed_rest = 0;
										if (Common.number(discount.getDiscountQuantity2()) > 0) {
											my_fixed_sets = (long)Math.floor(Common.number(shopcartitem.getItemQuantity()) / Common.number(discount.getDiscountQuantity2()));
											my_fixed_rest = Common.intnumber(shopcartitem.getItemQuantity()) - (my_fixed_sets * Common.intnumber(discount.getDiscountQuantity2()));
										} else {
											my_fixed_sets = 1;
											my_fixed_rest = 0;
										}
										if (Common.number(shopcartitem.getItemTotal()) > ((my_fixed_sets * Common.number(discount.getDiscountAmount())) + (my_fixed_rest * Common.number(shopcartitem.getProductPrice())))) {
											my_discount_amount = Common.number(shopcartitem.getItemTotal()) - ((my_fixed_sets * Common.number(discount.getDiscountAmount())) + (my_fixed_rest * Common.number(shopcartitem.getProductPrice())));
											my_discount_description = discount.getDiscountDescription();
										}
										shopcartitem_discount_item_quantity_rest.put(shopcartitem.getProductId(), ""+my_fixed_rest);
									} else {
										long my_quantity = Common.intnumber("" + shopcartitem_discount_item_quantity_rest.get(shopcartitem.getProductId()));
										long my_fixed_sets = 0;
										long my_fixed_rest = 0;
										if (Common.number(discount.getDiscountQuantity2()) > 0) {
											my_fixed_sets = (long)Math.floor(my_quantity / Common.number(discount.getDiscountQuantity2()));
											my_fixed_rest = my_quantity - (my_fixed_sets * Common.intnumber(discount.getDiscountQuantity2()));
										} else {
											my_fixed_sets = 1;
											my_fixed_rest = 0;
										}
										if ((my_quantity * Common.number(shopcartitem.getProductPrice())) > ((my_fixed_sets * Common.number(discount.getDiscountAmount())) + (my_fixed_rest * Common.number(shopcartitem.getProductPrice())))) {
											my_discount_amount = (my_quantity * Common.number(shopcartitem.getProductPrice())) - ((my_fixed_sets * Common.number(discount.getDiscountAmount())) + (my_fixed_rest * Common.number(shopcartitem.getProductPrice())));
											my_discount_description = discount.getDiscountDescription();
										}
										shopcartitem_discount_item_quantity_rest.put(shopcartitem.getProductId(), ""+my_fixed_rest);
									}
								}
								shopcartitem.setItemTotal("" + (Common.number(shopcartitem.getItemTotal()) - my_discount_amount));
							}
						}

						if ((discount.getDiscountType().equals("bogof")) && (discount.getDiscountProducts().equals("same"))) {
							if (Common.number(discount.getDiscountQuantity()) < Common.number(shopcartitem.getItemQuantity())) {
								int my_bogof_sets = (int)Math.floor(Common.number(shopcartitem.getItemQuantity()) / (Common.number(discount.getDiscountQuantity()) + Common.number(discount.getDiscountQuantity2())));
								long my_bogof_quantity = Common.intnumber(shopcartitem.getItemQuantity()) - (Common.intnumber(discount.getDiscountQuantity()) * my_bogof_sets) - (Common.intnumber(shopcartitem.getItemQuantity()) % (Common.intnumber(discount.getDiscountQuantity()) + Common.intnumber(discount.getDiscountQuantity2())));
								if (discount.getDiscountOrderItems().equals("off")) {
									if (discount.getDiscountCurrency().equals("%")) {
										my_discount_amount = Common.number(discount.getDiscountAmount()) * Common.number(shopcartitem.getProductPrice()) / 100 * my_bogof_quantity;
									} else {
//QQQQQ MISSING: handle different shopcartitem currency
										if (Common.number(shopcartitem.getProductPrice()) > Common.number(discount.getDiscountAmount())) {
											my_discount_amount = Common.number(discount.getDiscountAmount()) * my_bogof_quantity;
										} else {
											my_discount_amount = Common.number(shopcartitem.getProductPrice()) * my_bogof_quantity;
										}
									}
									my_discount_description = discount.getDiscountDescription();
								} else if (discount.getDiscountOrderItems().equals("each")) {
									if (discount.getDiscountCurrency().equals("%")) {
										my_discount_amount = (100 - Common.number(discount.getDiscountAmount())) * Common.number(shopcartitem.getProductPrice()) / 100 * my_bogof_quantity;
									} else if (Common.number(shopcartitem.getProductPrice()) > Common.number(discount.getDiscountAmount())) {
//QQQQQ MISSING: handle different shopcartitem currency
										my_discount_amount = (Common.number(shopcartitem.getProductPrice()) - Common.number(discount.getDiscountAmount())) * my_bogof_quantity;
									}
									my_discount_description = discount.getDiscountDescription();
								} else if (discount.getDiscountOrderItems().equals("total")) {
									if (discount.getDiscountCurrency().equals("%")) {
										my_discount_amount = (100 - Common.number(discount.getDiscountAmount())) * Common.number(shopcartitem.getProductPrice()) / 100 * my_bogof_quantity;
									} else if (Common.number(shopcartitem.getProductPrice()) * Common.number(discount.getDiscountQuantity2()) > Common.number(discount.getDiscountAmount())) {
//QQQQQ MISSING: handle different shopcartitem currency
										my_discount_amount = ((Common.number(shopcartitem.getProductPrice()) * Common.number(discount.getDiscountQuantity2())) - Common.number(discount.getDiscountAmount())) * my_bogof_sets;
									}
									my_discount_description = discount.getDiscountDescription();
								}
								shopcartitem.setItemTotal("" + (Common.number(shopcartitem.getItemTotal()) - my_discount_amount));
							}
						}

						if (discount.getDiscountCurrency().equals("%")) {
							// ok
						} else if (! discount.getDiscountCurrency().equals(order_currency.getId())) {
							Currency discount_currency = new Currency();
							discount_currency.read(db, discount.getDiscountCurrency());
							my_discount_amount = my_discount_amount * Common.number(order_currency.getRate()) / Common.number(discount_currency.getRate());
						}
						if (my_discount_amount > 0) {
							if (shopcartitem_discount_description.indexOf("<span class=\"discount\">" + discount.getDiscountDescription() + "</span>") < 0) {
//								shopcartitem_discount_description = shopcartitem_discount_description + "<span class=\"discount\">" + shopcartitem.getItemQuantity() + " x " + discount.getDiscountDescription() + "</span>" + " ";
								shopcartitem_discount_description = shopcartitem_discount_description + "<span class=\"discount\">" + discount.getDiscountDescription() + "</span>" + " ";
							}
							shopcartitem_discount_total = shopcartitem_discount_total + my_discount_amount;
						}
					}
					shopcartitem.setDiscountDescription(shopcartitem.getDiscountDescription() + shopcartitem_discount_description);
					if (discount_description.indexOf(shopcartitem_discount_description) < 0) {
						discount_description = discount_description + shopcartitem_discount_description;
					}
					shopcartitem.setDiscountTotal(shopcartitem.getDiscountTotal() + shopcartitem_discount_total);
					discount_total = discount_total + shopcartitem_discount_total;
				}
			}

			discount = new Discount();
			SQL = "select * from discounts";
			SQL += " where ((" + db.is_blank("country") + ") or (country=" + db.quote(delivery_country) + "))";
			SQL += " and ((" + db.is_blank("state") + ") or (state=" + db.quote(delivery_state) + "))";
			SQL += " and (((quantity_from is null) or (quantity_from <= " + order_quantity + ")) and ((quantity_to is null) or (quantity_to = 0) or (quantity_to >= " + order_quantity + ")))";
			SQL += " and (((total_from is null) or (total_from <= " + order_subtotal + ")) and ((total_to is null) or (total_to = 0) or (total_to >= " + order_subtotal + ")))";
			if (order_weight > 0) {
				SQL += " and ((((total_weight_from is null) or (total_weight_from = 0)) and ((total_weight_to is null) or (total_weight_to = 0))) or ((total_weight_from <= " + order_weight + ") and (total_weight_to >= " + order_weight + ")))";
			}
			if (order_volume > 0) {
				SQL += " and ((((total_volume_from is null) or (total_volume_from = 0)) and ((total_volume_to is null) or (total_volume_to = 0))) or ((total_volume_from <= " + order_volume + ") and (total_volume_to >= " + order_volume + ")))";
			}
			SQL += " and (" + db.is_blank("user_username") + " or (user_username = " + db.quote(Common.SQL_clean(mysession.get("username"))) + "))";
			SQL += " and (" + db.is_blank("user_group") + " or (" + Common.SQLwhere_in("-", "user_group", (mysession.get("usergroup") + "|" + mysession.get("usergroups")).replaceAll("\\|", ",")) + "))";
			SQL += " and (" + db.is_blank("user_type") + " or (" + Common.SQLwhere_in("-", "user_type", (mysession.get("usertype") + "|" + mysession.get("usertypes")).replaceAll("\\|", ",")) + "))";
			SQL += " and (" + db.is_blank("user_code") + " or (" + Common.SQLwhere_in("-", "user_code", mysession.get("discount").replaceAll("\\|", ",")) + "))";
			SQL += " and (" + db.is_blank("period_start") + " or (period_start <= " + db.quote(now) + "))";
			SQL += " and (" + db.is_blank("period_end") + " or (period_end >= " + db.quote(now) + "))";
			SQL += " and ";
			SQL += "(";
			// exclude applies to products discounts
 			SQL += "(";
 			SQL += "(" + db.is_blank("product_id") + " and " + db.is_blank("product_group") + " and " + db.is_blank("product_type") + ")";
			SQL += " and ";
			SQL += "(( product_weight_from is null) or (product_weight_from = 0)) and (( product_weight_to is null) or (product_weight_to = 0))";
			SQL += " and ";
			SQL += "(( product_volume_from is null) or (product_volume_from = 0)) and (( product_volume_to is null) or (product_volume_to = 0))";
			SQL += " and ";
			SQL += "(( product_width_from is null) or (product_width_from = 0)) and (( product_width_to is null) or (product_width_to = 0))";
			SQL += " and ";
			SQL += "(( product_height_from is null) or (product_height_from = 0)) and (( product_height_to is null) or (product_height_to = 0))";
			SQL += " and ";
			SQL += "(( product_depth_from is null) or (product_depth_from = 0)) and (( product_depth_to is null) or (product_depth_to = 0))";
 			SQL += ")";
			// except
			SQL += " or (discount_products=" + db.quote("any") + ")";
//			SQL += " or ((discount_type=" + db.quote("general") + ") and (discount_orderitems=" + db.quote("total") + "))";
 			SQL += ")";
			SQL += " order by discount_quantity desc, discount_quantity2 desc, discount_amount desc, title, id";

			shopcartitem_discount_description = "";
			shopcartitem_discount_total = 0;
			shopcartitem_discount_item_quantity_rest = new HashMap<String,String>();
			discount.records(db, SQL);
			while (discount.records(db, "")) {
				if (discount.getDiscountCurrency().equals("")) {
					discount.setDiscountCurrency(config.get(db, "default_currency"));
				}
				double my_discount_amount = 0;
				String my_discount_description = "";

				String SQLwhere = "";
				if (! discount.getProductId().equals("")) {
					SQLwhere += " and (id=" + discount.getProductId() + ")";
				}
				if (! discount.getProductGroup().equals("")) {
					SQLwhere += " and (contentgroup=" + db.quote(Common.SQL_clean(discount.getProductGroup())) + ")";
				}
				if (! discount.getProductType().equals("")) {
					SQLwhere += " and (contenttype=" + db.quote(Common.SQL_clean(discount.getProductType())) + ")";
				}
				if (productids.equals("")) {
					SQL = "select * from content where 1=0";
				} else if ((mysession.get("mode").equals("preview")) || (mysession.get("mode").equals("admin"))) {
					SQL = "select " + Common.SQLnumber(db, "product_price") + ", id, version, device, title, author, keywords, description, summary, image1, image2, image3, file1, file2, file3, link1, link2, link3, product_code, product_currency, product_price, product_period, product_weight, product_volume, product_width, product_height, product_depth, product_stock, product_comment, product_stock_amount, product_stock_text, product_lowstock_amount, product_lowstock_text, product_nostock_order, product_nostock_text, product_email, product_url, product_delivery, product_user, product_page, product_program, product_hosting, product_options, version_master from content where contentclass=" + db.quote("product") + " and id in (" + productids + ")" + SQLwhere + " order by 1 desc, title, id";
			 	} else {
					SQL = "select " + Common.SQLnumber(db, "product_price") + ", id, version, device, title, author, keywords, description, summary, image1, image2, image3, file1, file2, file3, link1, link2, link3, product_code, product_currency, product_price, product_period, product_weight, product_volume, product_width, product_height, product_depth, product_stock, product_comment, product_stock_amount, product_stock_text, product_lowstock_amount, product_lowstock_text, product_nostock_order, product_nostock_text, product_email, product_url, product_delivery, product_user, product_page, product_program, product_hosting, product_options, version_master from content_public where contentclass=" + db.quote("product") + " and id in (" + productids + ")" + SQLwhere + " order by 1 desc, title, id";
				}

				if (discount.getDiscountType().equals("general")) {
					if (discount.getDiscountOrderItems().equals("total")) {
//QQQQQ MISSING: handle different shopcartitem currency

						HashMap<String,String> discountedproducts = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discounted = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_item_quantity = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_item_total = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discount_total = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discount_description = new HashMap<String,String>();
						int discounted_item_quantity = 0;
						double discounted_item_total = 0;
						double discounted_order_total = 0;
						Content products = new Content(text);
						products.init();
						products.records("", "", "", "", "", "", "", db, config, SQL);
						while (products.records("", "", "", "", "", "", "", db, config, "")) {
							String discountedproductid = "" + products.getId();
							Orderitem shopcartitem = (Orderitem)shoppingcartitems.get(discountedproductid);
							for (int i=0; i<Common.number(shopcartitem.getItemQuantity()); i++) {
								if (discountedproducts.get(discountedproductid) == null) {
									discountedproducts.put(discountedproductid, discountedproductid);
									discountedproducts_discounted.put(discountedproductid, null);
									discountedproducts_item_total.put(discountedproductid, shopcartitem.getItemTotal());
									discountedproducts_discount_total.put(discountedproductid, shopcartitem.getDiscountTotal());
									discountedproducts_discount_description.put(discountedproductid, "<span class=\"discount\">" + discount.getDiscountDescription() + "</span>" + " ");
								}
								discounted_item_total += Common.number(shopcartitem.getProductPrice());
								discountedproducts_discounted.put(discountedproductid, "true");
								if (discount.getDiscountCurrency().equals("%")) {
									my_discount_amount = Common.number(discount.getDiscountAmount()) * Common.number(shopcartitem.getProductPrice()) / 100;
									discountedproducts_item_total.put(discountedproductid, "" + (Common.number("" + discountedproducts_item_total.get(discountedproductid)) - (Common.number(discount.getDiscountAmount()) * Common.number(shopcartitem.getProductPrice()) / 100)));
									discountedproducts_discount_total.put(discountedproductid, "" + (Common.number("" + discountedproducts_discount_total.get(discountedproductid)) + (Common.number(discount.getDiscountAmount()) * Common.number(shopcartitem.getProductPrice()) / 100)));
									discounted_order_total += Common.number(discount.getDiscountAmount()) * Common.number(shopcartitem.getProductPrice()) / 100;
								} else if ((Common.number(discount.getDiscountAmount()) - discounted_order_total) > Common.number(shopcartitem.getProductPrice())) {
									discountedproducts_item_total.put(discountedproductid, "" + (Common.number("" + discountedproducts_item_total.get(discountedproductid)) - Common.number(shopcartitem.getProductPrice())));
									discountedproducts_discount_total.put(discountedproductid, "" + (Common.number("" + discountedproducts_discount_total.get(discountedproductid)) + Common.number(shopcartitem.getProductPrice())));
									discounted_order_total += Common.number(shopcartitem.getProductPrice());
								} else {
									discountedproducts_item_total.put(discountedproductid, "" + (Common.number("" + discountedproducts_item_total.get(discountedproductid)) - (Common.number(discount.getDiscountAmount()) - discounted_order_total)));
									discountedproducts_discount_total.put(discountedproductid, "" + (Common.number("" + discountedproducts_discount_total.get(discountedproductid)) + (Common.number(discount.getDiscountAmount()) - discounted_order_total)));
									discounted_order_total += (Common.number(discount.getDiscountAmount()) - discounted_order_total);
								}
								discounted_item_quantity += 1;
//QQQ								if (discounted_order_total >= Common.number(discount.getDiscountAmount())) {
								if (discounted_item_quantity >= Common.number(discount.getDiscountQuantity2())) {
									Iterator discountedproductsitems = discountedproducts.keySet().iterator();
									while (discountedproductsitems.hasNext()) {
										String discountedproduct = "" + discountedproductsitems.next();
										if ((discountedproducts.get(discountedproduct) != null) && (discountedproducts_discounted.get(discountedproduct) != null)) {
											Orderitem discountedshopcartitem = (Orderitem)shoppingcartitems.get(discountedproduct);
											discountedproducts_discounted.put(discountedproduct, null);
											discountedshopcartitem.setItemTotal("" + discountedproducts_item_total.get(discountedproduct));
											discountedshopcartitem.setDiscountTotal("" + discountedproducts_discount_total.get(discountedproduct));
											if (discountedshopcartitem.getDiscountDescription().indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
												discountedshopcartitem.setDiscountDescription(discountedshopcartitem.getDiscountDescription() + discountedproducts_discount_description.get(discountedproduct));
											}
											if (discount_description.indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
												discount_description += discountedproducts_discount_description.get(discountedproduct);
											}
											shoppingcartitems.put(discountedproduct, discountedshopcartitem);
										}
									}
								}
							}
						}
						if (discounted_item_quantity >= 1) {
							Iterator discountedproductsitems = discountedproducts.keySet().iterator();
							while (discountedproductsitems.hasNext()) {
								String discountedproduct = "" + discountedproductsitems.next();
								if ((discountedproducts.get(discountedproduct) != null) && (discountedproducts_discounted.get(discountedproduct) != null)) {
									Orderitem discountedshopcartitem = (Orderitem)shoppingcartitems.get(discountedproduct);
									discountedproducts_discounted.put(discountedproduct, null);
									discountedshopcartitem.setItemTotal("" + discountedproducts_item_total.get(discountedproduct));
									discountedshopcartitem.setDiscountTotal("" + discountedproducts_discount_total.get(discountedproduct));
									if (discountedshopcartitem.getDiscountDescription().indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
										discountedshopcartitem.setDiscountDescription(discountedshopcartitem.getDiscountDescription() + discountedproducts_discount_description.get(discountedproduct));
									}
									if (discount_description.indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
										discount_description += discountedproducts_discount_description.get(discountedproduct);
									}
									shoppingcartitems.put(discountedproduct, discountedshopcartitem);
									discountedproducts.put(discountedproduct, null);
								}
							}
							discounted_item_quantity = 0;
							discounted_item_total = 0;
						}
						discount_total += discounted_order_total;

					}
				}

				if ((discount.getDiscountType().equals("volume")) && (discount.getDiscountProducts().equals("any"))) {
					if (discount.getDiscountCurrency().equals("%")) {
//QQQQQ MISSING: handle different shopcartitem currency
						HashMap<String,String> discountedproducts = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discounted = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_item_quantity = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_item_total = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discount_total = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discount_description = new HashMap<String,String>();
						int discounted_item_quantity = 0;
						double discounted_item_total = 0;
						double discounted_order_total = 0;
						Content products = new Content(text);
						products.init();
						products.records("", "", "", "", "", "", "", db, config, SQL);
						while (products.records("", "", "", "", "", "", "", db, config, "")) {
							String discountedproductid = "" + products.getId();
							Orderitem shopcartitem = (Orderitem)shoppingcartitems.get(discountedproductid);
							for (int i=0; i<Common.number(shopcartitem.getItemQuantity()); i++) {
								if (discountedproducts.get(discountedproductid) == null) {
									discountedproducts.put(discountedproductid, discountedproductid);
									discountedproducts_discounted.put(discountedproductid, null);
									discountedproducts_item_total.put(discountedproductid, shopcartitem.getItemTotal());
									discountedproducts_discount_total.put(discountedproductid, shopcartitem.getDiscountTotal());
									discountedproducts_discount_description.put(discountedproductid, "<span class=\"discount\">" + discount.getDiscountDescription() + "</span>" + " ");
								}
								discounted_item_total += Common.number(shopcartitem.getProductPrice());
								discountedproducts_discounted.put(discountedproductid, "true");
								discountedproducts_item_total.put(discountedproductid, "" + (Common.number("" + discountedproducts_item_total.get(discountedproductid)) - Common.number(shopcartitem.getProductPrice()) * Common.number(discount.getDiscountAmount()) / 100));
								discountedproducts_discount_total.put(discountedproductid, "" + (Common.number("" + discountedproducts_discount_total.get(discountedproductid)) + Common.number(shopcartitem.getProductPrice()) * Common.number(discount.getDiscountAmount()) / 100));
								discounted_item_quantity += 1;
								if (discounted_item_quantity == Common.number(discount.getDiscountQuantity2())) {
									if (discounted_item_total > (Common.number(discount.getDiscountAmount()) * Common.number(discount.getDiscountQuantity2()))) {
										Iterator discountedproductsitems = discountedproducts.keySet().iterator();
										while (discountedproductsitems.hasNext()) {
											String discountedproduct = "" + discountedproductsitems.next();
											if ((discountedproducts.get(discountedproduct) != null) && (discountedproducts_discounted.get(discountedproduct) != null)) {
												Orderitem discountedshopcartitem = (Orderitem)shoppingcartitems.get(discountedproduct);
												discountedproducts_discounted.put(discountedproduct, null);
												discountedshopcartitem.setItemTotal("" + discountedproducts_item_total.get(discountedproduct));
												discountedshopcartitem.setDiscountTotal("" + discountedproducts_discount_total.get(discountedproduct));
												if (discountedshopcartitem.getDiscountDescription().indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
													discountedshopcartitem.setDiscountDescription(discountedshopcartitem.getDiscountDescription() + discountedproducts_discount_description.get(discountedproduct));
												}
												if (discount_description.indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
													discount_description += discountedproducts_discount_description.get(discountedproduct);
												}
												shoppingcartitems.put(discountedproduct, discountedshopcartitem);
												discountedproducts.put(discountedproduct, null);
												discounted_order_total += Common.number("" + discountedproducts_discount_total.get(discountedproduct));
											}
										}
									}
									discounted_item_quantity = 0;
									discounted_item_total = 0;
								}
							}
						}
						if (discounted_item_quantity >= Common.number(discount.getDiscountQuantity())) {
							if (discounted_item_total > (Common.number(discount.getDiscountAmount()) * Common.number(discount.getDiscountQuantity2()))) {
								Iterator discountedproductsitems = discountedproducts.keySet().iterator();
								while (discountedproductsitems.hasNext()) {
									String discountedproduct = "" + discountedproductsitems.next();
									if ((discountedproducts.get(discountedproduct) != null) && (discountedproducts_discounted.get(discountedproduct) != null)) {
										Orderitem discountedshopcartitem = (Orderitem)shoppingcartitems.get(discountedproduct);
										discountedproducts_discounted.put(discountedproduct, null);
										discountedshopcartitem.setItemTotal("" + discountedproducts_item_total.get(discountedproduct));
										discountedshopcartitem.setDiscountTotal("" + discountedproducts_discount_total.get(discountedproduct));
										if (discountedshopcartitem.getDiscountDescription().indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
											discountedshopcartitem.setDiscountDescription(discountedshopcartitem.getDiscountDescription() + discountedproducts_discount_description.get(discountedproduct));
										}
										if (discount_description.indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
											discount_description += discountedproducts_discount_description.get(discountedproduct);
										}
										shoppingcartitems.put(discountedproduct, discountedshopcartitem);
										discountedproducts.put(discountedproduct, null);
										discounted_order_total += Common.number("" + discountedproducts_discount_total.get(discountedproduct));
									}
								}
							}
							discounted_item_quantity = 0;
							discounted_item_total = 0;
						}
						discount_total += discounted_order_total;

					} else if (discount.getDiscountOrderItems().equals("each")) {
//QQQQQ MISSING: handle different shopcartitem currency
						HashMap<String,String> discountedproducts = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discounted = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_item_quantity = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_item_total = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discount_total = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discount_description = new HashMap<String,String>();
						int discounted_item_quantity = 0;
						double discounted_item_total = 0;
						double discounted_order_total = 0;
						Content products = new Content(text);
						products.init();
						products.records("", "", "", "", "", "", "", db, config, SQL);
						while (products.records("", "", "", "", "", "", "", db, config, "")) {
							String discountedproductid = "" + products.getId();
							Orderitem shopcartitem = (Orderitem)shoppingcartitems.get(discountedproductid);
							long my_item_quantity = Common.intnumber(shopcartitem.getItemQuantity());
							if (shopcartitem_discount_item_quantity_rest.get(shopcartitem.getProductId()) != null) {
								my_item_quantity = Common.intnumber("" + shopcartitem_discount_item_quantity_rest.get(shopcartitem.getProductId()));
							}
							shopcartitem_discount_item_quantity_rest.put(shopcartitem.getProductId(), "" + my_item_quantity);
							for (int i=0; i<my_item_quantity; i++) {
								if (discountedproducts.get(discountedproductid) == null) {
									discountedproducts.put(discountedproductid, discountedproductid);
									discountedproducts_discounted.put(discountedproductid, null);
									discountedproducts_item_total.put(discountedproductid, shopcartitem.getItemTotal());
									discountedproducts_discount_total.put(discountedproductid, shopcartitem.getDiscountTotal());
									discountedproducts_discount_description.put(discountedproductid, "<span class=\"discount\">" + discount.getDiscountDescription() + "</span>" + " ");
								}
								discounted_item_total += Common.number(shopcartitem.getProductPrice());
								discountedproducts_discounted.put(discountedproductid, "true");
								if (Common.number("" + discountedproducts_item_total.get(discountedproductid)) > Common.number(discount.getDiscountAmount())) {
									discountedproducts_item_total.put(discountedproductid, "" + (Common.number("" + discountedproducts_item_total.get(discountedproductid)) - Common.number(discount.getDiscountAmount())));
									discountedproducts_discount_total.put(discountedproductid, "" + (Common.number("" + discountedproducts_discount_total.get(discountedproductid)) + Common.number(discount.getDiscountAmount())));
								} else {
									discountedproducts_item_total.put(discountedproductid, "0");
									discountedproducts_discount_total.put(discountedproductid, "" + (Common.number("" + discountedproducts_discount_total.get(discountedproductid)) + Common.number("" + discountedproducts_item_total.get(discountedproductid))));
								}
								discounted_item_quantity += 1;
								if (discounted_item_quantity == Common.number(discount.getDiscountQuantity2())) {
									if (discounted_item_total > (Common.number(discount.getDiscountAmount()) * Common.number(discount.getDiscountQuantity2()))) {
										Iterator discountedproductsitems = discountedproducts.keySet().iterator();
										while (discountedproductsitems.hasNext()) {
											String discountedproduct = "" + discountedproductsitems.next();
											if ((discountedproducts.get(discountedproduct) != null) && (discountedproducts_discounted.get(discountedproduct) != null)) {
												Orderitem discountedshopcartitem = (Orderitem)shoppingcartitems.get(discountedproduct);
												discountedproducts_discounted.put(discountedproduct, null);
												discountedshopcartitem.setItemTotal("" + discountedproducts_item_total.get(discountedproduct));
												discountedshopcartitem.setDiscountTotal("" + discountedproducts_discount_total.get(discountedproduct));
												if (discountedshopcartitem.getDiscountDescription().indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
													discountedshopcartitem.setDiscountDescription(discountedshopcartitem.getDiscountDescription() + discountedproducts_discount_description.get(discountedproduct));
												}
												if (discount_description.indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
													discount_description += discountedproducts_discount_description.get(discountedproduct);
												}
												shoppingcartitems.put(discountedproduct, discountedshopcartitem);
												discountedproducts.put(discountedproduct, null);
												discounted_order_total += Common.number("" + discountedproducts_discount_total.get(discountedproduct));
												shopcartitem_discount_item_quantity_rest.put(shopcartitem.getProductId(), "" + (Common.number("" + shopcartitem_discount_item_quantity_rest.get(shopcartitem.getProductId())) - discounted_item_quantity));
											}
										}
									}
									discounted_item_quantity = 0;
									discounted_item_total = 0;
								}
							}
						}
						if (discounted_item_quantity >= Common.number(discount.getDiscountQuantity())) {
							if (discounted_item_total > (Common.number(discount.getDiscountAmount()) * Common.number(discount.getDiscountQuantity2()))) {
								Iterator discountedproductsitems = discountedproducts.keySet().iterator();
								while (discountedproductsitems.hasNext()) {
									String discountedproduct = "" + discountedproductsitems.next();
									if ((discountedproducts.get(discountedproduct) != null) && (discountedproducts_discounted.get(discountedproduct) != null)) {
										Orderitem discountedshopcartitem = (Orderitem)shoppingcartitems.get(discountedproduct);
										discountedproducts_discounted.put(discountedproduct, null);
										discountedshopcartitem.setItemTotal("" + discountedproducts_item_total.get(discountedproduct));
										discountedshopcartitem.setDiscountTotal("" + discountedproducts_discount_total.get(discountedproduct));
										if (discountedshopcartitem.getDiscountDescription().indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
											discountedshopcartitem.setDiscountDescription(discountedshopcartitem.getDiscountDescription() + discountedproducts_discount_description.get(discountedproduct));
										}
										if (discount_description.indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
											discount_description += discountedproducts_discount_description.get(discountedproduct);
										}
										shoppingcartitems.put(discountedproduct, discountedshopcartitem);
										discountedproducts.put(discountedproduct, null);
										discounted_order_total += Common.number("" + discountedproducts_discount_total.get(discountedproduct));
										shopcartitem_discount_item_quantity_rest.put(discountedshopcartitem.getProductId(), "" + (Common.number("" + shopcartitem_discount_item_quantity_rest.get(discountedshopcartitem.getProductId())) - discounted_item_quantity));
									}
								}
							}
							discounted_item_quantity = 0;
							discounted_item_total = 0;
						}
						discount_total += discounted_order_total;

					} else if (discount.getDiscountOrderItems().equals("total")) {
//QQQQQ MISSING: handle different shopcartitem currency
						HashMap<String,String> discountedproducts = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discounted = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_item_quantity = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_item_total = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discount_total = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discount_description = new HashMap<String,String>();
						int discounted_item_quantity = 0;
						double discounted_item_total = 0;
						double discounted_order_total = 0;
						Content products = new Content(text);
						products.init();
						products.records("", "", "", "", "", "", "", db, config, SQL);
						while (products.records("", "", "", "", "", "", "", db, config, "")) {
							String discountedproductid = "" + products.getId();
							Orderitem shopcartitem = (Orderitem)shoppingcartitems.get(discountedproductid);
							long my_item_quantity = Common.intnumber(shopcartitem.getItemQuantity());
							if (shopcartitem_discount_item_quantity_rest.get(shopcartitem.getProductId()) != null) {
								my_item_quantity = Common.intnumber("" + shopcartitem_discount_item_quantity_rest.get(shopcartitem.getProductId()));
							}
							shopcartitem_discount_item_quantity_rest.put(shopcartitem.getProductId(), "" + my_item_quantity);
							for (int i=0; i<my_item_quantity; i++) {
								if (discountedproducts.get(discountedproductid) == null) {
									discountedproducts.put(discountedproductid, discountedproductid);
									discountedproducts_discounted.put(discountedproductid, null);
									discountedproducts_item_total.put(discountedproductid, shopcartitem.getItemTotal());
									discountedproducts_discount_total.put(discountedproductid, shopcartitem.getDiscountTotal());
									discountedproducts_discount_description.put(discountedproductid, "<span class=\"discount\">" + discount.getDiscountDescription() + "</span>" + " ");
								}
								discounted_item_quantity += 1;
								if (discounted_item_quantity == Common.number(discount.getDiscountQuantity())) {
									discountedproducts_discounted.put(discountedproductid, "true");
									if (Common.number("" + discountedproducts_item_total.get(discountedproductid)) > Common.number(discount.getDiscountAmount())) {
										discountedproducts_item_total.put(discountedproductid, "" + (Common.number("" + discountedproducts_item_total.get(discountedproductid)) - Common.number(discount.getDiscountAmount())));
										discountedproducts_discount_total.put(discountedproductid, "" + (Common.number("" + discountedproducts_discount_total.get(discountedproductid)) + Common.number(discount.getDiscountAmount())));
										discounted_item_total += Common.number(discount.getDiscountAmount());
									} else {
										discountedproducts_item_total.put(discountedproductid, "0");
										discountedproducts_discount_total.put(discountedproductid, "" + (Common.number("" + discountedproducts_discount_total.get(discountedproductid)) + Common.number("" + discountedproducts_item_total.get(discountedproductid))));
										discounted_item_total += Common.number("" + discountedproducts_item_total.get(discountedproductid));
									}
								} else if (discounted_item_quantity > Common.number(discount.getDiscountQuantity())) {
									discountedproducts_discounted.put(discountedproductid, "true");
								}
								if (discounted_item_quantity == Common.number(discount.getDiscountQuantity2())) {
									Iterator discountedproductsitems = discountedproducts.keySet().iterator();
									while (discountedproductsitems.hasNext()) {
										String discountedproduct = "" + discountedproductsitems.next();
										if ((discountedproducts.get(discountedproduct) != null) && (discountedproducts_discounted.get(discountedproduct) != null)) {
											Orderitem discountedshopcartitem = (Orderitem)shoppingcartitems.get(discountedproduct);
											// substract old discount total before the new discount total is calculated and added again below
											discounted_order_total -= Common.number(discountedshopcartitem.getDiscountTotal());
											discountedproducts_discounted.put(discountedproduct, null);
											discountedshopcartitem.setItemTotal("" + discountedproducts_item_total.get(discountedproduct));
											discountedshopcartitem.setDiscountTotal("" + discountedproducts_discount_total.get(discountedproduct));
											if (discountedshopcartitem.getDiscountDescription().indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
												discountedshopcartitem.setDiscountDescription(discountedshopcartitem.getDiscountDescription() + discountedproducts_discount_description.get(discountedproduct));
											}
											if (discount_description.indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
												discount_description += discountedproducts_discount_description.get(discountedproduct);
											}
											shoppingcartitems.put(discountedproduct, discountedshopcartitem);
											discountedproducts.put(discountedproduct, null);
											discounted_order_total += Common.number("" + discountedproducts_discount_total.get(discountedproduct));
											shopcartitem_discount_item_quantity_rest.put(shopcartitem.getProductId(), "" + (Common.number("" + shopcartitem_discount_item_quantity_rest.get(shopcartitem.getProductId())) - discounted_item_quantity));
										}
									}
									discounted_item_quantity = 0;
									discounted_item_total = 0;
								}
							}
						}
						if (discounted_item_quantity >= Common.number(discount.getDiscountQuantity())) {
							Iterator discountedproductsitems = discountedproducts.keySet().iterator();
							while (discountedproductsitems.hasNext()) {
								String discountedproduct = "" + discountedproductsitems.next();
								if ((discountedproducts.get(discountedproduct) != null) && (discountedproducts_discounted.get(discountedproduct) != null)) {
									Orderitem discountedshopcartitem = (Orderitem)shoppingcartitems.get(discountedproduct);
									// substract old discount total before the new discount total is calculated and added again below
									discounted_order_total -= Common.number(discountedshopcartitem.getDiscountTotal());
									discountedproducts_discounted.put(discountedproduct, null);
									discountedshopcartitem.setItemTotal("" + discountedproducts_item_total.get(discountedproduct));
									discountedshopcartitem.setDiscountTotal("" + discountedproducts_discount_total.get(discountedproduct));
									if (discountedshopcartitem.getDiscountDescription().indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
										discountedshopcartitem.setDiscountDescription(discountedshopcartitem.getDiscountDescription() + discountedproducts_discount_description.get(discountedproduct));
									}
									if (discount_description.indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
										discount_description += discountedproducts_discount_description.get(discountedproduct);
									}
									shoppingcartitems.put(discountedproduct, discountedshopcartitem);
									discountedproducts.put(discountedproduct, null);
									discounted_order_total += Common.number("" + discountedproducts_discount_total.get(discountedproduct));
									shopcartitem_discount_item_quantity_rest.put(discountedshopcartitem.getProductId(), "" + (Common.number("" + shopcartitem_discount_item_quantity_rest.get(discountedshopcartitem.getProductId())) - discounted_item_quantity));
								}
							}
							discounted_item_quantity = 0;
							discounted_item_total = 0;
						}
						discount_total += discounted_order_total;
					}
				}

				if ((discount.getDiscountType().equals("fixed")) && (discount.getDiscountProducts().equals("any"))) {
					if (discount.getDiscountOrderItems().equals("each")) {
//QQQQQ MISSING: handle different shopcartitem currency
						HashMap<String,String> discountedproducts = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discounted = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_item_quantity = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_item_total = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discount_total = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discount_description = new HashMap<String,String>();
						int discounted_item_quantity = 0;
						double discounted_item_total = 0;
						double discounted_order_total = 0;
						Content products = new Content(text);
						products.init();
						products.records("", "", "", "", "", "", "", db, config, SQL);
						while (products.records("", "", "", "", "", "", "", db, config, "")) {
							String discountedproductid = "" + products.getId();
							Orderitem shopcartitem = (Orderitem)shoppingcartitems.get(discountedproductid);
							if ((Common.number(shopcartitem.getProductPrice()) < Common.number(discount.getDiscountAmount())) && (discounted_item_quantity >= Common.number(discount.getDiscountQuantity()))) break;
							if ((Common.number(shopcartitem.getItemQuantity()) * Common.number(discount.getDiscountAmount())) > Common.number(shopcartitem.getItemTotal())) break;
							for (int i=0; i<Common.number(shopcartitem.getItemQuantity()); i++) {
								if (discountedproducts.get(discountedproductid) == null) {
									discountedproducts.put(discountedproductid, discountedproductid);
									discountedproducts_discounted.put(discountedproductid, null);
									discountedproducts_item_total.put(discountedproductid, shopcartitem.getItemTotal());
									discountedproducts_discount_total.put(discountedproductid, shopcartitem.getDiscountTotal());
									discountedproducts_discount_description.put(discountedproductid, "<span class=\"discount\">" + discount.getDiscountDescription() + "</span>" + " ");
								}
								discounted_item_total += Common.number(shopcartitem.getProductPrice());
								discountedproducts_discounted.put(discountedproductid, "true");
								discountedproducts_item_total.put(discountedproductid, "" + (Common.number("" + discountedproducts_item_total.get(discountedproductid)) - (Common.number(shopcartitem.getProductPrice()) - Common.number(discount.getDiscountAmount()))));
								discountedproducts_discount_total.put(discountedproductid, "" + (Common.number("" + discountedproducts_discount_total.get(discountedproductid)) + (Common.number(shopcartitem.getProductPrice()) - Common.number(discount.getDiscountAmount()))));
								discounted_item_quantity += 1;
								if (discounted_item_quantity == Common.number(discount.getDiscountQuantity2())) {
									if (discounted_item_total > (Common.number(discount.getDiscountAmount()) * discounted_item_quantity)) {
										Iterator discountedproductsitems = discountedproducts.keySet().iterator();
										while (discountedproductsitems.hasNext()) {
											String discountedproduct = "" + discountedproductsitems.next();
											if ((discountedproducts.get(discountedproduct) != null) && (discountedproducts_discounted.get(discountedproduct) != null)) {
												Orderitem discountedshopcartitem = (Orderitem)shoppingcartitems.get(discountedproduct);
												discountedproducts_discounted.put(discountedproduct, null);
												discountedshopcartitem.setItemTotal("" + discountedproducts_item_total.get(discountedproduct));
												discountedshopcartitem.setDiscountTotal("" + discountedproducts_discount_total.get(discountedproduct));
												if (discountedshopcartitem.getDiscountDescription().indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
													discountedshopcartitem.setDiscountDescription(discountedshopcartitem.getDiscountDescription() + discountedproducts_discount_description.get(discountedproduct));
												}
												if (discount_description.indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
													discount_description += discountedproducts_discount_description.get(discountedproduct);
												}
												shoppingcartitems.put(discountedproduct, discountedshopcartitem);
												discountedproducts.put(discountedproduct, null);
												discounted_order_total += Common.number("" + discountedproducts_discount_total.get(discountedproduct));
											}
										}
									}
									discounted_item_quantity = 0;
									discounted_item_total = 0;
								}
							}
						}
						if (discounted_item_quantity >= Common.number(discount.getDiscountQuantity())) {
							if (discounted_item_total > (Common.number(discount.getDiscountAmount()) * discounted_item_quantity)) {
								Iterator discountedproductsitems = discountedproducts.keySet().iterator();
								while (discountedproductsitems.hasNext()) {
									String discountedproduct = "" + discountedproductsitems.next();
									if ((discountedproducts.get(discountedproduct) != null) && (discountedproducts_discounted.get(discountedproduct) != null)) {
										Orderitem discountedshopcartitem = (Orderitem)shoppingcartitems.get(discountedproduct);
										discountedproducts_discounted.put(discountedproduct, null);
										discountedshopcartitem.setItemTotal("" + discountedproducts_item_total.get(discountedproduct));
										discountedshopcartitem.setDiscountTotal("" + discountedproducts_discount_total.get(discountedproduct));
										if (discountedshopcartitem.getDiscountDescription().indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
											discountedshopcartitem.setDiscountDescription(discountedshopcartitem.getDiscountDescription() + discountedproducts_discount_description.get(discountedproduct));
										}
										if (discount_description.indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
											discount_description += discountedproducts_discount_description.get(discountedproduct);
										}
										shoppingcartitems.put(discountedproduct, discountedshopcartitem);
										discountedproducts.put(discountedproduct, null);
										discounted_order_total += Common.number("" + discountedproducts_discount_total.get(discountedproduct));
									}
								}
							}
							discounted_item_quantity = 0;
							discounted_item_total = 0;
						}
						discount_total += discounted_order_total;

					} else if (discount.getDiscountOrderItems().equals("total")) {
//QQQQQ MISSING: handle different shopcartitem currency
						HashMap<String,String> discountedproducts = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discounted = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_item_quantity = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_item_total = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discount_total = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discount_description = new HashMap<String,String>();
						int discounted_item_quantity = 0;
						double discounted_item_total = 0;
						double discounted_order_total = 0;
						Content products = new Content(text);
						products.init();
						products.records("", "", "", "", "", "", "", db, config, SQL);
						while (products.records("", "", "", "", "", "", "", db, config, "")) {
							String discountedproductid = "" + products.getId();
							Orderitem shopcartitem = (Orderitem)shoppingcartitems.get(discountedproductid);
							long my_item_quantity = Common.intnumber(shopcartitem.getItemQuantity());
							if (shopcartitem_discount_item_quantity_rest.get(shopcartitem.getProductId()) != null) {
								my_item_quantity = Common.intnumber("" + shopcartitem_discount_item_quantity_rest.get(shopcartitem.getProductId()));
							}
							shopcartitem_discount_item_quantity_rest.put(shopcartitem.getProductId(), "" + my_item_quantity);
							for (int i=0; i<my_item_quantity; i++) {
								if (discountedproducts.get(discountedproductid) == null) {
									discountedproducts.put(discountedproductid, discountedproductid);
									discountedproducts_discounted.put(discountedproductid, null);
									discountedproducts_item_total.put(discountedproductid, shopcartitem.getItemTotal());
									discountedproducts_discount_total.put(discountedproductid, shopcartitem.getDiscountTotal());
									discountedproducts_discount_description.put(discountedproductid, "<span class=\"discount\">" + discount.getDiscountDescription() + "</span>" + " ");
								}
								discounted_item_total += Common.number(shopcartitem.getProductPrice());
								if (discounted_item_total > Common.number(discount.getDiscountAmount())) {
									discountedproducts_discounted.put(discountedproductid, "true");
									discountedproducts_item_total.put(discountedproductid, "" + (Common.number("" + discountedproducts_item_total.get(discountedproductid)) - (discounted_item_total - Common.number(discount.getDiscountAmount()))));
									discountedproducts_discount_total.put(discountedproductid, "" + (Common.number("" + discountedproducts_discount_total.get(discountedproductid)) + discounted_item_total - Common.number(discount.getDiscountAmount())));
									discounted_item_total = Common.number(discount.getDiscountAmount());
								}
								discounted_item_quantity += 1;
								if (discounted_item_quantity == Common.number(discount.getDiscountQuantity2())) {
									Iterator discountedproductsitems = discountedproducts.keySet().iterator();
									while (discountedproductsitems.hasNext()) {
										String discountedproduct = "" + discountedproductsitems.next();
										if ((discountedproducts.get(discountedproduct) != null) && (discountedproducts_discounted.get(discountedproduct) != null)) {
											Orderitem discountedshopcartitem = (Orderitem)shoppingcartitems.get(discountedproduct);
											// substract old discount total before the new discount total is calculated and added again below
											discounted_order_total -= Common.number(discountedshopcartitem.getDiscountTotal());
											discountedproducts_discounted.put(discountedproduct, null);
											discountedshopcartitem.setItemTotal("" + discountedproducts_item_total.get(discountedproduct));
											discountedshopcartitem.setDiscountTotal("" + discountedproducts_discount_total.get(discountedproduct));
											if (discountedshopcartitem.getDiscountDescription().indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
												discountedshopcartitem.setDiscountDescription(discountedshopcartitem.getDiscountDescription() + discountedproducts_discount_description.get(discountedproduct));
											}
											if (discount_description.indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
												discount_description += discountedproducts_discount_description.get(discountedproduct);
											}
											shoppingcartitems.put(discountedproduct, discountedshopcartitem);
											discountedproducts.put(discountedproduct, null);
											discounted_order_total += Common.number("" + discountedproducts_discount_total.get(discountedproduct));
											shopcartitem_discount_item_quantity_rest.put(shopcartitem.getProductId(), "" + (Common.number("" + shopcartitem_discount_item_quantity_rest.get(shopcartitem.getProductId())) - discounted_item_quantity));
										}
									}
									discounted_item_quantity = 0;
									discounted_item_total = 0;
								}
							}
						}
						if (discounted_item_quantity >= Common.number(discount.getDiscountQuantity())) {
							Iterator discountedproductsitems = discountedproducts.keySet().iterator();
							while (discountedproductsitems.hasNext()) {
								String discountedproduct = "" + discountedproductsitems.next();
								if ((discountedproducts.get(discountedproduct) != null) && (discountedproducts_discounted.get(discountedproduct) != null)) {
									Orderitem discountedshopcartitem = (Orderitem)shoppingcartitems.get(discountedproduct);
									// substract old discount total before the new discount total is calculated and added again below
									discounted_order_total -= Common.number(discountedshopcartitem.getDiscountTotal());
									discountedproducts_discounted.put(discountedproduct, null);
									discountedshopcartitem.setItemTotal("" + discountedproducts_item_total.get(discountedproduct));
									discountedshopcartitem.setDiscountTotal("" + discountedproducts_discount_total.get(discountedproduct));
									if (discountedshopcartitem.getDiscountDescription().indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
										discountedshopcartitem.setDiscountDescription(discountedshopcartitem.getDiscountDescription() + discountedproducts_discount_description.get(discountedproduct));
									}
									if (discount_description.indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
										discount_description += discountedproducts_discount_description.get(discountedproduct);
									}
									shoppingcartitems.put(discountedproduct, discountedshopcartitem);
									discountedproducts.put(discountedproduct, null);
									discounted_order_total += Common.number("" + discountedproducts_discount_total.get(discountedproduct));
									shopcartitem_discount_item_quantity_rest.put(discountedshopcartitem.getProductId(), "" + (Common.number("" + shopcartitem_discount_item_quantity_rest.get(discountedshopcartitem.getProductId())) - discounted_item_quantity));
								}
							}
							discounted_item_quantity = 0;
							discounted_item_total = 0;
						}
						discount_total += discounted_order_total;
					}
				}

				if ((discount.getDiscountType().equals("bogof")) && (discount.getDiscountProducts().equals("any"))) {
//QQQQQ MISSING: handle different shopcartitem currency
						HashMap<String,String> discountedproducts = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discounted = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_item_quantity = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_item_total = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discount_total = new HashMap<String,String>();
						HashMap<String,String> discountedproducts_discount_description = new HashMap<String,String>();
						int discounted_item_quantity = 0;
						double discounted_item_total = 0;
						double discounted_order_total = 0;
						Content products = new Content(text);
						products.init();
						products.records("", "", "", "", "", "", "", db, config, SQL);
						while (products.records("", "", "", "", "", "", "", db, config, "")) {
							String discountedproductid = "" + products.getId();
							Orderitem shopcartitem = (Orderitem)shoppingcartitems.get(discountedproductid);
							for (int i=0; i<Common.number(shopcartitem.getItemQuantity()); i++) {
								if (discountedproducts.get(discountedproductid) == null) {
									discountedproducts.put(discountedproductid, discountedproductid);
									discountedproducts_discounted.put(discountedproductid, null);
									discountedproducts_item_total.put(discountedproductid, shopcartitem.getItemTotal());
									discountedproducts_discount_total.put(discountedproductid, shopcartitem.getDiscountTotal());
									discountedproducts_discount_description.put(discountedproductid, "<span class=\"discount\">" + discount.getDiscountDescription() + "</span>" + " ");
								}

								if (discount.getDiscountOrderItems().equals("off")) {
									if (discount.getDiscountCurrency().equals("%")) {
										my_discount_amount = Common.number(discount.getDiscountAmount()) * Common.number(shopcartitem.getProductPrice()) / 100;
									} else {
//QQQQQ MISSING: handle different shopcartitem currency
										if (Common.number(shopcartitem.getProductPrice()) > Common.number(discount.getDiscountAmount())) {
											my_discount_amount = Common.number(discount.getDiscountAmount());
										} else {
											my_discount_amount = Common.number(shopcartitem.getProductPrice());
										}
									}
								} else if (discount.getDiscountOrderItems().equals("each")) {
									if (discount.getDiscountCurrency().equals("%")) {
										my_discount_amount = (100 - Common.number(discount.getDiscountAmount())) * Common.number(shopcartitem.getProductPrice()) / 100;
									} else if (Common.number(shopcartitem.getProductPrice()) > Common.number(discount.getDiscountAmount())) {
//QQQQQ MISSING: handle different shopcartitem currency
										my_discount_amount = (Common.number(shopcartitem.getProductPrice()) - Common.number(discount.getDiscountAmount()));
									}
								} else if (discount.getDiscountOrderItems().equals("total")) {
									if (discount.getDiscountCurrency().equals("%")) {
										my_discount_amount = (100 - Common.number(discount.getDiscountAmount())) * Common.number(shopcartitem.getProductPrice()) / 100;
									} else if (Common.number(shopcartitem.getProductPrice()) * Common.number(discount.getDiscountQuantity2()) > Common.number(discount.getDiscountAmount())) {
//QQQQQ MISSING: handle different shopcartitem currency
										my_discount_amount = ((Common.number(shopcartitem.getProductPrice()) * Common.number(discount.getDiscountQuantity2())) - Common.number(discount.getDiscountAmount()));
									}
								}
								discounted_item_quantity += 1;
								if (discounted_item_quantity > Common.number(discount.getDiscountQuantity())) {
									discountedproducts_discounted.put(discountedproductid, "true");
									discountedproducts_item_total.put(discountedproductid, "" + (Common.number("" + discountedproducts_item_total.get(discountedproductid)) - my_discount_amount));
									discountedproducts_discount_total.put(discountedproductid, "" + (Common.number("" + discountedproducts_discount_total.get(discountedproductid)) + my_discount_amount));
									discounted_item_total += my_discount_amount;
								} else {
									discountedproducts_discounted.put(discountedproductid, "true");
								}
								if (discounted_item_quantity == Common.number(discount.getDiscountQuantity()) + Common.number(discount.getDiscountQuantity2())) {
									Iterator discountedproductsitems = discountedproducts.keySet().iterator();
									while (discountedproductsitems.hasNext()) {
										String discountedproduct = "" + discountedproductsitems.next();
										if ((discountedproducts.get(discountedproduct) != null) && (discountedproducts_discounted.get(discountedproduct) != null)) {
											Orderitem discountedshopcartitem = (Orderitem)shoppingcartitems.get(discountedproduct);
											discountedproducts_discounted.put(discountedproduct, null);
											discounted_order_total += (Common.number("" + discountedproducts_discount_total.get(discountedproduct)) - Common.number("" + discountedshopcartitem.getDiscountTotal()));
											discountedshopcartitem.setItemTotal("" + discountedproducts_item_total.get(discountedproduct));
											discountedshopcartitem.setDiscountTotal("" + discountedproducts_discount_total.get(discountedproduct));
											if (discountedshopcartitem.getDiscountDescription().indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
												discountedshopcartitem.setDiscountDescription(discountedshopcartitem.getDiscountDescription() + discountedproducts_discount_description.get(discountedproduct));
											}
											if (discount_description.indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
												discount_description += discountedproducts_discount_description.get(discountedproduct);
											}
											shoppingcartitems.put(discountedproduct, discountedshopcartitem);
											discountedproducts.put(discountedproduct, null);
										}
									}
									discounted_item_quantity = 0;
									discounted_item_total = 0;
								}
							}
						}
						if (discounted_item_quantity > Common.number(discount.getDiscountQuantity())) {
							Iterator discountedproductsitems = discountedproducts.keySet().iterator();
							while (discountedproductsitems.hasNext()) {
								String discountedproduct = "" + discountedproductsitems.next();
								if ((discountedproducts.get(discountedproduct) != null) && (discountedproducts_discounted.get(discountedproduct) != null)) {
									Orderitem discountedshopcartitem = (Orderitem)shoppingcartitems.get(discountedproduct);
									discountedproducts_discounted.put(discountedproduct, null);
									discounted_order_total += (Common.number("" + discountedproducts_discount_total.get(discountedproduct)) - Common.number("" + discountedshopcartitem.getDiscountTotal()));
									discountedshopcartitem.setItemTotal("" + discountedproducts_item_total.get(discountedproduct));
									discountedshopcartitem.setDiscountTotal("" + discountedproducts_discount_total.get(discountedproduct));
									if (discountedshopcartitem.getDiscountDescription().indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
										discountedshopcartitem.setDiscountDescription(discountedshopcartitem.getDiscountDescription() + discountedproducts_discount_description.get(discountedproduct));
									}
									if (discount_description.indexOf(""+discountedproducts_discount_description.get(discountedproduct)) < 0) {
										discount_description += discountedproducts_discount_description.get(discountedproduct);
									}
									shoppingcartitems.put(discountedproduct, discountedshopcartitem);
									discountedproducts.put(discountedproduct, null);
								}
							}
							discounted_item_quantity = 0;
							discounted_item_total = 0;
						}
						discount_total += discounted_order_total;
				}

				if (discount.getDiscountCurrency().equals("%")) {
					// ok
				} else if (! discount.getDiscountCurrency().equals(order_currency.getId())) {
					Currency discount_currency = new Currency();
					discount_currency.read(db, discount.getDiscountCurrency());
				}
			}

			order_subtotal = order_subtotal - discount_total;
			order_total = order_total - discount_total;
} // end of discounts calculation

			setDiscountDescription("" + discount_description);
			setDiscountTotal("" + discount_total);
			setOrderQuantity("" + Common.integernumber("" + order_quantity));
			setOrderSubtotal("" + order_subtotal);
			setOrderTotal("" + order_total);

			String shipping_description = "";
			String tax_description = "";
			double shipping_total = 0;
			double tax_total = 0;

			Shipping shipping = new Shipping();
			Tax tax = new Tax();
			results.init();
			results.records("", "", "", "", "", "", "", db, config, productsSQL);
			while (results.records("", "", "", "", "", "", "", db, config, "")) {
				String productid = results.getId();
				String [] shopcartitem_elements = ("" + shoppingcart.get(productid)).split("\\|");
				if ((! results.getVersionMaster().equals("")) && (! results.getVersionMaster().equals("0")) && ((! results.getVersion().equals("")) || (! results.getDevice().equals("")) || (! results.getUsersegment().equals("")) || (! results.getUsertest().equals("")))) {
					if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
						results.preview_read(db, config, results.getVersionMaster(), session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, null);
					} else {
						results.public_read(db, config, results.getVersionMaster(), session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, null);
					}
				} else {
					if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
						results.preview_read(db, config, productid, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, null);
					} else {
						results.public_read(db, config, productid, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, null);
					}
				}

				String display_price = "";
				if (results.getProductCurrency().equals("")) {
					results.setProductCurrency(config.get(db, "default_currency"));
				}
				if (results.getProductCurrency().equals(order_currency.getId())) {
					display_price = results.getProductPrice();
				} else {
					Currency product_currency = new Currency();
					product_currency.read(db, results.getProductCurrency());
					display_price = "" + (Common.number(results.getProductPrice()) * Common.number(order_currency.getRate()) / Common.number(product_currency.getRate()));
				}

				Orderitem shopcartitem = new Orderitem();
				shopcartitem = (Orderitem)shoppingcartitems.get(productid);
				shopcartitem.setShippingTotal("0");
				shopcartitem.setTaxTotal("0");

//QQQ				order_availability += doProductAvailability(server, mysession, myrequest, myresponse, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, stockcheck, shopcartitem);
				if (true) {
					shipping = new Shipping();
					SQL = "select * from shipping";
					SQL = SQL + " where ((" + db.is_blank("country") + ") or (country=" + db.quote(delivery_country) + "))";
					SQL = SQL + " and ((" + db.is_blank("state") + ") or (state=" + db.quote(delivery_state) + "))";
					SQL = SQL + " and (((quantity_from is null) or (quantity_from <= " + order_quantity + ")) and ((quantity_to is null) or (quantity_to = 0) or (quantity_to >= " + order_quantity + ")))";
					SQL = SQL + " and (((total_from is null) or (total_from <= " + order_subtotal + ")) and ((total_to is null) or (total_to = 0) or (total_to >= " + order_subtotal + ")))";
					if (order_weight > 0) {
						SQL = SQL + " and ((((total_weight_from is null) or (total_weight_from = 0)) and ((total_weight_to is null) or (total_weight_to = 0))) or ((total_weight_from <= " + order_weight + ") and (total_weight_to >= " + order_weight + ")))";
					}
					if (order_volume > 0) {
						SQL = SQL + " and ((((total_volume_from is null) or (total_volume_from = 0)) and ((total_volume_to is null) or (total_volume_to = 0))) or ((total_volume_from <= " + order_volume + ") and (total_volume_to >= " + order_volume + ")))";
					}
					SQL = SQL + " and ((product_id=" + db.quote(productid) + ")";
					SQL = SQL + " or (" + db.is_blank("product_id") + " and ((" + db.is_blank("product_group") + ") or (product_group=" + db.quote(results.getContentGroup()) + ")) and ((" + db.is_blank("product_type") + ") or (product_type=" + db.quote(results.getContentType()) + ")) and ((" + db.is_not_blank("product_group") + ") or (" + db.is_not_blank("product_type") + ")))";
					SQL = SQL + " or ((" + db.is_blank("product_group") + ") and (" + db.is_blank("product_type") + ") and (((product_weight_from is not null) and (product_weight_from <> 0)) or ((product_weight_to is not null) and (product_weight_to <> 0)) or ((product_volume_from is not null) and (product_volume_from <> 0)) or ((product_volume_to is not null) and (product_volume_to <> 0)) or ((product_width_from is not null) and (product_width_from <> 0)) or ((product_width_to is not null) and (product_width_to <> 0)) or ((product_height_from is not null) and (product_height_from <> 0)) or ((product_height_to is not null) and (product_height_to <> 0)) or ((product_depth_from is not null) and (product_depth_from <> 0)) or ((product_depth_to is not null) and (product_depth_to <> 0))))";
					SQL = SQL + ")";
					if (Common.number(shopcartitem.getProductWeight()) > 0) {
						SQL = SQL + " and (((product_weight_from is null) or (product_weight_from <= " + shopcartitem.getProductWeight() + ")) and ((product_weight_to is null) or (product_weight_to = 0) or (product_weight_to >= " + shopcartitem.getProductWeight() + ")))";
					}
					if (Common.number(shopcartitem.getProductVolume()) > 0) {
						SQL = SQL + " and (((product_volume_from is null) or (product_volume_from <= " + shopcartitem.getProductVolume() + ")) and ((product_volume_to is null) or (product_volume_to = 0) or (product_volume_to >= " + shopcartitem.getProductVolume() + ")))";
					}
					if (Common.number(shopcartitem.getProductWidth()) > 0) {
						SQL = SQL + " and (((product_width_from is null) or (product_width_from <= " + shopcartitem.getProductWidth() + ")) and ((product_width_to is null) or (product_width_to = 0) or (product_width_to >= " + shopcartitem.getProductWidth() + ")))";
					}
					if (Common.number(shopcartitem.getProductHeight()) > 0) {
						SQL = SQL + " and (((product_height_from is null) or (product_height_from <= " + shopcartitem.getProductHeight() + ")) and ((product_height_to is null) or (product_height_to = 0) or (product_height_to >= " + shopcartitem.getProductHeight() + ")))";
					}
					if (Common.number(shopcartitem.getProductDepth()) > 0) {
						SQL = SQL + " and (((product_depth_from is null) or (product_depth_from <= " + shopcartitem.getProductDepth() + ")) and ((product_depth_to is null) or (product_depth_to = 0) or (product_depth_to >= " + shopcartitem.getProductDepth() + ")))";
					}
					String shopcartitem_shipping_description = "";
					double shopcartitem_shipping_total = 0;
					boolean shopcartitem_shipping_total_flag = false;
					String shopcartitem_shipping_max_description = "";
					double shopcartitem_shipping_max_total = 0;
					boolean shopcartitem_shipping_max_total_flag = false;
					shipping.records(db, SQL);
					while (shipping.records(db, "")) {
						if (shipping.getShipCurrency().equals("")) {
							shipping.setShipCurrency(config.get(db, "default_currency"));
						}
						double my_shipping_amount = 0;
						double my_shipping_max_amount = 0;
						if (shipping.getShipCurrency().equals(order_currency.getId())) {
							my_shipping_amount = Common.number(shipping.getShipOrder()) + (Common.number(shipping.getShipItem()) * Common.number(shopcartitem.getItemQuantity())) + (Common.number(shipping.getShipPercent()) * Common.number(shopcartitem.getItemTotal()) / 100);
							my_shipping_max_amount = Common.number(shipping.getShipTotal());
						} else {
							Currency shipping_currency = new Currency();
							shipping_currency.read(db, shipping.getShipCurrency());
							my_shipping_amount = (Common.number(shipping.getShipOrder()) * Common.number(order_currency.getRate()) / Common.number(shipping_currency.getRate())) + (Common.number(shipping.getShipItem()) * Common.number(shopcartitem.getItemQuantity()) * Common.number(order_currency.getRate()) / Common.number(shipping_currency.getRate())) + (Common.number(shipping.getShipPercent()) * Common.number(shopcartitem.getItemTotal()) / 100);
							my_shipping_max_amount = (Common.number(shipping.getShipTotal()) * Common.number(order_currency.getRate()) / Common.number(shipping_currency.getRate()));
						}
						shopcartitem_shipping_description = shopcartitem_shipping_description + "<span class=\"shipping\">" + shopcartitem.getItemQuantity() + " x " + shipping.getShipDescription() + "</span>" + " ";
						shopcartitem_shipping_total = shopcartitem_shipping_total + my_shipping_amount;
						if (shopcartitem_shipping_total > 0) {
							shopcartitem_shipping_total_flag = true;
						}
						if ((! shipping.getShipTotal().equals("")) && (Common.number(shipping.getShipTotal()) > 0)) {
							if ((my_shipping_max_amount < shopcartitem_shipping_max_total) || (! shopcartitem_shipping_max_total_flag)) {
								shopcartitem_shipping_max_description = "<span class=\"shipping\">" + shopcartitem.getItemQuantity() + " x " + shipping.getShipDescription() + "</span>" + " ";
								shopcartitem_shipping_max_total = my_shipping_max_amount;
								shopcartitem_shipping_max_total_flag = true;
							}
						}
					}
					if (shopcartitem_shipping_max_total_flag && ((shopcartitem_shipping_max_total < shopcartitem_shipping_total) || (! shopcartitem_shipping_total_flag))) {
						shopcartitem_shipping_description = shopcartitem_shipping_max_description;
						shopcartitem_shipping_total = shopcartitem_shipping_max_total;
					}
					shopcartitem.setShippingDescription(shopcartitem.getShippingDescription() + shopcartitem_shipping_description);
					shipping_description = shipping_description + shopcartitem_shipping_description;
					shopcartitem.setShippingTotal(shopcartitem.getShippingTotal() + shopcartitem_shipping_total);
					shipping_total = shipping_total + shopcartitem_shipping_total;

					tax = new Tax();
					SQL = "select * from tax";
					SQL = SQL + " where ((" + db.is_blank("country") + ") or (country=" + db.quote(delivery_country) + "))";
					SQL = SQL + " and ((" + db.is_blank("state") + ") or (state=" + db.quote(delivery_state) + "))";
					SQL = SQL + " and ";
					SQL = SQL + "((";

					// applies to products
					SQL = SQL + "(((quantity_from is null) or (quantity_from <= " + order_quantity + ")) and ((quantity_to is null) or (quantity_to = 0) or (quantity_to >= " + order_quantity + ")))";
					SQL = SQL + " and (((total_from is null) or (total_from <= " + order_subtotal + ")) and ((total_to is null) or (total_to = 0) or (total_to >= " + order_subtotal + ")))";
					if (order_weight > 0) {
						SQL = SQL + " and ((((total_weight_from is null) or (total_weight_from = 0)) and ((total_weight_to is null) or (total_weight_to = 0))) or ((total_weight_from <= " + order_weight + ") and (total_weight_to >= " + order_weight + ")))";
					}
					if (order_volume > 0) {
						SQL = SQL + " and ((((total_volume_from is null) or (total_volume_from = 0)) and ((total_volume_to is null) or (total_volume_to = 0))) or ((total_volume_from <= " + order_volume + ") and (total_volume_to >= " + order_volume + ")))";
					}
					SQL = SQL + " and ((product_id=" + db.quote(productid) + ")";
					SQL = SQL + " or (" + db.is_blank("product_id") + " and ((" + db.is_blank("product_group") + ") or (product_group=" + db.quote(results.getContentGroup()) + ")) and ((" + db.is_blank("product_type") + ") or (product_type=" + db.quote(results.getContentType()) + ")) and ((" + db.is_not_blank("product_group") + ") or (" + db.is_not_blank("product_type") + ")))";
					SQL = SQL + " or ((" + db.is_blank("product_group") + ") and (" + db.is_blank("product_type") + ") and (((product_weight_from is not null) and (product_weight_from <> 0)) or ((product_weight_to is not null) and (product_weight_to <> 0)) or ((product_volume_from is not null) and (product_volume_from <> 0)) or ((product_volume_to is not null) and (product_volume_to <> 0)) or ((product_width_from is not null) and (product_width_from <> 0)) or ((product_width_to is not null) and (product_width_to <> 0)) or ((product_height_from is not null) and (product_height_from <> 0)) or ((product_height_to is not null) and (product_height_to <> 0)) or ((product_depth_from is not null) and (product_depth_from <> 0)) or ((product_depth_to is not null) and (product_depth_to <> 0))))";
					SQL = SQL + ")";
					if (Common.number(shopcartitem.getProductWeight()) > 0) {
						SQL = SQL + " and (((product_weight_from is null) or (product_weight_from <= " + shopcartitem.getProductWeight() + ")) and ((product_weight_to is null) or (product_weight_to = 0) or (product_weight_to >= " + shopcartitem.getProductWeight() + ")))";
					}
					if (Common.number(shopcartitem.getProductVolume()) > 0) {
						SQL = SQL + " and (((product_volume_from is null) or (product_volume_from <= " + shopcartitem.getProductVolume() + ")) and ((product_volume_to is null) or (product_volume_to = 0) or (product_volume_to >= " + shopcartitem.getProductVolume() + ")))";
					}
					if (Common.number(shopcartitem.getProductWidth()) > 0) {
						SQL = SQL + " and (((product_width_from is null) or (product_width_from <= " + shopcartitem.getProductWidth() + ")) and ((product_width_to is null) or (product_width_to = 0) or (product_width_to >= " + shopcartitem.getProductWidth() + ")))";
					}
					if (Common.number(shopcartitem.getProductHeight()) > 0) {
						SQL = SQL + " and (((product_height_from is null) or (product_height_from <= " + shopcartitem.getProductHeight() + ")) and ((product_height_to is null) or (product_height_to = 0) or (product_height_to >= " + shopcartitem.getProductHeight() + ")))";
					}
					if (Common.number(shopcartitem.getProductDepth()) > 0) {
						SQL = SQL + " and (((product_depth_from is null) or (product_depth_from <= " + shopcartitem.getProductDepth() + ")) and ((product_depth_to is null) or (product_depth_to = 0) or (product_depth_to >= " + shopcartitem.getProductDepth() + ")))";
					}

					SQL = SQL + ") or (";

					// applies to order - VAT rate
					SQL += "((tax_percent > 0) and ((tax_order is null) or (tax_order = 0)) and ((tax_item is null) or (tax_item = 0)) and ((tax_total is null) or (tax_total = 0)))";
					SQL += " and (((total_from is null) or (total_from = 0)) and ((total_to is null) or (total_to = 0) or (total_to >= 0)))";
					SQL += " and (((total_weight_from is null) or (total_weight_from = 0)) and ((total_weight_to is null) or (total_weight_to = 0)))";
					SQL += " and (((total_volume_from is null) or (total_volume_from = 0)) and ((total_volume_to is null) or (total_volume_to = 0)))";
					SQL += " and (" + db.is_blank("product_id") + " and " + db.is_blank("product_group") + " and " + db.is_blank("product_type") + ")";
					SQL += " and (((product_weight_from is null) or (product_weight_from = 0)) and ((product_weight_to is null) or (product_weight_to = 0)))";
					SQL += " and (((product_volume_from is null) or (product_volume_from = 0)) and ((product_volume_to is null) or (product_volume_to = 0)))";
					SQL += " and (((product_width_from is null) or (product_width_from = 0)) and ((product_width_to is null) or (product_width_to = 0)))";
					SQL += " and (((product_height_from is null) or (product_height_from = 0)) and ((product_height_to is null) or (product_height_to = 0)))";
					SQL += " and (((product_depth_from is null) or (product_depth_from = 0)) and ((product_depth_to is null) or (product_depth_to = 0)))";

					SQL = SQL + "))";
					
					String shopcartitem_tax_description = "";
					String shopcartitem_tax_vat_description = "";
					boolean shopcartitem_tax_vat_flag = false;
					double shopcartitem_tax_total = 0;
					boolean shopcartitem_tax_total_flag = false;
					String shopcartitem_tax_max_description = "";
					double shopcartitem_tax_max_total = 0;
					boolean shopcartitem_tax_max_total_flag = false;
					tax.records(db, SQL);
					while (tax.records(db, "")) {
						if ((Common.number(tax.getTaxPercent()) > 0) && (Common.number(tax.getTaxOrder()) == 0) && (Common.number(tax.getTaxItem()) == 0) && (Common.number(tax.getTaxTotal()) == 0) && (Common.number(tax.getQuantityFrom()) == 0) && (Common.number(tax.getQuantityTo()) == 0) && (Common.number(tax.getTotalFrom()) == 0) && (Common.number(tax.getTotalTo()) == 0) && (Common.number(tax.getTotalWeightFrom()) == 0) && (Common.number(tax.getTotalWeightTo()) == 0) && (Common.number(tax.getTotalVolumeFrom()) == 0) && (Common.number(tax.getTotalVolumeTo()) == 0)) {
							shopcartitem_tax_vat_flag = true;
						} else {
							shopcartitem_tax_vat_flag = false;
						}
						if (tax.getTaxCurrency().equals("")) {
							tax.setTaxCurrency(config.get(db, "default_currency"));
						}
						double my_tax_amount = 0;
						double my_tax_max_amount = 0;
						if (tax.getTaxCurrency().equals(order_currency.getId())) {
							my_tax_amount = Common.number(tax.getTaxOrder()) + (Common.number(tax.getTaxItem()) * Common.number(shopcartitem.getItemQuantity())) + (Common.number(tax.getTaxPercent()) * Common.number(shopcartitem.getItemTotal()) / 100);
							my_tax_max_amount = Common.number(tax.getTaxTotal());
						} else {
							Currency tax_currency = new Currency();
							tax_currency.read(db, tax.getTaxCurrency());
							my_tax_amount = (Common.number(tax.getTaxOrder()) * Common.number(order_currency.getRate()) / Common.number(tax_currency.getRate())) + (Common.number(tax.getTaxItem()) * Common.number(shopcartitem.getItemQuantity()) * Common.number(order_currency.getRate()) / Common.number(tax_currency.getRate())) + (Common.number(tax.getTaxPercent()) * Common.number(shopcartitem.getItemTotal()) / 100);
							my_tax_max_amount = (Common.number(tax.getTaxTotal()) * Common.number(order_currency.getRate()) / Common.number(tax_currency.getRate()));
						}
						if (shopcartitem_tax_vat_flag) {
							shopcartitem_tax_vat_description = shopcartitem_tax_vat_description + "<span class=\"tax\">" + tax.getTaxDescription() + "</span>" + " ";
						} else {
							shopcartitem_tax_description = shopcartitem_tax_description + "<span class=\"tax\">" + shopcartitem.getItemQuantity() + " x " + tax.getTaxDescription() + "</span>" + " ";
						}
						shopcartitem_tax_total = shopcartitem_tax_total + my_tax_amount;
						if (shopcartitem_tax_total > 0) {
							shopcartitem_tax_total_flag = true;
						}
						if ((! tax.getTaxTotal().equals("")) && (Common.number(tax.getTaxTotal()) > 0)) {
							if ((my_tax_max_amount < shopcartitem_tax_max_total) || (! shopcartitem_tax_max_total_flag)) {
								shopcartitem_tax_max_description = "<span class=\"tax\">" + shopcartitem.getItemQuantity() + " x " + tax.getTaxDescription() + "</span>" + " ";
								shopcartitem_tax_max_total = my_tax_max_amount;
								shopcartitem_tax_max_total_flag = true;
							}
						}
					}
					if (shopcartitem_tax_max_total_flag && ((shopcartitem_tax_max_total < shopcartitem_tax_total) || (! shopcartitem_tax_total_flag))) {
						shopcartitem_tax_description = shopcartitem_tax_max_description;
						shopcartitem_tax_total = shopcartitem_tax_max_total;
					}
					shopcartitem.setTaxDescription(shopcartitem.getTaxDescription() + shopcartitem_tax_vat_description + shopcartitem_tax_description);
					tax_description = tax_description + shopcartitem_tax_description;
					shopcartitem.setTaxTotal(shopcartitem.getTaxTotal() + shopcartitem_tax_total);
					tax_total = tax_total + shopcartitem_tax_total;
				}
			}

			SQL = "select * from shipping";
			SQL = SQL + " where ((" + db.is_blank("country") + ") or (country=" + db.quote(delivery_country) + "))";
			SQL = SQL + " and ((" + db.is_blank("state") + ") or (state=" + db.quote(delivery_state) + "))";
			SQL = SQL + " and (((quantity_from is null) or (quantity_from <= " + order_quantity + ")) and ((quantity_to is null) or (quantity_to = 0) or (quantity_to >= " + order_quantity + ")))";
			SQL = SQL + " and (((total_from is null) or (total_from <= " + order_subtotal + ")) and ((total_to is null) or (total_to = 0) or (total_to >= " + order_subtotal + ")))";
			if (order_weight > 0) {
				SQL = SQL + " and ((((total_weight_from is null) or (total_weight_from = 0)) and ((total_weight_to is null) or (total_weight_to = 0))) or ((total_weight_from <= " + order_weight + ") and (total_weight_to >= " + order_weight + ")))";
			}
			if (order_volume > 0) {
				SQL = SQL + " and ((((total_volume_from is null) or (total_volume_from = 0)) and ((total_volume_to is null) or (total_volume_to = 0))) or ((total_volume_from <= " + order_volume + ") and (total_volume_to >= " + order_volume + ")))";
			}
			SQL += " and (" + db.is_blank("product_id") + " and " + db.is_blank("product_group") + " and " + db.is_blank("product_type") + ")";
			SQL += " and (( product_weight_from is null) or (product_weight_from = 0)) and (( product_weight_to is null) or (product_weight_to = 0))";
			SQL += " and (( product_volume_from is null) or (product_volume_from = 0)) and (( product_volume_to is null) or (product_volume_to = 0))";
			SQL += " and (( product_width_from is null) or (product_width_from = 0)) and (( product_width_to is null) or (product_width_to = 0))";
			SQL += " and (( product_height_from is null) or (product_height_from = 0)) and (( product_height_to is null) or (product_height_to = 0))";
			SQL += " and (( product_depth_from is null) or (product_depth_from = 0)) and (( product_depth_to is null) or (product_depth_to = 0))";
			String shopcart_shipping_description = "";
			double shopcart_shipping_total = shipping_total;
			boolean shopcart_shipping_total_flag = true;
			String shopcart_shipping_max_description = "";
			double shopcart_shipping_max_total = 0;
			boolean shopcart_shipping_max_total_flag = false;
			shipping = new Shipping();
			shipping.records(db, SQL);
			while (shipping.records(db, "")) {
				if (shipping.getShipCurrency().equals("")) {
					shipping.setShipCurrency(config.get(db, "default_currency"));
				}
				double my_shipping_amount = 0;
				double my_shipping_max_amount = 0;
				if (shipping.getShipCurrency().equals(order_currency.getId())) {
					my_shipping_amount = Common.number(shipping.getShipOrder()) + (Common.number(shipping.getShipItem()) * order_quantity) + (Common.number(shipping.getShipPercent()) * order_subtotal / 100);
					my_shipping_max_amount = Common.number(shipping.getShipTotal());
				} else {
					Currency shipping_currency = new Currency();
					shipping_currency.read(db, shipping.getShipCurrency());
					my_shipping_amount = (Common.number(shipping.getShipOrder()) * Common.number(order_currency.getRate()) / Common.number(shipping_currency.getRate())) + (Common.number(shipping.getShipItem()) * order_quantity * Common.number(order_currency.getRate()) / Common.number(shipping_currency.getRate())) + (Common.number(shipping.getShipPercent()) * order_subtotal / 100);
					my_shipping_max_amount = (Common.number(shipping.getShipTotal()) * Common.number(order_currency.getRate()) / Common.number(shipping_currency.getRate()));
				}
				shopcart_shipping_description = shopcart_shipping_description + "<span class=\"shipping\">" + shipping.getShipDescription() + "</span>" + " ";
				shopcart_shipping_total = shopcart_shipping_total + my_shipping_amount;
				if (shopcart_shipping_total > 0) {
					shopcart_shipping_total_flag = true;
				}
				if ((! shipping.getShipTotal().equals("")) && (Common.number(shipping.getShipTotal()) > 0)) {
					if ((my_shipping_max_amount < shopcart_shipping_max_total) || (! shopcart_shipping_max_total_flag)) {
						shopcart_shipping_max_description = "<span class=\"shipping\">" + shipping.getShipDescription() + "</span>" + " ";
						shopcart_shipping_max_total = my_shipping_max_amount;
						shopcart_shipping_max_total_flag = true;
					}
				}
			}
			shipping_description = shipping_description + shopcart_shipping_description;
			shipping_total = shopcart_shipping_total;
			if (shopcart_shipping_max_total_flag && ((shopcart_shipping_max_total < shopcart_shipping_total) || (! shopcart_shipping_total_flag))) {
				shipping_description = shopcart_shipping_max_description;
				shipping_total = shopcart_shipping_max_total;
			}

			SQL = "select * from tax";
			SQL = SQL + " where ((" + db.is_blank("country") + ") or (country=" + db.quote(delivery_country) + "))";
			SQL = SQL + " and ((" + db.is_blank("state") + ") or (state=" + db.quote(delivery_state) + "))";
			SQL = SQL + " and (((quantity_from is null) or (quantity_from <= " + order_quantity + ")) and ((quantity_to is null) or (quantity_to = 0) or (quantity_to >= " + order_quantity + ")))";
			SQL = SQL + " and (((total_from is null) or (total_from <= " + order_subtotal + ")) and ((total_to is null) or (total_to = 0) or (total_to >= " + order_subtotal + ")))";
			if (order_weight > 0) {
				SQL = SQL + " and ((((total_weight_from is null) or (total_weight_from = 0)) and ((total_weight_to is null) or (total_weight_to = 0))) or ((total_weight_from <= " + order_weight + ") and (total_weight_to >= " + order_weight + ")))";
			}
			if (order_volume > 0) {
				SQL = SQL + " and ((((total_volume_from is null) or (total_volume_from = 0)) and ((total_volume_to is null) or (total_volume_to = 0))) or ((total_volume_from <= " + order_volume + ") and (total_volume_to >= " + order_volume + ")))";
			}
			SQL += " and (" + db.is_blank("product_id") + " and " + db.is_blank("product_group") + " and " + db.is_blank("product_type") + ")";
			SQL += " and (( product_weight_from is null) or (product_weight_from = 0)) and (( product_weight_to is null) or (product_weight_to = 0))";
			SQL += " and (( product_volume_from is null) or (product_volume_from = 0)) and (( product_volume_to is null) or (product_volume_to = 0))";
			SQL += " and (( product_width_from is null) or (product_width_from = 0)) and (( product_width_to is null) or (product_width_to = 0))";
			SQL += " and (( product_height_from is null) or (product_height_from = 0)) and (( product_height_to is null) or (product_height_to = 0))";
			SQL += " and (( product_depth_from is null) or (product_depth_from = 0)) and (( product_depth_to is null) or (product_depth_to = 0))";
			String shopcart_tax_description = "";
			String shopcart_tax_vat_description = "";
			boolean shopcart_tax_vat_flag = false;
			double shopcart_tax_total = tax_total;
			boolean shopcart_tax_total_flag = true;
			String shopcart_tax_max_description = "";
			double shopcart_tax_max_total = 0;
			boolean shopcart_tax_max_total_flag = false;
			tax = new Tax();
			tax.records(db, SQL);
			while (tax.records(db, "")) {
				if ((Common.number(tax.getTaxPercent()) > 0) && (Common.number(tax.getTaxOrder()) == 0) && (Common.number(tax.getTaxItem()) == 0) && (Common.number(tax.getTaxTotal()) == 0) && (Common.number(tax.getQuantityFrom()) == 0) && (Common.number(tax.getQuantityTo()) == 0) && (Common.number(tax.getTotalFrom()) == 0) && (Common.number(tax.getTotalTo()) == 0) && (Common.number(tax.getTotalWeightFrom()) == 0) && (Common.number(tax.getTotalWeightTo()) == 0) && (Common.number(tax.getTotalVolumeFrom()) == 0) && (Common.number(tax.getTotalVolumeTo()) == 0)) {
					shopcart_tax_vat_flag = true;
				} else {
					shopcart_tax_vat_flag = false;
				}
				if (tax.getTaxCurrency().equals("")) {
					tax.setTaxCurrency(config.get(db, "default_currency"));
				}
				double my_tax_amount = 0;
				double my_tax_max_amount = 0;
				if (tax.getTaxCurrency().equals(order_currency.getId())) {
					my_tax_amount = Common.number(tax.getTaxOrder()) + (Common.number(tax.getTaxItem()) * order_quantity) + (Common.number(tax.getTaxPercent()) * order_subtotal / 100);
					my_tax_max_amount = Common.number(tax.getTaxTotal());
				} else {
					Currency tax_currency = new Currency();
					tax_currency.read(db, tax.getTaxCurrency());
					my_tax_amount = (Common.number(tax.getTaxOrder()) * Common.number(order_currency.getRate()) / Common.number(tax_currency.getRate())) + (Common.number(tax.getTaxItem()) * order_quantity * Common.number(order_currency.getRate()) / Common.number(tax_currency.getRate())) + (Common.number(tax.getTaxPercent()) * order_subtotal / 100);
					my_tax_max_amount = (Common.number(tax.getTaxTotal()) * Common.number(order_currency.getRate()) / Common.number(tax_currency.getRate()));
				}
				if (shopcart_tax_vat_flag) {
					shopcart_tax_vat_description = shopcart_tax_vat_description + "<span class=\"tax\">" + tax.getTaxDescription() + "</span>" + " ";
					// ignore - already added for each order item
				} else {
					shopcart_tax_description = shopcart_tax_description + "<span class=\"tax\">" + tax.getTaxDescription() + "</span>" + " ";
					shopcart_tax_total = shopcart_tax_total + my_tax_amount;
				}
				if (shopcart_tax_total > 0) {
					shopcart_tax_total_flag = true;
				}
				if ((! tax.getTaxTotal().equals("")) && (Common.number(tax.getTaxTotal()) > 0)) {
					if ((my_tax_max_amount < shopcart_tax_max_total) || (! shopcart_tax_max_total_flag)) {
						shopcart_tax_max_description = "<span class=\"tax\">" + tax.getTaxDescription() + "</span>" + " ";
						shopcart_tax_max_total = my_tax_max_amount;
						shopcart_tax_max_total_flag = true;
					}
				}
			}
			tax_description = tax_description + shopcart_tax_vat_description + shopcart_tax_description;
			tax_total = shopcart_tax_total;
			if (shopcart_tax_max_total_flag && ((shopcart_tax_max_total < shopcart_tax_total) || (! shopcart_tax_total_flag))) {
				tax_description = shopcart_tax_max_description;
				tax_total = shopcart_tax_max_total;
			}

			order_total = order_total + shipping_total;
			order_total = order_total + tax_total;

			setShippingDescription("" + shipping_description);
			setShippingTotal("" + shipping_total);
			setTaxDescription("" + tax_description);
			setTaxTotal("" + tax_total);
			setOrderQuantity("" + Common.integernumber("" + order_quantity));
			setOrderSubtotal("" + order_subtotal);
			setOrderTotal("" + order_total);
		}
	}



	public Orderitem doShopcartItem(ServletContext server, Session mysession, Request myrequest, Response myresponse, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, boolean stockcheck, Content product) {
		return doShopcartItem(server, mysession, myrequest, myresponse, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", "", "", stockcheck, product);
	}
	public Orderitem doShopcartItem(ServletContext server, Session mysession, Request myrequest, Response myresponse, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, boolean stockcheck, Content product) {
		String productid = product.getId();
		String [] shopcartitem_elements = ("" + shoppingcart.get(productid)).split("\\|");
		if ((! product.getVersionMaster().equals("")) && (! product.getVersionMaster().equals("0")) && ((! product.getVersion().equals("")) || (! product.getDevice().equals("")) || (! product.getUsersegment().equals("")) || (! product.getUsertest().equals("")))) {
			if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
				product.preview_read(db, config, product.getVersionMaster(), session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, null);
			} else {
				product.public_read(db, config, product.getVersionMaster(), session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, null);
			}
		} else {
			if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
				product.preview_read(db, config, productid, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, null);
			} else {
				product.public_read(db, config, productid, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, null);
			}
		}

		String display_price = "";
		if (product.getProductCurrency().equals("")) {
			product.setProductCurrency(config.get(db, "default_currency"));
		}
		if (product.getProductCurrency().equals(order_currency.getId())) {
			display_price = product.getProductPrice();
		} else {
			Currency product_currency = new Currency();
			product_currency.read(db, product.getProductCurrency());
			display_price = "" + (Common.number(product.getProductPrice()) * Common.number(order_currency.getRate()) / Common.number(product_currency.getRate()));
		}

		Orderitem shopcartitem = new Orderitem();
		shopcartitem.setProductId(productid);
		shopcartitem.setVersion(product.getVersion());
		shopcartitem.setDevice(product.getDevice());
		shopcartitem.setUsersegment(product.getUsersegment());
		shopcartitem.setUsertest(product.getUsertest());
		shopcartitem.setTitle(product.getTitle());
		shopcartitem.setAuthor(product.getAuthor());
		shopcartitem.setKeywords(product.getKeywords());
		shopcartitem.setDescription(product.getDescription());
		shopcartitem.setSummary(product.getSummary());
		shopcartitem.setImage1(product.getImage1());
		shopcartitem.setImage2(product.getImage2());
		shopcartitem.setImage3(product.getImage3());
		shopcartitem.setFile1(product.getFile1());
		shopcartitem.setFile2(product.getFile2());
		shopcartitem.setFile3(product.getFile3());
		shopcartitem.setLink1(product.getLink1());
		shopcartitem.setLink2(product.getLink2());
		shopcartitem.setLink3(product.getLink3());
		shopcartitem.setProductCode(product.getProductCode());
		shopcartitem.setProductCurrencyTitle(order_currency.getTitle());
		shopcartitem.setProductCurrency(order_currency.getSymbol());
		shopcartitem.setProductPrice(display_price);
		shopcartitem.setProductWeight(product.getProductWeight());
		shopcartitem.setProductVolume(product.getProductVolume());
		shopcartitem.setProductWidth(product.getProductWidth());
		shopcartitem.setProductHeight(product.getProductHeight());
		shopcartitem.setProductDepth(product.getProductDepth());
		shopcartitem.setProductPeriod(product.getProductPeriod());
		shopcartitem.setProductEmail(product.getProductEmail());
		shopcartitem.setProductURL(product.getProductURL());
		shopcartitem.setProductDelivery(product.getProductDelivery());
		shopcartitem.setProductUser(product.getProductUser());
		shopcartitem.setProductPage(product.getProductPage());
		shopcartitem.setProductProgram(product.getProductProgram());
		shopcartitem.setProductHosting(product.getProductHosting());
		shopcartitem.setProductBrand(product.getProductBrand());
		shopcartitem.setProductColour(product.getProductColour());
		shopcartitem.setProductSize(product.getProductSize());
		shopcartitem.setProductOptions(product.getProductOptions());
		String quantity = shopcartitem_elements[0];
		int stockamount = Common.intnumber(product.getProductStockAmount(db));
		if (product.getProductNoStockOrder().equals("")) {
			// unlimited
			shopcartitem.setProductStock(""+stockamount);
			shopcartitem.setProductComment(product.getProductStockText());
			shopcartitem.setProductStatus("in");
		} else if (stockcheck && (product.getProductNoStockOrder().equals("no")) && (stockamount-Common.intnumber(quantity)<0)) {
//			quantity = "0";
			if (stockamount > 0) {
				shopcartitem.setProductStock(""+stockamount);
			} else {
				shopcartitem.setProductStock("0");
			}
			shopcartitem.setProductComment(product.getProductNoStockText());
			setOutOfStock(productid, Common.intnumber(quantity), stockamount, shopcartitem.getProductComment());
			shopcartitem.setProductStatus("no");
		} else if ((product.getProductNoStockOrder().equals("no")) && (stockamount-Common.intnumber(quantity)<0)) {
			if (stockamount > 0) {
				shopcartitem.setProductStock(""+stockamount);
			} else {
				shopcartitem.setProductStock("0");
			}
			shopcartitem.setProductComment(product.getProductNoStockText());
			shopcartitem.setProductStatus("no");
		} else if (stockamount <= 0) {
			shopcartitem.setProductStock("0");
			shopcartitem.setProductComment(product.getProductNoStockText());
			shopcartitem.setProductStatus("no");
		} else if (stockamount <= Common.intnumber(product.getProductLowStockAmount())) {
			shopcartitem.setProductStock(""+stockamount);
			shopcartitem.setProductComment(product.getProductLowStockText());
			shopcartitem.setProductStatus("low");
		} else {
			shopcartitem.setProductStock(""+stockamount);
			shopcartitem.setProductComment(product.getProductStockText());
			shopcartitem.setProductStatus("in");
		}
		shopcartitem.setItemQuantity(quantity);
		shopcartitem.setItemSubtotal("" + (Common.number(quantity) * Common.number(display_price)));
		shopcartitem.setItemTotal("" + (Common.number(quantity) * Common.number(display_price)));
		shopcartitem.setDiscountTotal("0");
		shopcartitem.setShippingTotal("0");
		shopcartitem.setTaxTotal("0");
		shoppingcartitems.put(shopcartitem.getProductId(), shopcartitem);
		shoppingcartitems_sorted.put(shopcartitem.getProductId(), shopcartitem.getProductId());
		return shopcartitem;
	}



	public String doProductAvailability(ServletContext server, Session mysession, Request myrequest, Response myresponse, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, boolean stockcheck, Orderitem shopcartitem) {
		return doProductAvailability(server, mysession, myrequest, myresponse, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", stockcheck, shopcartitem);
	}
	public String doProductAvailability(ServletContext server, Session mysession, Request myrequest, Response myresponse, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, boolean stockcheck, Orderitem shopcartitem) {
		String order_availability = "";
		String product_availability_attribute = null;
		Object product_availability_value = null;
		if (shopcartitem.getProductProgram().equals("domainnameregistration")) {
			shopcartitem.setShopcart(shoppingcart);
			product_availability_attribute = "shopcartitem";
			product_availability_value = shopcartitem;
		}
		String product_availability = Common.execute("/" + text.display("adminpath") + "/productavailability/" + shopcartitem.getProductProgram() + ".jsp", product_availability_attribute, product_availability_value, "productavailability", server, mysession.getSession(), myrequest.getRequest(), myresponse.getResponse());
		if ((! product_availability.equals("")) && (product_availability.startsWith("0"))) {
			order_availability += product_availability.replaceAll("^0", "");
		}
		return order_availability;
	}



	public String getOrderAvailability() {
		return order_availability;
	}
	public void setOrderAvailability(String value) {
		order_availability = value;
	}



	public String getOrderQuantity() {
		return order_quantity;
	}
	public void setOrderQuantity(String value) {
		order_quantity = value;
	}



	public Currency getOrderCurrency() {
		return order_currency;
	}
	public void setOrderCurrency(Currency value) {
		order_currency = value;
	}



	public String getOrderSubtotal() {
		return order_subtotal;
	}
	public void setOrderSubtotal(String value) {
		order_subtotal = value;
	}



	public String getTaxDescription() {
		return tax_description;
	}
	public void setTaxDescription(String value) {
		tax_description = value;
	}



	public String getTaxTotal() {
		return tax_total;
	}
	public void setTaxTotal(String value) {
		tax_total = value;
	}



	public String getShippingDescription() {
		return shipping_description;
	}
	public void setShippingDescription(String value) {
		shipping_description = value;
	}



	public String getShippingTotal() {
		return shipping_total;
	}
	public void setShippingTotal(String value) {
		shipping_total = value;
	}



	public String getDiscountDescription() {
		return discount_description;
	}
	public void setDiscountDescription(String value) {
		discount_description = value;
	}



	public String getDiscountTotal() {
		return discount_total;
	}
	public void setDiscountTotal(String value) {
		discount_total = value;
	}



	public String getOrderTotal() {
		return order_total;
	}
	public void setOrderTotal(String value) {
		order_total = value;
	}



	public String getCardType() {
		return card_type;
	}
	public void setCardType(String value) {
		card_type = value;
	}



	public String getCardNumber() {
		return card_number;
	}
	public void setCardNumber(String value) {
		card_number = value;
	}



	public String getCardIssuedMonth() {
		return card_issuedmonth;
	}
	public void setCardIssuedMonth(String value) {
		card_issuedmonth = value;
	}



	public String getCardIssuedYear() {
		return card_issuedyear;
	}
	public void setCardIssuedYear(String value) {
		card_issuedyear = value;
	}



	public String getCardExpiryMonth() {
		return card_expirymonth;
	}
	public void setCardExpiryMonth(String value) {
		card_expirymonth = value;
	}



	public String getCardExpiryYear() {
		return card_expiryyear;
	}
	public void setCardExpiryYear(String value) {
		card_expiryyear = value;
	}



	public String getCardName() {
		return card_name;
	}
	public void setCardName(String value) {
		card_name = value;
	}



	public String getCardCVC() {
		return card_cvc;
	}
	public void setCardCVC(String value) {
		card_cvc = value;
	}



	public String getCardIssue() {
		return card_issue;
	}
	public void setCardIssue(String value) {
		card_issue = value;
	}



	public String getCardPostalcode() {
		return card_postalcode;
	}
	public void setCardPostalcode(String value) {
		card_postalcode = value;
	}



	public String getDeliveryName() {
		return delivery_name;
	}
	public void setDeliveryName(String value) {
		delivery_name = value;
	}



	public String getDeliveryOrganisation() {
		return delivery_organisation;
	}
	public void setDeliveryOrganisation(String value) {
		delivery_organisation = value;
	}



	public String getDeliveryAddress() {
		return delivery_address;
	}
	public void setDeliveryAddress(String value) {
		delivery_address = value;
	}



	public String getDeliveryPostalcode() {
		return delivery_postalcode;
	}
	public void setDeliveryPostalcode(String value) {
		delivery_postalcode = value;
	}



	public String getDeliveryCity() {
		return delivery_city;
	}
	public void setDeliveryCity(String value) {
		delivery_city = value;
	}



	public String getDeliveryState() {
		return delivery_state;
	}
	public void setDeliveryState(String value) {
		delivery_state = value;
	}



	public String getDeliveryCountry() {
		return delivery_country;
	}
	public void setDeliveryCountry(String value) {
		delivery_country = value;
	}



	public String getDeliveryPhone() {
		return delivery_phone;
	}
	public void setDeliveryPhone(String value) {
		delivery_phone = value;
	}



	public String getDeliveryFax() {
		return delivery_fax;
	}
	public void setDeliveryFax(String value) {
		delivery_fax = value;
	}



	public String getDeliveryEmail() {
		return delivery_email;
	}
	public void setDeliveryEmail(String value) {
		delivery_email = value;
	}



	public String getDeliveryWebsite() {
		return delivery_website;
	}
	public void setDeliveryWebsite(String value) {
		delivery_website = value;
	}



	public String getInvoiceName() {
		return invoice_name;
	}
	public void setInvoiceName(String value) {
		invoice_name = value;
	}



	public String getInvoiceOrganisation() {
		return invoice_organisation;
	}
	public void setInvoiceOrganisation(String value) {
		invoice_organisation = value;
	}



	public String getInvoiceAddress() {
		return invoice_address;
	}
	public void setInvoiceAddress(String value) {
		invoice_address = value;
	}



	public String getInvoicePostalcode() {
		return invoice_postalcode;
	}
	public void setInvoicePostalcode(String value) {
		invoice_postalcode = value;
	}



	public String getInvoiceCity() {
		return invoice_city;
	}
	public void setInvoiceCity(String value) {
		invoice_city = value;
	}



	public String getInvoiceState() {
		return invoice_state;
	}
	public void setInvoiceState(String value) {
		invoice_state = value;
	}



	public String getInvoiceCountry() {
		return invoice_country;
	}
	public void setInvoiceCountry(String value) {
		invoice_country = value;
	}



	public String getInvoicePhone() {
		return invoice_phone;
	}
	public void setInvoicePhone(String value) {
		invoice_phone = value;
	}



	public String getInvoiceFax() {
		return invoice_fax;
	}
	public void setInvoiceFax(String value) {
		invoice_fax = value;
	}



	public String getInvoiceEmail() {
		return invoice_email;
	}
	public void setInvoiceEmail(String value) {
		invoice_email = value;
	}



	public String getInvoiceWebsite() {
		return invoice_website;
	}
	public void setInvoiceWebsite(String value) {
		invoice_website = value;
	}



	public HashMap<String,String> getShoppingCart() {
		return shoppingcart;
	}



	public HashMap<String,Orderitem> getShoppingCartItems() {
		return shoppingcartitems;
	}



	public Orderitem getShoppingCartItem(String key) {
		return (Orderitem) shoppingcartitems.get(key);
	}



	public LinkedHashMap<String,String> getShoppingCartItemsSorted() {
		return shoppingcartitems_sorted;
	}



	public String getOutOfStock() {
		return out_of_stock;
	}
	public void setOutOfStock(String productid, int orderquantity, int stockamount, String stockcomment) {
		if (! out_of_stock.equals("")) out_of_stock += "\r\n";
		out_of_stock += productid+":::"+orderquantity+":::"+stockamount+":::"+stockcomment;
	}



}
