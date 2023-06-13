package br.vet.certvet.dto.responses;

import lombok.Getter;

import java.util.Optional;

@Getter
public class Metadata {
    private static final String PAGINA_PARAM = "?pagina=";
    private Long total;
    private int limit;
    private int page;
    private int totalPages;
    private String first;
    private String last;
    private Optional<String> prev;
    private Optional<String> next;

    public Metadata(String pathname, int page, int limit, Long total) {

        this.page = page;
        this.totalPages = (int) Math.ceil((float) total / (float) limit);
        this.limit = limit;
        this.total = total;

        this.first = pathname + PAGINA_PARAM + 1;
        this.last = pathname + PAGINA_PARAM + totalPages;
        this.next = (page + 1) > totalPages ? Optional.empty() : Optional.of(pathname + PAGINA_PARAM + (page + 1));
        this.prev = (page - 1) <= 0 ? Optional.empty() : Optional.of(pathname + PAGINA_PARAM + (page - 1));
    }
}
