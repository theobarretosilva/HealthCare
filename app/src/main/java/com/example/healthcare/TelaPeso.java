package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

public class TelaPeso extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_peso);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();
    }
}