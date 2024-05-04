package com.skazy.casseteteviet.infrastructure.exception;

public class FormatSolutionException extends Exception{
 String message = "Vous devez respecter le format 'valeur1,valeur2,..' pour la liste de valeur à sauvegarder.";
public FormatSolutionException(){
}

 @Override
 public String getMessage() {
  return message;
 }
}
