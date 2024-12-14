module com.example.cincuentazo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.junit.jupiter.api;
    requires org.junit.jupiter.engine;

    exports com.example.cincuentazo.controller;
    opens com.example.cincuentazo.controller to javafx.fxml;
    exports com.example.cincuentazo;
    opens com.example.cincuentazo to javafx.fxml;
    exports com.example.cincuentazo.model;
}