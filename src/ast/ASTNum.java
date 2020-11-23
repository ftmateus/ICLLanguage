package ast;

import java.io.IOException;

import env.CodeBlock;
import env.Coordinates;
import env.Environment;

public class ASTNum implements ASTNode {

	int val;

	public ASTNum(int n) {
		val = n;
	}
	
	public int eval(Environment<Integer> env) {
		return val;
	}

	@Override
	public void compile(CodeBlock c, Environment<Coordinates> cf) throws IOException {
		c.emit("sipush " + val);
	}
	
}

