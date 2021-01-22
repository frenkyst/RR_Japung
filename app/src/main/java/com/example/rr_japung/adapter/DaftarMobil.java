package com.example.rr_japung.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rr_japung.R;
import com.example.rr_japung.model.Upload;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class DaftarMobil extends RecyclerView.Adapter<DaftarMobil.DaftarMobilViewHolder>{
    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListener mListener;

    public DaftarMobil(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public DaftarMobil.DaftarMobilViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_view_daftar_mobil, parent, false);
        return new DaftarMobil.DaftarMobilViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DaftarMobil.DaftarMobilViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewTipeKendaraan.setText(uploadCurrent.getmTipeKendaraan());

        DecimalFormat decim = new DecimalFormat("#,###");
        //tvtotaltransaksi.setText("Rp. "+decim.format(Integer.parseInt(totaltransaksi)));
        holder.textViewHargaSewa.setText("Rp. "+decim.format(Integer.parseInt(uploadCurrent.getmHargaSewa()))+",00");


        holder.textViewMerkKendaraan.setText(uploadCurrent.getmMerk());
        holder.textViewUkuranMesin.setText(uploadCurrent.getmUkuranMesin()+" CC");
        holder.textViewTransmisi.setText(uploadCurrent.getmTransmisi());
        holder.textViewTahunProduksi.setText(uploadCurrent.getmTahunProduksi()+" TH");
        Picasso.get()
                .load(uploadCurrent.getmImageUrl())
                .placeholder(R.drawable.default_image1)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class DaftarMobilViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewTipeKendaraan,textViewHargaSewa,textViewMerkKendaraan,textViewUkuranMesin,textViewTransmisi,textViewTahunProduksi;
        public ImageView imageView;

        public DaftarMobilViewHolder(View itemView) {
            super(itemView);

            textViewTipeKendaraan = itemView.findViewById(R.id.text_tipe_kendaraan);
            textViewHargaSewa = itemView.findViewById(R.id.text_harga_sewa);
            textViewMerkKendaraan = itemView.findViewById(R.id.text_merk_kendaraan);
            textViewUkuranMesin = itemView.findViewById(R.id.text_ukuran_mesin);
            textViewTransmisi = itemView.findViewById(R.id.text_transmisi);
            textViewTahunProduksi = itemView.findViewById(R.id.text_tahun_produksi);

            imageView = itemView.findViewById(R.id.image_view_upload);

            itemView.setOnClickListener(this);
//            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }
//
//        @Override
//        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//            menu.setHeaderTitle("Select Action");
//            MenuItem doWhatever = menu.add(Menu.NONE, 1, 1, "Edit");
//            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");
//
//            doWhatever.setOnMenuItemClickListener(this);
//            delete.setOnMenuItemClickListener(this);
//        }
//
//        @Override
//        public boolean onMenuItemClick(MenuItem item) {
//            if (mListener != null) {
//                int position = getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION) {
//
//                    switch (item.getItemId()) {
//                        case 1:
//                            mListener.onWhatEverClick(position);
//                            return true;
//                        case 2:
//                            mListener.onDeleteClick(position);
//                            return true;
//                    }
//                }
//            }
//            return false;
//        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

//        void onWhatEverClick(int position);
//
//        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(DaftarMobil.OnItemClickListener listener) {
        mListener = listener;
    }
}
