package com.example.scannf.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scannf.R;
import com.example.scannf.dao.NossoAdapter;
import com.example.scannf.dao.NotaFiscal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;


public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FloatingActionButton btn_reader_code;
    private NavigationView nvLeft;
    private TextView tvName;
    private RecyclerView recyclerView;
    private DrawerLayout mDrawerLayout;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toolbar = findViewById(R.id.toolbar_menu);
        btn_reader_code = findViewById(R.id.menu_reader_code);
        drawerLayout = findViewById(R.id.drawerLayout);
        nvLeft = findViewById(R.id.navView);
        recyclerView = findViewById(R.id.rc_notas);
        mDrawerLayout = findViewById(R.id.drawerLayout);
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

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        View header = nvLeft.getHeaderView(0);
        tvName = header.findViewById(R.id.nav_header_name);
        tvName.setText(currentUser.getDisplayName());



        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);

        List<NotaFiscal> nf = new ArrayList<>();


        nf.add(new NotaFiscal("255579/37", "Refaturada", "10", "10:15"));
        nf.add(new NotaFiscal("2545579/37", "Cancelada", "2", "10:15"));
        nf.add(new NotaFiscal("2545579/37", "Entregue", "-", "10:15"));
        nf.add(new NotaFiscal("2545579/37", "Entregue", "-", "10:15"));
        nf.add(new NotaFiscal("2545579/37", "Entregue", "-", "10:15"));
        nf.add(new NotaFiscal("2545579/37", "Entregue", "-", "10:15"));
        nf.add(new NotaFiscal("2545579/37", "Entregue", "-", "10:15"));
        nf.add(new NotaFiscal("2545579/37", "Entregue", "-", "10:15"));

        recyclerView.setAdapter(new NossoAdapter(nf, this));

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layout);

        nvLeft.setNavigationItemSelectedListener(this);

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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_item_one: {
                Log.v("EDIT", "FOOOOOOOOOOOOOOOOOOOOI");
                break;
            }
            case R.id.nav_item_two: {
                adciveLogoff();
            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onBackPressed() {
        adciveLogoff();
    }

    public void adciveLogoff() {
        new AlertDialog.Builder(MenuActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("ScanNF - Sair")
                .setMessage("Você tem certeza que deseja sair?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        backMenu();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void backMenu() {
        Intent i = new Intent(MenuActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }
}