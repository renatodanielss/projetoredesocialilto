package HardCore;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.jsp.*;

public class UCmaintainDatabases {
	private Text text = new Text();



	public UCmaintainDatabases() {
	}



	public UCmaintainDatabases(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Databases getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Databases(text);
		String SQL = "select * from data order by title";
		Databases databases = new Databases(text);
		databases.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
		return databases;
	}



	public Databases getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Databases(text);
		Databases databases = new Databases(text);
		databases.read(db, myconfig, myrequest.getParameter("id"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		return databases;
	}



	public Databases getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Databases(text);
		Databases databases = new Databases(text);
		databases.read(db, myconfig, myrequest.getParameter("id"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		return databases;
	}



	public Databases getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Databases(text);
		Databases databases = new Databases(text);
		databases.read(db, myconfig, myrequest.getParameter("id"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		return databases;
	}



	public Databases getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Databases(text);
		Databases databases = new Databases(text);
		databases.read(db, myconfig, myrequest.getParameter("id"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		return databases;
	}



	public Databases doCreate(JspWriter out, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String database) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Databases(text);
		Databases databases = new Databases(text);
		databases.read(db, myconfig, myrequest.getParameter("id"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		databases.getForm(myrequest);
		databases.create(db);
		doCreateData(out, DOCUMENT_ROOT, db, database, databases);
		return databases;
	}



	public HashMap<String,String> doCreateData(JspWriter out, String DOCUMENT_ROOT, DB db, String database, Databases databases) {
		HashMap<String,String> tableinfo = new HashMap<String,String>();
		if (! databases.getId().equals("")) {
			db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/database.delete." + DB.db_type(database) + ".sql", "data" + databases.getId(), "", "");
			db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/database.create." + DB.db_type(database) + ".sql", "data" + databases.getId(), "", "");
			String[] mycolumns = databases.getContent().split("\r?\n");
			for (int i=0; i<mycolumns.length; i++) {
				String[] attribs = mycolumns[i].split("\\|");
				if (attribs.length >= 5) {
					String id = attribs[0];
					String order = attribs[1];
					String name = attribs[2];
					String index = attribs[3];
					String type = attribs[4];
					if (type.equals("text")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.text." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
						tableinfo.put("col" + id, "text");
					} else if (type.equals("html")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.text." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
						tableinfo.put("col" + id, "text");
					} else if (type.equals("number")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.number." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
						tableinfo.put("col" + id, "number");
					} else if (type.equals("select")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.text." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
						tableinfo.put("col" + id, "text");
					} else if (type.equals("selectmulti")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.text." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
						tableinfo.put("col" + id, "text");
					} else if (type.equals("radio")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.text." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
						tableinfo.put("col" + id, "text");
					} else if (type.equals("checkbox")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.text." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
						tableinfo.put("col" + id, "text");
					} else if (type.equals("datetime")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "20");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("created")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "20");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("updated")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "20");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("createdby")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("updatedby")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("content")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "10");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("page")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "10");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("image")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "10");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("file")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "10");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("link")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "10");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("element")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "10");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("contentclass")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("contentgroup")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("contenttype")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("pagegroup")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("pagetype")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("imagegroup")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("imagetype")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("filegroup")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("filetype")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("linkgroup")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("linktype")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("productgroup")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("producttype")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("imageformat")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("fileformat")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("version")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("database")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("datum")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "10");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("username")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "50");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("useremail")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("usergroup")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else if (type.equals("usertype")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
						tableinfo.put("col" + id, "varchar");
					} else {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.text." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
						tableinfo.put("col" + id, "text");
					}
					if (index.equals("index")) {
						if (type.equals("number")) {
							db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/index.number.create." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
						} else {
							db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/index.text.create." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
						}
					}
				}
			}
		}
		return tableinfo;
	}



	public Databases doUpdate(JspWriter out, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String database) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Databases(text);
		Databases databases = new Databases(text);
		databases.read(db, myconfig, myrequest.getParameter("id"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));

		HashMap<String,String> old_content = new HashMap<String,String>();
		String[] mycolumns = databases.getContent().split("\r\n");
		for (int i=0; i<mycolumns.length; i++) {
			String[] attribs = mycolumns[i].split("\\|");
			if (attribs.length >= 5) {
				String id = attribs[0];
				String order = attribs[1];
				String name = attribs[2];
				String index = attribs[3];
				String type = attribs[4];
				old_content.put(id, "" + mycolumns[i]);
			}
		}

		databases.getForm(myrequest);
		databases.update(db);
		doUpdateData(out, DOCUMENT_ROOT, db, database, databases, old_content);
		return databases;
	}



	public void doUpdateData(JspWriter out, String DOCUMENT_ROOT, DB db, String database, Databases databases, HashMap old_content) {
		HashMap<String,String> new_content = new HashMap<String,String>();
		String[] mycolumns = databases.getContent().split("\r\n");
		for (int i=0; i<mycolumns.length; i++) {
			String[] attribs = mycolumns[i].split("\\|");
			if (attribs.length >= 5) {
				String id = attribs[0];
				String order = attribs[1];
				String name = attribs[2];
				String index = attribs[3];
				String type = attribs[4];
				new_content.put(id, "" + mycolumns[i]);
				if (old_content.get("" + id) == null) {
					if (type.equals("text")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.text." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
					} else if (type.equals("html")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.text." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
					} else if (type.equals("number")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.number." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
					} else if (type.equals("select")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.text." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
					} else if (type.equals("selectmulti")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.text." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
					} else if (type.equals("radio")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.text." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
					} else if (type.equals("checkbox")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.text." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
					} else if (type.equals("datetime")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "20");
					} else if (type.equals("created")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "20");
					} else if (type.equals("updated")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "20");
					} else if (type.equals("createdby")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("updatedby")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("content")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "10");
					} else if (type.equals("page")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "10");
					} else if (type.equals("image")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "10");
					} else if (type.equals("file")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "10");
					} else if (type.equals("link")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "10");
					} else if (type.equals("element")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "10");
					} else if (type.equals("contentclass")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("contentgroup")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("contenttype")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("pagegroup")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("pagetype")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("imagegroup")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("imagetype")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("filegroup")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("filetype")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("linkgroup")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("linktype")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("productgroup")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("producttype")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("imageformat")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("fileformat")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("version")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("database")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("datum")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "10");
					} else if (type.equals("username")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "50");
					} else if (type.equals("useremail")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("usergroup")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else if (type.equals("usertype")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.varchar." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "255");
					} else {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.create.text." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
					}
					if (index.equals("index")) {
						if (type.equals("number")) {
							db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/index.number.create." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
						} else {
							db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/index.text.create." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
						}
					}
				} else {
					String[] old_attribs = ((String)old_content.get(id)).split("\\|");
					if (old_attribs.length >= 5) {
						String old_index = old_attribs[3];
						if ((index.equals("index")) && (! old_index.equals("index"))) {
							if (type.equals("number")) {
								db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/index.number.create." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
							} else {
								db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/index.text.create." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
							}
						} else if ((! index.equals("index")) && (old_index.equals("index"))) {
							db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/index.delete." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
						}
					}
				}
			}
		}

		Iterator columns = old_content.keySet().iterator();
		while (columns.hasNext()) {
			String column = "" + columns.next();
			if (old_content.get(column) != null) {
				String[] attribs = ((String)old_content.get(column)).split("\\|");
				if (attribs.length >= 5) {
					String id = attribs[0];
					String order = attribs[1];
					String name = attribs[2];
					String index = attribs[3];
					String type = attribs[4];
					if ((new_content.get(id) == null) && (! databases.getId().equals(""))) {
						if (index.equals("index")) {
							db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/index.delete." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
						}
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/column.delete." + DB.db_type(database) + ".sql", "data" + databases.getId(), "col" + id, "");
					}
				}
			}
		}
	}



	public Databases doDelete(JspWriter out, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String database) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Databases(text);
		Databases databases = new Databases(text);
		databases.read(db, myconfig, myrequest.getParameter("id"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		databases.delete(db);

		if (! databases.getId().equals("")) {
			db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/database.delete." + DB.db_type(database) + ".sql", "data" + databases.getId(), "", "");
		}

		return databases;
	}



}
