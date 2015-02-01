package HardCore;

import java.sql.*;
import java.util.HashMap;
import java.util.regex.*;

public class Shipping {
	private String id = "";
	private String title = "";
	private String country = "";
	private String state = "";
	private String product_id = "";
	private String product_group = "";
	private String product_type = "";
	private String product_weight_from = "";
	private String product_weight_to = "";
	private String product_volume_from = "";
	private String product_volume_to = "";
	private String product_width_from = "";
	private String product_width_to = "";
	private String product_height_from = "";
	private String product_height_to = "";
	private String product_depth_from = "";
	private String product_depth_to = "";
	private String quantity_from = "";
	private String quantity_to = "";
	private String total_currency = "";
	private String total_from = "";
	private String total_to = "";
	private String total_weight_from = "";
	private String total_weight_to = "";
	private String total_volume_from = "";
	private String total_volume_to = "";
	private String total_width_from = "";
	private String total_width_to = "";
	private String total_height_from = "";
	private String total_height_to = "";
	private String total_depth_from = "";
	private String total_depth_to = "";
	private String ship_description = "";
	private String ship_currency = "";
	private String ship_order = "";
	private String ship_item = "";
	private String ship_percent = "";
	private String ship_total = "";
	private Page dummy_page = new Page(null);

	private	Statement rs = null;



	public Shipping() {
		init();
	}



	public void init() {
		id = "";
		title = "";
		country = "";
		state = "";
		product_id = "";
		product_group = "";
		product_type = "";
		product_weight_from = "0";
		product_weight_to = "0";
		product_volume_from = "0";
		product_volume_to = "0";
		product_width_from = "0";
		product_width_to = "0";
		product_height_from = "0";
		product_height_to = "0";
		product_depth_from = "0";
		product_depth_to = "0";
		quantity_from = "0";
		quantity_to = "0";
		total_currency = "";
		total_from = "0";
		total_to = "0";
		total_weight_from = "0";
		total_weight_to = "0";
		total_volume_from = "0";
		total_volume_to = "0";
		total_width_from = "0";
		total_width_to = "0";
		total_height_from = "0";
		total_height_to = "0";
		total_depth_from = "0";
		total_depth_to = "0";
		ship_description = "";
		ship_currency = "";
		ship_order = "0";
		ship_item = "0";
		ship_percent = "0";
		ship_total = "0";
		dummy_page = new Page(null);
	}



	public void read(DB db, String id) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			String SQL = "select * from shipping where id=" + Common.integer(id);
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void getRecord(DB db, HashMap<String,String> record) {
		if (record.get("id") != null) id = "" + record.get("id"); else id = "";
		if (record.get("title") != null) title = "" + record.get("title"); else title = "";
		if (record.get("country") != null) country = "" + record.get("country"); else country = "";
		if (record.get("state") != null) state = "" + record.get("state"); else state = "";
		if (record.get("product_id") != null) product_id = "" + record.get("product_id"); else product_id = "";
		if (record.get("product_group") != null) product_group = "" + record.get("product_group"); else product_group = "";
		if (record.get("product_type") != null) product_type = "" + record.get("product_type"); else product_type = "";
		if (record.get("product_weight_from") != null) product_weight_from = "" + record.get("product_weight_from"); else product_weight_from = "";
		if (record.get("product_weight_to") != null) product_weight_to = "" + record.get("product_weight_to"); else product_weight_to = "";
		if (record.get("product_volume_from") != null) product_volume_from = "" + record.get("product_volume_from"); else product_volume_from = "";
		if (record.get("product_volume_to") != null) product_volume_to = "" + record.get("product_volume_to"); else product_volume_to = "";
		if (record.get("product_width_from") != null) product_width_from = "" + record.get("product_width_from"); else product_width_from = "";
		if (record.get("product_width_to") != null) product_width_to = "" + record.get("product_width_to"); else product_width_to = "";
		if (record.get("product_height_from") != null) product_height_from = "" + record.get("product_height_from"); else product_height_from = "";
		if (record.get("product_height_to") != null) product_height_to = "" + record.get("product_height_to"); else product_height_to = "";
		if (record.get("product_depth_from") != null) product_depth_from = "" + record.get("product_depth_from"); else product_depth_from = "";
		if (record.get("product_depth_to") != null) product_depth_to = "" + record.get("product_depth_to"); else product_depth_to = "";
		if (record.get("quantity_from") != null) quantity_from = "" + record.get("quantity_from"); else quantity_from = "";
		if (record.get("quantity_to") != null) quantity_to = "" + record.get("quantity_to"); else quantity_to = "";
		if (record.get("total_currency") != null) total_currency = "" + record.get("total_currency"); else total_currency = "";
		if (record.get("total_from") != null) total_from = "" + record.get("total_from"); else total_from = "";
		if (record.get("total_to") != null) total_to = "" + record.get("total_to"); else total_to = "";
		if (record.get("total_weight_from") != null) total_weight_from = "" + record.get("total_weight_from"); else total_weight_from = "";
		if (record.get("total_weight_to") != null) total_weight_to = "" + record.get("total_weight_to"); else total_weight_to = "";
		if (record.get("total_volume_from") != null) total_volume_from = "" + record.get("total_volume_from"); else total_volume_from = "";
		if (record.get("total_volume_to") != null) total_volume_to = "" + record.get("total_volume_to"); else total_volume_to = "";
		if (record.get("total_width_from") != null) total_width_from = "" + record.get("total_width_from"); else total_width_from = "";
		if (record.get("total_width_to") != null) total_width_to = "" + record.get("total_width_to"); else total_width_to = "";
		if (record.get("total_height_from") != null) total_height_from = "" + record.get("total_height_from"); else total_height_from = "";
		if (record.get("total_height_to") != null) total_height_to = "" + record.get("total_height_to"); else total_height_to = "";
		if (record.get("total_depth_from") != null) total_depth_from = "" + record.get("total_depth_from"); else total_depth_from = "";
		if (record.get("total_depth_to") != null) total_depth_to = "" + record.get("total_depth_to"); else total_depth_to = "";
		if (record.get("ship_description") != null) ship_description = "" + record.get("ship_description"); else ship_description = "";
		if (record.get("ship_currency") != null) ship_currency = "" + record.get("ship_currency"); else ship_currency = "";
		if (record.get("ship_order") != null) ship_order = "" + record.get("ship_order"); else ship_order = "";
		if (record.get("ship_item") != null) ship_item = "" + record.get("ship_item"); else ship_item = "";
		if (record.get("ship_percent") != null) ship_percent = "" + record.get("ship_percent"); else ship_percent = "";
		if (record.get("ship_total") != null) ship_total = "" + record.get("ship_total"); else ship_total = "";
	}



	public void getForm(Request request) {
		title = "" + request.getParameter("title");
		country = "" + request.getParameter("country");
		state = "" + request.getParameter("state");
		product_id = "" + request.getParameter("product_id");
		product_group = "" + request.getParameter("product_group");
		product_type = "" + request.getParameter("product_type");
		product_weight_from = "" + request.getParameter("product_weight_from");
		product_weight_to = "" + request.getParameter("product_weight_to");
		product_volume_from = "" + request.getParameter("product_volume_from");
		product_volume_to = "" + request.getParameter("product_volume_to");
		product_width_from = "" + request.getParameter("product_width_from");
		product_width_to = "" + request.getParameter("product_width_to");
		product_height_from = "" + request.getParameter("product_height_from");
		product_height_to = "" + request.getParameter("product_height_to");
		product_depth_from = "" + request.getParameter("product_depth_from");
		product_depth_to = "" + request.getParameter("product_depth_to");
		quantity_from = "" + request.getParameter("quantity_from");
		quantity_to = "" + request.getParameter("quantity_to");
		total_currency = "" + request.getParameter("total_currency");
		total_from = "" + request.getParameter("total_from");
		total_to = "" + request.getParameter("total_to");
		total_weight_from = "" + request.getParameter("total_weight_from");
		total_weight_to = "" + request.getParameter("total_weight_to");
		total_volume_from = "" + request.getParameter("total_volume_from");
		total_volume_to = "" + request.getParameter("total_volume_to");
		total_width_from = "" + request.getParameter("total_width_from");
		total_width_to = "" + request.getParameter("total_width_to");
		total_height_from = "" + request.getParameter("total_height_from");
		total_height_to = "" + request.getParameter("total_height_to");
		total_depth_from = "" + request.getParameter("total_depth_from");
		total_depth_to = "" + request.getParameter("total_depth_to");
		ship_description = "" + request.getParameter("ship_description");
		ship_currency = "" + request.getParameter("ship_currency");
		ship_order = "" + request.getParameter("ship_order");
		ship_item = "" + request.getParameter("ship_item");
		ship_percent = "" + request.getParameter("ship_percent");
		ship_total = "" + request.getParameter("ship_total");
	}



	public void create(DB db) {
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("title", "" + title);
		mydata.put("country", "" + country);
		mydata.put("state", "" + state);
		mydata.put("product_id", "" + product_id);
		mydata.put("product_group", "" + product_group);
		mydata.put("product_type", "" + product_type);
		mydata.put("product_weight_from", "" + Common.number(product_weight_from));
		mydata.put("product_weight_to", "" + Common.number(product_weight_to));
		mydata.put("product_volume_from", "" + Common.number(product_volume_from));
		mydata.put("product_volume_to", "" + Common.number(product_volume_to));
		mydata.put("product_width_from", "" + Common.number(product_width_from));
		mydata.put("product_width_to", "" + Common.number(product_width_to));
		mydata.put("product_height_from", "" + Common.number(product_height_from));
		mydata.put("product_height_to", "" + Common.number(product_height_to));
		mydata.put("product_depth_from", "" + Common.number(product_depth_from));
		mydata.put("product_depth_to", "" + Common.number(product_depth_to));
		mydata.put("quantity_from", "" + Common.number(quantity_from));
		mydata.put("quantity_to", "" + Common.number(quantity_to));
		mydata.put("total_currency", "" + total_currency);
		mydata.put("total_from", "" + Common.number(total_from));
		mydata.put("total_to", "" + Common.number(total_to));
		mydata.put("total_weight_from", "" + Common.number(total_weight_from));
		mydata.put("total_weight_to", "" + Common.number(total_weight_to));
		mydata.put("total_volume_from", "" + Common.number(total_volume_from));
		mydata.put("total_volume_to", "" + Common.number(total_volume_to));
		mydata.put("total_width_from", "" + Common.number(total_width_from));
		mydata.put("total_width_to", "" + Common.number(total_width_to));
		mydata.put("total_height_from", "" + Common.number(total_height_from));
		mydata.put("total_height_to", "" + Common.number(total_height_to));
		mydata.put("total_depth_from", "" + Common.number(total_depth_from));
		mydata.put("total_depth_to", "" + Common.number(total_depth_to));
		mydata.put("ship_description", "" + ship_description);
		mydata.put("ship_currency", "" + ship_currency);
		mydata.put("ship_order", "" + Common.number(ship_order));
		mydata.put("ship_item", "" + Common.number(ship_item));
		mydata.put("ship_percent", "" + Common.number(ship_percent));
		mydata.put("ship_total", "" + Common.number(ship_total));
		db.insert("shipping", mydata);
	}



	public void update(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("title", "" + title);
			mydata.put("country", "" + country);
			mydata.put("state", "" + state);
			mydata.put("product_id", "" + product_id);
			mydata.put("product_group", "" + product_group);
			mydata.put("product_type", "" + product_type);
			mydata.put("product_weight_from", "" + Common.number(product_weight_from));
			mydata.put("product_weight_to", "" + Common.number(product_weight_to));
			mydata.put("product_volume_from", "" + Common.number(product_volume_from));
			mydata.put("product_volume_to", "" + Common.number(product_volume_to));
			mydata.put("product_width_from", "" + Common.number(product_width_from));
			mydata.put("product_width_to", "" + Common.number(product_width_to));
			mydata.put("product_height_from", "" + Common.number(product_height_from));
			mydata.put("product_height_to", "" + Common.number(product_height_to));
			mydata.put("product_depth_from", "" + Common.number(product_depth_from));
			mydata.put("product_depth_to", "" + Common.number(product_depth_to));
			mydata.put("quantity_from", "" + Common.number(quantity_from));
			mydata.put("quantity_to", "" + Common.number(quantity_to));
			mydata.put("total_currency", "" + total_currency);
			mydata.put("total_from", "" + Common.number(total_from));
			mydata.put("total_to", "" + Common.number(total_to));
			mydata.put("total_weight_from", "" + Common.number(total_weight_from));
			mydata.put("total_weight_to", "" + Common.number(total_weight_to));
			mydata.put("total_volume_from", "" + Common.number(total_volume_from));
			mydata.put("total_volume_to", "" + Common.number(total_volume_to));
			mydata.put("total_width_from", "" + Common.number(total_width_from));
			mydata.put("total_width_to", "" + Common.number(total_width_to));
			mydata.put("total_height_from", "" + Common.number(total_height_from));
			mydata.put("total_height_to", "" + Common.number(total_height_to));
			mydata.put("total_depth_from", "" + Common.number(total_depth_from));
			mydata.put("total_depth_to", "" + Common.number(total_depth_to));
			mydata.put("ship_description", "" + ship_description);
			mydata.put("ship_currency", "" + ship_currency);
			mydata.put("ship_order", "" + Common.number(ship_order));
			mydata.put("ship_item", "" + Common.number(ship_item));
			mydata.put("ship_percent", "" + Common.number(ship_percent));
			mydata.put("ship_total", "" + Common.number(ship_total));
			db.update("shipping", "id", id, mydata);
		}
	}



	public void delete(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			db.delete("shipping", "id", id);
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



	public String currency_options(DB db, String selected) {
		Currency currency = new Currency();
		return currency.currency_options(db, selected);
	}



	public String currency_symbol(DB db, String id) {
		if (db == null) return "";
		String symbol = "";
		if ((id != null) && (! id.equals(""))) {
			Currency currency = new Currency();
			currency.read(db, id);
			symbol = currency.getSymbol();
		}
		return symbol;
	}



	public String product_title(DB db, String id) {
		if (db == null) return "";
		String my_product_title = "";
		if ((id != null) && (! id.equals(""))) {
			String SQL = "select title from content where id=" + Common.integer(id);
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				my_product_title = "" + row.get("title");
			}
		}
		return my_product_title;
	}



	public String product_options(DB db, String selected) {
		return dummy_page.select_options(db, "product", selected);
	}



	public String productgroup_options(DB db, String selected) {
		return Common.select_options(db, "productgroups", "productgroup", selected);
	}



	public String producttype_options(DB db, String selected) {
		return Common.select_options(db, "producttypes", "producttype", selected);
	}



	public String getId() {
		return id;
	}
	public void setId(String value) {
		id = value;
	}



	public String getTitle() {
		return title;
	}
	public void setTitle(String value) {
		title = value;
	}



	public String getCountry() {
		return country;
	}
	public void setCountry(String value) {
		country = value;
	}



	public String getState() {
		return state;
	}
	public void setState(String value) {
		state = value;
	}



	public String getProductId() {
		return product_id;
	}
	public void setProductId(String value) {
		product_id = value;
	}



	public String getProductGroup() {
		return product_group;
	}
	public void setProductGroup(String value) {
		product_group = value;
	}



	public String getProductType() {
		return product_type;
	}
	public void setProductType(String value) {
		product_type = value;
	}



	public String getProductWeightFrom() {
		return product_weight_from;
	}
	public void setProductWeightFrom(String value) {
		product_weight_from = value;
	}



	public String getProductWeightTo() {
		return product_weight_to;
	}
	public void setProductWeightTo(String value) {
		product_weight_to = value;
	}



	public String getProductVolumeFrom() {
		return product_volume_from;
	}
	public void setProductVolumeFrom(String value) {
		product_volume_from = value;
	}



	public String getProductVolumeTo() {
		return product_volume_to;
	}
	public void setProductVolumeTo(String value) {
		product_volume_to = value;
	}



	public String getProductWidthFrom() {
		return product_width_from;
	}
	public void setProductWidthFrom(String value) {
		product_width_from = value;
	}



	public String getProductWidthTo() {
		return product_width_to;
	}
	public void setProductWidthTo(String value) {
		product_width_to = value;
	}



	public String getProductHeightFrom() {
		return product_height_from;
	}
	public void setProductHeightFrom(String value) {
		product_height_from = value;
	}



	public String getProductHeightTo() {
		return product_height_to;
	}
	public void setProductHeightTo(String value) {
		product_height_to = value;
	}



	public String getProductDepthFrom() {
		return product_depth_from;
	}
	public void setProductDepthFrom(String value) {
		product_depth_from = value;
	}



	public String getProductDepthTo() {
		return product_depth_to;
	}
	public void setProductDepthTo(String value) {
		product_depth_to = value;
	}



	public String getQuantityFrom() {
		return quantity_from;
	}
	public void setQuantityFrom(String value) {
		quantity_from = value;
	}



	public String getQuantityTo() {
		return quantity_to;
	}
	public void setQuantityTo(String value) {
		quantity_to = value;
	}



	public String getTotalCurrency() {
		return total_currency;
	}
	public void setTotalCurrency(String value) {
		total_currency = value;
	}



	public String getTotalFrom() {
		return total_from;
	}
	public void setTotalFrom(String value) {
		total_from = value;
	}



	public String getTotalTo() {
		return total_to;
	}
	public void setTotalTo(String value) {
		total_to = value;
	}



	public String getTotalWeightFrom() {
		return total_weight_from;
	}
	public void setTotalWeightFrom(String value) {
		total_weight_from = value;
	}



	public String getTotalWeightTo() {
		return total_weight_to;
	}
	public void setTotalWeightTo(String value) {
		total_weight_to = value;
	}



	public String getTotalVolumeFrom() {
		return total_volume_from;
	}
	public void setTotalVolumeFrom(String value) {
		total_volume_from = value;
	}



	public String getTotalVolumeTo() {
		return total_volume_to;
	}
	public void setTotalVolumeTo(String value) {
		total_volume_to = value;
	}



	public String getTotalWidthFrom() {
		return total_width_from;
	}
	public void setTotalWidthFrom(String value) {
		total_width_from = value;
	}



	public String getTotalWidthTo() {
		return total_width_to;
	}
	public void setTotalWidthTo(String value) {
		total_width_to = value;
	}



	public String getTotalHeightFrom() {
		return total_height_from;
	}
	public void setTotalHeightFrom(String value) {
		total_height_from = value;
	}



	public String getTotalHeightTo() {
		return total_height_to;
	}
	public void setTotalHeightTo(String value) {
		total_height_to = value;
	}



	public String getTotalDepthFrom() {
		return total_depth_from;
	}
	public void setTotalDepthFrom(String value) {
		total_depth_from = value;
	}



	public String getTotalDepthTo() {
		return total_depth_to;
	}
	public void setTotalDepthTo(String value) {
		total_depth_to = value;
	}



	public String getShipDescription() {
		return ship_description;
	}
	public void setShipDescription(String value) {
		ship_description = value;
	}



	public String getShipCurrency() {
		return ship_currency;
	}
	public void setShipCurrency(String value) {
		ship_currency = value;
	}



	public String getShipOrder() {
		return ship_order;
	}
	public void setShipOrder(String value) {
		ship_order = value;
	}



	public String getShipItem() {
		return ship_item;
	}
	public void setShipItem(String value) {
		ship_item = value;
	}



	public String getShipPercent() {
		return ship_percent;
	}
	public void setShipPercent(String value) {
		ship_percent = value;
	}



	public String getShipTotal() {
		return ship_total;
	}
	public void setShipTotal(String value) {
		ship_total = value;
	}



}
