package com.gr2.libraryproject;

import com.gr2.pojos.Book;
import com.gr2.pojos.Category;
import com.gr2.pojos.Reservation;
import com.gr2.services.BookService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrimaryController implements Initializable {

    @FXML
    private TableView<Book> tbBooks;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private RadioButton rdTitle;
    @FXML
    private RadioButton rdAuthors;
    @FXML
    private RadioButton rdYear;
    @FXML
    private RadioButton rdCategory;
    @FXML
    private Label lbname;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup criteriaToggle = new ToggleGroup();
        this.rdTitle.setToggleGroup(criteriaToggle);
        this.rdAuthors.setToggleGroup(criteriaToggle);
        this.rdYear.setToggleGroup(criteriaToggle);
        this.rdCategory.setToggleGroup(criteriaToggle);
        try {

            this.loadBooks(null, ((RadioButton) criteriaToggle.getSelectedToggle()).getText());
            this.loadTableColumns();

        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.btnSearch.setOnAction(evt -> {
            try {

                String criteria = ((RadioButton) criteriaToggle.getSelectedToggle()).getText();
                System.out.println(criteria);
                this.loadBooks(this.txtSearch.getText(), criteria);

            } catch (SQLException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
//        this.txtSearch.textProperty().addListener(o -> {
//            try {
//                this.loadBooks(this.txtSearch.getText());
//            } catch (SQLException ex) {
//                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });

    }

    private void loadTableColumns() {
        TableColumn colTitle = new TableColumn("Title");
        colTitle.setCellValueFactory(new PropertyValueFactory("title"));
        colTitle.setPrefWidth(220);
        TableColumn colAuthors = new TableColumn("Authors");
        colAuthors.setPrefWidth(220);
        colAuthors.setCellValueFactory(new PropertyValueFactory("authors"));
        TableColumn colYear = new TableColumn("Year");
        colYear.setCellValueFactory(new PropertyValueFactory("publishedYear"));
        colYear.setPrefWidth(70);
        TableColumn colCate = new TableColumn("Category");
        colCate.setCellValueFactory(new PropertyValueFactory("categoryId"));
        colCate.setPrefWidth(100);
        TableColumn colDetail = new TableColumn();


        colDetail.setCellFactory(c  -> {
            TableCell<Book, Button> cell = new TableCell<>() {
                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);

                    if (!empty) {
                        Button btn = new Button();
                        btn.setText("Detail");
                        this.setGraphic(btn);
                        btn.setOnAction(evt -> {
                            try {

                                Stage mainStage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("bookDetail.fxml"));
                                Parent bookViewParent = loader.load();
                                Stage dialog = new Stage();
                                Scene scene = new Scene(bookViewParent);
                                BookDetailController controller = loader.getController();
                                Book b = tbBooks.getItems().get(this.getTableRow().getIndex());
                                controller.setBook(b);
                                dialog.setTitle("Book information");
                                dialog.initModality(Modality.APPLICATION_MODAL);
                                dialog.initOwner(mainStage);
                                dialog.setScene(scene);

                                dialog.show();
                            } catch (IOException ex) {
                                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                    } else {
                        this.setGraphic(null);
                    }
                }
            };

            return cell;
        });
        this.tbBooks.getColumns().addAll(colTitle, colAuthors, colYear, colCate, colDetail);

    }

    private void loadBooks(String kw, String criteria) throws SQLException {

        BookService service = new BookService();
        List<Book> books = service.getBooks(kw, criteria);
        this.tbBooks.getItems().clear();
        this.tbBooks.setItems(FXCollections.observableList(books));
    }

}
