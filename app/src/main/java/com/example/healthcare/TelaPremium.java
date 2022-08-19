package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

public class TelaPremium extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_premium);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();
    }

    @Override
    public void onBackPressed()
    {
        Intent voltarTelaConteudos = new Intent(this, TelaConteudos.class);
        startActivity(voltarTelaConteudos);
    }
}