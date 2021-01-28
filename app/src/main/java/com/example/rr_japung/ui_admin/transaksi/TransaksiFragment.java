package com.example.rr_japung.ui_admin.transaksi;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rr_japung.R;
import com.example.rr_japung.adapter.DaftarTransaksi;
import com.example.rr_japung.adapter.ImageAdapter;
import com.example.rr_japung.model.Transaksi;
import com.example.rr_japung.model.Upload;
import com.example.rr_japung.ui_admin.AdminActivity;
import com.example.rr_japung.ui_admin.home.HomeFragment;
import com.example.rr_japung.ui_pelanggan.PelangganActivity;
import com.example.rr_japung.ui_pelanggan.keranjang.DetailPemesananKendaraanFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class TransaksiFragment extends Fragment implements DaftarTransaksi.OnItemClickListener {

//    private TextView TanggalPinjam,TanggalKembali,JamPinjam;
    private Button ButtonPesanSekarang;
    private RecyclerView mRecyclerView;
    private DaftarTransaksi mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRefTransaksi;
    private ValueEventListener mDBListenerTransaksi;
    private List<Transaksi> mTransaksi;
    private ArrayList<Long> TanggalPesan = new ArrayList<>();
    private ArrayList<Long> TanggalKembali = new ArrayList<>();
//    private long cekR1 = ;
    private int TanggalPesan1[],TanggalKembali2[];
//    private int mYear, mMonth, mDay, mHour, mMinute;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.admin_ui_fragment_transaksi, container, false);
        mRecyclerView = root.findViewById(R.id.recycler_view_daftar_transaksi);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mProgressCircle = root.findViewById(R.id.progress_circle);
        mTransaksi = new ArrayList<>();
        mAdapter = new DaftarTransaksi(getContext(), mTransaksi);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(TransaksiFragment.this);
        mDatabaseRefTransaksi = FirebaseDatabase.getInstance().getReference("transaksi");
        mDBListenerTransaksi = mDatabaseRefTransaksi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mTransaksi.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(postSnapshot.child("statusPembayaran").exists()){
                        String query = postSnapshot.child("statusPembayaran").getValue(String.class);
                        if("SUKSES".equals(query)){
                            Transaksi transaksi = postSnapshot.getValue(Transaksi.class);
                            transaksi.setKey(postSnapshot.getKey());
                            mTransaksi.add(transaksi);
                        }
                    }
                    TanggalPesan.add( Long.parseLong(postSnapshot.child("tanggalPesan").getValue(String.class)));
                    TanggalKembali.add( Long.parseLong(postSnapshot.child("tanggalKembali").getValue(String.class)));
//                    TanggalKembali2.add(dataSnapshot.child("tanggalKembali").getValue(String.class));
//                    TanggalPinjam1.
                }
                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

        ButtonPesanSekarang = root.findViewById(R.id.jajal);
        ButtonPesanSekarang.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void  onClick(View v){
                int notready[]= {10,21,32,43,54,65,96,108};
                int notready1[]={20,31,42,53,64,75,106,118};
//                int angka = 11,angka1 = 12;
                long cekready = Long.parseLong("1608969641101");
                long cekready1 = Long.parseLong("1608779607690");
                long cekready2 = Long.parseLong("1608879607680");
                boolean HasilCek = true;
                for (int i = 0; i < TanggalPesan.size(); i++){
                    if(cekready2<TanggalPesan.get(i) || cekready1>TanggalKembali.get(i)){
                        Toast.makeText(getContext(), TanggalPesan.get(i)+" ya "+TanggalKembali.get(i), Toast.LENGTH_SHORT).show();
                        //JADWAL SET TIDAK ADA YANG BENTROK
                    } else {
                        Toast.makeText(getContext(), TanggalPesan.get(i)+" tidak "+TanggalKembali.get(i), Toast.LENGTH_SHORT).show();
                        //JADWAL SET BENTROK
                        HasilCek = false;
                        i = TanggalPesan.size();
                    }
                }

            }
        });

        return root;
    }


    @Override
    public void onItemClick(int position) {
        Transaksi selectedItem = mTransaksi.get(position);
        AdminActivity.reference_key_no_kendaraan = selectedItem.getKey();

        AdminActivity.merk_kendaraan = selectedItem.getMerkKendaraan();
        AdminActivity.tipe_kendaraan = selectedItem.getTipeKendaraan();
        AdminActivity.no_kendaraan =  selectedItem.getNoKendaraan();
        AdminActivity.tahun_produksi = selectedItem.getTahunProduksiKendaraan();
        AdminActivity.ukuran_mesin = selectedItem.getUkuranMesinKendaraan();
//        AdminActivity.status_kendaraan = selectedItem.getStatusKendaraan();
        AdminActivity.harga_sewa = selectedItem.getHargaSewaKendaraan();
        AdminActivity.transmisi = selectedItem.getTransmisiKendaraan();
        AdminActivity.mesin = selectedItem.getMesinKendaraan();
        AdminActivity.imageURL = selectedItem.getFotoKendaraanURL();
        AdminActivity.total_harga_sewa = selectedItem.getTotalHargaSewa();
        AdminActivity.tanggal_pesan = selectedItem.getTanggalPesan();
        AdminActivity.tanggal_kembali = selectedItem.getTanggalKembali();
        AdminActivity.nama_sopir = selectedItem.getNamaSopir();
        AdminActivity.no_telepon_sopir = selectedItem.getNoTeleponSopir();
        AdminActivity.harga_paket_sopir = selectedItem.getHargaPaketSopir();
        AdminActivity.no_KTP_pemesan = selectedItem.getNoKTPPemesan();
        AdminActivity.nama_pemesan = selectedItem.getNamaPemesan();
        AdminActivity.no_telepon_pemesan = selectedItem.getNoTeleponPemesan();
        AdminActivity.alamat_pemesan = selectedItem.getAlamatPemesan();
        AdminActivity.id_pembayaran = selectedItem.getIdPembayaran();
        AdminActivity.status_pembayaran = selectedItem.getStatusPembayaran();

        AppCompatActivity activity = (AppCompatActivity) getContext();
        Fragment myFragment = new DetailPesananKendaraanFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.admin_ui_fragment_transaksi, myFragment).addToBackStack(null).commit();
    }
}