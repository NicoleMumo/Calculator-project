import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.text.DefaultCaret;

public class CalculatorGUI extends JFrame {
    private final Calculator calculator;
    private final JTextField display;
    private final JTextArea historyArea;
    private String currentInput = "0";
    private String pendingOperator = "";
    private double firstNumber = 0;
    private boolean startNewInput = true;

    public CalculatorGUI() {
        super("Pink OOP Calculator");
        this.calculator = new Calculator();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 520);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(255, 240, 247));
        setLayout(new BorderLayout(10, 10));

        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("SansSerif", Font.BOLD, 28));
        display.setBackground(new Color(255, 248, 250));
        display.setForeground(new Color(90, 40, 70));
        display.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        add(display, BorderLayout.NORTH);
        // History panel on the right
        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setBackground(new Color(255, 245, 247));
        historyArea.setForeground(new Color(90, 40, 70));
        historyArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
        JScrollPane historyScroll = new JScrollPane(historyArea);
        historyScroll.setPreferredSize(new Dimension(160, 0));
        add(historyScroll, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(new Color(255, 240, 247));

        String[] buttonLabels = {
                "C", "^", "√", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", ".", "=", ""
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label.isEmpty() ? "" : label);
            button.setFocusPainted(false);
            button.setFont(new Font("SansSerif", Font.BOLD, 18));
            button.setForeground(new Color(90, 40, 70));
            button.setBackground(label.matches("[+\\-*/^]") ? new Color(255, 205, 220) : new Color(255, 240, 247));
            button.setBorder(BorderFactory.createLineBorder(new Color(245, 200, 215), 1));
            button.setPreferredSize(new Dimension(60, 60));

            button.addActionListener(e -> handleButtonClick(label));
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        registerKeyBindings();
    }

    private void handleButtonClick(String label) {
        switch (label) {
            case "C":
                clear();
                break;
            case "=":
                evaluate();
                break;
            case "√":
                applySquareRoot();
                break;
            case "+", "-", "*", "/", "^":
                chooseOperator(label);
                break;
            case ".":
                insertDecimal();
                break;
            default:
                appendDigit(label);
                break;
        }
    }

    private void appendDigit(String digit) {
        if (startNewInput) {
            currentInput = "";
            startNewInput = false;
        }
        if (digit.equals("0") && currentInput.equals("0")) {
            return;
        }
        currentInput += digit;
        display.setText(currentInput);
    }

    private void insertDecimal() {
        if (startNewInput) {
            currentInput = "0";
            startNewInput = false;
        }
        if (!currentInput.contains(".")) {
            currentInput += ".";
            display.setText(currentInput);
        }
    }

    private void chooseOperator(String operator) {
        if (pendingOperator.isEmpty()) {
            firstNumber = Double.parseDouble(currentInput);
            pendingOperator = operator;
            startNewInput = true;
            display.setText("0");
        } else {
            evaluate();
            firstNumber = Double.parseDouble(currentInput);
            pendingOperator = operator;
            startNewInput = true;
            display.setText("0");
        }
    }

    private void evaluate() {
        if (pendingOperator.isEmpty()) {
            return;
        }

        try {
            double secondNumber = Double.parseDouble(currentInput);
            double result;

            switch (pendingOperator) {
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
                default:
                    result = firstNumber;
            }

            currentInput = formatResult(result);
            display.setText(currentInput);
            pendingOperator = "";
            startNewInput = true;
            appendHistory();
        } catch (RuntimeException ex) {
            display.setText("Error");
            currentInput = "0";
            pendingOperator = "";
            startNewInput = true;
        }
    }

    private void applySquareRoot() {
        try {
            double value = Double.parseDouble(currentInput);
            double result = calculator.squareRoot(value);
            currentInput = formatResult(result);
            display.setText(currentInput);
            pendingOperator = "";
            startNewInput = true;
            appendHistory();
        } catch (RuntimeException ex) {
            display.setText("Error");
            currentInput = "0";
            pendingOperator = "";
            startNewInput = true;
        }
    }

    private void clear() {
        calculator.clear();
        currentInput = "0";
        firstNumber = 0;
        pendingOperator = "";
        startNewInput = true;
        display.setText("0");
        appendHistory();
    }

    private String formatResult(double value) {
        if (value == Math.rint(value)) {
            return String.format("%.0f", value);
        }
        return String.valueOf(value);
    }

    private void appendHistory() {
        String h = calculator.getHistory();
        if (h == null || h.isEmpty()) return;
        historyArea.append(h + "\n");
        DefaultCaret caret = (DefaultCaret)historyArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        historyArea.setCaretPosition(historyArea.getDocument().getLength());
    }

    private void registerKeyBindings() {
        InputMap im = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getRootPane().getActionMap();

        // digits
        for (int i = 0; i <= 9; i++) {
            final String key = String.valueOf(i);
            im.put(KeyStroke.getKeyStroke(key), "digit" + key);
            am.put("digit" + key, new AbstractAction() {
                public void actionPerformed(ActionEvent e) { handleButtonClick(key); }
            });
        }

        // operators and controls
        String[] ops = {"+","-","*","/","^",".","="};
        for (String op : ops) {
            String name = op.equals("=") ? "equals" : "op" + op;
            im.put(KeyStroke.getKeyStroke(op.charAt(0)), name);
            am.put(name, new AbstractAction() { public void actionPerformed(ActionEvent e) { handleButtonClick(op.equals("=") ? "=" : op); } });
        }

        // Enter -> equals
        im.put(KeyStroke.getKeyStroke("ENTER"), "equals");
        // Clear: C or c
        im.put(KeyStroke.getKeyStroke('C'), "clear");
        im.put(KeyStroke.getKeyStroke('c'), "clear");
        am.put("clear", new AbstractAction() { public void actionPerformed(ActionEvent e) { handleButtonClick("C"); } });

        // Square root: r or R
        im.put(KeyStroke.getKeyStroke('r'), "sqrt");
        im.put(KeyStroke.getKeyStroke('R'), "sqrt");
        am.put("sqrt", new AbstractAction() { public void actionPerformed(ActionEvent e) { handleButtonClick("√"); } });
    }
}
