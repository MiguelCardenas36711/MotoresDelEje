package view;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private JPanel panelLateral;
    private JPanel panelCentral;
    private CardLayout cardLayout;

    public VentanaPrincipal() {
        setTitle("Motores del Eje - Concesionario");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        iniciarComponentes();
        setVisible(true);
    }

    private void iniciarComponentes() {
        panelLateral = new JPanel();
        panelLateral.setLayout(new GridLayout(5, 1));
        panelLateral.setBackground(new Color(30, 30, 30));
        panelLateral.setPreferredSize(new Dimension(200, 0));

        JButton btnInventario = new JButton("Inventario");
        JButton btnAgregar = new JButton("Agregar Vehículo");
        JButton btnSalir = new JButton("Salir");

        // Eventos
        btnInventario.addActionListener(e -> cardLayout.show(panelCentral, "inventario"));
        btnAgregar.addActionListener(e -> cardLayout.show(panelCentral, "formulario"));
        btnSalir.addActionListener(e -> System.exit(0));

        panelLateral.add(btnInventario);
        panelLateral.add(btnAgregar);
        panelLateral.add(btnSalir);

        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);

        panelCentral.add(new JLabel("Bienvenido al concesionario"), "inicio");
        panelCentral.add(new PanelInventario(), "inventario");
        panelCentral.add(new FormularioVehiculoPanel(), "formulario");

        add(panelLateral, BorderLayout.WEST);
        add(panelCentral, BorderLayout.CENTER);

        JPanel panelGenerales = new JPanel(new GridLayout(4, 2));
        panelGenerales.setBorder(BorderFactory.createTitledBorder("Datos Generales"));

        JPanel panelTipo = new JPanel(new GridLayout(2, 2));
        panelTipo.setBorder(BorderFactory.createTitledBorder("Detalles del Vehículo"));

    }
}
