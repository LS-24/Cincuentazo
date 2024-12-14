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
    void testBarajarMazo() {
        ArrayList<Carta> cartasOriginales = new ArrayList<>(mazoController.getCartas());
        mazoController.barajarMazo();
        assertNotEquals(cartasOriginales, mazoController.getCartas(), "Las cartas deben estar barajadas");
    }

    @Test
    void testObtenerValor() {
        assertEquals(2, mazoController.obtenerValor("2"), "El valor de '2' debe ser 2");
        assertEquals(0, mazoController.obtenerValor("9"), "El valor de '9' debe ser 0");
        assertEquals(-10, mazoController.obtenerValor("J"), "El valor de 'J' debe ser -10");
        assertEquals(-10, mazoController.obtenerValor("Q"), "El valor de 'Q' debe ser -10");
        assertEquals(-10, mazoController.obtenerValor("K"), "El valor de 'K' debe ser -10");
        assertEquals(1, mazoController.obtenerValor("As"), "El valor de 'As' debe ser 1");
    }
}