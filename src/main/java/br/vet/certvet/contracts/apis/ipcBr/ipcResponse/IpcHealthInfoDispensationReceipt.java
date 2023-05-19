package br.vet.certvet.contracts.apis.ipcBr.ipcResponse;

import lombok.Builder;

@Builder
public record IpcHealthInfoDispensationReceipt(
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
) {
}
