package HardCore;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.regex.*;

public class UCreportOrders {
	private String SQLselectCustomers = "select count(distinct orders.user_id, orders.sessionid) as countcustomers";
	private String SQLselectOrders = "select count(distinct orders.id) as countorders";
	private String SQLselectOrderitems = "select count(*) as countorderitems";
	private String SQLselectItemQuantity = "select sum(orderitems.item_quantity) as sumitemquantity";
	private String SQLselectItemTotal = "select sum(orderitems.item_total) as sumitemtotal";
	private String SQLselectItemTotalBase = "select sum(orderitems.item_total_base) as sumitemtotal";

	private String SQLwherePage = "requestclass in ('page','product','guestbook','data')";

	private String SQLcolumnsDaily = "orders.createdyear, orders.createdmonth, orders.createdday";
	private String SQLcolumnsWeekly = "createdyear, createdweek";
	private String SQLcolumnsMonthly = "createdyear, createdmonth";
	private String SQLcolumnsYearly = "createdyear";
	private String SQLcolumnsHours = "createdhour";
	private String SQLcolumnsDays = "createdday";
	private String SQLcolumnsWeekdays = "createdweekday";
	private String SQLcolumnsWeeks = "createdweek";
	private String SQLcolumnsMonths = "createdmonth";
	private String SQLcolumnsWebsites = "requesthost";
	private String SQLcolumnsVisitors = "clienthost";
	private String SQLcolumnsVisitor = "clienthost,sessionid";
	private String SQLcolumnsUsername = "clienthost,sessionid";
	private String SQLcolumnsVisits = "clienthost,sessionid";
	private String SQLcolumnsCountries = "clienthosttld";
	private String SQLcolumnsWebBrowsers = "clientbrowser";
	private String SQLcolumnsWebBrowserVersions = "clientbrowser, clientversion";
	private String SQLcolumnsDevices = "clientdevice";
	private String SQLcolumnsDeviceVersions = "clientdevice, clientdeviceversion";
	private String SQLcolumnsOperatingSystems = "clientos";
	private String SQLcolumnsOperatingSystemVersions = "clientos, clientosversion";
	private String SQLcolumnsUsers = "user_id";
	private String SQLcolumnsUsergroups = "users.usergroup as usergroup";
	private String SQLcolumnsUsertypes = "users.usertype as usertype";
	private String SQLcolumnsAffiliates = "affiliate";
	private String SQLcolumnsReferers = "refererhost";
	private String SQLcolumnsRefererPages = "refererhost, refererpath";
	private String SQLcolumnsSearchEngines = "referersearchengine";
	private String SQLcolumnsSearchQueries = "referersearchquery";
	private String SQLcolumnsEntry = "session_entry";
	private String SQLcolumnsExit = "requestid, requestclass, requestdatabase";
	private String SQLcolumnsExitMySQL = "q1.requestid as requestid, q1.requestclass as requestclass, q1.requestdatabase as requestdatabase";
	private String SQLcolumnsPaths = "requestid, requestclass, requestdatabase, refererid, refererclass, refererdatabase";
	private String SQLcolumnsProducts = "product_id";
	private String SQLcolumnsProductgroups = "contentgroup";
	private String SQLcolumnsProducttypes = "contenttype";
	private String SQLcolumnsVariants = ", usersegments, usertests";
	private Text text = new Text();



	public UCreportOrders() {
	}



	public UCreportOrders(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public boolean getAccess(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		return initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
	}



	private boolean initRequest(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return false;
		if (! myconfig.get(db, "statistics_admin_users_type").equals("")) {
			accesspermission = RequireUser.AdministratorUsertype(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myconfig.get(db, "statistics_admin_users_type"), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
			if (! accesspermission) return false;
		}
		if (! myconfig.get(db, "statistics_admin_users_group").equals("")) {
			accesspermission = RequireUser.AdministratorUsergroup(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myconfig.get(db, "statistics_admin_users_group"), mysession.get("usergroup"), mysession.get("usergroups"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
			if (! accesspermission) return false;
		}
		UsageLog usagelog = new UsageLog();
		usagelog.clean(logdb, myconfig.get(db, "log_period"));

		setSessionPeriodFromRequest(mysession, myrequest);
		setSessionLimitFromRequest(mysession, myrequest);
		setSessionCurrencyFromRequest(mysession, myrequest);
		return true;
	}



	public HashMap getSummary(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,String>();
		HashMap<String,String> usage = new HashMap<String,String>();
		Date mynow = new Date();
		String SQLwhere;
		SQLwhere = SQLwherePeriod(db, mysession, mynow, "");

		if ((! mysession.get("admin_sales_currency").equals("")) && (! mysession.get("admin_sales_currency").equals("*"))) {
			Currency mycurrency = new Currency();	
			mycurrency.read(db, mysession.get("admin_sales_currency"));
			SQLwhere += " and orders.order_currency=" + db.quote(Common.SQL_clean(mycurrency.getSymbol()));
		}

		String SQL = "";
		String SQL2 = "";
		if (db.db_type(database).equals("mysql")) {
			// MySQL v3.3-v4.0 does not support sub-selects
//			SQL = "select count(distinct user_id, sessionid) from orders where " + SQLwhere;
			SQL = "select count(distinct user_id) from orders where (user_id is not null and user_id>0) and " + SQLwhere;
			SQL2 = "select count(distinct sessionid) from orders where (user_id is null or user_id=0) and " + SQLwhere;
		} else if (db.db_type(database).equals("oracle")) {
			// Oracle does not support naming of sub-selects
//			SQL = "select count(*) from (select distinct user_id, sessionid from orders where " + SQLwhere + ")";
			SQL = "select count(*) from (select distinct user_id from orders where (user_id is not null and user_id>0) and " + SQLwhere + ")";
			SQL2 = "select count(*) from (select distinct sessionid from orders where (user_id is null or user_id=0) and " + SQLwhere + ")";
		} else {
			// Microsoft SQL Server requires naming of sub-selects
//			SQL = "select count(*) from (select distinct user_id, sessionid from orders where " + SQLwhere + ") as user_id";
			SQL = "select count(*) from (select distinct user_id from orders where (user_id is not null and user_id>0) and " + SQLwhere + ") as user_id";
			SQL2 = "select count(*) from (select distinct sessionid from orders where (user_id is null or user_id=0) and " + SQLwhere + ") as user_id";
		}
		usage.put("countcustomers", "" + (db.query_value(SQL) + db.query_value(SQL2)));

		SQL = "select count(*) from orders where " + SQLwhere;
		usage.put("countorders", "" + db.query_value(SQL));

		SQL = "select count(*) from orders, orderitems where orders.id=orderitems.order_id and " + SQLwhere;
		usage.put("countorderitems", "" + db.query_value(SQL));

		SQL = "select sum(orderitems.item_quantity) from orders, orderitems where orders.id=orderitems.order_id and " + SQLwhere;
		usage.put("sumitemquantity", "" + db.query_value(SQL));

		if ((mysession.get("admin_sales_currency").equals("")) || (mysession.get("admin_sales_currency").equals("*"))) {
			SQL = "select sum(orderitems.item_total_base) from orders, orderitems where orders.id=orderitems.order_id and " + SQLwhere;
			usage.put("sumitemtotal", "" + db.query_value(SQL));

			SQL = "select sum(orders.order_subtotal_base) from orders where " + SQLwhere;
			usage.put("sumordersubtotal", "" + db.query_value(SQL));

			SQL = "select sum(orders.discount_total_base) from orders where " + SQLwhere;
			usage.put("sumorderdiscounttotal", "" + db.query_value(SQL));

			SQL = "select sum(orders.tax_total_base) from orders where " + SQLwhere;
			usage.put("sumordertaxtotal", "" + db.query_value(SQL));

			SQL = "select sum(orders.shipping_total_base) from orders where " + SQLwhere;
			usage.put("sumordershippingtotal", "" + db.query_value(SQL));

			SQL = "select sum(orders.order_total_base) from orders where " + SQLwhere;
			usage.put("sumordertotal", "" + db.query_value(SQL));
		} else {
			SQL = "select sum(orderitems.item_total) from orders, orderitems where orders.id=orderitems.order_id and " + SQLwhere;
			usage.put("sumitemtotal", "" + db.query_value(SQL));

			SQL = "select sum(orders.order_subtotal) from orders where " + SQLwhere;
			usage.put("sumordersubtotal", "" + db.query_value(SQL));

			SQL = "select sum(orders.discount_total) from orders where " + SQLwhere;
			usage.put("sumorderdiscounttotal", "" + db.query_value(SQL));

			SQL = "select sum(orders.tax_total) from orders where " + SQLwhere;
			usage.put("sumordertaxtotal", "" + db.query_value(SQL));

			SQL = "select sum(orders.shipping_total) from orders where " + SQLwhere;
			usage.put("sumordershippingtotal", "" + db.query_value(SQL));

			SQL = "select sum(orders.order_total) from orders where " + SQLwhere;
			usage.put("sumordertotal", "" + db.query_value(SQL));
		}
		return usage;
	}



	public HashMap<String,HashMap<String,String>> getDaily(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,HashMap<String,String>>();
		Date mynow = new Date();
		return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, periodDays(mysession, mynow), SQLcolumnsDaily, "daily", "yyyy-MM-dd", "%04d-%02d-%02d", "createdyear,createdmonth,createdday");
	}



	public HashMap<String,HashMap<String,String>> getWeekly(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,HashMap<String,String>>();
		Date mynow = new Date();
		return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, periodWeeks(mysession, mynow), SQLcolumnsWeekly, "weekly", "yyyy-ww", "%04d-%02d", "createdyear,createdweek");
	}



	public HashMap<String,HashMap<String,String>> getMonthly(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,HashMap<String,String>>();
		Date mynow = new Date();
		return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, periodMonths(mysession, mynow), SQLcolumnsMonthly, "monthly", "yyyy-MM", "%04d-%02d", "createdyear,createdmonth");
	}



	public HashMap<String,HashMap<String,String>> getYearly(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,HashMap<String,String>>();
		Date mynow = new Date();
		return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, periodYears(mysession, mynow), SQLcolumnsYearly, "yearly", "yyyy", "%04d", "createdyear");
	}



	public HashMap<String,HashMap<String,String>> getHours(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,HashMap<String,String>>();
		Date mynow = new Date();
		return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allHours(mysession, mynow), SQLcolumnsHours, "hours", "%02d", "%02d", "createdhour");
	}



	public HashMap<String,HashMap<String,String>> getDays(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,HashMap<String,String>>();
		Date mynow = new Date();
		return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allDays(mysession, mynow), SQLcolumnsDays, "days", "%02d", "%02d", "createdday");
	}



	public HashMap<String,HashMap<String,String>> getWeekdays(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,HashMap<String,String>>();
		Date mynow = new Date();
		return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allWeekdays(mysession, mynow), SQLcolumnsWeekdays, "weekdays", "%01d", "%01d", "createdweekday");
	}



	public HashMap<String,HashMap<String,String>> getWeeks(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,HashMap<String,String>>();
		Date mynow = new Date();
		return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allWeeks(mysession, mynow), SQLcolumnsWeeks, "weeks", "%02d", "%02d", "createdweek");
	}



	public HashMap<String,HashMap<String,String>> getMonths(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,HashMap<String,String>>();
		Date mynow = new Date();
		return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allMonths(mysession, mynow), SQLcolumnsMonths, "months", "%02d", "%02d", "createdmonth");
	}



	private HashMap<String,HashMap<String,String>> getPeriodData(Session mysession, Request myrequest, Configuration myconfig, DB db, DB logdb, String database, Date mynow, Long[] periods, String SQLcolumns, String prefix, String periodtimestamp, String datatimestamp, String keys) {
		HashMap<String,HashMap<String,String>> usage = new HashMap<String,HashMap<String,String>>();

		String SQLwhere = SQLwherePeriod(db, mysession, mynow, "");

		if ((! mysession.get("admin_sales_currency").equals("")) && (! mysession.get("admin_sales_currency").equals("*"))) {
			Currency mycurrency = new Currency();	
			mycurrency.read(db, mysession.get("admin_sales_currency"));
			SQLwhere += " and orders.order_currency=" + db.quote(Common.SQL_clean(mycurrency.getSymbol()));
		}

		String SQL = "";
		String[] keyparts;
		String timestamp = "";

		SQL = SQLcustomers(database, SQLcolumns, "orders", SQLwhere, "");
		HashMap<String,HashMap<String,String>> countcustomers = db.query_records(SQL);
		usage.put(prefix + "countcustomers", new HashMap<String,String>());
		for (int period=0; period<periods.length; period++) {
			timestamp = Common.DateFormat(periodtimestamp, periods[period].longValue());
			((HashMap<String,String>)usage.get(prefix + "countcustomers")).put(timestamp, "0");
		}
		Iterator mycountcustomers = countcustomers.keySet().iterator();
		while (mycountcustomers.hasNext()) {
			String countcustomer = "" + mycountcustomers.next();
			keyparts = keys.split(",");
			if (keyparts.length == 3) {
				String[] params = { "" + ((HashMap<String,String>)countcustomers.get(countcustomer)).get(keyparts[0]), "" + ((HashMap<String,String>)countcustomers.get(countcustomer)).get(keyparts[1]), "" + ((HashMap<String,String>)countcustomers.get(countcustomer)).get(keyparts[2]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 2) {
				String[] params = { "" + ((HashMap<String,String>)countcustomers.get(countcustomer)).get(keyparts[0]), "" + ((HashMap<String,String>)countcustomers.get(countcustomer)).get(keyparts[1]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 1) {
				String[] params = { "" + ((HashMap<String,String>)countcustomers.get(countcustomer)).get(keyparts[0]) };
				timestamp = Common.sprintf(datatimestamp, params);
			}
			((HashMap<String,String>)usage.get(prefix + "countcustomers")).put(timestamp, "" + ((HashMap<String,String>)countcustomers.get(countcustomer)).get("countcustomers"));
		}

		SQL = SQLorders(database, SQLcolumns, "orders", SQLwhere, "");
		HashMap<String,HashMap<String,String>> countorders = db.query_records(SQL);
		usage.put(prefix + "countorders", new HashMap<String,String>());
		for (int period=0; period<periods.length; period++) {
			timestamp = Common.DateFormat(periodtimestamp, periods[period].longValue());
			((HashMap<String,String>)usage.get(prefix + "countorders")).put(timestamp, "0");
		}
		Iterator mycountorders = countorders.keySet().iterator();
		while (mycountorders.hasNext()) {
			String countorder = "" + mycountorders.next();
			keyparts = keys.split(",");
			if (keyparts.length == 3) {
				String[] params = { "" + ((HashMap<String,String>)countorders.get(countorder)).get(keyparts[0]), "" + ((HashMap<String,String>)countorders.get(countorder)).get(keyparts[1]), "" + ((HashMap<String,String>)countorders.get(countorder)).get(keyparts[2]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 2) {
				String[] params = { "" + ((HashMap<String,String>)countorders.get(countorder)).get(keyparts[0]), "" + ((HashMap<String,String>)countorders.get(countorder)).get(keyparts[1]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 1) {
				String[] params = { "" + ((HashMap<String,String>)countorders.get(countorder)).get(keyparts[0]) };
				timestamp = Common.sprintf(datatimestamp, params);
			}
			((HashMap<String,String>)usage.get(prefix + "countorders")).put(timestamp, "" + ((HashMap<String,String>)countorders.get(countorder)).get("countorders"));
		}

		SQL = SQLorderitems(database, SQLcolumns, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLwhere, "");
		HashMap<String,HashMap<String,String>> countorderitems = db.query_records(SQL);
		usage.put(prefix + "countorderitems", new HashMap<String,String>());
		for (int period=0; period<periods.length; period++) {
			timestamp = Common.DateFormat(periodtimestamp, periods[period].longValue());
			((HashMap<String,String>)usage.get(prefix + "countorderitems")).put(timestamp, "0");
		}
		Iterator mycountorderitems = countorderitems.keySet().iterator();
		while (mycountorderitems.hasNext()) {
			String countorderitem = "" + mycountorderitems.next();
			keyparts = keys.split(",");
			if (keyparts.length == 3) {
				String[] params = { "" + ((HashMap<String,String>)countorderitems.get(countorderitem)).get(keyparts[0]), "" + ((HashMap<String,String>)countorderitems.get(countorderitem)).get(keyparts[1]), "" + ((HashMap<String,String>)countorderitems.get(countorderitem)).get(keyparts[2]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 2) {
				String[] params = { "" + ((HashMap<String,String>)countorderitems.get(countorderitem)).get(keyparts[0]), "" + ((HashMap<String,String>)countorderitems.get(countorderitem)).get(keyparts[1]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 1) {
				String[] params = { "" + ((HashMap<String,String>)countorderitems.get(countorderitem)).get(keyparts[0]) };
				timestamp = Common.sprintf(datatimestamp, params);
			}
			((HashMap<String,String>)usage.get(prefix + "countorderitems")).put(timestamp, "" + ((HashMap<String,String>)countorderitems.get(countorderitem)).get("countorderitems"));
		}

		SQL = SQLitemquantity(database, SQLcolumns, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLwhere, "");
		HashMap<String,HashMap<String,String>> countquantities = db.query_records(SQL);
		usage.put(prefix + "sumitemquantity", new HashMap<String,String>());
		for (int period=0; period<periods.length; period++) {
			timestamp = Common.DateFormat(periodtimestamp, periods[period].longValue());
			((HashMap<String,String>)usage.get(prefix + "sumitemquantity")).put(timestamp, "0");
		}
		Iterator mycountquantities = countquantities.keySet().iterator();
		while (mycountquantities.hasNext()) {
			String countquantity = "" + mycountquantities.next();
			keyparts = keys.split(",");
			if (keyparts.length == 3) {
				String[] params = { "" + ((HashMap<String,String>)countquantities.get(countquantity)).get(keyparts[0]), "" + ((HashMap<String,String>)countquantities.get(countquantity)).get(keyparts[1]), "" + ((HashMap<String,String>)countquantities.get(countquantity)).get(keyparts[2]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 2) {
				String[] params = { "" + ((HashMap<String,String>)countquantities.get(countquantity)).get(keyparts[0]), "" + ((HashMap<String,String>)countquantities.get(countquantity)).get(keyparts[1]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 1) {
				String[] params = { "" + ((HashMap<String,String>)countquantities.get(countquantity)).get(keyparts[0]) };
				timestamp = Common.sprintf(datatimestamp, params);
			}
			if (! timestamp.equals("0000-00-00")) {
				((HashMap<String,String>)usage.get(prefix + "sumitemquantity")).put(timestamp, "" + ((HashMap<String,String>)countquantities.get(countquantity)).get("sumitemquantity"));
			}
		}

		SQL = SQLitemtotal(mysession, database, SQLcolumns, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLwhere, "");
		HashMap<String,HashMap<String,String>> countitemtotals = db.query_records(SQL);
		usage.put(prefix + "sumitemtotal", new HashMap<String,String>());
		for (int period=0; period<periods.length; period++) {
			timestamp = Common.DateFormat(periodtimestamp, periods[period].longValue());
			((HashMap<String,String>)usage.get(prefix + "sumitemtotal")).put(timestamp, "0");
		}
		Iterator mycountitemtotals = countitemtotals.keySet().iterator();
		while (mycountitemtotals.hasNext()) {
			String countitemtotal = "" + mycountitemtotals.next();
			keyparts = keys.split(",");
			if (keyparts.length == 3) {
				String[] params = { "" + ((HashMap<String,String>)countitemtotals.get(countitemtotal)).get(keyparts[0]), "" + ((HashMap<String,String>)countitemtotals.get(countitemtotal)).get(keyparts[1]), "" + ((HashMap<String,String>)countitemtotals.get(countitemtotal)).get(keyparts[2]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 2) {
				String[] params = { "" + ((HashMap<String,String>)countitemtotals.get(countitemtotal)).get(keyparts[0]), "" + ((HashMap<String,String>)countitemtotals.get(countitemtotal)).get(keyparts[1]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 1) {
				String[] params = { "" + ((HashMap<String,String>)countitemtotals.get(countitemtotal)).get(keyparts[0]) };
				timestamp = Common.sprintf(datatimestamp, params);
			}
			if (! timestamp.equals("0000-00-00")) {
				((HashMap<String,String>)usage.get(prefix + "sumitemtotal")).put(timestamp, "" + ((HashMap<String,String>)countitemtotals.get(countitemtotal)).get("sumitemtotal"));
			}
		}

		return usage;
	}



	private HashMap<String,LinkedHashMap<String,String>> getRankedData(Session mysession, Request myrequest, Configuration myconfig, DB db, DB logdb, String database, String contentSQLtables, String contentSQLcolumns, String contentSQLwhere, String contentSQLorder, String prefix, String data, String keys, boolean lowercase_key) {
		HashMap<String,LinkedHashMap<String,String>> usage = new HashMap<String,LinkedHashMap<String,String>>();
		String SQL = "";
		Date mynow = new Date();
		String SQLwhere = SQLwherePeriod(db, mysession, mynow, "");
		if ((! mysession.get("admin_sales_currency").equals("")) && (! mysession.get("admin_sales_currency").equals("*"))) {
			Currency mycurrency = new Currency();	
			mycurrency.read(db, mysession.get("admin_sales_currency"));
			SQLwhere += " and orders.order_currency=" + db.quote(Common.SQL_clean(mycurrency.getSymbol()));
		}
		String[] keyparts;
		String timestamp = "";
		boolean countusersegments = (data.indexOf("countusersegments") > -1);
		boolean countusertests = (data.indexOf("countusertests") > -1);

		if (data.indexOf("countcustomers") > -1) {
			SQL = SQLcustomers(database, contentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
			usage = getRankedDataParts(usage, prefix, "countcustomers", keys, lowercase_key, countusersegments, countusertests, db, SQL);
		}

		if (data.indexOf("countorders") > -1) {
			SQL = SQLorders(database, contentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
			usage = getRankedDataParts(usage, prefix, "countorders", keys, lowercase_key, countusersegments, countusertests, db, SQL);
		}

		if (data.indexOf("countorderitems") > -1) {
			SQL = SQLorderitems(database, contentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
			usage = getRankedDataParts(usage, prefix, "countorderitems", keys, lowercase_key, countusersegments, countusertests, db, SQL);
		}

		if (data.indexOf("sumitemquantity") > -1) {
			SQL = SQLitemquantity(database, contentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
			usage = getRankedDataParts(usage, prefix, "sumitemquantity", keys, lowercase_key, countusersegments, countusertests, db, SQL);
		}

		if (data.indexOf("sumitemtotal") > -1) {
			SQL = SQLitemtotal(mysession, database, contentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
			usage = getRankedDataParts(usage, prefix, "sumitemtotal", keys, lowercase_key, countusersegments, countusertests, db, SQL);
		}

		if (data.indexOf("durations") > -1) {
			SQL = SQLvisitsduration(contentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
			usage = getRankedDataParts(usage, prefix, "durations", keys, lowercase_key, countusersegments, countusertests, db, SQL);
//QQQQQ columnname = "durations" but database/query attribute is maxsessionduration
		}

		// clean up empty segment and test data
		if (data.indexOf("counthits") > -1) {
			if (countusersegments && (((LinkedHashMap<String,String>)usage.get(prefix + "counthits" + "segment")).size() <= ((LinkedHashMap<String,String>)usage.get(prefix + "counthits")).size())) {
				usage.remove(prefix + "sumitemtotal" + "segment");
				usage.remove(prefix + "sumitemtotal" + "segments");
				usage.remove(prefix + "sumitemtotal" + "segmentstests");
				usage.remove(prefix + "sumitemquantity" + "segment");
				usage.remove(prefix + "sumitemquantity" + "segments");
				usage.remove(prefix + "sumitemquantity" + "segmentstests");
				usage.remove(prefix + "countorderitems" + "segment");
				usage.remove(prefix + "countorderitems" + "segments");
				usage.remove(prefix + "countorderitems" + "segmentstests");
				usage.remove(prefix + "countorders" + "segment");
				usage.remove(prefix + "countorders" + "segments");
				usage.remove(prefix + "countorders" + "segmentstests");
				usage.remove(prefix + "countcustomers" + "segment");
				usage.remove(prefix + "countcustomers" + "segments");
				usage.remove(prefix + "countcustomers" + "segmentstests");
			}
			if (countusertests && (((LinkedHashMap<String,String>)usage.get(prefix + "counthits" + "test")).size() <= ((LinkedHashMap<String,String>)usage.get(prefix + "counthits")).size())) {
				usage.remove(prefix + "sumitemtotal" + "test");
				usage.remove(prefix + "sumitemtotal" + "tests");
				usage.remove(prefix + "sumitemtotal" + "segmentstests");
				usage.remove(prefix + "sumitemquantity" + "test");
				usage.remove(prefix + "sumitemquantity" + "tests");
				usage.remove(prefix + "sumitemquantity" + "segmentstests");
				usage.remove(prefix + "countorderitems" + "test");
				usage.remove(prefix + "countorderitems" + "tests");
				usage.remove(prefix + "countorderitems" + "segmentstests");
				usage.remove(prefix + "countorders" + "test");
				usage.remove(prefix + "countorders" + "tests");
				usage.remove(prefix + "countorders" + "segmentstests");
				usage.remove(prefix + "countcustomers" + "test");
				usage.remove(prefix + "countcustomers" + "tests");
				usage.remove(prefix + "countcustomers" + "segmentstests");
			}
		}
		return usage;
	}



	HashMap<String,LinkedHashMap<String,String>> getRankedDataParts(HashMap<String,LinkedHashMap<String,String>> usage, String prefix, String columnname, String keys, boolean lowercase_key, boolean countusersegments, boolean countusertests, DB db, String SQL) {
		HashMap<String,HashMap<String,String>> countdata = db.query_records(SQL);
		usage.put(prefix + columnname, new LinkedHashMap<String,String>());
		boolean usersegments_found = false;
		boolean usertests_found = false;
		if (countusersegments) {
			usage.put(prefix + columnname + "segments", new LinkedHashMap<String,String>());
			usage.put(prefix + columnname + "segment", new LinkedHashMap<String,String>());
		}
		if (countusertests) {
			usage.put(prefix + columnname + "tests", new LinkedHashMap<String,String>());
			usage.put(prefix + columnname + "test", new LinkedHashMap<String,String>());
		}
		if (countusersegments && countusertests) {
			usage.put(prefix + columnname + "segmentstests", new LinkedHashMap<String,String>());
		}
		for (int i=0; i<countdata.size(); i++) {
			String mycountdata = "" + i;
			String key = "";
			String[] keyparts = keys.split(":::");
			for (int keypart=0; keypart<keyparts.length; keypart++) {
				key = key + ":::";
				key = key + ((HashMap<String,String>)countdata.get(mycountdata)).get(keyparts[keypart]);
			}
			if (! key.equals("")) key = key.substring(3);
			if (lowercase_key) key = key.toLowerCase();
			((LinkedHashMap<String,String>)usage.get(prefix + columnname)).put(key, "" + (Math.round(Common.number("" + ((LinkedHashMap<String,String>)usage.get(prefix + columnname)).get(key))) + Math.round(Common.number("" + ((HashMap<String,String>)countdata.get(mycountdata)).get(columnname)))));
			if (countusersegments) {
				if (("" + ((HashMap<String,String>)countdata.get(mycountdata)).get("usersegments")).trim().equals("")) {
					String mykey = key;
					((LinkedHashMap<String,String>)usage.get(prefix + columnname + "segment")).put(mykey, "" + (Math.round(Common.number("" + ((LinkedHashMap<String,String>)usage.get(prefix + columnname + "segment")).get(mykey))) + Math.round(Common.number("" + ((HashMap<String,String>)countdata.get(mycountdata)).get(columnname)))));
				} else {
					String[] myusersegments = ("" + ((HashMap<String,String>)countdata.get(mycountdata)).get("usersegments")).split(",");
					for (int j=0; j<myusersegments.length; j++) {
						String[] myusersegment = myusersegments[j].split("=");
						if (myusersegment.length == 2) {
							usersegments_found = true;
							String mysegment = myusersegment[0];
							int myweight = Common.intnumber(myusersegment[1]);
							if (myweight >=0) {
								String mykey = key + ":::" + mysegment;
								((LinkedHashMap<String,String>)usage.get(prefix + columnname + "segment")).put(mykey, "" + (Math.round(Common.number("" + ((LinkedHashMap<String,String>)usage.get(prefix + columnname + "segment")).get(mykey))) + Math.round(Common.number("" + ((HashMap<String,String>)countdata.get(mycountdata)).get(columnname)))));
							} else {
								String mykey = key + ":::" + "%" + mysegment;
								((LinkedHashMap<String,String>)usage.get(prefix + columnname + "segment")).put(mykey, "" + (Math.round(Common.number("" + ((LinkedHashMap<String,String>)usage.get(prefix + columnname + "segment")).get(mykey))) + Math.round(Common.number("" + ((HashMap<String,String>)countdata.get(mycountdata)).get(columnname)))));
							}
						} else {
							usersegments_found = true;
							String mysegment = myusersegment[0];
							String mykey = key + ":::" + mysegment;
							((LinkedHashMap<String,String>)usage.get(prefix + columnname + "segment")).put(mykey, "" + (Math.round(Common.number("" + ((LinkedHashMap<String,String>)usage.get(prefix + columnname + "segment")).get(mykey))) + Math.round(Common.number("" + ((HashMap<String,String>)countdata.get(mycountdata)).get(columnname)))));
						}
					}
				}
			}
			if (countusertests) {
				if ((((HashMap<String,String>)countdata.get(mycountdata)).get("usertests") == null) || (("" + ((HashMap<String,String>)countdata.get(mycountdata)).get("usertests")).trim().equals(""))) {
					String mykey = key;
					((LinkedHashMap<String,String>)usage.get(prefix + columnname + "test")).put(mykey, "" + (Math.round(Common.number("" + ((LinkedHashMap<String,String>)usage.get(prefix + columnname + "test")).get(mykey))) + Math.round(Common.number("" + ((HashMap<String,String>)countdata.get(mycountdata)).get(columnname)))));
				} else {
					String[] myusertests = ("" + ((HashMap<String,String>)countdata.get(mycountdata)).get("usertests")).split(",");
					for (int j=0; j<myusertests.length; j++) {
						String[] myusertest = myusertests[j].split("=");
						if (myusertest.length == 2) {
							usertests_found = true;
							String mytest = myusertest[0];
							String myvariant = myusertest[1];
							String mykey = key + ":::" + mytest + "::" + myvariant;
							((LinkedHashMap<String,String>)usage.get(prefix + columnname + "test")).put(mykey, "" + (Math.round(Common.number("" + ((LinkedHashMap<String,String>)usage.get(prefix + columnname + "test")).get(mykey))) + Math.round(Common.number("" + ((HashMap<String,String>)countdata.get(mycountdata)).get(columnname)))));
						} else if (myusertest.length == 1) {
							usertests_found = true;
							String mytest = myusertest[0];
							String myvariant = "";
							String mykey = key + ":::" + mytest + "::" + myvariant;
							((LinkedHashMap<String,String>)usage.get(prefix + columnname + "test")).put(mykey, "" + (Math.round(Common.number("" + ((LinkedHashMap<String,String>)usage.get(prefix + columnname + "test")).get(mykey))) + Math.round(Common.number("" + ((HashMap<String,String>)countdata.get(mycountdata)).get(columnname)))));
						}
					}
				}
			}
			if (countusersegments) {
				String mysegment = ((HashMap<String,String>)countdata.get(mycountdata)).get("usersegments");
				if (mysegment == null) mysegment = "";
				mysegment = mysegment.replaceAll("(^|,)(.*?)=[0-9]+", "$1$2");
				mysegment = mysegment.replaceAll("(^|,)(.*?)=-[0-9]+", "$1%$2");
				String mykey = key + ":::" + mysegment;
				((LinkedHashMap<String,String>)usage.get(prefix + columnname + "segments")).put(mykey, "" + (Math.round(Common.number("" + ((LinkedHashMap<String,String>)usage.get(prefix + columnname + "segments")).get(mykey))) + Math.round(Common.number("" + ((HashMap<String,String>)countdata.get(mycountdata)).get(columnname)))));
			}
			if (countusertests) {
				String mytest = ((HashMap<String,String>)countdata.get(mycountdata)).get("usertests");
				if (mytest == null) mytest = "";
				String mykey = key + ":::" + mytest;
				((LinkedHashMap<String,String>)usage.get(prefix + columnname + "tests")).put(mykey, "" + (Math.round(Common.number("" + ((LinkedHashMap<String,String>)usage.get(prefix + columnname + "tests")).get(mykey))) + Math.round(Common.number("" + ((HashMap<String,String>)countdata.get(mycountdata)).get(columnname)))));
			}
			if (countusersegments && countusertests) {
				String mysegment = ((HashMap<String,String>)countdata.get(mycountdata)).get("usersegments");
				if (mysegment == null) mysegment = "";
				mysegment = mysegment.replaceAll("(^|,)(.*?)=[0-9]+", "$1$2");
				mysegment = mysegment.replaceAll("(^|,)(.*?)=-[0-9]+", "$1%$2");
				String mytest = "" + ((HashMap<String,String>)countdata.get(mycountdata)).get("usertests");
				String mykey = key + ":::" + mysegment + ":::" + mytest;
				((LinkedHashMap<String,String>)usage.get(prefix + columnname + "segmentstests")).put(mykey, "" + (Math.round(Common.number("" + ((LinkedHashMap<String,String>)usage.get(prefix + columnname + "segmentstests")).get(mykey))) + Math.round(Common.number("" + ((HashMap<String,String>)countdata.get(mycountdata)).get(columnname)))));
			}
		}
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getWebsites(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience")) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsWebsites + SQLcolumnsVariants, "1=1", "", "websites", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal+countusersegments+countusertests", "requesthost", false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsWebsites, "1=1", "", "websites", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "requesthost", false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getCountries(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience")) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsCountries + SQLcolumnsVariants, "1=1", "", "countries", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal+countusersegments+countusertests", "clienthosttld", false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsCountries, "1=1", "", "countries", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "clienthosttld", false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getVisitors(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience")) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsVisitors + SQLcolumnsVariants, "1=1", "", "visitors", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal+countusersegments+countusertests", "clienthost", false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsVisitors, "1=1", "", "visitors", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "clienthost", false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getVisits(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience")) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orderitems", SQLcolumnsVisits + SQLcolumnsVariants, "1=1", SQLcolumnsVisits, "visits", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal+countusersegments+countusertests", "clienthost:::sessionid", false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orderitems", SQLcolumnsVisits, "1=1", SQLcolumnsVisits, "visits", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "clienthost:::sessionid", false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getVisitor(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience")) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orderitems", SQLcolumnsVisitor + SQLcolumnsVariants, "clienthost=" + db.quote(myrequest.getParameter("visitor")), SQLcolumnsVisitor, "visitor", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal+countusersegments+countusertests", "clienthost:::sessionid", false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orderitems", SQLcolumnsVisitor, "clienthost=" + db.quote(myrequest.getParameter("visitor")), SQLcolumnsVisitor, "visitor", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "clienthost:::sessionid", false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getUsername(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience")) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orderitems", SQLcolumnsUsername + SQLcolumnsVariants, "clientusername=" + db.quote(myrequest.getParameter("username")), SQLcolumnsUsername, "username", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal+countusersegments+countusertests", "clienthost:::sessionid", false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orderitems", SQLcolumnsUsername, "clientusername=" + db.quote(myrequest.getParameter("username")), SQLcolumnsUsername, "username", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "clienthost:::sessionid", false);
		}
	}



//QQQ !!!! differing HashMap levels and types
@SuppressWarnings("unchecked")
	public HashMap getVisit(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap();
		HashMap usage = new HashMap();
		Date mynow = new Date();
		String SQLwhere = SQLwherePeriod(db, mysession, mynow, "");

		String SQL = "";
		if (! myrequest.getParameter("visitor").equals("")) {
			SQL = "select * from orderitems where " + SQLwhere + " and clienthost=" + db.quote(myrequest.getParameter("visitor")) + " order by orders.created asc, requestclass desc";
		} else {
			SQL = "select * from orderitems where " + SQLwhere + " and sessionid=" + db.quote(myrequest.getParameter("visit")) + " order by orders.created asc, requestclass desc";
		}
		HashMap hits = db.query_records(SQL);
		usage.put("visit", new HashMap());
		usage.put("sessiondurations", new HashMap());
		for (int i=0; i<hits.size(); i++) {
			String hit = "" + i;
			HashMap myhit = (HashMap)hits.get(hit);
			((HashMap)usage.get("visit")).put(hit, myhit);
			String mysessionid = "" + myhit.get("sessionid");
			long mysessionduration = Common.integernumber("" + myhit.get("sessionduration"));
			if ((((HashMap)usage.get("sessiondurations")).get(mysessionid) == null) || (mysessionduration > Common.integernumber("" + ((HashMap)usage.get("sessiondurations")).get(mysessionid)))) {
				((HashMap)usage.get("sessiondurations")).put(mysessionid, "" + mysessionduration);
			}
		}
		return usage;
	}



//QQQ !!!! differing HashMap levels and types
@SuppressWarnings("unchecked")
	public HashMap getContentItem(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap();
		HashMap usage = new HashMap();
		Date mynow = new Date();
		String SQLwhere = SQLwherePeriod(db, mysession, mynow, "");

		String SQL = "";
		SQL = "select * from orderitems where " + SQLwhere + " and requestid=" + myrequest.getParameter("id") + " and " + db.is_blank("requestdatabase") + " order by clientusername, clienthost, sessionid, orders.created asc";
		HashMap hits = db.query_records(SQL);
		usage.put("contentitem", new LinkedHashMap());
		for (int i=0; i<hits.size(); i++) {
			String hit = "" + i;
			((LinkedHashMap)usage.get("contentitem")).put("" + i, hits.get(hit));
		}

		if (myrequest.getParameter("contentclass").equals("page")) {
			SQL = SQLitemtotal(mysession, database, SQLcolumnsPaths, "orderitems", SQLwhere + " and requestid=" + myrequest.getParameter("id") + " and refererid<>0 and " + SQLwherePage, "");
		} else {
			SQL = SQLitemtotal(mysession, database, SQLcolumnsPaths, "orderitems", SQLwhere + " and requestid=" + myrequest.getParameter("id") + " and refererid<>0", "");
		}
		HashMap contentitemfrompaths = db.query_records(SQL);
		usage.put("contentitemfrompaths", new LinkedHashMap());
		for (int contentitemfrompath=0; contentitemfrompath<contentitemfrompaths.size(); contentitemfrompath++) {
			String paths = "" + ((HashMap)contentitemfrompaths.get("" + contentitemfrompath)).get("refererclass") + ":::" + ((HashMap)contentitemfrompaths.get("" + contentitemfrompath)).get("refererdatabase") + ":::" + ((HashMap)contentitemfrompaths.get("" + contentitemfrompath)).get("refererid") + ":::" + ((HashMap)contentitemfrompaths.get("" + contentitemfrompath)).get("requestclass") + ":::" + ((HashMap)contentitemfrompaths.get("" + contentitemfrompath)).get("requestdatabase") + ":::" + ((HashMap)contentitemfrompaths.get("" + contentitemfrompath)).get("requestid");
			((LinkedHashMap)usage.get("contentitemfrompaths")).put(paths, "" + ((HashMap)contentitemfrompaths.get("" + contentitemfrompath)).get("sumitemquantity"));
		}

		if (myrequest.getParameter("contentclass").equals("page")) {
			SQL = SQLitemtotal(mysession, database, SQLcolumnsPaths, "orderitems", SQLwhere + " and refererid=" + myrequest.getParameter("id") + " and refererid<>0 and " + SQLwherePage, "");
		} else {
			SQL = SQLitemtotal(mysession, database, SQLcolumnsPaths, "orderitems", SQLwhere + " and refererid=" + myrequest.getParameter("id") + " and refererid<>0", "");
		}
		HashMap contentitemtopaths = db.query_records(SQL);
		usage.put("contentitemtopaths", new LinkedHashMap());
		for (int contentitemtopath=0; contentitemtopath<contentitemtopaths.size(); contentitemtopath++) {
			String paths = "" + ((HashMap)contentitemtopaths.get("" + contentitemtopath)).get("refererclass") + ":::" + ((HashMap)contentitemtopaths.get("" + contentitemtopath)).get("refererdatabase") + ":::" + ((HashMap)contentitemtopaths.get("" + contentitemtopath)).get("refererid") + ":::" + ((HashMap)contentitemtopaths.get("" + contentitemtopath)).get("requestclass") + ":::" + ((HashMap)contentitemtopaths.get("" + contentitemtopath)).get("requestdatabase") + ":::" + ((HashMap)contentitemtopaths.get("" + contentitemtopath)).get("requestid");
			((LinkedHashMap)usage.get("contentitemtopaths")).put(paths, "" + ((HashMap)contentitemtopaths.get("" + contentitemtopath)).get("sumitemquantity"));
		}

		SQL = SQLitemtotal(mysession, database, SQLcolumnsReferers, "orderitems", SQLwhere + " and requestid=" + myrequest.getParameter("id") + " and " + db.is_not_blank("refererhost") + "", "");
		HashMap contentitemrefererssumitemquantity = db.query_records(SQL);
		usage.put("contentitemrefererssumitemquantity", new LinkedHashMap());
		for (int contentitemrefererscounthit=0; contentitemrefererscounthit<contentitemrefererssumitemquantity.size(); contentitemrefererscounthit++) {
			String referers = "" + ((HashMap)contentitemrefererssumitemquantity.get("" + contentitemrefererscounthit)).get("refererhost");
			((LinkedHashMap)usage.get("contentitemrefererssumitemquantity")).put(referers, "" + ((HashMap)contentitemrefererssumitemquantity.get("" + contentitemrefererscounthit)).get("sumitemquantity"));
		}

		SQL = SQLitemtotal(mysession, database, SQLcolumnsRefererPages, "orderitems", SQLwhere + " and requestid=" + myrequest.getParameter("id") + " and " + db.is_not_blank("refererhost") + "", "");
		HashMap contentitemrefererpathssumitemquantity = db.query_records(SQL);
		usage.put("contentitemrefererpathssumitemquantity", new LinkedHashMap());
		for (int contentitemrefererpathscounthit=0; contentitemrefererpathscounthit<contentitemrefererpathssumitemquantity.size(); contentitemrefererpathscounthit++) {
			String refererpaths = "" + ((HashMap)contentitemrefererpathssumitemquantity.get("" + contentitemrefererpathscounthit)).get("refererhost") + ((HashMap)contentitemrefererpathssumitemquantity.get("" + contentitemrefererpathscounthit)).get("refererpath");
			((LinkedHashMap)usage.get("contentitemrefererpathssumitemquantity")).put(refererpaths, "" + ((HashMap)contentitemrefererpathssumitemquantity.get("" + contentitemrefererpathscounthit)).get("sumitemquantity"));
		}

		return usage;
	}



	public HashMap<String,LinkedHashMap<String,HashMap<String,String>>> getDataItem(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,HashMap<String,String>>>();
		HashMap<String,LinkedHashMap<String,HashMap<String,String>>> usage = new HashMap<String,LinkedHashMap<String,HashMap<String,String>>>();
		Date mynow = new Date();
		String SQLwhere = SQLwherePeriod(db, mysession, mynow, "");

		String SQL = "";
		SQL = "select * from orderitems where " + SQLwhere + " and requestid=" + myrequest.getParameter("id") + " and requestdatabase=" + db.quote(myrequest.getParameter("database")) + " order by clientusername, clienthost, sessionid, orders.created asc";
		HashMap<String,HashMap<String,String>> hits = db.query_records(SQL);
		usage.put("dataitem", new LinkedHashMap<String,HashMap<String,String>>());
		for (int i=0; i<hits.size(); i++) {
			String hit = "" + i;
			((LinkedHashMap<String,HashMap<String,String>>)usage.get("dataitem")).put("" + i, hits.get(hit));
		}

		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getWebBrowsers(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		UsageLog usagelog = new UsageLog();
		StringBuffer clientbrowsers = new StringBuffer();
		clientbrowsers.append("'" + usagelog.browserName[0]);
		for (int i=1; i<usagelog.browserName.length; i++) clientbrowsers.append("','" + usagelog.browserName[i]);
		clientbrowsers.append("'");
		HashMap<String,LinkedHashMap<String,String>> usage = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsWebBrowsers, "clientbrowser in (" + clientbrowsers + ")", "", "webbrowsers", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "clientbrowser", false);
		HashMap<String,LinkedHashMap<String,String>> usage2 = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsWebBrowserVersions, "clientbrowser in (" + clientbrowsers + ")", "", "webbrowserversions", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "clientbrowser:::clientversion", false);
		usage.put("webbrowserversionscountcustomers", (LinkedHashMap<String,String>)usage2.get("webbrowserversionscountcustomers"));
		usage.put("webbrowserversionscountorders", (LinkedHashMap<String,String>)usage2.get("webbrowserversionscountorders"));
		usage.put("webbrowserversionscountorderitems", (LinkedHashMap<String,String>)usage2.get("webbrowserversionscountorderitems"));
		usage.put("webbrowserversionssumitemquantity", (LinkedHashMap<String,String>)usage2.get("webbrowserversionssumitemquantity"));
		usage.put("webbrowserversionssumitemtotal", (LinkedHashMap<String,String>)usage2.get("webbrowserversionssumitemtotal"));
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getDevices(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		UsageLog usagelog = new UsageLog();
		StringBuffer clientdevices = new StringBuffer();
		clientdevices.append("'" + usagelog.deviceType[0]);
		for (int i=1; i<usagelog.deviceType.length; i++) clientdevices.append("','" + usagelog.deviceType[i]);
		clientdevices.append("'");
		HashMap<String,LinkedHashMap<String,String>> usage = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsDevices, "clientdevice in (" + clientdevices + ")", "", "devices", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "clientdevice", false);
		HashMap<String,LinkedHashMap<String,String>> usage2 = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsDeviceVersions, "clientdevice in (" + clientdevices + ")", "", "deviceversions", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "clientdevice:::clientdeviceversion", false);
		usage.put("deviceversionscountcustomers", (LinkedHashMap<String,String>)usage2.get("deviceversionscountcustomers"));
		usage.put("deviceversionscountorders", (LinkedHashMap<String,String>)usage2.get("deviceversionscountorders"));
		usage.put("deviceversionscountorderitems", (LinkedHashMap<String,String>)usage2.get("deviceversionscountorderitems"));
		usage.put("deviceversionssumitemquantity", (LinkedHashMap<String,String>)usage2.get("deviceversionssumitemquantity"));
		usage.put("deviceversionssumitemtotal", (LinkedHashMap<String,String>)usage2.get("deviceversionssumitemtotal"));
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getOperatingSystems(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		UsageLog usagelog = new UsageLog();
		StringBuffer clientos = new StringBuffer();
		clientos.append("'" + usagelog.osName[0]);
		for (int i=1; i<usagelog.osName.length; i++) clientos.append("','" + usagelog.osName[i]);
		clientos.append("'");
		HashMap<String,LinkedHashMap<String,String>> usage = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsOperatingSystems, "clientos in (" + clientos + ")", "", "operatingsystems", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "clientos", false);
		HashMap<String,LinkedHashMap<String,String>> usage2 = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsOperatingSystemVersions, "clientos in (" + clientos + ")", "", "operatingsystemversions", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "clientos:::clientosversion", false);
		usage.put("operatingsystemversionscountcustomers", (LinkedHashMap<String,String>)usage2.get("operatingsystemversionscountcustomers"));
		usage.put("operatingsystemversionscountorders", (LinkedHashMap<String,String>)usage2.get("operatingsystemversionscountorders"));
		usage.put("operatingsystemversionscountorderitems", (LinkedHashMap<String,String>)usage2.get("operatingsystemversionscountorderitems"));
		usage.put("operatingsystemversionssumitemquantity", (LinkedHashMap<String,String>)usage2.get("operatingsystemversionssumitemquantity"));
		usage.put("operatingsystemversionssumitemtotal", (LinkedHashMap<String,String>)usage2.get("operatingsystemversionssumitemtotal"));
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getUsers(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		String SQLtable;
		String SQLwhere;
		if (myrequest.getParameter("usergroup").equals("-")) {
			String users_ids = Common.SQL_list_row_ids(db, db.query_records("select id from users where " + db.is_blank("usergroup") + ""), true);
			SQLtable = "orders left outer join orderitems on orders.id=orderitems.order_id";
			SQLwhere = "user_id in (" + users_ids + ")";
		} else if ((! myrequest.getParameter("usergroup").equals("")) && (! myrequest.getParameter("usergroup").equals(" "))) {
			String users_ids = Common.SQL_list_row_ids(db, db.query_records("select id from users where usergroup=" + db.quote(myrequest.getParameter("usergroup"))), true);
			SQLtable = "orders left outer join orderitems on orders.id=orderitems.order_id";
			SQLwhere = "user_id in (" + users_ids + ")";
		} else if (myrequest.getParameter("usertype").equals("-")) {
			String users_ids = Common.SQL_list_row_ids(db, db.query_records("select id from users where " + db.is_blank("usertype") + ""), true);
			SQLtable = "orders left outer join orderitems on orders.id=orderitems.order_id";
			SQLwhere = "user_id in (" + users_ids + ")";
		} else if ((! myrequest.getParameter("usertype").equals("")) && (! myrequest.getParameter("usertype").equals(" "))) {
			String users_ids = Common.SQL_list_row_ids(db, db.query_records("select id from users where usertype=" + db.quote(myrequest.getParameter("usertype"))), true);
			SQLtable = "orders left outer join orderitems on orders.id=orderitems.order_id";
			SQLwhere = "user_id in (" + users_ids + ")";
		} else {
			SQLtable = "orders left outer join orderitems on orders.id=orderitems.order_id";
			SQLwhere = "1=1";
		}
		if (License.valid(db, myconfig, "experience")) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsUsers + SQLcolumnsVariants, SQLwhere, "", "users", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal+countusersegments+countusertests", "user_id", false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsUsers, SQLwhere, "", "users", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "user_id", false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getUsergroups(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		String SQLtable = "orders left outer join orderitems on orders.id=orderitems.order_id";
		HashMap<String,LinkedHashMap<String,String>> usage = new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,HashMap<String,String>> rows = db.query_records("select usergroup from usergroups");
		HashMap<String,String> myblank = new HashMap<String,String>();
		myblank.put("usergroup", "");
		rows.put("", myblank);
		Iterator rowsIterator = rows.keySet().iterator();
		while (rowsIterator.hasNext()) {
			String key = "" + rowsIterator.next();
			HashMap<String,String> row = (HashMap<String,String>)rows.get(key);
			String user_ids = Common.SQL_list_row_ids(db, db.query_records("select id from users where id is not null and id<>0" + Common.SQLwhere_equals(db, " ", "usergroup", "" + row.get("usergroup"))), true);
			HashMap<String,LinkedHashMap<String,String>> output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, db.quote("" + row.get("usergroup")) + " as usergroup", "user_id in (" + user_ids + ")", "", "usergroups", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "usergroup", false);
			if (usage.get("usergroupscountcustomers") == null) { usage.put("usergroupscountcustomers", new LinkedHashMap<String,String>()); }
			if (usage.get("usergroupscountorders") == null) { usage.put("usergroupscountorders", new LinkedHashMap<String,String>()); }
			if (usage.get("usergroupscountorderitems") == null) { usage.put("usergroupscountorderitems", new LinkedHashMap<String,String>()); }
			if (usage.get("usergroupssumitemquantity") == null) { usage.put("usergroupssumitemquantity", new LinkedHashMap<String,String>()); }
			if (usage.get("usergroupssumitemtotal") == null) { usage.put("usergroupssumitemtotal", new LinkedHashMap<String,String>()); }
			usage.put("usergroupscountcustomers", Common.array_merge((LinkedHashMap<String,String>)usage.get("usergroupscountcustomers"),(LinkedHashMap<String,String>)output.get("usergroupscountcustomers")));
			usage.put("usergroupscountorders", Common.array_merge((LinkedHashMap<String,String>)usage.get("usergroupscountorders"),(LinkedHashMap<String,String>)output.get("usergroupscountorders")));
			usage.put("usergroupscountorderitems", Common.array_merge((LinkedHashMap<String,String>)usage.get("usergroupscountorderitems"),(LinkedHashMap<String,String>)output.get("usergroupscountorderitems")));
			usage.put("usergroupssumitemquantity", Common.array_merge((LinkedHashMap<String,String>)usage.get("usergroupssumitemquantity"),(LinkedHashMap<String,String>)output.get("usergroupssumitemquantity")));
			usage.put("usergroupssumitemtotal", Common.array_merge((LinkedHashMap<String,String>)usage.get("usergroupssumitemtotal"),(LinkedHashMap<String,String>)output.get("usergroupssumitemtotal")));
		}
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getUsertypes(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		String SQLtable = "orders left outer join orderitems on orders.id=orderitems.order_id";
		HashMap<String,LinkedHashMap<String,String>> usage = new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,HashMap<String,String>> rows = db.query_records("select usertype from usertypes");
		HashMap<String,String> myblank = new HashMap<String,String>();
		myblank.put("usertype", "");
		rows.put("", myblank);
		Iterator rowsIterator = rows.keySet().iterator();
		while (rowsIterator.hasNext()) {
			String key = "" + rowsIterator.next();
			HashMap<String,String> row = (HashMap<String,String>)rows.get(key);
			String user_ids = Common.SQL_list_row_ids(db, db.query_records("select id from users where id is not null and id<>0" + Common.SQLwhere_equals(db, " ", "usertype", "" + row.get("usertype"))), true);
			HashMap<String,LinkedHashMap<String,String>> output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, db.quote("" + row.get("usertype")) + " as usertype", "user_id in (" + user_ids + ")", "", "usertypes", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "usertype", false);
			if (usage.get("usertypescountcustomers") == null) { usage.put("usertypescountcustomers", new LinkedHashMap<String,String>()); }
			if (usage.get("usertypescountorders") == null) { usage.put("usertypescountorders", new LinkedHashMap<String,String>()); }
			if (usage.get("usertypescountorderitems") == null) { usage.put("usertypescountorderitems", new LinkedHashMap<String,String>()); }
			if (usage.get("usertypessumitemquantity") == null) { usage.put("usertypessumitemquantity", new LinkedHashMap<String,String>()); }
			if (usage.get("usertypessumitemtotal") == null) { usage.put("usertypessumitemtotal", new LinkedHashMap<String,String>()); }
			usage.put("usertypescountcustomers", Common.array_merge((LinkedHashMap<String,String>)usage.get("usertypescountcustomers"),(LinkedHashMap<String,String>)output.get("usertypescountcustomers")));
			usage.put("usertypescountorders", Common.array_merge((LinkedHashMap<String,String>)usage.get("usertypescountorders"),(LinkedHashMap<String,String>)output.get("usertypescountorders")));
			usage.put("usertypescountorderitems", Common.array_merge((LinkedHashMap<String,String>)usage.get("usertypescountorderitems"),(LinkedHashMap<String,String>)output.get("usertypescountorderitems")));
			usage.put("usertypessumitemquantity", Common.array_merge((LinkedHashMap<String,String>)usage.get("usertypessumitemquantity"),(LinkedHashMap<String,String>)output.get("usertypessumitemquantity")));
			usage.put("usertypessumitemtotal", Common.array_merge((LinkedHashMap<String,String>)usage.get("usertypessumitemtotal"),(LinkedHashMap<String,String>)output.get("usertypessumitemtotal")));
		}
		return usage;
	}




	public HashMap<String,LinkedHashMap<String,String>> getAffiliates(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		String SQLwhere;
		SQLwhere = "" + db.is_not_blank("affiliate") + "";
		return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsAffiliates, SQLwhere, "", "affiliates", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "affiliate", false);
	}



	public HashMap<String,LinkedHashMap<String,String>> getReferers(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		UsageLog usagelog = new UsageLog();
		StringBuffer clientbrowsers = new StringBuffer();
		clientbrowsers.append("'" + usagelog.browserName[0]);
		for (int i=1; i<usagelog.browserName.length; i++) clientbrowsers.append("','" + usagelog.browserName[i]);
		clientbrowsers.append("'");
		HashMap<String,LinkedHashMap<String,String>> usage = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsReferers, "1=1", "", "referers", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "refererhost", false);
		HashMap<String,LinkedHashMap<String,String>> usage2 = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsRefererPages, "1=1", "", "refererpaths", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "refererhost:::refererpath", false);
		usage.put("refererpathscountcustomers", (LinkedHashMap<String,String>)usage2.get("refererpathscountcustomers"));
		usage.put("refererpathscountorders", (LinkedHashMap<String,String>)usage2.get("refererpathscountorders"));
		usage.put("refererpathscountorderitems", (LinkedHashMap<String,String>)usage2.get("refererpathscountorderitems"));
		usage.put("refererpathssumitemquantity", (LinkedHashMap<String,String>)usage2.get("refererpathssumitemquantity"));
		usage.put("refererpathssumitemtotal", (LinkedHashMap<String,String>)usage2.get("refererpathssumitemtotal"));
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getSearchEngines(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		UsageLog usagelog = new UsageLog();
		StringBuffer referersearchengines = new StringBuffer();
		referersearchengines.append("'" + usagelog.searchengineName[0]);
		for (int i=1; i<usagelog.searchengineName.length; i++) referersearchengines.append("','" + usagelog.searchengineName[i]);
		referersearchengines.append("'");
		if (License.valid(db, myconfig, "experience")) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsSearchEngines + SQLcolumnsVariants, "referersearchengine in (" + referersearchengines + ")", "", "searchengines", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal+countusersegments+countusertests", "referersearchengine", false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsSearchEngines, "referersearchengine in (" + referersearchengines + ")", "", "searchengines", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "referersearchengine", false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getSearchQueries(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		String SQLwhere;
		SQLwhere = "" + db.is_not_blank("referersearchquery") + "";
		if (License.valid(db, myconfig, "experience")) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsSearchQueries + SQLcolumnsVariants, SQLwhere, "", "searchqueries", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal+countusersegments+countusertests", "referersearchquery", true);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsSearchQueries, SQLwhere, "", "searchqueries", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "referersearchquery", true);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getSearchWords(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,LinkedHashMap<String,String>> usage = getSearchQueries(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		usage.put("searchwordscountcustomers", new LinkedHashMap<String,String>());
		usage.put("searchwordscountorders", new LinkedHashMap<String,String>());
		usage.put("searchwordscountorderitems", new LinkedHashMap<String,String>());
		usage.put("searchwordssumitemquantity", new LinkedHashMap<String,String>());
		usage.put("searchwordssumitemtotal", new LinkedHashMap<String,String>());
		Iterator mysearchqueriessumitemtotal = ((LinkedHashMap<String,String>)usage.get("searchqueriessumitemtotal")).keySet().iterator();
		while (mysearchqueriessumitemtotal.hasNext()) {
			String searchquery = "" + mysearchqueriessumitemtotal.next();
			String[] searchwords = searchquery.replaceAll("[^-_a-zA-Z0-9]+", " ").trim().split(" ");
			for (int i=0; i<searchwords.length; i++) {
				String searchword = searchwords[i];
				((HashMap<String,String>)usage.get("searchwordscountcustomers")).put(searchword, "" + new Long(Math.round(Common.number("" + ((HashMap<String,String>)usage.get("searchwordscountcustomers")).get(searchword)) + Common.number("" + ((HashMap<String,String>)usage.get("searchqueriescountcustomers")).get(searchquery)))));
				((HashMap<String,String>)usage.get("searchwordscountorders")).put(searchword, "" + new Long(Math.round(Common.number("" + ((HashMap<String,String>)usage.get("searchwordscountorders")).get(searchword)) + Common.number("" + ((HashMap<String,String>)usage.get("searchqueriescountorders")).get(searchquery)))));
				((HashMap<String,String>)usage.get("searchwordscountorderitems")).put(searchword, "" + new Long(Math.round(Common.number("" + ((HashMap<String,String>)usage.get("searchwordscountorderitems")).get(searchword)) + Common.number("" + ((HashMap<String,String>)usage.get("searchqueriescountorderitems")).get(searchquery)))));
				((HashMap<String,String>)usage.get("searchwordssumitemquantity")).put(searchword, "" + new Long(Math.round(Common.number("" + ((HashMap<String,String>)usage.get("searchwordssumitemquantity")).get(searchword)) + Common.number("" + ((HashMap<String,String>)usage.get("searchqueriessumitemquantity")).get(searchquery)))));
				((HashMap<String,String>)usage.get("searchwordssumitemtotal")).put(searchword, "" + new Long(Math.round(Common.number("" + ((HashMap<String,String>)usage.get("searchwordssumitemtotal")).get(searchword)) + Common.number("" + ((HashMap<String,String>)usage.get("searchqueriessumitemtotal")).get(searchquery)))));
			}
		}
		usage.put("searchqueriescountcustomers", new LinkedHashMap<String,String>());
		usage.put("searchqueriescountorders", new LinkedHashMap<String,String>());
		usage.put("searchqueriescountorderitems", new LinkedHashMap<String,String>());
		usage.put("searchqueriessumitemquantity", new LinkedHashMap<String,String>());
		usage.put("searchqueriessumitemtotal", new LinkedHashMap<String,String>());
		usage.put("searchwordscountcustomers", Common.LinkedHashMapSortedByValue((LinkedHashMap<String,String>)usage.get("searchwordscountcustomers"), false));
		usage.put("searchwordscountorders", Common.LinkedHashMapSortedByValue((LinkedHashMap<String,String>)usage.get("searchwordscountorders"), false));
		usage.put("searchwordscountorderitems", Common.LinkedHashMapSortedByValue((LinkedHashMap<String,String>)usage.get("searchwordscountorderitems"), false));
		usage.put("searchwordssumitemquantity", Common.LinkedHashMapSortedByValue((LinkedHashMap<String,String>)usage.get("searchwordssumitemquantity"), false));
		usage.put("searchwordssumitemtotal", Common.LinkedHashMapSortedByValue((LinkedHashMap<String,String>)usage.get("searchwordssumitemtotal"), false));
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getEntry(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience")) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsEntry + SQLcolumnsVariants, "1=1", "", "entry", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal+countusersegments+countusertests", "session_entry", false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orders left outer join orderitems on orders.id=orderitems.order_id", SQLcolumnsEntry, "1=1", "", "entry", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "session_entry", false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getExit(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,LinkedHashMap<String,String>> usage = new HashMap<String,LinkedHashMap<String,String>>();
		Date mynow = new Date();
		String SQLwhere = SQLwherePeriod(db, mysession, mynow, "");

		String SQL = "";
		if (db.db_type(database).equals("mysql")) {
			// MySQL v3.3-v4.0 does not support sub-selects
			// select exit page for each sessionid and count manually
			SQL = "select q1.requestid, q1.requestclass, q1.requestdatabase, q1.clienthost, q1.sessionid";
			SQL = SQL + " from orderitems q1 left join orderitems q2 on q1.sessionid=q2.sessionid and q1.sessionduration <= q2.sessionduration";
			SQL = SQL + " where " + SQLwherePeriod(db, mysession, mynow, "q1.") + " and q1." + SQLwherePage + "";
			SQL = SQL + " and " + SQLwherePeriod(db, mysession, mynow, "q2.") + " and q2." + SQLwherePage + "";
			SQL = SQL + " group by q1.requestid, q1.requestclass, q1.requestdatabase, q1.clienthost, q1.sessionid, q1.sessionduration having count(q2.sessionduration)=1";
			SQL = SQL + " order by q1.requestid, q1.requestclass, q1.requestdatabase, q1.clienthost, q1.sessionid, q1.sessionduration";
			HashMap<String,HashMap<String,String>> exitpages = db.query_records(SQL);
			usage.put("exitcountvisitors", new LinkedHashMap<String,String>());
			usage.put("exitcountvisits", new LinkedHashMap<String,String>());
			String clienthost = "";
			String sessionid = "";
			for (int i=0; i<exitpages.size(); i++) {
				String exitpage = "" + i;
				String myexit = "" + ((HashMap<String,String>)exitpages.get(exitpage)).get("requestclass") + ":::" + ((HashMap<String,String>)exitpages.get(exitpage)).get("requestdatabase") + ":::" + ((HashMap<String,String>)exitpages.get(exitpage)).get("requestid");
				if (! clienthost.equals(myexit + ":::" + ((HashMap<String,String>)exitpages.get(exitpage)).get("clienthost"))) {
					clienthost = myexit + ":::" + ((HashMap<String,String>)exitpages.get(exitpage)).get("clienthost");
					((LinkedHashMap<String,String>)usage.get("exitcountvisitors")).put(myexit, "" + new Long(Math.round(Common.number("" + ((HashMap<String,String>)usage.get("exitcountvisitors")).get(myexit)) + 1)));
				}
				if (! sessionid.equals(myexit + ":::" + ((HashMap<String,String>)exitpages.get(exitpage)).get("sessionid"))) {
					sessionid = myexit + ":::" + ((HashMap<String,String>)exitpages.get(exitpage)).get("sessionid");
					((LinkedHashMap<String,String>)usage.get("exitcountvisits")).put(myexit, "" + new Long(Math.round(Common.number("" + ((HashMap<String,String>)usage.get("exitcountvisits")).get(myexit)) + 1)));
				}
			}
			usage.put("exitcountvisitors", Common.LinkedHashMapSortedByValue((LinkedHashMap<String,String>)usage.get("exitcountvisitors"), false));
			usage.put("exitcountvisits", Common.LinkedHashMapSortedByValue((LinkedHashMap<String,String>)usage.get("exitcountvisits"), false));
		} else {
			if ((db.db_type(database).equals("access")) || (db.db_type(database).equals("mssql"))) {
				SQL = SQLorders(database, SQLcolumnsExit, "orderitems q1", SQLwhere + " and " + SQLwherePage + " and sessionduration in (select max(sessionduration) from orderitems q2 where q1.sessionid=q2.sessionid and " + SQLwherePage + ")", "");
			} else {
				SQL = SQLorders(database, SQLcolumnsExit, "orderitems", SQLwhere + " and " + SQLwherePage + " and (sessionid, sessionduration) in (select sessionid, max(sessionduration) from orderitems where " + SQLwherePage + " group by sessionid)", "");
			}
			HashMap<String,HashMap<String,String>> exitcountvisitors = db.query_records(SQL);
			usage.put("exitcountvisitors", new LinkedHashMap<String,String>());
			for (int i=0; i<exitcountvisitors.size(); i++) {
				String exitcountvisitor = "" + i;
				String myexit = "" + ((HashMap<String,String>)exitcountvisitors.get(exitcountvisitor)).get("requestclass") + ":::" + ((HashMap<String,String>)exitcountvisitors.get(exitcountvisitor)).get("requestdatabase") + ":::" + ((HashMap<String,String>)exitcountvisitors.get(exitcountvisitor)).get("requestid");
				((HashMap<String,String>)usage.get("exitcountvisitors")).put(myexit, "" + ((HashMap<String,String>)exitcountvisitors.get(exitcountvisitor)).get("countvisitors"));
			}

			if ((db.db_type(database).equals("access")) || (db.db_type(database).equals("mssql"))) {
				SQL = SQLorderitems(database, SQLcolumnsExit, "orderitems q1", SQLwhere + " and " + SQLwherePage + " and sessionduration in (select max(sessionduration) from orderitems q2 where q1.sessionid=q2.sessionid and " + SQLwherePage + ")", "");
			} else {
				SQL = SQLorderitems(database, SQLcolumnsExit, "orderitems", SQLwhere + " and " + SQLwherePage + " and (sessionid, sessionduration) in (select sessionid, max(sessionduration) from orderitems where " + SQLwherePage + " group by sessionid)", "");
			}
			HashMap<String,HashMap<String,String>> exitcountvisits = db.query_records(SQL);
			usage.put("exitcountvisits", new LinkedHashMap<String,String>());
			for (int i=0; i<exitcountvisits.size(); i++) {
				String exitcountvisit = "" + i;
				String myexit = "" + ((HashMap<String,String>)exitcountvisits.get(exitcountvisit)).get("requestclass") + ":::" + ((HashMap<String,String>)exitcountvisits.get(exitcountvisit)).get("requestdatabase") + ":::" + ((HashMap<String,String>)exitcountvisits.get(exitcountvisit)).get("requestid");
				((HashMap<String,String>)usage.get("exitcountvisits")).put(myexit, "" + ((HashMap<String,String>)exitcountvisits.get(exitcountvisit)).get("countvisits"));
			}
		}
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getPaths(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience")) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orderitems", SQLcolumnsPaths + SQLcolumnsVariants, SQLwherePage + " and refererid<>0", "", "paths", "countvisitors+countvisits+countpageviews+countusersegments+countusertests", "refererclass:::refererdatabase:::refererid:::requestclass:::requestdatabase:::requestid", false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "orderitems", SQLcolumnsPaths, SQLwherePage + " and refererid<>0", "", "paths", "countvisitors+countvisits+countpageviews", "refererclass:::refererdatabase:::refererid:::requestclass:::requestdatabase:::requestid", false);
		}
	}



	private String exitPagesMySQL(Session mysession, Request myrequest, Configuration config, DB db, DB logdb, String database, Date mynow, String minduration, String maxduration) {
		// MySQL v3.3-v4.0 does not support sub-selects
		// select exit page for each sessionid to be counted etc. manually
		String SQL = "select q1.requestid, q1.requestclass, q1.requestdatabase, q1.clienthost, q1.sessionid, q1.sessionduration as maxsessionduration";
		SQL = SQL + " from orderitems q1 left join orderitems q2 on q1.sessionid=q2.sessionid and q1.sessionduration <= q2.sessionduration";
		SQL = SQL + " where " + SQLwherePeriod(db, mysession, mynow, "q1.") + " and " + SQLwherePeriod(db, mysession, mynow, "q2.") + "";
		if ((! minduration.equals("")) && (! maxduration.equals(""))) {
			SQL = SQL + " and q1.sessionduration >= " + minduration + " and q1.sessionduration < " + maxduration;
		} else if (! minduration.equals("")) {
			SQL = SQL + " and q1.sessionduration >= " + minduration;
		}
		SQL = SQL + " group by q1.requestid, q1.requestclass, q1.requestdatabase, q1.clienthost, q1.sessionid, q1.sessionduration having count(q2.sessionduration)=1";
		SQL = SQL + " order by q1.requestid, q1.requestclass, q1.requestdatabase, q1.clienthost, q1.sessionid, q1.sessionduration";
		return SQL;
	}



	public HashMap<String,String> getDuration(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,String>();
		HashMap<String,String> usage = new HashMap<String,String>();
		Date mynow = new Date();
		String SQLwhere = SQLwherePeriod(db, mysession, mynow, "");

		String SQL = "";
		if (db.db_type(database).equals("mysql")) {
			// MySQL v3.3-v4.0 does not support sub-selects
			// select exit page for each sessionid and count manually
			SQL = exitPagesMySQL(mysession, myrequest, myconfig, db, logdb, database, mynow, "", "");
			HashMap<String,HashMap<String,String>> exitpages = db.query_records(SQL);
			usage.put("visitduration-count", "" + 0);
			usage.put("visitduration-total", "" + 0);
			for (int i=0; i<exitpages.size(); i++) {
				String exitpage = "" + i;
				usage.put("visitduration-count", "" + Math.round(Common.number("" + usage.get("visitduration-count")) + 1));
				usage.put("visitduration-total", "" + Math.round(Common.number("" + usage.get("visitduration-total")) + Common.number("" + ((HashMap)exitpages.get(exitpage)).get("maxsessionduration"))));
			}
			if (Common.number("" + usage.get("visitduration-count")) > 0) {
				usage.put("visitduration-average", "" + Math.round(Common.number("" + usage.get("visitduration-total")) / Common.number("" + usage.get("visitduration-count"))));
			} else {
				usage.put("visitduration-average", "" + 0);
			}
		} else if (db.db_type(database).equals("oracle")) {
			// Oracle does not support naming of sub-selects
			SQL = "select avg(maxsessionduration) from (select max(sessionduration) as maxsessionduration from orderitems where " + SQLwhere + " group by sessionid)";
			usage.put("visitduration-average", "" + db.query_value(SQL));
		} else {
			// Microsoft SQL Server requires naming of sub-selects
			SQL = "select avg(maxsessionduration) from (select max(sessionduration) as maxsessionduration from orderitems where " + SQLwhere + " group by sessionid) as maxsessiondurations";
			usage.put("visitduration-average", "" + db.query_value(SQL));
		}

		usage.put("visitduration-0s-30s", "" + getDurationVisit(mysession, myrequest, myconfig, db, logdb, database, mynow, SQLwhere, "0", "30"));
		usage.put("visitduration-30s-2m", "" + getDurationVisit(mysession, myrequest, myconfig, db, logdb, database, mynow, SQLwhere, "30", "120"));
		usage.put("visitduration-2m-5m", "" + getDurationVisit(mysession, myrequest, myconfig, db, logdb, database, mynow, SQLwhere, "120", "300"));
		usage.put("visitduration-5m-15m", "" + getDurationVisit(mysession, myrequest, myconfig, db, logdb, database, mynow, SQLwhere, "300", "900"));
		usage.put("visitduration-15m-30m", "" + getDurationVisit(mysession, myrequest, myconfig, db, logdb, database, mynow, SQLwhere, "900", "1800"));
		usage.put("visitduration-30m-1h", "" + getDurationVisit(mysession, myrequest, myconfig, db, logdb, database, mynow, SQLwhere, "1800", "3600"));
		usage.put("visitduration-1h+", "" + getDurationVisit(mysession, myrequest, myconfig, db, logdb, database, mynow, SQLwhere, "3600", ""));

		SQL = "select avg(refererduration) from orderitems where " + db.is_not_blank("refererclass") + " and " + SQLwhere + " and " + SQLwherePage;
		usage.put("pageviewduration-average", "" + db.query_value(SQL));

		usage.put("pageviewduration-0s-30s", "" + getDurationPageview(mysession, myrequest, myconfig, db, logdb, database, SQLwhere, "0", "30"));
		usage.put("pageviewduration-30s-2m", "" + getDurationPageview(mysession, myrequest, myconfig, db, logdb, database, SQLwhere, "30", "120"));
		usage.put("pageviewduration-2m-5m", "" + getDurationPageview(mysession, myrequest, myconfig, db, logdb, database, SQLwhere, "120", "300"));
		usage.put("pageviewduration-5m-15m", "" + getDurationPageview(mysession, myrequest, myconfig, db, logdb, database, SQLwhere, "300", "900"));
		usage.put("pageviewduration-15m-30m", "" + getDurationPageview(mysession, myrequest, myconfig, db, logdb, database, SQLwhere, "900", "1800"));
		usage.put("pageviewduration-30m-1h", "" + getDurationPageview(mysession, myrequest, myconfig, db, logdb, database, SQLwhere, "1800", "3600"));
		usage.put("pageviewduration-1h+", "" + getDurationPageview(mysession, myrequest, myconfig, db, logdb, database, SQLwhere, "3600", ""));

		return usage;
	}



	private Long getDurationVisit(Session mysession, Request myrequest, Configuration myconfig, DB db, DB logdb, String database, Date mynow, String SQLwhere, String minduration, String maxduration) {
		long duration = 0;
		String SQL = "";
		if (db.db_type(database).equals("mysql")) {
			// MySQL v3.3-v4.0 does not support sub-selects
			// select exit page for each sessionid and count manually
			SQL = exitPagesMySQL(mysession, myrequest, myconfig, db, logdb, database, mynow, minduration, maxduration);
			HashMap<String,HashMap<String,String>> exitpages = db.query_records(SQL);
			duration = 0;
			for (int i=0; i<exitpages.size(); i++) {
				duration = duration + 1;
			}
		} else if (db.db_type(database).equals("oracle")) {
			// Oracle does not support naming of sub-selects
			if (! maxduration.equals("")) {
				SQL = "select count(*) from (select max(sessionduration) as maxsessionduration from orderitems where " + SQLwhere + " group by sessionid) where maxsessionduration >= " + minduration + " and maxsessionduration < " + maxduration;
			} else {
				SQL = "select count(*) from (select max(sessionduration) as maxsessionduration from orderitems where " + SQLwhere + " group by sessionid) where maxsessionduration >= " + minduration;
			}
			duration = db.query_value(SQL).longValue();
		} else {
			// Microsoft SQL Server requires naming of sub-selects
			if (! maxduration.equals("")) {
				SQL = "select count(*) from (select max(sessionduration) as maxsessionduration from orderitems where " + SQLwhere + " group by sessionid) as maxsessiondurations where maxsessionduration >= " + minduration + " and maxsessionduration < " + maxduration;
			} else {
				SQL = "select count(*) from (select max(sessionduration) as maxsessionduration from orderitems where " + SQLwhere + " group by sessionid) as maxsessiondurations where maxsessionduration >= " + minduration;
			}
			duration = db.query_value(SQL).longValue();
		}
		return new Long(duration);
	}



	private Long getDurationPageview(Session mysession, Request myrequest, Configuration myconfig, DB db, DB logdb, String database, String SQLwhere, String minduration, String maxduration) {
		long duration = 0;
		String SQL = "";
		if (! maxduration.equals("")) {
			SQL = "select sum(hits) from orderitems where " + db.is_not_blank("refererclass") + " and " + SQLwhere + " and " + SQLwherePage + " and refererduration >= " + minduration + " and refererduration < " + maxduration;
		} else {
			SQL = "select sum(hits) from orderitems where " + db.is_not_blank("refererclass") + " and " + SQLwhere + " and " + SQLwherePage + " and refererduration >= " + minduration;
		}
		duration = db.query_value(SQL).longValue();
		return new Long(duration);
	}



	public HashMap<String,LinkedHashMap<String,String>> getProducts(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		String SQLtable = "";
		String SQLwhere = "";
		if (myrequest.getParameter("contentgroup").equals("-")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("product") + " and " + db.is_blank("contentgroup") + ""));
			SQLtable = "orders left outer join orderitems on orders.id=orderitems.order_id";
			SQLwhere = "product_id in (" + content_ids + ")";
		} else if ((! myrequest.getParameter("contentgroup").equals("")) && (! myrequest.getParameter("contentgroup").equals(" "))) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("product") + " and contentgroup=" + db.quote(myrequest.getParameter("contentgroup"))));
			SQLtable = "orders left outer join orderitems on orders.id=orderitems.order_id";
			SQLwhere = "product_id in (" + content_ids + ")";
		} else if (myrequest.getParameter("contenttype").equals("-")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("product") + " and " + db.is_blank("contenttype") + ""));
			SQLtable = "orders left outer join orderitems on orders.id=orderitems.order_id";
			SQLwhere = "product_id in (" + content_ids + ")";
		} else if ((! myrequest.getParameter("contenttype").equals("")) && (! myrequest.getParameter("contenttype").equals(" "))) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("product") + " and contenttype=" + db.quote(myrequest.getParameter("contenttype"))));
			SQLtable = "orders left outer join orderitems on orders.id=orderitems.order_id";
			SQLwhere = "product_id in (" + content_ids + ")";
		} else {
			SQLtable = "orders left outer join orderitems on orders.id=orderitems.order_id";
			SQLwhere = "1=1";
		}
		if (License.valid(db, myconfig, "experience")) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsProducts + SQLcolumnsVariants, SQLwhere, "", "products", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal+countusersegments+countusertests", "product_id", false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsProducts, SQLwhere, "", "products", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "product_id", false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getProductgroups(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		String SQLtable = "orders left outer join orderitems on orders.id=orderitems.order_id";
		HashMap<String,LinkedHashMap<String,String>> usage = new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,HashMap<String,String>> rows = db.query_records("select productgroup from productgroups");
		HashMap<String,String> myblank = new HashMap<String,String>();
		myblank.put("productgroup", "");
		rows.put("", myblank);
		Iterator rowsIterator = rows.keySet().iterator();
		while (rowsIterator.hasNext()) {
			String key = "" + rowsIterator.next();
			HashMap<String,String> row = (HashMap<String,String>)rows.get(key);
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("product") + Common.SQLwhere_equals(db, " ", "contentgroup", "" + row.get("productgroup"))));
			HashMap<String,LinkedHashMap<String,String>> output;
			if (License.valid(db, myconfig, "experience")) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, db.quote("" + row.get("productgroup")) + " as contentgroup" + SQLcolumnsVariants, "product_id in (" + content_ids + ")", "", "productgroups", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal+countusersegments+countusertests", "contentgroup", false);
			} else {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, db.quote("" + row.get("productgroup")) + " as contentgroup", "product_id in (" + content_ids + ")", "", "productgroups", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "contentgroup", false);
			}
			if (usage.get("productgroupscountcustomers") == null) { usage.put("productgroupscountcustomers", new LinkedHashMap<String,String>()); }
			if (usage.get("productgroupscountorders") == null) { usage.put("productgroupscountorders", new LinkedHashMap<String,String>()); }
			if (usage.get("productgroupscountorderitems") == null) { usage.put("productgroupscountorderitems", new LinkedHashMap<String,String>()); }
			if (usage.get("productgroupssumitemquantity") == null) { usage.put("productgroupssumitemquantity", new LinkedHashMap<String,String>()); }
			if (usage.get("productgroupssumitemtotal") == null) { usage.put("productgroupssumitemtotal", new LinkedHashMap<String,String>()); }
			usage.put("productgroupscountcustomers", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountcustomers"),(LinkedHashMap<String,String>)output.get("productgroupscountcustomers")));
			usage.put("productgroupscountorders", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountorders"),(LinkedHashMap<String,String>)output.get("productgroupscountorders")));
			usage.put("productgroupscountorderitems", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountorderitems"),(LinkedHashMap<String,String>)output.get("productgroupscountorderitems")));
			usage.put("productgroupssumitemquantity", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupssumitemquantity"),(LinkedHashMap<String,String>)output.get("productgroupssumitemquantity")));
			usage.put("productgroupssumitemtotal", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupssumitemtotal"),(LinkedHashMap<String,String>)output.get("productgroupssumitemtotal")));
			if (License.valid(db, myconfig, "experience")) {
				if (usage.get("productgroupscountcustomerssegment") == null) { usage.put("productgroupscountcustomerssegment", new LinkedHashMap<String,String>()); }
				if (usage.get("productgroupscountorderssegment") == null) { usage.put("productgroupscountorderssegment", new LinkedHashMap<String,String>()); }
				if (usage.get("productgroupscountorderitemssegment") == null) { usage.put("productgroupscountorderitemssegment", new LinkedHashMap<String,String>()); }
				if (usage.get("productgroupssumitemquantitysegment") == null) { usage.put("productgroupssumitemquantitysegment", new LinkedHashMap<String,String>()); }
				if (usage.get("productgroupssumitemtotalsegment") == null) { usage.put("productgroupssumitemtotalsegment", new LinkedHashMap<String,String>()); }
				usage.put("productgroupscountcustomerssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountcustomerssegment"),(LinkedHashMap<String,String>)output.get("productgroupscountcustomerssegment")));
				usage.put("productgroupscountorderssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountorderssegment"),(LinkedHashMap<String,String>)output.get("productgroupscountorderssegment")));
				usage.put("productgroupscountorderitemssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountorderitemssegment"),(LinkedHashMap<String,String>)output.get("productgroupscountorderitemssegment")));
				usage.put("productgroupssumitemquantitysegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupssumitemquantitysegment"),(LinkedHashMap<String,String>)output.get("productgroupssumitemquantitysegment")));
				usage.put("productgroupssumitemtotalsegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupssumitemtotalsegment"),(LinkedHashMap<String,String>)output.get("productgroupssumitemtotalsegment")));
				if (usage.get("productgroupscountcustomerstest") == null) { usage.put("productgroupscountcustomerstest", new LinkedHashMap<String,String>()); }
				if (usage.get("productgroupscountorderstest") == null) { usage.put("productgroupscountorderstest", new LinkedHashMap<String,String>()); }
				if (usage.get("productgroupscountorderitemstest") == null) { usage.put("productgroupscountorderitemstest", new LinkedHashMap<String,String>()); }
				if (usage.get("productgroupssumitemquantitytest") == null) { usage.put("productgroupssumitemquantitytest", new LinkedHashMap<String,String>()); }
				if (usage.get("productgroupssumitemtotaltest") == null) { usage.put("productgroupssumitemtotaltest", new LinkedHashMap<String,String>()); }
				usage.put("productgroupscountcustomerstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountcustomerstest"),(LinkedHashMap<String,String>)output.get("productgroupscountcustomerstest")));
				usage.put("productgroupscountorderstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountorderstest"),(LinkedHashMap<String,String>)output.get("productgroupscountorderstest")));
				usage.put("productgroupscountorderitemstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountorderitemstest"),(LinkedHashMap<String,String>)output.get("productgroupscountorderitemstest")));
				usage.put("productgroupssumitemquantitytest", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupssumitemquantitytest"),(LinkedHashMap<String,String>)output.get("productgroupssumitemquantitytest")));
				usage.put("productgroupssumitemtotaltest", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupssumitemtotaltest"),(LinkedHashMap<String,String>)output.get("productgroupssumitemtotaltest")));
				if (usage.get("productgroupscountcustomerssegments") == null) { usage.put("productgroupscountcustomerssegments", new LinkedHashMap<String,String>()); }
				if (usage.get("productgroupscountorderssegments") == null) { usage.put("productgroupscountorderssegments", new LinkedHashMap<String,String>()); }
				if (usage.get("productgroupscountorderitemssegments") == null) { usage.put("productgroupscountorderitemssegments", new LinkedHashMap<String,String>()); }
				if (usage.get("productgroupssumitemquantitysegments") == null) { usage.put("productgroupssumitemquantitysegments", new LinkedHashMap<String,String>()); }
				if (usage.get("productgroupssumitemtotalsegments") == null) { usage.put("productgroupssumitemtotalsegments", new LinkedHashMap<String,String>()); }
				usage.put("productgroupscountcustomerssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountcustomerssegments"),(LinkedHashMap<String,String>)output.get("productgroupscountcustomerssegments")));
				usage.put("productgroupscountorderssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountorderssegments"),(LinkedHashMap<String,String>)output.get("productgroupscountorderssegments")));
				usage.put("productgroupscountorderitemssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountorderitemssegments"),(LinkedHashMap<String,String>)output.get("productgroupscountorderitemssegments")));
				usage.put("productgroupssumitemquantitysegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupssumitemquantitysegments"),(LinkedHashMap<String,String>)output.get("productgroupssumitemquantitysegments")));
				usage.put("productgroupssumitemtotalsegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupssumitemtotalsegments"),(LinkedHashMap<String,String>)output.get("productgroupssumitemtotalsegments")));
				if (usage.get("productgroupscountcustomerstests") == null) { usage.put("productgroupscountcustomerstests", new LinkedHashMap<String,String>()); }
				if (usage.get("productgroupscountorderstests") == null) { usage.put("productgroupscountorderstests", new LinkedHashMap<String,String>()); }
				if (usage.get("productgroupscountorderitemstests") == null) { usage.put("productgroupscountorderitemstests", new LinkedHashMap<String,String>()); }
				if (usage.get("productgroupssumitemquantitytests") == null) { usage.put("productgroupssumitemquantitytests", new LinkedHashMap<String,String>()); }
				if (usage.get("productgroupssumitemtotaltests") == null) { usage.put("productgroupssumitemtotaltests", new LinkedHashMap<String,String>()); }
				usage.put("productgroupscountcustomerstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountcustomerstests"),(LinkedHashMap<String,String>)output.get("productgroupscountcustomerstests")));
				usage.put("productgroupscountorderstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountorderstests"),(LinkedHashMap<String,String>)output.get("productgroupscountorderstests")));
				usage.put("productgroupscountorderitemstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountorderitemstests"),(LinkedHashMap<String,String>)output.get("productgroupscountorderitemstests")));
				usage.put("productgroupssumitemquantitytests", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupssumitemquantitytests"),(LinkedHashMap<String,String>)output.get("productgroupssumitemquantitytests")));
				usage.put("productgroupssumitemtotaltests", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupssumitemtotaltests"),(LinkedHashMap<String,String>)output.get("productgroupssumitemtotaltests")));
				if (usage.get("productgroupscountcustomerssegmentstests") == null) { usage.put("productgroupscountcustomerssegmentstests", new LinkedHashMap<String,String>()); }
				if (usage.get("productgroupscountorderssegmentstests") == null) { usage.put("productgroupscountorderssegmentstests", new LinkedHashMap<String,String>()); }
				if (usage.get("productgroupscountorderitemssegmentstests") == null) { usage.put("productgroupscountorderitemssegmentstests", new LinkedHashMap<String,String>()); }
				if (usage.get("productgroupssumitemquantitysegmentstests") == null) { usage.put("productgroupssumitemquantitysegmentstests", new LinkedHashMap<String,String>()); }
				if (usage.get("productgroupssumitemtotalsegmentstests") == null) { usage.put("productgroupssumitemtotalsegmentstests", new LinkedHashMap<String,String>()); }
				usage.put("productgroupscountcustomerssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountcustomerssegmentstests"),(LinkedHashMap<String,String>)output.get("productgroupscountcustomerssegmentstests")));
				usage.put("productgroupscountorderssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountorderssegmentstests"),(LinkedHashMap<String,String>)output.get("productgroupscountorderssegmentstests")));
				usage.put("productgroupscountorderitemssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountorderitemssegmentstests"),(LinkedHashMap<String,String>)output.get("productgroupscountorderitemssegmentstests")));
				usage.put("productgroupssumitemquantitysegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupssumitemquantitysegmentstests"),(LinkedHashMap<String,String>)output.get("productgroupssumitemquantitysegmentstests")));
				usage.put("productgroupssumitemtotalsegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupssumitemtotalsegmentstests"),(LinkedHashMap<String,String>)output.get("productgroupssumitemtotalsegmentstests")));
			}
		}
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getProducttypes(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		String SQLtable = "orders left outer join orderitems on orders.id=orderitems.order_id";
		HashMap<String,LinkedHashMap<String,String>> usage = new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,HashMap<String,String>> rows = db.query_records("select producttype from producttypes");
		HashMap<String,String> myblank = new HashMap<String,String>();
		myblank.put("producttype", "");
		rows.put("", myblank);
		Iterator rowsIterator = rows.keySet().iterator();
		while (rowsIterator.hasNext()) {
			String key = "" + rowsIterator.next();
			HashMap<String,String> row = (HashMap<String,String>)rows.get(key);
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("product") + Common.SQLwhere_equals(db, " ", "contenttype", "" + row.get("producttype"))));
			HashMap<String,LinkedHashMap<String,String>> output;
			if (License.valid(db, myconfig, "experience")) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, db.quote("" + row.get("producttype")) + " as contenttype" + SQLcolumnsVariants, "product_id in (" + content_ids + ")", "", "producttypes", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal+countusersegments+countusertests", "contenttype", false);
			} else {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, db.quote("" + row.get("producttype")) + " as contenttype", "product_id in (" + content_ids + ")", "", "producttypes", "countcustomers+countorders+countorderitems+sumitemquantity+sumitemtotal", "contenttype", false);
			}
			if (usage.get("producttypescountcustomers") == null) { usage.put("producttypescountcustomers", new LinkedHashMap<String,String>()); }
			if (usage.get("producttypescountorders") == null) { usage.put("producttypescountorders", new LinkedHashMap<String,String>()); }
			if (usage.get("producttypescountorderitems") == null) { usage.put("producttypescountorderitems", new LinkedHashMap<String,String>()); }
			if (usage.get("producttypessumitemquantity") == null) { usage.put("producttypessumitemquantity", new LinkedHashMap<String,String>()); }
			if (usage.get("producttypessumitemtotal") == null) { usage.put("producttypessumitemtotal", new LinkedHashMap<String,String>()); }
			usage.put("producttypescountcustomers", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountcustomers"),(LinkedHashMap<String,String>)output.get("producttypescountcustomers")));
			usage.put("producttypescountorders", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountorders"),(LinkedHashMap<String,String>)output.get("producttypescountorders")));
			usage.put("producttypescountorderitems", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountorderitems"),(LinkedHashMap<String,String>)output.get("producttypescountorderitems")));
			usage.put("producttypessumitemquantity", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypessumitemquantity"),(LinkedHashMap<String,String>)output.get("producttypessumitemquantity")));
			usage.put("producttypessumitemtotal", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypessumitemtotal"),(LinkedHashMap<String,String>)output.get("producttypessumitemtotal")));
			if (License.valid(db, myconfig, "experience")) {
				if (usage.get("producttypescountcustomerssegment") == null) { usage.put("producttypescountcustomerssegment", new LinkedHashMap<String,String>()); }
				if (usage.get("producttypescountorderssegment") == null) { usage.put("producttypescountorderssegment", new LinkedHashMap<String,String>()); }
				if (usage.get("producttypescountorderitemssegment") == null) { usage.put("producttypescountorderitemssegment", new LinkedHashMap<String,String>()); }
				if (usage.get("producttypessumitemquantitysegment") == null) { usage.put("producttypessumitemquantitysegment", new LinkedHashMap<String,String>()); }
				if (usage.get("producttypessumitemtota;segment") == null) { usage.put("producttypessumitemtotalsegment", new LinkedHashMap<String,String>()); }
				usage.put("producttypescountcustomerssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountcustomerssegment"),(LinkedHashMap<String,String>)output.get("producttypescountcustomerssegment")));
				usage.put("producttypescountorderssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountorderssegment"),(LinkedHashMap<String,String>)output.get("producttypescountorderssegment")));
				usage.put("producttypescountorderitemssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountorderitemssegment"),(LinkedHashMap<String,String>)output.get("producttypescountorderitemssegment")));
				usage.put("producttypessumitemquantitysegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypessumitemquantitysegment"),(LinkedHashMap<String,String>)output.get("producttypessumitemquantitysegment")));
				usage.put("producttypessumitemtotalsegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypessumitemtotalsegment"),(LinkedHashMap<String,String>)output.get("producttypessumitemtotalsegment")));
				if (usage.get("producttypescountcustomerstest") == null) { usage.put("producttypescountcustomerstest", new LinkedHashMap<String,String>()); }
				if (usage.get("producttypescountorderstest") == null) { usage.put("producttypescountorderstest", new LinkedHashMap<String,String>()); }
				if (usage.get("producttypescountorderitemstest") == null) { usage.put("producttypescountorderitemstest", new LinkedHashMap<String,String>()); }
				if (usage.get("producttypessumitemquantitytest") == null) { usage.put("producttypessumitemquantitytest", new LinkedHashMap<String,String>()); }
				if (usage.get("producttypessumitemtotaltest") == null) { usage.put("producttypessumitemtotaltest", new LinkedHashMap<String,String>()); }
				usage.put("producttypescountcustomerstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountcustomerstest"),(LinkedHashMap<String,String>)output.get("producttypescountcustomerstest")));
				usage.put("producttypescountorderstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountorderstest"),(LinkedHashMap<String,String>)output.get("producttypescountorderstest")));
				usage.put("producttypescountorderitemstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountorderitemstest"),(LinkedHashMap<String,String>)output.get("producttypescountorderitemstest")));
				usage.put("producttypessumitemquantitytest", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypessumitemquantitytest"),(LinkedHashMap<String,String>)output.get("producttypessumitemquantitytest")));
				usage.put("producttypessumitemtotaltest", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypessumitemtotaltest"),(LinkedHashMap<String,String>)output.get("producttypessumitemtotaltest")));
				if (usage.get("producttypescountcustomerssegments") == null) { usage.put("producttypescountcustomerssegments", new LinkedHashMap<String,String>()); }
				if (usage.get("producttypescountorderssegments") == null) { usage.put("producttypescountorderssegments", new LinkedHashMap<String,String>()); }
				if (usage.get("producttypescountorderitemssegments") == null) { usage.put("producttypescountorderitemssegments", new LinkedHashMap<String,String>()); }
				if (usage.get("producttypessumitemquantitysegments") == null) { usage.put("producttypessumitemquantitysegments", new LinkedHashMap<String,String>()); }
				if (usage.get("producttypessumitemtotalsegments") == null) { usage.put("producttypessumitemtotalsegments", new LinkedHashMap<String,String>()); }
				usage.put("producttypescountcustomerssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountcustomerssegments"),(LinkedHashMap<String,String>)output.get("producttypescountcustomerssegments")));
				usage.put("producttypescountorderssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountorderssegments"),(LinkedHashMap<String,String>)output.get("producttypescountorderssegments")));
				usage.put("producttypescountorderitemssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountorderitemssegments"),(LinkedHashMap<String,String>)output.get("producttypescountorderitemssegments")));
				usage.put("producttypessumitemquantitysegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypessumitemquantitysegments"),(LinkedHashMap<String,String>)output.get("producttypessumitemquantitysegments")));
				usage.put("producttypessumitemtotalsegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypessumitemtotalsegments"),(LinkedHashMap<String,String>)output.get("producttypessumitemtotalsegments")));
				if (usage.get("producttypescountcustomerstests") == null) { usage.put("producttypescountcustomerstests", new LinkedHashMap<String,String>()); }
				if (usage.get("producttypescountorderstests") == null) { usage.put("producttypescountorderstests", new LinkedHashMap<String,String>()); }
				if (usage.get("producttypescountorderitemstests") == null) { usage.put("producttypescountorderitemstests", new LinkedHashMap<String,String>()); }
				if (usage.get("producttypessumitemquantitytests") == null) { usage.put("producttypessumitemquantitytests", new LinkedHashMap<String,String>()); }
				if (usage.get("producttypessumitemtotaltests") == null) { usage.put("producttypessumitemtotaltests", new LinkedHashMap<String,String>()); }
				usage.put("producttypescountcustomerstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountcustomerstests"),(LinkedHashMap<String,String>)output.get("producttypescountcustomerstests")));
				usage.put("producttypescountorderstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountorderstests"),(LinkedHashMap<String,String>)output.get("producttypescountorderstests")));
				usage.put("producttypescountorderitemstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountorderitemstests"),(LinkedHashMap<String,String>)output.get("producttypescountorderitemstests")));
				usage.put("producttypessumitemquantitytests", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypessumitemquantitytests"),(LinkedHashMap<String,String>)output.get("producttypessumitemquantitytests")));
				usage.put("producttypessumitemtotaltests", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypessumitemtotaltests"),(LinkedHashMap<String,String>)output.get("producttypessumitemtotaltests")));
				if (usage.get("producttypescountcustomerssegmentstests") == null) { usage.put("producttypescountcustomerssegmentstests", new LinkedHashMap<String,String>()); }
				if (usage.get("producttypescountorderssegmentstests") == null) { usage.put("producttypescountorderssegmentstests", new LinkedHashMap<String,String>()); }
				if (usage.get("producttypescountorderitemssegmentstests") == null) { usage.put("producttypescountorderitemssegmentstests", new LinkedHashMap<String,String>()); }
				if (usage.get("producttypessumitemquantitysegmentstests") == null) { usage.put("producttypessumitemquantitysegmentstests", new LinkedHashMap<String,String>()); }
				if (usage.get("producttypessumitemtotalsegmentstests") == null) { usage.put("producttypessumitemtotalsegmentstests", new LinkedHashMap<String,String>()); }
				usage.put("producttypescountcustomerssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountcustomerssegmentstests"),(LinkedHashMap<String,String>)output.get("producttypescountcustomerssegmentstests")));
				usage.put("producttypescountorderssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountorderssegmentstests"),(LinkedHashMap<String,String>)output.get("producttypescountorderssegmentstests")));
				usage.put("producttypescountorderitemssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountorderitemssegmentstests"),(LinkedHashMap<String,String>)output.get("producttypescountorderitemssegmentstests")));
				usage.put("producttypessumitemquantitysegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypessumitemquantitysegmentstests"),(LinkedHashMap<String,String>)output.get("producttypessumitemquantitysegmentstests")));
				usage.put("producttypessumitemtotalsegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypessumitemtotalsegmentstests"),(LinkedHashMap<String,String>)output.get("producttypessumitemtotalsegmentstests")));
			}
		}
		return usage;
	}



	private String columns2group(String columns) {
		String group = "" + columns;
		group = group.replaceAll("(^|,)'.*?' as ([a-zA-Z0-9]+?)(,|$)", "$1$2$3");
		group = group.replaceAll("(^|,).+? as ([a-zA-Z0-9]+?)(,|$)", "$1$2$3");
		return group;
	}



	private String SQLcustomers(String database, String columns, String from, String where, String order) {
		String group = columns2group(columns);
		if (order.equals("")) {
			order = "1 desc";
		}
		String SQL = "";
//QQQ		if ((DB.db_type(database).equals("access")) || (DB.db_type(database).equals("mssql"))) {
//QQQ			if (! group.equals("")) {
//QQQ				SQL = SQLselectCustomersAccess + ", " + columns + " from (select " + columns + ", clienthost from " + from + " where orders.user_id is not null and orders.user_id>0 and " + where + " group by " + group + ", clienthost) as clienthosts group by " + group + " order by " + order;
//QQQ			} else {
//QQQ				SQL = SQLselectCustomersAccess + ", " + columns + " from (select " + columns + ", clienthost from " + from + " where orders.user_id is not null and orders.user_id>0 and " + where + " group by 1, clienthost) as clienthosts group by 2 order by " + order;
//QQQ			}
//QQQ		} else {
			if (! group.equals("")) {
				SQL = SQLselectCustomers + ", " + columns + " from " + from + " where " + where + " group by " + group + " order by " + order;
			} else {
				SQL = SQLselectCustomers + ", " + columns + " from " + from + " where orders.user_id is not null and orders.user_id>0 and " + where + " group by 2 order by " + order;
			}
//QQQ		}
		return SQL;
	}



	private String SQLorders(String database, String columns, String from, String where, String order) {
		String group = columns2group(columns);
		if (order.equals("")) {
			order = "1 desc";
		}
		String SQL = "";
//QQQ		if ((DB.db_type(database).equals("access")) || (DB.db_type(database).equals("mssql"))) {
//QQQ			if (! group.equals("")) {
//QQQ				SQL = SQLselectOrdersAccess + ", " + columns + " from (select " + columns + ", clienthost from " + from + " where " + where + " group by " + group + ", clienthost) as clienthosts group by " + group + " order by " + order;
//QQQ			} else {
//QQQ				SQL = SQLselectOrdersAccess + ", " + columns + " from (select " + columns + ", clienthost from " + from + " where " + where + " group by 1, clienthost) as clienthosts group by 2 order by " + order;
//QQQ			}
//QQQ		} else {
			if (! group.equals("")) {
				SQL = SQLselectOrders + ", " + columns + " from " + from + " where " + where + " group by " + group + " order by " + order;
			} else {
				SQL = SQLselectOrders + ", " + columns + " from " + from + " where " + where + " group by 2 order by " + order;
			}
//QQQ		}
		return SQL;
	}



	private String SQLorderitems(String database, String columns, String from, String where, String order) {
		String group = columns2group(columns);
		int lastcolumnnumber = (SQLselectOrderitems + ", " + columns).split(",").length;
		if (order.equals("")) {
			order = "1 desc";
		}
		String SQL = "";
//QQQ		if ((DB.db_type(database).equals("access")) || (DB.db_type(database).equals("mssql"))) {
//QQQ			if (! group.equals("")) {
//QQQ				SQL = SQLselectOrderitemsAccess + ", " + columns + " from (select " + columns + ", sessionid from " + from + " where " + where + " group by " + group + ", sessionid) as sessionids group by " + group + " order by " + order;
//QQQ			} else {
//QQQ				SQL = SQLselectOrderitemsAccess + ", " + columns + " from (select " + columns + ", sessionid from " + from + " where " + where + " group by 1, sessionid) as sessionids group by 2 order by " + order;
//QQQ			}
//QQQ		} else {
			if (! group.equals("")) {
				SQL = SQLselectOrderitems + ", " + columns + " from " + from + " where " + where + " group by " + group + " order by " + order;
			} else {
				SQL = SQLselectOrderitems + ", " + columns + " from " + from + " where " + where + " group by " + lastcolumnnumber + " order by " + order;
			}
//QQQ		}
		return SQL;
	}



	private String SQLvisitsduration(String columns, String from, String where, String order) {
		String group = columns2group(columns);
		if (order.equals("")) {
			order = "1 desc";
		}
		String SQL = "";
		if (! group.equals("")) {
			SQL = SQLselectOrderitems + ", " + columns + ", max(sessionduration) as maxsessionduration from " + from + " where " + where + " group by " + group + " order by " + order;
		} else {
			SQL = SQLselectOrderitems + ", " + columns + ", max(sessionduration) as maxsessionduration from " + from + " where " + where + " group by 2 order by " + order;
		}
		return SQL;
	}



	private String SQLitemquantity(String database, String columns, String from, String where, String order) {
		String group = columns2group(columns);
		if (order.equals("")) {
			order = "1 desc";
		}
		String SQL = "";
		if (! group.equals("")) {
			SQL = SQLselectItemQuantity + ", " + group + " from " + from + " where " + where + " group by " + group + " order by " + order;
		} else {
			SQL = SQLselectItemQuantity + ", " + columns + " from " + from + " where " + where + " group by 2 order by " + order;
		}
		return SQL;
	}



	private String SQLitemtotal(Session mysession, String database, String columns, String from, String where, String order) {
		String group = columns2group(columns);
		if (order.equals("")) {
			order = "1 desc";
		}
		String SQL = "";
		if ((mysession.get("admin_sales_currency").equals("")) || (mysession.get("admin_sales_currency").equals("*"))) {
			if (! group.equals("")) {
				SQL = SQLselectItemTotalBase + ", " + columns + " from " + from + " where " + where + " group by " + group + " order by " + order;
			} else {
				SQL = SQLselectItemTotalBase + ", " + columns + " from " + from + " where " + where + " group by 2 order by " + order;
			}
		} else {
			if (! group.equals("")) {
				SQL = SQLselectItemTotal + ", " + columns + " from " + from + " where " + where + " group by " + group + " order by " + order;
			} else {
				SQL = SQLselectItemTotal + ", " + columns + " from " + from + " where " + where + " group by 2 order by " + order;
			}
		}
		return SQL;
	}



	private void setSessionCurrencyFromRequest(Session mysession, Request myrequest) {
		if (myrequest.getParameter("currency").equals(" ")) {
			mysession.set("admin_sales_currency", "");
		} else if (! myrequest.getParameter("currency").equals("")) {
			mysession.set("admin_sales_currency", html.encodeHtmlEntities(myrequest.getParameter("currency")));
		}
	}



	private void setSessionLimitFromRequest(Session mysession, Request myrequest) {
		if (myrequest.getParameter("limit").equals(" ")) {
			mysession.set("admin_sales_limit", "");
		} else if (! myrequest.getParameter("limit").equals("")) {
			mysession.set("admin_sales_limit", html.encodeHtmlEntities(myrequest.getParameter("limit")));
		}
	}



	private void setSessionPeriodFromRequest(Session mysession, Request myrequest) {
		if (! myrequest.getParameter("period").equals("")) {
			mysession.set("admin_sales_period", html.encodeHtmlEntities(myrequest.getParameter("period")));
		}
		if (! myrequest.getParameter("period_start").equals("")) {
			mysession.set("admin_sales_period_start", html.encodeHtmlEntities(myrequest.getParameter("period_start")));
		} else if (! myrequest.getParameter("period").equals("")) {
			mysession.set("admin_sales_period_start", "");
		}
		if (! myrequest.getParameter("period_end").equals("")) {
			mysession.set("admin_sales_period_end", html.encodeHtmlEntities(myrequest.getParameter("period_end")));
		} else if (! myrequest.getParameter("period").equals("")) {
			mysession.set("admin_sales_period_end", "");
		}
	}



	private String SQLwherePeriod(DB db, Session mysession, Date mynow, String prefix) {
		String SQLwhere = "";
		if ((! mysession.get("admin_sales_period_start").equals("")) || (! mysession.get("admin_sales_period_end").equals(""))) {
			if (! mysession.get("admin_sales_period_start").equals("")) {
				if (! SQLwhere.equals("")) SQLwhere += " and ";
				SQLwhere += "" + prefix + "orders.created >= " + db.quote(Common.SQL_clean(mysession.get("admin_sales_period_start")));
			}
			if (! mysession.get("admin_sales_period_end").equals("")) {
				if (! SQLwhere.equals("")) SQLwhere += " and ";
				SQLwhere += "" + prefix + "orders.created <= " + db.quote(Common.SQL_clean(mysession.get("admin_sales_period_end")));
			}
		} else if (mysession.get("admin_sales_period").equals("now")) {
			Calendar periodstart = Calendar.getInstance();
			periodstart.setTime(mynow);
			periodstart.add(Calendar.SECOND, -30*60);	// 30 minutes
			Calendar periodend = Calendar.getInstance();
			periodend.setTime(mynow);
			SQLwhere = "" + prefix + "orders.created > " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodstart.getTime()));
			SQLwhere = SQLwhere + " and " + prefix + "orders.created <= " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodend.getTime()));
		} else if (mysession.get("admin_sales_period").equals("today")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			SQLwhere = "" + prefix + "createdyear = " + period.get(Calendar.YEAR) + " and " + prefix + "createdmonth = " + (period.get(Calendar.MONTH)+1) + " and " + prefix + "createdday = " + period.get(Calendar.DAY_OF_MONTH);
		} else if (mysession.get("admin_sales_period").equals("last24hours")) {
			Calendar periodstart = Calendar.getInstance();
			periodstart.setTime(mynow);
			periodstart.add(Calendar.SECOND, -24*60*60);
			Calendar periodend = Calendar.getInstance();
			periodend.setTime(mynow);
			SQLwhere = "" + prefix + "orders.created > " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodstart.getTime()));
			SQLwhere = SQLwhere + " and " + prefix + "orders.created <= " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodend.getTime()));
		} else if (mysession.get("admin_sales_period").equals("yesterday")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.SECOND, -24*60*60);
			SQLwhere = "" + prefix + "createdyear = " + period.get(Calendar.YEAR) + " and " + prefix + "createdmonth = " + (period.get(Calendar.MONTH)+1) + " and " + prefix + "createdday = " + period.get(Calendar.DAY_OF_MONTH);
		} else if (mysession.get("admin_sales_period").equals("thisweek")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			long week = period.get(Calendar.WEEK_OF_YEAR);
			SQLwhere = "" + prefix + "createdyear = " + period.get(Calendar.YEAR) + " and " + prefix + "createdweek = " + week;
		} else if (mysession.get("admin_sales_period").equals("last7days")) {
			Calendar periodstart = Calendar.getInstance();
			periodstart.setTime(mynow);
			periodstart.add(Calendar.SECOND, -7*24*60*60);
			Calendar periodend = Calendar.getInstance();
			periodend.setTime(mynow);
			SQLwhere = "" + prefix + "orders.created > " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodstart.getTime()));
			SQLwhere = SQLwhere + " and " + prefix + "orders.created <= " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodend.getTime()));
		} else if (mysession.get("admin_sales_period").equals("lastweek")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.SECOND, -7*24*60*60);
			long week = period.get(Calendar.WEEK_OF_YEAR);
			SQLwhere = "" + prefix + "createdyear = " + period.get(Calendar.YEAR) + " and " + prefix + "createdweek = " + week;
		} else if (mysession.get("admin_sales_period").equals("last14days")) {
			Calendar periodstart = Calendar.getInstance();
			periodstart.setTime(mynow);
			periodstart.add(Calendar.SECOND, -14*24*60*60);
			Calendar periodend = Calendar.getInstance();
			periodend.setTime(mynow);
			SQLwhere = "" + prefix + "orders.created > " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodstart.getTime()));
			SQLwhere = SQLwhere + " and " + prefix + "orders.created <= " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodend.getTime()));
		} else if (mysession.get("admin_sales_period").equals("thismonth")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			SQLwhere = "" + prefix + "createdyear = " + period.get(Calendar.YEAR) + " and " + prefix + "createdmonth = " + (period.get(Calendar.MONTH)+1);
		} else if (mysession.get("admin_sales_period").equals("last30days")) {
			Calendar periodstart = Calendar.getInstance();
			periodstart.setTime(mynow);
			periodstart.add(Calendar.MONTH, -1);
			Calendar periodend = Calendar.getInstance();
			periodend.setTime(mynow);
			SQLwhere = "" + prefix + "orders.created > " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodstart.getTime()));
			SQLwhere = SQLwhere + " and " + prefix + "orders.created <= " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodend.getTime()));
		} else if (mysession.get("admin_sales_period").equals("lastmonth")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.MONTH, -1);
			SQLwhere = "" + prefix + "createdyear = " + period.get(Calendar.YEAR) + " and " + prefix + "createdmonth = " + (period.get(Calendar.MONTH)+1);
		} else if (mysession.get("admin_sales_period").equals("thisquarter")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			long period_mon = (long)(Math.floor(period.get(Calendar.MONTH)/3)*3)+1;
			SQLwhere = "" + prefix + "createdyear = " + period.get(Calendar.YEAR) + " and (" + prefix + "createdmonth >= " + period_mon + " and " + prefix + "createdmonth <= " + (period_mon+2) + ")";
		} else if (mysession.get("admin_sales_period").equals("last3months")) {
			Calendar periodstart = Calendar.getInstance();
			periodstart.setTime(mynow);
			periodstart.add(Calendar.MONTH, -3);
			Calendar periodend = Calendar.getInstance();
			periodend.setTime(mynow);
			SQLwhere = "" + prefix + "orders.created > " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodstart.getTime()));
			SQLwhere = SQLwhere + " and " + prefix + "orders.created <= " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodend.getTime()));
		} else if (mysession.get("admin_sales_period").equals("lastquarter")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.MONTH, -3);
			long period_mon = (long)(Math.floor(period.get(Calendar.MONTH)/3)*3)+1;
			SQLwhere = "" + prefix + "createdyear = " + period.get(Calendar.YEAR) + " and (" + prefix + "createdmonth >= " + period_mon + " and " + prefix + "createdmonth <= " + (period_mon+2) + ")";
		} else if (mysession.get("admin_sales_period").equals("thishalfyear")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			long period_mon = (long)(Math.floor(period.get(Calendar.MONTH)/6)*6)+1;
			SQLwhere = "" + prefix + "createdyear = " + period.get(Calendar.YEAR) + " and (" + prefix + "createdmonth >= " + period_mon + " and " + prefix + "createdmonth <= " + (period_mon+5) + ")";
		} else if (mysession.get("admin_sales_period").equals("last6months")) {
			Calendar periodstart = Calendar.getInstance();
			periodstart.setTime(mynow);
			periodstart.add(Calendar.MONTH, -6);
			Calendar periodend = Calendar.getInstance();
			periodend.setTime(mynow);
			SQLwhere = "" + prefix + "orders.created > " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodstart.getTime()));
			SQLwhere = SQLwhere + " and " + prefix + "orders.created <= " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodend.getTime()));
		} else if (mysession.get("admin_sales_period").equals("lasthalfyear")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.MONTH, -6);
			long period_mon = (long)(Math.floor(period.get(Calendar.MONTH)/6)*6)+1;
			SQLwhere = "" + prefix + "createdyear = " + period.get(Calendar.YEAR) + " and (" + prefix + "createdmonth >= " + period_mon + " and " + prefix + "createdmonth <= " + (period_mon+5) + ")";
		} else if (mysession.get("admin_sales_period").equals("thisyear")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			SQLwhere = "" + prefix + "createdyear = " + period.get(Calendar.YEAR);
		} else if (mysession.get("admin_sales_period").equals("last12months")) {
			Calendar periodstart = Calendar.getInstance();
			periodstart.setTime(mynow);
			periodstart.add(Calendar.SECOND, -365*24*60*60);
			Calendar periodend = Calendar.getInstance();
			periodend.setTime(mynow);
			SQLwhere = "" + prefix + "orders.created > " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodstart.getTime()));
			SQLwhere = SQLwhere + " and " + prefix + "orders.created <= " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodend.getTime()));
		} else if (mysession.get("admin_sales_period").equals("lastyear")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.YEAR, -1);
			SQLwhere = "" + prefix + "createdyear = " + period.get(Calendar.YEAR);
		} else if (mysession.get("admin_sales_period").equals("all")) {
			SQLwhere = "1=1";
		} else {
			Calendar periodstart = Calendar.getInstance();
			periodstart.setTime(mynow);
			periodstart.add(Calendar.SECOND, -30*60);	// 30 minutes
			Calendar periodend = Calendar.getInstance();
			periodend.setTime(mynow);
			SQLwhere = "" + prefix + "orders.created > " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodstart.getTime()));
			SQLwhere = SQLwhere + " and " + prefix + "orders.created <= " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodend.getTime()));
		}
		return SQLwhere;
	}



	private long[] periodStartEnd(DB db, Session mysession, Date mynow) {
		String save_admin_sales_period_start = mysession.get("admin_sales_period_start");
		String save_admin_sales_period_end = mysession.get("admin_sales_period_end");
		if ((mysession.get("admin_sales_period").equals("all")) && (mysession.get("admin_sales_period_start").equals("")) && (mysession.get("admin_sales_period_end").equals(""))) {
			mysession.set("admin_sales_period_start", db.query_string("select min(created) from orders"));
			mysession.set("admin_sales_period_end", "");
		}
		long[] mydata = periodStartEnd(mysession, mynow);
		mysession.set("admin_sales_period_start", save_admin_sales_period_start);
		mysession.set("admin_sales_period_end", save_admin_sales_period_end);
		return mydata;
	}
	private long[] periodStartEnd(Session mysession, Date mynow) {
		long periodstart;
		long periodend;
		if ((! mysession.get("admin_sales_period_start").equals("")) || (! mysession.get("admin_sales_period_end").equals(""))) {
			// "all"
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.DAY_OF_MONTH, -(period.get(Calendar.DAY_OF_MONTH)-1));
			period.add(Calendar.MONTH, -(period.get(Calendar.MONTH)));
			period.add(Calendar.MONTH, -12);
			periodstart = period.getTimeInMillis()/1000;
			periodend = mynow.getTime()/1000;

			if (! mysession.get("admin_sales_period_start").equals("")) {
				periodstart = Common.strtodate(mysession.get("admin_sales_period_start")).getTime()/1000;
			}
			if (! mysession.get("admin_sales_period_end").equals("")) {
				periodend = Common.strtodate(mysession.get("admin_sales_period_end")).getTime()/1000;
			}
		} else if (mysession.get("admin_sales_period").equals("now")) {
			periodstart = mynow.getTime()/1000 -30*60;	// 30 minutes
			periodend = mynow.getTime()/1000;
		} else if (mysession.get("admin_sales_period").equals("today")) {
			periodstart = mynow.getTime()/1000;
			periodend = mynow.getTime()/1000;
		} else if (mysession.get("admin_sales_period").equals("last24hours")) {
			periodstart = mynow.getTime()/1000 -24*60*60;
			periodend = mynow.getTime()/1000;
		} else if (mysession.get("admin_sales_period").equals("yesterday")) {
			periodstart = mynow.getTime()/1000 -24*60*60;
			periodend = mynow.getTime()/1000 -24*60*60;
		} else if (mysession.get("admin_sales_period").equals("thisweek")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			if (period.get(Calendar.DAY_OF_WEEK) == 1) {
				periodstart = period.getTimeInMillis()/1000 -6*24*60*60;
			} else {
				periodstart = period.getTimeInMillis()/1000 -(period.get(Calendar.DAY_OF_WEEK)-2)*24*60*60;
			}
			if (period.get(Calendar.DAY_OF_WEEK) == 1) {
				periodend = period.getTimeInMillis()/1000;
			} else {
				periodend = period.getTimeInMillis()/1000 +(8-period.get(Calendar.DAY_OF_WEEK))*24*60*60;
			}
		} else if (mysession.get("admin_sales_period").equals("last7days")) {
			periodstart = mynow.getTime()/1000 -6*24*60*60;
			periodend = mynow.getTime()/1000;
		} else if (mysession.get("admin_sales_period").equals("lastweek")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.SECOND, -7*24*60*60);
			if (period.get(Calendar.DAY_OF_WEEK) == 1) {
				periodstart = period.getTimeInMillis()/1000 -6*24*60*60;
			} else {
				periodstart = period.getTimeInMillis()/1000 -(period.get(Calendar.DAY_OF_WEEK)-2)*24*60*60;
			}
			if (period.get(Calendar.DAY_OF_WEEK) == 1) {
				periodend = period.getTimeInMillis()/1000;
			} else {
				periodend = period.getTimeInMillis()/1000 +(8-period.get(Calendar.DAY_OF_WEEK))*24*60*60;
			}
		} else if (mysession.get("admin_sales_period").equals("last14days")) {
			periodstart = mynow.getTime()/1000 -13*24*60*60;
			periodend = mynow.getTime()/1000;
		} else if (mysession.get("admin_sales_period").equals("thismonth")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.DAY_OF_MONTH, -(period.get(Calendar.DAY_OF_MONTH)-1));
			periodstart = period.getTimeInMillis()/1000;
			period.add(Calendar.MONTH, 1);
			period.add(Calendar.DAY_OF_MONTH, -1);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_sales_period").equals("last30days")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.MONTH, -1);
			periodstart = period.getTimeInMillis()/1000;
			period.setTime(mynow);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_sales_period").equals("lastmonth")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.DAY_OF_MONTH, -(period.get(Calendar.DAY_OF_MONTH)-1));
			period.add(Calendar.MONTH, -1);
			periodstart = period.getTimeInMillis()/1000;
			period.add(Calendar.MONTH, 1);
			period.add(Calendar.DAY_OF_MONTH, -1);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_sales_period").equals("thisquarter")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.DAY_OF_MONTH, -(period.get(Calendar.DAY_OF_MONTH)-1));
			period.add(Calendar.MONTH, (int)-(period.get(Calendar.MONTH)-((Math.floor(period.get(Calendar.MONTH)/3)*3))));
			periodstart = period.getTimeInMillis()/1000;
			period.add(Calendar.MONTH, 3);
			period.add(Calendar.DAY_OF_MONTH, -1);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_sales_period").equals("last3months")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.MONTH, -3);
			period.add(Calendar.DAY_OF_MONTH, 1);
			periodstart = period.getTimeInMillis()/1000;
			period.setTime(mynow);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_sales_period").equals("lastquarter")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.DAY_OF_MONTH, -(period.get(Calendar.DAY_OF_MONTH)-1));
			period.add(Calendar.MONTH, -3);
			period.add(Calendar.MONTH, (int)-(period.get(Calendar.MONTH)-((Math.floor(period.get(Calendar.MONTH)/3)*3))));
			periodstart = period.getTimeInMillis()/1000;
			period.add(Calendar.MONTH, 3);
			period.add(Calendar.DAY_OF_MONTH, -1);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_sales_period").equals("thishalfyear")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.DAY_OF_MONTH, -(period.get(Calendar.DAY_OF_MONTH)-1));
			period.add(Calendar.MONTH, (int)-(period.get(Calendar.MONTH)-((Math.floor(period.get(Calendar.MONTH)/6)*6))));
			periodstart = period.getTimeInMillis()/1000;
			period.add(Calendar.MONTH, 6);
			period.add(Calendar.DAY_OF_MONTH, -1);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_sales_period").equals("last6months")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.MONTH, -6);
			period.add(Calendar.DAY_OF_MONTH, 1);
			periodstart = period.getTimeInMillis()/1000;
			period.setTime(mynow);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_sales_period").equals("lasthalfyear")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.DAY_OF_MONTH, -(period.get(Calendar.DAY_OF_MONTH)-1));
			period.add(Calendar.MONTH, -6);
			period.add(Calendar.MONTH, (int)-(period.get(Calendar.MONTH)-((Math.floor(period.get(Calendar.MONTH)/6)*6))));
			periodstart = period.getTimeInMillis()/1000;
			period.add(Calendar.MONTH, 6);
			period.add(Calendar.DAY_OF_MONTH, -1);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_sales_period").equals("thisyear")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.DAY_OF_MONTH, -(period.get(Calendar.DAY_OF_MONTH)-1));
			period.add(Calendar.MONTH, -(period.get(Calendar.MONTH)));
			periodstart = period.getTimeInMillis()/1000;
			period.add(Calendar.MONTH, 12);
			period.add(Calendar.DAY_OF_MONTH, -1);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_sales_period").equals("last12months")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.MONTH, -12);
			period.add(Calendar.DAY_OF_MONTH, 1);
			periodstart = period.getTimeInMillis()/1000;
			period.setTime(mynow);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_sales_period").equals("lastyear")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.DAY_OF_MONTH, -(period.get(Calendar.DAY_OF_MONTH)-1));
			period.add(Calendar.MONTH, -12);
			period.add(Calendar.MONTH, -(period.get(Calendar.MONTH)));
			periodstart = period.getTimeInMillis()/1000;
			period.add(Calendar.MONTH, 12);
			period.add(Calendar.DAY_OF_MONTH, -1);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_sales_period").equals("all")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.DAY_OF_MONTH, -(period.get(Calendar.DAY_OF_MONTH)-1));
			period.add(Calendar.MONTH, -(period.get(Calendar.MONTH)));
			period.add(Calendar.MONTH, -12);
			periodstart = period.getTimeInMillis()/1000;
			periodend = mynow.getTime()/1000;
		} else {
			periodstart = mynow.getTime()/1000 -30*60;	// 30 minutes
			periodend = mynow.getTime()/1000;
		}
		long[] periods = { periodstart, periodend };
		return periods;
	}



	private Long[] periodDays(Session mysession, Date mynow) {
		ArrayList<Long> days = new ArrayList<Long>();

		long[] period = periodStartEnd(mysession, mynow);
		long periodstart = period[0];
		long periodend = period[1];

		long myday = periodstart;
		while (myday <= periodend) {
			days.add(new Long(myday));
			myday += 24*60*60;
		}
		Calendar lastday = Calendar.getInstance();
		lastday.setTime(new Date(Common.integernumber("" + days.get(days.size()-1))*1000));
		Calendar myperiodend = Calendar.getInstance();
		myperiodend.setTime(new Date(periodend*1000));
		if (lastday.get(Calendar.DAY_OF_MONTH) != myperiodend.get(Calendar.DAY_OF_MONTH)) {
			days.add(new Long(periodend));
		}
		return (Long[])days.toArray(new Long[0]);
	}



	private Long[] periodWeeks(Session mysession, Date mynow) {
		ArrayList<Long> weeks = new ArrayList<Long>();

		long[] period = periodStartEnd(mysession, mynow);
		long periodstart = period[0];
		long periodend = period[1];

		long myweek = periodstart;
		while (myweek <= periodend) {
			weeks.add(new Long(myweek));
			myweek += 7*24*60*60;
		}
		Calendar lastweek = Calendar.getInstance();
		lastweek.setTime(new Date(Common.integernumber("" + weeks.get(weeks.size()-1))*1000));
		Calendar myperiodend = Calendar.getInstance();
		myperiodend.setTime(new Date(periodend*1000));
		if (lastweek.get(Calendar.WEEK_OF_YEAR) != myperiodend.get(Calendar.WEEK_OF_YEAR)) {
			weeks.add(new Long(periodend));
		}
		return (Long[])weeks.toArray(new Long[0]);
	}



	private Long[] periodMonths(Session mysession, Date mynow) {
		ArrayList<Long> months = new ArrayList<Long>();

		long[] period = periodStartEnd(mysession, mynow);
		long periodstart = period[0];
		long periodend = period[1];

		Calendar mymonth = Calendar.getInstance();
		mymonth.setTime(new Date(periodstart*1000));
		while (mymonth.getTime().getTime()/1000 <= periodend) {
			months.add(new Long(mymonth.getTime().getTime()/1000));
			mymonth.add(Calendar.MONTH, 1);
		}
		Calendar lastmonth = Calendar.getInstance();
		lastmonth.setTime(new Date(Common.integernumber("" + months.get(months.size()-1))*1000));
		Calendar myperiodend = Calendar.getInstance();
		myperiodend.setTime(new Date(periodend*1000));
		if (lastmonth.get(Calendar.MONTH) != myperiodend.get(Calendar.MONTH)) {
			months.add(new Long(periodend));
		}
		return (Long[])months.toArray(new Long[0]);
	}



	private Long[] periodYears(Session mysession, Date mynow) {
		ArrayList<Long> years = new ArrayList<Long>();

		long[] period = periodStartEnd(mysession, mynow);
		long periodstart = period[0];
		long periodend = period[1];

		Calendar myyear = Calendar.getInstance();
		myyear.setTime(new Date(periodstart*1000));
		while (myyear.getTime().getTime()/1000 <= periodend) {
			years.add(new Long(myyear.getTime().getTime()/1000));
			myyear.add(Calendar.YEAR, 1);
		}
		Calendar lastyear = Calendar.getInstance();
		lastyear.setTime(new Date(Common.integernumber("" + years.get(years.size()-1))*1000));
		Calendar myperiodend = Calendar.getInstance();
		myperiodend.setTime(new Date(periodend*1000));
		if (lastyear.get(Calendar.YEAR) != myperiodend.get(Calendar.YEAR)) {
			years.add(new Long(periodend));
		}
		return (Long[])years.toArray(new Long[0]);
	}



	private Long[] allHours(Session mysession, Date mynow) {
		ArrayList<Long> hours = new ArrayList<Long>();
		for (int i=0; i<=23; i++) {
			hours.add(new Long(i));
		}
		return (Long[])hours.toArray(new Long[0]);
	}



	private Long[] allDays(Session mysession, Date mynow) {
		ArrayList<Long> days = new ArrayList<Long>();
		for (int i=1; i<=31; i++) {
			days.add(new Long(i));
		}
		return (Long[])days.toArray(new Long[0]);
	}



	private Long[] allWeekdays(Session mysession, Date mynow) {
		ArrayList<Long> weekdays = new ArrayList<Long>();
		for (int i=1; i<=7; i++) {
			weekdays.add(new Long(i));
		}
		return (Long[])weekdays.toArray(new Long[0]);
	}



	private Long[] allWeeks(Session mysession, Date mynow) {
		ArrayList<Long> weeks = new ArrayList<Long>();
		for (int i=1; i<=53; i++) {
			weeks.add(new Long(i));
		}
		return (Long[])weeks.toArray(new Long[0]);
	}



	private Long[] allMonths(Session mysession, Date mynow) {
		ArrayList<Long> months = new ArrayList<Long>();
		for (int i=1; i<=12; i++) {
			months.add(new Long(i));
		}
		return (Long[])months.toArray(new Long[0]);
	}



	public double maxValue(HashMap<String,String> usage) {
		double maxvalue = 1;
		if (usage != null) {
			Iterator myusage = usage.keySet().iterator();
			while (myusage.hasNext()) {
				String i = "" + myusage.next();
				double value = Common.number("0" + usage.get(i));
				if (value > maxvalue) {
					maxvalue = value;
				}
			}
		}
		return maxvalue;
	}



	public double totalValue(HashMap<String,String> usage) {
		double totalvalue = 0;
		if (usage != null) {
			Iterator myusage = usage.keySet().iterator();
			while (myusage.hasNext()) {
				String i = "" + myusage.next();
				double value = Common.number("0" + usage.get(i));
				totalvalue = totalvalue + value;
			}
		}
		return totalvalue;
	}



	public long countValues(HashMap<String,String> usage) {
		long countvalues = 0;
		if (usage != null) {
			Iterator myusage = usage.keySet().iterator();
			while (myusage.hasNext()) {
				String i = "" + myusage.next();
				countvalues = countvalues + 1;
			}
		}
		return countvalues;
	}



	public String countryVariant(String key) {
		String[] tmp = key.split(":::");
		String country = "" + key;
		String usersegments = "";
		String usertests = "";
		if (tmp.length > 0) country = tmp[0];
		if (tmp.length > 1) usersegments = tmp[1];
		if (tmp.length > 2) usertests = tmp[2];
		usersegments = usersegments.replaceAll("::", "=");
		usertests = usertests.replaceAll("::", "=");
		String myvariant = usersegments;
		if (! usertests.equals("")) {
			if (! myvariant.equals("")) myvariant += ":::";
			myvariant += usertests;
		}
		if (myvariant.equals("")) {
			return "&nbsp;";
		} else {
			return myvariant;
		}
	}



	public String countryDomain(String key) {
		String[] tmp = key.split(":::");
		String country = "" + key;
		String usersegments = "";
		String usertests = "";
		if (tmp.length > 0) country = tmp[0];
		if (tmp.length > 1) usersegments = tmp[1];
		if (tmp.length > 2) usertests = tmp[2];
		country = country.replaceAll(":::", "");
		if (country.equals("")) {
			country = "&nbsp;";
		} else {
			country = "(" + country + ")";
		}
		return country;
	}



	public String countryImage(String key) {
		String[] tmp = key.split(":::");
		String country = "" + key;
		String usersegments = "";
		String usertests = "";
		if (tmp.length > 0) country = tmp[0];
		if (tmp.length > 1) usersegments = tmp[1];
		if (tmp.length > 2) usertests = tmp[2];
		country = country.replaceAll(":::", "");
		if (country.equals("")) country = "unknown";
		return country;
	}



	public String countryName(String key) {
		String[] tmp = key.split(":::");
		String country = "" + key;
		String usersegments = "";
		String usertests = "";
		if (tmp.length > 0) country = tmp[0];
		if (tmp.length > 1) usersegments = tmp[1];
		if (tmp.length > 2) usertests = tmp[2];
		country = country.replaceAll(":::", "");

		String countryname = "unknown";

		if (country.equals("com")) return "Commercial";
		if (country.equals("org")) return "Organisations";
		if (country.equals("net")) return "Network";

		if (country.equals("edu")) return "USA Educational";
		if (country.equals("gov")) return "USA Government";

		if (country.equals("de")) return "Germany";
		if (country.equals("es")) return "Spain";
		if (country.equals("fr")) return "France";
		if (country.equals("gb")) return "United Kingdom";
		if (country.equals("it")) return "Italy";
		if (country.equals("uk")) return "United Kingdom";

		if (country.equals("au")) return "Australia";
		if (country.equals("br")) return "Brazil";
		if (country.equals("ca")) return "Canada";
		if (country.equals("il")) return "Israel";
		if (country.equals("in")) return "India";
		if (country.equals("jp")) return "Japan";
		if (country.equals("mx")) return "Mexico";
		if (country.equals("nz")) return "New Zealand";
		if (country.equals("sa")) return "Saudi Arabia";
		if (country.equals("tr")) return "Turkey";
		if (country.equals("us")) return "United States";
		if (country.equals("za")) return "South Africa";

		if (country.equals("at")) return "Austria";
		if (country.equals("be")) return "Belgium";
		if (country.equals("ch")) return "Switzerland";
		if (country.equals("dk")) return "Denmark";
		if (country.equals("fi")) return "Finland";
		if (country.equals("gr")) return "Greece";
		if (country.equals("ie")) return "Ireland";
		if (country.equals("nl")) return "Netherlands";
		if (country.equals("no")) return "Norway";
		if (country.equals("pt")) return "Portugal";
		if (country.equals("se")) return "Sweden";

		if (country.equals("ee")) return "Estonia";
		if (country.equals("lt")) return "Lithuania";
		if (country.equals("lv")) return "Latvia";
		if (country.equals("ru")) return "Russian Federation";
		if (country.equals("ua")) return "Ukraine";

		if (country.equals("ad")) return "Andorra";
		if (country.equals("cy")) return "Cyprus";
		if (country.equals("fo")) return "Faroe Islands";
		if (country.equals("gl")) return "Greenland";
		if (country.equals("is")) return "Iceland";
		if (country.equals("je")) return "Jersey";
		if (country.equals("li")) return "Liechtenstein";
		if (country.equals("lu")) return "Luxembourg";
		if (country.equals("mc")) return "Monaco";
		if (country.equals("va")) return "Holy See (Vatican City State)";

		if (country.equals("al")) return "Albania";
		if (country.equals("ba")) return "Bosnia and Herzegovina";
		if (country.equals("bg")) return "Bulgaria";
		if (country.equals("cs")) return "Serbia and Montenegro";
		if (country.equals("cz")) return "Czech Republic";
		if (country.equals("hr")) return "Croatia/Hrvatska";
		if (country.equals("hu")) return "Hungary";
		if (country.equals("mk")) return "Macedonia, The Former Yugoslav Republic of";
		if (country.equals("pl")) return "Poland";
		if (country.equals("ro")) return "Romania";
		if (country.equals("si")) return "Slovenia";
		if (country.equals("sk")) return "Slovak Republic";
		if (country.equals("yu")) return "Yugoslavia";

		if (country.equals("aero")) return "Air-transport industry";
		if (country.equals("biz")) return "Businesses";
		if (country.equals("coop")) return "Cooperative associations";
		if (country.equals("info")) return "Information";
		if (country.equals("museum")) return "Museums";
		if (country.equals("name")) return "Individuals";
		if (country.equals("pro")) return "Professionals";

		if (country.equals("ac")) return "Ascension Island";
		if (country.equals("ae")) return "United Arab Emirates";
		if (country.equals("af")) return "Afghanistan";
		if (country.equals("ag")) return "Antigua and Barbuda";
		if (country.equals("ai")) return "Anguilla";
		if (country.equals("am")) return "Armenia";
		if (country.equals("an")) return "Netherlands Antilles";
		if (country.equals("ao")) return "Angola";
		if (country.equals("aq")) return "Antarctica";
		if (country.equals("ar")) return "Argentina";
		if (country.equals("as")) return "American Samoa";
		if (country.equals("aw")) return "Aruba";
		if (country.equals("ax")) return "Aland Islands";
		if (country.equals("az")) return "Azerbaijan";
		if (country.equals("bb")) return "Barbados";
		if (country.equals("bd")) return "Bangladesh";
		if (country.equals("bf")) return "Burkina Faso";
		if (country.equals("bh")) return "Bahrain";
		if (country.equals("bi")) return "Burundi";
		if (country.equals("bj")) return "Benin";
		if (country.equals("bm")) return "Bermuda";
		if (country.equals("bn")) return "Brunei Darussalam";
		if (country.equals("bo")) return "Bolivia";
		if (country.equals("bs")) return "Bahamas";
		if (country.equals("bt")) return "Bhutan";
		if (country.equals("bv")) return "Bouvet Island";
		if (country.equals("bw")) return "Botswana";
		if (country.equals("by")) return "Belarus";
		if (country.equals("bz")) return "Belize";
		if (country.equals("cc")) return "Cocos (Keeling) Islands";
		if (country.equals("cd")) return "Congo, The Democratic Republic of the";
		if (country.equals("cf")) return "Central African Republic";
		if (country.equals("cg")) return "Congo, Republic of";
		if (country.equals("ci")) return "Cote d'Ivoire";
		if (country.equals("ck")) return "Cook Islands";
		if (country.equals("cl")) return "Chile";
		if (country.equals("cm")) return "Cameroon";
		if (country.equals("cn")) return "China";
		if (country.equals("co")) return "Colombia";
		if (country.equals("cr")) return "Costa Rica";
		if (country.equals("cu")) return "Cuba";
		if (country.equals("cv")) return "Cape Verde";
		if (country.equals("cx")) return "Christmas Island";
		if (country.equals("dj")) return "Djibouti";
		if (country.equals("dm")) return "Dominica";
		if (country.equals("do")) return "Dominican Republic";
		if (country.equals("dz")) return "Algeria";
		if (country.equals("ec")) return "Ecuador";
		if (country.equals("eg")) return "Egypt";
		if (country.equals("eh")) return "Western Sahara";
		if (country.equals("er")) return "Eritrea";
		if (country.equals("et")) return "Ethiopia";
		if (country.equals("fj")) return "Fiji";
		if (country.equals("fk")) return "Falkland Islands (Malvinas)";
		if (country.equals("fm")) return "Micronesia, Federal State of";
		if (country.equals("ga")) return "Gabon";
		if (country.equals("gd")) return "Grenada";
		if (country.equals("ge")) return "Georgia";
		if (country.equals("gf")) return "French Guiana";
		if (country.equals("gg")) return "Guernsey";
		if (country.equals("gh")) return "Ghana";
		if (country.equals("gi")) return "Gibraltar";
		if (country.equals("gm")) return "Gambia";
		if (country.equals("gn")) return "Guinea";
		if (country.equals("gp")) return "Guadeloupe";
		if (country.equals("gq")) return "Equatorial Guinea";
		if (country.equals("gs")) return "South Georgia and the South Sandwich Islands";
		if (country.equals("gt")) return "Guatemala";
		if (country.equals("gu")) return "Guam";
		if (country.equals("gw")) return "Guinea-Bissau";
		if (country.equals("gy")) return "Guyana";
		if (country.equals("hk")) return "Hong Kong";
		if (country.equals("hm")) return "Heard and McDonald Islands";
		if (country.equals("hn")) return "Honduras";
		if (country.equals("ht")) return "Haiti";
		if (country.equals("id")) return "Indonesia";
		if (country.equals("im")) return "Isle of Man";
		if (country.equals("io")) return "British Indian Ocean Territory";
		if (country.equals("iq")) return "Iraq";
		if (country.equals("ir")) return "Iran, Islamic Republic of";
		if (country.equals("jm")) return "Jamaica";
		if (country.equals("jo")) return "Jordan";
		if (country.equals("ke")) return "Kenya";
		if (country.equals("kg")) return "Kyrgyzstan";
		if (country.equals("kh")) return "Cambodia";
		if (country.equals("ki")) return "Kiribati";
		if (country.equals("km")) return "Comoros";
		if (country.equals("kn")) return "Saint Kitts and Nevis";
		if (country.equals("kp")) return "Korea, Democratic People's Republic";
		if (country.equals("kr")) return "Korea, Republic of";
		if (country.equals("kw")) return "Kuwait";
		if (country.equals("ky")) return "Cayman Islands";
		if (country.equals("kz")) return "Kazakhstan";
		if (country.equals("la")) return "Lao People's Democratic Republic";
		if (country.equals("lb")) return "Lebanon";
		if (country.equals("lc")) return "Saint Lucia";
		if (country.equals("lk")) return "Sri Lanka";
		if (country.equals("lr")) return "Liberia";
		if (country.equals("ls")) return "Lesotho";
		if (country.equals("ly")) return "Libyan Arab Jamahiriya";
		if (country.equals("ma")) return "Morocco";
		if (country.equals("md")) return "Moldova, Republic of";
		if (country.equals("mg")) return "Madagascar";
		if (country.equals("mh")) return "Marshall Islands";
		if (country.equals("ml")) return "Mali";
		if (country.equals("mm")) return "Myanmar";
		if (country.equals("mn")) return "Mongolia";
		if (country.equals("mo")) return "Macau";
		if (country.equals("mp")) return "Northern Mariana Islands";
		if (country.equals("mq")) return "Martinique";
		if (country.equals("mr")) return "Mauritania";
		if (country.equals("ms")) return "Montserrat";
		if (country.equals("mt")) return "Malta";
		if (country.equals("mu")) return "Mauritius";
		if (country.equals("mv")) return "Maldives";
		if (country.equals("mw")) return "Malawi";
		if (country.equals("my")) return "Malaysia";
		if (country.equals("mz")) return "Mozambique";
		if (country.equals("na")) return "Namibia";
		if (country.equals("nc")) return "New Caledonia";
		if (country.equals("ne")) return "Niger";
		if (country.equals("nf")) return "Norfolk Island";
		if (country.equals("ng")) return "Nigeria";
		if (country.equals("ni")) return "Nicaragua";
		if (country.equals("np")) return "Nepal";
		if (country.equals("nr")) return "Nauru";
		if (country.equals("nu")) return "Niue";
		if (country.equals("om")) return "Oman";
		if (country.equals("pa")) return "Panama";
		if (country.equals("pe")) return "Peru";
		if (country.equals("pf")) return "French Polynesia";
		if (country.equals("pg")) return "Papua New Guinea";
		if (country.equals("ph")) return "Philippines";
		if (country.equals("pk")) return "Pakistan";
		if (country.equals("pm")) return "Saint Pierre and Miquelon";
		if (country.equals("pn")) return "Pitcairn Island";
		if (country.equals("pr")) return "Puerto Rico";
		if (country.equals("ps")) return "Palestinian Territory, Occupied";
		if (country.equals("pw")) return "Palau";
		if (country.equals("py")) return "Paraguay";
		if (country.equals("qa")) return "Qatar";
		if (country.equals("re")) return "Reunion Island";
		if (country.equals("rw")) return "Rwanda";
		if (country.equals("sb")) return "Solomon Islands";
		if (country.equals("sc")) return "Seychelles";
		if (country.equals("sd")) return "Sudan";
		if (country.equals("sg")) return "Singapore";
		if (country.equals("sh")) return "Saint Helena";
		if (country.equals("sj")) return "Svalbard and Jan Mayen Islands";
		if (country.equals("sl")) return "Sierra Leone";
		if (country.equals("sm")) return "San Marino";
		if (country.equals("sn")) return "Senegal";
		if (country.equals("so")) return "Somalia";
		if (country.equals("sr")) return "Suriname";
		if (country.equals("st")) return "Sao Tome and Principe";
		if (country.equals("sv")) return "El Salvador";
		if (country.equals("sy")) return "Syrian Arab Republic";
		if (country.equals("sz")) return "Swaziland";
		if (country.equals("tc")) return "Turks and Caicos Islands";
		if (country.equals("td")) return "Chad";
		if (country.equals("tf")) return "French Southern Territories";
		if (country.equals("tg")) return "Togo";
		if (country.equals("th")) return "Thailand";
		if (country.equals("tj")) return "Tajikistan";
		if (country.equals("tk")) return "Tokelau";
		if (country.equals("tl")) return "Timor-Leste";
		if (country.equals("tm")) return "Turkmenistan";
		if (country.equals("tn")) return "Tunisia";
		if (country.equals("to")) return "Tonga";
		if (country.equals("tp")) return "East Timor";
		if (country.equals("tt")) return "Trinidad and Tobago";
		if (country.equals("tv")) return "Tuvalu";
		if (country.equals("tw")) return "Taiwan";
		if (country.equals("tz")) return "Tanzania";
		if (country.equals("ug")) return "Uganda";
		if (country.equals("um")) return "United States Minor Outlying Islands";
		if (country.equals("uy")) return "Uruguay";
		if (country.equals("uz")) return "Uzbekistan";
		if (country.equals("vc")) return "Saint Vincent and the Grenadines";
		if (country.equals("ve")) return "Venezuela";
		if (country.equals("vg")) return "Virgin Islands, British";
		if (country.equals("vi")) return "Virgin Islands, U.S.";
		if (country.equals("vn")) return "Vietnam";
		if (country.equals("vu")) return "Vanuatu";
		if (country.equals("wf")) return "Wallis and Futuna Islands";
		if (country.equals("ws")) return "Western Samoa";
		if (country.equals("ye")) return "Yemen";
		if (country.equals("yt")) return "Mayotte";
		if (country.equals("zm")) return "Zambia";
		if (country.equals("zw")) return "Zimbabwe";

		return countryname;
	}



	public String webbrowserName(String webbrowser) {
		if (webbrowser.equals("MSIE")) return "Microsoft Internet Explorer";
		if (webbrowser.equals("Firefox")) return "Mozilla Firefox";
		return webbrowser;
	}



	public String webbrowserversionName(String webbrowserversion) {
		String[] tmp = webbrowserversion.split(":::");
		String webbrowser = "";
		String version = "";
		if (tmp.length > 0) webbrowser = tmp[0];
		if (tmp.length > 1) version = tmp[1];
		return webbrowserName(webbrowser) + " " + version;
	}



	public String deviceName(String device) {
		return device;
	}



	public String deviceversionName(String deviceversion) {
		String[] tmp = deviceversion.split(":::");
		String device = "";
		String version = "";
		if (tmp.length > 0) device = tmp[0];
		if (tmp.length > 1) version = tmp[1];
		return deviceName(device) + " " + version;
	}



	public String operatingsystemName(String operatingsystem) {
		if (operatingsystem.equals("Windows")) return "Microsoft Windows";
		return operatingsystem;
	}



	public String operatingsystemversionName(String operatingsystemversion) {
		String[] tmp = operatingsystemversion.split(":::");
		String operatingsystem = "";
		String version = "";
		if (tmp.length > 0) operatingsystem = tmp[0];
		if (tmp.length > 1) version = tmp[1];
		if (operatingsystem.equals("Windows")) return "Microsoft " + version;
		return operatingsystem;
	}



	public String affiliateName(String affiliate) {
		String myaffiliate = affiliate.replaceAll(":::", "");
		if (! myaffiliate.equals("")) {
			if (myaffiliate.length() > 60) {
//				return "<a href=\"/" + text.display("adminpath") + "/orders/affiliate.jsp?affiliate=" + myaffiliate + "\">" + myaffiliate.substring(0,59) + "...</a>";
				return "" + myaffiliate.substring(0,59) + "...";
			} else {
//				return "<a href=\"/" + text.display("adminpath") + "/orders/affiliate.jsp?affiliate=" + myaffiliate + "\">" + myaffiliate + "</a>";
				return "" + myaffiliate + "";
			}
		} else {
			return "-";
		}
	}



	public String refererName(String referer) {
		String myreferer = referer.replaceAll(":::", "");
		if (! myreferer.equals("")) {
			if (myreferer.length() > 60) {
				return "<a target=\"_blank\" href=\"http://" + myreferer + "\">" + myreferer.substring(0,59) + "...</a>";
			} else {
				return "<a target=\"_blank\" href=\"http://" + myreferer + "\">" + myreferer + "</a>";
			}
		} else {
			return text.display("usage.referers.direct");
		}
	}



	public String searchengineName(String searchengine) {
		UsageLog usagelog = new UsageLog();
		for (int i=0; i<usagelog.searchengineName.length; i++) {
			if (usagelog.searchengineName[i].equals(searchengine)) {
				return "<a target=\"_blank\" href=\"http://" + usagelog.searchengineWebsite[i] + "/\">" + searchengine + "</a>";
			}
		}
		return searchengine;
	}



	public String pageName(String key, Configuration myconfig, DB db) {
		String[] tmp = key.split(":::");
		String contentclass = "";
		String database = "";
		String id = "";
		if (tmp.length > 0) contentclass = tmp[0];
		if (tmp.length > 1) database = tmp[1];
		if (tmp.length > 2) id = tmp[2];
		if (database.equals("")) {
			Content content = new Content(text);
			content.public_read(db, myconfig, id);
			if (content.getTitle().equals("")) {
//				return "<a href=\"/" + text.display("adminpath") + "/orders/contentitem.jsp?contentclass=" + contentclass + "&id=" + id + "\">" + "&nbsp;" + "</a>";
				return "" + "&nbsp;" + "";
			} else if (content.getTitle().length() > 60) {
//				return "<a href=\"/" + text.display("adminpath") + "/orders/contentitem.jsp?contentclass=" + contentclass + "&id=" + id + "\">" + content.getTitle().substring(0,59) + "...</a>";
				return "" + content.getTitle().substring(0,59) + "...";
			} else {
//				return "<a href=\"/" + text.display("adminpath") + "/orders/contentitem.jsp?contentclass=" + contentclass + "&id=" + id + "\">" + content.getTitle() + "</a>";
				return "" + content.getTitle() + "";
			}
		} else {
			Databases databases = new Databases(text);
			databases.readTitle("", "", "", "", "", "", "", db, myconfig, database);
			databases.getTitleColumn();
			String titleid = databases.getTitleColumnId();
			String titlename = databases.getTitleColumnName();
			String titlecolumn = databases.getTitleColumnColumn();
			Data data = new Data();
			data.read(db, "data" + databases.getId(), id);
			if (data.getContent(titlecolumn).equals("")) {
//				return "<a href=\"/" + text.display("adminpath") + "/orders/dataitem.jsp?contentclass=" + contentclass + "&id=" + id + "&database=" + database + "\">" + "&nbsp;" + "</a>";
				return "" + "&nbsp;" + "";
			} else if (data.getContent(titlecolumn).length() > 60) {
//				return "<a href=\"/" + text.display("adminpath") + "/orders/dataitem.jsp?contentclass=" + contentclass + "&id=" + id + "&database=" + database + "\">" + data.getContent(titlecolumn).substring(0,59) + "...</a>";
				return "" + data.getContent(titlecolumn).substring(0,59) + "...";
			} else {
//				return "<a href=\"/" + text.display("adminpath") + "/orders/dataitem.jsp?contentclass=" + contentclass + "&id=" + id + "&database=" + database + "\">" + data.getContent(titlecolumn) + "</a>";
				return "" + data.getContent(titlecolumn) + "";
			}
		}
	}



	public String pageVariantName(String key, Configuration myconfig, DB db) {
		String[] tmp = key.split(":::");
		String contentclass = "";
		String database = "";
		String id = "";
		String usersegments = "";
		String usertests = "";
		if (tmp.length > 0) contentclass = tmp[0];
		if (tmp.length > 1) database = tmp[1];
		if (tmp.length > 2) id = tmp[2];
		if (tmp.length > 3) usersegments = tmp[3];
		if (tmp.length > 4) usertests = tmp[4];
		if (database.equals("")) {
			Content content = new Content(text);
			content.public_read(db, myconfig, id);
			if (content.getTitle().equals("")) {
//				return "<a href=\"/" + text.display("adminpath") + "/orders/contentitem.jsp?contentclass=" + contentclass + "&id=" + id + "\">" + "&nbsp;" + "</a>";
				return "" + "&nbsp;" + "";
			} else if (content.getTitle().length() > 60) {
//				return "<a href=\"/" + text.display("adminpath") + "/orders/contentitem.jsp?contentclass=" + contentclass + "&id=" + id + "\">" + content.getTitle().substring(0,59) + "...</a>";
				return "" + content.getTitle().substring(0,59) + "...";
			} else {
//				return "<a href=\"/" + text.display("adminpath") + "/orders/contentitem.jsp?contentclass=" + contentclass + "&id=" + id + "\">" + content.getTitle() + "</a>";
				return "" + content.getTitle() + "";
			}
		} else {
			Databases databases = new Databases(text);
			databases.readTitle("", "", "", "", "", "", "", db, myconfig, database);
			databases.getTitleColumn();
			String titleid = databases.getTitleColumnId();
			String titlename = databases.getTitleColumnName();
			String titlecolumn = databases.getTitleColumnColumn();
			Data data = new Data();
			data.read(db, "data" + databases.getId(), id);
			if (data.getContent(titlecolumn).equals("")) {
//				return "<a href=\"/" + text.display("adminpath") + "/orders/dataitem.jsp?contentclass=" + contentclass + "&id=" + id + "&database=" + database + "\">" + "&nbsp;" + "</a>";
				return "" + "&nbsp;" + "";
			} else if (data.getContent(titlecolumn).length() > 60) {
//				return "<a href=\"/" + text.display("adminpath") + "/orders/dataitem.jsp?contentclass=" + contentclass + "&id=" + id + "&database=" + database + "\">" + data.getContent(titlecolumn).substring(0,59) + "...</a>";
				return "" + data.getContent(titlecolumn).substring(0,59) + "...";
			} else {
//				return "<a href=\"/" + text.display("adminpath") + "/orders/dataitem.jsp?contentclass=" + contentclass + "&id=" + id + "&database=" + database + "\">" + data.getContent(titlecolumn) + "</a>";
				return "" + data.getContent(titlecolumn) + "";
			}
		}
	}



	public String pageLink(String key) {
		String[] tmp = key.split(":::");
		String contentclass = "";
		String database = "";
		String id = "";
		if (tmp.length > 0) contentclass = tmp[0];
		if (tmp.length > 1) database = tmp[1];
		if (tmp.length > 2) id = tmp[2];
		if (database.equals("")) {
			return "<a target=\"_blank\" href=\"/" + contentclass + ".jsp?id=" + id + "&mode=+\">" + text.display("usage.view") + "</a>";
		} else {
			return "<a target=\"_blank\" href=\"/" + contentclass + ".jsp?database=" + database + "&id=" + id + "&mode=+\">" + text.display("usage.view") + "</a>";
		}
	}



	public String pageVariantLink(String key) {
		String[] tmp = key.split(":::");
		String contentclass = "";
		String database = "";
		String id = "";
		String usersegments = "";
		String usertests = "";
		if (tmp.length > 0) contentclass = tmp[0];
		if (tmp.length > 1) database = tmp[1];
		if (tmp.length > 2) id = tmp[2];
		if (tmp.length > 3) usersegments = tmp[3];
		if (tmp.length > 4) usertests = tmp[4];
		if (database.equals("")) {
			return "<a target=\"_blank\" href=\"/" + contentclass + ".jsp?id=" + id + "&mode=+\">" + text.display("usage.view") + "</a>";
		} else {
			return "<a target=\"_blank\" href=\"/" + contentclass + ".jsp?database=" + database + "&id=" + id + "&mode=+\">" + text.display("usage.view") + "</a>";
		}
	}



	public String contentVariant(String key) {
		String[] tmp = key.split(":::");
		String mycontentclass = "";
		String contentid = "";
		String usersegments = "";
		String usertests = "";
		if (tmp.length > 0) mycontentclass = tmp[0];
		if (tmp.length > 1) contentid = tmp[1];
		if (tmp.length > 2) usersegments = tmp[2];
		if (tmp.length > 3) usertests = tmp[3];
		if (tmp.length > 4) {
			usersegments = tmp[3];
			usertests = tmp[4];
		}
		usersegments = usersegments.replaceAll("::", "=");
		usertests = usertests.replaceAll("::", "=");
		String myvariant = usersegments;
		if (! usertests.equals("")) {
			if (! myvariant.equals("")) myvariant += ":::";
			myvariant += usertests;
		}
		if (myvariant.equals("")) {
			return "&nbsp;";
		} else {
			return myvariant;
		}
	}



	public String contentName(String key, Configuration myconfig, DB db) {
		String[] tmp = key.split(":::");
		String mycontentclass = "";
		String contentid = "";
		String database = "";
		if (tmp.length > 0) mycontentclass = tmp[0];
		if (tmp.length > 1) contentid = tmp[1];
		if (tmp.length > 2) database = tmp[2];
		if (! database.equals("")) {
			Databases databases = new Databases(text);
			databases.readTitle(db, myconfig, database);
			databases.getTitleColumn();
			String titleid = databases.getTitleColumnId();
			String titlename = databases.getTitleColumnName();
			String titlecolumn = databases.getTitleColumnColumn();
			Data data = new Data();
			data.read(db, "data" + databases.getId(), contentid);
			if (data.getContent(titlecolumn).equals("")) {
//				return "<a href=\"/" + text.display("adminpath") + "/orders/dataitem.jsp?contentclass=" + mycontentclass + "&id=" + contentid + "&database=" + database + "\">" + "&nbsp;" + "</a>";
				return "" + "&nbsp;" + "";
			} else if (data.getContent(titlecolumn).length() > 60) {
//				return "<a href=\"/" + text.display("adminpath") + "/orders/dataitem.jsp?contentclass=" + mycontentclass + "&id=" + contentid + "&database=" + database + "\">" + data.getContent(titlecolumn).substring(0,59) + "...</a>";
				return "" + data.getContent(titlecolumn).substring(0,59) + "...";
			} else {
//				return "<a href=\"/" + text.display("adminpath") + "/orders/dataitem.jsp?contentclass=" + mycontentclass + "&id=" + contentid + "&database=" + database + "\">" + data.getContent(titlecolumn) + "</a>";
				return "" + data.getContent(titlecolumn) + "";
			}
		} else {
			Content content = new Content(text);
			content.public_read(db, myconfig, contentid);
			if (content.getId().equals("")) {
				return "&nbsp;";
			} else if (content.getTitle().equals("")) {
//				return "<a href=\"/" + text.display("adminpath") + "/orders/contentitem.jsp?contentclass=" + content.getContentClass() + "&id=" + content.getId() + "\">" + "&nbsp;" + "</a>";
				return "" + "&nbsp;" + "";
			} else {
//				return "<a href=\"/" + text.display("adminpath") + "/orders/contentitem.jsp?contentclass=" + content.getContentClass() + "&id=" + content.getId() + "\">" + content.getTitle() + "</a>";
				return "" + content.getTitle() + "";
			}
		}
	}



	public String contentVariantName(String key, Configuration myconfig, DB db) {
		String[] tmp = key.split(":::");
		String mycontentclass = "";
		String contentid = "";
		String usersegments = "";
		String usertests = "";
		if (tmp.length > 0) mycontentclass = tmp[0];
		if (tmp.length > 1) contentid = tmp[1];
		if (tmp.length > 2) usersegments = tmp[2];
		if (tmp.length > 3) usertests = tmp[3];
		Content content = new Content(text);
		content.public_read(db, myconfig, contentid);
		if (content.getId().equals("")) {
			return "&nbsp;";
		} else if (content.getTitle().equals("")) {
//			return "<a href=\"/" + text.display("adminpath") + "/orders/contentitem.jsp?contentclass=" + content.getContentClass() + "&id=" + content.getId() + "\">" + "&nbsp;" + "</a>";
			return "" + "&nbsp;" + "";
		} else {
//			return "<a href=\"/" + text.display("adminpath") + "/orders/contentitem.jsp?contentclass=" + content.getContentClass() + "&id=" + content.getId() + "\">" + content.getTitle() + "</a>";
			return "" + content.getTitle() + "";
		}
	}



	public String contentLink(String key) {
		String[] tmp = key.split(":::");
		String mycontentclass = "";
		String contentid = "";
		String database = "";
		if (tmp.length > 0) mycontentclass = tmp[0];
		if (tmp.length > 1) contentid = tmp[1];
		if (tmp.length > 2) database = tmp[2];
		if ((contentid.equals("")) || (contentid.equals("0"))) {
			return "&nbsp;";
		} else if (! database.equals("")) {
			return "<a target=\"_blank\" href=\"/" + mycontentclass + ".jsp?database=" + database + "&id=" + contentid + "&mode=+\">" + text.display("usage.view") + "</a>";
		} else {
			return "<a target=\"_blank\" href=\"/" + mycontentclass + ".jsp?id=" + contentid + "&mode=+\">" + text.display("usage.view") + "</a>";
		}
	}



	public String contentVariantLink(String key) {
		String[] tmp = key.split(":::");
		String mycontentclass = "";
		String contentid = "";
		String usersegments = "";
		String usertests = "";
		if (tmp.length > 0) mycontentclass = tmp[0];
		if (tmp.length > 1) contentid = tmp[1];
		if (tmp.length > 2) usersegments = tmp[2];
		if (tmp.length > 3) usertests = tmp[3];
		if ((contentid.equals("")) || (contentid.equals("0"))) {
			return "&nbsp;";
		} else {
			return "<a target=\"_blank\" href=\"/" + mycontentclass + ".jsp?id=" + contentid + "&mode=+\">" + text.display("usage.view") + "</a>";
		}
	}



	public String contentClass(String key) {
		String[] tmp = key.split(":::");
		if (tmp.length > 0) return tmp[0];
		return "";
	}



	public String databaseName(String key) {
		String[] tmp = key.split(":::");
		if (tmp.length > 1) return tmp[1];
		return "";
	}



	public String pageId(String key) {
		String pageid = "";
		String[] tmp = key.split(":::");
		if (tmp.length > 2) pageid = tmp[2];
		if ((pageid.equals("")) || (pageid.matches("[^0-9]"))) {
			if (tmp.length > 1) pageid = tmp[1];
		}
		return pageid;
	}



	public String pageVariantId(String key) {
		String pageid = "";
		String[] tmp = key.split(":::");
		if (tmp.length > 2) pageid = tmp[1];
		if ((pageid.equals("")) || (pageid.matches("[^0-9]"))) {
			if (tmp.length > 1) pageid = tmp[1];
		}
		return pageid;
	}



	public String pageFromName(String key, Configuration myconfig, DB db) {
		return pageName(key, myconfig, db);
	}



	public String pageFromId(String key) {
		String[] tmp = key.split(":::");
		if (tmp.length > 2) return tmp[2];
		return "";
	}



	public String pageToName(String key, Configuration myconfig, DB db) {
		String[] tmp = key.split(":::");
		String mycontentclass = "";
		String database = "";
		String id = "";
		if (tmp.length > 3) mycontentclass = tmp[3];
		if (tmp.length > 4) database = tmp[4];
		if (tmp.length > 5) id = tmp[5];
		return pageName(mycontentclass + ":::" + database + ":::" + id, myconfig, db);
	}



	public String pageToId(String key) {
		String[] tmp = key.split(":::");
		if (tmp.length > 5) return tmp[5];
		return "";
	}



	public String categoryVariant(String key) {
		String[] tmp = key.split(":::");
		String myname = "";
		String usersegments = "";
		String usertests = "";
		if (tmp.length > 0) myname = tmp[0];
		if (tmp.length > 1) usersegments = tmp[1];
		if (tmp.length > 2) usertests = tmp[2];
		usersegments = usersegments.replaceAll("::", "=");
		usertests = usertests.replaceAll("::", "=");
		String myvariant = usersegments;
		if (! usertests.equals("")) {
			if (! myvariant.equals("")) myvariant += ":::";
			myvariant += usertests;
		}
		if (myvariant.equals("")) {
			return "&nbsp;";
		} else {
			return myvariant;
		}
	}



	public String categoryName(String key) {
		String myname = key;
		String[] tmp = key.split(":::");
		if (tmp.length > 0) myname = tmp[0];
		if (myname.equals(":::")) myname = "";
		if (myname.equals("")) {
			return text.display("none");
		} else {
			return myname;
		}
	}



	public String visitorName(String key) {
		String[] tmp = key.split(":::");
//		if (tmp.length > 0) return "<a href=\"/" + text.display("adminpath") + "/orders/visitor.jsp?visitor=" + tmp[0] + "\">" + tmp[0] + "</a>";
		if (tmp.length > 0) return "" + tmp[0] + "";
		return "";
	}



	public String userName(DB db, Configuration config, String id) {
		if ((! id.equals("")) && (! id.equals("0"))) {
//			return "<a href=\"/" + text.display("adminpath") + "/orders/username.jsp?id=" + id + "\">" + User.Username(db, config, id) + " (" + id + ")" + "</a>";
			return "" + User.Username(db, config, id) + " (" + id + ")" + "";
		} else {
			return text.display("usage.users.anonymous");
		}
	}



	public String visitName(String key) {
		String visit = "";
		String[] tmp = key.split(":::");
		if (tmp.length > 1) visit = tmp[1];
		if (visit.equals("")) {
			if (tmp.length > 0) visit = tmp[0];
		}
		if (visit.length() >= 14) {
//			return "<a href=\"/" + text.display("adminpath") + "/orders/visit.jsp?visit=" + visit + "\">" + visit.substring(0,4) + "-" + visit.substring(4,6) + "-" + visit.substring(6,8) + " " + visit.substring(8,10) + ":" + visit.substring(10,12) + ":" + visit.substring(12,14) + "</a>";
			return "" + visit.substring(0,4) + "-" + visit.substring(4,6) + "-" + visit.substring(6,8) + " " + visit.substring(8,10) + ":" + visit.substring(10,12) + ":" + visit.substring(12,14) + "";
		} else {
			return "";
		}
	}



	public String websiteAddress(String key) {
		String address = key;
		String[] tmp = key.split(":::");
		if (tmp.length > 0) address = tmp[0];
		address = address.replaceAll(":::", "");
		return address;
	}



	public String websiteVariant(String key) {
		String address = "";
		String usersegments = "";
		String usertests = "";
		String[] tmp = key.split(":::");
		if (tmp.length > 0) address = tmp[0];
		if (tmp.length > 1) usersegments = tmp[1];
		if (tmp.length > 2) usertests = tmp[2];
		usersegments = usersegments.replaceAll("::", "=");
		usertests = usertests.replaceAll("::", "=");
		String myvariant = usersegments;
		if (! usertests.equals("")) myvariant += ":::" + usertests;
		if (myvariant.equals("")) {
			return "&nbsp;";
		} else {
			return myvariant;
		}
	}



}
