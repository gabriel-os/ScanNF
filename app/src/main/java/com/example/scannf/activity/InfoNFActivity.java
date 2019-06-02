package com.example.scannf.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scannf.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InfoNFActivity extends AppCompatActivity {
    private TextView txtNumNF, titulo, carrroEntrega;
    private Intent in;
    private View vv;
    private Button btnSave;
    private Toolbar toolbar;
    private String numNf;
    private FirebaseAuth mAuth;
    private Spinner dropdown;
    private RadioGroup rg;
    private RadioButton rbEntregue, rbCancelado, rbRefaturado;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_nf);
        txtNumNF = findViewById(R.id.txt_num_nf);
        toolbar = findViewById(R.id.toolbar_info_nf);
        btnSave = findViewById(R.id.info_nf_save);
        dropdown = findViewById(R.id.spinner);
        rbEntregue = findViewById(R.id.r_entregue);
        rbCancelado = findViewById(R.id.r_cancelado);
        rbRefaturado = findViewById(R.id.r_refaturado);
        vv = findViewById(R.id.view6);
        rg = findViewById(R.id.radioStatus);
        titulo = findViewById(R.id.lbl_titulo_motivo);
        carrroEntrega = findViewById(R.id.txt_carro_entrega);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        mAuth = FirebaseAuth.getInstance();

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

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.r_cancelado) {
                    vv.setVisibility(View.VISIBLE);
                    titulo.setVisibility(View.VISIBLE);
                    dropdown.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.r_refaturado) {
                    vv.setVisibility(View.VISIBLE);
                    titulo.setVisibility(View.VISIBLE);
                    dropdown.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.r_entregue) {
                    vv.setVisibility(View.INVISIBLE);
                    titulo.setVisibility(View.INVISIBLE);
                    dropdown.setVisibility(View.INVISIBLE);
                }
            }
        });
        getCarro();

    }

    public void saveNf() {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat hf = new SimpleDateFormat("HH:mm");
        String uid = mAuth.getCurrentUser().getUid();
        String name = mAuth.getCurrentUser().getDisplayName();
        String carro = carrroEntrega.getText().toString();

        String date = df.format(Calendar.getInstance().getTime());
        String hour = hf.format(Calendar.getInstance().getTime());
        String status = "";
        String motivo = dropdown.getSelectedItem().toString();

        if (rbEntregue.isChecked()) {
            status = "Entregue";
            motivo = "-";
        } else if (rbCancelado.isChecked()) {
            status = "Cancelado";
        } else if (rbRefaturado.isChecked()) {
            status = "Refaturado";
        }

        myRef.child("nota_fiscal/" + uid + "/nome").setValue(name);
        myRef.child("nota_fiscal/" + uid + "/" + numNf + "/carro").setValue(carro);
        myRef.child("nota_fiscal/" + uid + "/" + numNf + "/status").setValue(status);
        myRef.child("nota_fiscal/" + uid + "/" + numNf + "/motivo").setValue(motivo);
        myRef.child("nota_fiscal/" + uid + "/" + numNf + "/date").setValue(date);
        myRef.child("nota_fiscal/" + uid + "/" + numNf + "/time").setValue(hour);

        Toast.makeText(InfoNFActivity.this, "Nota cadastrada com sucesso!!",
                Toast.LENGTH_LONG).show();
        myRef.keepSynced(true);
        backMenu();
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

    public void getCarro() {
        String uid = mAuth.getCurrentUser().getUid();
        Query query;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();


        query = myRef.getDatabase().getReference("nota_fiscal/" + uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (postSnapshot.getKey().equals("ultimoCarro")) {
                        carrroEntrega.setText(postSnapshot.getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}