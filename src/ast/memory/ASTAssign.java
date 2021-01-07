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

public class ASTAssign implements ASTNode {

	private ASTNode lhs, rhs;
	private IType lhsType;
	
	public ASTAssign(ASTNode lhs, ASTNode rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}
	
	@Override
	public IValue eval(Environment<IValue> env) {		
		IValue value = lhs.eval(env);
		VCell cell = (VCell) value;
		IValue newValue = rhs.eval(env);
		cell.set(newValue);
		return newValue;
	}

	@Override
	public void compile(CodeBlock c, Environment<Coordinates> env) throws IOException {
		String type;
		if (lhsType.toString().equals("ref int"))
			type = "ref_int";
		else
			type = "ref_class";

		String type2 = "I";
		if (type.equals("ref_class"))
			type2 = "Ljava/lang/Object;";

		lhs.compile(c, env);
		c.emitF("checkcast " + type);
		c.emitF("dup");

		rhs.compile(c, env);
		c.emitF("putfield " + type + "/v " + type2);
		c.emitF("getfield " + type + "/v " + type2);
	}

	@Override
	public IType typecheck(Environment<IType> env) {
		lhsType = lhs.typecheck(env);
		if(!(lhsType instanceof TRef))
			throw new TypeErrorException(":=", "ref");
		TRef ref = (TRef) lhsType;
		IType type = ref.getRefType();
		IType rhsType = rhs.typecheck(env);
		if(type.toString().equals(rhsType.toString()))
			return type;
		throw new TypeErrorException(":=", type.toString());
	}

}
