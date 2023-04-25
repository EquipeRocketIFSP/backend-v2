package br.vet.certvet.services;

import br.vet.certvet.contracts.apis.ipcBr.IpcResponse;
import br.vet.certvet.exceptions.DocumentoNotPersistedException;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface PdfService {

    byte[] writeProntuario(Prontuario prontuario) throws Exception;

    byte[] writeDocumento(Prontuario prontuario, Documento documento) throws DocumentoNotPersistedException, OptimisticLockingFailureException, IOException;

    byte[] retrieveFromRepository(Prontuario prontuario) throws IOException;

    IpcResponse getValidation(Documento documento) throws IOException, DocumentoNotPersistedException;

    default AccessPermission setAccessPermission() {
        AccessPermission accessPermission = new AccessPermission();
        accessPermission.setCanModify(true);
        accessPermission.isOwnerPermission();
        return accessPermission;
    }
}
