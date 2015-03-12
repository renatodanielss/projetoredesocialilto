package HardCore;

import java.io.*;
import java.io.File;
import java.nio.*;
import java.nio.channels.*;
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.*;
import javax.servlet.*;
import javax.servlet.jsp.*;

public class UCmaintainContent {
	private String error = "";
	public Fileupload fileupload = null;
	private Content create_records = new Content(null);
	private Content created_records = new Content(null);
	private Content expired_records = new Content(null);
	private Content updated_records = new Content(null);
	private Content checkedout_records = new Content(null);
	private Content workflow_records = new Content(null);
	private int record_count = 0;
	private Text text = new Text();



	public UCmaintainContent() {
	}



	public UCmaintainContent(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public void getAccess(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		if (db == null) return;
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
	}



	public void getIndexPersonal(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		if (db == null) return;
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		mysession.set("admin_contentpackage", "");
		mysession.set("admin_contentclass", "");
		mysession.set("admin_contentbundle", "");
		mysession.set("admin_contentgroup", "");
		mysession.set("admin_contenttype", "");
		mysession.set("admin_version", "");
		mysession.set("admin_status", "");
		mysession.set("admin_stock", "");
		String SQLwhere = "";
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		SQLwhere = SQLwhere_hide(mysession, myconfig, db, SQLwhere);

		String isUsersType = "((" + db.is_not_blank("users_type") + " and users_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (users_type=" + db.quote("*") + ") or (users_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isUsersGroup = "((" + db.is_not_blank("users_group") + " and users_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (users_group=" + db.quote("*") + ") or (users_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isDevelopersType = "((" + db.is_not_blank("developers_type") + " and developers_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (developers_type=" + db.quote("*") + ") or (developers_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isDevelopersGroup = "((" + db.is_not_blank("developers_group") + " and developers_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (developers_group=" + db.quote("*") + ") or (developers_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isCreatorsType = "((" + db.is_not_blank("creators_type") + " and creators_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (creators_type=" + db.quote("*") + ") or (creators_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isCreatorsGroup = "((" + db.is_not_blank("creators_group") + " and creators_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (creators_group=" + db.quote("*") + ") or (creators_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isEditorsType = "((" + db.is_not_blank("editors_type") + " and editors_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (editors_type=" + db.quote("*") + ") or (editors_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isEditorsGroup = "((" + db.is_not_blank("editors_group") + " and editors_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (editors_group=" + db.quote("*") + ") or (editors_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isPublishersType = "((" + db.is_not_blank("publishers_type") + " and publishers_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (publishers_type=" + db.quote("*") + ") or (publishers_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isPublishersGroup = "((" + db.is_not_blank("publishers_group") + " and publishers_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (publishers_group=" + db.quote("*") + ") or (publishers_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isAdministratorsType = "((" + db.is_not_blank("administrators_type") + " and administrators_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (administrators_type=" + db.quote("*") + ") or (administrators_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isAdministratorsGroup = "((" + db.is_not_blank("administrators_group") + " and administrators_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (administrators_group=" + db.quote("*") + ") or (administrators_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String noUsersType = "" + db.is_blank("users_type") + "";
		String noUsersGroup = "" + db.is_blank("users_group") + "";
		String noDevelopersType = "" + db.is_blank("developers_type") + "";
		String noDevelopersGroup = "" + db.is_blank("developers_group") + "";
		String noCreatorsType = "" + db.is_blank("creators_type") + "";
		String noCreatorsGroup = "" + db.is_blank("creators_group") + "";
		String noEditorsType = "" + db.is_blank("editors_type") + "";
		String noEditorsGroup = "" + db.is_blank("editors_group") + "";
		String noPublishersType = "" + db.is_blank("publishers_type") + "";
		String noPublishersGroup = "" + db.is_blank("publishers_group") + "";
		String noAdministratorsType = "" + db.is_blank("administrators_type") + "";
		String noAdministratorsGroup = "" + db.is_blank("administrators_group") + "";

		String isUser = "";
		String isDeveloper = "";
		String isCreator = "";
		String isEditor = "";
		String isPublisher = "";
		String isAdministrator = "";
		if (myconfig.get(db, "accessrestrictions").equals("or")) {
			isUser = "((" + noUsersType + " and " + noUsersGroup + ") or (" + isUsersGroup + " or " + isUsersType + "))";
			isDeveloper = "((" + noDevelopersType + " and " + noDevelopersGroup + ") or (" + isDevelopersGroup + " or " + isDevelopersType + "))";
			isCreator = "((" + noCreatorsType + " and " + noCreatorsGroup + ") or (" + isCreatorsGroup + " or " + isCreatorsType + "))";
			isEditor = "((" + noEditorsType + " and " + noEditorsGroup + ") or (" + isEditorsGroup + " or " + isEditorsType + "))";
			isPublisher = "((" + noPublishersType + " and " + noPublishersGroup + ") or (" + isPublishersGroup + " or " + isPublishersType + "))";
			isAdministrator = "((" + noAdministratorsType + " and " + noAdministratorsGroup + ") or (" + isAdministratorsGroup + " or " + isAdministratorsType + "))";
		} else {
			isUser = "((" + noUsersType + " and " + noUsersGroup + ") or (" + noUsersType + " and " + isUsersGroup + ") or (" + isUsersType + " and " + noUsersGroup + ") or (" + isUsersGroup + " and " + isUsersType + "))";
			isDeveloper = "((" + noDevelopersType + " and " + noDevelopersGroup + ") or (" + noDevelopersType + " and " + isDevelopersGroup + ") or (" + isDevelopersType + " and " + noDevelopersGroup + ") or (" + isDevelopersGroup + " and " + isDevelopersType + "))";
			isCreator = "((" + noCreatorsType + " and " + noCreatorsGroup + ") or (" + noCreatorsType + " and " + isCreatorsGroup + ") or (" + isCreatorsType + " and " + noCreatorsGroup + ") or (" + isCreatorsGroup + " and " + isCreatorsType + "))";
			isEditor = "((" + noEditorsType + " and " + noEditorsGroup + ") or (" + noEditorsType + " and " + isEditorsGroup + ") or (" + isEditorsType + " and " + noEditorsGroup + ") or (" + isEditorsGroup + " and " + isEditorsType + "))";
			isPublisher = "((" + noPublishersType + " and " + noPublishersGroup + ") or (" + noPublishersType + " and " + isPublishersGroup + ") or (" + isPublishersType + " and " + noPublishersGroup + ") or (" + isPublishersGroup + " and " + isPublishersType + "))";
			isAdministrator = "((" + noAdministratorsType + " and " + noAdministratorsGroup + ") or (" + noAdministratorsType + " and " + isAdministratorsGroup + ") or (" + isAdministratorsType + " and " + noAdministratorsGroup + ") or (" + isAdministratorsGroup + " and " + isAdministratorsType + "))";
		}
		String isSuperadmin = "";
		if (mysession.get("username").equals(myconfig.get(db, "superadmin"))) {
			isSuperadmin = "(created_by=" + db.quote(myconfig.get(db, "superadmin")) + ")";
		} else {
			isSuperadmin = "(1=0)";
		}

		String SQLwhereCreated = "" + SQLwhere;
		SQLwhereCreated = Common.SQLwhere(SQLwhereCreated, "((" + db.is_blank("published") + ") and (" + db.is_blank("unpublished") + ") and (" + db.is_blank("scheduled_unpublish") + "))");
		SQLwhereCreated = Common.SQLwhere(SQLwhereCreated, "(" + isCreator + " or " + isEditor + " or " + isDeveloper + " or " + isPublisher + " or " + isAdministrator + " or " + isSuperadmin + " or (" + db.is_not_blank("status") + ")" + ")");

		String SQLwhereExpired = "" + SQLwhere;
		SQLwhereExpired = Common.SQLwhere(SQLwhereExpired, "((" + db.is_blank("published") + ") and ((" + db.is_not_blank("scheduled_unpublish") + ") and (scheduled_unpublish < " + db.quote(now) + ")))");
		SQLwhereExpired = Common.SQLwhere(SQLwhereExpired, "(" + isCreator + " or " + isEditor + " or " + isDeveloper + " or " + isPublisher + " or " + isAdministrator + " or " + isSuperadmin + " or (" + db.is_not_blank("status") + ")" + ")");

		String SQLwhereUpdated = "" + SQLwhere;
		SQLwhereUpdated = Common.SQLwhere(SQLwhereUpdated, "(published < updated)");
		SQLwhereUpdated = Common.SQLwhere(SQLwhereUpdated, "(" + db.is_not_blank("published") + ")");
		SQLwhereUpdated = Common.SQLwhere(SQLwhereUpdated, "(" + isCreator + " or " + isEditor + " or " + isDeveloper + " or " + isPublisher + " or " + isAdministrator + " or " + isSuperadmin + " or (" + db.is_not_blank("status") + ")" + ")");

		String SQLwhereCheckedout = "" + SQLwhere;
		SQLwhereCheckedout = Common.SQLwhere(SQLwhereCheckedout, "((checkedout = " + db.quote(mysession.get("username")) + ") or ((checkedout = " + db.quote("-creators-") + ") and (" + isCreator + " or " + isAdministrator + ")) or ((checkedout = " + db.quote("-editors-") + ") and (" + isCreator + " or " + isEditor + " or " + isDeveloper + " or " + isPublisher + " or " + isAdministrator + ")) or ((checkedout = " + db.quote("-developers-") + ") and (" + isDeveloper + " or " + isAdministrator + ")) or ((checkedout = " + db.quote("-publishers-") + ") and (" + isPublisher + " or " + isAdministrator + ")) or ((checkedout = " + db.quote("-administrators-") + ") and (" + isAdministrator + ")))");

		String SQLwhereWorkflow = "" + SQLwhere;
		SQLwhereWorkflow = Common.SQLwhere(SQLwhereWorkflow, "(" + db.is_not_blank("status") + ")");
		SQLwhereWorkflow = Common.SQLwhere(SQLwhereWorkflow, "(" + isUser + " or " + isCreator + " or " + isEditor + " or " + isDeveloper + " or " + isPublisher + " or " + isAdministrator + " or " + isSuperadmin + ")");

		String SQLcolumns = "*";
//		String SQLcolumns = "id,locked,locked_user,locked_creator,locked_developer,locked_editor,locked_publisher,locked_administrator,locked_schedule,locked_unschedule,created,updated,published,unpublished,scheduled_publish,scheduled_unpublish,requested_publish,requested_unpublish,status,status_by,created_by,updated_by,published_by,unpublished_by,revision,history,version,version_master,title,searchable,menuitem,author,keywords,description,metainfo,htmlheader,htmlbodyonload,url,template,stylesheet,scripts,image1,image2,image3,file1,file2,file3,link1,link2,link3,contentformat,contentclass,contenttype,contentgroup,contentbundle,contentpackage,users_type,users_group,developers_type,developers_group,creators_type,creators_group,editors_type,editors_group,publishers_type,publishers_group,administrators_type,administrators_group,users_users,developers_users,creators_users,editors_users,publishers_users,administrators_users,checkedout,page_top,page_up,page_next,page_previous,page_first,page_last,related,upload_filename,server_filename,product_code,product_sku,product_currency,product_price,product_period,product_weight,product_volume,product_width,product_height,product_depth,product_brand,product_colour,product_size,product_count,product_tax,product_points_cost,product_points_reward,product_stock,product_cost,product_location,product_stock_amount,product_stock_text,product_restocked_amount,product_lowstock_amount,product_lowstock_text,product_nostock_order,product_nostock_text,product_comment,product_email,product_url,product_info,product_delivery,product_user,product_page,product_program,product_hosting,product_options,product_content,product_contentgroup,product_contenttype,product_imagegroup,product_imagetype,product_filegroup,product_filetype,product_linkgroup,product_linktype,product_productgroup,product_producttype,product_usergroup,product_usertype,custom,futurechar1,futurechar2,futurechar3,futuretext1,futuretext2,futuretext3,futureint1,futureint2,futureint3";
		String SQL = "";
		if (myrequest.getParameter("section").equals("created")) {
			SQL = "select " + SQLcolumns + " from content " + SQLwhereCreated + " order by title, version, id";
		} else {
			SQL = "select * from content where 1=0";
		}
		created_records = new Content(text);
		created_records.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
		if (myrequest.getParameter("section").equals("expired")) {
			SQL = "select " + SQLcolumns + " from content " + SQLwhereExpired + " order by title, version, id";
		} else {
			SQL = "select * from content where 1=0";
		}
		expired_records = new Content(text);
		expired_records.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
		if (myrequest.getParameter("section").equals("updated")) {
			SQL = "select " + SQLcolumns + " from content " + SQLwhereUpdated + " order by title, version, id";
		} else {
			SQL = "select * from content where 1=0";
		}
		updated_records = new Content(text);
		updated_records.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
		if (myrequest.getParameter("section").equals("checkedout")) {
			SQL = "select " + SQLcolumns + " from content " + SQLwhereCheckedout + " order by title, version, id";
		} else {
			SQL = "select * from content where 1=0";
		}
		checkedout_records = new Content(text);
		checkedout_records.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
		if (myrequest.getParameter("section").equals("workflow")) {
			SQL = "select " + SQLcolumns + " from content " + SQLwhereWorkflow + " order by title, version, id";
		} else {
			SQL = "select * from content where 1=0";
		}
		workflow_records = new Content(text);
		workflow_records.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
	}



	public Content getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		return getIndex(mysession, myrequest, myresponse, myconfig, db, true, true);
	}
	public Content getIndex(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, boolean edits, boolean creates) {
		if (db == null) return new Content(text);
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Content(text);
		if (((! myrequest.getParameter("segment").equals("")) && (! myrequest.getParameter("segment").equals(" "))) || (! mysession.get("admin_segment").equals(""))) {
			accesspermission = RequireUser.ExperienceAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
			if (! accesspermission) return new Content(text);
		}
		if (((! myrequest.getParameter("usertest").equals("")) && (! myrequest.getParameter("usertest").equals(" "))) || (! mysession.get("admin_usertest").equals(""))) {
			accesspermission = RequireUser.ExperienceAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
			if (! accesspermission) return new Content(text);
		}
		if (((! myrequest.getParameter("heatmap").equals("")) && (! myrequest.getParameter("heatmap").equals(" "))) || (! mysession.get("admin_heatmap").equals(""))) {
			accesspermission = RequireUser.ExperienceAdministrator(text, mysession, myrequest, myresponse, myconfig, db);
			if (! accesspermission) return new Content(text);
		}
		setSessionFilterFromRequest(mysession, myrequest);
		if (myrequest.getParameter("pagesize").equals(" ")) {
			mysession.set("admin_pagesize", "");
		} else if (! myrequest.getParameter("pagesize").equals("")) {
			mysession.set("admin_pagesize", html.encodeHtmlEntities(myrequest.getParameter("pagesize")));
		}
		String SQLwhere = "";
		if (mysession.get("admin_contentpackage").equals("-")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentpackage", "");
		} else if (! mysession.get("admin_contentpackage").equals("")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentpackage", "" + mysession.get("admin_contentpackage"));
		}
		if (mysession.get("admin_contentclass").equals("element")) {
//			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "page");
//			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "template");
//			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "stylesheet");
//			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "script");
//			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "image");
//			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "file");
//			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "link");
//			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "product");
			String elements = Element.csv(db);
			if (! elements.equals("")) {
				elements = "element," + elements;
			} else {
				elements = "element";
			}
			SQLwhere = Common.SQLwhere_in(SQLwhere, "contentclass", "" + mysession.get("admin_contentclass").replaceAll("element",elements));
		} else if (mysession.get("admin_contentclass").equals("page,product")) {
			SQLwhere = Common.SQLwhere_equals_or(SQLwhere, "contentclass", "page", "contentclass", "product");
		} else if (! mysession.get("admin_contentclass").equals("")) {
			String elements = Element.csv(db);
			if (! elements.equals("")) {
				elements = "element," + elements;
			} else {
				elements = "element";
			}
			SQLwhere = Common.SQLwhere_in(SQLwhere, "contentclass", "" + mysession.get("admin_contentclass").replaceAll("element", elements));
		}
		if (mysession.get("admin_contenttype").equals("-")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contenttype", "");
		} else if (! mysession.get("admin_contenttype").equals("")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contenttype", "" + mysession.get("admin_contenttype"));
		}
		if (mysession.get("admin_contentgroup").equals("-")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentgroup", "");
		} else if (! mysession.get("admin_contentgroup").equals("")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentgroup", "" + mysession.get("admin_contentgroup"));
		}
		if (mysession.get("admin_contentbundle").equals("-")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentbundle", "");
		} else if (! mysession.get("admin_contentbundle").equals("")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentbundle", "" + mysession.get("admin_contentbundle"));
		}
		if (mysession.get("admin_version").equals("-")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "version", "");
		} else if (! mysession.get("admin_version").equals("")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "version", "" + mysession.get("admin_version"));
		}
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		if (mysession.get("admin_status").equals("new")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "((" + db.is_blank("published") + ") and (" + db.is_blank("unpublished") + ") and ((" + db.is_blank("scheduled_unpublish") + ") or (scheduled_unpublish > " + db.quote(now) + ")))");
		} else if (mysession.get("admin_status").equals("updated")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "((updated > published) and " + db.is_not_blank("published") + ")");
		} else if (mysession.get("admin_status").equals("scheduled")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "((" + db.is_not_blank("scheduled_publish") + " and (scheduled_publish > " + db.quote(now) + ")) or (" + db.is_not_blank("scheduled_unpublish") + " and (scheduled_unpublish > " + db.quote(now) + ")))");
		} else if (mysession.get("admin_status").equals("published")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "((published >= updated) and ((" + db.is_blank("scheduled_unpublish") + ") or (scheduled_unpublish > " + db.quote(now) + ")))");
		} else if (mysession.get("admin_status").equals("unpublished")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "(((" + db.is_blank("published") + ") and (" + db.is_not_blank("unpublished") + ")) or ((" + db.is_not_blank("published") + ") and (" + db.is_not_blank("unpublished") + ") and (published < unpublished)))");
		} else if (mysession.get("admin_status").equals("expiring")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "(" + db.is_not_blank("scheduled_unpublish") + " and ((scheduled_unpublish > published) or (" + db.is_blank("published") + ")) and (scheduled_unpublish > " + db.quote(now) + "))");
		} else if (mysession.get("admin_status").equals("expired")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "(" + db.is_not_blank("scheduled_unpublish") + " and ((scheduled_unpublish > published) or (" + db.is_blank("published") + ")) and (scheduled_unpublish <= " + db.quote(now) + "))");
		} else if (mysession.get("admin_status").equals("checkedout")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "(" + db.is_not_blank("checkedout") + ")");
		} else if (mysession.get("admin_status").equals("-")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "status", "");
		} else if ((! mysession.get("admin_status").equals("")) && (! mysession.get("admin_status").equals(" "))) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "status", mysession.get("admin_status"));
		}
		SQLwhere = SQLwhere_hide(mysession, myconfig, db, SQLwhere);

// IF (product_nostock_order = "")
		if (mysession.get("admin_stock").equals("unlimited")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "product_nostock_order", "");
// IF (product_nostock_order <> "")
//   IF product_stock_amount > 0
//     IF product_stock_amount > product_lowstock_amount
		} else if (mysession.get("admin_stock").equals("in")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "((product_stock_amount > 0) and (product_stock_amount > product_lowstock_amount) and " + Common.SQLwhere_not_equals(db, "-", "product_nostock_order", "") + ")");
//     IF product_stock_amount <= product_lowstock_amount
		} else if (mysession.get("admin_stock").equals("low")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "((product_stock_amount > 0) and (product_stock_amount <= product_lowstock_amount) and " + Common.SQLwhere_not_equals(db, "-", "product_nostock_order", "") + ")");
//   IF product_stock_amount = 0
//     IF product_nostock_order = "no"
		} else if (mysession.get("admin_stock").equals("no")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "((product_stock_amount = 0) and " + Common.SQLwhere_equals(db, "-", "product_nostock_order", "no") + ")");
//     IF product_nostock_order = "yes"
		} else if (mysession.get("admin_stock").equals("orderable")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "((product_stock_amount = 0) and " + Common.SQLwhere_equals(db, "-", "product_nostock_order", "yes") + ")");
//   IF product_stock_amount < 0
		} else if (mysession.get("admin_stock").equals("ordered")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "((product_stock_amount < 0))");
		}

		if (! mysession.get("admin_segment").equals("")) {
			Segment mysegment = new Segment();
			mysegment.readSegmentId(db, mysession.get("admin_segment"));
			HashMap<String,String> myids = mysegment.content_ids(db);
			SQLwhere = Common.SQLwhere(SQLwhere, "id in (" + Common.SQL_list_values(myids) + ")");
		}
		if (! mysession.get("admin_usertest").equals("")) {
			Usertest myusertest = new Usertest();
			myusertest.readUsertest(db, mysession.get("admin_usertest"));
			HashMap<String,String> myids = myusertest.content_ids(db);
			SQLwhere = Common.SQLwhere(SQLwhere, "id in (" + Common.SQL_list_values(myids) + ")");
		}
		if (! mysession.get("admin_heatmap").equals("")) {
			HashMap<String,String> myids = db.query_values("select distinct id from heatmaps");
			SQLwhere = Common.SQLwhere(SQLwhere, db.is_not_blank("heatmap") + " or id in (" + Common.SQL_list_values(myids.values()) + ")");
		}

		String isUsersType = "((" + db.is_not_blank("users_type") + " and users_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (users_type=" + db.quote("*") + ") or (users_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isUsersGroup = "((" + db.is_not_blank("users_group") + " and users_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (users_group=" + db.quote("*") + ") or (users_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isDevelopersType = "((" + db.is_not_blank("developers_type") + " and developers_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (developers_type=" + db.quote("*") + ") or (developers_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isDevelopersGroup = "((" + db.is_not_blank("developers_group") + " and developers_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (developers_group=" + db.quote("*") + ") or (developers_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isCreatorsType = "((" + db.is_not_blank("creators_type") + " and creators_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (creators_type=" + db.quote("*") + ") or (creators_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isCreatorsGroup = "((" + db.is_not_blank("creators_group") + " and creators_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (creators_group=" + db.quote("*") + ") or (creators_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isEditorsType = "((" + db.is_not_blank("editors_type") + " and editors_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (editors_type=" + db.quote("*") + ") or (editors_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isEditorsGroup = "((" + db.is_not_blank("editors_group") + " and editors_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (editors_group=" + db.quote("*") + ") or (editors_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isPublishersType = "((" + db.is_not_blank("publishers_type") + " and publishers_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (publishers_type=" + db.quote("*") + ") or (publishers_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isPublishersGroup = "((" + db.is_not_blank("publishers_group") + " and publishers_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (publishers_group=" + db.quote("*") + ") or (publishers_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isAdministratorsType = "((" + db.is_not_blank("administrators_type") + " and administrators_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (administrators_type=" + db.quote("*") + ") or (administrators_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isAdministratorsGroup = "((" + db.is_not_blank("administrators_group") + " and administrators_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (administrators_group=" + db.quote("*") + ") or (administrators_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String noUsersType = "" + db.is_blank("users_type") + "";
		String noUsersGroup = "" + db.is_blank("users_group") + "";
		String noDevelopersType = "" + db.is_blank("developers_type") + "";
		String noDevelopersGroup = "" + db.is_blank("developers_group") + "";
		String noCreatorsType = "" + db.is_blank("creators_type") + "";
		String noCreatorsGroup = "" + db.is_blank("creators_group") + "";
		String noEditorsType = "" + db.is_blank("editors_type") + "";
		String noEditorsGroup = "" + db.is_blank("editors_group") + "";
		String noPublishersType = "" + db.is_blank("publishers_type") + "";
		String noPublishersGroup = "" + db.is_blank("publishers_group") + "";
		String noAdministratorsType = "" + db.is_blank("administrators_type") + "";
		String noAdministratorsGroup = "" + db.is_blank("administrators_group") + "";

		if (mysession.get("admin_contentgroup").equals("-")) {
			// ok
		} else if (! mysession.get("admin_contentgroup").equals("")) {
			Contentgroup contentgroup = new Contentgroup();
			if (mysession.get("admin_contentclass").equals("product")) {
				Productgroup productgroup = new Productgroup();
				productgroup.readProductgroup(db, "" + mysession.get("admin_contentgroup"));
				contentgroup = new Contentgroup(productgroup);
			} else if (mysession.get("admin_contentclass").equals("image")) {
				Imagegroup imagegroup = new Imagegroup();
				imagegroup.readImagegroup(db, "" + mysession.get("admin_contentgroup"));
				contentgroup = new Contentgroup(imagegroup);
			} else if (mysession.get("admin_contentclass").equals("file")) {
				Filegroup filegroup = new Filegroup();
				filegroup.readFilegroup(db, "" + mysession.get("admin_contentgroup"));
				contentgroup = new Contentgroup(filegroup);
			} else if (mysession.get("admin_contentclass").equals("link")) {
				Linkgroup linkgroup = new Linkgroup();
				linkgroup.readLinkgroup(db, "" + mysession.get("admin_contentgroup"));
				contentgroup = new Contentgroup(linkgroup);
			} else {
				contentgroup.readContentgroup(db, "" + mysession.get("admin_contentgroup"));
			}
			if (contentgroup.getUsersType().equals("")) {
				// all administrators - ok
			} else if (contentgroup.getUsersType().equals("*")) {
				// all administrators - ok
			} else if (contentgroup.getUsersType().equals("=")) {
				// self
				isUsersType += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usertype")+"|"+mysession.get("usertypes")+"|").indexOf("|"+contentgroup.getUsersType()+"|")<0) {
				// none
				isUsersType = "(1=0)";
				noUsersType = "(2=0)";
			}
			if (contentgroup.getUsersGroup().equals("")) {
				// all administrators - ok
			} else if (contentgroup.getUsersGroup().equals("*")) {
				// all administrators - ok
			} else if (contentgroup.getUsersGroup().equals("=")) {
				// self
				isUsersGroup += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usergroup")+"|"+mysession.get("usergroups")+"|").indexOf("|"+contentgroup.getUsersGroup()+"|")<0) {
				// none
				isUsersGroup = "(3=0)";
				noUsersGroup = "(4=0)";
			}
			if (contentgroup.getDevelopersType().equals("")) {
				// all administrators - ok
			} else if (contentgroup.getDevelopersType().equals("*")) {
				// all administrators - ok
			} else if (contentgroup.getDevelopersType().equals("=")) {
				// self
				isDevelopersType += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usertype")+"|"+mysession.get("usertypes")+"|").indexOf("|"+contentgroup.getDevelopersType()+"|")<0) {
				// none
				isDevelopersType = "(5=0)";
				noDevelopersType = "(6=0)";
			}
			if (contentgroup.getDevelopersGroup().equals("")) {
				// all administrators - ok
			} else if (contentgroup.getDevelopersGroup().equals("*")) {
				// all administrators - ok
			} else if (contentgroup.getDevelopersGroup().equals("=")) {
				// self
				isDevelopersGroup += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usergroup")+"|"+mysession.get("usergroups")+"|").indexOf("|"+contentgroup.getDevelopersGroup()+"|")<0) {
				// none
				isDevelopersGroup = "(7=0)";
				noDevelopersGroup = "(8=0)";
			}
			if (contentgroup.getCreatorsType().equals("")) {
				// all administrators - ok
			} else if (contentgroup.getCreatorsType().equals("*")) {
				// all administrators - ok
			} else if (contentgroup.getCreatorsType().equals("=")) {
				// self
				isCreatorsType += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usertype")+"|"+mysession.get("usertypes")+"|").indexOf("|"+contentgroup.getCreatorsType()+"|")<0) {
				// none
				isCreatorsType = "(9=0)";
				noCreatorsType = "(10=0)";
			}
			if (contentgroup.getCreatorsGroup().equals("")) {
				// all administrators - ok
			} else if (contentgroup.getCreatorsGroup().equals("*")) {
				// all administrators - ok
			} else if (contentgroup.getCreatorsGroup().equals("=")) {
				// self
				isCreatorsGroup += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usergroup")+"|"+mysession.get("usergroups")+"|").indexOf("|"+contentgroup.getCreatorsGroup()+"|")<0) {
				// none
				isCreatorsGroup = "(11=0)";
				noCreatorsGroup = "(12=0)";
			}
			if (contentgroup.getEditorsType().equals("")) {
				// all administrators - ok
			} else if (contentgroup.getEditorsType().equals("*")) {
				// all administrators - ok
			} else if (contentgroup.getEditorsType().equals("=")) {
				// self
				isEditorsType += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usertype")+"|"+mysession.get("usertypes")+"|").indexOf("|"+contentgroup.getEditorsType()+"|")<0) {
				// none
				isEditorsType = "(13=0)";
				noEditorsType = "(14=0)";
			}
			if (contentgroup.getEditorsGroup().equals("")) {
				// all administrators - ok
			} else if (contentgroup.getEditorsGroup().equals("*")) {
				// all administrators - ok
			} else if (contentgroup.getEditorsGroup().equals("=")) {
				// self
				isEditorsGroup += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usergroup")+"|"+mysession.get("usergroups")+"|").indexOf("|"+contentgroup.getEditorsGroup()+"|")<0) {
				// none
				isEditorsGroup = "(15=0)";
				noEditorsGroup = "(16=0)";
			}
			if (contentgroup.getPublishersType().equals("")) {
				// all administrators - ok
			} else if (contentgroup.getPublishersType().equals("*")) {
				// all administrators - ok
			} else if (contentgroup.getPublishersType().equals("=")) {
				// self
				isPublishersType += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usertype")+"|"+mysession.get("usertypes")+"|").indexOf("|"+contentgroup.getPublishersType()+"|")<0) {
				// none
				isPublishersType = "(17=0)";
				noPublishersType = "(18=0)";
			}
			if (contentgroup.getPublishersGroup().equals("")) {
				// all administrators - ok
			} else if (contentgroup.getPublishersGroup().equals("*")) {
				// all administrators - ok
			} else if (contentgroup.getPublishersGroup().equals("=")) {
				// self
				isPublishersGroup += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usergroup")+"|"+mysession.get("usergroups")+"|").indexOf("|"+contentgroup.getPublishersGroup()+"|")<0) {
				// none
				isPublishersGroup = "(19=0)";
				noPublishersGroup = "(20=0)";
			}
			if (contentgroup.getAdministratorsType().equals("")) {
				// all administrators - ok
			} else if (contentgroup.getAdministratorsType().equals("*")) {
				// all administrators - ok
			} else if (contentgroup.getAdministratorsType().equals("=")) {
				// self
				isAdministratorsType += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usertype")+"|"+mysession.get("usertypes")+"|").indexOf("|"+contentgroup.getAdministratorsType()+"|")<0) {
				// none
				isAdministratorsType = "(21=0)";
				noAdministratorsType = "(22=0)";
			}
			if (contentgroup.getAdministratorsGroup().equals("")) {
				// all administrators - ok
			} else if (contentgroup.getAdministratorsGroup().equals("*")) {
				// all administrators - ok
			} else if (contentgroup.getAdministratorsGroup().equals("=")) {
				// self
				isAdministratorsGroup += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usergroup")+"|"+mysession.get("usergroups")+"|").indexOf("|"+contentgroup.getAdministratorsGroup()+"|")<0) {
				// none
				isAdministratorsGroup = "(23=0)";
				noAdministratorsGroup = "(24=0)";
			}
		}
		if (mysession.get("admin_contenttype").equals("-")) {
			// ok
		} else if (! mysession.get("admin_contenttype").equals("")) {
			Contenttype contenttype = new Contenttype();
			if (mysession.get("admin_contentclass").equals("product")) {
				Producttype producttype = new Producttype();
				producttype.readProducttype(db, "" + mysession.get("admin_contenttype"));
				contenttype = new Contenttype(producttype);
			} else if (mysession.get("admin_contentclass").equals("image")) {
				Imagetype imagetype = new Imagetype();
				imagetype.readImagetype(db, "" + mysession.get("admin_contenttype"));
				contenttype = new Contenttype(imagetype);
			} else if (mysession.get("admin_contentclass").equals("file")) {
				Filetype filetype = new Filetype();
				filetype.readFiletype(db, "" + mysession.get("admin_contenttype"));
				contenttype = new Contenttype(filetype);
			} else if (mysession.get("admin_contentclass").equals("link")) {
				Linktype linktype = new Linktype();
				linktype.readLinktype(db, "" + mysession.get("admin_contenttype"));
				contenttype = new Contenttype(linktype);
			} else {
				contenttype.readContenttype(db, "" + mysession.get("admin_contenttype"));
			}
			if (contenttype.getUsersType().equals("")) {
				// all administrators - ok
			} else if (contenttype.getUsersType().equals("*")) {
				// all administrators - ok
			} else if (contenttype.getUsersType().equals("=")) {
				// self
				isUsersType += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usertype")+"|"+mysession.get("usertypes")+"|").indexOf("|"+contenttype.getUsersType()+"|")<0) {
				// none
				isUsersType = "(25=0)";
				noUsersType = "(26=0)";
			}
			if (contenttype.getUsersGroup().equals("")) {
				// all administrators - ok
			} else if (contenttype.getUsersGroup().equals("*")) {
				// all administrators - ok
			} else if (contenttype.getUsersGroup().equals("=")) {
				// self
				isUsersGroup += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usergroup")+"|"+mysession.get("usergroups")+"|").indexOf("|"+contenttype.getUsersGroup()+"|")<0) {
				// none
				isUsersGroup = "(27=0)";
				noUsersGroup = "(28=0)";
			}
			if (contenttype.getDevelopersType().equals("")) {
				// all administrators - ok
			} else if (contenttype.getDevelopersType().equals("*")) {
				// all administrators - ok
			} else if (contenttype.getDevelopersType().equals("=")) {
				// self
				isDevelopersType += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usertype")+"|"+mysession.get("usertypes")+"|").indexOf("|"+contenttype.getDevelopersType()+"|")<0) {
				// none
				isDevelopersType = "(29=0)";
				noDevelopersType = "(30=0)";
			}
			if (contenttype.getDevelopersGroup().equals("")) {
				// all administrators - ok
			} else if (contenttype.getDevelopersGroup().equals("*")) {
				// all administrators - ok
			} else if (contenttype.getDevelopersGroup().equals("=")) {
				// self
				isDevelopersGroup += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usergroup")+"|"+mysession.get("usergroups")+"|").indexOf("|"+contenttype.getDevelopersGroup()+"|")<0) {
				// none
				isDevelopersGroup = "(31=0)";
				noDevelopersGroup = "(32=0)";
			}
			if (contenttype.getCreatorsType().equals("")) {
				// all administrators - ok
			} else if (contenttype.getCreatorsType().equals("*")) {
				// all administrators - ok
			} else if (contenttype.getCreatorsType().equals("=")) {
				// self
				isCreatorsType += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usertype")+"|"+mysession.get("usertypes")+"|").indexOf("|"+contenttype.getCreatorsType()+"|")<0) {
				// none
				isCreatorsType = "(33=0)";
				noCreatorsType = "(34=0)";
			}
			if (contenttype.getCreatorsGroup().equals("")) {
				// all administrators - ok
			} else if (contenttype.getCreatorsGroup().equals("*")) {
				// all administrators - ok
			} else if (contenttype.getCreatorsGroup().equals("=")) {
				// self
				isCreatorsGroup += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usergroup")+"|"+mysession.get("usergroups")+"|").indexOf("|"+contenttype.getCreatorsGroup()+"|")<0) {
				// none
				isCreatorsGroup = "(35=0)";
				noCreatorsGroup = "(36=0)";
			}
			if (contenttype.getEditorsType().equals("")) {
				// all administrators - ok
			} else if (contenttype.getEditorsType().equals("*")) {
				// all administrators - ok
			} else if (contenttype.getEditorsType().equals("=")) {
				// self
				isEditorsType += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usertype")+"|"+mysession.get("usertypes")+"|").indexOf("|"+contenttype.getEditorsType()+"|")<0) {
				// none
				isEditorsType = "(37=0)";
				noEditorsType = "(38=0)";
			}
			if (contenttype.getEditorsGroup().equals("")) {
				// all administrators - ok
			} else if (contenttype.getEditorsGroup().equals("*")) {
				// all administrators - ok
			} else if (contenttype.getEditorsGroup().equals("=")) {
				// self
				isEditorsGroup += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usergroup")+"|"+mysession.get("usergroups")+"|").indexOf("|"+contenttype.getEditorsGroup()+"|")<0) {
				// none
				isEditorsGroup = "(39=0)";
				noEditorsGroup = "(40=0)";
			}
			if (contenttype.getPublishersType().equals("")) {
				// all administrators - ok
			} else if (contenttype.getPublishersType().equals("*")) {
				// all administrators - ok
			} else if (contenttype.getPublishersType().equals("=")) {
				// self
				isPublishersType += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usertype")+"|"+mysession.get("usertypes")+"|").indexOf("|"+contenttype.getPublishersType()+"|")<0) {
				// none
				isPublishersType = "(41=0)";
				noPublishersType = "(42=0)";
			}
			if (contenttype.getPublishersGroup().equals("")) {
				// all administrators - ok
			} else if (contenttype.getPublishersGroup().equals("*")) {
				// all administrators - ok
			} else if (contenttype.getPublishersGroup().equals("=")) {
				// self
				isPublishersGroup += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usergroup")+"|"+mysession.get("usergroups")+"|").indexOf("|"+contenttype.getPublishersGroup()+"|")<0) {
				// none
				isPublishersGroup = "(43=0)";
				noPublishersGroup = "(44=0)";
			}
			if (contenttype.getAdministratorsType().equals("")) {
				// all administrators - ok
			} else if (contenttype.getAdministratorsType().equals("*")) {
				// all administrators - ok
			} else if (contenttype.getAdministratorsType().equals("=")) {
				// self
				isAdministratorsType += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usertype")+"|"+mysession.get("usertypes")+"|").indexOf("|"+contenttype.getAdministratorsType()+"|")<0) {
				// none
				isAdministratorsType = "(45=0)";
				noAdministratorsType = "(46=0)";
			}
			if (contenttype.getAdministratorsGroup().equals("")) {
				// all administrators - ok
			} else if (contenttype.getAdministratorsGroup().equals("*")) {
				// all administrators - ok
			} else if (contenttype.getAdministratorsGroup().equals("=")) {
				// self
				isAdministratorsGroup += " and created_by=" + db.quote(mysession.get("username")) + "";
			} else if (("|"+mysession.get("usergroup")+"|"+mysession.get("usergroups")+"|").indexOf("|"+contenttype.getAdministratorsGroup()+"|")<0) {
				// none
				isAdministratorsGroup = "(47=0)";
				noAdministratorsGroup = "(48=0)";
			}
		}

		String isUser = "";
		String isDeveloper = "";
		String isCreator = "";
		String isEditor = "";
		String isPublisher = "";
		String isAdministrator = "";
		if (myconfig.get(db, "accessrestrictions").equals("or")) {
			isUser = "((" + noUsersType + " and " + noUsersGroup + ") or (" + isUsersGroup + " or " + isUsersType + "))";
			isDeveloper = "((" + noDevelopersType + " and " + noDevelopersGroup + ") or (" + isDevelopersGroup + " or " + isDevelopersType + "))";
			isCreator = "((" + noCreatorsType + " and " + noCreatorsGroup + ") or (" + isCreatorsGroup + " or " + isCreatorsType + "))";
			isEditor = "((" + noEditorsType + " and " + noEditorsGroup + ") or (" + isEditorsGroup + " or " + isEditorsType + "))";
			isPublisher = "((" + noPublishersType + " and " + noPublishersGroup + ") or (" + isPublishersGroup + " or " + isPublishersType + "))";
			isAdministrator = "((" + noAdministratorsType + " and " + noAdministratorsGroup + ") or (" + isAdministratorsGroup + " or " + isAdministratorsType + "))";
		} else {
			isUser = "((" + noUsersType + " and " + noUsersGroup + ") or (" + noUsersType + " and " + isUsersGroup + ") or (" + isUsersType + " and " + noUsersGroup + ") or (" + isUsersGroup + " and " + isUsersType + "))";
			isDeveloper = "((" + noDevelopersType + " and " + noDevelopersGroup + ") or (" + noDevelopersType + " and " + isDevelopersGroup + ") or (" + isDevelopersType + " and " + noDevelopersGroup + ") or (" + isDevelopersGroup + " and " + isDevelopersType + "))";
			isCreator = "((" + noCreatorsType + " and " + noCreatorsGroup + ") or (" + noCreatorsType + " and " + isCreatorsGroup + ") or (" + isCreatorsType + " and " + noCreatorsGroup + ") or (" + isCreatorsGroup + " and " + isCreatorsType + "))";
			isEditor = "((" + noEditorsType + " and " + noEditorsGroup + ") or (" + noEditorsType + " and " + isEditorsGroup + ") or (" + isEditorsType + " and " + noEditorsGroup + ") or (" + isEditorsGroup + " and " + isEditorsType + "))";
			isPublisher = "((" + noPublishersType + " and " + noPublishersGroup + ") or (" + noPublishersType + " and " + isPublishersGroup + ") or (" + isPublishersType + " and " + noPublishersGroup + ") or (" + isPublishersGroup + " and " + isPublishersType + "))";
			isAdministrator = "((" + noAdministratorsType + " and " + noAdministratorsGroup + ") or (" + noAdministratorsType + " and " + isAdministratorsGroup + ") or (" + isAdministratorsType + " and " + noAdministratorsGroup + ") or (" + isAdministratorsGroup + " and " + isAdministratorsType + "))";
		}

		String SQLwhereEdit = "" + SQLwhere;
		if (! mysession.get("username").equals(myconfig.get(db, "superadmin"))) {
			SQLwhereEdit = Common.SQLwhere(SQLwhereEdit, "(" + isUser + " or " + isDeveloper + " or " + isCreator + " or " + isEditor + " or " + isPublisher + " or " + isAdministrator + ")");
		}
		String SQLwhereCreate = "" + SQLwhere;
		if (! mysession.get("username").equals(myconfig.get(db, "superadmin"))) {
			SQLwhereCreate = Common.SQLwhere(SQLwhereCreate, "(" + isCreator + " or " + isPublisher + " or " + isAdministrator + ")");
		}

		String SQL = "select count(*) as count from content " + SQLwhereEdit;
		record_count = db.query_value(SQL).intValue();

		String SQLorderDir = "";
		if (myrequest.getParameter("sort_dir").equals("DESC")) {
			SQLorderDir = " desc";
		}
		String SQLorder = "title" + SQLorderDir + ", version" + SQLorderDir + ", device" + SQLorderDir + ", usersegment" + SQLorderDir + ", usertest" + SQLorderDir + ", id" + SQLorderDir;
		if (myrequest.getParameter("sort_col").equals("column_title")) {
			// default;
		} else if (myrequest.getParameter("sort_col").equals("column_id")) {
			SQLorder = "id" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_contentpackage")) {
			SQLorder = "contentpackage" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_contentclass")) {
			SQLorder = "contentclass" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_contentbundle")) {
			SQLorder = "contentbundle" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_contentgroup")) {
			SQLorder = "contentgroup" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_contenttype")) {
			SQLorder = "contenttype" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_version")) {
			SQLorder = "version" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_status")) {
			SQLorder = "updated" + SQLorderDir + ", published" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_stock")) {
			SQLorder = "product_stock_amount" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_stocklow")) {
			SQLorder = "product_lowstock_amount" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_product_brand")) {
			SQLorder = "product_brand" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_product_colour")) {
			SQLorder = "product_colour" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_product_size")) {
			SQLorder = "product_size" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", id" + SQLorderDir;
		}
		
		Content mypage = new Content(text);
		create_records = new Content(text);
		String offset = html.encodeHtmlEntities(myrequest.getParameter("offset"));
		String limit = html.encodeHtmlEntities(myrequest.getParameter("page_size"));
		if ((! offset.equals("")) && (! limit.equals(""))) {
			if (SQLwhereEdit.length() < 6) SQLwhereEdit += "      ";
			SQL = Common.SQLlimit(db.db_type(db.getDatabase()), "id", "*", "content", SQLwhereEdit.substring(6), SQLorder, (! SQLorderDir.equals("")), Integer.parseInt("0" + offset), Integer.parseInt("0" + limit));
			if (edits) {
				mypage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}
			SQL = "select * from content where 1=0";
			if (creates) {
				create_records.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}
		} else if ((mysession.get("admin_pagesize").equals("")) && (mysession.get("admin_usertest").equals("")) && (mysession.get("admin_segment").equals(""))) {
			SQL = "select * from content where 1=0";
			if (edits) {
				mypage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}
			SQL = "select * from content " + SQLwhereCreate + " order by " + SQLorder;
			if (creates) {
				create_records.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}
		} else {
			SQL = "select * from content " + SQLwhereEdit + " order by " + SQLorder;
			if (edits) {
				mypage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}
			SQL = "select * from content " + SQLwhereCreate + " order by " + SQLorder;
			if (creates) {
				create_records.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}
		}
		if ((creates) && (! edits)) {
			mypage = create_records;
		}
		handleCreateBlank(mypage, mysession, myrequest, myconfig, db);
		return mypage;
	}



	public Content getArchive(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		if (db == null) return new Content(text);
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Content(text);
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String SQL = "select archiveid, id, locked, locked_administrator, locked_creator, locked_developer, locked_editor, locked_publisher, locked_schedule, locked_unschedule, locked_user, created, created_by, updated, updated_by, published, published_by, unpublished, unpublished_by, scheduled_publish, scheduled_unpublish, requested_publish, requested_unpublish, status, status_by, title, revision, contentpackage, contentclass, contentbundle, contenttype, contentgroup, version, checkedout, users_users, users_type, users_group, creators_users, creators_type, creators_group, editors_users, editors_type, editors_group, developers_users, developers_type, developers_group, publishers_users, publishers_type, publishers_group, administrators_users, administrators_type, administrators_group, version_master, page_top, page_up, page_first, page_previous, page_next, page_last, template, stylesheet from content_archive where (id=" + myrequest.getParameter("id") + ") and ((" + db.is_blank("published") + ") or (published<" + db.quote(now) + ")) order by updated desc, archiveid desc";
		Content mypage = new Content(text);
		mypage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
		return mypage;
	}



	public Content getSchedule(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		if (db == null) return new Content(text);
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Content(text);
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String SQL = "select archiveid, id, locked, locked_administrator, locked_creator, locked_developer, locked_editor, locked_publisher, locked_schedule, locked_unschedule, locked_user, created, created_by, updated, updated_by, published, published_by, unpublished, unpublished_by, scheduled_publish, scheduled_unpublish, requested_publish, requested_unpublish, status, status_by, title, revision, contentpackage, contentclass, contentbundle, contenttype, contentgroup, version, checkedout, users_users, users_type, users_group, creators_users, creators_type, creators_group, editors_users, editors_type, editors_group, developers_users, developers_type, developers_group, publishers_users, publishers_type, publishers_group, administrators_users, administrators_type, administrators_group, version_master, page_top, page_up, page_first, page_previous, page_next, page_last, template, stylesheet from content_archive where (id=" + myrequest.getParameter("id") + ") and ((" + db.is_not_blank("published") + ") and (published>" + db.quote(now) + ")) order by published, archiveid";
		Content mypage = new Content(text);
		mypage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
		return mypage;
	}



	public Content getDeleteMultiple(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		if (db == null) return new Content(text);
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Content(text);
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		String session_administrator = mysession.get("administrator");
		String session_userid = mysession.get("userid");
		String session_username = mysession.get("username");
		String session_usertype = mysession.get("usertype");
		String session_usergroup = mysession.get("usergroup");
		String session_usertypes = mysession.get("usertypes");
		String session_usergroups = mysession.get("usergroups");
		setSessionFilterFromRequest(mysession, myrequest);

		String SQLwhere = "";
		if (mysession.get("admin_contentclass").equals("element")) {
//			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "page");
//			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "template");
//			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "stylesheet");
//			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "script");
//			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "image");
//			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "file");
//			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "link");
//			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "product");
			String elements = Element.csv(db);
			if (! elements.equals("")) {
				elements = "element," + elements;
			} else {
				elements = "element";
			}
			SQLwhere = Common.SQLwhere_in(SQLwhere, "contentclass", "" + mysession.get("admin_contentclass").replaceAll("element",elements));
		} else if (mysession.get("admin_contentclass").equals("page,product")) {
			SQLwhere = Common.SQLwhere_equals_or(SQLwhere, "contentclass", "page", "contentclass", "product");
		} else if (! mysession.get("admin_contentclass").equals("")) {
			String elements = Element.csv(db);
			if (! elements.equals("")) {
				elements = "element," + elements;
			} else {
				elements = "element";
			}
			SQLwhere = Common.SQLwhere_in(SQLwhere, "contentclass", "" + mysession.get("admin_contentclass").replaceAll("element", elements));
		}

		if (mysession.get("admin_contenttype").equals("-")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contenttype", "");
		} else if (! mysession.get("admin_contenttype").equals("")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contenttype", "" + mysession.get("admin_contenttype"));
		}
		if (mysession.get("admin_contentgroup").equals("-")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentgroup", "");
		} else if (! mysession.get("admin_contentgroup").equals("")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentgroup", "" + mysession.get("admin_contentgroup"));
		}
		if (mysession.get("admin_contentbundle").equals("-")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentbundle", "");
		} else if (! mysession.get("admin_contentbundle").equals("")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentbundle", "" + mysession.get("admin_contentbundle"));
		}
		if (mysession.get("admin_version").equals("-")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "version", "");
		} else if (! mysession.get("admin_version").equals("")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "version", "" + mysession.get("admin_version"));
		}

		String isUsersType = "((" + db.is_not_blank("users_type") + " and users_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (users_type=" + db.quote("*") + ") or (users_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isUsersGroup = "((" + db.is_not_blank("users_group") + " and users_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (users_group=" + db.quote("*") + ") or (users_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isDevelopersType = "((" + db.is_not_blank("developers_type") + " and developers_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (developers_type=" + db.quote("*") + ") or (developers_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isDevelopersGroup = "((" + db.is_not_blank("developers_group") + " and developers_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (developers_group=" + db.quote("*") + ") or (developers_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isCreatorsType = "((" + db.is_not_blank("creators_type") + " and creators_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (creators_type=" + db.quote("*") + ") or (creators_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isCreatorsGroup = "((" + db.is_not_blank("creators_group") + " and creators_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (creators_group=" + db.quote("*") + ") or (creators_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isEditorsType = "((" + db.is_not_blank("editors_type") + " and editors_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (editors_type=" + db.quote("*") + ") or (editors_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isEditorsGroup = "((" + db.is_not_blank("editors_group") + " and editors_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (editors_group=" + db.quote("*") + ") or (editors_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isPublishersType = "((" + db.is_not_blank("publishers_type") + " and publishers_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (publishers_type=" + db.quote("*") + ") or (publishers_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isPublishersGroup = "((" + db.is_not_blank("publishers_group") + " and publishers_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (publishers_group=" + db.quote("*") + ") or (publishers_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isAdministratorsType = "((" + db.is_not_blank("administrators_type") + " and administrators_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (administrators_type=" + db.quote("*") + ") or (administrators_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isAdministratorsGroup = "((" + db.is_not_blank("administrators_group") + " and administrators_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (administrators_group=" + db.quote("*") + ") or (administrators_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String noUsersType = "" + db.is_blank("users_type") + "";
		String noUsersGroup = "" + db.is_blank("users_group") + "";
		String noDevelopersType = "" + db.is_blank("developers_type") + "";
		String noDevelopersGroup = "" + db.is_blank("developers_group") + "";
		String noCreatorsType = "" + db.is_blank("creators_type") + "";
		String noCreatorsGroup = "" + db.is_blank("creators_group") + "";
		String noEditorsType = "" + db.is_blank("editors_type") + "";
		String noEditorsGroup = "" + db.is_blank("editors_group") + "";
		String noPublishersType = "" + db.is_blank("publishers_type") + "";
		String noPublishersGroup = "" + db.is_blank("publishers_group") + "";
		String noAdministratorsType = "" + db.is_blank("administrators_type") + "";
		String noAdministratorsGroup = "" + db.is_blank("administrators_group") + "";

		String isUser = "";
		String isDeveloper = "";
		String isCreator = "";
		String isEditor = "";
		String isPublisher = "";
		String isAdministrator = "";
		if (myconfig.get(db, "accessrestrictions").equals("or")) {
			isUser = "((" + noUsersType + " and " + noUsersGroup + ") or (" + isUsersGroup + " or " + isUsersType + "))";
			isDeveloper = "((" + noDevelopersType + " and " + noDevelopersGroup + ") or (" + isDevelopersGroup + " or " + isDevelopersType + "))";
			isCreator = "((" + noCreatorsType + " and " + noCreatorsGroup + ") or (" + isCreatorsGroup + " or " + isCreatorsType + "))";
			isEditor = "((" + noEditorsType + " and " + noEditorsGroup + ") or (" + isEditorsGroup + " or " + isEditorsType + "))";
			isPublisher = "((" + noPublishersType + " and " + noPublishersGroup + ") or (" + isPublishersGroup + " or " + isPublishersType + "))";
			isAdministrator = "((" + noAdministratorsType + " and " + noAdministratorsGroup + ") or (" + isAdministratorsGroup + " or " + isAdministratorsType + "))";
		} else {
			isUser = "((" + noUsersType + " and " + noUsersGroup + ") or (" + noUsersType + " and " + isUsersGroup + ") or (" + isUsersType + " and " + noUsersGroup + ") or (" + isUsersGroup + " and " + isUsersType + "))";
			isDeveloper = "((" + noDevelopersType + " and " + noDevelopersGroup + ") or (" + noDevelopersType + " and " + isDevelopersGroup + ") or (" + isDevelopersType + " and " + noDevelopersGroup + ") or (" + isDevelopersGroup + " and " + isDevelopersType + "))";
			isCreator = "((" + noCreatorsType + " and " + noCreatorsGroup + ") or (" + noCreatorsType + " and " + isCreatorsGroup + ") or (" + isCreatorsType + " and " + noCreatorsGroup + ") or (" + isCreatorsGroup + " and " + isCreatorsType + "))";
			isEditor = "((" + noEditorsType + " and " + noEditorsGroup + ") or (" + noEditorsType + " and " + isEditorsGroup + ") or (" + isEditorsType + " and " + noEditorsGroup + ") or (" + isEditorsGroup + " and " + isEditorsType + "))";
			isPublisher = "((" + noPublishersType + " and " + noPublishersGroup + ") or (" + noPublishersType + " and " + isPublishersGroup + ") or (" + isPublishersType + " and " + noPublishersGroup + ") or (" + isPublishersGroup + " and " + isPublishersType + "))";
			isAdministrator = "((" + noAdministratorsType + " and " + noAdministratorsGroup + ") or (" + noAdministratorsType + " and " + isAdministratorsGroup + ") or (" + isAdministratorsType + " and " + noAdministratorsGroup + ") or (" + isAdministratorsGroup + " and " + isAdministratorsType + "))";
		}

		if (! mysession.get("username").equals(myconfig.get(db, "superadmin"))) {
			SQLwhere = Common.SQLwhere(SQLwhere, "(" + isUser + " or " + isDeveloper + " or " + isCreator + " or " + isEditor + " or " + isPublisher + " or " + isAdministrator + ")");
		}
		if (SQLwhere.equals("")) {
			SQLwhere = "where (1=1)";
		}

		String SQL = "";
		String SQLwhere_in = "";
		String[] ids = myrequest.getParameters("id");
		String[] archiveids = myrequest.getParameters("archiveid");
		if (ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				if (! SQLwhere_in.equals("")) {
					SQLwhere_in += ",";
				}
				SQLwhere_in += id;
			}
			SQLwhere += " and id in (" + SQLwhere_in + ")";
			SQL = "select id, locked, locked_administrator, locked_creator, locked_developer, locked_editor, locked_publisher, locked_schedule, locked_unschedule, locked_user, created, created_by, updated, updated_by, published, published_by, unpublished, unpublished_by, scheduled_publish, scheduled_unpublish, requested_publish, requested_unpublish, status, status_by, title, contentpackage, contentclass, contentbundle, contenttype, contentgroup, version, checkedout, users_users, users_type, users_group, creators_users, creators_type, creators_group, editors_users, editors_type, editors_group, developers_users, developers_type, developers_group, publishers_users, publishers_type, publishers_group, administrators_users, administrators_type, administrators_group, version_master, page_top, page_up, page_first, page_previous, page_next, page_last, template, stylesheet from content " + SQLwhere + " order by title, version, id";
		} else if (archiveids.length > 0) {
			for (int i = 0; i < archiveids.length; i++) {
				String id = archiveids[i];
				if (! SQLwhere_in.equals("")) {
					SQLwhere_in += ",";
				}
				SQLwhere_in += id;
			}
			SQLwhere += " and archiveid in (" + SQLwhere_in + ")";
			SQL = "select archiveid, id, locked, locked_administrator, locked_creator, locked_developer, locked_editor, locked_publisher, locked_schedule, locked_unschedule, locked_user, created, created_by, updated, updated_by, published, published_by, unpublished, unpublished_by, scheduled_publish, scheduled_unpublish, requested_publish, requested_unpublish, status, status_by, title, contentpackage, contentclass, contentbundle, contenttype, contentgroup, version, checkedout, users_users, users_type, users_group, creators_users, creators_type, creators_group, editors_users, editors_type, editors_group, developers_users, developers_type, developers_group, publishers_users, publishers_type, publishers_group, administrators_users, administrators_type, administrators_group, version_master, page_top, page_up, page_first, page_previous, page_next, page_last, template, stylesheet from content_archive " + SQLwhere + " order by title, version, id, archiveid";
		} else {
			SQLwhere += " and id in (0)";
			SQL = "select id, locked, locked_administrator, locked_creator, locked_developer, locked_editor, locked_publisher, locked_schedule, locked_unschedule, locked_user, created, created_by, updated, updated_by, published, published_by, unpublished, unpublished_by, scheduled_publish, scheduled_unpublish, requested_publish, requested_unpublish, status, status_by, title, contentpackage, contentclass, contentbundle, contenttype, contentgroup, version, checkedout, users_users, users_type, users_group, creators_users, creators_type, creators_group, editors_users, editors_type, editors_group, developers_users, developers_type, developers_group, publishers_users, publishers_type, publishers_group, administrators_users, administrators_type, administrators_group, version_master, page_top, page_up, page_first, page_previous, page_next, page_last, template, stylesheet from content " + SQLwhere + " order by title, version, id";
		}

		Content mypage = new Content(text);
		mypage.records(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, myconfig, SQL);
		return mypage;
	}



	public Page getView(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return new Page(text);
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Page(text);
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		Page mypage = getPage(server, mysession, myrequest, myresponse, myconfig, db, null, false);
		return mypage;
	}



	public Page getCreate(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return new Page(text);
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Page(text);
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		Page mypage = getPage(server, mysession, myrequest, myresponse, myconfig, db, null, false);

/* QQQQQ automatic linking of related content items
		if (! mypage.getPageNext().equals("")) {
			Page mypagenext = getPageById(mypage.getPageNext(), server, mysession, myrequest, myresponse, myconfig, db);
			if ((mypagenext.getId().equals(mypage.getPageNext())) && (mypagenext.getPagePrevious().equals(mypage.getId()))) {
				mypage.setPageNext(mypagenext.getId());
				mypage.setPagePrevious(mypage.getId());
				mypage.parse_input(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), myconfig.get(db, "default_template"), mysession.get("stylesheet"), myconfig.get(db, "default_stylesheet"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			}
		}
*/

		handleCreateBlank(mypage, mysession, myrequest, myconfig, db);
		return mypage;
	}



	public Page getUpdate(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return new Page(text);
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Page(text);
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		Page mypage = getPage(server, mysession, myrequest, myresponse, myconfig, db, null, false);
		if (mypage.getEditor() && (myconfig.get(db, "use_checkout").equals("auto-on-update"))) {
			Workflow workflow = new Workflow(text);
			if ((myconfig.get(db, "use_workflow").equals("yes")) && (! mypage.getAdministrator()) && (! workflow.permissions(db, mypage.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (mypage.getStatusBy().equals(mysession.get("username")))))) {
				// no workflow permissions
			} else {
				mypage.checkout(db, mysession.get("username"));
			}
		}
		return mypage;
	}



	public Page getDelete(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return new Page(text);
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Page(text);
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		Page mypage = getPage(server, mysession, myrequest, myresponse, myconfig, db, null, false);
		return mypage;
	}



	public Content doSearch(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		return doSearch(mysession, myrequest, myresponse, myconfig, db, true, true);
	}
	public Content doSearch(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, boolean edits, boolean creates) {
		if (db == null) return new Content(text);
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Content(text);
		error = "";
		setSessionFilterFromRequest(mysession, myrequest);
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
		if (! myrequest.getParameter("bundle").equals("")) {
			mysession.set("search_bundle", html.encodeHtmlEntities(myrequest.getParameter("bundle")));
		} else if (myrequest.parameterExists("bundle")) {
			mysession.set("search_bundle", "");
		}
		if (! myrequest.getParameter("group").equals("")) {
			mysession.set("search_group", html.encodeHtmlEntities(myrequest.getParameter("group")));
		} else if (myrequest.parameterExists("group")) {
			mysession.set("search_group", "");
		}
		if (! myrequest.getParameter("type").equals("")) {
			mysession.set("search_type", html.encodeHtmlEntities(myrequest.getParameter("type")));
		} else if (myrequest.parameterExists("type")) {
			mysession.set("search_type", "");
		}
		if (! myrequest.getParameter("version").equals("")) {
			mysession.set("search_version", html.encodeHtmlEntities(myrequest.getParameter("version")));
		} else if (myrequest.parameterExists("version")) {
			mysession.set("search_version", "");
		}
		if (! myrequest.getParameter("device").equals("")) {
			mysession.set("search_device", html.encodeHtmlEntities(myrequest.getParameter("device")));
		} else if (myrequest.parameterExists("device")) {
			mysession.set("search_device", "");
		}
		if (! myrequest.getParameter("usersegment").equals("")) {
			mysession.set("search_usersegment", html.encodeHtmlEntities(myrequest.getParameter("usersegment")));
		} else if (myrequest.parameterExists("usersegment")) {
			mysession.set("search_usersegment", "");
		}
		if (! myrequest.getParameter("usertest").equals("")) {
			mysession.set("search_usertest", html.encodeHtmlEntities(myrequest.getParameter("usertest")));
		} else if (myrequest.parameterExists("usertest")) {
			mysession.set("search_usertest", "");
		}
		if (! myrequest.getParameter("package").equals("")) {
			mysession.set("search_package", html.encodeHtmlEntities(myrequest.getParameter("package")));
		} else if (myrequest.parameterExists("package")) {
			mysession.set("search_package", "");
		}
		if (! myrequest.getParameter("status").equals("")) {
			mysession.set("search_status", html.encodeHtmlEntities(myrequest.getParameter("status")));
		} else if (myrequest.parameterExists("status")) {
			mysession.set("search_status", "");
		}
		if (! myrequest.getParameter("filename").equals("")) {
			mysession.set("search_filename", html.encodeHtmlEntities(myrequest.getParameter("filename")));
		} else if (myrequest.parameterExists("filename")) {
			mysession.set("search_filename", "");
		}
		if (! myrequest.getParameter("attribute").equals("")) {
			mysession.set("search_attribute", html.encodeHtmlEntities(Common.join(",", myrequest.getParameters("attribute"))));
		} else if (myrequest.parameterExists("attribute")) {
			mysession.set("search_attribute", "");
		}
		if (myrequest.getParameter("id").equals("data_grid_header")) {
			// ignore
		} else if (! myrequest.getParameter("id").equals("")) {
			mysession.set("search_id", html.encodeHtmlEntities(myrequest.getParameter("id")));
		} else if (myrequest.parameterExists("id")) {
			mysession.set("search_id", "");
		}
		if (myrequest.getParameter("pagesize").equals(" ")) {
			mysession.set("admin_pagesize", "");
		} else if (! myrequest.getParameter("pagesize").equals("")) {
			mysession.set("admin_pagesize", html.encodeHtmlEntities(myrequest.getParameter("pagesize")));
		}
		String SQLwhere = "where (1=1)";
		if (mysession.get("search_section").equals("content")) {
			SQLwhere += " AND (";
			SQLwhere += "contentclass<>'image'";
			SQLwhere += " AND ";
			SQLwhere += "contentclass<>'file'";
			SQLwhere += " AND ";
			SQLwhere += "contentclass<>'link'";
			SQLwhere += " AND ";
			SQLwhere += "contentclass<>'product'";
			SQLwhere += " )";
		} else if (mysession.get("search_section").equals("library")) {
			SQLwhere += " AND (";
			SQLwhere += "contentclass='image'";
			SQLwhere += " OR ";
			SQLwhere += "contentclass='file'";
			SQLwhere += " OR ";
			SQLwhere += "contentclass='link'";
			SQLwhere += " )";
		} else if (mysession.get("search_section").equals("ecommerce")) {
			SQLwhere += " AND (";
			SQLwhere += "contentclass='product'";
			SQLwhere += " )";
		} else if (! mysession.get("search_section").equals("")) {
			SQLwhere = Common.SQLwhere_in(SQLwhere, "contentclass", mysession.get("search_section"));
		}
		if (mysession.get("search_bundle").equals(" ")) {
			SQLwhere = Common.SQLwhere_in(SQLwhere, "contentbundle", "");
		} else if (! mysession.get("search_bundle").equals("")) {
			SQLwhere = Common.SQLwhere_in(SQLwhere, "contentbundle", mysession.get("search_bundle"));
		}
		if (mysession.get("search_group").equals(" ")) {
			SQLwhere = Common.SQLwhere_in(SQLwhere, "contentgroup", "");
		} else if (! mysession.get("search_group").equals("")) {
			SQLwhere = Common.SQLwhere_in(SQLwhere, "contentgroup", mysession.get("search_group"));
		}
		if (mysession.get("search_type").equals(" ")) {
			SQLwhere = Common.SQLwhere_in(SQLwhere, "contenttype", "");
		} else if (! mysession.get("search_type").equals("")) {
			SQLwhere = Common.SQLwhere_in(SQLwhere, "contenttype", mysession.get("search_type"));
		}
		if (mysession.get("search_version").equals(" ")) {
			SQLwhere = Common.SQLwhere_in(SQLwhere, "version", "");
		} else if (! mysession.get("search_version").equals("")) {
			SQLwhere = Common.SQLwhere_in(SQLwhere, "version", mysession.get("search_version"));
		}
		if (mysession.get("search_device").equals(" ")) {
			SQLwhere = Common.SQLwhere_in(SQLwhere, "device", "");
		} else if (! mysession.get("search_device").equals("")) {
			SQLwhere = Common.SQLwhere_in(SQLwhere, "device", mysession.get("search_device"));
		}
		if (mysession.get("search_usersegment").equals(" ")) {
			SQLwhere = Common.SQLwhere_in(SQLwhere, "usersegment", "");
		} else if (! mysession.get("search_usersegment").equals("")) {
			SQLwhere = Common.SQLwhere_in(SQLwhere, "usersegment", mysession.get("search_usersegment"));
		}
		if (mysession.get("search_usertest").equals(" ")) {
			SQLwhere = Common.SQLwhere_in(SQLwhere, "usertest", "");
		} else if (! mysession.get("search_usertest").equals("")) {
			SQLwhere = Common.SQLwhere_in(SQLwhere, "usertest", mysession.get("search_usertest"));
		}
		if (mysession.get("search_package").equals(" ")) {
			SQLwhere = Common.SQLwhere_in(SQLwhere, "contentpackage", "");
		} else if (! mysession.get("search_package").equals("")) {
			SQLwhere = Common.SQLwhere_in(SQLwhere, "contentpackage", mysession.get("search_package"));
		}
		if (! mysession.get("search_filename").equals("")) {
			SQLwhere = Common.SQLwhere_contains(db, myconfig, SQLwhere, "server_filename", mysession.get("search_filename"));
			SQLwhere = SQLwhere.replaceAll("%//", "").replaceAll("//%", "").replaceAll("%\\^", "").replaceAll("\\$%", "");
		}
		if (! mysession.get("search_id").equals("")) {
			SQLwhere = Common.SQLwhere_in(SQLwhere, "id", mysession.get("search_id"));
		}
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		if (mysession.get("search_status").equals("new")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "((" + db.is_blank("published") + ") and (" + db.is_blank("unpublished") + ") and ((" + db.is_blank("scheduled_unpublish") + ") or (scheduled_unpublish > " + db.quote(now) + ")))");
		} else if (mysession.get("search_status").equals("updated")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "((updated > published) and " + db.is_not_blank("published") + ")");
		} else if (mysession.get("search_status").equals("scheduled")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "((" + db.is_not_blank("scheduled_publish") + " and (scheduled_publish > " + db.quote(now) + ")) or (" + db.is_not_blank("scheduled_unpublish") + " and (scheduled_unpublish > " + db.quote(now) + ")))");
		} else if (mysession.get("search_status").equals("published")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "((published >= updated) and ((" + db.is_blank("scheduled_unpublish") + ") or (scheduled_unpublish > " + db.quote(now) + ")))");
		} else if (mysession.get("search_status").equals("unpublished")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "(((" + db.is_blank("published") + ") and (" + db.is_not_blank("unpublished") + ")) or ((" + db.is_not_blank("published") + ") and (" + db.is_not_blank("unpublished") + ") and (published < unpublished)))");
		} else if (mysession.get("search_status").equals("expiring")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "(" + db.is_not_blank("scheduled_unpublish") + " and ((scheduled_unpublish > published) or (" + db.is_blank("published") + ")) and (scheduled_unpublish > " + db.quote(now) + "))");
		} else if (mysession.get("search_status").equals("expired")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "(" + db.is_not_blank("scheduled_unpublish") + " and ((scheduled_unpublish > published) or (" + db.is_blank("published") + ")) and (scheduled_unpublish <= " + db.quote(now) + "))");
		} else if (mysession.get("search_status").equals("checkedout")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "(" + db.is_not_blank("checkedout") + ")");
		} else if (mysession.get("search_status").equals("workflow")) {
			SQLwhere = Common.SQLwhere(SQLwhere, "(" + db.is_not_blank("status") + ")");
		} else if (! mysession.get("search_status").equals("")) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "status", mysession.get("search_status"));
		}
		if (! mysession.get("search").equals("")) {
			String wildcard = "_";
			if (db.db_type(db.getDatabase()) == "access") {
				wildcard = "?";
			}
//			String[] searchwords = mysession.get("search").replaceAll("'", wildcard).replaceAll("\"", wildcard).split(" ");
			String[] searchwords = mysession.get("search").replaceAll("'", "''").split(" ");
			for (int i=0; i<searchwords.length; i++) {
				String searchword = searchwords[i];
				if (! mysession.get("search_attribute").equals("")) {
					SQLwhere += " AND (";
					String[] myattributes = mysession.get("search_attribute").split(",");
					for (int j=0; j<myattributes.length; j++) {
						if (j>0) SQLwhere += " OR ";
						SQLwhere += SQL_search(myattributes[j], searchword, db);
					}
					SQLwhere += " )";
				} else {
					SQLwhere += " AND (";
					SQLwhere += SQL_search("title", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("keywords", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("description", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("content", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("summary", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("metainfo", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("product_info", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("htmlheader", searchword, db);
					SQLwhere += " OR ";
					SQLwhere += SQL_search("server_filename", searchword, db);
					if (searchword.matches("^[0-9]+$")) {
						SQLwhere += " OR (id=" + searchword + ")";
					}
					SQLwhere += " )";
				}
			}
		}
		SQLwhere = SQLwhere_hide(mysession, myconfig, db, SQLwhere);

		String isUsersType = "((" + db.is_not_blank("users_type") + " and users_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (users_type=" + db.quote("*") + ") or (users_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isUsersGroup = "((" + db.is_not_blank("users_group") + " and users_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (users_group=" + db.quote("*") + ") or (users_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isDevelopersType = "((" + db.is_not_blank("developers_type") + " and developers_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (developers_type=" + db.quote("*") + ") or (developers_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isDevelopersGroup = "((" + db.is_not_blank("developers_group") + " and developers_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (developers_group=" + db.quote("*") + ") or (developers_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isCreatorsType = "((" + db.is_not_blank("creators_type") + " and creators_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (creators_type=" + db.quote("*") + ") or (creators_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isCreatorsGroup = "((" + db.is_not_blank("creators_group") + " and creators_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (creators_group=" + db.quote("*") + ") or (creators_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isEditorsType = "((" + db.is_not_blank("editors_type") + " and editors_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (editors_type=" + db.quote("*") + ") or (editors_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isEditorsGroup = "((" + db.is_not_blank("editors_group") + " and editors_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (editors_group=" + db.quote("*") + ") or (editors_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isPublishersType = "((" + db.is_not_blank("publishers_type") + " and publishers_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (publishers_type=" + db.quote("*") + ") or (publishers_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isPublishersGroup = "((" + db.is_not_blank("publishers_group") + " and publishers_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (publishers_group=" + db.quote("*") + ") or (publishers_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isAdministratorsType = "((" + db.is_not_blank("administrators_type") + " and administrators_type in (" + db.quote(mysession.get("usertype")) + ", " + Common.SQL_list_values(db, mysession.get("usertypes").split("\\|")) + ")) or (administrators_type=" + db.quote("*") + ") or (administrators_type=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String isAdministratorsGroup = "((" + db.is_not_blank("administrators_group") + " and administrators_group in (" + db.quote(mysession.get("usergroup")) + ", " + Common.SQL_list_values(db, mysession.get("usergroups").split("\\|")) + ")) or (administrators_group=" + db.quote("*") + ") or (administrators_group=" + db.quote("=") + " and created_by=" + db.quote(mysession.get("username")) + "))";
		String noUsersType = "" + db.is_blank("users_type") + "";
		String noUsersGroup = "" + db.is_blank("users_group") + "";
		String noDevelopersType = "" + db.is_blank("developers_type") + "";
		String noDevelopersGroup = "" + db.is_blank("developers_group") + "";
		String noCreatorsType = "" + db.is_blank("creators_type") + "";
		String noCreatorsGroup = "" + db.is_blank("creators_group") + "";
		String noEditorsType = "" + db.is_blank("editors_type") + "";
		String noEditorsGroup = "" + db.is_blank("editors_group") + "";
		String noPublishersType = "" + db.is_blank("publishers_type") + "";
		String noPublishersGroup = "" + db.is_blank("publishers_group") + "";
		String noAdministratorsType = "" + db.is_blank("administrators_type") + "";
		String noAdministratorsGroup = "" + db.is_blank("administrators_group") + "";

		String isUser = "";
		String isDeveloper = "";
		String isCreator = "";
		String isEditor = "";
		String isPublisher = "";
		String isAdministrator = "";
		if (myconfig.get(db, "accessrestrictions").equals("or")) {
			isUser = "((" + noUsersType + " and " + noUsersGroup + ") or (" + isUsersGroup + " or " + isUsersType + "))";
			isDeveloper = "((" + noDevelopersType + " and " + noDevelopersGroup + ") or (" + isDevelopersGroup + " or " + isDevelopersType + "))";
			isCreator = "((" + noCreatorsType + " and " + noCreatorsGroup + ") or (" + isCreatorsGroup + " or " + isCreatorsType + "))";
			isEditor = "((" + noEditorsType + " and " + noEditorsGroup + ") or (" + isEditorsGroup + " or " + isEditorsType + "))";
			isPublisher = "((" + noPublishersType + " and " + noPublishersGroup + ") or (" + isPublishersGroup + " or " + isPublishersType + "))";
			isAdministrator = "((" + noAdministratorsType + " and " + noAdministratorsGroup + ") or (" + isAdministratorsGroup + " or " + isAdministratorsType + "))";
		} else {
			isUser = "((" + noUsersType + " and " + noUsersGroup + ") or (" + noUsersType + " and " + isUsersGroup + ") or (" + isUsersType + " and " + noUsersGroup + ") or (" + isUsersGroup + " and " + isUsersType + "))";
			isDeveloper = "((" + noDevelopersType + " and " + noDevelopersGroup + ") or (" + noDevelopersType + " and " + isDevelopersGroup + ") or (" + isDevelopersType + " and " + noDevelopersGroup + ") or (" + isDevelopersGroup + " and " + isDevelopersType + "))";
			isCreator = "((" + noCreatorsType + " and " + noCreatorsGroup + ") or (" + noCreatorsType + " and " + isCreatorsGroup + ") or (" + isCreatorsType + " and " + noCreatorsGroup + ") or (" + isCreatorsGroup + " and " + isCreatorsType + "))";
			isEditor = "((" + noEditorsType + " and " + noEditorsGroup + ") or (" + noEditorsType + " and " + isEditorsGroup + ") or (" + isEditorsType + " and " + noEditorsGroup + ") or (" + isEditorsGroup + " and " + isEditorsType + "))";
			isPublisher = "((" + noPublishersType + " and " + noPublishersGroup + ") or (" + noPublishersType + " and " + isPublishersGroup + ") or (" + isPublishersType + " and " + noPublishersGroup + ") or (" + isPublishersGroup + " and " + isPublishersType + "))";
			isAdministrator = "((" + noAdministratorsType + " and " + noAdministratorsGroup + ") or (" + noAdministratorsType + " and " + isAdministratorsGroup + ") or (" + isAdministratorsType + " and " + noAdministratorsGroup + ") or (" + isAdministratorsGroup + " and " + isAdministratorsType + "))";
		}

		if ((mysession.get("search").equals("")) && (mysession.get("search_id").equals("")) && (mysession.get("search_section").equals("")) && (mysession.get("search_bundle").equals("")) && (mysession.get("search_group").equals("")) && (mysession.get("search_type").equals("")) && (mysession.get("search_version").equals("")) && (mysession.get("search_device").equals("")) && (mysession.get("search_usersegment").equals("")) && (mysession.get("search_usertest").equals("")) && (mysession.get("search_package").equals("")) && (mysession.get("search_status").equals("")) && (mysession.get("search_filename").equals("")) && (mysession.get("search_attribute").equals(""))) {
			SQLwhere = "where (1=0)";
		} else if ((myconfig.get(db, "search_section_all").equals("no")) && (mysession.get("search").equals("")) && (mysession.get("search_id").equals("")) && (mysession.get("search_bundle").equals("")) && (mysession.get("search_group").equals("")) && (mysession.get("search_type").equals("")) && (mysession.get("search_version").equals("")) && (mysession.get("search_device").equals("")) && (mysession.get("search_usersegment").equals("")) && (mysession.get("search_usertest").equals("")) && (mysession.get("search_package").equals("")) && (mysession.get("search_status").equals("")) && (mysession.get("search_filename").equals("")) && (mysession.get("search_attribute").equals(""))) {
			SQLwhere = "where (1=0)";
		}

		String SQLwhereEdit = "" + SQLwhere;
		if (! mysession.get("username").equals(myconfig.get(db, "superadmin"))) {
			SQLwhereEdit = Common.SQLwhere(SQLwhereEdit, "(" + isUser + " or " + isDeveloper + " or " + isCreator + " or " + isEditor + " or " + isPublisher + " or " + isAdministrator + ")");
		}
		String SQLwhereCreate = "" + SQLwhere;
		if (! mysession.get("username").equals(myconfig.get(db, "superadmin"))) {
			SQLwhereCreate = Common.SQLwhere(SQLwhereCreate, "(" + isCreator + " or " + isPublisher + " or " + isAdministrator + ")");
		}

		String SQL = "select count(*) as count from content " + SQLwhereEdit;
		record_count = db.query_value(SQL).intValue();

		String SQLorderDir = "";
		if (myrequest.getParameter("sort_dir").equals("DESC")) {
			SQLorderDir = " desc";
		}
		String SQLorder = "title" + SQLorderDir + ", version" + SQLorderDir + ", device" + SQLorderDir + ", usersegment" + SQLorderDir + ", usertest" + SQLorderDir + ", id" + SQLorderDir;
		if (myrequest.getParameter("sort_col").equals("column_title")) {
			// default;
		} else if (myrequest.getParameter("sort_col").equals("column_id")) {
			SQLorder = "id" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", device" + SQLorderDir + ", usersegment" + SQLorderDir + ", usertest" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_contentpackage")) {
			SQLorder = "contentpackage" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", device" + SQLorderDir + ", usersegment" + SQLorderDir + ", usertest" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_contentclass")) {
			SQLorder = "contentclass" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", device" + SQLorderDir + ", usersegment" + SQLorderDir + ", usertest" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_contentbundle")) {
			SQLorder = "contentbundle" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", device" + SQLorderDir + ", usersegment" + SQLorderDir + ", usertest" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_contentgroup")) {
			SQLorder = "contentgroup" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", device" + SQLorderDir + ", usersegment" + SQLorderDir + ", usertest" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_contenttype")) {
			SQLorder = "contenttype" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", device" + SQLorderDir + ", usersegment" + SQLorderDir + ", usertest" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_version")) {
			SQLorder = "version" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", device" + SQLorderDir + ", usersegment" + SQLorderDir + ", usertest" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_device")) {
			SQLorder = "device" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", device" + SQLorderDir + ", usersegment" + SQLorderDir + ", usertest" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_usersegment")) {
			SQLorder = "usersegment" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", device" + SQLorderDir + ", usersegment" + SQLorderDir + ", usertest" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_usertest")) {
			SQLorder = "usertest" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", device" + SQLorderDir + ", usersegment" + SQLorderDir + ", usertest" + SQLorderDir + ", id" + SQLorderDir;
		} else if (myrequest.getParameter("sort_col").equals("column_status")) {
			SQLorder = "updated" + SQLorderDir + ", published" + SQLorderDir + ", title" + SQLorderDir + ", version" + SQLorderDir + ", device" + SQLorderDir + ", usersegment" + SQLorderDir + ", usertest" + SQLorderDir + ", id" + SQLorderDir;
		}
		
		Content mypage = new Content(text);
		create_records = new Content(text);
		String offset = html.encodeHtmlEntities(myrequest.getParameter("offset"));
		String limit = html.encodeHtmlEntities(myrequest.getParameter("page_size"));
		if ((! offset.equals("")) && (! limit.equals(""))) {
			if (SQLwhereEdit.length() < 6) SQLwhereEdit += "      ";
			SQL = Common.SQLlimit(db.db_type(db.getDatabase()), "id", "*", "content", SQLwhereEdit.substring(6), SQLorder, (! SQLorderDir.equals("")), Integer.parseInt("0" + offset), Integer.parseInt("0" + limit));
			if (edits) {
				mypage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}
			SQL = "select * from content where 1=0";
			if (creates) {
				create_records.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}
		} else if ((mysession.get("admin_pagesize").equals("")) && (myrequest.getRequestURI().indexOf("searchreplace.jsp") < 0)) {
			SQL = "select * from content where 1=0";
			if (edits) {
				mypage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}
			SQL = "select * from content " + SQLwhereCreate + " order by " + SQLorder;
			if (creates) {
				create_records.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}
		} else {
			SQL = "select * from content " + SQLwhereEdit + " order by " + SQLorder;
			if (edits) {
				mypage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}
			SQL = "select * from content " + SQLwhereCreate + " order by " + SQLorder;
			if (creates) {
				create_records.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}
		}
		if ((creates) && (! edits)) {
			mypage = create_records;
		}
		handleCreateBlank(mypage, mysession, myrequest, myconfig, db);
		return mypage;
	}



	private String SQL_search(String column, String value, DB db) {
		String SQLexpression = "";
//		SQLexpression = "lower(" + column + ") like " + db.quote("%" + value.toLowerCase() + "%");
		if (db.db_type(db.getDatabase()).equals("mssql")) {
			SQLexpression += "(";
			SQLexpression += "(lower(substring(" + column + ",1,datalength(" + column + "))) like " + db.quote("%" + value.toLowerCase() + "%") + ")";
			SQLexpression += " or ";
			SQLexpression += "(lower(substring(" + column + ",1,datalength(" + column + "))) like " + db.quote("%" + html.decodeHtmlEntities(value).toLowerCase() + "%") + ")";
			SQLexpression += ")";
		} else if (db.db_type(db.getDatabase()).equals("db2")) {
			SQLexpression += "(";
			SQLexpression += "(lower(varchar(" + column + ",32000)) like " + db.quote("%" + value.toLowerCase() + "%") + ")";
			SQLexpression += " or ";
			SQLexpression += "(lower(varchar(" + column + ",32000)) like " + db.quote("%" + html.decodeHtmlEntities(value).toLowerCase() + "%") + ")";
			SQLexpression += ")";
		} else {
			SQLexpression += "(";
			SQLexpression += "(lower(" + column + ") like " + db.quote("%" + value.toLowerCase() + "%") + ")";
			SQLexpression += " or ";
			SQLexpression += "(lower(" + column + ") like " + db.quote("%" + html.decodeHtmlEntities(value).toLowerCase() + "%") + ")";
			SQLexpression += ")";
		}
		return SQLexpression;
	}



	public LinkedHashMap<String,String> doSearchReplaceStrings(Page mypage, String attribute, String search_for, String replace_with) {
		LinkedHashMap<String,String> searchreplace = new LinkedHashMap<String,String>();
		String content = "";
		if (attribute.equals("title")) {
			content = mypage.getTitle();
		} else if (attribute.equals("description")) {
			content = mypage.getDescription();
		} else if (attribute.equals("keywords")) {
			content = mypage.getKeywords();
		} else if (attribute.equals("metainfo")) {
			content = mypage.getMetaInfo();
		} else if (attribute.equals("summary")) {
			content = mypage.getSummary();
		} else if (attribute.equals("htmlheader")) {
			content = mypage.getHtmlHeader();
		} else if (attribute.equals("htmlbodyonload")) {
			content = mypage.getHtmlBodyOnload();
		} else {
			content = mypage.getContent();
		}
		if ((! content.equals("")) && (! search_for.equals(""))) {
			int contextlength = 20;
			for (int i=content.indexOf(search_for); i>=0; i=content.indexOf(search_for, i+1)) {
				int position = i;
				int position_line = Common.getLineBreakCount(content.substring(0, position));
				int position_char = position - Math.max(content.substring(0, position).lastIndexOf("\r"), content.substring(0, position).lastIndexOf("\n"));
				String pre = "";
				if (i >= contextlength) {
					pre = content.substring(i-contextlength, i);
				} else {
					pre = content.substring(0, i);
				}
				if (pre.lastIndexOf("\r") >= 0) pre = pre.substring(pre.lastIndexOf("\r")+1);
				if (pre.lastIndexOf("\n") >= 0) pre = pre.substring(pre.lastIndexOf("\n")+1);
				if (pre.lastIndexOf("\t") >= 0) pre = pre.substring(pre.lastIndexOf("\t")+1);
				String post = "";
				if (i + search_for.length() + contextlength < content.length()) {
					post = content.substring(i + search_for.length(), i + search_for.length() + contextlength);
				} else {
					post = content.substring(i + search_for.length());
				}
				if (post.indexOf("\r") >= 0) post = post.substring(0, post.indexOf("\r"));
				if (post.indexOf("\n") >= 0) post = post.substring(0, post.indexOf("\n"));
				if (post.indexOf("\t") >= 0) post = post.substring(0, post.indexOf("\t"));
				int position_offset = pre.length();
				searchreplace.put("" + position + "_" + position_line + "_" + position_char + "_" + position_offset + "_" + pre + search_for + post, pre + replace_with + post);
			}
		}
		return searchreplace;
	}



	public String doSearchReplace(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return "";
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
		error = "";
		String[] pids = myrequest.getParameters("id");
		if (pids.length > 0) {
			long[] ids = new long[pids.length];
			for (int i = 0; i < pids.length; i++) {
				ids[i] = Common.integernumber(pids[i]);
			}
			java.util.Arrays.sort(ids);
			for (int i = ids.length-1; i >= 0; i--) {
				String myid = "" + ids[i];
				if ((myrequest.parameterExists("id_"+myid)) && (myrequest.parameterExists("position_"+myid)) && (myrequest.parameterExists("search_"+myid)) && (myrequest.parameterExists("replace_"+myid)) && (! myrequest.getParameter("search_"+myid).equals(""))) {
					String contentid = myrequest.getParameter("id_"+myid);
					Page mypage = getPrimaryOnlyPageById(contentid, mysession, myrequest, myresponse, myconfig, db, false);
					if ((mypage.getId().equals(contentid)) && (mypage.getEditor())) {
						String search = myrequest.getParameter("search"); 
						String replace = myrequest.getParameter("replace"); 
						int position = Common.intnumber(myrequest.getParameter("position_"+myid));
						int position_line = Common.intnumber(myrequest.getParameter("position_line_"+myid));
						int position_char = Common.intnumber(myrequest.getParameter("position_char_"+myid));
						int position_offset = Common.intnumber(myrequest.getParameter("position_offset_"+myid));
						Fileupload filepost = new Fileupload(null, null, null);
						filepost.setCharset(""); // charset already handled by myrequest
						filepost.setParameter("id", contentid);
						if (myrequest.getParameter("attribute").equals("title")) {
							String mycontent = mypage.getTitle();
							if ((mycontent.length() >= position+search.length()) && (mycontent.substring(position, position+search.length()).equals(search))) {
								mycontent = mycontent.substring(0, position) + replace + mycontent.substring(position+search.length());
							} else {
								error = "<div>" + mypage.getTitle() + " (" + mypage.getId() + ") " + position_line + ":" + position_char + "</div>" + "\r\n" + error;
							}
							filepost.setParameter("title", mycontent);
						} else if (myrequest.getParameter("attribute").equals("content")) {
							String mycontent = mypage.getContent();
							if ((mycontent.length() >= position+search.length()) && (mycontent.substring(position, position+search.length()).equals(search))) {
								mycontent = mycontent.substring(0, position) + replace + mycontent.substring(position+search.length());
							} else {
								error = "<div>" + mypage.getTitle() + " (" + mypage.getId() + ") " + position_line + ":" + position_char + "</div>" + "\r\n" + error;
							}
							filepost.setParameter("content", mycontent);
						} else if (myrequest.getParameter("attribute").equals("summary")) {
							String mycontent = mypage.getSummary();
							if ((mycontent.length() >= position+search.length()) && (mycontent.substring(position, position+search.length()).equals(search))) {
								mycontent = mycontent.substring(0, position) + replace + mycontent.substring(position+search.length());
							}
							filepost.setParameter("summary", mycontent);
						} else if (myrequest.getParameter("attribute").equals("description")) {
							String mycontent = mypage.getDescription();
							if ((mycontent.length() >= position+search.length()) && (mycontent.substring(position, position+search.length()).equals(search))) {
								mycontent = mycontent.substring(0, position) + replace + mycontent.substring(position+search.length());
							}
							filepost.setParameter("description", mycontent);
						} else if (myrequest.getParameter("attribute").equals("keywords")) {
							String mycontent = mypage.getKeywords();
							if ((mycontent.length() >= position+search.length()) && (mycontent.substring(position, position+search.length()).equals(search))) {
								mycontent = mycontent.substring(0, position) + replace + mycontent.substring(position+search.length());
							}
							filepost.setParameter("keywords", mycontent);
						} else if (myrequest.getParameter("attribute").equals("metainfo")) {
							String mycontent = mypage.getMetaInfo();
							if ((mycontent.length() >= position+search.length()) && (mycontent.substring(position, position+search.length()).equals(search))) {
								mycontent = mycontent.substring(0, position) + replace + mycontent.substring(position+search.length());
							}
							filepost.setParameter("metainfo", mycontent);
						} else if (myrequest.getParameter("attribute").equals("htmlheader")) {
							String mycontent = mypage.getHtmlHeader();
							if ((mycontent.length() >= position+search.length()) && (mycontent.substring(position, position+search.length()).equals(search))) {
								mycontent = mycontent.substring(0, position) + replace + mycontent.substring(position+search.length());
							}
							filepost.setParameter("htmlheader", mycontent);
						} else if (myrequest.getParameter("attribute").equals("htmlbodyonload")) {
							String mycontent = mypage.getHtmlBodyOnload();
							if ((mycontent.length() >= position+search.length()) && (mycontent.substring(position, position+search.length()).equals(search))) {
								mycontent = mycontent.substring(0, position) + replace + mycontent.substring(position+search.length());
							}
							filepost.setParameter("htmlbodyonload", mycontent);
						}
						String myerror = error;
						HardCore.Request myupdaterequest = new HardCore.Request(null);
						myupdaterequest.setParameter("id", filepost.getParameter("id"));
						doUpdate(server, DOCUMENT_ROOT, mysession, myupdaterequest, myresponse, myconfig, db, filepost);
						error = myerror + error;
					}
				}
			}
		}
		return error;
	}



	public Page doCheckin(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return new Page(text);
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Page(text);
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		Page mypage = getPage(server, mysession, myrequest, myresponse, myconfig, db, null, false);
		mypage.checkin(db, mysession.get("username"), myconfig.get(db, "superadmin"));
		if (myconfig.get(db, "use_archive").equals("auto-on-checkin")) {
			doArchive(server, mypage, mysession, myrequest, myresponse, myconfig, db);
		}
		return mypage;
	}



	public Page doCheckout(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return new Page(text);
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Page(text);
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		Page mypage = getPage(server, mysession, myrequest, myresponse, myconfig, db, null, false);
		Workflow workflow = new Workflow(text);
		if (((mypage.getEditor()) && (mypage.getScheduledPublish().equals(""))) || (mypage.getPublisher()) && ((! myconfig.get(db, "use_workflow").equals("yes")) || (mypage.getAdministrator()) || (workflow.permissions(db, mypage.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (mypage.getStatusBy().equals(mysession.get("username"))))))) {
			mypage.checkout(db, mysession.get("username"));
		}
		return mypage;
	}



	public Page doHeatmap(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return new Page(text);
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Page(text);
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		Page mypage = getPage(server, mysession, myrequest, myresponse, myconfig, db, null, false);
		if ((! myrequest.getParameter("id").equals("")) && (myrequest.getParameter("log").equals("delete"))) {
			db.execute("delete from heatmaps where id=" + db.quote(Common.SQL_clean(myrequest.getParameter("id"))));
		} else if ((myrequest.parameterExists("log")) && (myrequest.parameterExists("align"))) {
			mypage.update_heatmap(db, myconfig, myrequest.getParameter("log"), myrequest.getParameter("align"));
		} else if (myrequest.parameterExists("log")) {
			mypage.update_heatmap(db, myconfig, myrequest.getParameter("log"), null);
		} else if (myrequest.parameterExists("align")) {
			mypage.update_heatmap(db, myconfig, null, myrequest.getParameter("align"));
		}
		return mypage;
	}



	public HashMap<String,String> doEmail(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return new HashMap<String,String>();
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new HashMap<String,String>();
		HashMap<String,String> email = new HashMap<String,String>();
		if (License.valid(db, myconfig, "community")) {
			if (! myconfig.get(db, "email_admin_users_type").equals("")) {
				accesspermission = RequireUser.AdministratorUsertype(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myconfig.get(db, "email_admin_users_type"), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
				if (! accesspermission) return new HashMap<String,String>();
			}
			if (! myconfig.get(db, "email_admin_users_group").equals("")) {
				accesspermission = RequireUser.AdministratorUsergroup(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myconfig.get(db, "email_admin_users_group"), mysession.get("usergroup"), mysession.get("usergroups"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
				if (! accesspermission) return new HashMap<String,String>();
			}

			email.put("from", "");
			if (myrequest.parameterExists("from")) {
				email.put("from", myrequest.getParameter("from"));
			} else {
				email.put("from", myconfig.get(db, "contact_form_recipient"));
			}
			email.put("to", "");
			if (myrequest.parameterExists("to")) {
				email.put("to", myrequest.getParameter("to"));
			} else {
				email.put("to", myconfig.get(db, "contact_form_recipient"));
			}
			email.put("cc", "");
			if (myrequest.parameterExists("cc")) {
				email.put("cc", myrequest.getParameter("cc"));
			} else if (myrequest.getParameter("action").equals("")) {
				email.put("cc", mysession.get("email"));
			}
			email.put("bcc", "");
			if (myrequest.parameterExists("bcc")) {
				email.put("bcc", myrequest.getParameter("bcc"));
			} else if (myrequest.getParameter("action").equals("")) {
				String SQLwhere = "";
				String SQLwhere_in = "";
				String[] ids = myrequest.getParameters("id");
				if (ids.length > 0) {
					for (int i = 0; i < ids.length; i++) {
						String id = ids[i];
						if (! SQLwhere_in.equals("")) {
							SQLwhere_in += ",";
						}
						SQLwhere_in += "" + id;
					}
					SQLwhere += "id in (" + SQLwhere_in + ")";
				} else {
					SQLwhere += "id in (0)";
				}
				String SQL = "select id, name, email from users where " + SQLwhere + " order by name, email, id";
				User user = new User(text);
				user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
				while (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
//					if ((! user.getName().equals("")) && (! user.getEmail().equals(""))) {
//						email.put("bcc", (String)email.get("bcc") + "" + user.getName() + " &lt;" + user.getEmail() + "&gt;" + "\r\n");
//					} else if (! user.getEmail().equals("")) {
						email.put("bcc", (String)email.get("bcc") + "" + user.getEmail() + "\r\n");
//					}
				}
			}
			email.put("subject", "");
			email.put("content", "");
			email.put("content_plaintext", "");
			if (! myrequest.getParameter("content_plaintext").equals("")) {
				email.put("content_plaintext", myrequest.getParameter("content_plaintext"));
			}
			email.put("stylesheet", "");
			email.put("style", "");
			if ((myrequest.getParameter("action").equals("")) && (! myrequest.getParameter("content_id").equals(""))) {
				Page page = new Page(text);
				page.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("content_id"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				page.setBody(page.getContent());
				page.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, page.getId(), "content_public", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		
				page.setDisplayCurrency(db, mysession.get("currency"), myconfig.get(db, "default_currency"));
				page.parse_output_product(server, mysession, myrequest, myresponse, db, myconfig);
		
				Shopcart shopcart = new Shopcart(text);
				shopcart.read(mysession, db, myconfig);
				shopcart.create(mysession);
				shopcart.calculate(server, mysession, myrequest, myresponse, mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"));
				Page shopcartitem = new Page(text);
				shopcartitem.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_shopcart_entry"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				page.parse_output_shopcart(shopcart, "", "", server, myrequest, myresponse, mysession, "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), db, myconfig, shopcartitem, false);
		
				Page guestbookentry = new Page(text);
				guestbookentry.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_guestbook_entry"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				page.parse_output_guestbook(db, myconfig, guestbookentry, mysession, mysession.get("version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"));
		
				if (page.getUser()) {
					email.put("subject", page.getTitle());
					email.put("content", page.getBody());
					email.put("stylesheet", page.getStyleSheet());
					if (page.getStyleSheet().equals("0")) {
						email.put("stylesheet", "");
						email.put("style", "");
					} else if (page.getStyleSheet().equals("")) {
						Content stylesheet_content = new Content(text);
						stylesheet_content.public_read(db, myconfig, myconfig.get(db, "default_stylesheet"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
						email.put("stylesheet", "/stylesheet.jsp?id=" + myconfig.get(db, "default_stylesheet"));
						email.put("style", stylesheet_content.getContent());
					} else {
						Content stylesheet_content = new Content(text);
						stylesheet_content.public_read(db, myconfig, page.getStyleSheet(), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
						email.put("stylesheet", "/stylesheet.jsp?id=" + page.getStyleSheet());
						email.put("style", stylesheet_content.getContent());
					}
				}
			} else {
				if (! myrequest.getParameter("subject").equals("")) {
					email.put("subject", myrequest.getParameter("subject"));
				}
				if (! myrequest.getParameter("content").equals("")) {
					email.put("content", myrequest.getParameter("content"));
				}
				if (! myrequest.getParameter("stylesheet").equals("")) {
					email.put("stylesheet", myrequest.getParameter("stylesheet"));
				}
				if (! myrequest.getParameter("style").equals("")) {
					email.put("style", myrequest.getParameter("style"));
				}
			}
			if (! myrequest.getParameter("action").equals("")) {
				HashMap<String,String> requestForm = Email.getForm(myrequest);
				Email.send_email(text, requestForm, (String)email.get("subject"), (String)email.get("content"), (String)email.get("content_plaintext"), (String)email.get("from"), (String)email.get("to"), (String)email.get("cc"), (String)email.get("bcc"), (String)email.get("stylesheet"), (String)email.get("style"), myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
			}
//			mysession.set("admin_contentbundle", "");
//			mysession.set("admin_contentgroup", "");
//			mysession.set("admin_contenttype", "");
//			mysession.set("admin_version", "");
//			mysession.set("admin_contentclass", "page");
//			email.put("pages", getIndex(mysession, myrequest, myresponse, myconfig, db));
//			mysession.set("admin_contentclass", "product");
//			email.put("products", getIndex(mysession, myrequest, myresponse, myconfig, db));
		}
		return email;
	}



	public Page doRegister(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String database) throws Exception {
		return doRegister(server, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, null, database);
	}
	public Page doRegister(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite, String database) throws Exception {
		if (mywebsite == null) mywebsite = new Website(text);
		Fileupload filepost = new Fileupload(null, null, null);
		error = "";
		Page mypage = null;
		User user = new User(text);
		if (License.valid(db, myconfig, "community") && (myrequest.getMethod().equals("POST"))) {
			if ((! myconfig.get(db, "captcha").equals("")) && (myconfig.get(db, "captcha_register").equals("yes")) && ((myconfig.get(db, "captcha_user").equals("yes")) || (mysession.get("username").equals("")))) {
				mysession.set("captcha_error", "");
				CAPTCHA mycaptcha = new CAPTCHA(text);
				if (! mycaptcha.valid(mysession, server, myrequest, myconfig, db)) {
					error += "<br>" + mycaptcha.error;
					mysession.set("captcha_error", mycaptcha.error);
					mysession.set("POST", Common.serialize(myrequest));
					if (! mysession.get("captcha_url").equals("")) {
						myresponse.sendRedirect(Common.crlf_clean(mysession.get("captcha_url")));
					}
					return new Page();
				}
			}
			if ((myconfig.get(db, "authorize_register").equals("yes")) && (! Common.authorized(mysession, myrequest, "register"))) {
				return new Page();
			}
			if (License.valid(db, myconfig, "enterprise")) {
				if (Common.fileExists(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/validateuser.jsp"))) {
					String validation = "" + http.post(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort(), "/" + text.display("adminpath") + "/api/validateuser.jsp?request=" + myrequest.getRequestURI(), myrequest.getRequest());
					validation = validation.replaceAll("^OK$","").replaceAll("^OK:ALERT:","").replaceAll("^OK:CONFIRM:","").replaceAll("^OK:","").replaceAll("^ERROR:ALERT:","").replaceAll("^ERROR:CONFIRM:","").replaceAll("^ERROR:","");
					if ((validation != null) && (! validation.equals(""))) {
						error += validation;
					}
				}
			}
			String email = "";
			if (! myrequest.getParameter("email").equals("")) {
				email = html.encodeHtmlEntities(myrequest.getParameter("email"));
			} else {
				error += "<br>" + text.display("register.error.email");
			}
			String from = "";
			if ((! myrequest.getParameter("from").equals("")) && (myconfig.get(db, "contact_form_recipients").indexOf(myrequest.getParameter("from"))>=0)) {
				from = html.encodeHtmlEntities(myrequest.getParameter("from"));
			} else {
				from = myconfig.get(db, "contact_form_recipient");
			}
			String cc = "";
			if ((! myrequest.getParameter("cc").equals("")) && (myconfig.get(db, "contact_form_recipients").indexOf(myrequest.getParameter("cc"))>=0)) {
				cc = html.encodeHtmlEntities(myrequest.getParameter("cc"));
			}
			String bcc = "";
			if ((! myrequest.getParameter("bcc").equals("")) && (myconfig.get(db, "contact_form_recipients").indexOf(myrequest.getParameter("bcc"))>=0)) {
				bcc = html.encodeHtmlEntities(myrequest.getParameter("bcc"));
			}
			String name = "";
			if (! myrequest.getParameter("name").equals("")) {
				name = html.encodeHtmlEntities(myrequest.getParameter("name"));
			} else {
				name = html.encodeHtmlEntities(myrequest.getParameter("email"));
			}
			String username = "";
			if (! myrequest.getParameter("username").equals("")) {
				username = html.encodeHtmlEntities(myrequest.getParameter("username"));
			} else {
				username = html.encodeHtmlEntities(myrequest.getParameter("email"));
			}
			String password = "";
//TODO: only use password parameter if config option allows this - else generate password
			if (! myrequest.getParameter("password").equals("")) {
				password = html.encodeHtmlEntities(myrequest.getParameter("password"));
			}
			if (password.equals("")) {
				for (int i=1; i<=8; i++) {
					password = "" + password + (char)('a'+Integer.parseInt(Common.numberformat("" + Math.random()*25, 0)));
				}
			}
			String user_id = "";
			if (! myrequest.getParameter("user_id").equals("")) {
				user_id = html.encodeHtmlEntities(myrequest.getParameter("user_id"));
			} else {
//TODO: user_id = default user registration user id config option
			}
			String content_id = "";
			if (! myrequest.getParameter("content_id").equals("")) {
				content_id = html.encodeHtmlEntities(myrequest.getParameter("content_id"));
			} else {
//TODO: content_id = default user registration content id config option
			}

			if ((! email.equals("")) && (! username.equals("")) && (! password.equals("")) && (! user_id.equals("")) && (error.equals(""))) {
				user = new User(text);
				user.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, username);
				if (user.getUsername().equals(username)) {
					error += "<br>" + text.display("register.error.exists");
					user = new User(text);
				} else {
					user = new User(text);
					user.read(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, user_id);
					if (user.getCreator()) {
						String scheduled_publish = user.getScheduledPublish();
						String scheduled_notify = user.getScheduledNotify();
						String scheduled_unpublish = user.getScheduledUnpublish();
						String scheduled_publish_email = user.getScheduledPublishEmail();
						String scheduled_notify_email = user.getScheduledNotifyEmail();
						String scheduled_unpublish_email = user.getScheduledUnpublishEmail();
						user.setAdministrator(false);
						user.getForm(myrequest, myconfig, db);
						user.setEmail(email);
						user.setUsername(username);
						user.setPassword(password);
						user.setName(name);
						String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
						user.setCreated(timestamp);
						user.setCreatedBy(username);
						user.setUpdated(timestamp);
						user.setUpdatedBy(username);
						if (! scheduled_publish.equals("")) user.setScheduledPublish(scheduled_publish);
						if (! scheduled_notify.equals("")) user.setScheduledNotify(scheduled_notify);
						if (! scheduled_unpublish.equals("")) user.setScheduledUnpublish(scheduled_unpublish);
						if (! scheduled_publish_email.equals("")) user.setScheduledPublishEmail(scheduled_publish_email);
						if (! scheduled_notify_email.equals("")) user.setScheduledNotifyEmail(scheduled_notify_email);
						if (! scheduled_unpublish_email.equals("")) user.setScheduledUnpublishEmail(scheduled_unpublish_email);
						user.create(db);	//método cria o usuário no Banco de Dados.

						error += "<br>" + text.display("register.created");

						/*Obs: Desativado - Abaixo o Sistema Asbru realiza a criação da Personal Page do usuário.
						if (! content_id.equals("")) {
							mypage = getPublishedPageById(content_id, server, mysession, myrequest, myresponse, myconfig, db);
							mypage.setServerFilename("");
							mypage.setTitle(name);
							String content = mypage.getContent();
							content = content.replaceAll("@@@email@@@", user.getEmail().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							content = content.replaceAll("@@@name@@@", user.getName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							content = content.replaceAll("@@@username@@@", user.getUsername().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							content = content.replaceAll("@@@password@@@", user.getPassword().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							content = content.replaceAll("@@@user_activation@@@", user.getScheduledPublish().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							content = content.replaceAll("@@@user_notification@@@", user.getScheduledNotify().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							content = content.replaceAll("@@@user_expiration@@@", user.getScheduledUnpublish().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							Enumeration parameternames = myrequest.getParameterNames();
							while (parameternames.hasMoreElements()) {
								String inputname = "" + parameternames.nextElement();
								String inputvalue = myrequest.getParameter(inputname);
								inputvalue = inputvalue.replaceAll("<", "&lt;");
								inputvalue = inputvalue.replaceAll(">", "&gt;");
								inputvalue = inputvalue.replaceAll("\r", "");
								inputvalue = inputvalue.replaceAll("\n", "<br>");
								content = content.replaceAll("@@@" + inputname + "@@@", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							}
							mypage.setContent(content);
							if (mypage.getCreator()) {
								mypage.setCreated(timestamp);
								mypage.setCreatedBy(username);
								mypage.setUpdated(timestamp);
								mypage.setUpdatedBy(username);
								if ((! myrequest.getParameter("publish").equals("")) && (mypage.getPublisher())) {
									if (myrequest.getParameter("scheduled_publish").compareTo(timestamp) <= 0) {
										mypage.setRequestedPublish("");
										mypage.setRequestedUnpublish("");
										mypage.setScheduledPublish("");
										mypage.setPublished(timestamp);
										mypage.setPublishedBy(username);
										mypage.setUnpublished("");
										mypage.setUnpublishedBy("");
									}
								} else {
									mypage.setScheduledPublish("");
									mypage.setScheduledUnpublish("");
									mypage.setPublished("");
									mypage.setPublishedBy("");
								}

								mypage.create(db, myconfig, "content", "id");
								handleStaticAddress(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db, "");
								handleArchive(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
								handleSchedule(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
								handleSave(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
								handlePublish(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
								String mypageid = mypage.getId();
								mypage = getPublishedPageById(mypage.getId(), server, mysession, myrequest, myresponse, myconfig, db);
								mypage.outputStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
								mypage.exportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
								mypage.read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", "", "", "", "", "", "", "", db, myconfig, mypageid, "content", "id", "", myconfig.get(db, "default_version"), "", "", "", "", myconfig.get(db, "default_template"), "", myconfig.get(db, "default_stylesheet"), "", myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));

								String subject = "";
								String body = "";
								Page notification = new Page(text);
								if (! myrequest.getParameter("email_template").equals("")) {
									notification.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("email_template"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
									subject = "" + notification.getTitle() + " " + mypage.getTitle();
									body = "" + notification.getContent();
								} else if (! myconfig.get(db, "default_publish_ready").equals("")) {
									notification.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_publish_ready"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
									subject = "" + notification.getTitle() + " " + mypage.getTitle();
									body = "" + notification.getContent();
								}
								if (body.equals("")) {
									subject = "User account created: " + username + " - " + mypage.getTitle();
									body = "<p>The user '@@@username@@@' has been created with the content item '@@@title@@@'.</p>" + "\r\n" + "\r\n";
									body += "<p>Preview:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@preview@@@\">@@@preview@@@</a>" + "\r\n";
									body += "<p>Update/publish:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@update@@@\">@@@update@@@</a>" + "\r\n";
									body += "<p>Delete:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@delete@@@\">@@@delete@@@</a>" + "\r\n";
								}
								body = body.replaceAll("@@@id@@@", mypage.getId());
								body = body.replaceAll("@@@class@@@", mypage.getContentClass());
								body = body.replaceAll("@@@package@@@", mypage.getContentPackage());
								body = body.replaceAll("@@@bundle@@@", mypage.getContentBundle());
								body = body.replaceAll("@@@group@@@", mypage.getContentGroup());
								body = body.replaceAll("@@@type@@@", mypage.getContentType());
								body = body.replaceAll("@@@status@@@", mypage.getStatus().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
								if (! myrequest.getParameter("comments").equals("")) {
									body = body.replaceAll("@@@comments@@@", myrequest.getParameter("comments").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
								} else {
									body = body.replaceAll("@@@comments@@@", filepost.getParameter("comments").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
								}
								body = body.replaceAll("@@@title@@@", mypage.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
								body = body.replaceAll("@@@content@@@", mypage.getContent().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
								body = body.replaceAll("@@@summary@@@", mypage.getSummary().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
								body = body.replaceAll("@@@revision@@@", mypage.getRevision().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")).replaceAll("\\r\\n","<br>\r\n");
								body = body.replaceAll("@@@name@@@", user.getName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
								body = body.replaceAll("@@@email@@@", user.getEmail().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
								body = body.replaceAll("@@@username@@@", user.getUsername().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
								body = body.replaceAll("@@@password@@@", user.getPassword().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
								body = body.replaceAll("@@@user_activation@@@", user.getScheduledPublish().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
								body = body.replaceAll("@@@user_notification@@@", user.getScheduledNotify().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
								body = body.replaceAll("@@@user_expiration@@@", user.getScheduledUnpublish().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
								body = body.replaceAll("@@@preview@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + mypage.getContentClass() + ".jsp?id=" + mypage.getId() + "&mode=preview&version=&");
								body = body.replaceAll("@@@view@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/view.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
								body = body.replaceAll("@@@update@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/update.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
								body = body.replaceAll("@@@delete@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/delete.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
								body = mypage.parse_output_replace_inputs(myrequest, filepost, body);
								handleNotification(subject, body, server, mypage, null, filepost, myrequest, myresponse, mysession, myconfig, db);
							}
						}*/
						//Abaixo sistema Asbru realiza o processo de confirmação de registro do usuário
						if ((! myconfig.get(db, "default_register_confirmation_page").equals("")) || ((mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) && (! mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_register_confirmation_page").equals("")))) {
							Page confirmation = new Page(text);
							if ((mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) && (! mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_login").equals(""))) {
								confirmation.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_register_confirmation_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
							} else {
								confirmation.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_register_confirmation_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
							}
							String body = confirmation.getBody();
							body = body.replaceAll("@@@name@@@", user.getName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							body = body.replaceAll("@@@email@@@", user.getEmail().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							body = body.replaceAll("@@@username@@@", user.getUsername().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							body = body.replaceAll("@@@password@@@", user.getPassword().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							body = body.replaceAll("@@@user_activation@@@", user.getScheduledPublish().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							body = body.replaceAll("@@@user_notification@@@", user.getScheduledNotify().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							body = body.replaceAll("@@@user_expiration@@@", user.getScheduledUnpublish().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							body = body.replaceAll("@@@personalpage@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/personal/?" + user.getUsername().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							body = body.replaceAll("@@@personaladmin@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/personal/admin.jsp");
							confirmation.setBody(body);
							confirmation.parse_output_register("", "", "", "", "", user);
							String stylesheet = confirmation.getStyleSheet();
							String style = "";
							if (confirmation.getStyleSheet().equals("0")) {
								stylesheet = "";
								style = "";
							} else if (confirmation.getStyleSheet().equals("")) {
								Content stylesheet_content = new Content(text);
								stylesheet_content.public_read(db, myconfig, myconfig.get(db, "default_stylesheet"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
								stylesheet = "/stylesheet.jsp?id=" + myconfig.get(db, "default_stylesheet");
								style = stylesheet_content.getContent();
							} else {
								Content stylesheet_content = new Content(text);
								stylesheet_content.public_read(db, myconfig, confirmation.getStyleSheet(), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
								stylesheet = "/stylesheet.jsp?id=" + confirmation.getStyleSheet();
								style = stylesheet_content.getContent();
							}
							HashMap<String,String> requestForm = Email.getForm(myrequest);
							Email.send_email(text, requestForm, confirmation.getTitle(), confirmation.getBody(), "", from, email, cc, bcc, stylesheet, style, myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
						}

						if ((! myconfig.get(db, "default_register_notification_page").equals("")) || ((mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) && (! mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_register_notification_page").equals("")))) {
							Page notification = new Page(text);
							if ((mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) && (! mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_login").equals(""))) {
								notification.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_register_notification_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
							} else {
								notification.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_register_notification_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
							}
							String body = notification.getContent();
							body = body.replaceAll("@@@name@@@", user.getName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							body = body.replaceAll("@@@email@@@", user.getEmail().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							body = body.replaceAll("@@@username@@@", user.getUsername().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							body = body.replaceAll("@@@password@@@", user.getPassword().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							body = body.replaceAll("@@@user_activation@@@", user.getScheduledPublish().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							body = body.replaceAll("@@@user_notification@@@", user.getScheduledNotify().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							body = body.replaceAll("@@@user_expiration@@@", user.getScheduledUnpublish().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							body = body.replaceAll("@@@personalpage@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/personal/?" + user.getUsername().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							body = body.replaceAll("@@@personaladmin@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/personal/admin.jsp");
							notification.setContent(body);
							notification.parse_output_register("", "", "", "", "", user);
							String subject = notification.getTitle() + " " + email;
							body = notification.getContent();
							sendUserNotification(subject, body, server, user, filepost, myrequest, myresponse, mysession, myconfig, db);
						}
					} else {
						user = new User(text);
					}
				}

				if ((! myrequest.getParameter("username").equals("")) && (! myrequest.getParameter("password").equals(""))) {
					Login.login(text, null, "/login.jsp", "-", server, mysession, myrequest, myresponse, myconfig, db, myconfig.get(db, "require_ssl_user"), database);
				}

				if ((myrequest.getParameter("redirect").startsWith("http://")) || (myrequest.getParameter("redirect").startsWith("https://"))) {
					if ((! myconfig.get(db, "redirect_urls").trim().equals("")) && (! Common.startsWithAnyOf(Common.crlf_clean(myrequest.getParameter("redirect")), myconfig.get(db, "redirect_urls")))) {
						// ignore
					} else {
						myresponse.sendRedirect(Common.crlf_clean(myrequest.getParameter("redirect")));
					}
				} else if (! myrequest.getParameter("redirect").equals("")) {
					myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + Common.crlf_clean(myrequest.getParameter("redirect")));
				}
			}
		}
		UCbrowseWebsite browseWebsite = new UCbrowseWebsite(text);
		mypage = browseWebsite.getPage(server, mysession, myrequest, myresponse, myconfig, db, mywebsite);
		String mybody = mypage.getBody();
		mybody = mybody.replaceAll("@@@name@@@", user.getName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		mybody = mybody.replaceAll("@@@email@@@", user.getEmail().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		mybody = mybody.replaceAll("@@@username@@@", user.getUsername().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		mybody = mybody.replaceAll("@@@password@@@", user.getPassword().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		mybody = mybody.replaceAll("@@@user_activation@@@", user.getScheduledPublish().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		mybody = mybody.replaceAll("@@@user_notification@@@", user.getScheduledNotify().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		mybody = mybody.replaceAll("@@@user_expiration@@@", user.getScheduledUnpublish().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		mybody = mybody.replaceAll("@@@personalpage@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/personal/?" + user.getUsername().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		mybody = mybody.replaceAll("@@@personaladmin@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/personal/admin.jsp");
		mypage.setBody(mybody);
		mypage.parse_output_register(error, html.encodeHtmlEntities(myrequest.getParameter("email")), html.encodeHtmlEntities(myrequest.getParameter("username")), html.encodeHtmlEntities(myrequest.getParameter("password")), html.encodeHtmlEntities(myrequest.getParameter("name")), user);
		mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_doctype");
		return mypage;
	}



	public Page doPost(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		error = "";
		Page mypage = new Page(text);
		Fileupload filepost = new Fileupload(null, null, null);
		filepost = getFileupload(DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, 32);

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

		String page_id = "";
		mypage = getPage(server, mysession, myrequest, myresponse, myconfig, db, filepost, true);
		if (condition_true && (invalid.length == 0) && License.valid(db, myconfig, "community")) {
			mypage.setPublished("");
			mypage.setServerFilename("");
			if (! myrequest.getParameter("title").equals("")) {
				mypage.setTitle(myrequest.getParameter("title"));
			} else {
				mypage.setTitle(filepost.getParameter("title"));
			}
			mypage.setTitle(mypage.getTitle().replaceAll("&", "&amp;"));
			mypage.setTitle(mypage.getTitle().replaceAll("<", "&lt;"));
			mypage.setTitle(mypage.getTitle().replaceAll(">", "&gt;"));
			mypage.setTitle(mypage.getTitle().replaceAll("\r", ""));
			mypage.setTitle(mypage.getTitle().replaceAll("\n", "<br>"));
			mypage.setTitle(mypage.getTitle().replaceAll("@@@", "<nobr>@@@</nobr>"));
			mypage.setTitle(mypage.getTitle().replaceAll("###", "<nobr>###</nobr>"));
			String content = mypage.getContent();
			String title = mypage.getTitle();
			String summary = mypage.getSummary();
			String metainfo = mypage.getMetaInfo();
			String author = mypage.getAuthor();
			String keywords = mypage.getKeywords();
			String description = mypage.getDescription();
			Enumeration parameternames = myrequest.getParameterNames();
			while (parameternames.hasMoreElements()) {
				String inputname = "" + parameternames.nextElement();
				String inputvalue = myrequest.getParameter(inputname);
				inputvalue = inputvalue.replaceAll("&", "&amp;");
				inputvalue = inputvalue.replaceAll("<", "&lt;");
				inputvalue = inputvalue.replaceAll(">", "&gt;");
				inputvalue = inputvalue.replaceAll("\r", "");
				inputvalue = inputvalue.replaceAll("\n", "<br>");
				inputvalue = inputvalue.replaceAll("@@@", "<nobr>@@@</nobr>");
				inputvalue = inputvalue.replaceAll("###", "<nobr>###</nobr>");
				content = content.replaceAll("@@@" + inputname + "@@@", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				title = title.replaceAll("@@@" + inputname + "@@@", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				summary = summary.replaceAll("@@@" + inputname + "@@@", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				metainfo = metainfo.replaceAll("@@@" + inputname + "@@@", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				author = author.replaceAll("@@@" + inputname + "@@@", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				keywords = keywords.replaceAll("@@@" + inputname + "@@@", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				description = description.replaceAll("@@@" + inputname + "@@@", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				content = content.replaceAll("###" + inputname + "###", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				title = title.replaceAll("###" + inputname + "###", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				summary = summary.replaceAll("###" + inputname + "###", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				metainfo = metainfo.replaceAll("###" + inputname + "###", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				author = author.replaceAll("###" + inputname + "###", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				keywords = keywords.replaceAll("###" + inputname + "###", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				description = description.replaceAll("###" + inputname + "###", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			}
			Iterator filepostparameternames = filepost.getParameterNames();
			while (filepostparameternames.hasNext()) {
				String inputname = "" + filepostparameternames.next();
				String inputvalue = filepost.getParameter(inputname);
				inputvalue = inputvalue.replaceAll("&", "&amp;");
				inputvalue = inputvalue.replaceAll("<", "&lt;");
				inputvalue = inputvalue.replaceAll(">", "&gt;");
				inputvalue = inputvalue.replaceAll("\r", "");
				inputvalue = inputvalue.replaceAll("\n", "<br>");
				inputvalue = inputvalue.replaceAll("@@@", "<nobr>@@@</nobr>");
				inputvalue = inputvalue.replaceAll("###", "<nobr>###</nobr>");
				content = content.replaceAll("@@@" + inputname + "@@@", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				title = title.replaceAll("@@@" + inputname + "@@@", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				summary = summary.replaceAll("@@@" + inputname + "@@@", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				metainfo = metainfo.replaceAll("@@@" + inputname + "@@@", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				author = author.replaceAll("@@@" + inputname + "@@@", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				keywords = keywords.replaceAll("@@@" + inputname + "@@@", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				description = description.replaceAll("@@@" + inputname + "@@@", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				content = content.replaceAll("###" + inputname + "###", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				title = title.replaceAll("###" + inputname + "###", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				summary = summary.replaceAll("###" + inputname + "###", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				metainfo = metainfo.replaceAll("###" + inputname + "###", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				author = author.replaceAll("###" + inputname + "###", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				keywords = keywords.replaceAll("###" + inputname + "###", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				description = description.replaceAll("###" + inputname + "###", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			}
			content = content.replaceAll("###[a-zA-Z0-9]+###", "");
			title = title.replaceAll("###[a-zA-Z0-9]+###", "");
			summary = summary.replaceAll("###[a-zA-Z0-9]+###", "");
			metainfo = metainfo.replaceAll("###[a-zA-Z0-9]+###", "");
			author = author.replaceAll("###[a-zA-Z0-9]+###", "");
			keywords = keywords.replaceAll("###[a-zA-Z0-9]+###", "");
			description = description.replaceAll("###[a-zA-Z0-9]+###", "");
			mypage.setContent(content);
			mypage.setTitle(title);
			mypage.setSummary(summary);
			mypage.setMetaInfo(metainfo);
			mypage.setAuthor(author);
			mypage.setKeywords(keywords);
			mypage.setDescription(description);
			if (! myrequest.getParameter("page_top").equals("")) mypage.setPageTop(myrequest.getParameter("page_top"));
			if (! myrequest.getParameter("page_up").equals("")) mypage.setPageUp(myrequest.getParameter("page_up"));
			if (! myrequest.getParameter("page_first").equals("")) mypage.setPageFirst(myrequest.getParameter("page_first"));
			if (! myrequest.getParameter("page_previous").equals("")) mypage.setPagePrevious(myrequest.getParameter("page_previous"));
			if (! myrequest.getParameter("page_next").equals("")) mypage.setPageNext(myrequest.getParameter("page_next"));
			if (! myrequest.getParameter("page_last").equals("")) mypage.setPageLast(myrequest.getParameter("page_last"));
			if (! filepost.getParameter("page_top").equals("")) mypage.setPageTop(filepost.getParameter("page_top"));
			if (! filepost.getParameter("page_up").equals("")) mypage.setPageUp(filepost.getParameter("page_up"));
			if (! filepost.getParameter("page_first").equals("")) mypage.setPageFirst(filepost.getParameter("page_first"));
			if (! filepost.getParameter("page_previous").equals("")) mypage.setPagePrevious(filepost.getParameter("page_previous"));
			if (! filepost.getParameter("page_next").equals("")) mypage.setPageNext(filepost.getParameter("page_next"));
			if (! filepost.getParameter("page_last").equals("")) mypage.setPageLast(filepost.getParameter("page_last"));

			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			String username = mysession.get("username");
			if (username.equals("")) username = myrequest.getRemoteHost();
			if (username.equals("")) username = myrequest.getRemoteAddr();

			HashMap<String,String> postedfiles = new HashMap<String,String>();
			filepostparameternames = filepost.getParameterNames();
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

			if (((myrequest.getParameter("id").equals("")) && ((filepost == null) || (filepost.getParameter("id").equals("")))) || (mypage.getId().equals(""))) {
				if (! mysession.get("username").equals(myconfig.get(db, "superadmin"))) {
					mypage.setUser(false);
					mypage.setEditor(false);
					mypage.setCreator(false);
					mypage.setPublisher(false);
					mypage.setDeveloper(false);
					mypage.setAdministrator(false);
				}
			}

			if (mypage.getCreator()) {
				mypage.setCreated(timestamp);
				mypage.setCreatedBy(username);
				mypage.setUpdated(timestamp);
				mypage.setUpdatedBy(username);
				if ((! myrequest.getParameter("publish").equals("")) && (mypage.getPublisher())) {
					if (myrequest.getParameter("scheduled_publish").compareTo(timestamp) <= 0) {
						mypage.setRequestedPublish("");
						mypage.setRequestedUnpublish("");
						mypage.setScheduledPublish("");
						mypage.setPublished(timestamp);
						mypage.setPublishedBy(username);
						mypage.setUnpublished("");
						mypage.setUnpublishedBy("");
					}
				} else if ((filepost != null) && (! filepost.getParameter("publish").equals("")) && (mypage.getPublisher())) {
					if (filepost.getParameter("scheduled_publish").compareTo(timestamp) <= 0) {
						mypage.setRequestedPublish("");
						mypage.setRequestedUnpublish("");
						mypage.setScheduledPublish("");
						mypage.setPublished(timestamp);
						mypage.setPublishedBy(username);
						mypage.setUnpublished("");
						mypage.setUnpublishedBy("");
					}
				} else {
					mypage.setScheduledPublish("");
					mypage.setScheduledUnpublish("");
					mypage.setPublished("");
					mypage.setPublishedBy("");
				}

				mypage.create(db, myconfig, "content", "id");
				page_id = "" + mypage.getId();
				handleStaticAddress(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db, "");
				handleArchive(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
				handleSchedule(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
				handleSave(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
				handlePublish(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
				String mypageid = mypage.getId();
				mypage = getPublishedPageById(mypage.getId(), server, mysession, myrequest, myresponse, myconfig, db);
				mypage.outputStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
				mypage.exportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
				mypage.read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", "", "", "", "", "", "", "", db, myconfig, mypageid, "content", "id", "", myconfig.get(db, "default_version"), "", "", "", "", myconfig.get(db, "default_template"), "", myconfig.get(db, "default_stylesheet"), "", myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));

				if (postedfiles.size() > 0) {
					db.execute("update content set page_up=" + db.quote(mypage.getId()) + " where id in (" + Common.join(",", postedfiles.values().toArray()) + ")");
					db.execute("update content_public set page_up=" + db.quote(mypage.getId()) + " where id in (" + Common.join(",", postedfiles.values().toArray()) + ")");
				}

				String subject = "";
				String body = "";
				Page notification = new Page(text);
				String email_template = myrequest.getParameter("email_template");
				if ((email_template.equals("")) && (filepost != null)) email_template = filepost.getParameter("email_template");
				if (email_template.equals("")) email_template = myconfig.get(db, "default_publish_ready");
				if (! email_template.equals("")) {
					notification.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, email_template, mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
					subject = "" + notification.getTitle() + " " + mypage.getTitle();
					body = "" + notification.getContent();
				}
				if (body.equals("")) {
					subject = "Website content posted: " + mypage.getTitle();
					body = "<p>The user '@@@username@@@' has posted the content item '@@@title@@@'.</p>" + "\r\n" + "\r\n";
					body += "<p>Preview:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@preview@@@\">@@@preview@@@</a>" + "\r\n";
					body += "<p>Update/publish:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@update@@@\">@@@update@@@</a>" + "\r\n";
					body += "<p>Delete:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@delete@@@\">@@@delete@@@</a>" + "\r\n";
				}
				body = body.replaceAll("@@@id@@@", mypage.getId());
				body = body.replaceAll("@@@class@@@", mypage.getContentClass());
				body = body.replaceAll("@@@package@@@", mypage.getContentPackage());
				body = body.replaceAll("@@@bundle@@@", mypage.getContentBundle());
				body = body.replaceAll("@@@group@@@", mypage.getContentGroup());
				body = body.replaceAll("@@@type@@@", mypage.getContentType());
				body = body.replaceAll("@@@status@@@", mypage.getStatus().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				if (! myrequest.getParameter("comments").equals("")) {
					body = body.replaceAll("@@@comments@@@", myrequest.getParameter("comments").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				} else {
					body = body.replaceAll("@@@comments@@@", filepost.getParameter("comments").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				}
				body = body.replaceAll("@@@title@@@", mypage.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("@@@content@@@", mypage.getContent().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("@@@summary@@@", mypage.getSummary().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("@@@revision@@@", mypage.getRevision().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")).replaceAll("\\r\\n","<br>\r\n");
				body = body.replaceAll("@@@username@@@", username.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				User myuser = new User();
				myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, username);
				body = body.replaceAll("@@@name@@@", myuser.getName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("@@@preview@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + mypage.getContentClass() + ".jsp?id=" + mypage.getId() + "&mode=preview&version=&");
				body = body.replaceAll("@@@view@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/view.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
				body = body.replaceAll("@@@update@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/update.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
				body = body.replaceAll("@@@delete@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/delete.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
				body = mypage.parse_output_replace_inputs(myrequest, filepost, body);
				notification.setBody(body);
				notification.parse_output_extensions(server, myrequest, myresponse, mysession);
				notification.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, notification.getId(), "content_public", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				body = notification.getBody();
				handleNotification(subject, body, server, mypage, null, filepost, myrequest, myresponse, mysession, myconfig, db);
			}
		}

		if (! redirect.endsWith("=")) {
			page_id = "";
		}
		if ((redirect.startsWith("http://")) || (redirect.startsWith("https://"))) {
			if ((! myconfig.get(db, "redirect_urls").trim().equals("")) && (! Common.startsWithAnyOf(Common.crlf_clean(redirect), myconfig.get(db, "redirect_urls")))) {
				// ignore
			} else {
				myresponse.sendRedirect(Common.crlf_clean(redirect + page_id));
			}
		} else if (! redirect.equals("")) {
			myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + Common.crlf_clean(redirect + page_id));
		} else {
			myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/");
		}

		return mypage;
	}



	public Page doCreate(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return doCreate(server, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Page doCreate(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Fileupload filepost) throws Exception {
		if (db == null) return new Page(text);
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Page(text);
		error = "";
		if (filepost == null) filepost = getFileupload(DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		Page mypage = getPage(server, mysession, myrequest, myresponse, myconfig, db, filepost, false);
		handleCreateBlank(mypage, mysession, myrequest, filepost, myconfig, db);
		Workflow workflow = new Workflow(text);
		if (! filepost.getParameter("status").equals("")) {
			workflow.read(db, filepost.getParameter("status"));
		} else if (myconfig.get(db, "use_workflow_create").equals("none")) {
			workflow.setToState("");
		} else {
			workflow.setFromState(mypage.getStatus());
			workflow.setToState(mypage.getStatus());
		}
		String published_filename = "";
		String published_server_filename = "";
		String old_status = mypage.getStatus();
		String old_status_by = mypage.getStatusBy();
		String old_server_filename = mypage.getServerFilename();

		mypage.setServerFilename("");
		mypage.setPublished("");
		String old_checkedout = mypage.getCheckedOut();
		mypage.getForm(db, myconfig, filepost);
		mypage.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
		mypage.getForm(db, myconfig, filepost);
		String new_checkedout = mypage.getCheckedOut();
		mypage.setStatus(workflow.getToState());
		mypage.setCheckedOut(old_checkedout);
		mypage.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
		mypage.setCheckedOut(new_checkedout);
		if (! mysession.get("username").equals(myconfig.get(db, "superadmin"))) {
			handleCreateBlank(mypage, mysession, myrequest, filepost, myconfig, db);
			mypage.setCheckedOut(old_checkedout);
			mypage.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
			mypage.setCheckedOut(new_checkedout);
		}

		String server_filename = "";
		String preferred_filename = "";
		String filename = "";

		if ((mypage.getCreator()) && ((! myconfig.get(db, "use_workflow").equals("yes")) || (mypage.getAdministrator()) || (workflow.permission(db, old_status, mypage.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))))) || ((mypage.getStatus().equals("")) && (myconfig.get(db, "use_workflow_create").equals("none"))))) {
			if ((mypage.getContentClass().equals("image")) || (mypage.getContentClass().equals("file"))) { 
				if ((filepost.getParameter("file.filename").equals("")) && (! old_server_filename.equals(""))) {
					server_filename = Common.findUniqueFilename(Common.getRealPath(server, myconfig.get(db, "URLrootpath")), old_server_filename, text, server, db, myconfig, mysession, myrequest, myresponse);
					Common.copyFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename), Common.getRealPath(server, myconfig.get(db, "URLrootpath") + server_filename));
					Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/copy"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + old_server_filename + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + server_filename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:doCreate1\"", "", server, mysession, myrequest, myresponse);
					mypage.setServerFilename(server_filename);
					old_server_filename = server_filename;
					filepost.setParameter("server_filename", server_filename);
				} else {
					old_server_filename = "";
				}
				server_filename = handleFileUpload(mypage, old_server_filename, server_filename, published_server_filename, server, mysession, myrequest, myresponse, myconfig, db, filepost);
			}

			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			String username = mysession.get("username");
			mypage.setCreated(timestamp);
			mypage.setCreatedBy(username);
			mypage.setUpdated(timestamp);
			mypage.setUpdatedBy(username);
			if (mypage.getStatus().equals("")) {
				mypage.setStatusBy("");
			} else if (! mypage.getStatus().equals(old_status)) {
				mypage.setStatusBy(username);
			}
			workflow.permission(db, old_status, mypage.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))));
			if (myconfig.get(db, "use_workflow").equals("yes")) {
				workflow.update_content(mypage);
				if (workflow.getAutoPublish()) {
					if (mypage.getPublisher()) {
						filepost.setParameter("publish", "yes");
						mypage.setRequestedPublish("");
						mypage.setRequestedUnpublish("");
						mypage.setScheduledPublish("");
						mypage.setPublished(timestamp);
						mypage.setPublishedBy(username);
						mypage.setUnpublished("");
						mypage.setUnpublishedBy("");
					} else if (mypage.getEditor()) {
						filepost.setParameter("ready_to_publish", "yes");
					}
				}
				if (workflow.getAutoUnschedule()) {
					filepost.setParameter("scheduled_publish", "");
					filepost.setParameter("scheduled_unpublish", "");
					mypage.setScheduledPublish("");
					mypage.setScheduledUnpublish("");
				}
				if (workflow.getAutoArchive()) {
					filepost.setParameter("archive", "yes");
				}
			}
			if (mypage.isLockedSchedule()) {
				filepost.setParameter("scheduled_publish", "");
				mypage.setScheduledPublish("");
			}
			if (mypage.isLockedUnschedule()) {
				filepost.setParameter("scheduled_unpublish", "");
				mypage.setScheduledUnpublish("");
			}
			if ((! filepost.getParameter("publish").equals("")) && (mypage.getPublisher()) && ((! myconfig.get(db, "use_workflow").equals("yes")) || (mypage.getAdministrator()) || (workflow.permission(db, old_status, mypage.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))))))) {
				if ((filepost.getParameter("scheduled_publish").equals("")) || (filepost.getParameter("scheduled_publish").compareTo(timestamp) <= 0)) {
					mypage.setRequestedPublish("");
					mypage.setRequestedUnpublish("");
					mypage.setScheduledPublish("");
					mypage.setPublished(timestamp);
					mypage.setPublishedBy(username);
					mypage.setUnpublished("");
					mypage.setUnpublishedBy("");
				}
				if ((! filepost.getParameter("scheduled_unpublish").equals("")) && (filepost.getParameter("scheduled_unpublish").compareTo(timestamp) <= 0)) {
					filepost.setParameter("scheduled_unpublish", "");
					mypage.setScheduledUnpublish("");
				}
			} else {
				if ((filepost.getParameter("scheduled_publish").equals("")) || (filepost.getParameter("scheduled_publish").compareTo(timestamp) <= 0)) {
					mypage.setRequestedPublish("");
				}
				if ((filepost.getParameter("scheduled_unpublish").equals("")) || (filepost.getParameter("scheduled_unpublish").compareTo(timestamp) <= 0)) {
					mypage.setRequestedUnpublish("");
				}
				mypage.setScheduledPublish("");
				mypage.setScheduledUnpublish("");
				mypage.setPublished("");
				mypage.setPublishedBy("");
			}

// CUSTOMIZATION - START

String my_contentclass = mypage.getContentClass(); // "page" "product" "image" "file" "link" ...
String my_title = mypage.getTitle();
String my_content = mypage.getContent();
String my_author = mypage.getAuthor();
String my_description = mypage.getDescription();
String my_keywords = mypage.getKeywords();
String my_server_filename = "";
if (! mypage.getServerFilename().equals("")) {
	my_server_filename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + mypage.getServerFilename());
}

// place custom program code to check/modify content and file here

mypage.setTitle(my_title);
mypage.setContent(my_content);
mypage.setAuthor(my_author);
mypage.setDescription(my_description);
mypage.setKeywords(my_keywords);

// CUSTOMIZATION - END

			mypage.create(db, myconfig, "content", "id");
			Cms.CMSAudit("action=create content=" + mypage.getTitle() + " [" + mypage.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));

			handleStaticAddress(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db, "");
			handleArchive(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
			handleSchedule(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
			handleSave(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
			handlePublish(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
			handleProductStock(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
			String save_scheduled_publish = mypage.getScheduledPublish();
			String mypageid = mypage.getId();
			mypage = getPublishedPageById(mypage.getId(), server, mysession, myrequest, myresponse, myconfig, db);
			mypage.setScheduledPublish(save_scheduled_publish);
			handleStaticFile(server, mypage, filepost, myrequest, myresponse, mysession, myconfig, db);
			mypage.read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", "", "", "", "", "", "", "", db, myconfig, mypageid, "content", "id", "", myconfig.get(db, "default_version"), "", "", "", "", myconfig.get(db, "default_template"), "", myconfig.get(db, "default_stylesheet"), "", myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));

			if (myconfig.get(db, "use_workflow").equals("yes")) {
				if (workflow.getAutoCheckin()) {
					mypage.checkin(db, mysession.get("username"), myconfig.get(db, "superadmin"));
					if (myconfig.get(db, "use_archive").equals("auto-on-checkin")) {
						doArchive(server, mypage, mysession, myrequest, myresponse, myconfig, db);
					}
				}
				if (workflow.getAutoCheckout()) {
//					if (((mypage.getEditor()) && (mypage.getScheduledPublish().equals(""))) || (mypage.getPublisher()) && ((! myconfig.get(db, "use_workflow").equals("yes")) || (mypage.getAdministrator()) || (workflow.permissions(db, mypage.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (mypage.getStatusBy().equals(mysession.get("username"))))))) {
						mypage.checkout(db, mysession.get("username"));
//					}
				}
				if (workflow.getAutoUnpublish()) {
					mypage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
					mypage.public_delete(db, myconfig, mypage.getId(), Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
					mypage.deleteExportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
				}
				if (workflow.getAutoDelete()) {
					mypage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
					mypage.public_delete(db, myconfig, mypage.getId(), Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
					mypage.archive_delete_all(db, myconfig, mypage.getId(), Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
					mypage.preview_delete(db, myconfig, mypage.getId(), Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
					mypage.deleteExportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
				}
			}

			handleServerFilenameUploadAPI(server, mysession, myrequest, myresponse, myconfig, db, mypage, mypage.getUpdated().equals(mypage.getPublished()));
			if (mypage.getContentClass().equals("image") && (mypage.getImage1().equals("")) && (mypage.getImage2().equals("")) && (mypage.getImage3().equals(""))) {
				handleAdditionalContentUploadAPI(server, mysession, myrequest, myresponse, myconfig, db, mypage, mypage.getUpdated().equals(mypage.getPublished()));
			} else if (mypage.getContentClass().equals("file") && (mypage.getFile1().equals("")) && (mypage.getFile2().equals("")) && (mypage.getFile3().equals(""))) {
				handleAdditionalContentUploadAPI(server, mysession, myrequest, myresponse, myconfig, db, mypage, mypage.getUpdated().equals(mypage.getPublished()));
			}

/* QQQQQ automatic linking of related content items
			if ((! mypage.getPagePrevious().equals("")) && (! mypage.getPageNext().equals(""))) {
				Page mypageprev = getPageById(mypage.getPagePrevious(), server, mysession, myrequest, myresponse, myconfig, db);
				Page mypagenext = getPageById(mypage.getPageNext(), server, mysession, myrequest, myresponse, myconfig, db);
				if ((mypageprev.getPageNext().equals(mypagenext.getId())) && (mypagenext.getPagePrevious().equals(mypageprev.getId()))) {
					mypageprev.setPageNext(mypage.getId());
					mypagenext.setPagePrevious(mypage.getId());
					mypageprev.update(db, myconfig, mypageprev.getId(), "content", "id");
					mypagenext.update(db, myconfig, mypagenext.getId(), "content", "id");
				}
				if (mypage.getUpdated().equals(mypage.getPublished())) {
					mypageprev = getPublishedPageById(mypage.getPagePrevious(), server, mysession, myrequest, myresponse, myconfig, db);
					mypagenext = getPublishedPageById(mypage.getPageNext(), server, mysession, myrequest, myresponse, myconfig, db);
					if ((mypageprev.getPageNext().equals(mypagenext.getId())) && (mypagenext.getPagePrevious().equals(mypageprev.getId()))) {
						mypageprev.setPageNext(mypage.getId());
						mypagenext.setPagePrevious(mypage.getId());
						mypageprev.update(db, myconfig, mypageprev.getId(), "content_public", "id");
						mypagenext.update(db, myconfig, mypagenext.getId(), "content_public", "id");
					}
				}
			}
*/

			String subject = "";
			String body = "";
			Page notification = new Page(text);
			if (! filepost.getParameter("email_template").equals("")) {
				notification.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, filepost.getParameter("email_template"), "", myconfig.get(db, "default_version"), "", "", "", "", "", myconfig.get(db, "default_template"), "", "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				subject = "" + notification.getTitle() + " " + mypage.getTitle();
				body = "" + notification.getContent();
			} else if (! workflow.getNotifyEmail().equals("")) {
				filepost.setParameter("ready_to_publish", "yes");
				notification.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, workflow.getNotifyEmail(), "", myconfig.get(db, "default_version"), "", "", "", "", "", myconfig.get(db, "default_template"), "", "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				subject = "" + notification.getTitle() + " " + mypage.getTitle();
				body = "" + notification.getContent();
			} else if (! myconfig.get(db, "default_publish_ready").equals("")) {
				notification.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_publish_ready"), "", myconfig.get(db, "default_version"), "", "", "", "", "", myconfig.get(db, "default_template"), "", "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				subject = "" + notification.getTitle() + " " + mypage.getTitle();
				body = "" + notification.getContent();
			}
			if (body.equals("")) {
				subject = "Website content created and ready to publish: " + mypage.getTitle();
				body = "<p>The website administrator '@@@username@@@' has created the content item '@@@title@@@' and marked it as 'Ready To Publish'.</p>" + "\r\n" + "\r\n";
				body += "<p>Preview:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@preview@@@\">@@@preview@@@</a></p>" + "\r\n";
				body += "<p>Update/publish:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@update@@@\">@@@update@@@</a></p>" + "\r\n";
				body += "<p>Delete:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@delete@@@\">@@@delete@@@</a></p>" + "\r\n";
			}
			body = body.replaceAll("@@@id@@@", mypage.getId());
			body = body.replaceAll("@@@class@@@", mypage.getContentClass());
			body = body.replaceAll("@@@package@@@", mypage.getContentPackage());
			body = body.replaceAll("@@@bundle@@@", mypage.getContentBundle());
			body = body.replaceAll("@@@group@@@", mypage.getContentGroup());
			body = body.replaceAll("@@@type@@@", mypage.getContentType());
			body = body.replaceAll("@@@status@@@", mypage.getStatus().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (! myrequest.getParameter("comments").equals("")) {
				body = body.replaceAll("@@@comments@@@", myrequest.getParameter("comments").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			} else {
				body = body.replaceAll("@@@comments@@@", filepost.getParameter("comments").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			}
			body = body.replaceAll("@@@title@@@", mypage.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			body = body.replaceAll("@@@content@@@", mypage.getContent().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			body = body.replaceAll("@@@summary@@@", mypage.getSummary().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			body = body.replaceAll("@@@revision@@@", mypage.getRevision().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")).replaceAll("\\r\\n","<br>\r\n");
			body = body.replaceAll("@@@username@@@", username.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			User myuser = new User();
			myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, username);
			body = body.replaceAll("@@@name@@@", myuser.getName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			body = body.replaceAll("@@@preview@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + mypage.getContentClass() + ".jsp?id=" + mypage.getId() + "&mode=preview&version=&");
			body = body.replaceAll("@@@view@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/view.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
			body = body.replaceAll("@@@update@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/update.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
			body = body.replaceAll("@@@delete@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/delete.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
			body = mypage.parse_output_replace_inputs(myrequest, filepost, body);
			handleNotification(subject, body, server, mypage, workflow, filepost, myrequest, myresponse, mysession, myconfig, db);
		} else {
			error += text.display("content.create.nopermission");
		}
		return mypage;
	}



	public Page doUpdate(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return doUpdate(server, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, null, null);
	}
	public Page doUpdate(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String mycharset) throws Exception {
		return doUpdate(server, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, null, mycharset);
	}
	public Page doUpdate(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Fileupload filepost) throws Exception {
		return doUpdate(server, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, filepost, null);
	}
	public Page doUpdate(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Fileupload filepost, String mycharset) throws Exception {
		if (db == null) return new Page(text);
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Page(text);
		error = "";
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String username = mysession.get("username");
		String id = html.encodeHtmlEntities(myrequest.getParameter("id"));

		// Get file upload and posted HTML FORM data

		if (filepost == null) filepost = getFileupload(DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, mycharset);

		// Clear session variables

		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");

		// Get old published filename

		Page mypage = getPublishedPage(server, mysession, myrequest, myresponse, myconfig, db);
		String published_filename = "";
		String published_server_filename = "";
		if (! mypage.getServerFilename().equals("")) {
			published_filename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + mypage.getServerFilename());
			published_server_filename = mypage.getServerFilename(); 
		}

		// Read chosen workflow action (if any)

		mypage = getPage(server, mysession, myrequest, myresponse, myconfig, db, filepost, false);
		Workflow workflow = new Workflow(text);
		if (! filepost.getParameter("status").equals("")) {
			workflow.read(db, filepost.getParameter("status"));
		} else {
			workflow.setFromState(mypage.getStatus());
			workflow.setToState(mypage.getStatus());
		}

		String old_status = mypage.getStatus();
		String old_status_by = mypage.getStatusBy();
		String old_server_filename = mypage.getServerFilename();
		String server_filename = "";
		String preferred_filename = "";
		String filename = "";
		if (! mypage.getServerFilename().equals("")) {
			filename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + mypage.getServerFilename());
		}

		// Fail if content checkedout and without administrator permissions to override

		if ((! mypage.getCheckedOut().equals("")) && (! mypage.getCheckedOut().equals(mysession.get("username"))) && (! myconfig.get(db, "superadmin").equals(mysession.get("username"))) && (! ((mypage.getCheckedOut().equals("-creators-")) && (mypage.getCreator()))) && (! ((mypage.getCheckedOut().equals("-editors-")) && (mypage.getEditor()))) && (! ((mypage.getCheckedOut().equals("-developers-")) && (mypage.getDeveloper()))) && (! ((mypage.getCheckedOut().equals("-publishers-")) && (mypage.getPublisher()))) && (! ((mypage.getCheckedOut().equals("-administrators-")) && (mypage.getAdministrator())))) {
			if (! filepost.getParameter("file.filename").equals("")) {
				String upload_filename = "";
				upload_filename = filepost.getParameter("file.server_filename");
				Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + upload_filename));
			}
			error = text.display("error.content.update.checkedout") + mypage.getCheckedOut();
		} else {
			if (! mypage.getEditor()) {

				// Handle workflow action and revision for users without update permissions

				if ((mypage.getUser()) && (myconfig.get(db, "use_workflow").equals("yes")) && (workflow.permission(db, old_status, workflow.getToState(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))))) && (filepost.parameterExists("status"))) {
					if (filepost.parameterExists("revision")) {
						mypage.setRevision(filepost.getParameter("revision"));
					}
					if (filepost.parameterExists("checkedout")) {
						mypage.setCheckedOut(filepost.getParameter("checkedout"));
					}
					mypage.setStatus(workflow.getToState());
					mypage.setEditor(true);
				} else if ((mypage.getPublisher()) && (! myconfig.get(db, "use_workflow").equals("yes"))) {
					mypage.setEditor(true);
				} else {
					workflow.setFromState(old_status);
					workflow.setToState(old_status);
					if ((mypage.getUser()) && (myconfig.get(db, "use_workflow").equals("yes")) && (workflow.permission(db, old_status, old_status, mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))))) && (! mypage.getRevision().equals(filepost.getParameter("revision")))) {
						mypage.setRevision(filepost.getParameter("revision"));
						mypage.setEditor(true);
					} else {
						id = "";
					}
				}
			} else {
				// Handle workflow action and get posted data for users with update permissions

				mypage.getForm(db, myconfig, filepost);
				mypage.setStatus(workflow.getToState());
				server_filename = handleFileUpload(mypage, old_server_filename, server_filename, published_server_filename, server, mysession, myrequest, myresponse, myconfig, db, filepost);
			}

			// Clear status by if status cleared

			if (mypage.getStatus().equals("")) {
				mypage.setStatusBy("");
			} else if (! mypage.getStatus().equals(old_status)) {
				mypage.setStatusBy(username);
			}

			if ((! id.equals("")) && (mypage.getEditor() || mypage.getPublisher()) && ((! myconfig.get(db, "use_workflow").equals("yes")) || (mypage.getAdministrator()) || (workflow.permission(db, old_status, mypage.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))))))) {
				mypage.setUpdated(timestamp);
				mypage.setUpdatedBy(username);
				workflow.permission(db, old_status, mypage.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))));
				if (myconfig.get(db, "use_workflow").equals("yes")) {
					workflow.update_content(mypage);
					if (workflow.getAutoPublish()) {
						if (mypage.getPublisher()) {
							filepost.setParameter("publish", "yes");
							mypage.setRequestedPublish("");
							mypage.setRequestedUnpublish("");
							mypage.setScheduledPublish("");
							mypage.setPublished(timestamp);
							mypage.setPublishedBy(username);
							mypage.setUnpublished("");
							mypage.setUnpublishedBy("");
						} else if (mypage.getEditor()) {
							filepost.setParameter("ready_to_publish", "yes");
						}
					}
					if (workflow.getAutoUnschedule()) {
						filepost.setParameter("scheduled_publish", "");
						filepost.setParameter("scheduled_unpublish", "");
						mypage.setScheduledPublish("");
						mypage.setScheduledUnpublish("");
					}
					if (workflow.getAutoArchive()) {
						filepost.setParameter("archive", "yes");
					}
				}
				if (mypage.isLockedSchedule()) {
					filepost.setParameter("scheduled_publish", "");
					mypage.setScheduledPublish("");
				}
				if (mypage.isLockedUnschedule()) {
					filepost.setParameter("scheduled_unpublish", "");
					mypage.setScheduledUnpublish("");
				}
				if (filepost.getParameter("update").equals("view")) {

					// Handle scheduled publishing for user who is only viewing content

					if ((! filepost.getParameter("publish").equals("")) && (mypage.getPublisher())) {
						if (myconfig.get(db, "use_scheduled_publish").equals("yes")) {
							if (filepost.parameterExists("scheduled_publish")) mypage.setScheduledPublish(filepost.getParameter("scheduled_publish"));
						}
						if (myconfig.get(db, "use_scheduled_unpublish").equals("yes")) {
							if (filepost.parameterExists("scheduled_unpublish")) mypage.setScheduledUnpublish(filepost.getParameter("scheduled_unpublish"));
						}
						if ((filepost.getParameter("scheduled_publish").equals("")) || (filepost.getParameter("scheduled_publish").compareTo(timestamp) <= 0)) {
							mypage.setRequestedPublish("");
							mypage.setRequestedUnpublish("");
							mypage.setScheduledPublish("");
							mypage.setPublished(timestamp);
							mypage.setPublishedBy(username);
							mypage.setUnpublished("");
							mypage.setUnpublishedBy("");
						}
						if ((! filepost.getParameter("scheduled_unpublish").equals("")) && (filepost.getParameter("scheduled_unpublish").compareTo(timestamp) <= 0)) {
							filepost.setParameter("scheduled_unpublish", "");
							mypage.setScheduledUnpublish("");
						}
					} else if (mypage.getPublisher() && (filepost.parameterExists("scheduled_publish") || filepost.parameterExists("scheduled_unpublish"))) {
						if ((filepost.getParameter("scheduled_publish").equals("")) || (filepost.getParameter("scheduled_publish").compareTo(timestamp) <= 0)) {
							mypage.setRequestedPublish("");
						}
						if ((filepost.getParameter("scheduled_unpublish").equals("")) || (filepost.getParameter("scheduled_unpublish").compareTo(timestamp) <= 0)) {
							mypage.setRequestedUnpublish("");
						}
						mypage.setScheduledPublish("");
						mypage.setScheduledUnpublish("");
					}
				} else {
					if ((! filepost.getParameter("publish").equals("")) && (mypage.getPublisher())) {
						if ((filepost.getParameter("scheduled_publish").equals("")) || (filepost.getParameter("scheduled_publish").compareTo(timestamp) <= 0)) {
							mypage.setRequestedPublish("");
							mypage.setRequestedUnpublish("");
							mypage.setScheduledPublish("");
							mypage.setPublished(timestamp);
							mypage.setPublishedBy(username);
							mypage.setUnpublished("");
							mypage.setUnpublishedBy("");
						} else {
							if (myconfig.get(db, "use_scheduled_publish").equals("yes")) {
								mypage.setScheduledPublish(filepost.getParameter("scheduled_publish"));
							}
						}
						if ((! filepost.getParameter("scheduled_unpublish").equals("")) && (filepost.getParameter("scheduled_unpublish").compareTo(timestamp) <= 0)) {
							filepost.setParameter("scheduled_unpublish", "");
							mypage.setScheduledUnpublish("");
						} else if (! filepost.getParameter("scheduled_unpublish").equals("")) {
							if (myconfig.get(db, "use_scheduled_publish").equals("yes")) {
								mypage.setScheduledUnpublish(filepost.getParameter("scheduled_unpublish"));
							}
						}
					} else {
						if ((filepost.getParameter("scheduled_publish").equals("")) || (filepost.getParameter("scheduled_publish").compareTo(timestamp) <= 0)) {
							mypage.setRequestedPublish("");
						}
						if ((filepost.getParameter("scheduled_unpublish").equals("")) || (filepost.getParameter("scheduled_unpublish").compareTo(timestamp) <= 0)) {
							mypage.setRequestedUnpublish("");
						}
						mypage.setScheduledPublish("");
						mypage.setScheduledUnpublish("");
					}
				}

				handleRestore(server, mypage, filepost, myrequest, myresponse, mysession, myconfig, db);

// CUSTOMIZATION - START

String my_contentclass = mypage.getContentClass(); // "page" "product" "image" "file" "link" ...
String my_title = mypage.getTitle();
String my_content = mypage.getContent();
String my_author = mypage.getAuthor();
String my_description = mypage.getDescription();
String my_keywords = mypage.getKeywords();
String my_server_filename = "";
if (! mypage.getServerFilename().equals("")) {
	my_server_filename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + mypage.getServerFilename());
}

// place custom program code to check/modify content and file here

mypage.setTitle(my_title);
mypage.setContent(my_content);
mypage.setAuthor(my_author);
mypage.setDescription(my_description);
mypage.setKeywords(my_keywords);

// CUSTOMIZATION - END

				Cms.CMSAudit("action=update content=" + mypage.getTitle() + " [" + mypage.getId() + "]" + " status=" + old_status + "->" + mypage.getStatus() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));

				// Save content changes

				mypage.update(db, myconfig, id, "content", "id");
				mypage.setId(id);

				handleStaticAddress(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db, "");
				handleStaticAddressLinks(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db, mypage.getServerFilename(), old_server_filename);
				if ((! mypage.getPublished().equals("")) && (mypage.getUpdated().compareTo(mypage.getPublished()) <= 0)) {
					handleStaticAddressLinks(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db, mypage.getServerFilename(), published_server_filename);
				}
				handleArchive(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
				handleSchedule(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
				handleSave(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
				handlePublish(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
				handleProductStock(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
				String save_scheduled_publish = mypage.getScheduledPublish();
				String mypageid = mypage.getId();
				mypage = getPublishedPageById(mypage.getId(), server, mysession, myrequest, myresponse, myconfig, db);
				mypage.setScheduledPublish(save_scheduled_publish);
				handleStaticFile(server, mypage, filepost, myrequest, myresponse, mysession, myconfig, db);
				mypage.read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", "", "", "", "", "", "", "", db, myconfig, mypageid, "content", "id", "", myconfig.get(db, "default_version"), "", "", "", "", myconfig.get(db, "default_template"), "", myconfig.get(db, "default_stylesheet"), "", myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				handleWorkflowAuto(server, mypage, workflow, filepost, myrequest, myresponse, mysession, myconfig, db, published_server_filename, username, timestamp);
				handleServerFilenameUploadAPI(server, mysession, myrequest, myresponse, myconfig, db, mypage, mypage.getUpdated().equals(mypage.getPublished()));
				if (mypage.getContentClass().equals("image")) {
					handleAdditionalContentUploadAPI(server, mysession, myrequest, myresponse, myconfig, db, mypage, mypage.getUpdated().equals(mypage.getPublished()));
				} else if (mypage.getContentClass().equals("file")) {
					handleAdditionalContentUploadAPI(server, mysession, myrequest, myresponse, myconfig, db, mypage, mypage.getUpdated().equals(mypage.getPublished()));
				}
				handleEmailNotification(server, mypage, workflow, username, filepost, myrequest, myresponse, mysession, myconfig, db);
			}
		}
		return mypage;
	}



	public Page doDelete(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return doDelete(server, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Page doDelete(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Fileupload filepost) throws Exception {
		if (db == null) return new Page(text);
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Page(text);
		error = "";
		if (filepost == null) filepost = getFileupload(DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		Page mypage = getPublishedPage(server, mysession, myrequest, myresponse, myconfig, db);
		String published_server_filename = "";
		if (! mypage.getServerFilename().equals("")) {
			published_server_filename = mypage.getServerFilename(); 
		}
		mypage = getPage(server, mysession, myrequest, myresponse, myconfig, db, filepost, false);
		String old_status_by = mypage.getStatusBy();
		Workflow workflow = new Workflow(text);
		if ((! myconfig.get(db, "use_contentdependencies").equals("permit")) && (mypage.dependents(myconfig, db).size() > 0) && (! myconfig.get(db, "superadmin").equals(mysession.get("username"))) && ((myrequest.getParameter("archive").equals("")) || (myrequest.getParameter("archive").equals("public")))) {
			error = text.display("error.content.delete.dependencies") + mypage.getCheckedOut();
		} else if ((! mypage.getCheckedOut().equals("")) && (! mypage.getCheckedOut().equals(mysession.get("username"))) && (! myconfig.get(db, "superadmin").equals(mysession.get("username"))) && (! ((mypage.getCheckedOut().equals("-creators-")) && (mypage.getCreator()))) && (! ((mypage.getCheckedOut().equals("-editors-")) && (mypage.getEditor()))) && (! ((mypage.getCheckedOut().equals("-developers-")) && (mypage.getDeveloper()))) && (! ((mypage.getCheckedOut().equals("-publishers-")) && (mypage.getPublisher()))) && (! ((mypage.getCheckedOut().equals("-administrators-")) && (mypage.getAdministrator())))) {
			error = text.display("error.content.delete.checkedout") + mypage.getCheckedOut();
		} else {
			if ((mypage.getPublisher()) && ((! myconfig.get(db, "use_workflow").equals("yes")) || (mypage.getAdministrator()) || (workflow.permission(db, mypage.getStatus(), "", mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))))))) {
				String username = mysession.get("username");
				String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
				if ((! myrequest.getParameter("archive").equals("")) && (! myrequest.getParameter("archive").equals("public"))) {
					Cms.CMSAudit("action=delete.archived content=" + mypage.getTitle() + " [" + mypage.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
					mypage.archive_delete(db, myconfig, myrequest.getParameter("archive"), Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
				} else if ((! myrequest.getParameter("unpublish").equals("")) && (! myrequest.getParameter("id").equals(""))) {
					Cms.CMSAudit("action=delete.published content=" + mypage.getTitle() + " [" + mypage.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
					if ((! published_server_filename.equals("")) && ((! published_server_filename.equals(mypage.getServerFilename())) || (myconfig.get(db, "use_static_content").equals("yes")))) {
						String save_server_filename = mypage.getServerFilename();
						mypage.setServerFilename(published_server_filename);
						mypage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
						mypage.setServerFilename(save_server_filename);
					} else {
						mypage.unpublishStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
					}
					mypage.unpublish(db, username, now);
					mypage.public_delete(db, myconfig, myrequest.getParameter("id"), Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
					mypage.deleteExportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
					mypage.unpublishServerFilename(server, myrequest, myresponse, mysession, myconfig, db, published_server_filename);
				} else if ((! filepost.getParameter("unpublish").equals("")) && (! myrequest.getParameter("id").equals(""))) {
					Cms.CMSAudit("action=delete.published content=" + mypage.getTitle() + " [" + mypage.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
					if ((! published_server_filename.equals("")) && ((! published_server_filename.equals(mypage.getServerFilename())) || (myconfig.get(db, "use_static_content").equals("yes")))) {
						String save_server_filename = mypage.getServerFilename();
						mypage.setServerFilename(published_server_filename);
						mypage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
						mypage.setServerFilename(save_server_filename);
					} else {
						mypage.unpublishStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
					}
					mypage.unpublish(db, username, now);
					mypage.public_delete(db, myconfig, myrequest.getParameter("id"), Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
					mypage.deleteExportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
					mypage.unpublishServerFilename(server, myrequest, myresponse, mysession, myconfig, db, published_server_filename);
				} else if ((! myrequest.getParameter("delete").equals("")) && (! myrequest.getParameter("id").equals(""))) {
					Cms.CMSAudit("action=delete content=" + mypage.getTitle() + " [" + mypage.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
					String save_server_filename = mypage.getServerFilename();
					mypage.setServerFilename(published_server_filename);
					mypage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
					mypage.setServerFilename(save_server_filename);
					mypage.public_delete(db, myconfig, myrequest.getParameter("id"), Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
					mypage.archive_delete_all(db, myconfig, myrequest.getParameter("id"), Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
					mypage.preview_delete(db, myconfig, myrequest.getParameter("id"), Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
					mypage.deleteExportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
				} else if ((! filepost.getParameter("delete").equals("")) && (! myrequest.getParameter("id").equals(""))) {
					Cms.CMSAudit("action=delete content=" + mypage.getTitle() + " [" + mypage.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
					String save_server_filename = mypage.getServerFilename();
					mypage.setServerFilename(published_server_filename);
					mypage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
					mypage.setServerFilename(save_server_filename);
					mypage.public_delete(db, myconfig, myrequest.getParameter("id"), Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
					mypage.archive_delete_all(db, myconfig, myrequest.getParameter("id"), Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
					mypage.preview_delete(db, myconfig, myrequest.getParameter("id"), Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
					mypage.deleteExportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
				}
				if (myconfig.get(db, "use_version_notifications").equals("yes")) {
					String subject = "";
					String body = "";
					Page notification = new Page(text);
					if (! myconfig.get(db, "default_version_notification").equals("")) {
						notification.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_version_notification"), "", myconfig.get(db, "default_version"), "", "", "", "", "", myconfig.get(db, "default_template"), "", "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
						subject = "" + notification.getTitle() + " " + mypage.getTitle();
						body = "" + notification.getContent();
					}
					if (body.equals("")) {
						subject = "Master/default version website content deleted: " + mypage.getTitle();
						body = "<p>The website administrator '@@@username@@@' has deleted the content item '@@@title@@@'.</p>" + "\r\n" + "\r\n";
						body += "<p>View @@@version@@@ version:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"http://" + myrequest.getServerName() + "/@@@version_class@@@.jsp?id=@@@version_id@@@\">" + "http://" + myrequest.getServerName() + "/@@@version_class@@@.jsp?id=@@@version_id@@@</a></p>" + "\r\n";
						body += "<p>Preview @@@version@@@ version:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@version_preview@@@\">@@@version_preview@@@</a></p>" + "\r\n";
						body += "<p>Update/publish @@@version@@@ version:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@version_update@@@\">@@@version_update@@@</a></p>" + "\r\n";
						body += "<p>Delete @@@version@@@ version:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@version_delete@@@\">@@@version_delete@@@</a></p>" + "\r\n";
					}
					body = body.replaceAll("@@@id@@@", mypage.getId());
					body = body.replaceAll("@@@class@@@", mypage.getContentClass());
					body = body.replaceAll("@@@package@@@", mypage.getContentPackage());
					body = body.replaceAll("@@@bundle@@@", mypage.getContentBundle());
					body = body.replaceAll("@@@group@@@", mypage.getContentGroup());
					body = body.replaceAll("@@@type@@@", mypage.getContentType());
					body = body.replaceAll("@@@status@@@", mypage.getStatus().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					if (! myrequest.getParameter("comments").equals("")) {
						body = body.replaceAll("@@@comments@@@", myrequest.getParameter("comments").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					} else {
						body = body.replaceAll("@@@comments@@@", filepost.getParameter("comments").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					}
					body = body.replaceAll("@@@title@@@", mypage.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					body = body.replaceAll("@@@content@@@", mypage.getContent().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					body = body.replaceAll("@@@summary@@@", mypage.getSummary().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					body = body.replaceAll("@@@revision@@@", mypage.getRevision().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")).replaceAll("\\r\\n","<br>\r\n");
					body = body.replaceAll("@@@username@@@", username.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					User myuser = new User();
					myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, username);
					body = body.replaceAll("@@@name@@@", myuser.getName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					body = body.replaceAll("@@@preview@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + mypage.getContentClass() + ".jsp?id=" + mypage.getId() + "&mode=preview&version=&");
					body = body.replaceAll("@@@view@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/view.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
					body = body.replaceAll("@@@update@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/update.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
					body = body.replaceAll("@@@delete@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/delete.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
					body = mypage.parse_output_replace_inputs(myrequest, filepost, body);
					handleVersionNotification(subject, body, server, mypage, filepost, myrequest, myresponse, mysession, myconfig, db);
				}
			}
		}
		return mypage;
	}



	public String getValidateMarkup(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return "";
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
		Fileupload filepost = getFileupload(DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, -1);
		String save_session_stylesheet = mysession.get("stylesheet");
		String save_session_template = mysession.get("template");
		String save_session_device = mysession.get("device");
		String save_session_version = mysession.get("version");
		String save_session_mode = mysession.get("mode");
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("device", "");
		mysession.set("version", "");
		mysession.set("mode", "");
		Page mypage = getPage(server, mysession, myrequest, myresponse, myconfig, db, filepost, true);
		mypage.getForm(db, myconfig, filepost);
		if (mypage.getContentClass().equals("script")) {
			mypage.setServerFilename("dummy.js");
		} else if (mypage.getContentClass().equals("stylesheet")) {
			mypage.setServerFilename("dummy.css");
		} else {
			mypage.setServerFilename("dummy.html");
			mypage.setContentClass("page");
		}
		mypage.doParseOutput(true);
		mypage.parse(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"), "content_public", "id", "", myconfig.get(db, "default_version"), "", "", "", "", "", myconfig.get(db, "default_template"), "", "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		String content = mypage.outputStaticFileContent(server, myrequest, myresponse, mysession, null, myconfig, db);
		mysession.set("stylesheet", save_session_stylesheet);
		mysession.set("template", save_session_template);
		mysession.set("device", save_session_device);
		mysession.set("version", save_session_version);
		mysession.set("mode", save_session_mode);
		return content;
	}
	public String doValidateMarkupMultiple(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return "";
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
		String output = "";
		String save_session_stylesheet = mysession.get("stylesheet");
		String save_session_template = mysession.get("template");
		String save_session_device = mysession.get("device");
		String save_session_version = mysession.get("version");
		String save_session_mode = mysession.get("mode");
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("device", "");
		mysession.set("version", "");
		mysession.set("mode", "");
		String[] ids = myrequest.getParameters("id");
		if (ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String validation = "";
				String id = ids[i];
				myrequest.setParameter("id", id);
				Page mypage = getPageById(id, server, mysession, myrequest, myresponse, myconfig, db);
				if ((! mypage.getContentClass().equals("stylesheet")) && (! mypage.getContentClass().equals("script")) && (! mypage.getContentClass().equals("image")) && (! mypage.getContentClass().equals("file")) && (! mypage.getContentClass().equals("link"))) {
					mypage.setServerFilename("dummy.html");
					mypage.setContentClass("page");
					mypage.doParseOutput(true);
					mypage.parse(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, id, "content_public", "id", "", myconfig.get(db, "default_version"), "", "", "", "", "", myconfig.get(db, "default_template"), "", "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
					String content = mypage.outputStaticFileContent(server, myrequest, myresponse, mysession, null, myconfig, db);
					HashMap<String,String> formdata = new HashMap<String,String>();
					formdata.put("fragment", content);
					validation = http.post("http://validator.w3.org", "/check", formdata);
					if ((validation != null) && (! validation.equals(""))) {
						if (validation.indexOf("<body") > 0) validation = validation.substring(validation.indexOf("<body"));
						if (validation.lastIndexOf("</body>") > 0) validation = validation.substring(0, validation.lastIndexOf("</body>"));
					}
					validation = validation.replaceAll("^<body.*?>", "");
					validation = validation.replaceAll("</body>$", "");
					validation = validation.replaceAll("src=\"((?!http).*?)\"", "src=\"http://validator.w3.org/$1\"");
					validation = validation.replaceAll("href=\"((?!http).*?)\"", "href=\"http://validator.w3.org/$1\"");
					if (! myconfig.get(db, "validate_markup_response_begin").equals("")) {
						if (validation.indexOf(myconfig.get(db, "validate_markup_response_begin")) > 0) validation = validation.substring(validation.indexOf(myconfig.get(db, "validate_markup_response_begin")));
					}
					if (! myconfig.get(db, "validate_markup_response_end").equals("")) {
						if (validation.indexOf(myconfig.get(db, "validate_markup_response_end")) > 0) validation = validation.substring(0, validation.lastIndexOf(myconfig.get(db, "validate_markup_response_end"))+myconfig.get(db, "validate_markup_response_end").length());
					}
					if (! myconfig.get(db, "validate_markup_response_regexp_cut").equals("")) {
						validation = validation.replaceAll(myconfig.get(db, "validate_markup_response_regexp_cut") , "");
					}
					if (! myconfig.get(db, "validate_markup_response_regexp_keep").equals("")) {
						validation = validation.replaceAll("(" + myconfig.get(db, "validate_markup_response_regexp_keep") + ")" , "$1");
					}
				}
				output += "<h1>" + mypage.getTitle() + " (" + mypage.getId() + ")" + "</h1><hr>" + "\r\n" + validation + "\r\n" + "<hr>";
			}
		}
		mysession.set("stylesheet", save_session_stylesheet);
		mysession.set("template", save_session_template);
		mysession.set("device", save_session_device);
		mysession.set("version", save_session_version);
		mysession.set("mode", save_session_mode);
		return output;	 
	}



	public void doDeleteMultiple(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return;
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		error = "";
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		String[] ids = myrequest.getParameters("id");
		String[] archiveids = myrequest.getParameters("archiveid");
		if (ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				Page mypage = getPageById(id, server, mysession, myrequest, myresponse, myconfig, db, false);
				String old_status_by = mypage.getStatusBy();
				Workflow workflow = new Workflow(text);
				int dependentscount = 0;
				HashMap dependents = new HashMap();
				if ((! myconfig.get(db, "use_contentdependencies").equals("permit")) && (! myconfig.get(db, "superadmin").equals(mysession.get("username")))) {
					dependents = mypage.dependents(myconfig, db);
					Iterator dependentitems = dependents.keySet().iterator();
					while (dependentitems.hasNext()) {
						String dependentitem = "" + dependentitems.next();
						if (! dependentitem.equals("0")) {
							dependentscount++;
							for (int j = 0; j < ids.length; j++) {
								if (dependentitem.equals(ids[j])) {
									dependentscount--;
									break;
								}
							}
						}
					}
				}
//				if ((! myconfig.get(db, "use_contentdependencies").equals("permit")) && (mypage.dependents(myconfig, db).size() > 0)) {
				if ((! myconfig.get(db, "use_contentdependencies").equals("permit")) && (dependentscount > 0) && (! myconfig.get(db, "superadmin").equals(mysession.get("username")))) {
					error += "<br>" + text.display("error.content.delete.dependencies") + " - " + mypage.getTitle();
				} else if ((! mypage.getCheckedOut().equals("")) && (! mypage.getCheckedOut().equals(mysession.get("username"))) && (! myconfig.get(db, "superadmin").equals(mysession.get("username"))) && (! ((mypage.getCheckedOut().equals("-creators-")) && (mypage.getCreator()))) && (! ((mypage.getCheckedOut().equals("-editors-")) && (mypage.getEditor()))) && (! ((mypage.getCheckedOut().equals("-developers-")) && (mypage.getDeveloper()))) && (! ((mypage.getCheckedOut().equals("-publishers-")) && (mypage.getPublisher()))) && (! ((mypage.getCheckedOut().equals("-administrators-")) && (mypage.getAdministrator())))) {
					error += "<br>" + text.display("error.content.delete.checkedout") + " - " + mypage.getCheckedOut() + " - " + mypage.getTitle();
				} else {
					if ((mypage.getPublisher()) && ((! myconfig.get(db, "use_workflow").equals("yes")) || (mypage.getAdministrator()) || (workflow.permission(db, mypage.getStatus(), "", mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))))))) {
						Cms.CMSAudit("action=delete content=" + mypage.getTitle() + " [" + mypage.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
						Page mypublishedpage = getPublishedPageById(mypage.getId(), server, mysession, myrequest, myresponse, myconfig, db);
						String save_server_filename = mypage.getServerFilename();
						mypage.setServerFilename(mypublishedpage.getServerFilename());
						mypage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
						mypage.setServerFilename(save_server_filename);
						mypage.public_delete(db, myconfig, id, Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
						mypage.archive_delete_all(db, myconfig, id, Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
						mypage.preview_delete(db, myconfig, id, Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
						mypage.deleteExportStaticFile(server, myrequest.getRequest(), myresponse.getResponse(), mysession.getSession(), null, myconfig, db);
					}
				}
			}
		} else if (archiveids.length > 0) {
			for (int i = 0; i < archiveids.length; i++) {
				String id = archiveids[i];
				Page mypage = getPageByArchiveId(id, server, mysession, myrequest, myresponse, myconfig, db);
				String old_status_by = mypage.getStatusBy();
				Workflow workflow = new Workflow(text);
				if ((! mypage.getCheckedOut().equals("")) && (! mypage.getCheckedOut().equals(mysession.get("username"))) && (! myconfig.get(db, "superadmin").equals(mysession.get("username"))) && (! ((mypage.getCheckedOut().equals("-creators-")) && (mypage.getCreator()))) && (! ((mypage.getCheckedOut().equals("-editors-")) && (mypage.getEditor()))) && (! ((mypage.getCheckedOut().equals("-developers-")) && (mypage.getDeveloper()))) && (! ((mypage.getCheckedOut().equals("-publishers-")) && (mypage.getPublisher()))) && (! ((mypage.getCheckedOut().equals("-administrators-")) && (mypage.getAdministrator())))) {
					error += "<br>" + text.display("error.content.delete.checkedout") + " - " + mypage.getCheckedOut() + " - " + mypage.getTitle();
				} else {
					if ((mypage.getPublisher()) && ((! myconfig.get(db, "use_workflow").equals("yes")) || (mypage.getAdministrator()) || (workflow.permission(db, mypage.getStatus(), "", mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))))))) {
						Cms.CMSAudit("action=delete.archived content=" + mypage.getTitle() + " [" + mypage.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
						mypage.archive_delete(db, myconfig, id, Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
					}
				}
			}
		}
	}



	public void doArchiveMultiple(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return;
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		error = "";
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		String[] ids = myrequest.getParameters("id");
		String[] archiveids = myrequest.getParameters("archiveid");
		if (ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				Page mypage = getPageById(id, server, mysession, myrequest, myresponse, myconfig, db, false);
				if (mypage.getEditor()) {
					doArchive(server, mypage, mysession, myrequest, myresponse, myconfig, db);
				}
			}
		}
	}



	public void doCheckoutMultiple(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return;
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		error = "";
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		String[] ids = myrequest.getParameters("id");
		String[] archiveids = myrequest.getParameters("archiveid");
		if (ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				Page mypage = getPageById(id, server, mysession, myrequest, myresponse, myconfig, db, false);
				Workflow workflow = new Workflow(text);
				if ((myconfig.get(db, "use_workflow").equals("yes")) && (! mypage.getAdministrator()) && (! workflow.permissions(db, mypage.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (mypage.getStatusBy().equals(mysession.get("username")))))) {
					mypage.setEditor(false);
					mypage.setPublisher(false);
				}
				if (((mypage.getEditor()) && ((mypage.getScheduledPublish().equals("")) || mypage.getPublisher())) || (mypage.getPublisher() && (! myconfig.get(db, "inherit_accessrestrictions").equals("no"))) || ((mypage.getEditor()) && (myconfig.get(db, "use_scheduled_publish").equals("yes")) && (myconfig.get(db, "use_schedule").equals("yes")) && (mypage.getUser()) && (mypage.isScheduled(db)))) {
					mypage.checkout(db, mysession.get("username"));
				}
			}
		}
	}



	public void doCheckinMultiple(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return;
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		error = "";
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		String[] ids = myrequest.getParameters("id");
		String[] archiveids = myrequest.getParameters("archiveid");
		if (ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				Page mypage = getPageById(id, server, mysession, myrequest, myresponse, myconfig, db, false);
				mypage.checkin(db, mysession.get("username"), myconfig.get(db, "superadmin"));
				if (myconfig.get(db, "use_archive").equals("auto-on-checkin")) {
					doArchive(server, mypage, mysession, myrequest, myresponse, myconfig, db);
				}
			}
		}
	}



	public void doUsersegmentRemove(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return;
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		error = "";
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		String[] ids = myrequest.getParameters("id");
		if (ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				Page mypage = getPageById(id, server, mysession, myrequest, myresponse, myconfig, db, false);
				if (mypage.getEditor()) {
					mypage.update_usersegment(db, myconfig, "");
					Cms.CMSAudit("action=usersegment.remove content=" + mypage.getTitle() + " [" + mypage.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
				}
			}
		}
	}



	public void doUsertestRemove(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return;
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		error = "";
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		String[] ids = myrequest.getParameters("id");
		if (ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				Page mypage = getPageById(id, server, mysession, myrequest, myresponse, myconfig, db, false);
				if (mypage.getEditor()) {
					mypage.update_usertest(db, myconfig, "");
					Cms.CMSAudit("action=usertest.remove content=" + mypage.getTitle() + " [" + mypage.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
				}
			}
		}
	}



	public void doPublish(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return;
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		error = "";
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		String[] ids = myrequest.getParameters("id");
		if (! myrequest.getParameters("id").equals("")) {

			Page mypage = getPublishedPageById(myrequest.getParameter("id"), server, mysession, myrequest, myresponse, myconfig, db);
			Workflow workflow = new Workflow(text);
			String published_filename = "";
			String published_server_filename = "";
			if (! mypage.getServerFilename().equals("")) {
				published_filename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + mypage.getServerFilename());
				published_server_filename = mypage.getServerFilename(); 
			} else {
				published_filename = "";
				published_server_filename = "";
			}

			mypage = getPageById(myrequest.getParameter("id"), server, mysession, myrequest, myresponse, myconfig, db, false);

			String old_server_filename = mypage.getServerFilename();
			String upload_filename = "";
			String server_filename = "";
			String preferred_filename = "";
			String filename = "";
			if (! mypage.getServerFilename().equals("")) {
				filename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + mypage.getServerFilename());
			}

			Fileupload filepost = new Fileupload(null, null, null);
			if ((mypage.getPublisher()) && ((! myconfig.get(db, "use_workflow").equals("yes")) || (mypage.getAdministrator()) || (workflow.permissions(db, mypage.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (mypage.getStatusBy().equals(mysession.get("username"))))))) {
				filepost.setParameter("publish", "yes");
			} else if ((mypage.getEditor()) && ((! myconfig.get(db, "use_workflow").equals("yes")) || (mypage.getAdministrator()) || (workflow.permissions(db, mypage.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (mypage.getStatusBy().equals(mysession.get("username"))))))) {
				filepost.setParameter("ready_to_publish", "yes");
			}

			if ((! myrequest.getParameters("id").equals("")) && (mypage.getEditor()) && ((! myconfig.get(db, "use_workflow").equals("yes")) || (mypage.getAdministrator()) || (workflow.permissions(db, mypage.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (mypage.getStatusBy().equals(mysession.get("username"))))))) {
				String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
				String username = mysession.get("username");
				mypage.setUpdated(timestamp);
				mypage.setUpdatedBy(username);
				if ((! filepost.getParameter("publish").equals("")) && (mypage.getPublisher()) && ((! myconfig.get(db, "use_workflow").equals("yes")) || (mypage.getAdministrator()) || (workflow.permissions(db, mypage.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (mypage.getStatusBy().equals(mysession.get("username"))))))) {
					if (filepost.getParameter("scheduled_publish").compareTo(timestamp) <= 0) {
						mypage.setRequestedPublish("");
						mypage.setRequestedUnpublish("");
						mypage.setScheduledPublish("");
						mypage.setPublished(timestamp);
						mypage.setPublishedBy(username);
						mypage.setUnpublished("");
						mypage.setUnpublishedBy("");
					}
					if ((! filepost.getParameter("scheduled_unpublish").equals("")) && (filepost.getParameter("scheduled_unpublish").compareTo(timestamp) <= 0)) {
						filepost.setParameter("scheduled_unpublish", "");
						mypage.setScheduledUnpublish("");
					}
				} else {
					mypage.setScheduledPublish("");
					mypage.setScheduledUnpublish("");
				}

//				handleReplacePublishedFile(filename, published_filename, preferred_filename, published_server_filename, server, mypage, filepost, myrequest, mysession, myconfig, db);
				handleRestore(server, mypage, filepost, myrequest, myresponse, mysession, myconfig, db);
				Cms.CMSAudit("action=publish content=" + mypage.getTitle() + " [" + mypage.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
				mypage.update(db, myconfig, myrequest.getParameter("id"), "content", "id");
				mypage.setId(myrequest.getParameter("id"));
				handleStaticAddress(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db, mypage.getServerFilename());
				handleArchive(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
				handleSchedule(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
				handleSave(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
				handlePublish(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
				String mypageid = mypage.getId();
				mypage = getPublishedPageById(mypage.getId(), server, mysession, myrequest, myresponse, myconfig, db);
				handleStaticFile(server, mypage, filepost, myrequest, myresponse, mysession, myconfig, db);
				mypage.read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", "", "", "", "", "", "", "", db, myconfig, mypageid, "content", "id", "", myconfig.get(db, "default_version"), "", "", "", "", myconfig.get(db, "default_template"), "", myconfig.get(db, "default_stylesheet"), "", myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));

				String subject = "";
				String body = "";
				Page notification = new Page(text);
				if (! filepost.getParameter("email_template").equals("")) {
					notification.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, filepost.getParameter("email_template"), "", myconfig.get(db, "default_version"), "", "", "", "", "", myconfig.get(db, "default_template"), "", "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
					subject = "" + notification.getTitle() + " " + mypage.getTitle();
					body = "" + notification.getContent();
				} else if (! myconfig.get(db, "default_publish_ready").equals("")) {
					notification.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_publish_ready"), "", myconfig.get(db, "default_version"), "", "", "", "", "", myconfig.get(db, "default_template"), "", "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
					subject = "" + notification.getTitle() + " " + mypage.getTitle();
					body = "" + notification.getContent();
				}
				if (body.equals("")) {
					subject = "Website content updated and ready to publish: " + mypage.getTitle();
					body = "<p>The website administrator '@@@username@@@' has updated the content item '@@@title@@@' and marked it as 'Ready To Publish'.</p>" + "\r\n" + "\r\n";
					body += "<p>View current:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"http://" + myrequest.getServerName() + "/" + mypage.getContentClass() + ".jsp?id=" + mypage.getId() + "\">" + "http://" + myrequest.getServerName() + "/" + mypage.getContentClass() + ".jsp?id=" + mypage.getId() + "</a></p>" + "\r\n";
					body += "<p>Preview new:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@preview@@@\">@@@preview@@@</a></p>" + "\r\n";
					body += "<p>Update/publish:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@update@@@\">@@@update@@@</a></p>" + "\r\n";
					body += "<p>Delete:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@delete@@@\">@@@delete@@@</a></p>" + "\r\n";
				}
				body = body.replaceAll("@@@id@@@", mypage.getId());
				body = body.replaceAll("@@@class@@@", mypage.getContentClass());
				body = body.replaceAll("@@@package@@@", mypage.getContentPackage());
				body = body.replaceAll("@@@bundle@@@", mypage.getContentBundle());
				body = body.replaceAll("@@@group@@@", mypage.getContentGroup());
				body = body.replaceAll("@@@type@@@", mypage.getContentType());
				body = body.replaceAll("@@@status@@@", mypage.getStatus().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				if (! myrequest.getParameter("comments").equals("")) {
					body = body.replaceAll("@@@comments@@@", myrequest.getParameter("comments").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				} else {
					body = body.replaceAll("@@@comments@@@", filepost.getParameter("comments").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				}
				body = body.replaceAll("@@@title@@@", mypage.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("@@@content@@@", mypage.getContent().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("@@@summary@@@", mypage.getSummary().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("@@@revision@@@", mypage.getRevision().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")).replaceAll("\\r\\n","<br>\r\n");
				body = body.replaceAll("@@@username@@@", username.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				User myuser = new User();
				myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, username);
				body = body.replaceAll("@@@name@@@", myuser.getName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("@@@preview@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + mypage.getContentClass() + ".jsp?id=" + mypage.getId() + "&mode=preview&version=&");
				body = body.replaceAll("@@@view@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/view.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
				body = body.replaceAll("@@@update@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/update.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
				body = body.replaceAll("@@@delete@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/delete.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
				body = mypage.parse_output_replace_inputs(myrequest, filepost, body);
				handleNotification(subject, body, server, mypage, workflow, filepost, myrequest, myresponse, mysession, myconfig, db);
			}
		}
	}



	public void doPublishMultiple(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return;
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		error = "";
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		String[] ids = myrequest.getParameters("id");
		String[] archiveids = myrequest.getParameters("archiveid");

		if (ids.length > 0) {
			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			String username = mysession.get("username");
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				boolean updated = false;

				// Get file upload and posted HTML FORM data

				Fileupload filepost = new Fileupload(null, null, null);

				// Clear session variables

				mysession.set("stylesheet", "");
				mysession.set("template", "");
				mysession.set("version", "");
				mysession.set("mode", "admin");
				mysession.set("id", id);

				// Get old published filename

				Page mypage = getPublishedPageById(id, server, mysession, myrequest, myresponse, myconfig, db);
				String published_filename = "";
				String published_server_filename = "";
				if (! mypage.getServerFilename().equals("")) {
					published_filename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + mypage.getServerFilename());
					published_server_filename = mypage.getServerFilename(); 
				}

				// Read chosen workflow action (if any)

				mypage = getPageById(id, server, mysession, myrequest, myresponse, myconfig, db, false);
				Workflow workflow = new Workflow(text);
				if (! myrequest.getParameter("status").equals("")) {
					workflow.read(db, myrequest.getParameter("status"));
				} else {
					workflow.setToState(mypage.getStatus());
				}

				String old_status = mypage.getStatus();
				String old_status_by = mypage.getStatusBy();
				String old_server_filename = mypage.getServerFilename();
				String server_filename = "";
				String preferred_filename = "";
				String filename = "";
				if (! mypage.getServerFilename().equals("")) {
					filename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + mypage.getServerFilename());
				}

				// Fail if content checkedout and without administrator permissions to override

				if ((! mypage.getCheckedOut().equals("")) && (! mypage.getCheckedOut().equals(mysession.get("username"))) && (! myconfig.get(db, "superadmin").equals(mysession.get("username"))) && (! ((mypage.getCheckedOut().equals("-creators-")) && (mypage.getCreator()))) && (! ((mypage.getCheckedOut().equals("-editors-")) && (mypage.getEditor()))) && (! ((mypage.getCheckedOut().equals("-developers-")) && (mypage.getDeveloper()))) && (! ((mypage.getCheckedOut().equals("-publishers-")) && (mypage.getPublisher()))) && (! ((mypage.getCheckedOut().equals("-administrators-")) && (mypage.getAdministrator())))) {
					if (! filepost.getParameter("file.filename").equals("")) {
						String upload_filename = "";
						upload_filename = filepost.getParameter("file.server_filename");
						Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + upload_filename));
					}
					error = text.display("error.content.update.checkedout") + mypage.getCheckedOut();
				} else {
					if (! mypage.getEditor()) {

						// Handle workflow action and revision for users without update permissions

						if ((mypage.getUser()) && (myconfig.get(db, "use_workflow").equals("yes")) && (workflow.permission(db, old_status, workflow.getToState(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))))) && (filepost.parameterExists("status"))) {
							if (filepost.parameterExists("revision")) {
								mypage.setRevision(filepost.getParameter("revision"));
							}
							mypage.setStatus(workflow.getToState());
							mypage.setEditor(true);
						} else if ((mypage.getPublisher()) && (! myconfig.get(db, "use_workflow").equals("yes"))) {
							mypage.setEditor(true);
						} else {
							workflow.setFromState(old_status);
							workflow.setToState(old_status);
							if ((mypage.getUser()) && (myconfig.get(db, "use_workflow").equals("yes")) && (workflow.permission(db, old_status, old_status, mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))))) && (! mypage.getRevision().equals(filepost.getParameter("revision")))) {
								mypage.setRevision(filepost.getParameter("revision"));
								mypage.setEditor(true);
							} else {
								id = "";
							}
						}
					} else {
						// Handle workflow action and get posted data for users with update permissions

//						mypage.getForm(db, myconfig, myrequest);
//						mypage.setStatus(workflow.getToState());
//						server_filename = handleFileUpload(mypage, old_server_filename, server_filename, published_server_filename, server, mysession, myrequest, myresponse, myconfig, db, filepost);

						if ((! myrequest.getParameter("status").equals("")) && (! id.equals("")) && (mypage.getUser()) && (myconfig.get(db, "use_workflow").equals("yes")) && ((mypage.getAdministrator()) || (workflow.permission(db, mypage.getStatus(), workflow.getToState(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))))))) {
							mypage.setStatus(workflow.getToState());
							updated = true;
						}
					}

					// Clear status by if status cleared

					if (mypage.getStatus().equals("")) {
						mypage.setStatusBy("");
					} else if (! mypage.getStatus().equals(old_status)) {
						mypage.setStatusBy(username);
					}

					if ((! id.equals("")) && (mypage.getEditor() || mypage.getPublisher()) && ((! myconfig.get(db, "use_workflow").equals("yes")) || (mypage.getAdministrator()) || (workflow.permission(db, old_status, mypage.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))))))) {

						mypage.setUpdated(timestamp);
						mypage.setUpdatedBy(username);

						boolean workflow_permissions = workflow.permissions(db, mypage.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))));
						if ((mypage.getPublisher()) && ((! myconfig.get(db, "use_workflow").equals("yes")) || (mypage.getAdministrator()) || workflow_permissions)) {
							filepost.setParameter("publish", "yes");
							updated = true;
						} else if ((mypage.getEditor()) && ((! myconfig.get(db, "use_workflow").equals("yes")) || (mypage.getAdministrator()) || workflow_permissions)) {
							filepost.setParameter("ready_to_publish", "yes");
							updated = true;
						}

						if ((! filepost.getParameter("publish").equals("")) && (mypage.getPublisher())) {
							if ((myrequest.getParameter("scheduled_publish").equals("")) || (myrequest.getParameter("scheduled_publish").compareTo(timestamp) <= 0)) {
								mypage.setRequestedPublish("");
								mypage.setRequestedUnpublish("");
								mypage.setScheduledPublish("");
								mypage.setPublished(timestamp);
								mypage.setPublishedBy(username);
								mypage.setUnpublished("");
								mypage.setUnpublishedBy("");
							} else {
								if (myconfig.get(db, "use_scheduled_publish").equals("yes")) {
									mypage.setScheduledPublish(myrequest.getParameter("scheduled_publish"));
								}
							}
							if ((! myrequest.getParameter("scheduled_unpublish").equals("")) && (myrequest.getParameter("scheduled_unpublish").compareTo(timestamp) <= 0)) {
								myrequest.setParameter("scheduled_unpublish", "");
								mypage.setScheduledUnpublish("");
							} else if (! myrequest.getParameter("scheduled_unpublish").equals("")) {
								if (myconfig.get(db, "use_scheduled_publish").equals("yes")) {
									mypage.setScheduledUnpublish(myrequest.getParameter("scheduled_unpublish"));
								}
							}
						}

						workflow.permission(db, old_status, mypage.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))));
						if (myconfig.get(db, "use_workflow").equals("yes")) {
							workflow.update_content(mypage);
							if (workflow.getAutoPublish()) {
								if (mypage.getPublisher()) {
									filepost.setParameter("publish", "yes");
									mypage.setRequestedPublish("");
									mypage.setRequestedUnpublish("");
									mypage.setScheduledPublish("");
									mypage.setPublished(timestamp);
									mypage.setPublishedBy(username);
									mypage.setUnpublished("");
									mypage.setUnpublishedBy("");
								} else if (mypage.getEditor()) {
									filepost.setParameter("ready_to_publish", "yes");
								}
							}
							if (workflow.getAutoUnschedule()) {
								filepost.setParameter("scheduled_publish", "");
								filepost.setParameter("scheduled_unpublish", "");
								mypage.setScheduledPublish("");
								mypage.setScheduledUnpublish("");
							}
							if (workflow.getAutoArchive()) {
								filepost.setParameter("archive", "yes");
							}
							updated = true;
						}

						if (updated) {

//							handleReplacePublishedFile(filename, published_filename, preferred_filename, published_server_filename, server, mypage, filepost, myrequest, mysession, myconfig, db);
//							handleRestore(server, mypage, filepost, myrequest, myresponse, mysession, myconfig, db);

							Cms.CMSAudit("action=publish content=" + mypage.getTitle() + " [" + mypage.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));

							// Save content changes

							mypage.update(db, myconfig, id, "content", "id");
							mypage.setId(id);

							handleStaticAddress(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db, mypage.getServerFilename());
							handleStaticAddressLinks(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db, mypage.getServerFilename(), old_server_filename);
							if ((! mypage.getPublished().equals("")) && (mypage.getUpdated().compareTo(mypage.getPublished()) <= 0)) {
								handleStaticAddressLinks(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db, mypage.getServerFilename(), published_server_filename);
							}
							handleArchive(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
							handleSchedule(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
//							handleSave(server, mypage, filepost, mysession, myresponse, myconfig, db);
							handlePublish(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
							String save_scheduled_publish = mypage.getScheduledPublish();
							String mypageid = mypage.getId();
							if (mypage.getUpdated().compareTo(mypage.getPublished()) == 0) {
								mypage = getPublishedPageById(mypage.getId(), server, mysession, myrequest, myresponse, myconfig, db);
								mypage.setScheduledPublish(save_scheduled_publish);
								handleStaticFile(server, mypage, filepost, myrequest, myresponse, mysession, myconfig, db);
							}
							mypage.read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", "", "", "", "", "", "", "", db, myconfig, mypageid, "content", "id", "", myconfig.get(db, "default_version"), "", "", "", "", myconfig.get(db, "default_template"), "", myconfig.get(db, "default_stylesheet"), "", myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
							handleWorkflowAuto(server, mypage, workflow, filepost, myrequest, myresponse, mysession, myconfig, db, published_server_filename, username, timestamp);
//							handleServerFilenameUploadAPI(server, mysession, myrequest, myresponse, myconfig, db, mypage, mypage.getUpdated().equals(mypage.getPublished()));
//							if (mypage.getContentClass().equals("image")) {
//								handleAdditionalContentUploadAPI(server, mysession, myrequest, myresponse, myconfig, db, mypage, mypage.getUpdated().equals(mypage.getPublished()));
//							} else if (mypage.getContentClass().equals("file")) {
//								handleAdditionalContentUploadAPI(server, mysession, myrequest, myresponse, myconfig, db, mypage, mypage.getUpdated().equals(mypage.getPublished()));
//							}
							handleEmailNotification(server, mypage, workflow, username, filepost, myrequest, myresponse, mysession, myconfig, db);
						}
					}
				}
			}
		}
	}



	public void doMoveMultiple(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return;
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		error = "";
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		String[] ids = myrequest.getParameters("id");
		String[] archiveids = myrequest.getParameters("archiveid");

		// Get content ids if moving whole content class

		if ((ids.length == 0) && (myrequest.parameterExists("contentclass")) && (myrequest.parameterExists("from_folder")) && (myrequest.parameterExists("to_folder"))) {
			String SQLwhere = "";
			if (myrequest.getParameter("contentclass").equals("content")) {
				SQLwhere = Common.SQLwhere(SQLwhere, "(contentclass <> " + db.quote("image") + " and contentclass <> " + db.quote("file") + " and contentclass <> " + db.quote("link") + ")");
			} else if (myrequest.getParameter("contentclass").equals("image")) {
				SQLwhere = Common.SQLwhere(SQLwhere, "(contentclass = " + db.quote("image") + ")");
			} else if (myrequest.getParameter("contentclass").equals("file")) {
				SQLwhere = Common.SQLwhere(SQLwhere, "(contentclass = " + db.quote("file") + ")");
			} else {
				SQLwhere = Common.SQLwhere(SQLwhere, "(1 = 0)");
			}
			if (! myrequest.getParameter("from_folder").equals("")) {
				SQLwhere = Common.SQLwhere_contains(db, myconfig, SQLwhere, "server_filename", myrequest.getParameter("from_folder"));
			} else {
				SQLwhere = Common.SQLwhere_not_contains(db, myconfig, SQLwhere, "server_filename", "/");
			}
			String SQL = "select id from content " + SQLwhere + " order by title,id";
			ids = (String[])db.query_values(SQL).values().toArray(new String[0]);
		}

		if (ids.length > 0) {
			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			String username = mysession.get("username");
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				boolean updated = false;

				// Get file upload and posted HTML FORM data

				Fileupload filepost = new Fileupload(null, null, null);

				// Clear session variables

				mysession.set("stylesheet", "");
				mysession.set("template", "");
				mysession.set("version", "");
				mysession.set("mode", "admin");

				// Get old published filename

				Page mypage = getPublishedPageById(id, server, mysession, myrequest, myresponse, myconfig, db);
				String published_filename = "";
				String published_server_filename = "";
				if (! mypage.getServerFilename().equals("")) {
					published_filename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + mypage.getServerFilename());
					published_server_filename = mypage.getServerFilename(); 
				}

				// Read chosen workflow action (if any)

				mypage = getPageById(id, server, mysession, myrequest, myresponse, myconfig, db, false);
				Workflow workflow = new Workflow(text);
				if (! myrequest.getParameter("status").equals("")) {
					workflow.read(db, myrequest.getParameter("status"));
				} else {
					workflow.setToState(mypage.getStatus());
				}

				String old_status = mypage.getStatus();
				String old_status_by = mypage.getStatusBy();
				String save_old_server_filename = mypage.getServerFilename();
//				String server_filename = "";
//				String preferred_filename = "";
//				String filename = "";
//				if (! mypage.getServerFilename().equals("")) {
//					filename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + mypage.getServerFilename());
//				}

				// Fail if content checkedout and without administrator permissions to override

				if ((! mypage.getCheckedOut().equals("")) && (! mypage.getCheckedOut().equals(mysession.get("username"))) && (! myconfig.get(db, "superadmin").equals(mysession.get("username"))) && (! ((mypage.getCheckedOut().equals("-creators-")) && (mypage.getCreator()))) && (! ((mypage.getCheckedOut().equals("-editors-")) && (mypage.getEditor()))) && (! ((mypage.getCheckedOut().equals("-developers-")) && (mypage.getDeveloper()))) && (! ((mypage.getCheckedOut().equals("-publishers-")) && (mypage.getPublisher()))) && (! ((mypage.getCheckedOut().equals("-administrators-")) && (mypage.getAdministrator())))) {
					if (! filepost.getParameter("file.filename").equals("")) {
						String upload_filename = "";
						upload_filename = filepost.getParameter("file.server_filename");
						Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + upload_filename));
					}
					error = text.display("error.content.update.checkedout") + mypage.getCheckedOut();
				} else {
					if (! mypage.getEditor()) {

						// Handle workflow action and revision for users without update permissions

						if ((mypage.getUser()) && (myconfig.get(db, "use_workflow").equals("yes")) && (workflow.permission(db, old_status, workflow.getToState(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))))) && ((myrequest.parameterExists("status")) || (filepost.parameterExists("status")))) {
							if (filepost.parameterExists("revision")) {
								mypage.setRevision(filepost.getParameter("revision"));
							}
							mypage.setStatus(workflow.getToState());
							mypage.setEditor(true);
						} else {
							workflow.setFromState(old_status);
							workflow.setToState(old_status);
							if ((mypage.getUser()) && (myconfig.get(db, "use_workflow").equals("yes")) && (workflow.permission(db, old_status, old_status, mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))))) && (! mypage.getRevision().equals(filepost.getParameter("revision")))) {
								mypage.setRevision(filepost.getParameter("revision"));
								mypage.setEditor(true);
							} else {
								id = "";
							}
						}
					} else {
						// Handle workflow action and get posted data for users with update permissions

//						mypage.getForm(db, myconfig, filepost);
//						mypage.setStatus(workflow.getToState());
//						server_filename = handleFileUpload(mypage, old_server_filename, server_filename, published_server_filename, server, mysession, myrequest, myresponse, myconfig, db, filepost);

						// Move content item to bundle/group/type

						if ((! id.equals("")) && (mypage.getAdministrator())) {
							if (myrequest.getParameter("contentbundle").equals(" ")) {
								if (! mypage.getContentBundle().equals("")) {
									mypage.setContentBundle("");
									updated = true;
								}
							} else if (! myrequest.getParameter("contentbundle").equals("")) {
								if (! mypage.getContentBundle().equals(myrequest.getParameter("contentbundle"))) {
									mypage.setContentBundle(myrequest.getParameter("contentbundle"));
									updated = true;
								}
							}
							if (myrequest.getParameter("contentgroup").equals(" ")) {
								if (! mypage.getContentGroup().equals("")) {
									mypage.setContentGroup("");
									updated = true;
								}
							} else if (! myrequest.getParameter("contentgroup").equals("")) {
								if (! mypage.getContentGroup().equals(myrequest.getParameter("contentgroup"))) {
									mypage.setContentGroup(myrequest.getParameter("contentgroup"));
									updated = true;
								}
							}
							if (myrequest.getParameter("contenttype").equals(" ")) {
								if (! mypage.getContentType().equals("")) {
									mypage.setContentType("");
									updated = true;
								}
							} else if (! myrequest.getParameter("contenttype").equals("")) {
								if (! mypage.getContentType().equals(myrequest.getParameter("contenttype"))) {
									mypage.setContentType(myrequest.getParameter("contenttype"));
									updated = true;
								}
							}
						}

						if ((! myrequest.getParameter("status").equals("")) && (! id.equals("")) && (mypage.getUser()) && (myconfig.get(db, "use_workflow").equals("yes")) && ((mypage.getAdministrator()) || (workflow.permission(db, mypage.getStatus(), workflow.getToState(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))))))) {
							mypage.setStatus(workflow.getToState());
							updated = true;
						}
					}

					// Clear status by if status cleared

					if (mypage.getStatus().equals("")) {
						mypage.setStatusBy("");
					} else if (! mypage.getStatus().equals(old_status)) {
						mypage.setStatusBy(username);
					}

					if ((! id.equals("")) && (mypage.getEditor()) && ((! myconfig.get(db, "use_workflow").equals("yes")) || (mypage.getAdministrator()) || (workflow.permission(db, old_status, mypage.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))))))) {

						// Republish already published content

						if ((mypage.getPublisher() && mypage.getUpdated().equals(mypage.getPublished())) && (! myconfig.get(db, "use_publish").equals("manual-off"))) {
							filepost.setParameter("publish", "yes");
							mypage.setPublished(timestamp);
							mypage.setPublishedBy(username);
							mypage.setUnpublished("");
							mypage.setUnpublishedBy("");
						} else {
//							filepost.setParameter("publish", "");
						}

						mypage.setUpdated(timestamp);
						mypage.setUpdatedBy(username);

						if ((myconfig.get(db, "use_publish").equals("auto-on-save")) || (mypage.getPublished().compareTo(mypage.getUpdated()) >= 0)) {
							filepost.setParameter("publish", "yes");
							mypage.setPublished(timestamp);
							mypage.setPublishedBy(username);
							mypage.setUnpublished("");
							mypage.setUnpublishedBy("");
						} else {
//							filepost.setParameter("publish", "");
						}

						workflow.permission(db, old_status, mypage.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (old_status_by.equals(mysession.get("username"))));
						if (myconfig.get(db, "use_workflow").equals("yes")) {
							workflow.update_content(mypage);
							if (workflow.getAutoPublish()) {
								if (mypage.getPublisher()) {
									filepost.setParameter("publish", "yes");
									mypage.setRequestedPublish("");
									mypage.setRequestedUnpublish("");
									mypage.setScheduledPublish("");
									mypage.setPublished(timestamp);
									mypage.setPublishedBy(username);
									mypage.setUnpublished("");
									mypage.setUnpublishedBy("");
								} else if (mypage.getEditor()) {
									filepost.setParameter("ready_to_publish", "yes");
								}
							}
							if (workflow.getAutoUnschedule()) {
								filepost.setParameter("scheduled_publish", "");
								filepost.setParameter("scheduled_unpublish", "");
								mypage.setScheduledPublish("");
								mypage.setScheduledUnpublish("");
							}
							if (workflow.getAutoArchive()) {
								filepost.setParameter("archive", "yes");
							}
							updated = true;
						}

						if ((! myrequest.getParameter("folder").equals("")) && (myconfig.get(db, "use_static_filenames").equals("yes")) && (mypage.getEditor())) {
							if (! mypage.getServerFilename().equals("")) {
								String foldername = myrequest.getParameter("folder");
								foldername = foldername.replaceAll("^/", "").replaceAll("/$", "").replaceAll("^ ", "").replaceAll(" $", ""); 
								String old_server_filename = mypage.getServerFilename();
								String new_server_filename = mypage.getServerFilename();
								new_server_filename = new_server_filename.replaceAll("\\\\", "/").replaceAll("^/", "").replaceAll("/$", "").replaceAll("^ ", "").replaceAll(" $", "");
								if (new_server_filename.lastIndexOf("/") > 0) {
									new_server_filename = new_server_filename.substring(new_server_filename.lastIndexOf("/")+1);
								}
								if (! foldername.equals("")) {
									new_server_filename = foldername + "/" + new_server_filename;
								}
								if (! new_server_filename.equals(old_server_filename)) { 
									if (new_server_filename.equals(published_server_filename)) {
										mypage.setServerFilename(new_server_filename);
										if (! old_server_filename.equals("")) {
											Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename));
											Common.deleteFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename), false);
											Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/delete"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + old_server_filename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:doMoveMultiple2\"", "", server, mysession, myrequest, myresponse);
										}
										updated = true;
										error += "<p>" + mypage.getTitle() + " (" + mypage.getId() + ")" + "<br>" + text.display("content.movefolder.ok") + old_server_filename + " =&gt; " + new_server_filename + "</p>" + "\r\n";
									} else if ((Common.fileExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename), text, server, db, myconfig, mysession, myrequest, myresponse)) || (Common.fileExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename + "/index.jsp"), text, server, db, myconfig, mysession, myrequest, myresponse))) {
										error += "<p>" + mypage.getTitle() + " (" + mypage.getId() + ")" + "<br>" + text.display("content.movefolder.error.exists") + old_server_filename + " =&gt; " + new_server_filename + "</p>" + "\r\n";
									} else {
										mypage.setServerFilename(new_server_filename);
										if (published_server_filename.equals("")) {
											Common.createFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename));
											Common.copyFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename), Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename));
											Common.copyFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename), Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename));
											if (Common.fileOrFolderExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename))) {
												Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename));
												Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename + "/index.jsp"));
												Common.deleteFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename), false);
											}
											Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/move"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + old_server_filename + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + new_server_filename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:doMoveMultiple3\"", "", server, mysession, myrequest, myresponse);
										} else {
											Common.createFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename));
											Common.copyFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename), Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename));
											Common.copyFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename), Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename));
											Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/copy"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + old_server_filename + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + new_server_filename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:doMoveMultiple5\"", "", server, mysession, myrequest, myresponse);
										}
										updated = true;
										if (! Common.fileOrFolderExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename), text, server, db, myconfig, mysession, myrequest, myresponse)) {
											error += "<p>" + mypage.getTitle() + " (" + mypage.getId() + ")" + "<br>" + text.display("content.movefolder.error.failed") + old_server_filename + " =&gt; " + new_server_filename + "</p>" + "\r\n";
										} else {
											error += "<p>" + mypage.getTitle() + " (" + mypage.getId() + ")" + "<br>" + text.display("content.movefolder.ok") + old_server_filename + " =&gt; " + new_server_filename + "</p>" + "\r\n";
										}
									}
								}
							}
						}
						if ((myrequest.parameterExists("from_folder")) && (myrequest.parameterExists("to_folder")) && (myconfig.get(db, "use_static_filenames").equals("yes")) && (mypage.getEditor())) {
							if (! mypage.getServerFilename().equals("")) {
								String foldername = myrequest.getParameter("to_folder");
								foldername = foldername.replaceAll("^/", "").replaceAll("/$", "").replaceAll("^ ", "").replaceAll(" $", ""); 
								String old_server_filename = mypage.getServerFilename();
								String new_server_filename = mypage.getServerFilename();
								new_server_filename = new_server_filename.replaceAll("\\\\", "/").replaceAll("^/", "").replaceAll("/$", "").replaceAll("^ ", "").replaceAll(" $", "");
								if ((! mypage.getServerFilename().equals("")) && (myrequest.getParameter("from_folder").equals(""))) {
									if (new_server_filename.indexOf("/") <= 0) {
										new_server_filename = foldername + "/" + new_server_filename;  							
									}
								} else if ((! mypage.getServerFilename().equals("")) && (mypage.getServerFilename().startsWith(myrequest.getParameter("from_folder")+"/"))) {
									new_server_filename = new_server_filename.replaceAll("^" + myrequest.getParameter("from_folder") + "/", foldername + "/");
								}
								if (! new_server_filename.equals(old_server_filename)) { 
									if (new_server_filename.equals(published_server_filename)) {
										mypage.setServerFilename(new_server_filename);
										if (! old_server_filename.equals("")) {
											Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename));
											Common.deleteFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename), false);
											Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/delete"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + old_server_filename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:doMoveMultiple6\"", "", server, mysession, myrequest, myresponse);
										}
										updated = true;
										error += "<p>" + mypage.getTitle() + " (" + mypage.getId() + ")" + "<br>" + text.display("content.movefolder.ok") + old_server_filename + " =&gt; " + new_server_filename + "</p>" + "\r\n";
									} else if ((Common.fileExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename), text, server, db, myconfig, mysession, myrequest, myresponse)) || (Common.fileExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename + "/index.jsp"), text, server, db, myconfig, mysession, myrequest, myresponse))) {
										error += "<p>" + mypage.getTitle() + " (" + mypage.getId() + ")" + "<br>" + text.display("content.movefolder.error.exists") + old_server_filename + " =&gt; " + new_server_filename + "</p>" + "\r\n";
									} else {
										mypage.setServerFilename(new_server_filename);
										if (published_server_filename.equals("")) {
											Common.createFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename));
											Common.copyFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename), Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename));
											Common.copyFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename), Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename));
											if (Common.fileOrFolderExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename))) {
												Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename));
												Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename + "/index.jsp"));
												Common.deleteFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename), false);
											}
											Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/move"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + old_server_filename + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + new_server_filename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:doMoveMultiple7\"", "", server, mysession, myrequest, myresponse);
										} else {
											Common.createFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename));
											Common.copyFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename), Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename));
											Common.copyFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename), Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename));
											Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/copy"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + old_server_filename + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + new_server_filename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:doMoveMultiple9\"", "", server, mysession, myrequest, myresponse);
										}
										updated = true;
										if (! Common.fileOrFolderExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename), text, server, db, myconfig, mysession, myrequest, myresponse)) {
											error += "<p>" + mypage.getTitle() + " (" + mypage.getId() + ")" + "<br>" + text.display("content.movefolder.error.failed") + old_server_filename + " =&gt; " + new_server_filename + "</p>" + "\r\n";
										} else {
											error += "<p>" + mypage.getTitle() + " (" + mypage.getId() + ")" + "<br>" + text.display("content.movefolder.ok") + old_server_filename + " =&gt; " + new_server_filename + "</p>" + "\r\n";
										}
									}
								}
							}
						}

						if (updated) {

//							handleReplacePublishedFile(filename, published_filename, preferred_filename, published_server_filename, server, mypage, filepost, myrequest, mysession, myconfig, db);
//							handleRestore(server, mypage, filepost, myrequest, myresponse, mysession, myconfig, db);

							Cms.CMSAudit("action=update content=" + mypage.getTitle() + " [" + mypage.getId() + "]" + " status=" + old_status + "->" + mypage.getStatus() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));

							// Save content changes

							mypage.update(db, myconfig, id, "content", "id");
							mypage.setId(id);

							handleStaticAddress(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db, mypage.getServerFilename());
							handleStaticAddressLinks(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db, mypage.getServerFilename(), save_old_server_filename);
							if ((! mypage.getPublished().equals("")) && (mypage.getUpdated().compareTo(mypage.getPublished()) <= 0)) {
								handleStaticAddressLinks(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db, mypage.getServerFilename(), published_server_filename);
							}
							handleArchive(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
							handleSchedule(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
//							handleSave(server, mypage, filepost, mysession, myresponse, myconfig, db);
							handlePublish(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
							String save_scheduled_publish = mypage.getScheduledPublish();
							String mypageid = mypage.getId();
							if (mypage.getUpdated().compareTo(mypage.getPublished()) == 0) {
								mypage = getPublishedPageById(mypage.getId(), server, mysession, myrequest, myresponse, myconfig, db);
								mypage.setScheduledPublish(save_scheduled_publish);
								handleStaticFile(server, mypage, filepost, myrequest, myresponse, mysession, myconfig, db);
							}
							mypage.read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", "", "", "", "", "", "", "", db, myconfig, mypageid, "content", "id", "", myconfig.get(db, "default_version"), "", "", "", "", myconfig.get(db, "default_template"), "", myconfig.get(db, "default_stylesheet"), "", myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
							handleWorkflowAuto(server, mypage, workflow, filepost, myrequest, myresponse, mysession, myconfig, db, published_server_filename, username, timestamp);
//							handleServerFilenameUploadAPI(server, mysession, myrequest, myresponse, myconfig, db, mypage, mypage.getUpdated().equals(mypage.getPublished()));
//							if (mypage.getContentClass().equals("image")) {
//								handleAdditionalContentUploadAPI(server, mysession, myrequest, myresponse, myconfig, db, mypage, mypage.getUpdated().equals(mypage.getPublished()));
//							} else if (mypage.getContentClass().equals("file")) {
//								handleAdditionalContentUploadAPI(server, mysession, myrequest, myresponse, myconfig, db, mypage, mypage.getUpdated().equals(mypage.getPublished()));
//							}
							handleEmailNotification(server, mypage, workflow, username, filepost, myrequest, myresponse, mysession, myconfig, db);
						}
					}
				}
			}
		}
	}



	public void doRelations(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		error = "";
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "admin");
		String[] ids = myrequest.getParameters("id");
		if (ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
//				Page mypage = getPageById(id, mysession, myrequest, myresponse, myconfig, db);
				Page mypage = getPrimaryOnlyPageById(id, mysession, myrequest, myresponse, myconfig, db);
				Fileupload filepost = new Fileupload(null, null, null);
				if ((! id.equals("")) && (mypage.getPublisher())) {
					if ((! mypage.getPageTop().equals(myrequest.getParameter("page_top_" + id))) || (! mypage.getPageUp().equals(myrequest.getParameter("page_up_" + id))) || (! mypage.getPagePrevious().equals(myrequest.getParameter("page_previous_" + id))) || (! mypage.getPageNext().equals(myrequest.getParameter("page_next_" + id)))) {
						if (! myrequest.getParameter("page_top_" + id).equals("")) {
							mypage.setPageTop(myrequest.getParameter("page_top_" + id));
						} else {
							mypage.setPageTop("0");
						}
						if (! myrequest.getParameter("page_up_" + id).equals("")) {
							mypage.setPageUp(myrequest.getParameter("page_up_" + id));
						} else {
							mypage.setPageUp("0");
						}
						if (! myrequest.getParameter("page_previous_" + id).equals("")) {
							mypage.setPagePrevious(myrequest.getParameter("page_previous_" + id));
						} else {
							mypage.setPagePrevious("0");
						}
						if (! myrequest.getParameter("page_next_" + id).equals("")) {
							mypage.setPageNext(myrequest.getParameter("page_next_" + id));
						} else {
							mypage.setPageNext("0");
						}
						String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
						String username = mysession.get("username");
						if ((myconfig.get(db, "default_publish_ready").equals("auto-on-save")) || (mypage.getPublished().compareTo(mypage.getUpdated()) >= 0)) {
							filepost.setParameter("publish", "yes");
							mypage.setPublished(timestamp);
							mypage.setPublishedBy(username);
							mypage.setUnpublished("");
							mypage.setUnpublishedBy("");
						} else {
							filepost.setParameter("publish", "");
						}
						mypage.setUpdated(timestamp);
						mypage.setUpdatedBy(username);
						Cms.CMSAudit("action=update content=" + mypage.getTitle() + " [" + mypage.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
						mypage.update(db, myconfig, id, "content", "id");
						handleArchive(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
						handleSchedule(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
//						handleSave(server, mypage, filepost, mysession, myresponse, myconfig, db);
						handlePublish(server, mypage, filepost, mysession, myrequest, myresponse, myconfig, db);
					}
				}
			}
		}
	}



	public boolean getStructure(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		return accesspermission;
	}



	public String doStructure(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";

		String output = "";
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String username = mysession.get("username");
		boolean do_publish = (! myrequest.getParameter("publish").equals(""));
		boolean do_archive = false;
		if (! myrequest.getParameter("archive").equals("")) {
			do_archive = true;
		} else if (myconfig.get(db, "use_archive").equals("auto-on-save")) {
			do_archive = true;
		} else if ((! myrequest.getParameter("publish").equals("")) && (myconfig.get(db, "use_archive").equals("auto-on-publish"))) {
			do_archive = true;
		}

		HashMap<String,String> pagesupdated = new HashMap<String,String>();
		HashMap<String,String> pagetops = new HashMap<String,String>();
		Enumeration parameternames = myrequest.getParameterNames();
		while (parameternames.hasMoreElements()) {
			String myname = "" + parameternames.nextElement();
			String myvalue = myrequest.getParameter(myname);
			if (myname.matches("^page_top_[0-9]+$")) {
				String id = myname.replaceAll("^page_top_", "");
				Content content = new Content();
				content.read(db, myconfig, id, "content", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				if ((! content.getId().equals("")) && (content.getPublisher())) {
					if (! myvalue.equals("")) {
						pagetops.put(id, myvalue);
					} else {
						pagetops.put(id, id);
					}
					content.setPageTop(myvalue);
					String myoutput = doStructureUpdate(content, timestamp, username, do_archive, do_publish, mysession, db, myconfig);
					if (pagesupdated.get(myoutput) == null) {
						pagesupdated.put(myoutput, "");
						output += "" + myoutput;
					}
				}
			}
			if (myname.matches("^page_up_[0-9]+$")) {
				String id = myname.replaceAll("^page_up_", "");
				Content content = new Content();
				content.read(db, myconfig, id, "content", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				if ((! content.getId().equals("")) && (content.getPublisher())) {
					content.setPageUp(myvalue);
					String myoutput = doStructureUpdate(content, timestamp, username, do_archive, do_publish, mysession, db, myconfig);
					if (pagesupdated.get(myoutput) == null) {
						pagesupdated.put(myoutput, "");
						output += "" + myoutput;
					}
				}
			}
			if (myname.matches("^page_first_[0-9]+$")) {
				String id = myname.replaceAll("^page_first_", "");
				Content content = new Content();
				content.read(db, myconfig, id, "content", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				if ((! content.getId().equals("")) && (content.getPublisher())) {
					content.setPageFirst(myvalue);
					String myoutput = doStructureUpdate(content, timestamp, username, do_archive, do_publish, mysession, db, myconfig);
					if (pagesupdated.get(myoutput) == null) {
						pagesupdated.put(myoutput, "");
						output += "" + myoutput;
					}
				}
			}
			if (myname.matches("^page_previous_[0-9]+$")) {
				String id = myname.replaceAll("^page_previous_", "");
				Content content = new Content();
				content.read(db, myconfig, id, "content", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				if ((! content.getId().equals("")) && (content.getPublisher())) {
					content.setPagePrevious(myvalue);
					String myoutput = doStructureUpdate(content, timestamp, username, do_archive, do_publish, mysession, db, myconfig);
					if (pagesupdated.get(myoutput) == null) {
						pagesupdated.put(myoutput, "");
						output += "" + myoutput;
					}
				}
			}
			if (myname.matches("^page_next_[0-9]+$")) {
				String id = myname.replaceAll("^page_next_", "");
				Content content = new Content();
				content.read(db, myconfig, id, "content", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				if ((! content.getId().equals("")) && (content.getPublisher())) {
					content.setPageNext(myvalue);
					String myoutput = doStructureUpdate(content, timestamp, username, do_archive, do_publish, mysession, db, myconfig);
					if (pagesupdated.get(myoutput) == null) {
						pagesupdated.put(myoutput, "");
						output += "" + myoutput;
					}
				}
			}
			if (myname.matches("^page_last_[0-9]+$")) {
				String id = myname.replaceAll("^page_last_", "");
				Content content = new Content();
				content.read(db, myconfig, id, "content", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				if ((! content.getId().equals("")) && (content.getPublisher())) {
					content.setPageLast(myvalue);
					String myoutput = doStructureUpdate(content, timestamp, username, do_archive, do_publish, mysession, db, myconfig);
					if (pagesupdated.get(myoutput) == null) {
						pagesupdated.put(myoutput, "");
						output += "" + myoutput;
					}
				}
			}
		}
		if (! output.equals("")) output += "<p>" + "\r\n";
		Iterator mypagetops = pagetops.keySet().iterator();
		while (mypagetops.hasNext()) {
			String mypageup = "" + mypagetops.next();
			String mypagetop = "" + pagetops.get(mypageup);
			String myoutput = doStructureCascade(pagetops, mypagetop, mypageup, mysession, db, myconfig, do_archive, do_publish, timestamp, username);
			output += myoutput;
		}
		return output;
	}
	public String doStructureCascade(HashMap page_tops, String page_top, String page_up, Session mysession, DB db, Configuration myconfig, boolean do_archive, boolean do_publish, String timestamp, String username) {
		String output = "";
		Content content = new Content();
		String SQL = "select * from content where " + db.is_not_blank("page_up") + " and page_up <> " + db.quote("0") + " and page_up=" + db.quote(page_up) + " order by id";
		content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
		while (content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
			if (page_tops.get(content.getId()) != null) {
				// content is edited top
			} else if (content.getPageTop().equals(content.getId())) {
				// content is its own top
			} else if (db.query_value("select count(*) from content where page_top=" + db.quote(content.getId()) + "").doubleValue() >= 1) {
				// content is top
				if ((! content.getId().equals("")) && (content.getPublisher())) {
					content.setPageTop(page_top);
					output += doStructureUpdate(content, timestamp, username, do_archive, do_publish, mysession, db, myconfig);
				}
			} else {
				// content is not top
				if ((! content.getId().equals("")) && (content.getPublisher())) {
					content.setPageTop(page_top);
					output += doStructureUpdate(content, timestamp, username, do_archive, do_publish, mysession, db, myconfig);
				}
				output += doStructureCascade(page_tops, page_top, content.getId(), mysession, db, myconfig, do_archive, do_publish, timestamp, username);
			}
		}
		return output;
	}
	public String doStructureUpdate(Content content, String timestamp, String username, boolean do_archive, boolean do_publish, Session mysession, DB db, Configuration myconfig) {
		String output = "";
		if (do_publish && (! content.getPublished().equals("")) && (content.getPublished().compareTo(content.getUpdated()) >= 0)) {
			content.setPublished(timestamp);
			content.setPublishedBy(username);
		}
		content.setUpdated(timestamp);
		content.setUpdatedBy(username);
		content.update(db, myconfig, content.getId(), "content", "id");
		if (do_archive) {
			content.archive(db, myconfig);
		}
		if (do_publish) {
			Content publishedcontent = new Content();
			publishedcontent.read(db, myconfig, content.getId(), "content_public", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
			if (publishedcontent.getId().equals(content.getId())) {
				publishedcontent.setPageTop(content.getPageTop());
				publishedcontent.setPageUp(content.getPageUp());
				publishedcontent.setPageFirst(content.getPageFirst());
				publishedcontent.setPagePrevious(content.getPagePrevious());
				publishedcontent.setPageNext(content.getPageNext());
				publishedcontent.setPageLast(content.getPageLast());
				publishedcontent.setUpdated(timestamp);
				publishedcontent.setUpdatedBy(username);
				publishedcontent.setPublished(timestamp);
				publishedcontent.setPublishedBy(username);
				publishedcontent.update(db, myconfig, publishedcontent.getId(), "content_public", "id");
			}
		}
		output += "" + content.getTitle() + " (" + content.getId() + ")" + "<br>" + "\r\n";
		return output;
	}



	public Page getPersonal(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		Page page = new Page(text);
		String username = "";
		if (! mysession.get("username").equals("")) {
			username = mysession.get("username");
		}
		if (! username.equals("")) {
			String SQL = "select id from content where created_by=" + db.quote(username) + " order by created";
			Content content = new Content(text);
			content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			if (content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				UCbrowseWebsite browseWebsite = new UCbrowseWebsite(text);
				page = browseWebsite.getPageById(content.getId(), server, mysession, myrequest, myresponse, myconfig, db);
			}
			content.closeRecords(db);
		}
		return page;
	}



	public Page getPersonalAdmin(User user, Page page, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		Page adminpage = new Page(text);
		UCbrowseWebsite browseWebsite = new UCbrowseWebsite(text);
		if (! myrequest.getParameter("id").equals("")) {
			adminpage = browseWebsite.getPageById(myrequest.getParameter("id"), server, mysession, myrequest, myresponse, myconfig, db);
		} else if (! myconfig.get(db, "default_personal_admin_page").equals("")) {
			adminpage = browseWebsite.getPageById(myconfig.get(db, "default_personal_admin_page"), server, mysession, myrequest, myresponse, myconfig, db);
		}
		adminpage.parse_output_personal_admin(user, page, db, myconfig);
		return adminpage;
	}



	public Page doPersonalUpdate(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		error = "";
		Page page = getPersonal(server, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
		if (myrequest.getMethod().equals("POST")) {
			boolean pageUpdated = false;
			HashMap requestForm = Email.getForm(myrequest);
			Fileupload filepost = new Fileupload(null, null, null);
			Iterator requestFormItems = requestForm.keySet().iterator();
			while (requestFormItems.hasNext()) {
				String inputname = "" + requestFormItems.next();
				String inputvalue = "" + requestForm.get(inputname);
				filepost.setParameter(inputname, inputvalue);
			}
			requestFormItems = requestForm.keySet().iterator();
			while (requestFormItems.hasNext()) {
				String inputname = "" + requestFormItems.next();
				String inputvalue = "" + requestForm.get(inputname);
				inputvalue = inputvalue.replaceAll("@@@", "<nobr>@@@</nobr>").replaceAll("<nobr><nobr>@@@</nobr></nobr>", "<nobr>@@@</nobr>");
				inputvalue = inputvalue.replaceAll("###", "<nobr>###</nobr>").replaceAll("<nobr><nobr>###</nobr></nobr>", "<nobr>###</nobr>");
				if ((inputname.equals("title")) && (! inputvalue.equals(""))) {
					page.setTitle(inputvalue);
					pageUpdated = true;
				}
				if ((inputname.equals("content")) && (! inputvalue.equals(""))) {
					page.setContent(inputvalue);
					pageUpdated = true;
				}
				if ((inputname.equals("template")) && (! inputvalue.equals(""))) {
					page.setTemplate(inputvalue);
					pageUpdated = true;
				}
				if ((inputname.equals("stylesheet")) && (! inputvalue.equals(""))) {
					page.setStyleSheet(inputvalue);
					pageUpdated = true;
				}
			}
			Iterator contentelements = page.elements(db, myconfig).keySet().iterator();
			while (contentelements.hasNext()) {
				String element = "" + contentelements.next();
				if (! filepost.getParameter("personal_" + element + "_content").equals("")) {
					page.getElement().put(element, myrequest.getParameter("personal_" + element + "_content"));
					pageUpdated = true;
				}
			}
			if (pageUpdated && page.getEditor()) {
				String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
				String username = mysession.get("username");
				page.setUpdated(timestamp);
				page.setUpdatedBy(username);
				if ((! filepost.getParameter("publish").equals("")) && (page.getPublisher())) {
					if (filepost.getParameter("scheduled_publish").compareTo(timestamp) <= 0) {
						page.setRequestedPublish("");
						page.setRequestedUnpublish("");
						page.setScheduledPublish("");
						page.setPublished(timestamp);
						page.setPublishedBy(username);
						page.setUnpublished("");
						page.setUnpublishedBy("");
					}
					if ((! filepost.getParameter("scheduled_unpublish").equals("")) && (filepost.getParameter("scheduled_unpublish").compareTo(timestamp) <= 0)) {
						filepost.setParameter("scheduled_unpublish", "");
						page.setScheduledUnpublish("");
					}
				} else {
					page.setScheduledPublish("");
					page.setScheduledUnpublish("");
				}
//				handleReplacePublishedFile("", "", "", "", server, page, filepost, myrequest, mysession, myconfig, db);
				handleRestore(server, page, filepost, myrequest, myresponse, mysession, myconfig, db);
				page.update(db, myconfig, page.getId(), "content", "id");
				handleArchive(server, page, filepost, mysession, myrequest, myresponse, myconfig, db);
				handleSchedule(server, page, filepost, mysession, myrequest, myresponse, myconfig, db);
				handleSave(server, page, filepost, mysession, myrequest, myresponse, myconfig, db);
				handlePublish(server, page, filepost, mysession, myrequest, myresponse, myconfig, db);
				String mypageid = page.getId();
				page = getPublishedPageById(page.getId(), server, mysession, myrequest, myresponse, myconfig, db);
				page.outputStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
				page.exportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
				page.read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", "", "", "", "", "", "", "", db, myconfig, mypageid, "content", "id", "", myconfig.get(db, "default_version"), "", "", "", "", myconfig.get(db, "default_template"), "", myconfig.get(db, "default_stylesheet"), "", myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));

				String subject = "";
				String body = "";
				Page notification = new Page(text);
				if (! filepost.getParameter("email_template").equals("")) {
					notification.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, filepost.getParameter("email_template"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
					subject = "" + notification.getTitle() + " " + page.getTitle();
					body = "" + notification.getContent();
				} else if (! myconfig.get(db, "default_publish_ready").equals("")) {
					notification.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_publish_ready"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
					subject = "" + notification.getTitle() + " " + page.getTitle();
					body = "" + notification.getContent();
				}
				if (body.equals("")) {
					subject = "Website content updated and ready to publish: " + page.getTitle();
					body = "<p>The website administrator '@@@username@@@' has updated the content item '@@@title@@@' and marked it as 'Ready To Publish'.</p>" + "\r\n" + "\r\n";
					body += "<p>View current:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"http://" + myrequest.getServerName() + "/" + page.getContentClass() + ".jsp?id=" + page.getId() + "\">" + "http://" + myrequest.getServerName() + "/" + page.getContentClass() + ".jsp?id=" + page.getId() + "</a></p>" + "\r\n";
					body += "<p>Preview new:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@preview@@@\">@@@preview@@@</a></p>" + "\r\n";
					body += "<p>Update/publish:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@update@@@\">@@@update@@@</a></p>" + "\r\n";
					body += "<p>Delete:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@delete@@@\">@@@delete@@@</a></p>" + "\r\n";
				}
				body = body.replaceAll("@@@id@@@", page.getId());
				body = body.replaceAll("@@@class@@@", page.getContentClass());
				body = body.replaceAll("@@@package@@@", page.getContentPackage());
				body = body.replaceAll("@@@bundle@@@", page.getContentBundle());
				body = body.replaceAll("@@@group@@@", page.getContentGroup());
				body = body.replaceAll("@@@type@@@", page.getContentType());
				body = body.replaceAll("@@@status@@@", page.getStatus().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				if (! myrequest.getParameter("comments").equals("")) {
					body = body.replaceAll("@@@comments@@@", myrequest.getParameter("comments").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				} else {
					body = body.replaceAll("@@@comments@@@", filepost.getParameter("comments").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				}
				body = body.replaceAll("@@@title@@@", page.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("@@@content@@@", page.getContent().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("@@@summary@@@", page.getSummary().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("@@@revision@@@", page.getRevision().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")).replaceAll("\\r\\n","<br>\r\n");
				body = body.replaceAll("@@@username@@@", username.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				User myuser = new User();
				myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, username);
				body = body.replaceAll("@@@name@@@", myuser.getName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("@@@preview@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + page.getContentClass() + ".jsp?id=" + page.getId() + "&mode=preview&version=&");
				body = body.replaceAll("@@@view@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/view.jsp?id=" + page.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
				body = body.replaceAll("@@@update@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/update.jsp?id=" + page.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
				body = body.replaceAll("@@@delete@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/delete.jsp?id=" + page.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
				body = page.parse_output_replace_inputs(myrequest, filepost, body);
				handleNotification(subject, body, server, page, null, filepost, myrequest, myresponse, mysession, myconfig, db);
			}
		}
		return page;
	}



	public Page doPreview(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return doPreview(server, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Page doPreview(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) throws Exception {
		if (db == null) return new Page(text);
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Page(text);
		try {
			return doPreviewPublic(server, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
		} catch (Exception e) {
			return new Page(text);
		}
	}



	public Page doPreviewPublic(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return doPreviewPublic(server, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Page doPreviewPublic(ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) throws Exception {
		if (mywebsite == null) mywebsite = new Website(text);
		String save_mode = mysession.get("mode");
		String save_device = mysession.get("device");
		String save_version = mysession.get("version");
		String save_stylesheet = mysession.get("stylesheet");
		String save_template = mysession.get("template");
		Fileupload filepost = new Fileupload(myrequest, DOCUMENT_ROOT + myconfig.get(db, "URLrootpath"), myconfig.get(db, "URLuploadpath"));
		this.fileupload = filepost;

		if (myrequest.getParameter("mode").equals("preview")) {
			mysession.set("device", "");
			mysession.set("useragent", "");
			mysession.set("version", "");
			mysession.set("stylesheet", "");
			mysession.set("template", "");
		}
		if (filepost.getParameter("mode").equals("preview")) {
			mysession.set("device", "");
			mysession.set("useragent", "");
			mysession.set("version", "");
			mysession.set("stylesheet", "");
			mysession.set("template", "");
		}
		if (! myrequest.getParameter("device").equals("")) {
			mysession.set("device", html.encodeHtmlEntities(myrequest.getParameter("device")));
		}
		if (! filepost.getParameter("device").equals("")) {
			mysession.set("device", html.encodeHtmlEntities(filepost.getParameter("device")));
		}
		if (! myrequest.getParameter("version").equals("")) {
			mysession.set("version", html.encodeHtmlEntities(myrequest.getParameter("version")));
		}
		if (! filepost.getParameter("version").equals("")) {
			mysession.set("version", html.encodeHtmlEntities(filepost.getParameter("version")));
		}
		if (! myrequest.getParameter("stylesheet").equals("")) {
			mysession.set("stylesheet", html.encodeHtmlEntities(myrequest.getParameter("stylesheet")));
		}
		if (! filepost.getParameter("stylesheet").equals("")) {
			mysession.set("stylesheet", html.encodeHtmlEntities(filepost.getParameter("stylesheet")));
		}
		if (! myrequest.getParameter("template").equals("")) {
			mysession.set("template", html.encodeHtmlEntities(myrequest.getParameter("template")));
		}
		if (! filepost.getParameter("template").equals("")) {
			mysession.set("template", html.encodeHtmlEntities(filepost.getParameter("template")));
		}
		if (! myrequest.getParameter("mode").equals("")) {
			mysession.set("mode", html.encodeHtmlEntities(myrequest.getParameter("mode")));
		}
		if (! filepost.getParameter("mode").equals("")) {
			mysession.set("mode", html.encodeHtmlEntities(filepost.getParameter("mode")));
		}

		if ((myrequest.parameterExists("useragent")) && (myrequest.getQueryString().indexOf("useragent=") != -1)) {
			if (mysession.get("mode").equals("admin")) {
				mysession.set("useragent", myrequest.getParameter("useragent"));
			} else if (mysession.get("mode").equals("preview")) {
				mysession.set("useragent", myrequest.getParameter("useragent"));
			}
		} else if (! filepost.getParameter("useragent").equals("")) {
			if (mysession.get("mode").equals("admin")) {
				mysession.set("useragent", filepost.getParameter("useragent"));
			} else if (mysession.get("mode").equals("preview")) {
				mysession.set("useragent", filepost.getParameter("useragent"));
			}
		}
		if (! mysession.get("useragent").equals("")) {
			Device mydevice = new Device(mysession.get("useragent"), mysession, true);
		}

		mysession.set("preview_scheduled", "");
		if (mysession.get("mode").equals("preview")) {
			if (myrequest.parameterExists("scheduled_publish")) {
				mysession.set("preview_scheduled", myrequest.getParameter("scheduled_publish"));
			}
			if (filepost.parameterExists("scheduled_publish")) {
				mysession.set("preview_scheduled", filepost.getParameter("scheduled_publish"));
			}
		}

		String page_id = "";
		if (filepost.getParameter("contentclass").equals("template")) {
			page_id = "";
			mysession.set("template", html.encodeHtmlEntities(myrequest.getParameter("id")));
		} else if (filepost.getParameter("contentclass").equals("stylesheet")) {
			page_id = "";
			mysession.set("stylesheet", html.encodeHtmlEntities(myrequest.getParameter("id")));
		} else if (filepost.getParameter("contentclass").equals("script")) {
			page_id = "";
			mysession.set("template", "0");
			mysession.set("stylesheet", "0");
		} else if (filepost.getParameter("contentclass").equals("page")) {
			page_id = html.encodeHtmlEntities(myrequest.getParameter("id"));
		} else if (filepost.getParameter("contentclass").equals("product")) {
			page_id = html.encodeHtmlEntities(myrequest.getParameter("id"));
		} else if (myrequest.getParameter("redirect").indexOf("id=") != -1) {
			page_id = "" + Integer.parseInt("0" + myrequest.getParameter("redirect").substring(3+myrequest.getParameter("redirect").indexOf("id=")));
			if (page_id.equals("0")) {
				page_id = "";
			}
		}
		if ((page_id.equals("")) && (! myrequest.getParameter("preview").equals("")) && (! filepost.getParameter("contentclass").equals("template")) && (! filepost.getParameter("contentclass").equals("stylesheet"))) {
			page_id = html.encodeHtmlEntities(myrequest.getParameter("preview"));
//			mysession.set("template", "0");
			mysession.set("template", "");
			mysession.set("stylesheet", "");
		}
		if ((page_id.equals("")) && (myrequest.parameterExists("id")) && (myrequest.getParameter("id").equals("")) && (myrequest.parameterExists("preview")) && (myrequest.getParameter("preview").equals(""))) {
			page_id = "0";
		}

		Page mypage;
		if ((mysession.get("mode").equals("preview")) || (mysession.get("mode").equals("admin"))) {
			if ((mysession.get("mode").equals("admin")) && (! myrequest.getParameter("menu").equals(""))) {
				mysession.set("menu", html.encodeHtmlEntities(myrequest.getParameter("menu")));
			}
			mypage = new Page(text);
			if (page_id.equals("0")) {
				handleCreateBlank(mypage, mysession, myrequest, filepost, myconfig, db);
			} else if (! page_id.equals("")) {
				mypage.scheduled_read(db, myconfig, page_id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
			} else if (mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) {
				mypage.scheduled_read(db, myconfig, mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_page"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
			} else {
				mypage.scheduled_read(db, myconfig, myconfig.get(db, "default_page"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
			}
		} else {
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
			}
			mypage = new Page(text);
			if (! page_id.equals("")) {
				mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, page_id, mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else if (mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) {
				mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else {
				mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			}
		}

		if (filepost.parameterExists("contentclass")) {
			mypage.setContentClass(filepost.getParameter("contentclass"));
		}
		if (filepost.getParameter("contentclass").equals("template")) {
			mysession.set("version", "");
			mysession.set("stylesheet", "");
			mysession.set("template", "");
			mypage.setStyleSheet("0");
			mypage.parse_input(mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), myconfig.get(db, "default_template"), mysession.get("stylesheet"), myconfig.get(db, "default_stylesheet"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			mypage.getTemplateContent().getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
			mypage.getTemplateContent().getForm(db, myconfig, filepost);
			mypage.setBody(mypage.getTemplateContent().getContent());
			mypage.getTemplateContent().parse_input(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			if ((! mypage.getTemplateContent().getTemplate().equals("")) && (! mypage.getTemplateContent().getTemplate().equals("0"))) {
				Page template2 = new Page(text);
				template2.read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getTemplateContent().getTemplate(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				mypage.setBody(template2.getContent().replaceAll("@@@content@@@", mypage.getTemplateContent().getContent()));
				if (! template2.getStyleSheet().equals("")) {
					for (int i=0; i<template2.getStyleSheetContents().size(); i++) {
						mypage.getTemplateContent().getStyleSheetContents().add(mypage.getTemplateContent().getStyleSheetContents().size(), template2.getStyleSheetContents().get(i));
					}
				}
			}
			mypage.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
			mypage.parse_input_elements(mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"));
			mypage.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		} else if (filepost.getParameter("contentclass").equals("stylesheet")) {
			mypage.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
			mypage.parse_input(mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), myconfig.get(db, "default_template"), mysession.get("stylesheet"), myconfig.get(db, "default_stylesheet"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			if (mypage.getStyleSheetContents().size() == 0) mypage.getStyleSheetContents().add(0, new Content(text));
			for (int i=0; i<mypage.getStyleSheetContents().size(); i++) {
				((Content)mypage.getStyleSheetContents().get(i)).getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
				((Content)mypage.getStyleSheetContents().get(i)).getForm(db, myconfig, filepost);
			}
			mypage.parse_input_elements(mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"));
			mypage.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			mypage.setStyleSheet("PREVIEW");
			myconfig.setTemp("hardcore_stylesheet_hrefs", "inline");
		} else if (filepost.getParameter("contentclass").equals("script")) {
			mypage.setBody("");
			mypage.getForm(db, myconfig, filepost);
			mypage.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
			mypage.getForm(db, myconfig, filepost);
			mypage.parse_input(mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), myconfig.get(db, "default_template"), mysession.get("stylesheet"), myconfig.get(db, "default_stylesheet"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			mypage.parse_input_elements(mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"));
			mypage.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		} else if (filepost.getParameter("contentclass").equals("page")) {
			mysession.set("version", "");
			mysession.set("stylesheet", "");
			mysession.set("template", "");
			mypage.setBody("");
			mypage.getForm(db, myconfig, filepost);
			mypage.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
			mypage.getForm(db, myconfig, filepost);
			mypage.parse_input(mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), myconfig.get(db, "default_template"), mysession.get("stylesheet"), myconfig.get(db, "default_stylesheet"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			mypage.parse_input_elements(mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"));
			mypage.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		} else if (filepost.getParameter("contentclass").equals("link")) {
			mypage.getForm(db, myconfig, filepost);
			mypage.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
			mypage.getForm(db, myconfig, filepost);
			mypage.parse_input(mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), myconfig.get(db, "default_template"), mysession.get("stylesheet"), myconfig.get(db, "default_stylesheet"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			mypage.parse_input_elements(mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"));
			mypage.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));

		} else if (filepost.getParameter("contentclass").equals("product")) {
			mysession.set("version", "");
			mysession.set("stylesheet", "");
			mysession.set("template", "");
			mypage.setBody("");
			mypage.getForm(db, myconfig, filepost);
			mypage.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
			mypage.getForm(db, myconfig, filepost);
			mypage.parse_input(mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), myconfig.get(db, "default_template"), mysession.get("stylesheet"), myconfig.get(db, "default_stylesheet"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			mypage.parse_input_elements(mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"));
			mypage.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			mysession.set("currency", myconfig.get(db, "default_currency"));
			mypage.setDisplayCurrency(db, mysession.get("currency"), myconfig.get(db, "default_currency"));
			mypage.parse_output_product(server, mysession, myrequest, myresponse, db, myconfig);

		} else if (mypage.getElementContent().containsKey(filepost.getParameter("contentclass"))) {
			mypage.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
			mypage.parse_input(mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), myconfig.get(db, "default_template"), mysession.get("stylesheet"), myconfig.get(db, "default_stylesheet"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			mypage.parse_input_elements(mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"));
			for (int i=0; i<((Content[]) mypage.getElementContent().get(filepost.getParameter("contentclass"))).length; i++) {
				((Content[]) mypage.getElementContent().get(filepost.getParameter("contentclass")))[i].getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
				((Content[]) mypage.getElementContent().get(filepost.getParameter("contentclass")))[i].getForm(db, myconfig, filepost);
			}
			mypage.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
//		} else if (! myrequest.getParameter("useragent").equals("")) {
//			mysession.set("version", "");
//			mysession.set("stylesheet", "");
//			mysession.set("template", "");
//			mypage.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
//			mypage.parse_input(mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), myconfig.get(db, "default_template"), mysession.get("stylesheet"), myconfig.get(db, "default_stylesheet"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
//			mypage.getTemplateContent().getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
//			mypage.getTemplateContent().getForm(db, myconfig, filepost);
//			mypage.parse_input_elements(mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"));
//			mypage.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		} else {
			mysession.set("version", "");
			mysession.set("stylesheet", "");
			mysession.set("template", "0");
			mypage.setTemplate("0");
			mypage.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
			mypage.parse_input(mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), myconfig.get(db, "default_template"), mysession.get("stylesheet"), myconfig.get(db, "default_stylesheet"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			mypage.getTemplateContent().getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
			mypage.getTemplateContent().getForm(db, myconfig, filepost);
			mypage.setBody(mypage.getTemplateContent().getContent());
			mypage.parse_input_elements(mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"));
			mypage.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, mypage.getId(), "content", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		}

		if (! mysession.get("stylesheet").equals("")) {
//			mypage.setStyleSheet(mysession.get("stylesheet"));
		} else if (mypage.getStyleSheet().equals("")) {
//			mypage.setStyleSheet(myconfig.get(db, "default_stylesheet"));
		}

		boolean accesspermission = RequireUser.checkUserAccessRestrictions(text, true, mypage, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) mypage = new Page(text);

//		mysession.set("mode", save_mode);
		mysession.set("version", save_version);
		mysession.set("stylesheet", save_stylesheet);
		mysession.set("template", save_template);
		if ((mypage.getContentClass().equals("link")) && (! mypage.getUrl().equals("")) && (myrequest.getRequestURI().indexOf("/" + text.display("adminpath") + "/simulators/") < 0)) {
			myresponse.sendRedirect(Common.crlf_clean(mypage.getUrl()));
		}
		String mybody = mypage.getBody();
		mybody = mybody.replaceAll("/image.jsp\\?id=", "/image.jsp?" + Math.random() + "&id=");
		mypage.setBody(mybody);
		mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_doctype");
		return mypage;
	}



	public String getError() {
		return error;
	}



	public Content getCreateRecords() {
		return create_records;
	}



	public Content getCreatedRecords() {
		return created_records;
	}



	public Content getExpiredRecords() {
		return expired_records;
	}



	public Content getUpdatedRecords() {
		return updated_records;
	}



	public Content getCheckedoutRecords() {
		return checkedout_records;
	}



	public Content getWorkflowRecords() {
		return workflow_records;
	}



	public int getRecordCount() {
		return record_count;
	}



	private void setSessionFilterFromRequest(Session mysession, Request myrequest) {
		if (myrequest.getParameter("contentpackage").equals(" ")) {
			mysession.set("admin_contentpackage", "");
		} else if (! myrequest.getParameter("contentpackage").equals("")) {
			mysession.set("admin_contentpackage", myrequest.getParameter("contentpackage"));
		}
		if (myrequest.getParameter("contentclass").equals(" ")) {
			mysession.set("admin_contentclass", "");
		} else if (! myrequest.getParameter("contentclass").equals("")) {
			mysession.set("admin_contentclass", myrequest.getParameter("contentclass"));
		}
		if (myrequest.getParameter("contenttype").equals(" ")) {
			mysession.set("admin_contenttype", "");
		} else if (! myrequest.getParameter("contenttype").equals("")) {
			mysession.set("admin_contenttype", myrequest.getParameter("contenttype"));
		}
		if (myrequest.getParameter("contentgroup").equals(" ")) {
			mysession.set("admin_contentgroup", "");
		} else if (! myrequest.getParameter("contentgroup").equals("")) {
			mysession.set("admin_contentgroup", myrequest.getParameter("contentgroup"));
		}
		if (myrequest.getParameter("contentbundle").equals(" ")) {
			mysession.set("admin_contentbundle", "");
		} else if (! myrequest.getParameter("contentbundle").equals("")) {
			mysession.set("admin_contentbundle", myrequest.getParameter("contentbundle"));
		}
		if (myrequest.getParameter("version").equals(" ")) {
			mysession.set("admin_version", "");
		} else if (! myrequest.getParameter("version").equals("")) {
			mysession.set("admin_version", myrequest.getParameter("version"));
		}
		if (myrequest.getParameter("status").equals(" ")) {
			mysession.set("admin_status", "");
		} else if (! myrequest.getParameter("status").equals("")) {
			mysession.set("admin_status", myrequest.getParameter("status"));
		}
		if (myrequest.getParameter("stock").equals(" ")) {
			mysession.set("admin_stock", "");
		} else if (! myrequest.getParameter("stock").equals("")) {
			mysession.set("admin_stock", myrequest.getParameter("stock"));
		}

		// currently segment+usertest only used for separate experience management administration - test and clear other session filters
		if ((! myrequest.getParameter("contentpackage").equals("")) || (! myrequest.getParameter("contentclass").equals("")) || (! myrequest.getParameter("contenttype").equals("")) || (! myrequest.getParameter("contentgroup").equals("")) || (! myrequest.getParameter("contentbundle").equals("")) || (! myrequest.getParameter("version").equals("")) || (! myrequest.getParameter("status").equals("")) || (! myrequest.getParameter("stock").equals(""))) {
			mysession.set("admin_segment", "");
			mysession.set("admin_usertest", "");
			mysession.set("admin_heatmap", "");
		}
		if ((! myrequest.getParameter("segment").equals("")) || (! myrequest.getParameter("usertest").equals("")) || (! myrequest.getParameter("heatmap").equals(""))) {
			mysession.set("admin_contentpackage", "");
			mysession.set("admin_contentclass", "");
			mysession.set("admin_contenttype", "");
			mysession.set("admin_contentgroup", "");
			mysession.set("admin_contentbundle", "");
			mysession.set("admin_version", "");
			mysession.set("admin_status", "");
			mysession.set("admin_stock", "");
		}

		if (myrequest.getParameter("segment").equals(" ")) {
			mysession.set("admin_segment", "");
		} else if (! myrequest.getParameter("segment").equals("")) {
			mysession.set("admin_segment", myrequest.getParameter("segment"));
			mysession.set("admin_contentclass", "page,element,template,stylesheet,script,product,image,file,link");
		}
		if (myrequest.getParameter("usertest").equals(" ")) {
			mysession.set("admin_usertest", "");
		} else if (! myrequest.getParameter("usertest").equals("")) {
			mysession.set("admin_usertest", myrequest.getParameter("usertest"));
			mysession.set("admin_contentclass", "page,element,template,stylesheet,script,product,image,file,link");
		}
		if (myrequest.getParameter("heatmap").equals(" ")) {
			mysession.set("admin_heatmap", "");
		} else if (! myrequest.getParameter("heatmap").equals("")) {
			mysession.set("admin_heatmap", myrequest.getParameter("heatmap"));
			mysession.set("admin_contentclass", "page,product");
		}
	}



	public Content getContent(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Fileupload filepost) {
		if ((filepost != null) && (! filepost.getParameter("id").equals(""))) {
			mysession.set("id", html.encodeHtmlEntities(filepost.getParameter("id")));
		} else {
			mysession.set("id", html.encodeHtmlEntities(myrequest.getParameter("id")));
		}
		Content mycontent = new Content(text);
		if (myrequest.getParameter("archive").equals("public")) {
			mycontent.public_read(db, myconfig, myrequest.getParameter("id"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		} else if (! myrequest.getParameter("archive").equals("")) {
			mycontent.archive_read(db, myconfig, myrequest.getParameter("archive"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		} else if ((filepost != null) && (! filepost.getParameter("id").equals(""))) {
			mycontent.preview_read(db, myconfig, filepost.getParameter("id"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		} else {
			mycontent.preview_read(db, myconfig, myrequest.getParameter("id"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		}
		return mycontent;
	}



	public Page getPage(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Fileupload filepost, boolean do_parse_output) throws Exception {
		if ((filepost != null) && (! filepost.getParameter("id").equals(""))) {
			mysession.set("id", html.encodeHtmlEntities(filepost.getParameter("id")));
		} else {
			mysession.set("id", html.encodeHtmlEntities(myrequest.getParameter("id")));
		}
		Page mypage = new Page(text);
		mypage.doParseOutput(do_parse_output);
		if (myrequest.getParameter("archive").equals("public")) {
			mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"), "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		} else if ((! myrequest.getParameter("archive").equals("")) && (Pattern.compile("^[0-9]+$").matcher(myrequest.getParameter("archive")).find())) {
			mypage.archive_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("archive"), "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		} else if ((filepost != null) && (! filepost.getParameter("id").equals(""))) {
			mypage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, filepost.getParameter("id"), "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		} else {
			mypage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"), "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		}
		return mypage;
	}



	public Page getPublishedPage(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return getPublishedPage(server, mysession, myrequest, myresponse, myconfig, db, true);
	}
	public Page getPublishedPage(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, boolean do_parse_output) throws Exception {
		Page mypage = new Page(text);
		mypage.doParseOutput(do_parse_output);
		mysession.set("id", html.encodeHtmlEntities(myrequest.getParameter("id")));
		mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"), "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		return mypage;
	}



	public Page getPublishedPageById(String id, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return getPublishedPageById(id, server, mysession, myrequest, myresponse, myconfig, db, true);
	}
	public Page getPublishedPageById(String id, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, boolean do_parse_output) throws Exception {
		Page mypage = new Page(text);
		mypage.doParseOutput(do_parse_output);
		mysession.set("id", id);
		mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, id, "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		return mypage;
	}



	public Page getPageById(String id, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return getPageById(id, server, mysession, myrequest, myresponse, myconfig, db, true);
	}
	public Page getPageById(String id, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, boolean do_parse_output) throws Exception {
		Page mypage = new Page(text);
		mypage.doParseOutput(do_parse_output);
		mysession.set("id", id);
		mypage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, id, "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		return mypage;
	}



	public Page getPrimaryOnlyPageById(String id, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		return getPrimaryOnlyPageById(id, mysession, myrequest, myresponse, myconfig, db, true);
	}
	public Page getPrimaryOnlyPageById(String id, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, boolean do_parse_output) {
		Page mypage = new Page(text);
		mypage.doParseOutput(do_parse_output);
		mypage.read_primary_only(db, myconfig, id, "content", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), "", "", "", "", "", mysession);
		return mypage;
	}



	public Page getPageByArchiveId(String id, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return getPageByArchiveId(id, server, mysession, myrequest, myresponse, myconfig, db, true);
	}
	public Page getPageByArchiveId(String id, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, boolean do_parse_output) throws Exception {
		Page mypage = new Page(text);
		mypage.doParseOutput(do_parse_output);
		mypage.archive_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, id, "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		return mypage;
	}



	public Fileupload getFileupload(String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		return getFileupload(DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, 0);
	}
	public Fileupload getFileupload(String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String charset) {
		return getFileupload(DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, 0, charset);
	}
	public Fileupload getFileupload(String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, int randomize) {
		return getFileupload(DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, randomize, null);
	}
	public Fileupload getFileupload(String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, int randomize, String charset) {
		String save_content_editor = myconfig.get(db, "content_editor");
		Fileupload filepost = new Fileupload(myrequest, DOCUMENT_ROOT + myconfig.get(db, "URLrootpath"), myconfig.get(db, "URLuploadpath"), randomize, charset);
		myconfig.setTemp("content_editor", save_content_editor);
		return filepost;
	}



	private boolean validateFilenameExtension(String upload_filename, String server_filename, String old_server_filename, ServletContext server, Session mysession, Request myrequest, Response myresponse, Page mypage, Fileupload filepost, Configuration myconfig, DB db) throws Exception {
		if ((mypage.validFilenameExtension(db, filepost.getParameter("file.filenameextension"))) && (! Pattern.compile(Fileupload.blocked_files).matcher(server_filename).find())) {
//			if (((mypage.getContentClass().equals("image")) && (! myconfig.get(db, "hardcore_image_hrefs").equals("direct"))) || ((mypage.getContentClass().equals("file")) && (! myconfig.get(db, "hardcore_file_hrefs").equals("direct"))) || (old_server_filename.equals(""))) {
			if ((mypage.getContentClass().equals("image")) || (mypage.getContentClass().equals("file")) || (old_server_filename.equals(""))) {
				server_filename = Common.findUniqueFilename(Common.getRealPath(server, myconfig.get(db, "URLrootpath")), server_filename, mypage.getContentClass(), filepost.getParameter("file.filenameextension"), text, server, db, myconfig, mysession, myrequest, myresponse);
			}
			Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + server_filename));
			if (Common.moveFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + upload_filename), Common.getRealPath(server, myconfig.get(db, "URLrootpath") + server_filename))) {
				mypage.setServerFilename(server_filename);
			}
			return true;
		} else {
			Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + upload_filename));
			error = text.display("error.content.upload.format") + " " + filepost.getParameter("file.filenameextension");
			mypage.setServerFilename(old_server_filename);
			return false;
		}
	}



	private void setServerFilenameForContentClass(Page mypage, Fileupload filepost, Configuration myconfig, DB db) {
		if (mypage.getContentClass().equals("image")) {
			mypage.setServerFilename(filepost.getParameter("file.server_filename").replaceAll(myconfig.get(db, "URLuploadpath"), myconfig.get(db, "URLimagepath")));
		} else if (mypage.getContentClass().equals("file")) {
			mypage.setServerFilename(filepost.getParameter("file.server_filename").replaceAll(myconfig.get(db, "URLuploadpath"), myconfig.get(db, "URLfilepath")));
		}
	}



	private void handleArchive(ServletContext server, Page mypage, Fileupload filepost, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		if (! myrequest.getParameter("archive").equals("")) {
			doArchive(server, mypage, mysession, myrequest, myresponse, myconfig, db);
		} else if ((filepost != null) && (! filepost.getParameter("archive").equals(""))) {
			doArchive(server, mypage, mysession, myrequest, myresponse, myconfig, db);
		} else if (myconfig.get(db, "use_archive").equals("auto-on-save")) {
			doArchive(server, mypage, mysession, myrequest, myresponse, myconfig, db);
		} else if ((! myrequest.getParameter("publish").equals("")) && (myconfig.get(db, "use_archive").equals("auto-on-publish"))) {
			doArchive(server, mypage, mysession, myrequest, myresponse, myconfig, db);
		} else if ((filepost != null) && (! filepost.getParameter("publish").equals("")) && (myconfig.get(db, "use_archive").equals("auto-on-publish"))) {
			doArchive(server, mypage, mysession, myrequest, myresponse, myconfig, db);
		}
	}



	private void doArchive(ServletContext server, Page mypage, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		mypage.archive(db, myconfig);
		String archiveid = mypage.getArchiveId();
		if ((! mypage.getServerFilename().equals("")) && ((mypage.getContentClass().equals("image")) || (mypage.getContentClass().equals("file")))) {
			String filename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + mypage.getServerFilename());
			Common.copyFile(filename, filename + "." + archiveid);
			Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/copy"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + mypage.getServerFilename() + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + mypage.getServerFilename() + "." + archiveid + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:doArchive1\"", "", server, mysession, myrequest, myresponse);
		}
	}



	private void handleSchedule(ServletContext server, Page mypage, Fileupload filepost, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (! myrequest.getParameter("schedule").equals("")) {
			doSchedule(server, mypage, mysession, myrequest, myresponse, myconfig, db);
		} else if ((filepost != null) && (! filepost.getParameter("schedule").equals(""))) {
			doSchedule(server, mypage, mysession, myrequest, myresponse, myconfig, db);
		}
	}



	private void doSchedule(ServletContext server, Page mypage, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (! mypage.getScheduledPublish().equals("")) {
			String SQL = "select * from content_archive where (id=" + Common.integer(mypage.getId()) + ")";
			if (! mypage.getScheduledUnpublish().equals("")) {
				SQL += " and (" + db.is_not_blank("published") + ")";
				SQL += " and (published>=" + db.quote(mypage.getScheduledPublish()) + ")";
				SQL += " and (published<" + db.quote(mypage.getScheduledUnpublish()) + ")";
			} else  {
				SQL += " and (" + db.is_not_blank("published") + ")";
				SQL += " and (published=" + db.quote(mypage.getScheduledPublish()) + ")";
			}
			Content scheduledcontent = new Content();
			scheduledcontent.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (scheduledcontent.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				scheduledcontent.archive_delete(db, myconfig, scheduledcontent.getArchiveId(), Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
			}

			String save_published = "";
			String save_unpublished = "" + mypage.getUnpublished();
			String save_unpublished_by = "" + mypage.getUnpublishedBy();
			mypage.setPublished(mypage.getScheduledPublish());
			mypage.setUnpublished("");
			mypage.setUnpublishedBy("");
			mypage.archive(db, myconfig);
			String archiveid = mypage.getArchiveId();
			if ((! mypage.getServerFilename().equals("")) && ((mypage.getContentClass().equals("image")) || (mypage.getContentClass().equals("file")))) {
				String filename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + mypage.getServerFilename());
				Common.copyFile(filename, filename + "." + archiveid);
				Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/copy"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + mypage.getServerFilename() + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + mypage.getServerFilename() + "." + archiveid + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:doSchedule1\"", "", server, mysession, myrequest, myresponse);
			}
			if ((mypage.getPublished().compareTo(myconfig.get(db, "scheduled_next")) < 0) || (myconfig.get(db, "scheduled_next").equals(""))) {
				myconfig.set(db, "scheduled_next", mypage.getPublished());
			}
			mypage.setPublished(save_published);
			mypage.setUnpublished(save_unpublished);
			mypage.setUnpublishedBy(save_unpublished_by);
		}
	}



	private void handleRestore(ServletContext server, Page mypage, Fileupload filepost, Request myrequest, Response myresponse, Session mysession, Configuration myconfig, DB db) throws Exception {
		if ((! filepost.getParameter("restore").equals("")) && (! filepost.getParameter("restore").equals("public"))) {
			doRestore(server, mypage, filepost, myrequest, myresponse, mysession, myconfig, db);
		}
	}



	private void doRestore(ServletContext server, Page mypage, Fileupload filepost, Request myrequest, Response myresponse, Session mysession, Configuration myconfig, DB db) throws Exception {
		Page publishedfile = new Page(text);
		publishedfile.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		Page archivefile = new Page(text);
		archivefile.archive_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, filepost.getParameter("restore"), "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		String archivefilename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + archivefile.getServerFilename());
		if (Common.fileExists(archivefilename + "." + filepost.getParameter("restore"), text, server, db, myconfig, mysession, myrequest, myresponse)) {
			if ((filepost != null) && (! filepost.getParameter("publish").equals("")) && (! publishedfile.getServerFilename().equals("")) && (! mypage.getServerFilename().equals(publishedfile.getServerFilename()))) {
				// restore to changed published filename
				String restorefilename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + mypage.getServerFilename());
				Common.copyFile(archivefilename + "." + filepost.getParameter("restore"), restorefilename);
				Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/copy"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + archivefile.getServerFilename() + "." + filepost.getParameter("restore") + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + mypage.getServerFilename() + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:doRestore1\"", "", server, mysession, myrequest, myresponse);
				mypage.setServerFilename(mypage.getServerFilename());
			} else if ((filepost != null) && (! filepost.getParameter("publish").equals("")) && (! publishedfile.getServerFilename().equals(""))) {
				// restore to unchanged published filename
				String restorefilename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + publishedfile.getServerFilename());
				Common.copyFile(archivefilename + "." + filepost.getParameter("restore"), restorefilename);
				Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/copy"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + archivefile.getServerFilename() + "." + filepost.getParameter("restore") + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + publishedfile.getServerFilename() + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:doRestore2\"", "", server, mysession, myrequest, myresponse);
				mypage.setServerFilename(publishedfile.getServerFilename());
			} else  if ((! mypage.getServerFilename().equals("")) && (! mypage.getServerFilename().equals(publishedfile.getServerFilename()))) {
				// restore to unpublished filename
				String restorefilename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + mypage.getServerFilename());
				Common.copyFile(archivefilename + "." + filepost.getParameter("restore"), restorefilename);
				Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/copy"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + archivefile.getServerFilename() + "." + filepost.getParameter("restore") + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + mypage.getServerFilename() + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:doRestore3\"", "", server, mysession, myrequest, myresponse);
				mypage.setServerFilename(mypage.getServerFilename());
			} else  if ((! mypage.getServerFilename().equals("")) && (mypage.getServerFilename().equals(publishedfile.getServerFilename()))) {
				// restore to new unpublished filename
				String new_server_filename = Common.findUniqueFilename(Common.getRealPath(server, myconfig.get(db, "URLrootpath")), mypage.getServerFilename(), text, server, db, myconfig, mysession, myrequest, myresponse);
				String restorefilename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename);
				Common.copyFile(archivefilename + "." + filepost.getParameter("restore"), restorefilename);
				Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/copy"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + archivefile.getServerFilename() + "." + filepost.getParameter("restore") + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + new_server_filename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:doRestore4\"", "", server, mysession, myrequest, myresponse);
				mypage.setServerFilename(new_server_filename);
			} else {
				// dummy fallback restore to archived filename - should never occur
				Common.copyFile(archivefilename + "." + filepost.getParameter("restore"), archivefilename);
				Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/copy"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + archivefile.getServerFilename() + "." + filepost.getParameter("restore") + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + archivefile.getServerFilename() + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:doRestore5\"", "", server, mysession, myrequest, myresponse);
				mypage.setServerFilename(archivefile.getServerFilename());
			}
			mypage.setUploadFilename(archivefile.getUploadFilename());
		}
	}



	private void handleStaticAddressLinks(ServletContext server, Page mypage, Fileupload filepost, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String new_server_filename, String old_server_filename) throws Exception {
		mypage.handleStaticAddressLinks(server, mysession, myrequest, myresponse, myconfig, db, old_server_filename, new_server_filename);
	}



	public void handleStaticAddress(ServletContext server, Page mypage, Fileupload filepost, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String server_filename) throws Exception {
		if ((mypage.getContentClass().equals("image")) || (mypage.getContentClass().equals("file"))) {
			mypage.publishServerFilename(server, myrequest, myresponse, mysession, myconfig, db);
		} else if (((mypage.getContentClass().equals("page")) || (mypage.getContentClass().equals("product")) || (mypage.getContentClass().equals("stylesheet")) || (mypage.getContentClass().equals("script"))) && (myconfig.get(db, "use_static_filenames").equals("yes"))) {
			if ((! filepost.parameterExists("server_filename")) && (server_filename.equals(""))) {
				mypage.publishServerFilename(server, myrequest, myresponse, mysession, myconfig, db);
				return;
			}
 			String old_server_filename = mypage.getServerFilename();
			String new_server_filename = filepost.getParameter("server_filename");
			if (! server_filename.equals("")) {
				new_server_filename = server_filename;
			}
			Page publishedpage = new Page(text);
			publishedpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getId(), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			String old_published_filename = publishedpage.getServerFilename();
			String new_published_filename = "";
			if (((! server_filename.equals("")) || (! myrequest.getParameter("publish").equals("")) || ((filepost != null) && (! filepost.getParameter("publish").equals("")))) && (mypage.getPublisher())) {
				new_published_filename = "" + new_server_filename;
			} else {
				new_published_filename = "" + old_published_filename;
			}
			if ((! new_server_filename.equals("")) && (! old_server_filename.equals(new_server_filename)) && (! old_published_filename.equals(new_server_filename)) && ((Common.fileExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename), text, server, db, myconfig, mysession, myrequest, myresponse)) || (Common.fileExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename + "/index.jsp"), text, server, db, myconfig, mysession, myrequest, myresponse)))) {
				error += "<br>" + text.display("error.content.upload.filename.used") + new_server_filename;
			} else {
				if ((! mypage.getScheduledPublish().equals("")) && (mypage.getScheduledPublish().compareTo(mypage.getUpdated()) > 0)) {
					// scheduled publishing - do not delete old published filename
				} else if ((! old_published_filename.equals("")) && (! old_published_filename.equals(new_server_filename)) && (! old_published_filename.equals(new_published_filename))) {
					if ((old_published_filename != null) && (! old_published_filename.equals(""))) {
						if ((myconfig.get(db, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.html?$").matcher(old_published_filename).find())) {
							if (License.valid(db, myconfig, "enterprise")) {
								Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + old_published_filename + "\"");
							}
							publishedpage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
							publishedpage.deleteExportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
						} else if ((myconfig.get(db, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.css$").matcher(old_published_filename).find())) {
							if (License.valid(db, myconfig, "enterprise")) {
								Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + old_published_filename + "\"");
							}
							publishedpage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
							publishedpage.deleteExportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
						} else if ((myconfig.get(db, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.js$").matcher(old_published_filename).find())) {
							if (License.valid(db, myconfig, "enterprise")) {
								Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + old_published_filename + "\"");
							}
							publishedpage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
							publishedpage.deleteExportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
						}
for (int i=1; i<10; i++) {
						if (Common.fileExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_published_filename + "/index.jsp"), text, server, db, myconfig, mysession, myrequest, myresponse)) {
							publishedpage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
							publishedpage.deleteExportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
						} else if (Common.fileExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_published_filename), text, server, db, myconfig, mysession, myrequest, myresponse)) {
							publishedpage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
							publishedpage.deleteExportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
						}
}
					}
				}
				if ((! mypage.getScheduledPublish().equals("")) && (mypage.getScheduledPublish().compareTo(mypage.getUpdated()) > 0)) {
					// scheduled publishing - do not delete old published filename
				} else if ((! old_server_filename.equals("")) && (! old_server_filename.equals(new_server_filename)) && (! old_server_filename.equals(new_published_filename))) {
					String save_published = mypage.getPublished();
					if (old_published_filename.equals("")) {
						mypage.setPublished("");
					}
					if ((old_server_filename != null) && (! old_server_filename.equals(""))) {
						if ((myconfig.get(db, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.html?$").matcher(old_server_filename).find())) {
							if (License.valid(db, myconfig, "enterprise")) {
								Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + old_server_filename + "\"");
							}
							mypage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
							mypage.deleteExportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
						} else if ((myconfig.get(db, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.css$").matcher(old_server_filename).find())) {
							if (License.valid(db, myconfig, "enterprise")) {
								Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + old_server_filename + "\"");
							}
							mypage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
							mypage.deleteExportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
						} else if ((myconfig.get(db, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.js$").matcher(old_server_filename).find())) {
							if (License.valid(db, myconfig, "enterprise")) {
								Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + old_server_filename + "\"");
							}
							mypage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
							mypage.deleteExportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
						}
for (int i=1; i<10; i++) {
						if (Common.fileExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename + "/index.jsp"), text, server, db, myconfig, mysession, myrequest, myresponse)) {
							mypage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
							mypage.deleteExportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
						} else if (Common.fileExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename), text, server, db, myconfig, mysession, myrequest, myresponse)) {
							mypage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
							mypage.deleteExportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
						}
}
					}
					mypage.setPublished(save_published);
				}
				if ((! new_server_filename.equals("")) && (! new_server_filename.equals(old_server_filename))) {
					String filecontent = "";
					if ((myconfig.get(db, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.html?$").matcher(new_server_filename).find())) {
//						filecontent = Common.readFile(Common.getRealPath(server, "/" + mypage.getContentClass() + ".original.html"));
//handled elsewhere by Page.outputStaticFile
					} else if ((myconfig.get(db, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.css$").matcher(new_server_filename).find())) {
//						filecontent = Common.readFile(Common.getRealPath(server, "/" + mypage.getContentClass() + ".original.css"));
//handled elsewhere by Page.outputStaticFile
					} else if ((myconfig.get(db, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.js$").matcher(new_server_filename).find())) {
//						filecontent = Common.readFile(Common.getRealPath(server, "/" + mypage.getContentClass() + ".original.js"));
//handled elsewhere by Page.outputStaticFile
					} else if (myconfig.get(db, "URLrootpath").equals("/")) {
						filecontent = Common.readFile(Common.getRealPath(server, "/" + mypage.getContentClass() + ".original.jsp"));
					} else {
						filecontent = Common.readFile(Common.getRealPath(server, "/" + mypage.getContentClass() + ".original.hosting.jsp"));
					}
					filecontent = filecontent.replaceAll("myrequest\\.getParameter\\(\"id\"\\)", "\"" + mypage.getId() + "\"");
					if (myconfig.get(db, "use_static_content").equals("no")) {
						// ignore
					} else if ((mypage.getContentClass().equals("page")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.html?$").matcher(new_server_filename).find()) && (myconfig.get(db, "use_static_content").equals("yes"))) {
//						Common.createFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename));
//						Common.writeFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename), filecontent, myconfig.get(db, "charset"));
//handled elsewhere by Page.outputStaticFile
					} else if ((mypage.getContentClass().equals("stylesheet")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.css$").matcher(new_server_filename).find()) && (myconfig.get(db, "use_static_content").equals("yes"))) {
//						Common.createFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename));
//						Common.writeFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename), filecontent, myconfig.get(db, "charset"));
//handled elsewhere by Page.outputStaticFile
					} else if ((mypage.getContentClass().equals("script")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.js$").matcher(new_server_filename).find()) && (myconfig.get(db, "use_static_content").equals("yes"))) {
//						Common.createFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename));
//						Common.writeFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename), filecontent, myconfig.get(db, "charset"));
//handled elsewhere by Page.outputStaticFile
					} else if (! Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.jsp$").matcher(new_server_filename).find()) {
						Common.createFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename + "/index.jsp"));
						Common.writeFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename + "/index.jsp"), filecontent, myconfig.get(db, "charset"));
//						if (License.valid(db, myconfig, "enterprise")) {
//							Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/published"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + new_server_filename + "/index.jsp" + "\"");
//						}
//handled elsewhere by Page.outputStaticFile
					} else {
						Common.createFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename));
						Common.writeFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename), filecontent, myconfig.get(db, "charset"));
//						if (License.valid(db, myconfig, "enterprise")) {
//							Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/published"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + new_server_filename + "\"");
//						}
//handled elsewhere by Page.outputStaticFile
					}
				}
				if (! mypage.getServerFilename().equals(new_server_filename)) {
					mypage.setServerFilename("" + new_server_filename);
					mypage.update(db, myconfig, mypage.getId(), "content", "id");
				}
			}
		}
	}



	private void handlePublish(ServletContext server, Page mypage, Fileupload filepost, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		if (((! myrequest.getParameter("publish").equals("")) || ((filepost != null) && (! filepost.getParameter("publish").equals("")))) && (mypage.getPublisher())) {
			String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			if ((! mypage.getScheduledPublish().equals("")) && (mypage.getScheduledPublish().compareTo(now) > 0)) {
				if ((mypage.getScheduledPublish().compareTo(myconfig.get(db, "scheduled_next")) < 0) || (myconfig.get(db, "scheduled_next").equals(""))) {
					myconfig.set(db, "scheduled_next", mypage.getScheduledPublish());
				}
			} else {
				if (myconfig.get(db, "use_checkin").equals("auto-on-publish")) {
					mypage.checkin(db, mysession.get("username"), myconfig.get(db, "superadmin"));
					if (myconfig.get(db, "use_archive").equals("auto-on-checkin")) {
						doArchive(server, mypage, mysession, myrequest, myresponse, myconfig, db);
					}
				}

				Cms.CMSAudit("action=publish content=" + mypage.getTitle() + " [" + mypage.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
				if (mypage.exists(db, mypage.getId(), "content_public", "id", "", "", "", "", "", mysession)) {
					mypage.update(db, myconfig, mypage.getId(), "content_public", "id");
				} else {
					mypage.create(db, myconfig, "content_public", "id");
				}
				if (! mypage.getScheduledUnpublish().equals("")) {
					if ((mypage.getScheduledUnpublish().compareTo(myconfig.get(db, "scheduled_next")) < 0) || (myconfig.get(db, "scheduled_next").equals(""))) {
						myconfig.set(db, "scheduled_next", mypage.getScheduledUnpublish());
					}
				}
			}
		}
	}



	private void handleStaticFile(ServletContext server, Page mypage, Fileupload filepost, Request myrequest, Response myresponse, Session mysession, Configuration myconfig, DB db) {
		if (((! myrequest.getParameter("publish").equals("")) || ((filepost != null) && (! filepost.getParameter("publish").equals("")))) && (mypage.getPublisher())) {
			if (! mypage.getScheduledPublish().equals("")) {
				// ignore - publish later
			} else {
				mypage.outputStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
				mypage.exportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
				if (mypage.getContentClass().equals("template")) {
					String SQL = "select id from content_public where (template=" + db.quote(mypage.getId());
					if (mypage.getId().equals(myconfig.get(db, "default_template"))) {
						SQL += " or " + db.is_blank("template");
					}
					SQL += ")";
//					if (myconfig.get(db, "export_rootpath").equals("")) {
//						// do not export static pages - so no need to update content without static filename
//						SQL += " and " + db.is_not_blank("server_filename");
//					
//					}
					boolean parse_output = ((myconfig.get(db, "use_static_content").equals("yes")) || (! myconfig.get(db, "export_rootpath").equals("")));
					Page templatedpages = new Page();
					templatedpages.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
					while (templatedpages.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
						try {
							String myid = templatedpages.getId();
							Page mytemplatedpage = getPublishedPageById(myid, server, mysession, myrequest, myresponse, myconfig, db, parse_output);
							mytemplatedpage.outputStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
							mytemplatedpage.exportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
						} catch (Exception e) {
						}
					}
				}
			}
		}
	}



	private void handleSave(ServletContext server, Page mypage, Fileupload filepost, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		if (myconfig.get(db, "use_checkin").equals("auto-on-save")) {
			mypage.checkin(db, mysession.get("username"), myconfig.get(db, "superadmin"));
			if (myconfig.get(db, "use_archive").equals("auto-on-checkin")) {
				doArchive(server, mypage, mysession, myrequest, myresponse, myconfig, db);
			}
		}

		if (myconfig.get(db, "use_checkout").equals("auto-on-save")) {
			mypage.checkout(db, mysession.get("username"));
		}
	}



	private void handleWorkflowAuto(ServletContext server, Page mypage, Workflow workflow, Fileupload filepost, Request myrequest, Response myresponse, Session mysession, Configuration myconfig, DB db, String published_server_filename, String username, String now) throws Exception {
		if (myconfig.get(db, "use_workflow").equals("yes")) {
			if (workflow.getAutoCheckin()) {
				mypage.checkin(db, mysession.get("username"), myconfig.get(db, "superadmin"));
				if (myconfig.get(db, "use_archive").equals("auto-on-checkin")) {
					doArchive(server, mypage, mysession, myrequest, myresponse, myconfig, db);
				}
			}
			if (workflow.getAutoCheckout()) {
//				if (((mypage.getEditor()) && (mypage.getScheduledPublish().equals(""))) || (mypage.getPublisher()) && ((! myconfig.get(db, "use_workflow").equals("yes")) || (mypage.getAdministrator()) || (workflow.permissions(db, mypage.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (mypage.getStatusBy().equals(mysession.get("username"))))))) {
					mypage.checkout(db, mysession.get("username"));
//				}
			}
			if (workflow.getAutoUnpublish()) {
				if ((! published_server_filename.equals("")) && ((! published_server_filename.equals(mypage.getServerFilename())) || (myconfig.get(db, "use_static_content").equals("yes")))) {
					String save_server_filename = mypage.getServerFilename();
					mypage.setServerFilename(published_server_filename);
					mypage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
					mypage.setServerFilename(save_server_filename);
				} else {
					mypage.unpublishStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
				}
				mypage.unpublish(db, username, now);
				mypage.public_delete(db, myconfig, mypage.getId(), Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
				mypage.deleteExportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
				mypage.unpublishServerFilename(server, myrequest, myresponse, mysession, myconfig, db, published_server_filename);
			}
			if (workflow.getAutoDelete()) {
				mypage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
				mypage.public_delete(db, myconfig, mypage.getId(), Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
				mypage.archive_delete_all(db, myconfig, mypage.getId(), Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
				mypage.preview_delete(db, myconfig, mypage.getId(), Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
				mypage.deleteExportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
			}
		}
		if (! workflow.getWorkflowProgram().equals("")) {
			String output = Common.execute("/" + text.display("adminpath") + "/workflowaction/" + workflow.getWorkflowProgram() + ".jsp", "content", mypage, "workflow", workflow, "workflowaction", server, mysession, myrequest, myresponse);
		}
	}



	private void handleEmailNotification(ServletContext server, Page mypage, Workflow workflow, String username, Fileupload filepost, Request myrequest, Response myresponse, Session mysession, Configuration myconfig, DB db) throws Exception {
		String subject = "";
		String body = "";
		Page notification = new Page(text);
		if (! filepost.getParameter("email_template").equals("")) {
			notification.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, filepost.getParameter("email_template"), "", myconfig.get(db, "default_version"), "", "", "", "", "", myconfig.get(db, "default_template"), "", "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			subject = "" + notification.getTitle() + " " + mypage.getTitle();
			body = "" + notification.getContent();
		} else if (! workflow.getNotifyEmail().equals("")) {
			filepost.setParameter("ready_to_publish", "yes");
			notification.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, workflow.getNotifyEmail(), "", myconfig.get(db, "default_version"), "", "", "", "", "", myconfig.get(db, "default_template"), "", "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			subject = "" + notification.getTitle() + " " + mypage.getTitle();
			body = "" + notification.getContent();
		} else if (! myconfig.get(db, "default_publish_ready").equals("")) {
			notification.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_publish_ready"), "", myconfig.get(db, "default_version"), "", "", "", "", "", myconfig.get(db, "default_template"), "", "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			subject = "" + notification.getTitle() + " " + mypage.getTitle();
			body = "" + notification.getContent();
		}
		if (body.equals("")) {
			subject = "Website content updated and ready to publish: " + mypage.getTitle();
			body = "<p>The website administrator '@@@username@@@' has updated the content item '@@@title@@@' and marked it as 'Ready To Publish'.</p>" + "\r\n" + "\r\n";
			body += "<p>View current:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"http://" + myrequest.getServerName() + "/" + mypage.getContentClass() + ".jsp?id=" + mypage.getId() + "\">" + "http://" + myrequest.getServerName() + "/" + mypage.getContentClass() + ".jsp?id=" + mypage.getId() + "</a></p>" + "\r\n";
			body += "<p>Preview new:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@preview@@@\">@@@preview@@@</a></p>" + "\r\n";
			body += "<p>Update/publish:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@update@@@\">@@@update@@@</a></p>" + "\r\n";
			body += "<p>Delete:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@delete@@@\">@@@delete@@@</a></p>" + "\r\n";
		}
		body = body.replaceAll("@@@id@@@", mypage.getId());
		body = body.replaceAll("@@@class@@@", mypage.getContentClass());
		body = body.replaceAll("@@@package@@@", mypage.getContentPackage());
		body = body.replaceAll("@@@bundle@@@", mypage.getContentBundle());
		body = body.replaceAll("@@@group@@@", mypage.getContentGroup());
		body = body.replaceAll("@@@type@@@", mypage.getContentType());
		body = body.replaceAll("@@@status@@@", mypage.getStatus().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (! myrequest.getParameter("comments").equals("")) {
			body = body.replaceAll("@@@comments@@@", myrequest.getParameter("comments").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		} else {
			body = body.replaceAll("@@@comments@@@", filepost.getParameter("comments").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		}
		body = body.replaceAll("@@@title@@@", mypage.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		body = body.replaceAll("@@@content@@@", mypage.getContent().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		body = body.replaceAll("@@@summary@@@", mypage.getSummary().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		body = body.replaceAll("@@@revision@@@", mypage.getRevision().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")).replaceAll("\\r\\n","<br>\r\n");
		body = body.replaceAll("@@@username@@@", username.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		User myuser = new User();
		myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, username);
		body = body.replaceAll("@@@name@@@", myuser.getName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		body = body.replaceAll("@@@preview@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + mypage.getContentClass() + ".jsp?id=" + mypage.getId() + "&mode=preview&version=&");
		body = body.replaceAll("@@@view@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/view.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
		body = body.replaceAll("@@@update@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/update.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
		body = body.replaceAll("@@@delete@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/delete.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
		body = mypage.parse_output_replace_inputs(myrequest, filepost, body);
		handleNotification(subject, body, server, mypage, workflow, filepost, myrequest, myresponse, mysession, myconfig, db);
		if (myconfig.get(db, "use_version_notifications").equals("yes")) {
			body = "";
			if (! myconfig.get(db, "default_version_notification").equals("")) {
				notification.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_version_notification"), "", myconfig.get(db, "default_version"), "", "", "", "", "", myconfig.get(db, "default_template"), "", "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				subject = "" + notification.getTitle() + " " + mypage.getTitle();
				body = "" + notification.getContent();
			}
			if (body.equals("")) {
				subject = "Master/default version website content updated: " + mypage.getTitle();
				body = "<p>The website administrator '@@@username@@@' has updated the content item '@@@title@@@'.</p>" + "\r\n" + "\r\n";
				body += "<p>View master/default version:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"http://" + myrequest.getServerName() + "/" + mypage.getContentClass() + ".jsp?id=" + mypage.getId() + "\">" + "http://" + myrequest.getServerName() + "/" + mypage.getContentClass() + ".jsp?id=" + mypage.getId() + "</a></p>" + "\r\n";
				body += "<p>Preview master/default version:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@preview@@@\">@@@preview@@@</a></p>" + "\r\n";
				body += "<p>View @@@version@@@ version:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"http://" + myrequest.getServerName() + "/@@@version_class@@@.jsp?id=@@@version_id@@@\">" + "http://" + myrequest.getServerName() + "/@@@version_class@@@.jsp?id=@@@version_id@@@</a></p>" + "\r\n";
				body += "<p>Preview @@@version@@@ version:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@version_preview@@@\">@@@version_preview@@@</a></p>" + "\r\n";
				body += "<p>Update/publish @@@version@@@ version:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@version_update@@@\">@@@version_update@@@</a></p>" + "\r\n";
				body += "<p>Delete @@@version@@@ version:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@version_delete@@@\">@@@version_delete@@@</a></p>" + "\r\n";
			}
			body = body.replaceAll("@@@id@@@", mypage.getId());
			body = body.replaceAll("@@@class@@@", mypage.getContentClass());
			body = body.replaceAll("@@@package@@@", mypage.getContentPackage());
			body = body.replaceAll("@@@bundle@@@", mypage.getContentBundle());
			body = body.replaceAll("@@@group@@@", mypage.getContentGroup());
			body = body.replaceAll("@@@type@@@", mypage.getContentType());
			body = body.replaceAll("@@@status@@@", mypage.getStatus().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (! myrequest.getParameter("comments").equals("")) {
				body = body.replaceAll("@@@comments@@@", myrequest.getParameter("comments").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			} else {
				body = body.replaceAll("@@@comments@@@", filepost.getParameter("comments").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			}
			body = body.replaceAll("@@@title@@@", mypage.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			body = body.replaceAll("@@@content@@@", mypage.getContent().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			body = body.replaceAll("@@@summary@@@", mypage.getSummary().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			body = body.replaceAll("@@@revision@@@", mypage.getRevision().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")).replaceAll("\\r\\n","<br>\r\n");
			body = body.replaceAll("@@@username@@@", username.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			myuser = new User();
			myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, username);
			body = body.replaceAll("@@@name@@@", myuser.getName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			body = body.replaceAll("@@@preview@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + mypage.getContentClass() + ".jsp?id=" + mypage.getId() + "&mode=preview&version=&");
			body = body.replaceAll("@@@view@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/view.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
			body = body.replaceAll("@@@update@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/update.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
			body = body.replaceAll("@@@delete@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/delete.jsp?id=" + mypage.getId() + "&redirect=/" + text.display("adminpath") + "/index.jsp");
			body = mypage.parse_output_replace_inputs(myrequest, filepost, body);
			handleVersionNotification(subject, body, server, mypage, filepost, myrequest, myresponse, mysession, myconfig, db);
		}
	}



	private void handleNotification(String subject, String body, ServletContext server, Page mypage, Workflow workflow, Fileupload filepost, Request myrequest, Response myresponse, Session mysession, Configuration myconfig, DB db) throws Exception {
		if ((! myrequest.getParameter("ready_to_publish").equals("")) || ((filepost != null) && (! filepost.getParameter("ready_to_publish").equals("")))) {
			sendNotification(subject, body, server, mypage, workflow, filepost, myrequest, myresponse, mysession, myconfig, db);
		}
	}



	private void handleVersionNotification(String subject, String bodytemplate, ServletContext server, Page mypage, Fileupload filepost, Request myrequest, Response myresponse, Session mysession, Configuration myconfig, DB db) throws Exception {
		if ((((! myrequest.getParameter("ready_to_publish").equals("")) || ((filepost != null) && (! filepost.getParameter("ready_to_publish").equals("")))) || (((! myrequest.getParameter("publish").equals("")) || ((filepost != null) && (! filepost.getParameter("publish").equals("")))) && mypage.getPublisher())) && (! mypage.getId().equals(""))) {
			if (myconfig.get(db, "use_version_notifications").equals("yes")) {
				String SQL = "select * from content where version_master=" + db.quote(mypage.getId());
				Page notificationpage = new Page();
				notificationpage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
				while (notificationpage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
					String body = "" + bodytemplate;
					body = body.replaceAll("@@@version@@@", notificationpage.getVersion().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					body = body.replaceAll("@@@version_id@@@", notificationpage.getId());
					body = body.replaceAll("@@@version_class@@@", notificationpage.getContentClass());
					body = body.replaceAll("@@@version_package@@@", notificationpage.getContentPackage());
					body = body.replaceAll("@@@version_bundle@@@", notificationpage.getContentBundle());
					body = body.replaceAll("@@@version_group@@@", notificationpage.getContentGroup());
					body = body.replaceAll("@@@version_type@@@", notificationpage.getContentType());
					body = body.replaceAll("@@@version_title@@@", notificationpage.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					body = body.replaceAll("@@@version_preview@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + notificationpage.getContentClass() + ".jsp?id=" + notificationpage.getId() + "&mode=preview&version=&");
					body = body.replaceAll("@@@version_view@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/view.jsp?id=" + notificationpage.getId());
					body = body.replaceAll("@@@version_update@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/update.jsp?id=" + notificationpage.getId());
					body = body.replaceAll("@@@version_delete@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + text.display("adminpath") + "/content/delete.jsp?id=" + notificationpage.getId());
					sendNotification(subject, body, server, notificationpage, null, filepost, myrequest, myresponse, mysession, myconfig, db);
				}
			}
		}
	}



	private void sendNotification(String subject, String body, ServletContext server, Page mypage, Workflow workflow, Fileupload filepost, Request myrequest, Response myresponse, Session mysession, Configuration myconfig, DB db) throws Exception {
		String admin_email = "";
		String[] assigned_emails = null;
		if (mypage.getCheckedOut().equals("-creators-")) {
			assigned_emails = mypage.creatorsEmails(mysession, myconfig, db);
		} else if (mypage.getCheckedOut().equals("-editors-")) {
			assigned_emails = mypage.editorsEmails(mysession, myconfig, db);
		} else if (mypage.getCheckedOut().equals("-developers-")) {
			assigned_emails = mypage.developersEmails(mysession, myconfig, db);
		} else if (mypage.getCheckedOut().equals("-publishers-")) {
			assigned_emails = mypage.publishersEmails(mysession, myconfig, db);
		} else if (mypage.getCheckedOut().equals("-administrators-")) {
			assigned_emails = mypage.administratorsEmails(mysession, myconfig, db);
		} else if (! mypage.getCheckedOut().equals("")) {
			User myuser = new User();
			myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mypage.getCheckedOut());
			admin_email = myuser.getEmail();
		}
		String[] workflow_emails = null;
		if ((workflow != null) && (myconfig.get(db, "use_workflow").equals("yes"))) {
			if (mypage.getStatus().equals("")) {
				if (mypage.getPublished().equals(mypage.getUpdated())) {
					workflow_emails = mypage.publishersEmails(mysession, myconfig, db);
				} else if (mypage.getPublished().equals("")) {
					workflow_emails = mypage.publishersEmails(mysession, myconfig, db);
				}
			} else {
				workflow_emails = workflow.adminEmails(mypage, mysession, myconfig, db);
			}
		} else {
			workflow_emails = mypage.publishersEmails(mysession, myconfig, db);
		}
		if (! admin_email.equals("")) {
			// assigned to specific administrator
		} else if ((assigned_emails != null) && (workflow_emails != null)) {
			// assigned to administrator role with workflow permissions
			admin_email = Common.join(", ", Common.array_intersect(assigned_emails, workflow_emails));
		} else {
			// administrators with workflow permissions
			admin_email = Common.join(", ", workflow_emails);
		}
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
		if ((workflow != null) && (myconfig.get(db, "use_workflow").equals("yes")) && (myconfig.get(db, "workflow_notify_from").equals("superadmin"))) {
			from = myconfig.get(db, "superadmin_email");
		}
		if (from.indexOf("@")<0) {
			from = from + "@" + myrequest.getServerName();
		}
//		Email.send_email(text, new HashMap<String,String>(), subject, body, "", from, admin_email, myrequest.getServerName(), mysession, myrequest, myresponse, myconfig, db);
		HashMap<String,String> requestForm = Email.getForm(myrequest);
		Email.send_email(text, requestForm, subject, body, "", from, admin_email, "", "", "", "", myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
	}



	private void sendUserNotification(String subject, String body, ServletContext server, User user, Fileupload filepost, Request myrequest, Response myresponse, Session mysession, Configuration myconfig, DB db) throws Exception {
		String admin_email = Common.join(", ", user.publishersEmails(mysession, myconfig, db));
		if (admin_email.equals("")) {
			admin_email = myconfig.get(db, "superadmin_email");
		}
		String from = "";
		if (! mysession.get("email").equals("")) {
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
//			Email.send_email(text, new HashMap(), subject, body, "", from, admin_email, myrequest.getServerName(), mysession, myrequest, myresponse, myconfig, db);
		HashMap<String,String> requestForm = Email.getForm(myrequest);
		Email.send_email(text, requestForm, subject, body, "", from, admin_email, "", "", "", "", myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
	}



	public String listContentDependenciesSettings(HashMap dependents, Session mysession, Configuration myconfig, DB db) {
		String settings = "";
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_template|") > -1) {
			settings += "<p>" + text.display("config.website.contentdesign.template") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_stylesheet|") > -1) {
			settings += "<p>" + text.display("config.website.contentdesign.stylesheet") + "</p>" + "\r\n";
		}
		return settings;
	}



	public String listContentDependenciesSpecialPages(HashMap dependents, Session mysession, Configuration myconfig, DB db) {
		String specialpages = "";
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_page|") > -1) {
			specialpages += "<p>" + text.display("config.website.special.page") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_login|") > -1) {
			specialpages += "<p>" + text.display("config.website.special.login") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_searchresults_page|") > -1) {
			specialpages += "<p>" + text.display("config.website.special.searchresults.page") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_searchresults_entry|") > -1) {
			specialpages += "<p>" + text.display("config.website.special.searchresults.entry") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_guestbook_page|") > -1) {
			specialpages += "<p>" + text.display("config.website.special.guestbook.page") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_guestbook_entry|") > -1) {
			specialpages += "<p>" + text.display("config.website.special.guestbook.entry") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_list_entry|") > -1) {
			specialpages += "<p>" + text.display("config.website.special.list.entry") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_publish_ready|") > -1) {
			specialpages += "<p>" + text.display("config.website.special.publish.ready") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_register_confirmation_page|") > -1) {
			specialpages += "<p>" + text.display("config.website.special.community.register.confirmation") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_register_notification_page|") > -1) {
			specialpages += "<p>" + text.display("config.website.special.community.register.notification") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_personal_admin_page|") > -1) {
			specialpages += "<p>" + text.display("config.website.special.community.personal.admin") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|retrieve_password_page|") > -1) {
			specialpages += "<p>" + text.display("config.website.special.password.retrieve.page") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|retrieve_password_confirmation|") > -1) {
			specialpages += "<p>" + text.display("config.website.special.password.retrieve.confirmation") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|retrieve_password_email|") > -1) {
			specialpages += "<p>" + text.display("config.website.special.password.retrieve.email") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|retrieve_password_error|") > -1) {
			specialpages += "<p>" + text.display("config.website.special.password.retrieve.error") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_shopcartsummary_page|") > -1) {
			specialpages += "<p>" + text.display("config.ecommerce.special.shopcartsummary.page") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_shopcartsummary_entry|") > -1) {
			specialpages += "<p>" + text.display("config.ecommerce.special.shopcartsummary.entry") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_shopcart_page|") > -1) {
			specialpages += "<p>" + text.display("config.ecommerce.special.shopcart.page") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_shopcart_entry|") > -1) {
			specialpages += "<p>" + text.display("config.ecommerce.special.shopcart.entry") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_checkout_page|") > -1) {
			specialpages += "<p>" + text.display("config.ecommerce.special.checkout.page") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_checkout_entry|") > -1) {
			specialpages += "<p>" + text.display("config.ecommerce.special.checkout.entry") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_confirm_page|") > -1) {
			specialpages += "<p>" + text.display("config.ecommerce.special.confirm.page") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_confirm_entry|") > -1) {
			specialpages += "<p>" + text.display("config.ecommerce.special.confirm.entry") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_complete_page|") > -1) {
			specialpages += "<p>" + text.display("config.ecommerce.special.complete.page") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_complete_entry|") > -1) {
			specialpages += "<p>" + text.display("config.ecommerce.special.complete.entry") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_confirmation_page|") > -1) {
			specialpages += "<p>" + text.display("config.ecommerce.special.confirmation.page") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_confirmation_entry|") > -1) {
			specialpages += "<p>" + text.display("config.ecommerce.special.confirmation.entry") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_notification_page|") > -1) {
			specialpages += "<p>" + text.display("config.ecommerce.special.notification.page") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_notification_entry|") > -1) {
			specialpages += "<p>" + text.display("config.ecommerce.special.notification.entry") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_ordertracking_page|") > -1) {
			specialpages += "<p>" + text.display("config.ecommerce.special.ordertracking.page") + "</p>" + "\r\n";
		}
		if ((dependents.get("0") != null) && ((String)dependents.get("0")).indexOf("|default_ordertracking_entry|") > -1) {
			specialpages += "<p>" + text.display("config.ecommerce.special.ordertracking.entry") + "</p>" + "\r\n";
		}
		return specialpages;
	}



	public String listContentDependenciesUsergroups(HashMap dependents, Session mysession, Configuration myconfig, DB db) {
		String usergroups = "";
		Iterator ids = dependents.keySet().iterator();
		while (ids.hasNext()) {
			String id = "" + ids.next();
			String dependencies = "" + dependents.get(id);
			if ((id.equals("0")) && (dependencies.indexOf("|usergroup_") > -1)) {
				String usergroup = dependencies.substring(dependencies.indexOf("|usergroup_")+11);
				usergroup = usergroup.substring(0, usergroup.indexOf("|"));
				usergroups += "<p>" + usergroup + "</p>" + "\r\n";
			}
		}
		return usergroups;
	}



	public String listContentDependenciesUsertypes(HashMap dependents, Session mysession, Configuration myconfig, DB db) {
		String usertypes = "";
		Iterator ids = dependents.keySet().iterator();
		while (ids.hasNext()) {
			String id = "" + ids.next();
			String dependencies = "" + dependents.get(id);
			if ((id.equals("0")) && (dependencies.indexOf("|usertype_") > -1)) {
				String usertype = dependencies.substring(dependencies.indexOf("|usertype_")+10);
				usertype = usertype.substring(0, usertype.indexOf("|"));
				usertypes += "<p>" + usertype + "</p>" + "\r\n";
			}
		}
		return usertypes;
	}



	public String listContentDependenciesUsers(HashMap dependents, Session mysession, Configuration myconfig, DB db) {
		String users = "";
		Iterator ids = dependents.keySet().iterator();
		while (ids.hasNext()) {
			String id = "" + ids.next();
			String dependencies = "" + dependents.get(id);
			if ((id.equals("0")) && (dependencies.indexOf("|user_") > -1)) {
				String user = dependencies.substring(dependencies.indexOf("|user_")+6);
				user = user.substring(0, user.indexOf("|"));
				users += "<p>" + user + "</p>" + "\r\n";
			}
		}
		return users;
	}



	public String listContentDependenciesWebsites(HashMap dependents, Session mysession, Configuration myconfig, DB db) {
		String websites = "";
		Iterator ids = dependents.keySet().iterator();
		while (ids.hasNext()) {
			String id = "" + ids.next();
			String dependencies = "" + dependents.get(id);
			if ((id.equals("0")) && (dependencies.indexOf("|website_") > -1)) {
				String website = dependencies.substring(dependencies.indexOf("|website_")+9);
				website = website.substring(0, website.indexOf("|"));
				websites += "<p>" + website + "</p>" + "\r\n";
			}
			if ((id.equals("0")) && (dependencies.indexOf("|hosting_") > -1)) {
				String website = dependencies.substring(dependencies.indexOf("|hosting_")+9);
				website = website.substring(0, website.indexOf("|"));
				websites += "<p>" + website + "</p>" + "\r\n";
			}
		}
		return websites;
	}



	public String listContentDependenciesPages(HashMap dependents, Session mysession, Configuration myconfig, DB db) {
		String pages = "";
		Iterator ids = dependents.keySet().iterator();
		while (ids.hasNext()) {
			String id = "" + ids.next();
			String dependencies = "" + dependents.get(id);
			if ((! id.equals("0")) && (id.matches("^[0-9]+$")) && ((dependencies.indexOf("|content|") > -1) || (dependencies.indexOf("|summary|") > -1) || (dependencies.indexOf("|template|") > -1) || (dependencies.indexOf("|stylesheet|") > -1) || (dependencies.indexOf("|script|") > -1) || (dependencies.indexOf("|version_master|") > -1) || (dependencies.indexOf("|page_up|") > -1) || (dependencies.indexOf("|page_top|") > -1) || (dependencies.indexOf("|page_next|") > -1) || (dependencies.indexOf("|page_previous|") > -1) || (dependencies.indexOf("|page_last|") > -1) || (dependencies.indexOf("|page_first|") > -1) || (dependencies.indexOf("|related|") > -1) || (dependencies.indexOf("|image1|") > -1) || (dependencies.indexOf("|image2|") > -1) || (dependencies.indexOf("|image3|") > -1) || (dependencies.indexOf("|file1|") > -1) || (dependencies.indexOf("|file2|") > -1) || (dependencies.indexOf("|file3|") > -1) || (dependencies.indexOf("|link1|") > -1) || (dependencies.indexOf("|link2|") > -1) || (dependencies.indexOf("|link3|") > -1))) {
				Content dependentcontent = new Content(text);
				dependentcontent.read(db, myconfig, id, "", "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
				if (dependentcontent.getContentClass().equals("page")) {
					pages += "<p>" + dependentcontent.getTitle() + " [" + id + "]" + "</p>" + "\r\n";
				}
			}
		}
		return pages;
	}



	public String listContentDependenciesProducts(HashMap dependents, Session mysession, Configuration myconfig, DB db) {
		String products = "";
		Iterator ids = dependents.keySet().iterator();
		while (ids.hasNext()) {
			String id = "" + ids.next();
			String dependencies = "" + dependents.get(id);
			if ((! id.equals("0")) && (id.matches("^[0-9]+$")) && ((dependencies.indexOf("|content|") > -1) || (dependencies.indexOf("|summary|") > -1) || (dependencies.indexOf("|template|") > -1) || (dependencies.indexOf("|stylesheet|") > -1) || (dependencies.indexOf("|script|") > -1) || (dependencies.indexOf("|version_master|") > -1) || (dependencies.indexOf("|page_up|") > -1) || (dependencies.indexOf("|page_top|") > -1) || (dependencies.indexOf("|page_next|") > -1) || (dependencies.indexOf("|page_previous|") > -1) || (dependencies.indexOf("|page_last|") > -1) || (dependencies.indexOf("|page_first|") > -1) || (dependencies.indexOf("|related|") > -1) || (dependencies.indexOf("|image1|") > -1) || (dependencies.indexOf("|image2|") > -1) || (dependencies.indexOf("|image3|") > -1) || (dependencies.indexOf("|file1|") > -1) || (dependencies.indexOf("|file2|") > -1) || (dependencies.indexOf("|file3|") > -1) || (dependencies.indexOf("|link1|") > -1) || (dependencies.indexOf("|link2|") > -1) || (dependencies.indexOf("|link3|") > -1))) {
				Content dependentcontent = new Content(text);
				dependentcontent.read(db, myconfig, id, "", "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
				if (dependentcontent.getContentClass().equals("product")) {
					products += "<p>" + dependentcontent.getTitle() + " [" + id + "]" + "</p>" + "\r\n";
				}
			}
		}
		return products;
	}



	public String listContentDependenciesImages(HashMap dependents, Session mysession, Configuration myconfig, DB db) {
		String images = "";
		Iterator ids = dependents.keySet().iterator();
		while (ids.hasNext()) {
			String id = "" + ids.next();
			String dependencies = "" + dependents.get(id);
			if ((! id.equals("0")) && (id.matches("^[0-9]+$")) && ((dependencies.indexOf("|image|") > -1) || (dependencies.indexOf("|content|") > -1) || (dependencies.indexOf("|summary|") > -1) || (dependencies.indexOf("|related|") > -1))) {
				Content dependentcontent = new Content(text);
				dependentcontent.read(db, myconfig, id, "", "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
				if (dependentcontent.getContentClass().equals("image")) {
					images += "<p>" + dependentcontent.getTitle() + " [" + id + "]" + "</p>" + "\r\n";
				}
			}
		}
		return images;
	}



	public String listContentDependenciesFiles(HashMap dependents, Session mysession, Configuration myconfig, DB db) {
		String files = "";
		Iterator ids = dependents.keySet().iterator();
		while (ids.hasNext()) {
			String id = "" + ids.next();
			String dependencies = "" + dependents.get(id);
			if ((! id.equals("0")) && (id.matches("^[0-9]+$")) && ((dependencies.indexOf("|file|") > -1) || (dependencies.indexOf("|content|") > -1) || (dependencies.indexOf("|summary|") > -1) || (dependencies.indexOf("|related|") > -1))) {
				Content dependentcontent = new Content(text);
				dependentcontent.read(db, myconfig, id, "", "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
				if (dependentcontent.getContentClass().equals("file")) {
					files += "<p>" + dependentcontent.getTitle() + " [" + id + "]" + "</p>" + "\r\n";
				}
			}
		}
		return files;
	}



	public String listContentDependenciesLinks(HashMap dependents, Session mysession, Configuration myconfig, DB db) {
		String links = "";
		Iterator ids = dependents.keySet().iterator();
		while (ids.hasNext()) {
			String id = "" + ids.next();
			String dependencies = "" + dependents.get(id);
			if ((! id.equals("0")) && (id.matches("^[0-9]+$")) && ((dependencies.indexOf("|link|") > -1) || (dependencies.indexOf("|content|") > -1) || (dependencies.indexOf("|summary|") > -1) || (dependencies.indexOf("|related|") > -1))) {
				Content dependentcontent = new Content(text);
				dependentcontent.read(db, myconfig, id, "", "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
				if (dependentcontent.getContentClass().equals("link")) {
					links += "<p>" + dependentcontent.getTitle() + " [" + id + "]" + "</p>" + "\r\n";
				}
			}
		}
		return links;
	}



	public String listContentDependenciesElements(HashMap dependents, Session mysession, Configuration myconfig, DB db) {
		String elements = "";
		Iterator ids = dependents.keySet().iterator();
		while (ids.hasNext()) {
			String id = "" + ids.next();
			String dependencies = "" + dependents.get(id);
			if ((! id.equals("0")) && (id.matches("^[0-9]+$")) && (dependencies.indexOf("|element_") > -1)) {
				Content dependentcontent = new Content(text);
				dependentcontent.read(db, myconfig, id, "", "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
				elements += "<p>" + dependentcontent.getTitle() + " [" + id + "]" + "</p>" + "\r\n";
			} else if ((! id.equals("0")) && (id.matches("^[0-9]+$")) && ((dependencies.indexOf("|content|") > -1) || (dependencies.indexOf("|summary|") > -1))) {
				Content dependentcontent = new Content(text);
				dependentcontent.read(db, myconfig, id, "", "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
				if ((! dependentcontent.getContentClass().equals("page")) && (! dependentcontent.getContentClass().equals("product")) && (! dependentcontent.getContentClass().equals("image")) && (! dependentcontent.getContentClass().equals("file")) && (! dependentcontent.getContentClass().equals("link")) && (! dependentcontent.getContentClass().equals("template")) && (! dependentcontent.getContentClass().equals("stylesheet")) && (! dependentcontent.getContentClass().equals("script"))) {
					elements += "<p>" + dependentcontent.getTitle() + " [" + id + "]" + "</p>" + "\r\n";
				}
			}
		}
		return elements;
	}



	public String listContentDependenciesTemplates(HashMap dependents, Session mysession, Configuration myconfig, DB db) {
		String templates = "";
		Iterator ids = dependents.keySet().iterator();
		while (ids.hasNext()) {
			String id = "" + ids.next();
			String dependencies = "" + dependents.get(id);
			if ((! id.equals("0")) && (id.matches("^[0-9]+$")) && ((dependencies.indexOf("|content|") > -1) || (dependencies.indexOf("|summary|") > -1) || (dependencies.indexOf("|template|") > -1) || (dependencies.indexOf("|script|") > -1))) {
				Content dependentcontent = new Content(text);
				dependentcontent.read(db, myconfig, id, "", "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
				if (dependentcontent.getContentClass().equals("template")) {
					templates += "<p>" + dependentcontent.getTitle() + " [" + id + "]" + "</p>" + "\r\n";
				}
			}
		}
		return templates;
	}



	public String listContentDependenciesStylesheets(HashMap dependents, Session mysession, Configuration myconfig, DB db) {
		String stylesheets = "";
		Iterator ids = dependents.keySet().iterator();
		while (ids.hasNext()) {
			String id = "" + ids.next();
			String dependencies = "" + dependents.get(id);
			if ((! id.equals("0")) && (id.matches("^[0-9]+$")) && ((dependencies.indexOf("|content|") > -1) || (dependencies.indexOf("|summary|") > -1) || (dependencies.indexOf("|stylesheet|") > -1))) {
				Content dependentcontent = new Content(text);
				dependentcontent.read(db, myconfig, id, "", "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
				if (dependentcontent.getContentClass().equals("stylesheet")) {
					stylesheets += "<p>" + dependentcontent.getTitle() + " [" + id + "]" + "</p>" + "\r\n";
				}
			}
		}
		return stylesheets;
	}



	public String listContentDependenciesScripts(HashMap dependents, Session mysession, Configuration myconfig, DB db) {
		String scripts = "";
		Iterator ids = dependents.keySet().iterator();
		while (ids.hasNext()) {
			String id = "" + ids.next();
			String dependencies = "" + dependents.get(id);
			if ((! id.equals("0")) && (id.matches("^[0-9]+$")) && ((dependencies.indexOf("|content|") > -1) || (dependencies.indexOf("|summary|") > -1) || (dependencies.indexOf("|script|") > -1))) {
				Content dependentcontent = new Content(text);
				dependentcontent.read(db, myconfig, id, "", "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
				if (dependentcontent.getContentClass().equals("script")) {
					scripts += "<p>" + dependentcontent.getTitle() + " [" + id + "]" + "</p>" + "\r\n";
				}
			}
		}
		return scripts;
	}



	public String listContentDependenciesContentgroups(HashMap dependents, Session mysession, Configuration myconfig, DB db) {
		String contentgroups = "";
		Iterator ids = dependents.keySet().iterator();
		while (ids.hasNext()) {
			String id = "" + ids.next();
			String dependencies = "" + dependents.get(id);
			if ((id.equals("0")) && (dependencies.indexOf("|contentgroup_") > -1)) {
				Pattern p = Pattern.compile("\\|contentgroup_([^|]+)\\|");
				Matcher m = p.matcher(dependencies);
				while (m.find()) {
					String contentgroup = m.group(1);
					contentgroups += "<p>" + contentgroup + "</p>" + "\r\n";
				}
			}
		}
		return contentgroups;
	}



	public String listContentDependenciesContenttypes(HashMap dependents, Session mysession, Configuration myconfig, DB db) {
		String contenttypes = "";
		Iterator ids = dependents.keySet().iterator();
		while (ids.hasNext()) {
			String id = "" + ids.next();
			String dependencies = "" + dependents.get(id);
			if ((id.equals("0")) && (dependencies.indexOf("|contenttype_") > -1)) {
				Pattern p = Pattern.compile("\\|contenttype_([^|]+)\\|");
				Matcher m = p.matcher(dependencies);
				while (m.find()) {
					String contenttype = m.group(1);
					contenttypes += "<p>" + contenttype + "</p>" + "\r\n";
				}
			}
		}
		return contenttypes;
	}



	public String listContentDependenciesProductgroups(HashMap dependents, Session mysession, Configuration myconfig, DB db) {
		String productgroups = "";
		Iterator ids = dependents.keySet().iterator();
		while (ids.hasNext()) {
			String id = "" + ids.next();
			String dependencies = "" + dependents.get(id);
			if ((id.equals("0")) && (dependencies.indexOf("|productgroup_") > -1)) {
				Pattern p = Pattern.compile("\\|productgroup_([^|]+)\\|");
				Matcher m = p.matcher(dependencies);
				while (m.find()) {
					String productgroup = m.group(1);
					productgroups += "<p>" + productgroup + "</p>" + "\r\n";
				}
			}
		}
		return productgroups;
	}



	public String listContentDependenciesProducttypes(HashMap dependents, Session mysession, Configuration myconfig, DB db) {
		String producttypes = "";
		Iterator ids = dependents.keySet().iterator();
		while (ids.hasNext()) {
			String id = "" + ids.next();
			String dependencies = "" + dependents.get(id);
			if ((id.equals("0")) && (dependencies.indexOf("|producttype_") > -1)) {
				Pattern p = Pattern.compile("\\|producttype_([^|]+)\\|");
				Matcher m = p.matcher(dependencies);
				while (m.find()) {
					String producttype = m.group(1);
					producttypes += "<p>" + producttype + "</p>" + "\r\n";
				}
			}
		}
		return producttypes;
	}



	public String listContentPackageContent(DB db, Configuration config, String contentpackage, String contentclass) {
		return listContentPackageContent(db, config, contentpackage, contentclass, "", false);
	}
	public String listContentPackageContent(DB db, Configuration config, String contentpackage, String contentclass, String input, boolean selected) {
		String output = "";
		String inputselected = "";
		if ((input.equals("checkbox")) && selected) {
			inputselected = " checked";
		} else {
			inputselected = "";
		}
		Content content = new Content();
		ArrayList mycontentpackage = content.listContentpackage(db, contentpackage, contentclass);
		for (int i=0; i<mycontentpackage.size(); i++) {
			String id = "" + mycontentpackage.get(i);
			content.read(db, config, id, "content", "id");
			output += "<p>";
			if (input.equals("checkbox")) {
				output += "<input type=\"checkbox\" name=\"id\" value=\"" + id + "\"" + inputselected + "> ";
			}
			output += content.getTitle() + " [" + id + "]";
			output += "</p>" + "\r\n";
		}
		return output;
	}



	public String listContentBundleContent(DB db, Configuration config, String contentbundle, String contentclass) {
		return listContentBundleContent(db, config, contentbundle, contentclass, "", false);
	}
	public String listContentBundleContent(DB db, Configuration config, String contentbundle, String contentclass, String input, boolean selected) {
		String output = "";
		String inputselected = "";
		if ((input.equals("checkbox")) && selected) {
			inputselected = " checked";
		} else {
			inputselected = "";
		}
		Content content = new Content();
		ArrayList mycontentbundle = content.listContentbundle(db, contentbundle, contentclass);
		for (int i=0; i<mycontentbundle.size(); i++) {
			String id = "" + mycontentbundle.get(i);
			content.read(db, config, id, "content", "id");
			output += "<p>";
			if (input.equals("checkbox")) {
				output += "<input type=\"checkbox\" name=\"id\" value=\"" + id + "\"" + inputselected + "> ";
			}
			output += content.getTitle() + " [" + id + "]";
			output += "</p>" + "\r\n";
		}
		return output;
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



	public void exportStaticFilesAll(JspWriter out, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		error = "";
		if (db == null) return;
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		out.println("<p><b>Exporting content items as static files to " + myconfig.get(db, "export_rootpath") + ":</b></p><hr>");
		out.flush();
		Page mypage = new Page();
		Content mycontent = new Content(text);
		String SQL = "select id from content_public order by id";
		mycontent.records("", "", "", "", "", "", "", db, myconfig, SQL);
		while (mycontent.records("", "", "", "", "", "", "", db, myconfig, "")) {
			String myid = mycontent.getId();
			mypage = getPublishedPageById(myid, server, mysession, myrequest, myresponse, myconfig, db);
			String exportfilename = mypage.exportStaticFile(server, myrequest.getRequest(), myresponse.getResponse(), mysession.getSession(), null, myconfig, db);
			if (! exportfilename.equals("")) {
				out.println("" + mypage.getId() + " : " + mypage.getContentClass() + " : " + mypage.getTitle() + " : " + exportfilename + "<br>\r\n");
				out.flush();
			}
		}
		out.println("<p><hr><b>Done</b></p>");
		out.flush();
	}



	public Content getMetaData(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		error = "";
		Content content = new Content(text);
		if (db == null) return content;
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Content(text);
		return content;
	}



	public Content doMetaData(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		error = "";
		Content content = new Content(text);
		if (db == null) return content;
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return content;
		String contentclasses = Common.join(",", myrequest.getParameters("contentclass"));
		if (contentclasses.equals("")) contentclasses = "page,product";
		String SQLwhere = Common.SQLwhere_in("-", "contentclass", contentclasses);
		String SQL = "select id, locked, locked_administrator, locked_creator, locked_developer, locked_editor, locked_publisher, locked_schedule, locked_unschedule, locked_user, created, created_by, updated, updated_by, published, published_by, unpublished, unpublished_by, scheduled_publish, scheduled_unpublish, requested_publish, requested_unpublish, status, status_by, title, contentpackage, contentclass, contentbundle, contenttype, contentgroup, version, checkedout, users_users, users_type, users_group, creators_users, creators_type, creators_group, editors_users, editors_type, editors_group, developers_users, developers_type, developers_group, publishers_users, publishers_type, publishers_group, administrators_users, administrators_type, administrators_group, author, description, keywords, metainfo from content where " + SQLwhere + " order by title, version, id";
		content.records("", "", "", "", "", "", "", db, myconfig, SQL);
		if (myrequest.getParameter("format").equals("csv")) {
			myresponse.setHeader("Content-Disposition", "filename=metadata.csv");
			myresponse.setContentType("x-application/csv");
		}
		return content;
	}



	public String handleFileUpload(Page mypage, String old_server_filename, String server_filename, String published_server_filename, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Fileupload filepost) throws Exception {
		if (! filepost.getParameter("file.filename").equals("")) {
			// new file uploaded
			if ((mypage.getContentClass().equals("image")) || (mypage.getContentClass().equals("file"))) {
				if ((filepost.getParameter("server_filename").equals("")) && (! mypage.getContentClass().equals("")) && (! filepost.getParameter("file.filename").equals(""))) {
					filepost.setParameter("server_filename", mypage.getContentClass() + "/" + filepost.getParameter("file.filename"));
				}
				if (old_server_filename.equals("")) {
					// new file - no old file
					// use entered server filename
					String new_server_filename = Common.findUniqueFilename(Common.getRealPath(server, myconfig.get(db, "URLrootpath")), filepost.getParameter("server_filename"), mypage.getContentClass(), filepost.getParameter("file.filename"), text, server, db, myconfig, mysession, myrequest, myresponse);
					String new_server_filename_extension = new_server_filename.replaceAll("^.*\\.([^/\\\\.]+)$", "$1");
					if (! mypage.validFilenameExtension(db, new_server_filename_extension)) {
						error += "<br>" + text.display("error.content.upload.filename.extension") + old_server_filename + " - " + filepost.getParameter("server_filename");
						mypage.setServerFilename("");
					} else {
						mypage.setServerFilename(new_server_filename);
					}
				} else if (old_server_filename.equals(published_server_filename)) {
					// new file - keep old published file
					// use entered server filename
					String new_server_filename = Common.findUniqueFilename(Common.getRealPath(server, myconfig.get(db, "URLrootpath")), filepost.getParameter("server_filename"), mypage.getContentClass(), filepost.getParameter("file.filename"), text, server, db, myconfig, mysession, myrequest, myresponse);
					String new_server_filename_extension = new_server_filename.replaceAll("^.*\\.([^/\\\\.]+)$", "$1");
					if (! mypage.validFilenameExtension(db, new_server_filename_extension)) {
						error += "<br>" + text.display("error.content.upload.filename.extension") + old_server_filename + " - " + filepost.getParameter("server_filename");
						mypage.setServerFilename("");
					} else {
						mypage.setServerFilename(new_server_filename);
					}
				} else {
					// replace old unpublished file
					Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename));
					Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/delete"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + old_server_filename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:handleFileUpload1\"", "", server, mysession, myrequest, myresponse);
					// use entered server filename
					String new_server_filename = Common.findUniqueFilename(Common.getRealPath(server, myconfig.get(db, "URLrootpath")), filepost.getParameter("server_filename"), mypage.getContentClass(), filepost.getParameter("file.filename"), text, server, db, myconfig, mysession, myrequest, myresponse);
					String new_server_filename_extension = new_server_filename.replaceAll("^.*\\.([^/\\\\.]+)$", "$1");
					if (! mypage.validFilenameExtension(db, new_server_filename_extension)) {
						error += "<br>" + text.display("error.content.upload.filename.extension") + old_server_filename + " - " + filepost.getParameter("server_filename");
						mypage.setServerFilename("");
					} else {
						mypage.setServerFilename(new_server_filename);
					}
				}
				if (mypage.getServerFilename().equals("")) {
					// no server filename entered - use upload filename
					setServerFilenameForContentClass(mypage, filepost, myconfig, db);
				}
			} 
			String upload_filename = filepost.getParameter("file.server_filename");
			server_filename = mypage.getServerFilename();
			if (! validateFilenameExtension(upload_filename, server_filename, old_server_filename, server, mysession, myrequest, myresponse, mypage, filepost, myconfig, db)) {
				filepost.setParameter("file.filename", "");
				error += "<br>" + text.display("error.content.upload.format") + " " + server_filename;
			}
			server_filename = mypage.getServerFilename();
		} else {
			// no file uploaded
			if ((mypage.getContentClass().equals("image")) || (mypage.getContentClass().equals("file"))) {
				if (old_server_filename.equals("")) {
					// no old filename - ignore new filename without uploaded file
					mypage.setServerFilename("");
				} else if (filepost.getParameter("server_filename").equals(old_server_filename)) {
					// no new filename - keep old filename
				} else if ((filepost.parameterExists("server_filename")) && (filepost.getParameter("server_filename").equals(""))) {
					// blank new filename - delete old unpublished file
					mypage.setServerFilename("");
					if (! old_server_filename.equals(published_server_filename)) {
						Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename));
						Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/delete"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + old_server_filename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:handleFileUpload2\"", "", server, mysession, myrequest, myresponse);
					}
				} else if ((old_server_filename.equals(published_server_filename)) && (server_filename.equals("")) && (! filepost.parameterExists("server_filename"))) {
					// no new filename - keep old filename - search & replace
				} else if (old_server_filename.equals(published_server_filename)) {
					// copy old published file
					String new_server_filename = Common.findUniqueFilename(Common.getRealPath(server, myconfig.get(db, "URLrootpath")), filepost.getParameter("server_filename"), mypage.getContentClass(), old_server_filename, text, server, db, myconfig, mysession, myrequest, myresponse);
					String new_server_filename_extension = new_server_filename.replaceAll("^.*\\.([^/\\\\.]+)$", "$1");
					if (! mypage.validFilenameExtension(db, new_server_filename_extension)) {
						error += "<br>" + text.display("error.content.upload.filename.extension") + old_server_filename + " - " + filepost.getParameter("server_filename");
					} else {
						Common.createFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename));
						Common.copyFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename), Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename));
						Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/copy"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + old_server_filename + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + new_server_filename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:handleFileUpload3\"", "", server, mysession, myrequest, myresponse);
						mypage.setServerFilename(new_server_filename);
					}
				} else if (! filepost.parameterExists("server_filename")) {
					// no new filename - keep old filename
				} else {							
					// rename old unpublished file
					String new_server_filename = Common.findUniqueFilename(Common.getRealPath(server, myconfig.get(db, "URLrootpath")), filepost.getParameter("server_filename"), mypage.getContentClass(), old_server_filename, text, server, db, myconfig, mysession, myrequest, myresponse);
					String new_server_filename_extension = new_server_filename.replaceAll("^.*\\.([^/\\\\.]+)$", "$1");
					if (! mypage.validFilenameExtension(db, new_server_filename_extension)) {
						error += "<br>" + text.display("error.content.upload.filename.extension") + old_server_filename + " - " + filepost.getParameter("server_filename");
					} else {
						Common.createFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename));
						Common.moveFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename), Common.getRealPath(server, myconfig.get(db, "URLrootpath") + new_server_filename));
						Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/move"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + old_server_filename + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + new_server_filename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:handleFileUpload6\"", "", server, mysession, myrequest, myresponse);
						mypage.setServerFilename(new_server_filename);
					}
				}
				server_filename = mypage.getServerFilename();
			}
		}
		if (! published_server_filename.equals("")) {
			// update existing file
			if (filepost.getParameter("server_filename").equals(published_server_filename)) {
				// publish to old filename
				if ((! filepost.getParameter("server_filename").equals(server_filename)) && (! filepost.getParameter("server_filename").equals(server_filename.replaceAll("^(.*)(_[0-9]+)(\\.[^\\.]+)$", "$1$3"))) && (! filepost.getParameter("server_filename").equals("")) && (! server_filename.equals(""))) {
					error += "<br>" + text.display("error.content.upload.filename.used") + filepost.getParameter("server_filename") + " - " + server_filename;
				}
			} else {
				// publish to new filename
				if ((! filepost.getParameter("server_filename").equals(server_filename)) && (! filepost.getParameter("server_filename").equals("")) && (! server_filename.equals(""))) {
					error += "<br>" + text.display("error.content.upload.filename.used") + filepost.getParameter("server_filename") + " - " + server_filename;
				}
			}
		} else if (published_server_filename.equals("")) {
			// add new file
			if ((! filepost.getParameter("server_filename").equals(server_filename)) && (! filepost.getParameter("server_filename").equals("")) && (! server_filename.equals(""))) {
				error += "<br>" + text.display("error.content.upload.filename.used") + filepost.getParameter("server_filename") + " - " + server_filename;
			}
		}
		return server_filename;
	}



	public Content getCreateBlank(Content mypage, Session mysession, Request myrequest, Configuration myconfig, DB db) {
		return getCreateBlank(mypage, mysession, myrequest, null, myconfig, db);
	}
	public Content getCreateBlank(Content mypage, Session mysession, Request myrequest, Fileupload filepost, Configuration myconfig, DB db) {
		if (mypage == null) {
			mypage = new Content();
		}
		if (! mypage.getContentClass().equals("")) {
			// ignore
//		} else if (mysession.get("admin_contentclass").equals("element")) {
//			// ignore
		} else if ((! mysession.get("admin_contentclass").equals("")) && (! mysession.get("admin_contentclass").equals(" ")) && (! mysession.get("admin_contentclass").equals("element"))) {
			mypage.setContentClass(mysession.get("admin_contentclass"));
		} else if ((! myrequest.getParameter("contentclass").equals("")) && (! myrequest.getParameter("contentclass").equals(" "))) {
			mypage.setContentClass(myrequest.getParameter("contentclass"));
		} else if ((filepost != null) && (! filepost.getParameter("contentclass").equals("")) && (! filepost.getParameter("contentclass").equals(" "))) {
			mypage.setContentClass(filepost.getParameter("contentclass"));
		}
		if (! mypage.getContentType().equals("")) {
			// ignore
		} else if (mysession.get("admin_contenttype").equals("-")) {
			mypage.setContentType("");
		} else if ((! mysession.get("admin_contenttype").equals("")) && (! mysession.get("admin_contenttype").equals(" "))) {
			mypage.setContentType(mysession.get("admin_contenttype"));
		} else if ((! myrequest.getParameter("contenttype").equals("")) && (! myrequest.getParameter("contenttype").equals(" "))) {
			mypage.setContentType(myrequest.getParameter("contenttype"));
		} else if ((filepost != null) && (! filepost.getParameter("contenttype").equals("")) && (! filepost.getParameter("contenttype").equals(" "))) {
			mypage.setContentType(filepost.getParameter("contenttype"));
		}
		if (! mypage.getContentGroup().equals("")) {
			// ignore
		} else if (mysession.get("admin_contentgroup").equals("-")) {
			mypage.setContentGroup("");
		} else if ((! mysession.get("admin_contentgroup").equals("")) && (! mysession.get("admin_contentgroup").equals(" "))) {
			mypage.setContentGroup(mysession.get("admin_contentgroup"));
		} else if ((! myrequest.getParameter("contentgroup").equals("")) && (! myrequest.getParameter("contentgroup").equals(" "))) {
			mypage.setContentGroup(myrequest.getParameter("contentgroup"));
		} else if ((filepost != null) && (! filepost.getParameter("contentgroup").equals("")) && (! filepost.getParameter("contentgroup").equals(" "))) {
			mypage.setContentGroup(filepost.getParameter("contentgroup"));
		}
		if (! mypage.getContentBundle().equals("")) {
			// ignore
		} else if (mysession.get("admin_contentbundle").equals("-")) {
			mypage.setContentBundle("");
		} else if ((! mysession.get("admin_contentbundle").equals("")) && (! mysession.get("admin_contentbundle").equals(" "))) {
			mypage.setContentBundle(mysession.get("admin_contentbundle"));
		} else if ((! myrequest.getParameter("contentbundle").equals("")) && (! myrequest.getParameter("contentbundle").equals(" "))) {
			mypage.setContentBundle(myrequest.getParameter("contentbundle"));
		} else if ((filepost != null) && (! filepost.getParameter("contentbundle").equals("")) && (! filepost.getParameter("contentbundle").equals(" "))) {
			mypage.setContentBundle(filepost.getParameter("contentbundle"));
		}
		if (! mypage.getVersion().equals("")) {
			// ignore
		} else if (mysession.get("admin_version").equals("-")) {
			mypage.setVersion("");
		} else if ((! mysession.get("admin_version").equals("")) && (! mysession.get("admin_version").equals(" "))) {
			mypage.setVersion(mysession.get("admin_version"));
		} else if ((! myrequest.getParameter("version").equals("")) && (! myrequest.getParameter("version").equals(" "))) {
			mypage.setVersion(myrequest.getParameter("version"));
		} else if ((filepost != null) && (! filepost.getParameter("version").equals("")) && (! filepost.getParameter("version").equals(" "))) {
			mypage.setVersion(filepost.getParameter("version"));
		}

		if (! mypage.getContentGroup().equals("")) {
			if (mypage.getContentClass().equals("product")) {
				Productgroup mygroup = new Productgroup();
				mygroup.readProductgroup(db, mypage.getContentGroup());
				mypage.setContentgroupTemplate(mygroup.getTemplate());
				mypage.setContentgroupStylesheet(mygroup.getStyleSheet());
				mypage.setContentgroupDocType(mygroup.getDocType());
			} else if (mypage.getContentClass().equals("image")) {
				Imagegroup mygroup = new Imagegroup();
				mygroup.readImagegroup(db, mypage.getContentGroup());
				mypage.setContentgroupTemplate(mygroup.getTemplate());
				mypage.setContentgroupStylesheet(mygroup.getStyleSheet());
				mypage.setContentgroupDocType("");
			} else if (mypage.getContentClass().equals("file")) {
				Filegroup mygroup = new Filegroup();
				mygroup.readFilegroup(db, mypage.getContentGroup());
				mypage.setContentgroupTemplate(mygroup.getTemplate());
				mypage.setContentgroupStylesheet(mygroup.getStyleSheet());
				mypage.setContentgroupDocType("");
			} else if (mypage.getContentClass().equals("link")) {
				Linkgroup mygroup = new Linkgroup();
				mygroup.readLinkgroup(db, mypage.getContentGroup());
				mypage.setContentgroupTemplate(mygroup.getTemplate());
				mypage.setContentgroupStylesheet(mygroup.getStyleSheet());
				mypage.setContentgroupDocType("");
			} else {
				Contentgroup mygroup = new Contentgroup();
				mygroup.readContentgroup(db, mypage.getContentGroup());
				mypage.setContentgroupTemplate(mygroup.getTemplate());
				mypage.setContentgroupStylesheet(mygroup.getStyleSheet());
				mypage.setContentgroupDocType(mygroup.getDocType());
			}
		}
		if (! mypage.getContentType().equals("")) {
			if (mypage.getContentClass().equals("product")) {
				Producttype mytype = new Producttype();
				mytype.readProducttype(db, mypage.getContentType());
				mypage.setContenttypeTemplate(mytype.getTemplate());
				mypage.setContenttypeStylesheet(mytype.getStyleSheet());
				mypage.setContenttypeDocType(mytype.getDocType());
			} else if (mypage.getContentClass().equals("image")) {
				Imagetype mytype = new Imagetype();
				mytype.readImagetype(db, mypage.getContentType());
				mypage.setContenttypeTemplate(mytype.getTemplate());
				mypage.setContenttypeStylesheet(mytype.getStyleSheet());
				mypage.setContenttypeDocType("");
			} else if (mypage.getContentClass().equals("file")) {
				Filetype mytype = new Filetype();
				mytype.readFiletype(db, mypage.getContentType());
				mypage.setContenttypeTemplate(mytype.getTemplate());
				mypage.setContenttypeStylesheet(mytype.getStyleSheet());
				mypage.setContenttypeDocType("");
			} else if (mypage.getContentClass().equals("link")) {
				Linktype mytype = new Linktype();
				mytype.readLinktype(db, mypage.getContentType());
				mypage.setContenttypeTemplate(mytype.getTemplate());
				mypage.setContenttypeStylesheet(mytype.getStyleSheet());
				mypage.setContenttypeDocType("");
			} else {
				Contenttype mytype = new Contenttype();
				mytype.readContenttype(db, mypage.getContentType());
				mypage.setContenttypeTemplate(mytype.getTemplate());
				mypage.setContenttypeStylesheet(mytype.getStyleSheet());
				mypage.setContenttypeDocType(mytype.getDocType());
			}
		}

		if (! mysession.get("username").equals(myconfig.get(db, "superadmin"))) {
			mypage.setUser(false);
			mypage.setEditor(false);
			mypage.setCreator(false);
			mypage.setPublisher(false);
			mypage.setDeveloper(false);
			mypage.setAdministrator(false);
		}
		if ((myconfig.get(db, "use_create_blank").equals("yes")) && ((! mypage.getContentClass().equals("")) && ((! mypage.getContentGroup().equals("")) || (! mypage.getContentType().equals(""))))) {
			mypage.setUser(true);
			mypage.setEditor(true);
			mypage.setDeveloper(true);
			mypage.setCreator(true);
			mypage.setPublisher(true);
			mypage.setAdministrator(true);
			if (! mypage.getContentGroup().equals("")) {
				if (mypage.getContentClass().equals("product")) {
					Productgroup mygroup = new Productgroup();
					mygroup.readProductgroup(db, mypage.getContentGroup());
					mygroup.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
					mypage.setUser(mypage.getUser() && mygroup.getUser());
					mypage.setEditor(mypage.getEditor() && mygroup.getEditor());
					mypage.setDeveloper(mypage.getDeveloper() && mygroup.getDeveloper());
					mypage.setCreator(mypage.getCreator() && mygroup.getCreator());
					mypage.setPublisher(mypage.getUser() && mygroup.getPublisher());
					mypage.setAdministrator(mypage.getUser() && mygroup.getAdministrator());
				} else if (mypage.getContentClass().equals("image")) {
					Imagegroup mygroup = new Imagegroup();
					mygroup.readImagegroup(db, mypage.getContentGroup());
					mygroup.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
					mypage.setUser(mypage.getUser() && mygroup.getUser());
					mypage.setEditor(mypage.getEditor() && mygroup.getEditor());
					mypage.setDeveloper(mypage.getDeveloper() && mygroup.getDeveloper());
					mypage.setCreator(mypage.getCreator() && mygroup.getCreator());
					mypage.setPublisher(mypage.getUser() && mygroup.getPublisher());
					mypage.setAdministrator(mypage.getUser() && mygroup.getAdministrator());
				} else if (mypage.getContentClass().equals("file")) {
					Filegroup mygroup = new Filegroup();
					mygroup.readFilegroup(db, mypage.getContentGroup());
					mygroup.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
					mypage.setUser(mypage.getUser() && mygroup.getUser());
					mypage.setEditor(mypage.getEditor() && mygroup.getEditor());
					mypage.setDeveloper(mypage.getDeveloper() && mygroup.getDeveloper());
					mypage.setCreator(mypage.getCreator() && mygroup.getCreator());
					mypage.setPublisher(mypage.getUser() && mygroup.getPublisher());
					mypage.setAdministrator(mypage.getUser() && mygroup.getAdministrator());
				} else if (mypage.getContentClass().equals("link")) {
					Linkgroup mygroup = new Linkgroup();
					mygroup.readLinkgroup(db, mypage.getContentGroup());
					mygroup.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
					mypage.setUser(mypage.getUser() && mygroup.getUser());
					mypage.setEditor(mypage.getEditor() && mygroup.getEditor());
					mypage.setDeveloper(mypage.getDeveloper() && mygroup.getDeveloper());
					mypage.setCreator(mypage.getCreator() && mygroup.getCreator());
					mypage.setPublisher(mypage.getUser() && mygroup.getPublisher());
					mypage.setAdministrator(mypage.getUser() && mygroup.getAdministrator());
				} else {
					Contentgroup mygroup = new Contentgroup();
					mygroup.readContentgroup(db, mypage.getContentGroup());
					mygroup.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
					mypage.setUser(mypage.getUser() && mygroup.getUser());
					mypage.setEditor(mypage.getEditor() && mygroup.getEditor());
					mypage.setDeveloper(mypage.getDeveloper() && mygroup.getDeveloper());
					mypage.setCreator(mypage.getCreator() && mygroup.getCreator());
					mypage.setPublisher(mypage.getUser() && mygroup.getPublisher());
					mypage.setAdministrator(mypage.getUser() && mygroup.getAdministrator());
				}
			}
			if (! mypage.getContentType().equals("")) {
				if (mypage.getContentClass().equals("product")) {
					Producttype mytype = new Producttype();
					mytype.readProducttype(db, mypage.getContentType());
					mytype.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
					mypage.setUser(mypage.getUser() && mytype.getUser());
					mypage.setEditor(mypage.getEditor() && mytype.getEditor());
					mypage.setDeveloper(mypage.getDeveloper() && mytype.getDeveloper());
					mypage.setCreator(mypage.getCreator() && mytype.getCreator());
					mypage.setPublisher(mypage.getUser() && mytype.getPublisher());
					mypage.setAdministrator(mypage.getUser() && mytype.getAdministrator());
				} else if (mypage.getContentClass().equals("image")) {
					Imagetype mytype = new Imagetype();
					mytype.readImagetype(db, mypage.getContentType());
					mytype.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
					mypage.setUser(mypage.getUser() && mytype.getUser());
					mypage.setEditor(mypage.getEditor() && mytype.getEditor());
					mypage.setDeveloper(mypage.getDeveloper() && mytype.getDeveloper());
					mypage.setCreator(mypage.getCreator() && mytype.getCreator());
					mypage.setPublisher(mypage.getUser() && mytype.getPublisher());
					mypage.setAdministrator(mypage.getUser() && mytype.getAdministrator());
				} else if (mypage.getContentClass().equals("file")) {
					Filetype mytype = new Filetype();
					mytype.readFiletype(db, mypage.getContentType());
					mytype.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
					mypage.setUser(mypage.getUser() && mytype.getUser());
					mypage.setEditor(mypage.getEditor() && mytype.getEditor());
					mypage.setDeveloper(mypage.getDeveloper() && mytype.getDeveloper());
					mypage.setCreator(mypage.getCreator() && mytype.getCreator());
					mypage.setPublisher(mypage.getUser() && mytype.getPublisher());
					mypage.setAdministrator(mypage.getUser() && mytype.getAdministrator());
				} else if (mypage.getContentClass().equals("link")) {
					Linktype mytype = new Linktype();
					mytype.readLinktype(db, mypage.getContentType());
					mytype.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
					mypage.setUser(mypage.getUser() && mytype.getUser());
					mypage.setEditor(mypage.getEditor() && mytype.getEditor());
					mypage.setDeveloper(mypage.getDeveloper() && mytype.getDeveloper());
					mypage.setCreator(mypage.getCreator() && mytype.getCreator());
					mypage.setPublisher(mypage.getUser() && mytype.getPublisher());
					mypage.setAdministrator(mypage.getUser() && mytype.getAdministrator());
				} else {
					Contenttype mytype = new Contenttype();
					mytype.readContenttype(db, mypage.getContentType());
					mytype.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
					mypage.setUser(mypage.getUser() && mytype.getUser());
					mypage.setEditor(mypage.getEditor() && mytype.getEditor());
					mypage.setDeveloper(mypage.getDeveloper() && mytype.getDeveloper());
					mypage.setCreator(mypage.getCreator() && mytype.getCreator());
					mypage.setPublisher(mypage.getUser() && mytype.getPublisher());
					mypage.setAdministrator(mypage.getUser() && mytype.getAdministrator());
				}
			}
			if (mypage.getCreator()) {
				mypage.setCreatedBy(mysession.get("username"));
			}
		}
		return mypage;
	}



	public void handleCreateBlank(Content mypage, Session mysession, Request myrequest, Configuration myconfig, DB db) {
		handleCreateBlank(mypage, mysession, myrequest, null, myconfig, db);
	}
	public void handleCreateBlank(Content mypage, Session mysession, Request myrequest, Fileupload filepost, Configuration myconfig, DB db) {
		if ((mypage.getId().equals("")) || (mypage.getId().equals("0"))) {
			mypage = getCreateBlank(mypage, mysession, myrequest, filepost, myconfig, db);
		}
	}



	private String SQLwhere_hide(Session mysession, Configuration myconfig, DB db, String SQLwhere) {
		if ((myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) && (myconfig.get(db, "hide_accessrestricted_menu_items_content").equals("yes"))) {
			String SQLwherehide = "";

			String SQLwherecontentclass = "";
			Element elements = new Element();
			elements.records(db, "select * from elements order by element");
			while (elements.records(db, "")) {
				if (elements.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
					if (! SQLwherecontentclass.equals("")) SQLwherecontentclass += ",";
					SQLwherecontentclass += db.quote(Common.SQL_clean(elements.getElement()));
				}
			}
			if (! SQLwherecontentclass.equals("")) {
				SQLwherecontentclass = " ((contentclass is null) or (contentclass in (''," + db.quote("page") + "," + db.quote("script") + "," + db.quote("stylesheet") + "," + db.quote("template") + "," + db.quote("product") + "," + db.quote("image") + "," + db.quote("file") + "," + db.quote("link") + "," + SQLwherecontentclass + ")))";
				if (! SQLwherehide.equals("")) SQLwherehide += " and";
				SQLwherehide += SQLwherecontentclass;
			}

			String SQLwherecontentgroup = "";
			Contentgroup contentgroups = new Contentgroup();
			contentgroups.records(db, "select * from contentgroups order by contentgroup");
			while (contentgroups.records(db, "")) {
				if (contentgroups.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
					if (! SQLwherecontentgroup.equals("")) SQLwherecontentgroup += ",";
					SQLwherecontentgroup += db.quote(Common.SQL_clean(contentgroups.getContentgroup()));
				}
			}
			if (! SQLwherecontentgroup.equals("")) {
				SQLwherecontentgroup = " ((contentclass in (" + db.quote("image") + "," + db.quote("file") + "," + db.quote("link") + "," + db.quote("product") + ")) or ((contentclass not in (" + db.quote("image") + "," + db.quote("file") + "," + db.quote("link") + "," + db.quote("product") + ")) and ((contentgroup is null) or (contentgroup in (''," + SQLwherecontentgroup + ")))))";
				if (! SQLwherehide.equals("")) SQLwherehide += " and";
				SQLwherehide += SQLwherecontentgroup;
			}

			String SQLwherecontenttype = "";
			Contenttype contenttypes = new Contenttype();
			contenttypes.records(db, "select * from contenttypes order by contenttype");
			while (contenttypes.records(db, "")) {
				if (contenttypes.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
					if (! SQLwherecontenttype.equals("")) SQLwherecontenttype += ",";
					SQLwherecontenttype += db.quote(Common.SQL_clean(contenttypes.getContenttype()));
				}
			}
			if (! SQLwherecontenttype.equals("")) {
				SQLwherecontenttype = " ((contentclass in (" + db.quote("image") + "," + db.quote("file") + "," + db.quote("link") + "," + db.quote("product") + ")) or ((contentclass not in (" + db.quote("image") + "," + db.quote("file") + "," + db.quote("link") + "," + db.quote("product") + ")) and ((contenttype is null) or (contenttype in (''," + SQLwherecontenttype + ")))))";
				if (! SQLwherehide.equals("")) SQLwherehide += " and";
				SQLwherehide += SQLwherecontenttype;
			}

			String SQLwhereimagegroup = "";
			Imagegroup imagegroups = new Imagegroup();
			imagegroups.records(db, "select * from imagegroups order by imagegroup");
			while (imagegroups.records(db, "")) {
				if (imagegroups.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
					if (! SQLwhereimagegroup.equals("")) SQLwhereimagegroup += ",";
					SQLwhereimagegroup += db.quote(Common.SQL_clean(imagegroups.getImagegroup()));
				}
			}
			if (! SQLwhereimagegroup.equals("")) {
				SQLwhereimagegroup = " ((contentclass<>" + db.quote("image") + ") or ((contentgroup is null) or (contentgroup in (''," + SQLwhereimagegroup + "))))";
				if (! SQLwherehide.equals("")) SQLwherehide += " and";
				SQLwherehide += SQLwhereimagegroup;
			}

			String SQLwhereimagetype = "";
			Imagetype imagetypes = new Imagetype();
			imagetypes.records(db, "select * from imagetypes order by imagetype");
			while (imagetypes.records(db, "")) {
				if (imagetypes.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
					if (! SQLwhereimagetype.equals("")) SQLwhereimagetype += ",";
					SQLwhereimagetype += db.quote(Common.SQL_clean(imagetypes.getImagetype()));
				}
			}
			if (! SQLwhereimagetype.equals("")) {
				SQLwhereimagetype = " ((contentclass<>" + db.quote("image") + ") or ((contenttype is null) or (contenttype in (''," + SQLwhereimagetype + "))))";
				if (! SQLwherehide.equals("")) SQLwherehide += " and";
				SQLwherehide += SQLwhereimagetype;
			}

			String SQLwherefilegroup = "";
			Filegroup filegroups = new Filegroup();
			filegroups.records(db, "select * from filegroups order by filegroup");
			while (filegroups.records(db, "")) {
				if (filegroups.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
					if (! SQLwherefilegroup.equals("")) SQLwherefilegroup += ",";
					SQLwherefilegroup += db.quote(Common.SQL_clean(filegroups.getFilegroup()));
				}
			}
			if (! SQLwherefilegroup.equals("")) {
				SQLwherefilegroup = " ((contentclass<>" + db.quote("file") + ") or ((contentgroup is null) or (contentgroup in (''," + SQLwherefilegroup + "))))";
				if (! SQLwherehide.equals("")) SQLwherehide += " and";
				SQLwherehide += SQLwherefilegroup;
			}

			String SQLwherefiletype = "";
			Filetype filetypes = new Filetype();
			filetypes.records(db, "select * from filetypes order by filetype");
			while (filetypes.records(db, "")) {
				if (filetypes.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
					if (! SQLwherefiletype.equals("")) SQLwherefiletype += ",";
					SQLwherefiletype += db.quote(Common.SQL_clean(filetypes.getFiletype()));
				}
			}
			if (! SQLwherefiletype.equals("")) {
				SQLwherefiletype = " ((contentclass<>" + db.quote("file") + ") or ((contenttype is null) or (contenttype in (''," + SQLwherefiletype + "))))";
				if (! SQLwherehide.equals("")) SQLwherehide += " and";
				SQLwherehide += SQLwherefiletype;
			}

			String SQLwherelinkgroup = "";
			Linkgroup linkgroups = new Linkgroup();
			linkgroups.records(db, "select * from linkgroups order by linkgroup");
			while (linkgroups.records(db, "")) {
				if (linkgroups.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
					if (! SQLwherelinkgroup.equals("")) SQLwherelinkgroup += ",";
					SQLwherelinkgroup += db.quote(Common.SQL_clean(linkgroups.getLinkgroup()));
				}
			}
			if (! SQLwherelinkgroup.equals("")) {
				SQLwherelinkgroup = " ((contentclass<>" + db.quote("link") + ") or ((contentgroup is null) or (contentgroup in (''," + SQLwherelinkgroup + "))))";
				if (! SQLwherehide.equals("")) SQLwherehide += " and";
				SQLwherehide += SQLwherelinkgroup;
			}

			String SQLwherelinktype = "";
			Linktype linktypes = new Linktype();
			linktypes.records(db, "select * from linktypes order by linktype");
			while (linktypes.records(db, "")) {
				if (linktypes.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
					if (! SQLwherelinktype.equals("")) SQLwherelinktype += ",";
					SQLwherelinktype += db.quote(Common.SQL_clean(linktypes.getLinktype()));
				}
			}
			if (! SQLwherelinktype.equals("")) {
				SQLwherelinktype = " ((contentclass<>" + db.quote("link") + ") or ((contenttype is null) or (contenttype in (''," + SQLwherelinktype + "))))";
				if (! SQLwherehide.equals("")) SQLwherehide += " and";
				SQLwherehide += SQLwherelinktype;
			}

			String SQLwhereproductgroup = "";
			Productgroup productgroups = new Productgroup();
			productgroups.records(db, "select * from productgroups order by productgroup");
			while (productgroups.records(db, "")) {
				if (productgroups.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
					if (! SQLwhereproductgroup.equals("")) SQLwhereproductgroup += ",";
					SQLwhereproductgroup += db.quote(Common.SQL_clean(productgroups.getProductgroup()));
				}
			}
			if (! SQLwhereproductgroup.equals("")) {
				SQLwhereproductgroup = " ((contentclass<>" + db.quote("product") + ") or ((contentgroup is null) or (contentgroup in (''," + SQLwhereproductgroup + "))))";
				if (! SQLwherehide.equals("")) SQLwherehide += " and";
				SQLwherehide += SQLwhereproductgroup;
			}

			String SQLwhereproducttype = "";
			Producttype producttypes = new Producttype();
			producttypes.records(db, "select * from producttypes order by producttype");
			while (producttypes.records(db, "")) {
				if (producttypes.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
					if (! SQLwhereproducttype.equals("")) SQLwhereproducttype += ",";
					SQLwhereproducttype += db.quote(Common.SQL_clean(producttypes.getProducttype()));
				}
			}
			if (! SQLwhereproducttype.equals("")) {
				SQLwhereproducttype = " ((contentclass<>" + db.quote("product") + ") or ((contenttype is null) or (contenttype in (''," + SQLwhereproducttype + "))))";
				if (! SQLwherehide.equals("")) SQLwherehide += " and";
				SQLwherehide += SQLwhereproducttype;
			}

			String SQLwhereversion = "";
			Version versions = new Version();
			versions.records(db, "select * from versions order by version");
			while (versions.records(db, "")) {
				if (versions.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
					if (! SQLwhereversion.equals("")) SQLwhereversion += ",";
					SQLwhereversion += db.quote(Common.SQL_clean(versions.getVersion()));
				}
			}
			if (! SQLwhereversion.equals("")) {
				SQLwhereversion = " ((version is null) or (version in (''," + SQLwhereversion + ")))";
				if (! SQLwherehide.equals("")) SQLwherehide += " and";
				SQLwherehide += SQLwhereversion;
			}

			if (SQLwhere.equals("")) {
				SQLwhere += " where ";
			} else {
				SQLwhere += " and ";
			}
			SQLwhere += "(" + SQLwherehide + ")";
		}
		return SQLwhere;
	}



	public void handleProductStock(ServletContext server, Page mypage, Fileupload filepost, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (mypage.getContentClass().equals("product")) {
			if ((filepost != null) && (filepost.getParameter("product_stock_amount_update").equals("set")) && (! filepost.getParameter("product_stock_amount").equals(""))) {
				mypage.setProductStockAmount(db, Common.intnumber(filepost.getParameter("product_stock_amount")));
			} else if ((filepost != null) && (filepost.getParameter("product_stock_amount_update").equals("add")) && (! filepost.getParameter("product_stock_amount").equals(""))) {
				mypage.addProductStockAmount(db, Common.intnumber(filepost.getParameter("product_stock_amount")));
			} else if ((filepost != null) && (filepost.getParameter("product_stock_amount_update").equals("subtract")) && (! filepost.getParameter("product_stock_amount").equals(""))) {
				mypage.addProductStockAmount(db, -1 * Common.intnumber(filepost.getParameter("product_stock_amount")));
			} else if ((myrequest.getParameter("product_stock_amount_update").equals("set")) && (! myrequest.getParameter("product_stock_amount").equals(""))) {
				mypage.setProductStockAmount(db, Common.intnumber(myrequest.getParameter("product_stock_amount")));
			} else if ((myrequest.getParameter("product_stock_amount_update").equals("add")) && (! myrequest.getParameter("product_stock_amount").equals(""))) {
				mypage.addProductStockAmount(db, Common.intnumber(myrequest.getParameter("product_stock_amount")));
			} else if ((myrequest.getParameter("product_stock_amount_update").equals("subtract")) && (! myrequest.getParameter("product_stock_amount").equals(""))) {
				mypage.addProductStockAmount(db, -1 * Common.intnumber(myrequest.getParameter("product_stock_amount")));
			}
			if ((filepost != null) && (filepost.getParameter("product_restocked_amount_update").equals("set")) && (! filepost.getParameter("product_restocked_amount").equals(""))) {
				mypage.setProductRestockedAmount(db, Common.intnumber(filepost.getParameter("product_restocked_amount")));
			} else if ((filepost != null) && (filepost.getParameter("product_restocked_amount_update").equals("add")) && (! filepost.getParameter("product_restocked_amount").equals(""))) {
				mypage.addProductRestockedAmount(db, Common.intnumber(filepost.getParameter("product_restocked_amount")));
			} else if ((filepost != null) && (filepost.getParameter("product_restocked_amount_update").equals("subtract")) && (! filepost.getParameter("product_restocked_amount").equals(""))) {
				mypage.addProductRestockedAmount(db, -1 * Common.intnumber(filepost.getParameter("product_restocked_amount")));
			} else if ((myrequest.getParameter("product_restocked_amount_update").equals("set")) && (! myrequest.getParameter("product_restocked_amount").equals(""))) {
				mypage.setProductRestockedAmount(db, Common.intnumber(myrequest.getParameter("product_restocked_amount")));
			} else if ((myrequest.getParameter("product_restocked_amount_update").equals("add")) && (! myrequest.getParameter("product_restocked_amount").equals(""))) {
				mypage.addProductRestockedAmount(db, Common.intnumber(myrequest.getParameter("product_restocked_amount")));
			} else if ((myrequest.getParameter("product_restocked_amount_update").equals("subtract")) && (! myrequest.getParameter("product_restocked_amount").equals(""))) {
				mypage.addProductRestockedAmount(db, -1 * Common.intnumber(myrequest.getParameter("product_restocked_amount")));
			}
		}
	}



	public String doStock(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		// get administrator product stock details
		error = "";
		if (db == null) return "0:::0:::";
		boolean accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "0:::0:::";
		String id = html.encodeHtmlEntities(myrequest.getParameter("id"));
		Page mypage = getPageById(id, server, mysession, myrequest, myresponse, myconfig, db, false);
		handleProductStock(server, mypage, null, mysession, myrequest, myresponse, myconfig, db);
		mypage = getPageById(id, server, mysession, myrequest, myresponse, myconfig, db, false);
		if (! myrequest.getParameter("product_stock_amount_update").equals("")) {
			int stock_amount = Common.intnumber(mypage.getProductStockAmount(db));
			if (stock_amount < 0) {
				return "" + mypage.getId() + ":::" + stock_amount + ":::ordered:::" + mypage.getProductNoStockText();
			} else if (stock_amount == 0) {
				return "" + mypage.getId() + ":::" + stock_amount + ":::no:::" + mypage.getProductNoStockText();
			} else if (stock_amount <= Common.intnumber(mypage.getProductLowStockAmount())) {
				return "" + mypage.getId() + ":::" + stock_amount + ":::low:::" + mypage.getProductLowStockText();
			} else {
				return "" + mypage.getId() + ":::" + stock_amount + ":::in:::" + mypage.getProductStockText();
			}
		} else if (! myrequest.getParameter("product_restocked_amount_update").equals("")) {
			int restocked_amount = Common.intnumber(mypage.getProductRestockedAmount(db));
			return "" + mypage.getId() + ":::" + restocked_amount + ":::restocked:::";
		} else {
			return "0:::0:::";
		}
	}



}
