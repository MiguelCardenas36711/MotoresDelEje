package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormularioVehiculoPanel extends JPanel {
    private JTextField txtPlaca, txtMarca, txtModelo, txtAnio, txtPrecio, txtColor, txtKilometraje, txtNumeroPuertas;
    private JCheckBox chkSidecar;
    private JComboBox<String> comboTipo;
    private JComboBox<Estado> comboEstado;
    private JComboBox<Combustible> comboCombustible;
    private JComboBox<Transmision> comboTransmision;
    private JSpinner spinnerDuenoAnterior;
    private JFormattedTextField txtFechaIngreso;
    private JButton btnGuardar;
    private InventarioVehiculos inventario;

    public FormularioVehiculoPanel() {
        inventario = InventarioSingleton.getInstancia(); // En producción, se pasaría como parámetro
        setLayout(new GridLayout(0, 2, 10, 10));
        setBorder(BorderFactory.createTitledBorder("Agregar Vehículo"));

        txtPlaca = new JTextField();
        txtColor = new JTextField();
        txtKilometraje = new JTextField();
        comboEstado = new JComboBox<>(Estado.values());
        comboCombustible = new JComboBox<>(Combustible.values());
        comboTransmision = new JComboBox<>(Transmision.values());
        spinnerDuenoAnterior = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        txtFechaIngreso = new JFormattedTextField(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        txtFechaIngreso.setValue(LocalDate.now());
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
        add(new JLabel("Placa:"));
        add(txtPlaca);
        add(new JLabel("Marca:"));
        add(txtMarca);
        add(new JLabel("Modelo:"));
        add(txtModelo);
        add(new JLabel("Año:"));
        add(txtAnio);
        add(new JLabel("Color:"));
        add(txtColor);
        add(new JLabel("Kilometraje:"));
        add(txtKilometraje);
        add(new JLabel("Fecha de Ingreso:"));
        add(txtFechaIngreso);
        add(new JLabel("Estado:"));
        add(comboEstado);
        add(new JLabel("Combustible:"));
        add(comboCombustible);
        add(new JLabel("Transmisión:"));
        add(comboTransmision);
        add(new JLabel("Dueños Anteriores:"));
        add(spinnerDuenoAnterior);
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
            String placa = txtPlaca.getText();
            String marca = txtMarca.getText();
            String modelo = txtModelo.getText();
            int anio = Integer.parseInt(txtAnio.getText());
            String color = txtColor.getText();
            double kilometraje = Double.parseDouble(txtKilometraje.getText());
            LocalDate fechaIngreso = LocalDate.parse(txtFechaIngreso.getText());
            double precio = Double.parseDouble(txtPrecio.getText());
            Estado estado = (Estado) comboEstado.getSelectedItem();
            Combustible combustible = (Combustible) comboCombustible.getSelectedItem();
            Transmision transmision = (Transmision) comboTransmision.getSelectedItem();
            int duenos = (int) spinnerDuenoAnterior.getValue();

// Luego, según el tipo seleccionado:
            if (comboTipo.getSelectedItem().equals("Carro")) {
//                int numeroPuertas = (int) spinnerPuertas.getValue();
                int puertas = Integer.parseInt(txtNumeroPuertas.getText());
                Carro carro = new Carro(placa, marca, modelo, anio, color, kilometraje,
                        fechaIngreso, precio, estado, combustible,
                        transmision, duenos, puertas);
                inventario.agregarVehiculo(carro);
            } else {
                boolean tieneSidecar = chkSidecar.isSelected();
                Moto moto = new Moto(placa, marca, modelo, anio, color, kilometraje,
                        fechaIngreso, precio, estado, combustible,
                        transmision, duenos, tieneSidecar);
                inventario.agregarVehiculo(moto);
            }

            PersistenciaVehiculos.guardarInventario(inventario.obtenerTodos());
            JOptionPane.showMessageDialog(this, "Vehículo agregado exitosamente");
            limpiarFormulario();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar vehículo: " + ex.getMessage());
        }
    }

    private void limpiarFormulario() {
        txtPlaca.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtAnio.setText("");
        txtColor.setText("");
        txtKilometraje.setText("");
        txtPrecio.setText("");
        txtNumeroPuertas.setText("");
        chkSidecar.setSelected(false);
    }
}
