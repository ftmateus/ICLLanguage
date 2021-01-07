package ast.memory;

import java.io.IOException;

import ast.ASTNode;
import env.CodeBlock;
import env.Coordinates;
import env.Environment;
import exceptions.TypeErrorException;
import types.IType;
import types.TRef;
import values.IValue;
import values.VCell;

public class ASTDeref implements ASTNode {
	
	private ASTNode t;
	private IType type;
	
	public ASTDeref(ASTNode t) {
		this.t = t;
	}
	
	@Override
	public IValue eval(Environment<IValue> env) {
		IValue val = t.eval(env);
		VCell cell = (VCell) val;
		return cell.get();
	}

	@Override
	public void compile(CodeBlock c, Environment<Coordinates> env) throws IOException {
		t.compile(c, env);
		if (type.toString().equals("ref int")) {
			c.emitF("checkcast ref_int");
			c.emitF("getfield ref_int/v I");
		} else {
			c.emitF("checkcast ref_class");
			c.emitF("getfield ref_class/v Ljava/lang/Object;");
		}
	}

	@Override
	public IType typecheck(Environment<IType> env) {
		type = t.typecheck(env);
		if (type instanceof TRef)
			return ((TRef) type).getRefType();
		throw new TypeErrorException("!", "ref");
	}

}
