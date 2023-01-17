package io.github.guiwespinola.msavaliadorcredito.application.exception;

public class ClientDataNotFoundException extends Exception {
    public ClientDataNotFoundException() {
        super("Client Data not found for the given CPF.");
    }
}
