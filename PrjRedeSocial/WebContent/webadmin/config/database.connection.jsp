<%@ page import="HardCore.UCconfigureSystem" %>
<%@ page import="HardCore.Currency" %>
<%@ page import="HardCore.DB" %>
<%@ page import="HardCore.Fileupload" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.User" %>
<%@ page import="HardCore.RequireUser" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCconfigureSystem configureSystem = new UCconfigureSystem(mytext);
	db = configureSystem.doDatabase(out, servletcontext, database, current_database, dummy_database, original_database, ini_database, inifile, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, mytext);
	database = configureSystem.getDatabase();
	current_database = configureSystem.getCurrentDatabase();
	String dummy_database2 = configureSystem.getDummyDatabase2();
	String dummy_database3 = configureSystem.getDummyDatabase3();
	myconfig = configureSystem.getConfig();
	String error = configureSystem.getError();
	Currency mycurrencies = configureSystem.getCurrencies();
	Page mypage = configureSystem.getPage();
	User myuser = configureSystem.getUser();
%>