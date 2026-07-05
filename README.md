
Simple OOP Java Calculator

This repository contains a small Java calculator implemented with OOP principles and a Swing GUI.

Files:
- `src/Calculator.java` — calculation logic (model).
- `src/CalculatorGUI.java` — Swing UI (pink theme) with keyboard shortcuts and history.
- `src/CalculatorApp.java` — entry point; falls back to console mode if no display is available.

Build & run (recommended on your laptop):

```bash
cd /workspaces/Calculator-project
./build_and_run.sh
java -jar out/calculator.jar
```

Or compile & run directly:

```bash
javac -d out src/*.java
java -cp out CalculatorApp
```

Notes:
- The `out/` directory contains build artifacts and is ignored by git (`.gitignore` is provided).
- If you run the app in a headless environment (Codespaces, WSL without X, SSH without X forwarding), `CalculatorApp` will automatically use the console mode. Run locally to see the GUI.


