package HardCore;

public class UCmaintainImagegroups {
	private Text text = new Text();



	public UCmaintainImagegroups() {
	}



	public UCmaintainImagegroups(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Imagegroup getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imagegroup();
		String SQL = "select * from imagegroups order by imagegroup";
		Imagegroup imagegroup = new Imagegroup();
		imagegroup.records(db, SQL);
		return imagegroup;
	}



	public Imagegroup getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imagegroup();
		Imagegroup imagegroup = new Imagegroup();
		imagegroup.read(db, myrequest.getParameter("id"));
		return imagegroup;
	}



	public Imagegroup getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imagegroup();
		Imagegroup imagegroup = new Imagegroup();
		imagegroup.read(db, myrequest.getParameter("id"));
		return imagegroup;
	}



	public Imagegroup getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imagegroup();
		Imagegroup imagegroup = new Imagegroup();
		imagegroup.read(db, myrequest.getParameter("id"));
		return imagegroup;
	}



	public Imagegroup getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imagegroup();
		Imagegroup imagegroup = new Imagegroup();
		imagegroup.read(db, myrequest.getParameter("id"));
		return imagegroup;
	}



	public Imagegroup doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imagegroup();
		Imagegroup imagegroup = new Imagegroup();
		imagegroup.read(db, myrequest.getParameter("id"));
		imagegroup.getForm(myrequest);
		Imagegroup imagegroup_exists = new Imagegroup();
		imagegroup_exists.readImagegroup(db, imagegroup.getImagegroup());
		if (! imagegroup_exists.getImagegroup().equals(imagegroup.getImagegroup())) {
			Cms.CMSAudit("action=create imagegroup=" + imagegroup.getImagegroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			imagegroup.create(db);
			return imagegroup;
		} else {
			return imagegroup_exists;
		}
	}



	public Imagegroup doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imagegroup();
		Imagegroup imagegroup = new Imagegroup();
		imagegroup.read(db, myrequest.getParameter("id"));
		String old_imagegroup = imagegroup.getImagegroup();
		imagegroup.getForm(myrequest);
		String new_imagegroup = imagegroup.getImagegroup();
		Imagegroup imagegroup_exists = new Imagegroup();
		imagegroup_exists.readImagegroup(db, imagegroup.getImagegroup());
		if ((old_imagegroup.equals(new_imagegroup)) || (! imagegroup_exists.getImagegroup().equals(imagegroup.getImagegroup()))) {
			Cms.CMSAudit("action=update imagegroup=" + imagegroup.getImagegroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			imagegroup.update(db);
			if (! old_imagegroup.equals(new_imagegroup)) {
				Content content = new Content(text);
				content.renameImagegroup(db, old_imagegroup, new_imagegroup);
				Workflow workflow = new Workflow(text);
				workflow.renameImagegroup(db, old_imagegroup, new_imagegroup);
			}
			return imagegroup;
		} else {
			return imagegroup_exists;
		}
	}



	public Imagegroup doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Imagegroup();
		Imagegroup imagegroup = new Imagegroup();
		imagegroup.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete imagegroup=" + imagegroup.getImagegroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		imagegroup.delete(db);
		Content content = new Content(text);
		content.renameImagegroup(db, imagegroup.getImagegroup(), "");
		Workflow workflow = new Workflow(text);
		workflow.renameImagegroup(db, imagegroup.getImagegroup(), "");
		return imagegroup;
	}



}
