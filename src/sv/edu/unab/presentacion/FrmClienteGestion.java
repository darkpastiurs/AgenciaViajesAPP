package sv.edu.unab.presentacion;

import com.toedter.calendar.JDateChooser;
import sv.edu.unab.dominio.Cliente;
import sv.edu.unab.dominio.Persona;
import sv.edu.unab.infraestructura.ClienteCI;
import sv.edu.unab.presentacion.utils.Utilidades;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public class FrmClienteGestion extends JDialog {
    private static final Logger LOG = Logger.getLogger("sv.edu.unab.agenciaviajes");

    private JPanel contentPane;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JTextField txtNombre;
    private JTextField txtApellidoPaterno;
    private JTextField txtApellidoMaterno;
    private JFormattedTextField ftxtDUI;
    private JFormattedTextField ftxtNIT;
    private JTextField txtCorreo;
    private JTextArea txtaDireccion;
    private JDateChooser dcNacimiento;
    private JFormattedTextField ftxtTelefono;

    private Cliente clienteSeleccionado;
    private ClienteCI clienteCI;

    public FrmClienteGestion(Cliente cliente, Frame parent){
        super(parent, true);
        LOG.log(INFO, "[FrmClienteGestion][FrmClienteGestion] -> {0}", new Object[]{cliente});
        clienteCI = new ClienteCI();
        clienteSeleccionado = cliente;
        setContentPane(contentPane);
        getRootPane().setDefaultButton(btnGuardar);

        btnGuardar.addActionListener(e -> onGuardar());

        btnCancelar.addActionListener(e -> onCancel());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.pack();
        setLocationRelativeTo(null);
        Utilidades.duiFormato(ftxtDUI, this);
        Utilidades.nitFormato(ftxtNIT, this);
        Utilidades.telefonoFormato(ftxtTelefono, this);
        llenarCampos();
    }

    public FrmClienteGestion() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnGuardar);

        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onGuardar();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    private void llenarCampos(){
        if(clienteSeleccionado != null){
            txtNombre.setText(clienteSeleccionado.getDatosPersonales().getNombre());
            txtApellidoPaterno.setText(clienteSeleccionado.getDatosPersonales().getApellidoPaterno());
            txtApellidoMaterno.setText(clienteSeleccionado.getDatosPersonales().getApellidoMaterno());
            ftxtDUI.setValue(clienteSeleccionado.getDatosPersonales().getDui());
            ftxtNIT.setValue(clienteSeleccionado.getDatosPersonales().getNit());
            dcNacimiento.setDate(Date.valueOf(clienteSeleccionado.getDatosPersonales().getFechaNacimiento()));
            ftxtTelefono.setValue(clienteSeleccionado.getDatosPersonales().getTelefono());
            txtCorreo.setText(clienteSeleccionado.getDatosPersonales().getEmail());
            txtaDireccion.setText(clienteSeleccionado.getDatosPersonales().getDireccion());
        }
    }

    private void onGuardar() {
        // add your code here
        try {
            Cliente cliente = new Cliente();
            Persona persona = new Persona();
            persona.setNombre(txtNombre.getText());
            persona.setApellidoPaterno(txtApellidoPaterno.getText());
            persona.setApellidoMaterno(txtApellidoMaterno.getText().isEmpty() ? null : txtApellidoMaterno.getText());
            persona.setDui(ftxtDUI.getText());
            persona.setNit(ftxtNIT.getText());
            persona.setFechaNacimiento(dcNacimiento.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            persona.setTelefono(ftxtTelefono.getText());
            persona.setEmail(txtCorreo.getText());
            persona.setDireccion(txtaDireccion.getText());
            if(clienteSeleccionado != null){
                cliente.setId(clienteSeleccionado.getId());
                if(clienteSeleccionado.getDatosPersonales() != null){
                   persona.setId(clienteSeleccionado.getDatosPersonales().getId());
                }
            }
            cliente.setDatosPersonales(persona);
            long currentInit = System.currentTimeMillis();
            if(clienteSeleccionado != null){
//                clienteCI.actualizarCliente(cliente);
            } else {
//                clienteCI.registrarCliente(cliente);
            }
            long currentFin = System.currentTimeMillis();
            long time = currentFin - currentInit;
            LOG.log(INFO, "[FrmClienteGestion][onGuardar] -> Tiempo de ejecucion {0}", new Object[]{time});
            this.setVisible(false);
            this.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        FrmClienteGestion dialog = new FrmClienteGestion();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        dcNacimiento = new JDateChooser();
        dcNacimiento.setDateFormatString("EEEE dd 'de' MMMMM 'de' yyyy");
    }
}
