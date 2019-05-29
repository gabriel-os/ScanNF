package com.example.scannf.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scannf.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class MenuActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FloatingActionButton btn_reader_code;
    private NavigationView nvLeft;
    private TextView tvName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toolbar = findViewById(R.id.toolbar_menu);
        btn_reader_code = findViewById(R.id.menu_reader_code);
        drawerLayout = findViewById(R.id.drawerLayout);
        nvLeft = findViewById(R.id.navView);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);

        btn_reader_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReader();
            }
        });

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        View header = nvLeft.getHeaderView(0);
        tvName = header.findViewById(R.id.nav_header_name);
        tvName.setText("Ola");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                finish();
            } else {
                showResult(result.getContents(), result.getFormatName());

            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void showResult(final String nf, final String type) {
        Log.v("***", nf);
        Log.v("***", type);
        if (!type.equals("CODE_128")) {
            Toast toast = Toast.makeText(this, "O código lido não parece \n ser uma Nota Fiscal(E01) Tente novamente", Toast.LENGTH_LONG);
            toast.show();
        } else {
            openInfoNF(nf);
        }
    }

    private void openReader() {
        new IntentIntegrator(MenuActivity.this).setCaptureActivity(ScanActivity.class).initiateScan();
        Log.v("***", "Passou here");
    }

    private void openInfoNF(String nf) {
        Intent i = new Intent(this, InfoNFActivity.class);
        i.putExtra("num_nf", nf);
        startActivity(i);
    }
}