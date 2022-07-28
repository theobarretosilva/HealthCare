package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

public class TelaLogo extends AppCompatActivity implements Runnable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_logo);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        Handler handler = new Handler();
        handler.postDelayed(this, 1400);
    }

    @Override
    public void run() {

        Intent irTelaInicial = new Intent(this, TelaInicial.class);
        startActivity(irTelaInicial);

    }
}