package com.example.cincuentazo.controller;

import com.example.cincuentazo.model.Carta;

import java.util.ArrayList;
import java.util.Collections;

public class MazoController {

    protected ArrayList<Carta> cartas;

    public MazoController() {
        cartas = new ArrayList<>();
        createDeck();
        shuffleDeck();
    }

    /**
     * create the deck of cards
     */
    public void createDeck() {
        String[] palos = {"hearts", "diamonds", "clubs", "spades"};
        String[] valores = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "As"};
        int id = 1;

        for (String palo : palos) {
            for (String valor : valores) {

                int valorNumerico = getValue(valor);

                String nombre = valor + " de " + palo;

                String imagen = "imagen/" + palo + "/" + valor + palo + ".png";

                cartas.add(new Carta(id++, nombre, imagen, valorNumerico));
            }
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(cartas);
    }

    /**
     * getValue
     * @param valor
     * @return
     * returns the corresponding value
     */
    public int getValue(String valor) {
        switch (valor) {
            case "J":
            case "Q":
            case "K":
                return -10;
            case "As":
                return 5;
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
    public ArrayList<Carta> getLetters() {
        return cartas;
    }

    /**
     *
     * @param id
     * @return
     */
    public Carta getletterForId(int id) {
        for (Carta carta : cartas) {
            if (carta.getId() == id) {
                return carta;
            }
        }
        return null;
    }
}