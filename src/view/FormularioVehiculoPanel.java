package view;

import model.*;

import javax.swing.*;
import javax.swing.border.Border;
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
        inventario = InventarioSingleton.getInstancia();

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(45, 45, 45));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));


        JLabel titulo = new JLabel("➕ Agregar Vehículo", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        add(titulo, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridLayout(0, 2, 12, 10));
        campos.setBackground(getBackground());

        txtPlaca = crearCampo();
        txtMarca = crearCampo();
        txtModelo = crearCampo();
        txtAnio = crearCampo();
        txtColor = crearCampo();
        txtKilometraje = crearCampo();
        txtPrecio = crearCampo();
        txtNumeroPuertas = crearCampo();
        txtFechaIngreso = new JFormattedTextField(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        txtFechaIngreso.setValue(LocalDate.now());
        txtFechaIngreso.setBackground(new Color(60, 60, 60));
        txtFechaIngreso.setForeground(Color.WHITE);
        txtFechaIngreso.setCaretColor(Color.WHITE);
        txtFechaIngreso.setBorder(crearBorde());

        comboTipo = crearCombo("Carro", "Moto");
        comboEstado = new JComboBox<>(Estado.values());
        comboCombustible = new JComboBox<>(Combustible.values());
        comboTransmision = new JComboBox<>(Transmision.values());

        for (JComboBox<?> combo : new JComboBox[]{comboTipo, comboEstado, comboCombustible, comboTransmision}) {
            combo.setBackground(new Color(60, 60, 60));
            combo.setForeground(Color.WHITE);
            combo.setFocusable(false);
        }

        spinnerDuenoAnterior = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        chkSidecar = new JCheckBox("¿Tiene sidecar?");
        chkSidecar.setForeground(Color.LIGHT_GRAY);
        chkSidecar.setBackground(getBackground());

        // Añadir etiquetas y campos
        agregarCampo(campos, "Tipo:", comboTipo);
        agregarCampo(campos, "Placa:", txtPlaca);
        agregarCampo(campos, "Marca:", txtMarca);
        agregarCampo(campos, "Modelo:", txtModelo);
        agregarCampo(campos, "Año:", txtAnio);
        agregarCampo(campos, "Color:", txtColor);
        agregarCampo(campos, "Kilometraje:", txtKilometraje);
        agregarCampo(campos, "Fecha Ingreso:", txtFechaIngreso);
        agregarCampo(campos, "Estado:", comboEstado);
        agregarCampo(campos, "Combustible:", comboCombustible);
        agregarCampo(campos, "Transmisión:", comboTransmision);
        agregarCampo(campos, "Dueños Anteriores:", spinnerDuenoAnterior);
        agregarCampo(campos, "Precio:", txtPrecio);
        agregarCampo(campos, "Número de puertas:", txtNumeroPuertas);
        campos.add(new JLabel(""));
        campos.add(chkSidecar);

        add(campos, BorderLayout.CENTER);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(97, 95, 255));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel botonPanel = new JPanel();
        botonPanel.setBackground(getBackground());
        botonPanel.add(btnGuardar);
        add(botonPanel, BorderLayout.SOUTH);

        comboTipo.addActionListener(e -> actualizarCampos());
        btnGuardar.addActionListener(e -> guardarVehiculo());

        actualizarCampos();
    }

    private void agregarCampo(JPanel panel, String etiqueta, JComponent campo) {
        JLabel label = new JLabel(etiqueta);
        label.setForeground(Color.LIGHT_GRAY);
        panel.add(label);
        panel.add(campo);
    }

    private JTextField crearCampo() {
        JTextField campo = new JTextField();
        campo.setBackground(new Color(60, 60, 60));
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(crearBorde());
        return campo;
    }

    private JComboBox<String> crearCombo(String... opciones) {
        JComboBox<String> combo = new JComboBox<>(opciones);
        combo.setBorder(crearBorde());
        return combo;
    }

    private Border crearBorde() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(90, 90, 90)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        );
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

            if (comboTipo.getSelectedItem().equals("Carro")) {
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
            JOptionPane.showMessageDialog(this, "Error al agregar vehículo:\n" + ex.getMessage());
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
        txtFechaIngreso.setValue(LocalDate.now());
        spinnerDuenoAnterior.setValue(0);
    }
}