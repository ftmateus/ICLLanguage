package main;

import env.Environment;

import ast.ASTNode;
import exceptions.SyntaxException;
import parser.ParseException;
import parser.Parser;

public class Interpreter {
	
	public static void main(String args[]) {
		
    	Parser parser = new Parser(System.in);
    	ASTNode exp;

    	while (true) {
      		try{
      		  	Environment<Integer> env = new Environment<Integer>();
        		exp = parser.Start();
        		System.out.println("Result > " + exp.eval(env));
      		}
      		catch (SyntaxException | ParseException e) {
        		System.out.println("Syntax Error!" + e.toString());
        		parser.ReInit(System.in);
			  }
			  catch (Exception e)
			  {
				  e.printStackTrace();
				  break;
			  }
    	}
  	}

}
