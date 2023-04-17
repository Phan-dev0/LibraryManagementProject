/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.libraryproject;

import com.gr2.pojos.Book;
import com.gr2.pojos.BorrowDetail;
import com.gr2.pojos.LibraryCard;
import com.gr2.services.BookService;
import com.gr2.services.BorrowDetailService;
import com.gr2.services.LibraryCardService;
import com.gr2.services.UserService;
import com.gr2.utils.DateUtils;
import com.gr2.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author vegar
 */
public class selectUserBorrowController implements Initializable {
    @FXML
    private TableView tbLibraryCard;
    @FXML
    private Label lbNotify;
    @FXML
    private Button btnFind;
    @FXML
    private TextField txtSearch;
    
    private String title;

    private Stage detailStage;
    
    
    private int bookId;
    
    UserService userService = new UserService();
    
    public void setStage(Stage stg){
        this.detailStage = stg;
    }
    public void setId(int id){
        this.bookId = id;
    }
    
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try{
            this.loadTable();
            this.loadLibraryCard(null);
        }catch (SQLException ex) {
            Logger.getLogger(selectUserBorrowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.btnFind.setOnAction(evt -> {
            try {
                this.loadLibraryCard(this.txtSearch.getText());
                if (this.tbLibraryCard.getItems().isEmpty()) {
                    MessageBox.getMessageBox("INFO", "There is no result", Alert.AlertType.INFORMATION).show();
                }
            } catch (SQLException ex) {
                MessageBox.getMessageBox("ERROR", ex.getMessage(), Alert.AlertType.WARNING).show();
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private void loadTable(){
        TableColumn colId = new TableColumn("ID");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(180);
        TableColumn colName = new TableColumn("Name");
        colName.setPrefWidth(120);
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        TableColumn colBirthDay = new TableColumn("Birth Date");
        colBirthDay.setCellValueFactory(new PropertyValueFactory("birthDate"));
        colBirthDay.setPrefWidth(80);
        TableColumn colEmail = new TableColumn("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colEmail.setPrefWidth(100);
        TableColumn colExpireDate = new TableColumn("Expire Date");
        colExpireDate.setCellValueFactory(new PropertyValueFactory("expireDate"));
        colExpireDate.setPrefWidth(80);
        TableColumn colChoose = new TableColumn();
        colChoose.setCellFactory(c -> {
            TableCell<Book, Button> cell = new TableCell<>() {
                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);

                    if (!empty) {
                        Button btn = new Button();
                        btn.setText("Choose");
                        this.setGraphic(btn);
                        btn.setOnAction(evt -> {
                               DateUtils dateUtils = new DateUtils();
                               BookService bookService = new BookService();
                               String userId = "";
                               
                               LibraryCard card = (LibraryCard) tbLibraryCard.getItems().get(this.getTableRow().getIndex());
                               String cardId = card.getId();
                                try {
                                   userId = userService.getUserIdByCardId(cardId);
                                   
                                   if(bookService.isLendMoreFiveBook(userId)){
                                       lbNotify.setText("Borrowed more than 5 books");
                                       return;
                                   }
                                   
                                } catch (SQLException ex) {
                                    Logger.getLogger(selectUserBorrowController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                               
                               BorrowDetailService lendBook = new BorrowDetailService();
                               
                                try {
                                    lendBook.lendBookBaseOnBookId(userId, bookId);
                                } catch (SQLException ex) {
                                    Logger.getLogger(selectUserBorrowController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
                                Stage selectedStage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
                                detailStage.close();
                                selectedStage.close();
                                App primaryPage = new App();
                                if (primaryPage.getStage().isShowing()) {
                                   try {
                                       primaryPage.changeScene("primary");
                                   } catch (IOException ex) {
                                       Logger.getLogger(selectUserBorrowController.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                                }
                        });
                    } else {
                        this.setGraphic(null);
                    }
                }
            };

            return cell;
        });
        
        this.tbLibraryCard.getColumns().addAll(colId, colName, colBirthDay, colEmail, colExpireDate, colChoose);
    }

    private void loadLibraryCard(String kw) throws SQLException{
        LibraryCardService service = new LibraryCardService();
        List<LibraryCard> card = service.getLibraryCards(kw);
        this.tbLibraryCard.getItems().clear();
        this.tbLibraryCard.setItems(FXCollections.observableList(card));
    }
}
