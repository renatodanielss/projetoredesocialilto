package HardCore;

public class UCmaintainElements {
	private Text text = new Text();



	public UCmaintainElements() {
	}



	public UCmaintainElements(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Element getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Element();
		String SQL = "select * from elements order by element";
		Element element = new Element();
		element.records(db, SQL);
		return element;
	}



	public Element getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Element();
		Element element = new Element();
		element.read(db, myrequest.getParameter("id"));
		return element;
	}



	public Element getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Element();
		Element element = new Element();
		element.read(db, myrequest.getParameter("id"));
		return element;
	}



	public Element getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Element();
		Element element = new Element();
		element.read(db, myrequest.getParameter("id"));
		return element;
	}



	public Element getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Element();
		Element element = new Element();
		element.read(db, myrequest.getParameter("id"));
		return element;
	}



	public Element doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Element();
		Element element = new Element();
		element.read(db, myrequest.getParameter("id"));
		element.getForm(myrequest);
		Element element_exists = new Element();
		element_exists.readElement(db, element.getElement());
		if (! element_exists.getElement().equals(element.getElement())) {
			Cms.CMSAudit("action=create contentclass=" + element.getElement() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			element.create(db);
			return element;
		} else {
			return element_exists;
		}
	}



	public Element doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Element();
		Element element = new Element();
		element.read(db, myrequest.getParameter("id"));
		String old_contentclass = element.getElement();
		element.getForm(myrequest);
		String new_contentclass = element.getElement();
		Element element_exists = new Element();
		element_exists.readElement(db, element.getElement());
		if ((old_contentclass.equals(new_contentclass)) || (! element_exists.getElement().equals(element.getElement()))) {
			Cms.CMSAudit("action=update contentclass=" + element.getElement() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			element.update(db);
			if (! old_contentclass.equals(new_contentclass)) {
				Content content = new Content(text);
				content.renameContentclass(db, old_contentclass, new_contentclass);
				Workflow workflow = new Workflow(text);
				workflow.renameContentclass(db, old_contentclass, new_contentclass);
			}
			return element;
		} else {
			return element_exists;
		}
	}



	public Element doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Element();
		Element element = new Element();
		element.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete contentclass=" + element.getElement() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		element.delete(db);
		Content content = new Content(text);
		content.renameContentclass(db, element.getElement(), "");
		Workflow workflow = new Workflow(text);
		workflow.renameContentclass(db, element.getElement(), "");
		return element;
	}



}
