package ast;

import java.io.IOException;

import env.CodeBlock;
import env.Coordinates;
import env.Environment;

public interface ASTNode {

    int eval(Environment<Integer> env);
    
    void compile(CodeBlock c, Environment<Coordinates> cf) throws IOException;
	
}

