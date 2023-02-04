/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.model;

import java.sql.Date;

/**
 *
 * @author User
 */
public class NhomSanPham {
    public String maNhomSP;
    public String tenNhomSP;
    public String ngayNhap;

    public NhomSanPham() {
    }

    public NhomSanPham(String maNhomSP, String tenNhomSP, String ngayNhap) {
        this.maNhomSP = maNhomSP;
        this.tenNhomSP = tenNhomSP;
        this.ngayNhap = ngayNhap;
    }

    public String getMaNhomSP() {
        return maNhomSP;
    }

    public void setMaNhomSP(String maNhomSP) {
        this.maNhomSP = maNhomSP;
    }

    public String getTenNhomSP() {
        return tenNhomSP;
    }

    public void setTenNhomSP(String tenNhomSP) {
        this.tenNhomSP = tenNhomSP;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    @Override
    public String toString() {
        return "NhomSanPham{" + "maNhomSP=" + maNhomSP + ", tenNhomSP=" + tenNhomSP + ", ngayNhap=" + ngayNhap + '}';
    }

    
    
}
