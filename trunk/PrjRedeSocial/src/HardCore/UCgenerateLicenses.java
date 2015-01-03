package HardCore;

public class UCgenerateLicenses {
	private Text text = new Text();



	public UCgenerateLicenses() {
	}



	public UCgenerateLicenses(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public String getLicense(String owner, String product, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
		if (License.valid(db, myconfig, "hosting")) {
			return License.generate(owner, product);
		} else {
			return License.trial(owner, product);
		}
	}



}
