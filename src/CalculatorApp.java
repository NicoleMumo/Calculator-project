import java.awt.GraphicsEnvironment;
import java.util.Scanner;
import javax.swing.*;

public class CalculatorApp {
    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            runConsoleMode();
            return;
        }

        SwingUtilities.invokeLater(() -> {
            CalculatorGUI gui = new CalculatorGUI();
            gui.setVisible(true);
        });
    }

    private static void runConsoleMode() {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        System.out.println("No display found. Running console calculator mode.");

        while (true) {
            System.out.print("Enter first number (or q to quit): ");
            String firstInput = scanner.nextLine().trim();

            if (firstInput.equalsIgnoreCase("q") || firstInput.equalsIgnoreCase("quit") || firstInput.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            try {
                double firstNumber = Double.parseDouble(firstInput);
                System.out.print("Choose an operator (+, -, *, /, ^): ");
                String operator = scanner.nextLine().trim();
                if (operator.equalsIgnoreCase("q") || operator.equalsIgnoreCase("quit") || operator.equalsIgnoreCase("exit")) {
                    System.out.println("Goodbye!");
                    break;
                }
                System.out.print("Enter second number: ");
                String secondRaw = scanner.nextLine().trim();
                double secondNumber = 0;
                boolean hasSecond = true;

                if (operator.equalsIgnoreCase("r") || operator.equalsIgnoreCase("sqrt") || operator.equals("√")) {
                    // square root uses only the first number
                    hasSecond = false;
                } else {
                    secondNumber = Double.parseDouble(secondRaw);
                }

                double result;
                switch (operator) {
                    case "+":
                        result = calculator.add(firstNumber, secondNumber);
                        break;
                    case "-":
                        result = calculator.subtract(firstNumber, secondNumber);
                        break;
                    case "*":
                        result = calculator.multiply(firstNumber, secondNumber);
                        break;
                    case "/":
                        result = calculator.divide(firstNumber, secondNumber);
                        break;
                    case "^":
                        result = calculator.power(firstNumber, secondNumber);
                        break;
                    case "r":
                    case "sqrt":
                    case "√":
                        result = calculator.squareRoot(firstNumber);
                        break;
                    default:
                        System.out.println("Unknown operator.");
                        continue;
                }

                System.out.println("Result: " + result);
            } catch (NumberFormatException e) {
                // detect when user accidentally pasted shell commands into the running app
                if (firstInput.startsWith("javac") || firstInput.startsWith("java") || firstInput.contains("src/") || firstInput.startsWith("@")) {
                    System.out.println("It looks like you pasted shell commands. Run those in your terminal, not inside the calculator. To quit type 'q'.");
                } else {
                    System.out.println("Please enter a valid number.");
                }
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            }
        }

        scanner.close();
    }
}
