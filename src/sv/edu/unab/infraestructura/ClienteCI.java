package sv.edu.unab.infraestructura;

import sv.edu.unab.dominio.Cliente;
import sv.edu.unab.negocio.ClienteCN;
import sv.edu.unab.negocio.util.Filtro;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public class ClienteCI {

    private ClienteCN clienteCN = new ClienteCN();
    private static final Logger LOG = Logger.getLogger("sv.edu.unab.agenciaviajes");

    public List<Cliente> obtenerListado(List<Filtro> filtros){
        LOG.log(INFO, "[ClienteCI][obtenerListado]");
        return clienteCN.obtenerListadoClientes.apply(filtros);
    }

    public void registrarCliente(Cliente cliente){
        LOG.log(INFO, "[ClienteCI][registrarCliente] -> {0}", new Object[]{cliente});
        clienteCN.registrar.accept(cliente);
    }

    public void actualizarCliente(Cliente cliente){
        LOG.log(INFO, "[ClienteCI][actualizarCliente] -> {0}", new Object[]{cliente});
        clienteCN.actualizar.accept(cliente);
    }

    public void eliminarCliente(Cliente cliente){
        LOG.log(INFO, "[ClienteCI][eliminarCliente] -> {0}", new Object[]{cliente});
        clienteCN.eliminar.accept(cliente);
    }

}
