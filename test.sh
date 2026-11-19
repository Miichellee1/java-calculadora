#!/usr/bin/env bash
set -e
mkdir -p out
javac -encoding UTF-8 -d out $(find src test -name "*.java")
java -cp out calculadora.PruebasMotor
