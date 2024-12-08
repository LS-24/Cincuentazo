package com.example.cincuentazo.model;

import java.util.ArrayList;

public class Player {

    private String name;
    private ArrayList <Carta> mano;
    private int index;

    /**
     * Player
     * @param name
     * @param mano
     * initializes the player's name and hand
     */
    public Player(String name , ArrayList mano ) {
        this.name = name;
        this.mano = mano;
    }

    /**
     * getName
     * @return
     * returns the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * getMano
     * @return
     * returns the player's hand
     */
    public ArrayList<Carta> getMano() {
        return mano;
    }

    /**
     * getIndex
     * @return
     * returns the player's index
     */
    public int getIndex() {
        return index;
    }

    /**
     * agregarCarta
     * @param carta
     * adds a card to the player's hand
     */
    public void agregarCarta(Carta carta) {
        mano.add(carta);
    }

    /**
     * borrarCarta
     * @param carta
     * remove a card from the player's hand
     */
    public void borrarCarta(Carta carta) {
        if (carta != null && mano.contains(carta)) {
            mano.remove(carta);
            System.out.println("Carta " + carta.getNombre() + " eliminada correctamente.");
        } else {
            System.out.println("La carta no se encuentra en la mano.");
        }
    }
}
