package view;

import model.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelInventario extends JPanel {
    private JTable tabla;
    private DefaultTableModel modelo;
    private InventarioVehiculos inventario;
    private JTextField txtMarcaFiltro;
    private JComboBox<String> comboTipoFiltro;

    public PanelInventario() {
        inventario = InventarioSingleton.getInstancia();

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Inventario de Vehículos"));

        // Top: filtros
        JPanel panelFiltros = new JPanel();
        txtMarcaFiltro = new JTextField(10);
        comboTipoFiltro = new JComboBox<>(new String[]{"Todos", "Carro", "Moto"});
        JButton btnFiltrar = new JButton("Filtrar");

        panelFiltros.add(new JLabel("Marca:"));
        panelFiltros.add(txtMarcaFiltro);
        panelFiltros.add(new JLabel("Tipo:"));
        panelFiltros.add(comboTipoFiltro);
        panelFiltros.add(btnFiltrar);

        add(panelFiltros, BorderLayout.NORTH);

        // Centro: tabla
        modelo = new DefaultTableModel(new String[]{
                "Tipo", "Marca", "Modelo", "Año", "Precio", "Detalle"
        }, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Botón adicional si quieres seguir con "Cargar Datos"
        // add(btnCargar, BorderLayout.SOUTH);

        txtMarcaFiltro.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { cargarVehiculosFiltrados(); }
            public void removeUpdate(DocumentEvent e) { cargarVehiculosFiltrados(); }
            public void changedUpdate(DocumentEvent e) { cargarVehiculosFiltrados(); }
        });

        comboTipoFiltro.addActionListener(e -> cargarVehiculosFiltrados());

        // Evento
        btnFiltrar.addActionListener(e -> cargarVehiculosFiltrados());

        cargarVehiculosFiltrados();
    }

    private void cargarVehiculos() {
        modelo.setRowCount(0); // Limpia la tabla
        List<Vehiculo> lista = inventario.obtenerTodos();

        for (Vehiculo v : lista) {
            String detalle = "";
            if (v instanceof Carro carro) {
                detalle = carro.getNumeroPuertas() + " puertas";
            } else if (v instanceof Moto moto) {
                detalle = moto.isTieneSidecar() ? "con sidecar" : "sin sidecar";
            }

            modelo.addRow(new Object[]{
                    v.obtenerTipo(),
                    v.getMarca(),
                    v.getModelo(),
                    v.getAnio(),
                    "$" + v.getPrecio(),
                    detalle
            });
        }
    }

    private void cargarVehiculosFiltrados() {
        modelo.setRowCount(0); // limpia tabla

        String marcaFiltro = txtMarcaFiltro.getText().trim().toLowerCase();
        String tipoFiltro = comboTipoFiltro.getSelectedItem().toString();

        for (Vehiculo v : inventario.obtenerTodos()) {
            boolean coincideMarca = marcaFiltro.isEmpty()
                    || v.getMarca().toLowerCase().contains(marcaFiltro);

            boolean coincideTipo = tipoFiltro.equals("Todos")
                    || v.obtenerTipo().equalsIgnoreCase(tipoFiltro);

            if (coincideMarca && coincideTipo) {
                String detalle = (v instanceof Carro c)
                        ? c.getNumeroPuertas() + " puertas"
                        : (v instanceof Moto m)
                        ? (m.isTieneSidecar() ? "con sidecar" : "sin sidecar")
                        : "";

                modelo.addRow(new Object[]{
                        v.obtenerTipo(), v.getMarca(), v.getModelo(),
                        v.getAnio(), "$" + v.getPrecio(), detalle
                });
            }
        }
    }

}
