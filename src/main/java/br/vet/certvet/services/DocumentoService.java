package br.vet.certvet.services;

import br.vet.certvet.exceptions.DocumentLayoutNotFound;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.especializacoes.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public interface DocumentoService {
    default Doc provideLayout(String layout) throws DocumentLayoutNotFound{
        Documento documento = new Documento(LocalDateTime.now(), layout);

        if("sanitario".equals(layout))
            return new SanitarioDocumento(documento);
        if("obito".equals(layout))
            return new ObitoDocumento(documento);
        if("exames".equals(layout))
            return new ExameDocumento(documento);
        if("terapeutico".equals(layout))
            return new TerapeuticoDocumento(documento);
        if("retiraCorpo".equals(layout))
            return new RetiraCorpoDocumento(documento);
        if("cirurgia".equals(layout))
            return new CirurgiaDocumento(documento);
        if("tratamentoClinico".equals(layout))
            return new TratamentoClinicoDocumento(documento);
        if("anestesia".equals(layout))
            return new AnestesiaDocumento(documento);
        if("eutanasia".equals(layout))
            return new EutanasiaDocumento(documento);
        if("retiradaSemAlta".equals(layout))
            return new RetiraSemAltaDocumento(documento);
        if("vacinacao".equals(layout))
            return new VacinacaoDocumento(documento);
        if("doacaoPesquisa".equals(layout))
            return new DoacaoPesquisaDocumento(documento);
        throw new DocumentLayoutNotFound("Não foi possível identificar o layout do documento solicitado");
    }

    Optional<Documento> getByCodigo(String documentoCodigo);

    Documento save(Documento documento);
}
