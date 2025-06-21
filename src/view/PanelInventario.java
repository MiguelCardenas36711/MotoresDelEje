package view;

import model.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
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
                "Tipo", "Placa", "Marca", "Modelo", "Año", "Color", "Kilometraje",
                "Fecha Ingreso", "Estado", "Transmisión", "Precio", "Detalle"
        }, 0);

        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        tabla.setDefaultRenderer(Object.class, new RenderizadorEstado());


        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Editar Vehiculo:
        JButton btnEditar = new JButton("Editar Vehículo");
        btnEditar.addActionListener(e -> editarVehiculoSeleccionado());
        panelBotones.add(btnEditar);

        // Eliminar Vehiculo:
        JButton btnEliminar = new JButton("Eliminar Vehículo");
        btnEliminar.addActionListener(e -> eliminarVehiculoSeleccionado());
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);


//        JButton btnEliminar = new JButton("Eliminar Vehículo");
//        btnEliminar.addActionListener(e -> eliminarVehiculoSeleccionado());
//        add(btnEliminar, BorderLayout.SOUTH);


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
                        v.obtenerTipo(),
                        v.getPlaca(),
                        v.getMarca(),
                        v.getModelo(),
                        v.getAnio(),
                        v.getColor(),
                        String.format("%,.0f km", v.getKilometraje()),
                        v.getFechaIngreso(),
                        v.getEstado(),
                        v.getTransmision(),
                        Utilidades.formatearPrecio(v.getPrecio()),
                        (v instanceof Carro c)
                                ? c.getNumeroPuertas() + " puertas"
                                : (v instanceof Moto m)
                                ? (m.isTieneSidecar() ? "con sidecar" : "sin sidecar")
                                : ""
                });
            }
        }
    }

    private void editarVehiculoSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un vehículo para editar.");
            return;
        }

        String placa = modelo.getValueAt(fila, 1).toString(); // Columna de placa

        Vehiculo vehiculo = inventario.obtenerTodos().stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst().orElse(null);

        if (vehiculo == null) {
            JOptionPane.showMessageDialog(this, "Vehículo no encontrado.");
            return;
        }

        // Abrimos un formulario con los datos cargados
        FormularioEdicion edicion = new FormularioEdicion(vehiculo, () -> {
            cargarVehiculosFiltrados();
            PersistenciaVehiculos.guardarInventario(inventario.obtenerTodos());
        });
        edicion.setVisible(true);
    }


    private void eliminarVehiculoSeleccionado() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un vehículo para eliminar.");
            return;
        }

        String placa = modelo.getValueAt(filaSeleccionada, 1).toString(); // Columna de la placa

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas eliminar el vehículo con placa " + placa + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            Vehiculo vehiculoAEliminar = null;
            for (Vehiculo v : inventario.obtenerTodos()) {
                if (v.getPlaca().equalsIgnoreCase(placa)) {
                    vehiculoAEliminar = v;
                    break;
                }
            }

            if (vehiculoAEliminar != null) {
                inventario.eliminarVehiculo(vehiculoAEliminar);
                PersistenciaVehiculos.guardarInventario(inventario.obtenerTodos());
                cargarVehiculosFiltrados();
                JOptionPane.showMessageDialog(this, "Vehículo eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el vehículo en el inventario.");
            }
        }
    }


    private class RenderizadorEstado extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {

            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            String estado = table.getValueAt(row, 8).toString(); // columna "Estado"

            if (!isSelected) {
                switch (estado) {
                    case "DISPONIBLE" -> c.setBackground(new Color(204, 255, 204)); // Verde suave
                    case "RESERVADO"  -> c.setBackground(new Color(255, 245, 204)); // Naranja suave
                    case "VENDIDO"    -> c.setBackground(new Color(255, 204, 204)); // Rojo suave
                    default           -> c.setBackground(Color.WHITE);
                }
            } else {
                c.setBackground(table.getSelectionBackground());
            }

            return c;
        }
    }

}
