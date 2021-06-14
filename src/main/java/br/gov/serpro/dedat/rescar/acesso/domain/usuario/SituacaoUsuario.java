package br.gov.serpro.dedat.rescar.acesso.domain.usuario;

import br.gov.serpro.dedat.rescar.acesso.infrastructure.enumeration.EnumUtil;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.enumeration.ValueLabelEnum;

public enum SituacaoUsuario implements ValueLabelEnum<SituacaoUsuario> {

    ATIVO("A", "Ativo"),
    INATIVO("I", "Inativo");

    private String value;
    private String label;

    private SituacaoUsuario(String value, String label) {
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

    @Override
    public String toString() {
        return this.value;
    }

    public static SituacaoUsuario toEnum(String value) {
        return EnumUtil.getByValue(SituacaoUsuario.values(), value);
    }
}