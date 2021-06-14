package br.gov.serpro.dedat.rescar.acesso.infrastructure.enumeration;

public interface ValueLabelEnum<C extends Enum<C>> {

    String getValue();

    String getLabel();
}