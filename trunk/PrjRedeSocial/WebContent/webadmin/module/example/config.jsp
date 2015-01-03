<%

module.add(						"Example Module v1.1" );

%><%@ include file="../add.jsp" %><%

moduleToolbarTitle.add(module.size()-1,			"Example<br>Module" );
moduleToolbarImage.add(module.size()-1,			"/" + mytext.display("adminpath") + "/module/example/module.gif" );
moduleToolbarLink.add(module.size()-1,			"/" + mytext.display("adminpath") + "/module/example/index.jsp?menu=example" );

moduleHomeIntroTitle.add(module.size()-1,		"Example Module Home" );
moduleHomeIntroText.add(module.size()-1,		"This is the Example Module Home" );
moduleHomeIntroImage.add(module.size()-1,		"/" + mytext.display("adminpath") + "/module/example/module.gif" );
moduleHomeIntroLink.add(module.size()-1,		"/" + mytext.display("adminpath") + "/module/example/index.jsp?menu=example" );
moduleHomeMenuTitle.add(module.size()-1,		"Example Module Home Menu" );
moduleHomeMenuLink.add(	module.size()-1,		"/" + mytext.display("adminpath") + "/module/example/index.jsp?menu=example" );

moduleContentIntroTitle.add(module.size()-1,		"Example Module Content" );
moduleContentIntroText.add(module.size()-1,		"This is the Example Module Content" );
moduleContentMenuTitle.add(module.size()-1,		"Example Module Content Menu" );
moduleContentMenuLink.add(module.size()-1,		"/" + mytext.display("adminpath") + "/module/example/index.jsp?menu=example" );

moduleContentTab.add(module.size()-1,			"exampletab" );
moduleContentTabTitle.add(module.size()-1,		"Example Tab" );
moduleContentTabContent.add(module.size()-1,		"Please wait while loading" );
moduleContentTabURL.add(module.size()-1,		"/webadmin/module/example/content.html" );
moduleContentTabScript.add(module.size()-1,		"alert('Example tab Javascript function');" );

moduleLibraryIntroTitle.add(module.size()-1,		"Example Module Library" );
moduleLibraryIntroText.add(module.size()-1,		"This is the Example Module Library" );
moduleLibraryMenuTitle.add(module.size()-1,		"Example Module Library Menu" );
moduleLibraryMenuLink.add(module.size()-1,		"/" + mytext.display("adminpath") + "/module/example/index.jsp?menu=example" );

moduleEcommerceIntroTitle.add(module.size()-1,		"Example Module E-Commerce" );
moduleEcommerceIntroText.add(module.size()-1,		"This is the Example Module E-Commerce" );
moduleEcommerceMenuTitle.add(module.size()-1,		"Example Module E-Commerce Menu" );
moduleEcommerceMenuLink.add(module.size()-1,		"/" + mytext.display("adminpath") + "/module/example/index.jsp?menu=example" );

moduleCommunityIntroTitle.add(module.size()-1,		"Example Module Community" );
moduleCommunityIntroText.add(module.size()-1,		"This is the Example Module Community" );
moduleCommunityMenuTitle.add(module.size()-1,		"Example Module Community Menu" );
moduleCommunityMenuLink.add(module.size()-1,		"/" + mytext.display("adminpath") + "/module/example/index.jsp?menu=example" );

moduleDatabasesIntroTitle.add(module.size()-1,		"Example Module Databases" );
moduleDatabasesIntroText.add(module.size()-1,		"This is the Example Module Databases" );
moduleDatabasesMenuTitle.add(module.size()-1,		"Example Module Databases Menu" );
moduleDatabasesMenuLink.add(module.size()-1,		"/" + mytext.display("adminpath") + "/module/example/index.jsp?menu=example" );

moduleExperienceIntroTitle.add(module.size()-1,		"Example Module Experience Management" );
moduleExperienceIntroText.add(module.size()-1,		"This is the Example Module Experience Management" );
moduleExperienceMenuTitle.add(module.size()-1,		"Example Module Experience Management Menu" );
moduleExperienceMenuLink.add(module.size()-1,		"/" + mytext.display("adminpath") + "/module/example/index.jsp?menu=example" );

moduleStatisticsIntroTitle.add(module.size()-1,		"Example Module Statistics" );
moduleStatisticsIntroText.add(module.size()-1,		"This is the Example Module Statistics" );
moduleStatisticsMenuTitle.add(module.size()-1,		"Example Module Statistics Menu" );
moduleStatisticsMenuLink.add(module.size()-1,		"/" + mytext.display("adminpath") + "/module/example/index.jsp?menu=example" );

moduleUsersIntroTitle.add(module.size()-1,		"Example Module Users" );
moduleUsersIntroText.add(module.size()-1,		"This is the Example Module Users" );
moduleUsersMenuTitle.add(module.size()-1,		"Example Module Users Menu" );
moduleUsersMenuLink.add(module.size()-1,		"/" + mytext.display("adminpath") + "/module/example/index.jsp?menu=example" );

moduleConfigIntroTitle.add(module.size()-1,		"Example Module Config" );
moduleConfigIntroText.add(module.size()-1,		"This is the Example Module Config" );
moduleConfigMenuTitle.add(module.size()-1,		"Example Module Config Menu" );
moduleConfigMenuLink.add(module.size()-1,		"/" + mytext.display("adminpath") + "/module/example/index.jsp?menu=example" );

moduleHostingIntroTitle.add(module.size()-1,		"Example Module Hosting" );
moduleHostingIntroText.add(module.size()-1,		"This is the Example Module Hosting" );
moduleHostingMenuTitle.add(module.size()-1,		"Example Module Hosting Menu" );
moduleHostingMenuLink.add(module.size()-1,		"/" + mytext.display("adminpath") + "/module/example/index.jsp?menu=example" );

modulePaymentTitle.add(module.size()-1,			"" );
modulePaymentOptions.add(module.size()-1,		"" );

%>