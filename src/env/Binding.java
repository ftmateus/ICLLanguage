package env;

import ast.ASTNode;

public class Binding {
	
	private String id;
	private ASTNode node;
	
	public Binding(String id, ASTNode node) {
		this.id = id;
		this.node = node;
	}

	public String getId() {
		return id;
	}
	
	public ASTNode getExp() {
		return node;
	}
	
}
