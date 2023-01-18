package io.github.guiwespinola.msavaliadorcredito.application.exception;

public class CustomerDataNotFoundException extends Exception {
    public CustomerDataNotFoundException() {
        super("Client Data not found for the given CPF.");
    }
}
