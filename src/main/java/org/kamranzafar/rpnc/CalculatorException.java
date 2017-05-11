package org.kamranzafar.rpnc;

/**
 * Created by kamran on 11/05/17.
 */
public class CalculatorException extends RuntimeException {
    public CalculatorException() {
    }

    public CalculatorException(String message) {
        super(message);
    }

    public CalculatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
