package com.example.healthcare;

import com.google.firebase.database.DatabaseReference;

public class Alimentacao {
    String alimento, gramas, kcal;
    String tipoAlimentacao;
    private String id;

    public Alimentacao(String alimento, String gramas, String kcal) {
        this.alimento = alimento;
        this.gramas = gramas;
        this.kcal = kcal;
    }

    public Alimentacao(String tipoAlimentacao){

    }

    public Alimentacao() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference();
        this.setId(reference.push().getKey());
    }

    public void salvarCafeDaManha(){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(FirebaseHelper.getUIDUsuario())
                .child("Alimentacao")
                .child("Café da manhã")
                .child(this.id);
        reference.setValue(this);
    }

    public void salvarAlmoco(){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(FirebaseHelper.getUIDUsuario())
                .child("Alimentação")
                .child("Almoço")
                .child(this.id);
        reference.setValue(this);
    }

    public void salvarJantar(){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(FirebaseHelper.getUIDUsuario())
                .child("Alimentação")
                .child("Jantar")
                .child(this.id);
        reference.setValue(this);
    }

    public void salvarLanches(){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(FirebaseHelper.getUIDUsuario())
                .child("Alimentação")
                .child("Lanches")
                .child(this.id);
        reference.setValue(this);
    }

    public String getAlimento() {
        return alimento;
    }
    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }

    public String getGramas() {
        return gramas;
    }
    public void setGramas(String gramas) {
        this.gramas = gramas;
    }

    public String getKcal() {
        return kcal;
    }
    public void setKcal(String kcal) {
        this.kcal = kcal;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getTipoAlimentacao() {
        return tipoAlimentacao;
    }
    public void setTipoAlimentacao(String tipoAlimentacao) {
        this.tipoAlimentacao = tipoAlimentacao;
    }
}
