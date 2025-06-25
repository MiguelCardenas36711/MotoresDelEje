package view;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;

    public LoginFrame() {
        setTitle("Inicio de Sesión - Motores del Eje");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(false); // Puedes poner true para eliminar bordes OS
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(new GridBagLayout());

        // Componentes
        JLabel lblTitulo = new JLabel("MOTORES DEL EJE");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(220, 220, 220));

        JLabel lblUsuario = new JLabel("Usuario:");
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblUsuario.setForeground(Color.LIGHT_GRAY);
        lblContrasena.setForeground(Color.LIGHT_GRAY);

        txtUsuario = new JTextField("admin", 15);
        txtContrasena = new JPasswordField("1234", 15);
        btnIngresar = new JButton("Ingresar");

        configurarEstiloCampo(txtUsuario);
        configurarEstiloCampo(txtContrasena);
        configurarEstiloBoton(btnIngresar);

        // Layout con GridBag
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        add(lblTitulo, gbc);

        gbc.gridy++;
        add(lblUsuario, gbc);
        gbc.gridy++;
        add(txtUsuario, gbc);

        gbc.gridy++;
        add(lblContrasena, gbc);
        gbc.gridy++;
        add(txtContrasena, gbc);

        gbc.gridy++;
        add(btnIngresar, gbc);

        btnIngresar.addActionListener(e -> validarCredenciales());

        setVisible(true);
    }

    private void configurarEstiloCampo(JTextField campo) {
        campo.setBackground(new Color(50, 50, 50));
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
    }

    private void configurarEstiloBoton(JButton boton) {
        boton.setBackground(new Color(97, 95, 255));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void validarCredenciales() {
        String usuario = txtUsuario.getText();
        String contrasena = new String(txtContrasena.getPassword());

        if (usuario.equals("admin") && contrasena.equals("1234")) {
            JOptionPane.showMessageDialog(this, "¡Bienvenido, " + usuario + "!");
            dispose();
            new VentanaPrincipal();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
        }
    }
}
