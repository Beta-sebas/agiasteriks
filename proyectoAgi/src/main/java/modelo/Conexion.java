package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author chris
 */
public class Conexion {

    public Conexion() {

    }

    public Connection conectar() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/pagofacturasdb", "root", "");

    }

}
