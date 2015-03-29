package HardCore;

import java.io.*;
import java.text.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.*;
import java.util.TimeZone;

import javax.servlet.*;

import com.iliketo.dao.IliketoDAO;
import com.iliketo.util.Str;

public class UCmaintainDataILiketo {
	private String error = "";
	public Databases database;
	public String titleid = "";
	public String titlename = "";
	public String titlecolumn = "";
	public Fileupload filepost = null;
	private int record_count = 0;
	private Text text = new Text();



	public UCmaintainDataILiketo() {
	}



	public UCmaintainDataILiketo(Text mytext) {
		if (mytext != null) text = mytext;
		database = new Databases(text);
	}



	public Data getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Data();
		if (myrequest.getParameter("pagesize").equals(" ")) {
			mysession.set("admin_pagesize", "");
		} else if (! myrequest.getParameter("pagesize").equals("")) {
			mysession.set("admin_pagesize", html.encodeHtmlEntities(myrequest.getParameter("pagesize")));
		}

		database = new Databases(text);
		database.read(db, myconfig, myrequest.getParameter("database"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));

		database.getTitleColumn();
		titleid = database.getTitleColumnId();
		titlename = database.getTitleColumnName();
		titlecolumn = database.getTitleColumnColumn();

		String SQLselect = "";
		HashMap<String,String> indexnames = new HashMap<String,String>();
		HashMap<String,String> indexcolumns = new HashMap<String,String>();
		String[] mycolumns = database.getAdminIndex().split(" ");
		for (int i=0; i<mycolumns.length; i++) {
			String mycolumn = mycolumns[i];
			String mycolumnname = "";
			if (mycolumn.matches("^(.+?):(.+?):(.+)$")) {
				mycolumnname = mycolumn.replaceAll("^(.+?):(.+?):(.+)$", "$1");
			} else if (mycolumn.matches("^(.+):(.+)$")) {
				mycolumnname = mycolumn.replaceAll("^(.+):(.+)$", "$1");
			} else {
				mycolumnname = mycolumn;
			}
			if (database.namedcolumns.get(mycolumnname) != null) {
				if (! SQLselect.equals("")) SQLselect += ",";
				SQLselect += "col" + database.namedcolumns.get(mycolumnname).get("id");
				indexnames.put(mycolumnname, "col" + database.namedcolumns.get(mycolumnname).get("id"));
				indexcolumns.put("col" + database.namedcolumns.get(mycolumnname).get("id"), mycolumnname);
			}
		}
		if (SQLselect.equals("")) {
			SQLselect = "id," + titlecolumn;
		} else {
			if (indexcolumns.get(titlecolumn) == null) {
				SQLselect = titlecolumn + "," + SQLselect;
			}
			if (indexnames.get("id") == null) {
				SQLselect = "id," + SQLselect;
			}
		}

		String SQLorderDir = "";
		if (myrequest.getParameter("sort_dir").equals("DESC")) {
			SQLorderDir = " desc";
		}
		String SQLorder = titlecolumn + SQLorderDir + ", id" + SQLorderDir;
		if (db.db_type(db.getDatabase()).equals("mssql")) {
			SQLorder = "substring(" + titlecolumn + ",1,250)" + SQLorderDir + ", id" + SQLorderDir;
		} else if (db.db_type(db.getDatabase()).equals("oracle")) {
			SQLorder = "to_char(" + titlecolumn + ")" + SQLorderDir + ", id" + SQLorderDir;
		} else if (db.db_type(db.getDatabase()).equals("db2")) {
			SQLorder = "varchar(" + titlecolumn + ",250)" + SQLorderDir + ", id" + SQLorderDir;
		} else {
			SQLorder = titlecolumn + SQLorderDir + ", id" + SQLorderDir;
		}
		if (myrequest.getParameter("sort_col").equals("column_title")) {
			// default;
		} else if (myrequest.getParameter("sort_col").equals("column_id")) {
			if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQLorder = "id" + SQLorderDir + ", substring(" + titlecolumn + ",1,250)" + SQLorderDir;
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQLorder = "id" + SQLorderDir + ", to_char(" + titlecolumn + ")" + SQLorderDir;
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQLorder = "id" + SQLorderDir + ", varchar(" + titlecolumn + ",250)" + SQLorderDir;
			} else {
				SQLorder = "id" + SQLorderDir + ", " + titlecolumn + SQLorderDir;
			}
		}

		Data data = new Data();
		if ((! database.getId().equals("")) && (database.getUser())) {
			String SQL = "select count(*) as count from data" + Common.SQL_clean(database.getId());
			record_count = db.query_value(SQL).intValue();

			String offset = html.encodeHtmlEntities(myrequest.getParameter("offset"));
			String limit = html.encodeHtmlEntities(myrequest.getParameter("page_size"));
			if ((! offset.equals("")) && (! limit.equals(""))) {
				SQL = Common.SQLlimit(db.db_type(db.getDatabase()), "id", SQLselect, "data" + Common.SQL_clean(database.getId()), "", SQLorder, (! SQLorderDir.equals("")), Integer.parseInt("0" + offset), Integer.parseInt("0" + limit));
				data.records(db, SQL);
			} else {
				SQL = "select " + SQLselect + " from data" + database.getId() + " order by " + SQLorder;
				data.records(db, SQL);
			}
		} else if (! database.getId().equals("")) {
			data.records(db, "select " + SQLselect + " from data" + database.getId() + " where 1=0");
		}
		return data;
	}



	public Data doSearch(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Data();
		mysession.set("admin_contentclass", "");
		mysession.set("admin_contentbundle", "");
		mysession.set("admin_contentgroup", "");
		mysession.set("admin_contenttype", "");
		mysession.set("admin_version", "");
		mysession.set("admin_device", "");
		mysession.set("admin_usersegment", "");
		mysession.set("admin_usertest", "");
		if ((myrequest.getRequestURI().indexOf("search.jsp") > 0) && ((myrequest.parameterExists("search_section")) || (myrequest.parameterExists("search_bundle")) || (myrequest.parameterExists("search_group")) || (myrequest.parameterExists("search_type")) || (myrequest.parameterExists("search_version")) || (myrequest.parameterExists("search_device")) || (myrequest.parameterExists("search_usersegment")) || (myrequest.parameterExists("search_usertest")) || (myrequest.parameterExists("search_package")) || (myrequest.parameterExists("search_status")) || (myrequest.parameterExists("search_filename")) || (myrequest.parameterExists("search_attribute")) || (myrequest.parameterExists("search_id")))) {
			mysession.set("search_section", "");
			mysession.set("search_bundle", "");
			mysession.set("search_group", "");
			mysession.set("search_type", "");
			mysession.set("search_version", "");
			mysession.set("search_device", "");
			mysession.set("search_usersegment", "");
			mysession.set("search_usertest", "");
			mysession.set("search_package", "");
			mysession.set("search_status", "");
			mysession.set("search_filename", "");
			mysession.set("search_attribute", "");
			mysession.set("search_id", "");
		}
		if (myrequest.getRequestURI().indexOf("searchadvanced.jsp") > 0) {
			mysession.set("search_attribute", "");
		}
		if (! myrequest.getParameter("search").equals("")) {
			mysession.set("search", myrequest.getParameter("search"));
		} else if (myrequest.parameterExists("search")) {
			mysession.set("search", "");
		}
		if ((! myrequest.getParameter("section").equals(" ")) && (! myrequest.getParameter("section").equals(""))) {
			mysession.set("search_section", html.encodeHtmlEntities(myrequest.getParameter("section")));
		} else if (myrequest.getParameter("section").equals(" ")) {
			mysession.set("search_section", "");
		}
		if (myrequest.getParameter("pagesize").equals(" ")) {
			mysession.set("admin_pagesize", "");
		} else if (! myrequest.getParameter("pagesize").equals("")) {
			mysession.set("admin_pagesize", html.encodeHtmlEntities(myrequest.getParameter("pagesize")));
		}

		database = new Databases(text);
		database.read(db, myconfig, myrequest.getParameter("database"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));

		database.getTitleColumn();
		titleid = database.getTitleColumnId();
		titlename = database.getTitleColumnName();
		titlecolumn = database.getTitleColumnColumn();

		String SQLselect = "";
		HashMap<String,String> indexnames = new HashMap<String,String>();
		HashMap<String,String> indexcolumns = new HashMap<String,String>();
		String[] mycolumns = database.getAdminIndex().split(" ");
		for (int i=0; i<mycolumns.length; i++) {
			String mycolumn = mycolumns[i];
			String mycolumnname = "";
			if (mycolumn.matches("^(.+?):(.+?):(.+)$")) {
				mycolumnname = mycolumn.replaceAll("^(.+?):(.+?):(.+)$", "$1");
			} else if (mycolumn.matches("^(.+):(.+)$")) {
				mycolumnname = mycolumn.replaceAll("^(.+):(.+)$", "$1");
			} else {
				mycolumnname = mycolumn;
			}
			if (database.namedcolumns.get(mycolumnname) != null) {
				if (! SQLselect.equals("")) SQLselect += ",";
				SQLselect += "col" + database.namedcolumns.get(mycolumnname).get("id");
				indexnames.put(mycolumnname, "col" + database.namedcolumns.get(mycolumnname).get("id"));
				indexcolumns.put("col" + database.namedcolumns.get(mycolumnname).get("id"), mycolumnname);
			}
		}
		if (SQLselect.equals("")) {
			SQLselect = "id," + titlecolumn;
		} else {
			if (indexcolumns.get(titlecolumn) == null) {
				SQLselect = titlecolumn + "," + SQLselect;
			}
			if (indexnames.get("id") == null) {
				SQLselect = "id," + SQLselect;
			}
		}

		String SQLorderDir = "";
		if (myrequest.getParameter("sort_dir").equals("DESC")) {
			SQLorderDir = " desc";
		}
		String SQLorder = titlecolumn + SQLorderDir + ", id" + SQLorderDir;
		if (db.db_type(db.getDatabase()).equals("mssql")) {
			SQLorder = "substring(" + titlecolumn + ",1,250)" + SQLorderDir + ", id" + SQLorderDir;
		} else if (db.db_type(db.getDatabase()).equals("oracle")) {
			SQLorder = "to_char(" + titlecolumn + ")" + SQLorderDir + ", id" + SQLorderDir;
		} else if (db.db_type(db.getDatabase()).equals("db2")) {
			SQLorder = "varchar(" + titlecolumn + ",250)" + SQLorderDir + ", id" + SQLorderDir;
		} else {
			SQLorder = titlecolumn + SQLorderDir + ", id" + SQLorderDir;
		}
		if (myrequest.getParameter("sort_col").equals("column_title")) {
			// default;
		} else if (myrequest.getParameter("sort_col").equals("column_id")) {
			if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQLorder = "id" + SQLorderDir + ", substring(" + titlecolumn + ",1,250)" + SQLorderDir;
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQLorder = "id" + SQLorderDir + ", to_char(" + titlecolumn + ")" + SQLorderDir;
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQLorder = "id" + SQLorderDir + ", varchar(" + titlecolumn + ",250)" + SQLorderDir;
			} else {
				SQLorder = "id" + SQLorderDir + ", " + titlecolumn + SQLorderDir;
			}
		}

		String SQLwhere = "";
		String[] searchwords = mysession.get("search").replaceAll("'", "?").replaceAll("\"", "?").split(" ");
		for (int i=0; i<searchwords.length; i++) {
			String searchword = searchwords[i];
			if (! searchword.equals("")) {
				String mySQLwhere = "";
				Iterator mycolumn = database.columns.keySet().iterator();
				while (mycolumn.hasNext()) {
					HashMap column = (HashMap)database.columns.get("" + mycolumn.next());
					if (! mySQLwhere.equals("")) {
						mySQLwhere += " or ";
					}
					if ((column.get("type").equals("selectmulti")) || (column.get("type").equals("radio")) || (column.get("type").equals("checkbox")) || (column.get("type").equals("contents")) || (column.get("type").equals("contentclasses")) || (column.get("type").equals("contentgroups")) || (column.get("type").equals("contenttypes")) || (column.get("type").equals("pagegroups")) || (column.get("type").equals("pagetypes")) || (column.get("type").equals("imagegroups")) || (column.get("type").equals("imagetypes")) || (column.get("type").equals("imageformats")) || (column.get("type").equals("filegroups")) || (column.get("type").equals("filetypes")) || (column.get("type").equals("fileformats")) || (column.get("type").equals("linkgroups")) || (column.get("type").equals("linktypes")) || (column.get("type").equals("productgroups")) || (column.get("type").equals("producttypes")) || (column.get("type").equals("versions")) || (column.get("type").equals("databases")) || (column.get("type").equals("data")) || (column.get("type").equals("usernames")) || (column.get("type").equals("useremails")) || (column.get("type").equals("usergroups")) || (column.get("type").equals("usertypes"))) {
						mySQLwhere += "(";
						mySQLwhere += "col" + column.get("id") + " like " + db.quote(searchword);
						mySQLwhere += " or ";
						mySQLwhere += "col" + column.get("id") + " like " + db.quote(searchword + "|%");
						mySQLwhere += " or ";
						mySQLwhere += "col" + column.get("id") + " like " + db.quote("%|" + searchword + "|%");
						mySQLwhere += " or ";
						mySQLwhere += "col" + column.get("id") + " like " + db.quote("%|" + searchword);
						if (Data.csv_options) {
							mySQLwhere += " or ";
							mySQLwhere += "col" + column.get("id") + " like " + db.quote(searchword + ",%");
							mySQLwhere += " or ";
							mySQLwhere += "col" + column.get("id") + " like " + db.quote("%," + searchword + ",%");
							mySQLwhere += " or ";
							mySQLwhere += "col" + column.get("id") + " like " + db.quote("%," + searchword);
						}
						mySQLwhere += ")";
					} else if (column.get("type").equals("number")) {
						if (searchword.matches("^[-+][.,0-9]$")) {
							mySQLwhere += "col" + column.get("id") + " = " + (""+Common.number(searchword)).replaceAll("[.,]0+$", "");
						} else {
							mySQLwhere += "1=0";
						}
					} else if ((column.get("type").equals("datetime")) || (column.get("type").equals("created")) || (column.get("type").equals("updated"))) {
						mySQLwhere += "col" + column.get("id") + " like " + db.quote(searchword + "%");
					} else {
						if (searchword.equals("")) {
							mySQLwhere += db.is_blank("col" + column.get("id"));
						} else {
							if (db.db_type(db.getDatabase()).equals("mssql")) {
								mySQLwhere += "lower(substring(" + "col" + column.get("id") + ",1,datalength(" + "col" + column.get("id") + "))) like " + db.quote("%" + searchword.toLowerCase() + "%");
							} else if (db.db_type(db.getDatabase()).equals("db2")) {
								mySQLwhere += "lower(varchar(" + "col" + column.get("id") + ",32000)) like " + db.quote("%" + searchword.toLowerCase() + "%");
							} else {
								mySQLwhere += "lower(" + "col" + column.get("id") + ") like " + db.quote("%" + searchword.toLowerCase() + "%");
							}
						}
					}
				}
				if (! mySQLwhere.equals("")) {
					if (! SQLwhere.equals("")) {
						SQLwhere += " and ";
					}
					SQLwhere += "(" + mySQLwhere + ")";
				}
			}
		}
		if (SQLwhere.equals("")) SQLwhere = "1=1";

		Data data = new Data();
		if ((! database.getId().equals("")) && (database.getUser())) {
			String SQL = "select count(*) as count from data" + Common.SQL_clean(database.getId()) + " where " + SQLwhere;
			record_count = db.query_value(SQL).intValue();

			String offset = html.encodeHtmlEntities(myrequest.getParameter("offset"));
			String limit = html.encodeHtmlEntities(myrequest.getParameter("page_size"));
			if ((! offset.equals("")) && (! limit.equals(""))) {
				SQL = Common.SQLlimit(db.db_type(db.getDatabase()), "id", SQLselect, "data" + Common.SQL_clean(database.getId()), SQLwhere, SQLorder, (! SQLorderDir.equals("")), Integer.parseInt("0" + offset), Integer.parseInt("0" + limit));
				data.records(db, SQL);
			} else {
				SQL = "select " + SQLselect + " from data" + database.getId() + " where " + SQLwhere + " order by " + SQLorder;
				data.records(db, SQL);
			}
		} else if (! database.getId().equals("")) {
			data.records(db, "select " + SQLselect + " from data" + database.getId() + " where 1=0");
		}
		return data;
	}



	public Data getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Data();
		database = new Databases(text);
		database.read(db, myconfig, myrequest.getParameter("database"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		Data data = new Data();
		if ((! database.getId().equals("")) && (database.getUser())) {
			data.read(db, "data" + database.getId(), myrequest.getParameter("id"));
			data.adjustContent(database.columns);
		}
		return data;
	}



	public Data getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Data();
		database = new Databases(text);
		database.read(db, myconfig, myrequest.getParameter("database"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		Data data = new Data();
		if ((! database.getId().equals("")) && (database.getCreator())) {
			data.read(db, "data" + database.getId(), myrequest.getParameter("id"));
			data.adjustContent(database.columns);
		}
		return data;
	}



	public Data getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Data();
		database = new Databases(text);
		database.read(db, myconfig, myrequest.getParameter("database"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		Data data = new Data();
		if ((! database.getId().equals("")) && (database.getEditor())) {
			data.read(db, "data" + database.getId(), myrequest.getParameter("id"));
			data.adjustContent(database.columns);
		}
		return data;
	}



	public Data getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Data();
		database = new Databases(text);
		database.read(db, myconfig, myrequest.getParameter("database"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		Data data = new Data();
		if ((! database.getId().equals("")) && (database.getPublisher())) {
			data.read(db, "data" + database.getId(), myrequest.getParameter("id"));
			data.adjustContent(database.columns);
		}
		return data;
	}



	public Data getDeleteMultiple(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Data();
		database = new Databases(text);
		database.read(db, myconfig, myrequest.getParameter("database"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));

		database.getTitleColumn();
		titleid = database.getTitleColumnId();
		titlename = database.getTitleColumnName();
		titlecolumn = database.getTitleColumnColumn();

		Data data = new Data();
		String SQLwhere = "(1=0)";
		String[] ids = myrequest.getParameters("id");
		if ((! database.getId().equals("")) && (database.getPublisher()) && (ids.length > 0)) {
			String SQLwhere_in = "";
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				if (! SQLwhere_in.equals("")) {
					SQLwhere_in += ",";
				}
				SQLwhere_in += id;
			}
			SQLwhere += " or id in (" + SQLwhere_in + ")";
		}
		String SQL = "";
		if (db.db_type(db.getDatabase()).equals("mssql")) {
			SQL = "select id, " + titlecolumn + " from data" + database.getId() + " where " + SQLwhere + " order by substring(" + titlecolumn + ",1,250), id";
		} else if (db.db_type(db.getDatabase()).equals("oracle")) {
			SQL = "select id, " + titlecolumn + " from data" + database.getId() + " where " + SQLwhere + " order by to_char(" + titlecolumn + "), id";
		} else if (db.db_type(db.getDatabase()).equals("db2")) {
			SQL = "select id, " + titlecolumn + " from data" + database.getId() + " where " + SQLwhere + " order by varchar(" + titlecolumn + ",250), id";
		} else {
			SQL = "select id, " + titlecolumn + " from data" + database.getId() + " where " + SQLwhere + " order by " + titlecolumn + ", id";
		}
		data.records(db, SQL);
		return data;
	}



	public Data doPost(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String action) throws Exception {
		error = "";
		Page mypage = new Page(text);
		Fileupload filepost = new Fileupload(null, null, null);
		filepost = getFileupload(DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, 32);
		
		//se action for post_collection realiza o fluxo abaixo
		if(action.equals("post_collection")){
			//Recupera o nome da coleção para setar na session
			String nameOfCollection = filepost.getParameter(Str.NAME_COLLECTION); //recupera nome da coleção
			
			if(nameOfCollection != null && !nameOfCollection.equals("")){ //verifica nome da coleção
				//Seta o nome da coleção recuperado pelo parametro
				mysession.set(myconfig.get(db, "getset") + Str.S_COLLECTION, nameOfCollection);//seta na session
				System.out.println("Set session s_collection=" + nameOfCollection);
			}else{
				//não existe parametro name_collection
				//redirect para register collection.
				myresponse.sendRedirect("/page.jsp?id=410");				
			}
		}

		String redirect = myrequest.getParameter("redirect");
		if (redirect.equals("")) redirect = filepost.getParameter("redirect");
		if (! redirect.equals("")) {
			redirect = redirect.replaceAll("##", "###").replaceAll("@@", "@@@");
			if (redirect.indexOf("###")>=0) {
				Pattern p = Pattern.compile("###([_ a-zA-Z0-9\\w]+)###");
				Matcher m = p.matcher(redirect);
				while (m.find()) {
					String elementname = "" + m.group(1);
					String elementvalue = URLEncoder.encode(myrequest.getParameter(elementname));
					if (elementvalue.equals("")) elementvalue = URLEncoder.encode(filepost.getParameter(elementname));
					redirect = redirect.replaceAll("###" + elementname.replaceAll("###", "") + "###", elementvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					m.reset(redirect);
				}
			}
		}
		String redirect_invalid = myrequest.getParameter("redirect_invalid");
		if (redirect_invalid.equals("")) redirect_invalid = filepost.getParameter("redirect_invalid");
		if (! redirect_invalid.equals("")) {
			redirect_invalid = redirect_invalid.replaceAll("##", "###").replaceAll("@@", "@@@");
			if (redirect_invalid.indexOf("###")>=0) {
				Pattern p = Pattern.compile("###([_ a-zA-Z0-9\\w]+)###");
				Matcher m = p.matcher(redirect_invalid);
				while (m.find()) {
					String elementname = "" + m.group(1);
					String elementvalue = URLEncoder.encode(myrequest.getParameter(elementname));
					if (elementvalue.equals("")) elementvalue = URLEncoder.encode(filepost.getParameter(elementname));
					redirect_invalid = redirect_invalid.replaceAll("###" + elementname.replaceAll("###", "") + "###", elementvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					m.reset(redirect_invalid);
				}
			}
		}

		boolean condition_true = true;
		String[] conditions = myrequest.getParameters("if");
		if (conditions.length == 0) conditions = filepost.getParameters("if");
		for (int i=0; i<conditions.length; i++) {
			String condition = "" + conditions[i];
			if (! condition.equals("")) {
				condition = condition.replaceAll("##", "###").replaceAll("@@", "@@@");
				Pattern p = Pattern.compile("###([_ a-zA-Z0-9\\w]+)###");
				Matcher m = p.matcher(condition);
				while (m.find()) {
					String elementname = "" + m.group(1);
					String elementvalue = URLEncoder.encode(myrequest.getParameter(elementname));
					if (elementvalue.equals("")) elementvalue = URLEncoder.encode(filepost.getParameter(elementname));
					condition = condition.replaceAll("###" + elementname.replaceAll("###", "") + "###", elementvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					m.reset(condition);
				}
				mypage = new Page(text);
				condition = mypage.parse_output_replace(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, "", "content_public", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"), condition);
				p = Pattern.compile("^(.*?)(=|!=|>=|<=|&gt;|&lt;|&gt;=|&lt;=)(.*?)$");
				m = p.matcher(condition);
				if (m.find()) {
					String expression_value1 = "" + m.group(1);
					String expression_operand = "" + m.group(2);
					String expression_value2 = "" + m.group(3);
					if ((expression_operand.equals("=")) && (expression_value1.equals(expression_value2))) {
						// ok - condition_true = true;
					} else if ((expression_operand.equals("!=")) && (! expression_value1.equals(expression_value2))) {
						// ok - condition_true = true;
					} else if (((expression_operand.equals(">") || expression_operand.equals("&gt;"))) && (expression_value1.compareTo(expression_value2)>0)) {
						// ok - condition_true = true;
					} else if (((expression_operand.equals("<") || expression_operand.equals("&lt;"))) && (expression_value1.compareTo(expression_value2)<0)) {
						// ok - condition_true = true;
					} else if (((expression_operand.equals(">=") || expression_operand.equals("&gt;="))) && (expression_value1.compareTo(expression_value2)>=0)) {
						// ok - condition_true = true;
					} else if (((expression_operand.equals("<=") || expression_operand.equals("&lt;="))) && (expression_value1.compareTo(expression_value2)<=0)) {
						// ok - condition_true = true;
					} else {
						condition_true = false;
					}
				}
			}
		}

		String[] invalid = Common.array_merge(Common.validateFormData(myrequest), Common.validateFormData(filepost));

		if ((! myconfig.get(db, "captcha").equals("")) && (myconfig.get(db, "captcha_post").equals("yes")) && ((myconfig.get(db, "captcha_user").equals("yes")) || (mysession.get("username").equals("")))) {
			mysession.set("captcha_error", "");
			CAPTCHA mycaptcha = new CAPTCHA(text);
			if (! mycaptcha.valid(mysession, server, myrequest, filepost, myconfig, db)) {
				error += "<br>" + mycaptcha.error;
				mysession.set("captcha_error", mycaptcha.error);
				mysession.set("POST", Common.serialize(myrequest));
				if (redirect_invalid.equals("")) redirect_invalid =  mysession.get("captcha_url").replaceAll("[?&]invalid=[^?&]*", "");
				String[] newinvalid = new String[invalid.length+1];
				System.arraycopy(invalid, 0, newinvalid, 0, invalid.length);
				newinvalid[newinvalid.length-1] = "CAPTCHA";
				invalid = newinvalid;
				condition_true = false;
			}
		}

		if ((myconfig.get(db, "authorize_post").equals("yes")) && (! Common.authorized(mysession, myrequest, "post"))) {
			String[] newinvalid = new String[invalid.length+1];
			System.arraycopy(invalid, 0, newinvalid, 0, invalid.length);
			newinvalid[newinvalid.length-1] = "AUTHORIZE";
			invalid = newinvalid;
			condition_true = false;
		}

		if (((! condition_true) || (invalid.length > 0)) && (! redirect_invalid.equals(""))) {
			if (redirect_invalid.indexOf("?")>=0) {
				redirect = redirect_invalid + "&invalid=" + Common.join(",", invalid);
			} else {
				redirect = redirect_invalid + "?invalid=" + Common.join(",", invalid);
			}
		}

		Data data = new Data();
		database = new Databases(text);
		database.readTitle(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("database"));
		String data_id = "";
		if (condition_true && (invalid.length == 0) && (! database.getId().equals("")) && database.getCreator()) {
			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			String username = mysession.get("username");
			if (username.equals("")) username = myrequest.getRemoteHost();
			if (username.equals("")) username = myrequest.getRemoteAddr();

			HashMap<String,String> postedfiles = new HashMap<String,String>();
			Iterator filepostparameternames = filepost.getParameterNames();
			while (filepostparameternames.hasNext()) {
				String inputname = "" + filepostparameternames.next();
				String inputvalue = filepost.getParameter(inputname);
				if ((! filepost.getParameter(inputname + "_id").equals("")) && (! filepost.getParameter(inputname + ".filename").equals("")) && (! filepost.getParameter(inputname + ".fullpathname").equals(""))) {
					Content myfile = new Content();
					myfile.public_read(db, myconfig, filepost.getParameter(inputname + "_id"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
					if ((! myfile.getId().equals("")) && (myfile.getCreator())) {
						if (! filepost.getParameter(inputname + "_title").equals("")) {
							myfile.setTitle(filepost.getParameter(inputname + "_title"));
						} else {
							myfile.setTitle(filepost.getParameter(inputname + ".filename"));
						}
						myfile.setServerFilename(filepost.getParameter(inputname + ".server_filename"));

						String folder = filepost.getParameter(inputname + "_folder");
						folder = folder.replaceAll("^/", "").replaceAll("/$", "").replaceAll("\\.\\.", "").replaceAll("[^-_./a-zA-Z0-9]", "");
						if ((! folder.equals("")) && (! folder.toLowerCase().equals(text.display("adminpath").toLowerCase())) && (! folder.toLowerCase().startsWith(text.display("adminpath")+"/")) && (! folder.toLowerCase().equals("bizcard")) && (! folder.toLowerCase().startsWith("bizcard/")) && (! folder.toLowerCase().equals("password")) && (! folder.toLowerCase().startsWith("password/")) && (! folder.toLowerCase().equals("personal")) && (! folder.toLowerCase().startsWith("personal/")) && (! folder.toLowerCase().equals("upload")) && (! folder.toLowerCase().startsWith("upload/")) && (! folder.toLowerCase().equals("webadmin")) && (! folder.toLowerCase().startsWith("webadmin/")) && (! folder.toLowerCase().equals("app_code")) && (! folder.toLowerCase().startsWith("app_code/")) && (! folder.toLowerCase().equals("app_data")) && (! folder.toLowerCase().startsWith("app_data/")) && (! folder.toLowerCase().equals("aspnet_client")) && (! folder.toLowerCase().startsWith("aspnet_client/")) && (! folder.toLowerCase().equals("web-inf")) && (! folder.toLowerCase().startsWith("web-inf/")) && Common.folderExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + folder), text, server, db, myconfig, mysession, myrequest, myresponse)) {
							myfile.setServerFilename(myfile.getServerFilename().replaceAll(myconfig.get(db, "URLuploadpath"), folder + "/"));
						} else if (myfile.getContentClass().equals("image")) {
							myfile.setServerFilename(myfile.getServerFilename().replaceAll(myconfig.get(db, "URLuploadpath"), myconfig.get(db, "URLimagepath")));
						} else if (myfile.getContentClass().equals("file")) {
							myfile.setServerFilename(myfile.getServerFilename().replaceAll(myconfig.get(db, "URLuploadpath"), myconfig.get(db, "URLfilepath")));
						}

						if (myfile.validFilenameExtension(db, filepost.getParameter(inputname + ".filenameextension"))) {
							myfile.setServerFilename(Common.findUniqueFilename(Common.getRealPath(server, myconfig.get(db, "URLrootpath")), myfile.getServerFilename(), text, server, db, myconfig, mysession, myrequest, myresponse));
							if (! Common.moveFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + filepost.getParameter(inputname + ".server_filename")), Common.getRealPath(server, myconfig.get(db, "URLrootpath") + myfile.getServerFilename()))) {
								myfile.setServerFilename("");
							}
						} else {
							Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + filepost.getParameter(inputname + ".server_filename")));
							error = text.display("error.content.upload.format") + " " + filepost.getParameter(inputname + ".filenameextension");
							myfile.setServerFilename("");
						}

						myfile.setCreated(timestamp);
						myfile.setCreatedBy(username);
						myfile.setUpdated(timestamp);
						myfile.setUpdatedBy(username);
						myfile.setRequestedPublish("");
						myfile.setRequestedUnpublish("");
						myfile.setScheduledPublish("");
						myfile.setScheduledUnpublish("");
						if (myfile.getPublisher()) {
							myfile.setPublished(timestamp);
							myfile.setPublishedBy(username);
							myfile.setUnpublished("");
							myfile.setUnpublishedBy("");
						} else {
							myfile.setPublished("");
							myfile.setPublishedBy("");
						}
						myfile.create(db, myconfig, "content", "id");
						if (myfile.getPublisher()) {
							myfile.create(db, myconfig, "content_public", "id");
						}

						if (inputname.equals("file1")) {
							mypage.setFile1(myfile.getId());
						} else if (inputname.equals("file2")) {
							mypage.setFile2(myfile.getId());
						} else if (inputname.equals("file3")) {
							mypage.setFile3(myfile.getId());
						} else if (inputname.equals("image1")) {
							mypage.setImage1(myfile.getId());
						} else if (inputname.equals("image2")) {
							mypage.setImage2(myfile.getId());
						} else if (inputname.equals("image3")) {
							mypage.setImage3(myfile.getId());
						}
						postedfiles.put(inputname, myfile.getId());
						handleServerFilenameUploadAPI(server, mysession, myrequest, myresponse, myconfig, db, myfile, myfile.getPublisher());
						if (myfile.getContentClass().equals("image") && (myfile.getImage1().equals("")) && (myfile.getImage2().equals("")) && (myfile.getImage3().equals(""))) {
							handleAdditionalContentUploadAPI(server, mysession, myrequest, myresponse, myconfig, db, myfile, myfile.getPublisher());
						} else if (myfile.getContentClass().equals("file") && (myfile.getFile1().equals("")) && (myfile.getFile2().equals("")) && (myfile.getFile3().equals(""))) {
							handleAdditionalContentUploadAPI(server, mysession, myrequest, myresponse, myconfig, db, myfile, myfile.getPublisher());
						}
					}
				}
			}

			data_id = myrequest.getParameter("id");
			if (data_id.equals("")) data_id = filepost.getParameter("id");
			data.read(db, "data" + database.getId(), data_id);
			
			//se for action post_photo_member, então é um update da foto na database dbmembers
			if(action.equals("post_photo_member")){
				
				//ler a tabela usando o id do usuario >> 'id_member'
				String col = data.getColAdjustContent(database.columns, "id_member"); //recupera o nome original no banco de dados, ex: col1, col2
				HashMap<String, String> rows = data.readWhereILiketo(db, "data" + database.getId(), col, data_id);
				
				String colid_photo = data.getColAdjustContent(database.columns, "path_photo_member"); //col+id do campo da foto no banco de dados
				
				//verifica imagens default
				if (rows.get(colid_photo) != null){
					if(!rows.get(colid_photo).equals("avatar_male.png") && !rows.get(colid_photo).equals("avatar_female.png")){
						
						String namePhotoDelete = "" + rows.get(colid_photo); //recupera nome da foto no banco de dados
						String localImagePath = mysession.get(Str.STORAGE);	
						deleteFileImagePhysically(namePhotoDelete, localImagePath); //método deleta arquivo fisicamente
						
					}
				}
					
			}
			
			//Fluxo abaixo realiza updates geral, exemplo na tabela dbmembers, dbcollection e outras
			
			data.getAccessRestrictions(database, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
			if ((! data.getId().equals("")) && data.getEditor()) {
				String created = data.getCreated(database.columns);
				String createdby = data.getCreatedBy(database.columns);
				data.getFormData(database.columns, filepost);
				data.getFormData(database.columns, myrequest);
				data.getFormData(database.columns, postedfiles);
				data.adjustContent(database.columns);
				data.setCreated(database.columns, created, createdby);
				data.setUpdated(database.columns, timestamp, username);
				data.update(db, "data" + database.getId(), database.columns);
				
			} else {
				
				if(action.equals("post_add_item")){
					//se a action for post_add_item reaiza o fluxo abaixo e verifca quais os parametros com nomes iguais para gravar no banco cada item de registro
					String[] arrayCampos = {"title_item", "description_item", "path_photo_item", "fk_collection_id", "id_item"};//campos do form
					String[] todosTitleItem = filepost.getParameters(arrayCampos[0]);		//retorna todos title item
					String[] todosDescription = filepost.getParameters(arrayCampos[1]);	//retorns todos description item
					String[] todosPathPhotoItem = filepost.getParameters(arrayCampos[2]);		//retorna todas photo item
					String fk_collection_id = filepost.getParameter(arrayCampos[3]); //retorna um id_collection igual para todos
					//caso adicionar mais campos na database, necessario colocar aqui tambem
					
					int quantItens = todosPathPhotoItem.length;	//quantidade de itens para adicionar no banco
					
					for(int i= 0; i < quantItens; i++){
						Fileupload filepostCadaItem = new Fileupload(null, null, null);
						filepostCadaItem.setParameter(arrayCampos[0], todosTitleItem[i]);
						filepostCadaItem.setParameter(arrayCampos[1], todosDescription[i]);
						filepostCadaItem.setParameter(arrayCampos[2], todosPathPhotoItem[i]);
						filepostCadaItem.setParameter(arrayCampos[3], fk_collection_id);
						//caso adicionar mais campos na database, necessario colocar aqui tambem
						
						//Chama a classe data para criar cada item e salvar na tabela
						//filepostCadaItem >>> contem cada item ou linha para ser gravado no banco
						data.getFormData(database.columns, filepostCadaItem);
						data.getFormData(database.columns, myrequest);
						data.getFormData(database.columns, postedfiles);
						data.adjustContent(database.columns);
						data.setCreated(database.columns, timestamp, username);
						data.setUpdated(database.columns, timestamp, username);
						data.create(db, "data" + database.getId(), database.columns);
						
						//Após create e gerar o novo id de cada item pelo sistema, faz update para atualizar o id na database dbcollectionitem
						String idUpdate = data.getId();	//recupera id gerado pelo sistema
						Fileupload filepostUpateID = new Fileupload(null, null, null);
						filepostUpateID.setParameter(arrayCampos[4], idUpdate);	//set parametro do id_item
						
						if ((! data.getId().equals("")) && data.getEditor()) {							
							String created = data.getCreated(database.columns);
							String createdby = data.getCreatedBy(database.columns);
							data.getFormData(database.columns, filepostUpateID);	//filepostUpateID contem o parametro do id_item
							data.getFormData(database.columns, myrequest);
							data.getFormData(database.columns, postedfiles);
							data.adjustContent(database.columns);
							data.setCreated(database.columns, created, createdby);
							data.setUpdated(database.columns, timestamp, username);
							data.update(db, "data" + database.getId(), database.columns);
						}
					}
					
				}else if(action.equals("post_collection")){		
					
					//faz o fluxo abaixo para coleção
					data.getFormData(database.columns, filepost);
					data.getFormData(database.columns, myrequest);
					data.getFormData(database.columns, postedfiles);
					data.adjustContent(database.columns);
					data.setCreated(database.columns, timestamp, username);
					data.setUpdated(database.columns, timestamp, username);
					data.create(db, "data" + database.getId(), database.columns);
					
					//se for action post_collection, faz update do novo id gerado da coleção
					String idUpdate = data.getId();	//recupera id gerado pelo sistema
					Fileupload filepostUpateID = new Fileupload(null, null, null);
					filepostUpateID.setParameter("id_collection", idUpdate);	//set parametro do coleção
					
					if ((! data.getId().equals("")) && data.getEditor()) {							
						String created = data.getCreated(database.columns);
						String createdby = data.getCreatedBy(database.columns);
						data.getFormData(database.columns, filepostUpateID);	//filepostUpateID contem o parametro da coleção
						data.getFormData(database.columns, myrequest);
						data.getFormData(database.columns, postedfiles);
						data.adjustContent(database.columns);
						data.setCreated(database.columns, created, createdby);
						data.setUpdated(database.columns, timestamp, username);
						data.update(db, "data" + database.getId(), database.columns);
					}
					
				}else if(action.equals("post_add_video")){
					
					//faz o fluxo abaixo para add video
					data.getFormData(database.columns, filepost);
					data.getFormData(database.columns, myrequest);
					data.getFormData(database.columns, postedfiles);
					data.adjustContent(database.columns);
					data.setCreated(database.columns, timestamp, username);
					data.setUpdated(database.columns, timestamp, username);
					data.create(db, "data" + database.getId(), database.columns);
					
					//faz update do novo id gerado do video
					String idUpdate = data.getId();	//recupera id gerado pelo sistema
					Fileupload filepostUpateID = new Fileupload(null, null, null);
					filepostUpateID.setParameter("id_video", idUpdate);	//set parametro do coleção
					
					if ((! data.getId().equals("")) && data.getEditor()) {							
						String created = data.getCreated(database.columns);
						String createdby = data.getCreatedBy(database.columns);
						data.getFormData(database.columns, filepostUpateID);	//filepostUpateID contem o parametro da coleção
						data.getFormData(database.columns, myrequest);
						data.getFormData(database.columns, postedfiles);
						data.adjustContent(database.columns);
						data.setCreated(database.columns, created, createdby);
						data.setUpdated(database.columns, timestamp, username);
						data.update(db, "data" + database.getId(), database.columns);
					}
					
				}else if(action.equals("post_category") || action.equals("post_group") || action.equals("post_forum") || 
						action.equals("post_topic") || action.equals("post_comment")){
					
					//faz o fluxo abaixo para criar categoria, grupo, forum, topic ou comment - obs: action generica
					data.getFormData(database.columns, filepost);
					data.getFormData(database.columns, myrequest);
					data.getFormData(database.columns, postedfiles);
					data.adjustContent(database.columns);
					data.setCreated(database.columns, timestamp, username);
					data.setUpdated(database.columns, timestamp, username);
					data.create(db, "data" + database.getId(), database.columns);
					
					String idUpdate = data.getId();	//recupera id gerado pelo sistema
					
					//verifica o parametro para realizar update do id criado
					if(action.equals("post_category")){
						myrequest.setParameter("id_category", idUpdate);	//set parametro do id criado para categoria
					}else if(action.equals("post_group")){
						myrequest.setParameter("id_group", idUpdate);	//set parametro do id criado para grupo
					}else if(action.equals("post_forum")){
						myrequest.setParameter("id_forum", idUpdate);	//set parametro do id criado para forum
					}else if(action.equals("post_topic")){
						myrequest.setParameter("id_topic", idUpdate);	//set parametro do id criado para topic
					}else if(action.equals("post_comment")){
						myrequest.setParameter("id_comment", idUpdate);	//set parametro do id criado para comment
					}else{
						System.out.println("Log - Error não encontrado parametros de id do name do input hidden para atualizar o id do novo registro na database!");
					}
					
					//abaixo faz update do novo id tanto para forum, topic e comment
					if ((! data.getId().equals("")) && data.getEditor()) {							
						String created = data.getCreated(database.columns);
						String createdby = data.getCreatedBy(database.columns);
						data.getFormData(database.columns, filepost);
						data.getFormData(database.columns, myrequest);
						data.getFormData(database.columns, postedfiles);
						data.adjustContent(database.columns);
						data.setCreated(database.columns, created, createdby);
						data.setUpdated(database.columns, timestamp, username);
						data.update(db, "data" + database.getId(), database.columns);
					}
				}
			}
			data_id = "" + data.getId();

			String subject = "";
			String body = "";
			String email_confirmation = myrequest.getParameter("email_confirmation");
			if (email_confirmation.equals("")) email_confirmation = filepost.getParameter("email_confirmation");
			if ((! data_id.equals("")) && (! data_id.equals("0")) && (! email_confirmation.equals(""))) {
				Page confirmation = new Page(text);
				confirmation.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, email_confirmation, mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				subject = "" + confirmation.getTitle();
				subject = subject.replaceAll("@@@database_id@@@", database.getId().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				subject = subject.replaceAll("@@@database@@@", database.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				subject = subject.replaceAll("@@@title@@@", database.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = "" + confirmation.getContent();
				body = body.replaceAll("@@@id@@@", data.getId());
				body = body.replaceAll("@@@class@@@", "data");
				body = body.replaceAll("@@@database_id@@@", database.getId().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("@@@database@@@", database.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("@@@title@@@", database.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("@@@username@@@", username.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				confirmation.setBody(body);
				confirmation.parse_output_extensions(server, myrequest.getRequest(), myresponse.getResponse(), mysession.getSession());
				confirmation.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, confirmation.getId(), "content_public", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				body = confirmation.getBody();
				handleConfirmation(subject, body, database, filepost, server, myrequest, myresponse, mysession, myconfig, db);
			}

			subject = "";
			body = "";
			Page notification = new Page(text);
			String email_template = myrequest.getParameter("email_template");
			if ((email_template.equals("")) && (filepost != null)) email_template = filepost.getParameter("email_template");
			if (! email_template.equals("")) {
				notification.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, email_template, mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				subject = "" + notification.getTitle() + " " +  database.getTitle();
				subject = subject.replaceAll("@@@database_id@@@", database.getId().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				subject = subject.replaceAll("@@@database@@@", database.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				subject = subject.replaceAll("@@@title@@@", database.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = "" + notification.getContent();
			}
			if (body.equals("")) {
				subject = "Database content posted: " + database.getTitle();
				body = "<p>The user '@@@username@@@' has posted the database content item '@@@id@@@'.</p>" + "\r\n" + "\r\n";
				body += "<p>View:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@view@@@\">@@@view@@@</a>" + "\r\n";
				body += "<p>Update/publish:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@update@@@\">@@@update@@@</a>" + "\r\n";
				body += "<p>Delete:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@delete@@@\">@@@delete@@@</a>" + "\r\n";
			}
			body = body.replaceAll("@@@id@@@", data.getId());
			body = body.replaceAll("@@@class@@@", "data");
			body = body.replaceAll("@@@database_id@@@", database.getId().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			body = body.replaceAll("@@@database@@@", database.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			body = body.replaceAll("@@@title@@@", database.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			body = body.replaceAll("@@@username@@@", username.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			User myuser = new User();
			myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, username);
			body = body.replaceAll("@@@name@@@", myuser.getName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			body = body.replaceAll("@@@view@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/data/view.jsp?database=" + database.getId() + "&id=" + data.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
			body = body.replaceAll("@@@update@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/data/update.jsp?database=" + database.getId() + "&id=" + data.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
			body = body.replaceAll("@@@delete@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/data/delete.jsp?database=" + database.getId() + "&id=" + data.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
			body = notification.parse_output_replace_inputs(myrequest, filepost, body);
			notification.setBody(body);
			notification.parse_output_extensions(server, myrequest, myresponse, mysession);
			notification.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, notification.getId(), "content_public", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			body = notification.getBody();
			handleNotification(subject, body, database, filepost, server, myrequest, myresponse, mysession, myconfig, db);
		}

		if (! redirect.endsWith("=")) {
			data_id = "";
		}
		if ((redirect.startsWith("http://")) || (redirect.startsWith("https://"))) {
			if ((! myconfig.get(db, "redirect_urls").trim().equals("")) && (! Common.startsWithAnyOf(Common.crlf_clean(redirect), myconfig.get(db, "redirect_urls")))) {
				// ignore
			} else {
				myresponse.sendRedirect(Common.crlf_clean(redirect + data_id));
			}
		} else if (! redirect.equals("")) {
			myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + Common.crlf_clean(redirect + data_id));
		} else {
			//se redirect for vazio, não redireciona para nenhum lugar! obs:por padrão estava pagina home codigo abaixo
			//myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/");
		}

		return data;
	}



	public Data doCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Data();
		database = new Databases(text);
		database.read(db, myconfig, myrequest.getParameter("database"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		Data data = new Data();
		if ((! database.getId().equals("")) && (database.getCreator())) {
			data.read(db, "data" + database.getId(), myrequest.getParameter("id"));
			data.getForm(myrequest);
			data.adjustContent(database.columns);
			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			String username = mysession.get("username");
			data.setCreated(database.columns, timestamp, username);
			data.setUpdated(database.columns, timestamp, username);
			data.create(db, "data" + database.getId(), database.columns);
		}
		return data;
	}



	public Data doUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Data();
		database = new Databases(text);
		database.read(db, myconfig, myrequest.getParameter("database"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		Data data = new Data();
		if ((! database.getId().equals("")) && (database.getEditor())) {
			data.read(db, "data" + database.getId(), myrequest.getParameter("id"));
			data.getForm(myrequest);
			data.adjustContent(database.columns);
			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			String username = mysession.get("username");
			data.setUpdated(database.columns, timestamp, username);
			data.update(db, "data" + database.getId(), database.columns);
		}
		return data;
	}



	/**
	 * Método deleta uma lista de arquivos imagens fisicamente, passar uma lista com nomes do arquivo e endereço de armazenamento
	 * @param namePhotoDelete
	 * @param localImagePath
	 */
	public void deleteListFileImagePhysically(ArrayList<String> listNamesPhotoDelete, String localImagePath){
		
		for(String namePhoto : listNamesPhotoDelete){
			if(namePhoto != null && !namePhoto.equals("")){
				String pathFileName = localImagePath + namePhoto;
				try {
					
					Common.deleteFile(pathFileName);
					System.out.println("Log - Delete File Image OK local: " + pathFileName);
					
				} catch (Exception e) {
					System.out.println("Log - Error Delete File Image local: " + pathFileName);
					e.printStackTrace();
				}
			} else {
				System.out.println("Log - Delete File Image - name photo " +namePhoto+ " no exists BD!");
			}
		}
	}
	
	/**
	 * Método deleta um arquivo imagem fisicamente, passar o nome do arquivo e endereço de armazenamento
	 * @param namePhotoDelete
	 * @param localImagePath
	 */
	public void deleteFileImagePhysically(String namePhotoDelete, String localImagePath){
		
		if(namePhotoDelete != null && !namePhotoDelete.equals("")){
			String pathFileName = localImagePath + namePhotoDelete;
			try {
				
				Common.deleteFile(pathFileName);
				System.out.println("Log - Delete File Image ok local: " + pathFileName);
				
			} catch (Exception e) {
				System.out.println("Log - Error Delete File Image local: " + pathFileName);
				e.printStackTrace();
			}
		} else {
			System.out.println("Log - Delete File Image - name photo " +namePhotoDelete+ " no exists no BD!");
		}
	}
	
	/**
	 * Método responsavel por deletar uma coleção e seus itens passando o valor do id da coleção para deletar
	 * @param mysession
	 * @param db
	 * @param idDeleteCollection
	 */
	public void doDeleteCollection(Session mysession, DB db, String idDeleteCollection) {

			ArrayList<String> listNamesPhotoDelete = new ArrayList<String>(); //lista de imagens para deletar
			
			String namePhotoDelete = IliketoDAO.getValueOfDatabase(db, "path_photo_collection", "dbcollection", "id", idDeleteCollection);		
			listNamesPhotoDelete.add(namePhotoDelete); 	//adiciona imagem na lista
			IliketoDAO.deleteDadosIliketo(db, "dbcollection", "id", idDeleteCollection); //método deleta dados na database
			
			//Enquanto existir itens na coleção
			while(IliketoDAO.readRecordExistsDatabase(db, "dbcollectionitem", "fk_collection_id", idDeleteCollection)){
				
				String idRealItem = IliketoDAO.getValueOfDatabase(db, "id", "dbcollectionitem", "fk_collection_id", idDeleteCollection);
				String namePhotoItem = IliketoDAO.getValueOfDatabase(db, "path_photo_item", "dbcollectionitem", "id", idRealItem);		
				listNamesPhotoDelete.add(namePhotoItem); 
				IliketoDAO.deleteDadosIliketo(db, "dbcollectionitem", "id", idRealItem);
			}
				
			String localImagePath = mysession.get(Str.STORAGE);			//local da pasta das imagens armazenadas
			deleteListFileImagePhysically(listNamesPhotoDelete, localImagePath); //método deleta uma lista de arquivos fisicamente
				
	}
	
	/**
	 * Método responsavel por deletar um item por vez passando o valor do id do item para deletar
	 * @param mysession
	 * @param db
	 * @param idDeleteItem
	 */
	public void doDeleteItem(Session mysession, DB db, String idDeleteItem) {
		
		//pega nome da foto para deletar
		String namePhotoDelete = IliketoDAO.getValueOfDatabase(db, "path_photo_item", "dbcollectionitem", "id", idDeleteItem);		
		
		String localImagePath = mysession.get(Str.STORAGE);	 		//local da pasta das imagens armazenadas
		deleteFileImagePhysically(namePhotoDelete, localImagePath); //método deleta fisicamente
		
		IliketoDAO.deleteDadosIliketo(db, "dbcollectionitem", "id", idDeleteItem); //método deleta dados na database

	}
	
	/**
	 * Método responsavel por deletar um video por vez passando o valor do id do video para deletar
	 * @param mysession
	 * @param db
	 * @param idDeleteVideo
	 */
	public void doDeleteVideo(Session mysession, DB db, String idDeleteVideo) {
		
		//pega nome da foto para deletar
		String nameVideoDelete = IliketoDAO.getValueOfDatabase(db, "path_photo_video", "dbcollectionvideo", "id", idDeleteVideo);		
		
		String localPath = mysession.get(Str.STORAGE);	 	   //local armazenamento
		deleteFileImagePhysically(nameVideoDelete, localPath); //método deleta fisicamente
		
		IliketoDAO.deleteDadosIliketo(db, "dbcollectionvideo", "id", idDeleteVideo); //método deleta dados na database

	}

	/**
	 * Método deleta membro da database dbmembers e tabela users, deleta todas coleções e itens. Passar o id do member no parametro
	 * @param mysession
	 * @param db
	 * @param idDeleteMember
	 */
	public void doDeleteMember(Session mysession, DB db, String idDeleteMember) { 
		
		//pega nome da foto para deletar
		String namePhotoDelete = IliketoDAO.getValueOfDatabase(db, "path_photo_member", "dbmembers", "id_member", idDeleteMember); //recupera nome da foto do membro no registro
		
		//fotos deafult
		if(namePhotoDelete != null && !namePhotoDelete.equals("avatar_male.png") && !namePhotoDelete.equals("avatar_female.png")){
			String localImagePath = mysession.get(Str.STORAGE);			//local da imagem
			deleteFileImagePhysically(namePhotoDelete, localImagePath); //método deleta fisicamente
		}
		
		//Deleta todos dados do membro na database dbmembers e na tabela users
		IliketoDAO.deleteDadosIliketo(db, "dbmembers", "id_member", idDeleteMember); 	//dbmembers 'data9'
		IliketoDAO.deleteDadosMembroIliketo(db, "users", "id", idDeleteMember);			//tabela users
		
		//Enquanto existir linhas de registro das coleções, chama método doDeleteCollection
		while(IliketoDAO.readRecordExistsDatabase(db, "dbcollection", "fk_user_id", idDeleteMember)){
			String idRealCollection = IliketoDAO.getValueOfDatabase(db, "id", "dbcollection", "fk_user_id", idDeleteMember);
			doDeleteCollection(mysession, db, idRealCollection);
		}
	}



	public Data doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Data();
		database = new Databases(text);
		database.read(db, myconfig, myrequest.getParameter("database"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		Data data = new Data();
		if ((! database.getId().equals("")) && (database.getPublisher())) {
			data.read(db, "data" + database.getId(), myrequest.getParameter("id"));
			data.adjustContent(database.columns);
			data.delete(db, "data" + database.getId());
		}
		return data;
	}



	public void doDeleteMultiple(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		database = new Databases(text);
		database.read(db, myconfig, myrequest.getParameter("database"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		Data data = new Data();
		String ids[] = myrequest.getParameters("id");
		if ((! database.getId().equals("")) && (database.getPublisher()) && (ids.length > 0)) {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				data.read(db, "data" + database.getId(), id);
				data.adjustContent(database.columns);
				data.delete(db, "data" + database.getId());
			}
		}
	}



	public Databases getExport(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Databases(text);
		database = new Databases(text);
		database.read(db, myconfig, myrequest.getParameter("id"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		if ((! database.getId().equals("")) && (database.getAdministrator())) {
			// ok
		} else {
			database = new Databases(text);
		}
		return database;
	}



	public void doExport(Writer out, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		database = new Databases(text);
		database.read(db, myconfig, myrequest.getParameter("database"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		Data data = new Data();
		if ((! database.getId().equals("")) && (database.getAdministrator())) {
			SimpleDateFormat httpDateFormat = new SimpleDateFormat("EEE, dd MMM yyyyy HH:mm:ss z");
			httpDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			String cachedate = "" + httpDateFormat.format(new java.util.Date(new java.util.Date().getTime()+60*30*1000));
			myresponse.setHeader("Expires", cachedate);
			myresponse.setHeader("Cache-Control", "public");
			myresponse.setHeader("Pragma", "cache");
			myresponse.setContentType("text/plain");
			myresponse.setHeader("Content-Disposition", "attachment; filename=" + database.getTitle() + ".txt");
			data.export(out, db, "data" + database.getId(), database.columns);
		}
	}



	public Databases getImport(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Databases(text);
		database = new Databases(text);
		database.read(db, myconfig, myrequest.getParameter("id"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		if ((! database.getId().equals("")) && (database.getAdministrator())) {
			// ok
		} else {
			database = new Databases(text);
		}
		return database;
	}



	public String doImport(String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		String error = "";
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
		String save_content_editor = myconfig.get(db, "content_editor");
		filepost = new Fileupload(myrequest, DOCUMENT_ROOT + myconfig.get(db, "URLrootpath"), myconfig.get(db, "URLuploadpath"));
		myconfig.setTemp("content_editor", save_content_editor);
		if (! filepost.getParameter("database").equals("")) {
			database = new Databases(text);
			database.read(db, myconfig, filepost.getParameter("database"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
			Data data = new Data();
			if ((! database.getId().equals("")) && (database.getAdministrator()) && (! filepost.getParameter("file.filename").equals(""))) {
				if ((myrequest.getParameter("delete").equals("yes")) || (filepost.getParameter("delete").equals("yes"))) {
					data.delete_all(db, "data" + database.getId());
					data.importfile(db, "data" + database.getId(), database.columns, DOCUMENT_ROOT + myconfig.get(db, "URLrootpath") + filepost.getParameter("file.server_filename"), false);
				} else {
					data.importfile(db, "data" + database.getId(), database.columns, DOCUMENT_ROOT + myconfig.get(db, "URLrootpath") + filepost.getParameter("file.server_filename"), true);
				}
			}
		}
		return error;
	}



	private void handleNotification(String subject, String body, Databases database, Fileupload filepost, ServletContext server, Request myrequest, Response myresponse, Session mysession, Configuration myconfig, DB db) throws Exception {
		if ((! myrequest.getParameter("email_notification").equals("")) || ((filepost != null) && (! filepost.getParameter("email_notification").equals("")))) {
			sendNotification(subject, body, database, filepost, server, myrequest, myresponse, mysession, myconfig, db);
		}
	}



	private void sendNotification(String subject, String body, Databases database, Fileupload filepost, ServletContext server, Request myrequest, Response myresponse, Session mysession, Configuration myconfig, DB db) throws Exception {
		String admin_email = Common.join(", ", database.publishersEmails(mysession, myconfig, db));
		if ((! myrequest.getParameter("email_notification_to").equals("")) && (myconfig.get(db, "contact_form_recipients").indexOf(myrequest.getParameter("email_notification_to"))>=0)) {
			if (! admin_email.equals("")) {
				admin_email += ", ";
			}
			if (myrequest.getParameter("email_notification_to").indexOf("@")>=0) {
				admin_email += html.encodeHtmlEntities(myrequest.getParameter("email_notification_to"));
			} else {
				admin_email += html.encodeHtmlEntities(myrequest.getParameter("email_notification_to")) + "@" + myrequest.getServerName();
			}
		} else if ((filepost != null) && (! filepost.getParameter("email_notification_to").equals("")) && (myconfig.get(db, "contact_form_recipients").indexOf(filepost.getParameter("email_notification_to"))>=0)) {
			if (! admin_email.equals("")) {
				admin_email += ", ";
			}
			if (filepost.getParameter("email_notification_to").indexOf("@")>=0) {
				admin_email += html.encodeHtmlEntities(filepost.getParameter("email_notification_to"));
			} else {
				admin_email += html.encodeHtmlEntities(filepost.getParameter("email_notification_to")) + "@" + myrequest.getServerName();
			}
		}
		if (admin_email.equals("")) {
			admin_email = myconfig.get(db, "superadmin_email");
		}
		String from = "";
		if ((! myrequest.getParameter("email_notification_from").equals("")) && (myconfig.get(db, "contact_form_recipients").indexOf(myrequest.getParameter("email_notification_from"))>=0)) {
			if (myrequest.getParameter("email_notification_from").indexOf("@")>=0) {
				from = html.encodeHtmlEntities(myrequest.getParameter("email_notification_from"));
			} else {
				from = html.encodeHtmlEntities(myrequest.getParameter("email_notification_from")) + "@" + myrequest.getServerName();
			}
		} else if ((filepost != null) && (! filepost.getParameter("email_notification_from").equals("")) && (myconfig.get(db, "contact_form_recipients").indexOf(filepost.getParameter("email_notification_from"))>=0)) {
			if (filepost.getParameter("email_notification_from").indexOf("@")>=0) {
				from = html.encodeHtmlEntities(filepost.getParameter("email_notification_from"));
			} else {
				from = html.encodeHtmlEntities(filepost.getParameter("email_notification_from")) + "@" + myrequest.getServerName();
			}
		} else if (! mysession.get("email").equals("")) {
			from = mysession.get("email");
		} else if (! mysession.get("username").equals("")) {
			from = mysession.get("username") + "@" + myrequest.getServerName();
		} else if (! myrequest.getRemoteHost().equals("")) {
			from = myrequest.getRemoteHost() + "@" + myrequest.getServerName();
		} else if (! myrequest.getRemoteAddr().equals("")) {
			from = myrequest.getRemoteAddr() + "@" + myrequest.getServerName();
		} else {
			from = "nobody" + "@" + myrequest.getServerName();
		}
		if (from.indexOf("@")<0) {
			from = from + "@" + myrequest.getServerName();
		}
//		Email.send_email(text, new HashMap<String,String>(), subject, body, "", from, admin_email, myrequest.getServerName(), mysession, myrequest, myresponse, myconfig, db);
		HashMap<String,String> requestForm = Email.getForm(myrequest);
		Email.send_email(text, requestForm, subject, body, "", from, admin_email, "", "", "", "", myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
	}



	private void handleConfirmation(String subject, String body, Databases database, Fileupload filepost, ServletContext server, Request myrequest, Response myresponse, Session mysession, Configuration myconfig, DB db) throws Exception {
		if ((! myrequest.getParameter("email_confirmation").equals("")) || ((filepost != null) && (! filepost.getParameter("email_confirmation").equals("")))) {
			sendConfirmation(subject, body, database, filepost, server, myrequest, myresponse, mysession, myconfig, db);
		}
	}



	private void sendConfirmation(String subject, String body, Databases database, Fileupload filepost, ServletContext server, Request myrequest, Response myresponse, Session mysession, Configuration myconfig, DB db) throws Exception {
		String admin_email = "";
		if ((! myrequest.getParameter("email_confirmation_from").equals("")) && (myconfig.get(db, "contact_form_recipients").indexOf(myrequest.getParameter("email_confirmation_from"))>=0)) {
			if (myrequest.getParameter("email_confirmation_from").indexOf("@")>=0) {
				admin_email += html.encodeHtmlEntities(myrequest.getParameter("email_confirmation_from"));
			} else {
				admin_email += html.encodeHtmlEntities(myrequest.getParameter("email_confirmation_from")) + "@" + myrequest.getServerName();
			}
		} else if ((filepost != null) && (! filepost.getParameter("email_confirmation_from").equals("")) && (myconfig.get(db, "contact_form_recipients").indexOf(filepost.getParameter("email_confirmation_from"))>=0)) {
			if (filepost.getParameter("email_confirmation_from").indexOf("@")>=0) {
				admin_email += html.encodeHtmlEntities(filepost.getParameter("email_confirmation_from"));
			} else {
				admin_email += html.encodeHtmlEntities(filepost.getParameter("email_confirmation_from")) + "@" + myrequest.getServerName();
			}
		}
		if (admin_email.equals("")) {
			admin_email = myconfig.get(db, "superadmin_email");
		}
		String to = "";
		if (! myrequest.getParameter("email_confirmation_to").equals("")) {
			to = myrequest.getParameter(myrequest.getParameter("email_confirmation_to"));
		} else if (! filepost.getParameter("email_confirmation_to").equals("")) {
			to = filepost.getParameter(filepost.getParameter("email_confirmation_to"));
		} else if (! mysession.get("email").equals("")) {
			to = mysession.get("email");
		}
		if (! to.equals("")) {
//			Email.send_email(text, new HashMap<String,String>(), subject, body, "", admin_email, to, myrequest.getServerName(), mysession, myrequest, myresponse, myconfig, db);
			HashMap<String,String> requestForm = Email.getForm(myrequest);
			Email.send_email(text, requestForm, subject, body, "", admin_email, to, "", "", "", "", myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
		}
	}



	private Fileupload getFileupload(String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		return getFileupload(DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, 0);
	}
	private Fileupload getFileupload(String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, int randomize) {
		String save_content_editor = myconfig.get(db, "content_editor");
		Fileupload filepost = new Fileupload(myrequest, DOCUMENT_ROOT + myconfig.get(db, "URLrootpath"), myconfig.get(db, "URLuploadpath"), randomize);
		myconfig.setTemp("content_editor", save_content_editor);
		return filepost;
	}



	private void handleServerFilenameUploadAPI(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Content myfile, boolean do_publish) throws Exception {
		if (myfile.getServerFilename().equals("")) return;
		if (myfile.getContentClass().equals("image")) {
			String original_server_filename = myfile.getServerFilename();
			String new_server_filename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/image"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"", original_server_filename);
			new_server_filename = new_server_filename.replaceAll("^[ \"\r\n\t]*", "").replaceAll("[ \"\r\n\t]*$", "").replaceAll("[\"\r\n\t].*", "");
			if (! new_server_filename.equals(original_server_filename)) {
				myfile.setServerFilename(new_server_filename);
				myfile.update(db, myconfig, myfile.getId(), "content", "id");
				if (do_publish) {
					myfile.update(db, myconfig, myfile.getId(), "content_public", "id");
				}
			}
		} else if (myfile.getContentClass().equals("file")) {
			String original_server_filename = myfile.getServerFilename();
			String new_server_filename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/file"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"", original_server_filename);
			new_server_filename = new_server_filename.replaceAll("^[ \"\r\n\t]*", "").replaceAll("[ \"\r\n\t]*$", "").replaceAll("[\"\r\n\t].*", "");
			if (! new_server_filename.equals(original_server_filename)) {
				myfile.setServerFilename(new_server_filename);
				myfile.update(db, myconfig, myfile.getId(), "content", "id");
				if (do_publish) {
					myfile.update(db, myconfig, myfile.getId(), "content_public", "id");
				}
			}
		}
		String server_filename = myfile.getServerFilename();
		if (! server_filename.equals("")) {
			Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/upload"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + server_filename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:handleServerFilenameUploadAPI1\"", "", server, mysession, myrequest, myresponse);
		}
	}



	private void handleAdditionalContentUploadAPI(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Content myfile, boolean do_publish) throws Exception {
		if (myfile.getServerFilename().equals("")) return;
		if (myfile.getContentClass().equals("image")) {
			String original_id = myfile.getId();
			String original_title = myfile.getTitle();
			String original_page_up = myfile.getPageUp();
			String original_server_filename = myfile.getServerFilename();
			String new_image1 = myfile.getImage1();
			String new_image2 = myfile.getImage2();
			String new_image3 = myfile.getImage3();
			myfile.setPageUp(original_id);

			String myfilename = "";
			if (myfile.getImage1().equals("")) {
				myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/image1"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"");
				myfilename = myfilename.replaceAll("^[ \"\r\n\t]*", "").replaceAll("[ \"\r\n\t]*$", "").replaceAll("[\"\r\n\t].*", "");
				if (! myfilename.equals("")) {
					myfile.setTitle(original_title + " (1)");
					myfile.setServerFilename(myfilename);
					myfile.create(db, myconfig, "content", "id");
					if (do_publish) {
						myfile.create(db, myconfig, "content_public", "id");
					}
					new_image1 = myfile.getId();
				}
			} else {
				Content myoldfile = new Content();
				myoldfile.read(db, myconfig, myfile.getImage1(), "content", "id");
				if (myoldfile.getPageUp().equals(original_id)) {
					if (myoldfile.getServerFilename().equals("")) {
						myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/image1"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"");
						myfilename = myfilename.replaceAll("^[ \"\r\n\t]*", "").replaceAll("[ \"\r\n\t]*$", "").replaceAll("[\"\r\n\t].*", "");
						myfile.setTitle(original_title + " (1)");
						myfile.setServerFilename(myfilename);
						myfile.update(db, myconfig, myoldfile.getId(), "content", "id");
						if (do_publish) {
							myfile.update(db, myconfig, myoldfile.getId(), "content_public", "id");
						}
					} else {
						if (do_publish) {
							myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/image1"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + myoldfile.getServerFilename() + "\"", "", server, mysession, myrequest, myresponse);
						} else {
							myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/image1"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"", "", server, mysession, myrequest, myresponse);
						}
						myfilename = myfilename.replaceAll("^[ \"\r\n\t]*", "").replaceAll("[ \"\r\n\t]*$", "").replaceAll("[\"\r\n\t].*", "");
						myfile.setTitle(original_title + " (1)");
						myfile.setServerFilename(myfilename);
						myfile.update(db, myconfig, myoldfile.getId(), "content", "id");
						if (do_publish) {
							myfile.update(db, myconfig, myoldfile.getId(), "content_public", "id");
						}
					}
				}
			}
			if (! myfilename.equals("")) {
				Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/upload"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + myfilename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:handleAdditionalContentUploadAPI1\"", "", server, mysession, myrequest, myresponse);
			}

			myfilename = "";
			if (myfile.getImage2().equals("")) {
				myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/image2"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"");
				myfilename = myfilename.replaceAll("^[ \"\r\n\t]*", "").replaceAll("[ \"\r\n\t]*$", "").replaceAll("[\"\r\n\t].*", "");
				if (! myfilename.equals("")) {
					myfile.setTitle(original_title + " (2)");
					myfile.setServerFilename(myfilename);
					myfile.create(db, myconfig, "content", "id");
					if (do_publish) {
						myfile.create(db, myconfig, "content_public", "id");
					}
					new_image2 = myfile.getId();
				}
			} else {
				Content myoldfile = new Content();
				myoldfile.read(db, myconfig, myfile.getImage2(), "content", "id");
				if (myoldfile.getPageUp().equals(original_id)) {
					if (myoldfile.getServerFilename().equals("")) {
						myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/image2"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"");
						myfilename = myfilename.replaceAll("^[ \"\r\n\t]*", "").replaceAll("[ \"\r\n\t]*$", "").replaceAll("[\"\r\n\t].*", "");
						myfile.setTitle(original_title + " (2)");
						myfile.setServerFilename(myfilename);
						myfile.update(db, myconfig, myoldfile.getId(), "content", "id");
						if (do_publish) {
							myfile.update(db, myconfig, myoldfile.getId(), "content_public", "id");
						}
					} else {
						if (do_publish) {
							myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/image2"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + myoldfile.getServerFilename() + "\"", "", server, mysession, myrequest, myresponse);
						} else {
							myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/image2"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"", "", server, mysession, myrequest, myresponse);
						}
						myfilename = myfilename.replaceAll("^[ \"\r\n\t]*", "").replaceAll("[ \"\r\n\t]*$", "").replaceAll("[\"\r\n\t].*", "");
						myfile.setTitle(original_title + " (2)");
						myfile.setServerFilename(myfilename);
						myfile.update(db, myconfig, myoldfile.getId(), "content", "id");
						if (do_publish) {
							myfile.update(db, myconfig, myoldfile.getId(), "content_public", "id");
						}
					}
				}
			}
			if (! myfilename.equals("")) {
				Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/upload"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + myfilename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:handleAdditionalContentUploadAPI2\"", "", server, mysession, myrequest, myresponse);
			}

			myfilename = "";
			if (myfile.getImage3().equals("")) {
				myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/image3"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"");
				myfilename = myfilename.replaceAll("^[ \"\r\n\t]*", "").replaceAll("[ \"\r\n\t]*$", "").replaceAll("[\"\r\n\t].*", "");
				if (! myfilename.equals("")) {
					myfile.setTitle(original_title + " (3)");
					myfile.setServerFilename(myfilename);
					myfile.create(db, myconfig, "content", "id");
					if (do_publish) {
						myfile.create(db, myconfig, "content_public", "id");
					}
					new_image3 = myfile.getId();
				}
			} else {
				Content myoldfile = new Content();
				myoldfile.read(db, myconfig, myfile.getImage3(), "content", "id");
				if (myoldfile.getPageUp().equals(original_id)) {
					if (myoldfile.getServerFilename().equals("")) {
						myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/image3"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"");
						myfilename = myfilename.replaceAll("^[ \"\r\n\t]*", "").replaceAll("[ \"\r\n\t]*$", "").replaceAll("[\"\r\n\t].*", "");
						myfile.setTitle(original_title + " (3)");
						myfile.setServerFilename(myfilename);
						myfile.update(db, myconfig, myoldfile.getId(), "content", "id");
						if (do_publish) {
							myfile.update(db, myconfig, myoldfile.getId(), "content_public", "id");
						}
					} else {
						if (do_publish) {
							myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/image3"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + myoldfile.getServerFilename() + "\"", "", server, mysession, myrequest, myresponse);
						} else {
							myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/image3"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"", "", server, mysession, myrequest, myresponse);
						}
						myfilename = myfilename.replaceAll("^[ \"\r\n\t]*", "").replaceAll("[ \"\r\n\t]*$", "").replaceAll("[\"\r\n\t].*", "");
						myfile.setTitle(original_title + " (3)");
						myfile.setServerFilename(myfilename);
						myfile.update(db, myconfig, myoldfile.getId(), "content", "id");
						if (do_publish) {
							myfile.update(db, myconfig, myoldfile.getId(), "content_public", "id");
						}
					}
				}
			}
			if (! myfilename.equals("")) {
				Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/upload"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + myfilename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:handleAdditionalContentUploadAPI3\"", "", server, mysession, myrequest, myresponse);
			}



			myfile.setId(original_id);
			myfile.setTitle(original_title);
			myfile.setPageUp(original_page_up);
			myfile.setServerFilename(original_server_filename);
			if ((! new_image1.equals("")) || (! new_image2.equals("")) || (! new_image3.equals(""))) {
				myfile.setImage1(new_image1);
				myfile.setImage2(new_image2);
				myfile.setImage3(new_image3);
				myfile.update(db, myconfig, myfile.getId(), "content", "id");
				if (do_publish) {
					myfile.update(db, myconfig, myfile.getId(), "content_public", "id");
				}
			}

		} else if (myfile.getContentClass().equals("file")) {
			String original_id = myfile.getId();
			String original_title = myfile.getTitle();
			String original_page_up = myfile.getPageUp();
			String original_server_filename = myfile.getServerFilename();
			String new_file1 = myfile.getFile1();
			String new_file2 = myfile.getFile2();
			String new_file3 = myfile.getFile3();
			myfile.setPageUp(original_id);

			String myfilename = "";
			if (myfile.getFile1().equals("")) {
				myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/file1"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"");
				myfilename = myfilename.replaceAll("^[ \"\r\n\t]*", "").replaceAll("[ \"\r\n\t]*$", "").replaceAll("[\"\r\n\t].*", "");
				if (! myfilename.equals("")) {
					myfile.setTitle(original_title + " (1)");
					myfile.setServerFilename(myfilename);
					myfile.create(db, myconfig, "content", "id");
					if (do_publish) {
						myfile.create(db, myconfig, "content_public", "id");
					}
					new_file1 = myfile.getId();
				}
			} else {
				Content myoldfile = new Content();
				myoldfile.read(db, myconfig, myfile.getFile1(), "content", "id");
				if (myoldfile.getPageUp().equals(original_id)) {
					if (myoldfile.getServerFilename().equals("")) {
						myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/file1"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"");
						myfilename = myfilename.replaceAll("^[ \"\r\n\t]*", "").replaceAll("[ \"\r\n\t]*$", "").replaceAll("[\"\r\n\t].*", "");
						myfile.setTitle(original_title + " (1)");
						myfile.setServerFilename(myfilename);
						myfile.update(db, myconfig, myoldfile.getId(), "content", "id");
						if (do_publish) {
							myfile.update(db, myconfig, myoldfile.getId(), "content_public", "id");
						}
					} else {
						if (do_publish) {
							myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/file1"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + myoldfile.getServerFilename() + "\"", "", server, mysession, myrequest, myresponse);
						} else {
							myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/file1"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"", "", server, mysession.getSession(), myrequest.getRequest(), myresponse.getResponse());
						}
						myfilename = myfilename.replaceAll("^[ \"\r\n\t]*", "").replaceAll("[ \"\r\n\t]*$", "").replaceAll("[\"\r\n\t].*", "");
						myfile.setTitle(original_title + " (1)");
						myfile.setServerFilename(myfilename);
						myfile.update(db, myconfig, myoldfile.getId(), "content", "id");
						if (do_publish) {
							myfile.update(db, myconfig, myoldfile.getId(), "content_public", "id");
						}
					}
				}
			}
			if (! myfilename.equals("")) {
				Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/upload"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + myfilename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:handleAdditionalContentUploadAPI4\"", "", server, mysession, myrequest, myresponse);
			}

			myfilename = "";
			if (myfile.getFile2().equals("")) {
				myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/file2"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"");
				myfilename = myfilename.replaceAll("^[ \"\r\n\t]*", "").replaceAll("[ \"\r\n\t]*$", "").replaceAll("[\"\r\n\t].*", "");
				if (! myfilename.equals("")) {
					myfile.setTitle(original_title + " (2)");
					myfile.setServerFilename(myfilename);
					myfile.create(db, myconfig, "content", "id");
					if (do_publish) {
						myfile.create(db, myconfig, "content_public", "id");
					}
					new_file2 = myfile.getId();
				}
			} else {
				Content myoldfile = new Content();
				myoldfile.read(db, myconfig, myfile.getFile2(), "content", "id");
				if (myoldfile.getPageUp().equals(original_id)) {
					if (myoldfile.getServerFilename().equals("")) {
						myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/file2"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"");
						myfilename = myfilename.replaceAll("^[ \"\r\n\t]*", "").replaceAll("[ \"\r\n\t]*$", "").replaceAll("[\"\r\n\t].*", "");
						myfile.setTitle(original_title + " (2)");
						myfile.setServerFilename(myfilename);
						myfile.update(db, myconfig, myoldfile.getId(), "content", "id");
						if (do_publish) {
							myfile.update(db, myconfig, myoldfile.getId(), "content_public", "id");
						}
					} else {
						if (do_publish) {
							myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/file2"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + myoldfile.getServerFilename() + "\"", "", server, mysession, myrequest, myresponse);
						} else {
							myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/file2"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"", "", server, mysession, myrequest, myresponse);
						}
						myfilename = myfilename.replaceAll("^[ \"\r\n\t]*", "").replaceAll("[ \"\r\n\t]*$", "").replaceAll("[\"\r\n\t].*", "");
						myfile.setTitle(original_title + " (2)");
						myfile.setServerFilename(myfilename);
						myfile.update(db, myconfig, myoldfile.getId(), "content", "id");
						if (do_publish) {
							myfile.update(db, myconfig, myoldfile.getId(), "content_public", "id");
						}
					}
				}
			}
			if (! myfilename.equals("")) {
				Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/upload"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + myfilename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:handleAdditionalContentUploadAPI5\"", "", server, mysession, myrequest, myresponse);
			}

			myfilename = "";
			if (myfile.getFile3().equals("")) {
				myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/file3"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"");
				myfilename = myfilename.replaceAll("^[ \"\r\n\t]*", "").replaceAll("[ \"\r\n\t]*$", "").replaceAll("[\"\r\n\t].*", "");
				if (! myfilename.equals("")) {
					myfile.setTitle(original_title + " (3)");
					myfile.setServerFilename(myfilename);
					myfile.create(db, myconfig, "content", "id");
					if (do_publish) {
						myfile.create(db, myconfig, "content_public", "id");
					}
					new_file3 = myfile.getId();
				}
			} else {
				Content myoldfile = new Content();
				myoldfile.read(db, myconfig, myfile.getFile3(), "content", "id");
				if (myoldfile.getPageUp().equals(original_id)) {
					if (myoldfile.getServerFilename().equals("")) {
						myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/file3"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"");
						myfilename = myfilename.replaceAll("^[ \"\r\n\t]*", "").replaceAll("[ \"\r\n\t]*$", "").replaceAll("[\"\r\n\t].*", "");
						myfile.setTitle(original_title + " (3)");
						myfile.setServerFilename(myfilename);
						myfile.update(db, myconfig, myoldfile.getId(), "content", "id");
						if (do_publish) {
							myfile.update(db, myconfig, myoldfile.getId(), "content_public", "id");
						}
					} else {
						if (do_publish) {
							myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/file3"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + myoldfile.getServerFilename() + "\"", "", server, mysession, myrequest, myresponse);
						} else {
							myfilename = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/file3"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + original_server_filename + "\"", "", server, mysession, myrequest, myresponse);
						}
						myfilename = myfilename.replaceAll("^[ \"\r\n\t]*", "").replaceAll("[ \"\r\n\t]*$", "").replaceAll("[\"\r\n\t].*", "");
						myfile.setTitle(original_title + " (3)");
						myfile.setServerFilename(myfilename);
						myfile.update(db, myconfig, myoldfile.getId(), "content", "id");
						if (do_publish) {
							myfile.update(db, myconfig, myoldfile.getId(), "content_public", "id");
						}
					}
				}
			}
			if (! myfilename.equals("")) {
				Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/upload"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + myfilename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:handleAdditionalContentUploadAPI6\"", "", server, mysession, myrequest, myresponse);
			}



			myfile.setId(original_id);
			myfile.setTitle(original_title);
			myfile.setPageUp(original_page_up);
			myfile.setServerFilename(original_server_filename);
			if ((! new_file1.equals("")) || (! new_file2.equals("")) || (! new_file3.equals(""))) {
				myfile.setFile1(new_file1);
				myfile.setFile2(new_file2);
				myfile.setFile3(new_file3);
				myfile.update(db, myconfig, myfile.getId(), "content", "id");
				if (do_publish) {
					myfile.update(db, myconfig, myfile.getId(), "content_public", "id");
				}
			}

		}
	}



	public int getRecordCount() {
		return record_count;
	}



}
