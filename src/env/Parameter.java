package env;

public class Parameter {

	private String id, type;
	
	public Parameter(String id, String type) {
		this.id = id;
		this.type = type;
	}
	
	public String getId() {
		return id;
	}
	
	public String getType() {
		return type;
	}
}
