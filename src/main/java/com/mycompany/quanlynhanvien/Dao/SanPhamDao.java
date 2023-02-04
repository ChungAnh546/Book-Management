/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.Dao;


import com.mycompany.quanlynhanvien.Helpers.ConnectDB;
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
public class SanPhamDao {
    public List<SanPham> findAll ()throws Exception
    {
        String sql="select * from dbo.SanPham";
        
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
    private SanPham createSanPham(final ResultSet rs) throws NumberFormatException, SQLException {
        SanPham sp = new SanPham();
        sp.setMaSP(rs.getString("maSP"));
        sp.setTenSP(rs.getString("tenSP"));
        sp.setDonGia(rs.getFloat("donGia"));
        
        sp.setSoLuong(rs.getInt("soLuongTon"));
        NhomSanPham nsp = new NhomSanPham();
        nsp.setMaNhomSP(rs.getString("maNhomSP"));
        sp.setNhomSanPham(nsp);
        NhaCungCap ncc = new NhaCungCap();
        ncc.setMaNhaCC(rs.getString("maNhaCC"));
        sp.setNhaCungCap(ncc);
        TacGia tg = new TacGia();
        tg.setMaTacGia(rs.getString("maTacGia"));
        sp.setTacGia(tg);
        
        
        Blob blob = rs.getBlob("hinhSP");
        if(blob != null){sp.setHinhSP(blob.getBytes(1,(int) blob.length()));}
        
        return sp;
    }
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
    
    public String ghepChuoi(String Select, String ThuocTinh, String giaTriSS)
    {
        
        String sql = "select maSP from dbo.SanPham where maSP in ("+Select+") and "+ThuocTinh+" = N'"+giaTriSS+"'"; 
        return sql;
        
    }
    
    public List<SanPham> findDK (String maSP, String tenSP, String donGia, String maNSP, String maNCC, String maTG)throws Exception
    {
        String sql0,sql1,sql2,sql3,sql4,sql5;
        if(maSP.equals("")){sql0="SELECT maSP\n" +
                                            " FROM [dbo].[SanPham] ";}
        else{
            sql0="SELECT maSP\n" +
                                 " FROM [dbo].[SanPham] where maSP = N'"+maSP+"'";
        }
        
        
        if(tenSP.equals(""))
        {
            //ko co ten san pham chua xet don gia tro len
            sql1=sql0;
            if(donGia.equals(""))
            {
                //ko co don gia chua xet nhom sp tro len
                sql2=sql1;
                if(maNSP.equals(""))
                {
                    //ko co nhom san pham chu xet ncc tro len
                    sql3 = sql2;
                    if(maNCC.equals(""))
                    {
                        //ko co nha cung cap chua xet tac gia
                        sql4 = sql3;
                        if(maTG.equals(""))
                        {//ko co tac gia
                            sql5 = sql4;
                        }
                        else{
                            // co het
                                sql5 = ghepChuoi(sql4, "maTacGia", maTG);
                            }
                    }
                    else{
                        // co nha cung cap chua xet tac gia
                        sql4 = ghepChuoi(sql3, "maNhaCC", maNCC);
                        if(maTG.equals(""))
                        {
                            sql5 = sql4;
                        }
                        else{
                                sql5 = ghepChuoi(sql4, "maTacGia", maTG);
                            }
                        }
                }
                else{
                        //co nhom san pham chu xet ncc tro len
                        sql3 = ghepChuoi(sql2, "maNhomSP", maNSP);
                        if(maNCC.equals(""))
                    {
                        //ko co nha cung cap chua xet tac gia
                        sql4 = sql3;
                        if(maTG.equals(""))
                        {//ko co tac gia
                            sql5 = sql4;
                        }
                        else{
                            // co het
                                sql5 = ghepChuoi(sql4, "maTacGia", maTG);
                            }
                    }
                    else{
                        // co nha cung cap chua xet tac gia
                        sql4 = ghepChuoi(sql3, "maNhaCC", maNCC);
                        if(maTG.equals(""))
                        {
                            sql5 = sql4;
                        }
                        else{
                                sql5 = ghepChuoi(sql4, "maTacGia", maTG);
                            }
                        }
                    }
            }else{
                sql2 = ghepChuoi(sql1, "donGia",donGia );
                if(maNSP.equals(""))
                {
                    //ko co nhom san pham chu xet ncc tro len
                    sql3 = sql2;
                    if(maNCC.equals(""))
                    {
                        //ko co nha cung cap chua xet tac gia
                        sql4 = sql3;
                        if(maTG.equals(""))
                        {//ko co tac gia
                            sql5 = sql4;
                        }
                        else{
                            // co het
                                sql5 = ghepChuoi(sql4, "maTacGia", maTG);
                            }
                    }
                    else{
                        // co nha cung cap chua xet tac gia
                        sql4 = ghepChuoi(sql3, "maNhaCC", maNCC);
                        if(maTG.equals(""))
                        {
                            sql5 = sql4;
                        }
                        else{
                                sql5 = ghepChuoi(sql4, "maTacGia", maTG);
                            }
                        }
                }
                else{
                        //co nhom san pham chu xet ncc tro len
                        sql3 = ghepChuoi(sql2, "maNhomSP", maNSP);
                        if(maNCC.equals(""))
                    {
                        //ko co nha cung cap chua xet tac gia
                        sql4 = sql3;
                        if(maTG.equals(""))
                        {//ko co tac gia
                            sql5 = sql4;
                        }
                        else{
                            // co het
                                sql5 = ghepChuoi(sql4, "maTacGia", maTG);
                            }
                    }
                    else{
                        // co nha cung cap chua xet tac gia
                        sql4 = ghepChuoi(sql3, "maNhaCC", maNCC);
                        if(maTG.equals(""))
                        {
                            sql5 = sql4;
                        }
                        else{
                                sql5 = ghepChuoi(sql4, "maTacGia", maTG);
                            }
                        }
                    }
                 
            }
        }else{
            sql1=ghepChuoi(sql0, "tenSP", tenSP);
            if(donGia.equals(""))
            {
                //ko co don gia chua xet nhom sp tro len
                sql2=sql1;
                if(maNSP.equals(""))
                {
                    //ko co nhom san pham chu xet ncc tro len
                    sql3 = sql2;
                    if(maNCC.equals(""))
                    {
                        //ko co nha cung cap chua xet tac gia
                        sql4 = sql3;
                        if(maTG.equals(""))
                        {//ko co tac gia
                            sql5 = sql4;
                        }
                        else{
                            // co het
                                sql5 = ghepChuoi(sql4, "maTacGia", maTG);
                            }
                    }
                    else{
                        // co nha cung cap chua xet tac gia
                        sql4 = ghepChuoi(sql3, "maNhaCC", maNCC);
                        if(maTG.equals(""))
                        {
                            sql5 = sql4;
                        }
                        else{
                                sql5 = ghepChuoi(sql4, "maTacGia", maTG);
                            }
                        }
                }
                else{
                        //co nhom san pham chu xet ncc tro len
                        sql3 = ghepChuoi(sql2, "maNhomSP", maNSP);
                        if(maNCC.equals(""))
                    {
                        //ko co nha cung cap chua xet tac gia
                        sql4 = sql3;
                        if(maTG.equals(""))
                        {//ko co tac gia
                            sql5 = sql4;
                        }
                        else{
                            // co het
                                sql5 = ghepChuoi(sql4, "maTacGia", maTG);
                            }
                    }
                    else{
                        // co nha cung cap chua xet tac gia
                        sql4 = ghepChuoi(sql3, "maNhaCC", maNCC);
                        if(maTG.equals(""))
                        {
                            sql5 = sql4;
                        }
                        else{
                                sql5 = ghepChuoi(sql4, "maTacGia", maTG);
                            }
                        }
                    }
            }else{
                sql2 = ghepChuoi(sql1, "donGia",donGia );
                if(maNSP.equals(""))
                {
                    //ko co nhom san pham chu xet ncc tro len
                    sql3 = sql2;
                    if(maNCC.equals(""))
                    {
                        //ko co nha cung cap chua xet tac gia
                        sql4 = sql3;
                        if(maTG.equals(""))
                        {//ko co tac gia
                            sql5 = sql4;
                        }
                        else{
                            // co het
                                sql5 = ghepChuoi(sql4, "maTacGia", maTG);
                            }
                    }
                    else{
                        // co nha cung cap chua xet tac gia
                        sql4 = ghepChuoi(sql3, "maNhaCC", maNCC);
                        if(maTG.equals(""))
                        {
                            sql5 = sql4;
                        }
                        else{
                                sql5 = ghepChuoi(sql4, "maTacGia", maTG);
                            }
                        }
                }
                else{
                        //co nhom san pham chu xet ncc tro len
                        sql3 = ghepChuoi(sql2, "maNhomSP", maNSP);
                        if(maNCC.equals(""))
                    {
                        //ko co nha cung cap chua xet tac gia
                        sql4 = sql3;
                        if(maTG.equals(""))
                        {//ko co tac gia
                            sql5 = sql4;
                        }
                        else{
                            // co het
                                sql5 = ghepChuoi(sql4, "maTacGia", maTG);
                            }
                    }
                    else{
                        // co nha cung cap chua xet tac gia
                        sql4 = ghepChuoi(sql3, "maNhaCC", maNCC);
                        if(maTG.equals(""))
                        {
                            sql5 = sql4;
                        }
                        else{
                                sql5 = ghepChuoi(sql4, "maTacGia", maTG);
                            }
                        }
                    }
                 
            }
        }
        String sql="select * from dbo.SanPham where maSP in ("+sql5+")";
        
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
           
    }
    
      public SanPham find (String maSP)throws Exception
    {
       
      
        String sql="select * from dbo.SanPham where maSP = ?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,maSP);
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        SanPham sp = createSanPham(rs);
                        NhomSanPhamDao daoNSP = new NhomSanPhamDao();
                        sp.setNhomSanPham(daoNSP.find(sp.getNhomSanPham().getMaNhomSP()));
                        //
                        NhaCungCapDao daoNCC = new NhaCungCapDao();
                        sp.setNhaCungCap(daoNCC.find(sp.getNhaCungCap().getMaNhaCC()));
                        //
                        TacGiaDao daoTG = new TacGiaDao();
                        sp.setTacGia(daoTG.find(sp.getTacGia().getMaTacGia()));
                        //
                        return sp;
                    }
                    
                }return null;
        }
           
    }
      
      
      
      public boolean delete (String maSP)throws Exception
    {
       
      
        String sql=" DELETE FROM dbo.SanPham"+
                  " WHERE maSP = ?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,maSP);
                
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
    public boolean update (SanPham sp)throws Exception
    {
        
        String sql="UPDATE dbo.SanPham"
                + " SET tenSP = ?,soLuongTon = ?,donGia = ?,maNhomSP = ?,maNhaCC = ?,maTacGia = ?,hinhSP = ?" + 
                " WHERE maSP = ?";
        
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(8,sp.getMaSP());
                pstmt.setString(1,sp.getTenSP());
                pstmt.setInt(2,sp.getSoLuong());
                
                pstmt.setFloat(3,sp.getDonGia());
                
                pstmt.setString(4,sp.getNhomSanPham().getMaNhomSP());

                pstmt.setString(5,sp.getNhaCungCap().getMaNhaCC());
                
                pstmt.setString(6,sp.getTacGia().getMaTacGia());
                Blob hinh;
                if(sp.getHinhSP()!=null)
                {
                    hinh = new SerialBlob(sp.getHinhSP());
                    pstmt.setBlob(7,hinh);
                }
                else{
                       hinh = null;
                       pstmt.setBlob(7,hinh);
                    }
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
     public boolean insert (SanPham sp)throws Exception
    {
        
        String sql="INSERT INTO dbo.SanPham(maSP,tenSP,soLuongTon,donGia,maNhomSP,maNhaCC,maTacGia,hinhSP)"+
                    " VALUES(?,?,?,?,?,?,?,?)";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                
                pstmt.setString(1,sp.getMaSP());
                pstmt.setString(2,sp.getTenSP());
                pstmt.setInt(3,sp.getSoLuong());
                
                pstmt.setFloat(4,sp.getDonGia());
                
                pstmt.setString(5,sp.getNhomSanPham().getMaNhomSP());

                pstmt.setString(6,sp.getNhaCungCap().getMaNhaCC());
                
                pstmt.setString(7,sp.getTacGia().getMaTacGia());
                Blob hinh;
                if(sp.getHinhSP()!=null)
                {
                    hinh = new SerialBlob(sp.getHinhSP());
                    pstmt.setBlob(8,hinh);
                }
                else{
                       hinh = null;
                       pstmt.setBlob(8,hinh);
                    }
                return pstmt.executeUpdate()>0;
        }
           
    }
}
