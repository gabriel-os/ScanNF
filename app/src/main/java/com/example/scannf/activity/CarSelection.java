package com.example.scannf.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.scannf.R;

public class CarSelection extends AppCompatActivity {
    private Spinner sp_cars;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedIstanceState) {
        super.onCreate(savedIstanceState);
        setContentView(R.layout.activity_select_car);

        sp_cars = findViewById(R.id.sp_car_selection);
        btnSave = findViewById(R.id.btn_select_car);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
