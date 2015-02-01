package HardCore;

public class UCmaintainContenttypes {
	private Text text = new Text();



	public UCmaintainContenttypes() {
	}



	public UCmaintainContenttypes(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Contenttype getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Contenttype();
		String SQL = "select * from contenttypes order by contenttype";
		Contenttype contenttype = new Contenttype();
		contenttype.records(db, SQL);
		return contenttype;
	}



	public Contenttype getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Contenttype();
		Contenttype contenttype = new Contenttype();
		contenttype.read(db, myrequest.getParameter("id"));
		return contenttype;
	}



	public Contenttype getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Contenttype();
		Contenttype contenttype = new Contenttype();
		contenttype.read(db, myrequest.getParameter("id"));
		return contenttype;
	}



	public Contenttype getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Contenttype();
		Contenttype contenttype = new Contenttype();
		contenttype.read(db, myrequest.getParameter("id"));
		return contenttype;
	}



	public Contenttype getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Contenttype();
		Contenttype contenttype = new Contenttype();
		contenttype.read(db, myrequest.getParameter("id"));
		return contenttype;
	}



	public Contenttype doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Contenttype();
		Contenttype contenttype = new Contenttype();
		contenttype.read(db, myrequest.getParameter("id"));
		contenttype.getForm(myrequest);
		Contenttype contenttype_exists = new Contenttype();
		contenttype_exists.readContenttype(db, contenttype.getContenttype());
		if (! contenttype_exists.getContenttype().equals(contenttype.getContenttype())) {
			Cms.CMSAudit("action=create contenttype=" + contenttype.getContenttype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			contenttype.create(db);
			return contenttype;
		} else {
			return contenttype_exists;
		}
	}



	public Contenttype doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Contenttype();
		Contenttype contenttype = new Contenttype();
		contenttype.read(db, myrequest.getParameter("id"));
		String old_contenttype = contenttype.getContenttype();
		contenttype.getForm(myrequest);
		String new_contenttype = contenttype.getContenttype();
		Contenttype contenttype_exists = new Contenttype();
		contenttype_exists.readContenttype(db, contenttype.getContenttype());
		if ((old_contenttype.equals(new_contenttype)) || (! contenttype_exists.getContenttype().equals(contenttype.getContenttype()))) {
			Cms.CMSAudit("action=update contenttype=" + contenttype.getContenttype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			contenttype.update(db);
			if (! old_contenttype.equals(new_contenttype)) {
				Content content = new Content(text);
				content.renameContenttype(db, old_contenttype, new_contenttype);
				Workflow workflow = new Workflow(text);
				workflow.renameContenttype(db, old_contenttype, new_contenttype);
			}
			return contenttype;
		} else {
			return contenttype_exists;
		}
	}



	public Contenttype doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Contenttype();
		Contenttype contenttype = new Contenttype();
		contenttype.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete contenttype=" + contenttype.getContenttype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		contenttype.delete(db);
		Content content = new Content(text);
		content.renameContenttype(db, contenttype.getContenttype(), "");
		Workflow workflow = new Workflow(text);
		workflow.renameContenttype(db, contenttype.getContenttype(), "");
		return contenttype;
	}



}
