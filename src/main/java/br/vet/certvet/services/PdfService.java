package br.vet.certvet.services;

import br.vet.certvet.contracts.apis.ipcBr.IcpResponse;
import br.vet.certvet.exceptions.DocumentoNotPersistedException;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prescricao;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.models.especializacoes.Doc;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public interface PdfService {

    byte[] writeProntuario(Prontuario prontuario) throws Exception;

    byte[] writePdfDocumentoEmBranco(Prontuario prontuario, Doc documento) throws DocumentoNotPersistedException, OptimisticLockingFailureException, IOException;

    byte[] retrieveFromRepository(Prontuario prontuario) throws IOException;

    byte[] setProtection(byte[] documento, Prontuario prontuario) throws IOException;

    IcpResponse getIcpBrValidation(final String bucket, final String fileName) throws DocumentoNotPersistedException, IOException;

    ObjectMetadata saveDocumentoPdfInBucket(Documento documento, int version, byte[] documentoPdf);

    Optional<byte[]> getPrescricaoPdf(Prontuario prontuario, int version);

    Optional<byte[]> writePrescricao(Prontuario prontuario) throws IOException;

    ObjectMetadata savePrescricaoPdfInBucket(Prontuario prontuario, int version, byte[] medicacaoPrescritaPdf);
}
