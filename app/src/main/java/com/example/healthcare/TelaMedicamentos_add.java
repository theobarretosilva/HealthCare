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

public class TelaMedicamentos_add extends AppCompatActivity {

    EditText nome, qtd, mgDosagem, horario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_medicamentos_add);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
    }

    public void iniciarComponentes(){
        nome = findViewById(R.id.nomeDoMedicamento);
        qtd = findViewById(R.id.quantidadeDoMedicamento);
        mgDosagem = findViewById(R.id.mgDosagemDoMedicamento);
        horario = findViewById(R.id.horarioDoMedicamento);
    }

    public void mandarMedicamentosBD(View a){
        if (nome.getText().length() < 2){
            nome.setError("Preencha o nome corretamente");
        } else if (qtd.getText().length() == 0){
            qtd.setError("Preencha este campo");
        } else if (mgDosagem.getText().length() == 0){
            mgDosagem.setError("Preencha este campo");
        } else if (horario.getText().length() == 0){
            horario.setError("Preencha este campo");
        } else if (horario.getText().toString().contains("_")){
            horario.setError("Preencha este campo corretamente");
        } else {
            String nomeRemedio = nome.getText().toString();
            String quantidadeRemedio = qtd.getText().toString();
            String mgDosagemRemedio = mgDosagem.getText().toString();
            String horarioRemedio = horario.getText().toString();

            try {
                Medicamento medicamento = new Medicamento();
                medicamento.setNomeMedicamento(nomeRemedio);
                medicamento.setQtdMedicamento(quantidadeRemedio);
                medicamento.setMgDosagemMedicamento(mgDosagemRemedio);
                medicamento.setHorarioMedicamento(horarioRemedio);
                
                medicamento.salvarMedicamento();
                Toast.makeText(this, "Sucesso ao adicionar o medicamento!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(TelaMedicamentos_add.this, TelaMedicamentos.class));
            } catch (Exception e){
                Toast.makeText(this, "Não foi possível adicionar o medicamento!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void voltarTela(View a){
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.mover_direita);
        ActivityCompat.startActivity(TelaMedicamentos_add.this, new Intent(this, TelaMedicamentos.class), activityOptionsCompat.toBundle());

    }
}