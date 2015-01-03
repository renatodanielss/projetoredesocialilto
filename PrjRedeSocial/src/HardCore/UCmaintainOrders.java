package HardCore;

import java.text.*;
import java.util.*;
import javax.servlet.*;

public class UCmaintainOrders {
	private String error = "";
	private int record_count = 0;
	private Text text = new Text();



	public UCmaintainOrders() {
	}



	public UCmaintainOrders(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Order getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Order();
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Order();
		setSessionFilterFromRequest(mysession, myrequest);
		if (myrequest.getParameter("pagesize").equals(" ")) {
			mysession.set("admin_pagesize", "");
		} else if (! myrequest.getParameter("pagesize").equals("")) {
			mysession.set("admin_pagesize", html.encodeHtmlEntities(myrequest.getParameter("pagesize")));
		}
		String SQLselect = "select distinct orders.id as id, orders.created as created, orders.created_by as created_by, orders.updated as updated, orders.updated_by as updated_by, orders.published as published, orders.published_by as published_by, orders.paid as paid, orders.status as status, orders.status_by as status_by, orders.revision as revision, orders.checkedout as checkedout, orders.order_quantity as order_quantity, orders.order_currency as order_currency, orders.order_subtotal as order_subtotal, orders.order_total as order_total, orders.tax_description as tax_description, orders.tax_total as tax_total, orders.shipping_description as shipping_description, orders.shipping_total as shipping_total, orders.discount_description as discount_description, orders.discount_total as discount_total, orders.card_type as card_type, orders.card_number as card_number, orders.card_issuedmonth as card_issuedmonth, orders.card_issuedyear as card_issuedyear, orders.card_expirymonth as card_expirymonth, orders.card_expiryyear as card_expiryyear, orders.card_name as card_name, orders.card_cvc as card_cvc, orders.card_issue as card_issue, orders.card_postalcode as card_postalcode, orders.delivery_name as delivery_name, orders.delivery_organisation as delivery_organisation, orders.delivery_address as delivery_address, orders.delivery_postalcode as delivery_postalcode, orders.delivery_city as delivery_city, orders.delivery_state as delivery_state, orders.delivery_country as delivery_country, orders.delivery_phone as delivery_phone, orders.delivery_fax as delivery_fax, orders.delivery_email as delivery_email, orders.delivery_website as delivery_website, orders.invoice_name as invoice_name, orders.invoice_organisation as invoice_organisation, orders.invoice_address as invoice_address, orders.invoice_postalcode as invoice_postalcode, orders.invoice_city as invoice_city, orders.invoice_state as invoice_state, orders.invoice_country as invoice_country, orders.invoice_phone as invoice_phone, orders.invoice_fax as invoice_fax, orders.invoice_email as invoice_email, orders.invoice_website as invoice_website";
		String SQLfrom = " from orders left outer join orderitems on orders.id=orderitems.order_id";
		String SQLwhere = "";

		if (mysession.get("admin_contentgroup").equals("-")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("product") + " and " + db.is_blank("contentgroup") + ""));
			SQLwhere = Common.SQLwhere(SQLwhere, " product_id in (" + content_ids + ")");
		} else if (! mysession.get("admin_contentgroup").equals("")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("product") + " and contentgroup=" + db.quote(Common.SQL_clean(mysession.get("admin_contentgroup")))));
			SQLwhere = Common.SQLwhere(SQLwhere, " product_id in (" + content_ids + ")");
		}
		if (mysession.get("admin_contenttype").equals("-")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("product") + " and " + db.is_blank("contenttype") + ""));
			SQLwhere = Common.SQLwhere(SQLwhere, " product_id in (" + content_ids + ")");
		} else if (! mysession.get("admin_contenttype").equals("")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("product") + " and contenttype=" + db.quote(Common.SQL_clean(mysession.get("admin_contenttype")))));
			SQLwhere = Common.SQLwhere(SQLwhere, " product_id in (" + content_ids + ")");
		}

		if (mysession.get("admin_status").equals("closed")) {
			SQLwhere = Common.SQLwhere(SQLwhere, " " + db.is_not_blank("published"));
		} else if (mysession.get("admin_status").equals("paid")) {
			SQLwhere = Common.SQLwhere(SQLwhere, " " + db.is_not_blank("paid") + " and " + db.is_blank("published"));
		} else if (mysession.get("admin_status").equals("open")) {
			SQLwhere = Common.SQLwhere(SQLwhere, " (created<>updated) and " + db.is_blank("paid") + " and " + db.is_blank("published"));
		} else if (mysession.get("admin_status").equals("new")) {
			SQLwhere = Common.SQLwhere(SQLwhere, " (created=updated) and " + db.is_blank("paid") + " and " + db.is_blank("published"));
		} else if (mysession.get("admin_status").equals("-")) {
			SQLwhere = Common.SQLwhere(SQLwhere, " " + db.is_blank("orders.status"));
		} else if (! mysession.get("admin_status").equals("")) {
			SQLwhere = Common.SQLwhere(SQLwhere, " orders.status=" + db.quote(Common.SQL_clean(mysession.get("admin_status"))));
		}

// IF (product_nostock_order = "")
		if (mysession.get("admin_stock").equals("unlimited")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where " + Common.SQLwhere_equals(db, "-", "product_nostock_order", "")));
			SQLwhere = Common.SQLwhere(SQLwhere, " product_id in (" + content_ids + ")");
// IF (product_nostock_order <> "")
//   IF product_stock_amount > 0
//     IF product_stock_amount > product_lowstock_amount
		} else if (mysession.get("admin_stock").equals("in")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where " + Common.SQLwhere("-", "((product_stock_amount > 0) and (product_stock_amount > product_lowstock_amount) and " + Common.SQLwhere_not_equals(db, "-", "product_nostock_order", "") + ")")));
			SQLwhere = Common.SQLwhere(SQLwhere, " product_id in (" + content_ids + ")");
//     IF product_stock_amount <= product_lowstock_amount
		} else if (mysession.get("admin_stock").equals("low")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where " + Common.SQLwhere("-", "((product_stock_amount > 0) and (product_stock_amount <= product_lowstock_amount) and " + Common.SQLwhere_not_equals(db, "-", "product_nostock_order", "") + ")")));
			SQLwhere = Common.SQLwhere(SQLwhere, " product_id in (" + content_ids + ")");
//   IF product_stock_amount = 0
//     IF product_nostock_order = "no"
		} else if (mysession.get("admin_stock").equals("no")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where " + Common.SQLwhere("-", "((product_stock_amount = 0) and " + Common.SQLwhere_equals(db, "-", "product_nostock_order", "no") + ")")));
			SQLwhere = Common.SQLwhere(SQLwhere, " product_id in (" + content_ids + ")");
//     IF product_nostock_order = "yes"
		} else if (mysession.get("admin_stock").equals("orderable")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where " + Common.SQLwhere("-", "((product_stock_amount = 0) and " + Common.SQLwhere_equals(db, "-", "product_nostock_order", "yes") + ")")));
			SQLwhere = Common.SQLwhere(SQLwhere, " product_id in (" + content_ids + ")");
//   IF product_stock_amount < 0
		} else if (mysession.get("admin_stock").equals("ordered")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where " + Common.SQLwhere("-", "((product_stock_amount < 0))")));
			SQLwhere = Common.SQLwhere(SQLwhere, " product_id in (" + content_ids + ")");
		}

		String SQL = SQLselect + ", count(distinct orders.id) as rowcount" + SQLfrom + SQLwhere;
		record_count = Common.intnumber(db.query_value(SQL, "rowcount"));

		String SQLorderDir = " desc";
		if (myrequest.getParameter("sort_dir").equals("DESC")) {
			SQLorderDir = " desc";
		} else if (myrequest.getParameter("sort_dir").equals("ASC")) {
			SQLorderDir = "";
		}
		String SQLorder = "created" + SQLorderDir;
		if (myrequest.getParameter("sort_col").equals("column_title")) {
			// default;
		} else if (myrequest.getParameter("sort_col").equals("column_id")) {
			SQLorder = "id" + SQLorderDir + ", created" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_created")) {
			SQLorder = "created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_createdby")) {
			SQLorder = "created_by" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_updated")) {
			SQLorder = "updated" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_updatedby")) {
			SQLorder = "updated_by" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_paid")) {
			SQLorder = "paid" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_closed")) {
			SQLorder = "published" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_closedby")) {
			SQLorder = "published_by" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_order_quantity")) {
			SQLorder = "order_quantity" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_order_subtotal")) {
			SQLorder = "order_subtotal" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_order_total")) {
			SQLorder = "order_total" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_tax_total")) {
			SQLorder = "tax_total" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_shipping_total")) {
			SQLorder = "shipping_total" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_discount_total")) {
			SQLorder = "discount_total" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_name")) {
			SQLorder = "delivery_name" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_organisation")) {
			SQLorder = "delivery_organisation" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_address")) {
			SQLorder = "delivery_address" + SQLorderDir + ", delivery_postalcode" + SQLorderDir + ", delivery_city" + SQLorderDir + ", delivery_state" + SQLorderDir + ", delivery_country" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_postalcode")) {
			SQLorder = "delivery_postalcode" + SQLorderDir + ", delivery_city" + SQLorderDir + ", delivery_state" + SQLorderDir + ", delivery_country" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_city")) {
			SQLorder = "delivery_city" + SQLorderDir + ", delivery_state" + SQLorderDir + ", delivery_country" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_state")) {
			SQLorder = "delivery_state" + SQLorderDir + ", delivery_country" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_country")) {
			SQLorder = "delivery_country" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_phone")) {
			SQLorder = "delivery_phone" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_fax")) {
			SQLorder = "delivery_fax" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_website")) {
			SQLorder = "delivery_website" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_email")) {
			SQLorder = "delivery_email" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_name")) {
			SQLorder = "invoice_name" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_organisation")) {
			SQLorder = "invoice_organisation" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_address")) {
			SQLorder = "invoice_address" + SQLorderDir + ", invoice_postalcode" + SQLorderDir + ", invoice_city" + SQLorderDir + ", invoice_state" + SQLorderDir + ", invoice_country" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_postalcode")) {
			SQLorder = "invoice_postalcode" + SQLorderDir + ", invoice_city" + SQLorderDir + ", invoice_state" + SQLorderDir + ", invoice_country" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_city")) {
			SQLorder = "invoice_city" + SQLorderDir + ", invoice_state" + SQLorderDir + ", invoice_country" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_state")) {
			SQLorder = "invoice_state" + SQLorderDir + ", invoice_country" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_country")) {
			SQLorder = "invoice_country" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_phone")) {
			SQLorder = "invoice_phone" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_fax")) {
			SQLorder = "invoice_fax" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_website")) {
			SQLorder = "invoice_website" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_email")) {
			SQLorder = "invoice_email" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_type")) {
			SQLorder = "card_type" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_number")) {
			SQLorder = "card_number" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_issuedmonth")) {
			SQLorder = "card_issuedmonth" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_issuedyear")) {
			SQLorder = "card_issuedyear" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_issued")) {
			SQLorder = "card_issuedyear" + SQLorderDir + ", card_issuedmonth" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_expirymonth")) {
			SQLorder = "card_expirymonth" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_expiryyear")) {
			SQLorder = "card_expiryyear" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_expiry")) {
			SQLorder = "card_expiryyear" + SQLorderDir + ", card_expirymonth" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_name")) {
			SQLorder = "card_name" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_cvc")) {
			SQLorder = "card_cvc" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_issue")) {
			SQLorder = "card_issue" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_postalcode")) {
			SQLorder = "card_postalcode" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		}

		Order order = new Order();
		order.records(db, SQLselect + SQLfrom + SQLwhere + " order by " + SQLorder);
		return order;
	}



	public Order getDeleteMultiple(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		if (db == null) return new Order();
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Order();
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Order();
		String session_administrator = mysession.get("administrator");
		String session_userid = mysession.get("userid");
		String session_username = mysession.get("username");
		String session_usertype = mysession.get("usertype");
		String session_usergroup = mysession.get("usergroup");
		String session_usertypes = mysession.get("usertypes");
		String session_usergroups = mysession.get("usergroups");
		setSessionFilterFromRequest(mysession, myrequest);

		String SQLselect = "select distinct orders.id as id, orders.created as created, orders.created_by as created_by, orders.updated as updated, orders.updated_by as updated_by, orders.published as published, orders.published_by as published_by, orders.paid as paid, orders.status as status, orders.status_by as status_by, orders.revision as revision, orders.checkedout as checkedout, orders.order_quantity as order_quantity, orders.order_currency as order_currency, orders.order_subtotal as order_subtotal, orders.order_total as order_total, orders.tax_description as tax_description, orders.tax_total as tax_total, orders.shipping_description as shipping_description, orders.shipping_total as shipping_total, orders.discount_description as discount_description, orders.discount_total as discount_total, orders.card_type as card_type, orders.card_number as card_number, orders.card_issuedmonth as card_issuedmonth, orders.card_issuedyear as card_issuedyear, orders.card_expirymonth as card_expirymonth, orders.card_expiryyear as card_expiryyear, orders.card_name as card_name, orders.card_cvc as card_cvc, orders.card_issue as card_issue, orders.card_postalcode as card_postalcode, orders.delivery_name as delivery_name, orders.delivery_organisation as delivery_organisation, orders.delivery_address as delivery_address, orders.delivery_postalcode as delivery_postalcode, orders.delivery_city as delivery_city, orders.delivery_state as delivery_state, orders.delivery_country as delivery_country, orders.delivery_phone as delivery_phone, orders.delivery_fax as delivery_fax, orders.delivery_email as delivery_email, orders.delivery_website as delivery_website, orders.invoice_name as invoice_name, orders.invoice_organisation as invoice_organisation, orders.invoice_address as invoice_address, orders.invoice_postalcode as invoice_postalcode, orders.invoice_city as invoice_city, orders.invoice_state as invoice_state, orders.invoice_country as invoice_country, orders.invoice_phone as invoice_phone, orders.invoice_fax as invoice_fax, orders.invoice_email as invoice_email, orders.invoice_website as invoice_website";
		String SQLfrom = " from orders left outer join orderitems on orders.id=orderitems.order_id";
		String SQLwhere = "";
		String SQL = "";
		String SQLwhere_in = "";
		String[] ids = myrequest.getParameters("id");
		if (ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				if (! SQLwhere_in.equals("")) {
					SQLwhere_in += ",";
				}
				SQLwhere_in += id;
			}
			SQLwhere += " where orders.id in (" + SQLwhere_in + ")";
		} else {
			SQLwhere += " where orders.id in (0)";
		}
		String SQLorderDir = " desc";
		String SQLorder = "created" + SQLorderDir;

		Order order = new Order();
		order.records(db, SQLselect + SQLfrom + SQLwhere + " order by " + SQLorder);
		return order;
	}



	public Order getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Order();
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Order();
		Order order = new Order();
		order.read(db, myrequest.getParameter("id"));
		return order;
	}



	public Order getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Order();
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Order();
		Order order = new Order();
		order.read(db, myrequest.getParameter("id"));
		if (order.getOrderCurrency().equals("")) {
			Currency mycurrency = new Currency();
			mycurrency.read(db, myconfig.get(db, "default_currency"));
			order.setOrderCurrency(mycurrency.getSymbol());
		}
		return order;
	}



	public Order getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Order();
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Order();
		Order order = new Order();
		order.read(db, myrequest.getParameter("id"));
		return order;
	}



	public Order getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Order();
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Order();
		Order order = new Order();
		order.read(db, myrequest.getParameter("id"));
		return order;
	}



	public Order doSearch(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		return doSearch(mysession, myrequest, myresponse, myconfig, db, true, true);
	}
	public Order doSearch(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, boolean edits, boolean creates) {
		if (db == null) return new Order();
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Order();
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Order();
		error = "";
		setSessionFilterFromRequest(mysession, myrequest);
		mysession.set("admin_contentclass", "");
		mysession.set("admin_contentbundle", "");
		mysession.set("admin_contentgroup", "");
		mysession.set("admin_contenttype", "");
		mysession.set("admin_version", "");
		if (myrequest.getRequestURI().indexOf("search.jsp") > 0) {
			mysession.set("search_section", "");
			mysession.set("search_bundle", "");
			mysession.set("search_group", "");
			mysession.set("search_type", "");
			mysession.set("search_version", "");
			mysession.set("search_package", "");
			mysession.set("search_status", "");
			mysession.set("search_filename", "");
			mysession.set("search_attribute", "");
			mysession.set("search_id", "");
		}
		if (myrequest.getRequestURI().indexOf("searchadvanced.jsp") > 0) {
			mysession.set("search_attribute", "");
		}
		if (! myrequest.getParameter("search").equals("")) {
			mysession.set("search", myrequest.getParameter("search"));
		} else if (myrequest.parameterExists("search")) {
			mysession.set("search", "");
		}
		if ((! myrequest.getParameter("section").equals(" ")) && (! myrequest.getParameter("section").equals(""))) {
			mysession.set("search_section", html.encodeHtmlEntities(myrequest.getParameter("section")));
		} else if (myrequest.getParameter("section").equals(" ")) {
			mysession.set("search_section", "");
		}
		if (! myrequest.getParameter("bundle").equals("")) {
			mysession.set("search_bundle", html.encodeHtmlEntities(myrequest.getParameter("bundle")));
		} else if (myrequest.parameterExists("bundle")) {
			mysession.set("search_bundle", "");
		}
		if (! myrequest.getParameter("group").equals("")) {
			mysession.set("search_group", html.encodeHtmlEntities(myrequest.getParameter("group")));
		} else if (myrequest.parameterExists("group")) {
			mysession.set("search_group", "");
		}
		if (! myrequest.getParameter("type").equals("")) {
			mysession.set("search_type", html.encodeHtmlEntities(myrequest.getParameter("type")));
		} else if (myrequest.parameterExists("type")) {
			mysession.set("search_type", "");
		}
		if (! myrequest.getParameter("version").equals("")) {
			mysession.set("search_version", html.encodeHtmlEntities(myrequest.getParameter("version")));
		} else if (myrequest.parameterExists("version")) {
			mysession.set("search_version", "");
		}
		if (! myrequest.getParameter("package").equals("")) {
			mysession.set("search_package", html.encodeHtmlEntities(myrequest.getParameter("package")));
		} else if (myrequest.parameterExists("version")) {
			mysession.set("search_package", "");
		}
		if (! myrequest.getParameter("status").equals("")) {
			mysession.set("search_status", html.encodeHtmlEntities(myrequest.getParameter("status")));
		} else if (myrequest.parameterExists("status")) {
			mysession.set("search_status", "");
		}
		if (! myrequest.getParameter("filename").equals("")) {
			mysession.set("search_filename", html.encodeHtmlEntities(myrequest.getParameter("filename")));
		} else if (myrequest.parameterExists("filename")) {
			mysession.set("search_filename", "");
		}
		if (! myrequest.getParameter("attribute").equals("")) {
			mysession.set("search_attribute", html.encodeHtmlEntities(Common.join(",", myrequest.getParameters("attribute"))));
		} else if (myrequest.parameterExists("attribute")) {
			mysession.set("search_attribute", "");
		}
		if (myrequest.getParameter("id").equals("data_grid_header")) {
			// ignore
		} else if (! myrequest.getParameter("id").equals("")) {
			mysession.set("search_id", html.encodeHtmlEntities(myrequest.getParameter("id")));
		} else if (myrequest.parameterExists("id")) {
			mysession.set("search_id", "");
		}
		if (myrequest.getParameter("pagesize").equals(" ")) {
			mysession.set("admin_pagesize", "");
		} else if (! myrequest.getParameter("pagesize").equals("")) {
			mysession.set("admin_pagesize", html.encodeHtmlEntities(myrequest.getParameter("pagesize")));
		}
		String SQLselect = "select distinct orders.id as id, orders.created as created, orders.created_by as created_by, orders.updated as updated, orders.updated_by as updated_by, orders.published as published, orders.published_by as published_by, orders.paid as paid, orders.status as status, orders.status_by as status_by, orders.revision as revision, orders.checkedout as checkedout, orders.order_quantity as order_quantity, orders.order_currency as order_currency, orders.order_subtotal as order_subtotal, orders.order_total as order_total, orders.tax_description as tax_description, orders.tax_total as tax_total, orders.shipping_description as shipping_description, orders.shipping_total as shipping_total, orders.discount_description as discount_description, orders.discount_total as discount_total, orders.card_type as card_type, orders.card_number as card_number, orders.card_issuedmonth as card_issuedmonth, orders.card_issuedyear as card_issuedyear, orders.card_expirymonth as card_expirymonth, orders.card_expiryyear as card_expiryyear, orders.card_name as card_name, orders.card_cvc as card_cvc, orders.card_issue as card_issue, orders.card_postalcode as card_postalcode, orders.delivery_name as delivery_name, orders.delivery_organisation as delivery_organisation, orders.delivery_address as delivery_address, orders.delivery_postalcode as delivery_postalcode, orders.delivery_city as delivery_city, orders.delivery_state as delivery_state, orders.delivery_country as delivery_country, orders.delivery_phone as delivery_phone, orders.delivery_fax as delivery_fax, orders.delivery_email as delivery_email, orders.delivery_website as delivery_website, orders.invoice_name as invoice_name, orders.invoice_organisation as invoice_organisation, orders.invoice_address as invoice_address, orders.invoice_postalcode as invoice_postalcode, orders.invoice_city as invoice_city, orders.invoice_state as invoice_state, orders.invoice_country as invoice_country, orders.invoice_phone as invoice_phone, orders.invoice_fax as invoice_fax, orders.invoice_email as invoice_email, orders.invoice_website as invoice_website";
		String SQLfrom = " from orders left outer join orderitems on orders.id=orderitems.order_id";
		String SQLwhere = "where (1=1)";
		if (mysession.get("search_group").equals("-")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("product") + " and " + db.is_blank("contentgroup") + ""));
			SQLwhere = Common.SQLwhere(SQLwhere, " product_id in (" + content_ids + ")");
		} else if (! mysession.get("search_group").equals("")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("product") + " and contentgroup='" + mysession.get("search_group") + "'"));
			SQLwhere = Common.SQLwhere(SQLwhere, " product_id in (" + content_ids + ")");
		}
		if (mysession.get("search_type").equals("-")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("product") + " and " + db.is_blank("contenttype") + ""));
			SQLwhere = Common.SQLwhere(SQLwhere, " product_id in (" + content_ids + ")");
		} else if (! mysession.get("search_type").equals("")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("product") + " and contenttype='" + mysession.get("search_type") + "'"));
			SQLwhere = Common.SQLwhere(SQLwhere, " product_id in (" + content_ids + ")");
		}
		if (! mysession.get("search_filename").equals("")) {
			SQLwhere = Common.SQLwhere_contains(db, myconfig, SQLwhere, "server_filename", mysession.get("search_filename"));
			SQLwhere = SQLwhere.replaceAll("%//", "").replaceAll("//%", "").replaceAll("%\\^", "").replaceAll("\\$%", "");
		}
		if (! mysession.get("search_id").equals("")) {
			SQLwhere = Common.SQLwhere_in(SQLwhere, "id", mysession.get("search_id"));
		}
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		if (mysession.get("search_status").equals("closed")) {
			SQLwhere = Common.SQLwhere(SQLwhere, " " + db.is_not_blank("published"));
		} else if (mysession.get("search_status").equals("paid")) {
			SQLwhere = Common.SQLwhere(SQLwhere, " " + db.is_not_blank("paid") + " and " + db.is_blank("published"));
		} else if (mysession.get("search_status").equals("open")) {
			SQLwhere = Common.SQLwhere(SQLwhere, " (created<>updated) and " + db.is_blank("paid") + " and " + db.is_blank("published"));
		} else if (mysession.get("search_status").equals("new")) {
			SQLwhere = Common.SQLwhere(SQLwhere, " (created=updated) and " + db.is_blank("paid") + " and " + db.is_blank("published"));
		} else if (mysession.get("search_status").equals("checkedout")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "(" + db.is_not_blank("checkedout") + ")");
		} else if (mysession.get("search_status").equals("workflow")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "(" + db.is_not_blank("status") + ")");
		} else if (! mysession.get("search_status").equals("")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "status", mysession.get("search_status"));
		}

// IF (product_nostock_order = "")
		if (mysession.get("search_stock").equals("unlimited")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where " + Common.SQLwhere_equals(db, "-", "product_nostock_order", "")));
			SQLwhere = Common.SQLwhere(SQLwhere, " product_id in (" + content_ids + ")");
// IF (product_nostock_order <> "")
//   IF product_stock_amount > 0
//     IF product_stock_amount > product_lowstock_amount
		} else if (mysession.get("search_stock").equals("in")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where " + Common.SQLwhere("-", "((product_stock_amount > 0) and (product_stock_amount > product_lowstock_amount) and " + Common.SQLwhere_not_equals(db, "-", "product_nostock_order", "") + ")")));
			SQLwhere = Common.SQLwhere(SQLwhere, " product_id in (" + content_ids + ")");
//     IF product_stock_amount <= product_lowstock_amount
		} else if (mysession.get("search_stock").equals("low")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where " + Common.SQLwhere("-", "((product_stock_amount > 0) and (product_stock_amount <= product_lowstock_amount) and " + Common.SQLwhere_not_equals(db, "-", "product_nostock_order", "") + ")")));
			SQLwhere = Common.SQLwhere(SQLwhere, " product_id in (" + content_ids + ")");
//   IF product_stock_amount = 0
//     IF product_nostock_order = "no"
		} else if (mysession.get("search_stock").equals("no")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where " + Common.SQLwhere("-", "((product_stock_amount = 0) and " + Common.SQLwhere_equals(db, "-", "product_nostock_order", "no") + ")")));
			SQLwhere = Common.SQLwhere(SQLwhere, " product_id in (" + content_ids + ")");
//     IF product_nostock_order = "yes"
		} else if (mysession.get("search_stock").equals("orderable")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where " + Common.SQLwhere("-", "((product_stock_amount = 0) and " + Common.SQLwhere_equals(db, "-", "product_nostock_order", "yes") + ")")));
			SQLwhere = Common.SQLwhere(SQLwhere, " product_id in (" + content_ids + ")");
//   IF product_stock_amount < 0
		} else if (mysession.get("search_stock").equals("ordered")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where " + Common.SQLwhere("-", "((product_stock_amount < 0))")));
			SQLwhere = Common.SQLwhere(SQLwhere, " product_id in (" + content_ids + ")");
		}

		if (! mysession.get("search").equals("")) {
			String wildcard = "_";
			if (db.db_type(db.getDatabase()) == "access") {
				wildcard = "?";
			}
//			String[] searchwords = mysession.get("search").replaceAll("'", wildcard).replaceAll("\"", wildcard).split(" ");
			String[] searchwords = mysession.get("search").replaceAll("'", "''").split(" ");
			for (int i=0; i<searchwords.length; i++) {
				String searchword = searchwords[i];
				if (! mysession.get("search_attribute").equals("")) {
					SQLwhere += " AND (";
					String[] myattributes = mysession.get("search_attribute").split(",");
					for (int j=0; j<myattributes.length; j++) {
						if (j>0) SQLwhere += " OR ";
						SQLwhere += SQL_search(myattributes[j], searchword, db);
					}
					SQLwhere += " )";
				} else {
					SQLwhere += " AND (";
					SQLwhere += SQL_search("orders.created", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.created_by", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.updated", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.updated_by", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.published", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.published_by", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.revision", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.status", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.status_by", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.checkedout", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.paid", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.tax_description", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.shipping_description", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.discount_description", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.card_type", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.card_number", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.card_issuedmonth", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.card_issuedyear", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.card_expirymonth", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.card_expiryyear", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.card_name", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.card_cvc", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.card_issue", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.card_postalcode", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.delivery_name", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.delivery_organisation", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.delivery_address", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.delivery_postalcode", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.delivery_city", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.delivery_state", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.delivery_country", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.delivery_phone", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.delivery_fax", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.delivery_email", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.delivery_website", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.invoice_name", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.invoice_organisation", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.invoice_address", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.invoice_postalcode", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.invoice_city", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.invoice_state", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.invoice_country", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.invoice_phone", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.invoice_fax", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.invoice_email", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.invoice_website", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.order_quantity", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.order_subtotal", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.order_subtotal_base", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.tax_total", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.tax_total_base", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.shipping_total", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.shipping_total_base", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.discount_total", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.discount_total_base", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.order_total", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orders.order_total_base", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.title", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.author", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.keywords", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.description", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.summary", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.product_price", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.product_weight", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.product_volume", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.product_width", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.product_height", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.product_depth", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.product_comment", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.product_options", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.product_contentgroup", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.product_contenttype", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.product_imagegroup", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.product_imagetype", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.product_filegroup", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.product_filetype", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.product_linkgroup", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.product_linktype", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.product_productgroup", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.product_producttype", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.product_usergroup", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.product_usertype", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.item_quantity", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.item_total", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.item_total_base", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.discount_description", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.discount_total", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.discount_total_base", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.shipping_description", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.shipping_total", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.shipping_total_base", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.tax_description", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.tax_total", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("orderitems.tax_total_base", searchword, db);
					if (searchword.matches("^[0-9]+$")) {
						SQLwhere += " OR (orders.id=" + searchword + ")";
					}
					SQLwhere += " )";
				}
			}
		}

		String SQL = SQLselect + ", count(*) as rowcount" + SQLfrom + " " + SQLwhere;
		record_count = Common.intnumber(db.query_value(SQL, "rowcount"));

		String SQLorderDir = "";
		if (myrequest.getParameter("sort_dir").equals("DESC")) {
			SQLorderDir = " desc";
		}
		String SQLorder = "created" + SQLorderDir;
		if (myrequest.getParameter("sort_col").equals("column_title")) {
			// default;
		} else if (myrequest.getParameter("sort_col").equals("column_id")) {
			SQLorder = "id" + SQLorderDir + ", created" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_created")) {
			SQLorder = "created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_createdby")) {
			SQLorder = "created_by" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_updated")) {
			SQLorder = "updated" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_updatedby")) {
			SQLorder = "updated_by" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_paid")) {
			SQLorder = "paid" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_closed")) {
			SQLorder = "published" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_closedby")) {
			SQLorder = "published_by" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_order_quantity")) {
			SQLorder = "order_quantity" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_order_subtotal")) {
			SQLorder = "order_subtotal" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_order_total")) {
			SQLorder = "order_total" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_tax_total")) {
			SQLorder = "tax_total" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_shipping_total")) {
			SQLorder = "shipping_total" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_discount_total")) {
			SQLorder = "discount_total" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_name")) {
			SQLorder = "delivery_name" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_organisation")) {
			SQLorder = "delivery_organisation" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_address")) {
			SQLorder = "delivery_address" + SQLorderDir + ", delivery_postalcode" + SQLorderDir + ", delivery_city" + SQLorderDir + ", delivery_state" + SQLorderDir + ", delivery_country" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_postalcode")) {
			SQLorder = "delivery_postalcode" + SQLorderDir + ", delivery_city" + SQLorderDir + ", delivery_state" + SQLorderDir + ", delivery_country" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_city")) {
			SQLorder = "delivery_city" + SQLorderDir + ", delivery_state" + SQLorderDir + ", delivery_country" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_state")) {
			SQLorder = "delivery_state" + SQLorderDir + ", delivery_country" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_country")) {
			SQLorder = "delivery_country" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_phone")) {
			SQLorder = "delivery_phone" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_fax")) {
			SQLorder = "delivery_fax" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_website")) {
			SQLorder = "delivery_website" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_delivery_email")) {
			SQLorder = "delivery_email" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_name")) {
			SQLorder = "invoice_name" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_organisation")) {
			SQLorder = "invoice_organisation" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_address")) {
			SQLorder = "invoice_address" + SQLorderDir + ", invoice_postalcode" + SQLorderDir + ", invoice_city" + SQLorderDir + ", invoice_state" + SQLorderDir + ", invoice_country" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_postalcode")) {
			SQLorder = "invoice_postalcode" + SQLorderDir + ", invoice_city" + SQLorderDir + ", invoice_state" + SQLorderDir + ", invoice_country" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_city")) {
			SQLorder = "invoice_city" + SQLorderDir + ", invoice_state" + SQLorderDir + ", invoice_country" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_state")) {
			SQLorder = "invoice_state" + SQLorderDir + ", invoice_country" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_country")) {
			SQLorder = "invoice_country" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_phone")) {
			SQLorder = "invoice_phone" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_fax")) {
			SQLorder = "invoice_fax" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_website")) {
			SQLorder = "invoice_website" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_invoice_email")) {
			SQLorder = "invoice_email" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_type")) {
			SQLorder = "card_type" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_number")) {
			SQLorder = "card_number" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_issuedmonth")) {
			SQLorder = "card_issuedmonth" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_issuedyear")) {
			SQLorder = "card_issuedyear" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_issued")) {
			SQLorder = "card_issuedyear" + SQLorderDir + ", card_issuedmonth" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_expirymonth")) {
			SQLorder = "card_expirymonth" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_expiryyear")) {
			SQLorder = "card_expiryyear" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_expiry")) {
			SQLorder = "card_expiryyear" + SQLorderDir + ", card_expirymonth" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_name")) {
			SQLorder = "card_name" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_cvc")) {
			SQLorder = "card_cvc" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_issue")) {
			SQLorder = "card_issue" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_card_postalcode")) {
			SQLorder = "card_postalcode" + SQLorderDir + ", created" + SQLorderDir + ", id" + SQLorderDir;
		}

		Order order = new Order();
		String offset = html.encodeHtmlEntities(myrequest.getParameter("offset"));
		String limit = html.encodeHtmlEntities(myrequest.getParameter("page_size"));
		if ((! offset.equals("")) && (! limit.equals(""))) {
			if (SQLwhere.length() < 6) SQLwhere += "      ";
			SQL = Common.SQLlimit(db.db_type(db.getDatabase()), "orders.id", SQLselect.substring(7), SQLfrom.substring(5), SQLwhere.substring(6), SQLorder, (! SQLorderDir.equals("")), Common.intnumber(offset), Common.intnumber(limit));
		} else if ((mysession.get("admin_pagesize").equals("")) && (myrequest.getRequestURI().indexOf("searchreplace.jsp") < 0)) {
			SQL = SQLselect + SQLfrom + " where 1=0";
		} else {
			SQL = SQLselect + SQLfrom + " " + SQLwhere + " order by " + SQLorder;
		}
		order.records(db, SQL);
		return order;
	}



	private String SQL_search(String column, String value, DB db) {
		String SQLexpression = "";
//		SQLexpression = "lower(" + column + ") like '%" + value.toLowerCase() + "%'";
		if (db.db_type(db.getDatabase()).equals("mssql")) {
			SQLexpression += "(";
			SQLexpression += "(lower(substring(" + column + ",1,datalength(" + column + "))) like '%" + value.toLowerCase() + "%')";
			SQLexpression += " or ";
			SQLexpression += "(lower(substring(" + column + ",1,datalength(" + column + "))) like '%" + html.decodeHtmlEntities(value).toLowerCase() + "%')";
			SQLexpression += ")";
		} else {
			SQLexpression += "(";
			SQLexpression += "(lower(" + column + ") like '%" + value.toLowerCase() + "%')";
			SQLexpression += " or ";
			SQLexpression += "(lower(" + column + ") like '%" + html.decodeHtmlEntities(value).toLowerCase() + "%')";
			SQLexpression += ")";
		}
		//SQLexpression = ""
		//SQLexpression += column + " like '" & value & "'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '% " & value & "'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '%\>" & value & "'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '" & value & " %'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '% " & value & " %'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '%\>" & value & " %'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '" & value & ",%'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '% " & value & ",%'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '%\>" & value & ",%'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '" & value & ".%'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '% " & value & ".%'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '%\>" & value & ".%'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '" & value & "<%'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '% " & value & "<%'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '%\>" & value & "<%'"
		return SQLexpression;
	}



	public Order doCheckin(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return new Order();
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Order();
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Order();
		Order order = new Order();
		order.read(db, myrequest.getParameter("id"));
		order.checkin(db, mysession.get("username"), myconfig.get(db, "superadmin"));
		return order;
	}



	public Order doCheckout(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return new Order();
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Order();
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Order();
		Order order = new Order();
		order.read(db, myrequest.getParameter("id"));
		Workflow workflow = new Workflow(text);
		if ((myconfig.get(db, "use_workflow_orders").equals("yes")) && (! workflow.permissions(db, order.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), "-order-", "", "", "", ((order.getStatusBy().equals(mysession.get("username"))) || (order.getStatusBy().equals(""))))) && (! myconfig.get(db, "superadmin").equals(mysession.get("username")))) {
			// no workflow permission
		} else if ((! order.getCheckedOut().equals("")) && (! order.getCheckedOut().equals(mysession.get("username"))) && (! myconfig.get(db, "superadmin").equals(mysession.get("username")))) {
			// checked out by another administrator
		} else if (order.getCheckoutPermissions(mysession.get("username"), db, myconfig)) {
			order.checkout(db, mysession.get("username"));
		}
		return order;
	}



	public Order doCreate(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Order();
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Order();
		error = "";
		Order order = new Order();
		order.read(db, myrequest.getParameter("id"));
		Workflow workflow = new Workflow(text);
		if (! myrequest.getParameter("status").equals("")) {
			workflow.read(db, myrequest.getParameter("status"));
		} else {
//			workflow.setFromState(order.getStatus());
			workflow.setToState(order.getStatus());
		}
		String old_status = "";
		String old_status_by = "";
		String old_revision = "";
		order.getForm(myrequest);
		order.setStatus(workflow.getToState());
		String username = mysession.get("username");
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		order.setCreated(now);
		order.setCreatedBy(username);
		order.setUpdated(now);
		order.setUpdatedBy(username);
		if (myrequest.getParameter("state").equals("open")) {
			order.setPublished("");
			order.setPublishedBy("");
		} else if (myrequest.getParameter("state").equals("close")) {
			order.setPublished(now);
			order.setPublishedBy(username);
		}
		if (! order.getStatus().equals(old_status)) {
			order.setStatusBy(username);
		}
		if (((! myconfig.get(db, "use_workflow_orders").equals("yes")) || (workflow.permission(db, old_status, order.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), "-order-", "", "", "", ((old_status_by.equals(mysession.get("username"))) || (old_status_by.equals(""))))) || (myconfig.get(db, "superadmin").equals(mysession.get("username"))))) {
			order.calculate(db, myconfig);
			order.create(db);

			String SQL = "select * from orderitems where order_id=" + Common.intnumber(myrequest.getParameter("id"));
			String order_discount_description = "";
			String order_shipping_description = "";
			String order_tax_description = "";
			Orderitem orderitem = new Orderitem();
			orderitem.records(db, SQL);
			while (orderitem.records(db, "")) {
				if (! orderitem.getDiscountDescription().equals("")) {
					if (! order_discount_description.equals("")) order_discount_description += "\r\n";
					order_discount_description += orderitem.getDiscountDescription();
				}
				if (! orderitem.getShippingDescription().equals("")) {
					if (! order_shipping_description.equals("")) order_shipping_description += "\r\n";
					order_shipping_description += orderitem.getShippingDescription();
				}
				if (! orderitem.getTaxDescription().equals("")) {
					if (! order_tax_description.equals("")) order_tax_description += "\r\n";
					order_tax_description += orderitem.getTaxDescription();
				}
				orderitem.setOrderId("" + order.getId());
				orderitem.create(db);
			}
			order_discount_description = order_discount_description.replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n+", "\n").replaceAll("^\n+", "\n").replaceAll("\n*$", "\n").replaceAll("^\n*$", "").replaceAll("\n", "\r\n");
			order_shipping_description = order_shipping_description.replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n+", "\n").replaceAll("^\n+", "\n").replaceAll("\n*$", "\n").replaceAll("^\n*$", "").replaceAll("\n", "\r\n");
			order_tax_description = order_tax_description.replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n+", "\n").replaceAll("^\n+", "\n").replaceAll("\n*$", "\n").replaceAll("^\n*$", "").replaceAll("\n", "\r\n");
			order.setDiscountDescription(order_discount_description);
			order.setShippingDescription(order_shipping_description);
			order.setTaxDescription(order_tax_description);
			order.update(db);
			handleWorkflowAuto(server, order, workflow, myrequest, myresponse, mysession, myconfig, db, username, now);
			handleEmailNotification(server, order, workflow, username, myrequest, myresponse, mysession, myconfig, db);
		} else {
			error += text.display("orders.create.nopermission");
		}
		return order;
	}



	public Order doUpdate(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Order();
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Order();
		error = "";
		Order order = new Order();
		order.read(db, myrequest.getParameter("id"));
		Workflow workflow = new Workflow(text);
		if (! myrequest.getParameter("status").equals("")) {
			workflow.read(db, myrequest.getParameter("status"));
		} else {
			workflow.setFromState(order.getStatus());
			workflow.setToState(order.getStatus());
		}
		String checkedout = order.getCheckedOut();
		String old_published = order.getPublished();
		String old_published_by = order.getPublishedBy();
		String old_status = order.getStatus();
		String old_status_by = order.getStatusBy();
		String old_revision = order.getRevision();
		order.getForm(myrequest);
		order.setStatus(workflow.getToState());
		String username = mysession.get("username");
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		order.setUpdated(now);
		order.setUpdatedBy(username);
		if (myrequest.getParameter("state").equals("open")) {
			order.setPublished("");
			order.setPublishedBy("");
		} else if (myrequest.getParameter("state").equals("close")) {
			order.setPublished(now);
			order.setPublishedBy(username);
		}
		if (! order.getStatus().equals(old_status)) {
			order.setStatusBy(username);
		}
		if (! order.getPublished().equals(old_published)) {
			order.setStatusBy(username);
		}
		if ((! checkedout.equals("")) && (! checkedout.equals(mysession.get("username"))) && (! myconfig.get(db, "superadmin").equals(mysession.get("username")))) {
			error = text.display("orders.update.nopermission.checkedout") + checkedout;
		} else if (((! myconfig.get(db, "use_workflow_orders").equals("yes")) || (workflow.permission(db, old_status, order.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), "-order-", "", "", "", ((old_status_by.equals(mysession.get("username"))) || (old_status_by.equals(""))))) || (myconfig.get(db, "superadmin").equals(mysession.get("username"))))) {
//			if (myconfig.get(db, "use_workflow").equals("yes")) {
//				workflow.update_order(order);
//			}
			order.calculate(db, myconfig);
			order.update(db);
			handleWorkflowAuto(server, order, workflow, myrequest, myresponse, mysession, myconfig, db, username, now);
			handleEmailNotification(server, order, workflow, username, myrequest, myresponse, mysession, myconfig, db);
		} else {
			error += text.display("orders.update.nopermission");
		}
		return order;
	}



	public Order doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Order();
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Order();
		error = "";
		Order order = new Order();
		order.read(db, myrequest.getParameter("id"));
		String old_status_by = order.getStatusBy();
		Workflow workflow = new Workflow(text);
		if ((! order.getCheckedOut().equals("")) && (! order.getCheckedOut().equals(mysession.get("username"))) && (! myconfig.get(db, "superadmin").equals(mysession.get("username")))) {
			error = text.display("orders.delete.nopermission.checkedout") + order.getCheckedOut();
		} else if (((! myconfig.get(db, "use_workflow_orders").equals("yes")) || (workflow.permissionEnd(db, order.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), "-order-", "", "", "", ((old_status_by.equals(mysession.get("username"))) || (old_status_by.equals(""))))) || (myconfig.get(db, "superadmin").equals(mysession.get("username"))))) {
			order.delete(db);
		} else {
			error += text.display("orders.delete.nopermission");
		}
		return order;
	}



	public void doDeleteMultiple(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return;
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return;
		error = "";
		String[] ids = myrequest.getParameters("id");
		if (ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				Order order = new Order();
				order.read(db, id);
				String old_status_by = order.getStatusBy();
				Workflow workflow = new Workflow(text);
				if ((! order.getCheckedOut().equals("")) && (! order.getCheckedOut().equals(mysession.get("username"))) && (! myconfig.get(db, "superadmin").equals(mysession.get("username")))) {
					error += "<br>" + text.display("error.content.delete.checkedout") + " - " + order.getCheckedOut() + " - " + order.getDeliveryEmail();
				} else {
					if (((! myconfig.get(db, "use_workflow_orders").equals("yes")) || (myconfig.get(db, "superadmin").equals(mysession.get("username"))) || (workflow.permissionEnd(db, order.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), "-order-", "", "", "", ((old_status_by.equals(mysession.get("username"))) || (old_status_by.equals(""))))))) {
						Cms.CMSAudit("action=delete order=" + order.getDeliveryEmail() + " [" + order.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
						order.delete(db);
					} else {
						error += "<br>" + text.display("orders.delete.nopermission") + " - " + order.getCheckedOut() + " - " + order.getDeliveryEmail();
					}
				}
			}
		}
	}



	public void doCheckoutMultiple(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return;
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return;
		error = "";
		String[] ids = myrequest.getParameters("id");
		if (ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				Order order = new Order();
				order.read(db, id);
				Workflow workflow = new Workflow(text);
				if (((! myconfig.get(db, "use_workflow_orders").equals("yes")) || (myconfig.get(db, "superadmin").equals(mysession.get("username"))) || (workflow.permissions(db, order.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), "-order-", "", "", "", ((order.getStatusBy().equals(mysession.get("username"))) || (order.getStatusBy().equals(""))))))) {
					order.checkout(db, mysession.get("username"));
				}
			}
		}
	}



	public void doCheckinMultiple(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return;
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return;
		error = "";
		String[] ids = myrequest.getParameters("id");
		if (ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				Order order = new Order();
				order.read(db, id);
				order.checkin(db, mysession.get("username"), myconfig.get(db, "superadmin"));
			}
		}
	}



	public void doOpenMultiple(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return;
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return;
		error = "";
		String[] ids = myrequest.getParameters("id");

		if (ids.length > 0) {
			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			String username = mysession.get("username");
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				boolean updated = false;

				// Read chosen workflow action (if any)

				Order order = new Order();
				order.read(db, id);
				Workflow workflow = new Workflow(text);
				if (! myrequest.getParameter("status").equals("")) {
					workflow.read(db, myrequest.getParameter("status"));
				} else {
					workflow.setToState(order.getStatus());
				}

				String old_status = order.getStatus();
				String old_status_by = order.getStatusBy();

				// Fail if order checkedout and without administrator permissions to override

				if ((! order.getCheckedOut().equals("")) && (! order.getCheckedOut().equals(mysession.get("username"))) && (! myconfig.get(db, "superadmin").equals(mysession.get("username")))) {
					error = text.display("error.content.update.checkedout") + order.getCheckedOut();
				} else {
					// Handle workflow action and get posted data for users with update permissions

					if ((! myrequest.getParameter("status").equals("")) && (! id.equals("")) && (myconfig.get(db, "use_workflow_orders").equals("yes")) && ((myconfig.get(db, "superadmin").equals(mysession.get("username"))) || (workflow.permission(db, order.getStatus(), workflow.getToState(), mysession.get("usergroups"), mysession.get("usertypes"), "-order-", "", "", "", ((old_status_by.equals(mysession.get("username"))) || (old_status_by.equals(""))))))) {
						order.setStatus(workflow.getToState());
						updated = true;
					}

					// Clear status by if status cleared

					if (order.getStatus().equals("")) {
						order.setStatusBy("");
					} else if (! order.getStatus().equals(old_status)) {
						order.setStatusBy(username);
					}

					if ((! id.equals("")) && ((! myconfig.get(db, "use_workflow_orders").equals("yes")) || (myconfig.get(db, "superadmin").equals(mysession.get("username"))) || (workflow.permission(db, old_status, order.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), "-order-", "", "", "", ((old_status_by.equals(mysession.get("username"))) || (old_status_by.equals(""))))))) {

						order.setUpdated(timestamp);
						order.setUpdatedBy(username);
						order.setPublished("");
						order.setPublishedBy("");
						updated = true;

						workflow.permission(db, old_status, order.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), "-order-", "", "", "", ((old_status_by.equals(mysession.get("username"))) || (old_status_by.equals(""))));

						if (updated) {

//							if (myconfig.get(db, "use_workflow").equals("yes")) {
//								workflow.update_order(order);
//							}

							Cms.CMSAudit("action=open order=" + order.getDeliveryEmail() + " [" + order.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));

							// Save order changes

							order.update(db);
							order.setId(id);

							handleWorkflowAuto(server, order, workflow, myrequest, myresponse, mysession, myconfig, db, username, timestamp);
							handleEmailNotification(server, order, workflow, username, myrequest, myresponse, mysession, myconfig, db);

						}
					}
				}
			}
		}
	}



	public void doCloseMultiple(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return;
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return;
		error = "";
		String[] ids = myrequest.getParameters("id");

		if (ids.length > 0) {
			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			String username = mysession.get("username");
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				boolean updated = false;

				// Read chosen workflow action (if any)

				Order order = new Order();
				order.read(db, id);
				Workflow workflow = new Workflow(text);
				if (! myrequest.getParameter("status").equals("")) {
					workflow.read(db, myrequest.getParameter("status"));
				} else {
					workflow.setToState(order.getStatus());
				}

				String old_status = order.getStatus();
				String old_status_by = order.getStatusBy();

				// Fail if order checkedout and without administrator permissions to override

				if ((! order.getCheckedOut().equals("")) && (! order.getCheckedOut().equals(mysession.get("username"))) && (! myconfig.get(db, "superadmin").equals(mysession.get("username")))) {
					error = text.display("error.content.update.checkedout") + order.getCheckedOut();
				} else {
					// Handle workflow action and get posted data for users with update permissions

					if ((! myrequest.getParameter("status").equals("")) && (! id.equals("")) && (myconfig.get(db, "use_workflow_orders").equals("yes")) && ((myconfig.get(db, "superadmin").equals(mysession.get("username"))) || (workflow.permission(db, order.getStatus(), workflow.getToState(), mysession.get("usergroups"), mysession.get("usertypes"), "-order-", "", "", "", ((old_status_by.equals(mysession.get("username"))) || (old_status_by.equals(""))))))) {
						order.setStatus(workflow.getToState());
						updated = true;
					}

					// Clear status by if status cleared

					if (order.getStatus().equals("")) {
						order.setStatusBy("");
					} else if (! order.getStatus().equals(old_status)) {
						order.setStatusBy(username);
					}

					if ((! id.equals("")) && ((! myconfig.get(db, "use_workflow_orders").equals("yes")) || (myconfig.get(db, "superadmin").equals(mysession.get("username"))) || (workflow.permission(db, old_status, order.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), "-order-", "", "", "", ((old_status_by.equals(mysession.get("username"))) || (old_status_by.equals(""))))))) {

						order.setUpdated(timestamp);
						order.setUpdatedBy(username);
						order.setPublished(timestamp);
						order.setPublishedBy(username);
						updated = true;

						workflow.permission(db, old_status, order.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), "-order-", "", "", "", ((old_status_by.equals(mysession.get("username"))) || (old_status_by.equals(""))));

						if (updated) {

//							if (myconfig.get(db, "use_workflow").equals("yes")) {
//								workflow.update_order(order);
//							}

							Cms.CMSAudit("action=close order=" + order.getDeliveryEmail() + " [" + order.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));

							// Save order changes

							order.update(db);
							order.setId(id);

							handleWorkflowAuto(server, order, workflow, myrequest, myresponse, mysession, myconfig, db, username, timestamp);
							handleEmailNotification(server, order, workflow, username, myrequest, myresponse, mysession, myconfig, db);

						}
					}
				}
			}
		}
	}



	public void doMoveMultiple(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return;
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return;
		error = "";
		String[] ids = myrequest.getParameters("id");

		if (ids.length > 0) {
			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			String username = mysession.get("username");
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				boolean updated = false;

				// Read chosen workflow action (if any)

				Order order = new Order();
				order.read(db, id);
				Workflow workflow = new Workflow(text);
				if (! myrequest.getParameter("status").equals("")) {
					workflow.read(db, myrequest.getParameter("status"));
				} else {
					workflow.setToState(order.getStatus());
				}

				String old_status = order.getStatus();
				String old_status_by = order.getStatusBy();

				// Fail if order checkedout and without administrator permissions to override

				if ((! order.getCheckedOut().equals("")) && (! order.getCheckedOut().equals(mysession.get("username"))) && (! myconfig.get(db, "superadmin").equals(mysession.get("username")))) {
					error = text.display("error.content.update.checkedout") + order.getCheckedOut();
				} else {
					// Handle workflow action and get posted data for users with update permissions

					if ((! myrequest.getParameter("status").equals("")) && (! id.equals("")) && (myconfig.get(db, "use_workflow_orders").equals("yes")) && ((myconfig.get(db, "superadmin").equals(mysession.get("username"))) || (workflow.permission(db, order.getStatus(), workflow.getToState(), mysession.get("usergroups"), mysession.get("usertypes"), "-order-", "", "", "", ((old_status_by.equals(mysession.get("username"))) || (old_status_by.equals(""))))))) {
						order.setStatus(workflow.getToState());
						updated = true;
					}

					// Clear status by if status cleared

					if (order.getStatus().equals("")) {
						order.setStatusBy("");
					} else if (! order.getStatus().equals(old_status)) {
						order.setStatusBy(username);
					}

					if ((! id.equals("")) && ((! myconfig.get(db, "use_workflow_orders").equals("yes")) || (myconfig.get(db, "superadmin").equals(mysession.get("username"))) || (workflow.permission(db, old_status, order.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), "-order-", "", "", "", ((old_status_by.equals(mysession.get("username"))) || (old_status_by.equals(""))))))) {

						order.setUpdated(timestamp);
						order.setUpdatedBy(username);

						workflow.permission(db, old_status, order.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), "-order-", "", "", "", ((old_status_by.equals(mysession.get("username"))) || (old_status_by.equals(""))));

						if (updated) {

//							if (myconfig.get(db, "use_workflow").equals("yes")) {
//								workflow.update_order(order);
//							}

							Cms.CMSAudit("action=update order=" + order.getDeliveryEmail() + " [" + order.getId() + "]" + " status=" + old_status + "->" + order.getStatus() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));

							// Save order changes

							order.update(db);
							order.setId(id);

							handleWorkflowAuto(server, order, workflow, myrequest, myresponse, mysession, myconfig, db, username, timestamp);
							handleEmailNotification(server, order, workflow, username, myrequest, myresponse, mysession, myconfig, db);

						}
					}
				}
			}
		}
	}



	public HashMap<String,String> doEmail(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return new HashMap<String,String>();
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new HashMap<String,String>();
		error = "";
		HashMap<String,String> email = new HashMap<String,String>();
		if (License.valid(db, myconfig, "ecommerce")) {
			email.put("from", "");
			if (myrequest.parameterExists("from")) {
				email.put("from", myrequest.getParameter("from"));
			} else {
				email.put("from", myconfig.get(db, "contact_form_recipient"));
			}
			email.put("to", "");
			if (myrequest.parameterExists("to")) {
				email.put("to", myrequest.getParameter("to"));
			} else {
				email.put("to", myconfig.get(db, "contact_form_recipient"));
			}
			email.put("cc", "");
			if (myrequest.parameterExists("cc")) {
				email.put("cc", myrequest.getParameter("cc"));
			} else if (myrequest.getParameter("action").equals("")) {
				email.put("cc", mysession.get("email"));
			}
			email.put("bcc", "");
			if (myrequest.parameterExists("bcc")) {
				email.put("bcc", myrequest.getParameter("bcc"));
			} else if (myrequest.getParameter("action").equals("")) {
				String SQLwhere = "";
				String SQLwhere_in = "";
				String[] ids = myrequest.getParameters("id");
				if (ids.length > 0) {
					for (int i = 0; i < ids.length; i++) {
						String id = ids[i];
						if (! SQLwhere_in.equals("")) {
							SQLwhere_in += ",";
						}
						SQLwhere_in += "" + id;
					}
					SQLwhere += "id in (" + SQLwhere_in + ")";
				} else {
					SQLwhere += "id in (0)";
				}
				HashMap<String,String> emails = new HashMap<String,String>();
				String SQL = "select id, delivery_name, delivery_email, invoice_name, invoice_email from orders where " + SQLwhere + " order by delivery_name, invoice_name, delivery_email, invoice_email, id";
				Order order = new Order();
				order.records(db, SQL);
				while (order.records(db, "")) {
					if ((! order.getDeliveryEmail().equals("")) && (emails.get(order.getDeliveryEmail()) == null)) {
						email.put("bcc", (String)email.get("bcc") + "" + order.getDeliveryEmail() + "\r\n");
						emails.put(order.getDeliveryEmail(), order.getDeliveryEmail());
					}
					if ((! order.getInvoiceEmail().equals("")) && (emails.get(order.getInvoiceEmail()) == null)) {
						email.put("bcc", (String)email.get("bcc") + "" + order.getInvoiceEmail() + "\r\n");
						emails.put(order.getInvoiceEmail(), order.getInvoiceEmail());
					}
				}
			}
			email.put("subject", "");
			email.put("content", "");
			email.put("content_plaintext", "");
			if (! myrequest.getParameter("content_plaintext").equals("")) {
				email.put("content_plaintext", myrequest.getParameter("content_plaintext"));
			}
			email.put("stylesheet", "");
			email.put("style", "");
			if ((myrequest.getParameter("action").equals("")) && (! myrequest.getParameter("content_id").equals(""))) {
				Page page = new Page(text);
				page.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("content_id"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				page.setBody(page.getContent());
				page.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, page.getId(), "content_public", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		
				page.setDisplayCurrency(db, mysession.get("currency"), myconfig.get(db, "default_currency"));
				page.parse_output_product(server, mysession, myrequest, myresponse, db, myconfig);
		
				Shopcart shopcart = new Shopcart(text);
				shopcart.read(mysession, db, myconfig);
				shopcart.create(mysession);
				shopcart.calculate(server, mysession, myrequest, myresponse, mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"));
				Page shopcartitem = new Page(text);
				shopcartitem.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_shopcart_entry"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				page.parse_output_shopcart(shopcart, "", "", server, myrequest, myresponse, mysession, "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), db, myconfig, shopcartitem, false);
		
				Page guestbookentry = new Page(text);
				guestbookentry.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_guestbook_entry"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				page.parse_output_guestbook(db, myconfig, guestbookentry, mysession, mysession.get("version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"));
		
				if (page.getUser()) {
					email.put("subject", page.getTitle());
					email.put("content", page.getBody());
					email.put("stylesheet", page.getStyleSheet());
					if (page.getStyleSheet().equals("0")) {
						email.put("stylesheet", "");
						email.put("style", "");
					} else if (page.getStyleSheet().equals("")) {
						Content stylesheet_content = new Content(text);
						stylesheet_content.public_read(db, myconfig, myconfig.get(db, "default_stylesheet"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
						email.put("stylesheet", "/stylesheet.jsp?id=" + myconfig.get(db, "default_stylesheet"));
						email.put("style", stylesheet_content.getContent());
					} else {
						Content stylesheet_content = new Content(text);
						stylesheet_content.public_read(db, myconfig, page.getStyleSheet(), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
						email.put("stylesheet", "/stylesheet.jsp?id=" + page.getStyleSheet());
						email.put("style", stylesheet_content.getContent());
					}
				}
			} else {
				if (! myrequest.getParameter("subject").equals("")) {
					email.put("subject", myrequest.getParameter("subject"));
				}
				if (! myrequest.getParameter("content").equals("")) {
					email.put("content", myrequest.getParameter("content"));
				}
				if (! myrequest.getParameter("stylesheet").equals("")) {
					email.put("stylesheet", myrequest.getParameter("stylesheet"));
				}
				if (! myrequest.getParameter("style").equals("")) {
					email.put("style", myrequest.getParameter("style"));
				}
			}
			if (! myrequest.getParameter("action").equals("")) {
				HashMap<String,String> requestForm = Email.getForm(myrequest);
				Email.send_email(text, requestForm, (String)email.get("subject"), (String)email.get("content"), (String)email.get("content_plaintext"), (String)email.get("from"), (String)email.get("to"), (String)email.get("cc"), (String)email.get("bcc"), (String)email.get("stylesheet"), (String)email.get("style"), myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
			}
//			mysession.set("admin_contentbundle", "");
//			mysession.set("admin_contentgroup", "");
//			mysession.set("admin_contenttype", "");
//			mysession.set("admin_version", "");
//			mysession.set("admin_contentclass", "page");
//			email.put("pages", getIndex(mysession, myrequest, myresponse, myconfig, db));
//			mysession.set("admin_contentclass", "product");
//			email.put("products", getIndex(mysession, myrequest, myresponse, myconfig, db));
		}
		return email;
	}



	public void getExport(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return;
	}



	public LinkedHashMap doExport(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new LinkedHashMap();
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new LinkedHashMap();
		String SQL = "select orders.id as order_id, orders.user_id as user_id, orders.created as created, orders.updated as updated, orders.published as published, orders.paid as paid, orders.created_by as created_by, orders.updated_by as updated_by, orders.published_by as published_by, orders.status as status, orders.order_quantity as order_quantity, orders.order_currency as order_currency, orders.order_subtotal as order_subtotal, orders.tax_description as tax_description, orders.tax_total as tax_total, orders.shipping_description as shipping_description, orders.shipping_total as shipping_total, orders.order_total as order_total, orders.card_type as card_type, orders.card_number as card_number, orders.card_issuedmonth as card_issuedmonth, orders.card_issuedyear as card_issuedyear, orders.card_expirymonth as card_expirymonth, orders.card_expiryyear as card_expiryyear, orders.card_name as card_name, orders.card_cvc as card_cvc, orders.card_issue as card_issue, orders.card_postalcode as card_postalcode, orders.delivery_name as delivery_name, orders.delivery_organisation as delivery_organisation, orders.delivery_address as delivery_address, orders.delivery_postalcode as delivery_postalcode, orders.delivery_city as delivery_city, orders.delivery_state as delivery_state, orders.delivery_country as delivery_country, orders.delivery_phone as delivery_phone, orders.delivery_fax as delivery_fax, orders.delivery_email as delivery_email, orders.delivery_website as delivery_website, orders.invoice_name as invoice_name, orders.invoice_organisation as invoice_organisation, orders.invoice_address as invoice_address, orders.invoice_postalcode as invoice_postalcode, orders.invoice_city as invoice_city, orders.invoice_state as invoice_state, orders.invoice_country as invoice_country, orders.invoice_phone as invoice_phone, orders.invoice_fax as invoice_fax, orders.invoice_email as invoice_email, orders.invoice_website as invoice_website, orders.discount_description as discount_description, orders.discount_total as discount_total, orders.affiliate as affiliate from orders order by orders.id";
		LinkedHashMap orders = db.query_records(SQL);
		myresponse.setHeader("Content-Disposition", "filename=orders.csv");
		myresponse.setContentType("x-application/csv");
		return orders;
	}



	private void handleWorkflowAuto(ServletContext server, Order myorder, Workflow workflow, Request myrequest, Response myresponse, Session mysession, Configuration myconfig, DB db, String username, String now) throws Exception {
		if (myconfig.get(db, "use_workflow").equals("yes")) {
			if (workflow.getAutoCheckin()) {
//				myorder.checkin(db, mysession.get("username"), myconfig.get(db, "superadmin"));
			}
			if (workflow.getAutoCheckout()) {
//				myorder.checkout(db, mysession.get("username"));
			}
			if (workflow.getAutoPublish()) {
//				myorder.publish(db, username, now);
			}
			if (workflow.getAutoUnpublish()) {
//				myorder.unpublish(db, username, now);
			}
			if (workflow.getAutoDelete()) {
//				myorder.delete(db, username, now);
			}
		}
		if (! workflow.getWorkflowProgram().equals("")) {
			String output = Common.execute("/" + text.display("adminpath") + "/workflowaction/" + workflow.getWorkflowProgram() + ".jsp", "order", myorder, "workflow", workflow, "workflowaction", server, mysession.getSession(), myrequest.getRequest(), myresponse.getResponse());
		}
	}



	private void handleEmailNotification(ServletContext server, Order order, Workflow workflow, String username, Request myrequest, Response myresponse, Session mysession, Configuration myconfig, DB db) throws Exception {
		if (! workflow.getNotifyEmail().equals("")) {
			String subject = "";
			String body = "";
			Page notification = new Page(text);
			notification.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, workflow.getNotifyEmail(), "", myconfig.get(db, "default_version"), "", "", "", "", "", myconfig.get(db, "default_template"), "", "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			subject = "" + notification.getTitle() + " " + order.getId();
			body = "" + notification.getContent();
			User myuser = new User();
			myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, username);
			body = body.replaceAll("@@@name@@@", myuser.getName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			body = body.replaceAll("@@@view@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/orders/view.jsp?id=" + order.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
			body = body.replaceAll("@@@update@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/orders/update.jsp?id=" + order.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
			body = body.replaceAll("@@@delete@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/orders/delete.jsp?id=" + order.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
			Page ordernotification_entry = new Page(text);
			ordernotification_entry.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_notification_entry"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			body = notification.parse_output_order(order, body, db, myconfig, ordernotification_entry, "", "", "", "", server, mysession, myrequest, myresponse);
			sendNotification(subject, body, server, order, workflow, myrequest, myresponse, mysession, myconfig, db);
		}

		if (myrequest.getParameter("ordertracking").equals("yes")) {
			Page ordertracking = new Page(text);
			ordertracking.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_ordertracking_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			Page ordertracking_entry = new Page(text);
			ordertracking_entry.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_ordertracking_entry"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			ordertracking.parse_output_order(order, db, myconfig, ordertracking_entry, "", "", "", "", server, mysession, myrequest, myresponse);
			if (! order.getInvoiceEmail().equals("")) {
				Email.send_email(text, new HashMap<String,String>(), ordertracking.getTitle(), ordertracking.getBody(), "", myconfig.get(db, "order_form_recipient"), order.getInvoiceEmail(), "", "", "", "", myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
			} else if  (! order.getDeliveryEmail().equals("")) {
				Email.send_email(text, new HashMap<String,String>(), ordertracking.getTitle(), ordertracking.getBody(), "", myconfig.get(db, "order_form_recipient"), order.getDeliveryEmail(), "", "", "", "", myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
			}
		}
	}



	private void sendNotification(String subject, String body, ServletContext server, Order myorder, Workflow workflow, Request myrequest, Response myresponse, Session mysession, Configuration myconfig, DB db) throws Exception {
		String admin_email = "";
		String[] assigned_emails = null;
		if (myorder.getCheckedOut().equals("-creators-")) {
//			assigned_emails = myorder.creatorsEmails(mysession, myconfig, db);
		} else if (myorder.getCheckedOut().equals("-editors-")) {
//			assigned_emails = myorder.editorsEmails(mysession, myconfig, db);
		} else if (myorder.getCheckedOut().equals("-developers-")) {
//			assigned_emails = myorder.developersEmails(mysession, myconfig, db);
		} else if (myorder.getCheckedOut().equals("-publishers-")) {
//			assigned_emails = myorder.publishersEmails(mysession, myconfig, db);
		} else if (myorder.getCheckedOut().equals("-administrators-")) {
//			assigned_emails = myorder.administratorsEmails(mysession, myconfig, db);
		} else if (! myorder.getCheckedOut().equals("")) {
			User myuser = new User();
			myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myorder.getCheckedOut());
			admin_email = myuser.getEmail();
		}
		String[] workflow_emails = null;
		if ((workflow != null) && (myconfig.get(db, "use_workflow").equals("yes"))) {
			if (myorder.getStatus().equals("")) {
				workflow_emails = myorder.administratorsEmails(mysession, myconfig, db);
			} else {
				workflow_emails = workflow.adminEmails(myorder, mysession, myconfig, db);
			}
		} else {
			workflow_emails = myorder.administratorsEmails(mysession, myconfig, db);
		}
		if (! admin_email.equals("")) {
			// assigned to specific administrator
		} else if ((assigned_emails != null) && (workflow_emails != null)) {
			// assigned to administrator role with workflow permissions
			admin_email = Common.join(", ", Common.array_intersect(assigned_emails, workflow_emails));
		} else {
			// administrators with workflow permissions
			admin_email = Common.join(", ", workflow_emails);
		}
		if ((! myrequest.getParameter("email_notification_to").equals("")) && (myconfig.get(db, "contact_form_recipients").indexOf(myrequest.getParameter("email_notification_to"))>=0)) {
			if (! admin_email.equals("")) {
				admin_email += ", ";
			}
			if (myrequest.getParameter("email_notification_to").indexOf("@")>=0) {
				admin_email += html.encodeHtmlEntities(myrequest.getParameter("email_notification_to"));
			} else {
				admin_email += html.encodeHtmlEntities(myrequest.getParameter("email_notification_to")) + "@" + myrequest.getServerName();
			}
		}
		if (admin_email.equals("")) {
			admin_email = myconfig.get(db, "superadmin_email");
		}
		String from = "";
		if ((! myrequest.getParameter("email_notification_from").equals("")) && (myconfig.get(db, "contact_form_recipients").indexOf(myrequest.getParameter("email_notification_from"))>=0)) {
			if (myrequest.getParameter("email_notification_from").indexOf("@")>=0) {
				from = html.encodeHtmlEntities(myrequest.getParameter("email_notification_from"));
			} else {
				from = html.encodeHtmlEntities(myrequest.getParameter("email_notification_from")) + "@" + myrequest.getServerName();
			}
		} else if (! mysession.get("email").equals("")) {
			from = mysession.get("email");
		} else if (! mysession.get("username").equals("")) {
			from = mysession.get("username") + "@" + myrequest.getServerName();
		} else if (! myrequest.getRemoteHost().equals("")) {
			from = myrequest.getRemoteHost() + "@" + myrequest.getServerName();
		} else if (! myrequest.getRemoteAddr().equals("")) {
			from = myrequest.getRemoteAddr() + "@" + myrequest.getServerName();
		} else {
			from = "nobody" + "@" + myrequest.getServerName();
		}
		if ((workflow != null) && (myconfig.get(db, "use_workflow").equals("yes")) && (myconfig.get(db, "workflow_notify_from").equals("superadmin"))) {
			from = myconfig.get(db, "superadmin_email");
		}
		if (from.indexOf("@")<0) {
			from = from + "@" + myrequest.getServerName();
		}
//		Email.send_email(new HashMap(), subject, body, "", from, admin_email, myrequest.getServerName(), mysession, myrequest, myresponse, myconfig, db);
		HashMap<String,String> requestForm = Email.getForm(myrequest);
		Email.send_email(text, requestForm, subject, body, "", from, admin_email, "", "", "", "", myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
	}



	public String getError() {
		return error;
	}



	public int getRecordCount() {
		return record_count;
	}



	private void setSessionFilterFromRequest(Session mysession, Request myrequest) {
		if (myrequest.getParameter("contentpackage").equals(" ")) {
			mysession.set("admin_contentpackage", "");
		} else if (! myrequest.getParameter("contentpackage").equals("")) {
			mysession.set("admin_contentpackage", myrequest.getParameter("contentpackage"));
		}
		if (myrequest.getParameter("contentclass").equals(" ")) {
			mysession.set("admin_contentclass", "");
		} else if (! myrequest.getParameter("contentclass").equals("")) {
			mysession.set("admin_contentclass", myrequest.getParameter("contentclass"));
		}
		if (myrequest.getParameter("contenttype").equals(" ")) {
			mysession.set("admin_contenttype", "");
		} else if (! myrequest.getParameter("contenttype").equals("")) {
			mysession.set("admin_contenttype", myrequest.getParameter("contenttype"));
		}
		if (myrequest.getParameter("contentgroup").equals(" ")) {
			mysession.set("admin_contentgroup", "");
		} else if (! myrequest.getParameter("contentgroup").equals("")) {
			mysession.set("admin_contentgroup", myrequest.getParameter("contentgroup"));
		}
		if (myrequest.getParameter("contentbundle").equals(" ")) {
			mysession.set("admin_contentbundle", "");
		} else if (! myrequest.getParameter("contentbundle").equals("")) {
			mysession.set("admin_contentbundle", myrequest.getParameter("contentbundle"));
		}
		if (myrequest.getParameter("version").equals(" ")) {
			mysession.set("admin_version", "");
		} else if (! myrequest.getParameter("version").equals("")) {
			mysession.set("admin_version", myrequest.getParameter("version"));
		}
		if (myrequest.getParameter("status").equals(" ")) {
			mysession.set("admin_status", "");
		} else if (! myrequest.getParameter("status").equals("")) {
			mysession.set("admin_status", myrequest.getParameter("status"));
		}
		if (myrequest.getParameter("stock").equals(" ")) {
			mysession.set("admin_stock", "");
		} else if (! myrequest.getParameter("stock").equals("")) {
			mysession.set("admin_stock", myrequest.getParameter("stock"));
		}
	}



}
