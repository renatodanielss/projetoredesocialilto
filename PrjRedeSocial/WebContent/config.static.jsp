<%

myconfig.setTemp("logdatabase", "");
//myconfig.setTemp("logdatabase", "mysql:com.mysql.jdbc.Driver:username:password@jdbc:mysql://localhost/database");

// Configuration - System - Database [defaults.jsp]
//myconfig.setTemp("database_version", "9.1");

// Configuration - System - License
//myconfig.setTemp("hosting_license", ""); // prevent configuration of hosting license key
//myconfig.setTemp("experience_license", ""); // disable experience management module completely

// Configuration - System - Superadmin
//myconfig.setTemp("superadmin", "admin");

// Configuration - System - Website - Website Design [defaults.jsp]
//myconfig.setTemp("default_version", "");
//myconfig.setTemp("default_template", "");
//myconfig.setTemp("default_stylesheet", "");

// Configuration - System - Website - Security - Session Fixation and Hijacking
//myconfig.setTemp("session_address_lock", "none"); // does not require user's Internet address to stay the same from page to page
//myconfig.setTemp("session_address_lock", "base"); // requires user's base Internet address (excluding the first part of domain name) to stay the same from page to page
//myconfig.setTemp("session_address_lock", "full"); // requires user's full Internet address (including the first part of domain name) to stay the same from page to page

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
myconfig.setTemp("decimals", "2"); // as default output two decimals
//myconfig.setTemp("decimals", "0"); // do not output decimals for use in Japan
//myconfig.setTemp("grouping", "false"); // do not use grouping separators for 1,000 and 1,000,000 etc.
myconfig.setTemp("grouping", "true"); // do use grouping separators for 1,000 and 1,000,000 etc.

// Configuration - System - E-Commerce - Special Pages
//myconfig.setTemp("default_shopcartsummary_page", "");
//myconfig.setTemp("default_shopcartsummary_entry", "");

// Configuration - System - E-Commerce - Affiliate
myconfig.setTemp("affiliate_parameter", "affiliate"); // referer URL parameter name for affiliate id
myconfig.setTemp("affiliate_change", "yes"); // use latest affiliate id during session
//myconfig.setTemp("affiliate_change", "no"); // only use first affiliate id for whole session

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

//myconfig.setTemp("webadmin_workspace_workflow_noactions", "hide"); // do not list Personal Workspace workflow content items created/updated by administrator for which the administrator does not have update or workflow action permissions

// Configuration - Features - Publishing
//myconfig.setTemp("scheduled_next", ""); // !!!!! disables scheduled publishing/expiration functionality unless "/webadmin/publishscheduled.jsp" is called periodically
//myconfig.setTemp("export_rootpath", ""); // !!!!! disables export of content items as simple static files

// Configuration - Content - Classes
//myconfig.setTemp("elements", "banner,logo,menu,news,offer,toolbar");

// Website Content - Validate Markup
//myconfig.setTemp("validate_markup_response_begin", ""); // ignore response before the first instance of the given string (if any)
//myconfig.setTemp("validate_markup_response_end", ""); // ignore response after the last instance of the given string (if any)
//myconfig.setTemp("validate_markup_response_regexp_keep", ""); // only keep the part of the response that matches the given regular expression (if any)
//myconfig.setTemp("validate_markup_response_regexp_cut", ""); // cut everything from the response that matches the given regular expression (if any)
//myconfig.setTemp("checklinks_domains", "yourwebsite\\.com"); // only check links for domain names ending with the given regular expression (if any)
//myconfig.setTemp("checklinks_domains", "(yourwebsite\\.com|otherwebsite\\.com|thirdwebsite\\.com)"); // only check links for domain names ending with the given regular expression (if any)
//myconfig.setTemp("checklinks_domains", "-"); // do not check links

// Website Content - Insert Hyperlink / Insert Media
myconfig.setTemp("hide_accessrestricted_content_items", "yes"); // hide access restricted content items in Insert Hyperlink / Insert Media / Select Content pop-up windows

// VARIOUS
myconfig.setTemp("adjust_links", "yes");
//myconfig.setTemp("adjust_links", "no");
myconfig.setTemp("import_website_exclude", ""); // excludes the listed folders/files from the import existing website functionality
//myconfig.setTemp("import_website_exclude", "|webeditor|webmanager"); // excludes the listed folders/files from the import existing website functionality
//myconfig.setTemp("getset", "var_"); // prefix for session variable names for @@@get:...@@@ and @@@set:...@@@ special codes - use prefix to prevent access to arbitrary session variables used by the web content management system and other systems
myconfig.setTemp("getset", ""); // prefix for session variable names for @@@get:...@@@ and @@@set:...@@@ special codes - set to blank to permit access to arbitrary session variables used by the web content management system and other systems
myconfig.setTemp("website_structure_tops", "'',id"); // content items where page_up in ...
myconfig.setTemp("website_structure_orphans", "'0'"); // content items where page_up in ...
//myconfig.setTemp("website_structure_orphans", ""); // content items where page_up in ... - set to blank to hide/ignore orphans

//myconfig.setTemp("search_section_all", "no"); // do not search if only section parameter is given

// override HTML BODY margin attributes
//HardCore.Cms._BODYmargins = null; // use automatic defaults depending on the DOCTYPE
//HardCore.Cms._BODYmargins = " style=\"margin: 0px;\""; // default for html4+xhtml DOCTYPE
//HardCore.Cms._BODYmargins = " marginwidth=\"0\" marginheight=\"0\" leftmargin=\"0\" topmargin=\"0\""; // default for no or other DOCTYPE
//HardCore.Cms._BODYmargins = ""; // do not use any

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
//logdb.DEBUG = true;

//HardCore.Cache.DEBUG = true;
//if (HardCore.Cache.CACHE) HardCore.Cache.CACHE = false;

// invalid characters to be removed from the database connection string
//db.connectionstring_invalid_chars = "[^-=!: *.,;_?&%$@/\\\\{}()a-zA-Z0-9]";			// do not keep any "word" character classes except the ones explicitly specified
//db.connectionstring_invalid_chars = "[^-=!: *.,;_?&%$@/\\\\{}()a-zA-Z0-9\\w]";		// "\\w" may keep invalid Unicode encoded characters for InitialContext and Reference
//db.connectionstring_invalid_chars = "[^-=!: *.,;_?&%$@/\\\\{}()a-zA-Z0-9\\p{L}\\p{N}]";	// "\\p{L}\\p{N}" keeps all Unicode character that are letters or numbers

//HardCore.Content.DEBUG = true;
//HardCore.Page.DEBUG = true;
//HardCore.CAPTCHA.DEBUG = true;
//HardCore.User.DEBUG = true;
//HardCore.http.DEBUG = true;
//HardCore.http.GC = true;

// EXPERIMENTAL
//HardCore.Data.csv_options = false; // default: custom content database multi-value data must be separated by "|"
//HardCore.Data.csv_options = true; // custom content database multi-value data may be separated by "|" or ","

// EXPERIMENTAL - DEPRECATED
// new parsing method for content special codes which may be faster depending on the specific content and programming language and application server version used
// if both parsing methods are enabled then the new sequential parsing will be used first, followed by the old parsing method for eventual remaining special codes
//myconfig.setTemp("content_parse_seq", "no"); // default: do not parse content special codes sequentially
//myconfig.setTemp("content_parse_old", "yes"); // default: parse content special codes using "global" search and replace
//myconfig.setTemp("content_parse_seq", "yes"); // parse content special codes sequentially
//myconfig.setTemp("content_parse_old", "no"); // do not parse content special codes using "global" search and replace

// EXPERIMENTAL
// use of website visitor web browser identification may decrease the website performance slightly
//myconfig.setTemp("use_devices", "no"); // disable website visitor web browser device identification - device specific website content and special codes functionality will not work
//myconfig.setTemp("use_devices_minor", "no"); // disable website visitor web browser device identification of minor web browsers

// EXPERIMENTAL
// configuration of experience management license key may decrease the website performance slightly
// optionally, you may want to disable the experience management module functionality for the front-end delivery engine outside of specific experience management test periods
//myconfig.setTemp("use_experience", "no"); // disable experience management module functionality for front-end delivery engine - experience management administration still available, but no effect on website content
//myconfig.setTemp("use_usersegments", "no"); // disable experience management module segments functionality for front-end delivery engine - experience management administration still available, but no effect on website content
//myconfig.setTemp("use_usertests", "no"); // disable experience management module user tests functionality for front-end delivery engine - experience management administration still available, but no effect on website content

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

//db.useNonCrossRefIds = true; // use ids in database import files which refer to existing database items instead of setting them to blank - WARNING: this may give unauthorised access to restricted content if used incorrectly

//HardCore.User.DIGEST = true; // store user account password as digest fingerprint in database

//myconfig.setTemp("use_fulltext_indexing", "yes");
//myconfig.setTemp("use_contentdependencies_tables", "no");
// MySQL:
//   alter table content engine = myisam
//   alter table content_archive engine = myisam
//   alter table content_public engine = myisam
//   create fulltext index content_content_idx on content(content)
//   create fulltext index content_archive_content_idx on content_archive(content)
//   create fulltext index content_public_content_idx on content_public(content)
//   create fulltext index content_summary_idx on content(summary)
//   create fulltext index content_archive_summary_idx on content_archive(summary)
//   create fulltext index content_public_summary_idx on content_public(summary)
// Other:
//   create index content_content_idx on content(content)
//   create index content_archive_content_idx on content_archive(content)
//   create index content_public_content_idx on content_public(content)
//   create index content_summary_idx on content(summary)
//   create index content_archive_summary_idx on content_archive(summary)
//   create index content_public_summary_idx on content_public(summary)

%>