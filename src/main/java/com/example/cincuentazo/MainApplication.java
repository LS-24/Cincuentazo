package com.example.cincuentazo;

import com.example.cincuentazo.view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MainView.getInstance();
    }

    public static void main(String[] args) {
        launch();
    }
}