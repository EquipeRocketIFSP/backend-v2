package br.vet.certvet.contracts.apis.ipcBr;

import br.vet.certvet.contracts.apis.ipcBr.ipcResponse.IpcHealthInfo;
import br.vet.certvet.contracts.apis.ipcBr.ipcResponse.IpcReport;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Builder
@Slf4j
public record IpcResponse(
        IpcReport report,
        String receipt,
        IpcHealthInfo healthInfo
){
    public boolean isValidDocument(){
        return healthInfo.validDocument();
    }
    public Map<String, Signer> getSigners(){
        final var format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Map<String, Signer> signers = new HashMap<>();
        signers.put("first", new Signer(healthInfo.form().entry().cpf_first_signer(), LocalDate.parse(healthInfo.form().entry().signed_date_first_signer(), format)));
        try {
            var second = new Signer(healthInfo.form().entry().cpf_second_signer(), LocalDate.parse(healthInfo.form().entry().signed_date_second_signer(), format));
            signers.put("second", second);
        } catch (NullPointerException e){
            log.info("Não há segundo assinador. Prosseguindo...");
        }
        return signers;
    }
}
