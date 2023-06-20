package br.vet.certvet.controllers;

import br.vet.certvet.contracts.apis.ipc_br.IcpResponse;
import br.vet.certvet.dto.requests.prontuario.MedicacaoPrescritaDTO;
import br.vet.certvet.dto.requests.prontuario.MedicacaoPrescritaListDTO;
import br.vet.certvet.exceptions.AssinadorNaoCadastradoException;
import br.vet.certvet.exceptions.ErroSalvarPdfAssinadoAwsException;
import br.vet.certvet.exceptions.InvalidSignedDocumentoException;
import br.vet.certvet.exceptions.NotMatchingFileTypeToPdfException;
import br.vet.certvet.models.Prescricao;
import br.vet.certvet.models.Prontuario;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.PrescricaoRepository;
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

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/prontuario/prescricao")
@CrossOrigin
@Slf4j
public class PrescricaoController extends BaseController {
    private final PrescricaoRepository prescricaoRepository;
    private final ProntuarioService prontuarioService;
    private final PdfService pdfService;

    public PrescricaoController(
            final ProntuarioService prontuarioService,
            final PdfService pdfService,
            PrescricaoRepository prescricaoRepository) {
        this.prontuarioService = prontuarioService;
        this.pdfService = pdfService;
        this.prescricaoRepository = prescricaoRepository;
    }

    @GetMapping(
            value = "/{prontuario}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MedicacaoPrescritaListDTO> getPrescricao(
            @RequestHeader(AUTHORIZATION) String auth,
            @PathVariable("prontuario") String prontuarioCodigo,
            @RequestHeader(value = "versao", required = false) Integer versao
    ) {
        final int version = null == versao ? 1 : versao;
        Prontuario prontuario = findProntuarioEClinica(auth, prontuarioCodigo);
        List<MedicacaoPrescritaDTO> prescricoes = prontuario.getPrescricoes(version)
                .stream()
                .map(prescricao -> new MedicacaoPrescritaDTO().of(prescricao))
                .toList();
        return prescricoes.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(new MedicacaoPrescritaListDTO().of(prontuario));
    }

    /**
     * Se nenhum valor for informado para version, o valor default será 1
     *
     * @param prontuarioCodigo
     * @return pdf
     */
    @GetMapping(
            value = "/{prontuario}",
            consumes = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> getPrescricaoPdf(
            @RequestHeader(AUTHORIZATION) String auth,
            @PathVariable("prontuario") String prontuarioCodigo
    ) {
        Prontuario prontuario = findProntuarioEClinica(auth, prontuarioCodigo);
        Optional<byte[]> pdf = pdfService.writePrescricao(prontuario);

        return pdf.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping(
            value = "/{prontuario}",
            consumes = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<MedicacaoPrescritaListDTO> setSignedPrescricao(
            @RequestHeader(AUTHORIZATION) String auth,
            @PathVariable("prontuario") String prontuarioCodigo,
            @RequestBody byte[] medicacaoPrescritaPdf
    ) {

        if(!pdfService.isFileTypePdf(medicacaoPrescritaPdf))
            throw new NotMatchingFileTypeToPdfException("O arquivo recebido não foi identificado como pdf. Revise e tente novamente.");

        Prontuario prontuario = findProntuarioEClinica(auth, prontuarioCodigo);
        final int version = Integer.parseInt(prontuario.prescricaoLatestVersion());
        //Importante: passo necessário para adicionar o documento no repositório em nuvem para que o serviço icp-br possa realizar a validação do documento
        ObjectMetadata awsResponse = pdfService.savePrescricaoPdfInBucket(prontuario, version, medicacaoPrescritaPdf);
        if(null == awsResponse)
            throw new ErroSalvarPdfAssinadoAwsException("Não foi recebido nenhuma confirmação de que o arquivo foi salvo com sucesso. tente novamente");

        final String bucket = S3BucketServiceRepository.getConventionedBucketName(prontuario.getClinica().getCnpj());
        final String fileName = ProntuarioServiceImpl.writeNomeArquivoPrescricao(prontuario, version);
        final IcpResponse icpResponse = pdfService.getIcpBrValidation(bucket, fileName);
        if (!icpResponse.isValidDocument())
            throw new InvalidSignedDocumentoException("O documento não pôde ser confirmado pelo ICP-BR");

        final List<Usuario> assinadores = PdfFromHtmlPdfServiceImpl.assinadoresPresentesSistema(icpResponse);
        Prontuario signedPrescricao = prontuarioService.save(
                prontuario.setPrescricoes(
                        prontuario.getPrescricoes()
                                .stream()
                                .map(prescricao -> {
                                    if (prescricao.getVersao() == version) {
                                        prescricao.setVeterinarioAssinador(
                                                assinadores.stream()
                                                        .findFirst()
                                                        .orElseThrow(() -> new AssinadorNaoCadastradoException("Não foi possível identificar uma a assinatura válida."))
                                        );
                                    }
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
        final int max = prontuario.getPrescricoes()
                .stream()
                .mapToInt(Prescricao::getVersao)
                .max()
                .orElse(1);
        medicacaoPrescritaList.getMedicacoesPrescritas()
                .stream()
                .map(medicacaoPrescrita -> medicacaoPrescrita.translate(prontuario))
                .map(prescricao -> prescricao.setProntuario(prontuario))
                .forEach(prescricao -> {
                    prescricao.setVersao(max);
                    List<Prescricao> p = prontuario.getPrescricoes();
                    if (!p.contains(prescricao)) p.add(prescricao.setDataCriacao());
                });
//        List<Prescricao> prescricoesARemover = prontuario.getPrescricoes()
//                .stream()
//                .map(prescricao -> new MedicacaoPrescritaDTO().of(prescricao))
//                .filter(prescricao -> !medicacaoPrescritaList.getMedicacoesPrescritas().contains(prescricao))
//                .map(medicacaoPrescrita -> medicacaoPrescrita.translate(prontuario))
//                .map(prescricao -> prescricao.setProntuario(prontuario))
//                .toList();
//        prescricoesARemover.forEach(prescricao -> prescricaoRepository.save(prontuario.getPrescricoes().getprescricao.delete()));
        prescricaoRepository.saveAll(prontuario.getPrescricoes());
        return ResponseEntity.created(
                        URI.create("/api/prontuario/prescricao/" + prontuarioCodigo))
                .header("version", prontuario.prescricaoLatestVersion())
                .body(new MedicacaoPrescritaListDTO().of(
                        prontuario
                ));
    }

    @DeleteMapping("/{prontuario}")
    public ResponseEntity<String> deletePrescricao(
            @PathVariable("prontuario") String prontuarioCodigo
            //@RequestBody MedicacaoPrescritaListDTO medicacaoPrescritaList
    ) {
        //TODO: Sempre marcar como excluído a versão do documento anterior
        Prontuario prontuario = findProntuario(prontuarioCodigo);

        prontuario.getPrescricoes().forEach(prescricao -> {
            prescricao.delete();
        });
        List<String> excluidos = prontuarioService.save(prontuario)
                .getPrescricoes()
                .stream()
                .map(Prescricao::getDataExclusao)
                .map(LocalDateTime::toString)
                .toList();
        return ResponseEntity.ok("Removido com sucesso em: " + excluidos);
    }


}
