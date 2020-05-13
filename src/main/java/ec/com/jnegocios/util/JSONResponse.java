
package ec.com.jnegocios.util;

import java.util.HashMap;
import java.util.Map;

public class JSONResponse {

	private Map<String, Object> body;

	public JSONResponse() {

		this.body = new HashMap<>();

	}

	private JSONResponse(String path, String message, int statusCode) {

		this();
		this.body.put("path", path);
		this.body.put("message", message);
		this.body.put("code", statusCode);

	}

	public static JSONResponse fromGeneralTemplate(String path, String message, 
		int statusCode) {

		return ( new JSONResponse(path, message, statusCode) );

	}
	
	public JSONResponse addPropertie(String identifier, Object item) {

		body.put(identifier, item);

		return this;

	}

	public Map<String, Object> getBody() {

		return this.body;

	}
	
}
