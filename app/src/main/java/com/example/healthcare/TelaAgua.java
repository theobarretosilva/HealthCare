package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TelaAgua extends AppCompatActivity {

    TextView dataAtual, qntdAgua, tituloAgua, texto;
    ImageView imgGarrafa;
    Button btnIngerido;

    Date data = new Date();

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    String dataHoje = sdf.format(data);

    SimpleDateFormat sdfBarra = new SimpleDateFormat("dd/MM/yyyy");
    String dataHojeBarra = sdfBarra.format(data);

    int aguaIngerida = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_agua);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
        dataAtual.setText(dataHojeBarra);
        setarQtndAgua();
    }

    public void iniciarComponentes(){
        dataAtual = findViewById(R.id.dataHojeAgua);
        qntdAgua = findViewById(R.id.qntdAgua);
        imgGarrafa = findViewById(R.id.imgGarrafa);
        tituloAgua = findViewById(R.id.tituloAgua);
        texto = findViewById(R.id.texto);
        btnIngerido = findViewById(R.id.btnIngerido);
    }

    public void mandarAguaBD(View r){

        if(aguaIngerida >= 0 && aguaIngerida < 3000){
            aguaIngerida = aguaIngerida + 250;
        }

        Map<String, Object> aguaBD = new HashMap<>();
        aguaBD.put("Água ingerida", aguaIngerida);

        DocumentReference dr = FirebaseHelper.getFirebaseFirestore()
                .collection("Usuarios")
                .document(FirebaseHelper.getUIDUsuario())
                .collection("Informações pessoais")
                .document("Registros")
                .collection("Água")
                .document(dataHoje);
        dr.set(aguaBD);

        setarQtndAgua();
    }

    public void setarQtndAgua(){

        DocumentReference documentReference = FirebaseHelper.getFirebaseFirestore()
                .collection("Usuarios")
                .document(FirebaseHelper.getUIDUsuario())
                .collection("Informações pessoais")
                .document("Registros")
                .collection("Água")
                .document(dataHoje);

        documentReference.addSnapshotListener((documentSnapshot, error) -> {
            if (documentSnapshot.exists()){
                int valorAgua = Math.toIntExact((Long) documentSnapshot.getData().get("Água ingerida"));
                aguaIngerida = Math.toIntExact((Long) documentSnapshot.getData().get("Água ingerida"));

                if(valorAgua == 250){
                    imgGarrafa.setImageResource(R.drawable.garrafa1);
                }else if(valorAgua == 500){
                    imgGarrafa.setImageResource(R.drawable.garrafa2);
                }else if(valorAgua == 750){
                    imgGarrafa.setImageResource(R.drawable.garrafa3);
                }else if(valorAgua == 1000){
                    imgGarrafa.setImageResource(R.drawable.garrafa4);
                }else if(valorAgua == 1250){
                    imgGarrafa.setImageResource(R.drawable.garrafa5);
                }else if(valorAgua == 1500){
                    imgGarrafa.setImageResource(R.drawable.garrafa6);
                }else if(valorAgua == 1750){
                    imgGarrafa.setImageResource(R.drawable.garrafa7);
                }else if(valorAgua == 2000){
                    imgGarrafa.setImageResource(R.drawable.garrafa8);
                }else if(valorAgua == 2250){
                    imgGarrafa.setImageResource(R.drawable.garrafa9);
                }else if(valorAgua == 2500){
                    imgGarrafa.setImageResource(R.drawable.garrafa10);
                }else if(valorAgua == 2750){
                    imgGarrafa.setImageResource(R.drawable.garrafa11);
                }else if(valorAgua == 3000){
                    imgGarrafa.setImageResource(R.drawable.garrafa12);
                    tituloAgua.setText("Você concluiu sua meta! \uD83C\uDF89");
                    texto.setText(" ");
                    btnIngerido.setVisibility(View.GONE);
                }

                qntdAgua.setText(valorAgua + " / 3000ml");
            }else if(!documentSnapshot.exists()){
                imgGarrafa.setImageResource(R.drawable.garrafa0);
            }
        });
    }

    public void voltarTelaAgua(View t){
        Intent voltarTelaAgua = new Intent(this, TelaConteudos.class);
        startActivity(voltarTelaAgua);
    }
}