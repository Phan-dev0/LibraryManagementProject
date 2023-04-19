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
import com.gr2.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Cuong0311
 */
public class LibraryCardController implements Initializable {

    @FXML
    private Text txtCode;
    @FXML
    private Text txtName;
    @FXML
    private Text txtGender;
    @FXML
    private Text txtBirthDate;
    @FXML
    private Text txtSubject;
    @FXML
    private Text txtFaculty;
    @FXML
    private Text txtExpire;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtPhone;
    @FXML
    private Button saveButton;
    @FXML
    private AnchorPane anchorPane;

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
                txtGender.setText(libCard.getGender() == 1 ? "Male" : "Female");
                txtBirthDate.setText(libCard.getBirthDate().toString());
                txtSubject.setText(libCard.getSubject());
                Faculty f = facultyService.getFaculty(libCard.getFaculty_id());
                if (f != null) {
                    txtFaculty.setText(f.getName());
                } else {
                    txtFaculty.setText("");
                }
                txtExpire.setText(libCard.getExpireDate().toString()
                        + " -> " + libCard.getExpireDate().plusYears(4).toString());
                txtEmail.setText(libCard.getEmail());
                txtAddress.setText(libCard.getAddress());
                txtPhone.setText(libCard.getPhoneNumber());
                saveButton.setOnAction(evt -> {
                    clearErrorMsg();
                    String address = txtAddress.getText();
                    String email = txtEmail.getText();
                    String phoneNumber = txtPhone.getText();

                    Pattern emailPattern = Pattern.compile("^[\\w-\\.]+@([\\w-\\.])+[\\w-]{2,4}$");
                    if (email == null || !emailPattern.matcher(email).matches()) {
                        createErrorMsg(txtEmail, "Your new email is not valid!");
                    } else {
                        libCard.setEmail(txtEmail.getText());
                    }
                    Pattern phoneNumberPattern = Pattern.compile("^\\d{10,11}$");
                    if (phoneNumber == null || !phoneNumberPattern.matcher(phoneNumber).matches()) {
                        createErrorMsg(txtPhone, "Your new phone number is not valid!");
                    } else {
                        libCard.setPhoneNumber(txtPhone.getText());
                    }
                    
                    if (address == null) {
                        createErrorMsg(txtAddress, "Your new address is not valid!");
                    } else {
                        libCard.setAddress(txtAddress.getText());
                    }

                    try {
                        if (libCardService.updateLibraryCard(libCard)) {
                            MessageBox.getMessageBox("Info", "Update card successfully!", Alert.AlertType.INFORMATION).show();
                        }
                        
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                });

            }
        } catch (SQLException ex) {
            Logger.getLogger(LibraryCardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void createErrorMsg(TextField txt, String msg) {
        HBox hbox = (HBox) txt.getParent();
        Label lbError = new Label();
        lbError.setStyle("-fx-text-fill: red");
        lbError.setText(msg);
        hbox.getChildren().add(lbError);
    }
    
    public void clearErrorMsg() {
        List<TextField> textfields = new ArrayList<>(List.of(this.txtEmail, this.txtPhone, this.txtAddress));
        textfields.forEach(txt -> {
            List<Node> nodes = ((HBox)txt.getParent()).getChildren();
            if (!(nodes.get(nodes.size() - 1) instanceof Label)) return; 
            nodes.remove(nodes.get(nodes.size() - 1));
        });
    }
}
