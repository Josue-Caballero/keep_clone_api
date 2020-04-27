package ec.com.jnegocios.exception;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ErrorResponse implements Serializable {
	
	private static final long serialVersionUID = 6056962266204905573L;
	
	private LocalDateTime timestamps;
	
	private String path;
	
	private int code;
	
	private String message;
	
	private String description;
	
	private List<String> details;
	
	private String exception;
	
	
	public ErrorResponse (Exception ex, String path, int code)
	{
		this.timestamps = LocalDateTime.now();
		this.message = ex.getMessage();
		this.exception = ex.getClass().getSimpleName();
		this.path = path;
		this.code = code;
	}
	
	public ErrorResponse (String message, Exception ex, String path, int code)
	{
		this.timestamps = LocalDateTime.now();
		this.message = message;
		this.exception = ex.getClass().getSimpleName();
		this.path = path;
		this.code = code;
	}
	
	public ErrorResponse (String message, String description, Exception ex, String path, int code)
	{
		this.timestamps = LocalDateTime.now();
		this.message = message;
		this.description = description;
		this.exception = ex.getClass().getSimpleName();
		this.path = path;
		this.code = code;
	}
	
	public ErrorResponse(List<String> details, Exception ex, String path, int code) 
	{
		this.timestamps = LocalDateTime.now();
		this.details = details;
		this.exception = ex.getClass().getSimpleName();
		this.path = path;
		this.code = code;
	}
	
	public ErrorResponse(String message, List<String> details, Exception ex, String path, int code) 
	{
		this.timestamps = LocalDateTime.now();
		this.message = message;
		this.details = details;
		this.exception = ex.getClass().getSimpleName();
		this.path = path;
		this.code = code;
	}
	
	public ErrorResponse(String message, String description, List<String> details, Exception ex, String path, int code) 
	{
		this.timestamps = LocalDateTime.now();
		this.message = message;
		this.description = description;
		this.details = details;
		this.exception = ex.getClass().getSimpleName();
		this.path = path;
		this.code = code;
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

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
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
				+ path + ", details=" + details + "]";
	}

}
