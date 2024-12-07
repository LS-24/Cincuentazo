package com.example.cincuentazo.controller;

import com.example.cincuentazo.model.Carta;
import com.example.cincuentazo.model.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CincuentazoController {

    protected MazoController mazo = new MazoController();
    private CincuentazoGame juego;
    private int cartaIndex = 0;

    @FXML
    private ImageView cart1Player1ImageView, cart2Player1ImageView, cart3Player1ImageView, cart4Player1ImageView;

    @FXML
    private ImageView cart1Player2, cart2Player2, cart3Player2, cart4Player2;

    @FXML
    private ImageView cart1Player3, cart2Player3, cart3Player3, cart4Player3;

    @FXML
    private ImageView cart1Player4, cart2Player4, cart3Player4, cart4Player4;

    @FXML
    protected ImageView mazoImageView, cartaEnMesaImageView;

    @FXML
    protected HBox cartasJugador1HBox;
    @FXML
    private HBox cartasJugador2HBox;
    @FXML
    private HBox CartasMesaHBox;

    @FXML
    private VBox cartasJugador3HBox, cartasJugador4HBox;

    @FXML
    public Label sumaMesaLabel;

    /**
     *
     */
    @FXML
    private void initialize() {
        System.out.println("Controlador inicializado correctamente");
        if (cart1Player1ImageView == null) {
            System.out.println("cart1Player1ImageView es null. Creando uno nuevo.");
            cart1Player1ImageView = new ImageView();
            cart1Player1ImageView.setFitHeight(124.0);
            cart1Player1ImageView.setFitWidth(106.0);
        } else {
            System.out.println("cart1Player1ImageView está inicializado.");
        }
        if (cart2Player1ImageView == null) {
            System.out.println("cart2Player1ImageView es null. Creando uno nuevo.");
            cart2Player1ImageView = new ImageView();
            cart2Player1ImageView.setFitHeight(124.0);
            cart2Player1ImageView.setFitWidth(106.0);

        } else {
            System.out.println("cart1Player1ImageView está inicializado.");
        }
        if (cart3Player1ImageView == null) {
            System.out.println("cart3Player1ImageView es null. Creando uno nuevo.");
            cart3Player1ImageView = new ImageView();
            cart3Player1ImageView.setFitHeight(124.0);
            cart3Player1ImageView.setFitWidth(106.0);

        } else {
            System.out.println("cart1Player1ImageView está inicializado.");
        }
        if (cart4Player1ImageView == null) {
            System.out.println("cart4Player1ImageView es null. Creando uno nuevo.");
            cart4Player1ImageView = new ImageView();
            cart4Player1ImageView.setFitHeight(124.0);
            cart4Player1ImageView.setFitWidth(106.0);
        } else {
            System.out.println("cart1Player1ImageView está inicializado.");
        }
        if (cartaEnMesaImageView == null) {
            System.out.println("cartaEnMesaImageView es null. Creando uno nuevo.");
            cartaEnMesaImageView = new ImageView();
            cartaEnMesaImageView.setFitHeight(150.0);
            cartaEnMesaImageView.setFitWidth(200.0);
        }else {
            System.out.println("cartaEnMesaImageView está inicializado.");
        }
        int numJugadores = MainController.numJugadores;
        if (juego == null) {
            System.out.println("Inicializando el juego...");
            juego = new CincuentazoGame(this, numJugadores);
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    void oncartasJugador1HBoxClicked(MouseEvent event) {
        String source = String.valueOf(event.getPickResult().getIntersectedNode().getId());
        System.out.println("Hiciste clic en la carta con ID: " + source);

        if (source.equals("cart1Player1ImageView")) {
            cartaIndex = 0;
            cartaEnMesaImageView.setImage(cart1Player1ImageView.getImage());
            cart1Player1ImageView.setVisible(false);
        } else if (source.equals("cart2Player1ImageView")) {
            cartaIndex = 1;
            cartaEnMesaImageView.setImage(cart2Player1ImageView.getImage());
            cart2Player1ImageView.setVisible(false);
        } else if (source.equals("cart3Player1ImageView")) {
            cartaIndex = 2;
            cartaEnMesaImageView.setImage(cart3Player1ImageView.getImage());
            cart3Player1ImageView.setVisible(false);
        } else if (source.equals("cart4Player1ImageView")) {
            cartaIndex = 3;
            cartaEnMesaImageView.setImage(cart4Player1ImageView.getImage());
            cart4Player1ImageView.setVisible(false);
        }

        System.out.println("Se hizo clic en la carta " + cartaIndex);

        if (cartaIndex != -1 && juego != null) {
            juego.seleccionarCartaJugador(cartaIndex);
            System.out.println("Se hizo clic en la carta " + cartaIndex);
        }
    }

    /**
     *
     */
    protected void actualizarInterfazDeTurno() {
        if (juego.esTurnoDeMaquina) {
            Player jugador = juego.getJugadores().get(juego.turnoJugador);
            if (juego.esTurnoDeMaquina) {


            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                System.out.println("Es el turno de la máquina, actualizando interfaz...");
            }));
            timeline.play();
            } else {
                mostrarCartasJugador();
            }
        }else{
            mostrarCartasJugador();
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    void onmazoImageViewClicked(MouseEvent event) {

        mostrarCartasJugador();

        juego.tocarMazo(0);

        cartasJugador1HBox.setDisable(true);

        startUpdatingWithTimeline();

        juego.siguienteTurno();

    }

    /**
     *
     */
    private void mostrarCartasJugador() {
        cart1Player1ImageView.setVisible(true);
        cart2Player1ImageView.setVisible(true);
        cart3Player1ImageView.setVisible(true);
        cart4Player1ImageView.setVisible(true);
    }

    /**
     *
     * @param imagen
     */
    public void actualizarCartaEnMesa(String imagen) {
        cartaEnMesaImageView.setImage(new Image(String.valueOf(getClass().getResource("/com/example/cincuentazo/" + imagen))));
        System.out.println("actualizarCartaEnMesa imagen: " + imagen);
    }

    /**
     *
     * @param cartas
     */
    public void actualizarCartasJugador(String[] cartas) {

        if (cartas != null && cartas.length > 0) {

            if (cartas.length >= 1) {
                cart1Player1ImageView.setImage(new Image(String.valueOf(getClass().getResource("/com/example/cincuentazo/" + cartas[0]))));
            }
            if (cartas.length >= 2) {
                cart2Player1ImageView.setImage(new Image(String.valueOf(getClass().getResource("/com/example/cincuentazo/" + cartas[1]))));
            }
            if (cartas.length >= 3) {
                cart3Player1ImageView.setImage(new Image(String.valueOf(getClass().getResource("/com/example/cincuentazo/" + cartas[2]))));
            }
            if (cartas.length >= 4) {
                cart4Player1ImageView.setImage(new Image(String.valueOf(getClass().getResource("/com/example/cincuentazo/" + cartas[3]))));
            }
        }
    }

    /**
     *
     */
    public void startUpdatingWithTimeline() {

        String[] imagenesCartas = juego.obtenerImagenesCartasJugador(0);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {actualizarCartasJugador(imagenesCartas);}));

        timeline.play();
    }
}