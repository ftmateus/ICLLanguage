import com.CodeBlock;
import com.CompilerFrame;
import com.SystemUtils;

import ast.ASTNode;
import exceptions.SyntaxException;
import parser.ParseException;
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
import static com.SystemUtils.OS_SLASH;
import static com.SystemUtils.OS_NAME;
import static com.SystemUtils.debug;
import static com.JasminUtils.JASMIN_PATH;

public class Compiler {

	public static final String BIN_FILES_FOLDER = "comp"+ OS_SLASH;

	public static void main(String args[]) throws FileNotFoundException {
		InputStream stream = args.length >= 1 ? new FileInputStream(args[0]) : System.in;
		Parser parser = new Parser(stream);
		ASTNode exp;

		try {
			CodeBlock c = new CodeBlock(JASMIN_PATH);

			exp = parser.Start();

			debug.println(OS_NAME);
			debug.println(OS_SLASH);
			
			String deleteCMD = OS_NAME.contains("Windows") ? "del" : "rm";

			runProcessAndWait(deleteCMD + " " + JASMIN_PATH + "frame_*", true);
			runProcessAndWait(deleteCMD + " " + BIN_FILES_FOLDER+ "*.class", true);

			exp.compile(c, new CompilerFrame());
			c.finish();

			runProcessAndWait("java -jar " + JASMIN_PATH + "jasmin.jar -d " + BIN_FILES_FOLDER + " " + 
				JASMIN_PATH + "*.j", true);

			Process process = runProcessAndWait("java -cp comp main", false);

			String res = getProcessOutput(process);
			System.out.println("Result > " + res);

		} catch (SyntaxException | ParseException e) 
		{
			System.err.println("Syntax Error!" + e.toString());
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
