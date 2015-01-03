package HardCore;

import java.io.*;
import java.io.File;
import javax.servlet.*;
import javax.servlet.jsp.*;

public class UCmaintainCurrencies {
	private Text text = new Text();



	public UCmaintainCurrencies() {
	}



	public UCmaintainCurrencies(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Currency getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Currency();
		String SQL = "select * from currencies order by title";
		Currency currencies = new Currency();
		currencies.records(db, SQL);
		return currencies;
	}



	public Currency getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Currency();
		Currency currencies = new Currency();
		currencies.read(db, myrequest.getParameter("id"));
		return currencies;
	}



	public Currency getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Currency();
		Currency currencies = new Currency();
		currencies.read(db, myrequest.getParameter("id"));
		return currencies;
	}



	public Currency getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Currency();
		Currency currencies = new Currency();
		currencies.read(db, myrequest.getParameter("id"));
		return currencies;
	}



	public Currency getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Currency();
		Currency currencies = new Currency();
		currencies.read(db, myrequest.getParameter("id"));
		return currencies;
	}



	public Currency doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Currency();
		Currency currencies = new Currency();
		currencies.read(db, myrequest.getParameter("id"));
		currencies.getForm(myrequest);
		Cms.CMSAudit("action=create currency=" + currencies.getTitle() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		currencies.create(db);
		return currencies;
	}



	public Currency doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Currency();
		Currency currencies = new Currency();
		currencies.read(db, myrequest.getParameter("id"));
		currencies.getForm(myrequest);
		Cms.CMSAudit("action=update currency=" + currencies.getTitle() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		currencies.update(db);
		return currencies;
	}



	public Currency doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Currency();
		Currency currencies = new Currency();
		currencies.read(db, myrequest.getParameter("id"));
		Cms.CMSAudit("action=delete currency=" + currencies.getTitle() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
		currencies.delete(db);
		return currencies;
	}



	public Currency getExport(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Currency();
		Currency currencies = new Currency();
		return currencies;
	}



	public Currency getImport(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Currency();
		Currency currencies = new Currency();
		return currencies;
	}



	public Currency doExport(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Currency();
		Currency currencies = new Currency();
		String SQL = "select * from currencies order by title";
		currencies.records(db, SQL);
		myresponse.setHeader("Content-Disposition", "filename=currency.csv");
		myresponse.setContentType("x-application/csv");
		return currencies;
	}



	public String doImport(JspWriter out, ServletContext servletcontext, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
		String output = "";
		String save_content_editor = myconfig.get(db, "content_editor");
		Fileupload filepost = new Fileupload(myrequest, DOCUMENT_ROOT + myconfig.get(db, "URLrootpath"), myconfig.get(db, "URLuploadpath"));
		myconfig.setTemp("content_editor", save_content_editor);
		if (! filepost.getParameter("file.server_filename").equals("")) {
			String filename = Common.getRealPath(servletcontext, myconfig.get(db, "URLrootpath") + filepost.getParameter("file.server_filename"));
			File fh = new File(filename);
			if (fh.exists()) {
				BufferedReader input = null;
				try {
					input = new BufferedReader(new FileReader(filename));
					String my_line = "" + input.readLine();
					if (my_line.equals("id,title,symbol,rate")) {
						if (out != null) out.print(text.display("currency.import.importing"));
						output += text.display("currency.import.importing");
						while ((my_line = input.readLine()) != null) {
							if (out != null) out.print(" . ");
							if (out != null) out.flush();
							output += " . ";
							if (! my_line.equals("")) {
								String[] my_values = (my_line+",,,,x").split(",");
								String id = my_values[0];
								String title = my_values[1];
								String symbol = my_values[2];
								String rate = my_values[3];
								Currency currencies;
								currencies = new Currency();
								currencies.read(db, id);
								if (updated_currency(currencies, id, title, symbol, rate)) {
									currencies.update(db);
								}
							}
						}
						if (out != null) out.print(text.display("error.currency.import.done"));
						output += text.display("error.currency.import.done");
					} else {
						if (out != null) out.print(text.display("error.currency.import.format"));
						output += text.display("error.currency.import.format");
					}
					input.close();
				} catch (FileNotFoundException e) {
					if (input != null) try { input.close(); } catch (IOException ee) { ; }
				} catch (IOException e) {
					if (input != null) try { input.close(); } catch (IOException ee) { ; }
				}
				fh.delete();
			}
		}
		return output;
	}



	private boolean updated_currency(Currency currencies, String id, String title, String symbol, String rate) {
		boolean updated = false;
		if (currencies.getId().equals(id)) {
			if (! currencies.getTitle().equals(title)) {
				currencies.setTitle(title);
				updated = true;
			}
			if (! currencies.getSymbol().equals(symbol)) {
				currencies.setSymbol(symbol);
				updated = true;
			}
			if (! currencies.getRate().equals(rate)) {
				currencies.setRate(rate);
				updated = true;
			}
		}
		return updated;
	}



}
