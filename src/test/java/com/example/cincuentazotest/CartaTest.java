package com.example.cincuentazotest;

import com.example.cincuentazo.model.Carta;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartaTest {

    @Test
    public void testCreacionCarta() {
        Carta carta = new Carta(1, "As de Espadas", "as_espadas.png", 10);

        assertEquals(1, carta.getId(), "El ID de la carta debe ser 1");
        assertEquals("As de Espadas", carta.getName(), "El nombre de la carta debe ser 'As de Espadas'");
        assertEquals("as_espadas.png", carta.getImagen(), "El nombre de la imagen debe ser 'as_espadas.png'");
        assertEquals(10, carta.getValue(), "El valor de la carta debe ser 10");
    }

    @Test
    public void testValorTemporal() {

        Carta carta = new Carta(2, "Rey de Corazones", "rey_corazones.png", 5);

        carta.valorTemporal = 15;

        assertEquals(15, carta.getValue(), "El valor temporal debe ser 15");
    }

    @Test
    public void testValorOriginal() {
        Carta carta = new Carta(4, "Reina de Treboles", "reina_treboles.png", 12);

        assertEquals(12, carta.getValue(), "El valor de la carta debe ser 12");
    }

    @Test
    public void testCartaSinImagenYNombres() {

        Carta carta = new Carta(5, "", "", 1);

        assertEquals("", carta.getName(), "El nombre debe ser una cadena vacía");
        assertEquals("", carta.getImagen(), "La imagen debe ser una cadena vacía");
        assertEquals(1, carta.getValue(), "El valor debe ser 1");
    }
}
