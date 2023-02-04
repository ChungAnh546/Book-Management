/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.model;

/**
 *
 * @author User
 */
public class NhaCungCap {
    public String MaNhaCC;
    public String TenNhaCC;
    public String DiaChi;
    public String sDT; 
    public String email; 

    @Override
    public String toString() {
        return "NhaCungCap{" + "MaNhaCC=" + MaNhaCC + ", TenNhaCC=" + TenNhaCC + ", DiaChi=" + DiaChi + ", sDT=" + sDT + ", email=" + email + '}';
    }

    public String getMaNhaCC() {
        return MaNhaCC;
    }

    public void setMaNhaCC(String MaNhaCC) {
        this.MaNhaCC = MaNhaCC;
    }

    public String getTenNhaCC() {
        return TenNhaCC;
    }

    public void setTenNhaCC(String TenNhaCC) {
        this.TenNhaCC = TenNhaCC;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getsDT() {
        return sDT;
    }

    public void setsDT(String sDT) {
        this.sDT = sDT;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public NhaCungCap(String MaNhaCC, String TenNhaCC, String DiaChi, String sDT, String email) {
        this.MaNhaCC = MaNhaCC;
        this.TenNhaCC = TenNhaCC;
        this.DiaChi = DiaChi;
        this.sDT = sDT;
        this.email = email;
    }

    public NhaCungCap() {
    }
      
      
}
