package HardCore;

import java.sql.*;
import java.util.HashMap;
import java.util.regex.*;

public class Currency {
	private String id = "";
	private String title = "";
	private String symbol = "";
	private String rate = "";

	private	Statement rs = null;



	public Currency() {
		init();
	}



	public void init() {
		id = "";
		title = "";
		symbol = "";
		rate = "";
	}



	public void read(DB db, String id) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			String SQL = "select * from currencies where id=" + Common.integer(id);
@SuppressWarnings("unchecked")
			HashMap<String,String> row = (HashMap<String,String>)Cache.get(db, "currencies", "id:" + Common.integer(id));
			if (row == null) {
				row = db.query_record(SQL);
				if (row == null) row = new HashMap<String,String>();
				if (row != null) Cache.set(db, "currencies", "id:" + Common.integer(id), row);
			}
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void readSymbol(DB db, String symbol) {
		if (db == null) return;
		if ((symbol != null) && (! symbol.equals(""))) {
			String SQL = "select * from currencies where symbol='" + Common.SQL_clean(symbol) + "' or symbol='" + html.encodeHtmlEntities(symbol) + "' or symbol='" + html.encodeHtmlCharCodes(symbol) + "' or symbol=" + db.quote(html.decodeHtmlEntities(symbol)) + " order by id";
@SuppressWarnings("unchecked")
			HashMap<String,String> row = (HashMap<String,String>)Cache.get(db, "currencies", "symbol:" + Common.SQL_clean(symbol));
			if (row == null) {
				row = db.query_record(SQL);
				if (row == null) row = new HashMap<String,String>();
				if (row != null) Cache.set(db, "currencies", "symbol:" + Common.SQL_clean(symbol), row);
			}
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void getRecord(DB db, HashMap<String,String> record) {
		id = "" + record.get("id");
		title = "" + record.get("title");
		symbol = "" + record.get("symbol");
		rate = "" + record.get("rate");
	}



	public void getForm(Request request) {
		title = "" + request.getParameter("title");
		symbol = "" + request.getParameter("symbol");
		rate = "" + request.getParameter("rate");
	}



	public void create(DB db) {
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("title", "" + title);
		mydata.put("symbol", "" + symbol);
		mydata.put("rate", "" + rate);
		db.insert("currencies", mydata);
		Cache.clear(db, "currencies");
	}



	public void update(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("title", "" + title);
			mydata.put("symbol", "" + symbol);
			mydata.put("rate", "" + rate);
			db.update("currencies", "id", id, mydata);
			Cache.clear(db, "currencies");
		}
	}



	public void delete(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			db.delete("currencies", "id", id);
			Cache.clear(db, "currencies");
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
		return Common.select_options(db, "currencies", "symbol", "id", selected);
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



	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String value) {
		symbol = value;
	}



	public String getRate() {
		return rate;
	}
	public void setRate(String value) {
		rate = value;
	}



}
