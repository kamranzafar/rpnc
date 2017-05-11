package org.kamranzafar.rpnc;

/**
 * Created by kamran on 11/05/17.
 */
public class InsufficientParametersException extends CalculatorException {
    private String operator;
    private int index;

    public InsufficientParametersException(String operator, int index) {
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
