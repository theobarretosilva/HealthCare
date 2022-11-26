package com.example.healthcare;

import com.google.firebase.database.DatabaseReference;

public class Peso {
    String dataPeso, horaPeso;
    int peso;
    private String id;

    public Peso() {
        this.setId(FirebaseHelper.getDatabaseReference()
                .push()
                .getKey()
        );
    }

    public void salvarPeso(){
        DatabaseReference pesoReference = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(FirebaseHelper.getUIDUsuario())
                .child("Peso")
                .child(this.dataPeso);
        pesoReference.setValue(this);
    }

    public int getPeso() {
        return peso;
    }
    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getDataPeso() {
        return dataPeso;
    }
    public void setDataPeso(String dataPeso) {
        this.dataPeso = dataPeso;
    }

    public String getHoraPeso() {
        return horaPeso;
    }
    public void setHoraPeso(String horaPeso) {
        this.horaPeso = horaPeso;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
