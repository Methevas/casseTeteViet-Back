package com.skazy.casseteteviet.infrastructure.exception;

public class NombreAutoriseException extends Exception{
 String message = "Vous ne devez utiliser que les nombres de 1 à 9";
public NombreAutoriseException(){
}

 @Override
 public String getMessage() {
  return message;
 }
}
