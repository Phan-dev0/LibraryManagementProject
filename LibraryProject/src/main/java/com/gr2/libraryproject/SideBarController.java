package com.gr2.libraryproject;

import com.gr2.pojos.Book;
import com.gr2.pojos.LibraryCard;
import com.gr2.services.LibraryCardService;
import com.gr2.services.UserService;
import com.gr2.utils.MessageBox;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SideBarController implements Initializable {

    @FXML
    private Label lbYourname;
    @FXML
    private Button btnLogout;
    @FXML
    private BorderPane MainPane;
    @FXML
    private Button btnNav;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCard;
    @FXML
    private Button btnNavReturnBook;
    @FXML
    private Button btnNavHistoryBook;

    UserSession session = UserSession.getSession();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LibraryCardService libcardService = new LibraryCardService();
        LibraryCard libCard;
        String subject = session.getUserRole();
        String studentSubject = "STUDENT";

        if (session.getUserRole().equals(studentSubject)) {
            btnNavReturnBook.setVisible(false);
            btnNavHistoryBook.setVisible(false);
        }

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
        this.btnNav.setOnAction(evt -> {
            try {
                loadPage("availableBooksPage");
            } catch (IOException ex) {
                Logger.getLogger(SideBarController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.btnNavHistoryBook.setOnAction(evt -> {
            try {
                historyPage();
            } catch (IOException ex) {
                Logger.getLogger(SideBarController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.btnSave.setOnAction(evt -> {
            try {
                saveBooksPage();
            } catch (IOException ex) {
                Logger.getLogger(SideBarController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.btnNavReturnBook.setOnAction(evt -> {
            try {
                returnBooksPage();
            } catch (IOException ex) {
                Logger.getLogger(SideBarController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.btnLogout.setOnAction(evt -> {
            try {
                switchToLogin();
            } catch (IOException ex) {
                Logger.getLogger(SideBarController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        try {
            libCard = libcardService.getLibraryCardById(session.getUser().getCardId());
            lbYourname.setText(libCard.getName());
            loadPage("availableBooksPage");

        } catch (SQLException | IOException ex) {
            MessageBox.getMessageBox("ERROR", ex.getMessage(), Alert.AlertType.ERROR).show();
            Logger.getLogger(SideBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    };

    private void availableBooksPage() throws IOException {
        loadPage("availableBooksPage");
    }

    private void returnBooksPage() throws IOException {
        btnNav.getStyleClass().add("nav");
        loadPage("returnBooksPage");
    }

    private void saveBooksPage() throws IOException {
        btnNav.getStyleClass().add("nav");
        loadPage("saveBooksPage");
    }

    private void historyPage() throws IOException {
        btnNav.getStyleClass().add("nav");
        loadPage("historyPage");
    }

    private void switchToLogin() throws IOException {
        App signOut = new App();
        signOut.changeScene("firstPage");
        session.cleanSession();
    }

    private void loadPage(String page) throws IOException {
        Parent view = new FXMLLoader(App.class.getResource(page + ".fxml")).load();
        MainPane.setCenter(view);
    }

}
