    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.Helpers;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;

/**
 *
 * @author User
 */
public class DatabaseHelper {
    public static Connection openConnection() throws Exception{
        var server = "DESKTOP-31MOGNN\\SQLEXPRESS";
        var use = "sa";
        var password = "sapassword";
        var db = "QLNhaSach";
        var port = 1433;
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser(use);
        ds.setPassword(password);
        ds.setDatabaseName(db);
        ds.setServerName(server);
        ds.setPortNumber(port);
        try (Connection conn = ds.getConnection()) {
            return conn;
        } catch (SQLServerException ex) {
           
            return null;
        }
        
    }
   
   /* public static void main(String[] args) throws SQLException {
         var server = "DESKTOP-31MOGNN\\SQLEXPRESS";
        var use = "sa";
        var password = "sapassword";
        var db = "QLNhaSach";
        var port = 1433;
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser(use);
        ds.setPassword(password);
        ds.setDatabaseName(db);
        ds.setServerName(server);
        ds.setPortNumber(port);
        try (Connection con = ds.getConnection()) {
            
            System.out.print("Kết nối thành công");
        } catch (SQLServerException ex) {
           
            
        }
    }*/
         
}
