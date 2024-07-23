package com.ilham.sipol;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.ilham.sipol.adapter.PoliAdapter;
import com.ilham.sipol.model.PoliModel;
import com.ilham.sipol.services.PoliServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PoliListAct extends AppCompatActivity {
    ListView listView;
    AlertDialog.Builder dialog;
    List<PoliModel> polis =new ArrayList<>();

    PoliAdapter poliAdp;
    PoliServices db=new PoliServices(this);
    Button btnTambah;
    TextInputEditText tiCari;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poli_list);
        listView=findViewById(R.id.lv_poli);
        btnTambah=findViewById(R.id.bt_Tambah);
        tiCari=findViewById(R.id.tie_Cari);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent poli =new Intent(PoliListAct.this, PoliAct.class);
                startActivity(poli);
            }
        });

        tiCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getData(tiCari.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        poliAdp =new PoliAdapter(PoliListAct.this, polis);
        listView.setDivider(null);
        listView.setAdapter(poliAdp);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                           long id) {
                final String kode= polis.get(position).get_id_poli();
                final String nama= polis.get(position).get_nama_poli();


                final CharSequence[] dialogItem={"Ubah","Hapus","Copy"};
                dialog=new AlertDialog.Builder(PoliListAct.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent poli =new Intent(PoliListAct.this, PoliAct.class);
                                poli.putExtra("id_poli",kode);
                                poli.putExtra("nama_poli",nama);
                                startActivity(poli);
                                break;
                            case 1:
                                konfirmHapus(kode);
                                break;

                            case 2:
                                CopyToClipBoard(kode);
                                tiCari.setText("");
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });
    }


    protected void onResume() {
        super.onResume();
        polis.clear();

        if (tiCari.getText().equals("")){
            getData();
        }
        else {
            getData(tiCari.getText().toString());
        }
    }

    public void getData(){
        ArrayList<HashMap<String,String>> daftar=db.getAll();

        polis.clear();
        for (int i=0;i<daftar.size();i++){
            String kode=daftar.get(i).get("id_poli");
            String nama=daftar.get(i).get("nama_poli");

            PoliModel poli =new PoliModel();
            poli.set_id_poli(kode);
            poli.set_nama_poli(nama);
            polis.add(poli);
        }
        poliAdp.notifyDataSetChanged();
    }
    public void getData(String namaCari){
        ArrayList<HashMap<String,String>> daftar=db.getAllByNama(namaCari);
        polis.clear();
        for (int i=0;i<daftar.size();i++){
            String kode=daftar.get(i).get("id_poli");
            String nama=daftar.get(i).get("nama_poli");

            PoliModel poli =new PoliModel();
            poli.set_id_poli(kode);
            poli.set_nama_poli(nama);
            polis.add(poli);
        }
        poliAdp.notifyDataSetChanged();
    }

    private void CopyToClipBoard(String data) {
        String ambilData = data;
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(
                Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("copy data", ambilData);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "ID Poli di copy", Toast.LENGTH_SHORT).show();
    }

    private void konfirmHapus(String kode){
        AlertDialog.Builder alert=new AlertDialog.Builder(this);

        alert.setTitle("konfirmasi Hapus")
                .setMessage("Yakin data akan dihapus?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete(kode);
                        polis.clear();
                        getData();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false);
        alert.show();
    }

}
