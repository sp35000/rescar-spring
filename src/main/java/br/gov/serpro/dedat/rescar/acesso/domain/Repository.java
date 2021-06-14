package br.gov.serpro.dedat.rescar.acesso.domain;

import java.util.List;

public interface Repository<E, I> {

    public void add(E obj);

    public void remove(E obj);

    public E get(I id);

    public boolean exists(E obj);

    public List<E> all();
}