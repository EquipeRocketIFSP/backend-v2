package br.vet.certvet.services;

import br.vet.certvet.contracts.apis.ipcBr.IcpResponse;
import br.vet.certvet.exceptions.DocumentoNotPersistedException;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.models.especializacoes.Doc;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@Service
public interface PdfService {

    byte[] writeProntuario(Prontuario prontuario) throws Exception;

    byte[] writePdfDocumentoEmBranco(Prontuario prontuario, Doc documento) throws DocumentoNotPersistedException, OptimisticLockingFailureException, IOException;

    byte[] retrieveFromRepository(Prontuario prontuario) throws IOException;

    byte[] setProtection(byte[] documento, Prontuario prontuario) throws IOException;

    IcpResponse getIcpBrValidation(Documento documento) throws IOException, SQLException, DocumentoNotPersistedException;

    ObjectMetadata savePdfInBucket(Documento documento, byte[] documentoPdf);

    Optional<byte[]> getPrescricaoPdf(Prontuario prontuario);
}
