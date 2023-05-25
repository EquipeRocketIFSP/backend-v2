package br.vet.certvet.services.implementation;

import br.vet.certvet.repositories.PdfRepository;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
public class S3BucketServiceRepository implements PdfRepository {
final static private String OPEN_POLICY = """
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "PublicReadGetObject",
            "Effect": "Allow",
            "Principal": "*",
            "Action": ["s3:GetObject"],
            "Resource": ["arn:aws:s3:::${bucketName}/*"]
        }
    ]
}
""";

    private static final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.SA_EAST_1)
            .build();
    private static final String SUCESSFULLY_SAVED = "Arquivo salvo com sucesso: ";

    @Value("app.temp.path")
    private static String RESOURCE_PATH;

    @Override
    public Optional<byte[]> retrieveObject(String cnpj, String keyName) throws IOException {
        String bucketName = getConventionedBucketName(cnpj);
        byte[] arr = null;
        try {
            if(!s3.doesBucketExistV2(bucketName)){
                s3.createBucket(bucketName);
            }
            arr = s3.getObject(bucketName, keyName)
                    .getObjectContent()
                    .readAllBytes();
        } catch (AmazonS3Exception e) {
            logAmazonError(e);
        }
//        log.error("Nenhum arquivo identificado para o nome: " + keyName);
        return Optional.ofNullable(arr);
    }

    @Override
    public Boolean setPublicFileReadingPermission(String bucketName, Boolean allow) {

        try {
            if(allow) {
                s3.setBucketPolicy(bucketName, new StringSubstitutor(Map.of("bucketName", bucketName)).replace(OPEN_POLICY));
            } else{
                s3.deleteBucketPolicy(bucketName);
            }
            return Boolean.TRUE;
        } catch (AmazonS3Exception e) {
            logAmazonError(e);
        }
        return Boolean.FALSE;
    }

    @Override
    public boolean exists(String cnpj, String fileName) {
        final String bucketName = getConventionedBucketName(cnpj);
        try {
            setPublicFileReadingPermission(bucketName, true);
            return s3.doesObjectExist(bucketName, fileName);
        } finally {
            setPublicFileReadingPermission(bucketName, false);
        }
    }

    @Override
    public ObjectMetadata putObject(String cnpj, String keyName, byte[] fileBinary) {
        final String bucketName = getConventionedBucketName(cnpj);
        log.info("bucketName: " + bucketName + ", keyName: " + keyName);
        try{
            if(!s3.doesBucketExistV2(bucketName)){
                s3.createBucket(bucketName);
                var delete = new DeletePublicAccessBlockRequest();
                delete.setBucketName(bucketName);
                s3.deletePublicAccessBlock(delete);
            }
        } catch (AmazonS3Exception e) {
            log.error("Erro na criação do Bucket");
            logAmazonError(e);
        }

        try {
            setPublicFileReadingPermission(bucketName, true);
            log.debug("s3.doesObjectExist(bucketName, keyName): " + s3.doesObjectExist(bucketName, keyName));
            if (s3.doesObjectExist(bucketName, keyName)) {
                log.info("Arquivo identificado: " + keyName + ". Não será gravado");
                log.debug("s3.getObject(bucketName, keyName): " + s3.getObject(bucketName, keyName));
                return s3.getObject(bucketName, keyName).getObjectMetadata();
            }
        } catch (AmazonS3Exception e) {
            log.warn("Arquivo não identificado. Gravando...");
            logAmazonError(e);
        } finally {
            setPublicFileReadingPermission(bucketName, false);
        }
        try {
            log.debug("Persistindo o arquivo pdf");
            return s3.putObject(bucketName, keyName, new ByteArrayInputStream(fileBinary), getObjectMetadata(fileBinary)).getMetadata();
        } catch (AmazonServiceException | IOException e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }

    private static void logAmazonError(AmazonS3Exception e) {
        log.error("Error Message:    " + e.getMessage());
        log.error("HTTP Status Code: " + e.getStatusCode());
        log.error("AWS Error Code:   " + e.getErrorCode());
        log.error("Error Type:       " + e.getErrorType());
        log.error("Request ID:       " + e.getRequestId());
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
