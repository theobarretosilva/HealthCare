package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
        aguaIngerida  = aguaIngerida + 250;

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> aguaBD = new HashMap<>();
        aguaBD.put("Água ingerida", aguaIngerida);

        DocumentReference dr = db.collection("Usuarios").document(usuarioID).collection("Informações pessoais").document("Registros").collection("Água").document("Ingestão de água");
        dr.set(aguaBD);

        setarQtndAgua();
    }

    public void setarQtndAgua(){
        qntdAgua.setText(aguaIngerida + "/3000ml");
        imgGarrafa.setImageResource(R.drawable.garrafa1);
    }
}