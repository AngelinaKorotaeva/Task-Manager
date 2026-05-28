package gui;

import enumClass.Priority_task;
import enumClass.Status_task;
import java.util.List;
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
import javafx.scene.control.ListCell;
import zaznamy.Task;

public class ControlPanelHBox {

    private Sprava spravaTasks = new Sprava();
    private int IdSprava = 1;

    private final Button btnAddTask = new Button("Add Task");
    private final Button btnUpdateTask = new Button("Update Task");
    private final Button btnDeleteTask = new Button("Delete Task");
    private final Button btnDoFiltr = new Button("Do filter");

    private final TextField tfFiltr = new TextField();

    private final ChoiceBox<Priority_task> priorityTask = new ChoiceBox<>();
    private final ChoiceBox<Status_task> statusTask = new ChoiceBox<>();

    private final VBox vboxPanelList = new VBox();
    private final VBox vboxPanelButtons = new VBox();
    private final HBox hboxFiltrsPanel = new HBox();

    private final ObservableList<Priority_task> priority = FXCollections.observableArrayList(Priority_task.values());
    private final ObservableList<Status_task> status = FXCollections.observableArrayList(Status_task.values());

    private static final int ROOT_WIDTH = 700;
    private static final int ROOT_HEIGHT = 500;
    private static final int VELKY_WIDTH = 560;
    private static final int BTN_WIDTH = 90;
    private static final int CHOICE_BOX_WIDTH = 100;
    private static final int SPACING = 10;

    private final ListView<Task> listTasks;
    private final ObservableList<Task> obsListTasks = FXCollections.observableArrayList();

    private Priority_task aktualPriority = null;
    private Status_task aktualStatus = null;

    public ControlPanelHBox(HBox hbox, Sprava sprava) {

        this.spravaTasks = sprava;

        priorityTask.setItems(priority);
        priorityTask.getSelectionModel().clearSelection();
        priorityTask.setPrefWidth(CHOICE_BOX_WIDTH);

        priorityTask.setOnAction((evPrior) -> {
            actionPriority();
        });

        statusTask.setItems(status);
        statusTask.getSelectionModel().clearSelection();
        statusTask.setPrefWidth(CHOICE_BOX_WIDTH);

        btnAddTask.setPrefWidth(BTN_WIDTH);
        btnUpdateTask.setPrefWidth(BTN_WIDTH);
        btnDeleteTask.setPrefWidth(BTN_WIDTH);
        btnDoFiltr.setPrefWidth(BTN_WIDTH);

        btnAddTaskAction();
        btnUpdateTaskAction();
        btnDeleteTaskAction();
        priorityTaskAction();
        statusTaskAction();
        btnDoFilterAction();

        // ================= GRID =================
        GridPane grid = new GridPane();
        grid.setMaxSize(ROOT_WIDTH, ROOT_HEIGHT);
        grid.setPadding(new Insets(5));
        grid.setVgap(5);
        grid.setHgap(10);

        // ================= FILTER PANEL =================
        tfFiltr.setPrefWidth(110);
        tfFiltr.setPrefHeight(25);

        hboxFiltrsPanel.getChildren().addAll(
                new Label("filtr:"),
                tfFiltr,
                btnDoFiltr
        );

        hboxFiltrsPanel.setAlignment(Pos.CENTER_LEFT);
        hboxFiltrsPanel.setSpacing(5);

        // ================= LIST =================
        listTasks = new ListView<>();
        listTasks.setItems(obsListTasks);

        listTasks.setMinSize(VELKY_WIDTH, 430);
        listTasks.setMaxSize(VELKY_WIDTH, 430);

        // Говорим ListView, как нужно рисовать каждую ячейку
        listTasks.setCellFactory(param -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);

                if (empty || task == null) {
                    setText(null);
                    setStyle("");
                    return;
                }

                String statusIcon = "";

                switch (task.getStatus()) {
                    case TO_DO:
                        statusIcon = "○ ";
                        break;
                    case IN_PROGRESS:
                        statusIcon = "⏳ ";
                        break;
                    case COMPLETED:
                        statusIcon = "✓ ";
                        break;
                    case CANCELED:
                        statusIcon = "✕ ";
                        break;
                }
                setText(statusIcon + task.toString());

                String color = "";

                switch (task.getPriority()) {
                    case HIGH:
                        color = "#ffb3b3";
                        break;

                    case MEDIUM:
                        color = "#fff0b3";
                        break;

                    case LOW:
                        color = "#b3ffb3";
                        break;
                }

                if (isSelected()) {

                    setStyle(
                            "-fx-background-color: " + color + ";"
                            + "-fx-border-color: hotpink;"
                            + "-fx-border-width: 2;"
                            + "-fx-text-fill: black;"
                    );

                } else {

                    setStyle(
                            "-fx-background-color: " + color + ";"
                            + "-fx-text-fill: black;"
                    );
                }
            }
        });

        vboxPanelList.getChildren().add(listTasks);

        vboxPanelList.setMinSize(VELKY_WIDTH, 430);
        vboxPanelList.setMaxSize(VELKY_WIDTH, 430);

        vboxPanelList.setSpacing(SPACING);
        vboxPanelList.setAlignment(Pos.CENTER);

        // ================= BUTTONS =================
        vboxPanelButtons.getChildren().addAll(
                btnAddTask,
                btnUpdateTask,
                btnDeleteTask,
                priorityTask,
                statusTask
        );

        vboxPanelButtons.setMinSize(
                ROOT_WIDTH - VELKY_WIDTH,
                430
        );

        vboxPanelButtons.setMaxSize(
                ROOT_WIDTH - VELKY_WIDTH,
                430
        );

        vboxPanelButtons.setSpacing(SPACING);
        vboxPanelButtons.setAlignment(Pos.CENTER);

        // ================= GRID ADD =================
        grid.add(hboxFiltrsPanel, 0, 0);
        grid.add(vboxPanelList, 0, 1);
        grid.add(vboxPanelButtons, 1, 1);

        hbox.getChildren().add(grid);
    }

    private void actionPriority() {
        if (priorityTask.getSelectionModel().getSelectedItem() != null) {
            refreshList(priorityTask.getSelectionModel().getSelectedItem(), aktualStatus);
        }
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
            obsListTasks.add(task);

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
                Stage stage = new Stage();
                stage.setTitle("Update Task");

                TextField nameField = new TextField();
                nameField.setPromptText(listTasks.getSelectionModel().getSelectedItem().getName());

                TextField descriptionField = new TextField();
                descriptionField.setPromptText(listTasks.getSelectionModel().getSelectedItem().getDescription());

                DatePicker deadlinePicker = new DatePicker();
                deadlinePicker.setValue(listTasks.getSelectionModel().getSelectedItem().getDeadline());

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
                    Task oldTask = listTasks.getSelectionModel().getSelectedItem();
                    Task newTask = new Task(oldTask.getId(), nameField.getText(), descriptionField.getText(),
                            oldTask.getPriority(), oldTask.getStatus(), deadlinePicker.getValue());
                    spravaTasks.updateTask(oldTask.getId(), newTask);
                    int i = obsListTasks.indexOf(oldTask.toString());
                    obsListTasks.set(i, newTask);

                    listTasks.getSelectionModel().select(newTask);
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
                Task task = listTasks.getSelectionModel().getSelectedItem();
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
                Task task = listTasks.getSelectionModel().getSelectedItem();
                if (task == null) {
                    return;
                }
                task.setPriority(priorityTask.getSelectionModel().getSelectedItem());
                spravaTasks.updateTask(task.getId(), task);

                int index = listTasks.getSelectionModel().getSelectedIndex();
                obsListTasks.set(index, task);
                listTasks.refresh();

            }
        });
    }

    private void statusTaskAction() {
        statusTask.setOnAction((event) -> {
            if (listTasks.getSelectionModel().getSelectedItem() != null
                    && statusTask.getSelectionModel().getSelectedItem() != null) {
                Task task = listTasks.getSelectionModel().getSelectedItem();
                if (task == null) {
                    return;
                }
                task.setStatus(statusTask.getSelectionModel().getSelectedItem());
                spravaTasks.updateTask(task.getId(), task);
                obsListTasks.set(listTasks.getSelectionModel().getSelectedIndex(), task);
                listTasks.refresh();
            }
        });
    }

    private void btnDoFilterAction() {
        btnDoFiltr.setOnAction((event) -> {
            String filterText = tfFiltr.getText().trim();

            if (filterText.isEmpty()) {
                obsListTasks.setAll(spravaTasks.getAllTasks());
                listTasks.refresh();
                return;
            }

            String[] filters = filterText.split(",\\s");
            List<Task> tasks = spravaTasks.getAllTasks();
            ObservableList<Task> filteredTasks = FXCollections.observableArrayList();

            for (Task task : tasks) {

                boolean matches = false;

                for (String filter : filters) {

                    if (filter.toUpperCase().equals(task.getPriority().name())
                            || filter.toUpperCase().equals(task.getStatus().name())
                            || task.getName().contains(filter)
                            || task.getDescription().contains(filter)) {

                        matches = true;
                        break;
                    }
                }

                if (matches) {
                    filteredTasks.add(task);
                }
            }

            obsListTasks.setAll(filteredTasks);
            listTasks.refresh();

        });
    }

    private void refreshList(Priority_task prior, Status_task status) {
        if (prior != null) {
            switch (prior) {
                case HIGH -> {

                }
                case MEDIUM -> {

                }
                case LOW -> {

                }
            }
        }

        if (status != null) {
            switch (status) {
                case TO_DO -> {

                }
                case IN_PROGRESS -> {

                }
                case CANCELED -> {

                }
                case COMPLETED -> {

                }
            }
        }

    }
}
