package org.kamranzafar.rpnc;

import org.kamranzafar.rpnc.impl.RpnCalculator;

import java.io.Console;
import java.text.DecimalFormat;

/**
 * Created by kamran on 11/05/17.
 */
public class Main {
    public static void main(String... args) {
        RpnCalculator calculator = new RpnCalculator();
        Console console = System.console();

        if (console == null) {
            System.err.println("Console error.");
            System.exit(1);
        }

        while (true) {
            String line = console.readLine();

            if (line.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                calculator.evaluate(line);
            } catch (InsufficientParametersException e) {
                System.err.println(String.format("operator %s (position: %d): insufficient parameters",
                        e.getOperator(), e.getIndex()));
            } catch (CalculatorException e) {
                System.err.println(e.getMessage());
            }

            DecimalFormat decimalFormat = new DecimalFormat("0.##########");

            System.out.print("stack:");
            for (Double d : calculator.getValueStack()) {
                System.out.print(" " + decimalFormat.format(d));
            }
            System.out.println();
        }
    }
}
