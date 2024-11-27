package com.example.cincuentazo.controller;


import com.example.cincuentazo.view.CincuentazoView;
import com.example.cincuentazo.view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MainController {

    @FXML
    private Button iniciarJuegoButton;

    @FXML
    private Button instruccionesButton;

    @FXML
    private TextField numeroDeJugadoresTextField;

    @FXML
    private MenuItem numeroJugadoresMenu;

    @FXML
    private MenuButton numerodeJugadoresMenuButton;

    @FXML
    void onIniciarJuegoButtonClicked(MouseEvent event)throws IOException {
        CincuentazoView.getInstance();
        MainView.getInstance().close();
    }

    @FXML
    void onInstruccionesButtonClicked(MouseEvent event)  {

    }

    @FXML
    void onNumerodeJUgadoresAction(ActionEvent event) {

    }

    @FXML
    void onitem1(ActionEvent event) {
        numeroDeJugadoresTextField.setText("2 jugadores en la mesa");
    }

    @FXML
    void onitem2(ActionEvent event) {
        numeroDeJugadoresTextField.setText("3 jugadores en la mesa");
    }

    @FXML
    void onitem3(ActionEvent event) {
        numeroDeJugadoresTextField.setText("4 jugadores en la mesa");
    }

}