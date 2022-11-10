package com.example.healthcare;

public class Vacina {
    String titulo, subTitulo;

    public  Vacina(String titulo, String subTitulo) {
        this.titulo = titulo;
        this.subTitulo = subTitulo;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubTitulo() {
        return subTitulo;
    }
    public void setSubTitulo(String subTitulo) {
        this.subTitulo = subTitulo;
    }
}
