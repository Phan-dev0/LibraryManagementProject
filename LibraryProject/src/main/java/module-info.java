module com.gr2.libraryproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.gr2.libraryproject to javafx.fxml;
    exports com.gr2.libraryproject;
    exports com.gr2.pojos;
    exports com.gr2.services;
}
