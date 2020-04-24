package ec.com.jnegocios.exception;

public class ConflictException extends RuntimeException {
	
	private static final long serialVersionUID = -2628872345608879483L;

	public ConflictException(String ctx) 
	{
		super(ctx);
	}
}
