package br.vet.certvet.models.contracts;

public interface Fillable<Dto, Model> {
    void fill(Dto dto, Model model);
}
