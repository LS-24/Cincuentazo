package com.example.cincuentazo.controller;

import com.example.cincuentazo.model.Carta;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Random;

public class CincuentazoController {

    private CincuentazoGame juego;
    private int cartaIndex = -1;

    @FXML
    protected ImageView cart1Player1, cart2Player1, cart3Player1, cart4Player1;

    @FXML
    private ImageView cart1Player2, cart2Player2, cart3Player2, cart4Player2;

    @FXML
    private ImageView cart1Player3, cart2Player3, cart3Player3, cart4Player3;

    @FXML
    private ImageView cart1Player4, cart2Player4, cart3Player4, cart4Player4;

    @FXML
    protected ImageView mazoImageView, cartaEnMesaImageView;

    @FXML
    private HBox cartasJugador1HBox, cartasJugador2HBox, CartasMesaHBox;

    @FXML
    private VBox cartasJugador3HBox, cartasJugador4HBox;

    @FXML
    private Label sumaMesaLabel;

    protected MazoController mazo = new MazoController();


    /**
     *
     * @param event
     */
    @FXML
    void oncartasJugador1HBoxClicked(MouseEvent event) {
        String source = String.valueOf(event.getPickResult().getIntersectedNode().getId());
        System.out.println("Hiciste clic en la carta con ID: " + source);

        if (source.equals("cart1Player1")) {
            cartaIndex = 1;
        } else if (source.equals("cart2Player1")) {
            cartaIndex = 2;
        } else if (source.equals("cart3Player1")) {
            cartaIndex = 3;
        } else if (source.equals("cart4Player1")) {
            cartaIndex = 4;
        }

        System.out.println("Se hizo clic en la carta " + cartaIndex);

        if (cartaIndex != -1 && juego != null) {
            juego.seleccionarCartaJugador(cartaIndex);
            System.out.println("Se hizo clic en la carta " + cartaIndex);
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    void onmazoImageViewClicked(MouseEvent event) {

        Random random = new Random();

        int numeroAleatorio = random.nextInt(52) + 1;

        Carta carta = mazo.getCartaPorId(numeroAleatorio);

        if (carta != null) {
            System.out.println("ID: " + carta.getId());
            System.out.println("Valor: " + carta.getValor());
            System.out.println("Nombre: " + carta.getNombre());
            System.out.println("Imagen: " + carta.getImagen());
        }

        juego.tocarMazo(0);
        cartaEnMesaImageView.setImage(new Image(String.valueOf(getClass().getResource("/com/example/cincuentazo/" + carta.getImagen()))));
    }

    /**
     *
     * @param imagen
     */
    public void actualizarCartaEnMesa(String imagen) {
//        ArrayList<Carta> cartaACtualizar = new ArrayList<>();
//        cartaACtualizar.add(juego.cartasEnMesa.get(0));
//
//        if (juego.cartasEnMesa.isEmpty()) {
//
//            System.out.println("No hay cartas en la mesa para mostrar.");
//
//        } else {
//            cartaEnMesaImageView.setImage(new Image(String.valueOf(getClass().getResource("/com/example/cincuentazo/" + juego.cartasEnMesa.get(0).getImagen()))));
 //       System.out.println("actualizarCartaEnMesa"+"/com/example/cincuentazo/" + cartaACtualizar.get(0).getImagen());
//        }
    }

    /**
     *
     * @param cartas
     */
    public void actualizarCartasJugador(String[] cartas) {

    }
}