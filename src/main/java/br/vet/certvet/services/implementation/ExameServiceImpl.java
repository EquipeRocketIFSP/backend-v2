package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.prontuario.exame.ExameListDTO;
import br.vet.certvet.enums.ProntuarioStatus;
import br.vet.certvet.models.Exame;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.models.TipoExame;
import br.vet.certvet.models.factories.ExameFactory;
import br.vet.certvet.repositories.ProntuarioRepository;
import br.vet.certvet.repositories.TipoExameRepository;
import br.vet.certvet.services.ExameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class ExameServiceImpl implements ExameService {
    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @Autowired
    private TipoExameRepository tipoExameRepository;

    @Override
    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    public List<Exame> assignToProntuario(ExameListDTO dto, Prontuario prontuario) {
        if (prontuario.getStatus() == ProntuarioStatus.COMPLETED)
            prontuario.setStatus(ProntuarioStatus.UPDATING);

        List<TipoExame> tipoExames = this.tipoExameRepository.findAllParent();

        List<Exame> exames = dto.getExames()
                .stream()
                .map(exameDTO -> {
                    TipoExame selectedTipoExame = tipoExames.stream()
                            .filter(tipoExame -> tipoExame.getNome().equals(exameDTO.getTipoExame()))
                            .findFirst()
                            .orElseThrow();

                    if (!selectedTipoExame.getFilhos().isEmpty()) {
                        selectedTipoExame = selectedTipoExame.getFilhos()
                                .stream()
                                .filter(tipoExame -> tipoExame.getNome().equals(exameDTO.getSubTipoExame()))
                                .findFirst()
                                .orElseThrow();
                    }

                    return ExameFactory.factory(exameDTO, prontuario).setTipoExame(selectedTipoExame);
                })
                .toList();

        prontuario.getExames().clear();
        prontuario.getExames().addAll(exames);

        return this.prontuarioRepository.saveAndFlush(prontuario).getExames();
    }
}
