<%@ include file="../config.jsp" %><% if (RequireUser.Administrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"))) { %><%

// web browser user agent

// iPhone 5 - iOS 6.0 - Safari 6.0
String deviceUserAgent = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 6_0 like Mac OS X; en-us) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A405 Safari/8536.25";

// device dimensions

long devicePortraitScreenWidth = 640;
long devicePortraitScreenHeight = 1136;
long devicePortraitViewportWidth = 320;
long devicePortraitViewportHeight = 568;
double devicePortraitViewportScale = devicePortraitScreenWidth / devicePortraitViewportWidth;

long deviceLandscapeScreenWidth = 1136;
long deviceLandscapeScreenHeight = 640;
long deviceLandscapeViewportWidth = 568;
long deviceLandscapeViewportHeight = 320;
double deviceLandscapeViewportScale = deviceLandscapeScreenWidth / deviceLandscapeViewportWidth;

// device-portrait.png width and height

long devicePortraitWidth = 305;
long devicePortraitHeight = 638;

// device-landscape.png width and height

long deviceLandscapeWidth = 638;
long deviceLandscapeHeight = 305;

// web browser content scale

double devicePortraitFrameScale = 0.365;
double deviceLandscapeFrameScale = 0.50;

// web browser content position and size

long devicePortraitFrameTop = 159;
long devicePortraitFrameLeft = 26;
long devicePortraitFrameWidth = Math.round(256 / devicePortraitFrameScale);
long devicePortraitFrameHeight = Math.round(354 / devicePortraitFrameScale);

long deviceLandscapeFrameTop = 86;
long deviceLandscapeFrameLeft = 95;
long deviceLandscapeFrameWidth = Math.round(454 / deviceLandscapeFrameScale);
long deviceLandscapeFrameHeight = Math.round(167 / deviceLandscapeFrameScale);

// click to rotate bezel/button/icon position and size (if any)

long devicePortraitRotateTop = 0;
long devicePortraitRotateLeft = 0;
long devicePortraitRotateWidth = devicePortraitWidth;
long devicePortraitRotateHeight = 100;

long deviceLandscapeRotateTop = 0;
long deviceLandscapeRotateLeft = 0;
long deviceLandscapeRotateWidth = deviceLandscapeWidth;
long deviceLandscapeRotateHeight = 25;

// click to reload button/icon (if any)

long devicePortraitReloadTop = 89;
long devicePortraitReloadLeft = 582;
long devicePortraitReloadWidth =  18;
long devicePortraitReloadHeight = 18;

long deviceLandscapeReloadTop = 89;
long deviceLandscapeReloadLeft = 745;
long deviceLandscapeReloadWidth = 18;
long deviceLandscapeReloadHeight = 18;

// web browser URL input position and size (if any)

long devicePortraitURLTop = 128;
long devicePortraitURLLeft = 38;
long devicePortraitURLWidth = 145;
long devicePortraitURLHeight = 21;
long devicePortraitURLFontSize = 12;
String devicePortraitURLFontFamily = "'Droid Sans', 'Liberation Sans', FreeSans, 'Helvetica Neue LT Std', 'Helvetica LT Std', Helvetica, Arial, Tahoma, 'Lucida Grande', 'Lucida Sans', sans-serif";
String devicePortraitURLColor = "#717070";

long deviceLandscapeURLTop = 56;
long deviceLandscapeURLLeft = 108;
long deviceLandscapeURLWidth = 275;
long deviceLandscapeURLHeight = 21;
long deviceLandscapeURLFontSize = 12;
String deviceLandscapeURLFontFamily = "'Droid Sans', 'Liberation Sans', FreeSans, 'Helvetica Neue LT Std', 'Helvetica LT Std', Helvetica, Arial, Tahoma, 'Lucida Grande', 'Lucida Sans', sans-serif";
String deviceLandscapeURLColor = "#717070";

// web browser search input position and size (if any)

long devicePortraitSearchTop = 130;
long devicePortraitSearchLeft = 202;
long devicePortraitSearchWidth = 65;
long devicePortraitSearchHeight = 17;
long devicePortraitSearchFontSize = 12;
String devicePortraitSearchFontFamily = "'Droid Sans', 'Liberation Sans', FreeSans, 'Helvetica Neue LT Std', 'Helvetica LT Std', Helvetica, Arial, Tahoma, 'Lucida Grande', 'Lucida Sans', sans-serif";
String devicePortraitSearchColor = "#717070";

long deviceLandscapeSearchTop = 59;
long deviceLandscapeSearchLeft = 402;
long deviceLandscapeSearchWidth = 135;
long deviceLandscapeSearchHeight = 17;
long deviceLandscapeSearchFontSize = 12;
String deviceLandscapeSearchFontFamily = "'Droid Sans', 'Liberation Sans', FreeSans, 'Helvetica Neue LT Std', 'Helvetica LT Std', Helvetica, Arial, Tahoma, 'Lucida Grande', 'Lucida Sans', sans-serif";
String deviceLandscapeSearchColor = "#717070";

// web browser title position and size (if any)

long devicePortraitTitleTop = 110;
long devicePortraitTitleLeft = 85;
long devicePortraitTitleWidth = 140;
long devicePortraitTitleHeight = 21;
long devicePortraitTitleFontSize = 10;
String devicePortraitTitleFontFamily = "'Droid Sans', 'Liberation Sans', FreeSans, 'Helvetica Neue LT Std', 'Helvetica LT Std', Helvetica, Arial, Tahoma, 'Lucida Grande', 'Lucida Sans', sans-serif";
String devicePortraitTitleColor = "#717070";

long deviceLandscapeTitleTop = 38;
long deviceLandscapeTitleLeft = 255;
long deviceLandscapeTitleWidth = 140;
long deviceLandscapeTitleHeight = 21;
long deviceLandscapeTitleFontSize = 10;
String deviceLandscapeTitleFontFamily = "'Droid Sans', 'Liberation Sans', FreeSans, 'Helvetica Neue LT Std', 'Helvetica LT Std', Helvetica, Arial, Tahoma, 'Lucida Grande', 'Lucida Sans', sans-serif";
String deviceLandscapeTitleColor = "#717070";

// pop-up keyboard position and size (if any)

long devicePortraitKeyboardTop = 208;
long devicePortraitKeyboardLeft = 100;
long devicePortraitKeyboardWidth = 768;
long devicePortraitKeyboardHeight = 950;

long deviceLandscapeKeyboardTop = 194;
long deviceLandscapeKeyboardLeft = 116;
long deviceLandscapeKeyboardWidth = 1024;
long deviceLandscapeKeyboardHeight = 670;

// viewport

long viewportPortraitFrameWidth = devicePortraitFrameWidth;
long viewportPortraitFrameHeight = devicePortraitFrameHeight;
double viewportPortraitFrameScale = devicePortraitFrameScale;

long viewportLandscapeFrameWidth = deviceLandscapeFrameWidth;
long viewportLandscapeFrameHeight = deviceLandscapeFrameHeight;
double viewportLandscapeFrameScale = deviceLandscapeFrameScale;

%><%@ include file="../viewport.jsp" %><!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>iPhone 5 Simulator</title>

<style type="text/css">

body {
	color:#efefef;
	background-color:#2f2f2f2f;
	font-family:Arial, Helvetica, sans-serif;
	scrollbar-base-color:#060306;
	scrollbar-face-color:#090C0C;
	scrollbar-track-color:#090609;
	scrollbar-arrow-color:#030003;
	scrollbar-highlight-color:#0F0F0F;
	scrollbar-3dlight-color:#0C0C0C;
	scrollbar-shadow-color:#060306;
	scrollbar-darkshadow-color:#000000;
	background: #262626;
}

body,html {
	margin:0;
	padding:0;
}

a {
	color:#1e87e1;
}

img {
	border:0;
	border-image:initial;
	vertical-align:top;
}

input:focus,input:focus,textarea:focus {
	outline:none;
}

#wrapper {
	width:<%= deviceLandscapeWidth %>px;
	text-align:center;
	margin:0 auto;
}

#device {
	margin-top:50px;
	margin-left:auto;
	margin-right:auto;
	position:relative;
	top:0;
	left:0;
}

#device.landscape {
	background:url(device-landscape.png);
	width:<%= deviceLandscapeWidth %>px;
	height:<%= deviceLandscapeHeight %>px;
}

#device.portrait {
	background:url(device-portrait.png);
	width:<%= devicePortraitWidth %>px;
	height:<%= devicePortraitHeight %>px;
}

#device #rotate {
	cursor:pointer;
	position:absolute;
}

#device.landscape #rotate {
	top:<%= deviceLandscapeRotateTop %>px;
	left:<%= deviceLandscapeRotateLeft %>px;
	width:<%= deviceLandscapeRotateWidth %>px;
	height:<%= deviceLandscapeRotateHeight %>px;
}

#device.portrait #rotate {
	top:<%= devicePortraitRotateTop %>px;
	left:<%= devicePortraitRotateLeft %>px;
	width:<%= devicePortraitRotateWidth %>px;
	height:<%= devicePortraitRotateHeight %>px;
}

#device #reload {
	cursor:pointer;
	position:absolute;
}

#device.landscape #reload {
	top:<%= deviceLandscapeReloadTop %>px;
	left:<%= deviceLandscapeReloadLeft %>px;
	width:<%= deviceLandscapeReloadWidth %>px;
	height:<%= deviceLandscapeReloadHeight %>px;
}

#device.portrait #reload {
	top:<%= devicePortraitReloadTop %>px;
	left:<%= devicePortraitReloadLeft %>px;
	width:<%= devicePortraitReloadWidth %>px;
	height:<%= devicePortraitReloadHeight %>px;
}

#device #kbd {
	display:none;
	z-index:10;
	position:absolute;
}

#device.landscape #kbd {
	background:url(keyboard-landscape.png);
	top:<%= deviceLandscapeKeyboardTop %>px;
	left:<%= deviceLandscapeKeyboardLeft %>px;
	width:<%= deviceLandscapeKeyboardWidth %>px;
	height:<%= deviceLandscapeKeyboardHeight %>px;
}

#device.portrait #kbd {
	background:url(keyboard-portrait.png);
	top:<%= devicePortraitKeyboardTop %>px;
	left:<%= devicePortraitKeyboardLeft %>px;
	width:<%= devicePortraitKeyboardWidth %>px;
	height:<%= devicePortraitKeyboardHeight %>px;
}

#device #url {
	background:none;
	border:none;
	position:absolute;
}

#device.landscape #url {
	top:<%= deviceLandscapeURLTop %>px;
	left:<%= deviceLandscapeURLLeft %>px;
	width:<%= deviceLandscapeURLWidth %>px;
	height:<%= deviceLandscapeURLHeight %>px;
	color:<%= deviceLandscapeURLColor %>;
	font-size:<%= deviceLandscapeURLFontSize %>px;
	font-family:<%= deviceLandscapeURLFontFamily %>;
}

#device.portrait #url {
	top:<%= devicePortraitURLTop %>px;
	left:<%= devicePortraitURLLeft %>px;
	width:<%= devicePortraitURLWidth %>px;
	height:<%= devicePortraitURLHeight %>px;
	color:<%= devicePortraitURLColor %>;
	font-size:<%= devicePortraitURLFontSize %>px;
	font-family:<%= devicePortraitURLFontFamily %>;
}

#device #search {
	background:#ffffff;
	border:none;
	position:absolute;
}

#device.landscape #search {
	top:<%= deviceLandscapeSearchTop %>px;
	left:<%= deviceLandscapeSearchLeft %>px;
	width:<%= deviceLandscapeSearchWidth %>px;
	height:<%= deviceLandscapeSearchHeight %>px;
	color:<%= deviceLandscapeSearchColor %>;
	font-size:<%= deviceLandscapeSearchFontSize %>px;
	font-family:<%= deviceLandscapeSearchFontFamily %>;
}

#device.portrait #search {
	top:<%= devicePortraitSearchTop %>px;
	left:<%= devicePortraitSearchLeft %>px;
	width:<%= devicePortraitSearchWidth %>px;
	height:<%= devicePortraitSearchHeight %>px;
	color:<%= devicePortraitSearchColor %>;
	font-size:<%= devicePortraitSearchFontSize %>px;
	font-family:<%= devicePortraitSearchFontFamily %>;
}

#device #title {
	background:none;
	border:none;
	font-weight: bold;
	position:absolute;
}

#device.landscape #title {
	top:<%= deviceLandscapeTitleTop %>px;
	left:<%= deviceLandscapeTitleLeft %>px;
	width:<%= deviceLandscapeTitleWidth %>px;
	height:<%= deviceLandscapeTitleHeight %>px;
	color:<%= deviceLandscapeTitleColor %>;
	font-size:<%= deviceLandscapeTitleFontSize %>px;
	font-family:<%= deviceLandscapeTitleFontFamily %>;
}

#device.portrait #title {
	top:<%= devicePortraitTitleTop %>px;
	left:<%= devicePortraitTitleLeft %>px;
	width:<%= devicePortraitTitleWidth %>px;
	height:<%= devicePortraitTitleHeight %>px;
	color:<%= devicePortraitTitleColor %>;
	font-size:<%= devicePortraitTitleFontSize %>px;
	font-family:<%= devicePortraitTitleFontFamily %>;
}

#device #frame {
	border:none;
	background:#ffffff;
	position:absolute;
	transform-origin: 0% 0%;
	-ms-transform-origin: 0% 0%;
	-webkit-transform-origin: 0% 0%;
	-moz-transform-origin: 0% 0%;
}

#device.landscape #frame {
	top:<%= deviceLandscapeFrameTop %>px;
	left:<%= deviceLandscapeFrameLeft %>px;
	width:<%= viewportLandscapeFrameWidth %>px;
	height:<%= viewportLandscapeFrameHeight %>px;
	transform: scale(<%= viewportLandscapeFrameScale %>,<%= viewportLandscapeFrameScale %>);
	-ms-transform: scale(<%= viewportLandscapeFrameScale %>,<%= viewportLandscapeFrameScale %>);
	-webkit-transform: scale(<%= viewportLandscapeFrameScale %>,<%= viewportLandscapeFrameScale %>);
	-moz-transform: scale(<%= viewportLandscapeFrameScale %>,<%= viewportLandscapeFrameScale %>);
	-o-transform: scale(<%= viewportLandscapeFrameScale %>,<%= viewportLandscapeFrameScale %>);
}

#device.portrait #frame {
	top:<%= devicePortraitFrameTop %>px;
	left:<%= devicePortraitFrameLeft %>px;
	width:<%= viewportPortraitFrameWidth %>px;
	height:<%= viewportPortraitFrameHeight %>px;
	transform: scale(<%= viewportPortraitFrameScale %>,<%= viewportPortraitFrameScale %>);
	-ms-transform: scale(<%= viewportPortraitFrameScale %>,<%= viewportPortraitFrameScale %>);
	-webkit-transform: scale(<%= viewportPortraitFrameScale %>,<%= viewportPortraitFrameScale %>);
	-moz-transform: scale(<%= viewportPortraitFrameScale %>,<%= viewportPortraitFrameScale %>);
	-o-transform: scale(<%= viewportPortraitFrameScale %>,<%= viewportPortraitFrameScale %>);
}

</style>

<script type="text/javascript" src="../jquery.js"></script>
<script type="text/javascript" src="../jquery_url_min.js"></script>
<script type="text/javascript" src="../script.js"></script>

</head>

<body>

<%@ include file="../data.jsp" %>

<div id="wrapper" name="wrapper">
   <div id="device" name="device" class="portrait">
      <div id="rotate" name="rotate" title="Click to rotate ..."></div>
      <input id="url" name="url" value="">
      <input id="search" name="search" value="Search" onfocus="if (this.value == 'Search') this.value = '';" onblur="if (this.value == '') this.value = 'Search';">
      <div id="title" name="title">Preview</div>
      <iframe id="frame" name="frame"></iframe>
   </div>
</div>
    
<br><br><br>

</body>
</html>
<% } %>
