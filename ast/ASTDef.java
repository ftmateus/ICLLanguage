package ast;

import java.io.IOException;
import java.util.List;

import com.Binding;
import com.CodeBlock;
import com.CompilerFrame;
import com.Environment;


public class ASTDef implements ASTNode {

	private List<Binding> bindings;
	private ASTNode body;
	
	public ASTDef(List<Binding> bindings, ASTNode body) {
		this.body = body;
		this.bindings = bindings;
	}
	
	public int eval(Environment e) {
		Environment env = e.beginScope();
		int res;
		
		for(Binding a : bindings) {
			res = a.getExp().eval(env);
			env.assoc(a.getId(), res);
		}
		
		res = body.eval(env);
		e.endScope();
		
		return res;
	}

	@Override
	public void compile(CodeBlock c, CompilerFrame cf) throws IOException {
		CompilerFrame cf1 = cf.beginFrame();
		
		c.emit(cf1.createFrame()); //save SL e astore
		
		int total = bindings.size();
		for(int i = 0; i < total; i++) { 
			Binding b = bindings.get(i);
			if(i > 0)
				c.emit("dup");
			b.getExp().compile(c, cf1);
			c.emit(cf1.addVariable(b.getId()));
		}
		c.emit(cf1.finishFrame());

		body.compile(c, cf1);
		c.emit(cf1.reset());

	}



}
