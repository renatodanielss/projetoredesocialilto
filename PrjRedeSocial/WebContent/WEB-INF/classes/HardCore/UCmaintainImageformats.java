package HardCore;

public class UCmaintainImageformats {
	private Text text = new Text();



	public UCmaintainImageformats() {
	}



	public UCmaintainImageformats(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Imageformat getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imageformat();
		String SQL = "select * from imageformats order by filenameextension";
		Imageformat imageformat = new Imageformat();
		imageformat.records(db, SQL);
		return imageformat;
	}



	public Imageformat getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imageformat();
		Imageformat imageformat = new Imageformat();
		imageformat.read(db, myrequest.getParameter("id"));
		return imageformat;
	}



	public Imageformat getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imageformat();
		Imageformat imageformat = new Imageformat();
		imageformat.read(db, myrequest.getParameter("id"));
		return imageformat;
	}



	public Imageformat getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imageformat();
		Imageformat imageformat = new Imageformat();
		imageformat.read(db, myrequest.getParameter("id"));
		return imageformat;
	}



	public Imageformat getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imageformat();
		Imageformat imageformat = new Imageformat();
		imageformat.read(db, myrequest.getParameter("id"));
		return imageformat;
	}



	public Imageformat doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imageformat();
		Imageformat imageformat = new Imageformat();
		imageformat.read(db, myrequest.getParameter("id"));
		imageformat.getForm(myrequest);
		Cms.CMSAudit("action=create imageformat=" + imageformat.getFilenameextension() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		imageformat.create(db);
		return imageformat;
	}



	public Imageformat doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imageformat();
		Imageformat imageformat = new Imageformat();
		imageformat.read(db, myrequest.getParameter("id"));
		imageformat.getForm(myrequest);
		Cms.CMSAudit("action=update imageformat=" + imageformat.getFilenameextension() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		imageformat.update(db);
		return imageformat;
	}



	public Imageformat doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imageformat();
		Imageformat imageformat = new Imageformat();
		imageformat.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete imageformat=" + imageformat.getFilenameextension() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		imageformat.delete(db);
		return imageformat;
	}



}
