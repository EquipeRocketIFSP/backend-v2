package br.vet.certvet.dto.responses;

import java.util.List;

public class PaginatedResponse<DTO> {
    public Metadata meta;
    public List<DTO> data;

    public PaginatedResponse(Metadata meta, List<DTO> data) {
        this.meta = meta;
        this.data = data;
    }
}