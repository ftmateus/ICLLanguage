
import com.Environment;

import ast.ASTNode;
import parser.Parser;

public class Interpreter {
	
	public static void main(String args[]) {
		
    	Parser parser = new Parser(System.in);
    	ASTNode exp;

    	while (true) {
      		try{
      		  	Environment env = new Environment();
        		exp = parser.Start();
        		System.out.println("Result > " + exp.eval(env));
      		}
      		catch (Exception e) {
        		System.out.println("Syntax Error!" + e.toString());
        		parser.ReInit(System.in);
      		}
    	}
  	}

}
