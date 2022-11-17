package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        if (descriLembrete.getText().length() == 0) {
            descriLembrete.setError("Preencha este campo!");
        } else if (dataLembrete.getText().length() == 0) {
            dataLembrete.setError("Preencha este campo!");
        } else {
            String descricao = descriLembrete.getText().toString();
            String data = dataLembrete.getText().toString();

            try {
                Lembrete lembrete = new Lembrete();
                lembrete.setNomeLembrete(descricao);
                lembrete.setDataLembrete(data);

                lembrete.salvarLembrete();
                Toast.makeText(this, "Sucesso ao adicionar o lembrete!", Toast.LENGTH_SHORT).show();
                Intent irTelaLembretes = new Intent(this, TelaPerfil_Premium.class);
                startActivity(irTelaLembretes);
            }catch (Exception e){
                Toast.makeText(this, "Não foi possível adicionar o lembrete!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void voltarTelaPerfilP(View d){
        Intent voltarTelaPerfilP = new Intent(this, TelaPerfil_Premium.class);
        startActivity(voltarTelaPerfilP);
    }

}