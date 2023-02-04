/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.model;

/**
 *
 * @author User
 */
public class TacGia {
    public String MaTacGia;
    public String TenTacGia;
    public String ngaySinh;
    public String queQuan;

    @Override
    public String toString() {
        return "TacGia{" + "MaTacGia=" + MaTacGia + ", TenTacGia=" + TenTacGia + ", ngaySinh=" + ngaySinh + ", queQuan=" + queQuan + '}';
    }

    public TacGia() {
    }

    public TacGia(String MaTacGia, String TenTacGia, String ngaySinh, String queQuan) {
        this.MaTacGia = MaTacGia;
        this.TenTacGia = TenTacGia;
        this.ngaySinh = ngaySinh;
        this.queQuan = queQuan;
    }

    public String getMaTacGia() {
        return MaTacGia;
    }

    public void setMaTacGia(String MaTacGia) {
        this.MaTacGia = MaTacGia;
    }

    public String getTenTacGia() {
        return TenTacGia;
    }

    public void setTenTacGia(String TenTacGia) {
        this.TenTacGia = TenTacGia;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }
    
    
}
