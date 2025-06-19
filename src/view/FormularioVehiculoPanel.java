package view;

import model.*;

import javax.swing.*;
import java.awt.*;

public class FormularioVehiculoPanel extends JPanel {
    private JTextField txtMarca, txtModelo, txtAnio, txtPrecio, txtNumeroPuertas;
    private JCheckBox chkSidecar;
    private JComboBox<String> comboTipo;
    private JButton btnGuardar;
    private InventarioVehiculos inventario;

    public FormularioVehiculoPanel() {
        inventario = InventarioSingleton.getInstancia(); // En producción, se pasaría como parámetro
        setLayout(new GridLayout(0, 2, 10, 10));
        setBorder(BorderFactory.createTitledBorder("Agregar Vehículo"));

        txtMarca = new JTextField();
        txtModelo = new JTextField();
        txtAnio = new JTextField();
        txtPrecio = new JTextField();
        txtNumeroPuertas = new JTextField();
        chkSidecar = new JCheckBox("¿Tiene sidecar?");
        comboTipo = new JComboBox<>(new String[]{"Carro", "Moto"});
        btnGuardar = new JButton("Guardar");

        add(new JLabel("Tipo de Vehículo:"));
        add(comboTipo);
        add(new JLabel("Marca:"));
        add(txtMarca);
        add(new JLabel("Modelo:"));
        add(txtModelo);
        add(new JLabel("Año:"));
        add(txtAnio);
        add(new JLabel("Precio:"));
        add(txtPrecio);
        add(new JLabel("Número de puertas:"));
        add(txtNumeroPuertas);
        add(new JLabel("")); // espacio vacío
        add(chkSidecar);
        add(new JLabel(""));
        add(btnGuardar);

        // Mostrar u ocultar campos según tipo
        comboTipo.addActionListener(e -> actualizarCampos());

        // Acción guardar
        btnGuardar.addActionListener(e -> guardarVehiculo());

        actualizarCampos(); // Inicializa el formulario
    }

    private void actualizarCampos() {
        boolean esCarro = comboTipo.getSelectedItem().equals("Carro");
        txtNumeroPuertas.setEnabled(esCarro);
        chkSidecar.setEnabled(!esCarro);
    }

    private void guardarVehiculo() {
        try {
            String marca = txtMarca.getText();
            String modelo = txtModelo.getText();
            int anio = Integer.parseInt(txtAnio.getText());
            double precio = Double.parseDouble(txtPrecio.getText());

            Vehiculo v;
            if (comboTipo.getSelectedItem().equals("Carro")) {
                int puertas = Integer.parseInt(txtNumeroPuertas.getText());
                v = new Carro(marca, modelo, anio, precio, puertas);
            } else {
                boolean sidecar = chkSidecar.isSelected();
                v = new Moto(marca, modelo, anio, precio, sidecar);
            }

            inventario.agregarVehiculo(v);
            PersistenciaVehiculos.guardarInventario(inventario.obtenerTodos());
            JOptionPane.showMessageDialog(this, "Vehículo agregado exitosamente");
            limpiarFormulario();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar vehículo: " + ex.getMessage());
        }
    }

    private void limpiarFormulario() {
        txtMarca.setText("");
        txtModelo.setText("");
        txtAnio.setText("");
        txtPrecio.setText("");
        txtNumeroPuertas.setText("");
        chkSidecar.setSelected(false);
    }
}
