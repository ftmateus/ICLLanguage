package env;

import java.util.ArrayList;
import java.util.List;

import utils.JasminUtils;

import java.io.FileWriter; 
import java.io.IOException;

public class CodeBlock {

	private String location;
	private FileWriter main;	
	private List<Integer> frames;
	private int counter;
	
	public CodeBlock(String loc) throws IOException {
		location = loc;
		main = new FileWriter(location + "main.j", false);	
		this.frames = new ArrayList<>();
		this.counter = 0;
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
	
	public int getAncestor() {
		int size = frames.size();
		return frames.get(size-2);
	}
	
	public List<Integer> getAncestors(int levels) {
		List<Integer> ancestors = new ArrayList<Integer>();
		int size = frames.size();
		for(int i = 0; i < levels; i++)
			ancestors.add( frames.get(size - 1 - i) );
		return ancestors;
	}
	
	public int createFrame(int nVariables) throws IOException {
		int id = counter++;
		frames.add(id);
		Integer ancestor = null;
		int size = frames.size();
		if(size > 1)
			ancestor = frames.get(size - 2);
		
		emit("new frame_" + id + "\t\t\t\t\t\t; Frame " + id + " started");
		emit("dup");
		emit("invokespecial frame_" + id + "/<init>()V");
		
		FileWriter fw = new FileWriter(location + "frame_" + id + ".j", false);
		JasminUtils.firstDirectives("frame_" + id, "frame", fw);
		fw.write("\n");
		if (ancestor != null)
			fw.write("\n.field public SL Lframe_" + ancestor + ";");
		
		for(int i = 0; i < nVariables; i++)
			fw.write("\n.field public loc_" + i + " I");
		
		JasminUtils.initMethod(fw);
		fw.close();
		
		return id;
	}
	
	public void finishFrame() {
		int size = frames.size();
		frames.remove(size - 1);
	}
	
	public void emit(String	command) throws IOException {	
		main.write("\n\t" + command);
	}
	
	public void finish() throws IOException {
		main.write("\n\n\tinvokestatic java/lang/String/valueOf(I)Ljava/lang/String;");
	    main.write("\n\tinvokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
	    main.write("\n\treturn");
	    main.write("\n\n.end method");
		main.close();
	}
	
}
