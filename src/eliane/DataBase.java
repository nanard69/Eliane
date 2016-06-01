/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eliane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author slefebvre
 */
public class DataBase implements Runnable {

    private static Connection conn;  
    private static String url = "jdbc:oracle:thin:@basededonix7:1521:ARPE";  
    private static String user = "concerto";//Username of database  
    private static String pass = "arpege";//Password of database  
    
    private String typeDate="";
    private String dateDeb="";
    private String dateFin="";
    private String repertoire="";
    
    public DataBase(String typeDate, String dateDeb, String dateFin, String repertoire){
        this.typeDate=typeDate;
        this.dateDeb=dateDeb;
        this.dateFin=dateFin;
        this.repertoire=repertoire;
    }
  
    @Override
    public void run() {
        try {
            String requete="";
            
            Connection connection = getConnection();
            
            ResultSet rs = connection.createStatement().executeQuery(requete);            
            while(rs.next()){  
                rs.getString(0);  
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
     
    public static Connection connect() throws SQLException{  
     try{  
       Class.forName("oracle.jdbc.OracleDriver").newInstance();  
     }catch(ClassNotFoundException cnfe){  
       System.err.println("Error: "+cnfe.getMessage());  
     }catch(InstantiationException ie){  
       System.err.println("Error: "+ie.getMessage());  
     }catch(IllegalAccessException iae){  
       System.err.println("Error: "+iae.getMessage());  
     }  
     conn = DriverManager.getConnection(url,user,pass);  
     return conn;  
   }  
    
   public static Connection getConnection() throws SQLException, ClassNotFoundException{  
     if(conn !=null && !conn.isClosed())  
       return conn;  
     connect();  
     return conn;  
   }
}
