/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.libraryproject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author vegar
 */
public class firstPage  implements Initializable {

    
    @FXML
    private BorderPane MainPane;
    @FXML
    private Button nav1;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadPage("login");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
    @FXML
    private void login() throws IOException{
        loadPage("login");
    }
    @FXML
    private void register() throws IOException{
        nav1.getStyleClass().add("nav");
        loadPage("register");
    }
    
    private void loadPage(String page) throws IOException{
        Parent view = new FXMLLoader(App.class.getResource(page +".fxml")).load();
        MainPane.setCenter(view);
    }
}
