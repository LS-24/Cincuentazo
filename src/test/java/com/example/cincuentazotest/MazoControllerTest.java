package com.example.cincuentazotest;

import com.example.cincuentazo.controller.MazoController;
import com.example.cincuentazo.model.Carta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class MazoControllerTest {

    private MazoController mazoController;

    @BeforeEach
    void setUp() {
        mazoController = new MazoController();
    }

    @Test
    void testShuffleDeck() {
        ArrayList<Carta> cartasOriginales = new ArrayList<>(mazoController.getLetters());
        mazoController.shuffleDeck();
        assertNotEquals(cartasOriginales, mazoController.getLetters(), "Las cartas deben estar barajadas");
    }

    @Test
    void testGetValue() {
        assertEquals(2, mazoController.getValue("2"), "El valor de '2' debe ser 2");
        assertEquals(0, mazoController.getValue("9"), "El valor de '9' debe ser 0");
        assertEquals(-10, mazoController.getValue("J"), "El valor de 'J' debe ser -10");
        assertEquals(-10, mazoController.getValue("Q"), "El valor de 'Q' debe ser -10");
        assertEquals(-10, mazoController.getValue("K"), "El valor de 'K' debe ser -10");
        assertEquals(1, mazoController.getValue("As"), "El valor de 'As' debe ser 1");
    }
}