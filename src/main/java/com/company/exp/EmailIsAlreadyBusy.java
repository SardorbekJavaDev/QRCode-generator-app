package com.company.exp;

public class EmailIsAlreadyBusy extends RuntimeException {
    public EmailIsAlreadyBusy(String message) {
        super(message);
    }
}
