package com.ilham.sipol.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ilham.sipol.R;
import com.ilham.sipol.model.PoliModel;

import java.util.List;

public class PoliAdapter extends BaseAdapter {
    Activity activity;
    LayoutInflater inflater;
    List<PoliModel> polis;

    public PoliAdapter(Activity activity, List<PoliModel> polis) {
        this.activity = activity;
        this.polis = polis;
    }

    @Override
    public int getCount() {return polis.size();}

    @Override
    public Object getItem(int i) {return polis.get(i);}

    @Override
    public long getItemId(int i) {return i;}

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null){
            view=inflater.inflate(R.layout.list_poli,null);
        }
        if (view!=null){
            TextView tvNama=view.findViewById(R.id.tv_namaPoli);
            TextView tvId=view.findViewById(R.id.tv_idPoli);

            PoliModel poli = polis.get(i);
            tvNama.setText(poli.get_nama_poli());
            tvId.setText(poli.get_id_poli());
        }
        return view;

    }
}
