/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eliane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
    
    private Connection connection;
    
    public DataBase(String typeDate, String dateDeb, String dateFin, String repertoire){
        this.typeDate=typeDate;
        this.dateDeb=dateDeb;
        this.dateFin=dateFin;
        this.repertoire=repertoire;
    }
  
    @Override
    public void run() {
        
        try {
            connection = getConnection();
        
            StringBuilder strb = new StringBuilder (20000);
            
            String requete="select distinct b.id_pyr, i.matricule_fam, b.civilite_pyr, b.nom_pyr, b.prenom_pyr, c.adr_appt_per, c.adr_bat_per, c.adr_btq, c.adr_cp, c.adr_ville, c.adr_cplt, c.adr_num, d.lib_nom_rue, b.tel_perso_pyr, b.tel_portable_pyr, b.tel_prof_pyr, e.prix_net_uni, f.matricule_per, f.nom_per, f.prenom_per, f.date_naissance_per, h.nom_lie, k.nom_lie, k.nom_clg, f.id_per \n" +
            "from cr_inscription a, cr_payeur b, cr_adresse c, cr_rue d, cr_inscription_unite e, cr_personne f, cr_inscription_lieu g, cr_lieu h, cr_famille i, cr_inscription j, cr_inscription_lieu k \n" +
            "where a.ID_PYR=b.id_PYR and b.id_ADR=c.id_adr and c.id_rue=d.id_rue AND a.id_ins=e.id_ins AND a.id_per_ins=f.id_per and a.id_ins=g.id_ins and g.id_lie=h.id_lie and i.id_fam=a.id_fam and (a.id_per_ins=j.id_per_ins and (j.id_act=1 or j.id_act=2) and j.id_ins=k.id_ins and j.date_debut_ins > 20150801 and j.date_debut_ins < 20160731 and j.date_fin_ins > 20160127) and a.id_act=88 and a.date_debut_ins < 20160731 and a.date_fin_ins <= 20160731 and a.date_debut_ins > 20150801 and a.date_fin_ins > 20160127 and a.etat_ins like 'Inscription' \n" +
            "order by b.id_pyr";
            
            PreparedStatement ps1 = connection.prepareStatement(requete);
            PreparedStatement ps2 = connection.prepareStatement("select libelle_regime from cr_personne_regime where date_fin_regime >= 20160901 and id_per=?");
           
            ResultSet rs = ps1.executeQuery();
            ResultSet rs2;
            
            while(rs.next()){
               ps2.clearParameters();
               ps2.setInt(1, rs.getInt(25));
               rs2 = ps2.executeQuery();
               
               strb.append(rs.getInt(1));
               strb.append(";");
               strb.append(rs.getInt(2));
               strb.append(";");
               strb.append(rs.getString(3));
               strb.append(";");
               strb.append(rs.getString(4));
               strb.append(";");
               strb.append(rs.getString(5));
               strb.append(";");
               if (rs.getString(6)!=null) {
                    strb.append(rs.getString(6));
               }
               strb.append(";");
               if (rs.getString(7)!=null) {
                    strb.append(rs.getString(7));
               }
               strb.append(";");
               if (rs.getString(8)!=null) {
                    strb.append(rs.getString(8));
               }
               strb.append(";");
               strb.append(rs.getString(9));
               strb.append(";");
               strb.append(rs.getString(10));
               strb.append(";");
               if (rs.getString(11)!=null) {
                    strb.append(rs.getString(11));
               }
               strb.append(";");
               strb.append(rs.getInt(12));
               strb.append(";");
               strb.append(rs.getString(13));
               strb.append(";");
               if (rs.getString(14)!=null) {
                    strb.append(rs.getString(14));
               }
               strb.append(";");
               if (rs.getString(15)!=null) {
                    strb.append(rs.getString(15));
               }
               strb.append(";");
               if (rs.getString(16)!=null) {
                    strb.append(rs.getString(16));
               }
               strb.append(";");
               strb.append(rs.getDouble(17));
               strb.append(";");
               strb.append(rs.getInt(18));
               strb.append(";");
               strb.append(rs.getString(19));
               strb.append(";");
               strb.append(rs.getString(20));
               strb.append(";");
               strb.append(rs.getString(21));
               strb.append(";");
               strb.append(rs.getString(22));
               strb.append(";");
               strb.append(rs.getString(23));
               strb.append(";");
               strb.append(rs.getString(24));
               if (rs2!=null && rs2.next()) {
                    strb.append(";");                   
                    strb.append(rs2.getString(1));
               }
               
               rs2.close();
               strb.append("\n");  
            }
            
            File fichier = new File(repertoire+"\\restauration.csv");
            Writer fw = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(fichier),"ISO-8859-1"));
            try {
                fw.write(strb.toString());
            } finally {
                fw.close();
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
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
