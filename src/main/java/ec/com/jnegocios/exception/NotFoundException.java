package ec.com.jnegocios.exception;

public class NotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -4006682371131437146L;

	public NotFoundException(String context) {
		super(context);
	}
}
