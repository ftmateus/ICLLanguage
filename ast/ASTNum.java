package ast;

import com.CodeBlock;
import com.Environment;



public class ASTNum implements ASTNode {

	int val;

	public ASTNum(int n) {
		val = n;
	}
	
	public int eval(Environment env) {
		return val;
	}

	@Override
	public void compile(CodeBlock c) {
		c.emit("sipush " + val);	
	}
	
}

