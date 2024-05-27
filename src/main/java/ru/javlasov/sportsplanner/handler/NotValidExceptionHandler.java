package ru.javlasov.sportsplanner.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.javlasov.sportsplanner.dto.ErrorDto;

@ControllerAdvice
public class NotValidExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        var errorDto = new ErrorDto(null, status.toString());
        for (FieldError defaultMessage : exception.getBindingResult().getFieldErrors()) {
            if (errorDto.getMessage() == null) {
                errorDto.setMessage(defaultMessage.getDefaultMessage());
            } else {
                errorDto.setMessage(errorDto.getMessage() + ", " + defaultMessage.getDefaultMessage());
            }
        }
        return ResponseEntity.status(status).body(errorDto);
    }

}
