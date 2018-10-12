package sv.edu.unab.presentacion;

import sv.edu.unab.dominio.Cliente;
import sv.edu.unab.dominio.Persona;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOG = Logger.getLogger("sv.edu.unab.agenciaviajes");

    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        try {
            FileHandler fileLog = new FileHandler("./agencia_viajes.log", false);
            fileLog.setLevel(Level.ALL);
            LOG.addHandler(fileLog);
            LOG.log(Level.INFO, "[Main][main]");
//            FrmPrincipal frmPrincipal = new FrmPrincipal();
//            frmPrincipal.setVisible(true);
            emf = Persistence.createEntityManagerFactory("AgenciaVueloPU");
            em = emf.createEntityManager();
            Long id = nuevo();
            editar(id);
            eliminar(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Long nuevo() {
        LOG.log(Level.INFO, "[Main][nuevo] INSERCION");
        Long id = null;
        try {
            em.getTransaction().begin();
            Persona datos = new Persona();
            Cliente cliente = new Cliente();
            cliente.setPasaporte("123456789");
            cliente.setVisa("12345678");
            datos.setNombre("Fredy Pastor");
            datos.setApellidoPaterno("Lopez");
            datos.setApellidoMaterno("Cartagena");
            datos.setGenero('M');
            datos.setDui("1234567-8");
            datos.setNit("1234-123456-123-1");
            datos.setFechaNacimiento(LocalDate.now());
            datos.setTelefono("1234-1234");
            datos.setDireccion("Chalatenango");
            datos.setEmail("algo@hotmail.com");
            cliente.setDatosPersonales(datos);
            em.persist(cliente);
            em.flush();
            id = cliente.getId();
            em.getTransaction().commit();
            Query query = em.createQuery("SELECT c FROM Cliente c");
            List<Cliente> resultado = query.getResultList();
            resultado.forEach(c -> {
                System.out.println("ClienteID : " + c.getId());
                System.out.println("PersonaID : " + c.getDatosPersonales().getId());
                System.out.println("Nombre : " + c.getDatosPersonales().getNombre() + " " + c.getDatosPersonales().getApellidoPaterno());
            });
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return id;
    }

    private static void editar(Long id) {
        LOG.log(Level.INFO, "[Main][nuevo] ACTUALIZACION");
        try {
            em.getTransaction().begin();
            Cliente cliente = em.find(Cliente.class, id);
            Persona datos = cliente.getDatosPersonales();
            cliente.setPasaporte("123456789");
            cliente.setVisa("12345678");
            datos.setNombre("Manuel de Jesus");
            datos.setApellidoPaterno("Lopez");
            datos.setApellidoMaterno("Cartagena");
            datos.setGenero('M');
            datos.setDui("1234567-8");
            datos.setNit("1234-123456-123-1");
            datos.setFechaNacimiento(LocalDate.now());
            datos.setTelefono("1234-1234");
            datos.setDireccion("Chalatenango");
            datos.setEmail("algo@hotmail.com");
            cliente.setDatosPersonales(datos);
            em.merge(cliente);
            em.getTransaction().commit();
            Query query = em.createQuery("SELECT c FROM Cliente c");
            List<Cliente> resultado = query.getResultList();
            resultado.forEach(c -> {
                System.out.println("ClienteID : " + c.getId());
                System.out.println("PersonaID : " + c.getDatosPersonales().getId());
                System.out.println("Nombre : " + c.getDatosPersonales().getNombre() + " " + c.getDatosPersonales().getApellidoPaterno());
            });
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public static void eliminar(Long id) {
        LOG.log(Level.INFO, "[Main][nuevo] REMOVER");
        try {
            em.getTransaction().begin();
            Cliente cliente = em.find(Cliente.class, id);
            em.remove(cliente);
            em.getTransaction().commit();
            Query query = em.createQuery("SELECT c FROM Cliente c");
            List<Cliente> resultado = query.getResultList();
            resultado.forEach(c -> {
                System.out.println("ClienteID : " + c.getId());
                System.out.println("PersonaID : " + c.getDatosPersonales().getId());
                System.out.println("Nombre : " + c.getDatosPersonales().getNombre() + " " + c.getDatosPersonales().getApellidoPaterno());
            });
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

}
