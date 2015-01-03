package HardCore;

public class UCmaintainLinkgroups {
	private Text text = new Text();



	public UCmaintainLinkgroups() {
	}



	public UCmaintainLinkgroups(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Linkgroup getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Linkgroup();
		String SQL = "select * from linkgroups order by linkgroup";
		Linkgroup linkgroup = new Linkgroup();
		linkgroup.records(db, SQL);
		return linkgroup;
	}



	public Linkgroup getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Linkgroup();
		Linkgroup linkgroup = new Linkgroup();
		linkgroup.read(db, myrequest.getParameter("id"));
		return linkgroup;
	}



	public Linkgroup getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Linkgroup();
		Linkgroup linkgroup = new Linkgroup();
		linkgroup.read(db, myrequest.getParameter("id"));
		return linkgroup;
	}



	public Linkgroup getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Linkgroup();
		Linkgroup linkgroup = new Linkgroup();
		linkgroup.read(db, myrequest.getParameter("id"));
		return linkgroup;
	}



	public Linkgroup getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Linkgroup();
		Linkgroup linkgroup = new Linkgroup();
		linkgroup.read(db, myrequest.getParameter("id"));
		return linkgroup;
	}



	public Linkgroup doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Linkgroup();
		Linkgroup linkgroup = new Linkgroup();
		linkgroup.read(db, myrequest.getParameter("id"));
		linkgroup.getForm(myrequest);
		Linkgroup linkgroup_exists = new Linkgroup();
		linkgroup_exists.readLinkgroup(db, linkgroup.getLinkgroup());
		if (! linkgroup_exists.getLinkgroup().equals(linkgroup.getLinkgroup())) {
			Cms.CMSAudit("action=create linkgroup=" + linkgroup.getLinkgroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			linkgroup.create(db);
			return linkgroup;
		} else {
			return linkgroup_exists;
		}
	}



	public Linkgroup doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Linkgroup();
		Linkgroup linkgroup = new Linkgroup();
		linkgroup.read(db, myrequest.getParameter("id"));
		String old_linkgroup = linkgroup.getLinkgroup();
		linkgroup.getForm(myrequest);
		String new_linkgroup = linkgroup.getLinkgroup();
		Linkgroup linkgroup_exists = new Linkgroup();
		linkgroup_exists.readLinkgroup(db, linkgroup.getLinkgroup());
		if ((old_linkgroup.equals(new_linkgroup)) || (! linkgroup_exists.getLinkgroup().equals(linkgroup.getLinkgroup()))) {
			Cms.CMSAudit("action=update linkgroup=" + linkgroup.getLinkgroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			linkgroup.update(db);
			if (! old_linkgroup.equals(new_linkgroup)) {
				Content content = new Content(text);
				content.renameLinkgroup(db, old_linkgroup, new_linkgroup);
				Workflow workflow = new Workflow(text);
				workflow.renameLinkgroup(db, old_linkgroup, new_linkgroup);
			}
			return linkgroup;
		} else {
			return linkgroup_exists;
		}
	}



	public Linkgroup doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Linkgroup();
		Linkgroup linkgroup = new Linkgroup();
		linkgroup.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete linkgroup=" + linkgroup.getLinkgroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		linkgroup.delete(db);
		Content content = new Content(text);
		content.renameLinkgroup(db, linkgroup.getLinkgroup(), "");
		Workflow workflow = new Workflow(text);
		workflow.renameLinkgroup(db, linkgroup.getLinkgroup(), "");
		return linkgroup;
	}



}
