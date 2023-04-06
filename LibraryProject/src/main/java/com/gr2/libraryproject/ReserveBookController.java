/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.gr2.libraryproject;

import com.gr2.pojos.Reservation;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author jackc
 */
public class ReserveBookController implements  Initializable {
    
    
    @FXML TableView<Reservation> tbReserve;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    private void loadTableColumns() {
        TableColumn colId = new TableColumn("ID");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(100);
        TableColumn colCreatedDate = new TableColumn("CreatedDate");
        colCreatedDate.setCellValueFactory(new PropertyValueFactory("created_date"));
        colCreatedDate.setPrefWidth(200);
        TableColumn colBookId = new TableColumn("Book ID");
        colBookId.setCellValueFactory(new PropertyValueFactory("book_id"));
        colBookId.setPrefWidth(200);
        TableColumn colUserId = new TableColumn("User ID");
        colUserId.setCellValueFactory(new PropertyValueFactory("user_id"));
        colUserId.setPrefWidth(200);
        
        
        
        
    }
    
    
    
    
}
