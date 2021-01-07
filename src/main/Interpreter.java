package main;

import env.Environment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import ast.ASTNode;
import exceptions.SyntaxException;
import parser.ParseException;
import parser.Parser;
import types.IType;
import values.IValue;

public class Interpreter {

	public static void main(String args[]) throws FileNotFoundException {
		
    	InputStream stream = args.length >= 1 ? new FileInputStream(args[0]) : System.in;
		Parser parser = new Parser(stream);
    	ASTNode exp;

    	
    	while (true) {
      		try{
      		  	Environment<IValue> env = new Environment<IValue>();
        		exp = parser.Start();
        		IType t = exp.typecheck(new Environment<IType>());
				System.out.println("Output: " + t + " = " + exp.eval(env));
			} catch (SyntaxException | ParseException e) {
				System.out.println("Syntax Error!" + e.toString());
				parser.ReInit(System.in);
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
  	}

}
