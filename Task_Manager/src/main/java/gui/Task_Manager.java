package gui;

import database.Database;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import spravaZaznamu.Sprava;

public class Task_Manager extends Application{
    private Sprava spravaTasks = new Sprava();
    private ControlPanelHBox hBox;
    
    private static final int ROOT_WIDTH = 700;
    private static final int ROOT_HEIGHT = 500;
    private static final int SPACING = 10;

    public static void main(String[] args) {
        Database.initDatabase();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox hbox = new HBox();
        hbox.setSpacing(SPACING);
        hbox.setFillHeight(true);
        hBox = new ControlPanelHBox(hbox, spravaTasks);
        
        Scene scene = new Scene(hbox,ROOT_WIDTH,ROOT_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
