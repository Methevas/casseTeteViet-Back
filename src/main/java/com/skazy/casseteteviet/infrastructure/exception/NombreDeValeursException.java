package com.skazy.casseteteviet.infrastructure.exception;

public class NombreDeValeursException extends Exception{
 String message = "Le nombre de valeurs contenue dans la liste doit être de 9.";
public NombreDeValeursException(){
}

    @Override
    public String getMessage() {
        return message;
    }
}
