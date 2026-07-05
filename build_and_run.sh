#!/usr/bin/env bash
set -euo pipefail

# Build and create runnable jar
mkdir -p out
javac -d out src/*.java
jar cfe out/calculator.jar CalculatorApp -C out .

echo "Built out/calculator.jar"
echo "Run locally with: java -jar out/calculator.jar"
