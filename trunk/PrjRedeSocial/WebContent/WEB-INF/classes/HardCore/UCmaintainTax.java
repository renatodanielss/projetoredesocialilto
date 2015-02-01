package HardCore;

public class UCmaintainTax {
	private Tax create_records = new Tax();
	private Text text = new Text();



	public UCmaintainTax() {
	}



	public UCmaintainTax(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Tax getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Tax();
		String SQL = "select * from tax order by title";
		Tax tax = new Tax();
		tax.records(db, SQL);
		create_records = new Tax();
		create_records.records(db, SQL);
		return tax;
	}



	public Tax getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Tax();
		Tax tax = new Tax();
		tax.read(db, myrequest.getParameter("id"));
		return tax;
	}



	public Tax getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Tax();
		Tax tax = new Tax();
		tax.read(db, myrequest.getParameter("id"));
		return tax;
	}



	public Tax getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Tax();
		Tax tax = new Tax();
		tax.read(db, myrequest.getParameter("id"));
		return tax;
	}



	public Tax getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Tax();
		Tax tax = new Tax();
		tax.read(db, myrequest.getParameter("id"));
		return tax;
	}



	public Tax doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Tax();
		Tax tax = new Tax();
		tax.read(db, myrequest.getParameter("id"));
		tax.getForm(myrequest);
		Cms.CMSAudit("action=create tax=" + tax.getTitle() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		tax.create(db);
		return tax;
	}



	public Tax doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Tax();
		Tax tax = new Tax();
		tax.read(db, myrequest.getParameter("id"));
		tax.getForm(myrequest);
		Cms.CMSAudit("action=update tax=" + tax.getTitle() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		tax.update(db);
		return tax;
	}



	public Tax doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Tax();
		Tax tax = new Tax();
		tax.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete tax=" + tax.getTitle() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		tax.delete(db);
		return tax;
	}



	public Tax getCreateRecords() {
		return create_records;
	}



}
