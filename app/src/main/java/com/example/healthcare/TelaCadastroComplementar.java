package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TelaCadastroComplementar extends AppCompatActivity {

    EditText peso, altura;
    Spinner biotipo;
    Button btContinuar;

    String usuarioID;

    String[] biotipoCorporal = new String[]{"Biotipo corporal", "Ectomorfo", "Endomorfo", "Mesomorfo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro_complementar);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        peso = findViewById(R.id.peso);
        altura = findViewById(R.id.altura);
        btContinuar = findViewById(R.id.btContinuar);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, biotipoCorporal);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        biotipo = (Spinner) findViewById(R.id.biotipo);
        biotipo.setAdapter(adapter);
        biotipo.setSelection(0, false);

        biotipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(biotipo.getSelectedItem().toString().equals("Biotipo corporal")){
                    TextView errorText = (TextView)biotipo.getSelectedView();
                    errorText.setError("anything here, just to add the icon");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Selecione um biotipo corporal");
                }
                else{
                    mandarCadastroCompleBD();
                }
            }
        });
    }

    public void mandarCadastroCompleBD(){

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Intent irTelaConteudos = new Intent(this, TelaConteudos.class);

        String pesoP = peso.getText().toString();
        String alturaA = altura.getText().toString();
        String biotipoC = biotipo.getSelectedItem().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> cadastroComplementar = new HashMap<>();
        cadastroComplementar.put("Peso (kg)", pesoP);
        cadastroComplementar.put("Altura (cm)", alturaA);
        cadastroComplementar.put("Biotipo corporal", biotipoC);

        try {
            DocumentReference ns = db.collection("Usuarios").document(usuarioID).collection("Informações pessoais").document("Informações de cadastro");
            ns.set(cadastroComplementar);
        } catch (Exception e){
            startActivity(irTelaConteudos);
        }
    }
}