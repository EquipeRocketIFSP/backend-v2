package br.vet.certvet.models.mappers;

import br.vet.certvet.exceptions.UnprocessableEntityException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class DTOMapper {

    private DTOMapper(){}
    @SuppressWarnings("unchecked")
    public static <D, M, F> M assignToModel(D dto, M model, Class<F> dtoMapper) {
        final String className = dto.getClass().getName();
        final Method[] methods = dtoMapper.getDeclaredMethods();

        Method mapper = null;

        for (Method method : methods) {
            Parameter[] parameters = method.getParameters();

            if (parameters.length != 2)
                continue;
            else if (!parameters[0].getType().getName().equals(className) || !parameters[1].getType().getName().equals(model.getClass().getName()))
                continue;

            mapper = method;
            break;
        }

        if (mapper == null)
            throw new UnprocessableEntityException();

        try {
            return (M) mapper.invoke(null, dto, model);
        } catch (Exception e) {
            throw new UnprocessableEntityException(e);
        }
    }
}
