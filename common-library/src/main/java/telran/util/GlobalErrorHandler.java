package telran.util;

import org.slf4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice //annotation for intercepting all exceptions
public class GlobalErrorHandler {
	private static final Logger LOG =
			LoggerFactory.getLogger(GlobalErrorHandler.class);
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidInputException.class) 
	String getBadRequestExceptionMessage(InvalidInputException ex) {
		LOG.error(ex.getMessage());
		return ex.getMessage();
	}
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class) 
	String getNotFoundExceptionMessage(NotFoundException ex) {
		LOG.error(ex.getMessage());
		return ex.getMessage();
	}
	
	
	

}
