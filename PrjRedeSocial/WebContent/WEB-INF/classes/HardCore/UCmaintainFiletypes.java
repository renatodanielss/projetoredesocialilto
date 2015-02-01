package HardCore;

public class UCmaintainFiletypes {
	private Text text = new Text();



	public UCmaintainFiletypes() {
	}



	public UCmaintainFiletypes(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Filetype getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Filetype();
		String SQL = "select * from filetypes order by filetype";
		Filetype filetype = new Filetype();
		filetype.records(db, SQL);
		return filetype;
	}



	public Filetype getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Filetype();
		Filetype filetype = new Filetype();
		filetype.read(db, myrequest.getParameter("id"));
		return filetype;
	}



	public Filetype getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Filetype();
		Filetype filetype = new Filetype();
		filetype.read(db, myrequest.getParameter("id"));
		return filetype;
	}



	public Filetype getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Filetype();
		Filetype filetype = new Filetype();
		filetype.read(db, myrequest.getParameter("id"));
		return filetype;
	}



	public Filetype getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Filetype();
		Filetype filetype = new Filetype();
		filetype.read(db, myrequest.getParameter("id"));
		return filetype;
	}



	public Filetype doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Filetype();
		Filetype filetype = new Filetype();
		filetype.read(db, myrequest.getParameter("id"));
		filetype.getForm(myrequest);
		Filetype filetype_exists = new Filetype();
		filetype_exists.readFiletype(db, filetype.getFiletype());
		if (! filetype_exists.getFiletype().equals(filetype.getFiletype())) {
			Cms.CMSAudit("action=create filetype=" + filetype.getFiletype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			filetype.create(db);
			return filetype;
		} else {
			return filetype_exists;
		}
	}



	public Filetype doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Filetype();
		Filetype filetype = new Filetype();
		filetype.read(db, myrequest.getParameter("id"));
		String old_filetype = filetype.getFiletype();
		filetype.getForm(myrequest);
		String new_filetype = filetype.getFiletype();
		Filetype filetype_exists = new Filetype();
		filetype_exists.readFiletype(db, filetype.getFiletype());
		if ((old_filetype.equals(new_filetype)) || (! filetype_exists.getFiletype().equals(filetype.getFiletype()))) {
			Cms.CMSAudit("action=update filetype=" + filetype.getFiletype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			filetype.update(db);
			if (! old_filetype.equals(new_filetype)) {
				Content content = new Content(text);
				content.renameFiletype(db, old_filetype, new_filetype);
				Workflow workflow = new Workflow(text);
				workflow.renameFiletype(db, old_filetype, new_filetype);
			}
			return filetype;
		} else {
			return filetype_exists;
		}
	}



	public Filetype doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Filetype();
		Filetype filetype = new Filetype();
		filetype.read(db, myrequest.getParameter("id"));
			Cms.CMSAudit("action=delete filetype=" + filetype.getFiletype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		filetype.delete(db);
		Content content = new Content(text);
		content.renameFiletype(db, filetype.getFiletype(), "");
		Workflow workflow = new Workflow(text);
		workflow.renameFiletype(db, filetype.getFiletype(), "");
		return filetype;
	}



}
