/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.gr2.libraryproject;

import com.gr2.pojos.Book;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Cuong0311
 */
public class BookDetailController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Text txtDescription;
    @FXML
    private Text txtYear;
    @FXML
    private Text txtPlace;
    @FXML
    private Text txtImport;
    @FXML
    private Text txtLoc;
    @FXML
    private Text txtCate;
    @FXML
    private Text txtState;
    @FXML
    private Label lbTitle;
    @FXML
    private Label lbAuthors;
    @FXML private Button btnLend;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        
    }
    
    public void setBook(Book book) {
        this.lbTitle.setText(book.getTitle());
        this.lbAuthors.setText(book.getAuthors());
        this.txtDescription.setText(book.getDescription());
        this.txtYear.setText(Integer.toString(book.getPublishedYear()));
        this.txtPlace.setText(book.getPublishedPlace());
        this.txtImport.setText(book.getImportDate().toString());
        this.txtState.setText(book.getState());
        this.txtLoc.setText(book.getLocation());
    }
    
    public void lendBook(){
        btnLend.setOnAction(e ->{
        
        });
    }
}
