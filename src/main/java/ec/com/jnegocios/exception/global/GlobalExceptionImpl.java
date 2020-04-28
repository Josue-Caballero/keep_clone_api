package ec.com.jnegocios.exception.global;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

@Component
public class GlobalExceptionImpl implements GlobalException {
	
	@Override
	public ErrorResponse badException(HttpMessageNotReadableException exception, String path) {
		return new ErrorResponse(exception.getMostSpecificCause().getLocalizedMessage(), exception, path, HttpStatus.BAD_REQUEST.value());
	}

	@Override
	public ErrorResponse badException(HttpRequestMethodNotSupportedException exception, String path) {
		String message = "No se admite peticiones de tipo " + exception.getMethod();
		List<String> details = Arrays.asList(exception.getSupportedMethods());
		return new ErrorResponse(message, "Solo se admiten las siguientes peticiones", details, exception, path, HttpStatus.BAD_REQUEST.value());
	}

	@Override
	public ErrorResponse badException(MissingRequestHeaderException exception, String path) {
		return new ErrorResponse("El valor de la cabecera " + exception.getHeaderName() + " no es válido.", exception, path, HttpStatus.BAD_REQUEST.value());
	}

	@Override
	public ErrorResponse badException(MissingServletRequestParameterException exception, String path) {
		String message = "Se esperaba un parametro con el nombre " + exception.getParameterName()+ " de tipo "+ exception.getParameterType();
		return new ErrorResponse(message, exception, path, HttpStatus.BAD_REQUEST.value());
	}

	@Override
	public ErrorResponse badException(MethodArgumentTypeMismatchException exception, String path) {
		String message = "Se esperaba un " + exception.getRequiredType().getSimpleName();
		return new ErrorResponse(message, "No se puede convertir el valor " +  exception.getValue() 
    			+ " a un tipo de dato " + exception.getRequiredType().getSimpleName(), 
    			exception, path, HttpStatus.BAD_REQUEST.value());
	}

	@Override
	public ErrorResponse badException(MethodArgumentNotValidException exception, String path) {
		BindingResult bindingResult = exception.getBindingResult();
		
		String message = "Ha ocurrido un error al validar los campos de "+ bindingResult.getObjectName();
		List<FieldError> errors = bindingResult.getFieldErrors();
		List<String> listErrors = new ArrayList<String>();
		
		errors.forEach(e -> listErrors.add(e.getDefaultMessage()));
		return new ErrorResponse(message, listErrors, exception, path, HttpStatus.BAD_REQUEST.value());
	}

	@Override
	public ErrorResponse someException(Exception exception, String path) {
		return new ErrorResponse(exception, path, HttpStatus.BAD_REQUEST.value());
	}
	
	@Override
	public ErrorResponse badException(BadRequestException exception, String path) {
		return new ErrorResponse(exception.getMessage(), exception, path, HttpStatus.BAD_REQUEST.value());
	}

	@Override
	public ErrorResponse notfoundException(NoHandlerFoundException exception, String path) {
		return new ErrorResponse("No se ha encontrado la ruta solicitada.", exception, path, HttpStatus.NOT_FOUND.value());
	}

	@Override
	public ErrorResponse notfoundException(NotFoundException exception, String path) {
		return new ErrorResponse(exception, path, HttpStatus.NOT_FOUND.value());
	}

	@Override
	public ErrorResponse conflictException(ConflictException exception, String path) {
		return new ErrorResponse(exception, path, HttpStatus.CONFLICT.value());
	}

	@Override
	public ErrorResponse conflictException(ArithmeticException exception, String path) {
		return new ErrorResponse("Error al realizar la operación matemática", exception, path, HttpStatus.CONFLICT.value());
	}

	@Override
	public ErrorResponse Ups(Exception exception, String path) {
		ErrorResponse error = someException(exception, path);
		if(exception instanceof NoHandlerFoundException) error = notfoundException((NoHandlerFoundException) exception, path);
		if(exception instanceof NotFoundException) error = notfoundException((NotFoundException) exception, path);
		if(exception instanceof BadRequestException) error = badException((BadRequestException) exception, path);
		if(exception instanceof MethodArgumentNotValidException) error = badException((MethodArgumentNotValidException) exception, path);
		if(exception instanceof MethodArgumentTypeMismatchException) error = badException((MethodArgumentTypeMismatchException) exception, path);
		if(exception instanceof MissingServletRequestParameterException) error = badException((MissingServletRequestParameterException) exception, path);
		if(exception instanceof MissingRequestHeaderException) error = badException((MissingRequestHeaderException) exception, path);
		if(exception instanceof HttpMessageNotReadableException) error = badException((HttpMessageNotReadableException) exception, path);
		if(exception instanceof HttpRequestMethodNotSupportedException) error = badException((HttpRequestMethodNotSupportedException) exception, path);
		if(exception instanceof ConflictException) error = conflictException((ConflictException) exception, path);
		if(exception instanceof ArithmeticException) error = conflictException((ArithmeticException) exception, path);
	
		return error;
	}

}
