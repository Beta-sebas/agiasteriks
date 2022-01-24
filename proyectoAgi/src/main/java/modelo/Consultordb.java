/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Sebastian
 */
public class Consultordb {
    
    Conexion db = new Conexion();
    
    public ResultSet consultarUsuario(String cedula) throws SQLException{
        Statement st = db.conectar().createStatement();
         return st.executeQuery("SELECT * FROM usuarios WHERE cedula = " + cedula);  
    }  
}
