package HardCore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.*;
import javax.servlet.*;
import javax.servlet.jsp.*;
import org.apache.log4j.*;

public class Cms {
	private Text text = new Text();
	private ServletContext myserver;
	private Session mysession;
	private Request myrequest;
	private Response myresponse;
	private Configuration myconfig;
	private Website mywebsite;
	private DB db;
	private DB logdb;
	private String myroot;
	private String URLrootpath;
	private String csURLrootpath;
	private String image_output_mode;
	private String file_output_mode;
	private PageContext mypagecontext;
	private JspWriter myout;

	private	UCbrowseWebsite browseWebsite = null;
	private HashMap<String,Content> _CMScontent = new HashMap<String,Content>();
	private HashMap<String,Page> _CMSpage = new HashMap<String,Page>();
	private HashMap<String,Content> _CMSstyle = new HashMap<String,Content>();
	private HashMap<String,Content> _CMSscript = new HashMap<String,Content>();
	private HashMap<String,Data> _CMSdata = new HashMap<String,Data>();
	private HashMap<String,Content> _CMSimage = new HashMap<String,Content>();
	private HashMap<String,Content> _CMSfile = new HashMap<String,Content>();
	private HashMap<String,Content> _CMSlink = new HashMap<String,Content>();

	public static String _XHTMLclosed = null;
	public static String _BODYmargins = null;
	public String XHTMLclosed = "";
	public String BODYmargins = "";
	public String id = "";

        public Object DEBUGscript = new Object();
	public String DEBUGdatetime = "";
        public static boolean DEBUG = false;

	private static Logger auditlog = Logger.getLogger("WCMaudit");
	private static Logger debuglog = Logger.getLogger("WCMdebug");



	public Cms(ServletContext servletcontext, Session session, Request request, Response response, Configuration config, DB mydb, DB mylogdb) {
		init(servletcontext, session, request, response, config, mydb, mylogdb, null, null, null, null);
//		mysession.set("id", "");
	}
	public Cms(Text mytext, ServletContext servletcontext, Session session, Request request, Response response, Configuration config, DB mydb, DB mylogdb) {
		if (mytext != null) text = mytext;
		init(servletcontext, session, request, response, config, mydb, mylogdb, null, null, null, null);
//		mysession.set("id", "");
	}
	public Cms(Object myscript, Text mytext, ServletContext servletcontext, Session session, Request request, Response response, Configuration config, DB mydb, DB mylogdb) {
		DEBUGscript = myscript;
		if (mytext != null) text = mytext;
		init(servletcontext, session, request, response, config, mydb, mylogdb, null, null, null, null);
//		mysession.set("id", "");
	}
	public Cms(Object myscript, Text mytext, ServletContext servletcontext, Session session, Request request, Response response, Configuration config, DB mydb, DB mylogdb, Website website) {
		DEBUGscript = myscript;
		if (mytext != null) text = mytext;
		init(servletcontext, session, request, response, config, mydb, mylogdb, website, null, null, null);
//		mysession.set("id", "");
	}
	public Cms(Object myscript, Text mytext, ServletContext servletcontext, Session session, Request request, Response response, Configuration config, DB mydb, DB mylogdb, Website website, String DOCUMENT_ROOT, PageContext pagecontext, JspWriter out) {
		DEBUGscript = myscript;
		if (mytext != null) text = mytext;
		init(servletcontext, session, request, response, config, mydb, mylogdb, website, DOCUMENT_ROOT, pagecontext, out);
//		mysession.set("id", "");
	}



	public void init(ServletContext servletcontext, Session session, Request request, Response response, Configuration config, DB mydb, DB mylogdb, Website website) {
		init(servletcontext, session, request, response, config, mydb, mylogdb, website, null, null, null);
	}
	public void init(ServletContext servletcontext, Session session, Request request, Response response, Configuration config, DB mydb, DB mylogdb, Website website, String DOCUMENT_ROOT, PageContext pagecontext, JspWriter out) {
		this.myserver = servletcontext;
		this.mysession = session;
		this.myrequest = request;
		this.myresponse = response;
		this.myconfig = config;
		this.mywebsite = website;
		this.db = mydb;
		this.logdb = mylogdb;
		this.myroot = DOCUMENT_ROOT;
		this.myout = out;
		this.mypagecontext = pagecontext;
		this.browseWebsite = new UCbrowseWebsite(text);
		this._CMScontent = new HashMap<String,Content>();
		this._CMSpage = new HashMap<String,Page>();
		this._CMSstyle = new HashMap<String,Content>();
		this._CMSscript = new HashMap<String,Content>();
		this._CMSdata = new HashMap<String,Data>();
		this._CMSimage = new HashMap<String,Content>();
		this._CMSfile = new HashMap<String,Content>();
		this._CMSlink = new HashMap<String,Content>();
		initDOCTYPE(myconfig.get(db, "doctype"));
	}



	private void initDOCTYPE(String doctype) {
		if (doctype.indexOf("xhtml") > -1) {
			this.XHTMLclosed = " /";
			this.BODYmargins = " style=\"margin: 0px;\"";
		} else if (doctype.indexOf("html4") > -1) {
			this.XHTMLclosed = "";
			this.BODYmargins = " style=\"margin: 0px;\"";
		} else {
			this.XHTMLclosed = "";
			this.BODYmargins = " marginwidth=\"0\" marginheight=\"0\" leftmargin=\"0\" topmargin=\"0\"";
		}
		if (this._XHTMLclosed != null) {
			this.XHTMLclosed = this._XHTMLclosed;
		}
		if (this._BODYmargins != null) {
			this.BODYmargins = this._BODYmargins;
		}
	}



	public Data Data(String database, String id) throws Exception {
		if (_CMSdata.get(id) == null) {
			_CMSdata.put(id, browseWebsite.getDataData(database, id, mysession, myrequest, myresponse, myconfig, db, mywebsite));
		}
		return (Data)_CMSdata.get(id);
	}



	public Content Content(String id) throws Exception {
		ReadContent(id);
		return (Content)_CMScontent.get(id);
	}



	public Page Page(String id) throws Exception {
		ReadPage(id);
		return (Page)_CMSpage.get(id);
	}



	public Page Product(String id) throws Exception {
		ReadProduct(id);
		return (Page)_CMSpage.get(id);
	}



	public Content StyleSheet(String id) throws Exception {
		ReadStyleSheet(id);
		return (Content)_CMSstyle.get(id);
	}



	public Content Script(String id) throws Exception {
		ReadScript(id);
		return (Content)_CMSscript.get(id);
	}



	public Content Image(String id) throws Exception {
		ReadImage(id);
		return (Content)_CMSimage.get(id);
	}



	public Content File(String id) throws Exception {
		ReadFile(id);
		return (Content)_CMSfile.get(id);
	}



	public Content Link(String id) throws Exception {
		ReadLink(id);
		return (Content)_CMSlink.get(id);
	}



	private void _Experience(String contentclass, String database) {
		if ((! myconfig.get(db, "experience_license").equals("")) && (! myconfig.get(db, "use_experience").equals("no"))) {
			if (mysession.get("sessionid").equals("")) {
				if (! myconfig.get(db, "use_usersegments").equals("no")) {
					Segment mysegment = new Segment(mysession, db, myrequest, id, contentclass, database);
				}
				if (! myconfig.get(db, "use_usertests").equals("no")) {
					Usertest myusertest = new Usertest(mysession, db);
				}
			} else {
				if (! mysession.exists("usersegments")) {
					if (! myconfig.get(db, "use_usersegments").equals("no")) {
						Segment mysegment = new Segment(mysession, db, myrequest, id, contentclass, database);
					}
				}
				if ((! mysession.exists("usertests")) && (! mysession.exists("userteststags"))) {
					if (! myconfig.get(db, "use_usertests").equals("no")) {
						Usertest myusertest = new Usertest(mysession, db);
					}
				}
			}
		}
	}



	public void ReadData(String database, String id) throws Exception {
		_Experience("data", database);
		if (_CMSpage.get(id) == null) {
			_CMSpage.put(id, browseWebsite.getData(database, id, myserver, mysession, myrequest, myresponse, myconfig, db, mywebsite));
		}
		mysession.set("id", ((Page)_CMSpage.get(id)).getId());
	}



	public void ReadContent(String id) throws Exception {
		ReadContent(id, true);
	}
	public void ReadContent(String id, boolean doexperience) throws Exception {
		_Experience("page", "");
		mysession.set("id", html.encodeHtmlEntities(id));
		if (_CMScontent.get(id) == null) {
			_CMScontent.put(id, browseWebsite.getContentById(id, mysession, myrequest, myresponse, myconfig, db, mywebsite, doexperience));
		}
		mysession.set("id", ((Content)_CMScontent.get(id)).getId());
	}



	public String DisplayContent(String id, String attribute) throws Exception {
		ReadContent(id);
		if (_CMScontent.get(id) == null) return "";
		return ((Content)_CMScontent.get(id)).display(attribute, true, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"));
	}



	public String GetContent(String id, String attribute) throws Exception {
		return GetContent(id, attribute, true);
	}
	public String GetContent(String id, String attribute, boolean doexperience) throws Exception {
		ReadContent(id, doexperience);
		if (_CMScontent.get(id) == null) return "";
		return ((Content)_CMScontent.get(id)).display(attribute, false, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"));
	}



	public String ContentHeader(String id, String attribute) throws Exception {
		ReadContent(id);
		if (_CMScontent.get(id) == null) return "";
		return ((Content)_CMScontent.get(id)).header(db, myconfig, attribute, "");
	}



	public void ReadPersonal() throws Exception {
		mysession.set("id", html.encodeHtmlEntities(myrequest.getQueryString()));
		if (_CMSpage.get(myrequest.getQueryString()) == null) {
			_CMSpage.put(myrequest.getQueryString(), browseWebsite.getPersonal(myserver, mysession, myrequest, myresponse, myconfig, db, mywebsite));
		}
		mysession.set("id", ((Page)_CMSpage.get(myrequest.getQueryString())).getId());
		mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_doctype");
	}



	public void ReadPage(String id) throws Exception {
		_Experience("page", "");
		mysession.set("id", html.encodeHtmlEntities(id));
		if (_CMSpage.get(id) == null) {
			_CMSpage.put(id, browseWebsite.getPageById(id, myserver, mysession, myrequest, myresponse, myconfig, db, mywebsite));
		}
		mysession.set("id", ((Page)_CMSpage.get(id)).getId());
	}



	public String DisplayPage(String id, String attribute, String attribute2) throws Exception {
		ReadPage(id);
		if (_CMSpage.get(id) == null) return "";
		return ((Page)_CMSpage.get(id)).display(attribute, attribute2, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"));
	}



	public String PageDOCTYPE(String id) throws Exception {
		String mydoctype = "";
		ReadPage(id);
		if (_CMSpage.get(id) != null) {
			mydoctype = ((Page)_CMSpage.get(id)).header(db, myconfig, "doctype", "").trim();
		}
		if (mydoctype.equals("")) {
			mydoctype = mywebsite.getDefaultDocType();
		}
		if (mydoctype.equals("")) {
			mydoctype = myconfig.get(db, "doctype");
		}
		initDOCTYPE(mydoctype);
		return mydoctype;
	}



	public String ProductDOCTYPE(String id) throws Exception {
		String mydoctype = "";
		ReadProduct(id);
		if (_CMSpage.get(id) != null) {
			mydoctype = ((Page)_CMSpage.get(id)).header(db, myconfig, "doctype", "").trim();
		}
		if (mydoctype.equals("")) {
			mydoctype = mywebsite.getDefaultDocType();
		}
		if (mydoctype.equals("")) {
			mydoctype = myconfig.get(db, "doctype");
		}
		initDOCTYPE(mydoctype);
		return mydoctype;
	}



	public String DataDOCTYPE(String database, String id) throws Exception {
		String mydoctype = "";
		ReadData(database, id);
		if (_CMSpage.get(id) != null) {
			mydoctype = ((Page)_CMSpage.get(id)).header(db, myconfig, "doctype", "").trim();
		}
		if (mydoctype.equals("")) {
			mydoctype = mywebsite.getDefaultDocType();
		}
		if (mydoctype.equals("")) {
			mydoctype = myconfig.get(db, "doctype");
		}
		initDOCTYPE(mydoctype);
		return mydoctype;
	}



	public String PersonalDOCTYPE() throws Exception {
		String mydoctype = "";
		if (_CMSpage.get(myrequest.getQueryString()) == null) {
			_CMSpage.put(myrequest.getQueryString(), browseWebsite.getPersonal(myserver, mysession, myrequest, myresponse, myconfig, db, mywebsite));
		}
		if (_CMSpage.get(myrequest.getQueryString()) != null) {
			mydoctype = ((Page)_CMSpage.get(myrequest.getQueryString())).header(db, myconfig, "doctype", "").trim();
		}
		if (mydoctype.equals("")) {
			mydoctype = mywebsite.getDefaultDocType();
		}
		if (mydoctype.equals("")) {
			mydoctype = myconfig.get(db, "doctype");
		}
		initDOCTYPE(mydoctype);
		return mydoctype;
	}



	public String PageHTML(String id) throws Exception {
		String myhtmlattributes = "";
		ReadPage(id);
		if (_CMSpage.get(id) != null) {
			myhtmlattributes = ((Page)_CMSpage.get(id)).header(db, myconfig, "htmlattributes", "").trim();
		}
		if (! myhtmlattributes.equals("")) {
			myhtmlattributes = " " + myhtmlattributes;
		}
		return myhtmlattributes;
	}



	public String PageHeader(String id, String attribute, String attribute2) throws Exception {
		ReadPage(id);
		if (_CMSpage.get(id) == null) return "";
		String myoutput = ((Page)_CMSpage.get(id)).header(db, myconfig, attribute, attribute2).trim();
		myoutput = ((Page)_CMSpage.get(id)).parse_output_replace(myserver, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, id, "content_public", "id", mysession.get("version"), "", mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), "", "", "", myoutput);
		return myoutput;
	}
	public String PageHeader(String id, String attribute, String attribute2, String defaultoutput) throws Exception {
		String myoutput = PageHeader(id, attribute, attribute2);
		if ((! myoutput.equals("")) && (! myoutput.substring(0,1).equals(" "))) {
			myoutput = " " + myoutput;
		}
		if ((myoutput.equals("")) && (! defaultoutput.equals(""))) {
			myoutput = " " + defaultoutput;
		}
		return myoutput;
	}
	public String PageHeader(String id, String attribute, String attribute2, String defaultoutput, String mergeoutput) throws Exception {
		String myoutput = PageHeader(id, attribute, attribute2);
		if ((myoutput.equals("")) && (! defaultoutput.equals(""))) {
			myoutput = " " + defaultoutput;
		}
		if (! mergeoutput.equals("")) {
			myoutput = Page.mergeattributes(mergeoutput, myoutput);
		}
		if ((! myoutput.equals("")) && (! myoutput.substring(0,1).equals(" "))) {
			myoutput = " " + myoutput;
		}
		return myoutput;
	}



	public String PageStyleSheet(String id) throws Exception {
		String output = "";
		HashMap<String,String> displayedstylesheet = new HashMap<String,String>();
		ReadPage(id);
		if (_CMSpage.get(id) != null) {
			output = ((Page)_CMSpage.get(id)).outputStylesheetHtml(myconfig, db, displayedstylesheet) + output;
			if (((Page)_CMSpage.get(id)).getTemplateContent() != null) {
				output = ((Page)_CMSpage.get(id)).getTemplateContent().outputStylesheetHtml(myconfig, db, displayedstylesheet) + output;
				if (((Page)_CMSpage.get(id)).getTemplateContent().getTemplateContent() != null) {
					output = ((Page)_CMSpage.get(id)).getTemplateContent().getTemplateContent().outputStylesheetHtml(myconfig, db, displayedstylesheet) + output;
				}
			}
		}
		if (! output.equals("")) {
			Page mypage = new Page();
			mypage.setBody(output);
			mypage.parse_output(myserver, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, id, "content_public", "id", mysession.get("version"), "", mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), "", "", "");
			output = mypage.getBody() + "\r\n";
		}
		return output;
	}



	public String PageScripts(String id) throws Exception {
		String output = "";
		HashMap<String,String> displayedscript = new HashMap<String,String>();
		ReadPage(id);
		if (_CMSpage.get(id) != null) {
			output = ((Page)_CMSpage.get(id)).outputScriptHtml(myconfig, db, displayedscript) + output;
			if (((Page)_CMSpage.get(id)).getTemplateContent() != null) {
				output = ((Page)_CMSpage.get(id)).getTemplateContent().outputScriptHtml(myconfig, db, displayedscript) + output;
				if (((Page)_CMSpage.get(id)).getTemplateContent().getTemplateContent() != null) {
					output = ((Page)_CMSpage.get(id)).getTemplateContent().getTemplateContent().outputScriptHtml(myconfig, db, displayedscript) + output;
				}
			}
		}
		if (! output.equals("")) {
			Page mypage = new Page();
			mypage.setBody(output);
			mypage.parse_output(myserver, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, id, "content_public", "id", mysession.get("version"), "", mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), "", "", "");
			output = mypage.getBody() + "\r\n";
		}
		return output;
	}



	public void ReadProduct(String id) throws Exception {
		_Experience("product", "");
		mysession.set("id", html.encodeHtmlEntities(id));
		if (_CMSpage.get(id) == null) {
			_CMSpage.put(id, browseWebsite.getProductById(id, myserver, mysession, myrequest, myresponse, myconfig, db, mywebsite));
		}
		mysession.set("id", ((Page)_CMSpage.get(id)).getId());
	}



	public String DisplayProduct(String id, String attribute, String attribute2) throws Exception {
		ReadProduct(id);
		if (_CMSpage.get(id) == null) return "";
		return ((Page)_CMSpage.get(id)).display(attribute, attribute2, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"));
	}



	public String ProductHTML(String id) throws Exception {
		String myhtmlattributes = "";
		ReadProduct(id);
		if (_CMSpage.get(id) != null) {
			myhtmlattributes = ((Page)_CMSpage.get(id)).header(db, myconfig, "htmlattributes", "").trim();
		}
		if (! myhtmlattributes.equals("")) {
			myhtmlattributes = " " + myhtmlattributes;
		}
		return myhtmlattributes;
	}



	public String ProductHeader(String id, String attribute, String attribute2) throws Exception {
		ReadProduct(id);
		if (_CMSpage.get(id) == null) return "";
		String myoutput = ((Page)_CMSpage.get(id)).header(db, myconfig, attribute, attribute2).trim();
		myoutput = ((Page)_CMSpage.get(id)).parse_output_replace(myserver, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, id, "content_public", "id", mysession.get("version"), "", mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), "", "", "", myoutput);
		return myoutput;
	}
	public String ProductHeader(String id, String attribute, String attribute2, String defaultoutput) throws Exception {
		String myoutput = ProductHeader(id, attribute, attribute2);
		if ((! myoutput.equals("")) && (! myoutput.substring(0,1).equals(" "))) {
			myoutput = " " + myoutput;
		}
		if ((myoutput.equals("")) && (! defaultoutput.equals(""))) {
			myoutput = " " + defaultoutput;
		}
		return myoutput;
	}
	public String ProductHeader(String id, String attribute, String attribute2, String defaultoutput, String mergeoutput) throws Exception {
		String myoutput = ProductHeader(id, attribute, attribute2);
		if (! mergeoutput.equals("")) {
			myoutput = Page.mergeattributes(mergeoutput, myoutput);
		}
		if ((! myoutput.equals("")) && (! myoutput.substring(0,1).equals(" "))) {
			myoutput = " " + myoutput;
		}
		if ((myoutput.equals("")) && (! defaultoutput.equals(""))) {
			myoutput = " " + defaultoutput;
		}
		return myoutput;
	}



	public String ProductStyleSheet(String id) throws Exception {
		String output = "";
		HashMap<String,String> displayedstylesheet = new HashMap<String,String>();
		ReadProduct(id);
		if (_CMSpage.get(id) != null) {
			output = ((Page)_CMSpage.get(id)).outputStylesheetHtml(myconfig, db, displayedstylesheet) + output;
			if (((Page)_CMSpage.get(id)).getTemplateContent() != null) {
				output = ((Page)_CMSpage.get(id)).getTemplateContent().outputStylesheetHtml(myconfig, db, displayedstylesheet) + output;
				if (((Page)_CMSpage.get(id)).getTemplateContent().getTemplateContent() != null) {
					output = ((Page)_CMSpage.get(id)).getTemplateContent().getTemplateContent().outputStylesheetHtml(myconfig, db, displayedstylesheet) + output;
				}
			}
		}
		if (! output.equals("")) {
			Page mypage = new Page();
			mypage.setBody(output);
			mypage.parse_output(myserver, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, id, "content_public", "id", mysession.get("version"), "", mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), "", "", "");
			output = mypage.getBody() + "\r\n";
		}
		return output;
	}



	public String ProductScripts(String id) throws Exception {
		String output = "";
		HashMap<String,String> displayedscript = new HashMap<String,String>();
		ReadProduct(id);
		if (_CMSpage.get(id) != null) {
			output = ((Page)_CMSpage.get(id)).outputScriptHtml(myconfig, db, displayedscript) + output;
			if (((Page)_CMSpage.get(id)).getTemplateContent() != null) {
				output = ((Page)_CMSpage.get(id)).getTemplateContent().outputScriptHtml(myconfig, db, displayedscript) + output;
				if (((Page)_CMSpage.get(id)).getTemplateContent().getTemplateContent() != null) {
					output = ((Page)_CMSpage.get(id)).getTemplateContent().getTemplateContent().outputScriptHtml(myconfig, db, displayedscript) + output;
				}
			}
		}
		if (! output.equals("")) {
			Page mypage = new Page();
			mypage.setBody(output);
			mypage.parse_output(myserver, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, id, "content_public", "id", mysession.get("version"), "", mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), "", "", "");
			output = mypage.getBody() + "\r\n";
		}
		return output;
	}



	public void ReadStyleSheet(String ids) throws Exception {
		String[] myids = ids.split(",");
		for (int i=0; i<myids.length; i++) {
			String id = "" + myids[i];
			if (_CMSstyle.get(id) == null) {
				_CMSstyle.put(id, browseWebsite.getStyleSheetById(id, myserver, mysession, myrequest, myresponse, myconfig, db, mywebsite));
			}
		}
	}



	public String DisplayStyleSheet(String ids, String attribute, String attribute2) throws Exception {
		String output = "";
		String[] myids = ids.split(",");
		HashMap<String,String> displayedstylesheet = new HashMap<String,String>();
		for (int i=0; i<myids.length; i++) {
			String id = "" + myids[i];
			ReadStyleSheet(id);
			if (displayedstylesheet.get(id) == null) {
				if (_CMSstyle.get(id) != null) {
					Page mypage = new Page();
					mypage.setBody(((Content)_CMSstyle.get(id)).header(db, myconfig, attribute, ""));
					mypage.parse_output(myserver, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, id, "content_public", "id", mysession.get("version"), "", mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), "", "", "");
					output += mypage.getBody() + "\r\n";
				}
				displayedstylesheet.put(id, id);
			}
		}
		return output;
	}



	public String StyleSheetHeader() throws Exception {
		myresponse.setContentType("text/css");
		return "";
	}



	public void ReadScript(String ids) throws Exception {
		String[] myids = ids.split(",");
		for (int i=0; i<myids.length; i++) {
			String id = "" + myids[i];
			if (_CMSscript.get(id) == null) {
				_CMSscript.put(id, browseWebsite.getContentById(id, mysession, myrequest, myresponse, myconfig, db, mywebsite));
			}
		}
	}



	public String DisplayScript(String ids, String attribute, String attribute2) throws Exception {
		String output = "";
		String[] myids = ids.split(",");
		HashMap<String,String> displayedscript = new HashMap<String,String>();
		for (int i=0; i<myids.length; i++) {
			String id = "" + myids[i];
			ReadScript(id);
			if (displayedscript.get(id) == null) {
				if (_CMSscript.get(id) != null) {
					Page mypage = new Page();
					mypage.setBody(((Content)_CMSscript.get(id)).header(db, myconfig, attribute, ""));
					mypage.parse_output(myserver, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, id, "content_public", "id", mysession.get("version"), "", mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), "", "", "");
					output += mypage.getBody() + "\r\n";
				}
				displayedscript.put(id, id);
			}
		}
		return output;
	}



	public String ScriptHeader() throws Exception {
		myresponse.setContentType("application/x-javascript");
		return "";
	}



	public void ReadImage(String id) throws Exception {
		URLrootpath = myconfig.get(db, "URLrootpath");
		csURLrootpath = myconfig.get(db, "csURLrootpath");
		image_output_mode = myconfig.get(db, "image_output_mode");
		if (_CMSimage.get(id) == null) {
			_CMSimage.put(id, browseWebsite.getImageById(id, myserver, mysession, myrequest, myresponse, myconfig, db, mywebsite));
		}
	}



	public void DisplayImage(String id) throws Exception {
		Content image = new Content();
		ReadImage(id);
		if (_CMSimage.get(id) != null) {
			image = _CMSimage.get(id);
		}

//		if ((! mysession.get("mode").equals("preview")) && (! mysession.get("mode").equals("admin"))) {
//			Response.Expires = 10
//			Response.CacheControl = "public"
//		}
		if ((myrequest.getParameter("mode").equals("preview")) || (myrequest.getParameter("mode").equals("admin")) || (mysession.get("mode").equals("preview")) || (mysession.get("mode").equals("admin")) || (! mysession.get("preview_scheduled").equals("")) || ((! image.getVersionMaster().equals("")) && (! image.getVersionMaster().equals("0")))) {
			myresponse.setHeader("Cache-Control","no-cache");
			myresponse.setHeader("Pragma","no-cache");
			myresponse.setDateHeader ("Expires", 0);
		}

		String server_filename = URLrootpath + image.getServerFilename();
		String title_filename = "" + server_filename;
		if ((myrequest.getParameter("mode").equals("preview")) && (! myrequest.getParameter("archive").equals(""))) {
			server_filename += "." + myrequest.getParameter("archive");
			image_output_mode = "return";
		} else if ((mysession.get("mode").equals("preview")) && (! image.getArchiveId().equals(""))) {
			server_filename += "." + image.getArchiveId();
			image_output_mode = "return";
		} else if ((myrequest.getParameter("mode").equals("admin")) && (! myrequest.getParameter("archive").equals(""))) {
			server_filename += "." + myrequest.getParameter("archive");
			image_output_mode = "return";
		} else if ((mysession.get("mode").equals("admin")) && (! image.getArchiveId().equals(""))) {
			server_filename += "." + image.getArchiveId();
			image_output_mode = "return";
		}

		if ((! image.getServerFilename().equals("")) && (Common.fileExists(myroot + server_filename))) {
			if ((! image_output_mode.equals("return")) && (image.getUsersGroup().equals("")) && (image.getUsersType().equals("")) && (image.getUsersUsers().equals("")) && (image.getContentgroupUsersGroup().equals("")) && (image.getContentgroupUsersType().equals("")) && (image.getContentgroupUsersUsers().equals("")) && (image.getContenttypeUsersGroup().equals("")) && (image.getContenttypeUsersType().equals("")) && (image.getContenttypeUsersUsers().equals(""))) {
				// default
				myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + server_filename);
			} else {
				myresponse.returnFile(myroot + server_filename, myserver, "", title_filename, myrequest.getMethod());
				myout.clear();
				myout = mypagecontext.pushBody();
			}

		} else if ((! image.getServerFilename().equals("")) && (! csURLrootpath.equals(""))) {
			if ((! image_output_mode.equals("return")) && (image.getUsersGroup().equals("")) && (image.getUsersType().equals("")) && (image.getUsersUsers().equals("")) && (image.getContentgroupUsersGroup().equals("")) && (image.getContentgroupUsersType().equals("")) && (image.getContentgroupUsersUsers().equals("")) && (image.getContenttypeUsersGroup().equals("")) && (image.getContenttypeUsersType().equals("")) && (image.getContenttypeUsersUsers().equals(""))) {
				// default
				myresponse.sendRedirect(csURLrootpath + server_filename);
			} else {
				myresponse.returnFile(csURLrootpath + server_filename, myserver, "", title_filename, myrequest.getMethod());
				myout.clear();
				myout = mypagecontext.pushBody();
			}

		} else {
			if (! myresponse.getResponse().isCommitted()) {
				myresponse.getResponse().sendError(404);
			}
		}
	}



	public void ReadFile(String id) throws Exception {
		URLrootpath = myconfig.get(db, "URLrootpath");
		csURLrootpath = myconfig.get(db, "csURLrootpath");
		file_output_mode = myconfig.get(db, "file_output_mode");
		if (_CMSfile.get(id) == null) {
			_CMSfile.put(id, browseWebsite.getFileById(id, myserver, mysession, myrequest, myresponse, myconfig, db, mywebsite));
		}
	}



	public void DisplayFile(String id) throws Exception {
		Content file = new Content();
		ReadFile(id);
		if (_CMSfile.get(id) != null) {
			file = _CMSfile.get(id);
		}

//		if ((! mysession.get("mode").equals("preview")) && (! mysession.get("mode").equals("admin"))) {
//			Response.Expires = 10
//			Response.CacheControl = "public"
//		}
		if ((myrequest.getParameter("mode").equals("preview")) || (myrequest.getParameter("mode").equals("admin")) || (mysession.get("mode").equals("preview")) || (mysession.get("mode").equals("admin")) || (! mysession.get("preview_scheduled").equals("")) || ((! file.getVersionMaster().equals("")) && (! file.getVersionMaster().equals("0")))) {
			myresponse.setHeader("Cache-Control","no-cache");
			myresponse.setHeader("Pragma","no-cache");
			myresponse.setDateHeader ("Expires", 0);
		}

		String server_filename = URLrootpath + file.getServerFilename();
		String title_filename = "" + server_filename;
		if ((myrequest.getParameter("mode").equals("preview")) && (! myrequest.getParameter("archive").equals(""))) {
			server_filename += "." + myrequest.getParameter("archive");
			file_output_mode = "return";
		} else if ((mysession.get("mode").equals("preview")) && (! file.getArchiveId().equals(""))) {
			server_filename += "." + file.getArchiveId();
			file_output_mode = "return";
		} else if ((myrequest.getParameter("mode").equals("admin")) && (! myrequest.getParameter("archive").equals(""))) {
			server_filename += "." + myrequest.getParameter("archive");
			file_output_mode = "return";
		} else if ((mysession.get("mode").equals("admin")) && (! file.getArchiveId().equals(""))) {
			server_filename += "." + file.getArchiveId();
			file_output_mode = "return";
		}

		if ((! file.getServerFilename().equals("")) && (Common.fileExists(myroot + server_filename))) {
			if ((file_output_mode.equals("redirect")) && (file.getUsersGroup().equals("")) && (file.getUsersType().equals("")) && (file.getUsersUsers().equals("")) && (file.getContentgroupUsersGroup().equals("")) && (file.getContentgroupUsersType().equals("")) && (file.getContentgroupUsersUsers().equals("")) && (file.getContenttypeUsersGroup().equals("")) && (file.getContenttypeUsersType().equals("")) && (file.getContenttypeUsersUsers().equals(""))) {
				myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + server_filename);
			} else {
				// default
				myresponse.returnFile(myroot + server_filename, myserver, "", title_filename, myrequest.getMethod());
				myout.clear();
				myout = mypagecontext.pushBody();
			}

		} else if ((! file.getServerFilename().equals("")) && (! csURLrootpath.equals(""))) {
			if ((file_output_mode.equals("redirect")) && (file.getUsersGroup().equals("")) && (file.getUsersType().equals("")) && (file.getUsersUsers().equals("")) && (file.getContentgroupUsersGroup().equals("")) && (file.getContentgroupUsersType().equals("")) && (file.getContentgroupUsersUsers().equals("")) && (file.getContenttypeUsersGroup().equals("")) && (file.getContenttypeUsersType().equals("")) && (file.getContenttypeUsersUsers().equals(""))) {
				myresponse.sendRedirect(csURLrootpath + server_filename);
			} else {
				// default
				myresponse.returnFile(csURLrootpath + server_filename, myserver, "", title_filename, myrequest.getMethod());
				myout.clear();
				myout = mypagecontext.pushBody();
			}

		} else {
			if (! myresponse.getResponse().isCommitted()) {
				myresponse.getResponse().sendError(404);
			}
		}
	}



	public void ReadLink(String id) throws Exception {
		if (_CMSlink.get(id) == null) {
			_CMSlink.put(id, browseWebsite.getLinkById(id, mysession, myrequest, myresponse, myconfig, db, mywebsite));
		}
	}



	public void DisplayLink(String id) throws Exception {
		Content link = new Content();
		ReadLink(id);
		if (_CMSlink.get(id) != null) {
			link = _CMSlink.get(id);
		}

		String url = link.getUrl();
		if (! myresponse.getResponse().isCommitted()) {
			if (! url.equals("")) {
				myresponse.sendRedirect(url);
			} else {
				myresponse.getResponse().sendError(404);
			}
		}
	}



	public String CMSHeader(String id) throws Exception {
		ReadPage(id);
		if (_CMSpage.get(id) == null) return "";
		return ((Page)_CMSpage.get(id)).CMS_header(mysession.get("mode"), mysession.get("administrator"), mysession.get("admin_content_editor"), db, myconfig, myserver, mysession.getSession(), myrequest.getRequest(), myresponse.getResponse());
	}



	public String CMSStyleSheet(String id) throws Exception {
		String output = "";
		ReadPage(id);
		if (_CMSpage.get(id) != null) {
			for (int i=0; i<((Page)_CMSpage.get(id)).getStyleSheetContents().size(); i++) {
				if (((Page)_CMSpage.get(id)).getStyleSheetContents().get(i) != null) {
					output += ((Content)((Page)_CMSpage.get(id)).getStyleSheetContents().get(i)).display(" ", true, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator")) + "\r\n";
				}
			}
			if (((Page)_CMSpage.get(id)).getTemplateContent() != null) {
				for (int i=0; i<((Page)_CMSpage.get(id)).getTemplateContent().getStyleSheetContents().size(); i++) {
					if (((Page)_CMSpage.get(id)).getTemplateContent().getStyleSheetContents().get(i) != null) {
						output += ((Content)((Page)_CMSpage.get(id)).getTemplateContent().getStyleSheetContents().get(i)).display(" ", true, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator")) + "\r\n";
					}
				}
				if (((Page)_CMSpage.get(id)).getTemplateContent().getTemplateContent() != null) {
					for (int i=0; i<((Page)_CMSpage.get(id)).getTemplateContent().getTemplateContent().getStyleSheetContents().size(); i++) {
						if (((Page)_CMSpage.get(id)).getTemplateContent().getTemplateContent().getStyleSheetContents().get(i) != null) {
							output += ((Content)((Page)_CMSpage.get(id)).getTemplateContent().getTemplateContent().getStyleSheetContents().get(i)).display(" ", true, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator")) + "\r\n";
						}
					}
				}
			}
		}
		return output;
	}



	public String CMSTemplate(String id) throws Exception {
		ReadPage(id);
		if (_CMSpage.get(id) == null) return "";
		return ((Page)_CMSpage.get(id)).getTemplateContent().display(" ", true, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"));
	}



	public String CMSDisplay(String id) throws Exception {
		ReadPage(id);
		if (_CMSpage.get(id) == null) return "";
		return ((Page)_CMSpage.get(id)).CMS_display(mysession.get("mode"), mysession.get("administrator"), mysession.get("username"), mysession.get("preview_scheduled"), db, myconfig);
	}



	public void CMSLog(String requestid, String requestclass, String requestdatabase) {
		if ((! myrequest.getParameter("mode").equals("preview")) && (! myrequest.getParameter("mode").equals(" "))) {
			if ((requestid.equals("")) && (_CMSpage.get(requestid) != null)) {
				if ((! ((Page)_CMSpage.get(requestid)).getId().equals("")) && (! ((Page)_CMSpage.get(requestid)).getId().equals("0"))) {
					UCbrowseWebsite browseWebsite = new UCbrowseWebsite(text);
					if (logdb != null) {
						browseWebsite.doLog(((Page)_CMSpage.get(requestid)).getId(), requestclass, requestdatabase, mysession, myrequest, myconfig, logdb);
					} else {
						browseWebsite.doLog(((Page)_CMSpage.get(requestid)).getId(), requestclass, requestdatabase, mysession, myrequest, myconfig, db);
					}
				}
			} else {
				UCbrowseWebsite browseWebsite = new UCbrowseWebsite(text);
				if (logdb != null) {
					browseWebsite.doLog(requestid, requestclass, requestdatabase, mysession, myrequest, myconfig, logdb);
				} else {
					browseWebsite.doLog(requestid, requestclass, requestdatabase, mysession, myrequest, myconfig, db);
				}
			}
		}
		if (DEBUG) System.out.println("HardCore.Cms:ENDED:"+DEBUGdatetime+":"+(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS").format(new java.util.Date()))+":"+myrequest.getRequest().getRequestURL()+" - "+myrequest.getRequest().getQueryString()+" - "+myrequest.getRequest().getHeader("cookie")+" - "+DEBUGscript.getClass());
	}



       	static public void CMSAudit(String message) {
		// public Cms(Object myscript, Text mytext, ServletContext servletcontext, Session session, Request request, Response response, Configuration config, DB mydb, DB mylogdb) {
       		if (auditlog != null) {
			auditlog.info(message);
			//auditlog.debug("Operation performed successfully");
			//auditlog.error("Value of X is null");
		}
	}



       	static public void CMSDebug(String message) {
		// public Cms(Object myscript, Text mytext, ServletContext servletcontext, Session session, Request request, Response response, Configuration config, DB mydb, DB mylogdb) {
       		if (debuglog != null) {
			debuglog.debug(message);
			//auditlog.debug("Operation performed successfully");
			//auditlog.error("Value of X is null");
		}
	}



	public void CMSpage(String id, Page page) {
		_CMSpage.put(id, page);
		mysession.set("id", page.getId());
	}



	public void CMSstylesheet(String id, Content stylesheet) {
		_CMSstyle.put(id, stylesheet);
	}



	public void HttpHeaders(String id) {
		Pattern p = Pattern.compile("^<meta http-equiv=\"([^\"]+)\" content=\"([^\"]*)\".*>");
		Pattern p2 = Pattern.compile("^<meta http-equiv=\"([^\"]+)\" content='([^']*)'.*>");
		String htmlheaders = "";
		if (_CMSpage.get(id) != null) {
			htmlheaders = ((Page)_CMSpage.get(id)).header(db, myconfig, "htmlheader", "");
		} else if (_CMSpage.get(id) != null) {
			htmlheaders = ((Page)_CMSpage.get(id)).header(db, myconfig, "htmlheader", "");
		}
		int headerstart = htmlheaders.indexOf("<meta http-equiv=");
		while (headerstart > -1) {
			htmlheaders = htmlheaders.substring(headerstart);
			Matcher m = p.matcher(htmlheaders);
			Matcher m2 = p2.matcher(htmlheaders);
			if (m.find()) {
				myresponse.setHeader(m.group(1), m.group(2));
			} else if (m2.find()) {
				myresponse.setHeader(m2.group(1), m2.group(2));
			}
			htmlheaders = htmlheaders.substring(1);
			headerstart = htmlheaders.indexOf("<meta http-equiv=");
		}
	}



}
