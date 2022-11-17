package com.example.healthcare;

import com.google.firebase.database.DatabaseReference;

public class Exame {
    String clinica, tipo, data, horario;
    private String id;

    public Exame() {
        this.setId(FirebaseHelper.getDatabaseReference()
                .push()
                .getKey()
        );
    }

    public void salvarExame(){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(FirebaseHelper.getUIDUsuario())
                .child("Exames")
                .child(this.id);
        reference.setValue(this);
    }

    public String getClinica() {
        return clinica;
    }
    public void setClinica(String clinica) {
        this.clinica = clinica;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }
    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
