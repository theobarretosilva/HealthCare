package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;

public class TelaVinculacaoClinicas extends AppCompatActivity {

    TextView nomeClinica, enderecoClinica, telefoneClinica, servicosClinica;
    ImageView fotoClinica;
    CheckBox termos;
    BottomSheetDialog dialog;

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
        Intent voltarTela = new Intent(this, TelaClinicas.class);
        startActivity(voltarTela);
    }

    public void mostrarCard(){
        View view = getLayoutInflater().inflate(R.layout.card_clinica_vinculada, null, false);

        Button voltarTelaClinicas = view.findViewById(R.id.btnOk);
        voltarTelaClinicas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltarTela(view);
            }
        });

        dialog.setContentView(view);
        dialog.show();
    }

    public void vincularClinica(View v){

        if (termos.isChecked()){

            for (VincularClinicas c : TelaClinicas.lClinicas){

                DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                        .child("Registros")
                        .child(FirebaseHelper.getUIDUsuario())
                        .child("Clínicas vinculadas")
                        .child(c.getNomeClinica());

                reference.setValue(c.getNomeClinica()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        mostrarCard();
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TelaVinculacaoClinicas.this, "Não foi possível vincular a clínica.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } else {
            Toast.makeText(TelaVinculacaoClinicas.this, "Leia e concorde com os Termos de Vinculação!", Toast.LENGTH_SHORT).show();
        }
    }
}