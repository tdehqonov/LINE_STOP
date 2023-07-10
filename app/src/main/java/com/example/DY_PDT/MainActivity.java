package com.example.DY_PDT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnViewInformation, btnScanBarcode,btnScanAvtombil,btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        btnViewInformation = (Button) findViewById(R.id.btnViewInformation);
        btnScanBarcode = (Button) findViewById(R.id.btnScanBarcode);
        btnScanAvtombil=findViewById(R.id.btnScanAvtombil);
        btnExit=findViewById(R.id.btnExit);

        btnViewInformation.setOnClickListener(this);
        btnScanBarcode.setOnClickListener(this);
        btnScanAvtombil.setOnClickListener(this);
        btnExit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btnViewInformation:
//                startActivity(new Intent(MainActivity.this, Korish.class));
//                break;

//            case R.id.btnScanAvtombil:
//                startActivity(new Intent(MainActivity.this, activity_scan_avtomobil.class));
//                break;

            case R.id.btnExit:
                finish();
                break;
        }
    }
}


