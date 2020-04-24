package ec.com.jnegocios.exception;

public class ForbiddenException extends RuntimeException {
	
	private static final long serialVersionUID = -3364790569658919548L;

	public ForbiddenException(String context) 
	{
		super(context);
	}
}
