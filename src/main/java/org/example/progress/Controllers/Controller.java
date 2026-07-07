package org.example.progress.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.example.progress.Models.Task;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    //Поля
    @FXML private TextField titleTaskField;
    @FXML private ListView<Task> tasksListView;

    private ArrayList<Task> tasks = new ArrayList<>();
    private static final String saveFile = "tasks.json";

    // Методы
    @FXML
    private void initialize() {
        loadTasks();
        refreshList();

        tasksListView.setCellFactory(listView -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);

                if (empty || task == null) {
                    setGraphic(null);
                    return;
                }

                CheckBox checkBox = new CheckBox();
                checkBox.setSelected(task.isCompleted());

                checkBox.selectedProperty().addListener((obs, oldValue, newValue) -> {
                    task.setCompleted(newValue);
                    saveTasks();

                    refreshList();
                });

                Label label = new Label(task.getTitle());
                label.setWrapText(true);
                label.setMaxWidth(Double.MAX_VALUE);
                HBox.setHgrow(label, Priority.ALWAYS);

                if (task.isCompleted()) {
                    label.setStyle("-fx-text-fill: gray;");
                } else {
                    label.setStyle("-fx-text-fill: black;");
                }

                Button deleteButton = new Button("✕");
                deleteButton.getStyleClass().add("delete-button");

                deleteButton.setOnAction(event -> {
                    tasks.remove(task);
                    refreshList();
                    saveTasks();
                });

                HBox hbox = new HBox(10, checkBox, label, deleteButton);
                hbox.setPadding(new Insets(5, 0, 5, 0));
                hbox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

                setGraphic(hbox);
            }
        });
    }

    // Добавление задачи
    @FXML
    protected void addTask() {
        String text = titleTaskField.getText().trim();
        if (!text.isEmpty()) {
            tasks.add(new Task(text));
            titleTaskField.clear();
            refreshList();
        }
    }

    // Обновление списка
    @FXML
    private void refreshList() {
        tasksListView.getItems().clear();
        tasksListView.getItems().addAll(tasks);
        saveTasks();
    }

    // Сохранение задач
    private void saveTasks() {
        JSONArray jsonArray = new JSONArray();
        for (Task t : tasks) {
            JSONObject obj = new JSONObject();
            obj.put("title", t.getTitle());
            obj.put("completed", t.isCompleted());
            jsonArray.add(obj);
        }

        try (FileWriter writer = new FileWriter(saveFile)) {
            writer.write(jsonArray.toJSONString());
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Загрузка всех задач
    private void loadTasks() {
        tasks = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(saveFile)) {
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            for (Object obj : jsonArray) {
                JSONObject jsonObj = (JSONObject) obj;
                String title = (String) jsonObj.get("title");
                boolean completed = (boolean) jsonObj.get("completed");
                Task t = new Task(title);
                t.setCompleted(completed);
                tasks.add(t);
            }
        } catch (org.json.simple.parser.ParseException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}