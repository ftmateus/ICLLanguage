package main;

import env.CodeBlock;
import env.Coordinates;
import env.Environment;

import ast.ASTNode;
import exceptions.SyntaxException;
import parser.ParseException;
import parser.Parser;

import static utils.JasminUtils.JASMIN_PATH;
import static utils.SystemUtils.OS_NAME;
import static utils.SystemUtils.OS_SLASH;
import static utils.SystemUtils.debug;
import static utils.SystemUtils.getProcessOutput;
import static utils.SystemUtils.runProcessAndWait;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Compiler {

	public static final String BIN_FILES_FOLDER = "bin"+ OS_SLASH + "jasmin" + OS_SLASH;
	
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

			exp.compile(c, new Environment<Coordinates>());
			c.finish();

			runProcessAndWait("java -jar " + JASMIN_PATH + "jasmin.jar -d " + BIN_FILES_FOLDER + " " + 
				JASMIN_PATH + "*.j", true);

			Process process = runProcessAndWait("java -cp "  + BIN_FILES_FOLDER + " main", false);

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
