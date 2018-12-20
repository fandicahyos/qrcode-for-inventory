
package config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Vector; 
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

//import libraries.file_rewrite;


  public class database
{
      
    
//    file_rewrite file = new file_rewrite();
    
    DateFormat vdateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
    SimpleDateFormat vmonthformat = new SimpleDateFormat("MM/yyyy");
    Date date = new Date();
    String dateformat = vdateformat.format(date.getTime());
    String monthformat = vmonthformat.format(date.getTime());
        
    
      
    private Connection conn,conn2;
    private String status,option,temp,prtx,time,time2,dates;
    private String sql;
    private Vector data=new Vector();
    private DecimalFormat fmt = new DecimalFormat("0.000");
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private DateFormat dateFormat2 = new SimpleDateFormat("HHmmss");
    private DateFormat tf = new SimpleDateFormat("yyyy-MM-dd");
    private int i;
    private boolean statusdb;
    private final char BINTANG='*';
    private Hashtable ht;
    private InitialContext ctx;
    private DataSource ds;
    private int col_length;
    
    
    
    //private String HOST_DB="127.0.0.1"; 
    private String HOST_DB="192.168.5.5"; 
    private String HOST_DB_BACKUP="127.0.0.1";
    private int PORT=1521;
    private String USERNAME="keukkt";
    private String PASSWORD="kkttitik";
    private String NAME_DB="orcl"; 
    private String NAME_DB_BACKUP="adminit";  
  
    private Statement stm;  
    private Statement st;

//    private Environment env;

    public boolean isClosed() {
        statusdb=false;
        try {
            sql="select 1 from dual";
            st=conn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            rs.close();
//            st.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            statusdb=true;
        }
        return statusdb;
    }



     public Connection getConn(){
         return conn;
     }

     public Statement getStatement(){
         return st;
     }




    public void close()
    { 
        try
        {
//            data.removeAllElements();
            conn.close();
            conn=null;
//            conn2.close();
            
            System.out.println("Connection to DB Closed");
        }
        catch(Exception e)
        {
           System.out.println("Close DB Failed");
        }
    }

    public void executeIUD(String query)
    {
        try
        {
            stm=conn.createStatement();
            stm.executeUpdate(query);
            stm.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public database(String PROGRAM_NAME)
    {  
        data=new Vector();
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
//            file.file_write("err",dateformat +" [ load db driver failed ] "+ex.toString() );
            System.out.println("error "+dateformat +" [ load db driver failed ] "+ex.toString() );
        }
         
        try {
            java.util.Properties props = new java.util.Properties();
            props.setProperty("password", PASSWORD);
            props.setProperty("user", USERNAME);
            props.put("v$session.osuser", System.getProperty("user.name").toString());
//            try {
              
                
                props.put("v$session.machine", "WEBSERVER");
//            } 
//            catch (UnknownHostException ex) {
////                file.file_write("err",dateformat +" [ can not get host name ] "+ex.toString() );
//                  System.out.println("error "+dateformat +" [ can not get host name ] "+ex.toString() );
//            } 
            props.put("v$session.program", PROGRAM_NAME);
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//" + HOST_DB + ":" + PORT + "/" + NAME_DB, props);
            // conn = DriverManager.getConnection("jdbc:postgresql://"+HOST_DB+":"+PORT+"/"+NAME_DB, USERNAME,PASSWORD);
            conn.setAutoCommit(false);
            System.out.println("connection success");
            status="connection success";
            st=conn.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            System.out.println("connection db fail");
//            file.file_write("err",dateformat +" [ connection db failed ] "+ex.toString() );
            
                    System.out.println("error "+dateformat +" [ connection db failed ] "+ex.toString() );
           
            
            
            
             try {
                 java.util.Properties props = new java.util.Properties();
            props.setProperty("password", PASSWORD);
            props.setProperty("user", USERNAME);
            props.put("v$session.osuser", System.getProperty("user.name").toString());
//            try {
//                props.put("v$session.machine", InetAddress.getLocalHost().getCanonicalHostName());
//            } catch (UnknownHostException ex1) {
//                System.out.println("cannot get host name");
//            } 
            props.put("v$session.machine", "WEBSERVER");
            props.put("v$session.program", PROGRAM_NAME);
//            conn = DriverManager.getConnection("jdbc:oracle:thin:@//" + HOST_DB_BACKUP + ":" + PORT + "/" + NAME_DB_BACKUP, props);
conn = DriverManager.getConnection("jdbc:postgresql://"+HOST_DB_BACKUP+":"+PORT+"/"+NAME_DB_BACKUP, USERNAME,PASSWORD);
            conn.setAutoCommit(false);
            System.out.println("Koneksi ke database backup berhasil");
            status="Koneksi ke database backup berhasil";
            st=conn.createStatement();
            System.out.println("Statement created");
        } catch (SQLException ex1) {
            System.out.println(ex1.toString());
//            file.file_write("err",dateformat +" [ connection db backup failed ] "+ex.toString() );
                System.out.println("error "+dateformat +" [ connection db backup failed ] "+ex.toString() );
        }
            
            
            
            
        }
      }




}