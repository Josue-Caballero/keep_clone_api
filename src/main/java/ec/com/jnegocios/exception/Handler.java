package ec.com.jnegocios.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import ec.com.jnegocios.exception.global.GlobalException;
import ec.com.jnegocios.exception.global.auth.AccountServiceException;
import ec.com.jnegocios.exception.global.file.FileNotSupportException;

@RestControllerAdvice
public class Handler {
	
	@Autowired
	private GlobalException global;
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
    	NoHandlerFoundException.class,
    	NotFoundException.class
    })
    public ErrorResponse notFoundException(HttpServletRequest request, Exception exception) {
		return global.Ups(exception, request.getRequestURI()) ;
    }
	
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
		BadRequestException.class,
		FileNotSupportException.class,
		AccountServiceException.class,
		org.springframework.dao.DuplicateKeyException.class,
		org.springframework.web.HttpRequestMethodNotSupportedException.class,
		org.springframework.web.bind.MissingRequestHeaderException.class,
		org.springframework.web.bind.MissingServletRequestParameterException.class,
		org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class,
		org.springframework.http.converter.HttpMessageNotReadableException.class,
		org.springframework.web.bind.MethodArgumentNotValidException.class
    })
    public ErrorResponse badRequestException(HttpServletRequest request, Exception exception) {
    	return global.Ups(exception, request.getRequestURI());
    }
    
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({
    	ConflictException.class
    })
    public ErrorResponse conflictException(HttpServletRequest request, Exception exception) 
    {
    	return global.Ups(exception, request.getRequestURI()) ;
    }
    
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({
    	ForbiddenException.class
    })
    public ErrorResponse forbiddenException (HttpServletRequest request, Exception exception)
    {
    	return new ErrorResponse(exception, request.getRequestURI(), HttpStatus.FORBIDDEN.value());
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)  
    public ErrorResponse unexpectedException(HttpServletRequest request, Exception exception) {
    	return new ErrorResponse(exception, request.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    
}
