package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaPassos extends AppCompatActivity {

    TextView dataAtual;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date data = new Date();
    String dataHoje = sdf.format(data);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_passos);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        dataAtual = findViewById(R.id.dataAtualPassos);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String textoPronto = "\uD83C\uDFC3\uD83C\uDFFD " + dataHoje;

        dataAtual.setText(textoPronto);
    }

    public void voltarTelaConteudos(View g){
        Intent voltarTelaConteudos = new Intent(this, TelaConteudos.class);
        startActivity(voltarTelaConteudos);
    }
}