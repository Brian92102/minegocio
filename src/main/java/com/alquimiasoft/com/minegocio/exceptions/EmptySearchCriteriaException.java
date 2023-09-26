package com.alquimiasoft.com.minegocio.exceptions;


import org.springframework.http.HttpStatus;

public class EmptySearchCriteriaException extends RequestException {

    private static String emptySearchCriteria = "invalid search parameters";

    public EmptySearchCriteriaException(HttpStatus status, String message) {
        super(status, message);
    }

    public static String getEmptySearchCriteria() {
        return emptySearchCriteria;
    }
}
