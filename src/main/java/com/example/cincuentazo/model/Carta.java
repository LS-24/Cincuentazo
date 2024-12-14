package com.example.cincuentazo.model;

public class Carta {

    private int id;
    private int valor;
    private String nombre;
    private String imagen;
    public Integer valorTemporal;

    /**
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
        this.valorTemporal = null;
    }

    public Carta(String carta10, int i) {
    }

    /**
     * getId
     *
     * @return returns the identity that was assigned to the letter
     */
    public int getId() {
        return id;
    }

    /**
     * getName
     *
     * @return returns card name
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * getImagen
     *
     * @return returns the name of the card image
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * getValor
     *
     * @return returns value of the card
     */
    public int getValor() {
        if (valorTemporal != null) {
            return valorTemporal;
        }
        return valor;
    }
}