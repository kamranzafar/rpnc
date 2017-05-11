package org.kamranzafar.rpnc;

import org.apache.commons.lang3.StringUtils;

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

        System.out.println("RPNC (Reverse Polish Notation Calculator)");
        System.out.println("by Kamran Zafar\n");
        System.out.println("In order to exit either enter 'exit' or press ctrl+c\n");
        System.out.flush();

        while (true) {
            String line = console.readLine();

            if (StringUtils.isBlank(line)) {
                continue;
            }

            if (line.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                calculator.evaluate(line);
            } catch (InsufficientParametersException e) {
                System.err.println(String.format("operator %s (position: %d): insufficient parameters",
                        e.getOperator(), e.getIndex()));
            } catch (InvalidTokenException e) {
                System.err.println(String.format("invalid token %s (position: %d)",
                        e.getOperator(), e.getIndex()));
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
