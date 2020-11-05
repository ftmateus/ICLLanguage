package ast;

import java.io.IOException;

import com.CodeBlock;
import com.CompilerFrame;
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
	public void compile(CodeBlock c, CompilerFrame cf) throws IOException {
		c.emit("sipush " + val);			
	}
	
}

