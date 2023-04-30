package br.vet.certvet.services.implementation;

import br.vet.certvet.repositories.PdfRepository;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class S3BucketServiceRepository implements PdfRepository {

    private static final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.SA_EAST_1)
            .build();
    private static final String SUCESSFULLY_SAVED = "Arquivo salvo com sucesso: ";

    @Value("app.temp.path")
    private static String RESOURCE_PATH;

    @Override
    public byte[] retrieveObject(String cnpj, String keyName) throws IOException {
        String bucketName = getConventionedBucketName(cnpj);
        byte[] arr = null;
        if(!s3.doesBucketExistV2(bucketName)){
            s3.createBucket(bucketName);
        }
        try {
            arr = s3.getObject(bucketName, keyName)
                    .getObjectContent()
                    .readAllBytes();
        } catch (AmazonS3Exception e) {
            log.error(e.getLocalizedMessage());
        }
        if (arr == null) {
            log.error("Nenhum arquivo identificado para o nome: " + keyName);
            return null;
        }
        return arr;
    }

    @Override
    public Boolean setPublicFileReadingPermission(String bucketName, String keyName, Boolean allow) {

        try {
            AccessControlList acl = s3.getObjectAcl(bucketName, keyName);
            if(allow) {
                acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
            } else{
                acl.revokeAllPermissions(GroupGrantee.AllUsers);
            }
            s3.setObjectAcl(bucketName, keyName, acl);
            return Boolean.TRUE;
        } catch (AmazonS3Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return Boolean.FALSE;
    }

    @Override
    public PutObjectResult putObject(String cnpj, String keyName, byte[] fileBinary) {
        final String bucketName = getConventionedBucketName(cnpj);
        log.info("bucketName: " + bucketName + ", keyName: " + keyName);
        try {
            if (s3.doesObjectExist(bucketName, keyName)) {
                log.info("Arquivo identificado: " + keyName + ". Não será gravado");
                return null;
            }
        } catch (AmazonS3Exception e) {
            log.warn("Arquivo não identificado. Gravando...");
        }
        try {
            log.debug("Persistindo o arquivo pdf");
            return s3.putObject(bucketName, keyName, new ByteArrayInputStream(fileBinary), getObjectMetadata(fileBinary));
        } catch (AmazonServiceException e) {
            log.error(e.getErrorMessage());
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }

    private static ObjectMetadata getObjectMetadata(byte[] storedFile) throws IOException {
        ObjectMetadata o = new ObjectMetadata();
        o.setContentType("application/pdf");
        o.setContentLength((long) storedFile.length);
        return o;
    }

    public static String getConventionedBucketName(String cnpj) {
        return cnpj.replace("/", ".").toLowerCase();
    }

}
