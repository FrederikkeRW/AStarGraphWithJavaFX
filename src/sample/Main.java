package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //AStarGraph graph = createGraph();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Select a trip");
        primaryStage.setScene(new Scene(root, 362, 330));


        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
