package HardCore;

public class UCmaintainUsertypes {
	private Text text = new Text();



	public UCmaintainUsertypes() {
	}



	public UCmaintainUsertypes(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Usertype getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usertype();
		String SQL = "select * from usertypes order by usertype";
		Usertype usertype = new Usertype();
		usertype.records(db, SQL);
		return usertype;
	}



	public Usertype getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usertype();
		Usertype usertype = new Usertype();
		usertype.read(db, myrequest.getParameter("id"));
		usertype.records(db, "select * from usertypes order by usertype");
		return usertype;
	}



	public Usertype getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usertype();
		Usertype usertype = new Usertype();
		usertype.read(db, myrequest.getParameter("id"));
		usertype.records(db, "select * from usertypes order by usertype");
		return usertype;
	}



	public Usertype getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usertype();
		Usertype usertype = new Usertype();
		usertype.read(db, myrequest.getParameter("id"));
		usertype.records(db, "select * from usertypes order by usertype");
		return usertype;
	}



	public Usertype getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usertype();
		Usertype usertype = new Usertype();
		usertype.read(db, myrequest.getParameter("id"));
		usertype.records(db, "select * from usertypes order by usertype");
		return usertype;
	}



	public Usertype doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usertype();
		Usertype usertype = new Usertype();
		usertype.read(db, myrequest.getParameter("id"));
		usertype.getForm(myrequest);
		Usertype usertype_exists = new Usertype();
		usertype_exists.readUsertype(db, usertype.getUsertype());
		if (! usertype_exists.getUsertype().equals(usertype.getUsertype())) {
			Cms.CMSAudit("action=create usertype=" + usertype.getUsertype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			usertype.create(db);
			return usertype;
		} else {
			return usertype_exists;
		}
	}



	public Usertype doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usertype();
		Usertype usertype = new Usertype();
		usertype.read(db, myrequest.getParameter("id"));
		String old_usertype = usertype.getUsertype();
		usertype.getForm(myrequest);
		String new_usertype = usertype.getUsertype();
		Usertype usertype_exists = new Usertype();
		usertype_exists.readUsertype(db, usertype.getUsertype());
		if ((old_usertype.equals(new_usertype)) || (! usertype_exists.getUsertype().equals(usertype.getUsertype()))) {
			Cms.CMSAudit("action=update usertype=" + usertype.getUsertype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			usertype.update(db);
			if (! old_usertype.equals(new_usertype)) {
				usertype.renameUsertype(db, old_usertype, new_usertype);
				Content mycontent = new Content(text);
				mycontent.renameUsertype(db, old_usertype, new_usertype);
				Contentgroup mycontentgroup = new Contentgroup();
				mycontentgroup.renameUsertype(db, old_usertype, new_usertype);
				Contenttype mycontenttype = new Contenttype();
				mycontenttype.renameUsertype(db, old_usertype, new_usertype);
				Filegroup myfilegroup = new Filegroup();
				myfilegroup.renameUsertype(db, old_usertype, new_usertype);
				Filetype myfiletype = new Filetype();
				myfiletype.renameUsertype(db, old_usertype, new_usertype);
				Imagegroup myimagegroup = new Imagegroup();
				myimagegroup.renameUsertype(db, old_usertype, new_usertype);
				Imagetype myimagetype = new Imagetype();
				myimagetype.renameUsertype(db, old_usertype, new_usertype);
				Linkgroup mylinkgroup = new Linkgroup();
				mylinkgroup.renameUsertype(db, old_usertype, new_usertype);
				Linktype mylinktype = new Linktype();
				mylinktype.renameUsertype(db, old_usertype, new_usertype);
				Productgroup myproductgroup = new Productgroup();
				myproductgroup.renameUsertype(db, old_usertype, new_usertype);
				Producttype myproducttype = new Producttype();
				myproducttype.renameUsertype(db, old_usertype, new_usertype);
				User myuser = new User(text);
				myuser.renameUsertype(db, old_usertype, new_usertype);
				Workflow myworkflow = new Workflow(text);
				myworkflow.renameUsertype(db, old_usertype, new_usertype);
				Databases mydatabases = new Databases(text);
				mydatabases.renameUsertype(db, old_usertype, new_usertype);
				Version myversions = new Version();
				myversions.renameUsertype(db, old_usertype, new_usertype);
				Element myelements = new Element();
				myelements.renameUsertype(db, old_usertype, new_usertype);
			}
			return usertype;
		} else {
			return usertype_exists;
		}
	}



	public Usertype doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Usertype();
		Usertype usertype = new Usertype();
		usertype.read(db, myrequest.getParameter("id"));
		String old_usertype = usertype.getUsertype();
		String new_usertype = "";
		Cms.CMSAudit("action=delete usertype=" + usertype.getUsertype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		usertype.delete(db);
		usertype.renameUsertype(db, old_usertype, new_usertype);
		Content mycontent = new Content(text);
		mycontent.renameUsertype(db, old_usertype, new_usertype);
		Contentgroup mycontentgroup = new Contentgroup();
		mycontentgroup.renameUsertype(db, old_usertype, new_usertype);
		Contenttype mycontenttype = new Contenttype();
		mycontenttype.renameUsertype(db, old_usertype, new_usertype);
		Filegroup myfilegroup = new Filegroup();
		myfilegroup.renameUsertype(db, old_usertype, new_usertype);
		Filetype myfiletype = new Filetype();
		myfiletype.renameUsertype(db, old_usertype, new_usertype);
		Imagegroup myimagegroup = new Imagegroup();
		myimagegroup.renameUsertype(db, old_usertype, new_usertype);
		Imagetype myimagetype = new Imagetype();
		myimagetype.renameUsertype(db, old_usertype, new_usertype);
		Linkgroup mylinkgroup = new Linkgroup();
		mylinkgroup.renameUsertype(db, old_usertype, new_usertype);
		Linktype mylinktype = new Linktype();
		mylinktype.renameUsertype(db, old_usertype, new_usertype);
		Productgroup myproductgroup = new Productgroup();
		myproductgroup.renameUsertype(db, old_usertype, new_usertype);
		Producttype myproducttype = new Producttype();
		myproducttype.renameUsertype(db, old_usertype, new_usertype);
		User myuser = new User(text);
		myuser.renameUsertype(db, old_usertype, new_usertype);
		Workflow myworkflow = new Workflow(text);
		myworkflow.renameUsertype(db, old_usertype, new_usertype);
		Databases mydatabases = new Databases(text);
		mydatabases.renameUsertype(db, old_usertype, new_usertype);
		Version myversions = new Version();
		myversions.renameUsertype(db, old_usertype, new_usertype);
		Element myelements = new Element();
		myelements.renameUsertype(db, old_usertype, new_usertype);
		return usertype;
	}



}
