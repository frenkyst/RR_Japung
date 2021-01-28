package com.example.rr_japung.ui_admin.akun;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.rr_japung.R;
import com.example.rr_japung.model.Akun;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AkunFragment extends Fragment {

    private TextView EmailAkun,NamaAkun,NoKTPAkun,NoTeleponAkun,AlamatAkun;
    private String UidAkun,PhotoAkun,StatusAkun,EmailAkunNoEdit;
    private ProgressBar mProgressCircle;
    private ImageView mImageView;
    private Button UpdateAkun;
    private DatabaseReference mDatabaseRef,DatabaseRefAkun;
    private  ValueEventListener mDBListenerAkun;
    private String uid;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.admin_ui_fragment_akun, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }
        EmailAkun = root.findViewById(R.id.edit_email_akun);
        NamaAkun = root.findViewById(R.id.edit_nama_akun);
        NoTeleponAkun = root.findViewById(R.id.edit_no_telpon_akun);
        NoKTPAkun = root.findViewById(R.id.edit_no_ktp_akun);
        AlamatAkun = root.findViewById(R.id.edit_alamat_akun);
        mProgressCircle = root.findViewById(R.id.progress_circle);
        mImageView = root.findViewById(R.id.image_view);
        UpdateAkun = root.findViewById(R.id.button_update_akun);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Root/User/"+uid);
        DatabaseRefAkun = FirebaseDatabase.getInstance().getReference("Root/User/");
        mDBListenerAkun = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UidAkun = dataSnapshot.child("uidAkun").getValue(String.class);
                EmailAkunNoEdit = dataSnapshot.child("emailAkun").getValue(String.class);
                EmailAkun.setText(EmailAkunNoEdit);
                NamaAkun.setText(dataSnapshot.child("namaAkun").getValue(String.class));
                NoKTPAkun.setText(dataSnapshot.child("noKTPAkun").getValue(String.class));
                NoTeleponAkun.setText(dataSnapshot.child("noTeleponAkun").getValue(String.class));
                AlamatAkun.setText(dataSnapshot.child("alamatAkun").getValue(String.class));
                PhotoAkun = dataSnapshot.child("photoAkun").getValue(String.class);
                StatusAkun = dataSnapshot.child("statusAkun").getValue(String.class);
                mProgressCircle.setVisibility(View.INVISIBLE);
                Picasso.get()
                        .load(PhotoAkun)
                        .placeholder(R.drawable.mobildefault)
                        .into(mImageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
        UpdateAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Akun akun =  new Akun(UidAkun,
                        EmailAkunNoEdit,
                        NamaAkun.getText().toString(),
                        NoKTPAkun.getText().toString(),
                        NoTeleponAkun.getText().toString(),
                        AlamatAkun.getText().toString(),
                        PhotoAkun,
                        StatusAkun);
                DatabaseRefAkun.child(UidAkun).setValue(akun);
                Toast.makeText(getContext(), "  DataSopir Telah Diupdate  ", Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }
}