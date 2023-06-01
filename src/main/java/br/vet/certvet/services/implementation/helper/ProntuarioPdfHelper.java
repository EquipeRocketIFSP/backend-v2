package br.vet.certvet.services.implementation.helper;

import br.vet.certvet.models.*;
import br.vet.certvet.models.especializacoes.Doc;
import br.vet.certvet.models.especializacoes.PrescricaoDocumento;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.text.StringSubstitutor;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ProntuarioPdfHelper {

    private static String getObservacaoTutor(List<Documento> documentos) {
        return documentos.stream()
                .map(Documento::getObservacaoTutor)
                .filter(Objects::nonNull)
                .toList()
                .toString()
                .replaceAll("\\[]", "");
    }

    private static String getObservacaoVet(List<Documento> documentos) {
        return documentos.stream()
                .map(Documento::getObservacaoVet)
                .filter(Objects::nonNull)
                .toList()
                .toString()
                .replaceAll("\\[]", "");
    }

    private static String getDocumentoTipo(List<Documento> documentos, String tipo) {
        return documentos.stream()
                .filter(Objects::nonNull)
                .filter(documento -> documento.getTipo().equals(tipo))
                .toList()
                .toString()
                .replaceAll("\\[]", "");
    }

    private static Optional<Documento> getObito(Prontuario prontuario) {
        return prontuario.getDocumentos()
                .stream()
                .filter(Objects::nonNull)
                .filter(documento -> "obito".equals(documento.getTipo()))
                .findFirst();
    }

    public static String fillLayoutFieldsForProntuario(final Prontuario prontuario, String layout) {
        final Animal animal = prontuario.getAnimal();
        final Usuario veterinario = prontuario.getVeterinario();
        final Usuario tutor = prontuario.getTutor();
        final Clinica clinica = prontuario.getClinica();
        final List<Documento> documentos = prontuario.getDocumentos();
        final Optional<Documento> obito = getObito(prontuario);

        ImmutableMap.Builder<String, String> builder = ImmutableMap.<String, String>builder()
                .put("animal.nome",                     animal.getNome())
                .put("animal.especie",                  animal.getEspecie())
                .put("animal.raca",                     animal.getRaca())
                .put("animal.sexo",                     animal.getSexo().name().toLowerCase())
                .put("animal.idade",                    String.valueOf(animal.getIdade()))
                .put("animal.pelagem",                  animal.getPelagem())
                .put("clinica.razaoSocial",             clinica.getRazaoSocial())
                .put("clinica.telefone",                clinica.getCelular())
                .put("cidade",                          clinica.getCidade())
                .put("documento.observacaoVet",         getObservacaoVet(documentos))
                .put("documento.observacaoTutor",       getObservacaoTutor(documentos))
                .put("prontuario.anestesia",            getDocumentoTipo(documentos, "anestesia"))
                .put("prontuario.terapias",             getDocumentoTipo(documentos, "terapeutico"))
                .put("prontuario.codigo",               prontuario.getCodigo())
                .put("data.dia",                        String.valueOf(prontuario.getDataAtendimento().getDayOfMonth()))
                .put("data.mes",                        prontuario.getMonthAtendimento())
                .put("data.ano",                        String.valueOf(prontuario.getDataAtendimento().getYear()))
                .put("tutor.nome",                      tutor.getNome())
                .put("tutor.cpf",                       tutor.getCpf())
                .put("tutor.endereco",                  tutor.getEnderecoCompleto())
                .put("veterinario.nome",                veterinario.getNome())
                .put("veterinario.crmv",                veterinario.getRegistroCRMV());
        if(prontuario.getExames()!=null)
            builder.put("prontuario.exames",               prontuario.getExames().toString().replace("\\[]", ""));
        if(prontuario.getCirurgia()!=null)
            builder.put("prontuario.cirurgia",             prontuario.getCirurgia().getDescricao());
        if(obito.isPresent()) {
            Documento o = obito.get();
            builder.put("prontuario.obito.causa", o.getCausaMortis())
                    .put("documento.causaMortis", o.getCausaMortisDescription())
                    .put("documento.orientaDestinoCorpo", o.getOrientaDestinoCorpo())
                    .put("prontuario.obito.local", o.getLocal())
                    .put("documento.outrasObservacoes", o.getObservacoes())
                    .put("prontuario.obito.horas", o.getDataHoraObito().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                    .put("prontuario.obito.data", o.getDataHoraObito().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        return new StringSubstitutor(builder.build()).replace(layout);
    }

    public static String replaceWithDivsForDocumento(Doc documento, String layout) {
        return new StringSubstitutor(ImmutableMap.<String, String>builder()
                .put("documento.titulo",                    documento.getTitulo())
                .put("documento.declara_consentimento",     documento.getDeclaraConsentimento())
                .put("documento.declara_ciencia_riscos",    documento.getDeclaraCienciaRiscos() /*== null ? "" : documento.getDeclaraCienciaRiscos()*/)
                .put("documento.observacoes_veterinario",   documento.getObservacoesVeterinario() /*== null ? "" : documento.getObservacoesVeterinario()*/)
                .put("documento.observacoes_responsavel",   documento.getObservacoesResponsavel() /*== null ? "" : documento.getObservacoesResponsavel()*/)
                .put("documento.causa_mortis",              documento.getCausaMortis() /*== null ? "" : documento.getCausaMortis()*/)
                .put("documento.orienta_destino_corpo",     documento.getOrientaDestinoCorpo() /*== null ? "" : documento.getOrientaDestinoCorpo()*/)
                .put("documento.outras_observacoes",        documento.getOutrasObservacoes() /*== null ? "" : documento.getOutrasObservacoes()*/)
                .put("documento.assinatura_responsavel",    documento.getAssinaturaResponsavel() /*== null ? "" : documento.getAssinaturaResponsavel()*/)
                .put("documento.assinatura_vet",            documento.getAssinaturaVet() /*== null ? "" : documento.getAssinaturaVet()*/)
                .put("documento.explica_duas_vias",         documento.getExplicaDuasVias() /*== null ? "" : documento.getExplicaDuasVias()*/)
                .build()
        ).replace(layout);
    }

    public static String fillLayoutFieldsForDocumento(final Documento documento, String layout){
        final Prontuario prontuario = documento.getProntuario();
        final Animal animal = prontuario.getAnimal();
        final Usuario veterinario = prontuario.getVeterinario();
        final Usuario tutor = prontuario.getTutor();
        final Clinica clinica = prontuario.getClinica();
        final List<Documento> documentos = prontuario.getDocumentos();
        final Optional<Documento> obito = getObito(prontuario);
        final String exames = prontuario.getExames()!=null
                ? prontuario.getExames().toString().replace("\\[]", "")
                : "";
        final String cirurgia = prontuario.getCirurgia()!=null
                ? prontuario.getCirurgia().toString().replace("\\[]", "")
                : "";

        ImmutableMap.Builder<String, String> builder = ImmutableMap.<String, String>builder()
                .put("clinica.razaoSocial",             clinica.getRazaoSocial())
                .put("clinica.telefone",                clinica.getCelular())
                .put("veterinario.nome",                veterinario.getNome())
                .put("veterinario.crmv",                veterinario.getRegistroCRMV())
                .put("prontuario.codigo",               prontuario.getCodigo())
                .put("documento.observacaoVet",         getObservacaoVet(documentos))
                .put("documento.observacaoTutor",       getObservacaoTutor(documentos))
                .put("prontuario.anestesia",            getDocumentoTipo(documentos, "anestesia"))
                .put("prontuario.terapias",             getDocumentoTipo(documentos, "terapeutico"))
                .put("animal.nome",                     animal.getNome())
                .put("animal.especie",                  animal.getEspecie())
                .put("animal.raca",                     animal.getRaca())
                .put("animal.sexo",                     animal.getSexo().name().toLowerCase())
                .put("animal.idade",                    String.valueOf(animal.getIdade()))
                .put("animal.pelagem",                  animal.getPelagem())
                .put("tutor.nome",                      tutor.getNome())
                .put("tutor.cpf",                       tutor.getCpf())
                .put("tutor.endereco",                  tutor.getEnderecoCompleto())
                .put("cidade",                          clinica.getCidade())
                .put("data.dia",                        String.valueOf(prontuario.getDataAtendimento().getDayOfMonth()))
                .put("data.mes",                        prontuario.getMonthAtendimento())
                .put("data.ano",                        String.valueOf(prontuario.getDataAtendimento().getYear()))
                .put("prontuario.exames",               exames)
                .put("prontuario.cirurgia",             cirurgia);
        if(obito.isPresent()) {
            Documento o = obito.get();
            builder.put("prontuario.obito.causa",       o.getCausaMortis())
                .put("documento.causaMortis",           o.getCausaMortisDescription())
                .put("documento.orientaDestinoCorpo",   o.getOrientaDestinoCorpo())
                .put("prontuario.obito.local",          o.getLocal())
                .put("documento.outrasObservacoes",     o.getObservacoes())
                .put("prontuario.obito.horas",          o.getDataHoraObito().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                .put("prontuario.obito.data",           o.getDataHoraObito().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        return new StringSubstitutor(builder.build()).replace(layout);
    }
    public static String fillLayoutFieldsForPrescricao(Prontuario prontuario, String layout) {
        final Animal animal = prontuario.getAnimal();
        final Usuario veterinario = prontuario.getVeterinario();
        final Usuario tutor = prontuario.getTutor();
        final Clinica clinica = prontuario.getClinica();

        return new StringSubstitutor(ImmutableMap.<String, String>builder()
                .put("animal.nome",         animal.getNome())
                .put("animal.especie",      animal.getEspecie())
                .put("animal.sexo",         animal.getSexo().name().toLowerCase())
                .put("animal.idade",        String.valueOf(animal.getIdade()))
                .put("clinica.razaoSocial", clinica.getRazaoSocial())
                .put("tutor.nome",          tutor.getNome())
                .put("tutor.cpf",           tutor.getCpf())
                .put("tutor.telefone",      tutor.getTelefone())
                .put("veterinario.nome",    veterinario.getNome())
                .put("veterinario.crmv",    veterinario.getRegistroCRMV())
                .build()
        ).replace(layout);
    }

    public static String replaceWithDivsForPrescricao(String layout, List<Prescricao> prescricoes) {
        return new StringSubstitutor(
                Map.of("prontuario.prescricoes", PrescricaoDocumento.getDivLayout(prescricoes)))
                .replace(layout);
    }
}
