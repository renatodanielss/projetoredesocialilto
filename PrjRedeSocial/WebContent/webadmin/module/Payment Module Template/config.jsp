<%@ page import="HardCore.html" %><%

module.add(				   "Payment Module Template v1.0" );

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

modulePaymentTitle.add(			   "Payment Module Template" );
modulePaymentOptions.add(		   "Template for programming your own payment module.<br>"
+ "Your payment service provider account<br>"
+ "<input type=\"text\" size=\"40\" name=\"payment_account\" value=\"" + html.encode(myconfig.get(db, "payment_account")) + "\"><br>"
);

%>