/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.libraryproject;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 *
 * @author vegar
 */
public class RegisterController {
     @FXML
    public void switchToLogin(ActionEvent event) throws IOException{
        App register = new App();
        register.changeScene("login");
    }
}
