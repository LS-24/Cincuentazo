module com.example.cincuentazo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cincuentazo to javafx.fxml;
    exports com.example.cincuentazo;
    exports com.example.cincuentazo.controller;
    opens com.example.cincuentazo.controller to javafx.fxml;
}