package br.vet.certvet.services.implementation;

import br.vet.certvet.exceptions.AwsS3ReadException;
import br.vet.certvet.exceptions.AwsS3WritingException;
import br.vet.certvet.repositories.PdfRepository;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeletePublicAccessBlockRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
public class S3BucketServiceRepository implements PdfRepository {
    private static final String OPEN_POLICY = """
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
    private static final String SUCESSFULLY_SAVED = "Arquivo salvo com sucesso no bucket: ";

    @Override
    public Optional<byte[]> retrieveObject(String cnpj, String keyName) throws IOException {
        String bucketName = getConventionedBucketName(cnpj);
        byte[] arr = null;
        try {
            setPublicFileReadingPermission(bucketName,true);
            if(!s3.doesBucketExistV2(bucketName)){
                s3.createBucket(bucketName);
                var delete = new DeletePublicAccessBlockRequest();
                delete.setBucketName(bucketName);
                s3.deletePublicAccessBlock(delete);
            }
            arr = s3.getObject(bucketName, keyName)
                    .getObjectContent()
                    .readAllBytes();
            log.info(SUCESSFULLY_SAVED + bucketName);
        } catch (AmazonS3Exception e) {
            logAmazonError(e);
        }
        log.error("Nenhum arquivo identificado para o nome: " + keyName);
        return Optional.ofNullable(arr);
    }

    @Override
    public Boolean setPublicFileReadingPermission(String bucketName, Boolean allow) {

        try {
            if(Boolean.TRUE.equals(allow)) {
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
        try {
            if(!s3.doesBucketExistV2(bucketName)){
                s3.createBucket(bucketName);
                var delete = new DeletePublicAccessBlockRequest();
                delete.setBucketName(bucketName);
                s3.deletePublicAccessBlock(delete);
            }
        } catch (AmazonServiceException e) {
            log.error("Erro na criação do Bucket");
            logAmazonError(e);
        }

        try {
            setPublicFileReadingPermission(bucketName, true);
            boolean exists = s3.doesObjectExist(bucketName, keyName);
            log.debug("s3.doesObjectExist(bucketName, keyName): " + exists);
            if (exists) {
                log.info("Arquivo identificado: " + keyName + ". Não será gravado");
                var object = s3.getObject(bucketName, keyName);
                log.debug("s3.getObject(bucketName, keyName): " + object);
                return object.getObjectMetadata();
            }
        } catch (AmazonServiceException e) {
            log.warn("Arquivo não identificado. Gravando...");
            logAmazonError(e);
            throw new AwsS3ReadException(e.getLocalizedMessage(), e);
        } finally {
            setPublicFileReadingPermission(bucketName, false);
        }
        try {
            log.debug("Persistindo o arquivo pdf");
            return s3.putObject(bucketName, keyName, new ByteArrayInputStream(fileBinary), getObjectMetadata(fileBinary)).getMetadata();
        } catch (AmazonServiceException  e) {
            log.error(e.getLocalizedMessage());
            logAmazonError(e);
            throw new AwsS3WritingException(e.getLocalizedMessage(), e);
        }
    }

    private static void logAmazonError(AmazonServiceException e) {

        log.error("Error Message:    " + e.getMessage());
        log.error("HTTP Status Code: " + e.getStatusCode());
        log.error("AWS Error Code:   " + e.getErrorCode());
        log.error("Error Type:       " + e.getErrorType());
        log.error("Request ID:       " + e.getRequestId());
    }

    private static ObjectMetadata getObjectMetadata(byte[] storedFile) {
        ObjectMetadata o = new ObjectMetadata();
        o.setContentType("application/pdf");
        o.setContentLength(storedFile.length);
        return o;
    }

    public static String getConventionedBucketName(String cnpj) {
        return cnpj.replace("/", ".").toLowerCase();
    }

}
