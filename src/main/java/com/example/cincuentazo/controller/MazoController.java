package com.example.cincuentazo.controller;

import com.example.cincuentazo.model.Carta;

import java.util.ArrayList;

public class MazoController {

    private ArrayList<Carta> cartas;

    public MazoController() {
        cartas = new ArrayList<>();
        crearMazo();
    }

    /**
     *
     */
    private void crearMazo() {
        String[] palos = {"hearts", "diamonds", "clubs", "spades"};
        String[] valores = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "As"};
        int id = 1;

        for (String palo : palos) {
            for (String valor : valores) {

                int valorNumerico = obtenerValor(valor); // Obtener el valor correspondiente

                String nombre = valor + " de " + palo;

                String imagen = "imagen/" + palo + "/" + valor.toLowerCase() + palo + ".png";

                cartas.add(new Carta(id++, nombre, imagen, valorNumerico));
            }
        }
    }

    /**
     *
     * @param valor
     * @return
     */
    private int obtenerValor(String valor) {
        switch (valor) {
            case "J":
            case "Q":
            case "K":
                return -10;
            case "As":
                return 1;
            default:
                return Integer.parseInt(valor);
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    /**
     *
     * @param id
     * @return
     */
    public Carta getCartaPorId(int id) {
        for (Carta carta : cartas) {
            if (carta.getId() == id) {
                return carta;
            }
        }
        return null;
    }

}