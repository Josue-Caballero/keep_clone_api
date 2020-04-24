package ec.com.jnegocios.exception;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Error implements Serializable {
	
	private static final long serialVersionUID = 6056962266204905573L;

	private String message;
	
	private String exception;
	
	private String uri;
	
	private List<String> errors;
	
	public Error (Exception ex, String uri)
	{
		this.message = ex.getMessage();
		this.exception = ex.getClass().getSimpleName();
		this.uri = uri;
	}
	
	public Error (String message, Exception ex, String uri)
	{
		this.message = message;
		this.exception = ex.getClass().getSimpleName();
		this.uri = uri;
	}
	
	public Error(List<String> errors, Exception ex, String uri) 
	{
		this.errors = errors;
		this.exception = ex.getClass().getSimpleName();
		this.uri = uri;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	@Override
	public String toString() {
		return "Error [message=" + message + ", exception=" + exception + ", uri=" + uri + ", errors="
				+ errors + "]";
	}
	
}
