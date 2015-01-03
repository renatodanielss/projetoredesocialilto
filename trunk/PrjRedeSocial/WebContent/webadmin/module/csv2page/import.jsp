<%@ include file="../../config.jsp" %><%@ page import="HardCore.RequireUser" %><%@ page import="HardCore.MenuContent" %><% RequireUser.Administrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, database, mysession.get("database")); %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.Content" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.Fileupload" %><%@ page import="java.io.*" %><%@ page import="java.util.*" %><%@ page import="java.util.regex.*" %><%

String title = "Data-to-Page Import";

String menu = "";

String content = "";
content += "        <table width=\"100%\">" + "\r\n";
content += "          <tr>" + "\r\n";
content += "            <th class=\"WCMinnerContentHeading1\" align=\"left\">Data-to-Page Import</th>" + "\r\n";
content += "          </tr>" + "\r\n";
content += "          <tr>" + "\r\n";
content += "            <td class=\"WCMinnerContentIntro\">Generated pages from imported CSV format data file.</td>" + "\r\n";
content += "          </tr>" + "\r\n";
content += "        </table>" + "\r\n";


String error = "";
String save_content_editor = myconfig.get(db, "content_editor");
Fileupload filepost = new Fileupload(myrequest, DOCUMENT_ROOT + myconfig.get(db, "URLrootpath"), myconfig.get(db, "URLuploadpath"));
myconfig.setTemp("content_editor", save_content_editor);
if (filepost.getParameter("master_page").equals("")) {
	content += "<div style=\"color: #cc0000;\"><b>ERROR:</b> No master page selected.</div>\r\n";
}
if (filepost.getParameter("data_file.filename").equals("")) {
	content += "<div style=\"color: #cc0000;\"><b>ERROR:</b> No data file selected.</div>\r\n";
}
if ((! filepost.getParameter("master_page").equals("")) && (! filepost.getParameter("data_file.filename").equals(""))) {
	boolean first = true;
	BufferedReader file = null;
	try {
		file = new BufferedReader(new FileReader(DOCUMENT_ROOT + myconfig.get(db, "URLrootpath") + filepost.getParameter("data_file.server_filename")));
	} catch (FileNotFoundException e) {
		if (file != null) try { file.close(); } catch (IOException ee) { ; }
	}
	String[] columns = "".split("\\t");
	String my_line;
	while (((my_line = read_line(file)) != null) && (error.equals(""))) {
		if (first) {
			first = false;
			columns = (my_line+"\t").split("\\t");
		} else if (! my_line.equals("")) {
			Page mypage = new Page(mytext);
			mypage.read_primary_only(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, filepost.getParameter("master_page"), "content", "id", "", "");
			String[] values = (my_line+"\tEOL").split("\\t");
			for (int column = 0; column < columns.length; column++) {
				String key = "" + columns[column];
				String value = "" + values[column];
				mypage.setTitle(mypage.getTitle().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setContent(mypage.getContent().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setRevision(mypage.getRevision().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setAuthor(mypage.getAuthor().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setKeywords(mypage.getKeywords().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setDescription(mypage.getDescription().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setSummary(mypage.getSummary().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setMetaInfo(mypage.getMetaInfo().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setHtmlHeader(mypage.getHtmlHeader().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setHtmlBodyOnload(mypage.getHtmlBodyOnload().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setContentClass(mypage.getContentClass().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setContentGroup(mypage.getContentGroup().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setContentType(mypage.getContentType().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setProductCode(mypage.getProductCode().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setProductCurrency(mypage.getProductCurrency().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setProductPrice(mypage.getProductPrice().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setProductStock(mypage.getProductStock().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setProductComment(mypage.getProductComment().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setProductEmail(mypage.getProductEmail().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setProductURL(mypage.getProductURL().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setProductInfo(mypage.getProductInfo().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setProductOptions(mypage.getProductOptions().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setProductDelivery(mypage.getProductDelivery().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setProductUser(mypage.getProductUser().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setProductPage(mypage.getProductPage().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setProductProgram(mypage.getProductProgram().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				mypage.setProductHosting(mypage.getProductHosting().replaceAll("@@@" + key + "@@@", value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
			}
			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			mypage.setCreated(timestamp);
			mypage.setCreatedBy(mysession.get("username"));
			mypage.setUpdated(timestamp);
			mypage.setUpdatedBy(mysession.get("username"));
			if (! mypage.getPublished().equals("")) {
				mypage.setPublished(timestamp);
				mypage.setPublishedBy(mysession.get("username"));
			}
			mypage.create(db, myconfig,"content", "id");
			if (mypage.exists(db, mypage.getId(), "content_public", "id", "", "")) {
				mypage.update(db, myconfig,mypage.getId(), "content_public", "id");
			} else {
				mypage.create(db, myconfig,"content_public", "id");
			}
			content += "<div>" + mypage.getTitle() + " [" + mypage.getId() + "]</div>" + "\r\n";
		}
	}
	file.close();
	Common.deleteFile(DOCUMENT_ROOT + myconfig.get(db, "URLrootpath") + filepost.getParameter("data_file.server_filename"));
}

%><%!
	private String read_line(BufferedReader file) {
		String my_line = null;
		try {
			my_line = file.readLine();
		} catch (IOException e) {
			my_line = null;
		}
		return my_line;
	}

%><%@ include file="../../module.jsp.html" %>