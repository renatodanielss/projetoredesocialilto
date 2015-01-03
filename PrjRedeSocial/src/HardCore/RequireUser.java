package HardCore;

import java.util.regex.*;

public class RequireUser {



	public static boolean User(String session_username, Request request, Response response, Session session, DB db) {
		return User(new Text(), session_username, request, response, session, db);
	}
	public static boolean User(Text text, String session_username, Request request, Response response, Session session, DB db) {
		if (session_username.equals("")) {
			session.set("login_page", "");
			if (request.getMethod().equals("GET")) {
				String REQUEST_URI = "";
				if (request.getRequestURI() != null) REQUEST_URI += request.getRequestURI();
				String QUERY_STRING = "";
				if (request.getQueryString() != null) QUERY_STRING += request.getQueryString();
				response.sendRedirect(request.getProtocol() + request.getServerName() + request.getServerPort() + "/login_post.jsp?url=" + URLEncoder.encode(REQUEST_URI + "?" + Common.crlf_clean(QUERY_STRING)));
			} else {
				response.sendRedirect(request.getProtocol() + request.getServerName() + request.getServerPort() + "/login_post.jsp?url=" + URLEncoder.encode("/" + text.display("adminpath") + "/?error=" + text.display("error.login.expired")));
			}
			return false;
		} else {
			return true;
		}
	}



	public static boolean Userid(Text text, String userids, Request request, Response response, Session session, DB db) {
		return Userid(text, userids, "", request, response, session, db);
	}
	public static boolean Userid(Text text, String userids, String login_page, Request request, Response response, Session session, DB db) {
		if (userids.equals("")) return true;
		if (userids.equals(session.get("userid"))) return true;
		if (! Pattern.compile("(^|,)" + session.get("userid") + "(,|$)").matcher(userids).find()) {
			session.set("login_page", "" + login_page);
			if (request.getMethod().equals("GET")) {
				String REQUEST_URI = "";
				if (request.getRequestURI() != null) REQUEST_URI += request.getRequestURI();
				String QUERY_STRING = "";
				if (request.getQueryString() != null) QUERY_STRING += request.getQueryString();
				response.sendRedirect(request.getProtocol() + request.getServerName() + request.getServerPort() + "/login_post.jsp?url=" + URLEncoder.encode(REQUEST_URI + "?" + Common.crlf_clean(QUERY_STRING)));
			} else {
				response.sendRedirect(request.getProtocol() + request.getServerName() + request.getServerPort() + "/login_post.jsp?url=" + URLEncoder.encode("/" + text.display("adminpath") + "/?error=" + text.display("error.login.expired")));
			}
			return false;
		} else {
			return true;
		}
	}



	public static boolean Usergroup(String usergroup, String session_usergroup, String session_usergroups, Request request, Response response, Session session, DB db) {
		return Usergroup(new Text(), usergroup, session_usergroup, session_usergroups, request, response, session, db);
	}
	public static boolean Usergroup(Text text, String usergroup, String session_usergroup, String session_usergroups, Request request, Response response, Session session, DB db) {
		if ((! session_usergroup.equals(usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + usergroup + "|") >= 0))) {
			Usergroup myusergroup = new Usergroup();
			myusergroup.readUsergroup(db, usergroup);
			session.set("login_page", myusergroup.getLoginPage());
			if (request.getMethod().equals("GET")) {
				String REQUEST_URI = "";
				if (request.getRequestURI() != null) REQUEST_URI += request.getRequestURI();
				String QUERY_STRING = "";
				if (request.getQueryString() != null) QUERY_STRING += request.getQueryString();
				response.sendRedirect(request.getProtocol() + request.getServerName() + request.getServerPort() + "/login_post.jsp?url=" + URLEncoder.encode(REQUEST_URI + "?" + Common.crlf_clean(QUERY_STRING)));
			} else {
				response.sendRedirect(request.getProtocol() + request.getServerName() + request.getServerPort() + "/login_post.jsp?url=" + URLEncoder.encode("/" + text.display("adminpath") + "/?error=" + text.display("error.login.expired")));
			}
			return false;
		} else {
			return true;
		}
	}



	public static boolean Usertype(String usertype, String session_usertype, String session_usertypes, Request request, Response response, Session session, DB db) {
		return Usertype(new Text(), usertype, session_usertype, session_usertypes, request, response, session, db);
	}
	public static boolean Usertype(Text text, String usertype, String session_usertype, String session_usertypes, Request request, Response response, Session session, DB db) {
		if ((! session_usertype.equals(usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + usertype + "|") >= 0))) {
			Usertype myusertype = new Usertype();
			myusertype.readUsertype(db, usertype);
			session.set("login_page", myusertype.getLoginPage());
			if (request.getMethod().equals("GET")) {
				String REQUEST_URI = "";
				if (request.getRequestURI() != null) REQUEST_URI += request.getRequestURI();
				String QUERY_STRING = "";
				if (request.getQueryString() != null) QUERY_STRING += request.getQueryString();
				response.sendRedirect(request.getProtocol() + request.getServerName() + request.getServerPort() + "/login_post.jsp?url=" + URLEncoder.encode(REQUEST_URI + "?" + Common.crlf_clean(QUERY_STRING)));
			} else {
				response.sendRedirect(request.getProtocol() + request.getServerName() + request.getServerPort() + "/login_post.jsp?url=" + URLEncoder.encode("/" + text.display("adminpath") + "/?error=" + text.display("error.login.expired")));
			}
			return false;
		} else {
			return true;
		}
	}



	public static boolean UsergroupOrUsertype(String usergroup, String session_usergroup, String session_usergroups, String usertype, String session_usertype, String session_usertypes, Request request, Response response, Session session, DB db) {
		return UsergroupOrUsertype(new Text(), usergroup, session_usergroup, session_usergroups, usertype, session_usertype, session_usertypes, request, response, session, db);
	}
	public static boolean UsergroupOrUsertype(Text text, String usergroup, String session_usergroup, String session_usergroups, String usertype, String session_usertype, String session_usertypes, Request request, Response response, Session session, DB db) {
		if ((! session_usergroup.equals(usergroup)) && (! session_usertype.equals(usertype)) && (! (("|" + session_usergroups + "|").indexOf("|" + usergroup + "|") >= 0)) && (! (("|" + session_usertypes + "|").indexOf("|" + usertype + "|") >= 0))) {
			if ((! session_usertype.equals(usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + usertype + "|") >= 0))) {
				Usertype myusertype = new Usertype();
				myusertype.readUsertype(db, usertype);
				if (! myusertype.getLoginPage().equals("")) {
					session.set("login_page", myusertype.getLoginPage());
				}
			}
			if ((! session_usergroup.equals(usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + usergroup + "|") >= 0))) {
				Usergroup myusergroup = new Usergroup();
				myusergroup.readUsergroup(db, usergroup);
				if (! myusergroup.getLoginPage().equals("")) {
					session.set("login_page", myusergroup.getLoginPage());
				}
			}
			if (request.getMethod().equals("GET")) {
				String REQUEST_URI = "";
				if (request.getRequestURI() != null) REQUEST_URI += request.getRequestURI();
				String QUERY_STRING = "";
				if (request.getQueryString() != null) QUERY_STRING += request.getQueryString();
				response.sendRedirect(request.getProtocol() + request.getServerName() + request.getServerPort() + "/login_post.jsp?url=" + URLEncoder.encode(REQUEST_URI + "?" + Common.crlf_clean(QUERY_STRING)));
			} else {
				response.sendRedirect(request.getProtocol() + request.getServerName() + request.getServerPort() + "/login_post.jsp?url=" + URLEncoder.encode("/" + text.display("adminpath") + "/?error=" + text.display("error.login.expired")));
			}
			return false;
		} else {
			return true;
		}
	}



	public static boolean Administrator(String session_username, String config_superadmin, String session_administrator, Request request, Response response, String database, String session_database) {
		return Administrator(new Text(), session_username, config_superadmin, session_administrator, request, response, database, session_database);
	}
	public static boolean Administrator(Text text, String session_username, String config_superadmin, String session_administrator, Request request, Response response, String database, String session_database) {
		if (session_username == null) session_username = "";
		if (session_administrator == null) session_administrator = "";
		if (config_superadmin.equals("")) {
			return true;
		} else if ((! session_database.equals(database)) || ((! session_username.equals(config_superadmin)) && (! config_superadmin.equals("")) && (! session_administrator.equals("administrator")))) {
			if (request.getMethod().equals("GET")) {
				String REQUEST_URI = "";
				if (request.getServletPath() != null) REQUEST_URI += request.getServletPath();
				String QUERY_STRING = "";
				if (request.getQueryString() != null) QUERY_STRING += request.getQueryString();
				response.sendRedirect(request.getProtocol() + request.getServerName() + request.getServerPort() + "/" + text.display("adminpath") + "/login_post.jsp?url=" + URLEncoder.encode(REQUEST_URI + "?" + Common.crlf_clean(QUERY_STRING)));
			} else {
				response.sendRedirect(request.getProtocol() + request.getServerName() + request.getServerPort() + "/" + text.display("adminpath") + "/login_post.jsp?url=" + URLEncoder.encode("/" + text.display("adminpath") + "/?error=" + text.display("error.login.expired")));
			}
			return false;
		} else {
			return true;
		}
	}
	public static boolean isAdministrator(String session_username, String config_superadmin, String session_administrator, String database, String session_database) {
		if (session_username == null) session_username = "";
		if (session_administrator == null) session_administrator = "";
		if (config_superadmin.equals("")) {
			return true;
		} else if ((! session_database.equals(database)) || ((! session_username.equals(config_superadmin)) && (! config_superadmin.equals("")) && (! session_administrator.equals("administrator")))) {
			return false;
		} else {
			return true;
		}
	}



	public static boolean AdministratorUsergroup(String session_username, String config_superadmin, String session_administrator, String usergroup, String session_usergroup, String session_usergroups, Request request, Response response, String database, String session_database) {
		return AdministratorUsergroup(new Text(), session_username, config_superadmin, session_administrator, usergroup, session_usergroup, session_usergroups, request, response, database, session_database);
	}
	public static boolean AdministratorUsergroup(Text text, String session_username, String config_superadmin, String session_administrator, String usergroup, String session_usergroup, String session_usergroups, Request request, Response response, String database, String session_database) {
		if ((! session_database.equals(database)) || ((! session_username.equals(config_superadmin)) && ((! session_administrator.equals("administrator")) || ((! session_usergroup.equals(usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + usergroup + "|") >= 0)))))) {
			if (request.getMethod().equals("GET")) {
				String REQUEST_URI = "";
				if (request.getRequestURI() != null) REQUEST_URI += request.getRequestURI();
				String QUERY_STRING = "";
				if (request.getQueryString() != null) QUERY_STRING += request.getQueryString();
				response.sendRedirect(request.getProtocol() + request.getServerName() + request.getServerPort() + "/" + text.display("adminpath") + "/login_post.jsp?url=" + URLEncoder.encode(REQUEST_URI + "?" + Common.crlf_clean(QUERY_STRING)));
			} else {
				response.sendRedirect(request.getProtocol() + request.getServerName() + request.getServerPort() + "/" + text.display("adminpath") + "/login_post.jsp?url=" + URLEncoder.encode("/" + text.display("adminpath") + "/?error=" + text.display("error.login.expired")));
			}
			return false;
		} else {
			return true;
		}
	}
	public static boolean isAdministratorUsergroup(String session_username, String config_superadmin, String session_administrator, String usergroup, String session_usergroup, String session_usergroups, String database, String session_database) {
		if ((! session_database.equals(database)) || ((! session_username.equals(config_superadmin)) && ((! session_administrator.equals("administrator")) || ((! session_usergroup.equals(usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + usergroup + "|") >= 0)))))) {
			return false;
		} else {
			return true;
		}
	}



	public static boolean AdministratorUsertype(String session_username, String config_superadmin, String session_administrator, String usertype, String session_usertype, String session_usertypes, Request request, Response response, String database, String session_database) {
		return AdministratorUsertype(new Text(), session_username, config_superadmin, session_administrator, usertype, session_usertype, session_usertypes, request, response, database, session_database);
	}
	public static boolean AdministratorUsertype(Text text, String session_username, String config_superadmin, String session_administrator, String usertype, String session_usertype, String session_usertypes, Request request, Response response, String database, String session_database) {
		if ((! session_database.equals(database)) || ((! session_username.equals(config_superadmin)) && ((! session_administrator.equals("administrator")) || ((! session_usertype.equals(usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + usertype + "|") >= 0)))))) {
			if (request.getMethod().equals("GET")) {
				String REQUEST_URI = "";
				if (request.getRequestURI() != null) REQUEST_URI += request.getRequestURI();
				String QUERY_STRING = "";
				if (request.getQueryString() != null) QUERY_STRING += request.getQueryString();
				response.sendRedirect(request.getProtocol() + request.getServerName() + request.getServerPort() + "/" + text.display("adminpath") + "/login_post.jsp?url=" + URLEncoder.encode(REQUEST_URI + "?" + Common.crlf_clean(QUERY_STRING)));
			} else {
				response.sendRedirect(request.getProtocol() + request.getServerName() + request.getServerPort() + "/" + text.display("adminpath") + "/login_post.jsp?url=" + URLEncoder.encode("/" + text.display("adminpath") + "/?error=" + text.display("error.login.expired")));
			}
			return false;
		} else {
			return true;
		}
	}
	public static boolean isAdministratorUsertype(String session_username, String config_superadmin, String session_administrator, String usertype, String session_usertype, String session_usertypes, String database, String session_database) {
		if ((! session_database.equals(database)) || ((! session_username.equals(config_superadmin)) && ((! session_administrator.equals("administrator")) || ((! session_usertype.equals(usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + usertype + "|") >= 0)))))) {
			return false;
		} else {
			return true;
		}
	}



	public static boolean SuperAdministrator(String session_username, String config_superadmin, Request request, Response response, String database, String session_database) {
		return SuperAdministrator(new Text(), session_username, config_superadmin, request, response, database, session_database);
	}
	public static boolean SuperAdministrator(Text text, String session_username, String config_superadmin, Request request, Response response, String database, String session_database) {
		if (session_username == null) session_username = "";
		if (config_superadmin.equals("")) {
			return true;
		} else if (((! session_database.equals(database)) && (! database.equals(""))) || ((! session_username.equals(config_superadmin)) && (! config_superadmin.equals("")))) {
			if (request.getMethod().equals("GET")) {
				String REQUEST_URI = "";
				if (request.getRequestURI() != null) REQUEST_URI += request.getRequestURI();
				String QUERY_STRING = "";
				if (request.getQueryString() != null) QUERY_STRING += request.getQueryString();
				response.sendRedirect(request.getProtocol() + request.getServerName() + request.getServerPort() + "/" + text.display("adminpath") + "/login_post.jsp?url=" + URLEncoder.encode(REQUEST_URI + "?" + Common.crlf_clean(QUERY_STRING)));
			} else {
				response.sendRedirect(request.getProtocol() + request.getServerName() + request.getServerPort() + "/" + text.display("adminpath") + "/login_post.jsp?url=" + URLEncoder.encode("/" + text.display("adminpath") + "/?error=" + text.display("error.login.expired")));
			}
			return false;
		} else {
			return true;
		}
	}
	public static boolean isSuperAdministrator(String session_username, String config_superadmin, String database, String session_database) {
		if (session_username == null) session_username = "";
		if (config_superadmin.equals("")) {
			return true;
		} else if (((! session_database.equals(database)) && (! database.equals(""))) || ((! session_username.equals(config_superadmin)) && (! config_superadmin.equals("")))) {
			return false;
		} else {
			return true;
		}
	}



	public static boolean checkUserAccessRestrictions(boolean accesspermission, Content mycontent, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		return checkUserAccessRestrictions(new Text(), accesspermission, mycontent, mysession, myrequest, myresponse, myconfig, db);
	}
	public static boolean checkUserAccessRestrictions(Text text, boolean accesspermission, Content mycontent, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		if ((mysession.get("mode").equals("preview")) || (mysession.get("mode").equals("admin"))) {
			accesspermission = accesspermission && mycontent.getUser();
		} else if ((! mycontent.getUser()) && (! myconfig.get(db, "use_accessrestrictions").equals("")) && (! myconfig.get(db, "use_accessrestrictions").equals("none"))) {
			if (myconfig.get(db, "accessrestrictions").equals("or")) {
				if (accesspermission && ((mycontent.getUsersGroup().equals("*")) || (mycontent.getUsersType().equals("*")))) {
					accesspermission = RequireUser.User(text, mysession.get("username"), myrequest, myresponse, mysession, db);
				} else if (accesspermission && ((! mycontent.getUsersGroup().equals("")) && (! mycontent.getUsersType().equals("")))) {
					accesspermission = RequireUser.UsergroupOrUsertype(text, mycontent.getUsersGroup(), mysession.get("usergroup"), mysession.get("usergroups"), mycontent.getUsersType(), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, mysession, db);
				} else if (accesspermission && (! mycontent.getUsersGroup().equals(""))) {
					accesspermission = RequireUser.Usergroup(text, mycontent.getUsersGroup(), mysession.get("usergroup"), mysession.get("usergroups"), myrequest, myresponse, mysession, db);
				} else if (accesspermission && (! mycontent.getUsersType().equals(""))) {
					accesspermission = RequireUser.Usertype(text, mycontent.getUsersType(), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, mysession, db);
				}
				if ((accesspermission) && (myconfig.get(db, "use_useraccessrestrictions").equals("yes")) && (! mycontent.getUsersUsers().equals(""))) {
					accesspermission = RequireUser.Userid(text, mycontent.getUsersUsers(), myrequest, myresponse, mysession, db);
				}

				if (accesspermission && ((mycontent.getContentgroupUsersGroup().equals("*")) || (mycontent.getContentgroupUsersType().equals("*")))) {
					accesspermission = RequireUser.User(text, mysession.get("username"), myrequest, myresponse, mysession, db);
				} else if (accesspermission && ((! mycontent.getContentgroupUsersGroup().equals("")) && (! mycontent.getContentgroupUsersType().equals("")))) {
					accesspermission = RequireUser.UsergroupOrUsertype(text, mycontent.getContentgroupUsersGroup(), mysession.get("usergroup"), mysession.get("usergroups"), mycontent.getContentgroupUsersType(), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, mysession, db);
				} else if (accesspermission && (! mycontent.getContentgroupUsersGroup().equals(""))) {
					accesspermission = RequireUser.Usergroup(text, mycontent.getContentgroupUsersGroup(), mysession.get("usergroup"), mysession.get("usergroups"), myrequest, myresponse, mysession, db);
				} else if (accesspermission && (! mycontent.getContentgroupUsersType().equals(""))) {
					accesspermission = RequireUser.Usertype(text, mycontent.getContentgroupUsersType(), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, mysession, db);
				}
				if ((accesspermission) && (myconfig.get(db, "use_useraccessrestrictions").equals("yes")) && (! mycontent.getContentgroupUsersUsers().equals(""))) {
					accesspermission = RequireUser.Userid(text, mycontent.getContentgroupUsersUsers(), myrequest, myresponse, mysession, db);
				}

				if (accesspermission && ((mycontent.getContenttypeUsersGroup().equals("*")) || (mycontent.getContenttypeUsersType().equals("*")))) {
					accesspermission = RequireUser.User(text, mysession.get("username"), myrequest, myresponse, mysession, db);
				} else if (accesspermission && ((! mycontent.getContenttypeUsersGroup().equals("")) && (! mycontent.getContenttypeUsersType().equals("")))) {
					accesspermission = RequireUser.UsergroupOrUsertype(text, mycontent.getContenttypeUsersGroup(), mysession.get("usergroup"), mysession.get("usergroups"), mycontent.getContenttypeUsersType(), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, mysession, db);
				} else if (accesspermission && (! mycontent.getContenttypeUsersGroup().equals(""))) {
					accesspermission = RequireUser.Usergroup(text, mycontent.getContenttypeUsersGroup(), mysession.get("usergroup"), mysession.get("usergroups"), myrequest, myresponse, mysession, db);
				} else if (accesspermission && (! mycontent.getContenttypeUsersType().equals(""))) {
					accesspermission = RequireUser.Usertype(text, mycontent.getContenttypeUsersType(), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, mysession, db);
				}
				if ((accesspermission) && (myconfig.get(db, "use_useraccessrestrictions").equals("yes")) && (! mycontent.getContenttypeUsersUsers().equals(""))) {
					accesspermission = RequireUser.Userid(text, mycontent.getContenttypeUsersUsers(), myrequest, myresponse, mysession, db);
				}
			} else {
				if (accesspermission && (mycontent.getUsersType().equals("*"))) {
					accesspermission = RequireUser.User(text, mysession.get("username"), myrequest, myresponse, mysession, db);
				} else if (accesspermission && (! mycontent.getUsersType().equals(""))) {
					accesspermission = RequireUser.Usertype(text, mycontent.getUsersType(), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, mysession, db);
				}
				if (accesspermission && (mycontent.getUsersGroup().equals("*"))) {
					accesspermission = RequireUser.User(text, mysession.get("username"), myrequest, myresponse, mysession, db);
				} else if (accesspermission && (! mycontent.getUsersGroup().equals(""))) {
					accesspermission = RequireUser.Usergroup(text, mycontent.getUsersGroup(), mysession.get("usergroup"), mysession.get("usergroups"), myrequest, myresponse, mysession, db);
				}
				if ((accesspermission) && (myconfig.get(db, "use_useraccessrestrictions").equals("yes")) && (! mycontent.getUsersUsers().equals(""))) {
					accesspermission = RequireUser.Userid(text, mycontent.getUsersUsers(), myrequest, myresponse, mysession, db);
				}

				if (accesspermission && (mycontent.getContentgroupUsersType().equals("*"))) {
					accesspermission = RequireUser.User(text, mysession.get("username"), myrequest, myresponse, mysession, db);
				} else if (accesspermission && (! mycontent.getContentgroupUsersType().equals(""))) {
					accesspermission = RequireUser.Usertype(text, mycontent.getContentgroupUsersType(), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, mysession, db);
				}
				if (accesspermission && (mycontent.getContentgroupUsersGroup().equals("*"))) {
					accesspermission = RequireUser.User(text, mysession.get("username"), myrequest, myresponse, mysession, db);
				} else if (accesspermission && (! mycontent.getContentgroupUsersGroup().equals(""))) {
					accesspermission = RequireUser.Usergroup(text, mycontent.getContentgroupUsersGroup(), mysession.get("usergroup"), mysession.get("usergroups"), myrequest, myresponse, mysession, db);
				}
				if ((accesspermission) && (myconfig.get(db, "use_useraccessrestrictions").equals("yes")) && (! mycontent.getContentgroupUsersUsers().equals(""))) {
					accesspermission = RequireUser.Userid(text, mycontent.getContentgroupUsersUsers(), myrequest, myresponse, mysession, db);
				}

				if (accesspermission && (mycontent.getContenttypeUsersType().equals("*"))) {
					accesspermission = RequireUser.User(text, mysession.get("username"), myrequest, myresponse, mysession, db);
				} else if (accesspermission && (! mycontent.getContenttypeUsersType().equals(""))) {
					accesspermission = RequireUser.Usertype(text, mycontent.getContenttypeUsersType(), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, mysession, db);
				}
				if (accesspermission && (mycontent.getContenttypeUsersGroup().equals("*"))) {
					accesspermission = RequireUser.User(text, mysession.get("username"), myrequest, myresponse, mysession, db);
				} else if (accesspermission && (! mycontent.getContenttypeUsersGroup().equals(""))) {
					accesspermission = RequireUser.Usergroup(text, mycontent.getContenttypeUsersGroup(), mysession.get("usergroup"), mysession.get("usergroups"), myrequest, myresponse, mysession, db);
				}
				if ((accesspermission) && (myconfig.get(db, "use_useraccessrestrictions").equals("yes")) && (! mycontent.getContenttypeUsersUsers().equals(""))) {
					accesspermission = RequireUser.Userid(text, mycontent.getContenttypeUsersUsers(), myrequest, myresponse, mysession, db);
				}
			}
			if (accesspermission) {
				if ((myconfig.get(db, "website_users_type").equals("*")) && (mysession.get("username").equals(""))) {
					accesspermission = false;
				} else if ((! myconfig.get(db, "website_users_type").equals("*")) && (! myconfig.get(db, "website_users_type").equals("")) && (! myconfig.get(db, "website_users_type").equals(mysession.get("usertype"))) && (! (("|" + mysession.get("usertypes") + "|").indexOf("|" + myconfig.get(db, "website_users_type") + "|") >= 0))) {
					accesspermission = false;
				}
				if ((myconfig.get(db, "website_users_group").equals("*")) && (mysession.get("username").equals(""))) {
					accesspermission = false;
				} else if ((! myconfig.get(db, "website_users_group").equals("*")) && (! myconfig.get(db, "website_users_group").equals("")) && (! myconfig.get(db, "website_users_group").equals(mysession.get("usergroup"))) && (! (("|" + mysession.get("usergroups") + "|").indexOf("|" + myconfig.get(db, "website_users_group") + "|") >= 0))) {
					accesspermission = false;
				}
				if (myconfig.get(db, "accessrestrictions").equals("or")) {
					if (((! myconfig.get(db, "website_users_type").equals("")) && ((myconfig.get(db, "website_users_type").equals(mysession.get("usertype"))) || (("|" + mysession.get("usertypes") + "|").indexOf("|" + myconfig.get(db, "website_users_type") + "|") >= 0))) || ((! myconfig.get(db, "website_users_group").equals("")) && ((myconfig.get(db, "website_users_group").equals(mysession.get("usergroup"))) || (("|" + mysession.get("usergroups") + "|").indexOf("|" + myconfig.get(db, "website_users_group") + "|") >= 0)))) {
						accesspermission = true;
					}
				}
			}
		}
		return accesspermission;
	}



	public static boolean checkDataUserAccessRestrictions(boolean accesspermission, Databases database, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		return checkDataUserAccessRestrictions(new Text(), accesspermission, database, mysession, myrequest, myresponse, myconfig, db);
	}
	public static boolean checkDataUserAccessRestrictions(Text text, boolean accesspermission, Databases database, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		if ((mysession.get("mode").equals("preview")) || (mysession.get("mode").equals("admin"))) {
			accesspermission = accesspermission && database.getUser();
		} else if ((! database.getUser()) && (! myconfig.get(db, "use_accessrestrictions").equals("")) && (! myconfig.get(db, "use_accessrestrictions").equals("none"))) {
			if (myconfig.get(db, "accessrestrictions").equals("or")) {
				if (accesspermission && ((database.getUsersGroup().equals("*")) || (database.getUsersType().equals("*")))) {
					accesspermission = RequireUser.User(text, mysession.get("username"), myrequest, myresponse, mysession, db);
				} else if (accesspermission && ((! database.getUsersGroup().equals("")) && (! database.getUsersType().equals("")))) {
					accesspermission = RequireUser.UsergroupOrUsertype(text, database.getUsersGroup(), mysession.get("usergroup"), mysession.get("usergroups"), database.getUsersType(), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, mysession, db);
				} else if (accesspermission && (! database.getUsersGroup().equals(""))) {
					accesspermission = RequireUser.Usergroup(text, database.getUsersGroup(), mysession.get("usergroup"), mysession.get("usergroups"), myrequest, myresponse, mysession, db);
				} else if (accesspermission && (! database.getUsersType().equals(""))) {
					accesspermission = RequireUser.Usertype(text, database.getUsersType(), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, mysession, db);
				}
			} else {
				if (accesspermission && (database.getUsersType().equals("*"))) {
					accesspermission = RequireUser.User(text, mysession.get("username"), myrequest, myresponse, mysession, db);
				} else if (accesspermission && (! database.getUsersType().equals(""))) {
					accesspermission = RequireUser.Usertype(text, database.getUsersType(), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, mysession, db);
				}
				if (accesspermission && (database.getUsersGroup().equals("*"))) {
					accesspermission = RequireUser.User(text, mysession.get("username"), myrequest, myresponse, mysession, db);
				} else if (accesspermission && (! database.getUsersGroup().equals(""))) {
					accesspermission = RequireUser.Usergroup(text, database.getUsersGroup(), mysession.get("usergroup"), mysession.get("usergroups"), myrequest, myresponse, mysession, db);
				}
			}
			if ((accesspermission) && (myconfig.get(db, "use_useraccessrestrictions").equals("yes")) && (! database.getUsersUsers().equals(""))) {
				accesspermission = RequireUser.Userid(text, database.getUsersUsers(), myrequest, myresponse, mysession, db);
			}
			if (accesspermission) {
				if ((myconfig.get(db, "website_users_type").equals("*")) && (mysession.get("username").equals(""))) {
					accesspermission = false;
				} else if ((! myconfig.get(db, "website_users_type").equals("*")) && (! myconfig.get(db, "website_users_type").equals("")) && (! myconfig.get(db, "website_users_type").equals(mysession.get("usertype"))) && (! (("|" + mysession.get("usertypes") + "|").indexOf("|" + myconfig.get(db, "website_users_type") + "|") >= 0))) {
					accesspermission = false;
				}
				if ((myconfig.get(db, "website_users_group").equals("*")) && (mysession.get("username").equals(""))) {
					accesspermission = false;
				} else if ((! myconfig.get(db, "website_users_group").equals("*")) && (! myconfig.get(db, "website_users_group").equals("")) && (! myconfig.get(db, "website_users_group").equals(mysession.get("usergroup"))) && (! (("|" + mysession.get("usergroups") + "|").indexOf("|" + myconfig.get(db, "website_users_group") + "|") >= 0))) {
					accesspermission = false;
				}
				if (myconfig.get(db, "accessrestrictions").equals("or")) {
					if (((! myconfig.get(db, "website_users_type").equals("")) && ((myconfig.get(db, "website_users_type").equals(mysession.get("usertype"))) || (("|" + mysession.get("usertypes") + "|").indexOf("|" + myconfig.get(db, "website_users_type") + "|") >= 0))) || ((! myconfig.get(db, "website_users_group").equals("")) && ((myconfig.get(db, "website_users_group").equals(mysession.get("usergroup"))) || (("|" + mysession.get("usergroups") + "|").indexOf("|" + myconfig.get(db, "website_users_group") + "|") >= 0)))) {
						accesspermission = true;
					}
				}
			}
		}
		return accesspermission;
	}



	public static boolean OrderAdministrator(Text text, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! myconfig.get(db, "order_admin_users_type").equals("")) {
			accesspermission = accesspermission && AdministratorUsertype(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myconfig.get(db, "order_admin_users_type"), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		}
		if (! myconfig.get(db, "order_admin_users_group").equals("")) {
			accesspermission = accesspermission && AdministratorUsergroup(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myconfig.get(db, "order_admin_users_group"), mysession.get("usergroup"), mysession.get("usergroups"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		}
		return accesspermission;
	}
	public static boolean isOrderAdministrator(Session mysession, Configuration myconfig, DB db) {
		boolean accesspermission = isAdministrator(mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), db.getDatabase(), mysession.get("database"));
		if (! myconfig.get(db, "order_admin_users_type").equals("")) {
			accesspermission = accesspermission && isAdministratorUsertype(mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myconfig.get(db, "order_admin_users_type"), mysession.get("usertype"), mysession.get("usertypes"), db.getDatabase(), mysession.get("database"));
		}
		if (! myconfig.get(db, "order_admin_users_group").equals("")) {
			accesspermission = accesspermission && isAdministratorUsergroup(mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myconfig.get(db, "order_admin_users_group"), mysession.get("usergroup"), mysession.get("usergroups"), db.getDatabase(), mysession.get("database"));
		}
		return accesspermission;
	}



	public static boolean UsageAdministrator(Text text, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! myconfig.get(db, "statistics_admin_users_type").equals("")) {
			accesspermission = accesspermission && AdministratorUsertype(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myconfig.get(db, "statistics_admin_users_type"), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		}
		if (! myconfig.get(db, "statistics_admin_users_group").equals("")) {
			accesspermission = accesspermission && AdministratorUsergroup(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myconfig.get(db, "statistics_admin_users_group"), mysession.get("usergroup"), mysession.get("usergroups"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		}
		return accesspermission;
	}
	public static boolean isUsageAdministrator(Session mysession, Configuration myconfig, DB db) {
		boolean accesspermission = isAdministrator(mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), db.getDatabase(), mysession.get("database"));
		if (! myconfig.get(db, "statistics_admin_users_type").equals("")) {
			accesspermission = accesspermission && isAdministratorUsertype(mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myconfig.get(db, "statistics_admin_users_type"), mysession.get("usertype"), mysession.get("usertypes"), db.getDatabase(), mysession.get("database"));
		}
		if (! myconfig.get(db, "statistics_admin_users_group").equals("")) {
			accesspermission = accesspermission && isAdministratorUsergroup(mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myconfig.get(db, "statistics_admin_users_group"), mysession.get("usergroup"), mysession.get("usergroups"), db.getDatabase(), mysession.get("database"));
		}
		return accesspermission;
	}



	public static boolean ExperienceAdministrator(Text text, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! myconfig.get(db, "experience_admin_users_type").equals("")) {
			accesspermission = accesspermission && AdministratorUsertype(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myconfig.get(db, "experience_admin_users_type"), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		}
		if (! myconfig.get(db, "experience_admin_users_group").equals("")) {
			accesspermission = accesspermission && AdministratorUsergroup(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myconfig.get(db, "experience_admin_users_group"), mysession.get("usergroup"), mysession.get("usergroups"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		}
		return accesspermission;
	}
	public static boolean isExperienceAdministrator(Session mysession, Configuration myconfig, DB db) {
		boolean accesspermission = isAdministrator(mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), db.getDatabase(), mysession.get("database"));
		if (! myconfig.get(db, "experience_admin_users_type").equals("")) {
			accesspermission = accesspermission && isAdministratorUsertype(mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myconfig.get(db, "experience_admin_users_type"), mysession.get("usertype"), mysession.get("usertypes"), db.getDatabase(), mysession.get("database"));
		}
		if (! myconfig.get(db, "experience_admin_users_group").equals("")) {
			accesspermission = accesspermission && isAdministratorUsergroup(mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myconfig.get(db, "experience_admin_users_group"), mysession.get("usergroup"), mysession.get("usergroups"), db.getDatabase(), mysession.get("database"));
		}
		return accesspermission;
	}



}
