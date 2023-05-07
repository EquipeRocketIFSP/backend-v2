package br.vet.certvet.models.factories;

import br.vet.certvet.dto.requests.prontuario.ProntuarioDTO;
import br.vet.certvet.dto.requests.prontuario.SinaisVitaisDTO;
import br.vet.certvet.exceptions.UnprocessableEntityException;
import br.vet.certvet.models.Prontuario;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class ProntuarioFactory {
    public static Prontuario factory(ProntuarioDTO dto) {
        String className = dto.getClass().getSimpleName();

        Optional<Method> factory = Arrays.stream(ProntuarioFactory.class.getDeclaredMethods())
                .filter((method) -> method.getName().equals("factoryFrom" + className))
                .findFirst();

        if (factory.isEmpty())
            throw new UnprocessableEntityException();

        try {
            return (Prontuario) factory.get().invoke(null, dto);
        } catch (Exception e) {
            throw new UnprocessableEntityException(e);
        }
    }

    /**
     * Invocado via reflection. Não remover.
     */
    private static Prontuario factoryFromSinaisVitaisDTO(SinaisVitaisDTO dto) {
        Prontuario prontuario = new Prontuario();

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
}
