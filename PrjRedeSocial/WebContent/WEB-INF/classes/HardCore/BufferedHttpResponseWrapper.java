package HardCore;

import java.io.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.ServletOutputStream;

public class BufferedHttpResponseWrapper extends HttpServletResponseWrapper {

	PrintWriter writer = null;
	ByteArrayOutputStream stream = null;
	ServletOutputStream serveletStream = null;

	public BufferedHttpResponseWrapper(HttpServletResponse response) {
		super(response);
		stream = new ByteArrayOutputStream();
		writer = new PrintWriter(stream);
		try {
			writer = new PrintWriter(new OutputStreamWriter(stream, "UTF8"));
		} catch (Exception e) {
		}
	}

	public PrintWriter getWriter() throws IOException {
		return writer;
	}

	// WebLogic may output custom extension content out of place
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		this.serveletStream = new ServletOutputStreamWrapper(stream);
		return this.serveletStream;
	}

	public String getOutput() {
		writer.flush();
		writer.close();
//		if (this.serveletStream != null) {
//			try {
//				this.serveletStream.flush();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		String output = "";
		try {
			output = ""+stream.toString("UTF8");
		} catch (Exception e) {
			output = ""+stream.toString();
		}
		return output;
	}
}
