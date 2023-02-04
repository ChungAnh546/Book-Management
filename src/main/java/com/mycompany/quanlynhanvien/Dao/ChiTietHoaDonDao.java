/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.Dao;

import com.mycompany.quanlynhanvien.Helpers.ConnectDB;
import com.mycompany.quanlynhanvien.model.ChiTietHoaDon;
import com.mycompany.quanlynhanvien.model.HoaDon;
import com.mycompany.quanlynhanvien.model.KhachHang;
import com.mycompany.quanlynhanvien.model.NhanVien;
import com.mycompany.quanlynhanvien.model.SanPham;
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
public class ChiTietHoaDonDao {
     public boolean insert (ChiTietHoaDon cthd)throws Exception
    {
        
        String sql="INSERT INTO dbo.ChiTietHoaDon(thanhTien,donGia,maHD,maSP,Soluong)"+
                    " VALUES(?,?,?,?,?)";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                
                pstmt.setFloat(1,cthd.getThanhTien());
                pstmt.setFloat(2,cthd.getDonGia());
                pstmt.setString(3,cthd.getHD().getMaHD());
                
                pstmt.setString(4,cthd.getSP().getMaSP());
                
                pstmt.setInt(5,cthd.getSoluong());
               

                
                return pstmt.executeUpdate()>0;
        }
           
    }
     
    public List<ChiTietHoaDon> find (String maHD)throws Exception
    {
       
      
        String sql="select * from dbo.ChiTietHoaDon where maHD = ?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,maHD);
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                   List<ChiTietHoaDon> list = new ArrayList<>();
                    while(rs.next())
                    {
                        ChiTietHoaDon cthd = createChiTietHoaDon(rs);
                        list.add(cthd);
                        
                    }return list;
                    
                }
        }
           
    }
     private ChiTietHoaDon createChiTietHoaDon(final ResultSet rs) throws NumberFormatException, SQLException {
        ChiTietHoaDon cthd = new ChiTietHoaDon();
        cthd.setThanhTien(rs.getFloat("thanhTien"));
        cthd.setDonGia(rs.getFloat("donGia"));
        HoaDon hd = new HoaDon();
        hd.setMaHD(rs.getString("maHD"));
        cthd.setHD(hd);
        
        SanPham sp = new SanPham();
        sp.setMaSP(rs.getString("maSP"));
        cthd.setSP(sp);
        
        cthd.setSoluong(rs.getInt("Soluong"));
       

        return cthd;
    }
}
