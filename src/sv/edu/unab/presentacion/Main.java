package sv.edu.unab.presentacion;

import sv.edu.unab.dominio.Persona;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
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
//            FrmPrincipal frmPrincipal = new FrmPrincipal();
//            frmPrincipal.setVisible(true);
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("AgenciaVueloPU");
            EntityManager em = emf.createEntityManager();
            Query query = em.createNamedQuery("Persona.findAll");
            List<Persona> resultado = query.getResultList();
            resultado.forEach(p -> System.out.println("Persona : " + p));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
