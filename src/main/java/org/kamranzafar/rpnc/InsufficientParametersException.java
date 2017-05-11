package org.kamranzafar.rpnc;

/**
 * Created by kamran on 11/05/17.
 */
public class InsufficientParametersException extends InvalidTokenException {
    public InsufficientParametersException(String operator, int index) {
        super(operator, index);
    }
}
