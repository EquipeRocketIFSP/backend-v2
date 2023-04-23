package br.vet.certvet.dto.responses.IpcResponse;

import lombok.Builder;

import java.util.List;


@Builder
public record IpcHealthInfo(
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
) {
}
