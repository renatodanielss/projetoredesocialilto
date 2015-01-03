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

public class UCreportUsertest {



	private Text text = new Text();



	public UCreportUsertest() {
	}



	public UCreportUsertest(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public void init(DB db) {
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
		return true;
	}



	public HashMap<String,HashMap> getUsertest(Usertest usertest, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, DB logdb, String database) {
		boolean accesspermission = initRequest(mysession, myrequest, myresponse, myconfig, db, logdb, database);
		if (! accesspermission) return new HashMap<String,HashMap>();

		Page page = new Page();
		HashMap<String,LinkedHashMap<String,String>> total = new HashMap<String,LinkedHashMap<String,String>>();
		HashMap<String,HashMap<String,LinkedHashMap<String,String>>> usage = new HashMap<String,HashMap<String,LinkedHashMap<String,String>>>();
		HashMap<String,HashMap<String,Long>> visitors = new HashMap<String,HashMap<String,Long>>();
		HashMap<String,HashMap<String,Long>> conversions = new HashMap<String,HashMap<String,Long>>();
		HashMap<String,HashMap<String,String>> rate = new HashMap<String,HashMap<String,String>>();
		HashMap<String,HashMap<String,String>> improvement = new HashMap<String,HashMap<String,String>>();
		HashMap<String,HashMap<String,String>> confidence = new HashMap<String,HashMap<String,String>>();
		HashMap<String,String> maxconfidence = new HashMap<String,String>();
		HashMap<String,Long> totalvisitors = new HashMap<String,Long>();
		HashMap<String,Long> totalconversions = new HashMap<String,Long>();
		HashMap<String,String> totalrate = new HashMap<String,String>();
	
		NormalDistribution mydist = new NormalDistribution();
		UCreportUsage reportUsage = new UCreportUsage();

		// report data for user test's scheduled period (if any) or all

		mysession.set("admin_usage_period", "all");
		mysession.set("admin_usage_period_start", "");
		mysession.set("admin_usage_period_end", "");
		if (! usertest.getScheduled().equals("")) {
			mysession.set("admin_usage_period", "");
			mysession.set("admin_usage_period_start", usertest.getScheduled());
		}
		if (! usertest.getUnscheduled().equals("")) {
			mysession.set("admin_usage_period", "");
			mysession.set("admin_usage_period_end", usertest.getUnscheduled());
		}

		// ids for the content item variants for this user test

		LinkedHashMap<String,String> myids = usertest.content_ids(db);
		if (myids == null) myids = new LinkedHashMap<String,String>();

		// total number of visitors for all the content item variants for this user test

//		$total = $reportUsage->getContentIds($_SESSION, $_GET, $config, $db, $logdb, $database, join(",", $myids));
//		$total = $reportUsage->getContentIds($_SESSION, $_GET, $config, $db, $logdb, $database, join(",", $myids), "countclienthosts+countvisitors+countvisits+counthits+countusersegments+countusertests");
		total = reportUsage.getContentIds(mysession, myrequest, myresponse, myconfig, db, logdb, database, myids, "countvisitors+countusertests");


		String[] mygoals = usertest.getGoals().split(",");
		for (int i=0; i<mygoals.length; i++) {
			String myid = "" + mygoals[i];
			if (! myid.equals("")) {

				// total number of visitors for this content item goal for this user test

//				usage.put(myid, reportUsage.getContentIds(mysession, myrequest, myresponse, myconfig, db, logdb, database, myid));
//				usage.put(myid, reportUsage.getContentIds(mysession, myrequest, myresponse, myconfig, db, logdb, database, myid, "countclienthosts+countvisitors+countvisits+counthits+countusersegments+countusertests"));
				usage.put(myid, reportUsage.getContentIds(mysession, myrequest, myresponse, myconfig, db, logdb, database, myid.split(","), "countvisitors+countusertests"));

				long mybaseconversions = 0;
				try {
					mybaseconversions = Common.integernumber(usage.get(myid).get("countvisitorstest").get("total:::"+usertest.getUsertest()+"::"));
				} catch (Exception e) {
				}
				long mybasevisitors = 0;
				try {
					mybasevisitors = Common.integernumber(total.get("countvisitorstest").get("total:::"+usertest.getUsertest()+"::"));
				} catch (Exception e) {
				}

				double mybaserate = 0;
				if (mybasevisitors > 0) {
					mybaserate = (double)mybaseconversions / (double)mybasevisitors;
				} else {
					mybaserate = 0;
				}

				double mymaxconfidence = -1;
				long mytotalvisitors = 0;
				long mytotalconversions = 0;

				String[] myvariants = ("\r\n" + usertest.getVariants().trim()).split("\r\n");
				for (int j=0; j<myvariants.length; j++) {
					String myvariant = "" + myvariants[j];
					long myconversions = 0;
					try {
						myconversions = Common.integernumber(usage.get(myid).get("countvisitorstest").get("total:::"+usertest.getUsertest()+"::"+myvariant));
					} catch (Exception e) {
					}
					long myvisitors = 0;
					try {
						myvisitors = Common.integernumber(total.get("countvisitorstest").get("total:::"+usertest.getUsertest()+"::"+myvariant));
					} catch (Exception e) {
					}
					mytotalconversions += myconversions;
					mytotalvisitors += myvisitors;

					double myrate = 0;
					if (myvisitors > 0) {
						myrate =  (double)myconversions / (double)myvisitors;
					} else {
						myrate =  0;
					}

					String myimprovement = "";
					if (myvisitors == 0) {
						myimprovement = "-";
					} else if (mybaserate > 0) {
						myimprovement = "" + ((100 * myrate / mybaserate) - 100);
					} else if (myrate == 0) {
						myimprovement = "0";
					} else {
						myimprovement = "+";
					}

					String myconfidence = "";
					double div = 0;
					if ((myvisitors > 0) && (mybasevisitors > 0)) {
						div = ( Math.sqrt( ((myrate*(1-myrate)) / (double)myvisitors) + ((mybaserate*(1-mybaserate)) / (double)mybasevisitors) ) );
//					} else if (mybasevisitors > 0) {
//						div = ( Math.sqrt( 0 + ((mybaserate*(1-mybaserate)) / (double)mybasevisitors) ) );
//						div = 0;
//					} else if (myvisitors > 0) {
//						div = ( Math.sqrt( ((myrate*(1-myrate)) / (double)myvisitors) + 0 ) );
					} else {
						div = 0;
					}
					if (div > 0) {
						double myz = ( ((double)myrate - (double)mybaserate) / div );
						double myphi = mydist.phi(myz);
						if (myphi > mymaxconfidence) {
							mymaxconfidence = myphi;
						}
						myconfidence = "" + myphi;
					} else {
						myconfidence = "-";
					}

					if (visitors.get(myid) == null) visitors.put(myid, new HashMap<String,Long>());
					visitors.get(myid).put(myvariant, myvisitors);

					if (conversions.get(myid) == null) conversions.put(myid, new HashMap<String,Long>());
					conversions.get(myid).put(myvariant, myconversions);

					if (rate.get(myid) == null) rate.put(myid, new HashMap<String,String>());
					rate.get(myid).put(myvariant, Common.numberformat(myrate * 100, 1));

					if (improvement.get(myid) == null) improvement.put(myid, new HashMap<String,String>());
					if (! myvariant.equals("")) {
						if (myimprovement.equals("+")) {
							improvement.get(myid).put(myvariant, "+");
						} else if (myimprovement.equals("-")) {
							improvement.get(myid).put(myvariant, "-");
						} else {
							improvement.get(myid).put(myvariant, Common.numberformat(myimprovement, 1));
						}
					} else {
						improvement.get(myid).put(myvariant, "");
					}

					if (confidence.get(myid) == null) confidence.put(myid, new HashMap<String,String>());
					if (! myvariant.equals("")) {
						if (myconfidence.equals("-")) {
							confidence.get(myid).put(myvariant, "-");
						} else {
							confidence.get(myid).put(myvariant, Common.numberformat(Common.number(myconfidence) * 100, 1));
						}
					} else {
						confidence.get(myid).put(myvariant, "");
					}
				}

				double mytotalrate;
				if (mytotalvisitors > 0) {
					mytotalrate =  (double)mytotalconversions / (double)mytotalvisitors;
				} else {
					mytotalrate =  0;
				}
				totalvisitors.put(myid, mytotalvisitors);
				totalconversions.put(myid, mytotalconversions);
				totalrate.put(myid, Common.numberformat(mytotalrate * 100, 1));

				if ((! usertest.getConfidence().equals("")) && ((mymaxconfidence * 100) > Common.number(usertest.getConfidence()))) {
					maxconfidence.put(myid, Common.numberformat(mymaxconfidence * 100, 1));
				}

			}
		}

		HashMap<String,HashMap> data = new HashMap<String,HashMap>();
		data.put("total", total);
		data.put("usage", usage);
		data.put("visitors", visitors);
		data.put("conversions", conversions);
		data.put("rate", rate);
		data.put("improvement", improvement);
		data.put("confidence", confidence);
		data.put("maxconfidence", maxconfidence);
		data.put("totalvisitors", totalvisitors);
		data.put("totalconversions", totalconversions);
		data.put("totalrate", totalrate);
		return data;
	}



}
