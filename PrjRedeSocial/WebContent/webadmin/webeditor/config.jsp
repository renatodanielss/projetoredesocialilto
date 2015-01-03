<%

// Which spellcheck command and parameters to use
String spellcheckCommand = "";
String spellcheckParameters = "";
//spellcheckCommand = "/usr/local/bin/aspell";
//spellcheckCommand = "C:\\Progra~1\\Aspell\\bin\\aspell.exe";
//spellcheckCommand = "C:\\Progra~2\\Aspell\\bin\\aspell.exe";
spellcheckParameters = "-a -H";

// Which spellcheck command parameter to use for specifying which dictionary to use
String spellcheckDictionary = "";
spellcheckDictionary = "-d";
// Which dictionary options to make available to users
String spellcheckDictionaries = "";
spellcheckDictionaries = "<option value=\"\">- default -<option value=\"af\">Afrikaans<option value=\"br\">Breton<option value=\"bg\">Bulgarian<option value=\"ca\">Catalan<option value=\"hr\">Croatian<option value=\"cs\">Czech<option value=\"da\">Danish<option value=\"nl\">Dutch<option value=\"en\">English<option value=\"en_US\">English (American)<option value=\"en_GB\">English (British)<option value=\"en_CA\">English (Canadian)<option value=\"eo\">Esperanto<option value=\"fo\">Faroese<option value=\"fr\">French<option value=\"fr_FR\">French (French)<option value=\"fr_CH\">French (Swiss)<option value=\"gl\">Galician<option value=\"de\">German<option value=\"de_DE\">German (German)<option value=\"de_CH\">German (Swiss)<option value=\"el\">Greek<option value=\"is\">Icelandic<option value=\"id\">Indonesian<option value=\"ia\">Interlingua<option value=\"ga\">Irish<option value=\"it\">Italian<option value=\"mk\">Macedonian<option value=\"ms\">Malay<option value=\"mt\">Maltese<option value=\"gv\">Manx Gaelic<option value=\"mi\">Maori<option value=\"no\">Norwegian<option value=\"nb\">Norwegian Bokm&aring;l<option value=\"nn\">Norwegian Nynorsk<option value=\"pl\">Polish<option value=\"pt\">Portuguese<option value=\"pt_BR\">Portuguese (Brazilian)<option value=\"pt_PT\">Portuguese (Portuguese)<option value=\"ro\">Romanian<option value=\"ru\">Russian<option value=\"gd\">Scottish Gaelic<option value=\"tn\">Setswana<option value=\"sk\">Slovak<option value=\"sl\">Slovenian<option value=\"es\">Spanish<option value=\"sw\">Swahili<option value=\"sv\">Swedish<option value=\"tr\">Turkish<option value=\"uk\">Ukranian<option value=\"wa\">Walloon<option value=\"cy\">Welsh<option value=\"zu\">Zulu";


// Which (if any) context images and files folders are located under?
String context = "";
// context = "/myApplication";
// Please see "Asbru Web Content Editor - User & Developer Guide - 1.8 Relocation" for other required configuration for use under a context

// Where is your website root folder?
// Must be the full absolute folder path/name (not the URL) the website domain name address points to (typically, where the default website homepage is located)
// Set to website root folder if images and files folders are located under the website root folder
// Set to context folder if images and files folders are located under a context
String root_path = "";
//root_path = getServletConfig().getServletContext().getRealPath("/").replaceAll("[\\\\/]$", "");
//root_path = "D:\\Asbru\\Web Content Editor";

// Which (if any) temporary folder to use for upload?
// Must be the full absolute folder path/name of a temporary folder - not the final actual folder where files are stored
String enable_upload = "no";
//String enable_upload = "yes";
String upload_path = "";
//upload_path = getServletConfig().getServletContext().getRealPath("/upload/");
//upload_path = "D:\\Asbru\\Web Content Editor\\upload\\";

// Which (if any) folders to hide from users?
// Must be folder paths/names starting with "/" relative to the website root folder configured above as the "root_path"
String exclude_paths = "";
exclude_paths = "/hardcore,/webadmin,/WEB-INF";
exclude_paths += ",/App_Code,/App_Data,/App_Themes,/aspnet_client,/bizcard,/file.original,/image.original,/password,/personal,/upload,/upload.original";
exclude_paths += ",/SITEBIZCARD,/SITEDUMMY,/SITEPERS,/SITEPERSCOM,/SITEPERSDATA,/SITEPERSECOM,/SITEPERSSUITE,/SITEPROF,/SITEPROFCOM,/SITEPROFDATA,/SITEPROFECOM,/SITEPROFSUITE";

// Which (if any) pages folder to use for Insert Hyperlink?
// Must be folder path/name starting and ending with "/" relative to the website root folder configured above as the "root_path"
String pages_path = "";
pages_path = "/";

// Which (if any) images folder to use for Insert Hyperlink and Insert Media?
// Must be folder path/name starting and ending with "/" relative to the website root folder configured above as the "root_path"
String images_path = "";
images_path = "/image/";

// Which (if any) files folder to use for Insert Hyperlink?
// Must be folder path/name starting and ending with "/" relative to the website root folder configured above as the "root_path"
String files_path = "";
files_path = "/file/";

// Which (if any) page formats to allow for Insert Hyperlink?
String page_formats = "";
page_formats = "html,htm,txt";
page_formats += ",jsp";
page_formats += "," + HardCore.File.filenameextension_list(db);
page_formats += "," + HardCore.Image.filenameextension_list(db);
page_formats = page_formats.replaceAll(",,", ",");
//page_formats = ".*"; // allow all page formats

// Which (if any) file formats to allow for Insert Hyperlink?
String file_formats = "";
file_formats = "gif,jpg,jpeg,png,swf,class,txt,doc,pdf,zip,mov";
file_formats += "," + HardCore.File.filenameextension_list(db);
file_formats = file_formats.replaceAll(",,", ",");
//file_formats = ".*"; // allow all file formats

// Which (if any) image formats to allow for Insert Hyperlink/Media?
String image_formats = "";
image_formats = "gif,jpg,jpeg,png,swf,class,mov";
image_formats += "," + HardCore.Image.filenameextension_list(db);
image_formats = image_formats.replaceAll(",,", ",");
//image_formats = ".*"; // allow all image formats

// Add your own program code here to use different settings for different users etc. and for access restrictions

%>