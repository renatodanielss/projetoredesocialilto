package HardCore;

public class UCmaintainContentgroups {
	private Text text = new Text();



	public UCmaintainContentgroups() {
	}



	public UCmaintainContentgroups(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Contentgroup getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Contentgroup();
		String SQL = "select * from contentgroups order by contentgroup";
		Contentgroup contentgroup = new Contentgroup();
		contentgroup.records(db, SQL);
		return contentgroup;
	}



	public Contentgroup getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Contentgroup();
		Contentgroup contentgroup = new Contentgroup();
		contentgroup.read(db, myrequest.getParameter("id"));
		return contentgroup;
	}



	public Contentgroup getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Contentgroup();
		Contentgroup contentgroup = new Contentgroup();
		contentgroup.read(db, myrequest.getParameter("id"));
		return contentgroup;
	}



	public Contentgroup getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Contentgroup();
		Contentgroup contentgroup = new Contentgroup();
		contentgroup.read(db, myrequest.getParameter("id"));
		return contentgroup;
	}



	public Contentgroup getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Contentgroup();
		Contentgroup contentgroup = new Contentgroup();
		contentgroup.read(db, myrequest.getParameter("id"));
		return contentgroup;
	}



	public Contentgroup doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Contentgroup();
		Contentgroup contentgroup = new Contentgroup();
		contentgroup.read(db, myrequest.getParameter("id"));
		contentgroup.getForm(myrequest);
		Contentgroup contentgroup_exists = new Contentgroup();
		contentgroup_exists.readContentgroup(db, contentgroup.getContentgroup());
		if (! contentgroup_exists.getContentgroup().equals(contentgroup.getContentgroup())) {
			Cms.CMSAudit("action=create contentgroup=" + contentgroup.getContentgroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			contentgroup.create(db);
			return contentgroup;
		} else {
			return contentgroup_exists;
		}
	}



	public Contentgroup doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Contentgroup();
		Contentgroup contentgroup = new Contentgroup();
		contentgroup.read(db, myrequest.getParameter("id"));
		String old_contentgroup = contentgroup.getContentgroup();
		contentgroup.getForm(myrequest);
		String new_contentgroup = contentgroup.getContentgroup();
		Contentgroup contentgroup_exists = new Contentgroup();
		contentgroup_exists.readContentgroup(db, contentgroup.getContentgroup());
		if ((old_contentgroup.equals(new_contentgroup)) || (! contentgroup_exists.getContentgroup().equals(contentgroup.getContentgroup()))) {
			Cms.CMSAudit("action=update contentgroup=" + contentgroup.getContentgroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			contentgroup.update(db);
			if (! old_contentgroup.equals(new_contentgroup)) {
				Content content = new Content(text);
				content.renameContentgroup(db, old_contentgroup, new_contentgroup);
				Workflow workflow = new Workflow(text);
				workflow.renameContentgroup(db, old_contentgroup, new_contentgroup);
			}
			return contentgroup;
		} else {
			return contentgroup_exists;
		}
	}



	public Contentgroup doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Contentgroup();
		Contentgroup contentgroup = new Contentgroup();
		contentgroup.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete contentgroup=" + contentgroup.getContentgroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		contentgroup.delete(db);
		Content content = new Content(text);
		content.renameContentgroup(db, contentgroup.getContentgroup(), "");
		Workflow workflow = new Workflow(text);
		workflow.renameContentgroup(db, contentgroup.getContentgroup(), "");
		return contentgroup;
	}



}
