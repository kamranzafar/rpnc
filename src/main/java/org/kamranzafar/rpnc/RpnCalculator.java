package org.kamranzafar.rpnc;

import java.math.BigDecimal;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created by kamran on 11/05/17.
 */
public class RpnCalculator {
    private static int DEFAULT_PRECISION = 15;

    private Stack<Double> valueStack = new Stack<>();
    // keeps track of all operations for 'undo' operation
    private Stack<OperationInput> operationStack = new Stack<>();

    private int precision;
    private int tokenIndex;

    class OperationInput {
        private Stack<Double> operands = new Stack<>();

        OperationInput(Double... operands) {
            for (Double d : operands) {
                this.operands.push(d);
            }
        }

        public Stack<Double> getOperands() {
            return operands;
        }
    }

    public RpnCalculator() {
        this(DEFAULT_PRECISION);
    }

    public RpnCalculator(int precision) {
        this.precision = precision;
    }

    private void processToken(String token) {
        Operation operation = Operation.fromOperator(token);

        if (operation == null) {
            try {
                Double value = Double.parseDouble(token);
                valueStack.push(value);
                operationStack.push(new OperationInput());

                return;
            } catch (Throwable e) {
                throw new InvalidTokenException(token, tokenIndex);
            }
        } else if (valueStack.size() < operation.operands()) {
            throw new InsufficientParametersException(token, tokenIndex);
        }

        switch (operation) {
            case ADDITION:
                calculate(valueStack.pop(), valueStack.pop(), (x, y) -> y + x);
                break;
            case SUBTRACTION:
                calculate(valueStack.pop(), valueStack.pop(), (x, y) -> y - x);
                break;
            case MULTIPLICATION:
                calculate(valueStack.pop(), valueStack.pop(), (x, y) -> y * x);
                break;
            case DIVISION:
                calculate(valueStack.pop(), valueStack.pop(), (x, y) -> y / x);
                break;
            case SQUARE_ROOT:
                calculate(valueStack.pop(), Math::sqrt);
                break;
            case UNDO:
                undoLastOperation();
                break;
            case CLEAR:
                clearStacks();
                break;
        }
    }

    private void calculate(Double operand1, Double operand2, BinaryOperation binaryOperation) {
        Double res = BigDecimal.valueOf(binaryOperation.process(operand1, operand2))
                .setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();

        operationStack.push(new OperationInput(operand1, operand2));
        valueStack.push(res);
    }

    private void calculate(Double operand, UnaryOperation unaryOperation) {
        Double res = BigDecimal.valueOf(unaryOperation.process(operand))
                .setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();

        operationStack.push(new OperationInput(operand));
        valueStack.push(res);
    }

    private void clearStacks() {
        valueStack.clear();
        operationStack.clear();
    }

    private void undoLastOperation() {
        if (!valueStack.isEmpty()) {
            valueStack.pop();
        }

        if (!operationStack.empty()) {
            OperationInput operationInput = operationStack.pop();

            while (!operationInput.getOperands().empty()) {
                valueStack.push(operationInput.getOperands().pop());
            }
        }
    }

    public void evaluate(String input) {
        StringTokenizer tokenizer = new StringTokenizer(input);

        tokenIndex = 0;

        while (tokenizer.hasMoreTokens()) {
            processToken(tokenizer.nextToken());
            tokenIndex++;
        }
    }

    public Stack<Double> getValueStack() {
        return valueStack;
    }
}