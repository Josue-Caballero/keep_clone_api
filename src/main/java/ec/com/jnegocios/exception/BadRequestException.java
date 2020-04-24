package ec.com.jnegocios.exception;

public class BadRequestException extends RuntimeException{

	private static final long serialVersionUID = -2405540642623804538L;

	public BadRequestException(String context) {
		super(context);
	}
	
}
