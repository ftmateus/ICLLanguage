package ast;

import java.util.List;

import com.Binding;
import com.CodeBlock;
import com.Environment;


public class ASTDef implements ASTNode {

	private List<Binding> assignments;
	private ASTNode body;
	
	public ASTDef(List<Binding> assignments, ASTNode body) {
		this.body = body;
		this.assignments = assignments;
	}
	
	public int eval(Environment e) {
		Environment env = e.beginScope();
		int res;
		
		for(Binding a : assignments) {
			res = a.getExp().eval(env);
			env.assoc(a.getId(), res);
		}
		
		res = body.eval(env);
		e.endScope();
		
		return res;
	}

	@Override
	public void compile(CodeBlock c) {
		// TODO Auto-generated method stub
		
	}


}
