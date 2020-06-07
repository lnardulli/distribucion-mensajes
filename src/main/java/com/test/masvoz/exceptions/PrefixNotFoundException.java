package com.test.masvoz.exceptions;

public class PrefixNotFoundException extends RuntimeException {

    public static PrefixNotFoundException of(String prefix) {
        return new PrefixNotFoundException(prefix);
    }

    public PrefixNotFoundException(String prefix) {
        super(String.format("El prefijo %s no es soportado por ningun provedor.", prefix));
    }
}
