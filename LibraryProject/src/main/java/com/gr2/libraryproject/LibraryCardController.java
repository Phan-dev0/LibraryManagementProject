/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.gr2.libraryproject;

import com.gr2.pojos.Book;
import com.gr2.pojos.Faculty;
import com.gr2.pojos.LibraryCard;
import com.gr2.services.FacultyService;
import com.gr2.services.LibraryCardService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Cuong0311
 */
public class LibraryCardController implements Initializable {

    @FXML private Text txtCode;
    @FXML private Text txtName;
    @FXML private Text txtGender;
    @FXML private Text txtBirthDate;
    @FXML private Text txtSubject;
    @FXML private Text txtFaculty;
    @FXML private Text txtExpire;
    @FXML private TextField txtEmail;
    @FXML private Text txtAddress;
    @FXML private TextField txtPhone;
    @FXML private Button saveButton;


    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserSession session = UserSession.getSession();
        LibraryCardService libCardService = new LibraryCardService();
        FacultyService facultyService = new FacultyService();
        try {
            LibraryCard libCard = libCardService.getLibraryCardById(session.getUser().getCardId());
            if (libCard != null) {
                txtCode.setText(session.getUser().getUsername());
                txtName.setText(libCard.getName());
                txtGender.setText(libCard.getGender() > 0 ? "Male" : "Female");
                txtBirthDate.setText(libCard.getBirthDate().toString());
                txtSubject.setText(libCard.getSubject());
                Faculty f = facultyService.getFaculty(libCard.getFaculty_id());
                if (f != null)
                    txtFaculty.setText(f.getName());
                else
                    txtFaculty.setText("");
                txtExpire.setText(libCard.getExpireDate().toString() +
                          " -> " + libCard.getExpireDate().plusYears(4).toString());
                txtEmail.setText(libCard.getEmail());
                txtAddress.setText(libCard.getAddress());
                txtPhone.setText(libCard.getPhoneNumber());
                saveButton.setOnAction(evt -> {
                  
                });
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(LibraryCardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
}
