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

import static com.SystemUtils.getProcessOutput;
import static com.SystemUtils.runProcessAndWait;
import static com.SystemUtils.SLASH;
import static com.SystemUtils.OS;

public class Compiler {

	public static final String BIN_FILES_FOLDER = "comp"+SystemUtils.SLASH;

	public static void main(String args[]) throws FileNotFoundException {
		InputStream stream = args.length >= 1 ? new FileInputStream(args[0]) : System.in;
		Parser parser = new Parser(stream);
		ASTNode exp;

		String jasmin_path = "src" + SLASH + "jasmin" + SLASH;

		try {
			CodeBlock c = new CodeBlock(jasmin_path);

			exp = parser.Start();

			SystemUtils.debug.println(OS);
			SystemUtils.debug.println(SLASH);
			
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

	// public static boolean isEclipse() {
	//     return System.getProperty("java.class.path").toLowerCase().contains("eclipse");
	// }
}
