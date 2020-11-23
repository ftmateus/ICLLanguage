package ast;

import java.io.IOException;
import java.util.List;

import env.CodeBlock;
import env.Coordinates;
import env.Environment;

public class ASTId implements ASTNode {

	private String id;
	
	public ASTId(String id) {
		this.id = id;
	}
	
	@Override
	public int eval(Environment<Integer> env) {
		return env.find(id);
	}


	@Override
	public void compile(CodeBlock c, Environment<Coordinates> cf) throws IOException {
		c.emit("aload_1\t\t\t\t\t\t\t; Get " + id + " variable");
		
		Coordinates cd = cf.find(id);
		int levels = cf.getDepth() - cd.getDepth();
		
		List<Integer> ancestors = c.getAncestors(levels + 1);
		int current = ancestors.remove(0);
		for(int i = 0; i < levels; i++) {
			int next = ancestors.remove(0);
			c.emit("getfield frame_" +  current + "/SL Lframe_" + next + ";");
			current = next;
		}
		
		c.emit("getfield frame_"+ current + "/loc_" + cd.getId() + " I");
	}

}
