/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config;
import java.net.InetAddress;

/**
 *
 * @author hp
 */
public class config {
    private String baseURL;
    public void setbaseUrl(String URL) {
        this.baseURL = URL;
    }

    public String baseUrl() {
        return baseURL;

    }

    public String baseIP(InetAddress address) {
        return address.getHostAddress();
        //return "makassar.inaport4.co.id";

    }
    
    public String fcurrency() {
        return "fm999,999,999,999,999,990D00";
    }
    
    public String fdate() {
        return "dd/mm/yyyy";
    }
    public String fdatetime() {
        return "dd/mm/yyyy hh24:mi";
    }
    
    public String conn_inaport() {
        return "Y";
//        return "N";
    }
    
    public String conn_h2h() {
//        return "Y";
        return "N";
    }

    public String conn_ibs() {
        return "Y";
//        return "N";
    }
    //linux

    public String file_upload() {
        return "/home/file_upload/";
    }

    public String file_upload_storage() {
        return "/home/file_upload_storage/";
    }
    //windows
//    public String file_upload(){
//       return "c:/temp/file_upload/";
//    }
//    public String file_upload_storage(){
//       return "c:/file_upload_storage/";
//    }
    
 
}

