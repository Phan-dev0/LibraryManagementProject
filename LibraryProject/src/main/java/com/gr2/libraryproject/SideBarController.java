package com.gr2.libraryproject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class SideBarController implements Initializable {
    
   
    @FXML
    private BorderPane MainPane;
    @FXML
    private Button nav1;
    
    
   @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        try {
            loadPage("availableBooksPage");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
    @FXML
    private void availableBooksPage(ActionEvent event) throws IOException{
        loadPage("availableBooksPage");
    }
    @FXML
    private void returnBooksPage(ActionEvent event) throws IOException{
        nav1.getStyleClass().add("nav");
        loadPage("returnBooksPage");
    }
    @FXML
    private void saveBooksPage(ActionEvent event) throws IOException{
        nav1.getStyleClass().add("nav");
        loadPage("saveBooksPage");
    }
    @FXML
    private void historyPage(ActionEvent event) throws IOException{
        nav1.getStyleClass().add("nav");
        loadPage("historyPage");
    }
    
    @FXML
    private void switchToLogin(ActionEvent event) throws IOException{
        App signOut = new App();
        signOut.changeScene("login");
    }
    
    private void loadPage(String page) throws IOException{
        Parent view = new FXMLLoader(App.class.getResource(page +".fxml")).load();
        MainPane.setCenter(view);
    }
   
  
}
