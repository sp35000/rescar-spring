package br.gov.serpro.dedat.rescar.acesso.infrastructure.enumeration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public final class EnumUtil {

    private EnumUtil() {
    }

    public static <E extends Enum<E> & ValueLabelEnum<E>> Map<Integer, String> getMap(E[] v) {
        LinkedHashMap<Integer, String> mapa = new LinkedHashMap<>();
        for (E e : v) {
            mapa.put(e.ordinal(), e.getLabel());
        }

        return mapa;
    }

    public static <E extends Enum<E> & ValueLabelEnum<E>> E getByLabel(E[] v, String label) {
        for (E e : v) {
            if (e.getLabel().equals(label)) {
                return e;
            }
        }

        return null;
    }

    public static <E extends Enum<E> & ValueLabelEnum<E>> E getByValue(E[] v, String value) {
        for (E e : v) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }

        return null;
    }

    public static <E extends Enum<E> & ValueLabelEnum<E>> E getByValueIgnoreCase(E[] v, String value) {
        for (E e : v) {
            if (e.getValue().equalsIgnoreCase(value)) {
                return e;
            }
        }

        return null;
    }

    public static <E extends Enum<E> & ValueLabelEnum<E>> E getByOrdinal(E[] v, int ordinal) {
        return v[ordinal];
    }

    @SuppressWarnings({"rawtypes", "unchecked" })
    public static <E extends Enum<E> & ValueLabelEnum<E>> E getByClass(Class c, String value) {
        Method mth;

        try {
            mth = c.getDeclaredMethod("values");

            return EnumUtil.getByValue((E[]) mth.invoke(null), value);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new InvalidEnumClassException("EnumUtil.getByClass(Class c, String value)", e);
        }
    }
}