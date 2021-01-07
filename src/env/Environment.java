package env;

import java.util.HashMap;
import java.util.Map;

import exceptions.IDDeclaredException;
import exceptions.IdNotDefinedException;

public class Environment<T> {

	private Environment<T> parent = null;
	private Map<String, T> assoc = new HashMap<>();
	private int depth = 0;
	
	public Environment() {
		
	}
	
	public Environment(Environment<T> parent, int depth) {
		this.parent = parent;
		this.depth = depth;
	}
	
	public Environment<T> beginScope() {
		return new Environment<T>(this, depth + 1);
	}
	
	public Environment<T> endScope() {
		return parent;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public void assoc(String id, T bind) {
		if(assoc.get(id) != null)
			throw new IDDeclaredException(id);
		assoc.put(id, bind);
	}
	
	public T find(String id) {
		T v = assoc.get(id);
		if (v == null) {
			if (parent != null)
				return parent.find(id);
			throw new IdNotDefinedException(id);
		} 
		else return v;
	}
}
