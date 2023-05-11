package br.vet.certvet.services;

import br.vet.certvet.dto.requests.prontuario.ProntuarioDTO;
import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.models.Usuario;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface ProntuarioService {

    Prontuario create(ProntuarioDTO dto, Animal animal, Usuario tutor, Usuario veterinario);

    Prontuario edit(ProntuarioDTO dto, Prontuario prontuario);

    Prontuario findOne(Long id, Animal animal);

    Prontuario save(Prontuario prontuario);

    Optional<byte[]> retrievePdfFromRepository(Prontuario prontuario) throws IOException;

    boolean exists(LocalDateTime dataAtendimento);

    Optional<Prontuario> findById(Long id);

    Boolean deleteProntuario(Prontuario prontuario);

    byte[] getProntuarioPdfBy(Long id);

    byte[] getProntuarioPdfBy(Prontuario prontuario);

    List<Documento> getDocumentosFromProntuarioByTipo(String prontuarioId, String tipo);

    Documento attachDocumentoAndPdfPersist(Documento documento, ObjectMetadata pdf) throws SQLException;

    Optional<Prontuario> findByCodigo(String codigo);
}
