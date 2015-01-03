<%@ page import="HardCore.html" %><%

module.add(				   "PayPal Module v1.0" );

moduleToolbarTitle.add(			   "" );
moduleToolbarImage.add(			   "" );
moduleToolbarLink.add(			   "" );

moduleHomeIntroTitle.add(		   "" );
moduleHomeIntroText.add(		   "" );
moduleHomeIntroImage.add(		   "" );
moduleHomeIntroLink.add(		   "" );
moduleHomeMenuTitle.add(		   "" );
moduleHomeMenuLink.add(			   "" );

moduleContentIntroTitle.add(		   "" );
moduleContentIntroText.add(		   "" );
moduleContentMenuTitle.add(		   "" );
moduleContentMenuLink.add(		   "" );

moduleLibraryIntroTitle.add(		   "" );
moduleLibraryIntroText.add(		   "" );
moduleLibraryMenuTitle.add(		   "" );
moduleLibraryMenuLink.add(		   "" );

moduleEcommerceIntroTitle.add(		   "" );
moduleEcommerceIntroText.add(		   "" );
moduleEcommerceMenuTitle.add(		   "" );
moduleEcommerceMenuLink.add(		   "" );

moduleCommunityIntroTitle.add(		   "" );
moduleCommunityIntroText.add(		   "" );
moduleCommunityMenuTitle.add(		   "" );
moduleCommunityMenuLink.add(		   "" );

moduleDatabasesIntroTitle.add(		   "" );
moduleDatabasesIntroText.add(		   "" );
moduleDatabasesMenuTitle.add(		   "" );
moduleDatabasesMenuLink.add(		   "" );

moduleExperienceIntroTitle.add(	"" );
moduleExperienceIntroText.add(	"" );
moduleExperienceMenuTitle.add(	"" );
moduleExperienceMenuLink.add(	"" );

moduleStatisticsIntroTitle.add(		   "" );
moduleStatisticsIntroText.add(		   "" );
moduleStatisticsMenuTitle.add(		   "" );
moduleStatisticsMenuLink.add(		   "" );

moduleUsersIntroTitle.add(		   "" );
moduleUsersIntroText.add(		   "" );
moduleUsersMenuTitle.add(		   "" );
moduleUsersMenuLink.add(		   "" );

moduleConfigIntroTitle.add(		   "" );
moduleConfigIntroText.add(		   "" );
moduleConfigMenuTitle.add(		   "" );
moduleConfigMenuLink.add(		   "" );

moduleHostingIntroTitle.add(		   "" );
moduleHostingIntroText.add(		   "" );
moduleHostingMenuTitle.add(		   "" );
moduleHostingMenuLink.add(		   "" );

modulePaymentTitle.add(			   "PayPal" );
modulePaymentOptions.add(		   "Your PayPal account/email<br>" +
"<input type=\"text\" size=\"60\" name=\"paypal_email\" value=\"" + html.encode(myconfig.get(db, "paypal_email")) + "\"><br>" +
"Payment instructions<br>" +
"<input type=\"text\" size=\"60\" name=\"paypal_text\" value=\"" + html.encode(myconfig.get(db, "paypal_text")) + "\"><br>" +
"<br><input type=\"radio\" name=\"paypal_method\" value=\"get\"" + ((myconfig.get(db, "paypal_method").equals("get")) || (myconfig.get(db, "paypal_method").equals("")) ? " checked" : "") + "> Use GET method for PayPal payment button forms for Mozilla Thunderbird and Firefox compatibility.<br>" +
"<input type=\"radio\" name=\"paypal_method\" value=\"post\"" + (myconfig.get(db, "paypal_method").equals("post") ? " checked" : "") + "> Use POST method for PayPal payment button forms.<br>" +
"<br><input type=\"radio\" name=\"paypal_cart\" value=\"details\"" + ((myconfig.get(db, "paypal_cart").equals("details")) || (myconfig.get(db, "paypal_cart").equals("")) ? " checked" : "") + "> Order item details on PayPal payment page.<br>" +
"<input type=\"radio\" name=\"paypal_cart\" value=\"simple\"" + (myconfig.get(db, "paypal_cart").equals("simple") ? " checked" : "") + "> No order item details on PayPal payment page.<br>" +
"<br><input type=\"radio\" name=\"paypal_echeck\" value=\"Completed\"" + ((myconfig.get(db, "paypal_echeck").equals("Completed")) || (myconfig.get(db, "paypal_echeck").equals("")) ? " checked" : "") + "> Accept eCheck payments when cleared.<br>" +
"<input type=\"radio\" name=\"paypal_echeck\" value=\"Pending\"" + (myconfig.get(db, "paypal_echeck").equals("Pending") ? " checked" : "") + "> Accept eCheck payments immediately.<br>" +
"<!--" +
"Payments description (depreciated - for backwards compatibility only)<br>" +
"<input type=\"text\" size=\"60\" name=\"paypal_item_name\" value=\"" + html.encode(myconfig.get(db, "paypal_item_name")) + "\"><br>" +
"-->" +
"<p style=\"font-size: small; font-weight: normal;\"><a href=\"https://www.paypal.com/uk/mrb/pal=SRD5TKTJUGFKJ\" target=\"_blank\">Click here to sign up for a free PayPal Merchant account and start accepting credit card payments instantly.</a></font><br>" +
"<a href=\"https://www.paypal.com/uk/mrb/pal=SRD5TKTJUGFKJ\" target=\"_blank\"><img  src=\"http://images.paypal.com/en_GB/i/bnr/paypal_mrb_banner.gif\" border=\"0\" alt=\"Sign up for PayPal and start accepting credit card payments instantly.\"></a></p>");

%>