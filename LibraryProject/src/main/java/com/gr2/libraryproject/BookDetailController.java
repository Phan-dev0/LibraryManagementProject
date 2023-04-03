/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.gr2.libraryproject;

import com.gr2.pojos.Book;
import com.gr2.pojos.BorrowDetail;
import com.gr2.services.BookService;
import com.gr2.services.BorrowDetailService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

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
    
    public void lendBook(ActionEvent e) throws SQLException, IOException{
        Singleton dataUserId = Singleton.getInstance();
        BookService getBookId = new BookService();
        String userId = dataUserId.getUserId();
        int bookId = getBookId.getBookIdByBookTitle(lbTitle.getText());
        
        // change localDate to -> Date
        LocalDate borrowLocalDate = LocalDate.now();
        LocalDate returnLocalDate = borrowLocalDate.plusDays(14);
        Date borrowDate = asDate(borrowLocalDate);
        Date returnDate = asDate(returnLocalDate);
        

                
        BorrowDetail borrowBook = new BorrowDetail(borrowDate, returnDate);

        
        BorrowDetailService lendBook = new BorrowDetailService();
        try {
            lendBook.lendBookBaseOnBookId(borrowBook, userId, bookId);
            
        } catch (SQLException ex) {
            Logger.getLogger(BookDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Stage detailStage = (Stage) ((Node) e.getSource()).getScene().getWindow();  
        detailStage.close();
        App primaryPage = new App();  
        if(primaryPage.getStage().isShowing()){
            primaryPage.changeScene("primary");
        }
        
    }
    
    public Date asDate(LocalDate localDate){
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
