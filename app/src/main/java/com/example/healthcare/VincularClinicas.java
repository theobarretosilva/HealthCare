package com.example.healthcare;

import android.graphics.drawable.Drawable;

public class VincularClinicas {
    String nomeClinica, enderecoClinica, telefoneClinica, servicosClinica;
    Drawable fotoClinica;

    public VincularClinicas(String nomeClinica, String enderecoClinica, String telefoneClinica, String servicosClinica, Drawable fotoClinica) {
        this.nomeClinica = nomeClinica;
        this.enderecoClinica = enderecoClinica;
        this.telefoneClinica = telefoneClinica;
        this.servicosClinica = servicosClinica;
        this.fotoClinica = fotoClinica;
    }

    public String getNomeClinica() {
        return nomeClinica;
    }
    public void setNomeClinica(String nomeClinica) {
        this.nomeClinica = nomeClinica;
    }

    public String getEnderecoClinica() {
        return enderecoClinica;
    }
    public void setEnderecoClinica(String enderecoClinica) {
        this.enderecoClinica = enderecoClinica;
    }

    public String getTelefoneClinica() {
        return telefoneClinica;
    }
    public void setTelefoneClinica(String telefoneClinica) {
        this.telefoneClinica = telefoneClinica;
    }

    public String getServicosClinica() {
        return servicosClinica;
    }
    public void setServicosClinica(String servicosClinica) {
        this.servicosClinica = servicosClinica;
    }

    public Drawable getFotoClinica() {
        return fotoClinica;
    }
    public void setFotoClinica(Drawable fotoClinica) {
        this.fotoClinica = fotoClinica;
    }
}
