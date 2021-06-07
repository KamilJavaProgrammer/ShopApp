package ShopAppBackend.ExceptionHandlers;

import ShopAppBackend.Exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler  extends ResponseEntityExceptionHandler {


    @ExceptionHandler({UserNotFoundException.class,EmailNotFoundException.class,IllegalArgumentException.class})
    public ResponseEntity<Object> handleWebException(RuntimeException e, WebRequest webRequest) {

        return handleExceptionInternal(e,e.getMessage(),HttpHeaders.EMPTY,HttpStatus.BAD_REQUEST,webRequest);
    }


    @ExceptionHandler({DataNotFoundException.class, ArticleLineNotFoundException.class, SectionNotFoundException.class})
    public ResponseEntity<Object> handleWebExceptionMongo(RuntimeException e, WebRequest webRequest) {

        return handleExceptionInternal(e,e.getMessage(),HttpHeaders.EMPTY,HttpStatus.NOT_FOUND,webRequest);
    }



}
