package com.example.sensus.model;

public class Penduduk {
    private int id;
    private String name, pekerjaan;
    private int nik;
    private String kecamatan;

    public Penduduk(int id, String name, String pekerjaan, int nik, String kecamatan) {
        this.id = id;
        this.name = name;
        this.pekerjaan = pekerjaan;
        this.nik = nik;
        this.kecamatan = kecamatan;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public int getNik() {
        return nik;
    }

    public String getKecamatan() {
        return kecamatan;
    }
}
