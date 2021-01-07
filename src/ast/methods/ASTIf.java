package ast.methods;

import java.io.IOException;

import ast.ASTNode;
import env.CodeBlock;
import env.Coordinates;
import env.Environment;
import exceptions.TypeErrorException;
import types.IType;
import types.TBool;
import types.TInt;
import values.IValue;
import values.VBool;

public class ASTIf implements ASTNode {

	private final ASTNode cond;
	private final ASTNode then_node;
	private final ASTNode else_node;

	public ASTIf(ASTNode cond, ASTNode then_node, ASTNode else_node) {
		this.cond = cond;
		this.then_node = then_node;
		this.else_node = else_node;
	}

	@Override
	public IValue eval(Environment<IValue> env) {
		Object v1 = cond.eval(env);
		VBool bool = (VBool) v1;
		if (bool.get())
			return then_node.eval(env);
		else
			return else_node.eval(env);
	}

	@Override
	public void compile(CodeBlock c, Environment<Coordinates> env) throws IOException {
		String l1 = c.getNewLabel();
		String l2 = c.getNewLabel();
		cond.compile(c, env);
		c.emitF("ifeq " + l1);
		then_node.compile(c, env);
		c.emitF("goto " + l2);
		c.emitO(l1 + ":");
		else_node.compile(c, env);
		c.emitO(l2 + ":");
	}
	
	@Override
	public IType typecheck(Environment<IType> env) {
		IType c = cond.typecheck(env);
		if(!(c instanceof TBool))
			throw new TypeErrorException("if","bool");
		
		IType t = then_node.typecheck(env);
		IType e = else_node.typecheck(env);
		if(t instanceof TInt && e instanceof TInt ) 
			return TInt.INSTANCE;
		else if(t instanceof TBool && e instanceof TBool)
			return TBool.INSTANCE;
		else 
			throw new TypeErrorException("then...else...", "int");
	}
    
}
