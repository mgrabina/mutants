package meli.exceptions;

/**
 * A custom exception indicatitng whether a dna was invalid
 */
public class InvalidDna extends RuntimeException {

    public InvalidDna() {
        super();
    }

    public InvalidDna(String msg) {
        super(msg);
    }

    public InvalidDna(Exception cause) {
        super(cause);
    }
}