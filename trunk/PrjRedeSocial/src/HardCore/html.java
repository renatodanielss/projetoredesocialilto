package HardCore;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.concurrent.ConcurrentHashMap;	// WCM 8.0 - requires Java 1.5
import java.util.HashMap;

public class html {
	private static String encoding = "UTF-8";



	public static void charset(String myencoding) {
		if ((myencoding != null) && (! myencoding.equals(""))) {
			encoding = myencoding;
		}
	}



	public static String encode(String value) {
		String htmltext = "";
		if (value != null) htmltext = "" + value;
		htmltext = htmltext.replaceAll("\\&", "&amp;");
		htmltext = htmltext.replaceAll("<", "&lt;");
		htmltext = htmltext.replaceAll(">", "&gt;");
		htmltext = htmltext.replaceAll("\"", "&quot;");
		return htmltext;
	}



	public static String decode(String value) {
		String htmltext = "";
		if (value != null) htmltext = "" + value;
		htmltext = htmltext.replaceAll("\\&quot;", "\"");
		htmltext = htmltext.replaceAll("\\&gt;", ">");
		htmltext = htmltext.replaceAll("\\&lt;", "<");
		htmltext = htmltext.replaceAll("\\&amp;", "&");
//		for (int i=1; i<=255; i++) {
//			htmltext.replaceAll("&#" + i + ";", (char)i );
//		}
		return htmltext;
	}



	private static final Object[][] htmlentities_table = {
		{ new Integer(34), new String("&quot;") },
		{ new Integer(60), new String("&lt;") },
		{ new Integer(62), new String("&gt;") },
		{ new Integer(193), new String("&Aacute;") },
		{ new Integer(225), new String("&aacute;") },
		{ new Integer(194), new String("&Acirc;") },
		{ new Integer(226), new String("&acirc;") },
		{ new Integer(180), new String("&acute;") },
		{ new Integer(198), new String("&AElig;") },
		{ new Integer(230), new String("&aelig;") },
		{ new Integer(192), new String("&Agrave;") },
		{ new Integer(224), new String("&agrave;") },
		{ new Integer(8501), new String("&alefsym;") },
		{ new Integer(913), new String("&Alpha;") },
		{ new Integer(945), new String("&alpha;") },
		{ new Integer(38), new String("&amp;") },
		{ new Integer(8743), new String("&and;") },
		{ new Integer(8736), new String("&ang;") },
		{ new Integer(197), new String("&Aring;") },
		{ new Integer(229), new String("&aring;") },
		{ new Integer(8776), new String("&asymp;") },
		{ new Integer(195), new String("&Atilde;") },
		{ new Integer(227), new String("&atilde;") },
		{ new Integer(196), new String("&Auml;") },
		{ new Integer(228), new String("&auml;") },
		{ new Integer(8222), new String("&bdquo;") },
		{ new Integer(914), new String("&Beta;") },
		{ new Integer(946), new String("&beta;") },
		{ new Integer(166), new String("&brvbar;") },
		{ new Integer(8226), new String("&bull;") },
		{ new Integer(8745), new String("&cap;") },
		{ new Integer(199), new String("&Ccedil;") },
		{ new Integer(231), new String("&ccedil;") },
		{ new Integer(184), new String("&cedil;") },
		{ new Integer(162), new String("&cent;") },
		{ new Integer(935), new String("&Chi;") },
		{ new Integer(967), new String("&chi;") },
		{ new Integer(710), new String("&circ;") },
		{ new Integer(9827), new String("&clubs;") },
		{ new Integer(8773), new String("&cong;") },
		{ new Integer(169), new String("&copy;") },
		{ new Integer(8629), new String("&crarr;") },
		{ new Integer(8746), new String("&cup;") },
		{ new Integer(164), new String("&curren;") },
		{ new Integer(8224), new String("&dagger;") },
		{ new Integer(8225), new String("&Dagger;") },
		{ new Integer(8595), new String("&darr;") },
		{ new Integer(8659), new String("&dArr;") },
		{ new Integer(176), new String("&deg;") },
		{ new Integer(916), new String("&Delta;") },
		{ new Integer(948), new String("&delta;") },
		{ new Integer(9830), new String("&diams;") },
		{ new Integer(247), new String("&divide;") },
		{ new Integer(201), new String("&Eacute;") },
		{ new Integer(233), new String("&eacute;") },
		{ new Integer(202), new String("&Ecirc;") },
		{ new Integer(234), new String("&ecirc;") },
		{ new Integer(200), new String("&Egrave;") },
		{ new Integer(232), new String("&egrave;") },
		{ new Integer(8709), new String("&empty;") },
		{ new Integer(8195), new String("&emsp;") },
		{ new Integer(8194), new String("&ensp;") },
		{ new Integer(917), new String("&Epsilon;") },
		{ new Integer(949), new String("&epsilon;") },
		{ new Integer(8801), new String("&equiv;") },
		{ new Integer(919), new String("&Eta;") },
		{ new Integer(951), new String("&eta;") },
		{ new Integer(208), new String("&ETH;") },
		{ new Integer(240), new String("&eth;") },
		{ new Integer(203), new String("&Euml;") },
		{ new Integer(235), new String("&euml;") },
		{ new Integer(8364), new String("&euro;") },
		{ new Integer(8707), new String("&exist;") },
		{ new Integer(402), new String("&fnof;") },
		{ new Integer(8704), new String("&forall;") },
		{ new Integer(189), new String("&frac12;") },
		{ new Integer(188), new String("&frac14;") },
		{ new Integer(190), new String("&frac34;") },
		{ new Integer(8260), new String("&frasl;") },
		{ new Integer(915), new String("&Gamma;") },
		{ new Integer(947), new String("&gamma;") },
		{ new Integer(8805), new String("&ge;") },
		{ new Integer(8596), new String("&harr;") },
		{ new Integer(8660), new String("&hArr;") },
		{ new Integer(9829), new String("&hearts;") },
		{ new Integer(8230), new String("&hellip;") },
		{ new Integer(205), new String("&Iacute;") },
		{ new Integer(237), new String("&iacute;") },
		{ new Integer(206), new String("&Icirc;") },
		{ new Integer(238), new String("&icirc;") },
		{ new Integer(161), new String("&iexcl;") },
		{ new Integer(204), new String("&Igrave;") },
		{ new Integer(236), new String("&igrave;") },
		{ new Integer(8465), new String("&image;") },
		{ new Integer(8734), new String("&infin;") },
		{ new Integer(8747), new String("&int;") },
		{ new Integer(921), new String("&Iota;") },
		{ new Integer(953), new String("&iota;") },
		{ new Integer(191), new String("&iquest;") },
		{ new Integer(8712), new String("&isin;") },
		{ new Integer(207), new String("&Iuml;") },
		{ new Integer(239), new String("&iuml;") },
		{ new Integer(922), new String("&Kappa;") },
		{ new Integer(954), new String("&kappa;") },
		{ new Integer(923), new String("&Lambda;") },
		{ new Integer(955), new String("&lambda;") },
		{ new Integer(9001), new String("&lang;") },
		{ new Integer(171), new String("&laquo;") },
		{ new Integer(8592), new String("&larr;") },
		{ new Integer(8656), new String("&lArr;") },
		{ new Integer(8968), new String("&lceil;") },
		{ new Integer(8220), new String("&ldquo;") },
		{ new Integer(8804), new String("&le;") },
		{ new Integer(8970), new String("&lfloor;") },
		{ new Integer(8727), new String("&lowast;") },
		{ new Integer(9674), new String("&loz;") },
		{ new Integer(8206), new String("&lrm;") },
		{ new Integer(8249), new String("&lsaquo;") },
		{ new Integer(8216), new String("&lsquo;") },
		{ new Integer(175), new String("&macr;") },
		{ new Integer(8212), new String("&mdash;") },
		{ new Integer(181), new String("&micro;") },
		{ new Integer(183), new String("&middot;") },
		{ new Integer(8722), new String("&minus;") },
		{ new Integer(924), new String("&Mu;") },
		{ new Integer(956), new String("&mu;") },
		{ new Integer(8711), new String("&nabla;") },
		{ new Integer(160), new String("&nbsp;") },
		{ new Integer(8211), new String("&ndash;") },
		{ new Integer(8800), new String("&ne;") },
		{ new Integer(8715), new String("&ni;") },
		{ new Integer(172), new String("&not;") },
		{ new Integer(8713), new String("&notin;") },
		{ new Integer(8836), new String("&nsub;") },
		{ new Integer(209), new String("&Ntilde;") },
		{ new Integer(241), new String("&ntilde;") },
		{ new Integer(925), new String("&Nu;") },
		{ new Integer(957), new String("&nu;") },
		{ new Integer(211), new String("&Oacute;") },
		{ new Integer(243), new String("&oacute;") },
		{ new Integer(212), new String("&Ocirc;") },
		{ new Integer(244), new String("&ocirc;") },
		{ new Integer(338), new String("&OElig;") },
		{ new Integer(339), new String("&oelig;") },
		{ new Integer(210), new String("&Ograve;") },
		{ new Integer(242), new String("&ograve;") },
		{ new Integer(8254), new String("&oline;") },
		{ new Integer(937), new String("&Omega;") },
		{ new Integer(969), new String("&omega;") },
		{ new Integer(927), new String("&Omicron;") },
		{ new Integer(959), new String("&omicron;") },
		{ new Integer(8853), new String("&oplus;") },
		{ new Integer(8744), new String("&or;") },
		{ new Integer(170), new String("&ordf;") },
		{ new Integer(186), new String("&ordm;") },
		{ new Integer(216), new String("&Oslash;") },
		{ new Integer(248), new String("&oslash;") },
		{ new Integer(213), new String("&Otilde;") },
		{ new Integer(245), new String("&otilde;") },
		{ new Integer(8855), new String("&otimes;") },
		{ new Integer(214), new String("&Ouml;") },
		{ new Integer(246), new String("&ouml;") },
		{ new Integer(182), new String("&para;") },
		{ new Integer(8706), new String("&part;") },
		{ new Integer(8240), new String("&permil;") },
		{ new Integer(8869), new String("&perp;") },
		{ new Integer(934), new String("&Phi;") },
		{ new Integer(966), new String("&phi;") },
		{ new Integer(928), new String("&Pi;") },
		{ new Integer(960), new String("&pi;") },
		{ new Integer(982), new String("&piv;") },
		{ new Integer(177), new String("&plusmn;") },
		{ new Integer(163), new String("&pound;") },
		{ new Integer(8242), new String("&prime;") },
		{ new Integer(8243), new String("&Prime;") },
		{ new Integer(8719), new String("&prod;") },
		{ new Integer(8733), new String("&prop;") },
		{ new Integer(936), new String("&Psi;") },
		{ new Integer(968), new String("&psi;") },
		{ new Integer(8730), new String("&radic;") },
		{ new Integer(9002), new String("&rang;") },
		{ new Integer(187), new String("&raquo;") },
		{ new Integer(8594), new String("&rarr;") },
		{ new Integer(8658), new String("&rArr;") },
		{ new Integer(8969), new String("&rceil;") },
		{ new Integer(8221), new String("&rdquo;") },
		{ new Integer(8476), new String("&real;") },
		{ new Integer(174), new String("&reg;") },
		{ new Integer(8971), new String("&rfloor;") },
		{ new Integer(929), new String("&Rho;") },
		{ new Integer(961), new String("&rho;") },
		{ new Integer(8207), new String("&rlm;") },
		{ new Integer(8250), new String("&rsaquo;") },
		{ new Integer(8217), new String("&rsquo;") },
		{ new Integer(8218), new String("&sbquo;") },
		{ new Integer(352), new String("&Scaron;") },
		{ new Integer(353), new String("&scaron;") },
		{ new Integer(8901), new String("&sdot;") },
		{ new Integer(167), new String("&sect;") },
		{ new Integer(173), new String("&shy;") },
		{ new Integer(931), new String("&Sigma;") },
		{ new Integer(963), new String("&sigma;") },
		{ new Integer(962), new String("&sigmaf;") },
		{ new Integer(8764), new String("&sim;") },
		{ new Integer(9824), new String("&spades;") },
		{ new Integer(8834), new String("&sub;") },
		{ new Integer(8838), new String("&sube;") },
		{ new Integer(8721), new String("&sum;") },
		{ new Integer(185), new String("&sup1;") },
		{ new Integer(178), new String("&sup2;") },
		{ new Integer(179), new String("&sup3;") },
		{ new Integer(8835), new String("&sup;") },
		{ new Integer(8839), new String("&supe;") },
		{ new Integer(223), new String("&szlig;") },
		{ new Integer(932), new String("&Tau;") },
		{ new Integer(964), new String("&tau;") },
		{ new Integer(8756), new String("&there4;") },
		{ new Integer(920), new String("&Theta;") },
		{ new Integer(952), new String("&theta;") },
		{ new Integer(977), new String("&thetasym;") },
		{ new Integer(8201), new String("&thinsp;") },
		{ new Integer(222), new String("&THORN;") },
		{ new Integer(254), new String("&thorn;") },
		{ new Integer(732), new String("&tilde;") },
		{ new Integer(215), new String("&times;") },
		{ new Integer(8482), new String("&trade;") },
		{ new Integer(218), new String("&Uacute;") },
		{ new Integer(250), new String("&uacute;") },
		{ new Integer(8593), new String("&uarr;") },
		{ new Integer(8657), new String("&uArr;") },
		{ new Integer(219), new String("&Ucirc;") },
		{ new Integer(251), new String("&ucirc;") },
		{ new Integer(217), new String("&Ugrave;") },
		{ new Integer(249), new String("&ugrave;") },
		{ new Integer(168), new String("&uml;") },
		{ new Integer(978), new String("&upsih;") },
		{ new Integer(933), new String("&Upsilon;") },
		{ new Integer(965), new String("&upsilon;") },
		{ new Integer(220), new String("&Uuml;") },
		{ new Integer(252), new String("&uuml;") },
		{ new Integer(8472), new String("&weierp;") },
		{ new Integer(926), new String("&Xi;") },
		{ new Integer(958), new String("&xi;") },
		{ new Integer(221), new String("&Yacute;") },
		{ new Integer(253), new String("&yacute;") },
		{ new Integer(165), new String("&yen;") },
		{ new Integer(255), new String("&yuml;") },
		{ new Integer(376), new String("&Yuml;") },
		{ new Integer(918), new String("&Zeta;") },
		{ new Integer(950), new String("&zeta;") },
		{ new Integer(8205), new String("&zwj;") },
		{ new Integer(8204), new String("&zwnj;") }
	};
	private static final ConcurrentHashMap<String,String> htmlentities_char2entity = new ConcurrentHashMap<String,String>();	// WCM 8.0 - requires Java 1.5
	private static final ConcurrentHashMap<String,Integer> htmlentities_entity2char = new ConcurrentHashMap<String,Integer>();	// WCM 8.0 - requires Java 1.5



	private static void initHtmlEntities() {
		if (htmlentities_char2entity.isEmpty()) {
			for (int i = 0; i < htmlentities_table.length; ++i) {
				Integer ch = (Integer)htmlentities_table[i][0];
				String entity = (String)htmlentities_table[i][1];
				htmlentities_char2entity.put(""+ch, entity);
				htmlentities_entity2char.put(""+entity, ch);
			}
		}
	}



	public static String encodeHtmlEntities(String content) {
		if (htmlentities_char2entity.isEmpty()) initHtmlEntities();
		StringBuffer output = new StringBuffer();
	        try {
	        	Charset charset = null;
		        try {
		        	charset = Charset.forName(encoding);
		        } catch (Exception e) {
		        	charset = Charset.forName("UTF-8");
		        }
		        CharsetEncoder encoder = charset.newEncoder();
			for (int i=0; i<content.length(); ++i) {
				char ch = content.charAt(i);
				Integer chcode = new Integer(ch);
				String entity = (String)htmlentities_char2entity.get(""+chcode);
				if (entity == null) {
					if ((ch > 128) && (! encoder.canEncode(ch))) {
						output.append("&#" + chcode + ";");
					} else {
						output.append(ch);
					}
				} else {
					output.append(entity);
				}
			}
		} catch (Exception e) {
			System.out.println("HardCore/Common:htmlentities_table:encoding:"+encoding+":"+e);
			output = new StringBuffer();
			output.append(content);
		}
//		return output.toString().replaceAll("&amp;amp;", "&amp;");
		return output.toString().replaceAll("&amp;([a-zA-Z0-9]+);", "&$1;");
	}
	public static String encodeHtmlCharCodes(String content) {
		StringBuffer output = new StringBuffer();
	        try {
			for (int i=0; i<content.length(); ++i) {
				char ch = content.charAt(i);
				Integer chcode = new Integer(ch);
				if (ch > 128) {
					output.append("&#" + chcode + ";");
				} else {
					output.append(ch);
				}
			}
		} catch (Exception e) {
			System.out.println("HardCore/Common:htmlentities_table:encoding:"+encoding+":"+e);
			output = new StringBuffer();
			output.append(content);
		}
//		return output.toString().replaceAll("&amp;amp;", "&amp;");
		return output.toString().replaceAll("&amp;([a-zA-Z0-9]+);", "&$1;");
	}



	public static String decodeHtmlEntities(String content) {
		if (htmlentities_entity2char.isEmpty()) initHtmlEntities();
		int i = 0, j = 0, pos = 0;
		StringBuffer output = new StringBuffer();
		while ((i = content.indexOf("&", pos)) != -1 && (j = content.indexOf(';', i)) != -1) {
			// output non-entities
			for (int k=pos; k<i; ++k) output.append(content.charAt(k));
			pos = i;
		
			String entity = content.substring(i, j+1);
			if (htmlentities_entity2char.get(""+entity) != null) {
				// output decoded entity name
				Integer chcode = (Integer)htmlentities_entity2char.get(""+entity); 
				output.append((char)chcode.intValue());
				pos = j + 1;
			} else if (content.charAt(i+1) == '#') {
				// decode numeric entity
				int n = -1;
				for (i += 2; i < j; ++i) {
					char c = content.charAt(i);
					if (('0' <= c) && (c <= '9')) {
			    			n = ((n == -1) ? 0 : n * 10) + c - '0';
					} else {
			    			break;
			    		}
				}
				if (i != j) n = -1; // malformed numeric entity
		
				if (n != -1) {
					// output decoded numeric entity character
					output.append((char)n);
					pos = j + 1;
				} else {
					// output non-entity character
					output.append(content.charAt(pos));
					pos++;
				}
			} else {
				// output non-entity character
				output.append(content.charAt(pos));
				pos++;
			}
		}
		output.append(content.substring(pos, content.length()));
		return output.toString();
	}



}
