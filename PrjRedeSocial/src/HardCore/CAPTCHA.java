package HardCore;

import java.sql.*;
import java.util.HashMap;
import java.util.regex.*;
import javax.servlet.ServletContext;

public class CAPTCHA {
	static public boolean DEBUG = false;
	public boolean _DEBUG_ = false;
	public static String RECAPTCHA_API_SERVER = "http://api.recaptcha.net";
	public static String RECAPTCHA_API_SECURE_SERVER = "https://api-secure.recaptcha.net";
	public static String RECAPTCHA_VERIFY_SERVER = "http://api-verify.recaptcha.net";

	public String error = "";

	private Text text = null;



	public CAPTCHA(Text mytext) {
		if (mytext != null) text = mytext;
		_DEBUG_ = DEBUG;
	}



	public static String html(Session session, ServletContext servletcontext, Request request, Configuration config, DB db) {
		if (config.get(db, "captcha").equals("recaptcha")) {
			return recaptcha(config.get(db, "captcha_public_key"), "", false, config.get(db, "captcha_text"));
		} else if (config.get(db, "captcha").equals("characters")) {
			return characters(config.get(db, "captcha_characters"), session, config.get(db, "captcha_text"));
		} else if (config.get(db, "captcha").equals("words")) {
			return words(config.get(db, "captcha_words"), session, config.get(db, "captcha_text"));
		} else {
			return "";
		}
	}



	public boolean valid(Session session, ServletContext servletcontext, Request request, Configuration config, DB db) {
		return (validate(session, servletcontext, request, null, config, db).equals(""));
	}
	public boolean valid(Session session, ServletContext servletcontext, Request request, Fileupload filepost, Configuration config, DB db) {
		return (validate(session, servletcontext, request, filepost, config, db).equals(""));
	}
	public String validate(Session session, ServletContext servletcontext, Request request, Configuration config, DB db) {
		return (validate(session, servletcontext, request, null, config, db));
	}
	public String validate(Session session, ServletContext servletcontext, Request request, Fileupload filepost, Configuration config, DB db) {
		if (config.get(db, "captcha").equals("recaptcha")) {
			if ((filepost != null) && (! filepost.getParameter("recaptcha_challenge_field").equals("")) && (! filepost.getParameter("recaptcha_response_field").equals(""))) {
				if (_DEBUG_) { System.out.println("AsbruWCM/CAPTCHA.validate:challenge-response:"+filepost.getParameter("recaptcha_challenge_field")+":"+filepost.getParameter("recaptcha_response_field")); }
				error = validateRecaptcha(config.get(db, "captcha_private_key"), request.getRemoteAddr(), filepost.getParameter("recaptcha_challenge_field"), filepost.getParameter("recaptcha_response_field"), session);
			} else {
				if (_DEBUG_) { System.out.println("AsbruWCM/CAPTCHA.validate:challenge-response:"+request.getParameter("recaptcha_challenge_field")+":"+request.getParameter("recaptcha_response_field")); }
				error = validateRecaptcha(config.get(db, "captcha_private_key"), request.getRemoteAddr(), request.getParameter("recaptcha_challenge_field"), request.getParameter("recaptcha_response_field"), session);
			}
			if (_DEBUG_) { System.out.println("AsbruWCM/CAPTCHA.validate:reply:"+error); }
			if (! error.equals("")) {
				error = text.display("config.website.contactforms.captcha.recaptcha.error");
			}
		} else if (config.get(db, "captcha").equals("characters")) {
			if ((filepost != null) && (! filepost.getParameter("captcha").equals(""))) {
				error = validateCharacters(session, filepost.getParameter("captcha"));
			} else {
				error = validateCharacters(session, request.getParameter("captcha"));
			}
			if (! error.equals("")) {
				error = text.display("config.website.contactforms.captcha.characters.error");
			}
		} else if (config.get(db, "captcha").equals("words")) {
			if ((filepost != null) && (! filepost.getParameter("captcha").equals(""))) {
				error = validateWords(session, filepost.getParameter("captcha"));
			} else {
				error = validateWords(session, request.getParameter("captcha"));
			}
			if (! error.equals("")) {
				error = text.display("config.website.contactforms.captcha.words.error");
			}
		} else {
			error = "";
		}
		return error;
	}



	public static String recaptcha(String pubkey) {
		return recaptcha(pubkey, "", false, "");
	}
	public static String recaptcha(String pubkey, String error) {
		return recaptcha(pubkey, error, false, "");
	}
	public static String recaptcha(String pubkey, String error, boolean use_ssl) {
		return recaptcha(pubkey, error, use_ssl, "");
	}
	public static String recaptcha(String pubkey, String error, boolean use_ssl, String text) {
		if ((pubkey == null) || (pubkey.equals(""))) return "To use reCAPTCHA you must get an API key from <a href='http://recaptcha.net/api/getkey'>http://recaptcha.net/api/getkey</a>";
		String server = "";
		if (use_ssl) {
			server = RECAPTCHA_API_SECURE_SERVER;
		} else {
			server = RECAPTCHA_API_SERVER;
		}
		String errorpart = "";
		if (! error.equals("")) {
		   errorpart = "&amp;error=" + error;
		}
		String output = "";
	  	output += "\t" + text + "\r\n";
		output += "<script type=\"text/javascript\" src=\"" + server + "/challenge?k=" + pubkey + errorpart + "\"></script>" + "\r\n";
		output += "<noscript>" + "\r\n";
	  	output += "\t" + text + "\r\n";
	  	output += "\t<iframe src=\"" + server + "/noscript?k=" + pubkey + errorpart + "\" height=\"300\" width=\"500\" frameborder=\"0\"></iframe><br/>" + "\r\n";
	  	output += "\t<textarea name=\"recaptcha_challenge_field\" rows=\"3\" cols=\"40\"></textarea>" + "\r\n";
	  	output += "\t<input type=\"hidden\" name=\"recaptcha_response_field\" value=\"manual_challenge\"/>" + "\r\n";
		output += "</noscript>";
		return output;
	}



	public boolean validRecaptcha(String privkey, String remoteip, String challenge, String response) {
		return (validateRecaptcha(privkey, remoteip, challenge, response, null).equals(""));
	}
	public boolean validRecaptcha(String privkey, String remoteip, String challenge, String response, Session session) {
		return (validateRecaptcha(privkey, remoteip, challenge, response, session).equals(""));
	}
	public String validateRecaptcha(String privkey, String remoteip, String challenge, String response) {
		return validateRecaptcha(privkey, remoteip, challenge, response, null);
	}
	public String validateRecaptcha(String privkey, String remoteip, String challenge, String response, Session session) {
		if ((privkey == null) || (privkey.equals(""))) {
			if (_DEBUG_) { System.out.println("AsbruWCM/CAPTCHA.validateRecaptcha:error:key:"+privkey); }
			return "To use reCAPTCHA you must get an API key from <a href='http://recaptcha.net/api/getkey'>http://recaptcha.net/api/getkey</a>";
		}
		if ((remoteip == null) || (remoteip.equals(""))) {
			if (_DEBUG_) { System.out.println("AsbruWCM/CAPTCHA.validateRecaptcha:error:remoteip:"+remoteip); }
			return "For security reasons, you must pass the remote ip to reCAPTCHA";
		}
		if ((challenge == null) || (challenge.length() == 0) || (response == null) || (response.length() == 0)) {
			if (_DEBUG_) { System.out.println("AsbruWCM/CAPTCHA.validateRecaptcha:error:challenge-response:"+challenge+":"+response); }
			return "incorrect-captcha-sol";
		}
		String reply = http.post(RECAPTCHA_VERIFY_SERVER, "/verify", "privatekey=" + URLEncoder.encode(privkey) + "&remoteip=" + URLEncoder.encode(remoteip) + "&challenge=" + URLEncoder.encode(challenge) + "&response=" + URLEncoder.encode(response));
		if (reply == null) {
			if (_DEBUG_) { System.out.println("AsbruWCM/CAPTCHA.validateRecaptcha:null:"+RECAPTCHA_VERIFY_SERVER+"/verify"+":"+"privatekey=" + URLEncoder.encode(privkey) + "&remoteip=" + URLEncoder.encode(remoteip) + "&challenge=" + URLEncoder.encode(challenge) + "&response=" + URLEncoder.encode(response)+":"+reply); }
			return "no-reply"; // "Null read from server";
		}
		String[] answers = reply.split("\r?\n");
		if (answers.length < 1) {
			if (_DEBUG_) { System.out.println("AsbruWCM/CAPTCHA.validateRecaptcha:blank:"+RECAPTCHA_VERIFY_SERVER+"/verify"+":"+"privatekey=" + URLEncoder.encode(privkey) + "&remoteip=" + URLEncoder.encode(remoteip) + "&challenge=" + URLEncoder.encode(challenge) + "&response=" + URLEncoder.encode(response)+":"+reply); }
			return "no-reply"; // "No answer returned from recaptcha: " + message;
		}
		if (answers[0].equals("true")) {
			if (_DEBUG_) { System.out.println("AsbruWCM/CAPTCHA.validateRecaptcha:true:"+RECAPTCHA_VERIFY_SERVER+"/verify"+":"+"privatekey=" + URLEncoder.encode(privkey) + "&remoteip=" + URLEncoder.encode(remoteip) + "&challenge=" + URLEncoder.encode(challenge) + "&response=" + URLEncoder.encode(response)+":"+reply); }
			return "";
		} else if (answers.length > 1) {
			if (_DEBUG_) { System.out.println("AsbruWCM/CAPTCHA.validateRecaptcha:reply:"+RECAPTCHA_VERIFY_SERVER+"/verify"+":"+"privatekey=" + URLEncoder.encode(privkey) + "&remoteip=" + URLEncoder.encode(remoteip) + "&challenge=" + URLEncoder.encode(challenge) + "&response=" + URLEncoder.encode(response)+":"+reply); }
			return answers[1];
		} else {
			if (_DEBUG_) { System.out.println("AsbruWCM/CAPTCHA.validateRecaptcha:error:"+RECAPTCHA_VERIFY_SERVER+"/verify"+":"+"privatekey=" + URLEncoder.encode(privkey) + "&remoteip=" + URLEncoder.encode(remoteip) + "&challenge=" + URLEncoder.encode(challenge) + "&response=" + URLEncoder.encode(response)+":"+reply); }
			return "recaptcha4j-missing-error-message";
		}
	}



	public static String characters(String characterlist, Session session) {
		return characters(characterlist, session, 6, "");
	}
	public static String characters(String characterlist, Session session, String text) {
		return characters(characterlist, session, 6, text);
	}
	public static String characters(String characterlist, Session session, int count) {
		return characters(characterlist, session, 6, "");
	}
	public static String characters(String characterlist, Session session, int count, String text) {
		String challenge = "";
		for (int i=1; i <= count; i++) {
			challenge += characterlist.charAt((int)(Math.random()*characterlist.length()));
		}
		session.set("captcha_challenge", challenge);
		String output = "<span class=\"captcha\">" + text + "<span class=\"captchachallenge\">" + challenge + "</span> <span class=\"captcharesponse\"><input type=\"text\" size=\"" + count + "\" name=\"captcha\"></span></span>";
		return output;
	}



	public boolean validCharacters(Session session, String response) {
		return (validateCharacters(session, response).equals(""));
	}
	public String validateCharacters(Session session, String response) {
		if ((session.get("captcha_challenge").trim().equals("")) || (response.trim().equals(""))) {
			if (_DEBUG_) { System.out.println("AsbruWCM/CAPTCHA.validateCharacters:blank:"+session.get("captcha_challenge")+":"+response); }
			session.set("captcha_challenge", "");
			return "ERROR";
		} else if (session.get("captcha_challenge").trim().equals(response.trim())) {
			if (_DEBUG_) { System.out.println("AsbruWCM/CAPTCHA.validateCharacters:valid:"+session.get("captcha_challenge")+":"+response); }
			session.set("captcha_challenge", "");
			return "";
		} else {
			if (_DEBUG_) { System.out.println("AsbruWCM/CAPTCHA.validateCharacters:invalid:"+session.get("captcha_challenge")+":"+response); }
			session.set("captcha_challenge", "");
			return "ERROR";
		}
	}



	public static String words(String wordlist, Session session) {
		return words(wordlist, session, 2, "");
	}
	public static String words(String wordlist, Session session, String text) {
		return words(wordlist, session, 2, text);
	}
	public static String words(String wordlist, Session session, int count) {
		return words(wordlist, session, count, "");
	}
	public static String words(String wordlist, Session session, int count, String text) {
		String[] words = wordlist.replaceAll("\r", " ").replaceAll("\n", " ").replaceAll("\\.", " ").replaceAll(",", " ").replaceAll("  ", " ").split(" ");
		String challenge = "";
		for (int i=1; i <= count; i++) {
			if (! challenge.equals("")) challenge += " ";
			challenge += words[(int)(Math.random()*words.length)];
		}
		session.set("captcha_challenge", challenge);
		String output = "<span class=\"captcha\">" + text + "<span class=\"captchachallenge\">" + challenge + "</span> <span class=\"captcharesponse\"><input type=\"text\" size=\"" + (count*10) + "\" name=\"captcha\"></span></span>";
		return output;
	}



	public boolean validWords(Session session, String response) {
		return (validateWords(session, response).equals(""));
	}
	public String validateWords(Session session, String response) {
		if ((session.get("captcha_challenge").trim().equals("")) || (response.trim().equals(""))) {
			if (_DEBUG_) { System.out.println("AsbruWCM/CAPTCHA.validateWords:blank:"+session.get("captcha_challenge")+":"+response); }
			session.set("captcha_challenge", "");
			return "ERROR";
		} else if (session.get("captcha_challenge").trim().equals(response.trim())) {
			if (_DEBUG_) { System.out.println("AsbruWCM/CAPTCHA.validateCharacters:valid:"+session.get("captcha_challenge")+":"+response); }
			session.set("captcha_challenge", "");
			return "";
		} else {
			if (_DEBUG_) { System.out.println("AsbruWCM/CAPTCHA.validateCharacters:invalid:"+session.get("captcha_challenge")+":"+response); }
			session.set("captcha_challenge", "");
			return "ERROR";
		}
	}



}
