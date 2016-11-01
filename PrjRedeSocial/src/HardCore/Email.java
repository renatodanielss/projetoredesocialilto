package HardCore;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletContext;

public class Email {



	public static boolean send_email(Text text, HashMap<String,String> requestForm, String subject, String body, String plainbody, String from, String to, String cc, String bcc, String stylesheet, String style, String servername, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
//System.out.println("DEBUG:Email.send_email:"+":::from="+from+":::to="+to+":::cc="+cc+":::bcc="+bcc+":::subject="+subject+":::body="+body+":::plainbody="+plainbody);
		boolean htmlbody;
		String header = "";
		if (! body.equals("")) {
			htmlbody = true;
			header = "MIME-Version: 1.0\r\n";
			if ((myconfig != null) && (! myconfig.get(db, "email_charset").equals(""))) {
				header += "Content-type: text/html; charset=\"" + myconfig.get(db, "email_charset") + "\"\r\n";
			} else if ((myconfig != null) && (! myconfig.get(db, "charset").equals(""))) {
				header += "Content-type: text/html; charset=\"" + myconfig.get(db, "charset") + "\"\r\n";
			} else {
				header += "Content-type: text/html" + "\r\n";
			}
		} else if ((requestForm.get("template") != null) && (! requestForm.get("template").equals(""))) {
			htmlbody = true;
			body = "";
			header = "MIME-Version: 1.0\r\n";
			if ((myconfig != null) && (! myconfig.get(db, "email_charset").equals(""))) {
				header += "Content-type: text/html; charset=\"" + myconfig.get(db, "email_charset") + "\"\r\n";
			} else if ((myconfig != null) && (! myconfig.get(db, "charset").equals(""))) {
				header += "Content-type: text/html; charset=\"" + myconfig.get(db, "charset") + "\"\r\n";
			} else {
				header += "Content-type: text/html" + "\r\n";
			}
			Page page = new Page(text);
			page.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "" + requestForm.get("template"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			if (! page.getUser()) {
				if ((myconfig != null) && (myconfig.get(db, "accessrestrictions").equals("or"))) {
					if (page.getUsersGroup().equals("*") || page.getUsersType().equals("*")) {
						RequireUser.User(text, mysession.get("username"), myrequest, myresponse, mysession, db);
					} else if ((! page.getUsersGroup().equals("")) && (! page.getUsersType().equals(""))) {
						RequireUser.UsergroupOrUsertype(text, page.getUsersGroup(), mysession.get("usergroup"), mysession.get("usergroups"), page.getUsersType(), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, mysession, db);
					} else if (! page.getUsersGroup().equals("")) {
						RequireUser.Usergroup(text, page.getUsersGroup(), mysession.get("usergroup"), mysession.get("usergroups"), myrequest, myresponse, mysession, db);
					} else if (! page.getUsersType().equals("")) {
						RequireUser.Usertype(text, page.getUsersType(), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, mysession, db);
					}
				} else {
					if (page.getUsersType().equals("*")) {
						RequireUser.User(text, mysession.get("username"), myrequest, myresponse, mysession, db);
					} else if (! page.getUsersType().equals("")) {
						RequireUser.Usertype(text, page.getUsersType(), mysession.get("usertype"), mysession.get("usertypes"), myrequest, myresponse, mysession, db);
					}
					if (page.getUsersGroup().equals("*")) {
						RequireUser.User(text, mysession.get("username"), myrequest, myresponse, mysession, db);
					} else if (! page.getUsersGroup().equals("")) {
						RequireUser.Usergroup(text, page.getUsersGroup(), mysession.get("usergroup"), mysession.get("usergroups"), myrequest, myresponse, mysession, db);
					}
				}
			}
			body = page.getContent();
		} else {
			htmlbody = false;
			body = plainbody;
		}
		if (body.equals("")) {
			header = "";
			body = "SUBJECT: @@@subject@@@@@@title@@@" + "\r\n" + "\r\n" +
				"NAME: @@@name@@@" + "\r\n" + "\r\n" +
				"COMPANY: @@@company@@@" + "\r\n" + "\r\n" +
				"ADDRESS: @@@address@@@" + "\r\n" + "\r\n" +
				"PHONE: @@@phone@@@" + "\r\n" + "\r\n" +
				"EMAIL: @@@email@@@" + "\r\n" + "\r\n" +
				"WEBSITE: @@@website@@@" + "\r\n" + "\r\n" +
				"MESSAGE: @@@message@@@@@@content@@@" + "\r\n" + "\r\n";
	
			Iterator requestFormItems = requestForm.keySet().iterator();
			while (requestFormItems.hasNext()) {
				String inputname = "" + requestFormItems.next();
				if ((! inputname.equals("subject")) && (! inputname.equals("title")) && (! inputname.equals("name")) && (! inputname.equals("company")) && (! inputname.equals("address")) && (! inputname.equals("phone")) && (! inputname.equals("email")) && (! inputname.equals("website")) && (! inputname.equals("message")) && (! inputname.equals("content")) && (! inputname.equals("template")) && (! inputname.equals("redirect"))) {
					body += inputname.toUpperCase() + ": @@@" + inputname + "@@@" + "\r\n" + "\r\n";
				}
			}
		}

		Pattern p = Pattern.compile("@@@([^\\.@]+)@@@");
		Matcher m = p.matcher(subject);
		while (m.find()) {
			String inputname = m.group(1);
			String outputvalue = "";
			if (requestForm.get(inputname) != null) outputvalue = "" + requestForm.get(inputname);
			subject = subject.replaceAll("\\Q@@@" + inputname + "@@@\\E", outputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			m.reset(subject);
		}

		p = Pattern.compile("@@@([^\\.@]+)@@@");
		m = p.matcher(body);
		while (m.find()) {
			String inputname = m.group(1);
			String outputvalue = "";
			if (requestForm.get(inputname) != null) outputvalue = "" + requestForm.get(inputname);
			body = body.replaceAll("\\Q@@@" + inputname + "@@@\\E", outputvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			m.reset(body);
		}

		if (from.equals("")) {
			from = "nobody@" + servername;
		}

		from = from.replaceAll("\r", "\n");
		from = from.replaceAll("\n\n", "\n");
		from = from.replaceAll("\n", ",");
		from = from.replaceAll("^[, ]", "");
		from = from.replaceAll("[, ]$", "");
		header += "From: " + from + "\r\n";
		to = to.replaceAll("\r", "\n");
		to = to.replaceAll("\n\n", "\n");
		to = to.replaceAll("\n", ",");
		to = to.replaceAll("^[, ]", "");
		to = to.replaceAll("[, ]$", "");
		if (! to.equals("")) {
			header += "To: " + to + "\r\n";
		}
		cc = cc.replaceAll("\r", "\n");
		cc = cc.replaceAll("\n\n", "\n");
		cc = cc.replaceAll("\n", ",");
		cc = cc.replaceAll("^[, ]", "");
		cc = cc.replaceAll("[, ]$", "");
		if (! cc.equals("")) {
			header += "Cc: " + cc + "\r\n";
		}
		bcc = bcc.replaceAll("\r", "\n");
		bcc = bcc.replaceAll("\n\n", "\n");
		bcc = bcc.replaceAll("\n", ",");
		bcc = bcc.replaceAll("^[, ]", "");
		bcc = bcc.replaceAll("[, ]$", "");
		if (! bcc.equals("")) {
			header += "Bcc: " + bcc + "\r\n";
		}

		if (htmlbody) {
			String htmlheader = "";
			if (! stylesheet.equals("")) {
				htmlheader += "<link rel=\"stylesheet\" type=\"text/css\" href=\"" + stylesheet + "\">";
			}
			if (! style.equals("")) {
				htmlheader += "<style>" + style + "</style>";
			}
			body = "<html><head><base href=\"http://" + servername + "\">" + htmlheader + "</head><body>" + "\r\n" + body + "\r\n" + "</body></html>";
		}

		Properties properties = new Properties();
		if ((myconfig != null) && (! myconfig.get(db, "contact_form_mailserver").equals(""))) {
			String[] mailserver = myconfig.get(db, "contact_form_mailserver").split(":");
			if (mailserver.length >= 2) {
				properties.setProperty("mail.smtp.host", "br736.hostgator.com.br");
				properties.setProperty("mail.smtp.port", "587");
			} else {
				properties.setProperty("mail.smtp.host", "br736.hostgator.com.br");
			}
		} else {
			properties.setProperty("mail.smtp.host", "br736.hostgator.com.br");
		}
		
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.ssl.trust", "br736.hostgator.com.br");


		
		
		javax.mail.Session session = javax.mail.Session.getInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("account-activation@iliketoo.com", "ahGhU5gFI13abW978Aim");
				}
		});
		
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			if (! to.equals("")) {
				String[] addresses = to.split(",");
				for (int i=0; i<addresses.length; i++) {
					String address = addresses[i];
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
				}
			}
			if (! cc.equals("")) {
				String[] addresses = cc.split(",");
				for (int i=0; i<addresses.length; i++) {
					String address = addresses[i];
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(address));
				}
			}
			if (! bcc.equals("")) {
				String[] addresses = bcc.split(",");
				for (int i=0; i<addresses.length; i++) {
					String address = addresses[i];
					message.addRecipient(Message.RecipientType.BCC, new InternetAddress(address));
				}
			}
			if ((myconfig != null) && ((! myconfig.get(db, "charset").equals("")) && (! myconfig.get(db, "email_charset").equals("")))) {
				subject = new String(subject.getBytes(myconfig.get(db, "charset")), "iso-8859-1");
				body = new String(body.getBytes(myconfig.get(db, "charset")), "iso-8859-1");
				plainbody = new String(plainbody.getBytes(myconfig.get(db, "charset")), "iso-8859-1");
				subject = MimeUtility.encodeText(new String(subject.getBytes("iso-8859-1"), myconfig.get(db, "charset")), myconfig.get(db, "email_charset"), null);
				body = new String(body.getBytes("iso-8859-1"), myconfig.get(db, "charset"));
				plainbody = new String(plainbody.getBytes("iso-8859-1"), myconfig.get(db, "charset"));
			}
			message.setSubject(subject);
			if (htmlbody && (! plainbody.equals(""))) {
				MimeMultipart multipart = new MimeMultipart("alternative");
				MimeBodyPart bodypartplain = new MimeBodyPart();
				if ((myconfig != null) && (! myconfig.get(db, "email_charset").equals(""))) {
					bodypartplain.setText(plainbody, myconfig.get(db, "email_charset"));
				} else if ((myconfig != null) && (! myconfig.get(db, "charset").equals(""))) {
					bodypartplain.setText(plainbody, myconfig.get(db, "charset"));
				} else {
					bodypartplain.setText(plainbody);
				}
				multipart.addBodyPart(bodypartplain);
				MimeBodyPart bodyparthtml = new MimeBodyPart();
				if ((myconfig != null) && (! myconfig.get(db, "email_charset").equals(""))) {
					bodyparthtml.setContent(body, "text/html; charset=" + myconfig.get(db, "email_charset"));
				} else if ((myconfig != null) && (! myconfig.get(db, "charset").equals(""))) {
					bodyparthtml.setContent(body, "text/html; charset=" + myconfig.get(db, "charset"));
				} else {
					bodyparthtml.setContent(body, "text/html");
				}
				multipart.addBodyPart(bodyparthtml);
				message.setContent(multipart);
			} else if (htmlbody) {
				if ((myconfig != null) && (! myconfig.get(db, "email_charset").equals(""))) {
					message.setContent(body, "text/html; charset=" + myconfig.get(db, "email_charset"));
				} else if ((myconfig != null) && (! myconfig.get(db, "charset").equals(""))) {
					message.setContent(body, "text/html; charset=" + myconfig.get(db, "charset"));
				} else {
					message.setContent(body, "text/html");
				}
			} else {
				if ((myconfig != null) && (! myconfig.get(db, "email_charset").equals(""))) {
					message.setText(body, myconfig.get(db, "email_charset"));
				} else if ((myconfig != null) && (! myconfig.get(db, "charset").equals(""))) {
					message.setText(body, myconfig.get(db, "charset"));
				} else {
					message.setText(body);
				}
			}
//System.out.println("DEBUG:Email.send_email:"+":::from="+from+":::to="+to+":::cc="+cc+":::bcc="+bcc+":::subject="+subject+":::body="+body+":::plainbody="+plainbody);
			Transport.send(message);
		} catch (UnsupportedEncodingException e) {
			System.out.println("HardCore/Email.send_email:"+e);
			System.out.println(header);
			System.out.println(subject);
			System.out.println(body);
			System.out.println(plainbody);
			return false;
		} catch (MessagingException e) {
			System.out.println("HardCore/Email.send_email:"+e);
			System.out.println(header);
			System.out.println(subject);
			System.out.println(body);
			System.out.println(plainbody);
			return false;
		}
		return true;
	}



	public static HashMap<String,String> getForm(Request myrequest) {
		HashMap<String,String> requestForm = new HashMap<String,String>();
		Enumeration parameternames = myrequest.getParameterNames();
		while (parameternames.hasMoreElements()) {
			String parametername = "" + parameternames.nextElement();
			String parametervalue = "";
			String[] parametervalues = myrequest.getParameters(parametername);
			for (int i=0; i<parametervalues.length; i++) {
				if (! parametervalue.equals("")) {
					parametervalue += ",";
				}
				parametervalue += parametervalues[i];
			}
			requestForm.put(parametername, parametervalue);
		}
		return requestForm;
	}



	public static HashMap<String,String> getForm(Request myrequest, Fileupload filepost) {
		HashMap<String,String> requestForm = new HashMap<String,String>();
		Enumeration parameternames = myrequest.getParameterNames();
		while (parameternames.hasMoreElements()) {
			String parametername = "" + parameternames.nextElement();
			String parametervalue = "";
			String[] parametervalues = myrequest.getParameters(parametername);
			for (int i=0; i<parametervalues.length; i++) {
				if (! parametervalue.equals("")) {
					parametervalue += ",";
				}
				parametervalue += parametervalues[i];
			}
			requestForm.put(parametername, parametervalue);
		}
		Iterator filepostparameternames = filepost.getParameterNames();
		while (filepostparameternames.hasNext()) {
			String parametername = "" + filepostparameternames.next();
			String parametervalue = "";
			String[] parametervalues = filepost.getParameters(parametername);
			for (int i=0; i<parametervalues.length; i++) {
				if (! parametervalue.equals("")) {
					parametervalue += ",";
				}
				parametervalue += parametervalues[i];
			}
			requestForm.put(parametername, parametervalue);
		}
		return requestForm;
	}



	public static HashMap<String,String> getForm(Fileupload filepost) {
		HashMap<String,String> requestForm = new HashMap<String,String>();
		Iterator filepostparameternames = filepost.getParameterNames();
		while (filepostparameternames.hasNext()) {
			String parametername = "" + filepostparameternames.next();
			String parametervalue = "";
			String[] parametervalues = filepost.getParameters(parametername);
			for (int i=0; i<parametervalues.length; i++) {
				if (! parametervalue.equals("")) {
					parametervalue += ",";
				}
				parametervalue += parametervalues[i];
			}
			requestForm.put(parametername, parametervalue);
		}
		return requestForm;
	}



}
