package ast;

import com.CodeBlock;
import com.Environment;

public class ASTId implements ASTNode {

	private String id;
	
	public ASTId(String id) {
		this.id = id;
	}
	
	@Override
	public int eval(Environment env) {
		return env.find(id);
	}

	@Override
	public void compile(CodeBlock c) {
		// TODO Auto-generated method stub
		
	}

}
