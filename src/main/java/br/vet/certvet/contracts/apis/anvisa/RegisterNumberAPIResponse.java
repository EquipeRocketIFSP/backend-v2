package br.vet.certvet.contracts.apis.anvisa;

import java.util.HashMap;
import java.util.List;

public record RegisterNumberAPIResponse(
        List<HashMap<String, Object>> content,
        int totalPages,
        int totalElements,
        boolean last,
        int numberOfElements,
        int size,
        int number,
        boolean first,
        Object sort
) {
}
