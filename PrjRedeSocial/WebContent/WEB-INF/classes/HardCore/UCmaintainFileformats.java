package HardCore;

public class UCmaintainFileformats {
	private Text text = new Text();



	public UCmaintainFileformats() {
	}



	public UCmaintainFileformats(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Fileformat getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Fileformat();
		String SQL = "select * from fileformats order by filenameextension";
		Fileformat fileformat = new Fileformat();
		fileformat.records(db, SQL);
		return fileformat;
	}



	public Fileformat getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Fileformat();
		Fileformat fileformat = new Fileformat();
		fileformat.read(db, myrequest.getParameter("id"));
		return fileformat;
	}



	public Fileformat getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Fileformat();
		Fileformat fileformat = new Fileformat();
		fileformat.read(db, myrequest.getParameter("id"));
		return fileformat;
	}



	public Fileformat getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Fileformat();
		Fileformat fileformat = new Fileformat();
		fileformat.read(db, myrequest.getParameter("id"));
		return fileformat;
	}



	public Fileformat getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Fileformat();
		Fileformat fileformat = new Fileformat();
		fileformat.read(db, myrequest.getParameter("id"));
		return fileformat;
	}



	public Fileformat doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Fileformat();
		Fileformat fileformat = new Fileformat();
		fileformat.read(db, myrequest.getParameter("id"));
		fileformat.getForm(myrequest);
		Cms.CMSAudit("action=create fileformat=" + fileformat.getFilenameextension() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		fileformat.create(db);
		return fileformat;
	}



	public Fileformat doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Fileformat();
		Fileformat fileformat = new Fileformat();
		fileformat.read(db, myrequest.getParameter("id"));
		fileformat.getForm(myrequest);
		Cms.CMSAudit("action=update fileformat=" + fileformat.getFilenameextension() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		fileformat.update(db);
		return fileformat;
	}



	public Fileformat doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Fileformat();
		Fileformat fileformat = new Fileformat();
		fileformat.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete fileformat=" + fileformat.getFilenameextension() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		fileformat.delete(db);
		return fileformat;
	}



}
