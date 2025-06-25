package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class FormularioEdicion extends JDialog {
    private final Vehiculo vehiculo;
    private final Runnable onGuardar; // callback para refrescar tabla

    private JTextField txtColor, txtKilometraje, txtPrecio;
    private JComboBox<Estado> comboEstado;
    private JComboBox<Combustible> comboCombustible;
    private JComboBox<Transmision> comboTransmision;

    public FormularioEdicion(Vehiculo vehiculo, Runnable onGuardar) {
        this.vehiculo = vehiculo;
        this.onGuardar = onGuardar;

        setTitle("Editar Vehículo - " + vehiculo.getPlaca());
        setSize(400, 300);
        setModal(true);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
//        setLayout(new GridLayout(6, 2, 5, 5));
        setLayout(new GridLayout(0, 2, 5, 5));

        txtColor = new JTextField(vehiculo.getColor());
        txtKilometraje = new JTextField(String.valueOf(vehiculo.getKilometraje()));
        txtPrecio = new JTextField(String.valueOf(vehiculo.getPrecio()));

        comboEstado = new JComboBox<>(Estado.values());
        comboEstado.setSelectedItem(vehiculo.getEstado());

        comboCombustible = new JComboBox<>(Combustible.values());
        comboCombustible.setSelectedItem(vehiculo.getTipoCombustible());

        comboTransmision = new JComboBox<>(Transmision.values());
        comboTransmision.setSelectedItem(vehiculo.getTransmision());

        add(new JLabel("Color:"));
        add(txtColor);

        add(new JLabel("Kilometraje (km):"));
        add(txtKilometraje);

        add(new JLabel("Precio ($):"));
        add(txtPrecio);

        add(new JLabel("Estado:"));
        add(comboEstado);

        add(new JLabel("Combustible:"));
        add(comboCombustible);

        add(new JLabel("Transmisión:"));
        add(comboTransmision);

        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.addActionListener(e -> guardarCambios());
        add(btnGuardar);
    }

    private void guardarCambios() {
        try {
            vehiculo.setColor(txtColor.getText());
            vehiculo.setKilometraje(Double.parseDouble(txtKilometraje.getText()));
            vehiculo.setPrecio(Double.parseDouble(txtPrecio.getText()));
            vehiculo.setEstado((Estado) comboEstado.getSelectedItem());
            vehiculo.setTipoCombustible((Combustible) comboCombustible.getSelectedItem());
            vehiculo.setTransmision((Transmision) comboTransmision.getSelectedItem());

            JOptionPane.showMessageDialog(this, "Vehículo actualizado correctamente.");
            onGuardar.run(); // refresca tabla y guarda archivo
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
    }
}
