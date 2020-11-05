
import com.CodeBlock;
import com.CompilerFrame;

import ast.ASTNode;
import parser.Parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Compiler {
	
	/** Main entry point. */
	public static void main(String args[]) {
		Parser parser = new Parser(System.in);
		ASTNode exp;

		try {
			
			CodeBlock c = new CodeBlock();
			exp = parser.Start();
			
			ProcessBuilder pb = new ProcessBuilder();
			pb.command("/bin/bash", "-c", "cd src/jasmin && rm frame_* && rm *.class"); // delete old frame/class files
			Process process = pb.start();
			process.waitFor();
			
			exp.compile(c, new CompilerFrame());
			c.finish();

			pb = new ProcessBuilder();
			pb.command("/bin/bash", "-c", "cd src/jasmin && java -jar jasmin.jar *.j && java main"); 
			process = pb.start();

			process.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line, res = null;
			while ((line = reader.readLine()) != null)
				res = line;
			System.out.println("Result > " + res);

		} catch (Exception e) {
			System.out.println("Syntax Error!" + e.toString());
			parser.ReInit(System.in);
		}
	}

}
