package HardCore;

public class UCmaintainWebsites {
	private Text text = new Text();



	public UCmaintainWebsites() {
	}



	public UCmaintainWebsites(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Website getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Website(text);
		String SQL = "select * from websites order by domain,remote,language";
		Website website = new Website(text);
		website.records(db, SQL);
		return website;
	}



	public Website getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Website(text);
		Website website = new Website(text);
		website.read(db, myrequest.getParameter("id"));
		return website;
	}



	public Website getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Website(text);
		Website website = new Website(text);
		website.read(db, myrequest.getParameter("id"));
		return website;
	}



	public Website getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Website(text);
		Website website = new Website(text);
		website.read(db, myrequest.getParameter("id"));
		return website;
	}



	public Website getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Website(text);
		Website website = new Website(text);
		website.read(db, myrequest.getParameter("id"));
		return website;
	}



	public Website doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Website(text);
		Website website = new Website(text);
		website.read(db, myrequest.getParameter("id"));
		website.getForm(myrequest);
		Cms.CMSAudit("action=create website=" + website.getDomain() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		website.create(db);
		return website;
	}



	public Website doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Website(text);
		Website website = new Website(text);
		website.read(db, myrequest.getParameter("id"));
		website.getForm(myrequest);
		Cms.CMSAudit("action=update website=" + website.getDomain() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		website.update(db);
		return website;
	}



	public Website doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Website(text);
		Website website = new Website(text);
		website.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete website=" + website.getDomain() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		website.delete(db);
		return website;
	}



}
