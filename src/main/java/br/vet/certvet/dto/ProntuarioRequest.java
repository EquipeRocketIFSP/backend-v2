package br.vet.certvet.dto;

import br.vet.certvet.models.*;

import java.time.LocalDateTime;
import java.util.List;

public record ProntuarioRequest (
        Clinica clinica,
        int frequenciaCardiaca,
        int frequenciaRespiratoria,
        byte temperatura,
        String hidratacao,
        String tpc,
        String mucosa,
        String conciencia,
        String escoreCorporal,
        String supeitaDiagnostica,
        boolean prostracao,
        boolean febre,
        boolean vomito,
        boolean diarreia,
        boolean espasmosConvulsao,
        boolean deambulacao,
        boolean sensibilidadeDor,
        boolean lesoesNodulos,
        String apetite,
        String linfonodos,
        String linfonodosObs,
        String regiaoColuna,
        String regiaoAbdomen,
        String regiaoMToracicos,
        String regiaoMPelvicos,
        boolean regiaoCabeca,
        boolean regiaoTorax,
        LocalDateTime dataAtendimento,
        Animal animal,
        Usuario veterinario,
        Cirurgia cirurgia,
        List<Procedimento> procedimentos,
        List<Exame> exames,
        String codigo,
        Usuario tutor,
        List<Documento> documentos
){
    public Prontuario convert(){
        return Prontuario.builder()
                .clinica(clinica)
                .frequenciaCardiaca(frequenciaCardiaca)
                .frequenciaRespiratoria(frequenciaRespiratoria)
                .temperatura(temperatura)
                .hidratacao(hidratacao)
                .tpc(tpc)
                .mucosa(mucosa)
                .conciencia(conciencia)
                .escoreCorporal(escoreCorporal)
                .supeitaDiagnostica(supeitaDiagnostica)
                .prostracao(prostracao)
                .febre(febre)
                .vomito(vomito)
                .diarreia(diarreia)
                .espasmosConvulsao(espasmosConvulsao)
                .deambulacao(deambulacao)
                .sensibilidadeDor(sensibilidadeDor)
                .lesoesNodulos(lesoesNodulos)
                .apetite(apetite)
//                .linfonodos(linfonodos)
                .linfonodosObs(linfonodosObs)
                .regiaoColuna(regiaoColuna)
                .regiaoAbdomen(regiaoAbdomen)
                .regiaoMToracicos(regiaoMToracicos)
                .regiaoMPelvicos(regiaoMPelvicos)
                .regiaoCabeca(regiaoCabeca)
                .regiaoTorax(regiaoTorax)
                .dataAtendimento(dataAtendimento)
                .animal(animal)
                .veterinario(veterinario)
                .cirurgia(cirurgia)
                .procedimentos(procedimentos)
                .exames(exames)
                .codigo(codigo)
                .tutor(tutor)
                .documentos(documentos)
                .build();
    }
}
