package exceptions;
import java.lang.RuntimeException;

public class IdNotDefinedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public IdNotDefinedException(String message) {
		super(message + " has no value assigned.");
	}
    
}
