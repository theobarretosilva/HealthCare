package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TelaExames_add extends AppCompatActivity {

    EditText clinica, exame, date, horario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_exames_add);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
    }

    public void iniciarComponentes(){
        clinica = findViewById(R.id.clinica);
        exame = findViewById(R.id.exame);
        date = findViewById(R.id.data);
        horario = findViewById(R.id.horario);
    }

    public void mandarExamesBD(View g){

        if (clinica.getText().length() == 0){
            clinica.setError("Preencha este campo!");
        } else if (exame.getText().length() == 0) {
            exame.setError("Preencha este campo!");
        } else if (date.getText().length() == 0) {
            date.setError("Preencha este campo!");
        } else if (horario.getText().length() == 0) {
            horario.setError("Preencha este campo!");
        } else {
            String clinicaExame = clinica.getText().toString();
            String tipoExame = exame.getText().toString();
            String dataExame = date.getText().toString();
            String horarioExame = horario.getText().toString();

            try{
                Exame exame = new Exame();
                exame.setClinica(clinicaExame);
                exame.setTipo(tipoExame);
                exame.setData(dataExame);
                exame.setHorario(horarioExame);

                exame.salvarExame();
                Toast.makeText(this, "Sucesso ao adicionar o exame!", Toast.LENGTH_SHORT).show();
                Intent irTelaExames = new Intent(TelaExames_add.this, TelaExames.class);
                startActivity(irTelaExames);
            }catch (Exception e){
                Toast.makeText(this, "Não foi possível adicionar o exame!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void voltarTela(View h){
        Intent voltarTelaExames = new Intent(this, TelaExames.class);
        startActivity(voltarTelaExames);
    }
}
