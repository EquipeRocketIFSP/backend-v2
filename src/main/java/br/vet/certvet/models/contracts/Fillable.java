package br.vet.certvet.models.contracts;

public interface Fillable<Dto> {
    void fill(Dto dto);
}
