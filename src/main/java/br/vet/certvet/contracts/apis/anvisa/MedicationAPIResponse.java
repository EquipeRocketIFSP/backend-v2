package br.vet.certvet.contracts.apis.anvisa;

import java.util.HashMap;
import java.util.List;

public record MedicationAPIResponse(
        int codigoProduto,
        int tipoProduto,
        String dataProduto,
        String nomeComercial,
        String numeroRegistro,
        String dataVencimento,
        String mesAnoVencimento,
        String codigoParecerPublico,
        String codigoBulaPaciente,
        String codigoBulaProfissional,
        String principioAtivo,
        String medicamentoReferencia,
        String dataVencimentoRegistro,
        String categoriaRegulatoria,
        String classeTerapeutica,
        String atc,
        Object processosMedidaCautelar,
        Object processo,
        Object rotulos,
        Object anexoRotulos,
        HashMap<String, String> empresa,
        List<HashMap<String, Object>> apresentacoes,
        List<String> classesTerapeuticas
) {
}
