package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaExercicios extends AppCompatActivity {

    TextView dataAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_exercicios);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        dataAtual = findViewById(R.id.dataAtualExercicios);

        setarData();
    }

    public void setarData(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        String dataHoje = sdf.format(data);

        String dataCEmoji = "\uD83C\uDFCB️\u200D♀️ " + dataHoje;

        dataAtual.setText(dataCEmoji);
    }

    public void irTelaAddExercicio(View f){
        Intent irTelaAddExercicios = new Intent(this, TelaExercicios_add.class);
        startActivity(irTelaAddExercicios);
    }
}