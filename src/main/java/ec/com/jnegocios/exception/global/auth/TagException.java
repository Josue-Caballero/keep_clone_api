package ec.com.jnegocios.exception.global.auth;

public class TagException extends RuntimeException {

	private static final long serialVersionUID = -7659973393696796055L;
	
	private static final String DEFAULT_MSG = "Something exception occurred";
	
	public TagException() {
		super(DEFAULT_MSG);
	}
	
	public TagException(String details)
	{
		super( String.format("%s: %s", DEFAULT_MSG, details) );
	}
}
