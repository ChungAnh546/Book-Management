/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.model;

/**
 *
 * @author User
 */
public class SanPham {
    public String MaSP;
    public String TenSP;
    public int soLuong;
    public float donGia;
    public NhomSanPham nhomSanPham;
    public NhaCungCap nhaCungCap;
    public TacGia tacGia;
    public byte[] hinhSP;

    public SanPham() {
    }

    public SanPham(String MaSP, String TenSP, int soLuong, float donGia, NhomSanPham nhomSanPham, NhaCungCap nhaCungCap, TacGia tacGia, byte[] hinhSP) {
        this.MaSP = MaSP;
        this.TenSP = TenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.nhomSanPham = nhomSanPham;
        this.nhaCungCap = nhaCungCap;
        this.tacGia = tacGia;
        this.hinhSP = hinhSP;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String MaSP) {
        this.MaSP = MaSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String TenSP) {
        this.TenSP = TenSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public NhomSanPham getNhomSanPham() {
        return nhomSanPham;
    }

    public void setNhomSanPham(NhomSanPham nhomSanPham) {
        this.nhomSanPham = nhomSanPham;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public TacGia getTacGia() {
        return tacGia;
    }

    public void setTacGia(TacGia tacGia) {
        this.tacGia = tacGia;
    }

    public byte[] getHinhSP() {
        return hinhSP;
    }

    public void setHinhSP(byte[] hinhSP) {
        this.hinhSP = hinhSP;
    }

    @Override
    public String toString() {
        return "SanPham{" + "MaSP=" + MaSP + ", TenSP=" + TenSP + ", soLuong=" + soLuong + ", donGia=" + donGia + ", nhomSanPham=" + nhomSanPham + ", nhaCungCap=" + nhaCungCap + ", tacGia=" + tacGia + ", hinhSP=" + hinhSP + '}';
    }
   
    
}
