import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PaneNumerico extends JDialog {
    private JTextField textField;
    private double result;
    private boolean isValid;

    public PaneNumerico(Frame owner, String message) {
        super(owner, "Input", true);
        owner.setLayout(new BorderLayout());

        JButton okButton = new JButton("Aceptar");
        okButton.addActionListener(e -> {
            try {
                result = Double.parseDouble(textField.getText());
                isValid = true;
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido.");
            }
        });
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 30));

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != '.') {
                    e.consume();
                }
                if(c == KeyEvent.VK_ENTER) {
                    okButton.doClick();
                }

            }
        });

        add(new JLabel(message), BorderLayout.NORTH);
        add(textField, BorderLayout.CENTER);



        add(okButton, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    public double getResult() {
        return result;
    }

    public boolean isValid() {
        return isValid;
    }
}
