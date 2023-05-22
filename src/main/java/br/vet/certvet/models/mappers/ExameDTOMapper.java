package br.vet.certvet.models.mappers;

import br.vet.certvet.dto.requests.prontuario.exame.ExameDTO;
import br.vet.certvet.models.Exame;

public class ExameDTOMapper {
    public static Exame assignToModel(ExameDTO dto, Exame model) {
        model.setTipoExame(dto.getTipoExame())
                .setOutrosExames(dto.getExamesOutros())
                .setOutrosCitologia(dto.getCitologiaOutros())
                .setBioquimico(dto.getBioquimico())
                .setHematologia(dto.getHematologia())
                .setCitologia(dto.getCitologia())
                .setImagem(dto.getImagem())
                .setImagemRegiaoCabeca(dto.isCabeca())
                .setImagemRegiaoTorax(dto.isTorax())
                .setImagemObsRegioes(dto.getRegioesObs());

        model.setImagemRegiaoMToracicos(dto.getMToracicos() == null ? null : String.join(";", dto.getMToracicos()))
                .setImagemRegiaoMPelvicos(dto.getMPelvicos() == null ? null : String.join(";", dto.getMPelvicos()))
                .setImagemRegiaoAbdomen(dto.getAbdomen() == null ? null : String.join(";", dto.getAbdomen()))
                .setImagemRegiaoCervical(dto.getColuna() == null ? null : String.join(";", dto.getColuna()));

        return model;
    }
}
