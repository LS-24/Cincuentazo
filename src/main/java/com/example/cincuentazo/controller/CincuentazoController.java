package com.example.cincuentazo.controller;

import com.example.cincuentazo.model.Carta;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Random;

public class CincuentazoController {

    @FXML
    private ImageView cart1Player1, cart2Player1, cart3Player1, cart4Player1;

    @FXML
    private ImageView cart1Player2, cart2Player2, cart3Player2, cart4Player2;

    @FXML
    private ImageView cart1Player3, cart2Player3, cart3Player3, cart4Player3;

    @FXML
    private ImageView cart1Player4, cart2Player4, cart3Player4, cart4Player4;

    @FXML
    private ImageView cartaEnMesaImageView, mazoImageView;

    @FXML
    private HBox cartasJugador1HBox, cartasJugador2HBox;

    @FXML
    private VBox cartasJugador3HBox, cartasJugador4HBox;

    @FXML
    private Label sumaMesaLabel;

    /**
     *
     * @param event
     */
    @FXML
    void oncartasJugador1HBoxClicked(MouseEvent event) {

        Object source = event.getSource();

        if (source == cart1Player1) {
            System.out.println("Se hizo clic en la imagen 1");
        } else if (source == cart2Player1) {
            System.out.println("Se hizo clic en la imagen 2");
        } else if (source == cart3Player1) {
            System.out.println("Se hizo clic en la imagen 3");
        } else if (source == cart4Player1) {
            System.out.println("Se hizo clic en la imagen 4");
        }

        System.out.println(event.getPickResult());

    }

    /**
     *
     * @param event
     */
    @FXML
    void onmazoImageViewClicked(MouseEvent event) {

        Random random = new Random();

        int numeroAleatorio = random.nextInt(52) + 1;

        MazoController mazo = new MazoController();

        Carta carta = mazo.getCartaPorId(numeroAleatorio);

        if (carta != null) {
            System.out.println("ID: " + carta.getId());
            System.out.println("Valor: " + carta.getValor());
            System.out.println("Nombre: " + carta.getNombre());
            System.out.println("Imagen: " + carta.getImagen());
        }

        cargarImagen( "/" + carta.getImagen() );
        cartaEnMesaImageView.setImage(new Image("file:/" + carta.getImagen()));
    }

    /**
     *
     * @param rutaRelativa
     * @return
     */
    private Image cargarImagen(String rutaRelativa) {
        URL url = getClass().getResource("/" + rutaRelativa);

        if (url != null) {
            return new Image(url.toExternalForm());
        } else {
            System.out.println("Imagen no encontrada: " + rutaRelativa);
            return null;
        }
    }
}