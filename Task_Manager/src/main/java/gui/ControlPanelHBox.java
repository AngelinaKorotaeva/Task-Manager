package gui;

import enumClass.Priority_task;
import enumClass.Status_task;
import java.util.NoSuchElementException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import spravaZaznamu.Sprava;
import java.time.LocalDate;
import javafx.scene.control.DatePicker;
import zaznamy.Task;

public class ControlPanelHBox {

    private Sprava spravaTasks = new Sprava();

    private final Button btnAddTask = new Button("Add Task");
    private final Button btnUpdateTask = new Button("Update Task");
    private final Button btnDeleteTask = new Button("Delete Task");

    private final ChoiceBox<Priority_task> priorityTask = new ChoiceBox<>();
    private final ChoiceBox<Status_task> statusTask = new ChoiceBox<>();

    private final HBox hboxPanelList = new HBox();
    private final HBox hboxPanelButtons = new HBox();

    private final ObservableList<Priority_task> priority = FXCollections.observableArrayList(Priority_task.values());
    private final ObservableList<Status_task> status = FXCollections.observableArrayList(Status_task.values());

    private static final int ROOT_WIDTH = 700;
    private static final int ROOT_HEIGHT = 500;
    private static final int VELKY_WIDTH = 500;
    private static final int BTN_WIDTH = 70;
    private static final int SPACING = 10;

    private final ListView<String> listTasks;
    private final ObservableList<String> obsListTasks = FXCollections.observableArrayList();

    public ControlPanelHBox(HBox hbox, Sprava sprava) {
        this.spravaTasks = sprava;

        priorityTask.setItems(priority);
        priorityTask.getSelectionModel().clearSelection();
        statusTask.setItems(status);
        statusTask.getSelectionModel().clearSelection();

        this.btnAddTask.setPrefWidth(BTN_WIDTH);
        this.btnUpdateTask.setPrefWidth(BTN_WIDTH);
        this.btnDeleteTask.setPrefWidth(BTN_WIDTH);

        this.listTasks = new ListView();
        listTasks.setMaxSize(VELKY_WIDTH, ROOT_HEIGHT);
        listTasks.setItems(obsListTasks);

        btnAddTaskAction();
        btnUpdateTaskAction();
        btnDeleteTaskAction();
        priorityTaskAction();
        statusTaskAction();

        GridPane grid = new GridPane();

        hboxPanelList.getChildren().addAll(listTasks);
        hboxPanelButtons.getChildren().addAll(btnAddTask, btnUpdateTask, btnDeleteTask, priorityTask, statusTask);

        hboxPanelList.setMaxSize(VELKY_WIDTH, ROOT_HEIGHT);
        hboxPanelList.setSpacing(SPACING);
        hboxPanelList.setAlignment(Pos.CENTER);
        hboxPanelButtons.setMaxSize(ROOT_WIDTH - VELKY_WIDTH, ROOT_HEIGHT);
        hboxPanelButtons.setSpacing(SPACING);
        hboxPanelButtons.setAlignment(Pos.CENTER);

        grid.add(hboxPanelList, 0, 0);
        grid.add(hboxPanelButtons, 1, 0);

        hbox.setSpacing(SPACING);

        hbox.getChildren().addAll(grid);
    }

    private void btnAddTaskAction() {
        btnAddTask.setOnAction((event) -> {
            openAddTaskWindow();
        });
    }

    private void openAddTaskWindow() {
        Stage stage = new Stage();
        stage.setTitle("Add Task");

        TextField nameField = new TextField();
        nameField.setPromptText("Task name");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Description");

        ChoiceBox<Priority_task> priorityBox = new ChoiceBox<>();
        priorityBox.setItems(FXCollections.observableArrayList(Priority_task.values()));

        ChoiceBox<Status_task> statusBox = new ChoiceBox<>();
        statusBox.setItems(FXCollections.observableArrayList(Status_task.values()));

        DatePicker deadlinePicker = new DatePicker();

        Button saveButton = new Button("Save");

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        root.getChildren().addAll(new Label("Name:"), nameField, new Label("Description:"), descriptionField,
                new Label("Priority:"), priorityBox, new Label("Status:"), statusBox, new Label("Deadline:"),
                deadlinePicker, saveButton
        );
        
        saveButton.setOnAction(e -> {
            if (nameField.getText().isBlank() || priorityBox.getValue() == null || statusBox.getValue() == null || 
                    deadlinePicker.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Missing data");
                alert.setHeaderText(null);
                alert.setContentText("Fill name, priority, status and deadline.");
                alert.showAndWait();
                return;
            }

            Task task = new Task(0,nameField.getText(),descriptionField.getText(),
                    priorityBox.getValue(),statusBox.getValue(),deadlinePicker.getValue());

            spravaTasks.addTask(task);
            obsListTasks.add(task.toString());

            stage.close();
        });

        Scene scene = new Scene(root, 300, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void btnUpdateTaskAction() {
        btnUpdateTask.setOnAction((event) -> {

        });
    }

    private void btnDeleteTaskAction() {
        btnDeleteTask.setOnAction((event) -> {

        });
    }

    private void priorityTaskAction() {
        priorityTask.setOnAction((event) -> {

        });
    }

    private void statusTaskAction() {
        statusTask.setOnAction((event) -> {

        });
    }
}
