package br.vet.certvet.models.mappers;

import br.vet.certvet.dto.requests.prontuario.ProntuarioDTO;
import br.vet.certvet.dto.requests.prontuario.SinaisVitaisDTO;
import br.vet.certvet.dto.requests.prontuario.SuspeitaDiagnosticaDTO;
import br.vet.certvet.exceptions.UnprocessableEntityException;
import br.vet.certvet.models.Prontuario;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ProntuarioDTOMapper {
    public static Prontuario mapper(ProntuarioDTO dto, Prontuario prontuario) {
        final String className = dto.getClass().getName();
        final Method[] methods = ProntuarioDTOMapper.class.getDeclaredMethods();

        Method mapper = null;

        for (Method method : methods) {
            Parameter[] parameters = method.getParameters();

            if (parameters.length != 2)
                continue;
            else if (!parameters[0].getType().getName().equals(className) || !parameters[1].getType().getName().equals(Prontuario.class.getName()))
                continue;

            mapper = method;
            break;
        }

        if (mapper == null)
            throw new UnprocessableEntityException();

        try {
            return (Prontuario) mapper.invoke(null, dto, prontuario);
        } catch (Exception e) {
            throw new UnprocessableEntityException(e);
        }
    }

    private static Prontuario mapSinaisVitaisDTO(SinaisVitaisDTO dto, Prontuario prontuario) {
        return prontuario.setFrequenciaCardiaca(dto.getFrequenciaCardiaca())
                .setFrequenciaRespiratoria(dto.getFrequenciaRespiratoria())
                .setTemperatura(dto.getTemperatura())
                .setPeso(dto.getPeso())
                .setHidratacao(dto.getHidratacao())
                .setTpc(dto.getTpc())
                .setMucosa(dto.getMucosa())
                .setConciencia(dto.getConciencia())
                .setEscoreCorporal(dto.getEscoreCorporal());
    }

    private static Prontuario mapSuspeitaDiagnosticaDTO(SuspeitaDiagnosticaDTO dto, Prontuario prontuario) {
        return prontuario.setSupeitaDiagnostica(dto.getSupeitaDiagnostica());
    }
}
