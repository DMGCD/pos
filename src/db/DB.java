package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.Class.forName;

public class DB {
    private static DB dbconection;
    private static Connection connection;
   private DB(){
       try {
           Class.forName("com.mysql.cj.jdbc.Driver");
           connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/pos","root","root");
       } catch (ClassNotFoundException | SQLException e) {
           throw new RuntimeException(e);
       }

   }
   public static  DB getInstance(){

       return (dbconection==null)? dbconection =new DB(): dbconection;
   }

   public Connection getConnection()
   {
       return connection;
   }

}
