package org.example.progress;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 420, 240);
        stage.setTitle("Progress");
        stage.setMinWidth(420);
        stage.setMinHeight(240);
        stage.setScene(scene);
        stage.show();
    }
}
