module com.example.a1enhanced {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    opens com.example.a1enhanced to javafx.fxml;
    exports com.example.a1enhanced;
}