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

public class UCreportUsage {
	private String SQLselectClienthostsMySQL = "select count(distinct clienthost) as countclienthosts";
	private String SQLselectClienthostsMySQL2 = "select sum(clienthosts) as countclienthosts";
	private String SQLselectClienthostsAccess = "select count(*) as countclienthosts";
	private String SQLselectClienthostsAccess2 = "select sum(clienthosts) as countclienthosts";
	private String SQLselectVisitorsMySQL = "select count(distinct visitorid) as countvisitors";
	private String SQLselectVisitorsMySQL2 = "select sum(visitors) as countvisitors";
	private String SQLselectVisitorsAccess = "select count(*) as countvisitors";
	private String SQLselectVisitorsAccess2 = "select sum(visitors) as countvisitors";
	private String SQLselectVisitsMySQL = "select count(distinct sessionid) as countvisits";
	private String SQLselectVisitsMySQL2 = "select sum(visits) as countvisits";
	private String SQLselectVisitsAccess = "select count(*) as countvisits";
	private String SQLselectVisitsAccess2 = "select sum(visits) as countvisits";
	private String SQLselectPageViews = "select sum(hits) as countpageviews";
	private String SQLselectPageViews2 = "select sum(pageviews) as countpageviews";
	private String SQLselectHits = "select sum(hits) as counthits";
	private String SQLselectHits2 = "select sum(hits) as counthits";

	// reinitialized in init
	private String SQLwherePage = "requestclass in ('page','product','guestbook','data')";
	private String SQLwhereContent = "requestclass not in ('image','file','link')";
	private String SQLwhereLibrary = "requestclass in ('image','file','link')";
	private String SQLwhereImage = "requestclass='image'";
	private String SQLwhereFile = "requestclass='file'";
	private String SQLwhereLink = "requestclass='link'";

	private String SQLcolumnsDaily = "datetimeyear, datetimemonth, datetimeday";
	private String SQLcolumnsWeekly = "datetimeyear, datetimeweek";
	private String SQLcolumnsMonthly = "datetimeyear, datetimemonth";
	private String SQLcolumnsYearly = "datetimeyear";
	private String SQLcolumnsHours = "datetimehour";
	private String SQLcolumnsDays = "datetimeday";
	private String SQLcolumnsWeekdays = "datetimeweekday";
	private String SQLcolumnsWeeks = "datetimeweek";
	private String SQLcolumnsMonths = "datetimemonth";
	private String SQLcolumnsWebsites = "requesthost";
	private String SQLcolumnsClienthosts = "clienthost";
	private String SQLcolumnsClienthost = "clienthost, visitorid, sessionid";
	private String SQLcolumnsVisitors = "visitorid";
	private String SQLcolumnsVisitor = "visitorid, clienthost, sessionid";
	private String SQLcolumnsUsername = "clienthost, visitorid, sessionid";
	private String SQLcolumnsVisits = "sessionid, clienthost, visitorid";
	private String SQLcolumnsCountries = "clienthosttld";
	private String SQLcolumnsRobots = "clientbrowser";
	private String SQLcolumnsWebBrowsers = "clientbrowser";
	private String SQLcolumnsWebBrowserVersions = "clientbrowser, clientversion";
	private String SQLcolumnsDevices = "clientdevice";
	private String SQLcolumnsDeviceVersions = "clientdevice, clientdeviceversion";
	private String SQLcolumnsOperatingSystems = "clientos";
	private String SQLcolumnsOperatingSystemVersions = "clientos, clientosversion";
	private String SQLcolumnsUsers = "clientusername";
	private String SQLcolumnsUsergroups = "users.usergroup as usergroup";
	private String SQLcolumnsUsertypes = "users.usertype as usertype";
	private String SQLcolumnsUsersegments = "usersegments";
	private String SQLcolumnsUsertests = "usertests";
	private String SQLcolumnsReferers = "refererhost";
	private String SQLcolumnsRefererPages = "refererhost, refererpath";
	private String SQLcolumnsSearchEngines = "referersearchengine";
	private String SQLcolumnsSearchQueries = "referersearchquery";
	private String SQLcolumnsEntry = "requestid, requestclass, requestdatabase";
	private String SQLcolumnsExit = "requestid, requestclass, requestdatabase";
	private String SQLcolumnsExitMySQL = "q1.requestid as requestid, q1.requestclass as requestclass, q1.requestdatabase as requestdatabase";
	private String SQLcolumnsPaths = "requestid, requestclass, requestdatabase, refererid, refererclass, refererdatabase";
	private String SQLcolumnsContents = "requestid, requestclass, requestdatabase";
	private String SQLcolumnsPages = "requestid, requestclass, requestdatabase";
	private String SQLcolumnsPagegroups = "contentgroup";
	private String SQLcolumnsPagetypes = "contenttype";
	private String SQLcolumnsProducts = "requestid, requestclass, requestdatabase";
	private String SQLcolumnsProductgroups = "contentgroup";
	private String SQLcolumnsProducttypes = "contenttype";
	private String SQLcolumnsDatabases = "requestclass, requestdatabase";
	private String SQLcolumnsDatabase = "requestclass, requestdatabase, requestid";
	private String SQLcolumnsContacts = "requestid, requestclass";
	private String SQLcolumnsPosts = "requestid, requestclass";
	private String SQLcolumnsLogins = "requestid, requestclass";
	private String SQLcolumnsLogouts = "requestid, requestclass";
	private String SQLcolumnsStyleSheets = "requestid, requestclass";
	private String SQLcolumnsScripts = "requestid, requestclass";
	private String SQLcolumnsLibrary = "requestid, requestclass";
	private String SQLcolumnsImages = "requestid, requestclass";
	private String SQLcolumnsImagegroups = "contentgroup";
	private String SQLcolumnsImagetypes = "contenttype";
	private String SQLcolumnsFiles = "requestid, requestclass";
	private String SQLcolumnsFilegroups = "contentgroup";
	private String SQLcolumnsFiletypes = "contenttype";
	private String SQLcolumnsLinks = "requestid, requestclass";
	private String SQLcolumnsLinkgroups = "contentgroup";
	private String SQLcolumnsLinktypes = "contenttype";
	private String SQLcolumnsVariants = ", usersegments, usertests";
	private String SQLcolumnsVariantsSegments = ", usersegments";
	private String SQLcolumnsVariantsUsertests = ", usertests";
	private Text text = new Text();



	public UCreportUsage() {
	}



	public UCreportUsage(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public void init(DB db) {
		if (db == null) db = new DB();
		SQLwherePage = "requestclass in (" + db.quote("page") + "," + db.quote("product") + "," + db.quote("guestbook") + "," + db.quote("data") + ")";
		SQLwhereContent = "requestclass not in (" + db.quote("image") + "," + db.quote("file") + "," + db.quote("link") + ")";
		SQLwhereLibrary = "requestclass in (" + db.quote("image") + "," + db.quote("file") + "," + db.quote("link") + ")";
		SQLwhereImage = "requestclass=" + db.quote("image");
		SQLwhereFile = "requestclass=" + db.quote("file");
		SQLwhereLink = "requestclass=" + db.quote("link");
	}



	public boolean getAccess(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		return initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
	}



	private boolean initRequest(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		init(db);
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return false;
		accesspermission = RequireUser.UsageAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return false;

		UsageLog usagelog = new UsageLog();
		usagelog.clean(logdb, myconfig.get(db, "log_period"));

		setSessionPeriodFromRequest(mysession, myrequest);
		setSessionLimitFromRequest(mysession, myrequest);
		return true;
	}



	public HashMap<String,String> getSummary(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		if ((myrequest.getRequest() != null) && (myresponse.getResponse() != null)) {
			boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
			if (! accesspermission) return new HashMap<String,String>();
		}
		HashMap<String,String> usage = new HashMap<String,String>();
		Date mynow = new Date();
		String SQLwhere;
		SQLwhere = SQLwherePeriod(db, mysession, mynow, "");
		SQLwhere = SQLwhereNotRobots(db, SQLwhere);

		String SQL = "";
		if (logdb.db_type(database).equals("mysql")) {
			// MySQL v3.3-v4.0 does not support sub-selects
			SQL = "select count(distinct clienthost) from usagelog where " + logdb.is_not_blank("clienthost") + " and " + SQLwhere;
		} else if (logdb.db_type(database).equals("oracle")) {
			// Oracle does not support naming of sub-selects
			SQL = "select count(*) from (select distinct clienthost from usagelog where " + logdb.is_not_blank("clienthost") + " and " + SQLwhere + ")";
		} else {
			// Microsoft SQL Server requires naming of sub-selects
			SQL = "select count(*) from (select distinct clienthost from usagelog where " + logdb.is_not_blank("clienthost") + " and " + SQLwhere + ") as clienthosts";
		}
		usage.put("countclienthosts", "" + logdb.query_value(SQL));

		if (logdb.db_type(database).equals("mysql")) {
			// MySQL v3.3-v4.0 does not support sub-selects
			SQL = "select count(distinct visitorid) from usagelog where " + logdb.is_not_blank("visitorid") + " and " + SQLwhere;
		} else if (logdb.db_type(database).equals("oracle")) {
			// Oracle does not support naming of sub-selects
			SQL = "select count(*) from (select distinct visitorid from usagelog where " + logdb.is_not_blank("visitorid") + " and " + SQLwhere + ")";
		} else {
			// Microsoft SQL Server requires naming of sub-selects
			SQL = "select count(*) from (select distinct visitorid from usagelog where " + logdb.is_not_blank("visitorid") + " and " + SQLwhere + ") as visitorids";
		}
		usage.put("countvisitors", "" + logdb.query_value(SQL));

		if (logdb.db_type(database).equals("mysql")) {
			// MySQL v3.3-v4.0 does not support sub-selects
			SQL = "select count(distinct sessionid) from usagelog where " + logdb.is_not_blank("sessionid") + " and " + SQLwhere;
		} else if (logdb.db_type(database).equals("oracle")) {
			// Oracle does not support naming of sub-selects
			SQL = "select count(*) from (select distinct sessionid from usagelog where " + logdb.is_not_blank("sessionid") + " and " + SQLwhere + ")";
		} else {
			// Microsoft SQL Server requires naming of sub-selects
			SQL = "select count(*) from (select distinct sessionid from usagelog where " + logdb.is_not_blank("sessionid") + " and " + SQLwhere + ") as sessionids";
		}
		usage.put("countvisits", "" + logdb.query_value(SQL));

		SQL = "select sum(visits) from usagelog where (summarised='summary') and " + SQLwhere;
		double summarisedcount = logdb.query_value(SQL);
		SQL = "select sum(sessionduration*visits) from usagelog where (summarised='summary') and " + SQLwhere;
		double summarisedtotal = logdb.query_value(SQL);

		if (logdb.db_type(database).equals("mysql")) {
			// MySQL v3.3-v4.0 does not support sub-selects
			// select exit page for each sessionid and count manually
			SQL = exitPagesMySQL(mysession, myrequest, myconfig, db, logdb, database, mynow, "", "");
			HashMap<String,HashMap<String,String>> exitpages = logdb.query_records(SQL);
			long visitdurationcount = Common.integernumber(summarisedcount);
			long visitdurationtotal = Common.integernumber(summarisedtotal);
			double visitduration = 0.0;
			if (summarisedcount > 0) visitduration = summarisedtotal / summarisedcount;
			Iterator myexitpages = exitpages.keySet().iterator();
			while (myexitpages.hasNext()) {
				String exitpage = "" + myexitpages.next();
				visitdurationcount += 1;
				visitdurationtotal += Common.integernumber("" + ((HashMap<String,String>)exitpages.get(exitpage)).get("maxsessionduration"));
			}
			if (visitdurationcount > 0) {
				visitduration = (double)((double)visitdurationtotal / (double)visitdurationcount);
			} else {
				visitduration = 0.0;
			}
			usage.put("visitduration-count", "" + visitdurationcount);
			usage.put("visitduration-total", "" + visitdurationtotal);
			usage.put("averagevisitduration", "" + visitduration);
		} else if (logdb.db_type(database).equals("oracle")) {
			// Oracle does not support naming of sub-selects
//			SQL = "select avg(maxsessionduration) from (select max(sessionduration) as maxsessionduration from usagelog where " + logdb.is_blank("summarised") + " and " + SQLwhere + " group by sessionid)";
//			double visitduration = logdb.query_value(SQL);
			SQL = "select count(*) from (select max(sessionduration) as maxsessionduration from usagelog where " + logdb.is_blank("summarised") + " and " + SQLwhere + " group by sessionid)";
			double visitdurationcount = logdb.query_value(SQL);
			SQL = "select sum(maxsessionduration) from (select max(sessionduration) as maxsessionduration from usagelog where " + logdb.is_blank("summarised") + " and " + SQLwhere + " group by sessionid)";
			double visitdurationtotal = logdb.query_value(SQL);
			double visitduration = 0.0;
			if ((summarisedcount + visitdurationcount) > 0) visitduration = (summarisedtotal + visitdurationtotal) / (summarisedcount + visitdurationcount);
			usage.put("averagevisitduration", "" + visitduration);
		} else {
			// Microsoft SQL Server requires naming of sub-selects
//			SQL = "select avg(maxsessionduration) from (select max(sessionduration) as maxsessionduration from usagelog where " + logdb.is_blank("summarised") + " and " + SQLwhere + " group by sessionid) as maxsessiondurations";
//			double visitduration = logdb.query_value(SQL);
			SQL = "select count(*) from (select max(sessionduration) as maxsessionduration from usagelog where " + logdb.is_blank("summarised") + " and " + SQLwhere + " group by sessionid) as maxsessiondurations";
			double visitdurationcount = logdb.query_value(SQL);
			SQL = "select sum(maxsessionduration) from (select max(sessionduration) as maxsessionduration from usagelog where " + logdb.is_blank("summarised") + " and " + SQLwhere + " group by sessionid) as maxsessiondurations";
			double visitdurationtotal = logdb.query_value(SQL);
			double visitduration = 0.0;
			if ((summarisedcount + visitdurationcount) > 0) visitduration = (summarisedtotal + visitdurationtotal) / (summarisedcount + visitdurationcount);
			usage.put("averagevisitduration", "" + visitduration);
		}

		SQL = "select sum(hits) from usagelog where " + SQLwherePage + " and " + logdb.is_blank("summarised") + " and " + SQLwhere;
		double pageviewsdetails = logdb.query_value(SQL);
		SQL = "select sum(pageviews) from usagelog where (summarised='summary') and " + SQLwhere;
		double pageviewssummarised = logdb.query_value(SQL);
		usage.put("countpageviews", "" + Common.integernumber(pageviewsdetails + pageviewssummarised));

		SQL = "select sum(hits) from usagelog where " + logdb.is_blank("summarised") + " and " + SQLwhere;
		double hitsdetails = logdb.query_value(SQL);
		SQL = "select sum(hits) from usagelog where (summarised='summary') and " + SQLwhere;
		double hitssummarised = logdb.query_value(SQL);
		usage.put("counthits", "" + Common.integernumber(hitsdetails + hitssummarised));

		SQL = "select sum(pageviews) from usagelog where (summarised='summary') and " + SQLwhere;
		summarisedcount = logdb.query_value(SQL);
		SQL = "select sum(refererduration*pageviews) from usagelog where (summarised='summary') and " + SQLwhere;
		summarisedtotal = logdb.query_value(SQL);
		SQL = "select count(*) from usagelog where " + logdb.is_not_blank("refererclass") + " and " + logdb.is_blank("summarised") + " and " + SQLwhere;
		double pageviewdurationcount = logdb.query_value(SQL);
		SQL = "select sum(refererduration) from usagelog where " + logdb.is_not_blank("refererclass") + " and " + logdb.is_blank("summarised") + " and " + SQLwhere;
		double pageviewdurationtotal = logdb.query_value(SQL);
		double pageviewduration = 0.0;
		if ((summarisedcount + pageviewdurationcount) > 0) pageviewduration = (summarisedtotal + pageviewdurationtotal) / (summarisedcount + pageviewdurationcount);
		usage.put("averagepageviewduration", "" + pageviewduration);

		return usage;
	}



	public HashMap<String,HashMap<String,String>> getDaily(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,HashMap<String,String>>();
		Date mynow = new Date();
//		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, periodDays(mysession, mynow), "usagelog", SQLcolumnsDaily + SQLcolumnsVariantsSegments, "1=1", "", "daily", "yyyy-MM-dd", "%04d-%02d-%02d", "datetimeyear,datetimemonth,datetimeday", true);
//		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, periodDays(mysession, mynow), "usagelog", SQLcolumnsDaily + SQLcolumnsVariantsUsertests, "1=1", "", "daily", "yyyy-MM-dd", "%04d-%02d-%02d", "datetimeyear,datetimemonth,datetimeday", true);
//		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, periodDays(mysession, mynow), "usagelog", SQLcolumnsDaily + SQLcolumnsVariants, "1=1", "", "daily", "yyyy-MM-dd", "%04d-%02d-%02d", "datetimeyear,datetimemonth,datetimeday", true);
//		} else {
			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, periodDays(mysession, mynow), "usagelog", SQLcolumnsDaily, "1=1", "", "daily", "yyyy-MM-dd", "%04d-%02d-%02d", "datetimeyear,datetimemonth,datetimeday", true);
//		}
	}



	public HashMap<String,HashMap<String,String>> getWeekly(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,HashMap<String,String>>();
		Date mynow = new Date();
//		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, periodWeeks(mysession, mynow), "usagelog", SQLcolumnsWeekly + SQLcolumnsVariantsSegments, "1=1", "", "weekly", "yyyy-ww", "%04d-%02d", "datetimeyear,datetimeweek", true);
//		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, periodWeeks(mysession, mynow), "usagelog", SQLcolumnsWeekly + SQLcolumnsVariantsUsertests, "1=1", "", "weekly", "yyyy-ww", "%04d-%02d", "datetimeyear,datetimeweek", true);
//		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, periodWeeks(mysession, mynow), "usagelog", SQLcolumnsWeekly + SQLcolumnsVariants, "1=1", "", "weekly", "yyyy-ww", "%04d-%02d", "datetimeyear,datetimeweek", true);
//		} else {
			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, periodWeeks(mysession, mynow), "usagelog", SQLcolumnsWeekly, "1=1", "", "weekly", "yyyy-ww", "%04d-%02d", "datetimeyear,datetimeweek", true);
//		}
	}



	public HashMap<String,HashMap<String,String>> getMonthly(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,HashMap<String,String>>();
		Date mynow = new Date();
//		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, periodMonths(mysession, mynow), "usagelog", SQLcolumnsMonthly + SQLcolumnsVariantsSegments, "1=1", "", "monthly", "yyyy-MM", "%04d-%02d", "datetimeyear,datetimemonth", true);
//		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, periodMonths(mysession, mynow), "usagelog", SQLcolumnsMonthly + SQLcolumnsVariantsUsertests, "1=1", "", "monthly", "yyyy-MM", "%04d-%02d", "datetimeyear,datetimemonth", true);
//		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, periodMonths(mysession, mynow), "usagelog", SQLcolumnsMonthly + SQLcolumnsVariants, "1=1", "", "monthly", "yyyy-MM", "%04d-%02d", "datetimeyear,datetimemonth", true);
//		} else {
			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, periodMonths(mysession, mynow), "usagelog", SQLcolumnsMonthly, "1=1", "", "monthly", "yyyy-MM", "%04d-%02d", "datetimeyear,datetimemonth", true);
//		}
	}



	public HashMap<String,HashMap<String,String>> getYearly(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,HashMap<String,String>>();
		Date mynow = new Date();
//		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, periodYears(mysession, mynow), "usagelog", SQLcolumnsYearly + SQLcolumnsVariantsSegments, "1=1", "", "yearly", "yyyy", "%04d", "datetimeyear", true);
//		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, periodYears(mysession, mynow), "usagelog", SQLcolumnsYearly + SQLcolumnsVariantsUsertests, "1=1", "", "yearly", "yyyy", "%04d", "datetimeyear", true);
//		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, periodYears(mysession, mynow), "usagelog", SQLcolumnsYearly + SQLcolumnsVariants, "1=1", "", "yearly", "yyyy", "%04d", "datetimeyear", true);
//		} else {
			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, periodYears(mysession, mynow), "usagelog", SQLcolumnsYearly, "1=1", "", "yearly", "yyyy", "%04d", "datetimeyear", true);
//		}
	}



	public HashMap<String,HashMap<String,String>> getHours(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,HashMap<String,String>>();
		Date mynow = new Date();
//		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allHours(mysession, mynow), "usagelog", SQLcolumnsHours + SQLcolumnsVariantsSegments, "1=1", "", "hours", "%02d", "%02d", "datetimehour", true);
//		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allHours(mysession, mynow), "usagelog", SQLcolumnsHours + SQLcolumnsVariantsUsertests, "1=1", "", "hours", "%02d", "%02d", "datetimehour", true);
//		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allHours(mysession, mynow), "usagelog", SQLcolumnsHours + SQLcolumnsVariants, "1=1", "", "hours", "%02d", "%02d", "datetimehour", true);
//		} else {
			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allHours(mysession, mynow), "usagelog", SQLcolumnsHours, "1=1", "", "hours", "%02d", "%02d", "datetimehour", true);
//		}
	}



	public HashMap<String,HashMap<String,String>> getDays(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,HashMap<String,String>>();
		Date mynow = new Date();
//		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allDays(mysession, mynow), "usagelog", SQLcolumnsDays + SQLcolumnsVariantsSegments, "1=1", "", "days", "%02d", "%02d", "datetimeday", true);
//		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allDays(mysession, mynow), "usagelog", SQLcolumnsDays + SQLcolumnsVariantsUsertests, "1=1", "", "days", "%02d", "%02d", "datetimeday", true);
//		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allDays(mysession, mynow), "usagelog", SQLcolumnsDays + SQLcolumnsVariants, "1=1", "", "days", "%02d", "%02d", "datetimeday", true);
//		} else {
			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allDays(mysession, mynow), "usagelog", SQLcolumnsDays, "1=1", "", "days", "%02d", "%02d", "datetimeday", true);
//		}
	}



	public HashMap<String,HashMap<String,String>> getWeekdays(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,HashMap<String,String>>();
		Date mynow = new Date();
//		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allWeekdays(mysession, mynow), "usagelog", SQLcolumnsWeekdays + SQLcolumnsVariantsSegments, "1=1", "", "weekdays", "%01d", "%01d", "datetimeweekday", true);
//		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allWeekdays(mysession, mynow), "usagelog", SQLcolumnsWeekdays + SQLcolumnsVariantsUsertests, "1=1", "", "weekdays", "%01d", "%01d", "datetimeweekday", true);
//		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allWeekdays(mysession, mynow), "usagelog", SQLcolumnsWeekdays + SQLcolumnsVariants, "1=1", "", "weekdays", "%01d", "%01d", "datetimeweekday", true);
//		} else {
			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allWeekdays(mysession, mynow), "usagelog", SQLcolumnsWeekdays, "1=1", "", "weekdays", "%01d", "%01d", "datetimeweekday", true);
//		}
	}



	public HashMap<String,HashMap<String,String>> getWeeks(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,HashMap<String,String>>();
		Date mynow = new Date();
//		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allWeeks(mysession, mynow), "usagelog", SQLcolumnsWeeks + SQLcolumnsVariantsSegments, "1=1", "", "weeks", "%02d", "%02d", "datetimeweek", true);
//		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allWeeks(mysession, mynow), "usagelog", SQLcolumnsWeeks + SQLcolumnsVariantsUsertests, "1=1", "", "weeks", "%02d", "%02d", "datetimeweek", true);
//		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allWeeks(mysession, mynow), "usagelog", SQLcolumnsWeeks + SQLcolumnsVariants, "1=1", "", "weeks", "%02d", "%02d", "datetimeweek", true);
//		} else {
			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allWeeks(mysession, mynow), "usagelog", SQLcolumnsWeeks, "1=1", "", "weeks", "%02d", "%02d", "datetimeweek", true);
//		}
	}



	public HashMap<String,HashMap<String,String>> getMonths(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,HashMap<String,String>>();
		Date mynow = new Date();
//		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allMonths(mysession, mynow), "usagelog", SQLcolumnsMonths + SQLcolumnsVariantsSegments, "1=1", "", "months", "%02d", "%02d", "datetimemonth", true);
//		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allMonths(mysession, mynow), "usagelog", SQLcolumnsMonths + SQLcolumnsVariantsUsertests, "1=1", "", "months", "%02d", "%02d", "datetimemonth", true);
//		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
//			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allMonths(mysession, mynow), "usagelog", SQLcolumnsMonths + SQLcolumnsVariants, "1=1", "", "months", "%02d", "%02d", "datetimemonth", true);
//		} else {
			return getPeriodData(mysession, myrequest, myconfig, db, logdb, database, mynow, allMonths(mysession, mynow), "usagelog", SQLcolumnsMonths, "1=1", "", "months", "%02d", "%02d", "datetimemonth", true);
//		}
	}



	public HashMap<String,HashMap<String,String>> getPeriodData(Session mysession, Request myrequest, Configuration myconfig, DB db, DB logdb, String database, Date mynow, Long[] periods, String contentSQLtables, String contentSQLcolumns, String contentSQLwhere, String contentSQLorder, String prefix, String periodtimestamp, String datatimestamp, String keys, boolean include_zeros) {
		HashMap<String,HashMap<String,String>> usage = new HashMap<String,HashMap<String,String>>();

		String SQLwhere = SQLwherePeriod(db, mysession, mynow, "");
		SQLwhere = SQLwhereNotRobots(db, SQLwhere);

		String SQL = "";
		String[] keyparts;
		String timestamp = "";

		SQL = SQLclienthosts(logdb, database, contentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
		HashMap<String,HashMap<String,String>> countclienthosts = logdb.query_records(SQL);
		usage.put(prefix + "countclienthosts", new HashMap<String,String>());
		if (! periodtimestamp.equals("")) {
			for (int period=0; period<periods.length; period++) {
				if (periodtimestamp.indexOf("%0") >= 0) {
					timestamp = Common.DateFormat(periodtimestamp, periods[period].longValue());
				} else {
					timestamp = new SimpleDateFormat(periodtimestamp).format(new java.util.Date(periods[period].longValue()*1000));
				}
				((HashMap<String,String>)usage.get(prefix + "countclienthosts")).put(timestamp, "0");
			}
		}
		Iterator mycountclienthosts = countclienthosts.keySet().iterator();
		while (mycountclienthosts.hasNext()) {
			String countclienthost = "" + mycountclienthosts.next();
			keyparts = keys.split(",");
			if (keyparts.length == 8) {
				String[] params = { "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[0]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[1]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[2]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[3]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[4]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[5]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[6]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[7]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 7) {
				String[] params = { "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[0]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[1]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[2]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[3]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[4]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[5]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[6]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 6) {
				String[] params = { "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[0]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[1]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[2]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[3]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[4]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[5]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 5) {
				String[] params = { "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[0]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[1]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[2]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[3]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[4]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 4) {
				String[] params = { "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[0]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[1]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[2]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[3]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 3) {
				String[] params = { "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[0]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[1]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[2]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 2) {
				String[] params = { "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[0]), "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[1]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 1) {
				String[] params = { "" + ((HashMap<String,String>)countclienthosts.get(countclienthost)).get(keyparts[0]) };
				timestamp = Common.sprintf(datatimestamp, params);
			}
			if (! timestamp.equals("0000-00-00")) {
				if (include_zeros || (Common.integernumber(((HashMap<String,String>)countclienthosts.get(countclienthost)).get("countclienthosts")) > 0)) {
					((HashMap<String,String>)usage.get(prefix + "countclienthosts")).put(timestamp, "" + (Common.integernumber(((HashMap<String,String>)usage.get(prefix + "countclienthosts")).get(timestamp)) + Common.integernumber(((HashMap<String,String>)countclienthosts.get(countclienthost)).get("countclienthosts"))));
				}
			}
		}

		SQL = SQLvisitors(logdb, database, contentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
		HashMap<String,HashMap<String,String>> countvisitors = logdb.query_records(SQL);
		usage.put(prefix + "countvisitors", new HashMap<String,String>());
		if (! periodtimestamp.equals("")) {
			for (int period=0; period<periods.length; period++) {
				if (periodtimestamp.indexOf("%0") >= 0) {
					timestamp = Common.DateFormat(periodtimestamp, periods[period].longValue());
				} else {
					timestamp = new SimpleDateFormat(periodtimestamp).format(new java.util.Date(periods[period].longValue()*1000));
				}
				((HashMap<String,String>)usage.get(prefix + "countvisitors")).put(timestamp, "0");
			}
		}
		Iterator mycountvisitors = countvisitors.keySet().iterator();
		while (mycountvisitors.hasNext()) {
			String countvisitor = "" + mycountvisitors.next();
			keyparts = keys.split(",");
			if (keyparts.length == 8) {
				String[] params = { "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[0]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[1]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[2]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[3]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[4]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[5]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[6]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[7]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 7) {
				String[] params = { "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[0]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[1]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[2]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[3]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[4]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[5]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[6]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 6) {
				String[] params = { "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[0]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[1]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[2]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[3]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[4]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[5]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 5) {
				String[] params = { "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[0]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[1]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[2]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[3]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[4]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 4) {
				String[] params = { "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[0]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[1]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[2]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[3]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 3) {
				String[] params = { "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[0]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[1]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[2]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 2) {
				String[] params = { "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[0]), "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[1]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 1) {
				String[] params = { "" + ((HashMap<String,String>)countvisitors.get(countvisitor)).get(keyparts[0]) };
				timestamp = Common.sprintf(datatimestamp, params);
			}
			if (! timestamp.equals("0000-00-00")) {
				if (include_zeros || (Common.integernumber(((HashMap<String,String>)countvisitors.get(countvisitor)).get("countvisitors")) > 0)) {
					((HashMap<String,String>)usage.get(prefix + "countvisitors")).put(timestamp, "" + (Common.integernumber(((HashMap<String,String>)usage.get(prefix + "countvisitors")).get(timestamp)) + Common.integernumber(((HashMap<String,String>)countvisitors.get(countvisitor)).get("countvisitors"))));
				}
			}
		}

		SQL = SQLvisits(logdb, database, contentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
		HashMap<String,HashMap<String,String>> countvisits = logdb.query_records(SQL);
		usage.put(prefix + "countvisits", new HashMap<String,String>());
		if (! periodtimestamp.equals("")) {
			for (int period=0; period<periods.length; period++) {
				if (periodtimestamp.indexOf("%0") >= 0) {
					timestamp = Common.DateFormat(periodtimestamp, periods[period].longValue());
				} else {
					timestamp = new SimpleDateFormat(periodtimestamp).format(new java.util.Date(periods[period].longValue()*1000));
				}
				((HashMap<String,String>)usage.get(prefix + "countvisits")).put(timestamp, "0");
			}
		}
		Iterator mycountvisits = countvisits.keySet().iterator();
		while (mycountvisits.hasNext()) {
			String countvisit = "" + mycountvisits.next();
			keyparts = keys.split(",");
			if (keyparts.length == 8) {
				String[] params = { "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[0]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[1]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[2]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[3]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[4]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[5]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[6]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[7]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 7) {
				String[] params = { "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[0]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[1]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[2]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[3]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[4]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[5]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[6]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 6) {
				String[] params = { "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[0]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[1]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[2]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[3]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[4]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[5]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 5) {
				String[] params = { "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[0]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[1]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[2]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[3]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[4]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 4) {
				String[] params = { "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[0]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[1]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[2]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[3]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 3) {
				String[] params = { "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[0]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[1]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[2]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 2) {
				String[] params = { "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[0]), "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[1]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 1) {
				String[] params = { "" + ((HashMap<String,String>)countvisits.get(countvisit)).get(keyparts[0]) };
				timestamp = Common.sprintf(datatimestamp, params);
			}
			if (! timestamp.equals("0000-00-00")) {
				if (include_zeros || (Common.integernumber(((HashMap<String,String>)countvisits.get(countvisit)).get("countvisits")) > 0)) {
					((HashMap<String,String>)usage.get(prefix + "countvisits")).put(timestamp, "" + (Common.integernumber(((HashMap<String,String>)usage.get(prefix + "countvisits")).get(timestamp)) + Common.integernumber(((HashMap<String,String>)countvisits.get(countvisit)).get("countvisits"))));
				}
			}
		}

		SQL = SQLpageviews(logdb, contentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
		HashMap<String,HashMap<String,String>> countpageviews = logdb.query_records(SQL);
		usage.put(prefix + "countpageviews", new HashMap<String,String>());
		if (! periodtimestamp.equals("")) {
			for (int period=0; period<periods.length; period++) {
				if (periodtimestamp.indexOf("%0") >= 0) {
					timestamp = Common.DateFormat(periodtimestamp, periods[period].longValue());
				} else {
					timestamp = new SimpleDateFormat(periodtimestamp).format(new java.util.Date(periods[period].longValue()*1000));
				}
				((HashMap<String,String>)usage.get(prefix + "countpageviews")).put(timestamp, "0");
			}
		}
		Iterator mycountpageviews = countpageviews.keySet().iterator();
		while (mycountpageviews.hasNext()) {
			String countpageview = "" + mycountpageviews.next();
			keyparts = keys.split(",");
			if (keyparts.length == 8) {
				String[] params = { "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[0]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[1]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[2]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[3]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[4]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[5]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[6]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[7]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 7) {
				String[] params = { "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[0]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[1]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[2]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[3]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[4]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[5]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[6]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 6) {
				String[] params = { "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[0]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[1]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[2]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[3]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[4]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[5]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 5) {
				String[] params = { "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[0]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[1]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[2]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[3]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[4]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 4) {
				String[] params = { "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[0]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[1]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[2]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[3]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 3) {
				String[] params = { "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[0]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[1]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[2]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 2) {
				String[] params = { "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[0]), "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[1]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 1) {
				String[] params = { "" + ((HashMap<String,String>)countpageviews.get(countpageview)).get(keyparts[0]) };
				timestamp = Common.sprintf(datatimestamp, params);
			}
			if (! timestamp.equals("0000-00-00")) {
				if (include_zeros || (Common.integernumber(((HashMap<String,String>)countpageviews.get(countpageview)).get("countpageviews")) > 0)) {
					((HashMap<String,String>)usage.get(prefix + "countpageviews")).put(timestamp, "" + (Common.integernumber(((HashMap<String,String>)usage.get(prefix + "countpageviews")).get(timestamp)) + Common.integernumber(((HashMap<String,String>)countpageviews.get(countpageview)).get("countpageviews"))));
				}
			}
		}

		SQL = SQLhits(logdb, contentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
		HashMap<String,HashMap<String,String>> counthits = logdb.query_records(SQL);
		usage.put(prefix + "counthits", new HashMap<String,String>());
		if (! periodtimestamp.equals("")) {
			for (int period=0; period<periods.length; period++) {
				if (periodtimestamp.indexOf("%0") >= 0) {
					timestamp = Common.DateFormat(periodtimestamp, periods[period].longValue());
				} else {
					timestamp = new SimpleDateFormat(periodtimestamp).format(new java.util.Date(periods[period].longValue()*1000));
				}
				((HashMap<String,String>)usage.get(prefix + "counthits")).put(timestamp, "0");
			}
		}
		Iterator mycounthits = counthits.keySet().iterator();
		while (mycounthits.hasNext()) {
			String counthit = "" + mycounthits.next();
			keyparts = keys.split(",");
			if (keyparts.length == 12) {
				String[] params = { "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[0]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[1]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[2]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[3]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[4]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[5]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[6]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[7]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[8]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[9]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[10]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[11]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 11) {
				String[] params = { "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[0]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[1]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[2]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[3]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[4]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[5]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[6]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[7]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[8]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[9]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[10]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 10) {
				String[] params = { "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[0]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[1]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[2]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[3]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[4]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[5]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[6]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[7]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[8]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[9]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 9) {
				String[] params = { "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[0]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[1]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[2]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[3]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[4]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[5]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[6]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[7]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[8]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 8) {
				String[] params = { "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[0]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[1]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[2]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[3]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[4]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[5]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[6]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[7]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 7) {
				String[] params = { "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[0]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[1]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[2]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[3]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[4]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[5]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[6]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 6) {
				String[] params = { "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[0]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[1]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[2]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[3]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[4]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[5]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 5) {
				String[] params = { "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[0]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[1]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[2]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[3]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[4]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 4) {
				String[] params = { "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[0]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[1]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[2]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[3]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 3) {
				String[] params = { "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[0]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[1]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[2]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 2) {
				String[] params = { "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[0]), "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[1]) };
				timestamp = Common.sprintf(datatimestamp, params);
			} else if (keyparts.length == 1) {
				String[] params = { "" + ((HashMap<String,String>)counthits.get(counthit)).get(keyparts[0]) };
				timestamp = Common.sprintf(datatimestamp, params);
			}
			if (! timestamp.equals("0000-00-00")) {
				if (include_zeros || (Common.integernumber(((HashMap<String,String>)counthits.get(counthit)).get("counthits")) > 0)) {
					((HashMap<String,String>)usage.get(prefix + "counthits")).put(timestamp, "" + (Common.integernumber(((HashMap<String,String>)usage.get(prefix + "counthits")).get(timestamp)) + Common.integernumber(((HashMap<String,String>)counthits.get(counthit)).get("counthits"))));
				}
			}
		}

		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getRankedData(Session mysession, Request myrequest, Configuration myconfig, DB db, DB logdb, String database, String contentSQLtables, String contentSQLcolumns, String contentSQLwhere, String contentSQLorder, String prefix, String data, String keys, boolean exclude_robots, boolean lowercase_key) {
		HashMap<String,LinkedHashMap<String,String>> usage = new HashMap<String,LinkedHashMap<String,String>>();
		String SQL = "";
		Date mynow = new Date();
		String SQLwhere = SQLwherePeriod(db, mysession, mynow, "");
		if (exclude_robots) {
			SQLwhere = SQLwhereNotRobots(db, SQLwhere);
		}
		String[] keyparts;
		String timestamp = "";
		boolean countusersegments = (data.indexOf("countusersegments") > -1);
		boolean countusertests = (data.indexOf("countusertests") > -1);

		if (data.indexOf("countclienthosts") > -1) {
			SQL = SQLclienthosts(logdb, database, contentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
			// full SQL query may count website visitors multiple times when their user segments or user tests change during their website visit
			usage = getRankedDataParts(usage, prefix, "countclienthosts", keys, lowercase_key, countusersegments, countusertests, logdb, SQL);
			// strip user segments for more accurate data for user tests - changed user tests still counted multiple times, though, and data for each user test may also be counted multiple times
			if (contentSQLcolumns.indexOf(SQLcolumnsVariantsSegments) >= 0) {
				String mycontentSQLcolumns = contentSQLcolumns.replaceAll(SQLcolumnsVariantsSegments, "");
				SQL = SQLclienthosts(logdb, database, mycontentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
				HashMap<String,LinkedHashMap<String,String>> myusage = new HashMap<String,LinkedHashMap<String,String>>();
				myusage = getRankedDataParts(myusage, prefix, "countclienthosts", keys, lowercase_key, countusersegments, countusertests, logdb, SQL);
				usage.put(prefix + "countclienthosts", myusage.get(prefix + "countclienthosts"));
				usage.put(prefix + "countclienthoststests", myusage.get(prefix + "countclienthoststests"));
				usage.put(prefix + "countclienthoststest", myusage.get(prefix + "countclienthoststest"));
			}
			// strip user tests for more accurate data for user segments - changed user segments still counted multiple times, though, and data for each user segment may also be counted multiple times
			if (contentSQLcolumns.indexOf(SQLcolumnsVariantsUsertests) >= 0) {
				String mycontentSQLcolumns = contentSQLcolumns.replaceAll(SQLcolumnsVariantsUsertests, "");
				SQL = SQLclienthosts(logdb, database, mycontentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
				HashMap<String,LinkedHashMap<String,String>> myusage = new HashMap<String,LinkedHashMap<String,String>>();
				myusage = getRankedDataParts(myusage, prefix, "countclienthosts", keys, lowercase_key, countusersegments, countusertests, logdb, SQL);
				usage.put(prefix + "countclienthosts", myusage.get(prefix + "countclienthosts"));
				usage.put(prefix + "countclienthostssegments", myusage.get(prefix + "countclienthostssegments"));
				usage.put(prefix + "countclienthostssegment", myusage.get(prefix + "countclienthostssegment"));
			}
			// strip both user segments and tests for exact basic data
			if (contentSQLcolumns.indexOf(SQLcolumnsVariants) >= 0) {
				String mycontentSQLcolumns = contentSQLcolumns.replaceAll(SQLcolumnsVariants, "");
				SQL = SQLclienthosts(logdb, database, mycontentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
				HashMap<String,LinkedHashMap<String,String>> myusage = new HashMap<String,LinkedHashMap<String,String>>();
				myusage = getRankedDataParts(myusage, prefix, "countclienthosts", keys, lowercase_key, countusersegments, countusertests, logdb, SQL);
				usage.put(prefix + "countclienthosts", myusage.get(prefix + "countclienthosts"));
			}
		}
		if (data.indexOf("countvisitors") > -1) {
			SQL = SQLvisitors(logdb, database, contentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
			// full SQL query may count website visitors multiple times when their user segments or user tests change during their website visit
			usage = getRankedDataParts(usage, prefix, "countvisitors", keys, lowercase_key, countusersegments, countusertests, logdb, SQL);
			// strip user segments for more accurate data for user tests - changed user tests still counted multiple times, though, and data for each user test may also be counted multiple times
			if (contentSQLcolumns.indexOf(SQLcolumnsVariantsSegments) >= 0) {
				String mycontentSQLcolumns = contentSQLcolumns.replaceAll(SQLcolumnsVariantsSegments, "");
				SQL = SQLvisitors(logdb, database, mycontentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
				HashMap<String,LinkedHashMap<String,String>> myusage = new HashMap<String,LinkedHashMap<String,String>>();
				myusage = getRankedDataParts(myusage, prefix, "countvisitors", keys, lowercase_key, countusersegments, countusertests, logdb, SQL);
				usage.put(prefix + "countvisitors", myusage.get(prefix + "countvisitors"));
				usage.put(prefix + "countvisitorstests", myusage.get(prefix + "countvisitorstests"));
				usage.put(prefix + "countvisitorstest", myusage.get(prefix + "countvisitorstest"));
			}
			// strip user tests for more accurate data for user segments - changed user segments still counted multiple times, though, and data for each user segment may also be counted multiple times
			if (contentSQLcolumns.indexOf(SQLcolumnsVariantsUsertests) >= 0) {
				String mycontentSQLcolumns = contentSQLcolumns.replaceAll(SQLcolumnsVariantsUsertests, "");
				SQL = SQLvisitors(logdb, database, mycontentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
				HashMap<String,LinkedHashMap<String,String>> myusage = new HashMap<String,LinkedHashMap<String,String>>();
				myusage = getRankedDataParts(myusage, prefix, "countvisitors", keys, lowercase_key, countusersegments, countusertests, logdb, SQL);
				usage.put(prefix + "countvisitors", myusage.get(prefix + "countvisitors"));
				usage.put(prefix + "countvisitorssegments", myusage.get(prefix + "countvisitorssegments"));
				usage.put(prefix + "countvisitorssegment", myusage.get(prefix + "countvisitorssegment"));
			}
			// strip both user segments and tests for exact basic data
			if (contentSQLcolumns.indexOf(SQLcolumnsVariants) >= 0) {
				String mycontentSQLcolumns = contentSQLcolumns.replaceAll(SQLcolumnsVariants, "");
				SQL = SQLvisitors(logdb, database, mycontentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
				HashMap<String,LinkedHashMap<String,String>> myusage = new HashMap<String,LinkedHashMap<String,String>>();
				myusage = getRankedDataParts(myusage, prefix, "countvisitors", keys, lowercase_key, countusersegments, countusertests, logdb, SQL);
				usage.put(prefix + "countvisitors", myusage.get(prefix + "countvisitors"));
			}
		}
		if (data.indexOf("countvisits") > -1) {
			SQL = SQLvisits(logdb, database, contentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
			// full SQL query may count website visitors multiple times when their user segments or user tests change during their website visit
			usage = getRankedDataParts(usage, prefix, "countvisits", keys, lowercase_key, countusersegments, countusertests, logdb, SQL);
			// strip user segments for more accurate data for user tests - changed user tests still counted multiple times, though, and data for each user test may also be counted multiple times
			if (contentSQLcolumns.indexOf(SQLcolumnsVariantsSegments) >= 0) {
				String mycontentSQLcolumns = contentSQLcolumns.replaceAll(SQLcolumnsVariantsSegments, "");
				SQL = SQLvisits(logdb, database, mycontentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
				HashMap<String,LinkedHashMap<String,String>> myusage = new HashMap<String,LinkedHashMap<String,String>>();
				myusage = getRankedDataParts(myusage, prefix, "countvisits", keys, lowercase_key, countusersegments, countusertests, logdb, SQL);
				usage.put(prefix + "countvisits", myusage.get(prefix + "countvisits"));
				usage.put(prefix + "countvisitstests", myusage.get(prefix + "countvisitstests"));
				usage.put(prefix + "countvisitstest", myusage.get(prefix + "countvisitstest"));
			}
			// strip user tests for more accurate data for user segments - changed user segments still counted multiple times, though, and data for each user segment may also be counted multiple times
			if (contentSQLcolumns.indexOf(SQLcolumnsVariantsUsertests) >= 0) {
				String mycontentSQLcolumns = contentSQLcolumns.replaceAll(SQLcolumnsVariantsUsertests, "");
				SQL = SQLvisits(logdb, database, mycontentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
				HashMap<String,LinkedHashMap<String,String>> myusage = new HashMap<String,LinkedHashMap<String,String>>();
				myusage = getRankedDataParts(myusage, prefix, "countvisits", keys, lowercase_key, countusersegments, countusertests, logdb, SQL);
				usage.put(prefix + "countvisits", myusage.get(prefix + "countvisits"));
				usage.put(prefix + "countvisitssegments", myusage.get(prefix + "countvisitssegments"));
				usage.put(prefix + "countvisitssegment", myusage.get(prefix + "countvisitssegment"));
			}
			// strip both user segments and tests for exact basic data
			if (contentSQLcolumns.indexOf(SQLcolumnsVariants) >= 0) {
				String mycontentSQLcolumns = contentSQLcolumns.replaceAll(SQLcolumnsVariants, "");
				SQL = SQLvisits(logdb, database, mycontentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
				HashMap<String,LinkedHashMap<String,String>> myusage = new HashMap<String,LinkedHashMap<String,String>>();
				myusage = getRankedDataParts(myusage, prefix, "countvisits", keys, lowercase_key, countusersegments, countusertests, logdb, SQL);
				usage.put(prefix + "countvisits", myusage.get(prefix + "countvisits"));
			}
		}
		if (data.indexOf("countpageviews") > -1) {
			SQL = SQLpageviews(logdb, contentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
			usage = getRankedDataParts(usage, prefix, "countpageviews", keys, lowercase_key, countusersegments, countusertests, logdb, SQL);
		}
		if (data.indexOf("counthits") > -1) {
			SQL = SQLhits(logdb, contentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
			usage = getRankedDataParts(usage, prefix, "counthits", keys, lowercase_key, countusersegments, countusertests, logdb, SQL);
		}
		if (data.indexOf("durations") > -1) {
			SQL = SQLvisitsduration(contentSQLcolumns, contentSQLtables, SQLwhere + " and " + contentSQLwhere, contentSQLorder);
			usage = getRankedDataParts(usage, prefix, "durations", keys, lowercase_key, countusersegments, countusertests, logdb, SQL);
		}

		// clean up empty segment and test data
		if (data.indexOf("counthits") > -1) {
			if (countusersegments && (((LinkedHashMap<String,String>)usage.get(prefix + "counthits" + "segment")).size() < ((LinkedHashMap<String,String>)usage.get(prefix + "counthits")).size())) {
				usage.remove(prefix + "counthits" + "segment");
				usage.remove(prefix + "counthits" + "segments");
				usage.remove(prefix + "counthits" + "segmentstests");
				usage.remove(prefix + "countpageviews" + "segment");
				usage.remove(prefix + "countpageviews" + "segments");
				usage.remove(prefix + "countpageviews" + "segmentstests");
				usage.remove(prefix + "countvisits" + "segment");
				usage.remove(prefix + "countvisits" + "segments");
				usage.remove(prefix + "countvisits" + "segmentstests");
				usage.remove(prefix + "countvisitors" + "segment");
				usage.remove(prefix + "countvisitors" + "segments");
				usage.remove(prefix + "countvisitors" + "segmentstests");
				usage.remove(prefix + "countclienthosts" + "segment");
				usage.remove(prefix + "countclienthosts" + "segments");
				usage.remove(prefix + "countclienthosts" + "segmentstests");
			}
			if (countusertests && (((LinkedHashMap<String,String>)usage.get(prefix + "counthits" + "test")).size() < ((LinkedHashMap<String,String>)usage.get(prefix + "counthits")).size())) {
				usage.remove(prefix + "counthits" + "test");
				usage.remove(prefix + "counthits" + "tests");
				usage.remove(prefix + "counthits" + "segmentstests");
				usage.remove(prefix + "countpageviews" + "test");
				usage.remove(prefix + "countpageviews" + "tests");
				usage.remove(prefix + "countpageviews" + "segmentstests");
				usage.remove(prefix + "countvisits" + "test");
				usage.remove(prefix + "countvisits" + "tests");
				usage.remove(prefix + "countvisits" + "segmentstests");
				usage.remove(prefix + "countvisitors" + "test");
				usage.remove(prefix + "countvisitors" + "tests");
				usage.remove(prefix + "countvisitors" + "segmentstests");
				usage.remove(prefix + "countclienthosts" + "test");
				usage.remove(prefix + "countclienthosts" + "tests");
				usage.remove(prefix + "countclienthosts" + "segmentstests");
			}
		}
		return usage;
	}



	HashMap<String,LinkedHashMap<String,String>> getRankedDataParts(HashMap<String,LinkedHashMap<String,String>> usage, String prefix, String columnname, String keys, boolean lowercase_key, boolean countusersegments, boolean countusertests, DB logdb, String SQL) {
		HashMap<String,HashMap<String,String>> countdata = logdb.query_records(SQL);
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
					String[] myusersegments;
					if (((HashMap<String,String>)countdata.get(mycountdata)).get("usersegments") == null) {
						myusersegments = "".split(",");
					} else {
						myusersegments = ("" + ((HashMap<String,String>)countdata.get(mycountdata)).get("usersegments")).split(",");
					}
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
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsWebsites + SQLcolumnsVariantsSegments, "1=1", "", "websites", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusersegments", "requesthost", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsWebsites + SQLcolumnsVariantsUsertests, "1=1", "", "websites", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusertests", "requesthost", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsWebsites + SQLcolumnsVariants, "1=1", "", "websites", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusersegments+countusertests", "requesthost", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsWebsites, "1=1", "", "websites", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "requesthost", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getCountries(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsCountries + SQLcolumnsVariantsSegments, "1=1", "", "countries", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusersegments", "clienthosttld", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsCountries + SQLcolumnsVariantsUsertests, "1=1", "", "countries", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusertests", "clienthosttld", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsCountries + SQLcolumnsVariants, "1=1", "", "countries", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusersegments+countusertests", "clienthosttld", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsCountries, "1=1", "", "countries", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "clienthosttld", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getClienthosts(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsClienthosts + SQLcolumnsVariantsSegments, "(" + logdb.is_not_blank("clienthost") + " and (clienthost <> '-'))", "", "clienthosts", "countvisitors+countvisits+countpageviews+counthits+countusersegments", "clienthost", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsClienthosts + SQLcolumnsVariantsUsertests, "(" + logdb.is_not_blank("clienthost") + " and (clienthost <> '-'))", "", "clienthosts", "countvisitors+countvisits+countpageviews+counthits+countusertests", "clienthost", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsClienthosts + SQLcolumnsVariants, "(" + logdb.is_not_blank("clienthost") + " and (clienthost <> '-'))", "", "clienthosts", "countvisitors+countvisits+countpageviews+counthits+countusersegments+countusertests", "clienthost", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsClienthosts, "(" + logdb.is_not_blank("clienthost") + " and (clienthost <> '-'))", "", "clienthosts", "countvisitors+countvisits+countpageviews+counthits", "clienthost", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getVisitors(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsVisitors + SQLcolumnsVariantsSegments, "(" + logdb.is_not_blank("visitorid") + " and (visitorid <> '-'))", "", "visitors", "countvisits+countpageviews+counthits+countusersegments", "visitorid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsVisitors + SQLcolumnsVariantsUsertests, "(" + logdb.is_not_blank("visitorid") + " and (visitorid <> '-'))", "", "visitors", "countvisits+countpageviews+counthits+countusertests", "visitorid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsVisitors + SQLcolumnsVariants, "(" + logdb.is_not_blank("visitorid") + " and (visitorid <> '-'))", "", "visitors", "countvisits+countpageviews+counthits+countusersegments+countusertests", "visitorid", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsVisitors, "(" + logdb.is_not_blank("visitorid") + " and (visitorid <> '-'))", "", "visitors", "countvisits+countpageviews+counthits", "visitorid", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getVisits(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsVisits + SQLcolumnsVariantsSegments, "1=1", SQLcolumnsVisits, "visits", "countpageviews+counthits+durations+countusersegments", "clienthost:::visitorid:::sessionid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsVisits + SQLcolumnsVariantsUsertests, "1=1", SQLcolumnsVisits, "visits", "countpageviews+counthits+durations+countusertests", "clienthost:::visitorid:::sessionid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsVisits + SQLcolumnsVariants, "1=1", SQLcolumnsVisits, "visits", "countpageviews+counthits+durations+countusersegments+countusertests", "clienthost:::visitorid:::sessionid", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsVisits, "1=1", SQLcolumnsVisits, "visits", "countpageviews+counthits+durations", "clienthost:::visitorid:::sessionid", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getClienthost(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsClienthost + SQLcolumnsVariantsSegments, "clienthost=" + logdb.quote(myrequest.getParameter("clienthost")), SQLcolumnsClienthost, "clienthost", "countvisitors+countvisits+countpageviews+counthits+durations+countusersegments", "clienthost:::visitorid:::sessionid", false, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsClienthost + SQLcolumnsVariantsUsertests, "clienthost=" + logdb.quote(myrequest.getParameter("clienthost")), SQLcolumnsClienthost, "clienthost", "countvisitors+countvisits+countpageviews+counthits+durations+countusertests", "clienthost:::visitorid:::sessionid", false, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsClienthost + SQLcolumnsVariants, "clienthost=" + logdb.quote(myrequest.getParameter("clienthost")), SQLcolumnsClienthost, "clienthost", "countvisitors+countvisits+countpageviews+counthits+durations+countusersegments+countusertests", "clienthost:::visitorid:::sessionid", false, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsClienthost, "clienthost=" + logdb.quote(myrequest.getParameter("clienthost")), SQLcolumnsClienthost, "clienthost", "countvisitors+countvisits+countpageviews+counthits+durations", "clienthost:::visitorid:::sessionid", false, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getVisitor(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsVisitor + SQLcolumnsVariantsSegments, "visitorid=" + logdb.quote(myrequest.getParameter("visitor")) + " and (clienthost <> '-')", SQLcolumnsVisitor, "visitor", "countpageviews+counthits+durations+countusersegments", "clienthost:::visitorid:::sessionid", false, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsVisitor + SQLcolumnsVariantsUsertests, "visitorid=" + logdb.quote(myrequest.getParameter("visitor")) + " and (clienthost <> '-')", SQLcolumnsVisitor, "visitor", "countpageviews+counthits+durations+countusertests", "clienthost:::visitorid:::sessionid", false, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsVisitor + SQLcolumnsVariants, "visitorid=" + logdb.quote(myrequest.getParameter("visitor")) + " and (clienthost <> '-')", SQLcolumnsVisitor, "visitor", "countpageviews+counthits+durations+countusersegments+countusertests", "clienthost:::visitorid:::sessionid", false, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsVisitor, "visitorid=" + logdb.quote(myrequest.getParameter("visitor")) + " and (clienthost <> '-')", SQLcolumnsVisitor, "visitor", "countpageviews+counthits+durations", "clienthost:::visitorid:::sessionid", false, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getUsername(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsUsername + SQLcolumnsVariantsSegments, "clientusername=" + logdb.quote(myrequest.getParameter("username")) + " and (clienthost <> '-')", SQLcolumnsUsername, "username", "countpageviews+counthits+durations+countusersegments", "clienthost:::visitorid:::sessionid", false, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsUsername + SQLcolumnsVariantsUsertests, "clientusername=" + logdb.quote(myrequest.getParameter("username")) + " and (clienthost <> '-')", SQLcolumnsUsername, "username", "countpageviews+counthits+durations+countusertests", "clienthost:::visitorid:::sessionid", false, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsUsername + SQLcolumnsVariants, "clientusername=" + logdb.quote(myrequest.getParameter("username")) + " and (clienthost <> '-')", SQLcolumnsUsername, "username", "countpageviews+counthits+durations+countusersegments+countusertests", "clienthost:::visitorid:::sessionid", false, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsUsername, "clientusername=" + logdb.quote(myrequest.getParameter("username")) + " and (clienthost <> '-')", SQLcolumnsUsername, "username", "countpageviews+counthits+durations", "clienthost:::visitorid:::sessionid", false, false);
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
		if (! myrequest.getParameter("clienthost").equals("")) {
			SQL = "select * from usagelog where " + SQLwhere + " and clienthost=" + logdb.quote(myrequest.getParameter("clienthost")) + " and ((" + logdb.is_not_blank("requestclass") + ") or (summarised='clienthost')) order by datetimefull asc, requestclass desc, requestid asc";
		} else if (! myrequest.getParameter("visitor").equals("")) {
			SQL = "select * from usagelog where " + SQLwhere + " and visitorid=" + logdb.quote(myrequest.getParameter("visitor")) + " and ((" + logdb.is_not_blank("requestclass") + ") or (summarised='visitorid')) order by datetimefull asc, requestclass desc, requestid asc";
		} else {
			SQL = "select * from usagelog where " + SQLwhere + " and sessionid=" + logdb.quote(myrequest.getParameter("visit")) + " and ((" + logdb.is_not_blank("requestclass") + ") or (summarised='sessionid')) order by datetimefull asc, requestclass desc, requestid asc";
		}
		HashMap hits = logdb.query_records(SQL);
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
		SQLwhere = SQLwhereNotRobots(db, SQLwhere);

		String SQL = "";
		SQL = "select * from usagelog where " + SQLwhere + " and requestid=" + myrequest.getParameter("id") + " and " + logdb.is_blank("requestdatabase") + " order by clientusername, clienthost, visitorid, sessionid, datetimefull asc";
		HashMap hits = logdb.query_records(SQL);
		usage.put("contentitem", new LinkedHashMap());
		for (int i=0; i<hits.size(); i++) {
			String hit = "" + i;
			((LinkedHashMap)usage.get("contentitem")).put("" + i, hits.get(hit));
		}

		if (myrequest.getParameter("contentclass").equals("page")) {
			SQL = SQLhits(logdb, SQLcolumnsPaths, "usagelog", SQLwhere + " and requestid=" + myrequest.getParameter("id") + " and refererid<>0 and " + SQLwherePage, "");
		} else {
			SQL = SQLhits(logdb, SQLcolumnsPaths, "usagelog", SQLwhere + " and requestid=" + myrequest.getParameter("id") + " and refererid<>0", "");
		}
		HashMap contentitemfrompaths = logdb.query_records(SQL);
		usage.put("contentitemfrompaths", new LinkedHashMap());
		for (int contentitemfrompath=0; contentitemfrompath<contentitemfrompaths.size(); contentitemfrompath++) {
			String paths = "" + ((HashMap)contentitemfrompaths.get("" + contentitemfrompath)).get("refererclass") + ":::" + ((HashMap)contentitemfrompaths.get("" + contentitemfrompath)).get("refererdatabase") + ":::" + ((HashMap)contentitemfrompaths.get("" + contentitemfrompath)).get("refererid") + ":::" + ((HashMap)contentitemfrompaths.get("" + contentitemfrompath)).get("requestclass") + ":::" + ((HashMap)contentitemfrompaths.get("" + contentitemfrompath)).get("requestdatabase") + ":::" + ((HashMap)contentitemfrompaths.get("" + contentitemfrompath)).get("requestid");
			((LinkedHashMap)usage.get("contentitemfrompaths")).put(paths, "" + ((HashMap)contentitemfrompaths.get("" + contentitemfrompath)).get("counthits"));
		}

		if (myrequest.getParameter("contentclass").equals("page")) {
			SQL = SQLhits(logdb, SQLcolumnsPaths, "usagelog", SQLwhere + " and refererid=" + myrequest.getParameter("id") + " and refererid<>0 and " + SQLwherePage, "");
		} else {
			SQL = SQLhits(logdb, SQLcolumnsPaths, "usagelog", SQLwhere + " and refererid=" + myrequest.getParameter("id") + " and refererid<>0", "");
		}
		HashMap contentitemtopaths = logdb.query_records(SQL);
		usage.put("contentitemtopaths", new LinkedHashMap());
		for (int contentitemtopath=0; contentitemtopath<contentitemtopaths.size(); contentitemtopath++) {
			String paths = "" + ((HashMap)contentitemtopaths.get("" + contentitemtopath)).get("refererclass") + ":::" + ((HashMap)contentitemtopaths.get("" + contentitemtopath)).get("refererdatabase") + ":::" + ((HashMap)contentitemtopaths.get("" + contentitemtopath)).get("refererid") + ":::" + ((HashMap)contentitemtopaths.get("" + contentitemtopath)).get("requestclass") + ":::" + ((HashMap)contentitemtopaths.get("" + contentitemtopath)).get("requestdatabase") + ":::" + ((HashMap)contentitemtopaths.get("" + contentitemtopath)).get("requestid");
			((LinkedHashMap)usage.get("contentitemtopaths")).put(paths, "" + ((HashMap)contentitemtopaths.get("" + contentitemtopath)).get("counthits"));
		}

		SQL = SQLhits(logdb, SQLcolumnsReferers, "usagelog", SQLwhere + " and requestid=" + myrequest.getParameter("id") + " and " + logdb.is_not_blank("refererhost") + "", "");
		HashMap contentitemrefererscounthits = logdb.query_records(SQL);
		usage.put("contentitemrefererscounthits", new LinkedHashMap());
		for (int contentitemrefererscounthit=0; contentitemrefererscounthit<contentitemrefererscounthits.size(); contentitemrefererscounthit++) {
			String referers = "" + ((HashMap)contentitemrefererscounthits.get("" + contentitemrefererscounthit)).get("refererhost");
			((LinkedHashMap)usage.get("contentitemrefererscounthits")).put(referers, "" + ((HashMap)contentitemrefererscounthits.get("" + contentitemrefererscounthit)).get("counthits"));
		}

		SQL = SQLhits(logdb, SQLcolumnsRefererPages, "usagelog", SQLwhere + " and requestid=" + myrequest.getParameter("id") + " and " + logdb.is_not_blank("refererhost") + "", "");
		HashMap contentitemrefererpathscounthits = logdb.query_records(SQL);
		usage.put("contentitemrefererpathscounthits", new LinkedHashMap());
		for (int contentitemrefererpathscounthit=0; contentitemrefererpathscounthit<contentitemrefererpathscounthits.size(); contentitemrefererpathscounthit++) {
			String refererpaths = "" + ((HashMap)contentitemrefererpathscounthits.get("" + contentitemrefererpathscounthit)).get("refererhost") + ((HashMap)contentitemrefererpathscounthits.get("" + contentitemrefererpathscounthit)).get("refererpath");
			((LinkedHashMap)usage.get("contentitemrefererpathscounthits")).put(refererpaths, "" + ((HashMap)contentitemrefererpathscounthits.get("" + contentitemrefererpathscounthit)).get("counthits"));
		}

		return usage;
	}



	public HashMap<String,LinkedHashMap<String,HashMap<String,String>>> getDataItem(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,HashMap<String,String>>>();
		HashMap<String,LinkedHashMap<String,HashMap<String,String>>> usage = new HashMap<String,LinkedHashMap<String,HashMap<String,String>>>();
		Date mynow = new Date();
		String SQLwhere = SQLwherePeriod(db, mysession, mynow, "");
		SQLwhere = SQLwhereNotRobots(db, SQLwhere);

		String SQL = "";
		SQL = "select * from usagelog where " + SQLwhere + " and requestid=" + myrequest.getParameter("id") + " and requestdatabase=" + logdb.quote(myrequest.getParameter("database")) + " order by clientusername, clienthost, visitorid, sessionid, datetimefull asc";
		HashMap<String,HashMap<String,String>> hits = logdb.query_records(SQL);
		usage.put("dataitem", new LinkedHashMap<String,HashMap<String,String>>());
		for (int i=0; i<hits.size(); i++) {
			String hit = "" + i;
			((LinkedHashMap<String,HashMap<String,String>>)usage.get("dataitem")).put("" + i, hits.get(hit));
		}

		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getRobots(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		UsageLog usagelog = new UsageLog();
		StringBuffer clientbrowsers = new StringBuffer();
		clientbrowsers.append("'" + usagelog.robotName[0]);
		for (int i=1; i<usagelog.robotName.length; i++) clientbrowsers.append("','" + usagelog.robotName[i]);
		clientbrowsers.append("'");
		return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsRobots, "clientbrowser in (" + clientbrowsers + ")", "", "robots", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "clientbrowser", false, false);
	}



	public HashMap<String,LinkedHashMap<String,String>> getWebBrowsers(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		UsageLog usagelog = new UsageLog();
		StringBuffer clientbrowsers = new StringBuffer();
		clientbrowsers.append("'" + usagelog.browserName[0]);
		for (int i=1; i<usagelog.browserName.length; i++) clientbrowsers.append("','" + usagelog.browserName[i]);
		clientbrowsers.append("'");
		HashMap<String,LinkedHashMap<String,String>> usage = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsWebBrowsers, "clientbrowser in (" + clientbrowsers + ")", "", "webbrowsers", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "clientbrowser", false, false);
		HashMap<String,LinkedHashMap<String,String>> usage2 = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsWebBrowserVersions, "clientbrowser in (" + clientbrowsers + ")", "", "webbrowserversions", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "clientbrowser:::clientversion", false, false);
		usage.put("webbrowserversionscountclienthosts", (LinkedHashMap<String,String>)usage2.get("webbrowserversionscountclienthosts"));
		usage.put("webbrowserversionscountvisitors", (LinkedHashMap<String,String>)usage2.get("webbrowserversionscountvisitors"));
		usage.put("webbrowserversionscountvisits", (LinkedHashMap<String,String>)usage2.get("webbrowserversionscountvisits"));
		usage.put("webbrowserversionscountpageviews", (LinkedHashMap<String,String>)usage2.get("webbrowserversionscountpageviews"));
		usage.put("webbrowserversionscounthits", (LinkedHashMap<String,String>)usage2.get("webbrowserversionscounthits"));
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getDevices(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		UsageLog usagelog = new UsageLog();
		StringBuffer clientdevices = new StringBuffer();
		clientdevices.append("'','-','" + usagelog.deviceType[0]);
		for (int i=1; i<usagelog.deviceType.length; i++) clientdevices.append("','" + usagelog.deviceType[i]);
		clientdevices.append("'");
		HashMap<String,LinkedHashMap<String,String>> usage = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsDevices, "clientdevice in (" + clientdevices + ")", "", "devices", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "clientdevice", false, false);
		HashMap<String,LinkedHashMap<String,String>> usage2 = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsDeviceVersions, "clientdevice in (" + clientdevices + ")", "", "deviceversions", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "clientdevice:::clientdeviceversion", false, false);
		usage.put("deviceversionscountclienthosts", (LinkedHashMap<String,String>)usage2.get("deviceversionscountclienthosts"));
		usage.put("deviceversionscountvisitors", (LinkedHashMap<String,String>)usage2.get("deviceversionscountvisitors"));
		usage.put("deviceversionscountvisits", (LinkedHashMap<String,String>)usage2.get("deviceversionscountvisits"));
		usage.put("deviceversionscountpageviews", (LinkedHashMap<String,String>)usage2.get("deviceversionscountpageviews"));
		usage.put("deviceversionscounthits", (LinkedHashMap<String,String>)usage2.get("deviceversionscounthits"));
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
		HashMap<String,LinkedHashMap<String,String>> usage = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsOperatingSystems, "clientos in (" + clientos + ")", "", "operatingsystems", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "clientos", false, false);
		HashMap<String,LinkedHashMap<String,String>> usage2 = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsOperatingSystemVersions, "clientos in (" + clientos + ")", "", "operatingsystemversions", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "clientos:::clientosversion", false, false);
		usage.put("operatingsystemversionscountclienthosts", (LinkedHashMap<String,String>)usage2.get("operatingsystemversionscountclienthosts"));
		usage.put("operatingsystemversionscountvisitors", (LinkedHashMap<String,String>)usage2.get("operatingsystemversionscountvisitors"));
		usage.put("operatingsystemversionscountvisits", (LinkedHashMap<String,String>)usage2.get("operatingsystemversionscountvisits"));
		usage.put("operatingsystemversionscountpageviews", (LinkedHashMap<String,String>)usage2.get("operatingsystemversionscountpageviews"));
		usage.put("operatingsystemversionscounthits", (LinkedHashMap<String,String>)usage2.get("operatingsystemversionscounthits"));
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getUsers(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		String SQLtable;
		String SQLwhere;
		if (myrequest.getParameter("usergroup").equals("-")) {
			String SQL = "select username from users where " + db.is_blank("usergroup");
			String users_usernames = Common.SQL_list_row_ids(db, db.query_records(SQL));
			SQLtable = "usagelog";
			if (logdb.db_type(database).equals("oracle")) {
				SQLwhere = "" + logdb.is_not_blank("clientusername") + " and clientusername in (" + SQL + ")";
			} else {
				SQLwhere = "" + logdb.is_not_blank("clientusername") + " and clientusername in (" + users_usernames + ")";
			}
		} else if ((! myrequest.getParameter("usergroup").equals("")) && (! myrequest.getParameter("usergroup").equals(" "))) {
			String SQL = "select username from users where usergroup=" + db.quote(myrequest.getParameter("usergroup"));
			String users_usernames = Common.SQL_list_row_ids(db, db.query_records(SQL));
			SQLtable = "usagelog";
			if (logdb.db_type(database).equals("oracle")) {
				SQLwhere = "" + logdb.is_not_blank("clientusername") + " and clientusername in (" + SQL + ")";
			} else {
				SQLwhere = "" + logdb.is_not_blank("clientusername") + " and clientusername in (" + users_usernames + ")";
			}
		} else if (myrequest.getParameter("usertype").equals("-")) {
			String SQL = "select username from users where " + db.is_blank("usertype");
			String users_usernames = Common.SQL_list_row_ids(db, db.query_records(SQL));
			SQLtable = "usagelog";
			if (logdb.db_type(database).equals("oracle")) {
				SQLwhere = "" + logdb.is_not_blank("clientusername") + " and clientusername in (" + SQL + ")";
			} else {
				SQLwhere = "" + logdb.is_not_blank("clientusername") + " and clientusername in (" + users_usernames + ")";
			}
		} else if ((! myrequest.getParameter("usertype").equals("")) && (! myrequest.getParameter("usertype").equals(" "))) {
			String SQL = "select username from users where usertype=" + db.quote(myrequest.getParameter("usertype"));
			String users_usernames = Common.SQL_list_row_ids(db, db.query_records(SQL));
			SQLtable = "usagelog";
			if (logdb.db_type(database).equals("oracle")) {
				SQLwhere = "" + logdb.is_not_blank("clientusername") + " and clientusername in (" + SQL + ")";
			} else {
				SQLwhere = "" + logdb.is_not_blank("clientusername") + " and clientusername in (" + users_usernames + ")";
			}
		} else {
			SQLtable = "usagelog";
			SQLwhere = "1=1";
		}
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsUsers + SQLcolumnsVariantsSegments, SQLwhere, "", "users", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusersegments", "clientusername", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsUsers + SQLcolumnsVariantsUsertests, SQLwhere, "", "users", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusertests", "clientusername", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsUsers + SQLcolumnsVariants, SQLwhere, "", "users", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusersegments+countusertests", "clientusername", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsUsers, SQLwhere, "", "users", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "clientusername", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getUsergroups(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,LinkedHashMap<String,String>> usage = new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,HashMap<String,String>> rows = db.query_records("select usergroup from usergroups");
		rows.put("", new HashMap<String,String>());
		Iterator rowsIterator = rows.keySet().iterator();
		while (rowsIterator.hasNext()) {
			String key = "" + rowsIterator.next();
			HashMap<String,String> row = (HashMap<String,String>)rows.get(key);
			if (row.get("usergroup") == null) row.put("usergroup","");
			String user_ids = Common.SQL_list_row_ids(db, db.query_records("select username from users where " + db.is_not_blank("username") + Common.SQLwhere_equals(db, " ", "usergroup", "" + row.get("usergroup"))));
			HashMap<String,LinkedHashMap<String,String>> output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("usergroup")) + " as usergroup", "" + logdb.is_not_blank("clientusername") + " and clientusername in (" + user_ids + ")", "", "usergroups", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "usergroup", true, false);
			if (usage.get("usergroupscountclienthosts") == null) { usage.put("usergroupscountclienthosts", new LinkedHashMap<String,String>()); }
			if (usage.get("usergroupscountvisitors") == null) { usage.put("usergroupscountvisitors", new LinkedHashMap<String,String>()); }
			if (usage.get("usergroupscountvisits") == null) { usage.put("usergroupscountvisits", new LinkedHashMap<String,String>()); }
			if (usage.get("usergroupscountpageviews") == null) { usage.put("usergroupscountpageviews", new LinkedHashMap<String,String>()); }
			if (usage.get("usergroupscounthits") == null) { usage.put("usergroupscounthits", new LinkedHashMap<String,String>()); }
			usage.put("usergroupscountclienthosts", Common.array_merge((LinkedHashMap<String,String>)usage.get("usergroupscountclienthosts"),(LinkedHashMap<String,String>)output.get("usergroupscountclienthosts")));
			usage.put("usergroupscountvisitors", Common.array_merge((LinkedHashMap<String,String>)usage.get("usergroupscountvisitors"),(LinkedHashMap<String,String>)output.get("usergroupscountvisitors")));
			usage.put("usergroupscountvisits", Common.array_merge((LinkedHashMap<String,String>)usage.get("usergroupscountvisits"),(LinkedHashMap<String,String>)output.get("usergroupscountvisits")));
			usage.put("usergroupscountpageviews", Common.array_merge((LinkedHashMap<String,String>)usage.get("usergroupscountpageviews"),(LinkedHashMap<String,String>)output.get("usergroupscountpageviews")));
			usage.put("usergroupscounthits", Common.array_merge((LinkedHashMap<String,String>)usage.get("usergroupscounthits"),(LinkedHashMap<String,String>)output.get("usergroupscounthits")));
		}
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getUsertypes(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,LinkedHashMap<String,String>> usage = new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,HashMap<String,String>> rows = db.query_records("select usertype from usertypes");
		rows.put("", new HashMap<String,String>());
		Iterator rowsIterator = rows.keySet().iterator();
		while (rowsIterator.hasNext()) {
			String key = "" + rowsIterator.next();
			HashMap<String,String> row = (HashMap<String,String>)rows.get(key);
			if (row.get("usertype") == null) row.put("usertype","");
			String user_ids = Common.SQL_list_row_ids(db, db.query_records("select username from users where " + db.is_not_blank("username") + Common.SQLwhere_equals(db, " ", "usertype", "" + row.get("usertype"))));
			HashMap<String,LinkedHashMap<String,String>> output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("usertype")) + " as usertype", "" + logdb.is_not_blank("clientusername") + " and clientusername in (" + user_ids + ")", "", "usertypes", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "usertype", true, false);
			if (usage.get("usertypescountclienthosts") == null) { usage.put("usertypescountclienthosts", new LinkedHashMap<String,String>()); }
			if (usage.get("usertypescountvisitors") == null) { usage.put("usertypescountvisitors", new LinkedHashMap<String,String>()); }
			if (usage.get("usertypescountvisits") == null) { usage.put("usertypescountvisits", new LinkedHashMap<String,String>()); }
			if (usage.get("usertypescountpageviews") == null) { usage.put("usertypescountpageviews", new LinkedHashMap<String,String>()); }
			if (usage.get("usertypescounthits") == null) { usage.put("usertypescounthits", new LinkedHashMap<String,String>()); }
			usage.put("usertypescountclienthosts", Common.array_merge((LinkedHashMap<String,String>)usage.get("usertypescountclienthosts"),(LinkedHashMap<String,String>)output.get("usertypescountclienthosts")));
			usage.put("usertypescountvisitors", Common.array_merge((LinkedHashMap<String,String>)usage.get("usertypescountvisitors"),(LinkedHashMap<String,String>)output.get("usertypescountvisitors")));
			usage.put("usertypescountvisits", Common.array_merge((LinkedHashMap<String,String>)usage.get("usertypescountvisits"),(LinkedHashMap<String,String>)output.get("usertypescountvisits")));
			usage.put("usertypescountpageviews", Common.array_merge((LinkedHashMap<String,String>)usage.get("usertypescountpageviews"),(LinkedHashMap<String,String>)output.get("usertypescountpageviews")));
			usage.put("usertypescounthits", Common.array_merge((LinkedHashMap<String,String>)usage.get("usertypescounthits"),(LinkedHashMap<String,String>)output.get("usertypescounthits")));
		}
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getUsersegments(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsUsersegments, "1=1", "", "usersegments", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusersegments", "usersegments", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsUsersegments + SQLcolumnsVariantsUsertests, "1=1", "", "usersegments", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusersegments+countusertests", "usersegments", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsUsersegments + SQLcolumnsVariantsUsertests, "1=1", "", "usersegments", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusersegments+countusertests", "usersegments", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsUsersegments, "1=1", "", "usersegments", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusersegments", "usersegments", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getUsertests(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsUsertests + SQLcolumnsVariantsSegments, "1=1", "", "usertests", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusersegments+countusertests", "usertests", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsUsertests, "1=1", "", "usertests", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusertests", "usertests", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsUsertests + SQLcolumnsVariantsSegments, "1=1", "", "usertests", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusersegments+countusertests", "usertests", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsUsertests, "1=1", "", "usertests", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusertests", "usertests", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getReferers(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		UsageLog usagelog = new UsageLog();
		StringBuffer clientbrowsers = new StringBuffer();
		clientbrowsers.append("'','-','" + usagelog.browserName[0]);
		for (int i=1; i<usagelog.browserName.length; i++) clientbrowsers.append("','" + usagelog.browserName[i]);
		clientbrowsers.append("'");
		HashMap<String,LinkedHashMap<String,String>> usage = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsReferers, "1=1", "", "referers", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "refererhost", true, false);
		HashMap<String,LinkedHashMap<String,String>> usage2 = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsRefererPages, "1=1", "", "refererpaths", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "refererhost:::refererpath", true, false);
		usage.put("refererpathscountclienthosts", (LinkedHashMap<String,String>)usage2.get("refererpathscountclienthosts"));
		usage.put("refererpathscountvisitors", (LinkedHashMap<String,String>)usage2.get("refererpathscountvisitors"));
		usage.put("refererpathscountvisits", (LinkedHashMap<String,String>)usage2.get("refererpathscountvisits"));
		usage.put("refererpathscountpageviews", (LinkedHashMap<String,String>)usage2.get("refererpathscountpageviews"));
		usage.put("refererpathscounthits", (LinkedHashMap<String,String>)usage2.get("refererpathscounthits"));
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getSearchEngines(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		UsageLog usagelog = new UsageLog();
		StringBuffer referersearchengines = new StringBuffer();
		referersearchengines.append("'','-','" + usagelog.searchengineName[0]);
		for (int i=1; i<usagelog.searchengineName.length; i++) referersearchengines.append("','" + usagelog.searchengineName[i]);
		referersearchengines.append("'");
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsSearchEngines + SQLcolumnsVariantsSegments, "referersearchengine in (" + referersearchengines + ")", "", "searchengines", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusersegments", "referersearchengine", false, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsSearchEngines + SQLcolumnsVariantsUsertests, "referersearchengine in (" + referersearchengines + ")", "", "searchengines", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusertests", "referersearchengine", false, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsSearchEngines + SQLcolumnsVariants, "referersearchengine in (" + referersearchengines + ")", "", "searchengines", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusersegments+countusertests", "referersearchengine", false, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsSearchEngines, "referersearchengine in (" + referersearchengines + ")", "", "searchengines", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "referersearchengine", false, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getSearchQueries(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		String SQLwhere;
		SQLwhere = "" + logdb.is_not_blank("referersearchquery") + " and " + "(referersearchquery<>'-')";
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsSearchQueries + SQLcolumnsVariantsSegments, SQLwhere, "", "searchqueries", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusersegments", "referersearchquery", true, true);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsSearchQueries + SQLcolumnsVariantsUsertests, SQLwhere, "", "searchqueries", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusertests", "referersearchquery", true, true);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsSearchQueries + SQLcolumnsVariants, SQLwhere, "", "searchqueries", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+countusersegments+countusertests", "referersearchquery", true, true);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsSearchQueries, SQLwhere, "", "searchqueries", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "referersearchquery", true, true);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getSearchWords(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,LinkedHashMap<String,String>> usage = getSearchQueries(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		usage.put("searchwordscountclienthosts", new LinkedHashMap<String,String>());
		usage.put("searchwordscountvisitors", new LinkedHashMap<String,String>());
		usage.put("searchwordscountvisits", new LinkedHashMap<String,String>());
		usage.put("searchwordscountpageviews", new LinkedHashMap<String,String>());
		usage.put("searchwordscounthits", new LinkedHashMap<String,String>());
		Iterator mysearchqueriescountpageviews = ((LinkedHashMap<String,String>)usage.get("searchqueriescounthits")).keySet().iterator();
		while (mysearchqueriescountpageviews.hasNext()) {
			String searchquery = "" + mysearchqueriescountpageviews.next();
			String[] searchwords = searchquery.replaceAll("[^-_a-zA-Z0-9]+", " ").trim().split(" ");
			for (int i=0; i<searchwords.length; i++) {
				String searchword = searchwords[i];
				((HashMap<String,String>)usage.get("searchwordscountclienthosts")).put(searchword, "" + new Long(Math.round(Common.number("" + ((HashMap<String,String>)usage.get("searchwordscountclienthosts")).get(searchword)) + Common.number("" + ((HashMap<String,String>)usage.get("searchqueriescountclienthosts")).get(searchquery)))));
				((HashMap<String,String>)usage.get("searchwordscountvisitors")).put(searchword, "" + new Long(Math.round(Common.number("" + ((HashMap<String,String>)usage.get("searchwordscountvisitors")).get(searchword)) + Common.number("" + ((HashMap<String,String>)usage.get("searchqueriescountvisitors")).get(searchquery)))));
				((HashMap<String,String>)usage.get("searchwordscountvisits")).put(searchword, "" + new Long(Math.round(Common.number("" + ((HashMap<String,String>)usage.get("searchwordscountvisits")).get(searchword)) + Common.number("" + ((HashMap<String,String>)usage.get("searchqueriescountvisits")).get(searchquery)))));
				((HashMap<String,String>)usage.get("searchwordscountpageviews")).put(searchword, "" + new Long(Math.round(Common.number("" + ((HashMap<String,String>)usage.get("searchwordscountpageviews")).get(searchword)) + Common.number("" + ((HashMap<String,String>)usage.get("searchqueriescountpageviews")).get(searchquery)))));
				((HashMap<String,String>)usage.get("searchwordscounthits")).put(searchword, "" + new Long(Math.round(Common.number("" + ((HashMap<String,String>)usage.get("searchwordscounthits")).get(searchword)) + Common.number("" + ((HashMap<String,String>)usage.get("searchqueriescounthits")).get(searchquery)))));
			}
		}
		usage.put("searchqueriescountclienthosts", new LinkedHashMap<String,String>());
		usage.put("searchqueriescountvisitors", new LinkedHashMap<String,String>());
		usage.put("searchqueriescountvisits", new LinkedHashMap<String,String>());
		usage.put("searchqueriescountpageviews", new LinkedHashMap<String,String>());
		usage.put("searchqueriescounthits", new LinkedHashMap<String,String>());
		usage.put("searchwordscountclienthosts", Common.LinkedHashMapSortedByValue((LinkedHashMap<String,String>)usage.get("searchwordscountclienthosts"), false));
		usage.put("searchwordscountvisitors", Common.LinkedHashMapSortedByValue((LinkedHashMap<String,String>)usage.get("searchwordscountvisitors"), false));
		usage.put("searchwordscountvisits", Common.LinkedHashMapSortedByValue((LinkedHashMap<String,String>)usage.get("searchwordscountvisits"), false));
		usage.put("searchwordscountpageviews", Common.LinkedHashMapSortedByValue((LinkedHashMap<String,String>)usage.get("searchwordscountpageviews"), false));
		usage.put("searchwordscounthits", Common.LinkedHashMapSortedByValue((LinkedHashMap<String,String>)usage.get("searchwordscounthits"), false));
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getEntry(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsEntry + SQLcolumnsVariantsSegments, SQLwherePage + " and refererid=0", "", "entry", "countclienthosts+countvisitors+countvisits+countusersegments", "requestclass:::requestdatabase:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsEntry + SQLcolumnsVariantsUsertests, SQLwherePage + " and refererid=0", "", "entry", "countclienthosts+countvisitors+countvisits+countusertests", "requestclass:::requestdatabase:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsEntry + SQLcolumnsVariants, SQLwherePage + " and refererid=0", "", "entry", "countclienthosts+countvisitors+countvisits+countusersegments+countusertests", "requestclass:::requestdatabase:::requestid", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsEntry, SQLwherePage + " and refererid=0", "", "entry", "countclienthosts+countvisitors+countvisits", "requestclass:::requestdatabase:::requestid", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getExit(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,LinkedHashMap<String,String>> usage = new HashMap<String,LinkedHashMap<String,String>>();
		Date mynow = new Date();
		String SQLwhere = SQLwherePeriod(db, mysession, mynow, "");
		SQLwhere = SQLwhereNotRobots(db, SQLwhere);

		String SQL = "";
		if (logdb.db_type(database).equals("mysql")) {
			// MySQL v3.3-v4.0 does not support sub-selects
			// select exit page for each sessionid and count manually
			SQL = "select q1.requestid, q1.requestclass, q1.requestdatabase, q1.clienthost, q1.sessionid";
			SQL = SQL + " from usagelog q1 left join usagelog q2 on q1.sessionid=q2.sessionid and q1.sessionduration <= q2.sessionduration";
			SQL = SQL + " where " + SQLwherePeriod(db, mysession, mynow, "q1.") + " and q1." + SQLwherePage + "";
			SQL = SQL + " and " + SQLwherePeriod(db, mysession, mynow, "q2.") + " and q2." + SQLwherePage + "";
			SQL = SQL + " group by q1.requestid, q1.requestclass, q1.requestdatabase, q1.clienthost, q1.sessionid, q1.sessionduration having count(q2.sessionduration)=1";
			SQL = SQL + " order by q1.requestid, q1.requestclass, q1.requestdatabase, q1.clienthost, q1.sessionid, q1.sessionduration";
			HashMap<String,HashMap<String,String>> exitpages = logdb.query_records(SQL);
			usage.put("exitcountclienthosts", new LinkedHashMap<String,String>());
			usage.put("exitcountvisitors", new LinkedHashMap<String,String>());
			usage.put("exitcountvisits", new LinkedHashMap<String,String>());
			String clienthost = "";
			String visitorid = "";
			String sessionid = "";
			for (int i=0; i<exitpages.size(); i++) {
				String exitpage = "" + i;
				String myexit = "" + ((HashMap<String,String>)exitpages.get(exitpage)).get("requestclass") + ":::" + ((HashMap<String,String>)exitpages.get(exitpage)).get("requestdatabase") + ":::" + ((HashMap<String,String>)exitpages.get(exitpage)).get("requestid");
				if (! clienthost.equals(myexit + ":::" + ((HashMap<String,String>)exitpages.get(exitpage)).get("clienthost"))) {
					clienthost = myexit + ":::" + ((HashMap<String,String>)exitpages.get(exitpage)).get("clienthost");
					((LinkedHashMap<String,String>)usage.get("exitcountclienthosts")).put(myexit, "" + new Long(Math.round(Common.number("" + ((HashMap<String,String>)usage.get("exitcountclienthosts")).get(myexit)) + 1)));
				}
				if (! visitorid.equals(myexit + ":::" + ((HashMap<String,String>)exitpages.get(exitpage)).get("visitorid"))) {
					visitorid = myexit + ":::" + ((HashMap<String,String>)exitpages.get(exitpage)).get("visitorid");
					((LinkedHashMap<String,String>)usage.get("exitcountvisitors")).put(myexit, "" + new Long(Math.round(Common.number("" + ((HashMap<String,String>)usage.get("exitcountvisitors")).get(myexit)) + 1)));
				}
				if (! sessionid.equals(myexit + ":::" + ((HashMap<String,String>)exitpages.get(exitpage)).get("sessionid"))) {
					sessionid = myexit + ":::" + ((HashMap<String,String>)exitpages.get(exitpage)).get("sessionid");
					((LinkedHashMap<String,String>)usage.get("exitcountvisits")).put(myexit, "" + new Long(Math.round(Common.number("" + ((HashMap<String,String>)usage.get("exitcountvisits")).get(myexit)) + 1)));
				}
			}
			usage.put("exitcountclienthosts", Common.LinkedHashMapSortedByValue((LinkedHashMap<String,String>)usage.get("exitcountclienthosts"), false));
			usage.put("exitcountvisitors", Common.LinkedHashMapSortedByValue((LinkedHashMap<String,String>)usage.get("exitcountvisitors"), false));
			usage.put("exitcountvisits", Common.LinkedHashMapSortedByValue((LinkedHashMap<String,String>)usage.get("exitcountvisits"), false));
		} else {
			if ((logdb.db_type(database).equals("access")) || (logdb.db_type(database).equals("mssql"))) {
				SQL = SQLclienthosts(logdb, database, SQLcolumnsExit, "usagelog q1", SQLwhere + " and " + SQLwherePage + " and sessionduration in (select max(sessionduration) from usagelog q2 where q1.sessionid=q2.sessionid and " + SQLwherePage + ")", "");
			} else {
				SQL = SQLclienthosts(logdb, database, SQLcolumnsExit, "usagelog", SQLwhere + " and " + SQLwherePage + " and (sessionid, sessionduration) in (select sessionid, max(sessionduration) from usagelog where " + SQLwherePage + " group by sessionid)", "");
			}
			HashMap<String,HashMap<String,String>> exitcountclienthosts = logdb.query_records(SQL);
			usage.put("exitcountclienthosts", new LinkedHashMap<String,String>());
			for (int i=0; i<exitcountclienthosts.size(); i++) {
				String exitcountclienthost = "" + i;
				String myexit = "" + ((HashMap<String,String>)exitcountclienthosts.get(exitcountclienthost)).get("requestclass") + ":::" + ((HashMap<String,String>)exitcountclienthosts.get(exitcountclienthost)).get("requestdatabase") + ":::" + ((HashMap<String,String>)exitcountclienthosts.get(exitcountclienthost)).get("requestid");
				((HashMap<String,String>)usage.get("exitcountclienthosts")).put(myexit, "" + ((HashMap<String,String>)exitcountclienthosts.get(exitcountclienthost)).get("countclienthosts"));
			}

			if ((logdb.db_type(database).equals("access")) || (logdb.db_type(database).equals("mssql"))) {
				SQL = SQLvisitors(logdb, database, SQLcolumnsExit, "usagelog q1", SQLwhere + " and " + SQLwherePage + " and sessionduration in (select max(sessionduration) from usagelog q2 where q1.sessionid=q2.sessionid and " + SQLwherePage + ")", "");
			} else {
				SQL = SQLvisitors(logdb, database, SQLcolumnsExit, "usagelog", SQLwhere + " and " + SQLwherePage + " and (sessionid, sessionduration) in (select sessionid, max(sessionduration) from usagelog where " + SQLwherePage + " group by sessionid)", "");
			}
			HashMap<String,HashMap<String,String>> exitcountvisitors = logdb.query_records(SQL);
			usage.put("exitcountvisitors", new LinkedHashMap<String,String>());
			for (int i=0; i<exitcountvisitors.size(); i++) {
				String exitcountvisitor = "" + i;
				String myexit = "" + ((HashMap<String,String>)exitcountvisitors.get(exitcountvisitor)).get("requestclass") + ":::" + ((HashMap<String,String>)exitcountvisitors.get(exitcountvisitor)).get("requestdatabase") + ":::" + ((HashMap<String,String>)exitcountvisitors.get(exitcountvisitor)).get("requestid");
				((HashMap<String,String>)usage.get("exitcountvisitors")).put(myexit, "" + ((HashMap<String,String>)exitcountvisitors.get(exitcountvisitor)).get("countvisitors"));
			}

			if ((logdb.db_type(database).equals("access")) || (logdb.db_type(database).equals("mssql"))) {
				SQL = SQLvisits(logdb, database, SQLcolumnsExit, "usagelog q1", SQLwhere + " and " + SQLwherePage + " and sessionduration in (select max(sessionduration) from usagelog q2 where q1.sessionid=q2.sessionid and " + SQLwherePage + ")", "");
			} else {
				SQL = SQLvisits(logdb, database, SQLcolumnsExit, "usagelog", SQLwhere + " and " + SQLwherePage + " and (sessionid, sessionduration) in (select sessionid, max(sessionduration) from usagelog where " + SQLwherePage + " group by sessionid)", "");
			}
			HashMap<String,HashMap<String,String>> exitcountvisits = logdb.query_records(SQL);
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
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsPaths + SQLcolumnsVariantsSegments, SQLwherePage + " and refererid<>0", "", "paths", "countclienthosts+countvisitors+countvisits+countpageviews+countusersegments", "refererclass:::refererdatabase:::refererid:::requestclass:::requestdatabase:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsPaths + SQLcolumnsVariantsUsertests, SQLwherePage + " and refererid<>0", "", "paths", "countclienthosts+countvisitors+countvisits+countpageviews+countusertests", "refererclass:::refererdatabase:::refererid:::requestclass:::requestdatabase:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsPaths + SQLcolumnsVariants, SQLwherePage + " and refererid<>0", "", "paths", "countclienthosts+countvisitors+countvisits+countpageviews+countusersegments+countusertests", "refererclass:::refererdatabase:::refererid:::requestclass:::requestdatabase:::requestid", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsPaths, SQLwherePage + " and refererid<>0", "", "paths", "countclienthosts+countvisitors+countvisits+countpageviews", "refererclass:::refererdatabase:::refererid:::requestclass:::requestdatabase:::requestid", true, false);
		}
	}



	private String exitPagesMySQL(Session mysession, Request myrequest, Configuration config, DB db, DB logdb, String database, Date mynow, String minduration, String maxduration) {
		// MySQL v3.3-v4.0 does not support sub-selects
		// select exit page for each sessionid to be counted etc. manually
		String SQL = "select q1.requestid, q1.requestclass, q1.requestdatabase, q1.clienthost, q1.sessionid, q1.sessionduration as maxsessionduration";
		SQL = SQL + " from usagelog q1 left join usagelog q2 on q1.sessionid=q2.sessionid and q1.sessionduration <= q2.sessionduration";
		SQL = SQL + " where (q1.requestclass in ('page','product','data')) and (q2.requestclass in ('page','product','data')) and " + SQLwherePeriod(db, mysession, mynow, "q1.") + " and " + SQLwherePeriod(db, mysession, mynow, "q2.") + "";
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
		SQLwhere = SQLwhereNotRobots(db, SQLwhere);

		String SQL = "";
		if (logdb.db_type(database).equals("mysql")) {
			// MySQL v3.3-v4.0 does not support sub-selects
			// select exit page for each sessionid and count manually
			SQL = exitPagesMySQL(mysession, myrequest, myconfig, db, logdb, database, mynow, "", "");
			HashMap<String,HashMap<String,String>> exitpages = logdb.query_records(SQL);
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
		} else if (logdb.db_type(database).equals("oracle")) {
			// Oracle does not support naming of sub-selects
			SQL = "select avg(maxsessionduration) from (select max(sessionduration) as maxsessionduration from usagelog where " + SQLwhere + " group by sessionid)";
			usage.put("visitduration-average", "" + logdb.query_value(SQL));
		} else {
			// Microsoft SQL Server requires naming of sub-selects
			SQL = "select avg(maxsessionduration) from (select max(sessionduration) as maxsessionduration from usagelog where " + SQLwhere + " group by sessionid) as maxsessiondurations";
			usage.put("visitduration-average", "" + logdb.query_value(SQL));
		}

		usage.put("visitduration-0s-30s", "" + getDurationVisit(mysession, myrequest, myconfig, db, logdb, database, mynow, SQLwhere, "0", "30"));
		usage.put("visitduration-30s-2m", "" + getDurationVisit(mysession, myrequest, myconfig, db, logdb, database, mynow, SQLwhere, "30", "120"));
		usage.put("visitduration-2m-5m", "" + getDurationVisit(mysession, myrequest, myconfig, db, logdb, database, mynow, SQLwhere, "120", "300"));
		usage.put("visitduration-5m-15m", "" + getDurationVisit(mysession, myrequest, myconfig, db, logdb, database, mynow, SQLwhere, "300", "900"));
		usage.put("visitduration-15m-30m", "" + getDurationVisit(mysession, myrequest, myconfig, db, logdb, database, mynow, SQLwhere, "900", "1800"));
		usage.put("visitduration-30m-1h", "" + getDurationVisit(mysession, myrequest, myconfig, db, logdb, database, mynow, SQLwhere, "1800", "3600"));
		usage.put("visitduration-1h+", "" + getDurationVisit(mysession, myrequest, myconfig, db, logdb, database, mynow, SQLwhere, "3600", ""));

		SQL = "select avg(refererduration) from usagelog where " + logdb.is_not_blank("refererclass") + " and " + SQLwhere + " and " + SQLwherePage;
		usage.put("pageviewduration-average", "" + logdb.query_value(SQL));

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
		long duration;
		String SQL = "";
		if (logdb.db_type(database).equals("mysql")) {
			// MySQL v3.3-v4.0 does not support sub-selects
			// select exit page for each sessionid and count manually
			SQL = exitPagesMySQL(mysession, myrequest, myconfig, db, logdb, database, mynow, minduration, maxduration);
			HashMap<String,HashMap<String,String>> exitpages = logdb.query_records(SQL);
			duration = 0;
			for (int i=0; i<exitpages.size(); i++) {
				duration = duration + 1;
			}
		} else if (logdb.db_type(database).equals("oracle")) {
			// Oracle does not support naming of sub-selects
			if (! maxduration.equals("")) {
				SQL = "select count(*) from (select max(sessionduration) as maxsessionduration from usagelog where " + SQLwhere + " group by sessionid) where maxsessionduration >= " + minduration + " and maxsessionduration < " + maxduration;
			} else {
				SQL = "select count(*) from (select max(sessionduration) as maxsessionduration from usagelog where " + SQLwhere + " group by sessionid) where maxsessionduration >= " + minduration;
			}
			duration = logdb.query_value(SQL).longValue();
		} else {
			// Microsoft SQL Server requires naming of sub-selects
			if (! maxduration.equals("")) {
				SQL = "select count(*) from (select max(sessionduration) as maxsessionduration from usagelog where " + SQLwhere + " group by sessionid) as maxsessiondurations where maxsessionduration >= " + minduration + " and maxsessionduration < " + maxduration;
			} else {
				SQL = "select count(*) from (select max(sessionduration) as maxsessionduration from usagelog where " + SQLwhere + " group by sessionid) as maxsessiondurations where maxsessionduration >= " + minduration;
			}
			duration = logdb.query_value(SQL).longValue();
		}
		return new Long(duration);
	}



	private Long getDurationPageview(Session mysession, Request myrequest, Configuration myconfig, DB db, DB logdb, String database, String SQLwhere, String minduration, String maxduration) {
		long duration;
		String SQL = "";
		if (! maxduration.equals("")) {
			SQL = "select sum(hits) from usagelog where " + logdb.is_not_blank("refererclass") + " and " + SQLwhere + " and " + SQLwherePage + " and refererduration >= " + minduration + " and refererduration < " + maxduration;
		} else {
			SQL = "select sum(hits) from usagelog where " + logdb.is_not_blank("refererclass") + " and " + SQLwhere + " and " + SQLwherePage + " and refererduration >= " + minduration;
		}
		duration = logdb.query_value(SQL).longValue();
		return new Long(duration);
	}



	public HashMap<String,LinkedHashMap<String,String>> getContentIds(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database, String[] ids) {
		return getContentIds(mysession, myrequest, myresponse, myconfig, db, logdb, database, ids, "countclienthosts+countvisitors+countvisits+counthits+countusersegments+countusertests");
	}
	public HashMap<String,LinkedHashMap<String,String>> getContentIds(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database, String id, String reportdata) {
		String[] ids = new String[1];
		ids[0] = id;
		return getContentIds(mysession, myrequest, myresponse, myconfig, db, logdb, database, ids, reportdata);
	}
	public HashMap<String,LinkedHashMap<String,String>> getContentIds(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database, LinkedHashMap<String,String> ids, String reportdata) {
		return getContentIds(mysession, myrequest, myresponse, myconfig, db, logdb, database, ids.values().toArray(new String[0]), reportdata);
	}
	public HashMap<String,LinkedHashMap<String,String>> getContentIds(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database, String[] ids, String reportdata) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
//QQQQQ
		if (License.valid(db, myconfig, "experience")) {
			boolean countusersegments = (reportdata.indexOf("countusersegments") >= 0);
			boolean countusertests = (reportdata.indexOf("countusertests") >= 0);

			if (countusersegments && countusertests) {
				return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", "'total' as total" + SQLcolumnsVariants, "requestid in (" + Common.join(",", ids) + ")", "", "", reportdata, "total", true, false);
			} else if (countusersegments) {
				return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", "'total' as total" + SQLcolumnsVariantsSegments, "requestid in (" + Common.join(",", ids) + ")", "", "", reportdata, "total", true, false);
			} else if (countusertests) {
				return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", "'total' as total" + SQLcolumnsVariantsUsertests, "requestid in (" + Common.join(",", ids) + ")", "", "", reportdata, "total", true, false);
			} else {
				return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", "'total' as total", "requestid in (" + Common.join(",", ids) + ")", "", "", reportdata.replaceAll("[+]countusersegments", "").replaceAll("[+]countusertests", ""), "total", true, false);
			}
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", "'total' as total", "requestid in (" + Common.join(",", ids) + ")", "", "", reportdata.replaceAll("[+]countusersegments", "").replaceAll("[+]countusertests", ""), "total", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getContents(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsContents + SQLcolumnsVariantsSegments, SQLwhereContent, "", "contents", "countclienthosts+countvisitors+countvisits+counthits+countusersegments", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsContents + SQLcolumnsVariantsUsertests, SQLwhereContent, "", "contents", "countclienthosts+countvisitors+countvisits+counthits+countusertests", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsContents + SQLcolumnsVariants, SQLwhereContent, "", "contents", "countclienthosts+countvisitors+countvisits+counthits+countusersegments+countusertests", "requestclass:::requestid", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsContents, SQLwhereContent, "", "contents", "countclienthosts+countvisitors+countvisits+counthits", "requestclass:::requestid", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getPages(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		String SQLtable = "";
		String SQLwhere = "";
		if (myrequest.getParameter("contentgroup").equals("-")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("page") + " and " + db.is_blank("contentgroup") + ""));
			SQLtable = "usagelog";
			SQLwhere = "requestclass=" + logdb.quote("page") + " and requestid in (" + content_ids + ")";
		} else if ((! myrequest.getParameter("contentgroup").equals("")) && (! myrequest.getParameter("contentgroup").equals(" "))) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("page") + " and contentgroup=" + logdb.quote(myrequest.getParameter("contentgroup"))));
			SQLtable = "usagelog";
			SQLwhere = "requestclass=" + logdb.quote("page") + " and requestid in (" + content_ids + ")";
		} else if (myrequest.getParameter("contenttype").equals("-")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("page") + " and " + db.is_blank("contenttype") + ""));
			SQLtable = "usagelog";
			SQLwhere = "requestclass=" + logdb.quote("page") + " and requestid in (" + content_ids + ")";
		} else if ((! myrequest.getParameter("contenttype").equals("")) && (! myrequest.getParameter("contenttype").equals(" "))) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("page") + " and contenttype=" + logdb.quote(myrequest.getParameter("contenttype"))));
			SQLtable = "usagelog";
			SQLwhere = "requestclass=" + logdb.quote("page") + " and requestid in (" + content_ids + ")";
		} else {
			SQLtable = "usagelog";
			SQLwhere = "requestclass=" + logdb.quote("page");
		}
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsPages + SQLcolumnsVariantsSegments, SQLwhere, "", "pages", "countclienthosts+countvisitors+countvisits+countpageviews+countusersegments", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsPages + SQLcolumnsVariantsUsertests, SQLwhere, "", "pages", "countclienthosts+countvisitors+countvisits+countpageviews+countusertests", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsPages + SQLcolumnsVariants, SQLwhere, "", "pages", "countclienthosts+countvisitors+countvisits+countpageviews+countusersegments+countusertests", "requestclass:::requestid", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsPages, SQLwhere, "", "pages", "countclienthosts+countvisitors+countvisits+countpageviews", "requestclass:::requestid", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getPagegroups(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,LinkedHashMap<String,String>> usage = new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,HashMap<String,String>> rows = db.query_records("select contentgroup from contentgroups");
		rows.put("", new HashMap<String,String>());
		Iterator rowsIterator = rows.keySet().iterator();
		while (rowsIterator.hasNext()) {
			String key = "" + rowsIterator.next();
			HashMap<String,String> row = (HashMap<String,String>)rows.get(key);
			if (row.get("contentgroup") == null) row.put("contentgroup","");
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("page") + Common.SQLwhere_equals(db, " ", "contentgroup", "" + row.get("contentgroup"))));
			HashMap<String,LinkedHashMap<String,String>> output;
			if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("contentgroup")) + " as contentgroup" + SQLcolumnsVariantsSegments, "requestclass=" + logdb.quote("page") + " and requestid in (" + content_ids + ")", "", "pagegroups", "countclienthosts+countvisitors+countvisits+countpageviews+countusersegments", "contentgroup", true, false);
			} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("contentgroup")) + " as contentgroup" + SQLcolumnsVariantsUsertests, "requestclass=" + logdb.quote("page") + " and requestid in (" + content_ids + ")", "", "pagegroups", "countclienthosts+countvisitors+countvisits+countpageviews+countusertests", "contentgroup", true, false);
			} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("contentgroup")) + " as contentgroup" + SQLcolumnsVariants, "requestclass=" + logdb.quote("page") + " and requestid in (" + content_ids + ")", "", "pagegroups", "countclienthosts+countvisitors+countvisits+countpageviews+countusersegments+countusertests", "contentgroup", true, false);
			} else {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("contentgroup")) + " as contentgroup", "requestclass=" + logdb.quote("page") + " and requestid in (" + content_ids + ")", "", "pagegroups", "countclienthosts+countvisitors+countvisits+countpageviews", "contentgroup", true, false);
			}
			if (usage.get("pagegroupscountclienthosts") == null) { usage.put("pagegroupscountclienthosts", new LinkedHashMap<String,String>()); }
			if (usage.get("pagegroupscountvisitors") == null) { usage.put("pagegroupscountvisitors", new LinkedHashMap<String,String>()); }
			if (usage.get("pagegroupscountvisits") == null) { usage.put("pagegroupscountvisits", new LinkedHashMap<String,String>()); }
			if (usage.get("pagegroupscountpageviews") == null) { usage.put("pagegroupscountpageviews", new LinkedHashMap<String,String>()); }
			if (output.get("pagegroupscountclienthosts") != null) usage.put("pagegroupscountclienthosts", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountclienthosts"),(LinkedHashMap<String,String>)output.get("pagegroupscountclienthosts")));
			if (output.get("pagegroupscountvisitors") != null) usage.put("pagegroupscountvisitors", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountvisitors"),(LinkedHashMap<String,String>)output.get("pagegroupscountvisitors")));
			if (output.get("pagegroupscountvisits") != null) usage.put("pagegroupscountvisits", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountvisits"),(LinkedHashMap<String,String>)output.get("pagegroupscountvisits")));
			if (output.get("pagegroupscountpageviews") != null) usage.put("pagegroupscountpageviews", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountpageviews"),(LinkedHashMap<String,String>)output.get("pagegroupscountpageviews")));
			if (License.valid(db, myconfig, "experience")) {
				if (output.get("pagegroupscountclienthostssegment") != null) {
					if (usage.get("pagegroupscountclienthostssegment") == null) { usage.put("pagegroupscountclienthostssegment", new LinkedHashMap<String,String>()); }
					usage.put("pagegroupscountclienthostssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountclienthostssegment"),(LinkedHashMap<String,String>)output.get("pagegroupscountclienthostssegment")));
				}
				if (output.get("pagegroupscountvisitorssegment") != null) {
					if (usage.get("pagegroupscountvisitorssegment") == null) { usage.put("pagegroupscountvisitorssegment", new LinkedHashMap<String,String>()); }
					usage.put("pagegroupscountvisitorssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountvisitorssegment"),(LinkedHashMap<String,String>)output.get("pagegroupscountvisitorssegment")));
				}
				if (output.get("pagegroupscountvisitssegment") != null) {
					if (usage.get("pagegroupscountvisitssegment") == null) { usage.put("pagegroupscountvisitssegment", new LinkedHashMap<String,String>()); }
					usage.put("pagegroupscountvisitssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountvisitssegment"),(LinkedHashMap<String,String>)output.get("pagegroupscountvisitssegment")));
				}
				if (output.get("pagegroupscountpageviewssegment") != null) {
					if (usage.get("pagegroupscountpageviewssegment") == null) { usage.put("pagegroupscountpageviewssegment", new LinkedHashMap<String,String>()); }
					usage.put("pagegroupscountpageviewssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountpageviewssegment"),(LinkedHashMap<String,String>)output.get("pagegroupscountpageviewssegment")));
				}
				if (output.get("pagegroupscountclienthoststest") != null) {
					if (usage.get("pagegroupscountclienthoststest") == null) { usage.put("pagegroupscountclienthoststest", new LinkedHashMap<String,String>()); }
					usage.put("pagegroupscountclienthoststest", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountclienthoststest"),(LinkedHashMap<String,String>)output.get("pagegroupscountclienthoststest")));
				}
				if (output.get("pagegroupscountvisitorstest") != null) {
					if (usage.get("pagegroupscountvisitorstest") == null) { usage.put("pagegroupscountvisitorstest", new LinkedHashMap<String,String>()); }
					usage.put("pagegroupscountvisitorstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountvisitorstest"),(LinkedHashMap<String,String>)output.get("pagegroupscountvisitorstest")));
				}
				if (output.get("pagegroupscountvisitstest") != null) {
					if (usage.get("pagegroupscountvisitstest") == null) { usage.put("pagegroupscountvisitstest", new LinkedHashMap<String,String>()); }
					usage.put("pagegroupscountvisitstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountvisitstest"),(LinkedHashMap<String,String>)output.get("pagegroupscountvisitstest")));
				}
				if (output.get("pagegroupscountpageviewstest") != null) {
					if (usage.get("pagegroupscountpageviewstest") == null) { usage.put("pagegroupscountpageviewstest", new LinkedHashMap<String,String>()); }
					usage.put("pagegroupscountpageviewstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountpageviewstest"),(LinkedHashMap<String,String>)output.get("pagegroupscountpageviewstest")));
				}
				if (output.get("pagegroupscountclienthostssegments") != null) {
					if (usage.get("pagegroupscountclienthostssegments") == null) { usage.put("pagegroupscountclienthostssegments", new LinkedHashMap<String,String>()); }
					usage.put("pagegroupscountclienthostssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountclienthostssegments"),(LinkedHashMap<String,String>)output.get("pagegroupscountclienthostssegments")));
				}
				if (output.get("pagegroupscountvisitorssegments") != null) {
					if (usage.get("pagegroupscountvisitorssegments") == null) { usage.put("pagegroupscountvisitorssegments", new LinkedHashMap<String,String>()); }
					usage.put("pagegroupscountvisitorssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountvisitorssegments"),(LinkedHashMap<String,String>)output.get("pagegroupscountvisitorssegments")));
				}
				if (output.get("pagegroupscountvisitssegments") != null) {
					if (usage.get("pagegroupscountvisitssegments") == null) { usage.put("pagegroupscountvisitssegments", new LinkedHashMap<String,String>()); }
					usage.put("pagegroupscountvisitssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountvisitssegments"),(LinkedHashMap<String,String>)output.get("pagegroupscountvisitssegments")));
				}
				if (output.get("pagegroupscountpageviewssegments") != null) {
					if (usage.get("pagegroupscountpageviewssegments") == null) { usage.put("pagegroupscountpageviewssegments", new LinkedHashMap<String,String>()); }
					usage.put("pagegroupscountpageviewssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountpageviewssegments"),(LinkedHashMap<String,String>)output.get("pagegroupscountpageviewssegments")));
				}
				if (output.get("pagegroupscountclienthoststests") != null) {
					if (usage.get("pagegroupscountclienthoststests") == null) { usage.put("pagegroupscountclienthoststests", new LinkedHashMap<String,String>()); }
					usage.put("pagegroupscountclienthoststests", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountclienthoststests"),(LinkedHashMap<String,String>)output.get("pagegroupscountclienthoststests")));
				}
				if (output.get("pagegroupscountvisitorstests") != null) {
					if (usage.get("pagegroupscountvisitorstests") == null) { usage.put("pagegroupscountvisitorstests", new LinkedHashMap<String,String>()); }
					usage.put("pagegroupscountvisitorstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountvisitorstests"),(LinkedHashMap<String,String>)output.get("pagegroupscountvisitorstests")));
				}
				if (output.get("pagegroupscountvisitstests") != null) {
					if (usage.get("pagegroupscountvisitstests") == null) { usage.put("pagegroupscountvisitstests", new LinkedHashMap<String,String>()); }
					usage.put("pagegroupscountvisitstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountvisitstests"),(LinkedHashMap<String,String>)output.get("pagegroupscountvisitstests")));
				}
				if (output.get("pagegroupscountpageviewstests") != null) {
					if (usage.get("pagegroupscountpageviewstests") == null) { usage.put("pagegroupscountpageviewstests", new LinkedHashMap<String,String>()); }
					usage.put("pagegroupscountpageviewstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountpageviewstests"),(LinkedHashMap<String,String>)output.get("pagegroupscountpageviewstests")));
				}
				if (output.get("pagegroupscountclienthostssegmentstests") != null) {
					if (usage.get("pagegroupscountclienthostssegmentstests") == null) { usage.put("pagegroupscountclienthostssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("pagegroupscountclienthostssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountclienthostssegmentstests"),(LinkedHashMap<String,String>)output.get("pagegroupscountclienthostssegmentstests")));
				}
				if (output.get("pagegroupscountvisitorssegmentstests") != null) {
					if (usage.get("pagegroupscountvisitorssegmentstests") == null) { usage.put("pagegroupscountvisitorssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("pagegroupscountvisitorssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountvisitorssegmentstests"),(LinkedHashMap<String,String>)output.get("pagegroupscountvisitorssegmentstests")));
				}
				if (output.get("pagegroupscountvisitssegmentstests") != null) {
					if (usage.get("pagegroupscountvisitssegmentstests") == null) { usage.put("pagegroupscountvisitssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("pagegroupscountvisitssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountvisitssegmentstests"),(LinkedHashMap<String,String>)output.get("pagegroupscountvisitssegmentstests")));
				}
				if (output.get("pagegroupscountpageviewssegmentstests") != null) {
					if (usage.get("pagegroupscountpageviewssegmentstests") == null) { usage.put("pagegroupscountpageviewssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("pagegroupscountpageviewssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagegroupscountpageviewssegmentstests"),(LinkedHashMap<String,String>)output.get("pagegroupscountpageviewssegmentstests")));
				}
			}
		}
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getPagetypes(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,LinkedHashMap<String,String>> usage = new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,HashMap<String,String>> rows = db.query_records("select contenttype from contenttypes");
		rows.put("", new HashMap<String,String>());
		Iterator rowsIterator = rows.keySet().iterator();
		while (rowsIterator.hasNext()) {
			String key = "" + rowsIterator.next();
			HashMap<String,String> row = (HashMap<String,String>)rows.get(key);
			if (row.get("contenttype") == null) row.put("contenttype","");
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + logdb.quote("page") + Common.SQLwhere_equals(db, " ", "contenttype", "" + row.get("contenttype"))));
			HashMap<String,LinkedHashMap<String,String>> output;
			if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("contenttype")) + " as contenttype" + SQLcolumnsVariantsSegments, "requestclass=" + logdb.quote("page") + " and requestid in (" + content_ids + ")", "", "pagetypes", "countclienthosts+countvisitors+countvisits+countpageviews+countusersegments", "contenttype", true, false);
			} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("contenttype")) + " as contenttype" + SQLcolumnsVariantsUsertests, "requestclass=" + logdb.quote("page") + " and requestid in (" + content_ids + ")", "", "pagetypes", "countclienthosts+countvisitors+countvisits+countpageviews+countusertests", "contenttype", true, false);
			} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("contenttype")) + " as contenttype" + SQLcolumnsVariants, "requestclass=" + logdb.quote("page") + " and requestid in (" + content_ids + ")", "", "pagetypes", "countclienthosts+countvisitors+countvisits+countpageviews+countusersegments+countusertests", "contenttype", true, false);
			} else {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("contenttype")) + " as contenttype", "requestclass=" + logdb.quote("page") + " and requestid in (" + content_ids + ")", "", "pagetypes", "countclienthosts+countvisitors+countvisits+countpageviews", "contenttype", true, false);
			}
			if (usage.get("pagetypescountclienthosts") == null) { usage.put("pagetypescountclienthosts", new LinkedHashMap<String,String>()); }
			if (usage.get("pagetypescountvisitors") == null) { usage.put("pagetypescountvisitors", new LinkedHashMap<String,String>()); }
			if (usage.get("pagetypescountvisits") == null) { usage.put("pagetypescountvisits", new LinkedHashMap<String,String>()); }
			if (usage.get("pagetypescountpageviews") == null) { usage.put("pagetypescountpageviews", new LinkedHashMap<String,String>()); }
			if (output.get("pagetypescountclienthosts") != null) usage.put("pagetypescountclienthosts", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountclienthosts"),(LinkedHashMap<String,String>)output.get("pagetypescountclienthosts")));
			if (output.get("pagetypescountvisitors") != null) usage.put("pagetypescountvisitors", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountvisitors"),(LinkedHashMap<String,String>)output.get("pagetypescountvisitors")));
			if (output.get("pagetypescountvisits") != null) usage.put("pagetypescountvisits", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountvisits"),(LinkedHashMap<String,String>)output.get("pagetypescountvisits")));
			if (output.get("pagetypescountpageviews") != null) usage.put("pagetypescountpageviews", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountpageviews"),(LinkedHashMap<String,String>)output.get("pagetypescountpageviews")));
			if (License.valid(db, myconfig, "experience")) {
				if (output.get("pagetypescountclienthostssegment") != null) {
					if (usage.get("pagetypescountclienthostssegment") == null) { usage.put("pagetypescountclienthostssegment", new LinkedHashMap<String,String>()); }
					usage.put("pagetypescountclienthostssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountclienthostssegment"),(LinkedHashMap<String,String>)output.get("pagetypescountclienthostssegment")));
				}
				if (output.get("pagetypescountvisitorssegment") != null) {
					if (usage.get("pagetypescountvisitorssegment") == null) { usage.put("pagetypescountvisitorssegment", new LinkedHashMap<String,String>()); }
					usage.put("pagetypescountvisitorssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountvisitorssegment"),(LinkedHashMap<String,String>)output.get("pagetypescountvisitorssegment")));
				}
				if (output.get("pagetypescountvisitssegment") != null) {
					if (usage.get("pagetypescountvisitssegment") == null) { usage.put("pagetypescountvisitssegment", new LinkedHashMap<String,String>()); }
					usage.put("pagetypescountvisitssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountvisitssegment"),(LinkedHashMap<String,String>)output.get("pagetypescountvisitssegment")));
				}
				if (output.get("pagetypescountpageviewssegment") != null) {
					if (usage.get("pagetypescountpageviewssegment") == null) { usage.put("pagetypescountpageviewssegment", new LinkedHashMap<String,String>()); }
					usage.put("pagetypescountpageviewssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountpageviewssegment"),(LinkedHashMap<String,String>)output.get("pagetypescountpageviewssegment")));
				}
				if (output.get("pagetypescountclienthoststest") != null) {
					if (usage.get("pagetypescountclienthoststest") == null) { usage.put("pagetypescountclienthoststest", new LinkedHashMap<String,String>()); }
					usage.put("pagetypescountclienthoststest", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountclienthoststest"),(LinkedHashMap<String,String>)output.get("pagetypescountclienthoststest")));
				}
				if (output.get("pagetypescountvisitorstest") != null) {
					if (usage.get("pagetypescountvisitorstest") == null) { usage.put("pagetypescountvisitorstest", new LinkedHashMap<String,String>()); }
					usage.put("pagetypescountvisitorstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountvisitorstest"),(LinkedHashMap<String,String>)output.get("pagetypescountvisitorstest")));
				}
				if (output.get("pagetypescountvisitstest") != null) {
					if (usage.get("pagetypescountvisitstest") == null) { usage.put("pagetypescountvisitstest", new LinkedHashMap<String,String>()); }
					usage.put("pagetypescountvisitstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountvisitstest"),(LinkedHashMap<String,String>)output.get("pagetypescountvisitstest")));
				}
				if (output.get("pagetypescountpageviewstest") != null) {
					if (usage.get("pagetypescountpageviewstest") == null) { usage.put("pagetypescountpageviewstest", new LinkedHashMap<String,String>()); }
					usage.put("pagetypescountpageviewstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountpageviewstest"),(LinkedHashMap<String,String>)output.get("pagetypescountpageviewstest")));
				}
				if (output.get("pagetypescountclienthostssegments") != null) {
					if (usage.get("pagetypescountclienthostssegments") == null) { usage.put("pagetypescountclienthostssegments", new LinkedHashMap<String,String>()); }
					usage.put("pagetypescountclienthostssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountclienthostssegments"),(LinkedHashMap<String,String>)output.get("pagetypescountclienthostssegments")));
				}
				if (output.get("pagetypescountvisitorssegments") != null) {
					if (usage.get("pagetypescountvisitorssegments") == null) { usage.put("pagetypescountvisitorssegments", new LinkedHashMap<String,String>()); }
					usage.put("pagetypescountvisitorssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountvisitorssegments"),(LinkedHashMap<String,String>)output.get("pagetypescountvisitorssegments")));
				}
				if (output.get("pagetypescountvisitssegments") != null) {
					if (usage.get("pagetypescountvisitssegments") == null) { usage.put("pagetypescountvisitssegments", new LinkedHashMap<String,String>()); }
					usage.put("pagetypescountvisitssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountvisitssegments"),(LinkedHashMap<String,String>)output.get("pagetypescountvisitssegments")));
				}
				if (output.get("pagetypescountpageviewssegments") != null) {
					if (usage.get("pagetypescountpageviewssegments") == null) { usage.put("pagetypescountpageviewssegments", new LinkedHashMap<String,String>()); }
					usage.put("pagetypescountpageviewssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountpageviewssegments"),(LinkedHashMap<String,String>)output.get("pagetypescountpageviewssegments")));
				}
				if (output.get("pagetypescountclienthoststests") != null) {
					if (usage.get("pagetypescountclienthoststests") == null) { usage.put("pagetypescountclienthoststests", new LinkedHashMap<String,String>()); }
					usage.put("pagetypescountclienthoststests", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountclienthoststests"),(LinkedHashMap<String,String>)output.get("pagetypescountclienthoststests")));
				}
				if (output.get("pagetypescountvisitorstests") != null) {
					if (usage.get("pagetypescountvisitorstests") == null) { usage.put("pagetypescountvisitorstests", new LinkedHashMap<String,String>()); }
					usage.put("pagetypescountvisitorstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountvisitorstests"),(LinkedHashMap<String,String>)output.get("pagetypescountvisitorstests")));
				}
				if (output.get("pagetypescountvisitstests") != null) {
					if (usage.get("pagetypescountvisitstests") == null) { usage.put("pagetypescountvisitstests", new LinkedHashMap<String,String>()); }
					usage.put("pagetypescountvisitstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountvisitstests"),(LinkedHashMap<String,String>)output.get("pagetypescountvisitstests")));
				}
				if (output.get("pagetypescountpageviewstests") != null) {
					if (usage.get("pagetypescountpageviewstests") == null) { usage.put("pagetypescountpageviewstests", new LinkedHashMap<String,String>()); }
					usage.put("pagetypescountpageviewstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountpageviewstests"),(LinkedHashMap<String,String>)output.get("pagetypescountpageviewstests")));
				}
				if (output.get("pagetypescountclienthostssegmentstests") != null) {
					if (usage.get("pagetypescountclienthostssegmentstests") == null) { usage.put("pagetypescountclienthostssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("pagetypescountclienthostssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountclienthostssegmentstests"),(LinkedHashMap<String,String>)output.get("pagetypescountclienthostssegmentstests")));
				}
				if (output.get("pagetypescountvisitorssegmentstests") != null) {
					if (usage.get("pagetypescountvisitorssegmentstests") == null) { usage.put("pagetypescountvisitorssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("pagetypescountvisitorssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountvisitorssegmentstests"),(LinkedHashMap<String,String>)output.get("pagetypescountvisitorssegmentstests")));
				}
				if (output.get("pagetypescountvisitssegmentstests") != null) {
					if (usage.get("pagetypescountvisitssegmentstests") == null) { usage.put("pagetypescountvisitssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("pagetypescountvisitssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountvisitssegmentstests"),(LinkedHashMap<String,String>)output.get("pagetypescountvisitssegmentstests")));
				}
				if (output.get("pagetypescountpageviewssegmentstests") != null) {
					if (usage.get("pagetypescountpageviewssegmentstests") == null) { usage.put("pagetypescountpageviewssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("pagetypescountpageviewssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("pagetypescountpageviewssegmentstests"),(LinkedHashMap<String,String>)output.get("pagetypescountpageviewssegmentstests")));
				}
			}
		}
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getProducts(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		String SQLtable = "";
		String SQLwhere = "";
		if (myrequest.getParameter("contentgroup").equals("-")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("product") + " and " + db.is_blank("contentgroup") + ""));
			SQLtable = "usagelog";
			SQLwhere = "requestclass=" + logdb.quote("product") + " and requestid in (" + content_ids + ")";
		} else if ((! myrequest.getParameter("contentgroup").equals("")) && (! myrequest.getParameter("contentgroup").equals(" "))) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("product") + " and contentgroup=" + db.quote(myrequest.getParameter("contentgroup"))));
			SQLtable = "usagelog";
			SQLwhere = "requestclass=" + logdb.quote("product") + " and requestid in (" + content_ids + ")";
		} else if (myrequest.getParameter("contenttype").equals("-")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("product") + " and " + db.is_blank("contenttype") + ""));
			SQLtable = "usagelog";
			SQLwhere = "requestclass=" + logdb.quote("product") + " and requestid in (" + content_ids + ")";
		} else if ((! myrequest.getParameter("contenttype").equals("")) && (! myrequest.getParameter("contenttype").equals(" "))) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("product") + " and contenttype=" + db.quote(myrequest.getParameter("contenttype"))));
			SQLtable = "usagelog";
			SQLwhere = "requestclass=" + logdb.quote("product") + " and requestid in (" + content_ids + ")";
		} else {
			SQLtable = "usagelog";
			SQLwhere = "requestclass=" + logdb.quote("product");
		}
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsProducts + SQLcolumnsVariantsSegments, SQLwhere, "", "products", "countclienthosts+countvisitors+countvisits+countpageviews+countusersegments", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsProducts + SQLcolumnsVariantsUsertests, SQLwhere, "", "products", "countclienthosts+countvisitors+countvisits+countpageviews+countusertests", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsProducts + SQLcolumnsVariants, SQLwhere, "", "products", "countclienthosts+countvisitors+countvisits+countpageviews+countusersegments+countusertests", "requestclass:::requestid", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsProducts, SQLwhere, "", "products", "countclienthosts+countvisitors+countvisits+countpageviews", "requestclass:::requestid", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getProductgroups(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,LinkedHashMap<String,String>> usage = new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,HashMap<String,String>> rows = db.query_records("select productgroup from productgroups");
		rows.put("", new HashMap<String,String>());
		Iterator rowsIterator = rows.keySet().iterator();
		while (rowsIterator.hasNext()) {
			String key = "" + rowsIterator.next();
			HashMap<String,String> row = (HashMap<String,String>)rows.get(key);
			if (row.get("productgroup") == null) row.put("productgroup","");
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("product") + Common.SQLwhere_equals(db, " ", "contentgroup", "" + row.get("productgroup"))));
			HashMap<String,LinkedHashMap<String,String>> output;
			if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("productgroup")) + " as contentgroup" + SQLcolumnsVariantsSegments, "requestclass=" + logdb.quote("product") + " and requestid in (" + content_ids + ")", "", "productgroups", "countclienthosts+countvisitors+countvisits+countpageviews+countusersegments", "contentgroup", true, false);
			} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("productgroup")) + " as contentgroup" + SQLcolumnsVariantsUsertests, "requestclass=" + logdb.quote("product") + " and requestid in (" + content_ids + ")", "", "productgroups", "countclienthosts+countvisitors+countvisits+countpageviews+countusertests", "contentgroup", true, false);
			} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("productgroup")) + " as contentgroup" + SQLcolumnsVariants, "requestclass=" + logdb.quote("product") + " and requestid in (" + content_ids + ")", "", "productgroups", "countclienthosts+countvisitors+countvisits+countpageviews+countusersegments+countusertests", "contentgroup", true, false);
			} else {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("productgroup")) + " as contentgroup", "requestclass=" + logdb.quote("product") + " and requestid in (" + content_ids + ")", "", "productgroups", "countclienthosts+countvisitors+countvisits+countpageviews", "contentgroup", true, false);
			}
			if (usage.get("productgroupscountclienthosts") == null) { usage.put("productgroupscountclienthosts", new LinkedHashMap<String,String>()); }
			if (usage.get("productgroupscountvisitors") == null) { usage.put("productgroupscountvisitors", new LinkedHashMap<String,String>()); }
			if (usage.get("productgroupscountvisits") == null) { usage.put("productgroupscountvisits", new LinkedHashMap<String,String>()); }
			if (usage.get("productgroupscountpageviews") == null) { usage.put("productgroupscountpageviews", new LinkedHashMap<String,String>()); }
			if (output.get("productgroupscountclienthosts") != null) usage.put("productgroupscountclienthosts", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountclienthosts"),(LinkedHashMap<String,String>)output.get("productgroupscountclienthosts")));
			if (output.get("productgroupscountvisitors") != null) usage.put("productgroupscountvisitors", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountvisitors"),(LinkedHashMap<String,String>)output.get("productgroupscountvisitors")));
			if (output.get("productgroupscountvisits") != null) usage.put("productgroupscountvisits", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountvisits"),(LinkedHashMap<String,String>)output.get("productgroupscountvisits")));
			if (output.get("productgroupscountpageviews") != null) usage.put("productgroupscountpageviews", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountpageviews"),(LinkedHashMap<String,String>)output.get("productgroupscountpageviews")));
			if (License.valid(db, myconfig, "experience")) {
				if (output.get("productgroupscountclienthostssegment") != null) {
					if (usage.get("productgroupscountclienthostssegment") == null) { usage.put("productgroupscountclienthostssegment", new LinkedHashMap<String,String>()); }
					usage.put("productgroupscountclienthostssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountclienthostssegment"),(LinkedHashMap<String,String>)output.get("productgroupscountclienthostssegment")));
				}
				if (output.get("productgroupscountvisitorssegment") != null) {
					if (usage.get("productgroupscountvisitorssegment") == null) { usage.put("productgroupscountvisitorssegment", new LinkedHashMap<String,String>()); }
					usage.put("productgroupscountvisitorssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountvisitorssegment"),(LinkedHashMap<String,String>)output.get("productgroupscountvisitorssegment")));
				}
				if (output.get("productgroupscountvisitssegment") != null) {
					if (usage.get("productgroupscountvisitssegment") == null) { usage.put("productgroupscountvisitssegment", new LinkedHashMap<String,String>()); }
					usage.put("productgroupscountvisitssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountvisitssegment"),(LinkedHashMap<String,String>)output.get("productgroupscountvisitssegment")));
				}
				if (output.get("productgroupscountpageviewssegment") != null) {
					if (usage.get("productgroupscountpageviewssegment") == null) { usage.put("productgroupscountpageviewssegment", new LinkedHashMap<String,String>()); }
					usage.put("productgroupscountpageviewssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountpageviewssegment"),(LinkedHashMap<String,String>)output.get("productgroupscountpageviewssegment")));
				}
				if (output.get("productgroupscountclienthoststest") != null) {
					if (usage.get("productgroupscountclienthoststest") == null) { usage.put("productgroupscountclienthoststest", new LinkedHashMap<String,String>()); }
					usage.put("productgroupscountclienthoststest", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountclienthoststest"),(LinkedHashMap<String,String>)output.get("productgroupscountclienthoststest")));
				}
				if (output.get("productgroupscountvisitorstest") != null) {
					if (usage.get("productgroupscountvisitorstest") == null) { usage.put("productgroupscountvisitorstest", new LinkedHashMap<String,String>()); }
					usage.put("productgroupscountvisitorstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountvisitorstest"),(LinkedHashMap<String,String>)output.get("productgroupscountvisitorstest")));
				}
				if (output.get("productgroupscountvisitstest") != null) {
					if (usage.get("productgroupscountvisitstest") == null) { usage.put("productgroupscountvisitstest", new LinkedHashMap<String,String>()); }
					usage.put("productgroupscountvisitstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountvisitstest"),(LinkedHashMap<String,String>)output.get("productgroupscountvisitstest")));
				}
				if (output.get("productgroupscountpageviewstest") != null) {
					if (usage.get("productgroupscountpageviewstest") == null) { usage.put("productgroupscountpageviewstest", new LinkedHashMap<String,String>()); }
					usage.put("productgroupscountpageviewstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountpageviewstest"),(LinkedHashMap<String,String>)output.get("productgroupscountpageviewstest")));
				}
				if (output.get("productgroupscountclienthostssegments") != null) {
					if (usage.get("productgroupscountclienthostssegments") == null) { usage.put("productgroupscountclienthostssegments", new LinkedHashMap<String,String>()); }
					usage.put("productgroupscountclienthostssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountclienthostssegments"),(LinkedHashMap<String,String>)output.get("productgroupscountclienthostssegments")));
				}
				if (output.get("productgroupscountvisitorssegments") != null) {
					if (usage.get("productgroupscountvisitorssegments") == null) { usage.put("productgroupscountvisitorssegments", new LinkedHashMap<String,String>()); }
					usage.put("productgroupscountvisitorssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountvisitorssegments"),(LinkedHashMap<String,String>)output.get("productgroupscountvisitorssegments")));
				}
				if (output.get("productgroupscountvisitssegments") != null) {
					if (usage.get("productgroupscountvisitssegments") == null) { usage.put("productgroupscountvisitssegments", new LinkedHashMap<String,String>()); }
					usage.put("productgroupscountvisitssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountvisitssegments"),(LinkedHashMap<String,String>)output.get("productgroupscountvisitssegments")));
				}
				if (output.get("productgroupscountpageviewssegments") != null) {
					if (usage.get("productgroupscountpageviewssegments") == null) { usage.put("productgroupscountpageviewssegments", new LinkedHashMap<String,String>()); }
					usage.put("productgroupscountpageviewssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountpageviewssegments"),(LinkedHashMap<String,String>)output.get("productgroupscountpageviewssegments")));
				}
				if (output.get("productgroupscountclienthoststests") != null) {
					if (usage.get("productgroupscountclienthoststests") == null) { usage.put("productgroupscountclienthoststests", new LinkedHashMap<String,String>()); }
					usage.put("productgroupscountclienthoststests", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountclienthoststests"),(LinkedHashMap<String,String>)output.get("productgroupscountclienthoststests")));
				}
				if (output.get("productgroupscountvisitorstests") != null) {
					if (usage.get("productgroupscountvisitorstests") == null) { usage.put("productgroupscountvisitorstests", new LinkedHashMap<String,String>()); }
					usage.put("productgroupscountvisitorstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountvisitorstests"),(LinkedHashMap<String,String>)output.get("productgroupscountvisitorstests")));
				}
				if (output.get("productgroupscountvisitstests") != null) {
					if (usage.get("productgroupscountvisitstests") == null) { usage.put("productgroupscountvisitstests", new LinkedHashMap<String,String>()); }
					usage.put("productgroupscountvisitstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountvisitstests"),(LinkedHashMap<String,String>)output.get("productgroupscountvisitstests")));
				}
				if (output.get("productgroupscountpageviewstests") != null) {
					if (usage.get("productgroupscountpageviewstests") == null) { usage.put("productgroupscountpageviewstests", new LinkedHashMap<String,String>()); }
					usage.put("productgroupscountpageviewstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountpageviewstests"),(LinkedHashMap<String,String>)output.get("productgroupscountpageviewstests")));
				}
				if (output.get("productgroupscountclienthostssegmentstests") != null) {
					if (usage.get("productgroupscountclienthostssegmentstests") == null) { usage.put("productgroupscountclienthostssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("productgroupscountclienthostssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountclienthostssegmentstests"),(LinkedHashMap<String,String>)output.get("productgroupscountclienthostssegmentstests")));
				}
				if (output.get("productgroupscountvisitorssegmentstests") != null) {
					if (usage.get("productgroupscountvisitorssegmentstests") == null) { usage.put("productgroupscountvisitorssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("productgroupscountvisitorssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountvisitorssegmentstests"),(LinkedHashMap<String,String>)output.get("productgroupscountvisitorssegmentstests")));
				}
				if (output.get("productgroupscountvisitssegmentstests") != null) {
					if (usage.get("productgroupscountvisitssegmentstests") == null) { usage.put("productgroupscountvisitssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("productgroupscountvisitssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountvisitssegmentstests"),(LinkedHashMap<String,String>)output.get("productgroupscountvisitssegmentstests")));
				}
				if (output.get("productgroupscountpageviewssegmentstests") != null) {
					if (usage.get("productgroupscountpageviewssegmentstests") == null) { usage.put("productgroupscountpageviewssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("productgroupscountpageviewssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("productgroupscountpageviewssegmentstests"),(LinkedHashMap<String,String>)output.get("productgroupscountpageviewssegmentstests")));
				}
			}
		}
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getProducttypes(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,LinkedHashMap<String,String>> usage = new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,HashMap<String,String>> rows = db.query_records("select producttype from producttypes");
		rows.put("", new HashMap<String,String>());
		Iterator rowsIterator = rows.keySet().iterator();
		while (rowsIterator.hasNext()) {
			String key = "" + rowsIterator.next();
			HashMap<String,String> row = (HashMap<String,String>)rows.get(key);
			if (row.get("producttype") == null) row.put("producttype","");
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("product") + Common.SQLwhere_equals(db, " ", "contenttype", "" + row.get("producttype"))));
			HashMap<String,LinkedHashMap<String,String>> output;
			if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("producttype")) + " as contenttype" + SQLcolumnsVariantsSegments, "requestclass=" + logdb.quote("product") + " and requestid in (" + content_ids + ")", "", "producttypes", "countclienthosts+countvisitors+countvisits+countpageviews+countusersegments", "contenttype", true, false);
			} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("producttype")) + " as contenttype" + SQLcolumnsVariantsUsertests, "requestclass=" + logdb.quote("product") + " and requestid in (" + content_ids + ")", "", "producttypes", "countclienthosts+countvisitors+countvisits+countpageviews+countusertests", "contenttype", true, false);
			} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("producttype")) + " as contenttype" + SQLcolumnsVariants, "requestclass=" + logdb.quote("product") + " and requestid in (" + content_ids + ")", "", "producttypes", "countclienthosts+countvisitors+countvisits+countpageviews+countusersegments+countusertests", "contenttype", true, false);
			} else {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("producttype")) + " as contenttype", "requestclass=" + logdb.quote("product") + " and requestid in (" + content_ids + ")", "", "producttypes", "countclienthosts+countvisitors+countvisits+countpageviews", "contenttype", true, false);
			}
			if (usage.get("producttypescountclienthosts") == null) { usage.put("producttypescountclienthosts", new LinkedHashMap<String,String>()); }
			if (usage.get("producttypescountvisitors") == null) { usage.put("producttypescountvisitors", new LinkedHashMap<String,String>()); }
			if (usage.get("producttypescountvisits") == null) { usage.put("producttypescountvisits", new LinkedHashMap<String,String>()); }
			if (usage.get("producttypescountpageviews") == null) { usage.put("producttypescountpageviews", new LinkedHashMap<String,String>()); }
			if (output.get("producttypescountclienthosts") != null) usage.put("producttypescountclienthosts", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountclienthosts"),(LinkedHashMap<String,String>)output.get("producttypescountclienthosts")));
			if (output.get("producttypescountvisitors") != null) usage.put("producttypescountvisitors", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountvisitors"),(LinkedHashMap<String,String>)output.get("producttypescountvisitors")));
			if (output.get("producttypescountvisits") != null) usage.put("producttypescountvisits", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountvisits"),(LinkedHashMap<String,String>)output.get("producttypescountvisits")));
			if (output.get("producttypescountpageviews") != null) usage.put("producttypescountpageviews", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountpageviews"),(LinkedHashMap<String,String>)output.get("producttypescountpageviews")));
			if (License.valid(db, myconfig, "experience")) {
				if (output.get("producttypescountclienthostssegment") != null) {
					if (usage.get("producttypescountclienthostssegment") == null) { usage.put("producttypescountclienthostssegment", new LinkedHashMap<String,String>()); }
					usage.put("producttypescountclienthostssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountclienthostssegment"),(LinkedHashMap<String,String>)output.get("producttypescountclienthostssegment")));
				}
				if (output.get("producttypescountvisitorssegment") != null) {
					if (usage.get("producttypescountvisitorssegment") == null) { usage.put("producttypescountvisitorssegment", new LinkedHashMap<String,String>()); }
					usage.put("producttypescountvisitorssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountvisitorssegment"),(LinkedHashMap<String,String>)output.get("producttypescountvisitorssegment")));
				}
				if (output.get("producttypescountvisitssegment") != null) {
					if (usage.get("producttypescountvisitssegment") == null) { usage.put("producttypescountvisitssegment", new LinkedHashMap<String,String>()); }
					usage.put("producttypescountvisitssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountvisitssegment"),(LinkedHashMap<String,String>)output.get("producttypescountvisitssegment")));
				}
				if (output.get("producttypescountpageviewssegment") != null) {
					if (usage.get("producttypescountpageviewssegment") == null) { usage.put("producttypescountpageviewssegment", new LinkedHashMap<String,String>()); }
					usage.put("producttypescountpageviewssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountpageviewssegment"),(LinkedHashMap<String,String>)output.get("producttypescountpageviewssegment")));
				}
				if (output.get("producttypescountclienthoststest") != null) {
					if (usage.get("producttypescountclienthoststest") == null) { usage.put("producttypescountclienthoststest", new LinkedHashMap<String,String>()); }
					usage.put("producttypescountclienthoststest", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountclienthoststest"),(LinkedHashMap<String,String>)output.get("producttypescountclienthoststest")));
				}
				if (output.get("producttypescountvisitorstest") != null) {
					if (usage.get("producttypescountvisitorstest") == null) { usage.put("producttypescountvisitorstest", new LinkedHashMap<String,String>()); }
					usage.put("producttypescountvisitorstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountvisitorstest"),(LinkedHashMap<String,String>)output.get("producttypescountvisitorstest")));
				}
				if (output.get("producttypescountvisitstest") != null) {
					if (usage.get("producttypescountvisitstest") == null) { usage.put("producttypescountvisitstest", new LinkedHashMap<String,String>()); }
					usage.put("producttypescountvisitstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountvisitstest"),(LinkedHashMap<String,String>)output.get("producttypescountvisitstest")));
				}
				if (output.get("producttypescountpageviewstest") != null) {
					if (usage.get("producttypescountpageviewstest") == null) { usage.put("producttypescountpageviewstest", new LinkedHashMap<String,String>()); }
					usage.put("producttypescountpageviewstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountpageviewstest"),(LinkedHashMap<String,String>)output.get("producttypescountpageviewstest")));
				}
				if (output.get("producttypescountclienthostssegments") != null) {
					if (usage.get("producttypescountclienthostssegments") == null) { usage.put("producttypescountclienthostssegments", new LinkedHashMap<String,String>()); }
					usage.put("producttypescountclienthostssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountclienthostssegments"),(LinkedHashMap<String,String>)output.get("producttypescountclienthostssegments")));
				}
				if (output.get("producttypescountvisitorssegments") != null) {
					if (usage.get("producttypescountvisitorssegments") == null) { usage.put("producttypescountvisitorssegments", new LinkedHashMap<String,String>()); }
					usage.put("producttypescountvisitorssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountvisitorssegments"),(LinkedHashMap<String,String>)output.get("producttypescountvisitorssegments")));
				}
				if (output.get("producttypescountvisitssegments") != null) {
					if (usage.get("producttypescountvisitssegments") == null) { usage.put("producttypescountvisitssegments", new LinkedHashMap<String,String>()); }
					usage.put("producttypescountvisitssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountvisitssegments"),(LinkedHashMap<String,String>)output.get("producttypescountvisitssegments")));
				}
				if (output.get("producttypescountpageviewssegments") != null) {
					if (usage.get("producttypescountpageviewssegments") == null) { usage.put("producttypescountpageviewssegments", new LinkedHashMap<String,String>()); }
					usage.put("producttypescountpageviewssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountpageviewssegments"),(LinkedHashMap<String,String>)output.get("producttypescountpageviewssegments")));
				}
				if (output.get("producttypescountclienthoststests") != null) {
					if (usage.get("producttypescountclienthoststests") == null) { usage.put("producttypescountclienthoststests", new LinkedHashMap<String,String>()); }
					usage.put("producttypescountclienthoststests", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountclienthoststests"),(LinkedHashMap<String,String>)output.get("producttypescountclienthoststests")));
				}
				if (output.get("producttypescountvisitorstests") != null) {
					if (usage.get("producttypescountvisitorstests") == null) { usage.put("producttypescountvisitorstests", new LinkedHashMap<String,String>()); }
					usage.put("producttypescountvisitorstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountvisitorstests"),(LinkedHashMap<String,String>)output.get("producttypescountvisitorstests")));
				}
				if (output.get("producttypescountvisitstests") != null) {
					if (usage.get("producttypescountvisitstests") == null) { usage.put("producttypescountvisitstests", new LinkedHashMap<String,String>()); }
					usage.put("producttypescountvisitstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountvisitstests"),(LinkedHashMap<String,String>)output.get("producttypescountvisitstests")));
				}
				if (output.get("producttypescountpageviewstests") != null) {
					if (usage.get("producttypescountpageviewstests") == null) { usage.put("producttypescountpageviewstests", new LinkedHashMap<String,String>()); }
					usage.put("producttypescountpageviewstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountpageviewstests"),(LinkedHashMap<String,String>)output.get("producttypescountpageviewstests")));
				}
				if (output.get("producttypescountclienthostssegmentstests") != null) {
					if (usage.get("producttypescountclienthostssegmentstests") == null) { usage.put("producttypescountclienthostssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("producttypescountclienthostssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountclienthostssegmentstests"),(LinkedHashMap<String,String>)output.get("producttypescountclienthostssegmentstests")));
				}
				if (output.get("producttypescountvisitorssegmentstests") != null) {
					if (usage.get("producttypescountvisitorssegmentstests") == null) { usage.put("producttypescountvisitorssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("producttypescountvisitorssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountvisitorssegmentstests"),(LinkedHashMap<String,String>)output.get("producttypescountvisitorssegmentstests")));
				}
				if (output.get("producttypescountvisitssegmentstests") != null) {
					if (usage.get("producttypescountvisitssegmentstests") == null) { usage.put("producttypescountvisitssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("producttypescountvisitssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountvisitssegmentstests"),(LinkedHashMap<String,String>)output.get("producttypescountvisitssegmentstests")));
				}
				if (output.get("producttypescountpageviewssegmentstests") != null) {
					if (usage.get("producttypescountpageviewssegmentstests") == null) { usage.put("producttypescountpageviewssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("producttypescountpageviewssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("producttypescountpageviewssegmentstests"),(LinkedHashMap<String,String>)output.get("producttypescountpageviewssegmentstests")));
				}
			}
		}
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getDatabases(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsDatabases + SQLcolumnsVariantsSegments, "requestclass=" + logdb.quote("data"), "", "databases", "countclienthosts+countvisitors+countvisits+countpageviews+countusersegments", "requestclass:::requestdatabase", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsDatabases + SQLcolumnsVariantsUsertests, "requestclass=" + logdb.quote("data"), "", "databases", "countclienthosts+countvisitors+countvisits+countpageviews+countusertests", "requestclass:::requestdatabase", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsDatabases + SQLcolumnsVariants, "requestclass=" + logdb.quote("data"), "", "databases", "countclienthosts+countvisitors+countvisits+countpageviews+countusersegments+countusertests", "requestclass:::requestdatabase", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsDatabases, "requestclass=" + logdb.quote("data"), "", "databases", "countclienthosts+countvisitors+countvisits+countpageviews", "requestclass:::requestdatabase", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getDatabase(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsDatabase + SQLcolumnsVariantsSegments, "requestclass=" + logdb.quote("data") + " and requestdatabase=" + logdb.quote(myrequest.getParameter("database")), "", "database", "countclienthosts+countvisitors+countvisits+countpageviews+countusersegments", "requestclass:::requestdatabase:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsDatabase + SQLcolumnsVariantsUsertests, "requestclass=" + logdb.quote("data") + " and requestdatabase=" + logdb.quote(myrequest.getParameter("database")), "", "database", "countclienthosts+countvisitors+countvisits+countpageviews+countusertests", "requestclass:::requestdatabase:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsDatabase + SQLcolumnsVariants, "requestclass=" + logdb.quote("data") + " and requestdatabase=" + logdb.quote(myrequest.getParameter("database")), "", "database", "countclienthosts+countvisitors+countvisits+countpageviews+countusersegments+countusertests", "requestclass:::requestdatabase:::requestid", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsDatabase, "requestclass=" + logdb.quote("data") + " and requestdatabase=" + logdb.quote(myrequest.getParameter("database")), "", "database", "countclienthosts+countvisitors+countvisits+countpageviews", "requestclass:::requestdatabase:::requestid", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getContacts(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsContacts + SQLcolumnsVariantsSegments, "requestclass=" + logdb.quote("contact"), "", "contacts", "countclienthosts+countvisitors+countvisits+counthits+countusersegments", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsContacts + SQLcolumnsVariantsUsertests, "requestclass=" + logdb.quote("contact"), "", "contacts", "countclienthosts+countvisitors+countvisits+counthits+countusertests", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsContacts + SQLcolumnsVariants, "requestclass=" + logdb.quote("contact"), "", "contacts", "countclienthosts+countvisitors+countvisits+counthits+countusersegments+countusertests", "requestclass:::requestid", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsContacts, "requestclass=" + logdb.quote("contact"), "", "contacts", "countclienthosts+countvisitors+countvisits+counthits", "requestclass:::requestid", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getPosts(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsPosts + SQLcolumnsVariantsSegments, "requestclass=" + logdb.quote("post"), "", "posts", "countclienthosts+countvisitors+countvisits+counthits+countusersegments", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsPosts + SQLcolumnsVariantsUsertests, "requestclass=" + logdb.quote("post"), "", "posts", "countclienthosts+countvisitors+countvisits+counthits+countusertests", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsPosts + SQLcolumnsVariants, "requestclass=" + logdb.quote("post"), "", "posts", "countclienthosts+countvisitors+countvisits+counthits+countusersegments+countusertests", "requestclass:::requestid", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsPosts, "requestclass=" + logdb.quote("post"), "", "posts", "countclienthosts+countvisitors+countvisits+counthits", "requestclass:::requestid", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getLogins(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsLogins + SQLcolumnsVariantsSegments, "requestclass=" + logdb.quote("login"), "", "logins", "countclienthosts+countvisitors+countvisits+counthits+countusersegments", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsLogins + SQLcolumnsVariantsUsertests, "requestclass=" + logdb.quote("login"), "", "logins", "countclienthosts+countvisitors+countvisits+counthits+countusertests", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsLogins + SQLcolumnsVariants, "requestclass=" + logdb.quote("login"), "", "logins", "countclienthosts+countvisitors+countvisits+counthits+countusersegments+countusertests", "requestclass:::requestid", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsLogins, "requestclass=" + logdb.quote("login"), "", "logins", "countclienthosts+countvisitors+countvisits+counthits", "requestclass:::requestid", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getLogouts(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsLogouts + SQLcolumnsVariantsSegments, "requestclass=" + logdb.quote("logout"), "", "logouts", "countclienthosts+countvisitors+countvisits+counthits+countusersegments", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsLogouts + SQLcolumnsVariantsUsertests, "requestclass=" + logdb.quote("logout"), "", "logouts", "countclienthosts+countvisitors+countvisits+counthits+countusertests", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsLogouts + SQLcolumnsVariants, "requestclass=" + logdb.quote("logout"), "", "logouts", "countclienthosts+countvisitors+countvisits+counthits+countusersegments+countusertests", "requestclass:::requestid", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsLogouts, "requestclass=" + logdb.quote("logout"), "", "logouts", "countclienthosts+countvisitors+countvisits+counthits", "requestclass:::requestid", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getStyleSheets(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsStyleSheets + SQLcolumnsVariantsSegments, "requestclass=" + logdb.quote("stylesheet"), "", "stylesheets", "countclienthosts+countvisitors+countvisits+counthits+countusersegments", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsStyleSheets + SQLcolumnsVariantsUsertests, "requestclass=" + logdb.quote("stylesheet"), "", "stylesheets", "countclienthosts+countvisitors+countvisits+counthits+countusertests", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsStyleSheets + SQLcolumnsVariants, "requestclass=" + logdb.quote("stylesheet"), "", "stylesheets", "countclienthosts+countvisitors+countvisits+counthits+countusersegments+countusertests", "requestclass:::requestid", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsStyleSheets, "requestclass=" + logdb.quote("stylesheet"), "", "stylesheets", "countclienthosts+countvisitors+countvisits+counthits", "requestclass:::requestid", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getScripts(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsScripts + SQLcolumnsVariantsSegments, "requestclass=" + logdb.quote("script"), "", "scripts", "countclienthosts+countvisitors+countvisits+counthits+countusersegments", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsScripts + SQLcolumnsVariantsUsertests, "requestclass=" + logdb.quote("script"), "", "scripts", "countclienthosts+countvisitors+countvisits+counthits+countusertests", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsScripts + SQLcolumnsVariants, "requestclass=" + logdb.quote("script"), "", "scripts", "countclienthosts+countvisitors+countvisits+counthits+countusersegments+countusertests", "requestclass:::requestid", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsScripts, "requestclass=" + logdb.quote("script"), "", "scripts", "countclienthosts+countvisitors+countvisits+counthits", "requestclass:::requestid", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getLibrary(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsLibrary + SQLcolumnsVariantsSegments, SQLwhereLibrary, "", "library", "countclienthosts+countvisitors+countvisits+counthits+countusersegments", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsLibrary + SQLcolumnsVariantsUsertests, SQLwhereLibrary, "", "library", "countclienthosts+countvisitors+countvisits+counthits+countusertests", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsLibrary + SQLcolumnsVariants, SQLwhereLibrary, "", "library", "countclienthosts+countvisitors+countvisits+counthits+countusersegments+countusertests", "requestclass:::requestid", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", SQLcolumnsLibrary, SQLwhereLibrary, "", "library", "countclienthosts+countvisitors+countvisits+counthits", "requestclass:::requestid", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getImages(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		String SQLtable;
		String SQLwhere;
		if (myrequest.getParameter("contentgroup").equals("-")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("image") + " and " + db.is_blank("contentgroup") + ""));
			SQLtable = "usagelog";
			SQLwhere = SQLwhereImage + " and requestid in (" + content_ids + ")";
		} else if ((! myrequest.getParameter("contentgroup").equals("")) && (! myrequest.getParameter("contentgroup").equals(" "))) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("image") + " and contentgroup=" + db.quote(myrequest.getParameter("contentgroup"))));
			SQLtable = "usagelog";
			SQLwhere = SQLwhereImage + " and requestid in (" + content_ids + ")";
		} else if (myrequest.getParameter("contenttype").equals("-")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("image") + " and " + db.is_blank("contenttype") + ""));
			SQLtable = "usagelog";
			SQLwhere = SQLwhereImage + " and requestid in (" + content_ids + ")";
		} else if ((! myrequest.getParameter("contenttype").equals("")) && (! myrequest.getParameter("contenttype").equals(" "))) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("image") + " and contenttype=" + db.quote(myrequest.getParameter("contenttype"))));
			SQLtable = "usagelog";
			SQLwhere = SQLwhereImage + " and requestid in (" + content_ids + ")";
		} else {
			SQLtable = "usagelog";
			SQLwhere = SQLwhereImage;
		}
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsImages + SQLcolumnsVariantsSegments, SQLwhere, "", "images", "countclienthosts+countvisitors+countvisits+counthits+countusersegments", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsImages + SQLcolumnsVariantsUsertests, SQLwhere, "", "images", "countclienthosts+countvisitors+countvisits+counthits+countusertests", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsImages + SQLcolumnsVariants, SQLwhere, "", "images", "countclienthosts+countvisitors+countvisits+counthits+countusersegments+countusertests", "requestclass:::requestid", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsImages, SQLwhere, "", "images", "countclienthosts+countvisitors+countvisits+counthits", "requestclass:::requestid", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getImagegroups(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,LinkedHashMap<String,String>> usage = new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,HashMap<String,String>> rows = db.query_records("select imagegroup from imagegroups");
		rows.put("", new HashMap<String,String>());
		Iterator rowsIterator = rows.keySet().iterator();
		while (rowsIterator.hasNext()) {
			String key = "" + rowsIterator.next();
			HashMap<String,String> row = (HashMap<String,String>)rows.get(key);
			if (row.get("imagegroup") == null) row.put("imagegroup","");
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("image") + Common.SQLwhere_equals(db, " ", "contentgroup", "" + row.get("imagegroup"))));
			HashMap<String,LinkedHashMap<String,String>> output;
			if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("imagegroup")) + " as contentgroup" + SQLcolumnsVariantsSegments, SQLwhereImage + " and requestid in (" + content_ids + ")", "", "imagegroups", "countclienthosts+countvisitors+countvisits+counthits+countusersegments", "contentgroup", true, false);
			} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("imagegroup")) + " as contentgroup" + SQLcolumnsVariantsUsertests, SQLwhereImage + " and requestid in (" + content_ids + ")", "", "imagegroups", "countclienthosts+countvisitors+countvisits+counthits+countusertests", "contentgroup", true, false);
			} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("imagegroup")) + " as contentgroup" + SQLcolumnsVariants, SQLwhereImage + " and requestid in (" + content_ids + ")", "", "imagegroups", "countclienthosts+countvisitors+countvisits+counthits+countusersegments+countusertests", "contentgroup", true, false);
			} else {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("imagegroup")) + " as contentgroup", SQLwhereImage + " and requestid in (" + content_ids + ")", "", "imagegroups", "countclienthosts+countvisitors+countvisits+counthits", "contentgroup", true, false);
			}
			if (usage.get("imagegroupscountclienthosts") == null) { usage.put("imagegroupscountclienthosts", new LinkedHashMap<String,String>()); }
			if (usage.get("imagegroupscountvisitors") == null) { usage.put("imagegroupscountvisitors", new LinkedHashMap<String,String>()); }
			if (usage.get("imagegroupscountvisits") == null) { usage.put("imagegroupscountvisits", new LinkedHashMap<String,String>()); }
			if (usage.get("imagegroupscounthits") == null) { usage.put("imagegroupscounthits", new LinkedHashMap<String,String>()); }
			if (output.get("imagegroupscountclienthosts") != null) usage.put("imagegroupscountclienthosts", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscountclienthosts"),(LinkedHashMap<String,String>)output.get("imagegroupscountclienthosts")));
			if (output.get("imagegroupscountvisitors") != null) usage.put("imagegroupscountvisitors", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscountvisitors"),(LinkedHashMap<String,String>)output.get("imagegroupscountvisitors")));
			if (output.get("imagegroupscountvisits") != null) usage.put("imagegroupscountvisits", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscountvisits"),(LinkedHashMap<String,String>)output.get("imagegroupscountvisits")));
			if (output.get("imagegroupscounthits") != null) usage.put("imagegroupscounthits", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscounthits"),(LinkedHashMap<String,String>)output.get("imagegroupscounthits")));
			if (License.valid(db, myconfig, "experience")) {
				if (output.get("imagegroupscountclienthostssegment") != null) {
					if (usage.get("imagegroupscountclienthostssegment") == null) { usage.put("imagegroupscountclienthostssegment", new LinkedHashMap<String,String>()); }
					usage.put("imagegroupscountclienthostssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscountclienthostssegment"),(LinkedHashMap<String,String>)output.get("imagegroupscountclienthostssegment")));
				}
				if (output.get("imagegroupscountvisitorssegment") != null) {
					if (usage.get("imagegroupscountvisitorssegment") == null) { usage.put("imagegroupscountvisitorssegment", new LinkedHashMap<String,String>()); }
					usage.put("imagegroupscountvisitorssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscountvisitorssegment"),(LinkedHashMap<String,String>)output.get("imagegroupscountvisitorssegment")));
				}
				if (output.get("imagegroupscountvisitssegment") != null) {
					if (usage.get("imagegroupscountvisitssegment") == null) { usage.put("imagegroupscountvisitssegment", new LinkedHashMap<String,String>()); }
					usage.put("imagegroupscountvisitssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscountvisitssegment"),(LinkedHashMap<String,String>)output.get("imagegroupscountvisitssegment")));
				}
				if (output.get("imagegroupscounthitssegment") != null) {
					if (usage.get("imagegroupscounthitssegment") == null) { usage.put("imagegroupscounthitssegment", new LinkedHashMap<String,String>()); }
					usage.put("imagegroupscounthitssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscounthitssegment"),(LinkedHashMap<String,String>)output.get("imagegroupscounthitssegment")));
				}
				if (output.get("imagegroupscountclienthoststest") != null) {
					if (usage.get("imagegroupscountclienthoststest") == null) { usage.put("imagegroupscountclienthoststest", new LinkedHashMap<String,String>()); }
					usage.put("imagegroupscountclienthoststest", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscountclienthoststest"),(LinkedHashMap<String,String>)output.get("imagegroupscountclienthoststest")));
				}
				if (output.get("imagegroupscountvisitorstest") != null) {
					if (usage.get("imagegroupscountvisitorstest") == null) { usage.put("imagegroupscountvisitorstest", new LinkedHashMap<String,String>()); }
					usage.put("imagegroupscountvisitorstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscountvisitorstest"),(LinkedHashMap<String,String>)output.get("imagegroupscountvisitorstest")));
				}
				if (output.get("imagegroupscountvisitstest") != null) {
					if (usage.get("imagegroupscountvisitstest") == null) { usage.put("imagegroupscountvisitstest", new LinkedHashMap<String,String>()); }
					usage.put("imagegroupscountvisitstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscountvisitstest"),(LinkedHashMap<String,String>)output.get("imagegroupscountvisitstest")));
				}
				if (output.get("imagegroupscounthitstest") != null) {
					if (usage.get("imagegroupscounthitstest") == null) { usage.put("imagegroupscounthitstest", new LinkedHashMap<String,String>()); }
					usage.put("imagegroupscounthitstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscounthitstest"),(LinkedHashMap<String,String>)output.get("imagegroupscounthitstest")));
				}
				if (output.get("imagegroupscountclienthostssegments") != null) {
					if (usage.get("imagegroupscountclienthostssegments") == null) { usage.put("imagegroupscountclienthostssegments", new LinkedHashMap<String,String>()); }
					usage.put("imagegroupscountclienthostssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscountclienthostssegments"),(LinkedHashMap<String,String>)output.get("imagegroupscountclienthostssegments")));
				}
				if (output.get("imagegroupscountvisitorssegments") != null) {
					if (usage.get("imagegroupscountvisitorssegments") == null) { usage.put("imagegroupscountvisitorssegments", new LinkedHashMap<String,String>()); }
					usage.put("imagegroupscountvisitorssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscountvisitorssegments"),(LinkedHashMap<String,String>)output.get("imagegroupscountvisitorssegments")));
				}
				if (output.get("imagegroupscountvisitssegments") != null) {
					if (usage.get("imagegroupscountvisitssegments") == null) { usage.put("imagegroupscountvisitssegments", new LinkedHashMap<String,String>()); }
					usage.put("imagegroupscountvisitssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscountvisitssegments"),(LinkedHashMap<String,String>)output.get("imagegroupscountvisitssegments")));
				}
				if (output.get("imagegroupscounthitssegments") != null) {
					if (usage.get("imagegroupscounthitssegments") == null) { usage.put("imagegroupscounthitssegments", new LinkedHashMap<String,String>()); }
					usage.put("imagegroupscounthitssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscounthitssegments"),(LinkedHashMap<String,String>)output.get("imagegroupscounthitssegments")));
				}
				if (output.get("imagegroupscountclienthoststests") != null) {
					if (usage.get("imagegroupscountclienthoststests") == null) { usage.put("imagegroupscountclienthoststests", new LinkedHashMap<String,String>()); }
					usage.put("imagegroupscountclienthoststests", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscountclienthoststests"),(LinkedHashMap<String,String>)output.get("imagegroupscountclienthoststests")));
				}
				if (output.get("imagegroupscountvisitorstests") != null) {
					if (usage.get("imagegroupscountvisitorstests") == null) { usage.put("imagegroupscountvisitorstests", new LinkedHashMap<String,String>()); }
					usage.put("imagegroupscountvisitorstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscountvisitorstests"),(LinkedHashMap<String,String>)output.get("imagegroupscountvisitorstests")));
				}
				if (output.get("imagegroupscountvisitstests") != null) {
					if (usage.get("imagegroupscountvisitstests") == null) { usage.put("imagegroupscountvisitstests", new LinkedHashMap<String,String>()); }
					usage.put("imagegroupscountvisitstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscountvisitstests"),(LinkedHashMap<String,String>)output.get("imagegroupscountvisitstests")));
				}
				if (output.get("imagegroupscounthitstests") != null) {
					if (usage.get("imagegroupscounthitstests") == null) { usage.put("imagegroupscounthitstests", new LinkedHashMap<String,String>()); }
					usage.put("imagegroupscounthitstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscounthitstests"),(LinkedHashMap<String,String>)output.get("imagegroupscounthitstests")));
				}
				if (output.get("imagegroupscountclienthostssegmentstests") != null) {
					if (usage.get("imagegroupscountclienthostssegmentstests") == null) { usage.put("imagegroupscountclienthostssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("imagegroupscountclienthostssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscountclienthostssegmentstests"),(LinkedHashMap<String,String>)output.get("imagegroupscountclienthostssegmentstests")));
				}
				if (output.get("imagegroupscountvisitorssegmentstests") != null) {
					if (usage.get("imagegroupscountvisitorssegmentstests") == null) { usage.put("imagegroupscountvisitorssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("imagegroupscountvisitorssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscountvisitorssegmentstests"),(LinkedHashMap<String,String>)output.get("imagegroupscountvisitorssegmentstests")));
				}
				if (output.get("imagegroupscountvisitssegmentstests") != null) {
					if (usage.get("imagegroupscountvisitssegmentstests") == null) { usage.put("imagegroupscountvisitssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("imagegroupscountvisitssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscountvisitssegmentstests"),(LinkedHashMap<String,String>)output.get("imagegroupscountvisitssegmentstests")));
				}
				if (output.get("imagegroupscounthitssegmentstests") != null) {
					if (usage.get("imagegroupscounthitssegmentstests") == null) { usage.put("imagegroupscounthitssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("imagegroupscounthitssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagegroupscounthitssegmentstests"),(LinkedHashMap<String,String>)output.get("imagegroupscounthitssegmentstests")));
				}
			}
		}
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getImagetypes(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,LinkedHashMap<String,String>> usage = new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,HashMap<String,String>> rows = db.query_records("select imagetype from imagetypes");
		rows.put("", new HashMap<String,String>());
		Iterator rowsIterator = rows.keySet().iterator();
		while (rowsIterator.hasNext()) {
			String key = "" + rowsIterator.next();
			HashMap<String,String> row = (HashMap<String,String>)rows.get(key);
			if (row.get("imagetype") == null) row.put("imagetype","");
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("image") + Common.SQLwhere_equals(db, " ", "contenttype", "" + row.get("imagetype"))));
			HashMap<String,LinkedHashMap<String,String>> output;
			if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("imagetype")) + " as contenttype" + SQLcolumnsVariantsSegments, SQLwhereImage + " and requestid in (" + content_ids + ")", "", "imagetypes", "countclienthosts+countvisitors+countvisits+counthits+countusersegments", "contenttype", true, false);
			} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("imagetype")) + " as contenttype" + SQLcolumnsVariantsUsertests, SQLwhereImage + " and requestid in (" + content_ids + ")", "", "imagetypes", "countclienthosts+countvisitors+countvisits+counthits+countusertests", "contenttype", true, false);
			} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("imagetype")) + " as contenttype" + SQLcolumnsVariants, SQLwhereImage + " and requestid in (" + content_ids + ")", "", "imagetypes", "countclienthosts+countvisitors+countvisits+counthits+countusersegments+countusertests", "contenttype", true, false);
			} else {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("imagetype")) + " as contenttype", SQLwhereImage + " and requestid in (" + content_ids + ")", "", "imagetypes", "countclienthosts+countvisitors+countvisits+counthits", "contenttype", true, false);
			}
			if (usage.get("imagetypescountclienthosts") == null) { usage.put("imagetypescountclienthosts", new LinkedHashMap<String,String>()); }
			if (usage.get("imagetypescountvisitors") == null) { usage.put("imagetypescountvisitors", new LinkedHashMap<String,String>()); }
			if (usage.get("imagetypescountvisits") == null) { usage.put("imagetypescountvisits", new LinkedHashMap<String,String>()); }
			if (usage.get("imagetypescounthits") == null) { usage.put("imagetypescounthits", new LinkedHashMap<String,String>()); }
			if (output.get("imagetypescountclienthosts") != null) usage.put("imagetypescountclienthosts", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescountclienthosts"),(LinkedHashMap<String,String>)output.get("imagetypescountclienthosts")));
			if (output.get("imagetypescountvisitors") != null) usage.put("imagetypescountvisitors", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescountvisitors"),(LinkedHashMap<String,String>)output.get("imagetypescountvisitors")));
			if (output.get("imagetypescountvisits") != null) usage.put("imagetypescountvisits", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescountvisits"),(LinkedHashMap<String,String>)output.get("imagetypescountvisits")));
			if (output.get("imagetypescounthits") != null) usage.put("imagetypescounthits", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescounthits"),(LinkedHashMap<String,String>)output.get("imagetypescounthits")));
			if (License.valid(db, myconfig, "experience")) {
				if (output.get("imagetypescountclienthostssegment") != null) {
					if (usage.get("imagetypescountclienthostssegment") == null) { usage.put("imagetypescountclienthostssegment", new LinkedHashMap<String,String>()); }
					usage.put("imagetypescountclienthostssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescountclienthostssegment"),(LinkedHashMap<String,String>)output.get("imagetypescountclienthostssegment")));
				}
				if (output.get("imagetypescountvisitorssegment") != null) {
					if (usage.get("imagetypescountvisitorssegment") == null) { usage.put("imagetypescountvisitorssegment", new LinkedHashMap<String,String>()); }
					usage.put("imagetypescountvisitorssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescountvisitorssegment"),(LinkedHashMap<String,String>)output.get("imagetypescountvisitorssegment")));
				}
				if (output.get("imagetypescountvisitssegment") != null) {
					if (usage.get("imagetypescountvisitssegment") == null) { usage.put("imagetypescountvisitssegment", new LinkedHashMap<String,String>()); }
					usage.put("imagetypescountvisitssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescountvisitssegment"),(LinkedHashMap<String,String>)output.get("imagetypescountvisitssegment")));
				}
				if (output.get("imagetypescounthitssegment") != null) {
					if (usage.get("imagetypescounthitssegment") == null) { usage.put("imagetypescounthitssegment", new LinkedHashMap<String,String>()); }
					usage.put("imagetypescounthitssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescounthitssegment"),(LinkedHashMap<String,String>)output.get("imagetypescounthitssegment")));
				}
				if (output.get("imagetypescountclienthoststest") != null) {
					if (usage.get("imagetypescountclienthoststest") == null) { usage.put("imagetypescountclienthoststest", new LinkedHashMap<String,String>()); }
					usage.put("imagetypescountclienthoststest", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescountclienthoststest"),(LinkedHashMap<String,String>)output.get("imagetypescountclienthoststest")));
				}
				if (output.get("imagetypescountvisitorstest") != null) {
					if (usage.get("imagetypescountvisitorstest") == null) { usage.put("imagetypescountvisitorstest", new LinkedHashMap<String,String>()); }
					usage.put("imagetypescountvisitorstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescountvisitorstest"),(LinkedHashMap<String,String>)output.get("imagetypescountvisitorstest")));
				}
				if (output.get("imagetypescountvisitstest") != null) {
					if (usage.get("imagetypescountvisitstest") == null) { usage.put("imagetypescountvisitstest", new LinkedHashMap<String,String>()); }
					usage.put("imagetypescountvisitstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescountvisitstest"),(LinkedHashMap<String,String>)output.get("imagetypescountvisitstest")));
				}
				if (output.get("imagetypescounthitstest") != null) {
					if (usage.get("imagetypescounthitstest") == null) { usage.put("imagetypescounthitstest", new LinkedHashMap<String,String>()); }
					usage.put("imagetypescounthitstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescounthitstest"),(LinkedHashMap<String,String>)output.get("imagetypescounthitstest")));
				}
				if (output.get("imagetypescountclienthostssegments") != null) {
					if (usage.get("imagetypescountclienthostssegments") == null) { usage.put("imagetypescountclienthostssegments", new LinkedHashMap<String,String>()); }
					usage.put("imagetypescountclienthostssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescountclienthostssegments"),(LinkedHashMap<String,String>)output.get("imagetypescountclienthostssegments")));
				}
				if (output.get("imagetypescountvisitorssegments") != null) {
					if (usage.get("imagetypescountvisitorssegments") == null) { usage.put("imagetypescountvisitorssegments", new LinkedHashMap<String,String>()); }
					usage.put("imagetypescountvisitorssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescountvisitorssegments"),(LinkedHashMap<String,String>)output.get("imagetypescountvisitorssegments")));
				}
				if (output.get("imagetypescountvisitssegments") != null) {
					if (usage.get("imagetypescountvisitssegments") == null) { usage.put("imagetypescountvisitssegments", new LinkedHashMap<String,String>()); }
					usage.put("imagetypescountvisitssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescountvisitssegments"),(LinkedHashMap<String,String>)output.get("imagetypescountvisitssegments")));
				}
				if (output.get("imagetypescounthitssegments") != null) {
					if (usage.get("imagetypescounthitssegments") == null) { usage.put("imagetypescounthitssegments", new LinkedHashMap<String,String>()); }
					usage.put("imagetypescounthitssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescounthitssegments"),(LinkedHashMap<String,String>)output.get("imagetypescounthitssegments")));
				}
				if (output.get("imagetypescountclienthoststests") != null) {
					if (usage.get("imagetypescountclienthoststests") == null) { usage.put("imagetypescountclienthoststests", new LinkedHashMap<String,String>()); }
					usage.put("imagetypescountclienthoststests", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescountclienthoststests"),(LinkedHashMap<String,String>)output.get("imagetypescountclienthoststests")));
				}
				if (output.get("imagetypescountvisitorstests") != null) {
					if (usage.get("imagetypescountvisitorstests") == null) { usage.put("imagetypescountvisitorstests", new LinkedHashMap<String,String>()); }
					usage.put("imagetypescountvisitorstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescountvisitorstests"),(LinkedHashMap<String,String>)output.get("imagetypescountvisitorstests")));
				}
				if (output.get("imagetypescountvisitstests") != null) {
					if (usage.get("imagetypescountvisitstests") == null) { usage.put("imagetypescountvisitstests", new LinkedHashMap<String,String>()); }
					usage.put("imagetypescountvisitstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescountvisitstests"),(LinkedHashMap<String,String>)output.get("imagetypescountvisitstests")));
				}
				if (output.get("imagetypescounthitstests") != null) {
					if (usage.get("imagetypescounthitstests") == null) { usage.put("imagetypescounthitstests", new LinkedHashMap<String,String>()); }
					usage.put("imagetypescounthitstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescounthitstests"),(LinkedHashMap<String,String>)output.get("imagetypescounthitstests")));
				}
				if (output.get("imagetypescountclienthostssegmentstests") != null) {
					if (usage.get("imagetypescountclienthostssegmentstests") == null) { usage.put("imagetypescountclienthostssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("imagetypescountclienthostssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescountclienthostssegmentstests"),(LinkedHashMap<String,String>)output.get("imagetypescountclienthostssegmentstests")));
				}
				if (output.get("imagetypescountvisitorssegmentstests") != null) {
					if (usage.get("imagetypescountvisitorssegmentstests") == null) { usage.put("imagetypescountvisitorssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("imagetypescountvisitorssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescountvisitorssegmentstests"),(LinkedHashMap<String,String>)output.get("imagetypescountvisitorssegmentstests")));
				}
				if (output.get("imagetypescountvisitssegmentstests") != null) {
					if (usage.get("imagetypescountvisitssegmentstests") == null) { usage.put("imagetypescountvisitssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("imagetypescountvisitssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescountvisitssegmentstests"),(LinkedHashMap<String,String>)output.get("imagetypescountvisitssegmentstests")));
				}
				if (output.get("imagetypescounthitssegmentstests") != null) {
					if (usage.get("imagetypescounthitssegmentstests") == null) { usage.put("imagetypescounthitssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("imagetypescounthitssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("imagetypescounthitssegmentstests"),(LinkedHashMap<String,String>)output.get("imagetypescounthitssegmentstests")));
				}
			}
		}
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getFiles(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		String SQLtable;
		String SQLwhere;
		if (myrequest.getParameter("contentgroup").equals("-")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("file") + " and " + db.is_blank("contentgroup") + ""));
			SQLtable = "usagelog";
			SQLwhere = SQLwhereFile + " and requestid in (" + content_ids + ")";
		} else if ((! myrequest.getParameter("contentgroup").equals("")) && (! myrequest.getParameter("contentgroup").equals(" "))) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("file") + " and contentgroup=" + db.quote(myrequest.getParameter("contentgroup"))));
			SQLtable = "usagelog";
			SQLwhere = SQLwhereFile + " and requestid in (" + content_ids + ")";
		} else if (myrequest.getParameter("contenttype").equals("-")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("file") + " and " + db.is_blank("contenttype") + ""));
			SQLtable = "usagelog";
			SQLwhere = SQLwhereFile + " and requestid in (" + content_ids + ")";
		} else if ((! myrequest.getParameter("contenttype").equals("")) && (! myrequest.getParameter("contenttype").equals(" "))) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("file") + " and contenttype=" + db.quote(myrequest.getParameter("contenttype"))));
			SQLtable = "usagelog";
			SQLwhere = SQLwhereFile + " and requestid in (" + content_ids + ")";
		} else {
			SQLtable = "usagelog";
			SQLwhere = SQLwhereFile;
		}
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsFiles + SQLcolumnsVariantsSegments, SQLwhere, "", "files", "countclienthosts+countvisitors+countvisits+counthits+countusersegments", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsFiles + SQLcolumnsVariantsUsertests, SQLwhere, "", "files", "countclienthosts+countvisitors+countvisits+counthits+countusertests", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsFiles + SQLcolumnsVariants, SQLwhere, "", "files", "countclienthosts+countvisitors+countvisits+counthits+countusersegments+countusertests", "requestclass:::requestid", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsFiles, SQLwhere, "", "files", "countclienthosts+countvisitors+countvisits+counthits", "requestclass:::requestid", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getFilegroups(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,LinkedHashMap<String,String>> usage = new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,HashMap<String,String>> rows = db.query_records("select filegroup from filegroups");
		rows.put("", new HashMap<String,String>());
		Iterator rowsIterator = rows.keySet().iterator();
		while (rowsIterator.hasNext()) {
			String key = "" + rowsIterator.next();
			HashMap<String,String> row = (HashMap<String,String>)rows.get(key);
			if (row.get("filegroup") == null) row.put("filegroup","");
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("file") + Common.SQLwhere_equals(db, " ", "contentgroup", "" + row.get("filegroup"))));
			HashMap<String,LinkedHashMap<String,String>> output;
			if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("filegroup")) + " as contentgroup" + SQLcolumnsVariantsSegments, SQLwhereFile + " and requestid in (" + content_ids + ")", "", "filegroups", "countclienthosts+countvisitors+countvisits+counthits+countusersegments", "contentgroup", true, false);
			} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("filegroup")) + " as contentgroup" + SQLcolumnsVariantsUsertests, SQLwhereFile + " and requestid in (" + content_ids + ")", "", "filegroups", "countclienthosts+countvisitors+countvisits+counthits+countusertests", "contentgroup", true, false);
			} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("filegroup")) + " as contentgroup" + SQLcolumnsVariants, SQLwhereFile + " and requestid in (" + content_ids + ")", "", "filegroups", "countclienthosts+countvisitors+countvisits+counthits+countusersegments+countusertests", "contentgroup", true, false);
			} else {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("filegroup")) + " as contentgroup", SQLwhereFile + " and requestid in (" + content_ids + ")", "", "filegroups", "countclienthosts+countvisitors+countvisits+counthits", "contentgroup", true, false);
			}
			if (usage.get("filegroupscountclienthosts") == null) { usage.put("filegroupscountclienthosts", new LinkedHashMap<String,String>()); }
			if (usage.get("filegroupscountvisitors") == null) { usage.put("filegroupscountvisitors", new LinkedHashMap<String,String>()); }
			if (usage.get("filegroupscountvisits") == null) { usage.put("filegroupscountvisits", new LinkedHashMap<String,String>()); }
			if (usage.get("filegroupscounthits") == null) { usage.put("filegroupscounthits", new LinkedHashMap<String,String>()); }
			if (output.get("filegroupscountclienthosts") != null) usage.put("filegroupscountclienthosts", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscountclienthosts"),(LinkedHashMap<String,String>)output.get("filegroupscountclienthosts")));
			if (output.get("filegroupscountvisitors") != null) usage.put("filegroupscountvisitors", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscountvisitors"),(LinkedHashMap<String,String>)output.get("filegroupscountvisitors")));
			if (output.get("filegroupscountvisits") != null) usage.put("filegroupscountvisits", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscountvisits"),(LinkedHashMap<String,String>)output.get("filegroupscountvisits")));
			if (output.get("filegroupscounthits") != null) usage.put("filegroupscounthits", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscounthits"),(LinkedHashMap<String,String>)output.get("filegroupscounthits")));
			if (License.valid(db, myconfig, "experience")) {
				if (output.get("filegroupscountclienthostssegment") != null) {
					if (usage.get("filegroupscountclienthostssegment") == null) { usage.put("filegroupscountclienthostssegment", new LinkedHashMap<String,String>()); }
					usage.put("filegroupscountclienthostssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscountclienthostssegment"),(LinkedHashMap<String,String>)output.get("filegroupscountclienthostssegment")));
				}
				if (output.get("filegroupscountvisitorssegment") != null) {
					if (usage.get("filegroupscountvisitorssegment") == null) { usage.put("filegroupscountvisitorssegment", new LinkedHashMap<String,String>()); }
					usage.put("filegroupscountvisitorssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscountvisitorssegment"),(LinkedHashMap<String,String>)output.get("filegroupscountvisitorssegment")));
				}
				if (output.get("filegroupscountvisitssegment") != null) {
					if (usage.get("filegroupscountvisitssegment") == null) { usage.put("filegroupscountvisitssegment", new LinkedHashMap<String,String>()); }
					usage.put("filegroupscountvisitssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscountvisitssegment"),(LinkedHashMap<String,String>)output.get("filegroupscountvisitssegment")));
				}
				if (output.get("filegroupscounthitssegment") != null) {
					if (usage.get("filegroupscounthitssegment") == null) { usage.put("filegroupscounthitssegment", new LinkedHashMap<String,String>()); }
					usage.put("filegroupscounthitssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscounthitssegment"),(LinkedHashMap<String,String>)output.get("filegroupscounthitssegment")));
				}
				if (output.get("filegroupscountclienthoststest") != null) {
					if (usage.get("filegroupscountclienthoststest") == null) { usage.put("filegroupscountclienthoststest", new LinkedHashMap<String,String>()); }
					usage.put("filegroupscountclienthoststest", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscountclienthoststest"),(LinkedHashMap<String,String>)output.get("filegroupscountclienthoststest")));
				}
				if (output.get("filegroupscountvisitorstest") != null) {
					if (usage.get("filegroupscountvisitorstest") == null) { usage.put("filegroupscountvisitorstest", new LinkedHashMap<String,String>()); }
					usage.put("filegroupscountvisitorstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscountvisitorstest"),(LinkedHashMap<String,String>)output.get("filegroupscountvisitorstest")));
				}
				if (output.get("filegroupscountvisitstest") != null) {
					if (usage.get("filegroupscountvisitstest") == null) { usage.put("filegroupscountvisitstest", new LinkedHashMap<String,String>()); }
					usage.put("filegroupscountvisitstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscountvisitstest"),(LinkedHashMap<String,String>)output.get("filegroupscountvisitstest")));
				}
				if (output.get("filegroupscounthitstest") != null) {
					if (usage.get("filegroupscounthitstest") == null) { usage.put("filegroupscounthitstest", new LinkedHashMap<String,String>()); }
					usage.put("filegroupscounthitstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscounthitstest"),(LinkedHashMap<String,String>)output.get("filegroupscounthitstest")));
				}
				if (output.get("filegroupscountclienthostssegments") != null) {
					if (usage.get("filegroupscountclienthostssegments") == null) { usage.put("filegroupscountclienthostssegments", new LinkedHashMap<String,String>()); }
					usage.put("filegroupscountclienthostssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscountclienthostssegments"),(LinkedHashMap<String,String>)output.get("filegroupscountclienthostssegments")));
				}
				if (output.get("filegroupscountvisitorssegments") != null) {
					if (usage.get("filegroupscountvisitorssegments") == null) { usage.put("filegroupscountvisitorssegments", new LinkedHashMap<String,String>()); }
					usage.put("filegroupscountvisitorssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscountvisitorssegments"),(LinkedHashMap<String,String>)output.get("filegroupscountvisitorssegments")));
				}
				if (output.get("filegroupscountvisitssegments") != null) {
					if (usage.get("filegroupscountvisitssegments") == null) { usage.put("filegroupscountvisitssegments", new LinkedHashMap<String,String>()); }
					usage.put("filegroupscountvisitssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscountvisitssegments"),(LinkedHashMap<String,String>)output.get("filegroupscountvisitssegments")));
				}
				if (output.get("filegroupscounthitssegments") != null) {
					if (usage.get("filegroupscounthitssegments") == null) { usage.put("filegroupscounthitssegments", new LinkedHashMap<String,String>()); }
					usage.put("filegroupscounthitssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscounthitssegments"),(LinkedHashMap<String,String>)output.get("filegroupscounthitssegments")));
				}
				if (output.get("filegroupscountclienthoststests") != null) {
					if (usage.get("filegroupscountclienthoststests") == null) { usage.put("filegroupscountclienthoststests", new LinkedHashMap<String,String>()); }
					usage.put("filegroupscountclienthoststests", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscountclienthoststests"),(LinkedHashMap<String,String>)output.get("filegroupscountclienthoststests")));
				}
				if (output.get("filegroupscountvisitorstests") != null) {
					if (usage.get("filegroupscountvisitorstests") == null) { usage.put("filegroupscountvisitorstests", new LinkedHashMap<String,String>()); }
					usage.put("filegroupscountvisitorstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscountvisitorstests"),(LinkedHashMap<String,String>)output.get("filegroupscountvisitorstests")));
				}
				if (output.get("filegroupscountvisitstests") != null) {
					if (usage.get("filegroupscountvisitstests") == null) { usage.put("filegroupscountvisitstests", new LinkedHashMap<String,String>()); }
					usage.put("filegroupscountvisitstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscountvisitstests"),(LinkedHashMap<String,String>)output.get("filegroupscountvisitstests")));
				}
				if (output.get("filegroupscounthitstests") != null) {
					if (usage.get("filegroupscounthitstests") == null) { usage.put("filegroupscounthitstests", new LinkedHashMap<String,String>()); }
					usage.put("filegroupscounthitstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscounthitstests"),(LinkedHashMap<String,String>)output.get("filegroupscounthitstests")));
				}
				if (output.get("filegroupscountclienthostssegmentstests") != null) {
					if (usage.get("filegroupscountclienthostssegmentstests") == null) { usage.put("filegroupscountclienthostssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("filegroupscountclienthostssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscountclienthostssegmentstests"),(LinkedHashMap<String,String>)output.get("filegroupscountclienthostssegmentstests")));
				}
				if (output.get("filegroupscountvisitorssegmentstests") != null) {
					if (usage.get("filegroupscountvisitorssegmentstests") == null) { usage.put("filegroupscountvisitorssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("filegroupscountvisitorssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscountvisitorssegmentstests"),(LinkedHashMap<String,String>)output.get("filegroupscountvisitorssegmentstests")));
				}
				if (output.get("filegroupscountvisitssegmentstests") != null) {
					if (usage.get("filegroupscountvisitssegmentstests") == null) { usage.put("filegroupscountvisitssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("filegroupscountvisitssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscountvisitssegmentstests"),(LinkedHashMap<String,String>)output.get("filegroupscountvisitssegmentstests")));
				}
				if (output.get("filegroupscounthitssegmentstests") != null) {
					if (usage.get("filegroupscounthitssegmentstests") == null) { usage.put("filegroupscounthitssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("filegroupscounthitssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("filegroupscounthitssegmentstests"),(LinkedHashMap<String,String>)output.get("filegroupscounthitssegmentstests")));
				}
			}
		}
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getFiletypes(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,LinkedHashMap<String,String>> usage = new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,HashMap<String,String>> rows = db.query_records("select filetype from filetypes");
		rows.put("", new HashMap<String,String>());
		Iterator rowsIterator = rows.keySet().iterator();
		while (rowsIterator.hasNext()) {
			String key = "" + rowsIterator.next();
			HashMap<String,String> row = (HashMap<String,String>)rows.get(key);
			if (row.get("filetype") == null) row.put("filetype","");
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("file") + Common.SQLwhere_equals(db, " ", "contenttype", "" + row.get("filetype"))));
			HashMap<String,LinkedHashMap<String,String>> output;
			if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("filetype")) + " as contenttype" + SQLcolumnsVariantsSegments, SQLwhereFile + " and requestid in (" + content_ids + ")", "", "filetypes", "countclienthosts+countvisitors+countvisits+counthits+countusersegments", "contenttype", true, false);
			} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("filetype")) + " as contenttype" + SQLcolumnsVariantsUsertests, SQLwhereFile + " and requestid in (" + content_ids + ")", "", "filetypes", "countclienthosts+countvisitors+countvisits+counthits+countusertests", "contenttype", true, false);
			} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("filetype")) + " as contenttype" + SQLcolumnsVariants, SQLwhereFile + " and requestid in (" + content_ids + ")", "", "filetypes", "countclienthosts+countvisitors+countvisits+counthits+countusersegments+countusertests", "contenttype", true, false);
			} else {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("filetype")) + " as contenttype", SQLwhereFile + " and requestid in (" + content_ids + ")", "", "filetypes", "countclienthosts+countvisitors+countvisits+counthits", "contenttype", true, false);
			}
			if (usage.get("filetypescountclienthosts") == null) { usage.put("filetypescountclienthosts", new LinkedHashMap<String,String>()); }
			if (usage.get("filetypescountvisitors") == null) { usage.put("filetypescountvisitors", new LinkedHashMap<String,String>()); }
			if (usage.get("filetypescountvisits") == null) { usage.put("filetypescountvisits", new LinkedHashMap<String,String>()); }
			if (usage.get("filetypescounthits") == null) { usage.put("filetypescounthits", new LinkedHashMap<String,String>()); }
			if (output.get("filetypescountclienthosts") != null) usage.put("filetypescountclienthosts", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescountclienthosts"),(LinkedHashMap<String,String>)output.get("filetypescountclienthosts")));
			if (output.get("filetypescountvisitors") != null) usage.put("filetypescountvisitors", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescountvisitors"),(LinkedHashMap<String,String>)output.get("filetypescountvisitors")));
			if (output.get("filetypescountvisits") != null) usage.put("filetypescountvisits", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescountvisits"),(LinkedHashMap<String,String>)output.get("filetypescountvisits")));
			if (output.get("filetypescounthits") != null) usage.put("filetypescounthits", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescounthits"),(LinkedHashMap<String,String>)output.get("filetypescounthits")));
			if (License.valid(db, myconfig, "experience")) {
				if (output.get("filetypescountclienthostssegment") != null) {
					if (usage.get("filetypescountclienthostssegment") == null) { usage.put("filetypescountclienthostssegment", new LinkedHashMap<String,String>()); }
					usage.put("filetypescountclienthostssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescountclienthostssegment"),(LinkedHashMap<String,String>)output.get("filetypescountclienthostssegment")));
				}
				if (output.get("filetypescountvisitorssegment") != null) {
					if (usage.get("filetypescountvisitorssegment") == null) { usage.put("filetypescountvisitorssegment", new LinkedHashMap<String,String>()); }
					usage.put("filetypescountvisitorssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescountvisitorssegment"),(LinkedHashMap<String,String>)output.get("filetypescountvisitorssegment")));
				}
				if (output.get("filetypescountvisitssegment") != null) {
					if (usage.get("filetypescountvisitssegment") == null) { usage.put("filetypescountvisitssegment", new LinkedHashMap<String,String>()); }
					usage.put("filetypescountvisitssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescountvisitssegment"),(LinkedHashMap<String,String>)output.get("filetypescountvisitssegment")));
				}
				if (output.get("filetypescounthitssegment") != null) {
					if (usage.get("filetypescounthitssegment") == null) { usage.put("filetypescounthitssegment", new LinkedHashMap<String,String>()); }
					usage.put("filetypescounthitssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescounthitssegment"),(LinkedHashMap<String,String>)output.get("filetypescounthitssegment")));
				}
				if (output.get("filetypescountclienthoststest") != null) {
					if (usage.get("filetypescountclienthoststest") == null) { usage.put("filetypescountclienthoststest", new LinkedHashMap<String,String>()); }
					usage.put("filetypescountclienthoststest", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescountclienthoststest"),(LinkedHashMap<String,String>)output.get("filetypescountclienthoststest")));
				}
				if (output.get("filetypescountvisitorstest") != null) {
					if (usage.get("filetypescountvisitorstest") == null) { usage.put("filetypescountvisitorstest", new LinkedHashMap<String,String>()); }
					usage.put("filetypescountvisitorstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescountvisitorstest"),(LinkedHashMap<String,String>)output.get("filetypescountvisitorstest")));
				}
				if (output.get("filetypescountvisitstest") != null) {
					if (usage.get("filetypescountvisitstest") == null) { usage.put("filetypescountvisitstest", new LinkedHashMap<String,String>()); }
					usage.put("filetypescountvisitstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescountvisitstest"),(LinkedHashMap<String,String>)output.get("filetypescountvisitstest")));
				}
				if (output.get("filetypescounthitstest") != null) {
					if (usage.get("filetypescounthitstest") == null) { usage.put("filetypescounthitstest", new LinkedHashMap<String,String>()); }
					usage.put("filetypescounthitstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescounthitstest"),(LinkedHashMap<String,String>)output.get("filetypescounthitstest")));
				}
				if (output.get("filetypescountclienthostssegments") != null) {
					if (usage.get("filetypescountclienthostssegments") == null) { usage.put("filetypescountclienthostssegments", new LinkedHashMap<String,String>()); }
					usage.put("filetypescountclienthostssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescountclienthostssegments"),(LinkedHashMap<String,String>)output.get("filetypescountclienthostssegments")));
				}
				if (output.get("filetypescountvisitorssegments") != null) {
					if (usage.get("filetypescountvisitorssegments") == null) { usage.put("filetypescountvisitorssegments", new LinkedHashMap<String,String>()); }
					usage.put("filetypescountvisitorssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescountvisitorssegments"),(LinkedHashMap<String,String>)output.get("filetypescountvisitorssegments")));
				}
				if (output.get("filetypescountvisitssegments") != null) {
					if (usage.get("filetypescountvisitssegments") == null) { usage.put("filetypescountvisitssegments", new LinkedHashMap<String,String>()); }
					usage.put("filetypescountvisitssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescountvisitssegments"),(LinkedHashMap<String,String>)output.get("filetypescountvisitssegments")));
				}
				if (output.get("filetypescounthitssegments") != null) {
					if (usage.get("filetypescounthitssegments") == null) { usage.put("filetypescounthitssegments", new LinkedHashMap<String,String>()); }
					usage.put("filetypescounthitssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescounthitssegments"),(LinkedHashMap<String,String>)output.get("filetypescounthitssegments")));
				}
				if (output.get("filetypescountclienthoststests") != null) {
					if (usage.get("filetypescountclienthoststests") == null) { usage.put("filetypescountclienthoststests", new LinkedHashMap<String,String>()); }
					usage.put("filetypescountclienthoststests", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescountclienthoststests"),(LinkedHashMap<String,String>)output.get("filetypescountclienthoststests")));
				}
				if (output.get("filetypescountvisitorstests") != null) {
					if (usage.get("filetypescountvisitorstests") == null) { usage.put("filetypescountvisitorstests", new LinkedHashMap<String,String>()); }
					usage.put("filetypescountvisitorstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescountvisitorstests"),(LinkedHashMap<String,String>)output.get("filetypescountvisitorstests")));
				}
				if (output.get("filetypescountvisitstests") != null) {
					if (usage.get("filetypescountvisitstests") == null) { usage.put("filetypescountvisitstests", new LinkedHashMap<String,String>()); }
					usage.put("filetypescountvisitstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescountvisitstests"),(LinkedHashMap<String,String>)output.get("filetypescountvisitstests")));
				}
				if (output.get("filetypescounthitstests") != null) {
					if (usage.get("filetypescounthitstests") == null) { usage.put("filetypescounthitstests", new LinkedHashMap<String,String>()); }
					usage.put("filetypescounthitstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescounthitstests"),(LinkedHashMap<String,String>)output.get("filetypescounthitstests")));
				}
				if (output.get("filetypescountclienthostssegmentstests") != null) {
					if (usage.get("filetypescountclienthostssegmentstests") == null) { usage.put("filetypescountclienthostssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("filetypescountclienthostssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescountclienthostssegmentstests"),(LinkedHashMap<String,String>)output.get("filetypescountclienthostssegmentstests")));
				}
				if (output.get("filetypescountvisitorssegmentstests") != null) {
					if (usage.get("filetypescountvisitorssegmentstests") == null) { usage.put("filetypescountvisitorssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("filetypescountvisitorssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescountvisitorssegmentstests"),(LinkedHashMap<String,String>)output.get("filetypescountvisitorssegmentstests")));
				}
				if (output.get("filetypescountvisitssegmentstests") != null) {
					if (usage.get("filetypescountvisitssegmentstests") == null) { usage.put("filetypescountvisitssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("filetypescountvisitssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescountvisitssegmentstests"),(LinkedHashMap<String,String>)output.get("filetypescountvisitssegmentstests")));
				}
				if (output.get("filetypescounthitssegmentstests") != null) {
					if (usage.get("filetypescounthitssegmentstests") == null) { usage.put("filetypescounthitssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("filetypescounthitssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("filetypescounthitssegmentstests"),(LinkedHashMap<String,String>)output.get("filetypescounthitssegmentstests")));
				}
			}
		}
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getLinks(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		String SQLtable;
		String SQLwhere;
		if (myrequest.getParameter("contentgroup").equals("-")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("link") + " and " + db.is_blank("contentgroup") + ""));
			SQLtable = "usagelog";
			SQLwhere = SQLwhereLink + " and requestid in (" + content_ids + ")";
		} else if ((! myrequest.getParameter("contentgroup").equals("")) && (! myrequest.getParameter("contentgroup").equals(" "))) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("link") + " and contentgroup=" + db.quote(myrequest.getParameter("contentgroup"))));
			SQLtable = "usagelog";
			SQLwhere = SQLwhereLink + " and requestid in (" + content_ids + ")";
		} else if (myrequest.getParameter("contenttype").equals("-")) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("link") + " and " + db.is_blank("contenttype") + ""));
			SQLtable = "usagelog";
			SQLwhere = SQLwhereLink + " and requestid in (" + content_ids + ")";
		} else if ((! myrequest.getParameter("contenttype").equals("")) && (! myrequest.getParameter("contenttype").equals(" "))) {
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("link") + " and contenttype=" + db.quote(myrequest.getParameter("contenttype"))));
			SQLtable = "usagelog";
			SQLwhere = SQLwhereLink + " and requestid in (" + content_ids + ")";
		} else {
			SQLtable = "usagelog";
			SQLwhere = SQLwhereLink;
		}
		if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsLinks + SQLcolumnsVariantsSegments, SQLwhere, "", "links", "countclienthosts+countvisitors+countvisits+counthits+countusersegments", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsLinks + SQLcolumnsVariantsUsertests, SQLwhere, "", "links", "countclienthosts+countvisitors+countvisits+counthits+countusertests", "requestclass:::requestid", true, false);
		} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsLinks + SQLcolumnsVariants, SQLwhere, "", "links", "countclienthosts+countvisitors+countvisits+counthits+countusersegments+countusertests", "requestclass:::requestid", true, false);
		} else {
			return getRankedData(mysession, myrequest, myconfig, db, logdb, database, SQLtable, SQLcolumnsLinks, SQLwhere, "", "links", "countclienthosts+countvisitors+countvisits+counthits", "requestclass:::requestid", true, false);
		}
	}



	public HashMap<String,LinkedHashMap<String,String>> getLinkgroups(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,LinkedHashMap<String,String>> usage = new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,HashMap<String,String>> rows = db.query_records("select linkgroup from linkgroups");
		rows.put("", new HashMap<String,String>());
		Iterator rowsIterator = rows.keySet().iterator();
		while (rowsIterator.hasNext()) {
			String key = "" + rowsIterator.next();
			HashMap<String,String> row = (HashMap<String,String>)rows.get(key);
			if (row.get("linkgroup") == null) row.put("linkgroup","");
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("link") + Common.SQLwhere_equals(db, " ", "contentgroup", "" + row.get("linkgroup"))));
			HashMap<String,LinkedHashMap<String,String>> output;
			if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("linkgroup")) + " as contentgroup" + SQLcolumnsVariantsSegments, SQLwhereLink + " and requestid in (" + content_ids + ")", "", "linkgroups", "countclienthosts+countvisitors+countvisits+counthits+countusersegments", "contentgroup", true, false);
			} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("linkgroup")) + " as contentgroup" + SQLcolumnsVariantsUsertests, SQLwhereLink + " and requestid in (" + content_ids + ")", "", "linkgroups", "countclienthosts+countvisitors+countvisits+counthits+countusertests", "contentgroup", true, false);
			} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("linkgroup")) + " as contentgroup" + SQLcolumnsVariants, SQLwhereLink + " and requestid in (" + content_ids + ")", "", "linkgroups", "countclienthosts+countvisitors+countvisits+counthits+countusersegments+countusertests", "contentgroup", true, false);
			} else {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("linkgroup")) + " as contentgroup", SQLwhereLink + " and requestid in (" + content_ids + ")", "", "linkgroups", "countclienthosts+countvisitors+countvisits+counthits", "contentgroup", true, false);
			}
			if (usage.get("linkgroupscountclienthosts") == null) { usage.put("linkgroupscountclienthosts", new LinkedHashMap<String,String>()); }
			if (usage.get("linkgroupscountvisitors") == null) { usage.put("linkgroupscountvisitors", new LinkedHashMap<String,String>()); }
			if (usage.get("linkgroupscountvisits") == null) { usage.put("linkgroupscountvisits", new LinkedHashMap<String,String>()); }
			if (usage.get("linkgroupscounthits") == null) { usage.put("linkgroupscounthits", new LinkedHashMap<String,String>()); }
			if (output.get("linkgroupscountclienthosts") != null) usage.put("linkgroupscountclienthosts", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscountclienthosts"),(LinkedHashMap<String,String>)output.get("linkgroupscountclienthosts")));
			if (output.get("linkgroupscountvisitors") != null) usage.put("linkgroupscountvisitors", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscountvisitors"),(LinkedHashMap<String,String>)output.get("linkgroupscountvisitors")));
			if (output.get("linkgroupscountvisits") != null) usage.put("linkgroupscountvisits", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscountvisits"),(LinkedHashMap<String,String>)output.get("linkgroupscountvisits")));
			if (output.get("linkgroupscounthits") != null) usage.put("linkgroupscounthits", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscounthits"),(LinkedHashMap<String,String>)output.get("linkgroupscounthits")));
			if (License.valid(db, myconfig, "experience")) {
				if (output.get("linkgroupscountclienthostssegment") != null) {
					if (usage.get("linkgroupscountclienthostssegment") == null) { usage.put("linkgroupscountclienthostssegment", new LinkedHashMap<String,String>()); }
					usage.put("linkgroupscountclienthostssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscountclienthostssegment"),(LinkedHashMap<String,String>)output.get("linkgroupscountclienthostssegment")));
				}
				if (output.get("linkgroupscountvisitorssegment") != null) {
					if (usage.get("linkgroupscountvisitorssegment") == null) { usage.put("linkgroupscountvisitorssegment", new LinkedHashMap<String,String>()); }
					usage.put("linkgroupscountvisitorssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscountvisitorssegment"),(LinkedHashMap<String,String>)output.get("linkgroupscountvisitorssegment")));
				}
				if (output.get("linkgroupscountvisitssegment") != null) {
					if (usage.get("linkgroupscountvisitssegment") == null) { usage.put("linkgroupscountvisitssegment", new LinkedHashMap<String,String>()); }
					usage.put("linkgroupscountvisitssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscountvisitssegment"),(LinkedHashMap<String,String>)output.get("linkgroupscountvisitssegment")));
				}
				if (output.get("linkgroupscounthitssegment") != null) {
					if (usage.get("linkgroupscounthitssegment") == null) { usage.put("linkgroupscounthitssegment", new LinkedHashMap<String,String>()); }
					usage.put("linkgroupscounthitssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscounthitssegment"),(LinkedHashMap<String,String>)output.get("linkgroupscounthitssegment")));
				}
				if (output.get("linkgroupscountclienthoststest") != null) {
					if (usage.get("linkgroupscountclienthoststest") == null) { usage.put("linkgroupscountclienthoststest", new LinkedHashMap<String,String>()); }
					usage.put("linkgroupscountclienthoststest", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscountclienthoststest"),(LinkedHashMap<String,String>)output.get("linkgroupscountclienthoststest")));
				}
				if (output.get("linkgroupscountvisitorstest") != null) {
					if (usage.get("linkgroupscountvisitorstest") == null) { usage.put("linkgroupscountvisitorstest", new LinkedHashMap<String,String>()); }
					usage.put("linkgroupscountvisitorstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscountvisitorstest"),(LinkedHashMap<String,String>)output.get("linkgroupscountvisitorstest")));
				}
				if (output.get("linkgroupscountvisitstest") != null) {
					if (usage.get("linkgroupscountvisitstest") == null) { usage.put("linkgroupscountvisitstest", new LinkedHashMap<String,String>()); }
					usage.put("linkgroupscountvisitstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscountvisitstest"),(LinkedHashMap<String,String>)output.get("linkgroupscountvisitstest")));
				}
				if (output.get("linkgroupscounthitstest") != null) {
					if (usage.get("linkgroupscounthitstest") == null) { usage.put("linkgroupscounthitstest", new LinkedHashMap<String,String>()); }
					usage.put("linkgroupscounthitstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscounthitstest"),(LinkedHashMap<String,String>)output.get("linkgroupscounthitstest")));
				}
				if (output.get("linkgroupscountclienthostssegments") != null) {
					if (usage.get("linkgroupscountclienthostssegments") == null) { usage.put("linkgroupscountclienthostssegments", new LinkedHashMap<String,String>()); }
					usage.put("linkgroupscountclienthostssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscountclienthostssegments"),(LinkedHashMap<String,String>)output.get("linkgroupscountclienthostssegments")));
				}
				if (output.get("linkgroupscountvisitorssegments") != null) {
					if (usage.get("linkgroupscountvisitorssegments") == null) { usage.put("linkgroupscountvisitorssegments", new LinkedHashMap<String,String>()); }
					usage.put("linkgroupscountvisitorssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscountvisitorssegments"),(LinkedHashMap<String,String>)output.get("linkgroupscountvisitorssegments")));
				}
				if (output.get("linkgroupscountvisitssegments") != null) {
					if (usage.get("linkgroupscountvisitssegments") == null) { usage.put("linkgroupscountvisitssegments", new LinkedHashMap<String,String>()); }
					usage.put("linkgroupscountvisitssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscountvisitssegments"),(LinkedHashMap<String,String>)output.get("linkgroupscountvisitssegments")));
				}
				if (output.get("linkgroupscounthitssegments") != null) {
					if (usage.get("linkgroupscounthitssegments") == null) { usage.put("linkgroupscounthitssegments", new LinkedHashMap<String,String>()); }
					usage.put("linkgroupscounthitssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscounthitssegments"),(LinkedHashMap<String,String>)output.get("linkgroupscounthitssegments")));
				}
				if (output.get("linkgroupscountclienthoststests") != null) {
					if (usage.get("linkgroupscountclienthoststests") == null) { usage.put("linkgroupscountclienthoststests", new LinkedHashMap<String,String>()); }
					usage.put("linkgroupscountclienthoststests", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscountclienthoststests"),(LinkedHashMap<String,String>)output.get("linkgroupscountclienthoststests")));
				}
				if (output.get("linkgroupscountvisitorstests") != null) {
					if (usage.get("linkgroupscountvisitorstests") == null) { usage.put("linkgroupscountvisitorstests", new LinkedHashMap<String,String>()); }
					usage.put("linkgroupscountvisitorstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscountvisitorstests"),(LinkedHashMap<String,String>)output.get("linkgroupscountvisitorstests")));
				}
				if (output.get("linkgroupscountvisitstests") != null) {
					if (usage.get("linkgroupscountvisitstests") == null) { usage.put("linkgroupscountvisitstests", new LinkedHashMap<String,String>()); }
					usage.put("linkgroupscountvisitstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscountvisitstests"),(LinkedHashMap<String,String>)output.get("linkgroupscountvisitstests")));
				}
				if (output.get("linkgroupscounthitstests") != null) {
					if (usage.get("linkgroupscounthitstests") == null) { usage.put("linkgroupscounthitstests", new LinkedHashMap<String,String>()); }
					usage.put("linkgroupscounthitstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscounthitstests"),(LinkedHashMap<String,String>)output.get("linkgroupscounthitstests")));
				}
				if (output.get("linkgroupscountclienthostssegmentstests") != null) {
					if (usage.get("linkgroupscountclienthostssegmentstests") == null) { usage.put("linkgroupscountclienthostssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("linkgroupscountclienthostssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscountclienthostssegmentstests"),(LinkedHashMap<String,String>)output.get("linkgroupscountclienthostssegmentstests")));
				}
				if (output.get("linkgroupscountvisitorssegmentstests") != null) {
					if (usage.get("linkgroupscountvisitorssegmentstests") == null) { usage.put("linkgroupscountvisitorssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("linkgroupscountvisitorssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscountvisitorssegmentstests"),(LinkedHashMap<String,String>)output.get("linkgroupscountvisitorssegmentstests")));
				}
				if (output.get("linkgroupscountvisitssegmentstests") != null) {
					if (usage.get("linkgroupscountvisitssegmentstests") == null) { usage.put("linkgroupscountvisitssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("linkgroupscountvisitssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscountvisitssegmentstests"),(LinkedHashMap<String,String>)output.get("linkgroupscountvisitssegmentstests")));
				}
				if (output.get("linkgroupscounthitssegmentstests") != null) {
					if (usage.get("linkgroupscounthitssegmentstests") == null) { usage.put("linkgroupscounthitssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("linkgroupscounthitssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("linkgroupscounthitssegmentstests"),(LinkedHashMap<String,String>)output.get("linkgroupscounthitssegmentstests")));
				}
			}
		}
		return usage;
	}



	public HashMap<String,LinkedHashMap<String,String>> getLinktypes(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,LinkedHashMap<String,String>> usage = new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,HashMap<String,String>> rows = db.query_records("select linktype from linktypes");
		rows.put("", new HashMap<String,String>());
		Iterator rowsIterator = rows.keySet().iterator();
		while (rowsIterator.hasNext()) {
			String key = "" + rowsIterator.next();
			HashMap<String,String> row = (HashMap<String,String>)rows.get(key);
			if (row.get("linktype") == null) row.put("linktype","");
			String content_ids = Common.SQL_list_row_ids(db, db.query_records("select id from content where contentclass=" + db.quote("link") + Common.SQLwhere_equals(db, " ", "contenttype", "" + row.get("linktype"))));
			HashMap<String,LinkedHashMap<String,String>> output;
			if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("linktype")) + " as contenttype" + SQLcolumnsVariantsSegments, SQLwhereLink + " and requestid in (" + content_ids + ")", "", "linktypes", "countclienthosts+countvisitors+countvisits+counthits+countusersegments", "contenttype", true, false);
			} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusertests"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("linktype")) + " as contenttype" + SQLcolumnsVariantsUsertests, SQLwhereLink + " and requestid in (" + content_ids + ")", "", "linktypes", "countclienthosts+countvisitors+countvisits+counthits+countusertests", "contenttype", true, false);
			} else if (License.valid(db, myconfig, "experience") && (mysession.get("admin_usage_details").equals("+countusersegments+countusertests"))) {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("linktype")) + " as contenttype" + SQLcolumnsVariants, SQLwhereLink + " and requestid in (" + content_ids + ")", "", "linktypes", "countclienthosts+countvisitors+countvisits+counthits+countusersegments+countusertests", "contenttype", true, false);
			} else {
				output = getRankedData(mysession, myrequest, myconfig, db, logdb, database, "usagelog", logdb.quote("" + row.get("linktype")) + " as contenttype", SQLwhereLink + " and requestid in (" + content_ids + ")", "", "linktypes", "countclienthosts+countvisitors+countvisits+counthits", "contenttype", true, false);
			}
			if (usage.get("linktypescountclienthosts") == null) { usage.put("linktypescountclienthosts", new LinkedHashMap<String,String>()); }
			if (usage.get("linktypescountvisitors") == null) { usage.put("linktypescountvisitors", new LinkedHashMap<String,String>()); }
			if (usage.get("linktypescountvisits") == null) { usage.put("linktypescountvisits", new LinkedHashMap<String,String>()); }
			if (usage.get("linktypescounthits") == null) { usage.put("linktypescounthits", new LinkedHashMap<String,String>()); }
			if (output.get("linktypescountclienthosts") != null) usage.put("linktypescountclienthosts", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescountclienthosts"),(LinkedHashMap<String,String>)output.get("linktypescountclienthosts")));
			if (output.get("linktypescountvisitors") != null) usage.put("linktypescountvisitors", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescountvisitors"),(LinkedHashMap<String,String>)output.get("linktypescountvisitors")));
			if (output.get("linktypescountvisits") != null) usage.put("linktypescountvisits", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescountvisits"),(LinkedHashMap<String,String>)output.get("linktypescountvisits")));
			if (output.get("linktypescounthits") != null) usage.put("linktypescounthits", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescounthits"),(LinkedHashMap<String,String>)output.get("linktypescounthits")));
			if (License.valid(db, myconfig, "experience")) {
				if (output.get("linktypescountclienthostssegment") != null) {
					if (usage.get("linktypescountclienthostssegment") == null) { usage.put("linktypescountclienthostssegment", new LinkedHashMap<String,String>()); }
					usage.put("linktypescountclienthostssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescountclienthostssegment"),(LinkedHashMap<String,String>)output.get("linktypescountclienthostssegment")));
				}
				if (output.get("linktypescountvisitorssegment") != null) {
					if (usage.get("linktypescountvisitorssegment") == null) { usage.put("linktypescountvisitorssegment", new LinkedHashMap<String,String>()); }
					usage.put("linktypescountvisitorssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescountvisitorssegment"),(LinkedHashMap<String,String>)output.get("linktypescountvisitorssegment")));
				}
				if (output.get("linktypescountvisitssegment") != null) {
					if (usage.get("linktypescountvisitssegment") == null) { usage.put("linktypescountvisitssegment", new LinkedHashMap<String,String>()); }
					usage.put("linktypescountvisitssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescountvisitssegment"),(LinkedHashMap<String,String>)output.get("linktypescountvisitssegment")));
				}
				if (output.get("linktypescounthitssegment") != null) {
					if (usage.get("linktypescounthitssegment") == null) { usage.put("linktypescounthitssegment", new LinkedHashMap<String,String>()); }
					usage.put("linktypescounthitssegment", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescounthitssegment"),(LinkedHashMap<String,String>)output.get("linktypescounthitssegment")));
				}
				if (output.get("linktypescountclienthoststest") != null) {
					if (usage.get("linktypescountclienthoststest") == null) { usage.put("linktypescountclienthoststest", new LinkedHashMap<String,String>()); }
					usage.put("linktypescountclienthoststest", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescountclienthoststest"),(LinkedHashMap<String,String>)output.get("linktypescountclienthoststest")));
				}
				if (output.get("linktypescountvisitorstest") != null) {
					if (usage.get("linktypescountvisitorstest") == null) { usage.put("linktypescountvisitorstest", new LinkedHashMap<String,String>()); }
					usage.put("linktypescountvisitorstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescountvisitorstest"),(LinkedHashMap<String,String>)output.get("linktypescountvisitorstest")));
				}
				if (output.get("linktypescountvisitstest") != null) {
					if (usage.get("linktypescountvisitstest") == null) { usage.put("linktypescountvisitstest", new LinkedHashMap<String,String>()); }
					usage.put("linktypescountvisitstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescountvisitstest"),(LinkedHashMap<String,String>)output.get("linktypescountvisitstest")));
				}
				if (output.get("linktypescounthitstest") != null) {
					if (usage.get("linktypescounthitstest") == null) { usage.put("linktypescounthitstest", new LinkedHashMap<String,String>()); }
					usage.put("linktypescounthitstest", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescounthitstest"),(LinkedHashMap<String,String>)output.get("linktypescounthitstest")));
				}
				if (output.get("linktypescountclienthostssegments") != null) {
					if (usage.get("linktypescountclienthostssegments") == null) { usage.put("linktypescountclienthostssegments", new LinkedHashMap<String,String>()); }
					usage.put("linktypescountclienthostssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescountclienthostssegments"),(LinkedHashMap<String,String>)output.get("linktypescountclienthostssegments")));
				}
				if (output.get("linktypescountvisitorssegments") != null) {
					if (usage.get("linktypescountvisitorssegments") == null) { usage.put("linktypescountvisitorssegments", new LinkedHashMap<String,String>()); }
					usage.put("linktypescountvisitorssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescountvisitorssegments"),(LinkedHashMap<String,String>)output.get("linktypescountvisitorssegments")));
				}
				if (output.get("linktypescountvisitssegments") != null) {
					if (usage.get("linktypescountvisitssegments") == null) { usage.put("linktypescountvisitssegments", new LinkedHashMap<String,String>()); }
					usage.put("linktypescountvisitssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescountvisitssegments"),(LinkedHashMap<String,String>)output.get("linktypescountvisitssegments")));
				}
				if (output.get("linktypescounthitssegments") != null) {
					if (usage.get("linktypescounthitssegments") == null) { usage.put("linktypescounthitssegments", new LinkedHashMap<String,String>()); }
					usage.put("linktypescounthitssegments", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescounthitssegments"),(LinkedHashMap<String,String>)output.get("linktypescounthitssegments")));
				}
				if (output.get("linktypescountclienthoststests") != null) {
					if (usage.get("linktypescountclienthoststests") == null) { usage.put("linktypescountclienthoststests", new LinkedHashMap<String,String>()); }
					usage.put("linktypescountclienthoststests", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescountclienthoststests"),(LinkedHashMap<String,String>)output.get("linktypescountclienthoststests")));
				}
				if (output.get("linktypescountvisitorstests") != null) {
					if (usage.get("linktypescountvisitorstests") == null) { usage.put("linktypescountvisitorstests", new LinkedHashMap<String,String>()); }
					usage.put("linktypescountvisitorstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescountvisitorstests"),(LinkedHashMap<String,String>)output.get("linktypescountvisitorstests")));
				}
				if (output.get("linktypescountvisitstests") != null) {
					if (usage.get("linktypescountvisitstests") == null) { usage.put("linktypescountvisitstests", new LinkedHashMap<String,String>()); }
					usage.put("linktypescountvisitstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescountvisitstests"),(LinkedHashMap<String,String>)output.get("linktypescountvisitstests")));
				}
				if (output.get("linktypescounthitstests") != null) {
					if (usage.get("linktypescounthitstests") == null) { usage.put("linktypescounthitstests", new LinkedHashMap<String,String>()); }
					usage.put("linktypescounthitstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescounthitstests"),(LinkedHashMap<String,String>)output.get("linktypescounthitstests")));
				}
				if (output.get("linktypescountclienthostssegmentstests") != null) {
					if (usage.get("linktypescountclienthostssegmentstests") == null) { usage.put("linktypescountclienthostssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("linktypescountclienthostssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescountclienthostssegmentstests"),(LinkedHashMap<String,String>)output.get("linktypescountclienthostssegmentstests")));
				}
				if (output.get("linktypescountvisitorssegmentstests") != null) {
					if (usage.get("linktypescountvisitorssegmentstests") == null) { usage.put("linktypescountvisitorssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("linktypescountvisitorssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescountvisitorssegmentstests"),(LinkedHashMap<String,String>)output.get("linktypescountvisitorssegmentstests")));
				}
				if (output.get("linktypescountvisitssegmentstests") != null) {
					if (usage.get("linktypescountvisitssegmentstests") == null) { usage.put("linktypescountvisitssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("linktypescountvisitssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescountvisitssegmentstests"),(LinkedHashMap<String,String>)output.get("linktypescountvisitssegmentstests")));
				}
				if (output.get("linktypescounthitssegmentstests") != null) {
					if (usage.get("linktypescounthitssegmentstests") == null) { usage.put("linktypescounthitssegmentstests", new LinkedHashMap<String,String>()); }
					usage.put("linktypescounthitssegmentstests", Common.array_merge((LinkedHashMap<String,String>)usage.get("linktypescounthitssegmentstests"),(LinkedHashMap<String,String>)output.get("linktypescounthitssegmentstests")));
				}
			}
		}
		return usage;
	}



	private String columns2group(String columns) {
		String group = "" + columns;
		group = group.replaceAll("^('[^']*') as ([a-zA-Z0-9]+)$", "");
		group = group.replaceAll("^([0-9]+) as ([a-zA-Z0-9]+)$", "");
		group = group.replaceAll("^([^,]+?) as ([a-zA-Z0-9]+)$", "$2");

		group = group.replaceAll("^('[^']*') as ([a-zA-Z0-9]+), *", "");
		group = group.replaceAll("^([0-9]+) as ([a-zA-Z0-9]+), *", "");
		group = group.replaceAll("^([^,]+?) as ([a-zA-Z0-9]+), *", "$2, ");

		group = group.replaceAll(", *('[^']*') as ([a-zA-Z0-9]+)", "");
		group = group.replaceAll(", *([0-9]+) as ([a-zA-Z0-9]+)", "");
		group = group.replaceAll(", *([^,]+?) as ([a-zA-Z0-9]+)", ", $2");
		return group;
	}



	private String columns2summarised(String columns) {
		String summarised = "" + columns;
		summarised = summarised.replaceAll("^('[^']*') as ([a-zA-Z0-9]+)$", "$2");
		summarised = summarised.replaceAll("^([0-9]+) as ([a-zA-Z0-9]+)$", "$2");
		summarised = summarised.replaceAll("^([^,]+?) as ([a-zA-Z0-9]+)$", "$2");

		summarised = summarised.replaceAll("^('[^']*') as ([a-zA-Z0-9]+), *", "$2, ");
		summarised = summarised.replaceAll("^([0-9]+) as ([a-zA-Z0-9]+), *", "$2, ");
		summarised = summarised.replaceAll("^([^,]+?) as ([a-zA-Z0-9]+), *", "$2, ");

		summarised = summarised.replaceAll(", *('[^']*') as ([a-zA-Z0-9]+)", ", $2");
		summarised = summarised.replaceAll(", *([0-9]+) as ([a-zA-Z0-9]+)", ", $2");
		summarised = summarised.replaceAll(", *([^,]+?) as ([a-zA-Z0-9]+)", ", $2");

		summarised = summarised.replaceAll(", usertests", "");
		summarised = summarised.replaceAll(", usersegments", "");

		if (summarised.equals("requestid, requestclass")) summarised = "requestid, requestclass, requestdatabase";
		if (summarised.equals("contentgroup")) summarised = "requestid, requestclass, requestdatabase";
		if (summarised.equals("contenttype")) summarised = "requestid, requestclass, requestdatabase";
		if (summarised.equals("clienthost, visitorid, sessionid")) summarised = "clienthost";
		if (summarised.equals("visitorid, clienthost, sessionid")) summarised = "visitorid";
		if (summarised.equals("sessionid, clienthost, visitorid")) summarised = "sessionid";
		if (summarised.equals("clientbrowser")) summarised = "clientbrowser, clientversion";
		if (summarised.equals("clientos")) summarised = "clientos, clientosversion";
		if (summarised.equals("clientdevice")) summarised = "clientdevice, clientdeviceversion";
		if (summarised.equals("refererhost")) summarised = "refererhost, refererpath";
		if (summarised.equals("usergroup")) summarised = "clientusername";
		if (summarised.equals("usertype")) summarised = "clientusername";
		if (summarised.equals("usersegment")) summarised = "usersegments";
		if (summarised.equals("usertest")) summarised = "usertests";
		if (summarised.equals("datetimehour")) summarised = "datetimeyear, datetimemonth, datetimeday, datetimehour";
		if (summarised.equals("datetimeday")) summarised = "datetimeyear, datetimemonth, datetimeday";
		if (summarised.equals("datetimeweekday")) summarised = "datetimeyear, datetimemonth, datetimeday";
		if (summarised.equals("datetimeweek")) summarised = "datetimeyear, datetimeweek";
		if (summarised.equals("datetimemonth")) summarised = "datetimeyear, datetimemonth";
		if (summarised.equals("datetimeyear")) summarised = "datetimeyear, datetimemonth";
		return summarised;
	}



	private String SQLclienthosts(DB db, String database, String columns, String from, String where, String order) {
		String group = columns2group(columns);
		String summarised = columns2summarised(columns);
		if (order.equals("")) {
			order = "1 desc";
		}
		String SQL = "";
		if ((DB.db_type(database).equals("access")) || (DB.db_type(database).equals("mssql"))) {
			if (! group.equals("")) {
				SQL = SQLselectClienthostsAccess + ", " + columns + ", 'details' as source from (select " + columns + ", clienthost from " + from + " where " + db.is_blank("summarised") + " and " + db.is_not_blank("clienthost") + " and " + where + " group by " + group + ", clienthost) as clienthosts group by " + group;
				SQL += " union all ";
				SQL += SQLselectClienthostsAccess2 + ", " + columns + ", 'summarised' as source from " + from + " where " + "summarised=" + db.quote(summarised) + " and " + db.is_blank("clienthost") + " and " + where + " group by " + group;
				SQL += " order by " + order;
			} else {
				SQL = SQLselectClienthostsAccess + ", " + columns + ", 'details' as source from (select " + columns + ", clienthost from " + from + " where " + db.is_blank("summarised") + " and " + db.is_not_blank("clienthost") + " and " + where + " group by 1, clienthost) as clienthosts group by 2";
				SQL += " union all ";
				SQL += SQLselectClienthostsAccess2 + ", " + columns + ", 'summarised' as source from " + from + " where " + "summarised=" + db.quote(summarised) + " and " + db.is_blank("clienthost") + " and " + where + " group by 2";
				SQL += " order by " + order;
			}
		} else {
			if (! group.equals("")) {
				SQL = SQLselectClienthostsMySQL + ", " + columns + ", 'details' as source from " + from + " where " + db.is_blank("summarised") + " and " + db.is_not_blank("clienthost") + " and " + where + " group by " + group;
				SQL += " union all ";
				SQL += SQLselectClienthostsMySQL2 + ", " + columns + ", 'summarised' as source from " + from + " where " + "summarised=" + db.quote(summarised) + " and " + db.is_blank("clienthost") + " and " + where + " group by " + group;
				SQL += " order by " + order;
			} else {
				SQL = SQLselectClienthostsMySQL + ", " + columns + ", 'details' as source from " + from + " where " + db.is_blank("summarised") + " and " + db.is_not_blank("clienthost") + " and " + where + " group by 2";
				SQL += " union all ";
				SQL += SQLselectClienthostsMySQL2 + ", " + columns + ", 'summarised' as source from " + from + " where " + "summarised=" + db.quote(summarised) + " and " + db.is_blank("clienthost") + " and " + where + " group by 2";
				SQL += " order by " + order;
			}
		}
		return SQL;
	}



	private String SQLvisitors(DB db, String database, String columns, String from, String where, String order) {
		String group = columns2group(columns);
		String summarised = columns2summarised(columns);
		if (order.equals("")) {
			order = "1 desc";
		}
		String SQL = "";
		if ((DB.db_type(database).equals("access")) || (DB.db_type(database).equals("mssql"))) {
			if (! group.equals("")) {
				SQL = SQLselectVisitorsAccess + ", " + columns + ", 'details' as source from (select " + columns + ", visitorid from " + from + " where " + db.is_blank("summarised") + " and " + db.is_not_blank("visitorid") + " and " + where + " group by " + group + ", visitorid) as visitorids group by " + group;
				SQL += " union all ";
				SQL += SQLselectVisitorsAccess2 + ", " + columns + ", 'summarised' as source from " + from + " where " + "summarised=" + db.quote(summarised) + " and " + db.is_blank("visitorid") + " and " + where + " group by " + group;
				SQL += " order by " + order;
			} else {
				SQL = SQLselectVisitorsAccess + ", " + columns + ", 'details' as source from (select " + columns + ", visitorid from " + from + " where " + db.is_blank("summarised") + " and " + db.is_not_blank("visitorid") + " and " + where + " group by 1, visitorid) as visitorids group by 2";
				SQL += " union all ";
				SQL += SQLselectVisitorsAccess2 + ", " + columns + ", 'summarised' as source from " + from + " where " + "summarised=" + db.quote(summarised) + " and " + db.is_blank("visitorid") + " and " + where + " group by 2";
				SQL += " order by " + order;
			}
		} else {
			if (! group.equals("")) {
				SQL = SQLselectVisitorsMySQL + ", " + columns + ", 'details' as source from " + from + " where " + db.is_blank("summarised") + " and " + db.is_not_blank("visitorid") + " and " + where + " group by " + group;
				SQL += " union all ";
				SQL += SQLselectVisitorsMySQL2 + ", " + columns + ", 'summarised' as source from " + from + " where " + "summarised=" + db.quote(summarised) + " and " + db.is_blank("visitorid") + " and " + where + " group by " + group;
				SQL += " order by " + order;
			} else {
				SQL = SQLselectVisitorsMySQL + ", " + columns + ", 'details' as source from " + from + " where " + db.is_blank("summarised") + " and " + db.is_not_blank("visitorid") + " and " + where + " group by 2";
				SQL += " union all ";
				SQL += SQLselectVisitorsMySQL2 + ", " + columns + ", 'summarised' as source from " + from + " where " + "summarised=" + db.quote(summarised) + " and " + db.is_blank("visitorid") + " and " + where + " group by 2";
				SQL += " order by " + order;
			}
		}
		return SQL;
	}



	private String SQLvisits(DB db, String database, String columns, String from, String where, String order) {
		String group = columns2group(columns);
		String summarised = columns2summarised(columns);
		if (order.equals("")) {
			order = "1 desc";
		}
		String SQL = "";
		if ((DB.db_type(database).equals("access")) || (DB.db_type(database).equals("mssql"))) {
			if (! group.equals("")) {
				SQL = SQLselectVisitsAccess + ", " + columns + ", 'details' as source from (select " + columns + ", sessionid from " + from + " where " + db.is_blank("summarised") + " and " + db.is_not_blank("sessionid") + " and " + where + " group by " + group + ", sessionid) as sessionids group by " + group;
				SQL += " union all ";
				SQL += SQLselectVisitsAccess2 + ", " + columns + ", 'summarised' as source from " + from + " where " + "summarised=" + db.quote(summarised) + " and " + db.is_blank("sessionid") + " and " + where + " group by " + group;
				SQL += " order by " + order;
			} else {
				SQL = SQLselectVisitsAccess + ", " + columns + ", 'details' as source from (select " + columns + ", sessionid from " + from + " where " + db.is_blank("summarised") + " and " + db.is_not_blank("sessionid") + " and " + where + " group by 1, sessionid) as sessionids group by 2";
				SQL += " union all ";
				SQL += SQLselectVisitsAccess2 + ", " + columns + ", 'summarised' as source from " + from + " where " + "summarised=" + db.quote(summarised) + " and " + db.is_blank("sessionid") + " and " + where + " group by 2";
				SQL += " order by " + order;
			}
		} else {
			if (! group.equals("")) {
				SQL = SQLselectVisitsMySQL + ", " + columns + ", 'details' as source from " + from + " where " + db.is_blank("summarised") + " and " + db.is_not_blank("sessionid") + " and " + where + " group by " + group;
				SQL += " union all ";
				SQL += SQLselectVisitsMySQL2 + ", " + columns + ", 'summarised' as source from " + from + " where " + "summarised=" + db.quote(summarised) + " and " + db.is_blank("sessionid") + " and " + where + " group by " + group;
				SQL += " order by " + order;
			} else {
				SQL = SQLselectVisitsMySQL + ", " + columns + ", 'details' as source from " + from + " where " + db.is_blank("summarised") + " and " + db.is_not_blank("sessionid") + " and " + where + " group by 2";
				SQL += " union all ";
				SQL += SQLselectVisitsMySQL2 + ", " + columns + ", 'summarised' as source from " + from + " where " + "summarised=" + db.quote(summarised) + " and " + db.is_blank("sessionid") + " and " + where + " group by 2";
				SQL += " order by " + order;
			}
		}
		return SQL;
	}



	private String SQLvisitsduration(String columns, String from, String where, String order) {
		String group = columns2group(columns);
		if (order.equals("")) {
			order = "1 desc";
		}
		String SQL = "";
		if (! group.equals("")) {
			SQL = SQLselectVisitsAccess + ", " + columns + ", max(sessionduration) as durations from " + from + " where " + where + " group by " + group + " order by " + order;
		} else {
			SQL = SQLselectVisitsAccess + ", " + columns + ", max(sessionduration) as durations from " + from + " where " + where + " group by 2 order by " + order;
		}
		return SQL;
	}



	private String SQLpageviews(DB db, String columns, String from, String where, String order) {
		String group = columns2group(columns);
		String summarised = columns2summarised(columns);
		if (order.equals("")) {
			order = "1 desc";
		}
		String SQL = "";
		if (! group.equals("")) {
			SQL = SQLselectPageViews + ", " + columns + ", 'details' as source from " + from + " where " + db.is_blank("summarised") + " and " + SQLwherePage + " and " + where + " group by " + group;
			SQL += " union all ";
			SQL += SQLselectPageViews2 + ", " + columns + ", 'summarised' as source from " + from + " where " + "summarised=" + db.quote(summarised) + " and " + where + " group by " + group;
			SQL += " order by " + order;
		} else {
			SQL = SQLselectPageViews + ", " + columns + ", 'details' as source from " + from + " where " + db.is_blank("summarised") + " and " + SQLwherePage + " and " + where + " group by 2";
			SQL += " union all ";
			SQL += SQLselectPageViews2 + ", " + columns + ", 'summarised' as source from " + from + " where " + "summarised=" + db.quote(summarised) + " and " + where + " group by 2";
			SQL += " order by " + order;
		}
		return SQL;
	}



	private String SQLhits(DB db, String columns, String from, String where, String order) {
		String group = columns2group(columns);
		String summarised = columns2summarised(columns);
		if (order.equals("")) {
			order = "1 desc";
		}
		String SQL = "";
		if (! group.equals("")) {
			SQL = SQLselectHits + ", " + columns + ", 'details' as source from " + from + " where " + db.is_blank("summarised") + " and " + where + " group by " + group;
			SQL += " union all ";
			SQL += SQLselectHits2 + ", " + columns + ", 'summarised' as source from " + from + " where " + "summarised=" + db.quote(summarised) + " and " + where + " group by " + group;
			SQL += " order by " + order;
		} else {
			SQL = SQLselectHits + ", " + columns + ", 'details' as source from " + from + " where " + db.is_blank("summarised") + " and " + where + " group by 2";
			SQL += " union all ";
			SQL += SQLselectHits2 + ", " + columns + ", 'summarised' as source from " + from + " where " + "summarised=" + db.quote(summarised) + " and " + where + " group by 2";
			SQL += " order by " + order;
		}
		return SQL;
	}



	private void setSessionLimitFromRequest(Session mysession, Request myrequest) {
		if (myrequest.getParameter("limit").equals(" ")) {
			mysession.set("admin_usage_limit", "");
		} else if (! myrequest.getParameter("limit").equals("")) {
			mysession.set("admin_usage_limit", html.encodeHtmlEntities(myrequest.getParameter("limit")));
		}
		if (myrequest.parameterExists("details")) {
			mysession.set("admin_usage_details", html.encodeHtmlEntities(myrequest.getParameter("details")));
		}
	}



	private void setSessionPeriodFromRequest(Session mysession, Request myrequest) {
		if (! myrequest.getParameter("period").equals("")) {
			mysession.set("admin_usage_period", html.encodeHtmlEntities(myrequest.getParameter("period")));
		}
		if (! myrequest.getParameter("period_start").equals("")) {
			mysession.set("admin_usage_period_start", html.encodeHtmlEntities(myrequest.getParameter("period_start")));
		} else if (! myrequest.getParameter("period").equals("")) {
			mysession.set("admin_usage_period_start", "");
		}
		if (! myrequest.getParameter("period_end").equals("")) {
			mysession.set("admin_usage_period_end", html.encodeHtmlEntities(myrequest.getParameter("period_end")));
		} else if (! myrequest.getParameter("period").equals("")) {
			mysession.set("admin_usage_period_end", "");
		}
	}



	private String SQLwhereNotRobots(DB db, String SQLwhere) {
		UsageLog usagelog = new UsageLog();
		StringBuffer clientbrowsers = new StringBuffer();
		for (int i=0; i<usagelog.robotName.length; i++) {
			if (! ("" + clientbrowsers).equals("")) {
				clientbrowsers.append(",");
			}
			clientbrowsers.append(db.quote(usagelog.robotName[i]));
		}
		if (! SQLwhere.equals("")) {
			SQLwhere = SQLwhere + " and ";
		}
		SQLwhere = SQLwhere + "(" + db.is_blank("clientbrowser") + " or (clientbrowser not in (" + clientbrowsers + ")))";
		return SQLwhere;
	}



	private String SQLwherePeriod(DB db, Session mysession, Date mynow, String prefix) {
		String SQLwhere = "";
		if ((! mysession.get("admin_usage_period_start").equals("")) || (! mysession.get("admin_usage_period_end").equals(""))) {
			if (! mysession.get("admin_usage_period_start").equals("")) {
				if (! SQLwhere.equals("")) SQLwhere += " and ";
				SQLwhere += "" + prefix + "datetimefull >= " + db.quote(Common.SQL_clean(mysession.get("admin_usage_period_start")));
			}
			if (! mysession.get("admin_usage_period_end").equals("")) {
				if (! SQLwhere.equals("")) SQLwhere += " and ";
				SQLwhere += "" + prefix + "datetimefull <= " + db.quote(Common.SQL_clean(mysession.get("admin_usage_period_end")));
			}
		} else if (mysession.get("admin_usage_period").equals("now")) {
			Calendar periodstart = Calendar.getInstance();
			periodstart.setTime(mynow);
			periodstart.add(Calendar.SECOND, -30*60);	// 30 minutes
			Calendar periodend = Calendar.getInstance();
			periodend.setTime(mynow);
			SQLwhere = "" + prefix + "datetimefull > " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodstart.getTime()));
			SQLwhere = SQLwhere + " and " + prefix + "datetimefull <= " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodend.getTime()));
		} else if (mysession.get("admin_usage_period").equals("today")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			SQLwhere = "" + prefix + "datetimeyear = " + period.get(Calendar.YEAR) + " and " + prefix + "datetimemonth = " + (period.get(Calendar.MONTH)+1) + " and " + prefix + "datetimeday = " + period.get(Calendar.DAY_OF_MONTH);
		} else if (mysession.get("admin_usage_period").equals("last24hours")) {
			Calendar periodstart = Calendar.getInstance();
			periodstart.setTime(mynow);
			periodstart.add(Calendar.SECOND, -24*60*60);
			Calendar periodend = Calendar.getInstance();
			periodend.setTime(mynow);
			SQLwhere = "" + prefix + "datetimefull > " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodstart.getTime()));
			SQLwhere = SQLwhere + " and " + prefix + "datetimefull <= " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodend.getTime()));
		} else if (mysession.get("admin_usage_period").equals("yesterday")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.SECOND, -24*60*60);
			SQLwhere = "" + prefix + "datetimeyear = " + period.get(Calendar.YEAR) + " and " + prefix + "datetimemonth = " + (period.get(Calendar.MONTH)+1) + " and " + prefix + "datetimeday = " + period.get(Calendar.DAY_OF_MONTH);
		} else if (mysession.get("admin_usage_period").equals("thisweek")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			long week = period.get(Calendar.WEEK_OF_YEAR);
			SQLwhere = "" + prefix + "datetimeyear = " + period.get(Calendar.YEAR) + " and " + prefix + "datetimeweek = " + week;
		} else if (mysession.get("admin_usage_period").equals("last7days")) {
			Calendar periodstart = Calendar.getInstance();
			periodstart.setTime(mynow);
			periodstart.add(Calendar.SECOND, -7*24*60*60);
			Calendar periodend = Calendar.getInstance();
			periodend.setTime(mynow);
			SQLwhere = "" + prefix + "datetimefull > " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodstart.getTime()));
			SQLwhere = SQLwhere + " and " + prefix + "datetimefull <= " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodend.getTime()));
		} else if (mysession.get("admin_usage_period").equals("lastweek")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.SECOND, -7*24*60*60);
			long week = period.get(Calendar.WEEK_OF_YEAR);
			SQLwhere = "" + prefix + "datetimeyear = " + period.get(Calendar.YEAR) + " and " + prefix + "datetimeweek = " + week;
		} else if (mysession.get("admin_usage_period").equals("last14days")) {
			Calendar periodstart = Calendar.getInstance();
			periodstart.setTime(mynow);
			periodstart.add(Calendar.SECOND, -14*24*60*60);
			Calendar periodend = Calendar.getInstance();
			periodend.setTime(mynow);
			SQLwhere = "" + prefix + "datetimefull > " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodstart.getTime()));
			SQLwhere = SQLwhere + " and " + prefix + "datetimefull <= " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodend.getTime()));
		} else if (mysession.get("admin_usage_period").equals("thismonth")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			SQLwhere = "" + prefix + "datetimeyear = " + period.get(Calendar.YEAR) + " and " + prefix + "datetimemonth = " + (period.get(Calendar.MONTH)+1);
		} else if (mysession.get("admin_usage_period").equals("last30days")) {
			Calendar periodstart = Calendar.getInstance();
			periodstart.setTime(mynow);
			periodstart.add(Calendar.MONTH, -1);
			Calendar periodend = Calendar.getInstance();
			periodend.setTime(mynow);
			SQLwhere = "" + prefix + "datetimefull > " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodstart.getTime()));
			SQLwhere = SQLwhere + " and " + prefix + "datetimefull <= " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodend.getTime()));
		} else if (mysession.get("admin_usage_period").equals("lastmonth")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.MONTH, -1);
			SQLwhere = "" + prefix + "datetimeyear = " + period.get(Calendar.YEAR) + " and " + prefix + "datetimemonth = " + (period.get(Calendar.MONTH)+1);
		} else if (mysession.get("admin_usage_period").equals("thisquarter")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			long period_mon = (long)(Math.floor(period.get(Calendar.MONTH)/3)*3)+1;
			SQLwhere = "" + prefix + "datetimeyear = " + period.get(Calendar.YEAR) + " and (" + prefix + "datetimemonth >= " + period_mon + " and " + prefix + "datetimemonth <= " + (period_mon+2) + ")";
		} else if (mysession.get("admin_usage_period").equals("last3months")) {
			Calendar periodstart = Calendar.getInstance();
			periodstart.setTime(mynow);
			periodstart.add(Calendar.MONTH, -3);
			Calendar periodend = Calendar.getInstance();
			periodend.setTime(mynow);
			SQLwhere = "" + prefix + "datetimefull > " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodstart.getTime()));
			SQLwhere = SQLwhere + " and " + prefix + "datetimefull <= " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodend.getTime()));
		} else if (mysession.get("admin_usage_period").equals("lastquarter")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.MONTH, -3);
			long period_mon = (long)(Math.floor(period.get(Calendar.MONTH)/3)*3)+1;
			SQLwhere = "" + prefix + "datetimeyear = " + period.get(Calendar.YEAR) + " and (" + prefix + "datetimemonth >= " + period_mon + " and " + prefix + "datetimemonth <= " + (period_mon+2) + ")";
		} else if (mysession.get("admin_usage_period").equals("thishalfyear")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			long period_mon = (long)(Math.floor(period.get(Calendar.MONTH)/6)*6)+1;
			SQLwhere = "" + prefix + "datetimeyear = " + period.get(Calendar.YEAR) + " and (" + prefix + "datetimemonth >= " + period_mon + " and " + prefix + "datetimemonth <= " + (period_mon+5) + ")";
		} else if (mysession.get("admin_usage_period").equals("last6months")) {
			Calendar periodstart = Calendar.getInstance();
			periodstart.setTime(mynow);
			periodstart.add(Calendar.MONTH, -6);
			Calendar periodend = Calendar.getInstance();
			periodend.setTime(mynow);
			SQLwhere = "" + prefix + "datetimefull > " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodstart.getTime()));
			SQLwhere = SQLwhere + " and " + prefix + "datetimefull <= " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodend.getTime()));
		} else if (mysession.get("admin_usage_period").equals("lasthalfyear")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.MONTH, -6);
			long period_mon = (long)(Math.floor(period.get(Calendar.MONTH)/6)*6)+1;
			SQLwhere = "" + prefix + "datetimeyear = " + period.get(Calendar.YEAR) + " and (" + prefix + "datetimemonth >= " + period_mon + " and " + prefix + "datetimemonth <= " + (period_mon+5) + ")";
		} else if (mysession.get("admin_usage_period").equals("thisyear")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			SQLwhere = "" + prefix + "datetimeyear = " + period.get(Calendar.YEAR);
		} else if (mysession.get("admin_usage_period").equals("last12months")) {
			Calendar periodstart = Calendar.getInstance();
			periodstart.setTime(mynow);
			periodstart.add(Calendar.SECOND, -365*24*60*60);
			Calendar periodend = Calendar.getInstance();
			periodend.setTime(mynow);
			SQLwhere = "" + prefix + "datetimefull > " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodstart.getTime()));
			SQLwhere = SQLwhere + " and " + prefix + "datetimefull <= " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodend.getTime()));
		} else if (mysession.get("admin_usage_period").equals("lastyear")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.YEAR, -1);
			SQLwhere = "" + prefix + "datetimeyear = " + period.get(Calendar.YEAR);
		} else if (mysession.get("admin_usage_period").equals("all")) {
			SQLwhere = "1=1";
		} else {
			Calendar periodstart = Calendar.getInstance();
			periodstart.setTime(mynow);
			periodstart.add(Calendar.SECOND, -30*60);	// 30 minutes
			Calendar periodend = Calendar.getInstance();
			periodend.setTime(mynow);
			SQLwhere = "" + prefix + "datetimefull > " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodstart.getTime()));
			SQLwhere = SQLwhere + " and " + prefix + "datetimefull <= " + db.quote((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(periodend.getTime()));
		}
		return SQLwhere;
	}



	private long[] periodStartEnd(Session mysession, Date mynow) {
		long periodstart;
		long periodend;
		if ((! mysession.get("admin_usage_period_start").equals("")) || (! mysession.get("admin_usage_period_end").equals(""))) {
			// "all"
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.DAY_OF_MONTH, -(period.get(Calendar.DAY_OF_MONTH)-1));
			period.add(Calendar.MONTH, -(period.get(Calendar.MONTH)));
			period.add(Calendar.MONTH, -12);
			periodstart = period.getTimeInMillis()/1000;
			periodend = mynow.getTime()/1000;

			if (! mysession.get("admin_usage_period_start").equals("")) {
				periodstart = Common.strtodate(mysession.get("admin_usage_period_start")).getTime()/1000;
			}
			if (! mysession.get("admin_usage_period_end").equals("")) {
				periodend = Common.strtodate(mysession.get("admin_usage_period_end")).getTime()/1000;
			}
		} else if (mysession.get("admin_usage_period").equals("now")) {
			periodstart = mynow.getTime()/1000 -30*60;	// 30 minutes
			periodend = mynow.getTime()/1000;
		} else if (mysession.get("admin_usage_period").equals("today")) {
			periodstart = mynow.getTime()/1000;
			periodend = mynow.getTime()/1000;
		} else if (mysession.get("admin_usage_period").equals("last24hours")) {
			periodstart = mynow.getTime()/1000 -24*60*60;
			periodend = mynow.getTime()/1000;
		} else if (mysession.get("admin_usage_period").equals("yesterday")) {
			periodstart = mynow.getTime()/1000 -24*60*60;
			periodend = mynow.getTime()/1000 -24*60*60;
		} else if (mysession.get("admin_usage_period").equals("thisweek")) {
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
		} else if (mysession.get("admin_usage_period").equals("last7days")) {
			periodstart = mynow.getTime()/1000 -6*24*60*60;
			periodend = mynow.getTime()/1000;
		} else if (mysession.get("admin_usage_period").equals("lastweek")) {
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
		} else if (mysession.get("admin_usage_period").equals("last14days")) {
			periodstart = mynow.getTime()/1000 -13*24*60*60;
			periodend = mynow.getTime()/1000;
		} else if (mysession.get("admin_usage_period").equals("thismonth")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.DAY_OF_MONTH, -(period.get(Calendar.DAY_OF_MONTH)-1));
			periodstart = period.getTimeInMillis()/1000;
			period.add(Calendar.MONTH, 1);
			period.add(Calendar.DAY_OF_MONTH, -1);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_usage_period").equals("last30days")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.MONTH, -1);
			periodstart = period.getTimeInMillis()/1000;
			period.setTime(mynow);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_usage_period").equals("lastmonth")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.DAY_OF_MONTH, -(period.get(Calendar.DAY_OF_MONTH)-1));
			period.add(Calendar.MONTH, -1);
			periodstart = period.getTimeInMillis()/1000;
			period.add(Calendar.MONTH, 1);
			period.add(Calendar.DAY_OF_MONTH, -1);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_usage_period").equals("thisquarter")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.DAY_OF_MONTH, -(period.get(Calendar.DAY_OF_MONTH)-1));
			period.add(Calendar.MONTH, (int)-(period.get(Calendar.MONTH)-((Math.floor(period.get(Calendar.MONTH)/3)*3))));
			periodstart = period.getTimeInMillis()/1000;
			period.add(Calendar.MONTH, 3);
			period.add(Calendar.DAY_OF_MONTH, -1);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_usage_period").equals("last3months")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.MONTH, -3);
			period.add(Calendar.DAY_OF_MONTH, 1);
			periodstart = period.getTimeInMillis()/1000;
			period.setTime(mynow);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_usage_period").equals("lastquarter")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.DAY_OF_MONTH, -(period.get(Calendar.DAY_OF_MONTH)-1));
			period.add(Calendar.MONTH, -3);
			period.add(Calendar.MONTH, (int)-(period.get(Calendar.MONTH)-((Math.floor(period.get(Calendar.MONTH)/3)*3))));
			periodstart = period.getTimeInMillis()/1000;
			period.add(Calendar.MONTH, 3);
			period.add(Calendar.DAY_OF_MONTH, -1);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_usage_period").equals("thishalfyear")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.DAY_OF_MONTH, -(period.get(Calendar.DAY_OF_MONTH)-1));
			period.add(Calendar.MONTH, (int)-(period.get(Calendar.MONTH)-((Math.floor(period.get(Calendar.MONTH)/6)*6))));
			periodstart = period.getTimeInMillis()/1000;
			period.add(Calendar.MONTH, 6);
			period.add(Calendar.DAY_OF_MONTH, -1);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_usage_period").equals("last6months")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.MONTH, -6);
			period.add(Calendar.DAY_OF_MONTH, 1);
			periodstart = period.getTimeInMillis()/1000;
			period.setTime(mynow);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_usage_period").equals("lasthalfyear")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.DAY_OF_MONTH, -(period.get(Calendar.DAY_OF_MONTH)-1));
			period.add(Calendar.MONTH, -6);
			period.add(Calendar.MONTH, (int)-(period.get(Calendar.MONTH)-((Math.floor(period.get(Calendar.MONTH)/6)*6))));
			periodstart = period.getTimeInMillis()/1000;
			period.add(Calendar.MONTH, 6);
			period.add(Calendar.DAY_OF_MONTH, -1);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_usage_period").equals("thisyear")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.DAY_OF_MONTH, -(period.get(Calendar.DAY_OF_MONTH)-1));
			period.add(Calendar.MONTH, -(period.get(Calendar.MONTH)));
			periodstart = period.getTimeInMillis()/1000;
			period.add(Calendar.MONTH, 12);
			period.add(Calendar.DAY_OF_MONTH, -1);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_usage_period").equals("last12months")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.MONTH, -12);
			period.add(Calendar.DAY_OF_MONTH, 1);
			periodstart = period.getTimeInMillis()/1000;
			period.setTime(mynow);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_usage_period").equals("lastyear")) {
			Calendar period = Calendar.getInstance();
			period.setTime(mynow);
			period.add(Calendar.DAY_OF_MONTH, -(period.get(Calendar.DAY_OF_MONTH)-1));
			period.add(Calendar.MONTH, -12);
			period.add(Calendar.MONTH, -(period.get(Calendar.MONTH)));
			periodstart = period.getTimeInMillis()/1000;
			period.add(Calendar.MONTH, 12);
			period.add(Calendar.DAY_OF_MONTH, -1);
			periodend = period.getTimeInMillis()/1000;
		} else if (mysession.get("admin_usage_period").equals("all")) {
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



	public Long[] periodHours(Session mysession, Date mynow) {
		ArrayList<Long> days = new ArrayList<Long>();
		ArrayList<Long> hours = new ArrayList<Long>();

		long[] period = periodStartEnd(mysession, mynow);
		long periodstart = period[0];
		long periodend = period[1];

		long myday = periodstart;
		while (myday <= periodend) {
			days.add(new Long(myday));
			long myhour = myday;
			for (int hour=0; hour<24; hour++) {
				hours.add(new Long(myhour + (hour*60*60)));
			}
			myday += 24*60*60;
		}
		Calendar lastday = Calendar.getInstance();
		lastday.setTime(new Date(Common.integernumber("" + days.get(days.size()-1))*1000));
		Calendar myperiodend = Calendar.getInstance();
		myperiodend.setTime(new Date(periodend*1000));
		if (lastday.get(Calendar.DAY_OF_MONTH) != myperiodend.get(Calendar.DAY_OF_MONTH)) {
			days.add(new Long(periodend));
			long myhour = periodend;
			for (int hour=0; hour<24; hour++) {
				hours.add(new Long(myhour + (hour*60*60)));
			}
		}
		return (Long[])hours.toArray(new Long[0]);
	}



	public Long[] periodDays(Session mysession, Date mynow) {
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



	public Long[] periodWeeks(Session mysession, Date mynow) {
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



	public Long[] periodMonths(Session mysession, Date mynow) {
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



	public Long[] periodYears(Session mysession, Date mynow) {
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



	public Long[] allHours(Session mysession, Date mynow) {
		ArrayList<Long> hours = new ArrayList<Long>();
		for (int i=0; i<=23; i++) {
			hours.add(new Long(i));
		}
		return (Long[])hours.toArray(new Long[0]);
	}



	public Long[] allDays(Session mysession, Date mynow) {
		ArrayList<Long> days = new ArrayList<Long>();
		for (int i=1; i<=31; i++) {
			days.add(new Long(i));
		}
		return (Long[])days.toArray(new Long[0]);
	}



	public Long[] allWeekdays(Session mysession, Date mynow) {
		ArrayList<Long> weekdays = new ArrayList<Long>();
		for (int i=1; i<=7; i++) {
			weekdays.add(new Long(i));
		}
		return (Long[])weekdays.toArray(new Long[0]);
	}



	public Long[] allWeeks(Session mysession, Date mynow) {
		ArrayList<Long> weeks = new ArrayList<Long>();
		for (int i=1; i<=53; i++) {
			weeks.add(new Long(i));
		}
		return (Long[])weeks.toArray(new Long[0]);
	}



	public Long[] allMonths(Session mysession, Date mynow) {
		ArrayList<Long> months = new ArrayList<Long>();
		for (int i=1; i<=12; i++) {
			months.add(new Long(i));
		}
		return (Long[])months.toArray(new Long[0]);
	}



	public long maxValue(HashMap<String,String> usage) {
		long maxvalue = 1;
		if (usage != null) {
			Iterator myusage = usage.keySet().iterator();
			while (myusage.hasNext()) {
				String i = "" + myusage.next();
				long value = Common.integernumber("0" + usage.get(i));
				if (value > maxvalue) {
					maxvalue = value;
				}
			}
		}
		return maxvalue;
	}



	public long totalValue(HashMap<String,String> usage) {
		long totalvalue = 0;
		if (usage != null) {
			Iterator myusage = usage.keySet().iterator();
			while (myusage.hasNext()) {
				String i = "" + myusage.next();
				long value = Common.integernumber("0" + usage.get(i));
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
		if ((country.equals("")) || (country.equals("-"))) {
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
		if ((country.equals("")) || (country.equals("-"))) country = "unknown";
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



	public String robotName(String robot) {
		UsageLog usagelog = new UsageLog();
		for (int i=0; i<usagelog.robotName.length; i++) {
			if (usagelog.robotName[i].equals(robot)) {
				if (! usagelog.robotWebsite[i].equals("")) {
					return "<a target=\"_blank\" href=\"http://" + usagelog.robotWebsite[i] + "/\">" + robot + "</a>";
				} else {
					return robot;
				}
			}
		}
		return robot;
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



	public String deviceIcon(String device) {
		return device.replaceAll(" \\(.*\\)", "").replaceAll("/", "").replaceAll(" ", "").replaceAll("^-$", "default").replaceAll("^$", "default");
	}



	public String deviceversionIcon(String deviceversion) {
		String[] tmp = deviceversion.split(":::");
		String device = "";
		String version = "";
		if (tmp.length > 0) device = tmp[0];
		if (tmp.length > 1) version = tmp[1];
		return device.replaceAll(" \\(.*\\)", "").replaceAll("\\/", "").replaceAll(" ", "").replaceAll("^-$", "default").replaceAll("^$", "default");
	}



	public String deviceName(String device) {
		return device.replaceAll("^-$", "");
	}



	public String deviceversionName(String deviceversion) {
		String[] tmp = deviceversion.split(":::");
		String device = "";
		String version = "";
		if (tmp.length > 0) device = tmp[0];
		if (tmp.length > 1) version = tmp[1];
		return version.replaceAll("^-$", "");
	}



	public String operatingsystemIcon(String operatingsystem) {
		return operatingsystem.replaceAll(" \\(.*\\)", "").replaceAll(" ", "").replaceAll("\\/", "");
	}



	public String operatingsystemversionIcon(String operatingsystemversion) {
		String[] tmp = operatingsystemversion.split(":::");
		String operatingsystem = "";
		String version = "";
		if (tmp.length > 0) operatingsystem = tmp[0];
		if (tmp.length > 1) version = tmp[1];
		if (operatingsystem.equals("Windows")) {
			return version.replaceAll(" \\(.*\\)", "").replaceAll(" ", "").replaceAll("\\/", "");
		} else {
			return operatingsystem.replaceAll(" \\(.*\\)", "").replaceAll(" ", "").replaceAll("\\/", "");
		}
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
		if (operatingsystem.equals("Windows")) return version;
		return operatingsystemName(operatingsystem) + " " + version;
	}



	public String refererName(String referer) {
		String myreferer = referer.replaceAll(":::", "");
		String referername = myreferer;
		if ((! myreferer.equals("")) && (! myreferer.equals("-"))) {
			if (myreferer.length() > 60) {
				referername = "<a target=\"_blank\" href=\"http://" + myreferer + "\">" + myreferer.substring(0,59) + "...</a>";
			} else {
				referername = "<a target=\"_blank\" href=\"http://" + myreferer + "\">" + myreferer + "</a>";
			}
		} else {
			referername = text.display("usage.referers.direct");
		}
		return referername;
	}



	public String searchengineIcon(String searchengine) {
		return searchengine.replaceAll(" \\(.*\\)", "").replaceAll(" ", "").replaceAll("\\/", "").replaceAll("^$", "unknown");
	}



	public String searchengineName(String searchengine) {
		String searchenginename = searchengine;
		UsageLog usagelog = new UsageLog();
		for (int i=0; i<usagelog.searchengineName.length; i++) {
			if (usagelog.searchengineName[i].equals(searchengine)) {
				searchenginename = "<a target=\"_blank\" href=\"http://" + usagelog.searchengineWebsite[i] + "/\">" + searchengine + "</a>";
				break;
			}
		}
		return searchenginename;
	}



	public String pageName(String key, Configuration myconfig, DB db) {
		String[] tmp = key.split(":::");
		String contentclass = "";
		String database = "";
		String id = "";
		if (tmp.length > 0) contentclass = tmp[0];
		if (tmp.length > 1) database = tmp[1];
		if (tmp.length > 2) id = tmp[2];
		if ((contentclass.equals("")) || (id.equals(""))) {
			return "&nbsp;";
		} else if (database.equals("")) {
			Content content = new Content(text);
			content.public_read(db, myconfig, id);
			if (content.getTitle().equals("")) {
				return "<a href=\"/" + text.display("adminpath") + "/usage/contentitem.jsp?contentclass=" + contentclass + "&id=" + id + "\">" + "&nbsp;" + "</a>";
			} else if (content.getTitle().length() > 60) {
				return "<a href=\"/" + text.display("adminpath") + "/usage/contentitem.jsp?contentclass=" + contentclass + "&id=" + id + "\">" + content.getTitle().substring(0,59) + "...</a>";
			} else {
				return "<a href=\"/" + text.display("adminpath") + "/usage/contentitem.jsp?contentclass=" + contentclass + "&id=" + id + "\">" + content.getTitle() + "</a>";
			}
		} else {
			Databases databases = new Databases(text);
			databases.readTitle(db, myconfig, database);
			databases.getTitleColumn();
			String titleid = databases.getTitleColumnId();
			String titlename = databases.getTitleColumnName();
			String titlecolumn = databases.getTitleColumnColumn();
			Data data = new Data();
			data.read(db, "data" + databases.getId(), id);
			if (data.getContent(titlecolumn).equals("")) {
				return "<a href=\"/" + text.display("adminpath") + "/usage/dataitem.jsp?contentclass=" + contentclass + "&id=" + id + "&database=" + database + "\">" + "&nbsp;" + "</a>";
			} else if (data.getContent(titlecolumn).length() > 60) {
				return "<a href=\"/" + text.display("adminpath") + "/usage/dataitem.jsp?contentclass=" + contentclass + "&id=" + id + "&database=" + database + "\">" + data.getContent(titlecolumn).substring(0,59) + "...</a>";
			} else {
				return "<a href=\"/" + text.display("adminpath") + "/usage/dataitem.jsp?contentclass=" + contentclass + "&id=" + id + "&database=" + database + "\">" + data.getContent(titlecolumn) + "</a>";
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
				return "<a href=\"/" + text.display("adminpath") + "/usage/contentitem.jsp?contentclass=" + contentclass + "&id=" + id + "\">" + "&nbsp;" + "</a>";
			} else if (content.getTitle().length() > 60) {
				return "<a href=\"/" + text.display("adminpath") + "/usage/contentitem.jsp?contentclass=" + contentclass + "&id=" + id + "\">" + content.getTitle().substring(0,59) + "...</a>";
			} else {
				return "<a href=\"/" + text.display("adminpath") + "/usage/contentitem.jsp?contentclass=" + contentclass + "&id=" + id + "\">" + content.getTitle() + "</a>";
			}
		} else {
			Databases databases = new Databases(text);
			databases.readTitle(db, myconfig, database);
			databases.getTitleColumn();
			String titleid = databases.getTitleColumnId();
			String titlename = databases.getTitleColumnName();
			String titlecolumn = databases.getTitleColumnColumn();
			Data data = new Data();
			data.read(db, "data" + databases.getId(), id);
			if (data.getContent(titlecolumn).equals("")) {
				return "<a href=\"/" + text.display("adminpath") + "/usage/dataitem.jsp?contentclass=" + contentclass + "&id=" + id + "&database=" + database + "\">" + "&nbsp;" + "</a>";
			} else if (data.getContent(titlecolumn).length() > 60) {
				return "<a href=\"/" + text.display("adminpath") + "/usage/dataitem.jsp?contentclass=" + contentclass + "&id=" + id + "&database=" + database + "\">" + data.getContent(titlecolumn).substring(0,59) + "...</a>";
			} else {
				return "<a href=\"/" + text.display("adminpath") + "/usage/dataitem.jsp?contentclass=" + contentclass + "&id=" + id + "&database=" + database + "\">" + data.getContent(titlecolumn) + "</a>";
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
		String contentdatabase = "";
		String contentid = "";
		String usersegments = "";
		String usertests = "";
		if (tmp.length > 0) mycontentclass = tmp[0];
		if (mycontentclass.equals("data")) {
			if (tmp.length > 1) contentdatabase = tmp[1];
			if (tmp.length > 2) contentid = tmp[2];
			if (tmp.length > 3) usersegments = tmp[3];
			if (tmp.length > 4) usertests = tmp[4];
			if (tmp.length > 5) {
				usersegments = tmp[4];
				usertests = tmp[5];
			}
		} else {
			if (tmp.length > 1) contentid = tmp[1];
			if (tmp.length > 2) usersegments = tmp[2];
			if (tmp.length > 3) usertests = tmp[3];
			if (tmp.length > 4) {
				usersegments = tmp[3];
				usertests = tmp[4];
			}
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
				return "<a href=\"/" + text.display("adminpath") + "/usage/dataitem.jsp?contentclass=" + mycontentclass + "&id=" + contentid + "&database=" + database + "\">" + "&nbsp;" + "</a>";
			} else if (data.getContent(titlecolumn).length() > 60) {
				return "<a href=\"/" + text.display("adminpath") + "/usage/dataitem.jsp?contentclass=" + mycontentclass + "&id=" + contentid + "&database=" + database + "\">" + data.getContent(titlecolumn).substring(0,59) + "...</a>";
			} else {
				return "<a href=\"/" + text.display("adminpath") + "/usage/dataitem.jsp?contentclass=" + mycontentclass + "&id=" + contentid + "&database=" + database + "\">" + data.getContent(titlecolumn) + "</a>";
			}
		} else {
			Content content = new Content(text);
			content.public_read(db, myconfig, contentid);
			if (content.getId().equals("")) {
				return "&nbsp;";
			} else if (content.getTitle().equals("")) {
				return "<a href=\"/" + text.display("adminpath") + "/usage/contentitem.jsp?contentclass=" + content.getContentClass() + "&id=" + content.getId() + "\">" + "&nbsp;" + "</a>";
			} else {
				return "<a href=\"/" + text.display("adminpath") + "/usage/contentitem.jsp?contentclass=" + content.getContentClass() + "&id=" + content.getId() + "\">" + content.getTitle() + "</a>";
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
			return "<a href=\"/" + text.display("adminpath") + "/usage/contentitem.jsp?contentclass=" + content.getContentClass() + "&id=" + content.getId() + "\">" + "&nbsp;" + "</a>";
		} else {
			return "<a href=\"/" + text.display("adminpath") + "/usage/contentitem.jsp?contentclass=" + content.getContentClass() + "&id=" + content.getId() + "\">" + content.getTitle() + "</a>";
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
		if (tmp.length > 2) pageid = tmp[2];
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



	public String clienthostName(String key) {
		String clienthostname = key;
		String[] tmp = key.split(":::");
		if (tmp.length > 0) clienthostname = tmp[0];
		if ((! clienthostname.equals("")) && (! clienthostname.equals("-"))) {
			clienthostname = "<a href=\"/" + text.display("adminpath") + "/usage/clienthost.jsp?clienthost=" + clienthostname + "\">" + clienthostname + "</a>";
		} else {
			clienthostname = "-";
		}
		return clienthostname;
	}



	public String visitorName(String key) {
		String visitorname = key;
		String[] tmp = key.replaceAll(":::$", ":::-").split(":::");
		if (tmp.length > 1) visitorname = tmp[1];
		String visitorid = "" + visitorname;
		visitorname = visitorname.replaceAll("^([0-9][0-9][0-9][0-9])([0-9][0-9])([0-9][0-9])([0-9][0-9])([0-9][0-9])([0-9][0-9])([0-9]+)$", "$1-$2-$3 $4:$5:$6 $7");
		if ((! visitorid.equals("")) && (! visitorid.equals("-"))) {
			visitorname = "<a href=\"/" + text.display("adminpath") + "/usage/visitor.jsp?visitor=" + visitorid + "\">" + visitorname + "</a>";
		} else {
			visitorname = "-";
		}
		return visitorname;
	}



	public String userName(String key) {
		String username = key;
		if ((! username.equals("")) && (! username.equals("-"))) {
			username = "<a href=\"/" + text.display("adminpath") + "/usage/username.jsp?username=" + key + "\">" + key + "</a>";
		} else {
			username = text.display("usage.users.anonymous");
		}
		return username;
	}



	public String visitName(String key) {
		String visitname = key;
		String[] tmp = key.replaceAll(":::$", ":::-").split(":::");
		if (tmp.length > 2) visitname = tmp[2];
		if (visitname.length() >= 14) {
			visitname = "<a href=\"/" + text.display("adminpath") + "/usage/visit.jsp?visit=" + visitname + "\">" + visitname.substring(0,4) + "-" + visitname.substring(4,6) + "-" + visitname.substring(6,8) + " " + visitname.substring(8,10) + ":" + visitname.substring(10,12) + ":" + visitname.substring(12,14) + "</a>";
		} else {
			visitname = "-";
		}
		return visitname;
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



	public String usersegmentVariant(String key) {
		String usersegments = "";
		String usertests = "";
		String[] tmp = key.split(":::");
		if (tmp.length > 0) usersegments = tmp[0];
		if (tmp.length > 1) usertests = tmp[1];
		if ((usersegments.equals("")) || (usersegments.equals("-"))) {
			return "&nbsp;";
		} else {
			return usersegments;
		}
	}



	public String usertestVariant(String key) {
		String usertests = "";
		String usersegments = "";
		String[] tmp = key.split(":::");
		if (tmp.length > 0) usertests = tmp[0];
		if (tmp.length > 1) usersegments = tmp[1];
		if ((usertests.equals("")) || (usertests.equals("-"))) {
			return "&nbsp;";
		} else {
			return usertests;
		}
	}



}
