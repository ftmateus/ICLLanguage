package env;

import ast.ASTNode;

public class Binding {
	
	private String id, type;
	private ASTNode node;
	
	public Binding(String id, ASTNode node, String type) {
		this.id = id;
		this.node = node;
		this.type = type;
	}

	public String getId() {
		return id;
	}
	
	public ASTNode getExp() {
		return node;
	}
	
	public String getType() {
		return type;
	}
	
}
