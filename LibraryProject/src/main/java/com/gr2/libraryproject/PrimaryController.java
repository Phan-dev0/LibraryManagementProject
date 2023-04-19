package com.gr2.libraryproject;

import com.gr2.pojos.Book;
import com.gr2.pojos.Category;
import com.gr2.pojos.Reservation;
import com.gr2.services.BookService;
import com.gr2.services.UserService;
import com.gr2.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyBooleanWrapper;
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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    @FXML
    private Button btnAddBook;
    @FXML
    private Button btnUpdateBook;
    @FXML
    private Button btnDeleteBook;
    @FXML
    private Button btnLendAll;
  
    UserSession session = UserSession.getSession();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String subject = session.getUserRole();
        String studentSubject = "STUDENT";

        if (session.getUserRole().equals(studentSubject)) {
            btnAddBook.setVisible(false);
            btnDeleteBook.setVisible(false);
            btnUpdateBook.setVisible(false);
            btnLendAll.setVisible(false);
        }

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
                this.loadBooks(this.txtSearch.getText(), criteria);
                if (this.tbBooks.getItems().isEmpty()) {
                    MessageBox.getMessageBox("INFO", "There is no result", Alert.AlertType.INFORMATION).show();
                }

            } catch (SQLException ex) {
                MessageBox.getMessageBox("ERROR", ex.getMessage(), Alert.AlertType.WARNING).show();
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
        TableColumn colCate = new TableColumn("Cate");
        colCate.setCellValueFactory(new PropertyValueFactory("categoryId"));
        colCate.setPrefWidth(50);
        TableColumn colDetail = new TableColumn();

        colDetail.setCellFactory(c -> {
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
//                                System.out.println("Book id: " + b.getId());
                                controller.hideReserve();

                                dialog.setTitle("Book information");
                                dialog.initModality(Modality.APPLICATION_MODAL);
                                dialog.initOwner(mainStage);
                                dialog.setScene(scene);

                                dialog.show();
                            } catch (IOException | SQLException ex) {
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
        
       
        TableColumn<Book, Boolean> colCheck = new TableColumn();
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
            TableCell<Book, Boolean> cell = new TableCell<>() {
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
                         Book b = tbBooks.getItems().get(this.getTableRow().getIndex());
                         if(b.isSelected()){
                             b.setSelected(false);
                             return;
                         }
                         b.setSelected(true);
                    });
                }

            };
            return cell;

        });

        this.tbBooks.getColumns().addAll(colTitle, colAuthors, colYear, colCate, colDetail, colCheck);

    }

    private void loadBooks(String kw, String criteria) throws SQLException {
        BookService service = new BookService();
        List<Book> books = service.getBooks(kw, criteria);
        this.tbBooks.getItems().clear();
        this.tbBooks.setItems(FXCollections.observableList(books));
    }

    @FXML
    public void lendAll(ActionEvent e) throws IOException {
        
        int quantityOfBooks = 0;
        ObservableList<Book> selectedBooks = FXCollections.observableArrayList();
        ObservableList<Book> allSelectedBooks = tbBooks.getItems();
        ArrayList<Integer> bookIds = new ArrayList<>();
       
        for (Book b : allSelectedBooks) {
            if (b.isSelected()) {
                bookIds.add(b.getId());
                quantityOfBooks++;
            }
        }
        
//        for(int i : bookIds){
//            System.out.println(i);
//        }

        if(quantityOfBooks == 0){
            MessageBox.getMessageBox("ERROR", "No books is choosed", Alert.AlertType.ERROR).show();
            return;
        }else if(quantityOfBooks >= 6){
            MessageBox.getMessageBox("ERROR", "Can't borrow more than 5 books ", Alert.AlertType.ERROR).show();
            return;
        }
        
        Stage mainStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("selectUserBorrowBook.fxml"));
        Parent bookViewParent = loader.load();
        Stage dialog = new Stage();

        Scene scene = new Scene(bookViewParent);
        selectUserBorrowController controller = loader.getController();
        controller.setStage(mainStage);
        
        controller.setBookIds(bookIds);
        
//        controller.setId(Id);

        dialog.setTitle("Select the borrow one ");

        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(mainStage);
        dialog.setScene(scene);
        dialog.show();
//        System.out.println(selectedBooks.toString());
//        tbBooks.getItems().forEach(book -> System.out.println(book.isSelected()));
    }

    @FXML
    public void addBook(ActionEvent e) throws IOException {
        Stage mainStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("addBtnPage.fxml"));
        Parent addBookViewParent = loader.load();
        Stage dialog = new Stage();
        Scene scene = new Scene(addBookViewParent);

        dialog.setTitle("Select the borrow one ");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(mainStage);
        dialog.setScene(scene);
        dialog.show();
    }

    @FXML
    public void deleteBook(ActionEvent e) throws SQLException {
         if (tbBooks.getSelectionModel().getSelectedItem() == null) {
            MessageBox.getMessageBox("ERROR", "Select book to delete", Alert.AlertType.ERROR).show();
            return;
        }
         
        BookService bookService = new BookService();
        Book deleleBook = tbBooks.getSelectionModel().getSelectedItem();
        int bookId = deleleBook.getId();
        bookService.deleteBook(bookId);

        tbBooks.getItems().removeAll(tbBooks.getSelectionModel().getSelectedItem());

    }

    @FXML
    public void updateBook(ActionEvent e) throws IOException, SQLException {
        if (tbBooks.getSelectionModel().getSelectedItem() == null) {
            MessageBox.getMessageBox("ERROR", "Select book to update", Alert.AlertType.ERROR).show();
            return;
        }

        BookService bookService = new BookService();
        Book updateBook = tbBooks.getSelectionModel().getSelectedItem();

        Stage mainStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("updateBtnPage.fxml"));
        Parent addBookViewParent = loader.load();
        Stage dialog = new Stage();
        Scene scene = new Scene(addBookViewParent);
        addBookController controller = loader.getController();
        controller.setBook(updateBook);

        dialog.setTitle("Select the borrow one ");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(mainStage);
        dialog.setScene(scene);
        dialog.show();
    }
}
