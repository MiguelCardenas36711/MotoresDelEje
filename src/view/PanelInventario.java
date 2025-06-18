package view;

import model.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.text.DecimalFormat;

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

        JButton btnEliminar = new JButton("Eliminar Vehículo");
        btnEliminar.addActionListener(e -> eliminarVehiculoSeleccionado());
        add(btnEliminar, BorderLayout.SOUTH);


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
                        v.getAnio(), Utilidades.formatearPrecio(v.getPrecio()), detalle
                });
            }
        }
    }

    private void eliminarVehiculoSeleccionado() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un vehículo para eliminar.");
            return;
        }

        String marca = modelo.getValueAt(filaSeleccionada, 1).toString();
        String modeloVehiculo = modelo.getValueAt(filaSeleccionada, 2).toString();
        int anio = Integer.parseInt(modelo.getValueAt(filaSeleccionada, 3).toString());

        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Seguro que quieres eliminar el vehículo " + marca + " " + modeloVehiculo + " (" + anio + ")?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            Vehiculo vehiculoAEliminar = null;
            for (Vehiculo v : inventario.obtenerTodos()) {
                if (v.getMarca().equals(marca) && v.getModelo().equals(modeloVehiculo) && v.getAnio() == anio) {
                    vehiculoAEliminar = v;
                    break;
                }
            }

            if (vehiculoAEliminar != null) {
                inventario.eliminarVehiculo(vehiculoAEliminar);
                PersistenciaVehiculos.guardarInventario(inventario.obtenerTodos());
                cargarVehiculosFiltrados();
                JOptionPane.showMessageDialog(this, "Vehículo eliminado correctamente.");
            }
        }
    }



}
