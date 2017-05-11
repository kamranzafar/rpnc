package org.kamranzafar.rpnc;

/**
 * Created by kamran on 12/05/17.
 */
public class InvalidTokenException extends CalculatorException {
    private String operator;
    private int index;

    public InvalidTokenException(String operator, int index) {
        this.operator = operator;
        this.index = index;
    }

    public String getOperator() {
        return operator;
    }

    public int getIndex() {
        return index;
    }
}
