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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.scannf.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InfoNFActivity extends AppCompatActivity {
    private TextView txtNumNF;
    private Intent in;
    private Button btnSave;
    private Toolbar toolbar;
    private String numNf;
    private Spinner dropdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_nf);
        txtNumNF = findViewById(R.id.txt_num_nf);
        toolbar = findViewById(R.id.toolbar_info_nf);
        btnSave = findViewById(R.id.info_nf_save);
        dropdown = findViewById(R.id.spinner);

        String[] items = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        in = getIntent();
        Bundle extras = in.getExtras();
        numNf = extras.getString("num_nf");
        txtNumNF.setText(numNf);

        adviceRead();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adciveBackMenu();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNf();
            }
        });

    }

    public void saveNf() {
        String[] nf = {"CE002", "29/05/2019"};
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("nota_fiscal/" + numNf + "/carro").setValue("CE002");
        myRef.child("nota_fiscal/" + numNf + "/status").setValue("CE002");
        myRef.child("nota_fiscal/" + numNf + "/motivo").setValue("CE002");
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