<%@ include file="../../webadmin.jsp" %><%

String output = "";
String referrer = HardCore.URLDecoder.decode(myrequest.getHeader("referer").replaceAll("\\+", " ")).toLowerCase();
String referrer_address = referrer;
String referrer_query = referrer;
if (referrer.indexOf("\\?")>=0) {
	referrer_address = referrer.substring(0,referrer.indexOf("\\?"));
	referrer_query = referrer.substring(referrer.indexOf("\\?"));
}
String[] referrals;
if ((myrequest.getAttribute("extension") != null) && (! myrequest.getAttribute("extension").equals(""))) {
	referrals = (""+myrequest.getAttribute("extension")).split(":");
} else {
	referrals = mysession.get("extension").split(":");
}
for (int i=0; i<referrals.length; i++) {
	boolean referral_match = false;
	String referral_id = "";
	String params[] = referrals[i].split("=");
	if (params.length == 3) {
		String referral_address = params[0];
		String referral_keywords = params[1];
		referral_id = params[2];
		if (referrer_address.indexOf(referral_address.toLowerCase().trim())>=0) {
			referral_match = true;
			String[] mykeywords = referral_keywords.split(",");
			for (int j=0; j<mykeywords.length; j++) {
				if (referrer_query.indexOf(mykeywords[j].toLowerCase().trim())<0) {
					referral_match = false;
					break;
				}
			}
		}
	} else if (params.length == 2) {
		String referral = params[0];
		referral_id = params[1];
		referral_match = true;
		String[] mykeywords = referral.split(",");
		for (int j=0; j<mykeywords.length; j++) {
			if (referrer.indexOf(mykeywords[j].toLowerCase().trim())<0) {
				referral_match = false;
				break;
			}
		}
	}
	if (referral_match) {
		String[] content = referral_id.split("\\.");
		if (content.length == 1) {
			String id = content[0];
			String attribute = "content";
			output += cms.GetContent(id, attribute);
		} else if (content.length == 2) {
			String id = content[0];
			String attribute = content[1];
			output += cms.GetContent(id, attribute);
		}
		break;
	}
}

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>