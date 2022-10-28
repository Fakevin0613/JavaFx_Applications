module com.example.a2enhanced {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires javafx.base;


    opens com.example.a2enhanced to javafx.fxml;
    exports com.example.a2enhanced;
}