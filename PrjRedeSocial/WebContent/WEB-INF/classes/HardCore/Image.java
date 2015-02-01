package HardCore;

import java.sql.*;
import java.util.HashMap;

public class Image {



	public static String filenameextension_list(DB db) {
		return Common.csv(db, "imageformats", "filenameextension", "filenameextension");
	}



}
