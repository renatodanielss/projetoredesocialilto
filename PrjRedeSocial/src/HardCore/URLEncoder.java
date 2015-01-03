package HardCore;

import java.io.*;
import java.net.*;

public class URLEncoder {



	public static String encode(String url) {
		try {
			return java.net.URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return url;
		}
	}



}
