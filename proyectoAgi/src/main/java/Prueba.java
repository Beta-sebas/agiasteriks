import controlador.Usuarios;
import modelo.Conexion;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.asteriskjava.fastagi.*;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
/**
 *
 * @author Sebastian
 */
public class Prueba extends BaseAgiScript {
    public void service(AgiRequest request, AgiChannel channel)
            throws AgiException
    {
        Conexion bd = new Conexion();
        Usuarios usuarios = new Usuarios();
        
        try {
            bd.conectar();
        } catch (SQLException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Answer the channel...
        answer();
        // ...say hello...
        exec("Festival", "Bienvenido al sistema de pagos de facturas de servicios domiciliarios. "
                + "para ingresar a las opciones, digite su documento seguido de la tecla numeral"
                + "si desea salir marque 999");
        
        String cedula; 
         
        while (true) {
         
        cedula = this.getData(null,20000L,4);
            
            if (cedula.equals("999")) {
                break;
            }else{
                
            try {
                List<Object> person = usuarios.validarUsuario(cedula);
                if (person == null) {
                    exec("Festival", "El usuario ingresado no se encuentra registrado intentelo de nuevo");
                }else{
                    exec("Festival", "El usuario que ingreso fue " + person.get(0));
                    break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
            }
                 
            }
            //streamFile("tt-monkeys");
        }
        
        // ...and hangup.
        hangup();               
    }          
}


