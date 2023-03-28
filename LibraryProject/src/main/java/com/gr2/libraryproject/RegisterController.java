/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.libraryproject;

import com.gr2.pojos.Faculty;
import com.gr2.pojos.LibraryCard;
import com.gr2.pojos.User;
import com.gr2.services.FacultyService;
import com.gr2.services.LibraryCardService;
import com.gr2.services.UserService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 *
 * @author vegar
 */
public class RegisterController implements Initializable {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtComfirmPassword;
    @FXML private TextField txtYourName;
    @FXML private RadioButton radioBtnMale, radioBtnFemale;
    @FXML private DatePicker birthDate;
    @FXML private ComboBox cbFaculty;
    @FXML private Button btnSubmit;
    // Label
    @FXML private Label lbWrongYourname;
    @FXML private Label lbNotifyYourname;
    @FXML private Label lbWrongUsername;
    @FXML private Label lbNotifyUsername;
    @FXML private Label lbWrongPassword;
    @FXML private Label lbNotifyPassword;
    @FXML private Label lbWrongComfirmPassword;
    @FXML private Label lbNotifyComfirmPassword;
    @FXML private Label lbWrongSex;
    @FXML private Label lbNotifySex;
    @FXML private Label lbWrongDate;
    @FXML private Label lbWrongFaculty;
    @FXML private Label lbNotifyDate;
    @FXML private Label lbNotifyFaculty;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FacultyService fs = new FacultyService();
        try{
            List<Faculty> faculty = fs.getFaculties();
            List<String> facultyName = new ArrayList<>(); // lay ra facultyName de bo vao combo box
            for(Faculty f: faculty){
                facultyName.add(f.getName());
            }
            this.cbFaculty.setItems(FXCollections.observableArrayList(facultyName)); 
            // co the set faculty vao trong vi co phuong thuc toString 
            // nhung ma luc nay cac combox kieu du lieu cua no la faculty ko phai la String
            // -> kho xu li
            
            
        } catch (SQLException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 

    @FXML
    public void addAccount(ActionEvent event) throws SQLException{
        UserService addAccount = new UserService();
        try {
            //Kiem tra tat ca cac o nhap lieu co bi trong hay khong? 
            boolean isYournameBlank = isBlankTextField(txtYourName, lbWrongYourname, lbNotifyYourname, "Your name");
            boolean isUsernameBlank = isBlankTextField(txtUsername, lbWrongUsername, lbNotifyUsername, "Username");
            boolean isPasswordBlank = isBlankPasswordField(txtPassword, lbWrongPassword, lbNotifyPassword, "Password");
            boolean isCofirmPasswordBlank = isBlankPasswordField(txtPassword, lbWrongComfirmPassword, lbNotifyComfirmPassword, "Cofirm Password");
            boolean isSexBlank = isBlankSex(lbWrongSex, lbNotifySex, "Sex");
            boolean isDateBlank = isBlankDatePicker(birthDate , lbWrongDate, lbNotifyDate, "Date");
            boolean isFacultyBlank = isBlankComboBox(cbFaculty, lbWrongFaculty, lbNotifyFaculty, "Faculty");
            if(isYournameBlank || isUsernameBlank || isPasswordBlank || isCofirmPasswordBlank || isSexBlank || isDateBlank || isFacultyBlank) 
                return;
            
            if(!isComfirmPassword(txtPassword, txtComfirmPassword)){
                lbWrongComfirmPassword.setText("X");
                lbNotifyComfirmPassword.setText("Your comfirm password is wrong");
                return;
            }
            
            // library card
            String yourName = txtYourName.getText();
            int sex = selectSex();
            Date date = asDate(birthDate.getValue());   
            int facultyId = getFacultyId();
            // user
            String username = txtUsername.getText();
            String password = txtPassword.getText();
       
          
            LibraryCard card = new LibraryCard(yourName, sex, date, facultyId);
            User user = new User(username, password, card.getId());
            
            addAccount.addUser(card, user);
            
        } catch (SQLException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
  
    public int selectSex(){ // tra du lieu radio button sang kieu so 1: male, 2: female
        if(radioBtnMale.isSelected()){
            return 1;
        }else if(radioBtnFemale.isSelected()){
            return 2;
        }
        return 0;
    }
    
    public Date asDate(LocalDate localDate){
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    @FXML
    public int getFacultyId() throws SQLException{
        FacultyService fs = new FacultyService();
        String choosedFaculty = (String) cbFaculty.getValue();
        // test xem co ra so hay khong
//      txtUsername.setText(choosedFaculty);
//      int a = fs.getFacultyIdFromFacultyName(choosedFaculty);
//      txtUsername.setText("" + a);
        return fs.getFacultyIdFromFacultyName(choosedFaculty);
    
    }
    
   
   
      
    public boolean isBlankTextField(TextField txt, Label icon, Label notify, String whatBlank){ //check textfield is blank?
         if(txt.getText().isBlank()){
                icon.setText("X");
                notify.setText("* " + whatBlank + " is blank");
                return true;
            }
           icon.setText("");
           notify.setText("");  
           return false;
       
    }
    public boolean isBlankPasswordField(PasswordField txt, Label icon, Label notify, String whatBlank){ //check textfield is blank?
         if(txt.getText().isBlank()){
                icon.setText("X");
                notify.setText("* " + whatBlank + " is blank");
                return true;
            }
          icon.setText("");
          notify.setText("");  
          return false;
    }
    public boolean isBlankSex(Label icon, Label notify, String whatBlank){
        if(selectSex() == 0){
            icon.setText("X");
            notify.setText("* " + whatBlank + " is blank");
            return true;
        }
        icon.setText("");
        notify.setText("");  
        return false;
    }
    public boolean isBlankDatePicker(DatePicker date, Label icon, Label notify, String whatBlank){
        if(date.getValue() == null){
            icon.setText("X");
            notify.setText("* " + whatBlank + " is blank");
            return true;
        }
        icon.setText("");
        notify.setText("");  
        return false;
    }
    public boolean isBlankComboBox(ComboBox cb, Label icon, Label notify, String whatBlank){
        if(cb.getValue() == null){
            icon.setText("X");
            notify.setText("* " + whatBlank + " is blank");
            return true;
        }
        icon.setText("");
        notify.setText("");  
        return false;
    }
    public boolean isComfirmPassword(PasswordField password, PasswordField comfirmPassword){
        if(!password.getText().equals(comfirmPassword.getText())){
            return false;
        }
        return true;
    }
    
   
    
}

