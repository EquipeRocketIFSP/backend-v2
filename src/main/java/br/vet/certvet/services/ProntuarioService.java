package br.vet.certvet.services;

import br.vet.certvet.exceptions.DocumentoNotFoundException;
import br.vet.certvet.exceptions.ProntuarioNotFoundException;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface ProntuarioService {
    Prontuario save(Prontuario prontuario);


    byte[] retrievePdfFromRepository(Prontuario prontuario) throws IOException;
    boolean exists(LocalDateTime dataAtendimento);

    Optional<Prontuario> getByCodigo(String codigo);

    Optional<Prontuario> findById(Long id);
    Optional<Prontuario> createProntuario(Prontuario prontuario);

    Optional<Prontuario> editProntuario(Prontuario prontuario);

    Boolean deleteProntuario(Prontuario prontuario);

    byte[] getProntuarioPdfBy(Long id);
    byte[] getProntuarioPdfBy(Prontuario prontuario);

    List<Documento> getDocumentosFromProntuarioByTipo(String prontuarioId, String tipo);

    Prontuario attachDocumentoAndPdfPersist(String prontuarioCodigo, String documentoCodigo, byte[] documento) throws SQLException;

    Optional<Prontuario> findByCodigo(String codigo);
}
