
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Sebastian
 */
public class Consultordb {
    Conexion db;
    Statement st;

    public Consultordb() {
        db = new Conexion();
        try {
            st = db.conectar().createStatement();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ResultSet consultarUsuario(String cedula) throws SQLException {

        return st.executeQuery("SELECT * FROM usuarios WHERE cedula = " + cedula);
    }

    public ResultSet consultarDeuda(int idUsuario) throws SQLException {
        return st.executeQuery("SELECT * FROM facturas WHERE idUsuario = " + idUsuario);
    }

    public void pagarDeuda(int id, int idDeuda, double saldo, String servicio) throws SQLException {
        st.execute("UPDATE usuarios SET saldo = " + saldo + " WHERE id = " + id);
        st.execute("UPDATE facturas SET " + servicio + " = 0 WHERE id = " + idDeuda);
    }

    public ResultSet consultarSaldo(int idUsuario) throws SQLException {
        return st.executeQuery("SELECT saldo FROM usuarios WHERE id = " + idUsuario);
    }

    public void agregarSaldo(int idUsuario, double nuevoSaldo) throws SQLException {
        st.execute("UPDATE usuarios SET saldo = " + nuevoSaldo + " WHERE id = " + idUsuario);
    }



}