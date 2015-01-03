package HardCore;

import java.io.*;
import java.io.File;
import java.text.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.*;
import javax.servlet.jsp.*;

public class UCmaintainUsers {
	public HashMap<String,String> usergroups = new HashMap<String,String>();
	public HashMap<String,String> usertypes = new HashMap<String,String>();
	private String error = "";
	private User create_records;
	private int record_count = 0;
	private Text text = new Text();



	public UCmaintainUsers() {
	}



	public UCmaintainUsers(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public User getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new User(text);
		error = "";
		if (myrequest.getParameter("pagesize").equals(" ")) {
			mysession.set("admin_pagesize", "");
		} else if (! myrequest.getParameter("pagesize").equals("")) {
			mysession.set("admin_pagesize", html.encodeHtmlEntities(myrequest.getParameter("pagesize")));
		}
		if (myrequest.getParameter("userclass").equals(" ")) {
			mysession.set("admin_userclass", "");
		} else if (! myrequest.getParameter("userclass").equals("")) {
			mysession.set("admin_userclass", myrequest.getParameter("userclass"));
		}
		if (myrequest.getParameter("usertype").equals(" ")) {
			mysession.set("admin_usertype", "");
		} else if (! myrequest.getParameter("usertype").equals("")) {
			mysession.set("admin_usertype", myrequest.getParameter("usertype"));
		}
		if (myrequest.getParameter("usergroup").equals(" ")) {
			mysession.set("admin_usergroup", "");
		} else if (! myrequest.getParameter("usergroup").equals("")) {
			mysession.set("admin_usergroup", myrequest.getParameter("usergroup"));
		}
		if (myrequest.getParameter("status").equals(" ")) {
			mysession.set("admin_status", "");
		} else if (! myrequest.getParameter("status").equals("")) {
			mysession.set("admin_status", myrequest.getParameter("status"));
		}

		String SQLwhere = "";
		if (mysession.get("admin_userclass").equals("-")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "password", "");
		} else if (! mysession.get("admin_userclass").equals("")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "userclass", "" + mysession.get("admin_userclass"));
		}
		if (mysession.get("admin_usertype").equals("-")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "users.usertype", "");
		} else if (! mysession.get("admin_usertype").equals("")) {
			SQLwhere = Common.SQLwhere_equals_or(SQLwhere, "users.usertype", "" + mysession.get("admin_usertype"), "users_usertypes.usertype", "" + mysession.get("admin_usertype"));
		}
		if (mysession.get("admin_usergroup").equals("-")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "users.usergroup", "");
		} else if (! mysession.get("admin_usergroup").equals("")) {
			SQLwhere = Common.SQLwhere_equals_or(SQLwhere, "users.usergroup", "" + mysession.get("admin_usergroup"), "users_usergroups.usergroup", "" + mysession.get("admin_usergroup"));
		}
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		if (mysession.get("admin_status").equals("pending")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "(" + db.is_not_blank("users.scheduled_publish") + " and (users.scheduled_publish > '" + now + "'))");
		} else if (mysession.get("admin_status").equals("active")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "((" + db.is_blank("users.scheduled_publish") + " or (users.scheduled_publish <= '" + now + "')) and (" + db.is_blank("users.scheduled_unpublish") + " or (users.scheduled_unpublish >= '" + now + "')))");
		} else if (mysession.get("admin_status").equals("expired")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "(" + db.is_not_blank("users.scheduled_unpublish") + " and (users.scheduled_unpublish < '" + now + "'))");
		} else if (mysession.get("admin_status").equals("expiring")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "((" + db.is_not_blank("users.scheduled_notify") + " and (users.scheduled_notify <= '" + now + "')) and (" + db.is_not_blank("users.scheduled_unpublish") + " and (users.scheduled_unpublish > '" + now + "')))");
		}

		String isUsersType = "(" + db.is_not_blank("users_type") + " and users_type in ('" + mysession.get("usertype") + "', " + Common.SQL_list_values(mysession.get("usertypes").split("\\|")) + "))";
		String isUsersGroup = "(" + db.is_not_blank("users_group") + " and users_group in ('" + mysession.get("usergroup") + "', " + Common.SQL_list_values(mysession.get("usergroups").split("\\|")) + "))";
		String isCreatorsType = "(" + db.is_not_blank("creators_type") + " and creators_type in ('" + mysession.get("usertype") + "', " + Common.SQL_list_values(mysession.get("usertypes").split("\\|")) + "))";
		String isCreatorsGroup = "(" + db.is_not_blank("creators_group") + " and creators_group in ('" + mysession.get("usergroup") + "', " + Common.SQL_list_values(mysession.get("usergroups").split("\\|")) + "))";
		String isEditorsType = "(" + db.is_not_blank("editors_type") + " and editors_type in ('" + mysession.get("usertype") + "', " + Common.SQL_list_values(mysession.get("usertypes").split("\\|")) + "))";
		String isEditorsGroup = "(" + db.is_not_blank("editors_group") + " and editors_group in ('" + mysession.get("usergroup") + "', " + Common.SQL_list_values(mysession.get("usergroups").split("\\|")) + "))";
		String isPublishersType = "(" + db.is_not_blank("publishers_type") + " and publishers_type in ('" + mysession.get("usertype") + "', " + Common.SQL_list_values(mysession.get("usertypes").split("\\|")) + "))";
		String isPublishersGroup = "(" + db.is_not_blank("publishers_group") + " and publishers_group in ('" + mysession.get("usergroup") + "', " + Common.SQL_list_values(mysession.get("usergroups").split("\\|")) + "))";
		String isAdministratorsType = "(" + db.is_not_blank("administrators_type") + " and administrators_type in ('" + mysession.get("usertype") + "', " + Common.SQL_list_values(mysession.get("usertypes").split("\\|")) + "))";
		String isAdministratorsGroup = "(" + db.is_not_blank("administrators_group") + " and administrators_group in ('" + mysession.get("usergroup") + "', " + Common.SQL_list_values(mysession.get("usergroups").split("\\|")) + "))";
		String noUsersType = "" + db.is_blank("users_type") + "";
		String noUsersGroup = "" + db.is_blank("users_group") + "";
		String noCreatorsType = "" + db.is_blank("creators_type") + "";
		String noCreatorsGroup = "" + db.is_blank("creators_group") + "";
		String noEditorsType = "" + db.is_blank("editors_type") + "";
		String noEditorsGroup = "" + db.is_blank("editors_group") + "";
		String noPublishersType = "" + db.is_blank("publishers_type") + "";
		String noPublishersGroup = "" + db.is_blank("publishers_group") + "";
		String noAdministratorsType = "" + db.is_blank("administrators_type") + "";
		String noAdministratorsGroup = "" + db.is_blank("administrators_group") + "";

		String isUser = "";
		String isCreator = "";
		String isEditor = "";
		String isPublisher = "";
		String isAdministrator = "";
		if (myconfig.get(db, "accessrestrictions").equals("or")) {
			isUser = "((" + noUsersType + " and " + noUsersGroup + ") or (" + isUsersGroup + " or " + isUsersType + "))";
			isCreator = "((" + noCreatorsType + " and " + noCreatorsGroup + ") or (" + isCreatorsGroup + " or " + isCreatorsType + "))";
			isEditor = "((" + noEditorsType + " and " + noEditorsGroup + ") or (" + isEditorsGroup + " or " + isEditorsType + "))";
			isPublisher = "((" + noPublishersType + " and " + noPublishersGroup + ") or (" + isPublishersGroup + " or " + isPublishersType + "))";
			isAdministrator = "((" + noAdministratorsType + " and " + noAdministratorsGroup + ") or (" + isAdministratorsGroup + " or " + isAdministratorsType + "))";
		} else {
			isUser = "((" + noUsersType + " and " + noUsersGroup + ") or (" + noUsersType + " and " + isUsersGroup + ") or (" + isUsersType + " and " + noUsersGroup + ") or (" + isUsersGroup + " and " + isUsersType + "))";
			isCreator = "((" + noCreatorsType + " and " + noCreatorsGroup + ") or (" + noCreatorsType + " and " + isCreatorsGroup + ") or (" + isCreatorsType + " and " + noCreatorsGroup + ") or (" + isCreatorsGroup + " and " + isCreatorsType + "))";
			isEditor = "((" + noEditorsType + " and " + noEditorsGroup + ") or (" + noEditorsType + " and " + isEditorsGroup + ") or (" + isEditorsType + " and " + noEditorsGroup + ") or (" + isEditorsGroup + " and " + isEditorsType + "))";
			isPublisher = "((" + noPublishersType + " and " + noPublishersGroup + ") or (" + noPublishersType + " and " + isPublishersGroup + ") or (" + isPublishersType + " and " + noPublishersGroup + ") or (" + isPublishersGroup + " and " + isPublishersType + "))";
			isAdministrator = "((" + noAdministratorsType + " and " + noAdministratorsGroup + ") or (" + noAdministratorsType + " and " + isAdministratorsGroup + ") or (" + isAdministratorsType + " and " + noAdministratorsGroup + ") or (" + isAdministratorsGroup + " and " + isAdministratorsType + "))";
		}

		String SQLwhereEdit = "" + SQLwhere;
		if (! mysession.get("username").equals(myconfig.get(db, "superadmin"))) {
			SQLwhereEdit = Common.SQLwhere(SQLwhereEdit, "(" + isUser + " or " + isCreator + " or " + isEditor + " or " + isPublisher + " or " + isAdministrator + ")");
		}
		String SQLwhereCreate = "" + SQLwhere;
		if (! mysession.get("username").equals(myconfig.get(db, "superadmin"))) {
			SQLwhereCreate = Common.SQLwhere(SQLwhereCreate, "(" + isCreator + " or " + isPublisher + " or " + isAdministrator + ")");
		}

		String SQLjoin = "";
		if (! mysession.get("admin_usergroup").equals("")) {
			SQLjoin += " left outer join users_usergroups on users.username=users_usergroups.username";
		}
		if (! mysession.get("admin_usertype").equals("")) {
			SQLjoin += " left outer join users_usertypes on users.username=users_usertypes.username";
		}

		String SQL = "select count(distinct users.id) as count from users " + SQLjoin + " " + SQLwhereEdit;
		record_count = db.query_value(SQL).intValue();

		String SQLorderDir = "";
		if (myrequest.getParameter("sort_dir").equals("DESC")) {
			SQLorderDir = " desc";
		}
		String SQLorder = "users.name" + SQLorderDir + ", users.username" + SQLorderDir + ", users.id" + SQLorderDir;
		if (myrequest.getParameter("sort_col").equals("column_name")) {
			// default;
		} else if (myrequest.getParameter("sort_col").equals("column_username")) {
			SQLorder = "users.username" + SQLorderDir + ", users.name" + SQLorderDir + ", users.id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_id")) {
			SQLorder = "users.id" + SQLorderDir + ", users.name" + SQLorderDir + ", users.username" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_userclass")) {
			SQLorder = "users.userclass" + SQLorderDir + ", users.name" + SQLorderDir + ", users.username" + SQLorderDir + ", users.id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_usergroup")) {
			SQLorder = "users.usergroup" + SQLorderDir + ", users.name" + SQLorderDir + ", users.username" + SQLorderDir + ", users.id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_usertype")) {
			SQLorder = "users.usertype" + SQLorderDir + ", users.name" + SQLorderDir + ", users.username" + SQLorderDir + ", users.id" + SQLorderDir;
		}

		User user = new User(text);
		create_records = new User(text);
		String offset = html.encodeHtmlEntities(myrequest.getParameter("offset"));
		String limit = html.encodeHtmlEntities(myrequest.getParameter("page_size"));
		if ((! offset.equals("")) && (! limit.equals(""))) {
			if (SQLwhereEdit.length() < 6) SQLwhereEdit += "      ";
			if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQL = Common.SQLlimit(db.db_type(db.getDatabase()), "users.id", "distinct users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, substring(users.organisation,1,250) as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish", "users " + SQLjoin, SQLwhereEdit.substring(6), SQLorder, (! SQLorderDir.equals("")), Integer.parseInt("0" + offset), Integer.parseInt("0" + limit));
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQL = Common.SQLlimit(db.db_type(db.getDatabase()), "users.id", "distinct users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, to_char(users.organisation) as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish", "users " + SQLjoin, SQLwhereEdit.substring(6), SQLorder, (! SQLorderDir.equals("")), Integer.parseInt("0" + offset), Integer.parseInt("0" + limit));
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQL = Common.SQLlimit(db.db_type(db.getDatabase()), "users.id", "distinct users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, varchar(users.organisation,250) as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish", "users " + SQLjoin, SQLwhereEdit.substring(6), SQLorder, (! SQLorderDir.equals("")), Integer.parseInt("0" + offset), Integer.parseInt("0" + limit));
			} else {
				SQL = Common.SQLlimit(db.db_type(db.getDatabase()), "users.id", "distinct users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, users.organisation as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish", "users " + SQLjoin, SQLwhereEdit.substring(6), SQLorder, (! SQLorderDir.equals("")), Integer.parseInt("0" + offset), Integer.parseInt("0" + limit));
			}
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQL = "select users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, substring(users.organisation,1,250) as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish from users " + SQLjoin + " where 1=0";
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQL = "select users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, to_char(users.organisation) as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish from users " + SQLjoin + " where 1=0";
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQL = "select users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, varchar(users.organisation,250) as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish from users " + SQLjoin + " where 1=0";
			} else {
				SQL = "select users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, users.organisation as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish from users " + SQLjoin + " where 1=0";
			}
			create_records.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
		} else if (mysession.get("admin_pagesize").equals("")) {
			if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQL = "select users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, substring(users.organisation,1,250) as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish from users " + SQLjoin + " where 1=0";
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQL = "select users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, to_char(users.organisation) as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish from users " + SQLjoin + " where 1=0";
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQL = "select users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, varchar(users.organisation,250) as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish from users " + SQLjoin + " where 1=0";
			} else {
				SQL = "select users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, users.organisation as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish from users " + SQLjoin + " where 1=0";
			}
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQL = "select users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, substring(users.organisation,1,250) as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish from users " + SQLjoin + " " + SQLwhereCreate + " group by users.id, users.locked, users.created, users.updated, users.created_by, users.updated_by, users.name, substring(users.organisation,1,250), users.username, users.password, users.email, users.userclass, users.usertype, users.usergroup, users.users_type, users.users_group, users.creators_type, users.creators_group, users.editors_type, users.editors_group, users.publishers_type, users.publishers_group, users.administrators_type, users.administrators_group, users.scheduled_publish, users.scheduled_unpublish order by users.name, users.username, users.id";
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQL = "select users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, to_char(users.organisation) as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish from users " + SQLjoin + " " + SQLwhereCreate + " group by users.id, users.locked, users.created, users.updated, users.created_by, users.updated_by, users.name, to_char(users.organisation), users.username, users.password, users.email, users.userclass, users.usertype, users.usergroup, users.users_type, users.users_group, users.creators_type, users.creators_group, users.editors_type, users.editors_group, users.publishers_type, users.publishers_group, users.administrators_type, users.administrators_group, users.scheduled_publish, users.scheduled_unpublish order by users.name, users.username, users.id";
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQL = "select users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, varchar(users.organisation,250) as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish from users " + SQLjoin + " " + SQLwhereCreate + " group by users.id, users.locked, users.created, users.updated, users.created_by, users.updated_by, users.name, varchar(users.organisation,250), users.username, users.password, users.email, users.userclass, users.usertype, users.usergroup, users.users_type, users.users_group, users.creators_type, users.creators_group, users.editors_type, users.editors_group, users.publishers_type, users.publishers_group, users.administrators_type, users.administrators_group, users.scheduled_publish, users.scheduled_unpublish order by users.name, users.username, users.id";
			} else {
				SQL = "select users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, users.organisation as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish from users " + SQLjoin + " " + SQLwhereCreate + " group by users.id, users.locked, users.created, users.updated, users.created_by, users.updated_by, users.name, users.username, users.password, users.email, users.userclass, users.usertype, users.usergroup, users.users_type, users.users_group, users.creators_type, users.creators_group, users.editors_type, users.editors_group, users.publishers_type, users.publishers_group, users.administrators_type, users.administrators_group, users.scheduled_publish, users.scheduled_unpublish order by users.name, users.username, users.id";
			}
			create_records.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
		} else {
			if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQL = "select users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, substring(users.organisation,1,250) as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish from users " + SQLjoin + " " + SQLwhereEdit + " group by users.id, users.locked, users.created, users.updated, users.created_by, users.updated_by, users.name, substring(users.organisation,1,250), users.username, users.password, users.email, users.userclass, users.usertype, users.usergroup, users.users_type, users.users_group, users.creators_type, users.creators_group, users.editors_type, users.editors_group, users.publishers_type, users.publishers_group, users.administrators_type, users.administrators_group, users.scheduled_publish, users.scheduled_unpublish order by " + SQLorder;
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQL = "select users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, to_char(users.organisation) as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish from users " + SQLjoin + " " + SQLwhereEdit + " group by users.id, users.locked, users.created, users.updated, users.created_by, users.updated_by, users.name, to_char(users.organisation), users.username, users.password, users.email, users.userclass, users.usertype, users.usergroup, users.users_type, users.users_group, users.creators_type, users.creators_group, users.editors_type, users.editors_group, users.publishers_type, users.publishers_group, users.administrators_type, users.administrators_group, users.scheduled_publish, users.scheduled_unpublish order by " + SQLorder;
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQL = "select users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, varchar(users.organisation,250) as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish from users " + SQLjoin + " " + SQLwhereEdit + " group by users.id, users.locked, users.created, users.updated, users.created_by, users.updated_by, users.name, varchar(users.organisation,250), users.username, users.password, users.email, users.userclass, users.usertype, users.usergroup, users.users_type, users.users_group, users.creators_type, users.creators_group, users.editors_type, users.editors_group, users.publishers_type, users.publishers_group, users.administrators_type, users.administrators_group, users.scheduled_publish, users.scheduled_unpublish order by " + SQLorder;
			} else {
				SQL = "select users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, users.organisation as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish from users " + SQLjoin + " " + SQLwhereEdit + " group by users.id, users.locked, users.created, users.updated, users.created_by, users.updated_by, users.name, users.username, users.password, users.email, users.userclass, users.usertype, users.usergroup, users.users_type, users.users_group, users.creators_type, users.creators_group, users.editors_type, users.editors_group, users.publishers_type, users.publishers_group, users.administrators_type, users.administrators_group, users.scheduled_publish, users.scheduled_unpublish order by " + SQLorder;
			}
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQL = "select users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, substring(users.organisation,1,250) as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish from users " + SQLjoin + " " + SQLwhereCreate + " group by users.id, users.locked, users.created, users.updated, users.created_by, users.updated_by, users.name, substring(users.organisation,1,250), users.username, users.password, users.email, users.userclass, users.usertype, users.usergroup, users.users_type, users.users_group, users.creators_type, users.creators_group, users.editors_type, users.editors_group, users.publishers_type, users.publishers_group, users.administrators_type, users.administrators_group, users.scheduled_publish, users.scheduled_unpublish order by users.name, users.username, users.id";
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQL = "select users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, to_char(users.organisation) as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish from users " + SQLjoin + " " + SQLwhereCreate + " group by users.id, users.locked, users.created, users.updated, users.created_by, users.updated_by, users.name, to_char(users.organisation), users.username, users.password, users.email, users.userclass, users.usertype, users.usergroup, users.users_type, users.users_group, users.creators_type, users.creators_group, users.editors_type, users.editors_group, users.publishers_type, users.publishers_group, users.administrators_type, users.administrators_group, users.scheduled_publish, users.scheduled_unpublish order by users.name, users.username, users.id";
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQL = "select users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, varchar(users.organisation,250) as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish from users " + SQLjoin + " " + SQLwhereCreate + " group by users.id, users.locked, users.created, users.updated, users.created_by, users.updated_by, users.name, varchar(users.organisation,250), users.username, users.password, users.email, users.userclass, users.usertype, users.usergroup, users.users_type, users.users_group, users.creators_type, users.creators_group, users.editors_type, users.editors_group, users.publishers_type, users.publishers_group, users.administrators_type, users.administrators_group, users.scheduled_publish, users.scheduled_unpublish order by users.name, users.username, users.id";
			} else {
				SQL = "select users.id as id, users.locked as locked, users.created as created, users.updated as updated, users.created_by as created_by, users.updated_by as updated_by, users.name as name, users.organisation as organisation, users.username as username, users.password as password, users.email as email, users.userclass as userclass, users.usertype as usertype, users.usergroup as usergroup, users.users_type as users_type, users.users_group as users_group, users.creators_type as creators_type, users.creators_group as creators_group, users.editors_type as editors_type, users.editors_group as editors_group, users.publishers_type as publishers_type, users.publishers_group as publishers_group, users.administrators_type as administrators_type, users.administrators_group as administrators_group, users.scheduled_publish as scheduled_publish, users.scheduled_unpublish as scheduled_unpublish from users " + SQLjoin + " " + SQLwhereCreate + " group by users.id, users.locked, users.created, users.updated, users.created_by, users.updated_by, users.name, users.username, users.password, users.email, users.userclass, users.usertype, users.usergroup, users.users_type, users.users_group, users.creators_type, users.creators_group, users.editors_type, users.editors_group, users.publishers_type, users.publishers_group, users.administrators_type, users.administrators_group, users.scheduled_publish, users.scheduled_unpublish order by users.name, users.username, users.id";
			}
			create_records.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
		}
		return user;
	}



	public User getView(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new User(text);
		error = "";
		User user = new User(text);
		user.read(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"));
		return user;
	}



	public User getCreate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new User(text);
		error = "";
		User user = new User(text);
		user.read(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"));
		if ((myrequest.getParameter("id").equals("")) || (user.getId().equals(""))) {
			if (! mysession.get("admin_userclass").equals("")) {
				user.setUserclass(mysession.get("admin_userclass"));
			}
			if (mysession.get("admin_usertype").equals("-")) {
				user.setUsertype("");
			} else if (! mysession.get("admin_usertype").equals("")) {
				user.setUsertype(mysession.get("admin_usertype"));
			}
			if (mysession.get("admin_usergroup").equals("-")) {
				user.setUsergroup("");
			} else if (! mysession.get("admin_usergroup").equals("")) {
				user.setUsergroup(mysession.get("admin_usergroup"));
			}
			if (! mysession.get("username").equals(myconfig.get(db, "superadmin"))) {
				user.setUser(false);
				user.setEditor(false);
				user.setCreator(false);
				user.setPublisher(false);
				user.setAdministrator(false);
			}
		}
		return user;
	}



	public User getUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new User(text);
		error = "";
		User user = new User(text);
		user.read(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"));
		return user;
	}



	public User getDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new User(text);
		error = "";
		User user = new User(text);
		user.read(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"));
		return user;
	}



	public User getDeleteMultiple(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new User(text);
		error = "";

		String SQLwhere = "";
		if (mysession.get("admin_userclass").equals("-")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "password", "");
		} else if (! mysession.get("admin_userclass").equals("")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "userclass", mysession.get("admin_userclass"));
		}
		if (mysession.get("admin_usertype").equals("-")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "usertype", "");
		} else if (! mysession.get("admin_usertype").equals("")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "usertype", mysession.get("admin_usertype"));
		}
		if (mysession.get("admin_usergroup").equals("-")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "usergroup", "");
		} else if (! mysession.get("admin_usergroup").equals("")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "usergroup", mysession.get("admin_usergroup"));
		}
		if (! mysession.get("username").equals(myconfig.get(db, "superadmin"))) {
			SQLwhere = Common.SQLwhere(SQLwhere, "(((" + db.is_blank("editors_type") + " or editors_type='" + mysession.get("usertype") + "') and (" + db.is_blank("editors_group") + " or editors_group='" + mysession.get("usergroup") + "')) or ((" + db.is_blank("publishers_type") + " or publishers_type='" + mysession.get("usertype") + "') and (" + db.is_blank("publishers_group") + " or publishers_group='" + mysession.get("usergroup") + "')) or ((" + db.is_blank("administrators_type") + " or administrators_type='" + mysession.get("usertype") + "') and (" + db.is_blank("administrators_group") + " or administrators_group='" + mysession.get("usergroup") + "')))" );
		}

		if (! SQLwhere.equals("")) {
			SQLwhere += " and ";
		} else {
			SQLwhere += " where ";
		}
		String SQLwhere_in = "";
		String[] ids = myrequest.getParameters("id");
		if (ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				if (! SQLwhere_in.equals("")) {
					SQLwhere_in += ",";
				}
				SQLwhere_in += id;
			}
			SQLwhere += "id in (" + SQLwhere_in + ")";
		} else {
			SQLwhere += "id in (0)";
		}

		String SQL = "select * from users " + SQLwhere + " order by name, id";
		User user = new User(text);
		user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
		return user;
	}



	public User doSearch(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		return doSearch(mysession, myrequest, myresponse, myconfig, db, true, true);
	}
	public User doSearch(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, boolean edits, boolean creates) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new User(text);
		error = "";
		mysession.set("admin_userclass", "");
		mysession.set("admin_usertype", "");
		mysession.set("admin_usergroup", "");
		if (! myrequest.getParameter("search").equals("")) {
			mysession.set("search", html.encodeHtmlEntities(myrequest.getParameter("search").replaceAll("'", "?").replaceAll("\"", "?")));
		}

		if (myrequest.getParameter("pagesize").equals(" ")) {
			mysession.set("admin_pagesize", "");
		} else if (! myrequest.getParameter("pagesize").equals("")) {
			mysession.set("admin_pagesize", html.encodeHtmlEntities(myrequest.getParameter("pagesize")));
		}

		String SQLwhere = "where (1=1)";
		if (! mysession.get("search").equals("")) {
			String[] searchwords = mysession.get("search").replaceAll("'", "?").replaceAll("\"", "?").split(" ");
			for (int i=0; i<searchwords.length; i++) {
				String searchword = searchwords[i];
				SQLwhere += " AND (";
				SQLwhere += SQL_search("name", searchword);
				SQLwhere += " OR ";
				SQLwhere += SQL_search("organisation", searchword);
				SQLwhere += " OR ";
				SQLwhere += SQL_search("email", searchword);
				SQLwhere += " OR ";
				SQLwhere += SQL_search("username", searchword);
				SQLwhere += " OR ";
				SQLwhere += SQL_search("delivery_name", searchword);
				SQLwhere += " OR ";
				SQLwhere += SQL_search("delivery_organisation", searchword);
				SQLwhere += " OR ";
				SQLwhere += SQL_search("delivery_email", searchword);
				SQLwhere += " OR ";
				SQLwhere += SQL_search("delivery_website", searchword);
				SQLwhere += " OR ";
				SQLwhere += SQL_search("invoice_name", searchword);
				SQLwhere += " OR ";
				SQLwhere += SQL_search("invoice_organisation", searchword);
				SQLwhere += " OR ";
				SQLwhere += SQL_search("invoice_email", searchword);
				SQLwhere += " OR ";
				SQLwhere += SQL_search("invoice_website", searchword);
				SQLwhere += " OR ";
				SQLwhere += SQL_search("notes", searchword);
				SQLwhere += " OR ";
				SQLwhere += SQL_search("userinfo", searchword);
				SQLwhere += " )";
			}
		}

		String isUsersType = "(" + db.is_not_blank("users_type") + " and users_type in ('" + mysession.get("usertype") + "', " + Common.SQL_list_values(mysession.get("usertypes").split("\\|")) + "))";
		String isUsersGroup = "(" + db.is_not_blank("users_group") + " and users_group in ('" + mysession.get("usergroup") + "', " + Common.SQL_list_values(mysession.get("usergroups").split("\\|")) + "))";
		String isCreatorsType = "(" + db.is_not_blank("creators_type") + " and creators_type in ('" + mysession.get("usertype") + "', " + Common.SQL_list_values(mysession.get("usertypes").split("\\|")) + "))";
		String isCreatorsGroup = "(" + db.is_not_blank("creators_group") + " and creators_group in ('" + mysession.get("usergroup") + "', " + Common.SQL_list_values(mysession.get("usergroups").split("\\|")) + "))";
		String isEditorsType = "(" + db.is_not_blank("editors_type") + " and editors_type in ('" + mysession.get("usertype") + "', " + Common.SQL_list_values(mysession.get("usertypes").split("\\|")) + "))";
		String isEditorsGroup = "(" + db.is_not_blank("editors_group") + " and editors_group in ('" + mysession.get("usergroup") + "', " + Common.SQL_list_values(mysession.get("usergroups").split("\\|")) + "))";
		String isPublishersType = "(" + db.is_not_blank("publishers_type") + " and publishers_type in ('" + mysession.get("usertype") + "', " + Common.SQL_list_values(mysession.get("usertypes").split("\\|")) + "))";
		String isPublishersGroup = "(" + db.is_not_blank("publishers_group") + " and publishers_group in ('" + mysession.get("usergroup") + "', " + Common.SQL_list_values(mysession.get("usergroups").split("\\|")) + "))";
		String isAdministratorsType = "(" + db.is_not_blank("administrators_type") + " and administrators_type in ('" + mysession.get("usertype") + "', " + Common.SQL_list_values(mysession.get("usertypes").split("\\|")) + "))";
		String isAdministratorsGroup = "(" + db.is_not_blank("administrators_group") + " and administrators_group in ('" + mysession.get("usergroup") + "', " + Common.SQL_list_values(mysession.get("usergroups").split("\\|")) + "))";
		String noUsersType = "" + db.is_blank("users_type") + "";
		String noUsersGroup = "" + db.is_blank("users_group") + "";
		String noCreatorsType = "" + db.is_blank("creators_type") + "";
		String noCreatorsGroup = "" + db.is_blank("creators_group") + "";
		String noEditorsType = "" + db.is_blank("editors_type") + "";
		String noEditorsGroup = "" + db.is_blank("editors_group") + "";
		String noPublishersType = "" + db.is_blank("publishers_type") + "";
		String noPublishersGroup = "" + db.is_blank("publishers_group") + "";
		String noAdministratorsType = "" + db.is_blank("administrators_type") + "";
		String noAdministratorsGroup = "" + db.is_blank("administrators_group") + "";

		String isUser = "";
		String isCreator = "";
		String isEditor = "";
		String isPublisher = "";
		String isAdministrator = "";
		if (myconfig.get(db, "accessrestrictions").equals("or")) {
			isUser = "((" + noUsersType + " and " + noUsersGroup + ") or (" + isUsersGroup + " or " + isUsersType + "))";
			isCreator = "((" + noCreatorsType + " and " + noCreatorsGroup + ") or (" + isCreatorsGroup + " or " + isCreatorsType + "))";
			isEditor = "((" + noEditorsType + " and " + noEditorsGroup + ") or (" + isEditorsGroup + " or " + isEditorsType + "))";
			isPublisher = "((" + noPublishersType + " and " + noPublishersGroup + ") or (" + isPublishersGroup + " or " + isPublishersType + "))";
			isAdministrator = "((" + noAdministratorsType + " and " + noAdministratorsGroup + ") or (" + isAdministratorsGroup + " or " + isAdministratorsType + "))";
		} else {
			isUser = "((" + noUsersType + " and " + noUsersGroup + ") or (" + noUsersType + " and " + isUsersGroup + ") or (" + isUsersType + " and " + noUsersGroup + ") or (" + isUsersGroup + " and " + isUsersType + "))";
			isCreator = "((" + noCreatorsType + " and " + noCreatorsGroup + ") or (" + noCreatorsType + " and " + isCreatorsGroup + ") or (" + isCreatorsType + " and " + noCreatorsGroup + ") or (" + isCreatorsGroup + " and " + isCreatorsType + "))";
			isEditor = "((" + noEditorsType + " and " + noEditorsGroup + ") or (" + noEditorsType + " and " + isEditorsGroup + ") or (" + isEditorsType + " and " + noEditorsGroup + ") or (" + isEditorsGroup + " and " + isEditorsType + "))";
			isPublisher = "((" + noPublishersType + " and " + noPublishersGroup + ") or (" + noPublishersType + " and " + isPublishersGroup + ") or (" + isPublishersType + " and " + noPublishersGroup + ") or (" + isPublishersGroup + " and " + isPublishersType + "))";
			isAdministrator = "((" + noAdministratorsType + " and " + noAdministratorsGroup + ") or (" + noAdministratorsType + " and " + isAdministratorsGroup + ") or (" + isAdministratorsType + " and " + noAdministratorsGroup + ") or (" + isAdministratorsGroup + " and " + isAdministratorsType + "))";
		}

		if (mysession.get("search").equals("")) {
			SQLwhere = "where (1=0)";
		}

		String SQLwhereEdit = "" + SQLwhere;
		if (! mysession.get("username").equals(myconfig.get(db, "superadmin"))) {
			SQLwhereEdit = Common.SQLwhere(SQLwhereEdit, "(" + isUser + " or " + isCreator + " or " + isEditor + " or " + isPublisher + " or " + isAdministrator + ")");
		}
		String SQLwhereCreate = "" + SQLwhere;
		if (! mysession.get("username").equals(myconfig.get(db, "superadmin"))) {
			SQLwhereCreate = Common.SQLwhere(SQLwhereCreate, "(" + isCreator + " or " + isPublisher + " or " + isAdministrator + ")");
		}

		String SQL = "select count(distinct users.id) as count from users " + SQLwhereEdit;
		record_count = db.query_value(SQL).intValue();

		String SQLorderDir = "";
		if (myrequest.getParameter("sort_dir").equals("DESC")) {
			SQLorderDir = " desc";
		}
		String SQLorder = "name" + SQLorderDir + ", id" + SQLorderDir;
		if (myrequest.getParameter("sort_col").equals("column_user")) {
			// default;
		} else if (myrequest.getParameter("sort_col").equals("column_username")) {
			SQLorder = "username" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_id")) {
			SQLorder = "id" + SQLorderDir + ", name" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_userclass")) {
			SQLorder = "userclass" + SQLorderDir + ", name" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_usergroup")) {
			SQLorder = "usergroup" + SQLorderDir + ", name" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_usertype")) {
			SQLorder = "usertype" + SQLorderDir + ", name" + SQLorderDir + ", id" + SQLorderDir;
		}

		User user = new User(text);
		create_records = new User(text);
		String offset = html.encodeHtmlEntities(myrequest.getParameter("offset"));
		String limit = html.encodeHtmlEntities(myrequest.getParameter("page_size"));
		if ((! offset.equals("")) && (! limit.equals(""))) {
			if (SQLwhereEdit.length() < 6) SQLwhereEdit += "      ";
			SQL = Common.SQLlimit(db.db_type(db.getDatabase()), "id", "*", "users", SQLwhereEdit.substring(6), SQLorder, (! SQLorderDir.equals("")), Integer.parseInt("0" + offset), Integer.parseInt("0" + limit));
			if (edits) {
				user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}
			SQL = "select * from users where 1=0";
			if (creates) {
				create_records.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}
		} else if ((mysession.get("admin_pagesize").equals("")) && (myrequest.getRequestURI().indexOf("searchreplace.jsp") < 0)) {
			SQL = "select * from users where 1=0";
			if (edits) {
				user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}
			SQL = "select * from users " + SQLwhereCreate + " order by " + SQLorder;
			if (creates) {
				create_records.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}
		} else {
			SQL = "select * from users " + SQLwhereEdit + " order by " + SQLorder;
			if (edits) {
				user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}
			SQL = "select * from users " + SQLwhereCreate + " order by " + SQLorder;
			if (creates) {
				create_records.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}
		}
		if ((creates) && (! edits)) {
			return create_records;
		} else {
			return user;
		}
	}



	private String SQL_search(String column, String value) {
		String SQLexpression = column + " like '%" + value + "%'";
		//SQLexpression = ""
		//SQLexpression += column + " like '" & value & "'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '% " & value & "'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '%\>" & value & "'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '" & value & " %'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '% " & value & " %'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '%\>" & value & " %'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '" & value & ",%'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '% " & value & ",%'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '%\>" & value & ",%'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '" & value & ".%'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '% " & value & ".%'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '%\>" & value & ".%'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '" & value & "<%'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '% " & value & "<%'"
		//SQLexpression += " OR "
		//SQLexpression += column + " like '%\>" & value & "<%'"
		return SQLexpression;
	}



	public User doCreate(ServletContext servletcontext, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new User(text);
		error = "";
		User user = new User(text);
		user.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("username"));
		if (! user.getId().equals("")) {
			error += text.display("users.create.usernameexists");
		} else if (myrequest.getParameter("username").trim().equals("")) {
			error += text.display("users.create.nousername");
		} else {
			user.read(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"));
			user.getForm(myrequest, myconfig, db);
			if ((user.getUserclass().equals("administrator")) && (License.restriction(db, myconfig, "administrator") >= 0) && (user.administratorCount(db) >= License.restriction(db, myconfig, "administrator")-1)) {
				user.setUserclass("");
			}
			if ((myrequest.getParameter("id").equals("")) || (user.getId().equals(""))) {
				if (! mysession.get("username").equals(myconfig.get(db, "superadmin"))) {
					user.setUser(false);
					user.setEditor(false);
					user.setCreator(false);
					user.setPublisher(false);
					user.setAdministrator(false);
				}
			}
			if (user.getCreator()) {
				String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
				String username = mysession.get("username");
				user.setCreated(timestamp);
				user.setCreatedBy(username);
				user.setUpdated(timestamp);
				user.setUpdatedBy(username);
				Cms.CMSAudit("action=create user=" + user.getUsername() + " [" + user.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
				user.create(db);
				if (! user.getUsername().equals("")) {
					HashMap<String,Permission> permissions = user.permissions(db, "", "webadmin_%");
					Iterator mypermissions = permissions.keySet().iterator();
					while (mypermissions.hasNext()) {
						String mypermission = "" + mypermissions.next();
						if (! ((Permission)permissions.get(mypermission)).getAction().equals(myrequest.getParameter(((Permission)permissions.get(mypermission)).getResource()))) {
							((Permission)permissions.get(mypermission)).delete(db);
						}
					}
					for (Enumeration names = myrequest.getParameterNames(); names.hasMoreElements();) {
						String name = (String) names.nextElement();
						if (name.startsWith("webadmin_")) {
							Permission mypermission = new Permission();
							mypermission.setAction(myrequest.getParameter(name));
							mypermission.setResource(name);
							mypermission.setUsername(user.getUsername());
							if (! mypermission.exists(db)) {
								mypermission.create(db);
							}
						}
					}
				}
			}
			user.schedule(myconfig, db);
			UCpublishScheduled publishScheduled = new UCpublishScheduled(text);
			publishScheduled.doScheduled(servletcontext, mysession, myrequest, myresponse, myconfig, db);
		}
		return user;
	}



	public User doUpdate(ServletContext servletcontext, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new User(text);
		error = "";
		User userexists = new User(text);
		userexists.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("username"));
		User user = new User(text);
		user.read(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"));
		if ((! userexists.getId().equals("")) && (! myrequest.getParameter("username").equals(user.getUsername()))) {
			error += text.display("users.update.usernameexists");
		} else if (myrequest.getParameter("username").trim().equals("")) {
			error += text.display("users.update.nousername");
		} else {
			String old_username = user.getUsername();
			if (user.getEditor()) {
				user.getForm(myrequest, myconfig, db);
				if ((user.getUserclass().equals("administrator")) && (License.restriction(db, myconfig, "administrator") >= 0) && (user.administratorCount(db) > License.restriction(db, myconfig, "administrator")-1)) {
					user.setUserclass("");
				}
				String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
				String username = mysession.get("username");
				user.setUpdated(timestamp);
				user.setUpdatedBy(username);
				Cms.CMSAudit("action=update user=" + user.getUsername() + " [" + user.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
				user.update(db);
				if (! user.getUsername().equals("")) {
					if ((! old_username.equals(user.getUsername())) && (! old_username.trim().equals("")) && (! user.getUsername().trim().equals(""))) {
						Content content = new Content(text);
						content.renameUsername(db, old_username, user.getUsername());
						user.renameUsername(db, old_username, user.getUsername());
					}
					HashMap<String,Permission> permissions = user.permissions(db, "", "webadmin_%");
					Iterator mypermissions = permissions.keySet().iterator();
					while (mypermissions.hasNext()) {
						String mypermission = "" + mypermissions.next();
						if (! ((Permission)permissions.get(mypermission)).getAction().equals(myrequest.getParameter(((Permission)permissions.get(mypermission)).getResource()))) {
							((Permission)permissions.get(mypermission)).delete(db);
						}
					}
					for (Enumeration names = myrequest.getParameterNames(); names.hasMoreElements();) {
						String name = (String) names.nextElement();
						if (name.startsWith("webadmin_")) {
							Permission mypermission = new Permission();
							mypermission.setAction(myrequest.getParameter(name));
							mypermission.setResource(name);
							mypermission.setUsername(user.getUsername());
							if (! mypermission.exists(db)) {
								mypermission.create(db);
							}
						}
					}
				}
			}
			user.schedule(myconfig, db);
			UCpublishScheduled publishScheduled = new UCpublishScheduled(text);
			publishScheduled.doScheduled(servletcontext, mysession, myrequest, myresponse, myconfig, db);
		}
		return user;
	}



	public User doDelete(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new User(text);
		error = "";
		User user = new User(text);
		user.read(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"));
		if (user.getPublisher()) {
			Cms.CMSAudit("action=delete user=" + user.getUsername() + " [" + user.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
			user.delete(db);
			if (! user.getUsername().equals("")) {
				HashMap<String,Permission> permissions = user.permissions(db, "", "webadmin_%");
				Iterator mypermissions = permissions.keySet().iterator();
				while (mypermissions.hasNext()) {
					String mypermission = "" + mypermissions.next();
					if (! ((Permission)permissions.get(mypermission)).getAction().equals(myrequest.getParameter(((Permission)permissions.get(mypermission)).getResource()))) {
						((Permission)permissions.get(mypermission)).delete(db);
					}
				}
			}
		}
		return user;
	}



	public void doDeleteMultiple(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		error = "";
		String[] ids = myrequest.getParameters("id");
		if (ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				User user = new User(text);
				user.read(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, id);
				if (user.getPublisher()) {
					Cms.CMSAudit("action=delete user=" + user.getUsername() + " [" + user.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
					user.delete(db);
					if (! user.getUsername().equals("")) {
						HashMap<String,Permission> permissions = user.permissions(db, "", "webadmin_%");
						Iterator mypermissions = permissions.keySet().iterator();
						while (mypermissions.hasNext()) {
							String mypermission = "" + mypermissions.next();
							if (! ((Permission)permissions.get(mypermission)).getAction().equals(myrequest.getParameter(((Permission)permissions.get(mypermission)).getResource()))) {
								((Permission)permissions.get(mypermission)).delete(db);
							}
						}
					}
				}
			}
		}
	}



	public void doMoveMultiple(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		error = "";
		String[] ids = myrequest.getParameters("id");
		if (ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				User user = new User(text);
				user.read(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, id);
				if (user.getEditor()) {
					if (myrequest.getParameter("usergroup").equals(" ")) {
						user.setUsergroup("");
					} else if (! myrequest.getParameter("usergroup").equals("")) {
						user.setUsergroup(myrequest.getParameter("usergroup"));
					}
					if (myrequest.getParameter("usertype").equals(" ")) {
						user.setUsertype("");
					} else if (! myrequest.getParameter("usertype").equals("")) {
						user.setUsertype(myrequest.getParameter("usertype"));
					}
					Cms.CMSAudit("action=update user=" + user.getUsername() + " [" + user.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
					user.update(db);
				}
			}
		}
	}



	public User getPassword(ServletContext servletcontext, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new User(text);
		error = "";
		User user = new User(text);
		user.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mysession.get("username"));
		return user;
	}



	public User doPassword(ServletContext servletcontext, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new User(text);
		error = "";
		User user = new User(text);
		user.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mysession.get("username"));
		if ((! user.getId().equals("")) && (mysession.get("username").equals(user.getUsername()))) {
			String old_password = myrequest.getParameter("old_password");
			if (User.DIGEST) {
				old_password = db.digest(old_password, 250);
			}
			String new_password = myrequest.getParameter("new_password");
			String new_password2 = myrequest.getParameter("new_password2");
			if (! old_password.equals(user.getPassword())) {
				error += text.display("users.password.wrongpassword");
			} else if (old_password.equals(new_password)) {
				error += text.display("users.password.samepassword");
			} else if (new_password.equals("")) {
				error += text.display("users.password.nopassword");
			} else if (! new_password.equals(new_password2)) {
				error += text.display("users.password.diffentpasswords");
			} else {
				user.setPassword(new_password);
				String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
				String username = mysession.get("username");
				user.setUpdated(timestamp);
				user.setUpdatedBy(username);
				Cms.CMSAudit("action=password user=" + user.getUsername() + " [" + user.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
				user.update(db);
				mysession.set("password", new_password);
				mysession.set("user_updated", timestamp);
			}
		}
		return user;
	}



	public User getPersonal(Session mysession, Configuration myconfig, DB db) {
		error = "";
		User user = new User(text);
		user.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mysession.get("username"));
		return user;
	}



	public User doPersonalUpdate(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		error = "";
		User user = getPersonal(mysession, myconfig, db);
		if ((! user.getUsername().equals("")) && (myrequest.getMethod().equals("POST"))) {
			String old_username = user.getUsername();
			boolean userUpdated = false;
			HashMap<String,String> requestForm = Email.getForm(myrequest);
			Iterator requestFormItems = requestForm.keySet().iterator();
			while (requestFormItems.hasNext()) {
				String inputname = "" + requestFormItems.next();
				String inputvalue = "" + requestForm.get(inputname);
				if ((inputname.equals("name")) && (! inputvalue.equals(""))) {
					user.setName(inputvalue);
					userUpdated = true;
				}
				if ((inputname.equals("email")) && (! inputvalue.equals(""))) {
					user.setEmail(inputvalue);
					userUpdated = true;
				}
				if ((inputname.equals("username")) && (! inputvalue.equals(""))) {
					User other_user = new User(text);
					other_user.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, inputvalue);
					if (other_user.getUsername().equals(inputvalue)) {
						error += text.display("register.error.exists");
					} else {
						user.setUsername(inputvalue);
						userUpdated = true;
					}
				}
				if ((inputname.equals("password")) && (! inputvalue.equals(""))) {
					user.setPassword(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("organisation")) {
					user.setOrganisation(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("card_type")) {
					user.setCardType(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("card_number")) {
					user.setCardNumber(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("card_issuedmonth")) {
					user.setCardIssuedMonth(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("card_issuedyear")) {
					user.setCardIssuedYear(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("card_expirymonth")) {
					user.setCardExpiryMonth(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("card_expiryyear")) {
					user.setCardExpiryYear(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("card_name")) {
					user.setCardName(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("card_cvc")) {
					user.setCardCVC(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("card_issue")) {
					user.setCardIssue(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("card_postalcode")) {
					user.setCardPostalcode(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("delivery_name")) {
					user.setDeliveryName(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("delivery_organisation")) {
					user.setDeliveryOrganisation(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("delivery_address")) {
					user.setDeliveryAddress(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("delivery_postalcode")) {
					user.setDeliveryPostalcode(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("delivery_city")) {
					user.setDeliveryCity(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("delivery_state")) {
					user.setDeliveryState(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("delivery_country")) {
					user.setDeliveryCountry(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("delivery_phone")) {
					user.setDeliveryPhone(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("delivery_fax")) {
					user.setDeliveryFax(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("delivery_email")) {
					user.setDeliveryEmail(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("delivery_website")) {
					user.setDeliveryWebsite(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("invoice_name")) {
					user.setInvoiceName(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("invoice_organisation")) {
					user.setInvoiceOrganisation(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("invoice_address")) {
					user.setInvoiceAddress(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("invoice_postalcode")) {
					user.setInvoicePostalcode(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("invoice_city")) {
					user.setInvoiceCity(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("invoice_state")) {
					user.setInvoiceState(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("invoice_country")) {
					user.setInvoiceCountry(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("invoice_phone")) {
					user.setInvoicePhone(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("invoice_fax")) {
					user.setInvoiceFax(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("invoice_email")) {
					user.setInvoiceEmail(inputvalue);
					userUpdated = true;
				}
				if (inputname.equals("invoice_website")) {
					user.setInvoiceWebsite(inputvalue);
					userUpdated = true;
				}
				if (inputname.startsWith("userinfo_")) {
					inputname = inputname.replaceAll("^userinfo_", "").replaceAll("_", " ");
					user.setUserinfo(user.getUserinfo().replaceAll("<" + inputname + ">" + ".*" + "</" + inputname + ">", ("<" + inputname + ">" + inputvalue + "</" + inputname + ">").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
					userUpdated = true;
				}
			}
			if (userUpdated) {
				String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
				String username = mysession.get("username");
				user.setUpdated(timestamp);
				user.setUpdatedBy(username);
				Cms.CMSAudit("action=update user=" + user.getUsername() + " [" + user.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
				user.update(db);
				mysession.set("username", "" + user.getUsername());
				mysession.set("password", "" + user.getPassword());
			}
			if (! old_username.equals(user.getUsername())) {
				Content content = new Content(text);
				content.renameUsername(db, old_username, user.getUsername());
				user.renameUsername(db, old_username, user.getUsername());
			}
		}
		return user;
	}



	public void doSubscribe(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.User(text, mysession.get("username"), myrequest, myresponse, mysession, db);
		if (! accesspermission) return;
		User user = new User(text);
		user.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mysession.get("username"));
		if (user.getUsername().equals(mysession.get("username"))) {
			if (! myrequest.getParameter("usergroup").equals("")) {
				Usergroup usergroup = new Usergroup();
				usergroup.readUsergroup(db, myrequest.getParameter("usergroup"));
				if (usergroup.getUsergroup().equals(myrequest.getParameter("usergroup"))) {
					if ((! usergroup.getSubscribersGroup().equals("")) || (! usergroup.getSubscribersType().equals(""))) {
						if ((((usergroup.getSubscribersGroup().equals("*")) || (usergroup.getSubscribersGroup().equals(mysession.get("usergroup"))) || (("|" + mysession.get("usergroups") + "|").indexOf("|" + usergroup.getSubscribersGroup() + "|") >= 0)) && ((usergroup.getSubscribersType().equals("*")) || (usergroup.getSubscribersType().equals(mysession.get("usertype"))) || (("|" + mysession.get("usertypes") + "|").indexOf("|" + usergroup.getSubscribersType() + "|") >= 0)))) {
							user.addUsergroup(db, myrequest.getParameter("usergroup"));
						}
					}
				}
			}
			if (! myrequest.getParameter("usertype").equals("")) {
				Usertype usertype = new Usertype();
				usertype.readUsertype(db, myrequest.getParameter("usertype"));
				if (usertype.getUsertype().equals(myrequest.getParameter("usertype"))) {
					if ((! usertype.getSubscribersGroup().equals("")) || (! usertype.getSubscribersType().equals(""))) {
						if ((((usertype.getSubscribersGroup().equals("*")) || (usertype.getSubscribersGroup().equals(mysession.get("usertype"))) || (("|" + mysession.get("usertypes") + "|").indexOf("|" + usertype.getSubscribersGroup() + "|") >= 0)) && ((usertype.getSubscribersType().equals("*")) || (usertype.getSubscribersType().equals(mysession.get("usertype"))) || (("|" + mysession.get("usertypes") + "|").indexOf("|" + usertype.getSubscribersType() + "|") >= 0)))) {
							user.addUsertype(db, myrequest.getParameter("usertype"));
						}
					}
				}
			}
			if ((myrequest.getParameter("redirect").startsWith("http://")) || (myrequest.getParameter("redirect").startsWith("https://"))) {
				if ((! myconfig.get(db, "redirect_urls").trim().equals("")) && (! Common.startsWithAnyOf(Common.crlf_clean(myrequest.getParameter("redirect")), myconfig.get(db, "redirect_urls")))) {
					// ignore
				} else {
					myresponse.sendRedirect(Common.crlf_clean(myrequest.getParameter("redirect")));
				}
			} else if (! myrequest.getParameter("redirect").equals("")) {
				myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + Common.crlf_clean(myrequest.getParameter("redirect")));
			} else {
				myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort());
			}
		}
	}



	public void doUnsubscribe(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.User(text, mysession.get("username"), myrequest, myresponse, mysession, db);
		if (! accesspermission) return;
		User user = new User(text);
		user.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mysession.get("username"));
		if (user.getUsername().equals(mysession.get("username"))) {
			if (! myrequest.getParameter("usergroup").equals("")) {
				Usergroup usergroup = new Usergroup();
				usergroup.readUsergroup(db, myrequest.getParameter("usergroup"));
				if (usergroup.getUsergroup().equals(myrequest.getParameter("usergroup"))) {
					if ((! usergroup.getSubscribersGroup().equals("")) || (! usergroup.getSubscribersType().equals(""))) {
						if ((((usergroup.getSubscribersGroup().equals("*")) || (usergroup.getSubscribersGroup().equals(mysession.get("usergroup"))) || (("|" + mysession.get("usergroups") + "|").indexOf("|" + usergroup.getSubscribersGroup() + "|") >= 0)) && ((usergroup.getSubscribersType().equals("*")) || (usergroup.getSubscribersType().equals(mysession.get("usertype"))) || (("|" + mysession.get("usertypes") + "|").indexOf("|" + usergroup.getSubscribersType() + "|") >= 0)))) {
							user.removeUsergroup(db, myrequest.getParameter("usergroup"));
						}
					}
				}
			}
			if (! myrequest.getParameter("usertype").equals("")) {
				Usertype usertype = new Usertype();
				usertype.readUsertype(db, myrequest.getParameter("usertype"));
				if (usertype.getUsertype().equals(myrequest.getParameter("usertype"))) {
					if ((! usertype.getSubscribersGroup().equals("")) || (! usertype.getSubscribersType().equals(""))) {
						if ((((usertype.getSubscribersGroup().equals("*")) || (usertype.getSubscribersGroup().equals(mysession.get("usertype"))) || (("|" + mysession.get("usertypes") + "|").indexOf("|" + usertype.getSubscribersGroup() + "|") >= 0)) && ((usertype.getSubscribersType().equals("*")) || (usertype.getSubscribersType().equals(mysession.get("usertype"))) || (("|" + mysession.get("usertypes") + "|").indexOf("|" + usertype.getSubscribersType() + "|") >= 0)))) {
							user.removeUsertype(db, myrequest.getParameter("usertype"));
						}
					}
				}
			}
			if ((myrequest.getParameter("redirect").startsWith("http://")) || (myrequest.getParameter("redirect").startsWith("https://"))) {
				if ((! myconfig.get(db, "redirect_urls").trim().equals("")) && (! Common.startsWithAnyOf(Common.crlf_clean(myrequest.getParameter("redirect")), myconfig.get(db, "redirect_urls")))) {
					// ignore
				} else {
					myresponse.sendRedirect(Common.crlf_clean(myrequest.getParameter("redirect")));
				}
			} else if (! myrequest.getParameter("redirect").equals("")) {
				myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + Common.crlf_clean(myrequest.getParameter("redirect")));
			} else {
				myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort());
			}
		}
	}



	public String getError() {
		return error;
	}



	public User getCreateRecords() {
		return create_records;
	}



	public int getRecordCount() {
		return record_count;
	}



	public User getExport(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new User(text);
		User users = new User(text);
		return users;
	}



	public User getImport(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new User(text);
		User users = new User(text);
		return users;
	}



	public User doExport(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new User(text);

		usergroups = new HashMap<String,String>();
		String SQL = "select username,usergroup from users_usergroups order by username,usergroup";
		HashMap<String,HashMap<String,String>> usergrouprows = db.query_records(SQL);
		Iterator myusergrouprows = usergrouprows.keySet().iterator();
		while (myusergrouprows.hasNext()) {
			String myusergrouprow = "" + myusergrouprows.next();
			String username = "" + ((HashMap<String,String>)usergrouprows.get(myusergrouprow)).get("username");
			String usergroup = "" + ((HashMap<String,String>)usergrouprows.get(myusergrouprow)).get("usergroup");
			if (usergroups.get(username) == null) {
				usergroups.put(username, "");
			}
			if (! usergroups.get(username).equals("")) {
				usergroups.put(username, usergroups.get(username) + "|");
			}
			usergroups.put(username, usergroups.get(username) + usergroup);
		}

		usertypes = new HashMap<String,String>();
		SQL = "select username,usertype from users_usertypes order by username,usertype";
		HashMap<String,HashMap<String,String>> usertyperows = db.query_records(SQL);
		Iterator myusertyperows = usertyperows.keySet().iterator();
		while (myusertyperows.hasNext()) {
			String myusertyperow = "" + myusertyperows.next();
			String username = "" + ((HashMap<String,String>)usertyperows.get(myusertyperow)).get("username");
			String usertype = "" + ((HashMap<String,String>)usertyperows.get(myusertyperow)).get("usertype");
			if (usertypes.get(username) == null) {
				usertypes.put(username, "");
			}
			if (! usertypes.get(username).equals("")) {
				usertypes.put(username, usertypes.get(username) + "|");
			}
			usertypes.put(username, usertypes.get(username) + usertype);
		}

		User users = new User(text);
		SQL = "select * from users order by username, name, email, id";
		users.records("", "", "", "", "", db, myconfig, SQL);
		myresponse.setHeader("Content-Disposition", "filename=users.csv");
		myresponse.setContentType("x-application/csv");
		return users;
	}



	public void doImport(JspWriter out, ServletContext servletcontext, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
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
					if (my_line.equals("id,username,password,name,organisation,email,userclass,usertype,usergroup,usertypes,usergroups,users_type,users_group,creators_type,creators_group,editors_type,editors_group,publishers_type,publishers_group,administrators_type,administrators_group,content_editor,hardcore_language,hardcore_upload,hardcore_format,hardcore_width,hardcore_height,hardcore_onenter,hardcore_onctrlenter,hardcore_onshiftenter,hardcore_onaltenter,hardcore_toolbar1,hardcore_toolbar2,hardcore_toolbar3,hardcore_toolbar4,hardcore_toolbar5,scheduled_publish,scheduled_unpublish,card_type,card_number,card_issuedmonth,card_issuedyear,card_expirymonth,card_expiryyear,card_name,card_cvc,card_issue,card_postalcode,delivery_name,delivery_organisation,delivery_address,delivery_postalcode,delivery_city,delivery_state,delivery_country,delivery_phone,delivery_fax,delivery_email,invoice_name,invoice_organisation,invoice_address,invoice_postalcode,invoice_city,invoice_state,invoice_country,invoice_phone,invoice_fax,invoice_email,notes,keywords,description,created,updated,userinfo,menu_selection,workspace_sections,statistics_reports,index_content,index_library,index_product,index_order,index_user,index_websites,index_hosting,index_databases,index_workspace,title,gender,birthdate,birthday,birthmonth,birthyear")) {
						out.print("Importing file. Please wait.");
						while ((my_line = input.readLine()) != null) {
							String my_nextline;
							int quotes = 0;
							for (int i=0; i<my_line.length(); i++) {
								if (my_line.charAt(i) == '"') {
									quotes++;
								}
								if ((i == my_line.length()-1) && ((quotes % 2) == 1) && ((my_nextline = input.readLine()) != null)) {
									my_line += "\r\n" + my_nextline;
								}
							}
							out.print(" . ");
							out.flush();
							if (! my_line.equals("")) {
								String[] my_values = (my_line+",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,x").split(",");
								for (int i=0; i<my_values.length; i++) {
									if (my_values[i].startsWith("\"") && (my_values[i].endsWith("\""))) {
										my_values[i] = "" + my_values[i].substring(1, my_values[i].length()-1).replaceAll("\"\"", "\"");
									} else if (my_values[i].startsWith("\"") && (! my_values[i].endsWith("\""))) {
										String myvalue = "" + my_values[i];
										boolean joinvalues = true;
										int k=i+1;
										for (int j=i+1; j<my_values.length; j++) {
											if (joinvalues) {
												myvalue += "," + my_values[j];
											} else {
												my_values[k++] = "" + my_values[j];
												my_values[j] = "";
											}
											if (my_values[j].endsWith("\"")) {
												joinvalues = false;
											}
										}
										my_values[i] = "" + myvalue.substring(1, myvalue.length()-1).replaceAll("\"\"", "\"");
									}
								}
								String id = my_values[0];
								String username = my_values[1];
								String password = my_values[2];
								String name = my_values[3];
								String organisation = my_values[4];
								String email = my_values[5];
								String userclass = my_values[6];
								String usertype = my_values[7];
								String usergroup = my_values[8];
								String usertypes = my_values[9];
								String usergroups = my_values[10];
								String users_type = my_values[11];
								String users_group = my_values[12];
								String creators_type = my_values[13];
								String creators_group = my_values[14];
								String editors_type = my_values[15];
								String editors_group = my_values[16];
								String publishers_type = my_values[17];
								String publishers_group = my_values[18];
								String administrators_type = my_values[19];
								String administrators_group = my_values[20];
								String content_editor = my_values[21];
								String hardcore_language = my_values[22];
								String hardcore_upload = my_values[23];
								String hardcore_format = my_values[24];
								String hardcore_width = my_values[25];
								String hardcore_height = my_values[26];
								String hardcore_onenter = my_values[27];
								String hardcore_onctrlenter = my_values[28];
								String hardcore_onshiftenter = my_values[29];
								String hardcore_onaltenter = my_values[30];
								String hardcore_toolbar1 = my_values[31];
								String hardcore_toolbar2 = my_values[32];
								String hardcore_toolbar3 = my_values[33];
								String hardcore_toolbar4 = my_values[34];
								String hardcore_toolbar5 = my_values[35];
								String scheduled_publish = my_values[36];
								String scheduled_unpublish = my_values[37];
								String card_type = my_values[38];
								String card_number = my_values[39];
								String card_issuedmonth = my_values[40];
								String card_issuedyear = my_values[41];
								String card_expirymonth = my_values[42];
								String card_expiryyear = my_values[43];
								String card_name = my_values[44];
								String card_cvc = my_values[45];
								String card_issue = my_values[46];
								String card_postalcode = my_values[47];
								String delivery_name = my_values[48];
								String delivery_organisation = my_values[49];
								String delivery_address = my_values[50];
								String delivery_postalcode = my_values[51];
								String delivery_city = my_values[52];
								String delivery_state = my_values[53];
								String delivery_country = my_values[54];
								String delivery_phone = my_values[55];
								String delivery_fax = my_values[56];
								String delivery_email = my_values[57];
								String invoice_name = my_values[58];
								String invoice_organisation = my_values[59];
								String invoice_address = my_values[60];
								String invoice_postalcode = my_values[61];
								String invoice_city = my_values[62];
								String invoice_state = my_values[63];
								String invoice_country = my_values[64];
								String invoice_phone = my_values[65];
								String invoice_fax = my_values[66];
								String invoice_email = my_values[67];
								String notes = my_values[68];
								String keywords = my_values[69];
								String description = my_values[70];
//								String created = my_values[71];
//								String updated = my_values[72];
								String userinfo = my_values[73];
								String menu_selection = my_values[74];
								String workspace_sections = my_values[75];
								String statistics_reports = my_values[76];
								String index_content = my_values[77];
								String index_library = my_values[78];
								String index_product = my_values[79];
								String index_order = my_values[80];
								String index_user = my_values[81];
								String index_websites = my_values[82];
								String index_hosting = my_values[83];
								String index_databases = my_values[84];
								String index_workspace = my_values[85];
								String title = my_values[86];
								String gender = my_values[87];
								String birthdate = my_values[88];
								String birthday = my_values[89];
								String birthmonth = my_values[90];
								String birthyear = my_values[91];

								User user;
								user = new User(text);
								if (! id.equals("")) {
									user.read("", "", "", "", "", db, myconfig, id);
									if ((user.getId().equals(id)) && updated_user(db, user, id, username, password, name, organisation, email, userclass, usertype, usergroup, usertypes, usergroups, users_type, users_group, creators_type, creators_group, editors_type, editors_group, publishers_type, publishers_group, administrators_type, administrators_group, content_editor, hardcore_language, hardcore_upload, hardcore_format, hardcore_width, hardcore_height, hardcore_onenter, hardcore_onctrlenter, hardcore_onshiftenter, hardcore_onaltenter, hardcore_toolbar1, hardcore_toolbar2, hardcore_toolbar3, hardcore_toolbar4, hardcore_toolbar5, scheduled_publish, scheduled_unpublish, card_type, card_number, card_issuedmonth, card_issuedyear, card_expirymonth, card_expiryyear, card_name, card_cvc, card_issue, card_postalcode, delivery_name, delivery_organisation, delivery_address, delivery_postalcode, delivery_city, delivery_state, delivery_country, delivery_phone, delivery_fax, delivery_email, invoice_name, invoice_organisation, invoice_address, invoice_postalcode, invoice_city, invoice_state, invoice_country, invoice_phone, invoice_fax, invoice_email, notes, keywords, description, userinfo, menu_selection, workspace_sections, statistics_reports, index_content, index_library, index_product, index_order, index_user, index_websites, index_hosting, index_databases, index_workspace, title, gender, birthdate, birthday, birthmonth, birthyear)) {
										user.update(db);
									}
								} else {
									updated_user(db, user, id, username, password, name, organisation, email, userclass, usertype, usergroup, usertypes, usergroups, users_type, users_group, creators_type, creators_group, editors_type, editors_group, publishers_type, publishers_group, administrators_type, administrators_group, content_editor, hardcore_language, hardcore_upload, hardcore_format, hardcore_width, hardcore_height, hardcore_onenter, hardcore_onctrlenter, hardcore_onshiftenter, hardcore_onaltenter, hardcore_toolbar1, hardcore_toolbar2, hardcore_toolbar3, hardcore_toolbar4, hardcore_toolbar5, scheduled_publish, scheduled_unpublish, card_type, card_number, card_issuedmonth, card_issuedyear, card_expirymonth, card_expiryyear, card_name, card_cvc, card_issue, card_postalcode, delivery_name, delivery_organisation, delivery_address, delivery_postalcode, delivery_city, delivery_state, delivery_country, delivery_phone, delivery_fax, delivery_email, invoice_name, invoice_organisation, invoice_address, invoice_postalcode, invoice_city, invoice_state, invoice_country, invoice_phone, invoice_fax, invoice_email, notes, keywords, description, userinfo, menu_selection, workspace_sections, statistics_reports, index_content, index_library, index_product, index_order, index_user, index_websites, index_hosting, index_databases, index_workspace, title, gender, birthdate, birthday, birthmonth, birthyear);
									user.create(db);
								}
							}
						}
						out.print(text.display("error.users.import.done"));
					} else {
						out.print(text.display("error.users.import.format"));
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
	}



	private boolean updated_user(DB db, User user, String id, String username, String password, String name, String organisation, String email, String userclass, String usertype, String usergroup, String usertypes, String usergroups, String users_type, String users_group, String creators_type, String creators_group, String editors_type, String editors_group, String publishers_type, String publishers_group, String administrators_type, String administrators_group, String content_editor, String hardcore_language, String hardcore_upload, String hardcore_format, String hardcore_width, String hardcore_height, String hardcore_onenter, String hardcore_onctrlenter, String hardcore_onshiftenter, String hardcore_onaltenter, String hardcore_toolbar1, String hardcore_toolbar2, String hardcore_toolbar3, String hardcore_toolbar4, String hardcore_toolbar5, String scheduled_publish, String scheduled_unpublish, String card_type, String card_number, String card_issuedmonth, String card_issuedyear, String card_expirymonth, String card_expiryyear, String card_name, String card_cvc, String card_issue, String card_postalcode, String delivery_name, String delivery_organisation, String delivery_address, String delivery_postalcode, String delivery_city, String delivery_state, String delivery_country, String delivery_phone, String delivery_fax, String delivery_email, String invoice_name, String invoice_organisation, String invoice_address, String invoice_postalcode, String invoice_city, String invoice_state, String invoice_country, String invoice_phone, String invoice_fax, String invoice_email, String notes, String keywords, String description, String userinfo, String menu_selection, String workspace_sections, String statistics_reports, String index_content, String index_library, String index_product, String index_order, String index_user, String index_websites, String index_hosting, String index_databases, String index_workspace, String title, String gender, String birthdate, String birthday, String birthmonth, String birthyear) {
		boolean updated = false;
		if (! user.getUsername().equals(username)) {
			user.setUsername(username);
			updated = true;
		}
		if (! user.getPassword().equals(password)) {
			user.setPassword(password);
			updated = true;
		}
		if (! user.getName().equals(name)) {
			user.setName(name);
			updated = true;
		}
		if (! user.getOrganisation().equals(organisation)) {
			user.setOrganisation(organisation);
			updated = true;
		}
		if (! user.getEmail().equals(email)) {
			user.setEmail(email);
			updated = true;
		}
		if (! user.getUserclass().equals(userclass)) {
			user.setUserclass(userclass);
			updated = true;
		}
		if (! user.getUsertype().equals(usertype)) {
			user.setUsertype(usertype);
			updated = true;
		}
		if (! user.getUsergroup().equals(usergroup)) {
			user.setUsergroup(usergroup);
			updated = true;
		}
		if (! Common.join("|",(String[])user.additionalusertypes(db,null).keySet().toArray(new String[0])).equals(usertypes)) {
			updated = true;
		}
		HashMap<String,String> additional_usertypes = new HashMap<String,String>();
		String[] myusertypes = usertypes.split("\\|");
		for (int myusertype=0; myusertype<myusertypes.length; myusertype++) {
			additional_usertypes.put(myusertypes[myusertype], myusertypes[myusertype]);
		}
		user.setAdditionalUsertypes(additional_usertypes);
		if (! Common.join("|",(String[])user.additionalusergroups(db,null).keySet().toArray(new String[0])).equals(usergroups)) {
			updated = true;
		}
		HashMap<String,String> additional_usergroups = new HashMap<String,String>();
		String[] myusergroups = usergroups.split("\\|");
		for (int myusergroup=0; myusergroup<myusergroups.length; myusergroup++) {
			additional_usergroups.put(myusergroups[myusergroup], myusergroups[myusergroup]);
		}
		user.setAdditionalUsergroups(additional_usergroups);
		if (! user.getUsersType().equals(users_type)) {
			user.setUsersType(users_type);
			updated = true;
		}
		if (! user.getUsersGroup().equals(users_group)) {
			user.setUsersGroup(users_group);
			updated = true;
		}
		if (! user.getCreatorsType().equals(creators_type)) {
			user.setCreatorsType(creators_type);
			updated = true;
		}
		if (! user.getCreatorsGroup().equals(creators_group)) {
			user.setCreatorsGroup(creators_group);
			updated = true;
		}
		if (! user.getEditorsType().equals(editors_type)) {
			user.setEditorsType(editors_type);
			updated = true;
		}
		if (! user.getEditorsGroup().equals(editors_group)) {
			user.setEditorsGroup(editors_group);
			updated = true;
		}
		if (! user.getPublishersType().equals(publishers_type)) {
			user.setPublishersType(publishers_type);
			updated = true;
		}
		if (! user.getPublishersGroup().equals(publishers_group)) {
			user.setPublishersGroup(publishers_group);
			updated = true;
		}
		if (! user.getAdministratorsType().equals(administrators_type)) {
			user.setAdministratorsType(administrators_type);
			updated = true;
		}
		if (! user.getAdministratorsGroup().equals(administrators_group)) {
			user.setAdministratorsGroup(administrators_group);
			updated = true;
		}
		if (! user.getContentEditor().equals(content_editor)) {
			user.setContentEditor(content_editor);
			updated = true;
		}
		if (! user.getHardcoreLanguage().equals(hardcore_language)) {
			user.setHardcoreLanguage(hardcore_language);
			updated = true;
		}
		if (! user.getHardcoreUpload().equals(hardcore_upload)) {
			user.setHardcoreUpload(hardcore_upload);
			updated = true;
		}
		if (! user.getHardcoreFormat().equals(hardcore_format)) {
			user.setHardcoreFormat(hardcore_format);
			updated = true;
		}
		if (! user.getHardcoreWidth().equals(hardcore_width)) {
			user.setHardcoreWidth(hardcore_width);
			updated = true;
		}
		if (! user.getHardcoreHeight().equals(hardcore_height)) {
			user.setHardcoreHeight(hardcore_height);
			updated = true;
		}
		if (! user.getHardcoreOnEnter().equals(hardcore_onenter)) {
			user.setHardcoreOnEnter(hardcore_onenter);
			updated = true;
		}
		if (! user.getHardcoreOnCtrlEnter().equals(hardcore_onctrlenter)) {
			user.setHardcoreOnCtrlEnter(hardcore_onctrlenter);
			updated = true;
		}
		if (! user.getHardcoreOnShiftEnter().equals(hardcore_onshiftenter)) {
			user.setHardcoreOnShiftEnter(hardcore_onshiftenter);
			updated = true;
		}
		if (! user.getHardcoreOnAltEnter().equals(hardcore_onaltenter)) {
			user.setHardcoreOnAltEnter(hardcore_onaltenter);
			updated = true;
		}
		if (! user.getHardcoreToolbar1().equals(hardcore_toolbar1)) {
			user.setHardcoreToolbar1(hardcore_toolbar1);
			updated = true;
		}
		if (! user.getHardcoreToolbar2().equals(hardcore_toolbar2)) {
			user.setHardcoreToolbar2(hardcore_toolbar2);
			updated = true;
		}
		if (! user.getHardcoreToolbar3().equals(hardcore_toolbar3)) {
			user.setHardcoreToolbar3(hardcore_toolbar3);
			updated = true;
		}
		if (! user.getHardcoreToolbar4().equals(hardcore_toolbar4)) {
			user.setHardcoreToolbar4(hardcore_toolbar4);
			updated = true;
		}
		if (! user.getHardcoreToolbar5().equals(hardcore_toolbar5)) {
			user.setHardcoreToolbar5(hardcore_toolbar5);
			updated = true;
		}
		if (! user.getScheduledPublish().equals(scheduled_publish)) {
			user.setScheduledPublish(scheduled_publish);
			updated = true;
		}
		if (! user.getScheduledUnpublish().equals(scheduled_unpublish)) {
			user.setScheduledUnpublish(scheduled_unpublish);
			updated = true;
		}
		if (! user.getCardType().equals(card_type)) {
			user.setCardType(card_type);
			updated = true;
		}
		if (! user.getCardNumber().equals(card_number)) {
			user.setCardNumber(card_number);
			updated = true;
		}
		if (! user.getCardIssuedMonth().equals(card_issuedmonth)) {
			user.setCardIssuedMonth(card_issuedmonth);
			updated = true;
		}
		if (! user.getCardIssuedYear().equals(card_issuedyear)) {
			user.setCardIssuedYear(card_issuedyear);
			updated = true;
		}
		if (! user.getCardExpiryMonth().equals(card_expirymonth)) {
			user.setCardExpiryMonth(card_expirymonth);
			updated = true;
		}
		if (! user.getCardExpiryYear().equals(card_expiryyear)) {
			user.setCardExpiryYear(card_expiryyear);
			updated = true;
		}
		if (! user.getCardName().equals(card_name)) {
			user.setCardName(card_name);
			updated = true;
		}
		if (! user.getCardCVC().equals(card_cvc)) {
			user.setCardCVC(card_cvc);
			updated = true;
		}
		if (! user.getCardIssue().equals(card_issue)) {
			user.setCardIssue(card_issue);
			updated = true;
		}
		if (! user.getCardPostalcode().equals(card_postalcode)) {
			user.setCardPostalcode(card_postalcode);
			updated = true;
		}
		if (! user.getDeliveryName().equals(delivery_name)) {
			user.setDeliveryName(delivery_name);
			updated = true;
		}
		if (! user.getDeliveryOrganisation().equals(delivery_organisation)) {
			user.setDeliveryOrganisation(delivery_organisation);
			updated = true;
		}
		if (! user.getDeliveryAddress().equals(delivery_address)) {
			user.setDeliveryAddress(delivery_address);
			updated = true;
		}
		if (! user.getDeliveryPostalcode().equals(delivery_postalcode)) {
			user.setDeliveryPostalcode(delivery_postalcode);
			updated = true;
		}
		if (! user.getDeliveryCity().equals(delivery_city)) {
			user.setDeliveryCity(delivery_city);
			updated = true;
		}
		if (! user.getDeliveryState().equals(delivery_state)) {
			user.setDeliveryState(delivery_state);
			updated = true;
		}
		if (! user.getDeliveryCountry().equals(delivery_country)) {
			user.setDeliveryCountry(delivery_country);
			updated = true;
		}
		if (! user.getDeliveryPhone().equals(delivery_phone)) {
			user.setDeliveryPhone(delivery_phone);
			updated = true;
		}
		if (! user.getDeliveryFax().equals(delivery_fax)) {
			user.setDeliveryFax(delivery_fax);
			updated = true;
		}
		if (! user.getDeliveryEmail().equals(delivery_email)) {
			user.setDeliveryEmail(delivery_email);
			updated = true;
		}
		if (! user.getInvoiceName().equals(invoice_name)) {
			user.setInvoiceName(invoice_name);
			updated = true;
		}
		if (! user.getInvoiceOrganisation().equals(invoice_organisation)) {
			user.setInvoiceOrganisation(invoice_organisation);
			updated = true;
		}
		if (! user.getInvoiceAddress().equals(invoice_address)) {
			user.setInvoiceAddress(invoice_address);
			updated = true;
		}
		if (! user.getInvoicePostalcode().equals(invoice_postalcode)) {
			user.setInvoicePostalcode(invoice_postalcode);
			updated = true;
		}
		if (! user.getInvoiceCity().equals(invoice_city)) {
			user.setInvoiceCity(invoice_city);
			updated = true;
		}
		if (! user.getInvoiceState().equals(invoice_state)) {
			user.setInvoiceState(invoice_state);
			updated = true;
		}
		if (! user.getInvoiceCountry().equals(invoice_country)) {
			user.setInvoiceCountry(invoice_country);
			updated = true;
		}
		if (! user.getInvoicePhone().equals(invoice_phone)) {
			user.setInvoicePhone(invoice_phone);
			updated = true;
		}
		if (! user.getInvoiceFax().equals(invoice_fax)) {
			user.setInvoiceFax(invoice_fax);
			updated = true;
		}
		if (! user.getInvoiceEmail().equals(invoice_email)) {
			user.setInvoiceEmail(invoice_email);
			updated = true;
		}
		if (! user.getNotes().equals(notes)) {
			user.setNotes(notes);
			updated = true;
		}
		if (! user.getKeywords().equals(keywords)) {
			user.setKeywords(keywords);
			updated = true;
		}
		if (! user.getDescription().equals(description)) {
			user.setDescription(description);
			updated = true;
		}
		if (! user.getUserinfo().equals(userinfo)) {
			user.setUserinfo(userinfo);
			updated = true;
		}

		if (! user.getMenuSelection().equals(menu_selection)) {
			user.setMenuSelection(menu_selection);
			updated = true;
		}
		if (! user.getWorkspaceSections().equals(workspace_sections)) {
			user.setWorkspaceSections(workspace_sections);
			updated = true;
		}
		if (! user.getStatisticsReports().equals(statistics_reports)) {
			user.setStatisticsReports(statistics_reports);
			updated = true;
		}
		if (! user.getIndexContent().equals(index_content)) {
			user.setIndexContent(index_content);
			updated = true;
		}
		if (! user.getIndexLibrary().equals(index_library)) {
			user.setIndexLibrary(index_library);
			updated = true;
		}
		if (! user.getIndexProduct().equals(index_product)) {
			user.setIndexProduct(index_product);
			updated = true;
		}
		if (! user.getIndexOrder().equals(index_order)) {
			user.setIndexOrder(index_order);
			updated = true;
		}
		if (! user.getIndexUser().equals(index_user)) {
			user.setIndexUser(index_user);
			updated = true;
		}
		if (! user.getIndexWebsites().equals(index_websites)) {
			user.setIndexWebsites(index_websites);
			updated = true;
		}
		if (! user.getIndexHosting().equals(index_hosting)) {
			user.setIndexHosting(index_hosting);
			updated = true;
		}
		if (! user.getIndexDatabases().equals(index_databases)) {
			user.setIndexDatabases(index_databases);
			updated = true;
		}
		if (! user.getIndexWorkspace().equals(index_workspace)) {
			user.setIndexWorkspace(index_workspace);
			updated = true;
		}
		if (! user.getTitle().equals(title)) {
			user.setTitle(title);
			updated = true;
		}
		if (! user.getGender().equals(gender)) {
			user.setGender(gender);
			updated = true;
		}
		if (! user.getBirthDate().equals(birthdate)) {
			user.setBirthDate(birthdate);
			updated = true;
		}
		if (! user.getBirthDay().equals(birthday)) {
			user.setBirthDay(birthday);
			updated = true;
		}
		if (! user.getBirthMonth().equals(birthmonth)) {
			user.setBirthMonth(birthmonth);
			updated = true;
		}
		if (! user.getBirthYear().equals(birthyear)) {
			user.setBirthYear(birthyear);
			updated = true;
		}
		return updated;
	}



}
