package HardCore;

public class UCmaintainProductgroups {
	private Text text = new Text();



	public UCmaintainProductgroups() {
	}



	public UCmaintainProductgroups(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Productgroup getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Productgroup();
		String SQL = "select * from productgroups order by productgroup";
		Productgroup productgroup = new Productgroup();
		productgroup.records(db, SQL);
		return productgroup;
	}



	public Productgroup getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Productgroup();
		Productgroup productgroup = new Productgroup();
		productgroup.read(db, myrequest.getParameter("id"));
		return productgroup;
	}



	public Productgroup getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Productgroup();
		Productgroup productgroup = new Productgroup();
		productgroup.read(db, myrequest.getParameter("id"));
		return productgroup;
	}



	public Productgroup getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Productgroup();
		Productgroup productgroup = new Productgroup();
		productgroup.read(db, myrequest.getParameter("id"));
		return productgroup;
	}



	public Productgroup getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Productgroup();
		Productgroup productgroup = new Productgroup();
		productgroup.read(db, myrequest.getParameter("id"));
		return productgroup;
	}



	public Productgroup doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Productgroup();
		Productgroup productgroup = new Productgroup();
		productgroup.read(db, myrequest.getParameter("id"));
		productgroup.getForm(myrequest);
		Productgroup productgroup_exists = new Productgroup();
		productgroup_exists.readProductgroup(db, productgroup.getProductgroup());
		if (! productgroup_exists.getProductgroup().equals(productgroup.getProductgroup())) {
			Cms.CMSAudit("action=create productgroup=" + productgroup.getProductgroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			productgroup.create(db);
			return productgroup;
		} else {
			return productgroup_exists;
		}
	}



	public Productgroup doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Productgroup();
		Productgroup productgroup = new Productgroup();
		productgroup.read(db, myrequest.getParameter("id"));
		String old_productgroup = productgroup.getProductgroup();
		productgroup.getForm(myrequest);
		String new_productgroup = productgroup.getProductgroup();
		Productgroup productgroup_exists = new Productgroup();
		productgroup_exists.readProductgroup(db, productgroup.getProductgroup());
		if ((old_productgroup.equals(new_productgroup)) || (! productgroup_exists.getProductgroup().equals(productgroup.getProductgroup()))) {
			Cms.CMSAudit("action=update productgroup=" + productgroup.getProductgroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			productgroup.update(db);
			if (! old_productgroup.equals(new_productgroup)) {
				Content content = new Content(text);
				content.renameProductgroup(db, old_productgroup, new_productgroup);
				Workflow workflow = new Workflow(text);
				workflow.renameProductgroup(db, old_productgroup, new_productgroup);
			}
			return productgroup;
		} else {
			return productgroup_exists;
		}
	}



	public Productgroup doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Productgroup();
		Productgroup productgroup = new Productgroup();
		productgroup.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete productgroup=" + productgroup.getProductgroup() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		productgroup.delete(db);
		Content content = new Content(text);
		content.renameProductgroup(db, productgroup.getProductgroup(), "");
		Workflow workflow = new Workflow(text);
		workflow.renameProductgroup(db, productgroup.getProductgroup(), "");
		return productgroup;
	}



}
