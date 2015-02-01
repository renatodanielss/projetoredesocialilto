package HardCore;

public class UCmaintainSegments {
	private String error = "";
	private Text text = new Text();



	public UCmaintainSegments() {
	}



	public UCmaintainSegments(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Segment getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Segment();
		accesspermission = RequireUser.ExperienceAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Segment();
		String SQL = "select * from segments order by segmentid,segment,id";
		Segment segment = new Segment();
		segment.records(db, SQL);
		return segment;
	}



	public Segment getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Segment();
		accesspermission = RequireUser.ExperienceAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Segment();
		Segment segment = new Segment();
		segment.read(db, myrequest.getParameter("id"));
		return segment;
	}



	public Segment getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Segment();
		accesspermission = RequireUser.ExperienceAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Segment();
		Segment segment = new Segment();
		segment.read(db, myrequest.getParameter("id"));
		return segment;
	}



	public Segment getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Segment();
		accesspermission = RequireUser.ExperienceAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Segment();
		Segment segment = new Segment();
		segment.read(db, myrequest.getParameter("id"));
		return segment;
	}



	public Segment getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Segment();
		accesspermission = RequireUser.ExperienceAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Segment();
		Segment segment = new Segment();
		segment.read(db, myrequest.getParameter("id"));
		return segment;
	}



	public Segment doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Segment();
		accesspermission = RequireUser.ExperienceAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Segment();
		Segment segment = new Segment();
		segment.read(db, myrequest.getParameter("id"));
		segment.getForm(myrequest);
		Cms.CMSAudit("action=create segment=" + segment.getSegmentId() + ":" + segment.getSegment() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		segment.create(db);
		return segment;
	}



	public Segment doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Segment();
		accesspermission = RequireUser.ExperienceAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Segment();
		Segment segment = new Segment();
		segment.read(db, myrequest.getParameter("id"));
		segment.getForm(myrequest);
		Cms.CMSAudit("action=update segment=" + segment.getSegmentId() + ":" + segment.getSegment() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		segment.update(db);
		return segment;
	}



	public Segment doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Segment();
		accesspermission = RequireUser.ExperienceAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return new Segment();
		Segment segment = new Segment();
		segment.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete segment=" + segment.getSegmentId() + ":" + segment.getSegment() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		segment.delete(db);
		return segment;
	}



	public String getError() {
		return error;
	}



}
