package HardCore;

import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;


public class UCmaintainContentbundles {
	private Text text = new Text();



	public UCmaintainContentbundles() {
	}



	public UCmaintainContentbundles(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public ArrayList getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new ArrayList();
		Content content = new Content();
		ArrayList contentbundles = content.getContentbundles(db);
//QQQQQ		User user = new User();
//QQQQQ		ArrayList userbundles = user.getContentbundles(db);
//QQQQQ		Databases database = new Databases();
//QQQQQ		ArrayList databasebundles = database.getContentbundles(db);
//QQQQQ		contentbundles = array_merge($contentbundles, $userbundles, $databasebundles);
		return contentbundles;
	}



	public String getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
		String contentbundle = myrequest.getParameter("id");
		return contentbundle;
	}



	public String getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
		String contentbundle = myrequest.getParameter("id");
		return contentbundle;
	}



	public String getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
		String contentbundle = myrequest.getParameter("id");
		return contentbundle;
	}



	public void doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		String old_contentbundle = myrequest.getParameter("id");
		String new_contentbundle = myrequest.getParameter("contentbundle");
		if (! old_contentbundle.equals(new_contentbundle)) {
			Content content = new Content(text);
			content.renameContentbundle(db, old_contentbundle, new_contentbundle);
//QQQQQ			User user = new User(text);
//QQQQQ			user.renameContentbundle(db, old_contentbundle, new_contentbundle);
//QQQQQ			Databases database = new Databases(text);
//QQQQQ			database.renameContentbundle(db, old_contentbundle, new_contentbundle);
		}
	}



	public void doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		String old_contentbundle = myrequest.getParameter("id");
		String new_contentbundle = "";
		if (! old_contentbundle.equals(new_contentbundle)) {
			Content content = new Content(text);
			content.renameContentbundle(db, old_contentbundle, new_contentbundle);
//QQQQQ			User user = new User(text);
//QQQQQ			user.renameContentbundle(db, old_contentbundle, new_contentbundle);
//QQQQQ			Databases database = new Databases(text);
//QQQQQ			database.renameContentbundle(db, old_contentbundle, new_contentbundle);
		}
	}



}
