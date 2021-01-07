package values;

import java.util.ArrayList;
import java.util.List;

import ast.ASTNode;

public class VFun implements IValue {
	
	private ASTNode node;
	private List<String> ids;
	
	public VFun(ASTNode node) {
		this.node = node;
		this.ids = new ArrayList<>();
	}
	
	public void addId(String id) {
		this.ids.add(id);
	}
	
	public List<String> getParams() {
		return ids;
	}
	
	public ASTNode getFunction() {
		return node;
	}

}
