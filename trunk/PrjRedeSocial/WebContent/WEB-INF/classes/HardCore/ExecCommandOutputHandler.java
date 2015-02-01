package HardCore;

import java.util.*;
import java.io.*;

public class ExecCommandOutputHandler extends Thread {
	InputStream is;
	String type;
	StringBuffer content;
	int nextLineStart;

	ExecCommandOutputHandler(InputStream is, String type) {
		this.is = is;
		this.type = type;
		this.content = new StringBuffer();
		this.nextLineStart = 0;
	}

	public void run() {
		try {
			int ch;
			while ((ch = is.read()) != -1) {
				content.append((char)ch);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();  
		}
	}

	public String readLine() {
		if (nextLineStart >= content.length()) {
			return null;
		} else {
			int thisLineStart = nextLineStart;
			int thisLineLength = content.substring(thisLineStart).indexOf("\n");
			if (thisLineLength >= 0) {
				nextLineStart = thisLineStart + thisLineLength + 1;
			} else {
				nextLineStart = content.length();
			}
			return content.substring(thisLineStart, nextLineStart);
		}
	}
}
