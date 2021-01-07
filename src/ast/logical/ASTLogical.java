package ast.logical;

import java.io.IOException;

import ast.ASTNode;
import env.CodeBlock;
import env.Coordinates;
import env.Environment;
import exceptions.TypeErrorException;
import types.TBool;
import types.IType;

abstract class ASTLogical implements ASTNode {

	protected ASTNode rhs, lhs;
	private String compileOp, symbol;

	public ASTLogical(ASTNode lhs, ASTNode rhs, String compileOp, String symbol) {
		this.rhs = rhs;
		this.lhs = lhs;
		this.compileOp = compileOp;
		this.symbol = symbol;
	}
	
    @Override
	public void compile(CodeBlock c, Environment<Coordinates> cf) throws IOException {
		lhs.compile(c, cf);	
		rhs.compile(c, cf);	
		c.emitF(compileOp);	
    }
	
	@Override
	public IType typecheck(Environment<IType> env) {
		IType t1 = lhs.typecheck(env);
		IType t2 = rhs.typecheck(env);
		
		if(t1 instanceof TBool && t2 instanceof TBool ) 
			return TBool.INSTANCE;
		else 
			throw new TypeErrorException(symbol, "bool");
	}
	
}
