package HardCore;

public class UCmaintainFilegroups {
	private Text text = new Text();



	public UCmaintainFilegroups() {
	}



	public UCmaintainFilegroups(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Filegroup getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Filegroup();
		String SQL = "select * from filegroups order by filegroup";
		Filegroup filegroup = new Filegroup();
		filegroup.records(db, SQL);
		return filegroup;
	}



	public Filegroup getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Filegroup();
		Filegroup filegroup = new Filegroup();
		filegroup.read(db, myrequest.getParameter("id"));
		return filegroup;
	}



	public Filegroup getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Filegroup();
		Filegroup filegroup = new Filegroup();
		filegroup.read(db, myrequest.getParameter("id"));
		return filegroup;
	}



	public Filegroup getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Filegroup();
		Filegroup filegroup = new Filegroup();
		filegroup.read(db, myrequest.getParameter("id"));
		return filegroup;
	}



	public Filegroup getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Filegroup();
		Filegroup filegroup = new Filegroup();
		filegroup.read(db, myrequest.getParameter("id"));
		return filegroup;
	}



	public Filegroup doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Filegroup();
		Filegroup filegroup = new Filegroup();
		filegroup.read(db, myrequest.getParameter("id"));
		filegroup.getForm(myrequest);
		Filegroup filegroup_exists = new Filegroup();
		filegroup_exists.readFilegroup(db, filegroup.getFilegroup());
		if (! filegroup_exists.getFilegroup().equals(filegroup.getFilegroup())) {
			Cms.CMSAudit("action=create filegroup=" + filegroup.getFilegroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			filegroup.create(db);
			return filegroup;
		} else {
			return filegroup_exists;
		}
	}



	public Filegroup doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Filegroup();
		Filegroup filegroup = new Filegroup();
		filegroup.read(db, myrequest.getParameter("id"));
		String old_filegroup = filegroup.getFilegroup();
		filegroup.getForm(myrequest);
		String new_filegroup = filegroup.getFilegroup();
		Filegroup filegroup_exists = new Filegroup();
		filegroup_exists.readFilegroup(db, filegroup.getFilegroup());
		if ((old_filegroup.equals(new_filegroup)) || (! filegroup_exists.getFilegroup().equals(filegroup.getFilegroup()))) {
			Cms.CMSAudit("action=update filegroup=" + filegroup.getFilegroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			filegroup.update(db);
			if (! old_filegroup.equals(new_filegroup)) {
				Content content = new Content(text);
				content.renameFilegroup(db, old_filegroup, new_filegroup);
				Workflow workflow = new Workflow(text);
				workflow.renameFilegroup(db, old_filegroup, new_filegroup);
			}
			return filegroup;
		} else {
			return filegroup_exists;
		}
	}



	public Filegroup doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Filegroup();
		Filegroup filegroup = new Filegroup();
		filegroup.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete filegroup=" + filegroup.getFilegroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		filegroup.delete(db);
		Content content = new Content(text);
		content.renameFilegroup(db, filegroup.getFilegroup(), "");
		Workflow workflow = new Workflow(text);
		workflow.renameFilegroup(db, filegroup.getFilegroup(), "");
		return filegroup;
	}



}
