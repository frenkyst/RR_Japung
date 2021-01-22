package com.example.rr_japung.ui_pelanggan.transaksi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rr_japung.R;
import com.example.rr_japung.adapter.DaftarTransaksi;
import com.example.rr_japung.model.Transaksi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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

        View root = inflater.inflate(R.layout.pelanggan_ui_fragment_transaksi, container, false);
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
                    Transaksi transaksi = postSnapshot.getValue(Transaksi.class);
                    transaksi.setKey(postSnapshot.getKey());
                    mTransaksi.add(transaksi);
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

    }
}