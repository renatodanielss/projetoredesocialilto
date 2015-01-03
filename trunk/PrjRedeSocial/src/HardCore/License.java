package HardCore;

import java.sql.*;
import java.util.regex.*;

public class License {



	public static String generate(String owner, String product) {
		String key = "";
		if ((! owner.equals("")) && (! product.equals(""))) {
			key = "" + owner + ":" + product + ":" + COwnerRegistration.GenerateKey(owner, product);
		}
		return key;
	}



	public static String trial(String owner, String product) {
		String key = "";
		if ((! owner.equals("")) && (! product.equals(""))) {
			key = "TRIAL";
		}
		return key;
	}



	public static boolean valid(DB db, Configuration myconfig, String product) {
		if (product.equals("personal") && valid(db, myconfig, "professional")) {
			return true;
		} else if (product.equals("professional") && valid(db, myconfig, "enterprise")) {
			return true;
//		} else if (product.equals("enterprise") && valid(db, myconfig, "hosting")) {
//			return true;
		} else if ((product.equals("community") || product.equals("databases") || product.equals("ecommerce")) && valid(db, myconfig, "hosting")) {
			return true;
		} else if (! myconfig.get(db, product.replaceAll("^!", "") + "_license").equals("")) {
			product = product.replaceAll("^!", "");
			Pattern p;
			Matcher m;
			p = Pattern.compile("^([^:]+):([^:]+)(.*):([^:]+)$");
			m = p.matcher(myconfig.get(db, product + "_license"));
			if (m.find()) {
				if ((product.equals(m.group(2))) && (COwnerRegistration.IsKeyOK(m.group(4), m.group(1), m.group(2) + m.group(3)))) {
					return true;
				} else if (((product+"-personal").equals(m.group(2))) && (COwnerRegistration.IsKeyOK(m.group(4), m.group(1), m.group(2) + m.group(3))) && (! valid(db, myconfig, "professional"))) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}



	public static int restriction(DB db, Configuration myconfig, String restriction) {
		int limit = -1;
		if (! myconfig.get(db, "enterprise_license").equals("")) {
			Pattern p;
			Matcher m;
			p = Pattern.compile("[^0-9]([0-9]+)\\*" + restriction);
			m = p.matcher(myconfig.get(db, "enterprise_license"));
			if (m.find()) {
				limit = Integer.parseInt(m.group(1));
			}
		} else if (! myconfig.get(db, "professional_license").equals("")) {
			Pattern p;
			Matcher m;
			p = Pattern.compile("[^0-9]([0-9]+)\\*" + restriction);
			m = p.matcher(myconfig.get(db, "professional_license"));
			if (m.find()) {
				limit = Integer.parseInt(m.group(1));
			}
		} else if (! myconfig.get(db, "personal_license").equals("")) {
			Pattern p;
			Matcher m;
			p = Pattern.compile("[^0-9]([0-9]+)\\*" + restriction);
			m = p.matcher(myconfig.get(db, "personal_license"));
			if (m.find()) {
				limit = Integer.parseInt(m.group(1));
			}
		} else if (valid(db, myconfig, "hosting") && (restriction.equals("administrator"))) {
			limit = 1;
		}
		if (valid(db, myconfig, "hosting") && (restriction.equals("hosting"))) {
			Pattern p;
			Matcher m;
			p = Pattern.compile("[^0-9]([0-9]+)\\*" + restriction);
			m = p.matcher(myconfig.get(db, "hosting_license"));
			if (m.find()) {
				limit = Integer.parseInt(m.group(1));
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:hosting") > 0) {
				limit = -1;
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:enterprise") > 0) {
				limit = 0;
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:professional") > 0) {
				limit = 0;
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:smallbusiness") > 0) {
				limit = 0;
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:personal") > 0) {
				limit = 0;
			}
		} else if (valid(db, myconfig, "hosting") && (restriction.equals("enterprise"))) {
			Pattern p;
			Matcher m;
			p = Pattern.compile("[^0-9]([0-9]+)\\*" + restriction);
			m = p.matcher(myconfig.get(db, "hosting_license"));
			if (m.find()) {
				limit = Integer.parseInt(m.group(1));
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:hosting") > 0) {
				limit = -1;
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:enterprise") > 0) {
				limit = -1;
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:professional") > 0) {
				limit = 0;
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:smallbusiness") > 0) {
				limit = 0;
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:personal") > 0) {
				limit = 0;
			}
		} else if (valid(db, myconfig, "hosting") && (restriction.equals("professional"))) {
			Pattern p;
			Matcher m;
			p = Pattern.compile("[^0-9]([0-9]+)\\*" + restriction);
			m = p.matcher(myconfig.get(db, "hosting_license"));
			if (m.find()) {
				limit = Integer.parseInt(m.group(1));
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:hosting") > 0) {
				limit = -1;
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:enterprise") > 0) {
				limit = -1;
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:professional") > 0) {
				limit = -1;
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:smallbusiness") > 0) {
				limit = 0;
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:personal") > 0) {
				limit = 0;
			}
		} else if (valid(db, myconfig, "hosting") && (restriction.equals("smallbusiness"))) {
			Pattern p;
			Matcher m;
			p = Pattern.compile("[^0-9]([0-9]+)\\*" + restriction);
			m = p.matcher(myconfig.get(db, "hosting_license"));
			if (m.find()) {
				limit = Integer.parseInt(m.group(1));
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:hosting") > 0) {
				limit = -1;
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:enterprise") > 0) {
				limit = -1;
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:professional") > 0) {
				limit = -1;
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:smallbusiness") > 0) {
				limit = -1;
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:personal") > 0) {
				limit = 0;
			}
		} else if (valid(db, myconfig, "hosting") && (restriction.equals("personal"))) {
			Pattern p;
			Matcher m;
			p = Pattern.compile("[^0-9]([0-9]+)\\*" + restriction);
			m = p.matcher(myconfig.get(db, "hosting_license"));
			if (m.find()) {
				limit = Integer.parseInt(m.group(1));
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:hosting") > 0) {
				limit = -1;
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:enterprise") > 0) {
				limit = -1;
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:professional") > 0) {
				limit = -1;
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:smallbusiness") > 0) {
				limit = -1;
			} else if (myconfig.get(db, "hosting_license").indexOf("hosting:personal") > 0) {
				limit = -1;
			}
		}
		return limit;
	}



	public static String owner(String key) {
		Pattern p;
		Matcher m;
		p = Pattern.compile("^([^:]+):([^:]+)(.*):([^:]+)$");
		m = p.matcher(key);
		if (m.find()) {
			return "" + m.group(1);
		} else {
			return "";
		}
	}



	public static String product(String key) {
		Pattern p;
		Matcher m;
		p = Pattern.compile("^([^:]+):([^:]+)(.*):([^:]+)$");
		m = p.matcher(key);
		if (m.find()) {
			return "" + m.group(2);
		} else {
			return "";
		}
	}



}
