package com.example.healthcare;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TelaAlimentacao_add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_alimentacao_add);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();
    }
}