package br.vet.certvet.services.implementation.helper;

import br.vet.certvet.models.*;
import br.vet.certvet.models.especializacoes.Doc;
import br.vet.certvet.models.especializacoes.PrescricaoDocumento;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.text.StringSubstitutor;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProntuarioPdfHelper {

    public static String fillLayoutFields(Prontuario prontuario, String layout) {
        final Animal animal = prontuario.getAnimal();
        final Usuario veterinario = prontuario.getVeterinario();
        final Usuario tutor = prontuario.getTutor();
        final Clinica clinica = prontuario.getClinica();
        final List<Documento> documentos = prontuario.getDocumentos();
        final Documento obito = getObito(prontuario);

        return new StringSubstitutor(ImmutableMap.<String, String>builder()
                .put("animal.nome",                     animal.getNome())
                .put("animal.especie",                  animal.getEspecie())
                .put("animal.raca",                     animal.getRaca())
                .put("animal.sexo",                     animal.getSexo().name().toLowerCase())
                .put("animal.idade",                    String.valueOf(animal.getIdade()))
                .put("animal.pelagem",                  animal.getPelagem())
                .put("clinica.razaoSocial",             clinica.getRazaoSocial())
                .put("clinica.telefone",                clinica.getTelefone())
                .put("cidade",                          clinica.getCidade())
                .put("documento.observacaoVet",         getObservacaoVet(documentos))
                .put("documento.observacaoTutor",       getObservacaoTutor(documentos))
                .put("prontuario.anestesia",            getDocumentoTipo(documentos, "anestesia"))
                .put("prontuario.terapias",             getDocumentoTipo(documentos, "terapeutico"))
                .put("prontuario.obito.causa",          obito.getCausaMortis())
                .put("documento.causaMortis",           obito.getCausaMortisDescription())
                .put("documento.orientaDestinoCorpo",   obito.getOrientaDestinoCorpo())
                .put("prontuario.obito.local",          obito.getLocal())
                .put("documento.outrasObservacoes",     obito.getObservacoes())
                .put("prontuario.obito.horas",          obito.getDataHoraObito().toString())
                .put("prontuario.obito.data",           obito.getDataHoraObito().toString())
                .put("prontuario.codigo",               prontuario.getCodigo())
                .put("data.dia",                        String.valueOf(prontuario.getDataAtendimento().getDayOfMonth()))
                .put("data.mes",                        prontuario.getMonthAtendimento())
                .put("data.ano",                        String.valueOf(prontuario.getDataAtendimento().getYear()))
                .put("prontuario.exames",               prontuario.getExames().toString())
                .put("prontuario.cirurgia",             prontuario.getCirurgia().toString())
                .put("tutor.nome",                      tutor.getNome())
                .put("tutor.cpf",                       tutor.getCpf())
                .put("tutor.endereco",                  tutor.getEnderecoCompleto())
                .put("veterinario.nome",                veterinario.getNome())
                .put("veterinario.crmv",                veterinario.getRegistroCRMV())
                .build()
        ).replace(layout);
    }

    private static String getObservacaoTutor(List<Documento> documentos) {
        return documentos.stream()
                .filter(Objects::nonNull)
                .map(Documento::getObservacaoTutor)
                .toString();
    }

    private static String getObservacaoVet(List<Documento> documentos) {
        return documentos.stream()
                .filter(Objects::nonNull)
                .map(Documento::getObservacaoVet)
                .toString();
    }

    private static String getDocumentoTipo(List<Documento> documentos, String tipo) {
        return documentos.stream()
                .filter(Objects::nonNull)
                .filter(documento -> documento.getTipo().equals(tipo))
                .toString();
    }

    private static Documento getObito(Prontuario prontuario) {
        return prontuario.getDocumentos()
                .stream()
                .filter(Objects::nonNull)
                .filter(documento -> documento.getTipo().equals("obito"))
                .findFirst()
                .orElse(Documento.empty());
    }

    public static String replaceWithDivs(Doc documento, String layout) {
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
