package com.example.rr_japung.ui_pelanggan.transaksi;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.rr_japung.R;
import com.example.rr_japung.adapter.DaftarTransaksi;
import com.example.rr_japung.model.Transaksi;
import com.example.rr_japung.ui_pelanggan.PelangganActivity;
import com.example.rr_japung.ui_pelanggan.keranjang.DetailPemesananKendaraanFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private WebView webView;
TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.pelanggan_ui_fragment_transaksi, container, false);
        mRecyclerView = root.findViewById(R.id.recycler_view_daftar_transaksi);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mProgressCircle = root.findViewById(R.id.progress_circle);
        mTransaksi = new ArrayList<>();
        mAdapter = new DaftarTransaksi(getContext(), mTransaksi);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(com.example.rr_japung.ui_pelanggan.transaksi.TransaksiFragment.this);
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

        ButtonPesanSekarang = root.findViewById(R.id.jajal_tombol_iki);
        webView = root.findViewById(R.id.webview);
        textView = root.findViewById(R.id.tes_text_view);ButtonPesanSekarang.setText("iso ra3434tombol");
        ButtonPesanSekarang.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void  onClick(View v){

                textView.setText("iso ra ki tombol");
                // ...
                jajal();


//                int notready[]= {10,21,32,43,54,65,96,108};
//                int notready1[]={20,31,42,53,64,75,106,118};
////                int angka = 11,angka1 = 12;
//                long cekready = Long.parseLong("1608969641101");
//                long cekready1 = Long.parseLong("1608779607690");
//                long cekready2 = Long.parseLong("1608879607680");
//                boolean HasilCek = true;
//                for (int i = 0; i < TanggalPesan.size(); i++){
//                    if(cekready2<TanggalPesan.get(i) || cekready1>TanggalKembali.get(i)){
//                        Toast.makeText(getContext(), TanggalPesan.get(i)+" ya "+TanggalKembali.get(i), Toast.LENGTH_SHORT).show();
//                        //JADWAL SET TIDAK ADA YANG BENTROK
//                    } else {
//                        Toast.makeText(getContext(), TanggalPesan.get(i)+" tidak "+TanggalKembali.get(i), Toast.LENGTH_SHORT).show();
//                        //JADWAL SET BENTROK
//                        HasilCek = false;
//                        i = TanggalPesan.size();
//                    }
//                }

            }
        });

        return root;
    }

    public void jajal(){
//        webView.setVisibility(View.VISIBLE);
        // Instantiate the RequestQueue.
        String idInvoice = "600dcf9418e593404016e1aa";
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="http://192.168.22.107/rrjapung/cek-invoice.php?id-invoice="+idInvoice;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String invoice_url = jsonObject.getString("invoice_url");
                            textView.setText(invoice_url);
                        }catch (JSONException err){
                            Log.d("Error", err.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
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
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.pelanggan_ui_fragment_transaksi, myFragment).addToBackStack(null).commit();
    }
}