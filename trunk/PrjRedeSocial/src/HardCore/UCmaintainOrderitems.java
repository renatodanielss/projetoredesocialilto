package HardCore;

import java.text.*;
import java.util.*;

public class UCmaintainOrderitems {
	private String error = "";
	private Text text = new Text();



	public UCmaintainOrderitems() {
	}



	public UCmaintainOrderitems(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Orderitem getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Orderitem();
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Orderitem();
		Orderitem orderitem = new Orderitem();
		orderitem.read(db, myrequest.getParameter("id"));
		return orderitem;
	}



	public Orderitem getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Orderitem();
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Orderitem();
		Orderitem orderitem = new Orderitem();
		orderitem.read(db, myrequest.getParameter("id"));
		return orderitem;
	}



	public Orderitem getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Orderitem();
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Orderitem();
		Orderitem orderitem = new Orderitem();
		orderitem.read(db, myrequest.getParameter("id"));
		return orderitem;
	}



	public Orderitem getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Orderitem();
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Orderitem();
		Orderitem orderitem = new Orderitem();
		orderitem.read(db, myrequest.getParameter("id"));
		return orderitem;
	}



	public Orderitem doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Orderitem();
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Orderitem();
		error = "";
		Orderitem orderitem = new Orderitem();
		orderitem.read(db, myrequest.getParameter("id"));
		orderitem.getForm(myrequest);
		Order order = new Order();
		order.read(db, orderitem.getOrderId());
		Workflow workflow = new Workflow(text);
		workflow.setFromState(order.getStatus());
		workflow.setToState(order.getStatus());
		String old_status = order.getStatus();
		String old_status_by = order.getStatusBy();
		String username = mysession.get("username");
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		order.setUpdated(now);
		order.setUpdatedBy(username);
		if ((! order.getCheckedOut().equals("")) && (! order.getCheckedOut().equals(mysession.get("username"))) && (! myconfig.get(db, "superadmin").equals(mysession.get("username")))) {
			error = text.display("orders.update.nopermission.checkedout") + order.getCheckedOut();
		} else if (((! myconfig.get(db, "use_workflow_orders").equals("yes")) || (workflow.permission(db, old_status, order.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), "-order-", "", "", "", ((old_status_by.equals(mysession.get("username"))) || (old_status_by.equals(""))))) || (myconfig.get(db, "superadmin").equals(mysession.get("username"))))) {
			orderitem.calculate(db, myconfig, order.getOrderCurrency());
			orderitem.create(db);
			order.setOrderQuantity(Common.intnumber(order.getOrderQuantity()) + Common.intnumber(orderitem.getItemQuantity()));
			order.setOrderSubtotal(Common.number(order.getOrderSubtotal()) + Common.number(orderitem.getItemTotal()));
			order.setOrderTotal(Common.number(order.getOrderTotal()) + Common.number(orderitem.getItemTotal()));
			order.setDiscountTotal(Common.number(order.getDiscountTotal()) + Common.number(orderitem.getDiscountTotal()));
			order.setShippingTotal(Common.number(order.getShippingTotal()) + Common.number(orderitem.getShippingTotal()));
			order.setOrderTotal(Common.number(order.getOrderTotal()) + Common.number(orderitem.getShippingTotal()));
			order.setTaxTotal(Common.number(order.getTaxTotal()) + Common.number(orderitem.getTaxTotal()));
			order.setOrderTotal(Common.number(order.getOrderTotal()) + Common.number(orderitem.getTaxTotal()));
			doUpdateOrderDescriptions(order, db, "", "", "");
			order.calculate(db, myconfig);
			order.update(db);
		} else {
			error += text.display("orders.update.nopermission");
		}
		return orderitem;
	}



	public Orderitem doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Orderitem();
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Orderitem();
		error = "";
		Orderitem orderitem = new Orderitem();
		orderitem.read(db, myrequest.getParameter("id"));
		String old_orderitem_item_quantity = orderitem.getItemQuantity();
		String old_orderitem_item_total = orderitem.getItemTotal();
		String old_orderitem_discount_total = orderitem.getDiscountTotal();
		String old_orderitem_shipping_total = orderitem.getShippingTotal();
		String old_orderitem_tax_total = orderitem.getTaxTotal();
		String old_orderitem_discount_description = orderitem.getDiscountDescription();
		String old_orderitem_shipping_description = orderitem.getShippingDescription();
		String old_orderitem_tax_description = orderitem.getTaxDescription();
		orderitem.getForm(myrequest);
		Order order = new Order();
		order.read(db, orderitem.getOrderId());
		Workflow workflow = new Workflow(text);
		workflow.setFromState(order.getStatus());
		workflow.setToState(order.getStatus());
		String old_status = order.getStatus();
		String old_status_by = order.getStatusBy();
		String username = mysession.get("username");
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		order.setUpdated(now);
		order.setUpdatedBy(username);
		if ((! order.getCheckedOut().equals("")) && (! order.getCheckedOut().equals(mysession.get("username"))) && (! myconfig.get(db, "superadmin").equals(mysession.get("username")))) {
			error = text.display("orders.update.nopermission.checkedout") + order.getCheckedOut();
		} else if (((! myconfig.get(db, "use_workflow_orders").equals("yes")) || (workflow.permission(db, old_status, order.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), "-order-", "", "", "", ((old_status_by.equals(mysession.get("username"))) || (old_status_by.equals(""))))) || (myconfig.get(db, "superadmin").equals(mysession.get("username"))))) {
			orderitem.calculate(db, myconfig, order.getOrderCurrency());
			orderitem.update(db);
			order.setOrderQuantity(Common.intnumber(order.getOrderQuantity()) + Common.intnumber(orderitem.getItemQuantity()) - Common.intnumber(old_orderitem_item_quantity));
			order.setOrderSubtotal(Common.number(order.getOrderSubtotal()) + Common.number(orderitem.getItemTotal()) - Common.number(old_orderitem_item_total));
			order.setOrderTotal(Common.number(order.getOrderTotal()) + Common.number(orderitem.getItemTotal()) - Common.number(old_orderitem_item_total));
			order.setDiscountTotal(Common.number(order.getDiscountTotal()) + Common.number(orderitem.getDiscountTotal()) - Common.number(old_orderitem_discount_total));
			order.setShippingTotal(Common.number(order.getShippingTotal()) + Common.number(orderitem.getShippingTotal()) - Common.number(old_orderitem_shipping_total));
			order.setOrderTotal(Common.number(order.getOrderTotal()) + Common.number(orderitem.getShippingTotal()) - Common.number(old_orderitem_shipping_total));
			order.setTaxTotal(Common.number(order.getTaxTotal()) + Common.number(orderitem.getTaxTotal()) - Common.number(old_orderitem_tax_total));
			order.setOrderTotal(Common.number(order.getOrderTotal()) + Common.number(orderitem.getTaxTotal()) - Common.number(old_orderitem_tax_total));
			doUpdateOrderDescriptions(order, db, old_orderitem_discount_description, old_orderitem_shipping_description, old_orderitem_tax_description);
			order.calculate(db, myconfig);
			order.update(db);
		} else {
			error += text.display("orders.update.nopermission");
		}
		return orderitem;
	}



	public Orderitem doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Orderitem();
		accesspermission = RequireUser.OrderAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Orderitem();
		error = "";
		Orderitem orderitem = new Orderitem();
		orderitem.read(db, myrequest.getParameter("id"));
		String old_orderitem_item_quantity = orderitem.getItemQuantity();
		String old_orderitem_item_total = orderitem.getItemTotal();
		String old_orderitem_discount_total = orderitem.getDiscountTotal();
		String old_orderitem_shipping_total = orderitem.getShippingTotal();
		String old_orderitem_tax_total = orderitem.getTaxTotal();
		String old_orderitem_discount_description = orderitem.getDiscountDescription();
		String old_orderitem_shipping_description = orderitem.getShippingDescription();
		String old_orderitem_tax_description = orderitem.getTaxDescription();
		Order order = new Order();
		order.read(db, orderitem.getOrderId());
		Workflow workflow = new Workflow(text);
		workflow.setFromState(order.getStatus());
		workflow.setToState(order.getStatus());
		String old_status = order.getStatus();
		String old_status_by = order.getStatusBy();
		String username = mysession.get("username");
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		order.setUpdated(now);
		order.setUpdatedBy(username);
		if ((! order.getCheckedOut().equals("")) && (! order.getCheckedOut().equals(mysession.get("username"))) && (! myconfig.get(db, "superadmin").equals(mysession.get("username")))) {
			error = text.display("orders.update.nopermission.checkedout") + order.getCheckedOut();
		} else if (((! myconfig.get(db, "use_workflow_orders").equals("yes")) || (workflow.permission(db, old_status, order.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), "-order-", "", "", "", ((old_status_by.equals(mysession.get("username"))) || (old_status_by.equals(""))))) || (myconfig.get(db, "superadmin").equals(mysession.get("username"))))) {
			orderitem.delete(db);
			order.setOrderQuantity(Common.intnumber(order.getOrderQuantity()) - Common.intnumber(old_orderitem_item_quantity));
			order.setOrderSubtotal(Common.number(order.getOrderSubtotal()) - Common.number(old_orderitem_item_total));
			order.setOrderTotal(Common.number(order.getOrderTotal()) - Common.number(old_orderitem_item_total));
			order.setDiscountTotal(Common.number(order.getDiscountTotal()) - Common.number(old_orderitem_discount_total));
			order.setShippingTotal(Common.number(order.getShippingTotal()) - Common.number(old_orderitem_shipping_total));
			order.setOrderTotal(Common.number(order.getOrderTotal()) - Common.number(old_orderitem_shipping_total));
			order.setTaxTotal(Common.number(order.getTaxTotal()) - Common.number(old_orderitem_tax_total));
			order.setOrderTotal(Common.number(order.getOrderTotal()) - Common.number(old_orderitem_tax_total));
			doUpdateOrderDescriptions(order, db, old_orderitem_discount_description, old_orderitem_shipping_description, old_orderitem_tax_description);
			order.calculate(db, myconfig);
			order.update(db);
		} else {
			error += text.display("orders.update.nopermission");
		}
		return orderitem;
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
		String SQL = "select orders.id as order_id, orders.user_id as user_id, orders.created as created, orders.updated as updated, orders.published as published, orders.paid as paid, orders.created_by as created_by, orders.updated_by as updated_by, orders.published_by as published_by, orders.status as status, orders.order_quantity as order_quantity, orders.order_currency as order_currency, orders.order_subtotal as order_subtotal, orders.tax_description as tax_description, orders.tax_total as tax_total, orders.shipping_description as shipping_description, orders.shipping_total as shipping_total, orders.order_total as order_total, orders.card_type as card_type, orders.card_number as card_number, orders.card_issuedmonth as card_issuedmonth, orders.card_issuedyear as card_issuedyear, orders.card_expirymonth as card_expirymonth, orders.card_expiryyear as card_expiryyear, orders.card_name as card_name, orders.card_cvc as card_cvc, orders.card_issue as card_issue, orders.card_postalcode as card_postalcode, orders.delivery_name as delivery_name, orders.delivery_organisation as delivery_organisation, orders.delivery_address as delivery_address, orders.delivery_postalcode as delivery_postalcode, orders.delivery_city as delivery_city, orders.delivery_state as delivery_state, orders.delivery_country as delivery_country, orders.delivery_phone as delivery_phone, orders.delivery_fax as delivery_fax, orders.delivery_email as delivery_email, orders.delivery_website as delivery_website, orders.invoice_name as invoice_name, orders.invoice_organisation as invoice_organisation, orders.invoice_address as invoice_address, orders.invoice_postalcode as invoice_postalcode, orders.invoice_city as invoice_city, orders.invoice_state as invoice_state, orders.invoice_country as invoice_country, orders.invoice_phone as invoice_phone, orders.invoice_fax as invoice_fax, orders.invoice_email as invoice_email, orders.invoice_website as invoice_website, orders.affiliate as affiliate, orderitems.id as orderitem_id, orderitems.product_id as product_id, orderitems.version as version, orderitems.title as title, orderitems.summary as summary, orderitems.image1 as image1, orderitems.image2 as image2, orderitems.image3 as image3, orderitems.file1 as file1, orderitems.file2 as file2, orderitems.file3 as file3, orderitems.link1 as link1, orderitems.link2 as link2, orderitems.link3 as link3, orderitems.author as author, orderitems.keywords as keywords, orderitems.description as description, orderitems.product_code as product_code, orderitems.product_currency as product_currency, orderitems.product_price as product_price, orderitems.product_period as product_period, orderitems.product_stock as product_stock, orderitems.product_comment as product_comment, orderitems.product_email as product_email, orderitems.product_url as product_url, orderitems.product_delivery as product_delivery, orderitems.product_user as product_user, orderitems.product_page as product_page, orderitems.product_program as product_program, orderitems.product_hosting as product_hosting, orderitems.product_brand as product_brand, orderitems.product_colour as product_colour, orderitems.product_size as product_size, orderitems.product_options as product_options, orderitems.item_quantity as item_quantity, orderitems.item_subtotal as item_subtotal, orderitems.item_total as item_total, orderitems.shipping_total as item_shipping_total, orderitems.shipping_description as item_shipping_description, orderitems.tax_total as item_tax_total, orderitems.tax_description as item_tax_description, orderitems.discount_total as item_discount_total, orderitems.discount_description as item_discount_description, orders.discount_description as discount_description, orders.discount_total as discount_total from orders,orderitems where orders.id=orderitems.order_id order by orders.id,orderitems.id";
		LinkedHashMap orderitems = db.query_records(SQL);
		myresponse.setHeader("Content-Disposition", "filename=orderitems.csv");
		myresponse.setContentType("x-application/csv");
		return orderitems;
	}



	public String getError() {
		return error;
	}



	private void doUpdateOrderDescriptions(Order order, DB db, String old_orderitem_discount_description, String old_orderitem_shipping_description, String old_orderitem_tax_description) {
		String SQL = "select * from orderitems where order_id=" + Common.intnumber(order.getId());
		Orderitem orderitem = new Orderitem();
		orderitem.records(db, SQL);
		String order_discount_description = "\r\n" + order.getDiscountDescription() + "\r\n";
		String order_shipping_description = "\r\n" + order.getShippingDescription() + "\r\n";
		String order_tax_description = "\r\n" + order.getTaxDescription() + "\r\n";
		order_discount_description = order_discount_description.replaceAll("\r\n" + old_orderitem_discount_description + "\r\n", "\r\n");
		order_discount_description = order_discount_description.replaceAll("\r\n" + "<span class=\"discount\">" + old_orderitem_discount_description + "</span>" + "\r\n", "\r\n");
		order_shipping_description = order_shipping_description.replaceAll("\r\n" + old_orderitem_shipping_description + "\r\n", "\r\n");
		order_shipping_description = order_shipping_description.replaceAll("\r\n" + "<span class=\"shipping\">" + old_orderitem_shipping_description + "</span>" + "\r\n", "\r\n");
		order_tax_description = order_tax_description.replaceAll("\r\n" + old_orderitem_tax_description + "\r\n", "\r\n");
		order_tax_description = order_tax_description.replaceAll("\r\n" + "<span class=\"tax\">" + old_orderitem_tax_description + "</span>" + "\r\n", "\r\n");
		while (orderitem.records(db, "")) {
			if (! orderitem.getDiscountDescription().equals("")) {
				if ((order_discount_description.indexOf("\r\n" + orderitem.getDiscountDescription() + "\r\n") < 0) && (order_discount_description.indexOf("\r\n" + "<span class=\"discount\">" + orderitem.getDiscountDescription() + "</span>" + "\r\n") < 0)) {
					if (! order_discount_description.equals("")) order_discount_description = "\r\n" + order_discount_description;
					order_discount_description = orderitem.getDiscountDescription() + order_discount_description;
				}
			}
			if (! orderitem.getShippingDescription().equals("")) {
				if ((order_shipping_description.indexOf("\r\n" + orderitem.getShippingDescription() + "\r\n") < 0) && (order_shipping_description.indexOf("\r\n" + "<span class=\"shipping\">" + orderitem.getShippingDescription() + "</span>" + "\r\n") < 0)) {
					if (! order_shipping_description.equals("")) order_shipping_description = "\r\n" + order_shipping_description;
					order_shipping_description = orderitem.getShippingDescription() + order_shipping_description;
				}
			}
			if (! orderitem.getTaxDescription().equals("")) {
				if ((order_tax_description.indexOf("\r\n" + orderitem.getTaxDescription() + "\r\n") < 0) && (order_tax_description.indexOf("\r\n" + "<span class=\"tax\">" + orderitem.getTaxDescription() + "</span>" + "\r\n") < 0)) {
					if (! order_tax_description.equals("")) order_tax_description = "\r\n" + order_tax_description;
					order_tax_description = orderitem.getTaxDescription() + order_tax_description;
				}
			}
		}
		order_discount_description = order_discount_description.replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n+", "\n").replaceAll("^\n+", "\n").replaceAll("\n*$", "\n").replaceAll("^\n*$", "").replaceAll("\n", "\r\n");
		order_shipping_description = order_shipping_description.replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n+", "\n").replaceAll("^\n+", "\n").replaceAll("\n*$", "\n").replaceAll("^\n*$", "").replaceAll("\n", "\r\n");
		order_tax_description = order_tax_description.replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n+", "\n").replaceAll("^\n+", "\n").replaceAll("\n*$", "\n").replaceAll("^\n*$", "").replaceAll("\n", "\r\n");
		order.setDiscountDescription(order_discount_description);
		order.setShippingDescription(order_shipping_description);
		order.setTaxDescription(order_tax_description);
	}



}
