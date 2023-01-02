package com.haris.food_app;

public class produk {
    public String id;
    public String title;
    public String deskripsi;
    public String harga;
    public String stock;
    public String url_gambar;

    public produk() {

    }

    public produk(String id,String title, String deskripsi, String harga, String stock, String url_gambar) {
        this.id = id;
        this.title = title;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.stock = stock;
        this.url_gambar = url_gambar;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
    public String getUrlGambar() {
        return url_gambar;
    }

    public void setUrlGambar(String url_gambar) {
        this.url_gambar = url_gambar;
    }
}