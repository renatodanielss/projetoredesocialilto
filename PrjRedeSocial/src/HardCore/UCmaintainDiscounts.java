package HardCore;

public class UCmaintainDiscounts {
	private Discount create_records = new Discount();
	private Text text = new Text();



	public UCmaintainDiscounts() {
	}



	public UCmaintainDiscounts(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Discount getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Discount();
		String SQL = "select * from discounts order by title";
		Discount discount = new Discount();
		discount.records(db, SQL);
		create_records = new Discount();
		create_records.records(db, SQL);
		return discount;
	}



	public Discount getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Discount();
		Discount discount = new Discount();
		discount.read(db, myrequest.getParameter("id"));
		return discount;
	}



	public Discount getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Discount();
		Discount discount = new Discount();
		discount.read(db, myrequest.getParameter("id"));
		return discount;
	}



	public Discount getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Discount();
		Discount discount = new Discount();
		discount.read(db, myrequest.getParameter("id"));
		return discount;
	}



	public Discount getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Discount();
		Discount discount = new Discount();
		discount.read(db, myrequest.getParameter("id"));
		return discount;
	}



	public Discount doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Discount();
		Discount discount = new Discount();
		discount.read(db, myrequest.getParameter("id"));
		discount.getForm(myrequest);
		Cms.CMSAudit("action=create discount=" + discount.getTitle() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		discount.create(db);
		return discount;
	}



	public Discount doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Discount();
		Discount discount = new Discount();
		discount.read(db, myrequest.getParameter("id"));
		discount.getForm(myrequest);
		Cms.CMSAudit("action=update discount=" + discount.getTitle() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		discount.update(db);
		return discount;
	}



	public Discount doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Discount();
		Discount discount = new Discount();
		discount.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete discount=" + discount.getTitle() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		discount.delete(db);
		return discount;
	}



	public Discount getCreateRecords() {
		return create_records;
	}



}
