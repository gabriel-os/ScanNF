package com.example.scannf.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.scannf.R;

public class LoginActivity extends AppCompatActivity {
    private Button btnSingUp, btnLogin;
    private TextView txtRecoveryPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Context context;


        btnSingUp = findViewById(R.id.main_btn_sing_up);
        btnLogin = findViewById(R.id.main_btn_login);
        txtRecoveryPass = findViewById(R.id.txt_recovery_pass);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenu();
            }
        });
        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSingUp();
            }
        });
        txtRecoveryPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void openRecovery() {

    }

    public void openSingUp() {
        Intent i = new Intent(this, SingUpActivity.class);
        startActivity(i);
        //TODO intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public void openMenu() {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
    }


}

