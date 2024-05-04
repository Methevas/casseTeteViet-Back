package com.skazy.casseteteviet.infrastructure.exception;

public class SolutionNouvelleException extends Exception{

    String message = "Vous ne pouvez pas modifier une solution qui n'existe pas.";
    public SolutionNouvelleException(){
    }

    @Override
    public String getMessage() {
        return message;
    }
}
