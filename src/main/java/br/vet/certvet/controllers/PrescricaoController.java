package br.vet.certvet.controllers;

import br.vet.certvet.dto.requests.prontuario.MedicacaoPrescritaDTO;
import br.vet.certvet.dto.requests.prontuario.MedicacaoPrescritaListDTO;
import br.vet.certvet.exceptions.AlreadyPrescribedException;
import br.vet.certvet.exceptions.ProntuarioNotFoundException;
import br.vet.certvet.models.Prescricao;
import br.vet.certvet.models.Procedimento;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.services.ProcedimentoService;
import br.vet.certvet.services.ProntuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/prontuario/prescricao")
@CrossOrigin
@Slf4j
public class PrescricaoController extends BaseController {
    private final ProcedimentoService procedimentoService;

    public PrescricaoController(
            ProcedimentoService procedimentoService
    ) {
        this.procedimentoService = procedimentoService;
    }

    @GetMapping("/{prontuario}")
    public ResponseEntity<MedicacaoPrescritaListDTO> getPrescricao(
            @PathVariable("prontuario") String prontuarioCodigo
    ){
        Prontuario prontuario = findProntuario(prontuarioCodigo);
        var prescricoes = prontuario.getProcedimentos()
                .stream()
                .filter(p -> p.getDescricao().equals("Medicação"))
                .flatMap(
                        procedimento -> procedimento.getPrescricao()
                                .stream()
                                .map(prescricao -> new MedicacaoPrescritaDTO()
                                        .of(prescricao)))
                .toList();
        return prescricoes.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(new MedicacaoPrescritaListDTO().of(prescricoes));
    }

    @PostMapping("/{prontuario}")
    public ResponseEntity<MedicacaoPrescritaListDTO> setPrescricao(
            @PathVariable("prontuario") String prontuarioCodigo,
            @RequestBody MedicacaoPrescritaListDTO medicacaoPrescritaList
    ){
        Prontuario prontuario = findProntuario(prontuarioCodigo);
        var prescritoPreviamente = medicacaoPrescritaList.getMedicacoesUtilizadas()
                .stream()
                .map(MedicacaoPrescritaDTO::translate)
                .toList();
        Set<Procedimento> jaPrescrito = new HashSet<>();
        for(Procedimento procedimento : prontuario.getProcedimentos()){
            for(Prescricao prescricao : procedimento.getPrescricao()){
                if(prescritoPreviamente.contains(prescricao))
                    jaPrescrito.add(procedimento);
            }
        }
        Procedimento procedimento = procedimentoService.savePrescricao(
                jaPrescrito.stream()
                        .findFirst()
                        .orElseThrow(()->new AlreadyPrescribedException("A medicacão prescrita já foi receitada neste prontuário.")),
                medicacaoPrescritaList.translate()
        );
        return ResponseEntity.ok(
                new MedicacaoPrescritaListDTO().of(
                        procedimento.getPrescricao()
                                .stream()
                                .map(p -> new MedicacaoPrescritaDTO().of(p))
                                .toList()
                )
        );
    }

    @DeleteMapping("/{prontuario}")
    public ResponseEntity<String> deletePrescricao(
            @PathVariable("prontuario") String prontuarioCodigo,
            @RequestBody MedicacaoPrescritaListDTO medicacaoPrescritaList
    ){
        var prescritoARemover = medicacaoPrescritaList.getMedicacoesUtilizadas()
                .stream()
                .map(MedicacaoPrescritaDTO::translate)
                .toList();
        Prontuario prontuario = findProntuario(prontuarioCodigo);

//        prontuario.getProcedimentos()
//                .forEach(procedimento -> procedimento.getPrescricao()
//                        .stream()
//                        .map(prescricao -> {
//                            if(prescritoARemover.contains(prescricao))
//                                prescricao.delete();
//                            return prescricao;
//                        })
//                        .collect(Collectors.toList())
//                );
        List<Procedimento> excluidoEm = new ArrayList<>();
        for(Procedimento procedimento : prontuario.getProcedimentos()){
            for(Prescricao prescricao : procedimento.getPrescricao()){
                if(prescritoARemover.contains(prescricao)) {
                    prescricao.delete();
                    excluidoEm.add(procedimento);
                }
            }
        }

        var excluidos = excluidoEm.stream()
                .map(procedimentoService::save)
                .flatMap(procedimento -> procedimento.getPrescricao()
                        .stream()
                        .map(Prescricao::getDataExclusao))
                .map(LocalDate::toString)
                .toList();
        return ResponseEntity.ok("Removido com sucesso em: " + excluidos );
    }




}
