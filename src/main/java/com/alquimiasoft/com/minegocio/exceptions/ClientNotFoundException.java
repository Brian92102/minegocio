package com.alquimiasoft.com.minegocio.exceptions;

import org.springframework.http.HttpStatus;

public class ClientNotFoundException extends RequestException {

    private static String clientNotFound = "invalid search parameters";

    public ClientNotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public static String getClientNotFound() {
        return clientNotFound;
    }
}
