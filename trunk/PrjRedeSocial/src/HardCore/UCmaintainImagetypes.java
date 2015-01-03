package HardCore;

public class UCmaintainImagetypes {
	private Text text = new Text();



	public UCmaintainImagetypes() {
	}



	public UCmaintainImagetypes(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Imagetype getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imagetype();
		String SQL = "select * from imagetypes order by imagetype";
		Imagetype imagetype = new Imagetype();
		imagetype.records(db, SQL);
		return imagetype;
	}



	public Imagetype getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imagetype();
		Imagetype imagetype = new Imagetype();
		imagetype.read(db, myrequest.getParameter("id"));
		return imagetype;
	}



	public Imagetype getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imagetype();
		Imagetype imagetype = new Imagetype();
		imagetype.read(db, myrequest.getParameter("id"));
		return imagetype;
	}



	public Imagetype getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imagetype();
		Imagetype imagetype = new Imagetype();
		imagetype.read(db, myrequest.getParameter("id"));
		return imagetype;
	}



	public Imagetype getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imagetype();
		Imagetype imagetype = new Imagetype();
		imagetype.read(db, myrequest.getParameter("id"));
		return imagetype;
	}



	public Imagetype doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imagetype();
		Imagetype imagetype = new Imagetype();
		imagetype.read(db, myrequest.getParameter("id"));
		imagetype.getForm(myrequest);
		Imagetype imagetype_exists = new Imagetype();
		imagetype_exists.readImagetype(db, imagetype.getImagetype());
		if (! imagetype_exists.getImagetype().equals(imagetype.getImagetype())) {
			Cms.CMSAudit("action=create imagetype=" + imagetype.getImagetype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			imagetype.create(db);
			return imagetype;
		} else {
			return imagetype_exists;
		}
	}



	public Imagetype doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imagetype();
		Imagetype imagetype = new Imagetype();
		imagetype.read(db, myrequest.getParameter("id"));
		String old_imagetype = imagetype.getImagetype();
		imagetype.getForm(myrequest);
		String new_imagetype = imagetype.getImagetype();
		Imagetype imagetype_exists = new Imagetype();
		imagetype_exists.readImagetype(db, imagetype.getImagetype());
		if ((old_imagetype.equals(new_imagetype)) || (! imagetype_exists.getImagetype().equals(imagetype.getImagetype()))) {
			Cms.CMSAudit("action=update imagetype=" + imagetype.getImagetype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			imagetype.update(db);
			if (! old_imagetype.equals(new_imagetype)) {
				Content content = new Content(text);
				content.renameImagetype(db, old_imagetype, new_imagetype);
				Workflow workflow = new Workflow(text);
				workflow.renameImagetype(db, old_imagetype, new_imagetype);
			}
			return imagetype;
		} else {
			return imagetype_exists;
		}
	}



	public Imagetype doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imagetype();
		Imagetype imagetype = new Imagetype();
		imagetype.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete imagetype=" + imagetype.getImagetype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		imagetype.delete(db);
		Content content = new Content(text);
		content.renameImagetype(db, imagetype.getImagetype(), "");
		Workflow workflow = new Workflow(text);
		workflow.renameImagetype(db, imagetype.getImagetype(), "");
		return imagetype;
	}



}
