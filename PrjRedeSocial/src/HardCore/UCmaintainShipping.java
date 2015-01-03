package HardCore;

public class UCmaintainShipping {
	private Shipping create_records = new Shipping();
	private Text text = new Text();



	public UCmaintainShipping() {
	}



	public UCmaintainShipping(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Shipping getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Shipping();
		String SQL = "select * from shipping order by title";
		Shipping shipping = new Shipping();
		shipping.records(db, SQL);
		create_records = new Shipping();
		create_records.records(db, SQL);
		return shipping;
	}



	public Shipping getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Shipping();
		Shipping shipping = new Shipping();
		shipping.read(db, myrequest.getParameter("id"));
		return shipping;
	}



	public Shipping getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Shipping();
		Shipping shipping = new Shipping();
		shipping.read(db, myrequest.getParameter("id"));
		return shipping;
	}



	public Shipping getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Shipping();
		Shipping shipping = new Shipping();
		shipping.read(db, myrequest.getParameter("id"));
		return shipping;
	}



	public Shipping getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Shipping();
		Shipping shipping = new Shipping();
		shipping.read(db, myrequest.getParameter("id"));
		return shipping;
	}



	public Shipping doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Shipping();
		Shipping shipping = new Shipping();
		shipping.read(db, myrequest.getParameter("id"));
		shipping.getForm(myrequest);
		Cms.CMSAudit("action=create shipping=" + shipping.getTitle() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		shipping.create(db);
		return shipping;
	}



	public Shipping doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Shipping();
		Shipping shipping = new Shipping();
		shipping.read(db, myrequest.getParameter("id"));
		shipping.getForm(myrequest);
		Cms.CMSAudit("action=update shipping=" + shipping.getTitle() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		shipping.update(db);
		return shipping;
	}



	public Shipping doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Shipping();
		Shipping shipping = new Shipping();
		shipping.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete shipping=" + shipping.getTitle() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		shipping.delete(db);
		return shipping;
	}



	public Shipping getCreateRecords() {
		return create_records;
	}



}
