package sv.edu.unab.negocio;

import sv.edu.unab.dominio.Cliente;
import sv.edu.unab.dominio.Persona;
import sv.edu.unab.negocio.util.Filtro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.logging.Level.INFO;

public class ClienteCN {

    private static final Logger LOG = Logger.getLogger("sv.edu.unab.agenciaviajes");

    public Function<List<Filtro>, List<Cliente>> obtenerListadoClientes = (filtros) -> {
        LOG.log(INFO, "[ClienteCN][obtenerListadoClientes]");
        StringJoiner likeParameter = new StringJoiner("", "%", "%");
        List<Cliente> listado = null;
        StringJoiner sqlBuilder = new StringJoiner(" ");
        sqlBuilder.add("SELECT * FROM avr.clientes");
        if (filtros != null && !filtros.isEmpty()) {
            sqlBuilder.add("WHERE idpersona IN(SELECT id FROM avr.personas WHERE");
            List<Filtro> filtrosWithoutDate = filtros.stream().filter(f -> f.getTipo() != 'D').collect(Collectors.toList());
            filtrosWithoutDate.forEach(f -> {
                if (f.getOperador() != null) {
                    sqlBuilder.add(f.getOperador());
                }
                sqlBuilder.add(f.getNombre());
                if (f.getTipo() == 'S') {
                    sqlBuilder.add("ILIKE");
                    sqlBuilder.add("?");
                } else {
                    sqlBuilder.add("=");
                    sqlBuilder.add("?");
                }
            })
            ;
            filtros.stream().filter(f -> f.getTipo() == 'D').findFirst().ifPresent(f -> {
                if (f.getOperador() != null && filtrosWithoutDate.size() < 1) {
                    sqlBuilder.add(f.getOperador());
                } else if(filtrosWithoutDate.size() > 0){
                    sqlBuilder.add("AND");
                }
                sqlBuilder.add(f.getNombre());
                sqlBuilder.add("BETWEEN");
                sqlBuilder.add("? AND ?");
            });
            sqlBuilder.add(")");
        }
        try (Connection conn = new Conexion().getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sqlBuilder.toString())) {
            if (filtros != null && !filtros.isEmpty()) {
                AtomicInteger idx = new AtomicInteger(1);
                filtros.stream().filter(f -> f.getTipo() != 'D').forEach(f -> {
                    try {
                        if (f.getTipo() == 'S') {
                            likeParameter.add(f.getValor());
                            pstmt.setString(idx.get(),likeParameter.toString());
                        }
                        else
                            pstmt.setLong(idx.get(), Long.valueOf(f.getValor()));
                        idx.incrementAndGet();
                    } catch (SQLException e) {
                        LOG.log(Level.SEVERE, "[ClienteCN][obtenerListadoClientes][SQLException] -> ", e);
                    } catch (Exception e){
                        LOG.log(Level.SEVERE, "[ClienteCN][obtenerListadoClientes][Excepcion] -> ", e);
                    }
                });
                filtros.stream().filter(f -> f.getTipo() == 'D').forEach(f -> {
                    try {
                        pstmt.setDate(idx.get(), Date.valueOf(f.getValor()));
                        idx.incrementAndGet();
                    } catch (SQLException e) {
                        LOG.log(Level.SEVERE, "[ClienteCN][obtenerListadoClientes][SQLException] -> ", e);
                    }
                });
            }
            LOG.log(Level.INFO, "[ClienteCN][obtenerListadoClientes][Query] -> {0}", pstmt);
            try (ResultSet rs = pstmt.executeQuery()) {
                listado = new ArrayList<>();
                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getLong("id"));
                    cliente.setDatosPersonales(new PersonaCN().obtenerDatosPersonales.apply(new Persona(rs.getLong("idpersona"))));
                    listado.add(cliente);
                }
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "[ClienteCN][obtenerListadoClientes][SQLException] -> ", e);
        }
        return listado;
    };

    public Consumer<Cliente> registrar = c -> {
        LOG.log(INFO, "[ClienteCN][registrar] -> {0}", new Object[]{c});
        StringJoiner sqlBuilder = new StringJoiner(" ");
        sqlBuilder.add("INSERT INTO avr.personas(nombre,")
                .add("apellidopaterno,")
                .add("apellidomaterno,")
                .add("dui,")
                .add("nit,")
                .add("telefono,")
                .add("email,")
                .add("direccion,")
                .add("fechanacimiento)");
        sqlBuilder.add("VALUES (?,?,?,?,?,?,?,?,?)");
        sqlBuilder.add("RETURNING id");
        try (Connection conn = new Conexion().getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sqlBuilder.toString())) {
            pstmt.setString(1, c.getDatosPersonales().getNombre());
            pstmt.setString(2, c.getDatosPersonales().getApellidoPaterno());
            pstmt.setString(3, c.getDatosPersonales().getApellidoMaterno());
            pstmt.setString(4, c.getDatosPersonales().getDui());
            pstmt.setString(5, c.getDatosPersonales().getNit());
            pstmt.setString(6, c.getDatosPersonales().getTelefono());
            pstmt.setString(7, c.getDatosPersonales().getEmail());
            pstmt.setString(8, c.getDatosPersonales().getDireccion());
            pstmt.setDate(9, Date.valueOf(c.getDatosPersonales().getFechaNacimiento()));
            if (pstmt.execute()) {
                try (ResultSet rs = pstmt.getResultSet()) {
                    if (rs.next()) {
                        long idPersona = rs.getLong(1);
                        try (PreparedStatement pstmt1 = conn.prepareStatement("INSERT INTO avr.clientes (idpersona) VALUES (?)")) {
                            pstmt1.setLong(1, idPersona);
                            pstmt1.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "[ClienteCN][registrar][SQLException] -> ", e);
        }
    };

    public Consumer<Cliente> actualizar = c -> {
        LOG.log(INFO, "[ClienteCN][actualizar] -> {0}", new Object[]{c});
        StringJoiner sqlBuilder = new StringJoiner(" ");
        sqlBuilder.add("UPDATE avr.personas SET")
                .add("nombre=?,")
                .add("apellidopaterno=?,")
                .add("apellidomaterno=?,")
                .add("dui=?,")
                .add("nit=?,")
                .add("telefono=?,")
                .add("email=?,")
                .add("direccion=?,")
                .add("fechanacimiento=?");
        sqlBuilder.add("WHERE id=?");
        try (Connection conn = new Conexion().getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sqlBuilder.toString())) {
            pstmt.setString(1, c.getDatosPersonales().getNombre());
            pstmt.setString(2, c.getDatosPersonales().getApellidoPaterno());
            pstmt.setString(3, c.getDatosPersonales().getApellidoMaterno());
            pstmt.setString(4, c.getDatosPersonales().getDui());
            pstmt.setString(5, c.getDatosPersonales().getNit());
            pstmt.setString(6, c.getDatosPersonales().getTelefono());
            pstmt.setString(7, c.getDatosPersonales().getEmail());
            pstmt.setString(8, c.getDatosPersonales().getDireccion());
            pstmt.setDate(9, Date.valueOf(c.getDatosPersonales().getFechaNacimiento()));
            pstmt.setLong(10, c.getDatosPersonales().getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "[ClienteCN][actualizar][SQLException] -> ", e);
        }
    };

    public Consumer<Cliente> eliminar = c -> {
        LOG.log(INFO, "[ClienteCN][eliminar] -> {0}", new Object[]{c});
        try (Connection conn = new Conexion().getConexion();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM avr.clientes WHERE id = ?")) {
            pstmt.setLong(1, c.getId());
            if (pstmt.executeUpdate() > 0) {
                try (PreparedStatement pstmt1 = conn.prepareStatement("DELETE FROM avr.personas WHERE id = ?")) {
                    pstmt1.setLong(1, c.getDatosPersonales().getId());
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "[ClienteCN][eliminar][SQLException] -> ", e);
        }
    };

}
