package com.ilham.sipol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ilham.sipol.model.UserModel;
import com.ilham.sipol.services.UserServices;

import java.util.ArrayList;
import java.util.List;

public class RegisterAct extends AppCompatActivity {
    Button btn_simpan;
    private EditText etUsername, etPassword, etNama;

    List<UserModel> users = new ArrayList<>();
    UserServices dbuser = new UserServices(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etNama = findViewById(R.id.et_nama);
        btn_simpan = findViewById(R.id.bt_register);

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan();
            }
        });
    }

    public void simpan() {
        if (etPassword.getText().toString().isEmpty()  ||
                etNama.getText().toString().isEmpty() || etUsername.getText().toString().isEmpty() )   {
            Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
        } else {
            if (!dbuser.isExist(etUsername.getText().toString())) {
                dbuser.register(etUsername.getText().toString(),
                etPassword.getText().toString(),etNama.getText().toString());
//                Intent pel = new Intent(RegisterAct.this, DashboardAct.class);
//                pel.putExtra("nama", etNama.getText().toString()); // Mengirimkan nama ke DashboardAct
//                startActivity(pel);
                Intent intent = new Intent(RegisterAct.this, UserAct.class);
                intent.putExtra("register_success", true);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Username sudah terdaftar!", Toast.LENGTH_SHORT).show();
                etUsername.selectAll();
                etUsername.requestFocus();
            }
        }
    }
}
