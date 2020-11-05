package com;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class JasminUtils {

	public static AtomicInteger counter = new AtomicInteger(0);
	
	public static void initMethod(FileWriter main) throws IOException {
		main.write("\n\n.method public <init>()V");
		main.write("\n\taload_0");
		main.write("\n\tinvokenonvirtual java/lang/Object/<init>()V");
		main.write("\n\treturn");
		main.write("\n.end method");
	}
	
	public static void firstDirectives(String cName, FileWriter main) throws IOException {
		main.write(".source " + cName + ".j");
		main.write("\n.class public " + cName);
		main.write("\n.super java/lang/Object");
	}
	
	public static void firstDirectives(String cName, String iName, FileWriter main) throws IOException {
		firstDirectives(cName, main);
		main.write("\n.implements " + iName);
	}
	
}
