module com.gr2.libraryproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.gr2.libraryproject to javafx.fxml;
    exports com.gr2.libraryproject;
}
