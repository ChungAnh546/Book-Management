/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.Dao;

import com.mycompany.quanlynhanvien.Helpers.ConnectDB;
import com.mycompany.quanlynhanvien.model.KhachHang;
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
public class KhachHangDao {
    public List<KhachHang> findAll ()throws Exception
    {
        String sql="select * from dbo.KhachHang";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    List<KhachHang> list = new ArrayList<>();
                    while(rs.next())
                    {
                        KhachHang kh = createKhachHang(rs);
                        list.add(kh);
                        
                    }return list;
                    
                }
        }
           
    }
    public String ghepChuoi(String Select, String ThuocTinh, String giaTriSS)
    {
        
        String sql = "select maKH from dbo.KhachHang where maKH in ("+Select+") and "+ThuocTinh+" = N'"+giaTriSS+"'"; 
        return sql;
        
    }
    
    public List<KhachHang> findDK (String maKH, String tenKH, String sDT, int tuoi)throws Exception
    {
        String sql0,sql1,sql2,sql3;
        if(tuoi>0){
        sql0="SELECT maKH\n" +
                " FROM [dbo].[KhachHang] where tuoi = "+tuoi+" ";}
        else{
        sql0="SELECT maKH\n" +
                " FROM [dbo].[KhachHang] ";
        }
        
        if(maKH.equals(""))
        {
            sql1=sql0;
            if(tenKH.equals(""))
            {
                sql2=sql1;
                if(sDT.equals(""))
                {
                    sql3 = sql2;
                }
                else{
                sql3 = ghepChuoi(sql2, "sDT", sDT);
                }
            }else{
                sql2 = ghepChuoi(sql1, "tenKH",tenKH );
                 if(sDT.equals(""))
                {
                    sql3 = sql2;
                }
                else{
                sql3 = ghepChuoi(sql2, "sDT", sDT);
                }
            //có tennv
            }
        }else{
            sql1=ghepChuoi(sql0, "maKH", maKH);
            if(tenKH.equals(""))
            {
                sql2=sql1;
                if(sDT.equals(""))
                {
                    sql3 = sql2;
                }
                else{
                sql3 = ghepChuoi(sql2, "sDT", sDT);
                }
            }else{
                sql2 = ghepChuoi(sql1, "tenKH",tenKH );
                 if(sDT.equals(""))
                {
                    sql3 = sql2;
                }
                else{
                sql3 = ghepChuoi(sql2, "sDT", sDT);
                }
            //có tennv
            }
            //Có manv
        }
        String sql="select * from dbo.KhachHang where maKH in ("+sql3+")";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    List<KhachHang> list = new ArrayList<>();
                    while(rs.next())
                    {
                        KhachHang kh = createKhachHang(rs);
                        list.add(kh);
                        
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
    private KhachHang createKhachHang(final ResultSet rs) throws NumberFormatException, SQLException {
        KhachHang kh = new KhachHang();
        kh.setMaKH(rs.getString("maKH"));
        kh.setTenKH(rs.getString("tenKH"));
        kh.setDiaChi(rs.getString("diaChi"));
        
        kh.setsDT(rs.getString("sDT"));
        
        kh.setEmail(rs.getString("eMail"));
        kh.setTuoi(rs.getInt("tuoi"));
        
        return kh;
    }
     public KhachHang find (String maKH)throws Exception
    {
       
      
        String sql="select * from dbo.KhachHang where maKH=?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,maKH);
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        KhachHang kh = createKhachHang(rs);
                        return kh;
                    }
                    
                }return null;
        }
           
    }
     
     public KhachHang findTen (String tenKH)throws Exception
    {
       
      
        String sql="select * from dbo.KhachHang where tenKH=?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,tenKH);
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        KhachHang kh = createKhachHang(rs);
                        return kh;
                    }
                    
                }return null;
        }
           
    }
     
     public KhachHang findSDT (String sDT)throws Exception
    {
       
      
        String sql="select * from dbo.KhachHang where sDT=?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,sDT);
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        KhachHang kh = createKhachHang(rs);
                        return kh;
                    }
                    
                }return null;
        }
           
    }
     
      public boolean delete (String maKH)throws Exception
    {
       
      
        String sql=" DELETE FROM dbo.KhachHang"+
                  " WHERE maKH = ?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,maKH);
                
                return pstmt.executeUpdate()>0;
        }
           
    }
    
    public boolean update (KhachHang kh)throws Exception
    {
        
        String sql="UPDATE dbo.KhachHang"
                + " SET tenKH = ?,diaChi = ?,sDT = ?,eMail = ?,tuoi = ?" + 
                " WHERE maKH = ?";
        
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(6,kh.getMaKH());
                pstmt.setString(1,kh.getTenKH());
                pstmt.setString(2,kh.getDiaChi());
               
                pstmt.setString(3,kh.getsDT());
                
                pstmt.setString(4,kh.getEmail());

                pstmt.setInt(5,kh.getTuoi());
                
                
                return pstmt.executeUpdate()>0;
        }
           
    }
    public boolean insert (KhachHang kh)throws Exception
    {
        
        String sql="INSERT INTO dbo.KhachHang(maKH,tenKH,diaChi,sDT,eMail,tuoi)"+
                    " VALUES(?,?,?,?,?,?)";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                
                pstmt.setString(1,kh.getMaKH());
                pstmt.setString(2,kh.getTenKH());
                pstmt.setString(3,kh.getDiaChi());
                
                pstmt.setString(4,kh.getsDT());
                
                pstmt.setString(5,kh.getEmail());

                pstmt.setInt(6,kh.getTuoi());
                
                return pstmt.executeUpdate()>0;
        }
           
    }
}
