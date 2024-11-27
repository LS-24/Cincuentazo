package com.example.cincuentazo.model;

import java.util.ArrayList;

public class Player {

    private String name;
    private ArrayList mano;

    public Player(String name , ArrayList mano ) {
        this.name = name;
        this.mano = mano;
    }

    public String getName() {
        return name;
    }

    public ArrayList getMano() {
        return mano;
    }


}
