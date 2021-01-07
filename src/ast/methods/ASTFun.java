package ast.methods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ast.ASTNode;
import env.CodeBlock;
import env.Coordinates;
import env.Environment;
import exceptions.TypeErrorException;
import types.IType;
import types.TFun;
import values.IValue;
import values.VFun;

public class ASTFun implements ASTNode {

	private List<ASTNode> nodes;
	private String name;
	private TFun fun;

	public ASTFun(String name, List<ASTNode> nodes) {
		this.name = name;
		this.nodes = nodes;
	}

	@Override
	public IValue eval(Environment<IValue> env) {
		VFun fun = (VFun) env.find(name);
		List<String> params = fun.getParams();
		Environment<IValue> newEnv = env.beginScope();
		for (int i = 0; i < nodes.size(); i++) {
			IValue v = nodes.get(i).eval(newEnv);
			newEnv.assoc(params.get(i), v);
		}
		IValue res = fun.getFunction().eval(newEnv);
		newEnv.endScope();
		return res;
	}

	@Override
	public void compile(CodeBlock c, Environment<Coordinates> env) throws IOException {
		for (ASTNode t : nodes)
			t.compile(c, env);

		String params = "";
		List<IType> original = fun.getTypes();
		for (IType t : original)
			params += IType.compileFormatL(t.toString());

		String rT = IType.compileFormatL(fun.getReturnType().toString());
		c.emitF("invokestatic functions/" + name + "(" + params + ")" + rT);

	}

	@Override
	public IType typecheck(Environment<IType> env) {
		IType type = env.find(name);
		if (!(type instanceof TFun))
			throw new TypeErrorException(name, "fun");

		fun = (TFun) type;
		List<IType> original = fun.getTypes();
		List<IType> types = new ArrayList<>();
		for (ASTNode t : nodes)
			types.add(t.typecheck(env));

		int size = original.size();
		if (types.size() != size)
			throw new TypeErrorException(name, original, types);

		IType t1, t2;
		for (int i = 0; i < size; i++) {
			t1 = original.get(i);
			t2 = types.get(i);
			if (!t1.toString().equals(t2.toString()))
				throw new TypeErrorException(name, original, types);
		}

		return fun.getReturnType();
	}

}
