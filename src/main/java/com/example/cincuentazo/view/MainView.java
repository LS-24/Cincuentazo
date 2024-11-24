package com.example.cincuentazo.view;

import com.example.cincuentazo.controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView extends Stage {

    private MainController controller;

    public MainView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cincuentazo/main-View.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Cincuentazo");
        getIcons().add(new Image(String.valueOf(getClass().getResource("/com/example/cincuentazo/imagen/cartas-de-poker.png"))));
        setResizable(false);
        show();
    }

    public static MainView getInstance() throws IOException{
        if (mainViewHolder.INSTANCE == null){
            return mainViewHolder.INSTANCE = new MainView();
        }else{
            return mainViewHolder.INSTANCE;
        }
    }

    private static class mainViewHolder{
        private static MainView INSTANCE;
    }
}
