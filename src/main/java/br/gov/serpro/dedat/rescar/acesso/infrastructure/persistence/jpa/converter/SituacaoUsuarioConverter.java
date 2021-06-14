package br.gov.serpro.dedat.rescar.acesso.infrastructure.persistence.jpa.converter;

import javax.persistence.AttributeConverter;

import br.gov.serpro.dedat.rescar.acesso.domain.usuario.SituacaoUsuario;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.enumeration.EnumUtil;

public class SituacaoUsuarioConverter implements AttributeConverter<SituacaoUsuario, String> {

    @Override
    public String convertToDatabaseColumn(SituacaoUsuario enumeration) {
        if (enumeration != null) {
            return enumeration.getValue();
        }

        return null;
    }

    @Override
    public SituacaoUsuario convertToEntityAttribute(String value) {
        if (value != null) {
            return EnumUtil.getByValue(SituacaoUsuario.values(), value);
        }

        return null;
    }
}