module org.example.progress {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;


    opens org.example.progress to javafx.fxml;
    exports org.example.progress;
    exports org.example.progress.Controllers;
    opens org.example.progress.Controllers to javafx.fxml;
}