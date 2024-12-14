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
     * addLetter
     * @param carta
     * adds a card to the player's hand
     */
    public void addLetter(Carta carta) {
        mano.add(carta);
    }

    /**
     * deleteLetter
     * @param carta
     * remove a card from the player's hand
     */
    public void deleteLetter(Carta carta) {
        if (carta != null && mano.contains(carta)) {
            mano.remove(carta);
            System.out.println("Carta " + carta.getName() + " eliminada correctamente.");
        } else {
            System.out.println("La carta no se encuentra en la mano.");
        }
    }
}
