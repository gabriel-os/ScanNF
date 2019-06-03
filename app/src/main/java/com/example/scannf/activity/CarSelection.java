package com.example.scannf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.scannf.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CarSelection extends AppCompatActivity {
    private Button btnSave;
    private Toolbar toolbar;
    private Spinner dropdown;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedIstanceState) {
        super.onCreate(savedIstanceState);
        setContentView(R.layout.activity_select_car);

        toolbar = findViewById(R.id.toolbar_menu);
        btnSave = findViewById(R.id.btn_select_car);
        dropdown = findViewById(R.id.sp_car_selection);

        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        String[] items = new String[]{"C410600",
                "C410601",
                "C410602",
                "C410603",
                "C410604",
                "C410605",
                "C410635",
                "C410637",
                "C410638",
                "C410639",
                "C410640",
                "C410641",
                "C410642",
                "C410643",
                "C410644",
                "C410645",
                "C410646",
                "C410647",
                "C410648",
                "C410649",
                "C410650",
                "C410651",
                "C410652",
                "C410653",
                "C410654",
                "C410655",
                "C410656",
                "C410657",
                "C410670",
                "C410671",
                "C410672",
                "C410673",
                "C410674",
                "C410675",
                "C410676",
                "C410677",
                "C410678",
                "C410679",
                "C410680",
                "C410681",
                "C410682",
                "C410683",
                "C410684",
                "C410685",
                "C410686",
                "C410687",
                "C410688",
                "C410700",
                "C410701",
                "C410702",
                "C410703",
                "C410704",
                "C410705",
                "C410706",
                "C410707",
                "C410708",
                "C410709",
                "C410710",
                "C410711",
                "C410902",
                "C410912"};

        Bundle extras = getIntent().getExtras();
        if (extras.getString("edit").equals("true")) {
            btnSave.setText("Editar");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCar();
            }
        });

    }

    public void saveCar() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat hf = new SimpleDateFormat("HH:mm");
        String uid = mAuth.getCurrentUser().getUid();
        String name = mAuth.getCurrentUser().getDisplayName();
        String carro = dropdown.getSelectedItem().toString();
        String date = df.format(Calendar.getInstance().getTime());
        String hour = hf.format(Calendar.getInstance().getTime());


        myRef.child("nota_fiscal/" + uid + "/nome").setValue(name);
        myRef.child("nota_fiscal/" + uid + "/ultimoCarro").setValue(carro);
        myRef.child("nota_fiscal/" + uid + "/diaModificado").setValue(date);
        myRef.child("nota_fiscal/" + uid + "/horaModificado").setValue(hour);


        Toast.makeText(CarSelection.this, "Carro cadastrado com sucesso!!",
                Toast.LENGTH_LONG).show();
        myRef.keepSynced(true);
        backMenu();

    }

    public void backMenu() {
        Intent i = new Intent(CarSelection.this, MenuActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
