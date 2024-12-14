package com.example.cincuentazo.controller;

import com.example.cincuentazo.model.Player;
import com.example.cincuentazo.view.CincuentazoView;
import com.example.cincuentazo.view.MainView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;


public class CincuentazoController {

    protected CincuentazoGame juego;
    private int cartaIndex = 0;

    @FXML
    protected ImageView cart1Player1ImageView;
    @FXML
    private ImageView cart2Player1ImageView;
    @FXML
    private ImageView cart3Player1ImageView;
    @FXML
    private ImageView cart4Player1ImageView;

    @FXML
    private ImageView cart1Player2, cart2Player2, cart3Player2, cart4Player2;

    @FXML
    private ImageView cart1Player3, cart2Player3, cart3Player3, cart4Player3;

    @FXML
    private ImageView cart1Player4, cart2Player4, cart3Player4, cart4Player4;

    @FXML
    protected ImageView mazoImageView, cartaEnMesaImageView;

    @FXML
    protected HBox cartasJugador1HBox, cartasJugador2HBox;

    @FXML
    protected VBox cartasJugador3VBox, cartasJugador4VBox;

    @FXML
    private HBox CartasMesaHBox;

    @FXML
    public Label sumaMesaLabel, turnoDeJugadorLabel;

    @FXML
    protected Button newGameButton;

    /**
     * verifica e inicializa lo fundamental del juego
     */
    @FXML
    protected void initialize() {
        System.out.println("Controlador inicializado correctamente");
        if (cart1Player1ImageView == null) {
            cart1Player1ImageView = new ImageView();
            cart1Player1ImageView.setFitHeight(124.0);
            cart1Player1ImageView.setFitWidth(106.0);
        }
        if (cart2Player1ImageView == null) {
            cart2Player1ImageView = new ImageView();
            cart2Player1ImageView.setFitHeight(124.0);
            cart2Player1ImageView.setFitWidth(106.0);

        }
        if (cart3Player1ImageView == null) {
            cart3Player1ImageView = new ImageView();
            cart3Player1ImageView.setFitHeight(124.0);
            cart3Player1ImageView.setFitWidth(106.0);
        }
        if (cart4Player1ImageView == null) {
            cart4Player1ImageView = new ImageView();
            cart4Player1ImageView.setFitHeight(124.0);
            cart4Player1ImageView.setFitWidth(106.0);
        }
        if (cartaEnMesaImageView == null) {
            cartaEnMesaImageView = new ImageView();
            cartaEnMesaImageView.setFitHeight(150.0);
            cartaEnMesaImageView.setFitWidth(200.0);
        }
        if (cartasJugador1HBox == null){
            cartasJugador1HBox = new HBox();
        }
        if (cartasJugador2HBox == null){
            cartasJugador2HBox = new HBox();
        }
        if (cartasJugador3VBox == null){
            cartasJugador3VBox = new VBox();
        }
        if (cartasJugador4VBox == null){
            cartasJugador4VBox = new VBox();
        }

        int numJugadores = MainController.numJugadores;
        if (juego == null) {
            System.out.println("Inicializando el juego...");
            juego = new CincuentazoGame(this, numJugadores);
        }

    }

    /**
     * Check which card the player touched to hide it and send it to the table
     * @param event
     */
    @FXML
    void oncartasJugador1HBoxClicked(MouseEvent event) {
        String source = String.valueOf(event.getPickResult().getIntersectedNode().getId());
        System.out.println("Hiciste clic en la carta con ID: " + source);

        updateTableSum();

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
            juego.selectCardPlayer(cartaIndex);
            System.out.println("Se hizo clic en la carta " + cartaIndex);
        }
    }

    @FXML
    void onnewGameButtonClicked(MouseEvent event) throws IOException {
        MainView.getInstance();
        CincuentazoView.getInstance().close();
    }

    /**
     *
     */
    protected void updateShiftInterface() {
        if (juego.isMachineShift) {
            Player jugador = juego.getPlayers().get(juego.turnPlayer);
            if (juego.isMachineShift) {

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                System.out.println("Es el turno de la máquina, actualizando interfaz...");
            }));
            timeline.play();
            } else {
                showCardsPlayer();
            }
        }else{
            showCardsPlayer();
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    void onmazoImageViewClicked(MouseEvent event) {

        showCardsPlayer();

        juego.playDeck(0);

        cartasJugador1HBox.setDisable(true);

        updateLettersWithTime();

        mazoImageView.setDisable(true);

        juego.nextTurn();

    }

    /**
     * Show the player's cards
     */
    private void showCardsPlayer() {
        cart1Player1ImageView.setVisible(true);
        cart2Player1ImageView.setVisible(true);
        cart3Player1ImageView.setVisible(true);
        cart4Player1ImageView.setVisible(true);
    }

    /**
     * Update the card on the table
     * @param imagen
     */
    public void updateLetterOnTable(String imagen) {
        cartaEnMesaImageView.setImage(new Image(String.valueOf(getClass().getResource("/com/example/cincuentazo/" + imagen))));
        System.out.println("updateLetterOnTable imagen: " + imagen);
    }

    /**
     * Updates the visual of the player's cards
     * @param cartas
     */
    public void updateCardsPlayer(String[] cartas) {

        if (cartas != null && cartas.length > 0) {

            System.out.println("#######################");
            System.out.println(cartas[0]);
            if (cartas.length >= 1) {
                cart1Player1ImageView.setImage(new Image(String.valueOf(getClass().getResource("/com/example/cincuentazo/" + cartas[0]))));
            }
            System.out.println(cartas[1]);
            if (cartas.length >= 2) {
                cart2Player1ImageView.setImage(new Image(String.valueOf(getClass().getResource("/com/example/cincuentazo/" + cartas[1]))));
            }
            System.out.println(cartas[2]);
            if (cartas.length >= 3) {
                cart3Player1ImageView.setImage(new Image(String.valueOf(getClass().getResource("/com/example/cincuentazo/" + cartas[2]))));
            }
            System.out.println(cartas[3]);
            if (cartas.length >= 4) {
                cart4Player1ImageView.setImage(new Image(String.valueOf(getClass().getResource("/com/example/cincuentazo/" + cartas[3]))));
            }
        }
    }

    /**
     *  Wait a few seconds and update the player's cards
     */
    public void updateLettersWithTime() {

        String[] imagenesCartas = juego.getPicturesCardsPlayer(0);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            updateCardsPlayer(imagenesCartas);}));

        timeline.play();
    }

    /**
     *
     */
    public void updateTableSum() {
        int suma = juego.gameSum;
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), e -> {
            sumaMesaLabel.setText("Suma: " + juego.gameSum);
            juego.updateVisibilityPlayers();
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.play();
    }
}