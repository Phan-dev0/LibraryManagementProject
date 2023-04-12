/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.gr2.libraryproject;

import com.gr2.pojos.LibraryCard;
import com.gr2.pojos.User;
import com.gr2.services.LibraryCardService;
import com.gr2.services.UserService;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author vegar
 */
public class LoginController {

    /**
     * Initializes the controller class.
     */
    @FXML private TextField txtUsername;
    @FXML private PasswordField password;
    @FXML private Button btnLogin;
    @FXML private Label checkText;
    
    @FXML
    public void loginBtn(ActionEvent event) throws IOException, SQLException{
        UserService userService = new UserService();
        LibraryCardService librarycardService = new LibraryCardService();
        String loginUsername = txtUsername.getText();
        String loginPassword = password.getText();
        
        User user = userService.getLoginAccount(loginUsername, loginPassword);
        System.out.println(user.getUsername());
        if (user.getUsername() != null) {
            LibraryCard libCard = librarycardService.getLibraryCardById(user.getCardId());
            if(!UserSession.createSession(user, libCard.getSubject())){
                checkText.setText("ERROR....");
            }
            App loginToPrimary = new App();
            loginToPrimary.changeScene("primary");
        }
        else{
            checkText.setText("*Wrong username or password");
        }
        
           
    }
    
    
   
    
  
    
    
    
    
}
