package ast.methods;

import java.io.IOException;
import java.util.List;

import ast.ASTNode;
import env.CodeBlock;
import env.Coordinates;
import env.Environment;
import types.IType;
import values.IValue;

public class ASTId implements ASTNode {

	private String id;
	
	public ASTId(String id) {
		this.id = id;
	}
	
	@Override
	public IValue eval(Environment<IValue> env) {
		return env.find(id);
	}
	
	@Override
	public void compile(CodeBlock c, Environment<Coordinates> cf) throws IOException {		
		Coordinates cd = cf.find(id);
		if (cd.getDepth() == 0) {
			c.emitF("getstatic main/g_" + cd.getId() + " " + cd.getType() + "\t\t\t\t\t\t\t; Get " + id
					+ " variable");
			return;
		}

		c.emitF("aload_1\t\t\t\t\t\t\t\t\t\t\t\t\t; Get " + id + " variable");
		int levels = cf.getDepth() - cd.getDepth();
		
		List<Integer> ancestors = c.getAncestors(levels);
		int current = ancestors.remove(0);
		for (int i = 0; i < levels; i++) {
			int next = ancestors.remove(0);
			c.emitF("getfield frame_" + current + "/SL Lframe_" + next + ";");
			current = next;
		}
		c.emitF("getfield frame_" + current + "/loc_" + cd.getId() + " " + cd.getType());		
	}

	@Override
	public IType typecheck(Environment<IType> env) {
		return env.find(id);
	}

}
