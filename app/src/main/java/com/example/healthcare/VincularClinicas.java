package com.example.healthcare;

import android.widget.ImageView;

public class VincularClinicas {
    String nomeClinica, enderecoClinica, telefoneClinica, servicosClinica;
    ImageView fotoClinica;

    public VincularClinicas(String nomeClinica, String enderecoClinica, String telefoneClinica, String servicosClinica, ImageView fotoClinica) {
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

    public ImageView getFotoClinica() {
        return fotoClinica;
    }
    public void setFotoClinica(ImageView fotoClinica) {
        this.fotoClinica = fotoClinica;
    }
}
