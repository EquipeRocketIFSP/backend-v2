package br.vet.certvet.models.contracts;

/**
 *
 * @param <D> Dto
 */
public interface Fillable<D> {
    void fill(D dto);
}
