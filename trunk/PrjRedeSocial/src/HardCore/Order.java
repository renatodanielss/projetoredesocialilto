package HardCore;

import java.sql.*;
import java.text.*;
import java.text.DateFormat;
import java.util.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.*;
import javax.servlet.*;

public class Order {
	static public boolean DEBUG = false;
	public boolean _DEBUG_ = false;
	private String id = "";
	private String user_id = "";
	private String created = "";
	private String updated = "";
	private String published = "";
	private String created_by = "";
	private String updated_by = "";
	private String published_by = "";
	private String revision = "";
	private String status = "";
	private String status_by = "";
	private String checkedout = "";
	private String order_availability = "";
	private String order_quantity = "";
	private String order_currencytitle = "";
	private String order_currency = "";
	private String order_subtotal = "";
	private String order_subtotal_base = "";
	private String tax_description = "";
	private String tax_total = "";
	private String tax_total_base = "";
	private String shipping_description = "";
	private String shipping_total = "";
	private String shipping_total_base = "";
	private String discount_description = "";
	private String discount_total = "";
	private String discount_total_base = "";
	private String order_total = "";
	private String order_total_base = "";
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
	private String paid = "";
	private String usersegments = "";
	private String usertests = "";

	private	Statement rs = null;

	private Orderitem orderitem_records = null;



	public Order() {
		init();
	}



	public void debug(boolean mode) {
		_DEBUG_ = mode;
	}



	public void init() {
		_DEBUG_ = DEBUG;
		id = "";
		user_id = "";
		created = "";
		updated = "";
		published = "";
		created_by = "";
		updated_by = "";
		published_by = "";
		revision = "";
		status = "";
		status_by = "";
		checkedout = "";
		order_availability = "";
		order_quantity = "";
		order_currencytitle = "";
		order_currency = "";
		order_subtotal = "";
		order_subtotal_base = "";
		tax_description = "";
		tax_total = "";
		tax_total_base = "";
		shipping_description = "";
		shipping_total = "";
		shipping_total_base = "";
		discount_description = "";
		discount_total = "";
		discount_total_base = "";
		order_total = "";
		order_total_base = "";
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
		paid = "";
		usersegments = "";
		usertests = "";
	}



	public void read(DB db, String id) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			String SQL = "select * from orders where id=" + Common.integer(id);
			HashMap row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void getRecord(DB db, HashMap record) {
		if (record.get("id") != null) id = "" + record.get("id"); else id = "";
		if (record.get("user_id") != null) user_id = "" + record.get("user_id"); else user_id = "";
		if (record.get("created") != null) created = "" + record.get("created"); else created = "";
		if (record.get("updated") != null) updated = "" + record.get("updated"); else updated = "";
		if (record.get("published") != null) published = "" + record.get("published"); else published = "";
		if (record.get("created_by") != null) created_by = "" + record.get("created_by"); else created_by = "";
		if (record.get("updated_by") != null) updated_by = "" + record.get("updated_by"); else updated_by = "";
		if (record.get("published_by") != null) published_by = "" + record.get("published_by"); else published_by = "";
		if (record.get("revision") != null) revision = "" + record.get("revision"); else revision = "";
		if (record.get("status") != null) status = "" + record.get("status"); else status = "";
		if (record.get("status_by") != null) status_by = "" + record.get("status_by"); else status_by = "";
		if (record.get("checkedout") != null) checkedout = "" + record.get("checkedout"); else checkedout = "";
		if (record.get("order_quantity") != null) order_quantity = "" + record.get("order_quantity"); else order_quantity = "";
		if (record.get("order_currency") != null) order_currency = "" + record.get("order_currency"); else order_currency = "";
		order_currencytitle = "";
		Currency mycurrency = new Currency();
		mycurrency.readSymbol(db, order_currency);
		order_currencytitle = mycurrency.getTitle();
		if (record.get("order_subtotal") != null) order_subtotal = "" + record.get("order_subtotal"); else order_subtotal = "";
		if (record.get("tax_description") != null) tax_description = "" + record.get("tax_description"); else tax_description = "";
		if (record.get("tax_total") != null) tax_total = "" + record.get("tax_total"); else tax_total = "";
		if (record.get("shipping_description") != null) shipping_description = "" + record.get("shipping_description"); else shipping_description = "";
		if (record.get("shipping_total") != null) shipping_total = "" + record.get("shipping_total"); else shipping_total = "";
		if (record.get("discount_description") != null) discount_description = "" + record.get("discount_description"); else discount_description = "";
		if (record.get("discount_total") != null) discount_total = "" + record.get("discount_total"); else discount_total = "";
		if (record.get("order_total") != null) order_total = "" + record.get("order_total"); else order_total = "";
		if (record.get("card_type") != null) card_type = "" + record.get("card_type"); else card_type = "";
		if (record.get("card_number") != null) card_number = "" + record.get("card_number"); else card_number = "";
		if (record.get("card_issuedmonth") != null) card_issuedmonth = "" + record.get("card_issuedmonth"); else card_issuedmonth = "";
		if (record.get("card_issuedyear") != null) card_issuedyear = "" + record.get("card_issuedyear"); else card_issuedyear = "";
		if (record.get("card_expirymonth") != null) card_expirymonth = "" + record.get("card_expirymonth"); else card_expirymonth = "";
		if (record.get("card_expiryyear") != null) card_expiryyear = "" + record.get("card_expiryyear"); else card_expiryyear = "";
		if (record.get("card_name") != null) card_name = "" + record.get("card_name"); else card_name = "";
		if (record.get("card_cvc") != null) card_cvc = "" + record.get("card_cvc"); else card_cvc = "";
		if (record.get("card_issue") != null) card_issue = "" + record.get("card_issue"); else card_issue = "";
		if (record.get("card_postalcode") != null) card_postalcode = "" + record.get("card_postalcode"); else card_postalcode = "";
		if (record.get("delivery_name") != null) delivery_name = "" + record.get("delivery_name"); else delivery_name = "";
		if (record.get("delivery_organisation") != null) delivery_organisation = "" + record.get("delivery_organisation"); else delivery_organisation = "";
		if (record.get("delivery_address") != null) delivery_address = "" + record.get("delivery_address"); else delivery_address = "";
		if (record.get("delivery_postalcode") != null) delivery_postalcode = "" + record.get("delivery_postalcode"); else delivery_postalcode = "";
		if (record.get("delivery_city") != null) delivery_city = "" + record.get("delivery_city"); else delivery_city = "";
		if (record.get("delivery_state") != null) delivery_state = "" + record.get("delivery_state"); else delivery_state = "";
		if (record.get("delivery_country") != null) delivery_country = "" + record.get("delivery_country"); else delivery_country = "";
		if (record.get("delivery_phone") != null) delivery_phone = "" + record.get("delivery_phone"); else delivery_phone = "";
		if (record.get("delivery_fax") != null) delivery_fax = "" + record.get("delivery_fax"); else delivery_fax = "";
		if (record.get("delivery_email") != null) delivery_email = "" + record.get("delivery_email"); else delivery_email = "";
		if (record.get("delivery_website") != null) delivery_website = "" + record.get("delivery_website"); else delivery_website = "";
		if (record.get("invoice_name") != null) invoice_name = "" + record.get("invoice_name"); else invoice_name = "";
		if (record.get("invoice_organisation") != null) invoice_organisation = "" + record.get("invoice_organisation"); else invoice_organisation = "";
		if (record.get("invoice_address") != null) invoice_address = "" + record.get("invoice_address"); else invoice_address = "";
		if (record.get("invoice_postalcode") != null) invoice_postalcode = "" + record.get("invoice_postalcode"); else invoice_postalcode = "";
		if (record.get("invoice_city") != null) invoice_city = "" + record.get("invoice_city"); else invoice_city = "";
		if (record.get("invoice_state") != null) invoice_state = "" + record.get("invoice_state"); else invoice_state = "";
		if (record.get("invoice_country") != null) invoice_country = "" + record.get("invoice_country"); else invoice_country = "";
		if (record.get("invoice_phone") != null) invoice_phone = "" + record.get("invoice_phone"); else invoice_phone = "";
		if (record.get("invoice_fax") != null) invoice_fax = "" + record.get("invoice_fax"); else invoice_fax = "";
		if (record.get("invoice_email") != null) invoice_email = "" + record.get("invoice_email"); else invoice_email = "";
		if (record.get("invoice_website") != null) invoice_website = "" + record.get("invoice_website"); else invoice_website = "";
		if (record.get("paid") != null) paid = "" + record.get("paid"); else paid = "";
		if (record.get("usersegments") != null) usersegments = "" + record.get("usersegments"); else usersegments = "";
		if (record.get("usertests") != null) usertests = "" + record.get("usertests"); else usertests = "";
	}



	public void getForm(Request request) {
		if (request.parameterExists("revision")) revision = request.getParameter("revision"); else revision = "";
		if (request.parameterExists("status")) status = request.getParameter("status"); else status = "";
		if (request.parameterExists("status_by")) status_by = request.getParameter("status_by"); else status_by = "";
		if (request.parameterExists("checkedout")) checkedout = request.getParameter("checkedout");
		if (request.parameterExists("order_quantity")) order_quantity = request.getParameter("order_quantity"); else order_quantity = "";
		order_currencytitle = "";
		if (request.parameterExists("order_currency")) order_currency = request.getParameter("order_currency"); else order_currency = "";
		if (request.parameterExists("order_subtotal")) order_subtotal = request.getParameter("order_subtotal"); else order_subtotal = "";
		if (request.parameterExists("tax_description")) tax_description = request.getParameter("tax_description"); else tax_description = "";
		if (request.parameterExists("tax_total")) tax_total = request.getParameter("tax_total"); else tax_total = "";
		if (request.parameterExists("shipping_description")) shipping_description = request.getParameter("shipping_description"); else shipping_description = "";
		if (request.parameterExists("shipping_total")) shipping_total = request.getParameter("shipping_total"); else shipping_total = "";
		if (request.parameterExists("discount_description")) discount_description = request.getParameter("discount_description"); else discount_description = "";
		if (request.parameterExists("discount_total")) discount_total = request.getParameter("discount_total"); else discount_total = "";
		if (request.parameterExists("order_total")) order_total = request.getParameter("order_total"); else order_total = "";
		if (request.parameterExists("card_type")) card_type = request.getParameter("card_type"); else card_type = "";
		if (request.parameterExists("card_number")) card_number = request.getParameter("card_number"); else card_number = "";
		if (request.parameterExists("card_issuedmonth")) card_issuedmonth = request.getParameter("card_issuedmonth"); else card_issuedmonth = "";
		if (request.parameterExists("card_issuedyear")) card_issuedyear = request.getParameter("card_issuedyear"); else card_issuedyear = "";
		if (request.parameterExists("card_expirymonth")) card_expirymonth = request.getParameter("card_expirymonth"); else card_expirymonth = "";
		if (request.parameterExists("card_expiryyear")) card_expiryyear = request.getParameter("card_expiryyear"); else card_expiryyear = "";
		if (request.parameterExists("card_name")) card_name = request.getParameter("card_name"); else card_name = "";
		if (request.parameterExists("card_cvc")) card_cvc = request.getParameter("card_cvc"); else card_cvc = "";
		if (request.parameterExists("card_issue")) card_issue = request.getParameter("card_issue"); else card_issue = "";
		if (request.parameterExists("card_postalcode")) card_postalcode = request.getParameter("card_postalcode"); else card_postalcode = "";
		if (request.parameterExists("delivery_name")) delivery_name = request.getParameter("delivery_name"); else delivery_name = "";
		if (request.parameterExists("delivery_organisation")) delivery_organisation = request.getParameter("delivery_organisation"); else delivery_organisation = "";
		if (request.parameterExists("delivery_address")) delivery_address = request.getParameter("delivery_address"); else delivery_address = "";
		if (request.parameterExists("delivery_postalcode")) delivery_postalcode = request.getParameter("delivery_postalcode"); else delivery_postalcode = "";
		if (request.parameterExists("delivery_city")) delivery_city = request.getParameter("delivery_city"); else delivery_city = "";
		if (request.parameterExists("delivery_state")) delivery_state = request.getParameter("delivery_state"); else delivery_state = "";
		if (request.parameterExists("delivery_country")) delivery_country = request.getParameter("delivery_country"); else delivery_country = "";
		if (request.parameterExists("delivery_phone")) delivery_phone = request.getParameter("delivery_phone"); else delivery_phone = "";
		if (request.parameterExists("delivery_fax")) delivery_fax = request.getParameter("delivery_fax"); else delivery_fax = "";
		if (request.parameterExists("delivery_email")) delivery_email = request.getParameter("delivery_email"); else delivery_email = "";
		if (request.parameterExists("delivery_website")) delivery_website = request.getParameter("delivery_website"); else delivery_website = "";
		if (request.parameterExists("invoice_name")) invoice_name = request.getParameter("invoice_name"); else invoice_name = "";
		if (request.parameterExists("invoice_organisation")) invoice_organisation = request.getParameter("invoice_organisation"); else invoice_organisation = "";
		if (request.parameterExists("invoice_address")) invoice_address = request.getParameter("invoice_address"); else invoice_address = "";
		if (request.parameterExists("invoice_postalcode")) invoice_postalcode = request.getParameter("invoice_postalcode"); else invoice_postalcode = "";
		if (request.parameterExists("invoice_city")) invoice_city = request.getParameter("invoice_city"); else invoice_city = "";
		if (request.parameterExists("invoice_state")) invoice_state = request.getParameter("invoice_state"); else invoice_state = "";
		if (request.parameterExists("invoice_country")) invoice_country = request.getParameter("invoice_country"); else invoice_country = "";
		if (request.parameterExists("invoice_phone")) invoice_phone = request.getParameter("invoice_phone"); else invoice_phone = "";
		if (request.parameterExists("invoice_fax")) invoice_fax = request.getParameter("invoice_fax"); else invoice_fax = "";
		if (request.parameterExists("invoice_email")) invoice_email = request.getParameter("invoice_email"); else invoice_email = "";
		if (request.parameterExists("invoice_website")) invoice_website = request.getParameter("invoice_website"); else invoice_website = "";
		if (request.parameterExists("paid")) paid = request.getParameter("paid"); else paid = "";
		if (request.parameterExists("usersegments")) usersegments = request.getParameter("usersegments"); else usersegments = "";
		if (request.parameterExists("usertests")) usertests = request.getParameter("usertests"); else usertests = "";
	}



	public HashMap<String,String> setCreatedDateTime(HashMap<String,String> mydata, String created) {
		java.util.Date mydatetime = Common.strtodate(created);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mydatetime);
		mydata.put("createdyear", "" + calendar.get(Calendar.YEAR));
		mydata.put("createdmonth", "" + (calendar.get(Calendar.MONTH)+1));
		mydata.put("createdday", "" + calendar.get(Calendar.DAY_OF_MONTH));
		mydata.put("createdweek", "" + calendar.get(Calendar.WEEK_OF_YEAR));
		if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
			mydata.put("createdweekday", "" + 7);
		} else {
			mydata.put("createdweekday", "" + (calendar.get(Calendar.DAY_OF_WEEK)-1));
		}
		mydata.put("createdhour", "" + calendar.get(Calendar.HOUR_OF_DAY));
		mydata.put("createdmin", "" + calendar.get(Calendar.MINUTE));
		mydata.put("createdsec", "" + calendar.get(Calendar.SECOND));
		return mydata;
	}



	public void calculate(DB db, Configuration config) {
		if (db == null) return;
		Currency mycurrency = new Currency();
		mycurrency.readSymbol(db, order_currency);
		double base_rate = 100;
		if (mycurrency.getId().equals(config.get(db, "default_currency"))) {
			base_rate = 100;
		} else if (Common.number(mycurrency.getRate()) == 0) {
			base_rate = 100;
		} else {
			Currency base_currency = new Currency();
			base_currency.read(db, config.get(db, "default_currency"));
			base_rate = 100 * Common.number(base_currency.getRate()) / Common.number(mycurrency.getRate());
		}
		order_subtotal_base = "" + Common.number(order_subtotal) * base_rate / 100;
		tax_total_base = "" + Common.number(tax_total) * base_rate / 100;
		shipping_total_base = "" + Common.number(shipping_total) * base_rate / 100;
		discount_total_base = "" + Common.number(discount_total) * base_rate / 100;
		order_total_base = "" + Common.number(order_total) * base_rate / 100;
	}



	public void create(ServletContext server, Session mysession, Request myrequest, Response myresponse, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, Shopcart shopcart) {
		create(server, mysession, myrequest, myresponse, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", "", "", shopcart);
	}
	public void create(ServletContext server, Session mysession, Request myrequest, Response myresponse, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, Shopcart shopcart) {
		if (db == null) return;
		shopcart.calculate(server, mysession, myrequest, myresponse, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests);

		order_currencytitle = "" + shopcart.getOrderCurrency().getTitle();
		order_currency = "" + shopcart.getOrderCurrency().getSymbol();
		order_quantity = "" + shopcart.getOrderQuantity();
		order_subtotal = "" + shopcart.getOrderSubtotal();
		tax_description = "" + shopcart.getTaxDescription();
		tax_total = "" + shopcart.getTaxTotal();
		shipping_description = "" + shopcart.getShippingDescription();
		shipping_total = "" + shopcart.getShippingTotal();
		discount_description = "" + shopcart.getDiscountDescription();
		discount_total = "" + shopcart.getDiscountTotal();
		order_total = "" + shopcart.getOrderTotal();
		calculate(db, config);

		card_type = "" + shopcart.getCardType();
		card_number = "" + shopcart.getCardNumber();
		card_issuedmonth = "" + shopcart.getCardIssuedMonth();
		card_issuedyear = "" + shopcart.getCardIssuedYear();
		card_expirymonth = "" + shopcart.getCardExpiryMonth();
		card_expiryyear = "" + shopcart.getCardExpiryYear();
		card_name = "" + shopcart.getCardName();
		card_cvc = "" + shopcart.getCardCVC();
		card_issue = "" + shopcart.getCardIssue();
		card_postalcode = "" + shopcart.getCardPostalcode();
		delivery_name = "" + shopcart.getDeliveryName();
		delivery_organisation = "" + shopcart.getDeliveryOrganisation();
		delivery_address = "" + shopcart.getDeliveryAddress();
		delivery_postalcode = "" + shopcart.getDeliveryPostalcode();
		delivery_city = "" + shopcart.getDeliveryCity();
		delivery_state = "" + shopcart.getDeliveryState();
		delivery_country = "" + shopcart.getDeliveryCountry();
		delivery_phone = "" + shopcart.getDeliveryPhone();
		delivery_fax = "" + shopcart.getDeliveryFax();
		delivery_email = "" + shopcart.getDeliveryEmail();
		delivery_website = "" + shopcart.getDeliveryWebsite();
		invoice_name = "" + shopcart.getInvoiceName();
		invoice_organisation = "" + shopcart.getInvoiceOrganisation();
		invoice_address = "" + shopcart.getInvoiceAddress();
		invoice_postalcode = "" + shopcart.getInvoicePostalcode();
		invoice_city = "" + shopcart.getInvoiceCity();
		invoice_state = "" + shopcart.getInvoiceState();
		invoice_country = "" + shopcart.getInvoiceCountry();
		invoice_phone = "" + shopcart.getInvoicePhone();
		invoice_fax = "" + shopcart.getInvoiceFax();
		invoice_email = "" + shopcart.getInvoiceEmail();
		invoice_website = "" + shopcart.getInvoiceWebsite();
		usersegments = mysession.get("usersegments");
		usertests = mysession.get("usertests");

		create(db, mysession, myrequest);

		Iterator orderitems = shopcart.getShoppingCartItems().keySet().iterator();
		while (orderitems.hasNext()) {
			String myorderitem = "" + orderitems.next();
			Orderitem orderitem = (Orderitem) shopcart.getShoppingCartItem(myorderitem);
			orderitem.setOrderId(id);

			Content stockcontent = new Content();
			String stockcontentid = orderitem.getProductId();
			if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
				stockcontent.read(db, config, stockcontentid, "content", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, mysession);
			} else {
				stockcontent.read(db, config, stockcontentid, "content_public", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, mysession);
			}
			if (! stockcontent.getProductNoStockOrder().equals("")) {
				stockcontent.addProductStockAmount(db, -Common.intnumber(orderitem.getItemQuantity()));
			}

			String options = "";
			String[] product_options_selected = ("" + shopcart.getShoppingCart().get(orderitem.getProductId())).split("\\|");
			String[] product_options = orderitem.getProductOptions().split("[\\r\\n]+");
			for (int i=0; i<product_options.length; i++) {
				String product_option = product_options[i];
				Pattern p = Pattern.compile("<([^<>]+)>([^<>]*)<\\/([^<>]+)>");
				Matcher m = p.matcher(product_option);
				if (m.find()) {
					String myname = "" + m.group(1);
					String myvalue = "" + m.group(2);
					String myselected;
					if (product_options_selected.length-1 > i) {
						myselected = product_options_selected[i+1];
					} else {
						myselected = "";
					}
					if (myname.equals("hosting:subdomain")) {
						options += "<" + myname + ">" + myselected + "</" + myname + ">" + "\r\n";
					} else if (myname.equals("hosting:domain")) {
						options += "<" + myname + ">" + myselected + "</" + myname + ">" + "\r\n";
					} else if ((myname.startsWith("hosting:config:")) && (myvalue.equals(""))) {
						options += "<" + myname + ">" + myselected + "</" + myname + ">" + "\r\n";
					} else if (myname.startsWith("hosting:")) {
						options += "<" + myname + ">" + myvalue + "</" + myname + ">" + "\r\n";
					} else {
						options += "<" + myname + ">" + myselected + "</" + myname + ">" + "\r\n";
					}
				}
			}
			orderitem.setProductOptions(options);
			orderitem.calculate(db, config, order_currency);
			orderitem.create(db);
		}
	}
	public void create(DB db) {
		create(db, null, null);
	}
	public void create(DB db, Session mysession, Request myrequest) {
		if (db == null) return;

		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("user_id", "" + Common.integernumber(user_id));
		mydata.put("created", "" + created);
		mydata = setCreatedDateTime(mydata, created);
		mydata.put("updated", "" + updated);
		mydata.put("published", "" + published);
		mydata.put("created_by", "" + created_by);
		mydata.put("updated_by", "" + updated_by);
		mydata.put("published_by", "" + published_by);
		mydata.put("revision", "" + revision);
		mydata.put("status", "" + status);
		mydata.put("status_by", "" + status_by);
		mydata.put("checkedout", "" + checkedout);
		mydata.put("order_quantity", "" + Common.integernumber(order_quantity));
		mydata.put("order_currency", "" + order_currency);
		mydata.put("order_subtotal", "" + Common.number(order_subtotal));
		mydata.put("order_subtotal_base", "" + Common.number(order_subtotal_base));
		mydata.put("tax_description", "" + tax_description);
		mydata.put("tax_total", "" + Common.number(tax_total));
		mydata.put("tax_total_base", "" + Common.number(tax_total_base));
		mydata.put("shipping_description", "" + shipping_description);
		mydata.put("shipping_total", "" + Common.number(shipping_total));
		mydata.put("shipping_total_base", "" + Common.number(shipping_total_base));
		mydata.put("discount_description", "" + discount_description);
		mydata.put("discount_total", "" + Common.number(discount_total));
		mydata.put("discount_total_base", "" + Common.number(discount_total_base));
		mydata.put("order_total", "" + Common.number(order_total));
		mydata.put("order_total_base", "" + Common.number(order_total_base));
		mydata.put("card_type", "" + card_type);
		mydata.put("card_number", "" + card_number);
		mydata.put("card_issuedmonth", "" + card_issuedmonth);
		mydata.put("card_issuedyear", "" + card_issuedyear);
		mydata.put("card_expirymonth", "" + card_expirymonth);
		mydata.put("card_expiryyear", "" + card_expiryyear);
		mydata.put("card_name", "" + card_name);
		mydata.put("card_cvc", "" + card_cvc);
		mydata.put("card_issue", "" + card_issue);
		mydata.put("card_postalcode", "" + card_postalcode);
		mydata.put("delivery_name", "" + delivery_name);
		mydata.put("delivery_organisation", "" + delivery_organisation);
		mydata.put("delivery_address", "" + delivery_address);
		mydata.put("delivery_postalcode", "" + delivery_postalcode);
		mydata.put("delivery_city", "" + delivery_city);
		mydata.put("delivery_state", "" + delivery_state);
		mydata.put("delivery_country", "" + delivery_country);
		mydata.put("delivery_phone", "" + delivery_phone);
		mydata.put("delivery_fax", "" + delivery_fax);
		mydata.put("delivery_email", "" + delivery_email);
		mydata.put("delivery_website", "" + delivery_website);
		mydata.put("invoice_name", "" + invoice_name);
		mydata.put("invoice_organisation", "" + invoice_organisation);
		mydata.put("invoice_address", "" + invoice_address);
		mydata.put("invoice_postalcode", "" + invoice_postalcode);
		mydata.put("invoice_city", "" + invoice_city);
		mydata.put("invoice_state", "" + invoice_state);
		mydata.put("invoice_country", "" + invoice_country);
		mydata.put("invoice_phone", "" + invoice_phone);
		mydata.put("invoice_fax", "" + invoice_fax);
		mydata.put("invoice_email", "" + invoice_email);
		mydata.put("invoice_website", "" + invoice_website);
		mydata.put("paid", "" + paid);
		mydata.put("usersegments", "" + usersegments);
		mydata.put("usertests", "" + usertests);

		UsageLog usagelog = new UsageLog();
		if ((mysession != null) && (myrequest != null)) {
			usagelog = new UsageLog("", "", "", mysession, myrequest);
		}
		mydata.put("clienthost", "" + usagelog.getData("clienthost"));
		mydata.put("clienthosttld", "" + usagelog.getData("clienthosttld"));
		mydata.put("clientuseragent", "" + usagelog.getData("clientuseragent"));
		mydata.put("clientos", "" + usagelog.getData("clientos"));
		mydata.put("clientosversion", "" + usagelog.getData("clientosversion"));
		mydata.put("clientbrowser", "" + usagelog.getData("clientbrowser"));
		mydata.put("clientversion", "" + usagelog.getData("clientversion"));
		mydata.put("clientdevice", "" + usagelog.getData("clientdevice"));
		mydata.put("clientdeviceid", "" + usagelog.getData("clientdeviceid"));
		mydata.put("clientdeviceversion", "" + usagelog.getData("clientdeviceversion"));
		mydata.put("clientusername", "" + usagelog.getData("clientusername"));
		mydata.put("visitorid", "" + usagelog.getData("visitorid"));
		mydata.put("visitorduration", "" + Common.integernumber(usagelog.getData("visitorduration")));
		mydata.put("sessionid", "" + usagelog.getData("sessionid"));
		mydata.put("sessionduration", "" + Common.integernumber(usagelog.getData("sessionduration")));
		mydata.put("requesthost", "" + usagelog.getData("requesthost"));
		mydata.put("requestpath", "" + usagelog.getData("requestpath"));
		mydata.put("requestquery", "" + usagelog.getData("requestquery"));
		mydata.put("requestid", "" + Common.integernumber(usagelog.getData("requestid")));
		mydata.put("requestclass", "" + usagelog.getData("requestclass"));
		mydata.put("requestdatabase", "" + usagelog.getData("requestdatabase"));
		mydata.put("refererid", "" + Common.integernumber(usagelog.getData("refererid")));
		mydata.put("refererclass", "" + usagelog.getData("refererclass"));
		mydata.put("refererdatabase", "" + usagelog.getData("refererdatabase"));
		mydata.put("refererduration", "" + Common.integernumber(usagelog.getData("refererduration")));
//		mydata.put("refererhost", "" + usagelog.getData("refererhost"));
//		mydata.put("refererpath", "" + usagelog.getData("refererpath"));
//		mydata.put("refererquery", "" + usagelog.getData("refererquery"));
//		mydata.put("referersearchengine", "" + usagelog.getData("referersearchengine"));
//		mydata.put("referersearchquery", "" + usagelog.getData("referersearchquery"));

		if (mysession != null) {
			mydata.put("refererhost", "" + mysession.get("refererhost"));
			mydata.put("refererpath", "" + mysession.get("refererpath"));
			mydata.put("refererquery", "" + mysession.get("refererquery"));
			mydata.put("referersearchengine", "" + mysession.get("referersearchengine"));
			mydata.put("referersearchquery", "" + mysession.get("referersearchquery"));
			mydata.put("affiliate", "" + mysession.get("affiliate_id"));
			if (mysession.get("session_entry").length() > 250) {
				mydata.put("session_entry", "" + mysession.get("session_entry").substring(0,250));
			} else {
				mydata.put("session_entry", "" + mysession.get("session_entry"));
			}
		}

		db.insert("orders", mydata);
		id = created_id(db);
	}



	public String created_id(DB db) {
		if (db == null) return "0";
		String created_id = "0";
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("user_id", "" + Common.integernumber(user_id));
		mydata.put("created", "" + created);
		mydata.put("updated", "" + updated);
		mydata.put("published", "" + published);
		mydata.put("created_by", "" + created_by);
		mydata.put("updated_by", "" + updated_by);
		mydata.put("published_by", "" + published_by);
		mydata.put("revision", "" + revision);
		mydata.put("status", "" + status);
		mydata.put("status_by", "" + status_by);
		mydata.put("checkedout", "" + checkedout);
		mydata.put("order_quantity", "" + Common.integernumber(order_quantity));
		mydata.put("order_currency", "" + order_currency);
		mydata.put("order_subtotal", "" + Common.number(order_subtotal));
		mydata.put("tax_description", "" + tax_description);
		mydata.put("tax_total", "" + Common.number(tax_total));
		mydata.put("shipping_description", "" + shipping_description);
		mydata.put("shipping_total", "" + Common.number(shipping_total));
		mydata.put("discount_description", "" + discount_description);
		mydata.put("discount_total", "" + Common.number(discount_total));
		mydata.put("order_total", "" + Common.number(order_total));
		mydata.put("card_type", "" + card_type);
		mydata.put("card_number", "" + card_number);
		mydata.put("card_issuedmonth", "" + card_issuedmonth);
		mydata.put("card_issuedyear", "" + card_issuedyear);
		mydata.put("card_expirymonth", "" + card_expirymonth);
		mydata.put("card_expiryyear", "" + card_expiryyear);
		mydata.put("card_name", "" + card_name);
		mydata.put("card_cvc", "" + card_cvc);
		mydata.put("card_issue", "" + card_issue);
		mydata.put("card_postalcode", "" + card_postalcode);
		mydata.put("delivery_name", "" + delivery_name);
		mydata.put("delivery_organisation", "" + delivery_organisation);
		mydata.put("delivery_address", "" + delivery_address);
		mydata.put("delivery_postalcode", "" + delivery_postalcode);
		mydata.put("delivery_city", "" + delivery_city);
		mydata.put("delivery_state", "" + delivery_state);
		mydata.put("delivery_country", "" + delivery_country);
		mydata.put("delivery_phone", "" + delivery_phone);
		mydata.put("delivery_fax", "" + delivery_fax);
		mydata.put("delivery_email", "" + delivery_email);
		mydata.put("delivery_website", "" + delivery_website);
		mydata.put("invoice_name", "" + invoice_name);
		mydata.put("invoice_organisation", "" + invoice_organisation);
		mydata.put("invoice_address", "" + invoice_address);
		mydata.put("invoice_postalcode", "" + invoice_postalcode);
		mydata.put("invoice_city", "" + invoice_city);
		mydata.put("invoice_state", "" + invoice_state);
		mydata.put("invoice_country", "" + invoice_country);
		mydata.put("invoice_phone", "" + invoice_phone);
		mydata.put("invoice_fax", "" + invoice_fax);
		mydata.put("invoice_email", "" + invoice_email);
		mydata.put("invoice_website", "" + invoice_website);
		mydata.put("paid", "" + paid);
		mydata.put("usersegments", "" + usersegments);
		mydata.put("usertests", "" + usertests);
		HashMap row = db.select("orders", mydata, "order by id desc");
		if (row != null) {
			created_id = "" + row.get("id");
		}
		return created_id;
	}



	public void update(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("user_id", "" + Common.integernumber(user_id));
			mydata.put("created", "" + created);
			mydata = setCreatedDateTime(mydata, created);
			mydata.put("updated", "" + updated);
			mydata.put("published", "" + published);
			mydata.put("created_by", "" + created_by);
			mydata.put("updated_by", "" + updated_by);
			mydata.put("published_by", "" + published_by);
			mydata.put("revision", "" + revision);
			mydata.put("status", "" + status);
			mydata.put("status_by", "" + status_by);
			mydata.put("checkedout", "" + checkedout);
			mydata.put("order_quantity", "" + Common.integernumber(order_quantity));
			mydata.put("order_currency", "" + order_currency);
			mydata.put("order_subtotal", "" + Common.number(order_subtotal));
			mydata.put("order_subtotal_base", "" + Common.number(order_subtotal_base));
			mydata.put("tax_description", "" + tax_description);
			mydata.put("tax_total", "" + Common.number(tax_total));
			mydata.put("tax_total_base", "" + Common.number(tax_total_base));
			mydata.put("shipping_description", "" + shipping_description);
			mydata.put("shipping_total", "" + Common.number(shipping_total));
			mydata.put("shipping_total_base", "" + Common.number(shipping_total_base));
			mydata.put("discount_description", "" + discount_description);
			mydata.put("discount_total", "" + Common.number(discount_total));
			mydata.put("discount_total_base", "" + Common.number(discount_total_base));
			mydata.put("order_total", "" + Common.number(order_total));
			mydata.put("order_total_base", "" + Common.number(order_total_base));
			mydata.put("card_type", "" + card_type);
			mydata.put("card_number", "" + card_number);
			mydata.put("card_issuedmonth", "" + card_issuedmonth);
			mydata.put("card_issuedyear", "" + card_issuedyear);
			mydata.put("card_expirymonth", "" + card_expirymonth);
			mydata.put("card_expiryyear", "" + card_expiryyear);
			mydata.put("card_name", "" + card_name);
			mydata.put("card_cvc", "" + card_cvc);
			mydata.put("card_issue", "" + card_issue);
			mydata.put("card_postalcode", "" + card_postalcode);
			mydata.put("delivery_name", "" + delivery_name);
			mydata.put("delivery_organisation", "" + delivery_organisation);
			mydata.put("delivery_address", "" + delivery_address);
			mydata.put("delivery_postalcode", "" + delivery_postalcode);
			mydata.put("delivery_city", "" + delivery_city);
			mydata.put("delivery_state", "" + delivery_state);
			mydata.put("delivery_country", "" + delivery_country);
			mydata.put("delivery_phone", "" + delivery_phone);
			mydata.put("delivery_fax", "" + delivery_fax);
			mydata.put("delivery_email", "" + delivery_email);
			mydata.put("delivery_website", "" + delivery_website);
			mydata.put("invoice_name", "" + invoice_name);
			mydata.put("invoice_organisation", "" + invoice_organisation);
			mydata.put("invoice_address", "" + invoice_address);
			mydata.put("invoice_postalcode", "" + invoice_postalcode);
			mydata.put("invoice_city", "" + invoice_city);
			mydata.put("invoice_state", "" + invoice_state);
			mydata.put("invoice_country", "" + invoice_country);
			mydata.put("invoice_phone", "" + invoice_phone);
			mydata.put("invoice_fax", "" + invoice_fax);
			mydata.put("invoice_email", "" + invoice_email);
			mydata.put("invoice_website", "" + invoice_website);
			mydata.put("paid", "" + paid);
			mydata.put("usersegments", "" + usersegments);
			mydata.put("usertests", "" + usertests);
			db.update("orders", "id", id, mydata);
		}
	}



	public void delete(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			db.delete("orderitems", "order_id", Common.integer(id));
			db.delete("orders", "id", Common.integer(id));
		}
	}



	public void closeRecords(DB db) {
		if (db == null) return;
		db.closeRecords(rs);
		rs = null;
//		init();
	}



	public boolean records(DB db, String SQL) {
		if ((db == null) || db.isClosed()) return false;
		if ((SQL != null) && (! SQL.equals(""))) {
			rs = db.records(SQL);
			return true;
		} else {
			HashMap row = db.records(rs);
			if (row != null) {
				getRecord(db, row);
				return true;
			} else {
				init();
				return false;
			}
		}
	}



	public boolean orderitems(DB db) {
		if ((db == null) || db.isClosed()) return false;
		if (Common.intnumber(id) > 0) {
			if (orderitem_records == null) {
				orderitem_records = new Orderitem();
				orderitem_records.records(db, "select * from orderitems where order_id=" + Common.intnumber(id));
			}
			boolean rc = orderitem_records.records(db, "");
			if (! rc) orderitem_records = null;
			return rc;
		} else {
			return false;
		}
	}



	public void checkout(DB db, String username) {
		checkout(db, new Configuration(), username);
	}
	public void checkout(DB db, Configuration myconfig, String username) {
		if (db == null) return;
		String SQL = "";
		if (! username.equals("")) {
			if (getCheckoutPermissions(username, db, myconfig)) {
				SQL = "update orders set checkedout='" + Common.SQL_clean(username) + "' where id=" + Common.integer(id);
			} else {
				SQL = "update orders set checkedout='" + Common.SQL_clean(username) + "' where (" + db.is_blank("checkedout") + ") and id=" + Common.integer(id);
			}
		}
		if (! SQL.equals("")) {
			checkedout = username;
			db.execute(SQL);
			Cache.clear(db, "orders");
		}
	}



	public void checkin(DB db, String username, String superadmin) {
		checkin(db, new Configuration(), username, superadmin);
	}
	public void checkin(DB db, Configuration myconfig, String username, String superadmin) {
		if (db == null) return;
		String SQL = "";
		if (username.equals(superadmin)) {
			SQL = "update orders set checkedout='' where id=" + Common.integer(id);
		} else if (! username.equals("")) {
			if (getCheckoutPermissions(username, db, myconfig)) {
				SQL = "update orders set checkedout='' where id=" + Common.integer(id);
			} else {
				SQL = "update orders set checkedout='' where checkedout='" + Common.SQL_clean(username) + "' and id=" + Common.integer(id);
			}
		}
		if (! SQL.equals("")) {
			checkedout = "";
			db.execute(SQL);
			Cache.clear(db, "orders");
		}
	}



	public String administratorsSQLfromwhere(DB db, Configuration myconfig, boolean administrators, boolean broad_search) {
		return administratorsSQLfromwhere(db, myconfig, administrators, broad_search, true);
	}
	public String administratorsSQLfromwhere(DB db, Configuration myconfig, boolean administrators, boolean broad_search, boolean subgroupstypes) {
		String SQLwhere = "";
		if ((id != null) && (! id.equals("")) && (! id.equals("0")) && (! id.equals("-1"))) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "users.userclass", "administrator");
			SQLwhere += " and ((1=0)";

			boolean restricted_administrators = false;

			if (administrators) {
				String SQLwhereAdministrator = "";
				String myadministratorstype = myconfig.get(db, "order_admin_users_type");
				if (! myadministratorstype.equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(myadministratorstype);
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(myadministratorstype, myadministratorstype);
					if (! SQLwhereAdministrator.equals("")) SQLwhereAdministrator += " and ";
					SQLwhereAdministrator += "(";
					SQLwhereAdministrator += "users.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereAdministrator += " or ";
						SQLwhereAdministrator += "(users.username=users_usertypes.username and users_usertypes.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereAdministrator += ")";
				}
				String myadministratorsgroup = myconfig.get(db, "order_admin_users_group");
				if (! myadministratorsgroup.equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(myadministratorsgroup);
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(myadministratorsgroup, myadministratorsgroup);
					if (! SQLwhereAdministrator.equals("")) SQLwhereAdministrator += " and ";
					SQLwhereAdministrator += "(";
					SQLwhereAdministrator += "users.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereAdministrator += " or ";
						SQLwhereAdministrator += "(users.username=users_usergroups.username and users_usergroups.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereAdministrator += ")";
				}
				if (! SQLwhereAdministrator.equals("")) {
					SQLwhere += " or (" + SQLwhereAdministrator + ")";
					restricted_administrators = true;
				}
			}

			if (broad_search && (! restricted_administrators)) {
				SQLwhere += " or (1=1)";
			}

			SQLwhere += ")";
		}
		String SQLfrom = " from users";
		if (subgroupstypes) {
			if (SQLwhere.indexOf("users_usergroups")>=0) {
				SQLfrom += ",users_usergroups";
			}
			if (SQLwhere.indexOf("users_usertypes")>=0) {
				SQLfrom += ",users_usertypes";
			}
		}
		return SQLfrom + " " + SQLwhere;
	}



	public String[] administratorsEmails(Session mysession, Configuration myconfig, DB db) {
		HashMap<String,String> emails = new HashMap<String,String>();
		if ((id != null) && (! id.equals("")) && (! id.equals("0")) && (! id.equals("-1"))) {
			String admin_email = "";
			String SQL = "select distinct users.id as id, users.email as email, users.username as username " + administratorsSQLfromwhere(db,myconfig,true,false,false) + " and (" + db.is_not_blank("username") + ")";
			User user = new User();
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				String myusername = user.getUsername();
				String myemail = user.getEmail();
				user.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myusername);
				if (user.getEmail().equals("")) user.setEmail(myemail);
				if (! user.getEmail().equals("")) {
					emails.put(user.getEmail(), user.getEmail());
				}
			}
			SQL = "select distinct users.id as id, users.email as email, users.username as username " + administratorsSQLfromwhere(db,myconfig,true,false,true) + " and (" + db.is_not_blank("username") + ")";
			user = new User();
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				String myusername = user.getUsername();
				String myemail = user.getEmail();
				user.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myusername);
				if (user.getEmail().equals("")) user.setEmail(myemail);
				if (! user.getEmail().equals("")) {
					emails.put(user.getEmail(), user.getEmail());
				}
			}
		}
		if ((emails.size() == 0) && (! myconfig.get(db, "superadmin_email").equals(""))) {
			emails.put(myconfig.get(db, "superadmin_email"), myconfig.get(db, "superadmin_email"));
		}
		return (String[]) emails.values().toArray(new String[0]);
	}



	public String getId() {
		return id;
	}
	public void setId(String value) {
		id = value;
	}



	public String getUserId() {
		return user_id;
	}
	public void setUserId(String value) {
		user_id = value;
	}



	public String getCreated() {
		return created;
	}
	public void setCreated(String value) {
		created = value;
	}



	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String value) {
		updated = value;
	}



	public String getPublished() {
		if (published != null) {
			return published;
		} else {
			return "";
		}
	}
	public void setPublished(String value) {
		published = value;
	}



	public String getCreatedBy() {
		return created_by;
	}
	public void setCreatedBy(String value) {
		created_by = value;
	}



	public String getUpdatedBy() {
		return updated_by;
	}
	public void setUpdatedBy(String value) {
		updated_by = value;
	}



	public String getPublishedBy() {
		return published_by;
	}
	public void setPublishedBy(String value) {
		published_by = value;
	}



	public String getRevision() {
		return revision;
	}
	public void setRevision(String value) {
		revision = value;
	}



	public String getStatus() {
		return status;
	}
	public void setStatus(String value) {
		status = value;
	}



	public String getStatusBy() {
		return status_by;
	}
	public void setStatusBy(String value) {
		status_by = value;
	}



	public String getCheckedOut() {
		return checkedout;
	}
	public void setCheckedOut(String value) {
		checkedout = value;
	}



	public String getOrderAvailability() {
		return order_availability;
	}
	public void setOrderAvailability(String value) {
		order_quantity = value;
	}



	public String getOrderQuantity() {
		return Common.numberformat(order_quantity, 0);
	}
	public void setOrderQuantity(String value) {
		order_quantity = value;
	}
	public void setOrderQuantity(int value) {
		order_quantity = "" + value;
	}



	public String getOrderCurrencyTitle() {
		return order_currencytitle;
	}
	public void setOrderCurrencyTitle(String value) {
		order_currencytitle = value;
	}



	public String getOrderCurrency() {
		return order_currency;
	}
	public void setOrderCurrency(String value) {
		order_currency = value;
	}



	public String getOrderSubtotal() {
		return Common.numberformat(order_subtotal, 2);
	}
	public void setOrderSubtotal(String value) {
		order_subtotal = value;
	}
	public void setOrderSubtotal(double value) {
		order_subtotal = "" + value;
	}



	public String getTaxDescription() {
		return tax_description;
	}
	public void setTaxDescription(String value) {
		tax_description = value;
	}



	public String getTaxTotal() {
		return Common.numberformat(tax_total, 2);
	}
	public void setTaxTotal(String value) {
		tax_total = value;
	}
	public void setTaxTotal(double value) {
		tax_total = "" + value;
	}



	public String getDiscountDescription() {
		return discount_description;
	}
	public void setDiscountDescription(String value) {
		discount_description = value;
	}



	public String getDiscountTotal() {
		return Common.numberformat(discount_total, 2);
	}
	public void setDiscountTotal(String value) {
		discount_total = value;
	}
	public void setDiscountTotal(double value) {
		discount_total = "" + value;
	}



	public String getShippingDescription() {
		return shipping_description;
	}
	public void setShippingDescription(String value) {
		shipping_description = value;
	}



	public String getShippingTotal() {
		return Common.numberformat(shipping_total, 2);
	}
	public void setShippingTotal(String value) {
		shipping_total = value;
	}
	public void setShippingTotal(double value) {
		shipping_total = "" + value;
	}



	public String getOrderTotal() {
		return Common.numberformat(order_total, 2);
	}
	public void setOrderTotal(String value) {
		order_total = value;
	}
	public void setOrderTotal(double value) {
		order_total = "" + value;
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



	public String getPaid() {
		return paid;
	}
	public void setPaid(String value) {
		paid = value;
	}



	public String getUserSegments() {
		return usersegments;
	}
	public void setUserSegments(String value) {
		usersegments = value;
	}



	public String getUserTests() {
		return usertests;
	}
	public void setUserTests(String value) {
		usertests = value;
	}



	public Orderitem getOrderitem() {
		return orderitem_records;
	}
	public void setOrderitem(Orderitem value) {
		orderitem_records = value;
	}



	public boolean getCheckoutPermissions(String username, DB db, Configuration myconfig) {
		if (_DEBUG_) { System.out.println("AsbruWCM/Order.getCheckoutPermissions:" + id + ":username:"+username + ":checkedout:"+getCheckedOut()); }
		boolean permissions;
		if (getCheckedOut().equals(username)) {
			// ok - checked out by this user
			permissions = true;
		} else if ((! getCheckedOut().equals("")) && (username.equals(myconfig.get(db, "superadmin")))) {
			// ok - superadmin
			permissions = true;
		} else if (! getCheckedOut().equals("")) {
			// checked out by other user
			permissions = false;
		} else {
			// ok - not checked out
			permissions = true;
		}
		if (_DEBUG_) { System.out.println("AsbruWCM/Order.getCheckoutPermissions:" + id + ":username:"+username + ":checkedout:"+getCheckedOut() + ":permissions:"+permissions); }
		return permissions;
	}



}
