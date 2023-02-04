/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.Dao;

import com.mycompany.quanlynhanvien.Helpers.ConnectDB;
import com.mycompany.quanlynhanvien.model.ChiTietHoaDon;
import com.mycompany.quanlynhanvien.model.HoaDon;
import java.awt.desktop.PrintFilesEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class ThongKeDao {
    
     public int TongHD () throws SQLException
    {
         
        
        String sql=" SELECT TongHD = count(maHD) " +
                            "  FROM [dbo].[HoaDon]";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    
                   if(rs.next())
                    {
                       int tongHD = rs.getInt("TongHD");
                       return tongHD;
                    }
                        
                        
                    }return 0;
                
                    
               } 
    }            

     public String SPMuaNN () throws SQLException
    {
         
        
        String sql=" SELECT  Top 1 SoLuong = sum(Soluong) , maSP\n" +
                                "  FROM [dbo].[ChiTietHoaDon]\n" +
                                "  group by maSP\n" +
                                "  order by sum(Soluong) desc";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    
                   if(rs.next())
                    {
                       int soLuong = rs.getInt("SoLuong");
                       String maSP = rs.getString("maSP").replaceAll("\\s","");
                       String chuoi = maSP+"-"+soLuong;
                       return chuoi;
                    }
                        
                        
                    }return null;
                
                    
               } 
    }      
     public int TongSP () throws SQLException
    {
         
        
        String sql=" SELECT  TongSP = count(maSP)\n" +
                   "  FROM [dbo].[SanPham] ";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    
                   if(rs.next())
                    {
                       int soLuong = rs.getInt("TongSP");
                       
                       return soLuong;
                    }
                        
                        
                    }return 0;
                
                    
               } 
    }      
     public int SPHetHang () throws SQLException
    {
         
        
        String sql=" SELECT  SP = count(maSP)\n" +
                   "  FROM [dbo].[SanPham]\n" +
                   "where soLuongTon = 0";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    
                   if(rs.next())
                    {
                       int soLuong = rs.getInt("SP");
                       
                       return soLuong;
                    }
                        
                        
                    }return 0;
                
                    
               } 
    }      
     public int TongKH () throws SQLException
    {
         
        
        String sql=" SELECT  TongKH = count(maKH)\n" +
                   "  FROM [dbo].[KhachHang]";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    
                   if(rs.next())
                    {
                       int soLuong = rs.getInt("TongKH");
                       
                       return soLuong;
                    }
                        
                        
                    }return 0;
                
                    
               } 
    }      
     public int TongNV () throws SQLException
    {
         
        
        String sql=" SELECT  TongNV = count (maNhanVien)\n" +
                    "  FROM [dbo].[NhanVien]";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    
                   if(rs.next())
                    {
                       int soLuong = rs.getInt("TongNV");
                       
                       return soLuong;
                    }
                        
                        
                    }return 0;
                
                    
               } 
    }      
     public int NVConLam () throws SQLException
    {
         
        
        String sql=" SELECT  NV = count (maNhanVien)\n" +
                    "  FROM [dbo].[NhanVien]\n" +
                    "  where TinhTrang = N'Vẫn làm việc'";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    
                   if(rs.next())
                    {
                       int soLuong = rs.getInt("NV");
                       
                       return soLuong;
                    }
                        
                        
                    }return 0;
                
                    
               } 
    }   
           public static void main(String[] args) throws SQLException {
               ThongKeDao dao = new ThongKeDao();
               System.out.println(dao.SPMuaNN());
    }
     
                
        
           
    
}
