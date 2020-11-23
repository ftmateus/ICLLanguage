package exceptions;

public class IDDeclaredException extends SyntaxException {

	private static final long serialVersionUID = 1L;
	
	public IDDeclaredException(String message) {
		super(message + " was already declared.");
	}
    
}

