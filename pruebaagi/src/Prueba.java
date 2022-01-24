import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.asteriskjava.fastagi.*;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Prueba extends BaseAgiScript {

    Conexion bd = new Conexion();
    Usuarios usuarios = new Usuarios();
    List<Object> person, factura;


    public void service(AgiRequest request, AgiChannel channel)
            throws AgiException {
        try {
            bd.conectar();
        } catch (SQLException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Answer the channel...
        answer();
        // ...say hello...
        exec("Festival", "Bienvenido al sistema de pagos de facturas de servicios domiciliarios. "
                + "Para ingresar a las opciones digite su documento seguido de la tecla numeral. "
                + "Si desea salir marque 999");
        streamFile("beep");

        String cedula;

        while (true) {

            cedula = this.getData(null, 20000L, 4);

            if (cedula.equals("999")) {
                break;
            } else {

                try {
                    person = usuarios.validarUsuario(cedula);
                    if (person == null) {
                        exec("Festival", "El usuario ingresado no se encuentra registrado intentelo de nuevo");
                    } else {

                        // Cuando ingresa una cedula correcta:
                        exec("Festival", "El usuario que ingreso fue " + person.get(0));

                        Menu(request, channel);
                        break;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            // streamFile("tt-monkeys");
        }

        // ...and hangup.
        hangup();
    }

    public void Menu(AgiRequest request, AgiChannel channel) throws AgiException {

        boolean ban = true;

        while (ban) {

            exec("Festival", "Presione la tecla correspondiente seguida de la tecla numeral." +
                    " Presione 1 para pagar el recibo del agua." +
                    " Presione 2 para pagar el recibo de la energia." +
                    " Presione 3 para pagar el recibo del gas." +
                    " Presione 4 para pagar el recibo del internet" +
                    " Presione 5 para consultar el saldo de su cuenta." +
                    " Presione 6 para recargar saldo de la cuenta." +
                    " Presione 7 para escuchar estas opciones de nuevo." +
                    " Presione 8 para salir.");
            streamFile("beep");
            // exec("Wait(3)");

            String opcionPrincipal;

            opcionPrincipal = this.getData(null, 20000L, 2);
            try {
                factura = usuarios.validarDeuda((Integer) person.get(0));
                switch (opcionPrincipal) {
                    // Consulta de servicios
                    case "1": // agua
                        consultaFacura(factura, Integer.parseInt(opcionPrincipal));
                        break;
                    case "2": // energia
                        consultaFacura(factura, Integer.parseInt(opcionPrincipal));
                        break;
                    case "3": // gas
                        consultaFacura(factura, Integer.parseInt(opcionPrincipal));
                        break;
                    case "4": // internet
                        consultaFacura(factura, Integer.parseInt(opcionPrincipal));
                        break;

                    // Consultar el saldo de la persona
                    case "5":
                        
                        person.set(1, usuarios.consultarSaldo((Integer) person.get(0)));
                        exec("Festival", "Su saldo para pagar facturas de servicios es de " + person.get(1) + " pesos");
                        break;

                    // Llamar cierta logica para sumar dinero al saldo de la persona
                    case "6":
                        break;

                    // Escuchar el menú de nuevo
                    case "7":
                        break;

                    // Salir
                    case "8":
                        ban = false;
                        break;

                    default:
                        exec("festival", "La opcion ingresada no existe");
                        break;
                }

            } catch (SQLException ex) {
                Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void consultaFacura(List<Object> factura, int opcion) throws AgiException {

        ResultSetMetaData rsmd = (ResultSetMetaData) factura.get(6);
        try {
            if ((int) factura.get(opcion + 1) == 0) {
                exec("festival", "Usted no tiene deuda por concepto de servicio de " + rsmd.getColumnName(opcion + 2));
            } else {

                exec("festival",
                        "Usted tiene una deuda de " + (int) factura.get(opcion + 1) +
                                " pesos concepto de servicio de " + rsmd.getColumnName(opcion + 2));
                boolean ban = true;
                while (ban) {
                    exec("festival", "Presione la opcion seguida de la tecla numeral" +
                            "Presione 1 para realizar el pago." +
                            " Presione 2 para escuchar de nuevo el costo de la deuda." +
                            " Presione 3 para regresar al menu principal.");

                    String opcionPrincipal;
                    opcionPrincipal = this.getData(null, 20000L, 2);
                    switch (opcionPrincipal) {
                        // Consulta de servicios
                        case "1":

                            if ((int) person.get(1) < (int) factura.get(opcion + 1)) {
                                exec("festival", "Saldo insuficiente");
                            }else{
                                usuarios.cancelarDeuda((int) person.get(0), (int)factura.get(0),(int) person.get(1), (int)factura.get(opcion + 1), rsmd.getColumnName(opcion + 2));
                                exec("festival", "Pago realizado correctamente. ");
                                person.set(1, usuarios.consultarSaldo((Integer) person.get(0)));
                                ban = false;                           
                            }
                            break;
                        case "2":
                            exec("festival",
                                    "Usted tiene una deuda de " + (int) factura.get(opcion + 1) +
                                    " pesos por concepto de servicio de " + rsmd.getColumnName(opcion + 2));
                            break;
                        case "3":
                            ban = false;
                            break;
                        default:
                            exec("festival", "Opcion incorrecta");
                            break;
                    }
                }

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // Para realizar el pago, se necesita el id del usuario y el id de la deuda
    public void realizarPago() {

    }
}
