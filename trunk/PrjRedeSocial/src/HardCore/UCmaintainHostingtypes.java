package HardCore;

public class UCmaintainHostingtypes {
	private Text text = new Text();



	public UCmaintainHostingtypes() {
	}



	public UCmaintainHostingtypes(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Hostingtype getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Hostingtype();
		String SQL = "select * from hostingtypes order by hostingtype";
		Hostingtype hostingtype = new Hostingtype();
		hostingtype.records(db, SQL);
		return hostingtype;
	}



	public Hostingtype getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Hostingtype();
		Hostingtype hostingtype = new Hostingtype();
		hostingtype.read(db, myrequest.getParameter("id"));
		return hostingtype;
	}



	public Hostingtype getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Hostingtype();
		Hostingtype hostingtype = new Hostingtype();
		hostingtype.read(db, myrequest.getParameter("id"));
		return hostingtype;
	}



	public Hostingtype getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Hostingtype();
		Hostingtype hostingtype = new Hostingtype();
		hostingtype.read(db, myrequest.getParameter("id"));
		return hostingtype;
	}



	public Hostingtype getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Hostingtype();
		Hostingtype hostingtype = new Hostingtype();
		hostingtype.read(db, myrequest.getParameter("id"));
		return hostingtype;
	}



	public Hostingtype doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Hostingtype();
		Hostingtype hostingtype = new Hostingtype();
		hostingtype.read(db, myrequest.getParameter("id"));
		hostingtype.getForm(myrequest);
		Cms.CMSAudit("action=create hostingtype=" + hostingtype.getHostingtype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		hostingtype.create(db);
		return hostingtype;
	}



	public Hostingtype doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Hostingtype();
		Hostingtype hostingtype = new Hostingtype();
		hostingtype.read(db, myrequest.getParameter("id"));
		hostingtype.getForm(myrequest);
		Cms.CMSAudit("action=update hostingtype=" + hostingtype.getHostingtype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		hostingtype.update(db);
		return hostingtype;
	}



	public Hostingtype doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Hostingtype();
		Hostingtype hostingtype = new Hostingtype();
		hostingtype.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete hostingtype=" + hostingtype.getHostingtype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		hostingtype.delete(db);
		return hostingtype;
	}



}
