package env;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import utils.CompilerUtils;

import java.io.FileWriter; 
import java.io.IOException;

public class CodeBlock {

	private String location;
	private FileWriter main, functions, current;
	private Stack<Integer> frames;
	private int counter, labelCounter;
	
	public CodeBlock(String loc) throws IOException {
		location = loc;
		main = new FileWriter(location + "main.j", false);
		functions = new FileWriter(location + "functions.j", false);
		current = main;
		this.frames = new Stack<>();
		this.counter = 0;
		this.labelCounter = 0;
	}
	
	public void initFunctions() throws IOException {
		current = functions;
		CompilerUtils.firstDirectives("functions", current);
		emitF("");
		
		emitO(".method public static printI(I)V");
		emitF(".limit stack 2");
		emitF("getstatic java/lang/System/out Ljava/io/PrintStream;");
		emitF("iload_0");
		emitF("invokestatic java/lang/String/valueOf(I)Ljava/lang/String;");
		emitF("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
		emitF("return");
		emitO(".end method");
		emitF("");
		
		emitO(".method public static printO(Ljava/lang/Object;)V");
		emitF(".limit stack 2");
		emitF("getstatic java/lang/System/out Ljava/io/PrintStream;");
		emitF("aload_0");
		emitF("invokestatic java/lang/String/valueOf(Ljava/lang/Object;)Ljava/lang/String;");
		emitF("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
		emitF("return");
		emitO(".end method");
	}
	
	public void finishFunctions() throws IOException {
		current = main;
		functions.close();
	}
	
	public void initMain() throws IOException {
		current = main;
		CompilerUtils.firstDirectives("main", current);
	}
	
	public void startMain() throws IOException {
		CompilerUtils.initMethod(main);	
		emitO("");
		emitO(".method public static main([Ljava/lang/String;)V\n");
		emitF(".limit locals 2");
		emitF(".limit stack 256\n");
	} 
	
	//pre: frames.size() >= 2
	public int getAncestor() {
		int size = frames.size();
		return frames.get(size-2);
	}
	
	public List<Integer> getAncestors(int levels) {
		List<Integer> ancestors = new ArrayList<Integer>();
		int size = frames.size();
		for(int i = 0; i <= levels; i++)
			ancestors.add( frames.get(size - 1 - i) );
		return ancestors;
	}
	
	public FileWriter createFrame() throws IOException {
		int id = counter++;
		Integer ancestor = null;
		if(!frames.isEmpty())
			ancestor = frames.peek();
		frames.push(id);
		
		emitF("new frame_" + id + "\t\t\t\t\t\t\t\t\t\t\t\t; Frame " + id + " started");
		emitF("dup");
		emitF("invokespecial frame_" + id + "/<init>()V");
		
		FileWriter fw = new FileWriter(location + "frame_" + id + ".j", false);
		CompilerUtils.firstDirectives("frame_" + id, "frame", fw);
		fw.write("\n");
		if (ancestor != null)
			fw.write("\n.field public SL Lframe_" + ancestor + ";");
		
		return fw;
	}
	
	public void emitVariableFrame(FileWriter fw, String type, int i) throws IOException {
		fw.write("\n.field public loc_" + i + " " + type);
	}
	
	public void finishFrameFile(FileWriter fw) throws IOException {
		CompilerUtils.initMethod(fw);
		fw.close();
	}
	
	public int getCurrentFrame() {
		return frames.peek();
	}
	
	public void finishFrame() {
		frames.pop();
	}
		
	//emit inside function
	public void emitF(String command) throws IOException {	
		current.write("\n\t" + command);
	}
	
	//emit outside function
	public void emitO(String command) throws IOException {	
		current.write("\n" + command);
	}
	
	public void finishMain(String t) throws IOException {
	    emitF("return");
	    emitF("");
	    emitO(".end method");
		main.close();
	}
	
	public String getNewLabel() {
		return "L"+labelCounter++;
	}
	
	
}
