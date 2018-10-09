package sv.edu.unab.presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;
import java.util.Arrays;

public class FrmPrincipal extends JFrame {
    private JMenuBar mbMenu;
    private JPanel pnlRoot;
    private JDesktopPane dsktpPane;

    public FrmPrincipal() {
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(this.mbMenu);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        this.setUndecorated(true);
        this.setTitle("Pantalla Principal");
        this.pack();
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
                    System.exit(0);
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        mbMenu = new JMenuBar();
        JMenu catalogos = new JMenu("Catalogos");
        JMenuItem clientes = new JMenuItem("Clientes");
        clientes.addActionListener(evt -> {
            FrmClientes frm = new FrmClientes();
            Dimension desktopSize = dsktpPane.getSize();
            Dimension jInternalFrameSize = frm.getSize();
            frm.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
                    (desktopSize.height- jInternalFrameSize.height)/2);
            boolean isFormOpen = Arrays.stream(dsktpPane.getAllFrames()).allMatch(f -> f.equals(frm));
            if (isFormOpen) {
                dsktpPane.add(frm);
                frm.show();
            } else {
                JOptionPane.showMessageDialog(this, "El formulario ya esta abierto");
            }
        });
        catalogos.add(clientes);
        mbMenu.add(catalogos);
    }
}
