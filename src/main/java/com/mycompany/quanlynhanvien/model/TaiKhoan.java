/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.model;

/**
 *
 * @author User
 */
public class TaiKhoan {
    public String maTK;
    public String tenTK;
    public String matKhau;
    public String tinhTrang;

    public TaiKhoan() {
    }

    public TaiKhoan(String maTK, String tenTK, String matKhau, String tinhTrang) {
        this.maTK = maTK;
        this.tenTK = tenTK;
        this.matKhau = matKhau;
        this.tinhTrang = tinhTrang;
    }

    public TaiKhoan(String matk, String tentk, String matkhau) {
         this.maTK = matk;
        this.tenTK = tentk;
        this.matKhau = matkhau;//To change body of generated methods, choose Tools | Templates.
    }

    public String getMaTK() {
        return maTK;
    }

    public void setMaTK(String maTK) {
        this.maTK = maTK;
    }

    public String getTenTK() {
        return tenTK;
    }

    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" + "maTK=" + maTK + ", tenTK=" + tenTK + ", matKhau=" + matKhau + ", tinhTrang=" + tinhTrang + '}';
    }

    
   
    
}
