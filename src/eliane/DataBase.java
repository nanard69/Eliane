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
            String requete="select distinct b.id_pyr, i.matricule_fam, b.civilite_pyr, b.nom_pyr, b.prenom_pyr, c.adr_appt_per, c.adr_bat_per, c.adr_btq, c.adr_cp, c.adr_ville, c.adr_cplt, c.adr_num, d.lib_nom_rue, b.tel_perso_pyr, b.tel_portable_pyr, b.tel_prof_pyr, e.prix_net_uni, f.matricule_per, f.nom_per, f.prenom_per, f.date_naissance_per, h.nom_lie, k.nom_lie, k.nom_clg \n" +
            "from cr_inscription a, cr_payeur b, cr_adresse c, cr_rue d, cr_inscription_unite e, cr_personne f, cr_inscription_lieu g, cr_lieu h, cr_famille i, cr_inscription j, cr_inscription_lieu k \n" +
            "where a.ID_PYR=b.id_PYR and b.id_ADR=c.id_adr and c.id_rue=d.id_rue AND a.id_ins=e.id_ins AND a.id_per_ins=f.id_per and a.id_ins=g.id_ins and g.id_lie=h.id_lie and i.id_fam=a.id_fam and (a.id_per_ins=j.id_per_ins and (j.id_act=1 or j.id_act=2) and j.id_ins=k.id_ins and j.date_debut_ins > 20150801 and j.date_debut_ins < 20160731 and j.date_fin_ins > 20160127) and a.id_act=88 and a.date_debut_ins < 20160731 and a.date_fin_ins <= 20160731 and a.date_debut_ins > 20150801 and a.date_fin_ins > 20160127 and a.etat_ins like 'Inscription' \n" +
            "order by b.id_pyr";
            
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
