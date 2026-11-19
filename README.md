# 🧮 Calculadora en Java (Swing)

Calculadora con interfaz gráfica hecha con **Java Swing**. La lógica está separada de la interfaz (`MotorCalculadora`), lo que permite probarla sin abrir ninguna ventana.

## Características

- Suma, resta, multiplicación y división
- Decimales, cambio de signo (±), borrar último dígito y limpiar
- Control de división por cero
- Redondeo para evitar errores de coma flotante (`0.1 + 0.2 = 0.3`)
- Funciona con el mouse **y con el teclado** (números, `+ - * /`, `Enter`, `Backspace`, `Esc`)
- Pruebas automáticas y CI con GitHub Actions

## Requisitos

- JDK 11 o superior
- Un entorno con pantalla (por la interfaz gráfica)

## Ejecutar

**Linux / macOS**
```bash
bash run.sh
```

**Windows**
```bat
run.bat
```

**A mano**
```bash
mkdir out
javac -encoding UTF-8 -d out src/calculadora/*.java
java -cp out calculadora.Main
```

## Pruebas

```bash
bash test.sh        # Windows: test.bat
```

## Estructura

```
src/calculadora/
├── Main.java               # punto de entrada
├── CalculadoraGUI.java     # ventana y botones (Swing)
└── MotorCalculadora.java   # lógica de cálculo
test/calculadora/PruebasMotor.java
.github/workflows/ci.yml
```

## Nota

Las operaciones se evalúan de izquierda a derecha, como en una calculadora básica: `2 + 3 × 4 = 20`.

## Ideas para mejorar

- Precedencia de operadores (`2 + 3 × 4 = 14`)
- Historial de operaciones
- Porcentaje, raíz cuadrada y memoria

## Licencia

MIT
