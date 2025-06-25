package view;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private JPanel panelLateral;
    private JPanel panelCentral;
    private CardLayout cardLayout;

    private PanelInventario panelInventario;
    private FormularioVehiculoPanel panelFormulario;
    private PanelHistorialVentas panelHistorial;
    private PanelVenta panelVenta;

    public VentanaPrincipal() {
        setTitle("Motores del Eje - Concesionario");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout());

        iniciarComponentes();
        setVisible(true);
    }

    private void iniciarComponentes() {
        panelLateral = new JPanel(new GridLayout(6, 1, 0, 10));
        panelLateral.setBackground(new Color(40, 40, 40));
        panelLateral.setPreferredSize(new Dimension(220, 0));
        panelLateral.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));

        JButton btnInicio = crearBoton("Inicio");
        JButton btnInventario = crearBoton("Inventario");
        JButton btnAgregar = crearBoton("Agregar Vehículo");
        JButton btnVenta = crearBoton("Registrar Venta");
        JButton btnHistorial = crearBoton("Historial");
        JButton btnSalir = crearBoton("Salir");

        panelLateral.add(btnInicio);
        panelLateral.add(btnInventario);
        panelLateral.add(btnAgregar);
        panelLateral.add(btnVenta);
        panelLateral.add(btnHistorial);
        panelLateral.add(btnSalir);

        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);
        panelCentral.setBackground(new Color(45, 45, 45));

        // Panel Bienvenida
        JPanel panelBienvenida = new JPanel(new BorderLayout());
        panelBienvenida.setBackground(new Color(45, 45, 45));

        JLabel lblBienvenida = new JLabel("Bienvenido a Motores del Eje", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("k2d", Font.BOLD, 28));
        lblBienvenida.setForeground(new Color(230, 230, 230));

        panelBienvenida.add(lblBienvenida, BorderLayout.CENTER);

        // Vistas reales
        panelInventario = new PanelInventario();
        panelFormulario = new FormularioVehiculoPanel();
        panelVenta = new PanelVenta(() -> {
            panelHistorial = new PanelHistorialVentas();
            panelCentral.add(panelHistorial, "historial");
            cardLayout.show(panelCentral, "historial");
        });
        panelHistorial = new PanelHistorialVentas();

        panelCentral.add(panelBienvenida, "inicio");
        panelCentral.add(panelInventario, "inventario");
        panelCentral.add(panelFormulario, "formulario");
        panelCentral.add(panelVenta, "venta");
        panelCentral.add(panelHistorial, "historial");

        // Agregar a la ventana
        add(panelLateral, BorderLayout.WEST);
        add(panelCentral, BorderLayout.CENTER);

        // Acciones
        btnInicio.addActionListener(e -> cardLayout.show(panelCentral, "inicio"));

        btnInventario.addActionListener(e -> {
            panelInventario.cargarVehiculosFiltrados();
            cardLayout.show(panelCentral, "inventario");
        });

        btnAgregar.addActionListener(e -> {
//            panelFormulario.reiniciarFormulario();
            cardLayout.show(panelCentral, "formulario");
        });

        btnVenta.addActionListener(e -> {
            panelVenta.recargarVehiculos();
            cardLayout.show(panelCentral, "venta");
        });

        btnHistorial.addActionListener(e -> {
            panelHistorial = new PanelHistorialVentas();
            panelCentral.add(panelHistorial, "historial");
            cardLayout.show(panelCentral, "historial");
        });

        btnSalir.addActionListener(e -> System.exit(0));

        // Mostrar bienvenida al inicio
        cardLayout.show(panelCentral, "inicio");
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(60, 60, 60));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }
}
