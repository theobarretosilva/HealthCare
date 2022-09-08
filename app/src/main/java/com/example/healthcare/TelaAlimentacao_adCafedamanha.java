package com.example.healthcare;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class TelaAlimentacao_adCafedamanha extends AppCompatActivity{

    EditText nomeAlimento, kcalAlimento, gAlimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_alimentacao_ad_cafedamanha);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        nomeAlimento = findViewById(R.id.alimentoCafeManha);
        kcalAlimento = findViewById(R.id.kcalCafeManha);
        gAlimento = findViewById(R.id.gCafeManha);
    }

    public void verificarPreenchimento(View t){
        if (nomeAlimento.length() == 0) {
            nomeAlimento.setError("Preencha esse campo");
        } else if (kcalAlimento.length() == 0) {
            kcalAlimento.setError("Preencha esse campo");
        } else if (gAlimento.length() == 0) {
            gAlimento.setError("Preencha esse campo");
        } else {
            mandarParaBD();
        }
    }

    public void mandarParaBD(){
        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dataHoje = sdf.format(data);

        String alimento = nomeAlimento.getText().toString();
        int kcal = parseInt(kcalAlimento.getText().toString());
        int g = parseInt(gAlimento.getText().toString());

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> cafeDaManha = new HashMap<>();
        cafeDaManha.put("Nome do alimento", alimento);
        cafeDaManha.put("Calorias por porção", kcal);
        cafeDaManha.put("Quantidade por porção", g);

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID).collection("Informações pessoais").document("Registros").collection("Alimentação").document("Café da manhã").collection(dataHoje).document(alimento);
        documentReference.set(cafeDaManha).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(this, "Alimento adicionado com sucesso", Toast.LENGTH_LONG).show();

                Intent irTelaCafeManhaAdicionado = new Intent(this, TelaCafeManhaAdicionado.class);
                startActivity(irTelaCafeManhaAdicionado);
            }
        });
    }
}