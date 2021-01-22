package com.example.rr_japung.model;

import com.google.firebase.database.Exclude;

public class Upload {
    private String mMerk;
    private String mTipeKendaraan;
    private String mNoKendaraan;
    private String mTahunProduksi;//
    private String mUkuranMesin;//
    private String mStatusKendaraan;//
    private String mHargaSewa;
    private String mTransmisi;//
    private String mMesin;//

    private String mImageUrl;
    private String mKey;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String mMerk, String mTipeKendaraan, String mNoKendaraan, String mTahunProduksi, String mUkuranMesin, String mStatusKendaraan, String mHargaSewa, String mTransmisi, String mMesin, String mImageUrl) {
        this.mMerk = mMerk;
        this.mTipeKendaraan = mTipeKendaraan;
        this.mNoKendaraan = mNoKendaraan;
        this.mTahunProduksi = mTahunProduksi;
        this.mUkuranMesin = mUkuranMesin;
        this.mStatusKendaraan = mStatusKendaraan;
        this.mHargaSewa = mHargaSewa;
        this.mTransmisi = mTransmisi;
        this.mMesin = mMesin;
        this.mImageUrl = mImageUrl;
    }



    public String getmMerk() {
        return mMerk;
    }

    public void setmMerk(String mMerk) {
        this.mMerk = mMerk;
    }

    public String getmTipeKendaraan() {
        return mTipeKendaraan;
    }

    public void setmTipeKendaraan(String mTipeKendaraan) {
        this.mTipeKendaraan = mTipeKendaraan;
    }

    public String getmNoKendaraan() {
        return mNoKendaraan;
    }

    public void setmNoKendaraan(String mNoKendaraan) {
        this.mNoKendaraan = mNoKendaraan;
    }

    public String getmTahunProduksi() {
        return mTahunProduksi;
    }

    public void setmTahunProduksi(String mTahunProduksi) {
        this.mTahunProduksi = mTahunProduksi;
    }

    public String getmUkuranMesin() {
        return mUkuranMesin;
    }

    public void setmUkuranMesin(String mUkuranMesin) {
        this.mUkuranMesin = mUkuranMesin;
    }

    public String getmStatusKendaraan() {
        return mStatusKendaraan;
    }

    public void setmStatusKendaraan(String mStatusKendaraan) {
        this.mStatusKendaraan = mStatusKendaraan;
    }

    public String getmHargaSewa() {
        return mHargaSewa;
    }

    public void setmHargaSewa(String mHargaSewa) {
        this.mHargaSewa = mHargaSewa;
    }

    public String getmTransmisi() {
        return mTransmisi;
    }

    public void setmTransmisi(String mTransmisi) {
        this.mTransmisi = mTransmisi;
    }

    public String getmMesin() {
        return mMesin;
    }

    public void setmMesin(String mMesin) {
        this.mMesin = mMesin;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}