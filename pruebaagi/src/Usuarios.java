
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/*
 *
 * @author Sebastian
 */
public class Usuarios {
    String cedula;
    Consultordb consultor = new Consultordb();
    ResultSet rs;
    Object usuario[];

    public List<Object> validarUsuario(String cedula) throws SQLException {
        this.cedula = cedula;

        rs = consultor.consultarUsuario(cedula);
        rs.next();
        if (rs.getRow() == 0) {
            return null;
        } else {
            return Arrays.asList(rs.getInt("id"), rs.getInt("saldo"));
        }
    }

    public List<Object> validarDeuda(int idUsuario) throws SQLException {
        rs = consultor.consultarDeuda(idUsuario);
        rs.next();
        if (rs.getRow() == 0) {
            return null;
        } else {
            return Arrays.asList(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6),
                    rs.getMetaData());
        }
    }

    public void cancelarDeuda(int idUsuario, int idDeuda, double saldoUsuario, double deudaUsuario, String servicio) throws SQLException {
        double saldo = saldoUsuario - deudaUsuario;
        consultor.pagarDeuda(idUsuario, idDeuda, saldo, servicio);
    }

    public int consultarSaldo(int idUsuario) throws SQLException{
        rs = consultor.consultarSaldo(idUsuario);
        rs.next();
        if (rs.getRow() == 0) {
            return 0;
        } else {
            return rs.getInt("saldo");
        }
    }

}
