package ast;

import java.io.IOException;

import env.CodeBlock;
import env.Coordinates;
import env.Environment;

public class ASTDiv implements ASTNode {

	ASTNode lhs, rhs;

	public ASTDiv(ASTNode l, ASTNode r) {
		lhs = l;
		rhs = r;
	}
	
	public int eval(Environment<Integer> env) {
		int v1 = lhs.eval(env);
		int v2 = rhs.eval(env);
		return v1 / v2;
	}

	@Override
	public void compile(CodeBlock c, Environment<Coordinates> cf) throws IOException {
		lhs.compile(c, cf);	
		rhs.compile(c, cf);	
		c.emit("idiv");			
	}
	
}

