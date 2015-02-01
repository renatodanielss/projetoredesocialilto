package HardCore;

public class UCmaintainUsergroups {
	private Text text = new Text();



	public UCmaintainUsergroups() {
	}



	public UCmaintainUsergroups(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Usergroup getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usergroup();
		String SQL = "select * from usergroups order by usergroup";
		Usergroup usergroup = new Usergroup();
		usergroup.records(db, SQL);
		return usergroup;
	}



	public Usergroup getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usergroup();
		Usergroup usergroup = new Usergroup();
		usergroup.read(db, myrequest.getParameter("id"));
		usergroup.records(db, "select * from usergroups order by usergroup");
		return usergroup;
	}



	public Usergroup getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usergroup();
		Usergroup usergroup = new Usergroup();
		usergroup.read(db, myrequest.getParameter("id"));
		usergroup.records(db, "select * from usergroups order by usergroup");
		return usergroup;
	}



	public Usergroup getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usergroup();
		Usergroup usergroup = new Usergroup();
		usergroup.read(db, myrequest.getParameter("id"));
		usergroup.records(db, "select * from usergroups order by usergroup");
		return usergroup;
	}



	public Usergroup getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usergroup();
		Usergroup usergroup = new Usergroup();
		usergroup.read(db, myrequest.getParameter("id"));
		usergroup.records(db, "select * from usergroups order by usergroup");
		return usergroup;
	}



	public Usergroup doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usergroup();
		Usergroup usergroup = new Usergroup();
		usergroup.read(db, myrequest.getParameter("id"));
		usergroup.getForm(myrequest);
		Usergroup usergroup_exists = new Usergroup();
		usergroup_exists.readUsergroup(db, usergroup.getUsergroup());
		if (! usergroup_exists.getUsergroup().equals(usergroup.getUsergroup())) {
			Cms.CMSAudit("action=create usergroup=" + usergroup.getUsergroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			usergroup.create(db);
			return usergroup;
		} else {
			return usergroup_exists;
		}
	}



	public Usergroup doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usergroup();
		Usergroup usergroup = new Usergroup();
		usergroup.read(db, myrequest.getParameter("id"));
		String old_usergroup = usergroup.getUsergroup();
		usergroup.getForm(myrequest);
		String new_usergroup = usergroup.getUsergroup();
		Usergroup usergroup_exists = new Usergroup();
		usergroup_exists.readUsergroup(db, usergroup.getUsergroup());
		if ((old_usergroup.equals(new_usergroup)) || (! usergroup_exists.getUsergroup().equals(usergroup.getUsergroup()))) {
			Cms.CMSAudit("action=update usergroup=" + usergroup.getUsergroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			usergroup.update(db);
			if (! old_usergroup.equals(new_usergroup)) {
				usergroup.renameUsergroup(db, old_usergroup, new_usergroup);
				Content mycontent = new Content(text);
				mycontent.renameUsergroup(db, old_usergroup, new_usergroup);
				Contentgroup mycontentgroup = new Contentgroup();
				mycontentgroup.renameUsergroup(db, old_usergroup, new_usergroup);
				Contenttype mycontenttype = new Contenttype();
				mycontenttype.renameUsergroup(db, old_usergroup, new_usergroup);
				Filegroup myfilegroup = new Filegroup();
				myfilegroup.renameUsergroup(db, old_usergroup, new_usergroup);
				Filetype myfiletype = new Filetype();
				myfiletype.renameUsergroup(db, old_usergroup, new_usergroup);
				Imagegroup myimagegroup = new Imagegroup();
				myimagegroup.renameUsergroup(db, old_usergroup, new_usergroup);
				Imagetype myimagetype = new Imagetype();
				myimagetype.renameUsergroup(db, old_usergroup, new_usergroup);
				Linkgroup mylinkgroup = new Linkgroup();
				mylinkgroup.renameUsergroup(db, old_usergroup, new_usergroup);
				Linktype mylinktype = new Linktype();
				mylinktype.renameUsergroup(db, old_usergroup, new_usergroup);
				Productgroup myproductgroup = new Productgroup();
				myproductgroup.renameUsergroup(db, old_usergroup, new_usergroup);
				Producttype myproducttype = new Producttype();
				myproducttype.renameUsergroup(db, old_usergroup, new_usergroup);
				User myuser = new User(text);
				myuser.renameUsergroup(db, old_usergroup, new_usergroup);
				Workflow myworkflow = new Workflow(text);
				myworkflow.renameUsergroup(db, old_usergroup, new_usergroup);
				Databases mydatabases = new Databases(text);
				mydatabases.renameUsergroup(db, old_usergroup, new_usergroup);
				Version myversions = new Version();
				myversions.renameUsergroup(db, old_usergroup, new_usergroup);
				Element myelements = new Element();
				myelements.renameUsergroup(db, old_usergroup, new_usergroup);
			}
			return usergroup;
		} else {
			return usergroup_exists;
		}
	}



	public Usergroup doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usergroup();
		Usergroup usergroup = new Usergroup();
		usergroup.read(db, myrequest.getParameter("id"));
		String old_usergroup = usergroup.getUsergroup();
		String new_usergroup = "";
		Cms.CMSAudit("action=delete usergroup=" + usergroup.getUsergroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		usergroup.delete(db);
		usergroup.renameUsergroup(db, old_usergroup, new_usergroup);
		Content mycontent = new Content(text);
		mycontent.renameUsergroup(db, old_usergroup, new_usergroup);
		Contentgroup mycontentgroup = new Contentgroup();
		mycontentgroup.renameUsergroup(db, old_usergroup, new_usergroup);
		Contenttype mycontenttype = new Contenttype();
		mycontenttype.renameUsergroup(db, old_usergroup, new_usergroup);
		Filegroup myfilegroup = new Filegroup();
		myfilegroup.renameUsergroup(db, old_usergroup, new_usergroup);
		Filetype myfiletype = new Filetype();
		myfiletype.renameUsergroup(db, old_usergroup, new_usergroup);
		Imagegroup myimagegroup = new Imagegroup();
		myimagegroup.renameUsergroup(db, old_usergroup, new_usergroup);
		Imagetype myimagetype = new Imagetype();
		myimagetype.renameUsergroup(db, old_usergroup, new_usergroup);
		Linkgroup mylinkgroup = new Linkgroup();
		mylinkgroup.renameUsergroup(db, old_usergroup, new_usergroup);
		Linktype mylinktype = new Linktype();
		mylinktype.renameUsergroup(db, old_usergroup, new_usergroup);
		Productgroup myproductgroup = new Productgroup();
		myproductgroup.renameUsergroup(db, old_usergroup, new_usergroup);
		Producttype myproducttype = new Producttype();
		myproducttype.renameUsergroup(db, old_usergroup, new_usergroup);
		User myuser = new User(text);
		myuser.renameUsergroup(db, old_usergroup, new_usergroup);
		Workflow myworkflow = new Workflow(text);
		myworkflow.renameUsergroup(db, old_usergroup, new_usergroup);
		Databases mydatabases = new Databases(text);
		mydatabases.renameUsergroup(db, old_usergroup, new_usergroup);
		Version myversions = new Version();
		myversions.renameUsergroup(db, old_usergroup, new_usergroup);
		Element myelements = new Element();
		myelements.renameUsergroup(db, old_usergroup, new_usergroup);
		return usergroup;
	}



}
