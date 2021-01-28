package com.example.rr_japung.model;

import com.google.firebase.database.Exclude;

public class Transaksi {
    private String KeyKendaraan;///
    private String MerkKendaraan;
    private String TipeKendaraan;
    private String NoKendaraan;
    private String TahunProduksiKendaraan;
    private String UkuranMesinKendaraan;
    private String StatusKendaraan;///
    private String HargaSewaKendaraan;
    private String TransmisiKendaraan;
    private String MesinKendaraan;
    private String FotoKendaraanURL;
    private String TotalHargaSewa;
    private String TanggalPesan;
    private String TanggalKembali;
    private String TanggalTransaksi;
    private String NoKTPSopir;///
    private String NamaSopir;
    private String NoTeleponSopir;
    private String AlamatSopir;///
    private String HargaPaketSopir;
    private String NoKTPPemesan;
    private String NamaPemesan;
    private String NoTeleponPemesan;
    private String AlamatPemesan;
    private String IdPembayaran;
    private String StatusPembayaran;

    private String Key;///

    public Transaksi(){
        //PENTING AJA DI HAPUS .........
    }

    public Transaksi(String merkKendaraan, String tipeKendaraan, String noKendaraan, String tahunProduksiKendaraan, String ukuranMesinKendaraan, String hargaSewaKendaraan, String transmisiKendaraan, String mesinKendaraan, String fotoKendaraanURL, String totalHargaSewa, String tanggalPesan, String tanggalKembali, String tanggalTransaksi, String namaSopir, String noTeleponSopir, String hargaPaketSopir, String noKTPPemesan, String namaPemesan, String noTeleponPemesan, String alamatPemesan, String idPembayaran, String statusPembayaran) {
        MerkKendaraan = merkKendaraan;
        TipeKendaraan = tipeKendaraan;
        NoKendaraan = noKendaraan;
        TahunProduksiKendaraan = tahunProduksiKendaraan;
        UkuranMesinKendaraan = ukuranMesinKendaraan;
        HargaSewaKendaraan = hargaSewaKendaraan;
        TransmisiKendaraan = transmisiKendaraan;
        MesinKendaraan = mesinKendaraan;
        FotoKendaraanURL = fotoKendaraanURL;
        TotalHargaSewa = totalHargaSewa;
        TanggalPesan = tanggalPesan;
        TanggalKembali = tanggalKembali;
        TanggalTransaksi = tanggalTransaksi;
        NamaSopir = namaSopir;
        NoTeleponSopir = noTeleponSopir;
        HargaPaketSopir = hargaPaketSopir;
        NoKTPPemesan = noKTPPemesan;
        NamaPemesan = namaPemesan;
        NoTeleponPemesan = noTeleponPemesan;
        AlamatPemesan = alamatPemesan;
        IdPembayaran = idPembayaran;
        StatusPembayaran = statusPembayaran;
    }

    //    public Transaksi(String merkKendaraan, String tipeKendaraan, String noKendaraan, String tahunProduksiKendaraan, String ukuranMesinKendaraan, String hargaSewaKendaraan, String transmisiKendaraan, String mesinKendaraan, String fotoKendaraanURL, String totalHargaSewa, String tanggalPesan, String tanggalKembali, String tanggalTransaksi, String noKTPPemesan, String namaPemesan, String noTeleponPemesan, String alamatPemesan, String idPembayaran) {
//        MerkKendaraan = merkKendaraan;
//        TipeKendaraan = tipeKendaraan;
//        NoKendaraan = noKendaraan;
//        TahunProduksiKendaraan = tahunProduksiKendaraan;
//        UkuranMesinKendaraan = ukuranMesinKendaraan;
//        HargaSewaKendaraan = hargaSewaKendaraan;
//        TransmisiKendaraan = transmisiKendaraan;
//        MesinKendaraan = mesinKendaraan;
//        FotoKendaraanURL = fotoKendaraanURL;
//        TotalHargaSewa = totalHargaSewa;
//        TanggalPesan = tanggalPesan;
//        TanggalKembali = tanggalKembali;
//        TanggalTransaksi = tanggalTransaksi;
//        NoKTPPemesan = noKTPPemesan;
//        NamaPemesan = namaPemesan;
//        NoTeleponPemesan = noTeleponPemesan;
//        AlamatPemesan = alamatPemesan;
//        IdPembayaran = idPembayaran;
//    }

    public Transaksi(String merkKendaraan, String tipeKendaraan, String noKendaraan, String tahunProduksiKendaraan, String ukuranMesinKendaraan, String hargaSewaKendaraan, String transmisiKendaraan, String mesinKendaraan, String fotoKendaraanURL, String totalHargaSewa, String tanggalPesan, String tanggalKembali, String tanggalTransaksi, String noKTPPemesan, String namaPemesan, String noTeleponPemesan, String alamatPemesan, String idPembayaran, String statusPembayaran) {
        MerkKendaraan = merkKendaraan;
        TipeKendaraan = tipeKendaraan;
        NoKendaraan = noKendaraan;
        TahunProduksiKendaraan = tahunProduksiKendaraan;
        UkuranMesinKendaraan = ukuranMesinKendaraan;
        HargaSewaKendaraan = hargaSewaKendaraan;
        TransmisiKendaraan = transmisiKendaraan;
        MesinKendaraan = mesinKendaraan;
        FotoKendaraanURL = fotoKendaraanURL;
        TotalHargaSewa = totalHargaSewa;
        TanggalPesan = tanggalPesan;
        TanggalKembali = tanggalKembali;
        TanggalTransaksi = tanggalTransaksi;
        NoKTPPemesan = noKTPPemesan;
        NamaPemesan = namaPemesan;
        NoTeleponPemesan = noTeleponPemesan;
        AlamatPemesan = alamatPemesan;
        IdPembayaran = idPembayaran;
        StatusPembayaran = statusPembayaran;
    }

    public String getKeyKendaraan() {
        return KeyKendaraan;
    }

    public void setKeyKendaraan(String keyKendaraan) {
        KeyKendaraan = keyKendaraan;
    }

    public String getMerkKendaraan() {
        return MerkKendaraan;
    }

    public void setMerkKendaraan(String merkKendaraan) {
        MerkKendaraan = merkKendaraan;
    }

    public String getTipeKendaraan() {
        return TipeKendaraan;
    }

    public void setTipeKendaraan(String tipeKendaraan) {
        TipeKendaraan = tipeKendaraan;
    }

    public String getNoKendaraan() {
        return NoKendaraan;
    }

    public void setNoKendaraan(String noKendaraan) {
        NoKendaraan = noKendaraan;
    }

    public String getTahunProduksiKendaraan() {
        return TahunProduksiKendaraan;
    }

    public void setTahunProduksiKendaraan(String tahunProduksiKendaraan) {
        TahunProduksiKendaraan = tahunProduksiKendaraan;
    }

    public String getUkuranMesinKendaraan() {
        return UkuranMesinKendaraan;
    }

    public void setUkuranMesinKendaraan(String ukuranMesinKendaraan) {
        UkuranMesinKendaraan = ukuranMesinKendaraan;
    }

    public String getStatusKendaraan() {
        return StatusKendaraan;
    }

    public void setStatusKendaraan(String statusKendaraan) {
        StatusKendaraan = statusKendaraan;
    }

    public String getHargaSewaKendaraan() {
        return HargaSewaKendaraan;
    }

    public void setHargaSewaKendaraan(String hargaSewaKendaraan) {
        HargaSewaKendaraan = hargaSewaKendaraan;
    }

    public String getTransmisiKendaraan() {
        return TransmisiKendaraan;
    }

    public void setTransmisiKendaraan(String transmisiKendaraan) {
        TransmisiKendaraan = transmisiKendaraan;
    }

    public String getMesinKendaraan() {
        return MesinKendaraan;
    }

    public void setMesinKendaraan(String mesinKendaraan) {
        MesinKendaraan = mesinKendaraan;
    }

    public String getFotoKendaraanURL() {
        return FotoKendaraanURL;
    }

    public void setFotoKendaraanURL(String fotoKendaraanURL) {
        FotoKendaraanURL = fotoKendaraanURL;
    }

    public String getTotalHargaSewa() {
        return TotalHargaSewa;
    }

    public void setTotalHargaSewa(String totalHargaSewa) {
        TotalHargaSewa = totalHargaSewa;
    }

    public String getTanggalPesan() {
        return TanggalPesan;
    }

    public void setTanggalPesan(String tanggalPesan) {
        TanggalPesan = tanggalPesan;
    }

    public String getTanggalKembali() {
        return TanggalKembali;
    }

    public void setTanggalKembali(String tanggalKembali) {
        TanggalKembali = tanggalKembali;
    }

    public String getTanggalTransaksi() {
        return TanggalTransaksi;
    }

    public void setTanggalTransaksi(String tanggalTransaksi) {
        TanggalTransaksi = tanggalTransaksi;
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

    public String getHargaPaketSopir() {
        return HargaPaketSopir;
    }

    public void setHargaPaketSopir(String hargaPaketSopir) {
        HargaPaketSopir = hargaPaketSopir;
    }

    public String getNoKTPPemesan() {
        return NoKTPPemesan;
    }

    public void setNoKTPPemesan(String noKTPPemesan) {
        NoKTPPemesan = noKTPPemesan;
    }

    public String getNamaPemesan() {
        return NamaPemesan;
    }

    public void setNamaPemesan(String namaPemesan) {
        NamaPemesan = namaPemesan;
    }

    public String getNoTeleponPemesan() {
        return NoTeleponPemesan;
    }

    public void setNoTeleponPemesan(String noTeleponPemesan) {
        NoTeleponPemesan = noTeleponPemesan;
    }

    public String getAlamatPemesan() {
        return AlamatPemesan;
    }

    public void setAlamatPemesan(String alamatPemesan) {
        AlamatPemesan = alamatPemesan;
    }

    public String getIdPembayaran() {
        return IdPembayaran;
    }

    public void setIdPembayaran(String idPembayaran) {
        IdPembayaran = idPembayaran;
    }

    public String getStatusPembayaran() {
        return StatusPembayaran;
    }

    public void setStatusPembayaran(String statusPembayaran) {
        StatusPembayaran = statusPembayaran;
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
