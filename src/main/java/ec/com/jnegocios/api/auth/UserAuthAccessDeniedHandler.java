
package ec.com.jnegocios.api.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import ec.com.jnegocios.exception.ErrorResponse;

/**
 * UserAuthAccessDeniedHandler
 */
public class UserAuthAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest req, HttpServletResponse res,
		AccessDeniedException accessDeniedEx) 
		throws IOException, ServletException {
		
		ErrorResponse error = new ErrorResponse(
			"Your user cannot access this resource", 
			(Exception) accessDeniedEx, 
			req.getRequestURI(), 
			403
		);

		res.getWriter().write( new ObjectMapper().writeValueAsString(error) );
		res.setStatus(403);
		res.setContentType("application/json");
		
	}
	
}
