package com.example.rr_japung.ui_admin.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rr_japung.R;
import com.example.rr_japung.adapter.ImageAdapter;
import com.example.rr_japung.model.Upload;
import com.example.rr_japung.ui_admin.AdminActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ImageAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<Upload> mUploads;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.admin_ui_fragment_home, container, false);

        mRecyclerView = root.findViewById(R.id.recycler_view1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mProgressCircle = root.findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();
        mAdapter = new ImageAdapter(getContext(), mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(HomeFragment.this);
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
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
        Toast.makeText(getContext(), "Normal click at position: " + position, Toast.LENGTH_SHORT).show();

        ambilData(position);

        AppCompatActivity activity = (AppCompatActivity) getContext();
        Fragment myFragment = new PemesananKendaraanFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.admin_ui_fragment_home, myFragment).addToBackStack(null).commit();
    }

    private void ambilData(int position){
        Upload selectedItem = mUploads.get(position);
        AdminActivity.reference_key_no_kendaraan = selectedItem.getKey();

        AdminActivity.merk_kendaraan = selectedItem.getmMerk();
        AdminActivity.tipe_kendaraan = selectedItem.getmTipeKendaraan();
        AdminActivity.tahun_produksi = selectedItem.getmTahunProduksi();
        AdminActivity.ukuran_mesin = selectedItem.getmUkuranMesin();
        AdminActivity.status_kendaraan = selectedItem.getmStatusKendaraan();
        AdminActivity.harga_sewa = selectedItem.getmHargaSewa();
        AdminActivity.transmisi = selectedItem.getmTransmisi();
        AdminActivity.mesin = selectedItem.getmMesin();
        AdminActivity.imageURL = selectedItem.getmImageUrl();
    }

    @Override
    public void onWhatEverClick(int position) {
        Toast.makeText(getContext(), "Edit data mobil yang di pilih " + position, Toast.LENGTH_SHORT).show();

        ambilData(position);

        AppCompatActivity activity = (AppCompatActivity) getContext();
        Fragment myFragment = new EditDataMobilFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.admin_ui_fragment_home, myFragment).addToBackStack(null).commit();
    }

    @Override
    public void onDeleteClick(int position) {
        Upload selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getKey();
        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getmImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }


}