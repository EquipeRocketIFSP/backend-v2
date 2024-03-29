package br.vet.certvet.config.exception;

import br.vet.certvet.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionsHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            NotMatchingFileTypeToPdfException.class
    })
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField().replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase(),
                        FieldError::getDefaultMessage
                ));
    }
    @ExceptionHandler({
            HttpMediaTypeNotSupportedException.class
    })
    public ResponseEntity<String> handleUnsuportedMediaType(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(exception.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            NotFoundException.class,
            EntityNotFoundException.class,
            ProntuarioNotFoundException.class,
            DocumentoNotPersistedException.class,
            DocumentoNotFoundException.class,
            PdfNaoReconhecidoException.class
    })
    public ResponseEntity<String> handleNotFound(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({
            ConflictException.class,
            AlreadyPrescribedException.class
    })
    public ResponseEntity<String> handleConflict(RuntimeException exception) {
        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<String> handleUnprocessableEntity(RuntimeException exception) {
        return new ResponseEntity<>("Não foi possivel processar o conteúdo dessa requisição", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> handleForbidden(RuntimeException exception) {
        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler({
            BadGatewayException.class
    })
    public ResponseEntity<String> handleBadGateway(RuntimeException exception) {
        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler({AssinadorNaoCadastradoException.class})
    public ResponseEntity<String> handleNotAcceptable(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(exception.getLocalizedMessage());
    }

    @ExceptionHandler({
            AwsPermissionDeniedException.class,
            AwsS3WritingException.class
    })
    public ResponseEntity<String> handleServiceUnavailable(RuntimeException e){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(e.getLocalizedMessage());
    }

    @ExceptionHandler({
            ProcessamentoIcpBrJsonResponseException.class,
            ProcessamentoIcpBrJsonRequestException.class
    })
    public ResponseEntity<String> handleServiceUnavailable(IOException e){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(e.getLocalizedMessage());
    }

    @ExceptionHandler({
            ElementoMauFormadoNoHtmlException.class,
            PdfMauFormadoException.class,
            EscritaProntuarioPdfException.class,
            RendezizacaoPDFException.class,
            EscritaDocumentoPdfException.class,
            EscritaPrescricaoPdfException.class,
            ErroSalvarPdfAssinadoAwsException.class,
            FalhaEnvioEmailException.class,
            ErroAoProcessarTipoDocumento.class
    })
    public ResponseEntity<String> handleInternalServerError(IOException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getLocalizedMessage());
    }
}
