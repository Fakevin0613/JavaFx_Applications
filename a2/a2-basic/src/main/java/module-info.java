module com.example.a2basic {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens com.example.a2basic to javafx.fxml;
    exports com.example.a2basic;
}