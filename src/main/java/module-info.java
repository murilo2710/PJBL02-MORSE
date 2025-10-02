module com.example.pjb2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pjb2 to javafx.fxml;
    exports com.example.pjb2;
}