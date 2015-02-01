package HardCore;

import java.sql.*;
import java.util.HashMap;

public class File {



	public static String filenameextension_list(DB db) {
		return Common.csv(db, "fileformats", "filenameextension", "filenameextension");
	}



}
