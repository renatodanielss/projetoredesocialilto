package HardCore;

public class UCmaintainLinktypes {
	private Text text = new Text();



	public UCmaintainLinktypes() {
	}



	public UCmaintainLinktypes(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Linktype getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Linktype();
		String SQL = "select * from linktypes order by linktype";
		Linktype linktype = new Linktype();
		linktype.records(db, SQL);
		return linktype;
	}



	public Linktype getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Linktype();
		Linktype linktype = new Linktype();
		linktype.read(db, myrequest.getParameter("id"));
		return linktype;
	}



	public Linktype getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Linktype();
		Linktype linktype = new Linktype();
		linktype.read(db, myrequest.getParameter("id"));
		return linktype;
	}



	public Linktype getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Linktype();
		Linktype linktype = new Linktype();
		linktype.read(db, myrequest.getParameter("id"));
		return linktype;
	}



	public Linktype getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Linktype();
		Linktype linktype = new Linktype();
		linktype.read(db, myrequest.getParameter("id"));
		return linktype;
	}



	public Linktype doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Linktype();
		Linktype linktype = new Linktype();
		linktype.read(db, myrequest.getParameter("id"));
		linktype.getForm(myrequest);
		Linktype linktype_exists = new Linktype();
		linktype_exists.readLinktype(db, linktype.getLinktype());
		if (! linktype_exists.getLinktype().equals(linktype.getLinktype())) {
			Cms.CMSAudit("action=create linktype=" + linktype.getLinktype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			linktype.create(db);
			return linktype;
		} else {
			return linktype_exists;
		}
	}



	public Linktype doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Linktype();
		Linktype linktype = new Linktype();
		linktype.read(db, myrequest.getParameter("id"));
		String old_linktype = linktype.getLinktype();
		linktype.getForm(myrequest);
		String new_linktype = linktype.getLinktype();
		Linktype linktype_exists = new Linktype();
		linktype_exists.readLinktype(db, linktype.getLinktype());
		if ((old_linktype.equals(new_linktype)) || (! linktype_exists.getLinktype().equals(linktype.getLinktype()))) {
			Cms.CMSAudit("action=update linktype=" + linktype.getLinktype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			linktype.update(db);
			if (! old_linktype.equals(new_linktype)) {
				Content content = new Content(text);
				content.renameLinktype(db, old_linktype, new_linktype);
				Workflow workflow = new Workflow(text);
				workflow.renameLinktype(db, old_linktype, new_linktype);
			}
			return linktype;
		} else {
			return linktype_exists;
		}
	}



	public Linktype doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Linktype();
		Linktype linktype = new Linktype();
		linktype.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete linktype=" + linktype.getLinktype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		linktype.delete(db);
		Content content = new Content(text);
		content.renameLinktype(db, linktype.getLinktype(), "");
		Workflow workflow = new Workflow(text);
		workflow.renameLinktype(db, linktype.getLinktype(), "");
		return linktype;
	}



}
