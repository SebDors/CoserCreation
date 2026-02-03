package CoserCreation.configs;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        if (ex.getMostSpecificCause().getMessage().contains("clients_email_key")) {
            return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Data integrity violation", HttpStatus.BAD_REQUEST);
    }
}
