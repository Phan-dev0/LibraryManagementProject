package com.gr2.libraryproject;

import com.gr2.pojos.Book;
import com.gr2.pojos.Category;
import com.gr2.services.BookService;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class PrimaryController implements Initializable {

    @FXML
    private TableView<Book> tbBooks;
    @FXML private TextField txtSearch;
    @FXML private Button btnSearch;
    @FXML private RadioButton rdTitle;
    @FXML private RadioButton rdAuthors;
    @FXML private RadioButton rdYear;
    @FXML private RadioButton rdCategory;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup criteriaToggle = new ToggleGroup();
        this.rdTitle.setToggleGroup(criteriaToggle);
        this.rdAuthors.setToggleGroup(criteriaToggle);
        this.rdYear.setToggleGroup(criteriaToggle);
        this.rdCategory.setToggleGroup(criteriaToggle);
        try {
            this.loadTableColumns();
            this.loadBooks(null, ((RadioButton) criteriaToggle.getSelectedToggle()).getText());

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
        colDetail.setCellFactory(c -> {
            Button btn = new Button("Detail");
            
            TableCell cell = new TableCell();
            cell.setGraphic(btn);
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
