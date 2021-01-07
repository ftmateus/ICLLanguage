package env;

import java.util.ArrayList;
import java.util.List;

import ast.ASTNode;

public class Function {
	
	private String name, returnType;
	private List<Parameter> parameters;
	private ASTNode body;
	
	public Function(String name) {
		this.name = name;
		parameters = new ArrayList<>();
	}
	
	public void addParameter(String id, String type) {
		parameters.add(new Parameter(id,type));
	}
	
	public void setReturnType(String rType) {
		this.returnType = rType;
	}
	
	public void setBody(ASTNode node) {
		body = node;
	}
	
	public String getFunctionName() {
		return name;
	}
	
	public List<Parameter> getParameters() {
		return parameters;
	}
	
	public String getReturnType() {
		return returnType;
	}
	
	public ASTNode getBody() {
		return body;
	}
	
}
