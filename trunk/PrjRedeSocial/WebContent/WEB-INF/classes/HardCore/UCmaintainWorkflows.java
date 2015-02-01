package HardCore;

public class UCmaintainWorkflows {
	private Text text = new Text();



	public UCmaintainWorkflows() {
	}



	public UCmaintainWorkflows(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Workflow getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Workflow(text);
		String SQL = "select distinct title,action,fromstate,tostate,id from workflow";
		String SQLwhere = "";
		if (! myrequest.getParameter("workflow").equals("")) {
			SQLwhere = " where title='" + Common.SQL_clean(myrequest.getParameter("workflow")) + "'";
		}
		String SQLorder = " order by title,action,fromstate,tostate,id";
		SQL += SQLwhere + SQLorder;
		Workflow workflow = new Workflow(text);
		workflow.records(db, SQL);
		return workflow;
	}



	public Workflow getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Workflow(text);
		Workflow workflow = new Workflow(text);
		workflow.read(db, myrequest.getParameter("id"));
		return workflow;
	}



	public Workflow getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Workflow(text);
		Workflow workflow = new Workflow(text);
		workflow.read(db, myrequest.getParameter("id"));
		return workflow;
	}



	public Workflow getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Workflow(text);
		Workflow workflow = new Workflow(text);
		workflow.read(db, myrequest.getParameter("id"));
		return workflow;
	}



	public Workflow getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Workflow(text);
		Workflow workflow = new Workflow(text);
		workflow.read(db, myrequest.getParameter("id"));
		return workflow;
	}



	public Workflow doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Workflow(text);
		Workflow workflow = new Workflow(text);
		workflow.read(db, myrequest.getParameter("id"));
		workflow.getForm(myrequest);
		Cms.CMSAudit("action=create workflow=" + workflow.getTitle() + " - " + workflow.getAction() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		workflow.create(db);
		return workflow;
	}



	public Workflow doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Workflow(text);
		Workflow workflow = new Workflow(text);
		workflow.read(db, myrequest.getParameter("id"));
		workflow.getForm(myrequest);
		Cms.CMSAudit("action=update workflow=" + workflow.getTitle() + " - " + workflow.getAction() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		workflow.update(db);
		return workflow;
	}



	public Workflow doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Workflow(text);
		Workflow workflow = new Workflow(text);
		workflow.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete workflow=" + workflow.getTitle() + " - " + workflow.getAction() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		workflow.delete(db);
		return workflow;
	}



}
