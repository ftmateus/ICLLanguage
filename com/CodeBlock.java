package com;

import java.util.ArrayList;
import java.util.List;

public class CodeBlock {

	private List<String> code;	
	
	public CodeBlock() {
		this.code = new ArrayList<>();
	}
	
	public void emit(String	bytecode)	{	
		this.code.add(bytecode);
	}	
	
	public List<String> get() {
		return code;
	}
	
}
