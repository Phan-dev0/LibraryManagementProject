/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.gr2.libraryproject;

import com.gr2.pojos.Faculty;
import com.gr2.services.FacultyService;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Cuong0311
 */
public class LibraryCardController implements Initializable {

//    @FXML private TextField txtUsername;
//    @FXML private TextField txtPassword;
//    @FXML private TextField txtComfirmPassword;
//    @FXML private TextField txtYourName;
//    @FXML private RadioButton radioBtnMale;
//    @FXML private RadioButton radioBtnFemale;
//    @FXML private DatePicker dateRegister;
//    @FXML private ComboBox cbFaculty;
//    @FXML private Button btnSubmit;


    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        FacultyService fs = new FacultyService();
//        try{
//            List<Faculty> faculty = fs.getFaculties();
//            this.cbFaculty.setItems(FXCollections.observableList(faculty));
//            
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(LibraryCardController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }    
    
    public void getYourName(){
    }
}
