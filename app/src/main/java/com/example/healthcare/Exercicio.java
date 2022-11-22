package com.example.healthcare;

import com.google.firebase.database.DatabaseReference;

public class Exercicio {
    String nomeExercicio, tempoExercicio;
    private String id;

    public Exercicio() {
        this.setId(FirebaseHelper.getDatabaseReference()
                .push()
                .getKey()
        );
    }

    public void salvarExercicio(){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(FirebaseHelper.getUIDUsuario())
                .child("Exercicios")
                .child(this.id);
        reference.setValue(this);
    }

    public String getNomeExercicio() {
        return nomeExercicio;
    }
    public void setNomeExercicio(String nomeExercicio) {
        this.nomeExercicio = nomeExercicio;
    }

    public String getTempoExercicio() {
        return tempoExercicio;
    }
    public void setTempoExercicio(String tempoExercicio) {
        this.tempoExercicio = tempoExercicio;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
