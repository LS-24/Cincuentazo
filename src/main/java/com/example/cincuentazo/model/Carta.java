package com.example.cincuentazo.model;

public class Carta {

    private int id;
    private int valor;
    private String nombre;
    private String imagen;

    public Carta(int id, String nombre, String imagen, int valor) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public int getValor() {
        return valor;
    }
}
