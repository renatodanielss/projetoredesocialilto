package HardCore;

import java.net.URLEncoder;
import javax.servlet.ServletContext;

public class UCaccessAdministration {



	static public boolean doIndex(String wizard, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		return doIndex(new Text(), wizard, mysession, myrequest, myresponse, myconfig, db);
	}
	static public boolean doIndex(Text text, String wizard, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		if ((! wizard.equals("")) && ((myrequest.getQueryString().equals("")) || (myrequest.getQueryString().equals(" ")) || (myrequest.getQueryString().equals("%20")))) {
			myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/index.config.jsp?menu=configsystem");
		}
		if (db == null) return false;
		boolean administrator = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		mysession.set("menu", "");
		return administrator;
	}



	public String getLoginURL(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Text mytext) {
		mysession.set("menu", "");
		if (! myrequest.getParameter("url").equals("")) {
			return html.encodeHtmlEntities(myrequest.getParameter("url"));
		} else {
			return myconfig.get(db, "URLrootpath") + mytext.display("adminpath") + "/";
		}
	}



	public void doLogin(ServletContext servletcontext, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String database) throws Exception {
		doLogin(new Text(), servletcontext, mysession, myrequest, myresponse, myconfig, db, database);
	}
	public void doLogin(Text text, ServletContext servletcontext, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String database) throws Exception {
		Login.login(text, null, "/" + text.display("adminpath") + "/login.jsp", "/" + text.display("adminpath") + "/", servletcontext, mysession, myrequest, myresponse, myconfig, db, myconfig.get(db, "require_ssl_admin"), database);
	}



	public void doLogout(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (mysession != null) mysession.set("mode", "");
		Login.logout(mysession, myrequest, myresponse, db, myconfig.get(db, "require_ssl_logout_admin"));
	}



}
