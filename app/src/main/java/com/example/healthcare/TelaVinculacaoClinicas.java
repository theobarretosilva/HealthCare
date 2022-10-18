package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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
        nomeClinica.setText(telaClinicas.vincularClinicas.getNomeClinica());
        enderecoClinica.setText(telaClinicas.vincularClinicas.getEnderecoClinica());
        telefoneClinica.setText(telaClinicas.vincularClinicas.getTelefoneClinica());
        servicosClinica.setText(telaClinicas.vincularClinicas.getServicosClinica());
        fotoClinica.setImageDrawable(telaClinicas.vincularClinicas.getFotoClinica());
    }
}