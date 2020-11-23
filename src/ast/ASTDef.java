package ast;

import java.io.IOException;
import java.util.List;

import env.Binding;
import env.CodeBlock;
import env.Coordinates;
import env.Environment;


public class ASTDef implements ASTNode {

	private List<Binding> bindings;
	private ASTNode body;
	
	public ASTDef(List<Binding> bindings, ASTNode body) {
		this.body = body;
		this.bindings = bindings;
	}
	
	public int eval(Environment<Integer> e) {
		Environment<Integer> env = e.beginScope();
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
	public void compile(CodeBlock c, Environment<Coordinates> env) throws IOException {
		Environment<Coordinates> newEnv = env.beginScope();
		
		int frameId = c.createFrame(bindings.size());
		Integer ancestorId = null;
		if(env.getDepth() > 0) {
			ancestorId = c.getAncestor();
			c.emit("dup");
			c.emit("aload_1");
			c.emit("putfield frame_" + frameId +"/SL Lframe_" + ancestorId + ";");
		}
		c.emit("dup");
		c.emit("astore_1");
		
		int id = 0;
		for (Binding b : bindings) {
			c.emit("dup");
			b.getExp().compile(c, newEnv);
			c.emit("putfield frame_" + frameId + "/loc_" + id + " I");
			Coordinates cd = new Coordinates(newEnv.getDepth(), id++);
			newEnv.assoc(b.getId(), cd);
		}
		c.emit("pop\t\t\t\t\t\t\t\t; Frame " + frameId + " finished");
		
		body.compile(c, newEnv);
		
		env.endScope();
		c.finishFrame();

		if (ancestorId != null) {
			c.emit("aload_1\t\t\t\t\t\t\t; Leave nested frame");
			c.emit("getfield frame_" + frameId + "/SL Lframe_" + ancestorId + ";");
			c.emit("astore_1");
		}
		
	}

}
