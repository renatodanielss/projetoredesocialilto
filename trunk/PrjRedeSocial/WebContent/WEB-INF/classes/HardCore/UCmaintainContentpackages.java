package HardCore;

import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;


public class UCmaintainContentpackages {
	private Text text = new Text();



	public UCmaintainContentpackages() {
	}



	public UCmaintainContentpackages(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public ArrayList getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new ArrayList();
		Content content = new Content();
		ArrayList contentpackages = content.getContentpackages(db);
//QQQQQ		User user = new User();
//QQQQQ		ArrayList userpackages = user.getContentpackages(db);
//QQQQQ		Databases database = new Databases();
//QQQQQ		ArrayList databasepackages = database.getContentpackages(db);
//QQQQQ		contentpackages = array_merge($contentpackages, $userpackages, $databasepackages);
		return contentpackages;
	}



	public String getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
		String contentpackage = myrequest.getParameter("id");
		return contentpackage;
	}



	public String getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
		String contentpackage = myrequest.getParameter("id");
		return contentpackage;
	}



	public String getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
		String contentpackage = myrequest.getParameter("id");
		return contentpackage;
	}



	public void doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		String old_contentpackage = myrequest.getParameter("id");
		String new_contentpackage = myrequest.getParameter("contentpackage");
		if (! old_contentpackage.equals(new_contentpackage)) {
			Content content = new Content(text);
			content.renameContentpackage(db, old_contentpackage, new_contentpackage);
//QQQQQ			User user = new User(text);
//QQQQQ			user.renameContentpackage(db, old_contentpackage, new_contentpackage);
//QQQQQ			Databases database = new Databases(text);
//QQQQQ			database.renameContentpackage(db, old_contentpackage, new_contentpackage);
		}
	}



	public void doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		String contentpackage = myrequest.getParameter("id");
	}



}
