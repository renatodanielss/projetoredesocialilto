package HardCore;

import java.io.*;
import javax.servlet.ServletOutputStream;

public class ServletOutputStreamWrapper extends ServletOutputStream {

	private OutputStream stream = null;

	public ServletOutputStreamWrapper() {
		this.stream = new ByteArrayOutputStream();
	}

	public ServletOutputStreamWrapper(OutputStream stream) {
		this.stream = stream;
	}

	@Override
	public void write(int b) throws IOException {
		this.stream.write(b);
	}
}
