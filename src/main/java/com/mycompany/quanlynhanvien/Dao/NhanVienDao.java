/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.Dao;
import com.mycompany.quanlynhanvien.Helpers.ConnectDB;
import com.mycompany.quanlynhanvien.model.NhanVien;
import com.mycompany.quanlynhanvien.model.TaiKhoan;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Blob;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
/**
 *
 * @author User
 */
public class NhanVienDao {

    public List<NhanVien> findAll ()throws Exception
    {
        String sql="select * from dbo.NhanVien";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    List<NhanVien> list = new ArrayList<>();
                    while(rs.next())
                    {
                        NhanVien nv = createNhanVien(rs);
                        list.add(nv);
                        
                    }return list;
                    
                }
        }
           
    }
    public String ghepChuoi(String Select, String ThuocTinh, String giaTriSS)
    {
        
        String sql = "select maNhanVien from dbo.NhanVien where maNhanVien in ("+Select+") and "+ThuocTinh+" = N'"+giaTriSS+"'"; 
        return sql;
        
    }
    
    public List<NhanVien> findDK (String maNhanVien, String tenNhanVien, String chucVu, String gioiTinh)throws Exception
    {
        String sql0,sql1,sql2,sql3;
        sql0="SELECT maNhanVien\n" +
                " FROM [dbo].[NhanVien] where gioiTinh= N'"+gioiTinh+"'";
        
        if(maNhanVien.equals(""))
        {
            sql1=sql0;
            if(tenNhanVien.equals(""))
            {
                sql2=sql1;
                if(chucVu.equals(""))
                {
                    sql3 = sql2;
                }
                else{
                sql3 = ghepChuoi(sql2, "chucVu", chucVu);
                }
            }else{
                sql2 = ghepChuoi(sql1, "tenNhanVien",tenNhanVien );
                 if(chucVu.equals(""))
                {
                    sql3 = sql2;
                }
                else{
                sql3 = ghepChuoi(sql2, "chucVu", chucVu);
                }
            //có tennv
            }
        }else{
            sql1=ghepChuoi(sql0, "maNhanVien", maNhanVien);
            if(tenNhanVien.equals(""))
            {
                sql2=sql1;
                if(chucVu.equals(""))
                {
                    sql3 = sql2;
                }
                else{
                sql3 = ghepChuoi(sql2, "chucVu", chucVu);
                }
            }else{
                sql2 = ghepChuoi(sql1, "tenNhanVien",tenNhanVien );
                 if(chucVu.equals(""))
                {
                    sql3 = sql2;
                }
                else{
                sql3 = ghepChuoi(sql2, "chucVu", chucVu);
                }
            //có tennv
            }
            //Có manv
        }
        String sql="select * from dbo.NhanVien where maNhanVien in ("+sql3+")";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    List<NhanVien> list = new ArrayList<>();
                    while(rs.next())
                    {
                        NhanVien nv = createNhanVien(rs);
                        list.add(nv);
                        
                    }return list;
                    
                }
        }
           
    }
  //  public static void main(String[] args) throws Exception {
       // NhanVienDao dao = new NhanVienDao();
        //List<NhanVien> list = dao.findAll();
        //for(NhanVien it : list)
        //{
            //System.out.println(it.toString());
        //}
       // NhanVien nv= dao.find("3123");
       // System.out.println(nv.toString());
   // }
    private NhanVien createNhanVien(final ResultSet rs) throws NumberFormatException, SQLException {
        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(rs.getString("maNhanVien"));
        nv.setTenNhanVien(rs.getString("tenNhanVien"));
        nv.setChucVu(rs.getString("chucVu"));
        
        nv.setGioiTinh(rs.getString("gioiTinh"));
        String ns = String.valueOf(rs.getDate("ngaySinh"));
        nv.setNgaySinh(ns);
        nv.setTinhTrang(rs.getString("tinhTrang"));
        nv.setsDT(rs.getString("sDT"));
        
        nv.setMaTK(rs.getString("maTK"));
        Blob blob = rs.getBlob("hinh");
        if(blob != null)
        {
            nv.setHinh(blob.getBytes(1,(int) blob.length()));
        } 
        return nv;
    }
     /*public NhanVien find (String maNhanVien)throws Exception
    {
       
      
        String sql="select * from dbo.NhanVien where maNhanVien=?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,maNhanVien);
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        NhanVien nv = createNhanVien(rs);
                        return nv;
                    }
                    
                }return null;
        }
           
    }*/
      public NhanVien find (String maNhanVien)throws Exception
    {
       
      
        String sql="select * from dbo.NhanVien where maNhanVien=?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,maNhanVien);
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        NhanVien nv = createNhanVien(rs);
                        
                        return nv;
                    }
                    
                }return null;
        }
           
    }
      public NhanVien findSDT (String sDT)throws Exception
    {
       
      
        String sql="select * from dbo.NhanVien where sDT=?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,sDT);
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        NhanVien nv = createNhanVien(rs);
                        
                        return nv;
                    }
                    
                }return null;
        }
           
    }
      public NhanVien findNVByMaTK (String maTK)throws Exception
    {
       
      
        String sql="select * from dbo.NhanVien where maTK=?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,maTK);
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        NhanVien nv = createNhanVien(rs);
                        
                        return nv;
                    }
                    
                }return null;
        }
           
    }
      public List<String> findMaTK ()throws Exception
    {
       
      
        String sql="SELECT maTK FROM TaiKhoan WHERE maTK NOT IN(SELECT maTK FROM NhanVien where maTK not in ('NULL'))";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                
                 try(ResultSet rs =  pstmt.executeQuery();)
                {
                    List<String> list = new ArrayList<>();
                    while(rs.next())
                    {
                        String maTK = rs.getString("maTK");
                        list.add(maTK);
                        
                    }return list;
                    
                }
        }
           
    }
      
       public String findTenTK (String maTK)throws Exception
    {
       
      
        String sql="SELECT tenTK FROM TaiKhoan WHERE maTK = ?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                    pstmt.setString(1,maTK);
                 try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        String tenTK = rs.getString("tenTK");                  
                   
                    return tenTK;
                    }
                    
                    
                }return null;
        }
           
    }
       
        
        
      public boolean delete (String maNhanVien)throws Exception
    {
       
      
        String sql=" DELETE FROM dbo.NhanVien"+
                  " WHERE maNhanVien = ?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,maNhanVien);
                
                return pstmt.executeUpdate()>0;
        }
           
    }
    
    /*public boolean update (NhanVien nv)throws Exception
    {
        
        String sql="UPDATE dbo.NhanVien"
                + " SET tenNhanVien = ?,chucVu = ?,gioiTinh = ?,ngaySinh = ?,sDT = ?,maTK = ?,TinhTrang = ?" + 
                " WHERE maNhanVien = ?";
        
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(8,nv.getMaNhanVien());
                pstmt.setString(1,nv.getTenNhanVien());
                pstmt.setString(2,nv.getChucVu());
                String gt = String.valueOf(nv.getGioiTinh());
                pstmt.setString(3,gt);
                
                pstmt.setString(4,nv.getNgaySinh());

                pstmt.setString(5,nv.getsDT());
                if(nv.getMaTK().equals(""))
                {
                  pstmt.setString(6,null);
                }else{
                pstmt.setString(6,nv.getMaTK());}
                pstmt.setString(7,nv.getTinhTrang());
                
                return pstmt.executeUpdate()>0;
        }
           
    }*/
    public boolean update (NhanVien nv)throws Exception
    {
        
        String sql="UPDATE dbo.NhanVien"
                + " SET tenNhanVien = ?,chucVu = ?,gioiTinh = ?,ngaySinh = ?,sDT = ?,maTK = ?,TinhTrang = ?,hinh = ?" + 
                " WHERE maNhanVien = ?";
        
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(9,nv.getMaNhanVien());
                pstmt.setString(1,nv.getTenNhanVien());
                pstmt.setString(2,nv.getChucVu());
                String gt = String.valueOf(nv.getGioiTinh());
                pstmt.setString(3,gt);
                
                pstmt.setString(4,nv.getNgaySinh());

                pstmt.setString(5,nv.getsDT());
                if(nv.getMaTK().equals(""))
                {
                  pstmt.setString(6,null);
                }else{
                pstmt.setString(6,nv.getMaTK());}
                pstmt.setString(7,nv.getTinhTrang());
                
                if(nv.getHinh()!=null)
                {
                    Blob hinh = new SerialBlob(nv.getHinh());
                    pstmt.setBlob(8,hinh);
                }
                else{
                       Blob hinh = null;
                       pstmt.setBlob(8,hinh);
                    }
                return pstmt.executeUpdate()>0;
        }
           
    }
    /*public boolean insert (NhanVien nv)throws Exception
    {
        
        String sql="INSERT INTO dbo.NhanVien(maNhanVien,tenNhanVien,chucVu,gioiTinh,ngaySinh,sDT,maTK,TinhTrang)"+
                    " VALUES(?,?,?,?,?,?,?,?)";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                
                pstmt.setString(1,nv.getMaNhanVien());
                pstmt.setString(2,nv.getTenNhanVien());
                pstmt.setString(3,nv.getChucVu());
                String gt = String.valueOf(nv.getGioiTinh());
                pstmt.setString(4,gt);
                
                pstmt.setString(5,nv.getNgaySinh());

                pstmt.setString(6,nv.getsDT());
                if(nv.getMaTK().equals(""))
                {
                  pstmt.setString(7,null);
                }else
                {pstmt.setString(7,nv.getMaTK());}
                
              
                return pstmt.executeUpdate()>0;
        }
           
    }*/
     public boolean insert (NhanVien nv)throws Exception
    {
        
        String sql="INSERT INTO dbo.NhanVien(maNhanVien,tenNhanVien,chucVu,gioiTinh,ngaySinh,sDT,maTK,TinhTrang,hinh)"+
                    " VALUES(?,?,?,?,?,?,?,?,?)";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                
                pstmt.setString(1,nv.getMaNhanVien());
                pstmt.setString(2,nv.getTenNhanVien());
                pstmt.setString(3,nv.getChucVu());
                String gt = String.valueOf(nv.getGioiTinh());
                pstmt.setString(4,gt);
                
                pstmt.setString(5,nv.getNgaySinh());

                pstmt.setString(6,nv.getsDT());
                if(nv.getMaTK().equals(""))
                {
                  pstmt.setString(7,null);
                }else
                {pstmt.setString(7,nv.getMaTK());}
                pstmt.setString(8,nv.getTinhTrang());
                
                if(nv.getHinh()!=null)
                {
                    Blob hinh = new SerialBlob(nv.getHinh());
                    pstmt.setBlob(9,hinh);
                }
                else{
                       Blob hinh = null;
                       pstmt.setBlob(9,hinh);
                    }
                return pstmt.executeUpdate()>0;
        }
           
    }
}
