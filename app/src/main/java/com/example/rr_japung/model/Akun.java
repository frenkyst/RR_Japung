package com.example.rr_japung.model;

import com.google.firebase.database.Exclude;

public class Akun {
    private String UidAkun;
    private String EmailAkun;
    private String NamaAkun;
    private String NoKTPAkun;
    private String NoTeleponAkun;
    private String AlamatAkun;
    private String PhotoAkun;
    private String StatusAkun;
    
    private String Key;
    
    public Akun(){
        //OJO di hapus Pening bat........
    }

    public Akun(String uidAkun, String emailAkun, String namaAkun, String noKTPAkun, String noTeleponAkun, String alamatAkun, String photoAkun, String statusAkun) {
        UidAkun = uidAkun;
        EmailAkun = emailAkun;
        NamaAkun = namaAkun;
        NoKTPAkun = noKTPAkun;
        NoTeleponAkun = noTeleponAkun;
        AlamatAkun = alamatAkun;
        PhotoAkun = photoAkun;
        StatusAkun = statusAkun;
    }

    public Akun(String uidAkun, String emailAkun, String namaAkun, String photoAkun, String statusAkun) {
        UidAkun = uidAkun;
        EmailAkun = emailAkun;
        NamaAkun = namaAkun;
        PhotoAkun = photoAkun;
        StatusAkun = statusAkun;
    }

    public String getUidAkun() {
        return UidAkun;
    }

    public void setUidAkun(String uidAkun) {
        UidAkun = uidAkun;
    }

    public String getEmailAkun() {
        return EmailAkun;
    }

    public void setEmailAkun(String emailAkun) {
        EmailAkun = emailAkun;
    }

    public String getNamaAkun() {
        return NamaAkun;
    }

    public void setNamaAkun(String namaAkun) {
        NamaAkun = namaAkun;
    }

    public String getNoKTPAkun() {
        return NoKTPAkun;
    }

    public void setNoKTPAkun(String noKTPAkun) {
        NoKTPAkun = noKTPAkun;
    }

    public String getNoTeleponAkun() {
        return NoTeleponAkun;
    }

    public void setNoTeleponAkun(String noTeleponAkun) {
        NoTeleponAkun = noTeleponAkun;
    }

    public String getAlamatAkun() {
        return AlamatAkun;
    }

    public void setAlamatAkun(String alamatAkun) {
        AlamatAkun = alamatAkun;
    }

    public String getPhotoAkun() {
        return PhotoAkun;
    }

    public void setPhotoAkun(String photoAkun) {
        PhotoAkun = photoAkun;
    }

    public String getStatusAkun() {
        return StatusAkun;
    }

    public void setStatusAkun(String statusAkun) {
        StatusAkun = statusAkun;
    }

    @Exclude
    public String getKey() {
        return Key;
    }

    @Exclude
    public void setKey(String key) {
        Key = key;
    }

    @Override
    public String toString() {
        return  " "+UidAkun+"\n" +
                " "+EmailAkun+"\n" +
                " "+NamaAkun+"\n" +
                " "+NoKTPAkun+"\n" +
                " "+NoTeleponAkun+"\n" +
                " "+AlamatAkun+"\n" +
                " "+StatusAkun;

    }
}
