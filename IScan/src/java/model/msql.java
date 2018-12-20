/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import config.database;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Map.Entry;
import java.util.ArrayList;


/**
 *
 * @author hp
 */

public class msql  {
    private Statement stm;
    private ResultSet rs;
    private final  Connection conn;
    //log4j vlog4j = new log4j();
    DateFormat vdateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    SimpleDateFormat vmonthformat = new SimpleDateFormat("MM/yyyy");
    Date date = new Date();
    String dateformath2h = vdateformat.format(date.getTime());
    String monthformat = vmonthformat.format(date.getTime());
    
    public msql(database db) {
        //stm=db.getStatement();
         conn=db.getConn();

    }
 public ArrayList resultSetToArrayList(ResultSet rs) throws SQLException{
  ResultSetMetaData md = rs.getMetaData();
  int columns = md.getColumnCount();
  ArrayList list = new ArrayList(50);
  while (rs.next()){
     HashMap row = new HashMap(columns);
     for(int i=1; i<=columns; ++i){         
         if(rs.getObject(i)== null)
         {
            row.put(md.getColumnName(i)," ");  
         }
         else
         {
            row.put(md.getColumnName(i),rs.getObject(i));
             //System.out.println(md.getColumnName(i).toLowerCase());
         }
     }
      list.add(row);
  }

 return list;
}    
        
 public ArrayList<HashMap> proses_select_nolog(String SQL) {
    ArrayList<HashMap> v=new ArrayList<HashMap>();

    try {
        stm=conn.createStatement();
        rs = stm.executeQuery(SQL);
        v=resultSetToArrayList(rs);
        rs.close();
        stm.close();
    }   
    catch (SQLException ex) {
//        vlog4j.log("[ERROR] " + ex.toString());
        System.out.println(ex.toString());
        Logger.getLogger(msql.class.getName()).log(Level.SEVERE, null, ex);
    }
    return v;
}   
 public ArrayList<HashMap> proses_select_login(String SQL) {
    ArrayList<HashMap> v=new ArrayList<HashMap>();

    try {
        stm=conn.createStatement();
        rs = stm.executeQuery(SQL);
        v=resultSetToArrayList(rs);
        rs.close();
        stm.close();
    }   
    catch (SQLException ex) {
//        vlog4j.log("[ERROR] " + ex.toString());
        System.out.println(ex.toString());
        Logger.getLogger(msql.class.getName()).log(Level.SEVERE, null, ex);
    }
    return v;
}   
public ArrayList<HashMap> proses_select(String SQL) {
    ArrayList<HashMap> v=new ArrayList<HashMap>();
    System.out.println(dateformath2h+" "+SQL);
    try {
        stm=conn.createStatement();
        rs = stm.executeQuery(SQL);
        v=resultSetToArrayList(rs);
        rs.close();
        stm.close();
    }   
    catch (SQLException ex) {
        //vlog4j.log("[ERROR] " + ex.toString());
        System.out.println(ex.toString());
        Logger.getLogger(msql.class.getName()).log(Level.SEVERE, null, ex);
    }
    return v;
}
public boolean proses_update(String SQL)
    {
        System.out.println(dateformath2h+" "+SQL);
       //vlog4j.log("[SQL] " + SQL);
            try {
            stm=conn.createStatement();
            stm.executeUpdate(SQL);
            stm.close();
            return true;
        } catch (SQLException ex) {
            //vlog4j.log("[ERROR] " + ex.toString());
               System.out.println(ex.toString());
            Logger.getLogger(msql.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
public Map sqlInsert(String SQL)
    {
        System.out.println(dateformath2h+" "+SQL);
        Map<String, String> mapSQL = new HashMap<>();
        
            try {
            stm=conn.createStatement();
            stm.executeUpdate(SQL);
            stm.close();
            
            mapSQL.put("sqlStatus", "01");
            mapSQL.put("sqlMessage", "Insert Success");
            return mapSQL;
        } catch (SQLException ex) {
            //vlog4j.log("[ERROR] " + ex.toString());
            System.out.println(dateformath2h + ex.toString());
            Logger.getLogger(msql.class.getName()).log(Level.SEVERE, null, ex);
            mapSQL.put("sqlStatus", "02");
            mapSQL.put("sqlMessage", ex.toString());
            return mapSQL;
        }
    }
public HashMap sqlStatement(String descr, String SQL)
    {
        System.out.println(dateformath2h+" "+SQL);
        HashMap<String, String> mapSQL = new HashMap<>();
        
        try {
            stm=conn.createStatement();
            stm.executeUpdate(SQL);
            stm.close();
            mapSQL.put("sqlStatus", "success");
            mapSQL.put("sqlMessage", descr+" data success");
            return mapSQL;
        } catch (SQLException ex) {
            //vlog4j.log("[ERROR] " + ex.toString());
            System.out.println(dateformath2h + ex.toString());
            Logger.getLogger(msql.class.getName()).log(Level.SEVERE, null, ex);
            mapSQL.put("sqlStatus", "warning");
            mapSQL.put("sqlMessage", ex.toString());
            return mapSQL;
        }
    }
public ArrayList<Entry<String, String>> sqlStatement2(String descr, String SQL)
    {
        System.out.println(dateformath2h+" "+SQL);
        HashMap<String, String> mapSQL = new HashMap<>();
         ArrayList<Entry<String, String>> v = new ArrayList<>();
        try {
            stm=conn.createStatement();
            stm.executeUpdate(SQL);
            stm.close();
            mapSQL.put("sqlStatus", "success");
            mapSQL.put("sqlMessage", descr+" data success");
           
        v.addAll(mapSQL.entrySet());
            return v;
        } catch (SQLException ex) {
            //vlog4j.log("[ERROR] " + ex.toString());
            System.out.println(dateformath2h + ex.toString());
            Logger.getLogger(msql.class.getName()).log(Level.SEVERE, null, ex);
            mapSQL.put("sqlStatus", "warning");
            mapSQL.put("sqlMessage", ex.toString());
             v.addAll(mapSQL.entrySet());
            return v;
        }
    }
public String proses_insert(String SQL)
    {
        System.out.println(dateformath2h+" "+SQL);
        //vlog4j.log("[SQL] " + SQL);
            try {
            stm=conn.createStatement();
            stm.executeUpdate(SQL);
            stm.close();
            return "01";
        } catch (SQLException ex) {
            //vlog4j.log("[ERROR] " + ex.toString());
               System.out.println(dateformath2h + ex.toString());
            Logger.getLogger(msql.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
    }
public String proses_delete(String SQL)
    {
        System.out.println(dateformath2h+" "+SQL);
        //vlog4j.log("[SQL] " + SQL);
            try {
            stm=conn.createStatement();
            stm.executeUpdate(SQL);
            stm.close();
            return "01";
        } catch (SQLException ex) {
            //vlog4j.log("[ERROR] " + ex.toString());
               System.out.println(dateformath2h + ex.toString());
            Logger.getLogger(msql.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
    }
public boolean proses_insert_nolog(String SQL )
    {
           try {
            stm=conn.createStatement();
            stm.executeUpdate(SQL);
            stm.close();
            return true;
        } catch (SQLException ex) {
            //vlog4j.log("[ERROR] " + ex.toString());
               System.out.println(ex.toString());
            Logger.getLogger(msql.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

public boolean call_proc_c_pranota(String kd_kapal,String ves_id, String kd_agen,String username)
    {
//        String seq_no_order="";
//        String seq_epb = "";
        
//        WEB_INSERT_INVOICE_HEADER (VORDER CHAR, = bat_no 
//                                   VDO_NO CHAR, 
//                                   VDO_EXPIRED DATE,
//                                   VJENIS_DOC CHAR,
//                                   VNO_DOC CHAR,                
//                                   VTGL_DOC DATE,
//                                   VOLD_SERVICE_CODE CHAR,    
//                                   VOLD_PROFORMA CHAR,            
//                                   VVOI CHAR,
//                                   VINVOICE_NO CHAR,
//                                   VNO_FAKTUR CHAR)
        String SQL = "{ call C_PRANOTA(?,?,?,?,?) }";
        try {
            CallableStatement cs = conn.prepareCall(SQL);
            cs.setString(1, "");
            cs.setString(2, kd_kapal);
            cs.setString(3, ves_id);
            cs.setString(4, kd_agen);
            cs.setString(5, username);
//            cs.registerOutParameter(2, java.sql.Types.VARCHAR);
            cs.execute();
//            seq_no_order = cs.getString(2).trim();
             return true;
        } catch (SQLException ex) {
            Logger.getLogger(msql.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
public boolean call_proc_uper_kapal(String ves_id,String kd_agen,String no_ppkb,String ppkb_ke)
    {
//        String seq_no_order="";
//        String seq_epb = "";
        
//        WEB_INSERT_INVOICE_HEADER (VORDER CHAR, = bat_no 
//                                   VDO_NO CHAR, 
//                                   VDO_EXPIRED DATE,
//                                   VJENIS_DOC CHAR,
//                                   VNO_DOC CHAR,                
//                                   VTGL_DOC DATE,
//                                   VOLD_SERVICE_CODE CHAR,    
//                                   VOLD_PROFORMA CHAR,            
//                                   VVOI CHAR,
//                                   VINVOICE_NO CHAR,
//                                   VNO_FAKTUR CHAR)
        String SQL = "{ call UPER_KAPAL(?,?,?) }";
        try {
            CallableStatement cs = conn.prepareCall(SQL);
            cs.setString(1, "pnt2.g1_ves_id('"+ves_id+"','"+kd_agen+"')");
            cs.setString(2, no_ppkb);
            cs.setString(3, ppkb_ke);
//            cs.registerOutParameter(2, java.sql.Types.VARCHAR);
            cs.execute();
//            seq_no_order = cs.getString(2).trim();
             return true;
        } catch (SQLException ex) {
            Logger.getLogger(msql.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
public boolean create_proc(String SQL)
    {
        System.out.println(dateformath2h+" "+SQL);
            try {
            stm=conn.createStatement();
            stm.execute(SQL);
            stm.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(msql.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public void dbcommit(database db) {
        try {
           
            db.getConn().commit();
        } catch (SQLException ex) {
            try {
                db.getConn().rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(msql.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(msql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dbrollback(database db) {
        try {
            db.getConn().rollback();
        } catch (SQLException ex) {
            Logger.getLogger(msql.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
   
}
