package br.vet.certvet.services.implementation;

import br.vet.certvet.exceptions.DocumentoNotFoundException;
import br.vet.certvet.exceptions.ProntuarioNotFoundException;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.repositories.PdfRepository;
import br.vet.certvet.repositories.ProntuarioRepository;
import br.vet.certvet.services.DocumentoService;
import br.vet.certvet.services.ProntuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProntuarioServiceImpl implements ProntuarioService {
    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @Autowired
    private PdfRepository pdfRepository;

    @Autowired
    private DocumentoService documentoService;

    private String getProntuarioName(Prontuario prontuario){
        return prontuario.getCodigo() + ".pdf";
    }

    @Override
    public Optional<Prontuario> findById(Long id) {
        return prontuarioRepository.findById(id);
    }

    @Override
    public byte[] retrieveFromRepository(Prontuario prontuario) throws IOException {
        return pdfRepository.retrieveObject(
                prontuario.getClinica().getCnpj(),
                getProntuarioName(prontuario)
        );
    }

    @Override
    public Optional<Prontuario> createProntuario(Prontuario prontuario) {
        return Optional.empty();
    }

    @Override
    public Optional<Prontuario> editProntuario(Prontuario prontuario) {
        return Optional.empty();
    }

    @Override
    public Optional<Prontuario> getProntuarioById(Long id) {
        return Optional.empty();
    }

    @Override
    public Boolean deleteProntuario(Prontuario prontuario) {
        return Boolean.FALSE;
    }

    @Override
    public byte[] getProntuarioPdfBy(Long id) {
        return new byte[0];
    }

    @Override
    public byte[] getProntuarioPdfBy(Prontuario prontuario) {
        return new byte[0];
    }

    @Override
    public List<Documento> getDocumentosByTipo(Long prontuarioId, String tipo) {
        return prontuarioRepository.findByIdAndDocumentos_tipo(prontuarioId, tipo);
    }

    @Override
    public Documento addDocumento(Long prontuarioId, Long documentoId, byte[] documento, String tipo) {
        var prontuario = prontuarioRepository.findById(prontuarioId)
                .orElseThrow(ProntuarioNotFoundException::new);
        var d = prontuario
                .getDocumentos()
                .stream()
                .filter(
                        doc -> doc.getId()
                                .equals(documentoId))
                .findFirst()
                .orElseThrow(DocumentoNotFoundException::new);
        prontuarioRepository.save(prontuario);
        return d;
    }
}
