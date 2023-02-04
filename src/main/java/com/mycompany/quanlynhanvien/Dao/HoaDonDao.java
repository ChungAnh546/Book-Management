/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.Dao;

import com.mycompany.quanlynhanvien.Helpers.ConnectDB;
import com.mycompany.quanlynhanvien.model.HoaDon;
import com.mycompany.quanlynhanvien.model.KhachHang;
import com.mycompany.quanlynhanvien.model.NhaCungCap;
import com.mycompany.quanlynhanvien.model.NhanVien;
import com.mycompany.quanlynhanvien.model.NhomSanPham;
import com.mycompany.quanlynhanvien.model.SanPham;
import com.mycompany.quanlynhanvien.model.TacGia;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.rowset.serial.SerialBlob;

/**
 *
 * @author User
 */
public class HoaDonDao {
     public boolean insert (HoaDon hd)throws Exception
    {
        
        String sql="set dateformat DMY INSERT INTO dbo.HoaDon(maHD,ngayLap,maNhanVien,maKH,TongTien,ghiChu)"+
                    " VALUES(?,?,?,?,?,?)";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                
                pstmt.setString(1,hd.getMaHD());
                pstmt.setDate(2,hd.getNgayLap());
                pstmt.setString(3,hd.getNhanVien().getMaNhanVien());
                
                pstmt.setString(4,hd.getKhachHang().getMaKH());
                
                pstmt.setFloat(5,hd.getTongTien());
                pstmt.setString(6,hd.getGhiChu());

                
                return pstmt.executeUpdate()>0;
        }
           
    }
     public List<HoaDon> findAll ()throws Exception
    {
        String sql="select * from dbo.HoaDon";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    List<HoaDon> list = new ArrayList<>();
                    while(rs.next())
                    {
                        HoaDon hd = createHoaDon(rs);
                        list.add(hd);
                        
                    }return list;
                    
                }
        }
           
    }
     public String ghepChuoi(String Select, String ThuocTinh, String giaTriSS)
    {
        
        String sql = "select maHD from dbo.HoaDon where maHD in ("+Select+") and "+ThuocTinh+" = N'"+giaTriSS+"'"; 
        return sql;
        
    }
    public List<HoaDon> findDK (String Ma, String NgayLap)throws Exception
    {
        String regNV = "^[N][V][0-9]{3}$";
        String regKH = "^[K][H][0-9]{3}$";
        String regHD = "^[H][D][0-9]{5}$";
                
                            
        String sql0,sql1;
        if(Ma.equals("")){sql0="SELECT maHD\n" +
                                            " FROM [dbo].[HoaDon] ";}
        else{
            if(Ma.matches(regHD)){
            sql0="SELECT maHD\n" +
                                 " FROM [dbo].[HoaDon] where maHD = N'"+Ma+"'";}
            else {if(Ma.matches(regKH))
            {
                sql0="SELECT maHD\n" +
                                 " FROM [dbo].[HoaDon] where maKH = N'"+Ma+"'";
            }else{
                if(Ma.matches(regNV))
                {
                    sql0="SELECT maHD\n" +
                                 " FROM [dbo].[HoaDon] where maNhanVien = N'"+Ma+"'"; 
                }else{
                return null;
                }
            }
                }
        }
        
        
        if(NgayLap.equals(""))
        {
            //ko co ten san pham chua xet don gia tro len
            sql1=sql0;
         }  else{sql1 = ghepChuoi(sql0, "ngayLap", NgayLap);} 
              
                 
        
        String sql="select * from dbo.HoaDon where maHD in ("+sql1+")";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    List<HoaDon> list = new ArrayList<>();
                    while(rs.next())
                    {
                        HoaDon nsp = createHoaDon(rs);
                        list.add(nsp);
                        
                    }return list;
                    
                }
        }
    }
     
     public HoaDon find (String maHD)throws Exception
    {
       
      
        String sql="select * from dbo.HoaDon where maHD = ?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,maHD);
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        HoaDon hd = createHoaDon(rs);
                        
                        return hd;
                    }
                    
                }return null;
        }
           
    }
     private HoaDon createHoaDon(final ResultSet rs) throws NumberFormatException, SQLException {
        HoaDon hd = new HoaDon();
        hd.setMaHD(rs.getString("maHD"));
        hd.setNgayLap(rs.getDate("ngayLap"));
        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(rs.getString("maNhanVien"));
        hd.setNhanVien(nv);
        
        KhachHang kh = new KhachHang();
        kh.setMaKH(rs.getString("maKH"));
        hd.setKhachHang(kh);
        
        hd.setTongTien(rs.getFloat("TongTien"));
        hd.setGhiChu(rs.getString("ghiChu"));

        return hd;
    }
}