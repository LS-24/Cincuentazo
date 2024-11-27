package com.example.cincuentazo.model;

import java.util.ArrayList;

public class Player {

    private String name;
    private ArrayList mano;

    /**
     *
     * @param name
     * @param mano
     */
    public Player(String name , ArrayList mano ) {
        this.name = name;
        this.mano = mano;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public ArrayList getMano() {
        return mano;
    }
}
