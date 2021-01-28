package com.example.rr_japung.ui_pelanggan.home;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.rr_japung.R;
import com.example.rr_japung.adapter.DaftarSopirAdapter;
import com.example.rr_japung.model.DataSopir;
import com.example.rr_japung.model.Transaksi;
import com.example.rr_japung.ui_pelanggan.PelangganActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;


public class PemesananKendaraanFragment extends Fragment implements DaftarSopirAdapter.OnItemClickListener {

    private TextView TextViewTanggalPesan,TextViewTanggalKembali,TextViewJamPesan;
    private ImageView mImageView;
    private TextView TextViewHargaSewa,TextViewMerkTipeMobil, TextViewTipeMobil,TextViewTahunProduksi,TextViewTransmisi,TextViewUkuranMesin,TextViewMesinBS,TextViewNoKendaraan;
    private TextView TextViewPaketSupir;
    private TextView TextViewPaketNamaSupir,TextViewPaketNoTeleponSupir,TextViewPaketHargaSupir;
    //    private TextView TextViewNamaSupir,TextViewNoTeleponSupir,TextViewHargaPaketSupir;
    private TextView TextViewTotalHargaSewa;
    private EditText EditTextNamaPemesan,EditTextNoKTPPemesan,EditTextNoTeleponPemesan,EditTextAlamatPemesan;
    private Button ButtonPesanSekarang;
    private RecyclerView mRecyclerViewSopir;
    private DaftarSopirAdapter mAdapterSopir;
    private ProgressBar mProgressCircleSopir;
    private LinearLayout LinearLayoutPaketSopir,LineraLayoutListPaketSopir;
    private DatabaseReference mDatabaseRefDataSopir;
    private DatabaseReference mDatabaseRefPesanSekarang;
    private DatabaseReference mDatabaseRefDataAkunPelanggan;
    private ValueEventListener mDBListenerDataSopir;
    private ValueEventListener mDBListenerPesanSekarang;
    private ValueEventListener mDBListenerDataAkunPelanggan;
    private String uid;
    private List<DataSopir> mDataSopir;

    private int mYear, mMonth, mDay, mHour, mMinute;
    private int TotalHargaSewa = Integer.parseInt(PelangganActivity.harga_sewa),
            PaketHargaSupir = 0,
    JumlahPesanan = 1;

    private Date TanggalTransaksi = Calendar.getInstance().getTime();
    private Date TanggalPesan = TanggalTransaksi;
    private Date TanggalKembali = new Date();
    private SimpleDateFormat fulldate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private SimpleDateFormat tanggal = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat jam = new SimpleDateFormat("hh:mm:ss");
    private String formattedDateTanggalTransaksi = tanggal.format(TanggalTransaksi);
    private String formattedDateTanggalPesan = tanggal.format(TanggalPesan);
    private String formattedDateTanggalKembali = tanggal.format(TanggalKembali);
    private String formattedTime = jam.format(TanggalPesan);
    private Calendar TanggalPesan1 = Calendar.getInstance();
    private Calendar TanggalKembali1 = Calendar.getInstance();
    private int hour;
    private int minute;

    Date timestampTanggalTransaksi = null;
    Date timestampTanggalPesan = null;
    Date timestampTanggalKembali = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.pelanggan_ui_fragment_pemesanan_kendaraan, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
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
        TextViewPaketSupir = root.findViewById(R.id.textview_paket_supir);
        TextViewTotalHargaSewa = root.findViewById(R.id.textview_total_harga_sewa);
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
        TextViewNoKendaraan.setText(PelangganActivity.reference_key_no_kendaraan);
        TextViewTotalHargaSewa.setText("Rp. "+decim.format(Integer.parseInt(PelangganActivity.harga_sewa))+",00");

        TanggalKembali1.setTime(TanggalPesan);
        TanggalKembali1.add(Calendar.DATE, 1);
        TanggalKembali = TanggalKembali1.getTime();
        formattedDateTanggalKembali = tanggal.format(TanggalKembali);
        formattedTime = jam.format(TanggalPesan);
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
                mAdapterSopir.setOnItemClickListener(com.example.rr_japung.ui_pelanggan.home.PemesananKendaraanFragment.this);
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
//        TextViewNamaSupir = root.findViewById(R.id.textview_nama_sopir);
//        TextViewNoTeleponSupir = root.findViewById(R.id.textview_no_telepon_sopir);
//        TextViewHargaPaketSupir = root.findViewById(R.id.textview_harga_paket_sopir);
        EditTextNamaPemesan = root.findViewById(R.id.edittext_nama_pemesan);
        EditTextNoKTPPemesan = root.findViewById(R.id.edittext_no_ktp_pemesan);
        EditTextNoTeleponPemesan = root.findViewById(R.id.edittext_no_telpon_pemesan);
        EditTextAlamatPemesan = root.findViewById(R.id.edittext_alamat_pemesan);
        mDatabaseRefDataAkunPelanggan = FirebaseDatabase.getInstance().getReference("Root/User/"+uid);
        mDBListenerDataAkunPelanggan = mDatabaseRefDataAkunPelanggan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                EditTextNamaPemesan.setText(dataSnapshot.child("namaAkun").getValue(String.class));
                EditTextNoKTPPemesan.setText(dataSnapshot.child("noKTPAkun").getValue(String.class));
                EditTextNoTeleponPemesan.setText(dataSnapshot.child("noTeleponAkun").getValue(String.class));
                EditTextAlamatPemesan.setText(dataSnapshot.child("alamatAkun").getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        ButtonPesanSekarang .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PaketHargaSupir==0){
                    Transaksi dataTransaksi = new Transaksi(PelangganActivity.merk_kendaraan.trim(),
                            PelangganActivity.tipe_kendaraan.trim(),
                            PelangganActivity.reference_key_no_kendaraan.trim(),
                            PelangganActivity.tahun_produksi,
                            PelangganActivity.ukuran_mesin,
                            PelangganActivity.harga_sewa.trim(),
                            PelangganActivity.transmisi,
                            PelangganActivity.mesin,
                            PelangganActivity.imageURL,
                            String.valueOf(TotalHargaSewa).trim(),
                            String.valueOf(timestampTanggalPesan.getTime()).trim(),
                            String.valueOf(timestampTanggalKembali.getTime()).trim(),
                            String.valueOf(timestampTanggalTransaksi.getTime()).trim(),
                            EditTextNoKTPPemesan.getText().toString().trim(),
                            EditTextNamaPemesan.getText().toString().trim(),
                            EditTextNoTeleponPemesan.getText().toString().trim(),
                            EditTextAlamatPemesan.getText().toString().trim(),
                            "TRANSFER","PENDING");
                    mDatabaseRefPesanSekarang = FirebaseDatabase.getInstance().getReference("transaksi");
                    mDatabaseRefPesanSekarang.push().setValue(dataTransaksi);
                    Toast.makeText(getContext(), "Transaksi successful", Toast.LENGTH_LONG).show();
                } else {
                    Transaksi dataTransaksi = new Transaksi(PelangganActivity.merk_kendaraan.trim(),
                            PelangganActivity.tipe_kendaraan.trim(),
                            PelangganActivity.reference_key_no_kendaraan.trim(),
                            PelangganActivity.tahun_produksi,
                            PelangganActivity.ukuran_mesin,
                            PelangganActivity.harga_sewa.trim(),
                            PelangganActivity.transmisi,
                            PelangganActivity.mesin,
                            PelangganActivity.imageURL,
                            String.valueOf(TotalHargaSewa).trim(),
                            String.valueOf(timestampTanggalPesan.getTime()).trim(),
                            String.valueOf(timestampTanggalKembali.getTime()).trim(),
                            String.valueOf(timestampTanggalTransaksi.getTime()).trim(),
                            TextViewPaketNamaSupir.getText().toString().trim(),
                            TextViewPaketNoTeleponSupir.getText().toString().trim(),
                            String.valueOf(PaketHargaSupir).trim(),
                            EditTextNoKTPPemesan.getText().toString().trim(),
                            EditTextNamaPemesan.getText().toString().trim(),
                            EditTextNoTeleponPemesan.getText().toString().trim(),
                            EditTextAlamatPemesan.getText().toString().trim(),
                            "TRANSFER","PENDING");
                    mDatabaseRefPesanSekarang = FirebaseDatabase.getInstance().getReference("transaksi");
                    mDatabaseRefPesanSekarang.push().setValue(dataTransaksi);
                    Toast.makeText(getContext(), "Transaksi successful", Toast.LENGTH_LONG).show();
                }

            }
        });

        TanggalPesan1.setTime(TanggalPesan);
        hour = TanggalPesan1.get(Calendar.HOUR_OF_DAY);
        minute = TanggalPesan1.get(Calendar.MINUTE);
        TextView StatusPembayaran;
        StatusPembayaran = root.findViewById(R.id.textview_status_pembayaran);
        StatusPembayaran.setVisibility(View.GONE);

        return root;
    }

    public void onItemClick(int position) {
        PaketHargaSupir = 150000;
        DecimalFormat decim = new DecimalFormat("#,###");
        Toast.makeText(getContext(), "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
        DataSopir selectedItem = mDataSopir.get(position);
        TextViewPaketNamaSupir.setText(selectedItem.getNamaSopir());
        TextViewPaketNoTeleponSupir.setText(selectedItem.getNoTeleponSopir());
        TextViewPaketHargaSupir.setText("Rp."+decim.format(PaketHargaSupir)+",00");
        TotalHargaSewa=Integer.valueOf(PelangganActivity.harga_sewa);
        TotalHargaSewa = (TotalHargaSewa+PaketHargaSupir)*JumlahPesanan;
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
                        setTextTotalHarga();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public  void setTextTotalHarga(){
        formattedDateTanggalTransaksi = fulldate.format(TanggalTransaksi);
        formattedDateTanggalPesan = fulldate.format(TanggalPesan);
        formattedDateTanggalKembali = fulldate.format(TanggalKembali);
        try {
            timestampTanggalTransaksi = (Date)fulldate.parse(formattedDateTanggalTransaksi);
            timestampTanggalPesan = (Date)fulldate.parse(formattedDateTanggalPesan);
            timestampTanggalKembali = (Date)fulldate.parse(formattedDateTanggalKembali);
            Long JumlahPenguranganPesanan;
            JumlahPenguranganPesanan = (timestampTanggalKembali.getTime()-timestampTanggalPesan.getTime())/86400000;
            JumlahPesanan = Integer.valueOf(String.valueOf(JumlahPenguranganPesanan));
            TotalHargaSewa=Integer.valueOf(PelangganActivity.harga_sewa);
            TotalHargaSewa=(TotalHargaSewa+PaketHargaSupir)*JumlahPesanan;
            DecimalFormat decim = new DecimalFormat("#,###");
            TextViewTotalHargaSewa.setText("Rp. "+decim.format(TotalHargaSewa)+",00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
                        setTextTotalHarga();
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