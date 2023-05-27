package br.vet.certvet.services.implementation.helper;

import br.vet.certvet.models.*;
import br.vet.certvet.models.especializacoes.Doc;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.text.StringSubstitutor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
                .put("documento.observacaoVet",         documentos.stream().filter(Objects::nonNull).map(Documento::getObservacaoVet).toString())
                .put("documento.observacaoTutor",       documentos.stream().filter(Objects::nonNull).map(Documento::getObservacaoTutor).toString())
                .put("prontuario.anestesia",            documentos.stream().filter(Objects::nonNull).filter(documento -> documento.getTipo().equals("anestesia")).toString())
                .put("prontuario.terapias",             documentos.stream().filter(Objects::nonNull).filter(documento -> documento.getTipo().equals("terapeutico")).toString())
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

}
