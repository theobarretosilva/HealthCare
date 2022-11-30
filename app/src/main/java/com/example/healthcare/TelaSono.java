package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.google.firebase.firestore.DocumentReference;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;

public class TelaSono extends AppCompatActivity {

    TextView dataAtual, hDormiu, hAcordou, tempoDormido;

    Date data = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    String dataHoje = sdf.format(data);

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
        DocumentReference documentReference = FirebaseHelper.getFirebaseFirestore()
                .collection("Usuarios")
                .document(FirebaseHelper.getUIDUsuario())
                .collection("Informações pessoais")
                .document("Registros")
                .collection("Sono")
                .document(dataHoje);
        documentReference.addSnapshotListener((documentSnapshot, error) -> {
            if (documentSnapshot.exists()){
                String dormiu = documentSnapshot.getString("Horário que dormiu");
                hDormiu.setText(dormiu);
                String acordou = documentSnapshot.getString("Horário que acordou");
                hAcordou.setText(acordou);
            }
        });
    }

    public void gerarRelatorio(View q){
        int horasDormidas = 0 , minDormidos =0 ;
        if (!(hDormiu.getText().toString() == "")){
            LocalTime horarioD = LocalTime.parse(hDormiu.getText().toString());
            LocalTime horarioA = LocalTime.parse(hAcordou.getText().toString());

           if(horarioD.getHour() < horarioA.getHour()){
               Duration horas = Duration.between(horarioD, horarioA);
               long hora = horas.toHours();
               long minuto = horas.toMinutes();
               System.out.println(hora+" : "+minuto%60);
               tempoDormido.setText(hora+":"+minuto%60);
               return;
           }

            if(horarioD.getHour() > horarioA.getHour()){
                int diferencaH = 24 - horarioD.getHour();
                horasDormidas = horarioA.getHour() + diferencaH;


                if(horarioD.getMinute() > horarioA.getMinute()){
                    int diferecaM = 60 - horarioD.getMinute();
                    minDormidos = horarioA.getMinute() + diferecaM;
                    if(horarioD.getMinute() > 0){
                        horasDormidas = horasDormidas -1;
                    }

                }

                if(horasDormidas < 0){
                    horasDormidas = horasDormidas * -1;
                }
                if(minDormidos < 0){
                    minDormidos = minDormidos * -1;
                }
                System.out.println(horasDormidas+":"+minDormidos);
            }



            tempoDormido.setText(horasDormidas+":"+minDormidos);
        } else {
            Toast.makeText(this, "Você precisa adicionar as suas informações de sono primeiro!", Toast.LENGTH_LONG).show();
        }
    }

    public void voltarTela(View g){
        if (TelaLogin.premium) {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.mover_direita);
            ActivityCompat.startActivity(TelaSono.this, new Intent(this, TelaConteudos_Premium.class), activityOptionsCompat.toBundle());
        } else {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.mover_direita);
            ActivityCompat.startActivity(TelaSono.this, new Intent(this, TelaConteudos.class), activityOptionsCompat.toBundle());
        }
    }
}