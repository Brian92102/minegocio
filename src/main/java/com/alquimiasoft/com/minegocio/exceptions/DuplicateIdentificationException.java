package com.alquimiasoft.com.minegocio.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateIdentificationException extends RuntimeException {

    private static String duplicateIdentification = "A client with the same ID was found";

    public DuplicateIdentificationException(HttpStatus badRequest, String message) {
        super(message);
    }

    public static String getDuplicateIdentification() {
        return duplicateIdentification;
    }
}
