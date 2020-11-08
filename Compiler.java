
import com.CodeBlock;
import com.CompilerFrame;

import ast.ASTNode;
import parser.Parser;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.ProcessBuilder.Redirect;
import java.util.Arrays;

public class Compiler {

	/*
	 * Default settings for linux
	 */
	public static String terminal = "/bin/bash";
	public static String terminal_options = "-c";

	public static final String BIN_FILES_FOLDER = "comp";

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
	/**
	 * Main entry point.
	 * 
	 * @throws FileNotFoundException
	 */
	public static void main(String args[]) throws FileNotFoundException {
		InputStream stream = args.length >= 1 ? new FileInputStream(args[0]) : System.in;
		Parser parser = new Parser(stream);
		ASTNode exp;

		String OS = System.getProperty("os.name");
		if (OS.contains("Windows")) 
		{
			OS = "Windows";
			terminal = "cmd.exe";
			terminal_options = "/C";
		}
		
		String jasmin_path = isEclipse() ? "src/jasmin/" : "jasmin/";

		try {
			CodeBlock c = new CodeBlock(jasmin_path);

			exp = parser.Start();

			String deleteCMD = OS.equals("Windows") ? "del" : "rm";
			runProcessAndWait(deleteCMD + " " + jasmin_path + "frame_*.j", true);
			// && rm " + jasmin_source +" *.class"); // delete old

			exp.compile(c, new CompilerFrame());
			c.finish();

		 	runProcessAndWait("java -jar " + jasmin_path + "jasmin.jar -d " + BIN_FILES_FOLDER + " jasmin/*.j", true);

			Process process = runProcessAndWait("java -cp comp main", false);

			String res = getProcessOutput(process);
			System.out.println("Result > " + res);

		} catch (Exception e) {
			System.err.println("Syntax Error!" + e.toString());
			parser.ReInit(stream);
		}
	}

	/**
	 * Detects if this program is running on Eclipse IDE.
	 * @return
	 */
	public static boolean isEclipse() {
		/**@Francisco 
		 * Just a workaround. If the path has a folder eclipse unrelated to eclipse IDE, this will not work...
		 * */
	    return System.getProperty("java.class.path").toLowerCase().contains("eclipse");
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
        // https://stackoverflow.com/questions/19102989/java-is-there-a-way-to-run-a-system-command-and-print-the-output-during-executi
        try
        {
			ProcessBuilder pb = new ProcessBuilder(bCMD);
			
			if(inheritIO) pb.redirectOutput(Redirect.appendTo(new File("Debug.txt")));
			//pb.inheritIO();
            p = pb.start();
            p.waitFor();
        } catch(Exception ex) {
            System.err.println("Failed attempt to run process " + cmd +  " : "+ex);
		}
		return p;
	}
}
