package com.example.rr_japung.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.rr_japung.R;
import com.example.rr_japung.model.DataSopir;
import java.util.List;

public class DaftarSopirAdapter extends RecyclerView.Adapter<DaftarSopirAdapter.DaftarSopirViewHolder>{
    private Context mContext;
    private List<DataSopir> mDataSopir;
    private OnItemClickListener mListener;

    public DaftarSopirAdapter(Context context, List<DataSopir> dataSopir) {
        mContext = context;
        mDataSopir = dataSopir;
    }

    @Override
    public DaftarSopirViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_view_daftar_nama_sopir, parent, false);
        return new DaftarSopirViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DaftarSopirViewHolder holder, int position) {
        DataSopir dataSopirCurrent = mDataSopir.get(position);
        holder.textViewNamaSopir.setText(dataSopirCurrent.getNamaSopir());
        holder.textViewNoTeleponSupir.setText(dataSopirCurrent.getNoTeleponSopir());
        holder.textViewBiayaSopir.setText("Rp. 150.000,00");
    }

    @Override
    public int getItemCount() {
        return mDataSopir.size();
    }

    public class DaftarSopirViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
             {
        public TextView textViewNamaSopir,textViewNoTeleponSupir,textViewBiayaSopir;
//        public DaftarSopirView daftarSopirView;

        public DaftarSopirViewHolder(View itemView) {
            super(itemView);

            textViewNamaSopir = itemView.findViewById(R.id.textview_list_nama_sopir);
            textViewNoTeleponSupir = itemView.findViewById(R.id.textview_list_no_telepon_sopir);
            textViewBiayaSopir = itemView.findViewById(R.id.textView_list_biaya_sopir);

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

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
