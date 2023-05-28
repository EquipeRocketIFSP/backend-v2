package br.vet.certvet.controllers;

import br.vet.certvet.contracts.apis.ipcBr.IcpResponse;
import br.vet.certvet.dto.requests.prontuario.MedicacaoPrescritaDTO;
import br.vet.certvet.dto.requests.prontuario.MedicacaoPrescritaListDTO;
import br.vet.certvet.exceptions.InvalidSignedDocumentoException;
import br.vet.certvet.models.Prescricao;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.services.PdfService;
import br.vet.certvet.services.ProntuarioService;
import br.vet.certvet.services.implementation.PdfFromHtmlPdfServiceImpl;
import br.vet.certvet.services.implementation.ProntuarioServiceImpl;
import br.vet.certvet.services.implementation.S3BucketServiceRepository;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
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
            @PathVariable("prontuario") String prontuarioCodigo,
            @RequestHeader(value = "version", required = false) Integer v
    ){
        final int version = null == v ? 1 : v;
        Prontuario prontuario = findProntuario(prontuarioCodigo);
        List<MedicacaoPrescritaDTO> prescricoes = prontuario.getPrescricoes(version)
                .stream()
                .map(prescricao -> new MedicacaoPrescritaDTO().of(prescricao))
                .toList();
        return prescricoes.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(new MedicacaoPrescritaListDTO().of(prescricoes));
    }

    /**
     * Se nenhum valor for informado para version, o valor default será 1
     * @param prontuarioCodigo
     * @param v
     * @return pdf
     */
    @GetMapping(
            value="/{prontuario}",
            consumes= MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> getPrescricaoPdf(
            @PathVariable("prontuario") String prontuarioCodigo,
            @RequestHeader(value = "version", required = false) Integer v
    ){
        final int version = null == v ? 1 : v;
        Prontuario prontuario = findProntuario(prontuarioCodigo);
        Optional<byte[]> pdf;
        try {
            pdf = pdfService.writePrescricao(prontuario);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        Optional<byte[]> pdf = pdfService.getPrescricaoPdf(prontuario, version);
        return pdf.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping(
            value = "/{prontuario}",
            consumes = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<MedicacaoPrescritaListDTO> setSignedPrescricao(
            @PathVariable("prontuario") String prontuarioCodigo,
//            @RequestHeader(value = "versao", required = false) Integer versao,
            @RequestBody byte[] medicacaoPrescritaPdf
    ) throws IOException {
        Prontuario prontuario = findProntuario(prontuarioCodigo);
        final int version = Integer.parseInt(prontuario.prescricaoLatestVersion());
        //Importante: passo necessário para adicionar o documento no repositório em nuvem para que o serviço icp-br possa realizar a validação do documento
        ObjectMetadata awsResponse = pdfService.savePrescricaoPdfInBucket(prontuario, version, medicacaoPrescritaPdf);

        final String bucket = S3BucketServiceRepository.getConventionedBucketName(prontuario.getClinica().getCnpj());
        final String fileName = ProntuarioServiceImpl.writeNomeArquivoPrescricao(prontuario, version);
        final IcpResponse icpResponse = pdfService.getIcpBrValidation(bucket, fileName);
        if(!icpResponse.isValidDocument()) throw new InvalidSignedDocumentoException("O documento não pôde ser confirmado pelo ICP-BR");

        final List<Usuario> assinadores = PdfFromHtmlPdfServiceImpl.assinadoresPresentesSistema(icpResponse);
        Prontuario signedPrescricao = prontuarioService.save(
                prontuario.setPrescricoes(
                        prontuario.getPrescricoes()
                                .stream()
                                .map(prescricao -> {
                                    if(prescricao.getVersao() == version)
                                        prescricao.setAssinadores(assinadores);
                                    return prescricao;
                                }).toList())
        );

        return null != signedPrescricao
                ? ResponseEntity.created(URI.create(""))
                .header("version", prontuario.prescricaoLatestVersion())
                .build()
                : ResponseEntity.internalServerError().build();
    }

    @PostMapping("/{prontuario}")
    public ResponseEntity<MedicacaoPrescritaListDTO> setPrescricao(
            @PathVariable("prontuario") String prontuarioCodigo,
            @RequestBody MedicacaoPrescritaListDTO medicacaoPrescritaList
    ) {
        //TODO: testar ser versionamento esta correspondendo a expectativa
        final Prontuario prontuario = findProntuario(prontuarioCodigo);
        medicacaoPrescritaList.getMedicacoesUtilizadas()
                .stream()
                .map(medicacaoPrescrita -> new MedicacaoPrescritaDTO().translate(prontuario))
                .forEach(prescricao -> {
                    List<Prescricao> p = prontuario.getPrescricoes();
                    if(p.contains(prescricao)) p.set(p.indexOf(prescricao), p.get(p.indexOf(prescricao)).increaseVersion());
                    else p.add(prescricao.firstVersion());
                });
        Prontuario savedProntuario = prontuarioService.save(prontuario);
        return ResponseEntity.created(
                URI.create("/api/prontuario/prescricao/" + prontuarioCodigo))
                .header("version", savedProntuario.prescricaoLatestVersion())
                .body(new MedicacaoPrescritaListDTO().of(
                        savedProntuario.getPrescricoes()
                                .stream()
                                .map(p -> new MedicacaoPrescritaDTO().of(p))
                                .toList()
                ));
    }

    @DeleteMapping("/{prontuario}")
    public ResponseEntity<String> deletePrescricao(
            @PathVariable("prontuario") String prontuarioCodigo,
            @RequestBody MedicacaoPrescritaListDTO medicacaoPrescritaList
    ){
        //TODO: Sempre marcar como excluído a versão do documento anterior
        Prontuario prontuario = findProntuario(prontuarioCodigo);
        List<Prescricao> prescritoARemover = medicacaoPrescritaList.getMedicacoesUtilizadas()
                .stream()
                .map(medicacaoPrescrita -> new MedicacaoPrescritaDTO().translate(prontuario))
                .toList();
        prontuario.getPrescricoes()
                .forEach(prescricao -> {
                    if(prescritoARemover.contains(prescricao)) {
                        prescricao.delete();
                    }
                });
        List<String> excluidos = prontuarioService.save(prontuario)
                .getPrescricoes()
                .stream()
                .map(Prescricao::getDataExclusao)
                .map(LocalDate::toString)
                .toList();
        return ResponseEntity.ok("Removido com sucesso em: " + excluidos );
    }




}
