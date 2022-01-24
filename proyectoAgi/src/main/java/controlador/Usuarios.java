
package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import modelo.Consultordb;

/*
 *
 * @author Sebastian
 */
public class Usuarios {
    String cedula;
    Consultordb consultor = new Consultordb();
    ResultSet rs;
    Object usuario[];
    
    public List<Object> validarUsuario(String cedula) throws SQLException{
    this.cedula = cedula;
    
    rs = consultor.consultarUsuario(cedula);
    rs.next();
    if (rs.getRow() == 0) {
        return null;
    }else{
       return Arrays.asList(rs.getInt("id"),rs.getInt("saldo"));
    } 
}
    
}
