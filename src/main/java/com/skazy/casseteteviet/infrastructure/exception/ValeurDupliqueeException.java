package com.skazy.casseteteviet.infrastructure.exception;

public class ValeurDupliqueeException  extends Exception{
 String message = "Vous ne pouvez utiliser qu'une seul fois chaque chiffre de 1 à 9.";
public ValeurDupliqueeException(){
}

    @Override
    public String getMessage() {
        return message;
    }
}
