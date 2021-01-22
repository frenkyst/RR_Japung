package com.example.rr_japung.model;

import com.google.firebase.database.Exclude;

public class DataSopir {
    private String NoKTPSopir;
    private String NamaSopir;
    private String NoTeleponSopir;
    private String AlamatSopir;
    private String StatusSopir;

    private String Key;

    public DataSopir(){
        //Penting OJO DI HAPUS SAT!!!!!!!!!!
    }

    public DataSopir(String noKTPSopir, String namaSopir, String noTeleponSopir, String alamatSopir, String statusSopir) {
        NoKTPSopir = noKTPSopir;
        NamaSopir = namaSopir;
        NoTeleponSopir = noTeleponSopir;
        AlamatSopir = alamatSopir;
        StatusSopir = statusSopir;
    }


    public String getNoKTPSopir() {
        return NoKTPSopir;
    }

    public void setNoKTPSopir(String noKTPSopir) {
        NoKTPSopir = noKTPSopir;
    }

    public String getNamaSopir() {
        return NamaSopir;
    }

    public void setNamaSopir(String namaSopir) {
        NamaSopir = namaSopir;
    }

    public String getNoTeleponSopir() {
        return NoTeleponSopir;
    }

    public void setNoTeleponSopir(String noTeleponSopir) {
        NoTeleponSopir = noTeleponSopir;
    }

    public String getAlamatSopir() {
        return AlamatSopir;
    }

    public void setAlamatSopir(String alamatSopir) {
        AlamatSopir = alamatSopir;
    }

    public String getStatusSopir() {
        return StatusSopir;
    }

    public void setStatusSopir(String statusSopir) {
        StatusSopir = statusSopir;
    }

    @Exclude
    public String getKey() {
        return Key;
    }

    @Exclude
    public void setKey(String key) {
        Key = key;
    }
}
