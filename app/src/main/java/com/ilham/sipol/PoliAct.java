package com.ilham.sipol;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.ilham.sipol.services.PoliServices;

public class PoliAct extends AppCompatActivity {


    private TextInputEditText inpId, inpNamaPoli;

    private Button btnSimpan;
    private PoliServices db=new PoliServices(this);
    String kd,nm;

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poli_lay);

        inpId=findViewById(R.id.tie_Id);
        inpNamaPoli =findViewById(R.id.tie_Nama);;
        btnSimpan=findViewById(R.id.bt_Simpan);

        kd=getIntent().getStringExtra("id_poli");
        nm=getIntent().getStringExtra("nama_poli");


        if (kd==null || kd.equals("")){
            setTitle("Tambah Poli");
            inpId.requestFocus();
        }else {
            setTitle("Ubah Poli");
            inpId.setText(kd);
            inpNamaPoli.setText(nm);
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kd==null|| kd.equals("")){
                    simpan();
                }else {
                    ubah(kd);
                }
            }
        });

    }

    public  void simpan(){
        if (inpId.getText().equals("")|| inpNamaPoli.getText().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Silahkan isi semua data!",
                    Toast.LENGTH_SHORT).show();
        }else {
            if (!db.isExists(inpId.getText().toString())){
                db.insert(inpId.getText().toString(), inpNamaPoli.getText().toString());
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "ID Poli sudah terdaftar",
                        Toast.LENGTH_SHORT).show();
                inpId.selectAll();
                inpId.requestFocus();
            }
        }
    }

    public void ubah(String kode){
        if (inpId.getText().equals("")|| inpNamaPoli.getText().equals("")){
            Toast.makeText(getApplicationContext(),"Silahkan isi semua data!",
                    Toast.LENGTH_SHORT).show();
        }else {
            if (db.isExists(kd)){
                db.update(inpId.getText().toString(), inpNamaPoli.getText().toString(),kode);
                finish();
            }else {
                Toast.makeText(getApplicationContext(), "ID Poli tidak terdaftar",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }






}
