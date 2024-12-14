package com.example.cincuentazo.controller;

import com.example.cincuentazo.view.CincuentazoView;
import com.example.cincuentazo.view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import com.example.cincuentazo.model.alert.AlertBox;

import java.io.IOException;

public class MainController extends CincuentazoController {

    protected static int numJugadores = 1;

    @FXML
    private Button iniciarJuegoButton, instruccionesButton;;

    @FXML
    public TextField numeroDeJugadoresTextField;

    @FXML
    private MenuItem numeroJugadoresMenu;

    @FXML
    private MenuButton numerodeJugadoresMenuButton;

    /**
     * onIniciarJuegoButtonClicked
     *
     * @param event
     * @param
     * @throws IOException
     */
    @FXML
    public void onIniciarJuegoButtonClicked(MouseEvent event)throws IOException {

        int numJugadores = obtenerNumeroJugadores();

        CincuentazoGame juego = new CincuentazoGame(this, numJugadores);

        if (juego == null) {
            System.out.println("Error: El juego no se ha inicializado correctamente.");
        } else {
            System.out.println("Juego inicializado correctamente.");
        }

        System.out.println("Juego inicializado en el controlador: " + juego);

        CincuentazoView.getInstance();

        MainView.getInstance().close();

    }

    /**
     * onInstruccionesButtonClicked
     * @param event
     */
    @FXML
    void onInstruccionesButtonClicked(MouseEvent event)  {
       new AlertBox().showAlert("Cincuentazo",
               "Es un juego de cartas de Poker donde los jugadores (humano y maquina) deben sobrevivir utlizando sus cartas.\n"+
               "Se jugará contra 1, 2 o 3 jugadores máquina;\n"+
               "Cada jugador siempre debe tener una mano de 4 cartas que podrá jugar en su turno, siguiendo las siguientes reglas del juego:\n"+
               "• Regla principal: En la mesa existe una suma que no debe exceder el 50 (>50).\n" +
               "• Preparación: Del deck de cartas se reparten 4 cartas aleatorias a cada jugador, luego se colocará una carta aleatoria en la mesa boca arriba\n" +
               "(esta carta inicia la suma de la mesa) para así el jugador humano empezar a jugar;\n"+
               "el resto de las cartas se quedan en el deck boca abajo para luego ser tomadas por un jugador.\n" +
               "• Turno de juego: El juego se desarrolla por turnos.\n"+
               "En su turno, el jugador debe seleccionar una carta de su mano\n"+
               "teniendo en cuenta la regla principal y que:\n" +
               "• Todas las cartas con numeros del 2 al 8 y el 10 suman su numero.\n" +
               "• Todas las cartas con numero 9 ni suman ni restan.\n" +
               "• Todas las cartas con letras J, Q, K restan 10.\n" +
               "• Todas las cartas con letra A suman 1 o 10, según convenga.\n" +
               "la suma de la mesa será modificada con el valor de la carta anterior.\n"+
               "Luego, el mismo jugador deberá tomar una carta del deck \n" +
               "para que siempre cada jugador tenga 4 cartas en su mano.\n" +
               "En caso contrario, que el jugador no pueda jugar ninguna carta de su mano porque excede la suma de 50 en la mesa, este quedará eliminado.\n" +
               "• Fin del juego: El objetivo del juego es ser el último jugador en quedar en juego."
       );
    }

    @FXML
    void onNumerodeJUgadoresAction(ActionEvent event) {

    }

    @FXML
    void onitem1(ActionEvent event) {
        numeroDeJugadoresTextField.setText("2 jugadores en la mesa");
        iniciarJuegoButton.setDisable(false);
    }

    @FXML
    void onitem2(ActionEvent event) {
        numeroDeJugadoresTextField.setText("3 jugadores en la mesa");
        iniciarJuegoButton.setDisable(false);
    }

    @FXML
    void onitem3(ActionEvent event) {
        numeroDeJugadoresTextField.setText("4 jugadores en la mesa");
        iniciarJuegoButton.setDisable(false);
    }

    /**
     * obtenerNumeroJugadores
     * Obtiene el número de jugadores desde el campo de texto.
     * @return
     * Número de jugadores
     */
    public int obtenerNumeroJugadores() {

        String texto = numeroDeJugadoresTextField.getText();

        switch (texto) {
            case "2 jugadores en la mesa":
                numJugadores = 2;
                break;
            case "3 jugadores en la mesa":
                numJugadores = 3;
                break;
            case "4 jugadores en la mesa":
                numJugadores = 4;
                break;
        }
        return numJugadores;
    }
}