package br.gov.serpro.dedat.rescar.acesso.infrastructure.validation.beanvalidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.gov.serpro.dedat.rescar.acesso.infrastructure.enumeration.EnumUtil;
import br.gov.serpro.dedat.rescar.acesso.infrastructure.enumeration.ValueLabelEnum;

public class ValidEnumValidator implements ConstraintValidator<ValidEnum, String> {

    private Class<? extends ValueLabelEnum<?>> enumSelected;

    @Override
    public void initialize(ValidEnum stringEnumeration) {
        this.enumSelected = stringEnumeration.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return EnumUtil.getByClass(this.enumSelected, value) != null;
    }
}