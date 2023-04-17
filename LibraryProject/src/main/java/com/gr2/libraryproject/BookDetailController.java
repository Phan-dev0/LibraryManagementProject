/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.gr2.libraryproject;

import com.gr2.pojos.Book;
import com.gr2.pojos.User;
import com.gr2.services.BookService;
import com.gr2.services.BorrowDetailService;
import com.gr2.services.ReservationService;
import java.io.IOException;
import com.gr2.services.UserService;
import com.gr2.utils.MessageBox;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private String reserveUserID;

    UserSession session = UserSession.getSession();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String studentSubject = "STUDENT";

        if (session.getUserRole().equals(studentSubject)) {
            btnLend.setVisible(false);
            btnReserve.setVisible(true);
        }

        this.btnReserve.setOnAction(evt -> {
            Alert a = MessageBox.getMessageBox("Confirm Save", "Are you sure ?", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    reserveBook(evt);
                }
            });
        });
        this.btnLend.setOnAction(evt -> {
            try {
                BorrowDetailService borrowDetailService = new BorrowDetailService();
                if (session.getUserRole().equals("ADMIN") && this.getReserUserID() != null) {
                    if (borrowDetailService.lendBookBaseOnBookId(this.getReserUserID(), this.Id)) {
                        MessageBox.getMessageBox("INFO", "Lend Book Successfully!", Alert.AlertType.INFORMATION).show();
                        App primaryPage = new App();
                        if (primaryPage.getStage().isShowing()) {
                            try {
                                primaryPage.changeScene("primary");
                            } catch (IOException ex) {
                                Logger.getLogger(selectUserBorrowController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        Stage detailStage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
                        detailStage.close();
                    } else {
                        MessageBox.getMessageBox("ERROR", "Lend Book Failed!", Alert.AlertType.ERROR);
                    }
                } else {
                    lendBook(evt);
                }

            } catch (SQLException | IOException ex) {
                MessageBox.getMessageBox("ERROR", ex.getMessage(), Alert.AlertType.ERROR).show();
                Logger.getLogger(BookDetailController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        this.btnCancel.setOnAction(evt -> {
            Alert a = MessageBox.getMessageBox("Confirm Save", "Are you sure ?", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    cancelReserve(evt);
                }
            });

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

        controller.setId(Id);

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

    public void reserveBook(ActionEvent e) {
        try {
            UserService userService = new UserService();
            BookService bookService = new BookService();
            ReservationService reservationService = new ReservationService();
            User user = userService.getUserById(session.getUser().getId());
            int bookId = bookService.getBookIdByBookTitle(lbTitle.getText());
            reservationService.createReservation(user.getId(), bookId);
            Stage detailStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            detailStage.close();
            App primaryPage = new App();
            if (primaryPage.getStage().isShowing()) {
                primaryPage.changeScene("primary");
            }

        } catch (SQLException | IOException ex) {
            MessageBox.getMessageBox("ERROR", ex.getMessage(), Alert.AlertType.ERROR).show();
            Logger.getLogger(BookDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cancelReserve(ActionEvent e) {
        ReservationService reservationService = new ReservationService();
        try {
            reservationService.deleteReservation(this.Id, session.getUser().getId());
            Stage detailStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            detailStage.close();
            App primaryPage = new App();
            if (primaryPage.getStage().isShowing()) {
                primaryPage.changeScene("primary");
            }
        } catch (SQLException | IOException ex) {
            MessageBox.getMessageBox("ERROR", ex.getMessage(), Alert.AlertType.ERROR).show();
            Logger.getLogger(BookDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void hideReserve() throws SQLException {
        BookService bookService = new BookService();
        if (session.getUserRole().equals("STUDENT")) {
            this.btnReserve.setVisible(!(bookService.getBookById(this.Id).getState().equals("RESERVED")));
        }

    }

    public void showCancel() throws SQLException {
        BookService bookService = new BookService();
        this.btnCancel.setVisible(bookService.getBookById(this.Id).getState().equals("RESERVED"));
    }

    public void setReserveUserID(String id) {
        this.reserveUserID = id;
    }

    public String getReserUserID() {
        return reserveUserID;
    }
}
