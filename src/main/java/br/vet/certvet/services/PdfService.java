package br.vet.certvet.services;

import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.models.Usuario;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface PdfService {

    byte[] writeProntuario(Prontuario prontuario) throws Exception;

    byte[] writeDocumento(Prontuario prontuario, Documento documento) throws Exception;

    byte[] retrieveFromRepository(Prontuario prontuario) throws IOException;

    default AccessPermission setAccessPermission() {
        AccessPermission accessPermission = new AccessPermission();
        accessPermission.setCanModify(true);
        accessPermission.isOwnerPermission();
        return accessPermission;
    }
}
