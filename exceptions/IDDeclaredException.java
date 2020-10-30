package exceptions;
import java.lang.RuntimeException;

public class IDDeclaredException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public IDDeclaredException(String message) {
		super(message + " was already declared.");
	}
    
}

