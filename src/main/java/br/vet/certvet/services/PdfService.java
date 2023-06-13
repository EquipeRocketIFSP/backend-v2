package br.vet.certvet.services;

import br.vet.certvet.contracts.apis.ipc_br.IcpResponse;
import br.vet.certvet.exceptions.DocumentoNotPersistedException;
import br.vet.certvet.exceptions.ErroAoProcessarTipoDocumento;
import br.vet.certvet.models.Documento;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.models.especializacoes.Doc;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Optional;

@Service
public interface PdfService {

    default Boolean isFileTypePdf(byte[] file){
        try(InputStream inputStream = new ByteArrayInputStream(file)){
            String fileType = URLConnection.guessContentTypeFromStream(inputStream);
            return fileType.equals("application/pdf");
        } catch (IOException e) {
            throw new ErroAoProcessarTipoDocumento(e);
        }
    }

    byte[] writeProntuario(Prontuario prontuario);

    byte[] writePdfDocumentoEmBranco(Prontuario prontuario, Doc documentoTipo) throws DocumentoNotPersistedException, OptimisticLockingFailureException, IOException;

    byte[] retrieveFromRepository(Prontuario prontuario) throws IOException;

    byte[] setProtection(byte[] documento, Prontuario prontuario) throws IOException;
    IcpResponse getIcpBrValidation(final String bucket, final String fileName);

    ObjectMetadata saveDocumentoPdfInBucket(Documento documento, int version, byte[] documentoPdf);

    Optional<byte[]> getPrescricaoPdf(Prontuario prontuario, int version);

    Optional<byte[]> writePrescricao(Prontuario prontuario);

    ObjectMetadata savePrescricaoPdfInBucket(Prontuario prontuario, int version, byte[] medicacaoPrescritaPdf);
}
