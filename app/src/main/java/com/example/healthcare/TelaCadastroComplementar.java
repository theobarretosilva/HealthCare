package com.example.healthcare;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TelaCadastroComplementar extends AppCompatActivity{

    EditText peso, altura;
    Spinner biotipo;
    Button btContinuar;

    String[] biotipoCorporal = new String[]{"Biotipo corporal", "Ectomorfo", "Endomorfo", "Mesomorfo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro_complementar);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
        configSpinner();
    }

    public void iniciarComponentes(){
        peso = findViewById(R.id.peso);
        altura = findViewById(R.id.altura);
        btContinuar = findViewById(R.id.btContinuar);
        biotipo = findViewById(R.id.biotipo);
    }

    public void configSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spi_elemento, biotipoCorporal);
        adapter.setDropDownViewResource(R.layout.spi_dropdown_elemento);
        biotipo.setAdapter(adapter);
        biotipo.setSelection(0, false);
    }

    public void verificaPreenchimentoComplementar(View f){
        if (biotipo.getSelectedItem().toString().equals("Biotipo corporal")) {
            TextView errorText = (TextView)biotipo.getSelectedView();
            errorText.setError("anything here, just to add the icon");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Selecione um biotipo corporal");
        } else if(peso.getText().length() == 0) {
            peso.setError("Você precisa preencher este campo");
        } else if (altura.getText().length() == 0) {
            altura.setError("Você precisa preencher este campo");
        } else {
            mandarCadastroCompleBD();
        }
    }

    public void mandarCadastroCompleBD(){
        int pesoP = parseInt(peso.getText().toString());
        int alturaA = parseInt(altura.getText().toString());
        String biotipoC = biotipo.getSelectedItem().toString();

        Map<String, Object> cadastroComplementar = new HashMap<>();
        cadastroComplementar.put("Peso (kg)", pesoP);
        cadastroComplementar.put("Altura (cm)", alturaA);
        cadastroComplementar.put("Biotipo corporal", biotipoC);

        DocumentReference ns = FirebaseHelper.getFirebaseFirestore()
                .collection("Usuarios")
                .document(FirebaseHelper.getUIDUsuario())
                .collection("Informações pessoais")
                .document("Cadastro complementar");

        ns.set(cadastroComplementar).addOnSuccessListener(unused -> {
            Intent irTelaConteudos = new Intent(this, TelaConteudos.class);
            startActivity(irTelaConteudos);
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Não foi possivel continuar o seu cadastro, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
        });

    }

    public void irTiposBiotipoInternet(View j){
        String url = "https://health-care-site.vercel.app/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}