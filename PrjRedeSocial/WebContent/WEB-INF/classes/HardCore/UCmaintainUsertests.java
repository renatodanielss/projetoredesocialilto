package HardCore;

public class UCmaintainUsertests {
	private String error = "";
	private Text text = new Text();



	public UCmaintainUsertests() {
	}



	public UCmaintainUsertests(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Usertest getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usertest();
		accesspermission = RequireUser.ExperienceAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Usertest();
		String SQL = "select * from usertests order by usertest,variants,id";
		Usertest usertest = new Usertest();
		usertest.records(db, SQL);
		return usertest;
	}



	public Usertest getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usertest();
		accesspermission = RequireUser.ExperienceAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Usertest();
		Usertest usertest = new Usertest();
		usertest.read(db, myrequest.getParameter("id"));
		return usertest;
	}



	public Usertest getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usertest();
		accesspermission = RequireUser.ExperienceAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Usertest();
		Usertest usertest = new Usertest();
		usertest.read(db, myrequest.getParameter("id"));
		return usertest;
	}



	public Usertest getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usertest();
		accesspermission = RequireUser.ExperienceAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Usertest();
		Usertest usertest = new Usertest();
		usertest.read(db, myrequest.getParameter("id"));
		return usertest;
	}



	public Usertest getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usertest();
		accesspermission = RequireUser.ExperienceAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Usertest();
		Usertest usertest = new Usertest();
		usertest.read(db, myrequest.getParameter("id"));
		return usertest;
	}



	public Usertest doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usertest();
		accesspermission = RequireUser.ExperienceAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Usertest();
		Usertest usertest = new Usertest();
		usertest.read(db, myrequest.getParameter("id"));
		usertest.getForm(myrequest);
		Cms.CMSAudit("action=create usertest=" + usertest.getUsertest() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		usertest.create(db);
		return usertest;
	}



	public Usertest doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usertest();
		accesspermission = RequireUser.ExperienceAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Usertest();
		Usertest usertest = new Usertest();
		usertest.read(db, myrequest.getParameter("id"));
		usertest.getForm(myrequest);
		Cms.CMSAudit("action=update usertest=" + usertest.getUsertest() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		usertest.update(db);
		return usertest;
	}



	public Usertest doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usertest();
		accesspermission = RequireUser.ExperienceAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Usertest();
		Usertest usertest = new Usertest();
		usertest.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete usertest=" + usertest.getUsertest() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		usertest.delete(db);
		return usertest;
	}



	public String getError() {
		return error;
	}



}
