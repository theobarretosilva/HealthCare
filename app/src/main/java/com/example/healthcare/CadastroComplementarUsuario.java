package com.example.healthcare;

import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class CadastroComplementarUsuario {
    int peso, altura;
    String biotipo;

    public void cadastrarComplementoUsuario(){
        Map<String, Object> cadastroComplementar = new HashMap<>();
        cadastroComplementar.put("Peso (kg)", peso);
        cadastroComplementar.put("Altura (cm)", altura);
        cadastroComplementar.put("Biotipo corporal", biotipo);

        DocumentReference ns = FirebaseHelper.getFirebaseFirestore()
                .collection("Usuarios")
                .document(FirebaseHelper.getUIDUsuario())
                .collection("Informações pessoais")
                .document("Cadastro complementar");
        ns.set(cadastroComplementar);
    }

    public int getPeso() {
        return peso;
    }
    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getAltura() {
        return altura;
    }
    public void setAltura(int altura) {
        this.altura = altura;
    }

    public String getBiotipo() {
        return biotipo;
    }
    public void setBiotipo(String biotipo) {
        this.biotipo = biotipo;
    }
}
