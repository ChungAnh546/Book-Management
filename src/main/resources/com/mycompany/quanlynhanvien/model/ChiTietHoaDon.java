/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.model;

/**
 *
 * @author User
 */
public class ChiTietHoaDon {
    float thanhTien;
    float donGia;
    HoaDon HD;
    SanPham SP;
    int Soluong;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(float thanhTien, float donGia, HoaDon HD, SanPham SP, int Soluong) {
        this.thanhTien = thanhTien;
        this.donGia = donGia;
        this.HD = HD;
        this.SP = SP;
        this.Soluong = Soluong;
    }

    public float getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(float thanhTien) {
        this.thanhTien = thanhTien;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public HoaDon getHD() {
        return HD;
    }

    public void setHD(HoaDon HD) {
        this.HD = HD;
    }

    public SanPham getSP() {
        return SP;
    }

    public void setSP(SanPham SP) {
        this.SP = SP;
    }

    public int getSoluong() {
        return Soluong;
    }

    public void setSoluong(int Soluong) {
        this.Soluong = Soluong;
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{" + "thanhTien=" + thanhTien + ", donGia=" + donGia + ", HD=" + HD + ", SP=" + SP + ", Soluong=" + Soluong + '}';
    }

   
}
