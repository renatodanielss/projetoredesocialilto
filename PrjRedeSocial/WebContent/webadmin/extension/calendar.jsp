<%@ include file="../../webadmin.jsp" %><%@ page import="HardCore.Common" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Databases" %><%@ page import="HardCore.Page" %><%@ page import="java.util.Date" %><%@ page import="java.net.URLEncoder" %><%

boolean displayWeeks = true;
String padding = "-";
String week = "Week";
String monday = "M";
String tuesday = "T";
String wednesday = "W";
String thursday = "T";
String friday = "F";
String saturday = "S";
String sunday = "S";

String[] parameters;
if ((myrequest.getAttribute("extension") != null) && (! myrequest.getAttribute("extension").equals(""))) {
	parameters = ("" + myrequest.getAttribute("extension")).split(":", 4);
} else {
	parameters = mysession.get("extension").split(":", 4);
}
String parametername;
Date mynow = new Date();
Pattern p = Pattern.compile("^(.+)=(.*)$");
Matcher m = p.matcher(parameters[0]);
if (m.find()) {
	parametername = "" + m.group(1);
	mynow = new Date();
	if (! m.group(2).equals("")) {
		int offset = 0;
		if ((m.group(2).length() > 11) && (m.group(2).matches("^\\d\\d\\d\\d-\\d\\d-\\d\\d"))) {
			mynow = Common.strtodate("yyyy-mm-dd", m.group(2));
			offset = 0 + Integer.parseInt(m.group(2).substring(11));
			if (m.group(2).charAt(10) == '-') {
				offset = -offset;
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			calendar.setTime(mynow);
			calendar.add(Calendar.MONTH, offset);
			mynow = calendar.getTime();
		} else if ((m.group(2).length() == 10) && (m.group(2).matches("^\\d\\d\\d\\d-\\d\\d-\\d\\d"))) {
			mynow = Common.strtodate("yyyy-mm-dd", m.group(2));
		} else if ((m.group(2).length() == 7) && (m.group(2).matches("^\\d\\d\\d\\d-\\d\\d"))) {
			mynow = Common.strtodate("yyyy-mm-dd", m.group(2)+"-01");
		} else if ((m.group(2).length() == 4) && (m.group(2).matches("^\\d\\d\\d\\d"))) {
			mynow = Common.strtodate("yyyy-mm-dd", m.group(2)+"-01-01");
		} else if (m.group(2).length() < 10) {
			mynow = new Date();
			offset = 0 + (int)Common.integernumber(m.group(2).substring(1));
			if (m.group(2).charAt(0) == '-') {
				offset = -offset;
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			calendar.setTime(mynow);
			calendar.add(Calendar.MONTH, offset);
			mynow = calendar.getTime();
		} else {
			mynow = Common.strtodate("yyyy-mm-dd", m.group(2));
		}
	}
} else {
	parametername = "date";
	mynow = new Date();
	if (parameters.length > 0) {
		if (! parameters[0].equals("")) { mynow = Common.strtodate("yyyy-mm-dd", parameters[0]); }
	}
}
String page_id = "";
if (parameters.length > 1) { page_id = "" + parameters[1].replaceAll("^id=", ""); }
String datecolumnname = "";
if (parameters.length > 2) { datecolumnname = "" + parameters[2]; }
String datecolumnname2 = "";
String textcolumnname = "";
String timetext = "";
String[] datecolumns = "".split("-");
if (parameters.length > 2) { datecolumns = ("" + parameters[2]).split("-"); }
if (datecolumns.length > 0) { datecolumnname = datecolumns[0]; }
if (datecolumns.length > 1) { datecolumnname2 = datecolumns[1]; }
if (datecolumns.length > 2) { textcolumnname = datecolumns[2]; }
if (datecolumns.length > 3) { timetext = datecolumns[3]; }
String list = "";
if (parameters.length > 3) { list = "" + parameters[3]; }

String SQLwhere = "";
String SQLdata = "";
Page mypage = new Page(mytext);
Databases mydatabase = mypage.parse_output_list_database(list, myrequest, myresponse, mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
if ((! mydatabase.getId().equals("")) && (! mydatabase.getId().equals("0")) && (! mydatabase.getId().equals("-1"))) {
	SQLdata = mypage.parse_output_list_data(list, myrequest, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"), mydatabase);
} else {
	SQLwhere = mypage.parse_output_list_where(list, myrequest, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"), mydatabase);
}
String url = "/page.jsp?id=" + page_id + "&amp;" + parametername + "=";

HashMap dates = new HashMap();
HashMap texts = new HashMap();
if ((mydatabase != null) &&  ((! mydatabase.getId().equals("")) && (! mydatabase.getId().equals("0")) && (! mydatabase.getId().equals("-1"))) && (! SQLdata.equals(""))) {
	if (mydatabase.getUser()) {
		String datecolumn = mydatabase.getAttributeColumnColumn(datecolumnname);
		String datecolumn2 = "";
		String textcolumn = "";
		if (! datecolumnname2.equals("")) {
			datecolumn2 = mydatabase.getAttributeColumnColumn(datecolumnname2);
		}
		if (! textcolumnname.equals("")) {
			textcolumn = mydatabase.getAttributeColumnColumn(textcolumnname);
		}
		if (! datecolumn.equals("")) {
			SQLdata += " order by " + datecolumn;
			if (! datecolumn2.equals("")) {
				SQLdata += "," + datecolumn2;
			}
			if (! textcolumn.equals("")) {
				SQLdata += "," + textcolumn;
			}
		}
		Page dummypage = new Page(mytext);
		Data data = new Data();
		data.records(db, SQLdata);
		while (data.records(db, "")) {
			String date = data.getContent(datecolumn);
			if (date.length() > 10) date = date.substring(0,10);
			dates.put(date, url + date);
			String time = data.getContent(datecolumn);
			if (time.length() > 11) {
				time = time.substring(11);
			} else {
				time = "";
			}
			String date2 = "";
			String time2 = "";
			if (! datecolumn2.equals("")) {
				date2 = data.getContent(datecolumn2);
				if (date2.length() > 10) date2 = date2.substring(0,10);
				dates.put(date2, url + date2);
				time2 = data.getContent(datecolumn2);
				if (time2.length() > 11) {
					time2 = time2.substring(11);
				} else {
					time2 = "";
				}
			}
			if (! textcolumn.equals("")) {
				if (texts.get(date) == null) texts.put(date, "");
				texts.put(date, "" + texts.get(date) + "<br><span class=\"data\">");
				texts.put(date, "" + texts.get(date) + "<a class=\"emphasis\" href=\"" + url + date + "&data_id=" + data.getId() + "&" + URLEncoder.encode(textcolumnname) + "=" + URLEncoder.encode(data.getContent(textcolumn)) + "\">");
				if ((! timetext.equals("")) && (! time.equals(""))) {
					if (date.equals(date2)) {
						texts.put(date, "" + texts.get(date) + time + "-" + time2 + " ");
					} else {
						texts.put(date, "" + texts.get(date) + time + "-" + timetext + " ");
					}
				}
				texts.put(date, "" + texts.get(date) + data.getContent(textcolumn));
				texts.put(date, "" + texts.get(date) + "</a>");
				texts.put(date, "" + texts.get(date) + "</span>");
			}
			if (! datecolumn2.equals("")) {
				if ((! textcolumn.equals("")) && (! date.equals(date2))) {
					if (texts.get(date2) == null) texts.put(date2, "");
					texts.put(date2, "" + texts.get(date2) + "<br><span class=\"data\">");
					texts.put(date2, "" + texts.get(date2) + "<a class=\"emphasis\" href=\"" + url + date2 + "&data_id=" + data.getId() + "&" + URLEncoder.encode(textcolumnname) + "=" + URLEncoder.encode(data.getContent(textcolumn)) + "\">");
					if ((! timetext.equals("")) && (! time2.equals(""))) {
						texts.put(date2, "" + texts.get(date2) + timetext + "-" + time2 + " ");
					}
					texts.put(date2, "" + texts.get(date2) + data.getContent(textcolumn));
					texts.put(date2, "" + texts.get(date2) + "</a>");
					texts.put(date2, "" + texts.get(date2) + "</span>");
				}
				try {
					Date mydate = Common.strtodate("yyyy-mm-dd", data.getContent(datecolumn));
					Date mydate2 = Common.strtodate("yyyy-mm-dd", data.getContent(datecolumn2));
					Calendar calendar = Calendar.getInstance();
					calendar.setFirstDayOfWeek(Calendar.MONDAY);
					calendar.setTime(mydate);
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					mydate = calendar.getTime();
					while (mydate.getTime() < mydate2.getTime()) {
						date = "" + (new SimpleDateFormat("yyyy-MM-dd")).format(mydate.getTime());
						dates.put(date, url + "" + (new SimpleDateFormat("yyyy-MM-dd")).format(mydate.getTime()));
						if (! textcolumn.equals("")) {
							if (texts.get(date) == null) texts.put(date, "");
							texts.put(date, "" + texts.get(date) + "<br><span class=\"data\">");
							texts.put(date, "" + texts.get(date) + "<a class=\"emphasis\" href=\"" + url + date + "&data_id=" + data.getId() + "&" + URLEncoder.encode(textcolumnname) + "=" + URLEncoder.encode(data.getContent(textcolumn)) + "\">");
							if (! timetext.equals("")) {
								texts.put(date, "" + texts.get(date) + timetext + " ");
							}
							texts.put(date, "" + texts.get(date) + data.getContent(textcolumn));
							texts.put(date, "" + texts.get(date) + "</a>");
							texts.put(date, "" + texts.get(date) + "</span>");
						}
						calendar = Calendar.getInstance();
						calendar.setFirstDayOfWeek(Calendar.MONDAY);
						calendar.setTime(mydate);
						calendar.add(Calendar.DAY_OF_MONTH, 1);
						mydate = calendar.getTime();
					}
				} catch (Exception e) {
				}
			}
		}
	}
} else {
	String SQL = "select distinct " + datecolumnname + " from content_public where " + SQLwhere;
	Content content = new Content(mytext);
	content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
	while (content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
		String date = "";
		if (datecolumnname.equals("created")) {
			if (content.getCreated().length() > 10) {
				date = content.getCreated().substring(0, 10);
			} else {
				date = content.getCreated();
			}
			dates.put(date, url + date);
		} else if (datecolumnname.equals("updated")) {
			if (content.getUpdated().length() > 10) {
				date = content.getUpdated().substring(0, 10);
			} else {
				date = content.getUpdated();
			}
			dates.put(date, url + date);
		} else if (datecolumnname.equals("published")) {
			if (content.getPublished().length() > 10) {
				date = content.getPublished().substring(0, 10);
			} else {
				date = content.getPublished();
			}
			dates.put(date, url + date);
		}
	}
}

if (mynow == null) mynow = new Date();
Calendar monthstart = Calendar.getInstance();
monthstart.setFirstDayOfWeek(Calendar.MONDAY);
monthstart.setTime(mynow);
monthstart.set(Calendar.DAY_OF_MONTH, 1);
Calendar monthend = Calendar.getInstance();
monthend.setFirstDayOfWeek(Calendar.MONDAY);
monthend.setTime(monthstart.getTime());
monthend.add(Calendar.MONTH, 1);
monthend.add(Calendar.DAY_OF_MONTH, -1);

Calendar monthprevious = Calendar.getInstance();
monthprevious.setTime(monthstart.getTime());
monthprevious.add(Calendar.MONTH, -1);
Calendar monthnext = Calendar.getInstance();
monthnext.setTime(monthend.getTime());
monthnext.add(Calendar.MONTH, 1);

%>

<table class="calendar">
<thead>
<tr>
<th colspan="<% if (displayWeeks) { %>8<% } else { %>7<% } %>"><div style="white-space: nowrap"><a href="<%= url + (new SimpleDateFormat("yyyy-MM")).format(monthstart.getTime()) %>"><%= (new SimpleDateFormat("MMMMM")).format(monthstart.getTime()) + " " + (new SimpleDateFormat("yyyy")).format(monthstart.getTime()) %></a></div></th>
</tr>
</thead>
<tbody align="right">
<tr>
<% if (displayWeeks) { %>
<th style="width: 12.5%"><%= week %></th>
<th style="width: 12.5%"><%= monday %></th>
<th style="width: 12.5%"><%= tuesday %></th>
<th style="width: 12.5%"><%= wednesday %></th>
<th style="width: 12.5%"><%= thursday %></th>
<th style="width: 12.5%"><%= friday %></th>
<th style="width: 12.5%"><%= saturday %></th>
<th style="width: 12.5%"><%= sunday %></th>
<% } else { %>
<th style="width: 14%"><%= monday %></th>
<th style="width: 14%"><%= tuesday %></th>
<th style="width: 14%"><%= wednesday %></th>
<th style="width: 14%"><%= thursday %></th>
<th style="width: 14%"><%= friday %></th>
<th style="width: 14%"><%= saturday %></th>
<th style="width: 14%"><%= sunday %></th>
<% } %>
</tr>
<tr style="vertical-align: top;">
<% if (displayWeeks) { %>
<td class="Week"><%= (new SimpleDateFormat("w")).format(monthstart.getTime()) %></td>
<% } %>
<%
	int pad = monthstart.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
	if (pad<0) pad += 7;
	for (int i=0; i<pad; i++) {
		%><td><%= padding %></td><%
	}
	for (Calendar now=monthstart; (now.get(Calendar.DAY_OF_MONTH)<=monthend.get(Calendar.DAY_OF_MONTH)) && (now.get(Calendar.MONTH)==monthend.get(Calendar.MONTH)); now.add(Calendar.DAY_OF_MONTH,1)) {
		if ((now.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) && (now.get(Calendar.DAY_OF_MONTH) > 1)) {
			if (displayWeeks) {
				%><td class="Week"><%= (new SimpleDateFormat("w")).format(now.getTime()) %></td><%
			}
		}
		%><td class="<%= (new SimpleDateFormat("EEEEE")).format(now.getTime()) %>"><%
		if (dates.get((new SimpleDateFormat("yyyy-MM-dd")).format(now.getTime())) != null) {
			%><a class="emphasis" href="<%= dates.get((new SimpleDateFormat("yyyy-MM-dd")).format(now.getTime())) %>"><%
		}
		%><%= now.get(Calendar.DAY_OF_MONTH) %><%
		if (dates.get((new SimpleDateFormat("yyyy-MM-dd")).format(now.getTime())) != null) {
			%></a><%
		}
		if (texts.get((new SimpleDateFormat("yyyy-MM-dd")).format(now.getTime())) != null) {
			%><%= texts.get((new SimpleDateFormat("yyyy-MM-dd")).format(now.getTime())) %><%
		}
		%></td><%
		if (now.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			%></tr><%= "\r\n" %><tr style="vertical-align: top;"><%
		}
	}
	pad = Calendar.SUNDAY - monthend.get(Calendar.DAY_OF_WEEK);
	if (pad<0) pad += 7;
	for (int i=0; i<pad; i++) {
		%><td><%= padding %></td><%
	}
%>
</tr>
</tbody>
<tfoot>
<tr>
<% if (displayWeeks) { %>
<th colspan="4"><div style="white-space: nowrap"><a href="<%= url + (new SimpleDateFormat("yyyy-MM")).format(monthprevious.getTime()) %>">&lt;&lt; <%= (new SimpleDateFormat("MMM")).format(monthprevious.getTime()) + " " + (new SimpleDateFormat("yyyy")).format(monthprevious.getTime()) %></a></div></th>
<th colspan="4"><div style="white-space: nowrap"><a href="<%= url + (new SimpleDateFormat("yyyy-MM")).format(monthnext.getTime()) %>"><%= (new SimpleDateFormat("MMM")).format(monthnext.getTime()) + " " + (new SimpleDateFormat("yyyy")).format(monthnext.getTime()) %> &gt;&gt;</a></div></th>
<% } else { %>
<th colspan="3"><div style="white-space: nowrap"><a href="<%= url + (new SimpleDateFormat("yyyy-MM")).format(monthprevious.getTime()) %>">&lt;&lt; <%= (new SimpleDateFormat("MMM")).format(monthprevious.getTime()) + " " + (new SimpleDateFormat("yyyy")).format(monthprevious.getTime()) %></a></div></th>
<th colspan="1"></th>
<th colspan="3"><div style="white-space: nowrap"><a href="<%= url + (new SimpleDateFormat("yyyy-MM")).format(monthnext.getTime()) %>"><%= (new SimpleDateFormat("MMM")).format(monthnext.getTime()) + " " + (new SimpleDateFormat("yyyy")).format(monthnext.getTime()) %> &gt;&gt;</a></div></th>
<% } %>
</tr>
</tfoot>
</table>
