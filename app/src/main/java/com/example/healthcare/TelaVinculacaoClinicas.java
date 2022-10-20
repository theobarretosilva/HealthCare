package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TelaVinculacaoClinicas extends AppCompatActivity {

    TextView nomeClinica, enderecoClinica, telefoneClinica, servicosClinica;
    ImageView fotoClinica;

    TelaClinicas telaClinicas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_vinculacao_clinicas);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
        getSetClinica();
    }

    public void iniciarComponentes(){
        nomeClinica = findViewById(R.id.nomeClinica);
        enderecoClinica = findViewById(R.id.enderecoClinica);
        telefoneClinica = findViewById(R.id.telefoneClinica);
        servicosClinica = findViewById(R.id.servicosClinicas);
        fotoClinica = findViewById(R.id.fotoClinica);
    }

    public void getSetClinica(){
        System.out.println(telaClinicas.lClinicas);
        for (VincularClinicas c : telaClinicas.lClinicas){
            nomeClinica.setText(c.getNomeClinica());
            enderecoClinica.setText(c.getEnderecoClinica());
            telefoneClinica.setText(c.getTelefoneClinica());
            servicosClinica.setText(c.getServicosClinica());
            fotoClinica.setImageDrawable(c.getFotoClinica());
        }
    }

    public void voltarTela(View g){
        Intent voltarTela = new Intent(this, TelaClinicas.class);
        startActivity(voltarTela);
    }
}