package ast.methods;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import ast.ASTNode;
import env.Binding;
import env.CodeBlock;
import env.Coordinates;
import env.Environment;
import exceptions.TypeErrorException;
import types.IType;
import values.IValue;


public class ASTDef implements ASTNode {

	private List<Binding> bindings;
	private ASTNode body;
	
	public ASTDef(List<Binding> bindings, ASTNode body) {
		this.body = body;
		this.bindings = bindings;
	}
	
	public IValue eval(Environment<IValue> e) {
		Environment<IValue> env = e.beginScope();
		IValue res;
		
		for(Binding a : bindings) {
			res = a.getExp().eval(env);
			env.assoc(a.getId(), res);
		}
		
		res = body.eval(env);
		env.endScope();
		
		return res;
	}

	@Override
	public void compile(CodeBlock c, Environment<Coordinates> env) throws IOException {
		Environment<Coordinates> newEnv = env.beginScope();
		
		FileWriter fw = c.createFrame();
		int frameId = c.getCurrentFrame();
		
		Integer ancestorId = null;
		if(newEnv.getDepth() > 1) {
			ancestorId = c.getAncestor();
			c.emitF("dup");
			c.emitF("aload_1");
			c.emitF("putfield frame_" + frameId +"/SL Lframe_" + ancestorId + ";");
		}
		c.emitF("dup");
		c.emitF("astore_1");
		
		int id = 0;
		String type;
		for (Binding b : bindings) {
			c.emitF("dup");
			b.getExp().compile(c, newEnv);
			type = IType.compileFormatL(b.getType());
			if(!type.equals("I"))
				c.emitF("checkcast " + type.substring(1,type.length()-1));
			c.emitF("putfield frame_" + frameId + "/loc_" + id + " " + type);
			c.emitVariableFrame(fw, type.toString(), id);
			Coordinates cd = new Coordinates(newEnv.getDepth(), id++, type);
			newEnv.assoc(b.getId(), cd);
		}
		c.finishFrameFile(fw);
		c.emitF("pop\t\t\t\t\t\t\t\t\t\t\t\t\t\t; Frame " + frameId + " finished");
		
		
		body.compile(c, newEnv);
		
		newEnv.endScope();
		c.finishFrame();

		if (ancestorId != null) {
			c.emitF("aload_1\t\t\t\t\t\t\t\t\t\t\t\t\t; Leave nested frame");
			c.emitF("getfield frame_" + frameId + "/SL Lframe_" + ancestorId + ";");
			c.emitF("astore_1");
		}
		
	}

	@Override
	public IType typecheck(Environment<IType> env) {
		IType res;
		Environment<IType> newEnv = env.beginScope();
		
		for (Binding b : bindings) {
			IType idValue = b.getExp().typecheck(newEnv);
			String type = b.getType();
			if(!type.equals(idValue.toString()))
				throw new TypeErrorException("def", type);
			newEnv.assoc(b.getId(), idValue);
		}
		
		res = body.typecheck(newEnv);
		newEnv.endScope();
		return res;
	}

}
