package beevengers.billiardmanager.exception;

import beevengers.billiardmanager.exception.core.ApplicationException;
import beevengers.billiardmanager.exception.core.ErrorDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorDetails> handlerApplicationException(ApplicationException ex, HttpServletRequest request) {

        ErrorDetails errorDetails = ErrorDetails
                .builder()
                .statusCode(ex.getStatus().value())
                .status(ex.getStatus().getReasonPhrase())
                .message(ex.getMessage())
                .timestamp(new Date())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(ex.getStatus())
                .body(errorDetails);
    }
}
