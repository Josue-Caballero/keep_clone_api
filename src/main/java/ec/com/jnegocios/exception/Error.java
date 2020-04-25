package ec.com.jnegocios.exception;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Error implements Serializable {
	
	private static final long serialVersionUID = 6056962266204905573L;
	
	private LocalDateTime timestamps;
	
	private String message;
	
	private String exception;
	
	private String path;
	
	private List<String> errors;
	
	public Error (Exception ex, String path)
	{
		this.timestamps = LocalDateTime.now();
		this.message = ex.getMessage();
		this.exception = ex.getClass().getSimpleName();
		this.path = path;
	}
	
	public Error (String message, Exception ex, String path)
	{
		this.timestamps = LocalDateTime.now();
		this.message = message;
		this.exception = ex.getClass().getSimpleName();
		this.path = path;
	}
	
	public Error(List<String> errors, Exception ex, String path) 
	{
		this.timestamps = LocalDateTime.now();
		this.errors = errors;
		this.exception = ex.getClass().getSimpleName();
		this.path = path;
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
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public LocalDateTime getTimestamps() {
		return timestamps;
	}

	public void setTimestamps(LocalDateTime timestamps) {
		this.timestamps = timestamps;
	}

	@Override
	public String toString() {
		return "Error [timestamps=" + timestamps + ", message=" + message + ", exception=" + exception + ", path="
				+ path + ", errors=" + errors + "]";
	}

}
