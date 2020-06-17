package gipl.web.project.exception;

/**
 * ------------------------------------------------
 * This class handles 'Invalid Input' exception.
 * ------------------------------------------------
 */
public class InvalidInputException extends Exception {	
	private static final long serialVersionUID = 1340858650538182127L;

	public InvalidInputException(String errorMessage, Throwable error) {
		super(errorMessage, error);
	}
	
}
