package HardCore;

public class ExecCommand {
	Runtime rt;
	Process proc;
	ExecCommandInputHandler cmdInput;
	ExecCommandOutputHandler cmdOutput;
	ExecCommandOutputHandler cmdError;

	public ExecCommand(String command, String input) {
		try {
			rt = Runtime.getRuntime();
			if (System.getProperty("os.name").startsWith("Windows")) {
				String[] cmd = { "cmd.exe", "/c", command };
				proc = rt.exec(cmd);
			} else {
				String[] cmd = { "/bin/sh", "-c", command };
				proc = rt.exec(cmd);
			}
			cmdError = new ExecCommandOutputHandler(proc.getErrorStream(), "ERROR");            
			cmdOutput = new ExecCommandOutputHandler(proc.getInputStream(), "OUTPUT");
			if (input != null) cmdInput = new ExecCommandInputHandler(proc.getOutputStream(), "INPUT", input);
			cmdError.start();
			cmdOutput.start();
			if (input != null) cmdInput.start();
			int exitVal = proc.waitFor();
		} catch (Throwable t) {
			System.out.println("HardCore/ExecCommand:"+command+":"+input+":"+t);
			t.printStackTrace();
		}
	}

	public String readLine() {
		if (cmdOutput != null) {
			return cmdOutput.readLine();
		} else {
			return null;
		}
	}
}
