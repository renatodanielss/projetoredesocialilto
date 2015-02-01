package HardCore;

import java.io.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Text {
	private ResourceBundle bundle;
	private String charset = "";



	public Text() {
		init();
	}



	public Text(Request request) {
		init(request);
	}



	public void setCharset(String mycharset) {
		charset = mycharset;
	}



	public void init() {
		bundle = ResourceBundle.getBundle("hardcore");
	}
	public void init(Request request) {
		if (request != null) {
			Locale locale = request.getLocale();
			bundle = ResourceBundle.getBundle("hardcore", locale);
		} else {
			bundle = ResourceBundle.getBundle("hardcore");
		}
	}
	public void init(Locale locale) {
		if (locale != null) {
			bundle = ResourceBundle.getBundle("hardcore", locale);
		} else {
			bundle = ResourceBundle.getBundle("hardcore");
		}
	}
	public void init(String language) {
		if (language.length() >= 4) {
			try {
				bundle = ResourceBundle.getBundle("hardcore", new Locale(language.substring(0,2), language.substring(2,4)));
			} catch (Exception e) {
				try {
					bundle = ResourceBundle.getBundle("hardcore", new Locale(language.substring(0,2)));
				} catch (Exception e2) {
					bundle = ResourceBundle.getBundle("hardcore");
				}
			}
		} else if (language.length() >= 2) {
			try {
				bundle = ResourceBundle.getBundle("hardcore", new Locale(language.substring(0,2)));
			} catch (Exception e) {
				bundle = ResourceBundle.getBundle("hardcore");
			}
		} else {
			bundle = ResourceBundle.getBundle("hardcore");
		}
	}



	public String display(String name) {
		if ((bundle.getString(name) != null) && (! bundle.getString(name).equals(""))) {
			String value = "" + bundle.getString(name);
			value = value.replace("\r", "\\r").replace("\n", "\\n");
			if ((! charset.equals("")) && (! charset.toLowerCase().equals("iso-8859-1"))) {
				try {
					value = new String(value.getBytes("iso-8859-1"), charset);
				} catch (UnsupportedEncodingException e) {
				}
			}
			return value;
		} else {
			return name;
		}
	}



}
