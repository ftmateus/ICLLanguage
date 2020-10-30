import java.util.List;
import java.util.Stack;

import com.CodeBlock;

import ast.ASTNode;
import parser.Parser;

public class Compiler {
	
	public static void compile(List<String> cmds) {
		Stack<Integer> stack = new Stack<Integer>();
		Integer n1, n2, num;
		String[] exp;
		for(String cmd: cmds) {
			exp = cmd.split(" ");
			switch (exp[0]) {
			case "sipush":
				num = Integer.parseInt(exp[1]);
				stack.push(num);
				break;
			case "iadd":
				n1 = stack.pop();
				n2 = stack.pop();
				stack.push(n1+n2);
				break;
			case "isub":
				n1 = stack.pop();
				n2 = stack.pop();
				stack.push(n1-n2);
				break;
			case "imul":
				n1 = stack.pop();
				n2 = stack.pop();
				stack.push(n1*n2);
				break;
			case "idiv":
				n1 = stack.pop();
				n2 = stack.pop();
				stack.push(n1/n2);
				break;
			default:
				break;
			}
		}
		
		Integer res = stack.pop();
		System.out.println("Result > " + res);
		
	}
	
	/** Main entry point. */
	public static void main(String args[]) {
		Parser parser = new Parser(System.in);
		ASTNode exp;

		while (true) {
			try {
				CodeBlock c = new CodeBlock();
				exp = parser.Start();
				exp.compile(c);
				List<String> commands = c.get();
				compile(commands);
			} catch (Exception e) {
				System.out.println("Syntax Error!" + e.toString());
				parser.ReInit(System.in);
			}
		}
	}

}
