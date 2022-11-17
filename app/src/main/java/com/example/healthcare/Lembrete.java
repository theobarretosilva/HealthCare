package com.example.healthcare;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class Lembrete {
    String nomeLembrete, dataLembrete;
    private String id;

    public Lembrete() {
        this.setId(FirebaseHelper.getDatabaseReference()
                .push()
                .getKey()
        );
    }

    public void salvarLembrete(){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(FirebaseHelper.getUIDUsuario())
                .child("Lembretes")
                .child(this.id);
        reference.setValue(this);
    }

    public String getNomeLembrete() {
        return nomeLembrete;
    }
    public void setNomeLembrete(String nomeLembrete) {
        this.nomeLembrete = nomeLembrete;
    }

    public String getDataLembrete() {
        return dataLembrete;
    }
    public void setDataLembrete(String dataLembrete) {
        this.dataLembrete = dataLembrete;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
