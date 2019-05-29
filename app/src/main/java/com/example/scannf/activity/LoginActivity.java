package com.example.scannf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.scannf.R;

public class LoginActivity extends AppCompatActivity {
    private Button btnSingUp, btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSingUp = findViewById(R.id.main_btn_sing_up);
        btnLogin = findViewById(R.id.main_btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenu();
            }
        });

        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });
    }

    public void openActivity() {
        Intent i = new Intent(this, SingUpActivity.class);
        startActivity(i);
        //TODO intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public void openMenu() {


        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
    }


}

