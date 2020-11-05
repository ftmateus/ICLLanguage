package ast;

import java.io.IOException;

import com.CodeBlock;
import com.CompilerFrame;
import com.Environment;

public class ASTPlus implements ASTNode {

	ASTNode lhs, rhs;

	public ASTPlus(ASTNode l, ASTNode r) {
		lhs = l;
		rhs = r;
	}
	
	public int eval(Environment env) {
		int v1 = lhs.eval(env);
		int v2 = rhs.eval(env);
		return v1 + v2;
	}

	@Override
	public void compile(CodeBlock c, CompilerFrame cf) throws IOException {
		lhs.compile(c, cf);	
		rhs.compile(c, cf);	
		c.emit("iadd");			
	}


}

