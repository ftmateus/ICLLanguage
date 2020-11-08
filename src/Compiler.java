import com.CodeBlock;
import com.CompilerFrame;

import ast.ASTNode;
import parser.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.ProcessBuilder.Redirect;

public class Compiler {

	public static String terminal = "/bin/bash";
	public static String terminal_options = "-c";

	public static final String SLASH = System.getProperty("file.separator");

	public static final String BIN_FILES_FOLDER = "comp"+SLASH;

	

	private static final PrintStream debug;

	static 
	{
		PrintStream d;
		try {
			d = new PrintStream(new File("Debug.txt"));
		} catch (FileNotFoundException e) {
			d = null;
		}
		debug = d;
	}

	public static void main(String args[]) throws FileNotFoundException {
		InputStream stream = args.length >= 1 ? new FileInputStream(args[0]) : System.in;
		Parser parser = new Parser(stream);
		ASTNode exp;

		String OS = System.getProperty("os.name");
		if (OS.contains("Windows")) 
		{
			terminal = "cmd.exe";
			terminal_options = "/C";
		}
		
		String jasmin_path = "src" + SLASH + "jasmin" + SLASH;

		try {
			CodeBlock c = new CodeBlock(jasmin_path);

			exp = parser.Start();

			debug.println(OS);
			debug.println(SLASH);
			
			String deleteCMD = OS.contains("Windows") ? "del" : "rm";
			runProcessAndWait(deleteCMD + " " + jasmin_path + "frame_*", true);
			runProcessAndWait(deleteCMD + " " + BIN_FILES_FOLDER+ "*.class", true);

			exp.compile(c, new CompilerFrame());
			c.finish();

		 	runProcessAndWait("java -jar " + jasmin_path + "jasmin.jar -d " + BIN_FILES_FOLDER + " " + 
		 			jasmin_path + "/*.j", true);

			Process process = runProcessAndWait("java -cp comp main", false);

			String res = getProcessOutput(process);
			System.out.println("Result > " + res);

		} catch (Exception e) {
			System.err.println("Syntax Error!" + e.toString());
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
