import com.CodeBlock;
import com.CompilerFrame;
import com.SystemUtils;

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

	public static final String BIN_FILES_FOLDER = "comp"+SystemUtils.SLASH;

	public static void main(String args[]) throws FileNotFoundException {
		InputStream stream = args.length >= 1 ? new FileInputStream(args[0]) : System.in;
		Parser parser = new Parser(stream);
		ASTNode exp;

		String jasmin_path = "src" + SystemUtils.SLASH + "jasmin" + SystemUtils.SLASH;

		try {
			CodeBlock c = new CodeBlock(jasmin_path);

			exp = parser.Start();

			SystemUtils.debug.println(SystemUtils.OS);
			SystemUtils.debug.println(SystemUtils.SLASH);
			
			String deleteCMD = SystemUtils.OS.contains("Windows") ? "del" : "rm";

			SystemUtils.runProcessAndWait(deleteCMD + " " + jasmin_path + "frame_*", true);
			SystemUtils.runProcessAndWait(deleteCMD + " " + BIN_FILES_FOLDER+ "*.class", true);

			exp.compile(c, new CompilerFrame());
			c.finish();

			SystemUtils.runProcessAndWait("java -jar " + jasmin_path + "jasmin.jar -d " + BIN_FILES_FOLDER + " " + 
		 			jasmin_path + "/*.j", true);

			Process process = SystemUtils.runProcessAndWait("java -cp comp main", false);

			String res = SystemUtils.getProcessOutput(process);
			System.out.println("Result > " + res);

		} catch (Exception e) {
			System.err.println("Syntax Error!" + e.toString());
		}
	}

	// public static boolean isEclipse() {
	//     return System.getProperty("java.class.path").toLowerCase().contains("eclipse");
	// }
}
