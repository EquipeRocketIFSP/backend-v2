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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/prontuario/prescricao")
@CrossOrigin
@Slf4j
public class PrescricaoController extends BaseController {

    private final ProntuarioService prontuarioService;
    private final ProcedimentoService procedimentoService;

    public PrescricaoController(
            ProntuarioService prontuarioService,
            ProcedimentoService procedimentoService
    ) {
        this.prontuarioService = prontuarioService;
        this.procedimentoService = procedimentoService;
    }

    @GetMapping("/{prontuario}")
    public ResponseEntity<MedicacaoPrescritaListDTO> getPrescricao(
            @PathVariable("prontuario") String prontuarioCodigo){
        Prontuario prontuario = prontuarioService.findByCodigo(prontuarioCodigo)
                .stream()
                .findFirst()
                .orElseThrow(ProntuarioNotFoundException::new);
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
        Prontuario prontuario = prontuarioService.findByCodigo(prontuarioCodigo)
                .stream()
                .findFirst()
                .orElseThrow(ProntuarioNotFoundException::new);
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
//
//        if(!jaPrescrito.isEmpty())
//            throwExceptionFromController(new AlreadyPrescribedException("A medicacão prescrita já foi receitada neste prontuário."));

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



}
