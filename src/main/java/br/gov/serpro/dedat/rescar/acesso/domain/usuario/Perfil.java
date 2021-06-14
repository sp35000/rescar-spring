package br.gov.serpro.dedat.rescar.acesso.domain.usuario;

import br.gov.serpro.dedat.rescar.acesso.infrastructure.enumeration.EnumUtil;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.enumeration.ValueLabelEnum;

public enum Perfil implements ValueLabelEnum<Perfil> {

    COLABORADOR("C", "Colaborador"),
    MOTORISTA("M", "Motorista"),
    ADMINISTRADOR("A", "Administrador");

    private String value;
    private String label;

    private Perfil(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public static Perfil toEnum(String value) {
        return EnumUtil.getByValue(Perfil.values(), value);
    }
}