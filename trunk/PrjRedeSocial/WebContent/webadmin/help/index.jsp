<%@ include file="../config.jsp" %>
<html>
<head>
<title><%= mytext.display("help.title") %></title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=<%= myconfig.get(db, "charset") %>">
<meta http-equiv="Generator" content="<%= mytext.display("product") %> <%= mytext.display("version") %>">
<meta http-equiv="Copyright" content="<%= mytext.display("copyright") %>">
<link rel="stylesheet" type="text/css" href="/<%= mytext.display("adminpath") %>/webadmin.css" /> 
<link rel="StyleSheet" type="text/css" href="/<%= mytext.display("adminpath") %>/dtree.css" />
<script type="text/javascript" src="/<%= mytext.display("adminpath") %>/dtree.js"></script>
</head>
<body bgcolor="#FFFFFF" text="#000000" link="#000000" vlink="#000000" alink="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr bgcolor="#cc0000">
    <td class="WCMheader" align="left" colspan="2"><%= mytext.display("logo") %></td>
    <td class="WCMtitle" align="right" colspan="2"><p><%= mytext.display("help.title") %></p></td>
  </tr>
  <tr>
    <td class="WCMsubheader" height="34" align="left" valign="middle" bgcolor="#000000" colspan="4"><%= mytext.display("name") %></td>
  </tr>
  <tr><td>&nbsp;</td></tr>
  <tr>
    <td width="50">&nbsp;</td>
    <td>
      <p><%= mytext.display("help.gettingstarted.text") %><br>
      <a href="/<%= mytext.display("adminpath") %>/GettingStarted.pdf"><%= mytext.display("help.gettingstarted.link") %></a>
      </p>
    </td>
    <td><img src="/<%= mytext.display("adminpath") %>/system3.gif"></td>
    <td width="50">&nbsp;</td>
  </tr>
<!--
  <tr><td>&nbsp;</td></tr>
  <tr>
    <td width="50">&nbsp;</td>
    <td>
      <p><%= mytext.display("help.userguide.text") %><br>
      <a href="/<%= mytext.display("adminpath") %>/UserGuide.pdf"><%= mytext.display("help.userguide.link") %></a>
      </p>
    </td>
    <td><img src="/<%= mytext.display("adminpath") %>/configuration3.gif"></td>
    <td width="50">&nbsp;</td>
  </tr>
-->
  <tr><td>&nbsp;</td></tr>
  <tr>
    <td width="50">&nbsp;</td>
    <td>
      <p><%= mytext.display("help.introductionoverview.text") %><br>
      <a href="/<%= mytext.display("adminpath") %>/IntroductionAndOverview.pdf"><%= mytext.display("help.introductionoverview.link") %></a>
      </p>
    </td>
    <td><img src="/<%= mytext.display("adminpath") %>/databases3.gif"></td>
    <td width="50">&nbsp;</td>
  </tr>
  <tr><td>&nbsp;</td></tr>
  <tr>
    <td width="50">&nbsp;</td>
    <td>
      <p><%= mytext.display("help.websiteeditorguide.text") %><br>
      <a href="/<%= mytext.display("adminpath") %>/WebsiteEditorGuide.pdf"><%= mytext.display("help.websiteeditorguide.link") %></a>
      </p>
    </td>
    <td><img src="/<%= mytext.display("adminpath") %>/content3.gif"></td>
    <td width="50">&nbsp;</td>
  </tr>
  <tr><td>&nbsp;</td></tr>
  <tr>
    <td width="50">&nbsp;</td>
    <td>
      <p><%= mytext.display("help.websiteadministratorguide.text") %><br>
      <a href="/<%= mytext.display("adminpath") %>/WebsiteAdministratorGuide.pdf"><%= mytext.display("help.websiteadministratorguide.link") %></a>
      </p>
    </td>
    <td><img src="/<%= mytext.display("adminpath") %>/content1.gif"></td>
    <td width="50">&nbsp;</td>
  </tr>
  <tr><td>&nbsp;</td></tr>
  <tr>
    <td width="50">&nbsp;</td>
    <td>
      <p><%= mytext.display("help.websitedeveloperguide.text") %><br>
      <a href="/<%= mytext.display("adminpath") %>/WebsiteDeveloperGuide.pdf"><%= mytext.display("help.websitedeveloperguide.link") %></a>
      </p>
    </td>
    <td><img src="/<%= mytext.display("adminpath") %>/configuration1.gif"></td>
    <td width="50">&nbsp;</td>
  </tr>
  <tr><td>&nbsp;</td></tr>
  <tr>
    <td width="50">&nbsp;</td>
    <td>
      <p><%= mytext.display("help.referenceguide.text") %><br>
      <a href="/<%= mytext.display("adminpath") %>/ReferenceGuide.pdf"><%= mytext.display("help.referenceguide.link") %></a>
      </p>
    </td>
    <td><img src="/<%= mytext.display("adminpath") %>/content2.gif"></td>
    <td width="50">&nbsp;</td>
  </tr>
  <tr><td>&nbsp;</td></tr>
  <tr>
    <td width="50">&nbsp;</td>
    <td>
      <p><%= mytext.display("help.configurationguide.text") %><br>
      <a href="/<%= mytext.display("adminpath") %>/ConfigurationGuide.pdf"><%= mytext.display("help.configurationguide.link") %></a>
      </p>
    </td>
    <td><img src="/<%= mytext.display("adminpath") %>/configuration3.gif"></td>
    <td width="50">&nbsp;</td>
  </tr>
  <tr><td>&nbsp;</td></tr>
  <tr>
    <td width="50">&nbsp;</td>
    <td>
      <p><%= mytext.display("help.hostingeditionguide.text") %><br>
      <a href="/<%= mytext.display("adminpath") %>/HostingEditionGuide.pdf"><%= mytext.display("help.hostingeditionguide.link") %></a>
      </p>
    </td>
    <td><img src="/<%= mytext.display("adminpath") %>/hosting3.gif"></td>
    <td width="50">&nbsp;</td>
  </tr>
  <tr><td>&nbsp;</td></tr>
  <tr>
    <td width="50">&nbsp;</td>
    <td>
      <p><%= mytext.display("help.programmingapiguide.text") %><br>
      <a href="/<%= mytext.display("adminpath") %>/ProgrammingAPIGuide.pdf"><%= mytext.display("help.programmingapiguide.link") %></a>
      </p>
    </td>
    <td><img src="/<%= mytext.display("adminpath") %>/files3.gif"></td>
    <td width="50">&nbsp;</td>
  </tr>
  <tr><td>&nbsp;</td></tr>
  <tr>
    <td width="50">&nbsp;</td>
    <td>
      <p><%= mytext.display("help.installationguide.text") %><br>
      <a href="/<%= mytext.display("adminpath") %>/InstallationGuide.pdf"><%= mytext.display("help.installationguide.link") %></a>
      </p>
    </td>
    <td><img src="/<%= mytext.display("adminpath") %>/configuration2.gif"></td>
    <td width="50">&nbsp;</td>
  </tr>
  <tr><td>&nbsp;</td></tr>
  <tr>
    <td class="WCMfooter" colspan="4" align="center"> 
      <%= mytext.display("footer") %>
    </td>
  </tr>
</table>
</body>
</html>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>