package ast;

import com.CodeBlock;
import com.Environment;

public class ASTTimes implements ASTNode {

	ASTNode lhs, rhs;

	public ASTTimes(ASTNode l, ASTNode r) {
		lhs = l;
		rhs = r;
	}
	
	public int eval(Environment env) {
		int v1 = lhs.eval(env);
		int v2 = rhs.eval(env);
		return v1 * v2;
	}

	@Override
	public void compile(CodeBlock c) {
		lhs.compile(c);	
		rhs.compile(c);	
		c.emit("imul");	
	}

	
}

