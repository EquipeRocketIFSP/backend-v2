//package br.vet.certvet.services.implementation;
//
//import be.quodlibet.boxable.BaseTable;
//import be.quodlibet.boxable.Cell;
//import be.quodlibet.boxable.Row;
//import be.quodlibet.boxable.datatable.DataTable;
//import br.vet.certvet.models.Animal;
//import br.vet.certvet.models.Prontuario;
//import br.vet.certvet.models.Usuario;
//import br.vet.certvet.repositories.ClinicaRepository;
//import br.vet.certvet.repositories.PdfRepository;
//import br.vet.certvet.services.PdfService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
//import org.apache.pdfbox.pdmodel.encryption.ProtectionPolicy;
//import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.sql.SQLException;
//import java.text.MessageFormat;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
///**
// * !! Não é possível adicionar "\n" nas strings. se necessário, adicione uma nova linha ou aumente o tamanho da caixa
// */
//@Service
//@Slf4j
//public class PdfBoxPdfServiceImpl implements PdfService {
//
//    @Autowired
//    private PdfRepository pdfRepository;
//
//    @Autowired
//    private ClinicaRepository clinicaRepository;
//
//    private static List<Path> files = new ArrayList<>();
////    private static double mm = 1 / 72 * 25.4 / 72;
//    final private static float margin = 50;
//    final private static boolean drawContent = true;
//    final private static float bottomMargin = 70;
//    final private static float yPosition = 750;
//
//    @Value("${app.default.pdf.pass}")
//    private String defaultPass;
//    static private void writeException(Exception e) throws Exception {
//        log.error(String.format("{\"ERROR\": \"%s\", \"StackTrace\": \"%s\"}", e.getLocalizedMessage(), e.getStackTrace()));
//        throw e;
//    }
//
//    private Cell<PDPage> createCellDefaulFont(Row<PDPage> row1, Float width, String value) {
//        Cell<PDPage> cell = row1.createCell(width, value);
//        cell.setFont(PDType1Font.HELVETICA);
//        return cell;
//    }
//
//    private String getExtenseMonth(Date today) {
//        return StringUtils.capitalize(
//                MessageFormat.format("{0,date,MMMM}", today)
//        );
//    }
//
//    @Override
//    public byte[] termoAutorizacaoProcedimentoCirurgico(
//            String estabelecimento,
//            String procedimento,
//            String cidade,
//            Animal animal,
//            Usuario veterinario,
//            Usuario tutor
//    ) throws Exception {
//        // TODO: passar essas variáveis para configuração
//        String title = "TERMO DE AUTORIZAÇÃO PARA PROCEDIMENTO CIRÚRGICO";
//        String section1 = MessageFormat.format(
//                "Autorizo a realização do procedimento cirúrgico {0} no animal de nome {1}, espécie {2}, " +
//                        "raça {3}, sexo {4}, idade (real ou aproximada) {5}, pelagem {6}, outras informações que " +
//                        "possibilitem a identificação do animal (ex. microchip) a ser realizado pela(o) " +
//                        "médica(o)-vetenária(o){7} (CMRV-{8})",
//                procedimento,
//                animal.getNome(),
//                animal.getEspecie(),
//                animal.getRaca(),
//                animal.getSexo().getSigla(),
//                animal.getIdade(),
//                animal.getPelagem(),
//                veterinario.getNome(),
//                veterinario.getRegistroCRMV()
//                );
//        String section2 = "Identificação do responsável pelo animal:";
//        String section3 = MessageFormat.format(
//                "Nome: {0}<br>RG: {1}<br>CPF {2}<br>Endereço completo: {3}<br>Telefone: {4}<br>email: {5}",
//                tutor.getNome(),
//                tutor.getRg(),
//                tutor.getCpf(),
//                tutor.getLogradouro(),
//                tutor.getNumero(),
//                tutor.getTelefone(),
//                tutor.getEmail()
//        );
//        String section4 = "Declaro ter sido esclarecido acerca dos possíveis riscos inerentes durante ou após a " +
//                "realização do procedimento cirúrgico citado, estando o referido profissional isento de quaiSquer " +
//                "responsabilidades decorrentes de tais riscos.";
//        Date today = new Date();
//        String section5 = MessageFormat.format(
//                "{0}, {1,date,dd} de {2} de {1,date,yyyy}",
//                "São Paulo",
//                today,
//                getExtenseMonth(today)
//        );
//
//        final PDDocument document = new PDDocument();
//        final PDPage page = new PDPage();
//        document.addPage(page);
//        final PDPageContentStream contentStream = new PDPageContentStream(document, page);
//        final float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);// starting y position is whole page height subtracted by top and bottom margin
//        final float tableWidth = page.getMediaBox().getWidth() - (2 * margin);// we want table across whole page width (subtracted by left and right margin ofcourse)
//
//        try {
//            contentStream.setFont(PDType1Font.COURIER, 16);
//            BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, page, true, drawContent);
//
//
//            Row<PDPage> headerRow = table.createRow(15f);
//            headerRow.createCell(100, title).setFont(PDType1Font.HELVETICA_BOLD);
//            table.addHeaderRow(headerRow);
//
//            Row<PDPage> row1 = table.createRow(12);
//            row1.createCell(100, section1).setFont(PDType1Font.HELVETICA);
//
//            Row<PDPage> row2 = table.createRow(12);
//            row2.createCell(100, section2).setFont(PDType1Font.HELVETICA);
//
//            Row<PDPage> row3 = table.createRow(12);
//            row3.createCell(100, section3).setFont(PDType1Font.HELVETICA);
//
//            Row<PDPage> row4 = table.createRow(12);
//            row4.createCell(100, section4).setFont(PDType1Font.HELVETICA);
//
//            Row<PDPage> row5 = table.createRow(12);
//            row5.createCell(100, section5).setFont(PDType1Font.HELVETICA);
//
//            table.draw();
//            contentStream.close();
//
//            document.protect(new StandardProtectionPolicy(
//                    "ownerpass",
//                    "userpass",
//                    setAccessPermission()));
//
//            document.save("res/mockSample.pdf");
//            document.close();
//        } catch (IOException e){
//            writeException(e);
//        }
//        return null;
//    }
//
//    public String getProntuarioName(Prontuario prontuario){
//        return prontuario.getCodigo() + ".pdf";
//    }
//    @Override
//    public byte[] writeProntuario(Prontuario prontuario) throws Exception {
//        log.info("Iniciando escrita do pdf do prontuário: " + prontuario.getCodigo() + ", id: " + prontuario.getId());
//        // TODO: Passar essas variáveis para arquivo de configuração
//        final String title = "Prontuário Clínico veterinário";
//        final String vetQualificacaoL1 = prontuario.getVeterinario().getNome();
//        final String vetQualificacaoL2 = "Crmv: "+ prontuario.getVeterinario().getRegistroCRMV();
//        final String clinicaQualificacao0 = prontuario.getClinica().getRazaoSocial();
//        final String clinicaQualificacao1 = prontuario.getClinica()
//                .getTelefone();
////                .getTelefones().toString();
//        final String codigo = "Prontuário: " + prontuario.getCodigo();
//
////        final String fileName = "res/" + getProntuarioName(prontuario);
//        final String fileName = prontuario.getCodigo() + ".pdf";
//
//        try(final PDDocument document = new PDDocument()){
//            final PDPage page = new PDPage();
//            document.addPage(page);
//            try(final PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
//                final float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);// starting y position is whole page height subtracted by top and bottom margin
//                final float tableWidth = page.getMediaBox().getWidth() - (2 * margin);// we want table across whole page width (subtracted by left and right margin ofcourse)
//
//                contentStream.setFont(PDType1Font.COURIER, 16);
//
//                BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, page, true, drawContent);
//                DataTable t = new DataTable(table, page);
//                Row<PDPage> headerRow = table.createRow(15f);
//
//                headerRow.createCell(65f, title).setFont(PDType1Font.HELVETICA_BOLD);
//                headerRow.createCell(35f, codigo).setFont(PDType1Font.HELVETICA_BOLD);
//
//                table.addHeaderRow(headerRow);
//                table.createRow(15f).createCell(100f, "");
//
//                Row<PDPage> row1 = table.createRow(12);
//                createCellDefaulFont(row1, 100f, vetQualificacaoL1);
//
//                Row<PDPage> row2 = table.createRow(12);
//                createCellDefaulFont(row2, 100f, vetQualificacaoL2);
//
//                table.createRow(15f).createCell(100f, "");
//
//                Row<PDPage> row3 = table.createRow(12);
//                createCellDefaulFont(row3, 100f, clinicaQualificacao0);
//
//                Row<PDPage> row3_0 = table.createRow(12);
//                createCellDefaulFont(row3_0,25f, "Telefone");
//                createCellDefaulFont(row3_0,75f, clinicaQualificacao1);
//
//                table.createRow(15f).createCell(100f, "");
//
//                Row<PDPage> row4 = table.createRow(12);
//                createCellDefaulFont(row4, 25f, "Animal");
//                createCellDefaulFont(row4, 75f, prontuario.getAnimal().getNome());
//
//                Row<PDPage> row7 = table.createRow(12);
//                createCellDefaulFont(row7, 25f, "Raça");
//                createCellDefaulFont(row7, 75f, prontuario.getAnimal().getRaca());
//
//                log.info(prontuario.getAnimal().getEspecie());
//                log.info(prontuario.getAnimal().getSexo().getSigla());
//                log.info(String.valueOf(prontuario.getAnimal().getIdade()));
////                log.info(prontuario.getAnimal().getFormaIdentificacao());
//
//                t.addListToTable(
//                        List.of(
//                                List.of("Espécie",
//                                        "Sexo",
//                                        "Idade",
//                                        "Microchip"
//                                ),
//                                List.of(
//                                        prontuario.getAnimal().getEspecie(),
//                                        prontuario.getAnimal().getSexo().getSigla(),
//                                        String.valueOf(prontuario.getAnimal().getIdade()),
//                                        prontuario.getAnimal().getFormaIdentificacao()
//                                )
//                        ),
//                        DataTable.NOHEADER
//                );
//
////                Row<PDPage> row5 = table.createRow(12f);
////                List.of("Espécie",
////                        "Sexo",
////                        "Idade",
////                        "Microchip"
////                ).forEach(
////                        headerCell -> createCellDefaulFont(row5, 25f, headerCell)
////                );
////
////                Row<PDPage> row6 = table.createRow(12); //TODO: Quando a classe Animal estiver minimamente integrada com o prontuário, passar a string correta
////                List.of("prontuario.getAnimal().getEspecie()",
////                        "prontuario.getAnimal().getSexo()",
////                        "String.valueOf(prontuario.getAnimal().getIdade())",
////                        "prontuario.getAnimal().getOutros()"
////                ).forEach(
////                        bodyCell -> createCellDefaulFont(row6, 25f, bodyCell)
////                );
//
//                table.createRow(15f).createCell(100f, "");
//
//                Row<PDPage> row8 = table.createRow(12); // TODO: Alterar para dados reais
//                createCellDefaulFont(row8, 10f, "Tutor");
//                createCellDefaulFont(row8, 50f, prontuario.getTutor().getNome());
//                createCellDefaulFont(row8, 10f, "CPF");
//                createCellDefaulFont(row8, 30f, prontuario.getTutor().getCpf());
//
//                Row<PDPage> row9 = table.createRow(12);
//                createCellDefaulFont(row9, 10f, "Endereço");
//                createCellDefaulFont(row9, 50f, prontuario.getTutor().getLogradouro() + ", " + prontuario.getTutor().getNumero());
//
//                createCellDefaulFont(row9, 10f, "Telefone");
//                createCellDefaulFont(row9, 30f, prontuario.getTutor().getTelefone());
//
//                table.createRow(15f).createCell(100f, "");
//
//                Row<PDPage> row10 = table.createRow(12f);
//
//                createCellDefaulFont(row10, 10f, "Data");
//                createCellDefaulFont(row10, 40f, prontuario.getDataAtendimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//                createCellDefaulFont(row10, 10f, "Horário");
//                createCellDefaulFont(row10, 40f, prontuario.getDataAtendimento().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
//
//                table.createRow(30f).createCell(100f, "");
//
////                Map<Integer, Map<Integer, Float>> frameYX = Map.of(
////                        0, Map.of(
////                                0,16f,
////                                1, 100f),
////                        1, Map.of(
////                                0, 32f,
////                                1, 100f),
////                        2, Map.of(
////                                0,64f,
////                                1,100f)
////                );
////                Map<String, Map> itens = new LinkedHashMap<>();
////                itens.put("Imunizações", frameYX.get(0));
////                itens.put("Sinais Clínicos", frameYX.get(1));
////                itens.put("Exames", frameYX.get(0));
////                itens.put("Prescrições", frameYX.get(0));
////                itens.put("Diagnóstico", frameYX.get(0));
////                itens.put("Observações", frameYX.get(2));
////                itens.forEach(
////                        (cellValue, frame) -> {
////                            createCellDefaulFont(table.createRow(12f), 100f, cellValue);
////                            table.createRow((Float) frame.get(0)).createCell((Float) frame.get(1), "");
////                        }
////                );
//                createCellDefaulFont(table.createRow(12f), 100f, "Imunizações");
//                table.createRow(16f).createCell(100f, "");
//                createCellDefaulFont(table.createRow(12f), 100f, "Sinais Clínicos");
//                table.createRow(32f).createCell(100f, "");
//                createCellDefaulFont(table.createRow(12f), 100f, "Exames");
//                table.createRow(16f).createCell(100f, "");
//                createCellDefaulFont(table.createRow(12f), 100f, "Prescrições");
//                table.createRow(16f).createCell(100f, "");
//                createCellDefaulFont(table.createRow(12f), 100f, "Diagnóstico");
//                table.createRow(16f).createCell(100f, "");
//                createCellDefaulFont(table.createRow(12f), 100f, "Observações");
//                table.createRow(64f).createCell(100f, "");
//
//                log.info("Desenhando tabela");
//                table.draw();
//            } catch (IOException e){
//                writeException(e);
//            }
//            log.info("Adicionando proteção ao PDF");
//            try {
//                ProtectionPolicy policy = getProtectionPolicy(defaultPass, getTutorPass(prontuario));
//                document.protect(policy);
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//            document.save(fileName);
//            log.info("PDF do prontuário criado");
//        } catch (IllegalArgumentException e){
//            writeException(e);
//        }
//        Path path = Path.of(fileName);
//        log.info("Iniciando persistência no serviço AWS S3");
//        pdfRepository.putObject(
//                clinicaRepository.findById(prontuario.getClinica().getId()).stream()
//                        .findFirst()
//                        .orElseThrow(SQLException::new)
//                        .getCnpj(),
//                fileName.substring(fileName.indexOf("/")+1),
//                path.toFile()
//        );
//        log.info("Prontuário Salvo");
//        try {
//            return Files.readAllBytes(path);
//        } finally {
//            Files.deleteIfExists(path);
//        }
//    }
//
//    private static String getTutorPass(Prontuario prontuario) {
//        return prontuario.getTutor()
//                .getCpf()
//                .replace(".", "")
//                .substring(5, 9);
//    }
//
//    private static ProtectionPolicy getProtectionPolicy(String defaultPass, String tutorPass) throws IOException {
//        final AccessPermission accessPermission = new AccessPermission();
//        accessPermission.setCanModify(true);
//        accessPermission.setCanExtractForAccessibility(true);
//        accessPermission.setCanPrint(true);
//        accessPermission.setCanExtractContent(true);
//        var std = new StandardProtectionPolicy(defaultPass, tutorPass, accessPermission);
//        return std;
//    }
//
//    @Override
//    public byte[] retrieveFromRepository(Prontuario prontuario) throws IOException {
//        return pdfRepository.retrieveObject(
//                prontuario.getClinica().getCnpj(),
//                getProntuarioName(prontuario)
//        );
//    }
//
//    public static void run() throws IOException {
//
//        PDDocument document = new PDDocument();
//        PDPage page1 = new PDPage();
//        document.addPage(page1);
////        Path pathToPdf = Paths.get(new File("res/PDFBOX/pdfBoxHelloWorld.pdf").toURI());
//
//        PDPageContentStream contentStream = new PDPageContentStream(document, page1);
//
////        Path pathToImage = Paths.get(new File("res/arquitetura_v1.png").toURI());
////        PDImageXObject image = PDImageXObject.createFromFile(pathToImage.toAbsolutePath().toString(), document);
////        contentStream.drawImage(image, 0, 0);
////        contentStream.setFont(PDType1Font.COURIER, 12);
////        contentStream.beginText();
////        contentStream.newLineAtOffset(50, 700);
////        contentStream.showText("Hello World");
////        contentStream.endText();
//        //Dummy Table
//        float margin = 50;
//// starting y position is whole page height subtracted by top and bottom margin
//        float yStartNewPage = page1.getMediaBox().getHeight() - (2 * margin);
//// we want table across whole page width (subtracted by left and right margin ofcourse)
//        float tableWidth = page1.getMediaBox().getWidth() - (2 * margin);
//
//        boolean drawContent = true;
//        float yStart = yStartNewPage;
//        float bottomMargin = 70;
//// y position is your coordinate of top left corner of the table
//        float yPosition = 750;
//
//        BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, page1, true, drawContent);
//
//
//        Row<PDPage> headerRow = table.createRow(15f);
//        Cell<PDPage> cell = headerRow.createCell(100, "Header");
//        table.addHeaderRow(headerRow);
//
//
//        Row<PDPage> row = table.createRow(12);
//        cell = row.createCell(30, "Data 1");
//        cell = row.createCell(70, "Some value");
//        Row<PDPage> row2 = table.createRow(12);
//        cell = row2.createCell(70, "Data 1");
//
//        table.draw();
//
////        mainDocument.addPage(myPage);
////        mainDocument.save("testfile.pdf");
////        mainDocument.close();
//
//        contentStream.close();
//
//        document.save("res/PDFBOX/pdfBoxImage.pdf");
////        document.save(pathToImage.toAbsolutePath().toString());
//        document.close();
//    }
//
//    /*public static void main(String[] args) throws IOException {
//        new PdfServicePdfBoxImpl().termoAutorizacaoProcedimentoCirurgico(
//                "estabelecimento",
//                "Operação",
//                "São Paulo",
//                Animal.builder()
//                        .especie("cachorro")
//                        .raca("vira-lata")
//                        .sexo("masc")
//                        .idade(2)
//                        .nome("doguinho")
//                        .pelagem("curta")
//                        .build(),
//                Veterinario.builder()
//                        .registroCRMV("SP-12345")
//                        .nome("VET")
//                        .build(),
//                Tutor.builder()
//                        .cpf("228.831.350-10")
//                        .rg("12345689")
//                        .email("email@valido.com")
//                        .logradouro("endereco random")
//                        .numero("13")
//                        .telefone("123456789")
//                        .build()
//        );*/
//}
