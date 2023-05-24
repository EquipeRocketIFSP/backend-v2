package br.vet.certvet.controllers;

import br.vet.certvet.dto.requests.prontuario.MedicacaoPrescritaDTO;
import br.vet.certvet.dto.requests.prontuario.MedicacaoPrescritaListDTO;
import br.vet.certvet.models.Prescricao;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.services.PdfService;
import br.vet.certvet.services.ProntuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/prontuario/prescricao")
@CrossOrigin
@Slf4j
public class PrescricaoController extends BaseController {
    private final ProntuarioService prontuarioService;
    private final PdfService pdfService;

    public PrescricaoController(
            final ProntuarioService prontuarioService,
            final PdfService pdfService
    ) {
        this.prontuarioService = prontuarioService;
        this.pdfService = pdfService;
    }

    @GetMapping(
            value = "/{prontuario}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MedicacaoPrescritaListDTO> getPrescricao(
            @PathVariable("prontuario") String prontuarioCodigo
    ){
        Prontuario prontuario = findProntuario(prontuarioCodigo);
        var prescricoes = prontuario.getPrescricao().stream()
                .map(prescricao -> new MedicacaoPrescritaDTO()
                        .of(prescricao))
                .toList();
        return prescricoes.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(new MedicacaoPrescritaListDTO().of(prescricoes));
    }

    @GetMapping(
            value="/{prontuario}",
            consumes= MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> getPrescricaoPdf(
            @PathVariable("prontuario") String prontuarioCodigo
    ){
        //TODO: Adicionar retrieve de documento com versão mais recente
        Prontuario prontuario = findProntuario(prontuarioCodigo);

        Optional<byte[]> pdf = pdfService.getPrescricaoPdf(prontuario);
        return pdf.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping("/{prontuario}")
    public ResponseEntity<MedicacaoPrescritaListDTO> setPrescricao(
            @PathVariable("prontuario") String prontuarioCodigo,
            @RequestBody MedicacaoPrescritaListDTO medicacaoPrescritaList
    ){
        //TODO: Adicionar versionamento de documento e código
        final Prontuario prontuario = findProntuario(prontuarioCodigo);
        medicacaoPrescritaList.getMedicacoesUtilizadas()
                .stream()
                .map(MedicacaoPrescritaDTO::translate)
                .toList()
                .forEach(
                        prescricao -> prontuario.getPrescricao()
                                .add(prescricao)
                );

        prontuario.setPrescricao(
                new HashSet<>(prontuario.getPrescricao())
                        .stream()
                        .toList()
        );
        Prontuario savedProntuario = prontuarioService.save(prontuario);
        return ResponseEntity.ok(
                new MedicacaoPrescritaListDTO().of(
                        savedProntuario.getPrescricao()
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
        //TODO: Sempre marcar como excluído a versão do documento anterior
        List<Prescricao> prescritoARemover = medicacaoPrescritaList.getMedicacoesUtilizadas()
                .stream()
                .map(MedicacaoPrescritaDTO::translate)
                .toList();
        Prontuario prontuario = findProntuario(prontuarioCodigo);
        prontuario.getPrescricao()
                .forEach(prescricao -> {
                    if(prescritoARemover.contains(prescricao)) {
                        prescricao.delete();
                    }
                });
        List<String> excluidos = prontuarioService.save(prontuario)
                .getPrescricao()
                .stream()
                .map(Prescricao::getDataExclusao)
                .map(LocalDate::toString)
                .toList();
        return ResponseEntity.ok("Removido com sucesso em: " + excluidos );
    }




}
