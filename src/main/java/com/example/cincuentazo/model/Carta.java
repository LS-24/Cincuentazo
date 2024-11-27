package com.example.cincuentazo.model;

public class Carta {

    private int id;
    private int valor;
    private String nombre;
    private String imagen;

    /**
     *
     * @param id
     * @param nombre
     * @param imagen
     * @param valor
     */
    public Carta(int id, String nombre, String imagen, int valor) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.valor = valor;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @return
     */
    public String getImagen() {
        return imagen;
    }

    /**
     *
     * @return
     */
    public int getValor() {
        return valor;
    }
}
