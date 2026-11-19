package calculadora;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Lógica de la calculadora, independiente de la interfaz gráfica.
 * Las operaciones se evalúan de izquierda a derecha (como una calculadora básica).
 */
public class MotorCalculadora {
    private static final int MAX_DIGITOS = 15;

    private String pantalla = "0";
    private double acumulado = 0;
    private char operadorPendiente = 0;
    private boolean nuevaEntrada = true;
    private boolean error = false;

    public String getPantalla() {
        return pantalla;
    }

    public void digito(char d) {
        if (error) limpiar();
        if (nuevaEntrada || pantalla.equals("0")) {
            pantalla = String.valueOf(d);
            nuevaEntrada = false;
        } else if (pantalla.length() < MAX_DIGITOS) {
            pantalla += d;
        }
    }

    public void punto() {
        if (error) limpiar();
        if (nuevaEntrada) {
            pantalla = "0.";
            nuevaEntrada = false;
        } else if (!pantalla.contains(".")) {
            pantalla += ".";
        }
    }

    public void operador(char op) {
        if (error) return;
        if (operadorPendiente != 0 && !nuevaEntrada) {
            aplicar();
            if (error) return;
        } else {
            acumulado = Double.parseDouble(pantalla);
        }
        operadorPendiente = op;
        nuevaEntrada = true;
    }

    public void igual() {
        if (error || operadorPendiente == 0) return;
        aplicar();
        if (error) return;
        operadorPendiente = 0;
        nuevaEntrada = true;
    }

    public void negar() {
        if (error || pantalla.equals("0")) return;
        pantalla = pantalla.startsWith("-") ? pantalla.substring(1) : "-" + pantalla;
    }

    public void borrar() {
        if (error || nuevaEntrada) return;
        pantalla = pantalla.substring(0, pantalla.length() - 1);
        if (pantalla.isEmpty() || pantalla.equals("-")) {
            pantalla = "0";
        }
    }

    public void limpiar() {
        pantalla = "0";
        acumulado = 0;
        operadorPendiente = 0;
        nuevaEntrada = true;
        error = false;
    }

    private void aplicar() {
        double b = Double.parseDouble(pantalla);
        switch (operadorPendiente) {
            case '+': acumulado += b; break;
            case '-': acumulado -= b; break;
            case '*': acumulado *= b; break;
            case '/':
                if (b == 0) {
                    marcarError();
                    return;
                }
                acumulado /= b;
                break;
            default: acumulado = b;
        }
        pantalla = formatear(acumulado);
    }

    private void marcarError() {
        pantalla = "Error";
        error = true;
        operadorPendiente = 0;
        nuevaEntrada = true;
    }

    private String formatear(double valor) {
        if (Double.isNaN(valor) || Double.isInfinite(valor)) {
            marcarError();
            return "Error";
        }
        String texto = new BigDecimal(valor)
            .setScale(10, RoundingMode.HALF_UP)
            .stripTrailingZeros()
            .toPlainString();
        return texto.length() > 20 ? String.format("%.8e", valor) : texto;
    }
}
