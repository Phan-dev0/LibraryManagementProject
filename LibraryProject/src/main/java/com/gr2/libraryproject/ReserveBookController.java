/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.gr2.libraryproject;

import com.gr2.pojos.Book;
import com.gr2.pojos.Reservation;
import com.gr2.services.BookService;
import com.gr2.services.BorrowDetailService;
import com.gr2.services.ReservationService;
import com.gr2.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author jackc
 */
public class ReserveBookController implements Initializable {

    @FXML
    TableView<Reservation> tbReserve;
    @FXML
    private Button btnLendAll;
    
    private List<String> userIds = new ArrayList<>();
    
    UserSession session = UserSession.getSession();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String subject = session.getUserRole();
        String studentSubject = "STUDENT";

        if (session.getUserRole().equals(studentSubject)) {
            btnLendAll.setVisible(false);
        }
        
        ReservationService reservationService = new ReservationService();
        List<Reservation> reservations;
        try {
            reservations = reservationService.getReservations();
            reservations.stream().filter(r -> {
                
                return this.checkExpire(r);
                
            }).collect(Collectors.toList()).forEach(rev -> {
                try {
                    reservationService.deleteReservation(rev.getId());
                } catch (SQLException ex) {
                    Logger.getLogger(ReserveBookController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(ReserveBookController.class.getName()).log(Level.SEVERE, null, ex);
        }

        loadTableColumns();
        try {
            loadReservation();
        } catch (SQLException ex) {
            Logger.getLogger(ReserveBookController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void loadTableColumns() {

        TableColumn colId = new TableColumn("ID");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(100);
        TableColumn colCreatedDate = new TableColumn("CreatedDate");
        colCreatedDate.setCellValueFactory(new PropertyValueFactory("createdDate"));
        colCreatedDate.setPrefWidth(200);
        TableColumn colBookId = new TableColumn("Book ID");
        colBookId.setCellValueFactory(new PropertyValueFactory("bookId"));
        colBookId.setPrefWidth(100);
        TableColumn colUserId = new TableColumn("User ID");
        colUserId.setCellValueFactory(new PropertyValueFactory("userId"));
        colUserId.setPrefWidth(170);
        TableColumn colLend = new TableColumn();
        colLend.setCellFactory(c -> {
            TableCell<Reservation, Button> cell = new TableCell<>() {
                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);

                    if (!empty) {
                        Button btn = new Button();
                        btn.setText("Detail");
                        this.setGraphic(btn);
                        btn.setOnAction(evt -> {
                            try {
                                BookService bookService = new BookService();
                                Stage mainStage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("bookDetail.fxml"));
                                Parent bookViewParent = loader.load();
                                Stage dialog = new Stage();
                                Scene scene = new Scene(bookViewParent);
                                BookDetailController controller = loader.getController();

                                int currentBookId = tbReserve.getItems()
                                        .get(this.getTableRow().getIndex()).getBookId();
                                controller.setBook(bookService.getBookById(currentBookId));
                                controller.hideReserve();
                                controller.showCancel();
                                String userID = tbReserve.getItems().get(this.getTableRow().getIndex()).getUserId();
                                controller.setReserveUserID(userID);
                                dialog.setTitle("Book information");
                                dialog.initModality(Modality.APPLICATION_MODAL);

                                dialog.initOwner(mainStage);
                                dialog.setScene(scene);

                                dialog.show();
                            } catch (IOException | SQLException ex) {
                                Logger.getLogger(ReserveBookController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });

                    } else {
                        this.setGraphic(null);
                    }
                }
            };

            return cell;
        });
        
        TableColumn<Reservation, Boolean> colCheck = new TableColumn();
        colCheck.setPrefWidth(50);

        colCheck.setCellValueFactory(data -> {
            SimpleBooleanProperty checkProperty = new SimpleBooleanProperty();

            checkProperty.setValue(false);
            checkProperty.addListener((observable, oldValue, newValue) -> {
                data.getValue().setSelected(newValue);
            });
            return checkProperty;
        });
        colCheck.setCellFactory(c -> {
            TableCell<Reservation, Boolean> cell = new TableCell<>() {
                private final CheckBox checkBox = new CheckBox();

                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        
                        this.setGraphic(null);

                    } else {
//                        checkBox.setSelected(item);
                        this.setGraphic(checkBox);
                    }
                    
                    checkBox.setOnAction(e ->{
                         Reservation b = tbReserve.getItems().get(this.getTableRow().getIndex());
                         if(b.isSelected()){
                             String deleteUserId = b.getUserId();
                             userIds.removeAll(List.of(deleteUserId));
                             b.setSelected(false);
                             return;
                         }
                         b.setSelected(true);
                         userIds.add(b.getUserId());
                    });
                }

            };
            return cell;

        });
        this.tbReserve.getColumns().addAll(colId, colCreatedDate, colBookId, colUserId, colLend, colCheck);

    }
    
    @FXML
    public void lendAll(ActionEvent e) throws IOException, SQLException{
        int quantityOfBooks = 0;
        BorrowDetailService bookDetailService = new BorrowDetailService();
        ObservableList<Reservation> allSelectedBooks = tbReserve.getItems();
        ArrayList<Integer> bookIds = new ArrayList<>();
       
        for (Reservation b : allSelectedBooks) {
            if (b.isSelected()) {
                bookIds.add(b.getBookId());
                quantityOfBooks++;
            }
        }
        
        if(quantityOfBooks == 0){
            MessageBox.getMessageBox("ERROR", "No books is choosed", Alert.AlertType.ERROR).show();
            return;
        }else if(quantityOfBooks >= 6){
            MessageBox.getMessageBox("ERROR", "Can't borrow more than 5 books ", Alert.AlertType.ERROR).show();
            return;
        }
        
        String comfirmUserId = "";
        String tempUserId = "";
        boolean check = false;
        for(String userId1: userIds){
            comfirmUserId = userId1;
            for(String userId2: userIds){
                if(!comfirmUserId.equals(userId2)){
                    check = true;
                }
            }
        }
        
        if(check){
            comfirmUserId = "";
            MessageBox.getMessageBox("ERROR", "Can't lend to more than 1 user", Alert.AlertType.ERROR).show();
            return;
        }
        
        bookDetailService.lendMultiBooksBaseOnBookId(comfirmUserId, bookIds);
        
        App primaryPage = new App();
        if (primaryPage.getStage().isShowing()) {
            try {
                primaryPage.changeScene("primary");
            } catch (IOException ex) {
                Logger.getLogger(selectUserBorrowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void loadReservation() throws SQLException {
        ReservationService revService = new ReservationService();
        List<Reservation> reservations = revService.getReservations();
        tbReserve.getItems().clear();
        tbReserve.setItems(FXCollections.observableList(reservations));

    }

    public boolean checkExpire(Reservation reservation) {
        Instant currentTime = LocalDateTime.now().toInstant(ZoneOffset.of("+7"));
        Instant reservationTime = reservation.getCreatedDate().toInstant(ZoneOffset.of("+7"));
        Duration timeElapsed = Duration.between(currentTime, reservationTime);
        double hours = (double)timeElapsed.toSeconds()/ 3600;
        return Math.abs(hours) >= 48;
    }

}
