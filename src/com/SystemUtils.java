package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.ProcessBuilder.Redirect;

public class SystemUtils 
{

    public static final PrintStream debug;
    public static final String OS_NAME = System.getProperty("os.name");
    public static final String terminal;
	public static final String terminal_options;
	public static final String OS_SLASH = System.getProperty("file.separator");
	public static final String DEBUG_OUT_FILE = "Debug.txt";

	static 
	{
		PrintStream d;
		try {
			d = new PrintStream(new File(DEBUG_OUT_FILE));
		} catch (FileNotFoundException e) {
			d = null;
		}
        debug = d;

		if (OS_NAME.contains("Windows")) 
		{
			terminal = "cmd.exe";
			terminal_options = "/C";
        }
        else
        {
            terminal = "/bin/bash";
            terminal_options = "-c";
        }
	}

    public static String getProcessOutput(Process process) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		StringBuilder res = new StringBuilder();
		while ((line = reader.readLine()) != null)
			res.append(line + "\n");
		return res.toString();
	}

	public static Process runProcessAndWait(String cmd, boolean inheritIO) throws InterruptedException
    {
		debug.println(cmd);
		String[] bCMD = { terminal, terminal_options , cmd};

		Process p = null;
        try
        {
			ProcessBuilder pb = new ProcessBuilder(bCMD);
			pb.redirectErrorStream(true); 
			if(inheritIO) 
				pb.redirectOutput(Redirect.appendTo(new File(DEBUG_OUT_FILE)));
            p = pb.start();
            p.waitFor();
        } catch(Exception ex) {
            System.err.println("Failed attempt to run process " + cmd +  " : "+ex);
		}
		return p;
	}
}
