package com.example.rr_japung.ui_pelanggan.keranjang;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rr_japung.R;
import com.example.rr_japung.adapter.KeranjangPesanan;
import com.example.rr_japung.model.Transaksi;
import com.example.rr_japung.model.Upload;
import com.example.rr_japung.ui_pelanggan.PelangganActivity;
import com.example.rr_japung.ui_pelanggan.home.PemesananKendaraanFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class KeranjangPesananFragment extends Fragment implements KeranjangPesanan.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private KeranjangPesanan mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRefTransaksi;
    private ValueEventListener mDBListenerTransaksi;
    private List<Transaksi> mTransaksi;
    private ArrayList<Long> TanggalPesan = new ArrayList<>();
    private ArrayList<Long> TanggalKembali = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.pelanggan_ui_fragment_keranjang_pesanan, container, false);
        mRecyclerView = root.findViewById(R.id.recycler_view_keranjang_pesanan);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mProgressCircle = root.findViewById(R.id.progress_circle);
        mTransaksi = new ArrayList<>();
        mAdapter = new KeranjangPesanan(getContext(), mTransaksi);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(com.example.rr_japung.ui_pelanggan.keranjang.KeranjangPesananFragment.this);
        mDatabaseRefTransaksi = FirebaseDatabase.getInstance().getReference("transaksi");
        mDBListenerTransaksi = mDatabaseRefTransaksi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mTransaksi.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(postSnapshot.child("statusPembayaran").exists()){
                        String query = postSnapshot.child("statusPembayaran").getValue(String.class);
                        if(query.equals("PENDING")){
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


        return root;
    }

    @Override
    public void onItemClick(int position) {
        Transaksi selectedItem = mTransaksi.get(position);
        PelangganActivity.reference_key_no_kendaraan = selectedItem.getKey();

        PelangganActivity.merk_kendaraan = selectedItem.getMerkKendaraan();
        PelangganActivity.tipe_kendaraan = selectedItem.getTipeKendaraan();
        PelangganActivity.no_kendaraan =  selectedItem.getNoKendaraan();
        PelangganActivity.tahun_produksi = selectedItem.getTahunProduksiKendaraan();
        PelangganActivity.ukuran_mesin = selectedItem.getUkuranMesinKendaraan();
//        PelangganActivity.status_kendaraan = selectedItem.getStatusKendaraan();
        PelangganActivity.harga_sewa = selectedItem.getHargaSewaKendaraan();
        PelangganActivity.transmisi = selectedItem.getTransmisiKendaraan();
        PelangganActivity.mesin = selectedItem.getMesinKendaraan();
        PelangganActivity.imageURL = selectedItem.getFotoKendaraanURL();
        PelangganActivity.total_harga_sewa = selectedItem.getTotalHargaSewa();
        PelangganActivity.tanggal_pesan = selectedItem.getTanggalPesan();
        PelangganActivity.tanggal_kembali = selectedItem.getTanggalKembali();
        PelangganActivity.nama_sopir = selectedItem.getNamaSopir();
        PelangganActivity.no_telepon_sopir = selectedItem.getNoTeleponSopir();
        PelangganActivity.harga_paket_sopir = selectedItem.getHargaPaketSopir();
        PelangganActivity.no_KTP_pemesan = selectedItem.getNoKTPPemesan();
        PelangganActivity.nama_pemesan = selectedItem.getNamaPemesan();
        PelangganActivity.no_telepon_pemesan = selectedItem.getNoTeleponPemesan();
        PelangganActivity.alamat_pemesan = selectedItem.getAlamatPemesan();
        PelangganActivity.id_pembayaran = selectedItem.getIdPembayaran();
        PelangganActivity.status_pembayaran = selectedItem.getStatusPembayaran();

        AppCompatActivity activity = (AppCompatActivity) getContext();
        Fragment myFragment = new DetailPemesananKendaraanFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.pelanggan_ui_fragment_keranjang_pesanan, myFragment).addToBackStack(null).commit();

    }
}