package HardCore;

import java.util.*;
import java.io.*;

public class ExecCommandInputHandler extends Thread {
	OutputStream os;
	String type;
	String content;

	ExecCommandInputHandler(OutputStream os, String type, String content) {
		this.os = os;
		this.type = type;
		this.content = content;
	}

	public void run() {
		try {
			if (content.length() > 0) {
				os.write(content.getBytes());
			}
			os.close();
		} catch(IOException ioe) {
			ioe.printStackTrace();  
		}
	}
}
