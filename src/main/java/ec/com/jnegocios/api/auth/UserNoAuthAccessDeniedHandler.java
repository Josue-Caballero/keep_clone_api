
package ec.com.jnegocios.api.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import ec.com.jnegocios.exception.ErrorResponse;

public class UserNoAuthAccessDeniedHandler implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest req, HttpServletResponse res,
		AuthenticationException authEx) throws IOException, ServletException {
		
		ErrorResponse error = new ErrorResponse(
			"The Api Key is required to access this resource",
			(Exception) authEx, 
			req.getRequestURI(), 
			403
		);

		res.getWriter().write( new ObjectMapper().writeValueAsString(error) );
		res.setStatus(403);
		res.setContentType("application/json");
			
	}
	
}
