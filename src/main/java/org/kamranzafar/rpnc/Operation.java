package org.kamranzafar.rpnc;

/**
 * Created by kamran on 11/05/17.
 */
public enum Operation {
    ADDITION("+", 2), SUBTRACTION("-", 2), DIVISION("/", 2), MULTIPLICATION("*", 2), SQUARE_ROOT("sqrt", 1), UNDO("undo", 0), CLEAR("clear", 0);

    private String operator;
    private int operands;

    Operation(String operator, int operands) {
        this.operator = operator;
        this.operands = operands;
    }

    public static Operation fromOperator(String operator) {
        for (Operation operation : Operation.values()) {
            if (operation.operator().equalsIgnoreCase(operator)) {
                return operation;
            }
        }

        return null;
    }

    public String operator() {
        return operator;
    }

    public int operands() {
        return operands;
    }
}
