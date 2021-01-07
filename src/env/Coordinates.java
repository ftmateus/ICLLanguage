package env;

public class Coordinates {

	private int depth, id;
	private String type;
	
	public Coordinates(int depth, int id, String type) {
		this.depth = depth;
		this.id = id;
		this.type = type;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public int getId() {
		return id;
	}
	
	public String getType() {
		return type;
	}
	
}
