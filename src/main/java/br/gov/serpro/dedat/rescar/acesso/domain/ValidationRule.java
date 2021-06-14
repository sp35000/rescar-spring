package br.gov.serpro.dedat.rescar.acesso.domain;

public interface ValidationRule<T> {

    void validate(T obj);

}