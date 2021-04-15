package aderlass;

import java.sql.*;

public class Aderlass {

    Connection con;

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        Aderlass aderlass = new Aderlass();
        //aderlass.openConnection();
        aderlass.closeConnection();

    }

    public Connection openConnection() {
        
        try {
            String userName = "root"; //Change if you used a different one
            String password = "toor"; //Change if you used a different one
            String dbname = "aderlass";
            String hostname = "localhost";
            String port = "3306";
            // String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
            String url = "jdbc:mysql://localhost:3306/aderlass?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false"; //Sometimes, also valid without specifying the port
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, userName, "toor");
            
            System.out.println("Connection to the database done: " + con.getClass().getName());
        } catch (Exception e) {
            
            System.out.println("Error in the connection. Detailed message:\n");
            System.out.println(e.getMessage());
        }
        return con;
    }

    public void closeConnection() {
        try {
            con.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            System.out.println("Error when closing connection");
        }
    }

}
