package ast.methods;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ast.ASTNode;
import env.Binding;
import env.CodeBlock;
import env.Coordinates;
import env.Environment;
import env.Function;
import env.Parameter;
import exceptions.TypeErrorException;
import types.IType;
import types.TFun;
import values.IValue;
import values.VFun;

public class ASTGlobal implements ASTNode {

	private List<Binding> bindings;
	private List<Function> functions;
	private ASTNode main;
	
	public ASTGlobal(List<Binding> b, List<Function> functions, ASTNode main) {
		this.bindings = b;
		this.functions = functions;
		this.main = main;
	}
	
	@Override
	public IValue eval(Environment<IValue> e) {
		IValue res;
		
		for(Binding a : bindings) {
			res = a.getExp().eval(e);
			e.assoc(a.getId(), res);
		}
		
		for(Function f: functions) {
			List<Parameter> params = f.getParameters();
			VFun fun = new VFun(f.getBody());
			for(Parameter p : params)
				fun.addId(p.getId());
			e.assoc(f.getFunctionName(), fun);
		}
		
		res = main.eval(e);
		return res;
	}
	
	public void compileFunctions(CodeBlock c, Environment<Coordinates> env) throws IOException {
		for(Function function : functions) {
			Environment<Coordinates> newEnv = env.beginScope();
			c.emitF("");
			String params = "";
			int locals = 0;
			for(Parameter p :function.getParameters()) {
				params += IType.compileFormatL(p.getType());
				locals++;
			}
			String rType = IType.compileFormatL(function.getReturnType());
			
			c.emitO(".method public static " + function.getFunctionName() + "(" + params + ")" +  rType);
			c.emitF(".limit locals " + (locals+1));
			c.emitF(".limit stack 256");
			
			FileWriter fw = c.createFrame();
			int frameId = c.getCurrentFrame();
			c.emitF("dup");
			c.emitF("astore_" + locals);
			
			int id = 0;
			String type;
			for (Parameter p :function.getParameters()) {
				c.emitF("dup");
				type = IType.compileFormatL(p.getType());	
				if(type.equals("I")) 
					c.emitF("iload_" + id);
				else 
					c.emitF("aload_" +id);
				c.emitF("putfield frame_" + frameId + "/loc_" + id + " " + type);
				c.emitVariableFrame(fw, type, id);
				Coordinates cd = new Coordinates(newEnv.getDepth(), id++, type);
				newEnv.assoc(p.getId(), cd);
			}
			c.finishFrameFile(fw);
			c.emitF("pop\t\t\t\t\t\t\t\t\t\t\t\t\t\t; Frame " + frameId + " finished");
			
			c.emitF("aload_" + locals);
			c.emitF("astore_1");
			
			function.getBody().compile(c, newEnv);
			
			if(rType.equals("I"))
				c.emitF("ireturn");
			else
				c.emitF("areturn");
			c.emitO(".end method");
			newEnv.endScope();
		}		
	}
	
	@Override
	public void compile(CodeBlock c, Environment<Coordinates> env) throws IOException {
		c.initMain();
		if(!bindings.isEmpty()) {
			c.emitO("");
			List<String> types = new ArrayList<>();
			int id = 0;
			for(Binding b: bindings) {
				String type = IType.compileFormatL(b.getType());
				c.emitO(".field public static g_" + id + " " + type);
				types.add(type);
				Coordinates cd = new Coordinates(env.getDepth(), id++, type);
				env.assoc(b.getId(), cd);
			}
			
			c.emitO("\n.method static public <clinit>()V");
			c.emitF(".limit locals 2");
			c.emitF(".limit stack 256");
			id = 0;
			for(Binding b: bindings) {
				ASTNode exp = b.getExp();
				exp.compile(c, env);
				c.emitF("putstatic main/g_" + id + " " + types.get(id++));
				
			}
			c.emitF("return");
			c.emitO(".end method");
		} 

		c.initFunctions();
		compileFunctions(c,env);
		c.finishFunctions();
		
		c.startMain();
		
		main.compile(c, env);
		
		env.endScope();	
	}
	
	@Override
	public IType typecheck(Environment<IType> env) {		
		IType res;
		
		for (Binding b : bindings) {
			IType idValue = b.getExp().typecheck(env);
			String type = b.getType();
			if(!type.equals(idValue.toString()))
				throw new TypeErrorException(b.getId(), type);
			env.assoc(b.getId(), idValue);
		}
		
		for(Function function : functions) {
			List<Parameter> parameters = function.getParameters();
			Environment<IType> newEnv = env.beginScope();
			IType rType = IType.parseType(function.getReturnType());
			TFun fun = new TFun(rType);
			for (Parameter p : parameters) {
				IType type = IType.parseType(p.getType());
				fun.addType(type);
				newEnv.assoc(p.getId(), type);
			}
			env.assoc(function.getFunctionName(), fun);
			newEnv.assoc(function.getFunctionName(), fun);

			IType t = function.getBody().typecheck(newEnv);
			if (!t.toString().equals(rType.toString()))
				throw new TypeErrorException(function.getFunctionName(), t.toString(), rType.toString());
			
			newEnv.endScope();
		}
		
		res = main.typecheck(env);
		
		return res;
	}

}
