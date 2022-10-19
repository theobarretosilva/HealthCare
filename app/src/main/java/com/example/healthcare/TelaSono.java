package com.example.healthcare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class TelaSono extends AppCompatActivity {

    TextView dataAtual, hDormiu, hAcordou, tempoDormido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_sono);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        dataAtual = findViewById(R.id.dataAtualSono);
        hDormiu = findViewById(R.id.hDormiu);
        hAcordou = findViewById(R.id.hAcordou);
        tempoDormido = findViewById(R.id.tempoDormido);

        setarData();
        calculaSono();
    }

    public void adicionarRegistro(View g){
        Intent irTelaRegistroSono = new Intent(this, TelaSono_add.class);
        startActivity(irTelaRegistroSono);
    }

    public void setarData(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        String dataHoje = sdf.format(data);

        String dataCEmoji = "\uD83D\uDE34 " + dataHoje;

        dataAtual.setText(dataCEmoji);
    }

    public void calculaSono (){
        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dataHoje = sdf.format(data);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID).collection("Informações pessoais").document("Registros").collection("Sono").document(dataHoje);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null){
                    String dormiu = documentSnapshot.getString("Horário que dormiu");
                    hDormiu.setText(dormiu);
                    String acordou = documentSnapshot.getString("Horário que acordou");
                    hAcordou.setText(acordou);

                    LocalTime horarioD = LocalTime.parse(hDormiu.getText().toString());
                    LocalTime horarioA = LocalTime.parse(hAcordou.getText().toString());

                    int horasDormidas = horarioD.getHour() - horarioA.getHour();
                    if(horasDormidas < 0){
                       horasDormidas = horasDormidas * -1;
                    }

                    int minDormidos = horarioD.getMinute() - horarioA.getMinute();
                    if(minDormidos < 0){
                        minDormidos = minDormidos * -1;
                    }

                    tempoDormido.setText(horasDormidas+":"+minDormidos);

                }
            }
        });
    }
}