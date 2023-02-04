/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.Dao;

import com.mycompany.quanlynhanvien.Helpers.ConnectDB;
import com.mycompany.quanlynhanvien.model.TaiKhoan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class TaiKhoanDao {
    private ArrayList<TaiKhoan> dstk = new ArrayList<TaiKhoan>();
    public boolean themTaiKhoan(String maTK, String tenTK, String matKhau) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("Insert into TaiKhoan values(?,?,?)");
			stmt.setString(1, maTK);
			stmt.setString(2, tenTK);
			stmt.setString(3, matKhau);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}
     public boolean insert (TaiKhoan tk)throws Exception
    {
        
        String sql="INSERT INTO dbo.TaiKhoan(maTK,tenTK,matKhau,tinhTrang)"+
                    " VALUES(?,?,?,?)";
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                
                pstmt.setString(1,tk.getMaTK());
                pstmt.setString(2,tk.getTenTK());
                pstmt.setString(3,tk.getMatKhau());
                
                pstmt.setString(4,tk.getTinhTrang());
                
                
                
                return pstmt.executeUpdate()>0;
        }
           
    }

	public String tuDongLayMa() {
		String maTK = "";
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			String sql = "declare @ma char(5),@max int\r\n"
					+ "set @ma = RIGHT((select MAX(maTK) from TaiKhoan),3)\r\n"
					+ "set @max = CAST(@ma as int) + 1\r\n" + "set @ma = 'TK' + cast(@max as char(3))\r\n"
					+ "select @ma";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String ma = rs.getString(1);
				maTK = ma;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return maTK;
	}

	public boolean doiMatKhau(String maTK, String matKhau) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update TaiKhoan set MatKhau = ? where IDTaiKhoan like ?");
			stmt.setString(2, maTK);
			stmt.setString(1, matKhau);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}


    public ArrayList<TaiKhoan> docTuBang() {
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			String sql = "select t.IDTaiKhoan , TenTK , MatKhau\r\n" + "from TaiKhoan t join NhanVien n\r\n"
					+ "on t.IDTaiKhoan = n.IDTaiKhoan\r\n" + "where n.TinhTrang = 1";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String matk = rs.getString(1);
				String tentk = rs.getString(2);
				String matkhau = rs.getString(3);
				TaiKhoan tk = new TaiKhoan(matk, tentk, matkhau);
				dstk.add(tk);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dstk;
	}
    public TaiKhoan find (String tenTK)throws Exception
    {
       
      
        String sql="select top(1) * from dbo.TaiKhoan where tenTK=?"+ " COLLATE SQL_Latin1_General_CP1_CS_AS";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,tenTK);
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        TaiKhoan tk = createTaiKhoan(rs);
                        return tk;
                    }
                    
                }return null;
        }
           
    }
    public TaiKhoan checkLogin(String tenDN, String matKhau) throws SQLException
    {
         String sql="select * from dbo.TaiKhoan where tenTK=? and matKhau = ?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,tenDN);
                pstmt.setString(2,matKhau);
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        TaiKhoan tk = createTaiKhoan(rs);
                        return tk;
                    }
                    
                }return null;
        }
    }
     public String maTK(String SDT) throws SQLException
    {
         String sql="select * from dbo.NhanVien where sDT = ?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,SDT);
               
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        String maTK = rs.getString("maTK");
                        return maTK;
                    }
                    
                }return null;
        }
    }
       
     public TaiKhoan LayPass(String SDT) throws SQLException
    {
         String sql="select * from dbo.TaiKhoan where maTK = ?";
       
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(1,maTK(SDT));
                
                
                try(ResultSet rs =  pstmt.executeQuery();)
                {
                    if(rs.next())
                    {
                        TaiKhoan tk = createTaiKhoan(rs);
                        return tk;
                    }
                    
                }return null;
        }
    }    
        public boolean update (TaiKhoan tk)throws Exception
    {
        
        String sql="UPDATE dbo.TaiKhoan"
                + " SET matKhau = ?" + 
                " WHERE maTK= ?";
        
        
        try(
                Connection con = ConnectDB.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                
            ){
            
                pstmt.setString(2,tk.getMaTK());
                pstmt.setString(1,tk.getMatKhau());
                
                
                return pstmt.executeUpdate()>0;
        }
           
    }
    private TaiKhoan createTaiKhoan(final ResultSet rs) throws NumberFormatException, SQLException {
        TaiKhoan tk = new TaiKhoan();
        tk.setMaTK(rs.getString("maTK"));
        tk.setMatKhau(rs.getString("matKhau"));
        tk.setTenTK(rs.getString("tenTK"));
        tk.setTinhTrang(rs.getString("tinhTrang"));
        return tk;
    }
}
