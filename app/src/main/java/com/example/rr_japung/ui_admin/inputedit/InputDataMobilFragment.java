package com.example.rr_japung.ui_admin.inputedit;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.rr_japung.R;
import com.example.rr_japung.model.DataSopir;
import com.example.rr_japung.model.Upload;
import com.example.rr_japung.ui_admin.AdminActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;


public class InputDataMobilFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonPilihGambar;
    private Button mButtonUpload;
    private Button mButtonDataMobil,mButtonDataSopir;
    private Button mButtonSimpanSopir;
    private EditText mEditTextTipeKendaraan,mEditTextMerk,mEditTextNoKendaraan,mEditTextTahunProduksi,mEditTextUkuranMesin,mEditTextHargaSewa;
    private EditText mEditTextNoKTPSopir,mEditTextNamaSopir,mEditTextNoTeleponSopir,mEditTextAlamatSopir;
    private String mStatusKendaraan;
    private RadioButton mAutomatic, mManual, mBensin, mDisel;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef,mDatabaseRefSopir;
    private StorageTask mUploadTask;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.admin_ui_fragment_input_data_mobil, container, false);

        mButtonPilihGambar = root.findViewById(R.id.button_pilih_gambar);
        mButtonUpload = root.findViewById(R.id.button_simpan);
        mButtonDataMobil = root.findViewById(R.id.button_tab_data_mobil);
        mButtonDataSopir = root.findViewById(R.id.button_tab_data_sopir);
        mButtonSimpanSopir = root.findViewById(R.id.button_simpan_sopir);
        mEditTextTipeKendaraan = root.findViewById(R.id.tipe_kendaraan);
        mEditTextMerk = root.findViewById(R.id.merk_kendaraan);
        mEditTextNoKendaraan = root.findViewById(R.id.no_kendaraan);
        mEditTextTahunProduksi = root.findViewById(R.id.tahum_produksi);
        mEditTextUkuranMesin = root.findViewById(R.id.ukuran_mesin);
        mStatusKendaraan = "0"; //timestap format
        mEditTextHargaSewa = root.findViewById(R.id.harga_sewa);
        mEditTextNoKTPSopir = root.findViewById(R.id.no_ktp_supir);
        mEditTextNamaSopir = root.findViewById(R.id.nama_sopir);
        mEditTextNoTeleponSopir = root.findViewById(R.id.no_telepon_sopir);
        mEditTextAlamatSopir = root.findViewById(R.id.alamat_sopir);
        mAutomatic = root.findViewById(R.id.radioButton_automatic);
        mManual = root.findViewById(R.id.radioButton_manual);
        mDisel = root.findViewById(R.id.radioButton_disel);
        mBensin = root.findViewById(R.id.radioButton_bensin);
        mImageView = root.findViewById(R.id.image_view);
        mProgressBar = root.findViewById(R.id.progress_bar);
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRefSopir = FirebaseDatabase.getInstance().getReference("sopir");
        mButtonDataSopir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.findViewById(R.id.aksi_tab_mobil).setVisibility(View.GONE);
                root.findViewById(R.id.aksi_tab_sopir).setVisibility(View.VISIBLE);
                mButtonDataMobil.setEnabled(true);
                mButtonDataSopir.setEnabled(false);
                ((AdminActivity) getActivity())
                        .setActionBarTitle("DataSopir Sopir");
            }
        });
        mButtonDataMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.findViewById(R.id.aksi_tab_mobil).setVisibility(View.VISIBLE);
                root.findViewById(R.id.aksi_tab_sopir).setVisibility(View.GONE);
                mButtonDataMobil.setEnabled(false);
                mButtonDataSopir.setEnabled(true);
                ((AdminActivity) getActivity())
                        .setActionBarTitle("DataSopir Mobil");
            }
        });
        mButtonPilihGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });
        mButtonSimpanSopir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    SimpanDataSopir();
            }
        });

        return root;
    }

    public void SimpanDataSopir(){
        DataSopir data = new DataSopir(mEditTextNoKTPSopir.getText().toString().trim(),
                mEditTextNamaSopir.getText().toString().trim(),
                mEditTextNoTeleponSopir.getText().toString().trim(),
                mEditTextAlamatSopir.getText().toString().trim(),
                mStatusKendaraan.trim());
        //String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRefSopir.child(mEditTextNoKTPSopir.getText().toString()).setValue(data);
        Toast.makeText(getContext(), "Simpan successful", Toast.LENGTH_LONG).show();
    }

    public void onResume(){
        super.onResume();

        // Set title bar
        ((AdminActivity) getActivity())
                .setActionBarTitle("DataSopir Mobil");

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(mImageView);
        }

    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile() {
        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_LONG).show();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String transmisi = null,mesin = null;
                                    if(mAutomatic.isChecked()){
                                        transmisi = "Automatic";
                                    } else if(mManual.isChecked()) {
                                        transmisi = "Manual";
                                    }
                                    if(mBensin.isChecked()){
                                        mesin = "Bensin";
                                    } else if(mDisel.isChecked()) {
                                        mesin = "Disel";
                                    }
                                    Upload upload = new Upload(mEditTextMerk.getText().toString().trim(),
                                            mEditTextTipeKendaraan.getText().toString().trim(),
                                            mEditTextNoKendaraan.getText().toString().trim(),
                                            mEditTextTahunProduksi.getText().toString().trim(),
                                            mEditTextUkuranMesin.getText().toString().trim(),
                                            mStatusKendaraan.trim(),
                                            mEditTextHargaSewa.getText().toString().trim(),
                                            transmisi,mesin,
                                            uri.toString());
                                    //String uploadId = mDatabaseRef.push().getKey();
                                    mDatabaseRef.child(mEditTextNoKendaraan.getText().toString()).setValue(upload);
                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(getContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

}