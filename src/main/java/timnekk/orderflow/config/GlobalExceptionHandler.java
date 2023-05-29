package timnekk.orderflow.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import timnekk.orderflow.exception.DishNotFoundException;
import timnekk.orderflow.exception.OrderNotFoundException;
import timnekk.orderflow.exception.UsernameConflictException;
import timnekk.orderflow.exception.dto.StringExceptionResponse;
import timnekk.orderflow.exception.dto.StringMapExceptionResponse;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<StringExceptionResponse> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StringExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StringMapExceptionResponse> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new StringMapExceptionResponse(errors));
    }

    @ExceptionHandler(UsernameConflictException.class)
    public ResponseEntity<StringExceptionResponse> handleUsernameConflictException(UsernameConflictException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new StringExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity<StringExceptionResponse> handleDishNotFoundException(DishNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new StringExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<StringExceptionResponse> handleOrderNotFoundException(OrderNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new StringExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<StringExceptionResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new StringExceptionResponse(ex.getMessage()));
    }

}