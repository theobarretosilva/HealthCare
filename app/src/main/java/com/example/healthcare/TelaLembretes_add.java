package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TelaLembretes_add extends AppCompatActivity {

    EditText descriLembrete, dataLembrete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_lembretes_add);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
    }

    public void iniciarComponentes(){
        descriLembrete = findViewById(R.id.descriLembrete);
        dataLembrete = findViewById(R.id.dataLembrete);
    }

    public void mandarLembretesBD(View d){

    }

}