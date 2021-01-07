package ast.arithmetic;

import java.io.IOException;

import ast.ASTNode;
import env.CodeBlock;
import env.Coordinates;
import env.Environment;
import exceptions.TypeErrorException;
import types.IType;
import types.TInt;

abstract class ASTArithmetic implements ASTNode {

	protected ASTNode rhs, lhs;
	private String compileOp, symbol;

	public ASTArithmetic(ASTNode lhs, ASTNode rhs, String compileOp, String symbol) {
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
		
		if(t1 instanceof TInt && t2 instanceof TInt ) 
			return TInt.INSTANCE;
		else 
			throw new TypeErrorException(symbol, "int");
	}

}
