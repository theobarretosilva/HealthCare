package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

public class TelaExercicios_add extends AppCompatActivity {

    EditText nomeExercicio, tempoExercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_exercicios_add);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
    }

    public void iniciarComponentes(){
        nomeExercicio = findViewById(R.id.nomeDoExercicio);
        tempoExercicio = findViewById(R.id.tempoDoExercicio);
    }

    public void mandarExercicioBD(View a){

        if (nomeExercicio.getText().length() < 2){
            nomeExercicio.setError("Preencha este campo corretamente");
        } else if(tempoExercicio.getText().length() != 5){
            tempoExercicio.setError("Preencha este campo corretamente");
        } else {
            String nome = nomeExercicio.getText().toString();
            String duracao = tempoExercicio.getText().toString();

            try {
                Exercicio exercicio = new Exercicio();
                exercicio.setNomeExercicio(nome);
                exercicio.setTempoExercicio(duracao);

                exercicio.salvarExercicio();
                Toast.makeText(this, "Sucesso ao adicionar o exercício", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, TelaExercicios.class));
            } catch (Exception e){
                Toast.makeText(this, "Não foi possível adicionar o exercício", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void voltarTelaExercicios(View a){
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.mover_direita);
        ActivityCompat.startActivity(TelaExercicios_add.this, new Intent(this, TelaExercicios.class), activityOptionsCompat.toBundle());
    }
}