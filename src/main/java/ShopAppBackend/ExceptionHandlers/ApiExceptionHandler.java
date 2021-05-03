package ShopAppBackend.ExceptionHandlers;

import ShopAppBackend.User.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler  extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleWebExeption(RuntimeException e, WebRequest webRequest) {

        return handleExceptionInternal(e,e.getMessage(),HttpHeaders.EMPTY,HttpStatus.BAD_REQUEST,webRequest);
    }

//    @ExceptionHandler({ UnauthorizedException.class, TaskNotFoundException.class })
//    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
//        HttpHeaders headers = new HttpHeaders();
//        if (ex instanceof UnauthorizedException) {
//            HttpStatus status = HttpStatus.NOT_FOUND;
//            UnauthorizedException ue = (UnauthorizedException) ex;
//
//            return handleUserNotFoundException(ue, headers, status, request);
//        } else if (ex instanceof TaskNotFoundException) {
//            HttpStatus status = HttpStatus.BAD_REQUEST;
//            TaskNotFoundException tnfe = (TaskNotFoundException) ex;
//
//            return handleContentNotAllowedException(tnfe, headers, status, request);
//        } else {
//            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
//            return handleExceptionInternal(ex, null, headers, status, request);
//        }
//    }
}
