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
                .setImagemRegiaoCervical(dto.getCervical())
                .setImagemRegiaoAbdomen(dto.getAbdomen())
                .setImagemRegiaoCabeca(dto.isCabeca())
                .setImagemRegiaoTorax(dto.isTorax());

        if (dto.getMToracicos().length != 0)
            model.setImagemRegiaoMToracicos(String.join(";", dto.getMToracicos()));
        else model.setImagemRegiaoMToracicos(null);

        if (dto.getMPelvicos().length != 0)
            model.setImagemRegiaoMPelvicos(String.join(";", dto.getMPelvicos()));
        else model.setImagemRegiaoMPelvicos(null);

        return model;
    }
}
