<%

myconfig.setTemp("logdatabase", "");
//myconfig.setTemp("logdatabase", "mysql:com.mysql.jdbc.Driver:username:password@jdbc:mysql://localhost/database");

// Configuration - System - Database [defaults.jsp]
//myconfig.setTemp("database_version", "7.1");

// Configuration - System - License
//myconfig.setTemp("hosting_license", "");

// Configuration - System - Superadmin
//myconfig.setTemp("superadmin", "admin");

// Configuration - System - Website - Website Design [defaults.jsp]
//myconfig.setTemp("default_version", "");
//myconfig.setTemp("default_template", "");
//myconfig.setTemp("default_stylesheet", "");

// Configuration - System - Website - Special Pages - Default page (homepage) [defaults.jsp]
//myconfig.setTemp("default_page", "");

// Configuration - System - Website - Special Settings - HTML DOCTYPE
//myconfig.setTemp("doctype", "");
//myconfig.setTemp("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">");
//myconfig.setTemp("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");

// Configuration - System - Website - Special Settings - HTML Content-Type charset
//myconfig.setTemp("charset", "UTF-8");
//myconfig.setTemp("charset", "iso-8859-1");

// Configuration - System - Website - Special Settings - Style sheet web addresses
//myconfig.setTemp("hardcore_stylesheet_hrefs", "");
//myconfig.setTemp("hardcore_stylesheet_hrefs", "direct");
//myconfig.setTemp("hardcore_stylesheet_hrefs", "inline");

// Configuration - System - Website - Special Settings - URL Rewriting
//myconfig.setTemp("url_rewriting", "no");
//myconfig.setTemp("url_rewriting", "yes");

// Configuration - System - E-Commerce - Default Currency
//myconfig.setTemp("default_currency", "");

// Configuration - System - E-Commerce - Special Pages
//myconfig.setTemp("default_shopcartsummary_page", "");
//myconfig.setTemp("default_shopcartsummary_entry", "");

// Configuration - System - Usage Statistics
//myconfig.setTemp("do_log_page", "yes");
//myconfig.setTemp("do_log_page", "no");
//myconfig.setTemp("do_log_image", "yes");
//myconfig.setTemp("do_log_image", "no");
//myconfig.setTemp("do_log_file", "yes");
//myconfig.setTemp("do_log_file", "no");
//myconfig.setTemp("do_log_link", "yes");
//myconfig.setTemp("do_log_link", "no");
//myconfig.setTemp("do_log_product", "yes");
//myconfig.setTemp("do_log_product", "no");
//myconfig.setTemp("do_log_stylesheet", "yes");
//myconfig.setTemp("do_log_stylesheet", "no");
//myconfig.setTemp("do_log_script", "yes");
//myconfig.setTemp("do_log_script", "no");
//myconfig.setTemp("do_log_contact", "yes");
//myconfig.setTemp("do_log_contact", "no");
//myconfig.setTemp("do_log_post", "yes");
//myconfig.setTemp("do_log_post", "no");
//myconfig.setTemp("do_log_login", "yes");
//myconfig.setTemp("do_log_login", "no");
//myconfig.setTemp("do_log_logout", "yes");
//myconfig.setTemp("do_log_logout", "no");

// Configuration - Features - Access Restrictions
//myconfig.setTemp("use_accessrestrictions", "none");
//myconfig.setTemp("use_accessrestrictions", "users");
//myconfig.setTemp("use_accessrestrictions", "all");
//myconfig.setTemp("accessrestrictions", "and");
//myconfig.setTemp("accessrestrictions", "or");
//myconfig.setTemp("inherit_accessrestrictions", "yes");
//myconfig.setTemp("inherit_accessrestrictions", "no");
//myconfig.setTemp("website_users_type", "");
//myconfig.setTemp("website_users_group", "");
//myconfig.setTemp("website_editors_type", "");
//myconfig.setTemp("website_editors_group", "");
//myconfig.setTemp("website_developers_type", "");
//myconfig.setTemp("website_developers_group", "");
//myconfig.setTemp("website_creators_type", "");
//myconfig.setTemp("website_creators_group", "");
//myconfig.setTemp("website_publishers_type", "");
//myconfig.setTemp("website_publishers_group", "");
//myconfig.setTemp("website_administrators_type", "");
//myconfig.setTemp("website_administrators_group", "");

// Configuration - Features - Publishing
//myconfig.setTemp("scheduled_next", ""); // !!!!! disables scheduled publishing/expiration functionality unless "/webadmin/publishscheduled.jsp" is called periodically

// Configuration - Content - Classes
//myconfig.setTemp("elements", "banner,logo,menu,news,offer,toolbar");

// VARIOUS
myconfig.setTemp("adjust_links", "yes");
//myconfig.setTemp("adjust_links", "no");
myconfig.setTemp("import_website_exclude", ""); // excludes the listed folders/files from the import existing website functionality
//myconfig.setTemp("import_website_exclude", "|webeditor|webmanager"); // excludes the listed folders/files from the import existing website functionality

// Security - Session Fixation and Hijacking
//myconfig.setTemp("session_address_lock", "none"); // does not require user's Internet address to stay the same from page to page
myconfig.setTemp("session_address_lock", "base"); // requires user's base Internet address (excluding the first part of domain name) to stay the same from page to page
//myconfig.setTemp("session_address_lock", "full"); // requires user's full Internet address (including the first part of domain name) to stay the same from page to page

// automatic direct jdbc to data source database connection mapping
// important: context_provider should be a safe location as it contains database connection details
//db.context_provider = "file:///tmp";
//db.context_factory = "com.sun.jndi.fscontext.RefFSContextFactory";
//db.datasource_factory = "org.apache.commons.dbcp.BasicDataSourceFactory";
//db.datasource_prefix = "jdbc/AsbruWCM/";
//db.datasource_maxActive = "1000";
//db.datasource_maxIdle = "100";
//db.datasource_maxWait = "10000";
//db.DEBUG = true;

//Page.DEBUG = true;

// EXPERIMENTAL
// optional encyption of users database table data ("keywords", "description", "password", "email", "card_type", "card_number", "card_issuedmonth", "card_issuedyear", "card_expirymonth", "card_expiryyear", "card_name", "card_cvc", "card_issue", "card_postalcode", "delivery_name", "delivery_organisation", "delivery_address", "delivery_postalcode", "delivery_city", "delivery_state", "delivery_country", "delivery_phone", "delivery_fax", "delivery_email", "delivery_website", "invoice_name", "invoice_organisation", "invoice_address", "invoice_postalcode", "invoice_city", "invoice_state", "invoice_country", "invoice_phone", "invoice_fax", "invoice_email", "invoice_website", "notes", "userinfo")
// any unencrypted character in the first string will be encrypted as the character at the same position in the second string
// there should always be the same number of characters in both strings and both strings should contain the exact same characters but ordered differently
// the third string is added as a prefix before all encrypted data in the database - it must be unique and something that is never used as the start of any unencrypted user data
// important: after some data has been encrypted the encryption strings may not be changed/removed or the data will be corrupted - carefully, select the encryption strings from the start
// important: you must keep a safe copy of the encryption strings - the web content management system cannot retrieve them in any way if they are lost
// lost encryption strings may only be retrieved using general crypt analysis/cracking tools and Asbru Ltd. cannot assist with this in any way
// important: encryption may not be available in other programming language versions of the web content management system
// use "db.decrypt(value)" and "db.encrypt(value)" from your own custom extensions and add-on modules to decrypt/encrypt data read/written directly from/to the users database table
//db.encryption( "abcdefghijklmnopqrstuvwxyz0123456789", "bcdefghijklmnopqrstuvwxyz0123456789a", "!!!" );

%>