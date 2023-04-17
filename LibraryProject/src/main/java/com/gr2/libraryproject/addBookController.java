/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.libraryproject;

import com.gr2.pojos.Book;
import com.gr2.services.BookService;
import com.gr2.services.CategoryService;
import com.gr2.services.FacultyService;
import com.gr2.utils.DateUtils;
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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author vegar
 */
public class addBookController implements Initializable{
    @FXML 
    private TextField txtTitle;
    @FXML
    private TextField txtAuthors;
    @FXML
    private TextField txtDesciption;
    @FXML
    private TextField txtPulishPlace;
    @FXML
    private TextField txtLocation;
    @FXML
    private TextField txtPulishYear;
    @FXML
    private ComboBox<String> cbCategory;
    @FXML 
    private Button btnComfirm;
    @FXML
    private Label lbTitleIcon;
    @FXML
    private Label lbAuthorsIcon;
    @FXML
    private Label lbDesciptionIcon;
    @FXML
    private Label lbPublishPlaceIcon;
    @FXML
    private Label lbLocationIcon;
    @FXML
    private Label lbPublishYearIcon;
    @FXML
    private Label lbCategoryIcon;
    @FXML
    private Label lbTitleNotify;
    @FXML
    private Label lbAuthorsNotify;
    @FXML
    private Label lbDesciptionNotify;
    @FXML
    private Label lbPublishPlaceNotify;
    @FXML
    private Label lbLocationNotify;
    @FXML
    private Label lbPublishYearNotify;
    @FXML
    private Label lbCategoryNotify;
    
    private int id;
    private int categoryId;
    private LocalDate importDate;
    
    public void setBook(Book book){
        this.id = book.getId();
        this.txtTitle.setText(book.getTitle());
        this.txtAuthors.setText(book.getAuthors());
        this.txtDesciption.setText(book.getDescription());
        this.txtPulishYear.setText(Integer.toString(book.getPublishedYear()));
        this.txtPulishPlace.setText(book.getPublishedPlace());
        this.txtLocation.setText(book.getLocation());
        
        this.importDate = book.getImportDate();
        
        this.categoryId = book.getCategoryId();
        this.cbCategory.getSelectionModel().select(categoryId-1);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CategoryService getCategoryNameService = new CategoryService();
        
       
        try {
            List<String> categoryNames = getCategoryNameService.getCategoryName();
            this.cbCategory.setItems(FXCollections.observableArrayList(categoryNames)); 
        } catch (SQLException ex) {
            Logger.getLogger(addBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void comfirmSubmit(ActionEvent evt) throws SQLException{

        boolean isTitleBlank = isBlankTextField(txtTitle, lbTitleIcon, lbTitleNotify, "Title");
        boolean isAuthorsBlank = isBlankTextField(txtAuthors, lbAuthorsIcon, lbAuthorsNotify, "Authors");
        boolean isDescriptionBlank = isBlankTextField(txtDesciption, lbDesciptionIcon, lbDesciptionNotify, "Description");
        boolean isPublishPlaceBlank = isBlankTextField(txtPulishPlace, lbPublishPlaceIcon, lbPublishPlaceNotify, "Publish place");
        boolean isPublishYearBlank = isBlankTextField(txtPulishYear, lbPublishYearIcon, lbPublishYearNotify, "Publish Year");
        boolean isLocationBlank = isBlankTextField(txtLocation, lbLocationIcon, lbLocationNotify, "Location");
        boolean isCategoryBlank = isBlankComboBox(cbCategory, lbCategoryIcon, lbCategoryNotify, "Category");
        
        if(isTitleBlank || isAuthorsBlank || isDescriptionBlank || isPublishPlaceBlank || isPublishYearBlank || isLocationBlank || isCategoryBlank){
            return;
        }
        
        BookService BookService = new BookService();
        CategoryService CategoryService = new CategoryService();
       
        int publishYear = Integer.valueOf(txtPulishYear.getText());
                
        LocalDate importLocalDate = LocalDate.now();
        
        int categoryId = CategoryService.getCategoryIdFromItsName(cbCategory.getValue());
        
        Book book = new Book(txtTitle.getText(), txtAuthors.getText(), txtDesciption.getText(), publishYear, txtPulishPlace.getText(), importLocalDate, txtLocation.getText(), categoryId, "FREE");
        BookService.addBook(book);
        
        
        Stage addPage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        addPage.close();
        App primaryPage = new App();
        if (primaryPage.getStage().isShowing()) {
            try{
                 primaryPage.changeScene("primary");
            }catch (IOException ex) {
                Logger.getLogger(selectUserBorrowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void comfirmUpdate(ActionEvent evt) throws SQLException{

        boolean isTitleBlank = isBlankTextField(txtTitle, lbTitleIcon, lbTitleNotify, "Title");
        boolean isAuthorsBlank = isBlankTextField(txtAuthors, lbAuthorsIcon, lbAuthorsNotify, "Authors");
        boolean isDescriptionBlank = isBlankTextField(txtDesciption, lbDesciptionIcon, lbDesciptionNotify, "Description");
        boolean isPublishPlaceBlank = isBlankTextField(txtPulishPlace, lbPublishPlaceIcon, lbPublishPlaceNotify, "Publish place");
        boolean isPublishYearBlank = isBlankTextField(txtPulishYear, lbPublishYearIcon, lbPublishYearNotify, "Publish Year");
        boolean isLocationBlank = isBlankTextField(txtLocation, lbLocationIcon, lbLocationNotify, "Location");
        boolean isCategoryBlank = isBlankComboBox(cbCategory, lbCategoryIcon, lbCategoryNotify, "Category");
        
        if(isTitleBlank || isAuthorsBlank || isDescriptionBlank || isPublishPlaceBlank || isPublishYearBlank || isLocationBlank || isCategoryBlank){
            return;
        }
        
        BookService bookService = new BookService();
        int publishYear = Integer.valueOf(txtPulishYear.getText());
        
        Book book = new Book(id,txtTitle.getText(), txtAuthors.getText(), txtDesciption.getText(), publishYear, txtPulishPlace.getText(), importDate, txtLocation.getText(), categoryId, "FREE");
       
        try{
             bookService.updateBook(book);
        }catch (SQLException ex) {
          Logger.getLogger(addBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        Stage updatePage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        updatePage.close();
        App primaryPage = new App();
        if (primaryPage.getStage().isShowing()) {
            try{
                 primaryPage.changeScene("primary");
            }catch (IOException ex) {
                Logger.getLogger(selectUserBorrowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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

}   
