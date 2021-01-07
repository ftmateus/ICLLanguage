package ast.relational;


import java.io.IOException;

import ast.ASTNode;
import env.CodeBlock;
import env.Coordinates;
import env.Environment;
import exceptions.TypeErrorException;
import types.TBool;
import types.IType;
import types.TInt;

abstract class ASTRelational implements ASTNode {

	protected ASTNode rhs, lhs;
	private String compileOp, symbol;

	public ASTRelational(ASTNode lhs, ASTNode rhs, String compileOp, String symbol) {
		this.rhs = rhs;
		this.lhs = lhs;
		this.compileOp = compileOp;
		this.symbol = symbol;
	}
	
	@Override
    public void compile(CodeBlock c, Environment<Coordinates> env) throws IOException {
		String ts = c.getNewLabel();
        String fs = c.getNewLabel();
		lhs.compile(c, env);	
		rhs.compile(c, env);	
        c.emitF("isub");
        c.emitF(compileOp + " " + ts);
        c.emitF("sipush 0");
        c.emitF("goto " + fs);
        c.emitO(ts + ":");
        c.emitF("sipush 1");
        c.emitO(fs + ":");
    }
	
	@Override
	public IType typecheck(Environment<IType> env) {
		IType t1 = lhs.typecheck(env);
		IType t2 = rhs.typecheck(env);
		
		if(t1 instanceof TInt && t2 instanceof TInt ) 
			return TBool.INSTANCE;
		else if(t1 instanceof TBool && t2 instanceof TBool)
			return TBool.INSTANCE;
		else 
			throw new TypeErrorException(symbol, "int");
	}

}
