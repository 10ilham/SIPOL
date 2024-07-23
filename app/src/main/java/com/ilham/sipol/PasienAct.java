package com.ilham.sipol;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.ilham.sipol.services.PasienServices;
import com.ilham.sipol.services.PoliServices;

import java.util.ArrayList;
import java.util.List;

public class PasienAct extends AppCompatActivity {
    private TextInputEditText inpId, inpNama, inpUsia;
    private AutoCompleteTextView inpPoli, inpGol_darah;
    private RadioGroup jnsKelamin;
    private RadioButton LakiLaki,Perempuan;
    private Button btnSimpan;
    private PoliServices poliser =new PoliServices(this);
    private PasienServices db=new PasienServices(this);
    String kd,nm,jk,us,ps,gd;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pasien_lay);

        inpId = findViewById(R.id.tie_Id);
        inpNama = findViewById(R.id.tie_Nama);
        inpUsia = findViewById(R.id.tie_Usia);
        inpPoli = findViewById(R.id.tie_Poli);
        inpGol_darah = findViewById(R.id.tie_Gol_darah);
        jnsKelamin = (RadioGroup) findViewById(R.id.rg_jenis);
        LakiLaki = (RadioButton) findViewById(R.id.rb_laki);
        Perempuan= (RadioButton) findViewById(R.id.rb_perempuan);
        btnSimpan = findViewById(R.id.bt_Simpan);

        kd=getIntent().getStringExtra("id_pasien");
        nm=getIntent().getStringExtra("nama_pasien");
        jk=getIntent().getStringExtra("jk");
        us=getIntent().getStringExtra("usia");
        ps=getIntent().getStringExtra("poli");
        gd=getIntent().getStringExtra("gol_darah");

        if (kd==null || kd.equals("")){
            setTitle("Tambah Pasien");
            inpId.requestFocus();
        }else {
            setTitle("Ubah Pasien");
            inpId.setText(kd);
            inpNama.setText(nm);
            inpUsia.setText(us);
            inpPoli.setText(ps);
            inpGol_darah.setText(gd);

            if (jk != null){
                if(jk.equalsIgnoreCase("Laki-laki")){
                    LakiLaki.setChecked(true);
                } else if (jk.equalsIgnoreCase("Perempuan")) {
                    Perempuan.setChecked(true);
                }
            }
        }
        isiGol_darah();
        isiJenis();
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

    public void simpan() {
        String jnsK="";
        String idInput = inpId.getText().toString();
        String namaInput = inpNama.getText().toString();
        String usiaInput = inpUsia.getText().toString();
        String poliInput = inpPoli.getText().toString();
        String gol_darahInput = inpGol_darah.getText().toString();

        int selectedId = jnsKelamin.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(getApplicationContext(), "Silahkan pilih jenis kelamin!", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton selectedRadioButton = findViewById(selectedId);
        String jenisKelamin = selectedRadioButton.getText().toString();

        if (jenisKelamin.equalsIgnoreCase("Laki-laki")) {
            jnsK = "Laki-laki";
        } else if (jenisKelamin.equalsIgnoreCase("Perempuan")) {
            jnsK = "Perempuan";
        }

        if (inpId.getText().toString().isEmpty()  ||
                inpNama.getText().toString().isEmpty()|| jenisKelamin.isEmpty() || usiaInput.isEmpty() || poliInput.isEmpty() || gol_darahInput.isEmpty()
        ) {
            Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
        } else {
            if (!db.isExists(inpId.getText().toString())) {
                db.insert(idInput, namaInput,jnsK,usiaInput,poliInput,gol_darahInput);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "ID Poli sudah terdaftar!", Toast.LENGTH_SHORT).show();
                inpId.selectAll();
                inpId.requestFocus();
            }
        }
    }

    public void isiJenis(){

        List<String> poli = poliser.getDataPoli();

        ArrayAdapter<String> Combo = new ArrayAdapter<>(PasienAct.this, android.R.layout.simple_spinner_dropdown_item, poli);
        inpPoli.setAdapter(Combo);
    }

    public void ubah(String kode){
        String jnsK="";
        String idInput = inpId.getText().toString();
        String namaInput = inpNama.getText().toString();
        String usiaInput = inpUsia.getText().toString();
        String poliInput = inpPoli.getText().toString();
        String gol_darahInput = inpGol_darah.getText().toString();

        int selectedId = jnsKelamin.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(getApplicationContext(), "Silahkan pilih jenis kelamin!", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton selectedRadioButton = findViewById(selectedId);
        String jenisKelamin = selectedRadioButton.getText().toString();

        if (jenisKelamin.equalsIgnoreCase("Laki-laki")) {
            jnsK = "Laki-laki";
        } else if (jenisKelamin.equalsIgnoreCase("Perempuan")) {
            jnsK = "Perempuan";
        }

        if (inpId.getText().toString().isEmpty() ||
                inpNama.getText().toString().isEmpty() ||
                jenisKelamin.isEmpty() ||
                usiaInput.isEmpty() ||
                poliInput.isEmpty() ||
                gol_darahInput.isEmpty()
        ){
            Toast.makeText(getApplicationContext(), "Silahkan isi semua data", Toast.LENGTH_SHORT).show();
        }else {
            if (db.isExists(idInput)){
                db.update(idInput, namaInput, jnsK, usiaInput, poliInput, gol_darahInput,kd);
                finish();
            }else {
                Toast.makeText(getApplicationContext(), "ID pasien tidak terdaftar", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void isiGol_darah() {
        List<String> gol_darah = new ArrayList<>();

        gol_darah.add("O+");
        gol_darah.add("A+");
        gol_darah.add("B+");
        gol_darah.add("AB+");
        gol_darah.add("O-");
        gol_darah.add("A-");
        gol_darah.add("B-");
        gol_darah.add("AB-");

        ArrayAdapter<String> combo = new ArrayAdapter<>(PasienAct.this,
                android.R.layout.simple_spinner_dropdown_item, gol_darah);
        inpGol_darah.setAdapter(combo);
    }

}
