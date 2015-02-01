package HardCore;

import java.sql.*;
import java.util.HashMap;

public class Orderitem {
	private String id = "";
	private String order_id = "";
	private String product_id = "";
	private String version = "";
	private String device = "";
	private String usersegment = "";
	private String usertest = "";
	private String server_filename = "";
	private String title = "";
	private String author = "";
	private String keywords = "";
	private String description = "";
	private String summary = "";
	private String image1 = "";
	private String image2 = "";
	private String image3 = "";
	private String file1 = "";
	private String file2 = "";
	private String file3 = "";
	private String link1 = "";
	private String link2 = "";
	private String link3 = "";
	private String product_code = "";
	private String product_currencytitle = "";
	private String product_currency = "";
	private String product_price = "";
	private String product_period = "";
	private String product_weight = "";
	private String product_volume = "";
	private String product_width = "";
	private String product_height = "";
	private String product_depth = "";
	private String product_brand = "";
	private String product_colour = "";
	private String product_size = "";
	private String product_stock = "";
	private String product_comment = "";
	private String product_status = "";
	private String product_email = "";
	private String product_url = "";
	private String product_delivery = "";
	private String product_user = "";
	private String product_page = "";
	private String product_program = "";
	private String product_hosting = "";
	private String product_options = "";
	private String product_content = "";
	private String product_contentgroup = "";
	private String product_contenttype = "";
	private String product_imagegroup = "";
	private String product_imagetype = "";
	private String product_filegroup = "";
	private String product_filetype = "";
	private String product_linkgroup = "";
	private String product_linktype = "";
	private String product_productgroup = "";
	private String product_producttype = "";
	private String product_usergroup = "";
	private String product_usertype = "";
	private String item_quantity = "";
	private String item_subtotal = "";
	private String item_subtotal_base = "";
	private String item_total = "";
	private String item_total_base = "";
	private String discount_description = "";
	private String discount_total = "";
	private String discount_total_base = "";
	private String shipping_description = "";
	private String shipping_total = "";
	private String shipping_total_base = "";
	private String tax_description = "";
	private String tax_total = "";
	private String tax_total_base = "";

	private HashMap<String,String> shopcart = null;

	private	Statement rs = null;



	public Orderitem() {
		init();
	}



	public void init() {
		id = "";
		order_id = "0";
		product_id = "0";
		version = "";
		device = "";
		usersegment = "";
		usertest = "";
		server_filename = "";
		title = "";
		author = "";
		keywords = "";
		description = "";
		summary = "";
		image1 = "";
		image2 = "";
		image3 = "";
		file1 = "";
		file2 = "";
		file3 = "";
		link1 = "";
		link2 = "";
		link3 = "";
		product_code = "";
		product_currencytitle = "";
		product_currency = "";
		product_price = "";
		product_period = "";
		product_weight = "";
		product_volume = "";
		product_width = "";
		product_height = "";
		product_depth = "";
		product_brand = "";
		product_colour = "";
		product_size = "";
		product_stock = "";
		product_comment = "";
		product_status = "";
		product_email = "";
		product_url = "";
		product_delivery = "";
		product_user = "";
		product_page = "";
		product_program = "";
		product_hosting = "";
		product_options = "";
		product_content = "";
		product_contentgroup = "";
		product_contenttype = "";
		product_imagegroup = "";
		product_imagetype = "";
		product_filegroup = "";
		product_filetype = "";
		product_linkgroup = "";
		product_linktype = "";
		product_productgroup = "";
		product_producttype = "";
		product_usergroup = "";
		product_usertype = "";
		item_quantity = "";
		item_subtotal = "";
		item_subtotal_base = "";
		item_total = "";
		item_total_base = "";
		discount_description = "";
		discount_total = "";
		discount_total_base = "";
		shipping_description = "";
		shipping_total = "";
		shipping_total_base = "";
		tax_description = "";
		tax_total = "";
		tax_total_base = "";
	}



	public void read(DB db, String id) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			String SQL = "select * from orderitems where id=" + Common.integer(id);
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void getRecord(DB db, HashMap<String,String> record) {
		if (record.get("id") != null) { id = "" + record.get("id"); } else { id = ""; }
		if (record.get("order_id") != null) { order_id = "" + record.get("order_id"); } else { order_id = ""; }
		if (record.get("product_id") != null) { product_id = "" + record.get("product_id"); } else { product_id = ""; }
		if (record.get("version") != null) { version = "" + record.get("version"); } else { version = ""; }
		if (record.get("device") != null) { device = "" + record.get("device"); } else { device = ""; }
		if (record.get("usersegment") != null) { usersegment = "" + record.get("usersegment"); } else { usersegment = ""; }
		if (record.get("usertest") != null) { usertest = "" + record.get("usertest"); } else { usertest = ""; }
		if (record.get("server_filename") != null) { server_filename = "" + record.get("server_filename"); } else { server_filename = ""; }
		if (record.get("title") != null) { title = "" + record.get("title"); } else { title = ""; }
		if (record.get("author") != null) { author = "" + record.get("author"); } else { author = ""; }
		if (record.get("keywords") != null) { keywords = "" + record.get("keywords"); } else { keywords = ""; }
		if (record.get("description") != null) { description = "" + record.get("description"); } else { description = ""; }
		if (record.get("summary") != null) { summary = "" + record.get("summary"); } else { summary = ""; }
		if (record.get("image1") != null) { image1 = "" + record.get("image1"); } else { image1 = ""; }
		if (record.get("image2") != null) { image2 = "" + record.get("image2"); } else { image2 = ""; }
		if (record.get("image3") != null) { image3 = "" + record.get("image3"); } else { image3 = ""; }
		if (record.get("file1") != null) { file1 = "" + record.get("file1"); } else { file1 = ""; }
		if (record.get("file2") != null) { file2 = "" + record.get("file2"); } else { file2 = ""; }
		if (record.get("file3") != null) { file3 = "" + record.get("file3"); } else { file3 = ""; }
		if (record.get("link1") != null) { link1 = "" + record.get("link1"); } else { link1 = ""; }
		if (record.get("link2") != null) { link2 = "" + record.get("link2"); } else { link2 = ""; }
		if (record.get("link3") != null) { link3 = "" + record.get("link3"); } else { link3 = ""; }
		if (record.get("product_code") != null) { product_code = "" + record.get("product_code"); } else { product_code = ""; }
		if (record.get("product_currency") != null) { product_currency = "" + record.get("product_currency"); } else { product_currency = ""; }
		product_currencytitle = "";
		Currency mycurrency = new Currency();
		mycurrency.readSymbol(db, product_currency);
		product_currencytitle = mycurrency.getTitle();
		if (record.get("product_price") != null) { product_price = "" + record.get("product_price"); } else { product_price = ""; }
		if (record.get("product_period") != null) { product_period = "" + record.get("product_period"); } else { product_period = ""; }
		if (record.get("product_weight") != null) { product_weight = "" + record.get("product_weight"); } else { product_weight = ""; }
		if (record.get("product_volume") != null) { product_volume = "" + record.get("product_volume"); } else { product_volume = ""; }
		if (record.get("product_width") != null) { product_width = "" + record.get("product_width"); } else { product_width = ""; }
		if (record.get("product_height") != null) { product_height = "" + record.get("product_height"); } else { product_height = ""; }
		if (record.get("product_depth") != null) { product_depth = "" + record.get("product_depth"); } else { product_depth = ""; }
		if (record.get("product_brand") != null) { product_brand = "" + record.get("product_brand"); } else { product_brand = ""; }
		if (record.get("product_colour") != null) { product_colour = "" + record.get("product_colour"); } else { product_colour = ""; }
		if (record.get("product_size") != null) { product_size = "" + record.get("product_size"); } else { product_size = ""; }
		if (record.get("product_stock") != null) { product_stock = "" + record.get("product_stock"); } else { product_stock = ""; }
		if (record.get("product_comment") != null) { product_comment = "" + record.get("product_comment"); } else { product_comment = ""; }
		if (record.get("product_email") != null) { product_email = "" + record.get("product_email"); } else { product_email = ""; }
		if (record.get("product_url") != null) { product_url = "" + record.get("product_url"); } else { product_url = ""; }
		if (record.get("product_delivery") != null) { product_delivery = "" + record.get("product_delivery"); } else { product_delivery = ""; }
		if (record.get("product_user") != null) { product_user = "" + record.get("product_user"); } else { product_user = ""; }
		if (record.get("product_page") != null) { product_page = "" + record.get("product_page"); } else { product_page = ""; }
		if (record.get("product_program") != null) { product_program = "" + record.get("product_program"); } else { product_program = ""; }
		if (record.get("product_hosting") != null) { product_hosting = "" + record.get("product_hosting"); } else { product_hosting = ""; }
		if (record.get("product_options") != null) { product_options = "" + record.get("product_options"); } else { product_options = ""; }
		if (record.get("product_content") != null) product_content = "" + record.get("product_content"); else product_content = "";
		if (record.get("product_contentgroup") != null) product_contentgroup = "" + record.get("product_contentgroup"); else product_contentgroup = "";
		if (record.get("product_contenttype") != null) product_contenttype = "" + record.get("product_contenttype"); else product_contenttype = "";
		if (record.get("product_imagegroup") != null) product_imagegroup = "" + record.get("product_imagegroup"); else product_imagegroup = "";
		if (record.get("product_imagetype") != null) product_imagetype = "" + record.get("product_imagetype"); else product_imagetype = "";
		if (record.get("product_filegroup") != null) product_filegroup = "" + record.get("product_filegroup"); else product_filegroup = "";
		if (record.get("product_filetype") != null) product_filetype = "" + record.get("product_filetype"); else product_filetype = "";
		if (record.get("product_linkgroup") != null) product_linkgroup = "" + record.get("product_linkgroup"); else product_linkgroup = "";
		if (record.get("product_linktype") != null) product_linktype = "" + record.get("product_linktype"); else product_linktype = "";
		if (record.get("product_productgroup") != null) product_productgroup = "" + record.get("product_productgroup"); else product_productgroup = "";
		if (record.get("product_producttype") != null) product_producttype = "" + record.get("product_producttype"); else product_producttype = "";
		if (record.get("product_usergroup") != null) product_usergroup = "" + record.get("product_usergroup"); else product_usergroup = "";
		if (record.get("product_usertype") != null) product_usertype = "" + record.get("product_usertype"); else product_usertype = "";
		if (record.get("item_quantity") != null) { item_quantity = "" + record.get("item_quantity"); } else { item_quantity = ""; }
		if (record.get("item_subtotal") != null) { item_subtotal = "" + record.get("item_subtotal"); } else { item_subtotal = ""; }
		if (record.get("item_total") != null) { item_total = "" + record.get("item_total"); } else { item_total = ""; }
		if (record.get("discount_description") != null) { discount_description = "" + record.get("discount_description"); } else { discount_description = ""; }
		if (record.get("discount_total") != null) { discount_total = "" + record.get("discount_total"); } else { discount_total = ""; }
		if (record.get("shipping_description") != null) { shipping_description = "" + record.get("shipping_description"); } else { shipping_description = ""; }
		if (record.get("shipping_total") != null) { shipping_total = "" + record.get("shipping_total"); } else { shipping_total = ""; }
		if (record.get("tax_description") != null) { tax_description = "" + record.get("tax_description"); } else { tax_description = ""; }
		if (record.get("tax_total") != null) { tax_total = "" + record.get("tax_total"); } else { tax_total = ""; }
	}



	public void getForm(Request request) {
		order_id = request.getParameter("order_id");
		product_id = request.getParameter("product_id");
		version = request.getParameter("version");
		device = request.getParameter("device");
		usersegment = request.getParameter("usersegment");
		usertest = request.getParameter("usertest");
		server_filename = request.getParameter("server_filename");
		title = request.getParameter("title");
		author = request.getParameter("author");
		keywords = request.getParameter("keywords");
		description = request.getParameter("description");
		summary = request.getParameter("summary");
		image1 = request.getParameter("image1");
		image2 = request.getParameter("image2");
		image3 = request.getParameter("image3");
		file1 = request.getParameter("file1");
		file2 = request.getParameter("file2");
		file3 = request.getParameter("file3");
		link1 = request.getParameter("link1");
		link2 = request.getParameter("link2");
		link3 = request.getParameter("link3");
		product_code = request.getParameter("product_code");
		product_currencytitle = "";
		product_currency = request.getParameter("product_currency");
		product_price = request.getParameter("product_price");
		product_period = request.getParameter("product_period");
		product_weight = request.getParameter("product_weight");
		product_volume = request.getParameter("product_volume");
		product_width = request.getParameter("product_width");
		product_height = request.getParameter("product_height");
		product_depth = request.getParameter("product_depth");
		product_brand = request.getParameter("product_brand");
		product_colour = request.getParameter("product_colour");
		product_size = request.getParameter("product_size");
		product_stock = request.getParameter("product_stock");
		product_comment = request.getParameter("product_comment");
		product_email = request.getParameter("product_email");
		product_url = request.getParameter("product_url");
		product_delivery = request.getParameter("product_delivery");
		product_user = request.getParameter("product_user");
		product_page = request.getParameter("product_page");
		product_program = request.getParameter("product_program");
		product_hosting = request.getParameter("product_hosting");
		product_options = request.getParameter("product_options");
		product_content = request.getParameter("product_content");
		product_contentgroup = request.getParameter("product_contentgroup");
		product_contenttype = request.getParameter("product_contenttype");
		product_imagegroup = request.getParameter("product_imagegroup");
		product_imagetype = request.getParameter("product_imagetype");
		product_filegroup = request.getParameter("product_filegroup");
		product_filetype = request.getParameter("product_filetype");
		product_linkgroup = request.getParameter("product_linkgroup");
		product_linktype = request.getParameter("product_linktype");
		product_productgroup = request.getParameter("product_productgroup");
		product_producttype = request.getParameter("product_producttype");
		product_usergroup = request.getParameter("product_usergroup");
		product_usertype = request.getParameter("product_usertype");
		item_quantity = request.getParameter("item_quantity");
		item_subtotal = request.getParameter("item_subtotal");
		item_total = request.getParameter("item_total");
		discount_description = request.getParameter("discount_description");
		discount_total = request.getParameter("discount_total");
		shipping_description = request.getParameter("shipping_description");
		shipping_total = request.getParameter("shipping_total");
		tax_description = request.getParameter("tax_description");
		tax_total = request.getParameter("tax_total");
	}



	public void calculate(DB db, Configuration config, String ordercurrency) {
		if (db == null) return;
		Currency mycurrency = new Currency();
		mycurrency.readSymbol(db, ordercurrency);
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
		item_subtotal_base = "" + Common.number(item_subtotal) * base_rate / 100;
		item_total_base = "" + Common.number(item_total) * base_rate / 100;
		tax_total_base = "" + Common.number(tax_total) * base_rate / 100;
		shipping_total_base = "" + Common.number(shipping_total) * base_rate / 100;
		discount_total_base = "" + Common.number(discount_total) * base_rate / 100;
	}



	public void create(DB db) {
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("order_id", "" + Common.integernumber("" + order_id));
		mydata.put("product_id", "" + Common.integernumber("" + product_id));
		mydata.put("version", "" + version);
		mydata.put("device", "" + device);
		mydata.put("usersegment", "" + usersegment);
		mydata.put("usertest", "" + usertest);
		mydata.put("title", "" + title);
		mydata.put("author", "" + author);
		mydata.put("keywords", "" + keywords);
		mydata.put("description", "" + description);
		mydata.put("summary", "" + summary);
		mydata.put("image1", "" + image1);
		mydata.put("image2", "" + image2);
		mydata.put("image3", "" + image3);
		mydata.put("file1", "" + file1);
		mydata.put("file2", "" + file2);
		mydata.put("file3", "" + file3);
		mydata.put("link1", "" + link1);
		mydata.put("link2", "" + link2);
		mydata.put("link3", "" + link3);
		mydata.put("product_code", "" + product_code);
		mydata.put("product_currency", "" + product_currency);
		mydata.put("product_price", "" + product_price);
		mydata.put("product_period", "" + product_period);
		mydata.put("product_weight", "" + product_weight);
		mydata.put("product_volume", "" + product_volume);
		mydata.put("product_width", "" + product_width);
		mydata.put("product_height", "" + product_height);
		mydata.put("product_depth", "" + product_depth);
		mydata.put("product_brand", "" + product_brand);
		mydata.put("product_colour", "" + product_colour);
		mydata.put("product_size", "" + product_size);
		mydata.put("product_stock", "" + product_stock);
		mydata.put("product_comment", "" + product_comment);
		mydata.put("product_email", "" + product_email);
		mydata.put("product_url", "" + product_url);
		mydata.put("product_delivery", "" + product_delivery);
		mydata.put("product_user", "" + product_user);
		mydata.put("product_page", "" + product_page);
		mydata.put("product_program", "" + product_program);
		mydata.put("product_hosting", "" + product_hosting);
		mydata.put("product_options", "" + product_options);
		mydata.put("product_content", "" + product_content);
		mydata.put("product_contentgroup", "" + product_contentgroup);
		mydata.put("product_contenttype", "" + product_contenttype);
		mydata.put("product_imagegroup", "" + product_imagegroup);
		mydata.put("product_imagetype", "" + product_imagetype);
		mydata.put("product_filegroup", "" + product_filegroup);
		mydata.put("product_filetype", "" + product_filetype);
		mydata.put("product_linkgroup", "" + product_linkgroup);
		mydata.put("product_linktype", "" + product_linktype);
		mydata.put("product_productgroup", "" + product_productgroup);
		mydata.put("product_producttype", "" + product_producttype);
		mydata.put("product_usergroup", "" + product_usergroup);
		mydata.put("product_usertype", "" + product_usertype);
		mydata.put("item_quantity", "" + Common.integernumber(item_quantity));
		mydata.put("item_subtotal", "" + Common.number(item_subtotal));
		mydata.put("item_subtotal_base", "" + Common.number(item_subtotal_base));
		mydata.put("item_total", "" + Common.number(item_total));
		mydata.put("item_total_base", "" + Common.number(item_total_base));
		mydata.put("discount_description", "" + discount_description);
		mydata.put("discount_total", "" + Common.number(discount_total));
		mydata.put("discount_total_base", "" + Common.number(discount_total_base));
		mydata.put("shipping_description", "" + shipping_description);
		mydata.put("shipping_total", "" + Common.number(shipping_total));
		mydata.put("shipping_total_base", "" + Common.number(shipping_total_base));
		mydata.put("tax_description", "" + tax_description);
		mydata.put("tax_total", "" + Common.number(tax_total));
		mydata.put("tax_total_base", "" + Common.number(tax_total_base));
		db.insert("orderitems", mydata);
	}



	public void update(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("order_id", "" + Common.integernumber("" + order_id));
			mydata.put("product_id", "" + Common.integernumber("" + product_id));
			mydata.put("version", "" + version);
			mydata.put("device", "" + device);
			mydata.put("usersegment", "" + usersegment);
			mydata.put("usertest", "" + usertest);
			mydata.put("title", "" + title);
			mydata.put("author", "" + author);
			mydata.put("keywords", "" + keywords);
			mydata.put("description", "" + description);
			mydata.put("summary", "" + summary);
			mydata.put("image1", "" + image1);
			mydata.put("image2", "" + image2);
			mydata.put("image3", "" + image3);
			mydata.put("file1", "" + file1);
			mydata.put("file2", "" + file2);
			mydata.put("file3", "" + file3);
			mydata.put("link1", "" + link1);
			mydata.put("link2", "" + link2);
			mydata.put("link3", "" + link3);
			mydata.put("product_code", "" + product_code);
			mydata.put("product_currency", "" + product_currency);
			mydata.put("product_price", "" + product_price);
			mydata.put("product_period", "" + product_period);
			mydata.put("product_weight", "" + product_weight);
			mydata.put("product_volume", "" + product_volume);
			mydata.put("product_width", "" + product_width);
			mydata.put("product_height", "" + product_height);
			mydata.put("product_depth", "" + product_depth);
			mydata.put("product_brand", "" + product_brand);
			mydata.put("product_colour", "" + product_colour);
			mydata.put("product_size", "" + product_size);
			mydata.put("product_stock", "" + product_stock);
			mydata.put("product_comment", "" + product_comment);
			mydata.put("product_email", "" + product_email);
			mydata.put("product_url", "" + product_url);
			mydata.put("product_delivery", "" + product_delivery);
			mydata.put("product_user", "" + product_user);
			mydata.put("product_page", "" + product_page);
			mydata.put("product_program", "" + product_program);
			mydata.put("product_hosting", "" + product_hosting);
			mydata.put("product_options", "" + product_options);
			mydata.put("product_content", "" + product_content);
			mydata.put("product_contentgroup", "" + product_contentgroup);
			mydata.put("product_contenttype", "" + product_contenttype);
			mydata.put("product_imagegroup", "" + product_imagegroup);
			mydata.put("product_imagetype", "" + product_imagetype);
			mydata.put("product_filegroup", "" + product_filegroup);
			mydata.put("product_filetype", "" + product_filetype);
			mydata.put("product_linkgroup", "" + product_linkgroup);
			mydata.put("product_linktype", "" + product_linktype);
			mydata.put("product_productgroup", "" + product_productgroup);
			mydata.put("product_producttype", "" + product_producttype);
			mydata.put("product_usergroup", "" + product_usergroup);
			mydata.put("product_usertype", "" + product_usertype);
			mydata.put("item_quantity", "" + Common.integernumber(item_quantity));
			mydata.put("item_subtotal", "" + Common.number(item_subtotal));
			mydata.put("item_subtotal_base", "" + Common.number(item_subtotal_base));
			mydata.put("item_total", "" + Common.number(item_total));
			mydata.put("item_total_base", "" + Common.number(item_total_base));
			mydata.put("discount_description", "" + discount_description);
			mydata.put("discount_total", "" + Common.number(discount_total));
			mydata.put("discount_total_base", "" + Common.number(discount_total_base));
			mydata.put("shipping_description", "" + shipping_description);
			mydata.put("shipping_total", "" + Common.number(shipping_total));
			mydata.put("shipping_total_base", "" + Common.number(shipping_total_base));
			mydata.put("tax_description", "" + tax_description);
			mydata.put("tax_total", "" + Common.number(tax_total));
			mydata.put("tax_total_base", "" + Common.number(tax_total_base));
			db.update("orderitems", "id", id, mydata);
		}
	}



	public void delete(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			db.delete("orderitems", "id", id);
		}
	}



	public boolean records(DB db, String SQL) {
		if ((db == null) || db.isClosed()) return false;
		if ((SQL != null) && (! SQL.equals(""))) {
			rs = db.records(SQL);
			return true;
		} else {
			HashMap<String,String> row = db.records(rs);
			if (row != null) {
				getRecord(db, row);
				return true;
			} else {
				init();
				return false;
			}
		}
	}



	public String getId() {
		return id;
	}
	public void setId(String value) {
		id = value;
	}



	public String getOrderId() {
		return order_id;
	}
	public void setOrderId(String value) {
		order_id = value;
	}



	public String getProductId() {
		return product_id;
	}
	public void setProductId(String value) {
		product_id = value;
	}



	public String getProductIdTitle(DB db, Configuration config) {
		Content myproduct = new Content(null);
		myproduct.public_read(db, config, product_id);
		return myproduct.getTitle();
	}



	public String getVersion() {
		return version;
	}
	public void setVersion(String value) {
		version = value;
	}



	public String getDevice() {
		return device;
	}
	public void setDevice(String value) {
		device = value;
	}



	public String getUsersegment() {
		return usersegment;
	}
	public void setUsersegment(String value) {
		usersegment = value;
	}



	public String getUsertest() {
		return usertest;
	}
	public void setUsertest(String value) {
		usertest = value;
	}



	public String getServerFilename() {
		return server_filename;
	}
	public void setServerFilename(String value) {
		server_filename = value;
	}



	public String getTitle() {
		return title;
	}
	public void setTitle(String value) {
		title = value;
	}



	public String getAuthor() {
		return author;
	}
	public void setAuthor(String value) {
		author = value;
	}



	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String value) {
		keywords = value;
	}



	public String getDescription() {
		return description;
	}
	public void setDescription(String value) {
		description = value;
	}



	public String getSummary() {
		return summary;
	}
	public void setSummary(String value) {
		summary = value;
	}



	public String getImage1() {
		return image1;
	}
	public void setImage1(String value) {
		image1 = value;
	}



	public String getImage2() {
		return image2;
	}
	public void setImage2(String value) {
		image2 = value;
	}



	public String getImage3() {
		return image3;
	}
	public void setImage3(String value) {
		image3 = value;
	}



	public String getFile1() {
		return file1;
	}
	public void setFile1(String value) {
		file1 = value;
	}



	public String getFile2() {
		return file2;
	}
	public void setFile2(String value) {
		file2 = value;
	}



	public String getFile3() {
		return file3;
	}
	public void setFile3(String value) {
		file3 = value;
	}



	public String getLink1() {
		return link1;
	}
	public void setLink1(String value) {
		link1 = value;
	}



	public String getLink2() {
		return link2;
	}
	public void setLink2(String value) {
		link2 = value;
	}



	public String getLink3() {
		return link3;
	}
	public void setLink3(String value) {
		link3 = value;
	}



	public String getProductCode() {
		return product_code;
	}
	public void setProductCode(String value) {
		product_code = value;
	}



	public String getProductCurrencyTitle() {
		return product_currencytitle;
	}
	public void setProductCurrencyTitle(String value) {
		product_currencytitle = value;
	}



	public String getProductCurrency() {
		return product_currency;
	}
	public void setProductCurrency(String value) {
		product_currency = value;
	}



	public String getProductPrice() {
		return product_price;
	}
	public void setProductPrice(String value) {
		product_price = value;
	}



	public String getProductPeriod() {
		return product_period;
	}
	public void setProductPeriod(String value) {
		product_period = value;
	}



	public String getProductWeight() {
		return product_weight;
	}
	public void setProductWeight(String value) {
		product_weight = value;
	}



	public String getProductVolume() {
		return product_volume;
	}
	public void setProductVolume(String value) {
		product_volume = value;
	}



	public String getProductWidth() {
		return product_width;
	}
	public void setProductWidth(String value) {
		product_width = value;
	}



	public String getProductHeight() {
		return product_height;
	}
	public void setProductHeight(String value) {
		product_height = value;
	}



	public String getProductDepth() {
		return product_depth;
	}
	public void setProductDepth(String value) {
		product_depth = value;
	}



	public String getProductBrand() {
		return product_brand;
	}
	public void setProductBrand(String value) {
		product_brand = value;
	}



	public String getProductColour() {
		return product_colour;
	}
	public void setProductColour(String value) {
		product_colour = value;
	}



	public String getProductSize() {
		return product_size;
	}
	public void setProductSize(String value) {
		product_size = value;
	}



	public String getProductStock() {
		return product_stock;
	}
	public void setProductStock(String value) {
		product_stock = value;
	}



	public String getProductComment() {
		return product_comment;
	}
	public void setProductComment(String value) {
		product_comment = value;
	}



	public String getProductStatus() {
		return product_status;
	}
	public void setProductStatus(String value) {
		product_status = value;
	}



	public String getProductEmail() {
		return product_email;
	}
	public void setProductEmail(String value) {
		product_email = value;
	}



	public String getProductURL() {
		return product_url;
	}
	public void setProductURL(String value) {
		product_url = value;
	}



	public String getProductDelivery() {
		return product_delivery;
	}
	public void setProductDelivery(String value) {
		product_delivery = value;
	}



	public String getProductUser() {
		return product_user;
	}
	public void setProductUser(String value) {
		product_user = value;
	}



	public String getProductPage() {
		return product_page;
	}
	public void setProductPage(String value) {
		product_page = value;
	}



	public String getProductProgram() {
		return product_program;
	}
	public void setProductProgram(String value) {
		product_program = value;
	}



	public String getProductHosting() {
		return product_hosting;
	}
	public void setProductHosting(String value) {
		product_hosting = value;
	}



	public String getProductOptions() {
		return product_options;
	}
	public void setProductOptions(String value) {
		product_options = value;
	}



	public String getProductContent() {
		return product_content;
	}
	public void setProductContent(String value) {
		product_content = value;
	}



	public String getProductContentgroup() {
		return product_contentgroup;
	}
	public void setProductContentgroup(String value) {
		product_contentgroup = value;
	}



	public String getProductContenttype() {
		return product_contenttype;
	}
	public void setProductContenttype(String value) {
		product_contenttype = value;
	}



	public String getProductImagegroup() {
		return product_imagegroup;
	}
	public void setProductImagegroup(String value) {
		product_imagegroup = value;
	}



	public String getProductImagetype() {
		return product_imagetype;
	}
	public void setProductImagetype(String value) {
		product_imagetype = value;
	}



	public String getProductFilegroup() {
		return product_filegroup;
	}
	public void setProductFilegroup(String value) {
		product_filegroup = value;
	}



	public String getProductFiletype() {
		return product_filetype;
	}
	public void setProductFiletype(String value) {
		product_filetype = value;
	}



	public String getProductLinkgroup() {
		return product_linkgroup;
	}
	public void setProductLinkgroup(String value) {
		product_linkgroup = value;
	}



	public String getProductLinktype() {
		return product_linktype;
	}
	public void setProductLinktype(String value) {
		product_linktype = value;
	}



	public String getProductProductgroup() {
		return product_productgroup;
	}
	public void setProductProductgroup(String value) {
		product_productgroup = value;
	}



	public String getProductProducttype() {
		return product_producttype;
	}
	public void setProductProducttype(String value) {
		product_producttype = value;
	}



	public String getProductUsergroup() {
		return product_usergroup;
	}
	public void setProductUsergroup(String value) {
		product_usergroup = value;
	}



	public String getProductUsertype() {
		return product_usertype;
	}
	public void setProductUsertype(String value) {
		product_usertype = value;
	}



	public String getItemQuantity() {
		return item_quantity;
	}
	public void setItemQuantity(String value) {
		item_quantity = value;
	}
	public void setItemQuantity(int value) {
		item_quantity = "" + value;
	}



	public String getItemSubtotal() {
		return item_subtotal;
	}
	public void setItemSubtotal(String value) {
		item_subtotal = value;
	}
	public void setItemSubtotal(double value) {
		item_subtotal = "" + value;
	}



	public String getItemTotal() {
		return item_total;
	}
	public void setItemTotal(String value) {
		item_total = value;
	}
	public void setItemTotal(double value) {
		item_total = "" + value;
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
		return shipping_total;
	}
	public void setShippingTotal(String value) {
		shipping_total = value;
	}
	public void setShippingTotal(double value) {
		shipping_total = "" + value;
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
	public void setTaxTotal(double value) {
		tax_total = "" + value;
	}



	public HashMap<String,String> getShopcart() {
		return shopcart;
	}
	public void setShopcart(HashMap<String,String> value) {
		shopcart = value;
	}



}
