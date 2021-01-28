package com.example.rr_japung.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rr_japung.R;
import com.example.rr_japung.model.Transaksi;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DaftarTransaksi extends RecyclerView.Adapter<DaftarTransaksi.DaftarTransaksiViewHolder>{
    private Context mContext;
    private List<Transaksi> mDataTransaksi;
    private DaftarTransaksi.OnItemClickListener mListener;

    public DaftarTransaksi(Context context, List<Transaksi> dataTransaksi) {
        mContext = context;
        mDataTransaksi = dataTransaksi;
    }

    @Override
    public DaftarTransaksi.DaftarTransaksiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_view_daftar_transaksi, parent, false);
        return new DaftarTransaksi.DaftarTransaksiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DaftarTransaksi.DaftarTransaksiViewHolder holder, int position) {
        Transaksi dataTransaksiCurrent = mDataTransaksi.get(position);
        holder.textViewNamaPemesan.setText(dataTransaksiCurrent.getNamaPemesan());
        holder.textViewTipeKendaraan.setText(dataTransaksiCurrent.getTipeKendaraan());
        holder.textViewMerkKendaraan .setText(dataTransaksiCurrent.getMerkKendaraan());
        holder.textViewNoKendaraan .setText(dataTransaksiCurrent.getNoKendaraan());
        String date = DateFormat.format("dd-MM-yyyy  hh:mm", Long.parseLong(dataTransaksiCurrent.getTanggalPesan())).toString();
        holder.textViewTanggalWaktuPesan .setText(date);
        Picasso.get()
                .load(dataTransaksiCurrent.getFotoKendaraanURL())
                .placeholder(R.drawable.mobildefault)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mDataTransaksi.size();
    }

    public class DaftarTransaksiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView textViewNamaPemesan,textViewTipeKendaraan,textViewMerkKendaraan,textViewNoKendaraan,textViewTanggalWaktuPesan;
        public ImageView imageView;

        public DaftarTransaksiViewHolder(View itemView) {
            super(itemView);

            textViewNamaPemesan = itemView.findViewById(R.id.text_nama_pemesan);
            textViewTipeKendaraan = itemView.findViewById(R.id.text_tipe_kendaraan);
            textViewMerkKendaraan = itemView.findViewById(R.id.text_merk_kendaraan);
            textViewNoKendaraan = itemView.findViewById(R.id.text_no_kendaraan);
            textViewTanggalWaktuPesan = itemView.findViewById(R.id.text_waktu_pinjam);
            imageView = itemView.findViewById(R.id.image_view_upload);

//            daftarSopirView = itemView.findViewById(R.id.image_view_upload);

            itemView.setOnClickListener(this);
//            itemView.setOnCreateContextMenuListener(this);View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener
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
//
//        void onWhatEverClick(int position);
//
//        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(DaftarTransaksi.OnItemClickListener listener) {
        mListener = listener;
    }
}
