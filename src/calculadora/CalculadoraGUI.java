package calculadora;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

/** Ventana de la calculadora (Swing). Soporta mouse y teclado. */
public class CalculadoraGUI extends JFrame {
    private final MotorCalculadora motor = new MotorCalculadora();
    private final JTextField pantalla = new JTextField("0");

    public CalculadoraGUI() {
        super("Calculadora");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pantalla.setEditable(false);
        pantalla.setHorizontalAlignment(JTextField.RIGHT);
        pantalla.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
        pantalla.setPreferredSize(new Dimension(300, 60));

        add(pantalla, BorderLayout.NORTH);
        add(crearTeclado(), BorderLayout.CENTER);
        configurarTeclado();

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private JPanel crearTeclado() {
        JPanel p = new JPanel(new GridBagLayout());
        boton(p, "C", "C", 0, 0, 1);
        boton(p, "\u2190", "DEL", 1, 0, 1);
        boton(p, "\u00B1", "NEG", 2, 0, 1);
        boton(p, "\u00F7", "/", 3, 0, 1);

        boton(p, "7", "7", 0, 1, 1);
        boton(p, "8", "8", 1, 1, 1);
        boton(p, "9", "9", 2, 1, 1);
        boton(p, "\u00D7", "*", 3, 1, 1);

        boton(p, "4", "4", 0, 2, 1);
        boton(p, "5", "5", 1, 2, 1);
        boton(p, "6", "6", 2, 2, 1);
        boton(p, "\u2212", "-", 3, 2, 1);

        boton(p, "1", "1", 0, 3, 1);
        boton(p, "2", "2", 1, 3, 1);
        boton(p, "3", "3", 2, 3, 1);
        boton(p, "+", "+", 3, 3, 1);

        boton(p, "0", "0", 0, 4, 2);
        boton(p, ".", ".", 2, 4, 1);
        boton(p, "=", "=", 3, 4, 1);
        return p;
    }

    private void boton(JPanel panel, String texto, String token, int x, int y, int ancho) {
        JButton b = new JButton(texto);
        b.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        b.setFocusable(false);
        b.addActionListener(e -> procesar(token));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = ancho;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.ipadx = 20;
        c.ipady = 16;
        c.insets = new Insets(3, 3, 3, 3);
        panel.add(b, c);
    }

    private void procesar(String token) {
        switch (token) {
            case "C": motor.limpiar(); break;
            case "DEL": motor.borrar(); break;
            case "NEG": motor.negar(); break;
            case ".": motor.punto(); break;
            case "=": motor.igual(); break;
            case "+":
            case "-":
            case "*":
            case "/":
                motor.operador(token.charAt(0));
                break;
            default:
                if (token.length() == 1 && Character.isDigit(token.charAt(0))) {
                    motor.digito(token.charAt(0));
                }
        }
        pantalla.setText(motor.getPantalla());
    }

    private void configurarTeclado() {
        JRootPane root = getRootPane();
        InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = root.getActionMap();

        for (char ch : "0123456789.+-*/=".toCharArray()) {
            String clave = "tecla_" + ch;
            im.put(KeyStroke.getKeyStroke(ch), clave);
            am.put(clave, accion(String.valueOf(ch)));
        }
        im.put(KeyStroke.getKeyStroke("ENTER"), "tecla_ENTER");
        am.put("tecla_ENTER", accion("="));
        im.put(KeyStroke.getKeyStroke("BACK_SPACE"), "tecla_DEL");
        am.put("tecla_DEL", accion("DEL"));
        im.put(KeyStroke.getKeyStroke("ESCAPE"), "tecla_C");
        am.put("tecla_C", accion("C"));
    }

    private Action accion(String token) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesar(token);
            }
        };
    }
}
