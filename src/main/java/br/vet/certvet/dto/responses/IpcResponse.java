package br.vet.certvet.dto.responses;

import java.util.List;

public record IpcResponse(
        IpcReport report,
        String receipt,
        IpcHealthInfo healthInfo
){}
record IpcReport(
        IpcReportDate date,
        String generalStatus,
        Integer number,
        IcpReportSoftware software,
        IcpReportInitialReport initialReport,
        Boolean onlyVerifyAnchored,
        Boolean extendedReport,
        IcpReportSignatures signatures
){}

record IpcHealthInfo(
        IpcHealthInfoPrescriber prescriber,
        String documentHash,
        Boolean dispensed,
        IpcHealthInfoForm form,
        IpcHealthInfoSoftware software,
        Boolean validDocument,
        String documentType,
        List<IpcHealthInfoErrorCodes> errorCodes,
        IpcHealthInfoPharmacist pharmacist,
        String documentStatus,
        IpcHealthInfoDispensationReceipt dispensationReceipt
){}

record IpcHealthInfoForm(
        IpcEntry entry,
        String medicationDispenseString
){}

record IpcEntry(
    String cpf_second_signer,
    String cpf_first_signer,
    String signed_date_first_signer,
    String signed_date_second_signer
){}
record IpcReportDate(
        String sourceOfDate,
        IpcDate verificationDate
){}

record IpcDate(
        String ipcDate
){}

record IcpReportSoftware(
        String name,
        String sourceFileHash,
        String version,
        String sourceFile
){}

record IcpReportInitialReport(
        Integer qtdAnchorsSign,
        Integer qtdSignatures,
        String fileType
){}

record IcpReportSignatures(
        List<IpcReportSignature> signature
){}

record IpcReportSignature(
        Boolean containsMandatedCertificates,
        Boolean attributeValid,
        String errorMessages,
        String warningMessages,
        Boolean hasInvalidUpdates,
        String signaturePolicy,
        IpcIntegrity integrity,
        String signatureType,
        IpcAttributes attributes,
        IpcPaRules paRules,
        IpcDate signingTime,
        IpcCertification certification
){}
record IpcIntegrity(
        Boolean schema,
        String messageDigest,
        String references,
        String asymmetricCipher,
        String schemaPattern,
        String hash
){}
record IpcAttributes(
        List<RequiredAttribute> requiredAttributes,
        String ignoredAttributes,
        IpcOptionalAttributes optionalAttributes,
        String extraAttributes
){}

record RequiredAttribute(
        String name,
        String status
){}
record IpcOptionalAttributes(
        List<OptionalAttribute> optionalAttribute
){}

record OptionalAttribute(
        String name,
        String status
){}
record IpcPaRules(
        String prohibited,
        String mandatedCertificateInfo,
        String required
){}
record IpcCertification(
        String timeStamps,
        IpcSigner signer
){}

record IpcSigner(
    IpcExtensions extensions,
    String validSignature,
    String form,
    List<IpcCertificate> certificate,
    String certPathValid,
    String flagCountry,
    Boolean present,
    String subjectName
){}

record IpcExtensions(
    IpcSubjectAlternativeNames subjectAlternativeNames
){}

record IpcSubjectAlternativeNames(
    IpcGeneralName generalName
){}

record IpcGeneralName(
        String name,
        String value
){}
record IpcCertificate(
        IpcDate notAfter,
        Boolean validSignature,
        Long serialNumber,
        Boolean expired,
        String issuerName,
        Boolean online,
        IpcCrl crl,
        Boolean revoked,
        IpcDate notBefore,
        String subjectName
){}

record IpcCrl(
    Boolean validSignature,
    Long serialNumber,
    String issuerName,
    Boolean online,
    IpcCrcDates dates
){}
record IpcCrcDates(
    IpcDate thisUpdate,
    IpcDate nextUpdate
){}

record IpcHealthInfoPrescriber(
        String profession,
        String UF,
        String signatureHash,
        String validSignature,
        String statusSigner,
        String cpf,
        String professionalCode
){}

record IpcHealthInfoSoftware(
        String name,
        String version
){}

record IpcHealthInfoErrorCodes(){}

record IpcHealthInfoPharmacist(
    String profession,
    String uf,
    String signatureHash,
    String validSignature,
    String statusSigner,
    String cpf,
    String professionalCode
){}

record IpcHealthInfoDispensationReceipt(
    Integer qtd_aviada,
    String tipo,
    String endereco,
    String nome_farmaceutico,
    String cpf_farmaceutico,
    String nome_farmacia,
    String cnpj,
    String data_sub,
    String hash_presc,
    String uf_dentista,
    String medicamentos,
    String crf_farmaceutico,
    Integer nassinaturas,
    String uf_medico,
    String uf_farmaceutico
){}