
package ec.com.jnegocios.exception.global.file;

@SuppressWarnings("serial")
public class FileUploadException extends RuntimeException {

	private static final String DEFAULT_MSG = "Could not upload the file";

	public FileUploadException() {
	
		super(DEFAULT_MSG);
	
	}
	
	public FileUploadException(String details) {
	
		super( String.format("%s: %s", DEFAULT_MSG, details) );
	
	}

}
