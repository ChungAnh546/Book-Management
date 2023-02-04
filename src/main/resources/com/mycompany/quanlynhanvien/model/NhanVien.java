/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.model;



/**
 *
 * @author User
 */
public class NhanVien {
        public String maNhanVien;
	public String tenNhanVien;
	public String chucVu;
	public String  gioiTinh;
	public String ngaySinh;
        public String tinhTrang ;
	public String sDT ;
        public String maTK;
        public byte[] hinh;

    @Override
    public String toString() {
        return "NhanVien{" + "maNhanVien=" + maNhanVien + ", tenNhanVien=" + tenNhanVien + ", chucVu=" + chucVu + ", gioiTinh=" + gioiTinh + ", ngaySinh=" + ngaySinh + ", tinhTrang=" + tinhTrang + ", sDT=" + sDT + ", maTK=" + maTK + ", hinh=" + hinh + '}';
    }

    public NhanVien() {
    }

    public NhanVien(String maNhanVien, String tenNhanVien, String chucVu, String gioiTinh, String ngaySinh, String tinhTrang, String sDT, String maTK, byte[] hinh) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.chucVu = chucVu;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.tinhTrang = tinhTrang;
        this.sDT = sDT;
        this.maTK = maTK;
        this.hinh = hinh;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getsDT() {
        return sDT;
    }

    public void setsDT(String sDT) {
        this.sDT = sDT;
    }

    public String getMaTK() {
        return maTK;
    }

    public void setMaTK(String maTK) {
        this.maTK = maTK;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }
   
        
        
}
