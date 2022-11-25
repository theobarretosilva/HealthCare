package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TelaVinculacaoClinicas extends AppCompatActivity {

    TextView nomeClinica, enderecoClinica, telefoneClinica, servicosClinica;
    ImageView fotoClinica;
    CheckBox termos;
    BottomSheetDialog dialog;
    ArrayList<String> clinicas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_vinculacao_clinicas);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
        getSetClinica();
    }

    public void iniciarComponentes(){
        nomeClinica = findViewById(R.id.nomeClinica);
        enderecoClinica = findViewById(R.id.enderecoClinica);
        telefoneClinica = findViewById(R.id.telefoneClinica);
        servicosClinica = findViewById(R.id.servicosClinicas);
        fotoClinica = findViewById(R.id.fotoClinica);
        termos = findViewById(R.id.checkTermos);
        dialog = new BottomSheetDialog(this);
    }

    public void getSetClinica(){
        for (VincularClinicas c : TelaClinicas.lClinicas){
            nomeClinica.setText(c.getNomeClinica());
            enderecoClinica.setText(c.getEnderecoClinica());
            telefoneClinica.setText(c.getTelefoneClinica());
            servicosClinica.setText(c.getServicosClinica());
            fotoClinica.setImageDrawable(c.getFotoClinica());
        }
    }

    public void voltarTela(View g){
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.mover_direita);
        ActivityCompat.startActivity(TelaVinculacaoClinicas.this, new Intent(this, TelaClinicas.class), activityOptionsCompat.toBundle());
    }

    public void mostrarCard(){
        View view = getLayoutInflater().inflate(R.layout.card_clinica_vinculada, null, false);

        Button voltarTelaClinicas = view.findViewById(R.id.btnOk);
        voltarTelaClinicas.setOnClickListener(view1 -> voltarTela(view1));

        dialog.setContentView(view);
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (clinicas.isEmpty()){
            DatabaseReference clinicasReference = FirebaseHelper.getDatabaseReference()
                    .child("Registros")
                    .child(FirebaseHelper.getUIDUsuario())
                    .child("Clinicas vinculadas");
            clinicasReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snap : snapshot.getChildren()){
                        clinicas.add(snap.getValue().toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void vincularClinica(View v){
        if (termos.isChecked()){
            String nome = "\uD83C\uDFE5 " + nomeClinica.getText().toString();

            if (!clinicas.contains(nome)){
                clinicas.add(nome);

                DatabaseReference clinicasReference = FirebaseHelper.getDatabaseReference()
                        .child("Registros")
                        .child(FirebaseHelper.getUIDUsuario())
                        .child("Clinicas vinculadas");
                clinicasReference.setValue(clinicas);

                mostrarCard();
            } else {
                mostrarCard();
            }
        } else {
            Toast.makeText(TelaVinculacaoClinicas.this, "Leia e concorde com os Termos de Vinculação!", Toast.LENGTH_SHORT).show();
        }
    }
}