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


    byte[] retrieveFromRepository(Prontuario prontuario) throws IOException;
    boolean exists(LocalDateTime dataAtendimento);

    Optional<String> findByCertvetProntuario(String certvetCode);

    Optional<List<Prontuario>> getByCodigo(String codigo);

    Optional<Prontuario> findById(Long id);
    Optional<Prontuario> createProntuario(Prontuario prontuario);

    Optional<Prontuario> editProntuario(Prontuario prontuario);

    Optional<Prontuario> getProntuarioById(Long id);

    Boolean deleteProntuario(Prontuario prontuario);

    byte[] getProntuarioPdfBy(Long id);
    byte[] getProntuarioPdfBy(Prontuario prontuario);

    List<Documento> getDocumentosByTipo(Long prontuarioId, String tipo);

    Documento addDocumento(Long prontuarioId, Long documentoId, byte[] documento, String tipo) throws ProntuarioNotFoundException, DocumentoNotFoundException, SQLException;
}
