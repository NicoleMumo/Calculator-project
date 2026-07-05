public class Calculator {
    private double result;
    private String history;

    public Calculator() {
        this.result = 0.0;
        this.history = "No calculations yet";
    }

    public double add(double a, double b) {
        result = a + b;
        history = a + " + " + b + " = " + result;
        return result;
    }

    public double subtract(double a, double b) {
        result = a - b;
        history = a + " - " + b + " = " + result;
        return result;
    }

    public double multiply(double a, double b) {
        result = a * b;
        history = a + " * " + b + " = " + result;
        return result;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero.");
        }
        result = a / b;
        history = a + " / " + b + " = " + result;
        return result;
    }

    public double power(double base, double exponent) {
        result = Math.pow(base, exponent);
        history = base + " ^ " + exponent + " = " + result;
        return result;
    }

    public double squareRoot(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("Cannot square root a negative number.");
        }
        result = Math.sqrt(value);
        history = "sqrt(" + value + ") = " + result;
        return result;
    }

    public double getResult() {
        return result;
    }

    public String getHistory() {
        return history;
    }

    public void clear() {
        result = 0.0;
        history = "Cleared";
    }
}
