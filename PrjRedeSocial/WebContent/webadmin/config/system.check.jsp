
<br>
<table>
<%

boolean errors = false;
String myerror = "";
String status = "";

//status = mytext.display("intro.config.quickstart.system.status.ok");
//status = mytext.display("intro.config.quickstart.system.status.note");
//status = mytext.display("intro.config.quickstart.system.status.error");


%><tr><td>&nbsp;</td></tr><tr><th valign="top" align="left" colspan="5"><%= mytext.display("intro.config.quickstart.system.wcminstallation") %></th></tr><%


myerror = "";
status = mytext.display("intro.config.quickstart.system.status.ok");
String docroot = DOCUMENT_ROOT;
%><tr><td valign="top"><%= status %></td><td>&nbsp;</td><td valign="top" align="left"><%= mytext.display("intro.config.quickstart.system.docroot") %>:</td><td>&nbsp;</td><td valign="top"><%= docroot %><%= myerror %></td></tr><%


myerror = "";
status =  mytext.display("intro.config.quickstart.system.status.ok");
String abspath = Common.getRealPath(servletcontext, myrequest.getServletPath()).replaceAll("index.config.jsp", "");
if (! abspath.startsWith(docroot)) {
	status = mytext.display("intro.config.quickstart.system.status.error");
	myerror = "<br><font color=\"red\">" + mytext.display("intro.config.quickstart.system.abspath.error") + Common.getRealPath(servletcontext, "/" + mytext.display("adminpath") + "/");
	errors = true;
}
%><tr><td valign="top"><%= status %></td><td>&nbsp;</td><td valign="top" align="left"><%= mytext.display("intro.config.quickstart.system.abspath") %>:</td><td>&nbsp;</td><td valign="top"><%= html.encode(abspath) %><%= myerror %></td></tr><%


myerror = "";
status =  mytext.display("intro.config.quickstart.system.status.ok");
String languagefile = Common.getRealPath(servletcontext, "/WEB-INF/classes/hardcore.properties");
if (! Common.fileExists(languagefile)) {
	status =  mytext.display("intro.config.quickstart.system.status.error");
	myerror = "<br><font color=\"red\">" + mytext.display("intro.config.quickstart.system.languagefile.missing.error") + languagefile;
	errors = true;
}
%><tr><td valign="top"><%= status %></td><td>&nbsp;</td><td valign="top" align="left"><%= mytext.display("intro.config.quickstart.system.languagefile") %>:</td><td>&nbsp;</td><td valign="top"><%= html.encode(languagefile) %><%= myerror %></td></tr><%


myerror = "";
status =  mytext.display("intro.config.quickstart.system.status.ok");
String relpath = myrequest.getServletPath().replaceAll("index.config.jsp", "");
if (! relpath.equals("/" + mytext.display("adminpath") + "/")) {
	status =  mytext.display("intro.config.quickstart.system.status.error");
	myerror = "<br><font color=\"red\">" + mytext.display("intro.config.quickstart.system.relpath.error") + myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + mytext.display("adminpath") + "/";
	errors = true;
}
%><tr><td valign="top"><%= status %></td><td>&nbsp;</td><td valign="top" align="left"><%= mytext.display("intro.config.quickstart.system.relpath") %>:</td><td>&nbsp;</td><td valign="top"><%= myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + html.encode(relpath) %><%= myerror %></td></tr><%


%><tr><td>&nbsp;</td></tr><tr><th valign="top" align="left" colspan="5"><%= mytext.display("intro.config.quickstart.system.wcmconfiguration") %></th></tr><%


myerror = "";
status =  mytext.display("intro.config.quickstart.system.status.ok");
String webadmininifile = "" + inifile;
inidb.WriteINI(webadmininifile, "test", "test", "");
String test1 = inidb.ReadINI(webadmininifile, "systemcheck", "default_version", "");
inidb.DeleteINI(webadmininifile, "test", "test", "");
inidb.WriteINI(webadmininifile, "systemcheck", "default_version", "test");
String test2 = inidb.ReadINI(webadmininifile, "systemcheck", "default_version", "");
inidb.DeleteINI(webadmininifile, "systemcheck", "default_version", "test");
if (! Common.fileExists(webadmininifile)) {
	status =  mytext.display("intro.config.quickstart.system.status.error");
	myerror = "<br><font color=\"red\">" + mytext.display("intro.config.quickstart.system.webadmininifile.missing.error") + webadmininifile;
	errors = true;
} else {
	if ((! test1.equals("")) || (! test2.equals("test"))) {
		if ((inidb.ReadINI(webadmininifile, "default", "database", "").equals("")) || (inidb.ReadINI(webadmininifile, "default", "URLrootpath", "").equals(""))) {
			status =  mytext.display("intro.config.quickstart.system.status.error");
			myerror = "<br><font color=\"red\">" + mytext.display("intro.config.quickstart.system.webadmininifile.permission.error") + webadmininifile;
			errors = true;
		} else {
			status =  mytext.display("intro.config.quickstart.system.status.note");
			myerror = "<br><font color=\"orange\">" + mytext.display("intro.config.quickstart.system.webadmininifile.permission.note") + webadmininifile;
		}
	}
}
%><tr><td valign="top"><%= status %></td><td>&nbsp;</td><td valign="top" align="left"><%= mytext.display("intro.config.quickstart.system.webadmininifile") %>:</td><td>&nbsp;</td><td valign="top"><%= html.encode(webadmininifile) %><%= myerror %></td></tr><%


myerror = "";
status =  mytext.display("intro.config.quickstart.system.status.ok");
String websiteinifile = DOCUMENT_ROOT + java.io.File.separator + "ini.jsp";
inidb.WriteJSP(websiteinifile, "systemcheck", "default_version", "");
test1 = inidb.ReadJSP(websiteinifile, "systemcheck", "default_version", "");
inidb.DeleteJSP(websiteinifile, "systemcheck", "default_version", "");
inidb.WriteJSP(websiteinifile, "systemcheck", "default_version", "test");
test2 = inidb.ReadJSP(websiteinifile, "systemcheck", "default_version", "");
inidb.DeleteJSP(websiteinifile, "systemcheck", "default_version", "test");
if (! Common.fileExists(websiteinifile)) {
	status =  mytext.display("intro.config.quickstart.system.status.error");
	myerror = "<br><font color=\"red\">" + mytext.display("intro.config.quickstart.system.websiteinifile.missing.error") + websiteinifile;
	errors = true;
} else {
	if ((! test1.equals("")) || (! test2.equals("test"))) {
		if (inidb.ReadJSP(websiteinifile, "default", "ini_cache_database", "") == "") {
			status =  mytext.display("intro.config.quickstart.system.status.error");
			myerror = "<br><font color=\"red\">" + mytext.display("intro.config.quickstart.system.websiteinifile.permission.error") + websiteinifile;
			errors = true;
		} else {
			status =  mytext.display("intro.config.quickstart.system.status.note");
			myerror = "<br><font color=\"orange\">" + mytext.display("intro.config.quickstart.system.websiteinifile.permission.note") + websiteinifile;
		}
	}
}
%><tr><td valign="top"><%= status %></td><td>&nbsp;</td><td valign="top" align="left"><%= mytext.display("intro.config.quickstart.system.websiteinifile") %>:</td><td>&nbsp;</td><td valign="top"><%= html.encode(websiteinifile) %><%= myerror %></td></tr><%


myerror = "";
status =  mytext.display("intro.config.quickstart.system.status.ok");
String websitedefaultsfile = DOCUMENT_ROOT + java.io.File.separator + "defaults.jsp";
inidb.WriteJSP(websitedefaultsfile, "systemcheck", "default_version", "");
test1 = inidb.ReadJSP(websitedefaultsfile, "systemcheck", "default_version", "");
inidb.DeleteJSP(websitedefaultsfile, "systemcheck", "default_version", "");
inidb.WriteJSP(websitedefaultsfile, "systemcheck", "default_version", "test");
test2 = inidb.ReadJSP(websitedefaultsfile, "systemcheck", "default_version", "");
inidb.DeleteJSP(websitedefaultsfile, "systemcheck", "default_version", "test");
if (! Common.fileExists(websitedefaultsfile)) {
	status =  mytext.display("intro.config.quickstart.system.status.error");
	myerror = "<br><font color=\"red\">" + mytext.display("intro.config.quickstart.system.websitedefaultsfile.missing.error") + websitedefaultsfile;
	errors = true;
} else {
	if ((! test1.equals("")) || (! test2.equals("test"))) {
		if ((inidb.ReadJSP(websitedefaultsfile, "default", "database_version", "") == "") || (inidb.ReadJSP(websitedefaultsfile, "default", "default_page", "") == "")) {
			status =  mytext.display("intro.config.quickstart.system.status.error");
			myerror = "<br><font color=\"red\">" + mytext.display("intro.config.quickstart.system.websitedefaultsfile.permission.error") + websiteinifile;
			errors = true;
		} else {
			status =  mytext.display("intro.config.quickstart.system.status.note");
			myerror = "<br><font color=\"orange\">" + mytext.display("intro.config.quickstart.system.websitedefaultsfile.permission.note") + websiteinifile;
		}
	}
}
%><tr><td valign="top"><%= status %></td><td>&nbsp;</td><td valign="top" align="left"><%= mytext.display("intro.config.quickstart.system.websitedefaultsfile") %>:</td><td>&nbsp;</td><td valign="top"><%= html.encode(websitedefaultsfile) %><%= myerror %></td></tr><%


%><tr><td>&nbsp;</td></tr><tr><th valign="top" align="left" colspan="5"><%= mytext.display("intro.config.quickstart.system.filepermissions") %></th></tr><%


myerror = "";
status =  mytext.display("intro.config.quickstart.system.status.ok");
String rootfolder = DOCUMENT_ROOT;
if (! Common.folderExists(rootfolder)) {
	status =  mytext.display("intro.config.quickstart.system.status.error");
	myerror = "<br><font color=\"red\">" + mytext.display("intro.config.quickstart.system.rootfolder.missing.error") + rootfolder;
	errors = true;
} else {
	java.io.File myfh = java.io.File.createTempFile("test", "test", new java.io.File(rootfolder));
	if (myfh != null) {
		String testfile = "" + myfh.toString();
		Common.writeFile(testfile, "test");
		if (! Common.readFile(testfile).trim().equals("test")) {
			status =  mytext.display("intro.config.quickstart.system.status.error");
			myerror = "<br><font color=\"red\">" + mytext.display("intro.config.quickstart.system.rootfolder.permission.error") + rootfolder;
			errors = true;
		}
		Common.deleteFile(testfile);
	}
}
%><tr><td valign="top"><%= status %></td><td>&nbsp;</td><td valign="top" align="left"><%= mytext.display("intro.config.quickstart.system.rootfolder") %>:</td><td>&nbsp;</td><td valign="top"><%= html.encode(rootfolder) %><%= myerror %></td></tr><%



myerror = "";
status =  mytext.display("intro.config.quickstart.system.status.ok");
String uploadfolder = Common.getRealPath(servletcontext, myconfig.get(db, "URLrootpath") + myconfig.get(db, "URLuploadpath"));
if (! Common.folderExists(uploadfolder)) {
	status =  mytext.display("intro.config.quickstart.system.status.error");
	myerror = "<br><font color=\"red\">" + mytext.display("intro.config.quickstart.system.uploadfolder.missing.error") + uploadfolder;
	errors = true;
} else {
	java.io.File myfh = java.io.File.createTempFile("test", "test", new java.io.File(uploadfolder));
	if (myfh != null) {
		String testfile = "" + myfh.toString();
		Common.writeFile(testfile, "test");
		if (! Common.readFile(testfile).trim().equals("test")) {
			status =  mytext.display("intro.config.quickstart.system.status.error");
			myerror = "<br><font color=\"red\">" + mytext.display("intro.config.quickstart.system.uploadfolder.permission.error") + uploadfolder;
			errors = true;
		}
		Common.deleteFile(testfile);
	}
}
%><tr><td valign="top"><%= status %></td><td>&nbsp;</td><td valign="top" align="left"><%= mytext.display("intro.config.quickstart.system.uploadfolder") %>:</td><td>&nbsp;</td><td valign="top"><%= html.encode(uploadfolder) %><%= myerror %></td></tr><%



myerror = "";
status =  mytext.display("intro.config.quickstart.system.status.ok");
String imagefolder = Common.getRealPath(servletcontext, myconfig.get(db, "URLrootpath") + myconfig.get(db, "URLimagepath"));
if (! Common.folderExists(imagefolder)) {
	status =  mytext.display("intro.config.quickstart.system.status.error");
	myerror = "<br><font color=\"red\">" + mytext.display("intro.config.quickstart.system.imagefolder.missing.error") + imagefolder;
	errors = true;
} else {
	java.io.File myfh = java.io.File.createTempFile("test", "test", new java.io.File(imagefolder));
	if (myfh != null) {
		String testfile = "" + myfh.toString();
		Common.writeFile(testfile, "test");
		if (! Common.readFile(testfile).trim().equals("test")) {
			status =  mytext.display("intro.config.quickstart.system.status.error");
			myerror = "<br><font color=\"red\">" + mytext.display("intro.config.quickstart.system.imagefolder.permission.error") + imagefolder;
			errors = true;
		}
		Common.deleteFile(testfile);
	}
}
%><tr><td valign="top"><%= status %></td><td>&nbsp;</td><td valign="top" align="left"><%= mytext.display("intro.config.quickstart.system.imagefolder") %>:</td><td>&nbsp;</td><td valign="top"><%= html.encode(imagefolder) %><%= myerror %></td></tr><%



myerror = "";
status =  mytext.display("intro.config.quickstart.system.status.ok");
String filefolder = Common.getRealPath(servletcontext, myconfig.get(db, "URLrootpath") + myconfig.get(db, "URLfilepath"));
if (! Common.folderExists(filefolder)) {
	status =  mytext.display("intro.config.quickstart.system.status.error");
	myerror = "<br><font color=\"red\">" + mytext.display("intro.config.quickstart.system.filefolder.missing.error") + filefolder;
	errors = true;
} else {
	java.io.File myfh = java.io.File.createTempFile("test", "test", new java.io.File(filefolder));
	if (myfh != null) {
		String testfile = "" + myfh.toString();
		Common.writeFile(testfile, "test");
		if (! Common.readFile(testfile).trim().equals("test")) {
			status =  mytext.display("intro.config.quickstart.system.status.error");
			myerror = "<br><font color=\"red\">" + mytext.display("intro.config.quickstart.system.filefolder.permission.error") + filefolder;
			errors = true;
		}
		Common.deleteFile(testfile);
	}
}
%><tr><td valign="top"><%= status %></td><td>&nbsp;</td><td valign="top" align="left"><%= mytext.display("intro.config.quickstart.system.filefolder") %>:</td><td>&nbsp;</td><td valign="top"><%= html.encode(filefolder) %><%= myerror %></td></tr><%


%>
<%

String systemcheckfolder = Common.getRealPath(servletcontext, "/" + mytext.display("adminpath") + "/systemcheck/");
if (Common.folderExists(systemcheckfolder)) {
	java.io.File dir = new java.io.File(systemcheckfolder);
	String[] folders = dir.list();
	java.util.Arrays.sort(folders);
	String systemcheck = "";
	for (int i=0; i<folders.length; i++) {
		java.io.File folder = new java.io.File(systemcheckfolder + dir.separator + folders[i]);
		if ((folders[i].equals(".")) || (folders[i].equals(".."))) {
			// ignore
		} else if ((! folders[i].matches(".*\\.(aspx|php)$")) && (folder.isDirectory())) {
			%><tr><td>&nbsp;</td></tr><tr><th valign="top" align="left" colspan="5"><%= folders[i].replaceAll("\\.jsp$", "") %></th></tr><%
			dir = new java.io.File(systemcheckfolder + dir.separator + folders[i]);
			String[] files = dir.list();
			java.util.Arrays.sort(files);
			for (int j=0; j<files.length; j++) {
				java.io.File file = new java.io.File(systemcheckfolder + dir.separator + folders[i] + dir.separator + files[j]);
				if ((files[j].equals(".")) || (files[j].equals(".."))) {
					// ignore
				} else if ((files[j].endsWith(".jsp")) && (file.isFile())) {
					%><% myjspincludepage = "/" + mytext.display("adminpath") + "/systemcheck/" + folders[i] + "/" + files[j]; %><jsp:include page="<%= myjspincludepage %>" /><%
					systemcheck = "" + mysession.get("systemcheck");
					if (systemcheck.startsWith("ERROR:")) {
						systemcheck = "<font color=\"red\">" + systemcheck.replaceAll("^ERROR:", "");
						status =  mytext.display("intro.config.quickstart.system.status.error");
						errors = true;
					} else if (systemcheck.startsWith("NOTE:")) {
						systemcheck = "<font color=\"orange\">" + systemcheck.replaceAll("^NOTE:", "");
						status =  mytext.display("intro.config.quickstart.system.status.note");
					} else if (systemcheck.startsWith("OK:")) {
						systemcheck = "" + systemcheck.replaceAll("^OK:", "");
						status =  mytext.display("intro.config.quickstart.system.status.ok");
					}
					%><tr><td valign="top"><%= status %></td><td>&nbsp;</td><td valign="top" align="left"><%= files[j].replaceAll("\\.jsp$", "") %>:</td><td>&nbsp;</td><td valign="top"><%= systemcheck %></td></tr><%
				}
			}
		} else if ((folders[i].endsWith(".jsp")) && (folder.isFile())) {
			%><% myjspincludepage = "/" + mytext.display("adminpath") + "/systemcheck/" + folders[i]; %><jsp:include page="<%= myjspincludepage %>" /><%
			systemcheck = "" + mysession.get("systemcheck");
			if (systemcheck.startsWith("ERROR:")) {
				systemcheck = "<font color=\"red\">" + systemcheck.replaceAll("^ERROR:", "");
				status =  mytext.display("intro.config.quickstart.system.status.error");
				errors = true;
			} else if (systemcheck.startsWith("NOTE:")) {
				systemcheck = "<font color=\"orange\">" + systemcheck.replaceAll("^NOTE:", "");
				status =  mytext.display("intro.config.quickstart.system.status.note");
			} else if (systemcheck.startsWith("OK:")) {
				systemcheck = "" + systemcheck.replaceAll("^OK:", "");
				status =  mytext.display("intro.config.quickstart.system.status.ok");
			}
			%><tr><td>&nbsp;</td></tr><tr><th valign="top" align="left" colspan="5"><%= folders[i].replaceAll("\\.jsp$", "") %></th></tr><%
			%><tr><td valign="top"><%= status %></td><td>&nbsp;</td><td valign="top" align="left"><%= folders[i].replaceAll("\\.jsp$", "") %>:</td><td>&nbsp;</td><td valign="top"><%= systemcheck %></td></tr><%
		}
	}
}

%>
</table>
<br>
