package HardCore;

import java.math.BigDecimal;
import java.sql.*;
import java.text.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.*;
import javax.servlet.jsp.*;

public class UsageLog {

	public String[] devicePattern = {	"iphone",		"ipad",		"ipod",		"android(.*)mobile",		"android",			"blackberry",		"wince",			"windows ce",			"windows phone",		"windows(.*)touch",		"javame",		"j2me/midp",		"j2me",			"javafx"			};
	public String[] deviceType = {		"Phone",		"Tablet",	"",		"Phone",			"Tablet",			"Phone",		"Phone",			"Phone",			"Phone",			"Tablet",			"Phone",		"Phone",		"Phone",		"Phone"				};
	public String[] deviceId = {		"iPhone",		"iPad",		"iPod",		"AndroidPhone",			"AndroidTablet",		"BlackBerry",		"MicrosoftMobile",		"MicrosoftMobile",		"MicrosoftPhone",		"MicrosoftTablet",		"JavaPhone",		"JavaPhone",		"JavaPhone",		"JavaPhone"			};
	public String[] deviceVersion = {	"Apple iPhone",		"Apple iPad",	"Apple iPod",	"Google Android Phone",		"Google Android Tablet",	"BlackBerry Phone",	"Microsoft Windows Mobile",	"Microsoft Windows Mobile",	"Microsoft Windows Phone",	"Microsoft Windows Tablet",	"Java Mobile (JME)",	"Java Mobile (J2ME)",	"Java Mobile (J2ME)",	"Java Mobile (JavaFX)"		};

	public String[] osPattern = {		"win[_+ ]9x",	"winnt",	"win32",	"win(.*)98",	"win(.*)95",	"win(.*)16",	"win(.*)ce",	"linux",	"gnu.hurd",	"bsdi",	"gnu.kfreebsd",	"freebsd",	"openbsd",	"netbsd",	"dragonfly",	"gnu",	"aix",	"sunos",	"irix",	"osf",		"hp-ux",	"unix",			"x11",			"gnome\\-vfs",		"beos",	"os/2",	"amiga",	"atari",	"vms",	"commodore",	"qnx",		"inferno",	"palmos",	"syllable",	"blackberry",	"cp/m",	"crayos",	"dreamcast",	"risc[_+ ]?os",	"symbian",	"webtv",	"playstation",		"xbox",			"wii",		"vienna",	"newsfire",	"applesyndication",	"akregator",	"plagger",		"syndirella",	"j2me",		"java",		"microsoft",			"msie[_+ ]",			"ms[_+ ]frontpage"			};
	public String[] osId = {		"winme",	"winnt",	"winnt",	"win98",	"win95",	"win16",	"wince",	"Linux",	"gnu",		"bsdi",	"bsdkfreebsd",	"bsdfreebsd",	"bsdopenbsd",	"bsdnetbsd",	"bsddflybsd",	"gnu",	"aix",	"sunos",	"irix",	"osf",		"hp-ux",	"unix",			"unix",			"unix",			"beos",	"os/2",	"amigaos",	"atari",	"vms",	"commodore",	"qnx",		"inferno",	"palmos",	"syllable",	"blackberry",	"cp/m",	"crayos",	"dreamcast",	"riscos",	"symbian",	"webtv",	"psp",			"winxbox",		"wii",		"macosx",	"macosx",	"macosx",		"linux",	"unix",			"winxp",	"j2me",		"java",		"winunknown",			"winunknown",			"winunknown"			};
	public String[] osName = {		"Windows",	"Windows",	"Windows",	"Windows",	"Windows",	"Windows",	"Windows",	"Linux",	"GNU",		"BSDi",	"GNU/kFreeBSD",	"FreeBSD",	"OpenBSD",	"NetBSD",	"DragonFlyBSD",	"GNU",	"Aix",	"Sun Solaris",	"Irix",	"OSF Unix",	"HP Unix",	"Unix",			"Unix",			"Unix",			"BeOS",	"OS/2",	"AmigaOS",	"Atari",	"VMS",	"Commodore 64",	"QNX",		"Inferno",	"Palm OS",	"Syllable",	"BlackBerry",	"CPM",	"CrayOS",	"Dreamcast",	"RISC OS",	"Symbian OS",	"WebTV",	"Sony PlayStation",	"Microsoft XBOX",	"Nintendo Wii",	"Mac OS X",	"Mac OS X",	"Mac OS X",		"Linux",	"Unix",			"Windows",	"Java Mobile",	"Java",		"Windows",			"Windows",			"Windows"			};
	public String[] osVersion = {		"Windows ME",	"Windows NT",	"Windows NT",	"Windows 98",	"Windows 95",	"Windows 3.xx",	"Windows CE",	"Linux",	"GNU Hurd",	"BSDi",	"GNU/kFreeBSD",	"FreeBSD",	"OpenBSD",	"NetBSD",	"DragonFlyBSD",	"GNU",	"Aix",	"Sun Solaris",	"Irix",	"OSF Unix",	"HP Unix",	"Unix (unknown)",	"Unix (unknown)",	"Unix (unknown)",	"BeOS",	"OS/2",	"AmigaOS",	"Atari",	"VMS",	"Commodore 64",	"QNX",		"Inferno",	"Palm OS",	"Syllable",	"BlackBerry",	"CPM",	"CrayOS",	"Dreamcast",	"RISC OS",	"Symbian OS",	"WebTV",	"Sony PlayStation",	"Microsoft XBOX",	"Nintendo Wii",	"Mac OS X",	"Mac OS X",	"Mac OS X",		"Linux",	"Unix (unknown)",	"Windows XP",	"Java Mobile",	"Java",		"Windows (unknown)",		"Windows (unknown)",		"Windows (unknown)"		};

	public String[] browserPattern = {	"konqueror",	"opera",	"elinks",	"firebird",			"go!zilla",	"icab",	"links",	"lynx",	"omniweb",	"22acidownload",	"abrowse",	"aol\\-iweng",	"amaya",	"amigavoyager",	"arora",	"aweb",	"charon",	"donzilla",	"seamonkey",	"flock",	"minefield",				"bonecho",				"granparadiso",					"songbird",	"strata",	"sylera",	"kazehakase",	"prism",	"icecat",	"iceape",	"iceweasel",	"w3clinemode",	"bpftp",	"camino",	"chimera",		"cyberdog",	"dillo",	"xchaos_arachne",	"doris",		"dreamcast",	"xbox",		"downloadagent",	"ecatch",	"emailsiphon",	"encompass",	"epiphany",	"friendlyspider",	"fresco",	"galeon",	"flashget",	"freshdownload",	"getright",	"leechget",	"netants",	"headdump",	"hotjava",	"ibrowse",	"intergo",		"k\\-meleon",	"k\\-ninja",	"linemodebrowser",		"lotus\\-notes",		"macweb",	"multizilla",	"ncsa_mosaic",	"netcaptor",	"netnewswire",	"netpositive",	"nutscrape",	"msfrontpageexpress",	"contiki",	"emacs\\-w3",	"phoenix",	"shiira",	"tzgeturl",	"viking",	"webfetcher",	"webexplorer",		"webmirror",	"webvcr",	"qnx\\svoyager",	"teleport",	"webcapture",		"webcopier",	"real",						"winamp",			"windows\\-media\\-player",		"audion",			"freeamp",			"itunes",			"jetaudio",			"mint_audio",			"mpg123",			"mplayer",				"nsplayer",				"qts",				"quicktime",			"sonique",			"uplayer",			"xaudio",						"xine",			"xmms",			"gstreamer",			"abilon",		"aggrevator",			"aiderss",		"akregator",			"applesyndication",			"betanews_reader",			"blogbridge",			"cyndicate",			"feeddemon",			"feedreader",			"feedtools",			"greatnews",			"gregarius",			"hatena_rss",		"jetbrains_omea",	"liferea",		"netnewswire",			"newsfire",			"newsgator",			"newzcrawler",			"plagger",		"pluck",		"potu",			"pubsub\\-rss\\-reader",	"pulpfiction",			"rssbandit",			"rssreader",			"rssowl",		"rss\\sxpress",			"rssxpress",			"sage",			"sharpreader",			"shrook",		"straw",		"syndirella",			"vienna",		"wizz\\srss\\snews\\sreader",		"alcatel",				"lg\\-",			"mot\\-",				"nokia",				"panasonic",					"philips",				"sagem",			"samsung",			"sie\\-",			"sec\\-",				"sonyericsson",					"ericsson",				"mmef",							"mspie",						"vodafone",					"wapalizer",				"wapsilon",			"wap",						
"webcollage",				"up\\.",				"android",				"blackberry",				"cnf2",					"docomo",				"ipcheck",			"iphone",			"portalmmm",				"webtv",		"democracy",	"cjb\\.net",		"ossproxy",	"smallproxy",	"adobeair",	"apt",		"analogx_proxy",	"gnome\\-vfs",				"neon",					"curl",	"csscheck",		"httrack",	"fdm",				"javaws",		"wget",	"fget",		"chilkat",	"webdownloader\\sfor\\sx",	"w3m",	"wdg_validator",	"w3c_validator",	"w3c_css_validator",	"jigsaw",		"webreaper",	"webzip",	"staroffice",	"gnus",				"nikto",		"download\\smaster",	"microsoft\\-webdav\\-miniredir",				"microsoft\\sdata\\saccess\\sinternet\\spublishing\\sprovider\\scache\\smanager",	"microsoft\\sdata\\saccess\\sinternet\\spublishing\\sprovider\\sdav",	"poe\\-component\\-client\\-http",					"chrome",	"safari",	"mozilla",	"libwww",	"lwp",		"special:donotmatch:msie",	"special:donotmatch:firefox",	"special:donotmatch:netscape",	"special:donotmatch:mozilla",	"special:donotmatch:unknown"	};
	public String[] browserName = {		"Konqueror",	"Opera",	"ELinks",	"Firebird (Old Firefox)",	"Go!Zilla",	"iCab",	"Links",	"Lynx",	"OmniWeb",	"22AciDownload",	"ABrowse",	"AOL-Iweng",	"Amaya",	"AmigaVoyager",	"Arora",	"AWeb",	"Charon",	"Donzilla",	"SeaMonkey",	"Flock",	"Minefield (Firefox 3.0 development)",	"BonEcho (Firefox 2.0 development)",	"GranParadiso (Firefox 3.0 development)",	"Songbird",	"Strata",	"Sylera",	"Kazehakase",	"Prism",	"GNU IceCat",	"GNU IceApe",	"Iceweasel",	"W3CLineMode",	"BPFTP",	"Camino",	"Chimera (Old Camino)",	"Cyberdog",	"Dillo",	"Arachne",		"Doris (for Symbian)",	"Dreamcast",	"XBoX",		"DownloadAgent",	"eCatch",	"EmailSiphon",	"Encompass",	"Epiphany",	"FriendlySpider",	"ANT Fresco",	"Galeon",	"FlashGet",	"FreshDownload",	"GetRight",	"LeechGet",	"NetAnts",	"HeadDump",	"Sun HotJava",	"iBrowse",	"InterGO",		"K-Meleon",	"K-Ninja",	"W3C Line Mode Browser",	"Lotus Notes web client",	"MacWeb",	"MultiZilla",	"NCSA Mosaic",	"NetCaptor",	"NetNewsWire",	"NetPositive",	"Nutscrape",	"MS FrontPage Express",	"Contiki",	"Emacs/w3s",	"Phoenix",	"Shiira",	"TzGetURL",	"Viking",	"WebFetcher",	"IBM-WebExplorer",	"WebMirror",	"WebVCR",	"QNX Voyager",		"TelePort Pro",	"Acrobat Webcapture",	"WebCopier",	"Real player or compatible (media player)",	"WinAmp (media player)",	"Windows Media Player (media player)",	"Audion (media player)",	"FreeAmp (media player)",	"Apple iTunes (media player)",	"JetAudio (media player)",	"Mint Audio (media player)",	"mpg123 (media player)",	"The Movie Player (media player)",	"NetShow Player (media player)",	"QuickTime (media player)",	"QuickTime (media player)",	"Sonique (media player)",	"Ultra Player (media player)",	"Some XAudio Engine based MPEG player (media player)",	"Xine (media player)",	"XMMS (media player)",	"GStreamer (media library)",	"Abilon (RSS Reader)",	"Aggrevator (RSS Reader)",	"AideRSS (RSS Reader)",	"Akregator (RSS Reader)",	"AppleSyndication (RSS Reader)",	"Betanews Reader (RSS Reader)",		"BlogBridge (RSS Reader)",	"Cyndicate (RSS Reader)",	"FeedDemon (RSS Reader)",	"FeedReader (RSS Reader)",	"FeedTools (RSS Reader)",	"GreatNews (RSS Reader)",	"Gregarius (RSS Reader)",	"Hatena (RSS Reader)",	"Omea (RSS Reader)",	"Liferea (RSS Reader)",	"NetNewsWire (RSS Reader)",	"NewsFire (RSS Reader)",	"NewsGator (RSS Reader)",	"NewzCrawler (RSS Reader)",	"Plagger (RSS Reader)",	"Pluck (RSS Reader)",	"Potu (RSS Reader)",	"PubSub (RSS Reader)",		"PulpFiction (RSS Reader)",	"RSS Bandit (RSS Reader)",	"RssReader (RSS Reader)",	"RSSOwl (RSS Reader)",	"RSS Xpress (RSS Reader)",	"RSSXpress (RSS Reader)",	"Sage (RSS Reader)",	"SharpReader (RSS Reader)",	"Shrook (RSS Reader)",	"Straw (RSS Reader)",	"Syndirella (RSS Reader)",	"Vienna (RSS Reader)",	"Wizz RSS News Reader (RSS Reader)",	"Alcatel Browser (PDA/Phone browser)",	"LG (PDA/Phone browser)",	"Motorola Browser (PDA/Phone browser)",	"Nokia Browser (PDA/Phone browser)",	"Panasonic Browser (PDA/Phone browser)",	"Philips Browser (PDA/Phone browser)",	"Sagem (PDA/Phone browser)",	"Samsung (PDA/Phone browser)",	"SIE (PDA/Phone browser)",	"Sony/Ericsson (PDA/Phone browser)",	"Sony/Ericsson Browser (PDA/Phone browser)",	"Ericsson Browser (PDA/Phone browser)",	"Microsoft Mobile Explorer (PDA/Phone browser)",	"MS Pocket Internet Explorer (PDA/Phone browser)",	"Vodaphone browser (PDA/Phone browser)",	"WAPalizer (PDA/Phone browser)",	"WAPsilon (PDA/Phone browser)",	"Unknown WAP browser (PDA/Phone browser)",	
"WebCollage (PDA/Phone browser)",	"UP.Browser (PDA/Phone browser)",	"Android browser (PDA/Phone browser)",	"BlackBerry (PDA/Phone browser)",	"Supervision I-Mode ByTel (phone)",	"I-Mode phone (PDA/Phone browser)",	"Supervision IP Check (phone)",	"IPhone (PDA/Phone browser)",	"I-Mode phone (PDA/Phone browser)",	"WebTV browser",	"Democracy",	"CJB.NET Proxy",	"OSSProxy",	"SmallProxy",	"AdobeAir",	"Debian APT",	"AnalogX Proxy",	"Gnome FileSystem Abstraction library",	"Neon HTTP and WebDAV client library",	"Curl",	"WDG CSS Validator",	"HTTrack",	"FDM Free Download Manager",	"Java Web Start",	"Wget",	"FGet",		"Chilkat",	"Downloader for X",		"w3m",	"WDG HTML Validator",	"W3C HTML Validator",	"W3C CSS Validator",	"W3C Validator",	"WebReaper",	"WebZIP",	"StarOffice",	"Gnus Network User Services",	"Nikto Web Scanner",	"Download Master",	"Microsoft Data Access Component Internet Publishing Provider",	"Microsoft Data Access Component Internet Publishing Provider Cache Manager",		"Microsoft Data Access Component Internet Publishing Provider DAV",	"HTTP user-agent for POE (portable networking framework for Perl)",	"Chrome",	"Safari",	"Mozilla",	"LibWWW",	"LibWWW-perl",	"MSIE",				"Firefox",			"Netscape",			"Mozilla",			"unknown"			};
	public String[] browserVersion = {};

	public String[] robotPattern = {	"appie",		"architext",	"jeeves",		"bjaaland",	"contentmatch",				"ferret",	"googlebot",		"google\\-sitemaps",	"gulliver",		"virus[_+ ]detector",			"harvest",	"htdig",	"linkwalker",	"lilina",	"lycos[_+ ]",		"moget",	"muscatferret",		"myweb",	"nomad",	"scooter",		"slurp",		"^voyager\\/",	"weblayers",	"antibot",	"bruinbot",		"digout4u",	"echo",		"fast\\-webcrawler",	"ia_archiver\\-web\\.archive\\.org",	"ia_archiver",		"jennybot",	"mercator",	"netcraft",	"msnbot\\-media",		"msnbot",	"petersnews",	"relevantnoise\\.com",		"unlost_web_crawler",	"voila",	"webbase",	"webcollage",			"cfetch",			"zyborg",		"wisenutbot",		"yahoo\\-verticalcrawler",	"[^a]fish",	"abcdatos",	"acme\\.spider",	"ahoythehomepagefinder",	"alkaline",	"anthill",	"arachnophilia",	"arale",	"araneo",	"aretha",	"ariadne",	"powermarks",			"arks",	"aspider",	"atn\\.txt",		"atomz",	"auresys",	"backrub",	"bbot",	"bigbrother",	"blackwidow",	"blindekuh",	"bloodhound",	"borg\\-bot",	"brightnet",	"bspider",	"cactvschemistryspider",	"calif[^r]",	"cassandra",	"cgireader",		"checkbot",	"christcrawler",	"churl",	"cienciaficcion",	"collective",	"combine",		"conceptbot",	"coolbot",	"core",			"cosmos",	"cruiser",		"cusco",	"cyberspyder",	"desertrealm",	"deweb",	"dienstspider",	"digger",	"diibot",		"direct_hit",		"dnabot",	"download_express",	"dragonbot",	"dwcp",			"e\\-collector",	"ebiness",	"elfinbot",	"emacs",	"emcspider",	"esther",	"evliyacelebi",		"fastcrawler",	"feedcrawl",			"fdse",			"felix",	"fetchrover",	"fido",	"finnish",	"fireball",	"fouineur",	"francoroute",	"freecrawl",	"funnelweb",	"gama",		"gazz",	"gcreep",	"getbot",	"geturl",	"golem",	"gougou",	"grapnel",	"griffon",	"gromit",	"gulperbot",	"hambot",	"havindex",	"hometown",	"htmlgobble",	"hyperdecontextualizer",	"iajabot",	"iaskspider",		"hl_ftien_spider",	"sogou",		"iconoclast",		"ilse",		"imagelock",	"incywincy",	"informant",	"infoseek",		"infoseeksidewinder",	"infospider",	"inspectorwww",		"intelliagent",	"irobot",	"iron33",	"israelisearch",	"javabee",	"jbot",	"jcrawler",	"jobo",	"jobot",	"joebot",	"jubii",	"jumpstation",	"kapsi",		"katipo",	"kilroy",	"ko[_+ ]yappo[_+ ]robot",	"kummhttp",		"labelgrabber\\.txt",	"larbin",	"legs",	"linkidator",		"linkscan",	"lockon",	"logo_gif",	"macworm",	"magpie",	"marvin",		"mattie",	"mediafox",	"merzscope",	"meshexplorer",		"mindcrawler",	"mnogosearch",	"momspider",	"monster",	"motor",	"muncher",	"mwdsearch",	"ndspider",	"nederland\\.zoek",	"netcarta",		"netmechanic",	"netscoop",	"newscan\\-online",	"nhse",			"northstar",		"nzexplorer",	"objectssearch",	"occam",	"octopus",		"openfind",	"orb_search",	"packrat",	"pageboy",	"parasite",	"patric",	"pegasus",	"perignator",	"perlcrawler",	"phantom",	"phpdig",	"piltdownman",	"pimptrain",		"pioneer",	"pitkow",		"pjspider",		"plumtreewebaccessor",
"poppi",	"portalb",	"psbot",	"python",	"raven",	"rbse",	"resumerobot",	"rhcs",		"road_runner",	"robbie",	"robi",			"robocrawl",	"robofox",	"robozilla",	"roverbot",	"rules",	"safetynetrobot",	"search\\-info",	"search_au",		"searchprocess",	"senrigan",	"sgscout",	"shaggy",	"shaihulud",	"sift",	"simbot",		"site\\-valet",	"sitetech",		"skymob",	"slcrawler",	"smartspider",	"snooper",	"solbot",	"speedy",	"spider[_+ ]monkey",	"spiderbot",	"spiderline",	"spiderman",	"spiderview",	"spry",		"sqworm",		"ssearcher",		"suke",	"sunrise",		"suntek",	"sven",	"tach_bw",		"tagyu_agent",		"tailrank",		"tarantula",	"tarspider",	"techbot",	"templeton",	"titan",	"titin",	"tkwww",	"tlspider",	"ucsd",		"udmsearch",	"universalfeedparser",	"urlck",	"valkyrie",	"verticrawl",	"victoria",	"visionsearch",		"voidbot",	"vwbot",	"w3index",	"w3m2",	"wallpaper",			"wanderer",	"wapspirlider",	"webbandit",	"webcatcher",	"webcopy",	"webfetcher",	"webfoot",	"webinator",	"weblinker",	"webmirror",	"webmoose",	"webquest",	"webreader",		"webreaper",	"websnarf",	"webspider",	"webvac",	"webwalk",	"webwalker",	"webwatch",	"whatuseek",		"whowhere",	"wired\\-digital",	"wmir",		"wolp",		"wombat",	"wordpress",		"worm",			"woozweb",		"wwwc",	"wz101",	"xget",	"aport",	"awbot",	"baiduspider",	"bobby",	"boris",	"bumblebee",	"cscrawler",	"daviesbot",	"exactseek",	"ezresult",	"gigabot",	"gnodspider",	"grub",		"henrythemiragorobot",	"holmes",	"internetseer",	"justview",	"linkbot",	"linkchecker",	"mediapartners\\-google",	"metager\\-linkchecker",	"microsoft_url_control",	"nagios",	"msiecrawler",	"perman",	"pompos",	"rambler",	"redalert",	"shoutcast",	"slysearch",	"surveybot",	"turnitinbot",	"turtle",	"turtlescanner",	"ultraseek",	"webclipping\\.com",	"webcompass",	"wonderer",		"yandex",	"zealbot",	"robot",		"crawl",		"spider"		};
	public String[] robotName = {		"Walhello appie",	"Architext",	"AskJeeves",		"Bjaaland",	"Yahoo!China ContentMatch Crawler",	"Wild Ferret",	"Google",		"Google Sitemaps",	"Northern Light",	"virus_detector",			"Harvest",	"ht://Dig",	"LinkWalker",	"Lilina",	"Lycos",		"Moget",	"Muscat Ferret",	"Shinchakubin",	"Nomad",	"AltaVista",		"Inktomi Slurp",	"Voyager",	"WebLayers",	"Antibot",	"The web archive",	"Digout4u",	"EchO!",	"AllTheWeb",		"The web archive (IA Archiver)",	"Alexa",		"JennyBot",	"Mercator",	"Netcraft",	"MSNBot-media",			"MSN",		"Petersnews",	"Relevant Noise",		"Unlost",		"Voila",	"WebBase",	"WebCollage",			"Cfetch",			"Looksmart",		"Looksmart",		"Yahoo",			"Fish search",	"ABCdatos",	"Acme.Spider",		"Ahoy!",			"Alkaline",	"Anthill",	"Arachnophilia",	"Arale",	"Araneo",	"Aretha",	"ARIADNE",	"Powermarks",			"Arks",	"ASpider",	"ATN Worldwide",	"Atomz.com",	"AURESYS",	"BackRub",	"BBot",	"Big Brother",	"BlackWidow",	"Blinde Kuh",	"Bloodhound",	"Borg-Bot",	"Bright.net",	"BSpider",	"CACTVS Chemistry",		"Calif",	"Cassandra",	"Digimarc Marcspider",	"Checkbot",	"ChristCrawler.com",	"Churl",	"Cienciaficcion.net",	"Collective",	"Combine System",	"Conceptbot",	"CoolBot",	"Web Core / Roots",	"XYLEME",	"Internet Cruiser",	"Cusco",	"CyberSpyder",	"Desert Realm",	"DeWeb",	"DienstSpider",	"Digger",	"Digital Integrity",	"Direct Hit Grabber",	"DNAbot",	"DownLoad Express",	"DragonBot",	"DWCP (Dridus)",	"E-collector",		"EbiNess",	"ELFINBOT",	"Emacs-w3",	"Ananzi",	"Esther",	"Evliya Celebi",	"FastCrawler",	"FeedCrawl by feed@aobo.com",	"Fluid Dynamics",	"Felix IDE",	"FetchRover",	"Fido",	"Hoooki",	"KIT-Fireball",	"Fouineur",	"Francoroute",	"Freecrawl",	"FunnelWeb",	"GammaSpider",	"Gazz",	"GCreep",	"GetBot",	"GetURL",	"Golem",	"GouGou",	"Grapnel",	"Griffon",	"Gromit",	"Gulper Bot",	"HamBot",	"havIndex",	"Hometown",	"HTMLgobble",	"Hyper-Decontextualizer",	"IajaBot",	"Sina Iask Spider",	"Hylanda",		"Sogou Spider",		"Popular Iconoclast",	"Ingrid",	"Imagelock",	"IncyWincy",	"Informant",	"InfoSeek Robot 1.0",	"InfoSeek Sidewinder",	"InfoSpiders",	"Inspector Web",	"IntelliAgent",	"I, Robot",	"Iron33",	"Israeli-search",	"JavaBee",	"JBot",	"JCrawler",	"JoBo",	"Jobot",	"JoeBot",	"Jubii",	"JumpStation",	"Image.kapsi.net",	"Katipo",	"Kilroy",	"KO Yappo Robot",		"KummHttp",		"LabelGrabber",		"larbin",	"legs",	"Link Validator",	"LinkScan",	"Lockon",	"Logo.gif",	"Mac WWWWorm",	"Magpie",	"Marvin/InfoSeek",	"Mattie",	"MediaFox",	"MerzScope",	"NEC-MeshExplorer",	"MindCrawler",	"MnoGoSearch",	"MOMspider",	"Monster",	"Motor",	"Muncher",	"Mwd.Search",	"NDSpider",	"Nederland.zoek",	"NetCarta WebMap",	"NetMechanic",	"NetScoop",	"Newscan-online",	"NHSE Web Forager",	"NorthStar Robot",	"Nzexplorer",	"ObjectsSearch",	"Occam",	"HKU WWW Octopus",	"Openfind",	"Orb Search",	"Pack Rat",	"PageBoy",	"ParaSite",	"Patric",	"Pegasus",	"Peregrinator",	"PerlCrawler",	"Phantom",	"PhpDig",	"PiltdownMan",	"Pimptrain.com",	"Pioneer",	"Html Analyzer",	"Portal Juice Spider",	"PlumtreeWebAccessor",
"Poppi",	"PortalB",	"Psbot",	"Python",	"Raven Search",	"RBSE",	"Resume Robot",	"RoadHouse",	"Road Runner",	"Robbie",	"ComputingSite Robi",	"RoboCrawl",	"RoboFox",	"Robozilla",	"Roverbot",	"RuLeS",	"SafetyNet Robot",	"Sleek",		"Search.aus-au.com",	"SearchProcess",	"Senrigan",	"SG-Scout",	"ShagSeeker",	"ShaiHulud",	"Sift",	"Simmany Robot",	"Site Valet",	"SiteTech-Rover",	"Skymob.com",	"SLCrawler",	"Smart Spider",	"Snooper",	"Solbot",	"Speedy",	"Spider Monkey",	"SpiderBot",	"Spiderline",	"SpiderMan",	"SpiderView",	"Spry Wizard",	"Sqworm",		"Site Searcher",	"Suke",	"Sunrise",		"Suntek",	"Sven",	"TACH Black Widow",	"Tagyu Agent",		"TailRank",		"Tarantula",	"Tarspider",	"TechBOT",	"Templeton",	"TITAN",	"TitIn",	"TkWWW",	"TLSpider",	"UCSD Crawl",	"UdmSearch",	"UniversalFeedParser",	"URL Check",	"Valkyrie",	"Verticrawl",	"Victoria",	"Vision-search",	"Void-bot",	"VWbot",	"NWI Robot",	"W3M2",	"WallPaper (alias crawlpaper)",	"WWW Wanderer",	"W@pSpider",	"WebBandit",	"WebCatcher",	"WebCopy",	"WebFetcher",	"Webfoot",	"Webinator",	"WebLinker",	"WebMirror",	"Web Moose",	"WebQuest",	"Digimarc MarcSpider",	"WebReaper",	"Websnarf",	"WebSpider",	"WebVac",	"WebWalk",	"WebWalker",	"WebWatch",	"WhatUseek Winona",	"WhoWhere",	"Wired Digital",	"W3mir",	"WebStolperer",	"Web Wombat",	"WordPress",		"World Wide Web Worm",	"Woozweb Monitoring",	"WWWC",	"WebZinger",	"XGET",	"Aport",	"AWBot",	"BaiDuSpider",	"Bobby",	"Boris",	"Bumblebee",	"CsCrawler",	"DaviesBot",	"ExactSeek",	"Ezresult",	"GigaBot",	"GNOD",		"Grub.org",	"Mirago",		"Holmes",	"InternetSeer",	"JustView",	"LinkBot",	"LinkChecker",	"Google AdSense",		"MetaGer LinkChecker",		"Microsoft URL Control",	"Nagios",	"MSIECrawler",	"Perman",	"Pompos",	"StackRambler",	"Red Alert",	"Shoutcast",	"SlySearch",	"SurveyBot",	"Turn It In",	"Turtle",	"Turtle",		"Ultraseek",	"WebClipping.com",	"WebCompass",	"Web Wombat Redback",	"Yandex",	"ZealBot",	"Unknown robot",	"Unknown crawler",	"Unknown spider"	};
	public String[] robotVersion = {	"Walhello appie",	"Architext",	"AskJeeves",		"Bjaaland",	"Yahoo!China ContentMatch Crawler",	"Wild Ferret",	"Googlebot",		"Google Sitemaps",	"Northern Light",	"virus_detector",			"Harvest",	"ht://Dig",	"LinkWalker",	"Lilina",	"Lycos",		"Moget",	"Muscat Ferret",	"Shinchakubin",	"Nomad",	"Scooter",		"Inktomi Slurp",	"Voyager",	"WebLayers",	"Antibot",	"The web archive",	"Digout4u",	"EchO!",	"Fast-Webcrawler",	"The web archive (IA Archiver)",	"Alexa",		"JennyBot",	"Mercator",	"Netcraft",	"MSNBot-media",			"MSNBot",	"Petersnews",	"Relevant Noise",		"Unlost",		"Voila",	"WebBase",	"WebCollage",			"Cfetch",			"Zyborg",		"WISENutbot",		"Vertical Crawler",		"Fish search",	"ABCdatos",	"Acme.Spider",		"Ahoy!",			"Alkaline",	"Anthill",	"Arachnophilia",	"Arale",	"Araneo",	"Aretha",	"ARIADNE",	"Powermarks",			"Arks",	"ASpider",	"ATN Worldwide",	"Atomz.com",	"AURESYS",	"BackRub",	"BBot",	"Big Brother",	"BlackWidow",	"Blinde Kuh",	"Bloodhound",	"Borg-Bot",	"Bright.net",	"BSpider",	"CACTVS Chemistry",		"Calif",	"Cassandra",	"Digimarc Marcspider",	"Checkbot",	"ChristCrawler.com",	"Churl",	"Cienciaficcion.net",	"Collective",	"Combine System",	"Conceptbot",	"CoolBot",	"Web Core / Roots",	"XYLEME",	"Internet Cruiser",	"Cusco",	"CyberSpyder",	"Desert Realm",	"DeWeb",	"DienstSpider",	"Digger",	"Digital Integrity",	"Direct Hit Grabber",	"DNAbot",	"DownLoad Express",	"DragonBot",	"DWCP (Dridus)",	"E-collector",		"EbiNess",	"ELFINBOT",	"Emacs-w3",	"Ananzi",	"Esther",	"Evliya Celebi",	"FastCrawler",	"FeedCrawl by feed@aobo.com",	"Fluid Dynamics",	"Felix IDE",	"FetchRover",	"Fido",	"Hooki",	"KIT-Fireball",	"Fouineur",	"Francoroute",	"Freecrawl",	"FunnelWeb",	"GammaSpider",	"Gazz",	"GCreep",	"GetBot",	"GetURL",	"Golem",	"GouGou",	"Grapnel",	"Griffon",	"Gromit",	"Gulper Bot",	"HamBot",	"havIndex",	"Hometown",	"HTMLgobble",	"Hyper-Decontextualizer",	"IajaBot",	"Sina Iask Spider",	"Hylanda",		"Sogou Spider",		"Popular Iconoclast",	"Ingrid",	"Imagelock",	"IncyWincy",	"Informant",	"InfoSeek Robot 1.0",	"InfoSeek Sidewinder",	"InfoSpiders",	"Inspector Web",	"IntelliAgent",	"I, Robot",	"Iron33",	"Israeli-search",	"JavaBee",	"JBot",	"JCrawler",	"JoBo",	"Jobot",	"JoeBot",	"Jubii",	"JumpStation",	"Image.kapsi.net",	"Katipo",	"Kilroy",	"KO Yappo Robot",		"KummHttp",		"LabelGrabber",		"larbin",	"legs",	"Link Validator",	"LinkScan",	"Lockon",	"Logo.gif",	"Mac WWWWorm",	"Magpie",	"Marvin/InfoSeek",	"Mattie",	"MediaFox",	"MerzScope",	"NEC-MeshExplorer",	"MindCrawler",	"MnoGoSearch",	"MOMspider",	"Monster",	"Motor",	"Muncher",	"Mwd.Search",	"NDSpider",	"Nederland.zoek",	"NetCarta WebMap",	"NetMechanic",	"NetScoop",	"Newscan-online",	"NHSE Web Forager",	"NorthStar Robot",	"Nzexplorer",	"ObjectsSearch",	"Occam",	"HKU WWW Octopus",	"Openfind",	"Orb Search",	"Pack Rat",	"PageBoy",	"ParaSite",	"Patric",	"Pegasus",	"Peregrinator",	"PerlCrawler",	"Phantom",	"PhpDig",	"PiltdownMan",	"Pimptrain.com",	"Pioneer",	"Html Analyzer",	"Portal Juice Spider",	"PlumtreeWebAccessor",
"Poppi",	"PortalB",	"Psbot",	"Python",	"Raven Search",	"RBSE",	"Resume Robot",	"RoadHouse",	"Road Runner",	"Robbie",	"ComputingSite Robi",	"RoboCrawl",	"RoboFox",	"Robozilla",	"Roverbot",	"RuLeS",	"SafetyNet Robot",	"Sleek",		"Search.aus-au.com",	"SearchProcess",	"Senrigan",	"SG-Scout",	"ShagSeeker",	"ShaiHulud",	"Sift",	"Simmany Robot",	"Site Valet",	"SiteTech-Rover",	"Skymob.com",	"SLCrawler",	"Smart Spider",	"Snooper",	"Solbot",	"Speedy",	"Spider Monkey",	"SpiderBot",	"Spiderline",	"SpiderMan",	"SpiderView",	"Spry Wizard",	"Sqworm",		"Site Searcher",	"Suke",	"Sunrise",		"Suntek",	"Sven",	"TACH Black Widow",	"Tagyu Agent",		"TailRank",		"Tarantula",	"Tarspider",	"TechBOT",	"Templeton",	"TITAN",	"TitIn",	"TkWWW",	"TLSpider",	"UCSD Crawl",	"UdmSearch",	"UniversalFeedParser",	"URL Check",	"Valkyrie",	"Verticrawl",	"Victoria",	"Vision-search",	"Void-bot",	"VWbot",	"NWI Robot",	"W3M2",	"WallPaper (alias crawlpaper)",	"WWW Wanderer",	"W@pSpider",	"WebBandit",	"WebCatcher",	"WebCopy",	"WebFetcher",	"Webfoot",	"Webinator",	"WebLinker",	"WebMirror",	"Web Moose",	"WebQuest",	"Digimarc MarcSpider",	"WebReaper",	"Websnarf",	"WebSpider",	"WebVac",	"WebWalk",	"WebWalker",	"WebWatch",	"WhatUseek Winona",	"WhoWhere",	"Wired Digital",	"W3mir",	"WebStolperer",	"Web Wombat",	"WordPress",		"World Wide Web Worm",	"Woozweb Monitoring",	"WWWC",	"WebZinger",	"XGET",	"Aport",	"AWBot",	"BaiDuSpider",	"Bobby",	"Boris",	"Bumblebee",	"CsCrawler",	"DaviesBot",	"ExactSeek",	"Ezresult",	"GigaBot",	"GNOD",		"Grub.org",	"Mirago",		"Holmes",	"InternetSeer",	"JustView",	"LinkBot",	"LinkChecker",	"Google AdSense",		"MetaGer LinkChecker",		"Microsoft URL Control",	"Nagios",	"MSIECrawler",	"Perman",	"Pompos",	"StackRambler",	"Red Alert",	"Shoutcast",	"SlySearch",	"SurveyBot",	"Turn It In",	"Turtle",	"Turtle",		"Ultraseek",	"WebClipping.com",	"WebCompass",	"Web Wombat Redback",	"Yandex",	"ZealBot",	"Unknown robot",	"Unknown crawler",	"Unknown spider"	};
	public String[] robotWebsite = {	"",			"",		"www.askjeeves.com",	"",		"p4p.cn.yahoo.com",			"",		"www.google.com",	"",			"",			"www.securecomputing.com",		"",		"",		"",		"",		"www.lycos.com",	"",		"",			"",		"",		"www.altavista.com",	"",			"",		"",		"",		"web.archive.org",	"",		"",		"www.alltheweb.com",	"web.archive.org",			"www.alexa.com",	"",		"",		"",		"search.msn.com/msnbot.htm",	"www.msn.com",	"",		"www.relevantnoise.com",	"",			"",		"",		"www.jwz.org/webcollage/",	"www.kosmix.com/crawler.html",	"www.looksmart.com",	"www.looksmart.com",	"www.yahoo.com",		"",		"",		"",			"",				"",		"",		"",			"",		"",		"",		"",		"www.kaylon.com/power.html",	"",	"",		"",			"Atomz.com",	"",		"",		"",	"",		"",		"",		"",		"",		"",		"",		"",				"",		"",		"",			"",		"ChristCrawler.com",	"",		"Cienciaficcion.net",	"",		"",			"",		"",		"",			"",		"",			"",		"",		"",		"",		"",		"",		"",			"",			"",		"",			"",		"",			"",			"",		"",		"",		"",		"",		"",			"",		"",				"",			"",		"",		"",	"",		"",		"",		"",		"",		"",		"",		"",	"",		"",		"",		"",		"",		"",		"",		"",		"",		"",		"",		"",		"",		"",				"",		"www.iask.com",		"www.hylanda.com",	"www.sogou.com",	"",			"",		"",		"",		"",		"",			"",			"",		"",			"",		"",		"",		"",			"",		"",	"",		"",	"",		"",		"",		"",		"",			"",		"",		"",				"www.psychedelix.com",	"",			"",		"",	"",			"",		"",		"",		"",		"",		"",			"",		"",		"",		"",			"",		"",		"",		"",		"",		"",		"",		"",		"",			"",			"",		"",		"",			"",			"",			"",		"",			"",		"",			"",		"",		"",		"",		"",		"",		"",		"",		"",		"",		"",		"",		"",			"",		"",			"",			"",
"",		"",		"",		"",		"",		"",	"",		"",		"",		"",		"",			"",		"",		"",		"",		"",		"",			"",			"",			"",			"",		"",		"",		"",		"",	"",			"",		"",			"",		"",		"",		"",		"",		"",		"",			"",		"",		"",		"",		"",		"www.websense.com",	"",			"",	"www.sunrisexp.com",	"",		"",	"",			"www.tagyu.com",	"tailrank.com/robot",	"",		"",		"",		"",		"",		"",		"",		"",		"",		"",		"feedparser.org",	"",		"",		"",		"",		"",			"",		"",		"",		"",	"",				"",		"",		"",		"",		"",		"",		"",		"",		"",		"",		"",		"",			"",		"",		"",		"",		"",		"",		"",		"",		"",			"",		"",			"",		"",		"",		"wordpress.org",	"",			"",			"",	"",		"",	"",		"",		"",		"",		"",		"",		"",		"",		"",		"",		"",		"",		"",		"",			"",		"",		"",		"",		"",		"",				"",				"",				"",		"",		"",		"",		"",		"",		"",		"",		"",		"",		"",		"",			"",		"WebClipping.com",	"",		"",			"",		"",		"",			"",			""			};

	public String[] searchenginePattern = {	"google\\.[\\w.]+/products",	"base\\.google\\.",	"froogle\\.google\\.",	"groups\\.google\\.",	"images\\.google\\.",	"google\\.",		"googlee\\.",		"googlecom\\.com",		"goggle\\.co\\.hu",		"216\\.239\\.(35|37|39|51)\\.100",		"216\\.239\\.(35|37|39|51)\\.101",		"216\\.239\\.5[0-9]\\.104",			"64\\.233\\.1[0-9]{2}\\.104",			"66\\.102\\.[1-9]\\.104",			"66\\.249\\.93\\.104",				"72\\.14\\.2[0-9]{2}\\.104",			"msn\\.",	"live\\.com",			"bing\\.",		"voila\\.",	"mindset\\.research\\.yahoo",	"yahoo\\.",	"(66\\.218\\.71\\.225|216\\.109\\.117\\.135|216\\.109\\.125\\.130|66\\.218\\.69\\.11)",	"search\\.aol\\.co",	"tiscali\\.",	"lycos\\.",	"alexa\\.com",	"alltheweb\\.com",	"altavista\\.",		"a9\\.com",	"dmoz\\.org",	"netscape\\.",	"search\\.terra\\.",	"www\\.search\\.com",	"search\\.sli\\.sympatico\\.ca",	"excite\\.",	"4\\-counter\\.com",	"att\\.net",				"bungeebonesdotcom",		"northernlight\\.",	"hotbot\\.",	"kvasir\\.",	"webcrawler\\.",	"metacrawler\\.",	"go2net\\.com",	"(^|\\.)go\\.com",	"euroseek\\.",	"looksmart\\.",		"spray\\.",	"nbci\\.com/search",	"(^|\\.)ask\\.com",	"(^|\\.)ask\\.co\\.uk",	"atomz\\.",	"overture\\.com",	"teoma\\.",	"findarticles\\.com",	"infospace\\.com",	"mamma\\.",	"dejanews\\.",	"dogpile\\.com",	"wisenut\\.com",	"ixquick\\.com",	"search\\.earthlink\\.net",	"i-une\\.com",		"blingo\\.com",		"centraldatabase\\.org",	"clusty\\.com",		"mysearch\\.",		"vivisimo\\.com",	"kartoo\\.com",		"icerocket\\.com",	"sphere\\.com",		"ledix\\.net",		"start\\.shaw\\.ca",	"searchalot\\.com",	"copernic\\.com",	"avantfind\\.com",	"steadysearch\\.com",	"steady-search\\.com",	"chello\\.",		"mirago\\.",		"answerbus\\.com",	"icq\\.com\\/search",	"nusearch\\.com",	"goodsearch\\.com",	"scroogle\\.org",	"questionanswering\\.com",	"mywebsearch\\.com",		"as\\.starware\\.com",		"del\\.icio\\.us",			"digg\\.com",			"stumbleupon\\.com",			"swik\\.net",			"segnalo\\.alice\\.it",		"ineffabile\\.it",			"anzwers\\.com\\.au",	"engine\\.exe",	"miner\\.bol\\.com\\.br",	"\\.baidu\\.com",	"\\.vnet\\.cn",	"\\.soso\\.com",	"\\.sogou\\.com",	"\\.3721\\.com",	"iask\\.com",	"\\.accoona\\.com",	"\\.163\\.com",	"\\.zhongsou\\.com",	"search\\.sina\\.com",	"search\\.sohu\\.com",	"atlas\\.cz",		"seznam\\.cz",	"quick\\.cz",	"centrum\\.cz",	"jyxo\\.(cz|com)",	"najdi\\.to",	"redbox\\.cz",	"opasia\\.dk",	"danielsen\\.com",	"sol\\.dk",	"jubii\\.dk",	"find\\.dk",	"edderkoppen\\.dk",	"netstjernen\\.dk",	"orbis\\.dk",		"tyfon\\.dk",	"1klik\\.dk",	"ofir\\.dk",	"ilse\\.",	"vindex\\.",	"(^|\\.)ask\\.co\\.uk",	"bbc\\.co\\.uk/cgi-bin/search",	"ifind\\.freeserve",	"looksmart\\.co\\.uk",	"mirago\\.",	"splut\\.",	"spotjockey\\.",	"ukdirectory\\.",	"ukindex\\.co\\.uk",	"ukplus\\.",	"searchy\\.co\\.uk",	"haku\\.www\\.fi",	"recherche\\.aol\\.fr",	"ctrouve\\.",	"francite\\.",	"\\.lbb\\.org",	"rechercher\\.libertysurf\\.fr",	"search[\\w\\-]+\\.free\\.fr",	"recherche\\.club-internet\\.fr",	"toile\\.com",		"biglotron\\.com",	"mozbot\\.fr",		"sucheaol\\.aol\\.de",	"fireball\\.de",	"infoseek\\.de",	"suche\\d?\\.web\\.de",	"[a-z]serv\\.rrzn\\.uni-hannover\\.de",	"suchen\\.abacho\\.de",	"brisbane\\.t-online\\.de",	"allesklar\\.de",	"meinestadt\\.de",	"212\\.227\\.33\\.241",
"(161\\.58\\.227\\.204|161\\.58\\.247\\.101|212\\.40\\.165\\.90|213\\.133\\.108\\.202|217\\.160\\.108\\.151|217\\.160\\.111\\.99|217\\.160\\.131\\.108|217\\.160\\.142\\.227|217\\.160\\.176\\.42)",	"wwweasel\\.de",	"netluchs\\.de",	"schoenerbrausen\\.de",		"heureka\\.hu",	"vizsla\\.origo\\.hu",	"lapkereso\\.hu",	"goliat\\.hu",	"index\\.hu",	"wahoo\\.hu",	"webmania\\.hu",	"search\\.internetto\\.hu",	"tango\\.hu",	"keresolap\\.hu",	"polymeta\\.hu",	"sify\\.com",		"virgilio\\.it",	"arianna\\.libero\\.it",	"supereva\\.com",	"kataweb\\.it",			"search\\.alice\\.it\\.master",	"search\\.alice\\.it",	"gotuneed\\.com",	"godado",	"jumpy\\.it",	"shinyseek\\.it",	"teecno\\.it",		"ask\\.jp",	"sagool\\.jp",	"sok\\.start\\.no",	"eniro\\.no",	"szukaj\\.wp\\.pl",	"szukaj\\.onet\\.pl",	"dodaj\\.pl",	"gazeta\\.pl",		"gery\\.pl",		"hoga\\.pl",	"netsprint\\.pl",	"interia\\.pl",			"katalog\\.onet\\.pl",	"o2\\.pl",		"polska\\.pl",		"szukacz\\.pl",		"wow\\.pl",		"ya(ndex)?\\.ru",	"aport\\.ru",	"rambler\\.ru",	"turtle\\.ru",	"metabot\\.ru",	"evreka\\.passagen\\.se",	"eniro\\.se",	"zoznam\\.sk",	"sapo\\.pt",	"search\\.ch",	"search\\.bluewin\\.ch",	"pogodak\\.",	"search\\..*\\.\\w+"	};
	public String[] searchengineId = {	"google_products",		"google_base",		"google_froogle",	"google_groups",	"google_image",		"google",		"google",		"google",			"google",			"google_cache",					"google_cache",					"google_cache",					"google_cache",					"google_cache",					"google_cache",					"google_cache",					"msn",		"live",				"bing",			"voila",	"yahoo_mindset",		"yahoo",	"yahoo",										"aol",			"tiscali",	"lycos",	"alexa",	"alltheweb",		"altavista",		"a9",		"dmoz",		"netscape",	"terra",		"search.com",		"sympatico",				"excite",	"google4counter",	"att",					"bungeebonesdotcom",		"northernlight",	"hotbot",	"kvasir",	"webcrawler",		"metacrawler",		"go2net",	"go",			"euroseek",	"looksmart",		"spray",	"nbci",			"ask",			"ask",			"atomz",	"overture",		"teoma",	"findarticles",		"infospace",		"mamma",	"dejanews",	"dogpile",		"wisenut",		"ixquick",		"earthlink",			"iune",			"blingo",		"centraldatabase",		"clusty",		"mysearch",		"vivisimo",		"kartoo",		"icerocket",		"sphere",		"ledix",		"shawca",		"searchalot",		"copernic",		"avantfind",		"steadysearch",		"steadysearch",		"chellocom",		"mirago",		"answerbus",		"icq",			"nusearch",		"goodsearch",		"scroogle",		"questionanswering",		"mywebsearch",			"comettoolbar",			"delicious",				"digg",				"stumbleupon",				"swik",				"segnalo",			"ineffabile",				"anzwers",		"engine",	"miner",			"baidu",		"vnet",		"soso",			"sogou",		"3721",			"iask",		"accoona",		"netease",	"zhongsou",		"sinacom",		"sohucom",		"atlas",		"seznam",	"quick",	"centrum",	"jyxo",			"najdi",	"redbox",	"opasia",	"danielsen",		"sol",		"jubii",	"finddk",	"edderkoppen",		"netstjernen",		"orbis",		"tyfon",	"1klik",	"ofir",		"ilse",		"vindex",	"ask",			"bbc",				"freeserve",		"looksmartuk",		"mirago",	"splut",	"spotjockey",		"ukdirectory",		"ukindex",		"ukplus",	"searchy",		"haku",			"aolfr",		"ctrouve",	"francite",	"lbb",		"libertysurf",				"free",				"clubinternet",				"toile",		"biglotron",		"mozbot",		"aolde",		"fireball",		"infoseek",		"webde",		"meta",					"abacho",		"t-online",			"allesklar",		"meinestadt",		"metaspinner",
"metacrawler_de",																							"wwweasel",		"netluchs",		"schoenerbrausen",		"heureka",	"origo",		"lapkereso",		"goliat",	"indexhu",	"wahoo",	"webmania",		"internetto",			"tango_hu",	"keresolap_hu",		"polymeta_hu",		"sify",			"virgilio",		"arianna",			"supereva",		"kataweb",			"aliceitmaster",		"aliceit",		"gotuneed",		"godado",	"jumpy.it",	"shinyseek.it",		"teecnoit",		"askjp",	"sagool",	"start",		"eniro",	"wp",			"onetpl",		"dodajpl",	"gazetapl",		"gerypl",		"hogapl",	"netsprintpl",		"interiapl",			"katalogonetpl",	"o2pl",			"polskapl",		"szukaczpl",		"wowpl",		"yandex",		"aport",	"rambler",	"turtle",	"metabot",	"passagen",			"enirose",	"zoznam",	"sapo",		"searchch",	"bluewin",			"pogodak",	"search"		};
	public String[] searchengineQuery = {	"(p|q|as_p|as_q)=",		"(p|q|as_p|as_q)=",	"(p|q|as_p|as_q)=",	"group\\/",		"(p|q|as_p|as_q)=",	"(p|q|as_p|as_q)=",	"(p|q|as_p|as_q)=",	"(p|q|as_p|as_q)=",		"(p|q|as_p|as_q)=",		"(p|q|as_p|as_q)=cache:[0-9A-Za-z]{12}:",	"(p|q|as_p|as_q)=cache:[0-9A-Za-z]{12}:",	"(p|q|as_p|as_q)=cache:[0-9A-Za-z]{12}:",	"(p|q|as_p|as_q)=cache:[0-9A-Za-z]{12}:",	"(p|q|as_p|as_q)=cache:[0-9A-Za-z]{12}:",	"(p|q|as_p|as_q)=cache:[0-9A-Za-z]{12}:",	"(p|q|as_p|as_q)=cache:[0-9A-Za-z]{12}:",	"q=",		"q=",				"q=",			"(kw|rdata)=",	"p=",				"p=",		"p=",											"query=",		"key=",		"query=",	"q=",		"q(|uery)=",		"q=",			"a9\\.com\\/",	"search=",	"search=",	"query=",		"q=",			"query=",				"search=",	"(p|q|as_p|as_q)=",	"qry=",					"query=",			"qr=",			"mt=",		"q=",		"searchText=",		"general=",		"general=",	"qt=",			"query=",	"key=",			"string=",	"keyword=",		"(ask|q)=",		"(ask|q)=",		"sp-q=",	"keywords=",		"q=",		"key=",			"qkw=",			"query=",	"",		"q(|kw)=",		"query=",		"query=",		"q=",				"(keywords|q)=",	"q=",			"query=",			"query=",		"searchfor=",		"query=",		"",			"q=",			"q=",			"q=",			"q=",			"q=",			"web\\/",		"keywords=",		"w=",			"w=",			"q1=",			"(txtsearch|qry)=",	"",			"q=",			"nusearch_terms=",	"Keywords=",		"Gw=",			"",				"searchfor=",			"qry=",				"all=",					"s=",				"",					"swik\\.net/",			"",				"",					"search=",		"p1=",		"q=",				"(wd|word)=",		"kw=",		"q=",			"query=",		"(p|name)=",		"(w|k)=",	"qt=",			"q=",		"(word|w)=",		"word=",		"word=",		"(searchtext|q)=",	"(w|q)=",	"query=",	"q=",		"(s|q)=",		"dotaz=",	"srch=",	"q=",		"q=",			"q=",		"soegeord=",	"words=",	"query=",		"",			"search_field=",	"",		"query=",	"querytext=",	"search_for=",	"in=",		"(ask|q)=",		"q=",				"q=",			"key=",			"txtsearch=",	"pattern=",	"Search_Keyword=",	"k=",			"stext=",		"search=",	"search_term=",		"w=",			"",			"",		"name=",	"",		"",					"",				"q=",					"q=",			"question=",		"q=",			"q=",			"q=",			"qt=",			"su=",			"",					"q=",			"q=",				"",			"",			"qry=",
"qry=",																									"q=",			"query=",		"q=",				"heureka=",	"(q|search)=",		"",			"KERESES=",	"",		"q=",		"",			"searchstr=",			"q=",		"q=",			"",			"keyword=",		"qs=",			"query=",			"q=",			"q=",				"qs=",				"qs=",			"",			"Keywords=",	"searchWord=",	"KEY=",			"q=",			"(ask|q)=",	"q=",		"q=",			"q=",		"szukaj=",		"qt=",			"keyword=",	"slowo=",		"q=",			"qt=",		"q=",			"q=",				"qt=",			"qt=",			"qt=",			"q=",			"q=",			"text=",		"r=",		"words=",	"",		"st=",		"q=",				"q=",		"",		"",		"q=",		"qry=",				"q=",		""			};
	public String[] searchengineName = {	"Google (Products)",		"Google (Base)",	"Froogle (Google)",	"Google (Groups)",	"Google (Images)",	"Google",		"Google",		"Google",			"Google",			"Google (cache)",				"Google (cache)",				"Google (cache)",				"Google (cache)",				"Google (cache)",				"Google (cache)",				"Google (cache)",				"MSN",		"Microsoft Windows Live",	"Microsoft Bing",	"Voila",	"Yahoo! Mindset",		"Yahoo",	"Yahoo",										"AOL.com",		"Tiscali",	"Lycos",	"Alexa",	"AllTheWeb",		"Altavista",		"A9",		"DMOZ",		"Netscape",	"Terra",		"Search.com",		"Sympatico",				"Excite",	"4-counter (Google)",	"AT&T search (powered by Google)",	"BungeeBones",			"NorthernLight",	"Hotbot",	"Kvasir",	"WebCrawler",		"MetaCrawler",		"Go2Net",	"Go.com",		"Euroseek",	"Looksmart",		"Spray",	"NBCI",			"Ask Jeeves",		"Ask Jeeves",		"Atomz",	"Overture",		"Teoma",	"FindArticles",		"InfoSpace",		"Mamma",	"DejaNews",	"Dogpile",		"WISENut",		"Ixquick",		"EarthLink",			"i-une",		"Blingo",		"GPU p2p search",		"Clusty",		"My Search",		"Vivisimo",		"Kartoo",		"Icerocket (Blog)",	"Sphere (Blog)",	"Ledix",		"Shaw.ca",		"Searchalot",		"Copernic",		"Avantfind",		"Avantfind",		"Avantfind",		"Chello",		"Mirago",		"Answerbus",		"icq",			"Nusearch",		"GoodSearch",		"Scroogle",		"Questionanswering",		"MyWebSearch",			"Comet toolbar search",		"del.icio.us (Social Bookmark)",	"Digg (Social Bookmark)",	"Stumbleupon (Social Bookmark)",	"Swik (Social Bookmark)",	"Segnalo (Social Bookmark)",	"Ineffabile.it (Social Bookmark)",	"anzwers.com.au",	"Cade",		"Meta Miner",			"Baidu",		"VNet",		"SoSo",			"SoGou",		"3721",			"Iask",		"Accoona",		"NetEase",	"ZhongSou",		"Sina",			"Sohu",			"Atlas.cz",		"Seznam.cz",	"Quick.cz",	"Centrum.cz",	"Jyxo.cz",		"Najdi.to",	"RedBox.cz",	"Opasia.dk",	"Thor (danielsen.com)",	"SOL.dk",	"Jubii.dk",	"Find.dk",	"Edderkoppen.dk",	"Netstjernen.dk",	"Orbis.dk",		"Tyfon.dk",	"1Klik.dk",	"Ofir.dk",	"Ilse.nl",	"Vindex.nl",	"Ask Jeeves UK",	"BBC.co.uk",			"Freeserve.co.uk",	"Looksmart.co.uk",	"Mirago.co.uk",	"Splut.co.uk",	"Spotjockey.co.uk",	"UK Directory",		"UK Index",		"UK Plus",	"Searchy.co.uk",	"Haku.fi",		"AOL.fr",		"Ctrouve.fr",	"Francité.fr",	"LBB.org",	"Libertysurf.fr",			"Free.fr",			"Club-internet.fr",			"Toile du Quebec",	"Biglotron",		"Mozbot",		"AOL.de",		"Fireball.de",		"Infoseek.de",		"Web.de",		"Uni-hannover.de",			"Abacho.de",		"T-online.de",			"AllesKlar.de",		"MeineStadt.de",	"Metaspinner.de",
"Metacrawler.de",																							"WWWeasel",		"Netluchs",		"Schoenerbrausen",		"Heureka.hu",	"Origo.hu",		"Lapkereso.hu",		"Goliat.hu",	"Index.hu",	"Wahoo.hu",	"Webmania.hu",		"Internetto.hu",		"Tango",	"Tango keresolap",	"Polymeta",		"Sify",			"Virgilio.it",		"Arianna",			"Supereva",		"Kataweb",			"alice.it",			"alice.it",		"got u need",		"Godado.it",	"Jumpy.it",	"Shinyseek.it",		"Teecno",		"Ask Japan",	"Sagool",	"Start.no",		"Eniro",	"Wp.pl",		"Onet.pl",		"Dodaj.pl",	"Gazeta.pl",		"Gery.pl",		"Hoga.pl",	"NetSprint.pl",		"Interia.pl",			"Katalog.Onet.pl",	"o2.pl",		"Polska",		"Szukacz",		"Wow.pl",		"Yandex.ru",		"Aport.ru",	"Rambler.ru",	"Turtle.ru",	"Metabot.ru",	"Passagen.se",			"Eniro",	"",		"",		"Search.ch",	"Bluewin.ch",			"",		"unknown"		};
	public String[] searchengineWebsite = {	"www.google.com/products",	"base.google.com",	"froogle.google.com",	"groups.google.com",	"images.google.com",	"www.google.com",	"www.google.com",	"www.google.com",		"www.google.com",		"www.google.com/help/features.html#cached",	"www.google.com/help/features.html#cached",	"www.google.com/help/features.html#cached",	"www.google.com/help/features.html#cached",	"www.google.com/help/features.html#cached",	"www.google.com/help/features.html#cached",	"www.google.com/help/features.html#cached",	"msn.com",	"www.live.com",			"www.bing.com",		"voila.fr",	"mindset.research.yahoo.com",	"yahoo.com",	"yahoo.com",										"aol.com",		"tiscali.com",	"lycos.com",	"alexa.com",	"alltheweb.com",	"altavista.com",	"a9.com",	"dmoz.org",	"netscape.com",	"terra.com",		"search.com",		"sympatico.ca",				"excite.com",	"www.4-counter.com",	"www.att.net",				"BungeeBones.com/search.php",	"northernlight.com",	"hotbot.com",	"kvasir.com",	"webcrawler.com",	"metacrawler.com",	"go2net.com",	"go.com",		"euroseek.com",	"looksmart.com",	"spray.net",	"nbci.com",		"ask.com",		"ask.com",		"atomz.com",	"overture.com",		"teoma.com",	"findarticles.com",	"infospace.com",	"mamma.com",	"dejanews.com",	"dogpile.com",		"wisenut.com",		"ixquick.com",		"earthlink.net",		"i-une.com",		"www.blingo.com",	"search.centraldatabase.org",	"www.clusty.com",	"www.mysearch.com",	"www.vivisimo.com",	"www.kartoo.com",	"www.icerocket.com",	"www.sphere.com",	"www.ledix.net",	"start.shaw.ca",	"www.searchalot.com",	"www.copernic.com",	"www.avantfind.com",	"www.avantfind.com",	"www.avantfind.com",	"www.chello.com",	"www.mirago.com",	"www.answerbus.com",	"www.icq.com",		"www.nusearch.com",	"www.goodsearch.com",	"www.scroogle.org",	"www.questionanswering.com",	"search.mywebsearch.com",	"as.starware.com/dp/search",	"del.icio.us",				"www.digg.com",			"www.stumbleupon.com",			"swik.net",			"segnalo.alice.it",		"www.ineffabile.it",			"anzwers.com.au",	"",		"",				"baidu.com",		"114.vnet.cn",	"www.soso.com",		"www.sogou.com",	"www.3721.com",		"www.iask.com",	"cn.accoona.com",	"www.163.com",	"www.zhongsou.com",	"sina.com",		"sohu.com",		"atlas.cz",		"seznam.cz",	"quick.cz",	"centrum.cz",	"jyxo.cz",		"najdi.to",	"redbox.cz",	"opasia.dk",	"danielsen.com",	"sol.dk",	"jubii.dk",	"find.dk",	"edderkoppen.dk",	"netstjernen.dk",	"orbis.dk",		"tyfon.dk",	"1klik.dk",	"ofir.dk",	"ilse.nl",	"vindex.nl",	"ask.co.uk",		"bbc.co.uk",			"www.freeserve.co.uk",	"looksmart.co.uk",	"mirago.co.uk",	"splut.co.uk",	"spotjockey.co.uk",	"ukdirectory.co.uk",	"ukindex.co.uk",	"ukplus.co.uk",	"searchy.co.uk",	"haku.fi",		"aol.fr",		"ctrouve.fr",	"francite.fr",	"lbb.org",	"libertysurf.fr",			"free.fr",			"club-internet.fr",			"",			"www.biglotron.com",	"www.mozbot.fr",	"aol.de",		"fireball.de",		"infoseek.de",		"web.de",		"harvest.rrzn.uni-hannover.de",		"abacho.de",		"t-online.de",			"allesklar.de",		"meinestadt.de",	"metaspinner.de",
"metacrawler.de",																							"wwweasel.de",		"www.netluchs.de",	"www.schoenerbrausen.de",	"heureka.hu",	"origo.hu",		"lapkereso.hu",		"goliat.hu",	"index.hu",	"wahoo.hu",	"webmania.hu",		"internetto.hu",		"tango.hu",	"keresolap.hu",		"www.polymeta.hu",	"search.sify.com",	"virgilio.it",		"arianna.libero.it",		"search.supereva.com",	"www.kataweb.it/ricerca",	"www.alice.it",			"www.alice.it",		"www.gotuneed.com",	"godado.it",	"jumpy.it",	"shinyseek.it",		"www.teecno.it",	"www.ask.jp",	"sagool.jp",	"start.no",		"www.eniro.no",	"wp.pl",		"szukaj.onet.pl",	"www.dodaj.pl",	"szukaj.gazeta.pl",	"szukaj.gery.pl",	"www.hoga.pl",	"www.netsprint.pl",	"www.google.interia.pl",	"katalog.onet.pl",	"szukaj2.o2.pl",	"szukaj.polska.pl",	"www.szukacz.pl",	"szukaj.wow.pl",	"yandex.ru",		"aport.ru",	"rambler.ru",	"turtle.ru",	"metabot.ru",	"passagen.se",			"www.eniro.se",	"",		"",		"search.ch",	"bluewin.ch",			"",		""			};

	private HashMap<String,String> data = new HashMap<String,String>();

	private	Statement rs = null;



	public UsageLog() {
		init();
	}
	public UsageLog(String requestid, String requestclass, String requestdatabase, Session mysession, Request myrequest) {
		init();
		getLogData(requestid, requestclass, requestdatabase, mysession, myrequest, true);
	}
	public UsageLog(String requestid, String requestclass, String requestdatabase, Session mysession, Request myrequest, boolean setsession) {
		init();
		getLogData(requestid, requestclass, requestdatabase, mysession, myrequest, setsession);
	}



	public void init() {
		data = new HashMap<String,String>();
		data.put("hits", "1");
		data.put("datetimefull", "");
		data.put("datetimeyear", "");
		data.put("datetimemonth", "");
		data.put("datetimeday", "");
		data.put("datetimeweek", "");
		data.put("datetimeweekday", "");
		data.put("datetimehour", "");
		data.put("datetimemin", "");
		data.put("datetimesec", "");
		data.put("clientaddr1", "");
		data.put("clientaddr2", "");
		data.put("clientaddr3", "");
		data.put("clientaddr4", "");
		data.put("clienthost", "");
		data.put("clienthosttld", "");
		data.put("clientuser", "");
		data.put("clientuseragent", "");
		data.put("clientos", "");
		data.put("clientosversion", "");
		data.put("clientbrowser", "");
		data.put("clientversion", "");
		data.put("clientdevice", "");
		data.put("clientdeviceid", "");
		data.put("clientdeviceversion", "");
		data.put("clientusername", "");
		data.put("visitorid", "");
		data.put("visitorduration", "");
		data.put("sessionid", "");
		data.put("sessionduration", "");
		data.put("requesthost", "");
		data.put("requestport", "");
		data.put("requestmethod", "");
		data.put("requestpath", "");
		data.put("requestquery", "");
		data.put("requestprotocol", "");
		data.put("requestid", "");
		data.put("requestclass", "");
		data.put("requestdatabase", "");
		data.put("refererid", "");
		data.put("refererclass", "");
		data.put("refererdatabase", "");
		data.put("refererduration", "");
		data.put("refererhost", "");
		data.put("refererpath", "");
		data.put("refererquery", "");
		data.put("referersearchengine", "");
		data.put("referersearchquery", "");
		data.put("usersegments", "");
		data.put("usertests", "");
	}



	public void getLogData(String requestid, String requestclass, String requestdatabase, Session mysession, Request myrequest, boolean setsession) {
		String session_username = mysession.get("username");
		String session_visitorid = mysession.get("visitorid");
		String session_visitorduration = mysession.get("visitorduration");
		String session_sessionid = mysession.get("sessionid");
		String session_sessionstart = mysession.get("sessionstart");
		String session_refererid = mysession.get("refererid");
		String session_refererclass = mysession.get("refererclass");
		String session_refererdatabase = mysession.get("refererdatabase");
		String session_refererdatetime = mysession.get("refererdatetime");
		String session_refererhost = mysession.get("refererhost");
		String session_refererpath = mysession.get("refererpath");
		String session_refererquery = mysession.get("refererquery");
		String session_referersearchengine = mysession.get("referersearchengine");
		String session_referersearchquery = mysession.get("referersearchquery");
		String session_usersegments = mysession.get("usersegments");
		String session_usertests = mysession.get("usertests");
		Date now = new Date();
		setData("datetimefull", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		setData("datetimeyear", "" + calendar.get(Calendar.YEAR));
		setData("datetimemonth", "" + (calendar.get(Calendar.MONTH)+1));
		setData("datetimeday", "" + calendar.get(Calendar.DAY_OF_MONTH));
		setData("datetimeweek", "" + calendar.get(Calendar.WEEK_OF_YEAR));
		if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
			setData("datetimeweekday", "7");
		} else {
			setData("datetimeweekday", "" + (calendar.get(Calendar.DAY_OF_WEEK)-1));
		}
		setData("datetimehour", "" + calendar.get(Calendar.HOUR_OF_DAY));
		setData("datetimemin", "" + calendar.get(Calendar.MINUTE));
		setData("datetimesec", "" + calendar.get(Calendar.SECOND));

		if (myrequest.getRemoteAddr().equals("::1") || myrequest.getRemoteAddr().equals("0:0:0:0:0:0:0:1")) {
			setData("clientaddr", "127.0.0.1");
			setData("clientaddr1", "127");
			setData("clientaddr2", "0");
			setData("clientaddr3", "0");
			setData("clientaddr4", "1");
		} else {
			String[] clientaddr = myrequest.getRemoteAddr().split("\\.");
			setData("clientaddr", myrequest.getRemoteAddr());
			setData("clientaddr1", clientaddr[0]);
			setData("clientaddr2", clientaddr[1]);
			setData("clientaddr3", clientaddr[2]);
			setData("clientaddr4", clientaddr[3]);
		}
		if (! myrequest.getRemoteHost().equals("")) {
			setData("clienthost", myrequest.getRemoteHost());
		} else {
			setData("clienthost", myrequest.getRemoteAddr());
		}
		while ((("" + getData("clienthost")).length() > 250) && (("" + getData("clienthost")).indexOf(".")>-1)) {
			setData("clienthost", ("" + getData("clienthost")).substring(("" + getData("clienthost")).indexOf(".")+1));
		}
		if (("" + getData("clienthost")).length() > 250) {
			setData("clienthost", ("" + getData("clienthost")).substring(0, 250));
		}
		if ((! myrequest.getRemoteHost().equals("")) && (! myrequest.getRemoteHost().equals(myrequest.getRemoteAddr())) && (myrequest.getRemoteHost().lastIndexOf(".")>-1)) {
			setData("clienthosttld", myrequest.getRemoteHost().substring(myrequest.getRemoteHost().lastIndexOf(".")+1));
		}
		if (("" + getData("clienthosttld")).length() > 10) {
			setData("clienthosttld", ("" + getData("clienthosttld")).substring(0, 10));
		}
		setData("clientuser", myrequest.getRemoteUser());
		setData("clientusername", session_username);
		if (("" + getData("clientusername")).length() > 50) {
			setData("clientusername", ("" + getData("clientusername")).substring(0, 50));
		}

		setData("clientuseragent", myrequest.getHeader("User-Agent"));
		if (("" + getData("clientuseragent")).length() > 250) {
			setData("clientuseragent", ("" + getData("clientuseragent")).substring(0, 250));
		}
		HashMap<String,String> myclientos = getClientOS(myrequest.getHeader("User-Agent"));
		setData("clientos",  ""+myclientos.get("clientos"));
		if (("" + getData("clientos")).length() > 10) {
			setData("clientos", ("" + getData("clientos")).substring(0, 10));
		}
		setData("clientosversion",  ""+myclientos.get("clientosversion"));
		if (("" + getData("clientosversion")).length() > 20) {
			setData("clientosversion", ("" + getData("clientosversion")).substring(0, 20));
		}
		HashMap<String,String> myclientbrowser = getClientBrowser(myrequest.getHeader("User-Agent"));
		setData("clientbrowser",  ""+myclientbrowser.get("clientbrowser"));
		if (("" + getData("clientbrowser")).length() > 50) {
			setData("clientbrowser", ("" + getData("clientbrowser")).substring(0, 50));
		}
		setData("clientversion",  ""+myclientbrowser.get("clientversion"));
		if (("" + getData("clientversion")).length() > 20) {
			setData("clientversion", ("" + getData("clientversion")).substring(0, 20));
		}
		HashMap<String,String> myclientdevice = getClientDevice(myrequest.getHeader("User-Agent"));
		setData("clientdevice",  ""+myclientdevice.get("clientdevice"));
		if (("" + getData("clientdevice")).length() > 20) {
			setData("clientdevice", ("" + getData("clientdevice")).substring(0, 20));
		}
		setData("clientdeviceid",  ""+myclientdevice.get("clientdeviceid"));
		if (("" + getData("clientdeviceid")).length() > 20) {
			setData("clientdeviceid", ("" + getData("clientdeviceid")).substring(0, 20));
		}
		setData("clientdeviceversion",  ""+myclientdevice.get("clientdeviceversion"));
		if (("" + getData("clientdeviceversion")).length() > 50) {
			setData("clientdeviceversion", ("" + getData("clientdeviceversion")).substring(0, 50));
		}

		setData("preferredlanguage",  myrequest.getPreferredLanguage());
		setData("acceptedlanguages",  myrequest.getAcceptedLanguages());

		setData("visitorid", session_visitorid);
		if (! session_visitorduration.equals("")) {
			setData("visitorduration", session_visitorduration);
		} else {
			Date timestamp = Common.strtodate("YYYYmmddHHMMSS", session_visitorid.substring(0,14));
			setData("visitorduration", "" + ((int)(now.getTime()/1000) - (int)(timestamp.getTime()/1000)));
		}

		if (session_sessionid.equals("")) {
			String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(now);
			session_sessionid = "" + timestamp + (int)(Math.random()*10) + (int)(Math.random()*10) + (int)(Math.random()*10) + (int)(Math.random()*10) + (int)(Math.random()*10) + (int)(Math.random()*10);
			if (setsession) {
				mysession.set("sessionid", session_sessionid);
			}
		}
		setData("sessionid", session_sessionid);
		if (("" + getData("sessionid")).length() > 20) {
			setData("sessionid", ("" + getData("sessionid")).substring(0, 20));
		}
		if (session_sessionstart.equals("")) {
			session_sessionstart = "" + (int)(now.getTime()/1000);
			if (setsession) {
				mysession.set("sessionstart", session_sessionstart);
			}
		}
		long sessionstart = Common.integernumber(session_sessionstart);
		setData("sessionduration", "" + ((int)(now.getTime()/1000) - sessionstart));

		if (! session_refererid.equals("")) {
			setData("refererid", session_refererid);
		} else {
			setData("refererid", "0");
		}
		if (("" + getData("refererid")).length() > 10) {
			setData("refererid", ("" + getData("refererid")).substring(0, 10));
		}
		setData("refererclass", session_refererclass);
		setData("refererdatabase", session_refererdatabase);
		if (("" + getData("refererdatabase")).length() > 50) {
			setData("refererdatabase", ("" + getData("refererdatabase")).substring(0, 50));
		}
		if (session_refererdatetime.equals("")) {
			session_refererdatetime = "" + (int)(now.getTime()/1000);
			if (setsession) {
				mysession.set("refererdatetime", session_refererdatetime);
			}
		}
		long refererdatetime = Common.integernumber(session_refererdatetime);
		setData("refererduration", "" + ((int)(now.getTime()/1000) - refererdatetime));
		// only log first external referer - ignore local/subsequent referers
		if ((getData("sessionduration").equals("0")) && (getData("refererid").equals("0")) && (getData("refererclass").equals("")) && (getData("refererdatabase").equals(""))) {
			if ((! myrequest.getHeader("Referer").equals("")) && (myrequest.getHeader("Referer").matches("(?i)"+"https?:\\/\\/([^\\/]+)([^\\?]*)(.*)"))) {
				Pattern pattern = Pattern.compile("(?i)"+"https?:\\/\\/([^\\/]+)([^\\?]*)(.*)");
				Matcher matches = pattern.matcher(myrequest.getHeader("Referer"));
				if (matches.find()) {
					if (matches.group(1).length() > 50) {
						setData("refererhost", matches.group(1).substring(matches.group(1).length()-50, matches.group(1).length()));
					} else {
						setData("refererhost", matches.group(1));
					}
					if (matches.group(2).length() > 250) {
						setData("refererpath", matches.group(2).substring(0,250));
					} else {
						setData("refererpath", matches.group(2));
					}
					if (matches.group(3).length() > 250) {
						setData("refererquery", matches.group(3).substring(0,250));
					} else {
						setData("refererquery", matches.group(3));
					}
				}
				session_refererhost = "" + getData("refererhost");
				if (setsession) {
					mysession.set("refererhost", session_refererhost);
				}
				session_refererpath = "" + getData("refererpath");
				if (setsession) {
					mysession.set("refererpath", session_refererpath);
				}
				session_refererquery = "" + getData("refererquery");
				if (setsession) {
					mysession.set("refererquery", session_refererquery);
				}
				if (getData("refererclass").equals("")) {
					for (int i=0; i<searchenginePattern.length; i++) {
						if (getData("refererhost").matches("(?i)"+".*"+searchenginePattern[i]+".*")) {
							setData("referersearchengine", searchengineName[i]);

							if ((! searchengineQuery[i].equals("")) && (getData("refererquery").matches("(?i)"+".*"+"[\\?&]" + searchengineQuery[i] + "([^&]+)"+".*"))) {
								pattern = Pattern.compile("(?i)"+".*"+"[\\?&]" + searchengineQuery[i] + "([^&]+)"+".*");
								matches = pattern.matcher(myrequest.getHeader("Referer"));
								if (matches.find()) {
									String referersearchquery = URLDecoder.decode(matches.group(matches.groupCount()));
									if (referersearchquery.length() > 250) {
										setData("referersearchquery", referersearchquery.substring(0,250));
									} else {
										setData("referersearchquery", referersearchquery);
									}
								}
							} else if (searchengineName[i].equals("A9")) {
								String referersearchquery = "";
								if ((getData("refererpath") != null) && (! getData("refererpath").equals(""))) {
									referersearchquery = URLDecoder.decode(getData("refererpath").substring(1));
								}
								if (referersearchquery.length() > 250) {
									setData("referersearchquery", referersearchquery.substring(0,250));
								} else {
									setData("referersearchquery", referersearchquery);
								}
							} else {
								String referersearchquery = "";
								if ((getData("refererquery") != null) && (! getData("refererquery").equals(""))) {
									referersearchquery = URLDecoder.decode(getData("refererquery").substring(1));
								}
								if (referersearchquery.length() > 250) {
									setData("referersearchquery", referersearchquery.substring(0,250));
								} else {
									setData("referersearchquery", referersearchquery);
								}
							}
							break;
						}
					}
				}
				session_referersearchengine = "" + getData("referersearchengine");
				if (setsession) {
					mysession.set("referersearchengine", session_referersearchengine);
				}
				session_referersearchquery = "" + getData("referersearchquery");
				if (setsession) {
					mysession.set("referersearchquery", session_referersearchquery);
				}
			}
		}

		setData("requesthost", myrequest.getServerName());
		if (("" + getData("requesthost")).length() > 50) {
			setData("requesthost", ("" + getData("requesthost")).substring(0, 50));
		}
		setData("requestport", myrequest.getServerPort());
		setData("requestmethod", myrequest.getMethod());
		setData("requestpath", myrequest.getServletPath() + myrequest.getPathInfo());
		if (("" + getData("requestpath")).length() > 250) {
			setData("requestpath", ("" + getData("requestpath")).substring(0, 250));
		}
		setData("requestquery", myrequest.getQueryString());
		if (("" + getData("requestquery")).length() > 250) {
			setData("requestquery", ("" + getData("requestquery")).substring(0, 250));
		}
		setData("requestprotocol", myrequest.getProtocol());

		if (! requestid.equals("")) {
			setData("requestid", requestid);
		} else {
			setData("requestid", "0");
		}
		setData("requestclass", requestclass);
		if (("" + getData("requestclass")).length() > 10) {
			setData("requestclass", ("" + getData("requestclass")).substring(0, 10));
		}
		setData("requestdatabase", requestdatabase);
		if (("" + getData("requestdatabase")).length() > 50) {
			setData("requestdatabase", ("" + getData("requestdatabase")).substring(0, 50));
		}
		String myusersegments = session_usersegments;
		myusersegments = myusersegments.replaceAll("(^|,)([^,=]*?)=[1-9][0-9]*", "$1$2");
		myusersegments = myusersegments.replaceAll("(^|,)([^,=]*?)=-[0-9]+", "");
		myusersegments = myusersegments.replaceAll("(^|,)([^,=]*?)=0", "");
		setData("usersegments", myusersegments);
		setData("usertests", session_usertests);

		if ((requestclass.equals("page")) || (requestclass.equals("product")) || (requestclass.equals("data"))) {
			session_refererdatetime = "" + (int)(now.getTime()/1000);
			if (setsession) {
				mysession.set("refererdatetime", session_refererdatetime);
			}
			session_refererid = requestid;
			if (setsession) {
				mysession.set("refererid", session_refererid);
			}
			session_refererclass = requestclass;
			if (setsession) {
				mysession.set("refererclass", session_refererclass);
			}
			session_refererdatabase = requestdatabase;
			if (setsession) {
				mysession.set("refererdatabase", session_refererdatabase);
			}
		}
	}



	public void getRecord(DB db, HashMap<String,String> record) {
		data.put("hits", "" + record.get("hits"));
		data.put("datetimefull", "" + record.get("datetimefull"));
		data.put("datetimeyear", "" + record.get("datetimeyear"));
		data.put("datetimemonth", "" + record.get("datetimemonth"));
		data.put("datetimeday", "" + record.get("datetimeday"));
		data.put("datetimeweek", "" + record.get("datetimeweek"));
		data.put("datetimeweekday", "" + record.get("datetimeweekday"));
		data.put("datetimehour", "" + record.get("datetimehour"));
		data.put("datetimemin", "" + record.get("datetimemin"));
		data.put("datetimesec", "" + record.get("datetimesec"));
		data.put("clientaddr1", "" + record.get("clientaddr1"));
		data.put("clientaddr2", "" + record.get("clientaddr2"));
		data.put("clientaddr3", "" + record.get("clientaddr3"));
		data.put("clientaddr4", "" + record.get("clientaddr4"));
		data.put("clienthost", "" + record.get("clienthost"));
		data.put("clienthosttld", "" + record.get("clienthosttld"));
		data.put("clientuser", "" + record.get("clientuser"));
		data.put("clientuseragent", "" + record.get("clientuseragent"));
		data.put("clientos", "" + record.get("clientos"));
		data.put("clientosversion", "" + record.get("clientosversion"));
		data.put("clientbrowser", "" + record.get("clientbrowser"));
		data.put("clientversion", "" + record.get("clientversion"));
		data.put("clientdevice", "" + record.get("clientdevice"));
		data.put("clientdeviceid", "" + record.get("clientdeviceid"));
		data.put("clientdeviceversion", "" + record.get("clientdeviceversion"));
		data.put("clientusername", "" + record.get("clientusername"));
		data.put("visitorid", "" + record.get("visitorid"));
		data.put("visitorduration", "" + record.get("visitorduration"));
		data.put("sessionid", "" + record.get("sessionid"));
		data.put("sessionduration", "" + record.get("sessionduration"));
		data.put("requesthost", "" + record.get("requesthost"));
		data.put("requestport", "" + record.get("requestport"));
		data.put("requestmethod", "" + record.get("requestmethod"));
		data.put("requestpath", "" + record.get("requestpath"));
		data.put("requestquery", "" + record.get("requestquery"));
		data.put("requestprotocol", "" + record.get("requestprotocol"));
		data.put("requestid", "" + record.get("requestid"));
		data.put("requestclass", "" + record.get("requestclass"));
		data.put("requestdatabase", "" + record.get("requestdatabase"));
		data.put("refererid", "" + record.get("refererid"));
		data.put("refererclass", "" + record.get("refererclass"));
		data.put("refererdatabase", "" + record.get("refererdatabase"));
		data.put("refererduration", "" + record.get("refererduration"));
		data.put("refererhost", "" + record.get("refererhost"));
		data.put("refererpath", "" + record.get("refererpath"));
		data.put("refererquery", "" + record.get("refererquery"));
		data.put("referersearchengine", "" + record.get("referersearchengine"));
		data.put("referersearchquery", "" + record.get("referersearchquery"));
		data.put("usersegments", "" + record.get("usersegments"));
		data.put("usertests", "" + record.get("usertests"));
	}



	public void create(DB db) {
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("hits", "1");
		mydata.put("datetimefull", "" + data.get("datetimefull"));
		mydata.put("datetimeyear", "" + Common.intnumber("" + data.get("datetimeyear")));
		mydata.put("datetimemonth", "" + Common.intnumber("" + data.get("datetimemonth")));
		mydata.put("datetimeday", "" + Common.intnumber("" + data.get("datetimeday")));
		mydata.put("datetimeweek", "" + Common.intnumber("" + data.get("datetimeweek")));
		mydata.put("datetimeweekday", "" + Common.intnumber("" + data.get("datetimeweekday")));
		mydata.put("datetimehour", "" + Common.intnumber("" + data.get("datetimehour")));
		mydata.put("datetimemin", "" + Common.intnumber("" + data.get("datetimemin")));
		mydata.put("datetimesec", "" + Common.intnumber("" + data.get("datetimesec")));
//		mydata.put("clientaddr1", "" + data.get("clientaddr1"));
//		mydata.put("clientaddr2", "" + data.get("clientaddr2"));
//		mydata.put("clientaddr3", "" + data.get("clientaddr3"));
//		mydata.put("clientaddr4", "" + data.get("clientaddr4"));
		mydata.put("clienthost", "" + data.get("clienthost"));
		mydata.put("clienthosttld", "" + data.get("clienthosttld"));
//		mydata.put("clientuser", "" + data.get("clientuser"));
		mydata.put("clientuseragent", "" + data.get("clientuseragent"));
		mydata.put("clientos", "" + data.get("clientos"));
		mydata.put("clientosversion", "" + data.get("clientosversion"));
		mydata.put("clientbrowser", "" + data.get("clientbrowser"));
		mydata.put("clientversion", "" + data.get("clientversion"));
		mydata.put("clientdevice", "" + data.get("clientdevice"));
		mydata.put("clientdeviceid", "" + data.get("clientdeviceid"));
		mydata.put("clientdeviceversion", "" + data.get("clientdeviceversion"));
		mydata.put("clientusername", "" + data.get("clientusername"));
		mydata.put("visitorid", "" + data.get("visitorid"));
		mydata.put("visitorduration", "" + Common.integernumber(data.get("visitorduration")));
		mydata.put("sessionid", "" + data.get("sessionid"));
		mydata.put("sessionduration", "" + Common.integernumber(data.get("sessionduration")));
		mydata.put("requesthost", "" + data.get("requesthost"));
//		mydata.put("requestport", "" + data.get("requestport"));
//		mydata.put("requestmethod", "" + data.get("requestmethod"));
		mydata.put("requestpath", "" + data.get("requestpath"));
		mydata.put("requestquery", "" + data.get("requestquery"));
//		mydata.put("requestprotocol", "" + data.get("requestprotocol"));
		mydata.put("requestid", "" + Common.integernumber(data.get("requestid")));
		mydata.put("requestclass", "" + data.get("requestclass"));
		mydata.put("requestdatabase", "" + data.get("requestdatabase"));
		mydata.put("refererid", "" + Common.integernumber(data.get("refererid")));
		mydata.put("refererclass", "" + data.get("refererclass"));
		mydata.put("refererdatabase", "" + data.get("refererdatabase"));
		mydata.put("refererduration", "" + Common.integernumber(data.get("refererduration")));
		mydata.put("refererhost", "" + data.get("refererhost"));
		mydata.put("refererpath", "" + data.get("refererpath"));
		mydata.put("refererquery", "" + data.get("refererquery"));
		mydata.put("referersearchengine", "" + data.get("referersearchengine"));
		mydata.put("referersearchquery", "" + data.get("referersearchquery"));
		mydata.put("usersegments", "" + data.get("usersegments"));
		mydata.put("usertests", "" + data.get("usertests"));
		db.insert("usagelog", mydata);
	}



	public void clean(DB db, String log_period) {
		if ((log_period != null) && (! log_period.equals(""))) {
			Date now = new Date();
			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date((long)(now.getTime()) - Common.integernumber(log_period)*1000));
			db.deleteWhere("usagelog", "datetimefull<'" + timestamp + "'");
		}
	}



	public void summarise(JspWriter output, Text text, DB db, DB logdb, Configuration config, boolean test, boolean commit, boolean all, boolean force) throws Exception {
		String summarise_period = config.get(db, "summarise_period");
		if (summarise_period.equals("")) return;
		String summarised_period = config.get(db, "summarised_period");
		if (summarised_period.equals("")) summarised_period = "hourly";
		String summarised_details = config.get(db, "summarised_details");
		if (summarised_details.equals("")) summarised_details = "pageviews,hits";
		boolean exact_clienthosts = (summarised_details.contains("clienthosts"));
		boolean exact_visitors = (summarised_details.contains("visitors"));
		boolean exact_visits = (summarised_details.contains("visits"));
		boolean exact_pageviews = (summarised_details.contains("pageviews"));
		boolean exact_hits = (summarised_details.contains("hits"));
		boolean exact_usersegments = (summarised_details.contains("usersegments"));
		boolean exact_usertests = (summarised_details.contains("usertests"));

		UCreportUsage reportusage = new UCreportUsage();
		String SQLwheredetails = logdb.is_blank("summarised");
		String SQLwheresummarised = logdb.is_not_blank("summarised");

		if (force || (! config.getDB(db, "summarise_started").equals(""))) {
			if (force) {
				config.set(db, "summarise_started", "");
			} else {
				if (output != null) output.println("<p>" + text.display("usage.summarise.running") + ": " + config.getDB(db, "summarise_started"));
				if (output != null) output.flush();
			}
			return;
		}

		boolean first = true;
		while ((first || all) && (test || commit || all)) {
			first = false;

			if (output != null) output.println("<hr>");
	
			if (force) {
				if (output != null) output.println(text.display("usage.summarise.forcing"));
			} else if (all) {
				if (output != null) output.println(text.display("usage.summarise.committingall"));
			} else if (commit) {
				if (output != null) output.println(text.display("usage.summarise.committing"));
			} else if (test) {
				if (output != null) output.println(text.display("usage.summarise.testing"));
			}

			if (all) commit = true;
			all = false; // reload for each single period using Javascript instead

			String summarise_started = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			config.set(db, "summarise_started", summarise_started);
			if (! summarise_started.equals(config.getDB(db, "summarise_started"))) {
				break;
			}
			String summarise_ended = "";

			if (output != null) output.println(text.display("usage.summarise.started") + ": " + summarise_started);

			if (output != null) output.println(text.display("usage.summarise.settings"));

			if (output != null) output.println(text.display("usage.summarise.period") + ": " + summarised_period);
			if (output != null) output.println(text.display("usage.summarise.after") + ": ");
			if (summarise_period.equals("86400")) {
				if (output != null) output.println(text.display("config.statistics.logperiod.1day"));
			} else if (summarise_period.equals("604800")) {
				if (output != null) output.println(text.display("config.statistics.logperiod.1week"));
			} else if (summarise_period.equals("1209600")) {
				if (output != null) output.println(text.display("config.statistics.logperiod.2weeks"));
			} else if (summarise_period.equals("1814400")) {
				if (output != null) output.println(text.display("config.statistics.logperiod.3weeks"));
			} else if (summarise_period.equals("2678400")) {
				if (output != null) output.println(text.display("config.statistics.logperiod.1month"));
			} else if (summarise_period.equals("5356800")) {
				if (output != null) output.println(text.display("config.statistics.logperiod.2months"));
			} else if (summarise_period.equals("7948800")) {
				if (output != null) output.println(text.display("config.statistics.logperiod.3months"));
			} else if (summarise_period.equals("15897600")) {
				if (output != null) output.println(text.display("config.statistics.logperiod.6months"));
			} else if (summarise_period.equals("31622400")) {
				if (output != null) output.println(text.display("config.statistics.logperiod.1year"));
			} else if (summarise_period.equals("63158400")) {
				if (output != null) output.println(text.display("config.statistics.logperiod.2years"));
			} else {
				if (output != null) output.println(summarise_period);
			}

			Date now = new Date();
			String summary_period_start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date((long)(now.getTime()) - Common.integernumber(summarise_period)*1000));

			if (output != null) output.println(text.display("usage.summarise.after") + ": " + summary_period_start);

			if (output != null) output.println(text.display("usage.summarise.details"));

			if (output != null) output.println(text.display("usage.summarise.clienthosts") + ": " + (exact_clienthosts ? text.display("yes") : text.display("no")));
			if (output != null) output.println(text.display("usage.summarise.visitors") + ": " + (exact_visitors ? text.display("yes") : text.display("no")));
			if (output != null) output.println(text.display("usage.summarise.visits") + ": " + (exact_visits ? text.display("yes") : text.display("no")));
			if (output != null) output.println(text.display("usage.summarise.pageviews") + ": " + (exact_pageviews ? text.display("yes") : text.display("no")));
			if (output != null) output.println(text.display("usage.summarise.hits") + ": " + (exact_hits ? text.display("yes") : text.display("no")));
			if (output != null) output.println(text.display("usage.summarise.usersegments") + ": " + (exact_usersegments ? text.display("yes") : text.display("no")));
			if (output != null) output.println(text.display("usage.summarise.usertests") + ": " + (exact_usertests ? text.display("yes") : text.display("no")));
			if (output != null) output.println(text.display("usage.summarise.current"));

			String mindatetime = logdb.query_string("select min(datetimefull) from usagelog where " + SQLwheredetails);

			if (output != null) output.println(text.display("usage.summarise.oldest") + ": " + mindatetime);

			Matcher matches = Pattern.compile("^([0-9][0-9][0-9][0-9])-([0-9][0-9])-([0-9][0-9]) ([0-9][0-9]):([0-9][0-9]):([0-9][0-9])?$").matcher(mindatetime);
			if (! matches.matches()) {
				// no more details usagelog data
				config.set(db, "summarise_started", "");
				summarise_ended = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
				if (output != null) output.println(text.display("usage.summarise.ended") + ": " + summarise_ended);
				if (output != null) output.println("<hr>");
				break;
			} else {
				String datetimeyear = "" + matches.group(1);
				String datetimemonth = "" + matches.group(2);
				String datetimeday = "" + matches.group(3);
				String datetimehour = "" + matches.group(4);
				String datetimemin = "" + matches.group(5);
				String datetimesec = "" + matches.group(6);

				String datetimefull = "";
				String datetimefull_from = "";
				String datetimefull_to = "";
				if (summarised_period.equals("hourly")) {
					datetimemin = "00";
					datetimesec = "00";
					datetimefull = datetimeyear + "-" + datetimemonth + "-" + datetimeday + " " + datetimehour + ":" + datetimemin + ":" + datetimesec;
					datetimefull_from = datetimeyear + "-" + datetimemonth + "-" + datetimeday + " " + datetimehour + ":00:00";
					datetimefull_to = datetimeyear + "-" + datetimemonth + "-" + datetimeday + " " + datetimehour + ":59:59";
				} else if (summarised_period.equals("daily")) {
					datetimehour = "00";
					datetimemin = "00";
					datetimesec = "00";
					datetimefull = datetimeyear + "-" + datetimemonth + "-" + datetimeday + " " + datetimehour + ":" + datetimemin + ":" + datetimesec;
					datetimefull_from = datetimeyear + "-" + datetimemonth + "-" + datetimeday + " " + "00:00:00";
					datetimefull_to = datetimeyear + "-" + datetimemonth + "-" + datetimeday + " " + "23:59:59";
				} else if (summarised_period.equals("monthly")) {
					datetimeday = "01";
					datetimehour = "00";
					datetimemin = "00";
					datetimesec = "00";
					datetimefull = datetimeyear + "-" + datetimemonth + "-" + datetimeday + " " + datetimehour + ":" + datetimemin + ":" + datetimesec;
					datetimefull_from = datetimeyear + "-" + datetimemonth + "-" + "01" + " " + "00:00:00";
					datetimefull_to = datetimeyear + "-" + datetimemonth + "-" + "31" + " " + "23:59:59";
				} else {
					config.set(db, "summarise_started", "");
					summarise_ended = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
					if (output != null) output.println(text.display("usage.summarise.ended") + ": " + summarise_ended);
					if (output != null) output.println("<hr>");
					break; // error configuration
				}

				if (summary_period_start.compareTo(datetimefull_to) < 0) {
					config.set(db, "summarise_started", "");
					summarise_ended = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
					if (output != null) output.println(text.display("usage.summarise.ended") + ": " + summarise_ended);
					if (output != null) output.println("<hr>");
					break; // summary period spans details period
				}

				Date datetime = Common.strtodate(datetimefull);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(datetime);
				String datetimeweek = "" + calendar.get(Calendar.WEEK_OF_YEAR);
				String datetimeweekday = "" + (calendar.get(Calendar.DAY_OF_WEEK)-1);
				if (datetimeweekday.equals("0")) datetimeweekday = "7";

				Session mysession = new Session();
				mysession.set("admin_usage_period_start", datetimefull_from);
				mysession.set("admin_usage_period_end", datetimefull_to);
				mysession.set("admin_usage_details", "+countusersegments+countusertests");
				Request myrequest = new Request();
				Response myresponse = new Response();

				String SQLwheredatetime = "(datetimefull>='" + datetimefull_from + "' and datetimefull<='" + datetimefull_to + "')";
				String SQLwhere = SQLwheredatetime;

				long rowscount_pre = Math.round(logdb.query_value("select count(*) from usagelog"));
				long rowscount_details_pre = Math.round(logdb.query_value("select count(*) from usagelog where " + SQLwheredatetime + " and " + SQLwheredetails));
				long rowscount_summarised_pre = Math.round(logdb.query_value("select count(*) from usagelog where " + SQLwheredatetime + " and " + SQLwheresummarised));

				if (output != null) output.println(text.display("usage.summarise.current.all") + ": " + rowscount_pre);
				if (output != null) output.println(text.display("usage.summarise.current.details") + ": " + rowscount_details_pre);
				if (output != null) output.println(text.display("usage.summarise.current.summarised") + ": " + rowscount_summarised_pre);

				if (output != null) output.println(text.display("usage.summarise.result"));

				if (output != null) output.println(text.display("usage.summarise.fromto") + ": " + datetimefull_from + " - " + datetimefull_to);

				long rowscount_inserted = 0;
				long rowscount_deleted = 0;

				String SQLcolumns = "";
				if (exact_clienthosts && exact_visitors && exact_visits) {
					SQLcolumns = "clienthost, visitorid, sessionid";
				} else if (exact_clienthosts && exact_visitors) {
					SQLcolumns = "clienthost, visitorid, '' as sessionid";
				} else if (exact_clienthosts && exact_visits) {
					SQLcolumns = "clienthost, '' as visitorid, sessionid";
				} else if (exact_visitors && exact_visits) {
					SQLcolumns = "'' as clienthost, visitorid, sessionid";
				} else if (exact_clienthosts) {
					SQLcolumns = "clienthost, '' as visitorid, '' as sessionid";
				} else if (exact_visitors) {
					SQLcolumns = "'' as clienthost, visitorid, '' as sessionid";
				} else if (exact_visits) {
					SQLcolumns = "'' as clienthost, '' as visitorid, sessionid";
				} else {
					SQLcolumns = "'' as clienthost, '' as visitorid, '' as sessionid";
				}

				HashMap<String,String> summary = reportusage.getSummary(mysession, myrequest, myresponse, config, db, logdb, logdb.getDatabase());

				HashMap<String,HashMap<String,String>> hours = reportusage.getPeriodData(mysession, myrequest, config, db, logdb, logdb.getDatabase(), now, reportusage.periodHours(mysession, now), "usagelog", "datetimeyear, datetimemonth, datetimeday, datetimehour, datetimeweekday, datetimeweek", "1=1", "", "", "", "%04d:::%02d:::%02d:::%02d:::%01d:::%02d", "datetimeyear,datetimemonth,datetimeday,datetimehour,datetimeweekday,datetimeweek", false);
				HashMap<String,HashMap<String,String>> days = days = reportusage.getPeriodData(mysession, myrequest, config, db, logdb, logdb.getDatabase(), now, reportusage.periodDays(mysession, now), "usagelog", "datetimeyear, datetimemonth, datetimeday, datetimeweek, datetimeweekday, 0 as datetimehour", "1=1", "", "", "", "%04d:::%02d:::%02d:::%01d:::%02d", "datetimeyear,datetimemonth,datetimeday,datetimeweekday,datetimeweek", false);
				HashMap<String,HashMap<String,String>> weeks = reportusage.getPeriodData(mysession, myrequest, config, db, logdb, logdb.getDatabase(), now, reportusage.periodWeeks(mysession, now), "usagelog", "datetimeyear, 1 as datetimemonth, 1 as datetimeday, datetimeweek, 1 as datetimeweekday, 0 as datetimehour", "1=1", "", "", "", "%04d:::%02d", "datetimeyear,datetimeweek", false);
				HashMap<String,HashMap<String,String>> months = months = reportusage.getPeriodData(mysession, myrequest, config, db, logdb, logdb.getDatabase(), now, reportusage.periodMonths(mysession, now), "usagelog", "datetimeyear, datetimemonth, 1 as datetimeday, 1 as datetimeweek, 1 as datetimeweekday, 0 as datetimehour", "1=1", "", "", "", "%04d:::%02d", "datetimeyear,datetimemonth", false);

				HashMap<String,LinkedHashMap<String,String>> contents = reportusage.getRankedData(mysession, myrequest, config, db, logdb, logdb.getDatabase(), "usagelog", "requestid, requestclass, requestdatabase", "1=1", "", "", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "requestclass:::requestdatabase:::requestid", true, false);
				HashMap<String,LinkedHashMap<String,String>> websites = reportusage.getRankedData(mysession, myrequest, config, db, logdb, logdb.getDatabase(), "usagelog", "requesthost", "1=1", "", "", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "requesthost", true, false);
				HashMap<String,LinkedHashMap<String,String>> countries = reportusage.getRankedData(mysession, myrequest, config, db, logdb, logdb.getDatabase(), "usagelog", "clienthosttld", "1=1", "", "", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "clienthosttld", true, false);
				HashMap<String,LinkedHashMap<String,String>> clienthosts = reportusage.getRankedData(mysession, myrequest, config, db, logdb, logdb.getDatabase(), "usagelog", "clienthost", "1=1", "", "", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "clienthost", true, false);
				HashMap<String,LinkedHashMap<String,String>> visitors = reportusage.getRankedData(mysession, myrequest, config, db, logdb, logdb.getDatabase(), "usagelog", "visitorid", "1=1", "", "", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "visitorid", true, false);
				HashMap<String,LinkedHashMap<String,String>> visits = reportusage.getRankedData(mysession, myrequest, config, db, logdb, logdb.getDatabase(), "usagelog", "sessionid", "1=1", "", "", "countclienthosts+countvisitors+countvisits+countpageviews+counthits+durations", "sessionid", true, false);
				HashMap<String,LinkedHashMap<String,String>> useragents = reportusage.getRankedData(mysession, myrequest, config, db, logdb, logdb.getDatabase(), "usagelog", "clientbrowser, clientversion", "1=1", "", "", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "clientbrowser:::clientversion", false, false);
				HashMap<String,LinkedHashMap<String,String>> operatingsystems = reportusage.getRankedData(mysession, myrequest, config, db, logdb, logdb.getDatabase(), "usagelog", "clientos, clientosversion", "1=1", "", "", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "clientos:::clientosversion", false, false);
				HashMap<String,LinkedHashMap<String,String>> devices = reportusage.getRankedData(mysession, myrequest, config, db, logdb, logdb.getDatabase(), "usagelog", "clientdevice, clientdeviceversion", "1=1", "", "", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "clientdevice:::clientdeviceversion", false, false);
				HashMap<String,LinkedHashMap<String,String>> users = reportusage.getRankedData(mysession, myrequest, config, db, logdb, logdb.getDatabase(), "usagelog", "clientusername", "1=1", "", "", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "clientusername", true, false);
				HashMap<String,LinkedHashMap<String,String>> referers = reportusage.getRankedData(mysession, myrequest, config, db, logdb, logdb.getDatabase(), "usagelog", "refererhost, refererpath", "1=1", "", "", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "refererhost:::refererpath", true, false);
				HashMap<String,LinkedHashMap<String,String>> searchengines = reportusage.getRankedData(mysession, myrequest, config, db, logdb, logdb.getDatabase(), "usagelog", "referersearchengine", "1=1", "", "", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "referersearchengine", false, false);
				HashMap<String,LinkedHashMap<String,String>> searchqueries = reportusage.getRankedData(mysession, myrequest, config, db, logdb, logdb.getDatabase(), "usagelog", "referersearchquery", "1=1", "", "", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "referersearchquery", true, true);
				HashMap<String,LinkedHashMap<String,String>> usersegments = reportusage.getRankedData(mysession, myrequest, config, db, logdb, logdb.getDatabase(), "usagelog", "usersegments", "1=1", "", "", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "usersegments", true, false);
				HashMap<String,LinkedHashMap<String,String>> usertests = reportusage.getRankedData(mysession, myrequest, config, db, logdb, logdb.getDatabase(), "usagelog", "usertests", "1=1", "", "", "countclienthosts+countvisitors+countvisits+countpageviews+counthits", "usertests", true, false);

				// risky - delete old detailed data (and old summarised data if any) before storing new summarised data
				//logdb.deleteWhere("usagelog", SQLwheredatetime);

				// delete old summarised data if any (from interrupted previous summarisation)

				if (output != null) output.println(text.display("usage.summarise.delete"));
				if (output != null) output.println(text.display("usage.summarise.delete.details") + ": " + rowscount_details_pre);
				if (output != null) output.println(text.display("usage.summarise.delete.summarised") + ": " + rowscount_summarised_pre);

				if (output != null) output.println(text.display("usage.summarise.insert"));

				rowscount_deleted += rowscount_summarised_pre;
				if (commit) {
					logdb.deleteWhere("usagelog", SQLwheredatetime + " and " + SQLwheresummarised);
				}

				// store new summarised data

				HashMap<String,String> datetimedata = new HashMap<String,String>();
				datetimedata.put("datetimefull", "" + datetimefull);
				datetimedata.put("datetimeyear", "" + datetimeyear);
				datetimedata.put("datetimemonth", "" + datetimemonth);
				datetimedata.put("datetimeday", "" + datetimeday);
				datetimedata.put("datetimeweek", "" + datetimeweek);
				datetimedata.put("datetimeweekday", "" + datetimeweekday);
				datetimedata.put("datetimehour", "" + datetimehour);
				datetimedata.put("datetimemin", "" + datetimemin);
				datetimedata.put("datetimesec", "" + datetimesec);
				if (summary != null) {
//					if (output != null) output.println(text.display("usage.summarise.insert.summarised") + 1 + " " + text.display("usage.summarise.insert.summary"));
//					rowscount_inserted += 1;
					String clienthost = "";
					String visitorid = "";
					String sessionid = "";
					HashMap<String,String> data = new HashMap<String,String>(datetimedata);
					data.put("summarised", "summary");
					data.put("datetimeyear", datetimeyear);
					data.put("datetimemonth", datetimemonth);
					data.put("datetimeday", datetimeday);
					data.put("datetimehour", datetimehour);
					data.put("datetimeweekday", datetimeweekday);
					data.put("datetimeweek", datetimeweek);
					data.put("clienthost", clienthost);
					data.put("visitorid", visitorid);
					data.put("sessionid", sessionid);
					data.put("sessionduration", "" + Common.integernumber(Math.round(Common.number("0" + summary.get("averagevisitduration")))));
					data.put("refererduration", "" + Common.integernumber(Math.round(Common.number("0" + summary.get("averagepageviewduration")))));
					data.put("hits", "" + Common.integernumber(summary.get("counthits")));
					data.put("pageviews", "" + Common.integernumber(summary.get("countpageviews")));
					data.put("visits", "" + Common.integernumber(summary.get("countvisits")));
					data.put("visitors", "" + Common.integernumber(summary.get("countvisitors")));
					data.put("clienthosts", "" + Common.integernumber(summary.get("countclienthosts")));
					if (commit) {
						logdb.insert("usagelog", data);
					}
					summary = null;
				}
				if (hours != null) {
					if (output != null) output.println(text.display("usage.summarise.insert.summarised") + hours.get("counthits").size() + " " + text.display("usage.summarise.insert.hours"));
					rowscount_inserted += hours.get("counthits").size();
					hours.put("counthits", Common.LinkedHashMapSortedByKey(hours.get("counthits"), true));
					Iterator i = hours.get("counthits").keySet().iterator();
					while (i.hasNext()) {
						String key = "" + i.next();
						String[] keyparts = (key+":::::::::::::::::::::::::::x").split(":::");
						datetimeyear = keyparts[0];
						datetimemonth = keyparts[1];
						datetimeday = keyparts[2];
						datetimehour = keyparts[3];
						datetimeweekday = keyparts[4];
						datetimeweek = keyparts[5];
						String clienthost = "";
						String visitorid = "";
						String sessionid = "";
						HashMap<String,String> data = new HashMap<String,String>(datetimedata);
						data.put("summarised", "datetimeyear, datetimemonth, datetimeday, datetimehour");
						data.put("datetimeyear", datetimeyear);
						data.put("datetimemonth", datetimemonth);
						data.put("datetimeday", datetimeday);
						data.put("datetimehour", datetimehour);
						data.put("datetimeweekday", datetimeweekday);
						data.put("datetimeweek", datetimeweek);
						data.put("clienthost", clienthost);
						data.put("visitorid", visitorid);
						data.put("sessionid", sessionid);
						data.put("hits", "" + Common.integernumber(hours.get("counthits").get(key)));
						data.put("pageviews", "" + Common.integernumber(hours.get("countpageviews").get(key)));
						data.put("visits", "" + Common.integernumber(hours.get("countvisits").get(key)));
						data.put("visitors", "" + Common.integernumber(hours.get("countvisitors").get(key)));
						data.put("clienthosts", "" + Common.integernumber(hours.get("countclienthosts").get(key)));
						if (commit) {
							logdb.insert("usagelog", data);
						}
					}
					hours = null;
				}
				if (days != null) {
					if (output != null) output.println(text.display("usage.summarise.insert.summarised") + days.get("counthits").size() + " " + text.display("usage.summarise.insert.days"));
					rowscount_inserted += days.get("counthits").size();
					days.put("counthits", Common.LinkedHashMapSortedByKey(days.get("counthits"), true));
					Iterator i = days.get("counthits").keySet().iterator();
					while (i.hasNext()) {
						String key = "" + i.next();
						String[] keyparts = (key+":::::::::::::::::::::::::::x").split(":::");
						datetimeyear = keyparts[0];
						datetimemonth = keyparts[1];
						datetimeday = keyparts[2];
						datetimehour = "0";
						datetimeweekday = keyparts[3];
						datetimeweek = keyparts[4];
						String clienthost = "";
						String visitorid = "";
						String sessionid = "";
						HashMap<String,String> data = new HashMap<String,String>(datetimedata);
						data.put("summarised", "datetimeyear, datetimemonth, datetimeday");
						data.put("datetimeyear", datetimeyear);
						data.put("datetimemonth", datetimemonth);
						data.put("datetimeday", datetimeday);
						data.put("datetimehour", datetimehour);
						data.put("datetimeweekday", datetimeweekday);
						data.put("datetimeweek", datetimeweek);
						data.put("clienthost", clienthost);
						data.put("visitorid", visitorid);
						data.put("sessionid", sessionid);
						data.put("hits", "" + Common.integernumber(days.get("counthits").get(key)));
						data.put("pageviews", "" + Common.integernumber(days.get("countpageviews").get(key)));
						data.put("visits", "" + Common.integernumber(days.get("countvisits").get(key)));
						data.put("visitors", "" + Common.integernumber(days.get("countvisitors").get(key)));
						data.put("clienthosts", "" + Common.integernumber(days.get("countclienthosts").get(key)));
						if (commit) {
							logdb.insert("usagelog", data);
						}
					}
					days = null;
				}
				if (weeks != null) {
					if (output != null) output.println(text.display("usage.summarise.insert.summarised") + weeks.get("counthits").size() + " " + text.display("usage.summarise.insert.weeks"));
					rowscount_inserted += weeks.get("counthits").size();
					weeks.put("counthits", Common.LinkedHashMapSortedByKey(weeks.get("counthits"), true));
					Iterator i = weeks.get("counthits").keySet().iterator();
					while (i.hasNext()) {
						String key = "" + i.next();
						String[] keyparts = (key+":::::::::::::::::::::::::::x").split(":::");
						datetimeyear = keyparts[0];
						datetimemonth = "1";
						datetimeday = "1";
						datetimehour = "0";
						datetimeweekday = "1";
						datetimeweek = keyparts[1];
						String clienthost = "";
						String visitorid = "";
						String sessionid = "";
						HashMap<String,String> data = new HashMap<String,String>(datetimedata);
						data.put("summarised", "datetimeyear, datetimeweek");
						data.put("datetimeyear", datetimeyear);
						data.put("datetimemonth", datetimemonth);
						data.put("datetimeday", datetimeday);
						data.put("datetimehour", datetimehour);
						data.put("datetimeweekday", datetimeweekday);
						data.put("datetimeweek", datetimeweek);
						data.put("clienthost", clienthost);
						data.put("visitorid", visitorid);
						data.put("sessionid", sessionid);
						data.put("hits", "" + Common.integernumber(weeks.get("counthits").get(key)));
						data.put("pageviews", "" + Common.integernumber(weeks.get("countpageviews").get(key)));
						data.put("visits", "" + Common.integernumber(weeks.get("countvisits").get(key)));
						data.put("visitors", "" + Common.integernumber(weeks.get("countvisitors").get(key)));
						data.put("clienthosts", "" + Common.integernumber(weeks.get("countclienthosts").get(key)));
						if (commit) {
							logdb.insert("usagelog", data);
						}
					}
					weeks = null;
				}
				if (months != null) {
					if (output != null) output.println(text.display("usage.summarise.insert.summarised") + months.get("counthits").size() + " " + text.display("usage.summarise.insert.months"));
					rowscount_inserted += months.get("counthits").size();
					months.put("counthits", Common.LinkedHashMapSortedByKey(months.get("counthits"), true));
					Iterator i = months.get("counthits").keySet().iterator();
					while (i.hasNext()) {
						String key = "" + i.next();
						String[] keyparts = (key+":::::::::::::::::::::::::::x").split(":::");
						datetimeyear = keyparts[0];
						datetimemonth = keyparts[1];
						datetimeday = "1";
						datetimehour = "0";
						datetimeweekday = "1";
						datetimeweek = "1";
						String clienthost = "";
						String visitorid = "";
						String sessionid = "";
						HashMap<String,String> data = new HashMap<String,String>(datetimedata);
						data.put("summarised", "datetimeyear, datetimemonth");
						data.put("datetimeyear", datetimeyear);
						data.put("datetimemonth", datetimemonth);
						data.put("datetimeday", datetimeday);
						data.put("datetimehour", datetimehour);
						data.put("datetimeweekday", datetimeweekday);
						data.put("datetimeweek", datetimeweek);
						data.put("clienthost", clienthost);
						data.put("visitorid", visitorid);
						data.put("sessionid", sessionid);
						data.put("hits", "" + Common.integernumber(months.get("counthits").get(key)));
						data.put("pageviews", "" + Common.integernumber(months.get("countpageviews").get(key)));
						data.put("visits", "" + Common.integernumber(months.get("countvisits").get(key)));
						data.put("visitors", "" + Common.integernumber(months.get("countvisitors").get(key)));
						data.put("clienthosts", "" + Common.integernumber(months.get("countclienthosts").get(key)));
						if (commit) {
							logdb.insert("usagelog", data);
						}
					}
					months = null;
				}
				if (contents != null) {
					if (output != null) output.println(text.display("usage.summarise.insert.summarised") + contents.get("counthits").size() + " " + text.display("usage.summarise.insert.contents"));
					rowscount_inserted += contents.get("counthits").size();
					contents.put("counthits", Common.LinkedHashMapSortedByKey(contents.get("counthits"), true));
					Iterator i = contents.get("counthits").keySet().iterator();
					while (i.hasNext()) {
						String key = "" + i.next();
						String[] keyparts = (key+":::::::::::::::::::::::::::x").split(":::");
						String requestclass = keyparts[0];
						String requestdatabase = keyparts[1];
						String requestid = keyparts[2];
						String clienthost = "";
						String visitorid = "";
						String sessionid = "";
						HashMap<String,String> data = new HashMap<String,String>(datetimedata);
						data.put("summarised", "requestid, requestclass, requestdatabase");
						data.put("requestclass", requestclass);
						data.put("requestdatabase", requestdatabase);
						data.put("requestid", requestid);
						data.put("clienthost", clienthost);
						data.put("visitorid", visitorid);
						data.put("sessionid", sessionid);
						data.put("hits", "" + Common.integernumber(contents.get("counthits").get(key)));
						data.put("pageviews", "" + Common.integernumber(contents.get("countpageviews").get(key)));
						data.put("visits", "" + Common.integernumber(contents.get("countvisits").get(key)));
						data.put("visitors", "" + Common.integernumber(contents.get("countvisitors").get(key)));
						data.put("clienthosts", "" + Common.integernumber(contents.get("countclienthosts").get(key)));
						if (commit) {
							logdb.insert("usagelog", data);
						}
					}
					contents = null;
				}
				if (websites != null) {
					if (output != null) output.println(text.display("usage.summarise.insert.summarised") + websites.get("counthits").size() + " " + text.display("usage.summarise.insert.websites"));
					rowscount_inserted += websites.get("counthits").size();
					websites.put("counthits", Common.LinkedHashMapSortedByKey(websites.get("counthits"), true));
					Iterator i = websites.get("counthits").keySet().iterator();
					while (i.hasNext()) {
						String key = "" + i.next();
						String[] keyparts = (key+":::::::::::::::::::::::::::x").split(":::");
						String requesthost = keyparts[0];
						String clienthost = "";
						String visitorid = "";
						String sessionid = "";
						if (requesthost.equals("")) requesthost = "-";
						HashMap<String,String> data = new HashMap<String,String>(datetimedata);
						data.put("summarised", "requesthost");
						data.put("requesthost", requesthost);
						data.put("clienthost", clienthost);
						data.put("visitorid", visitorid);
						data.put("sessionid", sessionid);
						data.put("hits", "" + Common.integernumber(websites.get("counthits").get(key)));
						data.put("pageviews", "" + Common.integernumber(websites.get("countpageviews").get(key)));
						data.put("visits", "" + Common.integernumber(websites.get("countvisits").get(key)));
						data.put("visitors", "" + Common.integernumber(websites.get("countvisitors").get(key)));
						data.put("clienthosts", "" + Common.integernumber(websites.get("countclienthosts").get(key)));
						if (commit) {
							logdb.insert("usagelog", data);
						}
					}
					websites = null;
				}
				if (countries != null) {
					if (output != null) output.println(text.display("usage.summarise.insert.summarised") + countries.get("counthits").size() + " " + text.display("usage.summarise.insert.countries"));
					rowscount_inserted += countries.get("counthits").size();
					countries.put("counthits", Common.LinkedHashMapSortedByKey(countries.get("counthits"), true));
					Iterator i = countries.get("counthits").keySet().iterator();
					while (i.hasNext()) {
						String key = "" + i.next();
						String[] keyparts = (key+":::::::::::::::::::::::::::x").split(":::");
						String clienthosttld = keyparts[0];
						String clienthost = "";
						String visitorid = "";
						String sessionid = "";
						if (clienthosttld.equals("")) clienthosttld = "-";
						HashMap<String,String> data = new HashMap<String,String>(datetimedata);
						data.put("summarised", "clienthosttld");
						data.put("clienthosttld", clienthosttld);
						data.put("clienthost", clienthost);
						data.put("visitorid", visitorid);
						data.put("sessionid", sessionid);
						data.put("hits", "" + Common.integernumber(countries.get("counthits").get(key)));
						data.put("pageviews", "" + Common.integernumber(countries.get("countpageviews").get(key)));
						data.put("visits", "" + Common.integernumber(countries.get("countvisits").get(key)));
						data.put("visitors", "" + Common.integernumber(countries.get("countvisitors").get(key)));
						data.put("clienthosts", "" + Common.integernumber(countries.get("countclienthosts").get(key)));
						if (commit) {
							logdb.insert("usagelog", data);
						}
					}
					countries = null;
				}
				if (clienthosts != null) {
					if (output != null) output.println(text.display("usage.summarise.insert.summarised") + clienthosts.get("counthits").size() + " " + text.display("usage.summarise.insert.clienthosts"));
					rowscount_inserted += clienthosts.get("counthits").size();
					clienthosts.put("counthits", Common.LinkedHashMapSortedByKey(clienthosts.get("counthits"), true));
					Iterator i = clienthosts.get("counthits").keySet().iterator();
					while (i.hasNext()) {
						String key = "" + i.next();
						String[] keyparts = (key+":::::::::::::::::::::::::::x").split(":::");
						String clienthost = keyparts[0];
						String visitorid = "";
						String sessionid = "";
						if (clienthost.equals("")) clienthost = "-";
						HashMap<String,String> data = new HashMap<String,String>(datetimedata);
						data.put("summarised", "clienthost");
						data.put("clienthost", clienthost);
						data.put("visitorid", visitorid);
						data.put("sessionid", sessionid);
						data.put("sessionduration", "-1");
						data.put("hits", "" + Common.integernumber(clienthosts.get("counthits").get(key)));
						data.put("pageviews", "" + Common.integernumber(clienthosts.get("countpageviews").get(key)));
						data.put("visits", "" + Common.integernumber(clienthosts.get("countvisits").get(key)));
						data.put("visitors", "" + Common.integernumber(clienthosts.get("countvisitors").get(key)));
						data.put("clienthosts", "" + Common.integernumber(clienthosts.get("countclienthosts").get(key)));
						if (commit) {
							logdb.insert("usagelog", data);
						}
					}
					clienthosts = null;
				}
				if (visitors != null) {
					if (output != null) output.println(text.display("usage.summarise.insert.summarised") + visitors.get("counthits").size() + " " + text.display("usage.summarise.insert.visitors"));
					rowscount_inserted += visitors.get("counthits").size();
					visitors.put("counthits", Common.LinkedHashMapSortedByKey(visitors.get("counthits"), true));
					Iterator i = visitors.get("counthits").keySet().iterator();
					while (i.hasNext()) {
						String key = "" + i.next();
						String[] keyparts = (key+":::::::::::::::::::::::::::x").split(":::");
						String clienthost = "";
						String visitorid = keyparts[0];
						String sessionid = "";
						if (visitorid.equals("")) visitorid = "-";
						HashMap<String,String> data = new HashMap<String,String>(datetimedata);
						data.put("summarised", "visitorid");
						data.put("clienthost", clienthost);
						data.put("visitorid", visitorid);
						data.put("sessionid", sessionid);
						data.put("sessionduration", "-1");
						data.put("hits", "" + Common.integernumber(visitors.get("counthits").get(key)));
						data.put("pageviews", "" + Common.integernumber(visitors.get("countpageviews").get(key)));
						data.put("visits", "" + Common.integernumber(visitors.get("countvisits").get(key)));
						data.put("visitors", "" + Common.integernumber(visitors.get("countvisitors").get(key)));
						data.put("clienthosts", "" + Common.integernumber(visitors.get("countclienthosts").get(key)));
						if (commit) {
							logdb.insert("usagelog", data);
						}
					}
					visitors = null;
				}
				if (visits != null) {
					if (output != null) output.println(text.display("usage.summarise.insert.summarised") + visits.get("counthits").size() + " " + text.display("usage.summarise.insert.visits"));
					rowscount_inserted += visits.get("counthits").size();
					visits.put("counthits", Common.LinkedHashMapSortedByKey(visits.get("counthits"), true));
					Iterator i = visits.get("counthits").keySet().iterator();
					while (i.hasNext()) {
						String key = "" + i.next();
						String[] keyparts = (key+":::::::::::::::::::::::::::x").split(":::");
						String clienthost = "";
						String visitorid = "";
						String sessionid = keyparts[0];
						if (sessionid.equals("")) sessionid = "-";
						HashMap<String,String> data = new HashMap<String,String>(datetimedata);
						data.put("summarised", "sessionid");
						data.put("clienthost", clienthost);
						data.put("visitorid", visitorid);
						data.put("sessionid", sessionid);
						data.put("sessionduration", "" + Common.integernumber(visits.get("durations").get(key)));
						data.put("hits", "" + Common.integernumber(visits.get("counthits").get(key)));
						data.put("pageviews", "" + Common.integernumber(visits.get("countpageviews").get(key)));
						data.put("visits", "" + Common.integernumber(visits.get("countvisits").get(key)));
						data.put("visitors", "" + Common.integernumber(visits.get("countvisitors").get(key)));
						data.put("clienthosts", "" + Common.integernumber(visits.get("countclienthosts").get(key)));
						if (commit) {
							logdb.insert("usagelog", data);
						}
					}
					visits = null;
				}
				if (useragents != null) {
					if (output != null) output.println(text.display("usage.summarise.insert.summarised") + useragents.get("counthits").size() + " " + text.display("usage.summarise.insert.useragents"));
					rowscount_inserted += useragents.get("counthits").size();
					useragents.put("counthits", Common.LinkedHashMapSortedByKey(useragents.get("counthits"), true));
					Iterator i = useragents.get("counthits").keySet().iterator();
					while (i.hasNext()) {
						String key = "" + i.next();
						String[] keyparts = (key+":::::::::::::::::::::::::::x").split(":::");
						String clientbrowser = keyparts[0];
						String clientversion = keyparts[1];
						String clienthost = "";
						String visitorid = "";
						String sessionid = "";
						if (clientbrowser.equals("")) clientbrowser = "-";
						if (clientversion.equals("")) clientversion = "-";
						HashMap<String,String> data = new HashMap<String,String>(datetimedata);
						data.put("summarised", "clientbrowser, clientversion");
						data.put("clientbrowser", clientbrowser);
						data.put("clientversion", clientversion);
						data.put("clienthost", clienthost);
						data.put("visitorid", visitorid);
						data.put("sessionid", sessionid);
						data.put("hits", "" + Common.integernumber(useragents.get("counthits").get(key)));
						data.put("pageviews", "" + Common.integernumber(useragents.get("countpageviews").get(key)));
						data.put("visits", "" + Common.integernumber(useragents.get("countvisits").get(key)));
						data.put("visitors", "" + Common.integernumber(useragents.get("countvisitors").get(key)));
						data.put("clienthosts", "" + Common.integernumber(useragents.get("countclienthosts").get(key)));
						if (commit) {
							logdb.insert("usagelog", data);
						}
					}
					useragents = null;
				}
				if (operatingsystems != null) {
					if (output != null) output.println(text.display("usage.summarise.insert.summarised") + operatingsystems.get("counthits").size() + " " + text.display("usage.summarise.insert.operatingsystems"));
					rowscount_inserted += operatingsystems.get("counthits").size();
					operatingsystems.put("counthits", Common.LinkedHashMapSortedByKey(operatingsystems.get("counthits"), true));
					Iterator i = operatingsystems.get("counthits").keySet().iterator();
					while (i.hasNext()) {
						String key = "" + i.next();
						String[] keyparts = (key+":::::::::::::::::::::::::::x").split(":::");
						String clientos = keyparts[0];
						String clientosversion = keyparts[1];
						String clienthost = "";
						String visitorid = "";
						String sessionid = "";
						if (clientos.equals("")) clientos = "-";
						if (clientosversion.equals("")) clientosversion = "-";
						HashMap<String,String> data = new HashMap<String,String>(datetimedata);
						data.put("summarised", "clientos, clientosversion");
						data.put("clientos", clientos);
						data.put("clientosversion", clientosversion);
						data.put("clienthost", clienthost);
						data.put("visitorid", visitorid);
						data.put("sessionid", sessionid);
						data.put("hits", "" + Common.integernumber(operatingsystems.get("counthits").get(key)));
						data.put("pageviews", "" + Common.integernumber(operatingsystems.get("countpageviews").get(key)));
						data.put("visits", "" + Common.integernumber(operatingsystems.get("countvisits").get(key)));
						data.put("visitors", "" + Common.integernumber(operatingsystems.get("countvisitors").get(key)));
						data.put("clienthosts", "" + Common.integernumber(operatingsystems.get("countclienthosts").get(key)));
						if (commit) {
							logdb.insert("usagelog", data);
						}
					}
					operatingsystems = null;
				}
				if (devices != null) {
					if (output != null) output.println(text.display("usage.summarise.insert.summarised") + devices.get("counthits").size() + " " + text.display("usage.summarise.insert.devices"));
					rowscount_inserted += devices.get("counthits").size();
					devices.put("counthits", Common.LinkedHashMapSortedByKey(devices.get("counthits"), true));
					Iterator i = devices.get("counthits").keySet().iterator();
					while (i.hasNext()) {
						String key = "" + i.next();
						String[] keyparts = (key+":::::::::::::::::::::::::::x").split(":::");
						String clientdevice = keyparts[0];
						String clientdeviceversion = keyparts[1];
						String clienthost = "";
						String visitorid = "";
						String sessionid = "";
						if (clientdevice.equals("")) clientdevice = "-";
						if (clientdeviceversion.equals("")) clientdeviceversion = "-";
						HashMap<String,String> data = new HashMap<String,String>(datetimedata);
						data.put("summarised", "clientdevice, clientdeviceversion");
						data.put("clientdevice", clientdevice);
						data.put("clientdeviceversion", clientdeviceversion);
						data.put("clienthost", clienthost);
						data.put("visitorid", visitorid);
						data.put("sessionid", sessionid);
						data.put("hits", "" + Common.integernumber(devices.get("counthits").get(key)));
						data.put("pageviews", "" + Common.integernumber(devices.get("countpageviews").get(key)));
						data.put("visits", "" + Common.integernumber(devices.get("countvisits").get(key)));
						data.put("visitors", "" + Common.integernumber(devices.get("countvisitors").get(key)));
						data.put("clienthosts", "" + Common.integernumber(devices.get("countclienthosts").get(key)));
						if (commit) {
							logdb.insert("usagelog", data);
						}
					}
					devices = null;
				}
				if (users != null) {
					if (output != null) output.println(text.display("usage.summarise.insert.summarised") + users.get("counthits").size() + " " + text.display("usage.summarise.insert.users"));
					rowscount_inserted += users.get("counthits").size();
					users.put("counthits", Common.LinkedHashMapSortedByKey(users.get("counthits"), true));
					Iterator i = users.get("counthits").keySet().iterator();
					while (i.hasNext()) {
						String key = "" + i.next();
						String[] keyparts = (key+":::::::::::::::::::::::::::x").split(":::");
						String clientusername = keyparts[0];
						String clienthost = "";
						String visitorid = "";
						String sessionid = "";
						if (clientusername.equals("")) clientusername = "-";
						HashMap<String,String> data = new HashMap<String,String>(datetimedata);
						data.put("summarised", "clientusername");
						data.put("clientusername", clientusername);
						data.put("clienthost", clienthost);
						data.put("visitorid", visitorid);
						data.put("sessionid", sessionid);
						data.put("hits", "" + Common.integernumber(users.get("counthits").get(key)));
						data.put("pageviews", "" + Common.integernumber(users.get("countpageviews").get(key)));
						data.put("visits", "" + Common.integernumber(users.get("countvisits").get(key)));
						data.put("visitors", "" + Common.integernumber(users.get("countvisitors").get(key)));
						data.put("clienthosts", "" + Common.integernumber(users.get("countclienthosts").get(key)));
						if (commit) {
							logdb.insert("usagelog", data);
						}
					}
					users = null;
				}
				if (referers != null) {
					if (output != null) output.println(text.display("usage.summarise.insert.summarised") + referers.get("counthits").size() + " " + text.display("usage.summarise.insert.referers"));
					rowscount_inserted += referers.get("counthits").size();
					referers.put("counthits", Common.LinkedHashMapSortedByKey(referers.get("counthits"), true));
					Iterator i = referers.get("counthits").keySet().iterator();
					while (i.hasNext()) {
						String key = "" + i.next();
						String[] keyparts = (key+":::::::::::::::::::::::::::x").split(":::");
						String refererhost = keyparts[0];
						String refererpath = keyparts[1];
						String clienthost = "";
						String visitorid = "";
						String sessionid = "";
						if (refererhost.equals("")) refererhost = "-";
						HashMap<String,String> data = new HashMap<String,String>(datetimedata);
						data.put("summarised", "refererhost, refererpath");
						data.put("refererhost", refererhost);
						data.put("refererpath", refererpath);
						data.put("clienthost", clienthost);
						data.put("visitorid", visitorid);
						data.put("sessionid", sessionid);
						data.put("hits", "" + Common.integernumber(referers.get("counthits").get(key)));
						data.put("pageviews", "" + Common.integernumber(referers.get("countpageviews").get(key)));
						data.put("visits", "" + Common.integernumber(referers.get("countvisits").get(key)));
						data.put("visitors", "" + Common.integernumber(referers.get("countvisitors").get(key)));
						data.put("clienthosts", "" + Common.integernumber(referers.get("countclienthosts").get(key)));
						if (commit) {
							logdb.insert("usagelog", data);
						}
					}
					referers = null;
				}
				if (searchengines != null) {
					if (output != null) output.println(text.display("usage.summarise.insert.summarised") + searchengines.get("counthits").size() + " " + text.display("usage.summarise.insert.searchengines"));
					rowscount_inserted += searchengines.get("counthits").size();
					searchengines.put("counthits", Common.LinkedHashMapSortedByKey(searchengines.get("counthits"), true));
					Iterator i = searchengines.get("counthits").keySet().iterator();
					while (i.hasNext()) {
						String key = "" + i.next();
						String[] keyparts = (key+":::::::::::::::::::::::::::x").split(":::");
						String referersearchengine = keyparts[0];
						String clienthost = "";
						String visitorid = "";
						String sessionid = "";
						if (referersearchengine.equals("")) referersearchengine = "-";
						HashMap<String,String> data = new HashMap<String,String>(datetimedata);
						data.put("summarised", "referersearchengine");
						data.put("referersearchengine", referersearchengine);
						data.put("clienthost", clienthost);
						data.put("visitorid", visitorid);
						data.put("sessionid", sessionid);
						data.put("hits", "" + Common.integernumber(searchengines.get("counthits").get(key)));
						data.put("pageviews", "" + Common.integernumber(searchengines.get("countpageviews").get(key)));
						data.put("visits", "" + Common.integernumber(searchengines.get("countvisits").get(key)));
						data.put("visitors", "" + Common.integernumber(searchengines.get("countvisitors").get(key)));
						data.put("clienthosts", "" + Common.integernumber(searchengines.get("countclienthosts").get(key)));
						if (commit) {
							logdb.insert("usagelog", data);
						}
					}
					searchengines = null;
				}
				if (searchqueries != null) {
					if (output != null) output.println(text.display("usage.summarise.insert.summarised") + searchqueries.get("counthits").size() + " " + text.display("usage.summarise.insert.searchqueries"));
					rowscount_inserted += searchqueries.get("counthits").size();
					searchqueries.put("counthits", Common.LinkedHashMapSortedByKey(searchqueries.get("counthits"), true));
					Iterator i = searchqueries.get("counthits").keySet().iterator();
					while (i.hasNext()) {
						String key = "" + i.next();
						String[] keyparts = (key+":::::::::::::::::::::::::::x").split(":::");
						String referersearchquery = keyparts[0];
						String clienthost = "";
						String visitorid = "";
						String sessionid = "";
						if (referersearchquery.equals("")) referersearchquery = "-";
						HashMap<String,String> data = new HashMap<String,String>(datetimedata);
						data.put("summarised", "referersearchquery");
						data.put("referersearchquery", referersearchquery);
						data.put("clienthost", clienthost);
						data.put("visitorid", visitorid);
						data.put("sessionid", sessionid);
						data.put("hits", "" + Common.integernumber(searchqueries.get("counthits").get(key)));
						data.put("pageviews", "" + Common.integernumber(searchqueries.get("countpageviews").get(key)));
						data.put("visits", "" + Common.integernumber(searchqueries.get("countvisits").get(key)));
						data.put("visitors", "" + Common.integernumber(searchqueries.get("countvisitors").get(key)));
						data.put("clienthosts", "" + Common.integernumber(searchqueries.get("countclienthosts").get(key)));
						if (commit) {
							logdb.insert("usagelog", data);
						}
					}
					searchqueries = null;
				}
				if (usersegments != null) {
					if (output != null) output.println(text.display("usage.summarise.insert.summarised") + usersegments.get("counthits").size() + " " + text.display("usage.summarise.insert.usersegments"));
					rowscount_inserted += usersegments.get("counthits").size();
					usersegments.put("counthits", Common.LinkedHashMapSortedByKey(usersegments.get("counthits"), true));
					Iterator i = usersegments.get("counthits").keySet().iterator();
					while (i.hasNext()) {
						String key = "" + i.next();
						String[] keyparts = (key+":::::::::::::::::::::::::::x").split(":::");
						String usersegment = keyparts[0];
						String clienthost = "";
						String visitorid = "";
						String sessionid = "";
						if (usersegment.equals("")) usersegment = "-";
						HashMap<String,String> data = new HashMap<String,String>(datetimedata);
						data.put("summarised", "usersegments");
						data.put("usersegments", usersegment);
						data.put("clienthost", clienthost);
						data.put("visitorid", visitorid);
						data.put("sessionid", sessionid);
						data.put("hits", "" + Common.integernumber(usersegments.get("counthits").get(key)));
						data.put("pageviews", "" + Common.integernumber(usersegments.get("countpageviews").get(key)));
						data.put("visits", "" + Common.integernumber(usersegments.get("countvisits").get(key)));
						data.put("visitors", "" + Common.integernumber(usersegments.get("countvisitors").get(key)));
						data.put("clienthosts", "" + Common.integernumber(usersegments.get("countclienthosts").get(key)));
						if (commit) {
							logdb.insert("usagelog", data);
						}
					}
					usersegments = null;
				}
				if (usertests != null) {
					if (output != null) output.println(text.display("usage.summarise.insert.summarised") + usertests.get("counthits").size() + " " + text.display("usage.summarise.insert.usertests"));
					rowscount_inserted += usertests.get("counthits").size();
					usertests.put("counthits", Common.LinkedHashMapSortedByKey(usertests.get("counthits"), true));
					Iterator i = usertests.get("counthits").keySet().iterator();
					while (i.hasNext()) {
						String key = "" + i.next();
						String[] keyparts = (key+":::::::::::::::::::::::::::x").split(":::");
						String usertest = keyparts[0];
						String clienthost = "";
						String visitorid = "";
						String sessionid = "";
						if (usertest.equals("")) usertest = "-";
						HashMap<String,String> data = new HashMap<String,String>(datetimedata);
						data.put("summarised", "usertests");
						data.put("usertests", usertest);
						data.put("clienthost", clienthost);
						data.put("visitorid", visitorid);
						data.put("sessionid", sessionid);
						data.put("hits", "" + Common.integernumber(usertests.get("counthits").get(key)));
						data.put("pageviews", "" + Common.integernumber(usertests.get("countpageviews").get(key)));
						data.put("visits", "" + Common.integernumber(usertests.get("countvisits").get(key)));
						data.put("visitors", "" + Common.integernumber(usertests.get("countvisitors").get(key)));
						data.put("clienthosts", "" + Common.integernumber(usertests.get("countclienthosts").get(key)));
						if (commit) {
							logdb.insert("usagelog", data);
						}
					}
					usertests = null;
				}

				// delete old detailed data

				rowscount_deleted += rowscount_details_pre;
				if (commit) {
					logdb.deleteWhere("usagelog", SQLwheredatetime + " and " + SQLwheredetails);
				}

				long rowscount_post = Math.round(logdb.query_value("select count(*) from usagelog"));
				long rowscount_details_post = Math.round(logdb.query_value("select count(*) from usagelog where " + SQLwheredatetime + " and " + SQLwheredetails));
				long rowscount_summarised_post = Math.round(logdb.query_value("select count(*) from usagelog where " + SQLwheredatetime + " and " + SQLwheresummarised));

				if (output != null) output.println(text.display("usage.summarise.improvement"));

				//if (output != null) output.println(text.display("usage.summarise.current.all") + ": " + rowscount_post);
				//if (output != null) output.println(text.display("usage.summarise.current.details") + ": " + rowscount_details_post);
				//if (output != null) output.println(text.display("usage.summarise.current.summarised") + ": " + rowscount_summarised_post);

				if (output != null) output.println(text.display("usage.summarise.deleted") + ": " + rowscount_deleted);
				if (output != null) output.println(text.display("usage.summarise.inserted") + ": " + rowscount_inserted);
				if (rowscount_deleted == 0) rowscount_deleted = rowscount_inserted;
				if (rowscount_deleted == 0) {
					rowscount_deleted = 1;
					rowscount_inserted = 1;
				}
				if (output != null) output.println(text.display("usage.summarise.compressed") + ": " + Math.round((rowscount_inserted * 100) / rowscount_deleted) + "%");

				if (output != null) output.println("<span id=\"summarised\"></span>");

			}

			summarise_ended = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			if (output != null) output.println(text.display("usage.summarise.ended") + ": " + summarise_ended);
			if (output != null) output.println("<hr>");

			if (! all) config.set(db, "summarise_started", "");

		}

	}



	public boolean records(DB db, String SQL) {
		if ((db == null) || db.isClosed()) return false;
		if ((SQL != null) && (! SQL.equals(""))) {
			rs = db.records(SQL);
			return true;
		} else {
			HashMap<String,String> row = db.records(rs);
			if (row != null) {
				getRecord(db, row);
				return true;
			} else {
				init();
				return false;
			}
		}
	}



	public String getData(String name) {
		return "" + data.get(name);
	}
	public void setData(String name, String value) {
		data.put(name, value);
	}



	public String getSearchEngineOptions(String selected) {
		String myoutput = "";
		HashMap<String,String> mydata = new HashMap<String,String>();
		for (int i=0; i<searchengineName.length; i++) {
			if ((! searchengineName[i].equals("")) && (! searchengineWebsite[i].equals("")) && (! searchengineId[i].equals(""))) {
				mydata.put(searchengineName[i] + " (" + searchengineWebsite[i] + ")", searchengineId[i]);
			}
		}
		Iterator ii = Common.SortedHashMapKeySetIterator(mydata);
		while (ii.hasNext()) {
			String mysearchenginename = "" + ii.next();
			String mysearchengineid = "" + mydata.get(mysearchenginename);
			if ((! mysearchenginename.equals("")) && (! mysearchengineid.equals(""))) {
				myoutput += "<option value=\"" + mysearchengineid + "\">" + mysearchenginename + "</option>" + "\r\n";
			}
		}
		myoutput = myoutput.replaceAll(" (value=\"\\Q" + selected + "\\E\")", " $1 selected");
		return myoutput;
	}



	public String getClientDeviceOptions(String selected) {
		String myoutput = getClientDeviceTypeOptions(selected) + getClientDeviceIdOptions(selected) + getClientDeviceVersionOptions(selected);
		return myoutput;
	}



	public String getClientDeviceTypeOptions(String selected) {
		String myoutput = "";
		HashMap<String,String> mydata = new HashMap<String,String>();
		for (int i=0; i<deviceType.length; i++) {
			if (! deviceType[i].equals("")) {
				mydata.put(deviceType[i], deviceType[i]);
			}
		}
		Iterator ii = Common.SortedHashMapKeySetIterator(mydata);
		while (ii.hasNext()) {
			String mydevicetype = "" + ii.next();
			myoutput += "<option value=\"" + mydevicetype + "\">" + mydevicetype + "</option>" + "\r\n";
		}
		myoutput = myoutput.replaceAll(" (value=\"\\Q" + selected + "\\E\")", " $1 selected");
		return myoutput;
	}



	public String getClientDeviceIdOptions(String selected) {
		String myoutput = "";
		HashMap<String,String> mydata = new HashMap<String,String>();
		for (int i=0; i<deviceId.length; i++) {
			if (! deviceId[i].equals("")) {
				mydata.put(deviceId[i], deviceId[i]);
			}
		}
		Iterator ii = Common.SortedHashMapKeySetIterator(mydata);
		while (ii.hasNext()) {
			String mydeviceid = "" + ii.next();
			myoutput += "<option value=\"" + mydeviceid + "\">" + mydeviceid + "</option>" + "\r\n";
		}
		myoutput = myoutput.replaceAll(" (value=\"\\Q" + selected + "\\E\")", " $1 selected");
		return myoutput;
	}



	public String getClientDeviceVersionOptions(String selected) {
		String myoutput = "";
		HashMap<String,String> mydata = new HashMap<String,String>();
		for (int i=0; i<deviceVersion.length; i++) {
			if ((! deviceVersion[i].equals("")) && (! deviceId[i].equals(""))) {
				mydata.put(deviceVersion[i], deviceId[i]);
			}
		}
		Iterator ii = Common.SortedHashMapKeySetIterator(mydata);
		while (ii.hasNext()) {
			String mydeviceversion = "" + ii.next();
			String mydeviceid = "" + mydata.get(mydeviceversion);
			if ((! mydeviceversion.equals("")) && (! mydeviceid.equals(""))) {
				myoutput += "<option value=\"" + mydeviceid + "\">" + mydeviceversion + "</option>" + "\r\n";
			}
		}
		myoutput = myoutput.replaceAll(" (value=\"\\Q" + selected + "\\E\")", " $1 selected");
		return myoutput;
	}



	public String getClientOSOptions(String selected) {
		String myoutput = "";
		HashMap<String,String> mydata = new HashMap<String,String>();
		for (int i=0; i<osVersion.length; i++) {
			if (! osVersion[i].equals("")) {
				mydata.put(osVersion[i], osId[i]);
				mydata.put(osName[i], osName[i]);
			}
		}
		Iterator ii = Common.SortedHashMapKeySetIterator(mydata);
		while (ii.hasNext()) {
			String myosname = "" + ii.next();
			String myosid = "" + mydata.get(myosname);
			myoutput += "<option value=\"" + myosid + "\">" + myosname + "</option>" + "\r\n";
		}
		myoutput = myoutput.replaceAll(" (value=\"\\Q" + selected + "\\E\")", " $1 selected");
		return myoutput;
	}



	public String getClientBrowserOptions(String selected) {
		String myoutput = "";
		// major web browsers
		myoutput += "<option value=\"Safari\">Apple Safari</option>" + "\r\n";
		myoutput += "<option value=\"Chrome\">Google Chrome</option>" + "\r\n";
		myoutput += "<option value=\"MSIE\">Microsoft Internet Explorer</option>" + "\r\n";
		myoutput += "<option value=\"MSIE6\">Microsoft Internet Explorer 6</option>" + "\r\n";
		myoutput += "<option value=\"MSIE7\">Microsoft Internet Explorer 7</option>" + "\r\n";
		myoutput += "<option value=\"MSIE8\">Microsoft Internet Explorer 8</option>" + "\r\n";
		myoutput += "<option value=\"MSIE9\">Microsoft Internet Explorer 9</option>" + "\r\n";
		myoutput += "<option value=\"MSIE10\">Microsoft Internet Explorer 10</option>" + "\r\n";
		myoutput += "<option value=\"MSIE11\">Microsoft Internet Explorer 11</option>" + "\r\n";
		myoutput += "<option value=\"Firefox\">Mozilla Firefox</option>" + "\r\n";
		myoutput += "<option value=\"Opera\">Opera</option>" + "\r\n";

		HashMap<String,String> mydata = new HashMap<String,String>();
		// non-pattern programmatically identified web browsers
		mydata.put("Mozilla", "Mozilla");
		mydata.put("Netscape", "Netscape");
		mydata.put("Subversion", "Subversion");
		mydata.put("Konqueror", "Konqueror");
		// pattern identified web browsers
		for (int i=0; i<browserName.length; i++) {
			if (! browserName[i].equals("")) {
				mydata.put(browserName[i], browserName[i]);
			}
		}
		Iterator ii = Common.SortedHashMapKeySetIterator(mydata);
		while (ii.hasNext()) {
			String mybrowsername = "" + ii.next();
			myoutput += "<option value=\"" + mybrowsername + "\">" + mybrowsername + "</option>" + "\r\n";
		}
		myoutput = myoutput.replaceAll(" (value=\"\\Q" + selected + "\\E\")", " $1 selected");
		return myoutput;
	}



	public HashMap<String,String> getClientDevice(String useragent) {
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("clientdevice", "");
		mydata.put("clientdeviceid", "");
		mydata.put("clientdeviceversion", "");
		if (useragent == null) return mydata;
		if (useragent.equals("")) return mydata;
		if (useragent.contains("ApacheBench")) return mydata;
		String lc_useragent = useragent.toLowerCase();
		for (int i=0; i<devicePattern.length; i++) {
			if (lc_useragent.contains(devicePattern[i])) {
				mydata.put("clientdevice", deviceType[i]);
				mydata.put("clientdeviceid", deviceId[i]);
				mydata.put("clientdeviceversion", deviceVersion[i]);
				break;
			} else if ((devicePattern[i].contains("*")) && (lc_useragent.matches(".*"+devicePattern[i]+".*"))) {
				mydata.put("clientdevice", deviceType[i]);
				mydata.put("clientdeviceid", deviceId[i]);
				mydata.put("clientdeviceversion", deviceVersion[i]);
				break;
			}
		}
		return mydata;
	}



	public HashMap<String,String> getClientOS(String useragent) {
		return getClientOS(useragent, true);
	}
	public HashMap<String,String> getClientOS(String useragent, boolean minorclients) {
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("clientos", "");
		mydata.put("clientosversion", "");
		if (useragent == null) return mydata;
		if (useragent.equals("")) return mydata;
		if (useragent.contains("ApacheBench")) return mydata;
		String lc_useragent = useragent.toLowerCase();
		if (lc_useragent.contains("windows")) {
			if (lc_useragent.contains("nt")) {
				if (lc_useragent.contains("6.2")) {
					mydata.put("clientos", "Windows");
					mydata.put("clientosversion", "Windows 8");
				} else if (lc_useragent.contains("6.1")) {
					mydata.put("clientos", "Windows");
					mydata.put("clientosversion", "Windows 7");
				} else if (lc_useragent.contains("6.0")) {
					mydata.put("clientos", "Windows");
					mydata.put("clientosversion", "Windows Longhorn");
				} else if (lc_useragent.contains("6")) {
					mydata.put("clientos", "Windows");
					mydata.put("clientosversion", "Windows Vista");
				} else if (lc_useragent.contains("5.2")) {
					mydata.put("clientos", "Windows");
					mydata.put("clientosversion", "Windows 2003");
				} else if (lc_useragent.contains("5.1")) {
					mydata.put("clientos", "Windows");
					mydata.put("clientosversion", "Windows XP");
				}
			} else if (lc_useragent.contains("vista")) {
				mydata.put("clientos", "Windows");
				mydata.put("clientosversion", "Windows Vista");
			} else if (lc_useragent.contains("2008")) {
				mydata.put("clientos", "Windows");
				mydata.put("clientosversion", "Windows 2008");
			} else if (lc_useragent.contains("2005")) {
				mydata.put("clientos", "Windows");
				mydata.put("clientosversion", "Windows Longhorn");
			} else if (lc_useragent.contains("2003")) {
				mydata.put("clientos", "Windows");
				mydata.put("clientosversion", "Windows 2003");
			} else if (lc_useragent.contains("2000")) {
				mydata.put("clientos", "Windows");
				mydata.put("clientosversion", "Windows 2000");
			} else if (lc_useragent.contains("xp")) {
				mydata.put("clientos", "Windows");
				mydata.put("clientosversion", "Windows XP");
			} else if ((lc_useragent.contains("nt")) && (lc_useragent.contains("5"))) {
				mydata.put("clientos", "Windows");
				mydata.put("clientosversion", "Windows 2000");
			} else if (lc_useragent.contains("nt")) {
				mydata.put("clientos", "Windows");
				mydata.put("clientosversion", "Windows NT");
			} else if (lc_useragent.contains("me")) {
				mydata.put("clientos", "Windows");
				mydata.put("clientosversion", "Windows ME");
			} else if (lc_useragent.contains("3")) {
				mydata.put("clientos", "Windows");
				mydata.put("clientosversion", "Windows 3.xx");
			} else {
				mydata.put("clientos", "Windows");
				mydata.put("clientosversion", "Windows (unknown)");
			}
		} else if ((lc_useragent.contains("iphone")) || (lc_useragent.contains("ipad")) || (lc_useragent.contains("ipod"))) {
			Pattern pattern = Pattern.compile(".*"+"version\\/([0-9]+[\\.][0-9]*)"+".*");
			Matcher matches = pattern.matcher(lc_useragent);
			if (matches.find()) {
				mydata.put("clientos", "iOS");
				if (matches.group(1).length() > 20) {
					mydata.put("clientosversion", matches.group(1).substring(0,20));
				} else {
					mydata.put("clientosversion", matches.group(1));
				}
			} else {
				mydata.put("clientos", "iOS");
				mydata.put("clientosversion", "iOS");
			}
		} else if (lc_useragent.contains("mac")) {
			if ((lc_useragent.contains("os")) && (lc_useragent.contains("x"))) {
				mydata.put("clientos", "Mac OS");
				mydata.put("clientosversion", "Mac OS X");
			} else {
				mydata.put("clientos", "Mac OS");
				mydata.put("clientosversion", "Mac OS");
			}
		} else if ((lc_useragent.contains("linux")) || (lc_useragent.contains("android"))) {
			mydata.put("clientos", "Linux");
			mydata.put("clientosversion", "Google Android");
		} else if (minorclients) {
			for (int i=0; i<osPattern.length; i++) {
				if (lc_useragent.contains(osPattern[i])) {
					mydata.put("clientos", osName[i]);
					mydata.put("clientosversion", osVersion[i]);
					break;
				} else if ((osPattern[i].contains("*")) && (lc_useragent.matches(".*"+osPattern[i]+".*"))) {
					mydata.put("clientos", osName[i]);
					mydata.put("clientosversion", osVersion[i]);
					break;
				}
			}
		}
		return mydata;
	}



	public HashMap<String,String> getClientBrowser(String useragent) {
		return getClientBrowser(useragent, true);
	}
	public HashMap<String,String> getClientBrowser(String useragent, boolean minorclients) {
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("clientbrowser", "");
		mydata.put("clientversion", "");
		if (useragent == null) return mydata;
		if (useragent.equals("")) return mydata;
		if (useragent.contains("ApacheBench")) return mydata;
		String lc_useragent = useragent.toLowerCase();
		if ((lc_useragent.matches(".*"+"msie([+_ ]?)([\\.0-9]*)"+".*")) && (! lc_useragent.matches(".*"+"(webtv|omniweb|opera)"+".*"))) {
			Pattern pattern = Pattern.compile(".*"+"msie([+_ ]?)([0-9]*)"+".*");
			Matcher matches = pattern.matcher(lc_useragent);
			if (matches.find()) {
				mydata.put("clientbrowser", "MSIE");
				if (matches.group(2).length() > 20) {
					mydata.put("clientversion", matches.group(2).substring(0,20));
				} else {
					mydata.put("clientversion", matches.group(2));
				}
			}
		} else if ((lc_useragent.matches(".*"+"trident/([\\.0-9]*)"+".*")) && (! lc_useragent.matches(".*"+"(webtv|omniweb|opera)"+".*")) && (lc_useragent.matches(".*"+"rv:([\\.0-9]+)"+".*"))) {
			Pattern pattern = Pattern.compile(".*"+"rv:([0-9]+)"+".*");
			Matcher matches = pattern.matcher(lc_useragent);
			if (matches.find()) {
				mydata.put("clientbrowser", "MSIE");
				if (matches.group(1).length() > 20) {
					mydata.put("clientversion", matches.group(1).substring(0,20));
				} else {
					mydata.put("clientversion", matches.group(1));
				}
			}
		} else if ((lc_useragent.matches(".*"+"firefox\\/([\\.0-9]*)"+".*")) && (! lc_useragent.matches(".*"+"flock"+".*"))) {
			Pattern pattern = Pattern.compile(".*"+"firefox\\/([0-9]*)"+".*");
			Matcher matches = pattern.matcher(lc_useragent);
			if (matches.find()) {
				mydata.put("clientbrowser", "Firefox");
				if (matches.group(1).length() > 20) {
					mydata.put("clientversion", matches.group(1).substring(0,20));
				} else {
					mydata.put("clientversion", matches.group(1));
				}
			}
		} else if ((lc_useragent.matches(".*"+"safari\\/([\\.0-9]*)"+".*")) && (! lc_useragent.matches(".*"+"(android|arora|chrome|shiira)"+".*"))) {
			Pattern pattern = Pattern.compile(".*"+"version\\/([0-9]+[\\.][0-9]*)"+".*");
			Matcher matches = pattern.matcher(lc_useragent);
			Pattern pattern2 = Pattern.compile(".*"+"safari\\/([\\.0-9]*)"+".*");
			Matcher matches2 = pattern2.matcher(lc_useragent);
			if (matches.find()) {
				mydata.put("clientbrowser", "Safari");
				if (matches.group(1).length() > 20) {
					mydata.put("clientversion", matches.group(1).substring(0,20));
				} else {
					mydata.put("clientversion", matches.group(1));
				}
			} else if (matches2.find()) {
				mydata.put("clientbrowser", "Safari");
				if (matches2.group(1).length() > 20) {
					mydata.put("clientversion", matches2.group(1).substring(0,20));
				} else {
					mydata.put("clientversion", matches2.group(1));
				}
			}
		} else if ((lc_useragent.matches(".*"+"chrome\\/([0-9]*)"+".*"))) {
			Pattern pattern = Pattern.compile(".*"+"chrome\\/([0-9]*)"+".*");
			Matcher matches = pattern.matcher(lc_useragent);
			if (matches.find()) {
				mydata.put("clientbrowser", "Chrome");
				if (matches.group(1).length() > 20) {
					mydata.put("clientversion", matches.group(1).substring(0,20));
				} else {
					mydata.put("clientversion", matches.group(1));
				}
			}
		} else if ((lc_useragent.matches(".*"+"konqueror\\/([\\.0-9]*)"+".*"))) {
			Pattern pattern = Pattern.compile(".*"+"konqueror\\/([\\.0-9]*)"+".*");
			Matcher matches = pattern.matcher(lc_useragent);
			if (matches.find()) {
				mydata.put("clientbrowser", "Konqueror");
				if (matches.group(1).length() > 20) {
					mydata.put("clientversion", matches.group(1).substring(0,20));
				} else {
					mydata.put("clientversion", matches.group(1));
				}
			}
		} else if ((lc_useragent.matches(".*"+"opera\\/([\\.0-9]*)"+".*"))) {
			Pattern pattern = Pattern.compile(".*"+"opera\\/([\\.0-9]*)"+".*");
			Matcher matches = pattern.matcher(lc_useragent);
			if (matches.find()) {
				mydata.put("clientbrowser", "Opera");
				if (matches.group(1).length() > 20) {
					mydata.put("clientversion", matches.group(1).substring(0,20));
				} else {
					mydata.put("clientversion", matches.group(1));
				}
			}
		} else if ((lc_useragent.matches(".*"+"svn\\/([\\.0-9]*)"+".*"))) {
			Pattern pattern = Pattern.compile(".*"+"svn\\/([\\.0-9]*)"+".*");
			Matcher matches = pattern.matcher(lc_useragent);
			if (matches.find()) {
				mydata.put("clientbrowser", "Subversion");
				if (matches.group(1).length() > 20) {
					mydata.put("clientversion", matches.group(1).substring(0,20));
				} else {
					mydata.put("clientversion", matches.group(1));
				}
			}
		} else if ((lc_useragent.matches(".*"+"netscape.?\\/([\\.0-9]*)"+".*"))) {
			Pattern pattern = Pattern.compile(".*"+"netscape.?\\/([\\.0-9]*)"+".*");
			Matcher matches = pattern.matcher(lc_useragent);
			if (matches.find()) {
				mydata.put("clientbrowser", "Netscape");
				if (matches.group(1).length() > 20) {
					mydata.put("clientversion", matches.group(1).substring(0,20));
				} else {
					mydata.put("clientversion", matches.group(1));
				}
			}
		} else if (((lc_useragent.matches(".*"+"mozilla\\/?([\\.0-9]*)"+".*")) && (! lc_useragent.matches(".*"+"(gecko|compatible|opera|galeon|safari)"+".*")))) {
			Pattern pattern = Pattern.compile(".*"+"mozilla\\/?([\\.0-9]*)"+".*");
			Matcher matches = pattern.matcher(lc_useragent);
			if (matches.find()) {
				mydata.put("clientbrowser", "Mozilla");
				if (matches.group(1).length() > 20) {
					mydata.put("clientversion", matches.group(1).substring(0,20));
				} else {
					mydata.put("clientversion", matches.group(1));
				}
			}
		} else if (minorclients) {
			for (int i=0; i<browserPattern.length; i++) {
				if ((lc_useragent.matches(".*"+browserPattern[i]+".*"))) {
					mydata.put("clientbrowser", browserName[i]);
//						mydata.put("clientversion", browserVersion[i]);
					mydata.put("clientversion", "");
					break;
				}
			}
			if (mydata.get("clientbrowser").equals("")) {
				for (int i=0; i<robotPattern.length; i++) {
					if ((lc_useragent.contains(robotPattern[i]))) {
						mydata.put("clientbrowser", robotName[i]);
						mydata.put("clientversion", robotVersion[i]);
						break;
					} else if ((robotPattern[i].contains("*")) && (lc_useragent.matches(".*"+robotPattern[i]+".*"))) {
						mydata.put("clientbrowser", robotName[i]);
						mydata.put("clientversion", robotVersion[i]);
						break;
					}
				}
			}
			if (mydata.get("clientbrowser").equals("")) {
				if (((lc_useragent.matches(".*"+"mozilla"+".*")) && (! lc_useragent.matches(".*"+"compatible"+".*")) && (lc_useragent.matches(".*"+"rv:([\\.0-9]+)"+".*")))) {
					Pattern pattern = Pattern.compile(".*"+"rv:([\\.0-9]+)"+".*");
					Matcher matches = pattern.matcher(lc_useragent);
					if (matches.find()) {
						mydata.put("clientbrowser", "Mozilla");
						if (matches.group(1).length() > 20) {
							mydata.put("clientversion", matches.group(1).substring(0,20));
						} else {
							mydata.put("clientversion", matches.group(1));
						}
					}
				} else {
					mydata.put("clientbrowser", "unknown");
					mydata.put("clientversion", "");
				}
			}
		}
		return mydata;
	}



	static public String getCount(DB db) {
		return db.query_string("select count(*) from usagelog");
	}



	static public String getOldestDetailed(DB db) {
		return db.query_string("select min(datetimefull) from usagelog where " + db.is_blank("summarised"));
	}



	static public String getOldestSummarised(DB db) {
		return db.query_string("select min(datetimefull) from usagelog where " + db.is_not_blank("summarised"));
	}



	static public String getNewestSummarised(DB db) {
		return db.query_string("select max(datetimefull) from usagelog where " + db.is_not_blank("summarised"));
	}



}
