package com;

import java.util.HashMap;
import java.util.Map;

import exceptions.IDDeclaredException;
import exceptions.IdNotDefinedException;

public class Environment {

	private Environment parent = null;
	private Map<String, Integer> assoc = new HashMap<>();

	private final int depth;

	public Environment() 
	{
		this(null, 0);
	}
	
	private Environment(Environment parent, int depth) {
		this.depth = depth;
		this.parent = parent;
	}
	
	public Environment beginScope() {
		return new Environment(this, depth + 1);
	}
	
	public Environment endScope() {
		return parent;
	}

	public int getDepth()
	{
		return depth;
	}
	
	public void assoc(String id, Integer val) {
		if(assoc.get(id) != null)
			throw new IDDeclaredException(id);
		assoc.put(id, val);
	}
	
	public int find(String id) {
		Integer v = assoc.get(id);
		if (v == null) {
			if (parent != null)
				return parent.find(id);
			throw new IdNotDefinedException(id);
		} 
		else return v;
	}
	
	
}
