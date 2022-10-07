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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TelaExames_ad extends AppCompatActivity {
    EditText clinica, exame, date, horario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_exames_ad);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        clinica = findViewById(R.id.clinica);
        exame = findViewById(R.id.exame);
        date = findViewById(R.id.data);
        horario = findViewById(R.id.horario);

    }

    public void salvarBD(View v){
        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dataHoje = sdf.format(data);

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String novaData = dateFormat.format(date);
        System.out.println(novaData);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> novoExame = new HashMap<>();
        novoExame.put("Clínica", clinica.getText().toString());
        novoExame.put("Exame", exame.getText().toString());
        novoExame.put("Data", novaData ); // Ver se está funcionando
        novoExame.put("Horario", horario.getText().toString());

        try {
            DocumentReference ns = db.collection("Usuarios").document(usuarioID).collection("Informações pessoais").document("Registros").collection("Sono").document(dataHoje);
            ns.set(novoExame);
            Toast.makeText(this, "Sucesso!", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(this, "Não foi possível cadastrar suas informações!", Toast.LENGTH_SHORT).show();
        }
    }
}