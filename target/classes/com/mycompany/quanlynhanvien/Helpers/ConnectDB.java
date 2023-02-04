/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.Helpers;

/**
 *
 * @author User
 */
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectDB {
    public static Connection con= null;
    private static ConnectDB instance = new ConnectDB();
    public static ConnectDB getInstance() {
		return instance;
	}
	public static void connect() throws SQLException{
		final String url="jdbc:sqlserver://localhost:1433;databaseName=QLNhaSach";
		final String user="sa";
		final String password="sapassword";
		 con = DriverManager
                .getConnection("jdbc:sqlserver://localhost:1433;databaseName=QLNhaSach;user=sa;password=sapassword"); 
	}
	public static void disconnect() {
		if(con !=null)
			try {
				con.close();
			} catch (SQLException e) { e.printStackTrace();}
	}
	public static Connection getConnection() {
		try {
			connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
        /*public static void main(String[] args) throws SQLException {
        
         try (Connection con = ConnectDB.getConnection()) {
            
            System.out.print("Kết nối thành công");
            System.out.print(con.getWarnings());
        } catch (SQLServerException ex) {
           
            
        }
    }*/
}
