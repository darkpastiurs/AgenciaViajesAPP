package sv.edu.unab.presentacion;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOG = Logger.getLogger("sv.edu.unab.agenciaviajes");

    public static void main(String[] args) {
        try {
            FileHandler fileLog = new FileHandler("./agencia_viajes.log", false);
            fileLog.setLevel(Level.ALL);
            LOG.addHandler(fileLog);
            LOG.log(Level.INFO, "[Main][main]");
            FrmClientes frmPrincipal = new FrmClientes();
            frmPrincipal.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
