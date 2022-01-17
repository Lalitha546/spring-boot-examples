package com.customer.exception;

public class LogicalException  extends RuntimeException{
    private static final String LOGICAL_MESSAGE="An Application error occurred while processing the request - ";

    public LogicalException(){
        super(LOGICAL_MESSAGE);
    }
    public LogicalException(String message){
        super(LOGICAL_MESSAGE + message);
    }
    public LogicalException(Throwable cause){
        super(LOGICAL_MESSAGE + cause.getMessage());
    }
    public  LogicalException(String message,Throwable cause){
        super(LOGICAL_MESSAGE+message+ ". Root cause - "+ cause.getMessage());
    }
}
