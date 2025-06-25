package view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.Vector;

public class PanelHistorialVentas extends JPanel {
    private JTable tabla;
    private DefaultTableModel modelo;

    public PanelHistorialVentas() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Historial de Ventas"));

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtBuscarCedula = new JTextField(15);
        JButton btnBuscar = new JButton("Filtrar");

        panelBusqueda.add(new JLabel("Filtrar por cédula:"));
        panelBusqueda.add(txtBuscarCedula);
        panelBusqueda.add(btnBuscar);

        add(panelBusqueda, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{
                "Fecha", "Cédula", "Nombre", "Placa", "Vehículo", "Color", "Precio", "Combustible"
        }, 0);
        tabla = new JTable(modelo);

        txtBuscarCedula.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { actualizar(); }
            @Override
            public void removeUpdate(DocumentEvent e) { actualizar(); }
            @Override
            public void changedUpdate(DocumentEvent e) { actualizar(); }

            private void actualizar() {
                String cedula = txtBuscarCedula.getText().trim();
                cargarVentas(cedula);
            }
        });

        cargarVentas(null);

        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void cargarVentas(String cedulaFiltrada) {
        modelo.setRowCount(0); // Limpiar tabla

        try (BufferedReader br = new BufferedReader(new FileReader("ventas.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (cedulaFiltrada == null || cedulaFiltrada.isEmpty() || datos[1].contains(cedulaFiltrada)) {
                    modelo.addRow(datos);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar ventas: " + e.getMessage());
        }
    }

}
