package com.example.rr_japung.ui_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.rr_japung.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    public static String reference_key_no_kendaraan=null,
                            merk_kendaraan=null,
                            tipe_kendaraan=null,
                            tahun_produksi=null,
                            ukuran_mesin=null,
                            status_kendaraan=null,
                            harga_sewa=null,
                            transmisi=null,
                            mesin=null,
                            imageURL=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        BottomNavigationView navView = findViewById(R.id.nav_view_admin);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home_admin, R.id.navigation_transaksi_admin, R.id.navigation_input_data_mobil_admin, R.id.navigation_akun_admin)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_admin);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
//        AdminActivity.class.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}