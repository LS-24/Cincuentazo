package com.example.cincuentazo.controller;

import com.example.cincuentazo.model.Carta;
import com.example.cincuentazo.model.Player;
import com.example.cincuentazo.model.alert.AlertBox;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.util.Duration;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CincuentazoGame {

    private CincuentazoController controller;
    public ArrayList<Player> players;
    public ArrayList<Carta> deck;
    public ArrayList<Carta> lettersOnTable;
    protected ArrayList<Boolean> isMachine;
    public int turnPlayer;
    protected boolean isMachineShift;
    public int gameSum;

    /**
     * @param controller
     * @param numJugadores
     */
    public CincuentazoGame(CincuentazoController controller, int numJugadores) {
        if (controller == null) {
            throw new IllegalArgumentException("El controlador no puede ser null");
        }
        this.controller = controller;
        players = new ArrayList<>();
        lettersOnTable = new ArrayList<>();
        isMachine = new ArrayList<Boolean>();
        deck = new MazoController().getLetters();
        turnPlayer = 0;
        isMachineShift = false;
        gameSum = 0;
        starGame(numJugadores);

        System.out.println("Juego iniciado correctamente.");
        System.out.println("Número de players: " + players.size());
        System.out.println("Número de cartas en el deck: " + deck.size());
        System.out.println("Número de cartas en la mesa: " + lettersOnTable.size());
    }

    /**
     * Change the turn to the next player
     */
    public void nextTurn() {
        verifyWinner();

        turnPlayer = (turnPlayer + 1) % players.size();

        if (isMachine.get(turnPlayer)) {
            isMachineShift = true;

            PauseTransition pausa = new PauseTransition(Duration.seconds(4 + (int)(Math.random() * 2)));
            pausa.setOnFinished(event -> {
                playMachineTurn();
            });
            pausa.play();

        } else {
            isMachineShift = false;
            controller.cartasJugador1HBox.setDisable(false);
            controller.mazoImageView.setDisable(false);
            controller.updateShiftInterface();
        }
        controller.turnoDeJugadorLabel.setText("Turno de Jugador: " + turnPlayer);
        System.out.println("Turno del jugador: " + turnPlayer);
        System.out.println("Cartas restantes en el deck: " + deck.size());
        System.out.println("Cartas jugadas en la mesa: " + lettersOnTable.size());
    }

    /**
     * Manage the machine shift
     */
    public void playMachineTurn() {
        Player playerMachine = players.get(turnPlayer);
        ArrayList<Carta> handMachine = playerMachine.getMano();

        if (!validateCardsToPlay(playerMachine)) {
            deck.addAll(handMachine);;
            System.out.println("EL jugador no tiene cartas válidas para jugar y ha sido eliminado.");
            players.remove(playerMachine);

            verifyWinner();

            nextTurn();
            return;
        }

        Random random = new Random();
        int cartaIndex = random.nextInt(handMachine.size());
        Carta selectedLetter = handMachine.get(cartaIndex);

        while (gameSum + selectedLetter.getValue() > 50) {
            System.out.println("La máquina no puede jugar la carta " + selectedLetter.getName() + " porque excede la suma de 50.");
            cartaIndex = random.nextInt(handMachine.size());
            selectedLetter = handMachine.get(cartaIndex);
        }

        System.out.println("La máquina ha jugado: " + selectedLetter.getName());
        possiblePlayCard(playerMachine, selectedLetter);

        if (playerMachine.getMano().size() < 4 && !deck.isEmpty()) {
            Carta cartaDelMazo = deck.remove(deck.size() - 1);
            playerMachine.addLetter(cartaDelMazo);
            System.out.println("La máquina ha tomado una carta del deck: " + cartaDelMazo.getName());
        }

        if (deck.isEmpty()) {
            returnCardsToDeck();
        }

        nextTurn();
    }

    /**
     * Method to start the game
     * @param numJugadores
     */
    private void starGame(int numJugadores) {

        controller.cartasJugador1HBox.setVisible(false);
        controller.cartasJugador2HBox.setVisible(false);
        controller.cartasJugador3VBox.setVisible(false);
        controller.cartasJugador4VBox.setVisible(false);

        for (int i = 1; i <= numJugadores; i++) {
            boolean esMaquinaJugador = (i > 1);
            isMachine.add(esMaquinaJugador);

            ArrayList<Carta> mano = dealCards();
            players.add(new Player("Jugador " + i, mano));
        }

        updateVisibilityPlayers();

        placeFirstCardOnTable();

        controller.updateCardsPlayer(getPicturesCardsPlayer(0));
    }

    /**
     * Visually shows the players
     */
    protected void updateVisibilityPlayers() {

        switch (players.size()) {
            case 1:
                controller.cartasJugador1HBox.setVisible(true);
                controller.cartasJugador2HBox.setVisible(false);
                controller.cartasJugador3VBox.setVisible(false);
                controller.cartasJugador4VBox.setVisible(false);
                break;
            case 2:
                controller.cartasJugador1HBox.setVisible(true);
                controller.cartasJugador2HBox.setVisible(true);
                controller.cartasJugador3VBox.setVisible(false);
                controller.cartasJugador4VBox.setVisible(false);
                break;
            case 3:
                controller.cartasJugador1HBox.setVisible(true);
                controller.cartasJugador2HBox.setVisible(true);
                controller.cartasJugador3VBox.setVisible(true);
                controller.cartasJugador4VBox.setVisible(false);
                break;
            case 4:
                controller.cartasJugador1HBox.setVisible(true);
                controller.cartasJugador2HBox.setVisible(true);
                controller.cartasJugador3VBox.setVisible(true);
                controller.cartasJugador4VBox.setVisible(true);
                break;
            default:
                break;
        }
    }

    /**
     *  Visually hide eliminated players
     * @param jugador
     */
    private void hidePlayerContainer(Player jugador) {
        int index = players.indexOf(jugador);

        switch (index) {
            case 0:
                controller.cartasJugador1HBox.setVisible(false);
                break;
            case 1:
                controller.cartasJugador2HBox.setVisible(false);
                break;
            case 2:
                controller.cartasJugador3VBox.setVisible(false);
                break;
            case 3:
                controller.cartasJugador4VBox.setVisible(false);
                break;
            default:
                break;
        }
    }

    /**
     * Place the initial card
     */
    private void placeFirstCardOnTable() {
        int initialSum;

        Random random = new Random();
        Carta cartaInicial = deck.get(random.nextInt(deck.size()));
        lettersOnTable.add(cartaInicial);
        deck.remove(cartaInicial);

        controller.updateLetterOnTable(cartaInicial.getImagen());
        System.out.println("Carta en mesa: " + cartaInicial.getImagen());
        initialSum = 0;
        for (Carta carta : lettersOnTable) {
            initialSum += carta.getValue();
        }
        gameSum = initialSum;
    }

    /**
     * Gets the images of the player's cards
     * @param jugadorIndex
     * @return
     */
    protected String[] getPicturesCardsPlayer(int jugadorIndex) {
        Player jugador = players.get(jugadorIndex);
        ArrayList<Carta> mano = jugador.getMano();
        String[] imagenes = new String[mano.size()];

        for (int i = 0; i < mano.size(); i++) {
            imagenes[i] = mano.get(i).getImagen();
        }
        return imagenes;
    }

    /**
     * Deal the cards
     * @return
     */
    public ArrayList<Carta> dealCards() {
        ArrayList<Carta> manoJugador = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Carta carta = deck.remove(0);
            manoJugador.add(carta);
        }
        return manoJugador;
    }

    /**
     * return the players
     * @return
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Method for the player to select a card
     * @param cartaIndex
     */
    public void selectCardPlayer(int cartaIndex) {
        if (cartaIndex < 0 || cartaIndex >= players.get(0).getMano().size()) {
            System.out.println("Índice de carta inválido: " + cartaIndex);
            return;
        }

        Player jugador = players.get(turnPlayer);
        Carta cartaSeleccionada = jugador.getMano().get(cartaIndex);
        System.out.println("El jugador " + jugador.getName() + " ha jugado: " + cartaSeleccionada.getName());

        System.out.println("Jugador seleccionado: " + jugador.getName());
        System.out.println("Carta seleccionada: " + cartaSeleccionada.getName());

        if (gameSum + cartaSeleccionada.getValue() > 50) {
            new AlertBox().showAlert("ERROR", "no puede jugar esta carta porque excede la suma de 50.");
        }

        possiblePlayCard(jugador, cartaSeleccionada);

        System.out.println("Cartas restantes en la mano de " + jugador.getName() + ":");
        jugador.getMano().forEach(c -> System.out.println(c.getName()));

    }

    /**
     * allows the player to take a card from the deck
     * @param jugadorIndex
     */
    public void playDeck(int jugadorIndex) {
        Player jugador = players.get(jugadorIndex);

        if (jugador.getMano().size() < 4) {
            if (!deck.isEmpty()) {
                Carta cartaDelMazo = deck.remove(deck.size() - 1);
                jugador.addLetter(cartaDelMazo);

                controller.updateCardsPlayer(getPicturesCardsPlayer(jugadorIndex));
                System.out.println("El jugador " + jugador.getName() + " ha tomado una carta del deck: " + cartaDelMazo.getName());
            } else {
                returnCardsToDeck();
            }
        } else {
            System.out.println("El jugador " + jugador.getName() + " no puede tomar del deck porque ya tiene 4 cartas.");
        }
    }

    private int selectAsValue() {
        String[] opciones = {"1", "10"};
        int seleccion = JOptionPane.showOptionDialog(null, "Selecciona el valor del As", "Valor del As",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
        if (seleccion == 0) {
            return 1;
        } else {
            return 10;
        }
    }

    /**
     * Return cards to the deck when it becomes empty.
     */
    private void returnCardsToDeck() {
        if (!lettersOnTable.isEmpty()) {
            ArrayList<Carta> lettersToReturn = new ArrayList<>(lettersOnTable);
            Carta lastLetter = lettersToReturn.remove(lettersToReturn.size() - 1);

            deck.addAll(lettersToReturn);
            lettersOnTable.clear();

            lettersOnTable.add(lastLetter);

            Collections.shuffle(deck);

            System.out.println("El deck se ha rellenado.");
        }
    }

    /**
     *  Update the sum of the cards
     */
    public void updateSumTable(Carta carta) {
        int letterValue = carta.getValue();
//        if (carta.getValue() == 1) {
//            if (isMachineShift) {
//                letterValue = decideAsValue();
//            } else {
//                letterValue = selectAsValue();
//            }
//        }
        gameSum += letterValue;
        System.out.println("Suma de la mesa: " + gameSum);
    }

    private int decideAsValue() {
        if (gameSum < 41) {
            return 10;
        } else {
            return 1;
        }
    }

    /**
     *  Check if the player has cards they can play
      * @param jugador
     * @return
     */
    public boolean validateCardsToPlay(Player jugador) {
        ArrayList<Carta> manoJugador = jugador.getMano();

        for (Carta carta : manoJugador) {
            if (gameSum + carta.getValue() <= 50) {
                return true;
            }
        }
        return false;
    }

    /**
     *  Check if a card can be played
     * @param jugador
     * @param cartaSeleccionada
     */
    public void possiblePlayCard(Player jugador, Carta cartaSeleccionada) {
        if (gameSum + cartaSeleccionada.getValue() <= 50) {

            lettersOnTable.add(cartaSeleccionada);
            jugador.deleteLetter(cartaSeleccionada);
            controller.updateLetterOnTable(cartaSeleccionada.getImagen());
            updateSumTable(cartaSeleccionada);
            controller.cartasJugador1HBox.setDisable(true);

            if (!validateCardsToPlay(jugador)) {//If the player has no cards to play, add the cards to the deck and eliminate it.
                deck.addAll(jugador.getMano());
                System.out.println(jugador.getName() + " no tiene cartas válidas para jugar y ha sido eliminado.");
                players.remove(jugador);
                hidePlayerContainer(jugador);
                verifyWinner();
            }
        } else {
            System.out.println(jugador.getName() + " no puede jugar esta carta porque excede la suma de 50.");
        }

    }

    /**
     *  Check if there is a winner
     */
    public void verifyWinner() {
        if (players.size() == 1) {
            Player ganador = players.get(0);
            declareWinner(ganador);
        }
    }

    /**
     *  Indicates the winner
     * @param jugador
     */
    private void declareWinner(Player jugador) {
        controller.newGameButton.setVisible(true);
        controller.cartasJugador1HBox.setDisable(true);
        controller.cartasJugador2HBox.setDisable(true);
        controller.cartasJugador3VBox.setDisable(true);
        controller.cartasJugador4VBox.setDisable(true);
        controller.cartasJugador1HBox.setDisable(true);

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ganador");
            alert.setHeaderText("¡Felicidades!");
            alert.setContentText("¡El jugador ha ganado "+ players.get(0) +"!");

            alert.showAndWait();
        });
    }
}