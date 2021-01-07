package ast;

import java.io.IOException;

import env.CodeBlock;
import env.Coordinates;
import env.Environment;
import types.IType;
import values.IValue;

public interface ASTNode {

    IValue eval(Environment<IValue> env);
    
    void compile(CodeBlock c, Environment<Coordinates> env) throws IOException;

	IType typecheck(Environment<IType> env);
	
}

