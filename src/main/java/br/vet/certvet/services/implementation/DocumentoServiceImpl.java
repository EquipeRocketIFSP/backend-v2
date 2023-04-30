package br.vet.certvet.services.implementation;

import br.vet.certvet.exceptions.DocumentLayoutNotFound;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.especializacoes.*;
import br.vet.certvet.repositories.DocumentoRepository;
import br.vet.certvet.services.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentoServiceImpl implements DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Override
    public Documento provideLayout(String layout) throws DocumentLayoutNotFound {

        if("sanitario".equals(layout))
            return new SanitarioDocumento();
        if("obito".equals(layout))
            return new ObitoDocumento();
        if("exames".equals(layout))
            return new ExameDocumento();
        if("terapeutico".equals(layout))
            return new TerapeuticoDocumento();
        if("retiraCorpo".equals(layout))
            return new RetiraCorpoDocumento();
        if("cirurgia".equals(layout))
            return new CirurgiaDocumento();
        if("tratamentoClinico".equals(layout))
            return new TratamentoClinicoDocumento();
        if("anestesia".equals(layout))
            return new AnestesiaDocumento();
        if("eutanasia".equals(layout))
            return new EutanasiaDocumento();
        if("retiradaSemAlta".equals(layout))
            return new RetiraSemAltaDocumento();
        if("vacinacao".equals(layout))
            return new VacinacaoDocumento();
        if("doacaoPesquisa".equals(layout))
            return new DoacaoPesquisaDocumento();

        throw new DocumentLayoutNotFound("Não foi possível identificar o layout do documento solicitado");
    }

    @Override
    public Optional<Documento> getByCodigo(String documentoCodigo) {
        return documentoRepository.findByCodigo(documentoCodigo);
    }
}
