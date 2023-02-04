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
public class HoaDon {
    String maHD;
    Date ngayLap;
    NhanVien nhanVien;
    KhachHang khachHang;
    float TongTien;
    String ghiChu;

    public HoaDon() {
    }

    public HoaDon(String maHD, Date ngayLap, NhanVien nhanVien, KhachHang khachHang, float TongTien, String ghiChu) {
        this.maHD = maHD;
        this.ngayLap = ngayLap;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.TongTien = TongTien;
        this.ghiChu = ghiChu;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public float getTongTien() {
        return TongTien;
    }

    public void setTongTien(float TongTien) {
        this.TongTien = TongTien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public String toString() {
        return "HoaDon{" + "maHD=" + maHD + ", ngayLap=" + ngayLap + ", nhanVien=" + nhanVien + ", khachHang=" + khachHang + ", TongTien=" + TongTien + ", ghiChu=" + ghiChu + '}';
    }

    
    
}
