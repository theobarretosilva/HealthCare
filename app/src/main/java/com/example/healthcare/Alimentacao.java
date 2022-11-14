package com.example.healthcare;

import com.google.firebase.database.DatabaseReference;

public class Alimentacao {
    String alimento;
    int gramas, kcal;
    private String id;

    public Alimentacao() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference();
        this.setId(reference.push().getKey());
    }

    public void salvarCafeDaManha(){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(FirebaseHelper.getUIDUsuario())
                .child("Alimentação")
                .child("Café da manhã")
                .child(this.alimento);
        reference.setValue(this);
    }

    public void salvarAlmoco(){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(FirebaseHelper.getUIDUsuario())
                .child("Alimentação")
                .child("Almoço")
                .child(this.alimento);
        reference.setValue(this);
    }

    public void salvarJantar(){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(FirebaseHelper.getUIDUsuario())
                .child("Alimentação")
                .child("Jantar")
                .child(this.alimento);
        reference.setValue(this);
    }

    public void salvarLanches(){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(FirebaseHelper.getUIDUsuario())
                .child("Alimentação")
                .child("Lanches")
                .child(this.alimento);
        reference.setValue(this);
    }

    public String getAlimento() {
        return alimento;
    }
    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }

    public int getGramas() {
        return gramas;
    }
    public void setGramas(int gramas) {
        this.gramas = gramas;
    }

    public int getKcal() {
        return kcal;
    }
    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
