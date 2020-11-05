package ast;

import java.io.IOException;
import java.util.List;

import com.CodeBlock;
import com.CompilerFrame;
import com.Environment;

public class ASTId implements ASTNode {

	private String id;
	
	public ASTId(String id) {
		this.id = id;
	}
	
	@Override
	public int eval(Environment env) {
		return env.find(id);
	}


	@Override
	public void compile(CodeBlock c, CompilerFrame cf) throws IOException {
		c.emit("aload_1");
		
		List<Integer> levels = cf.findLevels(id);
		
		System.out.print(".ASTId, compile [APAGAR] : Levels for " + id + " > " );
		for(Integer l : levels) {
			System.out.print(l + " ");
		}
		System.out.println();
		
		int current = levels.remove(0); 
		c.emit("checkcast frame_" + current);
		while(!levels.isEmpty()) {
			int level = levels.remove(0);
			c.emit("getfield frame_" + current + "/SL Lframe_" + level + ";");
			current = level;
		}
		c.emit("getfield frame_" + current + "/" + id + " I");
	}

}
