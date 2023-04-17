package com.gr2.libraryproject;

import com.gr2.pojos.Book;
import com.gr2.pojos.BorrowDetail;
import com.gr2.services.BookService;
import com.gr2.services.BorrowDetailService;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author vegar
 */
public class ReturnBookController implements Initializable {

    @FXML
    private TableView<BorrowDetail> tbReturnBookView;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSeach;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.loadTableColumns();
            this.loadBorrowBooks(null);
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.btnSeach.setOnAction(e -> {
            try {
                this.loadBorrowBooks(txtSearch.getText());
            } catch (SQLException ex) {
                Logger.getLogger(ReturnBookController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    public void loadTableColumns() {
        TableColumn colBookId = new TableColumn("Book ID");
        colBookId.setCellValueFactory(new PropertyValueFactory("bookId"));
        colBookId.setPrefWidth(50);

        TableColumn colUserId = new TableColumn("User ID");
        colUserId.setCellValueFactory(new PropertyValueFactory("userId"));
        colUserId.setPrefWidth(380);

        TableColumn colBorrowDate = new TableColumn("Borrow Date");
        colBorrowDate.setCellValueFactory(new PropertyValueFactory("borrowDate"));
        colBorrowDate.setPrefWidth(100);

        TableColumn colReturnDate = new TableColumn("Return Date");
        colReturnDate.setCellValueFactory(new PropertyValueFactory("returnDate"));
        colReturnDate.setPrefWidth(100);

        TableColumn colBtnReturn = new TableColumn();
        colBtnReturn.setCellFactory(c -> {
            TableCell<BorrowDetail, Button> cell = new TableCell<>() {
                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);

                    if (!empty) {
                        Button btn = new Button("Return");
                        btn.setOnAction(e -> {
                            BookService sv = new BookService();
                            BorrowDetail borrowBook = (BorrowDetail) this.getTableRow().getItem(); // lay ra doi tuong book trong hang
                            System.out.print(borrowBook.getBookId());
                            int bookId = borrowBook.getBookId(); // lay ra bookId    
                            try {
                                sv.returnBook(bookId);
                                loadTableColumns();
                                loadBorrowBooks(null);
                            } catch (SQLException ex) {
                                Logger.getLogger(ReturnBookController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        this.setGraphic(btn);
                    }
                    else {
                        this.setGraphic(null);
                    }
                }
            };
            
            
            return cell;
        });

        this.tbReturnBookView.getColumns().addAll(colBookId, colUserId, colBorrowDate, colReturnDate, colBtnReturn);
    }

    private void loadBorrowBooks(String kw) throws SQLException {

        BookService service = new BookService();
        List<BorrowDetail> borrowBooks = service.getBorrowBook(kw);
        this.tbReturnBookView.getItems().clear();
        this.tbReturnBookView.setItems(FXCollections.observableList(borrowBooks));
    }

}
