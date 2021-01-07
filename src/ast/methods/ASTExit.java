package ast.methods;

import java.io.IOException;

import ast.ASTNode;
import env.CodeBlock;
import env.Coordinates;
import env.Environment;
import types.IType;
import values.IValue;

public class ASTExit implements ASTNode 
{
    @Override
    public IValue eval(Environment<IValue> env) {
        System.exit(0);
        return null;
    }

    @Override
    public void compile(CodeBlock c, Environment<Coordinates> env) throws IOException {
        System.exit(0);
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        System.exit(0);
        return null;
    }
    
}
