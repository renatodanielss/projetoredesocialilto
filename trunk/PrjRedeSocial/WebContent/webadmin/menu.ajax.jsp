<%@ include file="config.jsp" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.UCaccessAdministration" %>
<%@ page import="HardCore.UCmaintainContent" %><%

String output = "";
String menu = myrequest.getParameter("menu");
String selected = myrequest.getParameter("selected");
if (menu.equals("pagegroup")) {
	output = MenuContent.ajaxdtreePagesGroups(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("pagetype")) {
	output = MenuContent.ajaxdtreePagesTypes(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("elementgroup")) {
	output = MenuContent.ajaxdtreeElementsGroups(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("elementtype")) {
	output = MenuContent.ajaxdtreeElementsTypes(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("relationpagegroup")) {
	output = MenuContent.ajaxdtreeRelationsPagesGroups(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("relationpagetype")) {
	output = MenuContent.ajaxdtreeRelationsPagesTypes(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("imagegroup")) {
	output = MenuContent.ajaxdtreeImagesGroups(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("imagetype")) {
	output = MenuContent.ajaxdtreeImagesTypes(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("filegroup")) {
	output = MenuContent.ajaxdtreeFilesGroups(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("filetype")) {
	output = MenuContent.ajaxdtreeFilesTypes(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("linkgroup")) {
	output = MenuContent.ajaxdtreeLinksGroups(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("linktype")) {
	output = MenuContent.ajaxdtreeLinksTypes(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("productgroup")) {
	output = MenuContent.ajaxdtreeEcommerceProductsGroups(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("producttype")) {
	output = MenuContent.ajaxdtreeEcommerceProductsTypes(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("statisticspagegroup")) {
	output = MenuContent.ajaxdtreeStatisticsPagesGroups(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("statisticspagetype")) {
	output = MenuContent.ajaxdtreeStatisticsPagesTypes(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("statisticsimagegroup")) {
	output = MenuContent.ajaxdtreeStatisticsImagesGroups(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("statisticsimagetype")) {
	output = MenuContent.ajaxdtreeStatisticsImagesTypes(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("statisticsfilegroup")) {
	output = MenuContent.ajaxdtreeStatisticsFilesGroups(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("statisticsfiletype")) {
	output = MenuContent.ajaxdtreeStatisticsFilesTypes(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("statisticslinkgroup")) {
	output = MenuContent.ajaxdtreeStatisticsLinksGroups(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("statisticslinktype")) {
	output = MenuContent.ajaxdtreeStatisticsLinksTypes(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("statisticsproductgroup")) {
	output = MenuContent.ajaxdtreeStatisticsProductsGroups(mytext,db,myconfig,mysession,selected);
} else if (menu.equals("statisticsproducttype")) {
	output = MenuContent.ajaxdtreeStatisticsProductsTypes(mytext,db,myconfig,mysession,selected);
}

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>