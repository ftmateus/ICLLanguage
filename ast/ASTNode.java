package ast;

import com.CodeBlock;
import com.Environment;

public interface ASTNode {

    int eval(Environment env);
    
    void compile(CodeBlock c);
	
}

