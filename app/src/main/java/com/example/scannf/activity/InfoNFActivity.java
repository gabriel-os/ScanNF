package com.example.scannf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.scannf.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class InfoNFActivity extends AppCompatActivity {
    private TextView txtNumNF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_nf);
        txtNumNF = findViewById(R.id.txt_num_nf);

        new IntentIntegrator(InfoNFActivity.this).setCaptureActivity(ScanActivity.class).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                finish();
            } else {
                //show dialogue with result
                if (result.getFormatName() == "CODE_128") {
                    showResultDialogue(result.getContents());
                }
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void showResultDialogue(final String result) {
        System.out.println(result);
        txtNumNF.setText(result);
    }

}