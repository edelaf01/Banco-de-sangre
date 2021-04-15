/*
 *Para conectarse a la base de datgos
 */
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//Para acceder a las propiedades de objetos
import pojo.User;
//Para establecer la conexion a la BD
import aderlass.Aderlass;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
/**
 *
 * @author kkkk
 */
public class registroDao {
    
    public String registerUser(User user)
     {
           //Creo objeto aderlass para poder abrir conexion a la base de datos
         Aderlass obj=new Aderlass();
         
         String nombreUsuario = user.getUsername();
          String password = user.getPassword();
       
         String tipo=user.getType();
      
         Connection con ;
         PreparedStatement preparedStatement ;         
         try
         {
             
             con = obj.openConnection();
           
            
             String query = "INSERT INTO aderlass.user(username,password,type) VALUES(?,?,?);"; //Insert user details into the table 'USERS'
              
             preparedStatement = con.prepareStatement(query); //Making use of prepared statements here to insert bunch of data
             
             preparedStatement.setString(1, nombreUsuario);
           
             preparedStatement.setString(2, password);
             preparedStatement.setString(3, tipo);
              
             int i= preparedStatement.executeUpdate();
             
             if (i!=0)  //Just to ensure data has been inserted into the database
             return "SUCCESS"; 
         }
         catch(SQLException e)
         {
            e.printStackTrace();
         }       
         return "Oops.. Something went wrong there..!";  // On failure, send a message from here.
     }
}
