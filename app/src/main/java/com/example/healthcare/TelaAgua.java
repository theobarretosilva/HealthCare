package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    TextView dataAtual, qntdAgua;
    ImageView imgGarrafa;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date data = new Date();
    String dataHoje = sdf.format(data);

    String usuarioID;

    int aguaIngerida = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_agua);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        dataAtual = findViewById(R.id.dataHojeAgua);
        qntdAgua = findViewById(R.id.qntdAgua);
        imgGarrafa = findViewById(R.id.imgGarrafa);
    }

    @Override
    protected void onStart() {
        super.onStart();

        dataAtual.setText(dataHoje);
    }

    public void mandarAguaBD(View a){
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(aguaIngerida >= 0 && aguaIngerida < 3000){
            aguaIngerida = aguaIngerida + 250;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> aguaBD = new HashMap<>();
        aguaBD.put("Água ingerida", aguaIngerida);

        DocumentReference dr = db.collection("Usuarios").document(usuarioID).collection("Informações pessoais").document("Registros").collection("Água").document("Ingestão de água");
        dr.set(aguaBD);

        setarQtndAgua();
    }

    public void setarQtndAgua(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID).collection("Informações pessoais").document("Registros").collection("Água").document("Ingestão de água");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null){
                    int valorAgua = Math.toIntExact((Long) documentSnapshot.getData().get("Água ingerida"));


                        if (valorAgua == 250){
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
                        }

                    qntdAgua.setText(valorAgua + "/3000ml");
                }
            }
        });
    }

    public void voltarTelaAgua(View t){
        Intent voltarTelaAgua = new Intent(this, TelaConteudos.class);
        startActivity(voltarTelaAgua);
    }
}