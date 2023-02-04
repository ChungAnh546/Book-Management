/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.Dao;

import com.mycompany.quanlynhanvien.Helpers.ConnectDB;
import com.mycompany.quanlynhanvien.model.NhaCungCap;
import com.mycompany.quanlynhanvien.model.NhomSanPham;
import com.mycompany.quanlynhanvien.model.SanPham;
import com.mycompany.quanlynhanvien.model.NhomSanPham;
import java.sql.Blob;
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
public class NhomSanPhamDao {
    public List<String> findTenNhomSP ()throws Exception
    {
       
      
        String sql="SELECT tenNhomSP FROM NhomSanPham ";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                
                 try(ResultSet rs =  pstmt.executeQuery();)
                {
                    List<String> list = new ArrayList<>();
                    while(rs.next())
                    {
                        String tenNhomSP = rs.getString("tenNhomSP");
                        list.add(tenNhomSP);
                        
                    }return list;
                    
                }
        }
           
    }
    public NhomSanPham findMaNhomSPByTen (String tenNhomSP)throws Exception
    {
       
      String reg = "^[N][S][P][0-9]{3}$";
       if(!tenNhomSP.matches(reg)){
        String sql="select * from dbo.NhomSanPham where tenNhomSP=?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,tenNhomSP);
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        NhomSanPham nsp = createNhomSanPham(rs);
                        
                        return nsp;
                    }
                    
                }return null;
        }}
       NhomSanPham nsp = new NhomSanPham();
       nsp.setMaNhomSP(tenNhomSP);
       return nsp;
           
    }
    private NhomSanPham createNhomSanPham(final ResultSet rs) throws NumberFormatException, SQLException {
        NhomSanPham nsp = new NhomSanPham();
        nsp.setMaNhomSP(rs.getString("maNhomSP"));
        nsp.setTenNhomSP(rs.getString("tenNhomSP"));
        String ns = String.valueOf(rs.getDate("ngayNhap"));
        
        nsp.setNgayNhap(ns);
        
        
        
        return nsp;
    }
    
    public List<NhomSanPham> findAll ()throws Exception
    {
        String sql="select * from dbo.NhomSanPham";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    List<NhomSanPham> list = new ArrayList<>();
                    while(rs.next())
                    {
                        NhomSanPham nsp = createNhomSanPham(rs);
                        list.add(nsp);
                        
                    }return list;
                    
                }
        }
           
    }
    public String ghepChuoi(String Select, String ThuocTinh, String giaTriSS)
    {
        
        String sql = "select maNhomSP from dbo.NhomSanPham where maNhomSP in ("+Select+") and "+ThuocTinh+" = N'"+giaTriSS+"'"; 
        return sql;
        
    }
    
    public List<NhomSanPham> findDK (String maNSP, String NgayNhap)throws Exception
    {
        String sql0,sql1;
        if(maNSP.equals("")){sql0="SELECT maNhomSP\n" +
                                            " FROM [dbo].[NhomSanPham] ";}
        else{
            sql0="SELECT maNhomSP\n" +
                                 " FROM [dbo].[NhomSanPham] where maNhomSP = N'"+maNSP+"'";
        }
        
        
        if(NgayNhap.equals(""))
        {
            //ko co ten san pham chua xet don gia tro len
            sql1=sql0;
         }  else{sql1 = ghepChuoi(sql0, "ngayNhap", NgayNhap);} 
              
                 
        
        String sql="select * from dbo.NhomSanPham where maNhomSP in ("+sql1+")";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    List<NhomSanPham> list = new ArrayList<>();
                    while(rs.next())
                    {
                        NhomSanPham nsp = createNhomSanPham(rs);
                        list.add(nsp);
                        
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
      public NhomSanPham find (String maNSP)throws Exception
    {
       
      
        String sql="select * from dbo.NhomSanPham where maNhomSP = ?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,maNSP);
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        NhomSanPham nsp = createNhomSanPham(rs);
                        
                        return nsp;
                    }
                    
                }return null;
        }
           
    }
      
      
      
      public boolean delete (String maNSP)throws Exception
    {
       
      
        String sql=" DELETE FROM dbo.NhomSanPham"+
                  " WHERE maNhomSP = ?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,maNSP);
                
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
    public boolean update (NhomSanPham nsp)throws Exception
    {
        
        String sql="UPDATE dbo.NhomSanPham"
                + " SET tenNhomSP = ?,ngayNhap = ?" + 
                " WHERE maNhomSP = ?";
        
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(3,nsp.getMaNhomSP());
                pstmt.setString(1,nsp.getTenNhomSP());
                pstmt.setString(2,nsp.getNgayNhap());
                

                
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
     public boolean insert (NhomSanPham nsp)throws Exception
    {
        
        String sql="INSERT INTO dbo.NhomSanPham(maNhomSP,tenNhomSP,ngayNhap)"+
                    " VALUES(?,?,?)";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                
                pstmt.setString(1,nsp.getMaNhomSP());
                pstmt.setString(2,nsp.getTenNhomSP());
                
                
                pstmt.setString(3,nsp.getNgayNhap());

               
                
                
                return pstmt.executeUpdate()>0;
        }
           
    }
}
