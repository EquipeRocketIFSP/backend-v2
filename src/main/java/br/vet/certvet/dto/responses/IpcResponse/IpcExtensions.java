package br.vet.certvet.dto.responses.IpcResponse;

import IpcSubjectAlternativeNames;
import lombok.Builder;

@Builder
public record IpcExtensions(
        IpcSubjectAlternativeNames subjectAlternativeNames
) {
}
