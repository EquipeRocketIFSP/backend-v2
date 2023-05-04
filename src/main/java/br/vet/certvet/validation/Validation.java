package br.vet.certvet.validation;

import br.vet.certvet.models.Documento;
import java.util.function.Predicate;

public interface Validation {

    String getName();

    Predicate<byte[]> getPredicate();

    Exception getException();

//    se tiver mais de uma validação, isActive junto com compareTo (extends Comparable<>) e a prioridade de cada uma das classes dita a ordem

}
