package br.vet.certvet.validation;

import br.vet.certvet.models.Documento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.SortedSet;
import java.util.function.Predicate;

public final class ValidationExecutor {

    private static final Logger logger = LoggerFactory.getLogger(ValidationExecutor.class);

    private final SortedSet<Validation> validations;

    public ValidationExecutor(SortedSet<Validation> validations) {
        this.validations = validations;
    }

    public void validate(byte[] documento) throws Exception {
        for (Validation validation : validations) {
            logger.debug("Executando a validação: ", validation.getName());
            Predicate<byte[]> predicate = validation.getPredicate();
            if (predicate.test(documento)) {
                throw validation.getException();
            }
        }
    }


}
