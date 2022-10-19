package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TelaSono_add extends AppCompatActivity {

    EditText horaDormi, horaAcordei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_sono_add);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        horaDormi = findViewById(R.id.horaDormi);
        horaAcordei = findViewById(R.id.horaAcordei);
    }

    public void verificarPreenchimento(View g){
        if (horaDormi.length() == 0 || horaDormi.length() == 1 || horaDormi.length() == 2 || horaDormi.length() == 3 || horaDormi.length() == 4){
            horaDormi.setError("Preencha este campo corretamente");
        }else if (horaAcordei.length() == 0 || horaAcordei.length() == 1 || horaAcordei.length() == 2 || horaAcordei.length() == 3 || horaAcordei.length() == 4){
            horaAcordei.setError("Preencha este campo corretamente");
        }else{
            verificarHorario();
        }
    }

    public void verificarHorario(){
        String horariosDurmo = horaDormi.getText().toString();
        String[] durmoHM = horariosDurmo.split(":");
        int horasDurmo = Integer.parseInt( durmoHM[0]);
        int minutosDurmo = Integer.parseInt( durmoHM[1] );

        String horaAcorda = horaAcordei.getText().toString();
        String[] acordoHM = horaAcorda.split(":");
        int horasAcordo = Integer.parseInt( acordoHM[0] );
        int minutosAcordo = Integer.parseInt( acordoHM[1] );

        if (horasDurmo > 24 || minutosDurmo > 59){
            horaDormi.setError("Hora inválida!");
        }else if(horasAcordo > 24 || minutosAcordo > 59){
            horaAcordei.setError("Hora inválida");
        }else{
            mandarBD();
        }
    }

    public void mandarBD(){
        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dataHoje = sdf.format(data);

        String horarioDeDurmida = horaDormi.getText().toString();
        String horarioAcordou = horaAcordei.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> sonoAdd = new HashMap<>();
        sonoAdd.put("Horário que dormiu", horarioDeDurmida);
        sonoAdd.put("Horário que acordou", horarioAcordou);

        try {
            DocumentReference ns = db.collection("Usuarios").document(usuarioID).collection("Informações pessoais").document("Registros").collection("Sono").document(dataHoje);
            ns.set(sonoAdd);
            Toast.makeText(this, "Sucesso!", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(this, "Não foi possível cadastrar suas informações!", Toast.LENGTH_SHORT).show();
        }


    }
}