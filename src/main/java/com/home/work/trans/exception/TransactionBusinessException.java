package com.home.work.trans.exception;

public class TransactionBusinessException extends RuntimeException{

    public TransactionBusinessException(String message) {
        super(message);
    }
}
