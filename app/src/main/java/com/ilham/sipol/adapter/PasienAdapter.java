package com.ilham.sipol.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ilham.sipol.R;
import com.ilham.sipol.model.PasienModel;


import java.util.List;

public class PasienAdapter extends BaseAdapter {

    Activity activity;
    LayoutInflater inflater;
    List<PasienModel> pasiens;

    public PasienAdapter(Activity activity, List<PasienModel> pasiens) {
        this.activity = activity;
        this.pasiens = pasiens;
    }

    @Override
    public int getCount() {return pasiens.size();}

    @Override
    public Object getItem(int i) {return pasiens.get(i);}

    @Override
    public long getItemId(int i) {return i;}

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null){
            view=inflater.inflate(R.layout.list_pasien,null);
        }
        if (view!=null){
            TextView tvNama=view.findViewById(R.id.tv_nama);
            TextView tvId=view.findViewById(R.id.tv_id);
            TextView tvPoli =view.findViewById(R.id.tv_poli);

            PasienModel pasien = pasiens.get(i);
            tvNama.setText(pasien.get_nama_pasien());
            tvId.setText(pasien.get_id_pasien());
            tvPoli.setText(pasien.get_poli());
        }
        return view;
    }
}
