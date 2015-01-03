<%@ include file="../../config.jsp" %><%@ page import="HardCore.RequireUser" %><%@ page import="HardCore.MenuContent" %><% RequireUser.SuperAdministrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, database, mysession.get("database")); %><%@ page import="HardCore.Content" %><%

Content mypage = new Content(mytext);

String title = "Bulk Update";

String menu = "";

String content = "";
content +=  "      <form action=\"bulkupdate.jsp\" method=\"post\"" + "\r\n";
content +=  "        <input type=\"hidden\" value=\"index.jsp\">" + "\r\n";
content +=  "        <table width=\"100%\">" + "\r\n";
content +=  "          <tr>" + "\r\n";
content +=  "            <th class=\"WCMinnerContentHeading1\" align=\"left\">Bulk Update</th>" + "\r\n";
content +=  "          </tr>" + "\r\n";
content +=  "          <tr>" + "\r\n";
content +=  "            <td class=\"WCMinnerContentIntro\">Set style sheet and/or template for all content items in given content group/type.</td>" + "\r\n";
content +=  "          </tr>" + "\r\n";
content +=  "          <tr>" + "\r\n";
content +=  "            <th class=\"WCMinnerContentInputName\" align=\"left\">Style Sheet</th>" + "\r\n";
content +=  "            <th class=\"WCMinnerContentInputName\" align=\"left\">Template</th>" + "\r\n";
content +=  "          </tr>" + "\r\n";
content +=  "          <tr> " + "\r\n";
content +=  "            <td class=\"WCMinnerContentInputValue\"> " + "\r\n";
content +=  "              <select id=\"stylesheet\" name=\"stylesheet\" style=\"width: 400px;\">" + "\r\n";
content +=  "                <option value=\"-\">- UNCHANGED -" + "\r\n";
content +=  "                <option value=\"\">" + mytext.display("default") + "\r\n";
content +=  "                <option value=\"0\">" + mytext.display("none") + "\r\n";
content +=  mypage.select_options(db, "stylesheet", ""); 
content +=  "              </select>" + "\r\n";
content +=  "            </td>" + "\r\n";
content +=  "            <td class=\"WCMinnerContentInputValue\"> " + "\r\n";
content +=  "              <select id=\"template\" name=\"template\" style=\"width: 400px;\">" + "\r\n";
content +=  "                <option value=\"-\">- UNCHANGED -" + "\r\n";
content +=  "                <option value=\"\">" + mytext.display("default") + "\r\n";
content +=  "                <option value=\"0\">" + mytext.display("none") + "\r\n";
content +=  mypage.select_options(db, "template", ""); 
content +=  "              </select>" + "\r\n";
content +=  "            </td>" + "\r\n";
content +=  "          </tr>" + "\r\n";
content +=  "          <tr><td>&nbsp;</td></tr>" + "\r\n";
content +=  "          <tr>" + "\r\n";
content +=  "            <th class=\"WCMinnerContentInputName\" align=\"left\">" + mytext.display("contentgroups.contentgroup") + "</th>" + "\r\n";
content +=  "            <th class=\"WCMinnerContentInputName\" align=\"left\">" + mytext.display("contenttypes.contenttype") + "</th>" + "\r\n";
content +=  "          </tr>" + "\r\n";
content +=  "          <tr> " + "\r\n";
content +=  "            <td class=\"WCMinnerContentInputValue\"> " + "\r\n";
content +=  "              <select id=\"contentgroup\" name=\"contentgroup\" style=\"width: 400px;\">" + "\r\n";
content +=  "                <option value=\"-\">" + mytext.display("all") + "\r\n";
content +=  "                <option value=\"\">" + mytext.display("none") + "\r\n";
content +=  mypage.contentgroup_select_options(db, "") + "\r\n";
content +=  "              </select>" + "\r\n";
content +=  "            </td>" + "\r\n";
content +=  "            <td class=\"WCMinnerContentInputValue\"> " + "\r\n";
content +=  "              <select id=\"contenttype\" name=\"contenttype\" style=\"width: 400px;\">" + "\r\n";
content +=  "                <option value=\"-\">" + mytext.display("all") + "\r\n";
content +=  "                <option value=\"\">" + mytext.display("none") + "\r\n";
content +=  mypage.contenttype_select_options(db, "") + "\r\n";
content +=  "              </select>" + "\r\n";
content +=  "            </td>" + "\r\n";
content +=  "          </tr>" + "\r\n";
content +=  "        </table>" + "\r\n";
content +=  "        <p><input type=\"submit\" value=\"Apply Style Sheet And Template To Content Group/Type\"></p>" + "\r\n";
content +=  "      </form>" + "\r\n";

%><%@ include file="../../module.jsp.html" %>