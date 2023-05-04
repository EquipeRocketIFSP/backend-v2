package br.vet.certvet.validation;

import br.vet.certvet.models.Documento;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class DocumentoValidation implements Validation {

    private static final Logger log = LoggerFactory.getLogger(DocumentoValidation.class);

    @Override
    public String getName() {
        return "DocumentoValidation";
    }

    @Override
    public Predicate<byte[]> getPredicate() {
        log.debug("DocumentoValidation - validando se o documento está sendo criado corretamente");
        return Objects::isNull;
    }

    @Override
    public Exception getException() {
        return new Exception("Mensagem de erro na criação do documento");
//        splitar entre exception de documento não criado e documento criado errado?
    }

}
