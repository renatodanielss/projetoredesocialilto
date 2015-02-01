package HardCore;

import java.util.HashMap;
import javax.servlet.ServletContext;

public class UCretrievePassword {
	private Text text = new Text();



	public UCretrievePassword() {
	}



	public UCretrievePassword(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public String emailSuperadminPassword(ServletContext servletcontext, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		String error = "";
		String from = "nobody@" + myrequest.getServerName();
		String to = myconfig.get(db, "superadmin_email");
		String subject = "" + text.display("product") + " superadmin details";
		String body = "Your " + text.display("product") + " superadmin details are:" + "\r\n" + "\r\n";
		body = "" + body + "Username: " + myconfig.get(db, "superadmin") + "\r\n";
		body = "" + body + "Password: " + myconfig.get(db, "superadmin_password") + "\r\n";
		Email.send_email(text, new HashMap<String,String>(), subject, body, "", from, to, "", "", "", "", myrequest.getServerName(), servletcontext, mysession, myrequest, myresponse, myconfig, db);
		return error;
	}



}
