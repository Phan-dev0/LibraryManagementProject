/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.gr2.libraryproject;

import com.gr2.pojos.Book;
import com.gr2.pojos.Reservation;
import com.gr2.services.BookService;
import com.gr2.services.ReservationService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        this.tbReserve.getColumns().addAll(colId, colCreatedDate, colBookId, colUserId, colLend);

    }

    public void loadReservation() throws SQLException {
        ReservationService revService = new ReservationService();
        List<Reservation> reservations = revService.getReservations();
        tbReserve.getItems().clear();
        tbReserve.setItems(FXCollections.observableList(reservations));

    }

    public boolean checkExpire(Reservation reservation) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime reservationTime = reservation.getCreatedDate();
        int dateOffset = currentTime.getDayOfYear() - reservationTime.getDayOfYear();
        double currentHours = currentTime.getHour() + currentTime.getMinute() / 60 + currentTime.getSecond() / 3600;
        double reservationHours = reservationTime.getHour() + reservationTime.getMinute()/60 + reservationTime.getSecond()/3600;
        double hours = 0;
        if (dateOffset >= 1) {
            hours = 24*dateOffset - currentHours - reservationHours;
        }
        return hours >= 24;
    }

}