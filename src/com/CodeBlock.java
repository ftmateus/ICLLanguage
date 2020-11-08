package com;

import java.util.List;
import java.io.FileWriter; 
import java.io.IOException;

public class CodeBlock {

	protected static String location;
	
	private FileWriter main;
	
	public CodeBlock(String loc) throws IOException {
		location = loc;
		main = new FileWriter(location + "main.j", false);	
		init();
	}
	
	private void init() throws IOException {
		JasminUtils.firstDirectives("main", main);
		JasminUtils.initMethod(main);
		
		main.write("\n\n.method public static main([Ljava/lang/String;)V\n");
		main.write("\n\t.limit locals 2");
		main.write("\n\t.limit stack 256\n");
		main.write("\n\tgetstatic java/lang/System/out Ljava/io/PrintStream;\n");
	}
	
	public void emit(String	command) throws IOException {	
		main.write("\n\t" + command);
	}	
	
	public void emit(List<String> cmds) throws IOException {	
		for(String cmd: cmds)
			main.write("\n\t" + cmd);
	}
	
	public void finish() throws IOException {
		main.write("\n\n\tinvokestatic java/lang/String/valueOf(I)Ljava/lang/String;");
	    main.write("\n\tinvokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
	    main.write("\n\treturn");
	    main.write("\n\n.end method");
		main.close();
	}
	
	
}
