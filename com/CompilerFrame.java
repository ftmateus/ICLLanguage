package com;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exceptions.IDDeclaredException;
import exceptions.IdNotDefinedException;

public class CompilerFrame {
	
	private List<String> variables;
	private FileWriter fw;
	private CompilerFrame parent;
	private Integer id;

	public CompilerFrame() {
		fw = null;
		parent = null;
		id = null;
		variables = null;
	}
	
	public CompilerFrame(CompilerFrame parent) throws IOException {
		variables = new ArrayList<>();
		this.parent = parent;
		this.id = JasminUtils.counter.getAndIncrement();
		fw = new FileWriter(CodeBlock.location + "frame_" + id + ".j", false);
		init();
	}
	
	public Integer getId() {
		return id;
	}
	
	private void init() throws IOException {
		JasminUtils.firstDirectives("frame_" + id, "frame", fw);
		fw.write("\n");
		if (parent != null && parent.getId() != null) {
			fw.write("\n.field public SL Lframe_" + parent.getId() + ";");
		}
	}
	
	public CompilerFrame beginFrame() throws IOException {
		return new CompilerFrame(this);
	}
	
	public List<String> createFrame() {
		List<String> cmds = new ArrayList<>();
		cmds.add("new frame_" + id + "\t\t\t\t\t; Frame " + id + " started");
		cmds.add("dup");
		cmds.add("invokespecial frame_" + id + "/<init>()V");
		cmds.add("dup");
		if(parent != null && parent.getId() != null) {
			cmds.add("aload 1");
			cmds.add("putfield frame_" + id +"/SL Lframe_" + parent.getId() + ";");
			cmds.add("dup");
		}
		cmds.add("astore 1");
		cmds.add("dup");
		
		return cmds;
	}
	
	public String addVariable(String name) throws IOException {
		if(variables.contains(name))
			throw new IDDeclaredException(name);
		variables.add(name);
		fw.write("\n.field public " + name + " I");
		return "putfield frame_" + id + "/" + name + " I";
	}
	
	public String finishFrame() throws IOException {
		JasminUtils.initMethod(fw);
		fw.close();
		return "pop\t\t\t\t\t\t; Frame " + id + " finished";
	}
	

	private List<Integer> find(String name, List<Integer> levels) {
		if(variables != null && variables.contains(name))
			return levels;
		else {
			if (parent != null) {
				levels.add(parent.getId());
				return parent.find(name, levels);
			} else
				throw new IdNotDefinedException(name);
		}
	}
	
	public List<Integer> findLevels(String name) {
		List<Integer> levels = new ArrayList<>();
		levels.add(this.id);
		return this.find(name, levels);
	}
	
	//leave nested frame on def
	public List<String> reset() {
		List<String> cmds = new ArrayList<>();
		if (parent.getId() != null) {
			cmds.add("aload 1\t\t\t\t\t; Next 3ll: Get parent frame [" + parent.getId() + 
					"] and store it into local var 1");
			cmds.add("checkcast frame_" + id + "\t\t\t\t; Current frame stored in 1: " + id);
			cmds.add("getfield frame_" + this.id + "/SL Lframe_" + parent.getId() + ";");
			cmds.add("astore 1");
		}
		return cmds;

	}
	
	
}
