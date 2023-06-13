package br.vet.certvet.dto.responses;

import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Usuario;

import java.util.Date;
import java.util.List;

public record DocumentoResponse(
        String tipo,
        String caminho,
        String codigo,
        Integer versao,
        Date criadoEm,
        String veterinario,
        List<String> assinadores,
        String prontuarioCodigo
) {
    public DocumentoResponse(Documento documento){
        this(
            documento.getTipo(),
            documento.getCaminhoArquivo(),
            documento.getCodigo(),
            documento.getVersao(),
            documento.getCriadoEm(),
            documento.getVeterinario().getNome(),
            documento.getAssinadores()
                    .stream()
                    .map(Usuario::getNome)
                    .toList(),
            documento.getProntuario().getCodigo()
        );
    }
}
