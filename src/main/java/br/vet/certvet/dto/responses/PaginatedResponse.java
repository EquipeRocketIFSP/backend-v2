package br.vet.certvet.dto.responses;

import lombok.Getter;

import java.util.List;

/**
 *
 * @param <D> Dto
 */
@Getter
public class PaginatedResponse<D> {
    private final Metadata meta;
    private final List<D> data;

    public PaginatedResponse(List<D> data) {
        this.meta = null;
        this.data = data;
    }

    public PaginatedResponse(Metadata meta, List<D> data) {
        this.meta = meta;
        this.data = data;
    }
}