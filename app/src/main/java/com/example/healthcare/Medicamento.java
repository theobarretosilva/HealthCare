package com.example.healthcare;

import com.google.firebase.database.DatabaseReference;

public class Medicamento {
    String nomeMedicamento, qtdMedicamento, mgDosagemMedicamento, horarioMedicamento;
    private String id;

    public Medicamento() {
        this.setId(FirebaseHelper.getDatabaseReference()
                .push()
                .getKey()
        );
    }

    public void salvarMedicamento(){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(FirebaseHelper.getUIDUsuario())
                .child("Medicamentos")
                .child(this.id);
        reference.setValue(this);
    }

    public String getNomeMedicamento() {
        return nomeMedicamento;
    }
    public void setNomeMedicamento(String nomeMedicamento) {
        this.nomeMedicamento = nomeMedicamento;
    }

    public String getQtdMedicamento() {
        return qtdMedicamento;
    }
    public void setQtdMedicamento(String qtdMedicamento) {
        this.qtdMedicamento = qtdMedicamento;
    }

    public String getMgDosagemMedicamento() {
        return mgDosagemMedicamento;
    }
    public void setMgDosagemMedicamento(String mgDosagemMedicamento) {
        this.mgDosagemMedicamento = mgDosagemMedicamento;
    }

    public String getHorarioMedicamento() {
        return horarioMedicamento;
    }
    public void setHorarioMedicamento(String horarioMedicamento) {
        this.horarioMedicamento = horarioMedicamento;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
