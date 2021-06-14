package br.gov.serpro.dedat.rescar.acesso.infrastructure.persistence.jpa.converter;

import javax.persistence.AttributeConverter;

import br.gov.serpro.dedat.rescar.acesso.domain.usuario.Perfil;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.enumeration.EnumUtil;

public class PerfilConverter implements AttributeConverter<Perfil, String> {

    @Override
    public String convertToDatabaseColumn(Perfil enumeration) {
        if (enumeration != null) {
            return enumeration.getValue();
        }

        return null;
    }

    @Override
    public Perfil convertToEntityAttribute(String value) {
        if (value != null) {
            return EnumUtil.getByValue(Perfil.values(), value);
        }

        return null;
    }
}