package ast;

import java.io.IOException;

import com.CodeBlock;
import com.CompilerFrame;
import com.Environment;

public interface ASTNode {

    int eval(Environment env);
    
    void compile(CodeBlock c, CompilerFrame cf) throws IOException;
	
}

