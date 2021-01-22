package com.example.rr_japung.ui_pelanggan;

import android.os.Bundle;

import com.example.rr_japung.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class PelangganActivity extends AppCompatActivity {

    public static String reference_key_no_kendaraan=null,
            merk_kendaraan=null,
            tipe_kendaraan=null,
            no_kendaraan=null,
            tahun_produksi=null,
            ukuran_mesin=null,
            status_kendaraan=null,
            harga_sewa=null,
            transmisi=null,
            mesin=null,
            imageURL=null,
    total_harga_sewa=null,
    tanggal_pesan=null,
    tanggal_kembali=null,
            nama_sopir=null,
            no_telepon_sopir=null,
            harga_paket_sopir=null,
    nama_pemesan=null,
    no_KTP_pemesan=null,
    no_telepon_pemesan=null,
    alamat_pemesan=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelanggan);
        BottomNavigationView navView = findViewById(R.id.nav_view_pelanggan);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home_pelanggan, R.id.navigation_keranjang_pesanan_pelanggan, R.id.navigation_transaksi_pelanggan, R.id.navigation_akun_pelanggan)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_pelanggan);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}