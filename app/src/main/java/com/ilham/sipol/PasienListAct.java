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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.ilham.sipol.adapter.PasienAdapter;
import com.ilham.sipol.model.PasienModel;
import com.ilham.sipol.services.PasienServices;
import com.ilham.sipol.services.PoliServices;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PasienListAct extends AppCompatActivity {
    ListView listView;
    AlertDialog.Builder dialog;
    List<PasienModel> pasiens =new ArrayList<>();

    PasienAdapter pasienAdp;
    PasienServices db=new PasienServices(this);
    PoliServices dbpoli=new PoliServices(this);
    Button btnTambah;
    TextInputEditText tiCari;
    TextView tvJumlahLaki_laki, tvJumlahPerempuan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pasien_list);
        btnTambah=findViewById(R.id.bt_Tambah);
        tiCari=findViewById(R.id.tie_cari);
        listView=findViewById(R.id.lv_pasien);
        tvJumlahLaki_laki=findViewById(R.id.jumlah_laki_laki);
        tvJumlahPerempuan=findViewById(R.id.jumlah_perempuan);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent psn =new Intent(PasienListAct.this, PasienAct.class);
                startActivity(psn);
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
        pasienAdp =new PasienAdapter(PasienListAct.this, pasiens);
        listView.setDivider(null);
        listView.setAdapter(pasienAdp);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                           long id) {
                final String kode= pasiens.get(position).get_id_pasien();
                final String nama= pasiens.get(position).get_nama_pasien();
                final String jk= pasiens.get(position).get_jk();
                final String usia = pasiens.get(position).get_usia();
                final String poli = pasiens.get(position).get_poli();
                final String gol_darah = pasiens.get(position).get_gol_darah();

                final CharSequence[] dialogItem={"Ubah", "Hapus", "Copy"};
                dialog=new AlertDialog.Builder(PasienListAct.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent pasien =new Intent(PasienListAct.this, PasienAct.class);
                                pasien.putExtra("id_pasien",kode);
                                pasien.putExtra("nama_pasien",nama);
                                pasien.putExtra("jk",jk);
                                pasien.putExtra("usia",usia);
                                pasien.putExtra("poli",poli);
                                pasien.putExtra("gol_darah",gol_darah);
                                startActivity(pasien);
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
        pasiens.clear();

        if (tiCari.getText().equals("")){
            getData();
        }
        else {
            getData(tiCari.getText().toString());
        }
    }
    public void getData() {
        ArrayList<HashMap<String, String>> daftar = db.getAll();

        pasiens.clear();
        for (int i = 0; i < daftar.size(); i++) {
            String kode = daftar.get(i).get("id_pasien");
            String nama = daftar.get(i).get("nama_pasien");
            String jk=daftar.get(i).get("jk");
            String usia=daftar.get(i).get("usia");
            String poli =daftar.get(i).get("poli");
            String gol_darah =daftar.get(i).get("gol_darah");

            PasienModel pasien = new PasienModel();
            pasien.set_id_pasien(kode);
            pasien.set_nama_pasien(nama);
            pasien.set_jk(jk);
            pasien.set_usia(usia);
            pasien.set_poli(poli);
            pasien.set_gol_darah(gol_darah);
            pasiens.add(pasien);
        }
        pasienAdp.notifyDataSetChanged();
        updateGenderCounts();
    }

    public void getData(String namaCari){
        ArrayList<HashMap<String,String>> daftar=db.getAllByNama(namaCari);

        pasiens.clear();
        for (int i=0;i<daftar.size();i++){
            String kode = daftar.get(i).get("id_pasien");
            String nama = daftar.get(i).get("nama_pasien");
            String jk=daftar.get(i).get("jk");
            String usia = daftar.get(i).get("usia");
            String poli =daftar.get(i).get("poli");
            String gol_darah =daftar.get(i).get("gol_darah");

            PasienModel pasien = new PasienModel();
            pasien.set_id_pasien(kode);
            pasien.set_nama_pasien(nama);
            pasien.set_jk(jk);
            pasien.set_usia(usia);
            pasien.set_poli(poli);
            pasien.set_gol_darah(gol_darah);
            pasiens.add(pasien);
        }
        pasienAdp.notifyDataSetChanged();
        updateGenderCounts();;
    }

    private void CopyToClipBoard(String data) {
        String ambilData = data;
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(
                Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("copy data", ambilData);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "ID Pasien di copy", Toast.LENGTH_SHORT).show();
    }
    private void konfirmHapus(String kode){
        AlertDialog.Builder alert=new AlertDialog.Builder(this);

        alert.setTitle("konfirmasi Hapus")
                .setMessage("Yakin data akan dihapus?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete(kode);
                        pasiens.clear();
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

    private void updateGenderCounts() {
        int jumlahLaki_laki = 0;
        int jumlahPerempuan = 0;

        for (PasienModel pasien : pasiens) {
            if (pasien.get_jk().equalsIgnoreCase("Laki-laki")) {
                jumlahLaki_laki++;
            } else if (pasien.get_jk().equalsIgnoreCase("Perempuan")) {
                jumlahPerempuan++;
            }
        }

        tvJumlahLaki_laki.setText(String.valueOf(jumlahLaki_laki));
        tvJumlahPerempuan.setText(String.valueOf(jumlahPerempuan));
    }
}
