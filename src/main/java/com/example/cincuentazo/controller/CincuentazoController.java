package com.example.cincuentazo.controller;

import com.example.cincuentazo.model.Carta;
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

import java.util.ArrayList;
import java.util.Random;

public class CincuentazoController {

    private CincuentazoGame juego;
    private int cartaIndex = -1;

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
    private HBox cartasJugador1HBox, cartasJugador2HBox, CartasMesaHBox;

    @FXML
    private VBox cartasJugador3HBox, cartasJugador4HBox;

    @FXML
    private Label sumaMesaLabel;

    @FXML
    private void initialize() {
        System.out.println("Controlador inicializado correctamente");
        if (cart1Player1ImageView == null) {
            System.out.println("cart1Player1ImageView es null. Creando uno nuevo.");
            cart1Player1ImageView = new ImageView();  // Crear uno nuevo de forma programática
            // Puedes configurar las propiedades del ImageView aquí si es necesario
            cart1Player1ImageView.setFitHeight(124.0);
            cart1Player1ImageView.setFitWidth(106.0);
            //cartasJugador1HBox.getChildren().add(cart1Player1ImageView);
            // Por ejemplo, si tienes un contenedor donde agregarlo:
            // myAnchorPane.getChildren().add(cart1Player1ImageView);
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
        startUpdatingWithTimeline();
    }

    public void actualizarCartaJugador(String imagenPath) {
        if (cart1Player1ImageView != null) {
            cart1Player1ImageView.setImage(new Image(imagenPath));
        } else {
            System.out.println("El ImageView es null.");
        }
    }

    protected MazoController mazo = new MazoController();


    /**
     *
     * @param event
     */
    @FXML
    void oncartasJugador1HBoxClicked(MouseEvent event) {
        String source = String.valueOf(event.getPickResult().getIntersectedNode().getId());
        System.out.println("Hiciste clic en la carta con ID: " + source);

        if (source.equals("cart1Player1ImageView")) {
            cartaIndex = 1;
            juego.seleccionarCartaJugador(cartaIndex);
            cartaEnMesaImageView.setImage(cart1Player1ImageView.getImage());
        } else if (source.equals("cart2Player1ImageView")) {
            cartaIndex = 2;
            cartaEnMesaImageView.setImage(cart2Player1ImageView.getImage());
        } else if (source.equals("cart3Player1ImageView")) {
            cartaIndex = 3;
            cartaEnMesaImageView.setImage(cart3Player1ImageView.getImage());
        } else if (source.equals("cart4Player1ImageView")) {
            cartaIndex = 4;
            cartaEnMesaImageView.setImage(cart4Player1ImageView.getImage());
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
//       ArrayList<Carta> cartaACtualizar = new ArrayList<>();
//        cartaACtualizar.add(juego.cartasEnMesa.get(0));
//
//        if (juego.cartasEnMesa.isEmpty()) {
//
//            System.out.println("No hay cartas en la mesa para mostrar.");
//
//        } else {
//            cartaEnMesaImageView.setImage(new Image(String.valueOf(getClass().getResource("/com/example/cincuentazo/" + juego.cartasEnMesa.get(0).getImagen()))));
//        System.out.println("actualizarCartaEnMesa"+"/com/example/cincuentazo/" + cartaACtualizar.get(0).getImagen());
//        }
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

    public void startUpdatingWithTimeline() {

        String[] imagenesCartas = juego.obtenerImagenesCartasJugador(0);


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {

            actualizarCartasJugador(imagenesCartas);
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}