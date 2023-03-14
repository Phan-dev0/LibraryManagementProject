package com.gr2.libraryproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collection;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stg;

    @Override
    public void start(Stage stage) throws IOException {
        stg = stage;
        scene = new Scene(loadFXML("firstPage"));
        stage.setScene(scene);
        stage.show();
    }
   
    public void changeScene(String fxml) throws IOException{
        scene = new Scene(loadFXML(fxml));
        stg.setScene(scene);
        stg.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
   

    public static void main(String[] args) {
        launch();
    }

}