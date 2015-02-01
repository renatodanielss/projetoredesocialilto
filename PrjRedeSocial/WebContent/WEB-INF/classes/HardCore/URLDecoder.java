package HardCore;

import java.io.*;
import java.net.*;

public class URLDecoder {



	public static String decode(String url) {
		try {
			return java.net.URLDecoder.decode(url, "UTF-8");
		} catch (Exception e) {
			return url;
		}
	}



}
