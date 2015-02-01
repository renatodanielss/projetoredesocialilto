package HardCore;

import java.text.*;

public class UCmaintainGuestbook {
	private Text text = new Text();



	public UCmaintainGuestbook() {
	}



	public UCmaintainGuestbook(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Guestbook getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Guestbook();
		accesspermission = requireGuestbookAdministrator(mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Guestbook();
		String SQL = "select * from guestbook order by created";
		Guestbook guestbook = new Guestbook();
		guestbook.records(db, SQL);
		return guestbook;
	}



	public Guestbook getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Guestbook();
		accesspermission = requireGuestbookAdministrator(mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Guestbook();
		Guestbook guestbook = new Guestbook();
		guestbook.read(db, myrequest.getParameter("id"));
		return guestbook;
	}



	public Guestbook getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Guestbook();
		accesspermission = requireGuestbookAdministrator(mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Guestbook();
		Guestbook guestbook = new Guestbook();
		guestbook.read(db, myrequest.getParameter("id"));
		return guestbook;
	}



	public Guestbook getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Guestbook();
		accesspermission = requireGuestbookAdministrator(mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Guestbook();
		Guestbook guestbook = new Guestbook();
		guestbook.read(db, myrequest.getParameter("id"));
		return guestbook;
	}



	public Guestbook getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Guestbook();
		accesspermission = requireGuestbookAdministrator(mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Guestbook();
		Guestbook guestbook = new Guestbook();
		guestbook.read(db, myrequest.getParameter("id"));
		return guestbook;
	}



	public Guestbook doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Guestbook();
		accesspermission = requireGuestbookAdministrator(mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Guestbook();

		Guestbook guestbook = new Guestbook();
		guestbook.read(db, myrequest.getParameter("id"));
		guestbook.getForm(myrequest);

		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String username = mysession.get("username");
		guestbook.setCreated(timestamp);
		guestbook.setCreatedBy(username);
		guestbook.setUpdated(timestamp);
		guestbook.setUpdatedBy(username);
		if (! myrequest.getParameter("publish").equals("")) {
			guestbook.setPublished(timestamp);
			guestbook.setPublishedBy(username);
		} else {
			guestbook.setPublished("");
			guestbook.setPublishedBy("");
		}

		guestbook.create(db);
		return guestbook;
	}



	public Guestbook doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Guestbook();
		accesspermission = requireGuestbookAdministrator(mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Guestbook();
		Guestbook guestbook = new Guestbook();
		guestbook.read(db, myrequest.getParameter("id"));
		guestbook.getForm(myrequest);

		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String username = mysession.get("username");
		guestbook.setUpdated(timestamp);
		guestbook.setUpdatedBy(username);
		if (! myrequest.getParameter("publish").equals("")) {
			guestbook.setPublished(timestamp);
			guestbook.setPublishedBy(username);
		} else {
			guestbook.setPublished("");
			guestbook.setPublishedBy("");
		}

		guestbook.update(db);
		return guestbook;
	}



	public Guestbook doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Guestbook();
		accesspermission = requireGuestbookAdministrator(mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Guestbook();
		Guestbook guestbook = new Guestbook();
		guestbook.read(db, myrequest.getParameter("id"));
		guestbook.delete(db);
		return guestbook;
	}



	private boolean requireGuestbookAdministrator(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = true;
		if (! myconfig.get(db, "guestbook_admin_users_type").equals("")) {
			accesspermission = accesspermission && RequireUser.AdministratorUsertype(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myconfig.get(db, "guestbook_admin_users_type"), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		}
		if (! myconfig.get(db, "guestbook_admin_users_group").equals("")) {
			accesspermission = accesspermission && RequireUser.AdministratorUsergroup(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myconfig.get(db, "guestbook_admin_users_group"), mysession.get("usergroup"), mysession.get("usergroups"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		}
		return accesspermission;
	}



}
