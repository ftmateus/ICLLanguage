package ast.arithmetic;

import java.io.IOException;

import ast.ASTNode;
import env.CodeBlock;
import env.Coordinates;
import env.Environment;
import types.IType;
import types.TInt;
import values.IValue;
import values.VInt;

public class ASTNum implements ASTNode {

	private int val;

	public ASTNum(int n) {
		val = n;
	}
	
	public IValue eval(Environment<IValue> env) {
		return new VInt(val);
	}

	@Override
	public void compile(CodeBlock c, Environment<Coordinates> cf) throws IOException {
		if(val == -1)
			c.emitF("iconst_m1");
		else {
			if (val <= 5)
				c.emitF("iconst_" + val);
			else if(val <= Short.MAX_VALUE)
				c.emitF("sipush " + val);
			else c.emitF("ldc " + val);
		}
	}

	@Override
	public IType typecheck(Environment<IType> env) {
		return TInt.INSTANCE;
	}
	
}

