package com.example.rr_japung.ui_pelanggan.keranjang;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rr_japung.R;
import com.example.rr_japung.adapter.DaftarSopirAdapter;
import com.example.rr_japung.model.DataSopir;
import com.example.rr_japung.ui_pelanggan.PelangganActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.Delayed;


public class DetailPemesananKendaraanFragment extends Fragment implements DaftarSopirAdapter.OnItemClickListener{

    private TextView TextViewTanggalPesan,TextViewTanggalKembali,TextViewJamPesan;
    private ImageView mImageView;
    private TextView TextViewHargaSewa,TextViewMerkTipeMobil, TextViewTipeMobil,TextViewTahunProduksi,TextViewTransmisi,TextViewUkuranMesin,TextViewMesinBS,TextViewNoKendaraan;
    private TextView TextViewPaketSupir;
    private TextView TextViewPaketNamaSupir,TextViewPaketNoTeleponSupir,TextViewPaketHargaSupir;
    //    private TextView TextViewNamaSupir,TextViewNoTeleponSupir,TextViewHargaPaketSupir;
    private TextView TextViewTotalHargaSewa;
    private TextView TextViewStatusPembayaran;
    private EditText EditTextNamaPemesan,EditTextNoKTPPemesan,EditTextNoTeleponPemesan,EditTextAlamatPemesan;
    private Button ButtonPesanSekarang;
    private RecyclerView mRecyclerViewSopir;
    private DaftarSopirAdapter mAdapterSopir;
    private ProgressBar mProgressCircleSopir;
    private LinearLayout LinearLayoutPaketSopir,LineraLayoutListPaketSopir;
    private DatabaseReference mDatabaseRefDataSopir;
    private DatabaseReference mDatabaseRefPesanSekarang;
    private DatabaseReference mDatabaseRefBayarPesanan;
    private ValueEventListener mDBListenerDataSopir;
    private ValueEventListener mDBListenerPesanSekarang;
    private ValueEventListener mDBListenerBayarPesanan;
    private String uid,email;
    private List<DataSopir> mDataSopir;

    private int mYear, mMonth, mDay, mHour, mMinute;
    private int TotalHargaSewa = Integer.parseInt(PelangganActivity.harga_sewa),
            PaketHargaSupir = 0;

    private Date TanggalTransaksi = Calendar.getInstance().getTime();
    private SimpleDateFormat fulldate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private SimpleDateFormat tanggal = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat jam = new SimpleDateFormat("hh:mm:ss");
//    String date = DateFormat.format("dd-MM-yyyy  hh:mm", Long.parseLong(PelangganActivity.tanggal_pesan)).toString();
    private String formattedDateTanggalTransaksi = tanggal.format(TanggalTransaksi);
    private String formattedDateTanggalPesan = DateFormat.format("dd-MM-yyyy", Long.parseLong(PelangganActivity.tanggal_pesan)).toString();
    private String formattedDateTanggalKembali = DateFormat.format("dd-MM-yyyy", Long.parseLong(PelangganActivity.tanggal_kembali)).toString();
    private String formattedTime = DateFormat.format("hh:mm", Long.parseLong(PelangganActivity.tanggal_pesan)).toString();
    private Date TanggalPesan = new Date(Long.parseLong(PelangganActivity.tanggal_pesan));
    private Date TanggalKembali = new Date(Long.parseLong(PelangganActivity.tanggal_kembali));
    private Calendar TanggalPesan1 = Calendar.getInstance();
    private Calendar TanggalKembali1 = Calendar.getInstance();
    private int hour;
    private int minute;
    private WebView webView;
    private Boolean ButtonBayar=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.pelanggan_ui_fragment_pemesanan_kendaraan, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
            email = user.getEmail();
        }
        TextViewTanggalPesan = root.findViewById(R.id.textview_pesan);
        TextViewTanggalKembali = root.findViewById(R.id.textview_kembali);
        TextViewJamPesan = root.findViewById(R.id.textview_jam_pesan);
        mImageView = root.findViewById(R.id.image_view);
        TextViewHargaSewa = root.findViewById(R.id.textview_harga_sewa);
        TextViewMerkTipeMobil = root.findViewById(R.id.textview_merk_dan_tipe_kendaraan);
        TextViewTipeMobil = root.findViewById(R.id.textview_merk_kendaraan);
        TextViewTahunProduksi = root.findViewById(R.id.textview_tahun_produksi);
        TextViewTransmisi = root.findViewById(R.id.textview_transmisi);
        TextViewUkuranMesin = root.findViewById(R.id.textview_ukuran_mesin);
        TextViewMesinBS = root.findViewById(R.id.textview_mesin);
        TextViewNoKendaraan = root.findViewById(R.id.textview_no_kendaraan);
        TextViewPaketSupir = root.findViewById(R.id.textview_paket_supir);/////
        TextViewTotalHargaSewa = root.findViewById(R.id.textview_total_harga_sewa);
        TextViewStatusPembayaran = root.findViewById(R.id.textview_status_pembayaran);
        Picasso.get()
                .load(PelangganActivity.imageURL)
                .placeholder(R.drawable.mobildefault)
                .into(mImageView);
        DecimalFormat decim = new DecimalFormat("#,###");
        TextViewHargaSewa.setText("Rp. "+decim.format(Integer.parseInt(PelangganActivity.harga_sewa))+",00");
        TextViewMerkTipeMobil.setText(PelangganActivity.merk_kendaraan+" "+PelangganActivity.tipe_kendaraan);
        TextViewTipeMobil.setText(PelangganActivity.tipe_kendaraan);
        TextViewTahunProduksi.setText(PelangganActivity.tahun_produksi+" TH");
        TextViewTransmisi.setText(PelangganActivity.transmisi);
        TextViewUkuranMesin.setText(PelangganActivity.ukuran_mesin+" CC");
        TextViewMesinBS.setText(PelangganActivity.mesin);
        TextViewNoKendaraan.setText(PelangganActivity.no_kendaraan);
        TextViewTotalHargaSewa.setText("Rp. "+decim.format(Integer.parseInt(PelangganActivity.harga_sewa))+",00");

//        TanggalKembali1.setTime(TanggalPesan);
//        TanggalKembali1.add(Calendar.DATE, 1);
//        TanggalKembali = TanggalKembali1.getTime();
//        formattedDateTanggalKembali = tanggal.format(TanggalKembali);
//        formattedTime = jam.format(TanggalPesan);
        TextViewTanggalPesan.setText(formattedDateTanggalPesan);
        TextViewTanggalKembali.setText(formattedDateTanggalKembali);
        TextViewJamPesan.setText(formattedTime);
        TextViewTanggalPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date1();
            }
        });
        TextViewTanggalKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date2();
            }
        });
        TextViewJamPesan.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void  onClick(View v){
                time1();
            }
        });


        TextViewPaketNamaSupir = root.findViewById(R.id.textview_nama_sopir);
        TextViewPaketNoTeleponSupir = root.findViewById(R.id.textview_no_telepon_sopir);
        TextViewPaketHargaSupir = root.findViewById(R.id.textview_harga_paket_sopir);
        LinearLayoutPaketSopir = root.findViewById(R.id.linear_layout_paket_sopir);
        LineraLayoutListPaketSopir = root.findViewById(R.id.linear_layout_list_paket_sopir);
        if(PelangganActivity.nama_sopir!=null){
            LinearLayoutPaketSopir.setVisibility(View.VISIBLE);
            TextViewPaketNamaSupir.setText(PelangganActivity.nama_sopir);
            TextViewPaketNoTeleponSupir.setText(PelangganActivity.no_telepon_sopir);
            PaketHargaSupir=Integer.parseInt(PelangganActivity.harga_paket_sopir);
            TextViewPaketHargaSupir.setText("Rp. "+decim.format(PaketHargaSupir)+",00");
            TotalHargaSewa = PaketHargaSupir+TotalHargaSewa;
            TextViewTotalHargaSewa.setText("Rp. "+decim.format(TotalHargaSewa)+",00");
        }
//        Toast.makeText(getContext(), "nama sopir -> "+PelangganActivity.nama_sopir, Toast.LENGTH_LONG).show();

        TextViewPaketSupir.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void  onClick(View v){
                LinearLayoutPaketSopir.setVisibility(View.GONE);
                mRecyclerViewSopir = root.findViewById(R.id.recycler_view_pilih_sopir);
                LineraLayoutListPaketSopir.setVisibility(View.VISIBLE);
                mRecyclerViewSopir.setHasFixedSize(true);
                mRecyclerViewSopir.setLayoutManager(new LinearLayoutManager(getContext()));
                mProgressCircleSopir = root.findViewById(R.id.progress_circle_sopir);
                mDataSopir = new ArrayList<>();
                mAdapterSopir = new DaftarSopirAdapter(getContext(), mDataSopir);
                mRecyclerViewSopir.setAdapter(mAdapterSopir);
                mAdapterSopir.setOnItemClickListener(com.example.rr_japung.ui_pelanggan.keranjang.DetailPemesananKendaraanFragment.this);
                mDatabaseRefDataSopir = FirebaseDatabase.getInstance().getReference("sopir");
                mDBListenerDataSopir = mDatabaseRefDataSopir.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mDataSopir.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            DataSopir dataSopir = postSnapshot.getValue(DataSopir.class);
                            dataSopir.setKey(postSnapshot.getKey());
                            mDataSopir.add(dataSopir);
                        }
                        mAdapterSopir.notifyDataSetChanged();
                        mProgressCircleSopir.setVisibility(View.GONE);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        mProgressCircleSopir.setVisibility(View.GONE);
                    }
                });

            }
        });

        ButtonPesanSekarang = root.findViewById(R.id.button_pesan_sekarang);
        EditTextNamaPemesan = root.findViewById(R.id.edittext_nama_pemesan);
        EditTextNoKTPPemesan = root.findViewById(R.id.edittext_no_ktp_pemesan);
        EditTextNoTeleponPemesan = root.findViewById(R.id.edittext_no_telpon_pemesan);
        EditTextAlamatPemesan = root.findViewById(R.id.edittext_alamat_pemesan);
        EditTextNamaPemesan.setText(PelangganActivity.nama_pemesan);
        EditTextNoKTPPemesan.setText(PelangganActivity.no_KTP_pemesan);
        EditTextNoTeleponPemesan.setText(PelangganActivity.no_telepon_pemesan);
        EditTextAlamatPemesan.setText(PelangganActivity.alamat_pemesan);

        cekStatusPembayaran();
        ButtonPesanSekarang.setText("BAYAR");
        webView = root.findViewById(R.id.webview);
        ButtonPesanSekarang .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.VISIBLE);
                ButtonBayar=true;
                cekStatusPembayaran();
//                formattedDateTanggalTransaksi = fulldate.format(TanggalTransaksi);
//                formattedDateTanggalPesan = fulldate.format(TanggalPesan);
//                formattedDateTanggalKembali = fulldate.format(TanggalKembali);
//                Date timestampTanggalTransaksi = null;
//                Date timestampTanggalPesan = null;
//                Date timestampTanggalKembali = null;
//                try {
//                    timestampTanggalTransaksi = (Date)fulldate.parse(formattedDateTanggalTransaksi);
//                    timestampTanggalPesan = (Date)fulldate.parse(formattedDateTanggalPesan);
//                    timestampTanggalKembali = (Date)fulldate.parse(formattedDateTanggalKembali);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                if (PaketHargaSupir==0){
//                    Transaksi dataTransaksi = new Transaksi(PelangganActivity.merk_kendaraan.trim(),
//                            PelangganActivity.tipe_kendaraan.trim(),
//                            PelangganActivity.no_kendaraan.trim(),
//                            PelangganActivity.tahun_produksi,
//                            PelangganActivity.ukuran_mesin,
//                            PelangganActivity.harga_sewa.trim(),
//                            PelangganActivity.transmisi,
//                            PelangganActivity.mesin,
//                            PelangganActivity.imageURL,
//                            String.valueOf(TotalHargaSewa).trim(),
//                            String.valueOf(timestampTanggalPesan.getTime()).trim(),
//                            String.valueOf(timestampTanggalKembali.getTime()).trim(),
//                            String.valueOf(timestampTanggalTransaksi.getTime()).trim(),
//                            EditTextNoKTPPemesan.getText().toString().trim(),
//                            EditTextNamaPemesan.getText().toString().trim(),
//                            EditTextNoTeleponPemesan.getText().toString().trim(),
//                            EditTextAlamatPemesan.getText().toString().trim());
//                    mDatabaseRefPesanSekarang = FirebaseDatabase.getInstance().getReference("keranjang/"+PelangganActivity.reference_key_no_kendaraan);
//                    mDatabaseRefPesanSekarang.setValue(dataTransaksi);
//                    Toast.makeText(getContext(), "Transaksi successful", Toast.LENGTH_LONG).show();
//                } else {
//                    Transaksi dataTransaksi = new Transaksi(PelangganActivity.merk_kendaraan.trim(),
//                            PelangganActivity.tipe_kendaraan.trim(),
//                            PelangganActivity.no_kendaraan.trim(),
//                            PelangganActivity.tahun_produksi,
//                            PelangganActivity.ukuran_mesin,
//                            PelangganActivity.harga_sewa.trim(),
//                            PelangganActivity.transmisi,
//                            PelangganActivity.mesin,
//                            PelangganActivity.imageURL,
//                            String.valueOf(TotalHargaSewa).trim(),
//                            String.valueOf(timestampTanggalPesan.getTime()).trim(),
//                            String.valueOf(timestampTanggalKembali.getTime()).trim(),
//                            String.valueOf(timestampTanggalTransaksi.getTime()).trim(),
//                            TextViewPaketNamaSupir.getText().toString().trim(),
//                            TextViewPaketNoTeleponSupir.getText().toString().trim(),
//                            String.valueOf(PaketHargaSupir).trim(),
//                            EditTextNoKTPPemesan.getText().toString().trim(),
//                            EditTextNamaPemesan.getText().toString().trim(),
//                            EditTextNoTeleponPemesan.getText().toString().trim(),
//                            EditTextAlamatPemesan.getText().toString().trim());
//                    mDatabaseRefPesanSekarang = FirebaseDatabase.getInstance().getReference("keranjang/"+PelangganActivity.reference_key_no_kendaraan);
//                    mDatabaseRefPesanSekarang.setValue(dataTransaksi);
//                    Toast.makeText(getContext(), "Transaksi successful", Toast.LENGTH_LONG).show();
//                }

            }
        });

        TanggalPesan1.setTime(TanggalPesan);
        hour = TanggalPesan1.get(Calendar.HOUR_OF_DAY);
        minute = TanggalPesan1.get(Calendar.MINUTE);

        return root;
    }

    public void prosesPembayaran(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="http://192.168.22.107/rrjapung/create-invoice.php?id-invoices="+uid+"&email-invoice="+email+"&desciption-invoice="+"Pemesanan Mobil"+"&amount-invoice="+TotalHargaSewa;
//        final String[] invoice_url = new String[1];
//        invoice_url[0]=null;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String invoice_url;
                            invoice_url = jsonObject.getString("invoice_url");
                            webView.getSettings().setLoadsImagesAutomatically(true);
                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.getSettings().setDomStorageEnabled(true);
                            webView.loadUrl(invoice_url);
                            mDatabaseRefPesanSekarang = FirebaseDatabase.getInstance().getReference("transaksi/"+PelangganActivity.reference_key_no_kendaraan+"/idPembayaran");
                            mDatabaseRefPesanSekarang.setValue(invoice_url);
                        }catch (JSONException err){
                            Log.d("Error", err.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Respon URL ERROR ", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public void cekStatusPembayaran(){
        if("PENDING".equals(PelangganActivity.status_pembayaran)){
            if("TRANSFER".equals(PelangganActivity.id_pembayaran)){
                if(ButtonBayar==true){
                    prosesPembayaran();
                }
            } else {
                String idInvoice = PelangganActivity.id_pembayaran.substring(PelangganActivity.id_pembayaran.lastIndexOf("/") + 1);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                String url ="http://192.168.22.107/rrjapung/cek-invoice.php?id-invoice="+idInvoice;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");
                                    if("PENDING".equals(status) || "EXPIRED".equals(status)){
                                        webView.getSettings().setLoadsImagesAutomatically(true);
                                        webView.getSettings().setJavaScriptEnabled(true);
                                        webView.getSettings().setDomStorageEnabled(true);
                                        webView.loadUrl(PelangganActivity.id_pembayaran);
                                    }else {
                                        mDatabaseRefPesanSekarang = FirebaseDatabase.getInstance().getReference("transaksi/"+PelangganActivity.reference_key_no_kendaraan+"/statusPembayaran");
                                        mDatabaseRefPesanSekarang.setValue("SUKSES");
                                        TextViewStatusPembayaran.setText("SUKSES");
                                    }
                                }catch (JSONException err){
                                    Log.d("Error", err.toString());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Cek Status Pembayaran Error", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(stringRequest);

            }
        }else {
            TextViewStatusPembayaran.setText("SUKSES");
            if(webView != null){
                String idInvoice = PelangganActivity.id_pembayaran.substring(PelangganActivity.id_pembayaran.lastIndexOf("/") + 1);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                String url ="http://192.168.22.107/rrjapung/cek-invoice.php?id-invoice="+idInvoice;
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setDomStorageEnabled(true);
                webView.loadUrl(PelangganActivity.id_pembayaran);
            }
        }

    }

    public void onItemClick(int position) {
        PaketHargaSupir = 150000;
        DecimalFormat decim = new DecimalFormat("#,###");
        Toast.makeText(getContext(), "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
        DataSopir selectedItem = mDataSopir.get(position);
        TextViewPaketNamaSupir.setText(selectedItem.getNamaSopir());
        TextViewPaketNoTeleponSupir.setText(selectedItem.getNoTeleponSopir());
        TextViewPaketHargaSupir.setText("Rp."+decim.format(PaketHargaSupir)+",00");
        TotalHargaSewa = Integer.valueOf(PelangganActivity.harga_sewa)+150000;
        TextViewTotalHargaSewa.setText("Rp. "+decim.format(TotalHargaSewa)+",00");
        LineraLayoutListPaketSopir.setVisibility(View.GONE);
        LinearLayoutPaketSopir.setVisibility(View.VISIBLE);
    }

    private void date1(){
        TanggalPesan1.setTime(TanggalPesan);
        mYear = TanggalPesan1.get(Calendar.YEAR);
        mMonth = TanggalPesan1.get(Calendar.MONTH);
        mDay = TanggalPesan1.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Calendar combinedCal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));

                        combinedCal.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                        String formattedDate = df.format(combinedCal.getTime());
                        combinedCal.set(Calendar.HOUR_OF_DAY, hour);
                        combinedCal.set(Calendar.MINUTE, minute);
                        TextViewTanggalPesan.setText(formattedDate);
                        TanggalPesan1=combinedCal;
                        TanggalPesan = combinedCal.getTime();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    private void date2(){
        TanggalKembali1.setTime(TanggalKembali);
        mYear = TanggalKembali1.get(Calendar.YEAR);
        mMonth = TanggalKembali1.get(Calendar.MONTH);
        mDay = TanggalKembali1.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Calendar combinedCal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));

                        combinedCal.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                        String formattedDate = df.format(combinedCal.getTime());
                        combinedCal.set(Calendar.HOUR_OF_DAY, hour);
                        combinedCal.set(Calendar.MINUTE, minute);
                        TextViewTanggalKembali.setText(formattedDate);
                        TanggalKembali1=combinedCal;
                        TanggalKembali = combinedCal.getTime();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    private void time1(){
//        final Calendar mcurrentTime = Calendar.getInstance();
        hour = TanggalPesan1.get(Calendar.HOUR_OF_DAY);
        minute = TanggalPesan1.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                TextViewJamPesan.setText( String.format("%02d:%02d", selectedHour, selectedMinute)+":00");
                TanggalPesan1.setTime(TanggalPesan);
                TanggalPesan1.set(Calendar.HOUR_OF_DAY, selectedHour);
                TanggalPesan1.set(Calendar.MINUTE, selectedMinute);
                TanggalPesan = TanggalPesan1.getTime();
                TanggalPesan1.setTime(TanggalKembali);
                TanggalPesan1.set(Calendar.HOUR_OF_DAY, selectedHour);
                TanggalPesan1.set(Calendar.MINUTE, selectedMinute);
                TanggalKembali = TanggalPesan1.getTime();
                TanggalPesan1.setTime(TanggalPesan);
                hour = selectedHour;
                minute = selectedMinute;
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
}