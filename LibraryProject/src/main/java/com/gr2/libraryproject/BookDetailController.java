/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.gr2.libraryproject;

import com.gr2.pojos.Book;
import com.gr2.pojos.BorrowDetail;
import com.gr2.pojos.Reservation;
import com.gr2.pojos.User;
import com.gr2.services.BookService;
import com.gr2.services.BorrowDetailService;
import com.gr2.services.ReservationService;
import java.io.IOException;
import com.gr2.services.UserService;
import com.gr2.utils.DateUtils;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
    @FXML
    private Button btnLend;
    @FXML
    private Button btnReserve;
    @FXML
    private Button btnCancel;
    
    
    private int Id;

    UserSession session = UserSession.getSession();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserService userService = new UserService();
        BookService bookService = new BookService();
        ReservationService reservationService = new ReservationService();
        BorrowDetailService borrowDetailService = new BorrowDetailService();
        
        String subject = session.getUserRole();
        String studentSubject = "STUDENT";

        if(session.getUserRole().equals(studentSubject)){
            btnLend.setVisible(false);
        } 
        
        this.btnReserve.setOnAction(evt -> {
            try {
                User user = userService.getUserById(session.getUser().getId());
                int bookId = bookService.getBookIdByBookTitle(lbTitle.getText());
                Book b = bookService.getBookById(bookId);
                reservationService.createReservation(user.getId(), bookId);
                Stage detailStage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
                detailStage.close();
                App primaryPage = new App();
                if (primaryPage.getStage().isShowing()) {
                    primaryPage.changeScene("primary");
                }

            } catch (SQLException | IOException ex) {
                Logger.getLogger(BookDetailController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.btnLend.setOnAction(evt -> {
            try {

                lendBook(evt);

            } catch (SQLException | IOException ex) {
                Logger.getLogger(BookDetailController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        this.btnCancel.setOnAction(evt -> {
            try {
                reservationService.deleteReservation(this.Id, session.getUser().getId());
                Stage detailStage = (Stage) ((Node)evt.getSource()).getScene().getWindow();
                detailStage.close();
                App primaryPage = new App();
                if (primaryPage.getStage().isShowing()) {
                    primaryPage.changeScene("primary");
                }
            } catch (SQLException | IOException ex) {
                Logger.getLogger(BookDetailController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    public void setBook(Book book) {
        this.Id = book.getId();
        this.lbTitle.setText(book.getTitle());
        this.lbAuthors.setText(book.getAuthors());
        this.txtDescription.setText(book.getDescription());
        this.txtYear.setText(Integer.toString(book.getPublishedYear()));
        this.txtPlace.setText(book.getPublishedPlace());
        this.txtImport.setText(book.getImportDate().toString());
        this.txtState.setText(book.getState());
        this.txtLoc.setText(book.getLocation());
    }

    public void lendBook(ActionEvent e) throws SQLException, IOException {
          
          Stage mainStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("selectUserBorrowBook.fxml"));
          Parent bookViewParent = loader.load();
          Stage dialog = new Stage();
          Scene scene = new Scene(bookViewParent);
          selectUserBorrowController controller = loader.getController();
          controller.setStage(mainStage);
          controller.setTitle(lbTitle.getText());
          System.out.println(lbTitle.getText());
          
          

          dialog.setTitle("Select the borrow one ");
          dialog.initModality(Modality.APPLICATION_MODAL);
          dialog.initOwner(mainStage);
          dialog.setScene(scene);
          dialog.show();
//        DateUtils dateUtils = new DateUtils();
//        UserSession dataUserId = UserSession.getSession();
//        BookService getBookId = new BookService();
//        
//        String userId = dataUserId.getUser().getId();
//        int bookId = getBookId.getBookIdByBookTitle(lbTitle.getText());
//
//        LocalDate borrowDate = LocalDate.now();
//        LocalDate returnDate = dateUtils.getReturnDate(borrowDate);
//
//        BorrowDetail borrowBook = new BorrowDetail(borrowDate, returnDate);
//
//        BorrowDetailService lendBook = new BorrowDetailService();
//        try {
//            lendBook.lendBookBaseOnBookId(borrowBook, userId, bookId);
//
//        } catch (SQLException ex) {
//            Logger.getLogger(BookDetailController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        Stage detailStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
//        detailStage.close();
//        App primaryPage = new App();
//        if (primaryPage.getStage().isShowing()) {
//            primaryPage.changeScene("primary");
//        }

    }

    public void hideReserve() throws SQLException {
        BookService bookService = new BookService();
        this.btnReserve.setVisible(!(bookService.getBookById(this.Id).getState().equals("RESERVED")));

    }
    
    public void showCancel() throws SQLException {
        BookService bookService = new BookService();
        this.btnCancel.setVisible(bookService.getBookById(this.Id).getState().equals("RESERVED"));
    }
}
