package com.example.cincuentazo.controller;

import com.example.cincuentazo.model.Carta;

import java.util.ArrayList;
import java.util.Collections;

public class MazoController {

    protected ArrayList<Carta> cartas;

    public MazoController() {
        cartas = new ArrayList<>();
        crearMazo();
        barajarMazo();
    }

    /**
     * create the deck of cards
     */
    private void crearMazo() {
        String[] palos = {"hearts", "diamonds", "clubs", "spades"};
        String[] valores = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "As"};
        int id = 1;

        for (String palo : palos) {
            for (String valor : valores) {

                int valorNumerico = obtenerValor(valor);

                String nombre = valor + " de " + palo;

                String imagen = "imagen/" + palo + "/" + valor.toLowerCase() + palo + ".png";

                cartas.add(new Carta(id++, nombre, imagen, valorNumerico));
            }
        }
    }

    private void barajarMazo() {
        Collections.shuffle(cartas);  // Baraja el mazo
    }

    /**
     * obtenerValor
     * @param valor
     * @return
     * returns the corresponding value
     */
    private int obtenerValor(String valor) {
        switch (valor) {
            case "J":
            case "Q":
            case "K":
                return -10;
            case "As":
                return 1;
            case "9":
                return 0;
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