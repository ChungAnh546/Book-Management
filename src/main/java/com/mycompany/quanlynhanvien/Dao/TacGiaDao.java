/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.Dao;

import com.mycompany.quanlynhanvien.Helpers.ConnectDB;
import com.mycompany.quanlynhanvien.model.NhaCungCap;
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
public class TacGiaDao {
    
    public TacGia findMaTacGiaByTen (String tenTacGia)throws Exception
    {
       
      String reg = "^[T][G][0-9]{3}$";
       if(!tenTacGia.matches(reg)){
        String sql="select * from dbo.TacGia where tenTacGia=?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,tenTacGia);
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        TacGia tg = createTacGia(rs);
                        
                        return tg;
                    }
                    
                }return null;
        }}
       TacGia tg = new TacGia();
       tg.setMaTacGia(tenTacGia);
       return tg;
           
    }
    public List<String> findTenTacGia ()throws Exception
    {
       
      
        String sql="SELECT tenTacGia FROM TacGia ";
       
        try(
                    Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                
                 try(ResultSet rs =  pstmt.executeQuery();)
                {
                    List<String> list = new ArrayList<>();
                    while(rs.next())
                    {
                        String tenTacGia = rs.getString("tenTacGia");
                        list.add(tenTacGia);
                        
                    }return list;
                    
                }
        }
           
    }

    public String ghepChuoi(String Select, String ThuocTinh, String giaTriSS)
    {
        
        String sql = "select maTacGia from dbo.TacGia where maTacGia in ("+Select+") and "+ThuocTinh+" = N'"+giaTriSS+"'"; 
        return sql;
        
    }
    
    public List<TacGia> findDK (String maTG, String NgaySinh)throws Exception
    {
        String sql0,sql1;
        if(maTG.replaceAll("\\s","").equals(""))
        {
            sql0="SELECT maTacGia\n" +
                                 " FROM [dbo].[TacGia] ";
        }else
        {
            sql0="SELECT maTacGia\n" +
                                 " FROM [dbo].[TacGia] where maTacGia = N'"+maTG+"'";
        }
            
        
        
        
        if(NgaySinh.equals(""))
        {
            //ko co ten san pham chua xet don gia tro len
            sql1=sql0;
         }  else{sql1 = ghepChuoi(sql0, "ngaySinh", NgaySinh);} 
              
                 
        
        String sql="select * from dbo.TacGia where maTacGia in ("+sql1+")";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    List<TacGia> list = new ArrayList<>();
                    while(rs.next())
                    {
                        TacGia nsp = createTacGia(rs);
                        list.add(nsp);
                        
                    }return list;
                    
                }
        }
    }
    
    private TacGia createTacGia(ResultSet rs) throws SQLException {
        TacGia tg = new TacGia();
        
        tg.setMaTacGia(rs.getString("maTacGia"));
        tg.setTenTacGia(rs.getString("tenTacGia"));
        String ns = String.valueOf(rs.getDate("ngaySinh"));
        tg.setNgaySinh(ns);
        tg.setQueQuan(rs.getString("Quequan"));
        
        
        
        
        return tg;//To change body of generated methods, choose Tools | Templates.
    }
    public List<TacGia> findAll ()throws Exception
    {
        String sql="select * from dbo.TacGia";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    List<TacGia> list = new ArrayList<>();
                    while(rs.next())
                    {
                        TacGia tg = createTacGia(rs);
                        list.add(tg);
                        
                    }return list;
                    
                }
        }
           
    }
    /*public String ghepChuoi(String Select, String ThuocTinh, String giaTriSS)
    {
    
    String sql = "select maSanPham from dbo.SanPham where maSanPham in ("+Select+") and "+ThuocTinh+" = N'"+giaTriSS+"'";
    return sql;
    
    }*/
    
    /*public List<SanPham> findDK (String maSanPham, String tenSanPham, String chucVu, String gioiTinh)throws Exception
    {
    String sql0,sql1,sql2,sql3;
    sql0="SELECT maSanPham\n" +
    " FROM [dbo].[SanPham] where gioiTinh= N'"+gioiTinh+"'";
    
    if(maSanPham.equals(""))
    {
    sql1=sql0;
    if(tenSanPham.equals(""))
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
    sql2 = ghepChuoi(sql1, "tenSanPham",tenSanPham );
    if(chucVu.equals(""))
    {
    sql3 = sql2;
    }
    else{
    sql3 = ghepChuoi(sql2, "chucVu", chucVu);
    }
    //có tensp
    }
    }else{
    sql1=ghepChuoi(sql0, "maSanPham", maSanPham);
    if(tenSanPham.equals(""))
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
    sql2 = ghepChuoi(sql1, "tenSanPham",tenSanPham );
    if(chucVu.equals(""))
    {
    sql3 = sql2;
    }
    else{
    sql3 = ghepChuoi(sql2, "chucVu", chucVu);
    }
    //có tensp
    }
    //Có masp
    }
    String sql="select * from dbo.SanPham where maSanPham in ("+sql3+")";
    
    try(
    Connection con = ConnectDB.getConnection();
    PreparedStatement pstmt = con.prepareStatement(sql);
    
    ){
    
    try(ResultSet rs =  pstmt.executeQuery();)
    {
    List<SanPham> list = new ArrayList<>();
    while(rs.next())
    {
    SanPham sp = createSanPham(rs);
    list.add(sp);
    
    }return list;
    
    }
    }
    
    }*/
  //  public static void main(String[] args) throws Exception {
       // SanPhamDao dao = new SanPhamDao();
        //List<SanPham> list = dao.findAll();
        //for(SanPham it : list)
        //{
            //System.out.println(it.toString());
        //}
       // SanPham sp= dao.find("3123");
       // System.out.println(sp.toString());
   // }
    
     /*public SanPham find (String maSanPham)throws Exception
    {
       
      
        String sql="select * from dbo.SanPham where maSanPham=?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,maSanPham);
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        SanPham sp = createSanPham(rs);
                        return sp;
                    }
                    
                }return null;
        }
           
    }*/
      public TacGia find (String maTG)throws Exception
    {
       
      
        String sql="select * from dbo.TacGia where maTacGia = ?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,maTG);
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        TacGia tg = createTacGia(rs);
                        
                        return tg;
                    }
                    
                }return null;
        }
           
    }
      
      
      
      public boolean delete (String maTG)throws Exception
    {
       
      
        String sql=" DELETE FROM dbo.TacGia"+
                  " WHERE maTacGia = ?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,maTG);
                
                return pstmt.executeUpdate()>0;
        }
           
    }
    
    /*public boolean update (SanPham sp)throws Exception
    {
        
        String sql="UPDATE dbo.SanPham"
                + " SET tenSanPham = ?,chucVu = ?,gioiTinh = ?,ngaySinh = ?,sDT = ?,maTK = ?,TinhTrang = ?" + 
                " WHERE maSanPham = ?";
        
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(8,sp.getMaSanPham());
                pstmt.setString(1,sp.getTenSanPham());
                pstmt.setString(2,sp.getChucVu());
                String gt = String.valueOf(sp.getGioiTinh());
                pstmt.setString(3,gt);
                
                pstmt.setString(4,sp.getNgaySinh());

                pstmt.setString(5,sp.getsDT());
                if(sp.getMaTK().equals(""))
                {
                  pstmt.setString(6,null);
                }else{
                pstmt.setString(6,sp.getMaTK());}
                pstmt.setString(7,sp.getTinhTrang());
                
                return pstmt.executeUpdate()>0;
        }
           
    }*/
    public boolean update (TacGia tg)throws Exception
    {
        
        String sql="UPDATE dbo.TacGia"
                + " SET tenTacGia = ?,ngaySinh = ?,Quequan = ?" + 
                " WHERE maTacGia = ?";
        
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(4,tg.getMaTacGia());
                pstmt.setString(1,tg.getTenTacGia());
                pstmt.setString(2,tg.getNgaySinh());
                pstmt.setString(3,tg.getQueQuan());

                
                return pstmt.executeUpdate()>0;
        }
           
    }
    /*public boolean insert (SanPham sp)throws Exception
    {
        
        String sql="INSERT INTO dbo.SanPham(maSanPham,tenSanPham,chucVu,gioiTinh,ngaySinh,sDT,maTK,TinhTrang)"+
                    " VALUES(?,?,?,?,?,?,?,?)";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                
                pstmt.setString(1,sp.getMaSanPham());
                pstmt.setString(2,sp.getTenSanPham());
                pstmt.setString(3,sp.getChucVu());
                String gt = String.valueOf(sp.getGioiTinh());
                pstmt.setString(4,gt);
                
                pstmt.setString(5,sp.getNgaySinh());

                pstmt.setString(6,sp.getsDT());
                if(sp.getMaTK().equals(""))
                {
                  pstmt.setString(7,null);
                }else
                {pstmt.setString(7,sp.getMaTK());}
                
              
                return pstmt.executeUpdate()>0;
        }
           
    }*/
     public boolean insert (TacGia tg)throws Exception
    {
        
        String sql="INSERT INTO dbo.TacGia(maTacGia,tenTacGia,ngaySinh,Quequan)"+
                    " VALUES(?,?,?,?)";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                
                pstmt.setString(1,tg.getMaTacGia());
                pstmt.setString(2,tg.getTenTacGia());
                
                
                pstmt.setString(3,tg.getNgaySinh());

                pstmt.setString(4,tg.getQueQuan());
                
                
                return pstmt.executeUpdate()>0;
        }
           
    }
}
