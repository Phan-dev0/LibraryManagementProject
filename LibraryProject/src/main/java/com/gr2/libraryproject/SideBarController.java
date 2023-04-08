package com.gr2.libraryproject;

import com.gr2.pojos.Book;
import com.gr2.pojos.LibraryCard;
import com.gr2.services.LibraryCardService;
import com.gr2.services.UserService;
import java.io.IOException;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SideBarController implements Initializable {

    @FXML
    private Label lbYourname;

    @FXML
    private BorderPane MainPane;
    @FXML
    private Button nav1;
    @FXML
    Button btnCard;

    UserSession session = UserSession.getSession();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LibraryCardService libcardService = new LibraryCardService();
        LibraryCard libCard;
        btnCard.setOnAction(evt -> {

            try {
                Stage mainStage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("libraryCard.fxml"));
                Parent viewParent = loader.load();
                Stage dialog = new Stage();
                Scene scene = new Scene(viewParent);
                dialog.setTitle("Library card information");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(mainStage);
                dialog.setScene(scene);
                dialog.show();
            } catch (IOException ex) {
                Logger.getLogger(SideBarController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        try {
            libCard = libcardService.getLibraryCard(session.getUser().getCardId());
            lbYourname.setText(libCard.getName());
        } catch (SQLException ex) {
            Logger.getLogger(SideBarController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            loadPage("availableBooksPage");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void availableBooksPage(ActionEvent event) throws IOException {
        loadPage("availableBooksPage");
    }

    @FXML
    private void returnBooksPage(ActionEvent event) throws IOException {
        nav1.getStyleClass().add("nav");
        loadPage("returnBooksPage");
    }

    @FXML
    private void saveBooksPage(ActionEvent event) throws IOException {
        nav1.getStyleClass().add("nav");
        loadPage("saveBooksPage");
    }

    @FXML
    private void historyPage(ActionEvent event) throws IOException {
        nav1.getStyleClass().add("nav");
        loadPage("historyPage");
    }

    @FXML
    private void switchToLogin(ActionEvent event) throws IOException {
        App signOut = new App();
        signOut.changeScene("firstPage");
    }

    private void loadPage(String page) throws IOException {
        Parent view = new FXMLLoader(App.class.getResource(page + ".fxml")).load();
        MainPane.setCenter(view);
    }

}
