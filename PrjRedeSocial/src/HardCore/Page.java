package HardCore;

import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import com.iliketo.bean.AnnounceJB;
import com.iliketo.util.CmsConfigILiketo;

public class Page extends Content {
	private Page template_content = null;
	private ArrayList<Content> stylesheet_contents = new ArrayList<Content>();
	private String version_master_title = null;
	private String page_up_title = null;
	private String page_top_title = null;
	private String page_next_title = null;
	private String page_previous_title = null;
	private String page_last_title = null;
	private String page_first_title = null;
	private String page_title = "";
	private String body = "";
	private User dummy_user = null;
	private Hosting dummy_hosting = null;
	private String parse_output_list_link_start = "";
	private int parse_output_list_link_limit = 0;
	private int parse_output_list_link_first = 0;
	private int parse_output_list_link_previous = 0;
	private int parse_output_list_link_next = 0;
	private int parse_output_list_link_last = 0;
	private int parse_output_list_link_count = 0;
	private boolean do_parse_output = true;

	private Session _session = null;
	private String _session_administrator = "";
	private String _session_userid = "";
	private String _session_username = "";
	private String _session_usertype = "";
	private String _session_usergroup = "";
	private String _session_usertypes = "";
	private String _session_usergroups = "";
	private DB _db = null;
	private Configuration _config = null;
	private String _table = "";
	private String _column = "";
	private String _session_device = "";
	private String _session_usersegments = "";
	private String _session_usertests = "";
	private String _session_version = "";
	private String _default_version = "";

/*
	private Pattern regexLoop1 = null;
	private Pattern regexLoop2 = null;
	private Pattern regexLoop3 = null;
	private Pattern regexLoop4 = null;
	private Pattern regexParams = null;
	private Pattern regexDisplay = null;
	private Pattern regexDisplayHtml = null;
	private Pattern regexDisplayScript = null;
	private Pattern regexDisplayText = null;
	private Pattern regexUrl = null;
	private Pattern regexCalc = null;
	private Pattern regexIf = null;
	private Pattern regexElseIf = null;
	private Pattern regexElse = null;
	private Pattern regexEndIf = null;
	private Pattern regexOrder = null;
	private Pattern regexFirst = null;
	private Pattern regexPrevious = null;
	private Pattern regexNext = null;
	private Pattern regexLast = null;
	private Pattern regexPaged = null;
	private Pattern regexListWhere = null;
*/
	static private Pattern regexLoop1 = Pattern.compile("((###(([a-zA-Z0-9%\\w][-_ a-zA-Z0-9%\\w]*?)(\\.[a-zA-Z]+)?)###)|(@@@(([a-zA-Z0-9%\\w][- a-zA-Z0-9%\\w]*?)([_.]((?!@@@)[^.])+)?)@@@))", Pattern.DOTALL);
	static private Pattern regexLoop2 = Pattern.compile("((###(([a-zA-Z0-9%\\w][-_ a-zA-Z0-9%\\w]*?)(\\.[a-zA-Z]+)?)###)|(@@@(([a-zA-Z0-9%\\w][- a-zA-Z0-9%\\w]*?)([_.]((?!@@@)[^.])+)?)@@@)|(@@@((config|get|cookie|url|urlpathquery|urlpath|urlquery|now|created|updated|published|count|sum|min|max|avg|metainfo|productinfo)([:]((?!@@@).)+)?)@@@))", Pattern.DOTALL);
	static private Pattern regexLoop3 = Pattern.compile("((###(([a-zA-Z0-9%\\w][-_ a-zA-Z0-9%\\w]*?)(\\.[a-zA-Z]+)?)###)|(@@@(([a-zA-Z0-9%\\w][- a-zA-Z0-9%\\w]*?)([_.]((?!@@@)[^.])+)?)@@@)|(@@@((include|random)([:]((?!@@@).)+)?)@@@))", Pattern.DOTALL);
	static private Pattern regexLoop4 = Pattern.compile("((###(([a-zA-Z0-9%\\w][-_ a-zA-Z0-9%\\w]*?)(\\.[a-zA-Z]+)?)###)|(@@@(([a-zA-Z0-9%\\w][- a-zA-Z0-9%\\w]*?)([_.]((?!@@@)[^.])+)?)@@@)|(@@@(([a-zA-Z0-9%\\w][- a-zA-Z0-9%\\w]*?)([:]((?!@@@).)+)?)@@@))", Pattern.DOTALL);
	static private Pattern regexParams = Pattern.compile("###([-_ a-zA-Z0-9%\\w]+?)(\\.[a-zA-Z]+)?###");
	static private Pattern regexDisplay = Pattern.compile("^@@@(title|content|summary|author|keywords|description|created|updated|published|created_by|updated_by|published_by|image1|image2|image3|file1|file2|file3|link1|link2|link3)@@@$");
	static private Pattern regexDisplayHtml = Pattern.compile("^@@@(content|summary)\\.html@@@$");
	static private Pattern regexDisplayScript = Pattern.compile("^@@@(content|summary)\\.script@@@$");
	static private Pattern regexDisplayText = Pattern.compile("^@@@(content|summary)\\.text@@@$");
	static private Pattern regexUrl = Pattern.compile("^@@@(url|urlpathquery|urlpath|urlquery)@@@$");
	static private Pattern regexCalc = Pattern.compile("^(count|sum|min|max|avg)$");
	static private Pattern regexIf = Pattern.compile("^@@@condition:([^:@]+?):if:.*$");
	static private Pattern regexElseIf = Pattern.compile("^@@@condition:([^:@]+?):elseif:.*$");
	static private Pattern regexElse = Pattern.compile("^@@@condition:([^:@]+?):else@@@$");
	static private Pattern regexEndIf = Pattern.compile("^@@@condition:([^:@]+?):endif@@@$");
	static private Pattern regexOrder = Pattern.compile("^@@@(items|status|revision|quantity|currencytitle|currency|subtotal|order_id|order_status|order_quantity|order_currencytitle|order_currency|order_subtotal|order_created|order_created_by|order_updated|order_updated_by|order_closed|order_closed_by|order_paid|order_created:format=([^@]*?)|order_updated:format=([^@]*?)|order_closed:format=([^@]*?)|order_paid:format=([^@]*?)|tax_description|tax_currency|tax_currencytitle|tax|tax:(.*?)|shipping_description|shipping_currency|shipping_currencytitle|shipping|shipping:(.*?)|discount_description|discount_currency|discount_currencytitle|discount|discount:(.*?)|discount_description|discount_currency|discount_currencytitle|discount|@@@discount:(.*?)@@@|total|card_type|card_number|card_issuedmonth|card_issuedyear|card_expirymonth|card_expiryyear|card_name|card_cvc|card_issue|card_postalcode|delivery_name|delivery_organisation|delivery_address|delivery_postalcode|delivery_city|delivery_state|delivery_country|delivery_phone|delivery_fax|delivery_email|delivery_website|invoice_name|invoice_organisation|invoice_address|invoice_postalcode|invoice_city|invoice_state|invoice_country|invoice_phone|invoice_fax|invoice_email|invoice_website)@@@$");
	static private Pattern regexFirst = Pattern.compile("@@@first:([a-zA-Z][a-zA-Z0-9\\w]*?):([^@]+@?[^@]+?)@@@", Pattern.CASE_INSENSITIVE);
	static private Pattern regexPrevious = Pattern.compile("@@@previous:([a-zA-Z][a-zA-Z0-9\\w]*?):([^@]+@?[^@]+?)@@@", Pattern.CASE_INSENSITIVE);
	static private Pattern regexNext = Pattern.compile("@@@next:([a-zA-Z][a-zA-Z0-9\\w]*?):([^@]+@?[^@]+?)@@@", Pattern.CASE_INSENSITIVE);
	static private Pattern regexLast = Pattern.compile("@@@last:([a-zA-Z][a-zA-Z0-9\\w]*?):([^@]+@?[^@]+?)@@@", Pattern.CASE_INSENSITIVE);
	static private Pattern regexPaged = Pattern.compile("@@@paged:([a-zA-Z][a-zA-Z0-9\\w]*?):([^@]+@?[^@]+?)@@@", Pattern.CASE_INSENSITIVE);
	static private Pattern regexListWhere = Pattern.compile("^([^!=]+)(<=|>=|=<|=>|&lt;=|&gt;=|=&lt;|=&gt;|=|!=| in |<|>|&lt;|&gt;)(.*?)$", Pattern.CASE_INSENSITIVE);



	public Page() {
		text = new Text();
		stylesheet_contents = new ArrayList<Content>();
		do_parse_output = true;
		init();
		dummy_user = new User(null);;
		dummy_hosting = new Hosting(text);
	}



	public Page(Text mytext) {
		if (mytext != null) text = mytext;
		stylesheet_contents = new ArrayList<Content>();
		do_parse_output = true;
		init();
		dummy_user = new User(null);;
		dummy_hosting = new Hosting(text);
	}



	public void read_primary_only(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version) {
		read_primary_only(db, config, id, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, "", "", "", null);
	}
	public void read_primary_only(DB db, Configuration config, String id, String table, String column, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, Session session) {
		super.read(db, config, id, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
		body = getContent();
		body = adjust_links(session_version, body, db, config);
		setContent(adjust_links(session_version, getContent(), db, config));
		setHtmlAttributes(adjust_links(session_version, getHtmlAttributes(), db, config));
	}



	public void read(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_template, String default_template, String session_stylesheet, String default_stylesheet, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		read(server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, "", "", "", session_template, default_template, session_stylesheet, default_stylesheet, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public void read(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_template, String default_template, String session_stylesheet, String default_stylesheet, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		read(server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_template, "", default_template, session_stylesheet, "", default_stylesheet, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
		body = adjust_links(session_version, body, db, config);
		setContent(adjust_links(session_version, getContent(), db, config));
		setHtmlAttributes(adjust_links(session_version, getHtmlAttributes(), db, config));
	}
	public void read(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_template, String website_template, String default_template, String session_stylesheet, String website_stylesheet, String default_stylesheet, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		read(server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, "", "", "", session_template, website_template, default_template, session_stylesheet, website_stylesheet, default_stylesheet, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public void read(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_template, String website_template, String default_template, String session_stylesheet, String website_stylesheet, String default_stylesheet, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		super.read(db, config, id, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
		body = adjust_links(session_version, body, db, config);
		setContent(adjust_links(session_version, getContent(), db, config));
		setHtmlAttributes(adjust_links(session_version, getHtmlAttributes(), db, config));
		parse(server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_template, website_template, default_template, session_stylesheet, website_stylesheet, default_stylesheet, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}



	public void parse(ServletContext server, Request request, Response response, Session session, DB db, Configuration config) throws Exception {
		parse(server, request, response, session, request.getServletPath(), request.getQueryString(), session.get("mode"), session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config, getId(), "content", "id", session.get("version"), config.get(db, "default_version"), session.get("device"), session.get("usersegments"), session.get("usertests"), session.get("template"), "", config.get(db, "default_template"), session.get("stylesheet"), "", config.get(db, "default_stylesheet"), session.get("currency"), config.get(db, "default_currency"), config.get(db, "default_shopcartsummary_page"), config.get(db, "default_shopcartsummary_entry"));
	}
       	public void parse(ServletContext server, Request request, Response response, Session session, DB db, Configuration config, String website_template, String website_stylesheet) throws Exception {
		parse(server, request, response, session, request.getServletPath(), request.getQueryString(), session.get("mode"), session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config, getId(), "content", "id", session.get("version"), config.get(db, "default_version"), session.get("device"), session.get("usersegments"), session.get("usertests"), session.get("template"), website_template, config.get(db, "default_template"), session.get("stylesheet"), website_stylesheet, config.get(db, "default_stylesheet"), session.get("currency"), config.get(db, "default_currency"), config.get(db, "default_shopcartsummary_page"), config.get(db, "default_shopcartsummary_entry"));
       	}
       	public void parse(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_template, String website_template, String default_template, String session_stylesheet, String website_stylesheet, String default_stylesheet, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		parse(server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, "", "", "", session_template, website_template, default_template, session_stylesheet, website_stylesheet, default_stylesheet, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
       	}
       	public void parse(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_template, String website_template, String default_template, String session_stylesheet, String website_stylesheet, String default_stylesheet, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		if ((table.equals("content_archive")) && (column.equals("archiveid"))) {
			table = "content";
			column = "id";
		}
		parse_input(session, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_template, website_template, default_template, session_stylesheet, website_stylesheet, default_stylesheet, default_shopcartsummary_page, default_shopcartsummary_entry);
		parse_input_elements(session, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests);
		if (do_parse_output) {
			try {
				parse_output(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
			} catch (Exception e) {
				// loop detected
				System.out.println("ERROR:HardCore/Page.parse:loop_detected:"+getId()+":::"+e);
			}
		}
	}



	public void parse_input(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_template, String default_template, String session_stylesheet, String default_stylesheet, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		parse_input(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, "", "", "", session_template, default_template, session_stylesheet, default_stylesheet, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public void parse_input(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_template, String default_template, String session_stylesheet, String default_stylesheet, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		parse_input(null, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_template, "", default_template, session_stylesheet, "", default_stylesheet, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public void parse_input(Session session, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_template, String default_template, String session_stylesheet, String default_stylesheet, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		parse_input(session, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, "", "", "", session_template, default_template, session_stylesheet, default_stylesheet, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public void parse_input(Session session, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_template, String default_template, String session_stylesheet, String default_stylesheet, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		parse_input(session, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_template, "", default_template, session_stylesheet, "", default_stylesheet, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public void parse_input(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_template, String website_template, String default_template, String session_stylesheet, String website_stylesheet, String default_stylesheet, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		parse_input(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, "", "", "", session_template, website_template, default_template, session_stylesheet, website_stylesheet, default_stylesheet, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public void parse_input(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_template, String website_template, String default_template, String session_stylesheet, String website_stylesheet, String default_stylesheet, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		parse_input(null, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_template, website_template, default_template, session_stylesheet, website_stylesheet, default_stylesheet, default_shopcartsummary_page, default_shopcartsummary_entry, false);
	}
	public void parse_input(Session session, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_template, String website_template, String default_template, String session_stylesheet, String website_stylesheet, String default_stylesheet, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		parse_input(session, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, "", "", "", session_template, website_template, default_template, session_stylesheet, website_stylesheet, default_stylesheet, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public void parse_input(Session session, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_template, String website_template, String default_template, String session_stylesheet, String website_stylesheet, String default_stylesheet, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		parse_input(session, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_template, website_template, default_template, session_stylesheet, website_stylesheet, default_stylesheet, default_shopcartsummary_page, default_shopcartsummary_entry, false);
	}
	public void parse_input(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_template, String website_template, String default_template, String session_stylesheet, String website_stylesheet, String default_stylesheet, String default_shopcartsummary_page, String default_shopcartsummary_entry, boolean is_parsing_template) {
		parse_input(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, "", "", "", session_template, website_template, default_template, session_stylesheet, website_stylesheet, default_stylesheet, default_shopcartsummary_page, default_shopcartsummary_entry, is_parsing_template);
	}
	public void parse_input(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_template, String website_template, String default_template, String session_stylesheet, String website_stylesheet, String default_stylesheet, String default_shopcartsummary_page, String default_shopcartsummary_entry, boolean is_parsing_template) {
		parse_input(null, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_template, website_template, default_template, session_stylesheet, website_stylesheet, default_stylesheet, default_shopcartsummary_page, default_shopcartsummary_entry, is_parsing_template);
	}
	public void parse_input(Session session, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_template, String website_template, String default_template, String session_stylesheet, String website_stylesheet, String default_stylesheet, String default_shopcartsummary_page, String default_shopcartsummary_entry, boolean is_parsing_template) {
		parse_input(session, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, "", "", "", session_template, website_template, default_template, session_stylesheet, website_stylesheet, default_stylesheet, default_shopcartsummary_page, default_shopcartsummary_entry, is_parsing_template);
	}
	public void parse_input(Session session, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_template, String website_template, String default_template, String session_stylesheet, String website_stylesheet, String default_stylesheet, String default_shopcartsummary_page, String default_shopcartsummary_entry, boolean is_parsing_template) {
		if ((getContentClass().equals("stylesheet")) || (getContentClass().equals("script")) || (getContentClass().equals("image")) || (getContentClass().equals("file")) || (getContentClass().equals("link"))) {
			template_content = new Page(text);
		} else if (session_template.equals("0")) {
			template_content = new Page(text);
		} else if ((! session_template.equals("")) && (! getContentClass().equals("template")) && (! getContentClass().equals("")) && (! is_parsing_template)) {
			template_content = new Page(text);
			template_content.read_primary_only(db, config, session_template, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
				template_content.scheduled_read(db, config, session_template, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			}
			if (! template_content.getContentClass().equals("template")) {
				template_content = new Page(text);
			} else if (! template_content.getUser()) {
				template_content = new Page(text);
			} else {
				body = template_content.getContent();
				if (body.equals("")) body = "@@@content@@@";
			}
		} else if (getTemplate().equals("-")) {
			template_content = new Page(text);
			if ((! default_template.equals("")) && (! default_template.equals("0"))) {
				template_content.read_primary_only(db, config, default_template, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
				if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
					template_content.scheduled_read(db, config, default_template, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
				}
			}
			if (! template_content.getUser()) {
				template_content = new Page(text);
			}
			body = template_content.getContent();
			if (body.equals("")) body = "@@@content@@@";
		} else if (getTemplate().equals("0")) {
			template_content = new Page(text);
		} else if ((! getTemplate().equals("")) && (! getContentClass().equals(""))) {
			template_content = new Page(text);
			template_content.read_primary_only(db, config, getTemplate(), table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
				template_content.scheduled_read(db, config, getTemplate(), session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			}
			if (! template_content.getUser()) {
				template_content = new Page(text);
			}
			body = template_content.getContent();
			if (body.equals("")) body = "@@@content@@@";
		} else if (getContentgroupTemplate().equals("0")) {
			template_content = new Page(text);
		} else if ((! getContentgroupTemplate().equals("")) && (! getContentClass().equals("template")) && (! getContentClass().equals(""))) {
			template_content = new Page(text);
			template_content.read_primary_only(db, config, getContentgroupTemplate(), table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
				template_content.scheduled_read(db, config, getContentgroupTemplate(), session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			}
			if (! template_content.getUser()) {
				template_content = new Page(text);
			}
			body = template_content.getContent();
			if (body.equals("")) body = "@@@content@@@";
		} else if (getContenttypeTemplate().equals("0")) {
			template_content = new Page(text);
		} else if ((! getContenttypeTemplate().equals("")) && (! getContentClass().equals("template")) && (! getContentClass().equals(""))) {
			template_content = new Page(text);
			template_content.read_primary_only(db, config, getContenttypeTemplate(), table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
				template_content.scheduled_read(db, config, getContenttypeTemplate(), session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			}
			if (! template_content.getUser()) {
				template_content = new Page(text);
			}
			body = template_content.getContent();
			if (body.equals("")) body = "@@@content@@@";
		} else if (website_template.equals("0")) {
			template_content = new Page(text);
		} else if ((! website_template.equals("")) && (! getContentClass().equals("template")) && (! getContentClass().equals(""))) {
			template_content = new Page(text);
			template_content.read_primary_only(db, config, website_template, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
				template_content.scheduled_read(db, config, website_template, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			}
			if (! template_content.getUser()) {
				template_content = new Page(text);
			}
			body = template_content.getContent();
			if (body.equals("")) body = "@@@content@@@";
		} else if (default_template.equals("0")) {
			template_content = new Page(text);
		} else if ((! default_template.equals("")) && (! getContentClass().equals("template")) && (! getContentClass().equals(""))) {
			template_content = new Page(text);
			template_content.read_primary_only(db, config, default_template, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
				template_content.scheduled_read(db, config, default_template, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			}
			if (! template_content.getUser()) {
				template_content = new Page(text);
			}
			body = template_content.getContent();
			if (body.equals("")) body = "@@@content@@@";
		} else {
			template_content = new Page(text);
		}

		if ((! template_content.getTemplate().equals("")) && (! template_content.getTemplate().equals("0"))) {
			Page template2 = new Page(text);
			if (template_content.getTemplate().equals("-")) {
				template2.read_primary_only(db, config, default_template, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			} else {
				template2.read_primary_only(db, config, template_content.getTemplate(), table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			}
			if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
				if (template_content.getTemplate().equals("-")) {
					template2.scheduled_read(db, config, default_template, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
				} else {
					template2.scheduled_read(db, config, template_content.getTemplate(), session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
				}
			}
			if (! template2.getUser()) {
				template2 = new Page(text);
			}
			template_content.setTemplateContent(template2);
			body = template2.getContent().replaceAll("@@@content@@@", template_content.getContent().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (! template2.getStyleSheet().equals("")) {
				template_content.setStyleSheet(template2.getStyleSheet() + "," + template_content.getStyleSheet());
			}
		}
		if ((! template_content.getStyleSheet().equals("")) && (! is_parsing_template)) {
			template_content.parse_input(session, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_template, "", default_template, session_stylesheet, "", default_stylesheet, default_shopcartsummary_page, default_shopcartsummary_entry, true);
		}

		if ((getContentClass().equals("stylesheet")) || (getContentClass().equals("script")) || (getContentClass().equals("image")) || (getContentClass().equals("file")) || (getContentClass().equals("link"))) {
			stylesheet_contents = new ArrayList<Content>();
		} else if (session_stylesheet.equals("0")) {
			stylesheet_contents = new ArrayList<Content>();
			stylesheet_contents.add(0, new Content(text));
		} else if ((! session_stylesheet.equals("")) && (! getContentClass().equals("stylesheet")) && (! getContentClass().equals(""))) {
			stylesheet_contents = new ArrayList<Content>();
			String[] mystylesheets = session_stylesheet.split(",");
			for (int i=0; i<mystylesheets.length; i++) {
				String mystylesheet = "" + mystylesheets[i];
				stylesheet_contents.add(i, new Content(text));
				if (! mystylesheet.equals("")) {
					((Content)stylesheet_contents.get(i)).read(db, config, mystylesheet, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
						((Content)stylesheet_contents.get(i)).scheduled_read(db, config, mystylesheet, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					}
				} else {
					((Content)stylesheet_contents.get(i)).read(db, config, default_stylesheet, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
						((Content)stylesheet_contents.get(i)).scheduled_read(db, config, default_stylesheet, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					}
				}
				if (! ((Content)stylesheet_contents.get(i)).getContentClass().equals("stylesheet")) {
					stylesheet_contents.set(i, new Content(text));
				}
			}
		} else if (getStyleSheet().equals("0")) {
			stylesheet_contents = new ArrayList<Content>();
		} else if ((! getStyleSheet().equals("")) && (! getContentClass().equals("stylesheet")) && (! getContentClass().equals(""))) {
			stylesheet_contents = new ArrayList<Content>();
			String[] mystylesheets = getStyleSheet().split(",");
			for (int i=0; i<mystylesheets.length; i++) {
				String mystylesheet = "" + mystylesheets[i];
				stylesheet_contents.add(i, new Content(text));
				if (! mystylesheet.equals("")) {
					((Content)stylesheet_contents.get(i)).read(db, config, mystylesheet, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
						((Content)stylesheet_contents.get(i)).scheduled_read(db, config, mystylesheet, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					}
				} else {
					((Content)stylesheet_contents.get(i)).read(db, config, default_stylesheet, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
						((Content)stylesheet_contents.get(i)).scheduled_read(db, config, default_stylesheet, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					}
				}
				if (! ((Content)stylesheet_contents.get(i)).getContentClass().equals("stylesheet")) {
					stylesheet_contents.set(i, new Content(text));
				}
			}
		} else if (getContentgroupStylesheet().equals("0")) {
			stylesheet_contents = new ArrayList<Content>();
		} else if ((! getContentgroupStylesheet().equals("")) && (! getContentClass().equals("stylesheet")) && (! getContentClass().equals(""))) {
			stylesheet_contents = new ArrayList<Content>();
			String[] mystylesheets = getContentgroupStylesheet().split(",");
			for (int i=0; i<mystylesheets.length; i++) {
				String mystylesheet = "" + mystylesheets[i];
				stylesheet_contents.add(i, new Content(text));
				if (! mystylesheet.equals("")) {
					((Content)stylesheet_contents.get(i)).read(db, config, mystylesheet, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
						((Content)stylesheet_contents.get(i)).scheduled_read(db, config, mystylesheet, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					}
				} else {
					((Content)stylesheet_contents.get(i)).read(db, config, default_stylesheet, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
						((Content)stylesheet_contents.get(i)).scheduled_read(db, config, default_stylesheet, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					}
				}
				if (! ((Content)stylesheet_contents.get(i)).getContentClass().equals("stylesheet")) {
					stylesheet_contents.set(i, new Content(text));
				}
			}
		} else if (getContenttypeStylesheet().equals("0")) {
			stylesheet_contents = new ArrayList<Content>();
		} else if ((! getContenttypeStylesheet().equals("")) && (! getContentClass().equals("stylesheet")) && (! getContentClass().equals(""))) {
			stylesheet_contents = new ArrayList<Content>();
			String[] mystylesheets = getContenttypeStylesheet().split(",");
			for (int i=0; i<mystylesheets.length; i++) {
				String mystylesheet = "" + mystylesheets[i];
				stylesheet_contents.add(i, new Content(text));
				if (! mystylesheet.equals("")) {
					((Content)stylesheet_contents.get(i)).read(db, config, mystylesheet, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
						((Content)stylesheet_contents.get(i)).scheduled_read(db, config, mystylesheet, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					}
				} else {
					((Content)stylesheet_contents.get(i)).read(db, config, default_stylesheet, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
						((Content)stylesheet_contents.get(i)).scheduled_read(db, config, default_stylesheet, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					}
				}
				if (! ((Content)stylesheet_contents.get(i)).getContentClass().equals("stylesheet")) {
					stylesheet_contents.set(i, new Content(text));
				}
			}
		} else if (website_stylesheet.equals("0")) {
			stylesheet_contents = new ArrayList<Content>();
		} else if ((! website_stylesheet.equals("")) && (! getContentClass().equals("stylesheet")) && (! getContentClass().equals(""))) {
			stylesheet_contents = new ArrayList<Content>();
			stylesheet_contents.add(0, new Content(text));
			((Content)stylesheet_contents.get(0)).read(db, config, website_stylesheet, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
				((Content)stylesheet_contents.get(0)).scheduled_read(db, config, website_stylesheet, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			}
		} else if (default_stylesheet.equals("0")) {
			stylesheet_contents = new ArrayList<Content>();
		} else if ((! default_stylesheet.equals("")) && (! getContentClass().equals("stylesheet")) && (! getContentClass().equals(""))) {
			stylesheet_contents = new ArrayList<Content>();
			stylesheet_contents.add(0, new Content(text));
			((Content)stylesheet_contents.get(0)).read(db, config, default_stylesheet, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
				((Content)stylesheet_contents.get(0)).scheduled_read(db, config, default_stylesheet, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			}
		} else {
			stylesheet_contents = new ArrayList<Content>();
		}

		_db = db;
		_config = config;
		_session = session;
		_session_administrator = "" + session_administrator;
		_session_userid = "" + session_userid;
		_session_username = "" + session_username;
		_session_usertype = "" + session_usertype;
		_session_usergroup = "" + session_usergroup;
		_session_usertypes = "" + session_usertypes;
		_session_usergroups = "" + session_usergroups;
		_session_device = "" + session_device;
		_session_usersegments = "" + session_usersegments;
		_session_usertests = "" + session_usertests;
		_session_version = "" + session_version;
		_default_version = "" + default_version;
		_table = "" + table;
		_column = "" + column;

		version_master_title = null;
		page_up_title = null;
		page_top_title = null;
		page_previous_title = null;
		page_next_title = null;
		page_first_title = null;
		page_last_title = null;
	}



	public void parse_input_elements(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version) {
		parse_input_elements(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, "", "", "");
	}
	public void parse_input_elements(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests) {
		parse_input_elements(null, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests);
	}
	public void parse_input_elements(Session session, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version) {
		parse_input_elements(session, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, table, column, session_version, default_version, "", "", "");
	}
	public void parse_input_elements(Session session, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests) {
		HashMap element = getElement();
		Iterator elementitems = element.keySet().iterator();
		while (elementitems.hasNext()) {
			String elementitem = "" + elementitems.next();
			if ((elementitem != null) && (! elementitem.equals(""))) {
				String elementitem_id = parse_input_elements_elementitem_id(db, config, elementitem, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
				if (! elementitem_id.equals("0")) {
					String[] elementitem_ids = elementitem_id.split(",");
					Content[] mycontents = new Content[elementitem_ids.length];
					getElementContent().put(elementitem, mycontents);
					for (int i=0; i<elementitem_ids.length; i++) {
						elementitem_id = elementitem_ids[i];
						((Content[]) getElementContent().get(elementitem))[i] = new Content(text);
						if ((table.equals("content_archive")) && (column.equals("archiveid"))) {
							((Content[]) getElementContent().get(elementitem))[i].read(db, config, elementitem_id, "content", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
						} else {
							((Content[]) getElementContent().get(elementitem))[i].read(db, config, elementitem_id, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
							if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
								((Content[]) getElementContent().get(elementitem))[i].scheduled_read(db, config, elementitem_id, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
							}
						}
						if (! ((Content[]) getElementContent().get(elementitem))[i].getUser()) {
							((Content[]) getElementContent().get(elementitem))[i] = new Content(text);
						}
					}
				} else {
					Content[] mycontents = new Content[1];
					mycontents[0] = new Content(text);
					getElementContent().put(elementitem, mycontents);
				}
			}
		}

		if (template_content != null) {
			Iterator template_elementitems = template_content.getElement().keySet().iterator();
			while (template_elementitems.hasNext()) {
				String elementitem = "" + template_elementitems.next();
				if (getElementContent().get(elementitem) == null) {
					String elementitem_id = template_content.parse_input_elements_elementitem_id(db, config, elementitem, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					if (! elementitem_id.equals("0")) {
						String[] elementitem_ids = elementitem_id.split(",");
						Content[] mycontents = new Content[elementitem_ids.length];
						getElementContent().put(elementitem, mycontents);
						for (int i=0; i<elementitem_ids.length; i++) {
							elementitem_id = elementitem_ids[i];
							((Content[]) getElementContent().get(elementitem))[i] = new Content(text);
							if ((table.equals("content_archive")) && (column.equals("archiveid"))) {
								((Content[]) getElementContent().get(elementitem))[i].read(db, config, elementitem_id, "content", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
							} else {
								((Content[]) getElementContent().get(elementitem))[i].read(db, config, elementitem_id, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
								if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
									((Content[]) getElementContent().get(elementitem))[i].scheduled_read(db, config, elementitem_id, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
								}
							}
							if (! ((Content[]) getElementContent().get(elementitem))[i].getUser()) {
								((Content[]) getElementContent().get(elementitem))[i] = new Content(text);
							}
						}
					}
				}
			}
		}

		if ((template_content != null) && (template_content.getTemplateContent() != null)) {
			Iterator template_elementitems = template_content.getTemplateContent().getElement().keySet().iterator();
			while (template_elementitems.hasNext()) {
				String elementitem = "" + template_elementitems.next();
				if (getElementContent().get(elementitem) == null) {
					String elementitem_id = template_content.getTemplateContent().parse_input_elements_elementitem_id(db, config, elementitem, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					if (! elementitem_id.equals("0")) {
						String[] elementitem_ids = elementitem_id.split(",");
						Content[] mycontents = new Content[elementitem_ids.length];
						getElementContent().put(elementitem, mycontents);
						for (int i=0; i<elementitem_ids.length; i++) {
							elementitem_id = elementitem_ids[i];
							((Content[]) getElementContent().get(elementitem))[i] = new Content(text);
							if ((table.equals("content_archive")) && (column.equals("archiveid"))) {
								((Content[]) getElementContent().get(elementitem))[i].read(db, config, elementitem_id, "content", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
							} else {
								((Content[]) getElementContent().get(elementitem))[i].read(db, config, elementitem_id, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
								if ((session != null) && ((session.get("mode").equals("preview")) || (session.get("mode").equals("admin"))) && (table.equals("content"))) {
									((Content[]) getElementContent().get(elementitem))[i].scheduled_read(db, config, elementitem_id, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
								}
							}
							if (! ((Content[]) getElementContent().get(elementitem))[i].getUser()) {
								((Content[]) getElementContent().get(elementitem))[i] = new Content(text);
							}
						}
					}
				}
			}
		}
	}



	public String parse_input_elements_elementitem_id(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String elementitem, String table, String column, String session_version, String default_version) {
		return parse_input_elements_elementitem_id(db, config, elementitem, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, "", "", "", null);
	}
	public String parse_input_elements_elementitem_id(DB db, Configuration config, String elementitem, String table, String column, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, Session session) {
		HashMap element = getElement();
		if ((element != null) && (element.get(elementitem) != null)) {
			if (element.get(elementitem).equals("-2")) {
				//String SQL = "select " + db_random() + ",* from " + table + " where contentclass=" + db.quote(elementitem) + " order by 1";
				//String SQL = "select * from " + table + " where contentclass=" + db.quote(elementitem) + " order by " + db_random();
				//getElementContent().get(elementitem).records(db, SQL);
				//getElementContent().get(elementitem).records(db, "");
				// The database rand() does not always work reliably, so do it the hard way
				String SQL = "select id from " + table + " where contentclass=" + db.quote(elementitem) + " order by id";
				int tmp_count = 0;
				String tmp_random = "0";
				Content tmp_content = new Content(text);
				tmp_content.records(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, SQL);
				while (tmp_content.records(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, "")) {
					tmp_count += 1;
					if (Math.random() <= ((double)1/tmp_count)) {
						tmp_random = tmp_content.getId();
					}
				}
				return "" + tmp_random;
			} else if (element.get(elementitem).equals("0")) {
				return "0";
			} else if ((element.get(elementitem).equals("-1")) || (element.get(elementitem).equals(""))) {
				if (template_content != null) {
					return template_content.parse_input_elements_elementitem_id(db, config, elementitem, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
				} else {
					return "0";
				}
			} else {
				return "" + element.get(elementitem);
			}
		} else {
			return "0";
		}
	}



	public void parse_output(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_administrator, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		parse_output(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public void parse_output(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_administrator, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		parse_output(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, id);
	}
	public void parse_output(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_administrator, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, String parse_output_id) throws Exception {
		parse_output(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, parse_output_id);
	}
	public void parse_output(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_administrator, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, String parse_output_id) throws Exception {
		if (_DEBUG_) System.out.println("HardCore/Page.parse_output:"+table+":"+column+":"+id+":::"+parse_output_id);
		if (request.getAttribute("parse_output_loopdetection") == null) {
			request.setAttribute("parse_output_loopdetection", new HashMap<String,String>());
		}
@SuppressWarnings("unchecked")
		HashMap<String,String> parse_output_loopdetection = (HashMap<String,String>)request.getAttribute("parse_output_loopdetection");
		if (parse_output_loopdetection.get("" + parse_output_id) == null) {
			parse_output_loopdetection.put("" + parse_output_id, "" + 0);
		}
		if (Common.intnumber("0" + parse_output_loopdetection.get("" + parse_output_id)) < 10) {
			try {
				parse_output_loopdetection.put("" + parse_output_id, "" + (Common.intnumber("0" + parse_output_loopdetection.get("" + parse_output_id)) + 1));
				if ((! getTitle().contains("###")) && (! getTitle().contains("@@@"))) {
					editable.put("title_" + getId(), Boolean.TRUE);
					page_title = getTitle();
				} else {
					editable.put("title_" + getId(), Boolean.FALSE);
					page_title = parse_output_replace(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, getTitle());
				}
				if ((! getSummary().contains("###")) && (! getSummary().contains("@@@"))) {
					editable.put("summary_" + getId(), Boolean.TRUE);
				} else {
					editable.put("summary_" + getId(), Boolean.FALSE);
					setSummary(parse_output_replace(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, getSummary()));
				}
				if ((! getAuthor().contains("###")) && (! getAuthor().contains("@@@"))) {
					editable.put("author_" + getId(), Boolean.TRUE);
				} else {
					editable.put("author_" + getId(), Boolean.FALSE);
					setAuthor(parse_output_replace(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, getAuthor()));
				}
				if ((! getKeywords().contains("###")) && (! getKeywords().contains("@@@"))) {
					editable.put("keywords_" + getId(), Boolean.TRUE);
				} else {
					editable.put("keywords_" + getId(), Boolean.FALSE);
					setKeywords(parse_output_replace(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, getKeywords()));
				}
				if ((! getDescription().contains("###")) && (! getDescription().contains("@@@"))) {
					editable.put("description_" + getId(), Boolean.TRUE);
				} else {
					editable.put("description_" + getId(), Boolean.FALSE);
					setDescription(parse_output_replace(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, getDescription()));
				}
				setMetaInfo(parse_output_replace(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, getMetaInfo()));
				setHtmlHeader(parse_output_replace(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, getHtmlHeader()));
				setHtmlBodyOnload(parse_output_replace(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, getHtmlBodyOnload()));
				if ((! getContent().contains("###")) && (! getContent().contains("@@@"))) {
					editable.put("content_" + getId(), Boolean.TRUE);
				} else {
					editable.put("content_" + getId(), Boolean.FALSE);
				}
				if (! body.equals("")) {
					body = parse_output_replace(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, body);
				} else {
					body = parse_output_replace(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, getContent());
				}
				body = adjust_links(session_version, body, db, config);
				setContent(adjust_links(session_version, getContent(), db, config));
				setHtmlAttributes(adjust_links(session_version, getHtmlAttributes(), db, config));
				parse_output_loopdetection.put("" + parse_output_id, "" + ((Common.intnumber("0" + parse_output_loopdetection.get("" + parse_output_id)) - 1)));
			} catch (Exception e) {
				System.out.println("ERROR:Page:parse_output:loopdetection:id=" + id + ":::" + parse_output_id);
				setBody("");
				throw new Exception();
			}
		} else {
			parse_output_loopdetection.put("" + parse_output_id, "" + 0);
			System.out.println("ERROR:Page:parse_output:loopdetection:id=" + id + ":::" + parse_output_id + ":::" + getId());
			setBody("");
			throw new Exception();
		}
	}



	public String parse_output_replace(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_administrator, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, String mycontent) throws Exception {
		return parse_output_replace(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mycontent, "0");
	}
	public String parse_output_replace(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_administrator, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, String mycontent) throws Exception {
		return parse_output_replace(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mycontent, "0");
	}
	public String parse_output_replace(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_administrator, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, String mycontent, String parse_output_id) throws Exception {
		return parse_output_replace(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mycontent, parse_output_id);
	}
	public String parse_output_replace(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_administrator, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, String mycontent, String parse_output_id) throws Exception {
		if (_DEBUG_) System.out.println("HardCore/Page.parse_output_replace:"+table+":"+column+":"+id+":::"+parse_output_id+":::"+mycontent.length());
		if (request.getAttribute("parse_output_loopdetection") == null) {
			request.setAttribute("parse_output_loopdetection", new HashMap());
		}
@SuppressWarnings("unchecked")
		HashMap<String,String> parse_output_loopdetection = (HashMap<String,String>)request.getAttribute("parse_output_loopdetection");
		if (_DEBUG_) System.out.println("HardCore/Page.parse_output_replace:loopdetection:begin:"+table+":"+column+":"+id+":::"+parse_output_id+":::"+parse_output_loopdetection.get("" + parse_output_id));
		if (parse_output_loopdetection.get("" + parse_output_id) == null) {
			parse_output_loopdetection.put("" + parse_output_id, "" + 0);
		}
		if (Common.intnumber("0" + parse_output_loopdetection.get("" + parse_output_id)) < 10) {
			try {
				parse_output_loopdetection.put("" + parse_output_id, "" + (Common.intnumber("0" + parse_output_loopdetection.get("" + parse_output_id)) + 1));
				mycontent = parse_output_replace_sub(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mycontent);
				parse_output_loopdetection.put("" + parse_output_id, "" + (Common.intnumber("0" + parse_output_loopdetection.get("" + parse_output_id)) - 1));
			} catch (Exception e) {
				System.out.println("ERROR:Page:parse_output_replace:loopdetection:id=" + id + ":::" + parse_output_id + ":::" + e);
				setBody("");
				throw new Exception();
			}
		} else {
			parse_output_loopdetection.put("" + parse_output_id, "" + 0);
			System.out.println("ERROR:Page:parse_output_replace:loopdetection:id=" + id + ":::" + parse_output_id);
			setBody("");
			throw new Exception();
		}
		if (_DEBUG_) System.out.println("HardCore/Page.parse_output_replace:loopdetection:end:"+table+":"+column+":"+id+":::"+parse_output_id+":::"+parse_output_loopdetection.get("" + parse_output_id));
		return mycontent;
	}



	public String parse_output_replace_sub(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_administrator, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, String mycontent) throws Exception {
		if (_DEBUG_) System.out.println("HardCore/Page.parse_output_replace:"+table+":"+column+":"+id+":"+mycontent.length()+":"+mycontent);
		if (mycontent.equals("")) return "";
		if (session_mode.equals("admin")) {
			// Browse & Edit mode "non-box" handling of attributes
			mycontent = parse_output_replace_browseedit(request, session, script_name, query_string, session_mode, session_administrator, mycontent);
		}
//		if (config.getTemp("content_parse_seq").equals("yes")) {
//		if (! config.getTemp("content_parse_seq").equals("no")) {
		if (true) {
			HashMap<String,String> output_cache = new HashMap<String,String>();
			HashMap<String,Content> content_cache = new HashMap<String,Content>();
			HashMap<String,Page> page_cache = new HashMap<String,Page>();
			HashMap<String,Databases> databases_cache = new HashMap<String,Databases>();
			HashMap<String,Data> data_cache = new HashMap<String,Data>();
			HashMap<String,Object> order_cache = new HashMap<String,Object>();
			mycontent = parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mycontent, output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, 0);
		}
//		if (! config.getTemp("content_parse_old").equals("no")) {
//		if (config.getTemp("content_parse_old").equals("yes")) {
		if (false) {
			for (int i=0; i<2; i++) {
				if ((mycontent.contains("@@@")) || (mycontent.contains("###"))) {
					mycontent = parse_output_replace_params(db, config, request, mycontent);
					mycontent = parse_output_replace_user(session, mycontent);
					mycontent = parse_output_condition(request, mycontent);
					mycontent = parse_output_replace_get(db, config, session, mycontent);
					mycontent = parse_output_replace_cookie_get(request, mycontent);
					mycontent = parse_output_replace_display_if(db, config, request, session, mycontent);
					mycontent = parse_output_replace_url(request, mycontent);
					if (mycontent.contains("@@@")) {
						mycontent = parse_output_replace_browseedit(request, session, script_name, query_string, session_mode, session_administrator, mycontent);
						mycontent = parse_output_replace_content(request, session, script_name, query_string, session_mode, session_administrator, mycontent);
						if (mycontent.contains("@@@version@@@")) mycontent = mycontent.replaceAll("@@@version@@@", getVersion().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						if (mycontent.contains("@@@device@@@")) mycontent = mycontent.replaceAll("@@@device@@@", getDevice().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						mycontent = parse_output_replace_filename(db, config, mycontent);
						if (mycontent.contains("@@@title@@@")) mycontent = mycontent.replaceAll("@@@title@@@", display("title", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						if (mycontent.contains("@@@author@@@")) mycontent = mycontent.replaceAll("@@@author@@@", display("author", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						if (mycontent.contains("@@@keywords@@@")) mycontent = mycontent.replaceAll("@@@keywords@@@", display("keywords", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						if (mycontent.contains("@@@description@@@")) mycontent = mycontent.replaceAll("@@@description@@@", display("description", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						mycontent = parse_output_replace_summary(request, session, script_name, query_string, session_mode, session_administrator, mycontent);
						mycontent = parse_output_replace_image(request, session, script_name, query_string, session_mode, session_administrator, mycontent);
						mycontent = parse_output_replace_file(request, session, script_name, query_string, session_mode, session_administrator, mycontent);
						mycontent = parse_output_replace_link(request, session, script_name, query_string, session_mode, session_administrator, mycontent);
						if (mycontent.contains("@@@created@@@")) mycontent = mycontent.replaceAll("@@@created@@@", display("created", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						if (mycontent.contains("@@@updated@@@")) mycontent = mycontent.replaceAll("@@@updated@@@", display("updated", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						if (mycontent.contains("@@@published@@@")) mycontent = mycontent.replaceAll("@@@published@@@", display("published", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						if (mycontent.contains("@@@created_by@@@")) mycontent = mycontent.replaceAll("@@@created_by@@@", display("created_by", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						if (mycontent.contains("@@@updated_by@@@")) mycontent = mycontent.replaceAll("@@@updated_by@@@", display("updated_by", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						if (mycontent.contains("@@@published_by@@@")) mycontent = mycontent.replaceAll("@@@published_by@@@", display("published_by", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						mycontent = parse_output_replace_product(mycontent);
					}
					mycontent = parse_output_replace_metainfo(mycontent);
					mycontent = parse_output_replace_productinfo(mycontent);
					if (mycontent.contains("@@@")) {
						mycontent = parse_output_replace_now(mycontent);
						mycontent = parse_output_replace_created_format(request, session, script_name, query_string, session_mode, session_administrator, mycontent);
						mycontent = parse_output_replace_updated_format(request, session, script_name, query_string, session_mode, session_administrator, mycontent);
						mycontent = parse_output_replace_published_format(request, session, script_name, query_string, session_mode, session_administrator, mycontent);
						mycontent = parse_output_replace_elements(db, config, request, session, script_name, query_string, session_mode, session_administrator, mycontent);
					}
					mycontent = parse_output_replace_params(db, config, request, mycontent);
					mycontent = parse_output_replace_user(session, mycontent);
					mycontent = parse_output_condition(request, mycontent);
					mycontent = parse_output_replace_id(mycontent);
					mycontent = parse_output_replace_config(db, config, mycontent);
					mycontent = parse_output_replace_params(db, config, request, mycontent);
					mycontent = parse_output_replace_user(session, mycontent);
					mycontent = parse_output_condition(request, mycontent);
					mycontent = parse_output_replace_include_order(server, db, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, mycontent);
					mycontent = parse_output_replace_include_database(server, db, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, mycontent);
					mycontent = parse_output_replace_include(server, db, id, table, column, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mycontent);
					mycontent = parse_output_replace_params(db, config, request, mycontent);
					mycontent = parse_output_replace_user(session, mycontent);
					mycontent = parse_output_condition(request, mycontent);
					mycontent = parse_output_replace_id(mycontent);
					mycontent = parse_output_replace_calc(db, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mycontent);
					mycontent = parse_output_replace_random(db, table, column, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mycontent);
					mycontent = parse_output_replace_page_relations(mycontent);
					if (mycontent.contains("@@@searchresults@@@")) mycontent = mycontent.replaceAll("@@@searchresults@@@", "@@@list:searchresults@@@");
					mycontent = parse_output_replace_list(server, db, id, table, column, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mycontent);
					mycontent = parse_output_replace_params(db, config, request, mycontent);
					mycontent = parse_output_replace_user(session, mycontent);
					mycontent = parse_output_condition(request, mycontent);
					mycontent = parse_output_replace_shopcart(server, db, id, table, column, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mycontent);
					mycontent = parse_output_replace_error(session, mycontent);
					mycontent = parse_output_replace_captcha(server, db, config, request, session, mycontent);
					mycontent = parse_output_replace_authorize(session, mycontent);
					if (mycontent.contains("@@@")) {
						mycontent = parse_output_replace_id(mycontent);
						if (mycontent.contains("@@@class@@@")) mycontent = mycontent.replaceAll("@@@class@@@", getContentClass().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						mycontent = parse_output_replace_webeditor(server, db, id, table, column, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mycontent);

						String[] myinfos = getMetaInfo().split("[\r\n]+");
						for (int j=0; j<myinfos.length; j++) {
							String myinfo = myinfos[j];
							Matcher myinfoMatches = Pattern.compile("<([^<>]+?)>([^<>]*?)</([^<>]+?)>").matcher(myinfo);
							if (myinfoMatches.find()) {
								String myname = "" + myinfoMatches.group(1);
								String myvalue = "" + myinfoMatches.group(2);
								mycontent = mycontent.replaceAll("\\Q@@@" + myname + "@@@\\E", myvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							}
						}
						myinfos = getProductInfo().split("[\r\n]+");
						for (int j=0; j<myinfos.length; j++) {
							String myinfo = myinfos[j];
							Matcher myinfoMatches = Pattern.compile("<([^<>]+?)>([^<>]*?)</([^<>]+?)>").matcher(myinfo);
							if (myinfoMatches.find()) {
								String myname = "" + myinfoMatches.group(1);
								String myvalue = "" + myinfoMatches.group(2);
								mycontent = mycontent.replaceAll("\\Q@@@" + myname + "@@@\\E", myvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							}
						}
					}
				}
			}
			if (mycontent.contains("###")) mycontent = mycontent.replaceAll("###[-_ a-zA-Z0-9%\\w]+###", "");
			mycontent = parse_output_replace_set(db, config, session, mycontent);
			mycontent = parse_output_replace_cookie_set(response, mycontent);
		}
		return mycontent;
	}



	public String parse_output_replace_sequential(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_administrator, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, String mycontent, HashMap<String,String> output_cache, HashMap<String,Content> content_cache, HashMap<String,Page> page_cache, HashMap<String,Databases> databases_cache, HashMap<String,Data> data_cache, HashMap<String,Object> order_cache, int loopdetection) throws Exception {
try {
		if ((mycontent == null) || (mycontent.length() == 0)) return "";
		
		//CODIGOS PARA BINDING DOS BEANS DE MODELO E MSG DE ERROS NA VIEW
		if((mycontent.contains("${") || (request.getRequest().getAttribute("modelILiketo") != null && mycontent.contains("@@@"))) && !request.getRequest().getRequestURI().contains("/webadmin/")){
			CmsConfigILiketo cmsIliketo = new CmsConfigILiketo(request.getRequest(), response.getResponse());
			String binding = cmsIliketo.parseBindingModelBean(mycontent);
			if(binding != null){	//verifica parse ok
				mycontent = binding;
			}
		}
		
		if ((! mycontent.contains("###")) && (! mycontent.contains("@@@"))) return mycontent;
		if (loopdetection>10) {
			System.out.println("HardCore/Page.parse_output_replace_sequential:ERROR:LOOP:"+table+":"+column+":"+id+":::"+loopdetection+":"+mycontent.length()+":"+mycontent);
			return "";
		}
		if (_DEBUG_) System.out.println("HardCore/Page.parse_output_replace_sequential:"+table+":"+column+":"+id+":::"+loopdetection+":"+mycontent.length()+":"+mycontent);
		boolean do_output = true;
		StringBuilder sb = new StringBuilder(mycontent);
		HashMap<String,String> mymetainfo = getMetaInfos();
		HashMap<String,String> myproductinfo = getProductInfos();
		HashMap<String,String> myelements = elements(db, config);

		for (int parseloop=1; parseloop<=5; parseloop++) {

			StringBuilder myinput = new StringBuilder(sb);
			sb = new StringBuilder("");
			if ((myinput.indexOf("###")<0) && (myinput.indexOf("@@@")<0)) {
				sb.append(myinput);
				break;
			}

			Pattern p = null;
			if (parseloop == 1) {
				// FIRST PARSE: ONLY MATCH SIMPLE SPECIAL CODES WITHOUT PARAMETERS TO BE PARSED BEFORE COMPLEX SPECIAL CODES WITH PARAMETERS WHICH MAY BE NESTED
				if (regexLoop1 == null) regexLoop1 = Pattern.compile("((###(([a-zA-Z0-9%\\w][-_ a-zA-Z0-9%\\w]*?)(\\.[a-zA-Z]+)?)###)|(@@@(([a-zA-Z0-9%\\w][- a-zA-Z0-9%\\w]*?)([_.]((?!@@@)[^.])+)?)@@@))", Pattern.DOTALL);
				p = regexLoop1;
			} else if (parseloop == 2) {
				// SECOND PARSE: ALSO MATCH SELECT COMPLEX SPECIAL CODES WITH PARAMETERS WHICH MAY BE NESTED TO BE PARSED AFTER SIMPLE SPECIAL CODES WITHOUT PARAMETERS
				if (regexLoop2 == null) regexLoop2 = Pattern.compile("((###(([a-zA-Z0-9%\\w][-_ a-zA-Z0-9%\\w]*?)(\\.[a-zA-Z]+)?)###)|(@@@(([a-zA-Z0-9%\\w][- a-zA-Z0-9%\\w]*?)([_.]((?!@@@)[^.])+)?)@@@)|(@@@((config|get|cookie|url|urlpathquery|urlpath|urlquery|now|created|updated|published|count|sum|min|max|avg|metainfo|productinfo)([:]((?!@@@).)+)?)@@@))", Pattern.DOTALL);
				p = regexLoop2;
			} else if (parseloop == 3) {
				// THIRD PARSE: ALSO MATCH SELECT COMPLEX SPECIAL CODES WITH PARAMETERS WHICH MAY BE NESTED TO BE PARSED AFTER SIMPLE SPECIAL CODES WITHOUT PARAMETERS
				if (regexLoop3 == null) regexLoop3 = Pattern.compile("((###(([a-zA-Z0-9%\\w][-_ a-zA-Z0-9%\\w]*?)(\\.[a-zA-Z]+)?)###)|(@@@(([a-zA-Z0-9%\\w][- a-zA-Z0-9%\\w]*?)([_.]((?!@@@)[^.])+)?)@@@)|(@@@((include|random)([:]((?!@@@).)+)?)@@@))", Pattern.DOTALL);
				p = regexLoop3;
			} else {
				// FOURTH+ PARSE: ALSO MATCH ALL COMPLEX SPECIAL CODES WITH PARAMETERS WHICH MAY BE NESTED TO BE PARSED AFTER SIMPLE SPECIAL CODES WITHOUT PARAMETERS
				if (regexLoop4 == null) regexLoop4 = Pattern.compile("((###(([a-zA-Z0-9%\\w][-_ a-zA-Z0-9%\\w]*?)(\\.[a-zA-Z]+)?)###)|(@@@(([a-zA-Z0-9%\\w][- a-zA-Z0-9%\\w]*?)([_.]((?!@@@)[^.])+)?)@@@)|(@@@(([a-zA-Z0-9%\\w][- a-zA-Z0-9%\\w]*?)([:]((?!@@@).)+)?)@@@))", Pattern.DOTALL);
				p = regexLoop4;
			}

			Matcher m = p.matcher(myinput);
			while (m.find()) {
				if (do_output) {
//					m.appendReplacement(sb, "");
					StringBuffer mytemp = new StringBuffer("");
					m.appendReplacement(mytemp, "");
					sb.append(mytemp);
				} else {
					StringBuffer myskip = new StringBuffer("");
					m.appendReplacement(myskip, "");
				}
				// m.group(0) - whole special code match
				// m.group(1) - whole special code match
				// m.group(2) - "###" special code whole match
				// m.group(3) - "###" special code whole match excluding leading/trailing "###"
				// m.group(4) - "###" special code name - fx "id", "database", "add"
				// m.group(5) - "###" special code parameters - fx ".html", ".script", ".text", ".br", ".p", ".div"
				// m.group(6) - "@@@" special code whole match
				// m.group(7) - "@@@" special code whole match excluding leading/trailing "@@@"
				// m.group(8) - "@@@" special code name/type - fx "title", "menu", "include", "list"
				// m.group(9) - "@@@" special code parameters - fx ".script", ".title", ":id=123"
				// m.group(11) - "@@@" special code whole match
				// m.group(12) - "@@@" special code whole match excluding leading/trailing "@@@"
				// m.group(13) - "@@@" special code name/type - fx "title", "menu", "include", "list"
				// m.group(14) - "@@@" special code parameters - fx ".script", ".title", ":id=123"
				boolean parameter = false;
				String myname = "";
				String mydelim = null;
				String myparams = null;
				String mycode = "" + m.group(1);
				String myoutput = null;
				if (m.group(2) != null) {
					// "###" special code
					parameter = true;
					myname = "" + m.group(4);
					if (m.group(5) != null) {
						mydelim = m.group(5).substring(0,1);
						myparams = m.group(5).substring(1);
					}
				} else if (m.group(6) != null) {
					// "@@@" special code
					parameter = false;
					myname = "" + m.group(8);
					if (m.group(9) != null) {
						mydelim = m.group(9).substring(0,1);
						myparams = m.group(9).substring(1);
					}
				} else if (m.group(11) != null) {
					// "@@@" special code
					parameter = false;
					myname = "" + m.group(13);
					if (m.group(14) != null) {
						mydelim = m.group(14).substring(0,1);
						myparams = m.group(14).substring(1);
					}
				}

				if (regexDisplay == null) regexDisplay = Pattern.compile("^@@@(title|content|summary|author|keywords|description|created|updated|published|created_by|updated_by|published_by|image1|image2|image3|file1|file2|file3|link1|link2|link3)@@@$");
				if (regexDisplayHtml == null) regexDisplayHtml = Pattern.compile("^@@@(content|summary)\\.html@@@$");
				if (regexDisplayScript == null) regexDisplayScript = Pattern.compile("^@@@(content|summary)\\.script@@@$");
				if (regexDisplayText == null) regexDisplayText = Pattern.compile("^@@@(content|summary)\\.text@@@$");
				if (regexUrl == null) regexUrl = Pattern.compile("^@@@(url|urlpathquery|urlpath|urlquery)@@@$");

				//MEUS CODIGOS ESPECIAIS ILIKETO
				//DADOS PARA REPLACE NA PAGINA: CARTAO, ANUNCIO ... ...
				if(mycode != null && !mycode.equals("")){
					if (mycode.contains("@@@user_card") || mycode.contains("@@@ads")) { //user_car, ads ...
						HttpServletRequest httpReq = request.getRequest();
						output_cache.put(mycode, parse_output_special_code_iliketo(httpReq, mycode));
					}
				}
				
				if (output_cache.get(mycode) != null) {
					// duplicate special code - output same content as previously

				// SIMPLE SPECIAL CODES WITHOUT PARAMETERS TO BE PARSED BEFORE COMPLEX SPECIAL CODES WITH PARAMETERS WHICH MAY BE NESTED
				} else if (parameter) {
					// "###" special code
					output_cache.put(mycode, parse_output_replace_params(db, config, request, mycode));
				} else if (mycode.equals("@@@id@@@")) {
					output_cache.put(mycode, parse_output_replace_id(mycode));
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (regexDisplay.matcher(mycode).matches()) {
					output_cache.put(mycode, display(myname, "", script_name, query_string, session_mode, session_administrator).replaceAll(mycode, ""));
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (regexDisplayHtml.matcher(mycode).matches()) {
					output_cache.put(mycode, display(myname, "", script_name, query_string, session_mode, session_administrator).replaceAll(mycode, "").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (regexDisplayScript.matcher(mycode).matches()) {
					output_cache.put(mycode, display(myname, "", script_name, query_string, session_mode, session_administrator).replaceAll(mycode, "").replaceAll("\r", "\\\\\\\\r").replaceAll("\n", "\\\\\\\\n").replaceAll("'", "\\\\\\\\'"));
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (regexDisplayText.matcher(mycode).matches()) {
					output_cache.put(mycode, display(myname, "", script_name, query_string, session_mode, session_administrator).replaceAll(mycode, "").replaceAll("<br>", "\r\n").replaceAll("<p>", "\r\n\r\n").replaceAll("</p>", ""));
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if ((myelements.get(myname) != null) && (mydelim.equals(".")) && (myparams != null)) {
					output_cache.put(mycode, display(myname, myparams, script_name, query_string, session_mode, session_administrator).replaceAll(mycode, ""));
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@class@@@")) {
					output_cache.put(mycode, getContentClass());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@contentclass@@@")) {
					output_cache.put(mycode, getContentClass());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@contentgroup@@@")) {
					output_cache.put(mycode, getContentGroup());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@contenttype@@@")) {
					output_cache.put(mycode, getContentType());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@version@@@")) {
					output_cache.put(mycode, getVersion());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@device@@@")) {
					output_cache.put(mycode, getDevice());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@page_top@@@")) {
					output_cache.put(mycode, getPageTop());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@page_up@@@")) {
					output_cache.put(mycode, getPageUp());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@page_first@@@")) {
					output_cache.put(mycode, getPageFirst());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@page_last@@@")) {
					output_cache.put(mycode, getPageLast());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@page_previous@@@")) {
					output_cache.put(mycode, getPagePrevious());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@page_next@@@")) {
					output_cache.put(mycode, getPageNext());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@weight@@@")) {
					output_cache.put(mycode, getProductWeight());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@volume@@@")) {
					output_cache.put(mycode, getProductVolume());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@width@@@")) {
					output_cache.put(mycode, getProductWidth());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@height@@@")) {
					output_cache.put(mycode, getProductHeight());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@depth@@@")) {
					output_cache.put(mycode, getProductDepth());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@brand@@@")) {
					output_cache.put(mycode, getProductBrand());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@colour@@@")) {
					output_cache.put(mycode, getProductColour());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@size@@@")) {
					output_cache.put(mycode, getProductSize());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@filename@@@")) {
					output_cache.put(mycode, getServerFilename());
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@fileaddress@@@")) {
					output_cache.put(mycode, parse_output_replace_filename(db, config, mycode));
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@searchresults@@@")) {
					output_cache.put(mycode, "@@@list:searchresults@@@");
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@shopcart@@@")) {
					output_cache.put(mycode, parse_output_replace_shopcart(server, db, id, table, column, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mycode));
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (myname.equals("captcha")) {
					output_cache.put(mycode, parse_output_replace_captcha(server, db, config, request, session, mycode));
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (myname.equals("error")) {
					output_cache.put(mycode, parse_output_replace_error(session, mycode));
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (myname.equals("authorize")) {
					output_cache.put(mycode, parse_output_replace_authorize(session, mycode));
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (myname.equals("user")) {
					output_cache.put(mycode, parse_output_replace_user(session, mycode));
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (myname.equals("segment")) {
					output_cache.put(mycode, parse_output_replace_segment(session, mycode));
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (myname.equals("usertest")) {
					output_cache.put(mycode, parse_output_replace_usertest(session, mycode));
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (myname.equals("webeditor")) {
					output_cache.put(mycode, parse_output_replace_webeditor(server, db, id, table, column, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mycode));
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (regexUrl.matcher(mycode).matches()) { // SIMPLE VARIANTS
					output_cache.put(mycode, parse_output_replace_url(request, mycode));
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if (mycode.equals("@@@now@@@")) { // SIMPLE VARIANTS
					output_cache.put(mycode, parse_output_replace_now(mycode));
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if ((mymetainfo.get(myname) != null) && (myparams == null)) {
					output_cache.put(mycode, ""+mymetainfo.get(myname));
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
				} else if ((myproductinfo.get(myname) != null) && (myparams == null)) {
					output_cache.put(mycode, ""+myproductinfo.get(myname));
					output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));

				} else if (parseloop >= 2) {

					if (regexCalc == null) regexCalc = Pattern.compile("^(count|sum|min|max|avg)$");

					// SEMI-COMPLEX SPECIAL CODES WITH PARAMETERS WHICH MAY BE NESTED TO BE PARSED AFTER SIMPLE SPECIAL CODES WITHOUT PARAMETERS
					if (false) {
					} else if (myname.equals("config")) {
						output_cache.put(mycode, parse_output_replace_config(db, config, mycode));
						output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
					} else if (myname.equals("get")) {
						output_cache.put(mycode, parse_output_replace_get(db, config, session, mycode));
						output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
					} else if ((myname.equals("cookie")) && (! myparams.contains("="))) {
						output_cache.put(mycode, parse_output_replace_cookie_get(request, mycode));
						output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
					} else if (regexUrl.matcher(mycode).matches()) { // COMPLEX VARIANTS
						output_cache.put(mycode, parse_output_replace_url(request, mycode));
						output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
					} else if (myname.equals("now")) { // COMPLEX VARIANTS
						output_cache.put(mycode, parse_output_replace_now(mycode));
						output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
					} else if (myname.equals("created")) { // COMPLEX VARIANTS
						output_cache.put(mycode, parse_output_replace_created_format(request, session, script_name, query_string, session_mode, session_administrator, mycode));
						output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
					} else if (myname.equals("updated")) { // COMPLEX VARIANTS
						output_cache.put(mycode, parse_output_replace_updated_format(request, session, script_name, query_string, session_mode, session_administrator, mycode));
						output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
					} else if (myname.equals("published")) { // COMPLEX VARIANTS
						output_cache.put(mycode, parse_output_replace_published_format(request, session, script_name, query_string, session_mode, session_administrator, mycode));
						output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
					} else if (regexCalc.matcher(myname).matches()) {
						output_cache.put(mycode, parse_output_replace_calc(db, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mycode));
						output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
					} else if (myname.equals("metainfo")) {
						output_cache.put(mycode, parse_output_replace_metainfo(mycode));
						output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
					} else if (myname.equals("productinfo")) {
						output_cache.put(mycode, parse_output_replace_productinfo(mycode));
						output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));


					} else if (parseloop >= 3) {

						// COMPLEX SPECIAL CODES WITH PARAMETERS WHICH MAY BE NESTED TO BE PARSED AFTER SIMPLE SPECIAL CODES WITHOUT PARAMETERS
						if (false) {
						} else if (mycode.startsWith("@@@include:order=")) {
							if (do_output) {
								output_cache.put(mycode, parse_output_replace_include_order(server, db, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, mycode, order_cache));
								output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
							}
						} else if (mycode.startsWith("@@@include:database=")) {
							if (do_output) {
								output_cache.put(mycode, parse_output_replace_include_database(server, db, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, mycode, databases_cache, data_cache));
								output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
							}
						} else if (myname.equals("include")) {
							if (do_output) {
								output_cache.put(mycode, parse_output_replace_include(server, db, id, table, column, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mycode, page_cache));
								output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
							}
						} else if (myname.equals("random")) {
							if (do_output) {
								output_cache.put(mycode, parse_output_replace_random(db, table, column, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mycode, content_cache));
								output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
							}

						} else if (parseloop >= 4) {

							if (regexIf == null) regexIf = Pattern.compile("^@@@condition:([^:@]+?):if:.*$");
							if (regexElseIf == null) regexElseIf = Pattern.compile("^@@@condition:([^:@]+?):elseif:.*$");
							if (regexElse == null) regexElse = Pattern.compile("^@@@condition:([^:@]+?):else@@@$");
							if (regexEndIf == null) regexEndIf = Pattern.compile("^@@@condition:([^:@]+?):endif@@@$");
							if (regexOrder == null) regexOrder = Pattern.compile("^@@@(items|status|revision|quantity|currencytitle|currency|subtotal|order_id|order_status|order_quantity|order_currencytitle|order_currency|order_subtotal|order_created|order_created_by|order_updated|order_updated_by|order_closed|order_closed_by|order_paid|order_created:format=([^@]*?)|order_updated:format=([^@]*?)|order_closed:format=([^@]*?)|order_paid:format=([^@]*?)|tax_description|tax_currency|tax_currencytitle|tax|tax:(.*?)|shipping_description|shipping_currency|shipping_currencytitle|shipping|shipping:(.*?)|discount_description|discount_currency|discount_currencytitle|discount|discount:(.*?)|discount_description|discount_currency|discount_currencytitle|discount|@@@discount:(.*?)@@@|total|card_type|card_number|card_issuedmonth|card_issuedyear|card_expirymonth|card_expiryyear|card_name|card_cvc|card_issue|card_postalcode|delivery_name|delivery_organisation|delivery_address|delivery_postalcode|delivery_city|delivery_state|delivery_country|delivery_phone|delivery_fax|delivery_email|delivery_website|invoice_name|invoice_organisation|invoice_address|invoice_postalcode|invoice_city|invoice_state|invoice_country|invoice_phone|invoice_fax|invoice_email|invoice_website)@@@$");

							// COMPLEX SPECIAL CODES WITH PARAMETERS WHICH MAY BE NESTED TO BE PARSED AFTER SIMPLE SPECIAL CODES WITHOUT PARAMETERS
							if (false) {
							} else if (myname.equals("display")) {
								parse_output_replace_display_if(db, config, request, session, mycode, output_cache, ""+do_output);
								if (output_cache.get("!"+mycode) != null) {
									do_output = output_cache.get("!"+mycode).toLowerCase().equals("true");
								}
								myoutput = "";
							} else if (myname.equals("end")) {
								if (output_cache.get("!"+mycode) != null) {
									do_output = output_cache.get("!"+mycode).toLowerCase().equals("true");
								}
								myoutput = "";
							} else if (myname.equals("condition")) {
								if (regexIf.matcher(mycode).matches()) {
									do_output = parse_output_condition_if(request, mycode, output_cache, do_output);
									myoutput = "";
								} else if (regexElseIf.matcher(mycode).matches()) {
									do_output = parse_output_condition_elseif(request, mycode, output_cache, do_output);
									myoutput = "";
								} else if (regexElse.matcher(mycode).matches()) {
									do_output = parse_output_condition_else(request, mycode, output_cache, do_output);
									myoutput = "";
								} else if (regexEndIf.matcher(mycode).matches()) {
									do_output = parse_output_condition_endif(request, mycode, output_cache, do_output);
									myoutput = "";
								}

							} else if (myname.equals("list")) {
								if (do_output) {
									output_cache.put(mycode, parse_output_replace_list(server, db, id, table, column, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mycode, output_cache, page_cache));
									output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
								}

							// "@@@list:" - must also handle @@@first:...@@@, @@@previous:...@@@, @@@paged:...@@@, @@@next:...@@@, @@@last:...@@@ - parse_output_replace_list
							} else if (myname.equals("first")) {
								if (regexFirst == null) regexFirst = Pattern.compile("@@@first:([a-zA-Z][a-zA-Z0-9\\w]*?):([^@]+@?[^@]+?)@@@", Pattern.CASE_INSENSITIVE);
								Matcher m2 = regexFirst.matcher(mycode);
								if (do_output && m2.find() && (output_cache.get("!@@@first:" + m2.group(1) + "@@@") != null)) {
									//valida paginacao em uri executadas pelo Spring
									if( CmsConfigILiketo.validaPaginationILiketoo(request.getRequest()) ){
										String result = CmsConfigILiketo.processaPaginationILiketoo(request.getRequest(), m2.group(1), parse_output_list_first_link(m2.group(1), m2.group(2), Common.intnumber(output_cache.get("!@@@first:" + m2.group(1) + "@@@")), request));
										output_cache.put(mycode, result);		//put link com paginacao configurado com uri Spring + parametros Asbru
										output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
									}else{
										output_cache.put(mycode, parse_output_list_first_link(m2.group(1), m2.group(2), Common.intnumber(output_cache.get("!@@@first:" + m2.group(1) + "@@@")), request));
										output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
									}
								}
							} else if (myname.equals("previous")) {
								if (regexPrevious == null) regexPrevious = Pattern.compile("@@@previous:([a-zA-Z][a-zA-Z0-9\\w]*?):([^@]+@?[^@]+?)@@@", Pattern.CASE_INSENSITIVE);
								Matcher m2 = regexPrevious.matcher(mycode);
								if (do_output && m2.find() && (output_cache.get("!@@@previous:" + m2.group(1) + "@@@") != null)) {
									//valida paginacao em uri executadas pelo Spring
									if( CmsConfigILiketo.validaPaginationILiketoo(request.getRequest()) ){
										String result = CmsConfigILiketo.processaPaginationILiketoo(request.getRequest(), m2.group(1), parse_output_list_previous_link(m2.group(1), m2.group(2), Common.intnumber(output_cache.get("!@@@previous:" + m2.group(1) + "@@@")), request));
										output_cache.put(mycode, result);		//put link com paginacao configurado com uri Spring + parametros Asbru
										output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
									}else{
										output_cache.put(mycode, parse_output_list_previous_link(m2.group(1), m2.group(2), Common.intnumber(output_cache.get("!@@@previous:" + m2.group(1) + "@@@")), request));
										output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
									}
								}
							} else if (myname.equals("next")) {
								if (regexNext == null) regexNext = Pattern.compile("@@@next:([a-zA-Z][a-zA-Z0-9\\w]*?):([^@]+@?[^@]+?)@@@", Pattern.CASE_INSENSITIVE);
								Matcher m2 = regexNext.matcher(mycode);
								if (do_output && m2.find() && (output_cache.get("!@@@next:" + m2.group(1) + "@@@") != null)) {
									//valida paginacao em uri executadas pelo Spring
									if( CmsConfigILiketo.validaPaginationILiketoo(request.getRequest()) ){
										String result = CmsConfigILiketo.processaPaginationILiketoo(request.getRequest(), m2.group(1), parse_output_list_next_link(m2.group(1), m2.group(2), Common.intnumber(output_cache.get("!@@@next:" + m2.group(1) + "@@@")), request));
										output_cache.put(mycode, result);		//put link com paginacao configurado com uri Spring + parametros Asbru
										output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
									}else{
										output_cache.put(mycode, parse_output_list_next_link(m2.group(1), m2.group(2), Common.intnumber(output_cache.get("!@@@next:" + m2.group(1) + "@@@")), request));
										output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
									}
								}
							} else if (myname.equals("last")) {
								if (regexLast == null) regexLast = Pattern.compile("@@@last:([a-zA-Z][a-zA-Z0-9\\w]*?):([^@]+@?[^@]+?)@@@", Pattern.CASE_INSENSITIVE);
								Matcher m2 = regexLast.matcher(mycode);
								if (do_output && m2.find() && (output_cache.get("!@@@last:" + m2.group(1) + "@@@") != null)) {
									//valida paginacao em uri executadas pelo Spring
									if( CmsConfigILiketo.validaPaginationILiketoo(request.getRequest()) ){
										String result = CmsConfigILiketo.processaPaginationILiketoo(request.getRequest(), m2.group(1), parse_output_list_last_link(m2.group(1), m2.group(2), Common.intnumber(output_cache.get("!@@@last:" + m2.group(1) + "@@@")), request));
										output_cache.put(mycode, result);		//put link com paginacao configurado com uri Spring + parametros Asbru
										output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
									}else{
										output_cache.put(mycode, parse_output_list_last_link(m2.group(1), m2.group(2), Common.intnumber(output_cache.get("!@@@last:" + m2.group(1) + "@@@")), request));
										output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
									}
								}
							} else if (myname.equals("paged")) {
								if (regexPaged == null) regexPaged = Pattern.compile("@@@paged:([a-zA-Z][a-zA-Z0-9\\w]*?):([^@]+@?[^@]+?)@@@", Pattern.CASE_INSENSITIVE);
								Matcher m2 = regexPaged.matcher(mycode);
								if (do_output && m2.find() && (output_cache.get("!@@@paged:" + m2.group(1) + "@@@") != null)) {
									String[] myattrs = output_cache.get("!@@@paged:" + m2.group(1) + "@@@").split(":::");
									if (myattrs.length>=2) {
										output_cache.put(mycode, parse_output_list_paged_links(m2.group(1), m2.group(2), Common.intnumber(myattrs[0]), Common.intnumber(myattrs[1]), request));
										output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
									}
								}

							// ORDER SPECIAL CODES MUST BE HANDLED AFTER @@@include:order=...@@@ SPECIAL CODES

							} else if (regexOrder.matcher(mycode).matches()) {
								if (((Order)order_cache.get("order") != null) && ((Page)order_cache.get("orderitem") != null)) {
									output_cache.put(mycode, parse_output_order((Order)order_cache.get("order"), mycode, db, config, (Page)order_cache.get("orderitem"), session_version, session_device, session_usersegments, session_usertests, server, session, request, response).replaceAll(mycode, ""));
									output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
								}

							} else if (parseloop >= 5) {

								if (false) {
								} else if (myname.equals("set")) {
									if (do_output) {
										output_cache.put(mycode, parse_output_replace_set(db, config, session, mycode));
										output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
									}
								} else if (myname.equals("cookie")) {
									if (do_output) {
										output_cache.put(mycode, parse_output_replace_cookie_set(response, mycode));
										output_cache.put(mycode, parse_output_replace_sequential(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, output_cache.get(mycode), output_cache, content_cache, page_cache, databases_cache, data_cache, order_cache, loopdetection+1));
									}

								} else if (myname.equals("extension")) {
									// ignore - handled by final output

								} else {
									// other special code - passthrough to old special code parser
									if (_DEBUG_) System.out.println("HardCore/Page.parse_output_replace_sequential:UNKNOWN:"+table+":"+column+":"+id+":"+mycode);
								}
							}
						}
					}
				}

				if (! do_output) {
					// skip - inside false conditional special code
				} else if (myoutput != null) {
					sb.append(myoutput);
				} else if (output_cache.get(mycode) != null) {
					sb.append(output_cache.get(mycode));
				} else {
					sb.append(mycode);
				}
			}
//			m.appendTail(sb);
			StringBuffer mytail = new StringBuffer("");
			m.appendTail(mytail);
			sb.append(mytail);
		}
		return sb.toString();
} catch (Exception e) {
	System.out.println("ERROR:HardCore/Page.parse_output_replace_sequential:"+getId()+":::"+e);
	setBody("");
	throw new Exception();
}
	}



	public String parse_output_replace_inputs(Request request, String mycontent) {
		return parse_output_replace_inputs(null, null, request, mycontent);
	}
	public String parse_output_replace_inputs(Fileupload request, String mycontent) {
		return parse_output_replace_inputs(null, null, request, mycontent);
	}
	public String parse_output_replace_inputs(Request request, Fileupload filepost, String mycontent) {
		mycontent = parse_output_replace_inputs(null, null, request, mycontent);
		mycontent = parse_output_replace_inputs(null, null, filepost, mycontent);
		return mycontent;
	}
	public String parse_output_replace_inputs(DB db, Configuration config, Request request, String mycontent) {
		if ((mycontent.contains("###")) && (request != null)) {
			Enumeration parameternames = request.getParameterNames();
			while (parameternames.hasMoreElements()) {
				String inputname = "" + parameternames.nextElement();
				String inputformat = "";
				String inputvalue = "";
				String rawinputvalue = "";
				if (request.parameterExists(inputname)) {
					rawinputvalue = "" + Common.join(",", request.getParameters(inputname));
					inputvalue = html.encodeHtmlEntities(rawinputvalue).replaceAll("'", "&#039;");
				} else if (request.parameterExists(URLDecoder.decode(inputname))) {
					rawinputvalue = "" + Common.join(",", request.getParameters(URLDecoder.decode(inputname)));
					inputvalue = html.encodeHtmlEntities(rawinputvalue).replaceAll("'", "&#039;");
				} else if (request.parameterExists(html.decode(inputname))) {
					rawinputvalue = "" + Common.join(",", request.getParameters(html.decode(inputname)));
					inputvalue = html.encodeHtmlEntities(rawinputvalue).replaceAll("'", "&#039;");
				} else if (request.parameterExists(inputname.replaceAll("%20"," "))) {
					rawinputvalue = "" + Common.join(",", request.getParameters(inputname.replaceAll("%20"," ")));
					inputvalue = html.encodeHtmlEntities(rawinputvalue).replaceAll("'", "&#039;");
				}
//				inputvalue = inputvalue.replaceAll("&", "&amp;");
				inputvalue = inputvalue.replaceAll("<", "&lt;");
				inputvalue = inputvalue.replaceAll(">", "&gt;");
				inputvalue = inputvalue.replaceAll("\r", "");
				if ((config != null) && (db != null) && (config.get(db, "doctype").contains("xhtml"))) {
					inputvalue = inputvalue.replaceAll("\n", "<br />");
				} else {
					inputvalue = inputvalue.replaceAll("\n", "<br>");
				}
				inputvalue = parse_output_replace_params_escape(inputvalue);
				rawinputvalue = parse_output_replace_params_escape(rawinputvalue);
				inputvalue = inputvalue.replaceAll("@@@", "<nobr>@@@</nobr>");
				inputvalue = inputvalue.replaceAll("###", "<nobr>###</nobr>");
				rawinputvalue = rawinputvalue.replaceAll("@@@", "<nobr>@@@</nobr>");
				rawinputvalue = rawinputvalue.replaceAll("###", "<nobr>###</nobr>");
				if (mycontent.contains("###" + inputname.replaceAll("###", "") + "###")) mycontent = mycontent.replaceAll("###" + inputname.replaceAll("###", "") + "###", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				if (mycontent.contains("###" + inputname.replaceAll("###", "") + ".html" + "###")) mycontent = mycontent.replaceAll("###" + inputname.replaceAll("###", "") + ".html" + "###", rawinputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("&", "&amp;").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
				if (mycontent.contains("###" + inputname.replaceAll("###", "") + ".script" + "###")) mycontent = mycontent.replaceAll("###" + inputname.replaceAll("###", "") + ".script" + "###", rawinputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r", "\\\\\\\\r").replaceAll("\n", "\\\\\\\\n").replaceAll("'", "\\\\\\\\'"));
				if (mycontent.contains("###" + inputname.replaceAll("###", "") + ".text" + "###")) mycontent = mycontent.replaceAll("###" + inputname.replaceAll("###", "") + ".text" + "###", rawinputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("<br>", "\r\n").replaceAll("<p>", "\r\n\r\n").replaceAll("</p>", ""));
				if (mycontent.contains("###" + inputname.replaceAll("###", "") + ".values" + "###")) mycontent = mycontent.replaceAll("###" + inputname.replaceAll("###", "") + ".values" + "###", rawinputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll(",", "|"));
				if ((config != null) && (db != null) && (config.get(db, "doctype").contains("xhtml"))) {
					if (mycontent.contains("###" + inputname.replaceAll("###", "") + ".br" + "###")) mycontent = mycontent.replaceAll("###" + inputname.replaceAll("###", "") + ".br" + "###", rawinputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n", "<br />"));
				} else {
					if (mycontent.contains("###" + inputname.replaceAll("###", "") + ".br" + "###")) mycontent = mycontent.replaceAll("###" + inputname.replaceAll("###", "") + ".br" + "###", rawinputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n", "<br>"));
				}
				if (mycontent.contains("###" + inputname.replaceAll("###", "") + ".p" + "###")) mycontent = mycontent.replaceAll("###" + inputname.replaceAll("###", "") + ".p" + "###", rawinputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n", "<p>"));
				if (mycontent.contains("###" + inputname.replaceAll("###", "") + ".div" + "###")) mycontent = mycontent.replaceAll("###" + inputname.replaceAll("###", "") + ".div" + "###", "<div>" + rawinputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n", "</div><div>") + "</div>");
				if (mycontent.contains("###" + inputname.replaceAll("###", "") + inputformat + "###")) mycontent = mycontent.replaceAll("###" + inputname.replaceAll("###", "") + inputformat + "###", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			}
		}
		return mycontent;
	}
	public String parse_output_replace_inputs(DB db, Configuration config, Fileupload request, String mycontent) {
		if ((mycontent.contains("###")) && (request != null)) {
			Iterator filepostparameternames = request.getParameterNames();
			while (filepostparameternames.hasNext()) {
				String inputname = "" + filepostparameternames.next();
				String inputformat = "";
				String inputvalue = "";
				String rawinputvalue = "";
				if (request.parameterExists(inputname)) {
					rawinputvalue = "" + Common.join(",", request.getParameters(inputname));
					inputvalue = html.encodeHtmlEntities(rawinputvalue).replaceAll("'", "&#039;");
				} else if (request.parameterExists(URLDecoder.decode(inputname))) {
					rawinputvalue = "" + Common.join(",", request.getParameters(URLDecoder.decode(inputname)));
					inputvalue = html.encodeHtmlEntities(rawinputvalue).replaceAll("'", "&#039;");
				} else if (request.parameterExists(html.decode(inputname))) {
					rawinputvalue = "" + Common.join(",", request.getParameters(html.decode(inputname)));
					inputvalue = html.encodeHtmlEntities(rawinputvalue).replaceAll("'", "&#039;");
				} else if (request.parameterExists(inputname.replaceAll("%20"," "))) {
					rawinputvalue = "" + Common.join(",", request.getParameters(inputname.replaceAll("%20"," ")));
					inputvalue = html.encodeHtmlEntities(rawinputvalue).replaceAll("'", "&#039;");
				}
//				inputvalue = inputvalue.replaceAll("&", "&amp;");
				inputvalue = inputvalue.replaceAll("<", "&lt;");
				inputvalue = inputvalue.replaceAll(">", "&gt;");
				inputvalue = inputvalue.replaceAll("\r", "");
				if ((config != null) && (db != null) && (config.get(db, "doctype").contains("xhtml"))) {
					inputvalue = inputvalue.replaceAll("\n", "<br />");
				} else {
					inputvalue = inputvalue.replaceAll("\n", "<br>");
				}
				inputvalue = parse_output_replace_params_escape(inputvalue);
				rawinputvalue = parse_output_replace_params_escape(rawinputvalue);
				inputvalue = inputvalue.replaceAll("@@@", "<nobr>@@@</nobr>");
				inputvalue = inputvalue.replaceAll("###", "<nobr>###</nobr>");
				rawinputvalue = rawinputvalue.replaceAll("@@@", "<nobr>@@@</nobr>");
				rawinputvalue = rawinputvalue.replaceAll("###", "<nobr>###</nobr>");
				if (mycontent.contains("###" + inputname.replaceAll("###", "") + "###")) mycontent = mycontent.replaceAll("###" + inputname.replaceAll("###", "") + "###", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				if (mycontent.contains("###" + inputname.replaceAll("###", "") + ".html" + "###")) mycontent = mycontent.replaceAll("###" + inputname.replaceAll("###", "") + ".html" + "###", rawinputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("&", "&amp;").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
				if (mycontent.contains("###" + inputname.replaceAll("###", "") + ".script" + "###")) mycontent = mycontent.replaceAll("###" + inputname.replaceAll("###", "") + ".script" + "###", rawinputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r", "\\\\\\\\r").replaceAll("\n", "\\\\\\\\n").replaceAll("'", "\\\\\\\\'"));
				if (mycontent.contains("###" + inputname.replaceAll("###", "") + ".text" + "###")) mycontent = mycontent.replaceAll("###" + inputname.replaceAll("###", "") + ".text" + "###", rawinputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("<br>", "\r\n").replaceAll("<p>", "\r\n\r\n").replaceAll("</p>", ""));
				if (mycontent.contains("###" + inputname.replaceAll("###", "") + ".values" + "###")) mycontent = mycontent.replaceAll("###" + inputname.replaceAll("###", "") + ".values" + "###", rawinputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll(",", "|"));
				if ((config != null) && (db != null) && (config.get(db, "doctype").contains("xhtml"))) {
					if (mycontent.contains("###" + inputname.replaceAll("###", "") + ".br" + "###")) mycontent = mycontent.replaceAll("###" + inputname.replaceAll("###", "") + ".br" + "###", rawinputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n", "<br />"));
				} else {
					if (mycontent.contains("###" + inputname.replaceAll("###", "") + ".br" + "###")) mycontent = mycontent.replaceAll("###" + inputname.replaceAll("###", "") + ".br" + "###", rawinputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n", "<br>"));
				}
				if (mycontent.contains("###" + inputname.replaceAll("###", "") + ".p" + "###")) mycontent = mycontent.replaceAll("###" + inputname.replaceAll("###", "") + ".p" + "###", rawinputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n", "<p>"));
				if (mycontent.contains("###" + inputname.replaceAll("###", "") + ".div" + "###")) mycontent = mycontent.replaceAll("###" + inputname.replaceAll("###", "") + ".div" + "###", "<div>" + rawinputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n", "</div><div>") + "</div>");
				if (mycontent.contains("###" + inputname.replaceAll("###", "") + inputformat + "###")) mycontent = mycontent.replaceAll("###" + inputname.replaceAll("###", "") + inputformat + "###", inputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			}
		}
		return mycontent;
	}



	public String parse_output_replace_params(Request request, String mycontent) {
		return parse_output_replace_params(null, null, request, mycontent);
	}
	public String parse_output_replace_params(DB db, Configuration config, Request request, String mycontent) {
		if (mycontent.contains("###")) {
			if (regexParams == null) regexParams = Pattern.compile("###([-_ a-zA-Z0-9%\\w]+?)(\\.[a-zA-Z]+)?###");
			Pattern p = regexParams;
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String elementname = "" + m.group(1);
				String elementformat = "" + m.group(2);
				String elementvalue = "";
				String rawelementvalue = "";
				if (request.parameterExists(elementname)) {
					rawelementvalue = "" + Common.join(",", request.getParameters(elementname));
					elementvalue = html.encodeHtmlEntities(rawelementvalue).replaceAll("'", "&#039;");
				} else if (request.parameterExists(URLDecoder.decode(elementname))) {
					rawelementvalue = "" + Common.join(",", request.getParameters(URLDecoder.decode(elementname)));
					elementvalue = html.encodeHtmlEntities(rawelementvalue).replaceAll("'", "&#039;");
				} else if (request.parameterExists(html.decode(elementname))) {
					rawelementvalue = "" + Common.join(",", request.getParameters(html.decode(elementname)));
					elementvalue = html.encodeHtmlEntities(rawelementvalue).replaceAll("'", "&#039;");
				} else if (request.parameterExists(elementname.replaceAll("%20"," "))) {
					rawelementvalue = "" + Common.join(",", request.getParameters(elementname.replaceAll("%20"," ")));
					elementvalue = html.encodeHtmlEntities(rawelementvalue).replaceAll("'", "&#039;");
				}
//				elementvalue = elementvalue.replaceAll("&", "&amp;");
				elementvalue = elementvalue.replaceAll("<", "&lt;");
				elementvalue = elementvalue.replaceAll(">", "&gt;");
				elementvalue = elementvalue.replaceAll("\r", "");
				if ((config != null) && (db != null) && (config.get(db, "doctype").contains("xhtml"))) {
					elementvalue = elementvalue.replaceAll("\n", "<br />");
				} else {
					elementvalue = elementvalue.replaceAll("\n", "<br>");
				}
				elementvalue = parse_output_replace_params_escape(elementvalue);
				rawelementvalue = parse_output_replace_params_escape(rawelementvalue);
				elementvalue = elementvalue.replaceAll("@@@", "<nobr>@@@</nobr>");
				elementvalue = elementvalue.replaceAll("###", "<nobr>###</nobr>");
				rawelementvalue = rawelementvalue.replaceAll("@@@", "<nobr>@@@</nobr>");
				rawelementvalue = rawelementvalue.replaceAll("###", "<nobr>###</nobr>");
				if (mycontent.contains("###" + elementname.replaceAll("###", "") + "###")) mycontent = mycontent.replaceAll("###" + elementname.replaceAll("###", "") + "###", elementvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				if (mycontent.contains("###" + elementname.replaceAll("###", "") + ".html" + "###")) mycontent = mycontent.replaceAll("###" + elementname.replaceAll("###", "") + ".html" + "###", rawelementvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("&", "&amp;").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
				if (mycontent.contains("###" + elementname.replaceAll("###", "") + ".script" + "###")) mycontent = mycontent.replaceAll("###" + elementname.replaceAll("###", "") + ".script" + "###", rawelementvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r", "\\\\\\\\r").replaceAll("\n", "\\\\\\\\n").replaceAll("'", "\\\\\\\\'"));
				if (mycontent.contains("###" + elementname.replaceAll("###", "") + ".text" + "###")) mycontent = mycontent.replaceAll("###" + elementname.replaceAll("###", "") + ".text" + "###", rawelementvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("<br>", "\r\n").replaceAll("<p>", "\r\n\r\n").replaceAll("</p>", ""));
				if (mycontent.contains("###" + elementname.replaceAll("###", "") + ".values" + "###")) mycontent = mycontent.replaceAll("###" + elementname.replaceAll("###", "") + ".values" + "###", rawelementvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll(",", "|"));
				if ((config != null) && (db != null) && (config.get(db, "doctype").contains("xhtml"))) {
					if (mycontent.contains("###" + elementname.replaceAll("###", "") + ".br" + "###")) mycontent = mycontent.replaceAll("###" + elementname.replaceAll("###", "") + ".br" + "###", rawelementvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n", "<br />"));
				} else {
					if (mycontent.contains("###" + elementname.replaceAll("###", "") + ".br" + "###")) mycontent = mycontent.replaceAll("###" + elementname.replaceAll("###", "") + ".br" + "###", rawelementvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n", "<br>"));
				}
				if (mycontent.contains("###" + elementname.replaceAll("###", "") + ".p" + "###")) mycontent = mycontent.replaceAll("###" + elementname.replaceAll("###", "") + ".p" + "###", rawelementvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n", "<p>"));
				if (mycontent.contains("###" + elementname.replaceAll("###", "") + ".div" + "###")) mycontent = mycontent.replaceAll("###" + elementname.replaceAll("###", "") + ".div" + "###", "<div>" + rawelementvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n", "</div><div>") + "</div>");
				if (mycontent.contains("###" + elementname.replaceAll("###", "") + elementformat + "###")) mycontent = mycontent.replaceAll("###" + elementname.replaceAll("###", "") + elementformat + "###", elementvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_segment(Session session, String mycontent) throws Exception {
		if (mycontent.contains("@@@segment:")) {
			Segment mysegments = new Segment(session.get("usersegments"));
			Pattern p = Pattern.compile("@@@segment:([^@]+?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String myname = "" + m.group(1);
				mycontent = mycontent.replaceAll("\\Q@@@segment:" + myname + "@@@\\E", "" + mysegments.getWeight(myname));
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_usertest(Session session, String mycontent) throws Exception {
		if (mycontent.contains("@@@usertest:")) {
			Usertest myusertests = new Usertest(session.get("usertests"));
			Pattern p = Pattern.compile("@@@usertest:([^@]+?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String myname = "" + m.group(1);
				mycontent = mycontent.replaceAll("\\Q@@@usertest:" + myname + "@@@\\E", myusertests.get(myname).replaceAll("(\\$|\\\\)", "\\\\$1"));
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_get(DB db, Configuration config, Session session, String mycontent) throws Exception {
		if (mycontent.contains("@@@get:")) {
			Pattern p = Pattern.compile("@@@get:([_a-zA-Z0-9]+?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String myname = "" + m.group(1);
				mycontent = mycontent.replaceAll("\\Q@@@get:" + myname + "@@@\\E", session.get(config.get(db, "getset") + myname).replaceAll("(\\$|\\\\)", "\\\\$1"));
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_set(DB db, Configuration config, Session session, String mycontent) throws Exception {
		if (mycontent.contains("@@@user_segments@@@")) mycontent = mycontent.replaceAll("@@@user_segments@@@", session.get("usersegments").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@user_tests@@@")) mycontent = mycontent.replaceAll("@@@user_tests@@@", session.get("usertests").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@user_teststags@@@")) mycontent = mycontent.replaceAll("@@@user_teststags@@@", session.get("userteststags").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));

		if (mycontent.contains("@@@set:")) {
			Pattern p = Pattern.compile("@@@set:([_a-zA-Z0-9]+?)=(.*?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String myname = "" + m.group(1);
				String myvalue = "" + m.group(2);
				session.set(config.get(db, "getset") + myname, myvalue);
				mycontent = mycontent.replaceAll("\\Q@@@set:" + myname + "=" + myvalue + "@@@\\E", "");
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_cookie_get(Request request, String mycontent) throws Exception {
		if (mycontent.contains("@@@cookie:")) {
			Pattern p = Pattern.compile("@@@cookie:([_a-zA-Z0-9]+?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String myname = "" + m.group(1);
				mycontent = mycontent.replaceAll("\\Q@@@cookie:" + myname + "@@@\\E", request.getCookie(myname));
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_cookie_set(Response response, String mycontent) throws Exception {
		if (mycontent.contains("@@@cookie:")) {
			Pattern p = Pattern.compile("@@@cookie:([_a-zA-Z0-9]+?)=(.*?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String myname = "" + m.group(1);
				String myvalue = "" + m.group(2);
				response.setCookie(myname, myvalue);
				mycontent = mycontent.replaceAll("\\Q@@@cookie:" + myname + "=" + myvalue + "@@@\\E", "");
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_display_if(DB db, Configuration config, Request request, Session session, String mycontent) throws Exception {
		return parse_output_replace_display_if(db, config, request, session, mycontent, null, null);
	}
	public String parse_output_replace_display_if(DB db, Configuration config, Request request, Session session, String mycontent, HashMap<String,String> output_cache, String endif_output) throws Exception {
		if (mycontent.contains("@@@display:if:")) {
			Pattern p = Pattern.compile("@@@display:if:(.+?)(=|!=)(.*?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String myname = "" + m.group(1);
				String myoperand = "" + m.group(2);
				String myvalue = "" + m.group(3);
				boolean mycondition = false;
				if ((myname.equals("license")) && (! myvalue.equals(""))) {
					mycondition = License.valid(db, config, "!" + myvalue);
				} else if ((myname.equals("license")) && (myvalue.equals(""))) {
					mycondition = (! License.valid(db, config, "personal"));
				} else if ((myname.equals("browser")) && (! myvalue.equals(""))) {
					mycondition = (request.getUserAgent().contains(myvalue));
				} else if ((myname.equals("device")) && (! myvalue.equals(""))) {
					mycondition = (request.getUserAgent().contains(myvalue));
				} else if ((myname.equals("segment")) && (! myvalue.equals(""))) {
					Segment mysegments = new Segment(session.get("usersegments"));
					Pattern p2 = Pattern.compile("^(.+?)(<|>|&lt;|&gt;)(.*?)$");
					Matcher m2 = p2.matcher(myvalue);
					if (m2.find()) {
						String myname1 = "" + m2.group(1);
						String myoperand2 = "" + m2.group(2);
						String myname2 = "" + m2.group(3);
						if ((myoperand2.equals("<")) || (myoperand2.equals("&lt;"))) {
							mycondition = (mysegments.getWeight(myname1) < mysegments.getWeight(myname2));
						} else {
							mycondition = (mysegments.getWeight(myname1) > mysegments.getWeight(myname2));
						}
					} else {
						mycondition = (mysegments.getWeight(myvalue) > 0);
					}
				} else if ((myname.equals("usertest")) && (! myvalue.equals(""))) {
					Usertest myusertests = new Usertest(session.get("usertests"));
					Pattern p2 = Pattern.compile("^(.+?)(=|!=)(.*?)$");
					Matcher m2 = p2.matcher(myvalue);
					if (m2.find()) {
						String myname2 = "" + m.group(1);
						String myoperand2 = "" + m.group(2);
						String myvalue2 = "" + m.group(3);
						mycondition = (myusertests.get(myname2).equals(myvalue2));
						if (myoperand2.equals("!=")) {
							mycondition = ! mycondition;
						}
					} else {
						mycondition = (! myusertests.get(myvalue).equals(""));
					}
				} else {
					mycondition = myname.equals(myvalue);
				}
				if (myoperand.equals("!=")) {
					mycondition = ! mycondition;
				}
				if (mycondition) {
					mycontent = Pattern.compile("\\Q@@@display:if:" + myname + myoperand + myvalue + "@@@\\E" + "(.*?)" + "\\Q@@@end:if:" + myname + myoperand + myvalue + "@@@\\E", Pattern.DOTALL).matcher(mycontent).replaceAll("$1");
					if (output_cache != null) output_cache.put("!@@@display:if:" + myname + myoperand + myvalue + "@@@",  "true");
				} else {
					mycontent = Pattern.compile("\\Q@@@display:if:" + myname + myoperand + myvalue + "@@@\\E" + "(.*?)" + "\\Q@@@end:if:" + myname + myoperand + myvalue + "@@@\\E", Pattern.DOTALL).matcher(mycontent).replaceAll("");
					if (output_cache != null) output_cache.put("!@@@display:if:" + myname + myoperand + myvalue + "@@@",  "false");
				}
				mycontent = mycontent.replaceAll("\\Q@@@display:if:" + myname + myoperand + myvalue + "@@@\\E", "");
				mycontent = mycontent.replaceAll("\\Q@@@end:if:" + myname + myoperand + myvalue + "@@@\\E", "");
				if (output_cache != null) output_cache.put("!@@@end:if:" + myname + myoperand + myvalue + "@@@",  endif_output);
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_url(Request request, String mycontent) throws Exception {
		if (mycontent.contains("@@@url")) {
			String urlpath = "" + html.encode(Common.crlf_clean(request.getRequestURI())).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$");
			String urlquery = "" + html.encode(Common.crlf_clean(request.getQueryString())).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$");
			String urlpathquery = "" + urlpath;
			if (! urlquery.equals("")) {
				urlpathquery += "?" + urlquery;
			}
			String url = "" + request.getProtocol() + request.getServerName() + request.getServerPort() + urlpathquery;
			mycontent = mycontent.replaceAll("@@@url@@@", url);
			if (! urlquery.equals("")) {
					mycontent = mycontent.replaceAll("@@@url:([^@]*)@@@", url + "&$1");
			} else {
					mycontent = mycontent.replaceAll("@@@url:([^@]*)@@@", url + "?$1");
			}
			mycontent = mycontent.replaceAll("@@@urlpathquery@@@", urlpathquery);
			if (! urlquery.equals("")) {
					mycontent = mycontent.replaceAll("@@@urlpathquery:([^@]*)@@@", urlpathquery + "&$1");
			} else {
					mycontent = mycontent.replaceAll("@@@urlpathquery:([^@]*)@@@", urlpathquery + "?$1");
			}
			mycontent = mycontent.replaceAll("@@@urlpath@@@", urlpath);
			mycontent = mycontent.replaceAll("@@@urlpath:([^@]*)@@@", urlpath + "?$1");
			mycontent = mycontent.replaceAll("@@@urlquery@@@", urlquery);
			if (! urlquery.equals("")) {
					mycontent = mycontent.replaceAll("@@@urlquery:([^@]*)@@@", urlquery + "&$1");
			} else {
					mycontent = mycontent.replaceAll("@@@urlquery:([^@]*)@@@", urlquery + "$1");
			}
		}
		return mycontent;
	}



	public String parse_output_replace_browseedit(Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String mycontent) throws Exception {
 		if (session_mode.equals("admin")) {
			// Do not "box" html tag attribute special codes in browse & edit mode
			if (mycontent.contains("@@@content@@@")) mycontent = mycontent.replaceAll("<([^>]*?)@@@content@@@([^<]*?)>", "<$1" + display("content", "", script_name, query_string, "", session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "$2>");
			if (mycontent.contains("@@@content.html@@@")) mycontent = mycontent.replaceAll("<([^>]*?)@@@content.html@@@([^<]*?)>", "<$1" + display("content", "", script_name, query_string, "", session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;") + "$2>");
			if (mycontent.contains("@@@content.script@@@")) mycontent = mycontent.replaceAll("<([^>]*?)@@@content.script@@@([^<]*?)>", "<$1" + display("content", "", script_name, query_string, "", session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r", "\\\\\\\\r").replaceAll("\n", "\\\\\\\\n").replaceAll("'", "\\\\\\\\'") + "$2>");
			if (mycontent.contains("@@@content.text@@@")) mycontent = mycontent.replaceAll("<([^>]*?)@@@content.text@@@([^<]*?)>", "<$1" + display("content", "", script_name, query_string, "", session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("<br>", "\r\n").replaceAll("<p>", "\r\n\r\n").replaceAll("</p>", "") + "$2>");
			if (mycontent.contains("@@@title@@@")) mycontent = mycontent.replaceAll("<([^>]*?)@@@title@@@([^<]*?)>", "<$1" + display("title", "", script_name, query_string, "", session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "$2>");
			if (mycontent.contains("@@@author@@@")) mycontent = mycontent.replaceAll("<([^>]*?)@@@author@@@([^<]*?)>", "<$1" + display("author", "", script_name, query_string, "", session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "$2>");
			if (mycontent.contains("@@@keywords@@@")) mycontent = mycontent.replaceAll("<([^>]*?)@@@keywords@@@([^<]*?)>", "<$1" + display("keywords", "", script_name, query_string, "", session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "$2>");
			if (mycontent.contains("@@@description@@@")) mycontent = mycontent.replaceAll("<([^>]*?)@@@description@@@([^<]*?)>", "<$1" + display("description", "", script_name, query_string, "", session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "$2>");
			if (mycontent.contains("@@@summary@@@")) mycontent = mycontent.replaceAll("<([^>]*?)@@@summary@@@([^<]*?)>", "<$1" + display("summary", "", script_name, query_string, "", session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "$2>");
			if (mycontent.contains("@@@summary.html@@@")) mycontent = mycontent.replaceAll("<([^>]*?)@@@summary.html@@@([^<]*?)>", "<$1" + display("summary", "", script_name, query_string, "", session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;") + "$2>");
			if (mycontent.contains("@@@summary.script@@@")) mycontent = mycontent.replaceAll("<([^>]*?)@@@summary.script@@@([^<]*?)>", "<$1" + display("summary", "", script_name, query_string, "", session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r", "\\\\\\\\r").replaceAll("\n", "\\\\\\\\n").replaceAll("'", "\\\\\\\\'") + "$2>");
			if (mycontent.contains("@@@summary.script@@@")) mycontent = mycontent.replaceAll("<([^>]*?)@@@summary.text@@@([^<]*?)>", "<$1" + display("summary", "", script_name, query_string, "", session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("<br>", "\r\n").replaceAll("<p>", "\r\n\r\n").replaceAll("</p>", "") + "$2>");
		}
		return mycontent;
	}



	public String parse_output_replace_content(Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String mycontent) throws Exception {
		if (mycontent.contains("@@@content")) {
			if (mycontent.contains("@@@content@@@")) mycontent = mycontent.replaceAll("@@@content@@@", display("content", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@content.html@@@")) mycontent = mycontent.replaceAll("@@@content.html@@@", display("content", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
			if (mycontent.contains("@@@content.script@@@")) mycontent = mycontent.replaceAll("@@@content.script@@@", display("content", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r", "\\\\\\\\r").replaceAll("\n", "\\\\\\\\n").replaceAll("'", "\\\\\\\\'"));
			if (mycontent.contains("@@@content.text@@@")) mycontent = mycontent.replaceAll("@@@content.text@@@", display("content", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("<br>", "\r\n").replaceAll("<p>", "\r\n\r\n").replaceAll("</p>", ""));
			if (mycontent.contains("@@@contentclass@@@")) mycontent = mycontent.replaceAll("@@@contentclass@@@", getContentClass().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@contentgroup@@@")) mycontent = mycontent.replaceAll("@@@contentgroup@@@", getContentGroup().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@contenttype@@@")) mycontent = mycontent.replaceAll("@@@contenttype@@@", getContentType().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		}
		return mycontent;
	}



	public String parse_output_replace_filename(DB db, Configuration config, String mycontent) throws Exception {
		if (mycontent.contains("@@@file")) {
			if (mycontent.contains("@@@filename@@@")) mycontent = mycontent.replaceAll("@@@filename@@@", getServerFilename().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (! getServerFilename().equals("")) {
				if (mycontent.contains("@@@fileaddress@@@")) mycontent = mycontent.replaceAll("@@@fileaddress@@@", config.get(db, "URLrootpath") + getServerFilename().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			} else {
				if (mycontent.contains("@@@fileaddress@@@")) mycontent = mycontent.replaceAll("@@@fileaddress@@@", "/page.jsp?id=@@@id@@@");
			}
		}
		return mycontent;
	}



	public String parse_output_replace_summary(Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String mycontent) throws Exception {
		if (mycontent.contains("@@@summary")) {
			if (mycontent.contains("@@@summary@@@")) mycontent = mycontent.replaceAll("@@@summary@@@", display("summary", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@summary.html@@@")) mycontent = mycontent.replaceAll("@@@summary.html@@@", display("summary", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
			if (mycontent.contains("@@@summary.script@@@")) mycontent = mycontent.replaceAll("@@@summary.script@@@", display("summary", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r", "\\\\\\\\r").replaceAll("\n", "\\\\\\\\n").replaceAll("'", "\\\\\\\\'"));
			if (mycontent.contains("@@@summary.text@@@")) mycontent = mycontent.replaceAll("@@@summary.text@@@", display("summary", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("<br>", "\r\n").replaceAll("<p>", "\r\n\r\n").replaceAll("</p>", ""));
		}
		return mycontent;
	}



	public String parse_output_replace_image(Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String mycontent) throws Exception {
		if (mycontent.contains("@@@image")) {
			if (mycontent.contains("@@@image1@@@")) mycontent = mycontent.replaceAll("@@@image1@@@", display("image1", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@image2@@@")) mycontent = mycontent.replaceAll("@@@image2@@@", display("image2", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@image3@@@")) mycontent = mycontent.replaceAll("@@@image3@@@", display("image3", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		}
		return mycontent;
	}



	public String parse_output_replace_file(Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String mycontent) throws Exception {
		if (mycontent.contains("@@@file")) {
			if (mycontent.contains("@@@file1@@@")) mycontent = mycontent.replaceAll("@@@file1@@@", display("file1", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@file2@@@")) mycontent = mycontent.replaceAll("@@@file2@@@", display("file2", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@file3@@@")) mycontent = mycontent.replaceAll("@@@file3@@@", display("file3", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		}
		return mycontent;
	}



	public String parse_output_replace_link(Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String mycontent) throws Exception {
		if (mycontent.contains("@@@link")) {
			if (mycontent.contains("@@@link1@@@")) mycontent = mycontent.replaceAll("@@@link1@@@", display("link1", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@link2@@@")) mycontent = mycontent.replaceAll("@@@link2@@@", display("link2", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@link3@@@")) mycontent = mycontent.replaceAll("@@@link3@@@", display("link3", "", script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		}
		return mycontent;
	}



	public String parse_output_replace_product(String mycontent) throws Exception {
		if (getContentClass().equals("product")) {
			if (mycontent.contains("@@@weight@@@")) mycontent = mycontent.replaceAll("@@@weight@@@", getProductWeight().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@volume@@@")) mycontent = mycontent.replaceAll("@@@volume@@@", getProductVolume().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@width@@@")) mycontent = mycontent.replaceAll("@@@width@@@", getProductWidth().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@height@@@")) mycontent = mycontent.replaceAll("@@@height@@@", getProductHeight().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@depth@@@")) mycontent = mycontent.replaceAll("@@@depth@@@", getProductDepth().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@brand@@@")) mycontent = mycontent.replaceAll("@@@brand@@@", getProductBrand().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@colour@@@")) mycontent = mycontent.replaceAll("@@@colour@@@", getProductColour().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@size@@@")) mycontent = mycontent.replaceAll("@@@size@@@", getProductSize().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
//			setDisplayCurrency(db, session.get("currency"), config.get(db, "default_currency"));
//			mycontent = parse_output_product(server, session, request, response, db, config, mycontent);
		}
		return mycontent;
	}



	public String parse_output_replace_metainfo(String mycontent) throws Exception {
		if (mycontent.contains("@@@metainfo_")) {
			Pattern p = Pattern.compile("@@@metainfo_(.+?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String myname = "" + m.group(1);
				String myvalue = getMetaInfo(myname);
				mycontent = mycontent.replaceAll("\\Q@@@metainfo_" + myname + "@@@\\E", html.encode(myvalue).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_productinfo(String mycontent) throws Exception {
		if (mycontent.contains("@@@productinfo_")) {
			Pattern p = Pattern.compile("@@@productinfo_(.+?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String myname = "" + m.group(1);
				String myvalue = getProductInfo(myname);
				mycontent = mycontent.replaceAll("\\Q@@@productinfo_" + myname + "@@@\\E", html.encode(myvalue).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_now(String mycontent) throws Exception {
		Date now = new Date();
		if (mycontent.contains("@@@now@@@")) {
			String dateformat = "%Y-%m-%d %H:%M:%S";
			mycontent = mycontent.replaceAll("@@@now@@@", Common.strftime(dateformat, now).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		}
		if (mycontent.contains("@@@now:format=")) {
			Pattern p = Pattern.compile("@@@now:format=([^@]*?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String dateformat = "" + m.group(1);
				mycontent = mycontent.replaceAll("\\Q@@@now:format=" + dateformat + "@@@\\E", Common.strftime(dateformat, now).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_created_format(Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String mycontent) throws Exception {
		if (mycontent.contains("@@@created:format=")) {
			Pattern p = Pattern.compile("@@@created:format=([^@]*?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String dateformat = "" + m.group(1);
				Date date = new Date();
				date = Common.strtodate(display("created", "", script_name, query_string, session_mode, session_administrator));
				mycontent = mycontent.replaceAll("\\Q@@@created:format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_updated_format(Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String mycontent) throws Exception {
		if (mycontent.contains("@@@updated:format=")) {
			Pattern p = Pattern.compile("@@@updated:format=([^@]*?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String dateformat = "" + m.group(1);
				Date date = new Date();
				date = Common.strtodate(display("updated", "", script_name, query_string, session_mode, session_administrator));
				mycontent = mycontent.replaceAll("\\Q@@@updated:format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_published_format(Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String mycontent) throws Exception {
		if (mycontent.contains("@@@published:format=")) {
			Pattern p = Pattern.compile("@@@published:format=([^@]*?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String dateformat = "" + m.group(1);
				Date date = new Date();
				date = Common.strtodate(display("published", "", script_name, query_string, session_mode, session_administrator));
				mycontent = mycontent.replaceAll("\\Q@@@published:format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_elements(DB db, Configuration config, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String mycontent) throws Exception {
		Iterator contentelements = elements(db, config).keySet().iterator();
		while (contentelements.hasNext()) {
			String elementname = "" + contentelements.next();
			Pattern p = Pattern.compile("@@@(\\Q" + elementname + "\\E)\\.([^\\.@\\? ]+?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String myelementname = "" + m.group(1);
				String elementattribute = "" + m.group(2);
				String elementvalue = "" + getElement().get(myelementname);
				mycontent = mycontent.replaceAll("\\Q@@@" + myelementname + "." + elementattribute + "@@@\\E", display(myelementname, elementattribute, script_name, query_string, session_mode, session_administrator).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_id(String mycontent) throws Exception {
		if (mycontent.contains("@@@id@@@")) {
			if ((! getVersionMaster().equals("")) && (! getVersionMaster().equals("0"))) {
				mycontent = mycontent.replaceAll("@@@id@@@", getVersionMaster().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			} else {
				mycontent = mycontent.replaceAll("@@@id@@@", getId().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			}
		}
		return mycontent;
	}



	public String parse_output_replace_config(DB db, Configuration config, String mycontent) throws Exception {
		if (mycontent.contains("@@@config:")) {
			Pattern p = Pattern.compile("@@@config:([^@]*?):([^@]*?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find() && (! ("" + m.group(1)).contains("@@@"))) {
				String config_heading = "" + m.group(1);
				String config_name = "" + m.group(2);
				mycontent = mycontent.replaceAll("\\Q@@@config:" + config_heading + ":" + config_name + "@@@\\E", config.get(db, "config_" + config_heading + "_" + config_name).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_include_order(ServletContext server, DB db, Configuration config, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String mycontent) throws Exception {
		return parse_output_replace_include_order(server, db, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, mycontent, null);
	}
	public String parse_output_replace_include_order(ServletContext server, DB db, Configuration config, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String mycontent, HashMap<String,Object> order_cache) throws Exception {
		if (mycontent.contains("@@@include:order=")) {
			Pattern p = Pattern.compile("@@@include:order=([0-9]+?):orderitem=([0-9]+?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String orderid = "" + m.group(1);
				String orderitemsentryid = "" + m.group(2);
				Order order = new Order();
				order.read(db, orderid);
				if (((order.getUserId().equals(session_userid)) && (! session_userid.equals(""))) || ((order.getCreatedBy().equals(session_username)) && (! session_username.equals(""))) || ((session_username.equals(config.get(db, "superadmin"))) && (! session_username.equals(""))) || (RequireUser.isOrderAdministrator(session, config, db))) {
					Page orderitemsentry = new Page(text);
					boolean preview = false;
					if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
						orderitemsentry.read_primary_only(db, config, orderitemsentryid, "content", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
						orderitemsentry.previewScheduledQueued(session, config, db);
						preview = orderitemsentry.previewScheduled(session);
					}
					if (! preview) {
						orderitemsentry.read_primary_only(db, config, orderitemsentryid, "content_public", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					}
					if (order_cache != null) {
						order_cache.put("order", order);

						order_cache.put("orderitem", orderitemsentry);
					}
					mycontent = parse_output_order(order, mycontent, db, config, orderitemsentry, session_version, session_device, session_usersegments, session_usertests, server, session, request, response);
				}
				mycontent = mycontent.replaceAll("\\Q@@@include:order=" + orderid + ":orderitem=" + orderitemsentryid + "@@@\\E", "");
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_include_database(ServletContext server, DB db, Configuration config, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String mycontent) throws Exception {
		return parse_output_replace_include_database(server, db, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, mycontent, null, null);
	}
	public String parse_output_replace_include_database(ServletContext server, DB db, Configuration config, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String mycontent, HashMap<String,Databases> databases_cache, HashMap<String,Data> data_cache) throws Exception {
		if (mycontent.contains("@@@include:database=")) {
			Pattern p = Pattern.compile("@@@include:(database=[^\\.@\\?]+?:[^\\.@\\?]+?=((?!@@@).(?!@@@))*?:[^@\\?]+?):if:([^\\\\?!]*?)(=|!=)([^\\\\?]*?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find() && (! ("" + m.group(1)).contains("@@@"))) {
				String include_code = "" + m.group(1);
				String expression_value1 = "" + m.group(3);
				String expression_operand = "" + m.group(4);
				String expression_value2 = "" + m.group(5);
				if ((expression_operand.equals("=")) && (expression_value1.equals(expression_value2))) {
					mycontent = mycontent.replaceAll("\\Q@@@include:" + include_code + ":if:" + expression_value1 + expression_operand + expression_value2 + "@@@\\E", ("@@@include:" + include_code + "@@@").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				} else if ((expression_operand.equals("!=")) && (! expression_value1.equals(expression_value2))) {
					mycontent = mycontent.replaceAll("\\Q@@@include:" + include_code + ":if:" + expression_value1 + expression_operand + expression_value2 + "@@@\\E", ("@@@include:" + include_code + "@@@").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				} else {
					mycontent = mycontent.replaceAll("\\Q@@@include:" + include_code + ":if:" + expression_value1 + expression_operand + expression_value2 + "@@@\\E", "");
				}
				m.reset(mycontent);
			}
			p = Pattern.compile("@@@include:database=([^\\.@\\?]+?):([^\\.@\\?]+?)=(((?!@@@).(?!@@@))*?):([^@\\?]+?)@@@");
			m = p.matcher(mycontent);
			while (m.find()) {
				boolean processed = false;
				String include_databasename = "" + m.group(1);
				String include_idname = "" + m.group(2);
				String include_idvalue = "" + m.group(3);
				String my_include_databasename = include_databasename.replaceAll("%20", " ");
				Databases include_database = null;
				if (databases_cache != null) include_database = databases_cache.get(include_databasename);
				if (include_database == null) {
					include_database = new Databases(text);
					include_database.readTitle(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, my_include_databasename);
					if (databases_cache != null) databases_cache.put(include_databasename, include_database);
				}
				Data include_data = null;
				if (data_cache != null) include_data = data_cache.get(include_databasename+":::"+include_idname+":::"+include_idvalue);
				if (include_data == null) {
					include_data = new Data();
					if (include_database.getUser()) {
						if (include_idname.equals("id")) {
							include_data.read(db, "data" + include_database.getId(), include_idvalue);
						} else {
							HashMap include_column = parse_output_database_column(include_database, include_idname);
							if ((include_column != null) && (include_column.get("id") != null) && (! include_column.get("id").equals(""))) {
								include_data.readWhere(db, "data" + include_database.getId(), "col" + include_column.get("id"), include_idvalue);
							}
						}
						include_data.getAccessRestrictions(include_database, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config);
						if (! include_data.getUser()) {
							include_data.init();
						}
					}
					include_data.adjustContent(include_database.columns);
					if (data_cache != null) data_cache.put(include_databasename+":::"+include_idname+":::"+include_idvalue, include_data);
				}
				Pattern p2 = Pattern.compile("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + "\\E:([^:\\.@\\?]+?)[\\.:]([^:\\.@\\?]+?)@@@");
				Matcher m2 = p2.matcher(mycontent);
				while (m2.find()) {
					processed = true;
					String include_attribute = "" + m2.group(1);
					String include_format = "" + m2.group(2);
					HashMap include_attribute_column = parse_output_database_column(include_database, include_attribute);
					Page dummypage = new Page(text);
					String include_content = "";
					if ((include_attribute_column != null) && (include_attribute_column.get("id") != null) && (! include_attribute_column.get("id").equals(""))) {
						include_content = include_data.getContent("col" + include_attribute_column.get("id"));
					}
					String include_input_value = "" + include_content;
					if (request.parameterExists(include_attribute)) {
						include_input_value = request.getParameter(include_attribute);
					} else if (request.parameterExists(include_attribute.toLowerCase())) {
						include_input_value = request.getParameter(include_attribute.toLowerCase());
					} else if (request.parameterExists(include_attribute.replaceAll(" ", "_"))) {
						include_input_value = request.getParameter(include_attribute.replaceAll(" ", "_"));
					} else if (request.parameterExists(include_attribute.toLowerCase().replaceAll(" ", "_"))) {
						include_input_value = request.getParameter(include_attribute.toLowerCase().replaceAll(" ", "_"));
					}
					String myinput = "";
					if (include_attribute_column != null) {
						myinput = "<select name=\"" + include_attribute_column.get("name") + "\" multiple size=\"" + include_attribute_column.get("param1") + "\">" + include_data.select_options((""+include_attribute_column.get("options")), include_input_value) + "</select>";
					}
					mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + ".selectmulti@@@\\E", myinput.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					if ((include_attribute_column != null) && ((""+include_attribute_column.get("type")).equals("selectmulti"))) {
						mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + ".select@@@\\E", myinput.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					}
					if (include_attribute_column != null) {
						myinput = "<select name=\"" + include_attribute_column.get("name") + "\">" + include_data.select_options((""+include_attribute_column.get("options")), include_input_value) + "</select>";
					}
					mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + ".select@@@\\E", myinput.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					if (include_attribute_column != null) {
						myinput = include_data.radio_options(""+include_attribute_column.get("name"), (""+include_attribute_column.get("options")), include_input_value);
					}
					mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + ".radio@@@\\E", myinput.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					if (include_attribute_column != null) {
						myinput = include_data.checkbox_options(""+include_attribute_column.get("name"), (""+include_attribute_column.get("options")), include_input_value);
					}
					mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + ".checkbox@@@\\E", myinput.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					if ((include_attribute_column != null) && (((""+include_attribute_column.get("type")).equals("selectmulti")) || ((""+include_attribute_column.get("type")).equals("radio")) || ((""+include_attribute_column.get("type")).equals("checkbox")) || ((""+include_attribute_column.get("type")).equals("contentclasses")) || ((""+include_attribute_column.get("type")).equals("contentgroups")) || ((""+include_attribute_column.get("type")).equals("contenttypes")) || ((""+include_attribute_column.get("type")).equals("pagegroups")) || ((""+include_attribute_column.get("type")).equals("pagetypes")) || ((""+include_attribute_column.get("type")).equals("imagegroups")) || ((""+include_attribute_column.get("type")).equals("imagetypes")) || ((""+include_attribute_column.get("type")).equals("imageformats")) || ((""+include_attribute_column.get("type")).equals("filegroups")) || ((""+include_attribute_column.get("type")).equals("filetypes")) || ((""+include_attribute_column.get("type")).equals("fileformats")) || ((""+include_attribute_column.get("type")).equals("linkgroups")) || ((""+include_attribute_column.get("type")).equals("linktypes")) || ((""+include_attribute_column.get("type")).equals("productgroups")) || ((""+include_attribute_column.get("type")).equals("producttypes")) || ((""+include_attribute_column.get("type")).equals("versions")) || ((""+include_attribute_column.get("type")).equals("databases")) || ((""+include_attribute_column.get("type")).equals("data")) || ((""+include_attribute_column.get("type")).equals("usernames")) || ((""+include_attribute_column.get("type")).equals("useremails")) || ((""+include_attribute_column.get("type")).equals("usergroups")) || ((""+include_attribute_column.get("type")).equals("usertypes")))) {
						if (Data.csv_options) include_content = include_content.replaceAll(",", "|");
						mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + ".br" + "@@@\\E", include_content.replaceAll("\\|", "<br>").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + ".p" + "@@@\\E", "<p>" + include_content.replaceAll("\\|", "</p><p>").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</p>");
						mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + ".div" + "@@@\\E", "<div>" + include_content.replaceAll("\\|", "</div><div>").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</div>");
						mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + ".text" + "@@@\\E", include_content.replaceAll("\\|", "\r\n").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					} else if ((include_attribute_column != null) && (((""+include_attribute_column.get("type")).equals("content")) || ((""+include_attribute_column.get("type")).equals("contents")) || ((""+include_attribute_column.get("type")).equals("page")) || ((""+include_attribute_column.get("type")).equals("pages")) || ((""+include_attribute_column.get("type")).equals("image")) || ((""+include_attribute_column.get("type")).equals("images")) || ((""+include_attribute_column.get("type")).equals("file")) || ((""+include_attribute_column.get("type")).equals("files")) || ((""+include_attribute_column.get("type")).equals("link")) || ((""+include_attribute_column.get("type")).equals("links")) || ((""+include_attribute_column.get("type")).equals("product")) || ((""+include_attribute_column.get("type")).equals("products")) || ((""+include_attribute_column.get("type")).equals("element")) || ((""+include_attribute_column.get("type")).equals("elements")))) {
						mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + ".br" + "@@@\\E", include_content.replaceAll("\\|", "<br>").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + ".p" + "@@@\\E", "<p>" + include_content.replaceAll("\\|", "</p><p>").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</p>");
						mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + ".div" + "@@@\\E", "<div>" + include_content.replaceAll("\\|", "</div><div>").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</div>");
						mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + ".text" + "@@@\\E", include_content.replaceAll("\\|", "\r\n").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + ".titles" + "@@@\\E", dummypage.list_titles(session, request, response, config, db, include_content.replaceAll("\\|", ",")).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + ".links" + "@@@\\E", dummypage.list_links(session, request, response, config, db, include_content.replaceAll("\\|", ",")).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					} else if ((include_attribute_column != null) && (((""+include_attribute_column.get("type")).equals("datetime")) || ((""+include_attribute_column.get("type")).equals("created")) || ((""+include_attribute_column.get("type")).equals("updated")))) {
						Pattern p3 = Pattern.compile("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + "\\E:format=([^@]*?)@@@");
						Matcher m3 = p3.matcher(mycontent);
						while (m3.find()) {
							String dateformat = "" + m3.group(1);
							Date date = Common.strtodate("" + include_attribute_column.get("param1"), include_content);
							if (date != null) {
								mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + ":format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
							} else {
								mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + ":format=" + dateformat + "@@@\\E", "");
							}
							m3.reset(mycontent);
						}
					} else {
						mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + ".html@@@\\E", include_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
						mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + ".script@@@\\E", include_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r", "\\\\\\\\r").replaceAll("\n", "\\\\\\\\n").replaceAll("'", "\\\\\\\\'"));
						mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + ".text@@@\\E", include_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("<br>", "\r\n").replaceAll("<p>", "\r\n\r\n").replaceAll("</p>", ""));
					}
					mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + "\\E[\\.:]\\Q" + include_format + "@@@\\E", include_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					m2.reset(mycontent);
				}
				p2 = Pattern.compile("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + "\\E:([^:\\.@\\?]+?)@@@");
				m2 = p2.matcher(mycontent);
				while (m2.find()) {
					processed = true;
					String include_attribute = "" + m2.group(1);
					HashMap include_attribute_column = parse_output_database_column(include_database, include_attribute);
					String include_content = "";
					if ((include_attribute_column != null) && (include_attribute_column.get("id") != null) && (! include_attribute_column.get("id").equals(""))) {
						include_content = include_data.getContent("col" + include_attribute_column.get("id"));
					}
					mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + ":" + include_attribute + "@@@\\E", include_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					m2.reset(mycontent);
				}
				if (! processed) {
					mycontent = mycontent.replaceAll("\\Q@@@include:database=" + include_databasename + ":" + include_idname + "=" + include_idvalue + "\\E:([^@\\?]+?)@@@", "");
				}
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_include(ServletContext server, DB db, String id, String table, String column, Configuration config, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, String mycontent) throws Exception {
		return parse_output_replace_include(server, db, id, table, column, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mycontent, null);
	}
	public String parse_output_replace_include(ServletContext server, DB db, String id, String table, String column, Configuration config, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, String mycontent, HashMap<String,Page> page_cache) throws Exception {
		if (mycontent.contains("@@@include:")) {
			Pattern p = Pattern.compile("@@@include:([^@\\? ]*\\.[^\\.@\\?]+?):if:([^\\?!]*?)(=|!=)([^\\?]*?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find() && (! ("" + m.group(1)).contains("@@@"))) {
				String include_code = "" + m.group(1);
				String expression_value1 = "" + m.group(2);
				String expression_operand = "" + m.group(3);
				String expression_value2 = "" + m.group(4);
				if ((expression_operand.equals("=")) && (expression_value1.equals(expression_value2))) {
					mycontent = mycontent.replaceAll("\\Q@@@include:" + include_code + ":if:" + expression_value1 + expression_operand + expression_value2 + "@@@\\E", ("@@@include:" + include_code + "@@@").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				} else if ((expression_operand.equals("!=")) && (! expression_value1.equals(expression_value2))) {
					mycontent = mycontent.replaceAll("\\Q@@@include:" + include_code + ":if:" + expression_value1 + expression_operand + expression_value2 + "@@@\\E", ("@@@include:" + include_code + "@@@").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				} else {
					mycontent = mycontent.replaceAll("\\Q@@@include:" + include_code + ":if:" + expression_value1 + expression_operand + expression_value2 + "@@@\\E", "");
				}
				m.reset(mycontent);
			}
			p = Pattern.compile("@@@include:([^@\\? ]*?)\\.([^\\.@\\?]+?)@@@");
			m = p.matcher(mycontent);
			while (m.find()) {
				String special_code_include_id = "" + m.group(1);
				String include_id = "" + m.group(1);
				String include_attribute = "" + m.group(2);
				if (include_id.equals("page_top")) {
					include_id = "" + getPageTop();
				} else if (include_id.equals("page_up")) {
					include_id = "" + getPageUp();
				} else if (include_id.equals("page_first")) {
					include_id = "" + getPageFirst();
				} else if (include_id.equals("page_last")) {
					include_id = "" + getPageLast();
				} else if (include_id.equals("page_previous")) {
					include_id = "" + getPagePrevious();
				} else if (include_id.equals("page_next")) {
					include_id = "" + getPageNext();
				}
				Page include_page = null;
				if (page_cache != null) include_page = page_cache.get(special_code_include_id);
				if (include_page == null) {
					include_page = new Page(text);
					boolean preview = false;
					if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
						if (include_id.equals(getVersionMaster())) {
							include_page.read_primary_only(db, config, include_id, "content", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, "", "", "", "", "", session);
						} else {
							include_page.read_primary_only(db, config, include_id, "content", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
						}
						include_page.previewScheduledQueued(session, config, db);
						preview = include_page.previewScheduled(session);
					}
					if (! preview) {
						if (include_id.equals(getVersionMaster())) {
							include_page.read_primary_only(db, config, include_id, "content_public", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, "", "", "", "", "", session);
						} else {
							include_page.read_primary_only(db, config, include_id, "content_public", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
						}
					}
					if (! include_page.getUser()) {
						include_page = new Page(text);
					}
					include_page.parse_output(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, include_page.getId());
					if (page_cache != null) page_cache.put(special_code_include_id, include_page);
				}
				Pattern p2 = Pattern.compile("\\Q@@@include:" + special_code_include_id + "\\E\\.([^\\.@\\?]+?):format=([^@]*?)@@@");
				Matcher m2 = p2.matcher(mycontent);
				while (m2.find()) {
					include_attribute = "" + m2.group(1);
					String dateformat = "" + m2.group(2);
					String include_content = include_page.display(include_attribute, "", script_name, query_string, session_mode, session_administrator);
					Date date = new Date();
					date = Common.strtodate(include_content);
					mycontent = mycontent.replaceAll("\\Q@@@include:" + special_code_include_id + "." + include_attribute + ":format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					m2.reset(mycontent);
				}
				p2 = Pattern.compile("\\Q@@@include:" + special_code_include_id + "\\E\\.([^\\.@\\?]+?)@@@");
				m2 = p2.matcher(mycontent);
				while (m2.find()) {
					include_attribute = "" + m2.group(1);
					String include_content = include_page.display(include_attribute, "", script_name, query_string, session_mode, session_administrator);
					mycontent = mycontent.replaceAll("\\Q@@@include:" + special_code_include_id + "." + include_attribute + "@@@\\E", include_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					m2.reset(mycontent);
				}
				p2 = Pattern.compile("\\Q@@@include:" + include_id + "\\E\\.([^\\.@\\?]+?):format=([^@]*?)@@@");
				m2 = p2.matcher(mycontent);
				while (m2.find()) {
					include_attribute = "" + m2.group(1);
					String dateformat = "" + m2.group(2);
					String include_content = include_page.display(include_attribute, "", script_name, query_string, session_mode, session_administrator);
					Date date = new Date();
					date = Common.strtodate(include_content);
					mycontent = mycontent.replaceAll("\\Q@@@include:" + include_id + "." + include_attribute + ":format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					m2.reset(mycontent);
				}
				p2 = Pattern.compile("\\Q@@@include:" + include_id + "\\E\\.([^\\.@\\?]+?)@@@");
				m2 = p2.matcher(mycontent);
				while (m2.find()) {
					include_attribute = "" + m2.group(1);
					String include_content = include_page.display(include_attribute, "", script_name, query_string, session_mode, session_administrator);
					mycontent = mycontent.replaceAll("\\Q@@@include:" + include_id + "." + include_attribute + "@@@\\E", include_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					m2.reset(mycontent);
				}
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_calc(DB db, Configuration config, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, String mycontent) throws Exception {
		if ((mycontent.contains("@@@count")) || (mycontent.contains("@@@sum")) || (mycontent.contains("@@@min")) || (mycontent.contains("@@@max")) || (mycontent.contains("@@@avg"))) {
			Pattern p = Pattern.compile("@@@(count|sum|min|max|avg):([^@]+@?[^@]+?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String operand = "" + m.group(1);
				String list = "" + m.group(2);
				String listvalue = "";
				if (operand.equals("count")) {
					listvalue = parse_output_count(list, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
				} else {
					listvalue = parse_output_operand(operand, list, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
				}
				mycontent = mycontent.replaceAll("\\Q@@@" + operand + ":" + list + "@@@\\E", listvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_random(DB db, String table, String column, Configuration config, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, String mycontent) throws Exception {
		return parse_output_replace_random(db, table, column, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mycontent, null);
	}
	public String parse_output_replace_random(DB db, String table, String column, Configuration config, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, String mycontent, HashMap<String,Content> content_cache) throws Exception {
		if (mycontent.contains("@@@random:")) {
			Pattern p = Pattern.compile("@@@random:([^:@]+?):([^@]+@?[^@]+?):([^:@]+?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String randomid = "" + m.group(1);
				String randomfilter = "" + m.group(2);
				String randomattribute = "" + m.group(3);
				Content randomcontent = null;
				if (content_cache != null) randomcontent = content_cache.get(table+":::"+column+":::"+randomid+":::"+randomfilter);
				if (randomcontent == null) {
					randomcontent = parse_output_random(randomfilter, table, column, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
					content_cache.put(table+":::"+column+":::"+randomid+":::"+randomfilter, randomcontent);
				}
				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":id@@@\\E", randomcontent.getId().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":title@@@\\E", randomcontent.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":author@@@\\E", randomcontent.getAuthor().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":keywords@@@\\E", randomcontent.getKeywords().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":description@@@\\E", randomcontent.getDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":summary@@@\\E", randomcontent.getSummary().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":image1@@@\\E", randomcontent.getImage1().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":image2@@@\\E", randomcontent.getImage2().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":image3@@@\\E", randomcontent.getImage3().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":file1@@@\\E", randomcontent.getFile1().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":file2@@@\\E", randomcontent.getFile2().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":file3@@@\\E", randomcontent.getFile3().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":link1@@@\\E", randomcontent.getLink1().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":link2@@@\\E", randomcontent.getLink2().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":link3@@@\\E", randomcontent.getLink3().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":content@@@\\E", randomcontent.getContent().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":created@@@\\E", randomcontent.getCreated().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":updated@@@\\E", randomcontent.getUpdated().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":published@@@\\E", randomcontent.getPublished().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				Pattern rp = Pattern.compile("\\Q@@@random:" + randomid + ":" + randomfilter + "\\E:created:format=([^@]*?)@@@");
				Matcher rm = rp.matcher(mycontent);
				while (rm.find()) {
					String dateformat = "" + rm.group(1);
					Date date = new Date();
					date = Common.strtodate(randomcontent.display("created", true, script_name, query_string, session_mode, session_administrator));
					mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":created:format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					rm.reset(mycontent);
				}
				rp = Pattern.compile("\\Q@@@random:" + randomid + ":" + randomfilter + "\\E:updated:format=([^@]*?)@@@");
				rm = rp.matcher(mycontent);
				while (rm.find()) {
					String dateformat = "" + rm.group(1);
					Date date = new Date();
					date = Common.strtodate(randomcontent.display("updated", true, script_name, query_string, session_mode, session_administrator));
					mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":updated:format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					rm.reset(mycontent);
				}
				rp = Pattern.compile("\\Q@@@random:" + randomid + ":" + randomfilter + "\\E:published:format=([^@]*?)@@@");
				rm = rp.matcher(mycontent);
				while (rm.find()) {
					String dateformat = "" + rm.group(1);
					Date date = new Date();
					date = Common.strtodate(randomcontent.display("published", true, script_name, query_string, session_mode, session_administrator));
					mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":published:format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					rm.reset(mycontent);
				}
				if (! randomcontent.getContentClass().equals("product")) {
					mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":code@@@\\E", "");
					mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":currency@@@\\E", "");
					mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":price@@@\\E", "");
					mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":period@@@\\E", "");
					mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":stock@@@\\E", "");
					mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":stockcomment@@@\\E", "");
					mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":comment@@@\\E", "");
					mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":stockstatus@@@\\E", "");
				} else {
					mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":code@@@\\E", randomcontent.getProductCode().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":currency@@@\\E", randomcontent.getDisplayCurrency().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":price@@@\\E", randomcontent.getProductPrice().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":period@@@\\E", randomcontent.getProductPeriod().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
// deprecated				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":stock@@@\\E", randomcontent.getProductStock().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
// deprecated				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":comment@@@\\E", randomcontent.getProductComment().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					int stock_amount = Common.intnumber(randomcontent.getProductStockAmount(db));
					if (randomcontent.getProductNoStockOrder().equals("")) {
						// unlimited
						mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":stock@@@\\E", "<span id=\"WCMstock_" + randomcontent.getId() + "\" class=\"WCMstock WCMinstock\">" + stock_amount + "</span>");
						mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":stockcomment@@@\\E", "<span id=\"WCMstockcomment_" + randomcontent.getId() + "\" class=\"WCMstockcomment WCMinstock\">" + randomcontent.getProductStockText().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</span>");
						mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":comment@@@\\E", "<span id=\"WCMstockcomment_" + randomcontent.getId() + "\" class=\"WCMstockcomment WCMinstock\">" + randomcontent.getProductStockText().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</span>");
						mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":stockstatus@@@\\E", "in");
					} else if ((randomcontent.getProductNoStockOrder().equals("no")) && (stock_amount<=0)) {
						mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":stock@@@\\E", "<span id=\"WCMstock_" + randomcontent.getId() + "\" class=\"WCMstock WCMnostock\">" + "0" + "</span>");
						mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":stockcomment@@@\\E", "<span id=\"WCMstockcomment_" + randomcontent.getId() + "\" class=\"WCMstockcomment WCMnostock\">" + randomcontent.getProductNoStockText().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</span>");
						mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":comment@@@\\E", "<span id=\"WCMstockcomment_" + randomcontent.getId() + "\" class=\"WCMstockcomment WCMnostock\">" + randomcontent.getProductNoStockText().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</span>");
						mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":stockstatus@@@\\E", "no");
					} else if (stock_amount <= Common.intnumber(randomcontent.getProductLowStockAmount())) {
						mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":stock@@@\\E", "<span id=\"WCMstock_" + randomcontent.getId() + "\" class=\"WCMstock WCMlowstock\">" + stock_amount + "</span>");
						mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":stockcomment@@@\\E", "<span id=\"WCMstockcomment_" + randomcontent.getId() + "\" class=\"WCMstockcomment WCMlowstock\">" + randomcontent.getProductLowStockText().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</span>");
						mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":comment@@@\\E", "<span id=\"WCMstockcomment_" + randomcontent.getId() + "\" class=\"WCMstockcomment WCMlowstock\">" + randomcontent.getProductLowStockText().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</span>");
						mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":stockstatus@@@\\E", "low");
					} else {
						mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":stock@@@\\E", "<span id=\"WCMstock_" + randomcontent.getId() + "\" class=\"WCMstock WCMinstock\">" + stock_amount + "</span>");
						mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":stockcomment@@@\\E", "<span id=\"WCMstockcomment_" + randomcontent.getId() + "\" class=\"WCMstockcomment WCMinstock\">" + randomcontent.getProductStockText().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</span>");
						mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":comment@@@\\E", "<span id=\"WCMstockcomment_" + randomcontent.getId() + "\" class=\"WCMstockcomment WCMinstock\">" + randomcontent.getProductStockText().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</span>");
						mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":stockstatus@@@\\E", "in");
					}
				}
				mycontent = mycontent.replaceAll("\\Q@@@random:" + randomid + ":" + randomfilter + ":" + randomattribute + "@@@\\E", "");
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_page_relations(String mycontent) throws Exception {
		if (mycontent.contains("@@@page_")) {
			if (mycontent.contains("@@@page_top@@@")) mycontent = mycontent.replaceAll("@@@page_top@@@", getPageTop().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@page_up@@@")) mycontent = mycontent.replaceAll("@@@page_up@@@", getPageUp().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@page_first@@@")) mycontent = mycontent.replaceAll("@@@page_first@@@", getPageFirst().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@page_last@@@")) mycontent = mycontent.replaceAll("@@@page_last@@@", getPageLast().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@page_previous@@@")) mycontent = mycontent.replaceAll("@@@page_previous@@@", getPagePrevious().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@page_next@@@")) mycontent = mycontent.replaceAll("@@@page_next@@@", getPageNext().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		}
		return mycontent;
	}



	public String parse_output_replace_list(ServletContext server, DB db, String id, String table, String column, Configuration config, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, String mycontent) throws Exception {
		return parse_output_replace_list(server, db, id, table, column, config, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mycontent, null, null);
	}
	public String parse_output_replace_list(ServletContext server, DB db, String id, String table, String column, Configuration config, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, String mycontent, HashMap<String,String> output_cache, HashMap<String,Page> page_cache) throws Exception {
		if (mycontent.contains("@@@list:")) {
			Pattern p;
			Matcher m;
			int first;
			int remaining;
			if (mycontent.contains(":none=")) {
				p = Pattern.compile("@@@list:([^@]+@?[^@]+?):first=([0-9]*?):where:([^@\\?!]*?)(=|!=)([^@\\?]*?):none=([^@\\?]*?)@@@");
				m = p.matcher(mycontent);
				first = -1;
				remaining = -1;
				String none = "";
				boolean placeholder = false;
				while (m.find() && (! ("" + m.group(1)).contains("@@@"))) {
					String list_code = "" + m.group(1);
					String first_code = "" + m.group(2);
					String expression_value1 = "" + m.group(3);
					String expression_operand = "" + m.group(4);
					String expression_value2 = "" + m.group(5);
					String none_code = "" + m.group(6);
					if (none.equals("")) {
						none = "" + none_code;
					}
					if (first < 0) {
						first = Common.intnumber(first_code);
						remaining = 0 + first;
					}
					if ((remaining > 0) && (expression_operand.equals("=")) && (expression_value1.equals(expression_value2))) {
						mycontent = mycontent.replaceAll("\\Q@@@list:" + list_code + ":first=" + first_code + ":where:" + expression_value1 + expression_operand + expression_value2 + ":none=" + none_code + "@@@\\E", ("@@@list:" + list_code + "@@@").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						remaining--;
					} else if ((remaining > 0) && (expression_operand.equals("!=")) && (! expression_value1.equals(expression_value2))) {
						mycontent = mycontent.replaceAll("\\Q@@@list:" + list_code + ":first=" + first_code + ":where:" + expression_value1 + expression_operand + expression_value2 + ":none=" + none_code + "@@@\\E", ("@@@list:" + list_code + "@@@").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						remaining--;
					} else if (! placeholder) {
						mycontent = mycontent.replaceAll("\\Q@@@list:" + list_code + ":first=" + first_code + ":where:" + expression_value1 + expression_operand + expression_value2 + ":none=" + none_code + "@@@\\E", "@@@list:none@@@");
						placeholder = true;
					} else {
						mycontent = mycontent.replaceAll("\\Q@@@list:" + list_code + ":first=" + first_code + ":where:" + expression_value1 + expression_operand + expression_value2 + ":none=" + none_code + "@@@\\E", "");
					}
					m.reset(mycontent);
				}
				if ((first > 0) && (first == remaining)) {
					if (Pattern.compile("^[0-9]+$").matcher(none).find()) {
						mycontent = mycontent.replaceAll("@@@list:none@@@", "@@@include:" + none + ".content@@@");
					} else {
						mycontent = mycontent.replaceAll("@@@list:none@@@", none.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					}
				} else {
					mycontent = mycontent.replaceAll("@@@list:none@@@", "");
				}
			}

			if (mycontent.contains(":where:")) {
				p = Pattern.compile("@@@list:([^@]+@?[^@]+?):first=([0-9]*?):where:([^@\\?!]*?)(=|!=)([^@\\?]*?)@@@");
				m = p.matcher(mycontent);
				first = -1;
				remaining = -1;
				while (m.find() && (! ("" + m.group(1)).contains("@@@"))) {
					String list_code = "" + m.group(1);
					String first_code = "" + m.group(2);
					String expression_value1 = "" + m.group(3);
					String expression_operand = "" + m.group(4);
					String expression_value2 = "" + m.group(5);
					if (first < 0) {
						first = Common.intnumber(first_code);
						remaining = 0 + first;
					}
					if ((remaining > 0) && (expression_operand.equals("=")) && (expression_value1.equals(expression_value2))) {
						mycontent = mycontent.replaceAll("\\Q@@@list:" + list_code + ":first=" + first_code + ":where:" + expression_value1 + expression_operand + expression_value2 + "@@@\\E", ("@@@list:" + list_code + "@@@").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						remaining--;
					} else if ((remaining > 0) && (expression_operand.equals("!=")) && (! expression_value1.equals(expression_value2))) {
						mycontent = mycontent.replaceAll("\\Q@@@list:" + list_code + ":first=" + first_code + ":where:" + expression_value1 + expression_operand + expression_value2 + "@@@\\E", ("@@@list:" + list_code + "@@@").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						remaining--;
					} else {
						mycontent = mycontent.replaceAll("\\Q@@@list:" + list_code + ":first=" + first_code + ":where:" + expression_value1 + expression_operand + expression_value2 + "@@@\\E", "");
					}
					m.reset(mycontent);
				}
			}

			if (mycontent.contains(":if:")) {
				p = Pattern.compile("@@@list:([^@]+@?[^@]+?):if:([^@\\?!]*?)(=|!=)([^@\\?]*?)@@@");
				m = p.matcher(mycontent);
				while (m.find() && (! (""+m.group(1)).contains("@@@"))) {
					String old_content = "" + mycontent;
					String list_code = "" + m.group(1);
					String expression_value1 = "" + m.group(2);
					String expression_operand = "" + m.group(3);
					String expression_value2 = "" + m.group(4);
					if ((expression_operand.equals("=")) && (expression_value1.equals(expression_value2))) {
						mycontent = mycontent.replaceAll("\\Q@@@list:" + list_code + ":if:" + expression_value1 + expression_operand + expression_value2 + "@@@\\E", ("@@@list:" + list_code + "@@@").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					} else if ((expression_operand.equals("!=")) && (! expression_value1.equals(expression_value2))) {
						mycontent = mycontent.replaceAll("\\Q@@@list:" + list_code + ":if:" + expression_value1 + expression_operand + expression_value2 + "@@@\\E", ("@@@list:" + list_code + "@@@").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					} else {
						mycontent = mycontent.replaceAll("\\Q@@@list:" + list_code + ":if:" + expression_value1 + expression_operand + expression_value2 + "@@@\\E", "");
					}
					if (mycontent.equals(old_content)) break;
					m.reset(mycontent);
				}
			}

			p = Pattern.compile("@@@list:([^@]+@?[^@]+?)@@@");
			m = p.matcher(mycontent);
			while (m.find()) {
				String list = "" + m.group(1);
				String listcontent = parse_output_list(list, table, column, server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, "", page_cache, output_cache);
				String save_parse_output_list_link_start = parse_output_list_link_start;
				int save_parse_output_list_link_limit = parse_output_list_link_limit;
				int save_parse_output_list_link_first = parse_output_list_link_first;
				int save_parse_output_list_link_previous = parse_output_list_link_previous;
				int save_parse_output_list_link_next = parse_output_list_link_next;
				int save_parse_output_list_link_last = parse_output_list_link_last;
				int save_parse_output_list_link_count = parse_output_list_link_count;
				if (_DEBUG_) System.out.println("HardCore/Page.parse_output_replace:loopdetection:list:"+table+":"+column+":"+id+":::"+list);
				listcontent = parse_output_replace(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, listcontent, "000");
				parse_output_list_link_start = save_parse_output_list_link_start;
				parse_output_list_link_limit = save_parse_output_list_link_limit;
				parse_output_list_link_first = save_parse_output_list_link_first;
				parse_output_list_link_previous = save_parse_output_list_link_previous;
				parse_output_list_link_next = save_parse_output_list_link_next;
				parse_output_list_link_last = save_parse_output_list_link_last;
				parse_output_list_link_count = save_parse_output_list_link_count;
				// avoid possible replace loop and OutOfMemoryError if the listcontent output contains the same @@@list:...@@@ code as it is to replace
				listcontent = listcontent.replaceAll("\\Q@@@list:" + list + "@@@\\E", "");
				mycontent = mycontent.replaceAll("\\Q@@@list:" + list + "@@@\\E", listcontent.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				if (! parse_output_list_link_start.equals("")) {
					Pattern p2;
					Matcher m2;
					p2 = Pattern.compile("\\Q@@@previous:" + parse_output_list_link_start + "\\E:([^@]+@?[^@]+?)@@@");
					m2 = p2.matcher(mycontent);
					while (m2.find()) {
						String listformat = "" + m2.group(1);
						String listpreviouscontent = parse_output_list_previous_link(parse_output_list_link_start, listformat, parse_output_list_link_previous, request);
						mycontent = mycontent.replaceAll("\\Q@@@previous:" + parse_output_list_link_start + ":" + listformat + "@@@\\E", listpreviouscontent.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						m2.reset(mycontent);
					}
					if (output_cache != null) {
						output_cache.put("!@@@previous:" + parse_output_list_link_start + "@@@", ""+parse_output_list_link_previous);
					}
					p2 = Pattern.compile("\\Q@@@next:" + parse_output_list_link_start + "\\E:([^@]+@?[^@]+?)@@@");
					m2 = p2.matcher(mycontent);
					while (m2.find()) {
						String listformat = "" + m2.group(1);
						String listnextcontent = parse_output_list_next_link(parse_output_list_link_start, listformat, parse_output_list_link_next, request);
						mycontent = mycontent.replaceAll("\\Q@@@next:" + parse_output_list_link_start + ":" + listformat + "@@@\\E", listnextcontent.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						m2.reset(mycontent);
					}
					if (output_cache != null) {
						output_cache.put("!@@@next:" + parse_output_list_link_start + "@@@", ""+parse_output_list_link_next);
					}
					p2 = Pattern.compile("\\Q@@@first:" + parse_output_list_link_start + "\\E:([^@]+@?[^@]+?)@@@");
					m2 = p2.matcher(mycontent);
					while (m2.find()) {
						String listformat = "" + m2.group(1);
						String listfirstcontent = parse_output_list_first_link(parse_output_list_link_start, listformat, parse_output_list_link_first, request);
						mycontent = mycontent.replaceAll("\\Q@@@first:" + parse_output_list_link_start + ":" + listformat + "@@@\\E", listfirstcontent.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						m2.reset(mycontent);
					}
					if (output_cache != null) {
						output_cache.put("!@@@first:" + parse_output_list_link_start + "@@@", ""+parse_output_list_link_first);
					}
					p2 = Pattern.compile("\\Q@@@last:" + parse_output_list_link_start + "\\E:([^@]+@?[^@]+?)@@@");
					m2 = p2.matcher(mycontent);
					while (m2.find()) {
						String listformat = "" + m2.group(1);
						String listlastcontent = parse_output_list_last_link(parse_output_list_link_start, listformat, parse_output_list_link_last, request);
						mycontent = mycontent.replaceAll("\\Q@@@last:" + parse_output_list_link_start + ":" + listformat + "@@@\\E", listlastcontent.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						m2.reset(mycontent);
					}
					if (output_cache != null) {
						output_cache.put("!@@@last:" + parse_output_list_link_start + "@@@", ""+parse_output_list_link_last);
					}
					p2 = Pattern.compile("\\Q@@@paged:" + parse_output_list_link_start + "\\E:([^@]+@?[^@]+?)@@@");
					m2 = p2.matcher(mycontent);
					while (m2.find()) {
						String listformat = "" + m2.group(1);
						String listpagedcontent = parse_output_list_paged_links(parse_output_list_link_start, listformat, parse_output_list_link_limit, parse_output_list_link_count, request);
						mycontent = mycontent.replaceAll("\\Q@@@paged:" + parse_output_list_link_start + ":" + listformat + "@@@\\E", listpagedcontent.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						m2.reset(mycontent);
					}
					if (output_cache != null) {
						output_cache.put("!@@@paged:" + parse_output_list_link_start + "@@@", ""+parse_output_list_link_limit+":::"+parse_output_list_link_count);
					}
				}
				m.reset(mycontent);
			}
			mycontent = mycontent.replaceAll("@@@(first|previous|paged|next|last):([^@]+@?[^@]+?):([^@]+@?[^@]+?)@@@", "");
		}
		return mycontent;
	}



	public String parse_output_replace_shopcart(ServletContext server, DB db, String id, String table, String column, Configuration config, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, String mycontent) throws Exception {
		if (mycontent.contains("@@@shopcart@@@")) {
			Page shopcart_page = new Page(text);
			Page shopcart_item = new Page(text);
			boolean preview = false;
			if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
				shopcart_page.read_primary_only(db, config, default_shopcartsummary_page, "content", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
				shopcart_item.read_primary_only(db, config, default_shopcartsummary_entry, "content", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
				shopcart_item.previewScheduledQueued(session, config, db);
				preview = shopcart_item.previewScheduled(session);
			}
			if (! preview) {
				shopcart_page.read_primary_only(db, config, default_shopcartsummary_page, "content_public", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
				shopcart_item.read_primary_only(db, config, default_shopcartsummary_entry, "content_public", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			}
			Shopcart shopcart = new Shopcart(text);
			shopcart.read(session, db, config);
			shopcart_page.parse_output_shopcart(shopcart, "", "", server, request, response, session, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, db, config, shopcart_item, true);
			mycontent = mycontent.replaceAll("@@@shopcart@@@", shopcart_page.getBody().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		}

		return mycontent;
	}



	public String parse_output_replace_error(Session session, String mycontent) throws Exception {
		if (mycontent.contains("@@@error@@@")) {
			if (! session.get("captcha_error").equals("")) {
				mycontent = mycontent.replaceAll("@@@error@@@", session.get("captcha_error").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				session.set("captcha_error", "");
			}
		}
		return mycontent;
	}



	public String parse_output_replace_captcha(ServletContext server, DB db, Configuration config, Request request, Session session, String mycontent) throws Exception {
		if (mycontent.contains("@@@captcha@@@")) {
			session.set("captcha_url", "" + request.getProtocol() + request.getServerName() + request.getServerPort() + request.getServletPath() + "?" + Common.crlf_clean(request.getQueryString()).replaceAll("&[.0-9]+$", "") + "&" + Math.random());
			if ((! config.get(db, "captcha").equals("")) && ((config.get(db, "captcha_user").equals("yes")) || (session.get("username").equals("")))) {
				mycontent = mycontent.replaceAll("@@@captcha@@@", CAPTCHA.html(session, server, request, config, db).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			} else {
				mycontent = mycontent.replaceAll("@@@captcha@@@", "");
			}
			session.set("captcha_error", "");
		}
		if (mycontent.contains("@@@captcha:")) {
			Pattern p = Pattern.compile("@@@captcha:([_a-zA-Z0-9]+?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String myname = "" + m.group(1);
				if ((! config.get(db, "captcha").equals("")) && (! config.get(db, "captcha_" + myname).equals("")) && ((config.get(db, "captcha_user").equals("yes")) || (session.get("username").equals("")))) {
					session.set("captcha_url", "" + request.getProtocol() + request.getServerName() + request.getServerPort() + request.getServletPath() + "?" + Common.crlf_clean(request.getQueryString()).replaceAll("&[.0-9]+$", "") + "&" + Math.random());
					mycontent = mycontent.replaceAll("@@@captcha:" + myname + "@@@", CAPTCHA.html(session, server, request, config, db).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					session.set("captcha_error", "");
				} else {
					mycontent = mycontent.replaceAll("@@@captcha:" + myname + "@@@", "");
				}
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_authorize(Session session, String mycontent) throws Exception {
		if (mycontent.contains("@@@authorize:")) {
			Pattern p = Pattern.compile("@@@authorize:([_a-zA-Z0-9]+?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String myname = "" + m.group(1);
				String mytoken = Common.authorize(session, myname);
				mycontent = mycontent.replaceAll("@@@authorize:" + myname + "@@@", mytoken);
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_webeditor(ServletContext server, DB db, String id, String table, String column, Configuration config, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, String mycontent) throws Exception {
		if (mycontent.contains("@@@webeditor:language@@@")) mycontent = mycontent.replaceAll("@@@webeditor:language@@@", "jsp");
		if (mycontent.contains("@@@webeditor:")) {
			Pattern p = Pattern.compile("@@@webeditor:([^@]+@?[^@]+?)@@@");
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String webeditor = "" + m.group(1);
				String webeditorcontent = parse_output_webeditor(webeditor, server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
				mycontent = mycontent.replaceAll("\\Q@@@webeditor:" + webeditor + "@@@\\E", webeditorcontent.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(mycontent);
			}
		}
		return mycontent;
	}



	public String parse_output_replace_user(Session session, String mycontent) throws Exception {
		if (mycontent.contains("@@@user_")) {			
			if (mycontent.contains("@@@user_id@@@")) mycontent = mycontent.replaceAll("@@@user_id@@@", session.get("userid").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_username@@@")) mycontent = mycontent.replaceAll("@@@user_username@@@", session.get("username").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_password@@@")) mycontent = mycontent.replaceAll("@@@user_password@@@", session.get("password").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_name@@@")) mycontent = mycontent.replaceAll("@@@user_name@@@", session.get("name").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_email@@@")) mycontent = mycontent.replaceAll("@@@user_email@@@", session.get("email").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_class@@@")) mycontent = mycontent.replaceAll("@@@user_class@@@", session.get("administrator").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_group@@@")) mycontent = mycontent.replaceAll("@@@user_group@@@", session.get("usergroup").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_type@@@")) mycontent = mycontent.replaceAll("@@@user_type@@@", session.get("usertype").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_groups@@@")) mycontent = mycontent.replaceAll("@@@user_groups@@@", session.get("usergroups").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_types@@@")) mycontent = mycontent.replaceAll("@@@user_types@@@", session.get("usertypes").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_mode@@@")) mycontent = mycontent.replaceAll("@@@user_mode@@@", session.get("mode").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));

			if (mycontent.contains("@@@user_organisation@@@")) mycontent = mycontent.replaceAll("@@@user_organisation@@@", session.get("user_organisation").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_created@@@")) mycontent = mycontent.replaceAll("@@@user_created@@@", session.get("user_created").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_updated@@@")) mycontent = mycontent.replaceAll("@@@user_updated@@@", session.get("user_updated").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_scheduled_publish@@@")) mycontent = mycontent.replaceAll("@@@user_scheduled_publish@@@", session.get("user_scheduled_publish").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_scheduled_notify@@@")) mycontent = mycontent.replaceAll("@@@user_scheduled_notify@@@", session.get("user_scheduled_notify").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_scheduled_unpublish@@@")) mycontent = mycontent.replaceAll("@@@user_scheduled_unpublish@@@", session.get("user_scheduled_unpublish").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_card_type@@@")) mycontent = mycontent.replaceAll("@@@user_card_type@@@", session.get("user_card_type").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_card_number@@@")) mycontent = mycontent.replaceAll("@@@user_card_number@@@", session.get("user_card_number").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_card_issuedmonth@@@")) mycontent = mycontent.replaceAll("@@@user_card_issuedmonth@@@", session.get("user_card_issuedmonth").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_card_issuedyear@@@")) mycontent = mycontent.replaceAll("@@@user_card_issuedyear@@@", session.get("user_card_issuedyear").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_card_expirymonth@@@")) mycontent = mycontent.replaceAll("@@@user_card_expirymonth@@@", session.get("user_card_expirymonth").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_card_expiryyear@@@")) mycontent = mycontent.replaceAll("@@@user_card_expiryyear@@@", session.get("user_card_expiryyear").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_card_name@@@")) mycontent = mycontent.replaceAll("@@@user_card_name@@@", session.get("user_card_name").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_card_cvc@@@")) mycontent = mycontent.replaceAll("@@@user_card_cvc@@@", session.get("user_card_cvc").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_card_issue@@@")) mycontent = mycontent.replaceAll("@@@user_card_issue@@@", session.get("user_card_issue").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_card_postalcode@@@")) mycontent = mycontent.replaceAll("@@@user_card_postalcode@@@", session.get("user_card_postalcode").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_delivery_name@@@")) mycontent = mycontent.replaceAll("@@@user_delivery_name@@@", session.get("user_delivery_name").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_delivery_organisation@@@")) mycontent = mycontent.replaceAll("@@@user_delivery_organisation@@@", session.get("user_delivery_organisation").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_delivery_address@@@")) mycontent = mycontent.replaceAll("@@@user_delivery_address@@@", session.get("user_delivery_address").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_delivery_postalcode@@@")) mycontent = mycontent.replaceAll("@@@user_delivery_postalcode@@@", session.get("user_delivery_postalcode").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_delivery_city@@@")) mycontent = mycontent.replaceAll("@@@user_delivery_city@@@", session.get("user_delivery_city").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_delivery_state@@@")) mycontent = mycontent.replaceAll("@@@user_delivery_state@@@", session.get("user_delivery_state").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_delivery_country@@@")) mycontent = mycontent.replaceAll("@@@user_delivery_country@@@", session.get("user_delivery_country").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_delivery_phone@@@")) mycontent = mycontent.replaceAll("@@@user_delivery_phone@@@", session.get("user_delivery_phone").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_delivery_fax@@@")) mycontent = mycontent.replaceAll("@@@user_delivery_fax@@@", session.get("user_delivery_fax").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_delivery_email@@@")) mycontent = mycontent.replaceAll("@@@user_delivery_email@@@", session.get("user_delivery_email").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_delivery_website@@@")) mycontent = mycontent.replaceAll("@@@user_delivery_website@@@", session.get("user_delivery_website").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_invoice_name@@@")) mycontent = mycontent.replaceAll("@@@user_invoice_name@@@", session.get("user_invoice_name").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_invoice_organisation@@@")) mycontent = mycontent.replaceAll("@@@user_invoice_organisation@@@", session.get("user_invoice_organisation").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_invoice_address@@@")) mycontent = mycontent.replaceAll("@@@user_invoice_address@@@", session.get("user_invoice_address").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_invoice_postalcode@@@")) mycontent = mycontent.replaceAll("@@@user_invoice_postalcode@@@", session.get("user_invoice_postalcode").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_invoice_city@@@")) mycontent = mycontent.replaceAll("@@@user_invoice_city@@@", session.get("user_invoice_city").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_invoice_state@@@")) mycontent = mycontent.replaceAll("@@@user_invoice_state@@@", session.get("user_invoice_state").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_invoice_country@@@")) mycontent = mycontent.replaceAll("@@@user_invoice_country@@@", session.get("user_invoice_country").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_invoice_phone@@@")) mycontent = mycontent.replaceAll("@@@user_invoice_phone@@@", session.get("user_invoice_phone").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_invoice_fax@@@")) mycontent = mycontent.replaceAll("@@@user_invoice_fax@@@", session.get("user_invoice_fax").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_invoice_email@@@")) mycontent = mycontent.replaceAll("@@@user_invoice_email@@@", session.get("user_invoice_email").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_invoice_website@@@")) mycontent = mycontent.replaceAll("@@@user_invoice_website@@@", session.get("user_invoice_website").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@user_notes@@@")) mycontent = mycontent.replaceAll("@@@user_notes@@@", session.get("user_notes").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));

//			if (mycontent.contains("@@@user_segments@@@")) mycontent = mycontent.replaceAll("@@@user_segments@@@", session.get("usersegments").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
//			if (mycontent.contains("@@@user_tests@@@")) mycontent = mycontent.replaceAll("@@@user_tests@@@", session.get("usertests").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			
			String[] myinfos = session.get("user_userinfo").split("[\r\n]+");
			for (int j=0; j<myinfos.length; j++) {
				String myinfo = myinfos[j];
				Matcher myinfoMatches = Pattern.compile("<([^<>]+?)>([^<>]*?)</([^<>]+?)>").matcher(myinfo);
				if (myinfoMatches.find()) {
					String myname = "" + myinfoMatches.group(1);
					String myvalue = "" + myinfoMatches.group(2);
					if (mycontent.contains("@@@user_" + myname + "@@@")) mycontent = mycontent.replaceAll("\\Q@@@user_" + myname + "@@@\\E", myvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					if (mycontent.contains("@@@user_" + myname.replaceAll(" ", "_") + "@@@")) mycontent = mycontent.replaceAll("\\Q@@@user_" + myname.replaceAll(" ", "_") + "@@@\\E", myvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				}
			}
		}
		return mycontent;
	}



	public boolean parse_output_condition_if(Request request, String mycontent, HashMap<String,String> output_cache, boolean current_output) {
		boolean do_output = current_output;
		Pattern p = Pattern.compile("@@@condition:([^:@]+?):if:\\((((?!@@@).(?!@@@))*?)\\)@@@", Pattern.DOTALL);
		Matcher m = p.matcher(mycontent);
		if (m.find()) {
			String if_name = "" + m.group(1);
			String if_code = "" + m.group(2);
			boolean isValid = parse_output_condition_eval(if_code);
			if (isValid) {
				output_cache.put("!@@@condition:" + if_name + "@@@",  "true");
				do_output = current_output;
			} else {
				output_cache.put("!@@@condition:" + if_name + "@@@",  "false");
				do_output = false;
			}
			output_cache.put("!@@@condition:" + if_name + ":endif@@@",  ""+current_output);

		}
		return do_output;
	}
	public boolean parse_output_condition_elseif(Request request, String mycontent, HashMap<String,String> output_cache, boolean current_output) {
		boolean do_output = current_output;
		Pattern p = Pattern.compile("@@@condition:([^:@]+?):elseif:\\((((?!@@@).(?!@@@))*?)\\)@@@", Pattern.DOTALL);
		Matcher m = p.matcher(mycontent);
		if (m.find()) {
			String if_name = "" + m.group(1);
			String if_code = "" + m.group(2);
			boolean isValid = parse_output_condition_eval(if_code);
			if (output_cache.get("!@@@condition:" + if_name + "@@@") == null) {
				// skip - invalid - no initial if condition
				do_output = current_output;
			} else if (output_cache.get("!@@@condition:" + if_name + "@@@").toLowerCase().equals("true")) {
				// skip - previous condition was true
				do_output = false;
			} else if (isValid) {
				output_cache.put("!@@@condition:" + if_name + "@@@",  "true");
				do_output = do_output = output_cache.get("!@@@condition:" + if_name + ":endif@@@").toLowerCase().equals("true");
			} else {
				output_cache.put("!@@@condition:" + if_name + "@@@",  "false");
				do_output = false;
			}
		}
		return do_output;
	}
	public boolean parse_output_condition_else(Request request, String mycontent, HashMap<String,String> output_cache, boolean current_output) {
		boolean do_output = current_output;
		Pattern p = Pattern.compile("@@@condition:([^:@]+?):else@@@", Pattern.DOTALL);
		Matcher m = p.matcher(mycontent);
		if (m.find()) {
			String if_name = "" + m.group(1);
			if (output_cache.get("!@@@condition:" + if_name + "@@@") == null) {
				// skip - invalid - no initial if condition
				do_output = current_output;
			} else if (output_cache.get("!@@@condition:" + if_name + "@@@").toLowerCase().equals("true")) {
				// skip - previous condition was true
				do_output = false;
			} else {
				output_cache.put("!@@@condition:" + if_name + "@@@",  "true");
				do_output = output_cache.get("!@@@condition:" + if_name + ":endif@@@").toLowerCase().equals("true");
			}
		}
		return do_output;
	}
	public boolean parse_output_condition_endif(Request request, String mycontent, HashMap<String,String> output_cache, boolean current_output) {
		boolean do_output = current_output;
		Pattern p = Pattern.compile("@@@condition:([^:@]+?):endif@@@", Pattern.DOTALL);
		Matcher m = p.matcher(mycontent);
		if (m.find()) {
			String if_name = "" + m.group(1);
			if (output_cache.get("!@@@condition:" + if_name + "@@@") == null) {
				// skip - invalid - no initial if condition
				do_output = current_output;
			} else if (output_cache.get("!@@@condition:" + if_name + ":endif@@@") == null) {
				// skip - invalid - no endif condition
				do_output = current_output;
			} else {
				do_output = output_cache.get("!@@@condition:" + if_name + ":endif@@@").toLowerCase().equals("true");
			}
		}
		return do_output;
	}
	public String parse_output_condition(Request request, String mycontent) {
		if (mycontent.contains("@@@condition:")) {
			Pattern p = Pattern.compile("@@@condition:([^:@]+?):if:\\((((?!@@@).(?!@@@))*?)\\)@@@", Pattern.DOTALL);
			Matcher m = p.matcher(mycontent);
			while (m.find()) {
				String if_name = "" + m.group(1);
				String if_code = "" + m.group(2);
				Pattern p2 = Pattern.compile("\\Q@@@condition:" + if_name + ":endif@@@\\E");
				Matcher m2 = p2.matcher(mycontent);
				if (m2.find() && m.start() < m2.start()) {
					StringBuilder sb = new StringBuilder();
					sb.append(mycontent.substring(0, m.start()));

					int startpos = m.end();
					int endpos = m2.start();
					boolean isValid = parse_output_condition_eval(if_code);
					String elseifword = "@@@condition:" + if_name + ":elseif:";
					int elseifpos = mycontent.indexOf(elseifword, startpos);
					if (isValid) {
						if ((startpos < elseifpos) && (elseifpos < endpos)) {
							endpos = elseifpos;
						} else {
							String elseword = "@@@condition:" + if_name + ":else@@@";
							int elsepos = mycontent.indexOf(elseword, startpos);
							if (elsepos >= 0) {
								endpos = elsepos;
							}
						}
					} else if ((elseifpos >= 0) && (elseifpos < endpos)) {
						sb.append("@@@condition:").append(if_name).append(":if:");
						startpos = elseifpos + elseifword.length();
						endpos = m2.end();
					} else {
						String elseword = "@@@condition:" + if_name + ":else@@@";
						int elsepos = mycontent.indexOf(elseword, startpos);
						if (elsepos >= 0) {
							if ((startpos < elsepos) && (elsepos < endpos)) {
								startpos = elsepos + elseword.length();
							}
						} else {
							startpos = endpos;
						}
					}
					sb.append(mycontent.substring(startpos, endpos));
					if (mycontent.length() > m2.end()) {
						sb.append(mycontent.substring(m2.end()));
					}
					mycontent = sb.toString();
				} else {
					mycontent = mycontent.replaceAll("\\Q@@@condition:" + if_name + "\\E[^@\\?]*?@@@", "");
				}
				m.reset(mycontent);
			}
		}
		return mycontent;
	}
	private boolean parse_output_condition_eval(String if_code) {
		String[] expressions = if_code.split("&&|&amp;&amp;|:if:");
		Pattern p2 = Pattern.compile("^([^@\\?!]*?)(=|!=|<=|&lt;=|>=|&gt;=|<|&lt;|>|&gt;)([^@\\?]*?)$");
		boolean isValid = true;
		for (int i=0; i<expressions.length; i++) {
			String[] orExpressions = expressions[i].split("\\|\\|");
			boolean orValid = true;
			for (int j=0; j<orExpressions.length; j++) {
				Matcher m2 = p2.matcher(orExpressions[j]);
				if (m2.matches()) {
					String expression_value1 = "" + m2.group(1);
					String expression_operand = "" + m2.group(2);
					String expression_value2 = "" + m2.group(3);
					if ((expression_operand.equals("=")) && (expression_value1.equals(expression_value2))) {
						orValid = true;
						break;
					} else if ((expression_operand.equals("!=")) && (! expression_value1.equals(expression_value2))) {
						orValid = true;
						break;
					} else if ((expression_operand.equals(">")) || (expression_operand.equals("&gt;"))) {
						if (Common.numeric(expression_value1) && Common.numeric(expression_value2)) {
							if (Common.number(expression_value1) > Common.number(expression_value2)) {
								orValid = true;
								break;
							} else {
								orValid = false;
							}
						} else if (expression_value1.compareTo(expression_value2) > 0) {
							orValid = true;
							break;
						} else {
							orValid = false;
						}
					} else if ((expression_operand.equals("<")) || (expression_operand.equals("&lt;"))) {
						if (Common.numeric(expression_value1) && Common.numeric(expression_value2)) {
							if (Common.number(expression_value1) < Common.number(expression_value2)) {
								orValid = true;
								break;
							} else {
								orValid = false;
							}
						} else if (expression_value1.compareTo(expression_value2) < 0) {
							orValid = true;
							break;
						} else {
							orValid = false;
						}
					} else if ((expression_operand.equals(">=")) || (expression_operand.equals("&gt;="))) {
						if (Common.numeric(expression_value1) && Common.numeric(expression_value2)) {
							if (Common.number(expression_value1) >= Common.number(expression_value2)) {
								orValid = true;
								break;
							} else {
								orValid = false;
							}
						} else if (expression_value1.compareTo(expression_value2) >= 0) {
							orValid = true;
							break;
						} else {
							orValid = false;
						}
					} else if ((expression_operand.equals("<=")) || (expression_operand.equals("&lt;="))) {
						if (Common.numeric(expression_value1) && Common.numeric(expression_value2)) {
							if (Common.number(expression_value1) <= Common.number(expression_value2)) {
								orValid = true;
								break;
							} else {
								orValid = false;
							}
						} else if (expression_value1.compareTo(expression_value2) <= 0) {
							orValid = true;
							break;
						} else {
							orValid = false;
						}
					} else {
						orValid = false;
					}
				}
			}
			if (! orValid) {
				isValid = false;
				break;
			}
		}
		return isValid;
	}



	public String parse_output_count(String list, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		return parse_output_count(list, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public String parse_output_count(String list, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		if (db == null) return "0";
		String count = "0";

		String SQL = "";
		String SQLdata = "";
		Databases database = parse_output_list_database( list, request, response, session, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config);
		if ((! database.getId().equals("")) && (! database.getId().equals("0")) && (! database.getId().equals("-1"))) {
			SQLdata = parse_output_list_data_where(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, database);
		}

		String SQLwhere = parse_output_list_where(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, database);

		if ((database != null) && ((! database.getId().equals("")) && (! database.getId().equals("0")) && (! database.getId().equals("-1")))) {
			if (database.getUser()) {
				if (! SQLdata.equals("")) {
					SQL = "select count(id) from data" + database.getId() + " where " + SQLdata;
				} else {
					SQL = "select count(id) from data" + database.getId();
				}
			}
		} else if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
			String SQLfrom = "";
			if (_METAINFO_) {
				SQLfrom = parse_output_list_from(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
			}
			SQL = "select count(id) from content" + SQLfrom + " where " + SQLwhere;
		} else {
			String SQLfrom = "";
			if (_METAINFO_) {
				SQLfrom = parse_output_list_from(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
			}
			SQL = "select count(id) from content_public" + SQLfrom + " where " + SQLwhere;
		}

		if (! SQL.equals("")) {
			count = "" + db.query_value(SQL);
			count = count.replaceAll("[,\\.]0*$", "");
		}
		return "" + count;
	}



	public String parse_output_operand(String operand, String list, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		return parse_output_operand(operand, list, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public String parse_output_operand(String operand, String list, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		if (db == null) return "0";
		String value = "0";

		String SQL = "";
		String SQLdata = "";
		Databases database = parse_output_list_database( list, request, response, session, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config);
		if ((! database.getId().equals("")) && (! database.getId().equals("0")) && (! database.getId().equals("-1"))) {
			SQLdata = parse_output_list_data_where(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, database);
		}

		String SQLwhere = parse_output_list_where(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, database);
		HashMap datacolumn = parse_output_list_data_column(database, list);

		if ((database != null) && ((! database.getId().equals("")) && (! database.getId().equals("0")) && (! database.getId().equals("-1"))) && ((datacolumn != null) && (datacolumn.get("id") != null) && (! datacolumn.get("id").equals("")))) {
			if (database.getUser()) {
				if (! SQLdata.equals("")) {
					SQL = "select " + Common.SQL_clean(operand) + "(col" + datacolumn.get("id") + ") from data" + database.getId() + " where " + SQLdata;
				} else {
					SQL = "select " + Common.SQL_clean(operand) + "(col" + datacolumn.get("id") + ") from data" + database.getId();
				}
			}
		} else if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
			SQL = "select count(id) from content where " + SQLwhere;
		} else {
			SQL = "select count(id) from content_public where " + SQLwhere;
		}

		if (! SQL.equals("")) {
			value = "" + db.query_value(SQL);
			value = value.replaceAll("[,\\.]?0*$", "");
		}

		if (datacolumn != null) {
			if (datacolumn.get("type").equals("number")) {
				int digits = Common.intnumber("" + datacolumn.get("param1"));
				int decimals = Common.intnumber("" + datacolumn.get("param2"));
				NumberFormat numberformat = NumberFormat.getInstance();
				numberformat.setGroupingUsed(false);
				numberformat.setMinimumIntegerDigits(1);
				numberformat.setMinimumFractionDigits(decimals);
				numberformat.setMaximumFractionDigits(decimals);
				value = numberformat.format(Double.parseDouble("0" + value));
			}
		}
		return "" + value;
	}



	public Content parse_output_random(String randomfilter, String table, String column, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		return parse_output_random(randomfilter, table, column, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public Content parse_output_random(String randomfilter, String table, String column, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		Content randomcontent = new Content(text);
		if (db == null) return randomcontent;
		if ((table == null) || (table.equals(""))) {
			table = "content_public";
			if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
				table = "content";
			}
		}
		String SQLwhere = parse_output_list_where(randomfilter, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, null);

		long randomcount = 0;
		String SQL = "select count(*) as mycount from " + table + " where " + SQLwhere;
		randomcount = Common.integernumber("" + db.query_value(SQL));

		long randomid = 0;
		long randomrow = (long)(Math.random()*(randomcount))+1;

		SQL = "select id from " + table + " where " + SQLwhere + " order by id";
		Statement rs = db.records(SQL);
		HashMap row;
		while (((row = db.records(rs)) != null) && (randomrow > 0)) {
			randomid = Common.integernumber("" + row.get("id"));
			randomrow -= 1;
		}

		randomcontent.read(db, config, ""+randomid, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
		if (! randomcontent.getUser()) {
			randomcontent = new Content(text);
		}
		randomcontent.setDisplayCurrency(db, session.get("currency"), config.get(db, "default_currency"));
		return randomcontent;
	}



	public String parse_output_list(String list, String table, String column, ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, String listentrycontent) throws Exception {
		return parse_output_list(list, table, column, server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, listentrycontent, null, null);
	}
	public String parse_output_list(String list, String table, String column, ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, String listentrycontent, HashMap<String,Page> page_cache) throws Exception {
		return parse_output_list(list, table, column, server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, listentrycontent, page_cache, null);
	}
	public String parse_output_list(String list, String table, String column, ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, String listentrycontent, HashMap<String,Page> page_cache, HashMap<String,String> output_cache) throws Exception {
		StringBuilder content = new StringBuilder();
		String liststart = "";
		int listfirst = 1;
		int listprevious = 0;
		int listnext = 0;
		int listlast = 0;
		int listcount = 0;

		String SQLdata = "";
		Databases database = parse_output_list_database( list, request, response, session, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config);
		if ((! database.getId().equals("")) && (! database.getId().equals("0")) && (! database.getId().equals("-1"))) {
			SQLdata = parse_output_list_data(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, database);
		}

		String SQLfrom = "";
		if (_METAINFO_) {
			SQLfrom = parse_output_list_from(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
		}
		String SQLwhere = parse_output_list_where(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, database);
		String SQLorder = parse_output_list_order(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, database);
		String limit = parse_output_list_limit(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
		String none = parse_output_list_none(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
		String distinct = parse_output_list_distinct(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
		String listentryid = parse_output_list_entry(list, "entry", request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
		String listorderitemsentryid = parse_output_list_entry(list, "orderitem", request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
		boolean listmerge = parse_output_list_merge(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
		String listcolumns = parse_output_list_columns(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
		String listheader = parse_output_list_header(list, table, column, server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, page_cache);
		String listfooter = parse_output_list_footer(list, table, column, server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, page_cache);
		String listclass = parse_output_list_class(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
		boolean listsearchresults = (list.contains("searchresults"));
		if (listsearchresults) {
			Website mywebsite = new Website();
			if (! request.getParameter("searchresult").equals("")) {
				listentryid = request.getParameter("searchresult");
			} else if ((! request.getParameter("database").equals("")) && (database != null) && (! database.getSearchresult().equals(""))) {
				listentryid = database.getSearchresult();
			} else if ((mywebsite.exists(request, db, request.getServerName(), request.getHeader("Accept-Language"))) && (! mywebsite.get(request, db, request.getServerName(), request.getHeader("Accept-Language"), "default_searchresults_entry").equals(""))) {
				listentryid = mywebsite.get(request, db, request.getServerName(), request.getHeader("Accept-Language"), "default_searchresults_entry");
			} else {
				listentryid = config.get(db, "default_searchresults_entry");
			}
		} else {
			if (listentryid.equals("") && (database != null) && (! database.getViewPage().equals(""))) {
				listentryid = database.getViewPage();
			}
			if (listentryid.equals("")) listentryid = config.get(db, "default_list_entry");
		}
		if (listentrycontent.equals("")) {
			listentrycontent = parse_output_list_entrycontent(listentryid, database);
		}

		liststart = parse_output_list_start(list);
		if ((! liststart.equals("")) && (! request.getParameter(liststart).equals(""))) {
			listfirst = Common.intnumber(request.getParameter(liststart));
			listprevious = Common.intnumber(request.getParameter(liststart)) - Common.intnumber(limit);
		}

		Page listentry = new Page(text);
		Page listorderitemsentry = new Page(text);
		String SQL = "";
		boolean preview = false;
		if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
			if (listentrycontent.equals("")) {
				listentry.read_primary_only(db, config, listentryid, "content", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
				listentry.previewScheduledQueued(session, config, db);
				preview = listentry.previewScheduled(session);
			} else {
				listentry.setContent(listentrycontent);
			}
		}
		if (! preview) {
			if (listentrycontent.equals("")) {
				listentry.read_primary_only(db, config, listentryid, "content_public", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			} else {
				listentry.setContent(listentrycontent);
			}
		}
		preview = false;
		if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
			if (! listorderitemsentryid.equals("")) {
				listorderitemsentry.read_primary_only(db, config, listorderitemsentryid, "content", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			}
			listorderitemsentry.previewScheduledQueued(session, config, db);
			preview = listorderitemsentry.previewScheduled(session);
		}
		if (! preview) {
			if (! listorderitemsentryid.equals("")) {
				listorderitemsentry.read_primary_only(db, config, listorderitemsentryid, "content_public", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
			}
		}
		preview = false;
		if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
			if (Pattern.compile("@@@content[^@]*@@@").matcher(listentry.getContent()).find()) {
//				SQL = "select content, created, updated, published, contentclass, id, version, title, server_filename, image1, image2, image3, file1, file2, file3, link1, link2, link3, summary, description, keywords, author, metainfo, contenttype, contentgroup, page_top, page_up, page_previous, page_next, page_first, page_last, locked_user, users_type, users_group, creators_type, creators_group, developers_type, developers_group, editors_type, editors_group, publishers_type, publishers_group, administrators_type, administrators_group, product_code, product_currency, product_price, product_period, product_stock, product_comment, product_brand, product_colour, product_size, product_info, product_weight, product_volume, product_width, product_height, product_depth from content " + SQLfrom + " where " + SQLwhere + " " + SQLorder;
				SQL = "select * from content " + SQLfrom + " where " + SQLwhere + " " + SQLorder;
			} else {
//				SQL = "select created, updated, published, contentclass, id, version, title, server_filename, image1, image2, image3, file1, file2, file3, link1, link2, link3, summary, description, keywords, author, metainfo, contenttype, contentgroup, page_top, page_up, page_previous, page_next, page_first, page_last, locked_user, users_type, users_group, creators_type, creators_group, developers_type, developers_group, editors_type, editors_group, publishers_type, publishers_group, administrators_type, administrators_group, product_code, product_currency, product_price, product_period, product_stock, product_comment, product_brand, product_colour, product_size, product_info, product_weight, product_volume, product_width, product_height, product_depth from content " + SQLfrom + " where " + SQLwhere + " " + SQLorder;
				SQL = "select * from content " + SQLfrom + " where " + SQLwhere + " " + SQLorder;
			}
		} else {
			if (Pattern.compile("@@@content[^@]*@@@").matcher(listentry.getContent()).find()) {
//				SQL = "select content, created, updated, published, contentclass, id, version, title, server_filename, image1, image2, image3, file1, file2, file3, link1, link2, link3, summary, description, keywords, author, metainfo, contenttype, contentgroup, page_top, page_up, page_previous, page_next, page_first, page_last, locked_user, users_type, users_group, creators_type, creators_group, developers_type, developers_group, editors_type, editors_group, publishers_type, publishers_group, administrators_type, administrators_group, product_code, product_currency, product_price, product_period, product_stock, product_comment, product_brand, product_colour, product_size, product_info, product_weight, product_volume, product_width, product_height, product_depth from content_public " + SQLfrom + " where " + SQLwhere + " " + SQLorder;
				SQL = "select * from content_public " + SQLfrom + " where " + SQLwhere + " " + SQLorder;
			} else {
//				SQL = "select created, updated, published, contentclass, id, version, title, server_filename, image1, image2, image3, file1, file2, file3, link1, link2, link3, summary, description, keywords, author, metainfo, contenttype, contentgroup, page_top, page_up, page_previous, page_next, page_first, page_last, locked_user, users_type, users_group, creators_type, creators_group, developers_type, developers_group, editors_type, editors_group, publishers_type, publishers_group, administrators_type, administrators_group, product_code, product_currency, product_price, product_period, product_stock, product_comment, product_brand, product_colour, product_size, product_info, product_weight, product_volume, product_width, product_height, product_depth from content_public " + SQLfrom + " where " + SQLwhere + " " + SQLorder;
				SQL = "select * from content_public " + SQLfrom + " where " + SQLwhere + " " + SQLorder;
			}
		}
		if (listentrycontent.equals("")) {
			if (getId().equals(listentry.getId())) {
				listentry = new Page(text);
			}
			if (! listentry.getUser()) {
				listentry = new Page(text);
			}
		}

		if (list.startsWith("orders:")) {
			SQL = "select * from orders where " + SQLwhere + " " + SQLorder;
			Order orderlistentries = new Order();
			orderlistentries.records(db, SQL);
			int j = 0;
			while (((limit.equals("")) || (j < listfirst+Common.intnumber(limit)-1)) && orderlistentries.records(db, "")) {
				if (((orderlistentries.getUserId().equals(session_userid)) && (! session_userid.equals(""))) || ((orderlistentries.getCreatedBy().equals(session_username)) && (! session_username.equals(""))) || ((session_username.equals(config.get(db, "superadmin"))) && (! session_username.equals(""))) || (RequireUser.isOrderAdministrator(session, config, db))) {
					j++;
					if (j >= listfirst) {
						String mybody = "";
						Page listentries = new Page(text);
						listentries.setBody(listentry.getContent());
						listentries.parse_output(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, listentries.getId(), table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, "");
						listentries.setDisplayCurrency(db, session.get("currency"), config.get(db, "default_currency"));
						listentries.parse_output_product(server, session, request, response, db, config);
						mybody = listentries.getBody();
						mybody = listentry.adjust_links(session_version, mybody, db, config);
						mybody = parse_output_order(orderlistentries, mybody, db, config, listorderitemsentry, session_version, session_device, session_usersegments, session_usertests, server, session, request, response);
						mybody = listentry.adjust_links(session_version, mybody, db, config);
						content = parse_output_list_item(content, mybody, j, listfirst, liststart, listmerge, listcolumns, listclass, list, distinct, listheader);
					}
				}
			}
			if ((! listcolumns.equals("")) && ((j-listfirst+1) > 0)) {
				if (((j-listfirst+1) % Common.number(listcolumns)) != 0) {
					content.append("\r\n" + "</tr>" + "\r\n");
				}
				content.append("</tbody>" + "\r\n");
				content.append(listfooter);
				content.append("</table>" + "\r\n");
			}
			if (listmerge) {
				if (content.lastIndexOf("</tbody>")>=0) {
					content = new StringBuilder(content.substring(0, content.lastIndexOf("</tbody>")+8) + listfooter + content.substring(content.lastIndexOf("</tbody>")+8));
				}
			}
			if ((! limit.equals("")) && (j >= Common.intnumber(limit))) {
				while (orderlistentries.records(db, "")) {
					if (((orderlistentries.getUserId().equals(session_userid)) && (! session_userid.equals(""))) || ((orderlistentries.getCreatedBy().equals(session_username)) && (! session_username.equals(""))) || ((session_username.equals(config.get(db, "superadmin"))) && (! session_username.equals(""))) || (RequireUser.isOrderAdministrator(session, config, db))) {
						listnext = j+1;
						break;
					}
				}
			}
			if (! limit.equals("")) {
				listcount = Common.intnumber(db.query_value(SQL.replaceAll("select .*? from", "select count(*) from").replaceAll(" order by .*?$", "")));
			}
			orderlistentries.closeRecords(db);
		} else if (list.startsWith("users:")) {
			SQL = "select * from users where " + SQLwhere + " " + SQLorder;
			User userlistentries = new User();
			userlistentries.records(session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, SQL);
			int j = 0;
			while (((limit.equals("")) || (j < listfirst+Common.intnumber(limit)-1)) && userlistentries.records(session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, "")) {
				if (((userlistentries.getId().equals(session_userid)) && (! session_userid.equals(""))) || ((userlistentries.getCreatedBy().equals(session_username)) && (! session_username.equals(""))) || ((session_username.equals(config.get(db, "superadmin"))) && (! session_username.equals(""))) || (RequireUser.isAdministrator(session_username, config.get(db, "superadmin"), session_administrator, db.getDatabase(), session.get("database")))) {
					j++;
					if (j >= listfirst) {
						String mybody = "";
						Page listentries = new Page(text);
						String mycontent = listentry.getContent();
						Session myusersession = new Session();
						Login.userSession(userlistentries, myusersession, db);
						mycontent = parse_output_replace_user(myusersession, mycontent);
						listentries.setBody(mycontent);
						listentries.parse_output(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, listentries.getId(), table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, "");
						mybody = listentries.getBody();
						mybody = listentry.adjust_links(session_version, mybody, db, config);
						content = parse_output_list_item(content, mybody, j, listfirst, liststart, listmerge, listcolumns, listclass, list, distinct, listheader);
					}
				}
			}
			if ((! listcolumns.equals("")) && ((j-listfirst+1) > 0)) {
				if (((j-listfirst+1) % Common.number(listcolumns)) != 0) {
					content.append("\r\n" + "</tr>" + "\r\n");
				}
				content.append("</tbody>" + "\r\n");
				content.append(listfooter);
				content.append("</table>" + "\r\n");
			}
			if (listmerge) {
				if (content.lastIndexOf("</tbody>")>=0) {
					content = new StringBuilder(content.substring(0, content.lastIndexOf("</tbody>")+8) + listfooter + content.substring(content.lastIndexOf("</tbody>")+8));
				}
			}
			if ((! limit.equals("")) && (j >= Common.intnumber(limit))) {
				while (userlistentries.records(session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, "")) {
					if (((userlistentries.getId().equals(session_userid)) && (! session_userid.equals(""))) || ((userlistentries.getCreatedBy().equals(session_username)) && (! session_username.equals(""))) || ((session_username.equals(config.get(db, "superadmin"))) && (! session_username.equals(""))) || (RequireUser.isAdministrator(session_username, config.get(db, "superadmin"), session_administrator, db.getDatabase(), session.get("database")))) {
						listnext = j+1;
						break;
					}
				}
			}
			if (! limit.equals("")) {
				listcount = Common.intnumber(db.query_value(SQL.replaceAll("select .*? from", "select count(*) from").replaceAll(" order by .*?$", "")));
			}
			userlistentries.closeRecords(db);
		} else if ((database != null) &&  ((! database.getId().equals("")) && (! database.getId().equals("0")) && (! database.getId().equals("-1"))) && (! SQLdata.equals(""))) {
			if (database.getUser()) {
				Page dummypage = new Page(text);
				Data listentries = new Data();
				listentries.records(db, SQLdata);
				int j = 0;
				while (((limit.equals("")) || (j < listfirst+Common.intnumber(limit)-1)) && listentries.records(db, "")) {
					listentries.getAccessRestrictions(database, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config);
					if (listentries.getUser()) {
						j++;
						if (j >= listfirst) {
							listentries.adjustContent(database.columns);
							String mybody = listentry.getContent();
							if (mybody.contains("@@@database_id@@@")) mybody = mybody.replaceAll("@@@database_id@@@", database.getId());
							if (mybody.contains("@@@id@@@")) mybody = mybody.replaceAll("@@@id@@@", listentries.getId());
							Iterator mycolumns = database.columns.keySet().iterator();
							while (mycolumns.hasNext()) {
								HashMap mycolumn = (HashMap)database.columns.get("" + mycolumns.next());
								String listentries_content = listentries.getContent("col" + mycolumn.get("id"));
								String myinput = "";
								myinput = "<select name=\"" + mycolumn.get("name") + "\" multiple size=\"" + mycolumn.get("param1") + "\">" + listentries.select_options((""+mycolumn.get("options")), listentries_content) + "</select>";
								mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + ".selectmulti@@@\\E", myinput.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
								if (output_cache != null) {
									if (output_cache.get("@@@" + mycolumn.get("name") + ".selectmulti@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + ".selectmulti@@@", myinput);
									if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".selectmulti@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".selectmulti@@@", myinput);
								}
								if ((""+mycolumn.get("type")).equals("selectmulti")) {
									mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + ".select@@@\\E", myinput.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
									if (output_cache != null) {
										if (output_cache.get("@@@" + mycolumn.get("name") + ".select@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + ".select@@@", myinput);
										if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".select@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".select@@@", myinput);
									}
								} else {
									myinput = "<select name=\"" + mycolumn.get("name") + "\">" + listentries.select_options((""+mycolumn.get("options")), listentries_content) + "</select>";
									mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + ".select@@@\\E", myinput.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
									if (output_cache != null) {
										if (output_cache.get("@@@" + mycolumn.get("name") + ".select@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + ".select@@@", myinput);
										if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".select@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".select@@@", myinput);
									}
								}
								myinput = listentries.radio_options(""+mycolumn.get("name"), (""+mycolumn.get("options")), listentries_content);
								mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + ".radio@@@\\E", myinput.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
								if (output_cache != null) {
									if (output_cache.get("@@@" + mycolumn.get("name") + ".radio@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + ".radio@@@", myinput);
									if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".radio@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".radio@@@", myinput);
								}
								myinput = listentries.checkbox_options(""+mycolumn.get("name"), (""+mycolumn.get("options")), listentries_content);
								mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + ".checkbox@@@\\E", myinput.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
								if (output_cache != null) {
									if (output_cache.get("@@@" + mycolumn.get("name") + ".checkbox@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + ".checkbox@@@", myinput);
									if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".checkbox@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".checkbox@@@", myinput);
								}
								if (((""+mycolumn.get("type")).equals("selectmulti")) || ((""+mycolumn.get("type")).equals("radio")) || ((""+mycolumn.get("type")).equals("checkbox")) || ((""+mycolumn.get("type")).equals("contentclasses")) || ((""+mycolumn.get("type")).equals("contentgroups")) || ((""+mycolumn.get("type")).equals("contenttypes")) || ((""+mycolumn.get("type")).equals("pagegroups")) || ((""+mycolumn.get("type")).equals("pagetypes")) || ((""+mycolumn.get("type")).equals("imagegroups")) || ((""+mycolumn.get("type")).equals("imagetypes")) || ((""+mycolumn.get("type")).equals("imageformats")) || ((""+mycolumn.get("type")).equals("filegroups")) || ((""+mycolumn.get("type")).equals("filetypes")) || ((""+mycolumn.get("type")).equals("fileformats")) || ((""+mycolumn.get("type")).equals("linkgroups")) || ((""+mycolumn.get("type")).equals("linktypes")) || ((""+mycolumn.get("type")).equals("productgroups")) || ((""+mycolumn.get("type")).equals("producttypes")) || ((""+mycolumn.get("type")).equals("versions")) || ((""+mycolumn.get("type")).equals("databases")) || ((""+mycolumn.get("type")).equals("data")) || ((""+mycolumn.get("type")).equals("usernames")) || ((""+mycolumn.get("type")).equals("useremails")) || ((""+mycolumn.get("type")).equals("usergroups")) || ((""+mycolumn.get("type")).equals("usertypes"))) {
									if (Data.csv_options) listentries_content = listentries_content.replaceAll(",", "|");
									mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + ".br" + "@@@\\E", listentries_content.replaceAll("\\|", "<br>").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
									mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + ".p" + "@@@\\E", "<p>" + listentries_content.replaceAll("\\|", "</p><p>").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</p>");
									mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + ".div" + "@@@\\E", "<div>" + listentries_content.replaceAll("\\|", "</div><div>").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</div>");
									mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + ".text" + "@@@\\E", listentries_content.replaceAll("\\|", "\r\n").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
									if (output_cache != null) {
										if (output_cache.get("@@@" + mycolumn.get("name") + ".br@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + ".br@@@", listentries_content.replaceAll("\\|", "<br>"));
										if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".br@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".br@@@", listentries_content.replaceAll("\\|", "<br>"));
										if (output_cache.get("@@@" + mycolumn.get("name") + ".p@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + ".p@@@", "<p>" + listentries_content.replaceAll("\\|", "</p><p>") + "</p>");
										if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".p@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".p@@@", "<p>" + listentries_content.replaceAll("\\|", "</p><p>") + "</p>");
										if (output_cache.get("@@@" + mycolumn.get("name") + ".div@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + ".div@@@", "<div>" + listentries_content.replaceAll("\\|", "</div><div>") + "</div>");
										if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".div@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".div@@@", "<div>" + listentries_content.replaceAll("\\|", "</div><div>") + "</div>");
										if (output_cache.get("@@@" + mycolumn.get("name") + ".text@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + ".text@@@", listentries_content.replaceAll("\\|", "\r\n"));
										if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".text@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".text@@@", listentries_content.replaceAll("\\|", "\r\n"));
									}
								} else if (((""+mycolumn.get("type")).equals("content")) || ((""+mycolumn.get("type")).equals("contents")) || ((""+mycolumn.get("type")).equals("page")) || ((""+mycolumn.get("type")).equals("pages")) || ((""+mycolumn.get("type")).equals("image")) || ((""+mycolumn.get("type")).equals("images")) || ((""+mycolumn.get("type")).equals("file")) || ((""+mycolumn.get("type")).equals("files")) || ((""+mycolumn.get("type")).equals("link")) || ((""+mycolumn.get("type")).equals("links")) || ((""+mycolumn.get("type")).equals("product")) || ((""+mycolumn.get("type")).equals("products")) || ((""+mycolumn.get("type")).equals("element")) || ((""+mycolumn.get("type")).equals("elements"))) {
									if (Data.csv_options) listentries_content = listentries_content.replaceAll(",", "|");
									mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + ".br" + "@@@\\E", listentries_content.replaceAll("\\|", "<br>").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
									mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + ".p" + "@@@\\E", "<p>" + listentries_content.replaceAll("\\|", "</p><p>").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</p>");
									mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + ".div" + "@@@\\E", "<div>" + listentries_content.replaceAll("\\|", "</div><div>").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</div>");
									mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + ".text" + "@@@\\E", listentries_content.replaceAll("\\|", "\r\n").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
									mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + ".titles" + "@@@\\E", dummypage.list_titles(session, request, response, config, db, listentries_content.replaceAll("\\|", ",")).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
									mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + ".links" + "@@@\\E", dummypage.list_links(session, request, response, config, db, listentries_content.replaceAll("\\|", ",")).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
									if (output_cache != null) {
										if (output_cache.get("@@@" + mycolumn.get("name") + ".br@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + ".br@@@", listentries_content.replaceAll("\\|", "<br>"));
										if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".br@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".br@@@", listentries_content.replaceAll("\\|", "<br>"));
										if (output_cache.get("@@@" + mycolumn.get("name") + ".p@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + ".p@@@", "<p>" + listentries_content.replaceAll("\\|", "</p><p>") + "</p>");
										if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".p@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".p@@@", "<p>" + listentries_content.replaceAll("\\|", "</p><p>") + "</p>");
										if (output_cache.get("@@@" + mycolumn.get("name") + ".div@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + ".div@@@", "<div>" + listentries_content.replaceAll("\\|", "</div><div>") + "</div>");
										if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".div@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".div@@@", "<div>" + listentries_content.replaceAll("\\|", "</div><div>") + "</div>");
										if (output_cache.get("@@@" + mycolumn.get("name") + ".text@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + ".text@@@", listentries_content.replaceAll("\\|", "\r\n"));
										if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".text@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".text@@@", listentries_content.replaceAll("\\|", "\r\n"));
										if (output_cache.get("@@@" + mycolumn.get("name") + ".titles@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + ".titles@@@", dummypage.list_titles(session, request, response, config, db, listentries_content.replaceAll("\\|", ",")));
										if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".titles@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".titles@@@", dummypage.list_titles(session, request, response, config, db, listentries_content.replaceAll("\\|", ",")));
										if (output_cache.get("@@@" + mycolumn.get("name") + ".links@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + ".links@@@", dummypage.list_links(session, request, response, config, db, listentries_content.replaceAll("\\|", ",")));
										if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".links@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".links@@@", dummypage.list_links(session, request, response, config, db, listentries_content.replaceAll("\\|", ",")));
									}
								} else if (((""+mycolumn.get("type")).equals("datetime")) || ((""+mycolumn.get("type")).equals("created")) || ((""+mycolumn.get("type")).equals("updated"))) {
									Pattern p = Pattern.compile("\\Q@@@" + mycolumn.get("name") + "\\E:format=([^@]*?)@@@");
									Matcher m = p.matcher(mybody);
									while (m.find()) {
										String dateformat = "" + m.group(1);
										Date date = Common.strtodate("" + mycolumn.get("param1"), listentries_content);
										if (date != null) {
											mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + ":format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
											if (output_cache != null) {
												if (output_cache.get("@@@" + mycolumn.get("name") + ":format=" + dateformat + "@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + ":format=" + dateformat + "@@@", Common.strftime(dateformat, date));
												if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + ":format=" + dateformat + "@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + ":format=" + dateformat + "@@@", Common.strftime(dateformat, date));
											}
										} else {
											mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + ":format=" + dateformat + "@@@\\E", "");
											if (output_cache != null) {
												if (output_cache.get("@@@" + mycolumn.get("name") + ":format=" + dateformat + "@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + ":format=" + dateformat + "@@@", "");
												if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + ":format=" + dateformat + "@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + ":format=" + dateformat + "@@@", "");
											}
										}
										m.reset(mybody);
									}
								}
								mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + ".html@@@\\E", listentries_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
								mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + ".script@@@\\E", listentries_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r", "\\\\\\\\r").replaceAll("\n", "\\\\\\\\n").replaceAll("'", "\\\\\\\\'"));
								mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + ".text@@@\\E", listentries_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("<br>", "\r\n").replaceAll("<p>", "\r\n\r\n").replaceAll("</p>", ""));
								mybody = mybody.replaceAll("\\Q@@@" + mycolumn.get("name") + "@@@\\E", listentries_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
								mybody = mybody.replaceAll("\\Q@@@" + (""+mycolumn.get("name")).replaceAll(" ", "%20") + "@@@\\E", listentries_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
								mybody = mybody.replaceAll("\\Q@@@" + (""+mycolumn.get("name")).replaceAll(" ", "\\+") + "@@@\\E", listentries_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
								if (output_cache != null) {
									if (output_cache.get("@@@" + mycolumn.get("name") + ".html@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + ".html@@@", listentries_content.replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
									if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".html@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".html@@@", listentries_content.replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
									if (output_cache.get("@@@" + mycolumn.get("name") + ".script@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + ".script@@@", listentries_content.replaceAll("\r", "\\\\\\\\r").replaceAll("\n", "\\\\\\\\n").replaceAll("'", "\\\\\\\\'"));
									if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".script@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".script@@@", listentries_content.replaceAll("\r", "\\\\\\\\r").replaceAll("\n", "\\\\\\\\n").replaceAll("'", "\\\\\\\\'"));
									if (output_cache.get("@@@" + mycolumn.get("name") + ".text@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + ".text@@@", listentries_content.replaceAll("<br>", "\r\n").replaceAll("<p>", "\r\n\r\n").replaceAll("</p>", ""));
									if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".text@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + ".text@@@", listentries_content.replaceAll("<br>", "\r\n").replaceAll("<p>", "\r\n\r\n").replaceAll("</p>", ""));
									if (output_cache.get("@@@" + mycolumn.get("name") + "@@@") == null) output_cache.put("@@@" + mycolumn.get("name") + "@@@", listentries_content);
									if (output_cache.get("@@@" + database.getTitle() + "." + mycolumn.get("name") + "@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + mycolumn.get("name") + "@@@", listentries_content);
									if (output_cache.get("@@@" + (""+mycolumn.get("name")).replaceAll(" ", "%20") + "@@@") == null) output_cache.put("@@@" + (""+mycolumn.get("name")).replaceAll(" ", "%20") + "@@@", listentries_content);
									if (output_cache.get("@@@" + database.getTitle() + "." + (""+mycolumn.get("name")).replaceAll(" ", "%20") + "@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + (""+mycolumn.get("name")).replaceAll(" ", "%20") + "@@@", listentries_content);
									if (output_cache.get("@@@" + (""+mycolumn.get("name")).replaceAll(" ", "\\+") + "@@@") == null) output_cache.put("@@@" + (""+mycolumn.get("name")).replaceAll(" ", "\\+") + "@@@", listentries_content);
									if (output_cache.get("@@@" + database.getTitle() + "." + (""+mycolumn.get("name")).replaceAll(" ", "\\+") + "@@@") == null) output_cache.put("@@@" + database.getTitle() + "." + (""+mycolumn.get("name")).replaceAll(" ", "\\+") + "@@@", listentries_content);
								}
							}
							mybody = dummypage.adjust_links(session_version, mybody, db, config);
							if (! listcolumns.equals("")) {
								if (! listclass.equals("")) {
									if (j == 1) {
										content.append("\r\n" + "<table class=\"" + listclass + "\">");
										content.append(listheader);
										content.append("\r\n" + "<tbody>");
									}
									if ((j % Common.number(listcolumns)) == 1) {
										content.append("\r\n" + "<tr class=\"" + listclass + "\">");
									}
									content.append("\r\n" + "<td class=\"" + listclass + "\">");
								} else {
									if (j == 1) {
										content.append("\r\n" + "<table>");
										content.append(listheader);
										content.append("\r\n" + "<tbody>");
									}
									if ((j % Common.number(listcolumns)) == 1) {
										content.append("\r\n" + "<tr>");
									}
									content.append("\r\n" + "<td>");
								}
							}
							if (listmerge) {
								if ((j == 1) && (mybody.contains("<tbody"))) {
									mybody = mybody.substring(0, mybody.indexOf("<tbody")) + listheader + mybody.substring(mybody.indexOf("<tbody"));
								}
								int lastindex = content.lastIndexOf("</tr>");
								if ((mybody.contains("<tr")) && (lastindex>=0)) {
									content = new StringBuilder(content.substring(0, lastindex+5));
									mybody = mybody.substring(mybody.indexOf("<tr"));
								}
							}
							content.append(mybody);
							if (! listcolumns.equals("")) {
								content.append("</td>");
								if ((j % Common.number(listcolumns)) == 0) {
									content.append("\r\n" + "</tr>");
								}
							}
						}
					}
				}
				if ((! listcolumns.equals("")) && (j > 0)) {
					if ((j % Common.number(listcolumns)) != 0) {
						content.append("\r\n" + "</tr>" + "\r\n");
					}
					content.append("</tbody>" + "\r\n");
					content.append(listfooter);
					content.append("</table>" + "\r\n");
				}
				if (listmerge) {
					if (content.lastIndexOf("</tbody>")>=0) {
						content = new StringBuilder(content.substring(0, content.lastIndexOf("</tbody>")+8) + listfooter + content.substring(content.lastIndexOf("</tbody>")+8));
					}
				}
				if ((! limit.equals("")) && (j >= Common.intnumber(limit))) {
					while (listentries.records(db, "")) {
						if (listentries.getUser()) {
							listnext = j+1;
							break;
						}
					}
				}
				if (! limit.equals("")) {
					listcount = Common.intnumber(db.query_value(SQLdata.replaceAll("select .*? from", "select count(*) from").replaceAll(" order by .*?$", "")));
				}
				listentries.closeRecords(db);
			}
		} else if (((! _METAINFO_) && (SQLorder.contains("metainfo_"))) || (SQLorder.contains("productinfo_"))) {
			HashMap<String,String> listentriesids = new HashMap<String,String>();
			String mysql = SQL.substring(0, SQL.indexOf(" order by "));
			String[] myorderattributes = (SQLorder.substring(10).replaceAll(" (asc|desc)$", "") + ",id").split(",");
			Page listentries = new Page(text);
			listentries.records(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, mysql);
			while (listentries.records(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, "")) {
				if (listentries.getUser()) {
					String mysortkey = "";
					for (int i=0; i<myorderattributes.length; i++) {
						if (i>0) mysortkey += "          ";
						if (myorderattributes[i].equals("id")) {
							mysortkey += "00000000000000000000".substring(listentries.get(myorderattributes[i]).length()) + listentries.get(myorderattributes[i]);
						} else {
							mysortkey += listentries.get(myorderattributes[i]);
						}
					}
					listentriesids.put(listentries.getId(), mysortkey);
				}
			}
			LinkedHashMap<String,String> sortedlistentriesids;
			if (! SQLorder.endsWith(" desc")) {
				sortedlistentriesids = Common.LinkedHashMapSortedByValue(listentriesids, true);
			} else {
				sortedlistentriesids = Common.LinkedHashMapSortedByValue(listentriesids, false);
			}
			int j = 0;
			Iterator i = sortedlistentriesids.keySet().iterator();
			while (((limit.equals("")) || (j < listfirst+Common.intnumber(limit)-1)) && i.hasNext()) {
				String myid = "" + i.next();
				String mybody = "";
				listentries = new Page(text);
//				listentries.read(listentries.getId(), table, column, version);
				listentries.read_primary_only(db, config, myid, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
				if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
					listentries.previewScheduledQueued(session, config, db);
					listentries.previewScheduled(session);
				}
				if (listentries.getUser()) {
					j++;
					if (j >= listfirst) {
						listentries.setBody(listentry.getContent().replaceAll("@@@id@@@", listentries.getId()));
						listentries.parse_output(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, listentries.getId(), table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, listentries.getId());
						listentries.setDisplayCurrency(db, session.get("currency"), config.get(db, "default_currency"));
						listentries.parse_output_product(server, session, request, response, db, config);
						mybody = listentries.getBody();
						mybody = listentry.adjust_links(session_version, mybody, db, config);
						content = parse_output_list_item(content, mybody, j, listfirst, liststart, listmerge, listcolumns, listclass, list, distinct, listheader);
					}
				}
			}
			if ((! listcolumns.equals("")) && ((j-listfirst+1) > 0)) {
				if (((j-listfirst+1) % Common.number(listcolumns)) != 0) {
					content.append("\r\n" + "</tr>" + "\r\n");
				}
				content.append("</tbody>" + "\r\n");
				content.append(listfooter);
				content.append("</table>" + "\r\n");
			}
			if (listmerge) {
				if (content.lastIndexOf("</tbody>")>=0) {
					content = new StringBuilder(content.substring(0, content.lastIndexOf("</tbody>")+8) + listfooter + content.substring(content.lastIndexOf("</tbody>")+8));
				}
			}
			if ((! limit.equals("")) && (j >= Common.intnumber(limit))) {
				while (i.hasNext()) {
					String myid = "" + i.next();
					listentries = new Page(text);
//					listentries.read(listentries.getId(), table, column, version);
					listentries.read_primary_only(db, config, myid, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
						listentries.previewScheduledQueued(session, config, db);
						listentries.previewScheduled(session);
					}
					if (listentries.getUser()) {
						listnext = j+1;
						break;
					}
				}
			}
			if (! limit.equals("")) {
				listcount = Common.intnumber(db.query_value(mysql.replaceAll("select .*? from", "select count(*) from").replaceAll(" order by .*?$", "")));
			}
		} else {
			Page listentries = new Page(text);
//			Content listentries = new Content(text);
			listentries.records(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, SQL);
			int j = 0;
			while (((limit.equals("")) || (j < listfirst+Common.intnumber(limit)-1)) && listentries.records(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, "")) {
				if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
					listentries.previewScheduledQueued(session, config, db);
					listentries.previewScheduled(session);
				}
				if (listentries.getUser()) {
					j++;
					if (j >= listfirst) {
						String mybody = "";
						if (liststart.equals("")) {
							listentries.setBody(listentry.getContent().replaceAll("@@@id@@@", listentries.getId()));
							listentries.parse_output(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, listentries.getId(), table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, listentries.getId());
							listentries.setDisplayCurrency(db, session.get("currency"), config.get(db, "default_currency"));
							listentries.parse_output_product(server, session, request, response, db, config);
							mybody = listentries.getBody();
							mybody = listentry.adjust_links(session_version, mybody, db, config);
						} else {
							Page fulllistentry = new Page(text);
//							fulllistentry.read(listentries.getId(), table, column, version);
							fulllistentry.read_primary_only(db, config, listentries.getId(), table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
							if (fulllistentry.getUser()) {
								fulllistentry.setBody(listentry.getContent());
								fulllistentry.parse_output(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, fulllistentry.getId(), table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, fulllistentry.getId());
								fulllistentry.setDisplayCurrency(db, session.get("currency"), config.get(db, "default_currency"));
								fulllistentry.parse_output_product(server, session, request, response, db, config);
								mybody = fulllistentry.getBody();
								mybody = listentry.adjust_links(session_version, mybody, db, config);
							} else {
								mybody = "";
								j--;
							}
						}
						content = parse_output_list_item(content, mybody, j, listfirst, liststart, listmerge, listcolumns, listclass, list, distinct, listheader);
					}
				}
			}
			if ((! listcolumns.equals("")) && ((j-listfirst+1) > 0)) {
				if (((j-listfirst+1) % Common.number(listcolumns)) != 0) {
					content.append("\r\n" + "</tr>" + "\r\n");
				}
				content.append("</tbody>" + "\r\n");
				content.append(listfooter);
				content.append("</table>" + "\r\n");
			}
			if (listmerge) {
				if (content.lastIndexOf("</tbody>")>=0) {
					content = new StringBuilder(content.substring(0, content.lastIndexOf("</tbody>")+8) + listfooter + content.substring(content.lastIndexOf("</tbody>")+8));
				}
			}
			if ((! limit.equals("")) && (j >= Common.intnumber(limit))) {
				while (listentries.records(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, "")) {
					if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
						listentries.previewScheduledQueued(session, config, db);
						listentries.previewScheduled(session);
					}
					if (listentries.getUser()) {
						listnext = j+1;
						break;
					}
				}
			}
			if (! limit.equals("")) {
				listcount = Common.intnumber(db.query_value(SQL.replaceAll("select .*? from", "select count(*) from").replaceAll(" order by .*?$", "")));
			}
			listentries.closeRecords(db);
		}
		if (! limit.equals("")) {
			listlast = listcount - (listcount % Common.intnumber(limit)) + 1;
		}
		parse_output_list_link_start = liststart;
		parse_output_list_link_limit = Common.intnumber(limit);
		if (listprevious > 0) {
			parse_output_list_link_first = 1;
		} else {
			parse_output_list_link_first = 0;
		}
		parse_output_list_link_previous = listprevious;
		parse_output_list_link_next = listnext;
		if (listnext > 0) {
			parse_output_list_link_last = listlast;
		} else {
			parse_output_list_link_last = 0;
		}
		parse_output_list_link_count = listcount;
		if ((""+content).equals("")) {
			if (Pattern.compile("^[0-9]+$").matcher(none).find()) {
				content.append("@@@include:" + none + ".content@@@");
			} else {
				content.append(none);
			}
		}
		return "" + content;
	}



//	public StringBuilder parse_output_list_item(StringBuilder content, String mybody, int j, int listfirst, String liststart, boolean listmerge, String listcolumns, String listclass, String list) throws Exception {
//		return parse_output_list_item(content, mybody, j, listfirst, liststart, listmerge, listcolumns, listclass, list, "");
//	}
	public StringBuilder parse_output_list_item(StringBuilder content, String mybody, int j, int listfirst, String liststart, boolean listmerge, String listcolumns, String listclass, String list, String distinct) throws Exception {
		return parse_output_list_item(content, mybody, j, listfirst, liststart, listmerge, listcolumns, listclass, list, distinct, "");
	}
	public StringBuilder parse_output_list_item(StringBuilder content, String mybody, int j, int listfirst, String liststart, boolean listmerge, String listcolumns, String listclass, String list, String distinct, String listheader) throws Exception {
		if (! listcolumns.equals("")) {
			if (! listclass.equals("")) {
				if ((j-listfirst+1) == 1) {
					content.append("\r\n" + "<table class=\"" + listclass + "\">");
					content.append(listheader);
					content.append("\r\n" + "<tbody>");
				}
				if (((j-listfirst+1) % Common.number(listcolumns)) == 1) {
					content.append("\r\n" + "<tr class=\"" + listclass + "\">");
				}
				content.append("\r\n" + "<td class=\"" + listclass + "\">");
			} else {
				if ((j-listfirst+1) == 1) {
					content.append("\r\n" + "<table>");
					content.append(listheader);
					content.append("\r\n" + "<tbody>");
				}
				if (((j-listfirst+1) % Common.number(listcolumns)) == 1) {
					content.append("\r\n" + "<tr>");
				}
				content.append("\r\n" + "<td>");
			}
		}
		if (listmerge) {
			if (((j-listfirst+1) == 1) && (mybody.contains("<tbody"))) {
				mybody = mybody.substring(0, mybody.indexOf("<tbody")) + listheader + mybody.substring(mybody.indexOf("<tbody"));
			}
			int lastindex = content.lastIndexOf("</tr>");
			if ((mybody.contains("<tr")) && (lastindex>=0)) {
				content = new StringBuilder(content.substring(0, lastindex+5));
				mybody = mybody.substring(mybody.indexOf("<tr"));
			}
		}
		if (! distinct.equals("")) {
			if ((""+content).contains(mybody)) {
				mybody = "";
			}
		}
		try {
			content.append(mybody);
		} catch (Exception e) {
			System.out.println("ERROR:Page:parse_output_list_item:" + list + ":" + e);
		}
		if (! listcolumns.equals("")) {
			content.append("</td>");
			if (((j-listfirst+1) % Common.number(listcolumns)) == 0) {
				content.append("\r\n" + "</tr>");
			}
		}
		return content;
	}



	public String parse_output_list_start(String list) {
		String start = "";
		String[] listparts = list.split(":");
		for (int i=0; i<listparts.length; i++) {
			String listpart = listparts[i];
			Matcher startMatches = Pattern.compile("^start=([a-zA-Z][a-zA-Z0-9\\w]*?)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
			if (startMatches.find()) {
				start = "" + startMatches.group(1);
			}
		}
		return start;
	}



	public String parse_output_list_first_link(String liststart, String listformat, int listfirst, Request request) {
		String content = "";
		if (listfirst > 0) {
//			String url = request.getQueryString();
			String url = "";
			Enumeration parameters = request.getParameterNames();
			while (parameters.hasMoreElements()) {
				String key = html.encodeHtmlEntities("" + parameters.nextElement());
				String value = request.getParameter(key);
				if (! url.equals("")) {
					url += "&";
				}
				url += URLEncoder.encode(key) + "=" + URLEncoder.encode(value);
			}
			url = url.replaceAll("^\\Q" + liststart + "\\E=[0-9]*", "");
			url = url.replaceAll("&\\Q" + liststart + "\\E=[0-9]*", "");
			if (listfirst > 1) {
				if (! url.equals("")) {
					url += "&";
				}
				url += liststart + "=" + listfirst;
			}
			content = parse_output_list_link_content(listformat, url);
		}
		return content;
	}



	public String parse_output_list_previous_link(String liststart, String listformat, int listprevious, Request request) {
		String content = "";
		if (listprevious > 0) {
//			String url = request.getQueryString();
			String url = "";
			Enumeration parameters = request.getParameterNames();
			while (parameters.hasMoreElements()) {
				String key = html.encodeHtmlEntities("" + parameters.nextElement());
				String value = request.getParameter(key);
				if (! url.equals("")) {
					url += "&";
				}
				url += URLEncoder.encode(key) + "=" + URLEncoder.encode(value);
			}
			url = url.replaceAll("^\\Q" + liststart + "\\E=[0-9]*", "");
			url = url.replaceAll("&\\Q" + liststart + "\\E=[0-9]*", "");
			if (listprevious > 1) {
				if (! url.equals("")) {
					url += "&";
				}
				url += liststart + "=" + listprevious;
			}
			content = parse_output_list_link_content(listformat, url);
		}
		return content;
	}



	public String parse_output_list_next_link(String liststart, String listformat, int listnext, Request request) {
		String content = "";
		if (listnext > 0) {
//			String url = request.getQueryString();
			String url = "";
			Enumeration parameters = request.getParameterNames();
			while (parameters.hasMoreElements()) {
				String key = html.encodeHtmlEntities("" + parameters.nextElement());
				String value = request.getParameter(key);
				if (! url.equals("")) {
					url += "&";
				}
				url += URLEncoder.encode(key) + "=" + URLEncoder.encode(value);
			}
			url = url.replaceAll("^\\Q" + liststart + "\\E=[0-9]*", "");
			url = url.replaceAll("&\\Q" + liststart + "\\E=[0-9]*", "");
			if (listnext > 1) {
				if (! url.equals("")) {
					url += "&";
				}
				url += liststart + "=" + listnext;
			}
			content = parse_output_list_link_content(listformat, url);
		}
		return content;
	}



	public String parse_output_list_last_link(String liststart, String listformat, int listlast, Request request) {
		String content = "";
		if (listlast > 0) {
//			String url = request.getQueryString();
			String url = "";
			Enumeration parameters = request.getParameterNames();
			while (parameters.hasMoreElements()) {
				String key = html.encodeHtmlEntities("" + parameters.nextElement());
				String value = request.getParameter(key);
				if (! url.equals("")) {
					url += "&";
				}
				url += URLEncoder.encode(key) + "=" + URLEncoder.encode(value);
			}
			url = url.replaceAll("^\\Q" + liststart + "\\E=[0-9]*", "");
			url = url.replaceAll("&\\Q" + liststart + "\\E=[0-9]*", "");
			if (listlast > 1) {
				if (! url.equals("")) {
					url += "&";
				}
				url += liststart + "=" + listlast;
			}
			content = parse_output_list_link_content(listformat, url);
		}
		return content;
	}



	public String parse_output_list_paged_links(String liststart, String listformat, int listlimit, int listcount, Request request) {
		String content = "";
		if ((listlimit > 0) && (listcount > 0)) {
			content += "<span class=\"WCMpaged\">";
			for (int listpaged = 1; listpaged <= listcount; listpaged += listlimit) {
				int listpage = Math.round((listpaged-1)/listlimit)+1;
//				String url = request.getQueryString();
				String url = "";
				Enumeration parameters = request.getParameterNames();
				while (parameters.hasMoreElements()) {
					String key = html.encodeHtmlEntities("" + parameters.nextElement());
					String value = request.getParameter(key);
					if (! url.equals("")) {
						url += "&";
					}
					url += URLEncoder.encode(key) + "=" + URLEncoder.encode(value);
				}
				url = url.replaceAll("^\\Q" + liststart + "\\E=[0-9]*", "");
				url = url.replaceAll("&\\Q" + liststart + "\\E=[0-9]*", "");
				if (listpaged > 1) {
					if (! url.equals("")) {
						url += "&";
					}
					url += liststart + "=" + listpaged;
				}
				if (request.getParameter(liststart).equals(""+listpaged)) {
					content += parse_output_list_link_content(listformat + listpage, url, "WCMselected");
				} else {
					content += parse_output_list_link_content(listformat + listpage, url, "");
				}
			}
			content += "</span>";
		}
		return content;
	}



	public String parse_output_list_link_content(String listformat, String url) {
		return parse_output_list_link_content(listformat, url, "");
	}
	public String parse_output_list_link_content(String listformat, String url, String classname) {
		String content = "";
		String listtext = "";
		String listimage = "";
		String listbutton = "";
		String[] listparts = listformat.split(":");
		for (int i=0; i<listparts.length; i++) {
			String listpart = listparts[i];
			Matcher textMatches = Pattern.compile("^text=(.+?)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
			Matcher imageMatches = Pattern.compile("^image=(.+?)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
			Matcher buttonMatches = Pattern.compile("^button=(.+?)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
			if (textMatches.find()) {
				listtext = "" + textMatches.group(1);
			} else if (imageMatches.find()) {
				listimage = "" + imageMatches.group(1);
			} else if (buttonMatches.find()) {
				listbutton = "" + buttonMatches.group(1);
			}
		}
		if (! listtext.equals("")) {
			if (! classname.equals("")) {
				content = "<a class=\"" + classname + "\" href=\"?" + url + "\">" + listtext + "</a>";
			} else {
				content = "<a href=\"?" + url + "\">" + listtext + "</a>";
			}
		} else if (! listimage.equals("")) {
			if (! classname.equals("")) {
				content = "<a class=\"" + classname + "\" href=\"?" + url + "\">" + "<img class=\"" + classname + "\" border=\"0\" src=\"" + listimage + "\">" + "</a>";
			} else {
				content = "<a href=\"?" + url + "\">" + "<img border=\"0\" src=\"" + listimage + "\">" + "</a>";
			}
		} else if (! listbutton.equals("")) {
			if (! classname.equals("")) {
				content = "<input class=\"" + classname + "\" type=\"button\" onclick=\"document.location.href='?" + url + "'\" value=\"" + listbutton + "\">";
			} else {
				content = "<input type=\"button\" onclick=\"document.location.href='?" + url + "'\" value=\"" + listbutton + "\">";
			}
		}
		return content;
	}



	public Databases parse_output_list_database(String list, Request myrequest, Response myresponse, Session mysession, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration myconfig) {
		Databases databases = new Databases(text);
		boolean checkaccesspermissions = false;
		String database = "";
		String[] listparts = list.split(":");
		for (int i=0; i<listparts.length; i++) {
			String listpart = listparts[i];
			Matcher databaseMatches = Pattern.compile("^database=([- ,_a-zA-Z0-9\\w]+?)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
			if (listpart.equals("searchresults")) {
				database = "" + myrequest.getParameter("database");
				checkaccesspermissions = true;
			} else if (databaseMatches.find()) {
				database = "" + databaseMatches.group(1);
			}
		}
		if (! database.equals("")) {
			databases.readTitle(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, myconfig, database);
		}
		if (checkaccesspermissions) {
			boolean accesspermission = RequireUser.checkDataUserAccessRestrictions(text, true, databases, mysession, myrequest, myresponse, myconfig, db);
			if (! accesspermission) databases = new Databases(text);
		}
		return databases;
	}



	public HashMap parse_output_list_data_column(Databases database, String list) {
		HashMap column = null;
		String[] listparts = list.split(":");
		for (int i=0; i<listparts.length; i++) {
			String listpart = listparts[i];
			Matcher dataMatches = Pattern.compile("^data=([- ,_a-zA-Z0-9\\w]+?)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
			if (dataMatches.find()) {
				column = parse_output_database_column(database, "" + dataMatches.group(1));
			}
		}
		return column;
	}



	public String parse_output_list_data(String list, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, Databases database) {
		return parse_output_list_data(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, database);
	}
	public String parse_output_list_data(String list, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, Databases database) {
		String SQL = "";
		String SQLwhere = parse_output_list_data_where(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, database);
		String SQLorder = parse_output_list_data_order(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, database);
		SQL = "select * from data" + database.getId();
		if (! SQLwhere.equals("")) {
			SQL = SQL + " where " + SQLwhere;
		}
		if (! SQLorder.equals("")) {
			SQL = SQL + SQLorder;
		}
		return SQL;
	}



	public String parse_output_list_data_where(String list, Request myrequest, Session mysession, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration myconfig, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, Databases database) {
		return parse_output_list_data_where(list, myrequest, mysession, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, myconfig, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, database);
	}
	public String parse_output_list_data_where(String list, Request myrequest, Session mysession, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration myconfig, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, Databases database) {
		String SQLwhere = "";
		Data mydata = new Data();
		list = parse_output_replace_params_unescape(list);
		String[] listparts = list.split(":");
		for (int i=0; i<listparts.length; i++) {
			while (Pattern.compile("^[-0-9]+", Pattern.CASE_INSENSITIVE).matcher(listparts[i]).find()) {
				listparts[i-1] += ":" + listparts[i];
				for (int j=i+1; j<listparts.length; j++) {
					listparts[j-1] = listparts[j];
				}
				listparts[listparts.length-1] = "";
			}
		}
		for (int i=0; i<listparts.length; i++) {
			String listpart = listparts[i];
//			Matcher columnMatches = Pattern.compile("^([- ,_a-zA-Z0-9\\w]+?)=([-+ ,_%a-zA-Z0-9\\w]*?)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
			Matcher columnMatches = Pattern.compile("^([- ,_a-zA-Z0-9\\w]+?)=(.*?)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
			Matcher columnNotMatches = Pattern.compile("^([- ,_a-zA-Z0-9\\w]+?)!=(.*?)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
//			Matcher columnCompareMatches = Pattern.compile("^([- ,_a-zA-Z0-9\\w]+?)(<|>|<=|>=|&lt;|&gt;|&lt;=|&gt;=)([-+ ,_%a-zA-Z0-9\\w]*?)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
			Matcher columnCompareMatches = Pattern.compile("^([- ,_a-zA-Z0-9\\w]+?)(<|>|<=|>=|&lt;|&gt;|&lt;=|&gt;=)([^&<>=]*?)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
//			Matcher columninMatches = Pattern.compile("^([- ,_a-zA-Z0-9\\w]+?) in ([- ,_a-zA-Z0-9\\w]+?)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
			Matcher columninMatches = Pattern.compile("^([- ,_a-zA-Z0-9\\w]+?) in (.+?)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
			Matcher idMatches = Pattern.compile("^id=(.*?)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
			Matcher idNotMatches = Pattern.compile("^id!=(.*?)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
			Matcher idInMatches = Pattern.compile("^id in ([0-9,]+?)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
			if (listpart.equals("searchresults")) {
				if (! SQLwhere.equals("")) {
					SQLwhere += " and ";
				}
				SQLwhere += parse_output_list_where_searchresults(list, myrequest, mysession, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, myconfig, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, database);
			} else if (listpart.startsWith("database=")) {
				// ignore
			} else if (listpart.startsWith("data=")) {
				// ignore
			} else if (listpart.startsWith("limit=")) {
				// ignore
			} else if (listpart.startsWith("none=")) {
				// ignore
			} else if (listpart.startsWith("entry=")) {
				// ignore
			} else if (listpart.startsWith("order=")) {
				// ignore
			} else if (idMatches.find()) {
				if (! SQLwhere.equals("")) {
					SQLwhere += " and ";
				}
				SQLwhere += "id=" + Common.integer("" + idMatches.group(1)) + "";
			} else if (idNotMatches.find()) {
				if (! SQLwhere.equals("")) {
					SQLwhere += " and ";
				}
				SQLwhere += "id<>" + Common.integer("" + idNotMatches.group(1)) + "";
			} else if (idInMatches.find()) {
				if (! SQLwhere.equals("")) {
					SQLwhere += " and ";
				}
				SQLwhere += "id in (" + db.quote_csv("" + idInMatches.group(1)) + ")";
			} else if (columnMatches.find()) {
				HashMap column =  parse_output_database_column(database, "" + columnMatches.group(1));
				String database_columnname = Common.SQL_clean("" + columnMatches.group(1));
				String database_columnvalue = Common.SQL_clean("" + columnMatches.group(2));
				database_columnvalue = parse_output_replace_params_escape(database_columnvalue);
				if ((column != null) && (column.get("id") != null) && (! column.get("id").equals(""))) {
					if ((column.get("type").equals("selectmulti")) || (column.get("type").equals("radio")) || (column.get("type").equals("checkbox")) || (column.get("type").equals("contents")) || (column.get("type").equals("contentclasses")) || (column.get("type").equals("contentgroups")) || (column.get("type").equals("contenttypes")) || (column.get("type").equals("pagegroups")) || (column.get("type").equals("pagetypes")) || (column.get("type").equals("imagegroups")) || (column.get("type").equals("imagetypes")) || (column.get("type").equals("imageformats")) || (column.get("type").equals("filegroups")) || (column.get("type").equals("filetypes")) || (column.get("type").equals("fileformats")) || (column.get("type").equals("linkgroups")) || (column.get("type").equals("linktypes")) || (column.get("type").equals("productgroups")) || (column.get("type").equals("producttypes")) || (column.get("type").equals("versions")) || (column.get("type").equals("databases")) || (column.get("type").equals("data")) || (column.get("type").equals("usernames")) || (column.get("type").equals("useremails")) || (column.get("type").equals("usergroups")) || (column.get("type").equals("usertypes"))) {
						if (! SQLwhere.equals("")) {
							SQLwhere += " and ";
						}
						SQLwhere += "(";
						SQLwhere += "col" + column.get("id") + " like " + db.quote(database_columnvalue);
						SQLwhere += " or ";
						SQLwhere += "col" + column.get("id") + " like " + db.quote(database_columnvalue + "|%");
						SQLwhere += " or ";
						SQLwhere += "col" + column.get("id") + " like " + db.quote("%|" + database_columnvalue + "|%");
						SQLwhere += " or ";
						SQLwhere += "col" + column.get("id") + " like " + db.quote("%|" + database_columnvalue);
						if (Data.csv_options) {
							SQLwhere += " or ";
							SQLwhere += "col" + column.get("id") + " like " + db.quote(database_columnvalue + ",%");
							SQLwhere += " or ";
							SQLwhere += "col" + column.get("id") + " like " + db.quote("%," + database_columnvalue + ",%");
							SQLwhere += " or ";
							SQLwhere += "col" + column.get("id") + " like " + db.quote("%," + database_columnvalue);
						}
						SQLwhere += ")";
					} else if (column.get("type").equals("number")) {
						if (! SQLwhere.equals("")) {
							SQLwhere += " and ";
						}
						SQLwhere += "col" + column.get("id") + " = " + (""+Common.number(database_columnvalue)).replaceAll("[.,]0+$", "");
					} else if ((column.get("type").equals("datetime")) || (column.get("type").equals("created")) || (column.get("type").equals("updated"))) {
						Matcher columnPeriodMatches = Pattern.compile("^\\Q" + database_columnname + "\\E(<=|>=|=<|=>|&lt;=|&gt;=|=&lt;|=&gt;|=|<|>|&lt;|&gt;)([-+][0-9]+?)(secs|sec|mins|min|hours|hour|days|day|weeks|week|months|month|years|year)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
						if (columnPeriodMatches.find()) {
							SQLwhere = parse_output_list_where_period(db, SQLwhere, "col" + column.get("id"), columnPeriodMatches);
						} else {
							if (! SQLwhere.equals("")) {
								SQLwhere += " and ";
							}
							SQLwhere += "col" + column.get("id") + " like " + db.quote(database_columnvalue + "%");
						}
					} else {
						if (! SQLwhere.equals("")) { //else para comparar colunas do type plain text usando operador '=' na list e substituindo por ilike
							SQLwhere += " and ";
						}
						if (database_columnvalue.equals("")) {
							SQLwhere += db.is_blank("col" + column.get("id"));
						} else {
							SQLwhere += "col" + column.get("id") + " ilike " + db.quote(database_columnvalue); //operador ilike para case insensitive
						}
					}
				}
			} else if (columnNotMatches.find()) {
				HashMap column =  parse_output_database_column(database, "" + columnNotMatches.group(1));
				String database_columnname = Common.SQL_clean("" + columnNotMatches.group(1));
				String database_columnvalue = Common.SQL_clean("" + columnNotMatches.group(2));
				database_columnvalue = parse_output_replace_params_escape(database_columnvalue);
				if ((column != null) && (column.get("id") != null) && (! column.get("id").equals(""))) {
					if ((column.get("type").equals("selectmulti")) || (column.get("type").equals("radio")) || (column.get("type").equals("checkbox")) || (column.get("type").equals("contents")) || (column.get("type").equals("contentclasses")) || (column.get("type").equals("contentgroups")) || (column.get("type").equals("contenttypes")) || (column.get("type").equals("pagegroups")) || (column.get("type").equals("pagetypes")) || (column.get("type").equals("imagegroups")) || (column.get("type").equals("imagetypes")) || (column.get("type").equals("imageformats")) || (column.get("type").equals("filegroups")) || (column.get("type").equals("filetypes")) || (column.get("type").equals("fileformats")) || (column.get("type").equals("linkgroups")) || (column.get("type").equals("linktypes")) || (column.get("type").equals("productgroups")) || (column.get("type").equals("producttypes")) || (column.get("type").equals("versions")) || (column.get("type").equals("databases")) || (column.get("type").equals("data")) || (column.get("type").equals("usernames")) || (column.get("type").equals("useremails")) || (column.get("type").equals("usergroups")) || (column.get("type").equals("usertypes"))) {
						if (! SQLwhere.equals("")) {
							SQLwhere += " and ";
						}
						SQLwhere += "(";
						SQLwhere += "col" + column.get("id") + " not like " + db.quote(database_columnvalue);
						SQLwhere += " and ";
						SQLwhere += "col" + column.get("id") + " not like " + db.quote(database_columnvalue + "|%");
						SQLwhere += " and ";
						SQLwhere += "col" + column.get("id") + " not like " + db.quote("%|" + database_columnvalue + "|%");
						SQLwhere += " and ";
						SQLwhere += "col" + column.get("id") + " not like " + db.quote("%|" + database_columnvalue);
						if (Data.csv_options) {
							SQLwhere += " and ";
							SQLwhere += "col" + column.get("id") + " not like " + db.quote(database_columnvalue + ",%");
							SQLwhere += " and ";
							SQLwhere += "col" + column.get("id") + " not like " + db.quote("%," + database_columnvalue + ",%");
							SQLwhere += " and ";
							SQLwhere += "col" + column.get("id") + " not like " + db.quote("%," + database_columnvalue);
						}
						SQLwhere += ")";
					} else if (column.get("type").equals("number")) {
						if (! SQLwhere.equals("")) {
							SQLwhere += " and ";
						}
						SQLwhere += "col" + column.get("id") + " <> " + Common.SQL_clean(database_columnvalue);
					} else if ((column.get("type").equals("datetime")) || (column.get("type").equals("created")) || (column.get("type").equals("updated"))) {
						Matcher columnPeriodMatches = Pattern.compile("^\\Q" + database_columnname + "\\E(<=|>=|=<|=>|&lt;=|&gt;=|=&lt;|=&gt;|=|<|>|&lt;|&gt;)([-+][0-9]+?)(secs|sec|mins|min|hours|hour|days|day|weeks|week|months|month|years|year)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
						if (columnPeriodMatches.find()) {
							SQLwhere = parse_output_list_where_period(db, SQLwhere, "col" + column.get("id"), columnPeriodMatches);
						} else {
							if (! SQLwhere.equals("")) {
								SQLwhere += " and ";
							}
							SQLwhere += "col" + column.get("id") + " not like " + db.quote(database_columnvalue + "%");
						}
					} else {
						if (! SQLwhere.equals("")) {
							SQLwhere += " and ";
						}
						if (database_columnvalue.equals("")) {
							SQLwhere += db.is_not_blank("col" + column.get("id"));
						} else {
							SQLwhere += "col" + column.get("id") + " not like " + db.quote(database_columnvalue);
						}
					}
				}
			} else if (columnCompareMatches.find()) {
				String operand = ("" + columnCompareMatches.group(2)).replaceAll("&lt;", "<").replaceAll("&gt;", ">");
				HashMap column =  parse_output_database_column(database, "" + columnCompareMatches.group(1));
				String database_columnname = Common.SQL_clean("" + columnCompareMatches.group(1));
				String database_columnvalue = Common.SQL_clean("" + columnCompareMatches.group(3));
				database_columnvalue = parse_output_replace_params_escape(database_columnvalue);
				if ((column != null) && (column.get("id") != null) && (! column.get("id").equals(""))) {
					if (column.get("type").equals("number")) {
						if (! SQLwhere.equals("")) {
							SQLwhere += " and ";
						}
						if (database_columnvalue.equals("")) database_columnvalue = "0";
						SQLwhere += "col" + column.get("id") + " " + operand + " " + Common.SQL_clean(database_columnvalue);
					} else if ((column.get("type").equals("datetime")) || (column.get("type").equals("created")) || (column.get("type").equals("updated"))) {
						Matcher columnPeriodMatches = Pattern.compile("^\\Q" + database_columnname + "\\E(<=|>=|=<|=>|&lt;=|&gt;=|=&lt;|=&gt;|=|<|>|&lt;|&gt;)([-+][0-9]+?)(secs|sec|mins|min|hours|hour|days|day|weeks|week|months|month|years|year)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
						if (columnPeriodMatches.find()) {
							SQLwhere = parse_output_list_where_period(db, SQLwhere, "col" + column.get("id"), columnPeriodMatches);
						} else {
							if (! SQLwhere.equals("")) {
								SQLwhere += " and ";
							}
							if ((operand.equals("<")) || (operand.equals(">"))) {
								SQLwhere += "col" + column.get("id") + operand + db.quote(database_columnvalue);
							} else if ((operand.equals("<=")) || (operand.equals(">=")) || (operand.equals("=>")) || (operand.equals("=>"))) {
								SQLwhere += "(";
								SQLwhere += "col" + column.get("id") + operand + db.quote(database_columnvalue);
								SQLwhere += " or ";
								SQLwhere += "col" + column.get("id") + " like " + db.quote(database_columnvalue + "%");
								SQLwhere += ")";
							} else {
								SQLwhere += "col" + column.get("id") + " like " + db.quote(database_columnvalue + "%");
							}
						}
					} else {
						if (! SQLwhere.equals("")) {
							SQLwhere += " and ";
						}
						SQLwhere += "col" + column.get("id") + " " + operand + " " + db.quote(database_columnvalue);
					}
				}
			} else if (columninMatches.find()) {
				HashMap column =  parse_output_database_column(database, "" + columninMatches.group(1));
				String database_columnname = Common.SQL_clean("" + columninMatches.group(1));
				String database_columnvalue = Common.SQL_clean("" + columninMatches.group(2));
				database_columnvalue = parse_output_replace_params_escape(database_columnvalue);
				if ((column != null) && (column.get("id") != null) && (! column.get("id").equals(""))) {
					if (! SQLwhere.equals("")) {
						SQLwhere += " and ";
					}
					if ((column.get("type").equals("selectmulti")) || (column.get("type").equals("radio")) || (column.get("type").equals("checkbox")) || (column.get("type").equals("contents")) || (column.get("type").equals("contentclasses")) || (column.get("type").equals("contentgroups")) || (column.get("type").equals("contenttypes")) || (column.get("type").equals("pagegroups")) || (column.get("type").equals("pagetypes")) || (column.get("type").equals("imagegroups")) || (column.get("type").equals("imagetypes")) || (column.get("type").equals("imageformats")) || (column.get("type").equals("filegroups")) || (column.get("type").equals("filetypes")) || (column.get("type").equals("fileformats")) || (column.get("type").equals("linkgroups")) || (column.get("type").equals("linktypes")) || (column.get("type").equals("productgroups")) || (column.get("type").equals("producttypes")) || (column.get("type").equals("versions")) || (column.get("type").equals("databases")) || (column.get("type").equals("data")) || (column.get("type").equals("usernames")) || (column.get("type").equals("useremails")) || (column.get("type").equals("usergroups")) || (column.get("type").equals("usertypes"))) {
						String[] myvalues = database_columnvalue.split(",");
						String SQLvalues = "";
						for (int j=0; j<myvalues.length; j++) {
							String myvalue = Common.SQL_clean(myvalues[j]);
							if (! SQLvalues.equals("")) {
								SQLvalues += " or ";
							}
							SQLvalues += "(";
							SQLvalues += "col" + column.get("id") + " like " + db.quote(myvalue);
							SQLvalues += " or ";
							SQLvalues += "col" + column.get("id") + " like " + db.quote(myvalue + "|%");
							SQLvalues += " or ";
							SQLvalues += "col" + column.get("id") + " like " + db.quote("%|" + myvalue + "|%");
							SQLvalues += " or ";
							SQLvalues += "col" + column.get("id") + " like " + db.quote("%|" + myvalue);
							if (Data.csv_options) {
								SQLvalues += " or ";
								SQLvalues += "col" + column.get("id") + " like " + db.quote(myvalue + ",%");
								SQLvalues += " or ";
								SQLvalues += "col" + column.get("id") + " like " + db.quote("%," + myvalue + ",%");
								SQLvalues += " or ";
								SQLvalues += "col" + column.get("id") + " like " + db.quote("%," + myvalue);
							}
							SQLvalues += ")";
						}
						SQLwhere += "(" + SQLvalues + ")";
					} else if (column.get("type").equals("number")) {
						String[] myvalues = database_columnvalue.split(",");
						String SQLvalues = "";
						for (int j=0; j<myvalues.length; j++) {
							String myvalue = Common.SQL_clean(myvalues[j]);
							if (! SQLvalues.equals("")) {
								SQLvalues += ",";
							}
							SQLvalues += myvalue;
						}
						SQLwhere += "col" + column.get("id") + " in (" + SQLvalues + ")";
					} else {
						String[] myvalues = database_columnvalue.split(",");
						String SQLvalues = "";
						for (int j=0; j<myvalues.length; j++) {
							String myvalue = Common.SQL_clean(myvalues[j]);
							if (! SQLvalues.equals("")) {
								SQLvalues += ",";
							}
							SQLvalues += myvalue;
						}
						SQLwhere += "col" + column.get("id") + " in (" + db.quote_csv(SQLvalues) + ")";
					}
				}
			}
		}
		return SQLwhere;
	}



	public String parse_output_list_data_order(String list, Request myrequest, Session mysession, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration myconfig, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, Databases database) {
		return parse_output_list_data_order(list, myrequest, mysession, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, myconfig, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, database);
	}
	public String parse_output_list_data_order(String list, Request myrequest, Session mysession, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration myconfig, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, Databases database) {
		String SQLorder = "";
		String[] listparts = list.split(":");
		for (int i=0; i<listparts.length; i++) {
			String listpart = listparts[i];
			if (listpart.equals("searchresults")) {
				SQLorder = parse_output_list_order_searchresults(list, myrequest, mysession, db, myconfig, database);
			} else if (! listpart.startsWith("order=")) {
				// ignore
			} else {
				Matcher orderascMatches = Pattern.compile("^order=([- ,_a-zA-Z0-9\\w|]+?) asc$", Pattern.CASE_INSENSITIVE).matcher(listpart);
				Matcher orderdescMatches = Pattern.compile("^order=([- ,_a-zA-Z0-9\\w|]+?) desc$", Pattern.CASE_INSENSITIVE).matcher(listpart);
				Matcher orderMatches = Pattern.compile("^order=([- ,_a-zA-Z0-9\\w|]+?)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
				if (orderascMatches.find()) {
					String mySQLorder = "";
					String mycolumns = "" + orderascMatches.group(1);
					String[] mycolumnnames = mycolumns.split(",");
					for (int j=0; j<mycolumnnames.length; j++) {
						String mycolumnname = mycolumnnames[j];
						HashMap column = parse_output_database_column(database, mycolumnname.replaceAll(" asc$", "").replaceAll(" desc$", ""));
						if ((column != null) && (column.get("id") != null) && (! column.get("id").equals(""))) {
							if (! mySQLorder.equals("")) mySQLorder += ", ";
							if ((db.db_type(db.getDatabase()).equals("mssql")) && (! column.get("type").equals("number"))) {
								mySQLorder += "substring(col" + column.get("id") + ",1,250)";
							} else if ((db.db_type(db.getDatabase()).equals("oracle")) && (! column.get("type").equals("number"))) {
								mySQLorder += "to_char(col" + column.get("id") + ")";
							} else if ((db.db_type(db.getDatabase()).equals("db2")) && (! column.get("type").equals("number"))) {
								mySQLorder += "varchar(col" + column.get("id") + ",250)";
							} else {
								mySQLorder += "col" + column.get("id");
							}
						}
						if (mycolumnname.endsWith(" asc")) {
							mySQLorder += " asc";
						} else if (mycolumnname.endsWith(" desc")) {
							mySQLorder += " desc";
						}
					}
					if (! mySQLorder.equals("")) {
						SQLorder = " order by " + mySQLorder + " asc";
					}
				} else if (orderdescMatches.find()) {
					String mySQLorder = "";
					String mycolumns = "" + orderdescMatches.group(1);
					String[] mycolumnnames = mycolumns.split(",");
					for (int j=0; j<mycolumnnames.length; j++) {
						String mycolumnname = mycolumnnames[j];
						HashMap column = parse_output_database_column(database, mycolumnname.replaceAll(" asc$", "").replaceAll(" desc$", ""));
						if ((column != null) && (column.get("id") != null) && (! column.get("id").equals(""))) {
							if (! mySQLorder.equals("")) mySQLorder += ", ";
							if ((db.db_type(db.getDatabase()).equals("mssql")) && (! column.get("type").equals("number"))) {
								mySQLorder += "substring(col" + column.get("id") + ",1,250)";
							} else if ((db.db_type(db.getDatabase()).equals("oracle")) && (! column.get("type").equals("number"))) {
								mySQLorder += "to_char(col" + column.get("id") + ")";
							} else if ((db.db_type(db.getDatabase()).equals("db2")) && (! column.get("type").equals("number"))) {
								mySQLorder += "varchar(col" + column.get("id") + ",250)";
							} else {
								mySQLorder += "col" + column.get("id");
							}
						}
						if (mycolumnname.endsWith(" asc")) {
							mySQLorder += " asc";
						} else if (mycolumnname.endsWith(" desc")) {
							mySQLorder += " desc";
						}
					}
					if (! mySQLorder.equals("")) {
						SQLorder = " order by " + mySQLorder + " desc";
					}
				} else if (orderMatches.find()) {
					String mySQLorder = "";
					String mycolumns = "" + orderMatches.group(1);
					String[] mycolumnnames = mycolumns.split(",");
					for (int j=0; j<mycolumnnames.length; j++) {
						String mycolumnname = mycolumnnames[j];
						HashMap column = parse_output_database_column(database, mycolumnname.replaceAll(" asc$", "").replaceAll(" desc$", ""));
						if ((column != null) && (column.get("id") != null) && (! column.get("id").equals(""))) {
							if (! mySQLorder.equals("")) mySQLorder += ", ";
							if ((db.db_type(db.getDatabase()).equals("mssql")) && (! column.get("type").equals("number"))) {
								mySQLorder += "substring(col" + column.get("id") + ",1,250)";
							} else if ((db.db_type(db.getDatabase()).equals("oracle")) && (! column.get("type").equals("number"))) {
								mySQLorder += "to_char(col" + column.get("id") + ")";
							} else if ((db.db_type(db.getDatabase()).equals("db2")) && (! column.get("type").equals("number"))) {
								mySQLorder += "varchar(col" + column.get("id") + ",250)";
							} else {
								mySQLorder += "col" + column.get("id");
							}
						}
						if (mycolumnname.endsWith(" asc")) {
							mySQLorder += " asc";
						} else if (mycolumnname.endsWith(" desc")) {
							mySQLorder += " desc";
						}
					}
					if (! mySQLorder.equals("")) {
						SQLorder = " order by " + mySQLorder;
					}
				}
			}
		}
		return SQLorder;
	}



	public HashMap<String,String> parse_output_database_column(Databases database, String columnname) {
		HashMap<String,String> databasecolumn = null;
		Iterator mycolumns = database.columns.keySet().iterator();
		while (mycolumns.hasNext()) {
			HashMap<String,String> column = (HashMap<String,String>)database.columns.get("" + mycolumns.next());
			String id = "" + column.get("id");
			String order = "" + column.get("order");
			String name = "" + column.get("name");
			String index = "" + column.get("index");
			String type = "" + column.get("type");
			String param1 = "" + column.get("param1");
			String param2 = "" + column.get("param2");
			String options = "" + column.get("options");
			if (name.toLowerCase().equals(columnname.toLowerCase())) {
				databasecolumn = column;
			}
		}
		return databasecolumn;
	}



	public String parse_output_list_from(String list, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		return parse_output_list_from(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public String parse_output_list_from(String list, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		list = list.replaceAll("&amp;", "&");
		StringBuilder SQLfrom = new StringBuilder();
		String[] listparts = list.split(":");
		String table = "content_public";
		if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
			table = "content";
		}
		for (int i=0; i<listparts.length; i++) {
			String listpart = listparts[i];
			Matcher metainfoMatches = Pattern.compile("^(metainfo_[^=]+?)=(.*?)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
			Matcher metainfoInMatches = Pattern.compile("^(metainfo_[^=]+?) in (.+?)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
			Matcher orderMatches = Pattern.compile("^order=([- ,_a-zA-Z0-9\\w|]+?)$").matcher(listpart);
			if (metainfoMatches.find()) {
				String name = Common.SQL_clean("" + metainfoMatches.group(1));
				String value = Common.SQL_clean("" + metainfoMatches.group(2));
				String metaname = name.substring(9);
				String aliasname = "e_" + (metaname.replaceAll("[^a-zA-Z0-9]", "_"));
				SQLfrom.append(" left join ").append(table).append("_meta ").append(aliasname).append(" on (").append(table).append(".id = ").append(aliasname).append(".content_id)");
			} else if (metainfoInMatches.find()) {
				String name = Common.SQL_clean("" + metainfoInMatches.group(1));
				String[] values = ("" + metainfoInMatches.group(2)).split(",");
				String metaname = name.substring(9);
				String aliasname = "e_" + (metaname.replaceAll("[^a-zA-Z0-9]", "_"));
				SQLfrom.append(" left join ").append(table).append("_meta ").append(aliasname).append(" on (").append(table).append(".id = ").append(aliasname).append(".content_id)");
			} else if (orderMatches.find()) {
				String orders = Common.SQL_clean("" + orderMatches.group(1));
				if (orders.contains("metainfo_")) {
					Matcher metainfoOrder = Pattern.compile("metainfo_([-_a-zA-Z0-9\\w\\.]+)").matcher(orders);
					while (metainfoOrder.find()) {
						String metaname = "" + metainfoOrder.group(1);
						String aliasname = "o_" + (metaname.replaceAll("[^a-zA-Z0-9]", "_"));
						SQLfrom.append(" left join ").append(table).append("_meta ").append(aliasname).append(" on (").append(table).append(".id = ").append(aliasname).append(".content_id and ").append(aliasname).append(".meta_name = ").append(db.quote(metaname)).append(")");
					}
				}
			}
		}
		return SQLfrom.toString();
	}



	public String parse_output_list_where(String list, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, Databases database) {
		return parse_output_list_where(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, database);
	}
	public String parse_output_list_where(String list, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, Databases database) {
		String wildcard = "\u000bAst\u000b"; // parse_output_replace_params_escape escaped "*" character
		list = list.replaceAll("&amp;", "&");
		if (regexListWhere == null) regexListWhere = Pattern.compile("^([^!=]+)(<=|>=|=<|=>|&lt;=|&gt;=|=&lt;|=&gt;|=|!=| in |<|>|&lt;|&gt;)(.*?)$", Pattern.CASE_INSENSITIVE);
		String SQLwhere = "";
		String[] listparts = list.split(":");
		for (int i=0; i<listparts.length; i++) {
			String listpart = listparts[i];
			if (listpart.equals("content")) {
				if (! SQLwhere.equals("")) {
					SQLwhere += " and ";
				}
				SQLwhere += "1=1";

			} else if ((listpart.equals("pages")) || (listpart.equals("page"))) {
				if (! SQLwhere.equals("")) {
					SQLwhere += " and ";
				}
				SQLwhere += "contentclass=" + db.quote("page");
			} else if ((listpart.equals("files")) || (listpart.equals("file"))) {
				if (! SQLwhere.equals("")) {
					SQLwhere += " and ";
				}
				SQLwhere += "contentclass=" + db.quote("file");
			} else if ((listpart.equals("images")) || (listpart.equals("image"))) {
				if (! SQLwhere.equals("")) {
					SQLwhere += " and ";
				}
				SQLwhere += "contentclass=" + db.quote("image");
			} else if ((listpart.equals("links")) || (listpart.equals("link"))) {
				if (! SQLwhere.equals("")) {
					SQLwhere += " and ";
				}
				SQLwhere += "contentclass=" + db.quote("link");
			} else if ((listpart.equals("products")) || (listpart.equals("product"))) {
				if (! SQLwhere.equals("")) {
					SQLwhere += " and ";
				}
				SQLwhere += "contentclass=" + db.quote("product");
			} else if ((listpart.equals("stylesheets")) || (listpart.equals("stylesheet"))) {
				if (! SQLwhere.equals("")) {
					SQLwhere += " and ";
				}
				SQLwhere += "contentclass=" + db.quote("stylesheet");
			} else if ((listpart.equals("templates")) || (listpart.equals("template"))) {
				if (! SQLwhere.equals("")) {
					SQLwhere += " and ";
				}
				SQLwhere += "contentclass=" + db.quote("template");
			} else if (listpart.equals("related")) {
				if (! SQLwhere.equals("")) {
					SQLwhere += " and ";
				}
				SQLwhere += "id in (" + db.quote_csv(getRelated()) + ")";

			} else if (listpart.equals("searchresults")) {
				if (! SQLwhere.equals("")) {
					SQLwhere += " and ";
				}
				SQLwhere += parse_output_list_where_searchresults(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, database);
			} else {
				Matcher allMatches = regexListWhere.matcher(listpart);
				if (allMatches.matches()) {
					String myname = ""+allMatches.group(1);
					String myoperand = ""+allMatches.group(2);
					String myvalue = ""+allMatches.group(3);

					if ((myoperand.equals("=")) && ((myname.equals("contentgroup")) || (myname.equals("contenttype")))) {
						if ((! myvalue.equals(wildcard)) && (! myvalue.equals("*"))) {
							if (! SQLwhere.equals("")) {
								SQLwhere += " and ";
							}
							SQLwhere += myname + "=" + db.quote(myvalue);
						}
					} else if ((myoperand.equals("!=")) && ((myname.equals("contentgroup")) || (myname.equals("contenttype")))) {
						if (! SQLwhere.equals("")) {
							SQLwhere += " and ";
						}
						SQLwhere += myname + "<>" + db.quote(myvalue);
					} else if ((myoperand.equals(" in ")) && ((myname.equals("contentgroup")) || (myname.equals("contenttype")))) {
						if (! SQLwhere.equals("")) {
							SQLwhere += " and ";
						}
						SQLwhere += "" + myname + " in (" + db.quote_csv(myvalue) + ")";

					} else if ((myoperand.equals("=")) && ((myname.equals("pagegroup")) || (myname.equals("filegroup")) || (myname.equals("imagegroup")) || (myname.equals("linkgroup")) || (myname.equals("productgroup")))) {
						String mycontentclass = "";
						if (myname.equals("pagegroup")) mycontentclass = "page";
						else if (myname.equals("filegroup")) mycontentclass = "file";
						else if (myname.equals("imagegroup")) mycontentclass = "image";
						else if (myname.equals("linkgroup")) mycontentclass = "link";
						else if (myname.equals("productgroup")) mycontentclass = "product";
						if ((! myvalue.equals(wildcard)) && (! myvalue.equals("*"))) {
							if (! SQLwhere.equals("")) {
								SQLwhere += " and ";
							}
							SQLwhere += "contentclass=" + db.quote(mycontentclass) + " and contentgroup=" + db.quote(myvalue);
						} else {
							if (! SQLwhere.equals("")) {
								SQLwhere += " and ";
							}
							SQLwhere += "contentclass=" + db.quote(mycontentclass);
						}
					} else if ((myoperand.equals("!=")) && ((myname.equals("pagegroup")) || (myname.equals("filegroup")) || (myname.equals("imagegroup")) || (myname.equals("linkgroup")) || (myname.equals("productgroup")))) {
						String mycontentclass = "";
						if (myname.equals("pagegroup")) mycontentclass = "page";
						else if (myname.equals("filegroup")) mycontentclass = "file";
						else if (myname.equals("imagegroup")) mycontentclass = "image";
						else if (myname.equals("linkgroup")) mycontentclass = "link";
						else if (myname.equals("productgroup")) mycontentclass = "product";
						if (! SQLwhere.equals("")) {
							SQLwhere += " and ";
						}
						SQLwhere += "contentclass=" + db.quote(mycontentclass) + " and contentgroup<>" + db.quote(myvalue);
					} else if ((myoperand.equals(" in ")) && ((myname.equals("pagegroup")) || (myname.equals("filegroup")) || (myname.equals("imagegroup")) || (myname.equals("linkgroup")) || (myname.equals("productgroup")))) {
						String mycontentclass = "";
						if (myname.equals("pagegroup")) mycontentclass = "page";
						else if (myname.equals("filegroup")) mycontentclass = "file";
						else if (myname.equals("imagegroup")) mycontentclass = "image";
						else if (myname.equals("linkgroup")) mycontentclass = "link";
						else if (myname.equals("productgroup")) mycontentclass = "product";
						if (! SQLwhere.equals("")) {
							SQLwhere += " and ";
						}
						SQLwhere += "contentclass=" + db.quote(mycontentclass) + " and contentgroup in (" + db.quote_csv(myvalue) + ")";
					} else if ((myoperand.equals("=")) && ((myname.equals("pagetype")) || (myname.equals("filetype")) || (myname.equals("imagetype")) || (myname.equals("linktype")) || (myname.equals("producttype")))) {
						String mycontentclass = "";
						if (myname.equals("pagetype")) mycontentclass = "page";
						else if (myname.equals("filetype")) mycontentclass = "file";
						else if (myname.equals("imagetype")) mycontentclass = "image";
						else if (myname.equals("linktype")) mycontentclass = "link";
						else if (myname.equals("producttype")) mycontentclass = "product";
						if ((! myvalue.equals(wildcard)) && (! myvalue.equals("*"))) {
							if (! SQLwhere.equals("")) {
								SQLwhere += " and ";
							}
							SQLwhere += "contentclass=" + db.quote(mycontentclass) + " and contenttype=" + db.quote(myvalue);
						} else {
							if (! SQLwhere.equals("")) {
								SQLwhere += " and ";
							}
							SQLwhere += "contentclass=" + db.quote(mycontentclass);
						}
					} else if ((myoperand.equals("!=")) && ((myname.equals("pagetype")) || (myname.equals("filetype")) || (myname.equals("imagetype")) || (myname.equals("linktype")) || (myname.equals("producttype")))) {
						String mycontentclass = "";
						if (myname.equals("pagetype")) mycontentclass = "page";
						else if (myname.equals("filetype")) mycontentclass = "file";
						else if (myname.equals("imagetype")) mycontentclass = "image";
						else if (myname.equals("linktype")) mycontentclass = "link";
						else if (myname.equals("producttype")) mycontentclass = "product";
						if (! SQLwhere.equals("")) {
							SQLwhere += " and ";
						}
						SQLwhere += "contentclass=" + db.quote(mycontentclass) + " and contenttype<>" + db.quote(myvalue);
					} else if ((myoperand.equals(" in ")) && ((myname.equals("pagetype")) || (myname.equals("filetype")) || (myname.equals("imagetype")) || (myname.equals("linktype")) || (myname.equals("producttype")))) {
						String mycontentclass = "";
						if (myname.equals("pagetype")) mycontentclass = "page";
						else if (myname.equals("filetype")) mycontentclass = "file";
						else if (myname.equals("imagetype")) mycontentclass = "image";
						else if (myname.equals("linktype")) mycontentclass = "link";
						else if (myname.equals("producttype")) mycontentclass = "product";
						if (! SQLwhere.equals("")) {
							SQLwhere += " and ";
						}
						SQLwhere += "contentclass=" + db.quote(mycontentclass) + " and contenttype in (" + db.quote_csv(myvalue) + ")";

					} else if ((myname.equals("created")) || (myname.equals("updated")) || (myname.equals("published"))) {
			 			Matcher datetimeMatches = Pattern.compile("^([-_%0-9]+?)$", Pattern.CASE_INSENSITIVE).matcher(myvalue);
			 			Matcher periodMatches = Pattern.compile("^(-[0-9]+?)(secs|sec|mins|min|hours|hour|days|day|weeks|week|months|month|years|year)$", Pattern.CASE_INSENSITIVE).matcher(myvalue);
						if (datetimeMatches.matches()) {
							if (! SQLwhere.equals("")) {
								SQLwhere += " and ";
							}
							String timerelation = myoperand;
							String timedate = myvalue;
							timerelation = timerelation.replaceAll("&lt;", "<");
							timerelation = timerelation.replaceAll("&gt;", ">");
							if ((timerelation.equals("<")) || (timerelation.equals(">"))) {
								SQLwhere += "" + myname + "" + timerelation + db.quote(timedate);
							} else if ((timerelation.equals("<=")) || (timerelation.equals(">=")) || (timerelation.equals("=>")) || (timerelation.equals("=>"))) {
								SQLwhere += "(";
								SQLwhere += "" + myname + "" + timerelation + db.quote(timedate);
								SQLwhere += " or ";
								SQLwhere += "" + myname + " like " + db.quote(timedate + "%");
								SQLwhere += ")";
							} else {
								SQLwhere += "" + myname + " like " + db.quote(timedate + "%");
							}
						} else if (periodMatches.matches()) {
							String myamount = ""+periodMatches.group(1);
							String myunit = ""+periodMatches.group(2);
							SQLwhere = parse_output_list_where_period(db, SQLwhere, myname, myoperand, myamount, myunit);
						} else if ((myoperand.equals("=")) && (myvalue.equals(""))) {
							SQLwhere = Common.SQLwhere_equals(db, SQLwhere, myname, "");
						}

					} else if ((myoperand.equals("=")) && ((myname.equals("version")) || (myname.equals("device")) || (myname.equals("segment")) || (myname.equals("variant")) || (myname.equals("contentclass")) || (myname.equals("orderid")) || (myname.equals("id")) || (myname.equals("top")) || (myname.equals("up")) || (myname.equals("previous")) || (myname.equals("next")) || (myname.equals("first")) || (myname.equals("last")) || (myname.equals("createdby")) || (myname.equals("brand")) || (myname.equals("colour")) || (myname.equals("size")) || (myname.equals("name")) || (myname.equals("organisation")) || (myname.equals("email")) || (myname.equals("userclass")) || (myname.equals("usergroup")) || (myname.equals("usertype")) || (myname.equals("card_type")) || (myname.equals("card_issuedmonth")) || (myname.equals("card_issuedyear")) || (myname.equals("card_expirymonth")) || (myname.equals("card_expiryyear")) || (myname.equals("card_postalcode")) || (myname.equals("delivery_name")) || (myname.equals("delivery_organisation")) || (myname.equals("delivery_address")) || (myname.equals("delivery_postalcode")) || (myname.equals("delivery_city")) || (myname.equals("delivery_state")) || (myname.equals("delivery_country")) || (myname.equals("delivery_phone")) || (myname.equals("delivery_fax")) || (myname.equals("delivery_email")) || (myname.equals("delivery_website")) || (myname.equals("invoice_name")) || (myname.equals("invoice_organisation")) || (myname.equals("invoice_address")) || (myname.equals("invoice_postalcode")) || (myname.equals("invoice_city")) || (myname.equals("invoice_state")) || (myname.equals("invoice_country")) || (myname.equals("invoice_phone")) || (myname.equals("invoice_fax")) || (myname.equals("invoice_email")) || (myname.equals("invoice_website")))) {
						if (myname.equals("segment")) myname = "usersegment";
						else if (myname.equals("variant")) myname = "usertest";
						else if (myname.equals("orderid")) myname = "id";
						else if (myname.equals("top")) myname = "page_top";
						else if (myname.equals("up")) myname = "page_up";
						else if (myname.equals("previous")) myname = "page_previous";
						else if (myname.equals("next")) myname = "page_next";
						else if (myname.equals("first")) myname = "page_first";
						else if (myname.equals("last")) myname = "page_last";
						else if (myname.equals("createdby")) myname = "created_by";
						else if (myname.equals("brand")) myname = "product_brand";
						else if (myname.equals("colour")) myname = "product_colour";
						else if (myname.equals("size")) myname = "product_size";
						if ((! myvalue.equals(wildcard)) && (! myvalue.equals("*"))) {
							if (! SQLwhere.equals("")) {
								SQLwhere += " and ";
							}
							if (myvalue.equals("")) {
								SQLwhere += db.is_blank(myname);
							} else {
								SQLwhere += "" + myname + "=" + db.quote(myvalue);
							}
						}
					} else if ((myoperand.equals("!=")) && ((myname.equals("version")) || (myname.equals("device")) || (myname.equals("segment")) || (myname.equals("variant")) || (myname.equals("contentclass")) || (myname.equals("orderid")) || (myname.equals("id")) || (myname.equals("top")) || (myname.equals("up")) || (myname.equals("previous")) || (myname.equals("next")) || (myname.equals("first")) || (myname.equals("last")) || (myname.equals("createdby")) || (myname.equals("brand")) || (myname.equals("colour")) || (myname.equals("size")) || (myname.equals("name")) || (myname.equals("organisation")) || (myname.equals("email")) || (myname.equals("userclass")) || (myname.equals("usergroup")) || (myname.equals("usertype")) || (myname.equals("card_type")) || (myname.equals("card_issuedmonth")) || (myname.equals("card_issuedyear")) || (myname.equals("card_expirymonth")) || (myname.equals("card_expiryyear")) || (myname.equals("card_postalcode")) || (myname.equals("delivery_name")) || (myname.equals("delivery_organisation")) || (myname.equals("delivery_address")) || (myname.equals("delivery_postalcode")) || (myname.equals("delivery_city")) || (myname.equals("delivery_state")) || (myname.equals("delivery_country")) || (myname.equals("delivery_phone")) || (myname.equals("delivery_fax")) || (myname.equals("delivery_email")) || (myname.equals("delivery_website")) || (myname.equals("invoice_name")) || (myname.equals("invoice_organisation")) || (myname.equals("invoice_address")) || (myname.equals("invoice_postalcode")) || (myname.equals("invoice_city")) || (myname.equals("invoice_state")) || (myname.equals("invoice_country")) || (myname.equals("invoice_phone")) || (myname.equals("invoice_fax")) || (myname.equals("invoice_email")) || (myname.equals("invoice_website")))) {
						if (myname.equals("segment")) myname = "usersegment";
						else if (myname.equals("variant")) myname = "usertest";
						else if (myname.equals("orderid")) myname = "id";
						else if (myname.equals("top")) myname = "page_top";
						else if (myname.equals("up")) myname = "page_up";
						else if (myname.equals("previous")) myname = "page_previous";
						else if (myname.equals("next")) myname = "page_next";
						else if (myname.equals("first")) myname = "page_first";
						else if (myname.equals("last")) myname = "page_last";
						else if (myname.equals("createdby")) myname = "created_by";
						else if (myname.equals("brand")) myname = "product_brand";
						else if (myname.equals("colour")) myname = "product_colour";
						else if (myname.equals("size")) myname = "product_size";
						if (! SQLwhere.equals("")) {
							SQLwhere += " and ";
						}
						if (myvalue.equals("")) {
							SQLwhere += db.is_not_blank(myname);
						} else {
							SQLwhere += "" + myname + "<>" + db.quote(myvalue);
						}
					} else if ((myoperand.equals(" in ")) && ((myname.equals("version")) || (myname.equals("device")) || (myname.equals("segment")) || (myname.equals("variant")) || (myname.equals("contentclass")) || (myname.equals("orderid")) || (myname.equals("id")) || (myname.equals("top")) || (myname.equals("up")) || (myname.equals("previous")) || (myname.equals("next")) || (myname.equals("first")) || (myname.equals("last")) || (myname.equals("createdby")) || (myname.equals("brand")) || (myname.equals("colour")) || (myname.equals("size")) || (myname.equals("name")) || (myname.equals("organisation")) || (myname.equals("email")) || (myname.equals("userclass")) || (myname.equals("usergroup")) || (myname.equals("usertype")) || (myname.equals("card_type")) || (myname.equals("card_issuedmonth")) || (myname.equals("card_issuedyear")) || (myname.equals("card_expirymonth")) || (myname.equals("card_expiryyear")) || (myname.equals("card_postalcode")) || (myname.equals("delivery_name")) || (myname.equals("delivery_organisation")) || (myname.equals("delivery_address")) || (myname.equals("delivery_postalcode")) || (myname.equals("delivery_city")) || (myname.equals("delivery_state")) || (myname.equals("delivery_country")) || (myname.equals("delivery_phone")) || (myname.equals("delivery_fax")) || (myname.equals("delivery_email")) || (myname.equals("delivery_website")) || (myname.equals("invoice_name")) || (myname.equals("invoice_organisation")) || (myname.equals("invoice_address")) || (myname.equals("invoice_postalcode")) || (myname.equals("invoice_city")) || (myname.equals("invoice_state")) || (myname.equals("invoice_country")) || (myname.equals("invoice_phone")) || (myname.equals("invoice_fax")) || (myname.equals("invoice_email")) || (myname.equals("invoice_website")))) {
						if (myname.equals("segment")) myname = "usersegment";
						else if (myname.equals("variant")) myname = "usertest";
						else if (myname.equals("orderid")) myname = "id";
						else if (myname.equals("top")) myname = "page_top";
						else if (myname.equals("up")) myname = "page_up";
						else if (myname.equals("previous")) myname = "page_previous";
						else if (myname.equals("next")) myname = "page_next";
						else if (myname.equals("first")) myname = "page_first";
						else if (myname.equals("last")) myname = "page_last";
						else if (myname.equals("createdby")) myname = "created_by";
						else if (myname.equals("brand")) myname = "product_brand";
						else if (myname.equals("colour")) myname = "product_colour";
						else if (myname.equals("size")) myname = "product_size";
						if (! SQLwhere.equals("")) {
							SQLwhere += " and ";
						}
						SQLwhere += "" + myname + " in (" + db.quote_csv(myvalue) + ")";

					} else if ((myoperand.equals("=")) && (myname.equals("title"))) {
						if ((! myvalue.equals(wildcard)) && (! myvalue.equals("*"))) {
							if (! SQLwhere.equals("")) {
								SQLwhere += " and ";
							}
							SQLwhere += "title like " + db.quote(myvalue);
						}

					} else if ((myoperand.equals("=")) && ((myname.equals("author")) || (myname.equals("description")) || (myname.equals("keywords")))) {
						String[] myvalueparts = myvalue.split(",");
						String mySQLwhere = "";
						for (int j=0; j<myvalueparts.length; j++) {
							String myvaluepart = ("" + myvalueparts[j]).trim();
							if (! myvaluepart.equals("")) {
								if (! mySQLwhere.equals("")) {
									mySQLwhere += " and ";
								}
								mySQLwhere += "(";
								mySQLwhere += "(" + myname + " like " + db.quote(myvaluepart) + ")";
								mySQLwhere += " or ";
								mySQLwhere += "(" + myname + " like " + db.quote(myvaluepart + " %") + ")";
								mySQLwhere += " or ";
								mySQLwhere += "(" + myname + " like " + db.quote(myvaluepart + ",%") + ")";
								mySQLwhere += " or ";
								mySQLwhere += "(" + myname + " like " + db.quote("% " + myvaluepart + " %") + ")";
								mySQLwhere += " or ";
								mySQLwhere += "(" + myname + " like " + db.quote("%," + myvaluepart + " %") + ")";
								mySQLwhere += " or ";
								mySQLwhere += "(" + myname + " like " + db.quote("% " + myvaluepart + ",%") + ")";
								mySQLwhere += " or ";
								mySQLwhere += "(" + myname + " like " + db.quote("%," + myvaluepart + ",%") + ")";
								mySQLwhere += " or ";
								mySQLwhere += "(" + myname + " like " + db.quote("% " + myvaluepart) + ")";
								mySQLwhere += " or ";
								mySQLwhere += "(" + myname + " like " + db.quote("%," + myvaluepart) + ")";
								mySQLwhere += ")";
							}
						}
						if (! mySQLwhere.equals("")) {
							if (! SQLwhere.equals("")) {
								SQLwhere += " and ";
							}
							SQLwhere += "(" + mySQLwhere + ")";
						}
					} else if ((myoperand.equals(" in ")) && ((myname.equals("author")) || (myname.equals("description")) || (myname.equals("keywords")))) {
						String[] myvalueparts = myvalue.split(",");
						String mySQLwhere = "";
						for (int j=0; j<myvalueparts.length; j++) {
							String myvaluepart = ("" + myvalueparts[j]).trim();
							if (! myvaluepart.equals("")) {
								if (! mySQLwhere.equals("")) {
									mySQLwhere += " or ";
								}
								mySQLwhere += "(";
								mySQLwhere += "(" + myname + " like " + db.quote(myvaluepart) + ")";
								mySQLwhere += " or ";
								mySQLwhere += "(" + myname + " like " + db.quote(myvaluepart + " %") + ")";
								mySQLwhere += " or ";
								mySQLwhere += "(" + myname + " like " + db.quote(myvaluepart + ",%") + ")";
								mySQLwhere += " or ";
								mySQLwhere += "(" + myname + " like " + db.quote("% " + myvaluepart + " %") + ")";
								mySQLwhere += " or ";
								mySQLwhere += "(" + myname + " like " + db.quote("%," + myvaluepart + " %") + ")";
								mySQLwhere += " or ";
								mySQLwhere += "(" + myname + " like " + db.quote("% " + myvaluepart + ",%") + ")";
								mySQLwhere += " or ";
								mySQLwhere += "(" + myname + " like " + db.quote("%," + myvaluepart + ",%") + ")";
								mySQLwhere += " or ";
								mySQLwhere += "(" + myname + " like " + db.quote("% " + myvaluepart) + ")";
								mySQLwhere += " or ";
								mySQLwhere += "(" + myname + " like " + db.quote("%," + myvaluepart) + ")";
								mySQLwhere += ")";
							}
						}
						if (! mySQLwhere.equals("")) {
							if (! SQLwhere.equals("")) {
								SQLwhere += " and ";
							}
							SQLwhere += "(" + mySQLwhere + ")";
						}

					} else if ((myoperand.equals("=")) && (myname.startsWith("metainfo_"))) {
						if ((! myvalue.equals(wildcard)) && (! myvalue.equals("*"))) {
							String name = Common.SQL_clean(myname);
							String value = Common.SQL_clean(myvalue);
							if (! SQLwhere.equals("")) {
								SQLwhere += " and ";
							}
							if (_METAINFO_) {
								String metaname = name.substring(9);
								String aliasname = "e_" + metaname.replaceAll("[^a-zA-Z0-9]", "_");
								SQLwhere += "(";
								SQLwhere += aliasname + ".meta_name = " + db.quote(metaname);
								SQLwhere += " and ";
								SQLwhere += aliasname + ".meta_content = " + db.quote(value);
								SQLwhere += ")";
							} else {
								SQLwhere += "(";
								SQLwhere += "(metainfo like " + db.quote("%<" + name.substring(9) + ">" + value + "</" + name.substring(9) + ">%") + ")";
								SQLwhere += " or ";
								SQLwhere += "(metainfo like " + db.quote("%<_" + name.substring(9) + ">" + value + "</_" + name.substring(9) + ">%") + ")";
								SQLwhere += ")";
							}
						}
					} else if ((myoperand.equals(" in ")) && (myname.startsWith("metainfo_"))) {
						String name = Common.SQL_clean(myname);
						String value = Common.SQL_clean(myvalue);
						if (_METAINFO_) {
							String metaname = name.substring(9);
							String aliasname = "e_" + metaname.replaceAll("[^a-zA-Z0-9]", "_");
							if (! SQLwhere.equals("")) {
								SQLwhere += " and ";
							}
							SQLwhere += "(";
							SQLwhere += aliasname + ".meta_name = " + db.quote(metaname); 
							SQLwhere += " and ";
							SQLwhere += aliasname + ".meta_content in (" + db.quote_csv(value) + ")";
							SQLwhere += ")";
						} else {
							String[] values = myvalue.split(",");
							for (int j=0; j<values.length; j++) {
								values[j] = Common.SQL_clean(values[j]);
							}
							if (! SQLwhere.equals("")) {
								SQLwhere += " and ";
							}
							SQLwhere += "(";
							for (int j=0; j<values.length; j++) {
								if (j > 0) SQLwhere += " or ";
								SQLwhere += "(metainfo like " + db.quote("%<" + name.substring(9) + ">%" + values[j] + "%</" + name.substring(9) + ">%") + ")";
								SQLwhere += " or ";
								SQLwhere += "(metainfo like " + db.quote("%<_" + name.substring(9) + ">%" + values[j] + "%</_" + name.substring(9) + ">%") + ")";
							}
							SQLwhere += ")";
						}

					} else if ((myoperand.equals("=")) && (myname.startsWith("productinfo_"))) {
						if ((! myvalue.equals(wildcard)) && (! myvalue.equals("*"))) {
							String name = Common.SQL_clean(myname);
							String value = Common.SQL_clean(myvalue);
							if (! SQLwhere.equals("")) {
								SQLwhere += " and ";
							}
							SQLwhere += "(";
							SQLwhere += "(product_info like " + db.quote("%<" + name.substring(12) + ">" + value + "</" + name.substring(12) + ">%") + ")";
							SQLwhere += " or ";
							SQLwhere += "(product_info like " + db.quote("%<_" + name.substring(12) + ">" + value + "</_" + name.substring(12) + ">%") + ")";
							SQLwhere += ")";
						}
					} else if ((myoperand.equals(" in ")) && (myname.startsWith("productinfo_"))) {
						String name = Common.SQL_clean(myname);
						String[] values = myvalue.split(",");
						for (int j=0; j<values.length; j++) {
							values[j] = Common.SQL_clean(values[j]);
						}
						if (! SQLwhere.equals("")) {
							SQLwhere += " and ";
						}
						SQLwhere += "(";
						for (int j=0; j<values.length; j++) {
							if (j > 0) SQLwhere += " or ";
							SQLwhere += "(product_info like " + db.quote("%<" + name.substring(12) + ">%" + values[j] + "%</" + name.substring(12) + ">%") + ")";
							SQLwhere += " or ";
							SQLwhere += "(product_info like " + db.quote("%<_" + name.substring(12) + ">%" + values[j] + "%</_" + name.substring(12) + ">%") + ")";
						}
						SQLwhere += ")";

					} else if (myname.equals("product_price")) {
						if (! SQLwhere.equals("")) {
							SQLwhere += " and ";
						}
						SQLwhere += db.is_not_blank("product_price");

					} else if ((database != null) && (! database.getId().equals("")) && (! database.getId().equals("0")) && (! database.getId().equals("-1"))) {
						Matcher dataMatches = Pattern.compile("^([^=<>&;]+?)(<=|>=|=<|=>|&lt;=|&gt;=|=&lt;|=&gt;|=|<|>|&lt;|&gt;)([^&<>=]+?)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
						if (dataMatches.matches()) {
							String database_columnname = Common.SQL_clean(myname);
							String database_columnoperand = Common.SQL_clean(myoperand);
							String database_columnvalue = Common.SQL_clean(myvalue);
							HashMap<String,String> database_column = parse_output_database_column(database, database_columnname);
							if ((database_column != null) && (database_column.get("id") != null) && (! database_column.get("id").equals(""))) {
								if (! SQLwhere.equals("")) {
									SQLwhere += " and ";
								}
								Matcher dataPeriodMatches = Pattern.compile("^\\Q" + database_columnname + "\\E(<=|>=|=<|=>|&lt;=|&gt;=|=&lt;|=&gt;|=|<|>|&lt;|&gt;)([-+][0-9]+?)(secs|sec|mins|min|hours|hour|days|day|weeks|week|months|month|years|year)$", Pattern.CASE_INSENSITIVE).matcher(listpart);
								if (((database_column.get("type").equals("datetime")) || (database_column.get("type").equals("created")) || (database_column.get("type").equals("updated"))) && (dataPeriodMatches.matches())) {
									SQLwhere = parse_output_list_where_period(db, SQLwhere, "col" + database_column.get("id"), dataPeriodMatches);
								} else {
									SQLwhere += "col" + database_column.get("id") + "=" + db.quote(database_columnvalue);
								}
							}
						}

					} else {
						// ignore
					}
				}
			}
		}
		if (SQLwhere.equals("")) {
			SQLwhere += "(1=0)";
		}
		return "" + SQLwhere;
	}



	public String parse_output_list_where_searchresults(String list, Request myrequest, Session mysession, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration myconfig, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, Databases database) {
		return parse_output_list_where_searchresults(list, myrequest, mysession, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, myconfig, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, database);
	}
	public String parse_output_list_where_searchresults(String list, Request myrequest, Session mysession, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration myconfig, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, Databases database) {
		String SQL = "1=0";
		if ((! myrequest.getParameter("search").equals("")) || (! myrequest.getParameter("database").equals(""))) {
			String[] searchwords = myrequest.getParameter("search").replaceAll("'", "?").replaceAll("\"", "?").split(" ");
			if (! myrequest.getParameter("database").equals("")) {
				if (database.getUser() && (! database.getId().equals("")) && (! database.getId().equals("0")) && (! database.getId().equals("-1"))) {
					SQL = "(1=1)";
					for (int i=0; i<searchwords.length; i++) {
						String searchword = searchwords[i];
						if (! searchword.equals("")) {
							String SQLwhere = "";
							Iterator columns = database.columns.keySet().iterator();
							while (columns.hasNext()) {
								HashMap column = (HashMap)database.columns.get("" + columns.next());
								String mySQLwhere = "";
								if ((column.get("id") != null) && (column.get("type") != null) && (column.get("type").equals("number"))) {
									mySQLwhere = SQL_search_numeric("col" + column.get("id"), searchword);
								} else if ((column.get("id") != null) && (column.get("type") != null) && (column.get("type").equals("datetime"))) {
									Matcher dataPeriodMatches = Pattern.compile("^(=|<|>|&lt;|&gt;)-([0-9]+)(hour|hours|day|days|week|weeks|month|months|year|years)$", Pattern.CASE_INSENSITIVE).matcher(searchword);
									if (dataPeriodMatches.find()) {
										mySQLwhere = parse_output_list_where_period(db, mySQLwhere, "col" + column.get("id"), dataPeriodMatches);
									} else {
										mySQLwhere += SQL_search_datetime(db, "col" + column.get("id"), searchword);
									}
								} else if (column.get("id") != null) {
									mySQLwhere = SQL_search("col" + column.get("id"), searchword, db);
								}
								if ((! mySQLwhere.equals("")) && (! SQLwhere.equals(""))) SQLwhere += " OR ";
								SQLwhere += mySQLwhere;
							}
							SQL += " AND (" + SQLwhere + ")";
						}
					}
					Iterator columns = database.columns.keySet().iterator();
					while (columns.hasNext()) {
						HashMap column = (HashMap)database.columns.get("" + columns.next());
						String SQLwhere = "";
						if (! myrequest.getParameter("" + column.get("name")).equals("")) {
							if ((column.get("id") != null) && (column.get("type") != null) && (column.get("type").equals("number"))) {
								SQLwhere += SQL_search_numeric("col" + column.get("id"), myrequest.getParameter("" + column.get("name")));
							} else if ((column.get("id") != null) && (column.get("name") != null) && (column.get("type") != null) && (column.get("type").equals("datetime"))) {
								Matcher dataPeriodMatches = Pattern.compile("^(=|<|>|&lt;|&gt;)-([0-9]+)(hour|hours|day|days|week|weeks|month|months|year|years)$", Pattern.CASE_INSENSITIVE).matcher(myrequest.getParameter("" + column.get("name")));
								if (dataPeriodMatches.find()) {
									SQLwhere = parse_output_list_where_period(db, SQLwhere, "col" + column.get("id"), dataPeriodMatches);
								} else {
									SQLwhere += SQL_search_datetime(db, "col" + column.get("id"), myrequest.getParameter("" + column.get("name")));
								}
							} else if (column.get("name") != null) {
								SQLwhere += SQL_search("col" + column.get("id"), myrequest.getParameter("" + column.get("name")), db);
							}
						} else if (! myrequest.getParameter(("" + column.get("name")).replaceAll(" ", "_")).equals("")) {
							if ((column.get("name") != null) && (column.get("type") != null) && (column.get("type").equals("number"))) {
								SQLwhere += SQL_search_numeric("col" + column.get("id"), myrequest.getParameter(("" + column.get("name")).replaceAll(" ", "_")));
							} else if ((column.get("id") != null) && (column.get("name") != null) && (column.get("type") != null) && (column.get("type").equals("datetime"))) {
								Matcher dataPeriodMatches = Pattern.compile("^(=|<|>|&lt;|&gt;)-([0-9]+)(hour|hours|day|days|week|weeks|month|months|year|years)$", Pattern.CASE_INSENSITIVE).matcher(myrequest.getParameter(("" + column.get("name")).replaceAll(" ", "_")));
								if (dataPeriodMatches.find()) {
									SQLwhere = parse_output_list_where_period(db, SQLwhere, "col" + column.get("id"), dataPeriodMatches);
								} else {
									SQLwhere += SQL_search_datetime(db, "col" + column.get("id"), myrequest.getParameter(("" + column.get("name")).replaceAll(" ", "_")));
								}
							} else if (column.get("name") != null) {
								SQLwhere += SQL_search("col" + column.get("id"), myrequest.getParameter(("" + column.get("name")).replaceAll(" ", "_")), db);
							}
						}
						if (! SQLwhere.equals("")) {
							SQL += " AND (" + SQLwhere + ")";
						}
					}
				}
			} else {
				SQL = "(id<>0) and (" + db.is_blank("searchable") + " or (searchable<>'no'))";
				for (int i=0; i<searchwords.length; i++) {
					String searchword = searchwords[i];
					SQL = SQL + " AND (";
					SQL = SQL + SQL_search("title", searchword, db);
					SQL = SQL + " OR ";
					SQL = SQL + SQL_search("summary", searchword, db);
					SQL = SQL + " OR ";
					SQL = SQL + SQL_search("keywords", searchword, db);
					SQL = SQL + " OR ";
					SQL = SQL + SQL_search("description", searchword, db);
					SQL = SQL + " OR ";
					SQL = SQL + SQL_search("content", searchword, db);
					SQL = SQL + " OR ";
					SQL = SQL + SQL_search("metainfo", searchword, db);
					SQL = SQL + " OR ";
					SQL = SQL + SQL_search("product_info", searchword, db);
					SQL = SQL + " )";
				}
 				String version = "";
				if (! myrequest.getParameter("version").equals("")) {
					version = Common.join(",", myrequest.getParameters("version"));
				}
				if (! version.equals("")) {
					SQL += " AND (version in (" + db.quote_csv(version) + "))";
				} else {
					SQL += " AND (" + db.is_blank("version") + " or version=" + db.quote(mysession.get("version")) + " or version=" + db.quote(myconfig.get(db, "default_version")) + ")";
				}
				String contentclass = "";
				if (! myrequest.getParameter("contentclass").equals("")) {
					contentclass = Common.join(",", myrequest.getParameters("contentclass"));
				}
				if (! contentclass.equals("")) {
					SQL += " AND (contentclass in (" + db.quote_csv(contentclass) + "))";
				} else {
					SQL += " AND (contentclass in (" + db.quote("page") + "," + db.quote("product") + "))";
				}
				String contentgroup = "";
				if (! myrequest.getParameter("contentgroup").equals("")) {
					contentgroup = Common.join(",", myrequest.getParameters("contentgroup"));
				}
				if (! contentgroup.equals("")) {
					SQL += " AND (contentgroup in (" + db.quote_csv(contentgroup) + "))";
				}
				String contenttype = "";
				if (! myrequest.getParameter("contenttype").equals("")) {
					contenttype = Common.join(",", myrequest.getParameters("contenttype"));
				}
				if (! contenttype.equals("")) {
					SQL += " AND (contenttype in (" + db.quote_csv(contenttype) + "))";
				}
				Enumeration parameters = myrequest.getParameterNames();
				while (parameters.hasMoreElements()) {
					String name = "" + parameters.nextElement();
					if (name.startsWith("metainfo_") && (! myrequest.getParameter(name).equals(""))) {
						SQL += " AND (";
						String[] values = myrequest.getParameters(name);
						for (int i=0; i<values.length; i++) {
							String value = html.encodeHtmlEntities(values[i]);
							if (i > 0) {
								SQL += " or ";
							}
							if (db.db_type(db.getDatabase()).equals("mssql")) {
								SQL += "lower(substring(metainfo,1,datalength(metainfo))) like " + db.quote("%<" + name.toLowerCase().substring(9).replaceAll("'", "?") + ">%" + value.toLowerCase().replaceAll("'", "?") + "%</" + name.toLowerCase().substring(9).replaceAll("'", "?") + ">%");
							} else {
								SQL += "lower(metainfo) like " + db.quote("%<" + name.toLowerCase().substring(9).replaceAll("'", "?") + ">%" + value.toLowerCase().replaceAll("'", "?") + "%</" + name.toLowerCase().substring(9).replaceAll("'", "?") + ">%");
							}
						}
						SQL += ")";
					}
					if (name.startsWith("productinfo_") && (! myrequest.getParameter(name).equals(""))) {
						SQL += " AND (";
						String[] values = myrequest.getParameters(name);
						for (int i=0; i<values.length; i++) {
							String value = html.encodeHtmlEntities(values[i]);
							if (i > 0) {
								SQL += " or ";
							}
							if (db.db_type(db.getDatabase()).equals("mssql")) {
								SQL += "lower(substring(product_info,1,datalength(product_info))) like " + db.quote("%<" + name.toLowerCase().substring(12).replaceAll("'", "?") + ">%" + value.toLowerCase().replaceAll("'", "?") + "%</" + name.toLowerCase().substring(12).replaceAll("'", "?") + ">%");
							} else {
								SQL += "lower(product_info) like " + db.quote("%<" + name.toLowerCase().substring(12).replaceAll("'", "?") + ">%" + value.toLowerCase().replaceAll("'", "?") + "%</" + name.toLowerCase().substring(12).replaceAll("'", "?") + ">%");
							}
						}
						SQL += ")";
					}
				}
			}
		} else {
			SQL = "1=0";
		}
		return SQL;
	}



	private String SQL_search(String column, String value, DB db) {
		String SQLexpression = "";
		if (db.db_type(db.getDatabase()).equals("mssql")) {
			SQLexpression = "lower(substring(" + column + ",1,datalength(" + column + "))) like " + db.quote("%" + value.toLowerCase() + "%");
		} else if (db.db_type(db.getDatabase()).equals("db2")) {
			SQLexpression = "lower(varchar(" + column + ",32000)) like " + db.quote("%" + value.toLowerCase() + "%");
		} else {
			SQLexpression = "lower(" + column + ") like " + db.quote("%" + value.toLowerCase() + "%");
		}
		return SQLexpression;
	}



	private String SQL_search_numeric(String column, String value) {
		String SQLexpression = "";
		Pattern pattern = Pattern.compile("^[-,.0-9]$");
		Matcher matches = pattern.matcher(value);
		if (matches.find()) {
			SQLexpression = column + " = " + value + "";
		}
		return SQLexpression;
	}



	private String SQL_search_datetime(DB db, String column, String value) {
		String SQLexpression = "";
		Matcher periodMatches = Pattern.compile("^(=|<|>|&lt;|&gt;)-([0-9]+)(hour|hours|day|days|week|weeks|month|months|year|years)$", Pattern.CASE_INSENSITIVE).matcher(value);
		if (periodMatches.find()) {
			SQLexpression = parse_output_list_where_period(db, SQLexpression, column, periodMatches);
		} else if (value.startsWith(">=")) {
			SQLexpression = column + " >= " + db.quote(value.substring(2));
		} else if (value.startsWith("<=")) {
			SQLexpression = column + " <= " + db.quote(value.substring(2));
		} else if (value.startsWith(">")) {
			SQLexpression = column + " > " + db.quote(value.substring(1));
		} else if (value.startsWith("<")) {
			SQLexpression = column + " < " + db.quote(value.substring(1));
		} else {
			SQLexpression = column + " like " + db.quote("%" + value + "%");
		}
		return SQLexpression;
	}



	public String parse_output_list_where_period(DB db, String SQLwhere, String column, Matcher matches) {
		String timerelation = "" + matches.group(1);
		String timeamount = "" + matches.group(2);
		String timeunit = "" + matches.group(3);
		return parse_output_list_where_period(db, SQLwhere, column, timerelation, timeamount, timeunit);
	}
	public String parse_output_list_where_period(DB db, String SQLwhere, String column, String timerelation, String amount, String timeunit) {
		int timeamount = 0;
		try {
			timeamount = -1 * Common.intnumber(amount.replaceAll("\\+",""));
		} catch (Exception e) {
		}
		timerelation = timerelation.replaceAll("&lt;", "<");
		timerelation = timerelation.replaceAll("&gt;", ">");
		Date mynow = new Date();
		Calendar mydate = Calendar.getInstance();
		mydate.setTime(mynow);
		Calendar myperiodstart = Calendar.getInstance();
		Calendar myperiodend = Calendar.getInstance();
		if ((timeunit.equals("sec")) || (timeunit.equals("secs"))) {
			mydate.add(Calendar.SECOND, -timeamount);
		} else if ((timeunit.equals("min")) || (timeunit.equals("mins"))) {
			mydate.add(Calendar.SECOND, -(60*timeamount));
		} else if ((timeunit.equals("hour")) || (timeunit.equals("hours"))) {
			mydate.add(Calendar.SECOND, -(60*60*timeamount));
		} else if ((timeunit.equals("day")) || (timeunit.equals("days"))) {
			mydate.add(Calendar.SECOND, -(24*60*60*timeamount));
		} else if ((timeunit.equals("week")) || (timeunit.equals("weeks"))) {
			mydate.add(Calendar.SECOND, -(7*24*60*60*timeamount));
			if (mydate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
				myperiodstart.setTime(mydate.getTime());
				myperiodend.setTime(mydate.getTime());
				myperiodend.add(Calendar.SECOND, +(24*60*60*6));
			} else if (mydate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				myperiodstart.setTime(mydate.getTime());
				myperiodstart.add(Calendar.SECOND, -(24*60*60*6));
				myperiodend.setTime(mydate.getTime());
			} else {
				myperiodstart.setTime(mydate.getTime());
				myperiodstart.add(Calendar.SECOND, -(24*60*60*(mydate.get(Calendar.DAY_OF_WEEK)-Calendar.MONDAY)));
				myperiodend.setTime(mydate.getTime());
				myperiodend.add(Calendar.SECOND, +(24*60*60*(8-mydate.get(Calendar.DAY_OF_WEEK))));
			}
		} else if ((timeunit.equals("month")) || (timeunit.equals("months"))) {
			mydate.add(Calendar.MONTH, -timeamount);
		} else if ((timeunit.equals("year")) || (timeunit.equals("years"))) {
			mydate.add(Calendar.YEAR, -timeamount);
		}
		if (! SQLwhere.equals("")) {
			SQLwhere += " and ";
		}
		if ((timerelation.equals("<")) || (timerelation.equals(">"))) {
			SQLwhere += column + timerelation + db.quote(Common.strftime("yyyy-MM-dd HH:mm:ss", mydate));
		} else if ((timerelation.equals("<=")) || (timerelation.equals(">=")) || (timerelation.equals("=>")) || (timerelation.equals("=>"))) {
			SQLwhere += "(";
			SQLwhere += column + timerelation + db.quote(Common.strftime("yyyy-MM-dd HH:mm:ss", mydate));
			SQLwhere += " or ";
			if ((timeunit.equals("sec")) || (timeunit.equals("secs"))) {
				SQLwhere += column + " like " + db.quote(Common.strftime("yyyy-MM-dd HH:mm:ss", mydate));
			} else if ((timeunit.equals("min")) || (timeunit.equals("mins"))) {
				SQLwhere += column + " like " + db.quote(Common.strftime("yyyy-MM-dd HH:mm", mydate) + "%");
			} else if ((timeunit.equals("hour")) || (timeunit.equals("hours"))) {
				SQLwhere += column + " like " + db.quote(Common.strftime("yyyy-MM-dd HH", mydate) + "%");
			} else if ((timeunit.equals("day")) || (timeunit.equals("days"))) {
				SQLwhere += column + " like " + db.quote(Common.strftime("yyyy-MM-dd", mydate) + "%");
			} else if ((timeunit.equals("week")) || (timeunit.equals("weeks"))) {
				SQLwhere += "((" + column + ">=" + db.quote(Common.strftime("yyyy-MM-dd", myperiodstart) + " 00:00:00") + ") and (" + column + "<=" + db.quote(Common.strftime("yyyy-MM-dd", myperiodend) + " 23:59:59") + "))";
			} else if ((timeunit.equals("month")) || (timeunit.equals("months"))) {
				SQLwhere += column + " like " + db.quote(Common.strftime("yyyy-MM", mydate) + "%");
			} else if ((timeunit.equals("year")) || (timeunit.equals("years"))) {
				SQLwhere += column + " like " + db.quote(Common.strftime("yyyy", mydate) + "%");
			}
			SQLwhere += ")";
		} else if (timerelation.equals("=")) {
			if ((timeunit.equals("sec")) || (timeunit.equals("secs"))) {
				SQLwhere += column + " like " + db.quote(Common.strftime("yyyy-MM-dd HH:mm:ss", mydate));
			} else if ((timeunit.equals("min")) || (timeunit.equals("mins"))) {
				SQLwhere += column + " like " + db.quote(Common.strftime("yyyy-MM-dd HH:mm", mydate) + "%");
			} else if ((timeunit.equals("hour")) || (timeunit.equals("hours"))) {
				SQLwhere += column + " like " + db.quote(Common.strftime("yyyy-MM-dd HH", mydate) + "%");
			} else if ((timeunit.equals("day")) || (timeunit.equals("days"))) {
				SQLwhere += column + " like " + db.quote(Common.strftime("yyyy-MM-dd", mydate) + "%");
			} else if ((timeunit.equals("week")) || (timeunit.equals("weeks"))) {
				SQLwhere += "((" + column + ">=" + db.quote(Common.strftime("yyyy-MM-dd", myperiodstart) + " 00:00:00") + ") and (" + column + "<=" + db.quote(Common.strftime("yyyy-MM-dd", myperiodend) + " 23:59:59") + "))";
			} else if ((timeunit.equals("month")) || (timeunit.equals("months"))) {
				SQLwhere += column + " like " + db.quote(Common.strftime("yyyy-MM", mydate) + "%");
			} else if ((timeunit.equals("year")) || (timeunit.equals("years"))) {
				SQLwhere += column + " like " + db.quote(Common.strftime("yyyy", mydate) + "%");
			}
		} else if ((timerelation.equals("!=")) || (timerelation.equals("<>"))) {
			if ((timeunit.equals("sec")) || (timeunit.equals("secs"))) {
				SQLwhere += column + " not like " + db.quote(Common.strftime("yyyy-MM-dd HH:mm:ss", mydate));
			} else if ((timeunit.equals("min")) || (timeunit.equals("mins"))) {
				SQLwhere += column + " not like " + db.quote(Common.strftime("yyyy-MM-dd HH:mm", mydate) + "%");
			} else if ((timeunit.equals("hour")) || (timeunit.equals("hours"))) {
				SQLwhere += column + " not like " + db.quote(Common.strftime("yyyy-MM-dd HH", mydate) + "%");
			} else if ((timeunit.equals("day")) || (timeunit.equals("days"))) {
				SQLwhere += column + " not like " + db.quote(Common.strftime("yyyy-MM-dd", mydate) + "%");
			} else if ((timeunit.equals("week")) || (timeunit.equals("weeks"))) {
				SQLwhere += "((" + column + "<" + db.quote(Common.strftime("yyyy-MM-dd", myperiodstart) + " 00:00:00") + ") or (" + column + ">" + db.quote(Common.strftime("yyyy-MM-dd", myperiodend) + " 23:59:59") + "))";
			} else if ((timeunit.equals("month")) || (timeunit.equals("months"))) {
				SQLwhere += column + " not like " + db.quote(Common.strftime("yyyy-MM", mydate) + "%");
			} else if ((timeunit.equals("year")) || (timeunit.equals("years"))) {
				SQLwhere += column + " not like " + db.quote(Common.strftime("yyyy", mydate) + "%");
			}
		}
		return SQLwhere;
	}



	public String parse_output_list_order(String list, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, Databases database) {
		return parse_output_list_order(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, database);
	}
	public String parse_output_list_order(String list, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, Databases database) {
		String SQLorder = "";
		String[] listparts = list.split(":");
		for (int i=0; i<listparts.length; i++) {
			String listpart = listparts[i];
			Matcher orderascMatches = Pattern.compile("^order=([- ,_a-zA-Z0-9\\w|]+?) asc$").matcher(listpart);
			Matcher orderdescMatches = Pattern.compile("^order=([- ,_a-zA-Z0-9\\w|]+?) desc$").matcher(listpart);
			Matcher orderMatches = Pattern.compile("^order=([- ,_a-zA-Z0-9\\w|]+?)$").matcher(listpart);
			if (orderascMatches.find()) {
				if ((""+orderascMatches.group(1)).equals("product_price")) {
					SQLorder = " order by 1*product_price asc";
				} else if (((""+orderascMatches.group(1)).equals("filename")) || ((""+orderascMatches.group(1)).equals("server_filename"))) {
					if (db.db_type(db.getDatabase()).equals("mssql")) {
						SQLorder = " order by substring(server_filename,1,250) asc";
					} else if (db.db_type(db.getDatabase()).equals("oracle")) {
						SQLorder = " order by to_char(server_filename) asc";
					} else if (db.db_type(db.getDatabase()).equals("db2")) {
						SQLorder = " order by varchar(server_filename,250) asc";
					} else {
						SQLorder = " order by server_filename asc";
					}
				} else if ((""+orderascMatches.group(1)).contains("|")) {
					if (db.db_type(db.getDatabase()).equals("mssql")) {
						SQLorder = " order by (" + (""+orderascMatches.group(1)).replaceAll("\\|", " + ") + ") asc";
					} else if (db.db_type(db.getDatabase()).equals("oracle")) {
						SQLorder = " order by (" + (""+orderascMatches.group(1)).replaceAll("\\|", " || ") + ") asc";
					} else if (db.db_type(db.getDatabase()).equals("db2")) {
						SQLorder = " order by (" + (""+orderascMatches.group(1)).replaceAll("\\|", " || ") + ") asc";
					} else if (db.db_type(db.getDatabase()).equals("postgres")) {
						SQLorder = " order by (" + (""+orderascMatches.group(1)).replaceAll("\\|", " || ") + ") asc";
					} else if (db.db_type(db.getDatabase()).equals("mysql")) {
						SQLorder = " order by concat(" + (""+orderascMatches.group(1)).replaceAll("\\|", ",") + ") asc";
					} else if (db.db_type(db.getDatabase()).equals("access")) {
						SQLorder = " order by (" + (""+orderascMatches.group(1)).replaceAll("\\|", " & ") + ") asc";
					} else {
						SQLorder = " order by " + (""+orderascMatches.group(1)).replaceAll("\\|", " asc, ") + " asc";
					}
				} else {
					SQLorder = " order by " + orderascMatches.group(1) + " asc";
				}
			} else if (orderdescMatches.find()) {
				if ((""+orderdescMatches.group(1)).equals("product_price")) {
					SQLorder = " order by 1*product_price desc";
				} else if (((""+orderdescMatches.group(1)).equals("filename")) || ((""+orderdescMatches.group(1)).equals("server_filename"))) {
					if (db.db_type(db.getDatabase()).equals("mssql")) {
						SQLorder = " order by substring(server_filename,1,250) desc";
					} else if (db.db_type(db.getDatabase()).equals("oracle")) {
						SQLorder = " order by to_char(server_filename) desc";
					} else if (db.db_type(db.getDatabase()).equals("db2")) {
						SQLorder = " order by varchar(server_filename,250) desc";
					} else {
						SQLorder = " order by server_filename desc";
					}
				} else if ((""+orderdescMatches.group(1)).contains("|")) {
					if (db.db_type(db.getDatabase()).equals("mssql")) {
						SQLorder = " order by (" + (""+orderdescMatches.group(1)).replaceAll("\\|", " + ") + ") desc";
					} else if (db.db_type(db.getDatabase()).equals("oracle")) {
						SQLorder = " order by (" + (""+orderdescMatches.group(1)).replaceAll("\\|", " || ") + ") desc";
					} else if (db.db_type(db.getDatabase()).equals("db2")) {
						SQLorder = " order by (" + (""+orderdescMatches.group(1)).replaceAll("\\|", " || ") + ") desc";
					} else if (db.db_type(db.getDatabase()).equals("postgres")) {
						SQLorder = " order by (" + (""+orderdescMatches.group(1)).replaceAll("\\|", " || ") + ") desc";
					} else if (db.db_type(db.getDatabase()).equals("mysql")) {
						SQLorder = " order by concat(" + (""+orderdescMatches.group(1)).replaceAll("\\|", ",") + ") desc";
					} else if (db.db_type(db.getDatabase()).equals("access")) {
						SQLorder = " order by (" + (""+orderdescMatches.group(1)).replaceAll("\\|", " & ") + ") desc";
					} else {
						SQLorder = " order by " + (""+orderdescMatches.group(1)).replaceAll("\\|", " desc, ") + " desc";
					}
				} else {
					SQLorder = " order by " + orderdescMatches.group(1) + " desc";
				}
			} else if (orderMatches.find()) {
				if ((""+orderMatches.group(1)).equals("product_price")) {
					SQLorder = " order by 1*product_price";
				} else if (((""+orderMatches.group(1)).equals("filename")) || ((""+orderMatches.group(1)).equals("server_filename"))) {
					if (db.db_type(db.getDatabase()).equals("mssql")) {
						SQLorder = " order by substring(server_filename,1,250)";
					} else if (db.db_type(db.getDatabase()).equals("oracle")) {
						SQLorder = " order by to_char(server_filename)";
					} else if (db.db_type(db.getDatabase()).equals("db2")) {
						SQLorder = " order by varchar(server_filename,250)";
					} else {
						SQLorder = " order by server_filename";
					}
				} else if ((""+orderMatches.group(1)).contains("|")) {
					if (db.db_type(db.getDatabase()).equals("mssql")) {
						SQLorder = " order by (" + (""+orderMatches.group(1)).replaceAll("\\|", " + ") + ")";
					} else if (db.db_type(db.getDatabase()).equals("oracle")) {
						SQLorder = " order by (" + (""+orderMatches.group(1)).replaceAll("\\|", " || ") + ")";
					} else if (db.db_type(db.getDatabase()).equals("db2")) {
						SQLorder = " order by (" + (""+orderMatches.group(1)).replaceAll("\\|", " || ") + ")";
					} else if (db.db_type(db.getDatabase()).equals("postgres")) {
						SQLorder = " order by (" + (""+orderMatches.group(1)).replaceAll("\\|", " || ") + ")";
					} else if (db.db_type(db.getDatabase()).equals("mysql")) {
						SQLorder = " order by concat(" + (""+orderMatches.group(1)).replaceAll("\\|", ",") + ")";
					} else if (db.db_type(db.getDatabase()).equals("access")) {
						SQLorder = " order by (" + (""+orderMatches.group(1)).replaceAll("\\|", " & ") + ")";
					} else {
						SQLorder = " order by " + (""+orderMatches.group(1)).replaceAll("\\|", ", ") + "";
					}
				} else {
					SQLorder = " order by " + orderMatches.group(1);
				}
			} else if (listpart.equals("searchresults")) {
				SQLorder = parse_output_list_order_searchresults(list, request, session, db, config, database);
			}
			if (_METAINFO_) {
				if (SQLorder.contains("metainfo_")) {
					String[] orderparts = SQLorder.split(",");
					SQLorder = "";
					for (int j=0; j<orderparts.length; j++) {
						String orderpart = orderparts[j];
						String metaname = orderpart.replaceAll("( order by )? *metainfo_([-_a-zA-Z0-9\\w\\.]+)( asc| desc)?", "$2");
						String aliasname = "o_" + (metaname.replaceAll("[^a-zA-Z0-9]", "_"));
						orderpart = orderpart.replaceAll("metainfo_([-_a-zA-Z0-9\\w\\.]+)", aliasname + ".meta_content");
						if (! SQLorder.equals("")) {
							SQLorder += ",";
						}
						SQLorder += orderpart;
					}
				}
			}
		}
		return "" + SQLorder;
	}



	public String parse_output_list_order_searchresults(String list, Request myrequest, Session mysession, DB db, Configuration myconfig, Databases database) {
		String SQL = "";
		if ((! myrequest.getParameter("search").equals("")) || (! myrequest.getParameter("database").equals(""))) {
			if (! myrequest.getParameter("database").equals("")) {
				if (database.getUser() && (! database.getId().equals("")) && (! database.getId().equals("0")) && (! database.getId().equals("-1"))) {
					String order = "";
					if (! myrequest.getParameter("order").equals("")) {
						order = html.encodeHtmlEntities(myrequest.getParameter("order"));
					}
					if (! order.equals("")) {
						Iterator columns = database.columns.keySet().iterator();
						while (columns.hasNext()) {
							HashMap column = (HashMap)database.columns.get("" + columns.next());
							if (column.get("name").equals(order)) {
								if ((db.db_type(db.getDatabase()).equals("mssql")) && (! column.get("type").equals("number"))) {
									SQL += " order by substring(col" + column.get("id") + ",1,250)";
								} else if ((db.db_type(db.getDatabase()).equals("oracle")) && (! column.get("type").equals("number"))) {
									SQL += " order by to_char(col" + column.get("id") + ")";
								} else if ((db.db_type(db.getDatabase()).equals("db2")) && (! column.get("type").equals("number"))) {
									SQL += " order by varchar(col" + column.get("id") + ",250)";
								} else {
									SQL += " order by col" + column.get("id");
								}
							}
						}
					}
				}
			} else {
				SQL += " order by title";
			}
		} else {
			SQL = "";
		}
		return SQL;
	}



	public String parse_output_list_limit(String list, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		return parse_output_list_limit(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public String parse_output_list_limit(String list, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		String limit = "";
		String[] listparts = list.split(":");
		for (int i=0; i<listparts.length; i++) {
			String listpart = listparts[i];
			Matcher limitMatches = Pattern.compile("^limit=([0-9]+?)$").matcher(listpart);
			if (limitMatches.find()) {
				limit = "" + limitMatches.group(1);
			}
		}
		return "" + limit;
	}



	public String parse_output_list_none(String list, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		return parse_output_list_none(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public String parse_output_list_none(String list, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		String none = "";
		String[] listparts = list.split(":");
		for (int i=0; i<listparts.length; i++) {
			String listpart = listparts[i];
			Matcher noneMatches = Pattern.compile("^none=(.+?)$").matcher(listpart);
			if (noneMatches.find()) {
				none = "" + noneMatches.group(1);
			}
		}
		return "" + none;
	}



	public String parse_output_list_distinct(String list, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		return parse_output_list_distinct(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public String parse_output_list_distinct(String list, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		String distinct = "";
		String[] listparts = list.split(":");
		for (int i=0; i<listparts.length; i++) {
			String listpart = listparts[i];
			Matcher distinctMatches = Pattern.compile("^distinct=(.+?)$").matcher(listpart);
			if (listpart.equals("distinct")) {
				distinct = "*";
			} else if (distinctMatches.find()) {
				distinct = "" + distinctMatches.group(1);
			}
		}
		return "" + distinct;
	}



	public String parse_output_list_entry(String list, String entryname, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		return parse_output_list_entry(list, entryname, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public String parse_output_list_entry(String list, String entryname, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		String entry = "";
		String[] listparts = list.split(":");
		for (int i=0; i<listparts.length; i++) {
			String listpart = listparts[i];
			Matcher entryMatches = Pattern.compile("^" + entryname + "=(.+?)$").matcher(listpart);
			if (entryMatches.find()) {
				entry = "" + entryMatches.group(1);
			}
		}
		return "" + entry;
	}



	public String parse_output_list_entrycontent(String listentryid, Databases database) {
		String listentrycontent = "";
		String[] listentryidparts = listentryid.split("\\.");
		if (listentryidparts.length >= 2) {
			if ((listentryidparts.length >= 3) && (listentryidparts[listentryidparts.length-2].equals("option")) && (listentryidparts[listentryidparts.length-1].startsWith("selected="))) {
				if (listentryidparts.length == 3) {
					String value = listentryidparts[0];
					String text = listentryidparts[0];
					String selected = listentryidparts[listentryidparts.length-1].replaceAll("^selected=", "");
					listentrycontent = "<option value=\"@@@" + value + "@@@\"@@@display:if:@@@" + value + "@@@=" + selected + "@@@ selected@@@end:if:@@@" + value + "@@@=" + selected + "@@@>@@@" + text + "@@@</option>" + "\r\n";
				} else if (listentryidparts.length == 4) {
					String value = listentryidparts[0];
					String text = listentryidparts[1];
					String selected = listentryidparts[listentryidparts.length-1].replaceAll("^selected=", "");
					listentrycontent = "<option value=\"@@@" + value + "@@@\"@@@display:if:@@@" + value + "@@@=" + selected + "@@@ selected@@@end:if:@@@" + value + "@@@=" + selected + "@@@>@@@" + text + "@@@</option>" + "\r\n";
				}
			} else if ((listentryidparts.length >= 3) && (listentryidparts[listentryidparts.length-2].equals("option")) && (listentryidparts[listentryidparts.length-1].equals("selected"))) {
				if (listentryidparts.length == 3) {
					String value = listentryidparts[0];
					String text = listentryidparts[0];
					listentrycontent = "<option value=\"@@@" + value + "@@@\" selected>@@@" + text + "@@@</option>" + "\r\n";
				} else if (listentryidparts.length == 4) {
					String value = listentryidparts[0];
					String text = listentryidparts[1];
					listentrycontent = "<option value=\"@@@" + value + "@@@\" selected>@@@" + text + "@@@</option>" + "\r\n";
				}
			} else if ((listentryidparts.length >= 2) && (listentryidparts[listentryidparts.length-1].equals("option"))) {
				if (listentryidparts.length == 2) {
					String value = listentryidparts[0];
					String text = listentryidparts[0];
					listentrycontent = "<option value=\"@@@" + value + "@@@\">@@@" + text + "@@@</option>" + "\r\n";
				} else if (listentryidparts.length == 3) {
					String value = listentryidparts[0];
					String text = listentryidparts[1];
					listentrycontent = "<option value=\"@@@" + value + "@@@\">@@@" + text + "@@@</option>" + "\r\n";
				}
			} else if ((listentryidparts.length >= 4) && ((listentryidparts[listentryidparts.length-2].equals("checkbox")) || (listentryidparts[listentryidparts.length-2].equals("radio"))) && (listentryidparts[listentryidparts.length-1].equals("checked"))) {
				if (listentryidparts.length == 4) {
					String inputtype = listentryidparts[listentryidparts.length-2];
					String value = listentryidparts[0];
					String text = listentryidparts[0];
					String name = listentryidparts[1];
					listentrycontent = "<input type=\"" + inputtype + "\" name=\"" + name + "\" value=\"@@@" + value + "@@@\" checked>@@@" + text + "@@@" + "\r\n";
				} else if (listentryidparts.length == 5) {
					String inputtype = listentryidparts[listentryidparts.length-2];
					String value = listentryidparts[0];
					String text = listentryidparts[1];
					String name = listentryidparts[2];
					listentrycontent = "<input type=\"" + inputtype + "\" name=\"" + name + "\" value=\"@@@" + value + "@@@\" checked>@@@" + text + "@@@" + "\r\n";
				}
			} else if ((listentryidparts.length >= 3) && ((listentryidparts[listentryidparts.length-1].equals("checkbox")) || (listentryidparts[listentryidparts.length-1].equals("radio")))) {
				if (listentryidparts.length == 3) {
					String inputtype = listentryidparts[listentryidparts.length-1];
					String value = listentryidparts[0];
					String text = listentryidparts[0];
					String name = listentryidparts[1];
					listentrycontent = "<input type=\"" + inputtype + "\" name=\"" + name + "\" value=\"@@@" + value + "@@@\">@@@" + text + "@@@" + "\r\n";
				} else if (listentryidparts.length == 4) {
					String inputtype = listentryidparts[listentryidparts.length-1];
					String value = listentryidparts[0];
					String text = listentryidparts[1];
					String name = listentryidparts[2];
					listentrycontent = "<input type=\"" + inputtype + "\" name=\"" + name + "\" value=\"@@@" + value + "@@@\">@@@" + text + "@@@" + "\r\n";
				}
			} else if ((listentryidparts.length == 3) && ((listentryidparts[listentryidparts.length-2].equals("div")) || (listentryidparts[listentryidparts.length-2].equals("p")) || (listentryidparts[listentryidparts.length-2].equals("span")) || (listentryidparts[listentryidparts.length-2].equals("li")) || (listentryidparts[listentryidparts.length-2].equals("th")) || (listentryidparts[listentryidparts.length-2].equals("td")))) {
				String tag = listentryidparts[listentryidparts.length-2];
				String name = listentryidparts[listentryidparts.length-1];
				String text = listentryidparts[0];
				listentrycontent = "<" + tag + " class=\"" + name + "\">@@@" + text + "@@@</" + tag + ">" + "\r\n";
			} else if ((listentryidparts.length == 2) && ((listentryidparts[listentryidparts.length-1].equals("div")) || (listentryidparts[listentryidparts.length-1].equals("p")) || (listentryidparts[listentryidparts.length-1].equals("span")) || (listentryidparts[listentryidparts.length-1].equals("li")) || (listentryidparts[listentryidparts.length-1].equals("th")) || (listentryidparts[listentryidparts.length-1].equals("td")))) {
				String tag = listentryidparts[listentryidparts.length-1];
				String text = listentryidparts[0];
				listentrycontent = "<" + tag + ">@@@" + text + "@@@</" + tag + ">" + "\r\n";
			} else if ((listentryidparts.length == 3) && (listentryidparts[listentryidparts.length-2].equals("link"))) {
				String name = listentryidparts[listentryidparts.length-1];
				String text = listentryidparts[0];
				if ((database != null) &&  ((! database.getId().equals("")) && (! database.getId().equals("0")) && (! database.getId().equals("-1")))) {
					listentrycontent = "<a href=\"/data.jsp?database=" + database.getTitle() + "&id=@@@id@@@\" class=\"" + name + "\">@@@" + text + "@@@</a><br>" + "\r\n";
				} else {
					listentrycontent = "<a href=\"/@@@contentclass@@@.jsp?id=@@@id@@@\" class=\"" + name + "\">@@@" + text + "@@@</a><br>" + "\r\n";
				}
			} else if ((listentryidparts.length == 2) && (listentryidparts[listentryidparts.length-1].equals("link"))) {
				String text = listentryidparts[0];
				if ((database != null) &&  ((! database.getId().equals("")) && (! database.getId().equals("0")) && (! database.getId().equals("-1")))) {
					listentrycontent = "<a href=\"/data.jsp?database=" + database.getTitle() + "&id=@@@id@@@\">@@@" + text + "@@@</a><br>" + "\r\n";
				} else {
					listentrycontent = "<a href=\"/@@@contentclass@@@.jsp?id=@@@id@@@\">@@@" + text + "@@@</a><br>" + "\r\n";
				}
			} else if ((listentryidparts.length == 3) && (listentryidparts[listentryidparts.length-2].equals("image"))) {
				String name = listentryidparts[listentryidparts.length-1];
				String image = listentryidparts[0];
				listentrycontent = "<img src=\"/image.jsp?id=@@@" + image + "@@@\" class=\"" + name + "\"><br>" + "\r\n";
			} else if ((listentryidparts.length == 2) && (listentryidparts[listentryidparts.length-1].equals("image"))) {
				String image = listentryidparts[0];
				listentrycontent = "<img src=\"/image.jsp?id=@@@" + image + "@@@\"><br>" + "\r\n";
			} else if ((listentryidparts.length == 2) && (listentryidparts[listentryidparts.length-1].equals("br"))) {
				String text = listentryidparts[0];
				listentrycontent = "@@@" + text + "@@@<br>" + "\r\n";
			} else if ((listentryidparts.length == 2) && (listentryidparts[listentryidparts.length-1].equals("text"))) {
				String text = listentryidparts[0];
				listentrycontent = "@@@" + text + "@@@" + "\r\n";
			}
		}
		return listentrycontent;
	}



	public boolean parse_output_list_merge(String list, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		return parse_output_list_merge(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public boolean parse_output_list_merge(String list, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		boolean merge = false;
		String[] listparts = list.split(":");
		for (int i=0; i<listparts.length; i++) {
			String listpart = listparts[i];
			if (listpart.equals("merge")) {
				merge = true;
			}
		}
		return merge;
	}



	public String parse_output_list_columns(String list, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		return parse_output_list_columns(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public String parse_output_list_columns(String list, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		String listcolumns = "";
		String[] listparts = list.split(":");
		for (int i=0; i<listparts.length; i++) {
			String listpart = listparts[i];
			Matcher entryMatches = Pattern.compile("^columns=([0-9]+?)$").matcher(listpart);
			if (entryMatches.find()) {
				listcolumns = "" + entryMatches.group(1);
			}
		}
		return "" + listcolumns;
	}



	public String parse_output_list_header(String list, String table, String column, ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, HashMap<String,Page> page_cache) throws Exception {
		String listheader = "";
		String[] listparts = list.split(":");
		for (int i=0; i<listparts.length; i++) {
			String listpart = listparts[i];
			Matcher entryMatches = Pattern.compile("^header=([0-9]+?)$").matcher(listpart);
			Matcher textMatches = Pattern.compile("^header=(.+?)$").matcher(listpart);
			if (entryMatches.find()) {
				String myid = "" + entryMatches.group(1);
				Page mylistheader = null;
				if (page_cache != null) mylistheader = page_cache.get(myid);
				if (mylistheader == null) {
					mylistheader = new Page(text);
					boolean preview = false;
					if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
						mylistheader.read_primary_only(db, config, myid, "content", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
						mylistheader.previewScheduledQueued(session, config, db);
						preview = mylistheader.previewScheduled(session);
					}
					if (! preview) {
						mylistheader.read_primary_only(db, config, myid, "content_public", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					}
					if (! mylistheader.getUser()) {
						mylistheader = new Page(text);
					}
					mylistheader.parse_output(server, request, response, session, script_name, query_string, session.get("mode"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), session.get("administrator"), db, config, getId(), table, column, session.get("version"), config.get(db, "default_version"), session.get("device"), session.get("usersegments"), session.get("usertests"), session.get("currency"), default_currency, "", "", mylistheader.getId());
					if (page_cache != null) page_cache.put(myid, mylistheader);
				}
				listheader = "" + mylistheader.getBody();
			} else if (textMatches.find()) {
				String mytext = "" + textMatches.group(1);
				listheader = "\r\n" + "<thead><tr><th>" + (mytext.replaceAll(",", "</th><th>")) + "</th></tr></thead>" + "\r\n";
			}
		}
		return "" + listheader;
	}



	public String parse_output_list_footer(String list, String table, String column, ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry, HashMap<String,Page> page_cache) throws Exception {
		String listfooter = "";
		String[] listparts = list.split(":");
		for (int i=0; i<listparts.length; i++) {
			String listpart = listparts[i];
			Matcher entryMatches = Pattern.compile("^footer=([0-9]+?)$").matcher(listpart);
			Matcher textMatches = Pattern.compile("^footer=(.+?)$").matcher(listpart);
			if (entryMatches.find()) {
				String myid = "" + entryMatches.group(1);
				Page mylistfooter = null;
				if (page_cache != null) mylistfooter = page_cache.get(myid);
				if (mylistfooter == null) {
					mylistfooter = new Page(text);
					boolean preview = false;
					if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
						mylistfooter.read_primary_only(db, config, myid, "content", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
						mylistfooter.previewScheduledQueued(session, config, db);
						preview = mylistfooter.previewScheduled(session);
					}
					if (! preview) {
						mylistfooter.read_primary_only(db, config, myid, "content_public", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					}
					if (! mylistfooter.getUser()) {
						mylistfooter = new Page(text);
					}
					mylistfooter.parse_output(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, getId(), table, column, session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, mylistfooter.getId());
					if (page_cache != null) page_cache.put(myid, mylistfooter);
				}
				listfooter = "" + mylistfooter.getBody();
			} else if (textMatches.find()) {
				String mytext = "" + textMatches.group(1);
				listfooter = "\r\n " + "<tfoot><tr><th>" + (mytext.replaceAll(",", "</th><th>")) + "</th></tr></tfoot>" + "\r\n";
			}
		}
		return "" + listfooter;
	}



	public String parse_output_list_class(String list, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		return parse_output_list_class(list, request, session, script_name, query_string, session_mode, session_administrator, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public String parse_output_list_class(String list, Request request, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) {
		String listclass = "";
		String[] listparts = list.split(":");
		for (int i=0; i<listparts.length; i++) {
			String listpart = listparts[i];
			Matcher entryMatches = Pattern.compile("^class=([- ,_a-zA-Z0-9\\w]+?)$").matcher(listpart);
			if (entryMatches.find()) {
				listclass = "" + entryMatches.group(1);
			}
		}
		return "" + listclass;
	}



	public String adjust_links(String session_version, String content, DB db, Configuration config) {
		if (content.equals("")) return content;
if (! config.get(db, "adjust_links").equals("no")) {
		content = content.replaceAll("redirect=([^&<>\"]+?)\\.(aspx?|php)", "redirect=$1.jsp");
		content = content.replaceAll("/(index|page|image|file|link|element|script|stylesheet|template|manifest|xml|rss|atom|data|login|login_post|logout|contact|post|trackback|search|guestbook|product|shopcart|register|subscribe|unsubscribe|admin|view|create|update|delete|create_post|update_post|delete_post|checkin|checkout|publish|@@@[^@/]+?@@@|###[^#/]+?###)\\.(aspx?|php)", "/$1.jsp");
}
		if ((! session_version.equals("")) && (! session_version.equals(" "))) {
			content = content.replaceAll("/(index|page|image|file|link|product|element|script|stylesheet|template|xml|rss|atom)\\.jsp\\?id=([0-9]+?)([^&0-9])", "/$1.jsp?id=$2&version=" + session_version.replaceAll("&", "").replaceAll("=", "").replaceAll("@", "").replaceAll("#", "").replaceAll("\"", "").replaceAll(Pattern.quote("$"), "") + "$3");
			content = content.replaceAll("/(index|page|image|file|link|product|element|script|stylesheet|template|xml|rss|atom)\\.jsp/id=([0-9]+?)([^\\?/0-9])", "/$1.jsp/id=$2?version=" + session_version.replaceAll("&", "").replaceAll("=", "").replaceAll("@", "").replaceAll("#", "").replaceAll("\"", "").replaceAll(Pattern.quote("$"), "") + "$3");
		}

		if (config.get(db, "url_rewriting").equals("yes")) {
			content = content.replaceAll("/(index|page|image|file|link|product|element|script|stylesheet|template|manifest|xml|rss|atom)\\.jsp\\?([^\\?&\"\\r\\n]+?)\\&amp;", "/$1.jsp/$2?");
			content = content.replaceAll("/(index|page|image|file|link|product|element|script|stylesheet|template|manifest|xml|rss|atom)\\.jsp\\?([^\\?&\"\\r\\n]+?)\\&", "/$1.jsp/$2?");
			content = content.replaceAll("/(index|page|image|file|link|product|element|script|stylesheet|template|manifest|xml|rss|atom)\\.jsp\\?", "/$1.jsp/");
		} else if (! config.get(db, "adjust_links").equals("no")) {
			content = content.replaceAll("/(index|page|image|file|link|product|element|script|stylesheet|template|manifest|xml|rss|atom)\\.jsp/([^\\?&\"\\r\\n]+?)\\?", "/$1.jsp?$2&");
			content = content.replaceAll("/(index|page|image|file|link|product|element|script|stylesheet|template|manifest|xml|rss|atom)\\.jsp/", "/$1.jsp?");
		}
		return content;
	}



	public void scheduled_read(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String session_version, String default_version, String session_template, String website_template, String default_template, String session_stylesheet, String website_stylesheet, String default_stylesheet, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		scheduled_read(server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, session_version, default_version, "", "", "", session_template, website_template, default_template, session_stylesheet, website_stylesheet, default_stylesheet, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public void scheduled_read(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_template, String website_template, String default_template, String session_stylesheet, String website_stylesheet, String default_stylesheet, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		super.read(db, config, id, "content", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
		previewScheduledQueued(session, config, db);
		body = adjust_links(session_version, body, db, config);
		setContent(adjust_links(session_version, getContent(), db, config));
		setHtmlAttributes(adjust_links(session_version, getHtmlAttributes(), db, config));
		parse(server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, "content", "id", session_version, default_version, session_device, session_usersegments, session_usertests, session_template, website_template, default_template, session_stylesheet, website_stylesheet, default_stylesheet, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}



	public void preview_read(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String session_version, String default_version, String session_template, String default_template, String session_stylesheet, String default_stylesheet, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		preview_read(server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, session_version, default_version, "", "", "", session_template, "", default_template, session_stylesheet, "", default_stylesheet, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public void preview_read(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String session_version, String default_version, String session_template, String website_template, String default_template, String session_stylesheet, String website_stylesheet, String default_stylesheet, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		preview_read(server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, session_version, default_version, "", "", "", session_template, website_template, default_template, session_stylesheet, website_stylesheet, default_stylesheet, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public void preview_read(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_template, String website_template, String default_template, String session_stylesheet, String website_stylesheet, String default_stylesheet, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		read(server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, "content", "id", session_version, default_version, session_device, session_usersegments, session_usertests, session_template, website_template, default_template, session_stylesheet, website_stylesheet, default_stylesheet, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}



	public void archive_read(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String session_version, String default_version, String session_template, String default_template, String session_stylesheet, String default_stylesheet, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		archive_read(server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, session_version, default_version, session_template, "", default_template, session_stylesheet, "", default_stylesheet, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public void archive_read(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String session_version, String default_version, String session_template, String website_template, String default_template, String session_stylesheet, String website_stylesheet, String default_stylesheet, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		archive_read(server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, session_version, default_version, "", "", "", session_template, website_template, default_template, session_stylesheet, website_stylesheet, default_stylesheet, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public void archive_read(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_template, String website_template, String default_template, String session_stylesheet, String website_stylesheet, String default_stylesheet, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		read(server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, "content_archive", "archiveid", session_version, default_version, session_device, session_usersegments, session_usertests, session_template, website_template, default_template, session_stylesheet, website_stylesheet, default_stylesheet, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}



	public void public_read(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String session_version, String default_version, String session_template, String default_template, String session_stylesheet, String default_stylesheet, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		public_read(server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, session_version, default_version, "", "", "", session_template, "", default_template, session_stylesheet, "", default_stylesheet, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public void public_read(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String session_version, String default_version, String session_template, String website_template, String default_template, String session_stylesheet, String website_stylesheet, String default_stylesheet, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		public_read(server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, session_version, default_version, "", "", "", session_template, website_template, default_template, session_stylesheet, website_stylesheet, default_stylesheet, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public void public_read(ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_template, String website_template, String default_template, String session_stylesheet, String website_stylesheet, String default_stylesheet, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		read(server, request, response, session, script_name, query_string, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id, "content_public", "id", session_version, default_version, session_device, session_usersegments, session_usertests, session_template, website_template, default_template, session_stylesheet, website_stylesheet, default_stylesheet, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}



	public String pageclass_select_options(DB db, String selected) {
		StringBuilder options = new StringBuilder();
		if (selected.equals("page")) {
			options.append("<option value=\"page\" selected>page");
		} else {
			options.append("<option value=\"page\">page");
		}
		options.append(Common.select_options(db, "elements", "element", selected));
		if (selected.equals("template")) {
			options.append("<option value=\"template\" selected>template");
		} else {
			options.append("<option value=\"template\">template");
		}
		return "" + options;
	}



	public String pagetype_select_options(DB db, String selected) {
		return Common.select_options(db, "contenttypes", "contenttype", selected);
	}



	public String pagegroup_select_options(DB db, String selected) {
		return Common.select_options(db, "contentgroups", "contentgroup", selected);
	}



	public String usertype_select_options(DB db, String selected) {
		return dummy_user.usertype_select_options(db, selected);
	}



	public String usergroup_select_options(DB db, String selected) {
		return dummy_user.usergroup_select_options(db, selected);
	}



	public String User_template_select_options(DB db, String selected) {
		return dummy_user.template_select_options(db, selected);
	}



	public String Hosting_template_select_options(DB db, String selected) {
		return dummy_hosting.template_select_options(db, selected);
	}



	public String getDocType(DB db, Configuration myconfig) {
		return getDocType(db, myconfig, null);
	}
	public String getDocType(DB db, Configuration myconfig, Website mywebsite) {
		String mydoctype = "";
		if (! getDocType().equals("")) {
			mydoctype = getDocType();
		} else if ((template_content != null) && (! template_content.header(db, myconfig, "doctype", "").equals(""))) {
			mydoctype = template_content.header(db, myconfig, "doctype", "");
		} else if (! getContentgroupDocType().equals("")) {
			mydoctype = getContentgroupDocType();
		} else if (! getContenttypeDocType().equals("")) {
			mydoctype = getContenttypeDocType();
		}
		if ((mydoctype.equals("")) && (mywebsite != null)) {
			mydoctype = mywebsite.getDefaultDocType();
		}
		if (mydoctype.equals("")) {
			mydoctype = myconfig.get(db, "doctype");
		}
		return mydoctype;
	}



	public String header(DB db, String item, String item2) {
		return header(db, (Configuration) null, item, item2);
	}
	public String header(DB db, Configuration myconfig, String item, String item2) {
		if (item.equals("doctype")) {
			if (! getDocType().equals("")) {
				return getDocType();
			} else if ((template_content != null) && (! template_content.header(db, myconfig, item, item2).equals(""))) {
				return template_content.header(db, myconfig, item, item2);
			} else if (! getContentgroupDocType().equals("")) {
				return getContentgroupDocType();
			} else if (! getContenttypeDocType().equals("")) {
				return getContenttypeDocType();
			} else {
				return "";
			}
		} else if (item.equals("title")) {
//			return super.header(db, myconfig, item, item2);
			String mytitle_prefix = "" + getTitlePrefix();
			String mytitle_suffix = "" + getTitleSuffix();
			if ((mytitle_prefix.equals("")) && (myconfig != null) && (! myconfig.get(db, "default_title_prefix").equals(""))) mytitle_prefix = mytitle_prefix + myconfig.get(db, "default_title_prefix") + " ";
			if ((mytitle_suffix.equals("")) && (myconfig != null) && (! myconfig.get(db, "default_title_suffix").equals(""))) mytitle_suffix = mytitle_suffix + " " + myconfig.get(db, "default_title_suffix");
			return mytitle_prefix + page_title + mytitle_suffix;
		} else if (template_content != null) {
			if (item.equals("metainfo")) {
				return template_content.header(db, myconfig, item, item2) + super.header(db, myconfig, item, item2);
			} else if (item.equals("htmlheader")) {
				String elements_content = "";
				if (getElementContent() != null) {
					Iterator elementitems = getElementContent().keySet().iterator();
					while (elementitems.hasNext()) {
						String elementitem = "" + elementitems.next();
						if ((elementitem != null) && (! elementitem.equals("")) && (getElementContent().get(elementitem) != null)) {
							for (int i=0; i<((Content[])((HashMap)getElementContent()).get(elementitem)).length; i++) {
								elements_content += ((Content[])((HashMap)getElementContent()).get(elementitem))[i].header(db, myconfig, item, item2);
							}
						}
					}
				}
				return template_content.header(db, myconfig, item, item2) + super.header(db, myconfig, item, item2) + elements_content;
			} else if (item.equals("htmlbodyonload")) {
				String elements_content = "";
				if (getElementContent() != null) {
					Iterator elementitems = getElementContent().keySet().iterator();
					while (elementitems.hasNext()) {
						String elementitem = "" + elementitems.next();
						if ((elementitem != null) && (! elementitem.equals("")) && (getElementContent().get(elementitem) != null)) {
							for (int i=0; i<((Content[])((HashMap)getElementContent()).get(elementitem)).length; i++) {
								elements_content += ((Content[])((HashMap)getElementContent()).get(elementitem))[i].header(db, myconfig, item, item2);
							}
						}
					}
				}
				String mybody = template_content.header(db, myconfig, item, item2);
				mybody = mergeattributes(mybody, super.header(db, myconfig, item, item2));
				mybody = mergeattributes(mybody, elements_content);
				return mybody;
			} else {
				return (template_content.header(db, myconfig, item, item2) + " " + super.header(db, myconfig, item, item2)).replaceAll("^ ", "");
			}
		} else {
			return super.header(db, myconfig, item, item2);
		}
	}



	static public String mergeattributes(String mybody, String mybody2) {
		Matcher matches = Pattern.compile("(^| )([a-zA-Z0-9]+)=((\"(.*?)\")|([a-zA-Z0-9]+))").matcher(mybody2);
		while (matches.find()) {
			String myname = matches.group(2);
			String myvalue = "";
			if ((matches.group(5) != null) && (! (""+matches.group(5)).equals(""))) {
				myvalue = ""+matches.group(5);
			} else {
				myvalue = ""+matches.group(6);
			}
			if (Pattern.compile("(^| )(\\Q" + myname + "\\E)=\"(.*?)\"").matcher(mybody).find()) {
				mybody = mybody.replaceAll("(^| )(\\Q" + myname + "\\E)=\"(.*?)\"", "$1$2=\"$3 " + myvalue + "\"");
			} else if (Pattern.compile("(^| )(\\Q" + myname + "\\E)=([a-zA-Z0-9]+)").matcher(mybody).find()) {
				mybody = mybody.replaceAll("(^| )(\\Q" + myname + "\\E)=\"(.*?)\"", "$1$2=\"$3 " + myvalue + "\"");
			} else {
				mybody += " " + myname + "=\"" + myvalue + "\"";
			}
		}
		return mybody;
	}



	public String display(String item, String item2, String script_name, String query_string, String session_mode, String session_administrator) {
		if (item.equals("body")) {
			return body;
		} else if (! item2.equals("")) {
			if (getElementContent().get(item) != null) {
				String myoutput = "";
				for (int i=0; i<((Content[]) getElementContent().get(item)).length; i++) {
					myoutput += ((Content[]) getElementContent().get(item))[i].display(item2, true, script_name, query_string, session_mode, session_administrator);
				}
				return myoutput;
			} else {
				return "";
			}
		} else {
			return super.display(item, true, script_name, query_string, session_mode, session_administrator);
		}
	}



	public void parse_output_product(ServletContext server, Session session, Request request, Response response, DB db, Configuration config) {
		if (! getContentClass().equals("product")) return;
		body = parse_output_product(server, session, request, response, db, config, body);
	}
	public String parse_output_product(ServletContext server, Session session, Request request, Response response, DB db, Configuration config, String mycontent) {
		if (! getContentClass().equals("product")) return mycontent;
		if (mycontent.contains("@@@id@@@")) mycontent = mycontent.replaceAll("@@@id@@@", getId().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@class@@@")) mycontent = mycontent.replaceAll("@@@class@@@", getContentClass().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@code@@@")) mycontent = mycontent.replaceAll("@@@code@@@", getProductCode().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));

		Shopcart myshopcart = new Shopcart();
		Currency mycurrency = new Currency();
		mycurrency.read(db, session.get("currency"));
		if (mycurrency.getRate().equals("")) {
			mycurrency.read(db, config.get(db, "default_currency"));
		}
		myshopcart.setOrderCurrency(mycurrency);
		if (! session.get("delivery_country").equals("")) {
			myshopcart.setDeliveryCountry(session.get("delivery_country"));
		} else if (! session.get("default_country").equals("")) {
			myshopcart.setDeliveryCountry(session.get("default_country"));
		} else if (! config.get(db, "default_country").equals("")) {
			myshopcart.setDeliveryCountry(config.get(db, "default_country"));
		}
		if (! session.get("delivery_state").equals("")) {
			myshopcart.setDeliveryState(session.get("delivery_state"));
		} else if (! session.get("default_state").equals("")) {
			myshopcart.setDeliveryState(session.get("default_state"));
		} else if (! config.get(db, "default_state").equals("")) {
			myshopcart.setDeliveryState(config.get(db, "default_state"));
		}
		myshopcart.add(getId());
		myshopcart.calculate(server, session, request, response, session.get("mode"), session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config, session.get("version"), "", "", "", "", false, false);
		double price_list = Common.number(getDisplayPrice());
		double price_list_tax = Common.number(myshopcart.getTaxTotal());
		double price_list_shipping = Common.number(myshopcart.getShippingTotal());
		double price_list_taxed = price_list + price_list_tax;
		double price_list_shipped = price_list_taxed + price_list_shipping;
		myshopcart.calculate(server, session, request, response, session.get("mode"), session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config, session.get("version"), "", "", "", "", false, true);
		price_list = Common.number(getDisplayPrice());
		double price_discount = Common.number(myshopcart.getDiscountTotal());
		double price_discounted = price_list - price_discount;
		double price_tax = Common.number(myshopcart.getTaxTotal());
		double price_taxed = price_discounted + price_tax;
		double price_shipping = Common.number(myshopcart.getShippingTotal());
		double price_shipped = price_taxed + price_shipping;
		if (mycontent.contains("@@@price@@@")){
			if (! session.get("default_price").equals("")) {
				mycontent = mycontent.replaceAll("@@@price@@@", session.get("default_price").replaceAll("@@@price@@@", Common.numberformat(getDisplayPrice(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true"))));
			} else if (! config.get(db, "default_price").equals("")) {
				mycontent = mycontent.replaceAll("@@@price@@@", config.get(db, "default_price").replaceAll("@@@price@@@", Common.numberformat(getDisplayPrice(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true"))));
			} else {
				mycontent = mycontent.replaceAll("@@@price@@@", Common.numberformat(getDisplayPrice(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
			}
		}
		if (mycontent.contains("@@@price_list@@@")) mycontent = mycontent.replaceAll("@@@price_list@@@", Common.numberformat(price_list, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (mycontent.contains("@@@price_discount@@@")) mycontent = mycontent.replaceAll("@@@price_discount@@@", Common.numberformat(price_discount, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (mycontent.contains("@@@price_discount_description@@@")) mycontent = mycontent.replaceAll("@@@price_discount_description@@@", myshopcart.getDiscountDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@price_discounted@@@")) mycontent = mycontent.replaceAll("@@@price_discounted@@@", Common.numberformat(price_discounted, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (mycontent.contains("@@@price_tax@@@")) mycontent = mycontent.replaceAll("@@@price_tax@@@", Common.numberformat(price_tax, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (mycontent.contains("@@@price_tax_description@@@")) mycontent = mycontent.replaceAll("@@@price_tax_description@@@", myshopcart.getTaxDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@price_taxed@@@")) mycontent = mycontent.replaceAll("@@@price_taxed@@@", Common.numberformat(price_taxed, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (mycontent.contains("@@@price_shipping@@@")) mycontent = mycontent.replaceAll("@@@price_shipping@@@", Common.numberformat(price_shipping, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (mycontent.contains("@@@price_shipping_description@@@")) mycontent = mycontent.replaceAll("@@@price_shipping_description@@@", myshopcart.getShippingDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@price_shipped@@@")) mycontent = mycontent.replaceAll("@@@price_shipped@@@", Common.numberformat(price_shipped, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (mycontent.contains("@@@price_list_tax@@@")) mycontent = mycontent.replaceAll("@@@price_list_tax@@@", Common.numberformat(price_list_tax, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (mycontent.contains("@@@price_list_taxed@@@")) mycontent = mycontent.replaceAll("@@@price_list_taxed@@@", Common.numberformat(price_list_taxed, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (mycontent.contains("@@@price_list_shipping@@@")) mycontent = mycontent.replaceAll("@@@price_list_shipping@@@", Common.numberformat(price_list_shipping, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (mycontent.contains("@@@price_list_shipped@@@")) mycontent = mycontent.replaceAll("@@@price_list_shipped@@@", Common.numberformat(price_list_shipped, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (mycontent.contains("@@@currencytitle@@@")) mycontent = mycontent.replaceAll("@@@currencytitle@@@", getDisplayCurrencyTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@currency@@@")) mycontent = mycontent.replaceAll("@@@currency@@@", getDisplayCurrency().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));

// deprecated	mycontent = mycontent.replaceAll("@@@stock@@@", getProductStock().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
// deprecated	mycontent = mycontent.replaceAll("@@@comment@@@", getProductComment().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		int stock_amount = Common.intnumber(getProductStockAmount(db));
		if (getProductNoStockOrder().equals("")) {
			// unlimited
			if (mycontent.contains("@@@stock@@@")) mycontent = mycontent.replaceAll("@@@stock@@@", "<span id=\"WCMstock_" + getId() + "\" class=\"WCMstock WCMinstock\">" + stock_amount + "</span>");
			if (mycontent.contains("@@@stockcomment@@@")) mycontent = mycontent.replaceAll("@@@stockcomment@@@", "<span id=\"WCMstockcomment_" + getId() + "\" class=\"WCMstockcomment WCMinstock\">" + getProductStockText().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</span>");
			if (mycontent.contains("@@@comment@@@")) mycontent = mycontent.replaceAll("@@@comment@@@", "<span id=\"WCMstockcomment_" + getId() + "\" class=\"WCMstockcomment WCMinstock\">" + getProductStockText().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</span>");
			if (mycontent.contains("@@@stockstatus@@@")) mycontent = mycontent.replaceAll("@@@stockstatus@@@", "in");
		} else if (stock_amount<=0) {
			if (mycontent.contains("@@@stock@@@")) mycontent = mycontent.replaceAll("@@@stock@@@", "<span id=\"WCMstock_" + getId() + "\" class=\"WCMstock WCMnostock\">" + "0" + "</span>");
			if (mycontent.contains("@@@stockcomment@@@")) mycontent = mycontent.replaceAll("@@@stockcomment@@@", "<span id=\"WCMstockcomment_" + getId() + "\" class=\"WCMstockcomment WCMnostock\">" + getProductNoStockText().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</span>");
			if (mycontent.contains("@@@comment@@@")) mycontent = mycontent.replaceAll("@@@comment@@@", "<span id=\"WCMstockcomment_" + getId() + "\" class=\"WCMstockcomment WCMnostock\">" + getProductNoStockText().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</span>");
			if (mycontent.contains("@@@stockstatus@@@")) mycontent = mycontent.replaceAll("@@@stockstatus@@@", "no");
		} else if (stock_amount <= Common.intnumber(getProductLowStockAmount())) {
			if (mycontent.contains("@@@stock@@@")) mycontent = mycontent.replaceAll("@@@stock@@@", "<span id=\"WCMstock_" + getId() + "\" class=\"WCMstock WCMlowstock\">" + stock_amount + "</span>");
			if (mycontent.contains("@@@stockcomment@@@")) mycontent = mycontent.replaceAll("@@@stockcomment@@@", "<span id=\"WCMstockcomment_" + getId() + "\" class=\"WCMstockcomment WCMlowstock\">" + getProductLowStockText().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</span>");
			if (mycontent.contains("@@@comment@@@")) mycontent = mycontent.replaceAll("@@@comment@@@", "<span id=\"WCMstockcomment_" + getId() + "\" class=\"WCMstockcomment WCMlowstock\">" + getProductLowStockText().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</span>");
			if (mycontent.contains("@@@stockstatus@@@")) mycontent = mycontent.replaceAll("@@@stockstatus@@@", "low");
		} else {
			if (mycontent.contains("@@@stock@@@")) mycontent = mycontent.replaceAll("@@@stock@@@", "<span id=\"WCMstock_" + getId() + "\" class=\"WCMstock WCMinstock\">" + stock_amount + "</span>");
			if (mycontent.contains("@@@stockcomment@@@")) mycontent = mycontent.replaceAll("@@@stockcomment@@@", "<span id=\"WCMstockcomment_" + getId() + "\" class=\"WCMstockcomment WCMinstock\">" + getProductStockText().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</span>");
			if (mycontent.contains("@@@comment@@@")) mycontent = mycontent.replaceAll("@@@comment@@@", "<span id=\"WCMstockcomment_" + getId() + "\" class=\"WCMstockcomment WCMinstock\">" + getProductStockText().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</span>");
			if (mycontent.contains("@@@stockstatus@@@")) mycontent = mycontent.replaceAll("@@@stockstatus@@@", "in");
		}
		String options = "";
		String[] my_product_options = getProductOptions().split("[\\r\\n]+");
		for (int i=0; i<my_product_options.length; i++) {
			String product_option = my_product_options[i];
			Pattern p = Pattern.compile("<([^<>]+?)>([^<>]*?)<\\/([^<>]+?)>");
			Matcher m = p.matcher(product_option);
			if (m.find()) {
				String myname = "" + m.group(1);
				String myvalue = "" + m.group(2);
				if (myname.equals("hosting:subdomain")) {
					options += "<div>" + text.display("hosting.subdomain") + ":<br><nobr>" + config.get(db, "productdelivery_hosting_www") + "<input class=\"product_options\" type=\"text\" size=\"20\" name=\"" + getId() + "_" + (i+1) + "\" value=\"" + html.encode(myvalue) + "\">" + config.get(db, "productdelivery_hosting_domain") + "</nobr></div>";
				} else if (myname.equals("hosting:domain")) {
					options += "<div>" + text.display("hosting.domain") + ":<br><nobr>" + config.get(db, "productdelivery_hosting_www") + "<input class=\"product_options\" type=\"text\" size=\"20\" name=\"" + getId() + "_" + (i+1) + "\" value=\"" + html.encode(myvalue) + "\">" + "</nobr></div>";
				} else if (myname.startsWith("hosting:domain:")) {
					// ignore
				} else if ((myname.startsWith("hosting:config:")) && (myvalue.equals(""))) {
					options += "<div>" + myname.substring(15).replaceAll("_", " ") + ":<br><input class=\"product_options\" type=\"text\" size=\"20\" name=\"" + getId() + "_" + (i+1) + "\" value=\"" + myvalue + "\"></div>";
				} else if ((! myname.startsWith("hosting:")) && (! myname.startsWith("user:"))) {
					String[] product_option_values = myvalue.split("\\|");
					if (product_option_values.length == 1) {
						options += "<div>" + myname + ":<br><input class=\"product_options\" type=\"text\" size=\"20\" name=\"" + getId() + "_" + (i+1) + "\" value=\"" + myvalue + "\"></div>";
					} else {
						options += "<div>" + myname + ":<br><select class=\"product_options\" name=\"" + getId() + "_" + (i+1) + "\">";
						for (int j=0; j<product_option_values.length; j++) {
							String product_option_value = product_option_values[j];
							options += "<option>" + product_option_value + "</option>";
						}
						options += "</select></div>";
					}
				}
			}
		}
		if (mycontent.contains("@@@options@@@")) mycontent = mycontent.replaceAll("@@@options@@@", options.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if ((getProductProgram() != null) && (! getProductProgram().equals(""))) {
			if (mycontent.contains("@@@availability@@@")) {
				String product_availability = Common.execute("/" + text.display("adminpath") + "/productavailability/" + getProductProgram() + ".jsp", null, null, "productavailability", server, session, request, response);
				product_availability = product_availability.replaceAll("^[-+0-9]+ ", "");
				mycontent = mycontent.replaceAll("@@@availability@@@", product_availability.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			}
		}
		mycontent = parse_output_condition(request, mycontent);
		return mycontent;
	}



	public String parse_output_country(String list, ServletContext server, Request request, Response response, Session session, DB db, Configuration config) {
		String myoptions = "";
		String myid = "";
		String myclass = "";
		String myselect = "";
		String myselected = "";
		boolean mydiscounts = false;
		boolean mytax = false;
		boolean myshipping = false;
		String[] listparts = list.split(":");
		for (int i=0; i<listparts.length; i++) {
			String listpart = listparts[i];
			if (listpart.equals("discounts")) {
				mydiscounts = true;
			} else if (listpart.equals("tax")) {
				mytax = true;
			} else if (listpart.equals("shipping")) {
				myshipping = true;
			} else if (listpart.startsWith("id=")) {
				myid = "" + listpart.substring(3);
			} else if (listpart.startsWith("class=")) {
				myclass = "" + listpart.substring(6);
			} else if (listpart.startsWith("select=")) {
				myselect = "" + listpart.substring(7);
			} else if (listpart.startsWith("selected=")) {
				myselected = "" + listpart.substring(9);
			}
		}
		if (myselected.equals("SHOPCART")) {
			myselected = session.get(myselect);
		}
		LinkedHashMap<String,String> mycountries = new LinkedHashMap<String,String>();
		if (mydiscounts) {
			mycountries = Common.array_merge_values(mycountries, db.query_values("select distinct country from discounts order by country"));
		}
		if (mytax) {
			mycountries = Common.array_merge_values(mycountries, db.query_values("select distinct country from tax order by country"));
		}
		if (myshipping) {
			mycountries = Common.array_merge_values(mycountries, db.query_values("select distinct country from shipping order by country"));
		}
//		mycountries = Common.LinkedHashMapSortedByValue(mycountries, true);
		Iterator mycountry = mycountries.keySet().iterator();
		while (mycountry.hasNext()) {
			String mykey = "" + mycountry.next();
			String myvalue = "" + mycountries.get(mykey);
			if (! myvalue.equals("")) {
				if (myvalue.equals(myselected)) {
					myoptions += "<option value=\"" + myvalue + "\" selected>" + myvalue + "</option>";
				} else {
					myoptions += "<option value=\"" + myvalue + "\">" + myvalue + "</option>";
				}
			}
		}
		if (! myid.equals("")) {
			myid = " id=\"" + myid + "\"";
		}
		if (! myclass.equals("")) {
			myclass = " class=\"" + myclass + "\"";
		}
		if (! myselect.equals("")) {
			myoptions = "<select" + myid + " name=\"" + myselect + "\"" + myclass + "><option value=\"\">&nbsp;</option>" + myoptions + "</select>";
		}
		return myoptions;
	}



	public String parse_output_country_state(String list, ServletContext server, Request request, Response response, Session session, DB db, Configuration config) {
		String myoptions = "";
		String mycountry = "";
		String myid = "";
		String myclass = "";
		String myselect = "";
		String myselected = "";
		boolean mydiscounts = false;
		boolean mytax = false;
		boolean myshipping = false;
		String[] listparts = list.split(":");
		for (int i=0; i<listparts.length; i++) {
			String listpart = listparts[i];
			if (listpart.startsWith("country=")) {
				mycountry = "" + listpart.substring(8);
			} else if (listpart.equals("discounts")) {
				mydiscounts = true;
			} else if (listpart.equals("tax")) {
				mytax = true;
			} else if (listpart.equals("shipping")) {
				myshipping = true;
			} else if (listpart.startsWith("id=")) {
				myid = "" + listpart.substring(3);
			} else if (listpart.startsWith("class=")) {
				myclass = "" + listpart.substring(6);
			} else if (listpart.startsWith("select=")) {
				myselect = "" + listpart.substring(7);
			} else if (listpart.startsWith("selected=")) {
				myselected = "" + listpart.substring(9);
			}
		}
		if (mycountry.equals("SHOPCART")) {
			mycountry = session.get(myselect.replaceAll("state", "country"));
		}
		if (myselected.equals("SHOPCART")) {
			myselected = session.get(myselect);
		}
		LinkedHashMap<String,String> mystates = new LinkedHashMap<String,String>();
		if (mydiscounts) {
			if (mycountry.equals("")) {
				mystates = Common.array_merge_values(mystates, db.query_values("select distinct state from discounts order by country,state"));
			} else {
				mystates = Common.array_merge_values(mystates, db.query_values("select distinct state from discounts where country=" + db.quote(mycountry) + " order by country,state"));
			}
		}
		if (mytax) {
			if (mycountry.equals("")) {
				mystates = Common.array_merge_values(mystates, db.query_values("select distinct state from tax order by country,state"));
			} else {
				mystates = Common.array_merge_values(mystates, db.query_values("select distinct state from tax where country=" + db.quote(mycountry) + " order by country,state"));
			}
		}
		if (myshipping) {
			if (mycountry.equals("")) {
				mystates = Common.array_merge_values(mystates, db.query_values("select distinct state from shipping order by country,state"));
			} else {
				mystates = Common.array_merge_values(mystates, db.query_values("select distinct state from shipping where country=" + db.quote(mycountry) + " order by country,state"));
			}
		}
//		mystates = Common.LinkedHashMapSortedByValue(mystates, true);
		Iterator mystate = mystates.keySet().iterator();
		while (mystate.hasNext()) {
			String mykey = "" + mystate.next();
			String myvalue = "" + mystates.get(mykey);
			if (! myvalue.equals("")) {
				if (myvalue.equals(myselected)) {
					myoptions += "<option value=\"" + myvalue + "\" selected>" + myvalue + "</option>";
				} else {
					myoptions += "<option value=\"" + myvalue + "\">" + myvalue + "</option>";
				}
			}
		}
		if (! myid.equals("")) {
			myid = " id=\"" + myid + "\"";
		}
		if (! myclass.equals("")) {
			myclass = " class=\"" + myclass + "\"";
		}
		if (! myselect.equals("")) {
			myoptions = "<select" + myid + " name=\"" + myselect + "\"" + myclass + "><option value=\"\">&nbsp;</option>" + myoptions + "</select>";
		}
		return myoptions;
	}



	public void parse_output_guestbook(DB db, Configuration config, Page guestbookentry, String session_version) {
		parse_output_guestbook(db, config, guestbookentry, null, session_version, "", "", "");
	}
	public void parse_output_guestbook(DB db, Configuration config, Page guestbookentry, Session session, String session_version, String session_device, String session_usersegments, String session_usertests) {
		StringBuilder guestbookcontent = new StringBuilder();
		String SQL = "select * from guestbook where " + db.is_not_blank("published") + " order by id desc";
		Guestbook results = new Guestbook();
		results.records(db, SQL);
		while (results.records(db, "")) {
			String mybody = guestbookentry.getContent();
			if (mybody.contains("@@@id@@@")) mybody = mybody.replaceAll("@@@id@@@", results.getId().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@created@@@")) mybody = mybody.replaceAll("@@@created@@@", results.getCreated());
			if (mybody.contains("@@@updated@@@")) mybody = mybody.replaceAll("@@@updated@@@", results.getUpdated());
			if (mybody.contains("@@@published@@@")) mybody = mybody.replaceAll("@@@published@@@", results.getPublished());
			if (mybody.contains("@@@title@@@")) mybody = mybody.replaceAll("@@@title@@@", results.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@content@@@")) mybody = mybody.replaceAll("@@@content@@@", results.getContent().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@name@@@")) mybody = mybody.replaceAll("@@@name@@@", results.getName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@email@@@")) mybody = mybody.replaceAll("@@@email@@@", results.getEmail().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@website@@@")) mybody = mybody.replaceAll("@@@website@@@", results.getWebsite().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@company@@@")) mybody = mybody.replaceAll("@@@company@@@", results.getCompany().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			mybody = guestbookentry.adjust_links(session_version, mybody, db, config);
			guestbookcontent.append(mybody);
		}
		body = body.replaceAll("@@@guestbook@@@", ("" + guestbookcontent).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
	}



	public void parse_output_shopcart(Shopcart shopcart, String payment, String delivery, ServletContext server, Request request, Response response, Session session, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, DB db, Configuration config, Page item, boolean editoptions) {
		parse_output_shopcart(shopcart, payment, delivery, server, request, response, session, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, db, config, item, editoptions, true);
	}
	public void parse_output_shopcart(Shopcart shopcart, String payment, String delivery, ServletContext server, Request request, Response response, Session session, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, DB db, Configuration config, Page item, boolean editoptions, boolean stockcheck) {
		parse_output_shopcart(shopcart, payment, delivery, server, request, response, session, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, "", "", "", db, config, item, editoptions, stockcheck);
	}
	public void parse_output_shopcart(Shopcart shopcart, String payment, String delivery, ServletContext server, Request request, Response response, Session session, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, DB db, Configuration config, Page item, boolean editoptions) {
		parse_output_shopcart(shopcart, payment, delivery, server, request, response, session, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, db, config, item, editoptions, true);
	}
	public void parse_output_shopcart(Shopcart shopcart, String payment, String delivery, ServletContext server, Request request, Response response, Session session, String session_mode, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, DB db, Configuration config, Page item, boolean editoptions, boolean stockcheck) {
		shopcart.calculate(server, session, request, response, session_mode, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, session_version, default_version, session_device, session_usersegments, session_usertests, stockcheck);
		StringBuilder items = new StringBuilder();
		Iterator shopcartitems = shopcart.getShoppingCartItemsSorted().keySet().iterator();
		while (shopcartitems.hasNext()) {
			Orderitem shopcartitem = (Orderitem) shopcart.getShoppingCartItems().get(shopcartitems.next());
			String mybody = item.getContent();
			if (mybody.contains("@@@id@@@")) mybody = mybody.replaceAll("@@@id@@@", shopcartitem.getProductId().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@class@@@")) mybody = mybody.replaceAll("@@@class@@@", "product");
			if (mybody.contains("@@@version@@@")) mybody = mybody.replaceAll("@@@version@@@", shopcartitem.getVersion().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@device@@@")) mybody = mybody.replaceAll("@@@device@@@", shopcartitem.getDevice().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@filename@@@")) mybody = mybody.replaceAll("@@@filename@@@", shopcartitem.getServerFilename().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@title@@@")) mybody = mybody.replaceAll("@@@title@@@", shopcartitem.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@summary@@@")) mybody = mybody.replaceAll("@@@summary@@@", shopcartitem.getSummary().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@image1@@@")) mybody = mybody.replaceAll("@@@image1@@@", shopcartitem.getImage1().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@image2@@@")) mybody = mybody.replaceAll("@@@image2@@@", shopcartitem.getImage2().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@image3@@@")) mybody = mybody.replaceAll("@@@image3@@@", shopcartitem.getImage3().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@file1@@@")) mybody = mybody.replaceAll("@@@file1@@@", shopcartitem.getFile1().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@file2@@@")) mybody = mybody.replaceAll("@@@file2@@@", shopcartitem.getFile2().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@file3@@@")) mybody = mybody.replaceAll("@@@file3@@@", shopcartitem.getFile3().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@link1@@@")) mybody = mybody.replaceAll("@@@link1@@@", shopcartitem.getLink1().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@link2@@@")) mybody = mybody.replaceAll("@@@link2@@@", shopcartitem.getLink2().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@link3@@@")) mybody = mybody.replaceAll("@@@link3@@@", shopcartitem.getLink3().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@author@@@")) mybody = mybody.replaceAll("@@@author@@@", shopcartitem.getAuthor().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@keywords@@@")) mybody = mybody.replaceAll("@@@keywords@@@", shopcartitem.getKeywords().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@description@@@")) mybody = mybody.replaceAll("@@@description@@@", shopcartitem.getDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@code@@@")) mybody = mybody.replaceAll("@@@code@@@", shopcartitem.getProductCode().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@weight@@@")) mybody = mybody.replaceAll("@@@weight@@@", shopcartitem.getProductWeight().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@volume@@@")) mybody = mybody.replaceAll("@@@volume@@@", shopcartitem.getProductVolume().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@width@@@")) mybody = mybody.replaceAll("@@@width@@@", shopcartitem.getProductWidth().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@height@@@")) mybody = mybody.replaceAll("@@@height@@@", shopcartitem.getProductHeight().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@depth@@@")) mybody = mybody.replaceAll("@@@depth@@@", shopcartitem.getProductDepth().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@brand@@@")) mybody = mybody.replaceAll("@@@brand@@@", shopcartitem.getProductBrand().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@colour@@@")) mybody = mybody.replaceAll("@@@colour@@@", shopcartitem.getProductColour().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@size@@@")) mybody = mybody.replaceAll("@@@size@@@", shopcartitem.getProductSize().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@period@@@")) mybody = mybody.replaceAll("@@@period@@@", text.display("content.productdetails.period." + shopcartitem.getProductPeriod().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
			if (mybody.contains("@@@stock@@@")) mybody = mybody.replaceAll("@@@stock@@@", shopcartitem.getProductStock().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@stockstatus@@@")) mybody = mybody.replaceAll("@@@stockstatus@@@", shopcartitem.getProductStatus().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@stockcomment@@@")) mybody = mybody.replaceAll("@@@stockcomment@@@", shopcartitem.getProductComment().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@comment@@@")) mybody = mybody.replaceAll("@@@comment@@@", shopcartitem.getProductComment().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@quantity@@@")) mybody = mybody.replaceAll("@@@quantity@@@", shopcartitem.getItemQuantity().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			int quantity = Common.intnumber(shopcartitem.getItemQuantity());
			if (quantity == 0) quantity = 1;
			double price_list = Common.number(shopcartitem.getItemSubtotal()) / quantity;
			double price_discount = Common.number(shopcartitem.getDiscountTotal()) / quantity;
			double price_discounted = price_list - price_discount;
			double price_tax = Common.number(shopcartitem.getTaxTotal()) / quantity;
			double price_taxed = price_discounted + price_tax;
			double price_shipping = Common.number(shopcartitem.getShippingTotal()) / quantity;
			double price_shipped = price_taxed + price_shipping;
			double subtotal_list = Common.number(shopcartitem.getItemSubtotal());
			double subtotal_discount = Common.number(shopcartitem.getDiscountTotal());
			double subtotal_discounted = subtotal_list - subtotal_discount;
			double subtotal_tax = Common.number(shopcartitem.getTaxTotal());
			double subtotal_taxed = subtotal_discounted + subtotal_tax;
			double subtotal_shipping = Common.number(shopcartitem.getShippingTotal());
			double subtotal_shipped = subtotal_taxed + subtotal_shipping;
//			mybody = mybody.replaceAll("@@@price@@@", Common.numberformat(shopcartitem.getProductPrice(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
			if (mybody.contains("@@@price@@@")) {
				if (! session.get("default_price").equals("")) {
					mybody = mybody.replaceAll("@@@price@@@", session.get("default_price").replaceAll("@@@price@@@", Common.numberformat(shopcartitem.getProductPrice(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true"))));
				} else if (! config.get(db, "default_price").equals("")) {
					mybody = mybody.replaceAll("@@@price@@@", config.get(db, "default_price").replaceAll("@@@price@@@", Common.numberformat(shopcartitem.getProductPrice(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true"))));
				} else {
					mybody = mybody.replaceAll("@@@price@@@", Common.numberformat(shopcartitem.getProductPrice(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
				}
			}
			if (mybody.contains("@@@price_list@@@")) mybody = mybody.replaceAll("@@@price_list@@@", Common.numberformat(price_list, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
			if (mybody.contains("@@@price_discount@@@")) mybody = mybody.replaceAll("@@@price_discount@@@", Common.numberformat(price_discount, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
			if (mybody.contains("@@@price_discount_description@@@")) mybody = mybody.replaceAll("@@@price_discount_description@@@", shopcartitem.getDiscountDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@price_discounted@@@")) mybody = mybody.replaceAll("@@@price_discounted@@@", Common.numberformat(price_discounted, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
			if (mybody.contains("@@@price_tax@@@")) mybody = mybody.replaceAll("@@@price_tax@@@", Common.numberformat(price_tax, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
			if (mybody.contains("@@@price_tax_description@@@")) mybody = mybody.replaceAll("@@@price_tax_description@@@", shopcartitem.getTaxDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@price_taxed@@@")) mybody = mybody.replaceAll("@@@price_taxed@@@", Common.numberformat(price_taxed, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
			if (mybody.contains("@@@price_shipping@@@")) mybody = mybody.replaceAll("@@@price_shipping@@@", Common.numberformat(price_shipping, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
			if (mybody.contains("@@@price_shipping_description@@@")) mybody = mybody.replaceAll("@@@price_shipping_description@@@", shopcartitem.getShippingDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@price_shipped@@@")) mybody = mybody.replaceAll("@@@price_shipped@@@", Common.numberformat(price_shipped, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
			if (mybody.contains("@@@currencytitle@@@")) mybody = mybody.replaceAll("@@@currencytitle@@@", shopcartitem.getProductCurrencyTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@currency@@@")) mybody = mybody.replaceAll("@@@currency@@@", shopcartitem.getProductCurrency().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mybody.contains("@@@subtotal@@@")) mybody = mybody.replaceAll("@@@subtotal@@@", Common.numberformat(subtotal_list, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
			if (mybody.contains("@@@subtotal_discounted@@@")) mybody = mybody.replaceAll("@@@subtotal_discounted@@@", Common.numberformat(subtotal_discounted, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
			if (mybody.contains("@@@subtotal_taxed@@@")) mybody = mybody.replaceAll("@@@subtotal_taxed@@@", Common.numberformat(subtotal_taxed, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
			if (mybody.contains("@@@subtotal_shipped@@@")) mybody = mybody.replaceAll("@@@subtotal_shipped@@@", Common.numberformat(subtotal_shipped, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
			if (mybody.contains("@@@total@@@")) mybody = mybody.replaceAll("@@@total@@@", Common.numberformat(shopcartitem.getItemTotal(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
			if (Common.number(shopcartitem.getDiscountTotal()) != 0) {
				if (mybody.contains("@@@discount_description@@@")) mybody = mybody.replaceAll("@@@discount_description@@@", shopcartitem.getDiscountDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				if (mybody.contains("@@@discount_currency@@@")) mybody = mybody.replaceAll("@@@discount_currency@@@", shopcartitem.getProductCurrency().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				if (mybody.contains("@@@discount_currencytitle@@@")) mybody = mybody.replaceAll("@@@discount_currencytitle@@@", shopcartitem.getProductCurrencyTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				if (mybody.contains("@@@discount@@@")) mybody = mybody.replaceAll("@@@discount@@@", Common.numberformat(shopcartitem.getDiscountTotal(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
				mybody = mybody.replaceAll("(?s)@@@discount:(.*?)@@@", "$1");
			} else {
				if (mybody.contains("@@@discount_description@@@")) mybody = mybody.replaceAll("@@@discount_description@@@", "");
				if (mybody.contains("@@@discount_currency@@@")) mybody = mybody.replaceAll("@@@discount_currency@@@", "");
				if (mybody.contains("@@@discount_currencytitle@@@")) mybody = mybody.replaceAll("@@@discount_currencytitle@@@", "");
				if (mybody.contains("@@@discount@@@")) mybody = mybody.replaceAll("@@@discount@@@", "");
				mybody = mybody.replaceAll("(?s)@@@discount:(.*?)@@@", "");
			}
			if (Common.number(shopcartitem.getShippingTotal()) != 0) {
				if (mybody.contains("@@@shipping_description@@@")) mybody = mybody.replaceAll("@@@shipping_description@@@", shopcartitem.getShippingDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				if (mybody.contains("@@@shipping_currency@@@")) mybody = mybody.replaceAll("@@@shipping_currency@@@", shopcartitem.getProductCurrency().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				if (mybody.contains("@@@shipping_currencytitle@@@")) mybody = mybody.replaceAll("@@@shipping_currencytitle@@@", shopcartitem.getProductCurrencyTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				if (mybody.contains("@@@shipping@@@")) mybody = mybody.replaceAll("@@@shipping@@@", Common.numberformat(shopcartitem.getShippingTotal(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
				mybody = mybody.replaceAll("(?s)@@@shipping:(.*?)@@@", "$1");
			} else {
				if (mybody.contains("@@@shipping_description@@@")) mybody = mybody.replaceAll("@@@shipping_description@@@", "");
				if (mybody.contains("@@@shipping_currency@@@")) mybody = mybody.replaceAll("@@@shipping_currency@@@", "");
				if (mybody.contains("@@@shipping_currencytitle@@@")) mybody = mybody.replaceAll("@@@shipping_currencytitle@@@", "");
				if (mybody.contains("@@@shipping@@@")) mybody = mybody.replaceAll("@@@shipping@@@", "");
				mybody = mybody.replaceAll("(?s)@@@shipping:(.*?)@@@", "");
			}
			if (Common.number(shopcartitem.getTaxTotal()) != 0) {
				if (mybody.contains("@@@tax_description@@@")) mybody = mybody.replaceAll("@@@tax_description@@@", shopcartitem.getTaxDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				if (mybody.contains("@@@tax_currency@@@")) mybody = mybody.replaceAll("@@@tax_currency@@@", shopcartitem.getProductCurrency().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				if (mybody.contains("@@@tax_currencytitle@@@")) mybody = mybody.replaceAll("@@@tax_currencytitle@@@", shopcartitem.getProductCurrencyTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				if (mybody.contains("@@@tax@@@")) mybody = mybody.replaceAll("@@@tax@@@", Common.numberformat(shopcartitem.getTaxTotal(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
				mybody = mybody.replaceAll("(?s)@@@tax:(.*?)@@@", "$1");
			} else {
				if (mybody.contains("@@@tax_description@@@")) mybody = mybody.replaceAll("@@@tax_description@@@", "");
				if (mybody.contains("@@@tax_currency@@@")) mybody = mybody.replaceAll("@@@tax_currency@@@", "");
				if (mybody.contains("@@@tax_currencytitle@@@")) mybody = mybody.replaceAll("@@@tax_currencytitle@@@", "");
				if (mybody.contains("@@@tax@@@")) mybody = mybody.replaceAll("@@@tax@@@", "");
				mybody = mybody.replaceAll("(?s)@@@tax:(.*?)@@@", "");
			}
			String options = "";
			String[] product_options_selected = ("" + shopcart.getShoppingCart().get(shopcartitem.getProductId()) + "|||||||||||||||||||| ").split("\\|");
			String[] product_options = shopcartitem.getProductOptions().split("[\\r\\n]+");
			for (int i=0; i<product_options.length; i++) {
				String product_option = product_options[i];
				Pattern p = Pattern.compile("<([^<>]+?)>([^<>]*?)<\\/([^<>]+?)>");
				Matcher m = p.matcher(product_option);
				if (m.find()) {
					String myname = "" + m.group(1);
					String myvalue = "" + m.group(2);
					if ((myname.equals("hosting:subdomain")) && (myvalue.equals(""))) {
						if (editoptions) {
							options += "<div>" + text.display("hosting.subdomain") + ":<br><nobr>" + config.get(db, "productdelivery_hosting_www") + "<input class=\"product_options\" type=\"text\" size=\"20\" name=\"" + shopcartitem.getProductId() + "_" + (i+1) + "\" value=\"" + html.encode(product_options_selected[i+1]) + "\">" + config.get(db, "productdelivery_hosting_domain") + "</nobr></div>";
						} else {
							options += "<div>" + text.display("hosting.subdomain") + ": <nobr>" + config.get(db, "productdelivery_hosting_www") + html.encode(product_options_selected[i+1]) + config.get(db, "productdelivery_hosting_domain") + "</nobr></div>";
						}
					} else if (myname.equals("hosting:domain")) {
						if (editoptions) {
							options += "<div>" + text.display("hosting.domain") + ":<br><nobr>" + config.get(db, "productdelivery_hosting_www") + "<input class=\"product_options\" type=\"text\" size=\"20\" name=\"" + shopcartitem.getProductId() + "_" + (i+1) + "\" value=\"" + html.encode(product_options_selected[i+1]) + "\">" + "</nobr></div>";
						} else {
							options += "<div>" + text.display("hosting.domain") + ": <nobr>" + config.get(db, "productdelivery_hosting_www") + html.encode(product_options_selected[i+1]) + "</nobr></div>";
						}
					} else if (myname.startsWith("hosting:domain:")) {
						// ignore
					} else if ((myname.startsWith("hosting:config:")) && (myvalue.equals(""))) {
						if (editoptions) {
							options += "<div>" + myname.substring(15).replaceAll("_", " ") + ":<br><input class=\"product_options\" type=\"text\" size=\"20\" name=\"" + shopcartitem.getProductId() + "_" + (i+1) + "\" value=\"" + html.encode(product_options_selected[i+1]) + "\"></div>";
						} else {
							options += "<div>" + myname.substring(15).replaceAll("_", " ") + ": " + html.encode(product_options_selected[i+1]) + "</div>";
						}
					} else if ((! myname.startsWith("hosting:")) && (! myname.startsWith("user:"))) {
						if (myvalue.equals("")) {
							if (editoptions) {
								options += "<div>" + myname + ":<br><input class=\"product_options\" type=\"text\" size=\"20\" name=\"" + shopcartitem.getProductId() + "_" + (i+1) + "\" value=\"" + html.encode(product_options_selected[i+1]) + "\"></div>";
							} else {
								options += "<div>" + myname + ": " + html.encode(product_options_selected[i+1]) + "</div>";
							}
						} else {
							if (editoptions) {
								options += "<div>" + myname + ":<br><select class=\"product_options\" name=\"" + shopcartitem.getProductId() + "_" + (i+1) + "\">";
							} else {
								options += "<div>" + myname + ": ";
							}
							String[] product_option_values = myvalue.split("\\|");
							for (int j=0; j<product_option_values.length; j++) {
								String product_option_value = product_option_values[j];
								if (product_options_selected[i+1].trim().equals(product_option_value.trim())) {
									if (editoptions) {
										options += "<option selected>" + product_option_value + "</option>";
									} else {
										options += " " + product_option_value;
									}
								} else {
									if (editoptions) {
										options += "<option>" + product_option_value + "</option>";
									}
								}
							}
							if (editoptions) {
								options += "</select></div>";
							} else {
								options += "</div>";
							}
						}
					}
				}
			}
			mybody = mybody.replaceAll("@@@options@@@", options.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if ((shopcartitem.getProductProgram() != null) && (! shopcartitem.getProductProgram().equals(""))) {
				shopcartitem.setShopcart(shopcart.getShoppingCart());
				String product_availability = Common.execute("/" + text.display("adminpath") + "/productavailability/" + shopcartitem.getProductProgram() + ".jsp", "shopcart", shopcart, "shopcartitem", shopcartitem, "productavailability", server, session, request, response);
				product_availability = product_availability.replaceAll("^[-+0-9]+ ", "");
				mybody = mybody.replaceAll("@@@availability@@@", product_availability.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			}
			mybody = item.adjust_links(session_version, mybody, db, config);
			items.append(mybody);
		}
		if (body.contains("@@@items@@@")) body = body.replaceAll("@@@items@@@", ("" + items).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@payment@@@")) body = body.replaceAll("@@@payment@@@", ("" + payment).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery@@@")) body = body.replaceAll("@@@delivery@@@", ("" + delivery).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@quantity@@@")) body = body.replaceAll("@@@quantity@@@", Common.numberformat(shopcart.getOrderQuantity(), 0));
		if (body.contains("@@@currencytitle@@@")) body = body.replaceAll("@@@currencytitle@@@", shopcart.getOrderCurrency().getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@currency@@@")) body = body.replaceAll("@@@currency@@@", shopcart.getOrderCurrency().getSymbol().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@subtotal@@@")) body = body.replaceAll("@@@subtotal@@@", Common.numberformat(shopcart.getOrderSubtotal(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (body.contains("@@@tax_currency@@@")) body = body.replaceAll("@@@tax_currency@@@", shopcart.getOrderCurrency().getSymbol().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@tax_currencytitle@@@")) body = body.replaceAll("@@@tax_currencytitle@@@", shopcart.getOrderCurrency().getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@tax_description@@@")) body = body.replaceAll("@@@tax_description@@@", "" + shopcart.getTaxDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@tax@@@")) body = body.replaceAll("@@@tax@@@", Common.numberformat(shopcart.getTaxTotal(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (Common.number(shopcart.getTaxTotal()) != 0) {
			body = body.replaceAll("(?s)@@@tax:(.*?)@@@", "$1");
		} else {
			body = body.replaceAll("(?s)@@@tax:(.*?)@@@", "");
		}
		if (body.contains("@@@shipping_currency@@@")) body = body.replaceAll("@@@shipping_currency@@@", shopcart.getOrderCurrency().getSymbol().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@shipping_currencytitle@@@")) body = body.replaceAll("@@@shipping_currencytitle@@@", shopcart.getOrderCurrency().getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@shipping_description@@@")) body = body.replaceAll("@@@shipping_description@@@", "" + shopcart.getShippingDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@shipping@@@")) body = body.replaceAll("@@@shipping@@@", Common.numberformat(shopcart.getShippingTotal(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (Common.number(shopcart.getShippingTotal()) != 0) {
			body = body.replaceAll("(?s)@@@shipping:(.*?)@@@", "$1");
		} else {
			body = body.replaceAll("(?s)@@@shipping:(.*?)@@@", "");
		}
		if (Common.number(shopcart.getDiscountTotal()) != 0) {
			if (body.contains("@@@discount_description@@@")) body = body.replaceAll("@@@discount_description@@@", "" + shopcart.getDiscountDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@discount_currency@@@")) body = body.replaceAll("@@@discount_currency@@@", "" + shopcart.getOrderCurrency().getSymbol().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@discount_currencytitle@@@")) body = body.replaceAll("@@@discount_currencytitle@@@", "" + shopcart.getOrderCurrency().getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@discount@@@")) body = body.replaceAll("@@@discount@@@", Common.numberformat(shopcart.getDiscountTotal(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
			body = body.replaceAll("(?s)@@@discount:(.*?)@@@", "$1");
		} else {
			if (body.contains("@@@discount_description@@@")) body = body.replaceAll("@@@discount_description@@@", "");
			if (body.contains("@@@discount_currency@@@")) body = body.replaceAll("@@@discount_currency@@@", "");
			if (body.contains("@@@discount_currencytitle@@@")) body = body.replaceAll("@@@discount_currencytitle@@@", "");
			if (body.contains("@@@discount@@@")) body = body.replaceAll("@@@discount@@@", "");
			body = body.replaceAll("(?s)@@@discount:(.*?)@@@", "");
		}
		if (body.contains("@@@total@@@")) body = body.replaceAll("@@@total@@@", Common.numberformat(shopcart.getOrderTotal(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
if (body.contains("@@@card_")) {
		if (body.contains("@@@card_type@@@")) body = body.replaceAll("@@@card_type@@@", "" + shopcart.getCardType().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@card_number@@@")) body = body.replaceAll("@@@card_number@@@", "" + shopcart.getCardNumber().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@card_issuedmonth@@@")) body = body.replaceAll("@@@card_issuedmonth@@@", "" + shopcart.getCardIssuedMonth().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@card_issuedyear@@@")) body = body.replaceAll("@@@card_issuedyear@@@", "" + shopcart.getCardIssuedYear().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@card_expirymonth@@@")) body = body.replaceAll("@@@card_expirymonth@@@", "" + shopcart.getCardExpiryMonth().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@card_expiryyear@@@")) body = body.replaceAll("@@@card_expiryyear@@@", "" + shopcart.getCardExpiryYear().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@card_name@@@")) body = body.replaceAll("@@@card_name@@@", "" + shopcart.getCardName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@card_cvc@@@")) body = body.replaceAll("@@@card_cvc@@@", "" + shopcart.getCardCVC().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@card_issue@@@")) body = body.replaceAll("@@@card_issue@@@", "" + shopcart.getCardIssue().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@card_postalcode@@@")) body = body.replaceAll("@@@card_postalcode@@@", "" + shopcart.getCardPostalcode().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
}
if (body.contains("@@@delivery_")) {
		if (body.contains("@@@delivery_name@@@")) body = body.replaceAll("@@@delivery_name@@@", "" + shopcart.getDeliveryName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_organisation@@@")) body = body.replaceAll("@@@delivery_organisation@@@", "" + shopcart.getDeliveryOrganisation().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_address@@@")) body = body.replaceAll("@@@delivery_address@@@", "" + shopcart.getDeliveryAddress().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_postalcode@@@")) body = body.replaceAll("@@@delivery_postalcode@@@", "" + shopcart.getDeliveryPostalcode().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_city@@@")) body = body.replaceAll("@@@delivery_city@@@", "" + shopcart.getDeliveryCity().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_state@@@")) body = body.replaceAll("@@@delivery_state@@@", "" + shopcart.getDeliveryState().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_country@@@")) body = body.replaceAll("@@@delivery_country@@@", "" + shopcart.getDeliveryCountry().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_phone@@@")) body = body.replaceAll("@@@delivery_phone@@@", "" + shopcart.getDeliveryPhone().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_fax@@@")) body = body.replaceAll("@@@delivery_fax@@@", "" + shopcart.getDeliveryFax().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_email@@@")) body = body.replaceAll("@@@delivery_email@@@", "" + shopcart.getDeliveryEmail().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_website@@@")) body = body.replaceAll("@@@delivery_website@@@", "" + shopcart.getDeliveryWebsite().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
}
if (body.contains("@@@invoice_")) {
		if (body.contains("@@@invoice_name@@@")) body = body.replaceAll("@@@invoice_name@@@", "" + shopcart.getInvoiceName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_organisation@@@")) body = body.replaceAll("@@@invoice_organisation@@@", "" + shopcart.getInvoiceOrganisation().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_address@@@")) body = body.replaceAll("@@@invoice_address@@@", "" + shopcart.getInvoiceAddress().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_postalcode@@@")) body = body.replaceAll("@@@invoice_postalcode@@@", "" + shopcart.getInvoicePostalcode().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_city@@@")) body = body.replaceAll("@@@invoice_city@@@", "" + shopcart.getInvoiceCity().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_state@@@")) body = body.replaceAll("@@@invoice_state@@@", "" + shopcart.getInvoiceState().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_country@@@")) body = body.replaceAll("@@@invoice_country@@@", "" + shopcart.getInvoiceCountry().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_phone@@@")) body = body.replaceAll("@@@invoice_phone@@@", "" + shopcart.getInvoicePhone().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_fax@@@")) body = body.replaceAll("@@@invoice_fax@@@", "" + shopcart.getInvoiceFax().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_email@@@")) body = body.replaceAll("@@@invoice_email@@@", "" + shopcart.getInvoiceEmail().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_website@@@")) body = body.replaceAll("@@@invoice_website@@@", "" + shopcart.getInvoiceWebsite().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
}
if (body.contains("@@@paypal_")) {
		if (body.contains("@@@paypal_business@@@")) body = body.replaceAll("@@@paypal_business@@@", "" + URLEncoder.encode(config.get(db, "paypal_email")).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@paypal_email@@@")) body = body.replaceAll("@@@paypal_email@@@", "" + URLEncoder.encode(config.get(db, "paypal_email")).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@paypal_item_name@@@")) body = body.replaceAll("@@@paypal_item_name@@@", "" + URLEncoder.encode(config.get(db, "paypal_item_name")).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@paypal_return@@@")) body = body.replaceAll("@@@paypal_return@@@", "" + (request.getProtocol() + request.getServerName() + request.getServerPort() + "/page.jsp?id=" + config.get(db, "payment_return_success")).replaceAll(":", "%3A").replaceAll("\\?", "%3F").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@paypal_cancel_return@@@")) body = body.replaceAll("@@@paypal_cancel_return@@@", "" + (request.getProtocol() + request.getServerName() + request.getServerPort() + "/page.jsp?id=" + config.get(db, "payment_return_cancel")).replaceAll(":", "%3A").replaceAll("\\?", "%3F").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@paypal_currency@@@")) body = body.replaceAll("@@@paypal_currency@@@", "" + shopcart.getOrderCurrency().getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
}
		if (body.contains("@@@payment_return_success@@@")) body = body.replaceAll("@@@payment_return_success@@@", "" + (request.getProtocol() + request.getServerName() + request.getServerPort() + "/page.jsp?id=" + config.get(db, "payment_return_success")).replaceAll(":", "%3A").replaceAll("\\?", "%3F").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@payment_return_cancel@@@")) body = body.replaceAll("@@@payment_return_cancel@@@", "" + (request.getProtocol() + request.getServerName() + request.getServerPort() + "/page.jsp?id=" + config.get(db, "payment_return_cancel")).replaceAll(":", "%3A").replaceAll("\\?", "%3F").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@country:")) {
			Pattern p = Pattern.compile("@@@country:([^@]+@?[^@]+?)@@@");
			Matcher m = p.matcher(body);
			while (m.find()) {
				String list = "" + m.group(1);
				String listcontent = parse_output_country(list, server, request, response, session, db, config);
				body = body.replaceAll("\\Q@@@country:" + list + "@@@\\E", listcontent.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(body);
			}
		}
		if (body.contains("@@@state:")) {
			Pattern p = Pattern.compile("@@@state:([^@]+@?[^@]+?)@@@");
			Matcher m = p.matcher(body);
			while (m.find()) {
				String list = "" + m.group(1);
				String listcontent = parse_output_country_state(list, server, request, response, session, db, config);
				body = body.replaceAll("\\Q@@@state:" + list + "@@@\\E", listcontent.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(body);
			}
		}
	}



	public void parse_output_order(Order order, DB db, Configuration config, Page item, String session_version, ServletContext myserver, Session mysession, Request myrequest, Response myresponse) {
		parse_output_order(order, db, config, item, session_version, "", "", "", myserver, mysession, myrequest, myresponse);
	}
	public void parse_output_order(Order order, DB db, Configuration config, Page item, String session_version, String session_device, String session_usersegments, String session_usertests, ServletContext myserver, Session mysession, Request myrequest, Response myresponse) {
		body = parse_output_order(order, body, db, config, item, session_version, session_device, session_usersegments, session_usertests, myserver, mysession, myrequest, myresponse);
	}
	public String parse_output_order(Order order, String mycontent, DB db, Configuration config, Page item, String session_version, ServletContext myserver, Session mysession, Request myrequest, Response myresponse) {
		return parse_output_order(order, mycontent, db, config, item, session_version, "", "", "", myserver, mysession, myrequest, myresponse);
	}
	public String parse_output_order(Order order, String mycontent, DB db, Configuration config, Page item, String session_version, String session_device, String session_usersegments, String session_usertests, ServletContext myserver, Session mysession, Request myrequest, Response myresponse) {
		StringBuilder items = new StringBuilder();
		if (! order.getId().equals("")) {
			Orderitem orderitem = new Orderitem();
			orderitem.records(db, "select * from orderitems where order_id=" + order.getId());
			while (orderitem.records(db, "")) {
				String mybody = item.getContent();
				mybody = parse_output_orderitem(order, orderitem, mybody, myserver, mysession, myrequest, myresponse, db, config);
				mybody = item.adjust_links(session_version, mybody, db, config);
				items.append(mybody);
			}
		}
		if (mycontent.contains("@@@items@@@")) mycontent = mycontent.replaceAll("@@@items@@@", ("" + items).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@status@@@")) mycontent = mycontent.replaceAll("@@@status@@@", order.getRevision().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@revision@@@")) mycontent = mycontent.replaceAll("@@@revision@@@", order.getRevision().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@quantity@@@")) mycontent = mycontent.replaceAll("@@@quantity@@@", order.getOrderQuantity().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@currencytitle@@@")) mycontent = mycontent.replaceAll("@@@currencytitle@@@", order.getOrderCurrencyTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@currency@@@")) mycontent = mycontent.replaceAll("@@@currency@@@", order.getOrderCurrency().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@subtotal@@@")) mycontent = mycontent.replaceAll("@@@subtotal@@@", Common.numberformat(order.getOrderSubtotal(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (mycontent.contains("@@@order_id@@@")) mycontent = mycontent.replaceAll("@@@order_id@@@", order.getId().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@order_status@@@")) mycontent = mycontent.replaceAll("@@@order_status@@@", order.getStatus().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@order_quantity@@@")) mycontent = mycontent.replaceAll("@@@order_quantity@@@", order.getOrderQuantity().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@order_currencytitle@@@")) mycontent = mycontent.replaceAll("@@@order_currencytitle@@@", order.getOrderCurrencyTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@order_currency@@@")) mycontent = mycontent.replaceAll("@@@order_currency@@@", order.getOrderCurrency().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@order_subtotal@@@")) mycontent = mycontent.replaceAll("@@@order_subtotal@@@", Common.numberformat(order.getOrderSubtotal(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (mycontent.contains("@@@order_created@@@")) mycontent = mycontent.replaceAll("@@@order_created@@@", order.getCreated().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@order_created_by@@@")) mycontent = mycontent.replaceAll("@@@order_created_by@@@", order.getCreatedBy().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@order_updated@@@")) mycontent = mycontent.replaceAll("@@@order_updated@@@", order.getUpdated().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@order_updated_by@@@")) mycontent = mycontent.replaceAll("@@@order_updated_by@@@", order.getUpdatedBy().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@order_closed@@@")) mycontent = mycontent.replaceAll("@@@order_closed@@@", order.getPublished().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@order_closed_by@@@")) mycontent = mycontent.replaceAll("@@@order_closed_by@@@", order.getPublishedBy().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@order_paid@@@")) mycontent = mycontent.replaceAll("@@@order_paid@@@", order.getPaid().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		Pattern p = Pattern.compile("@@@order_created:format=([^@]*?)@@@");
		Matcher m = p.matcher(mycontent);
		while (m.find()) {
			String dateformat = "" + m.group(1);
			Date date = new Date();
			date = Common.strtodate(order.getCreated());
			mycontent = mycontent.replaceAll("\\Q@@@order_created:format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			m.reset(mycontent);
		}
		p = Pattern.compile("@@@order_updated:format=([^@]*?)@@@");
		m = p.matcher(mycontent);
		while (m.find()) {
			String dateformat = "" + m.group(1);
			Date date = new Date();
			date = Common.strtodate(order.getUpdated());
			mycontent = mycontent.replaceAll("\\Q@@@order_updated:format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			m.reset(mycontent);
		}
		p = Pattern.compile("@@@order_closed:format=([^@]*?)@@@");
		m = p.matcher(mycontent);
		while (m.find()) {
			String dateformat = "" + m.group(1);
			Date date = new Date();
			date = Common.strtodate(order.getPublished());
			mycontent = mycontent.replaceAll("\\Q@@@order_closed:format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			m.reset(mycontent);
		}
		p = Pattern.compile("@@@order_paid:format=([^@]*?)@@@");
		m = p.matcher(mycontent);
		while (m.find()) {
			String dateformat = "" + m.group(1);
			Date date = new Date();
			date = Common.strtodate(order.getPaid());
			mycontent = mycontent.replaceAll("\\Q@@@order_paid:format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			m.reset(mycontent);
		}

		if (mycontent.contains("@@@tax_description@@@")) mycontent = mycontent.replaceAll("@@@tax_description@@@", order.getTaxDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@tax_currency@@@")) mycontent = mycontent.replaceAll("@@@tax_currency@@@", "" + order.getOrderCurrency().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@tax_currencytitle@@@")) mycontent = mycontent.replaceAll("@@@tax_currencytitle@@@", "" + order.getOrderCurrencyTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@tax@@@")) mycontent = mycontent.replaceAll("@@@tax@@@", Common.numberformat(order.getTaxTotal(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (Common.number(order.getTaxTotal()) != 0) {
			if (mycontent.contains("@@@tax:")) mycontent = mycontent.replaceAll("(?s)@@@tax:(.*?)@@@", "$1");
		} else {
			if (mycontent.contains("@@@tax:")) mycontent = mycontent.replaceAll("(?s)@@@tax:(.*?)@@@", "");
		}
		if (mycontent.contains("@@@shipping_description@@@")) mycontent = mycontent.replaceAll("@@@shipping_description@@@", order.getShippingDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@shipping_currency@@@")) mycontent = mycontent.replaceAll("@@@shipping_currency@@@", "" + order.getOrderCurrency().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@shipping_currencytitle@@@")) mycontent = mycontent.replaceAll("@@@shipping_currencytitle@@@", "" + order.getOrderCurrencyTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@shipping@@@")) mycontent = mycontent.replaceAll("@@@shipping@@@", Common.numberformat(order.getShippingTotal(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (Common.number(order.getShippingTotal()) != 0) {
			if (mycontent.contains("@@@shipping:")) mycontent = mycontent.replaceAll("(?s)@@@shipping:(.*?)@@@", "$1");
		} else {
			if (mycontent.contains("@@@shipping:")) mycontent = mycontent.replaceAll("(?s)@@@shipping:(.*?)@@@", "");
		}
		if (Common.number(order.getDiscountTotal()) != 0) {
			if (mycontent.contains("@@@discount_description@@@")) mycontent = mycontent.replaceAll("@@@discount_description@@@", "" + order.getDiscountDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@discount_currency@@@")) mycontent = mycontent.replaceAll("@@@discount_currency@@@", "" + order.getOrderCurrency().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@discount_currencytitle@@@")) mycontent = mycontent.replaceAll("@@@discount_currencytitle@@@", "" + order.getOrderCurrencyTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@discount@@@")) mycontent = mycontent.replaceAll("@@@discount@@@", Common.numberformat(order.getDiscountTotal(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
			if (mycontent.contains("@@@discount:")) mycontent = mycontent.replaceAll("(?s)@@@discount:(.*?)@@@", "$1");
		} else {
			if (mycontent.contains("@@@discount_description@@@")) mycontent = mycontent.replaceAll("@@@discount_description@@@", "");
			if (mycontent.contains("@@@discount_currency@@@")) mycontent = mycontent.replaceAll("@@@discount_currency@@@", "");
			if (mycontent.contains("@@@discount_currencytitle@@@")) mycontent = mycontent.replaceAll("@@@discount_currencytitle@@@", "");
			if (mycontent.contains("@@@discount@@@")) mycontent = mycontent.replaceAll("@@@discount@@@", "");
			if (mycontent.contains("@@@discount:")) mycontent = mycontent.replaceAll("(?s)@@@discount:(.*?)@@@", "");
		}
		if (mycontent.contains("@@@total@@@")) mycontent = mycontent.replaceAll("@@@total@@@", Common.numberformat(order.getOrderTotal(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
if (mycontent.contains("@@@card_")) {
		if (mycontent.contains("@@@card_type@@@")) mycontent = mycontent.replaceAll("@@@card_type@@@", order.getCardType().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@card_number@@@")) mycontent = mycontent.replaceAll("@@@card_number@@@", order.getCardNumber().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@card_issuedmonth@@@")) mycontent = mycontent.replaceAll("@@@card_issuedmonth@@@", order.getCardIssuedMonth().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@card_issuedyear@@@")) mycontent = mycontent.replaceAll("@@@card_issuedyear@@@", order.getCardIssuedYear().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@card_expirymonth@@@")) mycontent = mycontent.replaceAll("@@@card_expirymonth@@@", order.getCardExpiryMonth().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@card_expiryyear@@@")) mycontent = mycontent.replaceAll("@@@card_expiryyear@@@", order.getCardExpiryYear().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@card_name@@@")) mycontent = mycontent.replaceAll("@@@card_name@@@", order.getCardName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@card_cvc@@@")) mycontent = mycontent.replaceAll("@@@card_cvc@@@", order.getCardCVC().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@card_issue@@@")) mycontent = mycontent.replaceAll("@@@card_issue@@@", order.getCardIssue().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@card_postalcode@@@")) mycontent = mycontent.replaceAll("@@@card_postalcode@@@", order.getCardPostalcode().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
}
if (mycontent.contains("@@@delivery_")) {
		if (mycontent.contains("@@@delivery_name@@@")) mycontent = mycontent.replaceAll("@@@delivery_name@@@", order.getDeliveryName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@delivery_organisation@@@")) mycontent = mycontent.replaceAll("@@@delivery_organisation@@@", order.getDeliveryOrganisation().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@delivery_address@@@")) mycontent = mycontent.replaceAll("@@@delivery_address@@@", order.getDeliveryAddress().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@delivery_postalcode@@@")) mycontent = mycontent.replaceAll("@@@delivery_postalcode@@@", order.getDeliveryPostalcode().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@delivery_city@@@")) mycontent = mycontent.replaceAll("@@@delivery_city@@@", order.getDeliveryCity().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@delivery_state@@@")) mycontent = mycontent.replaceAll("@@@delivery_state@@@", order.getDeliveryState().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@delivery_country@@@")) mycontent = mycontent.replaceAll("@@@delivery_country@@@", order.getDeliveryCountry().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@delivery_phone@@@")) mycontent = mycontent.replaceAll("@@@delivery_phone@@@", order.getDeliveryPhone().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@delivery_fax@@@")) mycontent = mycontent.replaceAll("@@@delivery_fax@@@", order.getDeliveryFax().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@delivery_email@@@")) mycontent = mycontent.replaceAll("@@@delivery_email@@@", order.getDeliveryEmail().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@delivery_website@@@")) mycontent = mycontent.replaceAll("@@@delivery_website@@@", order.getDeliveryWebsite().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
}
if (mycontent.contains("@@@invoice_")) {
		if (mycontent.contains("@@@invoice_name@@@")) mycontent = mycontent.replaceAll("@@@invoice_name@@@", order.getInvoiceName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@invoice_organisation@@@")) mycontent = mycontent.replaceAll("@@@invoice_organisation@@@", order.getInvoiceOrganisation().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@invoice_address@@@")) mycontent = mycontent.replaceAll("@@@invoice_address@@@", order.getInvoiceAddress().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@invoice_postalcode@@@")) mycontent = mycontent.replaceAll("@@@invoice_postalcode@@@", order.getInvoicePostalcode().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@invoice_city@@@")) mycontent = mycontent.replaceAll("@@@invoice_city@@@", order.getInvoiceCity().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@invoice_state@@@")) mycontent = mycontent.replaceAll("@@@invoice_state@@@", order.getInvoiceState().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@invoice_country@@@")) mycontent = mycontent.replaceAll("@@@invoice_country@@@", order.getInvoiceCountry().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@invoice_phone@@@")) mycontent = mycontent.replaceAll("@@@invoice_phone@@@", order.getInvoicePhone().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@invoice_fax@@@")) mycontent = mycontent.replaceAll("@@@invoice_fax@@@", order.getInvoiceFax().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@invoice_email@@@")) mycontent = mycontent.replaceAll("@@@invoice_email@@@", order.getInvoiceEmail().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@invoice_website@@@")) mycontent = mycontent.replaceAll("@@@invoice_website@@@", order.getInvoiceWebsite().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
}
		return mycontent;
	}



	public String parse_output_orderitem(Order order, Orderitem orderitem, String mycontent, ServletContext myserver, Session mysession, Request myrequest, Response myresponse, DB db, Configuration config) {
		if (mycontent.contains("@@@id@@@")) mycontent = mycontent.replaceAll("@@@id@@@", "" + orderitem.getProductId().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@class@@@")) mycontent = mycontent.replaceAll("@@@class@@@", "product");
		if (mycontent.contains("@@@version@@@")) mycontent = mycontent.replaceAll("@@@version@@@", "" + orderitem.getVersion().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@device@@@")) mycontent = mycontent.replaceAll("@@@device@@@", "" + orderitem.getDevice().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@filename@@@")) mycontent = mycontent.replaceAll("@@@filename@@@", "" + orderitem.getServerFilename().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@title@@@")) mycontent = mycontent.replaceAll("@@@title@@@", "" + orderitem.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@author@@@")) mycontent = mycontent.replaceAll("@@@author@@@", "" + orderitem.getAuthor().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@keywords@@@")) mycontent = mycontent.replaceAll("@@@keywords@@@", "" + orderitem.getKeywords().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@description@@@")) mycontent = mycontent.replaceAll("@@@description@@@", "" + orderitem.getDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@image1@@@")) mycontent = mycontent.replaceAll("@@@image1@@@", "" + orderitem.getImage1().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@image2@@@")) mycontent = mycontent.replaceAll("@@@image2@@@", "" + orderitem.getImage2().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@image3@@@")) mycontent = mycontent.replaceAll("@@@image3@@@", "" + orderitem.getImage3().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@file1@@@")) mycontent = mycontent.replaceAll("@@@file1@@@", "" + orderitem.getFile1().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@file2@@@")) mycontent = mycontent.replaceAll("@@@file2@@@", "" + orderitem.getFile2().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@file3@@@")) mycontent = mycontent.replaceAll("@@@file3@@@", "" + orderitem.getFile3().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@link1@@@")) mycontent = mycontent.replaceAll("@@@link1@@@", "" + orderitem.getLink1().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@link2@@@")) mycontent = mycontent.replaceAll("@@@link2@@@", "" + orderitem.getLink2().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@link3@@@")) mycontent = mycontent.replaceAll("@@@link3@@@", "" + orderitem.getLink3().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@code@@@")) mycontent = mycontent.replaceAll("@@@code@@@", "" + orderitem.getProductCode().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@currencytitle@@@")) mycontent = mycontent.replaceAll("@@@currencytitle@@@", "" + orderitem.getProductCurrencyTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@currency@@@")) mycontent = mycontent.replaceAll("@@@currency@@@", "" + orderitem.getProductCurrency().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@price@@@")) mycontent = mycontent.replaceAll("@@@price@@@", Common.numberformat(orderitem.getProductPrice(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (mycontent.contains("@@@weight@@@")) mycontent = mycontent.replaceAll("@@@weight@@@", "" + orderitem.getProductWeight().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@volume@@@")) mycontent = mycontent.replaceAll("@@@volume@@@", "" + orderitem.getProductVolume().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@width@@@")) mycontent = mycontent.replaceAll("@@@width@@@", "" + orderitem.getProductWidth().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@height@@@")) mycontent = mycontent.replaceAll("@@@height@@@", "" + orderitem.getProductHeight().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@depth@@@")) mycontent = mycontent.replaceAll("@@@depth@@@", "" + orderitem.getProductDepth().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@brand@@@")) mycontent = mycontent.replaceAll("@@@brand@@@", "" + orderitem.getProductBrand().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@colour@@@")) mycontent = mycontent.replaceAll("@@@colour@@@", "" + orderitem.getProductColour().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@size@@@")) mycontent = mycontent.replaceAll("@@@size@@@", "" + orderitem.getProductSize().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@period@@@")) mycontent = mycontent.replaceAll("@@@period@@@", text.display("content.productdetails.period." + orderitem.getProductPeriod()));
		if (mycontent.contains("@@@stock@@@")) mycontent = mycontent.replaceAll("@@@stock@@@", "" + orderitem.getProductStock().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@stockstatus@@@")) mycontent = mycontent.replaceAll("@@@stockstatus@@@", "" + orderitem.getProductStatus().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@stockcomment@@@")) mycontent = mycontent.replaceAll("@@@stockcomment@@@", "" + orderitem.getProductComment().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@comment@@@")) mycontent = mycontent.replaceAll("@@@comment@@@", "" + orderitem.getProductComment().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@quantity@@@")) mycontent = mycontent.replaceAll("@@@quantity@@@", "" + orderitem.getItemQuantity().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		double price_list = Common.number(orderitem.getItemSubtotal());
		double price_discount = Common.number(orderitem.getDiscountTotal());
		double price_discounted = price_list - price_discount;
		double price_tax = Common.number(orderitem.getTaxTotal());
		double price_taxed = price_discounted + price_tax;
		double price_shipping = Common.number(orderitem.getShippingTotal());
		double price_shipped = price_taxed + price_shipping;
		if (mycontent.contains("@@@subtotal@@@")) mycontent = mycontent.replaceAll("@@@subtotal@@@", Common.numberformat(price_list, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (mycontent.contains("@@@subtotal_discounted@@@")) mycontent = mycontent.replaceAll("@@@subtotal_discounted@@@", Common.numberformat(price_discounted, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (mycontent.contains("@@@subtotal_taxed@@@")) mycontent = mycontent.replaceAll("@@@subtotal_taxed@@@", Common.numberformat(price_taxed, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (mycontent.contains("@@@subtotal_shipped@@@")) mycontent = mycontent.replaceAll("@@@subtotal_shipped@@@", Common.numberformat(price_shipped, Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (mycontent.contains("@@@total@@@")) mycontent = mycontent.replaceAll("@@@total@@@", Common.numberformat(orderitem.getItemTotal(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
		if (Common.number(orderitem.getDiscountTotal()) != 0) {
			if (mycontent.contains("@@@discount_description@@@")) mycontent = mycontent.replaceAll("@@@discount_description@@@", orderitem.getDiscountDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@discount_currency@@@")) mycontent = mycontent.replaceAll("@@@discount_currency@@@", orderitem.getProductCurrency().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@discount_currencytitle@@@")) mycontent = mycontent.replaceAll("@@@discount_currencytitle@@@", orderitem.getProductCurrencyTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@discount@@@")) mycontent = mycontent.replaceAll("@@@discount@@@", Common.numberformat(orderitem.getDiscountTotal(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
			mycontent = mycontent.replaceAll("(?s)@@@discount:(.*?)@@@", "$1");
		} else {
			if (mycontent.contains("@@@discount_description@@@")) mycontent = mycontent.replaceAll("@@@discount_description@@@", "");
			if (mycontent.contains("@@@discount_currency@@@")) mycontent = mycontent.replaceAll("@@@discount_currency@@@", "");
			if (mycontent.contains("@@@discount_currencytitle@@@")) mycontent = mycontent.replaceAll("@@@discount_currencytitle@@@", "");
			if (mycontent.contains("@@@discount@@@")) mycontent = mycontent.replaceAll("@@@discount@@@", "");
			mycontent = mycontent.replaceAll("(?s)@@@discount:(.*?)@@@", "");
		}
		if (Common.number(orderitem.getShippingTotal()) != 0) {
			if (mycontent.contains("@@@shipping_description@@@")) mycontent = mycontent.replaceAll("@@@shipping_description@@@", orderitem.getShippingDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@shipping_currency@@@")) mycontent = mycontent.replaceAll("@@@shipping_currency@@@", orderitem.getProductCurrency().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@shipping_currencytitle@@@")) mycontent = mycontent.replaceAll("@@@shipping_currencytitle@@@", orderitem.getProductCurrencyTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@shipping@@@")) mycontent = mycontent.replaceAll("@@@shipping@@@", Common.numberformat(orderitem.getShippingTotal(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
			mycontent = mycontent.replaceAll("(?s)@@@shipping:(.*?)@@@", "$1");
		} else {
			if (mycontent.contains("@@@shipping_description@@@")) mycontent = mycontent.replaceAll("@@@shipping_description@@@", "");
			if (mycontent.contains("@@@shipping_currency@@@")) mycontent = mycontent.replaceAll("@@@shipping_currency@@@", "");
			if (mycontent.contains("@@@shipping_currencytitle@@@")) mycontent = mycontent.replaceAll("@@@shipping_currencytitle@@@", "");
			if (mycontent.contains("@@@shipping@@@")) mycontent = mycontent.replaceAll("@@@shipping@@@", "");
			mycontent = mycontent.replaceAll("(?s)@@@shipping:(.*?)@@@", "");
		}
		if (Common.number(orderitem.getTaxTotal()) != 0) {
			if (mycontent.contains("@@@tax_description@@@")) mycontent = mycontent.replaceAll("@@@tax_description@@@", orderitem.getTaxDescription().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@tax_currency@@@")) mycontent = mycontent.replaceAll("@@@tax_currency@@@", orderitem.getProductCurrency().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@tax_currencytitle@@@")) mycontent = mycontent.replaceAll("@@@tax_currencytitle@@@", orderitem.getProductCurrencyTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (mycontent.contains("@@@tax@@@")) mycontent = mycontent.replaceAll("@@@tax@@@", Common.numberformat(orderitem.getTaxTotal(), Common.intnumber(config.get(db, "decimals")), config.get(db, "grouping").equals("true")));
			mycontent = mycontent.replaceAll("(?s)@@@tax:(.*?)@@@", "$1");
		} else {
			if (mycontent.contains("@@@tax_description@@@")) mycontent = mycontent.replaceAll("@@@tax_description@@@", "");
			if (mycontent.contains("@@@tax_currency@@@")) mycontent = mycontent.replaceAll("@@@tax_currency@@@", "");
			if (mycontent.contains("@@@tax_currencytitle@@@")) mycontent = mycontent.replaceAll("@@@tax_currencytitle@@@", "");
			if (mycontent.contains("@@@tax@@@")) mycontent = mycontent.replaceAll("@@@tax@@@", "");
			mycontent = mycontent.replaceAll("(?s)@@@tax:(.*?)@@@", "");
		}
		String options = "";
		String[] product_options = orderitem.getProductOptions().split("[\\r\\n]+");
		for (int i=0; i<product_options.length; i++) {
			String product_option = product_options[i];
			Pattern p = Pattern.compile("<([^<>]+?)>([^<>]*?)<\\/([^<>]+?)>");
			Matcher m = p.matcher(product_option);
			if (m.find()) {
				String myname = "" + m.group(1);
				String myvalue = "" + m.group(2);
				if (myname.equals("hosting:subdomain")) {
					options += "<div>" + text.display("hosting.subdomain") + ": <nobr>" + config.get(db, "productdelivery_hosting_www") + html.encode(myvalue) + config.get(db, "productdelivery_hosting_domain") + "</nobr></div>";
				} else if (myname.equals("hosting:domain")) {
					options += "<div>" + text.display("hosting.domain") + ": <nobr>" + config.get(db, "productdelivery_hosting_www") + html.encode(myvalue) + "</nobr></div>";
				} else if (myname.startsWith("hosting:domain:")) {
					// ignore
				} else if (myname.startsWith("hosting:config:")) {
					options += "<div>" + myname.substring(15).replaceAll("_", " ") + ": " + myvalue + "</div>";
				} else if ((! myname.startsWith("hosting:")) && (! myname.startsWith("user:"))) {
					options += "<div>" + myname + ": " + myvalue + "</div>";
				}
			}
		}
		if (mycontent.contains("@@@options@@@")) mycontent = mycontent.replaceAll("@@@options@@@", options.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if ((orderitem.getProductProgram() != null) && (! orderitem.getProductProgram().equals(""))) {
			if (mycontent.contains("@@@availability@@@")) {
				String product_availability = Common.execute("/" + text.display("adminpath") + "/productavailability/" + orderitem.getProductProgram() + ".jsp", "order", order, "productavailability", myserver, mysession, myrequest, myresponse);
				product_availability = product_availability.replaceAll("^[-+0-9]+ ", "");
				mycontent = mycontent.replaceAll("@@@availability@@@", product_availability.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			}
		}
		return mycontent;
	}



	public void parse_output_delivery(Order order, Orderitem orderitem, User user, Hosting hosting, String program_output, ServletContext myserver, Session mysession, Request myrequest, Response myresponse, DB db, Configuration config) {
		Pattern p;
		Matcher m;

		if (orderitem != null) {
			body = parse_output_orderitem(order, orderitem, body, myserver, mysession, myrequest, myresponse, db, config);
		}

		if (order != null) {
			if (body.contains("@@@order_id@@@")) body = body.replaceAll("@@@order_id@@@", order.getId());
			if (body.contains("@@@order_created@@@")) body = body.replaceAll("@@@order_created@@@", order.getCreated());
			if (body.contains("@@@order_updated@@@")) body = body.replaceAll("@@@order_updated@@@", order.getUpdated());
			if (body.contains("@@@order_closed@@@")) body = body.replaceAll("@@@order_closed@@@", order.getPublished());
			if (body.contains("@@@order_paid@@@")) body = body.replaceAll("@@@order_paid@@@", order.getPaid());
			p = Pattern.compile("@@@order_created:format=([^@]*?)@@@");
			m = p.matcher(body);
			while (m.find()) {
				String dateformat = m.group(1);
				Date date = new Date();
				date = Common.strtodate(order.getCreated());
				body = body.replaceAll("\\Q@@@order_created:format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(body);
			}
			p = Pattern.compile("@@@order_updated:format=([^@]*?)@@@");
			m = p.matcher(body);
			while (m.find()) {
				String dateformat = m.group(1);
				Date date = new Date();
				date = Common.strtodate(order.getUpdated());
				body = body.replaceAll("\\Q@@@order_updated:format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(body);
			}
			p = Pattern.compile("@@@order_closed:format=([^@]*?)@@@");
			m = p.matcher(body);
			while (m.find()) {
				String dateformat = m.group(1);
				Date date = new Date();
				date = Common.strtodate(order.getPublished());
				body = body.replaceAll("\\Q@@@order_closed:format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(body);
			}
			p = Pattern.compile("@@@order_paid:format=([^@]*?)@@@");
			m = p.matcher(body);
			while (m.find()) {
				String dateformat = m.group(1);
				Date date = new Date();
				date = Common.strtodate(order.getPaid());
				body = body.replaceAll("\\Q@@@order_paid:format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(body);
			}
		}

		if (user != null) {
			if (body.contains("@@@user_id@@@")) body = body.replaceAll("@@@user_id@@@", user.getId().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@user_username@@@")) body = body.replaceAll("@@@user_username@@@", user.getUsername().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@user_password@@@")) body = body.replaceAll("@@@user_password@@@", user.getPassword().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@user_name@@@")) body = body.replaceAll("@@@user_name@@@", user.getName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@user_email@@@")) body = body.replaceAll("@@@user_email@@@", user.getEmail().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@user_created@@@")) body = body.replaceAll("@@@user_created@@@", user.getCreated());
			if (body.contains("@@@user_updated@@@")) body = body.replaceAll("@@@user_updated@@@", user.getUpdated());
			p = Pattern.compile("@@@user_created:format=([^@]*?)@@@");
			m = p.matcher(body);
			while (m.find()) {
				String dateformat = "" + m.group(1);
				Date date = new Date();
				date = Common.strtodate(user.getCreated());
				body = body.replaceAll("\\Q@@@user_created:format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(body);
			}
			p = Pattern.compile("@@@user_updated:format=([^@]*?)@@@");
			m = p.matcher(body);
			while (m.find()) {
				String dateformat = "" + m.group(1);
				Date date = new Date();
				date = Common.strtodate(user.getUpdated());
				body = body.replaceAll("\\Q@@@user_updated:format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(body);
			}
			if (body.contains("@@@user_activation@@@")) body = body.replaceAll("@@@user_activation@@@", user.getScheduledPublish());
			if (body.contains("@@@user_notification@@@")) body = body.replaceAll("@@@user_notification@@@", user.getScheduledNotify());
			if (body.contains("@@@user_expiration@@@")) body = body.replaceAll("@@@user_expiration@@@", user.getScheduledUnpublish());
			p = Pattern.compile("@@@user_activation:format=([^@]*?)@@@");
			m = p.matcher(body);
			while (m.find()) {
				String dateformat = m.group(1);
				Date date = new Date();
				date = Common.strtodate(user.getScheduledPublish());
				body = body.replaceAll("\\Q@@@user_activation:format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(body);
			}
			p = Pattern.compile("@@@user_notification:format=([^@]*?)@@@");
			m = p.matcher(body);
			while (m.find()) {
				String dateformat = m.group(1);
				Date date = new Date();
				date = Common.strtodate(user.getScheduledNotify());
				body = body.replaceAll("\\Q@@@user_notification:format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(body);
			}
			p = Pattern.compile("@@@user_expiration:format=([^@]*?)@@@");
			m = p.matcher(body);
			while (m.find()) {
				String dateformat = m.group(1);
				Date date = new Date();
				date = Common.strtodate(user.getScheduledUnpublish());
				body = body.replaceAll("\\Q@@@user_expiration:format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				m.reset(body);
			}

			String[] myinfos = user.getUserinfo().split("[\r\n]+");
			for (int j=0; j<myinfos.length; j++) {
				String myinfo = myinfos[j];
				Matcher myinfoMatches = Pattern.compile("<([^<>]+?)>([^<>]*?)</([^<>]+?)>").matcher(myinfo);
				if (myinfoMatches.find()) {
					String myname = "" + myinfoMatches.group(1);
					String myvalue = "" + myinfoMatches.group(2);
					body = body.replaceAll("\\Q@@@user_" + myname + "@@@\\E", myvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					body = body.replaceAll("\\Q@@@user_" + myname.replaceAll(" ", "_") + "@@@\\E", myvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				}
			}
		}

		if (hosting != null) {
			if (body.contains("@@@hosting_address@@@")) body = body.replaceAll("@@@hosting_address@@@", hosting.getClientAddress().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@hosting_urlrootpath@@@")) body = body.replaceAll("@@@hosting_urlrootpath@@@", hosting.getClientURLrootpath().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@hosting_database@@@")) body = body.replaceAll("@@@hosting_database@@@", hosting.getClientDatabase().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@hosting_username@@@")) body = body.replaceAll("@@@hosting_username@@@", hosting.getSuperadmin().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@hosting_password@@@")) body = body.replaceAll("@@@hosting_password@@@", hosting.getSuperadminPassword().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@hosting_email@@@")) body = body.replaceAll("@@@hosting_email@@@", hosting.getSuperadminEmail().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@hosting_active@@@")) body = body.replaceAll("@@@hosting_active@@@", hosting.getScheduledPublish().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@hosting_notify@@@")) body = body.replaceAll("@@@hosting_notify@@@", hosting.getScheduledNotify().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@hosting_expiry@@@")) body = body.replaceAll("@@@hosting_expiry@@@", hosting.getScheduledUnpublish().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@hosting_personal@@@")) body = body.replaceAll("@@@hosting_personal@@@", hosting.getPersonalLicense().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@hosting_professional@@@")) body = body.replaceAll("@@@hosting_professional@@@", hosting.getProfessionalLicense().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@hosting_enterprise@@@")) body = body.replaceAll("@@@hosting_enterprise@@@", hosting.getEnterpriseLicense().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@hosting_hosting@@@")) body = body.replaceAll("@@@hosting_hosting@@@", hosting.getHostingLicense().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@hosting_ecommerce@@@")) body = body.replaceAll("@@@hosting_ecommerce@@@", hosting.getEcommerceLicense().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@hosting_community@@@")) body = body.replaceAll("@@@hosting_community@@@", hosting.getCommunityLicense().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@hosting_databases@@@")) body = body.replaceAll("@@@hosting_databases@@@", hosting.getDatabasesLicense().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (body.contains("@@@hosting_statistics@@@")) body = body.replaceAll("@@@hosting_statistics@@@", hosting.getStatisticsLicense().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		}

		if (body.contains("@@@program_output@@@")) body = body.replaceAll("@@@program_output@@@", program_output.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
	}



	public void parse_output_data(Session session, Request request, Response response, DB db, Configuration config, String session_version, Databases database, Data data) {
		parse_output_data(session, request, response, db, config, session_version, "", "", "", database, data);
	}
	public void parse_output_data(Session session, Request request, Response response, DB db, Configuration config, String session_version, String session_device, String session_usersegments, String session_usertests, Databases database, Data data) {
		Page dummypage = new Page(text);
		page_title = page_title.replaceAll("@@@id@@@", data.getId().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		body = body.replaceAll("@@@id@@@", data.getId().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		Iterator mycolumns = database.columns.keySet().iterator();
		while (mycolumns.hasNext()) {
			HashMap mycolumn = (HashMap)database.columns.get("" + mycolumns.next());
			String data_content = data.getContent("col" + mycolumn.get("id"));
			String myinput = "";
			myinput = "<select name=\"" + mycolumn.get("name") + "\" multiple size=\"" + mycolumn.get("param1") + "\">" + data.select_options((""+mycolumn.get("options")), data_content) + "</select>";
			body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + ".selectmulti@@@\\E", myinput.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if ((""+mycolumn.get("type")).equals("selectmulti")) {
				body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + ".select@@@\\E", myinput.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			}
			myinput = "<select name=\"" + mycolumn.get("name") + "\">" + data.select_options((""+mycolumn.get("options")), data_content) + "</select>";
			body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + ".select@@@\\E", myinput.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			myinput = data.radio_options(""+mycolumn.get("name"), (""+mycolumn.get("options")), data_content);
			body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + ".radio@@@\\E", myinput.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			myinput = data.checkbox_options(""+mycolumn.get("name"), (""+mycolumn.get("options")), data_content);
			body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + ".checkbox@@@\\E", myinput.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (((""+mycolumn.get("type")).equals("selectmulti")) || ((""+mycolumn.get("type")).equals("radio")) || ((""+mycolumn.get("type")).equals("checkbox")) || ((""+mycolumn.get("type")).equals("contentclasses")) || ((""+mycolumn.get("type")).equals("contentgroups")) || ((""+mycolumn.get("type")).equals("contenttypes")) || ((""+mycolumn.get("type")).equals("pagegroups")) || ((""+mycolumn.get("type")).equals("pagetypes")) || ((""+mycolumn.get("type")).equals("imagegroups")) || ((""+mycolumn.get("type")).equals("imagetypes")) || ((""+mycolumn.get("type")).equals("imageformats")) || ((""+mycolumn.get("type")).equals("filegroups")) || ((""+mycolumn.get("type")).equals("filetypes")) || ((""+mycolumn.get("type")).equals("fileformats")) || ((""+mycolumn.get("type")).equals("linkgroups")) || ((""+mycolumn.get("type")).equals("linktypes")) || ((""+mycolumn.get("type")).equals("productgroups")) || ((""+mycolumn.get("type")).equals("producttypes")) || ((""+mycolumn.get("type")).equals("versions")) || ((""+mycolumn.get("type")).equals("databases")) || ((""+mycolumn.get("type")).equals("data")) || ((""+mycolumn.get("type")).equals("usernames")) || ((""+mycolumn.get("type")).equals("useremails")) || ((""+mycolumn.get("type")).equals("usergroups")) || ((""+mycolumn.get("type")).equals("usertypes"))) {
				if (Data.csv_options) data_content = data_content.replaceAll(",", "|");
				body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + ".br" + "@@@\\E", data_content.replaceAll("\\|", "<br>").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + ".p" + "@@@\\E", "<p>" + data_content.replaceAll("\\|", "</p><p>").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</p>");
				body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + ".div" + "@@@\\E", "<div>" + data_content.replaceAll("\\|", "</div><div>").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</div>");
				body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + ".text" + "@@@\\E", data_content.replaceAll("\\|", "\r\n").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			} else if (((""+mycolumn.get("type")).equals("content")) || ((""+mycolumn.get("type")).equals("contents")) || ((""+mycolumn.get("type")).equals("page")) || ((""+mycolumn.get("type")).equals("pages")) || ((""+mycolumn.get("type")).equals("image")) || ((""+mycolumn.get("type")).equals("images")) || ((""+mycolumn.get("type")).equals("file")) || ((""+mycolumn.get("type")).equals("files")) || ((""+mycolumn.get("type")).equals("link")) || ((""+mycolumn.get("type")).equals("links")) || ((""+mycolumn.get("type")).equals("product")) || ((""+mycolumn.get("type")).equals("products")) || ((""+mycolumn.get("type")).equals("element")) || ((""+mycolumn.get("type")).equals("elements"))) {
				body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + ".br" + "@@@\\E", data_content.replaceAll("\\|", "<br>").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + ".p" + "@@@\\E", "<p>" + data_content.replaceAll("\\|", "</p><p>").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</p>");
				body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + ".div" + "@@@\\E", "<div>" + data_content.replaceAll("\\|", "</div><div>").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$") + "</div>");
				body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + ".text" + "@@@\\E", data_content.replaceAll("\\|", "\r\n").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + ".titles" + "@@@\\E", dummypage.list_titles(session, request, response, config, db, data_content.replaceAll("\\|", ",")).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + ".links" + "@@@\\E", dummypage.list_links(session, request, response, config, db, data_content.replaceAll("\\|", ",")).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			} else if (((""+mycolumn.get("type")).equals("datetime")) || ((""+mycolumn.get("type")).equals("created")) || ((""+mycolumn.get("type")).equals("updated"))) {
				Pattern p = Pattern.compile("\\Q@@@" + mycolumn.get("name") + "\\E:format=([^@]*?)@@@");
				Matcher m = p.matcher(body);
				while (m.find()) {
					String dateformat = m.group(1);
					Date date = Common.strtodate("" + mycolumn.get("param1"), data_content);
					if (date != null) {
						body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + ":format=" + dateformat + "@@@\\E", Common.strftime(dateformat, date).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					} else {
						body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + ":format=" + dateformat + "@@@\\E", "");
					}
					m.reset(body);
				}
			}
			page_title = page_title.replaceAll("\\Q@@@" + mycolumn.get("name") + "@@@\\E", data_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + ".html@@@\\E", data_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
			body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + ".script@@@\\E", data_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("\r", "\\\\\\\\r").replaceAll("\n", "\\\\\\\\n").replaceAll("'", "\\\\\\\\'"));
			body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + ".text@@@\\E", data_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("<br>", "\r\n").replaceAll("<p>", "\r\n\r\n").replaceAll("</p>", ""));
			body = body.replaceAll("\\Q@@@" + mycolumn.get("name") + "@@@\\E", data_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			setAuthor(getAuthor().replaceAll("\\Q@@@" + mycolumn.get("name") + "@@@\\E", data_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
			setKeywords(getKeywords().replaceAll("\\Q@@@" + mycolumn.get("name") + "@@@\\E", data_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
			setDescription(getDescription().replaceAll("\\Q@@@" + mycolumn.get("name") + "@@@\\E", data_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
			setSummary(getSummary().replaceAll("\\Q@@@" + mycolumn.get("name") + "@@@\\E", data_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
			setImage1(getImage1().replaceAll("\\Q@@@" + mycolumn.get("name") + "@@@\\E", data_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
			setImage2(getImage2().replaceAll("\\Q@@@" + mycolumn.get("name") + "@@@\\E", data_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
			setImage3(getImage3().replaceAll("\\Q@@@" + mycolumn.get("name") + "@@@\\E", data_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
			setFile1(getFile1().replaceAll("\\Q@@@" + mycolumn.get("name") + "@@@\\E", data_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
			setFile2(getFile2().replaceAll("\\Q@@@" + mycolumn.get("name") + "@@@\\E", data_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
			setFile3(getFile3().replaceAll("\\Q@@@" + mycolumn.get("name") + "@@@\\E", data_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
			setLink1(getLink1().replaceAll("\\Q@@@" + mycolumn.get("name") + "@@@\\E", data_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
			setLink2(getLink2().replaceAll("\\Q@@@" + mycolumn.get("name") + "@@@\\E", data_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
			setLink3(getLink3().replaceAll("\\Q@@@" + mycolumn.get("name") + "@@@\\E", data_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
			setMetaInfo(getMetaInfo().replaceAll("\\Q@@@" + mycolumn.get("name") + "@@@\\E", data_content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
		}
		body = adjust_links(session_version, body, db, config);
	}

	/**
	 * Método criado para a validação de registro de membros, se conter erro
	 * mostra na página pro usuario e preenche os campos do form para não precisar digitar novamente
	 * Não é necesário mudar o método se adicionar mais campos no form
	 */
	public void parse_output_register_iliketo(String errorILiketo, HttpServletRequest request){
		
		//substitui o @@@error@@@ na page pelo errorILiketo
		if (body.contains("@@@error@@@")){
			body = body.replaceAll("@@@error@@@", ("" + errorILiketo).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		}
		
		Enumeration parameters = request.getParameterNames(); //recupera parametros do request que contem os campos do formulario
		
		while(parameters != null && parameters.hasMoreElements()){			
			
			String name = (String) parameters.nextElement();	//name campo do form
			String value = request.getParameter(name); //valor campo form
			
			//coloca os parametros que o usuario digitou novamente nos campos
			if (body.contains("@@@" + name + "@@@")){
				body = body.replaceAll("@@@" + name + "@@@", ("" + value).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));		
			}
		}
	}
	
	/**
	 * Metodo realiza o replace nos codigos especiais Iliketo que estiverem na pagina html pela informacao setada em sessao ou request
	 * setar valores no HttpServletRequest(escopo request) e recuperar pelo metodo getAttribute("name")
	 * setar valores no HttpSession(escopo sessao) e recuperar pelo metodo getAttribute("name")
	 * Pode-se colocar mais codigos especiais aqui
	 * Codigo @@@user_card_xxx 	- informacoes do cartao
	 * Codigo @@@ads_xxx 		- informacoes do anuncio
	 * 
	 * @param httpReq
	 * @param mycontent
	 * @return
	 */
	public String parse_output_special_code_iliketo(HttpServletRequest httpReq, String mycontent){
		//verifica se contem o comeco @@@user_card senao pula
		if(mycontent.contains("@@@user_card")){
			if (mycontent.contains("@@@user_card_type@@@")) mycontent = mycontent.replaceAll("@@@user_card_type@@@", (String) httpReq.getAttribute("user_card_type"));
			if (mycontent.contains("@@@user_card_number@@@")) mycontent = mycontent.replaceAll("@@@user_card_number@@@", (String) httpReq.getAttribute("user_card_number"));
			if (mycontent.contains("@@@user_card_issuedmonth@@@")) mycontent = mycontent.replaceAll("@@@user_card_issuedmonth@@@", (String) httpReq.getAttribute("user_card_issuedmonth"));
			if (mycontent.contains("@@@user_card_issuedyear@@@")) mycontent = mycontent.replaceAll("@@@user_card_issuedyear@@@", (String) httpReq.getAttribute("user_card_issuedyear"));
			if (mycontent.contains("@@@user_card_expirymonth@@@")) mycontent = mycontent.replaceAll("@@@user_card_expirymonth@@@", (String) httpReq.getAttribute("user_card_expirymonth"));
			if (mycontent.contains("@@@user_card_expiryyear@@@")) mycontent = mycontent.replaceAll("@@@user_card_expiryyear@@@", (String) httpReq.getAttribute("user_card_expiryyear"));
			if (mycontent.contains("@@@user_card_name@@@")) mycontent = mycontent.replaceAll("@@@user_card_name@@@", (String) httpReq.getAttribute("user_card_name"));
			if (mycontent.contains("@@@user_card_cvc@@@")) mycontent = mycontent.replaceAll("@@@user_card_cvc@@@", (String) httpReq.getAttribute("user_card_cvc"));
			if (mycontent.contains("@@@user_card_issue@@@")) mycontent = mycontent.replaceAll("@@@user_card_issue@@@", (String) httpReq.getAttribute("user_card_issue"));
			if (mycontent.contains("@@@user_card_postalcode@@@")) mycontent = mycontent.replaceAll("@@@user_card_postalcode@@@", (String) httpReq.getAttribute("user_card_postalcode"));
		}
		//verifica se contem o comeco @@@ads
		if(mycontent.contains("@@@ads")){
			AnnounceJB announceJB = (AnnounceJB) httpReq.getAttribute("announceJB"); //tenta recuperar do request
			if(announceJB == null){
				HttpSession httpSession = httpReq.getSession();
				announceJB = (AnnounceJB) httpSession.getAttribute("announceJB"); 	//tenta recuperar da sessao
			}
			if(announceJB != null){
				if(mycontent.contains("@@@ads_type_announce@@@")) mycontent = mycontent.replaceAll("@@@ads_type_announce@@@", announceJB.getTypeAnnounce());
				if(mycontent.contains("@@@ads_price_fixed@@@"))  mycontent = mycontent.replaceAll("@@@ads_price_fixed@@@", announceJB.getPriceFixed());
				if(mycontent.contains("@@@ads_price_initial@@@"))  mycontent = mycontent.replaceAll("@@@ads_price_initial@@@", announceJB.getPriceInitial());
				if(mycontent.contains("@@@ads_bid_actual@@@"))  mycontent = mycontent.replaceAll("@@@ads_bid_actual@@@", announceJB.getBidActual());
				if(mycontent.contains("@@@ads_lasting@@@"))  mycontent = mycontent.replaceAll("@@@ads_lasting@@@", announceJB.getLasting());
				if(mycontent.contains("@@@ads_total_bids@@@"))  mycontent = mycontent.replaceAll("@@@ads_total_bids@@@", announceJB.getTotalBids());
				if(mycontent.contains("@@@ads_title@@@")) mycontent = mycontent.replaceAll("@@@ads_title@@@", announceJB.getTitle());
				if(mycontent.contains("@@@ads_description@@@"))  mycontent = mycontent.replaceAll("@@@ads_description@@@", announceJB.getDescription());
				if(mycontent.contains("@@@ads_name_category@@@"))  mycontent = mycontent.replaceAll("@@@ads_name_category@@@", announceJB.getNameCategory());
				if(mycontent.contains("@@@ads_fk_collection_id@@@"))  mycontent = mycontent.replaceAll("@@@ads_fk_collection_id@@@", announceJB.getIdCollection());
				if(mycontent.contains("@@@ads_fk_item_id@@@"))  mycontent = mycontent.replaceAll("@@@ads_fk_item_id@@@", announceJB.getIdItem());
				if(mycontent.contains("@@@ads_fk_user_id@@@"))  mycontent = mycontent.replaceAll("@@@ads_fk_user_id@@@", announceJB.getIdMember());
				if(mycontent.contains("@@@ads_fk_category_id@@@"))  mycontent = mycontent.replaceAll("@@@ads_fk_category_id@@@", announceJB.getIdCategory());
				if(mycontent.contains("@@@ads_path_photo_ad@@@"))  mycontent = mycontent.replaceAll("@@@ads_path_photo_ad@@@", announceJB.getPath_photo_ad());
			}
		}
		
		return mycontent;
	}
	
	public void parse_output_register(String error, String email, String username, String password, String name, User user) {
		if (body.contains("@@@error@@@")) body = body.replaceAll("@@@error@@@", ("" + error).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@email@@@")) body = body.replaceAll("@@@email@@@", ("" + email).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@username@@@")) body = body.replaceAll("@@@username@@@", ("" + username).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@password@@@")) body = body.replaceAll("@@@password@@@", ("" + password).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@name@@@")) body = body.replaceAll("@@@name@@@", ("" + name).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@organisation@@@")) body = body.replaceAll("@@@organisation@@@", "" + user.getOrganisation().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@notes@@@")) body = body.replaceAll("@@@notes@@@", "" + user.getNotes().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@card_type@@@")) body = body.replaceAll("@@@card_type@@@", "" + user.getCardType().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@card_number@@@")) body = body.replaceAll("@@@card_number@@@", "" + user.getCardNumber().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@card_issuedmonth@@@")) body = body.replaceAll("@@@card_issuedmonth@@@", "" + user.getCardIssuedMonth().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@card_issuedyear@@@")) body = body.replaceAll("@@@card_issuedyear@@@", "" + user.getCardIssuedYear().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@card_expirymonth@@@")) body = body.replaceAll("@@@card_expirymonth@@@", "" + user.getCardExpiryMonth().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@card_expiryyear@@@")) body = body.replaceAll("@@@card_expiryyear@@@", "" + user.getCardExpiryYear().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@card_name@@@")) body = body.replaceAll("@@@card_name@@@", "" + user.getCardName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@card_cvc@@@")) body = body.replaceAll("@@@card_cvc@@@", "" + user.getCardCVC().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@card_issue@@@")) body = body.replaceAll("@@@card_issue@@@", "" + user.getCardIssue().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@card_postalcode@@@")) body = body.replaceAll("@@@card_postalcode@@@", "" + user.getCardPostalcode().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_name@@@")) body = body.replaceAll("@@@delivery_name@@@", "" + user.getDeliveryName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_organisation@@@")) body = body.replaceAll("@@@delivery_organisation@@@", "" + user.getDeliveryOrganisation().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_address@@@")) body = body.replaceAll("@@@delivery_address@@@", "" + user.getDeliveryAddress().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_postalcode@@@")) body = body.replaceAll("@@@delivery_postalcode@@@", "" + user.getDeliveryPostalcode().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_city@@@")) body = body.replaceAll("@@@delivery_city@@@", "" + user.getDeliveryCity().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_state@@@")) body = body.replaceAll("@@@delivery_state@@@", "" + user.getDeliveryState().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_country@@@")) body = body.replaceAll("@@@delivery_country@@@", "" + user.getDeliveryCountry().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_phone@@@")) body = body.replaceAll("@@@delivery_phone@@@", "" + user.getDeliveryPhone().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_fax@@@")) body = body.replaceAll("@@@delivery_fax@@@", "" + user.getDeliveryFax().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_email@@@")) body = body.replaceAll("@@@delivery_email@@@", "" + user.getDeliveryEmail().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@delivery_website@@@")) body = body.replaceAll("@@@delivery_website@@@", "" + user.getDeliveryWebsite().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_name@@@")) body = body.replaceAll("@@@invoice_name@@@", "" + user.getInvoiceName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_organisation@@@")) body = body.replaceAll("@@@invoice_organisation@@@", "" + user.getInvoiceOrganisation().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_address@@@")) body = body.replaceAll("@@@invoice_address@@@", "" + user.getInvoiceAddress().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_postalcode@@@")) body = body.replaceAll("@@@invoice_postalcode@@@", "" + user.getInvoicePostalcode().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_city@@@")) body = body.replaceAll("@@@invoice_city@@@", "" + user.getInvoiceCity().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_state@@@")) body = body.replaceAll("@@@invoice_state@@@", "" + user.getInvoiceState().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_country@@@")) body = body.replaceAll("@@@invoice_country@@@", "" + user.getInvoiceCountry().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_phone@@@")) body = body.replaceAll("@@@invoice_phone@@@", "" + user.getInvoicePhone().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_fax@@@")) body = body.replaceAll("@@@invoice_fax@@@", "" + user.getInvoiceFax().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_email@@@")) body = body.replaceAll("@@@invoice_email@@@", "" + user.getInvoiceEmail().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (body.contains("@@@invoice_website@@@")) body = body.replaceAll("@@@invoice_website@@@", "" + user.getInvoiceWebsite().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
	}



	public String parse_output_webeditor(String webeditor, ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_administrator, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		return parse_output_webeditor(webeditor, server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, id, table, column, session_version, default_version, "", "", "", session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry);
	}
	public String parse_output_webeditor(String webeditor, ServletContext server, Request request, Response response, Session session, String script_name, String query_string, String session_mode, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_administrator, DB db, Configuration config, String id, String table, String column, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, String session_currency, String default_currency, String default_shopcartsummary_page, String default_shopcartsummary_entry) throws Exception {
		String name = "content";
		String value = "";
		String width = "100%";
		String widthUnit = "";
		String height = "400";
		String heightUnit = "px";
		String developer = "";
		String manager = "";
		String stylesheet = "'/stylesheet.jsp?id=" + config.get(db, "default_stylesheet") + "'";

		Matcher valueMatch = Pattern.compile("(^|:)value=\"(.*?)\"(:|$)", Pattern.DOTALL).matcher(webeditor);
		if (valueMatch.find()) {
			value = "" + valueMatch.group(2);
		}

		String[] webeditorparts = webeditor.split(":");
		for (int i=0; i<webeditorparts.length; i++) {
			String webeditorpart = webeditorparts[i];
			Matcher nameMatches = Pattern.compile("^name=([a-zA-Z0-9\\w_]+?)$").matcher(webeditorpart);
			Matcher valueIdMatches = Pattern.compile("^value=([0-9]+?)$").matcher(webeditorpart);
			Matcher valueMatches = Pattern.compile("^value=\"(.*?)\"$").matcher(webeditorpart);
			Matcher widthMatches = Pattern.compile("^width=([0-9]+%?)$").matcher(webeditorpart);
			Matcher heightMatches = Pattern.compile("^height=([0-9]+%?)$").matcher(webeditorpart);
			Matcher managerNoMatches = Pattern.compile("^manager=(no|off)$").matcher(webeditorpart);
			Matcher managerYesMatches = Pattern.compile("^manager=(yes|wcm)$").matcher(webeditorpart);
			Matcher managerMatches = Pattern.compile("^manager=(.+?)$").matcher(webeditorpart);
			Matcher stylesheetDefaultMatches = Pattern.compile("^stylesheet=(default)$").matcher(webeditorpart);
			Matcher stylesheetMatches = Pattern.compile("^stylesheet=([0-9]+?)$").matcher(webeditorpart);
			if (nameMatches.find()) {
				name = "" + nameMatches.group(1);
			} else if (valueIdMatches.find()) {
				String value_id = "" + valueIdMatches.group(1);
				Page page = new Page(text);
				boolean preview = false;
				if ((session_mode.equals("preview")) || (session_mode.equals("admin"))) {
					page.read_primary_only(db, config, value_id, "content", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					page.parse_output(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, value_id, "content", "id", session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, page.getId());
					preview = page.previewScheduled(session);
				}
				if (! preview) {
					page.read_primary_only(db, config, value_id, "content_public", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
					page.parse_output(server, request, response, session, script_name, query_string, session_mode, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_administrator, db, config, value_id, "content_public", "id", session_version, default_version, session_device, session_usersegments, session_usertests, session_currency, default_currency, default_shopcartsummary_page, default_shopcartsummary_entry, page.getId());
				}
				value = page.getContent();
			} else if (valueMatches.find()) {
				value = "" + valueMatches.group(1);
			} else if (widthMatches.find()) {
				width = "" + widthMatches.group(1);
				if (! width.endsWith("%")) {
					widthUnit = "px";
				} else {
					widthUnit = "";
				}
			} else if (heightMatches.find()) {
				height = "" + heightMatches.group(1);
				if (! height.endsWith("%")) {
					heightUnit = "px";
				} else {
					heightUnit = "";
				}
			} else if (managerNoMatches.find()) {
				manager = "";
			} else if (managerYesMatches.find()) {
				if (session.get("administrator").equals("administrator")) {
					manager = "manager_wcm";
				}
			} else if (managerMatches.find()) {
				manager = "manager_" + managerMatches.group(1);
			} else if (stylesheetDefaultMatches.find()) {
				stylesheet = "'/stylesheet.jsp?id=" + config.get(db, "default_stylesheet") + "'";
			} else if (stylesheetMatches.find()) {
				if ((""+stylesheetMatches.group(1)).equals("0")) {
					stylesheet = "''";
				} else {
					stylesheet = "'/stylesheet.jsp?id=" + stylesheetMatches.group(1) + "'";
				}
			}
		}

		String output = "";
		output += "<script type=\"text/javascript\">WebEditorToolbar( { toolbar: '" + config.get(db, "hardcore_toolbar") + "' } );</script>";
		output += "<table style=\"border: none; padding: 0px; width: " + width + widthUnit + "; height: " + height + heightUnit + ";\"><tr><td style=\"border: none; padding: 0px; width: " + width + widthUnit + "; height: " + height + heightUnit + ";\">";
		output += "<script type=\"text/javascript\">WebEditor('" + name + "', '" + Common.html_javascript_specialcharacters(value) + "', { language: 'jsp', manager: '" + manager + "', stylesheet: " + stylesheet + ", width: '" + width + "', height: '" + height + "' })</script>";
		output += "</td></tr></table>";
                output += "<script type=\"text/javascript\">WebEditorDOMInspector('" + name + "');</script>";
		return output;
	}



	public void parse_output_personal_admin(User user, Page page, DB db, Configuration config) {
		String template_options = "<select name=\"template\">";
		template_options += "<option value=\"" + page.getTemplate() + "\">" + text.display("pleaseselect");
		if (page.getTemplate().equals("")) {
			template_options += "<option value=\"\" selected>" + text.display("default");
		} else {
			template_options += "<option value=\"\">" + text.display("default");
		}
		if (page.getTemplate().equals("0")) {
			template_options += "<option value=\"0\" selected>" + text.display("none");
		} else {
			template_options += "<option value=\"0\">" + text.display("none");
		}
		template_options += page.select_options(db, "template", page.getTemplate());
		template_options += "</select>";

		String stylesheet_options = "<select name=\"stylesheet\" onChange=\"HardCoreWebEditorStylesheet((this.value == '0') ? false : ((this.value == '') ? '/stylesheet.jsp?id=" + config.get(db, "default_stylesheet") + "' : '/stylesheet.jsp?id='+this.value))\">";
		stylesheet_options += "<option value=\"" + page.getStyleSheet() + "\">" + text.display("pleaseselect");
		if (page.getStyleSheet().equals("")) {
			stylesheet_options += "<option value=\"\" selected>" + text.display("default");
		} else {
			stylesheet_options += "<option value=\"\">" + text.display("default");
		}
		if (page.getStyleSheet().equals("0")) {
			stylesheet_options += "<option value=\"0\" selected>" + text.display("none");
		} else {
			stylesheet_options += "<option value=\"0\">" + text.display("none");
		}
		stylesheet_options += page.select_options(db, "stylesheet", page.getStyleSheet());
		stylesheet_options += "</select>";

		String stylesheet = "";
		if (page.getStyleSheet().equals("0")) {
			stylesheet = "false";
		} else if (page.getStyleSheet().equals("")) {
			stylesheet = "'/stylesheet.jsp?id=" + config.get(db, "default_stylesheet") + "'";
		} else {
			stylesheet = "'/stylesheet.jsp?id=" + page.getStyleSheet() + "'";
		}
		String width = "100%";
		String widthUnit = "";
		String height = "400";
		String heightUnit = "px";
		String webeditor = "";
		webeditor += "<script type=\"text/javascript\">HardCoreWebEditorToolbar();</script>";
		webeditor += "<table style=\"border: none; padding: 0px; width: " + width + widthUnit + "; height: " + height + heightUnit + ";\"><tr><td style=\"border: none; padding: 0px; width: " + width + widthUnit + "; height: " + height + heightUnit + ";\">";
		webeditor += "<script type=\"text/javascript\">content_editor = new HardCoreWebEditor('/" + text.display("adminpath") + "/webeditor/', 'jsp', 'content', '" + Common.html_javascript_specialcharacters(page.getContent()) + "', '" + Common.html_javascript_specialcharacters(html.encode(page.getContent())) + "', " + stylesheet + ", '" + page.getDeveloper() + "', 'manager_personal', '', '', '', '', '', '" + width + "', '" + height + "');</script>";
		webeditor += "</td></tr></table>";
                webeditor += "<script type=\"text/javascript\">HardCoreWebEditorDOMInspector('content');</script>";

		String mycontent = getBody();
		if (mycontent.contains("@@@personal_content@@@")) mycontent = mycontent.replaceAll("@@@personal_content@@@", webeditor.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_title@@@")) mycontent = mycontent.replaceAll("@@@personal_title@@@", page.getTitle().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_template@@@")) mycontent = mycontent.replaceAll("@@@personal_template@@@", page.getTemplate().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_stylesheet@@@")) mycontent = mycontent.replaceAll("@@@personal_stylesheet@@@", page.getStyleSheet().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_template_options@@@")) mycontent = mycontent.replaceAll("@@@personal_template_options@@@", template_options.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_stylesheet_options@@@")) mycontent = mycontent.replaceAll("@@@personal_stylesheet_options@@@", stylesheet_options.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_name@@@")) mycontent = mycontent.replaceAll("@@@personal_name@@@", user.getName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_email@@@")) mycontent = mycontent.replaceAll("@@@personal_email@@@", user.getEmail().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_username@@@")) mycontent = mycontent.replaceAll("@@@personal_username@@@", user.getUsername().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_password@@@")) mycontent = mycontent.replaceAll("@@@personal_password@@@", user.getPassword().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_organisation@@@")) mycontent = mycontent.replaceAll("@@@personal_organisation@@@", user.getOrganisation().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_card_type@@@")) mycontent = mycontent.replaceAll("@@@personal_card_type@@@", user.getCardType().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_card_number@@@")) mycontent = mycontent.replaceAll("@@@personal_card_number@@@", user.getCardNumber().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_card_issuedmonth@@@")) mycontent = mycontent.replaceAll("@@@personal_card_issuedmonth@@@", user.getCardIssuedMonth().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_card_issuedyear@@@")) mycontent = mycontent.replaceAll("@@@personal_card_issuedyear@@@", user.getCardIssuedYear().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_card_expirymonth@@@")) mycontent = mycontent.replaceAll("@@@personal_card_expirymonth@@@", user.getCardExpiryMonth().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_card_expiryyear@@@")) mycontent = mycontent.replaceAll("@@@personal_card_expiryyear@@@", user.getCardExpiryYear().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_card_name@@@")) mycontent = mycontent.replaceAll("@@@personal_card_name@@@", user.getCardName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_card_cvc@@@")) mycontent = mycontent.replaceAll("@@@personal_card_cvc@@@", user.getCardCVC().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_card_issue@@@")) mycontent = mycontent.replaceAll("@@@personal_card_issue@@@", user.getCardIssue().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_card_postalcode@@@")) mycontent = mycontent.replaceAll("@@@personal_card_postalcode@@@", user.getCardPostalcode().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_delivery_name@@@")) mycontent = mycontent.replaceAll("@@@personal_delivery_name@@@", user.getDeliveryName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_delivery_organisation@@@")) mycontent = mycontent.replaceAll("@@@personal_delivery_organisation@@@", user.getDeliveryOrganisation().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_delivery_address@@@")) mycontent = mycontent.replaceAll("@@@personal_delivery_address@@@", user.getDeliveryAddress().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_delivery_postalcode@@@")) mycontent = mycontent.replaceAll("@@@personal_delivery_postalcode@@@", user.getDeliveryPostalcode().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_delivery_city@@@")) mycontent = mycontent.replaceAll("@@@personal_delivery_city@@@", user.getDeliveryCity().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_delivery_state@@@")) mycontent = mycontent.replaceAll("@@@personal_delivery_state@@@", user.getDeliveryState().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_delivery_country@@@")) mycontent = mycontent.replaceAll("@@@personal_delivery_country@@@", user.getDeliveryCountry().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_delivery_phone@@@")) mycontent = mycontent.replaceAll("@@@personal_delivery_phone@@@", user.getDeliveryPhone().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_delivery_fax@@@")) mycontent = mycontent.replaceAll("@@@personal_delivery_fax@@@", user.getDeliveryFax().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_delivery_email@@@")) mycontent = mycontent.replaceAll("@@@personal_delivery_email@@@", user.getDeliveryEmail().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_delivery_website@@@")) mycontent = mycontent.replaceAll("@@@personal_delivery_website@@@", user.getDeliveryWebsite().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_invoice_name@@@")) mycontent = mycontent.replaceAll("@@@personal_invoice_name@@@", user.getInvoiceName().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_invoice_organisation@@@")) mycontent = mycontent.replaceAll("@@@personal_invoice_organisation@@@", user.getInvoiceOrganisation().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_invoice_address@@@")) mycontent = mycontent.replaceAll("@@@personal_invoice_address@@@", user.getInvoiceAddress().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_invoice_postalcode@@@")) mycontent = mycontent.replaceAll("@@@personal_invoice_postalcode@@@", user.getInvoicePostalcode().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_invoice_city@@@")) mycontent = mycontent.replaceAll("@@@personal_invoice_city@@@", user.getInvoiceCity().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_invoice_state@@@")) mycontent = mycontent.replaceAll("@@@personal_invoice_state@@@", user.getInvoiceState().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_invoice_country@@@")) mycontent = mycontent.replaceAll("@@@personal_invoice_country@@@", user.getInvoiceCountry().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_invoice_phone@@@")) mycontent = mycontent.replaceAll("@@@personal_invoice_phone@@@", user.getInvoicePhone().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_invoice_fax@@@")) mycontent = mycontent.replaceAll("@@@personal_invoice_fax@@@", user.getInvoiceFax().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_invoice_email@@@")) mycontent = mycontent.replaceAll("@@@personal_invoice_email@@@", user.getInvoiceEmail().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		if (mycontent.contains("@@@personal_invoice_website@@@")) mycontent = mycontent.replaceAll("@@@personal_invoice_website@@@", user.getInvoiceWebsite().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));

		Iterator contentelements = page.elements(db, config).keySet().iterator();
		while (contentelements.hasNext()) {
			String element = "" + contentelements.next();
			String options = "";
			options += "<select name=\"personal_" + element + "_content\">";
			options += "<option value=\"" + page.getElement().get(element) + "\">" + text.display("pleaseselect");
			if ((page != null) && (page.getElement() != null) && (page.getElement().get(element) != null)) {
				if ((page.getElement().get(element).equals("-1")) || (page.getElement().get(element).equals(""))) {
					options += "<option value=\"-1\" selected>" + text.display("default");
				} else {
					options += "<option value=\"-1\">" + text.display("default");
				}
				if (page.getElement().get(element).equals("0")) {
					options += "<option value=\"0\" selected>" + text.display("none");
				} else {
					options += "<option value=\"0\">" + text.display("none");
				}
				if (page.getElement().get(element).equals("-2")) {
					options += "<option value=\"-2\" selected>" + text.display("random");
				} else {
					options += "<option value=\"-2\">" + text.display("random");
				}
				options += page.select_options(db, element, "" + page.getElement().get(element));
			}
			options += "</select>";
			mycontent = mycontent.replaceAll("\\Q@@@personal_" + element + "_options@@@\\E", options.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		}
		setBody(mycontent);
	}



	public Page getTemplateContent() {
		if (template_content == null) {
			return new Page(text);
		} else {
			return template_content;
		}
	}
	public void setTemplateContent(Page value) {
		template_content = value;
	}



	public ArrayList<Content> getStyleSheetContents() {
		return stylesheet_contents;
	}



	public String getVersionMasterTitle() {
		if (version_master_title == null) {
			version_master_title = "";
			if ((! getVersionMaster().equals("")) && (! getVersionMaster().equals("0"))) {
				Content version_master = new Content(text);
				version_master.read(_db, _config, getVersionMaster(), _table, _column, _session_administrator, _session_userid, _session_username, _session_usertype, _session_usergroup, _session_usertypes, _session_usergroups, _session_version, _default_version, _session_device, _session_usersegments, _session_usertests, _session);
				if ((_session != null) && ((_session.get("mode").equals("preview")) || (_session.get("mode").equals("admin"))) && (_table.equals("content"))) {
					version_master.scheduled_read(_db, _config, getVersionMaster(), _session_administrator, _session_userid, _session_username, _session_usertype, _session_usergroup, _session_usertypes, _session_usergroups, _session_version, _default_version, _session_device, _session_usersegments, _session_usertests, _session);
				}
				version_master_title = version_master.getTitle();
			}
		}
		return version_master_title;
	}



	public String getPageTopTitle() {
		if (page_top_title == null) {
			page_top_title = "";
			if ((! getPageTop().equals("")) && (! getPageTop().equals("0"))) {
				Content page_top = new Content(text);
				page_top.read(_db, _config, getPageTop(), _table, _column, _session_administrator, _session_userid, _session_username, _session_usertype, _session_usergroup, _session_usertypes, _session_usergroups, _session_version, _default_version, _session_device, _session_usersegments, _session_usertests, _session);
				if ((_session != null) && ((_session.get("mode").equals("preview")) || (_session.get("mode").equals("admin"))) && (_table.equals("content"))) {
					page_top.scheduled_read(_db, _config, getPageTop(), _session_administrator, _session_userid, _session_username, _session_usertype, _session_usergroup, _session_usertypes, _session_usergroups, _session_version, _default_version, _session_device, _session_usersegments, _session_usertests, _session);
				}
				page_top_title = page_top.getTitle();
			}
		}
		return page_top_title;
	}



	public String getPageUpTitle() {
		if (page_up_title == null) {
			page_up_title = "";
			if ((! getPageUp().equals("")) && (! getPageUp().equals("0"))) {
				Content page_up = new Content(text);
				page_up.read(_db, _config, getPageUp(), _table, _column, _session_administrator, _session_userid, _session_username, _session_usertype, _session_usergroup, _session_usertypes, _session_usergroups, _session_version, _default_version, _session_device, _session_usersegments, _session_usertests, _session);
				if ((_session != null) && ((_session.get("mode").equals("preview")) || (_session.get("mode").equals("admin"))) && (_table.equals("content"))) {
					page_up.scheduled_read(_db, _config, getPageUp(), _session_administrator, _session_userid, _session_username, _session_usertype, _session_usergroup, _session_usertypes, _session_usergroups, _session_version, _default_version, _session_device, _session_usersegments, _session_usertests, _session);
				}
				page_up_title = page_up.getTitle();
			}
		}
		return page_up_title;
	}



	public String getPagePreviousTitle() {
		if (page_previous_title == null) {
			page_previous_title = "";
			if ((! getPagePrevious().equals("")) && (! getPagePrevious().equals("0"))) {
				Content page_previous = new Content(text);
				page_previous.read(_db, _config, getPagePrevious(), _table, _column, _session_administrator, _session_userid, _session_username, _session_usertype, _session_usergroup, _session_usertypes, _session_usergroups, _session_version, _default_version, _session_device, _session_usersegments, _session_usertests, _session);
				if ((_session != null) && ((_session.get("mode").equals("preview")) || (_session.get("mode").equals("admin"))) && (_table.equals("content"))) {
					page_previous.scheduled_read(_db, _config, getPagePrevious(), _session_administrator, _session_userid, _session_username, _session_usertype, _session_usergroup, _session_usertypes, _session_usergroups, _session_version, _default_version, _session_device, _session_usersegments, _session_usertests, _session);
				}
				page_previous_title = page_previous.getTitle();
			}
		}
		return page_previous_title;
	}



	public String getPageNextTitle() {
		if (page_next_title == null) {
			page_next_title = "";
			if ((! getPageNext().equals("")) && (! getPageNext().equals("0"))) {
				Content page_next = new Content(text);
				page_next.read(_db, _config, getPageNext(), _table, _column, _session_administrator, _session_userid, _session_username, _session_usertype, _session_usergroup, _session_usertypes, _session_usergroups, _session_version, _default_version, _session_device, _session_usersegments, _session_usertests, _session);
				if ((_session != null) && ((_session.get("mode").equals("preview")) || (_session.get("mode").equals("admin"))) && (_table.equals("content"))) {
					page_next.scheduled_read(_db, _config, getPageNext(), _session_administrator, _session_userid, _session_username, _session_usertype, _session_usergroup, _session_usertypes, _session_usergroups, _session_version, _default_version, _session_device, _session_usersegments, _session_usertests, _session);
				}
				page_next_title = page_next.getTitle();
			}
		}
		return page_next_title;
	}



	public String getPageFirstTitle() {
		if (page_first_title == null) {
			page_first_title = "";
			if ((! getPageFirst().equals("")) && (! getPageFirst().equals("0"))) {
				Content page_first = new Content(text);
				page_first.read(_db, _config, getPageFirst(), _table, _column, _session_administrator, _session_userid, _session_username, _session_usertype, _session_usergroup, _session_usertypes, _session_usergroups, _session_version, _default_version, _session_device, _session_usersegments, _session_usertests, _session);
				if ((_session != null) && ((_session.get("mode").equals("preview")) || (_session.get("mode").equals("admin"))) && (_table.equals("content"))) {
					page_first.scheduled_read(_db, _config, getPageFirst(), _session_administrator, _session_userid, _session_username, _session_usertype, _session_usergroup, _session_usertypes, _session_usergroups, _session_version, _default_version, _session_device, _session_usersegments, _session_usertests, _session);
				}
				page_first_title = page_first.getTitle();
			}
		}
		return page_first_title;
	}



	public String getPageLastTitle() {
		if (page_last_title == null) {
			page_last_title = "";
			if ((! getPageLast().equals("")) && (! getPageLast().equals("0"))) {
				Content page_last = new Content(text);
				page_last.read(_db, _config, getPageLast(), _table, _column, _session_administrator, _session_userid, _session_username, _session_usertype, _session_usergroup, _session_usertypes, _session_usergroups, _session_version, _default_version, _session_device, _session_usersegments, _session_usertests, _session);
				if ((_session != null) && ((_session.get("mode").equals("preview")) || (_session.get("mode").equals("admin"))) && (_table.equals("content"))) {
					page_last.scheduled_read(_db, _config, getPageLast(), _session_administrator, _session_userid, _session_username, _session_usertype, _session_usergroup, _session_usertypes, _session_usergroups, _session_version, _default_version, _session_device, _session_usersegments, _session_usertests, _session);
				}
				page_last_title = page_last.getTitle();
			}
		}
		return page_last_title;
	}



	public String getPageTitle() {
		return page_title;
	}



	public String getBody() {
		return body;
	}
	public void setBody(String value) {
		body = value;
	}



	public void doParseOutput(boolean value) {
		do_parse_output = value;
	}



	public void parse_output_extensions(ServletContext server, Request request, Response response, Session session) {
		parse_output_extensions(server, request.getRequest(), response.getResponse(), session.getSession());
	}
	public void parse_output_extensions(ServletContext server, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String output = "";
		String content = body;
		Pattern p = Pattern.compile("@@@extension:(.+?)(\\(|%28)(.*?)(\\)|%29)@@@");
		Matcher m = p.matcher(content);
		while (m.find()) {
			String extensionMatch = m.group(0);
			String extensionName = m.group(1);
			try {
				session.setAttribute("extension", m.group(3));
				request.setAttribute("extension", m.group(3));
			} catch (Exception e) {
			}
			String[] extensionParts = content.split(extensionMatch.replaceAll("([-+/\\*\\.\\(\\)\\|\\[\\]\\^\\$\\\\\\?\\{\\}])", "\\\\$1"),2);
			if (extensionParts.length > 0) {
//				output += extensionParts[0].replaceAll("@@@[ a-zA-Z0-9\\w]+@@@", "");
				output += extensionParts[0];
			}
			output += Common.execute("/" + text.display("adminpath") + "/extension/" + extensionName + ".jsp", null, null, "extension", server, session, request, response);
			if (extensionParts.length > 1) {
				content = "" + extensionParts[1];
			} else {
				content = "";
			}
			m.reset(content);
		}
		try {
			session.setAttribute("extension", "");
			request.removeAttribute("extension");
		} catch (Exception e) {
		}
//		output += content.replaceAll("@@@[ a-zA-Z0-9\\w]+@@@", "");
		output += content;
		body = output;
	}



	public String deleteExportStaticFile(ServletContext myserver, Request request, Response response, Session session, JspWriter out, Configuration myconfig, DB db) throws Exception {
		return deleteExportStaticFile(myserver, request.getRequest(), response.getResponse(), session.getSession(), out, myconfig, db);
	}
	public String deleteExportStaticFile(ServletContext myserver, HttpServletRequest request, HttpServletResponse response, HttpSession session, JspWriter out, Configuration myconfig, DB db) throws Exception {
		String exportfilename = "";
		if (! myconfig.get(db, "export_rootpath").equals("")) {
			String save_server_filename = getServerFilename();
			if ((! myconfig.get(db, "export_rootpath").endsWith("/")) && (! myconfig.get(db, "export_rootpath").endsWith("\\"))) {
				myconfig.setTemp("export_rootpath", myconfig.get(db, "export_rootpath") + "/");
			}
			// Do not allow export to website folder and sub-folders - do not allow hosting client to export to another hosting client's folder etc.
			// if (myconfig.get(db, "export_rootpath").replaceAll("\\", "/").startsWith(Common.getRealPath(myserver, "/").replaceAll("\\", "/"))) return "";
			// Only allow export to hosting client's own website sub-folder
			if (! myconfig.get(db, "export_rootpath").replaceAll("\\\\", "/").startsWith(Common.getRealPath(myserver, myconfig.get(db, "URLrootpath")).replaceAll("\\\\", "/"))) return "";

			if ((getContentClass().equals("image")) || (getContentClass().equals("file"))) {
				exportfilename = myconfig.get(db, "export_rootpath") + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename();
				Common.deleteFile(exportfilename);
				if (License.valid(db, myconfig, "enterprise")) {
					Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/export"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
				}
			} else if (getContentClass().equals("stylesheet")) {
				if (getServerFilename().equals("")) setServerFilename("stylesheet_" + getId() + ".css");
				setServerFilename(getServerFilename().replaceAll("\\..*?$", ".css"));
				exportfilename = myconfig.get(db, "export_rootpath") + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename();
				Common.deleteFile(exportfilename);
				if (License.valid(db, myconfig, "enterprise")) {
					Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/export"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
				}
			} else if (getContentClass().equals("script")) {
				if (getServerFilename().equals("")) setServerFilename("script_" + getId() + ".js");
				setServerFilename(getServerFilename().replaceAll("\\..*?$", ".js"));
				exportfilename = myconfig.get(db, "export_rootpath") + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename();
				Common.deleteFile(exportfilename);
				if (License.valid(db, myconfig, "enterprise")) {
					Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/export"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
				}
			} else if (getContentClass().equals("page")) {
				if (getServerFilename().equals("")) setServerFilename("page_" + getId() + ".html");
				if ((! getServerFilename().endsWith(".htm")) && (! getServerFilename().endsWith(".html"))) {
					setServerFilename(getServerFilename().replaceAll("\\..*?$", ".html"));
					setServerFilename(getServerFilename().replaceAll("^([^.]*)$", "$1.html"));
				}
				exportfilename = myconfig.get(db, "export_rootpath") + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename();
				Common.deleteFile(exportfilename);
				if (License.valid(db, myconfig, "enterprise")) {
					Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/export"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
				}
			}
			setServerFilename(save_server_filename);
		}
		return exportfilename;
	}



	public String exportStaticFile(ServletContext myserver, Request request, Response response, Session session, JspWriter out, Configuration myconfig, DB db) {
		return exportStaticFile(myserver, request.getRequest(), response.getResponse(), session.getSession(), out, myconfig, db);
	}
	public String exportStaticFile(ServletContext myserver, HttpServletRequest request, HttpServletResponse response, HttpSession session, JspWriter out, Configuration myconfig, DB db) {
		String exportfilename = "";
		if (! myconfig.get(db, "export_rootpath").equals("")) {
			String save_server_filename = getServerFilename();
			if ((! myconfig.get(db, "export_rootpath").endsWith("/")) && (! myconfig.get(db, "export_rootpath").endsWith("\\"))) {
				myconfig.setTemp("export_rootpath", myconfig.get(db, "export_rootpath") + "/");
			}
			// Do not allow export to website folder and sub-folders - do not allow hosting client to export to another hosting client's folder etc.
			// if (myconfig.get(db, "export_rootpath").replaceAll("\\", "/").startsWith(Common.getRealPath(myserver, "/").replaceAll("\\", "/"))) return "";
			// Only allow export to hosting client's own website sub-folder
			if (! myconfig.get(db, "export_rootpath").replaceAll("\\\\", "/").startsWith(Common.getRealPath(myserver, myconfig.get(db, "URLrootpath")).replaceAll("\\\\", "/"))) return "";

			if ((getContentClass().equals("image")) || (getContentClass().equals("file"))) {
				exportfilename = myconfig.get(db, "export_rootpath") + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename();
				Common.createFolder(exportfilename);
				Common.copyFile(Common.getRealPath(myserver, myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename()), exportfilename);
				if (License.valid(db, myconfig, "enterprise")) {
					Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/export"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
				}
			} else if (getContentClass().equals("stylesheet")) {
				if (getServerFilename().equals("")) setServerFilename("stylesheet_" + getId() + ".css");
				setServerFilename(getServerFilename().replaceAll("\\..*?$", ".css"));
				exportfilename = myconfig.get(db, "export_rootpath") + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename();
				String filecontent = outputStaticFileContent(myserver, request, response, session, out, myconfig, db);
				filecontent = exportStaticFile_adjustlinks(filecontent, myserver, request, response, session, out, myconfig, db);
				Common.createFolder(exportfilename);
				Common.writeFile(exportfilename, filecontent, myconfig.get(db, "charset"));
				if (License.valid(db, myconfig, "enterprise")) {
					Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/export"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
				}
			} else if (getContentClass().equals("script")) {
				if (getServerFilename().equals("")) setServerFilename("script_" + getId() + ".js");
				setServerFilename(getServerFilename().replaceAll("\\..*?$", ".js"));
				exportfilename = myconfig.get(db, "export_rootpath") + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename();
				String filecontent = outputStaticFileContent(myserver, request, response, session, out, myconfig, db);
				filecontent = exportStaticFile_adjustlinks(filecontent, myserver, request, response, session, out, myconfig, db);
				Common.createFolder(exportfilename);
				Common.writeFile(exportfilename, filecontent, myconfig.get(db, "charset"));
				if (License.valid(db, myconfig, "enterprise")) {
					Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/export"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
				}
			} else if (getContentClass().equals("page")) {
				if (getServerFilename().equals("")) setServerFilename("page_" + getId() + ".html");
				if ((! getServerFilename().endsWith(".htm")) && (! getServerFilename().endsWith(".html"))) {
					setServerFilename(getServerFilename().replaceAll("\\..*?$", ".html"));
					setServerFilename(getServerFilename().replaceAll("^([^.]*)$", "$1.html"));
				}
				exportfilename = myconfig.get(db, "export_rootpath") + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename();
				String filecontent = outputStaticFileContent(myserver, request, response, session, out, myconfig, db);
				filecontent = exportStaticFile_adjustlinks(filecontent, myserver, request, response, session, out, myconfig, db);
				Common.createFolder(exportfilename);
				Common.writeFile(exportfilename, filecontent, myconfig.get(db, "charset"));
				if (License.valid(db, myconfig, "enterprise")) {
					Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/export"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
				}
			}
			setServerFilename(save_server_filename);
		}
		return exportfilename;
	}



	public String exportStaticFile_adjustlinks(String filecontent, ServletContext myserver, HttpServletRequest request, HttpServletResponse response, HttpSession session, JspWriter out, Configuration myconfig, DB db) {
		Pattern p = Pattern.compile("/(index|page|script|stylesheet|image|file|link)\\.jsp\\?id=([0-9]+)([^0-9])");
		Matcher m = p.matcher(filecontent);
		while (m.find()) {
			String myid = "" + m.group(2);
			Content mycontent = new Content();
			mycontent.read(db, myconfig, myid, "content_public", "id");
			if (mycontent.getContentClass().equals("stylesheet")) {
				if (mycontent.getServerFilename().equals("")) mycontent.setServerFilename("stylesheet_" + myid + ".css");
				mycontent.setServerFilename(mycontent.getServerFilename().replaceAll("\\..*?$", ".css"));
				filecontent = filecontent.replaceAll("/stylesheet\\.jsp\\?id=" + myid + "&", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "?");
				filecontent = filecontent.replaceAll("/stylesheet\\.jsp\\?id=" + myid + "([^0-9])", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "$1");
			} else if (mycontent.getContentClass().equals("script")) {
				if (mycontent.getServerFilename().equals("")) mycontent.setServerFilename("script_" + myid + ".js");
				mycontent.setServerFilename(mycontent.getServerFilename().replaceAll("\\..*?$", ".js"));
				filecontent = filecontent.replaceAll("/script\\.jsp\\?id=" + myid + "&", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "?");
				filecontent = filecontent.replaceAll("/script\\.jsp\\?id=" + myid + "([^0-9])", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "$1");
			} else if (mycontent.getContentClass().equals("page")) {
				if (mycontent.getServerFilename().equals("")) mycontent.setServerFilename("page_" + myid + ".html");
				if ((! mycontent.getServerFilename().endsWith(".htm")) && (! mycontent.getServerFilename().endsWith(".html"))) {
					mycontent.setServerFilename(mycontent.getServerFilename().replaceAll("\\..*?$", ".html"));
					mycontent.setServerFilename(mycontent.getServerFilename().replaceAll("^([^.]*)$", "$1.html"));
				}
				filecontent = filecontent.replaceAll("/index\\.jsp\\?id=" + myid + "&", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "?");
				filecontent = filecontent.replaceAll("/index\\.jsp\\?id=" + myid + "([^0-9])", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "$1");
				filecontent = filecontent.replaceAll("/page\\.jsp\\?id=" + myid + "&", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "?");
				filecontent = filecontent.replaceAll("/page\\.jsp\\?id=" + myid + "([^0-9])", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "$1");
			}
			filecontent = filecontent.replaceAll("/stylesheet\\.jsp\\?id=" + myid + "&", myconfig.get(db, "URLrootpath") + "stylesheet_" + myid + ".css" + "?");
			filecontent = filecontent.replaceAll("/stylesheet\\.jsp\\?id=" + myid + "([^0-9])", myconfig.get(db, "URLrootpath") + "stylesheet_" + myid + ".css" + "$1");
			filecontent = filecontent.replaceAll("/script\\.jsp\\?id=" + myid + "&", myconfig.get(db, "URLrootpath") + "script_" + myid + ".js" + "?");
			filecontent = filecontent.replaceAll("/script\\.jsp\\?id=" + myid + "([^0-9])", myconfig.get(db, "URLrootpath") + "script_" + myid + ".js" + "$1");
			filecontent = filecontent.replaceAll("/index\\.jsp\\?id=" + myid + "&", myconfig.get(db, "URLrootpath") + "page_" + myid + ".html" + "?");
			filecontent = filecontent.replaceAll("/index\\.jsp\\?id=" + myid + "([^0-9])", myconfig.get(db, "URLrootpath") + "page_" + myid + ".html" + "$1");
			filecontent = filecontent.replaceAll("/page\\.jsp\\?id=" + myid + "&", myconfig.get(db, "URLrootpath") + "page_" + myid + ".html" + "?");
			filecontent = filecontent.replaceAll("/page\\.jsp\\?id=" + myid + "([^0-9])", myconfig.get(db, "URLrootpath") + "page_" + myid + ".html" + "$1");
			filecontent = filecontent.replaceAll("/image\\.jsp\\?id=" + myid + "&", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "?");
			filecontent = filecontent.replaceAll("/image\\.jsp\\?id=" + myid + "([^0-9])", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "$1");
			filecontent = filecontent.replaceAll("/file\\.jsp\\?id=" + myid + "&", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "?");
			filecontent = filecontent.replaceAll("/file\\.jsp\\?id=" + myid + "([^0-9])", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "$1");
			filecontent = filecontent.replaceAll("/link\\.jsp\\?id=" + myid + "&", mycontent.getUrl() + "?");
			filecontent = filecontent.replaceAll("/link\\.jsp\\?id=" + myid + "([^0-9])", mycontent.getUrl() + "$1");
			m.reset(filecontent);
		}

		p = Pattern.compile("/(index|page|script|stylesheet|image|file|link)\\.jsp\\?([^\\&\"]+)(\\&|\")");
		m = p.matcher(filecontent);
		while (m.find()) {
			String myid = "" + m.group(2);
			Content mycontent = new Content();
			mycontent.read(db, myconfig, myid, "content_public", "id");
			String mycontentid = mycontent.getId();
			if (mycontent.getContentClass().equals("stylesheet")) {
				if (mycontent.getServerFilename().equals("")) mycontent.setServerFilename("stylesheet_" + mycontentid + ".css");
				mycontent.setServerFilename(mycontent.getServerFilename().replaceAll("\\..*?$", ".css"));
				filecontent = filecontent.replaceAll("/stylesheet\\.jsp\\?" + myid + "&", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "?");
				filecontent = filecontent.replaceAll("/stylesheet\\.jsp\\?" + myid + "(\\&|\")", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "$1");
			} else if (mycontent.getContentClass().equals("script")) {
				if (mycontent.getServerFilename().equals("")) mycontent.setServerFilename("script_" + mycontentid + ".js");
				mycontent.setServerFilename(mycontent.getServerFilename().replaceAll("\\..*?$", ".js"));
				filecontent = filecontent.replaceAll("/script\\.jsp\\?" + myid + "&", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "?");
				filecontent = filecontent.replaceAll("/script\\.jsp\\?" + myid + "(\\&|\")", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "$1");
			} else if (mycontent.getContentClass().equals("page")) {
				if (mycontent.getServerFilename().equals("")) mycontent.setServerFilename("page_" + mycontentid + ".html");
				if ((! mycontent.getServerFilename().endsWith(".htm")) && (! mycontent.getServerFilename().endsWith(".html"))) {
					mycontent.setServerFilename(mycontent.getServerFilename().replaceAll("\\..*?$", ".html"));
					mycontent.setServerFilename(mycontent.getServerFilename().replaceAll("^([^.]*)$", "$1.html"));
				}
				filecontent = filecontent.replaceAll("/index\\.jsp\\?" + myid + "&", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "?");
				filecontent = filecontent.replaceAll("/index\\.jsp\\?" + myid + "(\\&|\")", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "$1");
				filecontent = filecontent.replaceAll("/page\\.jsp\\?" + myid + "&", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "?");
				filecontent = filecontent.replaceAll("/page\\.jsp\\?" + myid + "(\\&|\")", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "$1");
			}
			filecontent = filecontent.replaceAll("/stylesheet\\.jsp\\?" + myid + "&", myconfig.get(db, "URLrootpath") + "stylesheet_" + mycontentid + ".css" + "?");
			filecontent = filecontent.replaceAll("/stylesheet\\.jsp\\?" + myid + "(\\&|\")", myconfig.get(db, "URLrootpath") + "stylesheet_" + mycontentid + ".css" + "$1");
			filecontent = filecontent.replaceAll("/script\\.jsp\\?" + myid + "&", myconfig.get(db, "URLrootpath") + "script_" + mycontentid + ".js" + "?");
			filecontent = filecontent.replaceAll("/script\\.jsp\\?" + myid + "(\\&|\")", myconfig.get(db, "URLrootpath") + "script_" + mycontentid + ".js" + "$1");
			filecontent = filecontent.replaceAll("/index\\.jsp\\?" + myid + "&", myconfig.get(db, "URLrootpath") + "page_" + mycontentid + ".html" + "?");
			filecontent = filecontent.replaceAll("/index\\.jsp\\?" + myid + "(\\&|\")", myconfig.get(db, "URLrootpath") + "page_" + mycontentid + ".html" + "$1");
			filecontent = filecontent.replaceAll("/page\\.jsp\\?" + myid + "&", myconfig.get(db, "URLrootpath") + "page_" + mycontentid + ".html" + "?");
			filecontent = filecontent.replaceAll("/page\\.jsp\\?" + myid + "(\\&|\")", myconfig.get(db, "URLrootpath") + "page_" + mycontentid + ".html" + "$1");
			filecontent = filecontent.replaceAll("/image\\.jsp\\?" + myid + "&", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "?");
			filecontent = filecontent.replaceAll("/image\\.jsp\\?" + myid + "(\\&|\")", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "$1");
			filecontent = filecontent.replaceAll("/file\\.jsp\\?" + myid + "&", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "?");
			filecontent = filecontent.replaceAll("/file\\.jsp\\?" + myid + "(\\&|\")", myconfig.get(db, "URLrootpath") + mycontent.getServerFilename() + "$1");
			filecontent = filecontent.replaceAll("/link\\.jsp\\?" + myid + "&", mycontent.getUrl() + "?");
			filecontent = filecontent.replaceAll("/link\\.jsp\\?" + myid + "(\\&|\")", mycontent.getUrl() + "$1");
			m.reset(filecontent);
		}

		filecontent = filecontent.replaceAll("\\.jsp", ".html");
		filecontent = filecontent.replaceAll("@@@.*?@@@", "");
		filecontent = filecontent.replaceAll("###.*?###", "");
		return filecontent;
	}



	public void outputStaticFile(ServletContext server, Request request, Response response, Session session, JspWriter out, Configuration myconfig, DB db) {
		outputStaticFile(server, request.getRequest(), response.getResponse(), session.getSession(), out, myconfig, db);
	}
	public void outputStaticFile(ServletContext server, HttpServletRequest request, HttpServletResponse response, HttpSession session, JspWriter out, Configuration myconfig, DB db) {
		if (myconfig.get(db, "use_static_content").equals("yes")) {
			if ((getContentClass().equals("image")) || (getContentClass().equals("file")) || (getContentClass().equals("link"))) {
				if (! getServerFilename().equals("")) {
					if (License.valid(db, myconfig, "enterprise")) {
						if (Common.fileExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename() + "/index.jsp"))) {
							Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/published"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "/index.jsp" + "\"");
						} else {
							Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/published"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
						}
					}
				}
			} else if (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.html?$").matcher(getServerFilename()).find()) {
				String filecontent = outputStaticFileContent(server, request, response, session, out, myconfig, db);
				Common.createFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()));
				Common.writeFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()), filecontent, myconfig.get(db, "charset"));
				if (License.valid(db, myconfig, "enterprise")) {
					Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/published"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
				}
			} else if (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.css$").matcher(getServerFilename()).find()) {
				String filecontent = outputStaticFileContent(server, request, response, session, out, myconfig, db);
				Common.createFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()));
				Common.writeFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()), filecontent, myconfig.get(db, "charset"));
				if (License.valid(db, myconfig, "enterprise")) {
					Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/published"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
				}
			} else if (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.js$").matcher(getServerFilename()).find()) {
				String filecontent = outputStaticFileContent(server, request, response, session, out, myconfig, db);
				Common.createFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()));
				Common.writeFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()), filecontent, myconfig.get(db, "charset"));
				if (License.valid(db, myconfig, "enterprise")) {
					Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/published"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
				}
			} else if (! getServerFilename().equals("")) {
				if (License.valid(db, myconfig, "enterprise")) {
					if (Common.fileExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename() + "/index.jsp"))) {
						Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/published"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "/index.jsp" + "\"");
					} else {
						Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/published"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
					}
				}
			}
		} else {
			if (! getServerFilename().equals("")) {
				if (License.valid(db, myconfig, "enterprise")) {
					if (Common.fileExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename() + "/index.jsp"))) {
						Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/published"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "/index.jsp" + "\"");
					} else {
						Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/published"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
					}
				}
			}
		}
	}



	public String outputStaticFileContent(ServletContext server, Request request, Response response, Session session, JspWriter out, Configuration myconfig, DB db) {
		return outputStaticFileContent(server, request.getRequest(), response.getResponse(), session.getSession(), out, myconfig, db);
	}
	public String outputStaticFileContent(ServletContext server, HttpServletRequest request, HttpServletResponse response, HttpSession session, JspWriter out, Configuration myconfig, DB db) {
		String filecontent = "";
		if ((getContentClass().equals("image")) || (getContentClass().equals("file")) || (getContentClass().equals("link"))) {
		} else if ((getContentClass().equals("stylesheet")) || (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.css$").matcher(getServerFilename()).find())) {
			filecontent = Common.readFile(Common.getRealPath(server, "/" + getContentClass() + ".original.css"));
			String mystylesheet = body;
			if (filecontent.contains("@@@stylesheet@@@")) filecontent = filecontent.replaceAll("@@@stylesheet@@@", mystylesheet.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
		} else if ((getContentClass().equals("script")) || (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.js$").matcher(getServerFilename()).find())) {
			filecontent = Common.readFile(Common.getRealPath(server, "/" + getContentClass() + ".original.js"));
			String myscript = body;
			if (filecontent.contains("@@@script@@@")) filecontent = filecontent.replaceAll("@@@script@@@", myscript.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
//		} else if ((Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.html?$").matcher(getServerFilename()).find()) || (getServerFilename().equals(""))) {
//		} else if ((getContentClass().equals("page")) || (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.html?$").matcher(getServerFilename()).find())) {
		} else {
			String mydoctype = header(db, myconfig, "doctype", "").trim();
			if (mydoctype.equals("")) {
				mydoctype = myconfig.get(db, "doctype");
			}
			String XHTMLclosed = "";
			String BODYmargins = "";
			if (mydoctype.contains("xhtml")) {
				XHTMLclosed = " /";
				BODYmargins = " style=\"margin: 0px;\"";
			} else if (mydoctype.contains("html4")) {
				XHTMLclosed = "";
				BODYmargins = " style=\"margin: 0px;\"";
			} else {
				XHTMLclosed = "";
				BODYmargins = " marginwidth=\"0\" marginheight=\"0\" leftmargin=\"0\" topmargin=\"0\"";
			}
			if (Common.fileExists(Common.getRealPath(server, "/" + getContentClass() + ".original.html"))) {
				filecontent = Common.readFile(Common.getRealPath(server, "/" + getContentClass() + ".original.html"));
			} else {
				filecontent = Common.readFile(Common.getRealPath(server, "/" + "page" + ".original.html"));
			}
			if (getContentClass().equals("template")) {
				if (filecontent.contains("@@@body@@@")) filecontent = filecontent.replaceAll("@@@body@@@", "");
				if (filecontent.contains("@@@content@@@")) filecontent = filecontent.replaceAll("@@@content@@@", "");
			}
			if (filecontent.contains("@@@config:doctype@@@")) filecontent = filecontent.replaceAll("@@@config:doctype@@@", mydoctype.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (filecontent.contains("@@@config:charset@@@")) filecontent = filecontent.replaceAll("@@@config:charset@@@", myconfig.get(db, "charset").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (filecontent.contains("@@@title@@@")) filecontent = filecontent.replaceAll("@@@title@@@", header(db, myconfig, "title", "").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (filecontent.contains("@@@author@@@")) filecontent = filecontent.replaceAll("@@@author@@@", header(db, myconfig, "author", "").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (filecontent.contains("@@@description@@@")) filecontent = filecontent.replaceAll("@@@description@@@", header(db, myconfig, "description", "").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (filecontent.contains("@@@keywords@@@")) filecontent = filecontent.replaceAll("@@@keywords@@@", header(db, myconfig, "keywords", "").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (filecontent.contains("@@@metainfo@@@")) filecontent = filecontent.replaceAll("@@@metainfo@@@", header(db, myconfig, "metainfo", "").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (filecontent.contains("@@@htmlattributes@@@")) filecontent = filecontent.replaceAll("@@@htmlattributes@@@", OutputContent(text, server, request, response, session, null, header(db, myconfig, "htmlattributes", "")).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("@@@htmlattributes@@@", "@@@---htmlattributes---@@@")).replaceAll("@@@---htmlattributes---@@@", "@@@htmlattributes@@@");
			if (filecontent.contains("@@@htmlheader@@@")) filecontent = filecontent.replaceAll("@@@htmlheader@@@", OutputContent(text, server, request, response, session, null, header(db, myconfig, "htmlheader", "")).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("@@@htmlheader@@@", "@@@---htmlheader---@@@")).replaceAll("@@@---htmlheader---@@@", "@@@htmlheader@@@");
			if (filecontent.contains("@@@htmlbodyonload@@@")) filecontent = filecontent.replaceAll("@@@htmlbodyonload@@@", header(db, myconfig, "htmlbodyonload", "").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (filecontent.contains("@@@XHTMLclosed@@@")) filecontent = filecontent.replaceAll("@@@XHTMLclosed@@@", XHTMLclosed.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (filecontent.contains("@@@BODYmargins@@@")) filecontent = filecontent.replaceAll("@@@BODYmargins@@@", BODYmargins.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			String mystylesheets = "";
			HashMap<String,String> displayedstylesheet = new HashMap<String,String>();
			mystylesheets += outputStylesheetHtml(myconfig, db, displayedstylesheet);
			if (getTemplateContent() != null) {
				mystylesheets += getTemplateContent().outputStylesheetHtml(myconfig, db, displayedstylesheet);
			}
			if ((getTemplateContent() != null) && (getTemplateContent().getTemplateContent() != null)) {
				mystylesheets += getTemplateContent().getTemplateContent().outputStylesheetHtml(myconfig, db, displayedstylesheet);
			}
			String myscripts = "";
			HashMap<String,String> displayedscript = new HashMap<String,String>();
			myscripts += outputScriptHtml(myconfig, db, displayedscript);
			if (getTemplateContent() != null) {
				myscripts += getTemplateContent().outputScriptHtml(myconfig, db, displayedscript);
			}
			if ((getTemplateContent() != null) && (getTemplateContent().getTemplateContent() != null)) {
				myscripts += getTemplateContent().getTemplateContent().outputScriptHtml(myconfig, db, displayedscript);
			}
			if (filecontent.contains("@@@stylesheet@@@")) filecontent = filecontent.replaceAll("@@@stylesheet@@@", mystylesheets.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (filecontent.contains("@@@script@@@")) filecontent = filecontent.replaceAll("@@@script@@@", myscripts.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			if (filecontent.contains("@@@body@@@")) filecontent = filecontent.replaceAll("@@@body@@@", OutputContent(text, server, request, response, session, null, body).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$").replaceAll("@@@body@@@", "@@@---body---@@@")).replaceAll("@@@---body---@@@", "@@@body@@@");
		}
		return filecontent;
	}



	public String outputScriptHtml(Configuration myconfig, DB db, HashMap<String,String> displayedscript) {
		String output = "";
		if (! getHeatmap().equals("")) {
			if (false && myconfig.get(db, "hardcore_script_hrefs").equals("inline")) {
			} else if (false && myconfig.get(db, "hardcore_script_hrefs").equals("direct")) {
			} else {
				output += "<script type=\"text/javascript\" src=\"/heatmap.js.jsp?id=" + getId() + "\"></script>" + "\r\n";
			}
		}
		if (! getScripts().equals("")) {
			if (false && myconfig.get(db, "hardcore_script_hrefs").equals("inline")) {
			} else if (false && myconfig.get(db, "hardcore_script_hrefs").equals("direct")) {
			} else {
				String[] myids = getScripts().split(",");
				for (int i=0; i<myids.length; i++) {
					String myscriptid = "" + myids[i];
					if (displayedscript.get(myscriptid) == null) {
						displayedscript.put(myscriptid, myscriptid);
						if (myconfig.get(db, "doctype").contains("xhtml")) {
							output += "<script type=\"text/javascript\" src=\"/script.jsp?id=" + myscriptid + "\"></script>" + "\r\n";
						} else {
							output += "<script type=\"text/javascript\" src=\"/script.jsp?id=" + myscriptid + "\"></script>" + "\r\n";
						}
					}
				}
			}
		}
		if (getElementContent() != null) {
			HashMap<String,Content[]> element_contents = getElementContent();
			Iterator elementitems = element_contents.keySet().iterator();
			while (elementitems.hasNext()) {
				String elementitem = "" + elementitems.next();
				Content[] my_element_contents = (Content[])element_contents.get(elementitem);
				for (int j=0; j<my_element_contents.length; j++) {
					Content element_content = my_element_contents[j];
					if (! element_content.getScripts().equals("")) {
						if (false && myconfig.get(db, "hardcore_script_hrefs").equals("inline")) {
						} else if (false && myconfig.get(db, "hardcore_script_hrefs").equals("direct")) {
						} else {
							String[] myids = element_content.getScripts().split(",");
							for (int i=0; i<myids.length; i++) {
								String myscriptid = "" + myids[i];
								if (displayedscript.get(myscriptid) == null) {
									displayedscript.put(myscriptid, myscriptid);
									if (myconfig.get(db, "doctype").contains("xhtml")) {
										output += "<script type=\"text/javascript\" src=\"/script.jsp?id=" + myscriptid + "\"></script>" + "\r\n";
									} else {
										output += "<script type=\"text/javascript\" src=\"/script.jsp?id=" + myscriptid + "\"></script>" + "\r\n";
									}
								}
							}
						}
					}
				}
			}
		}
		return output;
	}



	public String outputStylesheetHtml(Configuration myconfig, DB db, HashMap<String,String> displayedstylesheet) {
		String output = "";
		if (getStyleSheetContents().size() > 0) {
			if (myconfig.get(db, "hardcore_stylesheet_hrefs").equals("inline")) {
				output += "<style>" + "\r\n";
				for (int i=0; i<getStyleSheetContents().size(); i++) {
					output += "" + ((Content)getStyleSheetContents().get(i)).header(db, myconfig, "content", "") + "\r\n";
				}
				output += "</style>";
			} else if (myconfig.get(db, "hardcore_stylesheet_hrefs").equals("direct")) {
				for (int i=0; i<getStyleSheetContents().size(); i++) {
					if (! ((Content)getStyleSheetContents().get(i)).getServerFilename().equals("")) {
						if (myconfig.get(db, "doctype").contains("xhtml")) {
							String mystylesheetid = "" + ((Content)getStyleSheetContents().get(i)).getServerFilename();
							if (displayedstylesheet.get(mystylesheetid) == null) {
								displayedstylesheet.put(mystylesheetid, mystylesheetid);
								output += "<link rel=\"stylesheet\" type=\"text/css\" href=\"/" + mystylesheetid + "\" />" + "\r\n";
							}
						} else {
							String mystylesheetid = "" + ((Content)getStyleSheetContents().get(i)).getServerFilename();
							if (displayedstylesheet.get(mystylesheetid) == null) {
								displayedstylesheet.put(mystylesheetid, mystylesheetid);
								output += "<link rel=\"stylesheet\" type=\"text/css\" href=\"/" + mystylesheetid + "\">" + "\r\n";
							}
						}
					} else if (! ((Content)getStyleSheetContents().get(i)).getId().equals("")) {
						if (myconfig.get(db, "doctype").contains("xhtml")) {
							String mystylesheetid = "" + ((Content)getStyleSheetContents().get(i)).getId();
							if (displayedstylesheet.get(mystylesheetid) == null) {
								displayedstylesheet.put(mystylesheetid, mystylesheetid);
								output += "<link rel=\"stylesheet\" type=\"text/css\" href=\"/stylesheet.jsp?id=" + mystylesheetid + "\" />" + "\r\n";
							}
						} else {
							String mystylesheetid = "" + ((Content)getStyleSheetContents().get(i)).getId();
							if (displayedstylesheet.get(mystylesheetid) == null) {
								displayedstylesheet.put(mystylesheetid, mystylesheetid);
								output += "<link rel=\"stylesheet\" type=\"text/css\" href=\"/stylesheet.jsp?id=" + mystylesheetid + "\">" + "\r\n";
							}
						}
					}
				}
			} else {
				for (int i=0; i<getStyleSheetContents().size(); i++) {
					if (! ((Content)getStyleSheetContents().get(i)).getId().equals("")) {
						if (myconfig.get(db, "doctype").contains("xhtml")) {
							String mystylesheetid = "" + ((Content)getStyleSheetContents().get(i)).getId();
							if (displayedstylesheet.get(mystylesheetid) == null) {
								displayedstylesheet.put(mystylesheetid, mystylesheetid);
								output += "<link rel=\"stylesheet\" type=\"text/css\" href=\"/stylesheet.jsp?id=" + mystylesheetid + "\" />" + "\r\n";
							}
						} else {
							String mystylesheetid = "" + ((Content)getStyleSheetContents().get(i)).getId();
							if (displayedstylesheet.get(mystylesheetid) == null) {
								displayedstylesheet.put(mystylesheetid, mystylesheetid);
								output += "<link rel=\"stylesheet\" type=\"text/css\" href=\"/stylesheet.jsp?id=" + mystylesheetid + "\">" + "\r\n";
							}
						}
					}
				}
			}
		}
		return output;
	}



	public String outputStylesheetIds(Configuration myconfig, DB db, HashMap<String,String> displayedstylesheet) {
		String output = "";
		if (! getStyleSheet().equals("")) {
			String[] mystylesheetids = getStyleSheet().split(",");
			for (int i=0; i<mystylesheetids.length; i++) {
				String mystylesheetid = "" + mystylesheetids[i];
				if (! mystylesheetid.equals("")) {
					if (displayedstylesheet.get("" + mystylesheetid) == null) {
						displayedstylesheet.put("" + mystylesheetid, "" + mystylesheetid);
						if (! output.equals("")) output += ",";
						output += mystylesheetid;
					}
				}
			}
		}
		if (getStyleSheetContents().size() > 0) {
			for (int i=0; i<getStyleSheetContents().size(); i++) {
				String mystylesheetid = "" + ((Content)getStyleSheetContents().get(i)).getId();
				if (! mystylesheetid.equals("")) {
					if (displayedstylesheet.get("" + mystylesheetid) == null) {
						displayedstylesheet.put("" + mystylesheetid, "" + mystylesheetid);
						if (! output.equals("")) output += ",";
						output += mystylesheetid;
					}
				}
			}
		}
		return output;
	}



	public void unpublishStaticFile(ServletContext myserver, Request request, Response response, Session session, JspWriter out, Configuration myconfig, DB db) {
		unpublishStaticFile(myserver, request.getRequest(), response.getResponse(), session.getSession(), out, myconfig, db);
	}
	public void unpublishStaticFile(ServletContext myserver, HttpServletRequest request, HttpServletResponse response, HttpSession session, JspWriter out, Configuration myconfig, DB db) {
		if (! getPublished().equals("")) {
			if (myconfig.get(db, "use_static_content").equals("yes")) {
				if (License.valid(db, myconfig, "enterprise")) {
					if (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.html?$").matcher(getServerFilename()).find()) {
						Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
					} else if (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.css$").matcher(getServerFilename()).find()) {
						Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
					} else if (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.js$").matcher(getServerFilename()).find()) {
						Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
					} else if (! getServerFilename().equals("")) {
						if (Common.fileExists(Common.getRealPath(myserver, myconfig.get(db, "URLrootpath") + getServerFilename() + "/index.jsp"))) {
							Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "/index.jsp" + "\"");
						} else {
							Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
						}
					}
				}
			} else {
				if (! getServerFilename().equals("")) {
					if (License.valid(db, myconfig, "enterprise")) {
						if (Common.fileExists(Common.getRealPath(myserver, myconfig.get(db, "URLrootpath") + getServerFilename() + "/index.jsp"))) {
							Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "/index.jsp" + "\"");
						} else {
							Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
						}
					}
				}
			}
		}
	}



	public void deleteStaticFile(ServletContext server, Request request, Response response, Session session, JspWriter out, Configuration myconfig, DB db) throws Exception {
		deleteStaticFile(server, request.getRequest(), response.getResponse(), session.getSession(), out, myconfig, db);
	}
	public void deleteStaticFile(ServletContext server, HttpServletRequest request, HttpServletResponse response, HttpSession session, JspWriter out, Configuration myconfig, DB db) throws Exception {
		if (myconfig.get(db, "use_static_content").equals("yes")) {
			if (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.html?$").matcher(getServerFilename()).find()) {
				if (! getPublished().equals("")) {
					if (License.valid(db, myconfig, "enterprise")) {
						Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
					}
				}
				Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()));
				Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename() + "/index.jsp"));
				Common.deleteFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()), false);
			} else if (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.css$").matcher(getServerFilename()).find()) {
				if (! getPublished().equals("")) {
					if (License.valid(db, myconfig, "enterprise")) {
						Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
					}
				}
				Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()));
				Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename() + "/index.jsp"));
				Common.deleteFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()), false);
			} else if (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.js$").matcher(getServerFilename()).find()) {
				if (! getPublished().equals("")) {
					if (License.valid(db, myconfig, "enterprise")) {
						Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
					}
				}
				Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()));
				Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename() + "/index.jsp"));
				Common.deleteFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()), false);
			} else if (! getServerFilename().equals("")) {
				if (! getPublished().equals("")) {
					if (License.valid(db, myconfig, "enterprise")) {
						if (Common.fileExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename() + "/index.jsp"))) {
							Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "/index.jsp" + "\"");
						} else {
							Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
						}
					}
				}
				Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()));
				Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename() + "/index.jsp"));
				Common.deleteFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()), false);
				Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/delete"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:deleteStaticFile1\"", "", server, session, request, response);
			}
		} else {
			if (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.html?$").matcher(getServerFilename()).find()) {
				if (! getPublished().equals("")) {
					if (License.valid(db, myconfig, "enterprise")) {
						if (Common.fileExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename() + "/index.jsp"))) {
							Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "/index.jsp" + "\"");
						} else {
							Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
						}
					}
				}
				Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()));
				Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename() + "/index.jsp"));
				Common.deleteFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()), false);
			} else if (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.css$").matcher(getServerFilename()).find()) {
				if (! getPublished().equals("")) {
					if (License.valid(db, myconfig, "enterprise")) {
						if (Common.fileExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename() + "/index.jsp"))) {
							Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "/index.jsp" + "\"");
						} else {
							Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
						}
					}
				}
				Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()));
				Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename() + "/index.jsp"));
				Common.deleteFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()), false);
			} else if (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.js$").matcher(getServerFilename()).find()) {
				if (! getPublished().equals("")) {
					if (License.valid(db, myconfig, "enterprise")) {
						if (Common.fileExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename() + "/index.jsp"))) {
							Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "/index.jsp" + "\"");
						} else {
							Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
						}
					}
				}
				Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()));
				Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename() + "/index.jsp"));
				Common.deleteFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()), false);
			} else if (! getServerFilename().equals("")) {
				if (! getPublished().equals("")) {
					if (License.valid(db, myconfig, "enterprise")) {
						if (Common.fileExists(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename() + "/index.jsp"))) {
							Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "/index.jsp" + "\"");
						} else {
							Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"");
						}
					}
				}
				Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()));
				Common.deleteFile(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename() + "/index.jsp"));
				Common.deleteFolder(Common.getRealPath(server, myconfig.get(db, "URLrootpath") + getServerFilename()), false);
				Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/delete"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:deleteStaticFile2\"", "", server, session, request, response);
			}
		}
	}



//QQQ ????? public void publishScheduledServerFilename(ServletContext myserver, Request myrequest, Response myresponse, Session mysession, Configuration myconfig, DB db) throws Exception {

      	public void publishServerFilename(ServletContext myserver, Request myrequest, Response myresponse, Session mysession, Configuration myconfig, DB db) throws Exception {
//		if (((getContentClass().equals("image")) || (getContentClass().equals("file"))) && (myconfig.get(db, "use_static_filenames").equals("yes"))) {
//		if (((getContentClass().equals("image")) || (getContentClass().equals("file")))) {
		if (true) {
			Page publishedpage = new Page(text);
			publishedpage.public_read(myserver, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, getId(), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			if ((! getPublished().equals("")) && (getUpdated().compareTo(getPublished()) <= 0)) {
				// page has been published - publish file
				if (publishedpage.getServerFilename().equals("")) {
					// no old published file - keep new published file (if any)
				} else if (getServerFilename().equals("")) {
					// no new published file - delete old published file
					if (License.valid(db, myconfig, "enterprise")) {
						if (Common.fileExists(Common.getRealPath(myserver, myconfig.get(db, "URLrootpath") + publishedpage.getServerFilename() + "/index.jsp"))) {
							Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + publishedpage.getServerFilename() + "/index.jsp" + "\"");
						} else {
							Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + publishedpage.getServerFilename() + "\"");
						}
					}
					Common.deleteFile(Common.getRealPath(myserver, myconfig.get(db, "URLrootpath") + publishedpage.getServerFilename()));
					Common.deleteFolder(Common.getRealPath(myserver, myconfig.get(db, "URLrootpath") + publishedpage.getServerFilename()), false);
					Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/delete"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + publishedpage.getServerFilename() + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:publishServerFilename1\"", "", myserver, mysession, myrequest, myresponse);
				} else if (publishedpage.getServerFilename().equals(getServerFilename())) {
					// no new published file - keep old published file
				} else {
					// delete old published file
					if (License.valid(db, myconfig, "enterprise")) {
						if (Common.fileExists(Common.getRealPath(myserver, myconfig.get(db, "URLrootpath") + publishedpage.getServerFilename() + "/index.jsp"))) {
							Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + publishedpage.getServerFilename() + "/index.jsp" + "\"");
						} else {
							Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/unpublished"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + publishedpage.getServerFilename() + "\"");
						}
					}
					Common.deleteFile(Common.getRealPath(myserver, myconfig.get(db, "URLrootpath") + publishedpage.getServerFilename()));
					Common.deleteFolder(Common.getRealPath(myserver, myconfig.get(db, "URLrootpath") + publishedpage.getServerFilename()), false);
					Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/delete"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + publishedpage.getServerFilename() + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:publishServerFilename2\"", "", myserver, mysession, myrequest, myresponse);
					// check if new published file's server filename is ~same as old published file's server filename
					if (getServerFilename().replaceAll("^(.*)(_[0-9]+)(\\..+?)$", "$1$3").equals(publishedpage.getServerFilename())) {
						// replace old published file with new published file
						Common.moveFile(Common.getRealPath(myserver, myconfig.get(db, "URLrootpath") + getServerFilename()), Common.getRealPath(myserver, myconfig.get(db, "URLrootpath") + publishedpage.getServerFilename()));
						Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/move"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + getServerFilename() + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + publishedpage.getServerFilename() + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:publishServerFilename3\"", "", myserver, mysession, myrequest, myresponse);
						// use old published file's server filename
						if (! getServerFilename().equals(publishedpage.getServerFilename())) {
							setServerFilename(publishedpage.getServerFilename());
							update(db, myconfig, getId(), "content", "id");
						}
					}
				}
			}
		}
	}



      	public void unpublishServerFilename(ServletContext myserver, Request myrequest, Response myresponse, Session mysession, Configuration myconfig, DB db, String published_server_filename) throws Exception {
		if ((! getServerFilename().equals("")) && (! published_server_filename.equals(""))) {
			String old_server_filename = getServerFilename();
			String old_server_filename_base = "" + old_server_filename;
			if (old_server_filename.lastIndexOf(".") >= 0) {
				old_server_filename_base = old_server_filename.substring(0, old_server_filename.lastIndexOf("."));
			}
			String old_server_filename_ext = "";
			if (old_server_filename.lastIndexOf(".") >= 0) {
				old_server_filename_ext = old_server_filename.substring(old_server_filename.lastIndexOf("."));
			}
			String published_server_filename_base = "" + published_server_filename;
			if (published_server_filename.lastIndexOf(".") >= 0) {
				published_server_filename_base = published_server_filename.substring(0, published_server_filename.lastIndexOf("."));
			}
			String published_server_filename_ext = "";
			if (published_server_filename.lastIndexOf(".") >= 0) {
				published_server_filename_ext = published_server_filename.substring(published_server_filename.lastIndexOf("."));
			}
			if (old_server_filename_base.startsWith(published_server_filename_base)) {
				String old_server_filename_rev = "" + old_server_filename_base.substring(published_server_filename_base.length());
				if (old_server_filename_rev.matches("^_[0-9]+$")) {
					Common.moveFile(Common.getRealPath(myserver, myconfig.get(db, "URLrootpath") + old_server_filename), Common.getRealPath(myserver, myconfig.get(db, "URLrootpath") + published_server_filename));
					setServerFilename(published_server_filename);
					update(db, myconfig, getId(), "content", "id");
					Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/move"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + old_server_filename + "\"" + " " + "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + published_server_filename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:unpublishServerFilename1\"", "", myserver, mysession, myrequest, myresponse);
				}
			}
		}
	}



	public void handleStaticAddressLinks(ServletContext myserver, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String old_server_filename, String new_server_filename) throws Exception {
		if ((getContentClass().equals("page")) || (getContentClass().equals("product"))) {
			Page linkedpage = new Page(text);
			String SQL = "";
			String SQLwhere = "";
			if ((! new_server_filename.equals("")) && (old_server_filename.equals(""))) {
				SQLwhere = Common.SQLwhere_like_or_or_or(db, "", "content", "%href=\"/" + getContentClass() +".asp?id=" + getId() + "%", "content", "%href=\"/" + getContentClass() +".aspx?id=" + getId() + "%", "content", "%href=\"/" + getContentClass() +".jsp?id=" + getId() + "%", "content", "%href=\"/" + getContentClass() +".php?id=" + getId() + "%");
				SQL = "select * from content" + SQLwhere;
				linkedpage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
				while (linkedpage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
					linkedpage.preview_read(myserver, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, linkedpage.getId(), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
	 				linkedpage.setContent(linkedpage.getContent().replaceAll("(href=\"?)/" + getContentClass() +"\\.(asp|aspx|jsp|php)\\?id=" + getId() + "([^0-9])", "$1" + myconfig.get(db, "URLrootpath") + new_server_filename + "$3"));
					linkedpage.update(db, myconfig, linkedpage.getId(), "content", "id");
				}
				if ((! getPublished().equals("")) && (getUpdated().compareTo(getPublished()) <= 0)) {
					SQL = "select * from content_public" + SQLwhere;
					linkedpage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
					while (linkedpage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
						linkedpage.public_read(myserver, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, linkedpage.getId(), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		 				linkedpage.setContent(linkedpage.getContent().replaceAll("(href=\"?)/" + getContentClass() +"\\.(asp|aspx|jsp|php)\\?id=" + getId() + "([^0-9])", "$1" + myconfig.get(db, "URLrootpath") + new_server_filename + "$3"));
						linkedpage.update(db, myconfig, linkedpage.getId(), "content_public", "id");
					}
				}
				SQLwhere = Common.SQLwhere_like_or_or_or(db, "", "content", "%href=/" + getContentClass() +".asp?id=" + getId() + "%", "content", "%href=/" + getContentClass() +".aspx?id=" + getId() + "%", "content", "%href=/" + getContentClass() +".jsp?id=" + getId() + "%", "content", "%href=/" + getContentClass() +".php?id=" + getId() + "%");
				SQL = "select * from content" + SQLwhere;
				linkedpage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
				while (linkedpage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
					linkedpage.preview_read(myserver, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, linkedpage.getId(), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
					linkedpage.setContent(linkedpage.getContent().replaceAll("(href=\"?)/" + getContentClass() +"\\.(asp|aspx|jsp|php)\\?id=" + getId() + "([^0-9])", "$1" + myconfig.get(db, "URLrootpath") + new_server_filename + "$3"));
					linkedpage.update(db, myconfig, linkedpage.getId(), "content", "id");
				}
				if ((! getPublished().equals("")) && (getUpdated().compareTo(getPublished()) <= 0)) {
					SQL = "select * from content_public" + SQLwhere;
					linkedpage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
					while (linkedpage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
						linkedpage.public_read(myserver, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, linkedpage.getId(), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
						linkedpage.setContent(linkedpage.getContent().replaceAll("(href=\"?)/" + getContentClass() +"\\.(asp|aspx|jsp|php)\\?id=" + getId() + "([^0-9])", "$1" + myconfig.get(db, "URLrootpath") + new_server_filename + "$3"));
						linkedpage.update(db, myconfig, linkedpage.getId(), "content_public", "id");
					}
				}
			}
			if ((! new_server_filename.equals(old_server_filename)) && (! old_server_filename.equals(""))) {
				SQLwhere = Common.SQLwhere_like_or(db, "", "content", "%href=" + myconfig.get(db, "URLrootpath") + old_server_filename + "%", "content", "%href=\"" + myconfig.get(db, "URLrootpath") + old_server_filename + "%");
				SQL = "select * from content" + SQLwhere;
				linkedpage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
				while (linkedpage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
					linkedpage.preview_read(myserver, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, linkedpage.getId(), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
					if (new_server_filename.equals("")) {
						linkedpage.setContent(linkedpage.getContent().replaceAll("(href=\"?)" + myconfig.get(db, "URLrootpath") + old_server_filename + "( |\"|[>]|\\s|$)", "$1" + "/" + getContentClass() +".jsp?id=" + getId() + "$2"));
					} else {
						linkedpage.setContent(linkedpage.getContent().replaceAll("(href=\"?)" + myconfig.get(db, "URLrootpath") + old_server_filename + "( |\"|[>]|\\s|$)", "$1" + myconfig.get(db, "URLrootpath") + new_server_filename + "$2"));
					}
					linkedpage.update(db, myconfig, linkedpage.getId(), "content", "id");
					if ((! getPublished().equals("")) && (getUpdated().compareTo(getPublished()) <= 0)) {
						if ((! linkedpage.getPublished().equals("")) && (linkedpage.getUpdated().compareTo(linkedpage.getPublished()) <= 0)) {
							String linkedpage_content = linkedpage.getContent();
							linkedpage.public_read(myserver, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, linkedpage.getId(), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
							linkedpage.setContent(linkedpage_content);
							linkedpage.update(db, myconfig, linkedpage.getId(), "content_public", "id");
						}
					}
				}
				if ((! getPublished().equals("")) && (getUpdated().compareTo(getPublished()) <= 0)) {
					SQL = "select * from content_public" + SQLwhere;
					linkedpage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
					while (linkedpage.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
						linkedpage.public_read(myserver, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, linkedpage.getId(), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
						if (new_server_filename.equals("")) {
							linkedpage.setContent(linkedpage.getContent().replaceAll("(href=\"?)" + myconfig.get(db, "URLrootpath") + old_server_filename + "( |\"|[>]|\\s|$)", "$1" + "/" + getContentClass() +".jsp?id=" + getId() + "$2"));
						} else {
							linkedpage.setContent(linkedpage.getContent().replaceAll("(href=\"?)" + myconfig.get(db, "URLrootpath") + old_server_filename + "( |\"|[>]|\\s|$)", "$1" + myconfig.get(db, "URLrootpath") + new_server_filename + "$2"));
						}
						linkedpage.update(db, myconfig, linkedpage.getId(), "content_public", "id");
					}
				}
			}
		}
	}



	public static String parse_output_replace_params_escape(String value) {
		String result = value;
		result = result.replaceAll("\u000b", "");
		result = result.replaceAll("@", "\u000bAt\u000b");
		result = result.replaceAll("\\\\", "\u000bYen\u000b");
		result = result.replaceAll("\\^", "\u000bHat\u000b");
		result = result.replaceAll("\\$", "\u000bDlr\u000b");
		result = result.replaceAll("\\*", "\u000bAst\u000b");
		result = result.replaceAll("\\?", "\u000bQst\u000b");
		result = result.replaceAll("\\|", "\u000bPip\u000b");
		result = result.replaceAll("\\(", "\u000bLsp\u000b");
		result = result.replaceAll("\\)", "\u000bRsp\u000b");
		result = result.replaceAll("\\[", "\u000bLmp\u000b");
		result = result.replaceAll("\\]", "\u000bRmp\u000b");
		result = result.replaceAll("\\{", "\u000bLlp\u000b");
		result = result.replaceAll("\\}", "\u000bMlp\u000b");
		result = result.replaceAll("=", "\u000bEq\u000b");
		result = result.replaceAll("!", "\u000bExc\u000b");
		return result;
	}



	public static String parse_output_replace_params_unescape(String value) {
		if (! value.contains("\u000b")) return value;
		String result = value;
		result = result.replaceAll("\u000bAt\u000b", "@");
		result = result.replaceAll("\u000bYen\u000b", "\\\\");
		result = result.replaceAll("\u000bHat\u000b", "\\^");
		result = result.replaceAll("\u000bDlr\u000b", "\\$");
		result = result.replaceAll("\u000bAst\u000b", "\\*");
		result = result.replaceAll("\u000bQst\u000b", "\\?");
		result = result.replaceAll("\u000bPip\u000b", "\\|");
		result = result.replaceAll("\u000bLsp\u000b", "\\(");
		result = result.replaceAll("\u000bRsp\u000b", "\\)");
		result = result.replaceAll("\u000bLmp\u000b", "\\[");
		result = result.replaceAll("\u000bRmp\u000b", "\\]");
		result = result.replaceAll("\u000bLlp\u000b", "\\{");
		result = result.replaceAll("\u000bMlp\u000b" , "\\}");
		result = result.replaceAll("\u000bEq\u000b", "=");
		result = result.replaceAll("\u000bExc\u000b", "!");
		return result;
	}



	public static String OutputContent(ServletContext server, HttpServletRequest request, HttpServletResponse response, HttpSession session, JspWriter out, String content) {
		return OutputContent(new Text(), server, request, response, session, out, content);
	}
	public static String OutputContent(Text text, ServletContext server, HttpServletRequest request, HttpServletResponse response, HttpSession session, JspWriter out, String content) {
		if ((! content.contains("@@@")) && (! content.contains("###"))) {
			content = parse_output_replace_params_unescape(content);
			try {
				if (out != null) out.print(content);
				if (out != null) out.flush();
			} catch (Exception e) {
			}
			return content;
		}
		String output = "";
		StringBuilder myoutput = new StringBuilder();

		Object save_id = "";
		Object save_mode = "";
		Object save_version = "";
		Object save_stylesheet = "";
		Object save_template = "";
		Object save_preview = "";

		try {
			save_id = session.getAttribute("id");
			save_mode = session.getAttribute("mode");
			save_version = session.getAttribute("version");
			save_stylesheet = session.getAttribute("stylesheet");
			save_template = session.getAttribute("template");
			save_preview = session.getAttribute("preview_scheduled");
		} catch (Exception e) {
		}

		if (content.contains("@@@extension:")){
			Pattern p = Pattern.compile("@@@extension:(.+?)(\\(|%28)(.*?)(\\)|%29)@@@");
			Matcher m = p.matcher(content);
			while (m.find()) {
				String extensionMatch = "" + m.group(0);
				String extensionName = "" + m.group(1);
				try {
					session.setAttribute("extension", "" + m.group(3));
					request.setAttribute("extension", "" + m.group(3));
				} catch (Exception e) {
				}
				String[] extensionParts = content.split(extensionMatch.replaceAll("([-+/\\*\\.\\(\\)\\|\\[\\]\\^\\$\\\\\\?\\{\\}])", "\\\\$1"),2);
				if (extensionParts.length > 0) {
					try {
						myoutput.append(extensionParts[0].replaceAll("@@@[-_ a-zA-Z0-9\\w]+@@@", ""));
					} catch (Exception e) {
					}
				}
				try {
					myoutput.append(Common.execute("/" + text.display("adminpath") + "/extension/" + extensionName + ".jsp", null, null, null, server, session, request, response));
				} catch (Exception e) {
				}
				if (extensionParts.length > 1) {
					content = "" + extensionParts[1];
				} else {
					content = "";
				}
				m.reset(content);
				try {
					session.setAttribute("id", save_id);
					session.setAttribute("mode", save_mode);
					session.setAttribute("version", save_version);
					session.setAttribute("stylesheet", save_stylesheet);
					session.setAttribute("template", save_template);
					session.setAttribute("preview_scheduled", save_preview);
				} catch (Exception e) {
				}
			}
		}
		try {
			session.setAttribute("extension", "");
			request.removeAttribute("extension");
		} catch (Exception e) {
		}
		content = content.replaceAll("@@@[-_ a-zA-Z0-9\\w]+@@@", "");
		myoutput.append(content);
		output = myoutput.toString();
		try {
			output = output.replaceAll("(<|&lt;)nobr(>|&gt;)(@@@|###)(<|&lt;)/nobr(>|&gt;)", "$3");
			output = parse_output_replace_params_unescape(output);
		} catch (Exception e) {
		}

		try {
			session.setAttribute("id", save_id);
			session.setAttribute("mode", save_mode);
			session.setAttribute("version", save_version);
			session.setAttribute("stylesheet", save_stylesheet);
			session.setAttribute("template", save_template);
			session.setAttribute("preview_scheduled", save_preview);
		} catch (Exception e) {
		}

		// expand relative parameters only links to absolute links for the current page
		output = output.replaceAll("href=\"\\?", "href=\"" + request.getRequestURI() + "?");

		try {
			if (out != null) out.print(output);
			if (out != null) out.flush();
		} catch (Exception e) {
		}
		return output;
	}

}
