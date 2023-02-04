/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.Dao;

import com.mycompany.quanlynhanvien.Helpers.ConnectDB;
import com.mycompany.quanlynhanvien.model.NhaCungCap;
import com.mycompany.quanlynhanvien.model.NhaCungCap;
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
public class NhaCungCapDao {
    public NhaCungCap findMaNhaCCByTen (String tenNhaCC)throws Exception
    {
       
      String reg = "^[N][C][C][0-9]{3}$";
       if(!tenNhaCC.matches(reg)){
        String sql="select * from dbo.NhaCungCap where tenNhaCC=?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,tenNhaCC);
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        NhaCungCap ncc = createNhaCC(rs);
                        
                        return ncc;
                    }
                    
                }return null;
        }}
       NhaCungCap ncc = new NhaCungCap();
       ncc.setMaNhaCC(tenNhaCC);
       return ncc;
           
    }
    public String ghepChuoi(String Select, String ThuocTinh, String giaTriSS)
    {
        
        String sql = "select maNhaCC from dbo.NhaCungCap where maNhaCC in ("+Select+") and "+ThuocTinh+" = N'"+giaTriSS+"'"; 
        return sql;
        
    }
    
    public List<NhaCungCap> findDK (String maNCC, String sDT)throws Exception
    {
        String sql0,sql1;
        if(maNCC.equals("")){sql0="SELECT maNhaCC\n" +
                                            " FROM [dbo].[NhaCungCap] ";}
        else{
            sql0="SELECT maNhaCC\n" +
                                 " FROM [dbo].[NhaCungCap] where maNhaCC = N'"+maNCC+"'";
        }
        
        
        if(sDT.equals(""))
        {
            //ko co ten san pham chua xet don gia tro len
            sql1=sql0;
         }  else{sql1 = ghepChuoi(sql0, "sDT", sDT);} 
              
                 
        
        String sql="select * from dbo.NhaCungCap where maNhaCC in ("+sql1+")";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    List<NhaCungCap> list = new ArrayList<>();
                    while(rs.next())
                    {
                        NhaCungCap ncc = createNhaCC(rs);
                        list.add(ncc);
                        
                    }return list;
                    
                }
        }
    }
    private NhaCungCap createNhaCC(final ResultSet rs) throws NumberFormatException, SQLException {
        NhaCungCap ncc = new NhaCungCap();
        ncc.setMaNhaCC(rs.getString("maNhaCC"));
        ncc.setTenNhaCC(rs.getString("tenNhaCC"));
        ncc.setDiaChi(rs.getString("diaChi"));
        
        ncc.setsDT(rs.getString("sDT"));
        
        ncc.setEmail(rs.getString("email"));
        
        
        return ncc;
    }
    public List<String> findTenNhaCC ()throws Exception
    {
       
       
        String sql="SELECT tenNhaCC FROM NhaCungCap ";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                
                 try(ResultSet rs =  pstmt.executeQuery();)
                {
                    List<String> list = new ArrayList<>();
                    while(rs.next())
                    {
                        String tenNhaCC = rs.getString("tenNhaCC");
                        list.add(tenNhaCC);
                        
                    }return list;
                    
                }
        }
           
    }
    
    private NhaCungCap createNhaCungCap(ResultSet rs) throws SQLException {
        NhaCungCap ncc = new NhaCungCap();
        
        ncc.setMaNhaCC(rs.getString("maNhaCC"));
        ncc.setTenNhaCC(rs.getString("tenNhaCC")); 
        ncc.setDiaChi(rs.getString("DiaChi"));
        ncc.setsDT(rs.getString("sDT"));
        ncc.setEmail(rs.getString("email"));
        return ncc;//To change body of generated methods, choose Tools | Templates.
    }
    public List<NhaCungCap> findAll ()throws Exception
    {
        String sql="select * from dbo.NhaCungCap";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    List<NhaCungCap> list = new ArrayList<>();
                    while(rs.next())
                    {
                        NhaCungCap ncc = createNhaCungCap(rs);
                        list.add(ncc);
                        
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
      public NhaCungCap find (String maNCC)throws Exception
    {
       
      
        String sql="select * from dbo.NhaCungCap where maNhaCC = ?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,maNCC);
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        NhaCungCap ncc = createNhaCungCap(rs);
                        
                        return ncc;
                    }
                    
                }return null;
        }
           
    }
      
      
      
      public boolean delete (String maNCC)throws Exception
    {
       
      
        String sql=" DELETE FROM dbo.NhaCungCap"+
                  " WHERE maNhaCC = ?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,maNCC);
                
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
    public boolean update (NhaCungCap ncc)throws Exception
    {
        
        String sql="UPDATE dbo.NhaCungCap"
                + " SET tenNhaCC = ?,DiaChi = ?,sDT = ? ,email = ?" + 
                " WHERE maNhaCC = ?";
        
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(5,ncc.getMaNhaCC());
                pstmt.setString(1,ncc.getTenNhaCC());
                pstmt.setString(2,ncc.getDiaChi());
                pstmt.setString(3,ncc.getsDT());
                pstmt.setString(4,ncc.getEmail());
                
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
     public boolean insert (NhaCungCap ncc)throws Exception
    {
        
        String sql="INSERT INTO dbo.NhaCungCap(maNhaCC,tenNhaCC,DiaChi,sDT,email)"+
                    " VALUES(?,?,?,?,?)";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                
                pstmt.setString(1,ncc.getMaNhaCC());
                pstmt.setString(2,ncc.getTenNhaCC());
                
                
                pstmt.setString(3,ncc.getDiaChi());

                pstmt.setString(4,ncc.getsDT());
                pstmt.setString(5,ncc.getEmail());
                
                
                return pstmt.executeUpdate()>0;
        }
           
    }
}
