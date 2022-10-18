package com.example.healthcare;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TelaVinculacaoClinicas extends AppCompatActivity {

    TextView nomeClinica, enderecoClinica, telefoneClinica, servicosClinica;
    ImageView fotoClinica;
    VincularClinicas vincularClinicas;
    TelaClinicas telaClinicas = new TelaClinicas();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_vinculacao_clinicas);

        nomeClinica = findViewById(R.id.nomeClinica);
        enderecoClinica = findViewById(R.id.enderecoClinica);
        telefoneClinica = findViewById(R.id.telefoneClinica);
        servicosClinica = findViewById(R.id.servicosClinicas);
        fotoClinica = findViewById(R.id.fotoClinica);

        getSetClinica();
    }

    public void getSetClinica(){
        nomeClinica.setText(vincularClinicas.getNomeClinica());
        enderecoClinica.setText(vincularClinicas.getEnderecoClinica());
        telefoneClinica.setText(vincularClinicas.getTelefoneClinica());
        servicosClinica.setText(vincularClinicas.getServicosClinica());
        fotoClinica.setImageDrawable(vincularClinicas.getFotoClinica());
    }
}