package HardCore;

public class UCmaintainHostinggroups {
	private Text text = new Text();



	public UCmaintainHostinggroups() {
	}



	public UCmaintainHostinggroups(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Hostinggroup getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Hostinggroup();
		String SQL = "select * from hostinggroups order by hostinggroup";
		Hostinggroup hostinggroup = new Hostinggroup();
		hostinggroup.records(db, SQL);
		return hostinggroup;
	}



	public Hostinggroup getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Hostinggroup();
		Hostinggroup hostinggroup = new Hostinggroup();
		hostinggroup.read(db, myrequest.getParameter("id"));
		return hostinggroup;
	}



	public Hostinggroup getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Hostinggroup();
		Hostinggroup hostinggroup = new Hostinggroup();
		hostinggroup.read(db, myrequest.getParameter("id"));
		return hostinggroup;
	}



	public Hostinggroup getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Hostinggroup();
		Hostinggroup hostinggroup = new Hostinggroup();
		hostinggroup.read(db, myrequest.getParameter("id"));
		return hostinggroup;
	}



	public Hostinggroup getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Hostinggroup();
		Hostinggroup hostinggroup = new Hostinggroup();
		hostinggroup.read(db, myrequest.getParameter("id"));
		return hostinggroup;
	}



	public Hostinggroup doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Hostinggroup();
		Hostinggroup hostinggroup = new Hostinggroup();
		hostinggroup.read(db, myrequest.getParameter("id"));
		hostinggroup.getForm(myrequest);
		Cms.CMSAudit("action=create hostinggroup=" + hostinggroup.getHostinggroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		hostinggroup.create(db);
		return hostinggroup;
	}



	public Hostinggroup doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Hostinggroup();
		Hostinggroup hostinggroup = new Hostinggroup();
		hostinggroup.read(db, myrequest.getParameter("id"));
		hostinggroup.getForm(myrequest);
		Cms.CMSAudit("action=update hostinggroup=" + hostinggroup.getHostinggroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		hostinggroup.update(db);
		return hostinggroup;
	}



	public Hostinggroup doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Hostinggroup();
		Hostinggroup hostinggroup = new Hostinggroup();
		hostinggroup.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete hostinggroup=" + hostinggroup.getHostinggroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		hostinggroup.delete(db);
		return hostinggroup;
	}



}
