package com.example.sensus.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sensus.Fragment.TambahFragment;
import com.example.sensus.model.Penduduk;
import com.example.sensus.R;

import java.util.ArrayList;

public class PendudukAdapter extends RecyclerView.Adapter<PendudukAdapter.PendudukViewHolder> implements Filterable {


    private  Context mContext;
    private  ArrayList<Penduduk> dataList;
    private  ArrayList<Penduduk> semuaData;

    public PendudukAdapter(Context context, ArrayList<Penduduk> arrayList, ArrayList<Penduduk> semuaData) {
        this.semuaData = semuaData;
        this.dataList = arrayList;
        this.mContext = context;


    }

    @NonNull
    @Override
    public PendudukViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_itemnya, parent, false);
        return new PendudukViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendudukViewHolder holder, final int position) {
        int id = dataList.get(position).getId();
        holder.txtNama.setText(dataList.get(position).getName());
        holder.txtPekerjaan.setText(dataList.get(position).getPekerjaan());
        holder.txtNik.setText(String.valueOf(dataList.get(position).getNik()));
        holder.txtKecamatan.setText(dataList.get(position).getKecamatan());

        holder.Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle dataUpdate = new Bundle();
                dataUpdate.putInt("ID", dataList.get(position).getId());
                dataUpdate.putString("NAMA", dataList.get(position).getName());
                dataUpdate.putString("PEKERJAAN", dataList.get(position).getPekerjaan());
                dataUpdate.putInt("NIK", dataList.get(position).getNik());
                dataUpdate.putString("KECAMATAN", dataList.get(position).getKecamatan());

                Fragment tambahFragment = new TambahFragment();
                tambahFragment.setArguments(dataUpdate);

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, tambahFragment)
                        .commit();

            }
        });





    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public Filter getFilter() {


        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString1 = charSequence.toString();
                String charString = charString1.toLowerCase();

                if (charString.isEmpty()) {


                    dataList = semuaData;

                } else {




                    ArrayList<Penduduk> filteredList = new ArrayList<>();

                    for (Penduduk penduduk : dataList) {

                        if (penduduk.getName().toLowerCase().contains(charString) ) {

                            filteredList.add(penduduk);
                        }
                    }

                    dataList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataList;
                return filterResults; }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataList = (ArrayList<Penduduk>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class PendudukViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNama,txtNik,txtPekerjaan,txtKecamatan;
        private ImageButton Update;

        public PendudukViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNama = (TextView) itemView.findViewById(R.id.nama);
            txtPekerjaan = (TextView) itemView.findViewById(R.id.pekerjaan);
            txtNik = (TextView)itemView.findViewById(R.id.nik);
            txtKecamatan = (TextView) itemView.findViewById(R.id.kecamatan);
            Update = (ImageButton) itemView.findViewById(R.id.update_btn);

        }
    }
}
