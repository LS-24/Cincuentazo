package com.example.cincuentazo.view;

import com.example.cincuentazo.controller.CincuentazoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class CincuentazoView extends Stage {

    private CincuentazoController controller;

    /**
     * Instantiate the window view
     * @throws IOException
     */
    public CincuentazoView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cincuentazo/cincuentazo-View.fxml"));
        Parent root = loader.load();
        CincuentazoController controller = loader.getController();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Cincuentazo");
        getIcons().add(new Image(String.valueOf(getClass().getResource("/com/example/cincuentazo/imagen/cartas-de-poker.png"))));
        setResizable(false);
        show();
    }

    public static CincuentazoView getInstance() throws IOException{
        if (CincuentazoViewHolder.INSTANCE == null){
            return CincuentazoViewHolder.INSTANCE = new CincuentazoView();
        }else{
            return CincuentazoViewHolder.INSTANCE;
        }
    }

    private static class CincuentazoViewHolder{
        private static CincuentazoView INSTANCE;
    }
}
