package br.vet.certvet.config.exception;

import br.vet.certvet.exceptions.ConflictException;
import br.vet.certvet.exceptions.ForbiddenException;
import br.vet.certvet.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionsHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(RuntimeException exception) {
        return new ResponseEntity<String>(exception.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> handleConflict(RuntimeException exception) {
        return new ResponseEntity<String>(exception.getLocalizedMessage(), HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleUnprocessableEntity(RuntimeException exception) {
        return new ResponseEntity<String>("Não foi possivel processar o conteúdo dessa requisição", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> handleForbidden(RuntimeException exception) {
        return new ResponseEntity<String>(exception.getLocalizedMessage(), HttpStatus.FORBIDDEN);
    }
}
