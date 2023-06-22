package br.vet.certvet.models.mappers;

import br.vet.certvet.dto.requests.prontuario.ManifestacoesClinicasDTO;
import br.vet.certvet.dto.requests.prontuario.ProntuarioDTO;
import br.vet.certvet.dto.requests.prontuario.SinaisVitaisDTO;
import br.vet.certvet.dto.requests.prontuario.SuspeitaDiagnosticaDTO;
import br.vet.certvet.models.ManifestacoesClinicas;
import br.vet.certvet.models.Prontuario;

public class ProntuarioDTOMapper {

    private ProntuarioDTOMapper(){}
    public static Prontuario assignToModel(ProntuarioDTO dto, Prontuario prontuario) {
        return DTOMapper.assignToModel(dto, prontuario, ProntuarioDTOMapper.class);
    }

    protected static Prontuario mapSinaisVitaisDTO(SinaisVitaisDTO dto, Prontuario prontuario) {
        return prontuario.setFrequenciaCardiaca(dto.getFrequenciaCardiaca())
                .setFrequenciaRespiratoria(dto.getFrequenciaRespiratoria())
                .setTemperatura(dto.getTemperatura())
                .setPeso(dto.getPeso())
                .setHidratacao(dto.getHidratacao())
                .setTpc(dto.getTpc())
                .setMucosa(dto.getMucosa())
                .setConciencia(dto.getConciencia())
                .setEscoreCorporal(dto.getEscoreCorporal());
    }

    protected static Prontuario mapSuspeitaDiagnosticaDTO(SuspeitaDiagnosticaDTO dto, Prontuario prontuario) {
        return prontuario.setSupeitaDiagnostica(dto.getSupeitaDiagnostica());
    }

    protected static Prontuario mapManifestacoesClinicasDTO(ManifestacoesClinicasDTO dto, Prontuario prontuario) {
        final ManifestacoesClinicas manifestacoesClinicas = new ManifestacoesClinicas()
                .setProntuario(prontuario)
                .setProstracao(dto.isProstracao())
                .setFebre(dto.isFebre())
                .setVomito(dto.isVomito())
                .setDiarreia(dto.isDiarreia())
                .setEspasmosConvulsao(dto.isEspasmosConvulsao())
                .setDeambulacao(dto.isDeambulacao())
                .setSensibilidadeDor(dto.isSensibilidadeDor())
                .setLesoesNodulos(dto.isLesoesNodulos())
                .setRegiaoCabeca(dto.isCabeca())
                .setRegiaoTorax(dto.isTorax())
                .setRegioesObs(dto.getRegioesObs());
//                .setLinfonodos(dto.getLinfonodos())
                //.setLinfonodosObs(dto.getLinfonodosObs());

        prontuario.setManifestacoesClinicas(manifestacoesClinicas);
    /**/

        /*if (dto.getMToracicos().length != 0)
            prontuario.setRegiaoMToracicos(String.join(";", dto.getMToracicos()));
        else prontuario.setRegiaoMToracicos(null);

        if (dto.getMPelvicos().length != 0)
            prontuario.setRegiaoMPelvicos(String.join(";", dto.getMPelvicos()));
        else prontuario.setRegiaoMPelvicos(null);

        if (dto.getColuna().length != 0)
            prontuario.setRegiaoColuna(String.join(";", dto.getColuna()));
        else prontuario.setRegiaoColuna(null);

        if (dto.getAbdomen().length != 0)
            prontuario.setRegiaoAbdomen(String.join(";", dto.getAbdomen()));
        else prontuario.setRegiaoAbdomen(null);*/

        return prontuario;
    }
}
