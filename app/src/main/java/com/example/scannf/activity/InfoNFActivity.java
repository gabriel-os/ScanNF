package com.example.scannf.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.scannf.R;

public class InfoNFActivity extends AppCompatActivity {
    private TextView txtNumNF;
    private Intent in;
    private Toolbar toolbar;
    private String numNf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_nf);
        txtNumNF = findViewById(R.id.txt_num_nf);
        toolbar = findViewById(R.id.toolbar_info_nf);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        in = getIntent();
        Bundle extras = in.getExtras();
        numNf = extras.getString("num_nf");
        txtNumNF.setText(numNf);

        adviceRead();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adciveBackMenu();
            }
        });



    }

    public void backMenu() {
        Intent i = new Intent(InfoNFActivity.this, MenuActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        adciveBackMenu();
    }

    public void adciveBackMenu() {
        new AlertDialog.Builder(InfoNFActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Cadastramento de status da Nota Fiscal")
                .setMessage("Você tem certeza que deseja fechar?\n*Perderá a leitura do código atual*")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        backMenu();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void adviceRead() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(400);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        v.vibrate(400);
    }

}