package HardCore;

public class UCmaintainVersions {
	private Text text = new Text();



	public UCmaintainVersions() {
	}



	public UCmaintainVersions(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Version getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Version();
		String SQL = "select * from versions order by version";
		Version version = new Version();
		version.records(db, SQL);
		return version;
	}



	public Version getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Version();
		Version version = new Version();
		version.read(db, myrequest.getParameter("id"));
		return version;
	}



	public Version getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Version();
		Version version = new Version();
		version.read(db, myrequest.getParameter("id"));
		return version;
	}



	public Version getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Version();
		Version version = new Version();
		version.read(db, myrequest.getParameter("id"));
		return version;
	}



	public Version getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Version();
		Version version = new Version();
		version.read(db, myrequest.getParameter("id"));
		return version;
	}



	public Version doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Version();
		Version version = new Version();
		version.read(db, myrequest.getParameter("id"));
		version.getForm(myrequest);
		Version version_exists = new Version();
		version_exists.readVersion(db, version.getVersion());
		if (! version_exists.getVersion().equals(version.getVersion())) {
			Cms.CMSAudit("action=create version=" + version.getVersion() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			version.create(db);
			return version;
		} else {
			return version_exists;
		}
	}



	public Version doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Version();
		Version version = new Version();
		version.read(db, myrequest.getParameter("id"));
		String old_version = version.getVersion();
		version.getForm(myrequest);
		String new_version = version.getVersion();
		Version version_exists = new Version();
		version_exists.readVersion(db, version.getVersion());
		if ((old_version.equals(new_version)) || (! version_exists.getVersion().equals(version.getVersion()))) {
			Cms.CMSAudit("action=update version=" + version.getVersion() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			version.update(db);
			if (! old_version.equals(new_version)) {
				Content content = new Content(text);
				content.renameVersion(db, old_version, new_version);
				Workflow workflow = new Workflow(text);
				workflow.renameVersion(db, old_version, new_version);
			}
			return version;
		} else {
			return version_exists;
		}
	}



	public Version doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Version();
		Version version = new Version();
		version.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete version=" + version.getVersion() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		version.delete(db);
		Content content = new Content(text);
		content.renameVersion(db, version.getVersion(), "");
		Workflow workflow = new Workflow(text);
		workflow.renameVersion(db, version.getVersion(), "");
		return version;
	}



}
