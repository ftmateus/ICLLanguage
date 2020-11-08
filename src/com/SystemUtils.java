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
    public static final String OS = System.getProperty("os.name");
    public static final String terminal;
	public static final String terminal_options;
	public static final String SLASH = System.getProperty("file.separator");

	static 
	{
		PrintStream d;
		try {
			d = new PrintStream(new File("Debug.txt"));
		} catch (FileNotFoundException e) {
			d = null;
		}
        debug = d;

		if (OS.contains("Windows")) 
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
		String line, res = null;
		while ((line = reader.readLine()) != null)
			res = line;
		return res;
	}

	public static Process runProcessAndWait(String cmd, boolean inheritIO)
    {
		String[] bCMD = { terminal, terminal_options , cmd};

		Process p = null;
        try
        {
			ProcessBuilder pb = new ProcessBuilder(bCMD);
			pb.redirectErrorStream(true); 
			if(inheritIO) 
				pb.redirectOutput(Redirect.appendTo(new File("Debug.txt")));
            p = pb.start();
            p.waitFor();
        } catch(Exception ex) {
            System.err.println("Failed attempt to run process " + cmd +  " : "+ex);
		}
		return p;
	}
}
