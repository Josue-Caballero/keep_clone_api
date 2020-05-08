
package ec.com.jnegocios.exception.global.file;

/**
 * FileNotSupportException
 */
@SuppressWarnings("serial")
public class FileNotSupportException extends RuntimeException {

	private static final String DEFAULT_MSG = "File not supported";

	public FileNotSupportException() {
	
		super(DEFAULT_MSG);
	
	}
	
	public FileNotSupportException(String details) {
	
		super( String.format("%s: %s", DEFAULT_MSG, details) );
	
	}
	
}
