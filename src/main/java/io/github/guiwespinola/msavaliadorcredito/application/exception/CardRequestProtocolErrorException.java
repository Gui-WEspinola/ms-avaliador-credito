package io.github.guiwespinola.msavaliadorcredito.application.exception;

public class CardRequestProtocolErrorException extends RuntimeException{
    public CardRequestProtocolErrorException(String message) {
        super(message);
    }
}
