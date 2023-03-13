/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.gr2.libraryproject;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * FXML Controller class
 *
 * @author vegar
 */
public class LoginController {

    /**
     * Initializes the controller class.
     */
    @FXML
    public void switchToPrimary(ActionEvent event) throws IOException{
        App register = new App();
        register.changeScene("primary");
    }
    @FXML
    public void switchToRegister(ActionEvent event) throws IOException{
        App register = new App();
        register.changeScene("register");
    }
    
    
}
