package br.vet.certvet.services;

import br.vet.certvet.contracts.apis.ipcBr.IpcResponse;
import br.vet.certvet.exceptions.DocumentoNotPersistedException;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;

@Service
public interface PdfService {

    byte[] writeProntuario(Prontuario prontuario) throws Exception;

    byte[] writePdfDocumentoEmBranco(Prontuario prontuario, Documento documento) throws DocumentoNotPersistedException, OptimisticLockingFailureException, IOException;

    byte[] retrieveFromRepository(Prontuario prontuario) throws IOException;

    byte[] setProtection(byte[] documento, Prontuario prontuario) throws IOException;

    IpcResponse getIcpBrValidation(Documento documento) throws IOException, SQLException, DocumentoNotPersistedException;

}
