package ec.com.jnegocios.exception.global;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import ec.com.jnegocios.exception.BadRequestException;
import ec.com.jnegocios.exception.ConflictException;
import ec.com.jnegocios.exception.ErrorResponse;
import ec.com.jnegocios.exception.NotFoundException;

public interface GlobalException {

	ErrorResponse Ups (Exception exception, String path);
	
	ErrorResponse badException(HttpMessageNotReadableException exception, String path);
	
	ErrorResponse badException(HttpRequestMethodNotSupportedException exception, String path);

	ErrorResponse badException(MissingRequestHeaderException exception, String path);
	
	ErrorResponse badException(MissingServletRequestParameterException exception, String path);
	
	ErrorResponse badException(MethodArgumentTypeMismatchException exception, String path);
	
	ErrorResponse badException(MethodArgumentNotValidException exception, String path);
	
	ErrorResponse badException(BadRequestException exception, String path);
	
	ErrorResponse someException(Exception exception, String path);
	
	ErrorResponse notfoundException(NoHandlerFoundException exception, String path);
	
	ErrorResponse notfoundException(NotFoundException exception, String path);
	
	ErrorResponse conflictException(ConflictException exception, String path);
	
	ErrorResponse conflictException(ArithmeticException exception, String path);
	
}
