package exceptions;

public class IdNotDefinedException extends SyntaxException {

	private static final long serialVersionUID = 1L;
	
	public IdNotDefinedException(String message) {
		super(message + " has no value assigned.");
	}
    
}
