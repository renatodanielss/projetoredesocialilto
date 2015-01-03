package HardCore;

public class UCmaintainProducttypes {
	private Text text = new Text();



	public UCmaintainProducttypes() {
	}



	public UCmaintainProducttypes(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Producttype getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Producttype();
		String SQL = "select * from producttypes order by producttype";
		Producttype producttype = new Producttype();
		producttype.records(db, SQL);
		return producttype;
	}



	public Producttype getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Producttype();
		Producttype producttype = new Producttype();
		producttype.read(db, myrequest.getParameter("id"));
		return producttype;
	}



	public Producttype getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Producttype();
		Producttype producttype = new Producttype();
		producttype.read(db, myrequest.getParameter("id"));
		return producttype;
	}



	public Producttype getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Producttype();
		Producttype producttype = new Producttype();
		producttype.read(db, myrequest.getParameter("id"));
		return producttype;
	}



	public Producttype getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Producttype();
		Producttype producttype = new Producttype();
		producttype.read(db, myrequest.getParameter("id"));
		return producttype;
	}



	public Producttype doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Producttype();
		Producttype producttype = new Producttype();
		producttype.read(db, myrequest.getParameter("id"));
		producttype.getForm(myrequest);
		Producttype producttype_exists = new Producttype();
		producttype_exists.readProducttype(db, producttype.getProducttype());
		if (! producttype_exists.getProducttype().equals(producttype.getProducttype())) {
			Cms.CMSAudit("action=create producttype=" + producttype.getProducttype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			producttype.create(db);
			return producttype;
		} else {
			return producttype_exists;
		}
	}



	public Producttype doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Producttype();
		Producttype producttype = new Producttype();
		producttype.read(db, myrequest.getParameter("id"));
		String old_producttype = producttype.getProducttype();
		producttype.getForm(myrequest);
		String new_producttype = producttype.getProducttype();
		Producttype producttype_exists = new Producttype();
		producttype_exists.readProducttype(db, producttype.getProducttype());
		if ((old_producttype.equals(new_producttype)) || (! producttype_exists.getProducttype().equals(producttype.getProducttype()))) {
			Cms.CMSAudit("action=update producttype=" + producttype.getProducttype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			producttype.update(db);
			if (! old_producttype.equals(new_producttype)) {
				Content content = new Content(text);
				content.renameProducttype(db, old_producttype, new_producttype);
				Workflow workflow = new Workflow(text);
				workflow.renameProducttype(db, old_producttype, new_producttype);
			}
			return producttype;
		} else {
			return producttype_exists;
		}
	}



	public Producttype doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Producttype();
		Producttype producttype = new Producttype();
		producttype.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete producttype=" + producttype.getProducttype() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		producttype.delete(db);
		Content content = new Content(text);
		content.renameProducttype(db, producttype.getProducttype(), "");
		Workflow workflow = new Workflow(text);
		workflow.renameProducttype(db, producttype.getProducttype(), "");
		return producttype;
	}



}
