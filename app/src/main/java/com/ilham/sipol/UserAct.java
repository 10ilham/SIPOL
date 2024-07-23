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

public class UserAct extends AppCompatActivity {
    private EditText inpUsername, inpPassword;
    List<UserModel> users = new ArrayList<>();
    UserServices dbauth = new UserServices(this);
    Button btn_login, btn_register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        inpUsername = findViewById(R.id.et_username);
        inpPassword = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.bt_login);
        btn_register = findViewById(R.id.bt_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (inpPassword.getText().toString().isEmpty()  ||
                            inpUsername.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (dbauth.login(inpUsername.getText().toString(),inpPassword.getText().toString())) {
                            String fullName = dbauth.getFullName(inpUsername.getText().toString());
                            Intent pel=new Intent(UserAct.this,DashboardAct.class);
                            pel.putExtra("nama", fullName);
                            startActivity(pel);
                        }else {
                            Toast.makeText(getApplicationContext(), "Username atau Password salah", Toast.LENGTH_SHORT).show();
                            inpUsername.selectAll();
                            inpUsername.requestFocus();
                        }
                    }
                }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regis=new Intent(UserAct.this, RegisterAct.class);
                startActivity(regis);
            }
        });
        // Cek apakah ada pesan notifikasi dari RegisterAct
        Intent intent = getIntent();
        if (intent.getBooleanExtra("register_success", false)){
            Toast.makeText(getApplicationContext(), "Registrasi berhasil!", Toast.LENGTH_SHORT).show();
        }
    }
}
