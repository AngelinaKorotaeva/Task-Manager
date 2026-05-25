package gui;

import enumClass.Priority_task;
import enumClass.Status_task;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import spravaZaznamu.Sprava;
import javafx.scene.control.DatePicker;
import zaznamy.Task;

public class ControlPanelHBox {

    private Sprava spravaTasks = new Sprava();
    private int IdSprava = 1;

    private final Button btnAddTask = new Button("Add Task");
    private final Button btnUpdateTask = new Button("Update Task");
    private final Button btnDeleteTask = new Button("Delete Task");

    private final ChoiceBox<Priority_task> priorityTask = new ChoiceBox<>();
    private final ChoiceBox<Status_task> statusTask = new ChoiceBox<>();

    private final HBox hboxPanelList = new HBox();
    private final VBox vboxPanelButtons = new VBox();

    private final ObservableList<Priority_task> priority = FXCollections.observableArrayList(Priority_task.values());
    private final ObservableList<Status_task> status = FXCollections.observableArrayList(Status_task.values());

    private static final int ROOT_WIDTH = 700;
    private static final int ROOT_HEIGHT = 500;
    private static final int VELKY_WIDTH = 560;
    private static final int BTN_WIDTH = 90;
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
        listTasks.setMinSize(VELKY_WIDTH, ROOT_HEIGHT);
        listTasks.setMaxSize(VELKY_WIDTH, ROOT_HEIGHT);
        listTasks.setItems(obsListTasks);

        btnAddTaskAction();
        btnUpdateTaskAction();
        btnDeleteTaskAction();
        priorityTaskAction();
        statusTaskAction();

        GridPane grid = new GridPane();
        grid.setMaxSize(ROOT_WIDTH, ROOT_HEIGHT);

        hboxPanelList.getChildren().addAll(listTasks);
        vboxPanelButtons.getChildren().addAll(btnAddTask, btnUpdateTask, btnDeleteTask, priorityTask, statusTask);

        hboxPanelList.setMaxSize(VELKY_WIDTH, ROOT_HEIGHT);
        hboxPanelList.setSpacing(SPACING);
        hboxPanelList.setAlignment(Pos.CENTER);
        vboxPanelButtons.setMaxSize((ROOT_WIDTH - VELKY_WIDTH), ROOT_HEIGHT);
        vboxPanelButtons.setMinSize((ROOT_WIDTH - VELKY_WIDTH), ROOT_HEIGHT);
        vboxPanelButtons.setSpacing(SPACING);
        vboxPanelButtons.setAlignment(Pos.CENTER);

        grid.add(hboxPanelList, 0, 0);
        grid.add(vboxPanelButtons, 1, 0);

        hbox.setSpacing(SPACING);
        //HBox.setHgrow(listTasks, Priority.NEVER);
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
        Button cancelButton = new Button("Cancel");

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        root.getChildren().addAll(new Label("Name:"), nameField, new Label("Description:"), descriptionField,
                new Label("Priority:"), priorityBox, new Label("Status:"), statusBox, new Label("Deadline:"),
                deadlinePicker, saveButton, cancelButton
        );

        saveButton.setOnAction(e -> {
            if (nameField.getText().isBlank() || priorityBox.getValue() == null || statusBox.getValue() == null
                    || deadlinePicker.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Missing data");
                alert.setHeaderText(null);
                alert.setContentText("Fill name, priority, status and deadline.");
                alert.showAndWait();
                return;
            }

            Task task = new Task(IdSprava, nameField.getText(), descriptionField.getText(),
                    priorityBox.getValue(), statusBox.getValue(), deadlinePicker.getValue());
            IdSprava++;

            spravaTasks.addTask(task);
            obsListTasks.add(task.toString());

            stage.close();
        });

        cancelButton.setOnAction((eventCancel) -> {
            stage.close();
        });

        Scene scene = new Scene(root, 300, 400);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void btnUpdateTaskAction() {
        btnUpdateTask.setOnAction((event) -> {
            if (listTasks.getSelectionModel().getSelectedItem() != null) {
                String[] words = listTasks.getSelectionModel().getSelectedItem().split(", ");
                Stage stage = new Stage();
                stage.setTitle("Update Task");

                TextField nameField = new TextField();
                nameField.setPromptText(words[1]);

                TextField descriptionField = new TextField();
                descriptionField.setPromptText(words[2]);

                DatePicker deadlinePicker = new DatePicker();

                Button saveButton = new Button("Save");
                Button cancelButton = new Button("Cancel");

                GridPane root = new GridPane();

                root.add(new Label("Name: "), 0, 0);
                root.add(nameField, 1, 0);
                root.add(new Label("Description: "), 0, 1);
                root.add(descriptionField, 1, 1);
                root.add(new Label("Deadline: "), 0, 2);
                root.add(deadlinePicker, 1, 2);
                root.add(cancelButton, 0, 3);
                root.add(saveButton, 1, 3);

                saveButton.setOnAction((e) -> {
                    if (nameField.getText().isBlank() || descriptionField.getText().isBlank() || deadlinePicker.getValue() == null) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Missing data");
                        alert.setHeaderText(null);
                        alert.setContentText("Fill name, priority, status and deadline.");
                        alert.showAndWait();
                        return;
                    }
                    Task oldTask = findTask(listTasks.getSelectionModel().getSelectedItem());
                    Task newTask = new Task(oldTask.getId(), nameField.getText(), descriptionField.getText(),
                            oldTask.getPriority(), oldTask.getStatus(), deadlinePicker.getValue());
                    spravaTasks.updateTask(Integer.parseInt(words[0]), newTask);
                    int i = obsListTasks.indexOf(oldTask.toString());
                    obsListTasks.set(i, newTask.toString());

                    listTasks.getSelectionModel().select(newTask.toString());
                });

                cancelButton.setOnAction((eventCancel) -> {
                    stage.close();
                });

                Scene scene = new Scene(root, 300, 400);
                stage.setScene(scene);
                stage.show();
            }
        });
    }

    private void btnDeleteTaskAction() {
        btnDeleteTask.setOnAction((event) -> {
            if (listTasks.getSelectionModel().getSelectedItem() != null) {
                String str = listTasks.getSelectionModel().getSelectedItem();
                Task task = findTask(str);
                if (task == null) {
                    return;
                }
                obsListTasks.remove(listTasks.getSelectionModel().getSelectedItem());
                spravaTasks.deleteTask(task.getId());
                IdSprava--;
            }
        });
    }

    private void priorityTaskAction() {
        priorityTask.setOnAction((event) -> {
            if (listTasks.getSelectionModel().getSelectedItem() != null
                    && priorityTask.getSelectionModel().getSelectedItem() != null) {
                String str = listTasks.getSelectionModel().getSelectedItem();
                Task task = findTask(str);
                if (task == null) {
                    return;
                }
                task.setPriority(priorityTask.getSelectionModel().getSelectedItem());
                spravaTasks.updateTask(task.getId(), task);
                obsListTasks.set(task.getId(), task.toString());
            }
        });
    }

    private void statusTaskAction() {
        statusTask.setOnAction((event) -> {
            if (listTasks.getSelectionModel().getSelectedItem() != null
                    && statusTask.getSelectionModel().getSelectedItem() != null) {
                String str = listTasks.getSelectionModel().getSelectedItem();
                Task task = findTask(str);
                if (task == null) {
                    return;
                }
                task.setStatus(statusTask.getSelectionModel().getSelectedItem());
                spravaTasks.updateTask(task.getId(), task);
                obsListTasks.set(task.getId(), task.toString());
            }
        });
    }

    private Task findTask(String str) {
        String[] words = str.split(", ");
        Task task = spravaTasks.findTask(Integer.parseInt(words[0]));

        return task;
    }
}
