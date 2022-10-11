package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.Externalizable;
import java.util.List;

public class TelaExames extends AppCompatActivity {

    List<Exame> exameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_exames);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();
    }

    public void recuperaExames(){
        DatabaseReference examesRef = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(uidUsuario)
                .child("Exames")
        examesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    Exame exame = snap.getValue(Exame.class);
                    exameList.add(exame);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}