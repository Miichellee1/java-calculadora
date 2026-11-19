package calculadora;

/** Pruebas del motor sin dependencias externas. */
public class PruebasMotor {
    private static int total = 0;
    private static int fallos = 0;

    public static void main(String[] args) {
        igual("suma simple", "5", teclear("2+3="));
        igual("decimales sin error de coma flotante", "0.3", teclear("0.1+0.2="));
        igual("resta con resultado negativo", "-1", teclear("9-10="));
        igual("multiplicación", "42", teclear("6*7="));
        igual("división con decimales", "2.5", teclear("10/4="));
        igual("división por cero", "Error", teclear("5/0="));
        igual("operaciones encadenadas (izq. a der.)", "20", teclear("2+3*4="));
        igual("después de un error se reinicia", "7", teclear("5/0=7"));
        igual("no admite dos puntos", "1.5", teclear("1.5."));
        igual("no admite ceros a la izquierda", "7", teclear("007"));

        MotorCalculadora m = new MotorCalculadora();
        escribir(m, "12");
        m.borrar();
        igual("borrar quita el último dígito", "1", m.getPantalla());
        m.borrar();
        igual("borrar el último dígito deja 0", "0", m.getPantalla());

        m = new MotorCalculadora();
        escribir(m, "7");
        m.negar();
        igual("negar cambia el signo", "-7", m.getPantalla());

        m = new MotorCalculadora();
        escribir(m, "9");
        m.limpiar();
        igual("limpiar vuelve a 0", "0", m.getPantalla());

        System.out.println(total + " pruebas, " + fallos + " fallos");
        if (fallos > 0) {
            System.exit(1);
        }
    }

    private static String teclear(String secuencia) {
        MotorCalculadora m = new MotorCalculadora();
        escribir(m, secuencia);
        return m.getPantalla();
    }

    private static void escribir(MotorCalculadora m, String secuencia) {
        for (char c : secuencia.toCharArray()) {
            if (Character.isDigit(c)) m.digito(c);
            else if (c == '.') m.punto();
            else if (c == '=') m.igual();
            else m.operador(c);
        }
    }

    private static void igual(String nombre, String esperado, String actual) {
        total++;
        if (esperado.equals(actual)) {
            System.out.println("OK    " + nombre);
        } else {
            fallos++;
            System.out.println("FALLO " + nombre + " -> esperado '" + esperado + "', obtenido '" + actual + "'");
        }
    }
}
