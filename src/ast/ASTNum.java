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
		switch (val)
		{
			case 0: case 1: case 2: case 3: case 4: case 5:
				c.emit("iconst_" + val); break;
			case -1:
				c.emit("iconst_m1"); break;	
			default:
				c.emit("sipush " + val);
		}		
	}
	
}

