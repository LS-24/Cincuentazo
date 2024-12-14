package com.example.cincuentazotest;

import static org.junit.jupiter.api.Assertions.*;

import com.example.cincuentazo.model.Carta ;
import com.example.cincuentazo.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class PlayerTest {

    private Player player;
    private ArrayList<Carta> mano;

    @BeforeEach
    public void setUp() {
        mano = new ArrayList<>();
        player = new Player("Jugador 1", mano);
    }

    @Test
    public void testConstructor() {
        assertEquals("Jugador 1", player.getName());
        assertTrue(player.getMano().isEmpty());
    }

    @Test
    public void testAgregarCarta() {
        Carta carta = new Carta(1, "2 de corazones", "imagen/corazones/2corazones.png", 2);
        player.agregarCarta(carta);
        assertTrue(player.getMano().contains(carta));
    }

    @Test
    public void testBorrarCarta() {
        Carta carta = new Carta(1, "2 de corazones", "imagen/corazones/2corazones.png", 2);
        player.agregarCarta(carta);
        assertTrue(player.getMano().contains(carta));
        player.borrarCarta(carta);
        assertFalse(player.getMano().contains(carta));
    }

    @Test
    public void testAgregarYEliminarVariasCartas() {

        Carta carta1 = new Carta(1, "2 de corazones", "imagen/corazones/2corazones.png", 2);
        Carta carta2 = new Carta(2, "3 de tréboles", "imagen/treboles/3treboles.png", 3);

        player.agregarCarta(carta1);
        player.agregarCarta(carta2);

        assertTrue(player.getMano().contains(carta1));
        assertTrue(player.getMano().contains(carta2));

        player.borrarCarta(carta1);

        assertFalse(player.getMano().contains(carta1));
        assertTrue(player.getMano().contains(carta2));
    }
}
