module com.libraryproject.libraryproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.libraryproject.libraryproject to javafx.fxml;
    exports com.libraryproject.libraryproject;
}
