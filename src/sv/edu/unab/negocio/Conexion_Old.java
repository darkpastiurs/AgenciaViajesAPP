package sv.edu.unab.negocio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion_Old {

    private static final Logger LOG = Logger.getLogger("sv.edu.unab.agenciaviajes");

    protected static final String user = "postgres";
    protected static final String password = "admin@root";
    protected static final String dbname = "agencia_vuelo";
    protected static final String server = "127.0.0.1";
    protected static final String puerto = "5432";

    private Connection conn;

    public Conexion_Old() {
        LOG.log(Level.INFO, "[Conexion_Old][INIT]");
        conn = null;
        try {
            StringJoiner url = new StringJoiner("");
            url.add("jdbc:postgresql://");
            url.add(server);
            url.add(":");
            url.add(puerto);
            url.add("/");
            url.add(dbname);
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url.toString(), user, password);
        } catch (ClassNotFoundException e) {
            LOG.log(Level.SEVERE, "[Conexion_Old][INIT][ClassNotFoundException] -> ", e);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "[Conexion_Old][INIT][SQLException] -> ", e);
        }
    }

    public Connection getConexion(){
        return conn;
    }
}
