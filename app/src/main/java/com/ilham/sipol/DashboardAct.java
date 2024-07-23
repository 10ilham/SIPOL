package com.ilham.sipol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardAct extends AppCompatActivity {

    ImageButton imgbtn_poli, imgbtn_pasien;
    String nama;
    TextView txt_nama;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        nama = getIntent().getStringExtra("nama");

        imgbtn_poli = (ImageButton) findViewById(R.id.ib_poli);
        imgbtn_pasien = (ImageButton) findViewById(R.id.ib_pasien);
        txt_nama = (TextView) findViewById(R.id.nama);
        txt_nama.setText(nama);

        imgbtn_poli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pel=new Intent(DashboardAct.this, PoliListAct.class);
                startActivity(pel);
            }
        });

        imgbtn_pasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pel=new Intent(DashboardAct.this, PasienListAct.class);
                startActivity(pel);
            }
        });
    }
}
